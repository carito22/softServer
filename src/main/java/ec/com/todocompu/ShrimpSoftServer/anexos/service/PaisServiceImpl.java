package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.PaisDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxPaisTO;

@Service
public class PaisServiceImpl implements PaisService {

    @Autowired
    private PaisDao paisDao;

    @Override
    public List<AnxPaisTO> getComboAnxPaisTO() throws Exception {
        return paisDao.getComboAnxPaisTO();
    }

}
