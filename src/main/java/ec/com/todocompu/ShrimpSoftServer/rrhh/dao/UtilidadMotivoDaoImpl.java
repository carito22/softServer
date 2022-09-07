package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidadMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidadMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class UtilidadMotivoDaoImpl extends GenericDaoImpl<RhUtilidadMotivo, RhUtilidadMotivoPK>
        implements UtilidadMotivoDao {

    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public boolean insertarRhUtilidadMotivo(RhUtilidadMotivo rhUtilidadMotivo, SisSuceso sisSuceso) throws Exception {
        insertar(rhUtilidadMotivo);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarRhUtilidadMotivo(RhUtilidadMotivo rhUtilidadMotivo, SisSuceso sisSuceso) throws Exception {
        actualizar(rhUtilidadMotivo);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarRhUtilidadMotivo(RhUtilidadMotivo rhUtilidadMotivo, SisSuceso sisSuceso) throws Exception {
        eliminar(rhUtilidadMotivo);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public RhUtilidadMotivo getRhUtilidadMotivo(String empresa, String detalle) throws Exception {
        return obtenerPorId(RhUtilidadMotivo.class, new RhUtilidadMotivoPK(empresa, detalle));
    }

    @Override
    public List<RhUtilidadMotivo> getListaRhUtilidadMotivo(String empresa) throws Exception {
        String consulta = "SELECT utimot FROM RhUtilidadMotivo utimot inner join utimot.rhUtilidadMotivoPK utimotpk "
                + "WHERE utimotpk.motEmpresa=?1 ORDER BY utimotpk.motDetalle";
        return obtenerPorHql(consulta, new Object[]{empresa});
    }

    @Override
    public List<RhUtilidadMotivo> getListaRhUtilidadMotivoSoloActivos(String empresa) throws Exception {
        String consulta = "SELECT utimot FROM RhUtilidadMotivo utimot inner join utimot.rhUtilidadMotivoPK utimotpk "
                + "WHERE utimotpk.motEmpresa=?1 AND utimot.motInactivo = false ORDER BY utimotpk.motDetalle";
        return obtenerPorHql(consulta, new Object[]{empresa});
    }

    @Override
    public boolean banderaEliminarUtilidadMotivo(String empresa, String detalle) throws Exception {
        return true;
    }

}
