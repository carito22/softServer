/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalleSeries;
import java.util.List;

/**
 *
 * @author User
 */
public interface ComprasDetalleSeriesDao extends GenericDao<InvComprasDetalleSeries, Integer> {

    public List<InvComprasDetalleSeries> listarSeriesPorSecuencialDetalle(Integer secuencial) throws Exception;
}
