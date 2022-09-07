package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNumeracionTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface AnexoNumeracionService {

    public List<AnxNumeracionTablaTO> getListaAnexoNumeracionTO(String empresa) throws Exception;

    public List<AnxNumeracionTablaTO> getListaAnexoNumeracionTO(String empresa, String tipoDocumento) throws Exception;

    public AnxNumeracionTO getAnexoNumeracionTO(Integer secuencia) throws Exception;

    public String insertarAnexoNumeracionTO(AnxNumeracionTO anxNumeracionTO, SisInfoTO sisInfoTO) throws Exception;

    public String modificarAnexoNumeracionTO(AnxNumeracionTO anxNumeracionTO, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarAnexoNumeracionTO(AnxNumeracionTO anxNumeracionTO, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> obtenerDatosParaAnexoNumeracionTO(Map<String, Object> map) throws Exception;

    public AnxNumeracion obtenerAnexoNumeracion(AnxNumeracionTO anxNumeracionTO, String empresa) throws Exception;

    public List<AnxNumeracionTablaTO> getListaAnexoNumeracionTO(String empresa, String tipoDocumento, String fecha) throws Exception;
}
