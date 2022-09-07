package ec.com.todocompu.ShrimpSoftServer.caja.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajValesConceptosComboTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajValesConceptos;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajValesConceptosPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ValesConceptosDao extends GenericDao<CajValesConceptos, CajValesConceptosPK> {

	public List<CajValesConceptosComboTO> getCajValesConceptosComboTOs(String empresa) throws Exception;

	public boolean comprobarCajValesConceptos(String empresa, String codigo) throws Exception;

	public Boolean accionCajValesConceptos(CajValesConceptos cajValesConceptos, SisSuceso sisSuceso, char accion)
			throws Exception;

}
