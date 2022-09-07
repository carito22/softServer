/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PrdReprocesoMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdReprocesoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Trabajo
 */
@Service
public class PrdReprocesoMotivoServiceImpl implements PrdReprocesoMotivoService {

    @Autowired
    private PrdReprocesoMotivoDao prdReprocesoMotivoDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public boolean comprobarPrdReprocesoMotivo(String empresa, String motCodigo) throws Exception {
        return prdReprocesoMotivoDao.comprobarPrdReprocesoMotivo(empresa, motCodigo);
    }

    @Override
    public List<PrdReprocesoMotivo> listarPrdReprocesoMotivo(String empresa, boolean incluirTodos) throws Exception {
        return prdReprocesoMotivoDao.listarPrdReprocesoMotivo(empresa, incluirTodos);
    }

    @Override
    public String modificarPrdReprocesoMotivo(PrdReprocesoMotivo motivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        PrdReprocesoMotivo prdLiquidacionMotivoAux = prdReprocesoMotivoDao.obtenerPorId(PrdReprocesoMotivo.class, motivo.getPrdReprocesoMotivoPK());
        if (prdLiquidacionMotivoAux != null) {
            susDetalle = "Se modificó el motivo de reproceso: " + motivo.getPrdDetalle();
            susClave = motivo.getPrdReprocesoMotivoPK().getPrdCodigo();
            susSuceso = "UPDATE";
            susTabla = "produccion.prd_reproceso_motivo";

            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            motivo.setUsrCodigo(sisInfoTO.getUsuario());
            motivo.setUsrEmpresa(sisInfoTO.getEmpresa());
            motivo.setUsrFechaInserta(UtilsDate.timestamp());

            if (prdReprocesoMotivoDao.modificarPrdReprocesoMotivo(motivo, sisSuceso)) {
                retorno = "TEl motivo de reproceso:" + motivo.getPrdReprocesoMotivoPK().getPrdCodigo() + ", se ha modificado correctamente.";
            } else {
                retorno = "Hubo un error al modificar el motivo de reproceso...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl motivo de reproceso que va a modificar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String modificarEstadoPrdReprocesoMotivo(PrdReprocesoMotivo motivo, boolean inactivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        PrdReprocesoMotivo prdLiquidacionMotivoAux = prdReprocesoMotivoDao.obtenerPorId(PrdReprocesoMotivo.class, motivo.getPrdReprocesoMotivoPK());
        if (prdLiquidacionMotivoAux != null) {
            susDetalle = "Se ha " + (inactivo ? "inactivado" : "activado") + " correctamente el motivo de reproceso: " + motivo.getPrdDetalle();
            susClave = motivo.getPrdReprocesoMotivoPK().getPrdCodigo();
            susSuceso = "UPDATE";
            susTabla = "produccion.prd_reproceso_motivo";

            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            motivo.setUsrCodigo(sisInfoTO.getUsuario());
            motivo.setUsrEmpresa(sisInfoTO.getEmpresa());
            motivo.setUsrFechaInserta(UtilsDate.timestamp());
            motivo.setPrdInactivo(inactivo);
            if (prdReprocesoMotivoDao.modificarPrdReprocesoMotivo(motivo, sisSuceso)) {
                retorno = "TEl motivo de reproceso:" + motivo.getPrdReprocesoMotivoPK().getPrdCodigo() + (inactivo ? ", se ha inactivado correctamente." : ", se ha activado correctamente.");
            } else {
                retorno = "Hubo un error al modificar el motivo de reproceso...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl motivo de reproceso que va a modificar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String eliminarPrdReprocesoMotivo(PrdReprocesoMotivo motivo, SisInfoTO sisInfoTO) throws Exception {
        boolean seguir = prdReprocesoMotivoDao.banderaEliminarPrdReprocesoMotivo(
                sisInfoTO.getEmpresa(),
                motivo.getPrdReprocesoMotivoPK().getPrdCodigo());
        String retorno = "";
        if (seguir) {
            motivo.setUsrFechaInserta(UtilsDate.timestamp());
            // SUCESO
            susClave = motivo.getPrdReprocesoMotivoPK().getPrdCodigo();
            susTabla = "produccion.prd_reproceso_motivo";
            susDetalle = "Se eliminó el motivo de reproceso: " + motivo.getPrdDetalle()
                    + " con código " + motivo.getPrdReprocesoMotivoPK().getPrdCodigo();
            susSuceso = "DELETE";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (prdReprocesoMotivoDao.eliminarPrdReprocesoMotivo(motivo, sisSuceso)) {
                retorno = "TEl motivo de reproceso:" + motivo.getPrdReprocesoMotivoPK().getPrdCodigo() + ", se ha eliminado correctamente.";
            } else {
                retorno = "FHubo un error al eliminar el motivo de reproceso...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FNo se puede eliminar un motivo con movimientos";
        }
        return retorno;
    }

    @Override
    public String insertarPrdReprocesoMotivo(PrdReprocesoMotivo motivo, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        if (prdReprocesoMotivoDao.obtenerPorId(PrdReprocesoMotivo.class, motivo.getPrdReprocesoMotivoPK()) == null) {
            /// PREPARANDO OBJETO SISSUCESO
            susDetalle = "Se insertó el motivo de reproceso: " + motivo.getPrdDetalle();
            susClave = motivo.getPrdReprocesoMotivoPK().getPrdCodigo();
            susSuceso = "INSERT";
            susTabla = "produccion.prd_reproceso_motivo";
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            motivo.getPrdReprocesoMotivoPK().setPrdEmpresa(sisInfoTO.getEmpresa());
            motivo.setUsrCodigo(sisInfoTO.getUsuario());
            motivo.setUsrEmpresa(sisInfoTO.getEmpresa());
            motivo.setUsrFechaInserta(UtilsDate.timestamp());
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (prdReprocesoMotivoDao.insertarPrdReprocesoMotivo(motivo, sisSuceso)) {
                retorno = "TEl motivo de reproceso:" + motivo.getPrdReprocesoMotivoPK().getPrdCodigo() + ", se ha guardado correctamente.";
            } else {
                retorno = "FHubo un error al guardar el motivo de reproceso...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl motivo de reproceso que va a ingresar ya existe...\nIntente con otro...";
        }
        return retorno;
    }

}
