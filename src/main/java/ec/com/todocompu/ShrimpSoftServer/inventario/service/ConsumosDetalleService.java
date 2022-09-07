package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleConsumoTO;

@Transactional
public interface ConsumosDetalleService {

	public List<InvListaDetalleConsumoTO> getListaInvConsumoDetalleTO(String empresa, String periodo, String motivo,
			String numeroConsumo) throws Exception;

}
