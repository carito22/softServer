package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSobrevivenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSobrevivencia;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface SobrevivenciaDao extends GenericDao<PrdSobrevivencia, Integer> {

    public boolean insertarPrdSobrevivencia(PrdSobrevivencia prdSobrevivencia, SisSuceso sisSuceso) throws Exception;

    public PrdSobrevivencia insertarSobrevivencia(PrdSobrevivencia prdSobrevivencia, SisSuceso sisSuceso) throws Exception;

    public boolean modificarPrdSobrevivencia(PrdSobrevivencia prdSobrevivencia, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarPrdSobrevivencia(PrdSobrevivencia prdSobrevivencia, SisSuceso sisSuceso) throws Exception;

    public List<PrdListaSobrevivenciaTO> getListaSobrevivencia(String empresa, String sector) throws Exception;

    public List<PrdListaSobrevivenciaTO> sobrevivenciaPorDefecto(String empresa, String sector) throws Exception;

}
