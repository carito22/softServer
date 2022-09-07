package ec.com.todocompu.ShrimpSoftServer.anexos.report;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.web.ComboGenericoTO;
import java.io.InputStream;
import java.util.List;

public interface ReporteComprobanteElectronicoService {

    public List<InputStream> generarReporteComprobanteElectronicoLote(List<ComboGenericoTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception;

    public byte[] generarReporteComprobanteElectronico(String empresa, String claveAcceso, String XmlString, String nombreReporteJasper, String rutaPersonalizada, String rutaLogo) throws Exception;

    public String validarExistenciaReportesElectronicos(String empresa, String claveAcceso, String nombreReporteJasper, String rutaPersonalizada) throws Exception;

    public String obtenerNombreReporte(String claveAcceso) throws Exception;
}
