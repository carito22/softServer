package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface PreLiquidacionMotivoDao extends GenericDao<PrdPreLiquidacionMotivo, PrdPreLiquidacionMotivoPK> {

    public boolean insertarPrdPreLiquidacionMotivo(PrdPreLiquidacionMotivo prdPreLiquidacionMotivo, SisSuceso sisSuceso)
            throws Exception;

    public boolean modificarPrdPreLiquidacionMotivo(PrdPreLiquidacionMotivo prdPreLiquidacionMotivo,
            SisSuceso sisSuceso) throws Exception;

    public boolean eliminarPrdPreLiquidacionMotivo(PrdPreLiquidacionMotivo prdPreLiquidacionMotivo, SisSuceso sisSuceso)
            throws Exception;

    public PrdPreLiquidacionMotivo getPrdPreLiquidacionMotivo(String empresa, String codigo) throws Exception;

    public List<PrdPreLiquidacionMotivo> getListaPrdPreLiquidacionMotivo(String empresa) throws Exception;

    public List<PrdPreLiquidacionMotivo> getListaPrdPreLiquidacionMotivoTO(String empresa, boolean inactivo) throws Exception;

    public boolean banderaEliminarPreLiquidacionMotivo(String empresa, String codigo) throws Exception;
}
