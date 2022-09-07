package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoSubcategoria;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoSubcategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface ProductoSubcategoriaService {

    public List<InvProductoSubcategoria> listarSubcategoriasProducto(String empresa) throws Exception;

    public InvProductoSubcategoria insertarInvSubCategoriaProducto(InvProductoSubcategoria invProductoSubcategoria , SisInfoTO sisInfoTO) throws Exception, GeneralException;

    public InvProductoSubcategoria modificarInvSubCategoriaProducto(InvProductoSubcategoria invProductoSubcategoria, SisInfoTO sisInfoTO) throws Exception, GeneralException;

    public boolean eliminarInvSubCategoriaProducto(InvProductoSubcategoriaPK invProductoSubcategoriaPK, SisInfoTO sisInfoTO) throws Exception, GeneralException;

}
