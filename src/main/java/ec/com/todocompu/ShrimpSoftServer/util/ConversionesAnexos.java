package ec.com.todocompu.ShrimpSoftServer.util;

import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxAnuladosTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraReembolsoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCuentasContablesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxGuiaRemisionElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxLiquidacionComprasElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxSustentoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoListaTransaccionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaExportacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.anxUrlWebServicesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxAnulados;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompra;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraDetalle;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraDividendo;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraElectronica;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraFormaPago;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraPK;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraReembolso;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxConcepto;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCuentascontables;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxFormaPago;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxGuiaRemisionElectronica;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxLiquidacionComprasElectronica;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxSustento;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxTipocomprobante;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxTipotransaccion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxUrlWebServices;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVenta;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaElectronica;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaExportacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificaciones;
import java.math.BigDecimal;

public class ConversionesAnexos {

    public static AnxCompra convertirAnxCompraTO_AnxCompra(AnxCompraTO anxCompraTO) {
        AnxCompra anxCompra = new AnxCompra();

        anxCompra.setAnxCompraPK(new AnxCompraPK(anxCompraTO.getEmpCodigo(), anxCompraTO.getPerCodigo(),
                anxCompraTO.getMotCodigo(), anxCompraTO.getCompNumero()));
        anxCompra.setCompSustentotributario(new AnxSustento(anxCompraTO.getCompSustentotributario()));// revisar

        anxCompra.setCompAutorizacion(anxCompraTO.getCompAutorizacion());
        anxCompra.setCompFechaEmision(UtilsValidacion.fecha(anxCompraTO.getCompEmision(), "dd-MM-yyyy"));
        anxCompra.setCompFechaCaduca(UtilsValidacion.fecha(anxCompraTO.getCompCaduca(), "dd-MM-yyyy"));
        anxCompra.setCompFechaRecepcion(UtilsValidacion.fecha(anxCompraTO.getCompCaduca(), "dd-MM-yyyy"));// revisar
        anxCompra.setCompBase0(anxCompraTO.getCompBase0());
        anxCompra.setCompBaseimponible(anxCompraTO.getCompBaseimponible());
        anxCompra.setCompBasenoobjetoiva(anxCompraTO.getCompBasenoobjetoiva());
        anxCompra.setCompMontoice(anxCompraTO.getCompMontoice());
        anxCompra.setCompMontoiva(anxCompraTO.getCompMontoiva());

        anxCompra.setCompBaseivabienes(anxCompraTO.getCompBaseivabienes());
        anxCompra.setCompBaseivaservicios(anxCompraTO.getCompBaseivaservicios());
        anxCompra.setCompBaseivaserviciosprofesionales(anxCompraTO.getCompBaseivaserviciosprofesionales());

        BigDecimal cero = new BigDecimal("0.00");
        if (anxCompraTO.getCompBaseivabienes() != null && anxCompraTO.getCompBaseivabienes().compareTo(cero) > 0) {
            anxCompra.setCompPorcentajebienes(anxCompraTO.getCompPorcentajebienes());
        } else {
            anxCompra.setCompPorcentajebienes(cero);
        }

        if (anxCompraTO.getCompBaseivaservicios() != null && anxCompraTO.getCompBaseivaservicios().compareTo(cero) > 0) {
            anxCompra.setCompPorcentajeservicios(anxCompraTO.getCompPorcentajeservicios());
        } else {
            anxCompra.setCompPorcentajeservicios(cero);
        }

        if (anxCompraTO.getCompBaseivaserviciosprofesionales() != null && anxCompraTO.getCompBaseivaserviciosprofesionales().compareTo(cero) > 0) {
            anxCompra.setCompPorcentajeserviciosprofesionales(anxCompraTO.getCompPorcentajeserviciosprofesionales());
        } else {
            anxCompra.setCompPorcentajeserviciosprofesionales(cero);
        }

        anxCompra.setCompValorbienes(anxCompraTO.getCompValorbienes());
        anxCompra.setCompValorservicios(anxCompraTO.getCompValorservicios());
        anxCompra.setCompValorserviciosprofesionales(anxCompraTO.getCompValorserviciosprofesionales());

        anxCompra.setCompRetencionEmpresa(anxCompraTO.getEmpCodigo());// revisar

        anxCompra.setCompRetencionNumero(anxCompraTO.getCompRetencionNumero());
        anxCompra.setCompRetencionAutorizacion(anxCompraTO.getCompRetencionAutorizacion());
        anxCompra.setCompRetencionFechaEmision(
                UtilsValidacion.fecha(anxCompraTO.getCompRetencionFechaEmision(), "yyyy-MM-dd"));

        anxCompra.setCompModificadoDocumentoEmpresa(anxCompraTO.getEmpCodigo());
        anxCompra.setCompModificadoDocumentoTipo(anxCompraTO.getCompModificadoDocumentoTipo());
        anxCompra.setCompModificadoDocumentoNumero(anxCompraTO.getCompModificadoDocumentoNumero());
        anxCompra.setCompModificadoAutorizacion(anxCompraTO.getCompModificadoAutorizacion());
        anxCompra.setRetImpresa(anxCompraTO.getRetImpreso());
        anxCompra.setRetEntregada(anxCompraTO.getRetEntregado());
        anxCompra.setRetElectronica(anxCompraTO.getRetElectronica());

        anxCompra.setFechaUltimaValidacionSri(anxCompraTO.getFechaUltimaValidacionSri() != null
                ? UtilsValidacion.fecha(anxCompraTO.getFechaUltimaValidacionSri(), "dd-MM-yyyy") : null);
        return anxCompra;
    }

    public static AnxCompraDetalle convertirAnxCompraDetalleTO_AnxCompraDetalle(AnxCompraDetalleTO anxCompraDetalleTO) {
        AnxCompraDetalle anxCompraDetalle = new AnxCompraDetalle();
        anxCompraDetalle.setDetBase0(anxCompraDetalleTO.getDetBase0());
        anxCompraDetalle.setDetBaseimponible(anxCompraDetalleTO.getDetBaseImponible());
        anxCompraDetalle.setDetBasenoobjetoiva(anxCompraDetalleTO.getDetBaseNoObjetoIva());
        anxCompraDetalle.setDetOrden(anxCompraDetalleTO.getDetOrden());
        anxCompraDetalle.setDetPorcentaje(anxCompraDetalleTO.getDetPorcentaje());
        anxCompraDetalle.setDetSecuencial(anxCompraDetalleTO.getDetSecuencial());
        anxCompraDetalle.setDetValorretenido(anxCompraDetalleTO.getDetValorRetenido());
        return anxCompraDetalle;
    }

