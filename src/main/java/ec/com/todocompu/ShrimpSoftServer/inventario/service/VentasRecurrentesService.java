/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClientesVentasDetalleDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteRecurrenteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientesVentasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author mario
 */
@Service
public class VentasRecurrentesService {

    @Autowired
    private ClientesVentasDetalleDao clientesVentasDetalleDao;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private VentasService ventasService;
    @Autowired
    private VentasMotivoService ventasMotivoService;
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;
    @Autowired
    private EnviarCorreoService envioCorreoService;

    @Async("asyncExecutor")
    public void recorrerClientesParaVentasRecurrentes(List<InvClienteRecurrenteTO> clientes, String empresa, InvVentasTO venta, String tipodocumento, String secuencial, String descripcionProducto,
            boolean debeContabilizar, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception {
        List<RespuestaWebTO> respuestas = new ArrayList<>();
        int guardadas = 0;
        try {
            for (InvClienteRecurrenteTO cliente : clientes) {
                List<InvClientesVentasDetalle> detalles = clientesVentasDetalleDao.listarInvClientesVentasDetalle(empresa, cliente.getVrCodigo(), cliente.getVrGrupo());
                RespuestaWebTO respuestaRecurrente = new RespuestaWebTO();
                respuestaRecurrente.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                if (detalles != null && !detalles.isEmpty()) {
                    respuestaRecurrente.setExtraInfo(detalles);
                    InvCliente clienteEntidad = detalles.get(0).getInvCliente();
                    String tipoDoc = clienteEntidad.getInvVentasMotivo() != null ? clienteEntidad.getInvVentasMotivo().getTcCodigo() : null;
                    if (!clienteService.validarClienteParaVentarecurrente(empresa, venta.getVtaFecha(), clienteEntidad.getInvClientePK().getCliCodigo(), cliente.getVrGrupo())) {
                        respuestaRecurrente.setOperacionMensaje("El cliente: " + cliente.getVrRazonSocial() + " ya tiene una venta recurrente.");
                        respuestas.add(respuestaRecurrente);
                    } else {
                        MensajeTO mensaje = new MensajeTO();
                        try {
                            String documento = tipoDoc != null && !tipoDoc.equals("") ? tipoDoc : tipodocumento;
                            mensaje = ventasService.insertarVentasRecurrentes(empresa, clienteEntidad, documento, venta, detalles, cliente.getVrGrupo(), cliente.getVrContrato(), secuencial, descripcionProducto, sisInfoTO);
                        } catch (Exception e) {
                            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
                            mensaje.setMensaje("F" + e.getMessage());
                        }
                        if (mensaje != null && mensaje.getMensaje().charAt(0) == 'T') {//EXITO
                            guardadas++;
                            InvVentasTO ventaResultado = (InvVentasTO) mensaje.getMap().get("invVentasTO");
                            ventasService.quitarPendiente(ventaResultado);
                            respuestaRecurrente.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                            mensaje.setMensaje(mensaje.getMensaje().substring(0, mensaje.getMensaje().lastIndexOf("</html>") + 7));
                            respuestaRecurrente.setOperacionMensaje(clienteEntidad.getCliRazonSocial() + " => " + mensaje.getMensaje().substring(1));
                            if (debeContabilizar || debeContabilizar(empresa, ventaResultado.getVtaMotivo())) {
                                MensajeTO contabilizar = ventasService.insertarInvContableVentasTO(ventaResultado.getVtaEmpresa(),
                                        ventaResultado.getVtaPeriodo(), ventaResultado.getVtaMotivo(), ventaResultado.getVtaNumero(),
                                        sisInfoTO.getUsuario(), false, null, null, sisInfoTO);
                                if (contabilizar != null && contabilizar.getMensaje().charAt(0) == 'T') {
                                    respuestaRecurrente.setExtraInfo("Venta contabilizada correctamente.");
                                } else {
                                    respuestaRecurrente.setExtraInfo("La venta no ha sido contabilizada.");
                                }
                            }
                        } else {//ERROR
                            respuestaRecurrente.setOperacionMensaje(clienteEntidad.getInvClientePK().getCliCodigo() + "-" + clienteEntidad.getCliRazonSocial() + " => " + mensaje.getMensaje().substring(1));
                            respuestaRecurrente.setExtraInfo(detalles);
                            respuestas.add(respuestaRecurrente);
                        }
                    }
                } else {//ERROR
                    respuestaRecurrente.setOperacionMensaje("El cliente: " + cliente.getVrRazonSocial() + " no tiene una configuración para venta recurrente.");
                    respuestas.add(respuestaRecurrente);
                }
            }
            cerrarEnvioVentasRecurrentes(respuestas, usuarioEmpresaReporteTO, sisInfoTO, guardadas);
        } catch (Exception e) {
            cerrarEnvioVentasRecurrentes(respuestas, usuarioEmpresaReporteTO, sisInfoTO, guardadas);
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
    }

    public void cerrarEnvioVentasRecurrentes(List<RespuestaWebTO> respuestas, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO, int guardadas) throws Exception {
        SisEmpresaParametros parametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, sisInfoTO.getEmpresa());
        if (parametros != null) {
            parametros.setParVentasEnProceso(false);
            empresaParametrosDao.actualizar(parametros);
        }
        ventasService.notificarPorCorreoVentasConError(usuarioEmpresaReporteTO, respuestas, sisInfoTO.getUsuario(), guardadas);
    }

    public boolean debeContabilizar(String empresa, String vtaMotivo) throws Exception {
        InvVentaMotivoTO motivo = ventasMotivoService.getInvVentasMotivoTO(empresa, vtaMotivo);
        return (motivo != null
                && (motivo.getVmFormaContabilizar().equals("AUTOMATICO")
                || motivo.getVmFormaContabilizar().equals("AUTOMATICO SIN NOTIFICAR")));
    }

}
