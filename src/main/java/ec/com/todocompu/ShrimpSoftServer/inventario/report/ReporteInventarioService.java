package ec.com.todocompu.ShrimpSoftServer.inventario.report;

import ec.com.todocompu.ShrimpSoftUtils.DetalleExportarFiltrado;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteContratoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteContratosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosTO;
import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunComprasConsolidandoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunConsumosConsolidandoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListadoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListadoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunMovimientosSerieTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasConsolidandoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasConsolidandoProductosCoberturaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasConsolidandoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasVendedorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasVsCostoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvKardexTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaBodegasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaProformaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaTransferenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvNumeracionCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvNumeracionConsumoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvNumeracionVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoMarcaComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoMedidaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionCajasComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionUnidadesComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoTipoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductosConErrorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVendedorComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasFormaCobroTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.SaldoBodegaComprobacionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.SaldoBodegaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemision;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.DatoFunListaProductosImpresionPlaca;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.InvListaConsultaCompra;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.InvListaConsultaConsumo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.InvListaConsultaTransferencia;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.InvListaConsultaVenta;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.ReporteCompraDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.ReporteProformaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.ReporteVentaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContrato;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.io.File;
import java.util.Map;

public interface ReporteInventarioService {

    public byte[] generarReporteInvKardex(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreProducto, String fechaDesde, String fechaHasta, List<InvKardexTO> listInvKardexTO, boolean banderaCosto) throws Exception;

    public byte[] generarReporteImpresionPlaca(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<DatoFunListaProductosImpresionPlaca> listProductosImpresionPlaca) throws Exception;

