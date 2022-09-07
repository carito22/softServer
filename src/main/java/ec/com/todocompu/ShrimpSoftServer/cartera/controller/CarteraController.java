package ec.com.todocompu.ShrimpSoftServer.cartera.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.todocompu.ShrimpSoftServer.cartera.report.ReporteCarteraService;
import ec.com.todocompu.ShrimpSoftServer.cartera.service.CobrosAnticiposService;
import ec.com.todocompu.ShrimpSoftServer.cartera.service.CobrosFormaService;
import ec.com.todocompu.ShrimpSoftServer.cartera.service.CobrosService;
import ec.com.todocompu.ShrimpSoftServer.cartera.service.PagosAnticiposService;
import ec.com.todocompu.ShrimpSoftServer.cartera.service.PagosFormaService;
import ec.com.todocompu.ShrimpSoftServer.cartera.service.PagosService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarComboPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarContableTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorCobrarSaldoAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarCobrarAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarCobrarGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCuentasPorPagarSaldoAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCobrosDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCobrosSaldoAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCuentasPorCobrarListadoVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCuentasPorPagarListadoComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunPagosDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunPagosPruebaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunPagosSaldoAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunPagosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaCobrosClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaMayorAuxiliarClienteProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosCobrosDetalleAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosCobrosDetalleFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosCobrosAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosDetalleAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosDetalleComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosDetalleFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CuentasPorCobrarDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CuentasPorPagarDetalladoTO;
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
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.math.BigDecimal;
import java.util.ArrayList;

@RestController
@RequestMapping("/todocompuWS/carteraController")
public class CarteraController {

    @Autowired
    private CobrosFormaService cobrosFormaService;

    @Autowired
    private CobrosService cobrosService;

    @Autowired
    private CobrosAnticiposService cobrosAnticiposService;

    @Autowired
    private PagosService pagosService;

    @Autowired
    private PagosAnticiposService pagosAnticiposService;

    @Autowired
    private PagosFormaService pagosFormaService;

    @Autowired
    private ReporteCarteraService reporteCarteraService;

    @Autowired
    private EnviarCorreoService envioCorreoService;

    @Autowired
    private ContableService contableService;

