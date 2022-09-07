
package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import java.util.List;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificacionesEventos;

public interface SisEventoNotificacionDao extends GenericDao<SisEmpresaNotificacionesEventos, String> {

	public List<SisEmpresaNotificacionesEventos> listarSisEventoNotificacion() throws Exception;

}
