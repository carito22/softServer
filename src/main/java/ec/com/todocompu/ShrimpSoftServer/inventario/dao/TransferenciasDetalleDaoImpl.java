package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleTransferenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasDetalle;

@Repository
public class TransferenciasDetalleDaoImpl extends GenericDaoImpl<InvTransferenciasDetalle, Integer>
        implements TransferenciasDetalleDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<InvListaDetalleTransferenciaTO> getListaInvTransferenciasDetalleTO(String empresa, String periodo,
            String motivo, String numeroTrans) throws Exception {
        String sql = "SELECT * FROM inventario.fun_listado_transferencias_detalle('" + empresa + "', '" + periodo
                + "', " + "'" + motivo + "', '" + numeroTrans + "')";
        return genericSQLDao.obtenerPorSql(sql, InvListaDetalleTransferenciaTO.class);
    }

    @Override
    public List<InvTransferenciasDetalle> obtenerTransferenciaDetallePorNumero(String empresa, String periodo, String motivo, String numero) throws Exception {
        String sql = "SELECT * FROM inventario.inv_transferencias_detalle WHERE trans_empresa = ('" + empresa + "') AND trans_periodo = ('" + periodo + "') "
                + "AND trans_motivo = ('" + motivo + "') AND trans_numero = ('" + numero + "') " + "Order by det_secuencial ASC";
        return genericSQLDao.obtenerPorSql(sql, InvTransferenciasDetalle.class);
    }

}
