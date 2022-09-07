package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisConsultaUsuarioGrupoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisLoginTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisUsuarioTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuario;
import java.util.Map;

public interface UsuarioService {

    public SisUsuario obtenerPorEmail(String usrEmail);

    @Transactional
    public SisUsuario obtenerPorId(String usrCodigo);

    @Transactional
    public SisUsuario obtenerPorNick(String usrNick);

    @Transactional
    public SisLoginTO getSisAccesoTO(String nick, String password, String empresa) throws Exception;

    @Transactional
    public SisLoginTO getPermisoInventarioTO(String infEmpresa, String infUsuario) throws Exception;

    @Transactional
    public String insertarSisUsuarioTO(SisUsuarioTO sisUsuarioTO, SisInfoTO sisInfoTO, boolean insertaDetalle)
            throws Exception;

    @Transactional
    public String modificarPasswordSisUsuarioTO(SisUsuarioTO sisUsuarioTO, String pass) throws Exception;

    @Transactional
    public String modificarSisUsuarioWebTO(SisUsuarioTO sisUsuarioTO, String pass) throws Exception;

    @Transactional
    public String modificarSisUsuarioTO(SisUsuarioTO sisUsuarioTO, String pass, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean eliminarSisUsuarioTO(SisUsuarioTO sisUsuarioTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public List<SisConsultaUsuarioGrupoTO> getListaUsuario(String infEmpresa, String infUsuario) throws Exception;

    public byte[] obtenerImagenUsuario(String nombre) throws Exception;

    public byte[] obtenerImagenUsuarioDefault(String nombre) throws Exception;

    @Transactional
    public String guardarImagenUsuario(byte[] imagen, String nombre) throws Exception;

    public byte[] generarReporteUsuario(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<SisConsultaUsuarioGrupoTO> listSisConsultaUsuarioGrupoTO)
            throws Exception;

    public String modificarSisUsuario(SisUsuario sisUsuario, byte[] imagen, SisInfoTO sisInfoTO) throws Exception;

    public boolean claveActualValida(String clave, String codigoUsuario) throws Exception;

    public Map<String, Object> obtenerPerfilDeUsuario(String usrNick) throws Exception;

    @Transactional
    public boolean modificarPasswordSisUsuario(String codigoUser, String clave) throws Exception;

    public String encriptarClaveEmail(String claveEmail) throws Exception;

    @Transactional
    public String insertarSistemUsuarioTO(SisUsuarioTO sisUsuarioTO, SisInfoTO sisInfoTO, boolean insertaDetalle)
            throws Exception;

    @Transactional
    public String modificarSistemUsuarioTO(SisUsuarioTO sisUsuarioTO, String pass, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean actualizarTelefonoUsuario(String usuario, String telefono) throws Exception;

    public String obtenerEmailAdministrador() throws Exception;
}
