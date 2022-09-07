package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleConsumoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosDetalle;

@Repository
public class ConsumosDetalleDaoImpl extends GenericDaoImpl<InvConsumosDetalle, Integer> implements ConsumosDetalleDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public List<InvListaDetalleConsumoTO> getListaInvConsumoDetalleTO(String empresa, String periodo, String motivo,
			String numeroConsumo) throws Exception {

		String sql = "SELECT inv_consumos_detalle.det_secuencial, inv_consumos_detalle.bod_codigo, "
				+ "inv_consumos_detalle.pro_codigo_principal, inv_producto.pro_nombre, "
				+ "inv_consumos_detalle.det_cantidad, inv_producto_medida.med_detalle, "
				+ "inv_consumos_detalle.sec_codigo, inv_consumos_detalle.pis_numero, det_valor_promedio "
				+ "FROM inventario.inv_consumos_detalle "
				+ "INNER JOIN inventario.inv_producto INNER JOIN inventario.inv_producto_medida "
				+ "ON inv_producto.med_empresa = inv_producto_medida.med_empresa AND "
				+ "inv_producto.med_codigo = inv_producto_medida.med_codigo "
				+ "ON inv_consumos_detalle.pro_empresa = inv_producto.pro_empresa "
				+ "AND inv_consumos_detalle.pro_codigo_principal = inv_producto.pro_codigo_principal "
				+ "WHERE (cons_empresa = '" + empresa + "') AND cons_periodo = ('" + periodo + "') "
				+ "AND cons_motivo = ('" + motivo + "') AND cons_numero = ('" + numeroConsumo + "') ORDER BY det_orden";

		return genericSQLDao.obtenerPorSql(sql, InvListaDetalleConsumoTO.class);

	}

        @Override
	public void eliminarPorSql(Integer detSecuencial) {
		genericSQLDao.ejecutarSQL("DELETE FROM inventario.inv_consumos_detalle WHERE det_secuencial=" + detSecuencial);
	}

}
