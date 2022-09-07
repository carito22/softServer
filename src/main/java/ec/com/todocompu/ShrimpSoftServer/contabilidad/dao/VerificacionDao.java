package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;


import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConVerificacionErrores;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface VerificacionDao extends GenericDao<ConVerificacionErrores, Integer> {

    public boolean insertar(ConVerificacionErrores conTipo, SisSuceso sisSuceso) throws Exception;

    public ConVerificacionErrores obtener(int secuencial) throws Exception;

}
