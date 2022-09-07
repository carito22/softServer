package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.CuentasDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasFormaPagoDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.SectorDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaVentasFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasFormaCobro;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSectorPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class VentasFormaPagoServiceImpl implements VentasFormaPagoService {

    @Autowired
    private VentasFormaPagoDao ventasFormaPagoDao;
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
    public List<InvComboFormaPagoTO> getComboFormaPagoVenta(String empresa) throws Exception {
        return ventasFormaPagoDao.getComboFormaPagoVenta(empresa);
    }

    @Override
    public List<InvVentasFormaPagoTO> getListaInvVentasFormaPagoTO(String empresa, boolean inactivos) throws Exception {
        return ventasFormaPagoDao.getListaInvVentasFormaPagoTO(empresa, inactivos);
    }

    @Override
    public List<InvListaVentasFormaPagoTO> getInvListaVentasFormaPagoTO(String empresa) throws Exception {
        return ventasFormaPagoDao.getInvListaVentasFormaPagoTO(empresa);
    }

    @Override
    public InvVentasFormaPagoTO getInvVentasFormaPagoTO(String empresa, String ctaCodigo) throws Exception {
        return ventasFormaPagoDao.getInvVentasFormaCobro(empresa, ctaCodigo);
    }

    @Override
    public String modificarEstadoInvVentasPagosForma(InvVentasFormaPagoTO invVentasFormaPagoTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        Boolean estadoCtaVentasForma = false;
        InvVentasFormaCobro invVentasFormaPagoAux = null;
        ///// BUSCANDO existencia cuentas
        estadoCtaVentasForma = cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(invVentasFormaPagoTO.getUsrEmpresa(), invVentasFormaPagoTO.getCtaCodigo())) != null ? true : false;
     
        if (estadoCtaVentasForma) {// revisar si estan
            ///// CREANDO Suceso
            susClave = invVentasFormaPagoTO.getFpDetalle();
            susDetalle = "Se modificó la Venta FormaPago con el detalle " + invVentasFormaPagoTO.getFpDetalle();
            susSuceso = "UPDATE";
            susTabla = "inventario.invVentasFormaPago";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            invVentasFormaPagoTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            InvVentasFormaCobro invVentasFormaPago = ConversionesInventario.convertirInvVentasFormaCobroTO_InvVentasFormaCobro(invVentasFormaPagoTO);
            invVentasFormaPagoAux = ventasFormaPagoDao.buscarVentasFormaPago(invVentasFormaPagoTO.getFpSecuencial());
            if (invVentasFormaPagoAux != null) {
                invVentasFormaPago.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                comprobar = ventasFormaPagoDao.accionInvVentasFormaCobro(invVentasFormaPago, sisSuceso, 'M');
            } else {
                mensaje = "FNo se encuentra la Venta forma de pago...";
            }
        } else {
            if (!estadoCtaVentasForma) {
                mensaje = "FNo se encuentra la cuenta contable forma de pago...";
            }
        }
        if (comprobar) {
            mensaje = "TSe modificó correctamente la Venta Pagos Forma";
        }
        return mensaje;
    }

    @Override
    public String accionInvVentasPagosForma(InvVentasFormaPagoTO invVentasFormaPagoTO, char accion, SisInfoTO sisInfoTO)
            throws Exception {
        comprobar = false;
        Boolean estadoCtaVentasForma = false;
        ///// BUSCANDO existencia cuentas
        estadoCtaVentasForma = cuentasDao.obtenerPorId(ConCuentas.class,
                new ConCuentasPK(invVentasFormaPagoTO.getUsrEmpresa(), invVentasFormaPagoTO.getCtaCodigo())) != null
                        ? true : false;
    
        if (estadoCtaVentasForma) {// revisar si estan
            // llenos
            ///// CREANDO Suceso
            susClave = invVentasFormaPagoTO.getFpDetalle();
            if (accion == 'I') {
                susDetalle = "Se insertó la Venta FormaPago con el detalle " + invVentasFormaPagoTO.getFpDetalle();
                susSuceso = "INSERT";
            }
            if (accion == 'M') {
                susDetalle = "Se modificó la Venta FormaPago con el detalle " + invVentasFormaPagoTO.getFpDetalle();
                susSuceso = "UPDATE";
            }
            if (accion == 'E') {
                susDetalle = "Se eliminó la Venta FormaPago con el detalle " + invVentasFormaPagoTO.getFpDetalle();
                susSuceso = "DELETE";
            }
            susTabla = "inventario.invVentasFormaPago";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            ///// CREANDO CarPagosForma
            InvVentasFormaCobro invVentasFormaCobro = ConversionesInventario
                    .convertirInvVentasFormaCobroTO_InvVentasFormaCobro(invVentasFormaPagoTO);

            InvVentasFormaCobro invVentasFormaPagoAux = null;

            if (accion == 'E') {
                ////// BUSCANDO existencia PagosForma
                invVentasFormaPagoAux = ventasFormaPagoDao
                        .buscarVentasFormaPago(invVentasFormaPagoTO.getFpSecuencial());
                if (invVentasFormaPagoAux != null) {
                    invVentasFormaCobro.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                    comprobar = ventasFormaPagoDao.accionInvVentasFormaCobro(invVentasFormaCobro, sisSuceso, accion);
                } else {
                    mensaje = "FNo se encuentra la Venta forma de pago...";
                }
            }
            if (accion == 'M') {
                ////// BUSCANDO existencia PagosForma
                invVentasFormaPagoAux = ventasFormaPagoDao
                        .buscarVentasFormaPago(invVentasFormaPagoTO.getFpSecuencial());
                if (invVentasFormaPagoAux != null) {
                    invVentasFormaCobro.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                    comprobar = ventasFormaPagoDao.accionInvVentasFormaCobro(invVentasFormaCobro, sisSuceso, accion);
                } else {
                    mensaje = "FNo se encuentra la Venta forma de pago...";
                }
            }
            if (accion == 'I') {
                ////// BUSCANDO existencia PagosForma
                if (!ventasFormaPagoDao.buscarInvVentasFormaCobro(invVentasFormaPagoTO.getCtaCodigo(),
                        invVentasFormaPagoTO.getUsrEmpresa())) {
                    comprobar = ventasFormaPagoDao.accionInvVentasFormaCobro(invVentasFormaCobro, sisSuceso, accion);
                } else {
                    mensaje = "FYa existe la Venta forma de cobro...";
                }
            }
        } else {
            if (!estadoCtaVentasForma) {
                mensaje = "FNo se encuentra la cuenta contable forma de cobro...";
            }
        }
        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TSe eliminó correctamente la Forma de Cobro";
            }
            if (accion == 'M') {
                mensaje = "TSe modificó correctamente la Forma de Cobro";
            }
            if (accion == 'I') {
                mensaje = "TSe ingresó correctamente la Forma de Cobro";
            }
        }
        return mensaje;
    }

}
