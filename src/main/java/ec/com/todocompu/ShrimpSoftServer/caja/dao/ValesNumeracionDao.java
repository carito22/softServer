package ec.com.todocompu.ShrimpSoftServer.caja.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajValesNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajValesNumeracionPK;

public interface ValesNumeracionDao extends GenericDao<CajValesNumeracion, CajValesNumeracionPK> {
	public int buscarConteoUltimaNumeracionCajaVale(String empCodigo, String perCodigo, String motCodigo)
			throws Exception;
}
