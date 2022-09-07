/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.LiquidacionCompraElectronicaDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaLiquidacionComprasElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxLiquidacionComprasElectronica;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxLiquidacionComprasElectronicaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEntidadTransaccionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCompras;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CarolValdiviezo
 */
@Service
public class LiquidacionCompraElectronicaServiceImpl implements LiquidacionCompraElectronicaService {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private LiquidacionCompraElectronicaDao liquidacionCompraElectronicaDao;
    @Autowired
    private ComprasDao comprasDao;

    @Override
    public List<AnxLiquidacionComprasElectronicaNotificaciones> listarNotificacionesElectronicas(String empresa, String periodo, String motivo, String numero) throws Exception {
        String query = "SELECT * FROM anexo.anx_liquidacion_compras_electronica_notificaciones n"
                + " WHERE n.comp_empresa = '" + empresa + "' AND n.comp_periodo = '" + periodo + "' AND n.comp_motivo = '" + motivo + "' AND n.comp_numero = '" + numero + "'  order by n.e_fecha;";
        List<AnxLiquidacionComprasElectronicaNotificaciones> notificaciones = genericSQLDao.obtenerPorSql(query, AnxLiquidacionComprasElectronicaNotificaciones.class);
        return notificaciones;
    }

    @Override
    public AnxLiquidacionComprasElectronica buscarAnxLiquidacionCompraElectronica(String empresa, String periodo, String motivo, String numero) throws Exception {
        return liquidacionCompraElectronicaDao.buscarAnxLiquidacionCompraElectronica(empresa, periodo, motivo, numero);
    }

    @Override
    public List<AnxListaLiquidacionComprasElectronicaTO> getListaAnxListaLiquidacionCompraElectronicaTO(String empresa, String fechaDesde, String fechaHasta) throws Exception {
        return liquidacionCompraElectronicaDao.getListaAnxListaLiquidacionCompraElectronicaTO(empresa, fechaDesde, fechaHasta);
    }

    @Override
    public InvEntidadTransaccionTO obtenerProveedorDeLiquidacionCompra(String empresa, String periodo, String motivo, String numero) throws Exception {
        InvCompras compra = comprasDao.buscarInvCompras(empresa, periodo, motivo, numero);
        if (compra != null) {
            InvEntidadTransaccionTO entidadTransaccion = new InvEntidadTransaccionTO();
            entidadTransaccion.setDocumento(compra.getCompDocumentoNumero());
            entidadTransaccion.setTipo("Liquidación compras");
            entidadTransaccion.setRazonSocial(compra.getInvProveedor().getProvRazonSocial());
            entidadTransaccion.setIdentificacion(compra.getInvProveedor().getProvIdNumero());
            return entidadTransaccion;
        }
        return null;
    }

}
