package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.GrameajeDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PiscinaDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesProduccion;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.ListadoGrameaje;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdGrameajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListadoFitoplanctonTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListadoGrameajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdGrameaje;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdGrameajePK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscina;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.HashMap;
import java.util.Map;

@Service
public class GrameajeServiceImpl implements GrameajeService {

    @Autowired
    private PiscinaDao piscinaDao;

    @Autowired
    private GrameajeDao grameajeDao;
    @Autowired
    private FitoplanctonService fitoplanctonService;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    private Boolean comprobar = false;

    @Override
    public PrdGrameajeTO getPrdGrameajeTO(String empresa, String sector, String piscina, String fecha)
            throws Exception {
        return grameajeDao.getPrdGrameajeTO(empresa, sector, piscina, fecha);
    }

    @Override
    public PrdGrameajeTO getPrdGrameajeTO(String empresa, String sector, String piscina, String desde, String hasta)
            throws Exception {
        return grameajeDao.getPrdGrameajeTO(empresa, sector, piscina, desde, hasta);
    }

    @Override
    public PrdGrameaje getPrdGrameaje(String empresa, String sector, String piscina) throws Exception {
        return grameajeDao.getPrdGrameaje(empresa, sector, piscina);
    }

    @Override
    public List<PrdListadoGrameajeTO> getPrdListadoGrameajeTO(String empresa, String sector, String piscina,
            String desde, String hasta) throws Exception {
        return grameajeDao.getPrdListadoGrameajeTO(empresa, sector, piscina, desde, hasta);
    }

    @Override
    public boolean getIsFechaGrameajeSuperior(String empresa, String sector, String numPiscina, String fechaDesde,
            String fechaHasta, String fecha) throws Exception {
        return grameajeDao.getIsFechaGrameajeSuperior(empresa, sector, numPiscina, fechaDesde, fechaHasta, fecha);
    }

    @Override
    public String obtenerFechaGrameajeSuperior(String empresa, String sector, String numPiscina) throws Exception {
        return grameajeDao.obtenerFechaGrameajeSuperior(empresa, sector, numPiscina);
    }

    @Override
    public boolean insertarPrdGrameaje(PrdGrameajeTO prdGrameajeTO, SisInfoTO sisInfoTO) throws Exception {
        PrdPiscina prdPiscina = null;
        comprobar = false;
        prdPiscina = piscinaDao.obtenerPorId(PrdPiscina.class, new PrdPiscinaPK(prdGrameajeTO.getPisEmpresa(),
                prdGrameajeTO.getPisSector(), prdGrameajeTO.getPisNumero()));
        susDetalle = "Se insertó grameaje de fecha " + prdGrameajeTO.getGraFecha() + ", de la piscina con codigo "
                + prdGrameajeTO.getGraPiscina() + ", del sector con codigo " + prdGrameajeTO.getGraSector();
        susSuceso = "INSERT";
        susTabla = "produccion.prd_grameaje";
        susClave = prdGrameajeTO.getGraSector() + " " + prdGrameajeTO.getGraPiscina() + " "
                + prdGrameajeTO.getGraFecha();
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        prdGrameajeTO.setUsrFechaInsertaGrameaje(UtilsValidacion.fechaSistema());
        PrdGrameaje prdGrameaje = ConversionesProduccion.convertirPrdGrameajeTO_PrdGrameaje(prdGrameajeTO);
        prdGrameaje.setPrdPiscina(prdPiscina);
        prdGrameaje.setGraAnulado(false);
        comprobar = grameajeDao.insertarPrdGrameaje(prdGrameaje, sisSuceso);
        return comprobar;
    }

