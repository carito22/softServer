package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.DpaEcuadorDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFunRegistroDatosCrediticiosTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxProvinciaCantonTO;

@Service
public class DpaEcuadorServiceImpl implements DpaEcuadorService {

    @Autowired
    private DpaEcuadorDao dpaEcuadorDao;

    @Override
    public List<AnxProvinciaCantonTO> getComboAnxDpaProvinciaTO() throws Exception {
        return dpaEcuadorDao.getComboAnxDpaProvinciaTO();
    }

    @Override
    public List<AnxProvinciaCantonTO> getComboAnxDpaCantonTO(String codigoProvincia) throws Exception {
        return dpaEcuadorDao.getComboAnxDpaCantonTO(codigoProvincia);
    }

    @Override
    public List<AnxProvinciaCantonTO> getComboAnxParroquiaTO(String codigoProvincia, String codigoCanton)
            throws Exception {
        return dpaEcuadorDao.getComboAnxParroquiaTO(codigoProvincia, codigoCanton);
    }

    @Override
    public String getObtenerProvincia(String canton) throws Exception {
        return dpaEcuadorDao.getObtenerProvincia(canton);
    }

    @Override
    public List<AnxFunRegistroDatosCrediticiosTO> getFunRegistroDatosCrediticiosTOs(String codigoEmpresa,
            String fechaDesde, String fechaHasta) throws Exception {
        return dpaEcuadorDao.getFunRegistroDatosCrediticiosTOs(codigoEmpresa, fechaDesde, fechaHasta);
    }
}
