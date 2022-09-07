package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaFormaPagoBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface FormaPagoBeneficioSocialService {

	public String accionRhFormaPagoBeneficioSocial(RhFormaPagoBeneficioSocialTO rhFormaPagoBeneficioSocialTO,
			char accion, SisInfoTO sisInfoTO) throws Exception;

	public List<RhListaFormaPagoBeneficioSocialTO> getListaFormaPagoBeneficioSocialTO(String empresa) throws Exception;

	public List<RhComboFormaPagoBeneficioSocialTO> getComboFormaPagoBeneficioSocialTO(String empresa) throws Exception;

        public RhFormaPagoBeneficioSocialTO insertarRhFormaPagoBeneficioSocial(RhFormaPagoBeneficioSocialTO rhFormaPagoBeneficioSocialTO, SisInfoTO sisInfoTO) throws Exception, GeneralException;
}
