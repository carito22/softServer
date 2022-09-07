package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoMarcaComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMarca;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMarcaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class ProductoMarcaDaoImpl extends GenericDaoImpl<InvProductoMarca, InvProductoMarcaPK>
        implements ProductoMarcaDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public Boolean accionInvProductoMarca(InvProductoMarca invProductoMarca, SisSuceso sisSuceso, char accion)
            throws Exception {
        if (accion == 'I') {
            insertar(invProductoMarca);
        }
        if (accion == 'M') {
            actualizar(invProductoMarca);
        }
        if (accion == 'E') {
            eliminar(invProductoMarca);
        }
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public InvProductoMarca buscarMarcaProducto(String empresa, String codigoMarca) throws Exception {
        return obtenerPorId(InvProductoMarca.class, new InvProductoMarcaPK(empresa, codigoMarca));
    }

    @Override
    public boolean comprobarInvProductoMarca(String empresa, String codigo, String detalle, String accion)
            throws Exception {
        String sql = "";
        if (accion.compareTo("INSERTAR") == 0) {
            sql = "SELECT COUNT(*)!=0 FROM inventario.inv_producto_marca " + "WHERE (mar_empresa = '" + empresa
                    + "' AND mar_codigo = '" + codigo + "')";
        } else {
            sql = "SELECT * FROM inventario.fun_sw_producto_marca_repetido('" + empresa + "','" + codigo + "', '"
                    + detalle + "');";
        }
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

    @Override
    public List<InvProductoMarcaComboListadoTO> getInvMarcaComboListadoTO(String empresa) throws Exception {
        String sql = "select mar_codigo,mar_detalle,mar_abreviado,mar_incluir_en_facturacion from inventario.inv_producto_marca "
                + "where mar_empresa = ('" + empresa + "') ORDER BY mar_codigo";

        return genericSQLDao.obtenerPorSql(sql, InvProductoMarcaComboListadoTO.class);
    }

    @Override
    public boolean comprobarEliminarInvProductoMarca(String empresa, String codigo) throws Exception {

        String sql = "SELECT * FROM inventario.fun_sw_producto_marca_eliminar(" + "'" + empresa + "', '" + codigo
                + "')";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

}
