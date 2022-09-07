package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.CuentasEspecialesDao;

@Service
public class CuentasEspecialesServiceImpl implements CuentasEspecialesService {

    @Autowired
    private CuentasEspecialesDao cuentasEspecialesDao;

    @Override
    public Boolean buscarConDetallesActivosBiologicos(String empresa) throws Exception {
        return cuentasEspecialesDao.buscarConDetallesActivosBiologicos(empresa);
    }
}
