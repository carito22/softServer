/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCtaFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhFormaPagoBeneficiosSociales;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface FormaPagoBeneficiosSocialesDao extends GenericDao<RhFormaPagoBeneficiosSociales, Integer> {
	RhFormaPagoBeneficiosSociales buscarFormaPagoBeneficioSocial(Integer fpSecuencia) throws Exception;

	Boolean accionRhFormaPagoBeneficioSocial(RhFormaPagoBeneficiosSociales rhFormaPagoBeneficioSocial,
			SisSuceso sisSuceso, char accion) throws Exception;

	Boolean buscarRhFormaPagoBeneficioSocial(String ctaContable, String empresa) throws Exception;

	List<RhListaFormaPagoBeneficioSocialTO> getListaFormaPagoBeneficioSocial(String empresa) throws Exception;

	List<RhComboFormaPagoBeneficioSocialTO> getComboFormaPagoBeneficioSocial(String empresa) throws Exception;

	RhCtaFormaPagoBeneficioSocialTO buscarCtaRhFormaPagoBeneficioSocial(String empCodigo, String fpDetalle)
			throws Exception;
}
