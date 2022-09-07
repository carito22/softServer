package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface VentasMotivoService {

    public List<InvVentaMotivoComboTO> getListaVentaMotivoComboTO(String empresa, boolean filtrarInactivos) throws Exception;

    public InvVentaMotivoTO getInvVentasMotivoTO(String empresa, String vmCodigo) throws Exception;

    public List<InvVentaMotivoTablaTO> getListaInvVentasMotivoTablaTO(String empresa) throws Exception;

    public List<InvVentaMotivoTO> getListaInvVentasMotivoTO(String empresa, boolean soloActivos, String tipoDocumento) throws Exception;

    public String insertarInvVentasMotivoTO(InvVentaMotivoTO invVentaMotivoTO, SisInfoTO sisInfoTO) throws Exception;

    public String modificarInvVentasMotivoTO(InvVentaMotivoTO invVentaMotivoTO, SisInfoTO sisInfoTO) throws Exception;

    public String modificarEstadoInvVentaMotivoTO(InvVentaMotivoTO invVentaMotivoTO, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarInvVentasMotivoTO(InvVentaMotivoTO invVentaMotivoTO, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> obtenerDatosParaCrudMotivoVentas(Map<String, Object> map) throws Exception;

}