    public byte[] generarReporteListadoCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String motivo, String proveedorId, String documento, List<InvFunComprasTO> listInvFunComprasTO) throws Exception;

    public byte[] generarReporteConsolidadoConsumoProducto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String bodega, String proveedor, String motivo, List<InvFunConsumosConsolidandoProductosTO> listInvFunConsumosConsolidandoProductosTO) throws Exception;

    public byte[] generarReporteConsolidadoCompraProducto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String bodega, String proveedor, List<InvFunComprasConsolidandoProductosTO> listInvFunConsumosConsolidandoProductosTO) throws Exception;

    public byte[] generarReporteListadoVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String motivo, String cliente, String documento, List<InvFunVentasTO> listInvFunVentasTO) throws Exception;

    public byte[] generarReporteConsolidadoVentaProducto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String bodega, String cliente, List<InvFunVentasConsolidandoProductosTO> listInvFunVentasConsolidandoProductosTO) throws Exception;

    public byte[] generarReporteConsolidadoVentaProductoCobertura(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String sector, String bodega, String motivo, String cliente, List<InvFunVentasConsolidandoProductosCoberturaTO> listado, String nombreReporte) throws Exception;

    public byte[] generarReporteConsolidadoVentaCliente(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String sector, List<InvFunVentasConsolidandoClientesTO> listInvFunVentasConsolidandoClientesTO) throws Exception;

    public byte[] generarReporteListadoConsumos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<InvFunConsumosTO> listInvFunConsumosTO) throws Exception;

    public byte[] generarReporteListaProductos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String bodega, List<InvListaProductosTO> listInvListaProductosTO) throws Exception;

    public byte[] generarReporteSaldoBodega(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String bodega, String fechaHasta, List<SaldoBodegaTO> listSaldoBodegaTO) throws Exception;

    public byte[] generarReporteSaldoBodegaComprobacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String bodega, String fechaDesde, String fechaHasta, List<SaldoBodegaComprobacionTO> lista) throws Exception;

    public byte[] generarReporteSaldoBodegaComprobacionCantidades(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String bodega, String fechaDesde, String fechaHasta, List<SaldoBodegaComprobacionTO> lista) throws Exception;

    public byte[] generarReporteInvProductosConError(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductosConErrorTO> listInvProductosConErrorTO) throws Exception;

    public File generarReporteInvProductosConErrorFile(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductosConErrorTO> listInvProductosConErrorTO) throws Exception;

    public byte[] generarReporteTrasferencias(String empresa, String periodo, String motivo, String numero, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception;

    public byte[] generarReporteTrasferenciasListado(String empresa, List<InvListaConsultaTransferenciaTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception;

    public byte[] generarReportePorLoteTrasferencias(String empresa, List<String> numeroTransferencia, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception;

    public byte[] generarReporteConsumoDetalle(List<InvConsumosTO> invConsumosTOs, boolean ordenado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception;

    public byte[] generarReporteCompraDetalle(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCompraDetalle> reporteCompraDetalles) throws Exception;

    public byte[] generarReporteCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvComprasPK> listaPk, boolean isIMB, String secNombre, String nombreReporte, String validarImpresionIMB) throws Exception;

    public byte[] generarReporteVentaDetalleImpresion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteVentaDetalle> lista, String nombreCliente, String nombreReporte) throws Exception;

    public byte[] generarReporteProformaDetalleImpresion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteProformaDetalle> lista, String nombreReporte) throws Exception;

    public byte[] generarReporteInvFunVentasVsCosto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String bodega, String clienteId, List<InvFunVentasVsCostoTO> invFunVentasVsCostoTO) throws Exception;

    public byte[] generarReporteBodega(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvListaBodegasTO> listado) throws Exception;

    public byte[] generarReporteCategoriaCliente(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvClienteCategoriaTO> listado) throws Exception;

    public byte[] generarReporteCategoriaProveedor(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProveedorCategoriaTO> listado) throws Exception;

    public byte[] generarReporteFormaPago(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvComprasFormaPagoTO> listado) throws Exception;

    public byte[] generarReporteFormaCobro(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvVentasFormaCobroTO> listado) throws Exception;

    public byte[] generarReporteMotivoCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvComprasMotivoTO> listado) throws Exception;

    public byte[] generarReporteMotivoVenta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvVentaMotivoTO> listado) throws Exception;

    public byte[] generarReporteMotivoConsumo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvConsumosMotivoTO> listado) throws Exception;

    public byte[] generarReporteMotivoProforma(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProformaMotivoTO> listado) throws Exception;

    public byte[] generarReporteMotivoTransferencia(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvTransferenciaMotivoTO> listado) throws Exception;

    public byte[] generarReporteNumeracionCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvNumeracionCompraTO> listado) throws Exception;

    public byte[] generarReporteNumeracionVenta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvNumeracionVentaTO> listado) throws Exception;

    public byte[] generarReporteNumeracionConsumo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvNumeracionConsumoTO> listado) throws Exception;

    public byte[] generarReporteCompraDetalleImprimir(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCompraDetalle> reporteCompraDetalles, String cmFormaImprimir) throws Exception;

    public byte[] generarReporteInvProductoMedidaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoMedidaTO> listaInvProductoMedidaTO, String nombreReporte) throws Exception;

    public byte[] generarReporteCliente(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvFunListadoClientesTO> listado) throws Exception;

    public byte[] generarReporteInvProductoCategoriaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoCategoriaTO> listaInvProductoCategoriaTO, String nombreReporte) throws Exception;

    public byte[] generarReporteProductos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvListaProductosGeneralTO> listado, List<InvFunListadoProductosTO> listado2) throws Exception;

    public byte[] generarReporteProductoMarca(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoMarcaComboListadoTO> listado) throws Exception;

    public byte[] generarReportePresentacionUnidad(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoPresentacionUnidadesComboListadoTO> listado) throws Exception;

    public byte[] generarReportePresentacionCaja(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoPresentacionCajasComboListadoTO> listado) throws Exception;

    public byte[] generarReporteProductoTipo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoTipoComboTO> listado) throws Exception;

    public byte[] generarReporteCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvListaConsultaCompraTO> listado) throws Exception;

    public byte[] generarReporteProforma(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<InvProformasPK> listadoPk) throws Exception;

    public byte[] generarReporteProformaListado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<InvListaConsultaProformaTO> listado) throws Exception;

    public byte[] generarReporteVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<InvListaConsultaVentaTO> listInvListaConsultaVentaTO) throws Exception;

    public byte[] generarReporteVentasDetalleProducto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<InvVentasDetalleProductoTO> listaVentasDetalleProducto) throws Exception;

    public byte[] generarReporteComprasDetalleProducto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<InvComprasDetalleProductoTO> listaComprasDetalleProducto) throws Exception;

    public byte[] generarReporteVendedor(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvVendedorComboListadoTO> listado) throws Exception;

    public byte[] generarReporteInvGuiaRemision(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvGuiaRemision> listado, String nombreReporte) throws Exception;

    /*exportar*/
    public Map<String, Object> exportarReporteInvKardex(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreProducto, String fechaDesde, String fechaHasta, List<InvKardexTO> listInvKardexTO, boolean banderaCosto) throws Exception;

    public Map<String, Object> exportarReporteBodegas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvListaBodegasTO> listado) throws Exception;

    public Map<String, Object> exportarReporteCategoriaCliente(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvClienteCategoriaTO> listado) throws Exception;

    public Map<String, Object> exportarReporteCategoriaProveedor(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProveedorCategoriaTO> listado) throws Exception;

    public Map<String, Object> exportarReporteFormaPago(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvComprasFormaPagoTO> listado) throws Exception;

    public Map<String, Object> exportarReporteFormaCobro(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvVentasFormaCobroTO> listado) throws Exception;

    public Map<String, Object> exportarReporteMotivoCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvComprasMotivoTO> listado) throws Exception;

    public Map<String, Object> exportarReporteMotivoVenta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvVentaMotivoTO> listado) throws Exception;

    public Map<String, Object> exportarReporteMotivoConsumo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvConsumosMotivoTO> listado) throws Exception;

    public Map<String, Object> exportarReporteMotivoProforma(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProformaMotivoTO> listado) throws Exception;

    public Map<String, Object> exportarReporteMotivoTransferencia(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvTransferenciaMotivoTO> listado) throws Exception;

    public Map<String, Object> exportarReporteNumeracionCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvNumeracionCompraTO> listado) throws Exception;

    public Map<String, Object> exportarReporteNumeracionVenta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvNumeracionVentaTO> listado) throws Exception;

    public Map<String, Object> exportarReporteNumeracionConsumo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvNumeracionConsumoTO> listado) throws Exception;

    public Map<String, Object> exportarReporteSaldoBodega(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<SaldoBodegaTO> listado, String fechaHasta) throws Exception;

    public Map<String, Object> exportarReporteCliente(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvFunListadoClientesTO> listado, List<DetalleExportarFiltrado> listadoFiltrado) throws Exception;

    public Map<String, Object> exportarReporteProductoImprimirPlacas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<DatoFunListaProductosImpresionPlaca> listado) throws Exception;

    public Map<String, Object> exportarReporteInvProductoCategoriaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoCategoriaTO> listaInvProductoCategoriaTO) throws Exception;

    public Map<String, Object> exportarReporteInvProductoMedidaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoMedidaTO> listado) throws Exception;

    public Map<String, Object> exportarReporteProductos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvListaProductosGeneralTO> listado, List<InvFunListadoProductosTO> listado2, List<DetalleExportarFiltrado> listadoFiltrado) throws Exception;

    public Map<String, Object> exportarReporteInvFunListadoProductosTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvFunListadoProductosTO> listado2) throws Exception;

    public Map<String, Object> exportarReporteProductoMarca(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoMarcaComboListadoTO> listado) throws Exception;

    public Map<String, Object> exportarReportePresentacionUnidad(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoPresentacionUnidadesComboListadoTO> listado) throws Exception;

    public Map<String, Object> exportarReportePresentacionCaja(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoPresentacionCajasComboListadoTO> listado) throws Exception;

    public Map<String, Object> exportarReporteProductoTipo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoTipoComboTO> listado) throws Exception;

    public Map<String, Object> exportarReporteComprasConsolidandoProductos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvFunComprasConsolidandoProductosTO> listado) throws Exception;

    public Map<String, Object> exportarReporteVentasConsolidandoProductos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvFunVentasConsolidandoProductosTO> listado) throws Exception;

    public Map<String, Object> exportarReporteSaldoBodegaComprobacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String bodega, String fechaDesde, String fechaHasta, List<SaldoBodegaComprobacionTO> listado) throws Exception;

    public Map<String, Object> exportarReporteSaldoBodegaComprobacionCantidades(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String bodega, String fechaDesde, String fechaHasta, List<SaldoBodegaComprobacionTO> listado) throws Exception;

    public Map<String, Object> exportarReporteInvFunListaProductosSaldosGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Object[][] datos, List<String> columnas, List<String> columnasFaltantes) throws Exception;

    public Map<String, Object> exportarReporteComprasPorPeriodo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Object[][] datos, List<String> columnas, List<String> columnasFaltantes) throws Exception;

    public Map<String, Object> exportarReporteInvFunVentasVsCosto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvFunVentasVsCostoTO> listado, String fechaDesde, String fechaHasta, String cliente, String bodega) throws Exception;

    public Map<String, Object> exportarListadoProductosPreciosStock(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvListaProductosTO> listado) throws Exception;

    public Map<String, Object> exportarReconstruccionSaldosCostos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductosConErrorTO> listado) throws Exception;

    public Map<String, Object> exportarReporteInvFunVentasConsolidandoClientesTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector, String fechaDesde, String fechaHasta, List<InvFunVentasConsolidandoClientesTO> listado) throws Exception;

    public Map<String, Object> exportarReporteInvFunVentasConsolidandoProductosCoberturaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector, String fechaDesde, String fechaHasta, List<InvFunVentasConsolidandoProductosCoberturaTO> listado) throws Exception;

    public Map<String, Object> exportarReporteListadoCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvFunComprasTO> listado) throws Exception;

    public Map<String, Object> exportarReporteListadoConsumos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvFunConsumosTO> listado) throws Exception;

    public Map<String, Object> exportarReporteListadoVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String motivo, String cliente, String documento, List<InvFunVentasTO> listInvFunVentasTO) throws Exception;

    public Map<String, Object> exportarReporteCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvListaConsultaCompraTO> listado) throws Exception;

    public Map<String, Object> exportarReporteProformas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvListaConsultaProformaTO> listado) throws Exception;

    public Map<String, Object> exportarInvListaConsultaVenta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvListaConsultaVentaTO> listInvListaConsultaVentaTO) throws Exception;

    public Map<String, Object> exportarReporteInvListaVentasDetalleProducto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvVentasDetalleProductoTO> listaVentasDetalleProducto) throws Exception;

    public Map<String, Object> exportarReporteInvListaComprasDetalleProducto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaInicio, String fechafinal, String centroProduccion, String centroCosto, String bodega, List<InvComprasDetalleProductoTO> listaComprasDetalleProducto) throws Exception;

    public Map<String, Object> exportarReporteConsolidadoConsumoProducto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String bodega, String proveedor, String motivo, List<InvFunConsumosConsolidandoProductosTO> listInvFunConsumosConsolidandoProductosTO) throws Exception;

    public Map<String, Object> exportarMensajesConError(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RespuestaWebTO> listado) throws Exception;

    public Map<String, Object> exportarReporteListadoVentasVendedor(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String motivo, String cliente, String documento, List<InvFunVentasVendedorTO> listInvFunVentasTO) throws Exception;

    public Map<String, Object> exportarReporteVendedor(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvVendedorComboListadoTO> listado, List<DetalleExportarFiltrado> listadoFiltrado) throws Exception;

    public Map<String, Object> exportarMovimientosSeries(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String titulo, List<InvFunMovimientosSerieTO> datos) throws Exception;

    public Map<String, Object> exportarPlantillaConsumo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception;

    public Map<String, Object> exportarPlantillaCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception;

    public Map<String, Object> exportarPlantillaVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception;

    public Map<String, Object> exportarPlantillaProveedores(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception;

    public Map<String, Object> exportarReporteClienteContratos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvFunListadoClientesTO> listado) throws Exception;
    
    public Map<String, Object> exportarReporteContrato(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvClienteContratoTO> listadoContratos) throws Exception;

    //nuevos escritorio
    public byte[] generarReporteInvListaConsultaCompra(List<InvListaConsultaCompra> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception;

    public byte[] generarReporteInvListaConsultaVenta(List<InvListaConsultaVenta> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception;

    public byte[] generarReporteInvListaConsultaConsumo(List<InvListaConsultaConsumo> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception;

    public byte[] generarReporteInvListaConsultaTransferencia(List<InvListaConsultaTransferencia> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception;

    public byte[] generarReporteContratoCliente(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvClienteContratoTO> listadoContratos) throws Exception;

    public Map<String, Object> exportarReporteComprativoClienteContratos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvClienteContratosTO> listado) throws Exception;
}
