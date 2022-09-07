package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalle;

public interface ComprasDetalleDao extends GenericDao<InvComprasDetalle, Integer> {

    public List<InvListaDetalleComprasTO> getListaInvCompraDetalleTO(String empresa, String periodo, String motivo, String numeroCompra) throws Exception;

    public List<InvComprasDetalle> obtenerCompraDetallePorNumero(String empresa, String periodo, String motivo, String numeroCompra) throws Exception;
    
    public void actualizarPorSql(Long detSecuencial) throws Exception;
}
