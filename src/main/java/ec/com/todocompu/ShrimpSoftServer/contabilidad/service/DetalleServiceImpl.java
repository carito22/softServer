package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.DetalleDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesContabilidad;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentas;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;

@Service
public class DetalleServiceImpl implements DetalleService {

    @Autowired
    private DetalleDao detalleDao;

    private boolean comprobar = false;

    @Override
    public int buscarConteoDetalleEliminarCuenta(String empCodigo, String cuentaCodigo) throws Exception {
        return detalleDao.buscarConteoDetalleEliminarCuenta(empCodigo, cuentaCodigo);
    }

    @Override
    public ConContableConDetalle buscarConContableConDetalle(Long detSecuencia) throws Exception {
        ConContableConDetalle conContableConDetalleTO = null;
        ConDetalle conDetalleAux = detalleDao.obtenerPorId(ConDetalle.class, detSecuencia);
        if (conDetalleAux != null) {
            conContableConDetalleTO = new ConContableConDetalle();
            ConContable conContable = conDetalleAux.getConContable();
            ConCuentas conCuentas = conDetalleAux.getConCuentas();
            ConDetalle conDetalle = ConversionesContabilidad.convertirConDetalle_ConDetalle(conDetalleAux);
            conDetalle.setConContable(conContable);
            conDetalle.setConCuentas(conCuentas);
            ///////
            conContableConDetalleTO.setConContable(conContable);
            conContableConDetalleTO.setConDetalle(conDetalle);
        }
        return conContableConDetalleTO;
    }

    @Override
    public ConDetalle buscarDetalleContable(Long codDetalle) throws Exception {
        return detalleDao.obtenerPorId(ConDetalle.class, codDetalle);
    }

    @Override
    public List<ConDetalle> getListConDetalle(ConContablePK conContablePK) throws Exception {
        return detalleDao.getListConDetalle(conContablePK);
    }

    @Override
    public List<ConDetalleTO> buscarConContable(String codEmpresa, String perCodigo, String tipCodigo, String conNumero)
            throws Exception {
        return detalleDao.getConDetalleTO(codEmpresa, perCodigo, tipCodigo, conNumero);
    }

    @Override
    public List<ConDetalleTablaTO> getListaConDetalleTO(String empresa, String periodo, String tipo, String numero) {
        return detalleDao.getListaConDetalleTO(empresa, periodo, tipo, numero);
    }
}
