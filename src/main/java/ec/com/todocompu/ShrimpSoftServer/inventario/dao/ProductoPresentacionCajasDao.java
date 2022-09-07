package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionCajasComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionCajas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionCajasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ProductoPresentacionCajasDao
		extends GenericDao<InvProductoPresentacionCajas, InvProductoPresentacionCajasPK> {

	public Boolean accionInvProductoPresentacionCajas(InvProductoPresentacionCajas invProductoPresentacionCajas,
			SisSuceso sisSuceso, char accion) throws Exception;

	public InvProductoPresentacionCajas buscarProductoPresentacionCajas(String empresa, String codigoPresentacion)
			throws Exception;

	public boolean comprobarInvProductoPresentacionCajas(String empresa, String codigo) throws Exception;

	public List<InvProductoPresentacionCajasComboListadoTO> getListaPresentacionCajaComboTO(String empresa)
			throws Exception;

	public boolean comprobarEliminarInvProductoPresentacionCajas(String empresa, String codigo) throws Exception;

}
