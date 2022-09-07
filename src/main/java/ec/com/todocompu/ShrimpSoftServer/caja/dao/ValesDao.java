package ec.com.todocompu.ShrimpSoftServer.caja.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaValesTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCuadreCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajaValesTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajVales;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajValesNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajValesPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ValesDao extends GenericDao<CajVales, CajValesPK> {

	public List<CajaValesTO> getListadoCajValesTO(String empresa, String motCadigo, String fechaDesde,
			String fechaHasta) throws Exception;

	public CajCajaValesTO getCajCajaValesTO(String empresa, String perCodigo, String motCodigo, String valeNumero)
			throws Exception;

	public List<CajCuadreCajaTO> getCajCuadreCajaTOs(String empresa, String codigoMotivo, String fechaDesde,
			String fechaHasta) throws Exception;

	public Boolean accionCajaValesTO(CajVales cajVales, SisSuceso sisSuceso, CajValesNumeracion cajValesNumeracion,
			String accion) throws Exception;

}
