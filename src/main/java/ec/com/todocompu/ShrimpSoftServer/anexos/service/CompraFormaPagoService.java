package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraFormaPago;

@Transactional
public interface CompraFormaPagoService {

	public List<AnxCompraFormaPagoTO> getAnexoCompraFormaPagoTO(String empresa, String periodo, String motivo,
			String numero) throws Exception;

	public List<AnxCompraFormaPago> getAnexoCompraFormaPago(String empresa, String periodo, String motivo,
			String numeroCompra) throws Exception;

}
