package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.UtilidadMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidadMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class UtilidadMotivoServiceImpl implements UtilidadMotivoService {
    
    @Autowired
    private UtilidadMotivoDao utilidadMotivoDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    
    @Override
    public RhUtilidadMotivo getRhUtilidadMotivo(String empresa, String codigo) throws Exception {
        return utilidadMotivoDao.getRhUtilidadMotivo(empresa, codigo);
    }
    
    @Override
    public String insertarRrhhUtilidadMotivo(RhUtilidadMotivo rhUtilidadMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        rhUtilidadMotivo.getRhUtilidadMotivoPK().setMotDetalle(rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotDetalle().toUpperCase());
        if (utilidadMotivoDao.obtenerPorId(RhUtilidadMotivo.class,
                rhUtilidadMotivo.getRhUtilidadMotivoPK()) != null) {
            retorno = "FEl motivo de la utilidad que va a ingresar ya existe. Intente con otro.";
        } else {
            sisInfoTO.setEmpresa(rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotEmpresa());
            susDetalle = "El motivo de utilidad: Detalle " + rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotDetalle() + ", se ha guardado correctamente";
            susClave = rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotDetalle();
            susTabla = "recursoshumanos.rh_utilidades_motivo";
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            rhUtilidadMotivo.setUsrEmpresa(sisInfoTO.getEmpresa());
            rhUtilidadMotivo.setUsrCodigo(sisInfoTO.getUsuario());
            rhUtilidadMotivo.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));
            if (utilidadMotivoDao.insertarRhUtilidadMotivo(rhUtilidadMotivo, sisSuceso)) {
                retorno = "TEl motivo de utilidad: Detalle " + rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotDetalle() + ", se ha guardado correctamente.";
            } else {
                retorno = "FHubo un error al guardar el motivo de la utilidad. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }
    
    @Override
    public MensajeTO insertarRhUtilidadMotivo(RhUtilidadMotivo rhUtilidadMotivo, SisInfoTO sisInfoTO) throws Exception {
        MensajeTO mensajeTO = new MensajeTO();
        String retorno = "";
        rhUtilidadMotivo.getRhUtilidadMotivoPK()
                .setMotDetalle(rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotDetalle().toUpperCase());
        
        if (utilidadMotivoDao.obtenerPorId(RhUtilidadMotivo.class,
                rhUtilidadMotivo.getRhUtilidadMotivoPK()) != null) {
            retorno = "FEl motivo de la utilidad que va a ingresar ya existe. Intente con otro.";
        } else {
            sisInfoTO.setEmpresa(rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotEmpresa());
            
            susDetalle = "Se insertó el motivo de la utilidad: "
                    + rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotDetalle();
            susClave = rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotDetalle();
            susTabla = "recursoshumanos.rh_utilidades_motivo";
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            
            rhUtilidadMotivo.setUsrEmpresa(sisInfoTO.getEmpresa());
            rhUtilidadMotivo.setUsrCodigo(sisInfoTO.getUsuario());
            rhUtilidadMotivo.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));
            
            if (utilidadMotivoDao.insertarRhUtilidadMotivo(rhUtilidadMotivo, sisSuceso)) {
                retorno = "TEl motivo de la utilidad se guardo correctamente.";
                mensajeTO.getMap().put("motivo", rhUtilidadMotivo);
            } else {
                retorno = "FHubo un error al guardar el motivo de la utilidad. Intente de nuevo o contacte con el administrador";
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }
    
    @Override
    public String modificarRhUtilidadMotivo(RhUtilidadMotivo rhUtilidadMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotEmpresa());
        susDetalle = "El motivo de utilidad: Detalle " + rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotDetalle() + ", se ha modificado correctamente";
        susClave = rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotDetalle();
        susSuceso = "UPDATE";
        susTabla = "recursoshumanos.rh_utilidades_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        
        if (utilidadMotivoDao.modificarRhUtilidadMotivo(rhUtilidadMotivo, sisSuceso)) {
            retorno = "TEl motivo de utilidad: Detalle " + rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotDetalle() + ", se ha modificado correctamente.";
        } else {
            retorno = "Hubo un error al modificar el motivo de la utilidad. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }
    
    @Override
    public String modificarEstadoRhUtilidadMotivo(RhUtilidadMotivo rhUtilidadMotivo, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotEmpresa());
        susDetalle = "El motivo de utilidad: Detalle " + rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotDetalle() + (estado ? ", se ha inactivado correctamente" : ", se ha activado correctamente");
        susClave = rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotDetalle();
        susSuceso = "UPDATE";
        susTabla = "recursoshumanos.rh_utilidades_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        rhUtilidadMotivo.setMotInactivo(estado);
        if (utilidadMotivoDao.modificarRhUtilidadMotivo(rhUtilidadMotivo, sisSuceso)) {
            retorno = "TEl motivo de utilidad: Código " + rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotDetalle() + (estado ? ", se ha inactivado correctamente" : ", se ha activado correctamente");
        } else {
            retorno = "Hubo un error al modificar el motivo de la utilidad. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }
    
    @Override
    public String eliminarRhUtilidadMotivo(RhUtilidadMotivo rhUtilidadMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        if (!utilidadMotivoDao.banderaEliminarUtilidadMotivo(rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotEmpresa(), rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotDetalle())) {
            retorno = "FNo se puede eliminar el motivo de la utilidad porque presenta movimientos";
        } else {
            sisInfoTO.setEmpresa(rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotEmpresa());
            susDetalle = "El motivo de utilidad: Detalle " + rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotDetalle() + ", se ha eliminado correctamente";
            susClave = rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotDetalle();
            susSuceso = "DELETE";
            susTabla = "recursoshumanos.rh_utilidades_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (utilidadMotivoDao.eliminarRhUtilidadMotivo(rhUtilidadMotivo, sisSuceso)) {
                retorno = "TEl motivo de utilidad: Detalle " + rhUtilidadMotivo.getRhUtilidadMotivoPK().getMotDetalle() + ", se ha eliminado correctamente.";
            } else {
                retorno = "FHubo un error al eliminar el motivo de la utilidad. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }
    
    @Override
    public List<RhUtilidadMotivo> getListaRhUtilidadMotivo(String empresa) throws Exception {
        return utilidadMotivoDao.getListaRhUtilidadMotivo(empresa);
    }
    
    @Override
    public List<RhUtilidadMotivo> getListaRhUtilidadMotivoSoloActivos(String empresa) throws Exception {
        return utilidadMotivoDao.getListaRhUtilidadMotivoSoloActivos(empresa);
    }
    
}
