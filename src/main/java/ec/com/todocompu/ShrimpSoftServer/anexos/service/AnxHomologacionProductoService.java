/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxHomologacionProducto;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author VALDIVIEZO
 */
public interface AnxHomologacionProductoService {

    public AnxHomologacionProducto obtenerHomologacionProducto(String empresa, String codigoProductoProveedor, String identificacionProveedor);

    public boolean insertarListadoAnxHomologacionProducto(List<AnxHomologacionProducto> listado, List<SisSuceso> listadoSisSuceso) throws Exception;

    public List<AnxHomologacionProducto> listarAnxHomologacionProducto(String empresa, String provCodigo, String busqueda, boolean incluirTodos) throws Exception;

    @Transactional
    public String insertarAnxHomologacionProducto(AnxHomologacionProducto anxHomologacionProducto, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String modificarAnxHomologacionProducto(AnxHomologacionProducto anxHomologacionProducto, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String eliminarAnxHomologacionProducto(Integer secuencial, SisInfoTO sisInfoTO) throws Exception;
}
