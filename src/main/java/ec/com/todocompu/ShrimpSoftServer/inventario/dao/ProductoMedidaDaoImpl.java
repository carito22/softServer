package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvMedidaComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoMedidaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMedida;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMedidaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class ProductoMedidaDaoImpl extends GenericDaoImpl<InvProductoMedida, InvProductoMedidaPK>
        implements ProductoMedidaDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public Boolean accionInvProductoMedida(InvProductoMedida invProductoMedida, SisSuceso sisSuceso, char accion)
            throws Exception {
        if (accion == 'I') {
            insertar(invProductoMedida);
        }
        if (accion == 'M') {

            actualizar(invProductoMedida);
        }
        if (accion == 'E') {
            eliminar(invProductoMedida);
        }
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public InvProductoMedida buscarProductoMedida(String empresa, String codigo) throws Exception {
        return obtenerPorId(InvProductoMedida.class, new InvProductoMedidaPK(empresa, codigo));
    }

    @Override
    public boolean comprobarInvProductoMedida(String empresa, String codigo) throws Exception {
        String sql = "SELECT COUNT(*)!=0 FROM inventario.inv_producto_medida WHERE (med_empresa = '" + empresa
                + "' AND med_codigo = '" + codigo + "')";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));

    }

    @Override
    public List<InvProductoMedidaTO> getInvProductoMedidaTO(String empresa) throws Exception {
        String sql = "SELECT med_empresa, med_codigo, med_detalle, med_conversion_libras, med_conversion_kilos, "
                + "usr_empresa, usr_codigo, usr_fecha_inserta, med_replicar FROM inventario.inv_producto_medida WHERE (usr_empresa = '"
                + empresa + "') ORDER BY med_detalle";

        return genericSQLDao.obtenerPorSql(sql, InvProductoMedidaTO.class);

    }

    @Override
    public List<InvMedidaComboTO> getListaInvMedidaTablaTO(String empresa) throws Exception {
        String sql = "SELECT med_codigo, med_detalle , med_conversion_libras FROM "
                + "inventario.inv_producto_medida WHERE med_empresa = ('" + empresa + "') ORDER BY med_detalle";
        return genericSQLDao.obtenerPorSql(sql, InvMedidaComboTO.class);
    }

    @Override
    public boolean comprobarEliminarInvProductoMedida(String empresa, String codigo) throws Exception {
        String sql = "SELECT inventario.fun_sw_producto_medida_eliminar(" + "'" + empresa + "', '" + codigo + "')";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

    @Override
    public boolean productoMedidaRepetido(String empresa, String codigo, String detalle) throws Exception {
        String sql = "SELECT * FROM inventario.fun_sw_producto_medida_repetido('" + empresa + "','" + codigo + "', '"
                + detalle + "');";
        return (boolean) genericSQLDao.obtenerObjetoPorSql(sql);
    }

}
