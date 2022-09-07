/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.web.PermisosEmpresaMenuTO;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 *
 * @author dev-out-03
 */
public interface SistemaWebServicio {

    public List<PermisosEmpresaMenuTO> getEmpresasPorUsuarioItemAngular(SisInfoTO usuario, String item) throws Exception;

    public Timestamp getFechaActual() throws Exception;

    public Date getFechaInicioMes() throws Exception;

    public Date getFechaFinMes() throws Exception;
    
    public String obtenerRutaImagen(String empresa) throws Exception;
}
