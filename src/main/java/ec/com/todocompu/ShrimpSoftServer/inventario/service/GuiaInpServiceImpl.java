package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.GuiaInpDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionInp;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class GuiaInpServiceImpl implements GuiaInpService {

    @Autowired
    private GuiaInpDao guiaInpDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public String insertarInvGuiaRemisionInp(InvGuiaRemisionInp guiaInp, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        if (guiaInpDao.buscarInvGuiaRemisionInp(guiaInp.getInvGuiaRemisionInpPK().getInpEmpresa(), guiaInp.getInvGuiaRemisionInpPK().getInpCodigo(), guiaInp.getInvGuiaRemisionInpPK().getInpCliCodigo()) == null) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = guiaInp.getInvGuiaRemisionInpPK().getInpCodigo();
            susDetalle = "Se ingresó INP" + guiaInp.getInpDescripcion() + " con código "
                    + susClave;
            susSuceso = "INSERT";
            susTabla = "inventario.inv_guia_remision_inp";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            if (guiaInpDao.insertarInvGuiaRemisionInp(guiaInp, sisSuceso)) {
                retorno = "TGuia INP ingresada correctamente...";
            } else {
                retorno = "FHubo un error al ingresar la Bodega...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl código de INP que va a ingresar ya existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String modificarEstadoInvGuiaRemisionInp(InvGuiaRemisionInp guiaInp, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        InvGuiaRemisionInp guiaInpAux = guiaInpDao.buscarInvGuiaRemisionInp(guiaInp.getInvGuiaRemisionInpPK().getInpEmpresa(), guiaInp.getInvGuiaRemisionInpPK().getInpCodigo(), guiaInp.getInvGuiaRemisionInpPK().getInpCliCodigo());
        if (guiaInpAux != null) {
            susClave = guiaInp.getInvGuiaRemisionInpPK().getInpCodigo();
            susDetalle = "Se cambió el estado de INP: " + guiaInp.getInpDescripcion() + " con código " + susClave;
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_guia_remision_inp";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            guiaInpAux.setInpInactivo(estado);
            if (guiaInpDao.modificarInvGuiaRemisionInp(guiaInp, sisSuceso)) {
                retorno = "TINP se modificó correctamente...";
            } else {
                retorno = "FHubo un error al modificar INP...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl código del INP que va a modificar ya no está disponible...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String modificarInvGuiaRemisionInp(InvGuiaRemisionInp guiaInp, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        InvGuiaRemisionInp guiaInpAux = guiaInpDao.buscarInvGuiaRemisionInp(guiaInp.getInvGuiaRemisionInpPK().getInpEmpresa(), guiaInp.getInvGuiaRemisionInpPK().getInpCodigo(), guiaInp.getInvGuiaRemisionInpPK().getInpCliCodigo());
        if (guiaInpAux != null) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = guiaInp.getInvGuiaRemisionInpPK().getInpCodigo();
            susDetalle = "Se modificó INP " + guiaInp.getInpDescripcion() + " con código "
                    + susClave;
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_guia_remision_inp";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (guiaInpDao.modificarInvGuiaRemisionInp(guiaInp, sisSuceso)) {
                retorno = "TINP se modificó correctamente...";
            } else {
                retorno = "FHubo un error al modificar INP...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl código del INP que va a modificar ya no está disponible...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String eliminarInvGuiaRemisionInp(InvGuiaRemisionInp guiaInp, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        /// PREPARANDO OBJETO SISSUCESO
        susClave = guiaInp.getInvGuiaRemisionInpPK().getInpCodigo();
        susDetalle = "Se eliminó INP " + guiaInp.getInpDescripcion() + " con código "
                + susClave;
        susSuceso = "DELETE";
        susTabla = "inventario.inv_guia_remision_inp";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        if (guiaInpDao.eliminarInvGuiaRemisionInp(guiaInp, sisSuceso)) {
            retorno = "TINP se eliminó correctamente...";
        } else {
            retorno = "FHubo un error al eliminar...\nIntente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public List<InvGuiaRemisionInp> getInvGuiaRemisionInp(String empresa, String cliente, boolean inactivo) throws Exception {
        return guiaInpDao.getInvGuiaRemisionInp(empresa, cliente, inactivo);
    }

}
