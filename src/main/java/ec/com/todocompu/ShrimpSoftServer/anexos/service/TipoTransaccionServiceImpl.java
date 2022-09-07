package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.TipoTransaccionDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoListaTransaccionTO;

@Service
public class TipoTransaccionServiceImpl implements TipoTransaccionService {

    @Autowired
    private TipoTransaccionDao tipoTransaccionDao;

    @Override
    public String getCodigoAnxTipoTransaccionTO(String tipoIdentificacion, String tipoTransaccion) throws Exception {
        return tipoTransaccionDao.getCodigoAnxTipoTransaccionTO(tipoIdentificacion, tipoTransaccion);
    }

    @Override
    public List<AnxTipoListaTransaccionTO> getAnexoTipoListaTransaccionTO() throws Exception {
        return tipoTransaccionDao.getAnexoTipoListaTransaccionTO();
    }

}
