package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCategoriaClienteComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteCategoria;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteCategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class ClienteCategoriaDaoImpl extends GenericDaoImpl<InvClienteCategoria, InvClienteCategoriaPK>
		implements ClienteCategoriaDao {

	@Autowired
	private SucesoDao sucesoDao;
	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public Boolean accionInvClienteCategoria(InvClienteCategoria invClienteCategoria, SisSuceso sisSuceso, char accion)
			throws Exception {
		if (accion == 'I') {
			insertar(invClienteCategoria);
		}
		if (accion == 'M') {
			actualizar(invClienteCategoria);
		}
		if (accion == 'E') {
			eliminar(invClienteCategoria);
		}
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public InvClienteCategoria buscarInvClienteCategoria(String empresa, String codigo) throws Exception {
		return obtenerPorId(InvClienteCategoria.class, new InvClienteCategoriaPK(empresa, codigo));
	}

        @Override
	public boolean comprobarInvClienteCategoria(String empresa, String codigo) throws Exception {
		String sql = "SELECT COUNT(*)!=0 " + "FROM inventario.inv_cliente_categoria " + "WHERE (cc_empresa = '"
				+ empresa + "' AND cc_codigo = '" + codigo + "');";
		return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
	}

        @Override
	public List<InvClienteCategoriaTO> getInvClienteCategoriaTO(String empresa) throws Exception {
		String sql = "SELECT cc_empresa, cc_codigo, cc_detalle, cc_retiene ,'1' as cc_nivel, usr_empresa, usr_codigo, usr_fecha_inserta "
				+ "FROM inventario.inv_cliente_categoria " + "WHERE (cc_empresa = '" + empresa
				+ "') ORDER BY cc_codigo;";
		return genericSQLDao.obtenerPorSql(sql, InvClienteCategoriaTO.class);
	}

        @Override
	public List<InvCategoriaClienteComboTO> getListaCategoriaClienteComboTO(String empresa) throws Exception {
		String sql = "SELECT cc_codigo, cc_detalle FROM inventario.inv_cliente_categoria WHERE (cc_empresa = '"
				+ empresa + "') ORDER BY cc_codigo";

		return genericSQLDao.obtenerPorSql(sql, InvCategoriaClienteComboTO.class);
	}

        @Override
	public boolean comprobarEliminarInvClienteCategoria(String empresa, String codigo) throws Exception {
		String sql = "SELECT inventario.fun_sw_cliente_categoria_eliminar(" + "'" + empresa + "', '" + codigo + "');";
		return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
	}

}
