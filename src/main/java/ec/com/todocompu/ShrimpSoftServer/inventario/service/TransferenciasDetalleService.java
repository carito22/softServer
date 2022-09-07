package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleTransferenciaTO;

@Transactional
public interface TransferenciasDetalleService {

	public List<InvListaDetalleTransferenciaTO> getListaInvTransferenciasDetalleTO(String empresa, String periodo,
			String motivo, String numeroTransferencia) throws Exception;

}
