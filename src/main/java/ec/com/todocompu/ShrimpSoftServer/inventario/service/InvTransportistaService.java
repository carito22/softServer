/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransportistaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransportistaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CarolValdiviezo
 */
@Transactional
public interface InvTransportistaService {

    public boolean getTransportistaRepetido(String empresa, String codigo, String id, String nombre) throws Exception;

    public List<InvTransportistaTO> getListaInvTransportistaTO(String empresa, String busqueda, boolean incluirInactivo) throws Exception;

    public String insertarInvTransportistaTO(InvTransportistaTO invTransportistaTO, SisInfoTO sisInfoTO) throws Exception;

    public boolean eliminarInvTransportista(InvTransportistaPK pk, SisInfoTO sisInfoTO) throws Exception;

    public String modificarInvTransportistaTO(InvTransportistaTO invTransportistaTO, String codigoAnterior, SisInfoTO sisInfoTO) throws Exception;

    public boolean modificarEstadoInvTransportista(InvTransportistaPK invClientePK, boolean estado, SisInfoTO sisInfoTO) throws GeneralException;
}
