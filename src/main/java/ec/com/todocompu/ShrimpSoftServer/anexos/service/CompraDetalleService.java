package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraDetalleTO;

@Transactional
public interface CompraDetalleService {

	public List<AnxCompraDetalleTO> getAnexoCompraDetalleTO(String empresa, String periodo, String motivo,
			String numeroCompra) throws Exception;

}
