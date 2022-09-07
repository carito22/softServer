package ec.com.todocompu.ShrimpSoftServer.util;

import java.math.BigDecimal;

import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasFlujoDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasFlujoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContabilizarComprasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContabilizarVentasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConTipoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasFlujo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasFlujoDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasFlujoDetallePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasFlujoPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscina;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;

public class ConversionesContabilidad {

    public static ConTipo convertirConTipoTO_ConTipo(ConTipoTO conTipoTO) {
        ConTipo conTipo = new ConTipo();
        conTipo.setConTipoPK(new ConTipoPK(conTipoTO.getEmpCodigo(), conTipoTO.getTipCodigo()));
        conTipo.setTipDetalle(conTipoTO.getTipDetalle());
        conTipo.setTipModulo(conTipoTO.getTipModulo());
        conTipo.setTipTipoPrincipal(conTipoTO.getTipTipoPrincipal());
        conTipo.setTipInactivo(conTipoTO.getTipInactivo());
        conTipo.setUsrCodigo(conTipoTO.getUsrInsertaTipo());
        conTipo.setUsrEmpresa(conTipoTO.getEmpCodigo());
        return conTipo;
    }

    public static ConTipoTO convertirConTipo_ConTipoTO(ConTipo conTipo) {
        ConTipoTO conTipoTO = new ConTipoTO();
        conTipoTO.setTipCodigo(conTipo.getConTipoPK().getTipCodigo());
        conTipoTO.setTipDetalle(conTipo.getTipDetalle());
        conTipoTO.setTipModulo(conTipo.getTipModulo());
        conTipoTO.setTipTipoPrincipal(conTipo.getTipTipoPrincipal());
        conTipoTO.setTipInactivo(conTipo.getTipInactivo());
        conTipoTO.setUsrInsertaTipo(conTipo.getUsrCodigo());
        return conTipoTO;
    }

    public static ConCuentas convertirConCuentasTO_ConCuentas(ConCuentasTO conCuentasTO) {
        ConCuentas conCuentas = new ConCuentas();
        conCuentas.setConCuentasPK(new ConCuentasPK(conCuentasTO.getEmpCodigo(), conCuentasTO.getCuentaCodigo()));
        conCuentas.setCtaDetalle(conCuentasTO.getCuentaDetalle());
        conCuentas.setCtaActivo(conCuentasTO.getCuentaActivo());
        conCuentas.setCtaBloqueada((conCuentasTO.getCuentaBloqueada() != false && conCuentasTO.getCuentaBloqueada() != null));
        conCuentas.setCtaRelacionada(conCuentasTO.getCtaRelacionada() != null ? conCuentasTO.getCtaRelacionada() : false);
        conCuentas.setCtaGrupoComparativo(conCuentasTO.getCtaGrupoComparativo());
        conCuentas.setCtaDetalleComparativo(conCuentasTO.getCtaDetalleComparativo());
        conCuentas.setUsrCodigo(conCuentasTO.getUsrInsertaCuenta());
        conCuentas.setUsrEmpresa(conCuentasTO.getEmpCodigo());
        conCuentas.setUsrFechaInserta(UtilsValidacion.fecha(conCuentasTO.getUsrFechaInsertaCuenta(), "yyyy-MM-dd"));
        return conCuentas;
    }

    public static ConCuentasFlujo convertirConCuentasFlujoTO_ConCuentasFlujo(ConCuentasFlujoTO conCuentasFlujoTO) {
        ConCuentasFlujo conCuentasFlujo = new ConCuentasFlujo();
        conCuentasFlujo.setConCuentasFlujoPK(
                new ConCuentasFlujoPK(conCuentasFlujoTO.getFluCodigo(), conCuentasFlujoTO.getCuentaCodigo()));
        conCuentasFlujo.setFluDetalle(conCuentasFlujoTO.getCuentaDetalle());
        conCuentasFlujo.setFluActivo(conCuentasFlujoTO.getCuentaActivo());
        conCuentasFlujo.setUsrCodigo(conCuentasFlujoTO.getUsrInsertaCuenta());
        conCuentasFlujo.setUsrEmpresa(conCuentasFlujoTO.getFluCodigo());
        conCuentasFlujo
                .setUsrFechaInserta(UtilsValidacion.fecha(conCuentasFlujoTO.getUsrFechaInsertaCuenta(), "yyyy-MM-dd"));
        return conCuentasFlujo;
    }

