/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClienteDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClientesDireccionesDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.TransferenciasMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.TransportistaDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentaGuiaRemisionDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.PeriodoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransportistaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientesDirecciones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransportista;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentaGuiaRemision;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CarolValdiviezo
 */
@Service
public class VentaGuiaRemisionServiceImpl implements VentaGuiaRemisionService {

    //VENTA GUIA REMISION
    @Autowired
    private ClientesDireccionesDao clientesDireccionesDao;
    @Autowired
    private TransportistaDao transportistaDao;
    @Autowired
    private VentaGuiaRemisionDao ventaGuiaRemisionDao;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private ClienteDao clienteDao;
    @Autowired
    private ComprasMotivoDao comprasMotivoDao;
    @Autowired
    private TransferenciasMotivoDao transferenciasMotivoDao;
    @Autowired
    private PeriodoDao periodoDao;

    @Override
    public Map<String, Object> obtenerDatosBasicosVentaGuiaRemision(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String clienteCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("clienteCodigo"));
        Timestamp fechaActual = sistemaWebServicio.getFechaActual();

        List<InvTransportistaTO> listaTransportista = transportistaDao.getListaInvTransportistaTO(empresa, null, false);
        List<InvClientesDirecciones> listaDirecciones = clientesDireccionesDao.listarInvClientesDirecciones(empresa, clienteCodigo);
        InvCliente cliente = clienteDao.buscarInvCliente(empresa, clienteCodigo);
        if (cliente.getCliCodigoEstablecimiento() != null && !cliente.getCliCodigoEstablecimiento().contentEquals("")) {
            InvClientesDirecciones direccionDefecto = new InvClientesDirecciones();
            direccionDefecto.setDirDetalle(cliente.getCliDireccion());
            direccionDefecto.setDirCodigo(cliente.getCliCodigoEstablecimiento());
            listaDirecciones.add(direccionDefecto);
        }

