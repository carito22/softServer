package ec.com.todocompu.ShrimpSoftServer.util.service;

import com.lowagie.text.pdf.*;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcel;
import static ec.com.todocompu.ShrimpSoftUtils.UtilsRESTFul.generaNumeroAleatorio;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.ServletOutputStream;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ArchivoServiceImpl implements ArchivoService {

    @Autowired
    protected DataSource dataSource;

    @Override
    public String generarReportePDF(byte[] respuesta, String archivo, HttpServletResponse response) throws Exception {
        try {
            File carpeta = new File("tmp/");
            if (!carpeta.exists()) {
                carpeta.mkdir();
            }
            archivo = "tmp/" + archivo;
            File fileReport = new File(archivo);
            if (fileReport.exists()) {
                archivo = archivo.replaceAll(".jrprint", "") + generaNumeroAleatorio(1, 100) + ".jrprint";
            }
            FileOutputStream output = new FileOutputStream(archivo);
            output.write(respuesta);
            output.close();
            response.setContentType("application/pdf");
            response.addHeader("Content-disposition", "inline; filename=" + archivo.replaceAll("tmp/", "").replaceAll(".jrprint", "") + ".pdf");

            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(archivo));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            exporter.setConfiguration(configuration);
            exporter.exportReport();

        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    @Override
    public String obtenerRutaDeReporteTemporal(byte[] bFile, String archivo) {
        try {
            File carpeta = new File("tmp/");
            if (!carpeta.exists()) {
                carpeta.mkdir();
            }
            archivo = "tmp/" + archivo;
            File fileReport = new File(archivo);
            if (fileReport.exists()) {
                archivo = archivo.replaceAll(".jrprint", "") + generaNumeroAleatorio(1, 100) + ".jrprint";
            }

            FileOutputStream output = new FileOutputStream(archivo);
            output.write(bFile);
            output.close();

            return archivo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public String generarReporteEXCEL(byte[] respuesta, String archivo, HttpServletResponse response) throws Exception {
        try {
            File carpeta = new File("tmp/");
            if (!carpeta.exists()) {
                carpeta.mkdir();
            }
            archivo = "tmp/" + archivo;
            File fileReport = new File(archivo);
            if (fileReport.exists()) {
                archivo = archivo.replaceAll(".jrprint", "") + generaNumeroAleatorio(1, 100) + ".jrprint";
            }
            FileOutputStream output = new FileOutputStream(archivo);
            output.write(respuesta);
            output.close();
            response.setContentType("application/xls");
            response.addHeader("Content-disposition", "inline; filename=" + archivo.replaceAll("tmp/", "").replaceAll(".jrprint", "") + ".xls");
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setExporterInput(new SimpleExporterInput(archivo));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
            configuration.setWhitePageBackground(false);
            configuration.setIgnoreGraphics(Boolean.FALSE);
            configuration.setIgnoreTextFormatting(Boolean.FALSE);
            exporter.setConfiguration(configuration);
            exporter.exportReport();
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    @Override
    public void generarReporteXLSAngular(List<String> listaCabecera, List<String> listaCuerpo, HttpServletResponse response) throws Exception {
        try {
            String nombreReporte = UtilsExcel.crearExcel(listaCabecera, listaCuerpo, "TODOCOMPU");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.addHeader("Content-disposition", "attachment; filename=" + nombreReporte);
            ServletOutputStream servletStream = response.getOutputStream();
            File f = new File(nombreReporte);
            InputStream in = new FileInputStream(f);
            int bit;
            while ((bit = in.read()) != -1) {
                servletStream.write(bit);
            }
            servletStream.flush();
            servletStream.close();
            in.close();
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String generaReportePDFMultiple(List<InputStream> list, HttpServletResponse response, String reporte) throws Exception {
        response.setContentType("application/pdf");
        response.addHeader("Content-disposition", "inline; filename=" + "pdfMultipleGenerado" + ".pdf");
        ServletOutputStream servletStream = response.getOutputStream();
        try {
            com.lowagie.text.Document document = new com.lowagie.text.Document();
            PdfWriter writer = PdfWriter.getInstance(document, servletStream);
            document.open();
            PdfContentByte cb = writer.getDirectContent();

            for (InputStream in : list) {
                PdfReader reader = new PdfReader(in);
                for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                    document.newPage();
                    PdfImportedPage page = writer.getImportedPage(reader, i);
                    cb.addTemplate(page, 0, 0);
                }
            }
            document.close();
            servletStream.flush();
            servletStream.close();
        } catch (Exception e) {
            throw e;
        }
        return null;
    }

    @Override
    public File respondeServidorPDF(String reporte) throws Exception {
        try {
            File file = File.createTempFile("temp", ".pdf");
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(reporte));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
            exporter.exportReport();
            return file;
        } catch (Exception e) {
            throw e;
        }
    }

}
