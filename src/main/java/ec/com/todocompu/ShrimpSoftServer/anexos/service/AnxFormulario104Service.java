/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxFormulario104;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author VALDIVIEZO
 */
public interface AnxFormulario104Service {

    public List<AnxFormulario104> getAnxListaAnxFormulario104(String empresa, String fechaDesde, String fechaHasta) throws Exception;

    @Transactional
    public String insertarAnxFormulario104(AnxFormulario104 anxFormulario104, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String modificarAnxFormulario104(AnxFormulario104 anxFormulario104, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String eliminarAnxFormulario104(Integer secuencial, SisInfoTO sisInfoTO) throws Exception;

    public AnxFormulario104 obtenerAnxFormulario104(String frm104Empresa, String inicio, String fin) throws Exception;

}
