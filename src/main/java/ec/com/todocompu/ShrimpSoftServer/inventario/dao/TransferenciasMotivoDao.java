package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaTransferenciaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciaMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface TransferenciasMotivoDao extends GenericDao<InvTransferenciasMotivo, InvTransferenciasMotivoPK> {

    public Boolean accionInvTransferenciasMotivo(InvTransferenciasMotivo invTransferenciasMotivo, SisSuceso sisSuceso, char accion) throws Exception;

    public InvTransferenciasMotivo buscarInvTransferenciaMotivo(String empresa, String codigo) throws Exception;

    public InvTransferenciaMotivoTO getInvTransferenciaMotivoTO(String empresa, String tmCodigo) throws Exception;

    public Boolean comprobarInvTransferenciaMotivo(String empresa, String motCodigo) throws Exception;

    public List<InvListaTransferenciaMotivoTO> getInvListaTransferenciaMotivoTO(String empresa) throws Exception;

    public List<InvTransferenciaMotivoComboTO> getListaTransferenciaMotivoComboTO(String empresa, boolean filtrarInactivos) throws Exception;

    public boolean retornoContadoEliminarTransferenciasMotivo(String empresa, String motivo) throws Exception;

    /*Lista InvTransferenciaMotivoTO*/
    public List<InvTransferenciaMotivoTO> getListaTransferenciaMotivoTO(String empresa, boolean incluirInactivos) throws Exception;
}
