package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.CuentasFlujoDetalleDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesContabilidad;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasFlujoDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasFlujoDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasFlujoDetallePK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class CuentasFlujoDetalleServiceImpl implements CuentasFlujoDetalleService {

    @Autowired
    private CuentasFlujoDetalleDao cuentasFlujoDetalleDao;

    private boolean comprobar = false;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public boolean insertarConCuentaFlujoDetalle(ConCuentasFlujoDetalleTO conCuentasFlujoDetalleTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        ConCuentasFlujoDetalle conCuentasFlujoDetalle = cuentasFlujoDetalleDao.obtenerPorId(
                ConCuentasFlujoDetalle.class, new ConCuentasFlujoDetallePK(conCuentasFlujoDetalleTO.getFluEmpresa(),
                        conCuentasFlujoDetalleTO.getFluCodigo(), conCuentasFlujoDetalleTO.getDetDebitoCredito()));
        if (conCuentasFlujoDetalle == null) {
            susClave = conCuentasFlujoDetalleTO.getFluCodigo();
            susDetalle = "Se insertó cuenta " + conCuentasFlujoDetalleTO.getFluCodigo() + " --> "
                    + conCuentasFlujoDetalleTO.getDetCuentaContable();
            susSuceso = "INSERT";
            susTabla = "cont.con_cuentas_flujo_detalle";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            conCuentasFlujoDetalleTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            conCuentasFlujoDetalle = ConversionesContabilidad
                    .convertirConCuentasFlujoDetalleTO_ConCuentasFlujoDetalle(conCuentasFlujoDetalleTO);
            comprobar = cuentasFlujoDetalleDao.insertarConCuentaFlujoDetalle(conCuentasFlujoDetalle, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public boolean modificarConCuentaFlujoDetalle(ConCuentasFlujoDetalleTO conCuentasFlujoDetalleTO,
            String codigoCambiarLlave, char formaPagoAnterior, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        ConCuentasFlujoDetalle conCuentasFlujoDetalleAux = null;
        if (codigoCambiarLlave.trim().isEmpty()) {
            conCuentasFlujoDetalleAux = cuentasFlujoDetalleDao.obtenerPorId(ConCuentasFlujoDetalle.class,
                    new ConCuentasFlujoDetallePK(conCuentasFlujoDetalleTO.getFluEmpresa().trim(),
                            conCuentasFlujoDetalleTO.getDetCuentaContable().trim(),
                            conCuentasFlujoDetalleTO.getDetDebitoCredito()));

        } else {
            conCuentasFlujoDetalleAux = cuentasFlujoDetalleDao.obtenerPorId(ConCuentasFlujoDetalle.class,
                    new ConCuentasFlujoDetallePK(conCuentasFlujoDetalleTO.getFluEmpresa().trim(),
                            codigoCambiarLlave, formaPagoAnterior));

        }
        if (conCuentasFlujoDetalleAux != null && codigoCambiarLlave.trim().isEmpty()) {
            susClave = conCuentasFlujoDetalleTO.getFluCodigo();
            susDetalle = "Se modificó a la cuenta flujo con código " + conCuentasFlujoDetalleTO.getFluCodigo();
            susSuceso = "UPDATE";
            susTabla = "cont.con_cuentas_flujo_detalle";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            conCuentasFlujoDetalleTO.setUsrFechaInserta(conCuentasFlujoDetalleAux.getUsrCodigo());
            conCuentasFlujoDetalleTO.setUsrFechaInserta(
                    UtilsValidacion.fecha(conCuentasFlujoDetalleAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            ConCuentasFlujoDetalle conCuentasFlujoDetalle = ConversionesContabilidad
                    .convertirConCuentasFlujoDetalleTO_ConCuentasFlujoDetalle(conCuentasFlujoDetalleTO);
            comprobar = cuentasFlujoDetalleDao.modificarConCuentaFlujoDetalle(conCuentasFlujoDetalle, sisSuceso);

        } else if (conCuentasFlujoDetalleAux != null && !codigoCambiarLlave.trim().isEmpty()) {
            susClave = conCuentasFlujoDetalleTO.getFluCodigo();
            susDetalle = "Se modificó la cuenta contable con código " + codigoCambiarLlave;
            susSuceso = "UPDATE";
            susTabla = "cont.con_cuentas_flujo_detalle";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            conCuentasFlujoDetalleTO.setUsrCodigo(conCuentasFlujoDetalleAux.getUsrCodigo());
            conCuentasFlujoDetalleTO.setUsrFechaInserta(
                    UtilsValidacion.fecha(conCuentasFlujoDetalleAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            ConCuentasFlujoDetalle conCuentasFlujoDetalle = ConversionesContabilidad
                    .convertirConCuentasFlujoDetalleTO_ConCuentasFlujoDetalle(conCuentasFlujoDetalleTO);
            if (conCuentasFlujoDetalleAux.getConCuentasFlujoDetallePK().getDetCuentaContable()
                    .equals(conCuentasFlujoDetalle.getConCuentasFlujoDetallePK().getDetCuentaContable())
                    && conCuentasFlujoDetalleAux.getConCuentasFlujoDetallePK().getDetEmpresa()
                            .equals(conCuentasFlujoDetalle.getConCuentasFlujoDetallePK().getDetEmpresa())
                    && conCuentasFlujoDetalleAux.getConCuentasFlujoDetallePK()
                            .getDetDebitoCredito() == conCuentasFlujoDetalle.getConCuentasFlujoDetallePK()
                            .getDetDebitoCredito()) {
                comprobar = cuentasFlujoDetalleDao.modificarConCuentaFlujoDetalle(conCuentasFlujoDetalle,
                        sisSuceso);
            } else {
                comprobar = cuentasFlujoDetalleDao.modificarConCuentaLlavePrincipalFlujoDetalle(
                        conCuentasFlujoDetalleAux, conCuentasFlujoDetalle, sisSuceso);
            }
        } else if (conCuentasFlujoDetalleAux == null && codigoCambiarLlave.trim().isEmpty()) {
            comprobar = false;
        } else if (conCuentasFlujoDetalleAux == null && !codigoCambiarLlave.trim().isEmpty()) {
            susClave = conCuentasFlujoDetalleTO.getFluCodigo();
            susDetalle = "Se modificó la cuenta contable con código " + codigoCambiarLlave;
            susSuceso = "UPDATE";
            susTabla = "cont.con_cuentas_flujo_detalle";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                    sisInfoTO);
            conCuentasFlujoDetalleTO.setUsrFechaInserta(conCuentasFlujoDetalleAux.getUsrCodigo());
            conCuentasFlujoDetalleTO.setUsrFechaInserta(UtilsValidacion
                    .fecha(conCuentasFlujoDetalleAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            ConCuentasFlujoDetalle conCuentasFlujoDetalle = ConversionesContabilidad
                    .convertirConCuentasFlujoDetalleTO_ConCuentasFlujoDetalle(conCuentasFlujoDetalleTO);
            comprobar = cuentasFlujoDetalleDao.modificarConCuentaFlujoDetalle(conCuentasFlujoDetalle,
                    sisSuceso);

        }
        return comprobar;
    }

    @Override
    public List<ConCuentasFlujoDetalleTO> getListaConCuentasFlujoDetalleTO(String empresa) throws Exception {
        return cuentasFlujoDetalleDao.getListaConCuentasFlujoDetalleTO(empresa);
    }

    public List<ConCuentasFlujoDetalleTO> getListaBuscarConCuentasFlujoDetalleTO(String empresa, String buscar)
            throws Exception {
        return cuentasFlujoDetalleDao.getListaBuscarConCuentasFlujoDetalleTO(empresa, buscar);
    }

}
