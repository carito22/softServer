package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCuentasContablesObjetosTO;
import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCuentasContablesTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface CuentasContablesService {

    public AnxCuentasContablesTO getAnxCuentasContablesTO(String empresa, String nombreCuenta) throws Exception;

    public AnxCuentasContablesObjetosTO getAnxCuentasContablesObjetosTO(String empresa, String nombreCuenta) throws Exception;

    public String actualizarAnxCuentasContables(AnxCuentasContablesTO anxCuentasContablesTO, String empresa,
            String usuario, SisInfoTO sisInfoTO) throws Exception;

}
