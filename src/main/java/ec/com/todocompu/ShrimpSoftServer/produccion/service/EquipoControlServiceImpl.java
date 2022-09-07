package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.EquipoControlDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdEquipoControl;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class EquipoControlServiceImpl implements EquipoControlService {

    @Autowired
    private EquipoControlDao equipoControlDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public String insertarEquipoControl(PrdEquipoControl prdEquipoControl, SisInfoTO sisInfoTO)
            throws Exception {
        String retorno = "";
        /// PREPARANDO OBJETO SISSUCESO
        susDetalle = "Se insertó el equipo de control con el codigo: " + prdEquipoControl.getPrdEquipoControlPK().getEcCodigo();
        susClave = prdEquipoControl.getPrdEquipoControlPK().getEcCodigo() + "-" + prdEquipoControl.getEcDescripcion();
        susSuceso = "INSERT";
        susTabla = "produccion.prd_equipo_control";
        prdEquipoControl.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        if (equipoControlDao.insertarEquipoControl(prdEquipoControl, sisSuceso)) {
            retorno = "TEl registro se guardo correctamente...";
        } else {
            retorno = "FHubo un error al guardar el registro...\nIntente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public List<PrdEquipoControl> listarEquiposControl(String empresa) throws Exception {
        return equipoControlDao.listarEquiposControl(empresa);
    }

    @Override
    public String actualizarEquipoControl(PrdEquipoControl prdEquipoControl, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        susDetalle = "Se modificó el registro con el codigo: " + prdEquipoControl.getPrdEquipoControlPK().getEcCodigo();
        susClave = prdEquipoControl.getPrdEquipoControlPK().getEcCodigo() + "-" + prdEquipoControl.getEcDescripcion();
        susSuceso = "UPDATE";
        susTabla = "produccion.prd_equipo_control";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        prdEquipoControl.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));
        if (equipoControlDao.actualizarEquipoControl(prdEquipoControl, sisSuceso)) {
            retorno = "TEl registro con el Código: " + prdEquipoControl.getPrdEquipoControlPK().getEcCodigo() + ", se modificó correctamente.";
        } else {
            retorno = "Hubo un error al modificar el registro. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

}
