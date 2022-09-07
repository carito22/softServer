package ec.com.todocompu.ShrimpSoftServer.sistema.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.amazonaws.services.s3.model.Bucket;
import ec.com.todocompu.ShrimpSoftServer.amazons3.AmazonS3Crud;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ComprasDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.MigracionesService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author dev-out-03
 */
@RestController
@RequestMapping("/todocompuWS/migracionesWebController")
public class MigracionesWebController {

    @Autowired
    private EnviarCorreoService envioCorreoService;
    @Autowired
    private MigracionesService migracionesService;
    @Autowired
    private ComprasDao comprasDao;
    @Autowired
    private ProductoDao productoDao;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;

    @RequestMapping("/migrarImagenesDeCompra")
    public RespuestaWebTO migrarImagenesDeCompra(@RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        List<String> empresas = UtilsJSON.jsonToList(String.class, map.get("empresas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RespuestaWebTO> mensajes = new ArrayList<>();
            if (empresas != null && !empresas.isEmpty()) {
                for (String empresa : empresas) {
                    RespuestaWebTO respEmpresa = new RespuestaWebTO();
                    try {
                        List<Integer> imagenesDeCompras = comprasDao.listaImagenesNoMigradas(empresa);
                        List<String> respuestas = new ArrayList<>();
                        if (imagenesDeCompras != null && !imagenesDeCompras.isEmpty()) {
                            String bucket = sistemaWebServicio.obtenerRutaImagen(empresa);
                            Bucket b = AmazonS3Crud.crearBucket(bucket);
                            if (b != null) {
                                for (Integer imagen : imagenesDeCompras) {
                                    try {
                                        String respuesta = migracionesService.migrarImagenDeCompra(imagen, bucket);
                                        respuestas.add(respuesta);
                                        respEmpresa.setExtraInfo(respuestas);
                                        respEmpresa.setOperacionMensaje("Migración para empresa: " + empresa);
                                        respEmpresa.setEstadoOperacion("success");
                                    } catch (Exception e) {
                                        respuestas.add(e.getMessage());
                                        respEmpresa.setExtraInfo(respuestas);
                                        respEmpresa.setOperacionMensaje("Migración para empresa: " + empresa);
                                        respEmpresa.setEstadoOperacion("success");
                                    }
                                }
                            } else {
                                respEmpresa.setEstadoOperacion("danger");
                                respEmpresa.setOperacionMensaje("Error al realizar migración. Empresa: " + empresa + ". Error al crear contenedor de imagenes.");
                            }
                        } else {
                            respEmpresa.setOperacionMensaje("No existen datos para migración. Empresa: " + empresa);
                            respEmpresa.setEstadoOperacion("warning");
                        }
                    } catch (Exception e) {
                        respEmpresa.setEstadoOperacion("danger");
                        respEmpresa.setOperacionMensaje("Error al realizar migración. Empresa: " + empresa + ". Error: " + e.getMessage());
                    }
                    mensajes.add(respEmpresa);
                }
                resp.setExtraInfo(mensajes);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            } else {
                resp.setOperacionMensaje("No existen empresas para realizar una migración de datos.");
            }
        } catch (GeneralException e) {
            resp.setOperacionMensaje(e.getMessage());
        } catch (Exception e) {
            resp.setOperacionMensaje("Ha ocurrido un error durante la migración de imágenes (" + e.getMessage() + ")");
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/verificarImagenesMigradas")
    public RespuestaWebTO verificarImagenesMigradas(@RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        List<String> empresas = UtilsJSON.jsonToList(String.class, map.get("empresas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RespuestaWebTO> mensajes = new ArrayList<>();
            if (empresas != null && !empresas.isEmpty()) {
                for (String empresa : empresas) {
                    RespuestaWebTO respEmpresa = new RespuestaWebTO();
                    try {
                        List<Integer> imagenesDeCompras = comprasDao.listaImagenesMigradas(empresa);
                        List<String> respuestas = new ArrayList<>();
                        if (imagenesDeCompras != null && !imagenesDeCompras.isEmpty()) {
                            for (Integer imagen : imagenesDeCompras) {
                                try {
                                    String respuesta = migracionesService.verificarImagenesMigradas(imagen);
                                    respuestas.add(respuesta);
                                    respEmpresa.setExtraInfo(respuestas);
                                    respEmpresa.setOperacionMensaje("Verificar migración para empresa: " + empresa);
                                    respEmpresa.setEstadoOperacion("success");
                                } catch (Exception e) {
                                    respuestas.add(e.getMessage());
                                    respEmpresa.setExtraInfo(respuestas);
                                    respEmpresa.setOperacionMensaje("Verificar migración para empresa: " + empresa);
                                    respEmpresa.setEstadoOperacion("success");
                                }
                            }
                        } else {
                            respEmpresa.setOperacionMensaje("No existen datos para verificar. Empresa: " + empresa);
                            respEmpresa.setEstadoOperacion("warning");
                        }
                    } catch (Exception e) {
                        respEmpresa.setEstadoOperacion("danger");
                        respEmpresa.setOperacionMensaje("Error al realizar la verificación. Empresa: " + empresa + ". Error: " + e.getMessage());
                    }
                    mensajes.add(respEmpresa);
                }
                resp.setExtraInfo(mensajes);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            } else {
                resp.setOperacionMensaje("No existen empresas para realizar una verificación de datos.");
            }
        } catch (GeneralException e) {
            resp.setOperacionMensaje(e.getMessage());
        } catch (Exception e) {
            resp.setOperacionMensaje("Ha ocurrido un error durante la migración de imágenes (" + e.getMessage() + ")");
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/migrarImagenesDeProducto")
    public RespuestaWebTO migrarImagenesDeProducto(@RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        List<String> empresas = UtilsJSON.jsonToList(String.class, map.get("empresas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RespuestaWebTO> mensajes = new ArrayList<>();
            if (empresas != null && !empresas.isEmpty()) {
                for (String empresa : empresas) {
                    RespuestaWebTO respEmpresa = new RespuestaWebTO();
                    try {
                        List<Integer> imagenes = productoDao.listaImagenesNoMigradas(empresa);
                        List<String> respuestas = new ArrayList<>();
                        if (imagenes != null && !imagenes.isEmpty()) {
                            String bucket = sistemaWebServicio.obtenerRutaImagen(empresa);
                            Bucket b = AmazonS3Crud.crearBucket(bucket);
                            if (b != null) {
                                for (Integer imagen : imagenes) {
                                    try {
                                        String respuesta = migracionesService.migrarImagenDeProducto(imagen, bucket);
                                        respuestas.add(respuesta);
                                        respEmpresa.setExtraInfo(respuestas);
                                        respEmpresa.setOperacionMensaje("Migración para empresa: " + empresa);
                                        respEmpresa.setEstadoOperacion("success");
                                    } catch (Exception e) {
                                        respuestas.add(e.getMessage());
                                        respEmpresa.setExtraInfo(respuestas);
                                        respEmpresa.setOperacionMensaje("Migración para empresa: " + empresa);
                                        respEmpresa.setEstadoOperacion("success");
                                    }
                                }
                            } else {
                                respEmpresa.setEstadoOperacion("danger");
                                respEmpresa.setOperacionMensaje("Error al realizar migración. Empresa: " + empresa + ". Error al crear contenedor de imagenes.");
                            }
                        } else {
                            respEmpresa.setOperacionMensaje("No existen datos para migración. Empresa: " + empresa);
                            respEmpresa.setEstadoOperacion("warning");
                        }
                    } catch (Exception e) {
                        respEmpresa.setEstadoOperacion("danger");
                        respEmpresa.setOperacionMensaje("Error al realizar migración. Empresa: " + empresa + ". Error: " + e.getMessage());
                    }
                    mensajes.add(respEmpresa);
                }
                resp.setExtraInfo(mensajes);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            } else {
                resp.setOperacionMensaje("No existen empresas para realizar una migración de datos.");
            }
        } catch (GeneralException e) {
            resp.setOperacionMensaje(e.getMessage());
        } catch (Exception e) {
            resp.setOperacionMensaje("Ha ocurrido un error durante la migración de imágenes (" + e.getMessage() + ")");
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/verificarImagenesProductos")
    public RespuestaWebTO verificarImagenesProductos(@RequestBody String json) throws Exception {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        List<String> empresas = UtilsJSON.jsonToList(String.class, map.get("empresas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<RespuestaWebTO> mensajes = new ArrayList<>();
            if (empresas != null && !empresas.isEmpty()) {
                for (String empresa : empresas) {
                    RespuestaWebTO respEmpresa = new RespuestaWebTO();
                    try {
                        List<Integer> imagenes = productoDao.listaImagenesMigradas(empresa);
                        List<String> respuestas = new ArrayList<>();
                        if (imagenes != null && !imagenes.isEmpty()) {
                            for (Integer imagen : imagenes) {
                                try {
                                    String respuesta = migracionesService.verificarImagenesProductos(imagen);
                                    respuestas.add(respuesta);
                                    respEmpresa.setExtraInfo(respuestas);
                                    respEmpresa.setOperacionMensaje("Verificar migración para empresa: " + empresa);
                                    respEmpresa.setEstadoOperacion("success");
                                } catch (Exception e) {
                                    respuestas.add(e.getMessage());
                                    respEmpresa.setExtraInfo(respuestas);
                                    respEmpresa.setOperacionMensaje("Verificar migración para empresa: " + empresa);
                                    respEmpresa.setEstadoOperacion("success");
                                }
                            }
                        } else {
                            respEmpresa.setOperacionMensaje("No existen datos para verificar. Empresa: " + empresa);
                            respEmpresa.setEstadoOperacion("warning");
                        }
                    } catch (Exception e) {
                        respEmpresa.setEstadoOperacion("danger");
                        respEmpresa.setOperacionMensaje("Error al realizar la verificación. Empresa: " + empresa + ". Error: " + e.getMessage());
                    }
                    mensajes.add(respEmpresa);
                }
                resp.setExtraInfo(mensajes);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            } else {
                resp.setOperacionMensaje("No existen empresas para realizar una verificación de datos.");
            }
        } catch (GeneralException e) {
            resp.setOperacionMensaje(e.getMessage());
        } catch (Exception e) {
            resp.setOperacionMensaje("Ha ocurrido un error durante la migración de imágenes (" + e.getMessage() + ")");
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }
}
