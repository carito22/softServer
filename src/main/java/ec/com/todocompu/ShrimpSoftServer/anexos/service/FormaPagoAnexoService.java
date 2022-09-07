package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxFormaPago;

@Transactional
public interface FormaPagoAnexoService {

    public AnxFormaPago getAnexoFormaPago(String codigo) throws Exception;

    public List<AnxFormaPagoTO> getAnexoFormaPagoTO(Date fechaFactura) throws Exception;

}
