package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCorridaDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorridaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorridaPK;

@Repository
public class CorridaDetalleDaoImpl extends GenericDaoImpl<PrdCorridaDetalle, Long> implements CorridaDetalleDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public List<PrdCorridaDetalle> getCorridaDetalleOrigenPorCorrida(String empresa, String sector, String piscina,
			String corrida) {
		String sql = "SELECT * FROM produccion.prd_corrida_detalle WHERE det_corrida_origen_empresa = '" + empresa
				+ "' AND det_corrida_origen_sector = '" + sector + "' AND det_corrida_origen_piscina = '" + piscina
				+ "' AND det_corrida_origen_numero='" + corrida + "'";
		List<PrdCorridaDetalleTO> listAux = genericSQLDao.obtenerPorSql(sql, PrdCorridaDetalleTO.class);
		List<PrdCorridaDetalle> list = new ArrayList<>();
		if (listAux != null)
			for (PrdCorridaDetalleTO cd : listAux)
				list.add(new PrdCorridaDetalle(cd.getDetSecuencial(),
						new PrdCorrida(cd.getDet_corrida_destino_empresa(), cd.getDet_corrida_destino_sector(),
								cd.getDet_corrida_destino_piscina(), cd.getDet_corrida_destino_numero()),
						new PrdCorrida(cd.getDet_corrida_origen_empresa(), cd.getDet_corrida_origen_sector(),
								cd.getDet_corrida_origen_piscina(), cd.getDet_corrida_origen_numero()),
						cd.getDetPorcentaje()));
		return list;
	}

        @Override
	public List<PrdCorridaDetalle> getCorridaDetalleDestinoPorCorrida(String empresa, String sector, String piscina,
			String corrida) {
		String sql = "SELECT * FROM produccion.prd_corrida_detalle WHERE det_corrida_destino_empresa = '" + empresa
				+ "' AND det_corrida_destino_sector = '" + sector + "' AND det_corrida_destino_piscina = '" + piscina
				+ "' AND det_corrida_destino_numero='" + corrida + "'";
		List<PrdCorridaDetalleTO> listAux = genericSQLDao.obtenerPorSql(sql, PrdCorridaDetalleTO.class);
		List<PrdCorridaDetalle> list = new ArrayList<>();
		if (listAux != null)
			for (PrdCorridaDetalleTO cd : listAux)
				list.add(new PrdCorridaDetalle(cd.getDetSecuencial(),
						new PrdCorrida(cd.getDet_corrida_destino_empresa(), cd.getDet_corrida_destino_sector(),
								cd.getDet_corrida_destino_piscina(), cd.getDet_corrida_destino_numero()),
						new PrdCorrida(cd.getDet_corrida_origen_empresa(), cd.getDet_corrida_origen_sector(),
								cd.getDet_corrida_origen_piscina(), cd.getDet_corrida_origen_numero()),
						cd.getDetPorcentaje()));
		return list;
	}

        @Override
	public BigDecimal getTotalPorcentajePorCorrida(PrdCorridaPK corridaOrigen, PrdCorridaPK corridaDestino) {
		String sql = "SELECT sum(det_porcentaje) FROM produccion.prd_corrida_detalle WHERE det_corrida_origen_empresa = '"
				+ corridaOrigen.getCorEmpresa() + "' AND det_corrida_origen_sector = '" + corridaOrigen.getCorSector()
				+ "' AND det_corrida_origen_piscina = '" + corridaOrigen.getCorPiscina()
				+ "' AND det_corrida_origen_numero='" + corridaOrigen.getCorNumero() + "' "
				+ "AND NOT (det_corrida_destino_empresa = '" + corridaDestino.getCorEmpresa()
				+ "' AND det_corrida_destino_sector = '" + corridaDestino.getCorSector()
				+ "' AND det_corrida_destino_piscina = '" + corridaDestino.getCorPiscina()
				+ "' AND det_corrida_destino_numero='" + corridaDestino.getCorNumero() + "')"
				+ "GROUP BY det_corrida_origen_empresa, det_corrida_origen_sector, det_corrida_origen_piscina, det_corrida_origen_numero";
		Object obj = (Object) genericSQLDao.obtenerObjetoPorSql(sql);
		return obj == null ? new BigDecimal("0.00") : (BigDecimal) UtilsConversiones.convertir(obj);
	}

        @Override
	public void ejecutarSQL(String sql) throws Exception {
		genericSQLDao.ejecutarSQL(sql);
	}

}
