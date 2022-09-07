package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.CuentasDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasFormaCobroDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.SectorDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasFormaCobroTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasFormaCobro;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSectorPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentasFormaCobroServiceImpl implements VentasFormaCobroService {

    @Autowired
    private VentasFormaCobroDao ventasFormaCobroDao;
    @Autowired
    private CuentasDao cuentasDao;
    @Autowired
    private SectorDao sectorDao;
    private boolean comprobar = false;
    private String mensaje;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<InvVentasFormaCobroTO> getListaInvVentasFormaCobroTO(String empresa, boolean inactivos) throws Exception {
        return ventasFormaCobroDao.getListaInvVentasFormaCobroTO(empresa, inactivos);
    }

    @Override
    public InvVentasFormaCobro buscarVentasFormaCobro(Integer secuencial) throws Exception {
        return ventasFormaCobroDao.buscarVentasFormaCobro(secuencial);
    }

    @Override
    public String modificarEstadoInvVentasFormaCobroTO(InvVentasFormaCobroTO invVentasFormaCobroTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        Boolean estadoCtaVentasForma = false;
        InvVentasFormaCobro invVentasFormaPagoAux = null;
        ///// BUSCANDO existencia cuentas
        estadoCtaVentasForma = cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(invVentasFormaCobroTO.getUsrEmpresa(), invVentasFormaCobroTO.getCtaCodigo())) != null;
        ///// BUSCANDO existencia sector
        if (estadoCtaVentasForma) {// revisar si estan
            ///// CREANDO Suceso
            susClave = invVentasFormaCobroTO.getFcDetalle();
            susDetalle = "La forma de cobro: Detalle " + invVentasFormaCobroTO.getFcDetalle() + (invVentasFormaCobroTO.getFcInactivo() ? ", se ha inactivado correctamente" : ", se ha activado correctamente");
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_ventas_forma_cobro";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            invVentasFormaCobroTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            InvVentasFormaCobro invVentasFormaPago = ConversionesInventario.convertirInvVentasFormaCobroTO_InvVentasFormaCobro(invVentasFormaCobroTO);
            invVentasFormaPagoAux = ventasFormaCobroDao.buscarVentasFormaCobro(invVentasFormaCobroTO.getFcSecuencial());
            if (invVentasFormaPagoAux != null) {
                invVentasFormaPago.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                comprobar = ventasFormaCobroDao.accionInvVentasFormaCobro(invVentasFormaPago, sisSuceso, 'M');
            } else {
                mensaje = "FNo se encuentra la Venta forma de cobro...";
            }
        } else {
            if (!estadoCtaVentasForma) {
                mensaje = "FNo se encuentra la cuenta contable forma de cobro...";
            }
        }
        if (comprobar) {
            mensaje = "TLa forma de cobro: Código " + invVentasFormaCobroTO.getFcSecuencial() + ", se ha " + (invVentasFormaCobroTO.getFcInactivo() ? "inactivado correctamente." : "activado correctamente.");
        }
        return mensaje;
    }

    @Override
    public InvVentasFormaCobroTO getInvVentasFormaCobroTO(String empresa, String ctaCodigo) throws Exception {
        return ventasFormaCobroDao.buscarInvVentaFormaCobroTO(empresa, ctaCodigo);
    }

    @Override
    public String accionInvVentasFormaCobroTO(InvVentasFormaCobroTO invVentasFormaCobroTO, char accion, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        Boolean estadoCtaVentasForma = false;
        InvVentasFormaCobro invVentasFormaPago = null;
        ///// BUSCANDO existencia cuentas
        estadoCtaVentasForma = cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(invVentasFormaCobroTO.getUsrEmpresa(), invVentasFormaCobroTO.getCtaCodigo())) != null;
        if (estadoCtaVentasForma) {// revisar si estan
            ///// CREANDO Suceso
            susClave = invVentasFormaCobroTO.getFcDetalle();
            if (accion == 'I') {
                susDetalle = "La forma de cobro: Detalle " + invVentasFormaCobroTO.getFcDetalle() + ", se ha guardado correctamente";
                susSuceso = "INSERT";
            }
            if (accion == 'M') {
                susDetalle = "La forma de cobro: Detalle " + invVentasFormaCobroTO.getFcDetalle() + ", se ha modificado correctamente";
                susSuceso = "UPDATE";
            }
            if (accion == 'E') {
                susDetalle = "La forma de cobro: Detalle " + invVentasFormaCobroTO.getFcDetalle() + ", se ha eliminado correctamente";
                susSuceso = "DELETE";
            }
            susTabla = "inventario.inv_ventas_forma_cobro";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            invVentasFormaCobroTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            invVentasFormaPago = ConversionesInventario.convertirInvVentasFormaCobroTO_InvVentasFormaCobro(invVentasFormaCobroTO);
            InvVentasFormaCobro invVentasFormaPagoAux = null;
            if (accion == 'E') {
                invVentasFormaPagoAux = ventasFormaCobroDao.buscarVentasFormaCobro(invVentasFormaCobroTO.getFcSecuencial());
                if (invVentasFormaPagoAux != null) {
                    invVentasFormaPago.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                    comprobar = ventasFormaCobroDao.accionInvVentasFormaCobro(invVentasFormaPago, sisSuceso, accion);
                } else {
                    mensaje = "FNo se encuentra la forma de cobro de venta...";
                }
            }
            if (accion == 'M') {
                ////// BUSCANDO existencia PagosForma
                invVentasFormaPagoAux = ventasFormaCobroDao.buscarVentasFormaCobro(invVentasFormaCobroTO.getFcSecuencial());
                if (invVentasFormaPagoAux != null) {
                    if (!ventasFormaCobroDao.buscarInvVentasFormaCobro(invVentasFormaCobroTO.getCtaCodigo(), invVentasFormaCobroTO.getUsrEmpresa(), invVentasFormaCobroTO.getFcSecuencial())) {
                        invVentasFormaPago.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                        comprobar = ventasFormaCobroDao.accionInvVentasFormaCobro(invVentasFormaPago, sisSuceso, accion);
                    } else {
                        mensaje = "FYa existe la forma de cobro de venta...";
                    }
                } else {
                    mensaje = "FNo se encuentra la forma de cobro de venta...";
                }
            }
            if (accion == 'I') {
                if (!ventasFormaCobroDao.buscarInvVentasFormaCobro(invVentasFormaCobroTO.getCtaCodigo(), invVentasFormaCobroTO.getUsrEmpresa(), null)) {
                    invVentasFormaPago.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                    comprobar = ventasFormaCobroDao.accionInvVentasFormaCobro(invVentasFormaPago, sisSuceso, accion);
                } else {
                    mensaje = "FYa existe la forma de cobro de venta...";
                }
            }
        } else {
            if (!estadoCtaVentasForma) {
                mensaje = "FNo se encuentra la cuenta contable forma de pago...";
            }
        }
        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TLa forma de cobro: Código " + invVentasFormaCobroTO.getFcSecuencial() + ", se ha eliminado correctamente.";
            }
            if (accion == 'M') {
                mensaje = "TLa forma de cobro: Código " + invVentasFormaCobroTO.getFcSecuencial() + ", se ha modificado correctamente.";
            }
            if (accion == 'I') {
                mensaje = "TLa forma de cobro: Código " + invVentasFormaPago.getFcSecuencial() + ", se ha guardado correctamente.";
            }
        }
        return mensaje;
    }
}
