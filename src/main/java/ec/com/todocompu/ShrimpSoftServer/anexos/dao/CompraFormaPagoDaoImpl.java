package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraFormaPago;

@Repository
public class CompraFormaPagoDaoImpl extends GenericDaoImpl<AnxCompraFormaPago, Integer> implements CompraFormaPagoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<AnxCompraFormaPagoTO> getAnexoCompraFormaPagoTO(String empresa, String periodo, String motivo, String numero) throws Exception {
        String sqlCondicion = "";
        if (numero != null) {
            sqlCondicion = " AND anexo.anx_compra_forma_pago.comp_numero = '" + numero + "'";
        }
        String sql = "SELECT * FROM anexo.anx_compra_forma_pago WHERE anexo.anx_compra_forma_pago.comp_empresa = '"
                + empresa + "' AND " + " anexo.anx_compra_forma_pago.comp_periodo = '" + periodo + "' AND "
                + " anexo.anx_compra_forma_pago.comp_motivo = '" + motivo + "'"
                + sqlCondicion;
        return genericSQLDao.obtenerPorSql(sql, AnxCompraFormaPagoTO.class);
    }

    @Override
    public List<AnxCompraFormaPago> getAnexoCompraFormaPago(String empresa, String periodo, String motivo,
            String numeroCompra) throws Exception {

        String sql = "SELECT a FROM AnxCompraFormaPago a WHERE " + "a.anxCompra.anxCompraPK.compEmpresa = '" + empresa
                + "' AND a.anxCompra.anxCompraPK.compPeriodo = '" + periodo
                + "' AND a.anxCompra.anxCompraPK.compMotivo = '" + motivo + "' "
                + "AND a.anxCompra.anxCompraPK.compNumero = '" + numeroCompra + "' ORDER BY a.detOrden";
        return genericSQLDao.obtenerPorSql(sql, AnxCompraFormaPago.class);
    }
}
