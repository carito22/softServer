package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionMotivoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface LiquidacionMotivoDao extends GenericDao<PrdLiquidacionMotivo, PrdLiquidacionMotivoPK> {

	public PrdLiquidacionMotivoTO getPrdLiquidacionMotivoTO(String empresa,
			PrdLiquidacionMotivoTablaTO prdLiquidacionMotivoTablaTO) throws Exception;

	public List<PrdLiquidacionMotivoTablaTO> getListaPrdLiquidacionMotivoTablaTO(String empresa) throws Exception;

	public List<PrdLiquidacionMotivoComboTO> getListaLiquidacionMotivoComboTO(String empresa, boolean filtrarInactivos)
			throws Exception;

	public boolean insertarPrdLiquidacionMotivo(PrdLiquidacionMotivo prdLiquidacionMotivo, SisSuceso sisSuceso)
			throws Exception;

	public boolean modificarPrdLiquidacionMotivo(PrdLiquidacionMotivo prdLiquidacionMotivo, SisSuceso sisSuceso)
			throws Exception;

	public boolean eliminarPrdLiquidacionMotivo(PrdLiquidacionMotivo prdLiquidacionMotivo, SisSuceso sisSuceso)
			throws Exception;

	public PrdLiquidacionMotivo getPrdLiquidacionMotivo(String empresa, String codigo) throws Exception;

	public List<PrdLiquidacionMotivo> getListaPrdLiquidacionMotivo(String empresa) throws Exception;
        
        public List<PrdLiquidacionMotivo> getListaPrdLiquidacionMotivoTO(String empresa, boolean inactivo) throws Exception;

	public boolean banderaEliminarLiquidacionMotivo(String empresa, String codigo) throws Exception;
}
