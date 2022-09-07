/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteNotificaciones;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CarolValdiviezo
 */
@Transactional
public interface InvClienteNotificacionesService {

    public List<InvClienteNotificaciones> listarNotificacionesElectronicas(String empresa, String cliCodigo, String motivo, int limite, int posicion) throws Exception;
}
