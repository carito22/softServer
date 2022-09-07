package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.AnexoNumeracionDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.TipoComprobanteDao;
import ec.com.todocompu.ShrimpSoftServer.criteria.Criterio;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.SectorDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SisEmpresaNotificacionesDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.FirmaElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesAnexos;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.Desencriptar;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisFirmaElectronica;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.criterion.Restrictions;

@Service
public class AnexoNumeracionServiceImpl implements AnexoNumeracionService {

    @Autowired
    private AnexoNumeracionDao numeracionDao;
    @Autowired
    private GenericoDao<AnxNumeracion, Integer> numeracionCriteriaDao;
    @Autowired
    private TipoComprobanteDao tipoComprobanteDao;
    @Autowired
    private SisEmpresaNotificacionesDao sisEmpresaNotificacionesDao;
    @Autowired
    private SectorDao sectorDao;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private FirmaElectronicaService firmaElectronicaService;
    private String susClave;
    private String susDetalle;
    private String susSuceso;
    private String susTabla;

    @Override
    public List<AnxNumeracionTablaTO> getListaAnexoNumeracionTO(String empresa) throws Exception {
        return numeracionDao.getListaAnexoNumeracionTO(empresa);
    }

    @Override
    public List<AnxNumeracionTablaTO> getListaAnexoNumeracionTO(String empresa, String tipoDocumento) throws Exception {
        return numeracionDao.getListaAnexoNumeracionTO(empresa, tipoDocumento);
    }

    @Override
    public List<AnxNumeracionTablaTO> getListaAnexoNumeracionTO(String empresa, String tipoDocumento, String fecha) throws Exception {
        return numeracionDao.getListaAnexoNumeracionTO(empresa, tipoDocumento, fecha);
    }

    @Override
    public AnxNumeracionTO getAnexoNumeracionTO(Integer secuencia) throws Exception {
        return numeracionDao.getAnexoNumeracionTO(secuencia);
    }

