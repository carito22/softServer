package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleTransferenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasDetalle;

public interface TransferenciasDetalleDao extends GenericDao<InvTransferenciasDetalle, Integer> {

    public List<InvListaDetalleTransferenciaTO> getListaInvTransferenciasDetalleTO(String empresa, String periodo,
            String motivo, String numeroTrans) throws Exception;

    public List<InvTransferenciasDetalle> obtenerTransferenciaDetallePorNumero(String empresa, String periodo,
            String motivo, String numeroTrans) throws Exception;

}
