package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoIdentificacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxTipoidentificacion;

public interface TipoIdentificacionDao extends GenericDao<AnxTipoidentificacion, Character> {

    public List<AnxTipoIdentificacionTO> getListaAnxTipoIdentificacionTO() throws Exception;

    public AnxTipoIdentificacionTO getAnxTipoIdentificacion(String codigo) throws Exception;
}