    public static ConCuentasFlujoDetalle convertirConCuentasFlujoDetalleTO_ConCuentasFlujoDetalle(
            ConCuentasFlujoDetalleTO conCuentasFlujoDetalleTO) {
        ConCuentasFlujoDetalle conCuentasFlujoDetalle = new ConCuentasFlujoDetalle();
        conCuentasFlujoDetalle
                .setConCuentasFlujoDetallePK(new ConCuentasFlujoDetallePK(conCuentasFlujoDetalleTO.getFluEmpresa(),
                        conCuentasFlujoDetalleTO.getCtaCodigo(), conCuentasFlujoDetalleTO.getDetDebitoCredito()));
        conCuentasFlujoDetalle.setConCuentas(new ConCuentas(
                new ConCuentasPK(conCuentasFlujoDetalleTO.getCtaEmpresa(), conCuentasFlujoDetalleTO.getCtaCodigo())));
        conCuentasFlujoDetalle
                .setConCuentasFlujo(new ConCuentasFlujo(new ConCuentasFlujoPK(conCuentasFlujoDetalleTO.getFluEmpresa(),
                        conCuentasFlujoDetalleTO.getFluCodigo())));
        conCuentasFlujoDetalle.setDetCuentaFlujo(conCuentasFlujoDetalleTO.getDetCuentaFlujo());
        conCuentasFlujoDetalle.setUsrCodigo(conCuentasFlujoDetalleTO.getUsrCodigo());
        conCuentasFlujoDetalle.setUsrEmpresa(conCuentasFlujoDetalleTO.getUsrEmpresa());
        conCuentasFlujoDetalle
                .setUsrFechaInserta(UtilsValidacion.fecha(conCuentasFlujoDetalleTO.getUsrFechaInserta(), "yyyy-MM-dd"));
        return conCuentasFlujoDetalle;
    }

    public static ConContable convertirConContableTO_ConContable(ConContableTO conContableTO) {
        ConContable conContable = new ConContable();
        conContable.setConContablePK(new ConContablePK(conContableTO.getEmpCodigo(), conContableTO.getPerCodigo(),
                conContableTO.getTipCodigo(), conContableTO.getConNumero()));
        conContable.setConFecha(UtilsValidacion.fecha(conContableTO.getConFecha(), "yyyy-MM-dd"));
        conContable.setConPendiente(conContableTO.getConPendiente());
        conContable.setConBloqueado(conContableTO.getConBloqueado());
        conContable.setConAnulado(conContableTO.getConAnulado());
        conContable.setConGenerado(conContableTO.getConGenerado());
        // conContable.setConDctoOrigen(conContableTO.getConDocOrigen());
        conContable.setConConcepto(conContableTO.getConConcepto());
        conContable.setConDetalle(conContableTO.getConDetalle());
        conContable.setConObservaciones(conContableTO.getConObservaciones());
        conContable.setUsrCodigo(conContableTO.getUsrInsertaContable());
        conContable.setUsrEmpresa(conContableTO.getEmpCodigo());
        conContable.setConReversado(conContableTO.getConReversado() == null ? false : conContableTO.getConReversado());

        conContable.setConCodigo(conContableTO.getConCodigo());
        conContable.setConReferencia(conContableTO.getConReferencia());
        return conContable;
    }

    public static ConDetalle convertirConDetalle_ConDetalle(ConDetalle conDetalleAux) {
        ConDetalle conDetalle = new ConDetalle();
        conDetalle.setDetSecuencia(conDetalleAux.getDetSecuencia());
        conDetalle.setPrdSector(conDetalleAux.getPrdSector());
        conDetalle.setPrdPiscina(conDetalleAux.getPrdPiscina());
        conDetalle.setDetDocumento(conDetalleAux.getDetDocumento());
        conDetalle.setDetDebitoCredito(conDetalleAux.getDetDebitoCredito());
        conDetalle.setDetValor(conDetalleAux.getDetValor());
        conDetalle.setDetSaldo(conDetalleAux.getDetSaldo());
        conDetalle.setDetGenerado(conDetalleAux.getDetGenerado());
        conDetalle.setDetReferencia(conDetalleAux.getDetReferencia());
        conDetalle.setDetObservaciones(conDetalleAux.getDetObservaciones());
        conDetalle.setDetOrden(conDetalleAux.getDetOrden());
        return conDetalle;
    }

    public static ConDetalle convertirConDetalleTO_ConDetalle(ConDetalleTO conDetalleTO) {
        ConDetalle conDetalle = new ConDetalle();
        conDetalle.setDetSecuencia(conDetalleTO.getDetSecuencia().longValue());
        conDetalle.setDetDocumento(conDetalleTO.getDetDocumento());
        conDetalle.setDetDebitoCredito(conDetalleTO.getDetDebitoCredito());
        conDetalle.setDetValor(conDetalleTO.getDetValor());
        conDetalle.setDetSaldo(BigDecimal.ZERO);
        conDetalle.setDetGenerado(conDetalleTO.getDetGenerado());
        conDetalle.setDetReferencia(conDetalleTO.getDetReferencia());
        conDetalle.setDetObservaciones(conDetalleTO.getDetObservaciones());
        conDetalle.setDetOrden(conDetalleTO.getDetOrden());

        String sector = conDetalleTO.getSecCodigo().trim();

        conDetalle.setPrdSector(new PrdSector(conDetalleTO.getEmpCodigo(), sector));

        if (conDetalleTO.getEmpCodigo() != null && sector != null && conDetalleTO.getPisNumero() != null) {
            conDetalle.setPrdPiscina(new PrdPiscina(conDetalleTO.getEmpCodigo(), sector, conDetalleTO.getPisNumero()));
        }
        return conDetalle;
    }

