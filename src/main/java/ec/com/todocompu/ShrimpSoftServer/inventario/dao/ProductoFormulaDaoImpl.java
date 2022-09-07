/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoFormula;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CarolValdiviezo
 */
@Repository
public class ProductoFormulaDaoImpl extends GenericDaoImpl<InvProductoFormula, Integer> implements ProductoFormulaDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<InvProductoFormula> getListInvProductoFormula(String empresa, String prodCodigoPrincipal) throws Exception {
        String sql = "SELECT * FROM inventario.inv_producto_formula WHERE pr_principal_empresa = ('" + empresa + "') AND pr_principal_codigo = ('" + prodCodigoPrincipal + "') Order by pr_secuencial ASC";
        return genericSQLDao.obtenerPorSql(sql, InvProductoFormula.class);
    }

    @Override
    public boolean tieneProductoFormula(String empresa, String codigoProductoPrincipal) throws Exception {
        empresa = empresa.compareTo("") == 0 ? null : "'" + empresa + "'";
        codigoProductoPrincipal = codigoProductoPrincipal.compareTo("") == 0 ? null : "'" + codigoProductoPrincipal + "'";

        String sql = "SELECT COUNT(*)!=0  FROM inventario.inv_producto_formula  WHERE pr_principal_empresa= " + empresa + " AND pr_principal_codigo= " + codigoProductoPrincipal;
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }
}
