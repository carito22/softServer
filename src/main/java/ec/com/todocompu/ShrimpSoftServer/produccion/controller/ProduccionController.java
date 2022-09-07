package ec.com.todocompu.ShrimpSoftServer.produccion.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.todocompu.ShrimpSoftServer.produccion.report.ReporteConsumoPorFecha;
import ec.com.todocompu.ShrimpSoftServer.produccion.report.ReporteConsumoPorPiscina;
import ec.com.todocompu.ShrimpSoftServer.produccion.report.ReporteCostoDetalladoCorrida;
import ec.com.todocompu.ShrimpSoftServer.produccion.report.ReporteCostoDetalleFecha;
import ec.com.todocompu.ShrimpSoftServer.produccion.report.ReporteListadoFunAnalisisPeso;
import ec.com.todocompu.ShrimpSoftServer.produccion.report.ReporteListadoGrameaje;
import ec.com.todocompu.ShrimpSoftServer.produccion.report.ReporteProduccionService;
import ec.com.todocompu.ShrimpSoftServer.produccion.report.ReporteResumenPesca;
import ec.com.todocompu.ShrimpSoftServer.produccion.report.ReporteResumenSiembra;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.CorridaDetalleService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.CorridaService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.GrameajeService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.LiquidacionDetalleService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.LiquidacionMotivoService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.LiquidacionService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.PiscinaService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.PrdProductoService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.PreLiquidacionMotivoService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.PreLiquidacionService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.PresupuestoPescaMotivoService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.PresupuestoPescaService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.ProyeccionService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.SectorService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.SobrevivenciaService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.TallaService;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.RetornoTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.ListaLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.ListaPreLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.ListadoGrameaje;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.MultiplePiscinaCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PiscinaGrameajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdComboCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdComboPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdContabilizarCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCostoDetalleFechaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunAnalisisVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunCostosPorFechaSimpleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdGrameajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionMotivoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionProductoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionTallaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionTallaTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaConsultaGrameajePromedioPorPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaCostosDetalleCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaDetalleLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaFunAnalisisPesosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaPiscinaComboTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorConHectareajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSobrevivenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListadoGrameajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPiscinaGrupoRelacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdProyeccionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenFinancieroTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenPescaSiembraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdSobrevivenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdUtilidadDiariaCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorridaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorridaPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdGrameaje;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscina;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPesca;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdProducto;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdTalla;
import ec.com.todocompu.ShrimpSoftUtils.produccion.report.ReporteLiquidacionPesca;
import ec.com.todocompu.ShrimpSoftUtils.produccion.report.ReportePrdFunCostosPorFechaSimpleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.report.ReporteResumenFinanciero;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/todocompuWS/produccionController")
public class ProduccionController {

    @Autowired
    private CorridaService corridaService;

    @Autowired
    private CorridaDetalleService corridaDetalleService;

    @Autowired
    private GrameajeService grameajeService;

    @Autowired
    private LiquidacionDetalleService liquidacionDetalleService;

    @Autowired
    private LiquidacionMotivoService liquidacionMotivoService;

    @Autowired
    private PresupuestoPescaMotivoService presupuestoPescaMotivoService;

    @Autowired
    private PreLiquidacionMotivoService preLiquidacionMotivoService;

    @Autowired
    private PrdProductoService prdProductoService;

    @Autowired
    private LiquidacionService liquidacionService;

    @Autowired
    private PreLiquidacionService preLiquidacionService;

    @Autowired
    private TallaService tallaService;

    @Autowired
    private PiscinaService piscinaService;

    @Autowired
    private SectorService sectorService;

    @Autowired
    private SobrevivenciaService sobrevivenciaService;

    @Autowired
    private PresupuestoPescaService presupuestoPescaService;

    @Autowired
    private ProyeccionService proyeccionService;

    @Autowired
    private EnviarCorreoService envioCorreoService;

    @Autowired
    private ReporteProduccionService reporteProduccionService;

