package ec.com.todocompu.ShrimpSoftServer.util;

import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanBanco;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanBancoPK;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarComboPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCobrosSaldoAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarFunPagosSaldoAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosCobrosDetalleAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosDetalleAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosDetalleComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosDetalleFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobros;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosAnticiposPK;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosDetalleAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosDetalleForma;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosDetalleVentas;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosForma;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosPK;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosAnticiposPK;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosDetalleAnticipos;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosDetalleCompras;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosDetalleForma;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosForma;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasPK;

public class ConversionesCar {

    public static CarCobros convertirCarCobrosTO_CarCobros(CarCobrosTO carCobrosTO) throws Exception {
        CarCobros carCobros = new CarCobros();
        carCobros.setCarCobrosPK(new CarCobrosPK(carCobrosTO.getUsrEmpresa(), carCobrosTO.getCobPeriodo(),
                carCobrosTO.getCobTipo(), carCobrosTO.getCobNumero()));
        carCobros.setCobAuxiliar(carCobrosTO.getCobReversado());

        carCobros.setCobSaldoActual(carCobrosTO.getCobSaldoActual());
        carCobros.setCobSaldoAnterior(carCobrosTO.getCobSaldoAnterior());
        carCobros.setCobValor(carCobrosTO.getCobValor());

        carCobros.setCobCodigoTransaccional(carCobrosTO.getCobCodigoTransaccional());
        carCobros.setCliEmpresa(carCobrosTO.getUsrEmpresa());
        carCobros.setCliCodigo(carCobrosTO.getCliCodigo());
        carCobros.setUsrEmpresa(carCobrosTO.getUsrEmpresa());
        carCobros.setUsrCodigo(carCobrosTO.getUsrCodigo());
        carCobros.setUsrFechaInserta(UtilsValidacion.fechaString_Date(carCobrosTO.getUsrFechaInserta()));
        return carCobros;
    }

    public static CarCobrosAnticipos convertirCarCobrosAnticipos_CarCobrosAnticipos(CarCobrosAnticipos carCobrosAnticiposAux) throws Exception {
        CarCobrosAnticipos carCobrosAnticipos = new CarCobrosAnticipos();
        carCobrosAnticipos.setCarCobrosAnticiposPK(carCobrosAnticiposAux.getCarCobrosAnticiposPK());
        carCobrosAnticipos.setAntValor(carCobrosAnticiposAux.getAntValor());
        carCobrosAnticipos.setAntCobrado(carCobrosAnticiposAux.getAntCobrado());
        carCobrosAnticipos.setAntAuxiliar(carCobrosAnticiposAux.isAntAuxiliar());
        carCobrosAnticipos.setCliEmpresa(carCobrosAnticiposAux.getUsrEmpresa());
        carCobrosAnticipos.setCliCodigo(carCobrosAnticiposAux.getCliCodigo());
        carCobrosAnticipos.setSecEmpresa(carCobrosAnticiposAux.getUsrEmpresa());
        carCobrosAnticipos.setSecCodigo(carCobrosAnticiposAux.getSecCodigo());
        carCobrosAnticipos.setFpSecuencial(carCobrosAnticiposAux.getFpSecuencial());
        carCobrosAnticipos.setUsrEmpresa(carCobrosAnticiposAux.getUsrEmpresa());
        carCobrosAnticipos.setUsrCodigo(carCobrosAnticiposAux.getUsrCodigo());
        carCobrosAnticipos.setUsrFechaInserta(carCobrosAnticiposAux.getUsrFechaInserta());
        carCobrosAnticipos.setCarCobrosDetalleAnticiposList(carCobrosAnticiposAux.getCarCobrosDetalleAnticiposList());
        carCobrosAnticipos.setDetCuenta(carCobrosAnticiposAux.getDetCuenta());
        carCobrosAnticipos.setDetFechaVencimiento(carCobrosAnticiposAux.getDetFechaVencimiento());
        return carCobrosAnticipos;
    }

    public static CarCobrosDetalleVentas convertirCarCobrosDetalleVentasTO_CarCobrosDetalleVentas(
            CarCobrosDetalleVentasTO carCobrosDetalleVentasTO) throws Exception {
        CarCobrosDetalleVentas carCobrosDetalleVentas = new CarCobrosDetalleVentas();
        carCobrosDetalleVentas.setDetSecuencial(carCobrosDetalleVentasTO.getDetSecuencial());
        carCobrosDetalleVentas.setDetValor(carCobrosDetalleVentasTO.getDetValor());
        carCobrosDetalleVentas.setCarCobros(new CarCobros(
                new CarCobrosPK(carCobrosDetalleVentasTO.getCobEmpresa(), carCobrosDetalleVentasTO.getCobPeriodo(),
                        carCobrosDetalleVentasTO.getCobTipo(), carCobrosDetalleVentasTO.getCobNumero())));
        carCobrosDetalleVentas.setVtaEmpresa(carCobrosDetalleVentasTO.getVtaEmpresa());
        carCobrosDetalleVentas.setVtaPeriodo(carCobrosDetalleVentasTO.getVtaPeriodo());
        carCobrosDetalleVentas.setVtaMotivo(carCobrosDetalleVentasTO.getVtaMotivo());
        carCobrosDetalleVentas.setVtaNumero(carCobrosDetalleVentasTO.getVtaNumero());
        carCobrosDetalleVentas.setSecEmpresa(carCobrosDetalleVentasTO.getVtaEmpresa());
        carCobrosDetalleVentas.setSecCodigo(carCobrosDetalleVentasTO.getVtaSecCodigo());
        return carCobrosDetalleVentas;
    }

