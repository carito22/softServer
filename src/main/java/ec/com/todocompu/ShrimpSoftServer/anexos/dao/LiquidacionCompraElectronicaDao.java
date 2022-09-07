/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaLiquidacionComprasElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxLiquidacionComprasElectronica;
import java.util.List;

/**
 *
 * @author CarolValdiviezo
 */
public interface LiquidacionCompraElectronicaDao extends GenericDao<AnxLiquidacionComprasElectronica, Integer> {

    public AnxLiquidacionComprasElectronica buscarAnxLiquidacionCompraElectronica(String empresa, String periodo, String motivo, String numero) throws Exception;

    public List<AnxListaLiquidacionComprasElectronicaTO> getListaAnxListaLiquidacionCompraElectronicaTO(String empresa, String fechaDesde, String fechaHasta) throws Exception;
}
