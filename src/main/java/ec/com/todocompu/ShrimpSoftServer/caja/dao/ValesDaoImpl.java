package ec.com.todocompu.ShrimpSoftServer.caja.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaValesTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCuadreCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajaValesTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajVales;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajValesNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajValesPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class ValesDaoImpl extends GenericDaoImpl<CajVales, CajValesPK> implements ValesDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

	@Autowired
	private SucesoDao sisSucesoDao;

	@Autowired
	private ValesNumeracionDao valesNumeracionDao;

        @Override
	public List<CajaValesTO> getListadoCajValesTO(String empresa, String motCadigo, String fechaDesde,
			String fechaHasta) throws Exception {
		empresa = empresa == null ? empresa : "'" + empresa + "'";
		motCadigo = motCadigo == null ? motCadigo : "'" + motCadigo + "'";
		fechaDesde = fechaDesde == null ? null : fechaDesde.isEmpty() ? null : "'" + fechaDesde + "'";
		fechaHasta = fechaHasta == null ? null : fechaHasta.isEmpty() ? null : "'" + fechaHasta + "'";
		return genericSQLDao.obtenerPorSql("SELECT vale_periodo, vale_motivo, vale_numero, vale_fecha, "
				+ "vale_valor, vale_concepto, vale_beneficiario, vale_observaciones, vae_usuario, "
				+ "usr_fecha_inserta, vale_anulado, id from caja.fun_vales_listado(" + empresa + "," + motCadigo + ","
				+ fechaDesde + "," + fechaHasta + ");", CajaValesTO.class);
	}

        @Override
	public CajCajaValesTO getCajCajaValesTO(String empresa, String perCodigo, String motCodigo, String valeNumero)
			throws Exception {
		String sql = "SELECT vale_empresa, vale_periodo, vale_motivo, vale_numero, vale_fecha, "
				+ "vale_valor, null as vale_concepto, vale_beneficiario, vale_observaciones, vale_anulado,  usr_empresa, "
				+ "usr_codigo as vae_usuario, usr_fecha_inserta, conc_empresa, conc_codigo, 1 as id  "
				+ "FROM caja.caj_vales  where vale_empresa = '" + empresa + "' and " + "vale_periodo = '" + perCodigo
				+ "' and vale_motivo= '" + motCodigo + "' and " + "vale_numero = '" + valeNumero+"';";
		return genericSQLDao.obtenerObjetoPorSql(sql, CajCajaValesTO.class);
	}

        @Override
	public List<CajCuadreCajaTO> getCajCuadreCajaTOs(String empresa, String codigoMotivo, String fechaDesde,
			String fechaHasta) throws Exception {

		fechaDesde = fechaDesde == null ? null : fechaDesde.isEmpty() ? null : "'" + fechaDesde + "'";
		fechaHasta = fechaHasta == null ? null : fechaHasta.isEmpty() ? null : "'" + fechaHasta + "'";
		String sql = "SELECT * FROM caja.fun_cuadre_de_caja(" + "'" + (empresa) + "', "
				+ (codigoMotivo == null ? codigoMotivo : "'" + codigoMotivo + "'") + ", " + fechaDesde + ", "
				+ fechaHasta + ");";
		return genericSQLDao.obtenerPorSql(sql, CajCuadreCajaTO.class);
	}

        @Override
	public Boolean accionCajaValesTO(CajVales cajVales, SisSuceso sisSuceso, CajValesNumeracion cajValesNumeracion,
			String accion) throws Exception {
		String rellenarConCeros = "";
		boolean bandera = false;
		try {

			int numeracion = valesNumeracionDao.buscarConteoUltimaNumeracionCajaVale(
					cajVales.getCajValesPK().getValeEmpresa(), cajVales.getCajValesPK().getValePeriodo(),
					cajVales.getCajValesPK().getValeMotivo());
			boolean nuevo = false;// , retorno = false;
			if (numeracion == 0)
				nuevo = true;
			if (accion.equals("I")) {
				do {
					numeracion++;
					int numeroCerosAponer = 7 - String.valueOf(numeracion).trim().length();
					rellenarConCeros = "";
					for (int i = 0; i < numeroCerosAponer; i++) {
						rellenarConCeros = rellenarConCeros + "0";
					}
					cajVales.setCajValesPK(new CajValesPK(cajVales.getCajValesPK().getValeEmpresa(),
							cajVales.getCajValesPK().getValePeriodo(), cajVales.getCajValesPK().getValeMotivo(),
							rellenarConCeros + numeracion));
					cajValesNumeracion.setNumSecuencia(rellenarConCeros + numeracion);
				} while (obtenerPorId(CajVales.class,
						new CajValesPK(cajVales.getCajValesPK().getValeEmpresa(),
								cajVales.getCajValesPK().getValePeriodo(), cajVales.getCajValesPK().getValeMotivo(),
								rellenarConCeros + numeracion)) != null);
			}
			sisSuceso.setSusClave(cajVales.getCajValesPK().getValePeriodo() + " "
					+ cajVales.getCajValesPK().getValeMotivo() + " " + cajVales.getCajValesPK().getValeNumero());
			String detalle = "Caja de Vale por " + cajVales.getValeObservaciones();
			sisSuceso.setSusDetalle(detalle.trim().length() > 300 ? detalle.trim().substring(0, 300) : detalle);
			bandera = accionCajVales(cajVales, sisSuceso, cajValesNumeracion, nuevo, accion);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bandera;
	}

	private boolean accionCajVales(CajVales cajVales, SisSuceso sisSuceso, CajValesNumeracion cajValesNumeracion,
			boolean nuevo, String accion) throws Exception {
		if (accion.equals("I")) {
			sisSucesoDao.insertar(sisSuceso);
			insertar(cajVales);
			if (nuevo)
				valesNumeracionDao.insertar(cajValesNumeracion);
			else {
				valesNumeracionDao.actualizar(cajValesNumeracion);
			}
		}
		if (accion.equals("E")) {
			sisSucesoDao.insertar(sisSuceso);
			eliminar(cajVales);
		}
		if (accion.equals("M")) {
			sisSucesoDao.insertar(sisSuceso);
			actualizar(cajVales);
		}
		return true;
	}

}
