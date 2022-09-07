package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraDetalle;

@Repository
public class CompraDetalleDaoImpl extends GenericDaoImpl<AnxCompraDetalle, Integer> implements CompraDetalleDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

	@Override
	public List<AnxCompraDetalleTO> getAnexoCompraDetalleTO(String empresa, String periodo, String motivo,
			String numeroCompra) throws Exception {
		String sql = "SELECT det_secuencial, det_concepto, det_base0, det_baseimponible, "
				+ "det_basenoobjetoiva, det_porcentaje, det_valorretenido, det_orden, "
				+ "anx_compra_detalle.comp_empresa, anx_compra_detalle.comp_periodo, "
				+ "anx_compra_detalle.comp_motivo, anx_compra_detalle.comp_numero, div_secuencial, "
				+ "div_fecha_pago, div_ir_asociado, div_anio_utilidades "
				+ "FROM anexo.anx_compra_detalle LEFT JOIN anexo.anx_compra_dividendo "
				+ "ON anx_compra_detalle.comp_empresa = anx_compra_dividendo.comp_empresa AND "
				+ "anx_compra_detalle.comp_periodo = anx_compra_dividendo.comp_periodo AND "
				+ "anx_compra_detalle.comp_motivo = anx_compra_dividendo.comp_motivo AND "
				+ "anx_compra_detalle.comp_numero = anx_compra_dividendo.comp_numero AND "
				+ "anx_compra_detalle.det_concepto = anx_compra_dividendo.con_codigo "
				+ "WHERE anx_compra_detalle.comp_empresa='" + empresa + "' AND " + "anx_compra_detalle.comp_periodo='"
				+ periodo + "' AND " + "anx_compra_detalle.comp_motivo='" + motivo + "' AND "
				+ "anx_compra_detalle.comp_numero='" + numeroCompra + "' " + "ORDER BY det_orden";
		return genericSQLDao.obtenerPorSql(sql, AnxCompraDetalleTO.class);
	}

}
