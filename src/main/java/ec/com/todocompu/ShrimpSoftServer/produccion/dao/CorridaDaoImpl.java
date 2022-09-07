package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoCorridaDao;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesProduccion;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.CostoDetalleMultipleCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.MultiplePiscinaCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdComboCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdConsumosDetalladoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdConsumosDiariosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdContabilizarCorridaCostoVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdContabilizarCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCorridaListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCostoDetalleFechaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCostoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCostosDiariosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunAnalisisVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunCostosPorFechaSimpleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaConsultaGrameajePromedioPorPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaCostosDetalleCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaFunAnalisisPesosComplementoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaFunAnalisisPesosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenCorridaSubcategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenFinancieroTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenPescaSiembraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenTrazabilidadAlimentacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdTrazabilidadCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdUtilidadDiariaCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorridaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorridaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoCorrida;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@Repository
public class CorridaDaoImpl extends GenericDaoImpl<PrdCorrida, PrdCorridaPK> implements CorridaDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private CorridaDetalleDao corridaDetalleDao;
    @Autowired
    private SucesoDao sisSucesoDao;
    @Autowired
    private SucesoCorridaDao sucesoCorridaDao;

    @Override
    public boolean modificarBooleanoCorrida(List<PrdCorrida> prdCorrida) throws Exception {
        for (int i = 0; i < prdCorrida.size(); i++) {
            actualizar(prdCorrida.get(i));
        }
        return true;
    }

    @Override
    public void actualizarSQL(String sql) throws Exception {
        genericSQLDao.ejecutarSQL(sql);
    }

    @Override
    public List<PrdListaCorridaTO> obtenerCorrida(String empresa, String sector, String piscina, Date fecha)
            throws Exception {
        String sql = "SELECT * FROM produccion.prd_corrida WHERE cor_empresa = ('" + empresa + "') AND "
                + "cor_sector = ('" + sector + "') AND cor_piscina = ('" + piscina + "') AND (('" + fecha
                + "' >= prd_corrida.cor_fecha_desde " + "AND '" + fecha + "' <= prd_corrida.cor_fecha_hasta) OR ('"
                + fecha + "' >= prd_corrida.cor_fecha_desde "
                + "AND prd_corrida.cor_fecha_hasta IS NULL)) ORDER BY cor_fecha_hasta";
        return genericSQLDao.obtenerPorSql(sql, PrdListaCorridaTO.class);
    }

    @Override
    public List<PrdListaCorridaTO> getListaCorrida(String empresa, String sector, String piscina, String fechaDesde, String fechaHasta, String tipo) throws Exception {
        String sql = "";
        if (tipo != null && !tipo.equals("")) {
            if (tipo.equals("SIEMBRA")) {
                sql = "SELECT cor_numero, cor_fecha_desde, cor_hectareas, "
                        + "cor_numero_larvas, cor_laboratorio, cor_nauplio, cor_pellet, cor_fecha_siembra, cor_fecha_siembra_madre, cor_tipo_siembra, cor_biomasa, "
                        + "cor_valor_venta, cor_observaciones, cor_fecha_pesca, cor_fecha_hasta, cor_activa, cor_numero_larvas_plus,cv_empresa,cv_periodo,cv_tipo,cv_numero "
                        + "FROM produccion.prd_corrida WHERE cor_empresa = ('" + empresa + "') AND cor_sector = ('" + sector
                        + "') AND cor_piscina = ('" + piscina + "')"
                        + " AND ('" + fechaDesde + "' <= prd_corrida.cor_fecha_siembra " + "AND '" + fechaHasta + "' >= prd_corrida.cor_fecha_siembra)"
                        + " ORDER BY cor_fecha_hasta DESC";
            } else {
                sql = "SELECT cor_numero, cor_fecha_desde, cor_hectareas, "
                        + "cor_numero_larvas, cor_laboratorio, cor_nauplio, cor_pellet, cor_fecha_siembra, cor_fecha_siembra_madre, cor_tipo_siembra, cor_biomasa, "
                        + "cor_valor_venta, cor_observaciones, cor_fecha_pesca, cor_fecha_hasta, cor_activa, cor_numero_larvas_plus,cv_empresa,cv_periodo,cv_tipo,cv_numero  "
                        + "FROM produccion.prd_corrida WHERE cor_empresa = ('" + empresa + "') AND cor_sector = ('" + sector
                        + "') AND cor_piscina = ('" + piscina + "')"
                        + " AND ('" + fechaDesde + "' <= prd_corrida.cor_fecha_pesca " + "AND '" + fechaHasta + "' >= prd_corrida.cor_fecha_pesca)"
                        + " ORDER BY cor_fecha_hasta DESC";
            }

        } else {
            sql = "SELECT cor_numero, cor_fecha_desde, cor_hectareas, "
                    + "cor_numero_larvas, cor_laboratorio, cor_nauplio, cor_pellet, cor_fecha_siembra, cor_fecha_siembra_madre, cor_tipo_siembra, cor_biomasa, "
                    + "cor_valor_venta, cor_observaciones, cor_fecha_pesca, cor_fecha_hasta, cor_activa, cor_numero_larvas_plus,cv_empresa,cv_periodo,cv_tipo,cv_numero  "
                    + "FROM produccion.prd_corrida WHERE cor_empresa = ('" + empresa + "') AND cor_sector = ('" + sector
                    + "') AND cor_piscina = ('" + piscina + "') ORDER BY cor_fecha_hasta DESC";
        }

        return genericSQLDao.obtenerPorSql(sql, PrdListaCorridaTO.class);
    }

    @Override
    public List<PrdCorridaListadoTO> listarCorridasPorFecha(String empresa, String desde, String hasta, String tipo) throws Exception {
        String sql = "SELECT * FROM produccion.fun_corrida_por_fechas('" + empresa + "', '" + tipo + "', '" + desde + "', '" + hasta + "' " + ");";
        return genericSQLDao.obtenerPorSql(sql, PrdCorridaListadoTO.class);
    }

    @Override
    public List<PrdListaCorridaTO> getListaCorrida(String empresa, String sector, String piscina, String corrida)
            throws Exception {
        String sql = "SELECT cor_numero, cor_fecha_desde, cor_hectareas, "
                + "cor_numero_larvas, cor_laboratorio, cor_nauplio, cor_pellet, cor_fecha_siembra, cor_fecha_siembra_madre, cor_tipo_siembra, cor_biomasa, "
                + "cor_valor_venta, cor_fecha_pesca, cor_fecha_hasta, cor_activa,cor_numero_larvas_plus,cv_empresa,cv_periodo,cv_tipo,cv_numero  FROM produccion.prd_corrida "
                + "WHERE cor_empresa = ('" + empresa + "') AND cor_sector = ('" + sector + "') AND "
                + "cor_piscina = ('" + piscina + "') AND cor_numero =  ('" + corrida + "') ORDER BY cor_fecha_hasta";
        return genericSQLDao.obtenerPorSql(sql, PrdListaCorridaTO.class);
    }

    @Override
    public List<PrdComboCorridaTO> getComboPrdCorrida(String empresa, String sector, String piscina) throws Exception {
        String sql = "SELECT cor_numero, cor_hectareas, cor_numero_larvas, "
                + "SUBSTRING(cast(cor_fecha_desde as TEXT), 1, 10) as desde, SUBSTRING(cast(cor_fecha_hasta as TEXT), 1, 10) as hasta FROM "
                + "produccion.prd_corrida WHERE (cor_empresa = '" + empresa + "') AND (cor_sector = '" + sector
                + "') AND " + "(cor_piscina = '" + piscina + "') ORDER BY cor_fecha_hasta";
        return genericSQLDao.obtenerPorSql(sql, PrdComboCorridaTO.class);
    }

    @Override
    public List<PrdComboCorridaTO> getComboPrdCorridaFiltrado(String empresa, String sector, String piscina, Date fecha) throws Exception {
        String fechaAuxiliar = (fecha == null ? null : "'" + UtilsDate.fechaFormatoString(fecha, "yyyy-MM-dd") + "'");
        String sql = "SELECT cor_numero, cor_hectareas, cor_numero_larvas, "
                + "SUBSTRING(cast(cor_fecha_desde as TEXT), 1, 10) as desde, SUBSTRING(cast(cor_fecha_hasta as TEXT), 1, 10) as hasta "
                + "FROM produccion.prd_corrida "
                + "WHERE (cor_empresa = '" + empresa + "') AND "
                + "(cor_sector = '" + sector + "') AND "
                + "(cor_piscina = '" + piscina + "') AND ("
                + fechaAuxiliar + " <= cor_fecha_desde OR ("
                + fechaAuxiliar + " >= cor_fecha_desde AND "
                + fechaAuxiliar + " <= COALESCE(cor_fecha_hasta,cast('2099-12-31' as DATE)))) "
                + "ORDER BY cor_fecha_hasta LIMIT 2";
        return genericSQLDao.obtenerPorSql(sql, PrdComboCorridaTO.class);
    }

    @Override
    public List<PrdCorrida> getComboPrdCorridaFiltradoFecha(String empresa, String sector, String piscina, Date fecha) {
        String fechaAuxiliar = (fecha == null ? null : "'" + UtilsDate.fechaFormatoString(fecha, "yyyy-MM-dd") + "'");
        String sql = "SELECT * FROM produccion.prd_corrida "
                + "WHERE (cor_empresa = '" + empresa + "') AND "
                + "(cor_sector = '" + sector + "') AND "
                + "(cor_piscina = '" + piscina + "') AND ("
                + fechaAuxiliar + " <= cor_fecha_desde OR ("
                + fechaAuxiliar + " >= cor_fecha_desde AND "
                + fechaAuxiliar + " <= COALESCE(cor_fecha_hasta,cast('2099-12-31' as DATE)))) "
                + "ORDER BY cor_fecha_hasta LIMIT 2";
        return genericSQLDao.obtenerPorSql(sql, PrdCorrida.class);
    }

    @Override
    public String consultarFechaMaxMin(String empresa, String tipoResumen) throws Exception {
        String sql;
        if (tipoResumen.equals("SIEMBRA")) {
            sql = "select substring(cast((min(cor_fecha_desde)) as text), 1, 10) ||' , '|| substring(cast ((max(cor_fecha_desde)) as text), 1,"
                    + " 10) from produccion.prd_corrida where cor_empresa =" + " ('" + empresa
                    + "') and cor_fecha_hasta is null";
        } else {
            sql = "SELECT SUBSTRING(cast(min(cor_fecha_hasta) as TEXT), 1, 10) ||' , '|| SUBSTRING(cast (max(cor_fecha_hasta) as TEXT), 1, 10)  "
                    + "FROM produccion.prd_corrida " + "WHERE cor_empresa = ('" + empresa
                    + "') AND cor_fecha_pesca IS NOT NULL;";
        }
        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public PrdCorridaTO getPrdCorridaTO(String empresa, String sector, String piscina, String fecha) throws Exception {
        String sql = "SELECT * FROM produccion.prd_corrida WHERE cor_empresa = ('" + empresa + "') AND "
                + "cor_sector = ('" + sector + "') AND cor_piscina = ('" + piscina + "') AND " + "(('" + fecha
                + "' >= prd_corrida.cor_fecha_desde AND '" + fecha + "' <= prd_corrida.cor_fecha_hasta) OR " + "('"
                + fecha
                + "' >= prd_corrida.cor_fecha_desde AND prd_corrida.cor_fecha_hasta IS NULL)) ORDER BY cor_fecha_hasta";
        return genericSQLDao.obtenerObjetoPorSql(sql, PrdCorridaTO.class);

    }

    @Override
    public List<MultiplePiscinaCorrida> getCostoDetallePersonalizado(String empresa, String sector, String fecha) {
        sector = sector.trim() == null ? null : "'" + sector + "'";
        String sql = "SELECT cor_empresa, cor_sector, cor_piscina, cor_numero, cor_fecha_desde, cor_fecha_hasta"
                + " FROM produccion.prd_corrida WHERE cor_empresa = ('" + empresa + "') AND CASE WHEN " + sector
                + " IS NULL THEN TRUE ELSE cor_sector = (" + sector + ") END AND " + "(('" + fecha
                + "' >= prd_corrida.cor_fecha_desde AND '" + fecha + "' <= prd_corrida.cor_fecha_hasta) OR " + "('"
                + fecha
                + "' >= prd_corrida.cor_fecha_desde AND prd_corrida.cor_fecha_hasta IS NULL)) ORDER BY 1,2,3,4;";
        return genericSQLDao.obtenerPorSql(sql, MultiplePiscinaCorrida.class);
    }

    @Override
    public List<PrdListaConsultaGrameajePromedioPorPiscinaTO> getListaConsultaGrameajePromedioPorPiscinaTOs(
            String empresa, String sector, String nombreSector) throws Exception {

        String sqlCondicion = (sector == null || sector.isEmpty() ? "" : (" AND gra_sector='" + sector + "'"));
        String sql = "SELECT gra_fecha, "
                + "gra_piscina || case when cor_fecha_siembra IS NULL then ' - ERROR (FALTA FECHA DE SIEMBRA)' else  '' end gra_piscina, "
                + "prd_corrida.cor_laboratorio gra_laboratorio, "
                + "round((coalesce(cor_numero_larvas, 0) + coalesce(cor_numero_larvas_plus, 0)) / nullif(cor_hectareas, 0), 2) gra_densidad, "
                + "coalesce(cor_fecha_siembra,current_date) cor_fecha_siembra, "
                + "(current_date - coalesce(cor_fecha_siembra,current_date) + 1) AS gra_edad, gra_tpromedio, gra_sobrevivencia,"
                + "row_number() over (partition by '' order by gra_fecha, gra_piscina) id "
                + "FROM produccion.prd_corrida " + "INNER JOIN produccion.prd_grameaje "
                + "ON prd_corrida.pis_empresa = prd_grameaje.pis_empresa "
                + "AND prd_corrida.pis_sector = prd_grameaje.pis_sector "
                + "AND prd_corrida.pis_numero = prd_grameaje.pis_numero "
                + "AND ((gra_fecha >= prd_corrida.cor_fecha_desde AND gra_fecha <= prd_corrida.cor_fecha_hasta) OR (gra_fecha >= prd_corrida.cor_fecha_desde AND prd_corrida.cor_fecha_hasta IS NULL)) "
                + "WHERE gra_empresa='" + empresa + "'" + sqlCondicion + " AND prd_corrida.cor_fecha_hasta IS NULL ORDER BY 1,2;";
        return genericSQLDao.obtenerPorSql(sql, PrdListaConsultaGrameajePromedioPorPiscinaTO.class);
    }

    @Override
    public List<PrdListaCostosDetalleCorridaTO> getPrdListaCostosDetalleCorridaTO(String empresa, String sector,
            String piscina, String corrida, String desde, String hasta, String agrupadoPor) throws Exception {
        agrupadoPor = agrupadoPor == null ? agrupadoPor : "'" + agrupadoPor + "'";
        hasta = hasta == null ? null : hasta;
        String sql = "SELECT * FROM produccion.fun_costos_detalle_corrida('" + empresa + "', '" + sector + "', "
                + "'" + piscina + "', " + "'" + corrida + "', " + desde + ", " + hasta + ", " + agrupadoPor + ");";
        return genericSQLDao.obtenerPorSql(sql, PrdListaCostosDetalleCorridaTO.class);
    }

    @Override
    public List<PrdResumenCorridaTO> getListaResumenCorridaTO(String empresa, String sector, String desde, String hasta, String tipoResumen) throws Exception {
        String sql;
        sector = sector == null ? null : "'" + sector + "'";
        if (tipoResumen.equals("SIEMBRA")) {
            sql = "SELECT * FROM produccion.fun_resumen_corrida_mejorado('" + empresa + "', " + sector + ", 'SIEMBRA', " + desde
                    + ", " + hasta + ")";
        } else {
            sql = "SELECT * FROM produccion.fun_resumen_corrida_mejorado('" + empresa + "', " + sector + ", '" + tipoResumen + "', " + desde
                    + ", " + hasta + ")";
        }
        return genericSQLDao.obtenerPorSql(sql, PrdResumenCorridaTO.class);
    }

    @Override
    public List<PrdContabilizarCorridaTO> getListaContabilizarCorridaTO(String empresa, String desde, String hasta) throws Exception {
        String sql = "SELECT * FROM produccion.fun_contabilizar_corrida ('" + empresa + "', " + desde + ", " + hasta
                + ")";
        return genericSQLDao.obtenerPorSql(sql, PrdContabilizarCorridaTO.class);
    }

    @Override
    public List<PrdResumenPescaSiembraTO> getResumenPesca(String empresa, String codigoSector, String fechaInicio,
            String fechaFin, boolean incluirTransferencia) throws Exception {
        codigoSector = codigoSector == null ? null : "'" + codigoSector + "'";
        String detallePesca = incluirTransferencia ? "'PESCA*'" : "'PESCA'";

        String sql = "SELECT * FROM produccion.fun_resumen_economico_corrida_mejorado('" + empresa + "', " + codigoSector
                + "," + detallePesca + ", " + fechaInicio + ", " + fechaFin + ", false)";
        return genericSQLDao.obtenerPorSql(sql, PrdResumenPescaSiembraTO.class);
    }

    @Override
    public List<PrdResumenPescaSiembraTO> getResumenSiembra(String empresa, String codigoSector, String fechaInicio,
            String fechaFin) throws Exception {
        codigoSector = codigoSector == null ? null : "'" + codigoSector + "'";
        String detalleSiembra = "'SIEMBRA'";
        String sql = "SELECT * FROM produccion.fun_resumen_economico_corrida_mejorado('" + empresa + "', " + codigoSector
                + "," + detalleSiembra + ", " + fechaInicio + ", " + fechaFin + ", false)";
        return genericSQLDao.obtenerPorSql(sql, PrdResumenPescaSiembraTO.class);
    }

    @Override
    public PrdFunAnalisisVentasTO getPrdFunAnalisisVentasTO(String empresa, String sector, String piscina,
            String fechaDesde, String fechaHasta) throws Exception {
        String sql = "SELECT * FROM produccion.fun_analisis_ventas('" + empresa + "', '" + sector + "', " + "'PESCA','"
                + fechaDesde + "', '" + fechaHasta + "') WHERE rc_piscina = '" + piscina + "';";
        return genericSQLDao.obtenerObjetoPorSql(sql, PrdFunAnalisisVentasTO.class);
    }

    @Override
    public List<PrdCostoDetalleFechaTO> getPrdListadoCostoDetalleFechaTO(String empresa, String secCodigo, String desde,
            String hasta, String agrupadoPor) throws Exception {
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        secCodigo = secCodigo == null ? secCodigo : "'" + secCodigo + "'";
        agrupadoPor = agrupadoPor == null ? agrupadoPor : "'" + agrupadoPor + "'";
        String sql = "SELECT * FROM produccion.fun_costos_detalle_por_fecha(" + "'" + empresa + "', " + secCodigo
                + ", " + desde + ", " + hasta + ", " + agrupadoPor + ")";
        return genericSQLDao.obtenerPorSql(sql, PrdCostoDetalleFechaTO.class);
    }

    @Override
    public List<CostoDetalleMultipleCorridaTO> getCostoDetalleMultipleCorridaTO(String empresa, String hasta,
            String agrupadoPor) throws Exception {
        agrupadoPor = agrupadoPor == null ? agrupadoPor : "'" + agrupadoPor + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        String sql = "SELECT * FROM produccion.fun_costos_productos_proceso('" + empresa + "', " + hasta + ", "
                + agrupadoPor + ")";
        return genericSQLDao.obtenerPorSql(sql, CostoDetalleMultipleCorridaTO.class);
    }

    @Override
    public List<PrdConsumosTO> getConsumoProductosProceso(String empresa, String sector, String fecha)
            throws Exception {
        fecha = fecha == null ? null : fecha;
        String sql = "SELECT * FROM produccion.fun_consumos_productos_proceso('" + empresa + "', '" + sector + "', '"
                + fecha + "')";
        return genericSQLDao.obtenerPorSql(sql, PrdConsumosTO.class);
    }

    @Override
    public List<PrdConsumosTO> getConsumoMultiplePiscina(String empresa) throws Exception {
        String sql = "SELECT * FROM produccion.fun_consumos_multiple_piscinas('" + empresa + "')";
        return genericSQLDao.obtenerPorSql(sql, PrdConsumosTO.class);
    }

    @Override
    public List<PrdCostoTO> getCostoPorFechaProrrateado(String empresa, String codigoSector, String fechaInicio,
            String fechaFin, String agrupadoPor) throws Exception {
        fechaInicio = fechaInicio == null ? null : "'" + fechaInicio + "'";
        fechaFin = fechaFin == null ? null : "'" + fechaFin + "'";
        codigoSector = codigoSector == null ? null : "'" + codigoSector + "'";
        agrupadoPor = agrupadoPor == null ? agrupadoPor : "'" + agrupadoPor + "'";
        String sql = "SELECT * FROM produccion.fun_costos_por_fecha_prorrateado('" + empresa + "'," + "" + codigoSector
                + ", " + fechaInicio + ", " + fechaFin + ", " + agrupadoPor + ")";
        return genericSQLDao.obtenerPorSql(sql, PrdCostoTO.class);
    }

    @Override
    public List<PrdFunCostosPorFechaSimpleTO> getPrdFunCostosPorFechaSimpleTO(String codigoSector, String fechaInicio,
            String fechaFin, String infEmpresea) throws Exception {
        fechaInicio = fechaInicio == null ? null : "'" + fechaInicio + "'";
        fechaFin = fechaFin == null ? null : "'" + fechaFin + "'";
        codigoSector = codigoSector == null ? null : "'" + codigoSector + "'";
        infEmpresea = infEmpresea == null ? "" : "" + infEmpresea + "";
        String sql = "SELECT * FROM produccion.fun_costos_por_fecha_simple('" + infEmpresea + "', " + codigoSector
                + ", " + fechaInicio + ", " + fechaFin + ")";
        return genericSQLDao.obtenerPorSql(sql, PrdFunCostosPorFechaSimpleTO.class);
    }

    @Override
    public List<PrdCostoTO> getCostoPorPorPiscinaSemanal(String empresa, String codigoSector, String numeroPiscina,
            String fechaInicio, String fechaFin, String agrupadoPor, String periodo) throws Exception {
        fechaInicio = fechaInicio == null ? null : "'" + fechaInicio + "'";
        fechaFin = (fechaFin == null || fechaFin.trim().compareTo("") == 0) ? null : "'" + fechaFin + "'";
        codigoSector = codigoSector == null ? null : "'" + codigoSector + "'";
        numeroPiscina = numeroPiscina == null ? null : "'" + numeroPiscina + "'";
        agrupadoPor = agrupadoPor == null ? agrupadoPor : "'" + agrupadoPor + "'";
        periodo = periodo == null ? periodo : "'" + periodo + "'";
        String sql = "SELECT cps.costo_semana as costo_sector_piscina, cps.costo_producto, cps.costo_codigo, cps.costo_medida, cps.costo_total, cps.id FROM produccion.fun_costos_por_piscina_semanal('"
                + empresa + "', " + codigoSector + ", " + numeroPiscina + ", " + fechaInicio + ", " + fechaFin + ", " + periodo + ", "
                + agrupadoPor + ") cps";
        return genericSQLDao.obtenerPorSql(sql, PrdCostoTO.class);
    }

    @Override
    public List<PrdCostoTO> getCostosMensuales(String empresa, String codigoSector, String codigoBodega, String fechaInicio, String fechaFin, String agrupadoPor) throws Exception {
        fechaInicio = fechaInicio == null ? null : "'" + fechaInicio + "'";
        fechaFin = (fechaFin == null || fechaFin.trim().compareTo("") == 0) ? null : "'" + fechaFin + "'";
        codigoSector = codigoSector == null ? null : "'" + codigoSector + "'";
        codigoBodega = codigoBodega == null ? null : "'" + codigoBodega + "'";
        agrupadoPor = agrupadoPor == null ? agrupadoPor : "'" + agrupadoPor + "'";
        String sql = "SELECT cps.costo_mes as costo_sector_piscina, cps.costo_producto, cps.costo_codigo, cps.costo_medida, cps.costo_total, cps.id FROM produccion.fun_costos_mensuales('"
                + empresa + "', " + codigoSector + ", " + codigoBodega + ", " + fechaInicio + ", " + fechaFin + ", " + agrupadoPor + ") cps";
        return genericSQLDao.obtenerPorSql(sql, PrdCostoTO.class);
    }

    @Override
    public List<PrdConsumosTO> getConsumoPorFechaDesglosado(String empresa, String codigoSector, String fechaInicio,
            String fechaFin) throws Exception {
        fechaInicio = fechaInicio == null ? null : "'" + fechaInicio + "'";
        fechaFin = fechaFin == null ? null : "'" + fechaFin + "'";
        codigoSector = codigoSector == null ? null : "'" + codigoSector + "'";
        String sql = "SELECT cfd.consumo_sector, cfd.consumo_producto, cfd.consumo_codigo, cfd.consumo_medida, cfd.consumo_total as consumo_cantidad, cfd.id FROM produccion.fun_consumos_por_fecha_desglosado('"
                + empresa + "', " + codigoSector + ", " + fechaInicio + ", " + fechaFin + ") cfd";
        return genericSQLDao.obtenerPorSql(sql, PrdConsumosTO.class);
    }

    @Override
    public List<PrdConsumosTO> getPrdConsumosFechaTO(String empresa, String sector, String fechaDesde,
            String fechaHasta) throws Exception {
        sector = sector == null ? null : "'" + sector + "'";
        fechaDesde = fechaDesde == null ? null : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? null : "'" + fechaHasta + "'";
        String sql = "SELECT '' as consumo_sector, cpp.consumo_producto, cpp.consumo_codigo, cpp.consumo_medida, cpp.consumo_cantidad, cpp.id FROM produccion.fun_consumos_por_fecha("
                + "'" + empresa + "', " + sector + ", " + fechaDesde + ", " + fechaHasta + ") cpp";
        return genericSQLDao.obtenerPorSql(sql, PrdConsumosTO.class);
    }

    @Override
    public List<PrdConsumosTO> getPrdConsumosPiscinaTO(String empresa, String sector, String piscina, String fechaDesde,
            String fechaHasta) throws Exception {
        sector = sector == null ? null : "'" + sector + "'";
        piscina = piscina == null ? null : "'" + piscina + "'";
        fechaDesde = fechaDesde == null ? null : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? null : "'" + fechaHasta + "'";
        String sql = "SELECT '' as consumo_sector, cpp.consumo_producto, cpp.consumo_codigo, cpp.consumo_medida, cpp.consumo_cantidad, cpp.id FROM produccion.fun_consumos_por_piscina("
                + "'" + empresa + "', " + sector + ", " + piscina + ", " + fechaDesde + ", " + fechaHasta + ") cpp";
        return genericSQLDao.obtenerPorSql(sql, PrdConsumosTO.class);
    }

    @Override
    public List<PrdConsumosDetalladoProductosTO> getPrdConsumosDetalladoProductos(String empresa, String motivo, String sector, String piscina, String busqueda, String fechaDesde, String fechaHasta, String equipo) throws Exception {
        sector = sector == null ? "''" : "'" + sector + "'";
        piscina = piscina == null ? "''" : "'" + piscina + "'";
        fechaDesde = fechaDesde == null ? null : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? null : "'" + fechaHasta + "'";
        motivo = motivo == null ? "''" : "'" + motivo + "'";
        busqueda = busqueda == null ? "''" : "'" + busqueda + "'";
        equipo = equipo == null ? "''" : "'" + equipo + "'";
        String sql = "SELECT * FROM produccion.fun_consumos_detallado_productos("
                + "'" + empresa + "', " + sector + ", " + piscina + ", " + fechaDesde + ", " + fechaHasta + ", " + motivo + ", " + busqueda + ", " + equipo + ") cpp";
        return genericSQLDao.obtenerPorSql(sql, PrdConsumosDetalladoProductosTO.class);
    }

    @Override
    public List<PrdConsumosDetalladoProductosTO> getPrdConsumosDetalladoProductosAgrupadoCC(String empresa, String motivo, String sector, String piscina, String busqueda, String fechaDesde, String fechaHasta, String equipo) throws Exception {
        sector = sector == null ? "''" : "'" + sector + "'";
        piscina = piscina == null ? "''" : "'" + piscina + "'";
        fechaDesde = fechaDesde == null ? null : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? null : "'" + fechaHasta + "'";
        motivo = motivo == null ? "''" : "'" + motivo + "'";
        busqueda = busqueda == null ? "''" : "'" + busqueda + "'";
        equipo = equipo == null ? "''" : "'" + equipo + "'";
        String sql = "SELECT * FROM produccion.fun_consumos_detallado_productos_agrupado_cc("
                + "'" + empresa + "', " + sector + ", " + piscina + ", " + fechaDesde + ", " + fechaHasta + ", " + motivo + ", " + busqueda + ", " + equipo + ") cpp";
        return genericSQLDao.obtenerPorSql(sql, PrdConsumosDetalladoProductosTO.class);
    }

    @Override
    public List<PrdConsumosDetalladoProductosTO> getPrdConsumosDetalladoProductosAgrupadoEquipo(String empresa, String motivo, String sector, String piscina, String busqueda, String fechaDesde, String fechaHasta, String equipo) throws Exception {
        sector = sector == null ? "''" : "'" + sector + "'";
        piscina = piscina == null ? "''" : "'" + piscina + "'";
        fechaDesde = fechaDesde == null ? null : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? null : "'" + fechaHasta + "'";
        motivo = motivo == null ? "''" : "'" + motivo + "'";
        busqueda = busqueda == null ? "''" : "'" + busqueda + "'";
        equipo = equipo == null ? "''" : "'" + equipo + "'";
        String sql = "SELECT * FROM produccion.fun_consumos_detallado_productos_agrupado_equipo("
                + "'" + empresa + "', " + sector + ", " + piscina + ", " + fechaDesde + ", " + fechaHasta + ", " + motivo + ", " + busqueda + ", " + equipo + ") cpp";
        return genericSQLDao.obtenerPorSql(sql, PrdConsumosDetalladoProductosTO.class);
    }

    @Override
    public List<PrdConsumosTO> getConsumoPorPiscinaPeriodo(String empresa, String codigoSector, String numeroPiscina,
            String fechaInicio, String fechaFin, String periodo) throws Exception {
        fechaInicio = fechaInicio == null ? null : "'" + fechaInicio + "'";
        fechaFin = fechaFin == null ? null : "'" + fechaFin + "'";
        codigoSector = codigoSector == null ? null : "'" + codigoSector + "'";
        numeroPiscina = numeroPiscina == null ? null : "'" + numeroPiscina + "'";
        String sql = "SELECT cpp.consumo_fecha as consumo_sector, cpp.consumo_producto, cpp.consumo_codigo, cpp.consumo_medida, cpp.consumo_total as consumo_cantidad, cpp.id FROM produccion.fun_consumos_por_piscina_periodo('"
                + empresa + "', " + codigoSector + ", " + numeroPiscina + ", " + fechaInicio + ", " + fechaFin + ", '"
                + periodo + "') cpp";
        return genericSQLDao.obtenerPorSql(sql, PrdConsumosTO.class);
    }

    @Override
    public List<PrdUtilidadDiariaCorridaTO> getUtilidadDiariaCorrida(String empresa, String codigoSector, String numeroPiscina, String numeroCorrida) throws Exception {
        String sql = "SELECT * FROM produccion.fun_utilidad_diaria('" + empresa + "', '" + codigoSector + "', '" + numeroPiscina + "', '" + numeroCorrida + "')";
        return genericSQLDao.obtenerPorSql(sql, PrdUtilidadDiariaCorridaTO.class);
    }

    @Override
    public List<PrdConsumosTO> getConsumosMensuales(String empresa, String codigoSector, String codigoBodega, String fechaInicio, String fechaFin) throws Exception {
        fechaInicio = fechaInicio == null ? null : "'" + fechaInicio + "'";
        fechaFin = fechaFin == null ? null : "'" + fechaFin + "'";
        codigoSector = codigoSector == null ? null : "'" + codigoSector + "'";
        codigoBodega = codigoBodega == null ? null : "'" + codigoBodega + "'";
        String sql = "SELECT cpp.consumo_fecha as consumo_sector, cpp.consumo_producto, cpp.consumo_codigo, cpp.consumo_medida, cpp.consumo_total as consumo_cantidad, cpp.id "
                + "FROM produccion.fun_consumos_mensuales('"
                + empresa + "', " + codigoSector + ", " + codigoBodega + ", " + fechaInicio + ", " + fechaFin + ") cpp";
        return genericSQLDao.obtenerPorSql(sql, PrdConsumosTO.class);
    }

    @Override
    public List<PrdConsumosDiariosTO> getConsumosDiarios(String empresa, String codigoSector, String codigoBodega, String fechaInicio, String fechaFin, String piscina, String codigoProducto) throws Exception {
        fechaInicio = fechaInicio == null ? null : "'" + fechaInicio + "'";
        fechaFin = fechaFin == null ? null : "'" + fechaFin + "'";
        codigoSector = codigoSector == null ? null : "'" + codigoSector + "'";
        codigoBodega = codigoBodega == null ? null : "'" + codigoBodega + "'";
        piscina = piscina == null ? null : "'" + piscina + "'";
        codigoProducto = codigoProducto == null ? null : "'" + codigoProducto + "'";
        String sql = "SELECT cpp.consumo_fecha as consumo_sector, cpp.consumo_piscina, cpp.consumo_producto, cpp.consumo_codigo, cpp.consumo_medida, cpp.consumo_total as consumo_cantidad, cpp.id "
                + "FROM produccion.fun_consumos_diarios('"
                + empresa + "', " + codigoSector + ", " + codigoBodega + ", " + fechaInicio + ", " + fechaFin + ", " + piscina + ", " + codigoProducto + ") cpp";
        return genericSQLDao.obtenerPorSql(sql, PrdConsumosDiariosTO.class);
    }

    @Override
    public List<PrdCostosDiariosTO> getCostosDiarios(String empresa, String codigoSector, String codigoBodega, String fechaInicio, String fechaFin, String piscina, String codigoProducto) throws Exception {
        fechaInicio = fechaInicio == null ? null : "'" + fechaInicio + "'";
        fechaFin = fechaFin == null ? null : "'" + fechaFin + "'";
        codigoSector = codigoSector == null ? null : "'" + codigoSector + "'";
        codigoBodega = codigoBodega == null ? null : "'" + codigoBodega + "'";
        piscina = piscina == null ? null : "'" + piscina + "'";
        codigoProducto = codigoProducto == null ? null : "'" + codigoProducto + "'";
        String sql = "SELECT cpp.costo_fecha as costo_sector, cpp.costo_piscina, cpp.costo_producto, cpp.costo_codigo, cpp.costo_medida, cpp.costo_total as costo_cantidad, cpp.id "
                + "FROM produccion.fun_costos_diarios('"
                + empresa + "', " + codigoSector + ", " + codigoBodega + ", " + fechaInicio + ", " + fechaFin + ", " + piscina + ", " + codigoProducto + ") cpp";
        return genericSQLDao.obtenerPorSql(sql, PrdCostosDiariosTO.class);
    }

    @Override
    public List<PrdResumenFinancieroTO> getPrdResumenFinancieroTO(String empresa, String secCodigo, String desde,
            String hasta) throws Exception {
        String sql = "SELECT * FROM produccion.fun_resumen_financiero(" + "'" + empresa + "', '" + secCodigo + "', "
                + desde + ", " + hasta + ")";
        return genericSQLDao.obtenerPorSql(sql, PrdResumenFinancieroTO.class);
    }

    @Override
    public Boolean comprobarPrdBalanceado(String empresa, String codigoPrincipal) throws Exception {
        String sql = "SELECT COUNT(*) FROM inventario.inv_producto_medida " + "WHERE med_empresa = ('" + empresa
                + "') AND med_codigo = ('" + codigoPrincipal + "')";
        return ((Integer) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql)) != 0);
    }

    @Override
    public List<PrdListaFunAnalisisPesosTO> getFunAnalisisPesos(String empresa, String sector, String fechaHasta)
            throws Exception {
        fechaHasta = fechaHasta == null ? null : fechaHasta;
        sector = sector == null ? null : "'" + sector + "'";

        String sql = "SELECT * " + "FROM produccion.fun_analisis_pesos('" + empresa + "', " + sector + ", "
                + fechaHasta + " )";
        return genericSQLDao.obtenerPorSql(sql, PrdListaFunAnalisisPesosTO.class);
    }

    @Override
    public List<PrdListaFunAnalisisPesosComplementoTO> getFunAnalisisPesosComplemento(String empresa, String sector,
            String fechaHasta) throws Exception {
        fechaHasta = fechaHasta == null ? null : fechaHasta;
        sector = sector == null ? null : "'" + sector + "'";
        String sql = "SELECT * " + "FROM produccion.fun_analisis_pesos_complemento('" + empresa + "', " + sector
                + ", " + fechaHasta + " )";
        return genericSQLDao.obtenerPorSql(sql, PrdListaFunAnalisisPesosComplementoTO.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getFunFechaSemanas(String empresa, String sector, String fechaHasta) throws Exception {
        String sql = "SELECT ap_fecha " + "FROM produccion.fun_analisis_pesos_complemento('" + empresa + "', '" + sector
                + "', " + fechaHasta + " ) WHERE ap_fecha!='Inc. Promedio' GROUP BY ap_fecha ORDER BY  ap_fecha DESC";
        return (List<String>) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql));
    }

    @Override
    public boolean eliminarPrdCorrida(String empresa, String sector, String piscina, String numero) throws Exception {
        String sql = "SELECT * FROM produccion.fun_eliminar_produccion('C', '" + empresa + "', '" + sector + "', '"
                + piscina + "', '" + numero + "')";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String cambiarCodigoPrdCorrida(PrdCorridaPK prdCorridaPK, String nuevoCodigoCorrida) throws Exception {
        String sql = "UPDATE produccion.prd_corrida SET cor_numero = '"
                + nuevoCodigoCorrida + "' WHERE cor_empresa = '"
                + prdCorridaPK.getCorEmpresa() + "' AND cor_sector = '"
                + prdCorridaPK.getCorSector() + "' AND cor_piscina = '"
                + prdCorridaPK.getCorPiscina() + "' AND cor_numero = '"
                + prdCorridaPK.getCorNumero() + "'";
        genericSQLDao.ejecutarSQL(sql);
        return "T";
    }

    @Override
    public String validarFechasComprasyConsumosHuerfanos(String empresa, String sector, String piscina, String numero,
            Date desde, Date hasta, String accion) throws Exception {
        String sql = "SELECT * FROM produccion.fun_validar_corrida_compras_consumos_huerfanos('" + empresa + "', '"
                + sector + "', '" + piscina + "', '" + numero + "', '"
                + UtilsDate.fechaFormatoString(desde, "yyyy-MM-dd") + "', "
                + (hasta == null ? null : "'" + UtilsDate.fechaFormatoString(hasta, "yyyy-MM-dd") + "'") + ", '"
                + accion + "')";
        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public boolean insertarPrdCorrida(PrdCorrida prdCorrida, SisSuceso sisSuceso) throws Exception {
        insertar(prdCorrida);
        sisSucesoDao.insertar(sisSuceso);
        //suceso corrida
        List<PrdCorridaDetalle> listadoCopia = prdCorrida.getPrdCorridaDetalleList().stream().collect(Collectors.toList());
        for (int i = 0; i < listadoCopia.size(); i++) {
            if (listadoCopia.get(i).getPrdCorridaDestino() != null) {
                listadoCopia.get(i).getPrdCorridaDestino().setPrdCorridaDetalleList(null);
            }
            if (listadoCopia.get(i).getPrdCorridaOrigen() != null) {
                listadoCopia.get(i).getPrdCorridaOrigen().setPrdCorridaDetalleList(null);
            }
        }
        SisSucesoCorrida sucesoCorrida = new SisSucesoCorrida();
        PrdCorrida copia = ConversionesProduccion.convertirPrdCorrida_PrdCorrida(prdCorrida);
        copia.setPrdCorridaDetalleList(listadoCopia);
        String json = UtilsJSON.objetoToJson(copia);
        if (prdCorrida.getPrdCorridaDetalleList() != null && prdCorrida.getPrdCorridaDetalleList().size() > 0) {
            String jsonListado = UtilsJSON.objetoToJson(prdCorrida.getPrdCorridaDetalleList());
            if (json != null && !json.equals("") && jsonListado != null && !jsonListado.equals("")) {
                json = json.substring(0, json.length() - 1) + "," + "\"prdCorridaDetalleList\"" + ":" + jsonListado + "}";
            }
        }
        sucesoCorrida.setSusJson(json);
        sucesoCorrida.setSisSuceso(sisSuceso);
        sucesoCorrida.setPrdCorrida(copia);
        sucesoCorridaDao.insertar(sucesoCorrida);
        return true;
    }

    @Override
    public boolean modificarPrdCorrida(PrdCorrida prdCorrida, SisSuceso sisSuceso) throws Exception {
        genericSQLDao.ejecutarSQL("DELETE FROM produccion.prd_corrida_detalle WHERE det_corrida_origen_empresa = '"
                + prdCorrida.getPrdCorridaPK().getCorEmpresa() + "' AND det_corrida_origen_sector = '"
                + prdCorrida.getPrdCorridaPK().getCorSector() + "' AND det_corrida_origen_piscina = '"
                + prdCorrida.getPrdCorridaPK().getCorPiscina() + "' AND det_corrida_origen_numero='"
                + prdCorrida.getPrdCorridaPK().getCorNumero() + "'");
        corridaDetalleDao.insertar(prdCorrida.getPrdCorridaDetalleList());
        actualizar(prdCorrida);
        sisSucesoDao.insertar(sisSuceso);
        //suceso
        SisSucesoCorrida sucesoCorrida = new SisSucesoCorrida();
        PrdCorrida copia = ConversionesProduccion.convertirPrdCorrida_PrdCorrida(prdCorrida);
        String json = UtilsJSON.objetoToJson(copia);
        if (prdCorrida.getPrdCorridaDetalleList() != null && prdCorrida.getPrdCorridaDetalleList().size() > 0) {
            String jsonListado = UtilsJSON.objetoToJson(prdCorrida.getPrdCorridaDetalleList());
            if (json != null && !json.equals("") && jsonListado != null && !jsonListado.equals("")) {
                json = json.substring(0, json.length() - 1) + "," + "\"prdCorridaDetalleList\"" + ":" + jsonListado + "}";
            }
        }
        sucesoCorrida.setSusJson(json);
        sucesoCorrida.setSisSuceso(sisSuceso);
        sucesoCorrida.setPrdCorrida(copia);
        sucesoCorridaDao.insertar(sucesoCorrida);
        return true;
    }

    @Override
    public boolean eliminarPrdCorrida(PrdCorrida prdCorrida, SisSuceso sisSuceso) throws Exception {
        //eliminar(prdCorrida);
        genericSQLDao.ejecutarSQL("DELETE FROM produccion.prd_corrida WHERE cor_empresa = '"
                + prdCorrida.getPrdCorridaPK().getCorEmpresa() + "' AND cor_sector = '"
                + prdCorrida.getPrdCorridaPK().getCorSector() + "' AND cor_piscina = '"
                + prdCorrida.getPrdCorridaPK().getCorPiscina() + "' AND cor_numero='"
                + prdCorrida.getPrdCorridaPK().getCorNumero() + "'");
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public PrdCorrida obtenerCorrida(PrdCorridaPK prdCorridaPK) throws Exception {
        String sql = "SELECT * FROM produccion.prd_corrida WHERE cor_empresa = '" + prdCorridaPK.getCorEmpresa()
                + "' AND cor_sector = '" + prdCorridaPK.getCorSector() + "' AND cor_piscina = '"
                + prdCorridaPK.getCorPiscina() + "' AND cor_numero='" + prdCorridaPK.getCorNumero() + "'";
        return genericSQLDao.obtenerPorSql(sql, PrdCorrida.class).get(0);
    }

    @Override
    public List<PrdCorrida> getListaCorridasPorEmpresaSectorPiscina(String empresa, String sector, String piscina) {
        String sql = "";
        if (piscina != null && !piscina.equals("")) {
            sql = "SELECT * FROM produccion.prd_corrida WHERE (cor_empresa = '" + empresa + "') AND (cor_sector = '"
                    + sector + "') AND (cor_piscina = '" + piscina + "') ORDER BY cor_fecha_desde DESC";
        } else {
            sql = "SELECT * FROM produccion.prd_corrida WHERE (cor_empresa = '" + empresa + "') AND (cor_sector = '"
                    + sector + "') ORDER BY cor_fecha_desde DESC";
        }
        return genericSQLDao.obtenerPorSql(sql, PrdCorrida.class);

    }

    @Override
    public List<PrdCorrida> getListaCorridasPorEmpresa(String empresa) {
        String sql = "SELECT * FROM produccion.prd_corrida WHERE (cor_empresa = '" + empresa + "') ORDER BY cor_fecha_desde DESC";
        return genericSQLDao.obtenerPorSql(sql, PrdCorrida.class);
    }

    @Override
    public List<PrdCorrida> getCorridaPorNumero(String empresa, String sector, String piscina, String numero) {
        String sql = "SELECT * FROM produccion.prd_corrida WHERE (cor_empresa = '" + empresa + "') AND (cor_sector = '"
                + sector + "') AND (cor_piscina = '" + piscina + "') AND (cor_numero='" + numero + "')";
        return genericSQLDao.obtenerPorSql(sql, PrdCorrida.class);
    }

    @Override
    public List<PrdResumenTrazabilidadAlimentacionTO> getPrdResumenTrazabilidadAlimentacionTO(String empresa, String sector, String tipo, String fechaInicio, String fechaFin) {
        sector = sector != null && !sector.equals("") ? "'" + sector + "'" : null;
        String sql = "SELECT * FROM produccion.fun_resumen_trazabilidad_alimentacion('" + empresa + "'," + sector + ",'" + tipo + "','" + fechaInicio + "','" + fechaFin + "')";
        return genericSQLDao.obtenerPorSql(sql, PrdResumenTrazabilidadAlimentacionTO.class);
    }

    @Override
    public List<PrdResumenCorridaSubcategoriaTO> getPrdResumenCorridaSubcategoriaTO(String empresa, String sector, String tipo, String fechaInicio, String fechaFin) {
        sector = sector != null && !sector.equals("") ? "'" + sector + "'" : null;
        String sql = "SELECT * FROM produccion.fun_resumen_corrida_subcategoria('" + empresa + "'," + sector + ",'" + tipo + "','" + fechaInicio + "','" + fechaFin + "')";
        return genericSQLDao.obtenerPorSql(sql, PrdResumenCorridaSubcategoriaTO.class);
    }

    @Override
    public List<PrdTrazabilidadCorridaTO> getPrdTrazabilidadCorridaTO(String empresa, String sector, String piscina, String corrida, BigDecimal costo, String tipo) throws Exception {
        String sql = "";
        if (tipo.equals("pesca")) {
            sql = "SELECT * FROM produccion.fun_trazabilidad_corrida('" + empresa + "','" + sector + "','" + piscina + "','" + corrida + "'," + costo + ")";
        }
        if (tipo.equals("siembra")) {
            sql = "SELECT * FROM produccion.fun_trazabilidad_corrida_siembra('" + empresa + "','" + sector + "','" + piscina + "','" + corrida + "'," + costo + ")";
        }
        return genericSQLDao.obtenerPorSql(sql, PrdTrazabilidadCorridaTO.class);
    }

    @Override
    public List<PrdContabilizarCorridaCostoVentaTO> getResumenCorridaCostoVenta(String empresa, String fechaInicio, String fechaFin) throws Exception {
        String sql = "SELECT * FROM produccion.fun_contabilizar_corrida_costo_venta('" + empresa + "', " + fechaInicio + ", " + fechaFin + ")";
        return genericSQLDao.obtenerPorSql(sql, PrdContabilizarCorridaCostoVentaTO.class);
    }

    @Override
    public List<String> obtenerLaboratiroDeCorrida(String empresa) throws Exception {
        String sql = "SELECT DISTINCT cor_laboratorio FROM produccion.prd_corrida WHERE cor_empresa='" + empresa + "' AND cor_laboratorio IS NOT NULL";
        return genericSQLDao.obtenerPorSql(sql);
    }

    @Override
    public List<String> obtenerNauplioDeCorrida(String empresa) throws Exception {
        String sql = "SELECT DISTINCT cor_nauplio FROM produccion.prd_corrida WHERE cor_empresa='" + empresa + "' AND cor_nauplio IS NOT NULL";
        return genericSQLDao.obtenerPorSql(sql);
    }
}
