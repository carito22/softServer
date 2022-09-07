package ec.com.todocompu.ShrimpSoftServer.banco.service;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanPreavisosTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

public interface PreavisosService {

	@Transactional
	public String insertarPreaviso(BanPreavisosTO banPreavisosTO, SisInfoTO sisInfoTO) throws Exception;

	@Transactional
	public String eliminarBanPreaviso(String empresa, String usuario, String cuenta, SisInfoTO sisInfoTO)
			throws Exception;

}