    public static CarCobrosDetalleForma convertirCarCobrosDetalleFormaTO_CarCobrosDetalleForma(
            CarCobrosDetalleFormaTO carCobrosDetalleFormaTO) throws Exception {
        CarCobrosDetalleForma carCobrosDetalleForma = new CarCobrosDetalleForma();
        carCobrosDetalleForma.setDetSecuencial(0);

        carCobrosDetalleForma.setDetBanco(carCobrosDetalleFormaTO.getDetBanco());
        carCobrosDetalleForma.setDetCuenta(carCobrosDetalleFormaTO.getDetCuenta());
        carCobrosDetalleForma
                .setDetFechaVencimiento(UtilsValidacion.fecha(carCobrosDetalleFormaTO.getDetFechaPst(), "yyyy-MM-dd"));

        carCobrosDetalleForma.setDetValor(carCobrosDetalleFormaTO.getDetValor());
        carCobrosDetalleForma.setDetReferencia(carCobrosDetalleFormaTO.getDetReferencia());
        carCobrosDetalleForma.setDetObservaciones(carCobrosDetalleFormaTO.getDetObservaciones());
        carCobrosDetalleForma.setBanCodigo(carCobrosDetalleFormaTO.getBanCodigo());
        carCobrosDetalleForma.setBanEmpresa(carCobrosDetalleFormaTO.getBanEmpresa());
        carCobrosDetalleForma.setCarCobros(new CarCobros(
                new CarCobrosPK(carCobrosDetalleFormaTO.getCobEmpresa(), carCobrosDetalleFormaTO.getCobPeriodo(),
                        carCobrosDetalleFormaTO.getCobMotivo(), carCobrosDetalleFormaTO.getCobNumero())));
        carCobrosDetalleForma.setFpSecuencial(new CarCobrosForma(carCobrosDetalleFormaTO.getFpSecuencial()));
        return carCobrosDetalleForma;

        // CarPagosDetalleForma carPagosDetalleForma = new
        // CarPagosDetalleForma();
        // carPagosDetalleForma.setDetSecuencial(0);
        // carPagosDetalleForma.setDetValor(carPagosDetalleFormaTO.getDetValor());
        // carPagosDetalleForma.setDetReferencia(carPagosDetalleFormaTO.getDetReferencia());
        // carPagosDetalleForma.setDetObservaciones(carPagosDetalleFormaTO.getDetObservaciones());
        // carPagosDetalleForma.setCarPagos(
        // new CarPagos(
        // new CarPagosPK(
        // carPagosDetalleFormaTO.getPagEmpresa(),
        // carPagosDetalleFormaTO.getPagPeriodo(),
        // carPagosDetalleFormaTO.getPagMotivo(),
        // carPagosDetalleFormaTO.getPagNumero())));
        // carPagosDetalleForma.setFpSecuencial(new CarPagosForma(
        // carPagosDetalleFormaTO.getFpSecuencial()));
        // return carPagosDetalleForma;
    }

    public static CarCobrosDetalleAnticipos convertirCarCobrosDetalleAnticiposTO_CarCobrosDetalleAnticipos(
            CarCobrosDetalleAnticiposTO carCobrosDetalleAnticiposTO) throws Exception {
        CarCobrosDetalleAnticipos carCobrosDetalleAnticipos = new CarCobrosDetalleAnticipos();
        carCobrosDetalleAnticipos.setDetSecuencial(0);
        carCobrosDetalleAnticipos.setDetValor(carCobrosDetalleAnticiposTO.getDetValor());
        carCobrosDetalleAnticipos.setDetObservaciones(carCobrosDetalleAnticiposTO.getDetObservaciones());
        carCobrosDetalleAnticipos.setCarCobrosAnticipos(new CarCobrosAnticipos(new CarCobrosAnticiposPK(
                carCobrosDetalleAnticiposTO.getAntEmpresa(), carCobrosDetalleAnticiposTO.getAntPeriodo(),
                carCobrosDetalleAnticiposTO.getAntTipo(), carCobrosDetalleAnticiposTO.getAntNumero())));
        return carCobrosDetalleAnticipos;
    }

    public static CarFunCobrosSaldoAnticipoTO convertirCarListaPagosCobrosDetalleAnticipoTO_CarFunCobrosSaldoAnticipoTO(
            CarListaPagosCobrosDetalleAnticipoTO carListaPagosCobrosDetalleAnticipoTO) throws Exception {
        CarFunCobrosSaldoAnticipoTO cobroAnticipo = new CarFunCobrosSaldoAnticipoTO();
        cobroAnticipo.setAntValor(carListaPagosCobrosDetalleAnticipoTO.getValor());
        cobroAnticipo.setAntTipo(carListaPagosCobrosDetalleAnticipoTO.getAntTipo());
        cobroAnticipo.setAntPeriodo(carListaPagosCobrosDetalleAnticipoTO.getAntPeriodo());
        cobroAnticipo.setAntNumero(carListaPagosCobrosDetalleAnticipoTO.getAntNumero());
        cobroAnticipo.setAntFecha(carListaPagosCobrosDetalleAnticipoTO.getAntFecha());
        return cobroAnticipo;
    }

    public static CarFunPagosSaldoAnticipoTO convertirCarListaPagosCobrosDetalleAnticipoTO_CarFunPagosSaldoAnticipoTO(
            CarListaPagosCobrosDetalleAnticipoTO carListaPagosCobrosDetalleAnticipoTO) throws Exception {
        CarFunPagosSaldoAnticipoTO pagoAnticipo = new CarFunPagosSaldoAnticipoTO();
        pagoAnticipo.setAntValor(carListaPagosCobrosDetalleAnticipoTO.getValor());
        pagoAnticipo.setAntTipo(carListaPagosCobrosDetalleAnticipoTO.getAntTipo());
        pagoAnticipo.setAntPeriodo(carListaPagosCobrosDetalleAnticipoTO.getAntPeriodo());
        pagoAnticipo.setAntNumero(carListaPagosCobrosDetalleAnticipoTO.getAntNumero());
        pagoAnticipo.setAntFecha(carListaPagosCobrosDetalleAnticipoTO.getAntFecha());
        return pagoAnticipo;
    }

