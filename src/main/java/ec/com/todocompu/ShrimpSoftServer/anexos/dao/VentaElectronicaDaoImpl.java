package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentasPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaElectronica;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class VentaElectronicaDaoImpl extends GenericDaoImpl<AnxVentaElectronica, Integer>
		implements VentaElectronicaDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

	@Autowired
	private SucesoDao sisSucesoDao;

	@Override
	public AnxVentaElectronica buscarAnxVentaElectronica(String empresa, String periodo, String motivo, String numero)
			throws Exception {
		String sql = "SELECT e_secuencial  " + "FROM anexo.anx_venta_electronica  " + "WHERE vta_empresa = '" + empresa
				+ "' AND vta_periodo = '" + periodo + "' AND   " + "vta_motivo = '" + motivo + "' and vta_numero = '"
				+ numero + "';";

		Object obj = genericSQLDao.obtenerObjetoPorSql(sql);

		return obtenerPorId(AnxVentaElectronica.class, (Integer) obj);
	}

	@Override
	public Boolean accionAnxVentaElectronica(AnxVentaElectronica anxVentaElectronica, SisSuceso sisSuceso, char accion)
			throws Exception {
		if (accion == 'I')
			insertar(anxVentaElectronica);
		if (accion == 'M')
			actualizar(anxVentaElectronica);
		if (accion == 'E')
			eliminar(anxVentaElectronica);
		if (sisSuceso != null)
			sisSucesoDao.insertar(sisSuceso);
		return true;
	}

	@Override
	public String getXmlComprobanteElectronico(String empresa, String ePeriodo, String eMotivo, String eNumero)
			throws Exception {
		empresa = empresa == null ? empresa : "'" + empresa + "'";
		ePeriodo = ePeriodo == null ? ePeriodo : "'" + ePeriodo + "'";
		eMotivo = eMotivo == null ? eMotivo : "'" + eMotivo + "'";
		eNumero = eNumero == null ? eNumero : "'" + eNumero + "'";
		String sql = "SELECT e_xml FROM anexo.anx_venta_electronica " + "WHERE vta_empresa = " + empresa
				+ " and  vta_periodo = " + ePeriodo + " and vta_motivo = " + eMotivo + " and vta_numero = " + eNumero;
		return new String((byte[]) genericSQLDao.obtenerObjetoPorSql(sql), "UTF-8");
	}

	@Override
	public boolean comprobarAnxVentaElectronica(String empresa, String ePeriodo, String eMotivo, String eNumero)
			throws Exception {
		empresa = empresa == null ? empresa : "'" + empresa + "'";
		ePeriodo = ePeriodo == null ? ePeriodo : "'" + ePeriodo + "'";
		eMotivo = eMotivo == null ? eMotivo : "'" + eMotivo + "'";
		eNumero = eNumero == null ? eNumero : "'" + eNumero + "'";
		String sql = "SELECT COUNT(*)!=0 FROM anexo.anx_venta_electronica " + "WHERE (vta_empresa = " + empresa
				+ " AND vta_periodo = " + ePeriodo + " AND vta_motivo = " + eMotivo + " AND vta_numero = " + eNumero
				+ ")";
		return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
	}

	@Override
	public String comprobarAnxVentaElectronicaAutorizacion(String empresa, String periodo, String motivo, String numero)
			throws Exception {

		String sql = "SELECT e_estado FROM anexo.anx_venta_electronica " + "WHERE (vta_empresa = '" + empresa
				+ "' AND vta_periodo = '" + periodo + "' AND vta_motivo = '" + motivo + "' AND vta_numero = '" + numero
				+ "')";

		return (String) genericSQLDao.obtenerObjetoPorSql(sql);
	}

	@Override
	public List<AnxListaVentasPendientesTO> getListaVentasPendientes(String empresa) throws Exception {
		String sql = "SELECT * FROM anexo.fun_ventas_electronicas_listado('" + empresa + "')";
		// return genericSQLDao.obtenerPorSql(sql,
		// AnxListaRetencionesRentaTO.class);
		return genericSQLDao.obtenerPorSql(sql, AnxListaVentasPendientesTO.class);
	}
        
        @Override
	public List<AnxListaVentasPendientesTO> getListaVentasPendientesAutorizacionAutomatica(String empresa) throws Exception {
		String sql = "SELECT * FROM anexo.fun_ventas_electronicas_listado('" + empresa + "') WHERE auxiliar";
		// return genericSQLDao.obtenerPorSql(sql,
		// AnxListaRetencionesRentaTO.class);
		return genericSQLDao.obtenerPorSql(sql, AnxListaVentasPendientesTO.class);
	}
}
