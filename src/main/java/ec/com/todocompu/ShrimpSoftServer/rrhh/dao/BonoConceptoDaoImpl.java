package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhBonoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboBonoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaBonoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhBonoConcepto;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class BonoConceptoDaoImpl extends GenericDaoImpl<RhBonoConcepto, Integer> implements BonoConceptoDao {

	@Autowired
	private SucesoDao sucesoDao;

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public RhBonoConcepto buscarBonoConcepto(Integer bcSecuencia) throws Exception {
		return obtenerPorId(RhBonoConcepto.class, bcSecuencia);
	}

        @Override
	public boolean insertarRhBonoConcepto(RhBonoConcepto rhBonoConcepto, SisSuceso sisSuceso) throws Exception {
		insertar(rhBonoConcepto);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean modificarRhBonoConcepto(RhBonoConcepto rhBonoConcepto, SisSuceso sisSuceso) throws Exception {
		actualizar(rhBonoConcepto);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean eliminarRhBonoConcepto(RhBonoConcepto rhBonoConcepto, SisSuceso sisSuceso) throws Exception {
		eliminar(rhBonoConcepto);
		sucesoDao.insertar(sisSuceso);
		return true;
	}

        @Override
	public boolean eliminarRhBonoConcepto(RhBonoConceptoTO rhBonoConceptoTO) throws Exception {
		// String sql = "";
		// return (boolean)
		// UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
		return true;

	}

        @Override
	public List<RhListaBonoConceptoTO> getListaBonoConcepto(String empresa) throws Exception {
		String sql = "SELECT bc_secuencial, bc_detalle, " + "bc_valor, bc_valor_fijo, bc_inactivo "
				+ "FROM recursoshumanos.rh_bono_concepto " + "WHERE (usr_empresa = '" + empresa + "')";

		return genericSQLDao.obtenerPorSql(sql, RhListaBonoConceptoTO.class);
	}
        
        @Override
	public List<RhListaBonoConceptoTO> getListaBonoConceptos(String empresa, boolean inactivo) throws Exception {
		String sql = "SELECT bc_secuencial, bc_detalle, " + "bc_valor, bc_valor_fijo, bc_inactivo "
				+ "FROM recursoshumanos.rh_bono_concepto " + "WHERE (usr_empresa = '" + empresa + "'"+(!inactivo ? " AND bc_inactivo="+inactivo:"")+")";

		return genericSQLDao.obtenerPorSql(sql, RhListaBonoConceptoTO.class);
	}

        @Override
	public List<RhComboBonoConceptoTO> getComboBonoConcepto(String empresa) throws Exception {
		String sql = "SELECT bc_detalle, bc_valor, bc_valor_fijo " + "FROM recursoshumanos.rh_bono_concepto "
				+ "WHERE (usr_empresa = '" + empresa + "') " + "AND (NOT bc_inactivo)";

		return genericSQLDao.obtenerPorSql(sql, RhComboBonoConceptoTO.class);
	}

}
