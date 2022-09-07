package ec.com.todocompu.ShrimpSoftServer.anexos.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.todocompu.ShrimpSoftServer.anexos.report.ReporteAnexosService;
import ec.com.todocompu.ShrimpSoftServer.anexos.report.ReporteComprobanteElectronicoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnexoNumeracionService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnuladosService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.CompraDetalleService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.CompraElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.CompraFormaPagoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.CompraReembolsoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.CompraService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.ComprobanteElectronicoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.ConceptoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.CuentasContablesService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.DpaEcuadorService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.EnviarComprobantesWSService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.FormaPagoAnexoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.PaisService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.PorcentajeIvaService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.ProvinciasService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.SustentoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.TipoComprobanteService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.TipoIdentificacionService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.TipoTransaccionService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.UrlWebServicesService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.VentaElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.VentaService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxAnuladoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxAnuladosTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraReembolsoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxComprobanteElectronicoUtilTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxConceptoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCuentasContablesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxEstablecimientoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFunListadoDevolucionIvaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFunListadoDevolucionIvaVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFunRegistroDatosCrediticiosTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaComprobanteAnuladoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaConsolidadoRetencionesVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaEstablecimientoRetencionesVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesFuenteIvaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesRentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentaElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentasPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListadoCompraElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListadoRetencionesVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionLineaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxPaisTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxProvinciaCantonTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxPuntoEmisionComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxSustentoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxSustentoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTalonResumenTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTalonResumenVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoIdentificacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoListaTransaccionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxUltimaAutorizacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.ComprobanteElectronico;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.anxUrlWebServicesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompra;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraFormaPago;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxFormaPago;
import ec.com.todocompu.ShrimpSoftUtils.anexos.report.ReporteObjetoAnexo;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisFirmaElectronica;
import ec.com.todocompu.ShrimpSoftUtils.sri.ws.autorizacion.RespuestaComprobante;

@RestController
@RequestMapping("/todocompuWS/anexosController")
public class AnexosController {

    @Autowired
    private AnuladosService anuladosService;

    @Autowired
    private CompraService compraService;

    @Autowired
    private CompraFormaPagoService compraFormaPagoService;

    @Autowired
    private CompraDetalleService compraDetalleService;

    @Autowired
    private CompraReembolsoService compraReembolsoService;

    @Autowired
    private ConceptoService conceptoService;

    @Autowired
    private EnviarComprobantesWSService enviarComprobantesWSService;

    @Autowired
    private CompraElectronicaService compraElectronicaService;

    @Autowired
    private CuentasContablesService cuentasContablesService;

    @Autowired
    private PaisService paisService;

    @Autowired
    private DpaEcuadorService dpaEcuadorService;

    @Autowired
    private UrlWebServicesService urlWebServicesService;

    @Autowired
    private TipoTransaccionService tipoTransaccionService;

    @Autowired
    private PorcentajeIvaService porcentajeIvaService;

    @Autowired
    private ProvinciasService provinciasService;

    @Autowired
    private FormaPagoAnexoService formaPagoAnexoService;

    @Autowired
    private TipoIdentificacionService tipoIdentificacionService;

    @Autowired
    private TipoComprobanteService tipoComprobanteService;

    @Autowired
    private AnexoNumeracionService numeracionService;

    @Autowired
    private SustentoService sustentoService;

    @Autowired
    private VentaService ventaService;

    @Autowired
    private VentaElectronicaService ventaElectronicaService;

    @Autowired
    private ComprobanteElectronicoService comprobanteElectronicoService;

    @Autowired
    private ReporteAnexosService reporteAnexosService;

    @Autowired
    private ReporteComprobanteElectronicoService reporteComprobanteElectronicoService;

    @Autowired
    private EnviarCorreoService envioCorreoService;

    @Autowired
    private GenericReporteService genericReporteService;

    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;

