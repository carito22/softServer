/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import java.util.List;

/**
 *
 * @author Desarrollador1
 */
public interface ArchivosServiceImpl {

    public String guardarArchivo(String ruta, byte[] archivo, String empresa, String nombre) throws Exception;

    public String eliminarArchivo(String ruta, String empresa, String nombre) throws Exception;

    public List< String> obtenerArchivo(String ruta, String empresa, String nombre) throws Exception;
}
