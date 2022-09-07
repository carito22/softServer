package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.anxUrlWebServicesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxUrlWebServices;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface UrlWebServicesDao extends GenericDao<AnxUrlWebServices, Integer> {

	public Boolean accionAnxUrlWebServixce(AnxUrlWebServices anxUrlWebServices, SisSuceso sisSuceso, char accion)
			throws Exception;

	public anxUrlWebServicesTO getAnxUrlWebServicesTO() throws Exception;
}
