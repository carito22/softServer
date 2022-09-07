/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import static ec.com.todocompu.ShrimpSoftUtils.UtilsArchivos.getRutaCompra;
import static ec.com.todocompu.ShrimpSoftUtils.UtilsArchivos.getRutaImagenProducto;
import static ec.com.todocompu.ShrimpSoftUtils.UtilsArchivos.guardarImagenCalidad;
import static ec.com.todocompu.ShrimpSoftUtils.UtilsArchivos.obtenerImagenCalidad;
import static ec.com.todocompu.ShrimpSoftUtils.UtilsArchivos.eliminarImagenCalidad;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Desarrollador1
 */
@Service
public class ArchivosService implements ArchivosServiceImpl {

    @Override
    public String guardarArchivo(String tipo, byte[] archivo, String empresa, String nombre) throws Exception {
        String ruta = "";
        switch (tipo.toUpperCase()) {
            case "COMPRA":
                ruta = getRutaCompra();
                break;
            case "PRODUCTO":
                ruta = getRutaImagenProducto();
                break;
        }
        return guardarImagenCalidad(ruta, empresa, archivo, nombre);
    }

    @Override
    public List<String> obtenerArchivo(String tipo, String empresa, String nombre) throws Exception {
        String ruta = "";
        switch (tipo.toUpperCase()) {
            case "COMPRA":
                ruta = getRutaCompra();
                break;
            case "PRODUCTO":
                ruta = getRutaImagenProducto() ;
                break;
        }
        return obtenerImagenCalidad(ruta, empresa, nombre);
    }

    @Override
    public String eliminarArchivo(String tipo, String empresa, String nombre) throws Exception {
        String ruta = "";
        switch (tipo.toUpperCase()) {
            case "COMPRA":
                ruta = getRutaCompra();
                break;
            case "PRODUCTO":
                ruta = getRutaImagenProducto();
                break;
        }        
         return eliminarImagenCalidad(ruta, empresa, nombre);
    }
}
