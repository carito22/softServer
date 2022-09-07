package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;


import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRelacionTrabajo;
import java.util.List;

public interface RelacionTrabajoDao extends GenericDao<RhRelacionTrabajo, String> {

    public List<RhRelacionTrabajo> listaRelacionTrabajo() throws Exception;

}
