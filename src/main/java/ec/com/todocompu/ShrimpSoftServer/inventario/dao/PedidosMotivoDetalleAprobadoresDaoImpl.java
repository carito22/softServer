package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoDetalleAprobadores;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class PedidosMotivoDetalleAprobadoresDaoImpl extends GenericDaoImpl<InvPedidosMotivoDetalleAprobadores, Integer> implements PedidosMotivoDetalleAprobadoresDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public boolean eliminarInvPedidosMotivoDetalleAprobadores(InvPedidosMotivoDetalleAprobadores invPedidosMotivoDetalleAprobadores, SisSuceso sisSuceso) throws Exception {
        eliminar(invPedidosMotivoDetalleAprobadores);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public InvPedidosMotivoDetalleAprobadores getInvPedidosMotivoDetalleAprobadores(Integer codigo) throws Exception {
        return obtenerPorId(InvPedidosMotivoDetalleAprobadores.class, codigo);
    }

    @Override
    public boolean insertarInvPedidosMotivoDetalleAprobadores(InvPedidosMotivoDetalleAprobadores invPedidosMotivoDetalleAprobadores, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        insertar(invPedidosMotivoDetalleAprobadores);
        return true;
    }

    @Override
    public boolean modificarInvPedidosMotivoDetalleAprobadores(InvPedidosMotivoDetalleAprobadores invPedidosMotivoDetalleAprobadores, SisSuceso sisSuceso) throws Exception {

        sucesoDao.insertar(sisSuceso);
        actualizar(invPedidosMotivoDetalleAprobadores);
        return true;
    }

    @Override
    public List<InvPedidosMotivoDetalleAprobadores> getListaInvPedidosMotivoDetalleAprobadores(InvPedidosMotivoPK invPedidosMotivoPK) throws Exception {
        return obtenerPorHql("select c from InvPedidosMotivoDetalleAprobadores c inner join c.invPedidosMotivo sec where sec.invPedidosMotivoPK.pmEmpresa=?1 and sec.invPedidosMotivoPK.pmCodigo=?2 and sec.invPedidosMotivoPK.pmSector=?3", new Object[]{invPedidosMotivoPK.getPmEmpresa(), invPedidosMotivoPK.getPmCodigo(), invPedidosMotivoPK.getPmSector()});
    }

}
