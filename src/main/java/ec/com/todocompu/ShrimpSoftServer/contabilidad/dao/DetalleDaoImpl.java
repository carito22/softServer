package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContabilizarComprasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class DetalleDaoImpl extends GenericDaoImpl<ConDetalle, Long> implements DetalleDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

	@Autowired
	private SucesoDao sucesoDao;

        @Override
	public boolean modificarConDetalleTO(ConDetalle conDetalle, SisSuceso sisSuceso) throws Exception {
		actualizar(conDetalle);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public int buscarConteoDetalleEliminarCuenta(String empCodigo, String cuentaCodigo) throws Exception {
		String sql = "SELECT COUNT(*) FROM contabilidad.con_detalle WHERE cta_empresa = ('" + empCodigo
				+ "') AND cta_codigo = ('" + cuentaCodigo + "')";

		return (int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
	}

        @Override
	public List<ConDetalleTO> getConDetalleTO(String empresa, String perCodigo, String tipCodigo, String numContable)
			throws Exception {
		String sql = "SELECT con_empresa, con_periodo, con_tipo, con_numero, det_secuencia, "
				+ "cta_codigo, sec_codigo, pis_sector, pis_numero, det_documento, det_debito_credito, "
				+ "det_valor, det_generado, det_referencia, det_observaciones, det_orden, "
				+ "row_number() over (partition by con_empresa order by det_orden) id "
				+ "FROM contabilidad.con_detalle WHERE con_empresa = ('" + empresa + "') AND con_tipo " + "= ('"
				+ tipCodigo + "') AND con_periodo = ('" + perCodigo + "') AND con_numero = ('" + numContable
				+ "') ORDER BY det_orden";

		return genericSQLDao.obtenerPorSql(sql, ConDetalleTO.class);
	}

        @Override
	public List<ConDetalle> getListConDetalle(ConContablePK conContablePK) throws Exception {
		String sql = "SELECT * FROM contabilidad.con_detalle WHERE con_empresa = ('" + conContablePK.getConEmpresa()
				+ "') AND con_tipo = ('" + conContablePK.getConTipo() + "') AND con_periodo = ('"
				+ conContablePK.getConPeriodo() + "') AND con_numero = ('" + conContablePK.getConNumero()
				+ "') ORDER BY det_orden";

		return genericSQLDao.obtenerPorSql(sql, ConDetalle.class);
	}

        @Override
	public List<ConFunContabilizarComprasDetalleTO> getConDetalleEliminarTO(String empresa, String perCodigo,
			String tipCodigo, String numContable) throws Exception {
		String sql = "SELECT * FROM contabilidad.con_detalle WHERE con_empresa = ('" + empresa + "') AND con_tipo "
				+ "= ('" + tipCodigo + "') AND con_periodo = ('" + perCodigo + "') AND " + "con_numero = ('"
				+ numContable + "') ORDER BY det_orden";
		return genericSQLDao.obtenerPorSql(sql, ConFunContabilizarComprasDetalleTO.class);
	}

        @Override
	public List<ConDetalleTablaTO> getListaConDetalleTO(String empresa, String periodo, String tipo, String numero) {
		String sql = "SELECT * FROM contabilidad.fun_listado_contable_detalle('" + empresa + "', '" + periodo + "', '"
				+ tipo + "' ,'" + numero + "')";
		return genericSQLDao.obtenerPorSql(sql, ConDetalleTablaTO.class);
	}

        @Override
	public void eliminarPorSql(Long detSecuencial) {
		genericSQLDao.ejecutarSQL("DELETE FROM contabilidad.con_detalle WHERE det_secuencia=" + detSecuencial);
	}
}
