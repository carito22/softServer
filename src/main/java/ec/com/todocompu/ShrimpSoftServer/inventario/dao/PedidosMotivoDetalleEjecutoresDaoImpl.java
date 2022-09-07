package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoDetalleEjecutores;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class PedidosMotivoDetalleEjecutoresDaoImpl extends GenericDaoImpl<InvPedidosMotivoDetalleEjecutores, Integer> implements PedidosMotivoDetalleEjecutoresDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public boolean eliminarInvPedidosMotivoDetalleEjecutores(InvPedidosMotivoDetalleEjecutores invPedidosMotivoDetalleEjecutores, SisSuceso sisSuceso) throws Exception {
        eliminar(invPedidosMotivoDetalleEjecutores);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public InvPedidosMotivoDetalleEjecutores getInvPedidosMotivoDetalleEjecutores(Integer codigo) throws Exception {
        return obtenerPorId(InvPedidosMotivoDetalleEjecutores.class, codigo);
    }

    @Override
    public boolean insertarInvPedidosMotivoDetalleEjecutores(InvPedidosMotivoDetalleEjecutores invPedidosMotivoDetalleEjecutores, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        insertar(invPedidosMotivoDetalleEjecutores);
        return true;
    }

    @Override
    public boolean modificarInvPedidosMotivoDetalleEjecutores(InvPedidosMotivoDetalleEjecutores invPedidosMotivoDetalleEjecutores, SisSuceso sisSuceso) throws Exception {

        sucesoDao.insertar(sisSuceso);
        actualizar(invPedidosMotivoDetalleEjecutores);
        return true;
    }

    @Override
    public List<InvPedidosMotivoDetalleEjecutores> getListaInvPedidosMotivoDetalleEjecutores(InvPedidosMotivoPK invPedidosMotivoPK) throws Exception {
        return obtenerPorHql("select c from InvPedidosMotivoDetalleEjecutores c inner join c.invPedidosMotivo sec where sec.invPedidosMotivoPK.pmEmpresa=?1 and sec.invPedidosMotivoPK.pmCodigo=?2 and sec.invPedidosMotivoPK.pmSector=?3", new Object[]{invPedidosMotivoPK.getPmEmpresa(), invPedidosMotivoPK.getPmCodigo(), invPedidosMotivoPK.getPmSector()});
    }

}
