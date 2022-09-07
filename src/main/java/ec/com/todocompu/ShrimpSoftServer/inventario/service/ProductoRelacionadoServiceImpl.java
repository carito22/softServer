/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoRelacionadoDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoRelacionados;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CarolValdiviezo
 */
@Service
public class ProductoRelacionadoServiceImpl implements ProductoRelacionadoService {

    @Autowired
    ProductoRelacionadoDao productoRelacionadoDao;

    @Override
    public List<InvProductoRelacionados> getListInvProductoRelacionados(String empresa, String prodCodigoPrincipal) throws Exception {
        return productoRelacionadoDao.getListInvProductoRelacionados(empresa, prodCodigoPrincipal);
    }

}
