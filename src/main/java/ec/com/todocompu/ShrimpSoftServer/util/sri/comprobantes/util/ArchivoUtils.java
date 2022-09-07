package ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util;

import com.thoughtworks.xstream.XStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import ec.com.todocompu.ShrimpSoftUtils.sri.util.XStreamUtil;
import ec.com.todocompu.ShrimpSoftUtils.sri.ws.autorizacion.Autorizacion;
import java.io.ByteArrayOutputStream;
import java.io.Writer;

public class ArchivoUtils {

    public static Object unmarshal(Class<?> clase, Node contenidoXml) throws Exception {
        return JAXBContext.newInstance(clase).createUnmarshaller()
                .unmarshal(new InputSource(new StringReader(contenidoXml.getTextContent())));
    }

    public static String obtenerDocumentoModificado(String codDoc) {
        if ("01".equals(codDoc)) {
            return "FACTURA";
        }
        if ("03".equals(codDoc)) {
            return "LIQUIDACIÓN DE COMPRA";
        }
        if ("04".equals(codDoc)) {
            return "NOTA DE CRÉDITO";
        }
        if ("05".equals(codDoc)) {
            return "NOTA DE DÉBITO";
        }
        if ("06".equals(codDoc)) {
            return "GUÍA REMISIÓN";
        }
        if ("07".equals(codDoc)) {
            return "COMPROBANTE DE RETENCIÓN";
        }
        return null;
    }

    public static DocumentoReporteRIDE documentoRIDE(Document doc) {
        NodeList list = doc.getElementsByTagName("*");
        String documento = "";
        String numeroAutorizacion = "";
        String fechaAutorizacion = "";
        String estado = "";
        for (int i = 0; i < list.getLength(); i++) {
            Element element = (Element) list.item(i);
            if (element.getNodeName().equals("comprobante")) {
                documento = element.getChildNodes().item(0).getNodeValue();
            } else if (element.getNodeName().equals("estado")) {
                estado = element.getChildNodes().item(0).getNodeValue();
            } else if (element.getNodeName().equals("numeroAutorizacion")) {
                numeroAutorizacion = element.getChildNodes().item(0).getNodeValue();
            } else if (element.getNodeName().equals("fechaAutorizacion")) {
                fechaAutorizacion = element.getChildNodes().item(0).getNodeValue();
            } else if (element.getNodeName().equals("claveAcceso")) {
            }
        }
        return new DocumentoReporteRIDE(numeroAutorizacion, fechaAutorizacion, estado, documento);
    }

    public static void crearXml(String nombreArchivo, String contenido) {
        String ruta = System.setProperty("java.io.tmpdir", "/comprobantes") + "/" + nombreArchivo.trim() + ".xml";
        File xmlFile = new File(ruta);
        BufferedWriter contenidoString;
        try {
            contenidoString = new BufferedWriter(new FileWriter(xmlFile));
            contenidoString.write(contenido);
            contenidoString.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String eliminaCaracteres(String s_cadena, String s_caracteres) {
        String nueva_cadena = "";
        Character caracter = null;
        boolean valido = true;
        /*
		 * Va recorriendo la cadena s_cadena y copia a la cadena que va a
		 * regresar, sólo los caracteres que no estén en la cadena s_caracteres
         */
        for (int i = 0; i < s_cadena.length(); i++) {
            valido = true;
            for (int j = 0; j < s_caracteres.length(); j++) {
                caracter = s_caracteres.charAt(j);
                if (s_cadena.charAt(i) == caracter) {
                    valido = false;
                    break;
                }
            }
            if (valido) {
                nueva_cadena += s_cadena.charAt(i);
            }
        }
        return nueva_cadena;
    }

    public static File stringToArchivo(String rutaArchivo, String contenidoArchivo) {
        FileOutputStream fos = null;
        File archivoCreado = null;
        int i = 0;
        try {
            fos = new FileOutputStream(rutaArchivo);
            OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
            for (i = 0; i < contenidoArchivo.length(); i++) {
                out.write(contenidoArchivo.charAt(i));
            }
            out.close();
            // archivoCreado = new File(rutaArchivo);
            archivoCreado = File.createTempFile(rutaArchivo, ".xml");
        } catch (Exception ex) {
            Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
            i = 0;
            return null;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception ex) {
                Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return archivoCreado;
    }

    public static File stringToArchivoEmail(String rutaArchivo, String contenidoArchivo) {
        FileOutputStream fos = null;
        File archivoCreado = null;
        int i = 0;
        try {
            archivoCreado = File.createTempFile(rutaArchivo, ".xml");
            fos = new FileOutputStream(archivoCreado.getPath());
            OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
            for (i = 0; i < contenidoArchivo.length(); i++) {
                out.write(contenidoArchivo.charAt(i));
            }
            out.close();
            // archivoCreado = new File(rutaArchivo);
        } catch (Exception ex) {
            Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
            i = 0;
            return null;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception ex) {
                Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return archivoCreado;
    }

    public static boolean copiarArchivo(File archivoOrigen, String pathDestino) {
        FileReader in = null;
        boolean resultado = false;
        try {
            File outputFile = new File(pathDestino);
            in = new FileReader(archivoOrigen);
            FileWriter out = new FileWriter(outputFile);
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
            in.close();
            out.close();
            resultado = true;
        } catch (IOException ex) {
            Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }

    private static Document merge(String exp, File[] files) throws Exception {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        XPathExpression expression = xpath.compile(exp);

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document base = docBuilder.parse(files[0]);

        Node results = (Node) expression.evaluate(base, XPathConstants.NODE);
        if (results == null) {
            throw new IOException(files[0] + ": expression does not evaluate to node");
        }

        for (int i = 1; i < files.length; i++) {
            Document merge = docBuilder.parse(files[i]);
            Node nextResults = (Node) expression.evaluate(merge, XPathConstants.NODE);
            results.appendChild(base.importNode(nextResults, true));
        }

        return base;
    }

    // import org.w3c.dom.Document;
    public static boolean adjuntarArchivo(File respuesta, File comprobante) {
        boolean exito = false;
        try {
            Document document = merge("*", new File[]{comprobante, respuesta});
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new OutputStreamWriter(new FileOutputStream(comprobante), "UTF-8"));
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.transform(source, result);
        } catch (Exception ex) {
            Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exito;
    }

    public static byte[] convertirXMLAutorizacionABytes(Autorizacion autorizacion) throws IOException {
        XStream xstream = XStreamUtil.getRespuestaXStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(outputStream, "UTF-8");
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xstream.toXML(autorizacion, writer);
        String xmlAutorizacion = outputStream.toString("UTF-8");
        return xmlAutorizacion.getBytes("UTF-8");
    }
}
