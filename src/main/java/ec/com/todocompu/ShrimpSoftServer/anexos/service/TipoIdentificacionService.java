package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoIdentificacionTO;

@Transactional
public interface TipoIdentificacionService {

    public List<AnxTipoIdentificacionTO> getListaAnxTipoIdentificacionTO() throws Exception;

    public AnxTipoIdentificacionTO getAnxTipoIdentificacion(String codigo) throws Exception;
}
