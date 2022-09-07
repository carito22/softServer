package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvNumeracionCompraTO;

@Transactional
public interface ComprasNumeracionService {

	public List<InvNumeracionCompraTO> getListaInvNumeracionCompraTO(String empresa, String periodo, String motivo)
			throws Exception;

}
