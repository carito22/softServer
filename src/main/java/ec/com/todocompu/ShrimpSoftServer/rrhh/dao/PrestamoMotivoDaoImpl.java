package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhPrestamoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhPrestamoMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class PrestamoMotivoDaoImpl extends GenericDaoImpl<RhPrestamoMotivo, RhPrestamoMotivoPK>
		implements PrestamoMotivoDao {

	@Autowired
	private SucesoDao sucesoDao;

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public boolean insertarRhPrestamoMotivo(RhPrestamoMotivo rhPrestamoMotivo, SisSuceso sisSuceso) throws Exception {
		insertar(rhPrestamoMotivo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean modificarRhPrestamoMotivo(RhPrestamoMotivo rhPrestamoMotivo, SisSuceso sisSuceso) throws Exception {
		actualizar(rhPrestamoMotivo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean eliminarRhPrestamoMotivo(RhPrestamoMotivo rhPrestamoMotivo, SisSuceso sisSuceso) throws Exception {
		eliminar(rhPrestamoMotivo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public RhPrestamoMotivo getRhPrestamoMotivo(String empresa, String detalle) throws Exception {
		return obtenerPorId(RhPrestamoMotivo.class, new RhPrestamoMotivoPK(empresa, detalle));
	}

        @Override
	public List<RhPrestamoMotivo> getListaRhPrestamoMotivo(String empresa) throws Exception {
		String consulta = "SELECT premot FROM RhPrestamoMotivo premot inner join premot.rhPrestamoMotivoPK premotpk "
				+ "WHERE premotpk.motEmpresa=?1 ORDER BY premotpk.motDetalle";
		return obtenerPorHql(consulta, new Object[] { empresa });
	}
        
        @Override
	public List<RhPrestamoMotivo> getListaRhPrestamoMotivos(String empresa, boolean inactivo) throws Exception {
		String consulta = "SELECT premot FROM RhPrestamoMotivo premot inner join premot.rhPrestamoMotivoPK premotpk "
				+ "WHERE premotpk.motEmpresa=?1"+(!inactivo ? " AND mot_inactivo=?2":"")+" ORDER BY premotpk.motDetalle";
		return obtenerPorHql(consulta, !inactivo ? new Object[] { empresa, inactivo } : new Object[] { empresa });
	}

        @Override
	public boolean banderaEliminarPrestamoMotivo(String empresa, String detalle) throws Exception {
		String sql = "SELECT * FROM recursoshumanos.fun_sw_elimina_motivo_descuento_fijo('" + empresa + "','" + detalle
				+ "');";
		return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
	}

}
