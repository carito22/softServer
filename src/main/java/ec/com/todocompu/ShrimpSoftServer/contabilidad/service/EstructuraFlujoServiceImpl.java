package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.EstructuraFlujoDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstructuraFlujoTO;

@Service
public class EstructuraFlujoServiceImpl implements EstructuraFlujoService {

    @Autowired
    private EstructuraFlujoDao estructuraFlujoDao;

    @Override
    public List<ConEstructuraFlujoTO> getListaConEstructuraFlujoTO(String empresa) throws Exception {
        return estructuraFlujoDao.getListaConEstructuraFlujoTO(empresa);
    }

}
