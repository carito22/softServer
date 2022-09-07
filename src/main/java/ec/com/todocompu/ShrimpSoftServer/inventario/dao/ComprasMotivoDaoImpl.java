package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCompraMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCompraMotivoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class ComprasMotivoDaoImpl extends GenericDaoImpl<InvComprasMotivo, InvComprasMotivoPK>
        implements ComprasMotivoDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public boolean eliminarInvComprasMotivo(InvComprasMotivo invComprasMotivo, SisSuceso sisSuceso) throws Exception {
        eliminar(invComprasMotivo);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public InvComprasMotivo getInvComprasMotivo(String empresa, String codigo) throws Exception {
        return obtenerPorId(InvComprasMotivo.class, new InvComprasMotivoPK(empresa, codigo));
    }

    @Override
    public InvComprasMotivoTO getInvComprasMotivoTO(String empresa, String cmCodigo) throws Exception {
        return ConversionesInventario.convertirInvComprasMotivo_InvComprasMotivoTO(
                obtenerPorId(InvComprasMotivo.class, new InvComprasMotivoPK(empresa, cmCodigo)));
    }

    @Override
    public boolean insertarInvComprasMotivo(InvComprasMotivo invComprasMotivo, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        insertar(invComprasMotivo);
        return true;
    }

    @Override
    public boolean modificarInvComprasMotivo(InvComprasMotivo invComprasMotivo, SisSuceso sisSuceso) throws Exception {

        sucesoDao.insertar(sisSuceso);

        actualizar(invComprasMotivo);
        return true;
    }

    @Override
    public Boolean comprobarInvComprasMotivo(String empresa, String motCodigo) throws Exception {
        String sql = "SELECT COUNT(*) " + "FROM inventario.inv_compras_motivo WHERE cm_empresa = " + "('" + empresa
                + "') AND cm_codigo = ('" + motCodigo + "')";
        Object obj = genericSQLDao.obtenerObjetoPorSql(sql);
        if (obj != null) {
            return ((int) UtilsConversiones.convertir(obj) == 0) ? false : true;
        }
        return false;
    }

    @Override
    public List<InvCompraMotivoComboTO> getListaCompraMotivoComboTO(String empresa, boolean filtrarInactivos)
            throws Exception {
        String sql = "";
        if (filtrarInactivos) {
            sql = "SELECT cm_codigo, cm_detalle, tip_codigo, cm_forma_contabilizar, cm_forma_imprimir FROM inventario.inv_compras_motivo "
                    + "WHERE cm_empresa = ('" + empresa + "') AND NOT cm_inactivo ORDER BY cm_codigo";
        } else {
            sql = "SELECT cm_codigo, cm_detalle, tip_codigo, cm_forma_contabilizar, cm_forma_imprimir  FROM inventario.inv_compras_motivo "
                    + "WHERE cm_empresa = ('" + empresa + "') ORDER BY cm_codigo";
        }
        return genericSQLDao.obtenerPorSql(sql, InvCompraMotivoComboTO.class);
    }

    @Override
    public List<InvCompraMotivoTablaTO> getListaInvComprasMotivoTablaTO(String empresa) throws Exception {
        String sql = "SELECT cm_codigo, cm_detalle, tip_detalle FROM inventario.inv_compras_motivo "
                + "LEFT JOIN contabilidad.con_tipo ON inv_compras_motivo.tip_empresa = con_tipo.tip_empresa "
                + "AND inv_compras_motivo.tip_codigo = con_tipo.tip_codigo WHERE cm_empresa = ('" + empresa
                + "') ORDER BY cm_codigo";

        return genericSQLDao.obtenerPorSql(sql, InvCompraMotivoTablaTO.class);
    }

    @Override
    public List<InvComprasMotivoTO> getListaInvComprasMotivoTO(String empresa, boolean soloActivos) throws Exception {
        String sql = "";
        if (soloActivos) {
            sql = "SELECT cm_empresa,cm_codigo, cm_detalle,cm_forma_contabilizar,cm_forma_imprimir,cm_ajustes_de_inventario,cm_inactivo,tip_codigo,usr_codigo,usr_fecha_inserta, cm_imb, cm_exigir_liquidacion, cm_exigir_imb, cm_exigir_orden_compra FROM inventario.inv_compras_motivo "
                    + "WHERE cm_empresa = ('" + empresa + "') AND NOT cm_inactivo ORDER BY cm_codigo";
        } else {
            sql = "SELECT cm_empresa,cm_codigo, cm_detalle,cm_forma_contabilizar,cm_forma_imprimir,cm_ajustes_de_inventario,cm_inactivo,tip_codigo,usr_codigo,usr_fecha_inserta, cm_imb, cm_exigir_liquidacion, cm_exigir_imb, cm_exigir_orden_compra FROM inventario.inv_compras_motivo "
                    + "WHERE cm_empresa = ('" + empresa + "') ORDER BY cm_codigo";
        }
        return genericSQLDao.obtenerPorSql(sql, InvComprasMotivoTO.class);
    }

    @Override
    public boolean retornoContadoEliminarComprasMotivo(String empresa, String motivo) throws Exception {
        String sql = "SELECT inventario.fun_sw_compra_motivo_eliminar('" + empresa + "', '" + motivo + "');";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public List<InvComprasMotivoTO> listarInvComprasMotivoTOAjusteInv(String empresa, boolean soloActivos) throws Exception {
        String sql = "";
        if (soloActivos) {
            sql = "SELECT cm_empresa,cm_codigo, cm_detalle,cm_forma_contabilizar,cm_forma_imprimir,cm_ajustes_de_inventario,cm_inactivo,tip_codigo,usr_codigo,usr_fecha_inserta, cm_imb, cm_exigir_liquidacion, cm_exigir_imb, cm_exigir_orden_compra FROM inventario.inv_compras_motivo "
                    + "WHERE cm_empresa = ('" + empresa + "') AND NOT cm_inactivo AND cm_ajustes_de_inventario ORDER BY cm_codigo";
        } else {
            sql = "SELECT cm_empresa,cm_codigo, cm_detalle,cm_forma_contabilizar,cm_forma_imprimir,cm_ajustes_de_inventario,cm_inactivo,tip_codigo,usr_codigo,usr_fecha_inserta, cm_imb, cm_exigir_liquidacion, cm_exigir_imb, cm_exigir_orden_compra FROM inventario.inv_compras_motivo "
                    + "WHERE cm_empresa = ('" + empresa + "') AND cm_ajustes_de_inventario ORDER BY cm_codigo";
        }
        return genericSQLDao.obtenerPorSql(sql, InvComprasMotivoTO.class);
    }

}
