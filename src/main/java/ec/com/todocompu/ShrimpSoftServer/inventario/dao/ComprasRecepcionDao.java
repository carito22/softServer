package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasRecepcionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasRecepcion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ComprasRecepcionDao extends GenericDao<InvComprasRecepcion, Long> {

	public boolean insertarModificarinvComprasRecepcion(InvComprasRecepcion invComprasRecepcion, SisSuceso sisSuceso)
			throws Exception;

	public InvComprasRecepcionTO getInvComprasRecepcionTO(String empresa, String periodo, String motivo, String numero)
			throws Exception;

}
