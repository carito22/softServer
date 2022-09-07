package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.ListaPreLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunPreLiquidacionConsolidandoTallasTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionConsolidandoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionConsultaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdPreLiquidacionesDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class PreLiquidacionDaoImpl extends GenericDaoImpl<PrdPreLiquidacion, PrdPreLiquidacionPK>
        implements PreLiquidacionDao {

    @Autowired
    private PreLiquidacionDetalleDao preLiquidacionDetalleDao;
    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public PrdPreLiquidacion buscarPrdPreLiquidacion(PrdPreLiquidacionPK prdPreLiquidacionPK) throws Exception {
        return obtenerPorId(PrdPreLiquidacion.class, prdPreLiquidacionPK);
    }

    @Override
    public boolean insertarModificarPrdPreLiquidacion(PrdPreLiquidacion prdPreLiquidacion,
            List<PrdPreLiquidacionDetalle> listaPrdPreLiquidacionDetalle, SisSuceso sisSuceso) throws Exception {

        if (prdPreLiquidacion.getPrdPreLiquidacionPK().getPlNumero() == null
                || prdPreLiquidacion.getPrdPreLiquidacionPK().getPlNumero().compareToIgnoreCase("") == 0) {
            String rellenarConCeros = "";
            int numeracion = buscarConteoUltimaNumeracionPreLiquidacion(
                    prdPreLiquidacion.getPrdPreLiquidacionPK().getPlEmpresa(),
                    prdPreLiquidacion.getPrdPreLiquidacionPK().getPlMotivo());
            do {
                numeracion++;
                int numeroCerosAponer = 7 - String.valueOf(numeracion).trim().length();
                rellenarConCeros = "";
                for (int i = 0; i < numeroCerosAponer; i++) {
                    rellenarConCeros = rellenarConCeros + "0";
                }

                prdPreLiquidacion.getPrdPreLiquidacionPK().setPlNumero(rellenarConCeros + numeracion);

            } while (buscarPrdPreLiquidacion(prdPreLiquidacion.getPrdPreLiquidacionPK()) != null);
        }
        sisSuceso.setSusClave(prdPreLiquidacion.getPrdPreLiquidacionPK().getPlMotivo() + " "
                + prdPreLiquidacion.getPrdPreLiquidacionPK().getPlNumero());

        sisSuceso.setSusDetalle("Se insertó la pre liquidacion: " + prdPreLiquidacion.getPrdPreLiquidacionPK().getPlNumero() + ", con el motivo: " + prdPreLiquidacion.getPrdPreLiquidacionPK().getPlMotivo());

        ////////////// Inserta el consumo///////////////////
        List<PrdPreLiquidacionDetalle> list = prdPreLiquidacion.getPrdPreLiquidacionDetalleList();

        for (int i = 0; i < listaPrdPreLiquidacionDetalle.size(); i++) {
            listaPrdPreLiquidacionDetalle.get(i).setDetSecuencial(null);
            listaPrdPreLiquidacionDetalle.get(i).setDetOrden(i + 1);
            listaPrdPreLiquidacionDetalle.get(i).setPrdPreLiquidacion(prdPreLiquidacion);
        }

        prdPreLiquidacion.setPrdPreLiquidacionDetalleList(new ArrayList<PrdPreLiquidacionDetalle>());
        prdPreLiquidacion.getPrdPreLiquidacionDetalleList().addAll(listaPrdPreLiquidacionDetalle);

        saveOrUpdate(prdPreLiquidacion);

        if (list != null && !list.isEmpty()) {
            // list.removeAll(listaPrdLiquidacionDetalle);
            for (int i = 0; i < list.size(); i++) {
                preLiquidacionDetalleDao.eliminarPorSql(list.get(i).getDetSecuencial());
            }
        }

        sucesoDao.insertar(sisSuceso);
        ////////////////////////////////
        return true;
    }

    @Override
    public int buscarConteoUltimaNumeracionPreLiquidacion(String empCodigo, String motCodigo) throws Exception {
        String sql = "SELECT num_secuencia FROM produccion.prd_liquidacion_numeracion WHERE num_empresa='" + empCodigo
                + "' AND num_motivo='" + motCodigo + "'";
        Object objeto = genericSQLDao.obtenerObjetoPorSql(sql);
        return objeto == null ? 0 : Integer.parseInt((String) UtilsConversiones.convertir(objeto));
    }

    @Override
    public PrdPreLiquidacion getPrdPreLiquidacion(PrdPreLiquidacionPK preLiquidacionPK) {
        PrdPreLiquidacion preLiquidacion = obtenerObjetoPorHql(
                "select  pl from PrdPreLiquidacion pl inner join pl.prdPreLiquidacionPK plk inner join fetch pl.prdPreLiquidacionDetalleList "
                + "where plk.plEmpresa=?1 and plk.plMotivo=?2 and plk.plNumero=?3",
                new Object[]{preLiquidacionPK.getPlEmpresa(), preLiquidacionPK.getPlMotivo(),
                    preLiquidacionPK.getPlNumero()});

        return preLiquidacion;
    }

    @Override
    public List<PrdPreLiquidacion> getListaPrdPreLiquidacion(String empresa) {
        return obtenerPorHql(
                "select distinct lp from PrdPreLiquidacion lp inner join lp.prdPreLiquidacionPK lppk inner join fetch lp.prdPreLiquidacionDetalleList "
                + "where lppk.plEmpresa=?1",
                new Object[]{empresa});
    }

    @Override
    public void desmayorizarPrdPreLiquidacion(PrdPreLiquidacionPK preLiquidacionPK) {
        String sql = "UPDATE produccion.prd_preliquidacion SET pl_pendiente=true WHERE pl_empresa='"
                + preLiquidacionPK.getPlEmpresa() + "' and pl_motivo='" + preLiquidacionPK.getPlMotivo()
                + "' and pl_numero='" + preLiquidacionPK.getPlNumero() + "';";
        genericSQLDao.ejecutarSQL(sql);
    }

    @Override
    public void anularRestaurarPrdPreLiquidacion(PrdPreLiquidacionPK preLiquidacionPK, boolean anularRestaurar) {
        String sql = "UPDATE produccion.prd_preliquidacion SET pl_anulado=" + anularRestaurar + " WHERE pl_empresa='"
                + preLiquidacionPK.getPlEmpresa() + "' and pl_motivo='" + preLiquidacionPK.getPlMotivo()
                + "' and pl_numero='" + preLiquidacionPK.getPlNumero() + "';";
        genericSQLDao.ejecutarSQL(sql);
    }

    @Override
    public List<ListaPreLiquidacionTO> getListaPrdConsultaPreLiquidacion(String empresa, String sector, String piscina,
            String busqueda) throws Exception {
        String sql = "SELECT * FROM produccion.fun_preliquidacion_listado('" + empresa + "', '" + sector + "', '" + piscina
                + "', '" + busqueda + "')";
        return genericSQLDao.obtenerPorSql(sql, ListaPreLiquidacionTO.class);
    }

    @Override
    public PrdPreLiquidacionTO getBuscaObjPreLiquidacionPorLote(String plLote) throws Exception {
        String sql = "SELECT * FROM produccion.prd_preliquidacion WHERE prd_preliquidacion.pl_lote = '" + plLote + "'";
        return genericSQLDao.obtenerObjetoPorSql(sql, PrdPreLiquidacionTO.class);
    }

    @Override
    public List<ListaPreLiquidacionTO> getListaPrdConsultaPreLiquidacionTO(String empresa, String sector, String piscina, String busqueda, String nRegistros) throws Exception {
        String limit = "";
        if (nRegistros != null && nRegistros.compareTo("") != 0 && nRegistros.compareTo("0") != 0) {
            limit = " limit " + nRegistros;
        }
        String sql = "SELECT * FROM produccion.fun_preliquidacion_listado('" + empresa + "','" + sector + "','"
                + piscina + "','" + busqueda + "')" + " Order by id DESC " + limit;
        return genericSQLDao.obtenerPorSql(sql, ListaPreLiquidacionTO.class);
    }

    @Override
    public List<PrdPreLiquidacionDetalleProductoTO> getListadoPreLiquidacionDetalleProductoTO(String empresa, String sector, String piscina, String desde, String hasta, String cliente, String talla, String tipo) throws Exception {

        empresa = empresa == null ? empresa : "'" + empresa + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        piscina = piscina == null ? piscina : "'" + piscina + "'";
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        talla = talla == null ? talla : "'" + talla + "'";
        tipo = tipo == null ? tipo : "'" + tipo + "'";
        String sql = "SELECT * FROM produccion.fun_preliquidacion_detalle_producto(" + empresa + "," + sector + ","
                + piscina + "," + desde + ", " + hasta + "," + cliente + "," + talla + "," + tipo + ")";

        return genericSQLDao.obtenerPorSql(sql, PrdPreLiquidacionDetalleProductoTO.class);
    }

    //Listado de PreLiquidacion Consolidando Tallas
    @Override
    public List<PrdFunPreLiquidacionConsolidandoTallasTO> getPrdFunPreLiquidacionConsolidandoTallasTO(String empresa, String desde, String hasta, String sector, String cliente, String piscina) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        piscina = piscina == null ? piscina : "'" + piscina + "'";
        String sql = "SELECT * FROM produccion.fun_pre_liquidacion_consolidando_tallas('" + empresa + "', " + desde + ", "
                + hasta + ", " + sector + "," + cliente + "," + piscina + ")";
        return genericSQLDao.obtenerPorSql(sql, PrdFunPreLiquidacionConsolidandoTallasTO.class);
    }
    
    //Preliquidacion consolidanco clientes
    @Override
    public List<PrdPreLiquidacionConsolidandoClientesTO> getPrdFunPreLiquidacionConsolidandoClientesTO(String empresa, String sector, String desde, String hasta, String cliente) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        String sql = "SELECT * FROM produccion.fun_preliquidacion_consolidando_clientes('" + empresa + "', " + sector + ", "
                + desde + ", " + hasta + ", " + cliente + ")";
        return genericSQLDao.obtenerPorSql(sql, PrdPreLiquidacionConsolidandoClientesTO.class);
    }
    
    //listar liquidacion consultas
    @Override
    public List<PrdPreLiquidacionConsultaTO> getPrdFunPreLiquidacionConsultaTO(String empresa, String sector, String piscina, String desde, String hasta, boolean incluirAnuladas) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        piscina = piscina == null ? piscina : "'" + piscina + "'";
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        String sql = "SELECT * FROM produccion.fun_preliquidacion_consulta(" + empresa + ", " + sector + ", " + piscina + ","
                + desde + ", " + hasta + ", " + incluirAnuladas + ")";
        List<PrdPreLiquidacionConsultaTO> prd = genericSQLDao.obtenerPorSql(sql, PrdPreLiquidacionConsultaTO.class);
        return prd;
    }
    
    //Listado de preliquidacion Detalle
    @Override
    public List<PrdPreLiquidacionesDetalleTO> listarPreLiquidacionesDetalle(String empresa, String desde, String hasta) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        String sql = "SELECT * FROM produccion.fun_pre_liquidaciones_detalle(" + empresa + "," + desde + ", " + hasta + ")";
        return genericSQLDao.obtenerPorSql(sql, PrdPreLiquidacionesDetalleTO.class);
    }
}
