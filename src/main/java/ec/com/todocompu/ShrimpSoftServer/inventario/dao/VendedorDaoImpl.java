package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVendedorComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVendedor;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVendedorPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class VendedorDaoImpl extends GenericDaoImpl<InvVendedor, InvVendedorPK> implements VendedorDao {

	@Autowired
	private SucesoDao sucesoDao;
	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public Boolean accionInvVendedor(InvVendedor invVendedor, SisSuceso sisSuceso, char accion) throws Exception {
		if (accion == 'I') {
			insertar(invVendedor);
		}
		if (accion == 'M') {
			actualizar(invVendedor);
		}
		if (accion == 'E') {
			eliminar(invVendedor);
		}
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public InvVendedor buscarInvVendedor(String empresa, String codigo) throws Exception {
		return obtenerPorId(InvVendedor.class, new InvVendedorPK(empresa, codigo));
	}

        @Override
	public boolean comprobarInvVendedor(String empresa, String codigo) throws Exception {

		String sql = "SELECT COUNT(*)!=0 FROM inventario.inv_vendedor " + "WHERE (vend_empresa = '" + empresa
				+ "' AND vend_codigo = '" + codigo + "')";

		return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
	}

        @Override
	public List<InvVendedorComboListadoTO> getComboinvListaVendedorTOs(String empresa,String busqueda) throws Exception {
             String sqlBusqueda = "";
            if (busqueda != null && !"".equals(busqueda)) {
                sqlBusqueda = " AND (vend_codigo  || vend_nombre"
                        + " LIKE TRANSLATE(' ' || CASE WHEN ('" + busqueda + "') = ''  THEN '~' ELSE ('" + busqueda
                        + "') END || ' ', ' ', '%')) ";
            }
		String sql = "select * from inventario.inv_vendedor where vend_empresa = '" + empresa + "'"
                        + sqlBusqueda
                        + " ORDER BY inventario.inv_vendedor.vend_nombre";

		return genericSQLDao.obtenerPorSql(sql, InvVendedorComboListadoTO.class);
	}

        @Override
	public boolean comprobarEliminarInvVendedor(String empresa, String codigo) throws Exception {
		String sql = "SELECT inventario.fun_sw_vendedor_eliminar(" + "'" + empresa + "', '" + codigo + "')";

		return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
	}

}
