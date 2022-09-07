package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSobrevivenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdSobrevivenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSobrevivencia;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface SobrevivenciaService {

    public boolean insertarPrdSobrevivencia(PrdSobrevivenciaTO prdSobrevivenciaTO, SisInfoTO sisInfoTO)
            throws Exception;

    public PrdSobrevivencia insertarSobrevivencia(PrdSobrevivenciaTO prdSobrevivenciaTO, SisInfoTO sisInfoTO)
            throws Exception;

    public boolean modificarPrdSobrevivencia(PrdSobrevivenciaTO prdSobrevivenciaTO, SisInfoTO sisInfoTO)
            throws Exception;

    public boolean eliminarPrdSobrevivencia(PrdSobrevivenciaTO prdSobrevivenciaTO, SisInfoTO sisInfoTO)
            throws Exception;

    public List<PrdListaSobrevivenciaTO> getListaSobrevivenciaTO(String empresa, String sector) throws Exception;

    public List<PrdListaSobrevivenciaTO> sobrevivenciaPorDefecto(String empresa, String sector) throws Exception;

}
