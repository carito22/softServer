package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoDetalleRegistradores;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class PedidosMotivoDetalleRegistradoresDaoImpl extends GenericDaoImpl<InvPedidosMotivoDetalleRegistradores, Integer> implements PedidosMotivoDetalleRegistradoresDao {

    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public boolean eliminarInvPedidosMotivoDetalleRegistradores(InvPedidosMotivoDetalleRegistradores invPedidosMotivoDetalleRegistradores, SisSuceso sisSuceso) throws Exception {
        eliminar(invPedidosMotivoDetalleRegistradores);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public InvPedidosMotivoDetalleRegistradores getInvPedidosMotivoDetalleRegistradores(Integer codigo) throws Exception {
        return obtenerPorId(InvPedidosMotivoDetalleRegistradores.class, codigo);
    }

    @Override
    public boolean insertarInvPedidosMotivoDetalleRegistradores(InvPedidosMotivoDetalleRegistradores invPedidosMotivoDetalleRegistradores, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        insertar(invPedidosMotivoDetalleRegistradores);
        return true;
    }

    @Override
    public boolean modificarInvPedidosMotivoDetalleRegistradores(InvPedidosMotivoDetalleRegistradores invPedidosMotivoDetalleRegistradores, SisSuceso sisSuceso) throws Exception {

        sucesoDao.insertar(sisSuceso);
        actualizar(invPedidosMotivoDetalleRegistradores);
        return true;
    }

    @Override
    public List<InvPedidosMotivoDetalleRegistradores> getListaInvPedidosMotivoDetalleRegistradores(InvPedidosMotivoPK invPedidosMotivoPK) throws Exception {
        return obtenerPorHql("select c from InvPedidosMotivoDetalleRegistradores c inner join c.invPedidosMotivo sec where sec.invPedidosMotivoPK.pmEmpresa=?1 and sec.invPedidosMotivoPK.pmCodigo=?2 and sec.invPedidosMotivoPK.pmSector=?3", new Object[]{invPedidosMotivoPK.getPmEmpresa(), invPedidosMotivoPK.getPmCodigo(), invPedidosMotivoPK.getPmSector()});
    }

}
