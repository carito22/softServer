/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.activoFijo.service;

import ec.com.todocompu.ShrimpSoftServer.activoFijo.dao.ActivoFijoDao;
import ec.com.todocompu.ShrimpSoftServer.activoFijo.dao.DepreciacionActivoFijoDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.ContableDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesActivoFijo;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfDepreciacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfActivos;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfDepreciaciones;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.math.BigDecimal;
import java.util.Map;
import java.util.HashMap;
import java.sql.Timestamp;

/**
 *
 * @author CarolValdiviezo
 */
@Service
public class DepreciacionActivoFijoServiceImpl implements DepreciacionActivoFijoService {

    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private DepreciacionActivoFijoDao depreciacionActivoFijoDao;
    @Autowired
    private ActivoFijoDao activoDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private ContableService contableService;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private ContableDao contableDao;

    @Override
    public List<AfDepreciacionesTO> listarDepreciaciones(String empresa, String fechaHasta) throws Exception {
        return depreciacionActivoFijoDao.listarDepreciaciones(empresa, fechaHasta);
    }

    @Override
    public List<AfDepreciacionesTO> listarDepreciacionesConsulta(String empresa, String fechaDesde, String fechaHasta) throws Exception {
        return depreciacionActivoFijoDao.listarDepreciacionesConsulta(empresa, fechaDesde, fechaHasta);
    }

    @Override
    public List<AfDepreciaciones> listarDepreciacionesSegunContable(String empresa, String periodo, String tipo, String numero) throws Exception {
        return depreciacionActivoFijoDao.listarDepreciacionesSegunContable(empresa, periodo, tipo, numero);
    }

    @Override
    public boolean productoSeEncuentraDepreciacion(String empresa, String codigoProducto) throws Exception {
        return depreciacionActivoFijoDao.productoSeEncuentraDepreciacion(empresa, codigoProducto);
    }

    @Override
    public MensajeTO insertarModificarAfDepreciaciones(String empresa, String fecha, List<AfDepreciacionesTO> listaAfDepreciacionesTO, SisInfoTO sisInfoTO) throws Exception {
        Date fechaPeriodo = UtilsDate.fechaFormatoDate(fecha, "yyyy-MM-dd");

        ConContable contable = generarContable(empresa, fechaPeriodo, "");
        ConContablePK contablePK = null;
        MensajeTO mensaje = new MensajeTO();
        String retorno = "";
        List<AfDepreciaciones> listaDepreciacionesCreadas = new ArrayList<>();
        if ((retorno = periodoService.validarPeriodo(contable.getConFecha(), empresa)) != null) {
            mensaje.setMensaje(retorno);
        } else {
            SisPeriodo periodo = periodoService.getPeriodoPorFecha(fechaPeriodo, empresa);
            for (AfDepreciacionesTO dep : listaAfDepreciacionesTO) {
                List<ConContableTO> listaContable = obtenerContablesSegunDepreciacion(empresa, periodo.getSisPeriodoPK().getPerCodigo(), dep.getAfCodigo());
                if (listaContable != null && listaContable.size() > 0) {
                    contablePK = new ConContablePK();
                    contablePK.setConEmpresa(empresa);
                    contablePK.setConNumero(listaContable.get(0).getConNumero());
                    contablePK.setConPeriodo(listaContable.get(0).getPerCodigo());
                    contablePK.setConTipo(listaContable.get(0).getTipCodigo());
                    break;
                }
            }

            if (contablePK != null) {
                contable.setConContablePK(contablePK);
                listaDepreciacionesCreadas = listarDepreciacionesSegunContable(empresa, contablePK.getConPeriodo(), contablePK.getConTipo(), contablePK.getConNumero());
            }

            List<AfDepreciaciones> lista = generarListaAfDepreciaciones(empresa, listaAfDepreciacionesTO, listaDepreciacionesCreadas, sisInfoTO);
            mensaje = insertarModificarAfDepreciaciones(contable, lista, sisInfoTO);
            if (mensaje.getMensaje() != null && mensaje.getMensaje().substring(0, 1).equals("T")) {
                contableService.mayorizarDesmayorizarSql(contable.getConContablePK(), false, sisInfoTO);
            }
        }
        return mensaje;
    }

    @Override
    public List<ConContableTO> obtenerContablesSegunDepreciacion(String empresa, String periodo, String codigoProducto) throws Exception {
        return depreciacionActivoFijoDao.obtenerContablesSegunDepreciacion(empresa, periodo, codigoProducto);
    }

