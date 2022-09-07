package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class VentasMotivoDaoImpl extends GenericDaoImpl<InvVentasMotivo, InvVentasMotivoPK> implements VentasMotivoDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public boolean insertarInvVentasMotivo(InvVentasMotivo invVentasMotivo, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        insertar(invVentasMotivo);
        return true;
    }

    @Override
    public boolean modificarInvVentasMotivo(InvVentasMotivo invVentasMotivo, SisSuceso sisSuceso) throws Exception {

        actualizar(invVentasMotivo);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarInvVentasMotivo(InvVentasMotivo invVentasMotivo, SisSuceso sisSuceso) throws Exception {
        eliminar(invVentasMotivo);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public InvVentasMotivo getInvVentasMotivo(String empresa, String codigo) throws Exception {
        return obtenerPorId(InvVentasMotivo.class, new InvVentasMotivoPK(empresa, codigo));
    }

    @Override
    public InvVentaMotivoTO getInvVentasMotivoTO(String empresa, String vmCodigo) throws Exception {
        return ConversionesInventario.convertirInvVentasMotivo_InvVentasMotivoTO(
                obtenerPorId(InvVentasMotivo.class, new InvVentasMotivoPK(empresa, vmCodigo)));
    }

    @Override
    public Boolean comprobarInvVentasMotivo(String empresa, String motCodigo) throws Exception {
        String sql = "SELECT COUNT(*) FROM inventario.inv_ventas_motivo " + "WHERE vm_empresa = ('" + empresa
                + "') AND vm_codigo = ('" + motCodigo + "')";

        return ((int) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0)) == 0) ? false : true;
    }

    @Override
    public List<InvVentaMotivoTablaTO> getListaInvVentasMotivoTablaTO(String empresa) throws Exception {
        String sql = "SELECT vm_codigo, vm_detalle, tip_detalle FROM inventario.inv_ventas_motivo "
                + "INNER JOIN contabilidad.con_tipo ON inv_ventas_motivo.tip_empresa = con_tipo.tip_empresa "
                + "AND inv_ventas_motivo.tip_codigo = con_tipo.tip_codigo " + "WHERE vm_empresa = ('" + empresa
                + "') ORDER BY vm_codigo";
        return genericSQLDao.obtenerPorSql(sql, InvVentaMotivoTablaTO.class);
    }

    @Override
    public List<InvVentaMotivoComboTO> getListaVentaMotivoComboTO(String empresa, boolean filtrarInactivos)
            throws Exception {
        String sql = "";
        if (filtrarInactivos) {
            sql = "SELECT vm_codigo, vm_detalle, tip_codigo, vm_forma_contabilizar, vm_forma_imprimir FROM inventario.inv_ventas_motivo "
                    + "WHERE vm_empresa = ('" + empresa + "') AND NOT vm_inactivo ORDER BY vm_codigo";
        } else {
            sql = "SELECT vm_codigo, vm_detalle, tip_codigo, vm_forma_contabilizar, vm_forma_imprimir FROM inventario.inv_ventas_motivo "
                    + "WHERE vm_empresa = ('" + empresa + "') ORDER BY vm_codigo";
        }
        return genericSQLDao.obtenerPorSql(sql, InvVentaMotivoComboTO.class);
    }

    @Override
    public List<InvVentaMotivoTO> getListaInvVentasMotivoTO(String empresa, boolean soloActivos, String tipoDocumento) throws Exception {
        String sql = "";
        if (soloActivos) {
            sql = "SELECT * FROM inventario.inv_ventas_motivo "
                    + "WHERE vm_empresa = ('" + empresa + "') AND NOT vm_inactivo ";
        } else {
            sql = "SELECT * FROM inventario.inv_ventas_motivo "
                    + "WHERE vm_empresa = ('" + empresa + "') ";
        }
        if (tipoDocumento != null && !tipoDocumento.equals("")) {
            sql = sql + " and (tc_codigo = '" + tipoDocumento + "' or tc_codigo is null or tc_codigo = '') ";
        }
        sql = sql + " ORDER BY vm_codigo";
        return genericSQLDao.obtenerPorSql(sql, InvVentaMotivoTO.class);
    }

    @Override
    public boolean retornoContadoEliminarVentasMotivo(String empresa, String motivo) throws Exception {
        String sql = "SELECT inventario.fun_sw_venta_motivo_eliminar('" + empresa + "', '" + motivo + "');";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

}