    @Override
    public String insertarAnexoNumeracionTO(AnxNumeracionTO anxNumeracionTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        if (numeracionDao.obtenerPorId(AnxNumeracion.class, anxNumeracionTO.getNumSecuencial()) == null) {
            susClave = anxNumeracionTO.getNumComprobante() + " | " + anxNumeracionTO.getNumDesde() + " | " + anxNumeracionTO.getNumHasta();
            susDetalle = "Se insertó una numeración con código " + anxNumeracionTO.getNumComprobante() + " " + anxNumeracionTO.getNumDesde() + " " + anxNumeracionTO.getNumHasta();
            susSuceso = "INSERT";
            susTabla = "anexos.anx_numeracion";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            anxNumeracionTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            AnxNumeracion anxNumeracion = ConversionesAnexos.convertirAnxNumeracionTO_AnxAnxNumeracion(anxNumeracionTO);
            if (numeracionDao.insertarAnexoNumeracion(anxNumeracion, sisSuceso)) {
                retorno = "TLa numeración se guardo correctamente...";
            } else {
                retorno = "FHubo un error al guardar la numeración...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FLa numeración que va a ingresar ya existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String modificarAnexoNumeracionTO(AnxNumeracionTO anxNumeracionTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        AnxNumeracion anxNumeracionAux = numeracionDao.obtenerPorId(AnxNumeracion.class, anxNumeracionTO.getNumSecuencial());
        if (anxNumeracionAux != null) {
            susClave = anxNumeracionTO.getNumComprobante() + " | " + anxNumeracionTO.getNumDesde() + " | " + anxNumeracionTO.getNumHasta();
            susDetalle = "Se modificó una numeración con código " + anxNumeracionTO.getNumComprobante() + " " + anxNumeracionTO.getNumDesde() + " " + anxNumeracionTO.getNumHasta();
            susSuceso = "UPDATE";
            susTabla = "anexos.anx_numeracion";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            anxNumeracionTO.setUsrCodigo(anxNumeracionAux.getUsrCodigo());
            anxNumeracionTO.setUsrFechaInserta(UtilsValidacion.fecha(anxNumeracionAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            AnxNumeracion anxNumeracion = ConversionesAnexos.convertirAnxNumeracionTO_AnxAnxNumeracion(anxNumeracionTO);

            if (numeracionDao.modificarAnexoNumeracion(anxNumeracion, sisSuceso)) {
                retorno = "TLa numeración se modificó correctamente...";
            } else {
                retorno = "FHubo un error al modificar la numeración...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FLa numeración que va a modificar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String eliminarAnexoNumeracionTO(AnxNumeracionTO anxNumeracionTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        AnxNumeracion anxNumeracionAux = numeracionDao.obtenerPorId(AnxNumeracion.class, anxNumeracionTO.getNumSecuencial());
        if (anxNumeracionAux != null) {
            susClave = anxNumeracionTO.getNumComprobante() + " | " + anxNumeracionTO.getNumDesde() + " | " + anxNumeracionTO.getNumHasta();
            susDetalle = "Se eliminó una numeración con código " + anxNumeracionTO.getNumComprobante() + " " + anxNumeracionTO.getNumDesde() + " " + anxNumeracionTO.getNumHasta();
            susSuceso = "DELETE";
            susTabla = "anexos.anx_numeracion";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            anxNumeracionTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            AnxNumeracion anxNumeracion = ConversionesAnexos.convertirAnxNumeracionTO_AnxAnxNumeracion(anxNumeracionTO);
            if (numeracionDao.eliminarAnexoNumeracion(anxNumeracion, sisSuceso)) {
                retorno = "TLa numeración se eliminó correctamente...";
            } else {
                retorno = "FHubo un error al eliminar la numeración...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FLa numeración que va a modificar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public Map<String, Object> obtenerDatosParaAnexoNumeracionTO(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        Integer secuencia = UtilsJSON.jsonToObjeto(Integer.class, map.get("secuencia"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));

        List<SisFirmaElectronica> listaFirma = firmaElectronicaService.listarSisFirmaElectronica(empresa);
        if (listaFirma != null && listaFirma.size() > 0) {
            SisFirmaElectronica firma = listaFirma.get(0);
            //
            KeyStore ks = null;
            Date fechaCaducidad = null;
            Desencriptar desencriptar1 = new Desencriptar();
            String passSignature = desencriptar1.desencriptarMod(firma.getFirmaClave());
            byte[] archivo = firma.getFirmaElectronicaArchivo();
            if (archivo != null && !archivo.equals("")) {
                ks = KeyStore.getInstance("PKCS12");
                InputStream keyStream = new ByteArrayInputStream(archivo);
                ks.load(keyStream, passSignature.toCharArray());
                String alias = getAlias(ks);
                X509Certificate c = (X509Certificate) ks.getCertificate(alias);
                if (c != null) {
                    fechaCaducidad = c.getNotAfter();
                    campos.put("fechaCaducidad", fechaCaducidad);
                }
            }
        }

        if (secuencia != null) {
            AnxNumeracionTO anexoNumeracionTO = numeracionDao.getAnexoNumeracionTO(secuencia);
            campos.put("anexoNumeracionTO", anexoNumeracionTO);
        }
        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        List<AnxTipoComprobanteComboTO> listaTipoComprobante = tipoComprobanteDao.getListaAnxTipoComprobanteComboTOCompleto();
        List<SisEmpresaNotificaciones> listaConfiguracion = sisEmpresaNotificacionesDao.listarSisEmpresaNotificaciones(empresa);
        List<PrdListaSectorTO> listaSectores = sectorDao.getListaSector(empresa, true);
        campos.put("fechaActual", fechaActual);
        campos.put("listaTipoComprobante", listaTipoComprobante);
        campos.put("listaConfiguracion", listaConfiguracion);
        campos.put("listaSectores", listaSectores);

        return campos;
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

    @Override
    public AnxNumeracion obtenerAnexoNumeracion(AnxNumeracionTO anxNumeracionTO, String empresa) throws Exception {
        Criterio filtro;
        filtro = Criterio.forClass(AnxNumeracion.class);
        filtro.add(Restrictions.eq("numEmpresa", anxNumeracionTO.getNumEmpresa()));
        filtro.add(Restrictions.eq("numComprobante", anxNumeracionTO.getNumComprobante()));
        filtro.add(Restrictions.eq("numDesde", anxNumeracionTO.getNumDesde()));
        filtro.add(Restrictions.eq("numHasta", anxNumeracionTO.getNumHasta()));
        return numeracionCriteriaDao.obtenerPorCriteriaSinProyeccionesDistinct(filtro);
    }
}
