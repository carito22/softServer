package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFunRegistroDatosCrediticiosTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxProvinciaCantonTO;

@Transactional
public interface DpaEcuadorService {

	public List<AnxProvinciaCantonTO> getComboAnxDpaProvinciaTO() throws Exception;

	public List<AnxProvinciaCantonTO> getComboAnxDpaCantonTO(String codigoProvincia) throws Exception;

	public List<AnxProvinciaCantonTO> getComboAnxParroquiaTO(String codigoProvincia, String codigoCanton)
			throws Exception;

	public String getObtenerProvincia(String canton) throws Exception;

	public List<AnxFunRegistroDatosCrediticiosTO> getFunRegistroDatosCrediticiosTOs(String codigoEmpresa,
			String fechaDesde, String fechaHasta) throws Exception;

}
