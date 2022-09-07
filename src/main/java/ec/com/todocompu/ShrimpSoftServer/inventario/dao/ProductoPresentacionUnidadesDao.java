package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionUnidadesComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionUnidades;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionUnidadesPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ProductoPresentacionUnidadesDao
		extends GenericDao<InvProductoPresentacionUnidades, InvProductoPresentacionUnidadesPK> {

	public Boolean accionInvProductoPresentacionUnidades(
			InvProductoPresentacionUnidades invProductoPresentacionUnidades, SisSuceso sisSuceso, char accion)
					throws Exception;

	public InvProductoPresentacionUnidades buscarProductoPresentacionUnidades(String empresa, String codigoPresentacion)
			throws Exception;

	public boolean comprobarInvProductoPresentacionUnidades(String empresa, String codigo) throws Exception;

	public List<InvProductoPresentacionUnidadesComboListadoTO> getListaPresentacionUnidadComboTO(String empresa)
			throws Exception;

	public boolean comprobarEliminarInvProductoPresentacionUnidades(String empresa, String codigo) throws Exception;

}