    public static CarPagos convertirCarPagosTO_CarPagos(CarPagosTO carPagosTO) throws Exception {
        CarPagos carPagos = new CarPagos();
        carPagos.setCarPagosPK(new CarPagosPK(carPagosTO.getUsrEmpresa(), carPagosTO.getPagPeriodo(),
                carPagosTO.getPagTipo(), carPagosTO.getPagNumero()));
        carPagos.setPagAuxiliar(carPagosTO.getPagReversado());
        carPagos.setProvEmpresa(carPagosTO.getUsrEmpresa());
        carPagos.setProvCodigo(carPagosTO.getProvCodigo());
        carPagos.setUsrEmpresa(carPagosTO.getUsrEmpresa());
        carPagos.setUsrCodigo(carPagosTO.getUsrCodigo());
        carPagos.setPagSaldoActual(carPagosTO.getPagSaldoActual());
        carPagos.setPagValor(carPagosTO.getPagValor());
        carPagos.setPagSaldoAnterior(carPagosTO.getPagSaldoAnterior());
        carPagos.setPagSaldoActual(carPagosTO.getPagSaldoActual());
        carPagos.setPagCodigoTransaccional(carPagosTO.getPagCodigoTransaccional());
        carPagos.setUsrFechaInserta(UtilsValidacion.fechaString_Date(carPagosTO.getUsrFechaInserta()));
        return carPagos;
    }

    public static CarPagos convertirCarPagos_CarPagos(CarPagos carPagosAux) throws Exception {
        CarPagos carPagos = new CarPagos();
        carPagos.setCarPagosPK(carPagosAux.getCarPagosPK());
        carPagos.setPagAuxiliar(carPagosAux.getPagAuxiliar());
        carPagos.setPagSaldoAnterior(carPagosAux.getPagSaldoAnterior());
        carPagos.setPagValor(carPagosAux.getPagValor());
        carPagos.setPagSaldoActual(carPagosAux.getPagSaldoActual());
        carPagos.setProvEmpresa(carPagosAux.getUsrEmpresa());
        carPagos.setProvCodigo(carPagosAux.getProvCodigo());
        carPagos.setUsrEmpresa(carPagosAux.getUsrEmpresa());
        carPagos.setUsrCodigo(carPagosAux.getUsrCodigo());
        carPagos.setUsrFechaInserta(carPagosAux.getUsrFechaInserta());
        carPagos.setPagCodigoTransaccional(carPagosAux.getPagCodigoTransaccional());

        carPagos.setCarPagosDetalleAnticiposList(carPagosAux.getCarPagosDetalleAnticiposList());
        carPagos.setCarPagosDetalleComprasList(carPagosAux.getCarPagosDetalleComprasList());
        carPagos.setCarPagosDetalleFormaList(carPagosAux.getCarPagosDetalleFormaList());
        return carPagos;
    }

    public static CarPagosAnticipos convertirCarPagosAnticipos_CarPagosAnticipos(CarPagosAnticipos carPagosAnticiposAux)
            throws Exception {
        CarPagosAnticipos carPagosAnticipos = new CarPagosAnticipos();
        carPagosAnticipos.setCarPagosAnticiposPK(carPagosAnticiposAux.getCarPagosAnticiposPK());
        carPagosAnticipos.setAntValor(carPagosAnticiposAux.getAntValor());
        carPagosAnticipos.setAntPagado(carPagosAnticiposAux.getAntPagado());
        carPagosAnticipos.setAntAuxiliar(carPagosAnticiposAux.isAntAuxiliar());
        carPagosAnticipos.setProvEmpresa(carPagosAnticiposAux.getUsrEmpresa());
        carPagosAnticipos.setProvCodigo(carPagosAnticiposAux.getProvCodigo());
        carPagosAnticipos.setSecEmpresa(carPagosAnticiposAux.getSecEmpresa());
        carPagosAnticipos.setSecCodigo(carPagosAnticiposAux.getSecCodigo());
        carPagosAnticipos.setUsrEmpresa(carPagosAnticiposAux.getUsrEmpresa());
        carPagosAnticipos.setUsrCodigo(carPagosAnticiposAux.getUsrCodigo());
        carPagosAnticipos.setUsrFechaInserta(carPagosAnticiposAux.getUsrFechaInserta());
        carPagosAnticipos.setFpSecuencial(carPagosAnticiposAux.getFpSecuencial());
        carPagosAnticipos.setCarPagosDetalleAnticiposList(carPagosAnticiposAux.getCarPagosDetalleAnticiposList());
        carPagosAnticipos.setPrdPiscina(carPagosAnticiposAux.getPrdPiscina());

        return carPagosAnticipos;
    }

