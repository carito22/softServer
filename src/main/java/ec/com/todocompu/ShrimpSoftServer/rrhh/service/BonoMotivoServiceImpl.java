package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.BonoMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhBonoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class BonoMotivoServiceImpl implements BonoMotivoService {

    @Autowired
    private BonoMotivoDao bonoMotivoDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public RhBonoMotivo getRhBonoMotivo(String empresa, String codigo) throws Exception {
        return bonoMotivoDao.getRhBonoMotivo(empresa, codigo);
    }

    @Override
    public MensajeTO insertarRhBonoMotivo(RhBonoMotivo rhBonoMotivo, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        String retorno = "";
        rhBonoMotivo.getRhBonoMotivoPK()
                .setMotDetalle(rhBonoMotivo.getRhBonoMotivoPK().getMotDetalle().toUpperCase());

        if (bonoMotivoDao.obtenerPorId(RhBonoMotivo.class, rhBonoMotivo.getRhBonoMotivoPK()) != null) {
            retorno = "FEl motivo del bono que va a ingresar ya existe. Intente con otro.";
        } else {
            sisInfoTO.setEmpresa(rhBonoMotivo.getRhBonoMotivoPK().getMotEmpresa());

            susDetalle = "Se insertó el motivo del bono: " + rhBonoMotivo.getRhBonoMotivoPK().getMotDetalle();
            susClave = rhBonoMotivo.getRhBonoMotivoPK().getMotDetalle();
            susTabla = "recursoshumanos.rh_bono_motivo";
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            rhBonoMotivo.setUsrEmpresa(sisInfoTO.getEmpresa());
            rhBonoMotivo.setUsrCodigo(sisInfoTO.getUsuario());
            rhBonoMotivo.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));

            if (bonoMotivoDao.insertarRhBonoMotivo(rhBonoMotivo, sisSuceso)) {
                retorno = "TEl motivo del bono se guardo correctamente.";
                mensajeTO.getMap().put("motivo", rhBonoMotivo);
            } else {
                retorno = "FHubo un error al guardar el motivo del bono. Intente de nuevo o contacte con el administrador";
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public String insertarRhhBonoMotivo(RhBonoMotivo rhBonoMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        rhBonoMotivo.getRhBonoMotivoPK()
                .setMotDetalle(rhBonoMotivo.getRhBonoMotivoPK().getMotDetalle().toUpperCase());

        if (bonoMotivoDao.obtenerPorId(RhBonoMotivo.class, rhBonoMotivo.getRhBonoMotivoPK()) != null) {
            retorno = "FEl motivo del bono que va a ingresar ya existe. Intente con otro.";
        } else {
            sisInfoTO.setEmpresa(rhBonoMotivo.getRhBonoMotivoPK().getMotEmpresa());

            susDetalle = "Se insertó el motivo del bono: " + rhBonoMotivo.getRhBonoMotivoPK().getMotDetalle();
            susClave = rhBonoMotivo.getRhBonoMotivoPK().getMotDetalle();
            susTabla = "recursoshumanos.rh_bono_motivo";
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            rhBonoMotivo.setUsrEmpresa(sisInfoTO.getEmpresa());
            rhBonoMotivo.setUsrCodigo(sisInfoTO.getUsuario());
            rhBonoMotivo.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));

            if (bonoMotivoDao.insertarRhBonoMotivo(rhBonoMotivo, sisSuceso)) {
                retorno = "TEl motivo del bono se guardo correctamente.";
            } else {
                retorno = "FHubo un error al guardar el motivo del bono. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public String modificarRhBonoMotivo(RhBonoMotivo rhBonoMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(rhBonoMotivo.getRhBonoMotivoPK().getMotEmpresa());

        susDetalle = "Se modificó el motivo del bono: " + rhBonoMotivo.getRhBonoMotivoPK().getMotDetalle();
        susClave = rhBonoMotivo.getRhBonoMotivoPK().getMotDetalle();
        susSuceso = "UPDATE";
        susTabla = "recursoshumanos.rh_bono_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        if (bonoMotivoDao.modificarRhBonoMotivo(rhBonoMotivo, sisSuceso)) {
            retorno = "TEl motivo del bono se modificó correctamente.";
        } else {
            retorno = "Hubo un error al modificar el motivo del bono. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String modificarEstadoRhBonoMotivo(RhBonoMotivo rhBonoMotivo, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(rhBonoMotivo.getRhBonoMotivoPK().getMotEmpresa());

        susDetalle = "Se modificó el motivo del bono: " + rhBonoMotivo.getRhBonoMotivoPK().getMotDetalle();
        susClave = rhBonoMotivo.getRhBonoMotivoPK().getMotDetalle();
        susSuceso = "UPDATE";
        susTabla = "recursoshumanos.rh_bono_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        rhBonoMotivo.setMotInactivo(estado);
        if (bonoMotivoDao.modificarRhBonoMotivo(rhBonoMotivo, sisSuceso)) {
            retorno = "TEl motivo del bono se modificó correctamente.";
        } else {
            retorno = "Hubo un error al modificar el motivo del bono. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String eliminarRhBonoMotivo(RhBonoMotivo rhBonoMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        if (!bonoMotivoDao.banderaEliminarBonoMotivo(rhBonoMotivo.getRhBonoMotivoPK().getMotEmpresa(),
                rhBonoMotivo.getRhBonoMotivoPK().getMotDetalle())) {
            retorno = "FNo se puede eliminar el motivo del bono porque presenta movimientos";
        } else {
            sisInfoTO.setEmpresa(rhBonoMotivo.getRhBonoMotivoPK().getMotEmpresa());

            susDetalle = "Se eliminó el motivo del bono: " + rhBonoMotivo.getRhBonoMotivoPK().getMotDetalle();
            susClave = rhBonoMotivo.getRhBonoMotivoPK().getMotDetalle();
            susSuceso = "DELETE";
            susTabla = "recursoshumanos.rh_bono_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (bonoMotivoDao.eliminarRhBonoMotivo(rhBonoMotivo, sisSuceso)) {
                retorno = "TEl motivo del bono se eliminó correctamente...";
            } else {
                retorno = "FHubo un error al eliminar el motivo del bono. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public List<RhBonoMotivo> getListaRhBonoMotivo(String empresa) throws Exception {
        return bonoMotivoDao.getListaRhBonoMotivo(empresa);
    }

    public List<RhBonoMotivo> getListaRhBonoMotivos(String empresa, boolean inactivo) throws Exception {
        return bonoMotivoDao.getListaRhBonoMotivos(empresa, inactivo);
    }

}
