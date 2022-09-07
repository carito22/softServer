package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCategoriaProveedorComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorCategoria;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorCategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class ProveedorCategoriaDaoImpl extends GenericDaoImpl<InvProveedorCategoria, InvProveedorCategoriaPK>
        implements ProveedorCategoriaDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public Boolean accionInvProveedorCategoria(InvProveedorCategoria invProveedorCategoria, SisSuceso sisSuceso,
            char accion) throws Exception {
        if (accion == 'I') {
            insertar(invProveedorCategoria);
        }
        if (accion == 'M') {

            actualizar(invProveedorCategoria);
        }
        if (accion == 'E') {

            eliminar(invProveedorCategoria);
        }
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public InvProveedorCategoria buscarInvProveedorCategoria(String empresa, String codigo) throws Exception {
        return obtenerPorId(InvProveedorCategoria.class, new InvProveedorCategoriaPK(empresa, codigo));
    }

    @Override
    public InvProveedorCategoria buscarInvProveedorCategoria(String empresa, String codigo, String detalle) throws Exception {
        String sql = "SELECT * FROM inventario.inv_proveedor_categoria " + "WHERE (pc_empresa = '"
                + empresa + "' AND pc_codigo = '" + codigo + "' AND pc_detalle = '" + detalle + "');";
        return genericSQLDao.obtenerObjetoPorSql(sql, InvProveedorCategoria.class);
    }

    @Override
    public boolean comprobarInvProveedorCategoria(String empresa, String codigo) throws Exception {
        String sql = "SELECT COUNT(*)!=0 " + "FROM inventario.inv_proveedor_categoria " + "WHERE (pc_empresa = '"
                + empresa + "' AND pc_codigo = '" + codigo + "');";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

    @Override
    public List<InvProveedorCategoriaTO> getInvProveedorCategoriaTO(String empresa) throws Exception {
        String sql = "SELECT pc_empresa, pc_codigo, pc_detalle, pc_aplica_retencion_iva, usr_empresa, usr_codigo, usr_fecha_inserta "
                + "FROM inventario.inv_proveedor_categoria " + "WHERE (pc_empresa = '" + empresa
                + "') ORDER BY pc_codigo";
        return genericSQLDao.obtenerPorSql(sql, InvProveedorCategoriaTO.class);
    }

    @Override
    public List<InvCategoriaProveedorComboTO> getListaCategoriaProveedorComboTO(String empresa) throws Exception {
        String sql = "SELECT pc_codigo, pc_detalle FROM inventario.inv_proveedor_categoria WHERE (pc_empresa = '"
                + empresa + "') ORDER BY pc_codigo";

        return genericSQLDao.obtenerPorSql(sql, InvCategoriaProveedorComboTO.class);
    }

    @Override
    public boolean comprobarEliminarInvProveedorCategoria(String empresa, String codigo) throws Exception {
        String sql = "SELECT inventario.fun_sw_proveedor_categoria_eliminar(" + "'" + empresa + "', '" + codigo + "');";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

}
