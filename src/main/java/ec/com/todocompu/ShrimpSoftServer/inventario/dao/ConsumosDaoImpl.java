package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoConsumoDao;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosImportarExportarTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunConsumosConsolidandoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvValidarStockTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosDetalleSeries;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoSaldos;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoConsumos;
import java.util.stream.Collectors;

@Repository
public class ConsumosDaoImpl extends GenericDaoImpl<InvConsumos, InvConsumosPK> implements ConsumosDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private ConsumosDetalleDao consumosDetalleDao;
    @Autowired
    private ConsumosMotivoAnulacionDao consumosMotivoAnulacionDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private ConsumosDetalleSeriesDao consumosDetalleSeriesDao;
    @Autowired
    private SucesoConsumoDao sucesoConsumoDao;

    @Override
    public InvConsumosTO getInvConsumoCabeceraTO(String empresa, String periodo, String motivo, String numeroConsumo)
            throws Exception {
        return ConversionesInventario.convertirInvConsumosCabecera_InvConsumosCabeceraTO(
                obtenerPorId(InvConsumos.class, new InvConsumosPK(empresa, periodo, motivo, numeroConsumo)));
    }

    @Override
    public boolean modificarInvConsumos(InvConsumos invConsumos, List<InvConsumosDetalle> listaInvDetalle,
            List<InvConsumosDetalle> listaInvDetalleEliminar, SisSuceso sisSuceso,
            List<InvProductoSaldos> listaInvProductoSaldos, InvConsumosMotivoAnulacion invConsumosMotivoAnulacion,
            boolean desmayorizar) throws Exception {
        if (!invConsumos.getConsAnulado()) {
            invConsumos.setConsPendiente(true);
        }
        actualizar(invConsumos);

        if (!invConsumos.getConsAnulado()) {
            if (!desmayorizar) {
                for (int i = 0; i < listaInvDetalle.size(); i++) {
                    listaInvDetalle.get(i).setDetPendiente(true);
                    listaInvDetalle.get(i).setInvConsumos(invConsumos);
                    consumosDetalleDao.saveOrUpdate(listaInvDetalle.get(i));
                }
            }
        }

        if (!listaInvDetalleEliminar.isEmpty()) {
            for (int i = 0; i < listaInvDetalleEliminar.size(); i++) {
                listaInvDetalleEliminar.get(i).setInvConsumos(invConsumos);
                consumosDetalleDao.eliminarPorSql(listaInvDetalleEliminar.get(i).getDetSecuencial());
            }
        }

        sucesoDao.insertar(sisSuceso);

        if (invConsumosMotivoAnulacion != null) {
            consumosMotivoAnulacionDao.insertar(invConsumosMotivoAnulacion);
        }

        return true;
    }

    @Override
    public int buscarConteoUltimaNumeracionConsumo(String empCodigo, String perCodigo, String motCodigo)
            throws Exception {
        String sql = "SELECT " + "num_secuencia FROM inventario.inv_consumos_numeracion " + "WHERE num_empresa = ('"
                + empCodigo + "') AND num_periodo = " + "('" + perCodigo + "') AND num_motivo = ('" + motCodigo + "')";
        Object objeto = genericSQLDao.obtenerObjetoPorSql(sql);
        return objeto == null ? 0 : Integer.parseInt((String) UtilsConversiones.convertir(objeto));
    }

    @Override
    public List<InvValidarStockTO> getListaInvConsumosValidarStockTO(String empresa, String periodo, String motivo,
            String numero, String accion) {
        String sql = "SELECT * FROM inventario.fun_sw_consumos_validar_stock('" + empresa + "','" + periodo + "','"
                + motivo + "','" + numero + "','" + accion + "');";
        return genericSQLDao.obtenerPorSql(sql, InvValidarStockTO.class);
    }

    @Override
    public List<InvConsumosImportarExportarTO> getListaInvConsumosImportarExportarTO(String empresa, String desde,
            String hasta) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        String sql = "SELECT inv_consumos.cons_empresa, inv_consumos.cons_periodo, "
                + "inv_consumos.cons_motivo, inv_consumos.cons_numero, cons_referencia, cons_fecha, "
                + "cons_observaciones, cons_pendiente, cons_revisado, cons_anulado, con_empresa, "
                + "con_periodo, con_tipo, con_numero, usr_empresa, usr_codigo, usr_fecha_inserta, "
                + "0 det_secuencial, det_orden, det_cantidad, det_valor_promedio, "
                + "det_valor_ultima_compra, det_saldo, bod_empresa, bod_codigo, pro_empresa, "
                + "pro_codigo_principal, sec_empresa, sec_codigo, pis_empresa, pis_sector, " + "pis_numero "
                + "FROM inventario.inv_consumos INNER JOIN inventario.inv_consumos_detalle "
                + "ON inv_consumos.cons_empresa = inv_consumos_detalle.cons_empresa AND "
                + "inv_consumos.cons_periodo = inv_consumos_detalle.cons_periodo AND "
                + "inv_consumos.cons_motivo = inv_consumos_detalle.cons_motivo AND "
                + "inv_consumos.cons_numero = inv_consumos_detalle.cons_numero " + "WHERE inv_consumos.cons_empresa='"
                + empresa + "' AND cons_fecha BETWEEN " + desde + " AND " + hasta + "AND NOT cons_pendiente";
        return genericSQLDao.obtenerPorSql(sql, InvConsumosImportarExportarTO.class);
    }

    @Override
    public List<InvListaConsultaConsumosTO> getFunConsumosListado(String empresa, String fechaDesde, String fechaHasta,
            String status) throws Exception {
        String pendiente = null;
        String anulado = null;
        if (status.equals("PENDIENTE")) {
            pendiente = "'PENDIENTE'";
            anulado = null;
        }
        if (status.equals("ANULADO")) {
            pendiente = null;
            anulado = "'ANULADO'";
        }
        if (status.equals("AMBOS")) {
            pendiente = "'PENDIENTE'";
            anulado = "'ANULADO'";
        }
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        String sql = "SELECT * FROM inventario.fun_consumos_listado('" + empresa + "', null, null, null, null, null, null, null) "
                + "WHERE cons_fecha >= " + fechaDesde + " AND cons_fecha <= " + fechaHasta + " "
                + "AND cons_pendiente = " + pendiente + " OR cons_anulado = " + anulado + "";
        return genericSQLDao.obtenerPorSql(sql, InvListaConsultaConsumosTO.class);
    }

    @Override
    public List<InvFunConsumosConsolidandoProductosTO> getInvFunConsumosConsolidandoProductosTO(String empresa,
            String desde, String hasta, String sector, String bodega) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        bodega = bodega == null ? bodega : "'" + bodega + "'";
        String sql = "SELECT * FROM inventario.fun_consumos_consolidando_productos('" + empresa + "', " + desde + ", "
                + hasta + ", " + sector + ", " + bodega + ", null)";
        return genericSQLDao.obtenerPorSql(sql, InvFunConsumosConsolidandoProductosTO.class);
    }

    @Override
    public List<InvListaConsultaConsumosTO> getListaInvConsultaConsumos(String empresa, String periodo, String motivo, String cliente, String proveedor, String producto, String empleado,
            String busqueda, String nRegistros) throws Exception {
        cliente = cliente != null && !cliente.equals("") ? "'" + cliente + "'" : null;
        proveedor = proveedor != null && !proveedor.equals("") ? "'" + proveedor + "'" : null;
        producto = producto != null && !producto.equals("") ? "'" + producto + "'" : null;
        empleado = empleado != null && !empleado.equals("") ? "'" + empleado + "'" : null;
        String limit = "";
        if (nRegistros != null && nRegistros.compareTo("") != 0 && nRegistros.compareTo("0") != 0) {
            limit = " limit " + nRegistros;
        }
        String sql = "SELECT * FROM inventario.fun_consumos_listado('" + empresa + "', '" + periodo + "', '" + motivo
                + "', '" + busqueda + "', " + cliente + ", " + proveedor + ", " + producto + ", " + empleado + ")" + limit;
        return genericSQLDao.obtenerPorSql(sql, InvListaConsultaConsumosTO.class);
    }

    @Override
    public List<InvFunConsumosTO> getInvFunConsumosTO(String empresa, String desde, String hasta, String motivo)
            throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        motivo = motivo == null ? motivo : "'" + motivo + "'";
        String sql = "SELECT * FROM inventario.fun_consumos('" + empresa + "', " + desde + ", " + hasta + ", " + motivo
                + ")";
        return genericSQLDao.obtenerPorSql(sql, InvFunConsumosTO.class);
    }

    @Override
    public Boolean reconstruirCostos(String empCodigo, String Producto, String periodoHasta, Boolean periodoEstado)
            throws Exception {
        periodoHasta = periodoHasta == null ? periodoHasta : "'" + periodoHasta + "'";
        String sql = "SELECT * FROM " + "inventario.fun_reconstruir_costos('" + empCodigo + "', " + Producto + ", "
                + periodoHasta + ", " + periodoEstado + ")";

        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

    @Override
    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivoTipo, String numero)
            throws Exception {
        String sql = "SELECT cons_pendiente as comp_pendiente, cons_anulado as comp_anulado, cons_pendiente as est_bloqueado, cons_revisado as est_generado, false as con_reversado FROM inventario.inv_consumos WHERE "
                + "cons_empresa = '" + empresa + "' AND cons_periodo = '" + periodo + "' " + "AND cons_motivo = '"
                + motivoTipo + "' AND cons_numero = '" + numero + "'";
        return genericSQLDao.obtenerPorSql(sql, InvEstadoCCCVT.class).isEmpty() ? null
                : genericSQLDao.obtenerPorSql(sql, InvEstadoCCCVT.class).get(0);
    }

    @Override
    public boolean insertarModificarInvConsumos(InvConsumos invConsumos, List<InvConsumosDetalle> listaInvConsumosDetalle, SisSuceso sisSuceso, boolean ignorarSeries) throws Exception {
        List<InvConsumosDetalle> listadoCopia = listaInvConsumosDetalle.stream().collect(Collectors.toList());
        for (int i = 0; i < listadoCopia.size(); i++) {
            listadoCopia.get(i).setInvConsumos(null);
        }
        if (invConsumos.getInvConsumosPK().getConsNumero() == null || invConsumos.getInvConsumosPK().getConsNumero().compareToIgnoreCase("") == 0) {
            String rellenarConCeros = "";
            int numeracion = buscarConteoUltimaNumeracionConsumo(invConsumos.getInvConsumosPK().getConsEmpresa(),
                    invConsumos.getInvConsumosPK().getConsPeriodo(), invConsumos.getInvConsumosPK().getConsMotivo());
            do {
                numeracion++;
                int numeroCerosAponer = 7 - String.valueOf(numeracion).trim().length();
                rellenarConCeros = "";
                for (int i = 0; i < numeroCerosAponer; i++) {
                    rellenarConCeros = rellenarConCeros + "0";
                }
                invConsumos.getInvConsumosPK().setConsNumero(rellenarConCeros + numeracion);
            } while (buscarInvConsumos(invConsumos.getInvConsumosPK()) != null);
        }
        String pendienteMensaje = invConsumos.getConsPendiente() ? " en pendiente" : "";
        sisSuceso.setSusClave(invConsumos.getInvConsumosPK().getConsPeriodo() + " " + invConsumos.getInvConsumosPK().getConsMotivo() + " " + invConsumos.getInvConsumosPK().getConsNumero());
        sisSuceso.setSusDetalle("Consumo por " + invConsumos.getConsObservaciones() + pendienteMensaje);
        ////////////// Inserta el consumo///////////////////
        List<InvConsumosDetalle> list = invConsumos.getInvConsumosDetalleList();
        for (int i = 0; i < listaInvConsumosDetalle.size(); i++) {
            if (listaInvConsumosDetalle.get(i).getDetPendiente() == false) {
                listaInvConsumosDetalle.get(i).setDetSecuencial(null);
            }
            //************SERIES*********************
            if (listaInvConsumosDetalle.get(i).getDetSecuencial() != null && !ignorarSeries) {
                List<InvConsumosDetalleSeries> seriesEnLaBase = consumosDetalleSeriesDao.listarSeriesPorSecuencialDetalle(listaInvConsumosDetalle.get(i).getDetSecuencial());
                if (seriesEnLaBase != null && !seriesEnLaBase.isEmpty()) {
                    List<InvConsumosDetalleSeries> seriesEntrantes = listaInvConsumosDetalle.get(i).getInvConsumosDetalleSeriesList();
                    if (seriesEntrantes == null) {
                        seriesEntrantes = new ArrayList<>();
                    }
                    seriesEnLaBase.removeAll(seriesEntrantes);
                    seriesEnLaBase.forEach((serie) -> {
                        consumosDetalleSeriesDao.eliminar(serie);
                    });
                }
            }
            //FIN SERIES
            listaInvConsumosDetalle.get(i).setDetOrden(i + 1);
            listaInvConsumosDetalle.get(i).setDetPendiente(true);
            listaInvConsumosDetalle.get(i).setInvConsumos(invConsumos);
        }
        if (list != null && !list.isEmpty()) {
            for (InvConsumosDetalle invConsumosDetalle : list) {
                invConsumosDetalle.setDetPendiente(true);
            }
        }
        invConsumos.setConsPendiente(true);
        invConsumos.setInvConsumosDetalleList(new ArrayList<InvConsumosDetalle>());
        invConsumos.getInvConsumosDetalleList().addAll(listaInvConsumosDetalle);
        saveOrUpdate(invConsumos);

        if (list != null && !list.isEmpty()) {
            list.removeAll(listaInvConsumosDetalle);
            for (int i = 0; i < list.size(); i++) {
                consumosDetalleDao.eliminarPorSql(list.get(i).getDetSecuencial());
            }
        }

        sucesoDao.insertar(sisSuceso);
        //suceso consumo
        SisSucesoConsumos sucesoConsumo = new SisSucesoConsumos();
        InvConsumos copia = ConversionesInventario.convertirInvConsumos_InvConsumos(invConsumos);
        if (copia.getInvProveedor() == null || (copia.getInvProveedor() != null && (copia.getInvProveedor().getInvProveedorPK().getProvCodigo() == null || copia.getInvProveedor().getInvProveedorPK().getProvCodigo().equals("")))) {
            copia.setInvProveedor(null);
        }
        if (copia.getInvCliente() == null || (copia.getInvCliente() != null && (copia.getInvCliente().getInvClientePK().getCliCodigo() == null || copia.getInvCliente().getInvClientePK().getCliCodigo().equals("")))) {
            copia.setInvCliente(null);
        }
        if (copia.getRhEmpleado() == null || (copia.getRhEmpleado() != null && (copia.getRhEmpleado().getRhEmpleadoPK().getEmpId() == null || copia.getRhEmpleado().getRhEmpleadoPK().getEmpId().equals("")))) {
            copia.setRhEmpleado(null);
        }
        copia.setInvConsumosDetalleList(listadoCopia);
        String json = UtilsJSON.objetoToJson(copia);
        sucesoConsumo.setSusJson(json);
        sucesoConsumo.setSisSuceso(sisSuceso);
        sucesoConsumo.setInvConsumos(copia);
        sucesoConsumoDao.insertar(sucesoConsumo);
        ////////////////////////////////
        return true;
    }

    @Override
    public InvConsumos buscarInvConsumos(String empresa, String perCodigo, String motCodigo, String compNumero)
            throws Exception {
        InvConsumos consumo = obtenerPorId(InvConsumos.class, new InvConsumosPK(empresa, perCodigo, motCodigo, compNumero));
        return consumo;
    }

    private InvConsumos buscarInvConsumos(InvConsumosPK invConsumosPK) throws Exception {
        return obtenerPorId(InvConsumos.class, invConsumosPK);
    }

    @Override
    public void actualizarPendienteSql(InvConsumosPK invConsumosPK, boolean pendiente, SisSuceso sisSuceso) {
        String sql = "UPDATE inventario.inv_consumos SET cons_pendiente=" + pendiente + " WHERE cons_empresa='"
                + invConsumosPK.getConsEmpresa() + "' and  cons_periodo='" + invConsumosPK.getConsPeriodo()
                + "' and cons_motivo='" + invConsumosPK.getConsMotivo() + "' and cons_numero='"
                + invConsumosPK.getConsNumero() + "';";
        sisSuceso.setSusDetalle("Se " + (pendiente ? "desmayorizo" : "mayorizo") + " el consumo: " + invConsumosPK.getConsPeriodo() + " | " + invConsumosPK.getConsMotivo() + " | " + invConsumosPK.getConsNumero());
        sucesoDao.insertar(sisSuceso);
        genericSQLDao.ejecutarSQL(sql);
    }

    @Override
    public void anularRestaurarSql(InvConsumosPK invConsumosPK, boolean anulado, SisSuceso sisSuceso) {
        String sql = "UPDATE inventario.inv_consumos SET cons_pendiente = false, cons_anulado=" + anulado + " WHERE cons_empresa='"
                + invConsumosPK.getConsEmpresa() + "' and  cons_periodo='" + invConsumosPK.getConsPeriodo()
                + "' and cons_motivo='" + invConsumosPK.getConsMotivo() + "' and cons_numero='"
                + invConsumosPK.getConsNumero() + "';";
        sisSuceso.setSusDetalle("Se " + (anulado ? "anuló" : "restauró") + " el consumo: " + invConsumosPK.getConsPeriodo() + " | " + invConsumosPK.getConsMotivo() + " | " + invConsumosPK.getConsNumero());
        sucesoDao.insertar(sisSuceso);
        genericSQLDao.ejecutarSQL(sql);
    }

    @Override
    public String eliminarConsumo(InvConsumosPK invConsumosPK, SisSuceso sisSuceso) {
        sisSuceso.setSusDetalle("Se eliminó el consumo: " + invConsumosPK.getConsPeriodo() + " | " + invConsumosPK.getConsMotivo() + " | " + invConsumosPK.getConsNumero());
        sucesoDao.insertar(sisSuceso);
        String sql = "SELECT * FROM inventario.fun_eliminar_consumos ('"
                + invConsumosPK.getConsEmpresa() + "', '" + invConsumosPK.getConsPeriodo()
                + "', '" + invConsumosPK.getConsMotivo() + "', '" + invConsumosPK.getConsNumero()
                + "');";
        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public boolean comprobarInvConsumosCodigoAleatorio(String codAleatorio) throws Exception {
        String sql = "SELECT COUNT(*) " + "FROM inventario.inv_consumos WHERE cons_codigo = '" + codAleatorio + "'";
        return ((int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql)) == 0);
    }

    @Override
    public List<InvFunConsumosConsolidandoProductosTO> obtenerInvFunConsumosConsolidandoProductosTO(String empresa, String desde,
            String hasta, String sector, String bodega, String motivo) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        bodega = bodega == null ? bodega : "'" + bodega + "'";
        motivo = motivo == null ? motivo : "'" + motivo + "'";

        String sql = "SELECT * FROM inventario.fun_consumos_consolidando_productos('" + empresa + "', " + desde + ", "
                + hasta + ", " + sector + ", " + bodega + ", " + motivo + ")";

        return genericSQLDao.obtenerPorSql(sql, InvFunConsumosConsolidandoProductosTO.class);
    }

    @Override
    public List<InvConsumosDatosAdjuntos> listarImagenesDeConsumo(InvConsumosPK invConsumosPK) throws Exception {
        String sql = "select * from inventario.inv_consumos_datos_adjuntos where "
                + "cons_empresa = '" + invConsumosPK.getConsEmpresa()
                + "' and cons_periodo = '" + invConsumosPK.getConsPeriodo()
                + "' and cons_motivo = '" + invConsumosPK.getConsMotivo()
                + "' and cons_numero = '" + invConsumosPK.getConsNumero() + "' ";
        return genericSQLDao.obtenerPorSql(sql, InvConsumosDatosAdjuntos.class);
    }

}
