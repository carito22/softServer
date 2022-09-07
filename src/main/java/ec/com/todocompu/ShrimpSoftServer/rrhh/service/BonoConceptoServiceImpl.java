package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.BonoConceptoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesRRHH;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhBonoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboBonoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaBonoConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhBonoConcepto;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class BonoConceptoServiceImpl implements BonoConceptoService {

    @Autowired
    private BonoConceptoDao bonoConceptoDao;

    private Boolean comprobar = false;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public boolean modificarRhBonoConcepto(RhBonoConceptoTO rhBonoConceptoTO, SisInfoTO sisInfoTO) throws Exception {
        rhBonoConceptoTO.setUsrFechaInsertaBonoConcepto(UtilsValidacion.fechaSistema());
        comprobar = false;
        RhBonoConcepto rhBonoConcepto = bonoConceptoDao.buscarBonoConcepto(rhBonoConceptoTO.getBcSecuencia());
        if (rhBonoConcepto != null) {
            susClave = rhBonoConceptoTO.getBcDetalle();
            susDetalle = "Se modificó el BonoConcepto con detalle " + rhBonoConceptoTO.getBcDetalle();
            susSuceso = "UPDATE";
            susTabla = "produccion.rh_BonoConcepto";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            rhBonoConceptoTO.setUsrInsertaBonoConcepto(rhBonoConcepto.getUsrCodigo());
            rhBonoConceptoTO.setUsrFechaInsertaBonoConcepto(
                    UtilsValidacion.fecha(rhBonoConcepto.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            rhBonoConcepto = ConversionesRRHH.convertirRhBonoConceptoTO_RhBonoConcepto(rhBonoConceptoTO);
            comprobar = bonoConceptoDao.modificarRhBonoConcepto(rhBonoConcepto, sisSuceso);
        }
        return comprobar;
    }
    
    @Override
    public boolean modificarEstadoRhBonoConcepto(RhBonoConceptoTO rhBonoConceptoTO, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        rhBonoConceptoTO.setUsrFechaInsertaBonoConcepto(UtilsValidacion.fechaSistema());
        comprobar = false;
        RhBonoConcepto rhBonoConcepto = bonoConceptoDao.buscarBonoConcepto(rhBonoConceptoTO.getBcSecuencia());
        if (rhBonoConcepto != null) {
            susClave = rhBonoConceptoTO.getBcDetalle();
            susDetalle = "Se modificó el BonoConcepto con detalle " + rhBonoConceptoTO.getBcDetalle();
            susSuceso = "UPDATE";
            susTabla = "produccion.rh_BonoConcepto";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            rhBonoConceptoTO.setUsrInsertaBonoConcepto(rhBonoConcepto.getUsrCodigo());
            rhBonoConceptoTO.setUsrFechaInsertaBonoConcepto(
                    UtilsValidacion.fecha(rhBonoConcepto.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            rhBonoConcepto = ConversionesRRHH.convertirRhBonoConceptoTO_RhBonoConcepto(rhBonoConceptoTO);
            rhBonoConcepto.setBcInactivo(estado);
            comprobar = bonoConceptoDao.modificarRhBonoConcepto(rhBonoConcepto, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public boolean eliminarRhBonoConcepto(RhBonoConceptoTO rhBonoConceptoTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        RhBonoConcepto rhBonoConcepto = bonoConceptoDao.buscarBonoConcepto(rhBonoConceptoTO.getBcSecuencia());
        if (bonoConceptoDao.eliminarRhBonoConcepto(rhBonoConceptoTO) == true) {
            susClave = rhBonoConceptoTO.getBcDetalle();
            susDetalle = "Se eliminó la BonoConcepto con detalle " + rhBonoConceptoTO.getBcDetalle();
            susSuceso = "DELETE";
            susTabla = "produccion.rh_BonoConcepto";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            rhBonoConcepto = ConversionesRRHH.convertirRhBonoConceptoTO_RhBonoConcepto(rhBonoConceptoTO);
            comprobar = bonoConceptoDao.eliminarRhBonoConcepto(rhBonoConcepto, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public boolean insertarRhBonoConcepto(RhBonoConceptoTO rhBonoConceptoTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        susClave = rhBonoConceptoTO.getBcDetalle();
        susDetalle = "Se insertó el BonoConcepto con el detalle " + rhBonoConceptoTO.getBcDetalle();
        susSuceso = "INSERT";
        susTabla = "produccion.rh_BonoConcepto";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        rhBonoConceptoTO.setUsrFechaInsertaBonoConcepto(UtilsValidacion.fechaSistema());
        RhBonoConcepto rhBonoConcepto = ConversionesRRHH.convertirRhBonoConceptoTO_RhBonoConcepto(rhBonoConceptoTO);
        comprobar = bonoConceptoDao.insertarRhBonoConcepto(rhBonoConcepto, sisSuceso);
        return comprobar;
    }

    @Override
    public List<RhListaBonoConceptoTO> getListaBonoConceptoTO(String empresa) throws Exception {
        return bonoConceptoDao.getListaBonoConcepto(empresa);
    }
    
    @Override
    public List<RhListaBonoConceptoTO> getListaBonoConceptosTO(String empresa, boolean inactivo) throws Exception {
        return bonoConceptoDao.getListaBonoConceptos(empresa, inactivo);
    }

    @Override
    public List<RhComboBonoConceptoTO> getComboBonoConceptoTO(String empresa) throws Exception {
        return bonoConceptoDao.getComboBonoConcepto(empresa);
    }

}
