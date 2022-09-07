package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.TransferenciasDetalleDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleTransferenciaTO;

@Service
public class TransferenciasDetalleServiceImpl implements TransferenciasDetalleService {

    @Autowired
    private TransferenciasDetalleDao transferenciasDetalleDao;

    @Override
    public List<InvListaDetalleTransferenciaTO> getListaInvTransferenciasDetalleTO(String empresa, String periodo,
            String motivo, String numeroTransferencia) throws Exception {
        return transferenciasDetalleDao.getListaInvTransferenciasDetalleTO(empresa, periodo, motivo,
                numeroTransferencia);
    }

}