    public static ConDetalle convertirConFunContabilizarCompraDetalleTO_ConDetalle(
            ConFunContabilizarComprasDetalleTO conDetalleTO) {
        ConDetalle conDetalle = new ConDetalle();
        conDetalle.setDetSecuencia(conDetalleTO.getDetSecuencia());
        conDetalle.setDetDocumento(conDetalleTO.getDetDocumento());
        conDetalle.setDetDebitoCredito(conDetalleTO.getDetDebitoCredito());
        conDetalle.setDetValor(conDetalleTO.getDetValor());
        conDetalle.setDetSaldo(conDetalleTO.getDetSaldo());
        conDetalle.setDetGenerado(conDetalleTO.getDetGenerado());
        conDetalle.setDetReferencia(conDetalleTO.getDetReferencia());
        conDetalle.setDetObservaciones(conDetalleTO.getDetObservaciones());
        conDetalle.setDetOrden(conDetalleTO.getDetOrden());
        conDetalle.setPrdSector(new PrdSector(conDetalleTO.getSecEmpresa(), conDetalleTO.getSecCodigo().trim()));

        if (conDetalleTO.getPisEmpresa() != null && conDetalleTO.getPisSector() != null
                && conDetalleTO.getPisNumero() != null) {
            conDetalle.setPrdPiscina(new PrdPiscina(conDetalleTO.getPisEmpresa(), conDetalleTO.getPisSector(),
                    conDetalleTO.getPisNumero()));
        }
        conDetalle.setConContable(new ConContable(new ConContablePK(conDetalleTO.getConEmpresa(),
                conDetalleTO.getConPeriodo(), conDetalleTO.getConTipo(), conDetalleTO.getConNumero())));
        conDetalle.setConCuentas(
                new ConCuentas(new ConCuentasPK(conDetalleTO.getCtaEmpresa(), conDetalleTO.getCtaCodigo())));
        return conDetalle;
    }

    public static ConDetalle convertirConFunContabilizarVentasDetalleTO_ConDetalle(
            ConFunContabilizarVentasDetalleTO conDetalleTO) {
        ConDetalle conDetalle = new ConDetalle();
        conDetalle.setDetSecuencia(conDetalleTO.getDetSecuencia());
        conDetalle.setDetDocumento(conDetalleTO.getDetDocumento());
        conDetalle.setDetDebitoCredito(conDetalleTO.getDetDebitoCredito());
        conDetalle.setDetValor(conDetalleTO.getDetValor());
        conDetalle.setDetSaldo(conDetalleTO.getDetSaldo());
        conDetalle.setDetGenerado(conDetalleTO.getDetGenerado());
        conDetalle.setDetReferencia(conDetalleTO.getDetReferencia());
        conDetalle.setDetObservaciones(conDetalleTO.getDetObservaciones());
        conDetalle.setDetOrden(conDetalleTO.getDetOrden());
        conDetalle.setPrdSector(new PrdSector(conDetalleTO.getSecEmpresa(), conDetalleTO.getSecCodigo().trim()));
        if (conDetalleTO.getPisEmpresa() != null && conDetalleTO.getPisSector() != null
                && conDetalleTO.getPisNumero() != null) {
            conDetalle.setPrdPiscina(new PrdPiscina(conDetalleTO.getPisEmpresa(), conDetalleTO.getPisSector(),
                    conDetalleTO.getPisNumero()));
        }
        conDetalle.setConContable(new ConContable(new ConContablePK(conDetalleTO.getConEmpresa(),
                conDetalleTO.getConPeriodo(), conDetalleTO.getConTipo(), conDetalleTO.getConNumero())));
        conDetalle.setConCuentas(
                new ConCuentas(new ConCuentasPK(conDetalleTO.getCtaEmpresa(), conDetalleTO.getCtaCodigo())));
        return conDetalle;
    }

    public static ConContable convertirConContable_ConContable(ConContable conContable2) {
        ConContable conContable = new ConContable();
        conContable.setConContablePK(conContable2.getConContablePK());
        conContable.setConCodigo(conContable2.getConCodigo());
        conContable.setConFecha(conContable2.getConFecha());
        conContable.setConPendiente(conContable2.getConPendiente());
        conContable.setConBloqueado(conContable2.getConBloqueado());
        conContable.setConAnulado(conContable2.getConAnulado());
        conContable.setConGenerado(conContable2.getConGenerado());
        conContable.setConReferencia(conContable2.getConReferencia());
        conContable.setConConcepto(conContable2.getConConcepto());
        conContable.setConDetalle(conContable2.getConDetalle());
        conContable.setConObservaciones(conContable2.getConObservaciones());
        conContable.setUsrCodigo(conContable2.getUsrCodigo());
        conContable.setUsrEmpresa(conContable2.getUsrEmpresa());
        conContable.setConReversado(conContable2.getConReversado());
        return conContable;
    }
}
