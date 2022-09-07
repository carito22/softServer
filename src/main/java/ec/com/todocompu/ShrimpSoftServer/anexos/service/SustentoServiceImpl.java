package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.SustentoDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxSustentoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxSustentoTO;

@Service
public class SustentoServiceImpl implements SustentoService {

    @Autowired
    private SustentoDao sustentoDao;

    @Override
    public List<AnxSustentoComboTO> getListaAnxSustentoComboTO(String tipoComprobante) throws Exception {
        return sustentoDao.getListaAnxSustentoComboTO(tipoComprobante);
    }

    @Override
    public List<AnxSustentoComboTO> getListaAnxSustentoComboTOByTipoCredito(String tipoComprobante, String tipoCredito) throws Exception {
        return sustentoDao.getListaAnxSustentoComboTOByTipoCredito(tipoComprobante, tipoCredito);
    }

    @Override
    public List<AnxSustentoTO> getAnexoSustentoTO() throws Exception {
        return sustentoDao.getAnexoSustentoTO();
    }

    @Override
    public AnxSustentoComboTO obtenerAnxSustentoComboTO(String codigo) throws Exception {
        return sustentoDao.obtenerAnxSustentoComboTO(codigo);
    }
}