    public static CarPagosDetalleCompras convertirCarPagosDetalleComprasTO_CarPagosDetalleCompras(
            CarPagosDetalleComprasTO carPagosDetalleComprasTO) throws Exception {
        CarPagosDetalleCompras carPagosDetalleCompras = new CarPagosDetalleCompras();
        carPagosDetalleCompras.setDetSecuencial(carPagosDetalleComprasTO.getDetSecuencial());
        carPagosDetalleCompras.setDetValor(carPagosDetalleComprasTO.getDetValor());
        carPagosDetalleCompras.setCarPagos(new CarPagos(
                new CarPagosPK(carPagosDetalleComprasTO.getPagEmpresa(), carPagosDetalleComprasTO.getPagPeriodo(),
                        carPagosDetalleComprasTO.getPagTipo(), carPagosDetalleComprasTO.getPagNumero())));
        carPagosDetalleCompras.setCompEmpresa(carPagosDetalleComprasTO.getCompEmpresa());
        carPagosDetalleCompras.setCompPeriodo(carPagosDetalleComprasTO.getCompPeriodo());
        carPagosDetalleCompras.setCompMotivo(carPagosDetalleComprasTO.getCompMotivo());
        carPagosDetalleCompras.setCompNumero(carPagosDetalleComprasTO.getCompNumero());
        carPagosDetalleCompras.setSecEmpresa(carPagosDetalleComprasTO.getCompEmpresa());
        carPagosDetalleCompras.setSecCodigo(carPagosDetalleComprasTO.getCompSecCodigo());
        return carPagosDetalleCompras;
    }

    public static CarPagosDetalleForma convertirCarPagosDetalleFormaTO_CarPagosDetalleForma(
            CarPagosDetalleFormaTO carPagosDetalleFormaTO) throws Exception {
        CarPagosDetalleForma carPagosDetalleForma = new CarPagosDetalleForma();
        carPagosDetalleForma.setDetSecuencial(0);
        carPagosDetalleForma.setDetValor(carPagosDetalleFormaTO.getDetValor());
        carPagosDetalleForma.setDetReferencia(carPagosDetalleFormaTO.getDetReferencia());
        carPagosDetalleForma.setDetObservaciones(carPagosDetalleFormaTO.getDetObservaciones());
        carPagosDetalleForma.setCarPagos(new CarPagos(
                new CarPagosPK(carPagosDetalleFormaTO.getPagEmpresa(), carPagosDetalleFormaTO.getPagPeriodo(),
                        carPagosDetalleFormaTO.getPagMotivo(), carPagosDetalleFormaTO.getPagNumero())));
        carPagosDetalleForma.setFpSecuencial(new CarPagosForma(carPagosDetalleFormaTO.getFpSecuencial()));
        return carPagosDetalleForma;
    }

    public static CarComboPagosCobrosFormaTO convertirCarPagosForma_CarComboPagosCobrosFormaTO(CarPagosForma carPagosForma) throws Exception {
        CarComboPagosCobrosFormaTO carComboPagosCobrosFormaTO = new CarComboPagosCobrosFormaTO();
        carComboPagosCobrosFormaTO.setCtaCodigo(carPagosForma.getCtaCodigo());
        carComboPagosCobrosFormaTO.setFpDetalle(carPagosForma.getFpDetalle());
        carComboPagosCobrosFormaTO.setFpSecuencial(carPagosForma.getFpSecuencial());
        return carComboPagosCobrosFormaTO;
    }

    public static CarComboPagosCobrosFormaTO convertirCarCobrosForma_CarComboPagosCobrosFormaTO(CarCobrosForma carCobrosForma) throws Exception {
        CarComboPagosCobrosFormaTO carComboPagosCobrosFormaTO = new CarComboPagosCobrosFormaTO();
        carComboPagosCobrosFormaTO.setCtaCodigo(carCobrosForma.getCtaCodigo());
        carComboPagosCobrosFormaTO.setFpDetalle(carCobrosForma.getFpDetalle());
        carComboPagosCobrosFormaTO.setFpSecuencial(carCobrosForma.getFpSecuencial());
        return carComboPagosCobrosFormaTO;
    }

    public static CarPagosDetalleAnticipos convertirCarPagosDetalleAnticiposTO_CarPagosDetalleAnticipos(
            CarPagosDetalleAnticiposTO carPagosDetalleAnticiposTO) throws Exception {
        CarPagosDetalleAnticipos carPagosDetalleAnticipos = new CarPagosDetalleAnticipos();
        carPagosDetalleAnticipos.setDetSecuencial(0);
        carPagosDetalleAnticipos.setDetValor(carPagosDetalleAnticiposTO.getDetValor());
        carPagosDetalleAnticipos.setDetObservaciones(carPagosDetalleAnticiposTO.getDetObservaciones());
        carPagosDetalleAnticipos.setCarPagosAnticipos(new CarPagosAnticipos(new CarPagosAnticiposPK(
                carPagosDetalleAnticiposTO.getAntEmpresa(), carPagosDetalleAnticiposTO.getAntPeriodo(),
                carPagosDetalleAnticiposTO.getAntTipo(), carPagosDetalleAnticiposTO.getAntNumero())));
        return carPagosDetalleAnticipos;
    }

    public static CarPagosForma convertirCarPagosFormaTO_CarPagosForma(CarPagosCobrosFormaTO carPagosFormaTO)
            throws Exception {
        CarPagosForma carPagosForma = new CarPagosForma();
        carPagosForma.setFpSecuencial(carPagosFormaTO.getFpSecuencial());
        carPagosForma.setFpDetalle(carPagosFormaTO.getFpDetalle());
        carPagosForma.setFpInactivo(carPagosFormaTO.getFpInactivo());
        carPagosForma.setSecEmpresa(carPagosFormaTO.getUsrEmpresa());
        carPagosForma.setSecCodigo(carPagosFormaTO.getSecCodigo());
        carPagosForma.setCtaEmpresa(carPagosFormaTO.getUsrEmpresa());
        carPagosForma.setCtaCodigo(carPagosFormaTO.getCtaCodigo());
        carPagosForma.setUsrEmpresa(carPagosFormaTO.getUsrEmpresa());
        carPagosForma.setUsrCodigo(carPagosFormaTO.getUsrCodigo());
        carPagosForma.setUsrFechaInserta(UtilsValidacion.fechaString_Date(carPagosFormaTO.getUsrFechaInserta()));
        return carPagosForma;
    }

