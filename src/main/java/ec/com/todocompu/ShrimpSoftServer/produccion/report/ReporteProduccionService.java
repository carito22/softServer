package ec.com.todocompu.ShrimpSoftServer.produccion.report;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.ListaLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.ListaPreLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdConsumosDetalladoProductosTO;
import java.util.List;

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
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionesDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdProyeccionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenPescaSiembraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdUtilidadDiariaCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdProducto;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdTalla;
import ec.com.todocompu.ShrimpSoftUtils.produccion.report.ReporteLiquidacionPesca;
import ec.com.todocompu.ShrimpSoftUtils.produccion.report.ReportePrdFunCostosPorFechaSimpleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.report.ReporteResumenFinanciero;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.util.Map;

public interface ReporteProduccionService {

    public byte[] generarReporteListadoGramaje(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String codigoSector,
            String codigoPiscina, String codigoCorrida, String fechaDesde, String fechaHasta,
            List<PrdListadoGrameajeTO> prdListadoGrameajeTO) throws Exception;

    public byte[] generarReportePrdResumenCorrida(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<PrdResumenCorridaTO> prdResumenCorrida, String tituloReporte) throws Exception;

    public byte[] generarReporteCostoDetalladoCorrida(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteCostoDetalladoCorrida> reporteCostoDetalladoCorrida, String tituloReporte) throws Exception;

    public byte[] generarReporteListadoFunAnalisiPeso(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteListadoFunAnalisisPeso> reporteListadoFunAnalisisPesos, String tituloReporte) throws Exception;

    public byte[] generarReporteListadoGrameaje(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteListadoGrameaje> reporteListadoGrameaje, String tituloReporte) throws Exception;

    public byte[] generarReporteCostoDetalleFecha(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteCostoDetalleFecha> reporteCostoDetalleFecha, String tituloReporte) throws Exception;

