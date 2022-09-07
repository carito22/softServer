/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.report;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisConsultaUsuarioGrupoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisPeriodoInnecesarioTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisSucesoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisFirmaElectronica;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPcs;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisScannerConfiguracion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.report.ReporteSuceso;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Developer
 */
public interface ReporteSistemaService {

    byte[] generarReporteSistemaSucesos(UsuarioEmpresaReporteTO usuarioEmpresaReporte, List<SisSucesoTO> listado, String nombreReporte) throws Exception;

    byte[] generarReportePeriodos(List<SisListaPeriodoTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporte) throws Exception;

    public Map<String, Object> exportarReporteSistemaSucesos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<SisSucesoTO> listado);

    public Map<String, Object> exportarUsuario(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<SisConsultaUsuarioGrupoTO> listado);

    public Map<String, Object> exportarReportePeriodos(List<SisListaPeriodoTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO);

    public Map<String, Object> exportarSisEmpresaNotificaciones(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<SisEmpresaNotificaciones> listado);

    public Map<String, Object> exportarReporteSisPcs(List<SisPcs> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception;

    byte[] generarReporteSuceso(List<ReporteSuceso> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporte) throws Exception;

    public Map<String, Object> exportarReporteSisScannerConfiguracion(List<SisScannerConfiguracion> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception;

    public Map<String, Object> exportarReporteSisFirmaElectronica(List<SisFirmaElectronica> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception;

    public byte[] generarReportePeriodosErrores(List<SisPeriodoInnecesarioTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception;
    
     public Map<String, Object> exportarReportePeriodosErrores(List<SisPeriodoInnecesarioTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception;

}
