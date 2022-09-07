package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsumosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ConsumosMotivoDao extends GenericDao<InvConsumosMotivo, InvConsumosMotivoPK> {

    public Boolean accionInvConsumosMotivo(InvConsumosMotivo invConsumosMotivo, SisSuceso sisSuceso, char accion) throws Exception;

    public InvConsumosMotivo buscarInvConsumosMotivo(String empresa, String codigo) throws Exception;

    public InvConsumosMotivoTO getInvConsumoMotivoTO(String empresa, String cmCodigo) throws Exception;

    public Boolean comprobarInvConsumosMotivo(String empresa, String motCodigo) throws Exception;

    public List<InvListaConsumosMotivoTO> getInvListaConsumosMotivoTO(String empresa) throws Exception;

    public List<InvConsumosMotivoComboTO> getListaConsumosMotivoComboTO(String empresa, boolean filtrarInactivos) throws Exception;

    public List<InvConsumosMotivo> getListaConsumosMotivo(String empresa, boolean filtrarInactivos) throws Exception;

    public boolean retornoContadoEliminarConsumosMotivo(String empresa, String motivo) throws Exception;

    /*Lista InvConsumosMotivoTO*/
    public List<InvConsumosMotivoTO> getInvListaConsumoMotivoTO(String empresa, boolean filtrarInactivos, String busqueda) throws Exception;
}
