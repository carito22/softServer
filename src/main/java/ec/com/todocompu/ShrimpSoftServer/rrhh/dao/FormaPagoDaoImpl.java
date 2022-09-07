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
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCtaFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhFormaPago;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class FormaPagoDaoImpl extends GenericDaoImpl<RhFormaPago, Integer> implements FormaPagoDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

	@Autowired
	private SucesoDao sucesoDao;

        @Override
	public Boolean accionRhFormaPago(RhFormaPago rhFormaPago, SisSuceso sisSuceso, char accion) throws Exception {
		if (accion == 'I')
			insertar(rhFormaPago);
		if (accion == 'M')
			actualizar(rhFormaPago);
		if (accion == 'E')
			eliminar(rhFormaPago);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public Boolean buscarRhFormaPago(String ctaContable, String empresa) throws Exception {
		String sql = "SELECT COUNT(*) FROM recursoshumanos.rh_forma_pago WHERE (cta_codigo = '" + ctaContable
				+ "') AND (cta_empresa = '" + empresa + "')";

		return ((int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql))) == 1 ? true : false;
	}

        @Override
	public List<RhListaFormaPagoTO> getListaFormaPago(String empresa) throws Exception {
		String sql = "SELECT rh_forma_pago.cta_codigo, fp_secuencial, upper(cta_detalle) fp_detalle, sec_codigo, fp_inactivo \n" +
                                    "FROM recursoshumanos.rh_forma_pago INNER JOIN contabilidad.con_cuentas\n" +
                                    "  ON rh_forma_pago.cta_empresa = con_cuentas.cta_empresa AND\n" +
                                    "     rh_forma_pago.cta_codigo = con_cuentas.cta_codigo\n" +
                                    "WHERE rh_forma_pago.cta_empresa = ('" + empresa + "') " +
                                    "ORDER BY 3";
		return genericSQLDao.obtenerPorSql(sql, RhListaFormaPagoTO.class);
	}

        @Override
	public List<RhComboFormaPagoTO> getComboFormaPago(String empresa) throws Exception {
		String sql = "SELECT sec_codigo || ' | ' || cta_detalle fp_detalle, rh_forma_pago.cta_codigo, ban_cuenta.cta_empresa IS NOT NULL validar "
				+ "FROM recursoshumanos.rh_forma_pago INNER JOIN contabilidad.con_cuentas "
				+ "ON rh_forma_pago.cta_empresa = con_cuentas.cta_empresa AND "
				+ "rh_forma_pago.cta_codigo = con_cuentas.cta_codigo LEFT JOIN banco.ban_cuenta "
				+ "ON rh_forma_pago.cta_empresa = ban_cuenta.cta_empresa AND "
				+ "rh_forma_pago.cta_codigo = ban_cuenta.cta_cuenta_contable WHERE rh_forma_pago.cta_empresa = '"
				+ empresa + "' AND NOT fp_inactivo ORDER BY sec_codigo, fp_detalle";

		return genericSQLDao.obtenerPorSql(sql, RhComboFormaPagoTO.class);
	}

        @Override
	public RhCtaFormaPagoTO buscarCtaRhFormaPago(String empCodigo, String fpDetalle) throws Exception {
		String sql = "SELECT cta_codigo, sec_codigo FROM recursoshumanos.rh_forma_pago WHERE (cta_codigo = '"
				+ fpDetalle + "') AND (cta_empresa = '" + empCodigo + "') AND (NOT fp_inactivo)";

		return genericSQLDao.obtenerObjetoPorSql(sql, RhCtaFormaPagoTO.class);
	}

	@Override
	public RhFormaPago buscarFormaPago(Integer fpSecuencia) throws Exception {
		return obtenerPorId(RhFormaPago.class, fpSecuencia);
	}

}
