/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.activoFijo.service;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfDepreciacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfDepreciaciones;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import java.util.Map;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CarolValdiviezo
 */
@Transactional
public interface DepreciacionActivoFijoService {

    public List<AfDepreciacionesTO> listarDepreciaciones(String empresa, String fechaHasta) throws Exception;

    public List<AfDepreciacionesTO> listarDepreciacionesConsulta(String empresa, String fechaDesde, String fechaHasta) throws Exception;

    public List<AfDepreciaciones> listarDepreciacionesSegunContable(String empresa, String periodo, String tipo, String numero) throws Exception;

    public MensajeTO insertarModificarAfDepreciaciones(String empresa, String fecha, List<AfDepreciacionesTO> listaAfDepreciacionesTO, SisInfoTO sisInfoTO) throws Exception;

    public boolean productoSeEncuentraDepreciacion(String empresa, String codigoProducto) throws Exception;

    public List<ConContableTO> obtenerContablesSegunDepreciacion(String empresa, String periodo, String codigoProducto) throws Exception;

    public Map<String, Object> exportarListadoDepreciacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaIngreso, List<AfDepreciacionesTO> listado) throws Exception;

}
