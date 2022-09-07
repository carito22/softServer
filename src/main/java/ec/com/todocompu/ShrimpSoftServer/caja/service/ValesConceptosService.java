package ec.com.todocompu.ShrimpSoftServer.caja.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajValesConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajValesConceptosComboTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

public interface ValesConceptosService {

	@Transactional
	public List<CajValesConceptosComboTO> getCajValesConceptosComboTOs(String empresa) throws Exception;

	@Transactional
	public String accionCajValesConceptosTO(CajValesConceptoTO cajValesConceptosTO, char accion, SisInfoTO sisInfoTO)
			throws Exception;

}
