package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.CorridaDetalleDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorridaDetalle;

@Service
public class CorridaDetalleServiceImpl implements CorridaDetalleService {

    @Autowired
    private CorridaDetalleDao corridaDetalleDao;

    @Override
    public List<PrdCorridaDetalle> getCorridaDetalleOrigenPorCorrida(String empresa, String sector, String piscina,
            String corrida) {
        return corridaDetalleDao.getCorridaDetalleOrigenPorCorrida(empresa, sector, piscina, corrida);
    }

    @Override
    public List<PrdCorridaDetalle> getCorridaDetalleDestinoPorCorrida(String empresa, String sector, String piscina,
            String corrida) {
        return corridaDetalleDao.getCorridaDetalleDestinoPorCorrida(empresa, sector, piscina, corrida);
    }
    
}
