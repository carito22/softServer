package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsumosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class ConsumosMotivoDaoImpl extends GenericDaoImpl<InvConsumosMotivo, InvConsumosMotivoPK>
        implements ConsumosMotivoDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public Boolean accionInvConsumosMotivo(InvConsumosMotivo invConsumosMotivo, SisSuceso sisSuceso, char accion)
            throws Exception {
        if (accion == 'I') {
            insertar(invConsumosMotivo);
        }
        if (accion == 'M') {

            actualizar(invConsumosMotivo);
        }
        if (accion == 'E') {
            eliminar(invConsumosMotivo);
        }
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public InvConsumosMotivo buscarInvConsumosMotivo(String empresa, String codigo) throws Exception {
        return obtenerPorId(InvConsumosMotivo.class, new InvConsumosMotivoPK(empresa, codigo));
    }

    @Override
    public InvConsumosMotivoTO getInvConsumoMotivoTO(String empresa, String cmCodigo) throws Exception {
        return ConversionesInventario.convertirInvConsumosMotivo_InvConsumosMotivoTO(
                obtenerPorId(InvConsumosMotivo.class, new InvConsumosMotivoPK(empresa, cmCodigo)));
    }

    @Override
    public Boolean comprobarInvConsumosMotivo(String empresa, String motCodigo) throws Exception {
        String sql = "SELECT COUNT(*) " + "FROM inventario.inv_consumos_motivo WHERE cm_empresa = " + "('" + empresa
                + "') AND cm_codigo = ('" + motCodigo + "')";
        return ((int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql)) == 0) ? false : true;
    }

    @Override
    public List<InvListaConsumosMotivoTO> getInvListaConsumosMotivoTO(String empresa) throws Exception {
        String sql = "SELECT cm_codigo, cm_detalle, cm_inactivo, cm_forma_contabilizar "
                + "FROM inventario.inv_consumos_motivo " + "WHERE (cm_empresa = '" + empresa + "') ORDER BY cm_codigo";

        return genericSQLDao.obtenerPorSql(sql, InvListaConsumosMotivoTO.class);
    }

    @Override
    public List<InvConsumosMotivoComboTO> getListaConsumosMotivoComboTO(String empresa, boolean filtrarInactivos)
            throws Exception {
        String sql = "";
        if (filtrarInactivos) {
            sql = "SELECT cm_codigo, cm_detalle FROM inventario.inv_consumos_motivo " + "WHERE cm_empresa = ('"
                    + empresa + "') AND NOT cm_inactivo ORDER BY cm_codigo";
        } else {
            sql = "SELECT cm_codigo, cm_detalle FROM inventario.inv_consumos_motivo " + "WHERE cm_empresa = ('"
                    + empresa + "') ORDER BY cm_codigo";
        }
        return genericSQLDao.obtenerPorSql(sql, InvConsumosMotivoComboTO.class);
    }

    @Override
    public List<InvConsumosMotivo> getListaConsumosMotivo(String empresa, boolean filtrarInactivos) throws Exception {
        return obtenerPorSql(
                "SELECT cm.* FROM inventario.inv_consumos_motivo cm WHERE cm.cm_empresa = ('" + empresa + "') "
                + (filtrarInactivos ? "AND NOT cm.cm_inactivo " : "") + "ORDER BY cm.cm_codigo",
                InvConsumosMotivo.class);
    }

    @Override
    public boolean retornoContadoEliminarConsumosMotivo(String empresa, String motivo) throws Exception {
        String sql = "SELECT inventario.fun_sw_consumo_motivo_eliminar('" + empresa + "', '" + motivo + "');";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    /*Lista InvConsumosMotivoTO*/
    @Override
    public List<InvConsumosMotivoTO> getInvListaConsumoMotivoTO(String empresa, boolean filtrarInactivos, String busqueda) throws Exception {
        String sqlString = "";
        if (busqueda != null) {
            sqlString += " AND " + "CASE WHEN ('" + busqueda + "') = '' THEN TRUE "
                    + "ELSE (cm_codigo || UPPER(cm_detalle) LIKE '%' || TRANSLATE('" + busqueda
                    + "', ' ', '%') || '%') END";
        }
        String sql = "SELECT * FROM inventario.inv_consumos_motivo WHERE cm_empresa = ('" + empresa + "')" + (filtrarInactivos ? "" : "AND NOT cm_inactivo ") + sqlString + " ORDER BY cm_codigo;";

        return genericSQLDao.obtenerPorSql(sql, InvConsumosMotivoTO.class);
    }

}
