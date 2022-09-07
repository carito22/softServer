package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesProduccion;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionProductoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdProducto;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdProductoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class PrdProductoDaoImpl extends GenericDaoImpl<PrdProducto, PrdProductoPK> implements PrdProductoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sisSucesoDao;

    @Override
    public PrdLiquidacionProductoTO getPrdLiquidacionProductoTO(String empresa,
            PrdLiquidacionProductoTablaTO liquidacionProductoTablaTO) throws Exception {
        return ConversionesProduccion.convertirPrdLiquidacionProducto_PrdLiquidacionProductoTO(obtenerPorId(
                PrdProducto.class, new PrdProductoPK(empresa, liquidacionProductoTablaTO.getProdCodigo())));
    }

    @Override
    public List<PrdLiquidacionProductoTablaTO> getListaPrdLiquidacionProductoTablaTO(String empresa) throws Exception {
        String sql = "SELECT prod_codigo, prod_detalle, prod_tipo, prod_clase, prod_inactivo FROM produccion.prd_Producto "
                + "WHERE prod_empresa = ('" + empresa + "') ORDER BY prod_codigo";
        return genericSQLDao.obtenerPorSql(sql, PrdLiquidacionProductoTablaTO.class);
    }

    @Override
    public List<PrdLiquidacionProductoTablaTO> getListaPrdLiquidacionProductoTablaTO(String empresa, String codigo)
            throws Exception {
        String sql = "SELECT prod_codigo, prod_detalle, prod_tipo, prod_clase FROM produccion.prd_Producto "
                + "WHERE prod_empresa = ('" + empresa + "' ) AND prod_inactivo = 'f' AND ((prod_codigo LIKE '%" + codigo
                + "%') OR (prod_detalle LIKE '%" + codigo + "%'))  ORDER BY prod_codigo";
        return genericSQLDao.obtenerPorSql(sql, PrdLiquidacionProductoTablaTO.class);
    }

    @Override
    public boolean insertarPrdLiquidacionProducto(PrdProducto prdLiquidacionProducto, SisSuceso sisSuceso)
            throws Exception {
        insertar(prdLiquidacionProducto);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarPrdLiquidacionProducto(PrdProducto prdLiquidacionProducto, SisSuceso sisSuceso)
            throws Exception {
        actualizar(prdLiquidacionProducto);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarPrdLiquidacionProducto(PrdProducto prdLiquidacionProducto, SisSuceso sisSuceso)
            throws Exception {
        eliminar(prdLiquidacionProducto);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public PrdProducto getPrdLiquidacionProducto(String empresa, String codigo) throws Exception {
        return obtenerPorId(PrdProducto.class, new PrdProductoPK(empresa, codigo));
    }

    @Override
    public List<PrdProducto> getListaPrdLiquidacionProducto(String empresa) throws Exception {
        return obtenerPorHql("SELECT lp FROM PrdProducto lp inner join lp.prdLiquidacionProductoPK lppk "
                + "WHERE lppk.prodEmpresa=?1 ORDER BY lppk.prodCodigo", new Object[]{empresa});
    }

    @Override
    public List<PrdProducto> getListaPrdLiquidacionProductos(String empresa, boolean inactivos) throws Exception {
        String sql = inactivos ? "" : " AND prod_inactivo=?2";
        return obtenerPorHql("SELECT lp FROM PrdProducto lp inner join lp.prdLiquidacionProductoPK lppk "
                + "WHERE lppk.prodEmpresa=?1 " + sql + " ORDER BY lppk.prodCodigo", inactivos ? new Object[]{empresa} : new Object[]{empresa, false});
    }

    @Override
    public boolean banderaEliminarLiquidacionProducto(String empresa, String codigo) throws Exception {
        String sql = "SELECT * FROM produccion.fun_sw_liquidacion_motivo_eliminar('" + empresa + "','" + codigo + "');";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public List<PrdProducto> getListaPrdLiquidacionProductos(String empresa, char clase, String tipo) throws Exception {
        String sql = "SELECT * FROM produccion.prd_producto WHERE prod_empresa='" + empresa + "' AND prod_clase='" + clase
                + "' AND prod_detalle LIKE '%" + tipo + "%'";
        return genericSQLDao.obtenerPorSql(sql, PrdProducto.class);

    }

}
