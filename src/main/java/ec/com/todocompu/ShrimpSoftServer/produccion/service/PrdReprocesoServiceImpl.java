/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftServer.inventario.service.BodegaService;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PiscinaDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PrdReprocesoDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PrdReprocesoEgresoDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PrdReprocesoIngresoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesProduccion;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaBodegasTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdReprocesoEgresoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdReprocesoIngresoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdReprocesoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReproceso;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReprocesoEgreso;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReprocesoIngreso;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReprocesoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReprocesoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Trabajo
 */
@Service
public class PrdReprocesoServiceImpl implements PrdReprocesoService {

    @Autowired
    private PrdReprocesoDao prdReprocesoDao;
    @Autowired
    private PrdReprocesoMotivoService prdReprocesoMotivoService;
    @Autowired
    private BodegaService bodegaService;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private PrdReprocesoEgresoDao prdReprocesoEgresoDao;
    @Autowired
    private PrdReprocesoIngresoDao prdReprocesoIngresoDao;
    @Autowired
    private PiscinaDao piscinaDao;
    @Autowired
    private SucesoDao sucesoDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<PrdReprocesoTO> getListarPrdReprocesoTO(String empresa, String motivo, String nrRegistros) throws Exception {
        return prdReprocesoDao.getListarPrdReprocesoTO(empresa, motivo, nrRegistros);
    }

