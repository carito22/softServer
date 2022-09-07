package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCompraMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCompraMotivoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class ComprasMotivoServiceImpl implements ComprasMotivoService {

    @Autowired
    private ComprasMotivoDao comprasMotivoDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<InvCompraMotivoComboTO> getListaCompraMotivoComboTO(String empresa, boolean filtrarInactivos)
            throws Exception {
        return comprasMotivoDao.getListaCompraMotivoComboTO(empresa, filtrarInactivos);
    }

    @Override
    public InvComprasMotivo getInvComprasMotivo(String empresa, String cmCodigo) throws Exception {
        return comprasMotivoDao.getInvComprasMotivo(empresa, cmCodigo);
    }

    @Override
    public InvComprasMotivoTO getInvComprasMotivoTO(String empresa, String cmCodigo) throws Exception {
        return comprasMotivoDao.getInvComprasMotivoTO(empresa, cmCodigo);
    }

    @Override
    public List<InvComprasMotivoTO> getListaInvComprasMotivoTO(String empresa, boolean soloActivos) throws Exception {
        return comprasMotivoDao.getListaInvComprasMotivoTO(empresa, soloActivos);
    }

    @Override
    public List<InvCompraMotivoTablaTO> getListaInvComprasMotivoTablaTO(String empresa) throws Exception {
        return comprasMotivoDao.getListaInvComprasMotivoTablaTO(empresa);
    }

    @Override
    public List<InvComprasMotivoTO> listarInvComprasMotivoTOAjusteInv(String empresa, boolean soloActivos) throws Exception {
        return comprasMotivoDao.listarInvComprasMotivoTOAjusteInv(empresa, soloActivos);
    }

    @Override
    public String insertarInvComprasMotivoTO(InvComprasMotivoTO invComprasMotivoTO, SisInfoTO sisInfoTO)
            throws Exception {
        String retorno = "";
        if (comprasMotivoDao.getInvComprasMotivo(invComprasMotivoTO.getCmEmpresa(),
                invComprasMotivoTO.getCmCodigo()) == null) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = invComprasMotivoTO.getTipCodigo();
            susDetalle = "El motivo de compra: Detalle " + invComprasMotivoTO.getCmDetalle() + ", se ha guardado correctamente";
            susSuceso = "INSERT";
            susTabla = "inventario.inv_compras_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            invComprasMotivoTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());

            InvComprasMotivo invComprasMotivo = ConversionesInventario
                    .convertirInvComprasMotivoTO_InvComprasMotivo(invComprasMotivoTO);

            if (comprasMotivoDao.insertarInvComprasMotivo(invComprasMotivo, sisSuceso)) {
                retorno = "TEl motivo de compra: Código " + invComprasMotivoTO.getCmCodigo() + ", se ha guardado correctamente.";
            } else {
                retorno = "FHubo un error al guardar el motivo de compra...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl motivo de compra que va a ingresar ya existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String modificarInvComprasMotivoTO(InvComprasMotivoTO invComprasMotivoTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        InvComprasMotivo invComprasMotivoAux = comprasMotivoDao.getInvComprasMotivo(invComprasMotivoTO.getCmEmpresa(), invComprasMotivoTO.getCmCodigo());
        if (invComprasMotivoAux != null) {
            // PREPARANDO OBJETO SISSUCESO
            susClave = invComprasMotivoTO.getTipCodigo();
            susDetalle = "El motivo de compra: Detalle " + invComprasMotivoTO.getCmDetalle() + ", se ha modificado correctamente";
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_compras_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            invComprasMotivoTO.setUsrCodigo(invComprasMotivoAux.getUsrCodigo());
            invComprasMotivoTO.setUsrFechaInserta(UtilsValidacion.fecha(invComprasMotivoAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            InvComprasMotivo invComprasMotivo = ConversionesInventario.convertirInvComprasMotivoTO_InvComprasMotivo(invComprasMotivoTO);
            if (comprasMotivoDao.modificarInvComprasMotivo(invComprasMotivo, sisSuceso)) {
                retorno = "TEl motivo de compra: Código " + invComprasMotivoTO.getCmCodigo() + ", se ha modificado correctamente.";
            } else {
                retorno = "FHubo un error al modificar el motivo de compra...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl motivo de compra que va a modificar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String modificarEstadoInvComprasMotivoTO(InvComprasMotivoTO invComprasMotivoTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        InvComprasMotivo invComprasMotivoAux = comprasMotivoDao.getInvComprasMotivo(invComprasMotivoTO.getCmEmpresa(), invComprasMotivoTO.getCmCodigo());
        if (invComprasMotivoAux != null) {
            // PREPARANDO OBJETO SISSUCESO
            susClave = invComprasMotivoTO.getTipCodigo();
            susDetalle = "El motivo de compra: Detalle " + invComprasMotivoTO.getCmDetalle() + (invComprasMotivoTO.getCmInactivo() ? ", se ha inactivado correctamente" : ", se ha activado correctamente");
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_compras_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            invComprasMotivoTO.setUsrCodigo(invComprasMotivoAux.getUsrCodigo());
            invComprasMotivoTO.setUsrFechaInserta(UtilsValidacion.fecha(invComprasMotivoAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
            InvComprasMotivo invComprasMotivo = ConversionesInventario.convertirInvComprasMotivoTO_InvComprasMotivo(invComprasMotivoTO);
            if (comprasMotivoDao.modificarInvComprasMotivo(invComprasMotivo, sisSuceso)) {
                retorno = "TEl motivo de compra: Código " + invComprasMotivoTO.getCmCodigo() + (invComprasMotivoTO.getCmInactivo() ? ", se ha inactivado correctamente" : ", se ha activado correctamente");
            } else {
                retorno = "FHubo un error al modificar el motivo de compra...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FEl motivo de compra que va a modificar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String eliminarInvComprasMotivoTO(InvComprasMotivoTO invComprasMotivoTO, SisInfoTO sisInfoTO)
            throws Exception {
        boolean seguir = comprasMotivoDao.retornoContadoEliminarComprasMotivo(invComprasMotivoTO.getCmEmpresa(),
                invComprasMotivoTO.getCmCodigo());
        InvComprasMotivo invComprasMotivo = null;
        String retorno = "";
        if (seguir) {
            invComprasMotivoTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            InvComprasMotivo invComprasMotivoAux = comprasMotivoDao
                    .getInvComprasMotivo(invComprasMotivoTO.getCmEmpresa(), invComprasMotivoTO.getCmCodigo());
            // SUCESO
            susClave = invComprasMotivoTO.getTipCodigo();
            susTabla = "inventario.inv_compras_motivo";
            susDetalle = "El motivo de compra: Detalle " + invComprasMotivoTO.getCmDetalle() + ", se ha eliminado correctamente";
            susSuceso = "DELETE";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);

            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            invComprasMotivoTO.setUsrCodigo(invComprasMotivoAux.getUsrCodigo());
            invComprasMotivoTO.setUsrFechaInserta(
                    UtilsValidacion.fecha(invComprasMotivoAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));

            invComprasMotivo = ConversionesInventario
                    .convertirInvComprasMotivoTO_InvComprasMotivo(invComprasMotivoTO);

            if (comprasMotivoDao.eliminarInvComprasMotivo(invComprasMotivo, sisSuceso)) {
                retorno = "TEl motivo de compra: Código " + invComprasMotivoTO.getCmCodigo() + ", se ha eliminado correctamente.";
            } else {
                retorno = "FHubo un error al eliminar el motivo de compra...\nIntente de nuevo o contacte con el administrador";
            }
        } else {
            retorno = "FNo se puede eliminar un motivo con movimientos";
        }
        return retorno;
    }

    @Override
    public Boolean comprobarInvComprasMotivo(String empresa, String motCodigo) throws Exception {
        return comprasMotivoDao.comprobarInvComprasMotivo(empresa, motCodigo);
    }

}
