package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoProveedorDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListadoProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorSinMovimientoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvNumeracionVarios;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedor;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoProveedor;

@Repository
public class ProveedorDaoImpl extends GenericDaoImpl<InvProveedor, InvProveedorPK> implements ProveedorDao {

    @Autowired
    private NumeracionVariosDao numeracionVariosDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoProveedorDao sucesoProveedorDao;

    @Override
    public InvProveedor buscarInvProveedor(String empresa, String codigoProveedor) throws Exception {
        return obtenerPorId(InvProveedor.class, new InvProveedorPK(empresa, codigoProveedor));
    }

    @Override
    public boolean eliminarInvProveedor(InvProveedor InvProveedor, SisSuceso sisSuceso) throws Exception {

        eliminar(InvProveedor);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean insertarInvProveedor(InvProveedor invProveedor, SisSuceso sisSuceso,
            InvNumeracionVarios invNumeracionVarios) throws Exception {
        insertar(invProveedor);
        sucesoDao.insertar(sisSuceso);
        //suceso personalizado 
        SisSucesoProveedor sucesoProveedor = new SisSucesoProveedor();
        String json = UtilsJSON.objetoToJson(invProveedor);
        sucesoProveedor.setSusJson(json);
        sucesoProveedor.setSisSuceso(sisSuceso);
        sucesoProveedor.setInvProveedor(invProveedor);
        sucesoProveedorDao.insertar(sucesoProveedor);
        //****
        if (invNumeracionVarios != null) {

            numeracionVariosDao.actualizar(invNumeracionVarios);
        }
        return true;
    }

    @Override
    public boolean modificarInvProveedor(InvProveedor invProveedor, SisSuceso sisSuceso) throws Exception {
        actualizar(invProveedor);
        sucesoDao.insertar(sisSuceso);
        //suceso personalizado 
        SisSucesoProveedor sucesoProveedor = new SisSucesoProveedor();
        String json = UtilsJSON.objetoToJson(invProveedor);
        sucesoProveedor.setSusJson(json);
        sucesoProveedor.setSisSuceso(sisSuceso);
        sucesoProveedor.setInvProveedor(invProveedor);
        sucesoProveedorDao.insertar(sucesoProveedor);
        //****
        return true;
    }

    @Override
    public boolean modificarInvProveedorLlavePrincipal(InvProveedor invProveedorEliminar, InvProveedor invProveedor,
            SisSuceso sisSuceso) throws Exception {
        eliminar(invProveedorEliminar);
        actualizar(invProveedor);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public String getInvProximaNumeracionProveedor(String empresa, InvProveedorTO invProveedorTO) throws Exception {
        String sql = "SELECT num_proveedores FROM " + "inventario.inv_numeracion_varios WHERE (num_empresa = '"
                + empresa + "')";

        String consulta = (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
        if (consulta != null) {
            invProveedorTO.setProvCodigo(consulta);
        } else {
            invProveedorTO.setProvCodigo("00000");
        }

        int numeracion = Integer.parseInt(invProveedorTO.getProvCodigo());
        String rellenarConCeros = "";
        do {
            numeracion++;
            int numeroCerosAponer = 5 - String.valueOf(numeracion).trim().length();
            rellenarConCeros = "";
            for (int i = 0; i < numeroCerosAponer; i++) {
                rellenarConCeros = rellenarConCeros + "0";
            }

            invProveedorTO.setProvCodigo(rellenarConCeros + numeracion);

        } while (buscarInvProveedor(invProveedorTO.getEmpCodigo(), invProveedorTO.getProvCodigo()) != null);
        return rellenarConCeros + numeracion;
    }

    @Override
    public Boolean buscarConteoProveedor(String empCodigo, String codigoProveedor) throws Exception {
        String sql = "SELECT * FROM " + "inventario.fun_sw_proveedor_eliminar('" + empCodigo + "', '" + codigoProveedor
                + "')";

        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

    @Override
    public boolean comprobarInvAplicaRetencionIva(String empresa, String codigo) throws Exception {
        String sql = "SELECT inv_proveedor_categoria.pc_aplica_retencion_iva "
                + "FROM inventario.inv_proveedor INNER JOIN inventario.inv_proveedor_categoria "
                + "ON inv_proveedor.pc_empresa = inv_proveedor_categoria.pc_empresa "
                + "AND inv_proveedor.pc_codigo = inv_proveedor_categoria.pc_codigo "
                + "WHERE inv_proveedor.prov_empresa = '" + empresa + "' AND " + "inv_proveedor.prov_codigo = '" + codigo
                + "'";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

    @Override
    public InvProveedorTO getBuscaCedulaProveedorTO(String empresa, String cedRuc) throws Exception {
        String sql = "SELECT * FROM inventario.inv_proveedor WHERE prov_empresa = ('" + empresa + "') AND "
                + "prov_id_numero = ('" + cedRuc + "')";
        return genericSQLDao.obtenerObjetoPorSql(sql, InvProveedorTO.class);
    }

    @Override
    public List<InvListaProveedoresTO> getListaProveedoresTO(String empresa, String busqueda,
            boolean incluirProveedorInactivo) throws Exception {
        String sql = "SELECT inv_proveedor.prov_codigo, inv_proveedor.prov_id_numero, "
                + "inv_proveedor.prov_razon_social, inv_proveedor_categoria.pc_detalle as prov_categoria "
                + "FROM inventario.inv_proveedor LEFT JOIN inventario.inv_proveedor_categoria "
                + "ON inv_proveedor.pc_empresa = inv_proveedor_categoria.pc_empresa "
                + "AND inv_proveedor.pc_codigo = inv_proveedor_categoria.pc_codigo WHERE "
                + (incluirProveedorInactivo ? "" : "NOT prov_inactivo " + "AND ") + " inv_proveedor.prov_empresa = ('"
                + empresa + "') "
                + "AND (prov_codigo || COALESCE(prov_id_numero,'') || prov_razon_social || CASE WHEN pc_detalle IS NULL THEN ('') ELSE (pc_detalle) END "
                + "LIKE TRANSLATE(' ' || CASE WHEN ('" + busqueda + "') = ''  THEN '~' ELSE ('" + busqueda
                + "') END || ' ', ' ', '%')) ORDER BY prov_razon_social";

        return genericSQLDao.obtenerPorSql(sql, InvListaProveedoresTO.class);
    }

    @Override
    public List<InvProveedorTO> getProveedorTO(String empresa, String codigo) throws Exception {
        String sql = "SELECT * FROM inventario.inv_proveedor WHERE prov_empresa = ('" + empresa + "') AND "
                + "prov_codigo = ('" + codigo + "')";
        return genericSQLDao.obtenerPorSql(sql, InvProveedorTO.class);
    }

    @Override
    public boolean getProveedorRepetido(String empresa, String codigo, String id, String nombre, String razonSocial) throws Exception {
        String sql = "SELECT * FROM inventario.fun_sw_proveedor_repetido(" + empresa + ", " + codigo + ", " + id + ", " + nombre + ", " + razonSocial + ")";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

    @Override
    public List<InvFunListadoProveedoresTO> getInvFunListadoProveedoresTO(String empresa, String categoria)
            throws Exception {
        categoria = categoria == null ? categoria : "'" + categoria + "'";
        String sql = "SELECT * FROM inventario.fun_listado_proveedores('" + empresa + "', " + categoria + ")";
        return genericSQLDao.obtenerPorSql(sql, InvFunListadoProveedoresTO.class);
    }

    @Override
    public InvProveedor obtenerInvProveedorPorCedulaRuc(String empresa, String ruc) {
        if (empresa == null || empresa.compareToIgnoreCase("") == 0) {
            return obtenerObjetoPorHql("select p from InvProveedor p where p.provIdNumero=?1 ", new Object[]{ruc});
        } else {
            return obtenerObjetoPorHql("select p from InvProveedor p inner join p.invProveedorPK ppk "
                    + "where p.provIdNumero=?1 and ppk.provEmpresa=?2", new Object[]{ruc, empresa});
        }
    }

    /*getListProveedorTO*/
    @Override
    public List<InvProveedorTO> getListProveedorTO(String empresa, String codigoCategoria, boolean inactivos, String busqueda) throws Exception {

        String sqlString = "";
        String sqlBusqueda = "";
        if (codigoCategoria != null) {
            sqlString = " AND inv_proveedor_categoria.pc_codigo= '" + codigoCategoria + "' ";
        }
        if (busqueda != null && !"".equals(busqueda)) {
            sqlBusqueda = "AND (prov_codigo || COALESCE(prov_id_numero,'') || prov_razon_social || CASE WHEN pc_detalle IS NULL THEN ('') ELSE (pc_detalle) END "
                    + "LIKE TRANSLATE(' ' || CASE WHEN ('" + busqueda + "') = ''  THEN '~' ELSE ('" + busqueda
                    + "') END || ' ', ' ', '%')) ";
        }
        String sql = "SELECT * FROM inventario.inv_proveedor LEFT JOIN inventario.inv_proveedor_categoria "
                + "ON inv_proveedor.pc_empresa = inv_proveedor_categoria.pc_empresa "
                + "AND inv_proveedor.pc_codigo = inv_proveedor_categoria.pc_codigo WHERE "
                + (inactivos ? "" : "NOT prov_inactivo " + "AND ") + " inv_proveedor.prov_empresa = ('" + empresa + "') "
                + sqlBusqueda + sqlString + "ORDER BY prov_razon_social";

        return genericSQLDao.obtenerPorSql(sql, InvProveedorTO.class);
    }

    @Override
    public List<InvProveedorSinMovimientoTO> obtenerProveedoresSinMovimientos(String empresa) throws Exception {
        String sql = "SELECT * FROM inventario.fun_sw_reporte_proveedores_sin_movimientos('" + empresa + "')";
        return genericSQLDao.obtenerPorSql(sql, InvProveedorSinMovimientoTO.class);
    }

}
