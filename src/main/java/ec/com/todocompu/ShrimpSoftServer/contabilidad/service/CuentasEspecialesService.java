package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CuentasEspecialesService {

	public Boolean buscarConDetallesActivosBiologicos(String empresa) throws Exception;

}
