package ec.com.todocompu.ShrimpSoftServer.security;

import ec.com.todocompu.ShrimpSoftServer.criteria.Criterio;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.NotificacionService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PermisoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.UsuarioDetalleService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.UsuarioService;
import ec.com.todocompu.ShrimpSoftServer.util.RespuestaWeb;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaEmpresaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisMenu;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuario;
import ec.com.todocompu.ShrimpSoftUtils.web.DatosInicialesTO;
import ec.com.todocompu.ShrimpSoftUtils.web.MenuTO;
import ec.com.todocompu.ShrimpSoftUtils.web.ModuloTO;
import ec.com.todocompu.ShrimpSoftUtils.web.SessionItemTO;
import ec.com.todocompu.ShrimpSoftUtils.web.SubMenuTO;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    public GenericoDao<SisUsuario, String> usuarioDao;
    @Autowired
    public UsuarioService usuarioService;
    @Autowired
    public PermisoService permisoService;
    @Autowired
    public NotificacionService notificacionService;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private UsuarioDetalleService usuarioDetalleService;

    public TokenUser obtenerTokenUser(String username, String password) throws UsernameNotFoundException, DisabledException {
        Criterio filtro;
        filtro = Criterio.forClass(SisUsuario.class);
        filtro.add(Restrictions.eq("usrEmail", username));
//        filtro.add(Restrictions.eq("usrPassword", password));
        SisUsuario user = usuarioDao.obtenerPorCriteriaSinProyeccionesDistinct(filtro);
        TokenUser currentUser;
        if (user != null && user.getUsrActivo()) {
            if (user.getUsrCaduca() != null && user.getUsrCaduca().before(new Date())) {
                throw new DisabledException("El acceso del usuario ha caducado. Contáctese con el administrador del sistema.");
            }
            currentUser = new TokenUser(user);
        } else {
            throw new DisabledException("El usuario no se encuentra registrado en el sistema.");
        }
        return currentUser;
    }

    @Override
    public SessionResponse obtenerSessionRespuesta(SessionResponse resp, String username, String password) throws Exception {
        TokenUser tokenUser = obtenerTokenUser(username, password);
        SessionItemTO respItem = new SessionItemTO();
        DatosInicialesTO datos = traerDatosIniciales(tokenUser.getUsuario().getUsrNick());
        String tokenString = this.tokenUtil.createTokenForUser(tokenUser);
        respItem.setUsuarioId(tokenUser.getUsuario().getUsrNick());
        respItem.setToken(tokenString);
        respItem.setNombrecompleto(tokenUser.getUsuario().getUsrNombre() + " " + tokenUser.getUsuario().getUsrApellido());
        respItem.setCodigo(tokenUser.getUsuario().getUsrCodigo());
        respItem.setEmail(tokenUser.getUsuario().getUsrEmail());
        resp.setEstadoOperacion(RespuestaWeb.EstadoOperacionEnum.EXITO.getValor());
        resp.setOperacionMensaje("Login Correcto");
        //suceso
        if (datos != null) {
            SisInfoTO sisInfoTO = datos.getUsuario();
            String susClave = sisInfoTO.getUsuario();
            String susDetalle = "El usuario " + sisInfoTO.getUsuario() + " se logueo desde WEB en el sistema a las " + UtilsValidacion.fechaSistema();
            String susSuceso = "LOGIN";
            String susTabla = "sistema.sis_usuario";
            List<SisListaEmpresaTO> listaLoginEmpresas = usuarioDetalleService.getListaLoginEmpresaTO(sisInfoTO.getUsuario());
            if (listaLoginEmpresas.size() > 0) {
                for (int i = 0; i < listaLoginEmpresas.size(); i++) {
                    sisInfoTO.setEmpresa(listaLoginEmpresas.get(i).getEmpCodigo());
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    sucesoDao.insertar(sisSuceso);
                }
            }
        }
        resp.setItem(respItem);
        if (datos == null) {
            resp.setIniciales(null);
        } else {
            resp.setIniciales(datos);
            resp.setSisInfoTO(datos.getUsuario());
            resp.setNotificaciones(notificacionService.notificar(respItem.getCodigo()));
        }
        return resp;
    }

    @Override
    public boolean logout(SisInfoTO sisInfoTO) throws Exception {
        String susClave = sisInfoTO.getUsuario();
        String susDetalle = "El usuario " + sisInfoTO.getUsuario() + " salio del sistema desde WEB a las " + UtilsValidacion.fechaSistema();
        String susSuceso = "LOGOUT";
        String susTabla = "sistema.sis_usuario";
        List<SisListaEmpresaTO> listaLoginEmpresas = usuarioDetalleService.getListaLoginEmpresaTO(sisInfoTO.getUsuario());
        if (listaLoginEmpresas.size() > 0) {
            for (int i = 0; i < listaLoginEmpresas.size(); i++) {
                sisInfoTO.setEmpresa(listaLoginEmpresas.get(i).getEmpCodigo());
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                sucesoDao.insertar(sisSuceso);
            }
        }
        return true;
    }

    public DatosInicialesTO traerDatosIniciales(String userName) throws GeneralException {
        DatosInicialesTO datos = new DatosInicialesTO();
        datos.setUsuario(traerInfoTO(userName));
        datos.setModulos(listaModulos(userName, datos.getUsuario()));
        datos.setCambiarContrasenia(cambiarContrasenia(userName, datos.getUsuario()));
        return datos;
    }

    public SisInfoTO traerInfoTO(String userName) throws GeneralException {
        try {
            Criterio filtro;
            filtro = Criterio.forClass(SisUsuario.class);
            filtro.add(Restrictions.eq("usrNick", userName));
            SisUsuario usuario = usuarioDao.obtenerPorCriteriaSinProyeccionesDistinct(filtro);
            SisInfoTO sisInfoTO = new SisInfoTO();
            sisInfoTO.setEmpresa("");
            sisInfoTO.setUsuarioNick(userName);
            sisInfoTO.setUsuario(usuario.getUsrCodigo());
            sisInfoTO.setUsuarioCompleto(usuario.getUsrNombre() == null ? sisInfoTO.getUsuarioNick() : usuario.getUsrNombre() + " " + usuario.getUsrApellido());
            sisInfoTO.setMac(usuario.getUsrIP() == null ? "" : usuario.getUsrIP());
            sisInfoTO.setAmbiente("WEB");
            sisInfoTO.setUsuarioCompleto(usuario.getUsrNombre() + " " + usuario.getUsrApellido());
            sisInfoTO.setEmail(usuario.getUsrEmail());
            sisInfoTO.setTelefono(usuario.getUsrTelefono());
            String nombreArchivo = usuario.getUsrNick() + "_" + usuario.getUsrNombre() + "_" + usuario.getUsrApellido();
            byte[] imagen = usuarioService.obtenerImagenUsuario(nombreArchivo);
            if (imagen != null && imagen.length > 0) {
                String base64Encoded = Base64.getEncoder().encodeToString(imagen);
                sisInfoTO.setImagen(base64Encoded);
            } else {
                imagen = usuarioService.obtenerImagenUsuarioDefault("default.png");
                sisInfoTO.setImagen(imagen != null ? Base64.getEncoder().encodeToString(imagen) : "");
            }
            return sisInfoTO;
        } catch (Exception e) {
            throw new GeneralException("Error al obtener SisUsuario", e.getCause());
        }
    }

    public ArrayList<ModuloTO> listaModulos(String usuario, SisInfoTO sisInfousuario) {
        List<SisMenu> menus = permisoService.getMenuWeb(sisInfousuario.getUsuario(), false);
        ArrayList<ModuloTO> listaREsultante = new ArrayList<>();
        for (SisMenu menu : menus) {
            ModuloTO moduloTO = new ModuloTO(menu.getPerModulo(), String.valueOf(menu.hashCode()), menu.hashCode(), menu.getPerIcono(), "");
            if (!itemRepetidoModulo(moduloTO, listaREsultante)) {
                listaREsultante.add(moduloTO);
            }
        }
        for (ModuloTO modulo : listaREsultante) {
            for (SisMenu menu : menus) {
                if (modulo.getLabel().equalsIgnoreCase(menu.getPerModulo())) {
                    MenuTO menuTO = new MenuTO(menu.getPerMenu(), String.valueOf(menu.hashCode()), menu.hashCode());
                    if (!itemRepetidoMmenu(menuTO, (ArrayList<MenuTO>) modulo.getMenus())) {
                        modulo.getMenus().add(menuTO);
                    }
                }
            }
        }
        for (ModuloTO modulo : listaREsultante) {
            for (MenuTO menuTO : modulo.getMenus()) {
                for (SisMenu menu : menus) {
                    if (menuTO.getLabel().equalsIgnoreCase(menu.getPerMenu()) && modulo.getLabel().equalsIgnoreCase(menu.getPerModulo())) {
                        SubMenuTO subMenuTO = new SubMenuTO(menu.getPerItemEtiqueta(), menu.getPerUrl(), menu.hashCode(), menu.getPerMenu());
                        if (!itemRepetidoSubMenu(subMenuTO, (ArrayList<SubMenuTO>) menuTO.getSubmenus())) {
                            menuTO.getSubmenus().add(subMenuTO);
                        }
                    }
                }
            }
        }
        return listaREsultante;
    }

    public boolean itemRepetidoModulo(ModuloTO modulo, ArrayList<ModuloTO> lista) {
        if (!lista.isEmpty()) {
            for (ModuloTO moduloLocal : lista) {
                if (moduloLocal.getLabel().equalsIgnoreCase(modulo.getLabel())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean itemRepetidoMmenu(MenuTO menu, ArrayList<MenuTO> lista) {
        if (!lista.isEmpty()) {
            for (MenuTO menuLocal : lista) {
                if (menuLocal.getLabel().equalsIgnoreCase(menu.getLabel())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean itemRepetidoSubMenu(SubMenuTO submenu, ArrayList<SubMenuTO> lista) {
        if (!lista.isEmpty()) {
            for (SubMenuTO subMenuLocal : lista) {
                if (subMenuLocal.getLabel().equalsIgnoreCase(submenu.getLabel())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean cambiarContrasenia(String usuarioLocal, SisInfoTO SisInfoUsuario) {
        SisUsuario usuario = usuarioService.obtenerPorNick(usuarioLocal);
        return usuario.getUsrCambiarContrasenia();
    }

}
