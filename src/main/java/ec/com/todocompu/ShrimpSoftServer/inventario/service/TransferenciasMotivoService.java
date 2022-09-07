package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaTransferenciaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciaMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface TransferenciasMotivoService {

    public List<InvTransferenciaMotivoComboTO> getListaTransferenciaMotivoComboTO(String empresa, boolean filtrarInactivos) throws Exception;

    public List<InvListaTransferenciaMotivoTO> getInvListaTransferenciaMotivoTO(String empresa) throws Exception;

    public InvTransferenciaMotivoTO getInvTransferenciaMotivoTO(String empresa, String tmCodigo) throws Exception;

    public String accionInvTransferenciaMotivo(InvTransferenciaMotivoTO invTransferenciaMotivoTO, char accion, SisInfoTO sisInfoTO) throws Exception;

    public String modificarEstadoInvTransferenciaMotivoTO(InvTransferenciaMotivoTO invTransferenciaMotivoTO, SisInfoTO sisInfoTO) throws Exception;

    /*Lista InvTransferenciaMotivoTO*/
    public List<InvTransferenciaMotivoTO> getListaTransferenciaMotivoTO(String empresa, boolean incluirInactivos) throws Exception;

}
