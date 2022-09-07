package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesProduccion;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionTallaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionTallaTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdTalla;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdTallaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class TallaDaoImpl extends GenericDaoImpl<PrdTalla, PrdTallaPK> implements TallaDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sisSucesoDao;

    @Override
    public PrdLiquidacionTallaTO getPrdLiquidacionTallaTO(String empresa,
            PrdLiquidacionTallaTablaTO liquidacionTallaTablaTO) throws Exception {
        return ConversionesProduccion.convertirPrdLiquidacionTalla_PrdLiquidacionTallaTO(
                obtenerPorId(PrdTalla.class, new PrdTallaPK(empresa, liquidacionTallaTablaTO.getTallaCodigo())));
    }

    @Override
    public boolean insertarPrdLiquidacionTalla(PrdTalla prdLiquidacionTalla, SisSuceso sisSuceso) throws Exception {
        insertar(prdLiquidacionTalla);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarPrdLiquidacionTalla(PrdTalla prdLiquidacionTalla, SisSuceso sisSuceso) throws Exception {
        actualizar(prdLiquidacionTalla);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarPrdLiquidacionTalla(PrdTalla prdLiquidacionTalla, SisSuceso sisSuceso) throws Exception {
        eliminar(prdLiquidacionTalla);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public PrdTalla getPrdLiquidacionTalla(String empresa, String codigo) throws Exception {
        return obtenerPorId(PrdTalla.class, new PrdTallaPK(empresa, codigo));
    }

    @Override
    public List<PrdTalla> getListaPrdLiquidacionTalla(String empresa, BigDecimal desde, BigDecimal hasta, String codigo) throws Exception {
        String sql = "select * from produccion.prd_talla where ((" + desde + " between talla_gramos_desde and talla_gramos_hasta)"
                + " or (" + hasta + " between talla_gramos_desde and talla_gramos_hasta)) "
                + " and talla_empresa = '" + empresa + "' and talla_codigo != '" + codigo + "';";
        return genericSQLDao.obtenerPorSql(sql, PrdTalla.class);
    }

    @Override
    public List<PrdTalla> getListaPrdLiquidacionTalla(String empresa, boolean presupuestoPesca) throws Exception {
        if (presupuestoPesca) {
            return obtenerPorHql(
                    "SELECT plt FROM PrdTalla plt WHERE plt.prdLiquidacionTallaPK.tallaEmpresa=?1 and plt.tallaPrecio>?2 order by plt.tallaDetalle desc",
                    new Object[]{empresa, new BigDecimal("0.00")});
        } else {
            return obtenerPorHql(
                    "SELECT plt FROM PrdTalla plt WHERE plt.prdLiquidacionTallaPK.tallaEmpresa=?1 order by plt.tallaDetalle desc",
                    new Object[]{empresa});
        }
    }

    @Override
    public List<PrdTalla> getListaPrdLiquidacionTalla(String empresa, boolean presupuestoPesca, boolean inactivos) throws Exception {
        String sql;
        if (presupuestoPesca) {
            sql = inactivos ? "" : "and talla_inactivo=?3";
            return obtenerPorHql(
                    "SELECT plt FROM PrdTalla plt WHERE plt.prdLiquidacionTallaPK.tallaEmpresa=?1 and plt.tallaPrecio>?2 " + sql + " order by plt.tallaDetalle desc",
                    inactivos ? new Object[]{empresa, new BigDecimal("0.00")} : new Object[]{empresa, new BigDecimal("0.00"), false});

        } else {
            sql = inactivos ? "" : "and talla_inactivo=?2";
            return obtenerPorHql(
                    "SELECT plt FROM PrdTalla plt WHERE plt.prdLiquidacionTallaPK.tallaEmpresa=?1 " + sql + " order by plt.tallaDetalle desc",
                    inactivos ? new Object[]{empresa} : new Object[]{empresa, false});
        }
    }

    @Override
    public boolean banderaEliminarLiquidacionTalla(String empresa, String codigo) throws Exception {
        try {
            String sql = "SELECT lte as bneliminar FROM produccion.fun_sw_liquidacion_talla_eliminar('" + empresa
                    + "', '" + codigo + "') lte;";
            return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<PrdLiquidacionTallaTablaTO> getListaPrdLiquidacionTallaTablaTO(String empresa) throws Exception {
        String sql = "SELECT talla_codigo, talla_detalle FROM produccion.prd_talla WHERE talla_empresa = ('" + empresa
                + "') ORDER BY talla_codigo";
        return genericSQLDao.obtenerPorSql(sql, PrdLiquidacionTallaTablaTO.class);
    }

    @Override
    public List<PrdLiquidacionTallaTablaTO> getListaPrdLiquidacionTallaTablaTO(String empresa, String codigo)
            throws Exception {
        codigo = codigo == null ? "" : codigo;
        String sql = "SELECT talla_codigo, talla_detalle,talla_inactivo FROM produccion.prd_talla "
                + "WHERE talla_empresa = ('" + empresa + "') AND ((talla_codigo LIKE '%" + codigo
                + "%') OR (talla_detalle LIKE '%" + codigo + "%')) ORDER BY talla_codigo";
        return genericSQLDao.obtenerPorSql(sql, PrdLiquidacionTallaTablaTO.class);
    }

    //listaTallas
    @Override
    public List<PrdTalla> getListaPrdLiquidacionTallaDetalle(String empresa) throws Exception {
        empresa = empresa == null ? "" : empresa;
        String sql = "SELECT * FROM produccion.prd_talla WHERE talla_empresa = '" + empresa + "'";
        //String sql="SELECT pt.talla_codigo, pt.talla_detalle FROM  produccion.prd_talla as pt WHERE pt.talla_empresa='"+empresa+"'";
        return genericSQLDao.obtenerPorSql(sql, PrdTalla.class);
    }

    @Override
    public List<PrdTalla> getListaPrdLiquidacionTallaDetalle(String empresa, String busqueda) throws Exception {
        String complementoSql = "";
        if (busqueda != null && !busqueda.equals("")) {
            complementoSql = " AND (talla_codigo || UPPER(talla_detalle)  LIKE TRANSLATE(' ' || CASE WHEN ('" + busqueda + "') = ''  THEN '~' ELSE ('" + busqueda
                    + "') END || ' ', ' ', '%')) ";
        }

        String sql = "SELECT * FROM produccion.prd_talla WHERE talla_empresa = ('" + empresa + "') "
                + complementoSql;
        return genericSQLDao.obtenerPorSql(sql, PrdTalla.class);
    }

}
