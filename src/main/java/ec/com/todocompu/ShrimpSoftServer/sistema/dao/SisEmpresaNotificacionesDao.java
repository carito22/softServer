
package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import java.util.List;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface SisEmpresaNotificacionesDao extends GenericDao<SisEmpresaNotificaciones, Integer> {

	public List<SisEmpresaNotificaciones> listarSisEmpresaNotificaciones(String empresa) throws Exception;

	public SisEmpresaNotificaciones insertarSisEmpresaNotificaciones(SisEmpresaNotificaciones sisEmpresaNotificaciones, SisSuceso sisSuceso) throws Exception;

	public SisEmpresaNotificaciones modificarSisEmpresaNotificaciones(SisEmpresaNotificaciones sisEmpresaNotificaciones, SisSuceso sisSuceso) throws Exception;

	public boolean eliminarSisEmpresaNotificaciones(Integer idNotificacion, SisSuceso sisSuceso) throws Exception;

}