    public static AnxCompraDividendo convertirAnxCompraDetalleTO_AnxCompraDividendo(
            AnxCompraDetalleTO anxCompraDetalleTO) {
        AnxCompraDividendo anxCompraDividendo = new AnxCompraDividendo();
        anxCompraDividendo.setDivSecuencial(anxCompraDetalleTO.getDivSecuencial());
        anxCompraDividendo.setDivAnioUtilidades(anxCompraDetalleTO.getDivAnioUtilidades());
        anxCompraDividendo.setDivIrAsociado(anxCompraDetalleTO.getDivIrAsociado());
        anxCompraDividendo.setDivFechaPago(UtilsValidacion.fecha(anxCompraDetalleTO.getDivFechaPago(), "dd-MM-yyyy"));
        return anxCompraDividendo;
    }

    public static AnxCompraReembolso convertirAnxCompraReembolsoTO_AnxCompraReembolso(
            AnxCompraReembolsoTO anxCompraReembolsoTO) {
        AnxCompraReembolso anxCompraReembolso = new AnxCompraReembolso(anxCompraReembolsoTO.getReembSecuencial(),
                anxCompraReembolsoTO.getProvEmpresa(), anxCompraReembolsoTO.getProvCodigo(),
                anxCompraReembolsoTO.getReembDocumentoTipo(), anxCompraReembolsoTO.getReembDocumentoNumero(),
                UtilsValidacion.fecha(anxCompraReembolsoTO.getReembFechaemision(), "dd-MM-yyyy"),
                anxCompraReembolsoTO.getReembAutorizacion(), anxCompraReembolsoTO.getReembBaseimponible(),
                anxCompraReembolsoTO.getReembBaseimpgrav(), anxCompraReembolsoTO.getReembBasenograiva(),
                anxCompraReembolsoTO.getReembMontoice(), anxCompraReembolsoTO.getReembMontoiva(),
                anxCompraReembolsoTO.getProvEmpresa(), anxCompraReembolsoTO.getProvCodigo());
        anxCompraReembolso.setAnxCompra(new AnxCompra(
                new AnxCompraPK(anxCompraReembolsoTO.getCompEmpresa(), anxCompraReembolsoTO.getCompPeriodo(),
                        anxCompraReembolsoTO.getCompMotivo(), anxCompraReembolsoTO.getCompNumero())));
        return anxCompraReembolso;
    }

    public static AnxCompraTO convertirAnxCompra_AnxCompraTO(AnxCompra anxCompra) {
        if (anxCompra != null) {
            AnxCompraTO anxCompraTO = new AnxCompraTO();
            anxCompraTO.setEmpCodigo(anxCompra.getAnxCompraPK().getCompEmpresa());
            anxCompraTO.setPerCodigo(anxCompra.getAnxCompraPK().getCompPeriodo());
            anxCompraTO.setMotCodigo(anxCompra.getAnxCompraPK().getCompMotivo());
            anxCompraTO.setCompNumero(anxCompra.getAnxCompraPK().getCompNumero());
            anxCompraTO.setCompSustentotributario(anxCompra.getCompSustentotributario().getSusCodigo());
            anxCompraTO.setCompAutorizacion(anxCompra.getCompAutorizacion());
            anxCompraTO.setCompCaduca(UtilsValidacion.fecha(anxCompra.getCompFechaCaduca(), "dd-MM-yyyy"));
            anxCompraTO.setCompEmision(anxCompra.getCompFechaEmision() != null
                    ? UtilsValidacion.fecha(anxCompra.getCompFechaEmision(), "dd-MM-yyyy") : null);
            anxCompraTO.setCompBase0(anxCompra.getCompBase0());
            anxCompraTO.setCompBaseimponible(anxCompra.getCompBaseimponible());
            anxCompraTO.setCompBasenoobjetoiva(anxCompra.getCompBasenoobjetoiva());
            anxCompraTO.setCompMontoice(anxCompra.getCompMontoice());
            anxCompraTO.setCompMontoiva(anxCompra.getCompMontoiva());
            anxCompraTO.setCompBaseivabienes(anxCompra.getCompBaseivabienes());
            anxCompraTO.setCompBaseivaservicios(anxCompra.getCompBaseivaservicios());
            anxCompraTO.setCompBaseivaserviciosprofesionales(anxCompra.getCompBaseivaserviciosprofesionales());
            anxCompraTO.setCompPorcentajebienes(anxCompra.getCompPorcentajebienes());
            anxCompraTO.setCompPorcentajeservicios(anxCompra.getCompPorcentajeservicios());
            anxCompraTO.setCompPorcentajeserviciosprofesionales(anxCompra.getCompPorcentajeserviciosprofesionales());
            anxCompraTO.setCompValorbienes(anxCompra.getCompValorbienes());
            anxCompraTO.setCompValorservicios(anxCompra.getCompValorservicios());
            anxCompraTO.setCompValorserviciosprofesionales(anxCompra.getCompValorserviciosprofesionales());
            anxCompraTO.setCompRetencionNumero(anxCompra.getCompRetencionNumero());
            anxCompraTO.setCompRetencionAutorizacion(anxCompra.getCompRetencionAutorizacion());

            java.text.DateFormat formato = new java.text.SimpleDateFormat("yyyy-MM-dd");
            anxCompraTO.setCompRetencionFechaEmision(formato.format(anxCompra.getCompRetencionFechaEmision()));
            anxCompraTO.setCompModificadoDocumentoTipo(anxCompra.getCompModificadoDocumentoTipo());
            anxCompraTO.setCompModificadoDocumentoNumero(anxCompra.getCompModificadoDocumentoNumero());
            anxCompraTO.setCompModificadoAutorizacion(anxCompra.getCompModificadoAutorizacion());
            anxCompraTO.setRetImpreso(anxCompra.getRetImpresa());
            anxCompraTO.setRetEntregado(anxCompra.getRetEntregada());
            anxCompraTO.setRetElectronica(anxCompra.getRetElectronica());
            anxCompraTO.setCompClaveAccesoExterna(anxCompra.getCompClaveAccesoExterna());
            anxCompraTO.setFechaUltimaValidacionSri(anxCompra.getFechaUltimaValidacionSri() != null
                    ? UtilsValidacion.fecha(anxCompra.getFechaUltimaValidacionSri(), "dd-MM-yyyy") : null);
            return anxCompraTO;
        } else {
            return null;
        }
    }

