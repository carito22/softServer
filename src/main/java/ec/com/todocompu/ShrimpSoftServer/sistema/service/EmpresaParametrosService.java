package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface EmpresaParametrosService {

    @Transactional
    public Integer getColumnasEstadosFinancieros(String empCodigo) throws Exception;

    public String actualizarTemporalDeEmpresas(List<String> empresas) throws Exception;

    public SisEmpresaParametros obtenerEmpresaParametros(String empCodigo) throws Exception;

}
