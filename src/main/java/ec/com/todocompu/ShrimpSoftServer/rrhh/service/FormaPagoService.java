package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface FormaPagoService {

    public String accionRhFormaPago(RhFormaPagoTO rhFormaPagoTO, char accion, SisInfoTO sisInfoTO) throws Exception;

    public List<RhListaFormaPagoTO> getListaFormaPagoTO(String empresa) throws Exception;

    public List<RhComboFormaPagoTO> getComboFormaPagoTO(String empresa) throws Exception;

    public RhFormaPagoTO insertarRhFormaPago(RhFormaPagoTO rhFormaPagoTO, SisInfoTO sisInfoTO) throws Exception, GeneralException;

}
