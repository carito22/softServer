package ec.com.todocompu.ShrimpSoftServer.util;

import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaValesTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajValesConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajCaja;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajCajaPK;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajVales;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajValesConceptos;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajValesConceptosPK;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajValesPK;

public class ConversionesCaja {

    public static CajCaja convertirCajCajaTO_CajCaja(CajCajaTO cajCajaTO) {
        CajCaja cajCaja = new CajCaja();
        cajCaja.setCajCajaPK(new CajCajaPK(cajCajaTO.getCajaEmpresa(), cajCajaTO.getCajaUsuarioResponsable()));

        cajCaja.setPermisoSecuencialFacturas(cajCajaTO.getPermisoSecuencialFacturas());
        cajCaja.setPermisoSecuencialNotasCredito(cajCajaTO.getPermisoSecuencialNotasCredito());
        cajCaja.setPermisoSecuencialNotasDebito(cajCajaTO.getPermisoSecuencialNotasDebito());
        cajCaja.setPermisoSecuencialRetenciones(cajCajaTO.getPermisoSecuencialRetenciones());
        cajCaja.setPermisoSecuencialGuias(cajCajaTO.getPermisoSecuencialGuias());
        cajCaja.setPermisoSecuencialLiquidacionCompras(cajCajaTO.getPermisoSecuencialLiquidacionCompras());
        cajCaja.setPermisoMotivoPermitido(cajCajaTO.getPermisoMotivoPermitido());
        cajCaja.setPermisoBodegaPermitida(cajCajaTO.getPermisoBodegaPermitida());
        cajCaja.setPermisoDocumentoPermitido(cajCajaTO.getPermisoDocumentoPermitido());
        cajCaja.setPermisoFormaPagoPermitida(cajCajaTO.getPermisoFormaPagoPermitida());

        cajCaja.setPermisoCambiarFechaVenta(cajCajaTO.getPermisoCambiarFechaVenta());
        cajCaja.setPermisoCambiarFechaRetencion(cajCajaTO.getPermisoCambiarFechaRetencion());

        cajCaja.setPermisoCambiarPrecio(cajCajaTO.getPermisoCambiarPrecio());
        cajCaja.setPermisoAplicarDescuentos(cajCajaTO.getPermisoAplicarDescuentos());
        cajCaja.setPermisoEliminarFilas(cajCajaTO.getPermisoEliminarFilas());
        cajCaja.setPermisoClienteCrear(cajCajaTO.getPermisoClienteCrear());
        cajCaja.setPermisoClienteCredito(cajCajaTO.getPermisoClienteCredito());
        cajCaja.setPermisoClientePrecioPermitido(cajCajaTO.getPermisoClientePrecioPermitido());

        cajCaja.setUsrEmpresa(cajCajaTO.getUsrEmpresa());
        cajCaja.setUsrCodigo(cajCajaTO.getUsrCodigo());
        cajCaja.setFirmaSecuencial(cajCajaTO.getFirmaSecuencial());
        cajCaja.setUsrFechaInserta(UtilsValidacion.fecha(cajCajaTO.getUsrFechaInserta(), "yyyy-MM-dd"));

        cajCaja.setPermisoUtilizaCodigoBarra(cajCajaTO.getPermisoUtilizaCodigoBarra());
        cajCaja.setPermisoAgruparProductos(cajCajaTO.getPermisoAgruparProductos());
        if (cajCajaTO.getInvProducto() != null && cajCajaTO.getInvProducto().getInvProductoPK() != null) {
            cajCaja.setInvProducto(cajCajaTO.getInvProducto());
        } else {
            cajCaja.setInvProducto(null);
        }

        return cajCaja;
    }

    public static CajVales convertirCajCajaValesTO_CajVales(CajCajaValesTO cajCajaValesTO) {
        CajValesConceptos cajValesConceptos = new CajValesConceptos(
                new CajValesConceptosPK(cajCajaValesTO.getConcEmpresa(), cajCajaValesTO.getConcCodigo()));
        CajVales cajVales = new CajVales();
        cajVales.setCajValesPK(new CajValesPK(cajCajaValesTO.getValeEmpresa(), cajCajaValesTO.getValePeriodo(),
                cajCajaValesTO.getValeMotivo(), cajCajaValesTO.getValeNumero()));
        cajVales.setCajValesConceptos(cajValesConceptos);
        cajVales.setValeBeneficiario(cajCajaValesTO.getValeBeneficiario());
        cajVales.setValeObservaciones(cajCajaValesTO.getValeObservaciones());
        cajVales.setValeAnulado(cajCajaValesTO.getValeAnulado());
        cajVales.setValeFecha(UtilsValidacion.fecha(cajCajaValesTO.getValeFecha(), "yyyy-MM-dd"));
        cajVales.setUsrEmpresa(cajCajaValesTO.getUsrEmpresa());
        cajVales.setValeValor(cajCajaValesTO.getValeValor());
        cajVales.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
        cajVales.setUsrCodigo(cajCajaValesTO.getVaeUsuario());
        return cajVales;
    }

    public static CajValesConceptos convertirCajValesConceptosTO_cajValesConceptos(
            CajValesConceptoTO cajValesConceptosTO) {
        CajValesConceptos cajValesConceptos = new CajValesConceptos();
        cajValesConceptos.setCajValesConceptosPK(
                new CajValesConceptosPK(cajValesConceptosTO.getConcEmpresa(), cajValesConceptosTO.getConcCodigo()));
        cajValesConceptos.setConcDetalle(cajValesConceptosTO.getConcDetalle());
        cajValesConceptos.setConcInactivo(cajValesConceptosTO.getConcInactivo());
        cajValesConceptos.setUsrEmpresa(cajValesConceptosTO.getUsrEmpresa());
        cajValesConceptos.setUsrCodigo(cajValesConceptosTO.getUsrCodigo());
        cajValesConceptos.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
        return cajValesConceptos;
    }

}
