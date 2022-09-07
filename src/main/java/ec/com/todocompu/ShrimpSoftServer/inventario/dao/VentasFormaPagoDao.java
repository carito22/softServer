package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaVentasFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasFormaCobro;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface VentasFormaPagoDao extends GenericDao<InvVentasFormaCobro, Integer> {

    public Boolean accionInvVentasFormaCobro(InvVentasFormaCobro invVentasFormaPago, SisSuceso sisSuceso, char accion) throws Exception;

    public InvVentasFormaCobro buscarVentasFormaPago(Integer secuencial) throws Exception;

    public InvVentasFormaPagoTO getInvVentasFormaCobro(String empresa, String ctaCodigo) throws Exception;

    public Boolean buscarInvVentasFormaCobro(String ctaContable, String empresa) throws Exception;

    public List<InvComboFormaPagoTO> getComboFormaPagoVenta(String empresa) throws Exception;

    public String getCuentaSectorFormaPagoVenta(String empresa, String detalleFormaPago) throws Exception;

    public List<InvListaVentasFormaPagoTO> getInvListaVentasFormaPagoTO(String empresa) throws Exception;

    public List<InvVentasFormaPagoTO> getListaInvVentasFormaPagoTO(String empresa, boolean incluirInactivos) throws Exception;
}