    public static CarCobrosForma convertirCarCobrosFormaTO_CarCobrosForma(CarPagosCobrosFormaTO carPagosFormaTO)
            throws Exception {
        CarCobrosForma carCobrosForma = new CarCobrosForma();
        carCobrosForma.setFpSecuencial(carPagosFormaTO.getFpSecuencial());
        carCobrosForma.setFpDetalle(carPagosFormaTO.getFpDetalle());
        carCobrosForma.setFpInactivo(carPagosFormaTO.getFpInactivo());
        carCobrosForma.setSecEmpresa(carPagosFormaTO.getUsrEmpresa());
        carCobrosForma.setSecCodigo(carPagosFormaTO.getSecCodigo());
        carCobrosForma.setCtaEmpresa(carPagosFormaTO.getUsrEmpresa());
        carCobrosForma.setCtaCodigo(carPagosFormaTO.getCtaCodigo());
        carCobrosForma.setUsrEmpresa(carPagosFormaTO.getUsrEmpresa());
        carCobrosForma.setUsrCodigo(carPagosFormaTO.getUsrCodigo());
        carCobrosForma.setUsrFechaInserta(UtilsValidacion.fechaString_Date(carPagosFormaTO.getUsrFechaInserta()));
        return carCobrosForma;
    }

    public static CarPagosAnticipos convertirCarPagosAnticiposTO_CarPagosAnticipos(
            CarPagosAnticipoTO carPagosAnticiposTO) throws Exception {
        CarPagosAnticipos carPagosAnticipos = new CarPagosAnticipos();
        carPagosAnticipos.setCarPagosAnticiposPK(
                new CarPagosAnticiposPK(carPagosAnticiposTO.getAntEmpresa(), carPagosAnticiposTO.getAntPeriodo(),
                        carPagosAnticiposTO.getAntTipo(), carPagosAnticiposTO.getAntNumero()));
        carPagosAnticipos.setAntPagado(carPagosAnticiposTO.getAntPagado());
        carPagosAnticipos.setAntValor(carPagosAnticiposTO.getAntValor());
        carPagosAnticipos.setAntAuxiliar(carPagosAnticiposTO.getAntReversado());
        carPagosAnticipos.setProvEmpresa(carPagosAnticiposTO.getProvEmpresa());
        carPagosAnticipos.setProvCodigo(carPagosAnticiposTO.getProvCodigo());
        carPagosAnticipos.setSecCodigo(carPagosAnticiposTO.getSecCodigo());
        carPagosAnticipos.setSecEmpresa(carPagosAnticiposTO.getSecEmpresa());
        carPagosAnticipos.setUsrCodigo(carPagosAnticiposTO.getUsrCodigo());
        carPagosAnticipos.setUsrEmpresa(carPagosAnticiposTO.getUsrEmpresa());
        carPagosAnticipos.setPrdPiscina(carPagosAnticiposTO.getPrdPiscina());
        carPagosAnticipos
                .setUsrFechaInserta(UtilsValidacion.fecha(carPagosAnticiposTO.getUsrFechaInserta(), "yyyy-MM-dd"));
        return carPagosAnticipos;
    }

    public static CarPagosAnticipoTO convertirCarPagosAnticipos_CarPagosAnticiposTO(CarPagosAnticipos carPagosAnticipos) throws Exception {
        CarPagosAnticipoTO carPagosAnticiposTO = new CarPagosAnticipoTO();
        carPagosAnticiposTO.setAntEmpresa(carPagosAnticipos.getCarPagosAnticiposPK().getAntEmpresa());
        carPagosAnticiposTO.setAntNumero(carPagosAnticipos.getCarPagosAnticiposPK().getAntNumero());
        carPagosAnticiposTO.setAntPagado(carPagosAnticipos.getAntPagado());
        carPagosAnticiposTO.setAntPeriodo(carPagosAnticipos.getCarPagosAnticiposPK().getAntPeriodo());
        carPagosAnticiposTO.setAntReversado(carPagosAnticipos.isAntAuxiliar());
        carPagosAnticiposTO.setAntTipo(carPagosAnticipos.getCarPagosAnticiposPK().getAntTipo());
        carPagosAnticiposTO.setAntValor(carPagosAnticipos.getAntValor());
        carPagosAnticiposTO.setFpSecuencial(carPagosAnticipos.getFpSecuencial() != null ? carPagosAnticipos.getFpSecuencial().getFpSecuencial() : null);
        carPagosAnticiposTO.setProvCodigo(carPagosAnticipos.getProvCodigo());
        carPagosAnticiposTO.setProvEmpresa(carPagosAnticipos.getProvEmpresa());
        carPagosAnticiposTO.setSecCodigo(carPagosAnticipos.getSecCodigo());
        carPagosAnticiposTO.setSecEmpresa(carPagosAnticipos.getSecEmpresa());
        carPagosAnticiposTO.setUsrCodigo(carPagosAnticipos.getUsrCodigo());
        carPagosAnticiposTO.setUsrEmpresa(carPagosAnticipos.getUsrEmpresa());
        carPagosAnticiposTO.setPrdPiscina(carPagosAnticipos.getPrdPiscina());
        carPagosAnticiposTO.setUsrFechaInserta(UtilsDate.fechaFormatoString(carPagosAnticipos.getUsrFechaInserta(), "yyyy-MM-dd"));
        return carPagosAnticiposTO;
    }

