package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.FirmaElectronicaDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util.AutoridadesCertificantes;
import ec.com.todocompu.ShrimpSoftServer.util.sri.comprobantes.util.X500NameGeneral;
import ec.com.todocompu.ShrimpSoftUtils.Desencriptar;
import ec.com.todocompu.ShrimpSoftUtils.UtilsArchivos;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisFirmaElectronica;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DEROctetString;

@Service
public class FirmaElectronicaServiceImpl implements FirmaElectronicaService {

    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private FirmaElectronicaDao firmaElectronicaDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<SisFirmaElectronica> listarSisFirmaElectronica(String empresa) throws Exception {
        return firmaElectronicaDao.listarFirmaElectronica(empresa);
    }

    @Override
    public List<SisFirmaElectronica> listarSisFirmaElectronicaNoVerificadas(String empresa) throws Exception {
        return firmaElectronicaDao.listarFirmaElectronicaNoVerificadas(empresa);
    }

    @Override
    public SisFirmaElectronica insertarSisFirmaElectronica(SisFirmaElectronica sisFirmaElectronica, SisInfoTO sisInfoTO) throws Exception {
        //prueba
        Desencriptar desencriptar1 = new Desencriptar();
        String passSignature = desencriptar1.desencriptarMod(sisFirmaElectronica.getFirmaClave());
        Date fechaCaducidad = verificarDatosCertificado(passSignature, sisInfoTO.getEmpresaRuc(), null, sisFirmaElectronica.getFirmaElectronicaArchivo());
        if (fechaCaducidad != null && !UtilsValidacion.isFechaSuperior(fechaCaducidad)) {
            throw new GeneralException("El certificado se encuentra caducado, la fecha de caducidad es la siguiente: " + fechaCaducidad);
        }

        if (firmaElectronicaDao.existeCertificado(sisInfoTO.getEmpresa(), null, sisFirmaElectronica.getFirmaElectronicaArchivo())) {
            throw new GeneralException("El certificado que desea guardar ya se subió anteriormente");
        }

        sisFirmaElectronica.setFirmaFechaCaducidad(fechaCaducidad);
        sisFirmaElectronica.setFirmaFechaAviso(fechaCaducidad);
        sisFirmaElectronica.setUsrCodigo(sisInfoTO.getUsuario());
        sisFirmaElectronica.setUsrEmpresa(sisInfoTO.getEmpresa());
        sisFirmaElectronica.setFirmaEmpresa(sisInfoTO.getEmpresa());
        sisFirmaElectronica.setUsrFechaInserta(new Date());
        sisFirmaElectronica.setFirmaVerificada(true);
        susClave = sisFirmaElectronica.getFirmaSecuencial() + "";
        susDetalle = "Se ingresó la firma electrónica: " + susClave;
        susSuceso = "INSERT";
        susTabla = "sistemaweb.sis_firma_electronica";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        sisFirmaElectronica = firmaElectronicaDao.insertarFirmaElectronica(sisFirmaElectronica, sisSuceso);
        return sisFirmaElectronica;
    }

    @Override
    public SisFirmaElectronica modificarSisFirmaElectronica(SisFirmaElectronica sisFirmaElectronica, SisInfoTO sisInfoTO) throws Exception {
        Desencriptar desencriptar1 = new Desencriptar();
        String passSignature = desencriptar1.desencriptarMod(sisFirmaElectronica.getFirmaClave());
        Date fechaCaducidad = verificarDatosCertificado(passSignature, sisInfoTO.getEmpresaRuc(), null, sisFirmaElectronica.getFirmaElectronicaArchivo());
        System.out.println("Fecha vencimiento");
        if (fechaCaducidad != null && !UtilsValidacion.isFechaSuperior(fechaCaducidad)) {
            throw new GeneralException("El certificado se encuentra caducado, la fecha de caducidad es la siguiente: " + fechaCaducidad);
        }
        if (firmaElectronicaDao.existeCertificado(sisFirmaElectronica.getFirmaEmpresa(), sisFirmaElectronica.getFirmaSecuencial(), sisFirmaElectronica.getFirmaElectronicaArchivo())) {
            throw new GeneralException("El certificado que desea guardar ya se subió anteriormente");
        }
        sisFirmaElectronica.setFirmaFechaCaducidad(fechaCaducidad);
        sisFirmaElectronica.setFirmaFechaAviso(fechaCaducidad);
        sisFirmaElectronica.setFirmaVerificada(true);
        susClave = sisFirmaElectronica.getFirmaSecuencial() + "";
        susDetalle = "Se modificó la configuración de scanner: " + susClave;
        susSuceso = "UPDATE";
        susTabla = "sistemaweb.sis_firma_electronica";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        sisFirmaElectronica = firmaElectronicaDao.modificarFirmaElectronica(sisFirmaElectronica, sisSuceso);
        return sisFirmaElectronica;
    }

