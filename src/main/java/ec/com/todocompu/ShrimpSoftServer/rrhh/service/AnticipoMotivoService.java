package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhAnticipoMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipoMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface AnticipoMotivoService {

	public List<RhAnticipoMotivoTO> getListaRhAnticipoMotivoTablaTO(String empresa) throws Exception;

	public List<RhAnticipoMotivoTO> getListaRhAnticipoMotivoTOTablaTO(String empresa, String codigo) throws Exception;

	public RhAnticipoMotivoTO getRhAnticipoMotivoTO(String empresa, String detalle) throws Exception;

	public RhAnticipoMotivo getRhAnticipoMotivo(String empresa, String codigo) throws Exception;

	public String accionRhAnticipoMotivo(RhAnticipoMotivoTO rhAnticipoMotivoTO, char accion, SisInfoTO sisInfoTO)
			throws Exception;

	public MensajeTO insertarRhAnticipoMotivo(RhAnticipoMotivo rhAnticipoMotivo, SisInfoTO sisInfoTO) throws Exception;

	public String modificarRhAnticipoMotivo(RhAnticipoMotivo rhAnticipoMotivo, SisInfoTO sisInfoTO) throws Exception;

	public String eliminarRhAnticipoMotivo(RhAnticipoMotivo rhAnticipoMotivo, SisInfoTO sisInfoTO) throws Exception;

	public List<RhAnticipoMotivo> getListaRhAnticipoMotivo(String empresa) throws Exception;

}