    public static CarCobrosAnticipoTO convertirCarCobrosAnticipos_CarCobrosAnticiposTO(CarCobrosAnticipos carCobrosAnticipos) throws Exception {
        CarCobrosAnticipoTO carCobrosAnticiposTO = new CarCobrosAnticipoTO();
        carCobrosAnticiposTO.setAntEmpresa(carCobrosAnticipos.getCarCobrosAnticiposPK().getAntEmpresa());
        carCobrosAnticiposTO.setAntNumero(carCobrosAnticipos.getCarCobrosAnticiposPK().getAntNumero());
        carCobrosAnticiposTO.setAntCobrado(carCobrosAnticipos.getAntCobrado());
        carCobrosAnticiposTO.setAntPeriodo(carCobrosAnticipos.getCarCobrosAnticiposPK().getAntPeriodo());
        carCobrosAnticiposTO.setAntReversado(carCobrosAnticipos.isAntAuxiliar());
        carCobrosAnticiposTO.setAntTipo(carCobrosAnticipos.getCarCobrosAnticiposPK().getAntTipo());
        carCobrosAnticiposTO.setAntValor(carCobrosAnticipos.getAntValor());
        carCobrosAnticiposTO.setFpSecuencial(carCobrosAnticipos.getFpSecuencial() != null ? carCobrosAnticipos.getFpSecuencial().getFpSecuencial() : null);
        carCobrosAnticiposTO.setCliCodigo(carCobrosAnticipos.getCliCodigo());
        carCobrosAnticiposTO.setCliEmpresa(carCobrosAnticipos.getCliEmpresa());
        carCobrosAnticiposTO.setSecCodigo(carCobrosAnticipos.getSecCodigo());
        carCobrosAnticiposTO.setSecEmpresa(carCobrosAnticipos.getSecEmpresa());
        carCobrosAnticiposTO.setUsrCodigo(carCobrosAnticipos.getUsrCodigo());
        carCobrosAnticiposTO.setUsrEmpresa(carCobrosAnticipos.getUsrEmpresa());
        carCobrosAnticiposTO.setUsrFechaInserta(UtilsDate.fechaFormatoString(carCobrosAnticipos.getUsrFechaInserta(), "yyyy-MM-dd"));
        carCobrosAnticiposTO.setPrdPiscina(carCobrosAnticipos.getPrdPiscina());

        carCobrosAnticiposTO.setDetBanco(carCobrosAnticipos.getDetBanco());
        carCobrosAnticiposTO.setDetReferencia(carCobrosAnticipos.getDetReferencia());
        carCobrosAnticiposTO.setDetCuenta(carCobrosAnticipos.getDetCuenta());
        carCobrosAnticiposTO.setDetFechaVencimiento(carCobrosAnticipos.getDetFechaVencimiento());
        carCobrosAnticiposTO.setBanCodigo(carCobrosAnticipos.getBanBanco() != null ? carCobrosAnticipos.getBanBanco().getBanBancoPK().getBanCodigo() : null);
        carCobrosAnticiposTO.setBanEmpresa(carCobrosAnticipos.getBanBanco() != null ? carCobrosAnticipos.getBanBanco().getBanBancoPK().getBanEmpresa() : null);
        carCobrosAnticiposTO.setCtaEmpresa(carCobrosAnticipos.getConCuentas() != null ? carCobrosAnticipos.getConCuentas().getConCuentasPK().getCtaEmpresa() : null);
        carCobrosAnticiposTO.setCtaCodigo(carCobrosAnticipos.getConCuentas() != null ? carCobrosAnticipos.getConCuentas().getConCuentasPK().getCtaCodigo() : null);
        carCobrosAnticiposTO.setDepEmpresa(carCobrosAnticipos.getConContableDeposito() != null ? carCobrosAnticipos.getConContableDeposito().getConContablePK().getConEmpresa() : null);
        carCobrosAnticiposTO.setDepPeriodo(carCobrosAnticipos.getConContableDeposito() != null ? carCobrosAnticipos.getConContableDeposito().getConContablePK().getConPeriodo() : null);
        carCobrosAnticiposTO.setDepTipo(carCobrosAnticipos.getConContableDeposito() != null ? carCobrosAnticipos.getConContableDeposito().getConContablePK().getConTipo() : null);
        carCobrosAnticiposTO.setDepNumero(carCobrosAnticipos.getConContableDeposito() != null ? carCobrosAnticipos.getConContableDeposito().getConContablePK().getConNumero() : null);
        return carCobrosAnticiposTO;
    }

