package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhBonoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhBonoMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class BonoMotivoDaoImpl extends GenericDaoImpl<RhBonoMotivo, RhBonoMotivoPK> implements BonoMotivoDao {

	@Autowired
	private SucesoDao sucesoDao;

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public boolean insertarRhBonoMotivo(RhBonoMotivo rhBonoMotivo, SisSuceso sisSuceso) throws Exception {
		insertar(rhBonoMotivo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean modificarRhBonoMotivo(RhBonoMotivo rhBonoMotivo, SisSuceso sisSuceso) throws Exception {
		actualizar(rhBonoMotivo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean eliminarRhBonoMotivo(RhBonoMotivo rhBonoMotivo, SisSuceso sisSuceso) throws Exception {
		eliminar(rhBonoMotivo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public RhBonoMotivo getRhBonoMotivo(String empresa, String detalle) throws Exception {
		return obtenerPorId(RhBonoMotivo.class, new RhBonoMotivoPK(empresa, detalle));
	}

        @Override
	public List<RhBonoMotivo> getListaRhBonoMotivo(String empresa) throws Exception {
		String consulta = "SELECT bonomot FROM RhBonoMotivo bonomot inner join bonomot.rhBonoMotivoPK bonomotpk "
				+ "WHERE bonomotpk.motEmpresa=?1 ORDER BY bonomotpk.motDetalle";
		return obtenerPorHql(consulta, new Object[] { empresa });
	}
        
        @Override
	public List<RhBonoMotivo> getListaRhBonoMotivos(String empresa, boolean inactivo) throws Exception {
		String consulta = "SELECT bonomot FROM RhBonoMotivo bonomot inner join bonomot.rhBonoMotivoPK bonomotpk "
				+ "WHERE bonomotpk.motEmpresa=?1"+(!inactivo ? " AND mot_inactivo=?2":"")+" ORDER BY bonomotpk.motDetalle";
		return obtenerPorHql(consulta, !inactivo ? new Object[] { empresa, inactivo } : new Object[] { empresa });
	}

        @Override
	public boolean banderaEliminarBonoMotivo(String empresa, String detalle) throws Exception {
		String sql = "SELECT * FROM recursoshumanos.fun_sw_elimina_motivo_descuento_fijo('" + empresa + "','" + detalle
				+ "');";
		return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
	}

}
