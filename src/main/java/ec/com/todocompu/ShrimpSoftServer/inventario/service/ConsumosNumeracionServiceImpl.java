package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ConsumosNumeracionDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvNumeracionConsumoTO;

@Service
public class ConsumosNumeracionServiceImpl implements ConsumosNumeracionService {

    @Autowired
    private ConsumosNumeracionDao consumosNumeracionDao;

    @Override
    public List<InvNumeracionConsumoTO> getListaInvNumeracionConsumoTO(String empresa, String periodo, String motivo)
            throws Exception {
        return consumosNumeracionDao.getListaInvNumeracionConsumoTO(empresa, periodo, motivo);
    }

}
