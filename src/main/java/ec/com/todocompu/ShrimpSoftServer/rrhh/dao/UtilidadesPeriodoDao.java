package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhUtilidadesPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidadesPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface UtilidadesPeriodoDao extends GenericDao<RhUtilidadesPeriodo, String> {

	public Boolean accionRhUtilidadesPeriodo(RhUtilidadesPeriodo rhUtilidadesPeriodo, SisSuceso sisSuceso, char accion)
			throws Exception;

	public boolean comprobarRhUtilidadesPeriodo(String utiEmpresa, String utiDescripcion) throws Exception;

	public List<RhUtilidadesPeriodoTO> getRhComboUtilidadesPeriodoTO(String empresa) throws Exception;

}
