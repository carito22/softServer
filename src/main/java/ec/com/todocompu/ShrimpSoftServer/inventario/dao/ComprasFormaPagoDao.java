package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaComprasFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasFormaPago;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ComprasFormaPagoDao extends GenericDao<InvComprasFormaPago, Integer> {

    public Boolean accionInvComprasFormaPago(InvComprasFormaPago invComprasFormaPago, SisSuceso sisSuceso, char accion) throws Exception;

    public InvComprasFormaPago buscarComprasFormaPago(Integer secuencial) throws Exception;

    public InvComprasFormaPagoTO getInvComprasFormaPago(String empresa, String ctaCodigo) throws Exception;

    public Boolean buscarInvComprasFormaPago(String ctaContable, String empresa,  Integer secuencial) throws Exception;

    public List<InvComboFormaPagoTO> getComboFormaPagoCompra(String empresa) throws Exception;

    public String getCuentaSectorFormaPagoCompra(String empresa, String detalleFormaPago) throws Exception;

    public List<InvListaComprasFormaPagoTO> getInvListaComprasFormaPagoTO(String empresa) throws Exception;

    public List<InvComprasFormaPagoTO> getInvListaInvComprasFormaPagoTO(String empresa, boolean inactivos) throws Exception;
}
