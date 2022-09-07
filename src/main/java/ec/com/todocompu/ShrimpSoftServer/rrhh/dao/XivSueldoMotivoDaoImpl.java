package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldoMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class XivSueldoMotivoDaoImpl extends GenericDaoImpl<RhXivSueldoMotivo, RhXivSueldoMotivoPK>
        implements XivSueldoMotivoDao {

    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public boolean insertarRhXivSueldoMotivo(RhXivSueldoMotivo rhXivSueldoMotivo, SisSuceso sisSuceso)
            throws Exception {
        insertar(rhXivSueldoMotivo);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarRhXivSueldoMotivo(RhXivSueldoMotivo rhXivSueldoMotivo, SisSuceso sisSuceso)
            throws Exception {
        actualizar(rhXivSueldoMotivo);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarRhXivSueldoMotivo(RhXivSueldoMotivo rhXivSueldoMotivo, SisSuceso sisSuceso)
            throws Exception {
        eliminar(rhXivSueldoMotivo);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public RhXivSueldoMotivo getRhXivSueldoMotivo(String empresa, String detalle) throws Exception {
        return obtenerPorId(RhXivSueldoMotivo.class, new RhXivSueldoMotivoPK(empresa, detalle));
    }

    @Override
    public List<RhXivSueldoMotivo> getListaRhXivSueldoMotivo(String empresa) throws Exception {
        String consulta = "SELECT xivmot FROM RhXivSueldoMotivo xivmot inner join xivmot.rhXivSueldoMotivoPK xivmotpk "
                + "WHERE xivmotpk.motEmpresa=?1 ORDER BY xivmotpk.motDetalle";
        return obtenerPorHql(consulta, new Object[]{empresa});
    }

    @Override
    public List<RhXivSueldoMotivo> getListaRhXivSueldoMotivoSoloActivos(String empresa) throws Exception {
        String consulta = "SELECT xivmot FROM RhXivSueldoMotivo xivmot inner join xivmot.rhXivSueldoMotivoPK xivmotpk "
                + "WHERE xivmotpk.motEmpresa=?1 AND xivmot.motInactivo = false ORDER BY xivmotpk.motDetalle";
        return obtenerPorHql(consulta, new Object[]{empresa});
    }

    @Override
    public boolean banderaEliminarXivMotivo(String empresa, String detalle) throws Exception {
        return true;
    }

}
