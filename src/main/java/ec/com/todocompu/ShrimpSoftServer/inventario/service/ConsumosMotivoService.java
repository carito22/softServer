package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsumosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface ConsumosMotivoService {

    public List<InvConsumosMotivoComboTO> getListaConsumosMotivoComboTO(String empresa, boolean filtrarInactivos) throws Exception;

    public List<InvConsumosMotivo> getListaConsumosMotivo(String empresa, boolean filtrarInactivos) throws Exception;

    public List<InvListaConsumosMotivoTO> getInvListaConsumosMotivoTO(String empresa) throws Exception;

    public InvConsumosMotivoTO getInvConsumoMotivoTO(String empresa, String cmCodigo) throws Exception;

    public String accionInvConsumosMotivo(InvConsumosMotivoTO invConsumosMotivoTO, char accion, SisInfoTO sisInfoTO) throws Exception;

    public String modificarEstadoInvConsumosMotivoTO(InvConsumosMotivoTO invConsumosMotivoTO, SisInfoTO sisInfoTO) throws Exception;

    public boolean tieneMovimientosConsumosMotivo(String empresa, String motivo) throws Exception;

    /*Lista InvConsumosMotivoTO*/
    public List<InvConsumosMotivoTO> getInvListaConsumoMotivoTO(String empresa, boolean filtrarInactivos, String busqueda) throws Exception;

    public Map<String, Object> obtenerDatosParaMotivoConsumos(Map<String, Object> map) throws Exception;

}
