/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaReembolsoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaReembolso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Trabajo
 */
@Repository
public class AnxVentaReembolsoDaoImpl extends GenericDaoImpl<AnxVentaReembolso, Integer> implements AnxVentaReembolsoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<AnxVentaReembolsoTO> getListaAnxVentaReembolsoTO(String vtaEmpresa, String vtaPeriodo, String vtaMotivo, String vtaNumero) throws Exception {

        String cad = "SELECT  anexo.anx_venta_reembolso.reemb_secuencial, "
                + " inventario.inv_compras.comp_documento_tipo reemb_documento_tipo, "
                + " inventario.inv_compras.comp_documento_numero reemb_documento_numero, "
                + " inventario.inv_compras.comp_fecha reemb_fechaemision, "
                + " anexo.anx_compra.comp_autorizacion reemb_autorizacion, "
                + " inventario.inv_compras.comp_base0 reemb_baseimponible, "
                + " inventario.inv_compras.comp_base_imponible reemb_baseimpgrav, "
                + " inventario.inv_compras.comp_base_no_objeto reemb_basenograiva, "
                + " inventario.inv_compras.comp_ice reemb_montoice, "
                + " inventario.inv_compras.comp_monto_iva reemb_montoiva, "
                + " inventario.inv_compras.prov_empresa, "
                + " inventario.inv_compras.prov_codigo, "
                + " inventario.inv_proveedor.prov_id_tipo, "
                + " inventario.inv_proveedor.prov_razon_social, "
                + " inventario.inv_proveedor.prov_id_numero, "
                + " inventario.inv_proveedor_categoria.pc_detalle prov_categoria_detalle, "
                + " anexo.anx_tipocomprobante.tc_abreviatura, "
                + " anexo.anx_venta_reembolso.vta_empresa, "
                + " anexo.anx_venta_reembolso.vta_periodo, "
                + " anexo.anx_venta_reembolso.vta_motivo, "
                + " anexo.anx_venta_reembolso.vta_numero, "
                + " anexo.anx_venta_reembolso.comp_empresa, "
                + " anexo.anx_venta_reembolso.comp_periodo, "
                + " anexo.anx_venta_reembolso.comp_motivo, "
                + " anexo.anx_venta_reembolso.comp_numero, "
                + " inventario.inv_compras.comp_iva_vigente reemb_iva_vigente "
                + " FROM anexo.anx_venta_reembolso "
                + " INNER JOIN inventario.inv_compras "
                + " ON anexo.anx_venta_reembolso.comp_empresa = inventario.inv_compras.comp_empresa AND anexo.anx_venta_reembolso.comp_periodo = inventario.inv_compras.comp_periodo "
                + " AND anexo.anx_venta_reembolso.comp_motivo = inventario.inv_compras.comp_motivo AND anexo.anx_venta_reembolso.comp_numero = inventario.inv_compras.comp_numero "
                + " INNER JOIN anexo.anx_tipocomprobante "
                + " ON inventario.inv_compras.comp_documento_tipo = anexo.anx_tipocomprobante.tc_codigo "
                + " INNER JOIN inventario.inv_proveedor "
                + " ON inventario.inv_compras.prov_empresa = inventario.inv_proveedor.prov_empresa AND inventario.inv_compras.prov_codigo = inventario.inv_proveedor.prov_codigo "
                + " INNER JOIN inventario.inv_proveedor_categoria "
                + " ON inventario.inv_proveedor.pc_empresa = inventario.inv_proveedor_categoria.pc_empresa AND inventario.inv_proveedor.pc_codigo = inventario.inv_proveedor_categoria.pc_codigo "
                + " INNER JOIN anexo.anx_compra "
                + " ON anexo.anx_compra.comp_empresa = inventario.inv_compras.comp_empresa AND anexo.anx_compra.comp_periodo = inventario.inv_compras.comp_periodo "
                + " AND anexo.anx_compra.comp_motivo = inventario.inv_compras.comp_motivo AND anexo.anx_compra.comp_numero = inventario.inv_compras.comp_numero "
                + " WHERE anexo.anx_venta_reembolso.vta_empresa = '" + vtaEmpresa + "' AND "
                + " anexo.anx_venta_reembolso.vta_periodo = '" + vtaPeriodo + "' AND "
                + " anexo.anx_venta_reembolso.vta_motivo = '" + vtaMotivo + "' AND "
                + " anexo.anx_venta_reembolso.vta_numero = '" + vtaNumero + "'";
        return genericSQLDao.obtenerPorSql(cad, AnxVentaReembolsoTO.class);
    }

}
