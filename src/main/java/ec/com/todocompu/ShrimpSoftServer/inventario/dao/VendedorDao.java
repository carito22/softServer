package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVendedorComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVendedor;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVendedorPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface VendedorDao extends GenericDao<InvVendedor, InvVendedorPK> {

	public Boolean accionInvVendedor(InvVendedor invVendedor, SisSuceso sisSuceso, char accion) throws Exception;

	public InvVendedor buscarInvVendedor(String empresa, String codigo) throws Exception;

	public boolean comprobarInvVendedor(String empresa, String codigo) throws Exception;

	public List<InvVendedorComboListadoTO> getComboinvListaVendedorTOs(String empresa,String busqueda) throws Exception;

	public boolean comprobarEliminarInvVendedor(String empresa, String codigo) throws Exception;

}
