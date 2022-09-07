package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoRelacionados;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author CarolValdiviezo
 */
public interface ProductoRelacionadoDao extends GenericDao<InvProductoRelacionados, Integer> {

    public List<InvProductoRelacionados> getListInvProductoRelacionados(String empresa, String prodCodigoPrincipal) throws Exception;
}
