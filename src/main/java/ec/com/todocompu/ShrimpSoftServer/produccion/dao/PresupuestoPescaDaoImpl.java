package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPesca;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class PresupuestoPescaDaoImpl extends GenericDaoImpl<PrdPresupuestoPesca, PrdPresupuestoPescaPK>
		implements PresupuestoPescaDao {

	@Autowired
	private PresupuestoPescaDetalleDao presupuestoPescaDetalleDao;
	@Autowired
	private GenericSQLDao genericSQLDao;
	@Autowired
	private SucesoDao sucesoDao;

	private PrdPresupuestoPesca obtenerPorId(PrdPresupuestoPescaPK prdPresupuestoPescaPK) throws Exception {
		return obtenerPorId(PrdPresupuestoPesca.class, prdPresupuestoPescaPK);
	}

        @Override
	public boolean insertarModificarPrdPresupuestoPesca(PrdPresupuestoPesca prdPresupuestoPesca,
			List<PrdPresupuestoPescaDetalle> listaPrdPresupuestoPescaDetalles, SisSuceso sisSuceso) throws Exception {
		if (prdPresupuestoPesca.getPrdPresupuestoPescaPK().getPresuNumero() == null
				|| prdPresupuestoPesca.getPrdPresupuestoPescaPK().getPresuNumero().compareToIgnoreCase("") == 0) {
			String rellenarConCeros = "";
			int numeracion = buscarConteoUltimaNumeracionPresupuesto(
					prdPresupuestoPesca.getPrdPresupuestoPescaPK().getPresuEmpresa(),
					prdPresupuestoPesca.getPrdPresupuestoPescaPK().getPresuMotivo());
			do {
				numeracion++;
				int numeroCerosAponer = 7 - String.valueOf(numeracion).trim().length();
				rellenarConCeros = "";
				for (int i = 0; i < numeroCerosAponer; i++) {
					rellenarConCeros = rellenarConCeros + "0";
				}

				prdPresupuestoPesca.getPrdPresupuestoPescaPK().setPresuNumero(rellenarConCeros + numeracion);

			} while (obtenerPorId(prdPresupuestoPesca.getPrdPresupuestoPescaPK()) != null);
		}
		sisSuceso.setSusClave(prdPresupuestoPesca.getPrdPresupuestoPescaPK().getPresuEmpresa() + " "
				+ prdPresupuestoPesca.getPrdPresupuestoPescaPK().getPresuNumero());
		sisSuceso.setSusDetalle("Presupuesto de pesca por " + prdPresupuestoPesca.getPresuObservaciones());

		////////////// Inserta el presupuesto///////////////////
		List<PrdPresupuestoPescaDetalle> list = prdPresupuestoPesca.getPrdPresupuestoPescaDetalleList();

		for (int i = 0; i < listaPrdPresupuestoPescaDetalles.size(); i++) {
			listaPrdPresupuestoPescaDetalles.get(i).getPrdLiquidacionTalla().setTallaGramosDesde(null);
			listaPrdPresupuestoPescaDetalles.get(i).setDetGramos(new BigDecimal("0.00"));
			if (listaPrdPresupuestoPescaDetalles.get(i).getDetCantidad() == 0) {
				listaPrdPresupuestoPescaDetalles.remove(i);
				i--;
			}
		}

		for (int i = 0; i < listaPrdPresupuestoPescaDetalles.size(); i++) {
			if (list == null || list.isEmpty())
				listaPrdPresupuestoPescaDetalles.get(i).setDetSecuencial(null);
			listaPrdPresupuestoPescaDetalles.get(i).setDetOrden(i + 1);
			listaPrdPresupuestoPescaDetalles.get(i).setPrdPresupuestoPesca(prdPresupuestoPesca);
		}

		prdPresupuestoPesca.setPrdPresupuestoPescaDetalleList(new ArrayList<PrdPresupuestoPescaDetalle>());
		prdPresupuestoPesca.getPrdPresupuestoPescaDetalleList().addAll(listaPrdPresupuestoPescaDetalles);
		prdPresupuestoPesca.setPresuGramosPromedio(new BigDecimal("0.00"));

		saveOrUpdate(prdPresupuestoPesca);

		if (list != null && !list.isEmpty()) {
			list.removeAll(listaPrdPresupuestoPescaDetalles);
			for (int i = 0; i < list.size(); i++)
				presupuestoPescaDetalleDao.eliminarPorSql(list.get(i).getDetSecuencial());
		}

		sucesoDao.insertar(sisSuceso);
		////////////////////////////////

		return true;
	}

        @Override
	public PrdPresupuestoPesca getPrdPresupuestoPesca(PrdPresupuestoPescaPK presupuestoPescaPK) {
		return obtenerObjetoPorHql(
				"select distinct pp from PrdPresupuestoPesca pp inner join pp.prdPresupuestoPescaPK pppk inner join fetch pp.prdPresupuestoPescaDetalleList "
						+ "where pppk.presuEmpresa=?1 and pppk.presuNumero=?2",
				new Object[] { presupuestoPescaPK.getPresuEmpresa(), presupuestoPescaPK.getPresuNumero() });
	}

        @Override
	public List<PrdPresupuestoPesca> getListaPrdPresupuestoPesca(String empresa) {
		return obtenerPorHql(
				"select distinct pp from PrdPresupuestoPesca pp inner join pp.prdPresupuestoPescaPK pppk inner join fetch pp.prdPresupuestoPescaDetalleList "
						+ "where pppk.presuEmpresa=?1",
				new Object[] { empresa });
	}

        @Override
	public void desmayorizarPresupuestoPesca(PrdPresupuestoPescaPK prdPresupuestoPescaPK) {
		String sql = "UPDATE produccion.prd_presupuesto_pesca SET presu_pendiente=true WHERE presu_empresa='"
				+ prdPresupuestoPescaPK.getPresuEmpresa() + "' and presu_numero='"
				+ prdPresupuestoPescaPK.getPresuNumero() + "';";
		genericSQLDao.ejecutarSQL(sql);
	}

        @Override
	public void anularRestaurarPresupuestoPesca(PrdPresupuestoPescaPK prdPresupuestoPescaPK, boolean anularRestaurar) {
		String sql = "UPDATE produccion.prd_presupuesto_pesca SET presu_anulado=" + anularRestaurar
				+ " WHERE presu_empresa='" + prdPresupuestoPescaPK.getPresuEmpresa() + "' and presu_numero='"
				+ prdPresupuestoPescaPK.getPresuNumero() + "';";
		genericSQLDao.ejecutarSQL(sql);
	}

        @Override
	public int buscarConteoUltimaNumeracionPresupuesto(String empCodigo, String motCodigo) throws Exception {
		String sql = "SELECT num_secuencia FROM produccion.prd_presupuesto_pesca_numeracion WHERE num_empresa='"
				+ empCodigo + "' AND num_motivo='" + motCodigo + "'";
		Object objeto = genericSQLDao.obtenerObjetoPorSql(sql);
		return objeto == null ? 0 : Integer.parseInt((String) UtilsConversiones.convertir(objeto));
	}

}
