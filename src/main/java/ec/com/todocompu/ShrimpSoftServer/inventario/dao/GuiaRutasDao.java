/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRutas;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.math.BigDecimal;
import java.util.List;


public interface GuiaRutasDao extends GenericDao<InvGuiaRutas, Integer> {

    public boolean insertarInvRutasGuias(InvGuiaRutas invGuiaRutas, SisSuceso sisSuceso) throws Exception;
    
    public List<InvGuiaRutas> getListarInvGuiaRutas (String empresa) throws Exception;
    
    public boolean modificarInvGuiaRutas (InvGuiaRutas invGuiaRutas, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarInvGuiaRutas(InvGuiaRutas invGuiaRutas, SisSuceso sisSuceso) throws Exception;
    
    public List<InvGuiaRutas> obtenerRuta(String empresa, BigDecimal guiaSecuencial) throws Exception;

}
