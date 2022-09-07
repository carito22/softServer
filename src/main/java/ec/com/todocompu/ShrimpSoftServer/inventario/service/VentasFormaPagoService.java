package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaVentasFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface VentasFormaPagoService {

    public List<InvComboFormaPagoTO> getComboFormaPagoVenta(String empresa) throws Exception;

    public List<InvVentasFormaPagoTO> getListaInvVentasFormaPagoTO(String empresa, boolean inactivos) throws Exception;

    public List<InvListaVentasFormaPagoTO> getInvListaVentasFormaPagoTO(String empresa) throws Exception;

    public InvVentasFormaPagoTO getInvVentasFormaPagoTO(String empresa, String ctaCodigo) throws Exception;

    public String accionInvVentasPagosForma(InvVentasFormaPagoTO invVentasFormaPagoTO, char accion, SisInfoTO sisInfoTO) throws Exception;

    public String modificarEstadoInvVentasPagosForma(InvVentasFormaPagoTO invVentasFormaPagoTO, SisInfoTO sisInfoTO) throws Exception;
}
