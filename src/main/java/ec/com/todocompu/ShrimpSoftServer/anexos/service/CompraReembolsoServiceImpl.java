package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraReembolsoDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraReembolsoTO;

@Service
public class CompraReembolsoServiceImpl implements CompraReembolsoService {

    @Autowired
    private CompraReembolsoDao compraReembolsoDao;

    @Override
    public List<AnxCompraReembolsoTO> getAnexoCompraReembolsoTOs(String empresa, String periodo, String motivo,
            String numeroCompra) throws Exception {
        List<AnxCompraReembolsoTO> anxCompraReembolsos = compraReembolsoDao.getAnexoCompraReembolsoTOs(empresa, periodo,
                motivo, numeroCompra);
        for (AnxCompraReembolsoTO anxCompraReembolsoTO : anxCompraReembolsos) {
            anxCompraReembolsoTO.setReembFechaemision(
                    UtilsValidacion.convertirAaaaMmDdTODdMmAaaa(anxCompraReembolsoTO.getReembFechaemision()));
        }
        return anxCompraReembolsos;
    }

}
