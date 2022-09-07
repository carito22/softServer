/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoFormulaDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoFormula;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CarolValdiviezo
 */
@Service
public class ProductoFormulaServiceImpl implements ProductoFormulaService {

    @Autowired
    ProductoFormulaDao productoFormulaDao;

    @Override
    public List<InvProductoFormula> getListInvProductoFormula(String empresa, String prodCodigoPrincipal) throws Exception {
        return productoFormulaDao.getListInvProductoFormula(empresa, prodCodigoPrincipal);
    }

    @Override
    public boolean tieneProductoFormula(String empresa, String codigoProductoPrincipal) throws Exception {
        return productoFormulaDao.tieneProductoFormula(empresa, codigoProductoPrincipal);
    }

}
