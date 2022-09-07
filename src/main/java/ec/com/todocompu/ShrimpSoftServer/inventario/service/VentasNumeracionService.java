package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvNumeracionVentaTO;

@Transactional
public interface VentasNumeracionService {

	public List<InvNumeracionVentaTO> getListaInvNumeracionVentaTO(String empresa, String periodo, String motivo)
			throws Exception;

}
