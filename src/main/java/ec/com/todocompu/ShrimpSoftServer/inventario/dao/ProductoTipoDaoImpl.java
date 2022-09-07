package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoTipoProductoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoTipoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoTipo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoTipoProducto;

@Repository
public class ProductoTipoDaoImpl extends GenericDaoImpl<InvProductoTipo, InvProductoTipoPK> implements ProductoTipoDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoTipoProductoDao sucesoTipoProductoDao;

    @Override
    public Boolean accionInvProductoTipo(InvProductoTipo invProductoTipo, SisSuceso sisSuceso, char accion)
            throws Exception {
        if (accion == 'I') {
            insertar(invProductoTipo);
        }
        if (accion == 'M') {
            actualizar(invProductoTipo);
        }
        if (accion == 'E') {
            eliminar(invProductoTipo);
        }
        sucesoDao.insertar(sisSuceso);
        ///////crear suceso/////////

        if (accion == 'I' || accion == 'M') {
            SisSucesoTipoProducto sucesoTipo = new SisSucesoTipoProducto();
            String json = UtilsJSON.objetoToJson(invProductoTipo);
            sucesoTipo.setSusJson(json);
            sucesoTipo.setSisSuceso(sisSuceso);
            sucesoTipo.setInvProductoTipo(invProductoTipo);
            sucesoTipoProductoDao.insertar(sucesoTipo);
        }
        return true;
    }

    @Override
    public InvProductoTipo buscarInvProductoTipo(String empresa, String codigo) throws Exception {
        return obtenerPorId(InvProductoTipo.class, new InvProductoTipoPK(empresa, codigo));
    }

    @Override
    public boolean comprobarInvProductoTipo(String empresa, String codigo) throws Exception {
        String sql = "SELECT COUNT(*)!=0 FROM inventario.inv_producto_tipo " + "WHERE (tip_empresa = '" + empresa + "' AND tip_codigo = '" + codigo + "')";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

    @Override
    public boolean comprobarInvProductoTipoActivoFijo(String empresa, String codigo) throws Exception {
        String sql = "SELECT COUNT(*)!=0 FROM inventario.inv_producto_tipo " + "WHERE (tip_empresa = '" + empresa + "' AND tip_codigo = '" + codigo + "' AND tip_tipo='ACTIVO FIJO')";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

    @Override
    public List<InvProductoTipoComboTO> getInvProductoTipoComboListadoTO(String empresa, String accion) throws Exception {
        String sql = "";
        if (accion.trim().equals("COMBO")) {
            sql = "SELECT tip_codigo, tip_detalle, tip_tipo, tip_activo, cta_empresa, cta_codigo FROM inventario.inv_producto_tipo WHERE "
                    + "tip_empresa= ('" + empresa + "') AND tip_activo ORDER BY tip_codigo";
        } else {
            sql = "SELECT tip_codigo, tip_detalle, tip_tipo, tip_activo, cta_empresa, cta_codigo FROM inventario.inv_producto_tipo WHERE tip_empresa= ('" + empresa + "')";
        }
        return genericSQLDao.obtenerPorSql(sql, InvProductoTipoComboTO.class);
    }

    @Override
    public List<InvProductoTipoComboTO> getInvProductoTipoComboListadoTOActivoFijo(String empresa, String accion) throws Exception {
        String sql = "";
        if (accion.trim().equals("COMBO")) {
            sql = "SELECT tip_codigo, tip_detalle, tip_tipo, tip_activo, cta_empresa, cta_codigo FROM inventario.inv_producto_tipo WHERE "
                    + "tip_empresa= ('" + empresa + "') AND tip_tipo='ACTIVO FIJO' AND tip_activo ORDER BY tip_codigo";
        } else {
            sql = "SELECT tip_codigo, tip_detalle, tip_tipo, tip_activo, cta_empresa, cta_codigo FROM inventario.inv_producto_tipo WHERE tip_empresa= ('" + empresa + "') AND tip_tipo='ACTIVO FIJO'";
        }
        return genericSQLDao.obtenerPorSql(sql, InvProductoTipoComboTO.class);
    }

    @Override
    public Boolean comprobarEliminarInvProductoTipo(String empresa, String codigo) throws Exception {
        String sql = "SELECT inventario.fun_sw_producto_tipo_eliminar(" + "'" + empresa + "', '" + codigo + "')";
        return (Boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

}
