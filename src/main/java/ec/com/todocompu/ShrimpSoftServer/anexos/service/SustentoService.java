package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxSustentoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxSustentoTO;

@Transactional
public interface SustentoService {

    public List<AnxSustentoComboTO> getListaAnxSustentoComboTO(String tipoComprobante) throws Exception;

    public List<AnxSustentoComboTO> getListaAnxSustentoComboTOByTipoCredito(String tipoComprobante, String tipoCredito) throws Exception;

    public List<AnxSustentoTO> getAnexoSustentoTO() throws Exception;

    public AnxSustentoComboTO obtenerAnxSustentoComboTO(String codigo) throws Exception;

}
