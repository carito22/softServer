/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoFormula;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CarolValdiviezo
 */
@Transactional
public interface ProductoFormulaService {

    public List<InvProductoFormula> getListInvProductoFormula(String empresa, String prodCodigoPrincipal) throws Exception;

    public boolean tieneProductoFormula(String empresa, String codigoProductoPrincipal) throws Exception;
}
