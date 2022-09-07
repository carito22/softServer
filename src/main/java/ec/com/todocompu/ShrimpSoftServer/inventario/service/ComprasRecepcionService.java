package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasRecepcionTO;

@Transactional
public interface ComprasRecepcionService {

	public InvComprasRecepcionTO getInvComprasRecepcionTO(String empresa, String periodo, String motivo, String numero)
			throws Exception;

}
