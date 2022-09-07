package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoListaTransaccionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxTipotransaccion;

public interface TipoTransaccionDao extends GenericDao<AnxTipotransaccion, String> {

	public List<AnxTipoListaTransaccionTO> getAnexoTipoListaTransaccionTO() throws Exception;

	public String getCodigoAnxTipoTransaccionTO(String codigoTipoIdentificacion, String tipoTransaccion)
			throws Exception;
}
