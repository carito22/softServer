/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoFormula;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import java.util.List;

/**
 *
 * @author CarolValdiviezo
 */
public interface ProductoFormulaDao extends GenericDao<InvProductoFormula, Integer> {

    public List<InvProductoFormula> getListInvProductoFormula(String empresa, String prodCodigoPrincipal) throws Exception;

    public boolean tieneProductoFormula(String empresa, String codigoProductoPrincipal) throws Exception;
}
