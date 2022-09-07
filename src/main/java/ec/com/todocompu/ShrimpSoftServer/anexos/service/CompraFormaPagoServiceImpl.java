package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraFormaPagoDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraFormaPago;

@Service
public class CompraFormaPagoServiceImpl implements CompraFormaPagoService {

    @Autowired
    private CompraFormaPagoDao compraFormaPagoDao;

    @Override
    public List<AnxCompraFormaPagoTO> getAnexoCompraFormaPagoTO(String empresa, String periodo, String motivo,
            String numero) throws Exception {
        return compraFormaPagoDao.getAnexoCompraFormaPagoTO(empresa, periodo, motivo, numero);
    }

    @Override
    public List<AnxCompraFormaPago> getAnexoCompraFormaPago(String empresa, String periodo, String motivo,
            String numeroCompra) throws Exception {
        return compraFormaPagoDao.getAnexoCompraFormaPago(empresa, periodo, motivo, numeroCompra);
    }
}
