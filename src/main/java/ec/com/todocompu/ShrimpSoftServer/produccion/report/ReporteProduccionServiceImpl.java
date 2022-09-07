package ec.com.todocompu.ShrimpSoftServer.produccion.report;

import ec.com.todocompu.ShrimpSoftServer.produccion.service.LiquidacionDetalleService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.LiquidacionService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.PreLiquidacionService;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionesDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.ListaLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.ListaPreLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdConsumosDetalladoProductosTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCorridasInconsistentesTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCostoDetalleFechaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunCostosPorFechaSimpleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunLiquidacionConsolidandoTallasProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunLiquidacionConsolidandoTallasTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunPreLiquidacionConsolidandoTallasTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionConsolidandoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionConsolidandoProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionConsultaEmpacadoraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionConsultaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionDetalleProductoEmpacadoraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionesDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaCostosDetalleCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaFunAnalisisPesosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSobrevivenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListadoFitoplanctonTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListadoGrameajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionConsolidandoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionConsultaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdProyeccionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenPescaSiembraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdUtilidadDiariaCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdProducto;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdTalla;
import ec.com.todocompu.ShrimpSoftUtils.produccion.report.ReporteLiquidacionPesca;
import ec.com.todocompu.ShrimpSoftUtils.produccion.report.ReportePrdCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.report.ReportePrdFunCostosPorFechaSimpleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.report.ReporteResumenFinanciero;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import jdk.nashorn.internal.objects.NativeArray;

@Service
public class ReporteProduccionServiceImpl implements ReporteProduccionService {

    @Autowired
    private GenericReporteService genericReporteService;
    @Autowired
    private PreLiquidacionService preLiquidacionService;
    @Autowired
    private LiquidacionService liquidacionService;
    private final String modulo = "produccion";
    @Autowired
    private LiquidacionDetalleService liquidacionDetalleService;

