package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprobantesTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComprobantesPublicosServiceImpl implements ComprobantesPublicosService {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<InvComprobantesTO> listarComprobantesPublicos(String comprobante, String identificador, String fechaDesde, String fechaHasta, String tipoDocumento) throws Exception {
        List<InvComprobantesTO> comprobantes = new ArrayList<>();
        List<InvComprobantesTO> comprobantesVentas = listarComprobantesVentas(comprobante, identificador, fechaDesde, fechaHasta, tipoDocumento);
        List<InvComprobantesTO> comprobantesCompras = listarComprobantesCompras(comprobante, identificador, fechaDesde, fechaHasta, tipoDocumento);
        comprobantes.addAll(comprobantesVentas);
        comprobantes.addAll(comprobantesCompras);
        return comprobantes;
    }

    public List<InvComprobantesTO> listarComprobantesVentas(String comprobante, String identificador, String fechaDesde, String fechaHasta, String tipoComprobante) throws Exception {
        String sql = "select v.vta_empresa || '|' || v.vta_periodo || '|' || v.vta_motivo || '|' || v.vta_numero id,"
                + " v.vta_documento_numero documento_numero, v.vta_empresa empresa, v.vta_periodo periodo,"
                + " v.vta_numero numero, v.vta_motivo motivo, v.vta_documento_tipo tipo, v.cli_codigo codigo,"
                + " c.cli_id_numero identificador, c.cli_email email, v.vta_fecha fecha"
                + " from inventario.inv_ventas v"
                + " inner join inventario.inv_cliente c on v.vta_empresa = c.cli_empresa and v.cli_codigo = c.cli_codigo"
                + " inner join anexo.anx_venta_electronica ve on ve.vta_empresa = v.vta_empresa and ve.vta_periodo = v.vta_periodo and ve.vta_motivo = v.vta_motivo and ve.vta_numero = v.vta_numero "
                + " where v.vta_documento_numero is not null and c.cli_id_numero = '" + identificador + "' ";
        if (comprobante != null) {
            sql = sql + " and v.vta_documento_numero = '" + comprobante + "' ";
        }
        if (tipoComprobante != null) {
            sql = sql + " and v.vta_documento_tipo = '" + tipoComprobante + "' ";
        }
        if (fechaDesde != null && fechaHasta != null) {
            sql = sql + " and v.vta_fecha between '" + fechaDesde + "' and '" + fechaHasta + "' ";
        }
        sql = sql + " order by v.vta_fecha desc ";
        return genericSQLDao.obtenerPorSql(sql, InvComprobantesTO.class);
    }

    public List<InvComprobantesTO> listarComprobantesCompras(String comprobante, String identificador, String fechaDesde, String fechaHasta, String tipoComprobante) throws Exception {
        String sql = "select c.comp_empresa || '|' || c.comp_periodo || '|' || c.comp_motivo || '|' || c.comp_numero id,"
                + " c.comp_documento_numero documento_numero, c.comp_empresa empresa, c.comp_periodo periodo, "
                + " c.comp_numero numero, c.comp_motivo motivo, c.comp_documento_tipo tipo, c.prov_codigo codigo,"
                + " p.prov_id_numero identificador, p.prov_email email, c.comp_fecha fecha"
                + " from inventario.inv_compras c"
                + " inner join inventario.inv_proveedor p on c.prov_empresa = p.prov_empresa and c.prov_codigo = p.prov_codigo "
                + " inner join anexo.anx_compra_electronica ce on ce.comp_empresa = c.comp_empresa and ce.comp_periodo = c.comp_periodo and ce.comp_motivo = c.comp_motivo and ce.comp_numero = c.comp_numero "
                + " where c.comp_documento_numero is not null and p.prov_id_numero = '" + identificador + "' ";
        if (comprobante != null) {
            sql = sql + " and c.comp_documento_numero = '" + comprobante + "' ";
        }
        if (tipoComprobante != null) {
            sql = sql + " and c.comp_documento_tipo = '" + tipoComprobante + "' ";
        }
        if (fechaDesde != null && fechaHasta != null) {
            sql = sql + " and c.comp_fecha between '" + fechaDesde + "' and '" + fechaHasta + "' ";
        }
        sql = sql + " order by c.comp_fecha desc ";
        return genericSQLDao.obtenerPorSql(sql, InvComprobantesTO.class);
    }

}
