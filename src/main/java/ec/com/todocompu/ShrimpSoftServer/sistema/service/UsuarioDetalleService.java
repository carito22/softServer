package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaEmpresaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaUsuarioTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisUsuarioEmailTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresa;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuarioDetalle;

public interface UsuarioDetalleService {

    @Transactional
    public List<SisListaEmpresaTO> getListaLoginEmpresaTO(String usrCodigo) throws Exception;

    @Transactional
    public List<SisEmpresa> getEmpresasPorUsuarioItem(String usuario, String item) throws Exception;

    public List<SisEmpresa> getEmpresasPorUsuarioItem(String usuario, String item, String empresa) throws Exception;

    @Transactional
    public String getUsuarioNombreApellido(String usrCodigo) throws Exception;

    @Transactional
    public List<SisListaUsuarioTO> getListaSisUsuarios(String empresa) throws Exception;

    public List<SisListaUsuarioTO> getListaSisUsuariosNoTieneConfCompras(String empresa) throws Exception;
    
    public List<SisUsuarioEmailTO> obtenerCorreosADM(String empresa) throws Exception;
    
    public SisUsuarioDetalle obtenerDetalleUsuario(String empresa, String usuario) throws Exception;
}
