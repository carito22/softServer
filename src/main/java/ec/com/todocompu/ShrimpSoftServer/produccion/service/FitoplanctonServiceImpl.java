package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.FitoplanctonDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PiscinaDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesProduccion;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFitoplanctonTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListadoFitoplanctonTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdFitoplancton;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdFitoplanctonPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscina;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author CarolValdiviezo
 */
@Service
public class FitoplanctonServiceImpl implements FitoplanctonService {

    @Autowired
    private FitoplanctonDao fitoplanctonDao;

    @Autowired
    private PiscinaDao piscinaDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public boolean insertarPrdFitoplancton(String codigoSector, String fecha, List<PrdFitoplanctonTO> listado, SisInfoTO sisInfoTO) throws Exception {
        boolean comprobar = false;
        PrdPiscina prdPiscina = null;
        susSuceso = "INSERT";
        susTabla = "produccion.prd_fitoplancton";
        for (PrdFitoplanctonTO item : listado) {
            prdPiscina = piscinaDao.obtenerPorId(PrdPiscina.class, new PrdPiscinaPK(sisInfoTO.getEmpresa(), codigoSector, item.getFitPiscinaCodigo()));
            susDetalle = "Se insertó filoplancton de fecha " + fecha + ", de la piscina con codigo " + item.getFitPiscinaCodigo() + ", del sector con codigo " + codigoSector;
            susSuceso = "INSERT";
            susTabla = "produccion.prd_fitoplancton";
            susClave = codigoSector + " " + item.getFitPiscinaCodigo() + " " + fecha;
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            PrdFitoplancton prdFitoplancton = ConversionesProduccion.convertirPrdFitoplanctonTO_PrdFitoplancton(item);
            prdFitoplancton.setPrdFitoplanctonPK(new PrdFitoplanctonPK(sisInfoTO.getEmpresa(), codigoSector, item.getFitPiscinaCodigo(), UtilsValidacion.fecha(fecha, "yyyy-MM-dd")));
            prdFitoplancton.setUsrEmpresa(sisInfoTO.getEmpresa());
            prdFitoplancton.setUsrCodigo(sisInfoTO.getUsuario());
            prdFitoplancton.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
            prdFitoplancton.setPrdPiscina(prdPiscina);
            comprobar = fitoplanctonDao.insertarPrdFitoplancton(prdFitoplancton, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public String eliminarPrdFitoplanctonTO(String codigoSector, String fecha, PrdFitoplanctonTO prdFitoplanctonTO, SisInfoTO sisInfoTO) throws Exception {
        boolean comprobar = false;
        String mensaje = "";
        PrdFitoplancton prdFitoplancton = null;
        ///// BUSCANDO existencia piscina
        PrdPiscina prdPiscina = piscinaDao.obtenerPorId(PrdPiscina.class, new PrdPiscinaPK(sisInfoTO.getEmpresa(), codigoSector, prdFitoplanctonTO.getFitPiscinaCodigo()));
        if (prdPiscina != null) {// revisar si estan llenos
            ///// CREANDO Suceso
            susClave = codigoSector + "|" + prdFitoplanctonTO.getFitPiscinaCodigo() + "|" + fecha;
            susDetalle = "Se eliminó grameaje del sector " + codigoSector + ", de la piscina " + prdFitoplanctonTO.getFitPiscinaCodigo() + ", con fecha " + fecha;
            susSuceso = "DELETE";
            susTabla = "produccion.prd_fitoplancton";
            ///// CREANDO prdFitoplancton
            prdFitoplancton = ConversionesProduccion.convertirPrdFitoplanctonTO_PrdFitoplancton(prdFitoplanctonTO);
            prdFitoplancton.setPrdFitoplanctonPK(new PrdFitoplanctonPK(sisInfoTO.getEmpresa(), codigoSector, prdFitoplanctonTO.getFitPiscinaCodigo(), UtilsValidacion.fecha(fecha, "yyyy-MM-dd")));
            prdFitoplancton.setUsrEmpresa(sisInfoTO.getEmpresa());
            prdFitoplancton.setUsrCodigo(sisInfoTO.getUsuario());
            prdFitoplancton.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
            prdFitoplancton.setPrdPiscina(prdPiscina);
            ////// BUSCANDO existencia PrdGrameaje
            if (prdFitoplancton != null) {
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                prdFitoplancton.setPrdPiscina(prdPiscina);
                comprobar = fitoplanctonDao.eliminarPrdFitoplancton(prdFitoplancton, sisSuceso);
            } else {
                mensaje = "FNo se encuentra el fitoplancton " + "\ndel sector " + codigoSector
                        + ", \nde la piscina " + prdFitoplanctonTO.getFitPiscinaNombre() + ", \ncon fecha "
                        + fecha;
            }
        }
        if (comprobar) {
            mensaje = "TSe eliminó correctamente el fitoplancton " + "\ndel sector " + codigoSector
                    + ", \nde la piscina " + prdFitoplanctonTO.getFitPiscinaNombre() + ", \ncon fecha "
                    + fecha;
        }
        return mensaje;

    }

    @Override
    public List<PrdFitoplanctonTO> getListPrdFitoplanctonTO(String empresa, String sector, String fecha) throws Exception {
        return fitoplanctonDao.getListPrdFitoplanctonTO(empresa, sector, fecha);
    }

    @Override
    public List<PrdListadoFitoplanctonTO> getListadoPrdFitoplanctonTO(String empresa, String sector, String piscina, String desde, String hasta) throws Exception {
        return fitoplanctonDao.getListadoPrdFitoplanctonTO(empresa, sector, piscina, desde, hasta);
    }

}
