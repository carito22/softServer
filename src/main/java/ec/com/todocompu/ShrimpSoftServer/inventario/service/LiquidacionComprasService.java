/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxLiquidacionComprasElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

/**
 *
 * @author CarolValdiviezo
 */
public interface LiquidacionComprasService {

    public Map<String, Object> obtenerDatosParaLiquidacionCompra(Map<String, Object> map) throws Exception;

    public String accionAnxLiquidacionComprasElectronicaTO(AnxLiquidacionComprasElectronicaTO anxLiquidacionComprasElectronicaTO, char accion, SisInfoTO sisInfoTO) throws Exception;

    public String getXmlComprobanteElectronico(String empresa, String ePeriodo, String eMotivo, String eNumero) throws Exception;

    public boolean comprobarAnxLiquidacionComprasElectronica(String empresa, String periodo, String motivo, String numero) throws Exception;

    public String comprobarAnxLiquidacionComprasElectronicaAutorizacion(String empresa, String periodo, String motivo, String numero) throws Exception;
}