    @RequestMapping("/generarReporteCuentasPorCobrarDetallado")
    public byte[] generarReporteCuentasPorCobrarDetallado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteCuentasPorCobrarDetallado> reporteCuentasPorCobrarDetallado = UtilsJSON
                .jsonToList(ReporteCuentasPorCobrarDetallado.class, map.get("reporteCuentasPorCobrarDetallado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteCarteraService.generarReporteCuentasPorCobrarDetallado(usuarioEmpresaReporteTO,
                    reporteCuentasPorCobrarDetallado);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCuentasPorPagarDetallado")
    public byte[] generarReporteCuentasPorPagarDetallado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteCuentasPorPagarDetallado> reporteCuentasPorPagarDetallado = UtilsJSON
                .jsonToList(ReporteCuentasPorPagarDetallado.class, map.get("reporteCuentasPorPagarDetallado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteCarteraService.generarReporteCuentasPorPagarDetallado(usuarioEmpresaReporteTO,
                    reporteCuentasPorPagarDetallado);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCuentasPorCobrarGeneral")
    public byte[] generarReporteCuentasPorCobrarGeneral(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteCuentasPorCobrarGeneral> reporteCuentasPorCobrarGeneral = UtilsJSON
                .jsonToList(ReporteCuentasPorCobrarGeneral.class, map.get("reporteCuentasPorCobrarGeneral"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteCarteraService.generarReporteCuentasPorCobrarGeneral(usuarioEmpresaReporteTO,
                    reporteCuentasPorCobrarGeneral);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCarFunCuentasPorCobrarListadoVentas")
    public byte[] generarReporteCarFunCuentasPorCobrarListadoVentas(@RequestBody String json,
            HttpServletResponse response) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteCarFunCuentasPorCobrarListadoVentas> reporteCarFunCuentasPorCobrarListadoVentas = UtilsJSON
                .jsonToList(ReporteCarFunCuentasPorCobrarListadoVentas.class,
                        map.get("reporteCarFunCuentasPorCobrarListadoVentas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteCarteraService.generarReporteCarFunCuentasPorCobrarListadoVentas(usuarioEmpresaReporteTO,
                    reporteCarFunCuentasPorCobrarListadoVentas);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCarFunCuentasPorPagarListadoCompras")
    public byte[] generarReporteCarFunCuentasPorPagarListadoCompras(@RequestBody String json,
            HttpServletResponse response) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteCarFunCuentasPorPagarListadoCompras> reporteCarFunCuentasPorPagarListadoCompras = UtilsJSON
                .jsonToList(ReporteCarFunCuentasPorPagarListadoCompras.class,
                        map.get("reporteCarFunCuentasPorPagarListadoCompras"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteCarteraService.generarReporteCarFunCuentasPorPagarListadoCompras(usuarioEmpresaReporteTO,
                    reporteCarFunCuentasPorPagarListadoCompras);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCuentasPorPagarGeneral")
    public byte[] generarReporteCuentasPorPagarGeneral(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteCuentasPorPagarGeneral> reporteCuentasPorPagarGeneral = UtilsJSON
                .jsonToList(ReporteCuentasPorPagarGeneral.class, map.get("reporteCuentasPorPagarGeneral"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteCarteraService.generarReporteCuentasPorPagarGeneral(usuarioEmpresaReporteTO,
                    reporteCuentasPorPagarGeneral);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCuentasPorPagarAnticipos")
    public byte[] generarReporteCuentasPorPagarAnticipos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteCuentasPorPagarAnticipos> reporteCuentasPorPagarAnticipos = UtilsJSON
                .jsonToList(ReporteCuentasPorPagarAnticipos.class, map.get("reporteCuentasPorPagarAnticipos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteCarteraService.generarReporteCuentasPorPagarAnticipos(usuarioEmpresaReporteTO,
                    reporteCuentasPorPagarAnticipos);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCuentasPorPagarSaldoAnticipos")
    public byte[] generarReporteCuentasPorPagarSaldoAnticipos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteCuentasPorPagarCobrarSaldoAnticipos> reporteCuentasPorPagarSaldoAnticipos = UtilsJSON.jsonToList(
                ReporteCuentasPorPagarCobrarSaldoAnticipos.class, map.get("reporteCuentasPorPagarSaldoAnticipos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteCarteraService.generarReporteCuentasPorPagarSaldoAnticipos(usuarioEmpresaReporteTO,
                    reporteCuentasPorPagarSaldoAnticipos);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCuentasPorCobrarSaldoAnticipos")
    public byte[] generarReporteCuentasPorCobrarSaldoAnticipos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteCuentasPorPagarCobrarSaldoAnticipos> reporteCuentasPorPagarSaldoAnticipos = UtilsJSON.jsonToList(
                ReporteCuentasPorPagarCobrarSaldoAnticipos.class, map.get("reporteCuentasPorPagarSaldoAnticipos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteCarteraService.generarReporteCuentasPorCobrarSaldoAnticipos(usuarioEmpresaReporteTO,
                    reporteCuentasPorPagarSaldoAnticipos);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCuentasPorCobrarAnticipos")
    public byte[] generarReporteCuentasPorCobrarAnticipos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteCuentasPorPagarAnticipos> reporteCuentasPorPagarAnticipos = UtilsJSON
                .jsonToList(ReporteCuentasPorPagarAnticipos.class, map.get("reporteCuentasPorPagarAnticipos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteCarteraService.generarReporteCuentasPorCobrarAnticipos(usuarioEmpresaReporteTO,
                    reporteCuentasPorPagarAnticipos);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCobros")
    public byte[] generarReporteCobros(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteCobros> reporteCobros = UtilsJSON.jsonToList(ReporteCobros.class, map.get("reporteCobros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteCarteraService.generarReporteCobros(usuarioEmpresaReporteTO, reporteCobros);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReportePagos")
    public byte[] generarReportePagos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReportePagos> reportePagos = UtilsJSON.jsonToList(ReportePagos.class, map.get("reportePagos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteCarteraService.generarReportePagos(usuarioEmpresaReporteTO, reportePagos);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteMayorAuxiliarProveedorCliente")
    public byte[] generarReporteMayorAuxiliarProveedorCliente(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteMayorAuxiliarProveedorCliente> reporteMayorAuxiliarProveedorCliente = UtilsJSON.jsonToList(
                ReporteMayorAuxiliarProveedorCliente.class, map.get("reporteMayorAuxiliarProveedorCliente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteCarteraService.generarReporteMayorAuxiliarProveedorCliente(usuarioEmpresaReporteTO,
                    reporteMayorAuxiliarProveedorCliente);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoPagos")
    public byte[] generarReporteListadoPagos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteListadoPagos> reporteListadoPagos = UtilsJSON.jsonToList(ReporteListadoPagos.class,
                map.get("reporteListadoPagos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteCarteraService.generarReporteListadoPagos(usuarioEmpresaReporteTO, reporteListadoPagos);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoCobros")
    public byte[] generarReporteListadoCobros(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteListadoCobros> reporteListadoCobros = UtilsJSON.jsonToList(ReporteListadoCobros.class,
                map.get("reporteListadoCobros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteCarteraService.generarReporteListadoCobros(usuarioEmpresaReporteTO, reporteListadoCobros);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarCarPagos")
    public CarContableTO insertarCarPagos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        CarPagosTO carPagosTO = UtilsJSON.jsonToObjeto(CarPagosTO.class, map.get("carPagosTO"));
        List<CarPagosDetalleComprasTO> carPagosDetalleComprasTOs = UtilsJSON.jsonToList(CarPagosDetalleComprasTO.class,
                map.get("carPagosDetalleComprasTOs"));
        List<CarPagosDetalleAnticiposTO> carPagosDetalleAnticiposTOs = UtilsJSON
                .jsonToList(CarPagosDetalleAnticiposTO.class, map.get("carPagosDetalleAnticiposTOs"));
        List<CarPagosDetalleFormaTO> carPagosDetalleFormaTOs = UtilsJSON.jsonToList(CarPagosDetalleFormaTO.class,
                map.get("carPagosDetalleFormaTOs"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            CarContableTO carContableTO = pagosService.insertarCarPagos(carPagosTO, carPagosDetalleComprasTOs,
                    carPagosDetalleAnticiposTOs, carPagosDetalleFormaTOs, new ArrayList<>(), sisInfoTO);
//			if (carContableTO.getMensaje().charAt(0) == 'T')
//				contableService.mayorizarDesmayorizarSql(new ConContablePK(carPagosTO.getUsrEmpresa(),
//						carContableTO.getContPeriodo(), carContableTO.getContTipo(), carContableTO.getContNumero()),
//						false, sisInfoTO);
            return carContableTO;
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getPagosConsultaProveedorTO")
    public CarListaPagosProveedorTO getPagosConsultaProveedorTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        try {
            return pagosService.getPagosConsultaProveedorTO(empresa, periodo, tipo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getPagosConsultaProveedorAnticipoTO")
    public CarListaPagosProveedorTO getPagosConsultaProveedorAnticipoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return pagosAnticiposService.getPagosConsultaProveedorAnticipoTO(empresa, periodo, tipo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getPagosConsultaDetalleComprasTO")
    public List<CarListaPagosTO> getPagosConsultaDetalleComprasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return pagosService.getPagosConsultaDetalleComprasTO(empresa, periodo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getPagosConsultaDetalleFormaTO")
    public List<CarListaPagosCobrosDetalleFormaTO> getPagosConsultaDetalleFormaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        try {
            return pagosService.getPagosConsultaDetalleFormaTO(empresa, periodo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getCarListaPagosTO")
    public List<CarListaPagosTO> getCarListaPagosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return pagosService.getCarListaPagosTO(empresa, proveedor);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCarFunPagosTO")
    public List<CarFunPagosTO> getCarFunPagosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        try {
            return pagosService.getCarFunPagosTO(empresa, sector, desde, hasta, proveedor, true);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getCarFunPagosPruebaTO")
    public List<CarFunPagosPruebaTO> getCarFunPagosPruebaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return pagosService.getCarFunPagosPruebaTO(empresa, desde, hasta, proveedor);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getCarFunPagosDetalleTO")
    public List<CarFunPagosDetalleTO> getCarFunPagosDetalleTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        String formaPago = UtilsJSON.jsonToObjeto(String.class, map.get("formaPago"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return pagosService.getCarFunPagosDetalleTO(empresa, sector, desde, hasta, proveedor, formaPago);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCarFunPagosSaldoAnticipoTO")
    public List<CarFunPagosSaldoAnticipoTO> getCarFunPagosSaldoAnticipoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return pagosAnticiposService.getCarFunPagosSaldoAnticipoTO(empresa, proveedor);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarCarCobros")
    public CarContableTO insertarCarCobros(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        CarCobrosTO carCobrosTO = UtilsJSON.jsonToObjeto(CarCobrosTO.class, map.get("carCobrosTO"));
        List<CarCobrosDetalleVentasTO> carCobrosDetalleVentasTOs = UtilsJSON.jsonToList(CarCobrosDetalleVentasTO.class,
                map.get("carCobrosDetalleVentasTOs"));
        List<CarCobrosDetalleAnticiposTO> carCobrosDetalleAnticiposTOs = UtilsJSON
                .jsonToList(CarCobrosDetalleAnticiposTO.class, map.get("carCobrosDetalleAnticiposTOs"));
        List<CarCobrosDetalleFormaTO> carCobrosDetalleFormaTOs = UtilsJSON.jsonToList(CarCobrosDetalleFormaTO.class,
                map.get("carCobrosDetalleFormaTOs"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            CarContableTO carContableTO = cobrosService.insertarCarCobros(carCobrosTO, carCobrosDetalleVentasTOs,
                    carCobrosDetalleAnticiposTOs, carCobrosDetalleFormaTOs, new ArrayList<>(), sisInfoTO);
//			if (carContableTO.getMensaje().charAt(0) == 'T')
//				contableService.mayorizarDesmayorizarSql(new ConContablePK(carCobrosTO.getUsrEmpresa(),
//						carContableTO.getContPeriodo(), carContableTO.getContTipo(), carContableTO.getContNumero()),
//						false, sisInfoTO);
            return carContableTO;
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getCobrosConsultaClienteTO")
    public CarListaCobrosClienteTO getCobrosConsultaClienteTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cobrosService.getCobrosConsultaClienteTO(empresa, periodo, tipo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCobrosConsultaClienteAnticipoTO")
    public CarListaCobrosClienteTO getCobrosConsultaClienteAnticipoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {

            return cobrosFormaService.getCobrosConsultaClienteAnticipoTO(empresa, periodo, tipo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCobrosConsultaDetalleVentasTO")
    public List<CarListaCobrosTO> getCobrosConsultaDetalleVentasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cobrosService.getCobrosConsultaDetalleVentasTO(empresa, periodo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCobrosConsultaDetalleFormaTO")
    public List<CarListaPagosCobrosDetalleFormaTO> getCobrosConsultaDetalleFormaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        boolean hayPostfechados = UtilsJSON.jsonToObjeto(boolean.class, map.get("hayPostfechados"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cobrosService.getCobrosConsultaDetalleFormaTO(empresa, periodo, numero, hayPostfechados);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCobrosConsultaDetalleAnticipo")
    public List<CarListaPagosCobrosDetalleAnticipoTO> getCobrosConsultaDetalleAnticipo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cobrosService.getCobrosConsultaDetalleAnticipo(empresa, periodo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getCarListaCobrosTO")
    public List<CarListaCobrosTO> getCarListaCobrosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cobrosService.getCarListaCobrosTO(empresa, proveedor);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getCarDeudaVencida")
    public java.math.BigDecimal getCarDeudaVencida(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cobrosService.getCarDeudaVencida(empresa, cliente);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCarFunCobrosTO")
    public List<CarFunCobrosTO> getCarFunCobrosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cobrosService.getCarFunCobrosTO(empresa, sector, desde, hasta, cliente, false);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCarFunCobrosDetalleTO")
    public List<CarFunCobrosDetalleTO> getCarFunCobrosDetalleTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String formaPago = UtilsJSON.jsonToObjeto(String.class, map.get("formaPago"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cobrosService.getCarFunCobrosDetalleTO(empresa, sector, desde, hasta, cliente, formaPago);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCarFunCobrosSaldoAnticipoTO")
    public List<CarFunCobrosSaldoAnticipoTO> getCarFunCobrosSaldoAnticipoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        try {
            return cobrosAnticiposService.getCarFunCobrosSaldoAnticipoTO(empresa, cliente);

        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionCarPagosForma")
    public String accionCarPagosForma(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        CarPagosCobrosFormaTO carPagosCobrosFormaTO = UtilsJSON.jsonToObjeto(CarPagosCobrosFormaTO.class,
                map.get("carPagosCobrosFormaTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return pagosFormaService.accionCarPagosForma(carPagosCobrosFormaTO, accion, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/accionCarCobrosForma")
    public String accionCarCobrosForma(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        CarPagosCobrosFormaTO carPagosCobrosFormaTO = UtilsJSON.jsonToObjeto(CarPagosCobrosFormaTO.class,
                map.get("carPagosCobrosFormaTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cobrosFormaService.accionCarCobrosForma(carPagosCobrosFormaTO, accion, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getCarComboPagosCobrosFormaTO")
    public List<CarComboPagosCobrosFormaTO> getCarComboPagosCobrosFormaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return pagosFormaService.getCarComboPagosCobrosFormaTO(empresa, accion);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCarListaPagosCobrosFormaTO")
    public List<CarListaPagosCobrosFormaTO> getCarListaPagosCobrosFormaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return pagosFormaService.getCarListaPagosCobrosFormaTO(empresa, accion);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCarPagosCobrosAnticipoTO")
    public CarPagosCobrosAnticipoTO getCarPagosCobrosAnticipoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return pagosAnticiposService.getCarPagosCobrosAnticipoTO(empresa, periodo, tipo, numero, accion);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getCarListaMayorAuxiliarClienteProveedorTO")
    public List<CarListaMayorAuxiliarClienteProveedorTO> getCarListaMayorAuxiliarClienteProveedorTO(
            @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cobrosService.getCarListaMayorAuxiliarClienteProveedorTO(empresa, sector, proveedor, desde, hasta,
                    accion, false);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCarListaCuentasPorPagarDetalladoTO")
    public List<CuentasPorPagarDetalladoTO> getCarListaCuentasPorPagarDetalladoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return pagosService.getCarListaCuentasPorPagarDetalladoTO(empresa, sector, proveedor, hasta, false, false, null);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCarListaCuentasPorPagarGeneralTO")
    public List<CarCuentasPorPagarCobrarGeneralTO> getCarListaCuentasPorPagarGeneralTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return pagosService.getCarListaCuentasPorPagarGeneralTO(empresa, sector, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCarListaCuentasPorPagarAnticiposTO")
    public List<CarCuentasPorPagarCobrarAnticiposTO> getCarListaCuentasPorPagarAnticiposTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return pagosService.getCarListaCuentasPorPagarAnticiposTO(empresa, sector, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getCarListaCuentasPorCobrarAnticiposTO")
    public List<CarCuentasPorPagarCobrarAnticiposTO> getCarListaCuentasPorCobrarAnticiposTO(@RequestBody String json) {

        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cobrosService.getCarListaCuentasPorCobrarAnticiposTO(empresa, sector, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getCarListaCuentasPorCobrarDetalladoTO")
    public List<CuentasPorCobrarDetalladoTO> getCarListaCuentasPorCobrarDetalladoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String grupo = UtilsJSON.jsonToObjeto(String.class, map.get("grupo"));
        boolean ichfa = UtilsJSON.jsonToObjeto(boolean.class, map.get("ichfa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cobrosService.getCarListaCuentasPorCobrarDetalladoTO(empresa, sector, cliente, desde, hasta, grupo, ichfa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getCarFunCuentasPorCobrarListadoVentasTO")
    public List<CarFunCuentasPorCobrarListadoVentasTO> getCarFunCuentasPorCobrarListadoVentasTO(
            @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cobrosService.getCarFunCuentasPorCobrarListadoVentasTO(empresa, sector, cliente, desde, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCarFunCuentasPorPagarListadoComprasTO")
    public List<CarFunCuentasPorPagarListadoComprasTO> getCarFunCuentasPorPagarListadoComprasTO(
            @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return pagosService.getCarFunCuentasPorPagarListadoComprasTO(empresa, sector, proveedor, desde, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getCarListaCuentasPorCobrarGeneralTO")
    public List<CarCuentasPorPagarCobrarGeneralTO> getCarListaCuentasPorCobrarGeneralTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cobrosService.getCarListaCuentasPorCobrarGeneralTO(empresa, sector, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarAnticiposPago")
    public String insertarAnticiposPago(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        CarPagosAnticipoTO carPagosAnticipoTO = UtilsJSON.jsonToObjeto(CarPagosAnticipoTO.class,
                map.get("carPagosAnticipoTO"));
        String observaciones = UtilsJSON.jsonToObjeto(String.class, map.get("observaciones"));
        String nombreProveedor = UtilsJSON.jsonToObjeto(String.class, map.get("nombreProveedor"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String sectorProveedor = UtilsJSON.jsonToObjeto(String.class, map.get("sectorProveedor"));
        String documento = UtilsJSON.jsonToObjeto(String.class, map.get("documento"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = pagosAnticiposService.insertarAnticiposPago(carPagosAnticipoTO, observaciones,
                    nombreProveedor, fecha, sectorProveedor, documento, new ArrayList<>(), sisInfoTO);
            if (mensajeTO.getMensaje().charAt(0) == 'T') {
                ConContable conContable = (ConContable) mensajeTO.getMap().get("conContable");
                contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
            }
            return mensajeTO.getMensaje();
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/insertarAnticiposCobro")
    public String insertarAnticiposCobro(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        CarCobrosAnticipoTO carCobrosAnticipoTO = UtilsJSON.jsonToObjeto(CarCobrosAnticipoTO.class,
                map.get("carCobrosAnticipoTO"));
        String observaciones = UtilsJSON.jsonToObjeto(String.class, map.get("observaciones"));
        String nombreCliente = UtilsJSON.jsonToObjeto(String.class, map.get("nombreCliente"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String sectorCliente = UtilsJSON.jsonToObjeto(String.class, map.get("sectorCliente"));
        String documento = UtilsJSON.jsonToObjeto(String.class, map.get("documento"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = cobrosAnticiposService.insertarAnticiposCobro(carCobrosAnticipoTO, observaciones,
                    nombreCliente, fecha, sectorCliente, documento, new ArrayList<>(), sisInfoTO);
            if (mensajeTO.getMensaje().charAt(0) == 'T') {
                ConContable conContable = (ConContable) mensajeTO.getMap().get("conContable");
                contableService.mayorizarDesmayorizarSql(conContable.getConContablePK(), false, sisInfoTO);
            }
            return mensajeTO.getMensaje();
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getCarListaCuentasPorPagarSaldoAnticiposTO")
    public List<CarCuentasPorPagarSaldoAnticiposTO> getCarListaCuentasPorPagarSaldoAnticiposTO(
            @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String proveedorCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("proveedorCodigo"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarCuentasPorPagarSaldoAnticiposTO> resultado = pagosAnticiposService.getCarListaCuentasPorPagarSaldoAnticiposTO(empresa, sector, proveedorCodigo,
                    hasta, false);
            //sumar totales para no afectar la funcion y hacer mas modificaciones
            if (resultado != null && !resultado.isEmpty()) {
                BigDecimal total = BigDecimal.ZERO;
                CarCuentasPorPagarSaldoAnticiposTO totales = new CarCuentasPorPagarSaldoAnticiposTO();
                totales.setAntProveedorRazonSocial("TOTAL");
                for (CarCuentasPorPagarSaldoAnticiposTO result : resultado) {
                    total = total.add(result.getAntValor());
                }
                totales.setAntValor(total);
                resultado.add(totales);
            }
            return resultado;
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getCarListaCuentasPorCobrarSaldoAnticiposTO")
    public List<CarCuentasPorCobrarSaldoAnticiposTO> getCarListaCuentasPorCobrarSaldoAnticiposTO(
            @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String clienteCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("clienteCodigo"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<CarCuentasPorCobrarSaldoAnticiposTO> resultado = cobrosAnticiposService.getCarListaCuentasPorCobrarSaldoAnticiposTO(empresa, sector, clienteCodigo,
                    hasta, false);
            //sumar totales para no afectar la funcion y hacer mas modificaciones
            if (resultado != null && !resultado.isEmpty()) {
                BigDecimal total = BigDecimal.ZERO;
                CarCuentasPorCobrarSaldoAnticiposTO totales = new CarCuentasPorCobrarSaldoAnticiposTO();
                totales.setAntClienteRazonSocial("TOTAL");
                for (CarCuentasPorCobrarSaldoAnticiposTO result : resultado) {
                    total = total.add(result.getAntValor());
                }
                totales.setAntValor(total);
                resultado.add(totales);
            }
            return resultado;
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getPagosConsultaDetalleAnticipo")
    public List<CarListaPagosCobrosDetalleAnticipoTO> getPagosConsultaDetalleAnticipo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return pagosService.getPagosConsultaDetalleAnticipo(empresa, periodo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }
}
