package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.CuentasDao;
import ec.com.todocompu.ShrimpSoftServer.criteria.Criterio;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.SectorDao;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.FormaPagoBeneficiosSocialesDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesRRHH;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSectorPK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhFormaPagoBeneficiosSociales;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import org.hibernate.criterion.Restrictions;

@Service
public class FormaPagoBeneficioSocialServiceImpl implements FormaPagoBeneficioSocialService {

    @Autowired
    private CuentasDao cuentasDao;
    @Autowired
    private FormaPagoBeneficiosSocialesDao formaPagoBeneficiosSocialesDao;
    @Autowired
    private SectorDao sectorDao;
    @Autowired
    private GenericoDao<RhFormaPagoBeneficiosSociales, Integer> formaPagoBeneficiosSocialesDaoCriteria;
    @Autowired
    private SucesoDao sucesoDao;

    private Boolean comprobar = false;
    private String mensaje = "";
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public String accionRhFormaPagoBeneficioSocial(RhFormaPagoBeneficioSocialTO rhFormaPagoBeneficioSocialTO, char accion, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        ///// BUSCANDO existencia cuentas
        Boolean estadoCtaComprasForma = cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(
                rhFormaPagoBeneficioSocialTO.getUsrEmpresa(), rhFormaPagoBeneficioSocialTO.getCtaCodigo())) != null;
        ///// BUSCANDO existencia sector
        Boolean estadoSector = sectorDao.obtenerPorId(PrdSector.class, new PrdSectorPK(
                rhFormaPagoBeneficioSocialTO.getUsrEmpresa(), rhFormaPagoBeneficioSocialTO.getSecCodigo())) != null;
        if (estadoCtaComprasForma && estadoSector) {// revisar si estan
            ///// CREANDO Suceso
            susClave = rhFormaPagoBeneficioSocialTO.getFpDetalle();
            if (accion == 'I') {
                susDetalle = "Se insertó la forma de pago beneficios sociales con el detalle " + rhFormaPagoBeneficioSocialTO.getFpDetalle();
                susSuceso = "INSERT";
            }
            if (accion == 'M') {
                susDetalle = "Se modificó la forma de pago beneficios sociales con el detalle " + rhFormaPagoBeneficioSocialTO.getFpDetalle();
                susSuceso = "UPDATE";
            }
            if (accion == 'E') {
                susDetalle = "Se eliminó la forma de pago beneficios sociales con el detalle " + rhFormaPagoBeneficioSocialTO.getFpDetalle();
                susSuceso = "DELETE";
            }
            susTabla = "recursoshumanos.rh_forma_pago_beneficios_sociales";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            ///// CREANDO CarPagosForma
            rhFormaPagoBeneficioSocialTO.setUsrCodigo(sisInfoTO.getUsuario());
            rhFormaPagoBeneficioSocialTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            RhFormaPagoBeneficiosSociales rhFormaPagoBeneficiosSociales = ConversionesRRHH.convertirRhFormaPagoBeneficioSocialTO_RhFormaPagoBeneficioSocial(rhFormaPagoBeneficioSocialTO);

            if (accion == 'E') {
                ////// BUSCANDO existencia PagosForma
                if (formaPagoBeneficiosSocialesDao
                        .buscarFormaPagoBeneficioSocial(rhFormaPagoBeneficioSocialTO.getFpSecuencial()) != null) {
                    rhFormaPagoBeneficiosSociales
                            .setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                    comprobar = formaPagoBeneficiosSocialesDao
                            .accionRhFormaPagoBeneficioSocial(rhFormaPagoBeneficiosSociales, sisSuceso, accion);
                } else {
                    mensaje = "FNo se encuentra la compra forma de pago beneficios sociales.";
                }
            }
            if (accion == 'M') {
                ////// BUSCANDO existencia PagosForma
                if (formaPagoBeneficiosSocialesDao.buscarFormaPagoBeneficioSocial(rhFormaPagoBeneficioSocialTO.getFpSecuencial()) != null) {
                    RhFormaPagoBeneficiosSociales fp = obtenerFormaDePagoPorDetalles(rhFormaPagoBeneficiosSociales);
                    if (fp != null && fp.getFpSecuencial() != rhFormaPagoBeneficioSocialTO.getFpSecuencial()) {
                        mensaje = "FYa existe la forma de pago beneficios sociales con código de cuenta " + rhFormaPagoBeneficioSocialTO.getCtaCodigo();
                    } else {
                        rhFormaPagoBeneficiosSociales.setUsrFechaInserta(formaPagoBeneficiosSocialesDao.buscarFormaPagoBeneficioSocial(rhFormaPagoBeneficioSocialTO.getFpSecuencial()).getUsrFechaInserta());
                        comprobar = formaPagoBeneficiosSocialesDao.accionRhFormaPagoBeneficioSocial(rhFormaPagoBeneficiosSociales, sisSuceso, accion);
                    }
                } else {
                    mensaje = "FNo se encuentra la compra forma de pago beneficios sociales.";
                }
            }
            if (accion == 'I') {
                ////// BUSCANDO existencia PagosForma
                if (obtenerFormaDePagoPorDetalles(rhFormaPagoBeneficiosSociales) != null) {
                    rhFormaPagoBeneficiosSociales.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                    comprobar = formaPagoBeneficiosSocialesDao.accionRhFormaPagoBeneficioSocial(rhFormaPagoBeneficiosSociales, sisSuceso, accion);
                } else {
                    mensaje = "FYa existe la forma de pago beneficios sociales " + rhFormaPagoBeneficioSocialTO.getCtaCodigo();
                }
            }
        } else {
            if (!estadoCtaComprasForma) {
                mensaje = "FNo se encuentra la cuenta contable forma de pago beneficios sociales.";
            }
            if (!estadoSector) {
                mensaje = "FNo se encuentra el sector.";
            }
        }
        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TLa forma de pago beneficios sociales " + rhFormaPagoBeneficioSocialTO.getFpDetalle() + " se ha eliminado correctamente.";
            }
            if (accion == 'M') {
                mensaje = "TLa forma de pago beneficios sociales " + rhFormaPagoBeneficioSocialTO.getFpDetalle() + " se ha modificado correctamente.";
            }
            if (accion == 'I') {
                mensaje = "TLa forma de pago beneficios sociales " + rhFormaPagoBeneficioSocialTO.getFpDetalle() + " se ha guardado correctamente.";
            }
        }
        return mensaje;
    }

    @Override
    public RhFormaPagoBeneficioSocialTO insertarRhFormaPagoBeneficioSocial(RhFormaPagoBeneficioSocialTO rhFormaPagoBeneficioSocialTO, SisInfoTO sisInfoTO) throws Exception, GeneralException {
        ///// BUSCANDO existencia cuentas
        Boolean estadoCtaComprasForma = cuentasDao.obtenerPorId(ConCuentas.class, new ConCuentasPK(rhFormaPagoBeneficioSocialTO.getUsrEmpresa(), rhFormaPagoBeneficioSocialTO.getCtaCodigo())) != null;
        ///// BUSCANDO existencia sector
        Boolean estadoSector = sectorDao.obtenerPorId(PrdSector.class, new PrdSectorPK(rhFormaPagoBeneficioSocialTO.getUsrEmpresa(), rhFormaPagoBeneficioSocialTO.getSecCodigo())) != null;
        if (estadoCtaComprasForma && estadoSector) {// revisar si estan
            ///// CREANDO Suceso
            susClave = rhFormaPagoBeneficioSocialTO.getFpDetalle();
            susDetalle = "Se insertó la FormaPago con el detalle " + rhFormaPagoBeneficioSocialTO.getFpDetalle();
            susSuceso = "INSERT";
            susTabla = "recursoshumanos.rh_forma_pago";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            ///// CREANDO CarPagosForma
            rhFormaPagoBeneficioSocialTO.setUsrCodigo(sisInfoTO.getUsuario());
            rhFormaPagoBeneficioSocialTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            RhFormaPagoBeneficiosSociales rhFormaPagoBeneficiosSociales = ConversionesRRHH.convertirRhFormaPagoBeneficioSocialTO_RhFormaPagoBeneficioSocial(rhFormaPagoBeneficioSocialTO);
            if (formaPagoBeneficiosSocialesDaoCriteria.obtener(RhFormaPagoBeneficiosSociales.class, rhFormaPagoBeneficiosSociales.getFpSecuencial()) != null) {
                throw new GeneralException("Ya existe la forma de pago beneficios sociales con secuencial: " + rhFormaPagoBeneficiosSociales.getFpSecuencial());
            }
            if (obtenerFormaDePagoPorDetalles(rhFormaPagoBeneficiosSociales) != null) {
                throw new GeneralException("Ya existe la forma de pago beneficios sociales con código de cuenta " + rhFormaPagoBeneficioSocialTO.getCtaCodigo());
            }
            rhFormaPagoBeneficiosSociales.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
            rhFormaPagoBeneficiosSociales = formaPagoBeneficiosSocialesDaoCriteria.insertar(rhFormaPagoBeneficiosSociales);
            rhFormaPagoBeneficioSocialTO.setFpSecuencial(rhFormaPagoBeneficiosSociales.getFpSecuencial());
            sucesoDao.insertar(sisSuceso);
        } else {
            if (!estadoCtaComprasForma) {
                throw new GeneralException("No se encuentra la cuenta contable forma de pago beneficios sociales.");
            }
            if (!estadoSector) {
                throw new GeneralException("No se encuentra el sector.");
            }
        }
        return rhFormaPagoBeneficioSocialTO;
    }

    @Override
    public List<RhListaFormaPagoBeneficioSocialTO> getListaFormaPagoBeneficioSocialTO(String empresa) throws Exception {
        return formaPagoBeneficiosSocialesDao.getListaFormaPagoBeneficioSocial(empresa);
    }

    @Override
    public List<RhComboFormaPagoBeneficioSocialTO> getComboFormaPagoBeneficioSocialTO(String empresa) throws Exception {
        return formaPagoBeneficiosSocialesDao.getComboFormaPagoBeneficioSocial(empresa);
    }

    private RhFormaPagoBeneficiosSociales obtenerFormaDePagoPorDetalles(RhFormaPagoBeneficiosSociales formaPago) {
        Criterio filtro;
        filtro = Criterio.forClass(RhFormaPagoBeneficiosSociales.class);
        filtro.add(Restrictions.eq("ctaCodigo", formaPago.getCtaCodigo()));
        filtro.add(Restrictions.eq("secCodigo", formaPago.getSecCodigo()));
        filtro.add(Restrictions.eq("usrEmpresa", formaPago.getUsrEmpresa()));
        return formaPagoBeneficiosSocialesDaoCriteria.obtenerPorCriteriaSinProyeccionesDistinct(filtro);
    }

}
