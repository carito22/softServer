package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.AnticipoMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesRRHH;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhAnticipoMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class AnticipoMotivoServiceImpl implements AnticipoMotivoService {

    @Autowired
    private AnticipoMotivoDao anticipoMotivoDao;

    private Boolean comprobar = false;
    private String mensaje = "";
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<RhAnticipoMotivoTO> getListaRhAnticipoMotivoTablaTO(String empresa) throws Exception {
        return anticipoMotivoDao.getListaRhAnticipoMotivoTablaTO(empresa);
    }

    @Override
    public List<RhAnticipoMotivoTO> getListaRhAnticipoMotivoTOTablaTO(String empresa, String codigo) throws Exception {
        return anticipoMotivoDao.getListaRhAnticipoMotivoTOTablaTO(empresa, codigo);
    }

    @Override
    public RhAnticipoMotivoTO getRhAnticipoMotivoTO(String empresa, String detalle) throws Exception {
        return anticipoMotivoDao.getRhAnticipoMotivoTO(empresa, detalle);
    }

    @Override
    public RhAnticipoMotivo getRhAnticipoMotivo(String empresa, String codigo) throws Exception {
        return anticipoMotivoDao.getRhAnticipoMotivo(empresa, codigo);
    }

    @Override
    public String accionRhAnticipoMotivo(RhAnticipoMotivoTO rhAnticipoMotivoTO, char accion, SisInfoTO sisInfoTO)
            throws Exception {
        comprobar = false;
        ///// CREANDO Suceso
        susClave = rhAnticipoMotivoTO.getMotDetalle();
        if (accion == 'I') {
            susDetalle = "Se insertó el AnticipoMotivo con el detalle " + rhAnticipoMotivoTO.getMotDetalle();
            susSuceso = "INSERT";
        }
        if (accion == 'M') {
            susDetalle = "Se modificó el AnticipoMotivo con el detalle " + rhAnticipoMotivoTO.getMotDetalle();
            susSuceso = "UPDATE";
        }
        if (accion == 'E') {
            susDetalle = "Se eliminó el AnticipoMotivo con el detalle " + rhAnticipoMotivoTO.getMotDetalle();
            susSuceso = "DELETE";
        }
        susTabla = "recursoshumanos.rh_anticipo_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        ///// CREANDO RhAnticipoMotivo
        rhAnticipoMotivoTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
        RhAnticipoMotivo rhAnticipoMotivo = ConversionesRRHH
                .convertirRhAnticipoMotivoTO_RhAnticipoMotivo(rhAnticipoMotivoTO);
        if (accion == 'E') {
            ////// BUSCANDO existencia RhAnticipoMotivo
            if (anticipoMotivoDao.getRhAnticipoMotivo(rhAnticipoMotivoTO.getMotEmpresa(),
                    rhAnticipoMotivoTO.getMotDetalle()) != null) {
                if (anticipoMotivoDao.banderaEliminarAnticipoMotivo(rhAnticipoMotivoTO.getMotEmpresa(),
                        rhAnticipoMotivoTO.getMotDetalle())) {
                    rhAnticipoMotivo.setUsrFechaInserta(
                            UtilsDate.timestamp(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema())));
                    comprobar = anticipoMotivoDao.accionRhAnticipoMotivo(rhAnticipoMotivo, sisSuceso, accion);
                } else {
                    mensaje = "FNo se puede eliminar, el AnticipoMotivo, está asignado con algun empleado...";
                }
            } else {
                mensaje = "FNo se encuentra el AnticipoMotivo1...";
            }
        }
        if (accion == 'M') {
            ////// BUSCANDO existencia RhAnticipoMotivo
            if (anticipoMotivoDao.getRhAnticipoMotivo(rhAnticipoMotivoTO.getMotEmpresa(),
                    rhAnticipoMotivoTO.getMotDetalle()) != null) {
                rhAnticipoMotivo.setUsrFechaInserta(anticipoMotivoDao
                        .getRhAnticipoMotivo(rhAnticipoMotivoTO.getMotEmpresa(), rhAnticipoMotivoTO.getMotDetalle())
                        .getUsrFechaInserta());
                comprobar = anticipoMotivoDao.accionRhAnticipoMotivo(rhAnticipoMotivo, sisSuceso, accion);
            } else {
                mensaje = "FNo se encuentra el AnticipoMotivo2...";
            }
        }
        if (accion == 'I') {
            ////// BUSCANDO existencia RhAnticipoMotivo
            if (!anticipoMotivoDao.buscarRhAnticipoMotivo(rhAnticipoMotivoTO.getMotEmpresa(),
                    rhAnticipoMotivoTO.getMotDetalle())) {
                rhAnticipoMotivo.setUsrFechaInserta(
                        UtilsDate.timestamp(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema())));
                comprobar = anticipoMotivoDao.accionRhAnticipoMotivo(rhAnticipoMotivo, sisSuceso, accion);
            } else {
                mensaje = "FYa existe el AnticipoMotivo...";
            }
        }
        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TEl AnticipoMotivo " + rhAnticipoMotivoTO.getMotDetalle() + " se ha eliminado correctamente.";
            }
            if (accion == 'M') {
                mensaje = "TEl AnticipoMotivo " + rhAnticipoMotivoTO.getMotDetalle() + " se ha modificado correctamente.";
            }
            if (accion == 'I') {
                mensaje = "TEl AnticipoMotivo " + rhAnticipoMotivoTO.getMotDetalle() + " se ha guardado correctamente.";
            }
        }
        return mensaje;
    }

    @Override
    public MensajeTO insertarRhAnticipoMotivo(RhAnticipoMotivo rhAnticipoMotivo, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        String retorno = "";
        rhAnticipoMotivo.getRhAnticipoMotivoPK()
                .setMotDetalle(rhAnticipoMotivo.getRhAnticipoMotivoPK().getMotDetalle().toUpperCase());

        if (anticipoMotivoDao.obtenerPorId(RhAnticipoMotivo.class,
                rhAnticipoMotivo.getRhAnticipoMotivoPK()) != null) {
            retorno = "FEl motivo del anticipo que va a ingresar ya existe. Intente con otro.";
        } else {
            sisInfoTO.setEmpresa(rhAnticipoMotivo.getRhAnticipoMotivoPK().getMotEmpresa());

            susDetalle = "Se insertó el motivo del anticipo: "
                    + rhAnticipoMotivo.getRhAnticipoMotivoPK().getMotDetalle();
            susClave = rhAnticipoMotivo.getRhAnticipoMotivoPK().getMotDetalle();
            susTabla = "recursoshumanos.rh_anticipo_motivo";
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            rhAnticipoMotivo.setUsrEmpresa(sisInfoTO.getEmpresa());
            rhAnticipoMotivo.setUsrCodigo(sisInfoTO.getUsuario());
            rhAnticipoMotivo.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));

            if (anticipoMotivoDao.insertarRhAnticipoMotivo(rhAnticipoMotivo, sisSuceso)) {
                retorno = "TEl motivo del anticipo " + rhAnticipoMotivo.getRhAnticipoMotivoPK().getMotDetalle() + " se ha guardado correctamente.";
                mensajeTO.getMap().put("motivo", rhAnticipoMotivo);
            } else {
                retorno = "FHubo un error al guardar el motivo del anticipo. Intente de nuevo o contacte con el administrador";
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public String modificarRhAnticipoMotivo(RhAnticipoMotivo rhAnticipoMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(rhAnticipoMotivo.getRhAnticipoMotivoPK().getMotEmpresa());

        susDetalle = "Se modificó el motivo del anticipo: "
                + rhAnticipoMotivo.getRhAnticipoMotivoPK().getMotDetalle();
        susClave = rhAnticipoMotivo.getRhAnticipoMotivoPK().getMotDetalle();
        susSuceso = "UPDATE";
        susTabla = "recursoshumanos.rh_anticipo_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        if (anticipoMotivoDao.modificarRhAnticipoMotivo(rhAnticipoMotivo, sisSuceso)) {
            retorno = "TEl motivo del anticipo " + rhAnticipoMotivo.getRhAnticipoMotivoPK().getMotDetalle() + " se ha modificado correctamente.";
        } else {
            retorno = "FHubo un error al modificar el motivo del anticipo. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String eliminarRhAnticipoMotivo(RhAnticipoMotivo rhAnticipoMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        if (!anticipoMotivoDao.banderaEliminarAnticipoMotivo(
                rhAnticipoMotivo.getRhAnticipoMotivoPK().getMotEmpresa(),
                rhAnticipoMotivo.getRhAnticipoMotivoPK().getMotDetalle())) {
            retorno = "FNo se puede eliminar el motivo del anticipo porque presenta movimientos";
        } else {
            sisInfoTO.setEmpresa(rhAnticipoMotivo.getRhAnticipoMotivoPK().getMotEmpresa());

            susDetalle = "Se eliminó el motivo del anticipo: "
                    + rhAnticipoMotivo.getRhAnticipoMotivoPK().getMotDetalle();
            susClave = rhAnticipoMotivo.getRhAnticipoMotivoPK().getMotDetalle();
            susSuceso = "DELETE";
            susTabla = "recursoshumanos.rh_anticipo_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (anticipoMotivoDao.eliminarRhAnticipoMotivo(rhAnticipoMotivo, sisSuceso)) {
                retorno = "TEl motivo del anticipo " + rhAnticipoMotivo.getRhAnticipoMotivoPK().getMotDetalle() + " se ha eliminado correctamente.";
            } else {
                retorno = "FHubo un error al eliminar el motivo del anticipo. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public List<RhAnticipoMotivo> getListaRhAnticipoMotivo(String empresa) throws Exception {
        return anticipoMotivoDao.getListaRhAnticipoMotivo(empresa);
    }

}
