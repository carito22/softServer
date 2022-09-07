package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvBodegaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComboBodegaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaBodegasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.SaldoBodegaComprobacionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.SaldoBodegaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvBodega;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvBodegaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class BodegaDaoImpl extends GenericDaoImpl<InvBodega, InvBodegaPK> implements BodegaDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public InvBodega buscarInvBodega(String empresa, String codigoBodega) throws Exception {
        return obtenerPorId(InvBodega.class, new InvBodegaPK(empresa.trim(), codigoBodega.trim()));
    }

    @Override
    public boolean eliminarInvBodega(InvBodega invBodega, SisSuceso sisSuceso) {
        eliminar(invBodega);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean insertarInvBodega(InvBodega invBodega, SisSuceso sisSuceso) throws Exception {
        insertar(invBodega);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarInvBodega(InvBodega invBodega, SisSuceso sisSuceso) throws Exception {
        actualizar(invBodega);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public Boolean buscarInvBodega(String empresa, String codigoBodega, String nombreBodega) throws Exception {
        String sql = "SELECT * FROM inventario.fun_sw_bodega_repetido('" + empresa + "', '" + codigoBodega + "', '"
                + nombreBodega + "');";
        return (Boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

    @Override
    public List<InvBodegaTO> getBodegaTO(String empresa, String codigo) throws Exception {
        String sql = "SELECT * FROM inventario.inv_bodega " + "WHERE bod_empresa = ('"
                + empresa + "') AND " + "bod_codigo = ('" + codigo + "')";

        return genericSQLDao.obtenerPorSql(sql, InvBodegaTO.class);
    }

    @Override
    public List<InvComboBodegaTO> getInvComboBodegaTO(String empresa) throws Exception {
        String sql = "SELECT bod_codigo, bod_nombre, sec_codigo " + "FROM inventario.inv_bodega "
                + "WHERE (bod_empresa = '" + empresa + "') ORDER BY bod_codigo";

        return genericSQLDao.obtenerPorSql(sql, InvComboBodegaTO.class);
    }

    @Override
    public List<InvListaBodegasTO> getListaBodegasTO(String empresa, boolean inacivo) throws Exception {
        String sql = "";
        if (!inacivo) {
            sql = " SELECT " + "bod_codigo, " + "bod_nombre, "
                    + "sis_usuario.usr_apellido ||' '||sis_usuario.usr_nombre bod_responsable, " + "bod_inactiva, "
                    + "sec_codigo " + "FROM inventario.inv_bodega LEFT JOIN sistemaweb.sis_usuario_detalle "
                    + "INNER JOIN sistemaweb.sis_usuario "
                    + "ON sis_usuario_detalle.usr_codigo = sis_usuario.usr_codigo "
                    + "ON inv_bodega.det_empresa = sis_usuario_detalle.det_empresa  AND "
                    + "sis_usuario_detalle.det_usuario = inv_bodega.det_usuario  " + "WHERE (bod_empresa = '" + empresa
                    + "') AND NOT bod_inactiva ORDER BY bod_codigo ";
        } else {
            sql = "SELECT " + "bod_codigo, " + "bod_nombre, "
                    + "sis_usuario.usr_apellido ||' '||sis_usuario.usr_nombre bod_responsable, " + "bod_inactiva, "
                    + "sec_codigo " + "FROM inventario.inv_bodega LEFT JOIN sistemaweb.sis_usuario_detalle "
                    + "INNER JOIN sistemaweb.sis_usuario "
                    + "ON sis_usuario_detalle.usr_codigo = sis_usuario.usr_codigo "
                    + "ON inv_bodega.det_empresa = sis_usuario_detalle.det_empresa  AND "
                    + "sis_usuario_detalle.det_usuario = inv_bodega.det_usuario  " + "WHERE (bod_empresa = '" + empresa
                    + "') ORDER BY bod_codigo";
        }
        return genericSQLDao.obtenerPorSql(sql, InvListaBodegasTO.class);
    }

    @Override
    public List<InvBodega> getListaBodegas(String empresa, boolean inacivo) throws Exception {
        String sql = " SELECT bod.* FROM inventario.inv_bodega bod LEFT JOIN sistemaweb.sis_usuario_detalle "
                + "INNER JOIN sistemaweb.sis_usuario ON sis_usuario_detalle.usr_codigo = sis_usuario.usr_codigo "
                + "ON bod.det_empresa = sis_usuario_detalle.det_empresa  AND "
                + "sis_usuario_detalle.det_usuario = bod.det_usuario  WHERE (bod_empresa = '" + empresa + "') "
                + (inacivo ? "" : "AND NOT bod_inactiva ") + "ORDER BY bod_codigo ";
        return genericSQLDao.obtenerPorSql(sql, InvBodega.class);
    }

    @Override
    public Boolean getPuedeEliminarBodega(String empresa, String bodega) throws Exception {
        String sql = "SELECT * FROM inventario.fun_sw_bodega_eliminar('" + empresa + "', '" + bodega + "');";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

    @Override
    public List<SaldoBodegaComprobacionTO> getInvFunSaldoBodegaComprobacionCantidadesTO(String empresa, String bodega,
            String desde, String hasta) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        bodega = bodega == null ? bodega : "'" + bodega + "'";
        String sql = "SELECT * FROM inventario.fun_saldo_bodega_comprobacion_cantidades(" + "'" + empresa + "', "
                + bodega + ", " + desde + ", " + hasta + ")";

        return genericSQLDao.obtenerPorSql(sql, SaldoBodegaComprobacionTO.class);
    }

    @Override
    public List<SaldoBodegaComprobacionTO> getInvFunSaldoBodegaComprobacionTO(String empresa, String bodega,
            String desde, String hasta) throws Exception {
        desde = desde == "" ? null : "'" + desde + "'";
        hasta = hasta == "" ? null : "'" + hasta + "'";
        bodega = bodega == "" ? null : "'" + bodega + "'";
        String sql = "SELECT * FROM inventario.fun_saldo_bodega_comprobacion(" + "'" + empresa + "', " + bodega + ", "
                + desde + ", " + hasta + ")";
        return genericSQLDao.obtenerPorSql(sql, SaldoBodegaComprobacionTO.class);
    }

    @Override
    public List<SaldoBodegaTO> getListaSaldoBodegaTO(String empresa, String bodega, String hasta, String categoria) throws Exception {
        hasta = hasta == "" ? null : "'" + hasta + "'";
        bodega = bodega == null ? null : "'" + bodega + "'";
        categoria = categoria == null ? null : "'" + categoria + "'";
        String sql = "SELECT * FROM inventario.fun_saldo_bodega('" + empresa + "', " + bodega + "," + categoria + " , " + hasta
                + ", false)";

        List<SaldoBodegaTO> lista = genericSQLDao.obtenerPorSql(sql, SaldoBodegaTO.class);
        return lista;
    }

    @Override
    public List<InvListaBodegasTO> buscarBodegasTO(String empresa, boolean inactivo, String busqueda) throws Exception {
        String sql = "SELECT " + "bod_codigo, " + "bod_nombre, "
                + "sis_usuario.usr_apellido ||' '||sis_usuario.usr_nombre bod_responsable, " + "bod_inactiva, "
                + "sec_codigo " + "FROM inventario.inv_bodega LEFT JOIN sistemaweb.sis_usuario_detalle "
                + "INNER JOIN sistemaweb.sis_usuario "
                + "ON sis_usuario_detalle.usr_codigo = sis_usuario.usr_codigo "
                + "ON inv_bodega.det_empresa = sis_usuario_detalle.det_empresa  AND "
                + "sis_usuario_detalle.det_usuario = inv_bodega.det_usuario  " + "WHERE (bod_empresa = '" + empresa
                + "')";
        if (busqueda != null && !busqueda.equals("")) {
            sql = sql + " AND (bod_codigo LIKE '%" + busqueda + "%' OR bod_nombre LIKE '%" + busqueda + "%') ";
        }
        if (!inactivo) {
            sql = sql + " AND NOT bod_inactiva ORDER BY bod_codigo ";
        }
        return genericSQLDao.obtenerPorSql(sql, InvListaBodegasTO.class);
    }

}