    @Override
    public boolean eliminarSisFirmaElectronica(Integer sisFirmaElectronica, SisInfoTO sisInfoTO) throws Exception {
        susClave = sisFirmaElectronica + "";
        susDetalle = "Se eliminó la configuración de scanner: " + susClave;
        susSuceso = "DELETE";
        susTabla = "sistemaweb.sis_firma_electronica";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        return firmaElectronicaDao.eliminarFirmaElectronica(new SisFirmaElectronica(sisFirmaElectronica), sisSuceso);
    }

    @Override
    public Map<String, Object> getDatosFirmaElectronica(Integer secuencial, SisInfoTO sisInfoTO) throws Exception {
        Map<String, Object> campos = new HashMap<>();

        SisFirmaElectronica sisFirmaElectronica = firmaElectronicaDao.obtenerFirmaElectronica(secuencial);
        if (sisFirmaElectronica != null) {
            Desencriptar claveReal = new Desencriptar();
            String passSignature = claveReal.desencriptarMod(sisFirmaElectronica.getFirmaClave());
            campos.put("claveReal", passSignature);
        }
        campos.put("sisFirmaElectronica", sisFirmaElectronica);

        return campos;

    }

    @Override
    public Date verificarDatosCertificado(String passSignature, String ruc, Date fechaVencimiento, byte[] archivo) throws Exception {
        KeyStore ks = null;
        Date fechaCaducidad = null;
        try {
            if (archivo != null && !archivo.equals("")) {
                ks = KeyStore.getInstance("PKCS12");
                InputStream keyStream = new ByteArrayInputStream(archivo);
                ks.load(keyStream, passSignature.toCharArray());
                String rucCertificado = null;
                String alias = getAlias(ks);
                X509Certificate c = (X509Certificate) ks.getCertificate(alias);
                String oidAutoridad = obtenerOidAutoridad(c);
                if (oidAutoridad != null) {
                    rucCertificado = getExtensionIdentifier(c, oidAutoridad);
                } else {
                    throw new GeneralException("No se encuentra OID de la autoridad certificante");
                }
                fechaCaducidad = c.getNotAfter();
                if (fechaVencimiento != null && fechaVencimiento.after(fechaCaducidad)) {
                    throw new GeneralException("Fecha vencimiento es incorrecta. La fecha caducidad de certificado: " + fechaCaducidad);
                }
                if (rucCertificado != null && !rucCertificado.equals(ruc)) {
                    throw new GeneralException("El RUC del certificado (" + rucCertificado + ") no coincide con el RUC de la empresa (" + ruc + ")");
                }
            } else {
                throw new GeneralException("Debe ingresar un archivo valido .p12");
            }
        } catch (CertificateExpiredException ex) {
            ex.printStackTrace();
            throw new GeneralException(ex.getMessage());
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
            throw new GeneralException(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new GeneralException("La contraseña ingresada es incorrecta " + e.getMessage());
        }

        return fechaCaducidad;
    }

    @Override
    public boolean migracionCajaAFirmaElectronica(SisInfoTO sisInfoTO) throws Exception {
        List<SisFirmaElectronica> listaFirmaNoVerificadas = listarSisFirmaElectronicaNoVerificadas(sisInfoTO.getEmpresa());
        for (SisFirmaElectronica firma : listaFirmaNoVerificadas) {
            //sisInfoTO.setEmpresa(firma.getUsrEmpresa());
            sisInfoTO.setUsuario(firma.getUsrCodigo());

            String pathSignature = UtilsArchivos.getRutaFirmaDigital() + firma.getFirmaElectronicaNombre();
            File file = new File(pathSignature);
            byte[] bytes = FileUtils.readFileToByteArray(file);
            firma.setFirmaElectronicaArchivo(bytes);
            modificarSisFirmaElectronica(firma, sisInfoTO);
        }
        return true;
    }

    @Override
    public Map<String, Object> validacionCertificado(byte[] archivo, String clave, boolean contraseniaEncriptada) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        Desencriptar desencriptar1 = new Desencriptar();
        String passSignature = null;
        if (!contraseniaEncriptada) {
            passSignature = clave;
        } else {
            passSignature = desencriptar1.desencriptarMod(clave);
        }

        KeyStore ks = null;
        Date fechaCaducidad = null;
        Date fechaEmision = null;
        try {
            if (archivo != null && !archivo.equals("")) {
                ks = KeyStore.getInstance("PKCS12");
                InputStream keyStream = new ByteArrayInputStream(archivo);
                ks.load(keyStream, passSignature.toCharArray());

                String rucCertificado = null;
                String nombresSubject = null;
                X500NameGeneral x500emisor = null;
                X500NameGeneral x500receptor = null;

                String alias = getAlias(ks);
                X509Certificate c = (X509Certificate) ks.getCertificate(alias);
                String oidAutoridad = obtenerOidAutoridad(c);
                if (oidAutoridad != null) {
                    rucCertificado = getExtensionIdentifier(c, oidAutoridad);
                } else {
                    throw new GeneralException("No se encuentra OID de la autoridad certificante");
                }
                nombresSubject = c.getSubjectDN().getName();
                fechaCaducidad = c.getNotAfter();
                fechaEmision = c.getNotBefore();
                x500emisor = new X500NameGeneral(c.getIssuerDN().getName());
                x500receptor = new X500NameGeneral(c.getSubjectDN().getName());

                campos.put("x500emisor", x500emisor);
                campos.put("x500receptor", x500receptor);
                campos.put("nombresSubject", nombresSubject);
                campos.put("fechaCaducidad", fechaCaducidad);
                campos.put("fechaEmision", fechaEmision);
                campos.put("rucCertificado", rucCertificado);
                campos.put("caducado",
                        (sistemaWebServicio.getFechaActual() != null
                        && sistemaWebServicio.getFechaActual().after(fechaCaducidad)) ? "SI" : "NO");
            } else {
                throw new GeneralException("Debe ingresar un archivo valido .p12");
            }
        } catch (CertificateExpiredException ex) {
            ex.printStackTrace();
            throw new GeneralException(ex.getMessage());
        } catch (KeyStoreException e) {
            e.printStackTrace();
            throw new GeneralException(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new GeneralException(e.getMessage());
        } catch (CertificateException e) {
            e.printStackTrace();
            throw new GeneralException(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new GeneralException("La contraseña ingresada es incorrecta " + e.getMessage());
        }

        return campos;
    }

    //metodos 
    public String getExtensionIdentifier(X509Certificate cert, String oid) throws IOException {
        String id = null;
        DERObject derObject = null;
        byte[] extensionValue = cert.getExtensionValue(oid);

        if (extensionValue != null) {
            derObject = toDERObject(extensionValue);
            if ((derObject instanceof DEROctetString)) {
                DEROctetString derOctetString = (DEROctetString) derObject;
                derObject = toDERObject(derOctetString.getOctets());
            }
        }
        if (derObject != null) {
            id = derObject.toString();
        } else {
            id = null;
        }
        return id;
    }

    public static String obtenerOidAutoridad(X509Certificate certificado) {
        String oidRaiz = null;
        String name = certificado.getIssuerDN().getName();
        X500NameGeneral x500emisor = new X500NameGeneral(name);
        String nombreAutoridad = x500emisor.getCN();

        if (nombreAutoridad != null && nombreAutoridad.contains(AutoridadesCertificantes.BANCO_CENTRAL.getCn())) {
            oidRaiz = AutoridadesCertificantes.BANCO_CENTRAL.getOid();
        } else if (nombreAutoridad != null && nombreAutoridad.contains(AutoridadesCertificantes.ANF.getCn())) {
            oidRaiz = AutoridadesCertificantes.ANF.getOid();
        } else if (nombreAutoridad != null && nombreAutoridad.contains(AutoridadesCertificantes.SECURITY_DATA.getCn())) {
            oidRaiz = AutoridadesCertificantes.SECURITY_DATA.getOid();
        } else if (nombreAutoridad != null && nombreAutoridad.contains(AutoridadesCertificantes.ICERT_EC.getCn())) {
            oidRaiz = AutoridadesCertificantes.ICERT_EC.getOid();
        } else if (nombreAutoridad != null && nombreAutoridad.contains(AutoridadesCertificantes.UANATACA.getCn())) {
            oidRaiz = AutoridadesCertificantes.UANATACA.getOid();
        }

        if (oidRaiz != null) {
            oidRaiz = oidRaiz.concat(".3.11");
        }

        return oidRaiz;
    }

    private static String getAlias(KeyStore keyStore) {
        String alias = null;
        Enumeration nombres;
        try {
            nombres = keyStore.aliases();

            while (nombres.hasMoreElements()) {
                String tmpAlias = (String) nombres.nextElement();
                if (keyStore.isKeyEntry(tmpAlias)) {
                    alias = tmpAlias;
                }
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return alias;
    }

    public static DERObject toDERObject(byte[] data) throws IOException {
        ByteArrayInputStream inStream = new ByteArrayInputStream(data);
        ASN1InputStream derInputStream = new ASN1InputStream(inStream);
        return derInputStream.readObject();
    }

}
