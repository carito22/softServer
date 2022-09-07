package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasMotivoAnulacion;

@Repository
public class ComprasMotivoAnuladoDaoImpl extends GenericDaoImpl<InvComprasMotivoAnulacion, Integer>
		implements ComprasMotivoAnuladoDao {

        @Override
	public InvComprasMotivoAnulacion buscarCompraMotivo(String empresa, String periodo, String motivo, String numero)
			throws Exception {
		return obtenerObjetoPorHql(
				"Select cma From InvComprasMotivoAnulacion cma inner join cma.invCompras ic inner join ic.invComprasPK icpx  Where icpx.compEmpresa=?1 and icpx.compPeriodo=?2 and icpx.compMotivo=?3 and icpx.compNumero=?4",
				new Object[] { empresa, periodo, motivo, numero });
	}

}
