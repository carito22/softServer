package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasNumeracionDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvNumeracionCompraTO;

@Service
public class ComprasNumeracionServiceImpl implements ComprasNumeracionService {

    @Autowired
    private ComprasNumeracionDao comprasNumeracionDao;

    @Override
    public List<InvNumeracionCompraTO> getListaInvNumeracionCompraTO(String empresa, String periodo, String motivo)
            throws Exception {
        return comprasNumeracionDao.getListaInvNumeracionCompraTO(empresa, periodo, motivo);
    }

}
