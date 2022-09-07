/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.isp.dao;

import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPromesaPago;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

/**
 *
 * @author VALDIVIEZO
 */
public interface SisPromesaPagoDao {

    public List<SisPromesaPago> getListSisPromesaPagoPorcliente(String empresa, String cliCodigo) throws Exception;

    public SisPromesaPago insertarSisPromesaPago(SisPromesaPago promesa, SisSuceso sisSuceso) throws Exception;

    public SisPromesaPago modificarSisPromesaPago(SisPromesaPago promesa, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarSisPromesaPago(SisPromesaPago promesa, SisSuceso sisSuceso) throws Exception;

}
