package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaDetalleLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionDetalle;

@Repository
public class LiquidacionDetalleDaoImpl extends GenericDaoImpl<PrdLiquidacionDetalle, Integer> implements LiquidacionDetalleDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<PrdListaDetalleLiquidacionTO> getListaPrdLiquidacionDetalleTO(String empresa, String motivo, String numero) throws Exception {
        String sql = "SELECT * FROM produccion.fun_liquidacion_detalle('" + empresa + "', '" + motivo + "', '" + numero + "')";
        return genericSQLDao.obtenerPorSql(sql, PrdListaDetalleLiquidacionTO.class);
    }

    @Override
    public void eliminarPorSql(Integer detSecuencial) {
        genericSQLDao.ejecutarSQL("DELETE FROM produccion.prd_liquidacion_detalle WHERE det_secuencial=" + detSecuencial);
    }

    @Override
    public List<PrdLiquidacionDetalle> getListaPrdLiquidacionDetalle(String empresa, String motivo, String numero) throws Exception {
        String sql = "SELECT * FROM produccion.prd_liquidacion_detalle WHERE liq_empresa = ('" + empresa + "') AND liq_motivo = ('" + motivo + "') AND liq_numero = ('" + numero + "') " + "Order by det_secuencial ASC";
       List<PrdLiquidacionDetalle> detalle = genericSQLDao.obtenerPorSql(sql, PrdLiquidacionDetalle.class);
        return detalle;
    }

}
