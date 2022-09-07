package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PreLiquidacionMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class PreLiquidacionMotivoServiceImpl implements PreLiquidacionMotivoService {

    @Autowired
    private PreLiquidacionMotivoDao preLiquidacionMotivoDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public String insertarPrdPreLiquidacionMotivo(PrdPreLiquidacionMotivo prdPreLiquidacionMotivo, SisInfoTO sisInfoTO)
            throws Exception {
        String retorno = "";
        prdPreLiquidacionMotivo.getPrdPreLiquidacionMotivoPK()
                .setPlmCodigo(prdPreLiquidacionMotivo.getPrdPreLiquidacionMotivoPK().getPlmCodigo().toUpperCase());

        if (preLiquidacionMotivoDao.obtenerPorId(PrdPreLiquidacionMotivo.class,
                prdPreLiquidacionMotivo.getPrdPreLiquidacionMotivoPK()) != null) {
            retorno = "FLa Talla de la liquidacion que va a ingresar ya existe. Intente con otro.";
        } else {
            sisInfoTO.setEmpresa(prdPreLiquidacionMotivo.getPrdPreLiquidacionMotivoPK().getPlmEmpresa());

            susDetalle = "Se insertó la Talla de la liquidacion: "
                    + prdPreLiquidacionMotivo.getPrdPreLiquidacionMotivoPK().getPlmEmpresa();
            susClave = prdPreLiquidacionMotivo.getPrdPreLiquidacionMotivoPK().getPlmCodigo();
            susTabla = "produccion.prd_liquidacion_Talla";
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            prdPreLiquidacionMotivo.setPlmDetalle(prdPreLiquidacionMotivo.getPlmDetalle().toUpperCase());
            prdPreLiquidacionMotivo.setUsrEmpresa(sisInfoTO.getEmpresa());
            prdPreLiquidacionMotivo.setUsrCodigo(sisInfoTO.getUsuario());
            prdPreLiquidacionMotivo.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));

            if (preLiquidacionMotivoDao.insertarPrdPreLiquidacionMotivo(prdPreLiquidacionMotivo, sisSuceso)) {
                retorno = "TEl motivo de la liquidación: Código: " + prdPreLiquidacionMotivo.getPrdPreLiquidacionMotivoPK().getPlmCodigo() + ", se guardo correctamente.";
            } else {
                retorno = "FHubo un error al guardar el Talla de la liquidacion. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public String modificarPrdPreLiquidacionMotivo(PrdPreLiquidacionMotivo prdPreLiquidacionMotivo, SisInfoTO sisInfoTO)
            throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(prdPreLiquidacionMotivo.getPrdPreLiquidacionMotivoPK().getPlmEmpresa());

        susDetalle = "Se modificó el motivo de la liquidacion: "
                + prdPreLiquidacionMotivo.getPrdPreLiquidacionMotivoPK().getPlmEmpresa();
        susClave = prdPreLiquidacionMotivo.getPrdPreLiquidacionMotivoPK().getPlmCodigo();
        susSuceso = "UPDATE";
        susTabla = "produccion.prd_liquidacion_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        prdPreLiquidacionMotivo.setPlmDetalle(prdPreLiquidacionMotivo.getPlmDetalle().toUpperCase());

        if (preLiquidacionMotivoDao.modificarPrdPreLiquidacionMotivo(prdPreLiquidacionMotivo, sisSuceso)) {
            retorno = "TEl motivo de la liquidación: Código: " + prdPreLiquidacionMotivo.getPrdPreLiquidacionMotivoPK().getPlmCodigo() + ", se modificó correctamente.";
        } else {
            retorno = "Hubo un error al modificar el motivo de la liquidacion. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String eliminarPrdPreLiquidacionMotivo(PrdPreLiquidacionMotivo prdPreLiquidacionMotivo, SisInfoTO sisInfoTO)
            throws Exception {
        String retorno = "";
        if (!preLiquidacionMotivoDao.banderaEliminarPreLiquidacionMotivo(
                prdPreLiquidacionMotivo.getPrdPreLiquidacionMotivoPK().getPlmEmpresa(),
                prdPreLiquidacionMotivo.getPrdPreLiquidacionMotivoPK().getPlmCodigo())) {
            retorno = "FNo se puede eliminar el motivo de la liquidacion porque presenta movimientos";
        } else {
            sisInfoTO.setEmpresa(prdPreLiquidacionMotivo.getPrdPreLiquidacionMotivoPK().getPlmEmpresa());

            susDetalle = "Se eliminó el motivo de la liquidacion: "
                    + prdPreLiquidacionMotivo.getPrdPreLiquidacionMotivoPK().getPlmEmpresa();
            susClave = prdPreLiquidacionMotivo.getPrdPreLiquidacionMotivoPK().getPlmCodigo();
            susSuceso = "DELETE";
            susTabla = "produccion.prd_liquidacion_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (preLiquidacionMotivoDao.eliminarPrdPreLiquidacionMotivo(prdPreLiquidacionMotivo, sisSuceso)) {
                retorno = "TEl motivo de la liquidación: Código: " + prdPreLiquidacionMotivo.getPrdPreLiquidacionMotivoPK().getPlmCodigo() + ", se eliminó correctamente.";
            } else {
                retorno = "FHubo un error al eliminar el motivo de la liquidacion. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public List<PrdPreLiquidacionMotivo> getListaPrdPreLiquidacionMotivo(String empresa) throws Exception {
        return preLiquidacionMotivoDao.getListaPrdPreLiquidacionMotivo(empresa);
    }

    @Override
    public List<PrdPreLiquidacionMotivo> getListaPrdPreLiquidacionMotivoTO(String empresa, boolean inactivo) throws Exception {
        return preLiquidacionMotivoDao.getListaPrdPreLiquidacionMotivoTO(empresa, inactivo);
    }

    @Override
    public String modificarEstadoPrdPreLiquidacionMotivo(PrdPreLiquidacionMotivo prdPreLiquidacionMotivo, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(prdPreLiquidacionMotivo.getPrdPreLiquidacionMotivoPK().getPlmEmpresa());

        susDetalle = "Se modificó el estado del motivo de la liquidacion: "
                + prdPreLiquidacionMotivo.getPrdPreLiquidacionMotivoPK().getPlmEmpresa();
        susClave = prdPreLiquidacionMotivo.getPrdPreLiquidacionMotivoPK().getPlmCodigo();
        susSuceso = "UPDATE";
        susTabla = "produccion.prd_liquidacion_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        prdPreLiquidacionMotivo.setPlmInactivo(estado);

        if (preLiquidacionMotivoDao.modificarPrdPreLiquidacionMotivo(prdPreLiquidacionMotivo, sisSuceso)) {
            retorno = "TEl motivo de la liquidación: Código: " + prdPreLiquidacionMotivo.getPrdPreLiquidacionMotivoPK().getPlmCodigo();
        } else {
            retorno = "Hubo un error al modificar el motivo de la liquidacion. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }
}
