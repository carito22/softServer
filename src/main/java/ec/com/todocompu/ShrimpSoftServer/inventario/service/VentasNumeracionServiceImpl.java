package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasNumeracionDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvNumeracionVentaTO;

@Service
public class VentasNumeracionServiceImpl implements VentasNumeracionService {

    @Autowired
    private VentasNumeracionDao ventasNumeracionDao;

    @Override
    public List<InvNumeracionVentaTO> getListaInvNumeracionVentaTO(String empresa, String periodo, String motivo)
            throws Exception {
        return ventasNumeracionDao.getListaInvNumeracionVentaTO(empresa, periodo, motivo);
    }

}
