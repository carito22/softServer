package ec.com.todocompu.ShrimpSoftServer.cartera.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.cartera.dao.PagosFormaDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.CuentasDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.SectorDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesCar;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarComboPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarPagosForma;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSectorPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class PagosFormaServiceImpl implements PagosFormaService {

    @Autowired
    private SectorDao sectorDao;
    @Autowired
    private PagosFormaDao pagosFormaDao;
    @Autowired
    private CuentasDao cuentasDao;
    private Boolean comprobar = false;
    private String mensaje = "";
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public String accionCarPagosForma(CarPagosCobrosFormaTO carPagosCobrosFormaTO, char accion, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        Boolean estadoCtaComprasForma = false;
        Boolean estadoSector = false;
        ///// BUSCANDO existencia cuentas
        estadoCtaComprasForma = cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(
                carPagosCobrosFormaTO.getUsrEmpresa(), carPagosCobrosFormaTO.getCtaCodigo())) != null ? true
                : false;
        ///// BUSCANDO existencia sector
        estadoSector = sectorDao.obtenerPorId(PrdSector.class, new PrdSectorPK(
                carPagosCobrosFormaTO.getUsrEmpresa(), carPagosCobrosFormaTO.getSecCodigo())) != null ? true
                : false;
        if (estadoCtaComprasForma && estadoSector) {// revisar si estan
            // llenos
            ///// CREANDO Suceso
            susClave = carPagosCobrosFormaTO.getFpDetalle();
            if (accion == 'I') {
                susDetalle = "La forma de pago: Detalle: " + carPagosCobrosFormaTO.getFpDetalle() + ", se ha guardado correctamente.";
                susSuceso = "INSERT";
            }
            if (accion == 'M') {
                susDetalle = "La forma de pago: Detalle " + carPagosCobrosFormaTO.getFpDetalle() + ", se ha modificado correctamente.";
                susSuceso = "UPDATE";
            }
            if (accion == 'E') {
                susDetalle = "La forma de pago: Detalle " + carPagosCobrosFormaTO.getFpDetalle() + ", se ha eliminado correctamente.";
                susSuceso = "DELETE";
            }
            susTabla = "cartera.car_pagos_forma";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            ///// CREANDO CarPagosForma
            carPagosCobrosFormaTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            CarPagosForma carPagosForma = ConversionesCar.convertirCarPagosFormaTO_CarPagosForma(carPagosCobrosFormaTO);
            if (accion == 'E') {
                ////// BUSCANDO existencia PagosForma
                if (pagosFormaDao.obtenerPorId(CarPagosForma.class, carPagosCobrosFormaTO.getFpSecuencial()) != null) {
                    carPagosForma.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                    comprobar = pagosFormaDao.accionCarFormaPago(carPagosForma, sisSuceso, accion);
                } else {
                    mensaje = "FNo se encuentra la compra forma de pago...";
                }
            }
            if (accion == 'M') {
                ////// BUSCANDO existencia PagosForma
                if (pagosFormaDao.obtenerPorId(CarPagosForma.class, carPagosCobrosFormaTO.getFpSecuencial()) != null) {
                    carPagosForma.setUsrFechaInserta(pagosFormaDao.obtenerPorId(CarPagosForma.class, carPagosCobrosFormaTO.getFpSecuencial()).getUsrFechaInserta());
                    comprobar = pagosFormaDao.accionCarFormaPago(carPagosForma, sisSuceso, accion);
                } else {
                    mensaje = "FNo se encuentra la compra forma de pago...";
                }
            }
            if (accion == 'I') {
                ////// BUSCANDO existencia PagosForma
                if (!pagosFormaDao.buscarCarPagosForma(carPagosCobrosFormaTO.getCtaCodigo(), carPagosCobrosFormaTO.getUsrEmpresa(), carPagosCobrosFormaTO.getSecCodigo())) {
                    carPagosForma.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                    comprobar = pagosFormaDao.accionCarFormaPago(carPagosForma, sisSuceso, accion);
                } else {
                    mensaje = "FYa existe la forma de pago...";
                }
            }
        } else {
            if (!estadoCtaComprasForma) {
                mensaje = "FNo se encuentra la cuenta contable forma de pago...";
            }
            if (!estadoSector) {
                mensaje = "FNo se encuentra el sector...";
            }
        }
        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TLa forma de pago: Detalle " + carPagosCobrosFormaTO.getFpDetalle() + ", se ha eliminado correctamente.";
            }
            if (accion == 'M') {
                mensaje = "TLa forma de pago: Detalle " + carPagosCobrosFormaTO.getFpDetalle() + ", se ha modificado correctamente.";
            }
            if (accion == 'I') {
                mensaje = "TLa forma de pago: Detalle " + carPagosCobrosFormaTO.getFpDetalle() + ", se ha guardado correctamente.";
            }
        }
        return mensaje;
    }

    @Override
    public List<CarComboPagosCobrosFormaTO> getCarComboPagosCobrosFormaTO(String empresa, char accion) throws Exception {
        return pagosFormaDao.getCarComboPagosCobrosForma(empresa, accion);
    }

    @Override
    public List<CarListaPagosCobrosFormaTO> getCarListaPagosCobrosFormaTO(String empresa, char accion) throws Exception {
        return pagosFormaDao.getCarListaPagosCobrosForma(empresa, accion);
    }

    @Override
    public List<CarPagosCobrosFormaTO> getListaPagosCobrosFormaTO(String empresa, char accion, boolean inactivos) throws Exception {
        return pagosFormaDao.getListaPagosCobrosFormaTO(empresa, accion, inactivos);
    }

    @Override
    public String modificarEstadoCarPagosForma(CarPagosCobrosFormaTO carPagosCobrosFormaTO, boolean inactivar, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        Boolean estadoCtaComprasForma = false;
        Boolean estadoSector = false;
        ///// BUSCANDO existencia cuentas
        estadoCtaComprasForma = cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(
                carPagosCobrosFormaTO.getUsrEmpresa(), carPagosCobrosFormaTO.getCtaCodigo())) != null ? true
                : false;
        ///// BUSCANDO existencia sector
        estadoSector = sectorDao.obtenerPorId(PrdSector.class, new PrdSectorPK(
                carPagosCobrosFormaTO.getUsrEmpresa(), carPagosCobrosFormaTO.getSecCodigo())) != null ? true
                : false;
        if (estadoCtaComprasForma && estadoSector) {// revisar si estan
            ///// CREANDO Suceso
            susClave = carPagosCobrosFormaTO.getFpDetalle();
            susDetalle = "La forma de pago: Detalle " + carPagosCobrosFormaTO.getFpDetalle() + (inactivar ? ", se ha inactivado correctamente." : ", se ha activado correctamente.");
            susSuceso = "UPDATE";
            susTabla = "cartera.car_pagos_forma";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            ///// CREANDO CarPagosForma
            carPagosCobrosFormaTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            CarPagosForma carPagosForma = ConversionesCar.convertirCarPagosFormaTO_CarPagosForma(carPagosCobrosFormaTO);
            ////// BUSCANDO existencia PagosForma
            if (pagosFormaDao.obtenerPorId(CarPagosForma.class, carPagosCobrosFormaTO.getFpSecuencial()) != null) {
                carPagosForma.setUsrFechaInserta(pagosFormaDao.obtenerPorId(CarPagosForma.class, carPagosCobrosFormaTO.getFpSecuencial()).getUsrFechaInserta());
                comprobar = pagosFormaDao.accionCarFormaPago(carPagosForma, sisSuceso, 'M');
            } else {
                mensaje = "FNo se encuentra la compra forma de pago...";
            }
        } else {
            if (!estadoCtaComprasForma) {
                mensaje = "FNo se encuentra la cuenta contable forma de pago...";
            }
            if (!estadoSector) {
                mensaje = "FNo se encuentra el sector...";
            }
        }

        if (comprobar) {
            mensaje = "TLa forma de pago: Detalle " + carPagosCobrosFormaTO.getFpDetalle() + (inactivar ? ", se ha inactivado correctamente." : ", se ha activado correctamente.");
        }
        return mensaje;

    }

    @Override
    public CarPagosCobrosFormaTO getCarPagosCobrosFormaTO(String empresa, String ctaCodigo, String sector) throws Exception {
        return pagosFormaDao.getCarPagosCobrosFormaTO(empresa, ctaCodigo, sector);
    }

}
