/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.AnxVentaExportacionDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaVentaExportacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxPaisTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCodigoRegimen;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxDistritosAduaneros;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxParaisosFiscales;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxTiposExportacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxTiposIngresoExterior;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxTiposRegimenFiscalExterior;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaExportacion;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CarolValdiviezo
 */
@Service
public class ExportacionesServiceImpl implements ExportacionesService {

    @Autowired
    private AnxCodigoRegimenService anxCodigoRegimenService;
    @Autowired
    private AnxDistritosAduanerosService anxDistritosAduanerosService;
    @Autowired
    private AnxParaisosFiscalesService anxParaisosFiscalesService;
    @Autowired
    private AnxTiposExportacionService anxTiposExportacionService;
    @Autowired
    private AnxTiposIngresoExteriorService anxTiposIngresoExteriorService;
    @Autowired
    private AnxTiposRegimenFiscalExteriorService anxTiposRegimenFiscalExteriorService;
    @Autowired
    private PaisService paisService;
    @Autowired
    private AnxVentaExportacionDao anxVentaExportacionDao;

    @Override
    public AnxVentaExportacion obtenerAnxVentaExportacion(int secuencial) {
        return anxVentaExportacionDao.obtenerPorId(AnxVentaExportacion.class, secuencial);
    }

    @Override
    public AnxVentaExportacion obtenerAnxVentaExportacion(String vtaEmpres, String vtaPeriodo, String vtaMotivo, String vtaNumero) {
        return anxVentaExportacionDao.obtenerAnxVentaExportacion(vtaEmpres, vtaPeriodo, vtaMotivo, vtaNumero);
    }

    @Override
    public List<AnxListaVentaExportacionTO> getAnxListaVentaExportacionTO(String empresa, String fechaDesde, String fechaHasta) throws Exception {
        return anxVentaExportacionDao.getAnxListaVentaExportacionTO(empresa, fechaDesde, fechaHasta);
    }

    @Override
    public Map<String, Object> obtenerDatosParaExportaciones(String empresa) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        List<AnxTiposRegimenFiscalExterior> listadoAnxTiposRegimenFiscalExterior = anxTiposRegimenFiscalExteriorService.getListaAnxTiposRegimenFiscalExterior();
        List<AnxTiposIngresoExterior> listadoAnxTiposIngresoExterior = anxTiposIngresoExteriorService.getListaAnxTiposIngresoExterior();
        List<AnxTiposExportacion> listadoAnxTiposExportacion = anxTiposExportacionService.getListaAnxTiposExportacion();
        List<AnxParaisosFiscales> listadoAnxParaisosFiscales = anxParaisosFiscalesService.getListaAnxParaisosFiscales();
        List<AnxDistritosAduaneros> listadoAnxDistritosAduaneros = anxDistritosAduanerosService.getListaAnxDistritosAduaneros();
        List<AnxCodigoRegimen> listadoAnxCodigoRegimen = anxCodigoRegimenService.getListaAnxCodigoRegimen();
        List<AnxPaisTO> listadoPaises = paisService.getComboAnxPaisTO();

        campos.put("listadoAnxTiposRegimenFiscalExterior", listadoAnxTiposRegimenFiscalExterior);
        campos.put("listadoAnxTiposIngresoExterior", listadoAnxTiposIngresoExterior);
        campos.put("listadoAnxTiposExportacion", listadoAnxTiposExportacion);
        campos.put("listadoAnxParaisosFiscales", listadoAnxParaisosFiscales);
        campos.put("listadoAnxDistritosAduaneros", listadoAnxDistritosAduaneros);
        campos.put("listadoAnxCodigoRegimen", listadoAnxCodigoRegimen);
        campos.put("listadoAnxPaisTO", listadoPaises);
        return campos;

    }

}
