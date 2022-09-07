package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRolMotivo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRolMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface RolMotivoDao extends GenericDao<RhRolMotivo, RhRolMotivoPK> {

	public boolean insertarRhRolMotivo(RhRolMotivo rhRolMotivo, SisSuceso sisSuceso) throws Exception;

	public boolean modificarRhRolMotivo(RhRolMotivo rhRolMotivo, SisSuceso sisSuceso) throws Exception;

	public boolean eliminarRhRolMotivo(RhRolMotivo rhRolMotivo, SisSuceso sisSuceso) throws Exception;

	public RhRolMotivo getRhRolMotivo(String empresa, String detalle) throws Exception;
        
        public RhRolMotivo obtenerMotivoPorTipoDecomprobante(String empresa, String tipoComprobante) throws Exception;

	public List<RhRolMotivo> getListaRhRolMotivo(String empresa) throws Exception;
        
        public List<RhRolMotivo> getListaRhRolMotivos(String empresa, boolean inactivo) throws Exception;

	public boolean banderaEliminarRolMotivo(String empresa, String detalle) throws Exception;

}
