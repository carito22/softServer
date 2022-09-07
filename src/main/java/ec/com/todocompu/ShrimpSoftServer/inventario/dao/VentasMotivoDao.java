package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface VentasMotivoDao extends GenericDao<InvVentasMotivo, InvVentasMotivoPK> {

    public boolean insertarInvVentasMotivo(InvVentasMotivo invVentasMotivo, SisSuceso sisSuceso) throws Exception;

    public boolean modificarInvVentasMotivo(InvVentasMotivo invVentasMotivo, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarInvVentasMotivo(InvVentasMotivo invVentasMotivo, SisSuceso sisSuceso) throws Exception;

    public InvVentasMotivo getInvVentasMotivo(String empresa, String codigo) throws Exception;

    public InvVentaMotivoTO getInvVentasMotivoTO(String empresa, String vmCodigo) throws Exception;

    public Boolean comprobarInvVentasMotivo(String empresa, String motCodigo) throws Exception;

    public List<InvVentaMotivoTablaTO> getListaInvVentasMotivoTablaTO(String empresa) throws Exception;

    public List<InvVentaMotivoComboTO> getListaVentaMotivoComboTO(String empresa, boolean filtrarInactivos) throws Exception;

    public List<InvVentaMotivoTO> getListaInvVentasMotivoTO(String empresa, boolean soloActivos, String tipoDocumento) throws Exception;

    public boolean retornoContadoEliminarVentasMotivo(String empresa, String motivo) throws Exception;

}
