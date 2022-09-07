/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.report;

import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Developer
 */
@Service
public class ReporteSistemasServiceImpl implements ReporteSistemaService {

    @Override
    public Map<String, Object> exportarReporteSistemaSucesos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<SisSucesoTO> listado) {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("STabla de sucesos");
            listaCabecera.add("S");
            listaCuerpo.add("SSecuencia" + "¬" + "STabla" + "¬" + "SClave" + "¬" + "SSuceso" + "¬" + "SDetalle" + "¬" + "SUsuario");
            for (SisSucesoTO suceso : listado) {
                listaCuerpo.add(
                        (suceso.getSusSecuencia() == null ? "B" : "S" + suceso.getSusSecuencia())
                        + "¬" + (suceso.getSusTabla() == null ? "B" : "S" + suceso.getSusTabla())
                        + "¬" + (suceso.getSusClave() == null ? "B" : "S" + suceso.getSusClave())
                        + "¬" + (suceso.getSusSuceso() == null ? "B" : "S" + suceso.getSusSuceso())
                        + "¬" + (suceso.getSusDetalle() == null ? "B" : "S" + suceso.getSusDetalle())
                        + "¬" + (suceso.getUsrCodigo() == null ? "B" : "S" + suceso.getUsrCodigo())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarUsuario(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<SisConsultaUsuarioGrupoTO> listado) {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SUsuarios");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SNick" + "¬" + "SApellidos" + "¬" + "SNombres" + "¬" + "SGrupo" + "¬" + "SEmail" + "¬" + "STeléfono" + "¬" + "SEstado");
            for (SisConsultaUsuarioGrupoTO usuario : listado) {
                listaCuerpo.add(
                        (usuario.getUsrCodigo() == null ? "B" : "S" + usuario.getUsrCodigo())
                        + "¬" + (usuario.getUsrNick() == null ? "B" : "S" + usuario.getUsrNick())
                        + "¬" + (usuario.getUsrApellido() == null ? "B" : "S" + usuario.getUsrApellido())
                        + "¬" + (usuario.getUsrNombre() == null ? "B" : "S" + usuario.getUsrNombre())
                        + "¬" + (usuario.getGruCodigo() == null ? "B" : "S" + usuario.getGruCodigo())
                        + "¬" + (usuario.getUsrEmail() == null ? "B" : "S" + usuario.getUsrEmail())
                        + "¬" + (usuario.getUsrTelefono() == null ? "B" : "S" + usuario.getUsrTelefono())
                        + "¬" + (usuario.getUsrActivo() ? "SActivo" : "SInactivo")
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Autowired
    private GenericReporteService genericReporteService;
    private String modulo = "sistema";

    @Override
    public byte[] generarReporteSistemaSucesos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<SisSucesoTO> listado, String nombreReporte) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReportePeriodos(List<SisListaPeriodoTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporte) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportPeriodos.jrxml", usuarioEmpresaReporte, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReportePeriodosErrores(List<SisPeriodoInnecesarioTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportPeriodosErrores.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public Map<String, Object> exportarReportePeriodos(List<SisListaPeriodoTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SPeriodos");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SDetalle" + "¬" + "SDesde" + "¬" + "SHasta" + "¬" + "SEstado");
            for (SisListaPeriodoTO periodo : listado) {
                listaCuerpo.add(
                        (periodo.getPerCodigo() == null ? "B" : "S" + periodo.getPerCodigo())
                        + "¬" + (periodo.getPerDetalle() == null ? "B" : "S" + periodo.getPerDetalle())
                        + "¬" + (periodo.getPerDesde() == null ? "B" : "S" + periodo.getPerDesde())
                        + "¬" + (periodo.getPerHasta() == null ? "B" : "S" + periodo.getPerHasta())
                        + "¬" + (periodo.getPerCerrado() ? "SCERRADO" : "SABIERTO")
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReportePeriodosErrores(List<SisPeriodoInnecesarioTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SPeriodos con Errores");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SDetalle");
            for (SisPeriodoInnecesarioTO periodo : listado) {
                listaCuerpo.add(
                        (periodo.getPerCodigo() == null ? "B" : "S" + periodo.getPerCodigo())
                        + "¬" + (periodo.getPerDetalle() == null ? "B" : "S" + periodo.getPerDetalle())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarSisEmpresaNotificaciones(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<SisEmpresaNotificaciones> listado) {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SConfiguracion de notificacions");
            listaCabecera.add("S");
            listaCuerpo.add("SPrincipal" + "¬" + "SSecundaria" + "¬" + "SDescripción" + "¬" + "SEvento notificación" + "¬" + "SEstado");
            listado.forEach((notificacion) -> {
                listaCuerpo.add(
                        (notificacion.getIdPrincipal() == null ? "B" : "S" + notificacion.getIdPrincipal())
                        + "¬" + (notificacion.getIdSecundaria() == null ? "B" : "S" + notificacion.getIdSecundaria())
                        + "¬" + (notificacion.getIdDescripcion() == null ? "B" : "S" + notificacion.getIdDescripcion())
                        + "¬" + (notificacion.getIdNotificacionesEventos() == null ? "B" : "S" + notificacion.getIdNotificacionesEventos())
                        + "¬" + (notificacion.getIdVerificado() ? "SVERIFICADO" : "SNO VERIFICADO")
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteSisPcs(List<SisPcs> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList<>();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            listaCabecera.add("SReporte de SisPcs");
            listaCabecera.add("SLista de SisPcs");
            listaCabecera.add("S");
            listaCuerpo.add("SMac" + "¬" + "SNombre" + "¬" + "SDescripción" + "¬" + "SEstado");
            for (SisPcs mac : listado) {
                listaCuerpo.add(
                        (mac.getInfMac() == null ? "B" : "S" + mac.getInfMac())
                        + "¬" + (mac.getInfNombre() == null ? "B" : "S" + mac.getInfNombre())
                        + "¬" + (mac.getInfDescripcion() == null ? "B" : "S" + mac.getInfDescripcion())
                        + "¬" + (mac.getInfEstado() ? "SActivo" : "SInactivo")
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteSisScannerConfiguracion(List<SisScannerConfiguracion> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList<>();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            listaCabecera.add("SReporte de configuración de Scanner");
            listaCabecera.add("S");
            listaCuerpo.add("SEmpresa" + "¬" + "SLicencia" + "¬" + "SEstado");
            for (SisScannerConfiguracion scanner : listado) {
                listaCuerpo.add(
                        (scanner.getScEmpresa() == null ? "B" : "S" + scanner.getScEmpresa())
                        + "¬" + (scanner.getScLicencia() == null ? "B" : "S" + scanner.getScLicencia())
                        + "¬" + (scanner.getScEstado() ? "SActivo" : "SInactivo")
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteSuceso(List<ReporteSuceso> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporte) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportSuceso.jrxml", usuarioEmpresaReporte, new HashMap<String, Object>(), listado);
    }

    @Override
    public Map<String, Object> exportarReporteSisFirmaElectronica(List<SisFirmaElectronica> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList<>();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            listaCabecera.add("SReporte de firma electrónica");
            listaCabecera.add("S");
            listaCuerpo.add("SEmpresa" + "¬" + "SNombre archivo" + "¬" + "SFecha aviso" + "¬" + "SFecha caducidad");
            listado.forEach((firma) -> {
                listaCuerpo.add(
                        (firma.getFirmaEmpresa() == null ? "B" : "S" + firma.getFirmaEmpresa())
                        + "¬" + (firma.getFirmaElectronicaNombre() == null ? "B" : "S" + firma.getFirmaElectronicaNombre())
                        + "¬" + (firma.getFirmaFechaAviso() == null ? "B" : "S" + UtilsDate.fechaFormatoString(firma.getFirmaFechaAviso(), "dd-MM-yyyy"))
                        + "¬" + (firma.getFirmaFechaCaducidad() == null ? "B" : "S" + UtilsDate.fechaFormatoString(firma.getFirmaFechaCaducidad(), "dd-MM-yyyy"))
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

}
