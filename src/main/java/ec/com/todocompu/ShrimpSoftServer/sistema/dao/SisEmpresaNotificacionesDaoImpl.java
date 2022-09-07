package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class SisEmpresaNotificacionesDaoImpl extends GenericDaoImpl<SisEmpresaNotificaciones, Integer> implements SisEmpresaNotificacionesDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public boolean eliminarSisEmpresaNotificaciones(Integer idNotificacion, SisSuceso sisSuceso) throws Exception {
        eliminar(new SisEmpresaNotificaciones(idNotificacion));
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<SisEmpresaNotificaciones> listarSisEmpresaNotificaciones(String empresa) throws Exception {
        String sql = "SELECT * FROM sistemaweb.sis_empresa_notificaciones " + "WHERE (emp_codigo = '" + empresa + "')";
        return genericSQLDao.obtenerPorSql(sql, SisEmpresaNotificaciones.class);
    }

    @Override
    public SisEmpresaNotificaciones insertarSisEmpresaNotificaciones(SisEmpresaNotificaciones sisEmpresaNotificaciones, SisSuceso sisSuceso) throws Exception {
        insertar(sisEmpresaNotificaciones);
        sucesoDao.insertar(sisSuceso);
        return sisEmpresaNotificaciones;
    }

    @Override
    public SisEmpresaNotificaciones modificarSisEmpresaNotificaciones(SisEmpresaNotificaciones sisEmpresaNotificaciones, SisSuceso sisSuceso) throws Exception {
        actualizar(sisEmpresaNotificaciones);
        sucesoDao.insertar(sisSuceso);
        return sisEmpresaNotificaciones;
    }
}
