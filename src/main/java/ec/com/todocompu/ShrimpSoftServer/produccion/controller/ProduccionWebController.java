package ec.com.todocompu.ShrimpSoftServer.produccion.controller;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
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
import ec.com.todocompu.ShrimpSoftServer.produccion.service.AmbienteProduccionService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.ComisionistaControlService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.CorridaDetalleService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.CorridaService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.EquipoControlService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.FitoplanctonService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.GrameajeService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.LiquidacionDetalleService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.LiquidacionMotivoService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.LiquidacionService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.PiscinaGrupoRelacionService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.PiscinaGrupoService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.PiscinaService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.PiscinaTipoService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.PrdPrecioTallaLiquidacionPescaService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.PrdProductoService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.PrdReprocesoMotivoService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.PrdReprocesoService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.PreLiquidacionMotivoService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.PreLiquidacionService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.PresupuestoPescaMotivoService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.PresupuestoPescaService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.ProyeccionService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.SectorService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.SobrevivenciaService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.TallaService;
import ec.com.todocompu.ShrimpSoftServer.util.SistemaWebUtil;
import ec.com.todocompu.ShrimpSoftServer.util.service.ArchivoService;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.RetornoTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.ListaLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.ListaPreLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.ListadoGrameaje;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.MultiplePiscinaCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PiscinaGrameajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdComboCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdComboPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdConsumosDetalladoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdContabilizarCorridaCostoVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdContabilizarCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCorridaListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCorridasInconsistentesTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCostoDetalleFechaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFitoplanctonTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunAnalisisVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunCostosPorFechaSimpleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunLiquidacionConsolidandoTallasProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunLiquidacionConsolidandoTallasTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunPreLiquidacionConsolidandoTallasTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdGrameajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionComisionistaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionConsolidandoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionConsolidandoProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionConsultaEmpacadoraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionConsultaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionDetalleProductoEmpacadoraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionMotivoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionProductoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionTallaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionTallaTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionesDetalleTO;
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
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListadoFitoplanctonTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListadoGrameajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPiscinaGrupoRelacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPiscinaGrupoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionConsolidandoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionConsultaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionesDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPrecioTallaLiquidacionPescaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdProyeccionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdReprocesoEgresoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdReprocesoIngresoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdReprocesoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenFinancieroTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenPescaSiembraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdSobrevivenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdTrazabilidadCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdUtilidadDiariaCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdAmbienteProduccion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdComisionista;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorridaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorridaPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdEquipoControl;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdGrameaje;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscina;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupoPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaGrupoRelacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaTipo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPrecioTallaLiquidacionPesca;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPesca;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdProducto;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReprocesoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSobrevivencia;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdTalla;
import ec.com.todocompu.ShrimpSoftUtils.produccion.report.ReportePrdFunCostosPorFechaSimpleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.report.ReporteResumenFinanciero;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.InformacionAdicional;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/todocompuWS/produccionWebController")
public class ProduccionWebController {

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
    @Autowired
    private ArchivoService archivoService;
    @Autowired
    private FitoplanctonService fitoplanctonService;
    @Autowired
    private PrdPrecioTallaLiquidacionPescaService prdPrecioTallaLiquidacionPescaService;
    @Autowired
    private PiscinaGrupoService piscinaGrupoService;
    @Autowired
    private PiscinaTipoService piscinaTipoService;
    @Autowired
    private PiscinaGrupoRelacionService piscinaGrupoRelacionService;
    @Autowired
    private AmbienteProduccionService ambienteProduccionService;
    @Autowired
    private EquipoControlService equipoControlService;
    @Autowired
    private ComisionistaControlService comisionistaControlService;
    @Autowired
    private PrdReprocesoMotivoService prdReprocesoMotivoService;
    @Autowired
    private PrdReprocesoService prdReprocesoService;

