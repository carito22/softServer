package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasDetalleDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasPK;

@Service
public class ComprasDetalleServiceImpl implements ComprasDetalleService {

    @Autowired
    private ComprasDetalleDao comprasDetalleDao;

    @Override
    public List<InvListaDetalleComprasTO> getListaInvCompraDetalleTO(String empresa, String periodo, String motivo, String numeroCompra) throws Exception {
        return comprasDetalleDao.getListaInvCompraDetalleTO(empresa, periodo, motivo, numeroCompra);
    }

    @Override
    public List<InvListaDetalleComprasTO> getListaInvCompraDetalleTO(InvComprasPK pk) throws Exception {
        return comprasDetalleDao.getListaInvCompraDetalleTO(pk.getCompEmpresa(), pk.getCompPeriodo(), pk.getCompMotivo(), pk.getCompNumero());
    }

    @Override
    public List<InvComprasDetalle> obtenerCompraDetallePorNumero(String empresa, String periodo, String motivo, String numero) throws Exception {
        return comprasDetalleDao.obtenerCompraDetallePorNumero(empresa, periodo, motivo, numero);
    }

}
