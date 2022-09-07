package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.RolMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRolMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class RolMotivoServiceImpl implements RolMotivoService {

    @Autowired
    private RolMotivoDao rolMotivoDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public RhRolMotivo getRhRolMotivo(String empresa, String codigo) throws Exception {
        return rolMotivoDao.getRhRolMotivo(empresa, codigo);
    }

    @Override
    public String insertarRhRolMotivo(RhRolMotivo rhRolMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        rhRolMotivo.getRhRolMotivoPK().setMotDetalle(rhRolMotivo.getRhRolMotivoPK().getMotDetalle().toUpperCase());

        if (rolMotivoDao.obtenerPorId(RhRolMotivo.class, rhRolMotivo.getRhRolMotivoPK()) != null) {
            retorno = "FEl motivo del rol que va a ingresar ya existe. Intente con otro.";
        } else {
            sisInfoTO.setEmpresa(rhRolMotivo.getRhRolMotivoPK().getMotEmpresa());

            susDetalle = "Se insertó el motivo del rol: " + rhRolMotivo.getRhRolMotivoPK().getMotDetalle();
            susClave = rhRolMotivo.getRhRolMotivoPK().getMotDetalle();
            susTabla = "recursoshumanos.rh_rol_motivo";
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            rhRolMotivo.setUsrEmpresa(sisInfoTO.getEmpresa());
            rhRolMotivo.setUsrCodigo(sisInfoTO.getUsuario());
            rhRolMotivo.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));

            if (rolMotivoDao.insertarRhRolMotivo(rhRolMotivo, sisSuceso)) {
                retorno = "TEl motivo del rol:  "+rhRolMotivo.getRhRolMotivoPK().getMotDetalle()+", se ha guardado correctamente.";
            } else {
                retorno = "FHubo un error al guardar el motivo del rol. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public String modificarRhRolMotivo(RhRolMotivo rhRolMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(rhRolMotivo.getRhRolMotivoPK().getMotEmpresa());

        susDetalle = "Se modificó el motivo del rol: " + rhRolMotivo.getRhRolMotivoPK().getMotDetalle();
        susClave = rhRolMotivo.getRhRolMotivoPK().getMotDetalle();
        susTabla = "recursoshumanos.rh_rol_motivo";
        susSuceso = "UPDATE";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        if (rolMotivoDao.modificarRhRolMotivo(rhRolMotivo, sisSuceso)) {
            retorno = "TEl motivo del rol:  "+rhRolMotivo.getRhRolMotivoPK().getMotDetalle()+", se ha modificado correctamente.";
        } else {
            retorno = "Hubo un error al modificar el motivo del rol. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }
    
    @Override
    public String modificarEstadoRhRolMotivo(RhRolMotivo rhRolMotivo, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(rhRolMotivo.getRhRolMotivoPK().getMotEmpresa());

        susDetalle = "Se modificó el motivo del rol: " + rhRolMotivo.getRhRolMotivoPK().getMotDetalle();
        susClave = rhRolMotivo.getRhRolMotivoPK().getMotDetalle();
        susTabla = "recursoshumanos.rh_rol_motivo";
        susSuceso = "UPDATE";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        rhRolMotivo.setMotInactivo(estado);
        if (rolMotivoDao.modificarRhRolMotivo(rhRolMotivo, sisSuceso)) {
            retorno = "TEl motivo del rol:  "+rhRolMotivo.getRhRolMotivoPK().getMotDetalle()+", se ha "+(estado ? "inactivado" : "activado")+" correctamente.";
        } else {
            retorno = "Hubo un error al modificar el motivo del rol. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String eliminarRhRolMotivo(RhRolMotivo rhRolMotivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        if (!rolMotivoDao.banderaEliminarRolMotivo(rhRolMotivo.getRhRolMotivoPK().getMotEmpresa(),
                rhRolMotivo.getRhRolMotivoPK().getMotDetalle())) {
            retorno = "FNo se puede eliminar el motivo del rol porque presenta movimientos";
        } else {
            sisInfoTO.setEmpresa(rhRolMotivo.getRhRolMotivoPK().getMotEmpresa());

            susDetalle = "Se eliminó el motivo del rol: " + rhRolMotivo.getRhRolMotivoPK().getMotDetalle();
            susClave = rhRolMotivo.getRhRolMotivoPK().getMotDetalle();
            susTabla = "recursoshumanos.rh_rol_motivo";
            susSuceso = "DELETE";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (rolMotivoDao.eliminarRhRolMotivo(rhRolMotivo, sisSuceso)) {
                retorno = "TEl motivo del rol:  "+rhRolMotivo.getRhRolMotivoPK().getMotDetalle()+", se ha eliminado correctamente.";
            } else {
                retorno = "FHubo un error al eliminar el motivo del rol. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public List<RhRolMotivo> getListaRhRolMotivo(String empresa) throws Exception {
        return rolMotivoDao.getListaRhRolMotivo(empresa);
    }
    
    @Override
    public List<RhRolMotivo> getListaRhRolMotivos(String empresa, boolean inactivo) throws Exception {
        return rolMotivoDao.getListaRhRolMotivos(empresa, inactivo);
    }

}