    @Override
    public Map<String, Object> exportarListadoDepreciacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaIngreso, List<AfDepreciacionesTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de Listado de depreciación de activos fijos");
            listaCabecera.add("SFecha: " + fechaIngreso);
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo " + "¬" + "SCategoria" + "¬" + "SUbicación" + "¬" + "SDescripción" + "¬" + "SFecha adquisición" + "¬" + "SValor adquisición " + "¬"
                    + "SValor residual" + "¬" + "SDepreciación anterior" + "¬" + "SDepreciación actual" + "¬" + "SDepreciación acumulada " + "¬" + "SDepreciación GND");
            listado.forEach((depre) -> {
                listaCuerpo.add((depre.getAfCodigo() == null ? "B" : "S" + depre.getAfCodigo())
                        + "¬" + (depre.getAfCategoria() == null ? "B" : "S" + depre.getAfCategoria())
                        + "¬" + (depre.getAfUbicacion() == null ? "B" : "S" + depre.getAfUbicacion())
                        + "¬" + (depre.getAfDescripcion() == null ? "B" : "S" + depre.getAfDescripcion())
                        + "¬" + (depre.getAfFechaAdquisicion() == null ? "B" : "S" + depre.getAfFechaAdquisicion())
                        + "¬" + (depre.getAfValorAdquisicion() == null ? "B" : "D" + depre.getAfValorAdquisicion().add(BigDecimal.ZERO).toString())
                        + "¬" + (depre.getAfValorResidual() == null ? "B" : "D" + depre.getAfValorResidual().add(BigDecimal.ZERO).toString())
                        + "¬" + (depre.getAfDepreciacionAnterior() == null ? "B" : "D" + depre.getAfDepreciacionAnterior().add(BigDecimal.ZERO).toString())
                        + "¬" + (depre.getAfDepreciacionActual() == null ? "B" : "D" + depre.getAfDepreciacionActual().add(BigDecimal.ZERO).toString())
                        + "¬" + (depre.getAfDepreciacionAcumulada() == null ? "B" : "D" + depre.getAfDepreciacionAcumulada().add(BigDecimal.ZERO).toString())
                        + "¬" + (depre.getAfDepreciacionGnd() == null ? "B" : "D" + depre.getAfDepreciacionGnd().add(BigDecimal.ZERO).toString())
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    public MensajeTO insertarModificarAfDepreciaciones(ConContable conContable, List<AfDepreciaciones> listaAfDepreciaciones, SisInfoTO sisInfoTO) throws Exception {
        Boolean comprobar = false;
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();

        SisPeriodo periodo = periodoService.getPeriodoPorFecha(conContable.getConFecha(), conContable.getConContablePK().getConEmpresa());
        sisInfoTO.setEmpresa(conContable.getConContablePK().getConEmpresa());
        if (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) {
            conContable.getConContablePK().setConPeriodo(periodo.getSisPeriodoPK().getPerCodigo());
            conContable.setConBloqueado(false);
            conContable.setConReversado(false);
            conContable.setConAnulado(false);
            conContable.setConGenerado(true);
            conContable.setConDetalle("COMPROBANTE GENERADO POR EL SISTEMA");
            conContable.setUsrEmpresa(sisInfoTO.getEmpresa());
            conContable.setUsrCodigo(sisInfoTO.getUsuario());
            conContable.setUsrFechaInserta(UtilsDate.timestamp());
        } else {
            conContable = contableDao.obtenerPorId(ConContable.class, conContable.getConContablePK());
        }

        susSuceso = (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) ? "INSERT" : "UPDATE";
        susTabla = "activosfijos.af_depreciaciones";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        comprobar = depreciacionActivoFijoDao.insertarModificarAfDepreciaciones(conContable, listaAfDepreciaciones, sisSuceso);
        if (!comprobar) {
            retorno = "FHubo un error al guardar la depreciación de activos fijos.\nIntente de nuevo o contacte con el administrador";
        } else {
            retorno = "T<html>Se guardó la depreciación de activos fijos con la siguiente informacion:<br><br>"
                    + "Periodo: <font size = 5>" + periodo.getPerDetalle() + "</font>.<br> Motivo: <font size = 5>"
                    + conContable.getConContablePK().getConTipo() + "</font>.<br> Numero: <font size = 5>"
                    + conContable.getConContablePK().getConNumero() + "</font>.<br></html>";
            mensajeTO.setFechaCreacion(conContable.getUsrFechaInserta().toString());
            mensajeTO.setContable(conContable.getConContablePK().getConNumero());
            mensajeTO.getMap().put("conContable", conContable);
        }

        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    public List<AfDepreciaciones> generarListaAfDepreciaciones(String empresa, List<AfDepreciacionesTO> listaAfDepreciacionesTO, List<AfDepreciaciones> listaDepreciacionesCreadas, SisInfoTO sisInfoTO) throws Exception {
        List<AfDepreciaciones> lista = new ArrayList<>();

        for (AfDepreciacionesTO dep : listaAfDepreciacionesTO) {
            AfActivos activoLocal = activoDao.getAfActivos(empresa, dep.getAfCodigo());
            AfDepreciaciones depreciacion = ConversionesActivoFijo.convertirAfDepreciacionesTO_AfDepreciaciones(activoLocal, dep);
            for (AfDepreciaciones deprCreada : listaDepreciacionesCreadas) {
                if (dep.getAfCodigo().equals(deprCreada.getAfActivos().getAfActivosPK().getAfCodigo())) {
                    depreciacion.setDepSecuencial(deprCreada.getDepSecuencial());
                }
            }

            depreciacion.setUsrCodigo(sisInfoTO.getUsuario());
            depreciacion.setUsrEmpresa(empresa);
            depreciacion.setUsrFechaInserta(UtilsDate.timestamp());
            lista.add(depreciacion);
        }
        return lista;
    }

    public ConContable generarContable(String empresa, Date fecha, String numeroContable) throws Exception {
        String secuencia = UtilsValidacion.generarSecuenciaAleatoria();
        ConContable contable = new ConContable(empresa, "", "C-AF", numeroContable);
        contable.setConFecha(fecha);
        contable.setConReferencia("activosfijos.af_depreciaciones");
        contable.setConPendiente(false);
        contable.setConCodigo(secuencia);
        return contable;
    }

}
