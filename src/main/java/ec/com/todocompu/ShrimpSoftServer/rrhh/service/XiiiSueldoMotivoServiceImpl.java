package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.XiiiSueldoMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class XiiiSueldoMotivoServiceImpl implements XiiiSueldoMotivoService {

    @Autowired
    private XiiiSueldoMotivoDao xiiiMotivoDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public RhXiiiSueldoMotivo getRhXiiiSueldoMotivo(String empresa, String codigo) throws Exception {
        return xiiiMotivoDao.getRhXiiiSueldoMotivo(empresa, codigo);
    }

    @Override
    public MensajeTO insertarRhXiiiSueldoMotivo(RhXiiiSueldoMotivo rhXiiiSueldoMotivo, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        String retorno = "";
        rhXiiiSueldoMotivo.getRhXiiiSueldoMotivoPK()
                .setMotDetalle(rhXiiiSueldoMotivo.getRhXiiiSueldoMotivoPK().getMotDetalle().toUpperCase());

        if (xiiiMotivoDao.obtenerPorId(RhXiiiSueldoMotivo.class,
                rhXiiiSueldoMotivo.getRhXiiiSueldoMotivoPK()) != null) {
            retorno = "FEl motivo del xiii sueldo que va a ingresar ya existe. Intente con otro.";
        } else {
            sisInfoTO.setEmpresa(rhXiiiSueldoMotivo.getRhXiiiSueldoMotivoPK().getMotEmpresa());

            susDetalle = "Se insertó el motivo del xiii sueldo: "
                    + rhXiiiSueldoMotivo.getRhXiiiSueldoMotivoPK().getMotDetalle();
            susClave = rhXiiiSueldoMotivo.getRhXiiiSueldoMotivoPK().getMotDetalle();
            susTabla = "recursoshumanos.rh_xiii_sueldo_motivo";
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            rhXiiiSueldoMotivo.setUsrEmpresa(sisInfoTO.getEmpresa());
            rhXiiiSueldoMotivo.setUsrCodigo(sisInfoTO.getUsuario());
            rhXiiiSueldoMotivo.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));

            if (xiiiMotivoDao.insertarRhXiiiSueldoMotivo(rhXiiiSueldoMotivo, sisSuceso)) {
                retorno = "TEl motivo del xiii sueldo: " + rhXiiiSueldoMotivo.getRhXiiiSueldoMotivoPK().getMotEmpresa() + ", se ha guardardo correctamente.";
                mensajeTO.getMap().put("motivo", rhXiiiSueldoMotivo);
            } else {
                retorno = "FHubo un error al guardar el motivo del xiii sueldo. Intente de nuevo o contacte con el administrador";
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public String modificarRhXiiiSueldoMotivo(RhXiiiSueldoMotivo rhXiiiSueldoMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(rhXiiiSueldoMotivo.getRhXiiiSueldoMotivoPK().getMotEmpresa());

        susDetalle = "Se modificó el motivo del xiii sueldo: "
                + rhXiiiSueldoMotivo.getRhXiiiSueldoMotivoPK().getMotDetalle();
        susClave = rhXiiiSueldoMotivo.getRhXiiiSueldoMotivoPK().getMotDetalle();
        susSuceso = "UPDATE";
        susTabla = "recursoshumanos.rh_xiii_sueldo_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        if (xiiiMotivoDao.modificarRhXiiiSueldoMotivo(rhXiiiSueldoMotivo, sisSuceso)) {
            retorno = "TEl motivo del xiii sueldo: " + rhXiiiSueldoMotivo.getRhXiiiSueldoMotivoPK().getMotEmpresa() + ", se ha modificado correctamente.";
        } else {
            retorno = "Hubo un error al modificar el motivo del xiii sueldo. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String modificarEstadoRhXiiiSueldoMotivo(RhXiiiSueldoMotivo rhXiiiSueldoMotivo, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(rhXiiiSueldoMotivo.getRhXiiiSueldoMotivoPK().getMotEmpresa());

        susDetalle = "Se modificó el motivo del xiii sueldo: "
                + rhXiiiSueldoMotivo.getRhXiiiSueldoMotivoPK().getMotDetalle();
        susClave = rhXiiiSueldoMotivo.getRhXiiiSueldoMotivoPK().getMotDetalle();
        susSuceso = "UPDATE";
        susTabla = "recursoshumanos.rh_xiii_sueldo_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        rhXiiiSueldoMotivo.setMotInactivo(estado);
        if (xiiiMotivoDao.modificarRhXiiiSueldoMotivo(rhXiiiSueldoMotivo, sisSuceso)) {
            retorno = "TEl motivo del xiii sueldo: " + rhXiiiSueldoMotivo.getRhXiiiSueldoMotivoPK().getMotEmpresa() + ", se ha " + (estado ? "inactivado" : "activado") + " correctamente.";;
        } else {
            retorno = "Hubo un error al modificar el motivo del xiii sueldo. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String eliminarRhXiiiSueldoMotivo(RhXiiiSueldoMotivo rhXiiiSueldoMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        if (!xiiiMotivoDao.banderaEliminarXiiiMotivo(rhXiiiSueldoMotivo.getRhXiiiSueldoMotivoPK().getMotEmpresa(),
                rhXiiiSueldoMotivo.getRhXiiiSueldoMotivoPK().getMotDetalle())) {
            retorno = "FNo se puede eliminar el motivo del xiii sueldo porque presenta movimientos";
        } else {
            sisInfoTO.setEmpresa(rhXiiiSueldoMotivo.getRhXiiiSueldoMotivoPK().getMotEmpresa());

            susDetalle = "Se eliminó el motivo del xiii sueldo: "
                    + rhXiiiSueldoMotivo.getRhXiiiSueldoMotivoPK().getMotDetalle();
            susClave = rhXiiiSueldoMotivo.getRhXiiiSueldoMotivoPK().getMotDetalle();
            susSuceso = "DELETE";
            susTabla = "recursoshumanos.rh_xiii_sueldo_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (xiiiMotivoDao.eliminarRhXiiiSueldoMotivo(rhXiiiSueldoMotivo, sisSuceso)) {
                retorno = "TEl motivo del xiii sueldo: " + rhXiiiSueldoMotivo.getRhXiiiSueldoMotivoPK().getMotEmpresa() + ", se eliminado correctamente.";
            } else {
                retorno = "FHubo un error al eliminar el motivo del xiii sueldo. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public List<RhXiiiSueldoMotivo> getListaRhXiiiSueldoMotivo(String empresa) throws Exception {
        return xiiiMotivoDao.getListaRhXiiiSueldoMotivo(empresa);
    }

    @Override
    public List<RhXiiiSueldoMotivo> getListaRhXiiiSueldoMotivos(String empresa, boolean inactivo) throws Exception {
        return xiiiMotivoDao.getListaRhXiiiSueldoMotivos(empresa, inactivo);
    }

}