    @Override
    public Map<String, Object> obtenerDatosParaPrdProceso(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String accion = UtilsJSON.jsonToObjeto(String.class, map.get("accion"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        PrdReprocesoPK pk = UtilsJSON.jsonToObjeto(PrdReprocesoPK.class, map.get("pk"));
        boolean incluirTodos = accion == null || (!accion.equals("CREAR") && !accion.equals("MAYORIZAR"));
        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        SisPeriodo sisPeriodo = periodoService.getPeriodoPorFecha(fechaActual, empresa);
        List<PrdListaPiscinaTO> piscinas = piscinaDao.getListaPiscina(empresa);
        List<PrdReprocesoMotivo> listaMotivos = prdReprocesoMotivoService.listarPrdReprocesoMotivo(empresa, incluirTodos);
        List<InvListaBodegasTO> listaBodegas = bodegaService.getListaBodegasTO(empresa, incluirTodos);
        if (pk != null) {
            PrdReproceso reproceso = prdReprocesoDao.obtenerPorId(PrdReproceso.class, new PrdReprocesoPK(empresa, pk.getRepPeriodo(), pk.getRepMotivo(), pk.getRepNumero()));
            List<PrdReprocesoIngresoTO> listaIngresosTO = prdReprocesoIngresoDao.getListarPrdReprocesoIngresoTO(pk.getRepEmpresa(), pk.getRepPeriodo(), pk.getRepMotivo(), pk.getRepNumero());
            List<PrdReprocesoEgresoTO> listaEgresosTO = prdReprocesoEgresoDao.getListarPrdReprocesoEgresoTO(pk.getRepEmpresa(), pk.getRepPeriodo(), pk.getRepMotivo(), pk.getRepNumero());
            sisPeriodo = periodoService.getPeriodoPorFecha(reproceso.getRepFecha(), empresa);
            List<PrdListaPiscinaTO> piscinasFiltradas = piscinaDao.getListaPiscinaBusqueda(empresa,
                    reproceso.getInvBodega().getSecCodigo(),
                    UtilsValidacion.fecha(reproceso.getRepFecha(), "yyyy-MM-dd"), null, null, incluirTodos);
            campos.put("listaIngresosTO", listaIngresosTO);
            campos.put("listaEgresosTO", listaEgresosTO);
            campos.put("piscinasFiltradas", piscinasFiltradas);
        }
        campos.put("periodoAbierto", (sisPeriodo != null ? (sisPeriodo.getPerCerrado() ? false : true) : false));
        campos.put("periodo", sisPeriodo);
        campos.put("fechaActual", fechaActual);
        campos.put("listaMotivos", listaMotivos);
        campos.put("listaBodegas", listaBodegas);
        campos.put("piscinas", piscinas);
        return campos;

    }

    @Override
    public Map<String, Object> guardarReproceso(Map<String, Object> map) throws Exception {
        PrdReprocesoTO prdReprocesoTO = UtilsJSON.jsonToObjeto(PrdReprocesoTO.class, map.get("prdReprocesoTO"));
        List<PrdReprocesoEgresoTO> listaPrdReprocesoEgresoTO = UtilsJSON.jsonToList(PrdReprocesoEgresoTO.class, map.get("listaPrdReprocesoEgresoTO"));
        List<PrdReprocesoIngresoTO> listaPrdReprocesoIngresoTO = UtilsJSON.jsonToList(PrdReprocesoIngresoTO.class, map.get("listaPrdReprocesoIngresoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        Map<String, Object> campos = new HashMap<>();
        campos.put("mensaje", "F");
        boolean comprobar = false;
        boolean periodoCerrado = false;
        if (!UtilsValidacion.isFechaSuperior(prdReprocesoTO.getRepFecha(), "yyyy-MM-dd")) {
            List<SisListaPeriodoTO> listaSisPeriodoTO = periodoService.getListaPeriodoTO(prdReprocesoTO.getRepEmpresa());
            for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                if (UtilsValidacion.fecha(prdReprocesoTO.getRepFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion
                        .fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                        && UtilsValidacion.fecha(prdReprocesoTO.getRepFecha(), "yyyy-MM-dd")
                                .getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd")
                                .getTime()) {
                    comprobar = true;
                    prdReprocesoTO.setRepPeriodo(sisListaPeriodoTO.getPerCodigo());
                    periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                }
            }
            if (comprobar) {
                if (!periodoCerrado) {
                    if (prdReprocesoMotivoService.comprobarPrdReprocesoMotivo(prdReprocesoTO.getRepEmpresa(), prdReprocesoTO.getRepMotivo())) {
                        susSuceso = "INSERT";
                        susTabla = "produccion.prd_reproceso";
                        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                        prdReprocesoTO.setBodEmpresa(sisInfoTO.getEmpresa());
                        prdReprocesoTO.setRepEmpresa(sisInfoTO.getEmpresa());
                        prdReprocesoTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
                        prdReprocesoTO.setUsrCodigo(sisInfoTO.getUsuario());
                        //convertir a entidad
                        PrdReproceso reproceso = ConversionesProduccion.convertirPrdReprocesoTO_PrdReproceso(prdReprocesoTO);
                        List<PrdReprocesoIngreso> listaIngresos = new ArrayList<>();
                        List<PrdReprocesoEgreso> listaEgresos = new ArrayList<>();
                        if (listaPrdReprocesoIngresoTO != null) {
                            listaIngresos = listaPrdReprocesoIngresoTO.stream()
                                    .map(item -> ConversionesProduccion.convertirPrdReprocesoIngresoTO_PrdReprocesoIngreso(item))
                                    .collect(Collectors.toList());
                        }

                        if (listaPrdReprocesoEgresoTO != null) {
                            listaEgresos = listaPrdReprocesoEgresoTO.stream()
                                    .map(item -> ConversionesProduccion.convertirPrdReprocesoEgresoTO_PrdReprocesoEgreso(item))
                                    .collect(Collectors.toList());
                        }
                        MensajeTO rpta = prdReprocesoDao.guardarPrdReproceso(reproceso, listaEgresos, listaIngresos, sisSuceso);
                        Map<String, Object> mapResultado = rpta.getMap();
                        PrdReproceso reprocesoResultado = (PrdReproceso) mapResultado.get("reproceso");

                        if (reprocesoResultado != null) {
                            PrdReprocesoTO reprocesoTO = ConversionesProduccion.convertirPrdReproceso_PrdReprocesoTO(reproceso);
                            String mensaje = "T<html>El reproceso se ha guardado correctamente con la siguiente información:<br><br>"
                                    + "Periodo: <font size = 5>"
                                    + "</font>.<br> Motivo: <font size = 5>"
                                    + reprocesoResultado.getPrdReprocesoPK().getRepMotivo()
                                    + "</font>.<br> Número: <font size = 5>"
                                    + reprocesoResultado.getPrdReprocesoPK().getRepNumero()
                                    + "</font>.</html>"
                                    + reprocesoResultado.getPrdReprocesoPK().getRepNumero() + ","
                                    + reprocesoResultado.getPrdReprocesoPK().getRepPeriodo();
                            campos.put("mensaje", mensaje);
                            campos.put("prdReprocesoTO", reprocesoTO);
                        } else {
                            campos.put("mensaje", "FOcurrió un error al intentar guardar reproceso");
                        }
                    } else {
                        campos.put("mensaje", "FMotivo seleccionado no existe");
                    }
                } else {
                    campos.put("mensaje", "FPeriodo cerrado.");
                }
            } else {
                campos.put("mensaje", "FPeriodo no existe o se encuentra cerrado.");
            }
        } else {
            campos.put("mensaje", "FFecha superior ");
        }
        return campos;
    }

    @Override
    public Map<String, Object> modificarReproceso(Map<String, Object> map) throws Exception {
        PrdReprocesoTO prdReprocesoTO = UtilsJSON.jsonToObjeto(PrdReprocesoTO.class, map.get("prdReprocesoTO"));
        List<PrdReprocesoEgresoTO> listaPrdReprocesoEgresoTO = UtilsJSON.jsonToList(PrdReprocesoEgresoTO.class, map.get("listaPrdReprocesoEgresoTO"));
        List<PrdReprocesoEgresoTO> listaPrdReprocesoEgresoTOEliminar = UtilsJSON.jsonToList(PrdReprocesoEgresoTO.class, map.get("listaPrdReprocesoEgresoTOEliminar"));
        List<PrdReprocesoIngresoTO> listaPrdReprocesoIngresoTO = UtilsJSON.jsonToList(PrdReprocesoIngresoTO.class, map.get("listaPrdReprocesoIngresoTO"));
        List<PrdReprocesoIngresoTO> listaPrdReprocesoIngresoTOEliminar = UtilsJSON.jsonToList(PrdReprocesoIngresoTO.class, map.get("listaPrdReprocesoIngresoTOEliminar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        Map<String, Object> campos = new HashMap<>();
        campos.put("mensaje", "F");
        boolean comprobar = false;
        boolean periodoCerrado = false;
        if (!UtilsValidacion.isFechaSuperior(prdReprocesoTO.getRepFecha(), "yyyy-MM-dd")) {
            List<SisListaPeriodoTO> listaSisPeriodoTO = periodoService.getListaPeriodoTO(prdReprocesoTO.getRepEmpresa());
            for (SisListaPeriodoTO sisListaPeriodoTO : listaSisPeriodoTO) {
                if (UtilsValidacion.fecha(prdReprocesoTO.getRepFecha(), "yyyy-MM-dd").getTime() >= UtilsValidacion
                        .fecha(sisListaPeriodoTO.getPerDesde(), "yyyy-MM-dd").getTime()
                        && UtilsValidacion.fecha(prdReprocesoTO.getRepFecha(), "yyyy-MM-dd")
                                .getTime() <= UtilsValidacion.fecha(sisListaPeriodoTO.getPerHasta(), "yyyy-MM-dd")
                                .getTime()) {
                    comprobar = true;
                    prdReprocesoTO.setRepPeriodo(sisListaPeriodoTO.getPerCodigo());
                    periodoCerrado = sisListaPeriodoTO.getPerCerrado();
                }
            }
            if (comprobar) {
                if (!periodoCerrado) {
                    if (prdReprocesoMotivoService.comprobarPrdReprocesoMotivo(prdReprocesoTO.getRepEmpresa(), prdReprocesoTO.getRepMotivo())) {
                        susSuceso = "UPDATE";
                        susTabla = "produccion.prd_reproceso";
                        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                        prdReprocesoTO.setBodEmpresa(sisInfoTO.getEmpresa());
                        prdReprocesoTO.setRepEmpresa(sisInfoTO.getEmpresa());
                        prdReprocesoTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
                        prdReprocesoTO.setUsrCodigo(sisInfoTO.getUsuario());
                        //convertir a entidad
                        PrdReproceso reproceso = ConversionesProduccion.convertirPrdReprocesoTO_PrdReproceso(prdReprocesoTO);
                        List<PrdReprocesoIngreso> listaIngresos = new ArrayList<>();
                        List<PrdReprocesoEgreso> listaEgresos = new ArrayList<>();
                        List<PrdReprocesoIngreso> listaIngresosEliminar = new ArrayList<>();
                        List<PrdReprocesoEgreso> listaEgresosEliminar = new ArrayList<>();
                        if (listaPrdReprocesoIngresoTO != null) {
                            listaIngresos = listaPrdReprocesoIngresoTO.stream()
                                    .map(item -> ConversionesProduccion.convertirPrdReprocesoIngresoTO_PrdReprocesoIngreso(item))
                                    .collect(Collectors.toList());
                        }
                        if (listaPrdReprocesoIngresoTOEliminar != null) {
                            listaIngresosEliminar = listaPrdReprocesoIngresoTOEliminar.stream()
                                    .map(item -> ConversionesProduccion.convertirPrdReprocesoIngresoTO_PrdReprocesoIngreso(item))
                                    .collect(Collectors.toList());
                        }
                        if (listaPrdReprocesoEgresoTO != null) {
                            listaEgresos = listaPrdReprocesoEgresoTO.stream()
                                    .map(item -> ConversionesProduccion.convertirPrdReprocesoEgresoTO_PrdReprocesoEgreso(item))
                                    .collect(Collectors.toList());
                        }
                        if (listaPrdReprocesoEgresoTOEliminar != null) {
                            listaEgresosEliminar = listaPrdReprocesoEgresoTOEliminar.stream()
                                    .map(item -> ConversionesProduccion.convertirPrdReprocesoEgresoTO_PrdReprocesoEgreso(item))
                                    .collect(Collectors.toList());
                        }
                        MensajeTO rpta = prdReprocesoDao.modificarPrdReproceso(reproceso, listaEgresos, listaEgresosEliminar, listaIngresos, listaIngresosEliminar, sisSuceso);
                        Map<String, Object> mapResultado = rpta.getMap();
                        PrdReproceso reprocesoResultado = (PrdReproceso) mapResultado.get("reproceso");

                        if (reprocesoResultado != null) {
                            PrdReprocesoTO reprocesoTO = ConversionesProduccion.convertirPrdReproceso_PrdReprocesoTO(reproceso);
                            String mensaje = "T<html>El reproceso se ha modificado correctamente con la siguiente información:<br><br>"
                                    + "Periodo: <font size = 5>"
                                    + "</font>.<br> Motivo: <font size = 5>"
                                    + reprocesoResultado.getPrdReprocesoPK().getRepMotivo()
                                    + "</font>.<br> Número: <font size = 5>"
                                    + reprocesoResultado.getPrdReprocesoPK().getRepNumero()
                                    + "</font>.</html>"
                                    + reprocesoResultado.getPrdReprocesoPK().getRepNumero() + ","
                                    + reprocesoResultado.getPrdReprocesoPK().getRepPeriodo();
                            campos.put("mensaje", mensaje);
                            campos.put("prdReprocesoTO", reprocesoTO);
                        } else {
                            campos.put("mensaje", "FOcurrió un error al intentar guardar reproceso");
                        }
                    } else {
                        campos.put("mensaje", "FMotivo seleccionado no existe");
                    }
                } else {
                    campos.put("mensaje", "FPeriodo cerrado.");
                }
            } else {
                campos.put("mensaje", "FPeriodo no existe o se encuentra cerrado.");
            }
        } else {
            campos.put("mensaje", "FFecha superior ");
        }
        return campos;
    }

    @Override
    public String desmayorizarPrdReproceso(String empresa, String periodo, String motivo, String numero, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        PrdReproceso reproceso = prdReprocesoDao.obtenerPorId(PrdReproceso.class, new PrdReprocesoPK(empresa, periodo, motivo, numero));
        susSuceso = "UPDATE";
        susTabla = "produccion.prd_reproceso";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        sisSuceso.setSusClave(
                reproceso.getPrdReprocesoPK().getRepPeriodo() + " | "
                + reproceso.getPrdReprocesoPK().getRepMotivo() + " | "
                + reproceso.getPrdReprocesoPK().getRepNumero());

        sisSuceso.setSusDetalle("El reproceso:" + sisSuceso.getSusClave() + " , se ha desmayorizado correctamente.");
        if ((mensaje = periodoService.validarPeriodo(reproceso.getRepFecha(), empresa)) == null) {
            prdReprocesoDao.actualizarPendienteSql(new PrdReprocesoPK(empresa, periodo, motivo, numero), true);
            mensaje = "TEl reproceso: " + numero + ", se ha desmayorizado correctamente.";
            sucesoDao.insertar(sisSuceso);
        }
        return mensaje;
    }

    @Override
    public String anularPrdReproceso(String empresa, String periodo, String motivo, String numero, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        susSuceso = "UPDATE";
        susTabla = "produccion.prd_reproceso";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        sisSuceso.setSusClave(periodo + " | " + motivo + " | " + numero);
        sisSuceso.setSusDetalle("El reproceso:" + sisSuceso.getSusClave() + " , se ha anulado correctamente.");
        prdReprocesoDao.anularRestaurarSql(new PrdReprocesoPK(empresa, periodo, motivo, numero), true);
        mensaje = "TEl reproceso: " + numero + ", se ha anulado correctamente.";
        sucesoDao.insertar(sisSuceso);
        return mensaje;
    }

    @Override
    public String restaurarPrdReproceso(String empresa, String periodo, String motivo, String numero, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        PrdReproceso reproceso = prdReprocesoDao.obtenerPorId(PrdReproceso.class, new PrdReprocesoPK(empresa, periodo, motivo, numero));
        susSuceso = "UPDATE";
        susTabla = "produccion.prd_reproceso";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        sisSuceso.setSusClave(
                reproceso.getPrdReprocesoPK().getRepPeriodo() + " | "
                + reproceso.getPrdReprocesoPK().getRepMotivo() + " | "
                + reproceso.getPrdReprocesoPK().getRepNumero());

        sisSuceso.setSusDetalle("El reproceso:" + sisSuceso.getSusClave() + " , se ha restaurado correctamente.");
        if ((mensaje = periodoService.validarPeriodo(reproceso.getRepFecha(), empresa)) == null) {
            prdReprocesoDao.anularRestaurarSql(new PrdReprocesoPK(empresa, periodo, motivo, numero), false);
            mensaje = "TEl reproceso: " + numero + ", se ha restaurado correctamente.";
            sucesoDao.insertar(sisSuceso);
        }
        return mensaje;
    }

}
