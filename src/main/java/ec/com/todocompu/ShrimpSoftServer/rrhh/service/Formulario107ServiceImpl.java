package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.Formulario107Dao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesRRHH;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormulario107TO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunFormulario107TO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunFormulario107_2013TO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhFormulario107;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class Formulario107ServiceImpl implements Formulario107Service {

    @Autowired
    private Formulario107Dao formulario107Dao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public RhFormulario107TO getRhFormulario107ConsultaTO(String empresa, String desde, String hasta, String empleadoID)
            throws Exception {
        return formulario107Dao.getRhFormulario107ConsultaTO(empresa, desde, hasta, empleadoID);
    }

    @Override
    public RhFormulario107TO getRhFormulario107TO(String empresa, String anio, String empleadoID) throws Exception {
        return formulario107Dao.getRhFormulario107TO(empresa, anio, empleadoID);
    }

    @Override
    public String insertarRhFormulario107TO(RhFormulario107TO rhFormulario107TO, SisInfoTO sisInfoTO) throws Exception {
        String mensajeRetorno = "";
        RhFormulario107 rhFormulario107Consulta = formulario107Dao.getRhFormulario107(
                rhFormulario107TO.getF107Empresa(), rhFormulario107TO.getF107Anio(), rhFormulario107TO.getF107Id());
        if (rhFormulario107Consulta == null) {
            susClave = rhFormulario107TO.getF107Empresa() + " | " + rhFormulario107TO.getF107Anio() + " | "
                    + rhFormulario107TO.getF107Id();
            susDetalle = "Se insertó el Formulario107 " + rhFormulario107TO.getF107Empresa() + " | "
                    + rhFormulario107TO.getF107Anio() + " | " + rhFormulario107TO.getF107Id();
            susSuceso = "INSERT";
            susTabla = "rh_formulario_107";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            rhFormulario107TO.setUsrFechaInserta(UtilsValidacion.fechaSistema("yyyy-MM-dd"));
            RhFormulario107 rhFormulario107 = ConversionesRRHH
                    .convertirRhFormulario107TO_RhFormulario107(rhFormulario107TO);

            if (formulario107Dao.insertarRhFormulario107(rhFormulario107, sisSuceso)) {
                mensajeRetorno = "T<html>Se ha guardado la información</font>.</html>";
            } else {
                mensajeRetorno = "FHubo un error al guardar la información...\nIntente de nuevo o contacte con el administrador.";
            }
        } else {
            mensajeRetorno = "FLa información ya existe en la Base de Datos.\nIntente con otro.";
        }
        return mensajeRetorno;
    }

    @Override
    public List<RhFunFormulario107TO> getRhFunFormulario107(String empresa, String desde, String hasta,
            Character status) throws Exception {
        return formulario107Dao.getRhFunFormulario107(empresa, desde, hasta, status);
    }

    @Override
    public List<RhFunFormulario107_2013TO> getRhFunFormulario107_2013(String empresa, String desde, String hasta,
            Character status) throws Exception {
        return formulario107Dao.getRhFunFormulario107_2013(empresa, desde, hasta, status);
    }

    @Override
    public List<RhFunFormulario107_2013TO> getRhFunFormulario107(String empresa, String desde, String hasta, Character status, String sector) throws Exception {
        return formulario107Dao.getRhFunFormulario107(empresa, desde, hasta, status, sector);
    }

}
