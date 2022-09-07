/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.isp.service;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPromesaPago;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author VALDIVIEZO
 */
@Transactional
public interface SisPromesaPagoService {

    public List<SisPromesaPago> getListSisPromesaPagoPorcliente(String empresa, String cliCodigo) throws Exception;

    public SisPromesaPago insertarSisPromesaPago(SisPromesaPago promesa, SisInfoTO sisInfoTO) throws Exception;

    public SisPromesaPago modificarSisPromesaPago(SisPromesaPago promesa, SisInfoTO sisInfoTO) throws Exception;

    public boolean eliminarSisPromesaPago(Integer secuencial, SisInfoTO sisInfoTO) throws Exception;

}
