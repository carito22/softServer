package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.CuentasDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasFormaPagoDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.SectorDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaComprasFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasFormaPago;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class ComprasFormaPagoServiceImpl implements ComprasFormaPagoService {

    @Autowired
    private ComprasFormaPagoDao comprasFormaPagoDao;
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
    public List<InvComboFormaPagoTO> getComboFormaPagoCompra(String empresa) throws Exception {
        return comprasFormaPagoDao.getComboFormaPagoCompra(empresa);
    }

    @Override
    public List<InvListaComprasFormaPagoTO> getInvListaComprasFormaPagoTO(String empresa) throws Exception {
        return comprasFormaPagoDao.getInvListaComprasFormaPagoTO(empresa);
    }

    @Override
    public List<InvComprasFormaPagoTO> getInvListaInvComprasFormaPagoTO(String empresa, boolean inactivos) throws Exception {
        return comprasFormaPagoDao.getInvListaInvComprasFormaPagoTO(empresa, inactivos);
    }

    @Override
    public InvComprasFormaPagoTO getInvComprasFormaPagoTO(String empresa, String ctaCodigo) throws Exception {
        return comprasFormaPagoDao.getInvComprasFormaPago(empresa, ctaCodigo);
    }

    @Override
    public String modificarEstadoInvComprasPagosForma(InvComprasFormaPagoTO invComprasFormaPagoTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        Boolean estadoCtaComprasForma = false;
        InvComprasFormaPago invComprasFormaPagoAux = null;
        ///// BUSCANDO existencia cuentas
        estadoCtaComprasForma = cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(
                invComprasFormaPagoTO.getUsrEmpresa(), invComprasFormaPagoTO.getCtaCodigo())) != null ? true
                : false;

        if (estadoCtaComprasForma) {// revisar si estan
            ///// CREANDO Suceso
            susClave = invComprasFormaPagoTO.getFpDetalle();
            susDetalle = "La forma de pago: Detalle " + invComprasFormaPagoTO.getFpDetalle() + (invComprasFormaPagoTO.getFpInactivo() ? " se ha inactivado correctamente" : " se ha activado correctamente");
            susSuceso = "UPDATE";
            susTabla = "inventario.invComprasFormaPag";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            invComprasFormaPagoTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            InvComprasFormaPago invComprasFormaPago = ConversionesInventario.convertirInvComprasFormaPagoTO_InvComprasFormaPago(invComprasFormaPagoTO);
            invComprasFormaPagoAux = comprasFormaPagoDao.buscarComprasFormaPago(invComprasFormaPagoTO.getFpSecuencial());
            if (invComprasFormaPagoAux != null) {
                invComprasFormaPago.setUsrFechaInserta(comprasFormaPagoDao.buscarComprasFormaPago(invComprasFormaPagoTO.getFpSecuencial()).getUsrFechaInserta());
                comprobar = comprasFormaPagoDao.accionInvComprasFormaPago(invComprasFormaPago, sisSuceso, 'M');
            } else {
                mensaje = "FNo se encuentra la compra forma de pago...";
            }
        } else {
            if (!estadoCtaComprasForma) {
                mensaje = "FNo se encuentra la cuenta contable forma de pago...";
            }
        }
        if (comprobar) {
            mensaje = "TLa forma de pago: Código " + invComprasFormaPagoTO.getFpSecuencial() + ", se ha " + (invComprasFormaPagoTO.getFpInactivo() ? "inactivado correctamente." : "activado correctamente.");
        }
        return mensaje;
    }

    @Override
    public String accionInvComprasPagosForma(InvComprasFormaPagoTO invComprasFormaPagoTO, char accion, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        Boolean estadoCtaComprasForma = false;
        InvComprasFormaPago invComprasFormaPago = null;
        ///// BUSCANDO existencia cuentas
        estadoCtaComprasForma = cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(
                invComprasFormaPagoTO.getUsrEmpresa(), invComprasFormaPagoTO.getCtaCodigo())) != null ? true
                : false;
        if (estadoCtaComprasForma) {// revisar si estan
            // llenos
            ///// CREANDO Suceso
            susClave = invComprasFormaPagoTO.getFpDetalle();
            if (accion == 'I') {
                susDetalle = "La forma de pago: Detalle " + invComprasFormaPagoTO.getFpDetalle() + ", se ha guardado correctamente";
                susSuceso = "INSERT";
            }
            if (accion == 'M') {
                susDetalle = "La forma de pago: Detalle " + invComprasFormaPagoTO.getFpDetalle() + ", se ha modificado correctamente";
                susSuceso = "UPDATE";
            }
            if (accion == 'E') {
                susDetalle = "La forma de pago: Detalle " + invComprasFormaPagoTO.getFpDetalle() + ", se ha eliminado correctamente";
                susSuceso = "DELETE";
            }
            susTabla = "inventario.invComprasFormaPag";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            ///// CREANDO CarPagosForma
            invComprasFormaPagoTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            invComprasFormaPago = ConversionesInventario.convertirInvComprasFormaPagoTO_InvComprasFormaPago(invComprasFormaPagoTO);
            InvComprasFormaPago invComprasFormaPagoAux = null;
            if (accion == 'E') {
                ////// BUSCANDO existencia PagosForma
                invComprasFormaPagoAux = comprasFormaPagoDao
                        .buscarComprasFormaPago(invComprasFormaPagoTO.getFpSecuencial());
                if (invComprasFormaPagoAux != null) {
                    invComprasFormaPago.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                    comprobar = comprasFormaPagoDao.accionInvComprasFormaPago(invComprasFormaPago, sisSuceso,
                            accion);
                } else {
                    mensaje = "FNo se encuentra la compra forma de pago...";
                }
            }
            if (accion == 'M') {
                ////// BUSCANDO existencia PagosForma
                invComprasFormaPagoAux = comprasFormaPagoDao.buscarComprasFormaPago(invComprasFormaPagoTO.getFpSecuencial());
                if (invComprasFormaPagoAux != null) {
                    if (!comprasFormaPagoDao.buscarInvComprasFormaPago(invComprasFormaPagoTO.getCtaCodigo(),
                            invComprasFormaPagoTO.getUsrEmpresa(),
                            invComprasFormaPagoTO.getFpSecuencial())) {
                        invComprasFormaPago.setUsrFechaInserta(invComprasFormaPagoAux.getUsrFechaInserta());
                        comprobar = comprasFormaPagoDao.accionInvComprasFormaPago(invComprasFormaPago, sisSuceso, accion);
                    } else {
                        mensaje = "FYa existe la compra forma de pago...";
                    }
                } else {
                    mensaje = "FNo se encuentra la compra forma de pago...";
                }
            }
            if (accion == 'I') {
                ////// BUSCANDO existencia PagosForma
                if (!comprasFormaPagoDao.buscarInvComprasFormaPago(invComprasFormaPagoTO.getCtaCodigo(), invComprasFormaPagoTO.getUsrEmpresa(), null)) {
                    invComprasFormaPago.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                    comprobar = comprasFormaPagoDao.accionInvComprasFormaPago(invComprasFormaPago, sisSuceso, accion);
                } else {
                    mensaje = "FYa existe la compra forma de pago...";
                }
            }
        } else {
            if (!estadoCtaComprasForma) {
                mensaje = "FNo se encuentra la cuenta contable forma de pago...";
            }
        }
        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TLa forma de pago: Código " + invComprasFormaPagoTO.getFpSecuencial() + ", se ha eliminado correctamente.";
            }
            if (accion == 'M') {
                mensaje = "TLa forma de pago: Código " + invComprasFormaPagoTO.getFpSecuencial() + ", se ha modificado correctamente.";
            }
            if (accion == 'I') {
                mensaje = "TLa forma de pago: Código " + invComprasFormaPago.getFpSecuencial() + ", se ha guardado correctamente.";
            }
        }
        return mensaje;
    }

}
