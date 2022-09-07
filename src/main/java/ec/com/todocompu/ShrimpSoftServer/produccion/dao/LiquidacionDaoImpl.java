package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoLiquidacionPescaDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesProduccion;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.ListaLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunLiquidacionConsolidandoTallasProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunLiquidacionConsolidandoTallasTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionConsolidandoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionConsolidandoProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionConsultaEmpacadoraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionConsultaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionDetalleProductoEmpacadoraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionesDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoLiquidacionPesca;

@Repository
public class LiquidacionDaoImpl extends GenericDaoImpl<PrdLiquidacion, PrdLiquidacionPK> implements LiquidacionDao {

    @Autowired
    private LiquidacionDetalleDao liquidacionDetalleDao;
    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private SucesoLiquidacionPescaDao sucesoLiquidacionPescaDao;

    @Override
    public void insertarPrdLiquidacion(PrdLiquidacion prdLiquidacion,
            List<PrdLiquidacionDetalle> listaPrdLiquidacionDetalle, SisSuceso sisSuceso,
            PrdLiquidacionNumeracion prdLiquidacionNumeracion, boolean nuevo) throws Exception {
        insertar(prdLiquidacion);
        for (int i = 0; i < listaPrdLiquidacionDetalle.size(); i++) {
            listaPrdLiquidacionDetalle.get(i).setDetOrden((i + 1));
            listaPrdLiquidacionDetalle.get(i).setPrdLiquidacion(prdLiquidacion);
            liquidacionDetalleDao.insertar(listaPrdLiquidacionDetalle.get(i));
        }

        sucesoDao.insertar(sisSuceso);
    }

    @Override
    public boolean modificarPrdLiquidacion(PrdLiquidacion prdLiquidacion, List<PrdLiquidacionDetalle> listaPrdDetalle,
            List<PrdLiquidacionDetalle> listaPrdDetalleEliminar, SisSuceso sisSuceso) throws Exception {
        actualizar(prdLiquidacion);
        for (int i = 0; i < listaPrdDetalle.size(); i++) {
            listaPrdDetalle.get(i).setDetOrden((i + 1));
            listaPrdDetalle.get(i).setPrdLiquidacion(prdLiquidacion);
            liquidacionDetalleDao.actualizar(listaPrdDetalle.get(i));
        }

        if (!listaPrdDetalleEliminar.isEmpty()) {
            for (int i = 0; i < listaPrdDetalleEliminar.size(); i++) {
                listaPrdDetalleEliminar.get(i).setPrdLiquidacion(prdLiquidacion);
                liquidacionDetalleDao.eliminar(listaPrdDetalleEliminar.get(i));
            }
        }

        if (sisSuceso != null) {
            sucesoDao.insertar(sisSuceso);
        }

        return true;
    }

    @Override
    public boolean insertarTransaccionPrdLiquidacion(PrdLiquidacion prdLiquidacion,
            List<PrdLiquidacionDetalle> listaPrdLiquidacionDetalles, SisSuceso sisSuceso,
            PrdLiquidacionNumeracion prdLiquidacionNumeracion) throws Exception {

        String rellenarConCeros = "";
        int numeracion = buscarConteoUltimaNumeracionLiquidacion(prdLiquidacion.getPrdLiquidacionPK().getLiqEmpresa(),
                prdLiquidacion.getPrdLiquidacionPK().getLiqMotivo());
        boolean nuevo = false;
        if (numeracion == 0) {
            nuevo = true;
        }
        do {
            numeracion++;
            int numeroCerosAponer = 7 - String.valueOf(numeracion).trim().length();
            rellenarConCeros = "";
            for (int i = 0; i < numeroCerosAponer; i++) {
                rellenarConCeros = rellenarConCeros + "0";
            }

            prdLiquidacion
                    .setPrdLiquidacionPK(new PrdLiquidacionPK(prdLiquidacion.getPrdLiquidacionPK().getLiqEmpresa(),
                            prdLiquidacion.getPrdLiquidacionPK().getLiqMotivo(), rellenarConCeros + numeracion));
            prdLiquidacionNumeracion.setNumSecuencia(rellenarConCeros + numeracion);

        } while (obtenerPorId(PrdLiquidacion.class,
                new PrdLiquidacionPK(prdLiquidacion.getPrdLiquidacionPK().getLiqEmpresa(),
                        prdLiquidacion.getPrdLiquidacionPK().getLiqMotivo(), rellenarConCeros + numeracion)) != null);
        sisSuceso.setSusClave(prdLiquidacion.getPrdLiquidacionPK().getLiqMotivo() + " "
                + prdLiquidacion.getPrdLiquidacionPK().getLiqNumero());
        String detalle = "Se insertó la liquidacion: " + prdLiquidacion.getPrdLiquidacionPK().getLiqNumero()
                + ", con el motivo: " + prdLiquidacion.getPrdLiquidacionPK().getLiqNumero();
        sisSuceso.setSusDetalle(detalle.length() > 300 ? detalle.substring(0, 300) : detalle);
        insertarPrdLiquidacion(prdLiquidacion, listaPrdLiquidacionDetalles, sisSuceso, prdLiquidacionNumeracion, nuevo);
        return true;
    }

