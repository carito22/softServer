package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasMotivoAnulacion;

@Repository
public class VentasMotivoAnulacionDaoImpl extends GenericDaoImpl<InvVentasMotivoAnulacion, Integer>
		implements VentasMotivoAnulacionDao {

        @Override
	public InvVentasMotivoAnulacion buscarVentaMotivo(String empresa, String periodo, String motivo, String numero)
			throws Exception {
		return obtenerObjetoPorHql(
				"Select vma From InvVentasMotivoAnulacion vma inner join vma.invVentas iv inner join iv.invVentasPK ivpk Where ivpk.vtaEmpresa=?1 and ivpk.vtaPeriodo=?2 and ivpk.vtaMotivo=?3 and ivpk.vtaNumero=?4",
				new Object[] { empresa, periodo, motivo, numero });
	}

}
