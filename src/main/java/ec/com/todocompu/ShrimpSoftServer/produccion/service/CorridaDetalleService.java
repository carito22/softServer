package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorridaDetalle;

@Transactional
public interface CorridaDetalleService {

    public List<PrdCorridaDetalle> getCorridaDetalleOrigenPorCorrida(String empresa, String sector, String piscina,
            String corrida);

    public List<PrdCorridaDetalle> getCorridaDetalleDestinoPorCorrida(String empresa, String sector, String piscina,
            String corrida);

}
