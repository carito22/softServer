package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.ProyeccionDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdProyeccionTO;

@Service
public class ProyeccionServiceImpl implements ProyeccionService {

    @Autowired
    private ProyeccionDao proyeccionDao;

    @Override
    public List<PrdProyeccionTO> getListaPrdProyeccion(String empresa, String sector, Date fecha) {
        return proyeccionDao.getListaPrdProyeccion(empresa, sector, fecha);
    }
}
