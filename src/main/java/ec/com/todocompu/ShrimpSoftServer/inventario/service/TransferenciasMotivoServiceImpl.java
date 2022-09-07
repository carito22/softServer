package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.TransferenciasMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaTransferenciaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciaMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class TransferenciasMotivoServiceImpl implements TransferenciasMotivoService {

    @Autowired
    private TransferenciasMotivoDao transferenciasMotivoDao;

    private boolean comprobar = false;
    private String mensaje;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<InvTransferenciaMotivoComboTO> getListaTransferenciaMotivoComboTO(String empresa, boolean filtrarInactivos) throws Exception {
        return transferenciasMotivoDao.getListaTransferenciaMotivoComboTO(empresa, filtrarInactivos);
    }

    @Override
    public List<InvListaTransferenciaMotivoTO> getInvListaTransferenciaMotivoTO(String empresa) throws Exception {
        return transferenciasMotivoDao.getInvListaTransferenciaMotivoTO(empresa);
    }

    @Override
    public InvTransferenciaMotivoTO getInvTransferenciaMotivoTO(String empresa, String tmCodigo) throws Exception {
        return transferenciasMotivoDao.getInvTransferenciaMotivoTO(empresa, tmCodigo);
    }

    @Override
    public String accionInvTransferenciaMotivo(InvTransferenciaMotivoTO invTransferenciaMotivoTO, char accion, SisInfoTO sisInfoTO) throws Exception {
        ///// CREANDO Suceso
        susClave = invTransferenciaMotivoTO.getTmDetalle();
        if (accion == 'I') {
            susDetalle = "El motivo de transferencia: Detalle " + invTransferenciaMotivoTO.getTmDetalle() + ", se ha guardado correctamente";
            susSuceso = "INSERT";
        }
        if (accion == 'M') {
            susDetalle = "El motivo de transferencia: Detalle " + invTransferenciaMotivoTO.getTmDetalle() + ", se ha modificado correctamente";
            susSuceso = "UPDATE";
        }
        if (accion == 'E') {
            susDetalle = "El motivo de transferencia: Detalle " + invTransferenciaMotivoTO.getTmDetalle() + ", se ha eliminado correctamente";
            susSuceso = "DELETE";
        }
        susTabla = "inventario.inv_consumos_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        ///// CREANDO CarPagosForma
        InvTransferenciasMotivo invTransferenciaMotivo = ConversionesInventario
                .convertirInvTransferenciasMotivoTO_InvTransferenciasMotivo(invTransferenciaMotivoTO);
        comprobar = false;

        InvTransferenciasMotivo invTransferenciaMotivoAux = null;

        if (accion == 'E') {
            boolean seguir = transferenciasMotivoDao.retornoContadoEliminarTransferenciasMotivo(
                    invTransferenciaMotivoTO.getTmEmpresa(), invTransferenciaMotivoTO.getTmCodigo());
            if (seguir) {
                ////// BUSCANDO existencia PagosForma
                if (transferenciasMotivoDao.comprobarInvTransferenciaMotivo(
                        invTransferenciaMotivoTO.getUsrEmpresa(), invTransferenciaMotivoTO.getTmCodigo())) {
                    invTransferenciaMotivo
                            .setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                    comprobar = transferenciasMotivoDao.accionInvTransferenciasMotivo(invTransferenciaMotivo,
                            sisSuceso, accion);
                } else {
                    mensaje = "FNo se encuentra la motivo de transferencia...";
                }
            } else {
                mensaje = "FNo se puede eliminar un motivo que tiene movimientos";
            }
        } else {
            if (accion == 'I') {
                if (!transferenciasMotivoDao.comprobarInvTransferenciaMotivo(
                        invTransferenciaMotivoTO.getUsrEmpresa(), invTransferenciaMotivoTO.getTmCodigo())) {
                    invTransferenciaMotivo
                            .setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                    comprobar = transferenciasMotivoDao.accionInvTransferenciasMotivo(invTransferenciaMotivo,
                            sisSuceso, accion);
                } else {
                    mensaje = "FSe encuentra creado el motivo de transferencia...";
                }
            }
            if (accion == 'M') {
                if (transferenciasMotivoDao.comprobarInvTransferenciaMotivo(
                        invTransferenciaMotivoTO.getUsrEmpresa(), invTransferenciaMotivoTO.getTmCodigo())) {
                    invTransferenciaMotivoAux = transferenciasMotivoDao.buscarInvTransferenciaMotivo(
                            invTransferenciaMotivoTO.getUsrEmpresa(), invTransferenciaMotivoTO.getTmCodigo());
                    invTransferenciaMotivo.setUsrFechaInserta(invTransferenciaMotivoAux.getUsrFechaInserta());
                    comprobar = transferenciasMotivoDao.accionInvTransferenciasMotivo(invTransferenciaMotivo,
                            sisSuceso, accion);
                } else {
                    mensaje = "FNo se encuentra creado la motivo de transferencia...";
                }
            }
        }
        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TEl motivo de transferencia: Código " + invTransferenciaMotivoTO.getTmCodigo() + ", se ha eliminado correctamente.";
            }
            if (accion == 'M') {
                mensaje = "TEl motivo de transferencia: Código " + invTransferenciaMotivoTO.getTmCodigo() + ", se ha modificado correctamente.";
            }
            if (accion == 'I') {
                mensaje = "TEl motivo de transferencia: Código " + invTransferenciaMotivoTO.getTmCodigo() + ", se ha guardado correctamente.";
            }
        }
        return mensaje;
    }

    @Override
    public String modificarEstadoInvTransferenciaMotivoTO(InvTransferenciaMotivoTO invTransferenciaMotivoTO, SisInfoTO sisInfoTO) throws Exception {
        ///// CREANDO Suceso
        susClave = invTransferenciaMotivoTO.getTmDetalle();
        susDetalle = "El motivo de transferencia: Detalle " + invTransferenciaMotivoTO.getTmDetalle() + (invTransferenciaMotivoTO.getTmInactivo() ? ", se ha inactivado correctamente" : ", se ha activado correctamente");
        susSuceso = "UPDATE";
        susTabla = "inventario.inv_consumos_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        ///// CREANDO CarPagosForma
        InvTransferenciasMotivo invTransferenciaMotivo = ConversionesInventario.convertirInvTransferenciasMotivoTO_InvTransferenciasMotivo(invTransferenciaMotivoTO);
        comprobar = false;
        InvTransferenciasMotivo invTransferenciaMotivoAux = null;
        if (transferenciasMotivoDao.comprobarInvTransferenciaMotivo(invTransferenciaMotivoTO.getUsrEmpresa(), invTransferenciaMotivoTO.getTmCodigo())) {
            invTransferenciaMotivoAux = transferenciasMotivoDao.buscarInvTransferenciaMotivo(
                    invTransferenciaMotivoTO.getUsrEmpresa(), invTransferenciaMotivoTO.getTmCodigo());
            invTransferenciaMotivo.setUsrFechaInserta(invTransferenciaMotivoAux.getUsrFechaInserta());
            comprobar = transferenciasMotivoDao.accionInvTransferenciasMotivo(invTransferenciaMotivo, sisSuceso, 'M');
        } else {
            mensaje = "FNo se encuentra creado la motivo de tranferencia...";
        }

        if (comprobar) {
            mensaje = "TEl motivo de transferencia: Código " + invTransferenciaMotivoTO.getTmCodigo() + (invTransferenciaMotivoTO.getTmInactivo() ? ", se ha inactivado correctamente" : ", se ha activado correctamente");
        }
        return mensaje;
    }

    /*Lista InvTransferenciaMotivoTO*/
    @Override
    public List<InvTransferenciaMotivoTO> getListaTransferenciaMotivoTO(String empresa, boolean incluirInactivos) throws Exception {
        return transferenciasMotivoDao.getListaTransferenciaMotivoTO(empresa, incluirInactivos);
    }

}
