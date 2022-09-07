package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.Date;
import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdProyeccionTO;

public interface ProyeccionDao extends GenericDao<PrdProyeccionTO, Integer> {

	public List<PrdProyeccionTO> getListaPrdProyeccion(String empresa, String sector, Date fecha);

}
