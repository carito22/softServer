package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaDetalleLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionDetalle;

public interface LiquidacionDetalleDao extends GenericDao<PrdLiquidacionDetalle, Integer> {

    public List<PrdListaDetalleLiquidacionTO> getListaPrdLiquidacionDetalleTO(String empresa, String motivo, String numero) throws Exception;

    public List<PrdLiquidacionDetalle> getListaPrdLiquidacionDetalle(String empresa, String motivo, String numero) throws Exception;

    public void eliminarPorSql(Integer detSecuencial);
}
