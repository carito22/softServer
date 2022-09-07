package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.PrestamoMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhPrestamoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class PrestamoMotivoServiceImpl implements PrestamoMotivoService {

    @Autowired
    private PrestamoMotivoDao prestamoMotivoDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public RhPrestamoMotivo getRhPrestamoMotivo(String empresa, String codigo) throws Exception {
        return prestamoMotivoDao.getRhPrestamoMotivo(empresa, codigo);
    }

    @Override
    public MensajeTO insertarRhPrestamoMotivo(RhPrestamoMotivo rhPrestamoMotivo, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        String retorno = "";
        rhPrestamoMotivo.getRhPrestamoMotivoPK()
                .setMotDetalle(rhPrestamoMotivo.getRhPrestamoMotivoPK().getMotDetalle().toUpperCase());

        if (prestamoMotivoDao.obtenerPorId(RhPrestamoMotivo.class,
                rhPrestamoMotivo.getRhPrestamoMotivoPK()) != null) {
            retorno = "FEl motivo del prestamo que va a ingresar ya existe. Intente con otro.";
        } else {
            sisInfoTO.setEmpresa(rhPrestamoMotivo.getRhPrestamoMotivoPK().getMotEmpresa());

            susDetalle = "Se insertó el motivo del prestamo: "
                    + rhPrestamoMotivo.getRhPrestamoMotivoPK().getMotDetalle();
            susClave = rhPrestamoMotivo.getRhPrestamoMotivoPK().getMotDetalle();
            susTabla = "recursoshumanos.rh_prestamo_motivo";
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            rhPrestamoMotivo.setUsrEmpresa(sisInfoTO.getEmpresa());
            rhPrestamoMotivo.setUsrCodigo(sisInfoTO.getUsuario());
            rhPrestamoMotivo.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));

            if (prestamoMotivoDao.insertarRhPrestamoMotivo(rhPrestamoMotivo, sisSuceso)) {
                retorno = "TEl motivo del prestamo: " + rhPrestamoMotivo.getRhPrestamoMotivoPK().getMotDetalle() + ", se ha guardado correctamente.";
                mensajeTO.getMap().put("motivo", rhPrestamoMotivo);
            } else {
                retorno = "FHubo un error al guardar el motivo del prestamo. Intente de nuevo o contacte con el administrador";
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public String modificarRhPrestamoMotivo(RhPrestamoMotivo rhPrestamoMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(rhPrestamoMotivo.getRhPrestamoMotivoPK().getMotEmpresa());

        susDetalle = "Se modificó el motivo del prestamo: "
                + rhPrestamoMotivo.getRhPrestamoMotivoPK().getMotDetalle();
        susClave = rhPrestamoMotivo.getRhPrestamoMotivoPK().getMotDetalle();
        susSuceso = "UPDATE";
        susTabla = "recursoshumanos.rh_prestamo_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        if (prestamoMotivoDao.modificarRhPrestamoMotivo(rhPrestamoMotivo, sisSuceso)) {
            retorno = "TEl motivo del prestamo: " + rhPrestamoMotivo.getRhPrestamoMotivoPK().getMotDetalle() + ", se ha modificado correctamente.";
        } else {
            retorno = "Hubo un error al modificar el motivo del prestamo. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String modificarEstadoRhPrestamoMotivo(RhPrestamoMotivo rhPrestamoMotivo, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(rhPrestamoMotivo.getRhPrestamoMotivoPK().getMotEmpresa());

        susDetalle = "Se modificó el motivo del prestamo: "
                + rhPrestamoMotivo.getRhPrestamoMotivoPK().getMotDetalle();
        susClave = rhPrestamoMotivo.getRhPrestamoMotivoPK().getMotDetalle();
        susSuceso = "UPDATE";
        susTabla = "recursoshumanos.rh_prestamo_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        rhPrestamoMotivo.setMotInactivo(estado);
        if (prestamoMotivoDao.modificarRhPrestamoMotivo(rhPrestamoMotivo, sisSuceso)) {
            retorno = "TEl motivo del prestamo: " + rhPrestamoMotivo.getRhPrestamoMotivoPK().getMotDetalle() + ", se ha " + (estado ? "inactivado" : "activado") + " correctamente.";
        } else {
            retorno = "Hubo un error al modificar el motivo del prestamo. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String eliminarRhPrestamoMotivo(RhPrestamoMotivo rhPrestamoMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        if (!prestamoMotivoDao.banderaEliminarPrestamoMotivo(
                rhPrestamoMotivo.getRhPrestamoMotivoPK().getMotEmpresa(),
                rhPrestamoMotivo.getRhPrestamoMotivoPK().getMotDetalle())) {
            retorno = "FNo se puede eliminar el motivo del prestamo porque presenta movimientos";
        } else {
            sisInfoTO.setEmpresa(rhPrestamoMotivo.getRhPrestamoMotivoPK().getMotEmpresa());

            susDetalle = "Se eliminó el motivo del prestamo: "
                    + rhPrestamoMotivo.getRhPrestamoMotivoPK().getMotDetalle();
            susClave = rhPrestamoMotivo.getRhPrestamoMotivoPK().getMotDetalle();
            susSuceso = "DELETE";
            susTabla = "recursoshumanos.rh_prestamo_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (prestamoMotivoDao.eliminarRhPrestamoMotivo(rhPrestamoMotivo, sisSuceso)) {
                retorno = "TEl motivo del prestamo: " + rhPrestamoMotivo.getRhPrestamoMotivoPK().getMotDetalle() + ", se ha eliminado correctamente.";
            } else {
                retorno = "FHubo un error al eliminar el motivo del prestamo. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public List<RhPrestamoMotivo> getListaRhPrestamoMotivo(String empresa) throws Exception {
        return prestamoMotivoDao.getListaRhPrestamoMotivo(empresa);
    }

    @Override
    public List<RhPrestamoMotivo> getListaRhPrestamoMotivos(String empresa, boolean inactivo) throws Exception {
        return prestamoMotivoDao.getListaRhPrestamoMotivos(empresa, inactivo);
    }

}
