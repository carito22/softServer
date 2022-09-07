package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientesDirecciones;

@Repository
public class ClientesDireccionesDaoImpl extends GenericDaoImpl<InvClientesDirecciones, Integer> implements ClientesDireccionesDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<InvClientesDirecciones> listarInvClientesDirecciones(String dirEmpresa, String dirCodigo) throws Exception {
        String sql = "select * from inventario.inv_clientes_direcciones where cli_empresa = '" + dirEmpresa + "' and cli_codigo = '" + dirCodigo + "'";
        return genericSQLDao.obtenerPorSql(sql, InvClientesDirecciones.class);
    }
    
    @Override
    public InvClientesDirecciones obtenerDireccion(String dirEmpresa, String dirCodigo, String cliente) throws Exception {
        String sql = "select * from inventario.inv_clientes_direcciones where cli_empresa = '" + dirEmpresa + "' and dir_codigo_establecimiento = '" + dirCodigo + "' and cli_codigo = '" + cliente + "'";
        return genericSQLDao.obtenerObjetoPorSql(sql, InvClientesDirecciones.class);
    }

}
