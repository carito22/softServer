package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

@Repository
public class PedidosMotivoDaoImpl extends GenericDaoImpl<InvPedidosMotivo, InvPedidosMotivoPK>
        implements PedidosMotivoDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public boolean eliminarInvPedidosMotivo(InvPedidosMotivo invPedidosMotivo, SisSuceso sisSuceso) throws Exception {
        eliminar(invPedidosMotivo);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public InvPedidosMotivo getInvPedidosMotivo(String empresa, String sector, String codigo) throws Exception {
        return obtenerPorId(InvPedidosMotivo.class, new InvPedidosMotivoPK(empresa, sector, codigo));
    }

    @Override
    public InvPedidosMotivo getInvPedidosMotivoPorAtributo(String empresa, String atributo, String valorAtributo) throws Exception {
        return obtenerPorAtributo(InvPedidosMotivo.class, atributo, valorAtributo, null);
    }

    @Override
    public boolean insertarInvPedidosMotivo(InvPedidosMotivo invPedidosMotivo, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        insertar(invPedidosMotivo);
        return true;
    }

    @Override
    public boolean modificarInvPedidosMotivo(InvPedidosMotivo invPedidosMotivo, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        actualizar(invPedidosMotivo);
        return true;
    }

    @Override
    public List<InvPedidosMotivo> getListaInvPedidosMotivo(String empresa, boolean filtrarInactivos) throws Exception {
        if (filtrarInactivos) {
            return obtenerPorHql("select c from InvPedidosMotivo c where c.invPedidosMotivoPK.pmEmpresa=?1 and c.pmInactivo=?2 order by c.invPedidosMotivoPK.pmCodigo", new Object[]{empresa, false});
        } else {
            return obtenerPorHql("select c from InvPedidosMotivo c where c.invPedidosMotivoPK.pmEmpresa=?1 order by c.invPedidosMotivoPK.pmCodigo", new Object[]{empresa});
        }

    }
}