    @Override
    public byte[] generarReporteResumenSiembra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<PrdResumenCorridaTO> listaPrdListaResumenCorridaTO) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("titulo_reporte", "RESUMEN DE SIEMBRA");
        return genericReporteService.generarReporte(modulo, "reportResumenSiembras.jrxml", usuarioEmpresaReporteTO, map,
                listaPrdListaResumenCorridaTO);
    }

    @Override
    public byte[] generarReportePrdResumenCorrida(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<PrdResumenCorridaTO> prdResumenCorrida, String tituloReporte) throws Exception {

        String reporte = "";
        Map<String, Object> map = new HashMap<>();
        if (tituloReporte.trim().equals("RESUMEN DE SIEMBRA")) {
            reporte = "reportResumenSiembras.jrxml";
            map.put("titulo_reporte", tituloReporte);
        } else {
            reporte = "reportResumenPescas.jrxml";
        }
        return genericReporteService.generarReporte(modulo, reporte, usuarioEmpresaReporteTO, map, prdResumenCorrida);
    }

    @Override
    public byte[] generarReporteCostoDetalladoCorrida(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteCostoDetalladoCorrida> reporteCostoDetalladoCorrida, String tituloReporte) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("titulo_reporte", tituloReporte);
        return genericReporteService.generarReporte(modulo, "reportCostoDetalleCorrida.jrxml", usuarioEmpresaReporteTO,
                map, reporteCostoDetalladoCorrida);
    }

    @Override
    public byte[] generarReporteListadoFunAnalisiPeso(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteListadoFunAnalisisPeso> reporteListadoFunAnalisisPesos, String tituloReporte) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("titulo_reporte", tituloReporte);
        return genericReporteService.generarReporte(modulo, "reporteListadoFunAnalisisPesos.jrxml",
                usuarioEmpresaReporteTO, map, reporteListadoFunAnalisisPesos);
    }

    @Override
    public byte[] generarReporteListadoGrameaje(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteListadoGrameaje> reporteListadoGrameaje, String tituloReporte) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("titulo_reporte", tituloReporte);
        return genericReporteService.generarReporte(modulo, "reportListadoGrameaje.jrxml", usuarioEmpresaReporteTO, map,
                reporteListadoGrameaje);
    }

    @Override
    public byte[] generarReporteCostoDetalleFecha(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteCostoDetalleFecha> reporteCostoDetalleFecha, String tituloReporte) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("titulo_reporte", tituloReporte);
        return genericReporteService.generarReporte(modulo, "reportCostoDetalleFecha.jrxml", usuarioEmpresaReporteTO,
                map, reporteCostoDetalleFecha);
    }

    @Override
    public byte[] generarReportePrdFunCostosPorFechaSimpleTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReportePrdFunCostosPorFechaSimpleTO> reportePrdFunCostosPorFechaSimpleTOs, String tituloReporte)
            throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("titulo_reporte", tituloReporte);
        return genericReporteService.generarReporte(modulo, "reporteFunCostosPorFechaSimple.jrxml",
                usuarioEmpresaReporteTO, map, reportePrdFunCostosPorFechaSimpleTOs);
    }

    @Override
    public byte[] generarReporteResumenFinanciero(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteResumenFinanciero> reporteResumenFinanciero, String tituloReporte) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("titulo_reporte", tituloReporte);
        return genericReporteService.generarReporte(modulo, "reportResumenFinanciero.jrxml", usuarioEmpresaReporteTO,
                map, reporteResumenFinanciero);
    }

    @Override
    public byte[] generarReporteConsumoPorPiscina(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteConsumoPorPiscina> reporteConsumoPorPiscina, String tituloReporte) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("titulo_reporte", tituloReporte);
        return genericReporteService.generarReporte(modulo, "reportConsumoPorPiscina.jrxml", usuarioEmpresaReporteTO,
                map, reporteConsumoPorPiscina);
    }

    @Override
    public byte[] generarReporteConsumoPorFecha(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteConsumoPorFecha> reporteConsumoPorFecha, String tituloReporte) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("titulo_reporte", tituloReporte);
        return genericReporteService.generarReporte(modulo, "reportConsumoPorFecha.jrxml", usuarioEmpresaReporteTO, map,
                reporteConsumoPorFecha);
    }

    @Override
    public byte[] generarReportePrdResumenPesca(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteResumenPesca> reporteResumenPescas, String tituloReporte) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("titulo_reporte", tituloReporte);
        return genericReporteService.generarReporte(modulo, "reportResumenPescasNew.jrxml", usuarioEmpresaReporteTO,
                map, reporteResumenPescas);
    }

    @Override
    public byte[] generarReportePrdResumenSiembra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteResumenSiembra> reporteResumenSiembra, String tituloReporte) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("titulo_reporte", tituloReporte);
        return genericReporteService.generarReporte(modulo, "reportResumenSiembrasNew.jrxml", usuarioEmpresaReporteTO,
                map, reporteResumenSiembra);
    }

    @Override
    public byte[] generarReporteLiquidacionPesca(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteLiquidacionPesca> reporteLiquidacionPescas) throws Exception {

        Map<String, Object> map = new HashMap<>();
        return genericReporteService.generarReporte(modulo, "reportLiquidacionPesca.jrxml", usuarioEmpresaReporteTO,
                map, reporteLiquidacionPescas);
    }

    public byte[] generarReporteLiquidacionPescaPorLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteLiquidacionPesca> reporteLiquidacionPescas) throws Exception {

        Map<String, Object> map = new HashMap<>();
        return genericReporteService.generarReporte(modulo, "reportLiquidacionPescaPorLote.jrxml", usuarioEmpresaReporteTO,
                map, reporteLiquidacionPescas);
    }

    @Override
    public byte[] generarReporteListadoGramaje(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String codigoSector,
            String codigoPiscina, String codigoCorrida, String fechaDesde, String fechaHasta,
            List<PrdListadoGrameajeTO> prdListadoGrameajeTO) throws Exception {

        List<ReporteListadoGrameaje> lista = new ArrayList<>();
        prdListadoGrameajeTO.stream().map((plgTO) -> new ReporteListadoGrameaje(codigoSector, codigoPiscina,
                codigoCorrida, fechaDesde, fechaHasta, plgTO.getGraFecha(), plgTO.getGratPromedio(),
                plgTO.getGraiPromedio(), plgTO.getGraBiomasa(), plgTO.getGraSobrevivencia(),
                plgTO.getGratBalanceadoAcumulado(), plgTO.getGraComentario())).forEachOrdered((reporteListadoGramaje) -> {
            lista.add(reporteListadoGramaje);
        });
        return genericReporteService.generarReporte(modulo, "reportListadoGrameaje.jrxml", usuarioEmpresaReporteTO,
                new HashMap<>(), lista);
    }

    @Override
    public byte[] generarReporteListadoFunAnalisisPesos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String codigoSector, String fechaHasta, List<PrdListaFunAnalisisPesosTO> prdListaFunAnalisisPesosTO)
            throws Exception {

        List<ReporteListadoFunAnalisisPeso> lista = new ArrayList<>();
        prdListaFunAnalisisPesosTO.stream().map((fapTO) -> new ReporteListadoFunAnalisisPeso(
                fechaHasta.replaceAll("'", ""), codigoSector,
                fapTO.getApPiscina() == null ? "" : fapTO.getApPiscina(),
                fapTO.getApCorrida() == null ? "" : fapTO.getApCorrida(),
                fapTO.getApHectareas() == null ? null : fapTO.getApHectareas(),
                fapTO.getApFechaSiembra() == null ? null : fapTO.getApFechaSiembra(),
                fapTO.getApFormaSiembra() == null ? null : fapTO.getApFormaSiembra(),
                fapTO.getApDensidadMetroCuadrado() == null ? null : fapTO.getApDensidadMetroCuadrado(),
                fapTO.getApSobrevivenciaMetroCuadrado() == null ? null : fapTO.getApSobrevivenciaMetroCuadrado(),
                fapTO.getApRaleo() == null ? null : fapTO.getApRaleo(),
                fapTO.getApEdad() == null ? null : fapTO.getApEdad(),
                fapTO.getApPesoIdeal() == null ? null : fapTO.getApPesoIdeal(),
                fapTO.getApPesoSobreIdeal() == null ? null : fapTO.getApPesoSobreIdeal(),
                fapTO.getApPesoPromedioActual() == null ? null : fapTO.getApPesoPromedioActual(),
                fapTO.getApPesoIncrementoSemana4() == null ? null : fapTO.getApPesoIncrementoSemana4(),
                fapTO.getApPesoIncrementoSemana3() == null ? null : fapTO.getApPesoIncrementoSemana3(),
                fapTO.getApPesoIncrementoSemana2() == null ? null : fapTO.getApPesoIncrementoSemana2(),
                fapTO.getApPesoIncrementoSemana1() == null ? null : fapTO.getApPesoIncrementoSemana1(),
                fapTO.getApPesoIncrementoPromedio() == null ? null : fapTO.getApPesoIncrementoPromedio(),
                fapTO.getApBalanceadoTipo() == null ? "" : fapTO.getApBalanceadoTipo(),
                fapTO.getApBalanceadoSacos() == null ? null : fapTO.getApBalanceadoSacos(),
                fapTO.getApBalanceadoKilosHectarea() == null ? null : fapTO.getApBalanceadoKilosHectarea(),
                fapTO.getApBalanceadoAcumulado() == null ? null : fapTO.getApBalanceadoAcumulado(),
                fapTO.getApBiomasaProyectada() == null ? null : fapTO.getApBiomasaProyectada(),
                fapTO.getApConversionAlimenticia() == null ? null : fapTO.getApConversionAlimenticia(),
                fapTO.getApLaboratorio() == null ? "" : fapTO.getApLaboratorio(),
                fapTO.getApNauplio() == null ? "" : fapTO.getApNauplio())).forEachOrdered((reporteListadoGramaje) -> {
            lista.add(reporteListadoGramaje);
        });
        return genericReporteService.generarReporte(modulo, "reporteListadoFunAnalisisPesos.jrxml",
                usuarioEmpresaReporteTO, new HashMap<>(), lista);
    }

    @Override
    public byte[] generarReporteConsumoFecha(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String secCodigo,
            String fechaDesde, String fechaHasta, List<PrdConsumosTO> prdConsumosTO) throws Exception {

        List<ReporteConsumoPorFecha> lista = new ArrayList<>();
        prdConsumosTO.stream().map((pcTO) -> new ReporteConsumoPorFecha(secCodigo, fechaDesde,
                fechaHasta, // codigoSector,
                pcTO.getConsumoProducto() == null ? "" : pcTO.getConsumoProducto(),
                pcTO.getConsumoCodigo() == null ? "" : pcTO.getConsumoCodigo(),
                pcTO.getConsumoMedida() == null ? "" : pcTO.getConsumoMedida(),
                pcTO.getConsumoCantidad() == null ? null : pcTO.getConsumoCantidad())).forEachOrdered((reporteConsumoPorFecha) -> {
            lista.add(reporteConsumoPorFecha);
        });
        return genericReporteService.generarReporte(modulo, "reportConsumoPorFecha.jrxml", usuarioEmpresaReporteTO,
                new HashMap<>(), lista);

    }

    @Override
    public byte[] generarReporteUtilidadDiariaCorrida(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector, String piscina, String corrida, List<PrdUtilidadDiariaCorridaTO> listUtilidad) throws Exception {

        List<ReporteUtilidadDiariaCorrida> lista = new ArrayList<>();
        listUtilidad.stream().map((utilidad) -> new ReporteUtilidadDiariaCorrida(sector, piscina, corrida, utilidad.getuTipo(), utilidad.getuDescripcion(), utilidad.getuValorNumerico(), utilidad.getuValorTexto())).forEach((reporteUtilidadDiaria) -> {
            lista.add(reporteUtilidadDiaria);
        });
        return genericReporteService.generarReporte(modulo, "reportUtilidadDiaria.jrxml", usuarioEmpresaReporteTO,
                new HashMap<>(), lista);

    }

    @Override
    public byte[] generarReporteConsumoPiscina(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String codigoSector,
            String codigoPiscina, String numeroCorrida, String hectareas, Integer numLarvas, String fechaDesde,
            String fechaHasta, List<PrdConsumosTO> prdConsumosTO) throws Exception {

        List<ReporteConsumoPorPiscina> lista = new ArrayList<>();
        prdConsumosTO.stream().map((fapTO) -> new ReporteConsumoPorPiscina(codigoSector,
                codigoPiscina, numeroCorrida, new BigDecimal(hectareas), numLarvas, fechaDesde, fechaHasta,
                fapTO.getConsumoProducto() == null ? "" : fapTO.getConsumoProducto(),
                fapTO.getConsumoCodigo() == null ? "" : fapTO.getConsumoCodigo(),
                fapTO.getConsumoMedida() == null ? "" : fapTO.getConsumoMedida(),
                fapTO.getConsumoCantidad() == null ? null : fapTO.getConsumoCantidad())).forEachOrdered((reporteConsumoPorPiscina) -> {
            lista.add(reporteConsumoPorPiscina);
        });
        return genericReporteService.generarReporte(modulo, "reportConsumoPorPiscina.jrxml", usuarioEmpresaReporteTO,
                new HashMap<>(), lista);
    }

    @Override
    public byte[] generarReporteEconomicoPorFechas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, List<PrdCostoDetalleFechaTO> listaPrdCostoDetalleFechaTO) throws Exception {

        List<ReporteCostoDetalleFecha> lista = new ArrayList<>();
        listaPrdCostoDetalleFechaTO.stream().map((fapTO) -> new ReporteCostoDetalleFecha(fechaDesde, fechaHasta,
                fapTO.getCostoProducto() == null ? "" : fapTO.getCostoProducto(),
                fapTO.getCostoCodigo() == null ? "" : fapTO.getCostoCodigo(),
                fapTO.getCostoMedida() == null ? "" : fapTO.getCostoMedida(),
                fapTO.getCostoCantidad() == null ? null : fapTO.getCostoCantidad(),
                fapTO.getCostoTotal() == null ? null : fapTO.getCostoTotal(),
                fapTO.getCostoPorcentaje() == null ? null : fapTO.getCostoPorcentaje())).forEachOrdered((reporteCostoDetalleFecha) -> {
            lista.add(reporteCostoDetalleFecha);
        });
        return genericReporteService.generarReporte(modulo, "reportCostoDetalleFecha.jrxml", usuarioEmpresaReporteTO,
                new HashMap<>(), lista);

    }

    @Override
    public byte[] generarReporteEconomicoPorPiscinas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String codigoSector, String codigoPiscina, String codigoCorrida, String hectareas, Integer numLarvas,
            String fechaDesde, String fechaHasta, List<PrdListaCostosDetalleCorridaTO> prdListaCostosDetalleCorridaTO)
            throws Exception {

        List<ReporteCostoDetalladoCorrida> lista = new ArrayList<>();
        prdListaCostosDetalleCorridaTO.stream().map((fapTO) -> new ReporteCostoDetalladoCorrida(codigoSector,
                codigoPiscina, codigoCorrida, new BigDecimal(hectareas), numLarvas, fechaDesde, fechaHasta,
                fapTO.getCostoProducto() == null ? "" : fapTO.getCostoProducto(),
                fapTO.getCostoCodigo() == null ? "" : fapTO.getCostoCodigo(),
                fapTO.getCostoMedida() == null ? "" : fapTO.getCostoMedida(),
                fapTO.getCostoCantidad() == null ? null : fapTO.getCostoCantidad(),
                fapTO.getCostoTotal() == null ? null : fapTO.getCostoTotal(),
                fapTO.getCostoPorcentaje() == null ? null : fapTO.getCostoPorcentaje())).forEachOrdered((reporteCostoDetalladoCorrida) -> {
            lista.add(reporteCostoDetalladoCorrida);
        });
        return genericReporteService.generarReporte(modulo, "reportCostoDetalleCorrida.jrxml", usuarioEmpresaReporteTO,
                new HashMap<>(), lista);

    }

    @Override
    public byte[] generarReporteResumenPesca(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<PrdResumenCorridaTO> listaPrdListaResumenCorridaTO) throws Exception {

        return genericReporteService.generarReporte(modulo, "reportResumenPescas.jrxml", usuarioEmpresaReporteTO,
                new HashMap<>(), listaPrdListaResumenCorridaTO);
    }

    @Override
    public byte[] generarReporteResumenEconomicoSiembra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdResumenPescaSiembraTO> datos)
            throws Exception {
        return genericReporteService.generarReporte(modulo, "reportResumenSiembrasNew.jrxml", usuarioEmpresaReporteTO,
                new HashMap<>(), datos);
    }

    @Override
    public byte[] generarReporteResumenEconomicoPesca(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdResumenPescaSiembraTO> datos)
            throws Exception {
        return genericReporteService.generarReporte(modulo, "reportResumenPescasNew.jrxml", usuarioEmpresaReporteTO,
                new HashMap<>(), datos);
    }

    @Override
    public byte[] generarReportePicina(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdListaPiscinaTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportPiscina.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReporteSector(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdListaSectorTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportSector.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReportePrdLiquidacionProductoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdProducto> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportLiquidacionProducto.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReportePrdLiquidacionTallaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdTalla> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportLiquidacionTalla.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReporteSobrevivencia(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdListaSobrevivenciaTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportSobrevivencia.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    //
    @Override
    public byte[] generarReporteLiquidacionDetalleProducto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdLiquidacionDetalleProductoTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportLiquidacionDetalleProducto.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReporteLiquidacionDetalleProductoEmpacadora(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdLiquidacionDetalleProductoEmpacadoraTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportLiquidacionDetalleProductoEmpacadora.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReportePreLiquidacionDetalleProducto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdPreLiquidacionDetalleProductoTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportPreLiquidacionDetalleProducto.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    //report tallas
    @Override
    public byte[] generarReporteLiquidacionConsolidandoTallas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdFunLiquidacionConsolidandoTallasTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportLiquidacionConsolidandoTallas.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    //report tallas pre
    @Override
    public byte[] generarReportePreLiquidacionConsolidandoTallas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdFunPreLiquidacionConsolidandoTallasTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportPreLiquidacionConsolidandoTallas.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    // repórt consolidando tallas proveedor
    @Override
    public byte[] generarReporteLiquidacionConsolidandoTallasProveedor(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdFunLiquidacionConsolidandoTallasProveedorTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportLiquidacionConsolidandoTallasProveedor.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    //reporteClienets
    @Override
    public byte[] generarReporteLiquidacionConsolidandoClientes(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdLiquidacionConsolidandoClientesTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportLiquidacionConsolidandoClientes.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    //reporte Preliquidacion Clientes
    @Override
    public byte[] generarReportePreLiquidacionConsolidandoClientes(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdPreLiquidacionConsolidandoClientesTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportPreLiquidacionConsolidandoClientes.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    //reporteClienets
    @Override
    public byte[] generarReporteLiquidacionConsolidandoProveedores(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdLiquidacionConsolidandoProveedoresTO> listado, String sector, String proveedor, String fechaDesde, String fechaHasta, String comisionista) throws Exception {

        List<ReporteLiquidacionConsolidadoProveedores> lista = new ArrayList<>();
        for (PrdLiquidacionConsolidandoProveedoresTO item : listado) {
            ReporteLiquidacionConsolidadoProveedores itemReporte = new ReporteLiquidacionConsolidadoProveedores();
            itemReporte.setLiqEmpresaProveedor(item.getLiqEmpresaProveedor());
            itemReporte.setLiqCodigoProveedor(item.getLiqCodigoProveedor());
            itemReporte.setLiqNombreProveedor(item.getLiqNombreProveedor());
            itemReporte.setLiqIdentificacion(item.getLiqIdentificacion());
            itemReporte.setLiqLibrasEnviadas(item.getLiqLibrasEnviadas());
            itemReporte.setLiqLibrasRecibidas(item.getLiqLibrasRecibidas());
            itemReporte.setLiqLibrasBasura(item.getLiqLibrasBasura());
            itemReporte.setLiqLibrasRetiradas(item.getLiqLibrasRetiradas());
            itemReporte.setLiqLibrasNetas(item.getLiqLibrasNetas());
            itemReporte.setLiqLibrasEntero(item.getLiqLibrasEntero());
            itemReporte.setLiqLibrasCola(item.getLiqLibrasCola());
            itemReporte.setLiqLibrasColaProcesadas(item.getLiqLibrasColaProcesadas());
            itemReporte.setLiqAnimalesCosechados(item.getLiqAnimalesCosechados());
            itemReporte.setLiqTotalMonto(item.getLiqTotalMonto());
            itemReporte.setLiqComisionista(comisionista);
            itemReporte.setPrdComisionista(item.getPrdComisionista());
            itemReporte.setFechaDesde(fechaDesde);
            itemReporte.setFechaHasta(fechaHasta);
            itemReporte.setSector(sector);
            itemReporte.setProveedor(proveedor);
            itemReporte.setComisionista(comisionista);
            lista.add(itemReporte);
        }

        return genericReporteService.generarReporte(modulo, "reportLiquidacionConsolidandoProveedores.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), lista);
    }

    //reporteConsultas
    @Override
    public byte[] generarReporteLiquidacionConsultas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdLiquidacionConsultaTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportLiquidacionListado.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);

    }

    //reporteConsultas
    @Override
    public byte[] generarReporteLiquidacionConsultasEmpacadora(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdLiquidacionConsultaEmpacadoraTO> listado, String proveedor) throws Exception {
        if (proveedor != null && proveedor.equals("OCE")) {
            return genericReporteService.generarReporte(modulo, "reportLiquidacionListadoEmpacadoraProveedor.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
        } else {
            return genericReporteService.generarReporte(modulo, "reportLiquidacionListadoEmpacadora.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
        }
    }

    //reporte preLiquidacion Consultas
    @Override
    public byte[] generarReportePreLiquidacionConsultas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdPreLiquidacionConsultaTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportPreLiquidacionListado.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);

    }

    //reporte PreLiquidacion Detalle
    @Override
    public byte[] generarReportePreLiquidacionesDetalle(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdPreLiquidacionesDetalleTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportPreLiquidacionesDetalle.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReporteLiquidacionesDetalle(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdLiquidacionesDetalleTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportLiquidacionesDetalle.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReportePrdListadoFitoplanctonTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdListadoFitoplanctonTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportPrdListadoFitoplancton.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public Map<String, Object> exportarReporteConsumosFecha(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String secCodigo,
            String fechaDesde, String fechaHasta, List<PrdConsumosTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de piscinas");
            listaCabecera.add("SSector: " + secCodigo);
            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SProducto" + "¬" + "SCódigo" + "¬" + "SMedida" + "¬" + "SCantidad");
            listado.forEach((consumos) -> {
                listaCuerpo.add((consumos.getConsumoProducto() == null ? "B"
                        : "S" + consumos.getConsumoProducto())
                        + "¬"
                        + (consumos.getConsumoCodigo() == null ? "B"
                        : "S" + consumos.getConsumoCodigo())
                        + "¬" + (consumos.getConsumoMedida() == null ? "B"
                        : "S" + consumos.getConsumoMedida())
                        + "¬" + (consumos.getConsumoCantidad() == null ? "B"
                        : "S" + consumos.getConsumoCantidad()));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteConsumosPiscina(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String codigoSector, String nombreSector, String piscina,
            String codigoPiscina, String numeroCorrida, String hectareas, Integer numLarvas, String fechaDesde,
            String fechaHasta, List<PrdConsumosTO> prdConsumosTO, boolean ordenProduccion) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add(ordenProduccion ? "SReporte de orden de producción" : "SReporte de consumos por piscina");
            listaCabecera.add((ordenProduccion ? "SCentro de producción: " : "SSector: ") + codigoSector);
            listaCabecera.add((ordenProduccion ? "SCentro de costo: " : "SPiscina: ") + piscina);
            listaCabecera.add((ordenProduccion ? "SOrden de producción: " : "SCorrrida: ") + numeroCorrida);
            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SProducto" + "¬" + "SCódigo" + "¬" + "SMedida" + "¬" + "SCantidad");
            prdConsumosTO.forEach((consumos) -> {
                listaCuerpo.add((consumos.getConsumoProducto() == null ? "B"
                        : "S" + consumos.getConsumoProducto())
                        + "¬"
                        + (consumos.getConsumoCodigo() == null ? "B"
                        : "S" + consumos.getConsumoCodigo())
                        + "¬" + (consumos.getConsumoMedida() == null ? "B"
                        : "S" + consumos.getConsumoMedida())
                        + "¬" + (consumos.getConsumoCantidad() == null ? "B"
                        : "S" + consumos.getConsumoCantidad()));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReportePiscinas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdListaPiscinaTO> listado, boolean centroCosto) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add(centroCosto ? "SReporte de centros de costo" : "SReporte de piscinas");
            listaCabecera.add("S");
            listaCuerpo.add("SNúmero" + "¬" + "SNombre" + "¬" + "SHectareaje" + "¬" + "SInactivo" + "¬" + "SCuenta de costo directo" + "¬" + "SCuenta de costo indirecto" + "¬" + "SCuenta de costo transferencia" + "¬" + "SCuenta de costo producto terminado");
            listado.forEach((piscina) -> {
                listaCuerpo.add((piscina.getPisNumero() == null ? "B"
                        : "S" + piscina.getPisNumero())
                        + "¬"
                        + (piscina.getPisNombre() == null ? "B" : "S" + piscina.getPisNombre())
                        + "¬" + (piscina.getPisHectareaje() == null ? "B" : "D" + piscina.getPisHectareaje())
                        + "¬" + (piscina.getPisActiva() ? "SActivo" : "SInactivo")
                        + "¬" + (piscina.getCtaCostoDirecto() == null ? "B" : "S" + piscina.getCtaCostoDirecto())
                        + "¬" + (piscina.getCtaCostoIndirecto() == null ? "B" : "S" + piscina.getCtaCostoIndirecto())
                        + "¬" + (piscina.getCtaCostoTransferencia() == null ? "B" : "S" + piscina.getCtaCostoTransferencia())
                        + "¬" + (piscina.getCtaCostoProductoTerminado() == null ? "B" : "S" + piscina.getCtaCostoProductoTerminado())
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteSectores(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdListaSectorTO> listado, boolean centroProduccion) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add(centroProduccion ? "SReporte de centro de producción" : "SReporte de sectores");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SNombre" + "¬" + "SDireccion" + "¬" + "STelefono" + "¬" + "SLatitud" + "¬" + "SLongitud" + "¬" + "SInactivo");
            listado.forEach((sector) -> {
                listaCuerpo.add((sector.getSecCodigo() == null ? "B" : "S" + sector.getSecCodigo())
                        + "¬" + (sector.getSecNombre() == null ? "B" : "S" + sector.getSecNombre())
                        + "¬" + (sector.getSecDireccion() == null ? "B" : "S" + sector.getSecDireccion())
                        + "¬" + (sector.getSecTelefono() == null ? "B" : "S" + sector.getSecTelefono())
                        + "¬" + (sector.getSecLatitud() == null ? "B" : "S" + sector.getSecLatitud())
                        + "¬" + (sector.getSecLongitud() == null ? "B" : "S" + sector.getSecLongitud())
                        + "¬" + (sector.getSecActivo() ? "SActivo" : "SInactivo"));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteDinamico(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String titulo, List<String> columnas, List<String[]> datos, String nombreSector, String piscina, String corrida) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("S" + titulo);
            listaCabecera.add("SSector: " + nombreSector);
            listaCabecera.add("SPiscina: " + piscina);
            listaCabecera.add("SCorrida: " + corrida);
            String titulos = this.generarColumnas(columnas);
            listaCuerpo.add(titulos);
            String cuerpo = "";
            for (int i = 0; i < datos.size(); i++) {
                for (int j = 0; j < columnas.size(); j++) {
                    if (j < columnas.size() - 1) {
                        cuerpo += comprobarNumericoString(datos.get(i)[j] != null ? datos.get(i)[j] : "");
                    } else {
                        cuerpo += comprobarNumericoString(datos.get(i)[j] != null ? datos.get(i)[j] : "");
                    }
                }
                listaCuerpo.add(cuerpo);
                cuerpo = "";
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteDinamicoConsumosMensuales(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String titulo, List<String> columnas, List<String[]> datos, String nombreSector, String bodega, String desde, String hasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("S" + titulo);
            listaCabecera.add("SSector: " + nombreSector);
            listaCabecera.add("SBodega: " + bodega);
            listaCabecera.add("SDesde: " + desde);
            listaCabecera.add("SHasta: " + hasta);

            String titulos = this.generarColumnas(columnas);
            listaCuerpo.add(titulos);
            String cuerpo = "";
            for (int i = 0; i < datos.size(); i++) {
                for (int j = 0; j < columnas.size(); j++) {
                    if (j < columnas.size() - 1) {
                        cuerpo += comprobarNumericoString(datos.get(i)[j] != null ? datos.get(i)[j] : "");
                    } else {
                        cuerpo += comprobarNumericoString(datos.get(i)[j] != null ? datos.get(i)[j] : "");
                    }
                }
                listaCuerpo.add(cuerpo);
                cuerpo = "";
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteConsumosDiarios(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String titulo, List<String> columnas, List<Object> datos, String nombreSector, String bodega, String desde, String hasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("S" + titulo);
            listaCabecera.add("SSector: " + nombreSector);
            listaCabecera.add("SBodega: " + bodega);
            listaCabecera.add("SDesde: " + desde);
            listaCabecera.add("SHasta: " + hasta);
            listaCabecera.add("S");

            for (int h = 0; h < datos.size(); h++) {
                String titulos = this.generarColumnas(columnas);
                listaCuerpo.add(titulos);
                String cuerpo = "";
                List<Object> listaDeDatos = (List<Object>) datos.get(h);
                for (int i = 0; i < listaDeDatos.size(); i++) {
                    List<String> listaDeValores = (List<String>) listaDeDatos.get(i);
                    for (int j = 0; j < columnas.size(); j++) {
                        if (j > 3) {
                            cuerpo += comprobarNumericoString(listaDeValores.get(j) != null ? listaDeValores.get(j) : "");
                        } else {
                            cuerpo += (listaDeValores.get(j) != null ? "S" + listaDeValores.get(j) : "B") + "¬";
                        }
                    }
                    listaCuerpo.add(cuerpo);
                    cuerpo = "";
                }
                listaCuerpo.add("");
            }

            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteDinamicoConsumoPiscinaPeriodo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String codigoSector, String nombreSector, String numeroPiscina, String codigoCorrida,
            String periodo, String fechaInicio, String fechaFin, String numeroLarvas, String numeroHectareas, String titulo, List<String> columnas, List<String[]> datos) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("S" + titulo);
            listaCabecera.add("SSector: " + nombreSector);
            listaCabecera.add("SPiscina: " + numeroPiscina);
            listaCabecera.add("SPeriodo: " + periodo);
            listaCabecera.add("SDesde: " + fechaInicio);
            listaCabecera.add("SHasta: " + fechaFin);
            listaCabecera.add("S");
            String titulos = this.generarColumnas(columnas);
            listaCuerpo.add(titulos);
            String cuerpo = "";
            for (int i = 0; i < datos.size(); i++) {
                for (int j = 0; j < columnas.size(); j++) {
                    if (j < columnas.size() - 1) {
                        if (j < 3) {
                            cuerpo += (datos.get(i)[j] == null ? "B" : "S" + datos.get(i)[j]) + "¬";
                        } else {
                            cuerpo += (datos.get(i)[j] == null ? "B" : "I" + datos.get(i)[j]) + "¬";
                        }
                    } else {
                        if (j < 3) {
                            cuerpo += (datos.get(i)[j] == null ? "B" : "S" + datos.get(i)[j]);
                        } else {
                            cuerpo += (datos.get(i)[j] == null ? "B" : "I" + datos.get(i)[j]);
                        }
                    }
                }
                listaCuerpo.add(cuerpo);
                cuerpo = "";
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteDinamicoCostosProductosProcesos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector, String hasta, List<String> columnas, List<String[]> datos) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte costos de productos en proceso");
            listaCabecera.add("SSector: " + sector);
            listaCabecera.add("SHasta: " + hasta);
            String titulos = this.generarColumnas(columnas);
            listaCuerpo.add(titulos);
            String cuerpo = "";
            for (int i = 0; i < datos.size(); i++) {
                for (int j = 0; j < columnas.size(); j++) {
                    if (j < columnas.size() - 1) {
                        cuerpo += (datos.get(i)[j] == null ? "B" : (j > 2 ? "D" : "S") + datos.get(i)[j]) + "¬";
                    } else {
                        cuerpo += (datos.get(i)[j] == null ? "B" : (j > 2 ? "D" : "S") + datos.get(i)[j]);
                    }
                }
                listaCuerpo.add(cuerpo);
                cuerpo = "";
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteConsumosFechaDesglosado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector, String desde, String hasta, List<String> columnas, List<String[]> datos) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de consumos por fecha desglosado");
            listaCabecera.add("SSector: " + sector);
            listaCabecera.add("SDesde: " + desde + " Hasta: " + hasta);
            listaCabecera.add("S");
            String titulos = this.generarColumnas(columnas);
            listaCuerpo.add(titulos);
            String cuerpo = "";
            for (int i = 0; i < datos.size(); i++) {
                for (int j = 0; j < columnas.size(); j++) {
                    if (j < columnas.size() - 1) {
                        cuerpo += (datos.get(i)[j] == null ? "B" : "S" + datos.get(i)[j]) + "¬";
                    } else {
                        cuerpo += (datos.get(i)[j] == null ? "B" : "S" + datos.get(i)[j]);
                    }
                }
                listaCuerpo.add(cuerpo);
                cuerpo = "";
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteConsumosProductosProcesos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector, String hasta, List<String> columnas, List<String[]> datos) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte costos de productos en proceso");
            listaCabecera.add("SSector: " + sector);
            listaCabecera.add("SFecha: " + hasta);
            String titulos = this.generarColumnas(columnas);
            listaCuerpo.add(titulos);
            String cuerpo = "";
            for (int i = 0; i < datos.size(); i++) {
                for (int j = 0; j < columnas.size(); j++) {
                    if (j < columnas.size() - 1) {
                        cuerpo += (datos.get(i)[j] == null ? "B" : "S" + datos.get(i)[j]) + "¬";
                    } else {
                        cuerpo += (datos.get(i)[j] == null ? "B" : "S" + datos.get(i)[j]);
                    }
                }
                listaCuerpo.add(cuerpo);
                cuerpo = "";
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteCostoPiscinaSemanal(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreSector, String sector, String corrida, String numeroPiscina, String desde, String hasta, String hectareas, Integer numLarvas, List<String> columnas, List<String[]> datos) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte costos por piscina semanal");
            listaCabecera.add("SSector: " + nombreSector);
            listaCabecera.add("SPiscina: " + numeroPiscina);
            listaCabecera.add("SCorrida: " + corrida);
            listaCabecera.add("SDesde: " + desde);
            listaCabecera.add("SHasta: " + hasta);
            String titulos = this.generarColumnas(columnas);
            listaCuerpo.add(titulos);
            String cuerpo = "";
            for (int i = 0; i < datos.size(); i++) {
                for (int j = 0; j < columnas.size(); j++) {
                    if (j < columnas.size() - 1) {
                        cuerpo += (datos.get(i)[j] == null ? "B" : (j >= 3 ? "D" : "S") + datos.get(i)[j]) + "¬";
                    } else {
                        cuerpo += (datos.get(i)[j] == null ? "B" : (j >= 3 ? "D" : "S") + datos.get(i)[j]);
                    }
                }
                listaCuerpo.add(cuerpo);
                cuerpo = "";
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReportePorFechaProrrateado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector, String nombreSector, String desde, String hasta, List<String> columnas, List<String[]> datos) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte costos por fechas (Porrateado) (CATEGORIA)");
            listaCabecera.add("SSector: " + nombreSector);
            listaCabecera.add("SDesde: " + desde);
            listaCabecera.add("SHasta: " + hasta);

            String titulos = this.generarColumnas(columnas);
            listaCuerpo.add(titulos);
            String cuerpo = "";
            for (int i = 0; i < datos.size(); i++) {
                for (int j = 0; j < columnas.size(); j++) {
                    if (j < columnas.size() - 1) {
                        cuerpo += (datos.get(i)[j] == null ? "B" : (j > 2 ? "D" : "S") + datos.get(i)[j]) + "¬";
                    } else {
                        cuerpo += (datos.get(i)[j] == null ? "B" : (j > 2 ? "D" : "S") + datos.get(i)[j]);
                    }
                }
                listaCuerpo.add(cuerpo);
                cuerpo = "";
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    private String generarColumnas(List<String> lista) {
        String columnas = "";
        for (int i = 0; i < lista.size(); i++) {
            if (i < lista.size() - 1) {
                columnas += "S" + lista.get(i) + "¬";
            } else {
                columnas += "S" + lista.get(i);
            }
        }
        return columnas;
    }

    @Override
    public Map<String, Object> exportarReporteListadoFunAnalisisPesos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String codigoSector, String nomSector, List<PrdListaFunAnalisisPesosTO> prdListaFunAnalisisPesosTO, String fechaHasta) {

        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            String nombreSector = nomSector;
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("TListado de Análisis de Pesos y creciemintos");
            listaCabecera.add("SSector: " + nombreSector);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("T");
            listaCuerpo.add("TSector" + "¬" + "TPiscina" + "¬" + "TCorrida" + "¬" + "THectareas" + "¬"
                    + "TFecha Siembra;" + "¬" + "TForma Siembra" + "¬" + "TDensidad Metro Cuadrado" + "¬"
                    + "TSobrevivencia Metro Cuadrado" + "¬" + "TRaleo" + "¬" + "TEdad" + "¬" + "TPeso Ideal" + "¬"
                    + "TPeso Sobre Ideal" + "¬" + "TPeso Promedio Actual" + "¬" + "TPeso Incremento Semana 4" + "¬"
                    + "TPeso Incremento Semana 3" + "¬" + "TPeso Incremento Semana 2" + "¬"
                    + "TPeso Incremento Semana 1" + "¬" + "TPeso Incremento Promedio" + "¬" + "TBalanceado Tipo" + "¬"
                    + "TBalanceado Sacos" + "¬" + "TBalanceado Kilos Hectarea" + "¬" + "TBalanceado Acumulado" + "¬"
                    + "TBiomasa Proyectada" + "¬" + "TConversion Alimenticia" + "¬" + "TLaboratorio" + "¬"
                    + "TNauplio");
            prdListaFunAnalisisPesosTO.forEach((prdListaFunAnalisisPesos) -> {
                listaCuerpo.add((prdListaFunAnalisisPesos.getApSector() == null ? "B"
                        : "S" + prdListaFunAnalisisPesos.getApSector())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApPiscina() == null ? "B"
                        : "S" + prdListaFunAnalisisPesos.getApPiscina())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApCorrida() == null ? "B"
                        : "S" + prdListaFunAnalisisPesos.getApCorrida())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApHectareas() == null ? "B"
                        : "D" + prdListaFunAnalisisPesos.getApHectareas())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApFechaSiembra() == null ? "B"
                        : "S" + prdListaFunAnalisisPesos.getApFechaSiembra())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApFormaSiembra() == null ? "B"
                        : "S" + prdListaFunAnalisisPesos.getApFormaSiembra())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApDensidadMetroCuadrado() == null ? "B"
                        : "D" + prdListaFunAnalisisPesos.getApDensidadMetroCuadrado())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApSobrevivenciaMetroCuadrado() == null ? "B"
                        : "D" + prdListaFunAnalisisPesos.getApSobrevivenciaMetroCuadrado())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApRaleo() == null ? "B"
                        : "D" + prdListaFunAnalisisPesos.getApRaleo())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApEdad() == null ? "B"
                        : "D" + prdListaFunAnalisisPesos.getApEdad())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApPesoIdeal() == null ? "B"
                        : "D" + prdListaFunAnalisisPesos.getApPesoIdeal())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApPesoSobreIdeal() == null ? "B"
                        : "D" + prdListaFunAnalisisPesos.getApPesoSobreIdeal())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApPesoPromedioActual() == null ? "B"
                        : "D" + prdListaFunAnalisisPesos.getApPesoPromedioActual())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApPesoIncrementoSemana4() == null ? "B"
                        : "D" + prdListaFunAnalisisPesos.getApPesoIncrementoSemana4())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApPesoIncrementoSemana3() == null ? "B"
                        : "D" + prdListaFunAnalisisPesos.getApPesoIncrementoSemana3())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApPesoIncrementoSemana2() == null ? "B"
                        : "D" + prdListaFunAnalisisPesos.getApPesoIncrementoSemana2())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApPesoIncrementoSemana1() == null ? "B"
                        : "D" + prdListaFunAnalisisPesos.getApPesoIncrementoSemana1())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApPesoIncrementoPromedio() == null ? "B"
                        : "D" + prdListaFunAnalisisPesos.getApPesoIncrementoPromedio())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApBalanceadoTipo() == null ? "B"
                        : "S" + prdListaFunAnalisisPesos.getApBalanceadoTipo())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApBalanceadoSacos() == null ? "B"
                        : "D" + prdListaFunAnalisisPesos.getApBalanceadoSacos())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApBalanceadoKilosHectarea() == null ? "B"
                        : "D" + prdListaFunAnalisisPesos.getApBalanceadoKilosHectarea())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApBalanceadoAcumulado() == null ? "B"
                        : "D" + prdListaFunAnalisisPesos.getApBalanceadoAcumulado())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApBiomasaProyectada() == null ? "B"
                        : "D" + prdListaFunAnalisisPesos.getApBiomasaProyectada())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApConversionAlimenticia() == null ? "B"
                        : "D" + prdListaFunAnalisisPesos.getApConversionAlimenticia())
                        + "¬"
                        + (prdListaFunAnalisisPesos.getApLaboratorio() == null ? "B"
                        : "S" + prdListaFunAnalisisPesos.getApLaboratorio())
                        + "¬" + (prdListaFunAnalisisPesos.getApNauplio() == null ? "B"
                        : "S" + prdListaFunAnalisisPesos.getApNauplio()));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteGramaje(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String codigoSector, String codigoPiscina,
            String codigoCorrida, List<PrdListadoGrameajeTO> prdListadoGrameajeTO, String fechaDesde, String fechaHasta, boolean esSamaniego) throws Exception {

        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado de Gramajes");
            listaCabecera.add("TSector: " + codigoSector);
            listaCabecera.add("TPiscina: " + codigoPiscina);
            listaCabecera.add("TCorrida: " + codigoCorrida);
            listaCabecera.add("TDesde: " + fechaDesde);
            listaCabecera.add("THasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add(
                    "TDías" + "¬"
                    + "TFecha" + "¬"
                    + "TPeso Promedio" + "¬"
                    + "TIncremento" + "¬"
                    + (esSamaniego ? "TIPS" : "TIncremento semanal(promedio)") + "¬"
                    + "TSobreviviencia" + "¬"
                    + "TBiomasa" + "¬"
                    + "TBiomasa hectárea" + "¬"
                    + "TBalanceado" + "¬"
                    + (esSamaniego ? "TKg/Has" : "TBalanceado hectárea") + "¬"
                    + "TBalanceado costo" + "¬"
                    + (esSamaniego ? "TCFR" : "TConversión alimenticia") + "¬"
                    + "TComentario");
            prdListadoGrameajeTO.forEach((prdListadoGrameaje) -> {
                listaCuerpo.add(
                        (prdListadoGrameaje.getGraDias() == null ? "B" : "I" + prdListadoGrameaje.getGraDias()) + "¬"
                        + (prdListadoGrameaje.getGraFecha() == null ? "B" : "S" + prdListadoGrameaje.getGraFecha()) + "¬"
                        + (prdListadoGrameaje.getGratPromedio() == null ? "B" : "D" + prdListadoGrameaje.getGratPromedio()) + "¬"
                        + (prdListadoGrameaje.getGraiPromedio() == null ? "B" : "D" + prdListadoGrameaje.getGraiPromedio()) + "¬"
                        + (prdListadoGrameaje.getGraPromedioSemanal() == null ? "B" : "D" + prdListadoGrameaje.getGraPromedioSemanal()) + "¬"
                        + (prdListadoGrameaje.getGraSobrevivencia() == null ? "B" : "D" + prdListadoGrameaje.getGraSobrevivencia()) + "¬"
                        + (prdListadoGrameaje.getGraBiomasa() == null ? "B" : "D" + prdListadoGrameaje.getGraBiomasa()) + "¬"
                        + (prdListadoGrameaje.getGraBiomasaHectarea() == null ? "B" : "D" + prdListadoGrameaje.getGraBiomasaHectarea()) + "¬"
                        + (prdListadoGrameaje.getGraBalanceado() == null ? "B" : "D" + prdListadoGrameaje.getGraBalanceado()) + "¬"
                        + (prdListadoGrameaje.getGraBalanceadoHectarea() == null ? "B" : "D" + prdListadoGrameaje.getGraBalanceadoHectarea()) + "¬"
                        + (prdListadoGrameaje.getGraBalanceadoCosto() == null ? "B" : "D" + prdListadoGrameaje.getGraBalanceadoCosto()) + "¬"
                        + (prdListadoGrameaje.getGraConversionAlimenticia() == null ? "B" : "D" + prdListadoGrameaje.getGraConversionAlimenticia()) + "¬"
                        + (prdListadoGrameaje.getGraComentario() == null ? "B" : "S" + prdListadoGrameaje.getGraComentario())
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteEconomicoPorPiscinas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String codigoSector,
            String nombreSector, String codigoPiscina, String codigoCorrida, String hectareas, Integer numLarvas, String fechaDesde,
            String fechaHasta, List<PrdListaCostosDetalleCorridaTO> prdListaCostosDetalleCorridaTO, boolean costoProduccion) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add(costoProduccion ? "TReporte de costos por orden de producción" : "TReporte de costos por Piscina");
            listaCabecera.add((costoProduccion ? "TCentro de producción: " : "TSector: ") + nombreSector);
            listaCabecera.add((costoProduccion ? "TCentro de costo: " : "TPiscina: ") + codigoPiscina);
            listaCabecera.add((costoProduccion ? "TOrden de producción: " : "TCorrida: ") + codigoCorrida);
            listaCabecera.add("TDesde: " + fechaDesde);
            listaCabecera.add("THasta: " + fechaHasta);
            listaCabecera.add("T");
            listaCuerpo.add("TProducto" + "¬" + "TCódigo" + "¬" + "TMedida" + "¬" + "TCantidad" + "¬" + "TTotal" + "¬"
                    + "TPorcentaje");
            prdListaCostosDetalleCorridaTO.forEach((prdListaCostosDetalleCorrida) -> {
                listaCuerpo.add(
                        (prdListaCostosDetalleCorrida.getCostoProducto() == null ? "B"
                        : "S" + prdListaCostosDetalleCorrida.getCostoProducto())
                        + "¬"
                        + (prdListaCostosDetalleCorrida.getCostoCodigo() == null ? "B"
                        : "S" + prdListaCostosDetalleCorrida.getCostoCodigo())
                        + "¬"
                        + (prdListaCostosDetalleCorrida.getCostoMedida() == null ? "B"
                        : "S" + prdListaCostosDetalleCorrida.getCostoMedida())
                        + "¬"
                        + (prdListaCostosDetalleCorrida.getCostoCantidad() == null ? "B"
                        : "D" + prdListaCostosDetalleCorrida.getCostoCantidad().toString())
                        + "¬"
                        + (prdListaCostosDetalleCorrida.getCostoTotal() == null ? "B"
                        : "D" + prdListaCostosDetalleCorrida.getCostoTotal().toString())
                        + "¬"
                        + (prdListaCostosDetalleCorrida.getCostoPorcentaje() == null ? "B"
                        : "D" + prdListaCostosDetalleCorrida.getCostoPorcentaje().toString())
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteUtilidadDiariaCorrida(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector, String piscina, String corrida,
            List<PrdUtilidadDiariaCorridaTO> listUtilidad, List<PrdUtilidadDiariaCorridaTO> listaResumenBiologico, List<PrdUtilidadDiariaCorridaTO> listaResumenFinanciero,
            List<PrdUtilidadDiariaCorridaTO> listaConsumoBalanceado) {

        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Utilidad diaria");
            listaCabecera.add("SSector: " + sector);
            listaCabecera.add("SPiscina: " + piscina);
            listaCabecera.add("SCorrida: " + corrida);
            listaCabecera.add("S");
            listaCuerpo.add("S");
            listaCuerpo.add("S RESUMEN BIOLOGICO");
            listaCuerpo.add("S");
            listaCuerpo.add("SDescripción" + "¬" + "SValor");
            listaResumenBiologico.stream().map((itemLista) -> {
                String item = "S" + itemLista.getuDescripcion()
                        + "¬";
                if (itemLista.getuValorTexto() != null && itemLista.getuValorNumerico() != null) {
                    item += ("S" + itemLista.getuValorNumerico() + " " + itemLista.getuValorTexto());
                } else if (itemLista.getuValorTexto() == null) {
                    item += ("D" + (itemLista.getuValorNumerico() == null ? "" : itemLista.getuValorNumerico()));
                } else {
                    item += ("S" + itemLista.getuValorTexto());
                }
                return item;
            }).forEachOrdered((item) -> {
                listaCuerpo.add(item);
            });

            listaCuerpo.add("S");
            listaCuerpo.add("S RESUMEN FINANCIERO");
            listaCuerpo.add("S");
            listaCuerpo.add("SDescripción" + "¬" + "SValor");
            listaResumenFinanciero.stream().map((itemLista) -> {
                String item = "S" + itemLista.getuDescripcion()
                        + "¬";
                if (itemLista.getuValorTexto() != null && itemLista.getuValorNumerico() != null) {
                    item += ("S" + itemLista.getuValorNumerico() + " " + itemLista.getuValorTexto());
                } else if (itemLista.getuValorTexto() == null) {
                    item += ("D" + (itemLista.getuValorNumerico() == null ? "" : itemLista.getuValorNumerico()));
                } else {
                    item += ("S" + itemLista.getuValorTexto());
                }
                return item;
            }).forEachOrdered((item) -> {
                listaCuerpo.add(item);
            });

            listaCuerpo.add("S");
            listaCuerpo.add("S CONSUMO BALANCEADO");
            listaCuerpo.add("S");
            listaCuerpo.add("SDescripción" + "¬" + "SValor");
            listaConsumoBalanceado.stream().map((itemLista) -> {
                String item = "S" + itemLista.getuDescripcion()
                        + "¬";
                if (itemLista.getuValorTexto() != null && itemLista.getuValorNumerico() != null) {
                    item += ("S" + itemLista.getuValorNumerico() + " " + itemLista.getuValorTexto());
                } else if (itemLista.getuValorTexto() == null) {
                    item += ("D" + (itemLista.getuValorNumerico() == null ? "" : itemLista.getuValorNumerico()));
                } else {
                    item += ("S" + itemLista.getuValorTexto());
                }
                return item;
            }).forEachOrdered((item) -> {
                listaCuerpo.add(item);
            });

            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReportePrdFunCostosPorFechaSimpleTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdFunCostosPorFechaSimpleTO> reportePrdFunCostosPorFechaSimpleTOs, String fechaDesde, String fechaHasta, String sector, String nombreSector) throws Exception {

        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SCosto Detalle por Fecha Simple");
            listaCabecera.add("SSector: " + nombreSector);
            listaCabecera.add("TDesde: " + fechaDesde);
            listaCabecera.add("THasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SSector" + "¬" + "SPiscina" + "¬" + "SCorrida" + "¬" + "STotal");

            reportePrdFunCostosPorFechaSimpleTOs.forEach((prdFunCostosPorFechaSimple) -> {
                listaCuerpo.add((prdFunCostosPorFechaSimple.getCosto_sector() == null ? "B"
                        : "S" + prdFunCostosPorFechaSimple.getCosto_sector())
                        + "¬"
                        + (prdFunCostosPorFechaSimple.getCosto_piscina() == null ? "B"
                        : "S" + prdFunCostosPorFechaSimple.getCosto_piscina())
                        + "¬"
                        + (prdFunCostosPorFechaSimple.getCosto_corrida() == null ? "B"
                        : "S" + prdFunCostosPorFechaSimple.getCosto_corrida())
                        + "¬" + (prdFunCostosPorFechaSimple.getCosto_total() == null ? "B"
                        : "D" + prdFunCostosPorFechaSimple.getCosto_total()));
            });

            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteCostoDetalleFecha(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<PrdCostoDetalleFechaTO> prdCostoDetalleFechaTO, String fechaDesde, String fechaHasta, String nombreSector, String sector) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SCosto Detalle por Fecha (CATEGORIA)");
            listaCabecera.add("SSector: " + nombreSector);
            listaCabecera.add("TDesde: " + fechaDesde);
            listaCabecera.add("THasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCosto Pro." + "¬" + "SCosto Cod." + "¬" + "SCosto Med." + "¬" + "SCantidad" + "¬"
                    + "STotal" + "¬" + "SPorcentaje");

            prdCostoDetalleFechaTO.forEach((prdListadoCostoDetalleFechaTO) -> {
                listaCuerpo.add((prdListadoCostoDetalleFechaTO.getCostoProducto() == null ? "B"
                        : "S" + prdListadoCostoDetalleFechaTO.getCostoProducto())
                        + "¬"
                        + (prdListadoCostoDetalleFechaTO.getCostoCodigo() == null ? "B"
                        : "S" + prdListadoCostoDetalleFechaTO.getCostoCodigo())
                        + "¬"
                        + (prdListadoCostoDetalleFechaTO.getCostoMedida() == null ? "B"
                        : "S" + prdListadoCostoDetalleFechaTO.getCostoMedida())
                        + "¬"
                        + (prdListadoCostoDetalleFechaTO.getCostoCantidad() == null ? "B"
                        : "D" + prdListadoCostoDetalleFechaTO.getCostoCantidad())
                        + "¬"
                        + (prdListadoCostoDetalleFechaTO.getCostoTotal() == null ? "B"
                        : "D" + prdListadoCostoDetalleFechaTO.getCostoTotal())
                        + "¬" + (prdListadoCostoDetalleFechaTO.getCostoPorcentaje() == null ? "B"
                        : "D" + prdListadoCostoDetalleFechaTO.getCostoPorcentaje()));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteCostosMensuales(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<String> columnas, List<String[]> datos, String sector, String nombreSector, String bodega, String desde, String hasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Costos Mensuales");
            if (sector != null && !sector.equals("")) {
                listaCabecera.add("SSector: " + nombreSector);
            }
            if (bodega != null && !bodega.equals("")) {
                listaCabecera.add("SBodega: " + bodega);
            }
            listaCabecera.add("SDesde: " + desde);
            listaCabecera.add("SHasta: " + hasta);

            listaCabecera.add("S");
            String titulos = this.generarColumnas(columnas);
            listaCuerpo.add(titulos);
            String cuerpo = "";
            for (int i = 0; i < datos.size(); i++) {
                for (int j = 0; j < columnas.size(); j++) {
                    if (j < columnas.size() - 1) {
                        cuerpo += comprobarNumericoString(datos.get(i)[j] != null ? datos.get(i)[j] : "");
                    } else {
                        cuerpo += comprobarNumericoString(datos.get(i)[j] != null ? datos.get(i)[j] : "");
                    }
                }
                listaCuerpo.add(cuerpo);
                cuerpo = "";
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteGrameajePromedioPorPiscina(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Object[][] datos, List<String> columnas, String sector) throws Exception {

        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SGrameajes de Piscinas en Proceso");
            listaCabecera.add("SSector: " + sector);
            listaCabecera.add("S");
            String nombresColumnas = "";

            for (int i = 0; i < columnas.size(); i++) {
                nombresColumnas = nombresColumnas + "S" + columnas.get(i) + "¬";
            }
            listaCuerpo.add(nombresColumnas);
            String dato = "";
            for (Object[] dato1 : datos) {
                dato = "S" + dato1[0] + "¬" + "S" + dato1[1] + "¬" + "S" + dato1[2] + "¬";
                for (int j = 3; j < columnas.size(); j++) {
                    dato += comprobarNumericoString(dato1[j] != null ? dato1[j].toString() : "");
                }
                listaCuerpo.add(dato);
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    String comprobarNumericoString(String dato) {
        BigDecimal valor = null;
        try {
            valor = new java.math.BigDecimal(dato);
            if (valor != null) {
                return "D" + dato + "¬";
            } else {
                return "S" + dato + "¬";
            }
        } catch (Exception e) {
            return "S" + dato + "¬";
        }
    }

    @Override
    public Map<String, Object> exportarReporteResumenSiembra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String nombreSector, String sector, List<PrdResumenCorridaTO> lista, boolean esAcuagold) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de Siembra");
            if (!sector.equals("")) {
                listaCabecera.add("TSector: " + sector);
            }
            listaCabecera.add("TDesde: " + fechaDesde);
            listaCabecera.add("THasta: " + fechaHasta);
            listaCabecera.add("T");

            String cuerpo = "";
            if (sector.equals("")) {
                cuerpo = "SSect." + "¬";
            }
            cuerpo += "SPisc." + "¬" + "SCor." + "¬" + "SCor. origen" + "¬" + "SHas." + "¬" + "SFecha Desde" + "¬" + "SFecha Siembra" + "¬" + "SEdad"
                    + "¬" + "SDías Secos" + "¬" + "SN.Larvas" + "¬" + "SDensidad" + "¬" + "SPeso Inicial" + "¬" + "SLab." + "¬" + "SNauplio" + "¬"
                    + "SBalanceado" + "¬" + "SBalanceado transferido" + "¬" + "SCosto Balanceado" + "¬" + "SCosto Balanceado transferido" + "¬" + "SBiomasa Proyectada" + "¬" + "SRaleo" + "¬" + "SRaleo USD" + "¬"
                    + "SConv. Al." + "¬" + "SGramos" + "¬" + "SCrecimiento Diario" + "¬" + "SIdeal" + "¬" + "SSobrev." + "¬" + "SAnimales por m2." + "¬" + "SCosto Total" + "¬" + "SCosto Directo" + "¬" + "SCosto Indirecto" + "¬"
                    + "SCosto Transferencia" + "¬" + "SCosto directo transferido" + "¬" + "SCosto indirecto transferido" + (esAcuagold ? "¬" + "SOtros costos" : "") + "¬" + "SCosto Hectarea" + "¬" + "STotal Diario" + "¬" + "SDirecto Diario" + "¬" + "SIndirecto Diario" + "¬"
                    + "STransferencia Diario" + "¬" + "SCosto directo transferido Hectarea dia" + "¬" + "SCosto indirecto transferido hectares dia" + (esAcuagold ? "¬" + "SOtros hectarea días" : "") + "¬" + "SCosto libra" + "¬" + "SCosto millar" + "¬" + "SVenta proyectada" + "¬" + "SResultado" + "¬" + "SResultado Hectárea" + "¬" + "SResultado Hectárea Día";
            listaCuerpo.add(cuerpo);

            lista.forEach((prdResumenCorridaTO) -> {
                listaCuerpo.add(
                        (sector.equals("") ? (prdResumenCorridaTO.getSecCodigo() == null ? "B" : "S" + prdResumenCorridaTO.getSecCodigo()) + "¬" : "")
                        + (prdResumenCorridaTO.getPisNumero() == null ? "B"
                        : "S" + prdResumenCorridaTO.getPisNumero())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCorridaNumero() == null ? "B"
                        : "S" + prdResumenCorridaTO.getRcCorridaNumero())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCorridaOrigen() == null ? "B"
                        : "S" + prdResumenCorridaTO.getRcCorridaOrigen())
                        + "¬"
                        + (prdResumenCorridaTO.getRcHectareaje() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcHectareaje().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcFechaDesde() == null ? "B"
                        : "T" + prdResumenCorridaTO.getRcFechaDesde())
                        + "¬"
                        + (prdResumenCorridaTO.getRcFechaSiembra() == null ? "B"
                        : "T" + prdResumenCorridaTO.getRcFechaSiembra())
                        + "¬"
                        + (prdResumenCorridaTO.getRcEdad() == null ? "B"
                        : "I" + prdResumenCorridaTO.getRcEdad().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcDiasSecos() == null ? "B"
                        : "I" + prdResumenCorridaTO.getRcDiasSecos().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcNumeroLarvas() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcNumeroLarvas().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcDensidad() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcDensidad().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcPesoInicial() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcPesoInicial().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcLaboratorio() == null ? "B"
                        : "S" + prdResumenCorridaTO.getRcLaboratorio())
                        + "¬"
                        + (prdResumenCorridaTO.getRcNauplio() == null ? "B"
                        : "S" + prdResumenCorridaTO.getRcNauplio())
                        + "¬"
                        + (prdResumenCorridaTO.getRcLibrasBalanceados() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcLibrasBalanceados().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcBalanceadoTransferido() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcBalanceadoTransferido().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoBalanceado() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoBalanceado().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoBalanceadoTransferido() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoBalanceadoTransferido().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcBiomasa() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcBiomasa().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcBiomasaReal() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcBiomasaReal().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcValorVenta() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcValorVenta().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcConversion() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcConversion().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcTPromedio() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcTPromedio().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCrecimientoDia() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCrecimientoDia().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcPesoIdeal() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcPesoIdeal().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcSobrevivencia() == null ? "B"
                        : "S" + prdResumenCorridaTO.getRcSobrevivencia())
                        + "¬"
                        + (prdResumenCorridaTO.getRcAnimales() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcAnimales())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoTotal() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoTotal().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoDirecto() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoDirecto().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoIndirecto() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoIndirecto().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoTransferencia() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoTransferencia().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoDirectoTransferido() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoDirectoTransferido().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoIndirectoTransferido() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoIndirectoTransferido().toString())
                        + "¬"
                        + (esAcuagold ? (prdResumenCorridaTO.getRcOtrosCostos() == null ? "B"
                                : "D" + prdResumenCorridaTO.getRcOtrosCostos().toString())
                                + "¬" : "")
                        + (prdResumenCorridaTO.getRcCostoHectarea() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoHectarea().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoTotalHectareaDia() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoTotalHectareaDia().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoDirectoHectareaDia() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoDirectoHectareaDia().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoindirectoHectareaDia() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoindirectoHectareaDia().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoTransferenciaHectareaDia() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoTransferenciaHectareaDia().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoDirectoTransferidoHectareaDia() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoDirectoTransferidoHectareaDia().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoIndirectoTransferidoHectareaDia() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoIndirectoTransferidoHectareaDia().toString())
                        + "¬"
                        + (esAcuagold ? (prdResumenCorridaTO.getRcOtrosHectareaDias() == null ? "B"
                                : "D" + prdResumenCorridaTO.getRcOtrosHectareaDias().toString())
                                + "¬" : "")
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoLibra() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoLibra().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoMillar() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoLibra().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcVentaProyectada() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcVentaProyectada().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcResultado() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcResultado().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcResultadoHectarea() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcResultadoHectarea().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcResultadoHectareaDia() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcResultadoHectareaDia().toString())
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteResumenPesca(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String nombreSector, String sector, List<PrdResumenCorridaTO> lista, boolean incluirTransferencia) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            BigDecimal cero = new BigDecimal("0.00");
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de Pesca");
            if (!nombreSector.equals("")) {
                listaCabecera.add("TSector: " + nombreSector);
            }
            listaCabecera.add("TDesde: " + fechaDesde);
            listaCabecera.add("THasta: " + fechaHasta);
            listaCabecera.add("T");

            String cuerpo = "";

            if (nombreSector.equals("")) {
                cuerpo = "SSect." + "¬";
            }
            cuerpo += "SPisc." + "¬" + "SHas." + "¬" + "SCor." + "¬" + "SCorrida origen" + "¬";

            if (incluirTransferencia) {
                cuerpo += "SCorrida destino" + "¬";
            }
            cuerpo += "SSiembra" + "¬"
                    + "SPesca" + "¬" + "SDías" + "¬" + "SDías muertos" + "¬" + "SN.Larvas" + "¬" + "SDensidad." + "¬" + "SPeso Inicial" + "¬" + "SLab." + "¬"
                    + "SNauplio." + "¬" + "SBalanceado" + "¬" + "SBalanceado Transferido" + "¬" + "SBiomasa Proy." + "¬" + "SBiomasa Real" + "¬"
                    + "SLibrasxHas." + "¬" + "SConv. Al." + "¬" + "SGra. Prom." + "¬" + "SGra. Ideal" + "¬" + "SSobrev." + "¬" + "SAnimales por m2."
                    + "¬" + "SCosto Balanceado" + "¬" + "SCosto Balanceado Transferido" + "¬" + "SCosto Total" + "¬" + "SDirecto" + "¬" + "SIndirecto" + "¬" + "SC. Transferencia"
                    + "¬" + "SCosto directo transferido" + "¬" + "SCosto indirecto transferido" + "¬" + "SCosto directo transferido Hectarea dia" + "¬" + "SCosto indirecto transferido hectares dia"
                    + "¬" + "SVenta" + "¬" + "SResul." + "¬" + "SRendimiento" + "¬"
                    + "SCostoxHas." + "¬" + "SVentaxHas." + "¬" + "SResultxHas." + "¬" + "SCostoxLb." + "¬"
                    + "SVentaxLb." + "¬" + "SResultxLb." + "¬" + "SResultDia" + "¬";
            listaCuerpo.add(cuerpo);
            for (int i = 0; i < lista.size(); i++) {
                PrdResumenCorridaTO prdResumenCorridaTO = lista.get(i);
                boolean esUltimo = (lista.size() - 1) == i;

                String Lb = prdResumenCorridaTO.getRcBiomasaReal() == null
                        || prdResumenCorridaTO.getRcHectareaje() == null
                        || prdResumenCorridaTO.getRcHectareaje().compareTo(cero) == 0 ? "B"
                        : "C" + String.valueOf(prdResumenCorridaTO.getRcBiomasaReal()
                                .divide(prdResumenCorridaTO.getRcHectareaje(), 2, java.math.RoundingMode.HALF_UP)
                                .intValue());

                if (esUltimo) {
                    List<BigDecimal> numbers = new ArrayList<>();

                    for (int j = 0; j < lista.size(); j++) {
                        if (j != lista.size() - 1) {
                            PrdResumenCorridaTO item = lista.get(j);
                            BigDecimal libxHec = (item.getRcBiomasaReal() == null
                                    || item.getRcHectareaje() == null
                                    || item.getRcHectareaje().compareTo(cero) == 0) ? cero
                                    : item.getRcBiomasaReal().divide(item.getRcHectareaje(), 2, java.math.RoundingMode.HALF_UP);
                            numbers.add(libxHec);
                        }
                    }
                    BigDecimal total = numbers.stream().reduce(cero, BigDecimal::add);
                    Lb = "C" + total;
                }
                listaCuerpo.add(
                        (nombreSector.equals("") ? (prdResumenCorridaTO.getSecCodigo() == null ? "B" : "S" + prdResumenCorridaTO.getSecCodigo())
                        + "¬" : "")
                        + (prdResumenCorridaTO.getPisNumero() == null ? "B"
                        : "S" + prdResumenCorridaTO.getPisNumero())
                        + "¬"
                        + (prdResumenCorridaTO.getRcHectareaje() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcHectareaje().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCorridaNumero() == null ? "B"
                        : "S" + prdResumenCorridaTO.getRcCorridaNumero())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCorridaOrigen() == null ? "B"
                        : "S" + prdResumenCorridaTO.getRcCorridaOrigen().toString())
                        + "¬"
                        + (incluirTransferencia ? (prdResumenCorridaTO.getRcCorridaDestino() == null ? "B"
                                : "S" + prdResumenCorridaTO.getRcCorridaDestino().toString())
                                + "¬" : "")
                        + (prdResumenCorridaTO.getRcFechaSiembra() == null ? "B"
                        : "T" + prdResumenCorridaTO.getRcFechaSiembra())
                        + "¬"
                        + (prdResumenCorridaTO.getRcFechaPesca() == null ? "B"
                        : "T" + prdResumenCorridaTO.getRcFechaPesca())
                        + "¬"
                        + (prdResumenCorridaTO.getRcEdad() == null ? "B"
                        : "C" + prdResumenCorridaTO.getRcEdad().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcDiasSecos() == null ? "B"
                        : "C" + prdResumenCorridaTO.getRcDiasSecos().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcNumeroLarvas() == null ? "B"
                        : "C" + prdResumenCorridaTO.getRcNumeroLarvas().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcDensidad() == null ? "B"
                        : "C" + prdResumenCorridaTO.getRcDensidad().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcPesoInicial() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcPesoInicial().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcLaboratorio() == null ? "B"
                        : "S" + prdResumenCorridaTO.getRcLaboratorio())
                        + "¬"
                        + (prdResumenCorridaTO.getRcNauplio() == null ? "B"
                        : "S" + prdResumenCorridaTO.getRcNauplio())
                        + "¬"
                        + (prdResumenCorridaTO.getRcLibrasBalanceados() == null ? "B"
                        : "C" + prdResumenCorridaTO.getRcLibrasBalanceados().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcBalanceadoTransferido() == null ? "B"
                        : "C" + prdResumenCorridaTO.getRcBalanceadoTransferido().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcBiomasa() == null ? "B"
                        : "C" + prdResumenCorridaTO.getRcBiomasa().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcBiomasaReal() == null ? "B"
                        : "C" + prdResumenCorridaTO.getRcBiomasaReal().toString())
                        + "¬"
                        + Lb
                        + "¬"
                        + (prdResumenCorridaTO.getRcConversion() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcConversion().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcTPromedio() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcTPromedio().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcPesoIdeal() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcPesoIdeal().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcSobrevivencia() == null ? "B"
                        : "S" + prdResumenCorridaTO.getRcSobrevivencia())
                        + "¬"
                        + (prdResumenCorridaTO.getRcAnimales() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcAnimales())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoBalanceado() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoBalanceado().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoBalanceadoTransferido() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoBalanceadoTransferido().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoTotal() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoTotal().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoDirecto() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoDirecto().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoIndirecto() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoIndirecto().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoTransferencia() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoTransferencia().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoDirectoTransferido() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoDirectoTransferido().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoIndirectoTransferido() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoIndirectoTransferido().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoDirectoTransferidoHectareaDia() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoDirectoTransferidoHectareaDia().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoIndirectoTransferidoHectareaDia() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoIndirectoTransferidoHectareaDia().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcValorVenta() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcValorVenta().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcResultado() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcResultado().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcRendimiento() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcRendimiento().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoHectarea() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoHectarea().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcVentaHectarea() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcVentaHectarea().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcResultadoHectarea() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcResultadoHectarea().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcCostoLibra() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcCostoLibra().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcVentaLibra() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcVentaLibra().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcResultadoLibra() == null ? "B"
                        : "D" + prdResumenCorridaTO.getRcResultadoLibra().toString())
                        + "¬"
                        + (prdResumenCorridaTO.getRcResultadoLibra() == null || prdResumenCorridaTO.getRcEdad() == null || prdResumenCorridaTO.getRcEdad().compareTo(cero) == 0
                        ? "B" : "D" + UtilsValidacion.redondeoDecimalBigDecimal(prdResumenCorridaTO.getRcResultadoHectarea().divide(prdResumenCorridaTO.getRcEdad(), 2, java.math.RoundingMode.HALF_UP)).toString())
                );
            }

            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    public static List<PrdLiquidacionDetalle> ordenacionBurbujaImprimir(List<PrdLiquidacionDetalle> lista) {
        final int n = lista.size() - 1;
        for (int i = n; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (lista.get(j).getDetOrden() > lista.get(j + 1).getDetOrden()) {
                    PrdLiquidacionDetalle temp = lista.get(j);
                    lista.set(j, lista.get(j + 1));
                    lista.set(j + 1, temp);
                }
            }
        }
        return lista;
    }

    public static List<PrdPreLiquidacionDetalle> ordenacionBurbujaImprimirPreLiquidacion(List<PrdPreLiquidacionDetalle> lista) {
        final int n = lista.size() - 1;
        for (int i = n; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (lista.get(j).getDetOrden() > lista.get(j + 1).getDetOrden()) {
                    PrdPreLiquidacionDetalle temp = lista.get(j);
                    lista.set(j, lista.get(j + 1));
                    lista.set(j + 1, temp);
                }
            }
        }
        return lista;
    }

    @Override
    public Map<String, Object> exportarReporteResumenPescaEconomico(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String nombreSector, String sector, List<PrdResumenPescaSiembraTO> lista, boolean incluirTransferencia) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte resumen económico de Pesca");
            if (!nombreSector.equals("")) {
                listaCabecera.add("TSector: " + nombreSector);
            }

            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("T");
            String cuerpo = "";
            if (nombreSector.equals("")) {
                cuerpo = "SSect." + "¬";
            }

            cuerpo += "SPisc." + "¬" + "SCor." + "¬" + "SCor. origen" + "¬";
            if (incluirTransferencia) {
                cuerpo += "SCor. destino" + "¬";
            }
            cuerpo += "SHas." + "¬" + "SDesde" + "¬" + "SHasta"
                    + "¬" + "SDías Cultivo" + "¬" + "SDías Inactivo" + "¬" + "SDensidad" + "¬" + "SLaboratorio" + "¬"
                    + "SNauplio" + "¬" + "SBiomasa Real" + "¬" + "SConv. Al." + "¬" + "SGramos Promedio" + "¬" + "SSobrev." + "¬"
                    + "SDirecto" + "¬" + "SIndirecto" + "¬" + "STransf." + "¬" + "SSubtotal" + "¬" + "SAdm." + "¬" + "SFin." + "¬" + "SGND."
                    + "¬" + "SSubtotal." + "¬" + "STOTAL." + "¬" + "SCosto Balanceado" + "¬" + "SCosto Balanceado Transferido" + "¬" + "SVENTA." + "¬"
                    + "SOtros Gastos" + "¬" + "SDirecto transferido" + "¬" + "SIndirecto transferido" + "¬" + "SGasto transferido" + "¬" + "SOtros Gastos transferidos" + "¬";
            listaCuerpo.add(cuerpo);
            lista.forEach((prdResumenPescaSiembraTO) -> {
                listaCuerpo.add(
                        (nombreSector.equals("") ? (prdResumenPescaSiembraTO.getRcSector() == null ? "B" : "S" + prdResumenPescaSiembraTO.getRcSector()) + "¬" : "")
                        + (prdResumenPescaSiembraTO.getRcPiscina() == null ? "B" : "S" + prdResumenPescaSiembraTO.getRcPiscina()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcCorrida() == null ? "B" : "S" + prdResumenPescaSiembraTO.getRcCorrida()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcCorridaOrigen() == null ? "B" : "S" + prdResumenPescaSiembraTO.getRcCorridaOrigen()) + "¬"
                        + (incluirTransferencia ? (prdResumenPescaSiembraTO.getRcCorridaDestino() == null ? "B" : "S" + prdResumenPescaSiembraTO.getRcCorridaDestino()) + "¬" : "")
                        + (prdResumenPescaSiembraTO.getRcHectareaje() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcHectareaje().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcFechaDesde() == null ? "B" : "T" + prdResumenPescaSiembraTO.getRcFechaDesde()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcFechaHasta() == null ? "B" : "T" + prdResumenPescaSiembraTO.getRcFechaHasta()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcEdad() == null ? "B" : "I" + prdResumenPescaSiembraTO.getRcEdad().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcDiasMuertos() == null ? "B" : "I" + prdResumenPescaSiembraTO.getRcDiasMuertos().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcDensidad() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcDensidad().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcLaboratorio() == null ? "B" : "S" + prdResumenPescaSiembraTO.getRcLaboratorio()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcNauplio() == null ? "B" : "S" + prdResumenPescaSiembraTO.getRcNauplio()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcBiomasaReal() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcBiomasaReal().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcConversion() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcConversion().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcTallaPromedio() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcTallaPromedio().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcSobrevivencia() == null ? "B" : "S" + prdResumenPescaSiembraTO.getRcSobrevivencia()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcDirecto() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcDirecto().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcIndirecto() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcIndirecto().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcTransferencia() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcTransferencia().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcSubtotal() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcSubtotal().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcAdministrativo() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcAdministrativo().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcFinanciero() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcFinanciero().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcGND() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcGND().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcSubtotal2() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcSubtotal2().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcTotal() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcTotal().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcCostoBalanceado() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcCostoBalanceado().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcCostoBalanceadoTransferido() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcCostoBalanceadoTransferido().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcVenta() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcVenta().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcOtrosGastos() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcOtrosGastos().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcDirectoTransferido() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcDirectoTransferido().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcIndirectoTransferido() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcIndirectoTransferido().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcGastoTransferido() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcGastoTransferido().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcOtroGastoTransferido() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcOtroGastoTransferido().toString()) + "¬"
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReportePrdLiquidacionProductoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdProducto> lista) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte liquidacion de producto");
            listaCabecera.add("S");
            listaCuerpo.add("SCodigo" + "¬" + "SDetalle" + "¬" + "STipo" + "¬" + "SClase" + "¬" + "SInactivo");

            lista.forEach((prdProductos) -> {
                listaCuerpo.add((prdProductos.getPrdLiquidacionProductoPK().getProdCodigo() == null ? "B"
                        : "S" + prdProductos.getPrdLiquidacionProductoPK().getProdCodigo())
                        + "¬"
                        + (prdProductos.getProdDetalle() == null ? "B"
                        : "S" + prdProductos.getProdDetalle())
                        + "¬"
                        + (prdProductos.getProdTipo() == null ? "B"
                        : "S" + prdProductos.getProdTipo())
                        + "¬" + (String.valueOf(prdProductos.getProdClase()) == null ? "B"
                        : "S" + prdProductos.getProdClase())
                        + "¬" + (String.valueOf(prdProductos.getProdInactivo()) == null ? "SINACTIVO"
                        : "SACTIVO"));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteLiquidacionMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdLiquidacionMotivo> prdLiquidacionMotivo) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportLiquidacionMotivo.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), prdLiquidacionMotivo);
    }

    @Override
    public Map<String, Object> exportarReporteLiquidacionMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<PrdLiquidacionMotivo> prdLiquidacionMotivo) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SLiquidación Motivo");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo¬SDetalle");
            prdLiquidacionMotivo.forEach((liquidacionTO) -> {
                listaCuerpo.add(
                        (liquidacionTO.getPrdLiquidacionMotivoPK().getLmCodigo() == null ? "B" : "S" + liquidacionTO.getPrdLiquidacionMotivoPK().getLmCodigo()) + "¬"
                        + (liquidacionTO.getLmDetalle() == null ? "B" : "S" + liquidacionTO.getLmDetalle()) + "¬"
                        + (liquidacionTO.getLmInactivo() == true ? "SINACTIVO" : "S") + "¬"
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReportePresupuestoPesca(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdPresupuestoPescaMotivo> prdPresupuestoPescaMotivo) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportPresupuestoPescaMotivo.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), prdPresupuestoPescaMotivo);
    }

    @Override
    public Map<String, Object> exportarReportePresupuestoPesca(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdPresupuestoPescaMotivo> prdPresupuestoPescaMotivo) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SMotivo de presupuesto de pesca");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo¬SDetalle");
            prdPresupuestoPescaMotivo.forEach((presupuestoPMTO) -> {
                listaCuerpo.add(
                        (presupuestoPMTO.getPrdPresupuestoPescaMotivoPK().getPresuCodigo() == null ? "B" : "S" + presupuestoPMTO.getPrdPresupuestoPescaMotivoPK().getPresuCodigo()) + "¬"
                        + (presupuestoPMTO.getPresuDetalle() == null ? "B" : "S" + presupuestoPMTO.getPresuDetalle()) + "¬"
                        + (presupuestoPMTO.getPresuInactivo() == true ? "SINACTIVO" : "S") + "¬"
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReportePreLiquidacionMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdPreLiquidacionMotivo> prdPreLiquidacionMotivo) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportPreLiquidacionMotivo.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), prdPreLiquidacionMotivo);
    }

    @Override
    public Map<String, Object> exportarReportePreLiquidacionMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdPreLiquidacionMotivo> prdPreLiquidacionMotivo) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SMotivo de pre liquidación");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo¬SDetalle");
            prdPreLiquidacionMotivo.forEach((presupuestoPMTO) -> {
                listaCuerpo.add(
                        (presupuestoPMTO.getPrdPreLiquidacionMotivoPK().getPlmCodigo() == null ? "B" : "S" + presupuestoPMTO.getPrdPreLiquidacionMotivoPK().getPlmCodigo()) + "¬"
                        + (presupuestoPMTO.getPlmDetalle() == null ? "B" : "S" + presupuestoPMTO.getPlmDetalle()) + "¬"
                        + (presupuestoPMTO.getPlmInactivo() == true ? "SINACTIVO" : "S") + "¬"
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteResumenEconomicoSiembra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String nombreSector, String sector, List<PrdResumenPescaSiembraTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Económico de Siembra");
            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            if (!nombreSector.equals("")) {
                listaCabecera.add("SNombre de sector: " + nombreSector);
            }
            listaCabecera.add("S");

            String cabecera = "";

            if (nombreSector.equals("")) {
                cabecera = "SSect." + "¬";
            }

            cabecera += "SPisc." + "¬" + "SCor." + "¬" + "SCor. origen" + "¬" + "SHas." + "¬" + "SDesde" + "¬"
                    + "SDías Cultivo" + "¬" + "SDías Inactivo" + "¬" + "SDensidad" + "¬" + "SLaboratorio" + "¬" + "SNauplio"
                    + "¬" + "SBiomasa Proy." + "¬" + "SConv. Al." + "¬" + "SGramos" + "¬" + "SSobrev."
                    + "¬" + "SDirecto" + "¬" + "SIndirecto" + "¬" + "STransf." + "¬" + "SSubtotal"
                    + "¬" + "SAdm." + "¬" + "SFin." + "¬" + "SGND." + "¬" + "SSubtotal" + "¬" + "STOTAL" + "¬" + "SCosto Balanceado" + "¬" + "SCosto Balanceado Transferido" + "¬"
                    + "SOtros Gastos" + "¬" + "SDirecto transferido" + "¬" + "SIndirecto transferido" + "¬" + "SGasto transferido" + "¬" + "SOtros Gastos transferidos" + "¬";

            listaCuerpo.add(cabecera);

            listado.forEach((prdResumenPescaSiembraTO) -> {
                listaCuerpo.add(
                        (nombreSector.equals("")
                        ? (prdResumenPescaSiembraTO.getRcSector() == null ? "B" : "S" + prdResumenPescaSiembraTO.getRcSector()) + "¬" : "")
                        + (prdResumenPescaSiembraTO.getRcPiscina() == null ? "B" : "S" + prdResumenPescaSiembraTO.getRcPiscina()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcCorrida() == null ? "B" : "S" + prdResumenPescaSiembraTO.getRcCorrida()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcCorridaOrigen() == null ? "B" : "S" + prdResumenPescaSiembraTO.getRcCorridaOrigen()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcHectareaje() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcHectareaje()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcFechaDesde() == null ? "B" : "T" + prdResumenPescaSiembraTO.getRcFechaDesde()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcEdad() == null ? "B" : "I" + prdResumenPescaSiembraTO.getRcEdad().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcDiasMuertos() == null ? "B" : "I" + prdResumenPescaSiembraTO.getRcDiasMuertos().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcDensidad() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcDensidad().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcLaboratorio() == null ? "B" : "S" + prdResumenPescaSiembraTO.getRcLaboratorio()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcNauplio() == null ? "B" : "S" + prdResumenPescaSiembraTO.getRcNauplio()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcBiomasa() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcBiomasa().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcConversion() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcConversion().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcTallaPromedio() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcTallaPromedio().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcSobrevivencia() == null ? "B" : "S" + prdResumenPescaSiembraTO.getRcSobrevivencia()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcDirecto() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcDirecto().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcIndirecto() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcIndirecto().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcTransferencia() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcTransferencia().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcSubtotal() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcSubtotal().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcAdministrativo() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcAdministrativo().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcFinanciero() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcFinanciero().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcGND() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcGND().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcSubtotal2() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcSubtotal2().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcTotal() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcTotal().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcCostoBalanceado() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcCostoBalanceado().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcCostoBalanceadoTransferido() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcCostoBalanceadoTransferido().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcOtrosGastos() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcOtrosGastos().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcDirectoTransferido() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcDirectoTransferido().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcIndirectoTransferido() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcIndirectoTransferido().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcGastoTransferido() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcGastoTransferido().toString()) + "¬"
                        + (prdResumenPescaSiembraTO.getRcOtroGastoTransferido() == null ? "B" : "D" + prdResumenPescaSiembraTO.getRcOtroGastoTransferido().toString()) + "¬"
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReportePescaLiquidacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ListaLiquidacionTO> listado, String sector, String piscina) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String guiaDocumento = usuarioEmpresaReporteTO.getEmpCodigo().equals("SA17") ? "SGuia" : "SDocumento";
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de liquidación de pesca");
            listaCabecera.add("SSector:" + sector);
            if (piscina != null) {
                listaCabecera.add("SPiscina:" + piscina);
            }
            listaCabecera.add("S");
            listaCuerpo.add("SEstado" + "¬" + "SMotivo" + "¬" + "SNúmero" + "¬" + "SLote" + "¬" + "SCorrida" + "¬" + "SFecha" + "¬" + "Ssector" + "¬" + "SPiscina" + "¬" + "SCliente" + "¬" + guiaDocumento + "¬" + "SLibras basura" + "¬" + "SLibras enviadas" + "¬" + "SLibras recibidas" + "¬" + "Stotal");
            listado.forEach((liq) -> {
                String estado = "";
                if (liq.getLiqAnulado() == "ANULADO") {
                    estado = liq.getLiqAnulado();
                } else if (liq.getLiqPendiente() == "PENDIENTE") {
                    estado = liq.getLiqPendiente();
                } else if (liq.getLiqBloqueado() == "BLOQUEADO") {
                    estado = liq.getLiqBloqueado();
                }
                listaCuerpo.add(
                        (estado == null ? "B" : "S" + estado)
                        + "¬" + (liq.getLiqMotivo() == null ? "B" : "S" + liq.getLiqMotivo())
                        + "¬" + (liq.getLiqNumero() == null ? "B" : "S" + liq.getLiqNumero())
                        + "¬" + (liq.getLiqLote() == null ? "B" : "S" + liq.getLiqLote())
                        + "¬" + (liq.getLiqCorrida() == null ? "B" : "S" + liq.getLiqCorrida())
                        + "¬" + (liq.getLiqFecha() == null ? "B" : "S" + liq.getLiqFecha())
                        + "¬" + (liq.getPisSector() == null ? "B" : "S" + liq.getPisSector())
                        + "¬" + (liq.getPisNumero() == null ? "B" : "S" + liq.getPisNumero())
                        + "¬" + (liq.getCliNombre() == null ? "B" : "S" + liq.getCliNombre())
                        + "¬" + (usuarioEmpresaReporteTO.getEmpCodigo().equals("SA17") ? liq.getLiqGuia() == null ? "B" : "S" + liq.getLiqGuia() :liq.getLiqDocumentoNumero() == null ? "B" : "S" + liq.getLiqDocumentoNumero())
                        + "¬" + (liq.getLiqLibrasBasura() == null ? "B" : "D" + liq.getLiqLibrasBasura())
                        + "¬" + (liq.getLiqLibrasEnviadas() == null ? "B" : "D" + liq.getLiqLibrasEnviadas())
                        + "¬" + (liq.getLiqLibrasRecibidas() == null ? "B" : "D" + liq.getLiqLibrasRecibidas())
                        + "¬" + (liq.getLiqTotalMonto() == null ? "B" : "D" + liq.getLiqTotalMonto())
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReportePrdLiquidacionTallaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdTalla> lista) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte liquidacion de talla");
            listaCabecera.add("S");
            listaCuerpo.add("SCodigo" + "¬" + "SDetalle" + "¬" + "SPrecio promedio" + "¬" + "SGramos desde" + "¬" + "SGramos hasta" + "¬"
                    + "¬" + "SUnidad de medida" + "¬" + "SInactivo");

            lista.forEach((prdTalla) -> {
                listaCuerpo.add((prdTalla.getPrdLiquidacionTallaPK().getTallaCodigo() == null ? "B"
                        : "S" + prdTalla.getPrdLiquidacionTallaPK().getTallaCodigo())
                        + "¬"
                        + (prdTalla.getTallaDetalle() == null ? "B"
                        : "S" + prdTalla.getTallaDetalle())
                        + "¬"
                        + (prdTalla.getTallaPrecio() == null ? "B"
                        : "S" + prdTalla.getTallaPrecio())
                        + "¬" + (prdTalla.getTallaGramosDesde() == null ? "B"
                        : "S" + prdTalla.getTallaGramosDesde())
                        + "¬" + (prdTalla.getTallaGramosHasta() == null ? "B"
                        : "S" + prdTalla.getTallaGramosHasta())
                        + "¬" + (prdTalla.getTallaUnidadMedida() == null ? "B"
                        : "S" + prdTalla.getTallaUnidadMedida())
                        + "¬" + (prdTalla.getTallaInactivo() ? "SINACTIVO"
                        : "SACTIVO"));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteSobrevivencia(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdListaSobrevivenciaTO> lista) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte sobrevivencia");
            listaCabecera.add("S");
            listaCuerpo.add("SDias desde" + "¬" + "SDias hasta" + "¬" + "SSobrevivencia" + "¬" + "SAlimentacion");

            lista.forEach((prdSobrevivencia) -> {
                listaCuerpo.add((prdSobrevivencia.getSobDiasDesde() == null ? "B"
                        : "S" + prdSobrevivencia.getSobDiasDesde())
                        + "¬"
                        + (prdSobrevivencia.getSobDiasHasta() == null ? "B"
                        : "S" + prdSobrevivencia.getSobDiasHasta())
                        + "¬"
                        + (prdSobrevivencia.getSobSobrevivencia() == null ? "B"
                        : "S" + prdSobrevivencia.getSobSobrevivencia())
                        + "¬" + (prdSobrevivencia.getSobAlimentacion() == null ? "B"
                        : "S" + prdSobrevivencia.getSobAlimentacion()));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarPrdCorrida(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdCorrida> listado, String sector, String piscina, boolean ordenProduccion) throws Exception {
        try {
            boolean hayPiscina = piscina != null && !piscina.equals("");
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add(ordenProduccion ? "SReporte de orden producción" : "SReporte de corridas");
            if (sector != null && !sector.equals("")) {
                listaCabecera.add((ordenProduccion ? "SCentro de producción: " : "SSector: ") + sector);
            }
            if (hayPiscina) {
                listaCabecera.add((ordenProduccion ? "SCentro de costo: " : "SPiscina: ") + piscina);
                piscina = "";
            } else {
                piscina = (ordenProduccion ? "SCentro de costo" : "SPiscina") + "¬";
            }
            listaCabecera.add("S");
            listaCuerpo.add(piscina + "SNúmero" + "¬" + "SHectáreas" + "¬" + "SFecha desde" + "¬" + "SFecha siembra" + "¬" + "SFecha siembra madre" + "¬"
                    + "SLarvas" + "¬" + "SLaboratorio" + "¬" + "SNauplio" + "¬" + "SFecha pesca" + "¬" + "SFecha hasta");
            listado.forEach((prdCorrida) -> {
                listaCuerpo.add(
                        (!hayPiscina ? (prdCorrida.getPrdCorridaPK().getCorPiscina() == null ? "B" : "S" + prdCorrida.getPrdCorridaPK().getCorPiscina()) + "¬" : "")
                        + (prdCorrida.getPrdCorridaPK().getCorNumero() == null ? "B" : "S" + prdCorrida.getPrdCorridaPK().getCorNumero())
                        + "¬" + (prdCorrida.getCorHectareas() == null ? "B" : "D" + prdCorrida.getCorHectareas())
                        + "¬" + (prdCorrida.getCorFechaDesde() == null ? "B" : "S" + UtilsDate.DeDateAString(prdCorrida.getCorFechaDesde()))
                        + "¬" + (prdCorrida.getCorFechaSiembra() == null ? "B" : "S" + UtilsDate.DeDateAString(prdCorrida.getCorFechaSiembra()))
                        + "¬" + (prdCorrida.getCorFechaSiembraMadre() == null ? "B" : "S" + UtilsDate.DeDateAString(prdCorrida.getCorFechaSiembraMadre()))
                        + "¬" + (prdCorrida.getCorNumeroLarvas() == null ? "B" : "I" + prdCorrida.getCorNumeroLarvas())
                        + "¬" + (prdCorrida.getCorLaboratorio() == null ? "B" : "S" + prdCorrida.getCorLaboratorio())
                        + "¬" + (prdCorrida.getCorNauplio() == null ? "B" : "S" + prdCorrida.getCorNauplio())
                        + "¬" + (prdCorrida.getCorFechaPesca() == null ? "B" : "S" + UtilsDate.DeDateAString(prdCorrida.getCorFechaPesca()))
                        + "¬" + (prdCorrida.getCorFechaHasta() == null ? "B" : "S" + UtilsDate.DeDateAString(prdCorrida.getCorFechaHasta())));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReportePrdCorrida(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<PrdCorrida> listado, String sector, String piscina) throws Exception {
        List<ReportePrdCorridaTO> listadoReporteCorrida = new ArrayList<>();
        listado.stream().map((corrida) -> {
            ReportePrdCorridaTO reporteCorrida = new ReportePrdCorridaTO();
            reporteCorrida.setCorEmpresa(corrida.getPrdCorridaPK().getCorEmpresa());
            reporteCorrida.setCorNumero(corrida.getPrdCorridaPK().getCorNumero());
            reporteCorrida.setCorPiscina(piscina != null ? piscina : "");
            reporteCorrida.setCorSector(sector != null ? sector : "");
            reporteCorrida.setCorFechaDesde(corrida.getCorFechaDesde() != null ? UtilsDate.DeDateAString(corrida.getCorFechaDesde()) : "");
            reporteCorrida.setCorFechaHasta(corrida.getCorFechaHasta() != null ? UtilsDate.DeDateAString(corrida.getCorFechaHasta()) : "");
            reporteCorrida.setCorFechaPesca(corrida.getCorFechaPesca() != null ? UtilsDate.DeDateAString(corrida.getCorFechaPesca()) : "");
            reporteCorrida.setCorFechaSiembra(corrida.getCorFechaSiembra() != null ? UtilsDate.DeDateAString(corrida.getCorFechaSiembra()) : "");
            return reporteCorrida;
        }).forEachOrdered((reporteCorrida) -> {
            listadoReporteCorrida.add(reporteCorrida);
        });
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<>(), listadoReporteCorrida);
    }

    @Override
    public byte[] generarReportePescaLiquidacionPorLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String empresa, String nombreReporte, List<ListaLiquidacionTO> listado) throws Exception {
        List<ReporteLiquidacionPesca> reporteLiquidacionListado = new ArrayList<>();
        Map<String, Object> parametros = new HashMap<>();
        BigDecimal totalLibras = BigDecimal.ZERO;
        for (ListaLiquidacionTO itemLiquidacion : listado) {
            List<PrdLiquidacionDetalle> detalle = new ArrayList<>();
            PrdLiquidacion prdLiquidacion = liquidacionService.getPrdLiquidacion(new PrdLiquidacionPK(empresa, itemLiquidacion.getLiqMotivo(), itemLiquidacion.getLiqNumero()));
            if (prdLiquidacion != null) {
                parametros.put("tipo", prdLiquidacion.getLiqTipoReliquidacion() != null ? prdLiquidacion.getLiqTipoReliquidacion() : "");
                detalle = liquidacionDetalleService.getListaPrdLiquidacionDetalle(prdLiquidacion.getPrdLiquidacionPK().getLiqEmpresa(), prdLiquidacion.getPrdLiquidacionPK().getLiqMotivo(), prdLiquidacion.getPrdLiquidacionPK().getLiqNumero());
            }
            if (prdLiquidacion.getPrdCorrida() == null) {
                PrdCorrida c = liquidacionService.obtenerCorrida(new PrdLiquidacionPK(empresa, itemLiquidacion.getLiqMotivo(), itemLiquidacion.getLiqNumero()));
                if (c != null) {
                    prdLiquidacion.setPrdCorrida(c);
                }
            }
            if (detalle != null && !detalle.isEmpty()) {
                Collections.sort(detalle, (x, y) -> x.getPrdProducto().getProdDetalle().compareToIgnoreCase(y.getPrdProducto().getProdDetalle()));
                prdLiquidacion.setPrdLiquidacionDetalleList(detalle);
                //List<PrdLiquidacionDetalle> listaPrdliqDetalle = ordenacionBurbujaImprimir(prdLiquidacion.getPrdLiquidacionDetalleList()); Ya no es necesario utilizar el metodo de burbuja para ordenar
                List<PrdLiquidacionDetalle> listaPrdliqDetalle = prdLiquidacion.getPrdLiquidacionDetalleList();
                listaPrdliqDetalle.stream().map((PrdLiquidacionDetalle icd) -> {
                    ReporteLiquidacionPesca reporteLiquidacion = new ReporteLiquidacionPesca();
                    reporteLiquidacion.setCliCodigo(itemLiquidacion.getCliCodigo());
                    reporteLiquidacion.setCliNombre(itemLiquidacion.getCliNombre());
                    reporteLiquidacion.setSectorDetalle(prdLiquidacion.getPrdPiscina() != null && prdLiquidacion.getPrdPiscina().getPrdSector() != null ? prdLiquidacion.getPrdPiscina().getPrdSector().getSecNombre() : "");
                    reporteLiquidacion.setLiqEmpresa(prdLiquidacion.getPrdLiquidacionPK().getLiqEmpresa());
                    reporteLiquidacion.setLiqFecha(UtilsDate.fechaFormatoString(prdLiquidacion.getLiqFecha(), "yyyy-MM-dd"));
                    reporteLiquidacion.setLiqMotivo(prdLiquidacion.getPrdLiquidacionPK().getLiqMotivo());
                    reporteLiquidacion.setLiqTotalMonto(itemLiquidacion.getLiqTotalMonto());
                    reporteLiquidacion.setLiqUsuario(prdLiquidacion.getUsrCodigo());
                    reporteLiquidacion.setPisSector(prdLiquidacion.getPrdPiscina().getPrdSector().getSecNombre());
                    reporteLiquidacion.setPisNumero(prdLiquidacion.getPrdPiscina().getPisNombre());
                    reporteLiquidacion.setLiqNumero(prdLiquidacion.getPrdLiquidacionPK().getLiqNumero());
                    reporteLiquidacion.setLiqLote(prdLiquidacion.getLiqLote());
                    reporteLiquidacion.setLiqLibrasEnviadas(prdLiquidacion.getLiqLibrasEnviadas());
                    reporteLiquidacion.setLiqLibrasRecibidas(prdLiquidacion.getLiqLibrasRecibidas());
                    reporteLiquidacion.setLiqLibrasBasura(prdLiquidacion.getLiqLibrasBasura());
                    reporteLiquidacion.setLiqLibrasNetas(prdLiquidacion.getLiqLibrasNetas());
                    reporteLiquidacion.setLiqLibrasEntero(prdLiquidacion.getLiqLibrasEntero());
                    reporteLiquidacion.setLiqLibrasCola(prdLiquidacion.getLiqLibrasCola());
                    reporteLiquidacion.setLiqAnimalesCosechados(prdLiquidacion.getLiqAnimalesCosechados());
                    reporteLiquidacion.setLiqLibrasRetiradas(prdLiquidacion.getLiqLibrasRetiradas());
                    reporteLiquidacion.setLiqLibrasColaProcesadas(prdLiquidacion.getLiqLibrasColaProcesadas());
                    reporteLiquidacion.setProvCodigo(itemLiquidacion.getProvCodigo());
                    reporteLiquidacion.setProvNombre(itemLiquidacion.getProvNombre());
                    reporteLiquidacion.setLiqAnulado(prdLiquidacion.getLiqAnulado());
                    reporteLiquidacion.setLiqGramaje(prdLiquidacion.getLiqGramaje() == null ? BigDecimal.ZERO : prdLiquidacion.getLiqGramaje());
                    reporteLiquidacion.setLiqGavetas(prdLiquidacion.getLiqGavetas() == null ? BigDecimal.ZERO : prdLiquidacion.getLiqGavetas());
                    reporteLiquidacion.setLiqPesoPromedio(prdLiquidacion.getLiqPesoPromedio() == null ? BigDecimal.ZERO : prdLiquidacion.getLiqPesoPromedio());
                    reporteLiquidacion.setLiqLarvilla(prdLiquidacion.getLiqLarvilla() == null ? BigDecimal.ZERO : prdLiquidacion.getLiqLarvilla());
                    reporteLiquidacion.setLiqQuebrado(prdLiquidacion.getLiqQuebrado() == null ? BigDecimal.ZERO : prdLiquidacion.getLiqQuebrado());
                    reporteLiquidacion.setLiqObservaciones(prdLiquidacion.getLiqObservaciones());
                    reporteLiquidacion.setDetGuia(prdLiquidacion.getLiqGuia());
                    reporteLiquidacion.setLiqBines(prdLiquidacion.getLiqBines());
                    reporteLiquidacion.setLiqAguaje(prdLiquidacion.getLiqAguaje());
                    reporteLiquidacion.setLiqPiscina(prdLiquidacion.getLiqPiscina());
                    reporteLiquidacion.setLiqComisionista(prdLiquidacion.getLiqComisionista());
                    reporteLiquidacion.setLiqTipoReliquidacion(prdLiquidacion.getLiqTipoReliquidacion());

                    if (prdLiquidacion.getPrdCorrida() != null) {
                        reporteLiquidacion.setCorFechaDesde(UtilsDate.fechaFormatoString(prdLiquidacion.getPrdCorrida().getCorFechaDesde(), "yyyy-MM-dd"));
                        reporteLiquidacion.setCorFechaHasta(UtilsDate.fechaFormatoString(prdLiquidacion.getPrdCorrida().getCorFechaHasta(), "yyyy-MM-dd"));
                        reporteLiquidacion.setCorNumero(prdLiquidacion.getPrdCorrida().getPrdCorridaPK().getCorNumero());
                        reporteLiquidacion.setCorPiscina(prdLiquidacion.getPrdCorrida().getPrdCorridaPK().getCorPiscina());
                        reporteLiquidacion.setCorSector(prdLiquidacion.getPrdCorrida().getPrdCorridaPK().getCorSector());
                    }

                    MathContext precision = new MathContext(4);
                    BigDecimal div = prdLiquidacion.getLiqLibrasEntero().divide(prdLiquidacion.getLiqLibrasNetas(), precision);
                    reporteLiquidacion.setRendimiento(div.multiply(new BigDecimal("100.00")));//rendimiento total

                    BigDecimal cal = prdLiquidacion.getLiqLibrasColaProcesadas().divide(prdLiquidacion.getLiqLibrasCola(), precision);
                    reporteLiquidacion.setLiqRendimientoCola(cal.multiply(new BigDecimal("100.00")));
                    //DETALLE
                    reporteLiquidacion.setProdCodigo(icd.getPrdProducto().getProdDetalle());
                    reporteLiquidacion.setLiqClase(icd.getPrdProducto().getProdTipo());
                    reporteLiquidacion.setTallaCodigo(icd.getPrdTalla().getTallaDetalle());
                    reporteLiquidacion.setTallaDescripcion(icd.getPrdTalla().getTallaDescripcion());
                    reporteLiquidacion.setUnidadMedida(icd.getPrdTalla().getTallaUnidadMedida());
                    reporteLiquidacion.setDetLibras(icd.getDetLibras());
                    reporteLiquidacion.setDetPrecio(icd.getDetPrecio());
                    reporteLiquidacion.setDetTotal(icd.getDetLibras().multiply(icd.getDetPrecio()));
                    return reporteLiquidacion;
                }).forEachOrdered((reporteLiquidacion) -> {
                    reporteLiquidacionListado.add(reporteLiquidacion);
                });
            } else {
                ReporteLiquidacionPesca reporteLiquidacion = new ReporteLiquidacionPesca();
                reporteLiquidacion.setCliCodigo(prdLiquidacion.getInvCliente() != null ? prdLiquidacion.getInvCliente().getInvClientePK().getCliCodigo() : "");
                reporteLiquidacion.setCliNombre(prdLiquidacion.getInvCliente() != null ? prdLiquidacion.getInvCliente().getCliRazonSocial() : "");
                reporteLiquidacion.setSectorDetalle(prdLiquidacion.getPrdPiscina() != null && prdLiquidacion.getPrdPiscina().getPrdSector() != null ? prdLiquidacion.getPrdPiscina().getPrdSector().getSecNombre() : "");
                reporteLiquidacion.setLiqEmpresa(prdLiquidacion.getPrdLiquidacionPK().getLiqEmpresa());
                reporteLiquidacion.setLiqFecha(UtilsDate.fechaFormatoString(prdLiquidacion.getLiqFecha(), "yyyy-MM-dd"));
                reporteLiquidacion.setLiqMotivo(prdLiquidacion.getPrdLiquidacionPK().getLiqMotivo());
                reporteLiquidacion.setLiqTotalMonto(prdLiquidacion.getLiqTotalMonto());
                reporteLiquidacion.setLiqUsuario(prdLiquidacion.getUsrCodigo());
                reporteLiquidacion.setPisSector(prdLiquidacion.getPrdPiscina().getPrdSector().getSecNombre());
                reporteLiquidacion.setPisNumero(prdLiquidacion.getPrdPiscina().getPisNombre());
                reporteLiquidacion.setLiqNumero(prdLiquidacion.getPrdLiquidacionPK().getLiqNumero());
                reporteLiquidacion.setLiqLote(prdLiquidacion.getLiqLote());
                reporteLiquidacion.setLiqLibrasEnviadas(prdLiquidacion.getLiqLibrasEnviadas());
                reporteLiquidacion.setLiqLibrasRecibidas(prdLiquidacion.getLiqLibrasRecibidas());
                reporteLiquidacion.setLiqLibrasBasura(prdLiquidacion.getLiqLibrasBasura());
                reporteLiquidacion.setLiqLibrasNetas(prdLiquidacion.getLiqLibrasNetas());
                reporteLiquidacion.setLiqLibrasEntero(prdLiquidacion.getLiqLibrasEntero());
                reporteLiquidacion.setLiqLibrasCola(prdLiquidacion.getLiqLibrasCola());
                reporteLiquidacion.setLiqAnimalesCosechados(prdLiquidacion.getLiqAnimalesCosechados());
                reporteLiquidacion.setLiqLibrasRetiradas(prdLiquidacion.getLiqLibrasRetiradas());
                reporteLiquidacion.setLiqLibrasColaProcesadas(prdLiquidacion.getLiqLibrasColaProcesadas());
                reporteLiquidacion.setProvCodigo(prdLiquidacion.getInvProveedor() != null ? prdLiquidacion.getInvProveedor().getInvProveedorPK().getProvCodigo() : "");
                reporteLiquidacion.setProvNombre(prdLiquidacion.getInvProveedor() != null ? prdLiquidacion.getInvProveedor().getProvRazonSocial() : "");
                reporteLiquidacion.setLiqAnulado(prdLiquidacion.getLiqAnulado());
                reporteLiquidacion.setLiqGramaje(prdLiquidacion.getLiqGramaje() == null ? BigDecimal.ZERO : prdLiquidacion.getLiqGramaje());
                reporteLiquidacion.setLiqGavetas(prdLiquidacion.getLiqGavetas() == null ? BigDecimal.ZERO : prdLiquidacion.getLiqGavetas());
                reporteLiquidacion.setLiqPesoPromedio(prdLiquidacion.getLiqPesoPromedio() == null ? BigDecimal.ZERO : prdLiquidacion.getLiqPesoPromedio());
                reporteLiquidacion.setLiqLarvilla(prdLiquidacion.getLiqLarvilla() == null ? BigDecimal.ZERO : prdLiquidacion.getLiqLarvilla());
                reporteLiquidacion.setLiqQuebrado(prdLiquidacion.getLiqQuebrado() == null ? BigDecimal.ZERO : prdLiquidacion.getLiqQuebrado());;
                reporteLiquidacion.setLiqObservaciones(prdLiquidacion.getLiqObservaciones());
                reporteLiquidacion.setDetGuia(prdLiquidacion.getLiqGuia());
                reporteLiquidacion.setLiqBines(prdLiquidacion.getLiqBines());
                reporteLiquidacion.setLiqAguaje(prdLiquidacion.getLiqAguaje());
                reporteLiquidacion.setLiqPiscina(prdLiquidacion.getLiqPiscina());
                reporteLiquidacion.setLiqComisionista(prdLiquidacion.getLiqComisionista());
                reporteLiquidacion.setLiqTipoReliquidacion(prdLiquidacion.getLiqTipoReliquidacion());
                if (prdLiquidacion.getPrdCorrida() != null) {
                    reporteLiquidacion.setCorFechaDesde(UtilsDate.fechaFormatoString(prdLiquidacion.getPrdCorrida().getCorFechaDesde(), "yyyy-MM-dd"));
                    reporteLiquidacion.setCorFechaHasta(UtilsDate.fechaFormatoString(prdLiquidacion.getPrdCorrida().getCorFechaHasta(), "yyyy-MM-dd"));
                    reporteLiquidacion.setCorNumero(prdLiquidacion.getPrdCorrida().getPrdCorridaPK().getCorNumero());
                    reporteLiquidacion.setCorPiscina(prdLiquidacion.getPrdCorrida().getPrdCorridaPK().getCorPiscina());
                    reporteLiquidacion.setCorSector(prdLiquidacion.getPrdCorrida().getPrdCorridaPK().getCorSector());
                }
                MathContext precision = new MathContext(4);
                if (prdLiquidacion.getLiqLibrasNetas() != null && prdLiquidacion.getLiqLibrasNetas().compareTo(BigDecimal.ZERO) == 1) {
                    BigDecimal div = prdLiquidacion.getLiqLibrasEntero().divide(prdLiquidacion.getLiqLibrasNetas(), precision);
                    reporteLiquidacion.setRendimiento(div.multiply(new BigDecimal("100.00")));//rendimiento total
                } else {
                    reporteLiquidacion.setRendimiento(BigDecimal.ZERO);
                }
                if(prdLiquidacion.getLiqLibrasCola() != null && prdLiquidacion.getLiqLibrasCola().compareTo(BigDecimal.ZERO) == 1){
                    BigDecimal cal = prdLiquidacion.getLiqLibrasColaProcesadas().divide(prdLiquidacion.getLiqLibrasCola(), precision);
                    reporteLiquidacion.setLiqRendimientoCola(cal.multiply(new BigDecimal("100.00")));
                }
                //DETALLE
                reporteLiquidacion.setProdCodigo("");
                reporteLiquidacion.setLiqClase("");
                reporteLiquidacion.setTallaCodigo("");
                reporteLiquidacion.setUnidadMedida("");
                reporteLiquidacion.setDetLibras(BigDecimal.ZERO);
                reporteLiquidacion.setDetPrecio(BigDecimal.ZERO);
                reporteLiquidacion.setDetTotal(BigDecimal.ZERO);
                reporteLiquidacion.setTotalLibras(totalLibras);
                reporteLiquidacionListado.add(reporteLiquidacion);
            }
        }
        if (empresa.equals("OCE")) {
            reporteLiquidacionListado.sort(Comparator.comparing(ReporteLiquidacionPesca::getTallaCodigo));
            reporteLiquidacionListado.sort(Comparator.comparing(ReporteLiquidacionPesca::getProdCodigo));
        }
        for (int i = 0; i < reporteLiquidacionListado.size(); i++) {
            totalLibras = totalLibras.add(reporteLiquidacionListado.get(i).getDetLibras());
            int pos = reporteLiquidacionListado.size();
            if (i == (pos - 1)) {
                reporteLiquidacionListado.get(0).setTotalLibras(totalLibras);
            }
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, parametros, reporteLiquidacionListado);
    }

    @Override
    public byte[] generarReporteLiquidacionPescaListado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String empresa,
            String nombreReporte, List<ListaLiquidacionTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReportePreLiquidacionPescaListado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String empresa,
            String nombreReporte, List<ListaPreLiquidacionTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public byte[] generarReportePescaPreLiquidacionPorLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String empresa,
            String nombreReporte, List<ListaPreLiquidacionTO> listado) throws Exception {
        List<ReporteLiquidacionPesca> reporteLiquidacionListado = new ArrayList<>();

        for (ListaPreLiquidacionTO itemLiquidacion : listado) {
            PrdPreLiquidacion prdPreLiquidacion = preLiquidacionService.getPrdPreLiquidacion(new PrdPreLiquidacionPK(empresa, itemLiquidacion.getPlMotivo(), itemLiquidacion.getPlNumero()));
            List<PrdPreLiquidacionDetalle> listaPrdliqDetalle = ordenacionBurbujaImprimirPreLiquidacion(prdPreLiquidacion.getPrdPreLiquidacionDetalleList());

            if (prdPreLiquidacion.getPrdCorrida() == null) {
                PrdCorrida c = preLiquidacionService.obtenerCorrida(new PrdPreLiquidacionPK(empresa, itemLiquidacion.getPlMotivo(), itemLiquidacion.getPlNumero()), null);
                if (c != null) {
                    prdPreLiquidacion.setPrdCorrida(c);
                }
            }

            listaPrdliqDetalle.stream().map((PrdPreLiquidacionDetalle icd) -> {
                PrdPreLiquidacionDetalle prdLiquidacionDetalle = new PrdPreLiquidacionDetalle();
                ReporteLiquidacionPesca reporteLiquidacion = new ReporteLiquidacionPesca();

                reporteLiquidacion.setLiqEmpresa(prdPreLiquidacion.getPrdPreLiquidacionPK().getPlEmpresa());
                reporteLiquidacion.setCliCodigo(itemLiquidacion.getCliCodigo());
                reporteLiquidacion.setCliNombre(itemLiquidacion.getCliNombre());
                reporteLiquidacion.setSectorDetalle(prdPreLiquidacion.getPrdPiscina() != null && prdPreLiquidacion.getPrdPiscina().getPrdSector() != null ? prdPreLiquidacion.getPrdPiscina().getPrdSector().getSecNombre() : "");
                reporteLiquidacion.setLiqFecha(UtilsDate.fechaFormatoString(prdPreLiquidacion.getPlFecha(), "yyyy-MM-dd"));
                reporteLiquidacion.setLiqMotivo(prdPreLiquidacion.getPrdPreLiquidacionPK().getPlMotivo());
                reporteLiquidacion.setLiqTotalMonto(prdPreLiquidacion.getPlTotalMonto());
                reporteLiquidacion.setLiqUsuario(prdPreLiquidacion.getUsrCodigo());
                reporteLiquidacion.setPisSector(prdPreLiquidacion.getPrdPiscina().getPrdSector().getSecNombre());
                reporteLiquidacion.setPisNumero(prdPreLiquidacion.getPrdPiscina().getPisNombre());
                reporteLiquidacion.setLiqNumero(prdPreLiquidacion.getPrdPreLiquidacionPK().getPlNumero());
                reporteLiquidacion.setLiqLote(prdPreLiquidacion.getPlLote());
                reporteLiquidacion.setLiqLibrasEnviadas(prdPreLiquidacion.getPlLibrasEnviadas());
                reporteLiquidacion.setLiqLibrasRecibidas(prdPreLiquidacion.getPlLibrasRecibidas());
                reporteLiquidacion.setLiqLibrasBasura(prdPreLiquidacion.getPlLibrasBasura());
                reporteLiquidacion.setLiqLibrasNetas(prdPreLiquidacion.getPlLibrasNetas());
                reporteLiquidacion.setLiqLibrasEntero(prdPreLiquidacion.getPlLibrasEntero());
                reporteLiquidacion.setLiqLibrasCola(prdPreLiquidacion.getPlLibrasCola());
                reporteLiquidacion.setLiqAnimalesCosechados(prdPreLiquidacion.getPlAnimalesCosechados());
                reporteLiquidacion.setLiqLibrasRetiradas(prdPreLiquidacion.getPlLibrasRetiradas());
                reporteLiquidacion.setLiqLibrasColaProcesadas(prdPreLiquidacion.getPlLibrasColaProcesadas());

                MathContext precision = new MathContext(4);
                BigDecimal div = prdPreLiquidacion.getPlLibrasEntero().divide(prdPreLiquidacion.getPlLibrasNetas(), precision);
                reporteLiquidacion.setRendimiento(div.multiply(new BigDecimal("100.00")));//rendimiento total

                if (prdPreLiquidacion.getPrdCorrida() != null) {
                    reporteLiquidacion.setCorFechaDesde(UtilsDate.fechaFormatoString(prdPreLiquidacion.getPrdCorrida().getCorFechaDesde(), "yyyy-MM-dd"));
                    reporteLiquidacion.setCorFechaHasta(UtilsDate.fechaFormatoString(prdPreLiquidacion.getPrdCorrida().getCorFechaHasta(), "yyyy-MM-dd"));
                    reporteLiquidacion.setCorNumero(prdPreLiquidacion.getPrdCorrida().getPrdCorridaPK().getCorNumero());
                    reporteLiquidacion.setCorPiscina(prdPreLiquidacion.getPrdCorrida().getPrdCorridaPK().getCorPiscina());
                    reporteLiquidacion.setCorSector(prdPreLiquidacion.getPrdCorrida().getPrdCorridaPK().getCorSector());
                }
                //DETALLE
                reporteLiquidacion.setProdCodigo(icd.getPrdProducto().getProdDetalle());
                reporteLiquidacion.setLiqClase(icd.getPrdProducto().getProdTipo());
                reporteLiquidacion.setTallaCodigo(icd.getPrdTalla().getTallaDetalle());
                reporteLiquidacion.setUnidadMedida(icd.getPrdTalla().getTallaUnidadMedida());
                reporteLiquidacion.setDetLibras(icd.getDetLibras());
                reporteLiquidacion.setDetPrecio(icd.getDetPrecio());
                reporteLiquidacion.setDetTotal(icd.getDetLibras().multiply(icd.getDetPrecio()));
                return reporteLiquidacion;
            }).forEachOrdered((reporteLiquidacion) -> {
                reporteLiquidacionListado.add(reporteLiquidacion);
            });
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<>(), reporteLiquidacionListado);
    }

    @Override
    public byte[] generarReportePrdProyeccion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte,
            List<PrdProyeccionTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public Map<String, Object> exportarReportePescaPreLiquidacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ListaPreLiquidacionTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de pre liquidación de pesca");
            listaCabecera.add("S");
            listaCuerpo.add("SMotivo" + "¬" + "SNúmero" + "¬" + "SLote" + "¬" + "SFecha" + "¬" + "Ssector" + "¬" + "SPiscina" + "¬" + "SCliente" + "¬" + "Stotal");
            listado.forEach((liq) -> {
                listaCuerpo.add((liq.getPlMotivo() == null ? "B" : "S" + liq.getPlMotivo())
                        + "¬" + (liq.getPlNumero() == null ? "B" : "S" + liq.getPlNumero())
                        + "¬" + (liq.getPlLote() == null ? "B" : "S" + liq.getPlLote())
                        + "¬" + (liq.getPlFecha() == null ? "B" : "S" + liq.getPlFecha())
                        + "¬" + (liq.getPisSector() == null ? "B" : "S" + liq.getPisSector())
                        + "¬" + (liq.getPisNumero() == null ? "B" : "S" + liq.getPisNumero())
                        + "¬" + (liq.getCliNombre() == null ? "B" : "S" + liq.getCliNombre())
                        + "¬" + (liq.getPlTotalMonto() == null ? "B" : "D" + liq.getPlTotalMonto())
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReportePrdProyeccion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdProyeccionTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de proyección");
            listaCabecera.add("S");
            listaCuerpo.add("SMotivo" + "¬" + "SNúmero" + "¬" + "SLote" + "¬" + "SFecha" + "¬" + "Ssector" + "¬" + "SPiscina" + "¬" + "SCliente" + "¬" + "Stotal");
            for (PrdProyeccionTO pro : listado) {
                /* listaCuerpo.add((pro.get() == null ? "B" : "S" + pro.getPlMotivo())
                 + "¬" + (pro.getPlNumero() == null ? "B" : "S" + pro.getPlNumero())
                 + "¬" + (pro.getPlLote() == null ? "B" : "S" + pro.getPlLote())
                 + "¬" + (pro.getPlFecha() == null ? "B" : "S" + pro.getPlFecha())
                 + "¬" + (pro.getPisSector() == null ? "B" : "S" + pro.getPisSector())
                 + "¬" + (pro.getPisNumero() == null ? "B" : "S" + pro.getPisNumero())
                 + "¬" + (pro.getCliNombre() == null ? "B" : "S" + pro.getCliNombre())
                 + "¬" + (pro.getPlTotalMonto() == null ? "B" : "D" + pro.getPlTotalMonto())
                 );*/
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteConsumos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvListaConsultaConsumosTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de consumos");
            listaCabecera.add("S");
            listaCuerpo.add("SNúmero" + "¬" + "SFecha" + "¬" + "SObservaciones" + "¬" + "SEstado");
            listado.forEach((cons) -> {
                listaCuerpo.add((cons.getConsNumero() == null ? "B" : "S" + cons.getConsNumero())
                        + "¬" + (cons.getConsFecha() == null ? "B" : "S" + cons.getConsFecha())
                        + "¬" + (cons.getConsObservaciones() == null ? "B" : "S" + cons.getConsObservaciones())
                        + "¬" + (cons.getConsStatus() == null ? "B" : "S" + cons.getConsStatus())
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    //metodo exportar excel consolidando tallas
    @Override
    public Map<String, Object> exportarReporteConsolidandoTallas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception {
        try {
            List<PrdFunLiquidacionConsolidandoTallasTO> listado = UtilsJSON.jsonToList(PrdFunLiquidacionConsolidandoTallasTO.class, map.get("listado"));
            String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
            String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
            String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
            String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));

            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de Consolidando Tallas");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("SCliente: " + cliente);
            listaCabecera.add("TSector: " + sector);
            listaCabecera.add("S");
            listaCuerpo.add("STalla" + "¬" + "SCodigo" + "¬" + "SMedida" + "¬" + "SLibras" + "¬" + "SPorcentaje" + "¬" + "STotal");
            listado.forEach((cons) -> {
                listaCuerpo.add(
                        (cons.getLctTalla() == null ? "B" : "S" + cons.getLctTalla())
                        + "¬" + (cons.getLctCodigo() == null ? "B" : "S" + cons.getLctCodigo())
                        + "¬" + (cons.getLctMedida() == null ? "B" : "S" + cons.getLctMedida())
                        + "¬" + (cons.getLctLibras() == null ? "B" : "D" + cons.getLctLibras())
                        + "¬" + (cons.getLctPorcentaje() == null ? "B" : "D" + cons.getLctPorcentaje())
                        + "¬" + (cons.getLctTotal() == null ? "B" : "D" + cons.getLctTotal())
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }

    }

    //metodo exportar excel consolidando tallas
    @Override
    public Map<String, Object> exportarReportePreConsolidandoTallas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception {
        try {
            List<PrdFunPreLiquidacionConsolidandoTallasTO> listado = UtilsJSON.jsonToList(PrdFunPreLiquidacionConsolidandoTallasTO.class, map.get("listado"));
            String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
            String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
            String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
            String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));

            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de PreLiquidacion Consolidando Tallas");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("SCliente: " + cliente);
            listaCabecera.add("TSector: " + sector);
            listaCabecera.add("S");
            listaCuerpo.add("STalla" + "¬" + "SCodigo" + "¬" + "SMedida" + "¬" + "SLibras" + "¬" + "SPorcentaje" + "¬" + "STotal");
            listado.forEach((cons) -> {
                listaCuerpo.add(
                        (cons.getPlctTalla() == null ? "B" : "S" + cons.getPlctTalla())
                        + "¬" + (cons.getPlctCodigo() == null ? "B" : "S" + cons.getPlctCodigo())
                        + "¬" + (cons.getPlctMedida() == null ? "B" : "S" + cons.getPlctMedida())
                        + "¬" + (cons.getPlctLibras() == null ? "B" : "D" + cons.getPlctLibras())
                        + "¬" + (cons.getPlctPorcentaje() == null ? "B" : "D" + cons.getPlctPorcentaje())
                        + "¬" + (cons.getPlctTotal() == null ? "B" : "D" + cons.getPlctTotal())
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }

    }

    //metodo exportar excel
    @Override
    public Map<String, Object> exportarReporteLiquidacionDetalleProductos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception {
        try {

            List<PrdLiquidacionDetalleProductoTO> listado = UtilsJSON.jsonToList(PrdLiquidacionDetalleProductoTO.class, map.get("listado"));
            String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
            String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
            String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
            String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
            String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
            String talla = UtilsJSON.jsonToObjeto(String.class, map.get("talla"));
            String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));

            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte liquidación detalle Productos");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("SCentro de producción: " + sector + " Centro de costo: " + piscina);
            listaCabecera.add("STalla: " + talla + " Tipo: " + tipo);
            listaCabecera.add("SCliente: " + cliente);
            listaCabecera.add("S");
            listaCuerpo.add("SNúmero" + "¬" + "SFecha" + "¬" + "SId Cliente" + "¬" + "SRazón Social" + "¬" + "SLote" + "¬" + "SNombre Producto" + "¬" + "SClase" + "¬" + "SPiscina" + "¬" + "STalla" + "¬" + "STipo" + "¬" + "SLibras" + "¬" + "SPrecio" + "¬" + "STotal");
            listado.forEach((cons) -> {
                BigDecimal total = cons.getDetLibras().multiply(cons.getDetPrecio());
                listaCuerpo.add((cons.getLiqNumeroSistema() == null ? "B" : "S" + cons.getLiqNumeroSistema())
                        + "¬" + (cons.getLiqFecha() == null ? "B" : "S" + UtilsDate.convetirDateConFormato(cons.getLiqFecha()))
                        + "¬" + (cons.getLiqClienteId() == null ? "B" : "S" + cons.getLiqClienteId())
                        + "¬" + (cons.getLiqClienteRazonSocial() == null ? "B" : "S" + cons.getLiqClienteRazonSocial())
                        + "¬" + (cons.getLiqLote() == null ? "B" : "S" + cons.getLiqLote())
                        + "¬" + (cons.getDetProductoNombre() == null ? "B" : "S" + cons.getDetProductoNombre())
                        + "¬" + (cons.getDetProductoClase() == null ? "B" : "S" + cons.getDetProductoClase())
                        + "¬" + (cons.getPisNumero() == null ? "B" : "S" + cons.getPisNumero())
                        + "¬" + (cons.getLiqTallaDetalle() == null ? "B" : "S" + cons.getLiqTallaDetalle())
                        + "¬" + (cons.getDetProductoTipo() == null ? "B" : "S" + cons.getDetProductoTipo())
                        + "¬" + (cons.getDetLibras() == null ? "B" : "D" + cons.getDetLibras())
                        + "¬" + (cons.getDetPrecio() == null ? "B" : "D" + cons.getDetPrecio())
                        + "¬" + (total == null ? "B" : "D" + total));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    //metodo exportar excel
    @Override
    public Map<String, Object> exportarReporteLiquidacionDetalleProductosEmpacadora(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception {
        try {

            List<PrdLiquidacionDetalleProductoEmpacadoraTO> listado = UtilsJSON.jsonToList(PrdLiquidacionDetalleProductoEmpacadoraTO.class, map.get("listado"));
            String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
            String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
            String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
            String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
            String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
            String talla = UtilsJSON.jsonToObjeto(String.class, map.get("talla"));
            String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));

            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte liquidación detalle Productos");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("SCentro de producción: " + sector + " Centro de costo: " + piscina);
            listaCabecera.add("STalla: " + talla + " Tipo: " + tipo);
            listaCabecera.add("SCliente: " + cliente);
            listaCabecera.add("S");
            listaCuerpo.add("SNúmero" + "¬" + "SFecha" + "¬" + "SId Proveedor" + "¬" + "SComisionista" + "¬" + "SRazón Social" + "¬" + "SLote" + "¬" + "SNombre Producto" + "¬" + "SClase" + "¬" + "SPiscina" + "¬" + "STalla" + "¬" + "STipo" + "¬" + "SLibras" + "¬" + "SPrecio" + "¬" + "STotal");
            listado.forEach((cons) -> {
                BigDecimal total = cons.getDetLibras().multiply(cons.getDetPrecio());
                listaCuerpo.add((cons.getLiqNumeroSistema() == null ? "B" : "S" + cons.getLiqNumeroSistema())
                        + "¬" + (cons.getLiqFecha() == null ? "B" : "S" + UtilsDate.convetirDateConFormato(cons.getLiqFecha()))
                        + "¬" + (cons.getLiqProveedorId() == null ? "B" : "S" + cons.getLiqProveedorId())
                        + "¬" + (cons.getLiqComisionista() == null ? "B" : "S" + cons.getLiqComisionista())
                        + "¬" + (cons.getLiqProveedorRazonSocial() == null ? "B" : "S" + cons.getLiqProveedorRazonSocial())
                        + "¬" + (cons.getLiqLote() == null ? "B" : "S" + cons.getLiqLote())
                        + "¬" + (cons.getDetProductoNombre() == null ? "B" : "S" + cons.getDetProductoNombre())
                        + "¬" + (cons.getDetProductoClase() == null ? "B" : "S" + cons.getDetProductoClase())
                        + "¬" + (cons.getPisNumero() == null ? "B" : "S" + cons.getPisNumero())
                        + "¬" + (cons.getLiqTallaDetalle() == null ? "B" : "S" + cons.getLiqTallaDetalle())
                        + "¬" + (cons.getDetProductoTipo() == null ? "B" : "S" + cons.getDetProductoTipo())
                        + "¬" + (cons.getDetLibras() == null ? "B" : "D" + cons.getDetLibras())
                        + "¬" + (cons.getDetPrecio() == null ? "B" : "D" + cons.getDetPrecio())
                        + "¬" + (total == null ? "B" : "D" + total));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    //metodo exportar excel
    @Override
    public Map<String, Object> exportarReportePreLiquidacionDetalleProductos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception {
        try {

            List<PrdPreLiquidacionDetalleProductoTO> listado = UtilsJSON.jsonToList(PrdPreLiquidacionDetalleProductoTO.class, map.get("listado"));
            String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
            String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
            String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
            String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
            String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
            String talla = UtilsJSON.jsonToObjeto(String.class, map.get("talla"));
            String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));

            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Preliquidación detalle Productos");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("SCentro de producción: " + sector + " Centro de costo: " + piscina);
            listaCabecera.add("STalla: " + talla + " Tipo: " + tipo);
            listaCabecera.add("SCliente: " + cliente);
            listaCabecera.add("S");
            listaCuerpo.add("SNúmero" + "¬" + "SFecha" + "¬" + "SId Cliente" + "¬" + "SRazón Social" + "¬" + "SLote" + "¬" + "SNombre Producto" + "¬" + "SClase" + "¬" + "SPiscina" + "¬" + "STalla" + "¬" + "STipo" + "¬" + "SLibras" + "¬" + "SPrecio" + "¬" + "STotal");
            listado.forEach((cons) -> {
                BigDecimal total = cons.getDetLibras().multiply(cons.getDetPrecio());
                listaCuerpo.add((cons.getPlNumeroSistema() == null ? "B" : "S" + cons.getPlNumeroSistema())
                        + "¬" + (cons.getPlFecha() == null ? "B" : "S" + UtilsDate.convetirDateConFormato(cons.getPlFecha()))
                        + "¬" + (cons.getPlClienteId() == null ? "B" : "S" + cons.getPlClienteId())
                        + "¬" + (cons.getPlClienteRazonSocial() == null ? "B" : "S" + cons.getPlClienteRazonSocial())
                        + "¬" + (cons.getPlLote() == null ? "B" : "S" + cons.getPlLote())
                        + "¬" + (cons.getDetProductoNombre() == null ? "B" : "S" + cons.getDetProductoNombre())
                        + "¬" + (cons.getDetProductoClase() == null ? "B" : "S" + cons.getDetProductoClase())
                        + "¬" + (cons.getPisNumero() == null ? "B" : "S" + cons.getPisNumero())
                        + "¬" + (cons.getPlTallaDetalle() == null ? "B" : "S" + cons.getPlTallaDetalle())
                        + "¬" + (cons.getDetProductoTipo() == null ? "B" : "S" + cons.getDetProductoTipo())
                        + "¬" + (cons.getDetLibras() == null ? "B" : "D" + cons.getDetLibras())
                        + "¬" + (cons.getDetPrecio() == null ? "B" : "D" + cons.getDetPrecio())
                        + "¬" + (total == null ? "B" : "D" + total));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    //exportarExcelClientes
    @Override
    public Map<String, Object> exportarReporteLiquidacionConsolidandoClientes(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception {
        try {
            List<PrdLiquidacionConsolidandoClientesTO> listado = UtilsJSON.jsonToList(PrdLiquidacionConsolidandoClientesTO.class, map.get("listado"));
            String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
            String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
            String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
            String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));

            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte liquidación detalle Clientes");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("SCliente: " + cliente);
            listaCabecera.add("TSector: " + sector);
            listaCabecera.add("S");
            listaCuerpo.add("SIdentificación" + "¬" + "SCliente" + "¬" + "SLibras Enviadas" + "¬" + "SLibras Recibidas" + "¬" + "SLibras Basura" + "¬" + "SLibras Netas" + "¬" + "SLibras Entero" + "¬" + "SLibras Cola" + "¬" + "SLibras Cola Procesada" + "¬" + "SMonto Total");
            listado.forEach((cons) -> {
                listaCuerpo.add((cons.getLiqIdentificacion() == null ? "B" : "S" + cons.getLiqIdentificacion())
                        + "¬" + (cons.getLiqNombreCliente() == null ? "B" : "S" + cons.getLiqNombreCliente())
                        + "¬" + (cons.getLiqLibrasEnviadas() == null ? "B" : "D" + cons.getLiqLibrasEnviadas())
                        + "¬" + (cons.getLiqLibrasRecibidas() == null ? "B" : "D" + cons.getLiqLibrasRecibidas())
                        + "¬" + (cons.getLiqLibrasBasura() == null ? "B" : "D" + cons.getLiqLibrasBasura())
                        + "¬" + (cons.getLiqLibrasNetas() == null ? "B" : "D" + cons.getLiqLibrasNetas())
                        + "¬" + (cons.getLiqLibrasEntero() == null ? "B" : "D" + cons.getLiqLibrasEntero())
                        + "¬" + (cons.getLiqLibrasCola() == null ? "B" : "D" + cons.getLiqLibrasCola())
                        + "¬" + (cons.getLiqLibrasColaProcesadas() == null ? "B" : "D" + cons.getLiqLibrasColaProcesadas())
                        + "¬" + (cons.getLiqTotalMonto() == null ? "B" : "D" + cons.getLiqTotalMonto()));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    //exportarExcelClientes
    @Override
    public Map<String, Object> exportarReporteLiquidacionConsolidandoProveedores(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception {
        try {
            List<PrdLiquidacionConsolidandoProveedoresTO> listado = UtilsJSON.jsonToList(PrdLiquidacionConsolidandoProveedoresTO.class, map.get("listado"));
            String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
            String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
            String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
            String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));

            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte liquidación detalle Proveedores");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("SProveedor: " + proveedor);
            listaCabecera.add("TSector: " + sector);
            listaCabecera.add("S");
            listaCuerpo.add("SIdentificación" + "¬" + "SProveedor" + "¬" + "SComisionista" + "¬" + "SLibras Enviadas" + "¬" + "SLibras Recibidas" + "¬" + "SLibras Basura" + "¬" + "SLibras Netas" + "¬" + "SLibras Entero" + "¬" + "SLibras Cola" + "¬" + "SLibras Cola Procesada" + "¬" + "SMonto Total");
            listado.forEach((cons) -> {
                listaCuerpo.add((cons.getLiqIdentificacion() == null ? "B" : "S" + cons.getLiqIdentificacion())
                        + "¬" + (cons.getLiqNombreProveedor() == null ? "B" : "S" + cons.getLiqNombreProveedor())
                        + "¬" + (cons.getLiqComisionista() == null ? "B" : "S" + cons.getLiqComisionista())
                        + "¬" + (cons.getLiqLibrasEnviadas() == null ? "B" : "D" + cons.getLiqLibrasEnviadas())
                        + "¬" + (cons.getLiqLibrasRecibidas() == null ? "B" : "D" + cons.getLiqLibrasRecibidas())
                        + "¬" + (cons.getLiqLibrasBasura() == null ? "B" : "D" + cons.getLiqLibrasBasura())
                        + "¬" + (cons.getLiqLibrasNetas() == null ? "B" : "D" + cons.getLiqLibrasNetas())
                        + "¬" + (cons.getLiqLibrasEntero() == null ? "B" : "D" + cons.getLiqLibrasEntero())
                        + "¬" + (cons.getLiqLibrasCola() == null ? "B" : "D" + cons.getLiqLibrasCola())
                        + "¬" + (cons.getLiqLibrasColaProcesadas() == null ? "B" : "D" + cons.getLiqLibrasColaProcesadas())
                        + "¬" + (cons.getLiqTotalMonto() == null ? "B" : "D" + cons.getLiqTotalMonto()));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReportePrdListadoFitoplancton(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception {
        try {
            List<PrdListadoFitoplanctonTO> listado = UtilsJSON.jsonToList(PrdListadoFitoplanctonTO.class, map.get("listado"));
            String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
            String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
            String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
            String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
            BigDecimal cero = new BigDecimal("0.00");
            BigDecimal cien = new BigDecimal("100.00");
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte analisis fitoplancton");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("SPiscina: " + piscina);
            listaCabecera.add("TSector: " + sector);
            listaCabecera.add("S");
            listaCuerpo.add("SPiscina"
                    + "¬" + "SFecha"
                    + "¬" + "SPorc. salinidad" + "¬" + "S%"
                    + "¬" + "SDiatomeas" + "¬" + "S%"
                    + "¬" + "SClorophytas" + "¬" + "S%"
                    + "¬" + "SCyanofitas" + "¬" + "S%"
                    + "¬" + "SEuglenales" + "¬" + "S%"
                    + "¬" + "SDinoflagelados" + "¬" + "S%"
                    + "¬" + "SBuenas" + "¬" + "S%"
                    + "¬" + "SMalas"
                    + "¬" + "STotal");
            listado.forEach((cons) -> {
                BigDecimal buenas = (cons.getFitClorophytas() == null ? cero : cons.getFitClorophytas()).add(cons.getFitDiatomeas() == null ? cero : cons.getFitDiatomeas());
                BigDecimal malas = (cons.getFitCyanofitas() == null ? cero : cons.getFitCyanofitas()).add(cons.getFitEuglenales() == null ? cero : cons.getFitEuglenales()).add(cons.getFitDinoflagelados() == null ? cero : cons.getFitDinoflagelados());
                BigDecimal total = buenas.add(malas);

                BigDecimal porcentajeDiatomeas = (cons.getFitDiatomeas() == null ? cero : cons.getFitDiatomeas()).divide(total, 6, RoundingMode.CEILING);
                BigDecimal porcentajeClorophytas = (cons.getFitClorophytas() == null ? cero : cons.getFitClorophytas()).divide(total, 6, RoundingMode.CEILING);
                BigDecimal porcentajeCyanofitas = (cons.getFitCyanofitas() == null ? cero : cons.getFitCyanofitas()).divide(total, 6, RoundingMode.CEILING);
                BigDecimal porcentajeEuglenales = (cons.getFitEuglenales() == null ? cero : cons.getFitEuglenales()).divide(total, 6, RoundingMode.CEILING);
                BigDecimal porcentajeDinoflagelados = (cons.getFitDinoflagelados() == null ? cero : cons.getFitDinoflagelados()).divide(total, 6, RoundingMode.CEILING);
                BigDecimal porcentajeBuenas = buenas.divide(total, 6, RoundingMode.CEILING);
                BigDecimal porcentajeMalas = malas.divide(total, 6, RoundingMode.CEILING);

                listaCuerpo.add((cons.getFitPiscinaNombre() == null ? "B" : "S" + cons.getFitPiscinaNombre())
                        + "¬" + (cons.getFitFecha() == null ? "B" : "S" + UtilsDate.fechaFormatoString(cons.getFitFecha(), "yyyy-MM-dd"))
                        + "¬" + (cons.getFitPorcentajeSalinidad() == null ? "B" : "D" + cons.getFitPorcentajeSalinidad())
                        + "¬D" + (porcentajeDiatomeas.multiply(cien))
                        + "¬" + (cons.getFitDiatomeas() == null ? "B" : "D" + cons.getFitDiatomeas())
                        + "¬D" + (porcentajeClorophytas.multiply(cien))
                        + "¬" + (cons.getFitClorophytas() == null ? "B" : "D" + cons.getFitClorophytas())
                        + "¬D" + (porcentajeCyanofitas.multiply(cien))
                        + "¬" + (cons.getFitCyanofitas() == null ? "B" : "D" + cons.getFitCyanofitas())
                        + "¬D" + (porcentajeEuglenales.multiply(cien))
                        + "¬" + (cons.getFitEuglenales() == null ? "B" : "D" + cons.getFitEuglenales())
                        + "¬D" + (porcentajeDinoflagelados.multiply(cien))
                        + "¬" + (cons.getFitDinoflagelados() == null ? "B" : "D" + cons.getFitDinoflagelados())
                        + "¬D" + (porcentajeBuenas.multiply(cien))
                        + "¬D" + (buenas)
                        + "¬D" + (porcentajeMalas.multiply(cien))
                        + "¬D" + (malas)
                        + "¬D" + (total)
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    //exportarExcelConsultas
    @Override
    public Map<String, Object> exportarReporteLiquidacionConsultas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception {
        try {
            List<PrdLiquidacionConsultaTO> listado = UtilsJSON.jsonToList(PrdLiquidacionConsultaTO.class, map.get("listado"));
            String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
            String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
            String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
            String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));

            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte liquidación Cosultas");
            listaCabecera.add("SDesde: " + fechaDesde + "    Hasta: " + fechaHasta);
            listaCabecera.add("TCentro de producción: " + sector + "     Centro de costo: " + piscina);
            listaCabecera.add("S");
            listaCuerpo.add("SMotivo" + "¬" + "SNúmero" + "¬" + "SLote" + "¬" + "SFecha"
                    + "¬" + "SLibras Enviadas" + "¬" + "SLibras Recibidas" + "¬" + "SLibras Basura"
                    + "¬" + "SLibras Netas" + "¬" + "SLibras Entero"
                    + "¬" + "SLibras Cola" + "¬" + "SLibras Procesadas" + "¬" + "STotal" + "¬" + "SPendiente" + "¬" + "SAnulado");
            listado.forEach((cons) -> {
                listaCuerpo.add((cons.getLiqMotivo() == null ? "B" : "S" + cons.getLiqMotivo())
                        + "¬" + (cons.getLiqNumero() == null ? "B" : "S" + cons.getLiqNumero())
                        + "¬" + (cons.getLiqLote() == null ? "B" : "S" + cons.getLiqLote())
                        + "¬" + (cons.getLiqFecha() == null ? "B" : "S" + cons.getLiqFecha())
                        + "¬" + (cons.getLiqLibrasEnviadas() == null ? "B" : "D" + cons.getLiqLibrasEnviadas())
                        + "¬" + (cons.getLiqLibrasRecibidas() == null ? "B" : "D" + cons.getLiqLibrasRecibidas())
                        + "¬" + (cons.getLiqLibrasBasura() == null ? "B" : "D" + cons.getLiqLibrasBasura())
                        + "¬" + (cons.getLiqLibrasNetas() == null ? "B" : "D" + cons.getLiqLibrasNetas())
                        + "¬" + (cons.getLiqLibrasEntero() == null ? "B" : "D" + cons.getLiqLibrasEntero())
                        + "¬" + (cons.getLiqLibrasCola() == null ? "B" : "D" + cons.getLiqLibrasCola())
                        + "¬" + (cons.getLiqLibrasColaProcesadas() == null ? "B" : "D" + cons.getLiqLibrasColaProcesadas())
                        + "¬" + (cons.getLiqTotalMonto() == null ? "B" : "D" + cons.getLiqTotalMonto())
                        + "¬" + (cons.getLiqPendiente() == null ? "B" : "S" + cons.getLiqPendiente())
                        + "¬" + (cons.getLiqAnulado() == null ? "B" : "S" + cons.getLiqAnulado()));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    //exportarExcelConsultas
    @Override
    public Map<String, Object> exportarReporteLiquidacionConsultasEmpacadora(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception {
        try {
            List<PrdLiquidacionConsultaEmpacadoraTO> listado = UtilsJSON.jsonToList(PrdLiquidacionConsultaEmpacadoraTO.class, map.get("listado"));
            String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
            String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
            String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
            String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));

            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte liquidación Cosultas (Empacadora)");
            listaCabecera.add("SDesde: " + fechaDesde + "    Hasta: " + fechaHasta);
            listaCabecera.add("TCentro de producción: " + sector + "     Centro de costo: " + piscina);
            listaCabecera.add("S");
            listaCuerpo.add("SMotivo" + "¬" + "SNúmero" + "¬" + "SLote" + "¬" + "SProveedor" + "¬" + "SComisionista" + "¬" + "SFecha"
                    + "¬" + "SLibras Enviadas" + "¬" + "SLibras Recibidas" + "¬" + "SLibras Basura"
                    + "¬" + "SLibras Netas" + "¬" + "SLibras Entero"
                    + "¬" + "SLibras Cola" + "¬" + "SLibras Procesadas" + "¬" + "STotal" + "¬" + "SPendiente" + "¬" + "SAnulado");
            listado.forEach((cons) -> {
                listaCuerpo.add((cons.getLiqMotivo() == null ? "B" : "S" + cons.getLiqMotivo())
                        + "¬" + (cons.getLiqNumero() == null ? "B" : "S" + cons.getLiqNumero())
                        + "¬" + (cons.getLiqLote() == null ? "B" : "S" + cons.getLiqLote())
                        + "¬" + (cons.getLiqProveedorNombre() == null ? "B" : "S" + cons.getLiqProveedorNombre())
                        + "¬" + (cons.getLiqComisionista() == null ? "B" : "S" + cons.getLiqComisionista())
                        + "¬" + (cons.getLiqFecha() == null ? "B" : "S" + cons.getLiqFecha())
                        + "¬" + (cons.getLiqLibrasEnviadas() == null ? "B" : "D" + cons.getLiqLibrasEnviadas())
                        + "¬" + (cons.getLiqLibrasRecibidas() == null ? "B" : "D" + cons.getLiqLibrasRecibidas())
                        + "¬" + (cons.getLiqLibrasBasura() == null ? "B" : "D" + cons.getLiqLibrasBasura())
                        + "¬" + (cons.getLiqLibrasNetas() == null ? "B" : "D" + cons.getLiqLibrasNetas())
                        + "¬" + (cons.getLiqLibrasEntero() == null ? "B" : "D" + cons.getLiqLibrasEntero())
                        + "¬" + (cons.getLiqLibrasCola() == null ? "B" : "D" + cons.getLiqLibrasCola())
                        + "¬" + (cons.getLiqLibrasColaProcesadas() == null ? "B" : "D" + cons.getLiqLibrasColaProcesadas())
                        + "¬" + (cons.getLiqTotalMonto() == null ? "B" : "D" + cons.getLiqTotalMonto())
                        + "¬" + (cons.getLiqPendiente() == null ? "B" : "S" + cons.getLiqPendiente())
                        + "¬" + (cons.getLiqAnulado() == null ? "B" : "S" + cons.getLiqAnulado()));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    //Exportar preLiquidacion Consultas
    @Override
    public Map<String, Object> exportarReportePreLiquidacionConsultas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception {
        try {
            List<PrdPreLiquidacionConsultaTO> listado = UtilsJSON.jsonToList(PrdPreLiquidacionConsultaTO.class, map.get("listado"));
            String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
            String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
            String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
            String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));

            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Preliquidación Cosultas");
            listaCabecera.add("SDesde: " + fechaDesde + "    Hasta: " + fechaHasta);
            listaCabecera.add("TCentro de producción: " + sector + "     Centro de costo: " + piscina);
            listaCabecera.add("S");
            listaCuerpo.add("SMotivo" + "¬" + "SNúmero" + "¬" + "SLote" + "¬" + "SFecha"
                    + "¬" + "SLibras Enviadas" + "¬" + "SLibras Recibidas" + "¬" + "SLibras Basura"
                    + "¬" + "SLibras Netas" + "¬" + "SLibras Entero"
                    + "¬" + "SLibras Cola" + "¬" + "SLibras Procesadas" + "¬" + "STotal" + "¬" + "SPendiente" + "¬" + "SAnulado");
            listado.forEach((cons) -> {
                listaCuerpo.add((cons.getPlcMotivo() == null ? "B" : "S" + cons.getPlcMotivo())
                        + "¬" + (cons.getPlcNumero() == null ? "B" : "S" + cons.getPlcNumero())
                        + "¬" + (cons.getPlcLote() == null ? "B" : "S" + cons.getPlcLote())
                        + "¬" + (cons.getPlcFecha() == null ? "B" : "S" + cons.getPlcFecha())
                        + "¬" + (cons.getPlcLibrasEnviadas() == null ? "B" : "D" + cons.getPlcLibrasEnviadas())
                        + "¬" + (cons.getPlcLibrasRecibidas() == null ? "B" : "D" + cons.getPlcLibrasRecibidas())
                        + "¬" + (cons.getPlcLibrasBasura() == null ? "B" : "D" + cons.getPlcLibrasBasura())
                        + "¬" + (cons.getPlcLibrasNetas() == null ? "B" : "D" + cons.getPlcLibrasNetas())
                        + "¬" + (cons.getPlcLibrasEntero() == null ? "B" : "D" + cons.getPlcLibrasEntero())
                        + "¬" + (cons.getPlcLibrasCola() == null ? "B" : "D" + cons.getPlcLibrasCola())
                        + "¬" + (cons.getPlcLibrasColaProcesadas() == null ? "B" : "D" + cons.getPlcLibrasColaProcesadas())
                        + "¬" + (cons.getPlcTotalMonto() == null ? "B" : "D" + cons.getPlcTotalMonto())
                        + "¬" + (cons.getPlcPendiente() == null ? "B" : "S" + cons.getPlcPendiente())
                        + "¬" + (cons.getPlcAnulado() == null ? "B" : "S" + cons.getPlcAnulado()));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteLiquidacionesDetalle(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception {
        try {
            List<PrdLiquidacionesDetalleTO> listado = UtilsJSON.jsonToList(PrdLiquidacionesDetalleTO.class, map.get("listado"));
            String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
            String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));

            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte liquidaciones detalle");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SNúmero" + "¬" + "SFecha" + "¬" + "SCliente" + "¬" + "SCorrida" + "¬" + "SLote" + "¬" + "SMes" + "¬"
                    + "SLib. Enviadas" + "¬" + "SLib. Recibidas" + "¬" + "SDiferencia" + "¬" + "SLib. Basura" + "¬" + "SLib. Netas" + "¬"
                    + "SLib. Entero" + "¬" + "SRendimiento" + "¬" + "S20_20" + "¬" + "S30_40" + "¬" + "S40_50" + "¬"
                    + "S50_60" + "¬" + "S60_70" + "¬" + "SOtras tallas" + "¬" + "SPrecio libra promedio" + "¬" + "SMonto total");
            listado.forEach((cons) -> {
                listaCuerpo.add((cons.getLiqNumero() == null ? "B" : "S" + cons.getLiqNumero())
                        + "¬" + (cons.getLiqFecha() == null ? "B" : "S" + cons.getLiqFecha())
                        + "¬" + (cons.getLiqCliente() == null ? "B" : "S" + cons.getLiqCliente())
                        + "¬" + (cons.getLiqCorrida() == null ? "B" : "S" + cons.getLiqCorrida())
                        + "¬" + (cons.getLiqLote() == null ? "B" : "S" + cons.getLiqLote())
                        + "¬" + (cons.getLiqMes() == null ? "B" : "S" + cons.getLiqMes())
                        + "¬" + (cons.getLiqLibrasEnviadas() == null ? "B" : "D" + cons.getLiqLibrasEnviadas())
                        + "¬" + (cons.getLiqLibrasRecibidas() == null ? "B" : "D" + cons.getLiqLibrasRecibidas())
                        + "¬" + (cons.getLiqDiferencia() == null ? "B" : "D" + cons.getLiqDiferencia())
                        + "¬" + (cons.getLiqLibrasBasura() == null ? "B" : "D" + cons.getLiqLibrasBasura())
                        + "¬" + (cons.getLiqLibrasNetas() == null ? "B" : "D" + cons.getLiqLibrasNetas())
                        + "¬" + (cons.getLiqLibrasEntero() == null ? "B" : "D" + cons.getLiqLibrasEntero())
                        + "¬" + (cons.getLiqRendimiento() == null ? "B" : "D" + cons.getLiqRendimiento())
                        + "¬" + (cons.getLiq20_30() == null ? "B" : "D" + cons.getLiq20_30())
                        + "¬" + (cons.getLiq30_40() == null ? "B" : "D" + cons.getLiq30_40())
                        + "¬" + (cons.getLiq40_50() == null ? "B" : "D" + cons.getLiq40_50())
                        + "¬" + (cons.getLiq50_60() == null ? "B" : "D" + cons.getLiq50_60())
                        + "¬" + (cons.getLiq60_70() == null ? "B" : "D" + cons.getLiq60_70())
                        + "¬" + (cons.getLiqOtrasTallas() == null ? "B" : "D" + cons.getLiqOtrasTallas())
                        + "¬" + (cons.getLiqPrecioLibraPromedio() == null ? "B" : "D" + cons.getLiqPrecioLibraPromedio())
                        + "¬" + (cons.getLiqTotalMonto() == null ? "B" : "D" + cons.getLiqTotalMonto()));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    //Exportar PreLiquidaciones Detalle
    @Override
    public Map<String, Object> exportarReportePreLiquidacionesDetalle(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception {
        try {
            List<PrdPreLiquidacionesDetalleTO> listado = UtilsJSON.jsonToList(PrdPreLiquidacionesDetalleTO.class, map.get("listado"));
            String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
            String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));

            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Preliquidaciones detalle");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SNúmero" + "¬" + "SFecha" + "¬" + "SCliente" + "¬" + "SCorrida" + "¬" + "SLote" + "¬" + "SMes" + "¬"
                    + "SLib. Enviadas" + "¬" + "SLib. Recibidas" + "¬" + "SDiferencia" + "¬" + "SLib. Basura" + "¬" + "SLib. Netas" + "¬"
                    + "SLib. Entero" + "¬" + "SRendimiento" + "¬" + "S20_20" + "¬" + "S30_40" + "¬" + "S40_50" + "¬"
                    + "S50_60" + "¬" + "S60_70" + "¬" + "SOtras tallas" + "¬" + "SPrecio libra promedio" + "¬" + "SMonto total");
            listado.forEach((cons) -> {
                listaCuerpo.add((cons.getPlcNumero() == null ? "B" : "S" + cons.getPlcNumero())
                        + "¬" + (cons.getPlcFecha() == null ? "B" : "S" + cons.getPlcFecha())
                        + "¬" + (cons.getPlcCliente() == null ? "B" : "S" + cons.getPlcCliente())
                        + "¬" + (cons.getPlcCorrida() == null ? "B" : "S" + cons.getPlcCorrida())
                        + "¬" + (cons.getPlcLote() == null ? "B" : "S" + cons.getPlcLote())
                        + "¬" + (cons.getPlcMes() == null ? "B" : "S" + cons.getPlcMes())
                        + "¬" + (cons.getPlcLibrasEnviadas() == null ? "B" : "D" + cons.getPlcLibrasEnviadas())
                        + "¬" + (cons.getPlcLibrasRecibidas() == null ? "B" : "D" + cons.getPlcLibrasRecibidas())
                        + "¬" + (cons.getPlcDiferencia() == null ? "B" : "D" + cons.getPlcDiferencia())
                        + "¬" + (cons.getPlcLibrasBasura() == null ? "B" : "D" + cons.getPlcLibrasBasura())
                        + "¬" + (cons.getPlcLibrasNetas() == null ? "B" : "D" + cons.getPlcLibrasNetas())
                        + "¬" + (cons.getPlcLibrasEntero() == null ? "B" : "D" + cons.getPlcLibrasEntero())
                        + "¬" + (cons.getPlcRendimiento() == null ? "B" : "D" + cons.getPlcRendimiento())
                        + "¬" + (cons.getPlc20_30() == null ? "B" : "D" + cons.getPlc20_30())
                        + "¬" + (cons.getPlc30_40() == null ? "B" : "D" + cons.getPlc30_40())
                        + "¬" + (cons.getPlc40_50() == null ? "B" : "D" + cons.getPlc40_50())
                        + "¬" + (cons.getPlc50_60() == null ? "B" : "D" + cons.getPlc50_60())
                        + "¬" + (cons.getPlc60_70() == null ? "B" : "D" + cons.getPlc60_70())
                        + "¬" + (cons.getPlcOtrasTallas() == null ? "B" : "D" + cons.getPlcOtrasTallas())
                        + "¬" + (cons.getPlcPrecioLibraPromedio() == null ? "B" : "D" + cons.getPlcPrecioLibraPromedio())
                        + "¬" + (cons.getPlcTotalMonto() == null ? "B" : "D" + cons.getPlcTotalMonto()));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    //exportarExcel PreLiquidacion Clientes
    @Override
    public Map<String, Object> exportarReportePreLiquidacionConsolidandoClientes(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception {
        try {
            List<PrdPreLiquidacionConsolidandoClientesTO> listado = UtilsJSON.jsonToList(PrdPreLiquidacionConsolidandoClientesTO.class, map.get("listado"));
            String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
            String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
            String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
            String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));

            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Preliquidación detalle Clientes");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("SCliente: " + cliente);
            listaCabecera.add("TSector: " + sector);
            listaCabecera.add("S");
            listaCuerpo.add("SIdentificación" + "¬" + "SCliente" + "¬" + "SLibras Enviadas" + "¬" + "SLibras Recibidas" + "¬" + "SLibras Basura" + "¬" + "SLibras Netas" + "¬" + "SLibras Entero" + "¬" + "SLibras Cola" + "¬" + "SLibras Cola Procesada" + "¬" + "SMonto Total");
            listado.forEach((cons) -> {
                listaCuerpo.add((cons.getPlcIdentificacion() == null ? "B" : "S" + cons.getPlcIdentificacion())
                        + "¬" + (cons.getPlcNombreCliente() == null ? "B" : "S" + cons.getPlcNombreCliente())
                        + "¬" + (cons.getPlcLibrasEnviadas() == null ? "B" : "D" + cons.getPlcLibrasEnviadas())
                        + "¬" + (cons.getPlcLibrasRecibidas() == null ? "B" : "D" + cons.getPlcLibrasRecibidas())
                        + "¬" + (cons.getPlcLibrasBasura() == null ? "B" : "D" + cons.getPlcLibrasBasura())
                        + "¬" + (cons.getPlcLibrasNetas() == null ? "B" : "D" + cons.getPlcLibrasNetas())
                        + "¬" + (cons.getPlcLibrasEntero() == null ? "B" : "D" + cons.getPlcLibrasEntero())
                        + "¬" + (cons.getPlcLibrasCola() == null ? "B" : "D" + cons.getPlcLibrasCola())
                        + "¬" + (cons.getPlcLibrasColaProcesadas() == null ? "B" : "D" + cons.getPlcLibrasColaProcesadas())
                        + "¬" + (cons.getPlcTotalMonto() == null ? "B" : "D" + cons.getPlcTotalMonto()));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteCorridasInconsistentes(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdCorridasInconsistentesTO> listado,
            String fechaDesde, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Corridas con inconsistencias");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SSector Origen" + "¬" + "SPiscina Origen" + "¬" + "SCorrida Origen" + "¬" + "SCierre Origen" + "¬" + "SSector Destino" + "¬" + "SPiscina Destino" + "¬"
                    + "SCorrida Destino" + "¬" + "SSiembra Destino" + "¬" + "SPorcentaje" + "¬" + "SObservaciones");
            listado.forEach((cons) -> {
                listaCuerpo.add((cons.getErrorOrigenSector() == null ? "B" : "S" + cons.getErrorOrigenSector())
                        + "¬" + (cons.getErrorOrigenPiscina() == null ? "B" : "S" + cons.getErrorOrigenPiscina())
                        + "¬" + (cons.getErrorOrigenCorrida() == null ? "B" : "S" + cons.getErrorOrigenCorrida())
                        + "¬" + (cons.getErrorOrigenCierre() == null ? "B" : "S" + cons.getErrorOrigenCierre())
                        + "¬" + (cons.getErrorDestinoSector() == null ? "B" : "S" + cons.getErrorDestinoSector())
                        + "¬" + (cons.getErrorDestinoPiscina() == null ? "B" : "S" + cons.getErrorDestinoPiscina())
                        + "¬" + (cons.getErrorDestinoCorrida() == null ? "B" : "S" + cons.getErrorDestinoCorrida())
                        + "¬" + (cons.getErrorDestinoSiembra() == null ? "B" : "S" + cons.getErrorDestinoSiembra())
                        + "¬" + (cons.getErrorPorcentaje() == null ? "B" : "S" + cons.getErrorPorcentaje())
                        + "¬" + (cons.getErrorObservaciones() == null ? "B" : "S" + cons.getErrorObservaciones()));
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteCorridasInconsistentes(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdCorridasInconsistentesTO> listado,
            String desde, String hasta) throws Exception {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("desde", desde);
        parametros.put("hasta", hasta);
        return genericReporteService.generarReporte(modulo, "reportCorridasInconsistentes.jrxml", usuarioEmpresaReporteTO, parametros, listado);
    }

    @Override
    public Map<String, Object> exportarReporteLiquidacionConsolidandoTallasProveedores(List<PrdFunLiquidacionConsolidandoTallasProveedorTO> listado, String fechaDesde, String fechaHasta, String sector, String proveedor, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de Consolidando Tallas Proveedores");
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("SProveedor: " + proveedor);
            listaCabecera.add("TSector: " + sector);
            listaCabecera.add("S");
            listaCuerpo.add("STalla" + "¬" + "SComisionista" + "¬" + "SCodigo" + "¬" + "SMedida" + "¬" + "SLibras" + "¬" + "SPorcentaje" + "¬" + "STotal");
            listado.forEach((cons) -> {
                listaCuerpo.add(
                        (cons.getLctTalla() == null ? "B" : "S" + cons.getLctTalla())
                        + "¬" + (cons.getLctComisionista() == null ? "B" : "S" + cons.getLctComisionista())
                        + "¬" + (cons.getLctCodigo() == null ? "B" : "S" + cons.getLctCodigo())
                        + "¬" + (cons.getLctMedida() == null ? "B" : "S" + cons.getLctMedida())
                        + "¬" + (cons.getLctLibras() == null ? "B" : "D" + cons.getLctLibras())
                        + "¬" + (cons.getLctPorcentaje() == null ? "B" : "D" + cons.getLctPorcentaje())
                        + "¬" + (cons.getLctTotal() == null ? "B" : "D" + cons.getLctTotal())
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteConsumoDetalladoProductos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<PrdConsumosDetalladoProductosTO> listado) throws Exception {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("desde", fechaDesde);
        parametros.put("hasta", fechaHasta);
        return genericReporteService.generarReporte(modulo, "reportConsumoDetalladoProductos.jrxml", usuarioEmpresaReporteTO, parametros, listado);
    }

}
