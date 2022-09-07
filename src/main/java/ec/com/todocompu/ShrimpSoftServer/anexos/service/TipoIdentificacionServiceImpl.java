package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.TipoIdentificacionDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoIdentificacionTO;

@Service
public class TipoIdentificacionServiceImpl implements TipoIdentificacionService {

    @Autowired
    private TipoIdentificacionDao tipoIdentificacionDao;

    @Override
    public List<AnxTipoIdentificacionTO> getListaAnxTipoIdentificacionTO() throws Exception {
        return tipoIdentificacionDao.getListaAnxTipoIdentificacionTO();
    }

    @Override
    public AnxTipoIdentificacionTO getAnxTipoIdentificacion(String codigo) throws Exception {
        return tipoIdentificacionDao.getAnxTipoIdentificacion(codigo);
    }

}
