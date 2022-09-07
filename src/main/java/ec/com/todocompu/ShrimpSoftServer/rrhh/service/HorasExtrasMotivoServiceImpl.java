package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.HorasExtrasMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtrasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class HorasExtrasMotivoServiceImpl implements HorasExtrasMotivoService {

    @Autowired
    private HorasExtrasMotivoDao horasExtrasMotivoDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public RhHorasExtrasMotivo getRhHorasExtrasMotivo(String empresa, String codigo) throws Exception {
        return horasExtrasMotivoDao.getRhHorasExtrasMotivo(empresa, codigo);
    }

    @Override
    public MensajeTO insertarRhHorasExtrasMotivo(RhHorasExtrasMotivo rhHorasExtrasMotivo, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        String retorno = "";
        rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK()
                .setMotDetalle(rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK().getMotDetalle().toUpperCase());

        if (horasExtrasMotivoDao.obtenerPorId(RhHorasExtrasMotivo.class, rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK()) != null) {
            retorno = "FEl motivo de horas extras que va a ingresar ya existe. Intente con otro.";
        } else {
            sisInfoTO.setEmpresa(rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK().getMotEmpresa());

            susDetalle = "Se insertó el motivo de horas extras: " + rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK().getMotDetalle();
            susClave = rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK().getMotDetalle();
            susTabla = "recursoshumanos.rh_horas_extras_motivo";
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            rhHorasExtrasMotivo.setUsrEmpresa(sisInfoTO.getEmpresa());
            rhHorasExtrasMotivo.setUsrCodigo(sisInfoTO.getUsuario());
            rhHorasExtrasMotivo.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));

            if (horasExtrasMotivoDao.insertarRhHorasExtrasMotivo(rhHorasExtrasMotivo, sisSuceso)) {
                retorno = "TEl motivo de horas extras se guardo correctamente.";
                mensajeTO.getMap().put("motivo", rhHorasExtrasMotivo);
            } else {
                retorno = "FHubo un error al guardar el motivo de horas extras. Intente de nuevo o contacte con el administrador";
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public String insertarRhhHorasExtrasMotivo(RhHorasExtrasMotivo rhHorasExtrasMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK()
                .setMotDetalle(rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK().getMotDetalle().toUpperCase());

        if (horasExtrasMotivoDao.obtenerPorId(RhHorasExtrasMotivo.class, rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK()) != null) {
            retorno = "FEl motivo de horas extras que va a ingresar ya existe. Intente con otro.";
        } else {
            sisInfoTO.setEmpresa(rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK().getMotEmpresa());

            susDetalle = "Se insertó el motivo de horas extras: " + rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK().getMotDetalle();
            susClave = rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK().getMotDetalle();
            susTabla = "recursoshumanos.rh_horas_extras_motivo";
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            rhHorasExtrasMotivo.setUsrEmpresa(sisInfoTO.getEmpresa());
            rhHorasExtrasMotivo.setUsrCodigo(sisInfoTO.getUsuario());
            rhHorasExtrasMotivo.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));

            if (horasExtrasMotivoDao.insertarRhHorasExtrasMotivo(rhHorasExtrasMotivo, sisSuceso)) {
                retorno = "TEl motivo de horas extras se guardo correctamente.";
            } else {
                retorno = "FHubo un error al guardar el motivo de horas extras. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public String modificarRhHorasExtrasMotivo(RhHorasExtrasMotivo rhHorasExtrasMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK().getMotEmpresa());

        susDetalle = "Se modificó el motivo de horas extras: " + rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK().getMotDetalle();
        susClave = rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK().getMotDetalle();
        susSuceso = "UPDATE";
        susTabla = "recursoshumanos.rh_horas_extras_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        if (horasExtrasMotivoDao.modificarRhHorasExtrasMotivo(rhHorasExtrasMotivo, sisSuceso)) {
            retorno = "TEl motivo de horas extras se modificó correctamente.";
        } else {
            retorno = "Hubo un error al modificar el motivo de horas extras. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String modificarEstadoRhHorasExtrasMotivo(RhHorasExtrasMotivo rhHorasExtrasMotivo, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK().getMotEmpresa());

        susDetalle = "Se modificó el motivo de horas extras: " + rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK().getMotDetalle();
        susClave = rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK().getMotDetalle();
        susSuceso = "UPDATE";
        susTabla = "recursoshumanos.rh_horas_extras_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        rhHorasExtrasMotivo.setMotInactivo(estado);
        if (horasExtrasMotivoDao.modificarRhHorasExtrasMotivo(rhHorasExtrasMotivo, sisSuceso)) {
            retorno = "TEl motivo de horas extras se modificó correctamente.";
        } else {
            retorno = "Hubo un error al modificar el motivo de horas extras. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String eliminarRhHorasExtrasMotivo(RhHorasExtrasMotivo rhHorasExtrasMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        if (!horasExtrasMotivoDao.banderaEliminarHorasExtrasMotivo(rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK().getMotEmpresa(),
                rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK().getMotDetalle())) {
            retorno = "FNo se puede eliminar el motivo de horas extras porque presenta movimientos";
        } else {
            sisInfoTO.setEmpresa(rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK().getMotEmpresa());

            susDetalle = "Se eliminó el motivo de horas extras: " + rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK().getMotDetalle();
            susClave = rhHorasExtrasMotivo.getRhHorasExtrasMotivoPK().getMotDetalle();
            susSuceso = "DELETE";
            susTabla = "recursoshumanos.rh_horas_extras_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (horasExtrasMotivoDao.eliminarRhHorasExtrasMotivo(rhHorasExtrasMotivo, sisSuceso)) {
                retorno = "TEl motivo de horas extras se eliminó correctamente...";
            } else {
                retorno = "FHubo un error al eliminar el motivo de horas extras. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public List<RhHorasExtrasMotivo> getListaRhHorasExtrasMotivo(String empresa) throws Exception {
        return horasExtrasMotivoDao.getListaRhHorasExtrasMotivo(empresa);
    }

    @Override
    public List<RhHorasExtrasMotivo> getListaRhHorasExtrasMotivos(String empresa, boolean inactivo) throws Exception {
        return horasExtrasMotivoDao.getListaRhHorasExtrasMotivos(empresa, inactivo);
    }

}
