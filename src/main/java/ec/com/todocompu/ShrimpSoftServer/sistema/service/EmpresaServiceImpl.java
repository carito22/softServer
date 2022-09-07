package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import static ec.com.todocompu.ShrimpSoftUtils.UtilsArchivos.eliminarArchivo;
import static ec.com.todocompu.ShrimpSoftUtils.UtilsArchivos.getRutaImagen;
import static ec.com.todocompu.ShrimpSoftUtils.UtilsArchivos.guardarImagen;
import static ec.com.todocompu.ShrimpSoftUtils.UtilsArchivos.leerImagen;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesSistema;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.CedulaRuc;
import ec.com.todocompu.ShrimpSoftUtils.Encriptacion;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisEmpresaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresa;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.web.ComboGenericoTO;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    @Autowired
    private EmpresaDao empresaDao;
    @Autowired
    private SucesoDao sucesoDao;

    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;

    private Boolean comprobar = false;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public SisEmpresaTO obtenerSisEmpresaTOByRUC(String ruc) {
        return empresaDao.obtenerSisEmpresaTOByRUC(ruc);
    }

    @Override
    public String guardarImagenEmpresa(byte[] imagen, String nombre) throws Exception {
        return guardarImagen(getRutaImagen(), imagen, nombre, 0, 0);
    }

    @Override
    public String eliminarImagenEmpresa(String nombre) throws Exception {
        String mensaje = "";
        if (eliminarArchivo(getRutaImagen() + nombre + ".jpg")) {
            mensaje = "Imagen Eliminada con exito..";
        } else {
            mensaje = "No tiene una Imagen ah eliminar..";
        }
        return mensaje;
    }

    @Override
    public byte[] obtenerImagenEmpresa(String nombre) throws Exception {
        InputStream is = leerImagen(getRutaImagen() + nombre);
        byte[] bytes = null;
        if (is != null) {
            bytes = new byte[is.available()];
            is.read(bytes);
        }
        return bytes;
    }

    @Override
    public SisEmpresa obtenerPorId(String empCodigo) {
        return empresaDao.obtenerPorId(SisEmpresa.class, empCodigo);
    }

    @Override
    public boolean estadoLlevarContabilidad(String empresa) throws Exception {
        return empresaDao.estadoLlevarContabilidad(empresa);
    }

    @Override
    public boolean insertarSisEmpresa(SisEmpresaTO sisEmpresaTO, SisInfoTO sisInfoTO) throws Exception {
        sisEmpresaTO.setUsrFechaInsertaUsuario(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
        sisEmpresaTO.setUsrInsertaUsuario(sisInfoTO.getUsuario());
        comprobar = false;
        if (empresaDao.obtenerPorId(SisEmpresa.class, sisEmpresaTO.getEmpCodigo()) == null) {
            SisEmpresa sisEmpresa = ConversionesSistema.ConvertirSisEmpresaTO_SisEmpresa(sisEmpresaTO);

            // SUCESO
            susClave = sisEmpresaTO.getEmpCodigo();
            susTabla = "sistemaWeb.sis_empresa";
            susDetalle = "Se insertó la empresa " + sisEmpresaTO.getEmpCodigo();
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            SisEmpresaParametros sisEmpresaParametros = new SisEmpresaParametros();
            sisEmpresaParametros.setEmpCodigo(sisEmpresa);
            sisEmpresaParametros.setParEmpresa(sisEmpresaTO.getEmpCodigo());
            sisEmpresaParametros.setParGerente(sisEmpresaTO.getEmpGerente());
            sisEmpresaParametros.setParGerenteTipoId(sisEmpresaTO.getEmpTipoIdGerente());
            sisEmpresaParametros.setParGerenteId(sisEmpresaTO.getEmpIdGerente());
            sisEmpresaParametros.setParContador(sisEmpresaTO.getEmpContador());
            sisEmpresaParametros.setParContadorRuc(sisEmpresaTO.getEmpRucContador());
            sisEmpresaParametros.setParFinanciero(sisEmpresaTO.getParFinanciero());
            sisEmpresaParametros.setParFinancieroId(sisEmpresaTO.getParFinancieroId());
            sisEmpresaParametros.setParActividad(sisEmpresaTO.getParActividad());
            sisEmpresaParametros.setParEscogerPrecioPor(sisEmpresaTO.getParEscogerPrecioPor());
            // sisEmpresaParametros.setParObligadoLlevarContabilidad(true);
            sisEmpresaParametros.setParObligadoEmitirDocumentosElectronicos(sisEmpresaTO.getParObligadoEmitirDocumentosElectronicos());
            sisEmpresaParametros.setParObligadoRegistrarLiquidacionPesca(sisEmpresaTO.getParObligadoRegistrarLiquidacionPesca());
            sisEmpresaParametros.setParResolucionContribuyenteEspecial(sisEmpresaTO.getEmResolucionContribuyenteEspecial());
            sisEmpresaParametros.setParColumnasEstadosFinancieros(sisEmpresaTO.getParColumnasEstadosFinancieros());
            sisEmpresaParametros.setParCodigoDinardap(sisEmpresaTO.getParCodigoDinardap());
            sisEmpresaParametros.setParObligadoAprobarPagos(sisEmpresaTO.getParObligadoAprobarPagos());
            sisEmpresaParametros.setParContribuyenteRegimenMicroempresa(sisEmpresaTO.isParContribuyenteRegimenMicroempresa());
            sisEmpresaParametros.setParAgenteRetencion(sisEmpresaTO.getParAgenteRetencion());
            sisEmpresaParametros.setIsExportadora(sisEmpresaTO.getIsExportador());
            // (sisEmpresaParametros.setParWebDocumentosElectronicos(sisEmpresaTO.get);

            comprobar = empresaDao.insertarSisEmpresa(sisEmpresa, sisSuceso, sisEmpresaParametros);
        }
        return comprobar;
    }

    @Override
    public boolean insertarRegistrosComplementarios(String empCodigo) throws Exception {
        return empresaDao.insertarRegistrosComplementarios(empCodigo);
    }

    @Override
    public boolean modificarSisEmpresa(SisEmpresaTO sisEmpresaTO, SisInfoTO sisInfoTO) throws Exception {
        sisEmpresaTO.setUsrFechaInsertaUsuario(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
        comprobar = false;
        SisEmpresa empresaAux = empresaDao.obtenerPorId(SisEmpresa.class, sisEmpresaTO.getEmpCodigo().trim());
        if (empresaAux != null) {
            SisEmpresa sisEmpresa = ConversionesSistema.ConvertirSisEmpresaTO_SisEmpresa(sisEmpresaTO);
            susClave = sisEmpresaTO.getEmpCodigo();
            susTabla = "sistemaWeb.sis_empresa";
            susDetalle = "Se modifico datos de la empresa " + sisEmpresaTO.getEmpCodigo();
            susSuceso = "UPDATE";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao
                    .obtenerPorId(SisEmpresaParametros.class, sisEmpresaTO.getEmpCodigo());

            sisEmpresaParametros.setEmpCodigo(sisEmpresa);
            // sisEmpresaParametros.setParEmpresa(sisEmpresaTO.getEmpCodigo());
            sisEmpresaParametros.setParGerente(sisEmpresaTO.getEmpGerente());
            sisEmpresaParametros.setParGerenteTipoId(sisEmpresaTO.getEmpTipoIdGerente());
            sisEmpresaParametros.setParGerenteId(sisEmpresaTO.getEmpIdGerente());
            sisEmpresaParametros.setParContador(sisEmpresaTO.getEmpContador());
            sisEmpresaParametros.setParContadorRuc(sisEmpresaTO.getEmpRucContador());
            sisEmpresaParametros.setParFinanciero(sisEmpresaTO.getParFinanciero());
            sisEmpresaParametros.setParFinancieroId(sisEmpresaTO.getParFinancieroId());
            sisEmpresaParametros.setParActividad(sisEmpresaTO.getParActividad());
            sisEmpresaParametros.setParEscogerPrecioPor(sisEmpresaTO.getParEscogerPrecioPor());
            sisEmpresaParametros.setParObligadoLlevarContabilidad(sisEmpresaTO.getEmObligadoLlevarContabilidad());
            sisEmpresaParametros.setParObligadoEmitirDocumentosElectronicos(sisEmpresaTO.getParObligadoEmitirDocumentosElectronicos());
            sisEmpresaParametros.setParObligadoRegistrarLiquidacionPesca(sisEmpresaTO.getParObligadoRegistrarLiquidacionPesca());
            sisEmpresaParametros.setParResolucionContribuyenteEspecial(sisEmpresaTO.getEmResolucionContribuyenteEspecial());
            sisEmpresaParametros.setParColumnasEstadosFinancieros(sisEmpresaTO.getParColumnasEstadosFinancieros());
            sisEmpresaParametros.setParCodigoDinardap(sisEmpresaTO.getParCodigoDinardap());
            sisEmpresaParametros.setParObligadoAprobarPagos(sisEmpresaTO.getParObligadoAprobarPagos());
            sisEmpresaParametros.setParContribuyenteRegimenMicroempresa(sisEmpresaTO.isParContribuyenteRegimenMicroempresa());
            sisEmpresaParametros.setParAgenteRetencion(sisEmpresaTO.getParAgenteRetencion());
            sisEmpresaParametros.setIsExportadora(sisEmpresaTO.getIsExportador());
            // (sisEmpresaParametros.setPa

            comprobar = empresaDao.modificarSisEmpresa(sisEmpresa, sisSuceso, sisEmpresaParametros);
        }
        return comprobar;
    }

    @Override
    public SisEmpresa modificarSisEmpresa(SisEmpresa sisEmpresa, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        sisEmpresa.setUsrFechaInsertaEmpresa(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
        SisEmpresa empresaAux = empresaDao.obtenerPorId(SisEmpresa.class, sisEmpresa.getEmpCodigo().trim());
        if (empresaAux != null) {
            susClave = sisEmpresa.getEmpCodigo();
            susTabla = "sistemaWeb.sis_empresa";
            susDetalle = "Se modifico datos de la empresa " + sisEmpresa.getEmpCodigo();
            susSuceso = "UPDATE";
            if (sisEmpresa.getEmpClave() != null) {
                sisEmpresa.setEmpClave(empresaAux.getEmpClave().equalsIgnoreCase(sisEmpresa.getEmpClave()) ? sisEmpresa.getEmpClave() : this.encriptarClaveEmail(sisEmpresa.getEmpClave()));
            }
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            empresaDao.actualizar(sisEmpresa);
            sucesoDao.insertar(sisSuceso);
        } else {
            throw new GeneralException("El objeto a modificar no existe.");
        }
        return sisEmpresa;
    }

    private String encriptarClaveEmail(String claveEmail) {
        if (!claveEmail.isEmpty()) {
            Encriptacion encriptacion1 = new Encriptacion();
            java.math.BigInteger[] textoPass = encriptacion1.encripta(claveEmail);
            String clavePass = "";
            for (BigInteger textoPas : textoPass) {
                clavePass = clavePass + textoPas.toString(16).toUpperCase() + "G";
            }
            String n = encriptacion1.damen().toString(16);
            String d = encriptacion1.damed().toString(16);
            clavePass = n.length() + encriptacion1.damen().toString(16).toUpperCase() + clavePass
                    + encriptacion1.damed().toString(16).toUpperCase() + d.length();
            return clavePass;
        }
        return null;
    }

    @Override
    public List<SisEmpresaTO> getListaSisEmpresaTO(String usrCodigo, String empresa) throws Exception {
        return empresaDao.getListaSisEmpresa(usrCodigo, empresa);
    }

    @Override
    public List<SisEmpresaTO> getListaSisEmpresaTOWeb(String usrCodigo) throws Exception {
        return empresaDao.getListaSisEmpresaWeb(usrCodigo);
    }

    @Override
    public SisEmpresa obtenerEmpresa(String empresa) throws Exception {
        return empresaDao.obtenerPorId(SisEmpresa.class, empresa);
    }

    @Override
    public Map<String, Object> obtenerDatosParaEmpresa(String empresa) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        if (empresa != null && !empresa.equals("")) {
            SisEmpresaTO sisEmpresaTO = empresaDao.obtenerSisEmpresaTO(empresa);
            if (sisEmpresaTO != null) {
                byte[] imagen = obtenerImagenEmpresa(empresa + ".jpg");
                if (imagen != null) {
                    String foto = Base64.getEncoder().encodeToString(imagen);
                    campos.put("foto", foto);
                }
            }
            campos.put("sisEmpresaTO", sisEmpresaTO);
        }
        List<ComboGenericoTO> empresaActividad = new ArrayList<>();
        empresaActividad.add(new ComboGenericoTO("CAMARONERA", "CAMARONERA"));
        empresaActividad.add(new ComboGenericoTO("MINERA", "MINERA"));
        empresaActividad.add(new ComboGenericoTO("COMISARIATO", "COMISARIATO"));
        empresaActividad.add(new ComboGenericoTO("COMERCIAL", "COMERCIAL"));

        List<ComboGenericoTO> escogerPor = new ArrayList<>();
        escogerPor.add(new ComboGenericoTO("CANTIDAD", "CANTIDAD"));
        escogerPor.add(new ComboGenericoTO("PRECIO", "PRECIO"));
        escogerPor.add(new ComboGenericoTO("MIXTO", "MIXTO"));

        List<ComboGenericoTO> tiposIdentidad = new ArrayList<>();
        tiposIdentidad.add(new ComboGenericoTO("C", "CEDULA"));
        tiposIdentidad.add(new ComboGenericoTO("P", "PASAPORTE"));

        List<ComboGenericoTO> estadosFinancieros = new ArrayList<>();
        estadosFinancieros.add(new ComboGenericoTO("0", "FORMATO NEC"));
        estadosFinancieros.add(new ComboGenericoTO("1", "FORMATO NIIF"));

        campos.put("empresaActividad", empresaActividad);
        campos.put("escogerPor", escogerPor);
        campos.put("tiposIdentidad", tiposIdentidad);
        campos.put("estadosFinancieros", estadosFinancieros);

        return campos;
    }

    @Override
    public SisEmpresaTO modificarSisEmpresaTO(SisEmpresaTO sisEmpresaTO, byte[] decodedString, SisInfoTO sisInfoTO, boolean validarRucRepetido) throws Exception {
        if (validarFormularioAdministracionEmpresa(sisEmpresaTO, false, validarRucRepetido)) {
            if (modificarSisEmpresa(sisEmpresaTO, sisInfoTO)) {
                if (decodedString != null) {
                    guardarImagenEmpresa(decodedString, sisEmpresaTO.getEmpCodigo());
                } else {
                    eliminarImagenEmpresa(sisEmpresaTO.getEmpCodigo());
                }
            }
        }
        return sisEmpresaTO;
    }

    @Override
    public SisEmpresaTO insertarSisEmpresaTO(SisEmpresaTO sisEmpresaTO, byte[] decodedString, SisInfoTO sisInfoTO, boolean validarRucRepetido) throws Exception {
        if (validarFormularioAdministracionEmpresa(sisEmpresaTO, true, validarRucRepetido)) {
            if (insertarSisEmpresa(sisEmpresaTO, sisInfoTO) && decodedString != null) {
                guardarImagenEmpresa(decodedString, sisEmpresaTO.getEmpCodigo());
            }
        }
        return sisEmpresaTO;
    }

    public boolean validarFormularioAdministracionEmpresa(SisEmpresaTO sisEmpresaTO, boolean isNuevo, boolean validarRucRepetido) throws Exception {
        this.comprobarRUCEmpresa(sisEmpresaTO.getEmpRuc());
        this.comprobarIDGerente(sisEmpresaTO.getEmpIdGerente());
        this.comprobarRUCContador(sisEmpresaTO.getEmpRucContador());
        if (isNuevo && obtenerEmpresa(sisEmpresaTO.getEmpCodigo()) != null) {
            throw new GeneralException("La empresa que va a ingresar ya existe");
        } else if (!isNuevo && obtenerEmpresa(sisEmpresaTO.getEmpCodigo()) == null) {
            throw new GeneralException("La empresa que va a modificar ha sido eliminada. Intente con otra");
        }
        if (empresaDao.existeEmpresaConElMismoNombre(sisEmpresaTO.getEmpCodigo(), sisEmpresaTO.getEmpNombre())) {
            throw new GeneralException("El nombre de la empresa que va a ingresar ya existe, ingrese otro");
        }
        if (empresaDao.existeEmpresaConLaMismaRazonSocial(sisEmpresaTO.getEmpCodigo(), sisEmpresaTO.getEmpRazonSocial())) {
            throw new GeneralException("La razón social que va a ingresar ya existe, ingrese otra");
        }
        if (validarRucRepetido) {
            if (empresaDao.existeEmpresaConElMismoRuc(sisEmpresaTO.getEmpCodigo(), sisEmpresaTO.getEmpRuc())) {
                throw new GeneralException("El RUC de la empresa ya existe, ¿Desea ingresarlo de todas maneras?", new Throwable());
            }
        }
        return true;
    }

    private String comprobarRUCEmpresa(String empresaRuc) throws Exception {
        String comprobarRUC = CedulaRuc.comprobacion(empresaRuc.trim());
        if (!comprobarRUC.substring(0, 1).equals("T")) {
            throw new GeneralException("En el campo RUC: " + comprobarRUC.substring(1));
        }
        return comprobarRUC;
    }

    private String comprobarIDGerente(String idGerente) throws Exception {
        String comprobarIDGerente = CedulaRuc.comprobacion(idGerente.trim());
        if (!comprobarIDGerente.substring(0, 1).equals("T")) {
            throw new GeneralException("En el campo ID Gerente: " + comprobarIDGerente.substring(1));
        }
        return comprobarIDGerente;
    }

    private String comprobarRUCContador(String rucContador) throws Exception {
        String comprobarRUCContador = "";
        if (rucContador != null && !rucContador.trim().isEmpty()) {
            comprobarRUCContador = CedulaRuc.comprobacion(rucContador.trim());
        } else {
            comprobarRUCContador = "V";
        }
        if (!(comprobarRUCContador.substring(0, 1).equals("T")) && !(comprobarRUCContador.substring(0, 1).equals("V"))) {
            throw new GeneralException("En el campo RUC Contador: " + comprobarRUCContador.substring(1));
        }
        return comprobarRUCContador;
    }

}
