package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxAnuladoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxAnuladosTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaComprobanteAnuladoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface AnuladosService {

    public List<AnxListaComprobanteAnuladoTO> getAnxListaComprobanteAnuladoTO(String empresa, String fechaDesde,
            String fechaHasta) throws Exception;

    public List<AnxAnuladoTablaTO> getListaAnxAnuladoTablaTO(String empresa) throws Exception;

    public AnxAnuladosTO getAnxAnuladosTO(Integer secuencial) throws Exception;

    public String insertarAnexoAnuladoTO(AnxAnuladosTO anxAnuladosTO, boolean validarRetencionElectronica, SisInfoTO sisInfoTO) throws Exception;

    public String modificarAnexoAnuladoTO(AnxAnuladosTO anxAnuladosTO, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarAnexoAnuladoTO(AnxAnuladosTO anxAnuladosTO, SisInfoTO sisInfoTO) throws Exception;

    public List<AnxAnuladosTO> obtenerAnexoAnulados(AnxAnuladosTO anxAnuladosTO, String empresa) throws Exception;

    public Map<String, Object> obtenerDatosParaAnexoAnuladosTO(Map<String, Object> map) throws Exception;
}
