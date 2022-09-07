package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCategoriaClienteComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface ClienteCategoriaService {

	public List<InvClienteCategoriaTO> getInvClienteCategoriaTO(String empresa) throws Exception;

	public String accionInvClienteCategoria(InvClienteCategoriaTO invClienteCategoriaTO, char accion,
			SisInfoTO sisInfoTO) throws Exception;

	public List<InvCategoriaClienteComboTO> getListaCategoriaClienteComboTO(String empresa) throws Exception;

}
