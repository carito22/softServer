package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PresupuestoPescaMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class PresupuestoPescaMotivoServiceImpl implements PresupuestoPescaMotivoService {

    @Autowired
    private PresupuestoPescaMotivoDao presupuestoPescaMotivoDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public String insertarPrdPresupuestoPescaMotivo(PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo,
            SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        prdPresupuestoPescaMotivo.getPrdPresupuestoPescaMotivoPK().setPresuCodigo(
                prdPresupuestoPescaMotivo.getPrdPresupuestoPescaMotivoPK().getPresuCodigo().toUpperCase());

        if (presupuestoPescaMotivoDao.obtenerPorId(PrdPresupuestoPescaMotivo.class,
                prdPresupuestoPescaMotivo.getPrdPresupuestoPescaMotivoPK()) != null) {
            retorno = "FEl motivo de presupuesto de pesca que va a ingresar ya existe. Intente con otro.";
        } else {
            sisInfoTO.setEmpresa(prdPresupuestoPescaMotivo.getPrdPresupuestoPescaMotivoPK().getPresuEmpresa());

            susDetalle = "Se insertó la Talla de la liquidacion: " + prdPresupuestoPescaMotivo.getPresuDetalle();
            susClave = prdPresupuestoPescaMotivo.getPrdPresupuestoPescaMotivoPK().getPresuCodigo();
            susTabla = "produccion.prd_liquidacion_Talla";
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            prdPresupuestoPescaMotivo.setPresuDetalle(prdPresupuestoPescaMotivo.getPresuDetalle().toUpperCase());
            prdPresupuestoPescaMotivo.setUsrEmpresa(sisInfoTO.getEmpresa());
            prdPresupuestoPescaMotivo.setUsrCodigo(sisInfoTO.getUsuario());
            prdPresupuestoPescaMotivo.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));

            if (presupuestoPescaMotivoDao.insertarPrdPresupuestoPescaMotivo(prdPresupuestoPescaMotivo, sisSuceso)) {
                retorno = "TEl motivo de presupuesto de pesca: Código: " + prdPresupuestoPescaMotivo.getPrdPresupuestoPescaMotivoPK().getPresuCodigo() + " se guardó correctamente.";
            } else {
                retorno = "FHubo un error al guardar el motivo de presupuesto de pesca. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public String modificarPrdPresupuestoPescaMotivo(PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo,
            SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(prdPresupuestoPescaMotivo.getPrdPresupuestoPescaMotivoPK().getPresuEmpresa());

        susDetalle = "Se modificó el motivo del motivo de presupuesto de pesca: " + prdPresupuestoPescaMotivo.getPresuDetalle();
        susClave = prdPresupuestoPescaMotivo.getPrdPresupuestoPescaMotivoPK().getPresuCodigo();
        susSuceso = "UPDATE";
        susTabla = "produccion.prd_liquidacion_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        prdPresupuestoPescaMotivo.setPresuDetalle(prdPresupuestoPescaMotivo.getPresuDetalle().toUpperCase());

        if (presupuestoPescaMotivoDao.modificarPrdPresupuestoPescaMotivo(prdPresupuestoPescaMotivo, sisSuceso)) {
            retorno = "TEl motivo de presupuesto de pesca: Código: " + prdPresupuestoPescaMotivo.getPrdPresupuestoPescaMotivoPK().getPresuCodigo() + " se modificó correctamente.";
        } else {
            retorno = "Hubo un error al modificar el motivo de presupuesto de pesca. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String eliminarPrdPresupuestoPescaMotivo(PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo,
            SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        if (!presupuestoPescaMotivoDao.banderaEliminarPresupuestoPescaMotivo(
                prdPresupuestoPescaMotivo.getPrdPresupuestoPescaMotivoPK().getPresuEmpresa(),
                prdPresupuestoPescaMotivo.getPrdPresupuestoPescaMotivoPK().getPresuCodigo())) {
            retorno = "FNo se puede eliminar el motivo de la liquidacion porque presenta movimientos";
        } else {
            sisInfoTO.setEmpresa(prdPresupuestoPescaMotivo.getPrdPresupuestoPescaMotivoPK().getPresuEmpresa());

            susDetalle = "Se eliminó el motivo de la liquidacion: " + prdPresupuestoPescaMotivo.getPresuDetalle();
            susClave = prdPresupuestoPescaMotivo.getPrdPresupuestoPescaMotivoPK().getPresuCodigo();
            susSuceso = "DELETE";
            susTabla = "produccion.prd_liquidacion_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (presupuestoPescaMotivoDao.eliminarPrdPresupuestoPescaMotivo(prdPresupuestoPescaMotivo, sisSuceso)) {
                retorno = "TEl motivo de presupuesto de pesca: Código: " + prdPresupuestoPescaMotivo.getPrdPresupuestoPescaMotivoPK().getPresuCodigo() + " se eliminó correctamente.";
            } else {
                retorno = "FHubo un error al eliminar el motivo de la liquidacion. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public List<PrdPresupuestoPescaMotivo> getListaPrdPresupuestoPescaMotivo(String empresa) throws Exception {
        return presupuestoPescaMotivoDao.getListaPrdPresupuestoPescaMotivo(empresa);
    }

    @Override
    public List<PrdPresupuestoPescaMotivo> getListaPrdPresupuestoPescaMotivoTO(String empresa, boolean inactivo) throws Exception {
        return presupuestoPescaMotivoDao.getListaPrdPresupuestoPescaMotivoTO(empresa, inactivo);
    }

    @Override
    public String modificarEstadoPrdPresupuestoPescaMotivo(PrdPresupuestoPescaMotivo prdPresupuestoPescaMotivo, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(prdPresupuestoPescaMotivo.getPrdPresupuestoPescaMotivoPK().getPresuEmpresa());

        susDetalle = "Se cambio el estado del motivo del presupuesto de pesca: " + prdPresupuestoPescaMotivo.getPresuDetalle();
        susClave = prdPresupuestoPescaMotivo.getPrdPresupuestoPescaMotivoPK().getPresuCodigo();
        susSuceso = "UPDATE";
        susTabla = "produccion.prd_liquidacion_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        prdPresupuestoPescaMotivo.setPresuInactivo(estado);

        if (presupuestoPescaMotivoDao.modificarPrdPresupuestoPescaMotivo(prdPresupuestoPescaMotivo, sisSuceso)) {
            retorno = "TEl motivo de presupuesto de pesca: Código: " + prdPresupuestoPescaMotivo.getPrdPresupuestoPescaMotivoPK().getPresuCodigo();
        } else {
            retorno = "Hubo un error al modificar el motivo de presupuesto de pesca. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }
}
