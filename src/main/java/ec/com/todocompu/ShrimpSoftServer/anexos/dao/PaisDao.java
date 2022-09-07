package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxPaisTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxPais;

public interface PaisDao extends GenericDao<AnxPais, String> {

    public List<AnxPaisTO> getComboAnxPaisTO() throws Exception;

    public AnxPaisTO getLocalAnxPaisTO() throws Exception;

    public AnxPaisTO getAnxPaisTO(String codigo) throws Exception;

}