    @RequestMapping("/obtenerDocumentosPorCedulaRucMesAnio")
    public List<ComprobanteElectronico> obtenerDocumentosPorCedulaRucMesAnio(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String cedulaRuc = UtilsJSON.jsonToObjeto(String.class, map.get("cedulaRuc"));
        String mes = UtilsJSON.jsonToObjeto(String.class, map.get("mes"));
        String anio = UtilsJSON.jsonToObjeto(String.class, map.get("anio"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprobanteElectronicoService.obtenerDocumentosPorCedulaRucMesAnio(cedulaRuc, mes, anio);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getEstablecimientos")
    public List<AnxEstablecimientoComboTO> getEstablecimientos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventaService.getEstablecimientos(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPuntosEmision")
    public List<AnxPuntoEmisionComboTO> getPuntosEmision(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String establecimiento = UtilsJSON.jsonToObjeto(String.class, map.get("establecimiento"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventaService.getPuntosEmision(empresa, establecimiento);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getNumeroAutorizacion")
    public AnxNumeracionLineaTO getNumeroAutorizacion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String numeroRetencion = UtilsJSON.jsonToObjeto(String.class, map.get("numeroRetencion"));
        String numeroComprobante = UtilsJSON.jsonToObjeto(String.class, map.get("numeroComprobante"));
        String fechaVencimiento = UtilsJSON.jsonToObjeto(String.class, map.get("fechaVencimiento"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            AnxNumeracionLineaTO anxNumeracionLineaTO = ventaService.getNumeroAutorizacion(empresa, numeroRetencion,
                    numeroComprobante, fechaVencimiento);
            return anxNumeracionLineaTO;
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaAnxSustentoComboTO")
    public List<AnxSustentoComboTO> getListaAnxSustentoComboTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String tipoComprobante = UtilsJSON.jsonToObjeto(String.class, map.get("tipoComprobante"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return sustentoService.getListaAnxSustentoComboTO(tipoComprobante);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaAnxConceptoComboTO")
    public List<AnxConceptoComboTO> getListaAnxConceptoComboTO(@RequestBody SisInfoTO sisInfoTO) {
        try {
            return conceptoService.getListaAnxConceptoComboTO();
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaAnxConceptoComboTO2")
    public List<AnxConceptoComboTO> getListaAnxConceptoComboTO2(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String fechaRetencion = UtilsJSON.jsonToObjeto(String.class, map.get("fechaRetencion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return conceptoService.getListaAnxConceptoComboTO(fechaRetencion);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaAnxConceptoTO")
    public List<AnxConceptoComboTO> getListaAnxConceptoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String fechaRetencion = UtilsJSON.jsonToObjeto(String.class, map.get("fechaRetencion"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return conceptoService.getListaAnxConceptoTO(fechaRetencion, busqueda);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCodigoAnxTipoTransaccionTO")
    public String getCodigoAnxTipoTransaccionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String tipoIdentificacion = UtilsJSON.jsonToObjeto(String.class, map.get("tipoIdentificacion"));
        String tipoTransaccion = UtilsJSON.jsonToObjeto(String.class, map.get("tipoTransaccion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tipoTransaccionService.getCodigoAnxTipoTransaccionTO(tipoIdentificacion, tipoTransaccion);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaAnxTipoComprobanteComboTO")
    public List<AnxTipoComprobanteComboTO> getListaAnxTipoComprobanteComboTO(@RequestBody String json) {

        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String codigoTipoTransaccion = UtilsJSON.jsonToObjeto(String.class, map.get("codigoTipoTransaccion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tipoComprobanteService.getListaAnxTipoComprobanteComboTO(codigoTipoTransaccion);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaAnxTipoComprobanteComboTOCompleto")
    public List<AnxTipoComprobanteComboTO> getListaAnxTipoComprobanteComboTOCompleto(@RequestBody SisInfoTO sisInfoTO) {
        try {
            return tipoComprobanteService.getListaAnxTipoComprobanteComboTOCompleto();
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getValorAnxPorcentajeIvaTO")
    public BigDecimal getValorAnxPorcentajeIvaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String fechaFactura = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFactura"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return porcentajeIvaService.getValorAnxPorcentajeIvaTO(fechaFactura);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getValorAnxMontoMaximoConsumidorFinalTO")
    public BigDecimal getValorAnxMontoMaximoConsumidorFinalTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String fechaFactura = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFactura"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return porcentajeIvaService.getValorAnxMontoMaximoConsumidorFinalTO(fechaFactura);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoCompraTO")
    public AnxCompraTO getAnexoCompraTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroCompra = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCompra"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraService.getAnexoCompraTO(empresa, periodo, motivo, numeroCompra);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoCompraDetalleTO")
    public List<AnxCompraDetalleTO> getAnexoCompraDetalleTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroCompra = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCompra"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraDetalleService.getAnexoCompraDetalleTO(empresa, periodo, motivo, numeroCompra);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoCompraReembolsoTOs")
    public List<AnxCompraReembolsoTO> getAnexoCompraReembolsoTOs(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroCompra = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCompra"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraReembolsoService.getAnexoCompraReembolsoTOs(empresa, periodo, motivo, numeroCompra);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoVentaTO")
    public AnxVentaTO getAnexoVentaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroVenta = UtilsJSON.jsonToObjeto(String.class, map.get("numeroVenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventaService.getAnexoVentaTO(empresa, periodo, motivo, numeroVenta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoCompra")
    public AnxCompra getAnexoCompra(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroCompra = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCompra"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraService.getAnexoCompra(empresa, periodo, motivo, numeroCompra);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoListaRetencionesTO")
    public List<AnxListaRetencionesTO> getAnexoListaRetencionesTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraService.getAnexoListaRetencionesTO(empresa, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoListaRetencionesRentaTO")
    public List<AnxListaRetencionesRentaTO> getAnexoListaRetencionesRentaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String pOrden = UtilsJSON.jsonToObjeto(String.class, map.get("pOrden"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraService.getAnexoListaRetencionesRentaTO(empresa, fechaDesde, fechaHasta, pOrden);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoListaRetencionesFuenteIvaTO")
    public List<AnxListaRetencionesFuenteIvaTO> getAnexoListaRetencionesFuenteIvaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraService.getAnexoListaRetencionesFuenteIvaTO(empresa, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoFunListadoRetencionesPorNumero")
    public List<AnxListaRetencionesTO> getAnexoFunListadoRetencionesPorNumero(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String parametroEstado = UtilsJSON.jsonToObjeto(String.class, map.get("parametroEstado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraService.getAnexoFunListadoRetencionesPorNumero(empresa, fechaDesde, fechaHasta,
                    parametroEstado);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnxListaComprobanteAnuladoTO")
    public List<AnxListaComprobanteAnuladoTO> getAnxListaComprobanteAnuladoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anuladosService.getAnxListaComprobanteAnuladoTO(empresa, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnxListaConsolidadoRetencionesVentasTO")
    public List<AnxListaConsolidadoRetencionesVentasTO> getAnxListaConsolidadoRetencionesVentasTO(
            @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventaService.getAnxListaConsolidadoRetencionesVentasTO(empresa, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnxListaEstablecimientoRetencionesVentasTO")
    public List<AnxListaEstablecimientoRetencionesVentasTO> getAnxListaEstablecimientoRetencionesVentasTO(
            @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventaService.getAnxListaEstablecimientoRetencionesVentasTO(empresa, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnxListadoRetencionesVentasTO")
    public List<AnxListadoRetencionesVentasTO> getAnxListadoRetencionesVentasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String tipoDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumento"));
        String establecimiento = UtilsJSON.jsonToObjeto(String.class, map.get("establecimiento"));
        String puntoEmision = UtilsJSON.jsonToObjeto(String.class, map.get("puntoEmision"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventaService.getAnxListadoRetencionesVentasTO(empresa, tipoDocumento, establecimiento, puntoEmision,
                    fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnxFunListadoDevolucionIvaVentasTO")
    public List<AnxFunListadoDevolucionIvaVentasTO> getAnxFunListadoDevolucionIvaVentasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventaService.getAnxFunListadoDevolucionIvaVentasTO(empresa, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoTalonResumenTO")
    public List<AnxTalonResumenTO> getAnexoTalonResumenTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraService.getAnexoTalonResumenTO(empresa, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoTalonResumenVentaTO")
    public List<AnxTalonResumenVentaTO> getAnexoTalonResumenVentaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventaService.getAnexoTalonResumenVentaTO(empresa, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaAnexoNumeracionTO")
    public List<AnxNumeracionTablaTO> getListaAnexoNumeracionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return numeracionService.getListaAnexoNumeracionTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoNumeracionTO")
    public AnxNumeracionTO getAnexoNumeracionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Integer secuencia = UtilsJSON.jsonToObjeto(Integer.class, map.get("secuencia"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return numeracionService.getAnexoNumeracionTO(secuencia);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarAnexoNumeracionTO")
    public String insertarAnexoNumeracionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxNumeracionTO anxNumeracionTO = UtilsJSON.jsonToObjeto(AnxNumeracionTO.class, map.get("anxNumeracionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return numeracionService.insertarAnexoNumeracionTO(anxNumeracionTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarAnexoNumeracionTO")
    public String modificarAnexoNumeracionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxNumeracionTO anxNumeracionTO = UtilsJSON.jsonToObjeto(AnxNumeracionTO.class, map.get("anxNumeracionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return numeracionService.modificarAnexoNumeracionTO(anxNumeracionTO, sisInfoTO);

        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarAnexoNumeracionTO")
    public String eliminarAnexoNumeracionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxNumeracionTO anxNumeracionTO = UtilsJSON.jsonToObjeto(AnxNumeracionTO.class, map.get("anxNumeracionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return numeracionService.eliminarAnexoNumeracionTO(anxNumeracionTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBuscarAnexoConceptoTO")
    public AnxConceptoTO getBuscarAnexoConceptoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String conceptoCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("conceptoCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return conceptoService.getBuscarAnexoConceptoTO(UtilsValidacion.fechaSistema(), conceptoCodigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoConceptoTO")
    public List<AnxConceptoTO> getAnexoConceptoTO(@RequestBody SisInfoTO sisInfoTO) {
        try {
            return conceptoService.getAnexoConceptoTO();
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoSustentoTO")
    public List<AnxSustentoTO> getAnexoSustentoTO(@RequestBody SisInfoTO sisInfoTO) {
        try {
            return sustentoService.getAnexoSustentoTO();
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoTipoComprobanteTO")
    public List<AnxTipoComprobanteTO> getAnexoTipoComprobanteTO(@RequestBody SisInfoTO sisInfoTO) {
        try {
            return tipoComprobanteService.getAnexoTipoComprobanteTO();
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaAnxTipoIdentificacionTO")
    public List<AnxTipoIdentificacionTO> getListaAnxTipoIdentificacionTO(@RequestBody SisInfoTO sisInfoTO) {
        try {
            return tipoIdentificacionService.getListaAnxTipoIdentificacionTO();
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoTipoListaTransaccionTO")
    public List<AnxTipoListaTransaccionTO> getAnexoTipoListaTransaccionTO(@RequestBody SisInfoTO sisInfoTO) {
        try {
            return tipoTransaccionService.getAnexoTipoListaTransaccionTO();
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaAnxAnuladoTablaTO")
    public List<AnxAnuladoTablaTO> getListaAnxAnuladoTablaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anuladosService.getListaAnxAnuladoTablaTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnxAnuladosTO")
    public AnxAnuladosTO getAnxAnuladosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Integer secuencial = UtilsJSON.jsonToObjeto(Integer.class, map.get("secuencial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anuladosService.getAnxAnuladosTO(secuencial);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarAnexoAnuladoTO")
    public String insertarAnexoAnuladoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxAnuladosTO anxAnuladosTO = UtilsJSON.jsonToObjeto(AnxAnuladosTO.class, map.get("anxAnuladosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anuladosService.insertarAnexoAnuladoTO(anxAnuladosTO, false, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarAnexoAnuladoTO")
    public String modificarAnexoAnuladoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxAnuladosTO anxAnuladosTO = UtilsJSON.jsonToObjeto(AnxAnuladosTO.class, map.get("anxAnuladosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anuladosService.modificarAnexoAnuladoTO(anxAnuladosTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarAnexoAnuladoTO")
    public String eliminarAnexoAnuladoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxAnuladosTO anxAnuladosTO = UtilsJSON.jsonToObjeto(AnxAnuladosTO.class, map.get("anxAnuladosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        try {
            return anuladosService.eliminarAnexoAnuladoTO(anxAnuladosTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnxCuentasContablesTO")
    public AnxCuentasContablesTO getAnxCuentasContablesTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String nombreCuenta = UtilsJSON.jsonToObjeto(String.class, map.get("nombreCuenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasContablesService.getAnxCuentasContablesTO(empresa, nombreCuenta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/actualizarAnxCuentasContables")
    public String actualizarAnxCuentasContables(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxCuentasContablesTO anxCuentasContablesTO = UtilsJSON.jsonToObjeto(AnxCuentasContablesTO.class,
                map.get("anxCuentasContablesTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return cuentasContablesService.actualizarAnxCuentasContables(anxCuentasContablesTO, empresa, usuario, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarAnxVentas")
    public String eliminarAnxVentas(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxVentaTO anxVentaTO = UtilsJSON.jsonToObjeto(AnxVentaTO.class, map.get("anxVentaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventaService.eliminarAnxVentas(anxVentaTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionAnxVenta")
    public String accionAnxVenta(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxVentaTO anxVentaTO = UtilsJSON.jsonToObjeto(AnxVentaTO.class, map.get("anxVentaTO"));
        String numeroFactura = UtilsJSON.jsonToObjeto(String.class, map.get("numeroFactura"));
        String periodoFactura = UtilsJSON.jsonToObjeto(String.class, map.get("periodoFactura"));
        String cliCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("cliCodigo"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventaService.accionAnxVenta(anxVentaTO, numeroFactura, periodoFactura, cliCodigo, accion, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnxFunListadoDevolucionIvaTOs")
    public List<AnxFunListadoDevolucionIvaTO> getAnxFunListadoDevolucionIvaTOs(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraService.getAnxFunListadoDevolucionIvaTOs(empCodigo, desde, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/reestructurarRetencion")
    public String reestructurarRetencion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxCompraTO anxCompraTO = UtilsJSON.jsonToObjeto(AnxCompraTO.class, map.get("anxCompraTO"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraService.reestructurarRetencion(anxCompraTO, usuario, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaAnexoTipoComprobanteTO")
    public List<AnxTipoComprobanteTablaTO> getListaAnexoTipoComprobanteTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return tipoComprobanteService.getListaAnexoTipoComprobanteTO(codigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoFormaPago")
    public AnxFormaPago getAnexoFormaPago(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return formaPagoAnexoService.getAnexoFormaPago(codigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoFormaPagoTO")
    public List<AnxFormaPagoTO> getAnexoFormaPagoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Date fechaFactura = UtilsJSON.jsonToObjeto(Date.class, map.get("fechaFactura"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return formaPagoAnexoService.getAnexoFormaPagoTO(fechaFactura);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoCompraFormaPagoTO")
    public List<AnxCompraFormaPagoTO> getAnexoCompraFormaPagoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraFormaPagoService.getAnexoCompraFormaPagoTO(empresa, periodo, motivo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoCompraFormaPago")
    public List<AnxCompraFormaPago> getAnexoCompraFormaPago(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroCompra = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCompra"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraFormaPagoService.getAnexoCompraFormaPago(empresa, periodo, motivo, numeroCompra);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getUltimaNumeracionComprobante")
    public String getUltimaNumeracionComprobante(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String comprobante = UtilsJSON.jsonToObjeto(String.class, map.get("comprobante"));
        String secuencial = UtilsJSON.jsonToObjeto(String.class, map.get("secuencial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventaService.getUltimaNumeracionComprobante(empresa, comprobante, secuencial);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getComboAnxPaisTO")
    public List<AnxPaisTO> getComboAnxPaisTO(@RequestBody SisInfoTO sisInfoTO) {
        try {
            return paisService.getComboAnxPaisTO();
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getComboAnxProvinciaTO")
    public List<AnxProvinciaCantonTO> getComboAnxProvinciaTO(@RequestBody SisInfoTO sisInfoTO) {
        try {
            return provinciasService.getComboAnxProvinciaTO();
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getComboAnxCantonTO")
    public List<AnxProvinciaCantonTO> getComboAnxCantonTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String provincia = UtilsJSON.jsonToObjeto(String.class, map.get("provincia"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return provinciasService.getComboAnxCantonTO(provincia);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getComboAnxDpaProvinciaTO")
    public List<AnxProvinciaCantonTO> getComboAnxDpaProvinciaTO(@RequestBody SisInfoTO sisInfoTO) {
        try {
            return dpaEcuadorService.getComboAnxDpaProvinciaTO();
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getComboAnxDpaCantonTO")
    public List<AnxProvinciaCantonTO> getComboAnxDpaCantonTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String codigoProvincia = UtilsJSON.jsonToObjeto(String.class, map.get("codigoProvincia"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return dpaEcuadorService.getComboAnxDpaCantonTO(codigoProvincia);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getComboAnxParroquiaTO")
    public List<AnxProvinciaCantonTO> getComboAnxParroquiaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String codigoProvincia = UtilsJSON.jsonToObjeto(String.class, map.get("codigoProvincia"));
        String codigoCanton = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCanton"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return dpaEcuadorService.getComboAnxParroquiaTO(codigoProvincia, codigoCanton);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getFunRegistroDatosCrediticiosTOs")
    public List<AnxFunRegistroDatosCrediticiosTO> getFunRegistroDatosCrediticiosTOs(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String codigoEmpresa = UtilsJSON.jsonToObjeto(String.class, map.get("codigoEmpresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return dpaEcuadorService.getFunRegistroDatosCrediticiosTOs(codigoEmpresa, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoFunListadoRetencionesHuerfanas")
    public List<String> getAnexoFunListadoRetencionesHuerfanas(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraService.getAnexoFunListadoRetencionesHuerfanas(empresa, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoFunListadoComprobantesPendientes")
    public List<String> getAnexoFunListadoComprobantesPendientes(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraService.getAnexoFunListadoComprobantesPendientes(empresa, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionAnxUrlWebServicesTO")
    public String accionAnxUrlWebServicesTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        anxUrlWebServicesTO anxUrlWebServicesTO = UtilsJSON.jsonToObjeto(anxUrlWebServicesTO.class,
                map.get("anxUrlWebServicesTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return urlWebServicesService.accionAnxUrlWebServicesTO(anxUrlWebServicesTO, accion, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnxUrlWebServicesTO")
    public anxUrlWebServicesTO getAnxUrlWebServicesTO(@RequestBody SisInfoTO sisInfoTO) {
        try {
            return urlWebServicesService.getAnxUrlWebServicesTO();
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCompAutorizacion")
    public String getCompAutorizacion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String provCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("provCodigo"));
        String codTipoComprobante = UtilsJSON.jsonToObjeto(String.class, map.get("codTipoComprobante"));
        String numComplemento = UtilsJSON.jsonToObjeto(String.class, map.get("numComplemento"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraService.getCompAutorizacion(empCodigo, provCodigo, codTipoComprobante, numComplemento);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionAnxVentaElectronica")
    public String accionAnxVentaElectronica(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxVentaElectronicaTO anxVentaElectronicaTO = UtilsJSON.jsonToObjeto(AnxVentaElectronicaTO.class,
                map.get("anxVentaElectronicaTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventaElectronicaService.accionAnxVentaElectronica(anxVentaElectronicaTO, accion, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionAnxCompraElectronica")
    public String accionAnxCompraElectronica(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        AnxCompraElectronicaTO anxCompraElectronicaTO = UtilsJSON.jsonToObjeto(AnxCompraElectronicaTO.class,
                map.get("anxCompraElectronicaTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraElectronicaService.accionAnxCompraElectronica(anxCompraElectronicaTO, accion, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaAnxVentaElectronicaTO")
    public List<AnxListaVentaElectronicaTO> getListaAnxVentaElectronicaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String tipoDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumento"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventaService.getListaAnxVentaElectronicaTO(empresa, fechaDesde, fechaHasta, tipoDocumento);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getXmlComprobanteRetencion")
    public String getXmlComprobanteRetencion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String ePeriodo = UtilsJSON.jsonToObjeto(String.class, map.get("ePeriodo"));
        String eMotivo = UtilsJSON.jsonToObjeto(String.class, map.get("eMotivo"));
        String eNumero = UtilsJSON.jsonToObjeto(String.class, map.get("eNumero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraElectronicaService.getXmlComprobanteRetencion(empresa, ePeriodo, eMotivo, eNumero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaAnxComprasElectronicaTO")
    public List<AnxListadoCompraElectronicaTO> getListaAnxComprasElectronicaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String estado = UtilsJSON.jsonToObjeto(String.class, map.get("estado"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraService.getListaAnxComprasElectronicaTO(empresa, fechaDesde, fechaHasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnexoListaRetencionesPendienteTO")
    public List<AnxListaRetencionesPendientesTO> getAnexoListaRetencionesPendienteTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraService.getAnexoListaRetencionesPendienteTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getListaVentasPendientes")
    public List<AnxListaVentasPendientesTO> getListaVentasPendientes(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventaElectronicaService.getListaVentasPendientes(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/comprobarRetencionAutorizadaProcesamiento")
    public boolean comprobarRetencionAutorizadaProcesamiento(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraElectronicaService.comprobarRetencionAutorizadaProcesamiento(empresa, periodo, motivo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/getXmlComprobanteElectronico")
    public String getXmlComprobanteElectronico(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String ePeriodo = UtilsJSON.jsonToObjeto(String.class, map.get("ePeriodo"));
        String eMotivo = UtilsJSON.jsonToObjeto(String.class, map.get("eMotivo"));
        String eNumero = UtilsJSON.jsonToObjeto(String.class, map.get("eNumero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventaElectronicaService.getXmlComprobanteElectronico(empresa, ePeriodo, eMotivo, eNumero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/comprobarAnxVentaElectronicaAutorizacion")
    public String comprobarAnxVentaElectronicaAutorizacion(@RequestBody String json) {

        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventaElectronicaService.comprobarAnxVentaElectronicaAutorizacion(empresa, periodo, motivo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getAnxUltimaAutorizacionTO")
    public AnxUltimaAutorizacionTO getAnxUltimaAutorizacionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        String tipoDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumento"));
        String secuencial = UtilsJSON.jsonToObjeto(String.class, map.get("secuencial"));
        String fechaFactura = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFactura"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraService.getAnxUltimaAutorizacionTO(empresa, proveedor, tipoDocumento, secuencial,
                    fechaFactura);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteTalonResumen")
    public byte[] generarReporteTalonResumen(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        List<AnxTalonResumenTO> listaAnxTalonResumenTO = UtilsJSON.jsonToList(AnxTalonResumenTO.class,
                map.get("listaAnxTalonResumenTO"));
        try {
            return reporteAnexosService.generarReporteTalonResumen(usuarioEmpresaReporteTO, desde, hasta,
                    listaAnxTalonResumenTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteTalonResumenVentas")
    public byte[] generarReporteTalonResumenVentas(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        List<AnxTalonResumenVentaTO> listaAnxTalonResumenVentaTO = UtilsJSON.jsonToList(AnxTalonResumenVentaTO.class,
                map.get("listaAnxTalonResumenVentaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteAnexosService.generarReporteTalonResumenVentas(usuarioEmpresaReporteTO, desde, hasta,
                    listaAnxTalonResumenVentaTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoRetencionesPorNumero")
    public byte[] generarReporteListadoRetencionesPorNumero(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<AnxListaRetencionesTO> listaAnxListaRetencionesTO = UtilsJSON.jsonToList(AnxListaRetencionesTO.class,
                map.get("listaAnxListaRetencionesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteAnexosService.generarReporteListadoRetencionesPorNumero(usuarioEmpresaReporteTO,
                    listaAnxListaRetencionesTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/generarReporteListadoRetencionesVentas")
    public byte[] generarReporteListadoRetencionesVentas(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<AnxListadoRetencionesVentasTO> anxListadoRetencionesVentasTOs = UtilsJSON
                .jsonToList(AnxListadoRetencionesVentasTO.class, map.get("anxListadoRetencionesVentasTOs"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteAnexosService.generarReporteListadoRetencionesVentas(usuarioEmpresaReporteTO, fechaDesde,
                    fechaHasta, anxListadoRetencionesVentasTOs);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoDevolucionIva")
    public byte[] generarReporteListadoDevolucionIva(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<AnxFunListadoDevolucionIvaTO> anxFunListadoDevolucionIvaTOs = UtilsJSON
                .jsonToList(AnxFunListadoDevolucionIvaTO.class, map.get("anxFunListadoDevolucionIvaTOs"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteAnexosService.generarReporteListadoDevolucionIva(usuarioEmpresaReporteTO, fechaDesde,
                    fechaHasta, anxFunListadoDevolucionIvaTOs);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoDevolucionIvaVentas")
    public byte[] generarReporteListadoDevolucionIvaVentas(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<AnxFunListadoDevolucionIvaVentasTO> anxFunListadoDevolucionIvaVentasTO = UtilsJSON
                .jsonToList(AnxFunListadoDevolucionIvaVentasTO.class, map.get("anxFunListadoDevolucionIvaVentasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteAnexosService.generarReporteListadoDevolucionIvaVentas(usuarioEmpresaReporteTO, fechaDesde,
                    fechaHasta, anxFunListadoDevolucionIvaVentasTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }
    // revisar

    @RequestMapping("/getAutorizadocionComprobantes")
    public RespuestaComprobante getAutorizadocionComprobantes(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String claveAcceso = UtilsJSON.jsonToObjeto(String.class, map.get("claveAcceso"));
        String tipoAmbiente = UtilsJSON.jsonToObjeto(String.class, map.get("tipoAmbiente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return urlWebServicesService.getAutorizadocionComprobantes(claveAcceso, tipoAmbiente, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;

    }

    @RequestMapping("/getEnvioComprobanteElectronicosWS")
    public AnxComprobanteElectronicoUtilTO getEnvioComprobanteElectronicosWS(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] eXmlFirmado = UtilsJSON.jsonToObjeto(byte[].class, map.get("eXmlFirmado"));
        String claveAcceso = UtilsJSON.jsonToObjeto(String.class, map.get("claveAcceso"));
        String tipoAmbiente = UtilsJSON.jsonToObjeto(String.class, map.get("tipoAmbiente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return urlWebServicesService.getEnvioComprobanteElectronicosWS(eXmlFirmado, claveAcceso, tipoAmbiente,
                    sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteComprobanteElectronico")
    public byte[] generarReporteComprobanteElectronico(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String claveAcceso = UtilsJSON.jsonToObjeto(String.class, map.get("claveAcceso"));
        String XmlString = UtilsJSON.jsonToObjeto(String.class, map.get("XmlString"));
        String nombreReporteJasper = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporteJasper"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            SisEmpresaParametros empresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
            return reporteComprobanteElectronicoService.generarReporteComprobanteElectronico(empresa, claveAcceso, XmlString, nombreReporteJasper, empresaParametros.getParRutaReportes(), empresaParametros.getParRutaLogo());
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/validarExistenciaReportesElectronicos")
    public String validarExistenciaReportesElectronicos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String claveAcceso = UtilsJSON.jsonToObjeto(String.class, map.get("claveAcceso"));
        String nombreReporteJasper = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporteJasper"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            SisEmpresaParametros empresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
            // String empresa, String nombreReporteJasper, String claveAcceso
            return reporteComprobanteElectronicoService.validarExistenciaReportesElectronicos(empresa, claveAcceso, nombreReporteJasper, empresaParametros.getParRutaReportes());
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/enviarAutorizarRetencionElectronica")
    public String enviarAutorizarRetencionElectronica(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String perCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("perCodigo"));
        String motCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("motCodigo"));
        String compNumero = UtilsJSON.jsonToObjeto(String.class, map.get("compNumero"));
        String tipoAmbiente = UtilsJSON.jsonToObjeto(String.class, map.get("tipoAmbiente"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        try {
            SisFirmaElectronica cajCajaTO = enviarComprobantesWSService.validarRequisitosParaEnviarAutorizacion(sisInfoTO);
            return enviarComprobantesWSService.enviarAutorizarRetencionElectronica(empresa, perCodigo, motCodigo,
                    compNumero, tipoAmbiente, accion, cajCajaTO, sisInfoTO) + ")";
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            return e != null ? "F" + e.getMessage() : "FError desconocido.";
        }
    }

    @RequestMapping("/enviarAutorizarRetencionElectronicaLote")
    public String enviarAutorizarRetencionElectronicaLote(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxListaRetencionesPendientesTO> listaEnviar = UtilsJSON.jsonToList(AnxListaRetencionesPendientesTO.class,
                map.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return enviarComprobantesWSService.enviarAutorizarRetencionElectronicaLote(empresa, listaEnviar, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            return e != null ? "F" + e.getMessage() : "FError desconocido.";
        }
    }

    @RequestMapping("/descargarrAutorizarRetencionElectronica")
    public String descargarrAutorizarRetencionElectronica(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String perCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("perCodigo"));
        String motCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("motCodigo"));
        String compNumero = UtilsJSON.jsonToObjeto(String.class, map.get("compNumero"));
        String tipoAmbiente = UtilsJSON.jsonToObjeto(String.class, map.get("tipoAmbiente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return enviarComprobantesWSService.descargarAutorizarRetencionElectronica(empresa, perCodigo, motCodigo,
                    compNumero, tipoAmbiente, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/enviarAutorizarFaturasElectronicaLote")
    public String enviarAutorizarFaturasElectronicaLote(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<AnxListaVentasPendientesTO> listaEnviar = UtilsJSON.jsonToList(AnxListaVentasPendientesTO.class,
                map.get("listaEnviar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return enviarComprobantesWSService.enviarAutorizarFaturasElectronicaLote(empresa, listaEnviar, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            return e != null ? "F" + e.getMessage() : "FError desconocido.";
        }
    }

    @RequestMapping("/enviarAutorizarFacturaElectronica")
    public String enviarAutorizarVentasElectronica(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String vtaPerCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("vtaPerCodigo"));
        String vtaMotCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("vtaMotCodigo"));
        String vtaNumero = UtilsJSON.jsonToObjeto(String.class, map.get("vtaNumero"));
        String tipoAmbiente = UtilsJSON.jsonToObjeto(String.class, map.get("tipoAmbiente"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        try {
            SisFirmaElectronica cajCajaTO = enviarComprobantesWSService.validarRequisitosParaEnviarAutorizacion(sisInfoTO);
            return enviarComprobantesWSService.enviarAutorizarFacturaElectronica(empresa, vtaPerCodigo, vtaMotCodigo,
                    vtaNumero, tipoAmbiente, accion, cajCajaTO, sisInfoTO) + ")";
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            return e != null ? "F" + e.getMessage() : "FError desconocido.";
        }
    }

    @RequestMapping("/descargarrAutorizarFacturaElectronica")
    public String descargarrAutorizarFacturaElectronica(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String vtaPerCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("vtaPerCodigo"));
        String vtaMotCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("vtaMotCodigo"));
        String vtaNumero = UtilsJSON.jsonToObjeto(String.class, map.get("vtaNumero"));
        String tipoAmbiente = UtilsJSON.jsonToObjeto(String.class, map.get("tipoAmbiente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return enviarComprobantesWSService.descargarAutorizarFacturaElectronica(empresa, vtaPerCodigo,
                    vtaMotCodigo, vtaNumero, tipoAmbiente, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/enviarEmailComprobantesElectronicos")
    public String enviarEmailComprobantesElectronicos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String ePeriodo = UtilsJSON.jsonToObjeto(String.class, map.get("ePeriodo"));
        String eMotivo = UtilsJSON.jsonToObjeto(String.class, map.get("eMotivo"));
        String eNumero = UtilsJSON.jsonToObjeto(String.class, map.get("eNumero"));
        String claveAcceso = UtilsJSON.jsonToObjeto(String.class, map.get("claveAcceso"));
        String nombreReporteJasper = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporteJasper"));
        String XmlString = UtilsJSON.jsonToObjeto(String.class, map.get("XmlString"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return compraElectronicaService.enviarEmailComprobantesElectronicos(empresa, ePeriodo, eMotivo, eNumero,
                    claveAcceso, nombreReporteJasper, XmlString, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRegistroDatosCrediticio")
    public byte[] generarReporteRegistroDatosCrediticio(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        List<AnxFunRegistroDatosCrediticiosTO> listaAnxFunRegistroDatosCrediticiosTO = UtilsJSON.jsonToList(AnxFunRegistroDatosCrediticiosTO.class,
                map.get("listaAnxFunRegistroDatosCrediticiosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteAnexosService.generarReporteRegistroDatosCrediticios(usuarioEmpresaReporteTO, desde, hasta,
                    listaAnxFunRegistroDatosCrediticiosTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteRetencion")
    public byte[] generarReporteRetencion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteObjetoAnexo> anxListaRetencionesTO = UtilsJSON.jsonToList(ReporteObjetoAnexo.class,
                map.get("lista"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporte"));
        Map<String, Object> parametros = (Map<String, Object>) map.get("parametros");
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return genericReporteService.generarReporte("anexos", nombreReporte + ".jrxml", usuarioEmpresaReporteTO, parametros, anxListaRetencionesTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }
}