    public byte[] generarReportePrdFunCostosPorFechaSimpleTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReportePrdFunCostosPorFechaSimpleTO> reportePrdFunCostosPorFechaSimpleTOs, String tituloReporte)
            throws Exception;

    public byte[] generarReporteResumenFinanciero(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteResumenFinanciero> reporteResumenFinanciero, String tituloReporte) throws Exception;

    public byte[] generarReporteConsumoPorPiscina(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteConsumoPorPiscina> reporteConsumoPorPiscina, String tituloReporte) throws Exception;

    public byte[] generarReporteConsumoPorFecha(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteConsumoPorFecha> reporteConsumoPorFecha, String tituloReporte) throws Exception;

    public byte[] generarReportePrdResumenPesca(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteResumenPesca> reporteResumenPescas, String tituloReporte) throws Exception;

    public byte[] generarReportePrdResumenSiembra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteResumenSiembra> reporteResumenSiembra, String tituloReporte) throws Exception;

    public byte[] generarReporteLiquidacionPesca(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteLiquidacionPesca> reporteLiquidacionPescas) throws Exception;

    public byte[] generarReporteListadoFunAnalisisPesos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String codigoSector, String fechaHasta, List<PrdListaFunAnalisisPesosTO> prdListaFunAnalisisPesosTO)
            throws Exception;

    public byte[] generarReporteConsumoFecha(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String secCodigo,
            String fechaDesde, String fechaHasta, List<PrdConsumosTO> prdConsumosTO) throws Exception;

    public byte[] generarReporteUtilidadDiariaCorrida(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector, String piscina, String corrida,
            List<PrdUtilidadDiariaCorridaTO> listUtilidad) throws Exception;

    public byte[] generarReporteConsumoPiscina(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String codigoSector,
            String codigoPiscina, String numeroCorrida, String hectareas, Integer numLarvas, String fechaDesde,
            String fechaHasta, List<PrdConsumosTO> prdConsumosTO) throws Exception;

    public byte[] generarReporteEconomicoPorFechas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, List<PrdCostoDetalleFechaTO> listaPrdCostoDetalleFechaTO) throws Exception;

    public byte[] generarReporteEconomicoPorPiscinas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String codigoSector, String codigoPiscina, String codigoCorrida, String hectareas, Integer numLarvas,
            String fechaDesde, String fechaHasta, List<PrdListaCostosDetalleCorridaTO> prdListaCostosDetalleCorridaTO)
            throws Exception;

    public byte[] generarReporteResumenSiembra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<PrdResumenCorridaTO> listaPrdListaResumenCorridaTO) throws Exception;

    public byte[] generarReporteResumenPesca(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<PrdResumenCorridaTO> listaPrdListaResumenCorridaTO) throws Exception;

    public byte[] generarReporteResumenEconomicoSiembra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdResumenPescaSiembraTO> datos)
            throws Exception;

    public byte[] generarReporteResumenEconomicoPesca(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdResumenPescaSiembraTO> datos)
            throws Exception;

    public byte[] generarReportePreLiquidacionMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdPreLiquidacionMotivo> prdPreLiquidacionMotivo) throws Exception;

    public byte[] generarReporteLiquidacionMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdLiquidacionMotivo> prdLiquidacionMotivo) throws Exception;

    public byte[] generarReportePrdLiquidacionProductoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdProducto> datos) throws Exception;

    public byte[] generarReportePresupuestoPesca(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdPresupuestoPescaMotivo> prdPresupuestoPescaMotivo) throws Exception;

    public byte[] generarReportePrdLiquidacionTallaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdTalla> datos) throws Exception;

    public byte[] generarReporteSobrevivencia(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdListaSobrevivenciaTO> listado) throws Exception;

    public byte[] generarReportePrdCorrida(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<PrdCorrida> listado, String sector, String piscina) throws Exception;

    public byte[] generarReportePescaLiquidacionPorLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String empresa, String nombreReporte, List<ListaLiquidacionTO> listado) throws Exception;

    public byte[] generarReporteLiquidacionPescaListado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String empresa, String nombreReporte, List<ListaLiquidacionTO> listado) throws Exception;

    public byte[] generarReportePreLiquidacionPescaListado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String empresa, String nombreReporte, List<ListaPreLiquidacionTO> listado) throws Exception;

    public byte[] generarReportePescaPreLiquidacionPorLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String empresa, String nombreReporte, List<ListaPreLiquidacionTO> listado) throws Exception;

    public byte[] generarReportePrdProyeccion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<PrdProyeccionTO> listado) throws Exception;

    public byte[] generarReportePicina(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdListaPiscinaTO> listado) throws Exception;

    public byte[] generarReporteSector(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdListaSectorTO> listado) throws Exception;

    //rerporteProducto
    public byte[] generarReporteLiquidacionDetalleProducto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdLiquidacionDetalleProductoTO> listado) throws Exception;

    public byte[] generarReportePreLiquidacionDetalleProducto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdPreLiquidacionDetalleProductoTO> listado) throws Exception;

    public byte[] generarReporteLiquidacionDetalleProductoEmpacadora(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdLiquidacionDetalleProductoEmpacadoraTO> listado) throws Exception;

    //repoporTallas
    public byte[] generarReporteLiquidacionConsolidandoTallas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdFunLiquidacionConsolidandoTallasTO> listado) throws Exception;

    public byte[] generarReportePreLiquidacionConsolidandoTallas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdFunPreLiquidacionConsolidandoTallasTO> listado) throws Exception;

    public byte[] generarReporteLiquidacionConsolidandoTallasProveedor(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdFunLiquidacionConsolidandoTallasProveedorTO> listado) throws Exception;

