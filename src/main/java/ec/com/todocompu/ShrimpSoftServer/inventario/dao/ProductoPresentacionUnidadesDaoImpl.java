package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionUnidadesComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionUnidades;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionUnidadesPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class ProductoPresentacionUnidadesDaoImpl
		extends GenericDaoImpl<InvProductoPresentacionUnidades, InvProductoPresentacionUnidadesPK>
		implements ProductoPresentacionUnidadesDao {

	@Autowired
	private SucesoDao sucesoDao;
	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public Boolean accionInvProductoPresentacionUnidades(
			InvProductoPresentacionUnidades invProductoPresentacionUnidades, SisSuceso sisSuceso, char accion)
					throws Exception {
		if (accion == 'I') {
			insertar(invProductoPresentacionUnidades);
		}
		if (accion == 'M') {
			actualizar(invProductoPresentacionUnidades);
		}
		if (accion == 'E') {
			eliminar(invProductoPresentacionUnidades);
		}
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public InvProductoPresentacionUnidades buscarProductoPresentacionUnidades(String empresa, String codigoPresentacion)
			throws Exception {
		return obtenerPorId(InvProductoPresentacionUnidades.class,
				new InvProductoPresentacionUnidadesPK(empresa, codigoPresentacion));
	}

        @Override
	public boolean comprobarInvProductoPresentacionUnidades(String empresa, String codigo) throws Exception {
		String sql = "SELECT COUNT(*)!=0 FROM inventario.inv_producto_presentacion_unidades "
				+ "WHERE (presu_empresa = '" + empresa + "' AND presu_codigo = '" + codigo + "')";

		return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
	}

        @Override
	public List<InvProductoPresentacionUnidadesComboListadoTO> getListaPresentacionUnidadComboTO(String empresa)
			throws Exception {
		String sql = "select presu_codigo, presu_detalle, presu_abreviado from inventario.inv_producto_presentacion_unidades "
				+ "where presu_empresa = ('" + empresa + "') ORDER BY presu_codigo";

		return genericSQLDao.obtenerPorSql(sql, InvProductoPresentacionUnidadesComboListadoTO.class);
	}

        @Override
	public boolean comprobarEliminarInvProductoPresentacionUnidades(String empresa, String codigo) throws Exception {
		String sql = "SELECT inventario.fun_sw_producto_presentacion_unidades_eliminar(" + "'" + empresa + "', '"
				+ codigo + "')";
		return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
	}

}
