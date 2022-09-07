package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface PresupuestoPescaMotivoDao extends GenericDao<PrdPresupuestoPescaMotivo, PrdPresupuestoPescaMotivoPK> {

	public boolean insertarPrdPresupuestoPescaMotivo(PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo,
			SisSuceso sisSuceso) throws Exception;

	public boolean modificarPrdPresupuestoPescaMotivo(PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo,
			SisSuceso sisSuceso) throws Exception;

	public boolean eliminarPrdPresupuestoPescaMotivo(PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo,
			SisSuceso sisSuceso) throws Exception;

	public PrdPresupuestoPescaMotivo getPrdPresupuestoPescaMotivo(String empresa, String codigo) throws Exception;

	public List<PrdPresupuestoPescaMotivo> getListaPrdPresupuestoPescaMotivo(String empresa) throws Exception;
        
        public List<PrdPresupuestoPescaMotivo> getListaPrdPresupuestoPescaMotivoTO(String empresa, boolean inactivo) throws Exception;

	public boolean banderaEliminarPresupuestoPescaMotivo(String empresa, String codigo) throws Exception;
}