        campos.put("fechaActual", fechaActual);
        campos.put("listaTransportista", listaTransportista);
        campos.put("listaDirecciones", listaDirecciones);
        return campos;
    }

    @Override
    public Map<String, Object> obtenerDatosBasicosImportarComprasTransferencias(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<InvComprasMotivoTO> listaInvComprasMotivoTO = comprasMotivoDao.getListaInvComprasMotivoTO(empresa, true);
        List<InvTransferenciaMotivoTO> listaInvTransferenciaMotivoTO = transferenciasMotivoDao.getListaTransferenciaMotivoTO(empresa, false);
        List<SisListaPeriodoTO> listaSisListaPeriodoTO = periodoDao.getListaSisPeriodoTO(empresa);

        campos.put("listaSisListaPeriodoTO", listaSisListaPeriodoTO);
        campos.put("listaInvComprasMotivoTO", listaInvComprasMotivoTO);
        campos.put("listaInvTransferenciaMotivoTO", listaInvTransferenciaMotivoTO);
        return campos;
    }

    @Override
    public InvVentaGuiaRemision obtenerInvVentaGuiaRemision(String empresa, String periodo, String motivo, String numero) {
        List<InvVentaGuiaRemision> lista = ventaGuiaRemisionDao.obtenerInvVentaGuiaRemision(empresa, periodo, motivo, numero);
        if (lista != null && lista.size() > 0) {
            return lista.get(0); //Por mientras el primer elemento
        }
        return null;
    }

    @Override
    public MensajeTO insertarTransaccionInvVentaGuiaRemision(InvVentaGuiaRemision invVentaGuiaRemision, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        boolean comprobar = false;
        MensajeTO mensajeTO = new MensajeTO();
        String empresa = invVentaGuiaRemision.getInvVentas().getInvVentasPK().getVtaEmpresa();
        susClave = invVentaGuiaRemision.getDetSecuencial() + "";
        susDetalle = "Se insertó la venta con guia de remisión: "
                + invVentaGuiaRemision.getInvVentas().getInvVentasPK().getVtaMotivo()
                + "|" + invVentaGuiaRemision.getInvVentas().getInvVentasPK().getVtaPeriodo()
                + "|" + invVentaGuiaRemision.getInvVentas().getInvVentasPK().getVtaNumero();
        susSuceso = "INSERT";
        susTabla = "inventario.inv_venta_guia_remision";
        invVentaGuiaRemision.setUsrFechaInserta(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd"));
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        ///// BUSCANDO TRANSPORTISTA
        InvTransportista transportista = invVentaGuiaRemision.getInvTransportista();
        InvTransportista invTransportista = transportistaDao.buscarInvTransportista(empresa, transportista.getInvTransportistaPK().getTransCodigo());

        if (invTransportista != null) {
            ////// CREANDO GUIA
            invVentaGuiaRemision.setInvTransportista(invTransportista);
            invVentaGuiaRemision.setUsrCodigo(sisInfoTO.getUsuario());
            invVentaGuiaRemision.setUsrFechaInserta(UtilsDate.timestamp());
            comprobar = ventaGuiaRemisionDao.accionInvVentaGuiaRemision(invVentaGuiaRemision, sisSuceso, 'I');
            if (comprobar) {
                retorno = "T<html>La guía de remisión, se ha guardado correctamente";
                Map<String, Object> campos = new HashMap<>();
                campos.put("invVentaGuiaRemision", invVentaGuiaRemision);
                mensajeTO.setMap(campos);
            } else {
                retorno = "F<html>Hubo un error al guardar la guia de remisión...\nIntente de nuevo o contacte con el administrador</html>";
            }
        } else {
            retorno = "F<html>El transportista que escogió ya no está disponible...\nIntente de nuevo, escoja otro transportista o contacte con el administrador</html>";
        }

        mensajeTO.setMensaje(retorno);
        return mensajeTO;

    }

    @Override
    public MensajeTO actualizarTransaccionInvVentaGuiaRemision(InvVentaGuiaRemision invVentaGuiaRemision, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        boolean comprobar = false;
        MensajeTO mensajeTO = new MensajeTO();

        InvVentaGuiaRemision local = ventaGuiaRemisionDao.obtenerInvVentaGuiaRemision(invVentaGuiaRemision.getDetSecuencial());
        if (local != null) {
            susClave = invVentaGuiaRemision.getDetSecuencial() + "";
            susDetalle = "Se modificó la venta con guia de remisión: "
                    + invVentaGuiaRemision.getInvVentas().getInvVentasPK().getVtaMotivo()
                    + "|" + invVentaGuiaRemision.getInvVentas().getInvVentasPK().getVtaPeriodo()
                    + "|" + invVentaGuiaRemision.getInvVentas().getInvVentasPK().getVtaNumero();
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_venta_guia_remision";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprobar = ventaGuiaRemisionDao.accionInvVentaGuiaRemision(invVentaGuiaRemision, sisSuceso, 'M');
            if (comprobar) {
                retorno = "T<html>La guía de remisión, se ha modificado correctamente";
                Map<String, Object> campos = new HashMap<>();
                campos.put("invVentaGuiaRemision", invVentaGuiaRemision);
                mensajeTO.setMap(campos);
            } else {
                retorno = "F<html>Hubo un error al guardar la guia de remisión...\nIntente de nuevo o contacte con el administrador</html>";
            }
        } else {
            retorno = "FEl motivo que va a modificar ya no existe...\nIntente con otro...";
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public MensajeTO insertarActualizarTransaccionInvVentaGuiaRemision(InvVentaGuiaRemision invVentaGuiaRemision, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        boolean comprobar = false;
        MensajeTO mensajeTO = new MensajeTO();

        InvVentaGuiaRemision local = ventaGuiaRemisionDao.obtenerInvVentaGuiaRemision(invVentaGuiaRemision.getDetSecuencial());
        if (local != null) {
            susClave = invVentaGuiaRemision.getDetSecuencial() + "";
            susDetalle = "Se modificó la venta con guia de remisión: "
                    + invVentaGuiaRemision.getInvVentas().getInvVentasPK().getVtaMotivo()
                    + "|" + invVentaGuiaRemision.getInvVentas().getInvVentasPK().getVtaPeriodo()
                    + "|" + invVentaGuiaRemision.getInvVentas().getInvVentasPK().getVtaNumero();
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_venta_guia_remision";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprobar = ventaGuiaRemisionDao.accionInvVentaGuiaRemision(invVentaGuiaRemision, sisSuceso, 'M');
            if (comprobar) {
                retorno = "T<html>La guía de remisión, se ha modificado correctamente";
                Map<String, Object> campos = new HashMap<>();
                campos.put("invVentaGuiaRemision", invVentaGuiaRemision);
                mensajeTO.setMap(campos);
            } else {
                retorno = "F<html>Hubo un error al guardar la guia de remisión...\nIntente de nuevo o contacte con el administrador</html>";
            }
            mensajeTO.setMensaje(retorno);
        } else {
            mensajeTO = insertarTransaccionInvVentaGuiaRemision(invVentaGuiaRemision, sisInfoTO);
        }
        return mensajeTO;
    }

}
