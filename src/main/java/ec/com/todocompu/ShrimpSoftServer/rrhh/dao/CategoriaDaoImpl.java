/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCategoriaProvisionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhCategoria;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhCategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class CategoriaDaoImpl extends GenericDaoImpl<RhCategoria, RhCategoriaPK> implements CategoriaDao {

	@Autowired
	private SucesoDao sucesoDao;
	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public RhCategoria buscarCategoria(String empCodigo, String catNombre) throws Exception {
		return obtenerPorId(RhCategoria.class, new RhCategoriaPK(empCodigo, catNombre));
	}

        @Override
	public Boolean accionRhCategoria(RhCategoria rhCategoria, SisSuceso sisSuceso, char accion) throws Exception {
		if (accion == 'I')
			insertar(rhCategoria);
		if (accion == 'M')
			actualizar(rhCategoria);
		if (accion == 'E')
			eliminar(rhCategoria);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public RhCategoriaTO getCategoria(String empCodigo, String catNombre) throws Exception {
		String sql = "SELECT " + "cat_empresa, cat_nombre, cta_anticipos, cta_prestamos, "
				+ "cta_por_pagar_bonos, cta_por_pagar_sueldo, cta_por_pagar_impuesto_renta, "
				+ "cta_por_pagar_utilidades, cta_saldo_horas_extras_50, cta_saldo_horas_extras_100, cta_saldo_horas_extras_extraordinarias_100, cta_gasto_horas_extras, "
                                + "cta_gasto_horas_extras_100, cta_gasto_horas_extras_extraordinarias_100, cta_gasto_bonos, cta_gasto_bonos_nd, cta_gasto_bono_fijo, "
				+ "cta_gasto_bono_fijo_nd, cta_gasto_otros_ingresos, cta_gasto_otros_ingresos_nd, "
				+ "cta_gasto_sueldos, cta_aportepersonal, cta_aporte_extension, cta_aportepatronal, cta_iece, cta_secap, "
				+ "cta_xiii, cta_xiv, cta_fondoreserva, cta_vacaciones, cta_desahucio, "
				+ "cta_gasto_aporteindividual, cta_gasto_aportepatronal, cta_gasto_iece, "
				+ "cta_gasto_secap, cta_gasto_xiii, cta_gasto_xiv, cta_gasto_fondoreserva, "
				+ "cta_gasto_vacaciones, cta_gasto_salario_digno, cta_gasto_desahucio, "
				+ "cta_gasto_desahucio_intempestivo, cta_gasto_liquidacion_bono, tip_codigo, usr_codigo, usr_fecha_inserta, "
                                + "cta_permiso_medico, cta_prestamo_quirografario, cta_prestamo_hipotecario, cta_descuento_vacaciones, cta_recargo_jornada_nocturna "
				+ "FROM recursoshumanos.rh_categoria WHERE (cat_empresa = '" + empCodigo + "') AND (cat_nombre = '"
				+ catNombre + "')";

		return genericSQLDao.obtenerObjetoPorSql(sql, RhCategoriaTO.class);
	}

        @Override
	public List<RhCategoriaTO> getListaRhCategoriaTO(String empresa) throws Exception {
		String sql = "SELECT " + "cat_empresa, cat_nombre, cta_anticipos, " + "cta_prestamos, "
				+ "cta_por_pagar_bonos, " + "cta_por_pagar_sueldo, " + "cta_por_pagar_impuesto_renta, "
				+ "cta_por_pagar_utilidades, cta_saldo_horas_extras_50, cta_saldo_horas_extras_100, cta_saldo_horas_extras_extraordinarias_100, cta_gasto_horas_extras, "
                                + "cta_gasto_horas_extras_100, cta_gasto_horas_extras_extraordinarias_100, cta_gasto_bonos, " + "cta_gasto_bonos_nd, " + "cta_gasto_bono_fijo, "
				+ "cta_gasto_bono_fijo_nd, " + "cta_gasto_otros_ingresos, " + "cta_gasto_otros_ingresos_nd, "
				+ "cta_gasto_sueldos, cta_aportepersonal, cta_aporte_extension, cta_aportepatronal, " + "cta_iece, " + "cta_secap, "
				+ "cta_xiii, " + "cta_xiv, " + "cta_fondoreserva, " + "cta_vacaciones, " + "cta_desahucio, "
				+ "cta_gasto_aporteindividual, " + "cta_gasto_aportepatronal, " + "cta_gasto_iece, "
				+ "cta_gasto_secap, " + "cta_gasto_xiii, " + "cta_gasto_xiv, " + "cta_gasto_fondoreserva, "
				+ "cta_gasto_vacaciones, " + "cta_gasto_salario_digno, " + "cta_gasto_desahucio, "
				+ "cta_gasto_desahucio_intempestivo, cta_descuento_vacaciones, "
				+ "cta_gasto_liquidacion_bono, tip_codigo, usr_codigo, usr_fecha_inserta, "
                                + "cta_permiso_medico, cta_prestamo_quirografario, cta_prestamo_hipotecario "
				+ "FROM recursoshumanos.rh_categoria " + "WHERE (cat_empresa = '" + empresa + "')";

		List<RhCategoriaTO> lista = genericSQLDao.obtenerPorSql(sql, RhCategoriaTO.class);
		return lista;

	}

        @Override
	public List<RhCategoriaTO> getListaRhCategoriaCuentasTO(String empresa) throws Exception {
		String sql = "SELECT * FROM recursoshumanos.fun_nombres_cuentas_categoria('" + empresa + "');";
		List<RhCategoriaTO> lista = genericSQLDao.obtenerPorSql(sql, RhCategoriaTO.class);
		return lista;

	}

        @Override
	public List<RhComboCategoriaTO> getComboCategoria(String empresa) throws Exception {
		String sql = "SELECT cat_nombre " + "FROM recursoshumanos.rh_categoria " + "WHERE (cat_empresa = '" + empresa
				+ "') ORDER BY cat_nombre";

		return genericSQLDao.obtenerPorSql(sql, RhComboCategoriaTO.class);
	}

        @Override
	public String buscarCtaRhXiiiCategoria(String empresa, String categoria) throws Exception {
		String sql = "SELECT rh_categoria.cta_xiii " + "FROM recursoshumanos.rh_categoria "
				+ "WHERE (rh_categoria.cat_empresa = '" + empresa + "') " + "AND (rh_categoria.cat_nombre = '"
				+ categoria.trim() + "')";
		return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
	}

        @Override
	public String buscarCtaRhXivCategoria(String empresa, String categoria) throws Exception {
		String sql = "SELECT rh_categoria.cta_xiv " + "FROM recursoshumanos.rh_categoria "
				+ "WHERE (rh_categoria.cat_empresa = '" + empresa + "') " + "AND (rh_categoria.cat_nombre = '"
				+ categoria.trim() + "')";
		return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
	}

        @Override
	public RhCategoriaProvisionesTO getRhCategoriaProvisionesTO(String empresa, String categoria) throws Exception {
		String sql = "SELECT cta_aportepatronal, cta_iece, cta_secap, "
				+ "cta_xiii, cta_xiv, cta_fondoreserva, cta_vacaciones, cta_desahucio, "
				+ "cta_gasto_aportepatronal, cta_gasto_iece, cta_gasto_secap, "
				+ "cta_gasto_xiii, cta_gasto_xiv, cta_gasto_fondoreserva, "
				+ "cta_gasto_vacaciones, cta_gasto_desahucio, cat_nombre " + "FROM recursoshumanos.rh_categoria "
				+ "WHERE (cat_empresa = '" + empresa + "') AND " + "(cat_nombre = '" + categoria + "')";

		return genericSQLDao.obtenerObjetoPorSql(sql, RhCategoriaProvisionesTO.class);
	}

}
