package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.Formulario107PeriodoDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormulario107PeriodoFiscalTO;

@Service
public class Formulario107PeriodoServiceImpl implements Formulario107PeriodoService {

    @Autowired
    private Formulario107PeriodoDao formulario107PeriodoDao;

    @Override
    public List<RhFormulario107PeriodoFiscalTO> getRhFormulario107PeriodoFiscalComboTO() throws Exception {
        return formulario107PeriodoDao.getRhFormulario107PeriodoFiscalComboTO();
    }

}
