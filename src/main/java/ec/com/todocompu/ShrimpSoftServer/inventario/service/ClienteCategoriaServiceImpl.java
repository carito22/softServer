package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClienteCategoriaDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCategoriaClienteComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteCategoria;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class ClienteCategoriaServiceImpl implements ClienteCategoriaService {

    @Autowired
    private ClienteCategoriaDao clienteCategoriaDao;

    private boolean comprobar = false;
    private String mensaje;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<InvClienteCategoriaTO> getInvClienteCategoriaTO(String empresa) throws Exception {
        return clienteCategoriaDao.getInvClienteCategoriaTO(empresa);
    }

    @Override
    public String accionInvClienteCategoria(InvClienteCategoriaTO invClienteCategoriaTO, char accion,
            SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        ///// CREANDO Suceso
        susClave = invClienteCategoriaTO.getCcCodigo() + " : " + invClienteCategoriaTO.getCcDetalle();
        if (accion == 'I') {
            susDetalle = "Se insertó categoría cliente " + invClienteCategoriaTO.getCcDetalle();
            susSuceso = "INSERT";
        }
        if (accion == 'M') {
            susDetalle = "Se modificó categoría cliente " + invClienteCategoriaTO.getCcDetalle();
            susSuceso = "UPDATE";
        }
        if (accion == 'E') {
            susDetalle = "Se eliminó categoría cliente " + invClienteCategoriaTO.getCcDetalle();
            susSuceso = "DELETE";
        }
        susTabla = "inventario.inv_cliente_categor";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        ///// CREANDO invClienteCategoria
        invClienteCategoriaTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
        InvClienteCategoria invClienteCategoria = ConversionesInventario
                .convertirInvClienteCategoriaTO_InvClienteCategoria(invClienteCategoriaTO);

        InvClienteCategoria invClienteCategoriaAux = null;

        if (accion == 'E') {
            ////// BUSCANDO existencia Categoría
            if (clienteCategoriaDao.comprobarInvClienteCategoria(invClienteCategoriaTO.getCcEmpresa(),
                    invClienteCategoriaTO.getCcCodigo())) {
                if (clienteCategoriaDao.comprobarEliminarInvClienteCategoria(invClienteCategoriaTO.getCcEmpresa(),
                        invClienteCategoriaTO.getCcCodigo())) {
                    invClienteCategoriaAux = clienteCategoriaDao.buscarInvClienteCategoria(
                            invClienteCategoriaTO.getCcEmpresa(), invClienteCategoriaTO.getCcCodigo());
                    invClienteCategoria.setUsrFechaInserta(invClienteCategoriaAux.getUsrFechaInserta());
                    comprobar = clienteCategoriaDao.accionInvClienteCategoria(invClienteCategoria, sisSuceso,
                            accion);
                } else {
                    mensaje = "FNo se puede eliminar, la Categoría ya tiene Clientes...";
                }
            } else {
                mensaje = "FNo se encuentra la Categoría cliente...";
            }
        }
        if (accion == 'M') {
            ////// BUSCANDO existencia Categoría
            if (clienteCategoriaDao.comprobarInvClienteCategoria(invClienteCategoriaTO.getCcEmpresa(),
                    invClienteCategoriaTO.getCcCodigo())) {
                invClienteCategoriaAux = clienteCategoriaDao.buscarInvClienteCategoria(
                        invClienteCategoriaTO.getCcEmpresa(), invClienteCategoriaTO.getCcCodigo());
                invClienteCategoria.setUsrFechaInserta(invClienteCategoriaAux.getUsrFechaInserta());
                comprobar = clienteCategoriaDao.accionInvClienteCategoria(invClienteCategoria, sisSuceso, accion);
            } else {
                mensaje = "FNo se encuentra la Categoría cliente...";
            }
        }
        if (accion == 'I') {
            ////// BUSCANDO existencia Categoría
            if (!clienteCategoriaDao.comprobarInvClienteCategoria(invClienteCategoriaTO.getCcEmpresa(),
                    invClienteCategoriaTO.getCcCodigo())) {
                invClienteCategoria.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                comprobar = clienteCategoriaDao.accionInvClienteCategoria(invClienteCategoria, sisSuceso, accion);
            } else {
                mensaje = "FYa existe la Categoría cliente...";
            }
        }

        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TLa categoría cliente:Código " + invClienteCategoriaTO.getCcCodigo() + ", se ha eliminado correctamente.";
            }
            if (accion == 'M') {
                mensaje = "TLa categoría cliente:Código " + invClienteCategoriaTO.getCcCodigo() + ", se ha modificado correctamente.";
            }
            if (accion == 'I') {
                mensaje = "TLa categoría cliente:Código " + invClienteCategoriaTO.getCcCodigo() + ", se ha guardado correctamente.";
            }
        }
        return mensaje;
    }

    @Override
    public List<InvCategoriaClienteComboTO> getListaCategoriaClienteComboTO(String empresa) throws Exception {
        return clienteCategoriaDao.getListaCategoriaClienteComboTO(empresa);
    }

}
