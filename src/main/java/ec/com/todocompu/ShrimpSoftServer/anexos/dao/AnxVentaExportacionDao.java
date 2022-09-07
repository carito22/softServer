/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentaExportacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaExportacion;
import java.util.List;

/**
 *
 * @author CarolValdiviezo
 */
public interface AnxVentaExportacionDao extends GenericDao<AnxVentaExportacion, Integer> {

    public AnxVentaExportacion obtenerAnxVentaExportacion(String vtaEmpres, String vtaPeriodo, String vtaMotivo, String vtaNumero);

    public List<AnxVentaExportacion> getListaAnxVentaExportacion(String empresa) throws Exception;

    public List<AnxListaVentaExportacionTO> getAnxListaVentaExportacionTO(String empresa, String fechaDesde, String fechaHasta) throws Exception;
}