    public static CarCobrosAnticipos convertirCarCobrosAnticiposTO_CarCobrosAnticipos(CarCobrosAnticipoTO carCobrosAnticiposTO) throws Exception {
        CarCobrosAnticipos carCobrosAnticipos = new CarCobrosAnticipos();
        carCobrosAnticipos.setCarCobrosAnticiposPK(new CarCobrosAnticiposPK(carCobrosAnticiposTO.getAntEmpresa(), carCobrosAnticiposTO.getAntPeriodo(), carCobrosAnticiposTO.getAntTipo(), carCobrosAnticiposTO.getAntNumero()));
        carCobrosAnticipos.setAntCobrado(carCobrosAnticiposTO.getAntCobrado());
        carCobrosAnticipos.setAntValor(carCobrosAnticiposTO.getAntValor());
        carCobrosAnticipos.setCliEmpresa(carCobrosAnticiposTO.getCliEmpresa());
        carCobrosAnticipos.setCliCodigo(carCobrosAnticiposTO.getCliCodigo());
        carCobrosAnticipos.setSecCodigo(carCobrosAnticiposTO.getSecCodigo());
        carCobrosAnticipos.setSecEmpresa(carCobrosAnticiposTO.getSecEmpresa());
        carCobrosAnticipos.setUsrCodigo(carCobrosAnticiposTO.getUsrCodigo());
        carCobrosAnticipos.setUsrEmpresa(carCobrosAnticiposTO.getUsrEmpresa());
        carCobrosAnticipos.setUsrFechaInserta(UtilsValidacion.fecha(carCobrosAnticiposTO.getUsrFechaInserta(), "yyyy-MM-dd"));

        carCobrosAnticipos.setDetCuenta(carCobrosAnticiposTO.getDetCuenta());
        carCobrosAnticipos.setDetFechaVencimiento(carCobrosAnticiposTO.getDetFechaVencimiento());
        carCobrosAnticipos.setDetBanco(carCobrosAnticiposTO.getDetBanco());
        carCobrosAnticipos.setDetReferencia(carCobrosAnticiposTO.getDetReferencia());
        carCobrosAnticipos.setPrdPiscina(carCobrosAnticiposTO.getPrdPiscina());

        if (carCobrosAnticiposTO.getBanCodigo() != null && carCobrosAnticiposTO.getBanEmpresa() != null) {
            carCobrosAnticipos.setBanBanco(new BanBanco(new BanBancoPK(carCobrosAnticiposTO.getBanEmpresa(), carCobrosAnticiposTO.getBanCodigo())));
        }

        if (carCobrosAnticiposTO.getCtaEmpresa() != null && carCobrosAnticiposTO.getCtaCodigo() != null) {
            carCobrosAnticipos.setConCuentas(new ConCuentas(new ConCuentasPK(carCobrosAnticiposTO.getCtaEmpresa(), carCobrosAnticiposTO.getCtaCodigo())));
        }

        if (carCobrosAnticiposTO.getDepEmpresa() != null && carCobrosAnticiposTO.getDepPeriodo() != null && carCobrosAnticiposTO.getDepTipo() != null) {
            carCobrosAnticipos.setConContableDeposito(new ConContable(new ConContablePK(
                    carCobrosAnticiposTO.getDepEmpresa(),
                    carCobrosAnticiposTO.getDepPeriodo(),
                    carCobrosAnticiposTO.getDepTipo(),
                    carCobrosAnticiposTO.getDepNumero())));
        }
        return carCobrosAnticipos;
    }

    public static CarFunCobrosTO convertirCarCobros_CarFunCobrosTO(CarCobros carCobros) throws Exception {
        CarFunCobrosTO carFunCobrosTO = new CarFunCobrosTO();
        carFunCobrosTO.setId(1);//2019-06 | C-COB | 0000001
        carFunCobrosTO.setCobNumeroSistema(carCobros.getCarCobrosPK().getCobPeriodo() + " | " + carCobros.getCarCobrosPK().getCobTipo() + " | " + carCobros.getCarCobrosPK().getCobNumero());
        return carFunCobrosTO;
    }

    public static CarPagosDetalleAnticipos convertirCarPagosDetalleAnticipos_CarPagosDetalleAnticipos(CarPagosDetalleAnticipos carPagosDetalleAnticiposAux) throws Exception {
        CarPagosDetalleAnticipos carPagosDetalleAnticipos = new CarPagosDetalleAnticipos();
        carPagosDetalleAnticipos.setDetSecuencial(carPagosDetalleAnticiposAux.getDetSecuencial());
        carPagosDetalleAnticipos.setDetValor(carPagosDetalleAnticiposAux.getDetValor());
        carPagosDetalleAnticipos.setDetObservaciones(carPagosDetalleAnticiposAux.getDetObservaciones());
        carPagosDetalleAnticipos.setCarPagosAnticipos(carPagosDetalleAnticiposAux.getCarPagosAnticipos());
        carPagosDetalleAnticipos.setCarPagos(carPagosDetalleAnticiposAux.getCarPagos());

        return carPagosDetalleAnticipos;
    }

    public static CarPagosDetalleForma convertirCarPagosDetalleForma_CarPagosDetalleForma(CarPagosDetalleForma carPagosDetalleFormaAux) throws Exception {
        CarPagosDetalleForma carPagosDetalleForma = new CarPagosDetalleForma();
        carPagosDetalleForma.setDetSecuencial(carPagosDetalleFormaAux.getDetSecuencial());
        carPagosDetalleForma.setDetValor(carPagosDetalleFormaAux.getDetValor());
        carPagosDetalleForma.setDetObservaciones(carPagosDetalleFormaAux.getDetObservaciones());
        carPagosDetalleForma.setDetReferencia(carPagosDetalleFormaAux.getDetReferencia());
        carPagosDetalleForma.setCarPagos(carPagosDetalleFormaAux.getCarPagos());
        carPagosDetalleForma.setFpSecuencial(carPagosDetalleFormaAux.getFpSecuencial());
        return carPagosDetalleForma;
    }

    public static CarPagosDetalleCompras convertirCarPagosDetalleCompras_CarPagosDetalleCompras(CarPagosDetalleCompras carPagosDetalleComprasAux) throws Exception {
        CarPagosDetalleCompras carPagosDetalleCompras = new CarPagosDetalleCompras();
        carPagosDetalleCompras.setDetSecuencial(carPagosDetalleComprasAux.getDetSecuencial());
        carPagosDetalleCompras.setDetValor(carPagosDetalleComprasAux.getDetValor());
        carPagosDetalleCompras.setCompEmpresa(carPagosDetalleComprasAux.getCompEmpresa());
        carPagosDetalleCompras.setCompMotivo(carPagosDetalleComprasAux.getCompMotivo());
        carPagosDetalleCompras.setCompPeriodo(carPagosDetalleComprasAux.getCompPeriodo());
        carPagosDetalleCompras.setCompNumero(carPagosDetalleComprasAux.getCompNumero());
        carPagosDetalleCompras.setSecCodigo(carPagosDetalleComprasAux.getSecCodigo());
        carPagosDetalleCompras.setSecEmpresa(carPagosDetalleComprasAux.getSecEmpresa());
        carPagosDetalleCompras.setCarPagos(carPagosDetalleComprasAux.getCarPagos());
        return carPagosDetalleCompras;
    }

