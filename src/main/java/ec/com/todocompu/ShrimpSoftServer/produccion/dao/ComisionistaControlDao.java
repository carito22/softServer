package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionComisionistaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdComisionista;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdComisionistaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ComisionistaControlDao extends GenericDao<PrdComisionista, PrdComisionistaPK> {

    public boolean insertarComisionista(PrdComisionista prdComisionista, SisSuceso sisSuceso) throws Exception;

    public List<PrdComisionista> listarComisionista(String empresa) throws Exception;

    public boolean actualizarComisionista(PrdComisionista prdComisionista, SisSuceso sisSuceso) throws Exception;

    public List<PrdLiquidacionComisionistaTO> buscarListadoLiquidacionComisionista(String empresa, String sector, String piscina, String fechaDesde, String fechaHasta, String comisionista, boolean incluirTodos) throws Exception;
}
