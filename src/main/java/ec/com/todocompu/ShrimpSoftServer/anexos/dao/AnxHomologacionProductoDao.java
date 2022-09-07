/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxHomologacionProducto;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

/**
 *
 * @author VALDIVIEZO
 */
public interface AnxHomologacionProductoDao extends GenericDao<AnxHomologacionProducto, Integer> {

    public AnxHomologacionProducto obtenerHomologacionProducto(String empresa, String codigoProductoProveedor, String identificacionProveedor);

    public AnxHomologacionProducto getAnxHomologacionProducto(Integer secuencia) throws Exception;

    public List<AnxHomologacionProducto> listarAnxHomologacionProducto(String empresa, String provIdentificacion, String busqueda, boolean incluirTodos) throws Exception;

    public boolean insertarListadoAnxHomologacionProducto(List<AnxHomologacionProducto> listado, List<SisSuceso> listadoSisSuceso) throws Exception;

    public boolean insertarAnxHomologacionProducto(AnxHomologacionProducto anxHomologacionProducto, SisSuceso sisSuceso) throws Exception;

    public boolean modificarAnxHomologacionProducto(AnxHomologacionProducto anxHomologacionProducto, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarAnxHomologacionProducto(AnxHomologacionProducto anxHomologacionProducto, SisSuceso sisSuceso) throws Exception;
}
