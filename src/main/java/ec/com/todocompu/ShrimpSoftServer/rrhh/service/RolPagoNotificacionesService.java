/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRolPagoNotificaciones;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author mario
 */
@Transactional
public interface RolPagoNotificacionesService {

    public List<RhRolPagoNotificaciones> listarRolPagoNotificaciones(String contable, String fechaInicio, String fechaFin, String empresa) throws Exception;
    
}
