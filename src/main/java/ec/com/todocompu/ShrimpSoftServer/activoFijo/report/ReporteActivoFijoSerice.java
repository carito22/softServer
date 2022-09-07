/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.activoFijo.report;

import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfDepreciacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.util.List;

/**
 *
 * @author Trabajo
 */
public interface ReporteActivoFijoSerice {

    public byte[] generarReporteDepreciacionConsulta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String desde,
            String hasta, List<AfDepreciacionesTO> listado) throws Exception;

}
