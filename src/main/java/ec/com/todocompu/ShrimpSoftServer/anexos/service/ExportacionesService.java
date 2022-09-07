/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentaExportacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaExportacion;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CarolValdiviezo
 */
@Transactional
public interface ExportacionesService {

    public AnxVentaExportacion obtenerAnxVentaExportacion(int secuencial);

    public AnxVentaExportacion obtenerAnxVentaExportacion(String vtaEmpres, String vtaPeriodo, String vtaMotivo, String vtaNumero);

    public Map<String, Object> obtenerDatosParaExportaciones(String empresa) throws Exception;

    public List<AnxListaVentaExportacionTO> getAnxListaVentaExportacionTO(String empresa, String fechaDesde, String fechaHasta) throws Exception;
}