    @Override
    public PrdEstadoCCCVT getEstadoCCCVT(String empresa, String motivoTipo, String numero) throws Exception {
        String sql = "SELECT liq_pendiente, liq_anulado FROM produccion.prd_liquidacion WHERE " + "liq_empresa = '"
                + empresa + "' AND liq_motivo = '" + motivoTipo + "' AND liq_numero = '" + numero + "'";

        return genericSQLDao.obtenerPorSql(sql, PrdEstadoCCCVT.class).get(0);
    }

    @Override
    public PrdLiquidacionCabeceraTO getPrdLiquidacionCabeceraTO(String empresa, String motivo, String numeroLiquidacion)
            throws Exception {
        String sql = "SELECT liq_lote, SUBSTRING(cast(liq_fecha as TEXT), 1, 10) as liq_fecha, liq_libras_enviadas, "
                + "liq_libras_recibidas, liq_libras_basura, liq_libras_retiradas, "
                + "liq_libras_netas, liq_libras_entero, liq_libras_cola, "
                + "liq_libras_cola_procesadas, liq_animales_cosechados, "
                + "liq_total_monto, liq_pendiente, liq_anulado, cli_empresa, "
                + "cli_codigo, pis_empresa, pis_sector, pis_numero, usr_empresa, usr_codigo, usr_fecha_inserta "
                + "FROM produccion.prd_liquidacion " + "WHERE (prd_liquidacion.liq_empresa = '" + empresa
                + "') AND prd_liquidacion.liq_motivo = ('" + motivo + "') " + "AND prd_liquidacion.liq_numero = ('"
                + numeroLiquidacion + "')";
        return genericSQLDao.obtenerObjetoPorSql(sql, PrdLiquidacionCabeceraTO.class);
    }

    @Override
    public int buscarConteoUltimaNumeracionLiquidacion(String empCodigo, String motCodigo) throws Exception {
        String sql = "SELECT num_secuencia FROM produccion.prd_liquidacion_numeracion WHERE num_empresa='" + empCodigo
                + "' AND num_motivo='" + motCodigo + "'";
        Object objeto = genericSQLDao.obtenerObjetoPorSql(sql);
        return objeto == null ? 0 : Integer.parseInt((String) UtilsConversiones.convertir(objeto));
    }

    @Override
    public String obtenerSiguienteLote(String empCodigo, String motCodigo) throws Exception {
        int numeracion = buscarConteoUltimaNumeracionLiquidacion(empCodigo, motCodigo);
        if (numeracion > 0) {
            numeracion++;
            int numeroCerosAponer = 7 - String.valueOf(numeracion).trim().length();
            String rellenarConCeros = "";
            for (int i = 0; i < numeroCerosAponer; i++) {
                rellenarConCeros = rellenarConCeros + "0";
            }
            return rellenarConCeros + numeracion;
        }
        return "0000001";
    }

