package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesRRHH;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhAnticipoMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipoMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class AnticipoMotivoDaoImpl extends GenericDaoImpl<RhAnticipoMotivo, RhAnticipoMotivoPK>
		implements AnticipoMotivoDao {

	@Autowired
	private SucesoDao sucesoDao;

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public Boolean accionRhAnticipoMotivo(RhAnticipoMotivo rhAnticipoMotivo, SisSuceso sisSuceso, char accion)
			throws Exception {
		if (accion == 'I')
			insertar(rhAnticipoMotivo);
		if (accion == 'M')
			actualizar(rhAnticipoMotivo);
		if (accion == 'E')
			eliminar(rhAnticipoMotivo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public RhAnticipoMotivoTO getRhAnticipoMotivoTO(String empresa, String detalle) throws Exception {
		return ConversionesRRHH.convertirRhAnticipoMotivo_RhAnticipoMotivoTO(getRhAnticipoMotivo(empresa, detalle));
	}

        @Override
	public Boolean buscarRhAnticipoMotivo(String empresa, String detalle) throws Exception {
		String sql = "SELECT COUNT(*) FROM recursoshumanos.rh_anticipo_motivo WHERE (mot_empresa = '" + empresa
				+ "') AND (mot_detalle = '" + detalle + "')";

		return ((int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql)) == 1) ? true : false;
	}

        @Override
	public List<RhAnticipoMotivoTO> getListaRhAnticipoMotivoTablaTO(String empresa) throws Exception {
		String sql = "SELECT mot_empresa, mot_detalle, mot_inactivo, usr_empresa, usr_codigo, usr_fecha_inserta, tip_empresa, tip_codigo "
				+ "FROM recursoshumanos.rh_anticipo_motivo WHERE mot_empresa = ('" + empresa
				+ "') ORDER BY mot_detalle";

		return genericSQLDao.obtenerPorSql(sql, RhAnticipoMotivoTO.class);
	}

        @Override
	public List<RhAnticipoMotivoTO> getListaRhAnticipoMotivoTOTablaTO(String empresa, String codigo) throws Exception {
		String sql = "SELECT * FROM recursoshumanos.rh_anticipo_motivo WHERE mot_empresa = ('" + empresa
				+ "') AND mot_detalle LIKE '%" + codigo + "%' ORDER BY mot_detalle";

		return genericSQLDao.obtenerPorSql(sql, RhAnticipoMotivoTO.class);
	}

        @Override
	public boolean insertarRhAnticipoMotivo(RhAnticipoMotivo rhAnticipoMotivo, SisSuceso sisSuceso) throws Exception {
		insertar(rhAnticipoMotivo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean modificarRhAnticipoMotivo(RhAnticipoMotivo rhAnticipoMotivo, SisSuceso sisSuceso) throws Exception {
		actualizar(rhAnticipoMotivo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean eliminarRhAnticipoMotivo(RhAnticipoMotivo rhAnticipoMotivo, SisSuceso sisSuceso) throws Exception {
		eliminar(rhAnticipoMotivo);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public RhAnticipoMotivo getRhAnticipoMotivo(String empresa, String detalle) throws Exception {
		return obtenerPorId(RhAnticipoMotivo.class, new RhAnticipoMotivoPK(empresa, detalle));
	}

        @Override
	public List<RhAnticipoMotivo> getListaRhAnticipoMotivo(String empresa) throws Exception {
		String consulta = "SELECT antmot FROM RhAnticipoMotivo antmot inner join antmot.rhAnticipoMotivoPK antmotpk "
				+ "WHERE antmotpk.motEmpresa=?1 ORDER BY antmotpk.motDetalle";
		return obtenerPorHql(consulta, new Object[] { empresa });
	}

        @Override
	public boolean banderaEliminarAnticipoMotivo(String empresa, String detalle) throws Exception {
		String sql = "SELECT * FROM recursoshumanos.fun_sw_elimina_motivo_descuento_fijo('" + empresa + "','" + detalle
				+ "');";
		return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
	}

}