    public static AnxVenta convertirAnxVentaTO_AnxVenta(AnxVentaTO anxVentaTO) {
        AnxVenta anxVenta = new AnxVenta();
        anxVenta.setAnxVentaPK(new AnxVentaPK(anxVentaTO.getVenEmpresa(), anxVentaTO.getVenPeriodo(),
                anxVentaTO.getVenMotivo(), anxVentaTO.getVenNumero()));
        anxVenta.setVenRetencionnumero(anxVentaTO.getVenRetencionNumero());
        anxVenta.setVenRetencionautorizacion(anxVentaTO.getVenRetencionAutorizacion());
        anxVenta.setVenRetencionfechaemision(
                UtilsValidacion.fecha(anxVentaTO.getVenRetencionFechaEmision(), "yyyy-MM-dd"));
        anxVenta.setVenBase0(anxVentaTO.getVenBase0());
        anxVenta.setVenBaseimponible(anxVentaTO.getVenBaseImponible());
        anxVenta.setVenBasenoobjetoiva(anxVentaTO.getVenBaseNoObjetoIva());
        anxVenta.setVenMontoiva(anxVentaTO.getVenMontoIva());
        anxVenta.setVenValorretenidoiva(anxVentaTO.getVenValorRetenidoIva());
        anxVenta.setVenValorretenidorenta(anxVentaTO.getVenValorRetenidoRenta());
        anxVenta.setUsrEmpresa(anxVentaTO.getUsrEmpresa());
        anxVenta.setUsrCodigo(anxVentaTO.getUsrCodigo());
        anxVenta.setUsrFechaInserta(UtilsValidacion.fechaString_Date(anxVentaTO.getUsrFechaInserta()));
        anxVenta.setFechaUltimaValidacionSri(anxVentaTO.getFechaUltimaValidacionSri() != null
                ? UtilsValidacion.fecha(anxVentaTO.getFechaUltimaValidacionSri(), "dd-MM-yyyy") : null);
        return anxVenta;
    }

