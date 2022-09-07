package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaTransferenciaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciaMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class TransferenciasMotivoDaoImpl extends GenericDaoImpl<InvTransferenciasMotivo, InvTransferenciasMotivoPK>
        implements TransferenciasMotivoDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public Boolean accionInvTransferenciasMotivo(InvTransferenciasMotivo invTransferenciasMotivo, SisSuceso sisSuceso,
            char accion) throws Exception {
        if (accion == 'I') {
            insertar(invTransferenciasMotivo);
        }
        if (accion == 'M') {
            actualizar(invTransferenciasMotivo);
        }
        if (accion == 'E') {
            eliminar(invTransferenciasMotivo);
        }
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public InvTransferenciasMotivo buscarInvTransferenciaMotivo(String empresa, String codigo) throws Exception {
        return obtenerPorId(InvTransferenciasMotivo.class, new InvTransferenciasMotivoPK(empresa, codigo));
    }

    @Override
    public InvTransferenciaMotivoTO getInvTransferenciaMotivoTO(String empresa, String tmCodigo) throws Exception {
        return ConversionesInventario.convertirInvTransferenciaMotivo_InvTransferenciaMotivoTO(
                obtenerPorId(InvTransferenciasMotivo.class, new InvTransferenciasMotivoPK(empresa, tmCodigo)));
    }

    @Override
    public Boolean comprobarInvTransferenciaMotivo(String empresa, String motCodigo) throws Exception {
        String sql = "SELECT COUNT(*) " + "FROM inventario.inv_transferencias_motivo WHERE tm_empresa = " + "('"
                + empresa + "') AND tm_codigo = ('" + motCodigo + "')";
        return ((int) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0)) == 0) ? false : true;
    }

    @Override
    public List<InvListaTransferenciaMotivoTO> getInvListaTransferenciaMotivoTO(String empresa) throws Exception {
        String sql = "SELECT tm_codigo, tm_detalle, tm_inactivo FROM inventario.inv_transferencias_motivo WHERE (tm_empresa = '"
                + empresa + "') ORDER BY tm_codigo";

        return genericSQLDao.obtenerPorSql(sql, InvListaTransferenciaMotivoTO.class);
    }

    @Override
    public List<InvTransferenciaMotivoComboTO> getListaTransferenciaMotivoComboTO(String empresa,
            boolean filtrarInactivos) throws Exception {
        String sql = "";
        if (filtrarInactivos) {
            sql = "SELECT tm_codigo, tm_detalle FROM inventario.inv_transferencias_motivo WHERE tm_empresa = ('"
                    + empresa + "') " + "AND NOT tm_inactivo ORDER BY tm_codigo";
        } else {
            sql = "SELECT tm_codigo, tm_detalle FROM inventario.inv_transferencias_motivo WHERE tm_empresa = ('"
                    + empresa + "') " + "ORDER BY tm_codigo";
        }
        return genericSQLDao.obtenerPorSql(sql, InvTransferenciaMotivoComboTO.class);
    }

    @Override
    public boolean retornoContadoEliminarTransferenciasMotivo(String empresa, String motivo) throws Exception {
        String sql = "SELECT inventario.fun_sw_transferencia_motivo_eliminar('" + empresa + "', '" + motivo + "');";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    /*Lista InvTransferenciaMotivoTO*/
    @Override
    public List<InvTransferenciaMotivoTO> getListaTransferenciaMotivoTO(String empresa, boolean incluirInactivos) throws Exception {
        String sql = "";
        if (!incluirInactivos) {
            sql = "SELECT * FROM inventario.inv_transferencias_motivo WHERE tm_empresa = ('"
                    + empresa + "') " + "AND NOT tm_inactivo ORDER BY tm_codigo";
        } else {
            sql = "SELECT * FROM inventario.inv_transferencias_motivo WHERE tm_empresa = ('"
                    + empresa + "') " + "ORDER BY tm_codigo";
        }
        return genericSQLDao.obtenerPorSql(sql, InvTransferenciaMotivoTO.class);
    }

}
