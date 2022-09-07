package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.NumeracionDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConNumeracionTO;

@Service
public class NumeracionServiceImpl implements NumeracionService {

    @Autowired
    private NumeracionDao numeracionDao;

    @Override
    public List<ConNumeracionTO> getListaConNumeracionTO(String empresa, String periodo, String tipo) throws Exception {
        return numeracionDao.getListaConNumeracionTO(empresa, periodo, tipo);
    }

}
