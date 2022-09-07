package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaEmpresaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaUsuarioTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisUsuarioEmailTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresa;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuarioDetalle;

public interface UsuarioDetalleDao extends GenericDao<SisUsuarioDetalle, String> {

    public List<SisListaEmpresaTO> getListaLoginEmpresaTO(String usrCodigo) throws Exception;

    public List<SisEmpresa> getEmpresasPorUsuarioItem(String usuario, String item) throws Exception;

    public List<SisEmpresa> getEmpresasPorUsuarioItem(String usuario, String item, String empresa) throws Exception;

    public boolean getUsuarioRepetido(String codigo, String nick, String nombre, String apellido) throws Exception;

    public int retornoContadoEliminarGrupo(String codEmpresa, String codGrupo) throws Exception;

    public String getUsuarioNombreApellido(String usrCodigo) throws Exception;

    public List<SisListaUsuarioTO> getListaSisUsuario(String empresa) throws Exception;

    public List<SisListaUsuarioTO> getListaSisUsuariosNoTieneConfCompras(String empresa) throws Exception;
    
    public List<SisUsuarioEmailTO> obtenerCorreosADM(String empresa) throws Exception;
    
    public SisUsuarioDetalle obtenerDetalleUsuario(String empresa, String usuario) throws Exception;
    
}
