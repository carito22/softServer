package ec.com.todocompu.ShrimpSoftServer.util.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public interface ArchivoService {

    public String generarReportePDF(byte[] respuesta, String nombrearchivo, HttpServletResponse response) throws Exception;

    public String generarReporteEXCEL(byte[] respuesta, String nombrearchivo, HttpServletResponse response) throws Exception;

    public void generarReporteXLSAngular(List<String> listaCabecera, List<String> listaCuerpo, HttpServletResponse response) throws Exception;

    public String generaReportePDFMultiple(List<InputStream> list, HttpServletResponse response, String reporte) throws Exception;

    public File respondeServidorPDF(String reporte) throws Exception;
    
    public String obtenerRutaDeReporteTemporal(byte[] bFile, String archivo) throws Exception;
    
}
