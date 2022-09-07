/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.NumeracionVariosDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.TransportistaDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransportistaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvNumeracionVarios;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransportista;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransportistaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CarolValdiviezo
 */
@Service
public class InvTransportistaServiceImpl implements InvTransportistaService {

    @Autowired
    private GenericoDao<InvTransportista, InvTransportistaPK> invTransportistaDaoCriteria;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private TransportistaDao transportistaDao;
    @Autowired
    private NumeracionVariosDao numeracionVariosDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<InvTransportistaTO> getListaInvTransportistaTO(String empresa, String busqueda, boolean incluirInactivo) throws Exception {
        return transportistaDao.getListaInvTransportistaTO(empresa, busqueda, incluirInactivo);
    }

    @Override
    public boolean getTransportistaRepetido(String empresa, String codigo, String id, String nombre) throws Exception {
        return transportistaDao.getTransportistaRepetido(empresa, codigo, id, nombre);
    }

    @Override
    public String insertarInvTransportistaTO(InvTransportistaTO invTransportistaTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        String codigoGenerado = "";
        if (invTransportistaTO.getTransCodigo().trim().isEmpty()) {
            invTransportistaTO.setTransCodigo(transportistaDao.getInvProximaNumeracionTransportista(invTransportistaTO.getTransEmpresa(), invTransportistaTO));
            codigoGenerado = invTransportistaTO.getTransCodigo();
        }
        if (transportistaDao.buscarInvTransportista(invTransportistaTO.getTransEmpresa(), invTransportistaTO.getTransCodigo()) == null) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = invTransportistaTO.getTransCodigo();
            susDetalle = "Se ingresó el transportista " + invTransportistaTO.getTransNombres() + ", con código: " + invTransportistaTO.getTransCodigo();
            susSuceso = "INSERT";
            susTabla = "inventario.inv_transportista";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
            invTransportistaTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
            InvTransportista invTransportista = ConversionesInventario.convertirInvTransportistaTO_InvTransportista(invTransportistaTO);
            ///// BUSCAR POR ID
            String numeroId = (invTransportistaTO.getTransIdNumero().trim().isEmpty() ? "" : "'" + invTransportistaTO.getTransIdNumero() + "'");
            String nombres = (invTransportistaTO.getTransNombres().trim().isEmpty() ? "" : "'" + invTransportistaTO.getTransNombres() + "'");
            String empresa = "'" + invTransportistaTO.getTransEmpresa() + "'";
            boolean var = transportistaDao.getTransportistaRepetido(empresa, "", numeroId, "");

            if (var && !("F".equals(invTransportistaTO.getTransIdTipo().toString()) && "9999999999999".equals(invTransportistaTO.getTransIdNumero()))) {
                retorno = "FEl ID del transportista ya existe en los registros.\nIntente ingresando otro ID...";
            } else ///// BUSCAR POR NOMBRE
            if (transportistaDao.getTransportistaRepetido(empresa, "", "", nombres)) {
                retorno = "FEl nombre del transportista ya existe en los registros.\nIntente ingresando otro Nombre...";
            } else {
                retorno = "T";
            }

            if (retorno.charAt(0) == 'T') {
                InvNumeracionVarios invNumeracionVarios = numeracionVariosDao.obtenerPorId(InvNumeracionVarios.class, invTransportistaTO.getTransEmpresa());
                if (invNumeracionVarios == null && codigoGenerado.trim().isEmpty()) {
                    invNumeracionVarios = null;
                } else if (invNumeracionVarios == null && !codigoGenerado.trim().isEmpty()) {
                    invNumeracionVarios = new InvNumeracionVarios(invTransportistaTO.getTransEmpresa(), "00000", "00000", codigoGenerado, invTransportistaTO.getTransEmpresa());
                } else if (invNumeracionVarios != null && codigoGenerado.trim().isEmpty()) {
                    invNumeracionVarios = null;
                } else if (invNumeracionVarios != null && !codigoGenerado.trim().isEmpty()) {
                    invNumeracionVarios.setNumClientes(codigoGenerado);
                }
                if (transportistaDao.insertarInvTransportista(invTransportista, sisSuceso, invNumeracionVarios)) {
                    retorno = "T<html>Se ha guardado el siguiente transportista:<br><br>Código: <font size = 5>"
                            + invTransportista.getInvTransportistaPK().getTransCodigo().trim()
                            + "</font>.<br>Nombre: <font size = 5>" + invTransportista.getTransNombres().trim()
                            + "</font>.</html>";
                } else {
                    retorno = "FHubo un error al ingresar al transportista...\nIntente de nuevo o contacte con el administrador";
                }
            }
        } else {
            retorno = "FEl código del transportista que va a ingresar ya existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public boolean eliminarInvTransportista(InvTransportistaPK pk, SisInfoTO sisInfoTO) throws Exception {
        InvTransportista invTransportista = invTransportistaDaoCriteria.obtener(InvTransportista.class, pk);
        if (invTransportista != null) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = invTransportista.getUsrCodigo();
            susDetalle = "Se eliminó el cliente " + invTransportista.getTransNombres() + ", con código: " + invTransportista.getInvTransportistaPK().getTransCodigo();
            susSuceso = "DELETE";
            susTabla = "inventario.inv_cliente";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            //ELIMINAR
            transportistaDao.eliminar(invTransportista);
            sucesoDao.insertar(sisSuceso);
            return true;

        } else {
            throw new GeneralException("El cliente ya no esta disponible.");
        }
    }

    @Override
    public String modificarInvTransportistaTO(InvTransportistaTO invTransportistaTO, String codigoAnterior, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        InvTransportista invTransportistaAux = null;
        if (codigoAnterior.trim().isEmpty()) {
            invTransportistaAux = transportistaDao.buscarInvTransportista(invTransportistaTO.getTransEmpresa(), invTransportistaTO.getTransCodigo());
        } else {
            invTransportistaAux = transportistaDao.buscarInvTransportista(invTransportistaTO.getTransEmpresa(), codigoAnterior);
        }

        String empresa = "'" + invTransportistaTO.getTransEmpresa() + "'";
        String codigo = (invTransportistaTO.getTransCodigo().trim().isEmpty() ? "" : "'" + invTransportistaTO.getTransCodigo() + "'");
        String id = (invTransportistaTO.getTransIdNumero().trim().isEmpty() ? "" : "'" + invTransportistaTO.getTransIdNumero() + "'");
        String nombres = (invTransportistaTO.getTransNombres().trim().isEmpty() ? "" : "'" + invTransportistaTO.getTransNombres() + "'");
        if (codigoAnterior.trim().isEmpty()) {
            ///// BUSCAR POR ID
            boolean var = transportistaDao.getTransportistaRepetido(empresa, codigo, id, "");
            if (var && !("F".equals(invTransportistaTO.getTransIdTipo().toString()) && "9999999999999".equals(invTransportistaTO.getTransIdNumero()))) {
                retorno = "FEl ID del transportista ya existe en los registros.\nIntente ingresando otro ID...";
            } else ///// BUSCAR POR NOMBRE
            if (transportistaDao.getTransportistaRepetido(empresa, codigo, "", nombres)) {
                retorno = "FEl nombre del transportista ya existe en los registros.\nIntente ingresando otro Nombre...";
            } else //// BUSCAR POR RAZON SOCIAL
            {
                retorno = "T";
            }
        } else {
            ///// BUSCAR POR ID
            boolean var = transportistaDao.getTransportistaRepetido(empresa, (codigoAnterior.trim().isEmpty() ? "" : "'" + codigoAnterior + "'"), codigo, "");
            if (var && !("F".equals(invTransportistaTO.getTransIdTipo().toString()) && "9999999999999".equals(invTransportistaTO.getTransCodigo()))) {
                retorno = "FEl ID del transportista ya existe en los registros.\nIntente ingresando otro ID...";
            } else ///// BUSCAR POR NOMBRE
            if (transportistaDao.getTransportistaRepetido(empresa, (codigoAnterior.trim().isEmpty() ? "" : "'" + codigoAnterior + "'"), "", nombres)) {
                retorno = "FEl Nombre del transportista ya existe en los registros.\nIntente ingresando otro Nombre...";
            } else //// BUSCAR POR RAZON SOCIAL
            {
                retorno = "T";
            }
        }

        if (retorno.charAt(0) == 'T') {
            if (invTransportistaAux != null && codigoAnterior.trim().isEmpty()) {
                /// PREPARANDO OBJETO SISSUCESO
                susClave = invTransportistaTO.getTransCodigo();
                susDetalle = "Se modificó el transportista " + invTransportistaTO.getTransNombres() + ", con código: " + invTransportistaTO.getTransCodigo();
                susSuceso = "UPDATE";
                susTabla = "inventario.inv_transportista";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A ENTITY
                invTransportistaTO.setUsrCodigo(invTransportistaAux.getUsrCodigo());
                invTransportistaTO.setUsrFechaInserta(UtilsValidacion.fecha(invTransportistaAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                InvTransportista invTransportista = ConversionesInventario.convertirInvTransportistaTO_InvTransportista(invTransportistaTO);

                if (transportistaDao.modificarInvTransportista(invTransportista, sisSuceso)) {
                    retorno = "TEl transportista se modificó correctamente...";
                } else {
                    retorno = "FHubo un error al modificar el transportista...\nIntente de nuevo o contacte con el administrador";
                }

            } else if (invTransportistaAux != null && !codigoAnterior.trim().isEmpty()) {
                /// PREPARANDO OBJETO SISSUCESO
                susClave = invTransportistaTO.getTransCodigo();
                susDetalle = "Se modificó el código " + codigoAnterior + " del transportista " + invTransportistaTO.getTransNombres();
                susSuceso = "UPDATE";
                susTabla = "inventario.inv_transportista";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO A
                /// ENTITY
                invTransportistaTO.setUsrCodigo(invTransportistaAux.getUsrCodigo());
                invTransportistaTO.setUsrFechaInserta(UtilsValidacion.fecha(invTransportistaAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                InvTransportista invTransportista = ConversionesInventario.convertirInvTransportistaTO_InvTransportista(invTransportistaTO);

                if (invTransportistaAux.getInvTransportistaPK().getTransCodigo().equals(invTransportista.getInvTransportistaPK().getTransCodigo())) {
                    if (transportistaDao.modificarInvTransportista(invTransportista, sisSuceso)) {
                        retorno = "TEl transportista se modificó correctamente...";
                    } else {
                        retorno = "FHubo un error al modificar el transportista...\nIntente de nuevo o contacte con el administrador";
                    }
                } else if (transportistaDao.modificarInvTransportistaLlavePrincipal(invTransportistaAux, invTransportista, sisSuceso)) {
                    retorno = "TEl transportista se modificó correctamente...";
                } else {
                    retorno = "FHubo un error al modificar el transportista...\nIntente de nuevo o contacte con el administrador";
                }

            } else if (invTransportistaAux == null && codigoAnterior.trim().isEmpty()) {
                retorno = "FEl código del transportista que va a modificar ya no está disponible...\nIntente con otro...";
            } else {
                /// PREPARANDO OBJETO SISSUCESO
                susClave = invTransportistaTO.getTransCodigo();
                susDetalle = "Se modificó el código " + codigoAnterior + " del transportista " + invTransportistaTO.getTransNombres() + " por el código " + invTransportistaTO.getTransCodigo();
                susSuceso = "UPDATE";
                susTabla = "inventario.inv_transportista";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                /// PONIENDOLE FECHA AL METODO Y CONVIRTIENDO DE TO
                /// A ENTITY
                invTransportistaTO.setUsrCodigo(invTransportistaAux.getUsrCodigo());
                invTransportistaTO.setUsrFechaInserta(UtilsValidacion.fecha(invTransportistaAux.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
                InvTransportista invTransportista = ConversionesInventario.convertirInvTransportistaTO_InvTransportista(invTransportistaTO);

                if (invTransportistaAux.getInvTransportistaPK().getTransCodigo().equals(invTransportista.getInvTransportistaPK().getTransCodigo())) {
                    if (transportistaDao.modificarInvTransportista(invTransportista, sisSuceso)) {
                        retorno = "TEl transportista se modificó correctamente...";
                    } else {
                        retorno = "FHubo un error al modificar el transportista...\nIntente de nuevo o contacte con el administrador";
                    }
                } else if (transportistaDao.modificarInvTransportistaLlavePrincipal(invTransportistaAux, invTransportista, sisSuceso)) {
                    retorno = "TEl transportista se modificó correctamente...";
                } else {
                    retorno = "FHubo un error al modificar el transportista...\nIntente de nuevo o contacte con el administrador";
                }
            }
        }
        return retorno;
    }

    @Override
    public boolean modificarEstadoInvTransportista(InvTransportistaPK invClientePK, boolean estado, SisInfoTO sisInfoTO) throws GeneralException {
        InvTransportista invTransportista = invTransportistaDaoCriteria.obtener(InvTransportista.class, invClientePK);
        if (invTransportista != null) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = invTransportista.getUsrCodigo();
            susDetalle = "Se modificó el estado de el transportista " + invTransportista.getTransNombres() + ", con código: " + invTransportista.getInvTransportistaPK().getTransCodigo();
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_transportista";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            //ACTUALIZAR ESTADO
            invTransportista.setTransInactivo(estado);
            transportistaDao.actualizar(invTransportista);
            sucesoDao.insertar(sisSuceso);
            return true;
        } else {
            throw new GeneralException("El transportista ya no esta disponible.");
        }
    }
}
