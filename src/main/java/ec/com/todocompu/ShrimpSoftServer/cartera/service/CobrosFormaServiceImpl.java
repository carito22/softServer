package ec.com.todocompu.ShrimpSoftServer.cartera.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.cartera.dao.CobrosDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.CobrosDetalleAnticiposDao;
import ec.com.todocompu.ShrimpSoftServer.cartera.dao.CobrosFormaDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.CuentasDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.SectorDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesCar;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarListaCobrosClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarPagosCobrosFormaTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.entity.CarCobrosForma;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSectorPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class CobrosFormaServiceImpl implements CobrosFormaService {

    @Autowired
    private SectorDao sectorDao;
    @Autowired
    private CobrosFormaDao cobrosFormaDao;
    @Autowired
    private CobrosDao cobrosDao;
    @Autowired
    private CobrosDetalleAnticiposDao cobrosDetalleAnticiposDao;
    @Autowired
    private CuentasDao cuentasDao;

    private Boolean comprobar = false;
    private String mensaje = "";
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public CarListaCobrosClienteTO getCobrosConsultaClienteAnticipoTO(String empresa, String periodo, String tipo,
            String numero) throws Exception {
        return cobrosDetalleAnticiposDao.getCobrosConsultaClienteAnticipo(empresa, periodo, tipo, numero);
    }

    @Override
    public String accionCarCobrosForma(CarPagosCobrosFormaTO carPagosCobrosFormaTO, char accion, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        Boolean estadoCtaComprasForma = false;
        Boolean estadoSector = false;
        ///// BUSCANDO existencia cuentas
        estadoCtaComprasForma = cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(carPagosCobrosFormaTO.getUsrEmpresa(), carPagosCobrosFormaTO.getCtaCodigo())) != null;
        ///// BUSCANDO existencia sector
        estadoSector = sectorDao.obtenerPorId(PrdSector.class, new PrdSectorPK(carPagosCobrosFormaTO.getUsrEmpresa(), carPagosCobrosFormaTO.getSecCodigo())) != null ? true : false;
        if (estadoCtaComprasForma && estadoSector) {// revisar si estan
            // llenos
            ///// CREANDO Suceso
            susClave = carPagosCobrosFormaTO.getFpDetalle();
            if (accion == 'I') {
                susDetalle = "La forma de cobro: Detalle: " + carPagosCobrosFormaTO.getFpDetalle() + ", se ha guardado correctamente.";
                susSuceso = "INSERT";
            }
            if (accion == 'M') {
                susDetalle = "La forma de cobro: Detalle: " + carPagosCobrosFormaTO.getFpDetalle() + ", se ha modificado correctamente.";
                susSuceso = "UPDATE";
            }
            if (accion == 'E') {
                susDetalle = "La forma de cobro: Detalle: " + carPagosCobrosFormaTO.getFpDetalle() + ", se ha eliminado correctamente.";
                susSuceso = "DELETE";
            }
            susTabla = "cartera.car_cobros_forma";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            ///// CREANDO CarPagosForma
            carPagosCobrosFormaTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            CarCobrosForma carCobrosForma = ConversionesCar.convertirCarCobrosFormaTO_CarCobrosForma(carPagosCobrosFormaTO);
            CarCobrosForma carCobrosFormaAux = cobrosFormaDao.obtenerPorId(CarCobrosForma.class, carPagosCobrosFormaTO.getFpSecuencial());
            if (accion == 'E') {
                ////// BUSCANDO existencia PagosForma
                if (cobrosFormaDao.obtenerPorId(CarCobrosForma.class, carPagosCobrosFormaTO.getFpSecuencial()) != null) {
                    carCobrosForma.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                    comprobar = cobrosFormaDao.accionCarFormaCobro(carCobrosFormaAux, carCobrosForma, sisSuceso, accion);
                } else {
                    mensaje = "FNo se encuentra la forma de cobro...";
                }
            }
            if (accion == 'M') {
                ////// BUSCANDO existencia PagosForma
                if (cobrosFormaDao.obtenerPorId(CarCobrosForma.class, carPagosCobrosFormaTO.getFpSecuencial()) != null) {
                    carCobrosForma.setUsrFechaInserta(cobrosFormaDao.obtenerPorId(CarCobrosForma.class, carPagosCobrosFormaTO.getFpSecuencial()).getUsrFechaInserta());
                    comprobar = cobrosFormaDao.accionCarFormaCobro(carCobrosFormaAux, carCobrosForma, sisSuceso, accion);
                } else {
                    mensaje = "FNo se encuentra la forma de cobro...";
                }
            }
            if (accion == 'I') {
                ////// BUSCANDO existencia PagosForma
                if (!cobrosDao.buscarCarCobrosForma(carPagosCobrosFormaTO.getCtaCodigo(), carPagosCobrosFormaTO.getUsrEmpresa(), carPagosCobrosFormaTO.getSecCodigo())) {
                    carCobrosForma.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                    comprobar = cobrosFormaDao.accionCarFormaCobro(carCobrosFormaAux, carCobrosForma, sisSuceso, accion);
                } else {
                    mensaje = "FYa existe la forma de cobro...";
                }
            }
        } else {
            if (!estadoCtaComprasForma) {
                mensaje = "FNo se encuentra la cuenta contable forma de cobro...";
            }
            if (!estadoSector) {
                mensaje = "FNo se encuentra el sector...";
            }
        }
        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TLa forma de cobro: Detalle " + carPagosCobrosFormaTO.getFpDetalle() + ", se ha eliminado correctamente.";
            }
            if (accion == 'M') {
                mensaje = "TLa forma de cobro: Detalle " + carPagosCobrosFormaTO.getFpDetalle() + ", se ha modificado correctamente.";
            }
            if (accion == 'I') {
                mensaje = "TLa forma de cobro: Detalle " + carPagosCobrosFormaTO.getFpDetalle() + ", se ha guardado correctamente.";
            }
        }
        return mensaje;
    }

    @Override
    public String modificarEstadoCarCobroForma(CarPagosCobrosFormaTO carPagosCobrosFormaTO, boolean inactivar, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        Boolean estadoCtaComprasForma = false;
        Boolean estadoSector = false;
        ///// BUSCANDO existencia cuentas
        estadoCtaComprasForma = cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(carPagosCobrosFormaTO.getUsrEmpresa(), carPagosCobrosFormaTO.getCtaCodigo())) != null ? true : false;
        ///// BUSCANDO existencia sector
        estadoSector = sectorDao.obtenerPorId(PrdSector.class, new PrdSectorPK(carPagosCobrosFormaTO.getUsrEmpresa(), carPagosCobrosFormaTO.getSecCodigo())) != null ? true : false;
        if (estadoCtaComprasForma && estadoSector) {// revisar si estan
            ///// CREANDO Suceso
            susClave = carPagosCobrosFormaTO.getFpDetalle();
            susDetalle = "La forma de cobro: Detalle " + carPagosCobrosFormaTO.getFpDetalle() + (inactivar ? ", se ha inactivado correctamente." : ", se ha activado correctamente.");
            susSuceso = "UPDATE";
            susTabla = "cartera.car_cobros_forma";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            ///// CREANDO CarPagosForma
            carPagosCobrosFormaTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            CarCobrosForma carCobrosForma = ConversionesCar.convertirCarCobrosFormaTO_CarCobrosForma(carPagosCobrosFormaTO);
            CarCobrosForma carCobrosFormaAux = cobrosFormaDao.obtenerPorId(CarCobrosForma.class, carPagosCobrosFormaTO.getFpSecuencial());
            ////// BUSCANDO existencia PagosForma
            if (cobrosFormaDao.obtenerPorId(CarCobrosForma.class, carPagosCobrosFormaTO.getFpSecuencial()) != null) {
                carCobrosForma.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                comprobar = cobrosFormaDao.accionCarFormaCobro(carCobrosFormaAux, carCobrosForma, sisSuceso, 'M');
            } else {
                mensaje = "FNo se encuentra la forma de cobro...";
            }
        } else {
            if (!estadoCtaComprasForma) {
                mensaje = "FNo se encuentra la cuenta contable forma de cobro...";
            }
            if (!estadoSector) {
                mensaje = "FNo se encuentra el sector...";
            }
        }

        if (comprobar) {
            mensaje = "TLa forma de cobro: Detalle " + carPagosCobrosFormaTO.getFpDetalle() + (inactivar ? ", se ha inactivado correctamente." : ", se ha activado correctamente.");
        }
        return mensaje;
    }
    
     @Override
    public CarPagosCobrosFormaTO getCarPagosCobrosFormaTO(String empresa, String ctaCodigo, String sector) throws Exception {
        return cobrosFormaDao.getCarPagosCobrosFormaTO(empresa, ctaCodigo, sector);
    }

}