    //GRAMEAJE PISICINA PROCESO
    @RequestMapping("/getConsultaGrameajePromedioPorPiscina")
    public ResponseEntity getConsultaGrameajePromedioPorPiscina(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String nomSector = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreSector"));
        boolean sobrevivencia = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("sobrevivencia"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            RetornoTO respues = corridaService.getConsultaGrameajePromedioPorPiscina(empresa, sector, nomSector, sobrevivencia, sisInfoTO);
            if (respues.getColumnas() != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/exportarReporteGrameajePromedioPorPiscina")
    public @ResponseBody
    String exportarReporteGrameajePromedioPorPiscina(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        Object[][] datos = UtilsJSON.jsonToObjeto(Object[][].class, map.get("datos"));
        List<String> columnas = UtilsJSON.jsonToObjeto(List.class, map.get("columnas"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteGrameajePromedioPorPiscina(usuarioEmpresaReporteTO, datos, columnas, codigoSector);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //RESUMEN SIEMBRA Y RESUMEN PESCA
    @RequestMapping("/getListaResumenCorridaTO")
    public ResponseEntity getListaResumenCorridaTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaFin"));
        String tipoResumen = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipoResumen"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<PrdResumenCorridaTO> respues = corridaService.getListaResumenCorridaTO(empresa, codigoSector, "'" + fechaInicio + "'", "'" + fechaFin + "'", tipoResumen);
            if (respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/getResumenSiembra")
    public ResponseEntity getResumenSiembra(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaFin"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, parametros.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<PrdResumenPescaSiembraTO> respues = corridaService.getResumenSiembra(empresa, codigoSector, fechaInicio, fechaFin, usuario);
            if (respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    // resumen economico de pesca
    @RequestMapping("/getResumenPesca")
    public RespuestaWebTO getResumenPesca(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, parametros.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaFin"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, parametros.get("usuario"));
        boolean incluirTransferencia = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("incluirTransferencia"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<PrdResumenPescaSiembraTO> respues = corridaService.getResumenPesca(empresa, codigoSector, fechaInicio, fechaFin, usuario, incluirTransferencia);
            if (respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return resp;
    }

    @RequestMapping("/exportarReporteResumenPescaEconomico")
    public @ResponseBody
    String exportarReporteResumenPescaEconomico(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String nombreSector = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreSector"));
        boolean incluirTransferencia = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("incluirTransferencia"));
        List<PrdResumenPescaSiembraTO> listado = UtilsJSON.jsonToList(PrdResumenPescaSiembraTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteResumenPescaEconomico(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, nombreSector, sector, listado, incluirTransferencia);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteResumenPescaEconomico")
    public @ResponseBody
    String generarReporteResumenPescaEconomico(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportResumenPescasNew.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdResumenPescaSiembraTO> listaPrdResumenPescaSiembraTO = UtilsJSON.jsonToList(PrdResumenPescaSiembraTO.class, parametros.get("listaPrdResumenPescaSiembraTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteResumenEconomicoPesca(usuarioEmpresaReporteTO, listaPrdResumenPescaSiembraTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteResumenSiembra")
    public @ResponseBody
    String generarReporteResumenSiembraAux(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportResumenSiembras.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdResumenCorridaTO> listaPrdListaResumenCorridaTO = UtilsJSON.jsonToList(PrdResumenCorridaTO.class, parametros.get("listaPrdListaResumenCorridaTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteResumenSiembra(usuarioEmpresaReporteTO, listaPrdListaResumenCorridaTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteResumenPesca")
    public @ResponseBody
    String generarResumenPesca(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportResumenPescas.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdResumenCorridaTO> listaPrdListaResumenCorridaTO = UtilsJSON.jsonToList(PrdResumenCorridaTO.class, parametros.get("listaPrdListaResumenCorridaTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteResumenPesca(usuarioEmpresaReporteTO, listaPrdListaResumenCorridaTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReportePrdResumenSiembra")
    public @ResponseBody
    String generarReportePrdResumenSiembra(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportResumenSiembrasNew.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ReporteResumenSiembra> reporteResumenSiembra = UtilsJSON.jsonToList(ReporteResumenSiembra.class, parametros.get("reporteResumenSiembra"));
        String tituloReporte = UtilsJSON.jsonToObjeto(String.class, parametros.get("tituloReporte"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePrdResumenSiembra(usuarioEmpresaReporteTO, reporteResumenSiembra, tituloReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReportePrdResumenPesca")
    public @ResponseBody
    String generarReportePrdResumenPesca(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportResumenPescasNew.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<ReporteResumenPesca> reporteResumenPescas = UtilsJSON.jsonToList(ReporteResumenPesca.class, parametros.get("reporteResumenPescas"));
        String tituloReporte = UtilsJSON.jsonToObjeto(String.class, parametros.get("tituloReporte"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePrdResumenPesca(usuarioEmpresaReporteTO, reporteResumenPescas, tituloReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteResumenEconomicoSiembra")
    public @ResponseBody
    String generarReporteResumenEconomicoSiembra(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportResumenSiembrasNew.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdResumenPescaSiembraTO> datos = UtilsJSON.jsonToList(PrdResumenPescaSiembraTO.class, parametros.get("datos"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteResumenEconomicoSiembra(usuarioEmpresaReporteTO, datos);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteResumenEconomicoSiembra")
    public @ResponseBody
    String exportarReporteResumenEconomicoSiembra(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String nombreSector = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreSector"));
        List<PrdResumenPescaSiembraTO> listado = UtilsJSON.jsonToList(PrdResumenPescaSiembraTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteResumenEconomicoSiembra(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, nombreSector, sector, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteResumenEconomicoPesca")
    public @ResponseBody
    String generarReporteResumenEconomicoPesca(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportResumenPescasNew.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdResumenPescaSiembraTO> datos = UtilsJSON.jsonToList(PrdResumenPescaSiembraTO.class, parametros.get("datos"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteResumenEconomicoPesca(usuarioEmpresaReporteTO, datos);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteResumenSiembra")
    public @ResponseBody
    String exportarReporteResumenSiembra(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String nombreSector = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreSector"));
        boolean esAcuagold = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("esAcuagold"));
        List<PrdResumenCorridaTO> listado = UtilsJSON.jsonToList(PrdResumenCorridaTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteResumenSiembra(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, nombreSector, sector, listado, esAcuagold);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteResumenPesca")
    public @ResponseBody
    String exportarReporteResumenPesca(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String nombreSector = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreSector"));
        List<PrdResumenCorridaTO> listado = UtilsJSON.jsonToList(PrdResumenCorridaTO.class, parametros.get("listado"));
        boolean incluirTransferencia = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("incluirTransferencia"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteResumenPesca(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, nombreSector, sector, listado, incluirTransferencia);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //**
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
    public RespuestaWebTO insertarPrdSectorTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdSectorTO prdSectorTO = UtilsJSON.jsonToObjeto(PrdSectorTO.class, map.get("prdSectorTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = sectorService.insertarPrdSector(prdSectorTO, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El sector: Código " + prdSectorTO.getSecCodigo() + ", se ha guardado correctamente");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("El codigo del sector que va a ingresar ya existe \nIntente con otro por favor...");
            }
        } catch (GeneralException e) {
            resp.setOperacionMensaje(e.getMessage());
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarPrdSectorTO")
    public RespuestaWebTO modificarPrdSectorTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdSectorTO prdSectorTO = UtilsJSON.jsonToObjeto(PrdSectorTO.class, map.get("prdSectorTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = sectorService.modificarPrdSector(prdSectorTO, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El sector: Código " + prdSectorTO.getSecCodigo() + ", se ha modificado correctamente");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al modificar el sector");
            }
        } catch (GeneralException e) {
            resp.setOperacionMensaje(e.getMessage());
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoPrdSector")
    public RespuestaWebTO modificarEstadoPrdSector(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdSectorTO prdSectorTO = UtilsJSON.jsonToObjeto(PrdSectorTO.class, map.get("prdSectorTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        boolean inactivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivos"));
        try {
            boolean respues = sectorService.modificarEstadoPrdSector(prdSectorTO, inactivos, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El sector: Código " + prdSectorTO.getSecCodigo() + ", se ha modificado correctamente");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al modificar el sector");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarPrdSectorTO")
    public RespuestaWebTO eliminarPrdSectorTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdSectorTO prdSectorTO = UtilsJSON.jsonToObjeto(PrdSectorTO.class, map.get("prdSectorTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = sectorService.eliminarPrdSector(prdSectorTO, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El sector: Código " + prdSectorTO.getSecCodigo() + ", se ha eliminado correctamente");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se puede eliminar sector. El sector tiene movimientos");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteSector")
    public @ResponseBody
    String generarReporteSectores(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportSector.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdListaSectorTO> listado = UtilsJSON.jsonToList(PrdListaSectorTO.class, parametros.get("ListadoSectores"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteSector(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteSector")
    public @ResponseBody
    String exportarReporteSectores(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<PrdListaSectorTO> listado = UtilsJSON.jsonToList(PrdListaSectorTO.class, map.get("ListadoSectores"));
        boolean centroProduccion = UtilsJSON.jsonToObjeto(boolean.class, map.get("centroProduccion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteSectores(usuarioEmpresaReporteTO, listado, centroProduccion);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarPrdLiquidacionMotivo")
    public RespuestaWebTO insertarPrdLiquidacionMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionMotivo prdLiquidacionMotivo = UtilsJSON.jsonToObjeto(PrdLiquidacionMotivo.class, map.get("prdLiquidacionMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = liquidacionMotivoService.insertarPrdLiquidacionMotivo(prdLiquidacionMotivo, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se ha guardado liquidación de motivo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarPrdLiquidacionMotivo")
    public RespuestaWebTO modificarPrdLiquidacionMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionMotivo prdLiquidacionMotivo = UtilsJSON.jsonToObjeto(PrdLiquidacionMotivo.class, map.get("prdLiquidacionMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = liquidacionMotivoService.modificarPrdLiquidacionMotivo(prdLiquidacionMotivo, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se ha podido modificar liquidación de motivo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoPrdLiquidacionMotivo")
    public RespuestaWebTO modificarEstadoPrdLiquidacionMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        PrdLiquidacionMotivo prdLiquidacionMotivo = UtilsJSON.jsonToObjeto(PrdLiquidacionMotivo.class, map.get("prdLiquidacionMotivo"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        try {
            String respuesta = liquidacionMotivoService.modificarEstadoPrdLiquidacionMotivo(prdLiquidacionMotivo, estado, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se ha podido modificar el estado de liquidación de motivo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarPrdLiquidacionMotivo")
    public RespuestaWebTO eliminarPrdLiquidacionMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionMotivo prdLiquidacionMotivo = UtilsJSON.jsonToObjeto(PrdLiquidacionMotivo.class, map.get("prdLiquidacionMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = liquidacionMotivoService.eliminarPrdLiquidacionMotivo(prdLiquidacionMotivo, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se ha podido eliminar liquidación de motivo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaPrdLiquidacionMotivo")
    public RespuestaWebTO getListaPrdLiquidacionMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean inactivo = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdLiquidacionMotivo> respues = liquidacionMotivoService.getListaPrdLiquidacionMotivoTO(empresa, inactivo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron liquidación de motivos.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarPrdPresupuestoPescaMotivo")
    public RespuestaWebTO insertarPrdPresupuestoPescaMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo = UtilsJSON.jsonToObjeto(PrdPresupuestoPescaMotivo.class, map.get("prdPresupuestoPescaMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = presupuestoPescaMotivoService.insertarPrdPresupuestoPescaMotivo(prdPresupuestoPescaMotivo, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se ha guardado presupuesto pesca motivo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarPrdPresupuestoPescaMotivo")
    public RespuestaWebTO modificarPrdPresupuestoPescaMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo = UtilsJSON.jsonToObjeto(PrdPresupuestoPescaMotivo.class, map.get("prdPresupuestoPescaMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = presupuestoPescaMotivoService.modificarPrdPresupuestoPescaMotivo(prdPresupuestoPescaMotivo, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se ha guardado presupuesto pesca motivo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoPrdPresupuestoPescaMotivo")
    public RespuestaWebTO modificarEstadoPrdPresupuestoPescaMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo = UtilsJSON.jsonToObjeto(PrdPresupuestoPescaMotivo.class, map.get("prdPresupuestoPescaMotivo"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = presupuestoPescaMotivoService.modificarEstadoPrdPresupuestoPescaMotivo(prdPresupuestoPescaMotivo, estado, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se ha guardado presupuesto pesca motivo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarPrdPresupuestoPescaMotivo")
    public RespuestaWebTO eliminarPrdPresupuestoPescaMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo = UtilsJSON.jsonToObjeto(PrdPresupuestoPescaMotivo.class, map.get("prdPresupuestoPescaMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = presupuestoPescaMotivoService.eliminarPrdPresupuestoPescaMotivo(prdPresupuestoPescaMotivo, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se ha guardado presupuesto pesca motivo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaPrdPresupuestoPescaMotivo")
    public RespuestaWebTO getListaPrdPresupuestoPescaMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean inactivo = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdPresupuestoPescaMotivo> respuesta = presupuestoPescaMotivoService.getListaPrdPresupuestoPescaMotivoTO(empresa, inactivo);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron liquidación de motivos.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarPrdPreLiquidacionMotivo")
    public RespuestaWebTO insertarPrdPreLiquidacionMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPreLiquidacionMotivo prdPreLiquidacionMotivo = UtilsJSON.jsonToObjeto(PrdPreLiquidacionMotivo.class, map.get("prdPreLiquidacionMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = preLiquidacionMotivoService.insertarPrdPreLiquidacionMotivo(prdPreLiquidacionMotivo, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se ha guardado pre liquidación motivo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarPrdPreLiquidacionMotivo")
    public RespuestaWebTO modificarPrdPreLiquidacionMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPreLiquidacionMotivo prdPreLiquidacionMotivo = UtilsJSON.jsonToObjeto(PrdPreLiquidacionMotivo.class, map.get("prdPreLiquidacionMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = preLiquidacionMotivoService.modificarPrdPreLiquidacionMotivo(prdPreLiquidacionMotivo, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se ha guardado pre liquidación motivo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoPrdPreLiquidacionMotivo")
    public RespuestaWebTO modificarEstadoPrdPreLiquidacionMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPreLiquidacionMotivo prdPreLiquidacionMotivo = UtilsJSON.jsonToObjeto(PrdPreLiquidacionMotivo.class, map.get("prdPreLiquidacionMotivo"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = preLiquidacionMotivoService.modificarEstadoPrdPreLiquidacionMotivo(prdPreLiquidacionMotivo, estado, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se ha cambiado el estado de motivo de pre liquidación.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarPrdPreLiquidacionMotivo")
    public RespuestaWebTO eliminarPrdPreLiquidacionMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPreLiquidacionMotivo prdPreLiquidacionMotivo = UtilsJSON.jsonToObjeto(PrdPreLiquidacionMotivo.class, map.get("prdPreLiquidacionMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = preLiquidacionMotivoService.eliminarPrdPreLiquidacionMotivo(prdPreLiquidacionMotivo, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se ha guardado pre liquidación motivo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaPrdPreLiquidacionMotivo")
    public RespuestaWebTO getListaPrdPreLiquidacionMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean inactivo = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdPreLiquidacionMotivo> respuesta = preLiquidacionMotivoService.getListaPrdPreLiquidacionMotivoTO(empresa, inactivo);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados motivos de pre liquidación.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
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

    // ARCHIVO: PRODUCTO DE PESCA
    @RequestMapping("/getListaPrdLiquidacionProducto")
    public RespuestaWebTO getListaPrdLiquidacionProducto(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdProducto> respues = prdProductoService.getListaPrdLiquidacionProducto(empresa);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron detalles de la corrida.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
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
    public RespuestaWebTO getListaSectorTO(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean activo = UtilsJSON.jsonToObjeto(boolean.class, map.get("activo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdListaSectorTO> respues = sectorService.getListaSectorTO(empresa, activo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron sectores.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
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

    @RequestMapping("/obtenerDatosParaCrudCorrida")
    public RespuestaWebTO obtenerDatosParaCrudCorrida(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = corridaService.obtenerDatosParaCrudCorrida(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se han encontrado datos para generar corrida.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDetalleCorrida")
    public RespuestaWebTO obtenerDetalleCorrida(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        Date fechaHasta = UtilsJSON.jsonToObjeto(Date.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = corridaService.obtenerDetalleCorrida(empresa, sector, piscina, numero, fechaHasta);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron detalles de la corrida.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
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
    public RespuestaWebTO insertarPrdCorrida(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdCorrida prdCorrida = UtilsJSON.jsonToObjeto(PrdCorrida.class, map.get("prdCorrida"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = corridaService.insertarCorrida(prdCorrida, sisInfoTO);
            if (respues != null && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues.substring(1));
                resp.setOperacionMensaje(respues.substring(1));
            } else {
                resp.setOperacionMensaje(respues != null ? respues.substring(1) : "No se ha guardado corrida.");
            }
        } catch (GeneralException e) {
            resp.setOperacionMensaje(e.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarPrdCorrida")
    public RespuestaWebTO modificarPrdCorrida(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdCorrida prdCorrida = UtilsJSON.jsonToObjeto(PrdCorrida.class, map.get("prdCorrida"));
        boolean cambioFechaDesde = UtilsJSON.jsonToObjeto(boolean.class, map.get("cambioFechaDesde"));
        boolean cambioFechaHasta = UtilsJSON.jsonToObjeto(boolean.class, map.get("cambioFechaHasta"));
        List<PrdCorridaDetalle> corridaDetalleList = UtilsJSON.jsonToList(PrdCorridaDetalle.class, map.get("corridaDetalleList"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            prdCorrida.setPrdCorridaDetalleList(corridaDetalleList);
            String respues = corridaService.modificarCorrida(prdCorrida, cambioFechaDesde, cambioFechaHasta, sisInfoTO);
            if (respues != null && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues.substring(1));
                resp.setOperacionMensaje(respues.substring(1));
            } else {
                resp.setOperacionMensaje(respues != null ? respues.substring(1) : "No se ha modificado corrida.");
            }
        } catch (GeneralException e) {
            resp.setOperacionMensaje(e.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarPrdCorrida")
    public RespuestaWebTO eliminarPrdCorrida(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdCorrida prdCorrida = UtilsJSON.jsonToObjeto(PrdCorrida.class, map.get("prdCorrida"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = corridaService.eliminarPrdCorrida(prdCorrida, sisInfoTO);
            if (respues != null && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues.substring(1));
                resp.setOperacionMensaje(respues.substring(1));
            } else {
                resp.setOperacionMensaje(respues != null ? respues.substring(1) : "No se ha modificado corrida.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/cambiarCodigoPrdCorrida")
    public RespuestaWebTO cambiarCodigoPrdCorrida(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdCorridaPK prdCorridaPK = UtilsJSON.jsonToObjeto(PrdCorridaPK.class, map.get("prdCorridaPK"));
        String nuevoCodigoPrdCorrida = UtilsJSON.jsonToObjeto(String.class, map.get("nuevoCodigoPrdCorrida"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = corridaService.cambiarCodigoPrdCorrida(prdCorridaPK, nuevoCodigoPrdCorrida);
            if (respues != null && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo("El número de la corrida " + prdCorridaPK.getCorNumero() + " se ha modificado correctamente.");
                resp.setOperacionMensaje("El número de la corrida " + prdCorridaPK.getCorNumero() + " se ha modificado correctamente.");
            } else {
                resp.setOperacionMensaje(respues != null ? respues.substring(1) : "No se ha modificado el número de la corrida.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarPrdCorrida")
    public @ResponseBody
    String exportarPrdCorrida(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<PrdCorrida> listado = UtilsJSON.jsonToList(PrdCorrida.class, map.get("listado"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        boolean ordenProduccion = UtilsJSON.jsonToObjeto(boolean.class, map.get("ordenProduccion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarPrdCorrida(usuarioEmpresaReporteTO, listado, sector, piscina, ordenProduccion);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReportePrdCorrida")
    public @ResponseBody
    String generarReportePrdCorrida(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportCorridas.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdCorrida> listado = UtilsJSON.jsonToList(PrdCorrida.class, parametros.get("listado"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, parametros.get("piscina"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePrdCorrida(usuarioEmpresaReporteTO, nombreReporte, listado, sector, piscina);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
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

    @RequestMapping("/eliminarPrdPiscinaTO")
    public boolean eliminarPrdPiscinaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPiscinaTO prdPiscinaTO = UtilsJSON.jsonToObjeto(PrdPiscinaTO.class, map.get("prdPiscinaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return false;
            // return piscinaService.eliminarPrdPiscina(prdPiscinaTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/insertarPrdPiscina")
    public RespuestaWebTO insertarPrdPiscina(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPiscinaTO prdPiscina = UtilsJSON.jsonToObjeto(PrdPiscinaTO.class, map.get("prdPiscina"));
        List<PrdPiscinaGrupoRelacionTO> listado = UtilsJSON.jsonToList(PrdPiscinaGrupoRelacionTO.class, map.get("listadoPrdPiscinaGrupoRelacionTO"));

        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = piscinaService.insertarPrdPiscina(prdPiscina, listado, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La piscina: Nº " + prdPiscina.getPisNumero() + ", se ha guardado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al guardar la piscina");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarPrdPiscina")
    public RespuestaWebTO modificarPrdPiscina(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPiscinaTO prdPiscinaTO = UtilsJSON.jsonToObjeto(PrdPiscinaTO.class, map.get("prdPiscinaTO"));
        List<PrdPiscinaGrupoRelacionTO> listado = UtilsJSON.jsonToList(PrdPiscinaGrupoRelacionTO.class, map.get("listadoPrdPiscinaGrupoRelacionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = piscinaService.modificarPrdPiscina(prdPiscinaTO, listado, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La piscina: Nº " + prdPiscinaTO.getPisNumero() + ", se ha guardado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al guardar la piscina");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoPrdPiscina")
    public RespuestaWebTO modificarEstadoPrdPiscina(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPiscinaTO prdPiscinaTO = UtilsJSON.jsonToObjeto(PrdPiscinaTO.class, map.get("prdPiscinaTO"));
        boolean estado = UtilsJSON.jsonToObjeto(Boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = piscinaService.modificarEstadoPrdPiscina(prdPiscinaTO, estado, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                String sms = estado ? " inactivado" : "activado";
                resp.setOperacionMensaje("La piscina: Nº " + prdPiscinaTO.getPisNumero() + ", se ha " + sms + " correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al guardar la piscina");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarPrdPiscina")
    public RespuestaWebTO eliminarPrdPiscina(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPiscinaTO prdPiscinaTO = UtilsJSON.jsonToObjeto(PrdPiscinaTO.class, map.get("prdPiscinaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = piscinaService.eliminarPrdPiscina(prdPiscinaTO, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La piscina: Nº " + prdPiscinaTO.getPisNumero() + ", se ha eliminado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al eliminar la piscina");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/cambiarCodigoPiscina")
    public RespuestaWebTO cambiarCodigoPiscina(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscinaIncorrecta = UtilsJSON.jsonToObjeto(String.class, map.get("piscinaIncorrecta"));
        String piscinaCorrecta = UtilsJSON.jsonToObjeto(String.class, map.get("piscinaCorrecta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = piscinaService.cambiarCodigoPiscina(empresa, sector, piscinaIncorrecta, piscinaCorrecta);
            if (respues != null && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo("El número de la piscina " + piscinaIncorrecta + " se ha modificado correctamente.");
                resp.setOperacionMensaje("El número de la piscina " + piscinaIncorrecta + " se ha modificado correctamente.");
            } else {
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePiscina")
    public @ResponseBody
    String generarReportePiscina(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportPiscina.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdListaPiscinaTO> listado = UtilsJSON.jsonToList(PrdListaPiscinaTO.class, parametros.get("ListadoPiscinas"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePicina(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReportePiscinas")
    public @ResponseBody
    String exportarReportePiscinas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<PrdListaPiscinaTO> listado = UtilsJSON.jsonToList(PrdListaPiscinaTO.class, map.get("ListadoPiscinas"));
        boolean centroCosto = UtilsJSON.jsonToObjeto(boolean.class, map.get("centroCosto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReportePiscinas(usuarioEmpresaReporteTO, listado, centroCosto);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPiscinaTO")
    public RespuestaWebTO getListaPiscinaTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        boolean activo = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("mostrarInactivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<PrdListaPiscinaTO> respues = piscinaService.getListaPiscinaTO(empresa.trim(), sector.trim(), activo);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron piscinas");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
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
    public RespuestaWebTO getListaPiscinasGrameaje(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        List<PiscinaGrameajeTO> listaResultante = new ArrayList<>();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirTodos"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PiscinaGrameajeTO> respuesta = piscinaService.getListaPiscinasGrameaje(empresa.trim(), sector.trim(), fecha);
            if (respuesta != null && !respuesta.isEmpty()) {
                if (!incluirTodos) {
                    List<PiscinaGrameajeTO> listaFiltrada = new ArrayList<>();
                    respuesta.stream().filter((piscinaGrameajeTO) -> (piscinaGrameajeTO.getGraPesoActual().compareTo(BigDecimal.ZERO) == 0)).map((gramaje) -> {
                        return gramaje;
                    }).forEachOrdered((gramaje) -> {
                        listaFiltrada.add(gramaje);
                    });
                    listaResultante = listaFiltrada;
                } else {
                    listaResultante = respuesta;
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(listaResultante);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
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
    
    @RequestMapping("/getListaPiscinaGeneralTO")
    public List<PrdListaPiscinaTO> getListaPiscinaGeneralTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return piscinaService.getListaPiscinaTO(empresa.trim());
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPiscinaTOBusqueda")
    public RespuestaWebTO getListaPiscinaTOBusqueda(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        List<String> sectores = UtilsJSON.jsonToList(String.class, map.get("sectores"));
        String grupo = UtilsJSON.jsonToObjeto(String.class, map.get("grupo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        boolean mostrarInactivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("mostrarInactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdListaPiscinaTO> piscinas = new ArrayList<>();
            if (sectores != null && !sectores.isEmpty()) {
                for (int i = 0; i < sectores.size(); i++) {
                    List<PrdListaPiscinaTO> piscinasPorSector = piscinaService.getListaPiscinaTOBusqueda(empresa, sectores.get(i), fecha, grupo, tipo, mostrarInactivos);
                    if (piscinasPorSector != null && !piscinasPorSector.isEmpty()) {
                        piscinas.addAll(piscinasPorSector);
                    }
                }
            } else {
                piscinas = piscinaService.getListaPiscinaTOBusqueda(empresa, sector, fecha, grupo, tipo, mostrarInactivos);
            }
            if (piscinas != null && !piscinas.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(piscinas);
            } else {
                resp.setOperacionMensaje("No se encontraron piscinas");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
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
    public RespuestaWebTO getListaCorridasPorEmpresaSectorPiscina(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdCorrida> respues = corridaService.getListaCorridasPorEmpresaSectorPiscina(empresa, sector, piscina);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron corridas");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
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
    public RespuestaWebTO getListaCorridaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdListaCorridaTO> respues = corridaService.getListaCorridaTO(empresa, sector, piscina, fechaDesde, fechaHasta, tipo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron corridas");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarCorridasPorFecha")
    public RespuestaWebTO listarCorridasPorFecha(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdCorridaListadoTO> respues = corridaService.listarCorridasPorFecha(empresa, fechaDesde, fechaHasta, tipo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron corridas");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
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
    public RespuestaWebTO getPrdListaCostosDetalleCorridaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
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
            List<PrdListaCostosDetalleCorridaTO> respues = corridaService.getPrdListaCostosDetalleCorridaTO(empresa, sector, piscina, corrida, desde, hasta,
                    agrupadoPor);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
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

    @RequestMapping("/getListaContabilizarCorridaTO")
    public RespuestaWebTO getListaContabilizarCorridaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdContabilizarCorridaTO> respues = corridaService.getListaContabilizarCorridaTO(empresa, "'" + desde + "'", "'" + hasta + "'");
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/consultarFechaMaxMin")
    public RespuestaWebTO consultarFechaMaxMin(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String tipoResumen = UtilsJSON.jsonToObjeto(String.class, map.get("tipoResumen"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = corridaService.consultarFechaMaxMin(empresa, tipoResumen);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                Date fechaHasta = UtilsValidacion.fecha(respuesta.substring(respuesta.indexOf(',') + 1), "yyyy-MM-dd");
                Date fechaDesde = UtilsValidacion.fechaString_Date("01/01/" + fechaHasta.toString().split(" ")[5], "dd/MM/yyyy");
                campos.put("fechaDesde", fechaDesde);
                campos.put("fechaHasta", fechaHasta);
                resp.setExtraInfo(campos);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarPrdSobrevivencia")
    public RespuestaWebTO insertarPrdSobrevivencia(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdSobrevivenciaTO prdSobrevivenciaTO = UtilsJSON.jsonToObjeto(PrdSobrevivenciaTO.class,
                map.get("prdSobrevivenciaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            PrdSobrevivencia respues = sobrevivenciaService.insertarSobrevivencia(prdSobrevivenciaTO, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La sobrevivencia: Código " + respues.getSobCodigo() + ", se ha ingresado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al ingresar sobrevivencia");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarPrdSobrevivencia")
    public RespuestaWebTO modificarPrdSobrevivencia(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdSobrevivenciaTO prdSobrevivenciaTO = UtilsJSON.jsonToObjeto(PrdSobrevivenciaTO.class,
                map.get("prdSobrevivenciaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = sobrevivenciaService.modificarPrdSobrevivencia(prdSobrevivenciaTO, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La sobrevivencia: Valor " + prdSobrevivenciaTO.getSobSobrevivencia() + ", se ha modificado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al modificar sobrevivencia");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarPrdSobrevivencia")
    public RespuestaWebTO eliminarPrdSobrevivencia(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdSobrevivenciaTO prdSobrevivenciaTO = UtilsJSON.jsonToObjeto(PrdSobrevivenciaTO.class,
                map.get("prdSobrevivenciaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = sobrevivenciaService.eliminarPrdSobrevivencia(prdSobrevivenciaTO, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La sobrevivencia: Valor " + prdSobrevivenciaTO.getSobSobrevivencia() + ", se ha eliminado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al eliminar sobrevivencia");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaSobrevivenciaTO")
    public RespuestaWebTO getListaSobrevivenciaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdListaSobrevivenciaTO> respues = sobrevivenciaService.getListaSobrevivenciaTO(empresa, sector);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteSobrevivenciaTO")
    public @ResponseBody
    String generarReporteSobrevivenciaTO(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportSobrevivencia.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdListaSobrevivenciaTO> listado = UtilsJSON.jsonToList(PrdListaSobrevivenciaTO.class, parametros.get("ListadoSobrevivencia"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteSobrevivencia(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteSobrevivenciaTO")
    public @ResponseBody
    String exportarReporteSobrevivenciaTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<PrdListaSobrevivenciaTO> listado = UtilsJSON.jsonToList(PrdListaSobrevivenciaTO.class, map.get("ListadoSobrevivencia"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteSobrevivencia(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
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

    @RequestMapping("/eliminarPrdGrameaje")
    public RespuestaWebTO eliminarPrdGrameaje(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdGrameajeTO prdGrameajeTO = UtilsJSON.jsonToObjeto(PrdGrameajeTO.class, map.get("prdGrameajeTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = grameajeService.eliminarPrdGrameaje(prdGrameajeTO, sisInfoTO);
            if (respuesta.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respuesta.substring(1, respuesta.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(respuesta.substring(1, respuesta.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
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
    public RespuestaWebTO getPrdListadoGrameajeTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdListadoGrameajeTO> respues = grameajeService.getPrdListadoGrameajeTO(empresa, sector, piscina, desde, hasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
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
    public RespuestaWebTO getPrdListadoCostoDetalleFechaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String secCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("secCodigo"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String agrupadoPor = UtilsJSON.jsonToObjeto(String.class, map.get("agrupadoPor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdCostoDetalleFechaTO> respuesta = corridaService.getPrdListadoCostoDetalleFechaTO(empresa, secCodigo, desde, hasta, agrupadoPor);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron corridas");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarCorridaActivo")
    public RespuestaWebTO modificarCorridaActivo(@RequestBody String json) {
        // Listado costos de productos en proceso
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String proceso = UtilsJSON.jsonToObjeto(String.class, map.get("proceso"));
        String agrupadoPor = UtilsJSON.jsonToObjeto(String.class, map.get("agrupadoPor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            RetornoTO respues = corridaService.modificarCorridaActivo(empresa, sector, hasta, proceso, agrupadoPor);
            if (respues.getColumnas() != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteConsumosProductosProcesos")
    public @ResponseBody
    String exportarReporteConsumosProductosProcesos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<String> columnas = UtilsJSON.jsonToList(String.class, map.get("columnas"));
        List<String[]> datos = UtilsJSON.jsonToList(String[].class, map.get("datos"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteConsumosProductosProcesos(usuarioEmpresaReporteTO, sector, hasta, columnas, datos);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCostosProductosProcesos")
    public @ResponseBody
    String exportarReporteCostosProductosProcesos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<String> columnas = UtilsJSON.jsonToList(String.class, map.get("columnas"));
        List<String[]> datos = UtilsJSON.jsonToList(String[].class, map.get("datos"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteDinamicoCostosProductosProcesos(usuarioEmpresaReporteTO, sector, hasta, columnas, datos);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarCorridaActivoSeleccionMultiple")
    public RespuestaWebTO modificarCorridaActivoSeleccionMultiple(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<MultiplePiscinaCorrida> multiplePiscinaCorrida = UtilsJSON.jsonToList(MultiplePiscinaCorrida.class,
                map.get("multiplePiscinaCorrida"));
        String proceso = UtilsJSON.jsonToObjeto(String.class, map.get("proceso"));
        String agrupadoPor = UtilsJSON.jsonToObjeto(String.class, map.get("agrupadoPor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            RetornoTO respues = corridaService.modificarCorridaActivoSeleccionMultiple(empresa, multiplePiscinaCorrida, proceso,
                    agrupadoPor);
            if (respues.getColumnas() != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteConsumosPiscinaMultiple")
    public @ResponseBody
    String exportarReporteConsumosPiscinaMultiple(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<String> columnas = UtilsJSON.jsonToList(String.class, map.get("columnas"));
        List<String[]> datos = UtilsJSON.jsonToList(String[].class, map.get("datos"));
        String nombreSector = UtilsJSON.jsonToObjeto(String.class, map.get("nombreSector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String corrida = UtilsJSON.jsonToObjeto(String.class, map.get("corrida"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String titulo = "Reporte consumos por piscina múltiple (CATEGORIA)";
        try {
            respuesta = reporteProduccionService.exportarReporteDinamico(usuarioEmpresaReporteTO, titulo, columnas, datos, nombreSector, piscina, corrida);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCostoPiscinaMultiple")
    public @ResponseBody
    String exportarReporteCostoPiscinaMultiple(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<String> columnas = UtilsJSON.jsonToList(String.class, map.get("columnas"));
        List<String[]> datos = UtilsJSON.jsonToList(String[].class, map.get("datos"));
        String nombreSector = UtilsJSON.jsonToObjeto(String.class, map.get("nombreSector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String corrida = UtilsJSON.jsonToObjeto(String.class, map.get("corrida"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String titulo = "Reporte costos por piscina múltiple (CATEGORIA)";
        try {
            respuesta = reporteProduccionService.exportarReporteDinamico(usuarioEmpresaReporteTO, titulo, columnas, datos, nombreSector, piscina, corrida);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
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

    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
    /////////////////////////////////////////////////////
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
    public RespuestaWebTO getCostoPorFechaProrrateado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        String agrupadoPor = UtilsJSON.jsonToObjeto(String.class, map.get("agrupadoPor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            RetornoTO respues = corridaService.getCostoPorFechaProrrateado(empresa, codigoSector, fechaInicio, fechaFin, usuario,
                    agrupadoPor);
            if (respues.getColumnas() != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReportePorFechaProrrateado")
    public @ResponseBody
    String exportarReportePorFechaProrrateado(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<String> columnas = UtilsJSON.jsonToList(String.class, map.get("columnas"));
        List<String[]> datos = UtilsJSON.jsonToList(String[].class, map.get("datos"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String nombreSector = UtilsJSON.jsonToObjeto(String.class, map.get("nombreSector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.
                    exportarReportePorFechaProrrateado(usuarioEmpresaReporteTO, codigoSector, nombreSector, fechaInicio, fechaFin, columnas, datos);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrdFunCostosPorFechaSimpleTO")
    public RespuestaWebTO getPrdFunCostosPorFechaSimpleTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String infEmpresea = UtilsJSON.jsonToObjeto(String.class, map.get("infEmpresea"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdFunCostosPorFechaSimpleTO> respuesta = corridaService.getPrdFunCostosPorFechaSimpleTO(codigoSector, fechaInicio, fechaFin, infEmpresea);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getCostoPorPiscinaSemanal")
    public RespuestaWebTO getCostoPorPiscinaSemanal(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
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
            RetornoTO respues = corridaService.getCostoPorPiscinaSemanal(empresa, codigoSector, numeroPiscina, fechaInicio, fechaFin,
                    usuario, agrupadoPor, periodo);
            if (respues.getColumnas() != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteCostoPiscinaSemanal")
    public @ResponseBody
    String exportarReporteCostoPiscinaSemanal(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<String> columnas = UtilsJSON.jsonToList(String.class, map.get("columnas"));
        List<String[]> datos = UtilsJSON.jsonToList(String[].class, map.get("datos"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String codigoCorrida = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCorrida"));
        String numeroPiscina = UtilsJSON.jsonToObjeto(String.class, map.get("numeroPiscina"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String nombreSector = UtilsJSON.jsonToObjeto(String.class, map.get("nombreSector"));
        String hectareas = UtilsJSON.jsonToObjeto(String.class, map.get("hectareas"));
        Integer numLarvas = UtilsJSON.jsonToObjeto(Integer.class, map.get("numLarvas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteCostoPiscinaSemanal(usuarioEmpresaReporteTO, nombreSector, codigoSector, codigoCorrida,
                    numeroPiscina, fechaInicio, fechaFin, hectareas, numLarvas, columnas, datos);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCostosMensuales")
    public RespuestaWebTO getCostosMensuales(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
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
            RetornoTO respuesta = corridaService.getCostosMensuales(empresa, codigoSector, codigoBodega, fechaInicio, fechaFin, usuario, agrupadoPor);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteCostosMensuales")
    public @ResponseBody
    String exportarReporteCostosMensuales(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<String> columnas = UtilsJSON.jsonToList(String.class, map.get("columnas"));
        List<String[]> datos = UtilsJSON.jsonToList(String[].class, map.get("datos"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String nombreSector = UtilsJSON.jsonToObjeto(String.class, map.get("nombreSector"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));

        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteCostosMensuales(usuarioEmpresaReporteTO, columnas, datos, sector, nombreSector, bodega, desde, hasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getConsumoPorFechaDesglosado")
    public RespuestaWebTO getConsumoPorFechaDesglosado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            RetornoTO respues = corridaService.getConsumoPorFechaDesglosado(empresa, codigoSector, fechaInicio, fechaFin, usuario);
            if (respues.getColumnas() != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteConsumosFechaDesglosado")
    public @ResponseBody
    String exportarReporteConsumosFechaDesglosado(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<String> columnas = UtilsJSON.jsonToList(String.class, map.get("columnas"));
        List<String[]> datos = UtilsJSON.jsonToList(String[].class, map.get("datos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        try {
            respuesta = reporteProduccionService.exportarReporteConsumosFechaDesglosado(usuarioEmpresaReporteTO, codigoSector, fechaInicio, fechaFin, columnas, datos);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrdConsumosFechaTO")
    public RespuestaWebTO getPrdConsumosFechaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdConsumosTO> respues = corridaService.getPrdConsumosFechaTO(empresa, sector, fechaDesde, fechaHasta);
            if (respues != null && respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteConsumosFecha")
    public @ResponseBody
    String exportarReporteConsumosFecha(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String secCodigo = UtilsJSON.jsonToObjeto(String.class, parametros.get("secCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<PrdConsumosTO> prdConsumosTO = UtilsJSON.jsonToList(PrdConsumosTO.class, parametros.get("prdConsumosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.exportarReporteConsumosFecha(usuarioEmpresaReporteTO, secCodigo, fechaDesde, fechaHasta, prdConsumosTO);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrdConsumosPiscinaTO")
    public RespuestaWebTO getPrdConsumosPiscinaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdConsumosTO> respues = corridaService.getPrdConsumosPiscinaTO(empresa, sector, piscina, fechaDesde, fechaHasta);
            if (respues != null && respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/completarProductos")
    public RespuestaWebTO completarProductos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<PrdListaCostosDetalleCorridaTO> consumos = UtilsJSON.jsonToList(PrdListaCostosDetalleCorridaTO.class, map.get("consumos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdListaCostosDetalleCorridaTO> respues = corridaService.completarProductos(consumos, sisInfoTO.getEmpresa());
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteConsumosPiscina")
    public @ResponseBody
    String exportarReporteConsumosPiscina(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String codigoPiscina = UtilsJSON.jsonToObjeto(String.class, map.get("codigoPiscina"));
        String numeroCorrida = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCorrida"));
        String hectareas = UtilsJSON.jsonToObjeto(String.class, map.get("hectareas"));
        Integer numLarvas = UtilsJSON.jsonToObjeto(Integer.class, map.get("numLarvas"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String nombreSector = UtilsJSON.jsonToObjeto(String.class, map.get("nombreSector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        boolean ordenProduccion = UtilsJSON.jsonToObjeto(boolean.class, map.get("ordenProduccion"));
        List<PrdConsumosTO> prdConsumosTO = UtilsJSON.jsonToList(PrdConsumosTO.class, map.get("prdConsumosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteConsumosPiscina(usuarioEmpresaReporteTO, codigoSector, nombreSector, piscina,
                    codigoPiscina, numeroCorrida, hectareas, numLarvas, fechaDesde, fechaHasta, prdConsumosTO, ordenProduccion);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getConsumoPorPiscinaPeriodo")
    public RespuestaWebTO getConsumoPorPiscinaPeriodo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
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
            RetornoTO respues = corridaService.getConsumoPorPiscinaPeriodo(empresa, codigoSector, numeroPiscina, fechaInicio,
                    fechaFin, periodo, usuario);
            if (respues.getColumnas() != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteConsumoPiscinaPeriodo")
    public @ResponseBody
    String exportarReporteConsumoPiscinaPeriodo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String numeroPiscina = UtilsJSON.jsonToObjeto(String.class, map.get("numeroPiscina"));
        String codigoCorrida = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCorrida"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String nombreSector = UtilsJSON.jsonToObjeto(String.class, map.get("nombreSector"));
        String numeroLarvas = UtilsJSON.jsonToObjeto(String.class, map.get("numeroLarvas"));
        String numeroHectareas = UtilsJSON.jsonToObjeto(String.class, map.get("numeroHectareas"));
        List<String> columnas = UtilsJSON.jsonToList(String.class, map.get("columnas"));
        List<String[]> datos = UtilsJSON.jsonToList(String[].class, map.get("datos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String titulo = "Reporte consumos por piscina periodo";
        try {
            respuesta = reporteProduccionService.exportarReporteDinamicoConsumoPiscinaPeriodo(usuarioEmpresaReporteTO, codigoSector, nombreSector, numeroPiscina, codigoCorrida,
                    periodo, fechaInicio, fechaFin, numeroLarvas, numeroHectareas, titulo, columnas, datos);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getUtilidadDiariaCorrida")
    public RespuestaWebTO getUtilidadDiariaCorrida(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String corrida = UtilsJSON.jsonToObjeto(String.class, map.get("corrida"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdUtilidadDiariaCorridaTO> respues = corridaService.getUtilidadDiariaCorrida(empresa, sector, piscina, corrida);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getConsumosMensuales")
    public RespuestaWebTO getConsumosMensuales(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String codigoBodega = UtilsJSON.jsonToObjeto(String.class, map.get("codigoBodega"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            RetornoTO respues = corridaService.getConsumosMensuales(empresa, codigoSector, codigoBodega, fechaInicio, fechaFin, usuario);
            if (respues.getColumnas() != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getConsumosDiarios")
    public RespuestaWebTO getConsumosDiarios(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String codigoBodega = UtilsJSON.jsonToObjeto(String.class, map.get("codigoBodega"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        String piscinaNumero = UtilsJSON.jsonToObjeto(String.class, map.get("piscinaNumero"));
        String codigoProducto = UtilsJSON.jsonToObjeto(String.class, map.get("codigoProducto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = corridaService.getConsumosDiarios(empresa, codigoSector, codigoBodega, fechaInicio, fechaFin, usuario, piscinaNumero, codigoProducto);
            if (respues.get("columnas") != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getCostosDiarios")
    public RespuestaWebTO getCostosDiarios(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String codigoBodega = UtilsJSON.jsonToObjeto(String.class, map.get("codigoBodega"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        String piscinaNumero = UtilsJSON.jsonToObjeto(String.class, map.get("piscinaNumero"));
        String codigoProducto = UtilsJSON.jsonToObjeto(String.class, map.get("codigoProducto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = corridaService.getCostosDiarios(empresa, codigoSector, codigoBodega, fechaInicio, fechaFin, usuario, piscinaNumero, codigoProducto);
            if (respues.get("columnas") != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteConsumosMensuales")
    public @ResponseBody
    String exportarReporteConsumosMensuales(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<String> columnas = UtilsJSON.jsonToList(String.class, map.get("columnas"));
        List<String[]> datos = UtilsJSON.jsonToList(String[].class, map.get("datos"));
        String nombreSector = UtilsJSON.jsonToObjeto(String.class, map.get("nombreSector"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String titulo = "Reporte de consumos mensuales";
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteDinamicoConsumosMensuales(usuarioEmpresaReporteTO, titulo, columnas, datos, nombreSector, bodega, desde, hasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteConsumosDiarios")
    public @ResponseBody
    String exportarReporteConsumosDiarios(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<String> columnas = UtilsJSON.jsonToList(String.class, map.get("columnas"));
        List<Object> datos = UtilsJSON.jsonToList(Object.class, map.get("datos"));
        String nombreSector = UtilsJSON.jsonToObjeto(String.class, map.get("nombreSector"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        boolean esCosto = UtilsJSON.jsonToObjeto(boolean.class, map.get("esCosto"));
        String titulo = esCosto ? "Reporte de costos diarios" : "Reporte de consumos diarios";
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteConsumosDiarios(usuarioEmpresaReporteTO, titulo, columnas, datos, nombreSector, bodega, desde, hasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaSectorConHectareaje")
    public RespuestaWebTO getListaSectorConHectareaje(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdListaSectorConHectareajeTO> respues = sectorService.getListaSectorConHectareaje(empresa, fecha);
            if (respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
                resp.setExtraInfo(false);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
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
    public RespuestaWebTO insertarPrdLiquidacionMotivoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionMotivoTO prdLiquidacionMotivoTO = UtilsJSON.jsonToObjeto(PrdLiquidacionMotivoTO.class, map.get("prdLiquidacionMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = liquidacionMotivoService.insertarPrdLiquidacionMotivoTO(prdLiquidacionMotivoTO, sisInfoTO);
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

    // liquidacion talla o talla de pesca
    @RequestMapping("/getListaPrdLiquidacionTalla")
    public RespuestaWebTO getListaPrdLiquidacionTalla(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean presupuestoPesca = UtilsJSON.jsonToObjeto(boolean.class, map.get("presupuestoPesca"));
        boolean inactivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdTalla> respues = tallaService.getListaPrdLiquidacionTalla(empresa, presupuestoPesca, inactivos);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    // listar tallas
    @RequestMapping("/getListaPrdLiquidacionTallaDetalle")
    public RespuestaWebTO getListaPrdLiquidacionTallaDetalle(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdTalla> respues = tallaService.getListaPrdLiquidacionTallaDetalle(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePrdLiquidacionTallaTO")
    public @ResponseBody
    String generarReportePrdLiquidacionTallaTO(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportLiquidacionTalla.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdTalla> listado = UtilsJSON.jsonToList(PrdTalla.class, parametros.get("ListadoLiquidacionTalla"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePrdLiquidacionTallaTO(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReportePrdLiquidacionTallaTO")
    public @ResponseBody
    String exportarReportePrdLiquidacionTallaTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<PrdTalla> listado = UtilsJSON.jsonToList(PrdTalla.class, map.get("ListadoLiquidacionTalla"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReportePrdLiquidacionTallaTO(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarPrdLiquidacionTalla")
    public RespuestaWebTO insertarPrdLiquidacionTalla(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdTalla prdLiquidacionTalla = UtilsJSON.jsonToObjeto(PrdTalla.class, map.get("prdLiquidacionTalla"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = tallaService.insertarPrdLiquidacionTalla(prdLiquidacionTalla, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarPrdLiquidacionTalla")
    public RespuestaWebTO modificarPrdLiquidacionTalla(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdTalla prdLiquidacionTalla = UtilsJSON.jsonToObjeto(PrdTalla.class, map.get("prdLiquidacionTalla"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = tallaService.modificarPrdLiquidacionTalla(prdLiquidacionTalla, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoPrdLiquidacionTalla")
    public RespuestaWebTO modificarEstadoPrdLiquidacionTalla(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdTalla prdLiquidacionTalla = UtilsJSON.jsonToObjeto(PrdTalla.class, map.get("prdLiquidacionTalla"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = tallaService.modificarEstadoPrdLiquidacionTalla(prdLiquidacionTalla, estado, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarPrdLiquidacionTalla")
    public RespuestaWebTO eliminarPrdLiquidacionTalla(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdTalla prdLiquidacionTalla = UtilsJSON.jsonToObjeto(PrdTalla.class, map.get("prdLiquidacionTalla"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = tallaService.eliminarPrdLiquidacionTalla(prdLiquidacionTalla, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
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

    // CRUD PRODUCTO DE PESCA
    @RequestMapping("/getListaPrdLiquidacionProductos")
    public RespuestaWebTO getListaPrdLiquidacionProductos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        boolean inactivos = UtilsJSON.jsonToObjeto(Boolean.class, map.get("inactivos"));
        try {
            List<PrdProducto> respues = prdProductoService.getListaPrdLiquidacionProductos(empresa, inactivos);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarPrdLiquidacionProductoTO")
    public RespuestaWebTO insertarPrdLiquidacionProductoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionProductoTO prdLiquidacionProductoTO = UtilsJSON.jsonToObjeto(PrdLiquidacionProductoTO.class,
                map.get("prdLiquidacionProductoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = prdProductoService.insertarPrdLiquidacionProductoTO(prdLiquidacionProductoTO, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarPrdLiquidacionProductoTO")
    public RespuestaWebTO modificarPrdLiquidacionProductoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionProductoTO prdLiquidacionProductoTO = UtilsJSON.jsonToObjeto(PrdLiquidacionProductoTO.class,
                map.get("prdLiquidacionProductoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = prdProductoService.modificarPrdLiquidacionProductoTO(prdLiquidacionProductoTO, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoPrdLiquidacionProductoTO")
    public RespuestaWebTO modificarEstadoPrdLiquidacionProductoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionProductoTO prdLiquidacionProductoTO = UtilsJSON.jsonToObjeto(PrdLiquidacionProductoTO.class,
                map.get("prdLiquidacionProductoTO"));
        boolean estado = UtilsJSON.jsonToObjeto(Boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = prdProductoService.modificarEstadoPrdLiquidacionProductoTO(prdLiquidacionProductoTO, estado, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarPrdLiquidacionProductoTO")
    public RespuestaWebTO eliminarPrdLiquidacionProductoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionProductoTO prdLiquidacionProductoTO = UtilsJSON.jsonToObjeto(PrdLiquidacionProductoTO.class,
                map.get("prdLiquidacionProductoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = prdProductoService.eliminarPrdLiquidacionProductoTO(prdLiquidacionProductoTO, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePrdLiquidacionProductoTO")
    public @ResponseBody
    String generarReportePrdLiquidacionProductoTO(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportLiquidacionProducto.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdProducto> listado = UtilsJSON.jsonToList(PrdProducto.class, parametros.get("ListadoProductoPesca"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePrdLiquidacionProductoTO(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReportePrdLiquidacionProductoTO")
    public @ResponseBody
    String exportarReportePrdLiquidacionProductoTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<PrdProducto> listado = UtilsJSON.jsonToList(PrdProducto.class, map.get("ListadoProductoPesca"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReportePrdLiquidacionProductoTO(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
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
    public RespuestaWebTO getFunAnalisisPesos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdListaFunAnalisisPesosTO> respues = corridaService.getFunAnalisisPesos(empresa, sector, fecha);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
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
        String nomSector = UtilsJSON.jsonToObjeto(String.class, map.get("nomSector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return corridaService.getListaConsultaGrameajePromedioPorPiscinaTOs(empresa, sector, nomSector);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoGramaje")
    public String generarReporteListadoGramaje(HttpServletResponse response, @RequestBody String json) {
        String nombreReporte = "reportListadoGrameaje.jrxml";
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String codigoPiscina = UtilsJSON.jsonToObjeto(String.class, map.get("codigoPiscina"));
        String codigoCorrida = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCorrida"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<PrdListadoGrameajeTO> prdListadoGrameajeTO = UtilsJSON.jsonToList(PrdListadoGrameajeTO.class,
                map.get("prdListadoGrameajeTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.generarReporteListadoGramaje(usuarioEmpresaReporteTO, codigoSector,
                    codigoPiscina, codigoCorrida, fechaDesde, fechaHasta, prdListadoGrameajeTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteListadoGramaje")
    public @ResponseBody
    String exportarReporteListadoGramaje(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String codigoPiscina = UtilsJSON.jsonToObjeto(String.class, map.get("codigoPiscina"));
        String codigoCorrida = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCorrida"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        boolean esSamaniego = UtilsJSON.jsonToObjeto(boolean.class, map.get("esSamaniego"));
        List<PrdListadoGrameajeTO> prdListadoGrameajeTO = UtilsJSON.jsonToList(PrdListadoGrameajeTO.class,
                map.get("prdListadoGrameajeTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteGramaje(usuarioEmpresaReporteTO, codigoSector, codigoPiscina,
                    codigoCorrida, prdListadoGrameajeTO, fechaDesde, fechaHasta, esSamaniego);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoFunAnalisisPesos")
    public String generarReporteListadoFunAnalisisPesos(HttpServletResponse response, @RequestBody String json) {
        String nombreReporte = "reporteListadoFunAnalisisPesos.jrxml";
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<PrdListaFunAnalisisPesosTO> prdListaFunAnalisisPesosTO = UtilsJSON.jsonToList(PrdListaFunAnalisisPesosTO.class, map.get("prdListaFunAnalisisPesosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.generarReporteListadoFunAnalisisPesos(usuarioEmpresaReporteTO, codigoSector, fechaHasta, prdListaFunAnalisisPesosTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteListadoFunAnalisisPesos")
    public @ResponseBody
    String exportarReporteListadoFunAnalisisPesos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String nomSector = UtilsJSON.jsonToObjeto(String.class, map.get("nombreSector"));
        List<PrdListaFunAnalisisPesosTO> prdListaFunAnalisisPesosTO = UtilsJSON.jsonToList(PrdListaFunAnalisisPesosTO.class, map.get("prdListaFunAnalisisPesosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteListadoFunAnalisisPesos(usuarioEmpresaReporteTO, codigoSector, nomSector, prdListaFunAnalisisPesosTO, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConsumoFecha")
    public String generarReporteConsumoFecha(HttpServletResponse response, @RequestBody String json) {
        String nombreReporte = "reportConsumoPorFecha.jrxml";
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String secCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("secCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<PrdConsumosTO> prdConsumosTO = UtilsJSON.jsonToList(PrdConsumosTO.class, map.get("prdConsumosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.generarReporteConsumoFecha(usuarioEmpresaReporteTO, secCodigo, fechaDesde,
                    fechaHasta, prdConsumosTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteUtilidadDiariaCorrida")
    public String generarReporteUtilidadDiariaCorrida(HttpServletResponse response, @RequestBody String json) {
        String nombreReporte = "reportUtilidadDiaria.jrxml";
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String corrida = UtilsJSON.jsonToObjeto(String.class, map.get("corrida"));
        List<PrdUtilidadDiariaCorridaTO> listUtilidad = UtilsJSON.jsonToList(PrdUtilidadDiariaCorridaTO.class, map.get("reporteUtilidadDiaria"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.generarReporteUtilidadDiariaCorrida(usuarioEmpresaReporteTO, sector, piscina, corrida, listUtilidad);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteUtilidadDiariaCorrida")
    public @ResponseBody
    String exportarReporteUtilidadDiariaCorrida(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String corrida = UtilsJSON.jsonToObjeto(String.class, map.get("corrida"));
        List<PrdUtilidadDiariaCorridaTO> listaResumenBiologico = UtilsJSON.jsonToList(PrdUtilidadDiariaCorridaTO.class, map.get("listaResumenBiologico"));
        List<PrdUtilidadDiariaCorridaTO> listaResumenFinanciero = UtilsJSON.jsonToList(PrdUtilidadDiariaCorridaTO.class, map.get("listaResumenFinanciero"));
        List<PrdUtilidadDiariaCorridaTO> listaConsumoBalanceado = UtilsJSON.jsonToList(PrdUtilidadDiariaCorridaTO.class, map.get("listaConsumoBalanceado"));
        List<PrdUtilidadDiariaCorridaTO> listUtilidad = UtilsJSON.jsonToList(PrdUtilidadDiariaCorridaTO.class, map.get("reporteUtilidadDiaria"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteUtilidadDiariaCorrida(usuarioEmpresaReporteTO, sector, piscina, corrida, listUtilidad, listaResumenBiologico, listaResumenFinanciero, listaConsumoBalanceado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConsumoPiscina")
    public String generarReporteConsumoPiscina(HttpServletResponse response, @RequestBody String json) {
        String nombreReporte = "reportConsumoPorPiscina.jrxml";
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
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
            respuesta = reporteProduccionService.generarReporteConsumoPiscina(usuarioEmpresaReporteTO, codigoSector,
                    codigoPiscina, numeroCorrida, hectareas, numLarvas, fechaDesde, fechaHasta, prdConsumosTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
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
    public String generarReporteEconomicoPorPiscinas(HttpServletResponse response, @RequestBody String json) {
        String nombreReporte = "reportCostoDetalleCorrida.jrxml";
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
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
            respuesta = reporteProduccionService.generarReporteEconomicoPorPiscinas(usuarioEmpresaReporteTO, codigoSector,
                    codigoPiscina, codigoCorrida, hectareas, numLarvas, fechaDesde, fechaHasta,
                    prdListaCostosDetalleCorridaTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteEconomicoPorPiscinas")
    public @ResponseBody
    String exportarReporteEconomicoPorPiscinas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String codigoPiscina = UtilsJSON.jsonToObjeto(String.class, map.get("codigoPiscina"));
        String codigoCorrida = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCorrida"));
        String hectareas = UtilsJSON.jsonToObjeto(String.class, map.get("hectareas"));
        Integer numLarvas = UtilsJSON.jsonToObjeto(Integer.class, map.get("numLarvas"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String nombreSector = UtilsJSON.jsonToObjeto(String.class, map.get("nombreSector"));
        boolean costoProduccion = UtilsJSON.jsonToObjeto(boolean.class, map.get("costoProduccion"));
        List<PrdListaCostosDetalleCorridaTO> prdListaCostosDetalleCorridaTO = UtilsJSON
                .jsonToList(PrdListaCostosDetalleCorridaTO.class, map.get("prdListaCostosDetalleCorridaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteEconomicoPorPiscinas(usuarioEmpresaReporteTO, codigoSector, nombreSector,
                    codigoPiscina, codigoCorrida, hectareas, numLarvas, fechaDesde, fechaHasta,
                    prdListaCostosDetalleCorridaTO, costoProduccion);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
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
    public String generarReporteCostoDetalleFecha(HttpServletResponse response, @RequestBody String json) {
        String nombreReporte = "reportCostoDetalleFecha.jrxml";
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ReporteCostoDetalleFecha> reporteCostoDetalleFecha = UtilsJSON.jsonToList(ReporteCostoDetalleFecha.class, map.get("reporteCostoDetalleFecha"));
        String tituloReporte = UtilsJSON.jsonToObjeto(String.class, map.get("tituloReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.generarReporteCostoDetalleFecha(usuarioEmpresaReporteTO, reporteCostoDetalleFecha, tituloReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCostoDetalleFecha")
    public @ResponseBody
    String exportarReporteCostoDetalleFecha(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<PrdCostoDetalleFechaTO> prdCostoDetalleFechaTO = UtilsJSON.jsonToList(PrdCostoDetalleFechaTO.class, map.get("prdCostoDetalleFechaTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String nombreSector = UtilsJSON.jsonToObjeto(String.class, map.get("nombreSector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteCostoDetalleFecha(usuarioEmpresaReporteTO, prdCostoDetalleFechaTO, fechaDesde, fechaHasta, nombreSector, sector);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReportePrdFunCostosPorFechaSimpleTO")
    public String generarReportePrdFunCostosPorFechaSimpleTO(HttpServletResponse response, @RequestBody String json) {
        String nombreReporte = "reporteFunCostosPorFechaSimple.jrxml";
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ReportePrdFunCostosPorFechaSimpleTO> reportePrdFunCostosPorFechaSimpleTOs = UtilsJSON.jsonToList(ReportePrdFunCostosPorFechaSimpleTO.class, map.get("reportePrdFunCostosPorFechaSimpleTOs"));
        String tituloReporte = UtilsJSON.jsonToObjeto(String.class, map.get("tituloReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.generarReportePrdFunCostosPorFechaSimpleTO(usuarioEmpresaReporteTO, reportePrdFunCostosPorFechaSimpleTOs, tituloReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReportePrdFunCostosPorFechaSimpleTO")
    public @ResponseBody
    String exportarReportePrdFunCostosPorFechaSimpleTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<PrdFunCostosPorFechaSimpleTO> reportePrdFunCostosPorFechaSimpleTOs = UtilsJSON.jsonToList(PrdFunCostosPorFechaSimpleTO.class, map.get("reportePrdFunCostosPorFechaSimpleTOs"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String nombreSector = UtilsJSON.jsonToObjeto(String.class, map.get("nombreSector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReportePrdFunCostosPorFechaSimpleTO(usuarioEmpresaReporteTO, reportePrdFunCostosPorFechaSimpleTOs,
                    fechaDesde, fechaHasta, sector, nombreSector);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
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

    @RequestMapping("/generarReporteLiquidacionMotivo")
    public @ResponseBody
    String generarReporteLiquidacionMotivo(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportLiquidacionMotivo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdLiquidacionMotivo> prdLiquidacionMotivo = UtilsJSON.jsonToList(PrdLiquidacionMotivo.class, parametros.get("prdLiquidacionMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.generarReporteLiquidacionMotivo(usuarioEmpresaReporteTO, prdLiquidacionMotivo);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteLiquidacionMotivo")
    public @ResponseBody
    String exportarReporteLiquidacionMotivo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<PrdLiquidacionMotivo> prdLiquidacionMotivo = UtilsJSON.jsonToList(PrdLiquidacionMotivo.class, map.get("prdLiquidacionMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteLiquidacionMotivo(usuarioEmpresaReporteTO, prdLiquidacionMotivo);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReportePreLiquidacionMotivo")
    public @ResponseBody
    String generarReportePreLiquidacionMotivo(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportPreLiquidacionMotivo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdPreLiquidacionMotivo> prdPreLiquidacionMotivo = UtilsJSON.jsonToList(PrdPreLiquidacionMotivo.class, parametros.get("prdPreLiquidacionMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.generarReportePreLiquidacionMotivo(usuarioEmpresaReporteTO, prdPreLiquidacionMotivo);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReportePreLiquidacionMotivo")
    public @ResponseBody
    String exportarReportePreLiquidacionMotivo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<PrdPreLiquidacionMotivo> prdPreLiquidacionMotivo = UtilsJSON.jsonToList(PrdPreLiquidacionMotivo.class, map.get("prdPreLiquidacionMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReportePreLiquidacionMotivo(usuarioEmpresaReporteTO, prdPreLiquidacionMotivo);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReportePresupuestoPesca")
    public @ResponseBody
    String generarReportePresupuestoPesca(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportPresupuestoPescaMotivo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdPresupuestoPescaMotivo> prdPresupuestoPescaMotivo = UtilsJSON.jsonToList(PrdPresupuestoPescaMotivo.class, parametros.get("prdPresupuestoPescaMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.generarReportePresupuestoPesca(usuarioEmpresaReporteTO, prdPresupuestoPescaMotivo);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReportePresupuestoPesca")
    public @ResponseBody
    String exportarReportePresupuestoPesca(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<PrdPresupuestoPescaMotivo> prdPresupuestoPescaMotivo = UtilsJSON.jsonToList(PrdPresupuestoPescaMotivo.class, map.get("prdPresupuestoPescaMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReportePresupuestoPesca(usuarioEmpresaReporteTO, prdPresupuestoPescaMotivo);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //Liquidacion
    @RequestMapping("/getListaPrdConsultaLiquidacion")
    public ResponseEntity getListaPrdConsultaLiquidacion(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, parametros.get("piscina"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, parametros.get("busqueda"));
        String nRegistros = UtilsJSON.jsonToObjeto(String.class, parametros.get("nRegistros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<ListaLiquidacionTO> respues = liquidacionService.getListaPrdConsultaLiquidacionTO(empresa, sector, piscina, busqueda, nRegistros);
            if (respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/getListaPrdConsultaLiquidacionTOSoloActivas")
    public ResponseEntity getListaPrdConsultaLiquidacionTOSoloActivas(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, parametros.get("cliente"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, parametros.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, parametros.get("numero"));
        String nRegistros = UtilsJSON.jsonToObjeto(String.class, parametros.get("nRegistros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<ListaLiquidacionTO> respues = liquidacionService.getListaPrdConsultaLiquidacionTOSoloActivas(empresa, proveedor, cliente, motivo, numero, nRegistros);
            if (respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/obtenerDatosParaLiquidacionPesca")
    public ResponseEntity obtenerDatosParaLiquidacionPesca(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, parametros.get("piscina"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            Map<String, Object> respues = liquidacionService.obtenerDatosParaLiquidacionPesca(empresa, piscina, sector);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/obtenerSiguienteLote")
    public ResponseEntity obtenerSiguienteLote(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, parametros.get("motivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            String lote = liquidacionService.obtenerSiguienteLote(empresa, motivo);
            if (lote != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(lote);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("00000001");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/getPrdLiquidacion")
    public ResponseEntity consultarLiquidacionPesca(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        PrdLiquidacionPK liquidacionPK = UtilsJSON.jsonToObjeto(PrdLiquidacionPK.class, parametros.get("liquidacionPK"));
        String accion = UtilsJSON.jsonToObjeto(String.class, parametros.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        String permitirMoficar = null;
        try {
            PrdLiquidacion respues = liquidacionService.getPrdLiquidacion(liquidacionPK);
            if (respues != null) {
                if (!accion.equals("Consultar")) {
                    permitirMoficar = liquidacionService.controlOpcionSegunPeriodoLiquidacion(respues);
                } else {
                    permitirMoficar = "TPermitir";
                }
                if (respues.getPrdPreLiquidacion() != null) {
                    respues.setPrdPreLiquidacion(new PrdPreLiquidacion(respues.getPrdPreLiquidacion().getPrdPreLiquidacionPK()));
                }
                if (respues.getPrdCorrida() == null) {
                    PrdCorrida c = liquidacionService.obtenerCorrida(liquidacionPK);
                    respues.setPrdCorrida(c);
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(permitirMoficar != null ? permitirMoficar.substring(1, permitirMoficar.length()) : "");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/desmayorizarPrdLiquidacion")
    public ResponseEntity desmayorizarPrdLiquidacion(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        PrdLiquidacionPK liquidacionPK = UtilsJSON.jsonToObjeto(PrdLiquidacionPK.class, parametros.get("liquidacionPK"));
        boolean entregaCasa = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("esEntregaCasa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        String respues = "";
        try {
            PrdLiquidacion liquidacion = liquidacionService.getPrdLiquidacion(liquidacionPK);
            String permitirMoficar = liquidacionService.controlOpcionSegunPeriodoLiquidacion(liquidacion);
            if (permitirMoficar.substring(0, 1).equals("T")) {
                respues = liquidacionService.desmayorizarPrdLiquidacion(liquidacionPK, entregaCasa, sisInfoTO);
            } else {
                respues = permitirMoficar;
            }

            if (respues.substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/desmayorizarPrdLiquidacionLote")
    public ResponseEntity desmayorizarPrdLiquidacionLote(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<ListaLiquidacionTO> listado = UtilsJSON.jsonToList(ListaLiquidacionTO.class, parametros.get("ListaLiquidacionTO"));
        String[] listaMensajes = new String[]{};
        ArrayList<String> listaMensajesEnviar = new ArrayList<>();
        try {
            String respues = liquidacionService.desmayorizarPrdLiquidacionLote(listado, empresa, sisInfoTO);
            listaMensajes = respues.substring(1).split("\\/");
            for (String listaMensaje : listaMensajes) {
                if (!listaMensaje.equals(".")) {
                    listaMensajesEnviar.add(listaMensaje.substring(1));
                }
            }

            if (respues.substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
                resp.setExtraInfo(listaMensajesEnviar);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/insertarModificarPrdLiquidacion")
    public ResponseEntity insertarModificarPrdLiquidacion(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        PrdLiquidacion prdLiquidacion = UtilsJSON.jsonToObjeto(PrdLiquidacion.class, parametros.get("prdLiquidacion"));
        boolean pendiente = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("pendiente"));
        List<PrdLiquidacionDetalle> listaPrdLiquidacionDetalle = UtilsJSON.jsonToList(PrdLiquidacionDetalle.class, parametros.get("listaPrdLiquidacionDetalle"));
        List<PrdLiquidacionDatosAdjuntos> listadoImagenes = UtilsJSON.jsonToList(PrdLiquidacionDatosAdjuntos.class, parametros.get("listaImagenes"));
        boolean entregaCasa = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("esEntregaCasa"));

        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            MensajeTO respues = liquidacionService.insertarModificarPrdLiquidacion(prdLiquidacion, listaPrdLiquidacionDetalle, listadoImagenes, entregaCasa, sisInfoTO);
            if (respues.getMensaje().substring(0, 1).equals("T")) {
                if (pendiente) {
                    Map<String, Object> campos = respues.getMap();
                    PrdLiquidacion liquidacionPesca = UtilsJSON.jsonToObjeto(PrdLiquidacion.class, campos.get("liquidacion"));
                    String desmayRespuesta = liquidacionService.desmayorizarPrdLiquidacion(liquidacionPesca.getPrdLiquidacionPK(), false, sisInfoTO);
                    if (desmayRespuesta.substring(0, 1).equals("T")) {
                        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                        resp.setOperacionMensaje(respues.getMensaje().substring(1, respues.getMensaje().length()));
                        resp.setExtraInfo(true);
                    } else {
                        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                        resp.setOperacionMensaje(desmayRespuesta.substring(1, desmayRespuesta.length()));
                    }
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(respues.getMensaje().substring(1, respues.getMensaje().length()));
                    resp.setExtraInfo(respues);
                }
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respues.getMensaje().substring(1, respues.getMensaje().length()));
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/anularRestaurarPrdLiquidacion")
    public ResponseEntity anularRestaurarPrdLiquidacion(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        PrdLiquidacionPK liquidacionPK = UtilsJSON.jsonToObjeto(PrdLiquidacionPK.class, parametros.get("liquidacionPK"));
        boolean anularRestaurar = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("anularRestaurar"));
        boolean entregaCasa = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("esEntregaCasa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            String respues = liquidacionService.anularRestaurarPrdLiquidacion(liquidacionPK, anularRestaurar, entregaCasa, sisInfoTO);
            if (respues.substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/generarReporteLiquidacionPescaListado")
    public @ResponseBody
    String generarReporteLiquidacionPescaListado(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportLiquidacionPescaListado.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<ListaLiquidacionTO> listado = UtilsJSON.jsonToList(ListaLiquidacionTO.class, parametros.get("ListaLiquidacionTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteLiquidacionPescaListado(usuarioEmpresaReporteTO, empresa, nombreReporte, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteLiquidacionPescaPorLote")
    public @ResponseBody
    String generarReporteLiquidacionPescaPorLote(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportLiquidacionPesca.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<ListaLiquidacionTO> listado = UtilsJSON.jsonToList(ListaLiquidacionTO.class, parametros.get("ListaLiquidacionTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePescaLiquidacionPorLote(usuarioEmpresaReporteTO, empresa, nombreReporte, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReportePescaLiquidacion")
    public @ResponseBody
    String exportarReportePescaLiquidacion(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ListaLiquidacionTO> listado = UtilsJSON.jsonToList(ListaLiquidacionTO.class, map.get("ListaLiquidacionTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina= UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReportePescaLiquidacion(usuarioEmpresaReporteTO, listado, sector, piscina);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBuscaObjLiquidacionPorLote")
    public ResponseEntity getBuscaObjLiquidacionPorLote(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String liqLote = UtilsJSON.jsonToObjeto(String.class, parametros.get("liqLote"));
        String liqEmpresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("liqEmpresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            PrdLiquidacionTO respues = liquidacionService.getBuscaObjLiquidacionPorLote(liqLote, liqEmpresa);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
//

    @RequestMapping("/consultarFechaMaxMinPesca")
    public ResponseEntity consultarFechaMaxMinPesca(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            String fechaServidor = corridaService.consultarFechaMaxMin(empresa, "PESCA");
            if (fechaServidor != null) {
                Map<String, Object> respuesta = new HashMap<>();
                Date fechaHasta = UtilsValidacion.fecha(fechaServidor.substring(fechaServidor.indexOf(',') + 1), "yyyy-MM-dd");
                respuesta.put("fechaHasta", fechaHasta);
                respuesta.put("fechaDesde", UtilsValidacion.fechaString_Date("01/01/" + fechaHasta.toString().split(" ")[5], "dd/MM/yyyy"));
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No existen datos en corrida");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/consultarFechaMaxMinSiembra")
    public ResponseEntity consultarFechaMaxMinSiembra(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            String fechaServidor = corridaService.consultarFechaMaxMin(empresa, "SIEMBRA");
            if (fechaServidor != null) {
                Map<String, Object> respuesta = new HashMap<>();
                Date fechaHasta = UtilsValidacion.fecha(fechaServidor.substring(fechaServidor.indexOf(',') + 1), "yyyy-MM-dd");
                respuesta.put("fechaHasta", fechaHasta);
                respuesta.put("fechaDesde", UtilsValidacion.fecha(fechaServidor.substring(0, fechaServidor.indexOf(',') - 1), "yyyy-MM-dd"));
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No existen datos en corrida");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    //Gramaje
    @RequestMapping("/insertarGrameajeListado")
    public ResponseEntity insertarGrameajeListado(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        List<PrdGrameaje> listaGrameajes = UtilsJSON.jsonToList(PrdGrameaje.class, parametros.get("listaGrameajes"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, parametros.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            boolean respues = grameajeService.insertarGrameajeListado(listaGrameajes, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Se ingresaron " + listaGrameajes.size() + " gramajes con fecha " + fecha);
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se pudo ingresar");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/eliminarGrameaje")
    public ResponseEntity eliminarGrameaje(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        PrdGrameaje prdGrameaje = UtilsJSON.jsonToObjeto(PrdGrameaje.class, parametros.get("prdGrameaje"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            String respues = grameajeService.eliminarGrameaje(prdGrameaje, sisInfoTO);
            if (respues.substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se pudo eliminar el gramaje");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    //Preliquidacion pesca
    @RequestMapping("/getListaPrdConsultaPreLiquidacion")
    public ResponseEntity getListaPrdConsultaPreLiquidacion(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, parametros.get("piscina"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, parametros.get("busqueda"));
        String nRegistros = UtilsJSON.jsonToObjeto(String.class, parametros.get("nRegistros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<ListaPreLiquidacionTO> respues = preLiquidacionService.getListaPrdConsultaPreLiquidacionTO(empresa, sector, piscina, busqueda, nRegistros);
            if (respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/getPrdPreLiquidacion")
    public ResponseEntity getPrdPreLiquidacion(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        PrdPreLiquidacionPK pk = UtilsJSON.jsonToObjeto(PrdPreLiquidacionPK.class, parametros.get("pk"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            PrdPreLiquidacion respues = preLiquidacionService.getPrdPreLiquidacion(pk);
            if (respues != null) {
                PrdCorrida c = preLiquidacionService.obtenerCorrida(pk, sisInfoTO);
                respues.setPrdCorrida(c);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No existe pre liquidación");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/obtenerDatosParaPreLiquidacionPesca")
    public ResponseEntity obtenerDatosParaPreLiquidacionPesca(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, parametros.get("piscina"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            Map<String, Object> respues = preLiquidacionService.obtenerDatosParaPreLiquidacionPesca(empresa, piscina, sector);
            if (respues != null) {
                List<PrdProducto> listaProducto = prdProductoService.getListaPrdLiquidacionProductos(empresa, false);
                respues.put("listadoProductos", listaProducto);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/getBuscaObjPreLiquidacionPorLote")
    public ResponseEntity getBuscaObjPreLiquidacionPorLote(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String liqLote = UtilsJSON.jsonToObjeto(String.class, parametros.get("liqLote"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            PrdPreLiquidacionTO respues = preLiquidacionService.getBuscaObjPreLiquidacionPorLote(liqLote);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/anularRestaurarPrdPreLiquidacion")
    public ResponseEntity anularRestaurarPrdPreLiquidacion(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        PrdPreLiquidacionPK pk = UtilsJSON.jsonToObjeto(PrdPreLiquidacionPK.class, parametros.get("pk"));
        boolean anularRestaurar = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("anularRestaurar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            String respues = preLiquidacionService.anularRestaurarPrdPreLiquidacion(pk, anularRestaurar);
            if (respues.substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/desmayorizarPrdPreLiquidacion")
    public ResponseEntity desmayorizarPrdPreLiquidacion(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        PrdPreLiquidacionPK pk = UtilsJSON.jsonToObjeto(PrdPreLiquidacionPK.class, parametros.get("pk"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            String respues = preLiquidacionService.desmayorizarPrdPreLiquidacion(pk);
            if (respues.substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se pudo desmayorizar");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/desmayorizarPrdPreLiquidacionLote")
    public ResponseEntity desmayorizarPrdPreLiquidacionLote(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<ListaPreLiquidacionTO> listado = UtilsJSON.jsonToList(ListaPreLiquidacionTO.class, parametros.get("ListaPreLiquidacionTO"));
        String[] listaMensajes = new String[]{};
        ArrayList<String> listaMensajesEnviar = new ArrayList<>();
        try {
            String respues = preLiquidacionService.desmayorizarPrdPreLiquidacionLote(listado, empresa);
            listaMensajes = respues.substring(1).split("\\/");
            for (String listaMensaje : listaMensajes) {
                if (!listaMensaje.equals(".")) {
                    listaMensajesEnviar.add(listaMensaje.substring(1));
                }
            }
            if (respues.substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
                resp.setExtraInfo(listaMensajesEnviar);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/insertarModificarPrdPreLiquidacion")
    public ResponseEntity insertarModificarPrdPreLiquidacion(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        PrdPreLiquidacion prdPreLiquidacion = UtilsJSON.jsonToObjeto(PrdPreLiquidacion.class, parametros.get("prdPreLiquidacion"));
        boolean pendiente = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("pendiente"));
        List<PrdPreLiquidacionDetalle> listaDetalle = UtilsJSON.jsonToList(PrdPreLiquidacionDetalle.class, parametros.get("listaPrdPreLiquidacionDetalle"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            MensajeTO respues = preLiquidacionService.insertarModificarPrdPreLiquidacion(prdPreLiquidacion, listaDetalle, sisInfoTO);
            if (respues.getMensaje().substring(0, 1).equals("T")) {
                Map<String, Object> campos = respues.getMap();
                PrdPreLiquidacion liquidacionPesca = UtilsJSON.jsonToObjeto(PrdPreLiquidacion.class, campos.get("liquidacion"));
                if (pendiente) {
                    String desmayRespuesta = preLiquidacionService.desmayorizarPrdPreLiquidacion(liquidacionPesca.getPrdPreLiquidacionPK());
                    if (desmayRespuesta.substring(0, 1).equals("T")) {
                        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                        resp.setOperacionMensaje(respues.getMensaje().substring(1, respues.getMensaje().length()));
                        resp.setExtraInfo(liquidacionPesca);
                    } else {
                        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                        resp.setOperacionMensaje(desmayRespuesta.substring(1, desmayRespuesta.length()));
                    }
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(respues.getMensaje().substring(1, respues.getMensaje().length()));
                    resp.setExtraInfo(liquidacionPesca);
                }
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respues.getMensaje().substring(1, respues.getMensaje().length()));
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/generarReportePreLiquidacionPescaListado")
    public @ResponseBody
    String generarReportePreLiquidacionPescaListado(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportPreLiquidacionPescaListado.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<ListaPreLiquidacionTO> listado = UtilsJSON.jsonToList(ListaPreLiquidacionTO.class, parametros.get("ListaPreLiquidacionTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePreLiquidacionPescaListado(usuarioEmpresaReporteTO, empresa, nombreReporte, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReportePreLiquidacionPescaPorLote")
    public @ResponseBody
    String generarReportePreLiquidacionPescaPorLote(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportPreLiquidacionPesca.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<ListaPreLiquidacionTO> listado = UtilsJSON.jsonToList(ListaPreLiquidacionTO.class, parametros.get("ListaPreLiquidacionTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePescaPreLiquidacionPorLote(usuarioEmpresaReporteTO, empresa, nombreReporte, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReportePescaPreLiquidacion")
    public @ResponseBody
    String exportarReportePescaPreLiquidacion(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ListaPreLiquidacionTO> listado = UtilsJSON.jsonToList(ListaPreLiquidacionTO.class, map.get("ListaPreLiquidacionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReportePescaPreLiquidacion(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerDetalleCuenta")
    public RespuestaWebTO obtenerDetalleCuenta(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = piscinaService.obtenerDetalleCuenta(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se han encontrado resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/sobrevivenciaPorDefecto")
    public RespuestaWebTO sobrevivenciaPorDefecto(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        try {
            List<PrdListaSobrevivenciaTO> respues = sobrevivenciaService.sobrevivenciaPorDefecto(empresa, sector);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se han encontrado resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //PROYECCION
    @RequestMapping("/getListaPrdProyeccion")
    public RespuestaWebTO getListaPrdProyeccion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        Date fecha = UtilsJSON.jsonToObjeto(Date.class, map.get("fecha"));
        try {
            List<PrdProyeccionTO> respues = proyeccionService.getListaPrdProyeccion(empresa, sector, fecha);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se han encontrado resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePrdProyeccion")
    public @ResponseBody
    String generarReportePrdProyeccion(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportProyeccionListado.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdProyeccionTO> listado = UtilsJSON.jsonToList(PrdProyeccionTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePrdProyeccion(usuarioEmpresaReporteTO, nombreReporte, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReportePrdProyeccion")
    public @ResponseBody
    String exportarReportePrdProyeccion(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<PrdProyeccionTO> listado = UtilsJSON.jsonToList(PrdProyeccionTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReportePrdProyeccion(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteConsumos")
    public @ResponseBody
    String exportarReporteConsumos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvListaConsultaConsumosTO> listado = UtilsJSON.jsonToList(InvListaConsultaConsumosTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteConsumos(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReportePrdCorridaMatricial")
    public RespuestaWebTO generarReportePrdCorridaMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportCorridas.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdCorrida> listado = UtilsJSON.jsonToList(PrdCorrida.class, parametros.get("listado"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, parametros.get("piscina"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePrdCorrida(usuarioEmpresaReporteTO, nombreReporte, listado, sector, piscina);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;

    }

    @RequestMapping("/generarReporteLiquidacionMotivoMatricial")
    public RespuestaWebTO generarReporteLiquidacionMotivoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdLiquidacionMotivo> prdLiquidacionMotivo = UtilsJSON.jsonToList(PrdLiquidacionMotivo.class, parametros.get("prdLiquidacionMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.generarReporteLiquidacionMotivo(usuarioEmpresaReporteTO, prdLiquidacionMotivo);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePreLiquidacionMotivoMatricial")
    public RespuestaWebTO generarReportePreLiquidacionMotivoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdPreLiquidacionMotivo> prdPreLiquidacionMotivo = UtilsJSON.jsonToList(PrdPreLiquidacionMotivo.class, parametros.get("prdPreLiquidacionMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.generarReportePreLiquidacionMotivo(usuarioEmpresaReporteTO, prdPreLiquidacionMotivo);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePresupuestoPescaMatricial")
    public RespuestaWebTO generarReportePresupuestoPescaMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdPresupuestoPescaMotivo> prdPresupuestoPescaMotivo = UtilsJSON.jsonToList(PrdPresupuestoPescaMotivo.class, parametros.get("prdPresupuestoPescaMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.generarReportePresupuestoPesca(usuarioEmpresaReporteTO, prdPresupuestoPescaMotivo);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteSobrevivenciaMatricial")
    public RespuestaWebTO generarReporteSobrevivenciaMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdListaSobrevivenciaTO> listado = UtilsJSON.jsonToList(PrdListaSobrevivenciaTO.class, parametros.get("ListadoSobrevivencia"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteSobrevivencia(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteEconomicoPorPiscinasMatricial")
    public RespuestaWebTO generarReporteEconomicoPorPiscinasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
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
            respuesta = reporteProduccionService.generarReporteEconomicoPorPiscinas(usuarioEmpresaReporteTO, codigoSector,
                    codigoPiscina, codigoCorrida, hectareas, numLarvas, fechaDesde, fechaHasta,
                    prdListaCostosDetalleCorridaTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePicinaMatricial")
    public RespuestaWebTO generarReportePicinaMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdListaPiscinaTO> listado = UtilsJSON.jsonToList(PrdListaPiscinaTO.class, parametros.get("ListadoPiscinas"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePicina(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePrdLiquidacionProductoTOMatricial")
    public RespuestaWebTO generarReportePrdLiquidacionProductoTOMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdProducto> listado = UtilsJSON.jsonToList(PrdProducto.class, parametros.get("ListadoProductoPesca"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePrdLiquidacionProductoTO(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteSectorMatricial")
    public RespuestaWebTO generarReporteSectorMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdListaSectorTO> listado = UtilsJSON.jsonToList(PrdListaSectorTO.class, parametros.get("ListadoSectores"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteSector(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePrdLiquidacionTallaTOMatricial")
    public RespuestaWebTO generarReportePrdLiquidacionTallaTOMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdTalla> listado = UtilsJSON.jsonToList(PrdTalla.class, parametros.get("ListadoLiquidacionTalla"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePrdLiquidacionTallaTO(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsumoFechaMatricial")
    public RespuestaWebTO generarReporteConsumoFechaMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String secCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("secCodigo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<PrdConsumosTO> prdConsumosTO = UtilsJSON.jsonToList(PrdConsumosTO.class, map.get("prdConsumosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.generarReporteConsumoFecha(usuarioEmpresaReporteTO, secCodigo, fechaDesde,
                    fechaHasta, prdConsumosTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsumoPiscinaMatricial")
    public RespuestaWebTO generarReporteConsumoPiscinaMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
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
            respuesta = reporteProduccionService.generarReporteConsumoPiscina(usuarioEmpresaReporteTO, codigoSector,
                    codigoPiscina, numeroCorrida, hectareas, numLarvas, fechaDesde, fechaHasta, prdConsumosTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePrdFunCostosPorFechaSimpleTOMatricial")
    public RespuestaWebTO generarReportePrdFunCostosPorFechaSimpleTOMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ReportePrdFunCostosPorFechaSimpleTO> reportePrdFunCostosPorFechaSimpleTOs = UtilsJSON.jsonToList(ReportePrdFunCostosPorFechaSimpleTO.class, map.get("reportePrdFunCostosPorFechaSimpleTOs"));
        String tituloReporte = UtilsJSON.jsonToObjeto(String.class, map.get("tituloReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.generarReportePrdFunCostosPorFechaSimpleTO(usuarioEmpresaReporteTO, reportePrdFunCostosPorFechaSimpleTOs, tituloReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCostoDetalleFechaMatricial")
    public RespuestaWebTO generarReporteCostoDetalleFechaMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ReporteCostoDetalleFecha> reporteCostoDetalleFecha = UtilsJSON.jsonToList(ReporteCostoDetalleFecha.class, map.get("reporteCostoDetalleFecha"));
        String tituloReporte = UtilsJSON.jsonToObjeto(String.class, map.get("tituloReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.generarReporteCostoDetalleFecha(usuarioEmpresaReporteTO, reporteCostoDetalleFecha, tituloReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoGramajeMatricial")
    public RespuestaWebTO generarReporteListadoGramajeMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String codigoPiscina = UtilsJSON.jsonToObjeto(String.class, map.get("codigoPiscina"));
        String codigoCorrida = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCorrida"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<PrdListadoGrameajeTO> prdListadoGrameajeTO = UtilsJSON.jsonToList(PrdListadoGrameajeTO.class,
                map.get("prdListadoGrameajeTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.generarReporteListadoGramaje(usuarioEmpresaReporteTO, codigoSector,
                    codigoPiscina, codigoCorrida, fechaDesde, fechaHasta, prdListadoGrameajeTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteResumenEconomicoPescaMatricial")
    public RespuestaWebTO generarReporteResumenEconomicoPescaMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdResumenPescaSiembraTO> listaPrdResumenPescaSiembraTO = UtilsJSON.jsonToList(PrdResumenPescaSiembraTO.class, parametros.get("listaPrdResumenPescaSiembraTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteResumenEconomicoPesca(usuarioEmpresaReporteTO, listaPrdResumenPescaSiembraTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteUtilidadDiariaCorridaMatricial")
    public RespuestaWebTO generarReporteUtilidadDiariaCorridaMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String corrida = UtilsJSON.jsonToObjeto(String.class, map.get("corrida"));
        List<PrdUtilidadDiariaCorridaTO> listUtilidad = UtilsJSON.jsonToList(PrdUtilidadDiariaCorridaTO.class, map.get("reporteUtilidadDiaria"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.generarReporteUtilidadDiariaCorrida(usuarioEmpresaReporteTO, sector, piscina, corrida, listUtilidad);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoFunAnalisisPesosMatricial")
    public RespuestaWebTO generarReporteListadoFunAnalisisPesosMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<PrdListaFunAnalisisPesosTO> prdListaFunAnalisisPesosTO = UtilsJSON.jsonToList(PrdListaFunAnalisisPesosTO.class, map.get("prdListaFunAnalisisPesosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.generarReporteListadoFunAnalisisPesos(usuarioEmpresaReporteTO, codigoSector, fechaHasta, prdListaFunAnalisisPesosTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;

    }

    @RequestMapping("/generarReporteResumenSiembraMatricial")
    public RespuestaWebTO generarReporteResumenSiembraMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdResumenCorridaTO> listaPrdListaResumenCorridaTO = UtilsJSON.jsonToList(PrdResumenCorridaTO.class, parametros.get("listaPrdListaResumenCorridaTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteResumenSiembra(usuarioEmpresaReporteTO, listaPrdListaResumenCorridaTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;

    }

    @RequestMapping("/generarReporteResumenPescaMatricial")
    public RespuestaWebTO generarReporteResumenPescaMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdResumenCorridaTO> listaPrdListaResumenCorridaTO = UtilsJSON.jsonToList(PrdResumenCorridaTO.class, parametros.get("listaPrdListaResumenCorridaTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteResumenPesca(usuarioEmpresaReporteTO, listaPrdListaResumenCorridaTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;

    }

    @RequestMapping("/generarReporteLiquidacionPescaListadoMatricial")
    public RespuestaWebTO generarReporteLiquidacionPescaListadoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportLiquidacionPescaListado.jrxml";
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<ListaLiquidacionTO> listado = UtilsJSON.jsonToList(ListaLiquidacionTO.class, parametros.get("ListaLiquidacionTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteLiquidacionPescaListado(usuarioEmpresaReporteTO, empresa, nombreReporte, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;

    }

    @RequestMapping("/generarReportePreLiquidacionPescaListadoMatricial")
    public RespuestaWebTO generarReportePreLiquidacionPescaListadoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportPreLiquidacionPescaListado.jrxml";
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<ListaPreLiquidacionTO> listado = UtilsJSON.jsonToList(ListaPreLiquidacionTO.class, parametros.get("ListaPreLiquidacionTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePreLiquidacionPescaListado(usuarioEmpresaReporteTO, empresa, nombreReporte, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;

    }

    @RequestMapping("/generarReportePescaPreLiquidacionPorLoteMatricial")
    public RespuestaWebTO generarReportePescaPreLiquidacionPorLoteMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportPreLiquidacionPesca.jrxml";
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<ListaPreLiquidacionTO> listado = UtilsJSON.jsonToList(ListaPreLiquidacionTO.class, parametros.get("ListaPreLiquidacionTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePescaPreLiquidacionPorLote(usuarioEmpresaReporteTO, empresa, nombreReporte, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;

    }

    @RequestMapping("/generarReportePescaLiquidacionPorLoteMatricial")
    public RespuestaWebTO generarReportePescaLiquidacionPorLoteMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportLiquidacionPesca.jrxml";
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<ListaLiquidacionTO> listado = UtilsJSON.jsonToList(ListaLiquidacionTO.class, parametros.get("ListaLiquidacionTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePescaLiquidacionPorLote(usuarioEmpresaReporteTO, empresa, nombreReporte, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;

    }

    //fun_liquidacion_detalle_producto
    @RequestMapping("/getListadoLiquidacionDetalleProducto")
    public RespuestaWebTO getListadoLiquidacionDetalleProducto(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String talla = UtilsJSON.jsonToObjeto(String.class, map.get("talla"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdLiquidacionDetalleProductoTO> respues = liquidacionService.getListadoLiquidacionDetalleProductoTO(empresa, sector, piscina, fechaDesde, fechaHasta, cliente, talla, tipo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //fun_liquidacion_detalle_producto
    @RequestMapping("/getListadoLiquidacionDetalleProductoEmpacadora")
    public RespuestaWebTO getListadoLiquidacionDetalleProductoEmpacadora(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        String talla = UtilsJSON.jsonToObjeto(String.class, map.get("talla"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String comisionista = UtilsJSON.jsonToObjeto(String.class, map.get("comisionista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdLiquidacionDetalleProductoEmpacadoraTO> respues = liquidacionService.getListadoLiquidacionDetalleProductoEmpacadoraTO(empresa, sector, piscina, fechaDesde, fechaHasta, proveedor, talla, tipo, comisionista);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //fun_preliquidacion_detalle_producto
    @RequestMapping("/getListadoPreLiquidacionDetalleProducto")
    public RespuestaWebTO getListadoPreLiquidacionDetalleProducto(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String talla = UtilsJSON.jsonToObjeto(String.class, map.get("talla"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdPreLiquidacionDetalleProductoTO> respues = preLiquidacionService.getListadoPreLiquidacionDetalleProductoTO(empresa, sector, piscina, fechaDesde, fechaHasta, cliente, talla, tipo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }
    //funcion liquidacion consolidando tallas//

    @RequestMapping("/getPrdFunLiquidacionConsolidandoTallasTO")
    public RespuestaWebTO getPrdFunLiquidacionConsolidandoTallasTO(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdFunLiquidacionConsolidandoTallasTO> respues = liquidacionService.getPrdFunLiquidacionConsolidandoTallasTO(empresa, desde, hasta, sector, cliente, piscina);
            if (respues != null && !respues.isEmpty() && respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //funcion Preliquidacion consolidando tallas//
    @RequestMapping("/getPrdFunPreLiquidacionConsolidandoTallasTO")
    public RespuestaWebTO getPrdFunPreLiquidacionConsolidandoTallasTO(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdFunPreLiquidacionConsolidandoTallasTO> respues = preLiquidacionService.getPrdFunPreLiquidacionConsolidandoTallasTO(empresa, desde, hasta, sector, cliente, piscina);
            if (respues != null && !respues.isEmpty() && respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //funcion liquidacion consolidando clientes//
    @RequestMapping("/getListadoLiquidacionConsolidandoClientes")
    public RespuestaWebTO getListadoLiquidacionConsolidandoClientes(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdLiquidacionConsolidandoClientesTO> respues = liquidacionService.getListadoLiquidacionConsolidandoClientesTO(empresa, sector, fechaDesde, fechaHasta, cliente);
            if (respues != null && !respues.isEmpty() && respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //funcion liquidacion consolidando proveedores//
    @RequestMapping("/getListadoLiquidacionConsolidandoProveedores")
    public RespuestaWebTO getListadoLiquidacionConsolidandoProveedores(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        String comisionista = UtilsJSON.jsonToObjeto(String.class, map.get("comisionista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdLiquidacionConsolidandoProveedoresTO> respues = liquidacionService.getListadoLiquidacionConsolidandoProveedoresTO(empresa, sector, fechaDesde, fechaHasta, proveedor, comisionista);
            if (respues != null && !respues.isEmpty() && respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //funcion Preliquidacion consolidando clientes//
    @RequestMapping("/getListadoPreLiquidacionConsolidandoClientes")
    public RespuestaWebTO getListadoPreLiquidacionConsolidandoClientes(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdPreLiquidacionConsolidandoClientesTO> respues = preLiquidacionService.getListadoPreLiquidacionConsolidandoClientesTO(empresa, sector, fechaDesde, fechaHasta, cliente);
            if (respues != null && !respues.isEmpty() && respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //listar liquidacioon para consulta
    @RequestMapping("/getListadoLiquidacionConsultas")
    public RespuestaWebTO getListadoLiquidacionConsolidandoConsulta(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        boolean incluirAnuladas = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirAnuladas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        try {
            List<PrdLiquidacionConsultaTO> respuesta = liquidacionService.getListadoLiquidacionConsultasTO(empresa, sector, piscina, fechaDesde, fechaHasta, incluirAnuladas);
            if (respuesta != null && !respuesta.isEmpty() && respuesta.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //listar liquidacioon para consulta
    @RequestMapping("/getListadoLiquidacionConsultasEmpacadora")
    public RespuestaWebTO getListadoLiquidacionConsolidandoConsultaEmpadora(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        boolean incluirAnuladas = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirAnuladas"));
        String comisionista = UtilsJSON.jsonToObjeto(String.class, map.get("comisionista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        try {
            List<PrdLiquidacionConsultaEmpacadoraTO> respuesta = liquidacionService.getListadoLiquidacionConsultasEmpacadoraTO(empresa, sector, piscina, fechaDesde, fechaHasta, proveedor, incluirAnuladas, comisionista);
            if (respuesta != null && !respuesta.isEmpty() && respuesta.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //listar Preliquidacioon para consulta
    @RequestMapping("/getListadoPreLiquidacionConsultas")
    public RespuestaWebTO getListadoPreLiquidacionConsolidandoConsulta(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        boolean incluirAnuladas = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirAnuladas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        try {
            List<PrdPreLiquidacionConsultaTO> respuesta = preLiquidacionService.getListadoPreLiquidacionConsultasTO(empresa, sector, piscina, fechaDesde, fechaHasta, incluirAnuladas);
            if (respuesta != null && !respuesta.isEmpty() && respuesta.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //GENERACION DE REPORTES PDF
    //reporte matricial
    @RequestMapping("/generarReporteLiquidacionDetalleProductoMatricial")
    public RespuestaWebTO generarReporteLiquidacionDetalleProductoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdLiquidacionDetalleProductoTO> listado = UtilsJSON.jsonToList(PrdLiquidacionDetalleProductoTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteLiquidacionDetalleProducto(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //reporte matricial
    @RequestMapping("/generarReporteLiquidacionDetalleProductoEmpacadoraMatricial")
    public RespuestaWebTO generarReporteLiquidacionDetalleProductoEmpacadoraMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdLiquidacionDetalleProductoEmpacadoraTO> listado = UtilsJSON.jsonToList(PrdLiquidacionDetalleProductoEmpacadoraTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteLiquidacionDetalleProductoEmpacadora(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteLiquidacionDetalleProducto")
    public @ResponseBody
    String generarReporteLiquidacionDetalleProducto(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportLiquidacionDetalleProducto.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdLiquidacionDetalleProductoTO> listado = UtilsJSON.jsonToList(PrdLiquidacionDetalleProductoTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteLiquidacionDetalleProducto(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //reporte matricial preliquidacion
    @RequestMapping("/generarReportePreLiquidacionDetalleProductoMatricial")
    public RespuestaWebTO generarReportePreLiquidacionDetalleProductoMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdPreLiquidacionDetalleProductoTO> listado = UtilsJSON.jsonToList(PrdPreLiquidacionDetalleProductoTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePreLiquidacionDetalleProducto(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePreLiquidacionDetalleProducto")
    public @ResponseBody
    String generarReportePreLiquidacionDetalleProducto(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportPreLiquidacionDetalleProducto.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdPreLiquidacionDetalleProductoTO> listado = UtilsJSON.jsonToList(PrdPreLiquidacionDetalleProductoTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePreLiquidacionDetalleProducto(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //reporte liquidacion consolidando tallas
    //reporte matricial
    @RequestMapping("/generarReporteLiquidacionConsolidandoTallasMatricial")
    public RespuestaWebTO generarReporteLiquidacionConsolidandoTallasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdFunLiquidacionConsolidandoTallasTO> listado = UtilsJSON.jsonToList(PrdFunLiquidacionConsolidandoTallasTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteLiquidacionConsolidandoTallas(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePreLiquidacionConsolidandoTallasMatricial")
    public RespuestaWebTO generarReportePreLiquidacionConsolidandoTallasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdFunPreLiquidacionConsolidandoTallasTO> listado = UtilsJSON.jsonToList(PrdFunPreLiquidacionConsolidandoTallasTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePreLiquidacionConsolidandoTallas(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePreLiquidacionConsolidandoTallas")
    public @ResponseBody
    String generarReportePreLiquidacionConsolidandoTallas(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportPreLiquidacionConsolidandoTallas.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdFunPreLiquidacionConsolidandoTallasTO> listado = UtilsJSON.jsonToList(PrdFunPreLiquidacionConsolidandoTallasTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePreLiquidacionConsolidandoTallas(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //metodo imprimir
    @RequestMapping("/generarReporteLiquidacionConsolidandoTallas")
    public @ResponseBody
    String generarReporteLiquidacionConsolidandoTallas(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportLiquidacionConsolidandoTallas.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdFunLiquidacionConsolidandoTallasTO> listado = UtilsJSON.jsonToList(PrdFunLiquidacionConsolidandoTallasTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteLiquidacionConsolidandoTallas(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //exportar excel
    @RequestMapping("/exportarReporteLiquidacionConsolidandoTallas")
    public @ResponseBody
    String exportarReporteLiquidacionConsolidandoTallas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteConsolidandoTallas(usuarioEmpresaReporteTO, map);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //exportar excel
    @RequestMapping("/exportarReportePreLiquidacionConsolidandoTallas")
    public @ResponseBody
    String exportarReportePreLiquidacionConsolidandoTallas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReportePreConsolidandoTallas(usuarioEmpresaReporteTO, map);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //expórtar
    @RequestMapping("/exportarReporteLiquidacionDetalleProductos")
    public @ResponseBody
    String exportarReporteLiquidacionDetalleProductos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteLiquidacionDetalleProductos(usuarioEmpresaReporteTO, map);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //expórtar
    @RequestMapping("/exportarReporteLiquidacionDetalleProductosEmpacadora")
    public @ResponseBody
    String exportarReporteLiquidacionDetalleProductosEmpacadora(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteLiquidacionDetalleProductosEmpacadora(usuarioEmpresaReporteTO, map);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //Exportar preLiquidacion Detalle producto
    @RequestMapping("/exportarReportePreLiquidacionDetalleProductos")
    public @ResponseBody
    String exportarReportePreLiquidacionDetalleProductos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReportePreLiquidacionDetalleProductos(usuarioEmpresaReporteTO, map);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //reporteClientesPDF
    @RequestMapping("/generarReporteLiquidacionConsolidandoClientesMatricial")
    public RespuestaWebTO generarReporteLiquidacionConsolidandoClientesMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdLiquidacionConsolidandoClientesTO> listado = UtilsJSON.jsonToList(PrdLiquidacionConsolidandoClientesTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteLiquidacionConsolidandoClientes(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //reporteClientesPDF
    @RequestMapping("/generarReporteLiquidacionConsolidandoClientes")
    public @ResponseBody
    String generarReporteLiquidacionConsolidandoClientes(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportLiquidacionConsolidandoClientes.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdLiquidacionConsolidandoClientesTO> listado = UtilsJSON.jsonToList(PrdLiquidacionConsolidandoClientesTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteLiquidacionConsolidandoClientes(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //Reporte Proveedores
    @RequestMapping("/generarReporteLiquidacionConsolidandoProveedoresMatricial")
    public RespuestaWebTO generarReporteLiquidacionConsolidandoProveedoresMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdLiquidacionConsolidandoProveedoresTO> listado = UtilsJSON.jsonToList(PrdLiquidacionConsolidandoProveedoresTO.class, parametros.get("listado"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String comisionista = UtilsJSON.jsonToObjeto(String.class, parametros.get("comisionista"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteLiquidacionConsolidandoProveedores(usuarioEmpresaReporteTO, listado, sector, proveedor, fechaDesde, fechaHasta, comisionista);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteLiquidacionConsolidandoProveedores")
    public @ResponseBody
    String generarReporteLiquidacionConsolidandoProveedore(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportLiquidacionConsolidandoProveedores.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdLiquidacionConsolidandoProveedoresTO> listado = UtilsJSON.jsonToList(PrdLiquidacionConsolidandoProveedoresTO.class, parametros.get("listado"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String comisionista = UtilsJSON.jsonToObjeto(String.class, parametros.get("comisionista"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteLiquidacionConsolidandoProveedores(usuarioEmpresaReporteTO, listado, sector, proveedor, fechaDesde, fechaHasta, comisionista);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //exportarClientesExcel
    @RequestMapping("/exportarReporteLiquidacionConsolidandoClientes")
    public @ResponseBody
    String exportarReporteLiquidacionConsolidandoClientes(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteLiquidacionConsolidandoClientes(usuarioEmpresaReporteTO, map);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //exportarProveedoresExcel
    @RequestMapping("/exportarReporteLiquidacionConsolidandoProveedores")
    public @ResponseBody
    String exportarReporteLiquidacionConsolidandoProveedores(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteLiquidacionConsolidandoProveedores(usuarioEmpresaReporteTO, map);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //reportePreLiquidacionClientesPDF
    @RequestMapping("/generarReportePreLiquidacionConsolidandoClientesMatricial")
    public RespuestaWebTO generarReportePreLiquidacionConsolidandoClientesMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdPreLiquidacionConsolidandoClientesTO> listado = UtilsJSON.jsonToList(PrdPreLiquidacionConsolidandoClientesTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePreLiquidacionConsolidandoClientes(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePreLiquidacionConsolidandoClientes")
    public @ResponseBody
    String generarReportePreLiquidacionConsolidandoClientes(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportPreLiquidacionConsolidandoClientes.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdPreLiquidacionConsolidandoClientesTO> listado = UtilsJSON.jsonToList(PrdPreLiquidacionConsolidandoClientesTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePreLiquidacionConsolidandoClientes(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //exportarClientesExcel
    @RequestMapping("/exportarReportePreLiquidacionConsolidandoClientes")
    public @ResponseBody
    String exportarReportePreLiquidacionConsolidandoClientes(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReportePreLiquidacionConsolidandoClientes(usuarioEmpresaReporteTO, map);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //reporteClientesPDF
    @RequestMapping("/generarReportePrdListadoFitoplanctonTOMatricial")
    public RespuestaWebTO generarReportePrdListadoFitoplanctonTOMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdListadoFitoplanctonTO> listado = UtilsJSON.jsonToList(PrdListadoFitoplanctonTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePrdListadoFitoplanctonTO(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //reporteClientesPDF
    @RequestMapping("/generarReportePrdListadoFitoplanctonTO")
    public @ResponseBody
    String generarReportePrdListadoFitoplanctonTO(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportPrdListadoFitoplancton.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdListadoFitoplanctonTO> listado = UtilsJSON.jsonToList(PrdListadoFitoplanctonTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePrdListadoFitoplanctonTO(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReportePrdListadoFitoplancton")
    public @ResponseBody
    String exportarReportePrdListadoFitoplancton(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReportePrdListadoFitoplancton(usuarioEmpresaReporteTO, map);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //reporteConsultasPDF
    @RequestMapping("/generarReporteLiquidacionConsultasMatricial")
    public RespuestaWebTO generarReporteLiquidacionConsultasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdLiquidacionConsultaTO> listado = UtilsJSON.jsonToList(PrdLiquidacionConsultaTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteLiquidacionConsultas(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //reporteConsultasPDF
    @RequestMapping("/generarReporteLiquidacionConsultas")
    public @ResponseBody
    String generarReporteLiquidacionConsultas(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportLiquidacionListado.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdLiquidacionConsultaTO> listado = UtilsJSON.jsonToList(PrdLiquidacionConsultaTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteLiquidacionConsultas(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //reporteConsultasEmpacadoraPDF
    @RequestMapping("/generarReporteLiquidacionConsultasEmpacadoraMatricial")
    public RespuestaWebTO generarReporteLiquidacionConsultasEmpacadoraMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdLiquidacionConsultaEmpacadoraTO> listado = UtilsJSON.jsonToList(PrdLiquidacionConsultaEmpacadoraTO.class, parametros.get("listado"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteLiquidacionConsultasEmpacadora(usuarioEmpresaReporteTO, listado, proveedor);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //reporteConsultasEmpacadoraPDF
    @RequestMapping("/generarReporteLiquidacionConsultasEmpacadora")
    public @ResponseBody
    String generarReporteLiquidacionConsultasEmpacadora(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportLiquidacionListadoEmpacadora.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdLiquidacionConsultaEmpacadoraTO> listado = UtilsJSON.jsonToList(PrdLiquidacionConsultaEmpacadoraTO.class, parametros.get("listado"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteLiquidacionConsultasEmpacadora(usuarioEmpresaReporteTO, listado, proveedor);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //reporteConsultasPDF
    @RequestMapping("/generarReportePreLiquidacionConsultasMatricial")
    public RespuestaWebTO generarReportePreLiquidacionConsultasMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdPreLiquidacionConsultaTO> listado = UtilsJSON.jsonToList(PrdPreLiquidacionConsultaTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePreLiquidacionConsultas(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePreLiquidacionConsultas")
    public @ResponseBody
    String generarReportePreLiquidacionConsultas(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportPreLiquidacionListado.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdPreLiquidacionConsultaTO> listado = UtilsJSON.jsonToList(PrdPreLiquidacionConsultaTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePreLiquidacionConsultas(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //exportarConsultasExcel
    @RequestMapping("/exportarReporteLiquidacionConsultas")
    public @ResponseBody
    String exportarReporteLiquidacionConsultas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteLiquidacionConsultas(usuarioEmpresaReporteTO, map);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //exportarConsultasEmpacadora
    @RequestMapping("/exportarReporteLiquidacionConsultasEmpacadora")
    public @ResponseBody
    String exportarReporteLiquidacionConsultasEmpacadora(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteLiquidacionConsultasEmpacadora(usuarioEmpresaReporteTO, map);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //exportarConsultasExcel
    @RequestMapping("/exportarReportePreLiquidacionConsultas")
    public @ResponseBody
    String exportarReportePreLiquidacionConsultas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReportePreLiquidacionConsultas(usuarioEmpresaReporteTO, map);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //Liquidacion detalle
    @RequestMapping("/listarLiquidacionesDetalle")
    public RespuestaWebTO listarLiquidacionesDetalle(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdLiquidacionesDetalleTO> respues = liquidacionService.listarLiquidacionesDetalle(empresa, fechaDesde, fechaHasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //Liquidacion detalle
    @RequestMapping("/listarPreLiquidacionesDetalle")
    public RespuestaWebTO listarPreLiquidacionesDetalle(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdPreLiquidacionesDetalleTO> respues = preLiquidacionService.listarPreLiquidacionesDetalle(empresa, fechaDesde, fechaHasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePreLiquidacionesDetalleMatricial")
    public RespuestaWebTO generarReportePreLiquidacionesDetalleMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdPreLiquidacionesDetalleTO> listado = UtilsJSON.jsonToList(PrdPreLiquidacionesDetalleTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePreLiquidacionesDetalle(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePreLiquidacionesDetalle")
    public @ResponseBody
    String generarReportePreLiquidacionesDetalle(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportPreLiquidacionesDetalle.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdPreLiquidacionesDetalleTO> listado = UtilsJSON.jsonToList(PrdPreLiquidacionesDetalleTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReportePreLiquidacionesDetalle(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReportePreLiquidacionesDetalle")
    public @ResponseBody
    String exportarReportePreLiquidacionesDetalle(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReportePreLiquidacionesDetalle(usuarioEmpresaReporteTO, map);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteLiquidacionesDetalleMatricial")
    public RespuestaWebTO generarReporteLiquidacionesDetalleMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdLiquidacionesDetalleTO> listado = UtilsJSON.jsonToList(PrdLiquidacionesDetalleTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteLiquidacionesDetalle(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteLiquidacionesDetalle")
    public @ResponseBody
    String generarReporteLiquidacionesDetalle(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportLiquidacionDetalleProducto.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdLiquidacionesDetalleTO> listado = UtilsJSON.jsonToList(PrdLiquidacionesDetalleTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteLiquidacionesDetalle(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteLiquidacionesDetalle")
    public @ResponseBody
    String exportarReporteLiquidacionesDetalle(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteLiquidacionesDetalle(usuarioEmpresaReporteTO, map);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/listarPrdFitoplanctonTO")
    public RespuestaWebTO listarPrdFitoplanctonTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        List<PrdFitoplanctonTO> listaResultante = new ArrayList<>();
        try {
            List<PrdFitoplanctonTO> respues = fitoplanctonService.getListPrdFitoplanctonTO(empresa, sector, fecha);
            if (respues != null && !respues.isEmpty()) {
                if (!incluirTodos) {
                    List<PrdFitoplanctonTO> listaFiltrada = new ArrayList<>();
                    respues.stream().filter((prdFitoplanctonTO) -> ((prdFitoplanctonTO.getFitPorcentajeSalinidadActual() == null || prdFitoplanctonTO.getFitPorcentajeSalinidadActual().compareTo(BigDecimal.ZERO) == 0))
                            && (prdFitoplanctonTO.getFitClorophytasActual() == null || prdFitoplanctonTO.getFitClorophytasActual().compareTo(BigDecimal.ZERO) == 0)
                            && (prdFitoplanctonTO.getFitCyanofitasActual() == null || prdFitoplanctonTO.getFitCyanofitasActual().compareTo(BigDecimal.ZERO) == 0)
                            && (prdFitoplanctonTO.getFitDiatomeasActual() == null || prdFitoplanctonTO.getFitDiatomeasActual().compareTo(BigDecimal.ZERO) == 0)
                            && (prdFitoplanctonTO.getFitDinoflageladosActual() == null || prdFitoplanctonTO.getFitDinoflageladosActual().compareTo(BigDecimal.ZERO) == 0)
                            && (prdFitoplanctonTO.getFitEuglenalesActual() == null || prdFitoplanctonTO.getFitEuglenalesActual().compareTo(BigDecimal.ZERO) == 0)
                    ).map((fito) -> {
                        return fito;
                    }).forEachOrdered((fito) -> {
                        listaFiltrada.add(fito);
                    });
                    listaResultante = listaFiltrada;
                } else {
                    listaResultante = respues;
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(listaResultante);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarPrdFitoplanctonTO")
    public ResponseEntity insertarPrdFitoplanctonTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        List<PrdFitoplanctonTO> listaPrdFitoplanctonTO = UtilsJSON.jsonToList(PrdFitoplanctonTO.class, parametros.get("listaPrdFitoplanctonTO"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, parametros.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            boolean respues = fitoplanctonService.insertarPrdFitoplancton(sector, fecha, listaPrdFitoplanctonTO, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Se ingresaron " + listaPrdFitoplanctonTO.size() + " fitoplancton con fecha " + fecha);
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se pudo ingresar");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/eliminarPrdFitoplanctonTO")
    public RespuestaWebTO eliminarPrdFitoplanctonTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        PrdFitoplanctonTO prdFitoplanctonTO = UtilsJSON.jsonToObjeto(PrdFitoplanctonTO.class, map.get("prdFitoplanctonTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = fitoplanctonService.eliminarPrdFitoplanctonTO(sector, fecha, prdFitoplanctonTO, sisInfoTO);
            if (respuesta.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respuesta.substring(1, respuesta.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(respuesta.substring(1, respuesta.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListadoPrdFitoplanctonTO")
    public RespuestaWebTO getListadoPrdFitoplanctonTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdListadoFitoplanctonTO> respues = fitoplanctonService.getListadoPrdFitoplanctonTO(empresa, sector, piscina, desde, hasta);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerGramajesYFitoplanctonListado")
    public RespuestaWebTO obtenerGramajesYFitoplanctonListado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        try {
            Map<String, Object> respues = grameajeService.obtenerGramajesYFitoplanctonListado(empresa, sector, piscina, desde, hasta);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se han encontrado resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getComboPrdCorridaFiltradoFecha")
    public RespuestaWebTO getComboPrdCorridaFiltradoFecha(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        Date fecha = UtilsJSON.jsonToObjeto(Date.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdCorrida> corridas = corridaService.getComboPrdCorridaFiltradoFecha(empresa, sector, piscina, fecha);
            if (!corridas.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(corridas);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron corridas");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getPrdLiquidacionSoloActivas")
    public ResponseEntity getPrdLiquidacionSoloActivas(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, parametros.get("cliente"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, parametros.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, parametros.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            PrdLiquidacion respues = liquidacionService.getPrdLiquidacionSoloActivas(empresa, proveedor, cliente, motivo, numero);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/getListPrdLiquidacionParaCompras")
    public ResponseEntity getListPrdLiquidacionParaCompras(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<InvComprasLiquidacionTO> listado = UtilsJSON.jsonToList(InvComprasLiquidacionTO.class, parametros.get("listadoListaInvComprasLiquidacionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<PrdLiquidacion> respues = liquidacionService.getListPrdLiquidacionParaCompras(empresa, listado);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/getListPrdLiquidacionParaVentas")
    public ResponseEntity getListPrdLiquidacionParaVentas(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<InvVentasLiquidacionTO> listado = UtilsJSON.jsonToList(InvVentasLiquidacionTO.class, parametros.get("listadoInvVentasLiquidacionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<PrdLiquidacion> respues = liquidacionService.getListPrdLiquidacionParaVentas(empresa, listado);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/generarReporteCorridasInconsistentesMatricial")
    public RespuestaWebTO generarReporteCorridasInconsistentesMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, parametros.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("hasta"));
        List<PrdCorridasInconsistentesTO> listado = UtilsJSON.jsonToList(PrdCorridasInconsistentesTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteCorridasInconsistentes(usuarioEmpresaReporteTO, listado, desde, hasta);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCorridasInconsistentes")
    public @ResponseBody
    String generarReporteCorridasInconsistentes(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportLiquidacionDetalleProducto.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdCorridasInconsistentesTO> listado = UtilsJSON.jsonToList(PrdCorridasInconsistentesTO.class, parametros.get("listado"));
        String desde = UtilsJSON.jsonToObjeto(String.class, parametros.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("hasta"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteCorridasInconsistentes(usuarioEmpresaReporteTO, listado, desde, hasta);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCorridasInconsistentes")
    public @ResponseBody
    String exportarReporteCorridasInconsistentes(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<PrdCorridasInconsistentesTO> listado = UtilsJSON.jsonToList(PrdCorridasInconsistentesTO.class, map.get("listado"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteCorridasInconsistentes(usuarioEmpresaReporteTO, listado, desde, hasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrdResumenTrazabilidadAlimentacionTO")
    public ResponseEntity getPrdResumenTrazabilidadAlimentacionTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            RetornoTO respues = corridaService.getPrdResumenTrazabilidadAlimentacionTO(empresa, sector, tipo, fechaDesde, fechaHasta, sisInfoTO);
            if (respues.getColumnas() != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/getPrdResumenCorridaSubcategoriaTO")
    public ResponseEntity getPrdResumenCorridaSubcategoriaTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipo"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            RetornoTO respues = corridaService.getPrdResumenCorridaSubcategoriaTO(empresa, sector, tipo, fechaDesde, fechaHasta, sisInfoTO);
            if (respues.getColumnas() != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/getListPrdPrecioTallaLiquidacionPesca")
    public ResponseEntity getListPrdPrecioTallaLiquidacionPesca(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, parametros.get("busqueda"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, parametros.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<PrdPrecioTallaLiquidacionPesca> respues = prdPrecioTallaLiquidacionPescaService.getListPrdPrecioTallaLiquidacionPesca(
                    empresa, fecha, busqueda);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/cambiarPrecioTallaLiquidacionPesca")
    public RespuestaWebTO cambiarPrecioTallaLiquidacionPesca(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<PrdPrecioTallaLiquidacionPescaTO> listado = UtilsJSON.jsonToList(PrdPrecioTallaLiquidacionPescaTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = prdPrecioTallaLiquidacionPescaService.cambiarPrecioTallaLiquidacionPesca(listado, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMap());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setExtraInfo(mensajeTO != null ? mensajeTO.getListaErrores1() : new ArrayList<>());
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha actualizado.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListPrdPrecioTallaLiquidacionPescaTONuevos")
    public ResponseEntity getListPrdPrecioTallaLiquidacionPescaTONuevos(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, parametros.get("fecha"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, parametros.get("busqueda"));

        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<PrdPrecioTallaLiquidacionPescaTO> respues = prdPrecioTallaLiquidacionPescaService.getListPrdPrecioTallaLiquidacionPescaTONuevos(empresa, fecha, busqueda);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/obtenerPrdPrecioTallaLiquidacionPesca")
    public ResponseEntity obtenerPrdPrecioTallaLiquidacionPesca(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String tallaCodigo = UtilsJSON.jsonToObjeto(String.class, parametros.get("tallaCodigo"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, parametros.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            PrdPrecioTallaLiquidacionPesca respues = prdPrecioTallaLiquidacionPescaService.obtenerPrdPrecioTallaLiquidacionPesca(
                    empresa, fecha, tallaCodigo);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/getListPrdPrecioTallaLiquidacionPescaTO")
    public ResponseEntity getListPrdPrecioTallaLiquidacionPescaTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, parametros.get("busqueda"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, parametros.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<PrdPrecioTallaLiquidacionPescaTO> respues = prdPrecioTallaLiquidacionPescaService.getListPrdPrecioTallaLiquidacionPescaTO(
                    empresa, fecha, busqueda);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/getPrdTrazabilidadCorridaTO")
    public ResponseEntity getPrdTrazabilidadCorridaTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, parametros.get("piscina"));
        String corrida = UtilsJSON.jsonToObjeto(String.class, parametros.get("corrida"));
        BigDecimal costo = UtilsJSON.jsonToObjeto(BigDecimal.class, parametros.get("costo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<PrdTrazabilidadCorridaTO> respues = corridaService.getPrdTrazabilidadCorridaTO(empresa, sector, piscina, corrida, costo, tipo);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/listarImagenesLiquidacionPesca")
    public RespuestaWebTO listarImagenesLiquidacionPesca(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionPK pk = UtilsJSON.jsonToObjeto(PrdLiquidacionPK.class, map.get("pk"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdLiquidacionDatosAdjuntos> respues = liquidacionService.listarImagenesDeLiquidacionPesca(pk);
            if (respues != null) {
                respues.forEach((detalle) -> {
                    detalle.setPrdLiquidacion(new PrdLiquidacion(detalle.getPrdLiquidacion().getPrdLiquidacionPK()));
                });
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron imágenes de liquidación de pesca.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarPiscinaTipo")

    public RespuestaWebTO listarPiscinaTipo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean filtrarInactivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("filtrarInactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdPiscinaTipo> respues = piscinaTipoService.listarPiscinaTipo(empresa, filtrarInactivos);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarPrdPiscinaTipo")
    public RespuestaWebTO insertarPrdPiscinaTipo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPiscinaTipo prdPiscinaTipo = UtilsJSON.jsonToObjeto(PrdPiscinaTipo.class, map.get("prdPiscinaTipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = piscinaTipoService.insertarPrdPiscinaTipo(prdPiscinaTipo, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El tipo de piscina con código: " + prdPiscinaTipo.getPrdPiscinaTipoPK().getTipoCodigo() + ", se ha guardado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al guardar la piscina");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarPrdPiscinaTipo")
    public RespuestaWebTO modificarPrdPiscinaTipo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPiscinaTipo prdPiscinaTipo = UtilsJSON.jsonToObjeto(PrdPiscinaTipo.class, map.get("prdPiscinaTipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = piscinaTipoService.modificarPrdPiscinaTipo(prdPiscinaTipo, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El tipo de piscina con código: " + prdPiscinaTipo.getPrdPiscinaTipoPK().getTipoCodigo() + ", se ha modificado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al guardar la piscina");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoPiscinaTipo")
    public RespuestaWebTO modificarEstadoPiscinaTipo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPiscinaTipoPK pk = UtilsJSON.jsonToObjeto(PrdPiscinaTipoPK.class, map.get("pk"));
        boolean activar = UtilsJSON.jsonToObjeto(Boolean.class, map.get("activar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = piscinaTipoService.modificarEstadoPiscinaTipo(pk, activar, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                String sms = !activar ? " inactivado" : "activado";
                resp.setOperacionMensaje("El tipo de piscina con código: " + pk.getTipoCodigo() + ", se ha " + sms + " correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al guardar la piscina");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarPrdPiscinaTipo")
    public RespuestaWebTO eliminarPrdPiscinaTipo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPiscinaTipoPK pk = UtilsJSON.jsonToObjeto(PrdPiscinaTipoPK.class, map.get("pk"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = piscinaTipoService.eliminarPrdPiscinaTipo(pk, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El tipo de piscina con código: " + pk.getTipoCodigo() + ", se ha eliminado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al eliminar la piscina");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarPiscinaGrupo")
    public RespuestaWebTO listarPiscinaGrupo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean filtrarInactivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("filtrarInactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdPiscinaGrupo> respues = piscinaGrupoService.listarPiscinaGrupo(empresa, filtrarInactivos);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarPiscinaGrupoTO")
    public RespuestaWebTO listarPiscinaGrupoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean filtrarInactivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("filtrarInactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdPiscinaGrupoTO> respues = piscinaGrupoService.listarPiscinaGrupoTO(empresa, filtrarInactivos);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarPrdPiscinaGrupo")
    public RespuestaWebTO insertarPrdPiscinaGrupo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPiscinaGrupo prdPiscinaGrupo = UtilsJSON.jsonToObjeto(PrdPiscinaGrupo.class, map.get("prdPiscinaGrupo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = piscinaGrupoService.insertarPrdPiscinaGrupo(prdPiscinaGrupo, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El grupo de piscina con código: " + prdPiscinaGrupo.getPrdPiscinaGrupoPK().getGrupoCodigo() + ", se ha guardado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al guardar la piscina");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarPrdPiscinaGrupo")
    public RespuestaWebTO modificarPrdPiscinaGrupo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPiscinaGrupo prdPiscinaGrupo = UtilsJSON.jsonToObjeto(PrdPiscinaGrupo.class, map.get("prdPiscinaGrupo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = piscinaGrupoService.modificarPrdPiscinaGrupo(prdPiscinaGrupo, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El grupo de piscina con código: " + prdPiscinaGrupo.getPrdPiscinaGrupoPK().getGrupoCodigo() + ", se ha modificado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al guardar la piscina");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoPiscinaGrupo")
    public RespuestaWebTO modificarEstadoPiscinaGrupo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPiscinaGrupoPK pk = UtilsJSON.jsonToObjeto(PrdPiscinaGrupoPK.class, map.get("pk"));
        boolean activar = UtilsJSON.jsonToObjeto(Boolean.class, map.get("activar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = piscinaGrupoService.modificarEstadoPiscinaGrupo(pk, activar, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                String sms = !activar ? " inactivado" : "activado";
                resp.setOperacionMensaje("El grupo de piscina con código: " + pk.getGrupoCodigo() + ", se ha " + sms + " correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al guardar la piscina");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarPrdPiscinaGrupo")
    public RespuestaWebTO eliminarPrdPiscinaGrupo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPiscinaGrupoPK pk = UtilsJSON.jsonToObjeto(PrdPiscinaGrupoPK.class, map.get("pk"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = piscinaGrupoService.eliminarPrdPiscinaGrupo(pk, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El grupo de piscina con código: " + pk.getGrupoCodigo() + ", se ha eliminado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al eliminar la piscina");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarPrdPiscinaGrupoRelacion")
    public RespuestaWebTO insertarPrdPiscinaGrupoRelacion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPiscinaGrupoRelacionTO prdPiscinaGrupoRelacionTO = UtilsJSON.jsonToObjeto(PrdPiscinaGrupoRelacionTO.class, map.get("prdPiscinaGrupoRelacionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            PrdPiscinaGrupoRelacion respues = piscinaGrupoRelacionService.insertarPrdPiscinaGrupoRelacion(prdPiscinaGrupoRelacionTO, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La relación de grupo piscina con secuencial: " + respues.getGrupoSecuencial() + ", se ha guardado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al guardar la piscina");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarPrdPiscinaGrupoRelacion")
    public RespuestaWebTO modificarPrdPiscinaGrupoRelacion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdPiscinaGrupoRelacionTO prdPiscinaGrupoRelacionTO = UtilsJSON.jsonToObjeto(PrdPiscinaGrupoRelacionTO.class, map.get("prdPiscinaGrupoRelacionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            PrdPiscinaGrupoRelacion respues = piscinaGrupoRelacionService.modificarPrdPiscinaGrupoRelacion(prdPiscinaGrupoRelacionTO, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La relación de grupo piscina con secuencial: " + respues.getGrupoSecuencial() + ", se ha modificado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al guardar la piscina");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarPrdPiscinaGrupoRelacion")
    public RespuestaWebTO eliminarPrdPiscinaGrupoRelacion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Integer secuencial = UtilsJSON.jsonToObjeto(Integer.class, map.get("pk"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            PrdPiscinaGrupoRelacion respues = piscinaGrupoRelacionService.eliminarPrdPiscinaGrupoRelacion(secuencial, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La relación de grupo piscina con secuencial: " + secuencial + ", se ha eliminado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al eliminar la piscina");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaCrudPiscina")
    public RespuestaWebTO obtenerDatosParaCrudPiscina(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = piscinaService.obtenerDatosParaCrudPiscina(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se han encontrado datos para generar corrida.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarAmbienteProduccion")
    public RespuestaWebTO insertarAmbienteProduccion(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdAmbienteProduccion prdAmbienteProduccion = UtilsJSON.jsonToObjeto(PrdAmbienteProduccion.class, map.get("prdAmbienteProduccion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = ambienteProduccionService.insertarAmbienteProduccion(prdAmbienteProduccion, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));

            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListarPrdAmbienteProduccion")
    public RespuestaWebTO getListarPrdAmbienteProduccion(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        try {
            List<PrdAmbienteProduccion> respues = ambienteProduccionService.getListarPrdAmbienteProduccion(empresa);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarAmbienteProduccion")
    public RespuestaWebTO modificarInvRutasGuias(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdAmbienteProduccion prdAmbienteProduccion = UtilsJSON.jsonToObjeto(PrdAmbienteProduccion.class, map.get("prdAmbienteProduccion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = ambienteProduccionService.modificarAmbienteProduccion(prdAmbienteProduccion, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarAmbienteProduccion")
    public RespuestaWebTO eliminarInvGuiaRutas(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdAmbienteProduccion prdAmbienteProduccion = UtilsJSON.jsonToObjeto(PrdAmbienteProduccion.class, map.get("prdAmbienteProduccion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = ambienteProduccionService.eliminarAmbienteProduccion(prdAmbienteProduccion, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getPrdFunLiquidacionConsolidandoTallasProveedor")
    public RespuestaWebTO getPrdFunLiquidacionConsolidandoTallasProveedor(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String comisionista = UtilsJSON.jsonToObjeto(String.class, map.get("comisionista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdFunLiquidacionConsolidandoTallasProveedorTO> respues = liquidacionService.getPrdFunLiquidacionConsolidandoTallasProveedor(empresa, desde, hasta, sector, proveedor, piscina, comisionista);
            if (respues != null && !respues.isEmpty() && respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteLiquidacionConsolidandoTallasProveedores")
    public @ResponseBody
    String exportarReporteLiquidacionConsolidandoTallasProveedores(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<PrdFunLiquidacionConsolidandoTallasProveedorTO> listado = UtilsJSON.jsonToList(PrdFunLiquidacionConsolidandoTallasProveedorTO.class, map.get("listado"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteProduccionService.exportarReporteLiquidacionConsolidandoTallasProveedores(listado, fechaDesde, fechaHasta, sector, proveedor, usuarioEmpresaReporteTO);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteLiquidacionConsolidandoTallasProveedorMatricial")
    public RespuestaWebTO generarReporteLiquidacionConsolidandoTallasProveedorMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdFunLiquidacionConsolidandoTallasProveedorTO> listado = UtilsJSON.jsonToList(PrdFunLiquidacionConsolidandoTallasProveedorTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteLiquidacionConsolidandoTallasProveedor(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteLiquidacionConsolidandoTallasProveedor")
    public @ResponseBody
    String generarReporteLiquidacionConsolidandoTallasProveedor(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        byte[] respuesta;
        String nombreReporte = "reportLiquidacionConsolidandoTallasProveedor.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdFunLiquidacionConsolidandoTallasProveedorTO> listado = UtilsJSON.jsonToList(PrdFunLiquidacionConsolidandoTallasProveedorTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteLiquidacionConsolidandoTallasProveedor(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPiscinaSegunGrupos")
    public RespuestaWebTO getListaPiscinaSegunGrupos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<String> grupos = UtilsJSON.jsonToList(String.class, map.get("grupos"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdListaPiscinaTO> piscinas = new ArrayList<>();
            if (grupos != null && !grupos.isEmpty()) {
                for (int i = 0; i < grupos.size(); i++) {
                    List<PrdListaPiscinaTO> piscinasPorGrupo = piscinaService.getListaPiscinaTOBusqueda(empresa, null, fecha, grupos.get(i), null, true);
                    if (piscinasPorGrupo != null && !piscinasPorGrupo.isEmpty()) {
                        piscinas.addAll(piscinasPorGrupo);
                    }
                }
            }
            if (piscinas != null && !piscinas.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(piscinas);
            } else {
                resp.setOperacionMensaje("No se encontraron piscinas");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/guardarImagenesLiquidacionPesca")
    public RespuestaWebTO guardarImagenesLiquidacionPesca(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacionPK pk = UtilsJSON.jsonToObjeto(PrdLiquidacionPK.class, map.get("pk"));
        List<PrdLiquidacionDatosAdjuntos> imagenes = UtilsJSON.jsonToList(PrdLiquidacionDatosAdjuntos.class, map.get("listImagen"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = liquidacionService.guardarImagenesLiquidacionPesca(pk, imagenes, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                if (imagenes == null || imagenes.isEmpty()) {
                    resp.setOperacionMensaje("Las imagenes para liquidación de pesca se han eliminado correctamente.");
                } else {
                    resp.setOperacionMensaje("Las imagenes para liquidación de pesca se han guardado correctamente.");
                }
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al guardar imagenes de consumo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/verificarTallaExcel")
    public RespuestaWebTO verificarTallaExcel(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<PrdTalla> prdLiquidacionTalla = UtilsJSON.jsonToList(PrdTalla.class, map.get("tallas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = tallaService.verificarTallaExcel(prdLiquidacionTalla, sisInfoTO);
            if (respuesta != null) {
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No hay resgistros en el excel, verifique el documento");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/importarTallasEnLote")
    public RespuestaWebTO importarTallasEnLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<PrdTalla> tallas = UtilsJSON.jsonToList(PrdTalla.class, map.get("tallas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdTalla> tallasInsertado = new ArrayList<>();
            List<String> mensajes = new ArrayList<>();
            Map<String, Object> respuestaEnviar = new HashMap<>();

            for (int i = 0; i < tallas.size(); i++) {
                Map<String, Object> respuesta = tallaService.insertarTallasImportadas(tallas.get(i), sisInfoTO);
                if (respuesta != null) {
                    String mensaje = (String) respuesta.get("mensaje");
                    PrdTalla talla = (PrdTalla) respuesta.get("prdTalla");
                    if (mensaje.subSequence(0, 1).equals("T")) {
                        tallasInsertado.add(talla);
                    }
                    mensajes.add(mensaje);
                } else {
                    mensajes.add("FHubo un error al insertar tallas: " + tallas.get(i).getPrdLiquidacionTallaPK().getTallaCodigo());
                }
            }
            respuestaEnviar.put("tallasInsertados", tallasInsertado);
            respuestaEnviar.put("listaMensajesEnviar", mensajes);
            resp.setExtraInfo(respuestaEnviar);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarConsumosDetalladoProductos")
    public RespuestaWebTO listarConsumosDetalladoProductos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String equipo = UtilsJSON.jsonToObjeto(String.class, map.get("equipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdConsumosDetalladoProductosTO> respues = corridaService.getPrdConsumosDetalladoProductos(empresa, motivo, sector, piscina, busqueda, fechaDesde, fechaHasta, equipo);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaPrdListaPiscinaTO")
    public RespuestaWebTO getListaPrdListaPiscinaTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<PrdListaPiscinaTO> respues = piscinaService.getListaPiscinaTO(empresa.trim());
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron piscinas");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getResumenCorridaCostoVenta")
    public RespuestaWebTO getResumenCorridaCostoVenta(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdContabilizarCorridaCostoVentaTO> respues = corridaService.getResumenCorridaCostoVenta(empresa, "'" + desde + "'", "'" + hasta + "'");
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listadoComisionista")
    public RespuestaWebTO listadoComisionista(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<String> respuesta = liquidacionService.listadoComisionista(empresa);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarPrdEquipoControl")
    public RespuestaWebTO insertarPrdEquipoControl(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdEquipoControl prdEquipoControl = UtilsJSON.jsonToObjeto(PrdEquipoControl.class, map.get("prdEquipoControl"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = equipoControlService.insertarEquipoControl(prdEquipoControl, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se ha guardado pre liquidación motivo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarEquiposControl")
    public RespuestaWebTO listarEquiposControl(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdEquipoControl> respuesta = equipoControlService.listarEquiposControl(empresa);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados motivos de pre liquidación.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/actualizarEquipoControl")
    public RespuestaWebTO actualizarEquipoControl(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdEquipoControl prdEquipoControl = UtilsJSON.jsonToObjeto(PrdEquipoControl.class, map.get("prdEquipoControl"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = equipoControlService.actualizarEquipoControl(prdEquipoControl, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se ha guardado pre liquidación motivo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarComisionista")
    public RespuestaWebTO insertarComisionista(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdComisionista prdComisionista = UtilsJSON.jsonToObjeto(PrdComisionista.class, map.get("prdComisionista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = comisionistaControlService.insertarComisionista(prdComisionista, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se ha guardado registro comisionista.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarComisionista")
    public RespuestaWebTO listarComisionista(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdComisionista> respuesta = comisionistaControlService.listarComisionista(empresa);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron registros de comisionistas.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/actualizarComisionista")
    public RespuestaWebTO actualizarComisionista(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdComisionista prdComisionista = UtilsJSON.jsonToObjeto(PrdComisionista.class, map.get("prdComisionista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = comisionistaControlService.actualizarComisionista(prdComisionista, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se ha guardado registro de comisionista.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarListadoComisionista")
    public @ResponseBody
    String exportarListadoComisionista(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<PrdComisionista> listadoComisionista = UtilsJSON.jsonToList(PrdComisionista.class, map.get("listadoComisionista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = comisionistaControlService.exportarListadoComisionista(usuarioEmpresaReporteTO, listadoComisionista);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("generarReporteComisionistaMatricial")
    public RespuestaWebTO generarReporteComisionistaMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        List<PrdComisionista> listadoComisionista = UtilsJSON.jsonToList(PrdComisionista.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        try {
            respuesta = comisionistaControlService.generarReporteComisionista(usuarioEmpresaReporteTO, listadoComisionista, sisInfoTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("generarReporteComisionista")
    public RespuestaWebTO generarReporteComisionista(HttpServletResponse response, @RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        List<PrdComisionista> listadoComisionista = UtilsJSON.jsonToList(PrdComisionista.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        try {
            respuesta = comisionistaControlService.generarReporteComisionista(usuarioEmpresaReporteTO, listadoComisionista, sisInfoTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/buscarListadoLiquidacionComisionista")
    public RespuestaWebTO buscarListadoLiquidacionComisionista(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String comisionista = UtilsJSON.jsonToObjeto(String.class, map.get("comisionista"));
        boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("inculirTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        try {
            List<PrdLiquidacionComisionistaTO> respuesta = comisionistaControlService.buscarListadoLiquidacionComisionista(empresa, sector, piscina, fechaDesde, fechaHasta, comisionista, incluirTodos);
            if (respuesta != null && !respuesta.isEmpty() && respuesta.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarLiquidacionComisionista")
    public @ResponseBody
    String exportarLiquidacionComisionista(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = comisionistaControlService.exportarLiquidacionComisionista(usuarioEmpresaReporteTO, map);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteLiquidacionComisionistaMatricial")
    public RespuestaWebTO generarReporteLiquidacionComisionistaMatricial(HttpServletResponse response, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdLiquidacionComisionistaTO> listado = UtilsJSON.jsonToList(PrdLiquidacionComisionistaTO.class, parametros.get("listado"));
        List<InformacionAdicional> infoAdicional = UtilsJSON.jsonToList(InformacionAdicional.class, parametros.get("informacionAdicional"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaFinal"));
        String centroProduccion = UtilsJSON.jsonToObjeto(String.class, parametros.get("centroProduccion"));
        String centroCosto = UtilsJSON.jsonToObjeto(String.class, parametros.get("centroCosto"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = comisionistaControlService.generarReporteLiquidacionComisionista(usuarioEmpresaReporteTO, listado, infoAdicional, fechaInicio, fechaFin, centroProduccion, centroCosto);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //reporteConsultasPDF
    @RequestMapping("/generarReporteLiquidacionComisionista")
    public @ResponseBody
    String generarReporteLiquidacionComisionista(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportLiquidacionListado.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<PrdLiquidacionComisionistaTO> listado = UtilsJSON.jsonToList(PrdLiquidacionComisionistaTO.class, parametros.get("listado"));
        List<InformacionAdicional> infoAdicional = UtilsJSON.jsonToList(InformacionAdicional.class, parametros.get("informacionAdicional"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaFinal"));
        String centroProduccion = UtilsJSON.jsonToObjeto(String.class, parametros.get("centroProduccion"));
        String centroCosto = UtilsJSON.jsonToObjeto(String.class, parametros.get("centroCosto"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = comisionistaControlService.generarReporteLiquidacionComisionista(usuarioEmpresaReporteTO, listado, infoAdicional, fechaInicio, fechaFin, centroProduccion, centroCosto);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrdConsumosDetalladoProductosAgrupadoCC")
    public RespuestaWebTO getPrdConsumosDetalladoProductosAgrupadoCC(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String equipo = UtilsJSON.jsonToObjeto(String.class, map.get("equipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdConsumosDetalladoProductosTO> respues = corridaService.getPrdConsumosDetalladoProductosAgrupadoCC(empresa, motivo, sector, piscina, busqueda, fechaDesde, fechaHasta, equipo);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getPrdConsumosDetalladoProductosAgrupadoEquipo")
    public RespuestaWebTO getPrdConsumosDetalladoProductosAgrupadoEquipo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String equipo = UtilsJSON.jsonToObjeto(String.class, map.get("equipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdConsumosDetalladoProductosTO> respues = corridaService.getPrdConsumosDetalladoProductosAgrupadoEquipo(empresa, motivo, sector, piscina, busqueda, fechaDesde, fechaHasta, equipo);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosConsumoDetalladoProductos")
    public RespuestaWebTO obtenerDatosConsumoDetalladoProductos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = corridaService.obtenerDatosConsumoDetalladoProductos(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se han encontrado datos para generar corrida.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsumoDetalladoProductos")
    public @ResponseBody
    String generarReporteConsumoDetalladoProductos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportConsumoDetalladoProductos.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<PrdConsumosDetalladoProductosTO> listado = UtilsJSON.jsonToList(PrdConsumosDetalladoProductosTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteProduccionService.generarReporteConsumoDetalladoProductos(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/verificarExistenciasLiquidacion")
    public RespuestaWebTO verificarExistenciasLiquidacion(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdLiquidacion liquidicacion = UtilsJSON.jsonToObjeto(PrdLiquidacion.class, map.get("liquidacion"));
        List<PrdLiquidacionDetalle> detalles = UtilsJSON.jsonToList(PrdLiquidacionDetalle.class, map.get("detalles"));//clave: codigo valor:descripcion de producto
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (liquidicacion != null) {
                Map<String, Object> respuesta = liquidacionService.verificarExistenciasLiquidacion(sisInfoTO.getEmpresa(), liquidicacion, detalles);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Datos de liquidación es incorrecto");
            }

        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }
//Reproceso motivo

    @RequestMapping("/getListaPrdReprocesoMotivo")
    public RespuestaWebTO getListaPrdReprocesoMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdReprocesoMotivo> respues = prdReprocesoMotivoService.listarPrdReprocesoMotivo(empresa, incluirTodos);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarPrdReprocesoMotivo")
    public RespuestaWebTO insertarPrdReprocesoMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdReprocesoMotivo prdReprocesoMotivo = UtilsJSON.jsonToObjeto(PrdReprocesoMotivo.class, map.get("prdReprocesoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = prdReprocesoMotivoService.insertarPrdReprocesoMotivo(prdReprocesoMotivo, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se ha guardado motivo de reproceso.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarPrdReprocesoMotivo")
    public RespuestaWebTO modificarPrdReprocesoMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdReprocesoMotivo prdReprocesoMotivo = UtilsJSON.jsonToObjeto(PrdReprocesoMotivo.class, map.get("prdReprocesoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = prdReprocesoMotivoService.modificarPrdReprocesoMotivo(prdReprocesoMotivo, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta.substring(1));
                resp.setOperacionMensaje(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se ha guardado motivo de reproceso.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarPrdReprocesoMotivo")
    public RespuestaWebTO eliminarPrdReprocesoMotivo(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        PrdReprocesoMotivo prdReprocesoMotivo = UtilsJSON.jsonToObjeto(PrdReprocesoMotivo.class, map.get("prdReprocesoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = prdReprocesoMotivoService.eliminarPrdReprocesoMotivo(prdReprocesoMotivo, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoPrdReprocesoMotivo")
    public ResponseEntity modificarEstadoPrdReprocesoMotivo(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        PrdReprocesoMotivo prdReprocesoMotivo = UtilsJSON.jsonToObjeto(PrdReprocesoMotivo.class, map.get("prdReprocesoMotivo"));
        boolean inactivo = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivo"));
        try {
            String msje = prdReprocesoMotivoService.modificarEstadoPrdReprocesoMotivo(prdReprocesoMotivo, inactivo, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                resp.setOperacionMensaje("F" + e.getMessage());
            } else {
                resp.setOperacionMensaje("FLo sentimos, ocurrió un error inesperado!");
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
//Reproceso

    @RequestMapping("/obtenerDatosParaPrdProceso")
    public RespuestaWebTO obtenerDatosParaPrdProceso(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = prdReprocesoService.obtenerDatosParaPrdProceso(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListarPrdReprocesoTO")
    public RespuestaWebTO getListarPrdReprocesoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String nrRegistros = UtilsJSON.jsonToObjeto(String.class, map.get("nroRegistros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdReprocesoTO> respues = prdReprocesoService.getListarPrdReprocesoTO(empresa, motivo, nrRegistros);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/guardarReproceso")
    public RespuestaWebTO guardarReproceso(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = prdReprocesoService.guardarReproceso(map);
            if (respues != null) {
                String mensaje = UtilsJSON.jsonToObjeto(String.class, respues.get("mensaje"));
                PrdReprocesoTO prdReprocesoTO = UtilsJSON.jsonToObjeto(PrdReprocesoTO.class, respues.get("prdReprocesoTO"));
                if (mensaje.substring(0, 1).equals("T")) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
                    resp.setExtraInfo(prdReprocesoTO);
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
                }
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarReproceso")
    public RespuestaWebTO modificarReproceso(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = prdReprocesoService.modificarReproceso(map);
            if (respues != null) {
                String mensaje = UtilsJSON.jsonToObjeto(String.class, respues.get("mensaje"));
                PrdReprocesoTO prdReprocesoTO = UtilsJSON.jsonToObjeto(PrdReprocesoTO.class, respues.get("prdReprocesoTO"));
                if (mensaje.substring(0, 1).equals("T")) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
                    resp.setExtraInfo(prdReprocesoTO);
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
                }
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/desmayorizarPrdReproceso")
    public ResponseEntity desmayorizarPrdReproceso(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        try {
            String msje = prdReprocesoService.desmayorizarPrdReproceso(empresa, periodo, motivo, numero, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                resp.setOperacionMensaje("F" + e.getMessage());
            } else {
                resp.setOperacionMensaje("FLo sentimos, ocurrió un error inesperado!");
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/anularPrdReproceso")
    public ResponseEntity anularPrdReproceso(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        try {
            String msje = prdReprocesoService.anularPrdReproceso(empresa, periodo, motivo, numero, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                resp.setOperacionMensaje("F" + e.getMessage());
            } else {
                resp.setOperacionMensaje("FLo sentimos, ocurrió un error inesperado!");
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/restaurarPrdReproceso")
    public ResponseEntity restaurarPrdReproceso(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        try {
            String msje = prdReprocesoService.restaurarPrdReproceso(empresa, periodo, motivo, numero, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                resp.setOperacionMensaje("F" + e.getMessage());
            } else {
                resp.setOperacionMensaje("FLo sentimos, ocurrió un error inesperado!");
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
    @RequestMapping("/verificarExistenciaPiscina")
    public RespuestaWebTO verificarExistenciaPiscina(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<PrdPiscinaTO> importarPiscinas = UtilsJSON.jsonToList(PrdPiscinaTO.class, map.get("importarPiscina"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String empresa = sisInfoTO.getEmpresa();
            Map<String, Object> respuesta = piscinaService.verificarExistenciaPiscina(empresa, importarPiscinas);
            if (respuesta != null) {
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No hay lista que importar. comunicarse con el administrador");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }
    
    @RequestMapping("/importarPiscinaEnLote")
    public RespuestaWebTO importarPiscinaEnLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<PrdPiscinaTO> listado = UtilsJSON.jsonToList(PrdPiscinaTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<PrdPiscinaTO> piscinasInsertadas = new ArrayList<>();
            List<String> mensajes = new ArrayList<>();
            Map<String, Object> respuestaEnviar = new HashMap<>();

            for (int i = 0; i < listado.size(); i++) {
                PrdPiscinaTO piscina = listado.get(i);
                Map<String, Object> respuesta = piscinaService.insertarPiscinaImportacion(piscina, sisInfoTO);
                if (respuesta != null) {
                    String mensaje = (String) respuesta.get("mensaje");
                    PrdPiscinaTO piscinaResp = (PrdPiscinaTO) respuesta.get("piscinaTO");
                    if (mensaje.subSequence(0, 1).equals("T")) {
                        piscinasInsertadas.add(piscinaResp);
                    }
                    mensajes.add(mensaje);
                } else {
                    mensajes.add("FHubo un error al insertar producto: " + piscina.getPisNombre());
                }
            }
            respuestaEnviar.put("piscinasInsertadas", piscinasInsertadas);
            respuestaEnviar.put("listaMensajesEnviar", mensajes);
            resp.setExtraInfo(respuestaEnviar);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }
}
