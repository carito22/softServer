package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificacionesEventos;

@Repository
public class SisEventoNotificacionDaoImpl extends GenericDaoImpl<SisEmpresaNotificacionesEventos, String> implements SisEventoNotificacionDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<SisEmpresaNotificacionesEventos> listarSisEventoNotificacion() throws Exception {
        String sql = "SELECT * FROM sistemaweb.sis_empresa_notificaciones_eventos";
        return genericSQLDao.obtenerPorSql(sql, SisEmpresaNotificacionesEventos.class);
    }
    
}
