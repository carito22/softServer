/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import com.amazonaws.services.s3.model.Bucket;
import ec.com.todocompu.ShrimpSoftServer.amazons3.AmazonS3Crud;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClienteContratoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsString;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteContratoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaBodegasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvBodega;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContrato;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContratoDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContratoTipo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientesDirecciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.web.ComboGenericoTO;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author VALDIVIEZO
 */
@Service
public class ClienteContratoServiceImpl implements ClienteContratoService {

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private GenericoDao<InvClienteContratoDatosAdjuntos, Integer> invClienteContratoDatosAdjuntosDao;
    @Autowired
    private ClienteContratoDao clienteContratoDao;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private TipoContratoService tipoContratoService;
    @Autowired
    private BodegaService bodegaService;

    @Override
    public Map<String, Object> obtenerDatosParaContrato(Map<String, Object> map) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cliCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("cliCodigo"));
        boolean mostrarInactivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("mostrarInactivos"));//
        Integer secuencialCliente = UtilsJSON.jsonToObjeto(Integer.class, map.get("secuencial"));
        Timestamp fechaActual = sistemaWebServicio.getFechaActual();
        List<InvListaBodegasTO> listaBodegas = bodegaService.getListaBodegasTO(empresa, mostrarInactivos);
        List<InvClienteContratoTipo> listadoTipo = tipoContratoService.getListarInvTipoContrato(empresa);
        if (cliCodigo != null) {
            List<InvClientesDirecciones> listaInvClientesDirecciones = clienteService.getListInvClientesDirecciones(empresa, cliCodigo);
            campos.put("listaInvClientesDirecciones", listaInvClientesDirecciones);
        }
        if (secuencialCliente != null) {
            List<InvClienteContratoDatosAdjuntos> listaAdjuntos = clienteContratoDao.listarImagenesDeClienteContrato(secuencialCliente);
            campos.put("listaAdjuntos", listaAdjuntos);
        }

        campos.put("fechaActual", fechaActual);
        campos.put("listaBodegas", listaBodegas);
        campos.put("listadoTipo", listadoTipo);
        return campos;
    }

    @Override
    public List<InvClienteContratoTO> getListarInvClienteContratoTO(String empresa, String cliCodigo, String busqueda, String nroRegistro) throws Exception {
        return clienteContratoDao.getListaInvClienteContratoTO(empresa, cliCodigo, busqueda, nroRegistro);
    }

    @Override
    public List<InvClienteContratoDatosAdjuntos> listarImagenesDeClienteContrato(Integer secuencial) throws Exception {
        return clienteContratoDao.listarImagenesDeClienteContrato(secuencial);
    }

    @Override
    public InvClienteContrato obtenerInvClienteContrato(String empresa, String numeroContrato) throws Exception {
        return clienteContratoDao.obtenerInvClienteContrato(empresa, numeroContrato);
    }

    @Override
    public InvClienteContrato obtenerInvClienteContrato(String empresa, String cliCodigo, String ip) throws Exception {
        return clienteContratoDao.obtenerInvClienteContrato(empresa, cliCodigo, ip);
    }

    @Override
    public InvClienteContrato obtenerInvClienteContrato(Integer secuencial) throws Exception {
        return clienteContratoDao.obtenerInvClienteContrato(secuencial);
    }

    @Override
    public String insertarInvClienteContrato(InvClienteContrato invClienteContrato, List<InvClienteContratoDatosAdjuntos> listaImagenes, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        susClave = invClienteContrato.getCliNumeroContrato() + "";
        susDetalle = "Se insertó contrato del cliente con código:" + invClienteContrato.getInvCliente().getInvClientePK().getCliCodigo();
        susSuceso = "INSERT";
        susTabla = "inventario.inv_cliente_contrato";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        invClienteContrato.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));

        InvCliente cliente = clienteService.obtenerCliente(
                invClienteContrato.getInvCliente().getInvClientePK().getCliEmpresa(),
                invClienteContrato.getInvCliente().getInvClientePK().getCliCodigo());
        if (cliente != null) {
            invClienteContrato.setInvCliente(cliente);
            if (clienteContratoDao.insertarInvClienteContrato(invClienteContrato, sisSuceso)) {
                actualizarImagenes(listaImagenes, invClienteContrato);
                mensaje = "TEl contrato de cliente con identificación: " + invClienteContrato.getInvCliente().getCliIdNumero() + ", se ha guardado correctamente.";
                //GUARDAR CONTRATO EN WISPRO

                //********************
            } else {
                mensaje = "FNo se pudo ingresar el registro";
            }
        } else {
            mensaje = "FEl cliente seleccionado no existe";
        }

        return mensaje;
    }

    @Override
    public String modificarInvClienteContrato(InvClienteContrato invClienteContrato, List<InvClienteContratoDatosAdjuntos> listaImagenes, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        InvClienteContrato contrato = clienteContratoDao.obtenerPorId(InvClienteContrato.class, invClienteContrato.getCliSecuencial());
        if (contrato != null) {
            susDetalle = "Se modificó contrato del cliente con código:" + invClienteContrato.getInvCliente().getInvClientePK().getCliCodigo();
            susClave = invClienteContrato.getCliSecuencial() + "";
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_cliente_contrato";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (clienteContratoDao.modificarInvClienteContrato(invClienteContrato, sisSuceso)) {
                actualizarImagenes(listaImagenes, invClienteContrato);
                mensaje = "TEl contrato de cliente con identificación: " + invClienteContrato.getInvCliente().getCliIdNumero() + ", se ha modificado correctamente.";
            } else {
                mensaje = "FHubo un error al modificar el registro. Intente de nuevo o contacte con el administrador";
            }
        } else {
            mensaje = "FEl contrato que desea modificar ya no existe";
        }

        return mensaje;
    }

    @Override
    public String eliminarInvClienteContrato(Integer secuencial, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        InvClienteContrato contrato = clienteContratoDao.obtenerPorId(InvClienteContrato.class, secuencial);
        if (contrato != null) {
            susDetalle = "Se eliminó contrato del cliente con código:" + contrato.getInvCliente().getInvClientePK().getCliCodigo();
            susClave = secuencial + "";
            susSuceso = "DELETE";
            susTabla = "inventario.inv_cliente_contrato";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (clienteContratoDao.eliminarInvClienteContrato(contrato, sisSuceso)) {
                mensaje = "TEl contrato de cliente con identificación: " + contrato.getInvCliente().getCliIdNumero() + ", se ha eliminado correctamente.";
            } else {
                mensaje = "FHubo un error al eliminar el registro";
            }
        } else {
            mensaje = "FEl contrato que desea eliminar ya no existe";
        }

        return mensaje;
    }

    public boolean actualizarImagenes(List<InvClienteContratoDatosAdjuntos> listado, InvClienteContrato invClienteContrato) throws Exception {

        List<InvClienteContratoDatosAdjuntos> listAdjuntosEnLaBase = clienteContratoDao.listarImagenesDeClienteContrato(invClienteContrato.getCliSecuencial());
        if (listado != null && !listado.isEmpty()) {
            if (listAdjuntosEnLaBase.size() > 0) {
                listado.forEach((item) -> {//dejar las que tengo que eliminar(están enla base pero no vienen del cliente)
                    listAdjuntosEnLaBase.removeIf(n -> (n.getAdjSecuencial().equals(item.getAdjSecuencial())));
                });
                if (listAdjuntosEnLaBase.size() > 0) {
                    listAdjuntosEnLaBase.forEach((itemEliminar) -> {
                        invClienteContratoDatosAdjuntosDao.eliminar(itemEliminar);
                        AmazonS3Crud.eliminarArchivo(itemEliminar.getAdjBucket(), itemEliminar.getAdjClaveArchivo());
                    });
                }
            }
            insertarImagenes(listado, invClienteContrato);
        }

        return true;
    }

    public boolean insertarImagenes(List<InvClienteContratoDatosAdjuntos> listado, InvClienteContrato invClienteContrato) throws Exception {
        String bucket = sistemaWebServicio.obtenerRutaImagen(invClienteContrato.getInvCliente().getInvClientePK().getCliEmpresa());
        Bucket b = AmazonS3Crud.crearBucket(bucket);
        if (b != null) {
            for (InvClienteContratoDatosAdjuntos datoAdjunto : listado) {
                if (datoAdjunto.getAdjSecuencial() == null) {
                    ComboGenericoTO combo = UtilsString.completarDatosAmazon(datoAdjunto.getAdjClaveArchivo(), datoAdjunto.getImagenString());
                    InvClienteContratoDatosAdjuntos invAdjunto = new InvClienteContratoDatosAdjuntos();
                    String nombre = UtilsString.generarNombreAmazonS3() + "." + combo.getClave();
                    String carpeta = "cliente_contrato/" + invClienteContrato.getCliSecuencial() + "/";
                    invAdjunto.setInvClienteContrato(invClienteContrato);
                    invAdjunto.setAdjBucket(bucket);
                    invAdjunto.setAdjTipo(datoAdjunto.getAdjTipo());
                    invAdjunto.setAdjExtension(datoAdjunto.getAdjExtension());
                    invAdjunto.setAdjClaveArchivo(carpeta + nombre);
                    invAdjunto.setAdjUrlArchivo("https://" + bucket + ".s3.us-east-1.amazonaws.com/" + carpeta + nombre);
                    invClienteContratoDatosAdjuntosDao.insertar(invAdjunto);
                    AmazonS3Crud.subirArchivo(bucket, carpeta + nombre, datoAdjunto.getImagenString(), combo.getValor());
                }
            }
        } else {
            throw new GeneralException("Error al crear contenedor de imágenes.");
        }
        return true;
    }

    @Override
    public Map<String, Object> validarExistenciasDatos(List<InvClienteContrato> contratos, String empresa) throws Exception {
        //validar existencia cliente, establecimiento,bodega y tipo contrato
        Map<String, Object> map = new HashMap<>();
        ArrayList<String> listaMensajesEnviar = new ArrayList<>();
        List<InvClienteContrato> correctos = new ArrayList<>();

        if (contratos != null && !contratos.isEmpty()) {
            for (int i = 0; i < contratos.size(); i++) {
                //validar numero contrato
                InvClienteContrato contrato = clienteContratoDao.obtenerInvClienteContrato(empresa, contratos.get(i).getCliNumeroContrato());
                if (contrato == null) {
                    //cliente
                    InvCliente cliente = clienteService.obtenerInvClientePorCedulaRuc(empresa, contratos.get(i).getInvCliente().getCliIdNumero());
                    if (cliente == null) {
                        listaMensajesEnviar.add("FEl cliente:<strong class='pl-2'>" + contratos.get(i).getInvCliente().getCliIdNumero() + " </strong> no existe.");
                    } else {
                        InvClienteContrato contratoIp = clienteContratoDao.obtenerInvClienteContrato(empresa, cliente.getInvClientePK().getCliCodigo(), contratos.get(i).getCliDireccionIp());
                        if (contratoIp == null) {
                            contratos.get(i).setInvCliente(cliente);
                            //establecimiento 
                            boolean existeEstablecimiento = false;
                            List<InvClientesDirecciones> listaEstabl = clienteService.getListInvClientesDirecciones(empresa, cliente.getInvClientePK().getCliCodigo());
                            if (listaEstabl != null && listaEstabl.size() > 0) {
                                for (InvClientesDirecciones establecimiento : listaEstabl) {
                                    if (contratos.get(i).getCliEstablecimiento() != null && contratos.get(i).getCliEstablecimiento().equals(establecimiento.getDirCodigo())) {
                                        existeEstablecimiento = true;
                                    }
                                }
                            }

                            if (!existeEstablecimiento) {
                                listaMensajesEnviar.add("FEl establecimiento: <strong class='pl-2'>"
                                        + contratos.get(i).getCliEstablecimiento()
                                        + " </strong> del cliente:"
                                        + contratos.get(i).getInvCliente().getCliIdNumero()
                                        + " no existe.<br>");
                            } else {
                                //bodega 
                                InvBodega invBodegaAux = bodegaService.buscarInvBodega(empresa, contratos.get(i).getInvBodega().getInvBodegaPK().getBodCodigo());
                                if (invBodegaAux == null) {
                                    listaMensajesEnviar.add("FLa bodega:<strong class='pl-2'>" + contratos.get(i).getInvBodega().getInvBodegaPK().getBodCodigo() + " </strong> no existe.");
                                } else {
                                    //tipo contrato
                                    InvClienteContratoTipo tipo = tipoContratoService.obtenerInvTipoContrato(empresa, contratos.get(i).getInvClienteContratoTipo().getInvClienteContratoTipoPK().getCliCodigo());
                                    if (tipo == null) {
                                        listaMensajesEnviar.add("FEl tipo de contrato: <strong class='pl-2'>"
                                                + contratos.get(i).getInvClienteContratoTipo().getInvClienteContratoTipoPK().getCliCodigo()
                                                + " </strong> del cliente:"
                                                + contratos.get(i).getInvCliente().getCliIdNumero()
                                                + " no existe.");
                                    } else {
                                        correctos.add(contratos.get(i));
                                    }
                                }
                            }
                        } else {
                            listaMensajesEnviar.add("FEl cliente:<strong class='pl-2'>" + contratos.get(i).getInvCliente().getCliIdNumero() + " </strong> con dirección IP:<strong class='pl-2'>" + contratos.get(i).getCliDireccionIp() + "</strong> ya se encuentra registrado.");
                        }
                    }
                } else {
                    listaMensajesEnviar.add("FEl número de contraro: <strong class='pl-2'>" + contratos.get(i).getCliNumeroContrato() + " </strong> ya se encuentra registrado.");
                }
            }
        }

        map.put("listaMensajesEnviar", listaMensajesEnviar);
        map.put("correctos", correctos);
        return map;
    }

    @Override
    public String modificarIPInvClienteContrato(Integer secuencial, String ip, SisInfoTO sisInfoTO) throws Exception {
        String mensaje = "";
        InvClienteContrato contrato = clienteContratoDao.obtenerPorId(InvClienteContrato.class, secuencial);
        String ipAux = contrato.getCliDireccionIp();
        if (contrato != null) {
            contrato.setCliDireccionIp(ip);
            susDetalle = "Se modificó contrato del cliente con código:" + contrato.getInvCliente().getInvClientePK().getCliCodigo();
            susClave = secuencial + "";
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_cliente_contrato";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (clienteContratoDao.modificarInvClienteContrato(contrato, sisSuceso)) {
                mensaje = "TSe modificó correctamente la IP: " + ipAux + " del cliente con identificación " + contrato.getInvCliente().getCliIdNumero() + " por la siguiente IP: " + ip;
            } else {
                mensaje = "FHubo un error al modificar el registro. Intente de nuevo o contacte con el administrador";
            }
        } else {
            mensaje = "FEl contrato que desea modificar ya no existe";
        }

        return mensaje;
    }

}
