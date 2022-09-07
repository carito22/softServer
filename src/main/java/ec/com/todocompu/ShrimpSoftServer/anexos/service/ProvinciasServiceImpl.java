package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.ProvinciasDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxProvinciaCantonTO;

@Service
public class ProvinciasServiceImpl implements ProvinciasService {

    @Autowired
    private ProvinciasDao provinciasDao;

    @Override
    public List<AnxProvinciaCantonTO> getComboAnxProvinciaTO() throws Exception {
        return provinciasDao.getComboAnxProvinciaTO();
    }

    @Override
    public List<AnxProvinciaCantonTO> getComboAnxCantonTO(String provincia) throws Exception {
        return provinciasDao.getComboAnxCantonTO(provincia);
    }

}
