package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesAnexos;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxEstablecimientoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFunListadoDevolucionIvaVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaConsolidadoRetencionesVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaEstablecimientoRetencionesVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentaElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListadoRetencionesVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionLineaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxPuntoEmisionComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTalonResumenVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVenta;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaElectronica;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaPK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class VentaDaoImpl extends GenericDaoImpl<AnxVenta, AnxVentaPK> implements VentaDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Autowired
    private SucesoDao sisSucesoDao;

    @Override
    public AnxVentaTO getAnexoVentaTO(String empresa, String periodo, String motivo, String numeroVenta)
            throws Exception {
        return ConversionesAnexos.convertirAnxVenta_AnxVentaTO(
                obtenerPorId(AnxVenta.class, new AnxVentaPK(empresa, periodo, motivo, numeroVenta)));
    }

    @Override
    public AnxVentaTO getAnexoVentaTO(String empresa, String claveAcceso) throws Exception {
        String sql = "SELECT * FROM anexo.anx_venta where ven_empresa = '" + empresa + "' and ven_retencionautorizacion = '" + claveAcceso + "'";
        List<AnxVenta> listado = genericSQLDao.obtenerPorSql(sql, AnxVenta.class);
        return listado != null && !listado.isEmpty() ? ConversionesAnexos.convertirAnxVenta_AnxVentaTO(listado.get(0)) : null;
    }

    @Override
    public AnxVentaElectronica getAnexoVentaElectronicaTO(String empresa, String periodo, String motivo, String numeroVenta)
            throws Exception {
        String sql = "SELECT * FROM anexo.anx_venta_electronica where vta_empresa = '" + empresa + "' and vta_periodo = '" + periodo + "' and vta_motivo = '" + motivo + "' and vta_numero = '" + numeroVenta + "'";
        List<AnxVentaElectronica> listado = genericSQLDao.obtenerPorSql(sql, AnxVentaElectronica.class);
        return listado != null && !listado.isEmpty() ? listado.get(0) : null;
    }

    @Override
    public Boolean eliminarAnxVentas(AnxVenta anxVenta, SisSuceso sisSuceso) throws Exception {
        eliminar(anxVenta);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public Boolean accionAnxVenta(AnxVenta anxVenta, SisSuceso sisSuceso, char accion) throws Exception {
        if (accion == 'I') {
            insertar(anxVenta);
        }
        if (accion == 'M') {
            actualizar(anxVenta);
        }
        if (accion == 'E') {
            eliminar(anxVenta);
        }
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public String getConteoNumeroRetencionVenta(String empresaCodigo, String numeroRetencion, String cliente)
            throws Exception {
        String retorno = "";
        String empresa = "";
        String periodo = "";
        String motivo = "";
        String numero = "";
        String sql = "SELECT ven_empresa, ven_periodo, ven_motivo, ven_numero "
                + "FROM anexo.anx_venta INNER JOIN inventario.inv_ventas ON "
                + "anx_venta.ven_empresa = inv_ventas.vta_empresa AND anx_venta.ven_periodo = "
                + "inv_ventas.vta_periodo AND anx_venta.ven_motivo = inv_ventas.vta_motivo AND "
                + "anx_venta.ven_numero = inv_ventas.vta_numero WHERE ven_empresa = ('" + empresaCodigo + "') "
                + "AND cli_codigo = ('" + cliente + "') AND ven_retencionnumero = ('" + numeroRetencion
                + "') AND NOT vta_anulado";
        List<Object> lista = genericSQLDao.obtenerPorSql(sql);
        if (!lista.isEmpty()) {
            Object[] array = UtilsConversiones.convertirListToArray(lista, 0);
            empresa = array[0].toString().trim();
            periodo = array[1].toString().trim();
            motivo = array[2].toString().trim();
            numero = array[3].toString().trim();
            retorno = empresa + periodo + motivo + numero;
        } else {
            retorno = "";
        }
        return retorno;
    }

    @Override
    public boolean comprobarEliminarAnxVentas(String empresa, String ventPeriodo, String ventMotivo, String ventaNumero)
            throws Exception {
        String sql = " SELECT COUNT(*)!=0 FROM anexo.anx_venta " + "WHERE (ven_empresa = '" + empresa + "' "
                + "AND ven_periodo = '" + ventPeriodo + "' " + "AND ven_motivo = '" + ventMotivo + "' "
                + "AND ven_numero ='" + ventaNumero + "');";

        return (boolean) genericSQLDao.obtenerObjetoPorSql(sql);
    }

    @Override
    public boolean comprobarAnxVentas(String empresa, String ventPeriodo, String ventMotivo, String ventaNumero)
            throws Exception {
        String sql = " SELECT COUNT(*)!=0 FROM anexo.anx_venta " + "WHERE (ven_empresa = '" + empresa + "' "
                + "AND ven_periodo = '" + ventPeriodo + "' " + "AND ven_motivo = '" + ventMotivo + "' "
                + "AND ven_numero ='" + ventaNumero + "');";

        return (boolean) genericSQLDao.obtenerObjetoPorSql(sql);
    }

    @Override
    public List<AnxListaRetencionVentaTO> getAnxListaRetencionVentaTO(String empresa, String fechaDesde,
            String fechaHasta) throws Exception {
        String sql = "SELECT anx_tipotransaccion.tt_codigo, "
                + "inv_cliente.cli_id_numero, inv_ventas.vta_documento_tipo, "
                + "COUNT(*) n_retenciones, 0.00 ven_basenoobjetoiva, "
                + "ROUND(SUM(inv_ventas.vta_subtotal_base0), 2) ven_base0, "
                + "ROUND(SUM(inv_ventas.vta_subtotal_baseimponible), 2) ven_baseimponible, "
                + "SUM(inv_ventas.vta_montoiva) ven_montoiva, " + "SUM(ven_valorretenidoiva) ven_valorretenidoiva, "
                + "SUM(ven_valorretenidorenta) ven_valorretenidorenta "
                + "FROM inventario.inv_cliente INNER JOIN inventario.inv_ventas " + "LEFT JOIN anexo.anx_venta "
                + "ON inv_ventas.vta_empresa = anx_venta.ven_empresa "
                + "AND inv_ventas.vta_periodo = anx_venta.ven_periodo "
                + "AND inv_ventas.vta_motivo = anx_venta.ven_motivo "
                + "AND inv_ventas.vta_numero = anx_venta.ven_numero "
                + "ON inv_cliente.cli_empresa = inv_ventas.cli_empresa "
                + "AND inv_cliente.cli_codigo = inv_ventas.cli_codigo " + "INNER JOIN anexo.anx_tipotransaccion "
                + "ON inv_cliente.cli_tipo_id = anx_tipotransaccion.tt_id "
                + "AND anx_tipotransaccion.tt_codigo IN ('04','05','06','07') " + "WHERE vta_empresa='" + empresa + "' "
                + "AND vta_fecha>='" + fechaDesde + "' " + "AND vta_fecha<='" + fechaHasta + "' "
                + "AND NOT vta_anulado AND vta_documento_tipo != '00'"
                + "GROUP BY anx_tipotransaccion.tt_codigo, inv_cliente.cli_id_numero, "
                + "inv_ventas.vta_documento_tipo;";
        return genericSQLDao.obtenerPorSql(sql, AnxListaRetencionVentaTO.class);
    }

    @Override
    public List<AnxListaVentaElectronicaTO> getListaAnxVentaElectronicaTO(String empresa,
            String fechaDesde, String fechaHasta, String tipoDocumento) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        tipoDocumento = tipoDocumento == null || tipoDocumento.equals("TODOS") ? "" : " where vta_documento_tipo = '" + tipoDocumento + "' ";

        String sql = "SELECT * FROM anexo.fun_ventas_electronicas_autorizadas_listado(" + empresa + ", " + fechaDesde + ", " + fechaHasta + ")";
        sql = sql + tipoDocumento;
        return genericSQLDao.obtenerPorSql(sql, AnxListaVentaElectronicaTO.class);
    }

    @Override
    public List<AnxEstablecimientoComboTO> getEstablecimientos(String empresa) throws Exception {
        String sql = "SELECT * FROM anexo.fun_establecimientos('" + empresa + "')";
        return genericSQLDao.obtenerPorSql(sql, AnxEstablecimientoComboTO.class);
    }

    @Override
    public List<AnxPuntoEmisionComboTO> getPuntosEmision(String empresa, String establecimiento) throws Exception {
        String sql = "SELECT * FROM anexo.fun_puntos_de_emision('" + empresa + "','" + establecimiento + "')";
        return genericSQLDao.obtenerPorSql(sql, AnxPuntoEmisionComboTO.class);
    }

    @Override
    public AnxNumeracionLineaTO getNumeroAutorizacion(String empresa, String numeroRetencion, String numeroComprobante,
            String fechaVencimiento) throws Exception {
        fechaVencimiento = fechaVencimiento.contains("'") ? fechaVencimiento : "'" + fechaVencimiento + "'";
        String sql = "SELECT * FROM anexo.fun_comprobante_verificar_autorizacion('" + empresa + "', '"
                + numeroComprobante + "', '" + numeroRetencion + "', " + fechaVencimiento + ")";
        return genericSQLDao.obtenerObjetoPorSql(sql, AnxNumeracionLineaTO.class);
    }

    @Override
    public AnxNumeracion obtenerNumeracionPorTipoYNumeroDocumento(String empresa, String tipoDocumento, String numeroComprobante) throws Exception {
        String sql = "SELECT * FROM anexo.anx_numeracion where num_empresa = '" + empresa + "' and num_comprobante = '" + tipoDocumento + "' "
                + "and '" + numeroComprobante + "' >= anx_numeracion.num_desde AND "
                + "'" + numeroComprobante + "' <= anx_numeracion.num_hasta";
        return genericSQLDao.obtenerObjetoPorSql(sql, AnxNumeracion.class);
    }

    @Override
    public AnxNumeracion obtenerNumeracionPorTipoNumeroDocumentoSector(String empresa, String tipoDocumento, String sector, String numeroComprobante) throws Exception {
        String sql = "SELECT * FROM anexo.anx_numeracion where num_empresa = '" + empresa + "' and num_comprobante = '" + tipoDocumento + "' "
                + "and '" + numeroComprobante + "' >= anx_numeracion.num_desde AND "
                + "'" + numeroComprobante + "' <= anx_numeracion.num_hasta AND sec_codigo = '"
                + sector + "'";
        return genericSQLDao.obtenerObjetoPorSql(sql, AnxNumeracion.class);
    }

    @Override
    public String getUltimaNumeracionComprobante(String empresa, String comprobante, String secuencial)
            throws Exception {
        String sql = "SELECT cast(fun_comprobante_obtener_ultima_numeracion as text) FROM anexo.fun_comprobante_obtener_ultima_numeracion('"
                + empresa + "', '" + comprobante + "', '" + secuencial + "');";
        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public List<AnxListaConsolidadoRetencionesVentasTO> getAnxListaConsolidadoRetencionesVentasTO(String empresa,
            String fechaDesde, String fechaHasta) throws Exception {
        String sql = "SELECT * FROM anexo.fun_consolidado_retenciones_ventas(" + "'" + empresa + "', " + "'"
                + fechaDesde + "', " + "'" + fechaHasta + "');";
        return genericSQLDao.obtenerPorSql(sql, AnxListaConsolidadoRetencionesVentasTO.class);
    }

    @Override
    public List<AnxListaEstablecimientoRetencionesVentasTO> getAnxListaEstablecimientoRetencionesVentasTO(
            String empresa, String fechaDesde, String fechaHasta) throws Exception {
        String sql = "SELECT * FROM " + "anexo.fun_consolidado_por_establecimiento_retenciones_ventas(" + "'" + empresa
                + "', " + "'" + fechaDesde + "', " + "'" + fechaHasta + "');";
        return genericSQLDao.obtenerPorSql(sql, AnxListaEstablecimientoRetencionesVentasTO.class);

    }

    @Override
    public List<AnxListadoRetencionesVentasTO> getAnxListadoRetencionesVentasTO(String empresa, String tipoDocumento,
            String establecimiento, String puntoEmision, String fechaDesde, String fechaHasta) throws Exception {
        tipoDocumento = (tipoDocumento == null ? null : "'" + tipoDocumento + "'");
        establecimiento = (establecimiento == null ? null : "'" + establecimiento + "'");
        puntoEmision = (puntoEmision == null ? null : "'" + puntoEmision + "'");
        String sql = "SELECT * FROM " + "anexo.fun_listado_retenciones_ventas(" + "'" + empresa + "', " + tipoDocumento
                + ", " + establecimiento + ", " + puntoEmision + ", " + "'" + fechaDesde + "', " + "'" + fechaHasta
                + "');";

        return genericSQLDao.obtenerPorSql(sql, AnxListadoRetencionesVentasTO.class);
    }

    @Override
    public List<AnxFunListadoDevolucionIvaVentasTO> getAnxFunListadoDevolucionIvaVentasTO(String empCodigo, String desde,
            String hasta) throws Exception {

        String sql = "SELECT * FROM " + "anexo.fun_listado_devolucion_iva_ventas(" + "'" + empCodigo + "', '"
                + desde + "', " + "'" + hasta + "');";
        return genericSQLDao.obtenerPorSql(sql, AnxFunListadoDevolucionIvaVentasTO.class);
    }

    @Override
    public List<AnxTalonResumenVentaTO> getAnexoTalonResumenVentaTO(String empresa, String fechaDesde,
            String fechaHasta) throws Exception {
        String sql = "SELECT * FROM anexo.fun_talon_resumen_ventas" + "('" + empresa + "',null, null, null, '"
                + fechaDesde + "', '" + fechaHasta + "')";
        return genericSQLDao.obtenerPorSql(sql, AnxTalonResumenVentaTO.class);
    }

    @Override
    public boolean anularConContableVentas(InvVentasPK pk, SisSuceso sisSuceso) throws Exception {
        String sql = "UPDATE inventario.inv_ventas SET con_empresa = null, con_periodo = null, con_tipo = null, con_numero = null WHERE vta_empresa= '" + pk.getVtaEmpresa() + "' and "
                + "vta_periodo= '" + pk.getVtaPeriodo() + "' and vta_numero= '" + pk.getVtaNumero() + "' and vta_motivo = '" + pk.getVtaMotivo() + "';";
        genericSQLDao.ejecutarSQL(sql);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

}
