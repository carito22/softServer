package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdProyeccionTO;

@Repository
public class ProyeccionDaoImpl extends GenericDaoImpl<PrdProyeccionTO, Integer> implements ProyeccionDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

        @Override
	public List<PrdProyeccionTO> getListaPrdProyeccion(String empresa, String sector, Date fecha) {
		String sql = "SELECT * from produccion.fun_proyeccion('" + empresa + "', '" + sector + "', '"
				+ UtilsDate.fechaFormatoString(fecha, "dd-MM-yyyy") + "');";
		return genericSQLDao.obtenerPorSql(sql, PrdProyeccionTO.class);
	}

}
