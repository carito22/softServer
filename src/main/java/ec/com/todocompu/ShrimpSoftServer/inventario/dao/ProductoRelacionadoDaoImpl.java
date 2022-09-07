/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoRelacionados;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CarolValdiviezo
 */
@Repository
public class ProductoRelacionadoDaoImpl extends GenericDaoImpl<InvProductoRelacionados, Integer> implements ProductoRelacionadoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<InvProductoRelacionados> getListInvProductoRelacionados(String empresa, String prodCodigoPrincipal) throws Exception {
        String sql = "SELECT * FROM inventario.inv_productos_relacionados WHERE pr_principal_empresa = ('" + empresa + "') AND pr_principal_codigo = ('" + prodCodigoPrincipal + "') Order by pr_secuencial ASC";
        return genericSQLDao.obtenerPorSql(sql, InvProductoRelacionados.class);
    }

}
