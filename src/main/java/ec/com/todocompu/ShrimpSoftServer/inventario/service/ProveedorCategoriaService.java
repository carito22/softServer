package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCategoriaProveedorComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorCategoria;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface ProveedorCategoriaService {

    public List<InvProveedorCategoriaTO> getInvProveedorCategoriaTO(String empresa) throws Exception;

    public List<InvCategoriaProveedorComboTO> getListaCategoriaProveedorComboTO(String empresa) throws Exception;

    public String accionInvProveedorCategoria(InvProveedorCategoriaTO invProveedorCategoriaTO, char accion,
            SisInfoTO sisInfoTO) throws Exception;

    public InvProveedorCategoria buscarInvProveedorCategoria(String empresa, String codigo, String detalle) throws Exception;

    public InvProveedorCategoria buscarInvProveedorCategoria(String empresa, String codigo) throws Exception;
}