    @Override
    public List<ListaLiquidacionTO> getListaPrdConsultaLiquidacion(String empresa, String sector,
            String piscina, String busqueda) throws Exception {
        String sql = "SELECT * FROM produccion.fun_liquidacion_listado('" + empresa + "', '" + sector + "', '" + piscina
                + "', '" + busqueda + "')";
        return genericSQLDao.obtenerPorSql(sql, ListaLiquidacionTO.class);
    }

    @Override
    public PrdLiquidacion buscarPrdLiquidacion(PrdLiquidacionPK prdLiquidacionPK) throws Exception {
        PrdLiquidacion liqu = obtenerPorId(PrdLiquidacion.class, prdLiquidacionPK);
        if (liqu != null) {
            List<PrdLiquidacionDetalle> detalle = liquidacionDetalleDao.getListaPrdLiquidacionDetalle(prdLiquidacionPK.getLiqEmpresa(), prdLiquidacionPK.getLiqMotivo(), prdLiquidacionPK.getLiqNumero());
            if (detalle != null) {
                liqu.setPrdLiquidacionDetalleList(detalle);
            } else {
                liqu.setPrdLiquidacionDetalleList(new ArrayList<>());
            }
        }
        return liqu;
    }

    @Override
    public boolean insertarModificarPrdLiquidacion(PrdLiquidacion prdLiquidacion,
            List<PrdLiquidacionDetalle> listaPrdLiquidacionDetalle, SisSuceso sisSuceso) throws Exception {

        if (prdLiquidacion.getPrdLiquidacionPK().getLiqNumero() == null || prdLiquidacion.getPrdLiquidacionPK().getLiqNumero().compareToIgnoreCase("") == 0) {
            String rellenarConCeros = "";
            int numeracion = buscarConteoUltimaNumeracionLiquidacion(
                    prdLiquidacion.getPrdLiquidacionPK().getLiqEmpresa(),
                    prdLiquidacion.getPrdLiquidacionPK().getLiqMotivo());
            do {
                numeracion++;
                int numeroCerosAponer = 7 - String.valueOf(numeracion).trim().length();
                rellenarConCeros = "";
                for (int i = 0; i < numeroCerosAponer; i++) {
                    rellenarConCeros = rellenarConCeros + "0";
                }

                prdLiquidacion.getPrdLiquidacionPK().setLiqNumero(rellenarConCeros + numeracion);

            } while (buscarPrdLiquidacion(prdLiquidacion.getPrdLiquidacionPK()) != null);
            if (prdLiquidacion.getInvProveedor() != null && prdLiquidacion.getInvProveedor().getInvProveedorPK() != null
                    && prdLiquidacion.getInvProveedor().getInvProveedorPK().getProvCodigo() != null && !prdLiquidacion.getInvProveedor().getInvProveedorPK().getProvCodigo().equals("")) {//generar nuevo Lote
                if (prdLiquidacion.getLiqLote() == null) {
                    prdLiquidacion.setLiqLote(prdLiquidacion.getPrdLiquidacionPK().getLiqNumero());
                }
            }
        }
        sisSuceso.setSusClave(prdLiquidacion.getPrdLiquidacionPK().getLiqMotivo() + " "
                + prdLiquidacion.getPrdLiquidacionPK().getLiqNumero());

        String detalle = sisSuceso.getSusSuceso().equals("INSERT") ? "Se insertó la liquidacion: " : "Se actualizó la liquidacion: ";
        sisSuceso.setSusDetalle(detalle + prdLiquidacion.getPrdLiquidacionPK().getLiqNumero()
                + ", con el motivo: " + prdLiquidacion.getPrdLiquidacionPK().getLiqMotivo());

        ////////////// Inserta el consumo///////////////////
        List<PrdLiquidacionDetalle> list = prdLiquidacion.getPrdLiquidacionDetalleList();

        for (int i = 0; i < listaPrdLiquidacionDetalle.size(); i++) {
            listaPrdLiquidacionDetalle.get(i).setDetSecuencial(null);
            listaPrdLiquidacionDetalle.get(i).setDetOrden(i + 1);
            listaPrdLiquidacionDetalle.get(i).setPrdLiquidacion(prdLiquidacion);
        }
        //limpia los detalles
        prdLiquidacion.setPrdLiquidacionDetalleList(new ArrayList<>());
        //agrega todos los detalles
        prdLiquidacion.getPrdLiquidacionDetalleList().addAll(listaPrdLiquidacionDetalle);

        saveOrUpdate(prdLiquidacion);

        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                liquidacionDetalleDao.eliminarPorSql(list.get(i).getDetSecuencial());
            }
        }
        sucesoDao.insertar(sisSuceso);
        //////////////crear suceso///////////
        SisSucesoLiquidacionPesca sucesoLiq = new SisSucesoLiquidacionPesca();
        PrdLiquidacion copia = ConversionesProduccion.convertirPrdLiquidacion_PrdLiquidacion(prdLiquidacion);
        if (copia.getInvCliente() != null && (copia.getInvCliente().getInvClientePK().getCliCodigo() == null
                || copia.getInvCliente().getInvClientePK().getCliCodigo().equals(""))) {
            copia.setInvCliente(null);
        }
        if (copia.getInvProveedor() != null && (copia.getInvProveedor().getInvProveedorPK().getProvCodigo() == null
                || copia.getInvProveedor().getInvProveedorPK().getProvCodigo().equals(""))) {
            copia.setInvProveedor(null);
        }
        if (copia.getPrdPreLiquidacion() != null && (copia.getPrdPreLiquidacion().getPrdPreLiquidacionPK().getPlNumero() == null
                || copia.getPrdPreLiquidacion().getPrdPreLiquidacionPK().getPlNumero().equals(""))) {
            copia.setPrdPreLiquidacion(null);
        }
        List<PrdLiquidacionDetalle> detalleCopia = new ArrayList<>();
        for (PrdLiquidacionDetalle en : listaPrdLiquidacionDetalle) {
            PrdLiquidacionDetalle det = ConversionesProduccion.convertirPrdLiquidacionDetalle_PrdLiquidacionDetalle(en);
            det.setPrdLiquidacion(null);
            detalleCopia.add(det);
        }
        copia.setPrdLiquidacionDetalleList(detalleCopia);
        String json = UtilsJSON.objetoToJson(copia);
        sucesoLiq.setSusJson(json);
        sucesoLiq.setSisSuceso(sisSuceso);
        sucesoLiq.setPrdLiquidacion(copia);
        sucesoLiquidacionPescaDao.insertar(sucesoLiq);
        return true;
    }

    @Override
    public PrdLiquidacion getPrdLiquidacion(PrdLiquidacionPK liquidacionPK) {
        return obtenerObjetoPorHql(
                "select distinct lp from PrdLiquidacion lp inner join lp.prdLiquidacionPK lppk inner join fetch lp.prdLiquidacionDetalleList "
                + "where lppk.liqEmpresa=?1 and lppk.liqMotivo=?2 and lppk.liqNumero=?3",
                new Object[]{liquidacionPK.getLiqEmpresa(), liquidacionPK.getLiqMotivo(),
                    liquidacionPK.getLiqNumero()});
    }

    @Override
    public List<PrdLiquidacion> getListaPrdLiquidacion(String empresa) {
        return obtenerPorHql(
                "select distinct lp from PrdLiquidacion lp inner join lp.prdLiquidacionPK lppk inner join fetch lp.prdLiquidacionDetalleList "
                + "where lppk.liqEmpresa=?1",
                new Object[]{empresa});
    }

    @Override
    public void desmayorizarPrdLiquidacion(PrdLiquidacionPK liquidacionPK, SisSuceso sisSuceso) {
        String sql = "UPDATE produccion.prd_liquidacion SET liq_pendiente=true WHERE liq_empresa='"
                + liquidacionPK.getLiqEmpresa() + "' and liq_motivo='" + liquidacionPK.getLiqMotivo()
                + "' and liq_numero='" + liquidacionPK.getLiqNumero() + "';";
        sisSuceso.setSusDetalle("Se desmayorizó la liquidación: " + liquidacionPK.getLiqNumero()
                + ", con el motivo: " + liquidacionPK.getLiqMotivo());
        sucesoDao.insertar(sisSuceso);
        genericSQLDao.ejecutarSQL(sql);
    }

    @Override
    public void anularRestaurarPrdLiquidacion(PrdLiquidacionPK liquidacionPK, boolean anularRestaurar, SisSuceso sisSuceso) {
        String sql = "UPDATE produccion.prd_liquidacion SET liq_anulado=" + anularRestaurar + " WHERE liq_empresa='"
                + liquidacionPK.getLiqEmpresa() + "' and liq_motivo='" + liquidacionPK.getLiqMotivo()
                + "' and liq_numero='" + liquidacionPK.getLiqNumero() + "';";
        sisSuceso.setSusDetalle("Se " + (anularRestaurar ? "Anulo" : "Restauró") + " la liquidación:" + liquidacionPK.getLiqNumero()
                + ", con el motivo: " + liquidacionPK.getLiqMotivo());
        sucesoDao.insertar(sisSuceso);
        genericSQLDao.ejecutarSQL(sql);
    }

    @Override
    public PrdLiquidacionTO getBuscaObjLiquidacionPorLote(String liqLote, String empresa) throws Exception {
        String sql = "SELECT * FROM produccion.prd_liquidacion WHERE prd_liquidacion.liq_lote = '" + liqLote + "' and liq_empresa='" + empresa + "'";
        return genericSQLDao.obtenerObjetoPorSql(sql, PrdLiquidacionTO.class);
    }

    @Override
    public List<ListaLiquidacionTO> getListaPrdConsultaLiquidacionTO(String empresa, String sector, String piscina, String busqueda, String nRegistros) throws Exception {
        String limit = "";
        piscina = piscina == null ? piscina : "'" + piscina + "'";
        if (nRegistros != null && nRegistros.compareTo("") != 0 && nRegistros.compareTo("0") != 0) {
            limit = " limit " + nRegistros;
        }
        String sql = "SELECT * FROM produccion.fun_liquidacion_listado('" + empresa + "','" + sector + "',"
                + piscina + ",'" + busqueda + "')" + " Order by id DESC " + limit;
        return genericSQLDao.obtenerPorSql(sql, ListaLiquidacionTO.class);
    }

    @Override
    public List<ListaLiquidacionTO> getListaPrdConsultaLiquidacionTOSoloActivas(String empresa, String proveedor, String cliente, String motivo, String numero, String nRegistros) throws Exception {
        String limit = "";
        String sql = "";
        motivo = motivo == null ? motivo : "'" + motivo + "'";
        numero = numero == null ? numero : "'" + numero + "'";
        if (nRegistros != null && nRegistros.compareTo("") != 0 && nRegistros.compareTo("0") != 0) {
            limit = " limit " + nRegistros;
        }

        if (cliente != null && !cliente.equals("")) {

            sql = "SELECT * FROM produccion.fun_liquidacion_ventas_listado('" + empresa + "'," + motivo + "," + numero + ",'" + cliente + "')" + limit;
        } else {

            sql = "SELECT * FROM produccion.fun_liquidacion_compras_listado('" + empresa + "'," + motivo + "," + numero + ",'" + proveedor + "')" + limit;
        }

        return genericSQLDao.obtenerPorSql(sql, ListaLiquidacionTO.class);
    }

    @Override
    public List<PrdFunLiquidacionConsolidandoTallasTO> getPrdFunLiquidacionConsolidandoTallasTO(String empresa, String desde, String hasta, String sector, String cliente, String piscina) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        piscina = piscina == null ? piscina : "'" + piscina + "'";
        String sql = "SELECT * FROM produccion.fun_liquidacion_consolidando_tallas('" + empresa + "', " + desde + ", "
                + hasta + ", " + sector + "," + cliente + "," + piscina + ")";
        return genericSQLDao.obtenerPorSql(sql, PrdFunLiquidacionConsolidandoTallasTO.class);
    }

    //liquidacion consolidanco clientes
    @Override
    public List<PrdLiquidacionConsolidandoClientesTO> getPrdFunLiquidacionConsolidandoClientesTO(String empresa, String sector, String desde, String hasta, String cliente) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        String sql = "SELECT * FROM produccion.fun_liquidacion_consolidando_clientes('" + empresa + "', " + sector + ", "
                + desde + ", " + hasta + ", " + cliente + ")";
        return genericSQLDao.obtenerPorSql(sql, PrdLiquidacionConsolidandoClientesTO.class);
    }

    //liquidacion consolidanco proveedores
    @Override
    public List<PrdLiquidacionConsolidandoProveedoresTO> getPrdFunLiquidacionConsolidandoProveedoresTO(String empresa, String sector, String desde, String hasta, String proveedor, String comisionista) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        proveedor = proveedor == null ? proveedor : "'" + proveedor + "'";
        comisionista = comisionista == null ? comisionista : "'" + comisionista + "'";
        String sql = "SELECT * FROM produccion.fun_liquidacion_consolidando_proveedores('" + empresa + "', " + sector + ", "
                + desde + ", " + hasta + ", " + proveedor + ", " + comisionista + ")";
        return genericSQLDao.obtenerPorSql(sql, PrdLiquidacionConsolidandoProveedoresTO.class);
    }

    //listar liquidacion consultas
    @Override
    public List<PrdLiquidacionConsultaTO> getPrdFunLiquidacionConsultaTO(String empresa, String sector, String piscina, String desde, String hasta, boolean incluirAnuladas) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        piscina = piscina == null ? piscina : "'" + piscina + "'";
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        String sql = "SELECT * FROM produccion.fun_liquidacion_consulta(" + empresa + ", " + sector + ", " + piscina + ","
                + desde + ", " + hasta + ", " + incluirAnuladas + ")";
        List<PrdLiquidacionConsultaTO> prd = genericSQLDao.obtenerPorSql(sql, PrdLiquidacionConsultaTO.class);
        return prd;
    }

    //listar liquidacion consultas Empacadora
    @Override
    public List<PrdLiquidacionConsultaEmpacadoraTO> getPrdFunLiquidacionConsultaEmpacadoraTO(String empresa, String sector, String piscina, String desde, String hasta, String proveedor, boolean incluirAnuladas, String comisionista) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        piscina = piscina == null ? piscina : "'" + piscina + "'";
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        proveedor = proveedor == null ? proveedor : "'" + proveedor + "'";
        comisionista = comisionista == null ? comisionista : "'" + comisionista + "'";
        String sql = "SELECT * FROM produccion.fun_liquidacion_consulta_empacadora(" + empresa + ", " + sector + ", " + piscina + ","
                + desde + ", " + hasta + ", " + proveedor + ", " + incluirAnuladas + "," + comisionista + ")";
        List<PrdLiquidacionConsultaEmpacadoraTO> prd = genericSQLDao.obtenerPorSql(sql, PrdLiquidacionConsultaEmpacadoraTO.class);
        return prd;
    }

    @Override
    public List<PrdLiquidacionDetalleProductoTO> getListadoLiquidacionDetalleProductoTO(String empresa, String sector, String piscina, String desde, String hasta, String cliente, String talla, String tipo) throws Exception {

        empresa = empresa == null ? empresa : "'" + empresa + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        piscina = piscina == null ? piscina : "'" + piscina + "'";
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        talla = talla == null ? talla : "'" + talla + "'";
        tipo = tipo == null ? tipo : "'" + tipo + "'";
        String sql = "SELECT * FROM produccion.fun_liquidacion_detalle_producto(" + empresa + "," + sector + ","
                + piscina + "," + desde + ", " + hasta + "," + cliente + "," + talla + "," + tipo + ")";

        return genericSQLDao.obtenerPorSql(sql, PrdLiquidacionDetalleProductoTO.class);
    }

    @Override
    public List<PrdLiquidacionDetalleProductoEmpacadoraTO> getListadoLiquidacionDetalleProductoEmpacadoraTO(String empresa, String sector, String piscina, String desde, String hasta, String proveedor, String talla, String tipo, String comisionista) throws Exception {

        empresa = empresa == null ? empresa : "'" + empresa + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        piscina = piscina == null ? piscina : "'" + piscina + "'";
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        proveedor = proveedor == null ? proveedor : "'" + proveedor + "'";
        talla = talla == null ? talla : "'" + talla + "'";
        tipo = tipo == null ? tipo : "'" + tipo + "'";
        comisionista = comisionista == null ? comisionista : "'" + comisionista + "'";
        String sql = "SELECT * FROM produccion.fun_liquidacion_detalle_producto_empacadora(" + empresa + "," + sector + ","
                + piscina + "," + desde + ", " + hasta + "," + proveedor + "," + talla + "," + tipo + "," + comisionista + ")";

        return genericSQLDao.obtenerPorSql(sql, PrdLiquidacionDetalleProductoEmpacadoraTO.class);
    }

    @Override
    public List<PrdLiquidacionesDetalleTO> listarLiquidacionesDetalle(String empresa, String desde, String hasta) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        String sql = "SELECT * FROM produccion.fun_liquidaciones_detalle(" + empresa + "," + desde + ", " + hasta + ")";
        return genericSQLDao.obtenerPorSql(sql, PrdLiquidacionesDetalleTO.class);
    }

    @Override
    public List<PrdLiquidacionDetalleProductoTO> getListadoLiquidacionConsolidadoProductoTO(String empresa, String sector, String piscina, String desde, String hasta, String cliente) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        piscina = piscina == null ? piscina : "'" + piscina + "'";
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        cliente = cliente == null ? cliente : "'" + cliente + "'";
        String sql = "SELECT * FROM produccion.fun_liquidacion_detalle_producto(" + empresa + "," + sector + ","
                + piscina + "," + desde + ", " + hasta + "," + cliente + ")";

        return genericSQLDao.obtenerPorSql(sql, PrdLiquidacionDetalleProductoTO.class);
    }

    @Override
    public boolean verificarSiDesmayorizarAnularLiquidacion(String empresa, String motivo, String eNumero) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        motivo = motivo == null ? motivo : "'" + motivo + "'";
        eNumero = eNumero == null ? eNumero : "'" + eNumero + "'";
        String sql = "SELECT * FROM inventario.fun_verificar_si_desmayorizar_anular_liquidacion(" + empresa + "," + motivo + "," + eNumero + ")";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public List<PrdLiquidacionDatosAdjuntos> listarImagenesDeLiquidacionPesca(PrdLiquidacionPK pk) throws Exception {
        String sql = "select * from produccion.prd_liquidacion_datos_adjuntos where "
                + "liq_empresa = '" + pk.getLiqEmpresa()
                + "' and liq_motivo = '" + pk.getLiqMotivo()
                + "' and liq_numero = '" + pk.getLiqNumero() + "' ";
        return genericSQLDao.obtenerPorSql(sql, PrdLiquidacionDatosAdjuntos.class);
    }

    @Override
    public List<PrdFunLiquidacionConsolidandoTallasProveedorTO> getPrdFunLiquidacionConsolidandoTallasProveedor(String empresa, String desde, String hasta, String sector, String proveedor, String piscina, String comisionista) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        proveedor = proveedor == null ? proveedor : "'" + proveedor + "'";
        piscina = piscina == null ? piscina : "'" + piscina + "'";
        comisionista = comisionista == null ? comisionista : "'" + comisionista + "'";
        String sql = "SELECT * FROM produccion.fun_liquidacion_consolidando_tallas_proveedor('" + empresa + "', " + desde + ", "
                + hasta + ", " + sector + "," + proveedor + "," + piscina + "," + comisionista + ")";
        return genericSQLDao.obtenerPorSql(sql, PrdFunLiquidacionConsolidandoTallasProveedorTO.class);
    }

    @Override
    public List<String> listadoComisionista(String empresa) throws Exception {
        String sql = "SELECT liq_comisionista FROM produccion.prd_liquidacion WHERE liq_comisionista IS NOT NULL AND liq_comisionista != '' AND liq_empresa='" + empresa + "' GROUP BY liq_comisionista";
        return genericSQLDao.obtenerPorSql(sql);
    }
}
