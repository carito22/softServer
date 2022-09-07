package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxProvinciaCantonTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxProvincias;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxProvinciasPK;

public interface ProvinciasDao extends GenericDao<AnxProvincias, AnxProvinciasPK> {

	public List<AnxProvinciaCantonTO> getComboAnxProvinciaTO() throws Exception;

	public List<AnxProvinciaCantonTO> getComboAnxCantonTO(String provincia) throws Exception;
        
        public AnxProvinciaCantonTO obtenerProvincia(String provincia) throws Exception;
        
        public AnxProvinciaCantonTO obtenerCanton(String provincia, String canton) throws Exception;

}
