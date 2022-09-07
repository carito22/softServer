package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.ListadoGrameaje;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdGrameajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListadoGrameajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdGrameaje;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdGrameajePK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface GrameajeDao extends GenericDao<PrdGrameaje, PrdGrameajePK> {

	public boolean insertarPrdGrameaje(PrdGrameaje prdGrameaje, SisSuceso sisSuceso) throws Exception;

	public boolean eliminarPrdGrameaje(PrdGrameaje prdGrameaje, SisSuceso sisSuceso) throws Exception;

	public String obtenerFechaGrameajeSuperior(String empresa, String sector, String numPiscina) throws Exception;

	public boolean getIsFechaGrameajeSuperior(String empresa, String sector, String numPiscina, String fechaDesde,
			String fechaHasta, String fecha) throws Exception;

	public PrdGrameajeTO getPrdGrameajeTO(String empresa, String sector, String piscina, String fecha) throws Exception;

	public PrdGrameajeTO getPrdGrameajeTO(String empresa, String sector, String piscina, String desde, String hasta)
			throws Exception;

	public PrdGrameaje getPrdGrameaje(String empresa, String sector, String piscina) throws Exception;

	public List<PrdListadoGrameajeTO> getPrdListadoGrameajeTO(String empresa, String sector, String piscina,
			String desde, String hasta) throws Exception;

	public List<PrdGrameaje> getPrdListadoGrameaje(String empresa, String sector, String fecha) throws Exception;

	public List<ListadoGrameaje> obtenerGrameajePorFechasCorrida(String empresa, String sector, String piscina,
			String desde, String hasta);
}
