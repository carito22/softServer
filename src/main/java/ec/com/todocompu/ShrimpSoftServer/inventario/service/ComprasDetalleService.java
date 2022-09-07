package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasPK;

@Transactional
public interface ComprasDetalleService {

    public List<InvListaDetalleComprasTO> getListaInvCompraDetalleTO(String empresa, String periodo, String motivo, String numeroCompra) throws Exception;
    
    public List<InvListaDetalleComprasTO> getListaInvCompraDetalleTO(InvComprasPK pk) throws Exception;

    public List<InvComprasDetalle> obtenerCompraDetallePorNumero(String empresa, String periodo, String motivo, String numero) throws Exception;
}