    public static CarCobros convertirCarCobros_CarCobros(CarCobros carCobrosAux) throws Exception {
        CarCobros carCobros = new CarCobros();
        carCobros.setCarCobrosPK(carCobrosAux.getCarCobrosPK());
        carCobros.setCobSaldoActual(carCobrosAux.getCobSaldoActual());
        carCobros.setCobSaldoAnterior(carCobrosAux.getCobSaldoAnterior());
        carCobros.setCobValor(carCobrosAux.getCobValor());
        carCobros.setCobCodigoTransaccional(carCobrosAux.getCobCodigoTransaccional());
        carCobros.setCliEmpresa(carCobrosAux.getUsrEmpresa());
        carCobros.setCliCodigo(carCobrosAux.getCliCodigo());
        carCobros.setUsrEmpresa(carCobrosAux.getUsrEmpresa());
        carCobros.setUsrCodigo(carCobrosAux.getUsrCodigo());
        carCobros.setUsrFechaInserta(carCobrosAux.getUsrFechaInserta());

        return carCobros;
    }

    public static CarCobrosDetalleVentas convertirCarCobrosDetalleVentas_CarCobrosDetalleVentas(CarCobrosDetalleVentas carCobrosDetalleVentasAux) throws Exception {
        CarCobrosDetalleVentas carCobrosDetalleVentas = new CarCobrosDetalleVentas();
        carCobrosDetalleVentas.setDetSecuencial(carCobrosDetalleVentasAux.getDetSecuencial());
        carCobrosDetalleVentas.setDetValor(carCobrosDetalleVentasAux.getDetValor());
        carCobrosDetalleVentas.setVtaEmpresa(carCobrosDetalleVentasAux.getVtaEmpresa());
        carCobrosDetalleVentas.setVtaMotivo(carCobrosDetalleVentasAux.getVtaMotivo());
        carCobrosDetalleVentas.setVtaPeriodo(carCobrosDetalleVentasAux.getVtaPeriodo());
        carCobrosDetalleVentas.setVtaNumero(carCobrosDetalleVentasAux.getVtaNumero());
        carCobrosDetalleVentas.setSecCodigo(carCobrosDetalleVentasAux.getSecCodigo());
        carCobrosDetalleVentas.setSecEmpresa(carCobrosDetalleVentasAux.getSecEmpresa());
        carCobrosDetalleVentas.setCarCobros(carCobrosDetalleVentasAux.getCarCobros());
        return carCobrosDetalleVentas;
    }

    public static CarCobrosDetalleForma convertirCarCobrosDetalleForma_CarCobrosDetalleForma(CarCobrosDetalleForma carCobrosDetalleFormaAux) throws Exception {
        CarCobrosDetalleForma carCobrosDetalleForma = new CarCobrosDetalleForma();

        carCobrosDetalleForma.setDetSecuencial(carCobrosDetalleFormaAux.getDetSecuencial());
        carCobrosDetalleForma.setDetValor(carCobrosDetalleFormaAux.getDetValor());
        carCobrosDetalleForma.setBanCodigo(carCobrosDetalleFormaAux.getBanCodigo());
        carCobrosDetalleForma.setBanEmpresa(carCobrosDetalleFormaAux.getBanEmpresa());
        carCobrosDetalleForma.setCarCobros(carCobrosDetalleFormaAux.getCarCobros());
        carCobrosDetalleForma.setCtaCodigo(carCobrosDetalleFormaAux.getCtaCodigo());
        carCobrosDetalleForma.setCtaEmpresa(carCobrosDetalleFormaAux.getCtaEmpresa());
        carCobrosDetalleForma.setDepEmpresa(carCobrosDetalleFormaAux.getDepEmpresa());
        carCobrosDetalleForma.setDepNumero(carCobrosDetalleFormaAux.getDepNumero());
        carCobrosDetalleForma.setDepPeriodo(carCobrosDetalleFormaAux.getDepPeriodo());
        carCobrosDetalleForma.setDepTipo(carCobrosDetalleFormaAux.getDepTipo());
        carCobrosDetalleForma.setDetBanco(carCobrosDetalleFormaAux.getDepTipo());
        carCobrosDetalleForma.setDetCuenta(carCobrosDetalleFormaAux.getDetCuenta());
        carCobrosDetalleForma.setDetFechaVencimiento(carCobrosDetalleFormaAux.getDetFechaVencimiento());
        carCobrosDetalleForma.setDetObservaciones(carCobrosDetalleFormaAux.getDetObservaciones());
        carCobrosDetalleForma.setDetReferencia(carCobrosDetalleFormaAux.getDetReferencia());
        carCobrosDetalleForma.setFpSecuencial(carCobrosDetalleFormaAux.getFpSecuencial());
        return carCobrosDetalleForma;
    }

    public static CarCobrosDetalleAnticipos convertirCarCobrosDetalleAnticipos_CarCobrosDetalleAnticipos(CarCobrosDetalleAnticipos carCobrosDetalleAnticiposAux) throws Exception {
        CarCobrosDetalleAnticipos carCobrosDetalleAnticipos = new CarCobrosDetalleAnticipos();
        carCobrosDetalleAnticipos.setDetSecuencial(carCobrosDetalleAnticiposAux.getDetSecuencial());
        carCobrosDetalleAnticipos.setDetValor(carCobrosDetalleAnticiposAux.getDetValor());
        carCobrosDetalleAnticipos.setDetObservaciones(carCobrosDetalleAnticiposAux.getDetObservaciones());
        carCobrosDetalleAnticipos.setCarCobros(carCobrosDetalleAnticiposAux.getCarCobros());
        carCobrosDetalleAnticipos.setCarCobrosAnticipos(carCobrosDetalleAnticiposAux.getCarCobrosAnticipos());

        return carCobrosDetalleAnticipos;
    }
}
