package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionCajasComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionCajas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionCajasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class ProductoPresentacionCajasDaoImpl
		extends GenericDaoImpl<InvProductoPresentacionCajas, InvProductoPresentacionCajasPK>
		implements ProductoPresentacionCajasDao {

	@Autowired
	private SucesoDao sucesoDao;
	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public Boolean accionInvProductoPresentacionCajas(InvProductoPresentacionCajas invProductoPresentacionCajas,
			SisSuceso sisSuceso, char accion) throws Exception {
		if (accion == 'I') {
			insertar(invProductoPresentacionCajas);
		}
		if (accion == 'M') {
			actualizar(invProductoPresentacionCajas);
		}
		if (accion == 'E') {
			eliminar(invProductoPresentacionCajas);
		}
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public InvProductoPresentacionCajas buscarProductoPresentacionCajas(String empresa, String codigoPresentacion)
			throws Exception {
		return obtenerPorId(InvProductoPresentacionCajas.class,
				new InvProductoPresentacionCajasPK(empresa, codigoPresentacion));
	}

        @Override
	public boolean comprobarInvProductoPresentacionCajas(String empresa, String codigo) throws Exception {
		String sql = "SELECT COUNT(*)!=0 FROM inventario.inv_producto_presentacion_cajas " + "WHERE (presc_empresa = '"
				+ empresa + "' AND presc_codigo = '" + codigo + "')";
		return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
	}

        @Override
	public List<InvProductoPresentacionCajasComboListadoTO> getListaPresentacionCajaComboTO(String empresa)
			throws Exception {
		String sql = "select presc_codigo, presc_detalle, presc_abreviado from inventario.inv_producto_presentacion_cajas where presc_empresa= ('"
				+ empresa + "') ORDER BY presc_codigo";

		return genericSQLDao.obtenerPorSql(sql, InvProductoPresentacionCajasComboListadoTO.class);
	}

        @Override
	public boolean comprobarEliminarInvProductoPresentacionCajas(String empresa, String codigo) throws Exception {
		String sql = "SELECT inventario.fun_sw_producto_presentacion_cajas_eliminar(" + "'" + empresa + "', '" + codigo
				+ "')";

		return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
	}

}
