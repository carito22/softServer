package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraReembolsoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraReembolso;

@Repository
public class CompraReembolsoDaoImpl extends GenericDaoImpl<AnxCompraReembolso, Integer> implements CompraReembolsoDao {

	@Autowired
	private GenericSQLDao genericSQLDao;

	@Override
	public List<AnxCompraReembolsoTO> getAnexoCompraReembolsoTOs(String empresa, String periodo, String motivo,
			String numeroCompra) throws Exception {
		String cad = "SELECT  anexo.anx_compra_reembolso.reemb_secuencial, anexo.anx_compra_reembolso.reemb_documento_tipo, "
				+ " anexo.anx_compra_reembolso.reemb_documento_numero, "
				+ " anexo.anx_compra_reembolso.reemb_fechaemision, "
				+ " anexo.anx_compra_reembolso.reemb_autorizacion, "
				+ " anexo.anx_compra_reembolso.reemb_baseimponible, "
				+ " anexo.anx_compra_reembolso.reemb_baseimpgrav, " + " anexo.anx_compra_reembolso.reemb_basenograiva, "
				+ " anexo.anx_compra_reembolso.reemb_montoice, " + " anexo.anx_compra_reembolso.reemb_montoiva, "
				+ " anexo.anx_compra_reembolso.prov_empresa, " + " anexo.anx_compra_reembolso.prov_codigo, "
				+ " anexo.anx_compra_reembolso.comp_empresa, " + " anexo.anx_compra_reembolso.comp_periodo, "
				+ " anexo.anx_compra_reembolso.comp_motivo, " + " anexo.anx_compra_reembolso.comp_numero, "
				+ " anexo.anx_tipocomprobante.tc_abreviatura, " + " inventario.inv_proveedor.prov_razon_social, "
				+ " inventario.inv_proveedor.prov_id_numero " + " FROM anexo.anx_compra_reembolso"
				+ " INNER JOIN anexo.anx_tipocomprobante ON anexo.anx_compra_reembolso.reemb_documento_tipo = anexo.anx_tipocomprobante.tc_codigo"
				+ " INNER JOIN inventario.inv_proveedor ON anexo.anx_compra_reembolso.prov_empresa = inventario.inv_proveedor.prov_empresa AND anexo.anx_compra_reembolso.prov_codigo = inventario.inv_proveedor.prov_codigo WHERE "
				+ " anexo.anx_compra_reembolso.comp_empresa = '" + empresa + "' AND "
				+ " anexo.anx_compra_reembolso.comp_periodo = '" + periodo + "' AND "
				+ " anexo.anx_compra_reembolso.comp_motivo = '" + motivo + "' AND "
				+ " anexo.anx_compra_reembolso.comp_numero = '" + numeroCompra + "'";

		return genericSQLDao.obtenerPorSql(cad, AnxCompraReembolsoTO.class);
	}
}
