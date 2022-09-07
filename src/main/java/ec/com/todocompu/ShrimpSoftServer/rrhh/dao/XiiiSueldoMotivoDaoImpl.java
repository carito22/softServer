package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldoMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class XiiiSueldoMotivoDaoImpl extends GenericDaoImpl<RhXiiiSueldoMotivo, RhXiiiSueldoMotivoPK>
		implements XiiiSueldoMotivoDao {

	@Autowired
	private SucesoDao sucesoDao;

        @Override
	public boolean insertarRhXiiiSueldoMotivo(RhXiiiSueldoMotivo rhXiiiSueldoMotivo, SisSuceso sisSuceso)
			throws Exception {
		insertar(rhXiiiSueldoMotivo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean modificarRhXiiiSueldoMotivo(RhXiiiSueldoMotivo rhXiiiSueldoMotivo, SisSuceso sisSuceso)
			throws Exception {
		actualizar(rhXiiiSueldoMotivo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean eliminarRhXiiiSueldoMotivo(RhXiiiSueldoMotivo rhXiiiSueldoMotivo, SisSuceso sisSuceso)
			throws Exception {
		eliminar(rhXiiiSueldoMotivo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public RhXiiiSueldoMotivo getRhXiiiSueldoMotivo(String empresa, String detalle) throws Exception {
		return obtenerPorId(RhXiiiSueldoMotivo.class, new RhXiiiSueldoMotivoPK(empresa, detalle));
	}

        @Override
	public List<RhXiiiSueldoMotivo> getListaRhXiiiSueldoMotivo(String empresa) throws Exception {
		String consulta = "SELECT viiimot FROM RhXiiiSueldoMotivo viiimot inner join viiimot.rhXiiiSueldoMotivoPK viiimotpk "
				+ "WHERE viiimotpk.motEmpresa=?1 ORDER BY viiimotpk.motDetalle";
		return obtenerPorHql(consulta, new Object[] { empresa });
	}
        
        @Override
	public List<RhXiiiSueldoMotivo> getListaRhXiiiSueldoMotivos(String empresa, boolean inactivo) throws Exception {
		String consulta = "SELECT viiimot FROM RhXiiiSueldoMotivo viiimot inner join viiimot.rhXiiiSueldoMotivoPK viiimotpk "
				+ "WHERE viiimotpk.motEmpresa=?1"+(!inactivo ? " AND mot_inactivo=?2":"")+" ORDER BY viiimotpk.motDetalle";
		return obtenerPorHql(consulta, !inactivo ? new Object[] { empresa, inactivo } : new Object[] { empresa });
	}

        @Override
	public boolean banderaEliminarXiiiMotivo(String empresa, String detalle) throws Exception {
		return true;
	}

}
