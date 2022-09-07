package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxPorcentajeIce;
import java.util.List;

public interface AnexoPorcentajeIceDao extends GenericDao<AnxPorcentajeIce, Integer> {

    public List<AnxPorcentajeIce> listarAnexoPorcentajeIce() throws Exception;

}
