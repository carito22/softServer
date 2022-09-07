package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRolMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRolMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class RolMotivoDaoImpl extends GenericDaoImpl<RhRolMotivo, RhRolMotivoPK> implements RolMotivoDao {

	@Autowired
	private SucesoDao sucesoDao;

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public boolean insertarRhRolMotivo(RhRolMotivo rhRolMotivo, SisSuceso sisSuceso) throws Exception {
		insertar(rhRolMotivo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean modificarRhRolMotivo(RhRolMotivo rhRolMotivo, SisSuceso sisSuceso) throws Exception {
		actualizar(rhRolMotivo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean eliminarRhRolMotivo(RhRolMotivo rhRolMotivo, SisSuceso sisSuceso) throws Exception {
		eliminar(rhRolMotivo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public RhRolMotivo getRhRolMotivo(String empresa, String detalle) throws Exception {
		return obtenerPorId(RhRolMotivo.class, new RhRolMotivoPK(empresa, detalle));
	}
        
        @Override
	public RhRolMotivo obtenerMotivoPorTipoDecomprobante(String empresa, String tipoComprobante) throws Exception {
            String sql = "SELECT * FROM recursoshumanos.rh_rol_motivo "
				+ "WHERE tip_empresa='" + empresa + "' AND tip_codigo = '" + tipoComprobante + "'";
            return genericSQLDao.obtenerObjetoPorSql(sql, RhRolMotivo.class);
	}

        @Override
	public List<RhRolMotivo> getListaRhRolMotivo(String empresa) throws Exception {
		String consulta = "SELECT rolmot FROM RhRolMotivo rolmot inner join rolmot.rhRolMotivoPK rolmotpk "
				+ "WHERE rolmotpk.motEmpresa=?1 ORDER BY rolmotpk.motDetalle";
		return obtenerPorHql(consulta, new Object[] { empresa });
	}
        
        @Override
	public List<RhRolMotivo> getListaRhRolMotivos(String empresa, boolean inactivo) throws Exception {
		String consulta = "SELECT rolmot FROM RhRolMotivo rolmot inner join rolmot.rhRolMotivoPK rolmotpk "
				+ "WHERE rolmotpk.motEmpresa=?1"+(!inactivo ? " AND mot_inactivo=?2":"")+" ORDER BY rolmotpk.motDetalle";
		return obtenerPorHql(consulta, !inactivo ? new Object[] { empresa, inactivo } : new Object[] { empresa });
	}

        @Override
	public boolean banderaEliminarRolMotivo(String empresa, String detalle) throws Exception {
		String sql = "SELECT * FROM recursoshumanos.fun_sw_elimina_motivo_descuento_fijo('" + empresa + "','" + detalle
				+ "');";
		return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
	}

}
