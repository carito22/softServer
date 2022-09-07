/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.activoFijo.report;

import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfDepreciacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Trabajo
 */
@Service
public class ReporteActivoFijoSericeImpl implements ReporteActivoFijoSerice {

    @Autowired
    private GenericReporteService genericReporteService;
    private final String modulo = "activosFijos";

    @Override
    public byte[] generarReporteDepreciacionConsulta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String desde,
            String hasta, List<AfDepreciacionesTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportAfDepreciacionesDetalle.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }
}
