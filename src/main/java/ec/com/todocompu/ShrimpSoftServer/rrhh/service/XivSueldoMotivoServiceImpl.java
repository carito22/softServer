package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.XivSueldoMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class XivSueldoMotivoServiceImpl implements XivSueldoMotivoService {

    @Autowired
    private XivSueldoMotivoDao xivMotivoDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public RhXivSueldoMotivo getRhXivSueldoMotivo(String empresa, String codigo) throws Exception {
        return xivMotivoDao.getRhXivSueldoMotivo(empresa, codigo);
    }

    @Override
    public MensajeTO insertarRhXivSueldoMotivo(RhXivSueldoMotivo rhXivSueldoMotivo, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        String retorno = "";
        rhXivSueldoMotivo.getRhXivSueldoMotivoPK().setMotDetalle(rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotDetalle().toUpperCase());
        if (xivMotivoDao.obtenerPorId(RhXivSueldoMotivo.class, rhXivSueldoMotivo.getRhXivSueldoMotivoPK()) != null) {
            retorno = "FEl motivo del xiv sueldo que va a ingresar ya existe. Intente con otro.";
        } else {
            sisInfoTO.setEmpresa(rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotEmpresa());

            susDetalle = "Se insertó el motivo del xiv sueldo: " + rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotDetalle();
            susClave = rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotDetalle();
            susTabla = "recursoshumanos.rh_xiv_sueldo_motivo";
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            rhXivSueldoMotivo.setUsrEmpresa(sisInfoTO.getEmpresa());
            rhXivSueldoMotivo.setUsrCodigo(sisInfoTO.getUsuario());
            rhXivSueldoMotivo.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));

            if (xivMotivoDao.insertarRhXivSueldoMotivo(rhXivSueldoMotivo, sisSuceso)) {
                retorno = "TEl motivo del xiv sueldo se guardo correctamente.";
                mensajeTO.getMap().put("motivo", rhXivSueldoMotivo);
            } else {
                retorno = "FHubo un error al guardar el motivo del xiv sueldo. Intente de nuevo o contacte con el administrador";
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    @Override
    public String insertarRhXivSueldMotivo(RhXivSueldoMotivo rhXivSueldoMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        rhXivSueldoMotivo.getRhXivSueldoMotivoPK().setMotDetalle(rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotDetalle().toUpperCase());
        if (xivMotivoDao.obtenerPorId(RhXivSueldoMotivo.class, rhXivSueldoMotivo.getRhXivSueldoMotivoPK()) != null) {
            retorno = "FEl motivo del xiv sueldo que va a ingresar ya existe. Intente con otro.";
        } else {
            sisInfoTO.setEmpresa(rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotEmpresa());
            susDetalle = "El motivo de 14º sueldo: Detalle " + rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotDetalle() + ", se ha guardado correctamente";
            susClave = rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotDetalle();
            susTabla = "recursoshumanos.rh_xiv_sueldo_motivo";
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            rhXivSueldoMotivo.setUsrEmpresa(sisInfoTO.getEmpresa());
            rhXivSueldoMotivo.setUsrCodigo(sisInfoTO.getUsuario());
            rhXivSueldoMotivo.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));

            if (xivMotivoDao.insertarRhXivSueldoMotivo(rhXivSueldoMotivo, sisSuceso)) {
                retorno = "TEl motivo de 14º sueldo: Detalle " + rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotDetalle() + ", se ha guardado correctamente.";
            } else {
                retorno = "FHubo un error al guardar el motivo del xiv sueldo. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public String modificarRhXivSueldoMotivo(RhXivSueldoMotivo rhXivSueldoMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotEmpresa());
        susDetalle = "El motivo de 14º sueldo: Detalle " + rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotDetalle() + ", se ha modificado correctamente";
        susClave = rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotDetalle();
        susSuceso = "UPDATE";
        susTabla = "recursoshumanos.rh_xiv_sueldo_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        if (xivMotivoDao.modificarRhXivSueldoMotivo(rhXivSueldoMotivo, sisSuceso)) {
            retorno = "TEl motivo de 14º sueldo: Detalle " + rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotDetalle() + ", se ha modificado correctamente.";
        } else {
            retorno = "Hubo un error al modificar el motivo del xiv sueldo. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String modificarEstadoRhXivSueldoMotivo(RhXivSueldoMotivo rhXivSueldoMotivo, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotEmpresa());
        susDetalle = "El motivo de 14º sueldo: Detalle " + rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotDetalle() + (estado ? ", se ha inactivado correctamente" : ", se ha activado correctamente");
        susClave = rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotDetalle();
        susSuceso = "UPDATE";
        susTabla = "recursoshumanos.rh_xiv_sueldo_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        rhXivSueldoMotivo.setMotInactivo(estado);
        if (xivMotivoDao.modificarRhXivSueldoMotivo(rhXivSueldoMotivo, sisSuceso)) {
            retorno = "TEl motivo de 14º sueldo: Código " + rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotDetalle() + (estado ? ", se ha inactivado correctamente" : ", se ha activado correctamente");
        } else {
            retorno = "Hubo un error al modificar el motivo del xiv sueldo. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String eliminarRhXivSueldoMotivo(RhXivSueldoMotivo rhXivSueldoMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        if (!xivMotivoDao.banderaEliminarXivMotivo(rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotEmpresa(), rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotDetalle())) {
            retorno = "FNo se puede eliminar el motivo del xiv sueldo porque presenta movimientos";
        } else {
            sisInfoTO.setEmpresa(rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotEmpresa());
            susDetalle = "El motivo de 14º sueldo: Detalle " + rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotDetalle() + ", se ha eliminado correctamente";
            susClave = rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotDetalle();
            susSuceso = "DELETE";
            susTabla = "recursoshumanos.rh_xiv_sueldo_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (xivMotivoDao.eliminarRhXivSueldoMotivo(rhXivSueldoMotivo, sisSuceso)) {
                retorno = "TEl motivo de 14º sueldo: Detalle " + rhXivSueldoMotivo.getRhXivSueldoMotivoPK().getMotDetalle() + ", se ha eliminado correctamente.";
            } else {
                retorno = "FHubo un error al eliminar el motivo del xiv sueldo. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public List<RhXivSueldoMotivo> getListaRhXivSueldoMotivo(String empresa) throws Exception {
        return xivMotivoDao.getListaRhXivSueldoMotivo(empresa);
    }

    @Override
    public List<RhXivSueldoMotivo> getListaRhXivSueldoMotivoSoloActivos(String empresa) throws Exception {
        return xivMotivoDao.getListaRhXivSueldoMotivoSoloActivos(empresa);
    }

}
