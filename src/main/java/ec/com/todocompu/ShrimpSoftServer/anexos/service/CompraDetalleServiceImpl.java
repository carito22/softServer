package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraDetalleDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraDetalleTO;

@Service
public class CompraDetalleServiceImpl implements CompraDetalleService {

    @Autowired
    private CompraDetalleDao compraDetalleDao;

    @Override
    public List<AnxCompraDetalleTO> getAnexoCompraDetalleTO(String empresa, String periodo, String motivo,
            String numeroCompra) throws Exception {
        return compraDetalleDao.getAnexoCompraDetalleTO(empresa, periodo, motivo, numeroCompra);
    }

}
