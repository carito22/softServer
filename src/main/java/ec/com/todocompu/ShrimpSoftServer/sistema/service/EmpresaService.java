package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisEmpresaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresa;
import java.util.Map;

public interface EmpresaService {

    public SisEmpresaTO obtenerSisEmpresaTOByRUC(String ruc);

    public String guardarImagenEmpresa(byte[] imagen, String nombre) throws Exception;

    public String eliminarImagenEmpresa(String nombre) throws Exception;

    public byte[] obtenerImagenEmpresa(String nombre) throws Exception;

    @Transactional
    public SisEmpresa obtenerPorId(String empCodigo);

    @Transactional
    public boolean insertarSisEmpresa(SisEmpresaTO sisEmpresaTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean estadoLlevarContabilidad(String empresa) throws Exception;

    @Transactional
    public boolean modificarSisEmpresa(SisEmpresaTO sisEmpresaTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public List<SisEmpresaTO> getListaSisEmpresaTO(String usrCodigo, String empresa) throws Exception;

    @Transactional
    public List<SisEmpresaTO> getListaSisEmpresaTOWeb(String usrCodigo) throws Exception;

    public SisEmpresa obtenerEmpresa(String empresa) throws Exception;

    @Transactional
    public SisEmpresa modificarSisEmpresa(SisEmpresa sisEmpresa, SisInfoTO sisInfoTO) throws GeneralException, Exception;

    public Map<String, Object> obtenerDatosParaEmpresa(String empresa) throws Exception;

    @Transactional
    public SisEmpresaTO modificarSisEmpresaTO(SisEmpresaTO sisEmpresaTO, byte[] decodedString, SisInfoTO sisInfoTO, boolean validarRucRepetido) throws Exception;

    @Transactional
    public SisEmpresaTO insertarSisEmpresaTO(SisEmpresaTO sisEmpresaTO, byte[] decodedString, SisInfoTO sisInfoTO, boolean validarRucRepetido) throws Exception;

    public boolean insertarRegistrosComplementarios(String empCodigo) throws Exception;

}
