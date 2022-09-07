/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaLiquidacionComprasElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxLiquidacionComprasElectronica;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxLiquidacionComprasElectronicaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEntidadTransaccionTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CarolValdiviezo
 */
@Transactional
public interface LiquidacionCompraElectronicaService {

    public List<AnxListaLiquidacionComprasElectronicaTO> getListaAnxListaLiquidacionCompraElectronicaTO(String empresa, String fechaDesde, String fechaHasta) throws Exception;

    public List<AnxLiquidacionComprasElectronicaNotificaciones> listarNotificacionesElectronicas(String empresa, String periodo, String motivo, String numero) throws Exception;

    public InvEntidadTransaccionTO obtenerProveedorDeLiquidacionCompra(String empresa, String periodo, String motivo, String numero) throws Exception;

    public AnxLiquidacionComprasElectronica buscarAnxLiquidacionCompraElectronica(String empresa, String periodo, String motivo, String numero) throws Exception;

}
