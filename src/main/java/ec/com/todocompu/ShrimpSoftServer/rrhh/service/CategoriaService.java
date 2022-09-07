package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhCategoria;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface CategoriaService {

    public String accionRhCategoria(RhCategoriaTO rhCategoriaTO, char accion, SisInfoTO sisInfoTO) throws Exception;

    public RhCategoriaTO getRhCategoriaTO(String empCodigo, String catNombre) throws Exception;

    public List<RhComboCategoriaTO> getComboRhCategoriaTO(String empresa) throws Exception;

    public List<RhCategoriaTO> getListaRhCategoriaTO(String empresa) throws Exception;

    public List<RhCategoriaTO> getListaRhCategoriaCuentasTO(String empresa) throws Exception;

    public List<RhCategoria> getListaRhCategoria(String empresa) throws Exception;

    public Map<String, Object> obtenerDatosCategoria(Map<String, Object> map) throws Exception;
}