    @RequestMapping("/getListaPrdProyeccion")
    public List<PrdProyeccionTO> getListaPrdProyeccion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        Date fecha = UtilsJSON.jsonToObjeto(Date.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proyeccionService.getListaPrdProyeccion(empresa, sector, fecha);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrdLiquidacion")
    public PrdLiquidacion getPrdLiquidacion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionPK liquidacionPK = UtilsJSON.jsonToObjeto(PrdLiquidacionPK.class, map.get("liquidacionPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return liquidacionService.getPrdLiquidacion(liquidacionPK);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPrdLiquidacion")
    public List<PrdLiquidacion> getListaPrdLiquidacion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return liquidacionService.getListaPrdLiquidacion(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/anularRestaurarPrdLiquidacion")
    public String anularRestaurarPrdLiquidacion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionPK liquidacionPK = UtilsJSON.jsonToObjeto(PrdLiquidacionPK.class, map.get("liquidacionPK"));
        boolean anularRestaurar = UtilsJSON.jsonToObjeto(boolean.class, map.get("anularRestaurar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return liquidacionService.anularRestaurarPrdLiquidacion(liquidacionPK, anularRestaurar, false, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/desmayorizarPrdLiquidacion")
    public String desmayorizarPrdLiquidacion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionPK liquidacionPK = UtilsJSON.jsonToObjeto(PrdLiquidacionPK.class, map.get("liquidacionPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return liquidacionService.desmayorizarPrdLiquidacion(liquidacionPK, false, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarModificarPrdLiquidacion")
    public MensajeTO insertarModificarPrdLiquidacion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacion prdLiquidacion = UtilsJSON.jsonToObjeto(PrdLiquidacion.class, map.get("prdLiquidacion"));
        List<PrdLiquidacionDetalle> listaPrdLiquidacionDetalle = UtilsJSON.jsonToList(PrdLiquidacionDetalle.class,
                map.get("listaPrdLiquidacionDetalle"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return liquidacionService.insertarModificarPrdLiquidacion(prdLiquidacion, listaPrdLiquidacionDetalle, null, false,
                    sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrdPreLiquidacion")
    public PrdPreLiquidacion getPrdPreLiquidacion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPreLiquidacionPK liquidacionPK = UtilsJSON.jsonToObjeto(PrdPreLiquidacionPK.class, map.get("liquidacionPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return preLiquidacionService.getPrdPreLiquidacion(liquidacionPK);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPrdPreLiquidacion")
    public List<PrdPreLiquidacion> getListaPrdPreLiquidacion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return preLiquidacionService.getListaPrdPreLiquidacion(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/anularRestaurarPrdPreLiquidacion")
    public String anularRestaurarPrdPreLiquidacion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPreLiquidacionPK liquidacionPK = UtilsJSON.jsonToObjeto(PrdPreLiquidacionPK.class, map.get("liquidacionPK"));
        boolean anularRestaurar = UtilsJSON.jsonToObjeto(boolean.class, map.get("anularRestaurar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return preLiquidacionService.anularRestaurarPrdPreLiquidacion(liquidacionPK, anularRestaurar);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/desmayorizarPrdPreLiquidacion")
    public String desmayorizarPrdPreLiquidacion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPreLiquidacionPK liquidacionPK = UtilsJSON.jsonToObjeto(PrdPreLiquidacionPK.class, map.get("liquidacionPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return preLiquidacionService.desmayorizarPrdPreLiquidacion(liquidacionPK);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarModificarPrdPreLiquidacion")
    public MensajeTO insertarModificarPrdPreLiquidacion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPreLiquidacion liquidacion = UtilsJSON.jsonToObjeto(PrdPreLiquidacion.class, map.get("liquidacion"));
        List<PrdPreLiquidacionDetalle> listaLiquidacionDetalle = UtilsJSON.jsonToList(PrdPreLiquidacionDetalle.class,
                map.get("listaLiquidacionDetalle"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return preLiquidacionService.insertarModificarPrdPreLiquidacion(liquidacion, listaLiquidacionDetalle,
                    sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPrdConsultaPreLiquidacion")
    public List<ListaPreLiquidacionTO> getListaPrdConsultaPreLiquidacion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return preLiquidacionService.getListaPrdConsultaPreLiquidacion(empresa, sector, piscina, busqueda);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrdPresupuestoPesca")
    public PrdPresupuestoPesca getPrdPresupuestoPesca(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPresupuestoPescaPK presupuestoPescaPK = UtilsJSON.jsonToObjeto(PrdPresupuestoPescaPK.class,
                map.get("presupuestoPescaPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return presupuestoPescaService.getPrdPresupuestoPesca(presupuestoPescaPK);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPrdPresupuestoPesca")
    public List<PrdPresupuestoPesca> getListaPrdPresupuestoPesca(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return presupuestoPescaService.getListaPrdPresupuestoPesca(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/anularRestaurarPresupuestoPesca")
    public String anularRestaurarPresupuestoPesca(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPresupuestoPescaPK presupuestoPescaPK = UtilsJSON.jsonToObjeto(PrdPresupuestoPescaPK.class,
                map.get("presupuestoPescaPK"));
        boolean anularRestaurar = UtilsJSON.jsonToObjeto(boolean.class, map.get("anularRestaurar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return presupuestoPescaService.anularRestaurarPresupuestoPesca(presupuestoPescaPK, anularRestaurar);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/desmayorizarPresupuestoPesca")
    public String desmayorizarPresupuestoPesca(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPresupuestoPescaPK presupuestoPescaPK = UtilsJSON.jsonToObjeto(PrdPresupuestoPescaPK.class,
                map.get("presupuestoPescaPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return presupuestoPescaService.desmayorizarPresupuestoPesca(presupuestoPescaPK);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarModificarPrdPresupuestoPesca")
    public MensajeTO insertarModificarPrdPresupuestoPesca(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPresupuestoPesca prdPresupuestoPesca = UtilsJSON.jsonToObjeto(PrdPresupuestoPesca.class,
                map.get("prdPresupuestoPesca"));
        List<PrdPresupuestoPescaDetalle> listaPrdPresupuestoPescaDetalles = UtilsJSON
                .jsonToList(PrdPresupuestoPescaDetalle.class, map.get("listaPrdPresupuestoPescaDetalles"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return presupuestoPescaService.insertarModificarPrdPresupuestoPesca(prdPresupuestoPesca,
                    listaPrdPresupuestoPescaDetalles, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarPrdSectorTO")
    public boolean insertarPrdSectorTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdSectorTO prdSectorTO = UtilsJSON.jsonToObjeto(PrdSectorTO.class, map.get("prdSectorTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return sectorService.insertarPrdSector(prdSectorTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/modificarPrdSectorTO")
    public boolean modificarPrdSectorTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdSectorTO prdSectorTO = UtilsJSON.jsonToObjeto(PrdSectorTO.class, map.get("prdSectorTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return sectorService.modificarPrdSector(prdSectorTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/eliminarPrdSectorTO")
    public boolean eliminarPrdSectorTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdSectorTO prdSectorTO = UtilsJSON.jsonToObjeto(PrdSectorTO.class, map.get("prdSectorTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return sectorService.eliminarPrdSector(prdSectorTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/insertarPrdLiquidacionMotivo")
    public String insertarPrdLiquidacionMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionMotivo prdLiquidacionMotivo = UtilsJSON.jsonToObjeto(PrdLiquidacionMotivo.class,
                map.get("prdLiquidacionMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return liquidacionMotivoService.insertarPrdLiquidacionMotivo(prdLiquidacionMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarPrdLiquidacionMotivo")
    public String modificarPrdLiquidacionMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionMotivo prdLiquidacionMotivo = UtilsJSON.jsonToObjeto(PrdLiquidacionMotivo.class,
                map.get("prdLiquidacionMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return liquidacionMotivoService.modificarPrdLiquidacionMotivo(prdLiquidacionMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarPrdLiquidacionMotivo")
    public String eliminarPrdLiquidacionMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionMotivo prdLiquidacionMotivo = UtilsJSON.jsonToObjeto(PrdLiquidacionMotivo.class,
                map.get("prdLiquidacionMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return liquidacionMotivoService.eliminarPrdLiquidacionMotivo(prdLiquidacionMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPrdLiquidacionMotivo")
    public List<PrdLiquidacionMotivo> getListaPrdLiquidacionMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return liquidacionMotivoService.getListaPrdLiquidacionMotivo(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarPrdPresupuestoPescaMotivo")
    public String insertarPrdPresupuestoPescaMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo = UtilsJSON.jsonToObjeto(PrdPresupuestoPescaMotivo.class,
                map.get("prdPresupuestoPescaMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return presupuestoPescaMotivoService.insertarPrdPresupuestoPescaMotivo(prdPresupuestoPescaMotivo,
                    sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarPrdPresupuestoPescaMotivo")
    public String modificarPrdPresupuestoPescaMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo = UtilsJSON.jsonToObjeto(PrdPresupuestoPescaMotivo.class,
                map.get("prdPresupuestoPescaMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return presupuestoPescaMotivoService.modificarPrdPresupuestoPescaMotivo(prdPresupuestoPescaMotivo,
                    sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarPrdPresupuestoPescaMotivo")
    public String eliminarPrdPresupuestoPescaMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo = UtilsJSON.jsonToObjeto(PrdPresupuestoPescaMotivo.class,
                map.get("prdPresupuestoPescaMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return presupuestoPescaMotivoService.eliminarPrdPresupuestoPescaMotivo(prdPresupuestoPescaMotivo,
                    sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPrdPresupuestoPescaMotivo")
    public List<PrdPresupuestoPescaMotivo> getListaPrdPresupuestoPescaMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return presupuestoPescaMotivoService.getListaPrdPresupuestoPescaMotivo(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarPrdPreLiquidacionMotivo")
    public String insertarPrdPreLiquidacionMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPreLiquidacionMotivo prdPreLiquidacionMotivo = UtilsJSON.jsonToObjeto(PrdPreLiquidacionMotivo.class,
                map.get("prdPreLiquidacionMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return preLiquidacionMotivoService.insertarPrdPreLiquidacionMotivo(prdPreLiquidacionMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarPrdPreLiquidacionMotivo")
    public String modificarPrdPreLiquidacionMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPreLiquidacionMotivo prdPreLiquidacionMotivo = UtilsJSON.jsonToObjeto(PrdPreLiquidacionMotivo.class,
                map.get("prdPreLiquidacionMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return preLiquidacionMotivoService.modificarPrdPreLiquidacionMotivo(prdPreLiquidacionMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarPrdPreLiquidacionMotivo")
    public String eliminarPrdPreLiquidacionMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPreLiquidacionMotivo prdPreLiquidacionMotivo = UtilsJSON.jsonToObjeto(PrdPreLiquidacionMotivo.class,
                map.get("prdPreLiquidacionMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return preLiquidacionMotivoService.eliminarPrdPreLiquidacionMotivo(prdPreLiquidacionMotivo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPrdPreLiquidacionMotivo")
    public List<PrdPreLiquidacionMotivo> getListaPrdPreLiquidacionMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return preLiquidacionMotivoService.getListaPrdPreLiquidacionMotivo(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarPrdLiquidacionProducto")
    public String insertarPrdLiquidacionProducto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdProducto prdLiquidacionProducto = UtilsJSON.jsonToObjeto(PrdProducto.class,
                map.get("prdLiquidacionProducto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prdProductoService.insertarPrdLiquidacionProducto(prdLiquidacionProducto, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarPrdLiquidacionProducto")
    public String modificarPrdLiquidacionProducto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdProducto prdLiquidacionProducto = UtilsJSON.jsonToObjeto(PrdProducto.class,
                map.get("prdLiquidacionProducto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prdProductoService.modificarPrdLiquidacionProducto(prdLiquidacionProducto, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarPrdLiquidacionProducto")
    public String eliminarPrdLiquidacionProducto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdProducto prdLiquidacionProducto = UtilsJSON.jsonToObjeto(PrdProducto.class,
                map.get("prdLiquidacionProducto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prdProductoService.eliminarPrdLiquidacionProducto(prdLiquidacionProducto, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPrdLiquidacionProducto")
    public List<PrdProducto> getListaPrdLiquidacionProducto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prdProductoService.getListaPrdLiquidacionProducto(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerFechaGrameajeSuperior")
    public String obtenerFechaGrameajeSuperior(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String numPiscina = UtilsJSON.jsonToObjeto(String.class, map.get("numPiscina"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return grameajeService.obtenerFechaGrameajeSuperior(empresa, sector, numPiscina);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerPorEmpresaSectorPiscina")
    public PrdPiscina obtenerPorEmpresaSectorPiscina(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String numPiscina = UtilsJSON.jsonToObjeto(String.class, map.get("numPiscina"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return piscinaService.obtenerPorEmpresaSectorPiscina(empresa, sector, numPiscina);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaSectorTO")
    public List<PrdListaSectorTO> getListaSectorTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean activo = UtilsJSON.jsonToObjeto(boolean.class, map.get("activo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return sectorService.getListaSectorTO(empresa, activo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarPrdSector")
    public String insertarPrdSector(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdSector prdSector = UtilsJSON.jsonToObjeto(PrdSector.class, map.get("prdSector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return sectorService.insertarPrdSector(prdSector, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarPrdSector")
    public String modificarPrdSector(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdSector prdSector = UtilsJSON.jsonToObjeto(PrdSector.class, map.get("prdSector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return sectorService.modificarPrdSector(prdSector, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarPrdSector")
    public String eliminarPrdSector(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdSector prdSector = UtilsJSON.jsonToObjeto(PrdSector.class, map.get("prdSector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return sectorService.eliminarPrdSector(prdSector, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCorridaDetalleOrigenPorCorrida")
    public List<PrdCorridaDetalle> getCorridaDetalleOrigenPorCorrida(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String corrida = UtilsJSON.jsonToObjeto(String.class, map.get("corrida"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaDetalleService.getCorridaDetalleOrigenPorCorrida(empresa, sector, piscina, corrida);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCorridaDetalleDestinoPorCorrida")
    public List<PrdCorridaDetalle> getCorridaDetalleDestinoPorCorrida(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String corrida = UtilsJSON.jsonToObjeto(String.class, map.get("corrida"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaDetalleService.getCorridaDetalleDestinoPorCorrida(empresa, sector, piscina, corrida);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarPrdCorrida")
    public String insertarPrdCorrida(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdCorrida prdCorrida = UtilsJSON.jsonToObjeto(PrdCorrida.class, map.get("prdCorrida"));
        List<PrdCorridaDetalle> corridaDetalleList = UtilsJSON.jsonToList(PrdCorridaDetalle.class,
                map.get("corridaDetalleList"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            prdCorrida.setPrdCorridaDetalleList(corridaDetalleList);
            return corridaService.insertarPrdCorrida(prdCorrida, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarPrdCorrida")
    public String modificarPrdCorrida(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdCorrida prdCorrida = UtilsJSON.jsonToObjeto(PrdCorrida.class, map.get("prdCorrida"));
        List<PrdCorridaDetalle> corridaDetalleList = UtilsJSON.jsonToList(PrdCorridaDetalle.class,
                map.get("corridaDetalleList"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            prdCorrida.setPrdCorridaDetalleList(corridaDetalleList);
            return corridaService.modificarPrdCorrida(prdCorrida, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarPrdCorrida")
    public String eliminarPrdCorrida(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdCorrida prdCorrida = UtilsJSON.jsonToObjeto(PrdCorrida.class, map.get("prdCorrida"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.eliminarPrdCorrida(prdCorrida, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/cambiarCodigoPrdCorrida")
    public String cambiarCodigoPrdCorrida(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdCorridaPK prdCorridaPK = UtilsJSON.jsonToObjeto(PrdCorridaPK.class, map.get("prdCorridaPK"));
        String nuevoCodigoPrdCorrida = UtilsJSON.jsonToObjeto(String.class, map.get("nuevoCodigoPrdCorrida"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.cambiarCodigoPrdCorrida(prdCorridaPK, nuevoCodigoPrdCorrida);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerPorEmpresaSector")
    public PrdSector obtenerPorEmpresaSector(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return sectorService.obtenerPorEmpresaSector(empresa, sector);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaSectorPorEmpresa")
    public List<PrdSector> getListaSectorPorEmpresa(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean activo = UtilsJSON.jsonToObjeto(boolean.class, map.get("activo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return sectorService.getListaSectorPorEmpresa(empresa, activo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPiscinasPorEmpresaSector")
    public List<PrdPiscina> getListaPiscinasPorEmpresaSector(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return piscinaService.getListaSectorPorEmpresa(empresa, sector);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarPrdPiscinaTO")
    public boolean insertarPrdPiscinaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPiscinaTO prdPiscinaTO = UtilsJSON.jsonToObjeto(PrdPiscinaTO.class, map.get("prdPiscinaTO"));
        List<PrdPiscinaGrupoRelacionTO> listado = UtilsJSON.jsonToList(PrdPiscinaGrupoRelacionTO.class, map.get("listadoPrdPiscinaGrupoRelacionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return piscinaService.insertarPrdPiscina(prdPiscinaTO, listado, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/modificarPrdPiscinaTO")
    public boolean modificarPrdPiscinaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPiscinaTO prdPiscinaTO = UtilsJSON.jsonToObjeto(PrdPiscinaTO.class, map.get("prdPiscinaTO"));
        List<PrdPiscinaGrupoRelacionTO> listado = UtilsJSON.jsonToList(PrdPiscinaGrupoRelacionTO.class, map.get("listadoPrdPiscinaGrupoRelacionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return piscinaService.modificarPrdPiscina(prdPiscinaTO, listado, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/eliminarPrdPiscinaTO")
    public boolean eliminarPrdPiscinaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPiscinaTO prdPiscinaTO = UtilsJSON.jsonToObjeto(PrdPiscinaTO.class, map.get("prdPiscinaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return piscinaService.eliminarPrdPiscina(prdPiscinaTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/insertarPrdPiscina")
    public String insertarPrdPiscina(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPiscina prdPiscina = UtilsJSON.jsonToObjeto(PrdPiscina.class, map.get("prdPiscina"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return piscinaService.insertarPrdPiscina(prdPiscina, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarPrdPiscina")
    public String modificarPrdPiscina(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPiscina prdPiscina = UtilsJSON.jsonToObjeto(PrdPiscina.class, map.get("prdPiscina"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return piscinaService.modificarPrdPiscina(prdPiscina, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarPrdPiscina")
    public String eliminarPrdPiscina(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPiscina prdPiscina = UtilsJSON.jsonToObjeto(PrdPiscina.class, map.get("prdPiscina"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return piscinaService.eliminarPrdPiscina(prdPiscina, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPiscinaTO")
    public List<PrdListaPiscinaTO> getListaPiscinaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return piscinaService.getListaPiscinaTO(empresa.trim(), sector.trim());
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPiscinaPor_Empresa_Sector_Activa")
    public List<PrdPiscina> getListaPiscinaPor_Empresa_Sector_Activa(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        boolean activa = UtilsJSON.jsonToObjeto(boolean.class, map.get("activa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return piscinaService.getListaPiscinaPor_Empresa_Sector_Activa(empresa.trim(), sector.trim(), activa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPiscinasGrameaje")
    public List<PiscinaGrameajeTO> getListaPiscinasGrameaje(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return piscinaService.getListaPiscinasGrameaje(empresa.trim(), sector.trim(), fecha);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaGrameajes")
    public List<PrdGrameaje> getListaGrameajes(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return grameajeService.getPrdListadoGrameaje(empresa.trim(), sector.trim(), fecha);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPiscinaActivoTO")
    public List<PrdListaPiscinaComboTO> getListaPiscinaActivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean activo = UtilsJSON.jsonToObjeto(boolean.class, map.get("activo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return piscinaService.getListaPiscinaTO(empresa, activo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPiscinaTOBusqueda")
    public List<PrdListaPiscinaTO> getListaPiscinaTOBusqueda(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return piscinaService.getListaPiscinaTOBusqueda(empresa, sector, fecha);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getComboPrdPiscinaTO")
    public List<PrdComboPiscinaTO> getComboPrdPiscinaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return piscinaService.getComboPiscinaTO(empresa, sector);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaCorridasPorEmpresaSectorPiscina")
    public List<PrdCorrida> getListaCorridasPorEmpresaSectorPiscina(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getListaCorridasPorEmpresaSectorPiscina(empresa, sector, piscina);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCorridaPorNumero")
    public List<PrdCorrida> getCorridaPorNumero(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getCorridaPorNumero(empresa, sector, piscina, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerCorrida")
    public List<PrdListaCorridaTO> obtenerCorrida(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        Date fecha = UtilsJSON.jsonToObjeto(Date.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.obtenerCorrida(empresa, sector, piscina, fecha);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaCorridaTO")
    public List<PrdListaCorridaTO> getListaCorridaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getListaCorridaTO(empresa, sector, piscina, null, null, null);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaCorridaTOCorrida")
    public List<PrdListaCorridaTO> getListaCorridaTOCorrida(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String corrida = UtilsJSON.jsonToObjeto(String.class, map.get("corrida"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getListaCorridaTO(empresa, sector, piscina, corrida);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrdFunAnalisisVentasTO")
    public PrdFunAnalisisVentasTO getPrdFunAnalisisVentasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getPrdFunAnalisisVentasTO(empresa, sector, piscina, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrdListaCostosDetalleCorridaTO")
    public List<PrdListaCostosDetalleCorridaTO> getPrdListaCostosDetalleCorridaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String corrida = UtilsJSON.jsonToObjeto(String.class, map.get("corrida"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String agrupadoPor = UtilsJSON.jsonToObjeto(String.class, map.get("agrupadoPor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getPrdListaCostosDetalleCorridaTO(empresa, sector, piscina, corrida, desde, hasta,
                    agrupadoPor);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getComboPrdCorridaTO")
    public List<PrdComboCorridaTO> getComboPrdCorridaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getComboPrdCorridaTO(empresa, sector, piscina);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getComboPrdCorridaFiltradoTO")
    public List<PrdComboCorridaTO> getComboPrdCorridaFiltradoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        Date fecha = UtilsJSON.jsonToObjeto(Date.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getComboPrdCorridaFiltradoTO(empresa, sector, piscina, fecha);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaResumenCorridaTO")
    public List<PrdResumenCorridaTO> getListaResumenCorridaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String tipoResumen = UtilsJSON.jsonToObjeto(String.class, map.get("tipoResumen"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getListaResumenCorridaTO(empresa, sector, desde, hasta, tipoResumen);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaContabilizarCorridaTO")
    public List<PrdContabilizarCorridaTO> getListaContabilizarCorridaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getListaContabilizarCorridaTO(empresa, desde, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/consultarFechaMaxMin")
    public String consultarFechaMaxMin(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String tipoResumen = UtilsJSON.jsonToObjeto(String.class, map.get("tipoResumen"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.consultarFechaMaxMin(empresa, tipoResumen);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarPrdSobrevivencia")
    public boolean insertarPrdSobrevivencia(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdSobrevivenciaTO prdSobrevivenciaTO = UtilsJSON.jsonToObjeto(PrdSobrevivenciaTO.class,
                map.get("prdSobrevivenciaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return sobrevivenciaService.insertarPrdSobrevivencia(prdSobrevivenciaTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/modificarPrdSobrevivencia")
    public boolean modificarPrdSobrevivencia(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdSobrevivenciaTO prdSobrevivenciaTO = UtilsJSON.jsonToObjeto(PrdSobrevivenciaTO.class,
                map.get("prdSobrevivenciaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return sobrevivenciaService.modificarPrdSobrevivencia(prdSobrevivenciaTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/eliminarPrdSobrevivencia")
    public boolean eliminarPrdSobrevivencia(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdSobrevivenciaTO prdSobrevivenciaTO = UtilsJSON.jsonToObjeto(PrdSobrevivenciaTO.class,
                map.get("prdSobrevivenciaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return sobrevivenciaService.eliminarPrdSobrevivencia(prdSobrevivenciaTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/getListaSobrevivenciaTO")
    public List<PrdListaSobrevivenciaTO> getListaSobrevivenciaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return sobrevivenciaService.getListaSobrevivenciaTO(empresa, sector);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getIsFechaGrameajeSuperior")
    public boolean getIsFechaGrameajeSuperior(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String numPiscina = UtilsJSON.jsonToObjeto(String.class, map.get("numPiscina"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return grameajeService.getIsFechaGrameajeSuperior(empresa, sector, numPiscina, fechaDesde, fechaHasta,
                    fecha);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/insertarPrdGrameaje")
    public boolean insertarPrdGrameaje(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdGrameajeTO prdGrameajeTO = UtilsJSON.jsonToObjeto(PrdGrameajeTO.class, map.get("prdGrameajeTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return grameajeService.insertarPrdGrameaje(prdGrameajeTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/insertarGrameajeListado")
    public boolean insertarGrameajeListado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<PrdGrameaje> listaGrameajes = UtilsJSON.jsonToList(PrdGrameaje.class, map.get("listaGrameajes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return grameajeService.insertarGrameajeListado(listaGrameajes, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/eliminarPrdGrameaje")
    public String eliminarPrdGrameaje(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdGrameajeTO prdGrameajeTO = UtilsJSON.jsonToObjeto(PrdGrameajeTO.class, map.get("prdGrameajeTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return grameajeService.eliminarPrdGrameaje(prdGrameajeTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return "";
    }

    @RequestMapping("/eliminarGrameaje")
    public String eliminarGrameaje(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdGrameaje prdGrameaje = UtilsJSON.jsonToObjeto(PrdGrameaje.class, map.get("prdGrameaje"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return grameajeService.eliminarGrameaje(prdGrameaje, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return "";
    }

    @RequestMapping("/getPrdGrameaje")
    public PrdGrameaje getPrdGrameaje(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return grameajeService.getPrdGrameaje(empresa, sector, piscina);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrdListadoGrameajeTO")
    public List<PrdListadoGrameajeTO> getPrdListadoGrameajeTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return grameajeService.getPrdListadoGrameajeTO(empresa, sector, piscina, desde, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaGrameajesPorFechasCorrida")
    public List<ListadoGrameaje> getListaGrameajesPorFechasCorrida(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return grameajeService.getListaGrameajesPorFechasCorrida(empresa, sector, piscina, desde, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrdListadoCostoDetalleFechaTO")
    public List<PrdCostoDetalleFechaTO> getPrdListadoCostoDetalleFechaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String secCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("secCodigo"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String agrupadoPor = UtilsJSON.jsonToObjeto(String.class, map.get("agrupadoPor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getPrdListadoCostoDetalleFechaTO(empresa, secCodigo, desde, hasta, agrupadoPor);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarCorridaActivo")
    public RetornoTO modificarCorridaActivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String proceso = UtilsJSON.jsonToObjeto(String.class, map.get("proceso"));
        String agrupadoPor = UtilsJSON.jsonToObjeto(String.class, map.get("agrupadoPor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.modificarCorridaActivo(empresa, sector, hasta, proceso, agrupadoPor);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarCorridaActivoSeleccionMultiple")
    public RetornoTO modificarCorridaActivoSeleccionMultiple(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<MultiplePiscinaCorrida> multiplePiscinaCorrida = UtilsJSON.jsonToList(MultiplePiscinaCorrida.class,
                map.get("multiplePiscinaCorrida"));
        String proceso = UtilsJSON.jsonToObjeto(String.class, map.get("proceso"));
        String agrupadoPor = UtilsJSON.jsonToObjeto(String.class, map.get("agrupadoPor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.modificarCorridaActivoSeleccionMultiple(empresa, multiplePiscinaCorrida, proceso,
                    agrupadoPor);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrdCorridaTO")
    public PrdCorridaTO getPrdCorridaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getPrdCorridaTO(empresa, sector, piscina, fecha);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrdGrameajeTOAux")
    public PrdGrameajeTO getPrdGrameajeTOAux(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return grameajeService.getPrdGrameajeTO(empresa, sector, piscina, fecha);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getPrdGrameajeTO")
    public PrdGrameajeTO getPrdGrameajeTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return grameajeService.getPrdGrameajeTO(empresa, sector, piscina, desde, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCostoDetallePersonalizado")
    public List<MultiplePiscinaCorrida> getCostoDetallePersonalizado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getCostoDetallePersonalizado(empresa, sector, fecha);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCostoPorFechaProrrateado")
    public RetornoTO getCostoPorFechaProrrateado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        String agrupadoPor = UtilsJSON.jsonToObjeto(String.class, map.get("agrupadoPor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getCostoPorFechaProrrateado(empresa, codigoSector, fechaInicio, fechaFin, usuario,
                    agrupadoPor);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrdFunCostosPorFechaSimpleTO")
    public List<PrdFunCostosPorFechaSimpleTO> getPrdFunCostosPorFechaSimpleTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String infEmpresea = UtilsJSON.jsonToObjeto(String.class, map.get("infEmpresea"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getPrdFunCostosPorFechaSimpleTO(codigoSector, fechaInicio, fechaFin, infEmpresea);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCostoPorPiscinaSemanal")
    public RetornoTO getCostoPorPiscinaSemanal(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String numeroPiscina = UtilsJSON.jsonToObjeto(String.class, map.get("numeroPiscina"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        String agrupadoPor = UtilsJSON.jsonToObjeto(String.class, map.get("agrupadoPor"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getCostoPorPiscinaSemanal(empresa, codigoSector, numeroPiscina, fechaInicio, fechaFin,
                    usuario, agrupadoPor, periodo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCostosMensuales")
    public RetornoTO getCostosMensuales(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String codigoBodega = UtilsJSON.jsonToObjeto(String.class, map.get("codigoBodega"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        String agrupadoPor = UtilsJSON.jsonToObjeto(String.class, map.get("agrupadoPor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getCostosMensuales(empresa, codigoSector, codigoBodega, fechaInicio, fechaFin, usuario, agrupadoPor);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getConsumoPorFechaDesglosado")
    public RetornoTO getConsumoPorFechaDesglosado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getConsumoPorFechaDesglosado(empresa, codigoSector, fechaInicio, fechaFin, usuario);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrdConsumosFechaTO")
    public List<PrdConsumosTO> getPrdConsumosFechaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getPrdConsumosFechaTO(empresa, sector, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrdConsumosPiscinaTO")
    public List<PrdConsumosTO> getPrdConsumosPiscinaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getPrdConsumosPiscinaTO(empresa, sector, piscina, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getConsumoPorPiscinaPeriodo")
    public RetornoTO getConsumoPorPiscinaPeriodo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String numeroPiscina = UtilsJSON.jsonToObjeto(String.class, map.get("numeroPiscina"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getConsumoPorPiscinaPeriodo(empresa, codigoSector, numeroPiscina, fechaInicio,
                    fechaFin, periodo, usuario);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getUtilidadDiariaCorrida")
    public List<PrdUtilidadDiariaCorridaTO> getUtilidadDiariaCorrida(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String corrida = UtilsJSON.jsonToObjeto(String.class, map.get("corrida"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getUtilidadDiariaCorrida(empresa, sector, piscina, corrida);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getConsumosMensuales")
    public RetornoTO getConsumosMensuales(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String codigoBodega = UtilsJSON.jsonToObjeto(String.class, map.get("codigoBodega"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getConsumosMensuales(empresa, codigoSector, codigoBodega, fechaInicio, fechaFin, usuario);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaSectorConHectareaje")
    public List<PrdListaSectorConHectareajeTO> getListaSectorConHectareaje(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return sectorService.getListaSectorConHectareaje(empresa, fecha);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrdResumenFinancieroTO")
    public List<PrdResumenFinancieroTO> getPrdResumenFinancieroTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String secCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("secCodigo"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getPrdResumenFinancieroTO(empresa, secCodigo, desde, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getResumenPesca")
    public List<PrdResumenPescaSiembraTO> getResumenPesca(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getResumenPesca(empresa, codigoSector, fechaInicio, fechaFin, usuario, false);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getResumenSiembra")
    public List<PrdResumenPescaSiembraTO> getResumenSiembra(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getResumenSiembra(empresa, codigoSector, fechaInicio, fechaFin, usuario);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrdLiquidacionMotivoTO")
    public PrdLiquidacionMotivoTO getPrdLiquidacionMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        PrdLiquidacionMotivoTablaTO prdLiquidacionMotivoTablaTO = UtilsJSON
                .jsonToObjeto(PrdLiquidacionMotivoTablaTO.class, map.get("prdLiquidacionMotivoTablaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return liquidacionMotivoService.getPrdLiquidacionMotivoTO(empresa, prdLiquidacionMotivoTablaTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPrdLiquidacionMotivoTablaTO")
    public List<PrdLiquidacionMotivoTablaTO> getListaPrdLiquidacionMotivoTablaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return liquidacionMotivoService.getListaPrdLiquidacionMotivoTablaTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarPrdLiquidacionMotivoTO")
    public String insertarPrdLiquidacionMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionMotivoTO prdLiquidacionMotivoTO = UtilsJSON.jsonToObjeto(PrdLiquidacionMotivoTO.class,
                map.get("prdLiquidacionMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return liquidacionMotivoService.insertarPrdLiquidacionMotivoTO(prdLiquidacionMotivoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarPrdLiquidacionMotivoTO")
    public String modificarPrdLiquidacionMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionMotivoTO prdLiquidacionMotivoTO = UtilsJSON.jsonToObjeto(PrdLiquidacionMotivoTO.class,
                map.get("prdLiquidacionMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return liquidacionMotivoService.modificarPrdLiquidacionMotivoTO(prdLiquidacionMotivoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarPrdLiquidacionMotivoTO")
    public String eliminarPrdLiquidacionMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionMotivoTO prdLiquidacionMotivoTO = UtilsJSON.jsonToObjeto(PrdLiquidacionMotivoTO.class,
                map.get("prdLiquidacionMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return liquidacionMotivoService.eliminarPrdLiquidacionMotivoTO(prdLiquidacionMotivoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrdLiquidacionTallaTO")
    public PrdLiquidacionTallaTO getPrdLiquidacionTallaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        PrdLiquidacionTallaTablaTO prdLiquidacionTallaTablaTO = UtilsJSON.jsonToObjeto(PrdLiquidacionTallaTablaTO.class,
                map.get("prdLiquidacionMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tallaService.getPrdLiquidacionTallaTO(empresa, prdLiquidacionTallaTablaTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPrdLiquidacionTalla")
    public List<PrdTalla> getListaPrdLiquidacionTalla(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean presupuestoPesca = UtilsJSON.jsonToObjeto(boolean.class, map.get("presupuestoPesca"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tallaService.getListaPrdLiquidacionTalla(empresa, presupuestoPesca);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarPrdLiquidacionTalla")
    public String insertarPrdLiquidacionTalla(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdTalla prdLiquidacionTalla = UtilsJSON.jsonToObjeto(PrdTalla.class, map.get("prdLiquidacionTalla"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tallaService.insertarPrdLiquidacionTalla(prdLiquidacionTalla, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarPrdLiquidacionTalla")
    public String modificarPrdLiquidacionTalla(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdTalla prdLiquidacionTalla = UtilsJSON.jsonToObjeto(PrdTalla.class, map.get("prdLiquidacionTalla"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tallaService.modificarPrdLiquidacionTalla(prdLiquidacionTalla, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarPrdLiquidacionTalla")
    public String eliminarPrdLiquidacionTalla(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdTalla prdLiquidacionTalla = UtilsJSON.jsonToObjeto(PrdTalla.class, map.get("prdLiquidacionTalla"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tallaService.eliminarPrdLiquidacionTalla(prdLiquidacionTalla, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPrdLiquidacionTallaTablaTO")
    public List<PrdLiquidacionTallaTablaTO> getListaPrdLiquidacionTallaTablaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tallaService.getListaPrdLiquidacionTallaTablaTO(empresa, codigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarPrdLiquidacionTallaTO")
    public String insertarPrdLiquidacionTallaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionTallaTO prdLiquidacionTallaTO = UtilsJSON.jsonToObjeto(PrdLiquidacionTallaTO.class,
                map.get("prdLiquidacionTallaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tallaService.insertarPrdLiquidacionTallaTO(prdLiquidacionTallaTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarPrdLiquidacionTallaTO")
    public String modificarPrdLiquidacionTallaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionTallaTO prdLiquidacionTallaTO = UtilsJSON.jsonToObjeto(PrdLiquidacionTallaTO.class,
                map.get("prdLiquidacionTallaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tallaService.modificarPrdLiquidacionTallaTO(prdLiquidacionTallaTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarPrdLiquidacionTallaTO")
    public String eliminarPrdLiquidacionTallaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionTallaTO prdLiquidacionTallaTO = UtilsJSON.jsonToObjeto(PrdLiquidacionTallaTO.class,
                map.get("prdLiquidacionTallaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tallaService.eliminarPrdLiquidacionTallaTO(prdLiquidacionTallaTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrdLiquidacionProductoTO")
    public PrdLiquidacionProductoTO getPrdLiquidacionProductoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        PrdLiquidacionProductoTablaTO prdLiquidacionProductoTablaTO = UtilsJSON
                .jsonToObjeto(PrdLiquidacionProductoTablaTO.class, map.get("prdLiquidacionProductoTablaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prdProductoService.getPrdLiquidacionProductoTO(empresa, prdLiquidacionProductoTablaTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPrdLiquidacionProductoTablaTO")
    public List<PrdLiquidacionProductoTablaTO> getListaPrdLiquidacionProductoTablaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prdProductoService.getListaPrdLiquidacionProductoTablaTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPrdLiquidacionProductoTablaTOAux")
    public List<PrdLiquidacionProductoTablaTO> getListaPrdLiquidacionProductoTablaTOAux(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prdProductoService.getListaPrdLiquidacionProductoTablaTO(empresa, codigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarPrdLiquidacionProductoTO")
    public String insertarPrdLiquidacionProductoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionProductoTO prdLiquidacionProductoTO = UtilsJSON.jsonToObjeto(PrdLiquidacionProductoTO.class,
                map.get("prdLiquidacionProductoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prdProductoService.insertarPrdLiquidacionProductoTO(prdLiquidacionProductoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarPrdLiquidacionProductoTO")
    public String modificarPrdLiquidacionProductoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionProductoTO prdLiquidacionProductoTO = UtilsJSON.jsonToObjeto(PrdLiquidacionProductoTO.class,
                map.get("prdLiquidacionProductoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prdProductoService.modificarPrdLiquidacionProductoTO(prdLiquidacionProductoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarPrdLiquidacionProductoTO")
    public String eliminarPrdLiquidacionProductoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionProductoTO prdLiquidacionProductoTO = UtilsJSON.jsonToObjeto(PrdLiquidacionProductoTO.class,
                map.get("prdLiquidacionProductoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return prdProductoService.eliminarPrdLiquidacionProductoTO(prdLiquidacionProductoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarPrdLiquidacionTO")
    public String insertarPrdLiquidacionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionTO prdLiquidacionTO = UtilsJSON.jsonToObjeto(PrdLiquidacionTO.class, map.get("prdLiquidacionTO"));
        List<PrdLiquidacionDetalleTO> listaPrdLiquidacionDetalleTO = UtilsJSON.jsonToList(PrdLiquidacionDetalleTO.class,
                map.get("listaPrdLiquidacionDetalleTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return liquidacionService.insertarPrdLiquidacionTO(prdLiquidacionTO, listaPrdLiquidacionDetalleTO,
                    sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarPrdLiquidacionTO")
    public String modificarPrdLiquidacionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionTO prdLiquidacionTO = UtilsJSON.jsonToObjeto(PrdLiquidacionTO.class, map.get("prdLiquidacionTO"));
        List<PrdLiquidacionDetalleTO> listaPrdLiquidacionDetalleTO = UtilsJSON.jsonToList(PrdLiquidacionDetalleTO.class,
                map.get("listaPrdLiquidacionDetalleTO"));
        List<PrdLiquidacionDetalleTO> listaPrdLiquidacionEliminarDetalleTO = UtilsJSON
                .jsonToList(PrdLiquidacionDetalleTO.class, map.get("listaPrdLiquidacionEliminarDetalleTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return liquidacionService.modificarPrdLiquidacionTO(prdLiquidacionTO, listaPrdLiquidacionDetalleTO,
                    listaPrdLiquidacionEliminarDetalleTO, sisInfoTO);

        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaLiquidacionMotivoComboTO")
    public List<PrdLiquidacionMotivoComboTO> getListaLiquidacionMotivoComboTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean filtrarInactivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("filtrarInactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return liquidacionDetalleService.getListaLiquidacionMotivoComboTO(empresa, filtrarInactivos);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPrdConsultaLiquidacion")
    public List<ListaLiquidacionTO> getListaPrdConsultaLiquidacion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return liquidacionService.getListaPrdConsultaLiquidacion(empresa, sector, piscina, busqueda);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getEstadoCCCVT")
    public PrdEstadoCCCVT getEstadoCCCVT(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String motivoTipo = UtilsJSON.jsonToObjeto(String.class, map.get("motivoTipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return liquidacionService.getEstadoCCCVT(empresa, motivoTipo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrdLiquidacionCabeceraTO")
    public PrdLiquidacionCabeceraTO getPrdLiquidacionCabeceraTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroLiquidacion = UtilsJSON.jsonToObjeto(String.class, map.get("numeroLiquidacion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return liquidacionService.getPrdLiquidacionCabeceraTO(empresa, motivo, numeroLiquidacion);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getFunAnalisisPesos")
    public List<PrdListaFunAnalisisPesosTO> getFunAnalisisPesos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getFunAnalisisPesos(empresa, sector, fecha);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getFunFechaSemanas")
    public List<String> getFunFechaSemanas(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getFunFechaSemanas(empresa, sector, fecha);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPrdLiquidacionDetalleTO")
    public List<PrdListaDetalleLiquidacionTO> getListaPrdLiquidacionDetalleTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return liquidacionDetalleService.getListaPrdLiquidacionDetalleTO(empresa, motivo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaConsultaGrameajePromedioPorPiscinaTOs")
    public List<PrdListaConsultaGrameajePromedioPorPiscinaTO> getListaConsultaGrameajePromedioPorPiscinaTOs(
            @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String nomSector = UtilsJSON.jsonToObjeto(String.class, map.get("nombreSector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getListaConsultaGrameajePromedioPorPiscinaTOs(empresa, sector, nomSector);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getConsultaGrameajePromedioPorPiscina")
    public RetornoTO getConsultaGrameajePromedioPorPiscina(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String nombreSector = UtilsJSON.jsonToObjeto(String.class, map.get("nombreSector"));
        boolean sobrevivencia = false;
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getConsultaGrameajePromedioPorPiscina(empresa, sector, nombreSector, sobrevivencia, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoGramaje")
    public byte[] generarReporteListadoGramaje(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String codigoPiscina = UtilsJSON.jsonToObjeto(String.class, map.get("codigoPiscina"));
        String codigoCorrida = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCorrida"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<PrdListadoGrameajeTO> prdListadoGrameajeTO = UtilsJSON.jsonToList(PrdListadoGrameajeTO.class,
                map.get("prdListadoGrameajeTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReporteListadoGramaje(usuarioEmpresaReporteTO, codigoSector,
                    codigoPiscina, codigoCorrida, fechaDesde, fechaHasta, prdListadoGrameajeTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/generarReporteListadoFunAnalisisPesos")
    public byte[] generarReporteListadoFunAnalisisPesos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<PrdListaFunAnalisisPesosTO> prdListaFunAnalisisPesosTO = UtilsJSON
                .jsonToList(PrdListaFunAnalisisPesosTO.class, map.get("prdListaFunAnalisisPesosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReporteListadoFunAnalisisPesos(usuarioEmpresaReporteTO, codigoSector,
                    fechaHasta, prdListaFunAnalisisPesosTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/generarReporteConsumoFecha")
    public byte[] generarReporteConsumoFecha(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String secCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("secCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<PrdConsumosTO> prdConsumosTO = UtilsJSON.jsonToList(PrdConsumosTO.class, map.get("prdConsumosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReporteConsumoFecha(usuarioEmpresaReporteTO, secCodigo, fechaDesde,
                    fechaHasta, prdConsumosTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteUtilidadDiariaCorrida")
    public byte[] generarReporteUtilidadDiariaCorrida(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String corrida = UtilsJSON.jsonToObjeto(String.class, map.get("corrida"));
        List<PrdUtilidadDiariaCorridaTO> listUtilidad = UtilsJSON.jsonToList(PrdUtilidadDiariaCorridaTO.class, map.get("reporteUtilidadDiaria"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReporteUtilidadDiariaCorrida(usuarioEmpresaReporteTO, sector, piscina, corrida, listUtilidad);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConsumoPiscina")
    public byte[] generarReporteConsumoPiscina(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String codigoPiscina = UtilsJSON.jsonToObjeto(String.class, map.get("codigoPiscina"));
        String numeroCorrida = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCorrida"));
        String hectareas = UtilsJSON.jsonToObjeto(String.class, map.get("hectareas"));
        Integer numLarvas = UtilsJSON.jsonToObjeto(Integer.class, map.get("numLarvas"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<PrdConsumosTO> prdConsumosTO = UtilsJSON.jsonToList(PrdConsumosTO.class, map.get("prdConsumosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReporteConsumoPiscina(usuarioEmpresaReporteTO, codigoSector,
                    codigoPiscina, numeroCorrida, hectareas, numLarvas, fechaDesde, fechaHasta, prdConsumosTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteEconomicoPorFechas")
    public byte[] generarReporteEconomicoPorFechas(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<PrdCostoDetalleFechaTO> listaPrdCostoDetalleFechaTO = UtilsJSON.jsonToList(PrdCostoDetalleFechaTO.class,
                map.get("listaPrdCostoDetalleFechaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReporteEconomicoPorFechas(usuarioEmpresaReporteTO, fechaDesde,
                    fechaHasta, listaPrdCostoDetalleFechaTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteEconomicoPorPiscinas")
    public byte[] generarReporteEconomicoPorPiscinas(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String codigoPiscina = UtilsJSON.jsonToObjeto(String.class, map.get("codigoPiscina"));
        String codigoCorrida = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCorrida"));
        String hectareas = UtilsJSON.jsonToObjeto(String.class, map.get("hectareas"));
        Integer numLarvas = UtilsJSON.jsonToObjeto(Integer.class, map.get("numLarvas"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<PrdListaCostosDetalleCorridaTO> prdListaCostosDetalleCorridaTO = UtilsJSON
                .jsonToList(PrdListaCostosDetalleCorridaTO.class, map.get("prdListaCostosDetalleCorridaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReporteEconomicoPorPiscinas(usuarioEmpresaReporteTO, codigoSector,
                    codigoPiscina, codigoCorrida, hectareas, numLarvas, fechaDesde, fechaHasta,
                    prdListaCostosDetalleCorridaTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteResumenSiembraAux")
    public byte[] generarReporteResumenSiembraAux(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<PrdResumenCorridaTO> listaPrdListaResumenCorridaTO = UtilsJSON.jsonToList(PrdResumenCorridaTO.class,
                map.get("listaPrdListaResumenCorridaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReporteResumenSiembra(usuarioEmpresaReporteTO,
                    listaPrdListaResumenCorridaTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarResumenPesca")
    public byte[] generarResumenPesca(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<PrdResumenCorridaTO> listaPrdListaResumenCorridaTO = UtilsJSON.jsonToList(PrdResumenCorridaTO.class,
                map.get("listaPrdListaResumenCorridaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReporteResumenPesca(usuarioEmpresaReporteTO,
                    listaPrdListaResumenCorridaTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteResumenEconomicoSiembra")
    public byte[] generarReporteResumenEconomicoSiembra(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<PrdResumenPescaSiembraTO> datos = UtilsJSON.jsonToList(PrdResumenPescaSiembraTO.class, map.get("datos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReporteResumenEconomicoSiembra(usuarioEmpresaReporteTO, datos);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteResumenEconomicoPesca")
    public byte[] generarReporteResumenEconomicoPesca(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<PrdResumenPescaSiembraTO> datos = UtilsJSON.jsonToList(PrdResumenPescaSiembraTO.class, map.get("datos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReporteResumenEconomicoPesca(usuarioEmpresaReporteTO, datos);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReportePrdResumenCorrida")
    public byte[] generarReportePrdResumenCorrida(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<PrdResumenCorridaTO> prdResumenCorrida = UtilsJSON.jsonToList(PrdResumenCorridaTO.class,
                map.get("prdResumenCorrida"));
        String tituloReporte = UtilsJSON.jsonToObjeto(String.class, map.get("tituloReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReportePrdResumenCorrida(usuarioEmpresaReporteTO, prdResumenCorrida,
                    tituloReporte);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCostoDetalladoCorrida")
    public byte[] generarReporteCostoDetalladoCorrida(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteCostoDetalladoCorrida> reporteCostoDetalladoCorrida = UtilsJSON
                .jsonToList(ReporteCostoDetalladoCorrida.class, map.get("reporteCostoDetalladoCorrida"));
        String tituloReporte = UtilsJSON.jsonToObjeto(String.class, map.get("tituloReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReporteCostoDetalladoCorrida(usuarioEmpresaReporteTO,
                    reporteCostoDetalladoCorrida, tituloReporte);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoFunAnalisiPeso")
    public byte[] generarReporteListadoFunAnalisiPeso(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteListadoFunAnalisisPeso> reporteListadoFunAnalisisPesos = UtilsJSON
                .jsonToList(ReporteListadoFunAnalisisPeso.class, map.get("reporteListadoFunAnalisisPesos"));
        String tituloReporte = UtilsJSON.jsonToObjeto(String.class, map.get("tituloReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReporteListadoFunAnalisiPeso(usuarioEmpresaReporteTO,
                    reporteListadoFunAnalisisPesos, tituloReporte);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoGrameaje")
    public byte[] generarReporteListadoGrameaje(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteListadoGrameaje> reporteListadoGrameaje = UtilsJSON.jsonToList(ReporteListadoGrameaje.class,
                map.get("reporteListadoGrameaje"));
        String tituloReporte = UtilsJSON.jsonToObjeto(String.class, map.get("tituloReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReporteListadoGrameaje(usuarioEmpresaReporteTO,
                    reporteListadoGrameaje, tituloReporte);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCostoDetalleFecha")
    public byte[] generarReporteCostoDetalleFecha(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteCostoDetalleFecha> reporteCostoDetalleFecha = UtilsJSON.jsonToList(ReporteCostoDetalleFecha.class,
                map.get("reporteCostoDetalleFecha"));
        String tituloReporte = UtilsJSON.jsonToObjeto(String.class, map.get("tituloReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReporteCostoDetalleFecha(usuarioEmpresaReporteTO,
                    reporteCostoDetalleFecha, tituloReporte);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReportePrdFunCostosPorFechaSimpleTO")
    public byte[] generarReportePrdFunCostosPorFechaSimpleTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReportePrdFunCostosPorFechaSimpleTO> reportePrdFunCostosPorFechaSimpleTOs = UtilsJSON
                .jsonToList(ReportePrdFunCostosPorFechaSimpleTO.class, map.get("reportePrdFunCostosPorFechaSimpleTOs"));
        String tituloReporte = UtilsJSON.jsonToObjeto(String.class, map.get("tituloReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReportePrdFunCostosPorFechaSimpleTO(usuarioEmpresaReporteTO,
                    reportePrdFunCostosPorFechaSimpleTOs, tituloReporte);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteResumenFinanciero")
    public byte[] generarReporteResumenFinanciero(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteResumenFinanciero> reporteResumenFinanciero = UtilsJSON.jsonToList(ReporteResumenFinanciero.class,
                map.get("reporteResumenFinanciero"));
        String tituloReporte = UtilsJSON.jsonToObjeto(String.class, map.get("tituloReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReporteResumenFinanciero(usuarioEmpresaReporteTO,
                    reporteResumenFinanciero, tituloReporte);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConsumoPorPiscina")
    public byte[] generarReporteConsumoPorPiscina(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteConsumoPorPiscina> reporteConsumoPorPiscina = UtilsJSON.jsonToList(ReporteConsumoPorPiscina.class,
                map.get("reporteConsumoPorPiscina"));
        String tituloReporte = UtilsJSON.jsonToObjeto(String.class, map.get("tituloReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReporteConsumoPorPiscina(usuarioEmpresaReporteTO,
                    reporteConsumoPorPiscina, tituloReporte);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConsumoPorFecha")
    public byte[] generarReporteConsumoPorFecha(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteConsumoPorFecha> reporteConsumoPorFecha = UtilsJSON.jsonToList(ReporteConsumoPorFecha.class,
                map.get("reporteConsumoPorFecha"));
        String tituloReporte = UtilsJSON.jsonToObjeto(String.class, map.get("tituloReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReporteConsumoPorFecha(usuarioEmpresaReporteTO,
                    reporteConsumoPorFecha, tituloReporte);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReportePrdResumenPesca")
    public byte[] generarReportePrdResumenPesca(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteResumenPesca> reporteResumenPescas = UtilsJSON.jsonToList(ReporteResumenPesca.class,
                map.get("reporteResumenPescas"));
        String tituloReporte = UtilsJSON.jsonToObjeto(String.class, map.get("tituloReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReportePrdResumenPesca(usuarioEmpresaReporteTO, reporteResumenPescas,
                    tituloReporte);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReportePrdResumenSiembra")
    public byte[] generarReportePrdResumenSiembra(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteResumenSiembra> reporteResumenSiembra = UtilsJSON.jsonToList(ReporteResumenSiembra.class,
                map.get("reporteResumenSiembra"));
        String tituloReporte = UtilsJSON.jsonToObjeto(String.class, map.get("tituloReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReportePrdResumenSiembra(usuarioEmpresaReporteTO,
                    reporteResumenSiembra, tituloReporte);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteLiquidacionPesca")
    public byte[] generarReporteLiquidacionPesca(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteLiquidacionPesca> reporteLiquidacionPescas = UtilsJSON.jsonToList(ReporteLiquidacionPesca.class,
                map.get("reporteLiquidacionPescas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteProduccionService.generarReporteLiquidacionPesca(usuarioEmpresaReporteTO,
                    reporteLiquidacionPescas);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBuscaObjLiquidacionPorLote")
    public PrdLiquidacionTO getBuscaObjLiquidacionPorLote(@RequestParam("liqLote") String liqLote, @RequestParam("liqEmpresa") String liqEmpresa, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return liquidacionService.getBuscaObjLiquidacionPorLote(liqLote, liqEmpresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getBuscaObjPreLiquidacionPorLote")
    public PrdPreLiquidacionTO getBuscaObjPreLiquidacionPorLote(@RequestParam("plLote") String plLote, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return preLiquidacionService.getBuscaObjPreLiquidacionPorLote(plLote);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }
}
