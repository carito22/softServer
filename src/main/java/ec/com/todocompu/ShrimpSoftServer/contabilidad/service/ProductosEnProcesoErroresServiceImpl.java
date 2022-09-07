package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.InventarioProductosCuentasInconsistentes;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.InventarioProductosEnProcesoErroresCompra;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.InventarioProductosEnProcesoErroresConsumo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCorridasInconsistentesTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductosEnProcesoErroresServiceImpl implements ProductosEnProcesoErroresService {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<InventarioProductosEnProcesoErroresCompra> listarPppErroresCompra(String empresa, String desde, String hasta) throws Exception {
        String sql = "SELECT * FROM contabilidad.fun_inventario_productos_proceso_errores_compras('" + empresa + "', '" + desde + "', '" + hasta + "')";
        return genericSQLDao.obtenerPorSql(sql, InventarioProductosEnProcesoErroresCompra.class);
    }

    @Override
    public List<InventarioProductosEnProcesoErroresConsumo> listarPppErroresConsumo(String empresa, String desde, String hasta) throws Exception {
        String sql = "SELECT * FROM contabilidad.fun_inventario_productos_proceso_errores_consumos('" + empresa + "', '" + desde + "', '" + hasta + "')";
        return genericSQLDao.obtenerPorSql(sql, InventarioProductosEnProcesoErroresConsumo.class);
    }
    
    @Override
    public List<InventarioProductosCuentasInconsistentes> productosCuentasInconsitentes(String empresa) throws Exception {
        String sql = "SELECT * FROM inventario.fun_productos_cuentas_inconsistentes('" + empresa + "')";
        return genericSQLDao.obtenerPorSql(sql, InventarioProductosCuentasInconsistentes.class);
    }

    @Override
    public List<PrdCorridasInconsistentesTO> listarCorridasInconsistentes(String empresa, String fechaInicio, String fechaFin) throws Exception {
        String sql = "SELECT * FROM produccion.fun_corridas_inconsistentes('" + empresa + "', '" + fechaInicio + "', '" + fechaFin + "')";
        return genericSQLDao.obtenerPorSql(sql, PrdCorridasInconsistentesTO.class);
    }

}
