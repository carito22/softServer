package ec.com.todocompu.ShrimpSoftServer.cartera.report;

import ec.com.todocompu.ShrimpSoftUtils.DetalleExportarFiltrado;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorCobrarSaldoAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarCobrarAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarCobrarGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarSaldoAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCobrosDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCuentasPagadasListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCuentasPorCobrarListadoVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCuentasPorPagarListadoComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunPagosDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunPagosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaMayorAuxiliarClienteProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CuentasPorCobrarDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CuentasPorPagarDetalladoTO;
import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.cartera.report.ReporteCarFunCuentasPorCobrarListadoVentas;
import ec.com.todocompu.ShrimpSoftUtils.cartera.report.ReporteCarFunCuentasPorPagarListadoCompras;
import ec.com.todocompu.ShrimpSoftUtils.cartera.report.ReporteCobros;
import ec.com.todocompu.ShrimpSoftUtils.cartera.report.ReporteCuentasPorCobrarDetallado;
import ec.com.todocompu.ShrimpSoftUtils.cartera.report.ReporteCuentasPorCobrarGeneral;
import ec.com.todocompu.ShrimpSoftUtils.cartera.report.ReporteCuentasPorPagarAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.report.ReporteCuentasPorPagarCobrarSaldoAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.report.ReporteCuentasPorPagarDetallado;
import ec.com.todocompu.ShrimpSoftUtils.cartera.report.ReporteCuentasPorPagarGeneral;
import ec.com.todocompu.ShrimpSoftUtils.cartera.report.ReporteListadoCobros;
import ec.com.todocompu.ShrimpSoftUtils.cartera.report.ReporteListadoPagos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.report.ReporteMayorAuxiliarProveedorCliente;
import ec.com.todocompu.ShrimpSoftUtils.cartera.report.ReportePagos;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.io.File;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public interface ReporteCarteraService {

    public byte[] generarReporteCuentasPorCobrarDetallado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCuentasPorCobrarDetallado> reporteCuentasPorCobrarDetallado) throws Exception;

    public byte[] generarReporteCuentasPorPagarDetallado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCuentasPorPagarDetallado> reporteCuentasPorPagarDetallado) throws Exception;

    public byte[] generarReporteCuentasPorCobrarGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCuentasPorCobrarGeneral> reporteCuentasPorCobrarGeneral) throws Exception;

    public byte[] generarReporteCarFunCuentasPorCobrarListadoVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCarFunCuentasPorCobrarListadoVentas> reporteCarFunCuentasPorCobrarListadoVentas) throws Exception;

    public byte[] generarReporteCarFunCuentasPorPagarListadoCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCarFunCuentasPorPagarListadoCompras> reporteCarFunCuentasPorPagarListadoCompras) throws Exception;

    public byte[] generarReporteCuentasPorPagarGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCuentasPorPagarGeneral> reporteCuentasPorPagarGeneral) throws Exception;

    public byte[] generarReporteCuentasPorPagarAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCuentasPorPagarAnticipos> reporteCuentasPorPagarAnticipos) throws Exception;

    public byte[] generarReporteCuentasPorPagarSaldoAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCuentasPorPagarCobrarSaldoAnticipos> reporteCuentasPorPagarSaldoAnticipos) throws Exception;

    public byte[] generarReporteCuentasPorCobrarSaldoAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCuentasPorPagarCobrarSaldoAnticipos> reporteCuentasPorPagarSaldoAnticipos) throws Exception;

    public byte[] generarReporteCuentasPorCobrarAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCuentasPorPagarAnticipos> reporteCuentasPorPagarAnticipos) throws Exception;

    public byte[] generarReporteCobros(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCobros> reporteCobros) throws Exception;

    public byte[] generarReportePagos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReportePagos> reportePagos) throws Exception;

    public byte[] generarReporteMayorAuxiliarProveedorCliente(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteMayorAuxiliarProveedorCliente> reporteMayorAuxiliarProveedorCliente) throws Exception;

    public byte[] generarReporteListadoPagos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteListadoPagos> reporteListadoPagos) throws Exception;

    public byte[] generarReporteListadoCobros(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteListadoCobros> reporteListadoCobros) throws Exception;

    public byte[] generarReporteCarReporteFormaPago(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<CarPagosCobrosFormaTO> listado) throws Exception;

    //Exportar
    public Map<String, Object> exportarReporteCarReporteFormaPago(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String tipo, List<CarPagosCobrosFormaTO> listado) throws Exception;

    //Reporte Consultas
    public byte[] generarReporteCarFunCobrosDetalle(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, String cliente, List<CarFunCobrosDetalleTO> listado, String sector, String fechaDesde, String fechaHasta) throws Exception;

    public Map<String, Object> exportarReporteCarFunCobros(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String cliente, List<CarFunCobrosTO> listado, String sector, String fechaDesde, String fechaHasta);

    public byte[] generarReporteCarFunCuentasPorCobrarListadoVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, String cliente, List<CarFunCuentasPorCobrarListadoVentasTO> listado, String sector, String fechaDesde, String fechaHasta) throws Exception;

    public Map<String, Object> exportarReporteCarFunCuentasPorCobrarListadoVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String cliente, List<CarFunCuentasPorCobrarListadoVentasTO> listado, String sector, String fechaDesde, String fechaHasta);

    public byte[] generarReporteCarListaMayorAuxiliarClienteProveedor(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, String cliente, List<CarListaMayorAuxiliarClienteProveedorTO> listado, String sector, String fechaDesde, String fechaHasta, String tipo) throws Exception;

    public Map<String, Object> exportarReporteCarListaMayorAuxiliarClienteProveedor(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String cliente, List<CarListaMayorAuxiliarClienteProveedorTO> listado, String sector, String fechaDesde, String fechaHasta, String tipo);

    public byte[] generarReporteCarListaCuentasPorCobrarDetallado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<CuentasPorCobrarDetalladoTO> listado, String sector, String fechaDesde, String fechaHasta) throws Exception;

    public Map<String, Object> exportarReporteCarListaCuentasPorCobrarDetallado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CuentasPorCobrarDetalladoTO> listado, String sector, String fechaDesde, String fechaHasta);

    public byte[] generarReporteCarListaCuentasPorCobrarGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<CarCuentasPorPagarCobrarGeneralTO> listado, String sector, String fechaHasta);

    public Map<String, Object> exportarReporteCarListaCuentasPorCobrarGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CarCuentasPorPagarCobrarGeneralTO> listado, String sector, String fechaHasta);

    public byte[] generarReporteCuentasPorPagarAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, String cliente, List<CarCuentasPorCobrarSaldoAnticiposTO> listado, String sector, String fechaHasta) throws Exception;

    public Map<String, Object> exportarReporteCuentasPorPagarAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CarCuentasPorCobrarSaldoAnticiposTO> listado, String sector, String fechaHasta);

    public byte[] generarReporteCuentasPorCobrarAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<CarCuentasPorPagarCobrarAnticiposTO> listado, String sector, String fechaHasta, boolean verDetalle) throws Exception;

    public File generarReporteCuentasPorCobrarAnticiposFile(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CarCuentasPorPagarCobrarAnticiposTO> listado, String fechaHasta) throws Exception;

    public Map<String, Object> exportarReporteCuentasPorCobrarAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CarCuentasPorPagarCobrarAnticiposTO> listado, String sector, String fechaHasta, boolean verDetalle);

    public byte[] generarReporteCarFunPagos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<CarFunPagosTO> listado, String sector, String fechaDesde, String fechaHasta, String proveedor) throws Exception;

    public Map<String, Object> exportarReporteCarFunPagos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CarFunPagosTO> listado, String sector, String fechaDesde, String fechaHasta, String proveedor);

    public byte[] generarReporteCarFunPagosDetalle(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<CarFunPagosDetalleTO> listado, String sector, String fechaDesde, String fechaHasta, String proveedor, String formaPago);

    public Map<String, Object> exportarReporteCarFunPagosDetalle(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CarFunPagosDetalleTO> listado, String sector, String fechaDesde, String fechaHasta, String proveedor);

    public byte[] generarReporteCarFunCuentasPorPagarListadoCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<CarFunCuentasPorPagarListadoComprasTO> listado, String sector, String fechaDesde, String fechaHasta, String proveedor) throws Exception;

    public byte[] generarReporteCuentasPagadas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<CarFunCuentasPagadasListadoTO> listado, String sector, String fechaDesde, String fechaHasta, String proveedor) throws Exception;

    public Map<String, Object> exportarReporteCuentasPorPagarListadoCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CarFunCuentasPorPagarListadoComprasTO> listado, String sector, String fechaDesde, String fechaHasta, String proveedor);

    public byte[] generarReporteCarListaCuentasPorPagarDetallado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<CuentasPorPagarDetalladoTO> listado, String sector, String fechaHasta, String proveedor) throws Exception;

    public Map<String, Object> exportarReporteCarListaCuentasPorPagarDetallado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CuentasPorPagarDetalladoTO> listado, String sector, String fechaHasta, String proveedor);

    public byte[] generarReporteCarListaCuentasPorPagarGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<CarCuentasPorPagarCobrarGeneralTO> listado, String sector, String fechaHasta, String proveedor) throws Exception;

    public Map<String, Object> exportarReporteCarListaCuentasPorPagarGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CarCuentasPorPagarCobrarGeneralTO> listado, String sector, String fechaHasta);

    public byte[] generarReporteCarListaCuentasPorPagarSaldoAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<CarCuentasPorPagarSaldoAnticiposTO> listado, String sector, String fechaDesde, String fechaHasta, String proveedor) throws Exception;

    public Map<String, Object> exportarReporteCarListaCuentasPorPagarSaldoAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CarCuentasPorPagarSaldoAnticiposTO> listado, String sector, String fechaDesde, String fechaHasta);

    public byte[] generarReporteCarCuentasPorPagarCobrarAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<CarCuentasPorPagarCobrarAnticiposTO> listado, String sector, String fechaHasta, boolean verDetalle) throws Exception;

    public File generarReporteCarCuentasPorPagarCobrarAnticiposFile(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CarCuentasPorPagarCobrarAnticiposTO> listado, String fechaHasta);

    public Map<String, Object> exportarReporteCuentasPorPagarCobrarAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CarCuentasPorPagarCobrarAnticiposTO> listado, String sector, String fechaHasta, List<DetalleExportarFiltrado> listadoFiltrado);

    public byte[] generarReportePorLoteCobros(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<String> numeroSistema, String empresa) throws Exception;

    public byte[] generarReportePorPagos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<String> numeroSistema, String empresa) throws Exception;

    public void exportarTxtDebitoBancario(List<CuentasPorCobrarDetalladoTO> cuentasPorCobrarDetallado, String reporte, HttpServletResponse response, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> exportarCuentasPagadas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CarFunCuentasPagadasListadoTO> listado, String sector, String fechaDesde, String fechaHasta, String proveedor);

    public Map<String, Object> exportarReporteCarFunPagosDetalleForma(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CarFunPagosDetalleTO> listado, String sector, String fechaDesde, String fechaHasta, String proveedor, String formaPagoSeleccionada) throws Exception;

    public Map<String, Object> exportarReporteCarFunCobrosDetalleForma(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String cliente, List<CarFunCobrosDetalleTO> listado, String sector, String fechaDesde, String fechaHasta, String formaPagoSeleccionada) throws Exception;

    public byte[] generarReporteCarFunCobros(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, String cliente, List<CarFunCobrosTO> listado, String sector, String fechaDesde, String fechaHasta) throws Exception;

}
