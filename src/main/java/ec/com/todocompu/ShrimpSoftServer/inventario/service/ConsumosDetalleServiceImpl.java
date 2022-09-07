package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ConsumosDetalleDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleConsumoTO;

@Service
public class ConsumosDetalleServiceImpl implements ConsumosDetalleService {

    @Autowired
    private ConsumosDetalleDao consumosDetalleDao;

    @Override
    public List<InvListaDetalleConsumoTO> getListaInvConsumoDetalleTO(String empresa, String periodo, String motivo,
            String numeroConsumo) throws Exception {
        return consumosDetalleDao.getListaInvConsumoDetalleTO(empresa, periodo, motivo, numeroConsumo);
    }

}
