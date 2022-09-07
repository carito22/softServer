package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.LiquidacionMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesProduccion;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionMotivoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class LiquidacionMotivoServiceImpl implements LiquidacionMotivoService {

    @Autowired
    private LiquidacionMotivoDao liquidacionMotivoDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public PrdLiquidacionMotivoTO getPrdLiquidacionMotivoTO(String empresa,
            PrdLiquidacionMotivoTablaTO prdLiquidacionMotivoTablaTO) throws Exception {
        return liquidacionMotivoDao.getPrdLiquidacionMotivoTO(empresa, prdLiquidacionMotivoTablaTO);
    }

    @Override
    public List<PrdLiquidacionMotivoTablaTO> getListaPrdLiquidacionMotivoTablaTO(String empresa) throws Exception {
        return liquidacionMotivoDao.getListaPrdLiquidacionMotivoTablaTO(empresa);
    }

    @Override
    public String insertarPrdLiquidacionMotivoTO(PrdLiquidacionMotivoTO prdLiquidacionMotivoTO, SisInfoTO sisInfoTO)
            throws Exception {
        String retorno = "";
        if (liquidacionMotivoDao.obtenerPorId(PrdLiquidacionMotivo.class, new PrdLiquidacionMotivoPK(
                prdLiquidacionMotivoTO.getLmEmpresa(), prdLiquidacionMotivoTO.getLmCodigo())) == null) {
            /// PREPARANDO OBJETO SISSUCESO
            susDetalle = "Se insertó el motivo de la liquidacion: " + prdLiquidacionMotivoTO.getLmDetalle();
            susClave = prdLiquidacionMotivoTO.getLmCodigo();
            susSuceso = "INSERT";
            susTabla = "produccion.prd_liquidacion_motivo";
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            prdLiquidacionMotivoTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            PrdLiquidacionMotivo prdLiquidacionMotivo = ConversionesProduccion
                    .convertirPrdLiquidacionMotivoTO_PrdLiquidacionMotivo(prdLiquidacionMotivoTO);
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (liquidacionMotivoDao.insertarPrdLiquidacionMotivo(prdLiquidacionMotivo, sisSuceso)) {
                retorno = "TEl motivo de la liquidacion se guardo correctamente...";
            } else {
                retorno = "FHubo un error al guardar el motivo de la liquidacion...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl motivo de la liquidacion que va a ingresar ya existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String modificarPrdLiquidacionMotivoTO(PrdLiquidacionMotivoTO prdLiquidacionMotivoTO, SisInfoTO sisInfoTO)
            throws Exception {
        String retorno = "";
        PrdLiquidacionMotivo prdLiquidacionMotivoAux = liquidacionMotivoDao.obtenerPorId(PrdLiquidacionMotivo.class,
                new PrdLiquidacionMotivoPK(prdLiquidacionMotivoTO.getLmEmpresa(),
                        prdLiquidacionMotivoTO.getLmCodigo()));
        if (prdLiquidacionMotivoAux != null) {
            susDetalle = "Se modificó el motivo de la liquidacion: " + prdLiquidacionMotivoTO.getLmDetalle();
            susClave = prdLiquidacionMotivoTO.getLmCodigo();
            susSuceso = "UPDATE";
            susTabla = "produccion.prd_liquidacion_motivo";

            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            prdLiquidacionMotivoTO.setUsrCodigo(prdLiquidacionMotivoAux.getUsrCodigo());
            prdLiquidacionMotivoTO.setUsrFechaInserta(
                    UtilsValidacion.fecha(prdLiquidacionMotivoAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            PrdLiquidacionMotivo prdLiquidacionMotivo = ConversionesProduccion
                    .convertirPrdLiquidacionMotivoTO_PrdLiquidacionMotivo(prdLiquidacionMotivoTO);

            if (liquidacionMotivoDao.modificarPrdLiquidacionMotivo(prdLiquidacionMotivo, sisSuceso)) {
                retorno = "TEl motivo de la liquidacion se modificó correctamente...";
            } else {
                retorno = "Hubo un error al modificar el motivo de la liquidacion...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl motivo de la liquidacion que va a modificar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String eliminarPrdLiquidacionMotivoTO(PrdLiquidacionMotivoTO prdLiquidacionMotivoTO, SisInfoTO sisInfoTO)
            throws Exception {

        boolean seguir = liquidacionMotivoDao.banderaEliminarLiquidacionMotivo(prdLiquidacionMotivoTO.getLmEmpresa(),
                prdLiquidacionMotivoTO.getLmCodigo());
        PrdLiquidacionMotivo prdLiquidacionMotivo = null;
        String retorno = "";
        if (seguir) {
            prdLiquidacionMotivoTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            prdLiquidacionMotivo = liquidacionMotivoDao.getPrdLiquidacionMotivo(
                    prdLiquidacionMotivoTO.getLmEmpresa(), prdLiquidacionMotivoTO.getLmCodigo());
            // SUCESO
            susClave = prdLiquidacionMotivoTO.getLmEmpresa();
            susTabla = "produccion.prd_liquidacion_motivo";
            susDetalle = "Se eliminó el motivo de liquidacion: " + prdLiquidacionMotivoTO.getLmDetalle()
                    + " con código " + prdLiquidacionMotivoTO.getLmCodigo();
            susSuceso = "DELETE";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            prdLiquidacionMotivoTO.setUsrCodigo(prdLiquidacionMotivo.getUsrCodigo());
            prdLiquidacionMotivoTO.setUsrFechaInserta(
                    UtilsValidacion.fecha(prdLiquidacionMotivo.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));

            prdLiquidacionMotivo = ConversionesProduccion
                    .convertirPrdLiquidacionMotivoTO_PrdLiquidacionMotivo(prdLiquidacionMotivoTO);

            if (liquidacionMotivoDao.eliminarPrdLiquidacionMotivo(prdLiquidacionMotivo, sisSuceso)) {
                retorno = "TEl motivo de liquidación de pesca se eliminó correctamente...";
            } else {
                retorno = "FHubo un error al eliminar el motivo de liquidación de pesca...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FNo se puede eliminar un motivo con movimientos";
        }
        return retorno;
    }

    @Override
    public String insertarPrdLiquidacionMotivo(PrdLiquidacionMotivo prdLiquidacionMotivo, SisInfoTO sisInfoTO)
            throws Exception {
        String retorno = "";
        prdLiquidacionMotivo.getPrdLiquidacionMotivoPK()
                .setLmCodigo(prdLiquidacionMotivo.getPrdLiquidacionMotivoPK().getLmCodigo().toUpperCase());

        if (liquidacionMotivoDao.obtenerPorId(PrdLiquidacionMotivo.class,
                prdLiquidacionMotivo.getPrdLiquidacionMotivoPK()) != null) {
            retorno = "FEl motivo de la liquidación que va a ingresar ya existe. Intente con otro.";
        } else {
            sisInfoTO.setEmpresa(prdLiquidacionMotivo.getPrdLiquidacionMotivoPK().getLmEmpresa());

            susDetalle = "Se insertó la Talla de la liquidacion: " + prdLiquidacionMotivo.getLmDetalle();
            susClave = prdLiquidacionMotivo.getPrdLiquidacionMotivoPK().getLmCodigo();
            susTabla = "produccion.prd_liquidacion_Talla";
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            prdLiquidacionMotivo.setLmDetalle(prdLiquidacionMotivo.getLmDetalle().toUpperCase());
            prdLiquidacionMotivo.setUsrEmpresa(sisInfoTO.getEmpresa());
            prdLiquidacionMotivo.setUsrCodigo(sisInfoTO.getUsuario());
            prdLiquidacionMotivo.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));

            if (liquidacionMotivoDao.insertarPrdLiquidacionMotivo(prdLiquidacionMotivo, sisSuceso)) {
                retorno = "TEl motivo de liquidación: código: " + prdLiquidacionMotivo.getPrdLiquidacionMotivoPK().getLmCodigo() + ", se guardó correctamente.";
            } else {
                retorno = "FHubo un error al guardar el Talla de la liquidacion. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public String modificarPrdLiquidacionMotivo(PrdLiquidacionMotivo prdLiquidacionMotivo, SisInfoTO sisInfoTO)
            throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(prdLiquidacionMotivo.getPrdLiquidacionMotivoPK().getLmEmpresa());

        susDetalle = "Se modificó el motivo de la liquidacion: " + prdLiquidacionMotivo.getLmDetalle();
        susClave = prdLiquidacionMotivo.getPrdLiquidacionMotivoPK().getLmCodigo();
        susSuceso = "UPDATE";
        susTabla = "produccion.prd_liquidacion_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

        prdLiquidacionMotivo.setLmDetalle(prdLiquidacionMotivo.getLmDetalle().toUpperCase());

        if (liquidacionMotivoDao.modificarPrdLiquidacionMotivo(prdLiquidacionMotivo, sisSuceso)) {
            retorno = "TEl motivo de la liquidación: código:" + prdLiquidacionMotivo.getPrdLiquidacionMotivoPK().getLmCodigo() + ", se modificó correctamente.";
        } else {
            retorno = "Hubo un error al modificar el motivo de la liquidacion. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }

    @Override
    public String eliminarPrdLiquidacionMotivo(PrdLiquidacionMotivo prdLiquidacionMotivo, SisInfoTO sisInfoTO)
            throws Exception {
        String retorno = "";
        if (!liquidacionMotivoDao.banderaEliminarLiquidacionMotivo(
                prdLiquidacionMotivo.getPrdLiquidacionMotivoPK().getLmEmpresa(),
                prdLiquidacionMotivo.getPrdLiquidacionMotivoPK().getLmCodigo())) {
            retorno = "FNo se puede eliminar el motivo de la liquidación porque presenta movimientos";
        } else {
            sisInfoTO.setEmpresa(prdLiquidacionMotivo.getPrdLiquidacionMotivoPK().getLmEmpresa());

            susDetalle = "Se eliminó el motivo de la liquidacion: " + prdLiquidacionMotivo.getLmDetalle();
            susClave = prdLiquidacionMotivo.getPrdLiquidacionMotivoPK().getLmCodigo();
            susSuceso = "DELETE";
            susTabla = "produccion.prd_liquidacion_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (liquidacionMotivoDao.eliminarPrdLiquidacionMotivo(prdLiquidacionMotivo, sisSuceso)) {
                retorno = "TEl motivo de la liquidación: Código:" + prdLiquidacionMotivo.getPrdLiquidacionMotivoPK().getLmCodigo() + ", se eliminó correctamente.";
            } else {
                retorno = "FHubo un error al eliminar el motivo de la liquidación. Intente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public List<PrdLiquidacionMotivo> getListaPrdLiquidacionMotivo(String empresa) throws Exception {
        return liquidacionMotivoDao.getListaPrdLiquidacionMotivo(empresa);
    }

    @Override
    public List<PrdLiquidacionMotivo> getListaPrdLiquidacionMotivoTO(String empresa, boolean inactivo) throws Exception {
        return liquidacionMotivoDao.getListaPrdLiquidacionMotivoTO(empresa, inactivo);
    }

    @Override
    public String modificarEstadoPrdLiquidacionMotivo(PrdLiquidacionMotivo prdLiquidacionMotivo, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(prdLiquidacionMotivo.getPrdLiquidacionMotivoPK().getLmEmpresa());
        susDetalle = "Se cambió el estado del motivo de la liquidacion: " + prdLiquidacionMotivo.getLmDetalle();
        susClave = prdLiquidacionMotivo.getPrdLiquidacionMotivoPK().getLmCodigo();
        susSuceso = "UPDATE";
        susTabla = "produccion.prd_liquidacion_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        prdLiquidacionMotivo.setLmInactivo(estado);
        if (liquidacionMotivoDao.modificarPrdLiquidacionMotivo(prdLiquidacionMotivo, sisSuceso)) {
            retorno = "TEl motivo de liquidacion: Código:" + prdLiquidacionMotivo.getPrdLiquidacionMotivoPK().getLmCodigo();
        } else {
            retorno = "Hubo un error al modificar el motivo de la liquidacion. Intente de nuevo o contacte con el administrador";
        }
        return retorno;
    }
}
