package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.TipoComprobanteDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteTablaTO;

@Service
public class TipoComprobanteServiceImpl implements TipoComprobanteService {

    @Autowired
    private TipoComprobanteDao tipoComprobanteDao;

    @Override
    public List<AnxTipoComprobanteTablaTO> getListaAnexoTipoComprobanteTO(String codigo) throws Exception {
        return tipoComprobanteDao.getListaAnexoTipoComprobanteTO(codigo);
    }

    @Override
    public List<AnxTipoComprobanteComboTO> getListaAnxTipoComprobanteComboTO(String codigoTipoTransaccion)
            throws Exception {
        return tipoComprobanteDao.getListaAnxTipoComprobanteComboTO(codigoTipoTransaccion);
    }

    @Override
    public List<AnxTipoComprobanteComboTO> getListaAnxTipoComprobanteComboTOCompleto() throws Exception {
        return tipoComprobanteDao.getListaAnxTipoComprobanteComboTOCompleto();
    }

    @Override
    public List<AnxTipoComprobanteTO> getAnexoTipoComprobanteTO() throws Exception {
        return tipoComprobanteDao.getAnexoTipoComprobanteTO();
    }
}
