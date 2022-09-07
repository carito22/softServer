package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasComplementoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasComplemento;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasComplementoPK;

public interface VentasComplementoDao extends GenericDao<InvVentasComplemento, InvVentasComplementoPK> {

	public InvVentasComplemento buscarVentasComplemento(String empresa, String periodo, String motivo, String numero)
			throws Exception;
        
        public InvVentasComplementoTO buscarVentasComplementoTO(String empresa, String periodo, String motivo, String numero)
			throws Exception;

}
