package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClientesVentasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientesVentasDetalle;

@Repository
public class ClientesVentasDetalleDaoImpl extends GenericDaoImpl<InvClientesVentasDetalle, Integer> implements ClientesVentasDetalleDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<InvClientesVentasDetalle> listarInvClientesVentasDetalle(String cliEmpresa, String cliCodigo, int grupo) throws Exception {
        String sql = "select * from inventario.inv_clientes_ventas_detalle where cli_empresa = '" + cliEmpresa + "' and cli_codigo = '" + cliCodigo + "'";
        if (grupo > 0) {
            sql = sql + " and det_grupo = " + grupo;
        }
        return genericSQLDao.obtenerPorSql(sql, InvClientesVentasDetalle.class);
    }

    @Override
    public List<InvClientesVentasDetalleTO> listarInvClientesVentasDetalleTO(String cliEmpresa, String cliCodigo, int grupo) throws Exception {
        String sql = "select * from inventario.inv_clientes_ventas_detalle d"
                + " inner join inventario.inv_producto p on p.pro_empresa = d.pro_empresa and p.pro_codigo_principal = d.pro_codigo_principal"
                + " where d.cli_empresa = '" + cliEmpresa + "' and d.cli_codigo = '" + cliCodigo + "'";
        if (grupo > 0) {
            sql = sql + " and d.det_grupo = " + grupo;
        }
        return genericSQLDao.obtenerPorSql(sql, InvClientesVentasDetalleTO.class);
    }
}
