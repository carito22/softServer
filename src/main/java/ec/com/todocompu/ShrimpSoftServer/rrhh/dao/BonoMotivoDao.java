package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhBonoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhBonoMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface BonoMotivoDao extends GenericDao<RhBonoMotivo, RhBonoMotivoPK> {

	public boolean insertarRhBonoMotivo(RhBonoMotivo rhBonoMotivo, SisSuceso sisSuceso) throws Exception;

	public boolean modificarRhBonoMotivo(RhBonoMotivo rhBonoMotivo, SisSuceso sisSuceso) throws Exception;

	public boolean eliminarRhBonoMotivo(RhBonoMotivo rhBonoMotivo, SisSuceso sisSuceso) throws Exception;

	public RhBonoMotivo getRhBonoMotivo(String empresa, String detalle) throws Exception;

	public List<RhBonoMotivo> getListaRhBonoMotivo(String empresa) throws Exception;
        
        public List<RhBonoMotivo> getListaRhBonoMotivos(String empresa, boolean inactivo) throws Exception;

	public boolean banderaEliminarBonoMotivo(String empresa, String detalle) throws Exception;

}
