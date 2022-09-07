package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtrasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtrasMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class HorasExtrasMotivoDaoImpl extends GenericDaoImpl<RhHorasExtrasMotivo, RhHorasExtrasMotivoPK> implements HorasExtrasMotivoDao {

	@Autowired
	private SucesoDao sucesoDao;

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public boolean insertarRhHorasExtrasMotivo(RhHorasExtrasMotivo rhHorasExtrasMotivo, SisSuceso sisSuceso) throws Exception {
		insertar(rhHorasExtrasMotivo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean modificarRhHorasExtrasMotivo(RhHorasExtrasMotivo rhHorasExtrasMotivo, SisSuceso sisSuceso) throws Exception {
		actualizar(rhHorasExtrasMotivo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean eliminarRhHorasExtrasMotivo(RhHorasExtrasMotivo rhHorasExtrasMotivo, SisSuceso sisSuceso) throws Exception {
		eliminar(rhHorasExtrasMotivo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public RhHorasExtrasMotivo getRhHorasExtrasMotivo(String empresa, String detalle) throws Exception {
		return obtenerPorId(RhHorasExtrasMotivo.class, new RhHorasExtrasMotivoPK(empresa, detalle));
	}

        @Override
	public List<RhHorasExtrasMotivo> getListaRhHorasExtrasMotivo(String empresa) throws Exception {
		String consulta = "SELECT bonomot FROM RhHorasExtrasMotivo bonomot inner join bonomot.rhHorasExtrasMotivoPK bonomotpk "
				+ "WHERE bonomotpk.motEmpresa=?1 ORDER BY bonomotpk.motDetalle";
		return obtenerPorHql(consulta, new Object[] { empresa });
	}
        
        @Override
	public List<RhHorasExtrasMotivo> getListaRhHorasExtrasMotivos(String empresa, boolean inactivo) throws Exception {
		String consulta = "SELECT bonomot FROM RhHorasExtrasMotivo bonomot inner join bonomot.rhHorasExtrasMotivoPK bonomotpk "
				+ "WHERE bonomotpk.motEmpresa=?1"+(!inactivo ? " AND mot_inactivo=?2":"")+" ORDER BY bonomotpk.motDetalle";
		return obtenerPorHql(consulta, !inactivo ? new Object[] { empresa, inactivo } : new Object[] { empresa });
	}

        @Override
	public boolean banderaEliminarHorasExtrasMotivo(String empresa, String detalle) throws Exception {
		String sql = "SELECT * FROM recursoshumanos.fun_sw_elimina_motivo_descuento_fijo('" + empresa + "','" + detalle + "');";
		return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
	}

}