    @Override
    public boolean insertarGrameajeListado(List<PrdGrameaje> listaGrameaje, SisInfoTO sisInfoTO) throws Exception {
        susSuceso = "INSERT";
        susTabla = "produccion.prd_grameaje";
        for (PrdGrameaje grameaje : listaGrameaje) {
            susDetalle = "Se insertó grameaje de fecha "
                    + UtilsValidacion.fecha(grameaje.getPrdGrameajePK().getGraFecha(), "yyyy-MM-dd")
                    + ", de la piscina con codigo " + grameaje.getPrdPiscina().getPisNombre()
                    + ", del sector con codigo " + grameaje.getPrdGrameajePK().getGraSector();
            susClave = grameaje.getPrdGrameajePK().getGraSector() + " "
                    + grameaje.getPrdGrameajePK().getGraPiscina() + " " + grameaje.getPrdGrameajePK().getGraFecha();
            sisInfoTO.setEmpresa(grameaje.getPrdGrameajePK().getGraEmpresa());
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprobar = grameajeDao.insertarPrdGrameaje(grameaje, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public String eliminarPrdGrameaje(PrdGrameajeTO prdGrameajeTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        String mensaje = "";
        PrdGrameaje prdGrameaje = null;
        ///// BUSCANDO existencia piscina
        PrdPiscina prdPiscina = piscinaDao.obtenerPorId(PrdPiscina.class, new PrdPiscinaPK(
                prdGrameajeTO.getGraEmpresa(), prdGrameajeTO.getGraSector(), prdGrameajeTO.getGraPiscina()));
        if (prdPiscina != null) {// revisar si estan llenos
            ///// CREANDO Suceso
            susClave = prdGrameajeTO.getGraSector() + "|" + prdGrameajeTO.getGraPiscina() + "|"
                    + prdGrameajeTO.getGraFecha();
            susDetalle = "Se eliminó grameaje del sector " + prdGrameajeTO.getGraSector() + ", de la piscina "
                    + prdGrameajeTO.getGraPiscina() + ", con fecha " + prdGrameajeTO.getGraFecha();
            susSuceso = "DELETE";
            susTabla = "produccion.prd_grameaje";
            ///// CREANDO PrdGrameaje
            prdGrameaje = ConversionesProduccion
                    .convertirPrdGrameaje_PrdGrameaje(grameajeDao.obtenerPorId(PrdGrameaje.class,
                            new PrdGrameajePK(prdGrameajeTO.getGraEmpresa(), prdGrameajeTO.getGraSector(),
                                    prdGrameajeTO.getGraPiscina(), UtilsDate
                                    .fechaFormatoDate(prdGrameajeTO.getGraFecha(), "dd-MM-yyyy"))));
            ////// BUSCANDO existencia PrdGrameaje
            if (prdGrameaje != null) {
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                prdGrameaje.setPrdPiscina(prdPiscina);
                comprobar = grameajeDao.eliminarPrdGrameaje(prdGrameaje, sisSuceso);
            } else {
                mensaje = "FNo se encuentra el grameaje " + "\ndel sector " + prdGrameajeTO.getGraSector()
                        + ", \nde la piscina " + prdGrameajeTO.getGraPiscina() + ", \ncon fecha "
                        + prdGrameajeTO.getGraFecha();
            }
        }
        if (comprobar) {
            mensaje = "TSe eliminó correctamente el grameaje " + "\ndel sector " + prdGrameajeTO.getGraSector()
                    + ", \nde la piscina " + prdGrameajeTO.getGraPiscina() + ", \ncon fecha "
                    + prdGrameajeTO.getGraFecha();
        }
        return mensaje;
    }

    @Override
    public String eliminarGrameaje(PrdGrameaje prdGrameaje, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        String mensaje = "";
        ///// CREANDO Suceso
        susClave = prdGrameaje.getPrdGrameajePK().getGraSector() + "|" + prdGrameaje.getPrdGrameajePK().getGraPiscina() + "|" + prdGrameaje.getPrdGrameajePK().getGraFecha();
        susDetalle = "El gramaje del sector " + prdGrameaje.getPrdGrameajePK().getGraSector()
                + ", de la piscina " + prdGrameaje.getPrdGrameajePK().getGraPiscina() + ", con fecha "
                + prdGrameaje.getPrdGrameajePK().getGraFecha() + ", se ha eliminado correctamente";
        susSuceso = "DELETE";
        susTabla = "produccion.prd_grameaje";
        ///// CREANDO PrdGrameaje
        sisInfoTO.setEmpresa(prdGrameaje.getPrdGrameajePK().getGraEmpresa());
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        comprobar = grameajeDao.eliminarPrdGrameaje(prdGrameaje, sisSuceso);
        if (comprobar) {
            mensaje = "TEl gramaje " + "\ndel sector "
                    + prdGrameaje.getPrdGrameajePK().getGraSector() + ", \nde la piscina "
                    + prdGrameaje.getPrdPiscina().getPisNombre() + ", \ncon fecha "
                    + UtilsDate.fechaFormatoString(prdGrameaje.getPrdGrameajePK().getGraFecha(), "yyyy-MM-dd") + ", se ha eliminado correctamente";
        }
        return mensaje;
    }

    @Override
    public List<PrdGrameaje> getPrdListadoGrameaje(String empresa, String sector, String fecha) throws Exception {
        return grameajeDao.getPrdListadoGrameaje(empresa, sector, fecha);
    }

    @Override
    public List<ListadoGrameaje> getListaGrameajesPorFechasCorrida(String empresa, String sector, String piscina,
            String desde, String hasta) throws Exception {
        return grameajeDao.obtenerGrameajePorFechasCorrida(empresa, sector, piscina, desde, hasta);
    }

    @Override
    public Map<String, Object> obtenerGramajesYFitoplanctonListado(String empresa, String sector, String piscina, String desde, String hasta) throws Exception {

        Map<String, Object> campos = new HashMap<>();
        List<PrdListadoGrameajeTO> prdListadoGrameajeTO = getPrdListadoGrameajeTO(empresa, sector, piscina, desde, hasta);
        List<PrdListadoFitoplanctonTO> prdListadoFitoplanctonTO = fitoplanctonService.getListadoPrdFitoplanctonTO(empresa, sector, piscina, desde, hasta);

        campos.put("prdListadoGrameajeTO", prdListadoGrameajeTO);
        campos.put("prdListadoFitoplanctonTO", prdListadoFitoplanctonTO);
        return campos;

    }

}
