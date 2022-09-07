package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdProyeccionTO;

@Transactional
public interface ProyeccionService {

	public List<PrdProyeccionTO> getListaPrdProyeccion(String empresa, String sector, Date fecha);
}
