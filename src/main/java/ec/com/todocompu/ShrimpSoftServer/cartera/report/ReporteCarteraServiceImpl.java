package ec.com.todocompu.ShrimpSoftServer.cartera.report;

import ec.com.todocompu.ShrimpSoftServer.cartera.dao.CobrosDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.PagosDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.UsuarioService;
import ec.com.todocompu.ShrimpSoftUtils.DetalleExportarFiltrado;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
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
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaCobrosClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaMayorAuxiliarClienteProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosCobrosDetalleFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CuentasPorCobrarDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CuentasPorPagarDetalladoTO;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuario;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Service
public class ReporteCarteraServiceImpl implements ReporteCarteraService {

    BigDecimal cero = new BigDecimal("0.00");

    @Autowired
    private GenericReporteService genericReporteService;
    @Autowired
    private CobrosDao cobrosDao;
    @Autowired
    private PagosDao pagosDao;
    @Autowired
    private UsuarioService usuarioService;

    private String modulo = "cartera";

    @Override
    public byte[] generarReporteCuentasPorCobrarDetallado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCuentasPorCobrarDetallado> reporteCuentasPorCobrarDetallado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportCuentasPorCobrarDetallado.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteCuentasPorCobrarDetallado);
    }

    @Override
    public byte[] generarReporteCuentasPorPagarDetallado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCuentasPorPagarDetallado> reporteCuentasPorPagarDetallado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportCuentasPorPagarDetallado.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteCuentasPorPagarDetallado);
    }

    public byte[] generarReporteCuentasPorCobrarGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCuentasPorCobrarGeneral> reporteCuentasPorCobrarGeneral) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportCuentasPorCobrarGeneral.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteCuentasPorCobrarGeneral);
    }

    @Override
    public byte[] generarReporteCarFunCuentasPorCobrarListadoVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCarFunCuentasPorCobrarListadoVentas> reporteCarFunCuentasPorCobrarListadoVentas) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportCarFunCuentasPorCobrarListadoVentas.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteCarFunCuentasPorCobrarListadoVentas);
    }

    @Override
    public byte[] generarReporteCarFunCuentasPorPagarListadoCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCarFunCuentasPorPagarListadoCompras> reporteCarFunCuentasPorPagarListadoCompras) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportCarFunCuentasPorPagarListadoCompras.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteCarFunCuentasPorPagarListadoCompras);
    }

    @Override
    public byte[] generarReporteCuentasPorPagarGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCuentasPorPagarGeneral> reporteCuentasPorPagarGeneral) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportCuentasPorPagarGeneral.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteCuentasPorPagarGeneral);
    }

    @Override
    public byte[] generarReporteCuentasPorPagarAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCuentasPorPagarAnticipos> reporteCuentasPorPagarAnticipos) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportCuentasPorPagarAnticipos.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteCuentasPorPagarAnticipos);
    }

    @Override
    public byte[] generarReporteCuentasPorPagarSaldoAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCuentasPorPagarCobrarSaldoAnticipos> reporteCuentasPorPagarSaldoAnticipos) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportCuentasPorPagarSaldoAnticipos.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteCuentasPorPagarSaldoAnticipos);
    }

    @Override
    public byte[] generarReporteCuentasPorCobrarSaldoAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCuentasPorPagarCobrarSaldoAnticipos> reporteCuentasPorPagarSaldoAnticipos) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportCuentasPorCobrarSaldoAnticipos.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteCuentasPorPagarSaldoAnticipos);
    }

    @Override
    public byte[] generarReporteCuentasPorCobrarAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCuentasPorPagarAnticipos> reporteCuentasPorPagarAnticipos) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportCuentasPorCobrarAnticipos.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteCuentasPorPagarAnticipos);
    }

    @Override
    public byte[] generarReporteCobros(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCobros> reporteCobros) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportCobros.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteCobros);
    }

    @Override
    public byte[] generarReportePagos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReportePagos> reportePagos) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportPagos.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reportePagos);
    }

    @Override
    public byte[] generarReporteMayorAuxiliarProveedorCliente(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteMayorAuxiliarProveedorCliente> reporteMayorAuxiliarProveedorCliente) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportMayorAuxiliarProveedorCliente.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteMayorAuxiliarProveedorCliente);
    }

    @Override
    public byte[] generarReporteListadoPagos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteListadoPagos> reporteListadoPagos) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportListadoPago.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteListadoPagos);
    }

    @Override
    public byte[] generarReporteListadoCobros(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteListadoCobros> reporteListadoCobros) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportListadoCobro.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteListadoCobros);
    }

    @Override
    public byte[] generarReporteCarReporteFormaPago(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<CarPagosCobrosFormaTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public Map<String, Object> exportarReporteCarReporteFormaPago(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String tipo, List<CarPagosCobrosFormaTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            if (tipo.equalsIgnoreCase("P")) {
                listaCabecera.add("SReporte de forma de pago");
            } else {
                listaCabecera.add("SReporte de forma de cobro");
            }
            listaCabecera.add("S");
            listaCuerpo.add("SCuenta" + "¬" + "SDetalle" + "¬" + "SSector" + "¬" + "SInactivo");
            for (CarPagosCobrosFormaTO fp : listado) {
                listaCuerpo.add(
                        (fp.getCtaCodigo() == null ? "B" : "S" + fp.getCtaCodigo()) + "¬"
                        + (fp.getFpDetalle() == null ? "B" : "S" + fp.getFpDetalle()) + "¬"
                        + (fp.getSecCodigo() == null ? "B" : "S" + fp.getSecCodigo()) + "¬"
                        + (fp.getFpInactivo() ? "SINACTIVO" : "SACTIVO"));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteCarFunCobrosDetalle(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, String cliente, List<CarFunCobrosDetalleTO> listado, String sector, String fechaDesde, String fechaHasta) {
        java.util.List<ReporteListadoCobros> reporteListadoCobroses = new java.util.ArrayList();
        ReporteListadoCobros reporteListadoCobros = null;
        for (CarFunCobrosDetalleTO carFunCobrosDetalleTO : listado) {
            reporteListadoCobros = new ReporteListadoCobros();
            reporteListadoCobros.setCodigoCP(sector);
            reporteListadoCobros.setDesde(fechaDesde);
            reporteListadoCobros.setHasta(fechaHasta);
            reporteListadoCobros.setCliente(cliente);

            reporteListadoCobros.setCobNumeroSistema(carFunCobrosDetalleTO.getCobNumeroSistema());
            reporteListadoCobros.setCobFecha(carFunCobrosDetalleTO.getCobFecha());
            reporteListadoCobros.setCobCliente(carFunCobrosDetalleTO.getCobCliente());
            reporteListadoCobros.setCobValor(carFunCobrosDetalleTO.getCobValor());
            reporteListadoCobros.setCobObservaciones(carFunCobrosDetalleTO.getCobObservaciones());
            reporteListadoCobros.setCobPendiente(carFunCobrosDetalleTO.getCobPendiente());
            reporteListadoCobros.setCobAnulado(carFunCobrosDetalleTO.getCobAnulado());
            reporteListadoCobroses.add(reporteListadoCobros);
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteListadoCobroses);
    }

    @Override
    public Map<String, Object> exportarReporteCarFunCobros(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String cliente, List<CarFunCobrosTO> listado, String sector, String fechaDesde, String fechaHasta) {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado de Cobros");
            listaCabecera.add("SCentro de producción: " + (sector != null ? sector : " "));
            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("SCliente: " + (cliente != null ? cliente : " "));
            listaCabecera.add("S");
            listaCuerpo.add("SNúmero" + "¬" + "SFecha" + "¬" + "SId" + "¬" + "SCliente" + "¬" + "SValor" + "¬" + "SObservaciones"
                    + "¬" + "SPendiente" + "¬" + "SAnulado");
            for (CarFunCobrosTO carFunCobrosTO : listado) {
                listaCuerpo.add((carFunCobrosTO.getCobNumeroSistema() == null ? "B"
                        : "S" + carFunCobrosTO.getCobNumeroSistema()) + "¬"
                        + (carFunCobrosTO.getCobFecha() == null ? "B" : "S" + carFunCobrosTO.getCobFecha())
                        + "¬"
                        + (carFunCobrosTO.getCobClienteId() == null ? "B" : "S" + carFunCobrosTO.getCobClienteId())
                        + "¬"
                        + (carFunCobrosTO.getCobCliente() == null ? "B" : "S" + carFunCobrosTO.getCobCliente())
                        + "¬"
                        + (carFunCobrosTO.getCobValor() == null ? "B" : "D" + carFunCobrosTO.getCobValor().add(cero).toString())
                        + "¬"
                        + (carFunCobrosTO.getCobObservaciones() == null ? "B" : "S" + carFunCobrosTO.getCobObservaciones())
                        + "¬"
                        + (carFunCobrosTO.getCobPendiente() == null ? "B" : "S" + (carFunCobrosTO.getCobPendiente() ? "PENDIENTE" : "NO"))
                        + "¬"
                        + (carFunCobrosTO.getCobAnulado() == null ? "B" : "S" + (carFunCobrosTO.getCobAnulado() ? "ANULADO" : "NO"))
                        + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteCarFunCobrosDetalleForma(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String cliente, List<CarFunCobrosDetalleTO> listado, String sector, String fechaDesde, String fechaHasta, String formaSeleccionada) {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado de Cobros por forma de pago");
            listaCabecera.add("SCentro de producción: " + (sector != null ? sector : " "));
            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("SForma de Pago: " + (formaSeleccionada != null ? formaSeleccionada : " "));
            listaCabecera.add("SCliente: " + (cliente != null ? cliente : " "));
            listaCabecera.add("S");
            listaCuerpo.add("SNúmero" + "¬" + "SFecha" + "¬" + "SCliente" + "¬" + "SForma de pago" + "¬" + "SValor" + "¬" + "SObservaciones"
                    + "¬" + "SPendiente" + "¬" + "SAnulado");
            for (CarFunCobrosDetalleTO carFunCobrosTO : listado) {
                listaCuerpo.add((carFunCobrosTO.getCobNumeroSistema() == null ? "B"
                        : "S" + carFunCobrosTO.getCobNumeroSistema()) + "¬"
                        + (carFunCobrosTO.getCobFecha() == null ? "B" : "S" + carFunCobrosTO.getCobFecha())
                        + "¬"
                        + (carFunCobrosTO.getCobCliente() == null ? "B" : "S" + carFunCobrosTO.getCobCliente())
                        + "¬"
                        + (carFunCobrosTO.getCobFormaPago() == null ? "B" : "S" + carFunCobrosTO.getCobFormaPago())
                        + "¬"
                        + (carFunCobrosTO.getCobValor() == null ? "B" : "D" + carFunCobrosTO.getCobValor().add(cero).toString())
                        + "¬"
                        + (carFunCobrosTO.getCobObservaciones() == null ? "B" : "S" + carFunCobrosTO.getCobObservaciones())
                        + "¬"
                        + (carFunCobrosTO.getCobPendiente() == null ? "B" : "S" + (carFunCobrosTO.getCobPendiente() ? "PENDIENTE" : "NO"))
                        + "¬"
                        + (carFunCobrosTO.getCobAnulado() == null ? "B" : "S" + (carFunCobrosTO.getCobAnulado() ? "ANULADO" : "NO"))
                        + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteCarFunCuentasPorCobrarListadoVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, String cliente,
            List<CarFunCuentasPorCobrarListadoVentasTO> listado, String sector, String fechaDesde, String fechaHasta) throws Exception {

        java.util.List<ReporteCarFunCuentasPorCobrarListadoVentas> reporteListadoVenta = new java.util.ArrayList();
        ReporteCarFunCuentasPorCobrarListadoVentas reporteListadoVentas = null;
        for (CarFunCuentasPorCobrarListadoVentasTO carFunVentasDetalleTO : listado) {
            reporteListadoVentas = new ReporteCarFunCuentasPorCobrarListadoVentas();
            reporteListadoVentas.setCodigoCP(sector);
            reporteListadoVentas.setFechaHasta(fechaHasta);

            reporteListadoVentas.setCxcdPeriodo(carFunVentasDetalleTO.getCxcdPeriodo());
            reporteListadoVentas.setCxcdMotivo(carFunVentasDetalleTO.getCxcdMotivo());
            reporteListadoVentas.setCxcdNumero(carFunVentasDetalleTO.getCxcdNumero());
            reporteListadoVentas.setCxcdCliente(carFunVentasDetalleTO.getCxcdCliente());
            reporteListadoVentas.setCxcdFechaEmision(carFunVentasDetalleTO.getCxcdFechaEmision());
            reporteListadoVentas.setCxcdFechaVencimiento(carFunVentasDetalleTO.getCxcdFechaVencimiento());
            reporteListadoVentas.setCxcdSaldo(carFunVentasDetalleTO.getCxcdSaldo());
            reporteListadoVenta.add(reporteListadoVentas);
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteListadoVenta);
    }

    @Override
    public Map<String, Object> exportarReporteCarFunCuentasPorCobrarListadoVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String cliente,
            List<CarFunCuentasPorCobrarListadoVentasTO> listado, String sector, String fechaDesde, String fechaHasta) {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado de Cobros");
            listaCabecera.add("SCentro de producción: " + (sector != null ? sector : " "));
            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("SCliente: " + (cliente != null ? cliente : " "));
            listaCabecera.add("S");
            listaCuerpo.add("SPeriodo" + "¬" + "SMotivo" + "¬" + "SNúmero" + "¬" + "SCliente" + "¬" + "SFecha Emisión" + "¬" + "SFecha Vencimiento"
                    + "¬" + "SSaldo");
            for (CarFunCuentasPorCobrarListadoVentasTO carFunVentasTO : listado) {
                listaCuerpo.add((carFunVentasTO.getCxcdPeriodo() == null ? "B" : "S" + carFunVentasTO.getCxcdPeriodo())
                        + "¬"
                        + (carFunVentasTO.getCxcdMotivo() == null ? "B" : "S" + carFunVentasTO.getCxcdMotivo())
                        + "¬"
                        + (carFunVentasTO.getCxcdNumero() == null ? "B" : "S" + carFunVentasTO.getCxcdNumero())
                        + "¬"
                        + (carFunVentasTO.getCxcdCliente() == null ? "B" : "S" + carFunVentasTO.getCxcdCliente())
                        + "¬"
                        + (carFunVentasTO.getCxcdFechaEmision() == null ? "B" : "S" + carFunVentasTO.getCxcdFechaEmision())
                        + "¬"
                        + (carFunVentasTO.getCxcdFechaVencimiento() == null ? "B" : "S" + (carFunVentasTO.getCxcdFechaVencimiento()))
                        + "¬"
                        + (carFunVentasTO.getCxcdSaldo() == null ? "B" : "D" + carFunVentasTO.getCxcdSaldo().add(cero).toString())
                        + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteCarListaMayorAuxiliarClienteProveedor(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte,
            String cliente, List<CarListaMayorAuxiliarClienteProveedorTO> listado, String sector, String fechaDesde, String fechaHasta, String tipo) throws Exception {

        java.util.List<ReporteMayorAuxiliarProveedorCliente> reporteListaMayorAuxiliar = new java.util.ArrayList();
        ReporteMayorAuxiliarProveedorCliente reporteListadoMayAux = null;

        for (CarListaMayorAuxiliarClienteProveedorTO carListaMayAux : listado) {
            reporteListadoMayAux = new ReporteMayorAuxiliarProveedorCliente();
            if ("C".equals(tipo)) {
                reporteListadoMayAux.setTituloReporte("CLIENTES");
            } else {
                reporteListadoMayAux.setTituloReporte("PROVEEDORES");
            }
            reporteListadoMayAux.setDesde(fechaDesde);
            reporteListadoMayAux.setHasta(fechaHasta);
            reporteListadoMayAux.setId(cliente);

            reporteListadoMayAux.setMaContable(carListaMayAux.getMaContable());
            reporteListadoMayAux.setMaFecha(carListaMayAux.getMaFecha());
            reporteListadoMayAux.setMaCp(carListaMayAux.getMaCp());
            reporteListadoMayAux.setMaDebe(carListaMayAux.getMaDebe());
            reporteListadoMayAux.setMaHaber(carListaMayAux.getMaHaber());
            reporteListadoMayAux.setMaSaldo(carListaMayAux.getMaSaldo());
            reporteListadoMayAux.setMaObservaciones(carListaMayAux.getMaObservaciones());
            reporteListadoMayAux.setMapGrupoEmpresarial(carListaMayAux.getMapGrupoEmpresarial());
            reporteListaMayorAuxiliar.add(reporteListadoMayAux);
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteListaMayorAuxiliar);
    }

    @Override
    public Map<String, Object> exportarReporteCarListaMayorAuxiliarClienteProveedor(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String cliente, List<CarListaMayorAuxiliarClienteProveedorTO> listado, String sector, String fechaDesde, String fechaHasta, String tipo) {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            if ("C".equals(tipo)) {
                listaCabecera.add("SCobros - Mayor Auxiliar Cliente");
            } else {
                listaCabecera.add("SCobros - Mayor Auxiliar Proveedores");
            }
            listaCabecera.add("SCentro de producción: " + (sector != null ? sector : " "));
            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("SCliente: " + (cliente != null ? cliente : " "));
            listaCabecera.add("S");
            listaCuerpo.add("SContable" + "¬" + "SFecha" + "¬" + "SClave Principal" + "¬" + "SCp" + "¬" + "SDocumento"
                    + "¬" + "SDebe" + "¬" + "SHaber" + "¬" + "SSaldo" + "¬" + "SObservaciones" + "¬" + "SGrupo empresarial" + "¬" + "SOrden");
            for (CarListaMayorAuxiliarClienteProveedorTO carListaMayorAuxiliarClienteProveedorTO : listado) {
                listaCuerpo.add((carListaMayorAuxiliarClienteProveedorTO.getMaContable() == null ? "B"
                        : "S" + carListaMayorAuxiliarClienteProveedorTO.getMaContable())
                        + "¬"
                        + (carListaMayorAuxiliarClienteProveedorTO.getMaFecha() == null ? "B"
                        : "S" + carListaMayorAuxiliarClienteProveedorTO.getMaFecha())
                        + "¬"
                        + (carListaMayorAuxiliarClienteProveedorTO.getMaClavePrincipal() == null ? "B"
                        : "S" + carListaMayorAuxiliarClienteProveedorTO.getMaClavePrincipal())
                        + "¬"
                        + (carListaMayorAuxiliarClienteProveedorTO.getMaCp() == null ? "B"
                        : "S" + carListaMayorAuxiliarClienteProveedorTO.getMaCp())
                        + "¬"
                        + (carListaMayorAuxiliarClienteProveedorTO.getMaDocumento() == null ? "B"
                        : "S" + carListaMayorAuxiliarClienteProveedorTO.getMaDocumento())
                        + "¬"
                        + (carListaMayorAuxiliarClienteProveedorTO.getMaDebe() == null ? "B"
                        : "D" + carListaMayorAuxiliarClienteProveedorTO.getMaDebe().add(cero).toString())
                        + "¬"
                        + (carListaMayorAuxiliarClienteProveedorTO.getMaHaber() == null ? "B"
                        : "D" + carListaMayorAuxiliarClienteProveedorTO.getMaHaber().add(cero).toString())
                        + "¬"
                        + (carListaMayorAuxiliarClienteProveedorTO.getMaSaldo() == null ? "B"
                        : "D" + carListaMayorAuxiliarClienteProveedorTO.getMaSaldo().add(cero).toString())
                        + "¬"
                        + (carListaMayorAuxiliarClienteProveedorTO.getMaObservaciones() == null ? "B"
                        : "S" + carListaMayorAuxiliarClienteProveedorTO.getMaObservaciones())
                        + "¬"
                        + (carListaMayorAuxiliarClienteProveedorTO.getMapGrupoEmpresarial() == null ? "B"
                        : "S" + carListaMayorAuxiliarClienteProveedorTO.getMapGrupoEmpresarial())
                        + "¬" + (carListaMayorAuxiliarClienteProveedorTO.getMaOrden() == null ? "B"
                        : "I" + carListaMayorAuxiliarClienteProveedorTO.getMaOrden())
                        + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteCarListaCuentasPorCobrarDetallado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte,
            List<CuentasPorCobrarDetalladoTO> listado, String sector, String fechaDesde, String fechaHasta) throws Exception {
        java.util.List<ReporteCuentasPorCobrarDetallado> reporteListaCuentasPorCobrar = new java.util.ArrayList();
        ReporteCuentasPorCobrarDetallado reporteCuentasCobrarD = null;
        for (CuentasPorCobrarDetalladoTO carCuentasPorCobrar : listado) {
            reporteCuentasCobrarD = new ReporteCuentasPorCobrarDetallado();
            reporteCuentasCobrarD.setCodigoCP(sector);
            reporteCuentasCobrarD.setFechaHasta(fechaHasta);

            reporteCuentasCobrarD.setCxpdPeriodo(carCuentasPorCobrar.getCxcdPeriodo());
            reporteCuentasCobrarD.setCxpdMotivo(carCuentasPorCobrar.getCxcdMotivo());
            reporteCuentasCobrarD.setCxpdNumero(carCuentasPorCobrar.getCxcdNumero());
            reporteCuentasCobrarD.setCxpdCliente(carCuentasPorCobrar.getCxcdCliente());
            reporteCuentasCobrarD.setCxpdFechaEmision(carCuentasPorCobrar.getCxcdFechaEmision());
            reporteCuentasCobrarD.setCxpdFechaVencimiento(carCuentasPorCobrar.getCxcdFechaVencimiento());
            reporteCuentasCobrarD.setCxpdSaldo(carCuentasPorCobrar.getCxcdSaldo());

            reporteCuentasCobrarD.setCxcdDiasCredito(carCuentasPorCobrar.getCxcdDiasCredito());
            reporteCuentasCobrarD.setCxcdDiasVencidos(carCuentasPorCobrar.getCxcdDiasVencidos());
            reporteCuentasCobrarD.setCxcdTotal(carCuentasPorCobrar.getCxcdTotal());
            reporteCuentasCobrarD.setCxcdRetencionValor(carCuentasPorCobrar.getCxcdRetencionValor());
            reporteCuentasCobrarD.setCxcdAbono(carCuentasPorCobrar.getCxcdAbono());
            reporteCuentasCobrarD.setCxcdClienteGrupoEmpresarial(carCuentasPorCobrar.getCxcdClienteGrupoEmpresarial());
            reporteCuentasCobrarD.setCxcdClienteRazonSocial(carCuentasPorCobrar.getCxcdClienteRazonSocial());
            reporteCuentasCobrarD.setCxcdClienteCelular(carCuentasPorCobrar.getCxcdClienteCelular());
            reporteCuentasCobrarD.setCxcdClienteEmail(carCuentasPorCobrar.getCxcdClienteEmail());
            reporteCuentasCobrarD.setCxcdRetencionNumero(carCuentasPorCobrar.getCxcdRetencionNumero());
            reporteCuentasCobrarD.setCxcdChequeNumero(carCuentasPorCobrar.getCxcdChequeNumero());

            reporteListaCuentasPorCobrar.add(reporteCuentasCobrarD);
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteListaCuentasPorCobrar);
    }

    @Override
    public Map<String, Object> exportarReporteCarListaCuentasPorCobrarDetallado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CuentasPorCobrarDetalladoTO> listado, String sector, String fechaDesde, String fechaHasta) {
        try {

            List<String> rucsMARAQUATIC = Arrays.asList("0993013447001", "0993046590001", "0993176907001");
            List<String> rucsNET = Arrays.asList("0791755093001", "0791702070001", "0791702054001", "0791723396001", "0791807298001");
            boolean esMARAQUATIC = rucsMARAQUATIC.contains(usuarioEmpresaReporteTO.getEmpRuc());
            boolean esNET = rucsNET.contains(usuarioEmpresaReporteTO.getEmpRuc());
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SCuentas por cobrar detallado");
            listaCabecera.add("SCentro de producción: " + (sector != null ? sector : " "));
            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");

            String cabecera = "SPeriodo" + "¬" + "SMotivo" + "¬" + "SNúmero" + (esNET ? "¬SContrato" : "") + "¬" + "SComprobante de Venta"
                    + "¬" + "SCliente ID" + "¬" + "SCliente Nombre" + "¬" + "SGrupo Empresarial"
                    + (!esMARAQUATIC ? ("¬" + "SCliente Direccion" + "¬" + "SCliente cantón" + "¬" + "SCelular" + "¬" + "SCorreo") : "")
                    + "¬" + "SFecha Emisión" + "¬" + "SFecha Vencimiento" + "¬" + "SCupo de crédito" + "¬" + "SDias Credito" + "¬" + "SDias Vencidos"
                    + "¬" + "SComprobante de Retención" + "¬" + "SCheque Número"
                    + "¬" + "STotal" + "¬" + "SRetención" + "¬" + "SAbonos" + "¬" + "SSaldo";
            if (esMARAQUATIC) {
                cabecera = cabecera + "¬" + "SDías crédito aseguradora" + "¬" + "SCupo crédito aseguradora" + "¬" + "SGarantía";
            }

            listaCuerpo.add(cabecera);
            for (CuentasPorCobrarDetalladoTO cuentasPorCobrarDetalladoTO : listado) {

                String cuerpo = (cuentasPorCobrarDetalladoTO.getCxcdPeriodo() == null ? "B" : "S" + cuentasPorCobrarDetalladoTO.getCxcdPeriodo())
                        + "¬"
                        + (cuentasPorCobrarDetalladoTO.getCxcdMotivo() == null ? "B" : "S" + cuentasPorCobrarDetalladoTO.getCxcdMotivo())
                        + "¬"
                        + (cuentasPorCobrarDetalladoTO.getCxcdNumero() == null ? "B" : "S" + cuentasPorCobrarDetalladoTO.getCxcdNumero())
                        + (esNET ? ("¬" + (cuentasPorCobrarDetalladoTO.getCxcdContrato() == null ? "B" : "S" + cuentasPorCobrarDetalladoTO.getCxcdContrato())) : "")
                        + "¬"
                        + (cuentasPorCobrarDetalladoTO.getCxcdCliente() == null ? "B" : "S" + cuentasPorCobrarDetalladoTO.getCxcdCliente())
                        + "¬"
                        + (cuentasPorCobrarDetalladoTO.getCxcdClienteId() == null ? "B" : "S" + cuentasPorCobrarDetalladoTO.getCxcdClienteId())
                        + "¬"
                        + (cuentasPorCobrarDetalladoTO.getCxcdClienteRazonSocial() == null ? "B" : "S" + cuentasPorCobrarDetalladoTO.getCxcdClienteRazonSocial())
                        + "¬"
                        + (cuentasPorCobrarDetalladoTO.getCxcdClienteGrupoEmpresarial() == null ? "B" : "S" + cuentasPorCobrarDetalladoTO.getCxcdClienteGrupoEmpresarial())
                        + (!esMARAQUATIC ? ("¬"
                                + (cuentasPorCobrarDetalladoTO.getCxcdClienteDireccion() == null ? "B" : "S" + cuentasPorCobrarDetalladoTO.getCxcdClienteDireccion())
                                + "¬"
                                + (cuentasPorCobrarDetalladoTO.getCxcdClienteCanton() == null ? "B" : "S" + cuentasPorCobrarDetalladoTO.getCxcdClienteCanton())
                                + "¬"
                                + (cuentasPorCobrarDetalladoTO.getCxcdClienteCelular() == null ? "B" : "S" + cuentasPorCobrarDetalladoTO.getCxcdClienteCelular())
                                + "¬"
                                + (cuentasPorCobrarDetalladoTO.getCxcdClienteEmail() == null ? "B" : "S" + cuentasPorCobrarDetalladoTO.getCxcdClienteEmail())) : "")
                        + "¬"
                        + (cuentasPorCobrarDetalladoTO.getCxcdFechaEmision() == null ? "B" : "S" + cuentasPorCobrarDetalladoTO.getCxcdFechaEmision())
                        + "¬"
                        + (cuentasPorCobrarDetalladoTO.getCxcdFechaVencimiento() == null ? "B" : "S" + cuentasPorCobrarDetalladoTO.getCxcdFechaVencimiento())
                        + "¬"
                        + (cuentasPorCobrarDetalladoTO.getCxcdCupoCredito() == null ? "B" : "S" + cuentasPorCobrarDetalladoTO.getCxcdCupoCredito().toString())
                        + "¬"
                        + (cuentasPorCobrarDetalladoTO.getCxcdDiasCredito() == null ? "B" : "S" + cuentasPorCobrarDetalladoTO.getCxcdDiasCredito().toString())
                        + "¬"
                        + (cuentasPorCobrarDetalladoTO.getCxcdDiasVencidos() == null ? "B" : "S" + cuentasPorCobrarDetalladoTO.getCxcdDiasVencidos().toString())
                        + "¬"
                        + (cuentasPorCobrarDetalladoTO.getCxcdRetencionNumero() == null ? "B" : "S" + cuentasPorCobrarDetalladoTO.getCxcdRetencionNumero())
                        + "¬"
                        + (cuentasPorCobrarDetalladoTO.getCxcdChequeNumero() == null ? "B" : "S" + cuentasPorCobrarDetalladoTO.getCxcdChequeNumero())
                        + "¬"
                        + (cuentasPorCobrarDetalladoTO.getCxcdTotal() == null ? "B" : "D" + cuentasPorCobrarDetalladoTO.getCxcdTotal().toString())
                        + "¬"
                        + (cuentasPorCobrarDetalladoTO.getCxcdRetencionValor() == null ? "B" : "D" + cuentasPorCobrarDetalladoTO.getCxcdRetencionValor().toString())
                        + "¬"
                        + (cuentasPorCobrarDetalladoTO.getCxcdAbono() == null ? "B" : "D" + cuentasPorCobrarDetalladoTO.getCxcdAbono().toString())
                        + "¬"
                        + (cuentasPorCobrarDetalladoTO.getCxcdSaldo() == null ? "B" : "D" + cuentasPorCobrarDetalladoTO.getCxcdSaldo().toString());
                if (esMARAQUATIC) {
                    cuerpo = cuerpo
                            + "¬"
                            + (cuentasPorCobrarDetalladoTO.getCliDiasCreditoAseguradora() == null ? "B" : "D" + cuentasPorCobrarDetalladoTO.getCliDiasCreditoAseguradora().toString())
                            + "¬"
                            + (cuentasPorCobrarDetalladoTO.getCliCupoCreditoAseguradora() == null ? "B" : "D" + cuentasPorCobrarDetalladoTO.getCliCupoCreditoAseguradora().toString())
                            + "¬"
                            + (cuentasPorCobrarDetalladoTO.getCliGarantia() == null ? "B" : "D" + cuentasPorCobrarDetalladoTO.getCliGarantia().toString());
                }

                listaCuerpo.add(cuerpo);
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteCarListaCuentasPorCobrarGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte,
            List<CarCuentasPorPagarCobrarGeneralTO> listado, String sector, String fechaHasta) {
        java.util.List<ReporteCuentasPorCobrarGeneral> reporteListaCuentasPorCobrarGeneral = new java.util.ArrayList();
        ReporteCuentasPorCobrarGeneral itemReporte = null;
        for (CarCuentasPorPagarCobrarGeneralTO carCobrarGeneral : listado) {
            itemReporte = new ReporteCuentasPorCobrarGeneral();
            itemReporte.setCodigoCP(sector);
            itemReporte.setFechaHasta(fechaHasta);

            itemReporte.setCxpgCodigo(carCobrarGeneral.getCxpgCodigo());
            itemReporte.setCxpgNombre(carCobrarGeneral.getCxpgNombre());
            itemReporte.setCxpgSaldo(carCobrarGeneral.getCxpgSaldo());
            reporteListaCuentasPorCobrarGeneral.add(itemReporte);
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteListaCuentasPorCobrarGeneral);
    }

    @Override
    public Map<String, Object> exportarReporteCarListaCuentasPorCobrarGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CarCuentasPorPagarCobrarGeneralTO> listado, String sector, String fechaHasta) {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SCuentas por cobrar general");
            listaCabecera.add("SCentro de producción: " + (sector != null ? sector : " "));
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SNombre" + "¬" + "SSaldo");
            for (CarCuentasPorPagarCobrarGeneralTO carCuentasPorCobrar : listado) {
                listaCuerpo.add((carCuentasPorCobrar.getCxpgCodigo() == null ? "B" : "S" + carCuentasPorCobrar.getCxpgCodigo())
                        + "¬"
                        + (carCuentasPorCobrar.getCxpgNombre() == null ? "B" : "S" + carCuentasPorCobrar.getCxpgNombre())
                        + "¬"
                        + (carCuentasPorCobrar.getCxpgSaldo() == null ? "B" : "D" + carCuentasPorCobrar.getCxpgSaldo().add(cero).toString())
                        + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteCuentasPorPagarAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, String cliente,
            List<CarCuentasPorCobrarSaldoAnticiposTO> listado, String sector, String fechaHasta) throws Exception {
        java.util.List<ReporteCuentasPorPagarAnticipos> reporteListaCuentasPorPagarAnticipo = new java.util.ArrayList();
        ReporteCuentasPorPagarAnticipos itemReporte = null;
        for (CarCuentasPorCobrarSaldoAnticiposTO carPagarAnticipo : listado) {
            itemReporte = new ReporteCuentasPorPagarAnticipos();
            itemReporte.setCodigoCP(sector);
            itemReporte.setFechaHasta(fechaHasta);

            itemReporte.setCxpgCodigo(carPagarAnticipo.getAntClienteCodigo());
            itemReporte.setCxpgNombre(carPagarAnticipo.getAntClienteRazonSocial());
            itemReporte.setCxpgSaldo(carPagarAnticipo.getAntValor());
            reporteListaCuentasPorPagarAnticipo.add(itemReporte);
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteListaCuentasPorPagarAnticipo);
    }

    @Override
    public Map<String, Object> exportarReporteCuentasPorPagarAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CarCuentasPorCobrarSaldoAnticiposTO> listado, String sector, String fechaHasta) {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SAnticipo de Clientes - Saldo Detallado");
            listaCabecera.add("SCentro de producción: " + (sector != null ? sector : " "));
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SPeriodo" + "¬" + "STipo" + "¬" + "SNúmero" + "¬" + "SSector" + "¬" + "SCentro de costo" + "¬" + "SFecha" + "¬" + "SIdentificación cliente" + "¬" + "SCódigo cliente" + "¬" + "SNombre cliente"
                    + "¬" + "SValor" + "¬" + "SObservaciones" + "¬" + "SPendiente" + "¬" + "SAnulado" + "¬" + "SReversado");
            for (CarCuentasPorCobrarSaldoAnticiposTO carCuentasPagarAnticipo : listado) {
                listaCuerpo.add((carCuentasPagarAnticipo.getAntPeriodo() == null ? "B" : "S" + carCuentasPagarAnticipo.getAntPeriodo())
                        + "¬"
                        + (carCuentasPagarAnticipo.getAntTipo() == null ? "B" : "S" + carCuentasPagarAnticipo.getAntTipo())
                        + "¬"
                        + (carCuentasPagarAnticipo.getAntNumero() == null ? "B" : "S" + carCuentasPagarAnticipo.getAntNumero())
                        + "¬"
                        + (carCuentasPagarAnticipo.getAntSector() == null ? "B" : "S" + carCuentasPagarAnticipo.getAntSector())
                        + "¬"
                        + (carCuentasPagarAnticipo.getAntCentroCosto() == null ? "B" : "S" + carCuentasPagarAnticipo.getAntCentroCosto())
                        + "¬"
                        + (carCuentasPagarAnticipo.getAntFecha() == null ? "B" : "S" + carCuentasPagarAnticipo.getAntFecha())
                        + "¬"
                        + (carCuentasPagarAnticipo.getAntClienteIdentificacion() == null ? "B" : "S" + carCuentasPagarAnticipo.getAntClienteIdentificacion())
                        + "¬"
                        + (carCuentasPagarAnticipo.getAntClienteCodigo() == null ? "B" : "S" + carCuentasPagarAnticipo.getAntClienteCodigo())
                        + "¬"
                        + (carCuentasPagarAnticipo.getAntClienteRazonSocial() == null ? "B" : "S" + carCuentasPagarAnticipo.getAntClienteRazonSocial())
                        + "¬"
                        + (carCuentasPagarAnticipo.getAntValor() == null ? "B" : "D" + carCuentasPagarAnticipo.getAntValor().add(cero).toString())
                        + "¬"
                        + (carCuentasPagarAnticipo.getAntObservaciones() == null ? "B" : "S" + carCuentasPagarAnticipo.getAntObservaciones())
                        + "¬"
                        + (!carCuentasPagarAnticipo.getAntPendiente() ? "B" : "S" + (carCuentasPagarAnticipo.getAntPendiente() ? "PENDIENTE" : "NO"))
                        + "¬"
                        + (!carCuentasPagarAnticipo.getAntAnulado() ? "B" : "S" + (carCuentasPagarAnticipo.getAntAnulado() ? "ANULADO" : "NO"))
                        + "¬"
                        + (!carCuentasPagarAnticipo.getAntReversado() ? "B" : "S" + (carCuentasPagarAnticipo.getAntReversado() ? "REVERSADO" : "NO"))
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteCuentasPorCobrarAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte,
            List<CarCuentasPorPagarCobrarAnticiposTO> listado, String sector, String fechaHasta, boolean verDetalle) throws Exception {

        java.util.List<ReporteCuentasPorPagarAnticipos> reporteListaCuentasPorCobrarAnticipo = new java.util.ArrayList();
        ReporteCuentasPorPagarAnticipos itemReporte = null;
        for (CarCuentasPorPagarCobrarAnticiposTO carCobrarAnticipo : listado) {
            itemReporte = new ReporteCuentasPorPagarAnticipos();
            itemReporte.setCodigoCP(sector);
            itemReporte.setFechaHasta(fechaHasta);

            itemReporte.setCxpgCodigo(carCobrarAnticipo.getCxpgCodigo());
            itemReporte.setCxpgNombre(carCobrarAnticipo.getCxpgNombre());
            itemReporte.setCxpgSaldo(carCobrarAnticipo.getCxpgSaldo());
            itemReporte.setCxpgAnticipo(carCobrarAnticipo.getCxpgAnticipos());
            itemReporte.setCxpgTotal(carCobrarAnticipo.getCxpgTotal());

            itemReporte.setCxppSaldoSinVencer(carCobrarAnticipo.getCxppSaldoSinVencer());
            itemReporte.setCxppSaldoVencido30(carCobrarAnticipo.getCxppSaldoVencido30());
            itemReporte.setCxppSaldoVencido60(carCobrarAnticipo.getCxppSaldoVencido60());
            itemReporte.setCxppSaldoVencido90(carCobrarAnticipo.getCxppSaldoVencido90());
            itemReporte.setCxppSaldoVencido120(carCobrarAnticipo.getCxppSaldoVencido120());
            itemReporte.setCxppSaldoVencidoMayor120(carCobrarAnticipo.getCxppSaldoVencidoMayor120());
            itemReporte.setVerDetalle(verDetalle);
            reporteListaCuentasPorCobrarAnticipo.add(itemReporte);
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteListaCuentasPorCobrarAnticipo);
    }

    @Override
    public File generarReporteCuentasPorCobrarAnticiposFile(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<CarCuentasPorPagarCobrarAnticiposTO> listado, String fechaHasta) throws Exception {

        java.util.List<ReporteCuentasPorPagarAnticipos> reporteListaCuentasPorCobrarAnticipo = new java.util.ArrayList();
        ReporteCuentasPorPagarAnticipos itemReporte = null;
        for (CarCuentasPorPagarCobrarAnticiposTO carCobrarAnticipo : listado) {
            itemReporte = new ReporteCuentasPorPagarAnticipos();
            itemReporte.setCodigoCP("");
            itemReporte.setFechaHasta(fechaHasta);

            itemReporte.setCxpgCodigo(carCobrarAnticipo.getCxpgCodigo());
            itemReporte.setCxpgNombre(carCobrarAnticipo.getCxpgNombre());
            itemReporte.setCxpgSaldo(carCobrarAnticipo.getCxpgSaldo());
            itemReporte.setCxpgAnticipo(carCobrarAnticipo.getCxpgAnticipos());
            itemReporte.setCxpgTotal(carCobrarAnticipo.getCxpgTotal());
            reporteListaCuentasPorCobrarAnticipo.add(itemReporte);
        }
        return genericReporteService.generarFile(modulo, "reportCuentasPorCobrarAnticipos.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteListaCuentasPorCobrarAnticipo);
    }

    @Override
    public Map<String, Object> exportarReporteCuentasPorCobrarAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CarCuentasPorPagarCobrarAnticiposTO> listado, String sector, String fechaHasta, boolean verDetalle) {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SCuentas por cobrar V.S. anticipos");
            listaCabecera.add("SCentro de producción: " + (sector != null ? sector : " "));
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            String cabecera = "SCódigo" + "¬" + "SNombre" + "¬" + "SSaldo" + "¬" + "SAnticipos" + "¬" + "STotal";

            if (verDetalle) {
                cabecera += "¬SSaldo sin vencer¬SSaldo vencido 30¬SSaldo vencido 60¬SSaldo vencido 90¬SSaldo vencido 120¬SSaldo vencido mayor 120";
            }
            listaCuerpo.add(cabecera);

            for (CarCuentasPorPagarCobrarAnticiposTO carCuentasCobrarAnticipo : listado) {
                String cuerpo = ((carCuentasCobrarAnticipo.getCxpgCodigo() == null ? "B" : "S" + carCuentasCobrarAnticipo.getCxpgCodigo())
                        + "¬"
                        + (carCuentasCobrarAnticipo.getCxpgNombre() == null ? "B" : "S" + carCuentasCobrarAnticipo.getCxpgNombre())
                        + "¬"
                        + (carCuentasCobrarAnticipo.getCxpgSaldo() == null ? "B" : "D" + carCuentasCobrarAnticipo.getCxpgSaldo().add(cero).toString())
                        + "¬"
                        + (carCuentasCobrarAnticipo.getCxpgAnticipos() == null ? "B" : "D" + carCuentasCobrarAnticipo.getCxpgAnticipos().add(cero).toString())
                        + "¬"
                        + (carCuentasCobrarAnticipo.getCxpgTotal() == null ? "B" : "D" + carCuentasCobrarAnticipo.getCxpgTotal().add(cero).toString()));
                if (verDetalle) {
                    cuerpo = cuerpo + "¬"
                            + (carCuentasCobrarAnticipo.getCxppSaldoSinVencer() == null ? "B" : "D" + carCuentasCobrarAnticipo.getCxppSaldoSinVencer().add(cero).toString())
                            + "¬"
                            + (carCuentasCobrarAnticipo.getCxppSaldoVencido30() == null ? "B" : "D" + carCuentasCobrarAnticipo.getCxppSaldoVencido30().add(cero).toString())
                            + "¬"
                            + (carCuentasCobrarAnticipo.getCxppSaldoVencido60() == null ? "B" : "D" + carCuentasCobrarAnticipo.getCxppSaldoVencido60().add(cero).toString())
                            + "¬"
                            + (carCuentasCobrarAnticipo.getCxppSaldoVencido90() == null ? "B" : "D" + carCuentasCobrarAnticipo.getCxppSaldoVencido90().add(cero).toString())
                            + "¬"
                            + (carCuentasCobrarAnticipo.getCxppSaldoVencido120() == null ? "B" : "D" + carCuentasCobrarAnticipo.getCxppSaldoVencido120().add(cero).toString())
                            + "¬"
                            + (carCuentasCobrarAnticipo.getCxppSaldoVencidoMayor120() == null ? "B" : "D" + carCuentasCobrarAnticipo.getCxppSaldoVencidoMayor120().add(cero).toString());
                }
                listaCuerpo.add(cuerpo);
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteCarFunPagos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte,
            List<CarFunPagosTO> listado, String sector, String fechaDesde, String fechaHasta, String proveedor) throws Exception {

        java.util.List<ReporteListadoPagos> listaPagos = new java.util.ArrayList();
        ReporteListadoPagos itemReporte = null;
        for (CarFunPagosTO carPagos : listado) {
            itemReporte = new ReporteListadoPagos();
            itemReporte.setCodigoCP(sector);
            itemReporte.setDesde(fechaDesde);
            itemReporte.setHasta(fechaHasta);
            itemReporte.setPagProveedor(proveedor);

            itemReporte.setPagNumeroSistema(carPagos.getPagNumeroSistema());
            itemReporte.setPagFecha(carPagos.getPagFecha());
            itemReporte.setPagProveedor(carPagos.getPagProveedor());
            itemReporte.setPagValor(carPagos.getPagValor());
            itemReporte.setPagObservaciones(carPagos.getPagObservaciones());
            itemReporte.setPagPendiente(carPagos.getPagPendiente());
            itemReporte.setPagAnulado(carPagos.getPagAnulado());
            listaPagos.add(itemReporte);
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaPagos);
    }

    @Override
    public Map<String, Object> exportarReporteCarFunPagos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CarFunPagosTO> listado,
            String sector, String fechaDesde, String fechaHasta, String proveedor) {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado de pagos");
            listaCabecera.add("SCentro de producción: " + (sector != null ? sector : " "));
            listaCabecera.add("SHasta: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SNúmero" + "¬" + "SFecha" + "¬" + "SProveedor" + "¬" + "SValor" + "¬" + "SObservaciones" + "¬" + "SPendiente" + "¬" + "SAnulado");
            for (CarFunPagosTO carFunPagos : listado) {
                listaCuerpo.add((carFunPagos.getPagNumeroSistema() == null ? "B" : "S" + carFunPagos.getPagNumeroSistema())
                        + "¬"
                        + (carFunPagos.getPagFecha() == null ? "B" : "S" + carFunPagos.getPagFecha())
                        + "¬"
                        + (carFunPagos.getPagProveedor() == null ? "B" : "S" + carFunPagos.getPagProveedor())
                        + "¬"
                        + (carFunPagos.getPagValor() == null ? "B" : "D" + carFunPagos.getPagValor().add(cero).toString())
                        + "¬"
                        + (carFunPagos.getPagObservaciones() == null ? "B" : "S" + carFunPagos.getPagObservaciones()
                        + "¬"
                        + (carFunPagos.getPagPendiente() == null ? "B" : "S" + (carFunPagos.getPagPendiente() ? "PENDIENTE" : "NO"))
                        + "¬"
                        + (carFunPagos.getPagAnulado() == null ? "B" : "S" + (carFunPagos.getPagAnulado() ? "ANULADO" : "NO")))
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteCarFunPagosDetalle(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte,
            List<CarFunPagosDetalleTO> listado, String sector, String fechaDesde, String fechaHasta, String proveedor, String formaPago) {

        java.util.List<ReporteListadoPagos> listaPagos = new java.util.ArrayList();
        ReporteListadoPagos itemReporte = null;
        for (CarFunPagosDetalleTO carPagos : listado) {
            itemReporte = new ReporteListadoPagos();
            itemReporte.setCodigoCP(sector);
            itemReporte.setDesde(fechaDesde);
            itemReporte.setHasta(fechaHasta);
            itemReporte.setPagProveedor(proveedor);

            itemReporte.setPagNumeroSistema(carPagos.getPagNumeroSistema());
            itemReporte.setPagFecha(carPagos.getPagFecha());
            itemReporte.setPagProveedor(carPagos.getPagProveedor());
            itemReporte.setPagValor(carPagos.getPagValor());
            itemReporte.setPagObservaciones(carPagos.getPagObservaciones());
            itemReporte.setPagPendiente(carPagos.getPagPendiente());
            itemReporte.setPagAnulado(carPagos.getPagAnulado());
            itemReporte.setFormaPago(carPagos.getPagFormaPago());
            listaPagos.add(itemReporte);
        }
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("formaPago", formaPago);
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, parametros, listaPagos);
    }

    @Override
    public Map<String, Object> exportarReporteCarFunPagosDetalle(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CarFunPagosDetalleTO> listado, String sector, String fechaDesde, String fechaHasta, String proveedor) {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado de pagos detalle");
            listaCabecera.add("SCentro de producción: " + (sector != null ? sector : " "));
            listaCabecera.add("SHasta: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SNúmero" + "¬" + "SFecha" + "¬" + "SProveedor" + "¬" + "SValor" + "¬" + "SObservaciones" + "¬" + "SPendiente" + "¬" + "SAnulado");
            for (CarFunPagosDetalleTO carFunPagos : listado) {
                listaCuerpo.add((carFunPagos.getPagNumeroSistema() == null ? "B" : "S" + carFunPagos.getPagNumeroSistema())
                        + "¬"
                        + (carFunPagos.getPagFecha() == null ? "B" : "S" + carFunPagos.getPagFecha())
                        + "¬"
                        + (carFunPagos.getPagProveedor() == null ? "B" : "S" + carFunPagos.getPagProveedor())
                        + "¬"
                        + (carFunPagos.getPagValor() == null ? "B" : "D" + carFunPagos.getPagValor().add(cero).toString())
                        + "¬"
                        + (carFunPagos.getPagObservaciones() == null ? "B" : "S" + carFunPagos.getPagObservaciones()
                        + "¬"
                        + (carFunPagos.getPagPendiente() == null ? "B" : "S" + (carFunPagos.getPagPendiente() ? "PENDIENTE" : "NO"))
                        + "¬"
                        + (carFunPagos.getPagAnulado() == null ? "B" : "S" + (carFunPagos.getPagAnulado() ? "ANULADO" : "NO")))
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteCarFunPagosDetalleForma(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CarFunPagosDetalleTO> listado, String sector, String fechaDesde, String fechaHasta, String proveedor, String formaPago) {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado de pagos detalle por forma de pago");
            listaCabecera.add("SCentro de producción: " + (sector != null ? sector : " "));
            listaCabecera.add("SForma de Pago: " + (formaPago != null ? formaPago : " "));
            listaCabecera.add("SHasta: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SNúmero" + "¬" + "SFecha" + "¬" + "SForma de Pago" + "¬" + "SProveedor" + "¬" + "SValor" + "¬" + "SObservaciones" + "¬" + "SPendiente" + "¬" + "SAnulado");
            for (CarFunPagosDetalleTO carFunPagos : listado) {
                listaCuerpo.add((carFunPagos.getPagNumeroSistema() == null ? "B" : "S" + carFunPagos.getPagNumeroSistema())
                        + "¬"
                        + (carFunPagos.getPagFecha() == null ? "B" : "S" + carFunPagos.getPagFecha())
                        + "¬"
                        + (carFunPagos.getPagFormaPago() == null ? "B" : "S" + carFunPagos.getPagFormaPago())
                        + "¬"
                        + (carFunPagos.getPagProveedor() == null ? "B" : "S" + carFunPagos.getPagProveedor())
                        + "¬"
                        + (carFunPagos.getPagValor() == null ? "B" : "D" + carFunPagos.getPagValor().add(cero).toString())
                        + "¬"
                        + (carFunPagos.getPagObservaciones() == null ? "B" : "S" + carFunPagos.getPagObservaciones()
                        + "¬"
                        + (carFunPagos.getPagPendiente() == null ? "B" : "S" + (carFunPagos.getPagPendiente() ? "PENDIENTE" : "NO"))
                        + "¬"
                        + (carFunPagos.getPagAnulado() == null ? "B" : "S" + (carFunPagos.getPagAnulado() ? "ANULADO" : "NO")))
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteCarFunCuentasPorPagarListadoCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte,
            List<CarFunCuentasPorPagarListadoComprasTO> listado, String sector, String fechaDesde, String fechaHasta, String proveedor) throws Exception {

        java.util.List<ReporteCarFunCuentasPorPagarListadoCompras> listaPagos = new java.util.ArrayList();
        ReporteCarFunCuentasPorPagarListadoCompras itemReporte = null;
        for (CarFunCuentasPorPagarListadoComprasTO carPagos : listado) {
            itemReporte = new ReporteCarFunCuentasPorPagarListadoCompras();
            itemReporte.setCodigoCP(sector);
            itemReporte.setFechaHasta(fechaHasta);

            itemReporte.setCxpdPeriodo(carPagos.getCxpdPeriodo());
            itemReporte.setCxpdMotivo(carPagos.getCxpdMotivo());
            itemReporte.setCxpdNumero(carPagos.getCxpdNumero());
            itemReporte.setCxpdProveedor(carPagos.getCxpdProveedor());
            itemReporte.setCxpdFechaEmision(carPagos.getCxpdFechaEmision());
            itemReporte.setCxpdFechaVencimiento(carPagos.getCxpdFechaVencimiento());
            itemReporte.setCxpdSaldo(carPagos.getCxpdSaldo());
            listaPagos.add(itemReporte);
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaPagos);
    }

    @Override
    public byte[] generarReporteCuentasPagadas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<CarFunCuentasPagadasListadoTO> listado, String sector, String fechaDesde, String fechaHasta, String proveedor) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public Map<String, Object> exportarReporteCuentasPorPagarListadoCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<CarFunCuentasPorPagarListadoComprasTO> listado, String sector, String fechaDesde, String fechaHasta, String proveedor) {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SCuentas por pagar - listado compras");
            listaCabecera.add("SCentro de producción: " + (sector != null ? sector : " "));
            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SPeriodo" + "¬" + "SMotivo" + "¬" + "SNúmero" + "¬" + "SCliente" + "¬" + "SFecha emisión" + "¬" + "SFecha vencimiento" + "¬" + "SSaldo");
            for (CarFunCuentasPorPagarListadoComprasTO carFunPagos : listado) {
                listaCuerpo.add((carFunPagos.getCxpdPeriodo() == null ? "B" : "S" + carFunPagos.getCxpdPeriodo())
                        + "¬"
                        + (carFunPagos.getCxpdMotivo() == null ? "B" : "S" + carFunPagos.getCxpdMotivo())
                        + "¬"
                        + (carFunPagos.getCxpdNumero() == null ? "B" : "S" + carFunPagos.getCxpdNumero())
                        + "¬"
                        + (carFunPagos.getCxpdProveedor() == null ? "B" : "S" + carFunPagos.getCxpdProveedor())
                        + "¬"
                        + (carFunPagos.getCxpdFechaEmision() == null ? "B" : "S" + carFunPagos.getCxpdFechaEmision()
                        + "¬"
                        + (carFunPagos.getCxpdFechaVencimiento() == null ? "B" : "S" + (carFunPagos.getCxpdFechaVencimiento()))
                        + "¬"
                        + (carFunPagos.getCxpdSaldo() == null ? "B" : "D" + carFunPagos.getCxpdSaldo().add(cero).toString()))
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarCuentasPagadas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<CarFunCuentasPagadasListadoTO> listado, String sector, String fechaDesde, String fechaHasta, String proveedor) {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SCuentas pagadas");
            listaCabecera.add("SCentro de producción: " + (sector != null ? sector : " "));
            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SPago" + "¬" + "SPeriodo" + "¬" + "SMotivo" + "¬" + "SNúmero" + "¬" + "SCliente" + "¬" + "SFecha emisión" + "¬" + "SFecha vencimiento" + "¬" + "SFecha aprobación" + "¬" + "SUsuario aprobador" + "¬" + "SSaldo");
            for (CarFunCuentasPagadasListadoTO carFunPagos : listado) {
                listaCuerpo.add(
                        (carFunPagos.getCxpdPago() == null ? "B" : "S" + carFunPagos.getCxpdPago())
                        + "¬"
                        + (carFunPagos.getCxpdPeriodo() == null ? "B" : "S" + carFunPagos.getCxpdPeriodo())
                        + "¬"
                        + (carFunPagos.getCxpdMotivo() == null ? "B" : "S" + carFunPagos.getCxpdMotivo())
                        + "¬"
                        + (carFunPagos.getCxpdNumero() == null ? "B" : "S" + carFunPagos.getCxpdNumero())
                        + "¬"
                        + (carFunPagos.getCxpdProveedor() == null ? "B" : "S" + carFunPagos.getCxpdProveedor())
                        + "¬"
                        + (carFunPagos.getCxpdFechaEmision() == null ? "B" : "S" + carFunPagos.getCxpdFechaEmision()
                        + "¬"
                        + (carFunPagos.getCxpdFechaVencimiento() == null ? "B" : "S" + (carFunPagos.getCxpdFechaVencimiento()))
                        + "¬"
                        + (carFunPagos.getCxpdFechaAprobacion() == null ? "B" : "S" + (carFunPagos.getCxpdFechaAprobacion()))
                        + "¬"
                        + (carFunPagos.getCxpdUsuarioAprobador() == null ? "B" : "S" + (carFunPagos.getCxpdUsuarioAprobador()))
                        + "¬"
                        + (carFunPagos.getCxpdSaldo() == null ? "B" : "D" + carFunPagos.getCxpdSaldo().add(cero).toString()))
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteCarListaCuentasPorPagarDetallado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte,
            List<CuentasPorPagarDetalladoTO> listado, String sector, String fechaHasta, String proveedor) throws Exception {

        java.util.List<ReporteCuentasPorPagarDetallado> listaPagos = new java.util.ArrayList();
        ReporteCuentasPorPagarDetallado itemReporte = null;
        for (CuentasPorPagarDetalladoTO carPagos : listado) {
            itemReporte = new ReporteCuentasPorPagarDetallado();
            itemReporte.setCodigoCP(sector);
            itemReporte.setFechaHasta(fechaHasta);
            itemReporte.setCxpdDocumento(carPagos.getCxpdDocumento());
            itemReporte.setCxpdPeriodo(carPagos.getCxpdPeriodo());
            itemReporte.setCxpdMotivo(carPagos.getCxpdMotivo());
            itemReporte.setCxpdNumero(carPagos.getCxpdNumero());
            itemReporte.setCxpdProveedor(carPagos.getCxpdProveedorRazonSocial());
            itemReporte.setCxpdObservaciones(carPagos.getCxpdObservaciones());
            itemReporte.setCxpdFechaEmision(carPagos.getCxpdFechaEmision());
            itemReporte.setCxpdFechaVencimiento(carPagos.getCxpdFechaVencimiento());
            itemReporte.setCxpdSaldo(carPagos.getCxpdSaldo());
            itemReporte.setCxpdZona(carPagos.getCxpdZona());

            itemReporte.setCxpdSaldoPorVencer(carPagos.getCxpdSaldoPorVencer());
            itemReporte.setCxpdSaldoVencido(carPagos.getCxpdSaldoVencido());
            itemReporte.setCxpdDiasCredito(carPagos.getCxpdDiasCredito());
            itemReporte.setCxpdDiasVencidos(carPagos.getCxpdDiasVencidos());
            listaPagos.add(itemReporte);
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaPagos);
    }

    @Override
    public Map<String, Object> exportarReporteCarListaCuentasPorPagarDetallado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<CuentasPorPagarDetalladoTO> listado, String sector, String fechaHasta, String proveedor) {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SCuentas por pagar detallado");
            listaCabecera.add("SCentro de producción: " + (sector != null ? sector : " "));
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SPeriodo" + "¬" + "SMotivo" + "¬" + "SNúmero" + "¬" + "SProveedor Codigo" + "¬" + "SRazon Social" + "¬" + "SDocumento" + "¬" + "SObservaciones" + "¬" + "SFecha emisión"
                    + "¬" + "SFecha vencimiento"
                    + "¬" + "SDías credito"
                    + "¬" + "SDías vencidos"
                    + "¬" + "SSaldo"
                    + "¬" + "SSaldo vencido"
                    + "¬" + "SSaldo por vencer"
                    + "¬" + "SZona");
            for (CuentasPorPagarDetalladoTO carFunPagos : listado) {
                listaCuerpo.add((carFunPagos.getCxpdPeriodo() == null ? "B" : "S" + carFunPagos.getCxpdPeriodo())
                        + "¬"
                        + (carFunPagos.getCxpdMotivo() == null ? "B" : "S" + carFunPagos.getCxpdMotivo())
                        + "¬"
                        + (carFunPagos.getCxpdNumero() == null ? "B" : "S" + carFunPagos.getCxpdNumero())
                        + "¬"
                        + (carFunPagos.getCxpdProveedorId() == null ? "B" : "S" + carFunPagos.getCxpdProveedorId())
                        + "¬"
                        + (carFunPagos.getCxpdProveedorRazonSocial() == null ? "B" : "S" + carFunPagos.getCxpdProveedorRazonSocial())
                        + "¬"
                        + (carFunPagos.getCxpdDocumento() == null ? "B" : "S" + carFunPagos.getCxpdDocumento())
                        + "¬"
                        + (carFunPagos.getCxpdProveedorRazonSocial() == null ? "B" : "S" + carFunPagos.getCxpdProveedorRazonSocial() + (carFunPagos.getCxpdObservaciones() == null ? "" : " - " + carFunPagos.getCxpdObservaciones()))
                        + "¬"
                        + (carFunPagos.getCxpdFechaEmision() == null ? "B" : "S" + carFunPagos.getCxpdFechaEmision())
                        + "¬"
                        + (carFunPagos.getCxpdFechaVencimiento() == null ? "B" : "S" + (carFunPagos.getCxpdFechaVencimiento()))
                        + "¬"
                        + (carFunPagos.getCxpdDiasCredito() == null ? "B" : "I" + carFunPagos.getCxpdDiasCredito())
                        + "¬"
                        + (carFunPagos.getCxpdDiasVencidos() == null ? "B" : "I" + carFunPagos.getCxpdDiasVencidos())
                        + "¬"
                        + (carFunPagos.getCxpdSaldo() == null ? "B" : "D" + carFunPagos.getCxpdSaldo().add(cero).toString())
                        + "¬"
                        + (carFunPagos.getCxpdSaldoVencido() == null ? "B" : "D" + carFunPagos.getCxpdSaldoVencido().add(cero).toString())
                        + "¬"
                        + (carFunPagos.getCxpdSaldoPorVencer() == null ? "B" : "D" + carFunPagos.getCxpdSaldoPorVencer().add(cero).toString())
                        + "¬"
                        + (carFunPagos.getCxpdZona() == null ? "B" : "S" + carFunPagos.getCxpdZona())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteCarListaCuentasPorPagarGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte,
            List<CarCuentasPorPagarCobrarGeneralTO> listado, String sector, String fechaHasta, String proveedor) throws Exception {

        java.util.List<ReporteCuentasPorPagarGeneral> listaCuentas = new java.util.ArrayList();
        ReporteCuentasPorPagarGeneral itemReporte = null;
        for (CarCuentasPorPagarCobrarGeneralTO carCuentas : listado) {
            itemReporte = new ReporteCuentasPorPagarGeneral();
            itemReporte.setCodigoCP(sector);
            itemReporte.setFechaHasta(fechaHasta);

            itemReporte.setCxpgCodigo(carCuentas.getCxpgCodigo());
            itemReporte.setCxpgNombre(carCuentas.getCxpgNombre());
            itemReporte.setCxpgSaldo(carCuentas.getCxpgSaldo());
            listaCuentas.add(itemReporte);
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaCuentas);
    }

    @Override
    public Map<String, Object> exportarReporteCarListaCuentasPorPagarGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<CarCuentasPorPagarCobrarGeneralTO> listado, String sector, String fechaHasta) {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SCuentas por pagar general");
            listaCabecera.add("SCentro de producción: " + (sector != null ? sector : " "));
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SNombre" + "¬" + "SSaldo");
            for (CarCuentasPorPagarCobrarGeneralTO carFunPagos : listado) {
                listaCuerpo.add((carFunPagos.getCxpgCodigo() == null ? "B" : "S" + carFunPagos.getCxpgCodigo())
                        + "¬"
                        + (carFunPagos.getCxpgNombre() == null ? "B" : "S" + carFunPagos.getCxpgNombre())
                        + "¬"
                        + (carFunPagos.getCxpgSaldo() == null ? "B" : "D" + carFunPagos.getCxpgSaldo().add(cero).toString())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteCarListaCuentasPorPagarSaldoAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte,
            List<CarCuentasPorPagarSaldoAnticiposTO> listado, String sector, String fechaDesde, String fechaHasta, String proveedor) throws Exception {

        java.util.List<ReporteCuentasPorPagarCobrarSaldoAnticipos> listaCuentas = new java.util.ArrayList();
        ReporteCuentasPorPagarCobrarSaldoAnticipos itemReporte = null;
        for (CarCuentasPorPagarSaldoAnticiposTO carCuentas : listado) {
            String razonSocial = (carCuentas.getAntPeriodo() != null && carCuentas.getAntPeriodo().contains("SUBTOTAL") ? ""
                    : (carCuentas.getAntProveedorRazonSocial() == null ? "" : carCuentas.getAntProveedorRazonSocial()));
            String cp = (carCuentas.getAntPeriodo() != null && carCuentas.getAntPeriodo().contains("SUBTOTAL") ? ""
                    : (carCuentas.getAntSector() == null ? "" : carCuentas.getAntSector()));

            itemReporte = new ReporteCuentasPorPagarCobrarSaldoAnticipos();
            itemReporte.setFechaHasta(fechaHasta);
            itemReporte.setFechaDesde(fechaDesde);
            itemReporte.setAntPeriodo(carCuentas.getAntPeriodo());
            itemReporte.setAntTipo(carCuentas.getAntTipo());
            itemReporte.setAntNumero(carCuentas.getAntNumero());
            itemReporte.setAntSector(cp);
            itemReporte.setAntFecha(carCuentas.getAntFecha());
            itemReporte.setCodigoCP(cp);
            itemReporte.setAntProveedorRazonSocial(razonSocial);
            itemReporte.setAntValor(carCuentas.getAntValor());
            listaCuentas.add(itemReporte);
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaCuentas);
    }

    @Override
    public Map<String, Object> exportarReporteCarListaCuentasPorPagarSaldoAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<CarCuentasPorPagarSaldoAnticiposTO> listado, String sector, String fechaDesde, String fechaHasta) {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add(fechaDesde != null ? "SListado anticipo proveedor historico" : "SCuentas por pagar saldo anticipo");
            listaCabecera.add("SCentro de producción: " + (sector != null ? sector : " "));
            listaCabecera.add("SHasta: " + fechaHasta);
            if (fechaDesde != null) {
                listaCabecera.add("SDesde: " + fechaDesde);
            }
            listaCabecera.add("S");
            listaCuerpo.add("SPeriodo" + "¬" + "STipo" + "¬" + "SNúmero" + "¬" + "SSector" + "¬" + "SCentro de costo" + "¬" + "SFecha" + "¬" + "SProveedor identificación" + "¬" + "SCódigo proveedor"
                    + "¬" + "SNombre proveedor" + "¬" + "SValor" + "¬" + "SObservaciones" + "¬" + "SPendiente" + "¬" + "SAnulado");
            for (CarCuentasPorPagarSaldoAnticiposTO carFunCuentas : listado) {
                listaCuerpo.add((carFunCuentas.getAntPeriodo() == null ? "B" : "S" + carFunCuentas.getAntPeriodo())
                        + "¬"
                        + (carFunCuentas.getAntTipo() == null ? "B" : "S" + carFunCuentas.getAntTipo())
                        + "¬"
                        + (carFunCuentas.getAntNumero() == null ? "B" : "S" + carFunCuentas.getAntNumero())
                        + "¬"
                        + (carFunCuentas.getAntPeriodo() != null && carFunCuentas.getAntPeriodo().contains("SUBTOTAL") ? "B"
                        : (carFunCuentas.getAntSector() == null ? "B" : "S" + carFunCuentas.getAntSector()))
                        + "¬"
                        + (carFunCuentas.getAntPeriodo() != null && carFunCuentas.getAntPeriodo().contains("SUBTOTAL") ? "B"
                        : (carFunCuentas.getAntCentroCosto() == null ? "B" : "S" + carFunCuentas.getAntCentroCosto()))
                        + "¬"
                        + (carFunCuentas.getAntFecha() == null ? "B" : "S" + carFunCuentas.getAntFecha())
                        + "¬"
                        + (carFunCuentas.getAntPeriodo() != null && carFunCuentas.getAntPeriodo().contains("SUBTOTAL") ? "B"
                        : (carFunCuentas.getAntProveedorIdentificacion() == null ? "B" : "S" + carFunCuentas.getAntProveedorIdentificacion()))
                        + "¬"
                        + (carFunCuentas.getAntPeriodo() != null && carFunCuentas.getAntPeriodo().contains("SUBTOTAL") ? "B"
                        : (carFunCuentas.getAntProveedorCodigo() == null ? "B" : "S" + carFunCuentas.getAntProveedorCodigo()))
                        + "¬"
                        + (carFunCuentas.getAntPeriodo() != null && carFunCuentas.getAntPeriodo().contains("SUBTOTAL") ? "B"
                        : (carFunCuentas.getAntProveedorRazonSocial() == null ? "B" : "S" + carFunCuentas.getAntProveedorRazonSocial()))
                        + "¬"
                        + (carFunCuentas.getAntValor() == null ? "B" : "D" + carFunCuentas.getAntValor().add(cero).toString())
                        + "¬"
                        + (carFunCuentas.getAntObservaciones() == null ? "B" : "S" + carFunCuentas.getAntObservaciones())
                        + "¬"
                        + (carFunCuentas.getAntPendiente() == null || !carFunCuentas.getAntPendiente() ? "B" : "S" + (carFunCuentas.getAntPendiente() ? "PENDIENTE" : "NO"))
                        + "¬"
                        + (carFunCuentas.getAntAnulado() == null || !carFunCuentas.getAntAnulado() ? "B" : "S" + (carFunCuentas.getAntAnulado() ? "ANULADO" : "NO"))
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteCarCuentasPorPagarCobrarAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte,
            List<CarCuentasPorPagarCobrarAnticiposTO> listado, String sector, String fechaHasta, boolean verDetalle) {

        java.util.List<ReporteCuentasPorPagarAnticipos> reporteListaCuentasPorCobrarAnticipo = new java.util.ArrayList();
        ReporteCuentasPorPagarAnticipos itemReporte = null;
        for (CarCuentasPorPagarCobrarAnticiposTO carCobrarAnticipo : listado) {
            itemReporte = new ReporteCuentasPorPagarAnticipos();
            itemReporte.setCodigoCP(sector);
            itemReporte.setFechaHasta(fechaHasta);

            itemReporte.setCxpgCodigo(carCobrarAnticipo.getCxpgCodigo());
            itemReporte.setCxpgNombre(carCobrarAnticipo.getCxpgNombre());
            itemReporte.setCxpgSaldo(carCobrarAnticipo.getCxpgSaldo());
            itemReporte.setCxpgAnticipo(carCobrarAnticipo.getCxpgAnticipos());
            itemReporte.setCxpgTotal(carCobrarAnticipo.getCxpgTotal());

            itemReporte.setCxppSaldoSinVencer(carCobrarAnticipo.getCxppSaldoSinVencer());
            itemReporte.setCxppSaldoVencido30(carCobrarAnticipo.getCxppSaldoVencido30());
            itemReporte.setCxppSaldoVencido60(carCobrarAnticipo.getCxppSaldoVencido60());
            itemReporte.setCxppSaldoVencido90(carCobrarAnticipo.getCxppSaldoVencido90());
            itemReporte.setCxppSaldoVencido120(carCobrarAnticipo.getCxppSaldoVencido120());
            itemReporte.setCxppSaldoVencidoMayor120(carCobrarAnticipo.getCxppSaldoVencidoMayor120());
            itemReporte.setVerDetalle(verDetalle);
            reporteListaCuentasPorCobrarAnticipo.add(itemReporte);
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteListaCuentasPorCobrarAnticipo);
    }

    @Override
    public File generarReporteCarCuentasPorPagarCobrarAnticiposFile(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<CarCuentasPorPagarCobrarAnticiposTO> listado, String fechaHasta) {

        java.util.List<ReporteCuentasPorPagarAnticipos> reporteListaCuentasPorCobrarAnticipo = new java.util.ArrayList();
        ReporteCuentasPorPagarAnticipos itemReporte = null;
        for (CarCuentasPorPagarCobrarAnticiposTO carCobrarAnticipo : listado) {
            itemReporte = new ReporteCuentasPorPagarAnticipos();
            itemReporte.setCodigoCP("");
            itemReporte.setFechaHasta(fechaHasta);

            itemReporte.setCxpgCodigo(carCobrarAnticipo.getCxpgCodigo());
            itemReporte.setCxpgNombre(carCobrarAnticipo.getCxpgNombre());
            itemReporte.setCxpgSaldo(carCobrarAnticipo.getCxpgSaldo());
            itemReporte.setCxpgAnticipo(carCobrarAnticipo.getCxpgAnticipos());
            itemReporte.setCxpgTotal(carCobrarAnticipo.getCxpgTotal());
            reporteListaCuentasPorCobrarAnticipo.add(itemReporte);
        }
        return genericReporteService.generarFile(modulo, "reportCuentasPorPagarAnticipos.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteListaCuentasPorCobrarAnticipo);
    }

    @Override
    public Map<String, Object> exportarReporteCuentasPorPagarCobrarAnticipos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<CarCuentasPorPagarCobrarAnticiposTO> listado, String sector, String fechaHasta, List<DetalleExportarFiltrado> listadoFiltrado) {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SCuentas por pagar V.S. anticipos");
            listaCabecera.add("SCentro de producción: " + (sector != null ? sector : " "));
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");

            String fila = "";
            String filaCabecera = "";
            for (DetalleExportarFiltrado cabecera : listadoFiltrado) {
                filaCabecera += "S" + cabecera.getHeaderName() + "¬";
            }
            listaCuerpo.add(filaCabecera);

            for (CarCuentasPorPagarCobrarAnticiposTO carCuentasCobrarAnticipo : listado) {
                fila = "";
                for (int i = 0; i < listadoFiltrado.size(); i++) {
                    switch (listadoFiltrado.get(i).getField()) {
                        case "cxpgCodigo":
                            fila += (carCuentasCobrarAnticipo.getCxpgCodigo() == null ? "B" : "S" + carCuentasCobrarAnticipo.getCxpgCodigo()) + "¬";
                            break;
                        case "cxpgNombre":
                            fila += (carCuentasCobrarAnticipo.getCxpgNombre() == null ? "B" : "S" + carCuentasCobrarAnticipo.getCxpgNombre()) + "¬";
                            break;
                        case "cxpgSaldo":
                            fila += (carCuentasCobrarAnticipo.getCxpgSaldo() == null ? "B" : "D" + carCuentasCobrarAnticipo.getCxpgSaldo().add(cero).toString()) + "¬";
                            break;
                        case "cxpgAnticipos":
                            fila += (carCuentasCobrarAnticipo.getCxpgAnticipos() == null ? "B" : "D" + carCuentasCobrarAnticipo.getCxpgAnticipos().add(cero).toString()) + "¬";
                            break;
                        case "cxppSaldoSinVencer":
                            fila += (carCuentasCobrarAnticipo.getCxppSaldoSinVencer() == null ? "B" : "D" + carCuentasCobrarAnticipo.getCxppSaldoSinVencer().add(cero).toString()) + "¬";
                            break;
                        case "cxppSaldoVencido30":
                            fila += (carCuentasCobrarAnticipo.getCxppSaldoVencido30() == null ? "B" : "D" + carCuentasCobrarAnticipo.getCxppSaldoVencido30().add(cero).toString()) + "¬";
                            break;
                        case "cxppSaldoVencido60":
                            fila += (carCuentasCobrarAnticipo.getCxppSaldoVencido60() == null ? "B" : "D" + carCuentasCobrarAnticipo.getCxppSaldoVencido60().add(cero).toString()) + "¬";
                            break;
                        case "cxppSaldoVencido90":
                            fila += (carCuentasCobrarAnticipo.getCxppSaldoVencido90() == null ? "B" : "D" + carCuentasCobrarAnticipo.getCxppSaldoVencido90().add(cero).toString()) + "¬";
                            break;
                        case "cxppSaldoVencido120":
                            fila += (carCuentasCobrarAnticipo.getCxppSaldoVencido120() == null ? "B" : "D" + carCuentasCobrarAnticipo.getCxppSaldoVencido120().add(cero).toString()) + "¬";
                            break;
                        case "cxppSaldoVencidoMayor120":
                            fila += (carCuentasCobrarAnticipo.getCxppSaldoVencidoMayor120() == null ? "B" : "D" + carCuentasCobrarAnticipo.getCxppSaldoVencidoMayor120().add(cero).toString()) + "¬";
                            break;
                        case "cxpgTotal":
                            fila += (carCuentasCobrarAnticipo.getCxpgTotal() == null ? "B" : "D" + carCuentasCobrarAnticipo.getCxpgTotal().add(cero).toString()) + "¬";
                            break;
                        default:
                            fila += "";
                            break;
                    }
                }
                listaCuerpo.add(fila);
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReportePorLoteCobros(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<String> numeroSistema, String empresa) throws Exception {
        List<ReporteCobros> listaReporteCobros = new ArrayList<>();
        for (int i = 0; i < numeroSistema.size(); i++) {
            List<String> comprobante = UtilsValidacion.separarComprobante(numeroSistema.get(i));

            CarListaCobrosClienteTO cabecera = cobrosDao.getCobrosConsultaCliente(empresa, comprobante.get(0), comprobante.get(1), comprobante.get(2));
            List<CarListaCobrosTO> listaCobros = cobrosDao.getCobrosConsultaDetalleVentas(empresa, comprobante.get(0), comprobante.get(2));
            List<CarListaPagosCobrosDetalleFormaTO> listadoDeFormasDeCobro = cobrosDao.getCobrosConsultaDetalleForma(empresa, comprobante.get(0), comprobante.get(2), false);
            int numeroMayor = listaCobros.size() >= listadoDeFormasDeCobro.size() ? listaCobros.size() : listadoDeFormasDeCobro.size();
            BigDecimal totalVenta = BigDecimal.ZERO;
            BigDecimal totalCobro = BigDecimal.ZERO;
            SisUsuario usuario = usuarioService.obtenerPorNick(usuarioEmpresaReporteTO.getUsrNick());
            for (int j = 0; j < numeroMayor; j++) {
                ReporteCobros reporteCobros = new ReporteCobros();
                //CABECERA
                reporteCobros.setNombre(usuario.getUsrNombre() + " " + usuario.getUsrApellido());
                reporteCobros.setNumeroCobro(comprobante.get(2));
                reporteCobros.setFecha(cabecera.getConFecha());
                reporteCobros.setId(cabecera.getCliRuc());
                reporteCobros.setCliente(cabecera.getCliRazonSocial());
                reporteCobros.setDireccion(cabecera.getCliDireccion());
                reporteCobros.setCobSaldoActual(cabecera.getCobSaldoActual());
                reporteCobros.setCobSaldoAnterior(cabecera.getCobSaldoAnterior());
                reporteCobros.setCobValor(cabecera.getCobValor());
                reporteCobros.setObservaciones(cabecera.getCliObservaciones());
                //DETALLE 1 - VENTA
                if (j < listaCobros.size()) {
                    reporteCobros.setPeriodo(listaCobros.get(j).getCxccPeriodo());
                    reporteCobros.setMotivo(listaCobros.get(j).getCxccMotivo());
                    reporteCobros.setNumero(listaCobros.get(j).getCxccNumero());
                    reporteCobros.setDocumento(listaCobros.get(j).getCxccDocumentoNumero());
                    reporteCobros.setSector(listaCobros.get(j).getCxccSector());
                    reporteCobros.setFechaDetalle(listaCobros.get(j).getCxccFechaEmision());
                    reporteCobros.setVence(listaCobros.get(j).getCxccFechaVencimiento());
                    reporteCobros.setTotal(listaCobros.get(j).getCxccTotal());
                    reporteCobros.setValor(listaCobros.get(j).getCxccAbonos());
                    totalVenta = totalVenta.add(listaCobros.get(j).getCxccAbonos());
                }
                //DETALLE 2 - COBROS
                if (j < listadoDeFormasDeCobro.size()) {
                    reporteCobros.setForma(listadoDeFormasDeCobro.get(j).getFpForma());
                    reporteCobros.setReferencia(listadoDeFormasDeCobro.get(j).getFpReferencia());
                    reporteCobros.setValorDetalleInferior(listadoDeFormasDeCobro.get(j).getFpValor());
                    totalCobro = totalCobro.add(listadoDeFormasDeCobro.get(j).getFpValor());
                    reporteCobros.setObservacionesDetalleInferior(listadoDeFormasDeCobro.get(j).getFpObservaciones());
                }
                //DETALLE TOTALES
                reporteCobros.setTotalDetalleSuperior(totalVenta);
                reporteCobros.setTotalDetalleInferior(totalCobro);
                reporteCobros.setAntTotal(totalVenta.subtract(totalCobro));
                listaReporteCobros.add(reporteCobros);
            }
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaReporteCobros);
    }

    @Override
    public byte[] generarReportePorPagos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<String> numeroSistema, String empresa) throws Exception {
        List<ReportePagos> listaReportePagos = new ArrayList<>();
        for (int i = 0; i < numeroSistema.size(); i++) {
            List<String> comprobante = UtilsValidacion.separarComprobante(numeroSistema.get(i));

            CarListaPagosProveedorTO cabecera = pagosDao.getPagosConsultaProveedor(empresa, comprobante.get(0), comprobante.get(1), comprobante.get(2));
            List<CarListaPagosTO> listaPagos = pagosDao.getPagosConsultaDetalleCompras(empresa, comprobante.get(0), comprobante.get(2));
            List<CarListaPagosCobrosDetalleFormaTO> listadoDeFormasDePago = pagosDao.getPagosConsultaDetalleForma(empresa, comprobante.get(0), comprobante.get(2));
            int numeroMayor = listaPagos.size() >= listadoDeFormasDePago.size() ? listaPagos.size() : listadoDeFormasDePago.size();
            BigDecimal totalVenta = BigDecimal.ZERO;
            BigDecimal totalPago = BigDecimal.ZERO;
            for (int j = 0; j < numeroMayor; j++) {
                ReportePagos reportePagos = new ReportePagos();
                //CABECERA
                reportePagos.setNumeroPago(comprobante.get(2));
                reportePagos.setFecha(cabecera.getConFecha());
                reportePagos.setProveedor(cabecera.getProvCodigo());
                reportePagos.setId(cabecera.getProvRuc());
                reportePagos.setNombre(cabecera.getProvRazonSocial());
                reportePagos.setDireccion(cabecera.getProvDireccion());
                reportePagos.setPagSaldoActual(cabecera.getPagSaldoActual());
                reportePagos.setPagSaldoAnterior(cabecera.getPagSaldoAnterior());
                reportePagos.setPagValor(cabecera.getPagValor());
                reportePagos.setObservaciones(cabecera.getProvObservaciones());
                //DETALLE 1 - COMPRA
                if (j < listaPagos.size()) {
                    reportePagos.setPeriodo(listaPagos.get(j).getCxppPeriodo());
                    reportePagos.setMotivo(listaPagos.get(j).getCxppMotivo());
                    reportePagos.setNumero(listaPagos.get(j).getCxppNumero());
                    reportePagos.setDocumento(listaPagos.get(j).getCxppDocumentoNumero());
                    reportePagos.setSector(listaPagos.get(j).getCxppSector());
                    reportePagos.setFechaDetalle(listaPagos.get(j).getCxppFechaEmision());
                    reportePagos.setVence(listaPagos.get(j).getCxppFechaVencimiento());
                    reportePagos.setTotal(listaPagos.get(j).getCxppTotal());
                    reportePagos.setValor(listaPagos.get(j).getCxppAbonos());
                    totalVenta = totalVenta.add(listaPagos.get(j).getCxppAbonos());
                }
                //DETALLE 2 - PAGOS
                if (j < listadoDeFormasDePago.size()) {
                    reportePagos.setForma(listadoDeFormasDePago.get(j).getFpForma());
                    reportePagos.setReferencia(listadoDeFormasDePago.get(j).getFpReferencia());
                    reportePagos.setValorDetalleInferior(listadoDeFormasDePago.get(j).getFpValor());
                    totalPago = totalPago.add(listadoDeFormasDePago.get(j).getFpValor());
                    reportePagos.setObservacionesDetalleInferior(listadoDeFormasDePago.get(j).getFpObservaciones());
                }
                //DETALLE TOTALES
                reportePagos.setTotalDetalleSuperior(totalVenta);
                reportePagos.setTotalDetalleInferior(totalPago);
                reportePagos.setAntTotal(totalVenta.subtract(totalPago));
                listaReportePagos.add(reportePagos);
            }
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaReportePagos);
    }

    @Override
    public void exportarTxtDebitoBancario(List<CuentasPorCobrarDetalladoTO> cuentasPorCobrarDetallado, String reporte, HttpServletResponse response, SisInfoTO sisInfoTO) throws Exception {
        response.setContentType("text/plain");
        response.addHeader("Content-disposition", "attachment; filename=" + "mificherotemporal");
        File tempFile = File.createTempFile("mificherotemporal", ".txt");
        BufferedWriter out = new BufferedWriter(new FileWriter(tempFile));
        List<String> lista = new ArrayList<>();
        switch (reporte) {
            case "CARTERAPICH":
                lista = generarArchivoCARTERAPICH(cuentasPorCobrarDetallado);
                break;
            case "COOPSR":
                lista = generarArchivoCOOPSR(cuentasPorCobrarDetallado);
                break;
            case "PACIFICO":
                lista = generarArchivoPACIFICO(cuentasPorCobrarDetallado);
                break;
            case "AUSTRO":
                lista = generarArchivoAUSTRO(cuentasPorCobrarDetallado);
                break;
        }
        if (lista == null || lista.isEmpty()) {
            throw new GeneralException("No hay documentos para exportar. Considere elegir otro rango de fechas.");
        }
        for (String item : lista) {
            out.write(item);
        }
        out.flush();
        out.close();
        ServletOutputStream servletStream = response.getOutputStream();
        InputStream in = new FileInputStream(tempFile);
        int bit;
        while ((bit = in.read()) != -1) {
            servletStream.write(bit);
        }
        servletStream.flush();
        servletStream.close();
        in.close();
    }

    private List<String> generarArchivoAUSTRO(List<CuentasPorCobrarDetalladoTO> cuentasPorCobrarDetallado) throws Exception {
        List<String> lista = new java.util.ArrayList();
        for (CuentasPorCobrarDetalladoTO cuentaPorCobrar : cuentasPorCobrarDetallado) {
            //if (cuentaPorCobrar.getCxcdNumero() != null && esUnRegistroDebitoBancario(cuentaPorCobrar) && cuentaPorCobrar.getCxcdVtaRecurrente() != null && cuentaPorCobrar.getCxcdVtaRecurrente() > 0) {
            if (cuentaPorCobrar.getCxcdNumero() != null && esUnRegistroDebitoBancario(cuentaPorCobrar)) {
                BigDecimal saldo = cuentaPorCobrar.getCxcdSaldo().multiply(new BigDecimal(100));
                int parteEntera = saldo.intValue();
                lista.add(
                        new String(
                                (convertirAEspacios("CO", 2, false) + "\t" // PA, CO
                                        + convertirAEspacios(cuentaPorCobrar.getCxcdClienteTipoId(), 2, false) + "\t" //C, R
                                        + convertirAEspacios(cuentaPorCobrar.getCxcdClienteId() != null ? cuentaPorCobrar.getCxcdClienteId() : "", 13, false) + "\t" //identificador
                                        + convertirAEspacios(cuentaPorCobrar.getCxcdClienteId() != null ? cuentaPorCobrar.getCxcdClienteId() : "", 13, false) + "\t" //contrapartida
                                        + convertirAEspacios(cuentaPorCobrar.getCxcdClienteRazonSocial() != null ? cuentaPorCobrar.getCxcdClienteRazonSocial().trim() : "", 50, false) + "\t" //beneficiario
                                        + convertirAEspacios(cuentaPorCobrar.getCxcdClienteTipoCuenta(), 3, false) + "\t" //CTE, AHO, TAR
                                        + convertirAEspacios(cuentaPorCobrar.getCxcdClienteNumeroCuenta(), 20, false) + "\t" //numero de cuenta
                                        + convertirAEspacios("USD", 3, false) + "\t" // moneda
                                        + convertirAEspacios(parteEntera + "", 13, true) + "\t" // valor
                                        + convertirAEspacios(cuentaPorCobrar.getCxcdClienteBanco(), 4, false) //codigo del banco
                                        + "\r\n").getBytes("ASCII"),
                                "ISO-8859-1"
                        )
                );
            }
        }
        return lista;
    }

    private List<String> generarArchivoPACIFICO(List<CuentasPorCobrarDetalladoTO> cuentasPorCobrarDetallado) throws Exception {
        List<String> lista = new java.util.ArrayList();
        for (CuentasPorCobrarDetalladoTO cuentaPorCobrar : cuentasPorCobrarDetallado) {
            //if (cuentaPorCobrar.getCxcdNumero() != null && cuentaPorCobrar.getCxcdVtaRecurrente() != null && cuentaPorCobrar.getCxcdVtaRecurrente() > 0) {
            if (cuentaPorCobrar.getCxcdNumero() != null) {
                String observacion = "PERIODO " + cuentaPorCobrar.getCxcdPeriodo();
                if (cuentaPorCobrar.getCxcdVtaObservaciones() != null && !cuentaPorCobrar.getCxcdVtaObservaciones().trim().equals("")) {
                    observacion = cuentaPorCobrar.getCxcdVtaObservaciones();
                }
                BigDecimal saldo = cuentaPorCobrar.getCxcdSaldo().multiply(new BigDecimal(100));
                int parteEntera = saldo.intValue();
                lista.add(
                        new String(
                                (convertirAEspacios("1", 1, false) //siempre 1 -> Columna 1-1
                                        + convertirAEspacios("OCP", 3, false) // Siempres OCP -> columna 2-4
                                        + convertirAEspacios("ZG", 2, false) // OC, ZG o SG -> columna 5-6
                                        + convertirAEspacios("", 2, false) // Tipo cuenta -> columna 7-8
                                        + convertirAEspacios("", 8, false) // Numero de cuenta -> columna 9-16
                                        + completarConCeros(parteEntera + "", 15) // Valor 13 enteros + 2 decimales -> columna 17-31
                                        + convertirAEspacios(cuentaPorCobrar.getCxcdClienteId() != null ? cuentaPorCobrar.getCxcdClienteId() : "", 15, false) // Codigo del cliente -> columna 32-46
                                        + convertirAEspacios(observacion, 20, false) // referencia -> columna 47-66
                                        + convertirAEspacios("RE", 2, false) // CU, RE -> columna 67-68
                                        + convertirAEspacios("USD", 3, false) // Moneda -> columna 69-71
                                        + convertirAEspacios(cuentaPorCobrar.getCxcdClienteRazonSocial() != null ? cuentaPorCobrar.getCxcdClienteRazonSocial().trim() : "", 30, false) // Nombre -> columna 72-101
                                        + convertirAEspacios("", 2, false) // Espacio -> columna 102-103
                                        + convertirAEspacios("", 2, false) // Espacio -> columna 103-105
                                        + convertirAEspacios(cuentaPorCobrar.getCxcdClienteTipoId(), 1, false) // C, R, P -> columna 106-106
                                        + convertirAEspacios(cuentaPorCobrar.getCxcdClienteId() != null ? cuentaPorCobrar.getCxcdClienteId() : "", 14, false) // Identificacion -> columna 107-120
                                        + "\r\n").getBytes("ASCII"),
                                "ISO-8859-1"
                        )
                );
            }
        }
        return lista;
    }

    private List<String> generarArchivoCOOPSR(List<CuentasPorCobrarDetalladoTO> cuentasPorCobrarDetallado) throws Exception {
        List<String> lista = new java.util.ArrayList();
        for (int i = 0; i < cuentasPorCobrarDetallado.size(); i++) {
            CuentasPorCobrarDetalladoTO cuentaPorCobrar = cuentasPorCobrarDetallado.get(i);
            //if (cuentaPorCobrar.getCxcdNumero() != null && cuentaPorCobrar.getCxcdVtaRecurrente() != null && cuentaPorCobrar.getCxcdVtaRecurrente() > 0) {
            if (cuentaPorCobrar.getCxcdNumero() != null) {
                int mes = 1;
                if (cuentaPorCobrar.getCxcdClienteId() != null) {
                    if (i > 0 && cuentaPorCobrar.getCxcdClienteId().equals(cuentasPorCobrarDetallado.get(i - 1).getCxcdClienteId())) {
                        mes = mes + 1;
                    }
                }
                String razonSocial = cuentaPorCobrar.getCxcdClienteRazonSocial() != null ? cuentaPorCobrar.getCxcdClienteRazonSocial().trim() : "";
                String clienteId= cuentaPorCobrar.getCxcdClienteId() != null ? cuentaPorCobrar.getCxcdClienteId() : "";
                mes = Integer.parseInt(cuentaPorCobrar.getCxcdFechaEmision().substring(5, 7));
                lista.add(
                        new String(
                                ("0201" + clienteId + "|"
                                        + clienteId + "|"
                                        + razonSocial + "|"
                                        + cuentaPorCobrar.getCxcdPeriodo().substring(0, 4) + "|"
                                        + mes + "|"
                                        + cuentaPorCobrar.getCxcdSaldo().add(new BigDecimal("0.00")) + "\r\n").getBytes("ASCII"),
                                "ISO-8859-1"
                        )
                );
            }
        }
        return lista;
    }

    private List<String> generarArchivoCARTERAPICH(List<CuentasPorCobrarDetalladoTO> cuentasPorCobrarDetallado) throws Exception {
        List<String> lista = new java.util.ArrayList();
        for (CuentasPorCobrarDetalladoTO cuentaPorCobrar : cuentasPorCobrarDetallado) {
            //if (cuentaPorCobrar.getCxcdNumero() != null && cuentaPorCobrar.getCxcdVtaRecurrente() != null && cuentaPorCobrar.getCxcdVtaRecurrente() > 0) {
            if (cuentaPorCobrar.getCxcdNumero() != null) {
                String observacion = "PERIODO " + cuentaPorCobrar.getCxcdPeriodo();
                if (cuentaPorCobrar.getCxcdVtaObservaciones() != null && !cuentaPorCobrar.getCxcdVtaObservaciones().trim().equals("")) {
                    observacion = cuentaPorCobrar.getCxcdVtaObservaciones();
                }
                BigDecimal saldo = cuentaPorCobrar.getCxcdSaldo().multiply(new BigDecimal(100));
                String razonSocial = cuentaPorCobrar.getCxcdClienteRazonSocial() != null ? cuentaPorCobrar.getCxcdClienteRazonSocial().trim() : "";
                int parteEntera = saldo.intValue();
                lista.add(
                        new String(
                                ("CO"
                                        + ";" + convertirAEspacios(cuentaPorCobrar.getCxcdClienteId() != null ? cuentaPorCobrar.getCxcdClienteId() : "", 20, false)
                                        + ";USD"
                                        + ";" + convertirAEspacios(parteEntera + "", 13, true)
                                        + ";REC"
                                        + ";" + convertirAEspacios("", 3, false)
                                        + ";" + convertirAEspacios("", 20, false)
                                        + ";" + convertirAEspacios(observacion, 40, false)
                                        + ";" + cuentaPorCobrar.getCxcdClienteTipoId()
                                        + ";" + convertirAEspacios(cuentaPorCobrar.getCxcdClienteId() != null ? cuentaPorCobrar.getCxcdClienteId() : "", 20, false)
                                        + ";" + razonSocial + "\r\n").getBytes("ASCII"),
                                "ISO-8859-1"
                        )
                );
            }
        }
        return lista;
    }

    public String convertirAEspacios(String campo, int longitudTotalDeEspacios, boolean ponerEspaciosALaIzquierda) {
        String valorConEspacios = "";
        campo = campo.trim();
        int longitudDelCampo = campo.length();
        int espaciosQueDeboAgregar = longitudTotalDeEspacios - longitudDelCampo;
        if (!ponerEspaciosALaIzquierda) {
            for (int i = 0; i < espaciosQueDeboAgregar; i++) {
                valorConEspacios += " ";
            }
            valorConEspacios = campo + valorConEspacios;
        } else {
            for (int i = 0; i < espaciosQueDeboAgregar; i++) {
                valorConEspacios += " ";
            }
            valorConEspacios = valorConEspacios + campo;
        }
        if (valorConEspacios.length() > longitudTotalDeEspacios) {
            valorConEspacios = valorConEspacios.substring(0, longitudTotalDeEspacios);
        }
        return valorConEspacios;
    }

    public String completarConCeros(String campo, int longitudTotalDeEspacios) {
        String valorConEspacios = "";
        campo = campo.trim();
        int longitudDelCampo = campo.length();
        int espaciosQueDeboAgregar = longitudTotalDeEspacios - longitudDelCampo;
        for (int i = 0; i < espaciosQueDeboAgregar; i++) {
            valorConEspacios += "0";
        }
        valorConEspacios = valorConEspacios + campo;
        return valorConEspacios;
    }

    public boolean esUnRegistroDebitoBancario(CuentasPorCobrarDetalladoTO cuentaPorCobrar) {
        return cuentaPorCobrar.getCxcdClienteBanco() != null && !cuentaPorCobrar.getCxcdClienteBanco().equals("")
                && cuentaPorCobrar.getCxcdClienteTipoCuenta() != null && !cuentaPorCobrar.getCxcdClienteTipoCuenta().equals("")
                && cuentaPorCobrar.getCxcdClienteNumeroCuenta() != null && !cuentaPorCobrar.getCxcdClienteNumeroCuenta().equals("");
    }

    @Override
    public byte[] generarReporteCarFunCobros(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, String cliente, List<CarFunCobrosTO> listado, String sector, String fechaDesde, String fechaHasta) {
        java.util.List<ReporteListadoCobros> reporteListadoCobroses = new java.util.ArrayList();
        ReporteListadoCobros reporteListadoCobros = null;
        for (CarFunCobrosTO carFunCobrosTO : listado) {
            reporteListadoCobros = new ReporteListadoCobros();
            reporteListadoCobros.setCodigoCP(sector);
            reporteListadoCobros.setDesde(fechaDesde);
            reporteListadoCobros.setHasta(fechaHasta);
            reporteListadoCobros.setCliente(cliente);

            reporteListadoCobros.setCobNumeroSistema(carFunCobrosTO.getCobNumeroSistema());
            reporteListadoCobros.setCobFecha(carFunCobrosTO.getCobFecha());
            reporteListadoCobros.setCobCliente(carFunCobrosTO.getCobCliente());
            reporteListadoCobros.setCobValor(carFunCobrosTO.getCobValor());
            reporteListadoCobros.setCobObservaciones(carFunCobrosTO.getCobObservaciones());
            reporteListadoCobros.setCobPendiente(carFunCobrosTO.getCobPendiente());
            reporteListadoCobros.setCobAnulado(carFunCobrosTO.getCobAnulado());
            reporteListadoCobroses.add(reporteListadoCobros);
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteListadoCobroses);
    }
}
