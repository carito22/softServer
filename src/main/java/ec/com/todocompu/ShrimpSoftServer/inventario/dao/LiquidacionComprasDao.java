/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxLiquidacionComprasElectronica;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

/**
 *
 * @author CarolValdiviezo
 */
public interface LiquidacionComprasDao extends GenericDao<AnxLiquidacionComprasElectronica, Integer> {

    public AnxLiquidacionComprasElectronica buscarAnxLiquidacionComprasElectronica(String empresa, String periodo, String motivo, String numero) throws Exception;

    public Boolean accionAnxLiquidacionComprasElectronica(AnxLiquidacionComprasElectronica anxLiquidacionComprasElectronica, SisSuceso sisSuceso, char accion) throws Exception;

    public String getXmlComprobanteElectronico(String empresa, String ePeriodo, String eMotivo, String eNumero) throws Exception;

    public boolean comprobarAnxLiquidacionComprasElectronica(String empresa, String periodo, String motivo, String numero) throws Exception;

    public String comprobarAnxLiquidacionComprasElectronicaAutorizacion(String empresa, String periodo, String motivo, String numero) throws Exception;
}
