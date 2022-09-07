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
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCtaFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhFormaPagoBeneficiosSociales;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class FormaPagoBeneficiosSocialesDaoImpl extends GenericDaoImpl<RhFormaPagoBeneficiosSociales, Integer>
		implements FormaPagoBeneficiosSocialesDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

	@Autowired
	private SucesoDao sucesoDao;

        @Override
	public RhFormaPagoBeneficiosSociales buscarFormaPagoBeneficioSocial(Integer fpSecuencia) throws Exception {
		return obtenerPorId(RhFormaPagoBeneficiosSociales.class, fpSecuencia);
	}

        @Override
	public Boolean accionRhFormaPagoBeneficioSocial(RhFormaPagoBeneficiosSociales rhFormaPagoBeneficioSocial,
			SisSuceso sisSuceso, char accion) throws Exception {
		if (accion == 'I')
			insertar(rhFormaPagoBeneficioSocial);
		if (accion == 'M')
			actualizar(rhFormaPagoBeneficioSocial);
		if (accion == 'E')
			eliminar(rhFormaPagoBeneficioSocial);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public Boolean buscarRhFormaPagoBeneficioSocial(String ctaContable, String empresa) throws Exception {
		String sql = "SELECT COUNT(*) FROM recursoshumanos.rh_forma_pago_beneficios_sociales " + "WHERE (cta_codigo = '"
				+ ctaContable + "') AND (cta_empresa = '" + empresa + "')";

		return ((int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql))) == 1 ? true : false;

	}

        @Override
	public List<RhListaFormaPagoBeneficioSocialTO> getListaFormaPagoBeneficioSocial(String empresa) throws Exception {
		String sql = "SELECT rh_forma_pago_beneficios_sociales.cta_codigo, fp_secuencial, UPPER(cta_detalle) fp_detalle, "
				+ "fp_codigo_ministerial, sec_codigo, fp_inactivo "
				+ "FROM recursoshumanos.rh_forma_pago_beneficios_sociales INNER JOIN contabilidad.con_cuentas "
                                + "ON rh_forma_pago_beneficios_sociales.cta_empresa = con_cuentas.cta_empresa AND "
                                + "rh_forma_pago_beneficios_sociales.cta_codigo = con_cuentas.cta_codigo "
                                + "WHERE (rh_forma_pago_beneficios_sociales.cta_empresa = '" + empresa + "')"
                                + "ORDER BY 3";

		return genericSQLDao.obtenerPorSql(sql, RhListaFormaPagoBeneficioSocialTO.class);
	}

        @Override
	public List<RhComboFormaPagoBeneficioSocialTO> getComboFormaPagoBeneficioSocial(String empresa) throws Exception {
		String sql = "SELECT UPPER(cta_detalle) fp_detalle, rh_forma_pago_beneficios_sociales.cta_codigo, sec_codigo, "
                                + "fp_codigo_ministerial, ban_cuenta.cta_empresa IS NOT NULL validar  "
                            + "FROM recursoshumanos.rh_forma_pago_beneficios_sociales INNER JOIN contabilidad.con_cuentas "
                                + "ON rh_forma_pago_beneficios_sociales.cta_empresa = con_cuentas.cta_empresa AND "
                                    + "rh_forma_pago_beneficios_sociales.cta_codigo = con_cuentas.cta_codigo "
                                + "LEFT JOIN banco.ban_cuenta "
				+ "ON rh_forma_pago_beneficios_sociales.cta_empresa = ban_cuenta.cta_empresa AND "
				+ "rh_forma_pago_beneficios_sociales.cta_codigo = ban_cuenta.cta_cuenta_contable "
                                + "WHERE rh_forma_pago_beneficios_sociales.cta_empresa = '" + empresa + "' AND NOT fp_inactivo "
                                + "ORDER BY 1";
		return genericSQLDao.obtenerPorSql(sql, RhComboFormaPagoBeneficioSocialTO.class);
	}

        @Override
	public RhCtaFormaPagoBeneficioSocialTO buscarCtaRhFormaPagoBeneficioSocial(String empCodigo, String fpDetalle)
			throws Exception {
		String sql = "SELECT cta_codigo, sec_codigo, fp_codigo_ministerial "
				+ "FROM recursoshumanos.rh_forma_pago_beneficios_sociales WHERE (fp_detalle = '" + fpDetalle
				+ "') AND (cta_empresa = '" + empCodigo.trim() + "') AND (NOT fp_inactivo)";

		return genericSQLDao.obtenerObjetoPorSql(sql, RhCtaFormaPagoBeneficioSocialTO.class);
	}
}
