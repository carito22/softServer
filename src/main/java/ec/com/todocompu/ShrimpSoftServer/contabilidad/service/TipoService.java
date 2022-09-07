package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConTipoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface TipoService {

    public MensajeTO insertarConTipo(ConTipoTO conTipoTO, SisInfoTO sisInfoTO) throws Exception;

    public MensajeTO modificarConTipo(ConTipoTO conTipoTO, SisInfoTO sisInfoTO) throws Exception;

    public MensajeTO modificarEstadoConTipo(ConTipoTO conTipoTO, boolean estado, SisInfoTO sisInfoTO) throws Exception;

    public MensajeTO eliminarConTipo(ConTipoTO conTipoTO, SisInfoTO sisInfoTO) throws Exception;

    public List<ConTipoTO> getListaConTipoTO(String empresa) throws Exception;

    public List<ConTipoTO> getListaConTipoCodigo(String empresa, String codigo) throws Exception;

    public ConTipoTO getConTipoTO(String empresa, String tipCodigo) throws Exception;

    public List<ConTipoTO> getListaConTipoRrhhTO(String empresa) throws Exception;

    public List<ConTipoTO> getListaConTipoCarteraTO(String empresa) throws Exception;

    public List<ConTipoTO> getListaConTipoCarteraAnticiposTO(String empresa) throws Exception;

    public List<ConTipo> getListaConTipo(String empresa) throws Exception;

    public List<ConTipo> getListaConTipo(String empresa, String referencia);

    public List<ConTipoTO> getListaConTipoTOFiltrado(String empresa, String filtro) throws Exception;

}
