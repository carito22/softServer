package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;


import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdAmbienteProduccion;

import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface AmbienteProduccionDao extends GenericDao<PrdAmbienteProduccion, Integer> {

    public boolean insertarAmbienteProduccion(PrdAmbienteProduccion prdAmbienteProduccion, SisSuceso sisSuceso) throws Exception;

    public List<PrdAmbienteProduccion> getListarPrdAmbienteProduccion(String empresa) throws Exception;
    
    public boolean modificarAmbienteProduccion (PrdAmbienteProduccion prdAmbienteProduccion, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarAmbienteProduccion(PrdAmbienteProduccion prdAmbienteProduccion, SisSuceso sisSuceso) throws Exception;
}
