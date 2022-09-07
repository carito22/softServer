package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoProductoCategoriaDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCategoriaComboProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCategoriaSincronizarTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoCategoria;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoCategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoProductoCategoria;

@Repository
public class ProductoCategoriaDaoImpl extends GenericDaoImpl<InvProductoCategoria, InvProductoCategoriaPK>
        implements ProductoCategoriaDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoProductoCategoriaDao sucesoProductoCategoriaDao;

    @Override
    public Boolean accionInvProductoCategoria(InvProductoCategoria invProductoCategoria, SisSuceso sisSuceso,
            char accion) throws Exception {
        if (accion == 'I') {
            insertar(invProductoCategoria);
            sucesoDao.insertar(sisSuceso);
            //crear producto
            SisSucesoProductoCategoria sucesoProductoCategoria = new SisSucesoProductoCategoria();
            InvProductoCategoria copia = ConversionesInventario.convertirInvProductoCategoria_InvProductoCategoria(invProductoCategoria);
            String json = UtilsJSON.objetoToJson(copia);
            sucesoProductoCategoria.setSusJson(json);
            sucesoProductoCategoria.setSisSuceso(sisSuceso);
            sucesoProductoCategoria.setInvProductoCategoria(copia);
            sucesoProductoCategoriaDao.insertar(sucesoProductoCategoria);
        }
        if (accion == 'M') {
            actualizar(invProductoCategoria);
            sucesoDao.insertar(sisSuceso);
            //crear producto
            SisSucesoProductoCategoria sucesoProductoCategoria = new SisSucesoProductoCategoria();
            InvProductoCategoria copia = ConversionesInventario.convertirInvProductoCategoria_InvProductoCategoria(invProductoCategoria);
            String json = UtilsJSON.objetoToJson(copia);
            sucesoProductoCategoria.setSusJson(json);
            sucesoProductoCategoria.setSisSuceso(sisSuceso);
            sucesoProductoCategoria.setInvProductoCategoria(copia);
            sucesoProductoCategoriaDao.insertar(sucesoProductoCategoria);
        }
        if (accion == 'E') {
            eliminar(invProductoCategoria);
            sucesoDao.insertar(sisSuceso);
        }

        return true;
    }

    @Override
    public InvProductoCategoria buscarInvProductoCategoria(String empresa, String codigo) throws Exception {
        return obtenerPorId(InvProductoCategoria.class, new InvProductoCategoriaPK(empresa, codigo));
    }

    @Override
    public boolean comprobarInvProductoCategoria(String empresa, String codigo) throws Exception {
        String sql = "SELECT COUNT(*)!=0 " + "FROM inventario.inv_producto_categoria " + "WHERE (cat_empresa = '"
                + empresa + "' AND cat_codigo = '" + codigo + "')";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

    @Override
    public List<InvProductoCategoriaTO> getInvProductoCategoriaTO(String empresa) throws Exception {
        String sql = "SELECT cat_empresa, cat_codigo, cat_detalle, cat_precio_fijo, cat_activa, cta_empresa as cc_empresa, scat_codigo, scat_empresa, cat_cuenta_venta, cat_cuenta_compra, cat_cuenta_costo_venta "
                + " usr_empresa, usr_codigo, usr_fecha_inserta, cat_cuenta_venta, cat_cuenta_compra, cat_cuenta_costo_venta, cat_replicar FROM inventario.inv_producto_categoria "
                + "WHERE (usr_empresa = '" + empresa + "') ORDER BY cat_codigo;";
        return genericSQLDao.obtenerPorSql(sql, InvProductoCategoriaTO.class);

    }

    @Override
    public List<InvCategoriaComboProductoTO> getListaCategoriaComboTO(String empresa) throws Exception {
        String sql = "SELECT cat_codigo, cat_detalle, cat_cuenta_compra cta_codigo FROM inventario.inv_producto_categoria "
                + "WHERE cat_empresa = ('" + empresa + "') ORDER BY cat_codigo";

        return genericSQLDao.obtenerPorSql(sql, InvCategoriaComboProductoTO.class);
    }

    @Override
    public Boolean getPrecioFijoCategoriaProducto(String empresa, String codigoCategoria) throws Exception {
        String sql = "SELECT inv_producto_categoria.cat_precio_fijo FROM "
                + "inventario.inv_producto_categoria WHERE cat_empresa = ('" + empresa + "') " + "AND cat_codigo = ('"
                + codigoCategoria + "')";

        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

    @Override
    public boolean comprobarEliminarInvProductoCategoria(String empresa, String codigo) throws Exception {
        String sql = "SELECT inventario.fun_sw_producto_categoria_eliminar(" + "'" + empresa + "', '" + codigo + "');";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

    @Override
    public List<InvCategoriaSincronizarTO> invCategoriasSincronizar(String empresaOrigen, String empresaDestino)
            throws Exception {
        String sql = "SELECT * from inventario.fun_sincronizar_categorias('" + empresaOrigen + "', '" + empresaDestino
                + "')";
        return genericSQLDao.obtenerPorSql(sql, InvCategoriaSincronizarTO.class);
    }

}
