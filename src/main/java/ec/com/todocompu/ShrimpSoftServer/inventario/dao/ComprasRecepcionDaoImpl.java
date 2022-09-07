package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasRecepcionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasRecepcion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class ComprasRecepcionDaoImpl extends GenericDaoImpl<InvComprasRecepcion, Long> implements ComprasRecepcionDao {

	@Autowired
	private SucesoDao sucesoDao;
	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public boolean insertarModificarinvComprasRecepcion(InvComprasRecepcion invComprasRecepcion, SisSuceso sisSuceso)
			throws Exception {
		sucesoDao.insertar(sisSuceso);
		saveOrUpdate(invComprasRecepcion);
		return true;
	}

        @Override
	public InvComprasRecepcionTO getInvComprasRecepcionTO(String empresa, String periodo, String motivo, String numero)
			throws Exception {
		String sql = "SELECT recep_secuencial, SUBSTRING(cast(recep_fecha as TEXT), 1, 10) as recep_fecha, comp_empresa, comp_periodo, "
				+ "comp_motivo, comp_numero, usr_empresa, usr_codigo, "
				+ "SUBSTRING(cast(usr_fecha_inserta as TEXT), 1, 10) as usr_fecha_inserta FROM inventario.inv_compras_recepcion "
				+ "WHERE comp_empresa = '" + empresa + "' AND comp_periodo = '" + periodo + "' " + "AND comp_motivo = '"
				+ motivo + "' AND comp_numero = '" + numero + "'";

		return genericSQLDao.obtenerObjetoPorSql(sql, InvComprasRecepcionTO.class);

	}
}
