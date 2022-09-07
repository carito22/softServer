/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRutas;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import java.math.BigDecimal;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GuiaRutasService {

    public String insertarInvRutasGuias(InvGuiaRutas invGuiaRutas, SisInfoTO sisInfoTO) throws Exception;
    
    public List<InvGuiaRutas> getListarInvGuiaRutas (String empresa) throws Exception;
    
    public String modificarInvGuiaRutas (InvGuiaRutas invGuiaRutas, SisInfoTO sisInfoTO) throws Exception;
    
    public String eliminarInvGuiaRutas(InvGuiaRutas invGuiaRutas, SisInfoTO sisInfoTO) throws Exception;
    
    public List<InvGuiaRutas> obtenerRuta(String empresa, BigDecimal guiaSecuencial) throws Exception;

}
