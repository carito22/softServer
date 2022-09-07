/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransportistaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvNumeracionVarios;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransportista;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransportistaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

/**
 *
 * @author CarolValdiviezo
 */
public interface TransportistaDao extends GenericDao<InvTransportista, InvTransportistaPK> {

    public List<InvTransportistaTO> getListaInvTransportistaTO(String empresa, String busqueda, boolean incluirInactivo) throws Exception;

    public boolean getTransportistaRepetido(String empresa, String codigo, String id, String nombre) throws Exception;

    public boolean modificarInvTransportistaLlavePrincipal(InvTransportista invTransportistaEliminar, InvTransportista invTransportista, SisSuceso sisSuceso) throws Exception;

    public String getInvProximaNumeracionTransportista(String empresa, InvTransportistaTO invTransportistaTO) throws Exception;

    public InvTransportista buscarInvTransportista(String empresa, String codigo) throws Exception;

    public boolean eliminarInvTransportista(InvTransportista invTransportista, SisSuceso sisSuceso) throws Exception;

    public boolean insertarInvTransportista(InvTransportista invTransportista, SisSuceso sisSuceso, InvNumeracionVarios invNumeracionVarios) throws Exception;

    public boolean modificarInvTransportista(InvTransportista invTransportista, SisSuceso sisSuceso) throws Exception;

}
