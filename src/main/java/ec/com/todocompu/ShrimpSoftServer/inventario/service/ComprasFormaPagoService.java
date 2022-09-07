package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaComprasFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface ComprasFormaPagoService {

    public List<InvComboFormaPagoTO> getComboFormaPagoCompra(String empresa) throws Exception;

    public List<InvListaComprasFormaPagoTO> getInvListaComprasFormaPagoTO(String empresa) throws Exception;

    public List<InvComprasFormaPagoTO> getInvListaInvComprasFormaPagoTO(String empresa, boolean inactivos) throws Exception;

    public InvComprasFormaPagoTO getInvComprasFormaPagoTO(String empresa, String ctaCodigo) throws Exception;

    public String accionInvComprasPagosForma(InvComprasFormaPagoTO invComprasFormaPagoTO, char accion, SisInfoTO sisInfoTO) throws Exception;

    public String modificarEstadoInvComprasPagosForma(InvComprasFormaPagoTO invComprasFormaPagoTO, SisInfoTO sisInfoTO) throws Exception;
}
