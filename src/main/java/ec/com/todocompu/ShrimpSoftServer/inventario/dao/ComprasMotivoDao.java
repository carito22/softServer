package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCompraMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCompraMotivoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ComprasMotivoDao extends GenericDao<InvComprasMotivo, InvComprasMotivoPK> {

    public boolean eliminarInvComprasMotivo(InvComprasMotivo invComprasMotivo, SisSuceso sisSuceso) throws Exception;

    public InvComprasMotivo getInvComprasMotivo(String empresa, String codigo) throws Exception;

    public InvComprasMotivoTO getInvComprasMotivoTO(String empresa, String cmCodigo) throws Exception;

    public boolean insertarInvComprasMotivo(InvComprasMotivo invComprasMotivo, SisSuceso sisSuceso) throws Exception;

    public boolean modificarInvComprasMotivo(InvComprasMotivo invComprasMotivo, SisSuceso sisSuceso) throws Exception;

    public Boolean comprobarInvComprasMotivo(String empresa, String motCodigo) throws Exception;

    public List<InvCompraMotivoComboTO> getListaCompraMotivoComboTO(String empresa, boolean filtrarInactivos) throws Exception;

    public List<InvCompraMotivoTablaTO> getListaInvComprasMotivoTablaTO(String empresa) throws Exception;

    public List<InvComprasMotivoTO> getListaInvComprasMotivoTO(String empresa, boolean soloActivos) throws Exception;

    public boolean retornoContadoEliminarComprasMotivo(String empresa, String motivo) throws Exception;

    public List<InvComprasMotivoTO> listarInvComprasMotivoTOAjusteInv(String empresa, boolean soloActivos) throws Exception;
}
