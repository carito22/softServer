package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasRecepcionDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasRecepcionTO;

@Service
public class ComprasRecepcionServiceImpl implements ComprasRecepcionService {

    @Autowired
    private ComprasRecepcionDao comprasRecepcionDao;

    @Override
    public InvComprasRecepcionTO getInvComprasRecepcionTO(String empresa, String periodo, String motivo, String numero)
            throws Exception {
        return comprasRecepcionDao.getInvComprasRecepcionTO(empresa, periodo, motivo, numero);
    }

}