//repoporteClientes
    public byte[] generarReporteLiquidacionConsolidandoClientes(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdLiquidacionConsolidandoClientesTO> listado) throws Exception;

    public byte[] generarReportePreLiquidacionConsolidandoClientes(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdPreLiquidacionConsolidandoClientesTO> listado) throws Exception;

    //repoporteProveedores
    public byte[] generarReporteLiquidacionConsolidandoProveedores(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdLiquidacionConsolidandoProveedoresTO> listado, String sector, String proveedor, String fechaDesde, String fechaHasta, String comisionista) throws Exception;

    //repoporteConsultas
    public byte[] generarReporteLiquidacionConsultas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdLiquidacionConsultaTO> listado) throws Exception;

    //repoporteConsultasEmpacadora
    public byte[] generarReporteLiquidacionConsultasEmpacadora(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdLiquidacionConsultaEmpacadoraTO> listado, String proveedor) throws Exception;

    public byte[] generarReportePreLiquidacionConsultas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdPreLiquidacionConsultaTO> listado) throws Exception;

    public byte[] generarReportePrdListadoFitoplanctonTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdListadoFitoplanctonTO> listado) throws Exception;

    //reporte Preliquidación Detalle
    public byte[] generarReportePreLiquidacionesDetalle(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdPreLiquidacionesDetalleTO> listado) throws Exception;

    public Map<String, Object> exportarReportePiscinas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdListaPiscinaTO> listado, boolean centroCosto) throws Exception;

    public Map<String, Object> exportarReporteSectores(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdListaSectorTO> listado, boolean centroProduccion) throws Exception;

    public Map<String, Object> exportarReporteDinamico(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String titulo, List<String> columnas, List<String[]> datos, String nombreSector, String piscina, String corrida) throws Exception;

    //consumos mensuales
    public Map<String, Object> exportarReporteDinamicoConsumosMensuales(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String titulo, List<String> columnas, List<String[]> datos, String nombreSector, String bodega, String desde, String hasta) throws Exception;

    public Map<String, Object> exportarReporteConsumosDiarios(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String titulo, List<String> columnas, List<Object> datos, String nombreSector, String boodega, String desde, String hasta) throws Exception;

    public Map<String, Object> exportarReporteDinamicoConsumoPiscinaPeriodo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String codigoSector, String nombreSector, String numeroPiscina, String codigoCorrida,
            String periodo, String fechaInicio, String fechaFin, String numeroLarvas, String numeroHectareas, String titulo, List<String> columnas, List<String[]> datos) throws Exception;

    public Map<String, Object> exportarReporteConsumosFecha(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String secCodigo,
            String fechaDesde, String fechaHasta, List<PrdConsumosTO> listado) throws Exception;

    public Map<String, Object> exportarReporteConsumosPiscina(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String codigoSector, String nombreSector, String piscina,
            String codigoPiscina, String numeroCorrida, String hectareas, Integer numLarvas, String fechaDesde,
            String fechaHasta, List<PrdConsumosTO> prdConsumosTO, boolean ordenProduccion) throws Exception;

    public Map<String, Object> exportarReporteDinamicoCostosProductosProcesos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector, String hasta, List<String> columnas, List<String[]> datos) throws Exception;

    public Map<String, Object> exportarReporteListadoFunAnalisisPesos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String codigoSector, String nomSector, List<PrdListaFunAnalisisPesosTO> prdListaFunAnalisisPesosTO, String fechaHasta) throws Exception;

    public Map<String, Object> exportarReporteConsumosFechaDesglosado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector, String desde, String hasta, List<String> columnas, List<String[]> datos) throws Exception;

    public Map<String, Object> exportarReporteConsumosProductosProcesos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector, String hasta, List<String> columnas, List<String[]> datos) throws Exception;

    public Map<String, Object> exportarReporteGramaje(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String codigoSector,
            String codigoPiscina, String codigoCorrida, List<PrdListadoGrameajeTO> prdListadoGrameajeTO,
            String fechaDesde, String fechaHasta, boolean esSamaniego) throws Exception;

    public Map<String, Object> exportarReporteEconomicoPorPiscinas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String codigoSector, String nombreSector, String codigoPiscina, String codigoCorrida,
            String hectareas, Integer numLarvas, String fechaDesde, String fechaHasta, List<PrdListaCostosDetalleCorridaTO> prdListaCostosDetalleCorridaTO, boolean costoPrudccion) throws Exception;

    public Map<String, Object> exportarReporteGrameajePromedioPorPiscina(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Object[][] datos, List<String> columnas, String sector) throws Exception;

    public Map<String, Object> exportarReporteResumenSiembra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String nombreSector, String sector, List<PrdResumenCorridaTO> lista, boolean esAcuagold) throws Exception;

    public Map<String, Object> exportarReporteResumenPesca(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String nombreSector, String sector, List<PrdResumenCorridaTO> lista, boolean incluirTransferencia) throws Exception;

    public Map<String, Object> exportarReporteUtilidadDiariaCorrida(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector, String piscina, String corrida, List<PrdUtilidadDiariaCorridaTO> listUtilidad, List<PrdUtilidadDiariaCorridaTO> listaResumenBiologico, List<PrdUtilidadDiariaCorridaTO> listaResumenFinanciero, List<PrdUtilidadDiariaCorridaTO> listaConsumoBalanceado) throws Exception;

    public Map<String, Object> exportarReportePrdFunCostosPorFechaSimpleTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdFunCostosPorFechaSimpleTO> reportePrdFunCostosPorFechaSimpleTOs, String fechaDesde, String fechaHasta, String sector, String nombreSector) throws Exception;

    public Map<String, Object> exportarReporteCostoDetalleFecha(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdCostoDetalleFechaTO> prdCostoDetalleFechaTO, String fechaDesde, String fechaHasta, String nombreSector, String sector) throws Exception;

    public Map<String, Object> exportarReporteCostosMensuales(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<String> columnas, List<String[]> datos, String sector, String nombreSector, String bodega, String desde, String hasta) throws Exception;

    public Map<String, Object> exportarReporteCostoPiscinaSemanal(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreSector, String sector, String corrida, String numeroPiscina, String desde, String hasta, String hectareas, Integer numLarvas, List<String> columnas, List<String[]> datos) throws Exception;

    public Map<String, Object> exportarReportePorFechaProrrateado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector, String nombreSector, String desde, String hasta, List<String> columnas, List<String[]> datos) throws Exception;

    public Map<String, Object> exportarReporteResumenEconomicoSiembra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String nombreSector, String sector, List<PrdResumenPescaSiembraTO> listado) throws Exception;

    public Map<String, Object> exportarReporteLiquidacionMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdLiquidacionMotivo> prdLiquidacionMotivo) throws Exception;

    public Map<String, Object> exportarReporteResumenPescaEconomico(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String nombreSector, String sector, List<PrdResumenPescaSiembraTO> lista, boolean incluirTransferencia) throws Exception;

    public Map<String, Object> exportarReportePrdLiquidacionProductoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdProducto> datos) throws Exception;

    public Map<String, Object> exportarReportePresupuestoPesca(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdPresupuestoPescaMotivo> prdPresupuestoPescaMotivo) throws Exception;

    public Map<String, Object> exportarReportePreLiquidacionMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdPreLiquidacionMotivo> prdPreLiquidacionMotivo) throws Exception;

    public Map<String, Object> exportarReportePescaLiquidacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ListaLiquidacionTO> listado, String sector, String piscina) throws Exception;

    public Map<String, Object> exportarReportePrdLiquidacionTallaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdTalla> datos) throws Exception;

    public Map<String, Object> exportarReporteSobrevivencia(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdListaSobrevivenciaTO> datos) throws Exception;

    public Map<String, Object> exportarPrdCorrida(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdCorrida> listado, String sector, String piscina, boolean ordenProduccion) throws Exception;

    public Map<String, Object> exportarReportePescaPreLiquidacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ListaPreLiquidacionTO> listado) throws Exception;

    public Map<String, Object> exportarReportePrdProyeccion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdProyeccionTO> listado) throws Exception;

    public Map<String, Object> exportarReporteConsumos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvListaConsultaConsumosTO> listado) throws Exception;

    //exportarExcel
    public Map<String, Object> exportarReporteConsolidandoTallas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception;

    //export consolidando productos
    public Map<String, Object> exportarReporteLiquidacionDetalleProductos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception;

    //export consulta productos empacadora(Proveedores)
    public Map<String, Object> exportarReporteLiquidacionDetalleProductosEmpacadora(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception;

    //exportarExcelClientes
    public Map<String, Object> exportarReporteLiquidacionConsolidandoClientes(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception;

    //exportarExcelProveedores
    public Map<String, Object> exportarReporteLiquidacionConsolidandoProveedores(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception;

    //exportarExcelConsultas
    public Map<String, Object> exportarReporteLiquidacionConsultas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception;

    //exportarExcelConsultasEmpacadora
    public Map<String, Object> exportarReporteLiquidacionConsultasEmpacadora(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception;

    public byte[] generarReporteLiquidacionesDetalle(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdLiquidacionesDetalleTO> listado) throws Exception;

    public Map<String, Object> exportarReporteLiquidacionesDetalle(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception;

    public Map<String, Object> exportarReportePrdListadoFitoplancton(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception;

    //Exportar preliquidacion
    public Map<String, Object> exportarReportePreLiquidacionDetalleProductos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception;

    //Exportar preLiquidacion Consolidando tallas
    public Map<String, Object> exportarReportePreConsolidandoTallas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception;

    //Exportar preLiquidacion Consolidando Clientes
    public Map<String, Object> exportarReportePreLiquidacionConsolidandoClientes(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception;

    //Exportar preLiquidacion Consultas
    public Map<String, Object> exportarReportePreLiquidacionConsultas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception;

    //Exportar PreLiquidaciones Detalle
    public Map<String, Object> exportarReportePreLiquidacionesDetalle(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception;

    public Map<String, Object> exportarReporteCorridasInconsistentes(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdCorridasInconsistentesTO> listado, String fechaDesde, String fechaHasta) throws Exception;

    public byte[] generarReporteCorridasInconsistentes(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdCorridasInconsistentesTO> listado, String fechaDesde, String fechaHasta) throws Exception;

    public Map<String, Object> exportarReporteLiquidacionConsolidandoTallasProveedores(List<PrdFunLiquidacionConsolidandoTallasProveedorTO> listado, String fechaDesde, String fechaHasta, String sector, String proveedor, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception;

    public byte[] generarReporteConsumoDetalladoProductos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<PrdConsumosDetalladoProductosTO> listado) throws Exception;

}
