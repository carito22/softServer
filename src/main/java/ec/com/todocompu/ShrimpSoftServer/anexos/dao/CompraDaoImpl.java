package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesAnexos;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFormulario103TO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFormulario104TO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFunListadoDevolucionIvaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesFuenteIvaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesRentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaRetencionesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListadoCompraElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxRetencionCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxRetencionVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTalonResumenTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxUltimaAutorizacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompra;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraDetalle;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class CompraDaoImpl extends GenericDaoImpl<AnxCompra, AnxCompraPK> implements CompraDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Autowired
    private SucesoDao sisSucesoDao;

    @Override
    public AnxCompraTO getAnexoCompraTO(String empresa, String periodo, String motivo, String numeroCompra)
            throws Exception {
        AnxCompra anexo = obtenerPorId(AnxCompra.class, new AnxCompraPK(empresa, periodo, motivo, numeroCompra));
        AnxCompraTO anxCompraTO = null;
        if (anexo != null) {
            anxCompraTO = ConversionesAnexos.convertirAnxCompra_AnxCompraTO(anexo);
        }
        return anxCompraTO;
    }

    @Override
    public Boolean reestructurarRetencion(AnxCompra anxCompra, SisSuceso sisSuceso) throws Exception {
        actualizar(anxCompra);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public Boolean actualizarClaveExternaRetencion(AnxCompraPK pk, String clave, SisSuceso sisSuceso) throws Exception {
        // anxCompra.setCompClaveAccesoExterna(clave);
        // actualizar(anxCompra);
        String sql = "UPDATE anexo.anx_compra SET comp_clave_acceso_externa=" + clave
                + " WHERE comp_empresa='"
                + pk.getCompEmpresa()
                + "' and  comp_periodo='" + pk.getCompPeriodo()
                + "' and comp_motivo='" + pk.getCompMotivo()
                + "' and comp_numero='" + pk.getCompNumero()
                + "';";
        genericSQLDao.ejecutarSQL(sql);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public Boolean existeDetalleRetención(AnxCompraPK pk) throws Exception {
        String sql = "SELECT * from anexo.anx_compra_detalle "
                + " WHERE comp_empresa='"
                + pk.getCompEmpresa()
                + "' and  comp_periodo='" + pk.getCompPeriodo()
                + "' and comp_motivo='" + pk.getCompMotivo()
                + "' and comp_numero='" + pk.getCompNumero()
                + "';";
        List<AnxCompraDetalle> detalles = genericSQLDao.obtenerPorSql(sql, AnxCompraDetalle.class);
        if (detalles != null && !detalles.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public String getConteoNumeroRetencion(String empresaCodigo, String numeroRetencion) throws Exception {
        String sql = "SELECT " + "(anexo.anx_compra.comp_empresa ||" + "anexo.anx_compra.comp_periodo || "
                + "anexo.anx_compra.comp_motivo ||" + "anexo.anx_compra.comp_numero) as numero_retencion "
                + "FROM anexo.anx_compra INNER JOIN inventario.inv_compras ON "
                + "anx_compra.comp_empresa = inv_compras.comp_empresa AND anx_compra.comp_periodo = "
                + "inv_compras.comp_periodo AND anx_compra.comp_motivo = inv_compras.comp_motivo AND "
                + "anx_compra.comp_numero = inv_compras.comp_numero WHERE anexo.anx_compra.comp_empresa = ('"
                + empresaCodigo + "') " + "AND comp_retencion_numero = ('" + numeroRetencion
                + "') AND NOT comp_anulado;";
        Object obj = genericSQLDao.obtenerObjetoPorSql(sql);
        if (obj != null) {
            return (String) UtilsConversiones.convertir(obj);
        } else {
            return null;
        }
    }

    @Override
    public String getCompAutorizacion(String empCodigo, String provCodigo, String codTipoComprobante,
            String numComplemento) throws Exception {
        String sql = "SELECT anx_compra.comp_autorizacion FROM inventario.inv_compras INNER JOIN anexo.anx_compra "
                + "ON inv_compras.comp_empresa = anx_compra.comp_empresa AND "
                + "inv_compras.comp_periodo = anx_compra.comp_periodo AND "
                + "inv_compras.comp_motivo = anx_compra.comp_motivo AND "
                + "inv_compras.comp_numero = anx_compra.comp_numero " + "WHERE prov_empresa = '" + empCodigo + "' AND "
                + "prov_codigo = '" + provCodigo + "' AND " + "comp_documento_tipo = '" + codTipoComprobante + "' AND "
                + "comp_documento_numero = '" + numComplemento + "' AND " + "NOT (comp_pendiente OR comp_anulado);";
        Object obj = genericSQLDao.obtenerObjetoPorSql(sql);
        return obj == null ? "" : (String) UtilsConversiones.convertir(obj);

    }

    @Override
    public AnxCompraTO getAnexoCompraTO(String empresa, String numeroDocumento) throws Exception {
        String sql = "SELECT * FROM  anexo.anx_compra "
                + "WHERE comp_empresa = '" + empresa + "' AND "
                + "comp_retencion_numero = '" + numeroDocumento + "' ;";
        AnxCompra anexo = genericSQLDao.obtenerObjetoPorSql(sql, AnxCompra.class);
        return ConversionesAnexos.convertirAnxCompra_AnxCompraTO(anexo);
    }

    @Override
    public List<AnxListadoCompraElectronicaTO> getListaAnxComprasElectronicaTO(String empresa,
            String fechaDesde, String fechaHasta) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";

        String sql = "SELECT * FROM anexo.fun_compras_electronicas_autorizadas_listado( " + empresa + ", " + fechaDesde + ", " + fechaHasta + ")";
        return genericSQLDao.obtenerPorSql(sql, AnxListadoCompraElectronicaTO.class);
    }

    @Override
    public AnxUltimaAutorizacionTO getAnxUltimaAutorizacionTO(String empresa, String proveedor, String tipoDocumento,
            String secuencial, String fechaFactura) throws Exception {
        AnxUltimaAutorizacionTO anxUltimaAutorizacionTO;
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        proveedor = proveedor == null ? proveedor : "'" + proveedor + "'";
        tipoDocumento = tipoDocumento == null ? tipoDocumento : "'" + tipoDocumento + "'";
        secuencial = secuencial == null ? secuencial : "'" + secuencial + "'";
        fechaFactura = fechaFactura == null ? fechaFactura : "'" + fechaFactura + "'";
        String sql = "SELECT comp_autorizacion, comp_fecha_emision, comp_fecha_caduca "
                + "FROM ( SELECT comp_fecha, comp_autorizacion, comp_fecha_emision, comp_fecha_caduca FROM inventario.inv_compras "
                + "INNER JOIN anexo.anx_compra ON inv_compras.comp_empresa = anx_compra.comp_empresa AND inv_compras.comp_periodo = anx_compra.comp_periodo AND "
                + "inv_compras.comp_motivo = anx_compra.comp_motivo AND inv_compras.comp_numero = anx_compra.comp_numero "
                + "WHERE inv_compras.prov_empresa = " + empresa + " AND inv_compras.prov_codigo = " + proveedor
                + " AND " + "inv_compras.comp_documento_tipo = " + tipoDocumento
                + " AND substring(inv_compras.comp_documento_numero,1,7) = " + secuencial
                + " AND NOT (comp_anulado OR comp_pendiente) " + "ORDER BY comp_fecha DESC LIMIT 1) ultima_factura "
                + "WHERE length(comp_autorizacion)=10 AND " + fechaFactura + " >= comp_fecha_emision AND "
                + fechaFactura + " <= comp_fecha_caduca;";

        List<AnxUltimaAutorizacionTO> listaAux = genericSQLDao.obtenerPorSql(sql, AnxUltimaAutorizacionTO.class);
        if (!listaAux.isEmpty()) {
            anxUltimaAutorizacionTO = new AnxUltimaAutorizacionTO();
            anxUltimaAutorizacionTO.setCompAutorizacion(listaAux.get(0).getCompAutorizacion());
            anxUltimaAutorizacionTO.setCompEmision(listaAux.get(0).getCompEmision());
            anxUltimaAutorizacionTO.setCompCaduca(listaAux.get(0).getCompCaduca());
        } else {
            anxUltimaAutorizacionTO = null;
        }
        return anxUltimaAutorizacionTO;
    }

    @Override
    public List<AnxListaRetencionesTO> getAnexoListaRetencionesTO(String empresa, String fechaDesde, String fechaHasta)
            throws Exception {
        String sql = "SELECT * FROM anexo.fun_listado_retenciones" + "('" + empresa + "', '" + fechaDesde + "', '"
                + fechaHasta + "')";
        return genericSQLDao.obtenerPorSql(sql, AnxListaRetencionesTO.class);
    }

    @Override
    public List<AnxListaRetencionesRentaTO> getAnexoListaRetencionesRentaTO(String empresa, String fechaDesde,
            String fechaHasta, String pOrden) throws Exception {
        pOrden = pOrden == null ? null : "'" + pOrden + "'";
        String sql = "SELECT * FROM anexo.fun_listado_retenciones_renta" + "('" + empresa + "', '" + fechaDesde + "', '"
                + fechaHasta + "', " + pOrden + ")";
        return genericSQLDao.obtenerPorSql(sql, AnxListaRetencionesRentaTO.class);
    }

    @Override
    public List<AnxListaRetencionesFuenteIvaTO> getAnexoListaRetencionesFuenteIvaTO(String empresa, String fechaDesde,
            String fechaHasta) throws Exception {
        String sql = "SELECT * FROM anexo.fun_listado_retenciones_iva" + "('" + empresa + "', '" + fechaDesde + "', '"
                + fechaHasta + "')";
        return genericSQLDao.obtenerPorSql(sql, AnxListaRetencionesFuenteIvaTO.class);
    }

    @Override
    public List<AnxListaRetencionesTO> getAnexoFunListadoRetencionesPorNumero(String empresa, String fechaDesde,
            String fechaHasta, String parametroEstado) throws Exception {
        fechaDesde = (fechaDesde == null ? null : "'" + fechaDesde + "'");
        fechaHasta = (fechaHasta == null ? null : "'" + fechaHasta + "'");
        parametroEstado = (parametroEstado == null ? "''" : "'" + parametroEstado + "'");

        String sql = "SELECT * FROM anexo.fun_listado_retenciones_por_numero" + "('" + empresa + "', " + fechaDesde
                + " , " + fechaHasta + " ,  " + parametroEstado + " )";

        return genericSQLDao.obtenerPorSql(sql, AnxListaRetencionesTO.class);
    }

    @Override
    public List<String> getAnexoFunListadoRetencionesHuerfanas(String empresa, String fechaDesde, String fechaHasta)
            throws Exception {
        String sql = "SELECT ret_retencionnumero FROM anexo.fun_listado_retenciones_por_numero " + "('" + empresa
                + "', '" + fechaDesde + "', '" + fechaHasta + "', 'ERROR')";

        return genericSQLDao.obtenerPorSql(sql);
    }

    @Override
    public List<AnxListaRetencionesPendientesTO> getAnexoListaRetencionesPendienteTO(String empresa) throws Exception {
        String sql = "SELECT * FROM anexo.fun_listado_retenciones_pendientes" + "('" + empresa + "')";
        return genericSQLDao.obtenerPorSql(sql, AnxListaRetencionesPendientesTO.class);
    }

    @Override
    public List<AnxListaRetencionesPendientesTO> getAnexoListaRetencionesPendienteAutomaticasTO(String empresa) throws Exception {
        String sql = "SELECT * FROM anexo.fun_listado_retenciones_pendientes" + "('" + empresa + "') where ret_auxiliar";
        return genericSQLDao.obtenerPorSql(sql, AnxListaRetencionesPendientesTO.class);
    }

    @Override
    public List<AnxFunListadoDevolucionIvaTO> getAnxFunListadoDevolucionIvaTOs(String empCodigo, String desde,
            String hasta) throws Exception {
        desde = desde == null ? null : "'" + desde + "'";
        hasta = hasta == null ? null : "'" + hasta + "'";

        String sql = "SELECT * FROM anexo.fun_listado_devolucion_iva('" + empCodigo + "', " + desde + ", " + hasta
                + ");";
        return genericSQLDao.obtenerPorSql(sql, AnxFunListadoDevolucionIvaTO.class);
    }

    @Override
    public List<AnxTalonResumenTO> getAnexoTalonResumenTO(String empresa, String fechaDesde, String fechaHasta)
            throws Exception {
        String sql = "SELECT * FROM anexo.fun_talon_resumen" + "('" + empresa + "', '" + fechaDesde + "', '"
                + fechaHasta + "')";

        return genericSQLDao.obtenerPorSql(sql, AnxTalonResumenTO.class);
    }

    @Override
    public List<AnxFormulario103TO> listarFormulario103(String empresa, String fechaDesde, String fechaHasta) throws Exception {
        String sql = "SELECT * FROM anexo.fun_formulario_103" + "('" + empresa + "', '" + fechaDesde + "', '" + fechaHasta + "')";
        return genericSQLDao.obtenerPorSql(sql, AnxFormulario103TO.class);
    }

    @Override
    public List<AnxFormulario104TO> listarFormulario104(String empresa, String fechaDesde, String fechaHasta) throws Exception {
        String sql = "SELECT * FROM anexo.fun_formulario_104" + "('" + empresa + "', '" + fechaDesde + "', '" + fechaHasta + "')";
        return genericSQLDao.obtenerPorSql(sql, AnxFormulario104TO.class);
    }

    @Override
    public List<AnxRetencionCompraTO> listarAnxRetencionCompraTO(String empresa, String fechaDesde, String fechaHasta, String codigoSustento, String registros) throws Exception {
        codigoSustento = codigoSustento == null ? null : "'" + codigoSustento + "'";
        registros = registros == null ? null : "'" + registros + "'";
        String sql = "SELECT * FROM anexo.fun_retencion_compras('" + empresa + "', '" + fechaDesde + "', '" + fechaHasta + "'," + codigoSustento + "," + registros + ")";
        return genericSQLDao.obtenerPorSql(sql, AnxRetencionCompraTO.class);
    }

    @Override
    public List<AnxRetencionVentaTO> getListaAnxRetencionVentaTO(String empresa, String fechaDesde, String fechaHasta, String registro) throws Exception {
        registro = registro == null ? null : "'" + registro + "'";
        String sql = "SELECT * FROM anexo.fun_retencion_ventas('" + empresa + "', '" + fechaDesde + "', '" + fechaHasta + "', " + registro + ")";
        return genericSQLDao.obtenerPorSql(sql, AnxRetencionVentaTO.class);
    }

    @Override
    public Boolean getComprobanteVerificarAutorizacion(String empresa, String comprobanteTipo, String establecimiento,
            String puntoEmision, String secuencialInicio, String secuencialFin) throws Exception {

        String sql = "SELECT * FROM anexo.fun_comprobante_verificar_utilizacion('" + empresa + "', '" + comprobanteTipo
                + "', '" + establecimiento + "', '" + puntoEmision + "', '" + secuencialInicio + "', '" + secuencialFin
                + "')";

        return (boolean) genericSQLDao.obtenerObjetoPorSql(sql);
    }

    @Override
    public List<String> getAnexoFunListadoComprobantesPendientes(String empresa, String fechaDesde, String fechaHasta)
            throws Exception {
        String sql = "SELECT * FROM anexo.fun_validar_comprobantes_pendientes " + "('" + empresa + "', '" + fechaDesde
                + "', '" + fechaHasta + "')";

        return genericSQLDao.obtenerPorSql(sql);
    }

}
