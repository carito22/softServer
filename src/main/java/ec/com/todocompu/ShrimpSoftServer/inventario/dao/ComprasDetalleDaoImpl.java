package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalle;

@Repository
public class ComprasDetalleDaoImpl extends GenericDaoImpl<InvComprasDetalle, Integer> implements ComprasDetalleDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<InvListaDetalleComprasTO> getListaInvCompraDetalleTO(String empresa, String periodo, String motivo, String numeroCompra) throws Exception {
        String sql = "SELECT * FROM inventario.fun_compras_detalle('" + empresa + "', '" + periodo + "', '" + motivo + "', '" + numeroCompra + "')";
        return genericSQLDao.obtenerPorSql(sql, InvListaDetalleComprasTO.class);

    }

    @Override
    public List<InvComprasDetalle> obtenerCompraDetallePorNumero(String empresa, String periodo, String motivo, String numero) throws Exception {
        String sql = "SELECT * FROM inventario.inv_compras_detalle WHERE comp_empresa = ('" + empresa + "') AND comp_periodo = ('" + periodo + "') "
                + "AND comp_motivo = ('" + motivo + "') AND comp_numero = ('" + numero + "') " + "Order by det_secuencial ASC";
        return genericSQLDao.obtenerPorSql(sql, InvComprasDetalle.class);
    }

    @Override
    public void actualizarPorSql(Long detSecuencial) throws Exception {
        genericSQLDao.ejecutarSQL("update inventario.inv_compras_detalle set det_secuencial_orden_compra = null  WHERE det_secuencial=" + detSecuencial);
    }

}
