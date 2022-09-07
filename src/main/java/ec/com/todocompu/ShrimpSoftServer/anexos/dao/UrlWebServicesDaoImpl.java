package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.anxUrlWebServicesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxUrlWebServices;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class UrlWebServicesDaoImpl extends GenericDaoImpl<AnxUrlWebServices, Integer> implements UrlWebServicesDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

	@Autowired
	private SucesoDao sisSucesoDao;

	@Override
	public Boolean accionAnxUrlWebServixce(AnxUrlWebServices anxUrlWebServices, SisSuceso sisSuceso, char accion)
			throws Exception {
		if (accion == 'I')
			insertar(anxUrlWebServices);
		if (accion == 'M')
			actualizar(anxUrlWebServices);
		if (accion == 'E')
			eliminar(anxUrlWebServices);
		sisSucesoDao.insertar(sisSuceso);
		return true;
	}

	@Override
	public anxUrlWebServicesTO getAnxUrlWebServicesTO() throws Exception {
		anxUrlWebServicesTO anxUrlWebServicesTO = null;
		String sql = "SELECT url_secuencial serial, url_ambiente_produccion, "
				+ "url_ambiente_pruebas FROM anexo.anx_url_web_services";
		Object[] array = UtilsConversiones.convertirListToArray(genericSQLDao.obtenerPorSql(sql), 0);
		if (array != null) {
			anxUrlWebServicesTO = new anxUrlWebServicesTO();
			anxUrlWebServicesTO.setUrlSecuencial(array[0] == null ? 0 : new Integer(array[0].toString().trim()));
			anxUrlWebServicesTO.setUrlAmbienteProduccion(array[1] == null ? null : array[1].toString().trim());
			anxUrlWebServicesTO.setUrlAmbientePruebas(array[2] == null ? null : array[2].toString().trim());
		} else {
			anxUrlWebServicesTO = null;
		}
		return anxUrlWebServicesTO;
	}
}
