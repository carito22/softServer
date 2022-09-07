package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasDetalleDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleVentasTO;

@Service
public class VentasDetalleServiceImpl implements VentasDetalleService {

    @Autowired
    private VentasDetalleDao ventasDetalleDao;

    @Override
    public List<InvListaDetalleVentasTO> getListaInvVentasDetalleTO(String empresa, String periodo, String motivo,
            String numeroVentas) throws Exception {
        return ventasDetalleDao.getListaInvVentasDetalleTO(empresa, periodo, motivo, numeroVentas);
    }

}
