package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasFlujoDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasFlujoDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConCuentasFlujoDetallePK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class CuentasFlujoDetalleDaoImpl extends GenericDaoImpl<ConCuentasFlujoDetalle, ConCuentasFlujoDetallePK>
		implements CuentasFlujoDetalleDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

	@Autowired
	private SucesoDao sucesoDao;

        @Override
	public boolean insertarConCuentaFlujoDetalle(ConCuentasFlujoDetalle conCuentasFlujoDetalle, SisSuceso sisSuceso)
			throws Exception {
		insertar(conCuentasFlujoDetalle);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean modificarConCuentaFlujoDetalle(ConCuentasFlujoDetalle conCuentasFlujoDetalle, SisSuceso sisSuceso)
			throws Exception {
		actualizar(conCuentasFlujoDetalle);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean modificarConCuentaLlavePrincipalFlujoDetalle(ConCuentasFlujoDetalle conCuentasFlujoEliminarDetalle,
			ConCuentasFlujoDetalle conFlujoCuentasDetalle, SisSuceso sisSuceso) throws Exception {
		eliminar(conCuentasFlujoEliminarDetalle);
		insertar(conFlujoCuentasDetalle);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean eliminarConCuentaFlujoDetalle(ConCuentasFlujoDetalle conCuentasFlujoDetalle, SisSuceso sisSuceso)
			throws Exception {
		eliminar(conCuentasFlujoDetalle);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public List<ConCuentasFlujoDetalleTO> getListaBuscarConCuentasFlujoDetalleTO(String empresa, String buscar)
			throws Exception {
		String sql = "SELECT * FROM contabilidad.con_cuentas_flujo_detalle WHERE det_empresa = ('" + empresa
				+ "') AND (det_cuenta_contable || UPPER(det_cuenta_flujo) LIKE TRANSLATE(' ' || CASE WHEN ('" + buscar
				+ "') = '' THEN '~' ELSE ('" + buscar + "') END || ' ', ' ', '%'))";

		return genericSQLDao.obtenerPorSql(sql, ConCuentasFlujoDetalleTO.class);
	}

        @Override
	public List<ConCuentasFlujoDetalleTO> getListaConCuentasFlujoDetalleTO(String empresa) throws Exception {
		String sql = "SELECT det_empresa, det_cuenta_contable, det_debito_credito, det_cuenta_flujo, "
				+ " con_cuentas_flujo_detalle.flu_empresa, con_cuentas_flujo_detalle.flu_codigo,"
				+ " con_cuentas_flujo_detalle.cta_empresa, con_cuentas_flujo_detalle.cta_codigo,"
				+ " con_cuentas_flujo_detalle.usr_empresa, con_cuentas_flujo_detalle.usr_codigo, con_cuentas_flujo_detalle.usr_fecha_inserta,"
				+ " con_cuentas.cta_detalle, con_cuentas_flujo.flu_detalle"
				+ " FROM contabilidad.con_cuentas_flujo_detalle INNER JOIN contabilidad.con_cuentas"
				+ " ON con_cuentas_flujo_detalle.cta_empresa = con_cuentas.cta_empresa AND"
				+ "   con_cuentas_flujo_detalle.cta_codigo = con_cuentas.cta_codigo"
				+ " INNER JOIN contabilidad.con_cuentas_flujo"
				+ " ON con_cuentas_flujo_detalle.flu_empresa = con_cuentas_flujo.flu_empresa AND"
				+ " con_cuentas_flujo_detalle.flu_codigo = con_cuentas_flujo.flu_codigo WHERE det_empresa = ('"
				+ empresa + "')";

		return genericSQLDao.obtenerPorSql(sql, ConCuentasFlujoDetalleTO.class);
	}
}
