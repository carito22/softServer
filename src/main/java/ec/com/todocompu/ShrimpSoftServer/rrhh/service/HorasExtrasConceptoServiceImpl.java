package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.HorasExtrasConceptoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtrasConcepto;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.Date;

@Service
public class HorasExtrasConceptoServiceImpl implements HorasExtrasConceptoService {

    @Autowired
    private HorasExtrasConceptoDao horasExtrasConceptoDao;

    private Boolean comprobar = false;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public boolean modificarRhHorasExtrasConcepto(RhHorasExtrasConcepto rhHorasExtrasConceptoTO, SisInfoTO sisInfoTO) throws Exception {
        rhHorasExtrasConceptoTO.setUsrFechaInserta(new Date());
        comprobar = false;
        RhHorasExtrasConcepto rhHorasExtrasConcepto = horasExtrasConceptoDao.buscarHorasExtrasConcepto(rhHorasExtrasConceptoTO.getHecSecuencial());
        if (rhHorasExtrasConcepto != null) {
            susClave = rhHorasExtrasConceptoTO.getHecDetalle();
            susDetalle = "Se modificó el HorasExtrasConcepto con detalle " + rhHorasExtrasConceptoTO.getHecDetalle();
            susSuceso = "UPDATE";
            susTabla = "rrhh.rh_horas_extras_concepto";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            rhHorasExtrasConceptoTO.setUsrEmpresa(rhHorasExtrasConcepto.getUsrEmpresa());
            rhHorasExtrasConceptoTO.setUsrFechaInserta(rhHorasExtrasConcepto.getUsrFechaInserta());
            rhHorasExtrasConcepto = rhHorasExtrasConceptoTO;
            comprobar = horasExtrasConceptoDao.modificarRhHorasExtrasConcepto(rhHorasExtrasConcepto, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public boolean modificarEstadoRhHorasExtrasConcepto(RhHorasExtrasConcepto rhHorasExtrasConceptoTO, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        rhHorasExtrasConceptoTO.setUsrFechaInserta(new Date());
        comprobar = false;
        RhHorasExtrasConcepto rhHorasExtrasConcepto = horasExtrasConceptoDao.buscarHorasExtrasConcepto(rhHorasExtrasConceptoTO.getHecSecuencial());
        if (rhHorasExtrasConcepto != null) {
            susClave = rhHorasExtrasConceptoTO.getHecDetalle();
            susDetalle = "Se modificó el HorasExtrasConcepto con detalle " + rhHorasExtrasConceptoTO.getHecDetalle();
            susSuceso = "UPDATE";
            susTabla = "rrhh.rh_horas_extras_concepto";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            rhHorasExtrasConceptoTO.setUsrEmpresa(rhHorasExtrasConcepto.getUsrEmpresa());
            rhHorasExtrasConceptoTO.setUsrFechaInserta(rhHorasExtrasConcepto.getUsrFechaInserta());
            rhHorasExtrasConcepto = rhHorasExtrasConceptoTO;
            rhHorasExtrasConcepto.setHecInactivo(estado);
            comprobar = horasExtrasConceptoDao.modificarRhHorasExtrasConcepto(rhHorasExtrasConcepto, sisSuceso);
        }
        return comprobar;
    }

    @Override
    public boolean eliminarRhHorasExtrasConcepto(RhHorasExtrasConcepto rhHorasExtrasConceptoTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        susClave = rhHorasExtrasConceptoTO.getHecDetalle();
        susDetalle = "Se eliminó la HorasExtrasConcepto con detalle " + rhHorasExtrasConceptoTO.getHecDetalle();
        susSuceso = "DELETE";
        susTabla = "rrhh.rh_horas_extras_concepto";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        comprobar = horasExtrasConceptoDao.eliminarRhHorasExtrasConcepto(rhHorasExtrasConceptoTO, sisSuceso);
        return comprobar;
    }

    @Override
    public boolean insertarRhHorasExtrasConcepto(RhHorasExtrasConcepto rhHorasExtrasConceptoTO, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        susClave = rhHorasExtrasConceptoTO.getHecDetalle();
        susDetalle = "Se insertó el HorasExtrasConcepto con el detalle " + rhHorasExtrasConceptoTO.getHecDetalle();
        susSuceso = "INSERT";
        susTabla = "rrhh.rh_horas_extras_concepto";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        rhHorasExtrasConceptoTO.setUsrFechaInserta(new Date());
        rhHorasExtrasConceptoTO.setUsrCodigo(sisInfoTO.getUsuario());
        comprobar = horasExtrasConceptoDao.insertarRhHorasExtrasConcepto(rhHorasExtrasConceptoTO, sisSuceso);
        return comprobar;
    }

    @Override
    public List<RhHorasExtrasConcepto> getListaHorasExtrasConcepto(String empresa) throws Exception {
        return horasExtrasConceptoDao.getListaHorasExtrasConcepto(empresa);
    }

    @Override
    public List<RhHorasExtrasConcepto> getListaHorasExtrasConceptos(String empresa, boolean inactivo) throws Exception {
        return horasExtrasConceptoDao.getListaHorasExtrasConceptos(empresa, inactivo);
    }

}
