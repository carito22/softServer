package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.CuentasDao;
import ec.com.todocompu.ShrimpSoftServer.criteria.Criterio;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.SectorDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.FormaPagoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesRRHH;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSectorPK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhFormaPago;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import org.hibernate.criterion.Restrictions;

@Service
public class FormaPagoServiceImpl implements FormaPagoService {

    @Autowired
    private FormaPagoDao formaPagoDao;
    @Autowired
    private CuentasDao cuentasDao;
    @Autowired
    private SectorDao sectorDao;
    @Autowired
    private GenericoDao<RhFormaPago, Integer> formaPagoDaoCriteria;
    @Autowired
    private SucesoDao sucesoDao;

    private Boolean comprobar = false;
    private String mensaje = "";
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public String accionRhFormaPago(RhFormaPagoTO rhFormaPagoTO, char accion, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        ///// BUSCANDO existencia cuentas
        Boolean estadoCtaComprasForma = cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(sisInfoTO.getEmpresa(), rhFormaPagoTO.getCtaCodigo())) != null;
        ///// BUSCANDO existencia sector
        Boolean estadoSector = sectorDao.obtenerPorId(PrdSector.class, new PrdSectorPK(sisInfoTO.getEmpresa(), rhFormaPagoTO.getSecCodigo())) != null;
        if (!estadoCtaComprasForma) {
            mensaje = "FNo se encuentra la cuenta contable forma de pago con el detalle " + rhFormaPagoTO.getFpDetalle();
        } else if (!estadoSector) {
            mensaje = "FNo se encuentra el sector.";
        } else {
            ///// CREANDO Suceso
            susClave = rhFormaPagoTO.getFpDetalle();
            rhFormaPagoTO.setFpDetalle(rhFormaPagoTO.getFpDetalle().toUpperCase());
            if (accion == 'I') {
                susDetalle = "Se insertó la Forma de Pago " + rhFormaPagoTO.getCtaCodigo() + " - " + rhFormaPagoTO.getFpDetalle();
                susSuceso = "INSERT";
            } else if (accion == 'M') {
                susDetalle = "Se modificó la Forma de Pago " + rhFormaPagoTO.getCtaCodigo() + " - " + rhFormaPagoTO.getFpDetalle();
                susSuceso = "UPDATE";
            } else if (accion == 'E') {
                susDetalle = "Se eliminó la Forma Pago " + rhFormaPagoTO.getCtaCodigo() + " - " + rhFormaPagoTO.getFpDetalle();
                susSuceso = "DELETE";
            }
            susTabla = "recursoshumanos.rh_forma_pago";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            ///// CREANDO CarPagosForma
            rhFormaPagoTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            rhFormaPagoTO.setUsrEmpresa(sisInfoTO.getEmpresa());
            rhFormaPagoTO.setUsrCodigo(sisInfoTO.getUsuario());
            RhFormaPago rhFormaPago = ConversionesRRHH.convertirRhFormaPagoTO_RhFormaPago(rhFormaPagoTO);
            if (accion == 'E') {
                ////// BUSCANDO existencia PagosForma
                if (formaPagoDao.buscarFormaPago(rhFormaPagoTO.getFpSecuencial()) != null) {
                    rhFormaPago.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                    comprobar = formaPagoDao.accionRhFormaPago(rhFormaPago, sisSuceso, accion);
                } else {
                    mensaje = "FNo se encuentra la forma de pago " + rhFormaPagoTO.getCtaCodigo() + " - " + rhFormaPagoTO.getFpDetalle();
                }
            } else if (accion == 'M') {
                ////// BUSCANDO existencia PagosForma
                if (formaPagoDao.buscarFormaPago(rhFormaPagoTO.getFpSecuencial()) != null) {
                    RhFormaPago fp = obtenerFormaDePagoPorDetalles(rhFormaPago);
                    if (fp != null && fp.getFpSecuencial() != rhFormaPagoTO.getFpSecuencial()) {
                        mensaje = "FYa existe la forma de pago con código de cuenta " + rhFormaPagoTO.getCtaCodigo();
                    } else {
                        rhFormaPago.setUsrFechaInserta(formaPagoDao.buscarFormaPago(rhFormaPagoTO.getFpSecuencial()).getUsrFechaInserta());
                        comprobar = formaPagoDao.accionRhFormaPago(rhFormaPago, sisSuceso, accion);
                    }
                } else {
                    mensaje = "FNo se encuentra la forma de pago " + rhFormaPagoTO.getCtaCodigo() + " - " + rhFormaPagoTO.getFpDetalle();
                }
            } else if (accion == 'I') {
                ////// BUSCANDO existencia PagosForma
                if (obtenerFormaDePagoPorDetalles(rhFormaPago) == null) {
                    rhFormaPago.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                    comprobar = formaPagoDao.accionRhFormaPago(rhFormaPago, sisSuceso, accion);
                } else {
                    mensaje = "FYa existe la forma de pago " + rhFormaPagoTO.getCtaCodigo();
                }
            }
            if (comprobar) {
                if (accion == 'E') {
                    mensaje = "TLa Forma de Pago " + rhFormaPagoTO.getCtaCodigo() + " - "
                            + rhFormaPagoTO.getFpDetalle() + " se ha eliminado correctamente.";
                } else if (accion == 'M') {
                    mensaje = "TLa Forma de Pago " + rhFormaPagoTO.getCtaCodigo() + " - "
                            + rhFormaPagoTO.getFpDetalle() + " se ha modificado correctamente.";
                } else if (accion == 'I') {
                    mensaje = "TLa Forma de Pago " + rhFormaPagoTO.getCtaCodigo() + " - "
                            + rhFormaPagoTO.getFpDetalle() + " se ha guardado correctamente.";
                }
            }
        }
        return mensaje;
    }

    @Override
    public RhFormaPagoTO insertarRhFormaPago(RhFormaPagoTO rhFormaPagoTO, SisInfoTO sisInfoTO) throws Exception, GeneralException {
        Boolean estadoCtaComprasForma = cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(sisInfoTO.getEmpresa(), rhFormaPagoTO.getCtaCodigo())) != null;
        ///// BUSCANDO existencia sector
        Boolean estadoSector = sectorDao.obtenerPorId(PrdSector.class, new PrdSectorPK(sisInfoTO.getEmpresa(), rhFormaPagoTO.getSecCodigo())) != null;
        if (!estadoCtaComprasForma) {
            throw new GeneralException("No se encuentra la cuenta contable forma de pago con el detalle " + rhFormaPagoTO.getFpDetalle());
        } else if (!estadoSector) {
            throw new GeneralException("No se encuentra el sector.");
        } else {
            ///// CREANDO Suceso
            susClave = rhFormaPagoTO.getFpDetalle();
            rhFormaPagoTO.setFpDetalle(rhFormaPagoTO.getFpDetalle().toUpperCase());
            susDetalle = "Se insertó la Forma de Pago " + rhFormaPagoTO.getCtaCodigo() + " - " + rhFormaPagoTO.getFpDetalle();
            susSuceso = "INSERT";
            susTabla = "recursoshumanos.rh_forma_pago";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            ///// CREANDO CarPagosForma
            rhFormaPagoTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            rhFormaPagoTO.setUsrEmpresa(sisInfoTO.getEmpresa());
            rhFormaPagoTO.setUsrCodigo(sisInfoTO.getUsuario());
            RhFormaPago rhFormaPago = ConversionesRRHH.convertirRhFormaPagoTO_RhFormaPago(rhFormaPagoTO);
            if (formaPagoDaoCriteria.obtener(RhFormaPago.class, rhFormaPago.getFpSecuencial()) != null) {
                throw new GeneralException("Ya existe la forma de pago con secuencial: " + rhFormaPago.getFpSecuencial());
            }
            if (obtenerFormaDePagoPorDetalles(rhFormaPago) != null) {
                throw new GeneralException("Ya existe la forma de pago con código de cuenta " + rhFormaPagoTO.getCtaCodigo());
            }
            rhFormaPago.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
            rhFormaPago = formaPagoDaoCriteria.insertar(rhFormaPago);
            rhFormaPagoTO.setFpSecuencial(rhFormaPago.getFpSecuencial());
            sucesoDao.insertar(sisSuceso);
        }
        return rhFormaPagoTO;
    }

    private RhFormaPago obtenerFormaDePagoPorDetalles(RhFormaPago formaPago) {
        Criterio filtro;
        filtro = Criterio.forClass(RhFormaPago.class);
        filtro.add(Restrictions.eq("ctaCodigo", formaPago.getCtaCodigo()));
        filtro.add(Restrictions.eq("secCodigo", formaPago.getSecCodigo()));
        filtro.add(Restrictions.eq("usrEmpresa", formaPago.getUsrEmpresa()));
        return formaPagoDaoCriteria.obtenerPorCriteriaSinProyeccionesDistinct(filtro);
    }

    @Override
    public List<RhListaFormaPagoTO> getListaFormaPagoTO(String empresa) throws Exception {
        return formaPagoDao.getListaFormaPago(empresa);
    }

    @Override
    public List<RhComboFormaPagoTO> getComboFormaPagoTO(String empresa) throws Exception {
        return formaPagoDao.getComboFormaPago(empresa);
    }

}
