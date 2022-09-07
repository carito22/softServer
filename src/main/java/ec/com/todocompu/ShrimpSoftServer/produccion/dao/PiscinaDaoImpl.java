package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PiscinaGrameajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdComboPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaPiscinaComboTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscina;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class PiscinaDaoImpl extends GenericDaoImpl<PrdPiscina, PrdPiscinaPK> implements PiscinaDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sisSucesoDao;

    @Override
    public List<PrdCorrida> buscarTodasCorrida(String empresa) throws Exception {
        String sql = "SELECT * FROM produccion.prd_corrida WHERE cor_empresa = '" + empresa + "'";
        List<PrdCorrida> prdCorridas = genericSQLDao.obtenerPorSql(sql, PrdCorrida.class);
        return prdCorridas;
    }

    @Override
    public List<PrdListaPiscinaTO> getListaPiscina(String empresa, String sector) throws Exception {
        String sql = "SELECT ROW_NUMBER() OVER(ORDER BY prd_piscina.pis_numero DESC) AS id,prd_piscina.pis_numero, prd_piscina.pis_nombre, prd_piscina.pis_hectareaje, prd_piscina.pis_activa, prd_piscina.pis_sector, cta_costo_directo, cta_costo_indirecto, cta_costo_transferencia, cta_costo_producto_terminado, tipo_empresa, tipo_codigo,'' grupo_empresa,'' grupo_codigo,cta_costo_venta  FROM produccion.prd_piscina "
                + "WHERE prd_piscina.pis_empresa = ('" + empresa + "') AND prd_piscina.pis_sector = ('" + sector + "') ORDER BY prd_piscina.pis_numero";
        return genericSQLDao.obtenerPorSql(sql, PrdListaPiscinaTO.class);
    }

    @Override
    public List<PrdListaPiscinaTO> getListaPiscina(String empresa) throws Exception {
        String sql = "SELECT ROW_NUMBER() OVER(ORDER BY prd_piscina.pis_numero DESC) AS id,prd_piscina.pis_numero, prd_piscina.pis_nombre, prd_piscina.pis_hectareaje, prd_piscina.pis_activa, prd_piscina.pis_sector, cta_costo_directo, cta_costo_indirecto, cta_costo_transferencia, cta_costo_producto_terminado, tipo_empresa, tipo_codigo,'' grupo_empresa,'' grupo_codigo,cta_costo_venta FROM produccion.prd_piscina "
                + "WHERE prd_piscina.pis_empresa = ('" + empresa + "')";
        return genericSQLDao.obtenerPorSql(sql, PrdListaPiscinaTO.class);
    }

    @Override
    public List<PrdListaPiscinaTO> getListaPiscina(String empresa, String sector, boolean mostrarInactivo) throws Exception {
        String sql;
        if (mostrarInactivo) {
            sql = "SELECT ROW_NUMBER() OVER(ORDER BY prd_piscina.pis_numero DESC) AS id,prd_piscina.pis_numero, prd_piscina.pis_nombre, prd_piscina.pis_hectareaje, prd_piscina.pis_activa, prd_piscina.pis_sector, prd_piscina.cta_costo_directo, prd_piscina.cta_costo_indirecto, prd_piscina.cta_costo_transferencia, prd_piscina.cta_costo_producto_terminado, prd_piscina.tipo_empresa, prd_piscina.tipo_codigo, '' grupo_empresa, '' grupo_codigo,cta_costo_venta FROM produccion.prd_piscina "
                    + "WHERE prd_piscina.pis_empresa = ('" + empresa + "') AND prd_piscina.pis_sector = ('" + sector + "') ORDER BY prd_piscina.pis_numero";
        } else {
            sql = "SELECT ROW_NUMBER() OVER(ORDER BY prd_piscina.pis_numero DESC) AS id,prd_piscina.pis_numero, prd_piscina.pis_nombre, prd_piscina.pis_hectareaje, prd_piscina.pis_activa, prd_piscina.pis_sector, prd_piscina.cta_costo_directo, prd_piscina.cta_costo_indirecto, prd_piscina.cta_costo_transferencia, prd_piscina.cta_costo_producto_terminado, prd_piscina.tipo_empresa, prd_piscina.tipo_codigo, '' grupo_empresa, '' grupo_codigo,cta_costo_venta FROM produccion.prd_piscina "
                    + "WHERE prd_piscina.pis_empresa = ('" + empresa + "') AND prd_piscina.pis_sector = ('" + sector + "') AND pis_activa ORDER BY prd_piscina.pis_numero";
        }
        return genericSQLDao.obtenerPorSql(sql, PrdListaPiscinaTO.class);
    }

    @Override
    public List<PiscinaGrameajeTO> getListaPiscinasGrameaje(String empresa, String sector, String fecha)
            throws Exception {
        String sql = "SELECT *, 0.00 as gra_promedio, 0.00 as gra_biomasa,0.00 as gra_animales_m2, '' as gra_comentario FROM produccion.fun_plantilla_grameaje_lote('"
                + empresa + "', '" + sector + "', '" + fecha + "');";
        return genericSQLDao.obtenerPorSql(sql, PiscinaGrameajeTO.class);
    }

    @Override
    public List<PrdListaPiscinaComboTO> getListaPiscina(String empresa, boolean activo) throws Exception {
        String sql;
        if (activo) {
            sql = "SELECT pis_numero, pis_nombre, pis_sector FROM produccion.prd_piscina " + "WHERE pis_empresa = ('"
                    + empresa + "') AND pis_activa  ORDER BY pis_numero";
        } else {
            sql = "SELECT pis_numero, pis_nombre, pis_sector " + "FROM produccion.prd_piscina "
                    + "WHERE pis_empresa = ('" + empresa + "')  ORDER BY pis_numero";

        }
        return genericSQLDao.obtenerPorSql(sql, PrdListaPiscinaComboTO.class);
    }

    @Override
    public List<PrdListaPiscinaTO> getListaPiscinaBusqueda(String empresa, String sector, String fecha)
            throws Exception {
        fecha = fecha == null ? null : fecha.isEmpty() ? null : fecha;
        String sql = "SELECT ROW_NUMBER() OVER(ORDER BY prd_piscina.pis_numero DESC) AS id,prd_piscina.pis_numero, prd_piscina.pis_nombre, prd_piscina.pis_hectareaje, prd_piscina.pis_activa,prd_piscina.pis_sector, "
                + "cta_costo_directo, cta_costo_indirecto, cta_costo_transferencia, cta_costo_producto_terminado, tipo_empresa, tipo_codigo, '' grupo_empresa, '' grupo_codigo,cta_costo_venta "
                + "FROM produccion.prd_piscina INNER JOIN produccion.prd_corrida ON prd_corrida.cor_empresa = prd_piscina.pis_empresa AND "
                + "prd_corrida.cor_sector  = prd_piscina.pis_sector AND prd_corrida.cor_piscina = prd_piscina.pis_numero AND "
                + "(('" + fecha + "' >= prd_corrida.cor_fecha_desde AND '" + fecha
                + "' <= prd_corrida.cor_fecha_hasta) OR " + "('" + fecha
                + "' >= prd_corrida.cor_fecha_desde AND prd_corrida.cor_fecha_hasta IS NULL)) "
                + "WHERE prd_piscina.pis_empresa = ('" + empresa + "') AND prd_piscina.pis_sector = ('" + sector + "') "
                + "ORDER BY prd_piscina.pis_numero";
        return genericSQLDao.obtenerPorSql(sql, PrdListaPiscinaTO.class);
    }

    @Override
    public List<PrdListaPiscinaTO> getListaPiscinaBusqueda(String empresa, String sector, String fecha, String grupo, String tipo, boolean mostrarInactivos) throws Exception {
        fecha = fecha == null ? null : fecha.isEmpty() ? null : fecha;
        grupo = grupo == null ? null : grupo.isEmpty() ? null : "'" + grupo + "'";
        tipo = tipo == null ? null : tipo.isEmpty() ? null : "'" + tipo + "'";
        String sql = "";

        if (tipo != null) {
            sql = "SELECT ROW_NUMBER() OVER(ORDER BY prd_piscina.pis_numero DESC) AS id,prd_piscina.pis_numero, prd_piscina.pis_nombre, prd_piscina.pis_hectareaje, prd_piscina.pis_activa,prd_piscina.pis_sector, "
                    + "cta_costo_directo,cta_costo_indirecto, cta_costo_transferencia, cta_costo_producto_terminado, tipo_empresa, tipo_codigo, grupo_empresa, grupo_codigo,cta_costo_venta "
                    + "FROM produccion.prd_piscina "
                    + " INNER JOIN produccion.prd_piscina_grupos_relacion "
                    + " ON prd_piscina_grupos_relacion.pis_empresa = prd_piscina.pis_empresa AND "
                    + " prd_piscina_grupos_relacion.pis_sector  = prd_piscina.pis_sector AND "
                    + " prd_piscina_grupos_relacion.pis_numero = prd_piscina.pis_numero "
                    + " INNER JOIN produccion.prd_corrida "
                    + " ON prd_corrida.cor_empresa = prd_piscina.pis_empresa AND "
                    + " prd_corrida.cor_piscina = prd_piscina.pis_numero AND "
                    + " prd_corrida.cor_sector = prd_piscina.pis_sector AND "
                    + "(('" + fecha + "' >= prd_corrida.cor_fecha_desde AND '" + fecha
                    + "' <= prd_corrida.cor_fecha_hasta) OR " + "('" + fecha
                    + "' >= prd_corrida.cor_fecha_desde AND prd_corrida.cor_fecha_hasta IS NULL)) "
                    + "WHERE prd_piscina.pis_empresa = ('" + empresa + "')"
                    + (mostrarInactivos ? "" : " AND pis_activa ")
                    + (tipo != null ? " AND tipo_empresa='" + empresa + "' AND tipo_codigo=" + tipo : "")
                    + "ORDER BY prd_piscina.pis_numero";
        } else {
            if (grupo != null) {
                sql = "SELECT ROW_NUMBER() OVER(ORDER BY prd_piscina.pis_numero DESC) AS id,prd_piscina.pis_numero, prd_piscina.pis_nombre, prd_piscina.pis_hectareaje, prd_piscina.pis_activa,prd_piscina.pis_sector, "
                        + "cta_costo_directo,cta_costo_indirecto, cta_costo_transferencia, cta_costo_producto_terminado, tipo_empresa, tipo_codigo, grupo_empresa, grupo_codigo,cta_costo_venta "
                        + "FROM produccion.prd_piscina INNER JOIN produccion.prd_piscina_grupos_relacion "
                        + "  ON prd_piscina_grupos_relacion.pis_empresa = prd_piscina.pis_empresa AND "
                        + "  prd_piscina_grupos_relacion.pis_sector  = prd_piscina.pis_sector AND "
                        + "  prd_piscina_grupos_relacion.pis_numero = prd_piscina.pis_numero "
                        + " INNER JOIN produccion.prd_corrida "
                        + " ON prd_corrida.cor_empresa = prd_piscina.pis_empresa AND "
                        + " prd_corrida.cor_piscina = prd_piscina.pis_numero AND "
                        + " prd_corrida.cor_sector = prd_piscina.pis_sector AND "
                        + "(('" + fecha + "' >= prd_corrida.cor_fecha_desde AND '" + fecha
                        + "' <= prd_corrida.cor_fecha_hasta) OR " + "('" + fecha
                        + "' >= prd_corrida.cor_fecha_desde AND prd_corrida.cor_fecha_hasta IS NULL)) "
                        + "WHERE prd_piscina.pis_empresa = ('" + empresa + "')"
                        + (mostrarInactivos ? "" : " AND pis_activa ")
                        + (grupo != null ? " AND grupo_empresa='" + empresa + "' AND grupo_codigo=" + grupo : "")
                        + (sector != null ? " AND prd_piscina.pis_sector='" + sector + "'" : "")
                        + "ORDER BY prd_piscina.pis_numero";
            } else {
                sql = "SELECT ROW_NUMBER() OVER(ORDER BY prd_piscina.pis_numero DESC) AS id,prd_piscina.pis_numero, prd_piscina.pis_nombre, prd_piscina.pis_hectareaje, prd_piscina.pis_activa,prd_piscina.pis_sector, "
                        + "cta_costo_directo, cta_costo_indirecto, cta_costo_transferencia, cta_costo_producto_terminado, tipo_empresa, tipo_codigo, '' grupo_empresa, '' grupo_codigo,cta_costo_venta "
                        + "FROM produccion.prd_piscina INNER JOIN produccion.prd_corrida ON prd_corrida.cor_empresa = prd_piscina.pis_empresa AND "
                        + "prd_corrida.cor_sector  = prd_piscina.pis_sector AND prd_corrida.cor_piscina = prd_piscina.pis_numero AND "
                        + "(('" + fecha + "' >= prd_corrida.cor_fecha_desde AND '" + fecha
                        + "' <= prd_corrida.cor_fecha_hasta) OR " + "('" + fecha
                        + "' >= prd_corrida.cor_fecha_desde AND prd_corrida.cor_fecha_hasta IS NULL)) "
                        + "WHERE prd_piscina.pis_empresa = ('" + empresa + "') AND prd_piscina.pis_sector = ('" + sector + "') "
                        + (mostrarInactivos ? "" : " AND pis_activa ")
                        + (tipo != null ? " AND tipo_empresa='" + empresa + "' AND tipo_codigo=" + tipo : "")
                        + "ORDER BY prd_piscina.pis_numero";
            }
        }
        return genericSQLDao.obtenerPorSql(sql, PrdListaPiscinaTO.class);

    }

    @Override
    public List<PrdComboPiscinaTO> getComboPiscina(String empresa, String sector) throws Exception {
        String sql = "SELECT pis_numero, pis_nombre, pis_hectareaje FROM produccion.prd_piscina "
                + "WHERE (pis_empresa = '" + empresa + "') AND (pis_sector = '" + sector + "') "
                + "AND pis_activa ORDER BY pis_numero";
        return genericSQLDao.obtenerPorSql(sql, PrdComboPiscinaTO.class);
    }

    @Override
    public boolean eliminarPrdPiscina(String empresa, String sector, String piscina) throws Exception {
        String sql = "SELECT * FROM produccion.fun_eliminar_produccion('P', ('" + empresa + "'), ('" + sector + "'), ('"
                + piscina + "'), null)";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public boolean insertarPrdPiscina(PrdPiscina prdPiscina, SisSuceso sisSuceso) throws Exception {
        insertar(prdPiscina);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarPrdPiscina(PrdPiscina prdPiscina, SisSuceso sisSuceso) throws Exception {
        actualizar(prdPiscina);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarPrdPiscina(PrdPiscina prdPiscina, SisSuceso sisSuceso) throws Exception {
        eliminar(prdPiscina);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public PrdPiscina obtenerPorEmpresaSectorPiscina(String empresa, String sector, String piscina) {
        String sql = "SELECT * FROM produccion.prd_piscina WHERE pis_empresa='" + empresa + "' and sec_codigo='"
                + sector + "'" + " and pis_numero='" + piscina + "'";
        return genericSQLDao.obtenerObjetoPorSql(sql, PrdPiscina.class);
    }

    @Override
    public List<PrdPiscina> obtenerPorEmpresaSector(String empresa, String sector) {
        String sql = "SELECT * FROM produccion.prd_piscina WHERE pis_empresa='" + empresa + "' and sec_codigo='"
                + sector + "' ORDER BY pis_nombre";
        return genericSQLDao.obtenerPorSql(sql, PrdPiscina.class);
    }

    @Override
    public List<PrdPiscina> getListaPiscinaPor_Empresa_Sector_Activa(String empresa, String sector, boolean activo)
            throws Exception {
        String sql = "";
        if (activo) {
            sql = "SELECT * FROM produccion.prd_piscina WHERE pis_empresa = ('" + empresa + "') AND pis_sector = ('"
                    + sector + "') AND pis_activa ORDER BY pis_numero";
        } else {
            sql = "SELECT * FROM produccion.prd_piscina WHERE pis_empresa = ('" + empresa + "') AND pis_sector = ('"
                    + sector + "') ORDER BY pis_numero";
        }
        return genericSQLDao.obtenerPorSql(sql, PrdPiscina.class);
    }

    @Override
    public boolean cambiarCodigoPiscina(String empresa, String sector, String piscinaIncorrecta, String piscinaCorrecta) throws Exception {
        String sql = "SELECT * FROM produccion.fun_reestructurar_piscinas('" + empresa + "', '" + sector + "', '" + piscinaIncorrecta + "', '" + piscinaCorrecta + "')";
        String respuesta = (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
        return respuesta.equals("true");
    }
}
