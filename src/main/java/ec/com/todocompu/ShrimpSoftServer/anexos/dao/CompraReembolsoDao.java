package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraReembolsoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCompraReembolso;

public interface CompraReembolsoDao extends GenericDao<AnxCompraReembolso, Integer> {

	@Transactional
	public List<AnxCompraReembolsoTO> getAnexoCompraReembolsoTOs(String empresa, String periodo, String motivo,
			String numeroCompra) throws Exception;

}
