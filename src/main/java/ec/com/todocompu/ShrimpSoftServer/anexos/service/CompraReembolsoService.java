package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraReembolsoTO;

public interface CompraReembolsoService {

	public List<AnxCompraReembolsoTO> getAnexoCompraReembolsoTOs(String empresa, String periodo, String motivo,
			String numeroCompra) throws Exception;

}