    public static AnxVentaTO convertirAnxVenta_AnxVentaTO(AnxVenta anxVenta) {
        if (anxVenta != null) {
            AnxVentaTO anxVentaTO = new AnxVentaTO();
            anxVentaTO.setVenEmpresa(anxVenta.getAnxVentaPK().getVenEmpresa());
            anxVentaTO.setVenPeriodo(anxVenta.getAnxVentaPK().getVenPeriodo());
            anxVentaTO.setVenMotivo(anxVenta.getAnxVentaPK().getVenMotivo());
            anxVentaTO.setVenNumero(anxVenta.getAnxVentaPK().getVenNumero());
            anxVentaTO.setVenRetencionNumero(anxVenta.getVenRetencionnumero());
            anxVentaTO.setVenRetencionAutorizacion(anxVenta.getVenRetencionautorizacion());
            anxVentaTO.setVenBase0(anxVenta.getVenBase0());
            anxVentaTO.setVenBaseImponible(anxVenta.getVenBaseimponible());
            anxVentaTO.setVenBaseNoObjetoIva(anxVenta.getVenBasenoobjetoiva());
            anxVentaTO.setVenMontoIva(anxVenta.getVenMontoiva());
            anxVentaTO.setVenValorRetenidoIva(anxVenta.getVenValorretenidoiva());
            anxVentaTO.setVenValorRetenidoRenta(anxVenta.getVenValorretenidorenta());

            java.text.DateFormat formato = new java.text.SimpleDateFormat("yyyy-MM-dd");
            anxVentaTO.setVenRetencionFechaEmision(formato.format(anxVenta.getVenRetencionfechaemision()));
            anxVentaTO.setUsrEmpresa(anxVenta.getUsrEmpresa());
            anxVentaTO.setUsrCodigo(anxVenta.getUsrCodigo());
            anxVentaTO.setUsrFechaInserta(UtilsValidacion.fecha(anxVenta.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            anxVentaTO.setFechaUltimaValidacionSri(anxVenta.getFechaUltimaValidacionSri() != null
                    ? UtilsValidacion.fecha(anxVenta.getFechaUltimaValidacionSri(), "dd-MM-yyyy") : null);
            return anxVentaTO;
        } else {
            return null;
        }
    }

    public static AnxNumeracion convertirAnxNumeracionTO_AnxAnxNumeracion(AnxNumeracionTO anxNumeracionTO) {
        AnxNumeracion anxNumeracion = new AnxNumeracion();
        anxNumeracion.setNumSecuencial(anxNumeracionTO.getNumSecuencial());
        anxNumeracion.setNumEmpresa(anxNumeracionTO.getNumEmpresa());
        anxNumeracion.setNumComprobante(anxNumeracionTO.getNumComprobante());
        anxNumeracion.setNumDesde(anxNumeracionTO.getNumDesde());
        anxNumeracion.setNumHasta(anxNumeracionTO.getNumHasta());
        anxNumeracion.setNumAutorizacion(anxNumeracionTO.getNumAutorizacion());
        anxNumeracion.setNumCaduca(anxNumeracionTO.getNumCaduca());
        anxNumeracion.setNumLineas(anxNumeracionTO.getNumLineas());
        anxNumeracion.setNumFormatoPos(anxNumeracionTO.isNumFormatoPos());
        anxNumeracion.setNumDocumentoElectronico(anxNumeracionTO.isNumDocumentoElectronico());
        anxNumeracion.setNumAmbienteProduccion(anxNumeracionTO.isNumAmbienteProduccion());
        anxNumeracion.setNumAutorizacionAutomatica(anxNumeracionTO.isNumAutorizacionAutomatica());
        anxNumeracion.setNumMostrarDialogoImpresion(anxNumeracionTO.isNumMostrarDialogoImpresion());
        anxNumeracion.setSecEmpresa(anxNumeracionTO.getNumEmpresa());
        anxNumeracion.setSecCodigo(anxNumeracionTO.getSecCodigo());
        anxNumeracion.setUsrEmpresa(anxNumeracionTO.getNumEmpresa());
        anxNumeracion.setUsrCodigo(anxNumeracionTO.getUsrCodigo());
        anxNumeracion.setUsrFechaInserta(UtilsValidacion.fechaString_Date(anxNumeracionTO.getUsrFechaInserta()));
        anxNumeracion.setNumObservacion(anxNumeracionTO.getNumObservacion());
        if (anxNumeracionTO.getIdNotificaciones() > 0) {
            anxNumeracion.setIdNotificaciones(new SisEmpresaNotificaciones(anxNumeracionTO.getIdNotificaciones()));
        }
        return anxNumeracion;
    }

    public static AnxNumeracionTO convertirAnxNumeracion_AnxAnxNumeracionTO(AnxNumeracion anxNumeracion) {
        AnxNumeracionTO anxNumeracionTO = new AnxNumeracionTO();

        anxNumeracionTO.setNumSecuencial(anxNumeracion.getNumSecuencial());
        anxNumeracionTO.setNumEmpresa(anxNumeracion.getNumEmpresa());
        anxNumeracionTO.setNumComprobante(anxNumeracion.getNumComprobante());
        anxNumeracionTO.setNumDesde(anxNumeracion.getNumDesde());
        anxNumeracionTO.setNumHasta(anxNumeracion.getNumHasta());
        anxNumeracionTO.setNumAutorizacion(anxNumeracion.getNumAutorizacion());
        anxNumeracionTO.setNumAutorizacionAutomatica(anxNumeracion.isNumAutorizacionAutomatica());
        anxNumeracionTO.setNumCaduca(anxNumeracion.getNumCaduca());
        anxNumeracionTO.setNumLineas(anxNumeracion.getNumLineas());
        anxNumeracionTO.setNumFormatoPos(anxNumeracion.getNumFormatoPos());
        anxNumeracionTO.setNumDocumentoElectronico(anxNumeracion.getNumDocumentoElectronico());
        anxNumeracionTO.setNumAmbienteProduccion(anxNumeracion.getNumAmbienteProduccion());
        anxNumeracionTO.setNumMostrarDialogoImpresion(anxNumeracion.getNumMostrarDialogoImpresion());
        anxNumeracionTO.setNumObservacion(anxNumeracion.getNumObservacion());
        // anxNumeracionTO.setNumActual(anxNumeracion.getNumActual());
        anxNumeracionTO.setSecCodigo(anxNumeracion.getSecCodigo());
        anxNumeracionTO.setUsrCodigo(anxNumeracion.getUsrCodigo());
        anxNumeracionTO.setUsrFechaInserta(UtilsValidacion.fecha(anxNumeracion.getUsrFechaInserta(), "yyyy-MM-dd"));
        if (anxNumeracion.getIdNotificaciones() != null) {
            anxNumeracionTO.setIdNotificaciones(anxNumeracion.getIdNotificaciones().getId());
        }
        return anxNumeracionTO;
    }

    public static AnxConceptoTO convertirAnxConcepto_AnxConceptoTO(AnxConcepto anxConcepto) {
        AnxConceptoTO anxConceptoTO = new AnxConceptoTO();
        anxConceptoTO.setConSecuencial(anxConcepto.getConSecuencial());
        anxConceptoTO.setConCodigo(anxConcepto.getConCodigo());
        anxConceptoTO.setConConcepto(anxConcepto.getConConcepto());
        anxConceptoTO.setConPorcentaje(anxConcepto.getConPorcentaje());
        anxConceptoTO.setFechaInicio(anxConcepto.getConFechaInicio() == null ? ""
                : UtilsValidacion.fecha(anxConcepto.getConFechaInicio(), "yyyy-MM-dd"));
        anxConceptoTO.setFechaFin(anxConcepto.getConFechaFin() == null ? ""
                : UtilsValidacion.fecha(anxConcepto.getConFechaFin(), "yyyy-MM-dd"));
        return anxConceptoTO;
    }

    public static AnxFormaPagoTO convertirAnxFormaPago_AnxFormaPagoTO(AnxFormaPago anxFormaPago) {
        AnxFormaPagoTO anxFormaPagoTO = new AnxFormaPagoTO();
        anxFormaPagoTO.setFpCodigo(anxFormaPago.getFpCodigo());
        anxFormaPagoTO.setFpDetalle(anxFormaPago.getFpDetalle());
        return anxFormaPagoTO;
    }

    public static AnxSustentoTO convertirAnxSustento_AnxSustentoTO(AnxSustento anxSustento) {
        AnxSustentoTO anxSustentoTO = new AnxSustentoTO();
        anxSustentoTO.setSusCodigo(anxSustento.getSusCodigo());
        anxSustentoTO.setSusDescripcion(anxSustento.getSusDescripcion());
        anxSustentoTO.setSusComprobante(anxSustento.getSusComprobante());
        anxSustentoTO.setSusTipoCreditoTributario(anxSustento.getSusTipoCreditoTributario());
        return anxSustentoTO;
    }

    public static AnxTipoComprobanteTO convertirAnxTipoComprobante_AnxTipoComprobanteTO(
            AnxTipocomprobante anxTipoComprobante) {
        AnxTipoComprobanteTO anxTipoComprobanteTO = new AnxTipoComprobanteTO();
        anxTipoComprobanteTO.setTcCodigo(anxTipoComprobante.getTcCodigo());
        anxTipoComprobanteTO.setTcDescripcion(anxTipoComprobante.getTcDescripcion());
        anxTipoComprobanteTO.setTcTransaccion(anxTipoComprobante.getTcTransaccion());
        anxTipoComprobanteTO.setTcSustento(anxTipoComprobante.getTcSustento());
        anxTipoComprobanteTO.setTcAbreviatura(anxTipoComprobante.getTcAbreviatura());
        return anxTipoComprobanteTO;
    }

    public static AnxTipoListaTransaccionTO convertirAnxTipoListaTransaccion_AnxTipoListaTransaccionTO(
            AnxTipotransaccion anxTipoTransaccion) {
        AnxTipoListaTransaccionTO anxTipoListaTransaccionTO = new AnxTipoListaTransaccionTO();
        anxTipoListaTransaccionTO.setTtCodigo(anxTipoTransaccion.getTtCodigo());
        anxTipoListaTransaccionTO.setTtTransaccion(anxTipoTransaccion.getTtTransaccion());
        anxTipoListaTransaccionTO.setTtId(anxTipoTransaccion.getTtId());
        return anxTipoListaTransaccionTO;
    }

    public static AnxAnuladosTO convertirAnxAnulados_AnxAnuladosTO(AnxAnulados anxAnulados) {
        AnxAnuladosTO anxAnuladosTO = new AnxAnuladosTO();
        anxAnuladosTO.setAnuSecuencial(anxAnulados.getAnuSecuencial());
        anxAnuladosTO.setAnuComprobanteEstablecimiento(anxAnulados.getAnuComprobanteEstablecimiento());
        anxAnuladosTO.setAnuComprobantePuntoEmision(anxAnulados.getAnuComprobantePuntoEmision());
        anxAnuladosTO.setAnuSecuencialInicio(anxAnulados.getAnuSecuencialInicio());
        anxAnuladosTO.setAnuSecuencialFin(anxAnulados.getAnuSecuencialFin());
        anxAnuladosTO.setAnuAutorizacion(anxAnulados.getAnuAutorizacion());
        anxAnuladosTO.setAnuFecha(UtilsValidacion.fecha(anxAnulados.getAnuFecha(), "yyyy-MM-dd"));
        anxAnuladosTO.setTcCodigo(anxAnulados.getTcCodigo().getTcCodigo());
        anxAnuladosTO.setUsrEmpresa(anxAnulados.getUsrEmpresa());
        anxAnuladosTO.setUsrCodigo(anxAnulados.getUsrCodigo());
        anxAnuladosTO.setUsrFechaInserta(UtilsValidacion.fecha(anxAnulados.getUsrFechaInserta(), "yyyy-MM-dd"));

        return anxAnuladosTO;
    }

    public static AnxAnulados convertirAnxAnuladosTO_AnxAnulados(AnxAnuladosTO anxAnuladosTO) {
        AnxAnulados anxAnulados = new AnxAnulados();
        anxAnulados.setAnuSecuencial(anxAnuladosTO.getAnuSecuencial());
        anxAnulados.setAnuComprobanteEstablecimiento(anxAnuladosTO.getAnuComprobanteEstablecimiento());
        anxAnulados.setAnuComprobantePuntoEmision(anxAnuladosTO.getAnuComprobantePuntoEmision());
        anxAnulados.setAnuSecuencialInicio(anxAnuladosTO.getAnuSecuencialInicio());
        anxAnulados.setAnuSecuencialFin(anxAnuladosTO.getAnuSecuencialFin());
        anxAnulados.setAnuAutorizacion(anxAnuladosTO.getAnuAutorizacion());
        anxAnulados.setAnuFecha(UtilsValidacion.fecha(anxAnuladosTO.getAnuFecha(), "yyyy-MM-dd"));
        anxAnulados.setUsrEmpresa(anxAnuladosTO.getUsrEmpresa());
        anxAnulados.setUsrCodigo(anxAnuladosTO.getUsrCodigo());
        anxAnulados.setUsrFechaInserta(UtilsValidacion.fechaString_Date(anxAnuladosTO.getUsrFechaInserta()));

        return anxAnulados;
    }

    public static AnxCuentascontables convertirAnxCuentasContablesTO_AnxCuentasContables(
            AnxCuentasContablesTO anxCuentasContablesTO) {
        AnxCuentascontables anxCuentascontables = new AnxCuentascontables();

        anxCuentascontables.setCtaSecuencial(anxCuentasContablesTO.getCtaSecuencial());
        anxCuentascontables.setCtaEmpresa(anxCuentasContablesTO.getCtaEmpresa());
        anxCuentascontables.setCtaIvaPagado(anxCuentasContablesTO.getCtaIvaPagado());
        anxCuentascontables.setCtaIvaPagadoActivoFijo(anxCuentasContablesTO.getCtaIvaPagadoActivoFijo());
        anxCuentascontables.setCtaIvaPagadoGasto(anxCuentasContablesTO.getCtaIvaPagadoGasto());
        anxCuentascontables.setCtaIvaCobrado(anxCuentasContablesTO.getCtaIvaCobrado());
        anxCuentascontables.setCtaRetFteIvaPagado(anxCuentasContablesTO.getCtaRetFteIvaPagado());
        anxCuentascontables.setCtaRetFteIvaCobrado(anxCuentasContablesTO.getCtaRetFteIvaCobrado());
        anxCuentascontables.setCtaRetFteIvaAsumido(anxCuentasContablesTO.getCtaRetFteIvaAsumido());
        anxCuentascontables.setCtaRetFteIrPagado(anxCuentasContablesTO.getCtaRetFteIrPagado());
        anxCuentascontables.setCtaRetFteIrCobrado(anxCuentasContablesTO.getCtaRetFteIrCobrado());
        anxCuentascontables.setCtaRetFteIrAsumido(anxCuentasContablesTO.getCtaRetFteIrAsumido());
        anxCuentascontables.setCtaOtrosImpuestos(anxCuentasContablesTO.getCtaOtrosImpuestos());
        anxCuentascontables.setCtaCuentasPorCobrar(anxCuentasContablesTO.getCtaCuentasPorCobrar());
        anxCuentascontables.setCtaCuentasPorPagar(anxCuentasContablesTO.getCtaCuentasPorPagar());
        anxCuentascontables.setCtaCuentasPorPagarActivoFijo(anxCuentasContablesTO.getCtaCuentasPorPagarActivoFijo());
        anxCuentascontables.setCtaAnticiposDeClientes(anxCuentasContablesTO.getCtaAnticiposDeClientes());
        anxCuentascontables.setCtaAnticiposAProveedores(anxCuentasContablesTO.getCtaAnticiposAProveedores());
        anxCuentascontables.setCtaInventarioProductosProceso(anxCuentasContablesTO.getCtaInventarioProductosProceso());
        anxCuentascontables.setCtaCostoProductosProceso(anxCuentasContablesTO.getCtaCostoProductosProceso());
        anxCuentascontables.setCtaUtilidadEjercicio(anxCuentasContablesTO.getCtaUtilidadEjercicio());
        anxCuentascontables.setCtaPropina(anxCuentasContablesTO.getCtaPropina());
        anxCuentascontables.setCtaCuentasMercaderiaTransito(anxCuentasContablesTO.getCtaCuentasMercaderiaTransito());
        anxCuentascontables.setCtaCostoInicialProductosProceso(anxCuentasContablesTO.getCtaCostoInicialProductosProceso());
        anxCuentascontables.setCtaCostoFinalProductosProceso(anxCuentasContablesTO.getCtaCostoFinalProductosProceso());
        return anxCuentascontables;
    }

    public static AnxCompra convertirAnxCompra_AnxCompra(AnxCompra anxCompra) {
        // if (anxCompra != null){
        AnxCompra anxCompras = new AnxCompra();
        anxCompras.setAnxCompraPK(new AnxCompraPK(anxCompra.getAnxCompraPK().getCompEmpresa(),
                anxCompra.getAnxCompraPK().getCompPeriodo(), anxCompra.getAnxCompraPK().getCompMotivo(),
                anxCompra.getAnxCompraPK().getCompNumero()));
        /*
		 *
		 *
		 * comp_modificado_documento_empresa character(7),
		 * comp_modificado_documento_tipo character(2),
		 * comp_modificado_documento_numero character(17),
		 * comp_modificado_autorizacion character(37), ret_impresa boolean NOT
		 * NULL, ret_entregada boolean NOT NULL, ret_electronica boolean NOT
		 * NULL,
         */

        anxCompras.setCompSustentotributario(anxCompra.getCompSustentotributario());
        anxCompras.setCompAutorizacion(anxCompra.getCompAutorizacion());
        anxCompras.setCompFechaEmision(anxCompra.getCompFechaEmision());
        anxCompras.setCompFechaCaduca(anxCompra.getCompFechaCaduca());
        anxCompras.setCompFechaRecepcion(anxCompra.getCompFechaRecepcion());
        anxCompras.setCompBase0(anxCompra.getCompBase0());
        anxCompras.setCompBaseimponible(anxCompra.getCompBaseimponible());
        anxCompras.setCompBasenoobjetoiva(anxCompra.getCompBasenoobjetoiva());
        anxCompras.setCompMontoice(anxCompra.getCompMontoice());
        anxCompras.setCompMontoiva(anxCompra.getCompMontoiva());
        anxCompras.setCompBaseivabienes(anxCompra.getCompBaseivabienes());
        anxCompras.setCompBaseivaservicios(anxCompra.getCompBaseivaservicios());
        anxCompras.setCompBaseivaserviciosprofesionales(anxCompra.getCompBaseivaserviciosprofesionales());

        anxCompras.setCompPorcentajebienes(anxCompra.getCompPorcentajebienes());
        anxCompras.setCompPorcentajeservicios(anxCompra.getCompPorcentajeservicios());
        anxCompras.setCompPorcentajeserviciosprofesionales(anxCompra.getCompPorcentajeserviciosprofesionales());
        anxCompras.setCompValorbienes(anxCompra.getCompValorbienes());
        anxCompras.setCompValorservicios(anxCompra.getCompValorservicios());
        anxCompras.setCompValorserviciosprofesionales(anxCompra.getCompValorserviciosprofesionales());
        anxCompras.setCompRetencionEmpresa(anxCompra.getCompRetencionEmpresa());
        anxCompras.setCompRetencionNumero(anxCompra.getCompRetencionNumero());
        anxCompras.setCompRetencionAutorizacion(anxCompra.getCompRetencionAutorizacion());

        anxCompras.setCompRetencionFechaEmision(anxCompra.getCompRetencionFechaEmision());

        anxCompras.setCompModificadoDocumentoEmpresa(anxCompra.getCompModificadoDocumentoEmpresa());
        anxCompras.setCompModificadoDocumentoTipo(anxCompra.getCompModificadoDocumentoTipo());
        anxCompras.setCompModificadoDocumentoNumero(anxCompra.getCompModificadoDocumentoNumero());
        anxCompras.setCompModificadoAutorizacion(anxCompra.getCompModificadoAutorizacion());
        anxCompras.setRetImpresa(anxCompra.getRetImpresa());
        anxCompras.setRetEntregada(anxCompra.getRetEntregada());
        anxCompras.setRetElectronica(anxCompra.getRetElectronica());

        return anxCompras;
    }

    public static AnxCompraFormaPago convertirAnxCompraFormaPagoTO_AnxCompraFormaPago(
            AnxCompraFormaPagoTO anxCompraFormaPagoTO) {
        AnxCompraFormaPago anxCompraFormaPago = new AnxCompraFormaPago();
        anxCompraFormaPago.setDetSecuencial(anxCompraFormaPagoTO.getDetSecuencial());
        return anxCompraFormaPago;
    }

    public static AnxUrlWebServices convertirAnxUrlWebServicesTO_AnxUrlWebServices(
            anxUrlWebServicesTO anxUrlWebServicesTO) {
        AnxUrlWebServices anxUrlWebServices = new AnxUrlWebServices();
        anxUrlWebServices.setUrlSecuencial(anxUrlWebServicesTO.getUrlSecuencial());
        anxUrlWebServices.setUrlAmbienteProduccion(anxUrlWebServicesTO.getUrlAmbienteProduccion());
        anxUrlWebServices.setUrlAmbientePruebas(anxUrlWebServicesTO.getUrlAmbientePruebas());
        return anxUrlWebServices;
    }

    public static AnxVentaElectronica convertirAnxVentaElectronicaTO_AnxVentaElectronica(
            AnxVentaElectronicaTO anxVentaElectronicaTO) {
        AnxVentaElectronica anxVentaElectronica = new AnxVentaElectronica();
        anxVentaElectronica.setESecuencial(anxVentaElectronicaTO.geteSecuencial());
        anxVentaElectronica.setETipoAmbiente(anxVentaElectronicaTO.geteTipoAmbiente());
        anxVentaElectronica.setEClaveAcceso(anxVentaElectronicaTO.geteClaveAcceso());
        anxVentaElectronica.setEEstado(anxVentaElectronicaTO.geteEstado());
        anxVentaElectronica.setEAutorizacionNumero(anxVentaElectronicaTO.geteAutorizacionNumero());
        anxVentaElectronica.setEAutorizacionFecha(
                UtilsValidacion.fecha(anxVentaElectronicaTO.geteAutorizacionFecha(), "yyyy-MM-dd HH:mm:ss"));// ,
        // "yyyy-mm-dd
        // hh:mm:ss"
        anxVentaElectronica.setEXml(anxVentaElectronicaTO.geteXml());
        anxVentaElectronica.setEEnviadoPorCorreo(anxVentaElectronicaTO.geteEnviadoPorCorreo());
        anxVentaElectronica.setVtaEmpresa(anxVentaElectronicaTO.getVtaEmpresa());
        anxVentaElectronica.setVtaPeriodo(anxVentaElectronicaTO.getVtaPeriodo());
        anxVentaElectronica.setVtaMotivo(anxVentaElectronicaTO.getVtaMotivo());
        anxVentaElectronica.setVtaNumero(anxVentaElectronicaTO.getVtaNumero());
        anxVentaElectronica.setUsrEmpresa(anxVentaElectronicaTO.getUsrEmpresa());
        anxVentaElectronica.setUsrCodigo(anxVentaElectronicaTO.getUsrCodigo());
        anxVentaElectronica
                .setUsrFechaInserta(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss"));
        // anxVentaElectronica.setUsrFechaInserta(anxVentaElectronicaTO.getUsrFechaInserta());
        return anxVentaElectronica;
    }

    public static AnxCompraElectronica convertirAnxVentaElectronicaTO_AnxVentaElectronica(
            AnxCompraElectronicaTO anxCompraElectronicaTO) {

        AnxCompraElectronica anxCompraElectronica = new AnxCompraElectronica();
        anxCompraElectronica.setESecuencial(anxCompraElectronicaTO.geteSecuencial());
        anxCompraElectronica.setETipoAmbiente(anxCompraElectronicaTO.geteTipoAmbiente());
        anxCompraElectronica.setEClaveAcceso(anxCompraElectronicaTO.geteClaveAcceso());
        anxCompraElectronica.setEEstado(anxCompraElectronicaTO.geteEstado());
        anxCompraElectronica.setEAutorizacionNumero(anxCompraElectronicaTO.geteAutorizacionNumero());
        anxCompraElectronica.setEAutorizacionFecha(
                UtilsValidacion.fechaString_Date(anxCompraElectronicaTO.geteAutorizacionFecha()));// ,
        // "yyyy-mm-dd
        // hh:mm:ss"

        anxCompraElectronica.setEXml(anxCompraElectronicaTO.geteXml());

        anxCompraElectronica.setEEnviadoPorCorreo(anxCompraElectronicaTO.geteEnviadoPorCorreo());
        anxCompraElectronica.setAnxCompra(
                new AnxCompra(anxCompraElectronicaTO.getCompEmpresa(), anxCompraElectronicaTO.getCompPeriodo(),
                        anxCompraElectronicaTO.getCompMotivo(), anxCompraElectronicaTO.getCompNumero()));
        anxCompraElectronica.setUsrEmpresa(anxCompraElectronicaTO.getUsrEmpresa());
        anxCompraElectronica.setUsrCodigo(anxCompraElectronicaTO.getUsrCodigo());
        anxCompraElectronica
                .setUsrFechaInserta(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss"));
        // anxVentaElectronica.setUsrFechaInserta(anxVentaElectronicaTO.getUsrFechaInserta());
        return anxCompraElectronica;
    }

    public static AnxGuiaRemisionElectronica convertirAnxGuiaRemisionElectronicaTO_AnxGuiaRemisionElectronica(AnxGuiaRemisionElectronicaTO anxGuiaRemisionElectronicaTO) {
        AnxGuiaRemisionElectronica anxGuiaRemisionElectronica = new AnxGuiaRemisionElectronica();
        anxGuiaRemisionElectronica.seteSecuencial(anxGuiaRemisionElectronicaTO.geteSecuencial());
        anxGuiaRemisionElectronica.seteTipoAmbiente(anxGuiaRemisionElectronicaTO.geteTipoAmbiente());
        anxGuiaRemisionElectronica.seteClaveAcceso(anxGuiaRemisionElectronicaTO.geteClaveAcceso());
        anxGuiaRemisionElectronica.seteEstado(anxGuiaRemisionElectronicaTO.geteEstado());
        anxGuiaRemisionElectronica.seteAutorizacionNumero(anxGuiaRemisionElectronicaTO.geteAutorizacionNumero());
        anxGuiaRemisionElectronica.seteAutorizacionFecha(UtilsValidacion.fecha(anxGuiaRemisionElectronicaTO.geteAutorizacionFecha(), "yyyy-MM-dd HH:mm:ss"));// ,
        // "yyyy-mm-dd
        anxGuiaRemisionElectronica.seteXml(anxGuiaRemisionElectronicaTO.geteXml());
        anxGuiaRemisionElectronica.seteEnviadoPorCorreo(anxGuiaRemisionElectronicaTO.geteEnviadoPorCorreo());
        anxGuiaRemisionElectronica.setGuiaEmpresa(anxGuiaRemisionElectronicaTO.getGuiaEmpresa());
        anxGuiaRemisionElectronica.setGuiaPeriodo(anxGuiaRemisionElectronicaTO.getGuiaPeriodo());
        anxGuiaRemisionElectronica.setGuiaNumero(anxGuiaRemisionElectronicaTO.getGuiaNumero());
        anxGuiaRemisionElectronica.setUsrEmpresa(anxGuiaRemisionElectronicaTO.getUsrEmpresa());
        anxGuiaRemisionElectronica.setUsrCodigo(anxGuiaRemisionElectronicaTO.getUsrCodigo());
        anxGuiaRemisionElectronica.setUsrFechaInserta(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss"));
        return anxGuiaRemisionElectronica;
    }

    public static AnxLiquidacionComprasElectronica convertirAnxLiquidacionComprasElectronicaTO_AnxLiquidacionComprasElectronica(AnxLiquidacionComprasElectronicaTO anxLiquidacionComprasElectronicaTO) {
        AnxLiquidacionComprasElectronica anxLiquidacionComprasElectronica = new AnxLiquidacionComprasElectronica();
        anxLiquidacionComprasElectronica.seteSecuencial(anxLiquidacionComprasElectronicaTO.geteSecuencial());
        anxLiquidacionComprasElectronica.seteTipoAmbiente(anxLiquidacionComprasElectronicaTO.geteTipoAmbiente());
        anxLiquidacionComprasElectronica.seteClaveAcceso(anxLiquidacionComprasElectronicaTO.geteClaveAcceso());
        anxLiquidacionComprasElectronica.seteEstado(anxLiquidacionComprasElectronicaTO.geteEstado());
        anxLiquidacionComprasElectronica.seteAutorizacionNumero(anxLiquidacionComprasElectronicaTO.geteAutorizacionNumero());
        anxLiquidacionComprasElectronica.seteAutorizacionFecha(UtilsValidacion.fecha(anxLiquidacionComprasElectronicaTO.geteAutorizacionFecha(), "yyyy-MM-dd HH:mm:ss"));// ,
        anxLiquidacionComprasElectronica.seteXml(anxLiquidacionComprasElectronicaTO.geteXml());
        anxLiquidacionComprasElectronica.seteEnviadoPorCorreo(anxLiquidacionComprasElectronicaTO.geteEnviadoPorCorreo());
        anxLiquidacionComprasElectronica.setCompEmpresa(anxLiquidacionComprasElectronicaTO.getCompEmpresa());
        anxLiquidacionComprasElectronica.setCompPeriodo(anxLiquidacionComprasElectronicaTO.getCompPeriodo());
        anxLiquidacionComprasElectronica.setCompMotivo(anxLiquidacionComprasElectronicaTO.getCompMotivo());
        anxLiquidacionComprasElectronica.setCompNumero(anxLiquidacionComprasElectronicaTO.getCompNumero());
        anxLiquidacionComprasElectronica.setUsrEmpresa(anxLiquidacionComprasElectronicaTO.getUsrEmpresa());
        anxLiquidacionComprasElectronica.setUsrCodigo(anxLiquidacionComprasElectronicaTO.getUsrCodigo());
        anxLiquidacionComprasElectronica.setUsrFechaInserta(UtilsValidacion.fecha(UtilsValidacion.fechaSistema(), "yyyy-MM-dd HH:mm:ss"));
        return anxLiquidacionComprasElectronica;
    }

    public static AnxVentaExportacion convertirAnxVentaExportacionTO_AnxVentaExportacion(AnxVentaExportacionTO anxVentaExportacionTO) {
        AnxVentaExportacion anxVentaExportacion = new AnxVentaExportacion();
        anxVentaExportacion.setExpSecuencial(anxVentaExportacionTO.getExpSecuencial());
        anxVentaExportacion.setExpTipoRegimenFiscal(anxVentaExportacionTO.getExpTipoRegimenFiscal());
        anxVentaExportacion.setExpRegimenGeneral(anxVentaExportacionTO.getExpRegimenGeneral());
        anxVentaExportacion.setExpParaisoFiscal(anxVentaExportacionTO.getExpParaisoFiscal());
        anxVentaExportacion.setExpRegimenFiscalPreferente(anxVentaExportacionTO.getExpRegimenFiscalPreferente());
        anxVentaExportacion.setExpPaisEfectuaExportacion(anxVentaExportacionTO.getExpPaisEfectuaExportacion());
        anxVentaExportacion.setExpFechaExportacion(UtilsValidacion.fecha(anxVentaExportacionTO.getExpFechaExportacion(), "yyyy-MM-dd"));// 
        anxVentaExportacion.setExpValorFobExterior(anxVentaExportacionTO.getExpValorFobExterior());
        anxVentaExportacion.setExpValorFobLocal(anxVentaExportacionTO.getExpValorFobLocal());
        anxVentaExportacion.setExpTipoExportacion(anxVentaExportacionTO.getExpTipoExportacion());
        anxVentaExportacion.setExpRefrendoDocumentoTransporte(anxVentaExportacionTO.getExpRefrendoDocumentoTransporte());
        anxVentaExportacion.setExpRefrendoDistrito(anxVentaExportacionTO.getExpRefrendoDistrito());
        anxVentaExportacion.setExpRefrendoAnio(anxVentaExportacionTO.getExpRefrendoAnio());
        anxVentaExportacion.setExpRefrendoRegimen(anxVentaExportacionTO.getExpRefrendoRegimen());
        anxVentaExportacion.setExpRefrendoCorrelativo(anxVentaExportacionTO.getExpRefrendoCorrelativo());
        anxVentaExportacion.setExpTipoIngresoExterior(anxVentaExportacionTO.getExpTipoIngresoExterior());
        anxVentaExportacion.setExpImpuestoPagadoExterior(anxVentaExportacionTO.getExpImpuestoPagadoExterior());
        anxVentaExportacion.setExpObservaciones(anxVentaExportacionTO.getExpObservaciones());
        anxVentaExportacion.setVtaEmpresa(anxVentaExportacionTO.getVtaEmpresa());
        anxVentaExportacion.setVtaPeriodo(anxVentaExportacionTO.getVtaPeriodo());
        anxVentaExportacion.setVtaMotivo(anxVentaExportacionTO.getVtaMotivo());
        anxVentaExportacion.setVtaNumero(anxVentaExportacionTO.getVtaNumero());
        anxVentaExportacion.setExpNumeroFue(anxVentaExportacionTO.getExpNumeroFue());
        anxVentaExportacion.setExpVerificador(anxVentaExportacionTO.getExpVerificador());
        anxVentaExportacion.setExpFactura(anxVentaExportacionTO.getExpFactura());
        anxVentaExportacion.setExpLugar(anxVentaExportacionTO.getExpLugar());
        anxVentaExportacion.setExpPuertoEmbarque(anxVentaExportacionTO.getExpPuertoEmbarque());
        anxVentaExportacion.setExpPuertoDestino(anxVentaExportacionTO.getExpPuertoDestino());
        anxVentaExportacion.setExpPaisDestino(anxVentaExportacionTO.getExpPaisDestino());
        anxVentaExportacion.setExpFlete(anxVentaExportacionTO.getExpFlete());
        anxVentaExportacion.setExpSeguro(anxVentaExportacionTO.getExpSeguro());
        anxVentaExportacion.setExpGastosAduaneros(anxVentaExportacionTO.getExpGastosAduaneros());
        anxVentaExportacion.setExpTransporteOtros(anxVentaExportacionTO.getExpTransporteOtros());
        return anxVentaExportacion;
    }

    public static AnxAnulados convertirAnxAnulados_AnxAnulados(AnxAnulados anxAnulados) {
        AnxAnulados anxAnuladosAux = new AnxAnulados();
        anxAnuladosAux.setAnuSecuencial(anxAnulados.getAnuSecuencial());
        anxAnuladosAux.setAnuComprobanteEstablecimiento(anxAnulados.getAnuComprobanteEstablecimiento());
        anxAnuladosAux.setAnuComprobantePuntoEmision(anxAnulados.getAnuComprobantePuntoEmision());
        anxAnuladosAux.setAnuSecuencialInicio(anxAnulados.getAnuSecuencialInicio());
        anxAnuladosAux.setAnuSecuencialFin(anxAnulados.getAnuSecuencialFin());
        anxAnuladosAux.setAnuAutorizacion(anxAnulados.getAnuAutorizacion());
        anxAnuladosAux.setAnuFecha(anxAnulados.getAnuFecha());
        anxAnuladosAux.setTcCodigo(anxAnulados.getTcCodigo());
        anxAnuladosAux.setUsrEmpresa(anxAnulados.getUsrEmpresa());
        anxAnuladosAux.setUsrCodigo(anxAnulados.getUsrCodigo());
        anxAnuladosAux.setUsrFechaInserta(anxAnulados.getUsrFechaInserta());

        return anxAnuladosAux;
    }
}
