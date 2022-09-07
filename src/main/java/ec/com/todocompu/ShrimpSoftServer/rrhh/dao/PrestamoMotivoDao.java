package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhPrestamoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhPrestamoMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface PrestamoMotivoDao extends GenericDao<RhPrestamoMotivo, RhPrestamoMotivoPK> {

	public boolean insertarRhPrestamoMotivo(RhPrestamoMotivo rhPrestamoMotivo, SisSuceso sisSuceso) throws Exception;

	public boolean modificarRhPrestamoMotivo(RhPrestamoMotivo rhPrestamoMotivo, SisSuceso sisSuceso) throws Exception;

	public boolean eliminarRhPrestamoMotivo(RhPrestamoMotivo rhPrestamoMotivo, SisSuceso sisSuceso) throws Exception;

	public RhPrestamoMotivo getRhPrestamoMotivo(String empresa, String detalle) throws Exception;

	public List<RhPrestamoMotivo> getListaRhPrestamoMotivo(String empresa) throws Exception;
        
        public List<RhPrestamoMotivo> getListaRhPrestamoMotivos(String empresa, boolean inactivo) throws Exception;

	public boolean banderaEliminarPrestamoMotivo(String empresa, String detalle) throws Exception;

}
