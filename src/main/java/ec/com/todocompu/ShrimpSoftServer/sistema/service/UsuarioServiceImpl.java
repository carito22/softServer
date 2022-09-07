package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.UsuarioDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.UsuarioDetalleDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.report.ReporteUsuario;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesSistema;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.Encriptacion;
import ec.com.todocompu.ShrimpSoftUtils.UtilsArchivos;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisConsultaUsuarioGrupoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisLoginTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisUsuarioTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisGrupo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisGrupoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuario;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuarioDetalle;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuarioDetallePK;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private UsuarioDetalleDao usuarioDetalleDao;

    @Autowired
    private SucesoDao sucesoDao;

    private Boolean comprobar = false;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    private String modulo = "sistema";
    @Autowired
    private GenericReporteService genericReporteService;

    @Override
    public SisUsuario obtenerPorEmail(String usrEmail) {
        return usuarioDao.obtenerObjetoPorHql("Select su From SisUsuario su Where su.usrEmail=?1", new Object[]{usrEmail});
    }

    @Override
    public SisUsuario obtenerPorId(String usrCodigo) {
        return usuarioDao.obtenerPorId(SisUsuario.class, usrCodigo);
    }

    @Override
    public SisUsuario obtenerPorNick(String usrNick) {
        return usuarioDao.obtenerObjetoPorHql("Select su From SisUsuario su Where su.usrNick=?1",
                new Object[]{usrNick});
    }

    @Override
    public SisLoginTO getSisAccesoTO(String nick, String password, String empresa) throws Exception {
        String nickApp = nick;
        String passwordApp = password;
        return usuarioDao.getSisAcceso(nickApp, passwordApp, empresa);
    }

    @Override
    public SisLoginTO getPermisoInventarioTO(String infEmpresa, String infUsuario) throws Exception {
        return usuarioDao.getPermisoInventarioTO(infEmpresa, infUsuario);
    }

    @Override
    public String insertarSisUsuarioTO(SisUsuarioTO sisUsuarioTO, SisInfoTO sisInfoTO, boolean insertaDetalle)
            throws Exception {
        String retorno = "";
        sisUsuarioTO.setUsrNick((sisUsuarioTO.getUsrNick()));
        SisUsuario sisUsuario = null;
        if (insertaDetalle) {
            retorno = "T";
        }
        if (!insertaDetalle) {
            if (usuarioDao.obtenerPorId(SisUsuario.class, sisUsuarioTO.getUsrCodigo()) == null) {
                ///// BUSCAR POR NICK
                if (usuarioDetalleDao.getUsuarioRepetido(null,
                        (sisUsuarioTO.getUsrNick().trim().isEmpty() ? null : "'" + sisUsuarioTO.getUsrNick() + "'"),
                        null, null)) {
                    retorno = "FEl Nick del Usuario ya existe en los registros.\nIntente ingresando otro Nick...";
                } else ///// BUSCAR POR NOMBRE Y APELLIDO
                if (usuarioDetalleDao.getUsuarioRepetido(null, null,
                        (sisUsuarioTO.getUsrNombre().trim().isEmpty() ? null
                        : "'" + sisUsuarioTO.getUsrNombre() + "'"),
                        (sisUsuarioTO.getUsrApellido().trim().isEmpty() ? null
                        : "'" + sisUsuarioTO.getUsrApellido() + "'"))) {
                    retorno = "FEl Nombre y Apellido que ingresó ya existe en los registros.\nIntente ingresando otro Nombre y Apellido...";
                } else {
                    retorno = "T";
                }
            } else {
                retorno = "FEl Código del Usuario que va a ingresar ya existe...\nIntente con otro...";
            }
        }
        if (retorno.charAt(0) == 'T') {
            sisUsuarioTO.setUsrFechaInsertaUsuario(UtilsValidacion.fechaSistema());
            sisUsuario = ConversionesSistema.ConvertirSisUsuarioTO_SisUsuario(sisUsuarioTO);
            SisUsuarioDetalle sisUsuarioDetalle = new SisUsuarioDetalle(
                    new SisUsuarioDetallePK(sisInfoTO.getEmpresa(), sisUsuario.getUsrCodigo()), "", true,
                    UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
            sisUsuarioDetalle.setSisUsuario(sisUsuario);
            sisUsuarioDetalle.setSisGrupo(
                    new SisGrupo(new SisGrupoPK(sisInfoTO.getEmpresa(), sisUsuarioTO.getGruCodigo())));

            comprobar = false;
            // SUCESO
            susClave = sisUsuarioTO.getUsrCodigo();
            susTabla = insertaDetalle == true ? "sistemaWeb.sis_usuario_detalle" : "sistemaWeb.sis_usuario";
            susDetalle = "Se insertó datos del usuario " + sisUsuarioTO.getUsrCodigo() + " en la empresa "
                    + sisInfoTO.getEmpresa();
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (insertaDetalle) {
                comprobar = usuarioDao.insertarSisUsuarioDetalle(sisUsuario, sisUsuarioDetalle, sisSuceso);
            } else {
                comprobar = usuarioDao.insertarSisUsuario(sisUsuario, sisUsuarioDetalle, sisSuceso);
            }

            if (comprobar) {
                retorno = "TUsuario guardado correctamente...";
            } else {
                retorno = "FHubo un error al guardar el usuario...\nIntente de nuevo o contacte con el administrador.";
            }
        }
        return retorno;
    }

    @Override
    public String insertarSistemUsuarioTO(SisUsuarioTO sisUsuarioTO, SisInfoTO sisInfoTO, boolean insertaDetalle)
            throws Exception {
        String retorno = "";
        sisUsuarioTO.setUsrNick((sisUsuarioTO.getUsrNick()));
        SisUsuario sisUsuario = null;
        if (insertaDetalle) {
            retorno = "T";
        }
        if (!insertaDetalle) {
            if (usuarioDao.obtenerPorId(SisUsuario.class, sisUsuarioTO.getUsrCodigo()) == null) {
                ///// BUSCAR POR NICK
                if (usuarioDetalleDao.getUsuarioRepetido(null,
                        (sisUsuarioTO.getUsrNick().trim().isEmpty() ? null : "'" + sisUsuarioTO.getUsrNick() + "'"),
                        null, null)) {
                    retorno = "FEl Nick del Usuario ya existe en los registros.\nIntente ingresando otro Nick...";
                } else ///// BUSCAR POR NOMBRE Y APELLIDO
                if (usuarioDetalleDao.getUsuarioRepetido(null, null,
                        (sisUsuarioTO.getUsrNombre().trim().isEmpty() ? null
                        : "'" + sisUsuarioTO.getUsrNombre() + "'"),
                        (sisUsuarioTO.getUsrApellido().trim().isEmpty() ? null
                        : "'" + sisUsuarioTO.getUsrApellido() + "'"))) {
                    retorno = "FEl Nombre y Apellido que ingresó ya existe en los registros.\nIntente ingresando otro Nombre y Apellido...";
                } else {
                    retorno = "T";
                }
            } else {
                retorno = "FEl Código del Usuario que va a ingresar ya existe...\nIntente con otro...";
            }
        }
        if (retorno.charAt(0) == 'T') {
            sisUsuarioTO.setUsrFechaInsertaUsuario(UtilsValidacion.fechaSistema());
            sisUsuario = ConversionesSistema.ConvertirSisUsuarioTO_SisUsuario(sisUsuarioTO);
            SisUsuarioDetalle sisUsuarioDetalle = new SisUsuarioDetalle(
                    new SisUsuarioDetallePK(sisInfoTO.getEmpresa(), sisUsuario.getUsrCodigo()), "", true,
                    UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
            sisUsuarioDetalle.setSisUsuario(sisUsuario);
            sisUsuarioDetalle.setSisGrupo(
                    new SisGrupo(new SisGrupoPK(sisInfoTO.getEmpresa(), sisUsuarioTO.getGruCodigo())));

            comprobar = false;
            // SUCESO
            susClave = sisUsuarioTO.getUsrCodigo();
            susTabla = insertaDetalle == true ? "sistemaWeb.sis_usuario_detalle" : "sistemaWeb.sis_usuario";
            susDetalle = "Se insertó datos del usuario " + sisUsuarioTO.getUsrCodigo() + " en la empresa "
                    + sisInfoTO.getEmpresa();
            susSuceso = "INSERT";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (insertaDetalle) {
                comprobar = usuarioDao.insertarSisUsuarioDetalle(sisUsuario, sisUsuarioDetalle, sisSuceso);
            } else {
                comprobar = usuarioDao.insertarSisUsuario(sisUsuario, sisUsuarioDetalle, sisSuceso);
            }

            if (comprobar) {
                retorno = "TUsuario guardado correctamente...";
            } else {
                retorno = "FHubo un error al guardar el usuario...\nIntente de nuevo o contacte con el administrador.";
            }
        }
        return retorno;
    }

    @Override
    public String modificarPasswordSisUsuarioTO(SisUsuarioTO sisUsuarioTO, String pass) throws Exception {
        SisUsuario sisUsuario = usuarioDao.obtenerPorId(SisUsuario.class, sisUsuarioTO.getUsrCodigo());
        sisUsuario.setUsrPassword(pass);
        sisUsuario.setUsrCambiarContrasenia(false);
        if (usuarioDao.modificarSisUsuario(sisUsuario)) {
            return "TSu contraseña se modifico correctamente...";
        } else {
            return "FHubo un error al actualizar la contraseña...\nIntente de nuevo o contacte con el administrador.";
        }
    }

    @Override
    public boolean modificarPasswordSisUsuario(String codigoUser, String clave) throws Exception {
        Encriptacion encriptacion = new Encriptacion();
        SisUsuario sisUsuario = usuarioDao.obtenerPorId(SisUsuario.class, codigoUser);
        sisUsuario.setUsrPassword(encriptacion.sha256(clave));
        sisUsuario.setUsrCambiarContrasenia(false);
        usuarioDao.modificarSisUsuario(sisUsuario);
        return true;
    }

    @Override
    public boolean actualizarTelefonoUsuario(String usuario, String telefono) throws Exception {
        SisUsuario sisUsuario = usuarioDao.obtenerPorId(SisUsuario.class, usuario);
        if (sisUsuario != null && telefono != null) {
            sisUsuario.setUsrTelefono(telefono.toLowerCase());
            usuarioDao.modificarSisUsuario(sisUsuario);
            return true;
        }
        return false;
    }

    @Override
    public String obtenerEmailAdministrador() throws Exception {
        SisUsuario sisUsuario = usuarioDao.obtenerPorId(SisUsuario.class, "ADM");
        if (sisUsuario != null) {
            return sisUsuario.getUsrEmail();
        }
        return "";
    }

    @Override
    public String modificarSisUsuarioWebTO(SisUsuarioTO sisUsuarioTO, String pass) throws Exception {
        SisUsuario sisUsuario = usuarioDao.obtenerPorId(SisUsuario.class, sisUsuarioTO.getUsrCodigo());
        sisUsuario.setUsrPassword(pass);
        sisUsuario.setUsrCambiarContrasenia(false);
        sisUsuario.setUsrApellido(sisUsuarioTO.getUsrApellido());
        sisUsuario.setUsrNombre(sisUsuarioTO.getUsrNombre());
        sisUsuario.setUsrEmail(sisUsuarioTO.getUsrEmail());
        sisUsuario.setUsrPasswordEmail(sisUsuarioTO.getUsrPasswordEmail());
        if (usuarioDao.modificarSisUsuario(sisUsuario)) {
            return "TSus datos han sido actualizados correctamente";
        } else {
            return "FHubo un error al actualizar sus datos...\nIntente de nuevo o contacte con el administrador.";
        }
    }

    @Override
    public String modificarSisUsuarioTO(SisUsuarioTO sisUsuarioTO, String pass, SisInfoTO sisInfoTO) throws Exception {
        sisUsuarioTO.setUsrNick(sisUsuarioTO.getUsrNick());
        SisUsuario sisUsuario = null;
        String fechaEnviar = "";
        String retorno = "";
        if (usuarioDao.obtenerPorId(SisUsuario.class, sisUsuarioTO.getUsrCodigo()) != null) {
            ///// BUSCAR POR NICK
            if (usuarioDetalleDao.getUsuarioRepetido("'" + sisUsuarioTO.getUsrCodigo().trim() + "'",
                    (sisUsuarioTO.getUsrNick().trim().isEmpty() ? null : "'" + sisUsuarioTO.getUsrNick() + "'"),
                    null, null)) {
                retorno = "FEl Nick del Usuario ya existe en los registros.\nIntente ingresando otro Nick...";
            } else ///// BUSCAR POR NOMBRE Y APELLIDO
            if (usuarioDetalleDao.getUsuarioRepetido("'" + sisUsuarioTO.getUsrCodigo().trim() + "'", null,
                    (sisUsuarioTO.getUsrNombre().trim().isEmpty() ? null
                    : "'" + sisUsuarioTO.getUsrNombre() + "'"),
                    (sisUsuarioTO.getUsrApellido().trim().isEmpty() ? null
                    : "'" + sisUsuarioTO.getUsrApellido() + "'"))) {
                retorno = "FEl Nombre y Apellido que ingresó ya existe en los registros.\nIntente ingresando otro Nombre y Apellido...";
            } else {
                retorno = "T";
            }
            if (retorno.charAt(0) == 'T') {
                comprobar = false;
                if (usuarioDao.obtenerPorId(SisUsuario.class, sisUsuarioTO.getUsrCodigo()) != null) {
                    if (sisUsuarioTO.getUsrCaduca() == null) {
                        SisUsuario sisUsuarioAux = usuarioDao.obtenerPorId(SisUsuario.class,
                                sisUsuarioTO.getUsrCodigo());
                        if (sisUsuarioAux.getUsrCaduca() == null) {
                            fechaEnviar = null;
                        } else {
                            fechaEnviar = sisUsuarioAux.getUsrCaduca().toString();
                            Calendar c1 = Calendar.getInstance();
                            c1.setTime(sisUsuarioAux.getUsrCaduca());
                            fechaEnviar = UtilsValidacion.fecha(c1.getTime(), "yyyy-MM-dd HH:mm:ss");
                        }
                    } else {
                        fechaEnviar = UtilsValidacion
                                .fechaSumarDias(UtilsValidacion.fechaString_Date(sisUsuarioTO.getUsrCaduca()), 72);
                    }
                    sisUsuarioTO.setUsrCaduca(fechaEnviar);
                    // SUCESO
                    susClave = sisUsuarioTO.getUsrCodigo();
                    susTabla = "sistemaWeb.sis_usuario";
                    susDetalle = "Se modificó datos del usuario " + sisUsuarioTO.getUsrCodigo();
                    susSuceso = "UPDATE";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                            sisInfoTO);
                    if (pass == null) {
                        SisUsuario sisUsuarioDato = usuarioDao.obtenerPorId(SisUsuario.class,
                                sisUsuarioTO.getUsrCodigo());
                        sisUsuarioTO.setUsrPassword(sisUsuarioDato.getUsrPassword());
                        sisUsuarioTO.setUsrInsertaUsuario("");
                        sisUsuarioTO.setUsrFechaInsertaUsuario(UtilsValidacion.fechaSistema());
                        sisUsuario = ConversionesSistema.ConvertirSisUsuarioTO_SisUsuario(sisUsuarioTO);
                        sisUsuario.setUsrCodigo(sisUsuarioTO.getUsrCodigo());
                        sisUsuario.setUsrCodigoInserta(sisInfoTO.getUsuario());
                        sisUsuario.setUsrFechaInsertaUsuario(sisUsuarioDato.getUsrFechaInsertaUsuario());
                        SisUsuarioDetalle sisUsuarioDetalle = new SisUsuarioDetalle(
                                new SisUsuarioDetallePK(sisInfoTO.getEmpresa(), sisUsuario.getUsrCodigo()), "",
                                true, UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                        sisUsuarioDetalle.setDetActivo(sisUsuario.getUsrActivo());
                        sisUsuarioDetalle.setSisUsuario(sisUsuario);
                        sisUsuario.setUsrActivo(true);
                        sisUsuarioDetalle.setSisGrupo(new SisGrupo(
                                new SisGrupoPK(sisInfoTO.getEmpresa(), sisUsuarioTO.getGruCodigo())));
                        comprobar = usuarioDao.modificarSisUsuario(sisUsuario, sisUsuarioDetalle, sisSuceso);
                        if (comprobar) {
                            retorno = "TUsuario modificado correctamente...";
                        } else {
                            retorno = "FHubo un error al modificar el usuario...\nIntente de nuevo o contacte con el administrador.";
                        }
                    } else {
                        SisUsuario sisUsuarioDato = usuarioDao.obtenerPorId(SisUsuario.class,
                                sisUsuarioTO.getUsrCodigo());
                        sisUsuarioTO.setUsrFechaInsertaUsuario(UtilsValidacion
                                .fecha(sisUsuarioDato.getUsrFechaInsertaUsuario(), "yyyy-MM-dd HH:mm:ss"));
                        sisUsuario = ConversionesSistema.ConvertirSisUsuarioTO_SisUsuario(sisUsuarioTO);
                        sisUsuario.setUsrCodigoInserta(sisInfoTO.getUsuario());
                        sisUsuario.setUsrFechaInsertaUsuario(sisUsuarioDato.getUsrFechaInsertaUsuario());
                        sisUsuario.setUsrCodigo(sisUsuarioTO.getUsrCodigo());

                        SisUsuarioDetalle sisUsuarioDetalle = new SisUsuarioDetalle(
                                new SisUsuarioDetallePK(sisInfoTO.getEmpresa(), sisUsuario.getUsrCodigo()), "",
                                true, UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                        sisUsuarioDetalle.setDetActivo(sisUsuario.getUsrActivo());
                        sisUsuarioDetalle.setSisUsuario(sisUsuario);
                        sisUsuario.setUsrActivo(true);
                        sisUsuarioDetalle.setSisGrupo(new SisGrupo(
                                new SisGrupoPK(sisInfoTO.getEmpresa(), sisUsuarioTO.getGruCodigo())));
                        comprobar = usuarioDao.modificarSisUsuario(sisUsuario, sisUsuarioDetalle, sisSuceso);
                        if (comprobar) {
                            retorno = "TUsuario modificado correctamente...";
                        } else {
                            retorno = "FHubo un error al modificar el usuario...\nIntente de nuevo o contacte con el administrador.";
                        }
                    }
                }
            }
        } else {
            retorno = "FEl Código del Usuario que va a modificar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public String modificarSistemUsuarioTO(SisUsuarioTO sisUsuarioTO, String pass, SisInfoTO sisInfoTO) throws Exception {
        sisUsuarioTO.setUsrNick(sisUsuarioTO.getUsrNick());
        SisUsuario sisUsuario = null;
        String fechaEnviar = "";
        String retorno = "";
        if (usuarioDao.obtenerPorId(SisUsuario.class, sisUsuarioTO.getUsrCodigo()) != null) {
            ///// BUSCAR POR NICK
            if (usuarioDetalleDao.getUsuarioRepetido("'" + sisUsuarioTO.getUsrCodigo().trim() + "'",
                    (sisUsuarioTO.getUsrNick().trim().isEmpty() ? null : "'" + sisUsuarioTO.getUsrNick() + "'"),
                    null, null)) {
                retorno = "FEl Nick del Usuario ya existe en los registros.\nIntente ingresando otro Nick...";
            } else ///// BUSCAR POR NOMBRE Y APELLIDO
            if (usuarioDetalleDao.getUsuarioRepetido("'" + sisUsuarioTO.getUsrCodigo().trim() + "'", null,
                    (sisUsuarioTO.getUsrNombre().trim().isEmpty() ? null
                    : "'" + sisUsuarioTO.getUsrNombre() + "'"),
                    (sisUsuarioTO.getUsrApellido().trim().isEmpty() ? null
                    : "'" + sisUsuarioTO.getUsrApellido() + "'"))) {
                retorno = "FEl Nombre y Apellido que ingresó ya existe en los registros.\nIntente ingresando otro Nombre y Apellido...";
            } else {
                retorno = "T";
            }
            if (retorno.charAt(0) == 'T') {
                comprobar = false;
                if (usuarioDao.obtenerPorId(SisUsuario.class, sisUsuarioTO.getUsrCodigo()) != null) {
                    if (sisUsuarioTO.getUsrCaduca() == null) {
                        SisUsuario sisUsuarioAux = usuarioDao.obtenerPorId(SisUsuario.class,
                                sisUsuarioTO.getUsrCodigo());
                        if (sisUsuarioTO.getUsrCaduca() == null) {
                            fechaEnviar = null;
                        } else {
                            fechaEnviar = sisUsuarioAux.getUsrCaduca().toString();
                            Calendar c1 = Calendar.getInstance();
                            c1.setTime(sisUsuarioAux.getUsrCaduca());
                            fechaEnviar = UtilsValidacion.fecha(c1.getTime(), "yyyy-MM-dd HH:mm:ss");
                        }
                    } else {
                        fechaEnviar = UtilsValidacion
                                .fechaSumarDias(UtilsValidacion.fechaString_Date(sisUsuarioTO.getUsrCaduca()), 72);
                    }
                    sisUsuarioTO.setUsrCaduca(fechaEnviar);
                    // SUCESO
                    susClave = sisUsuarioTO.getUsrCodigo();
                    susTabla = "sistemaWeb.sis_usuario";
                    susDetalle = "Se modificó datos del usuario " + sisUsuarioTO.getUsrCodigo();
                    susSuceso = "UPDATE";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle,
                            sisInfoTO);
                    if (pass == null) {
                        SisUsuario sisUsuarioDato = usuarioDao.obtenerPorId(SisUsuario.class,
                                sisUsuarioTO.getUsrCodigo());
                        sisUsuarioTO.setUsrPassword(sisUsuarioDato.getUsrPassword());
                        sisUsuarioTO.setUsrInsertaUsuario("");
                        sisUsuarioTO.setUsrFechaInsertaUsuario(UtilsValidacion.fechaSistema());
                        sisUsuario = ConversionesSistema.ConvertirSisUsuarioTO_SisUsuario(sisUsuarioTO);
                        sisUsuario.setUsrCodigo(sisUsuarioTO.getUsrCodigo());
                        sisUsuario.setUsrCodigoInserta(sisInfoTO.getUsuario());
                        sisUsuario.setUsrFechaInsertaUsuario(sisUsuarioDato.getUsrFechaInsertaUsuario());
                        SisUsuarioDetalle sisUsuarioDetalle = new SisUsuarioDetalle(
                                new SisUsuarioDetallePK(sisInfoTO.getEmpresa(), sisUsuario.getUsrCodigo()), "",
                                true, UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                        sisUsuarioDetalle.setDetActivo(sisUsuario.getUsrActivo());
                        sisUsuarioDetalle.setSisUsuario(sisUsuario);
                        sisUsuario.setUsrActivo(true);
                        sisUsuarioDetalle.setSisGrupo(new SisGrupo(
                                new SisGrupoPK(sisInfoTO.getEmpresa(), sisUsuarioTO.getGruCodigo())));
                        comprobar = usuarioDao.modificarSisUsuario(sisUsuario, sisUsuarioDetalle, sisSuceso);
                        if (comprobar) {
                            retorno = "TUsuario modificado correctamente...";
                        } else {
                            retorno = "FHubo un error al modificar el usuario...\nIntente de nuevo o contacte con el administrador.";
                        }
                    } else {
                        SisUsuario sisUsuarioDato = usuarioDao.obtenerPorId(SisUsuario.class,
                                sisUsuarioTO.getUsrCodigo());
                        sisUsuarioTO.setUsrFechaInsertaUsuario(UtilsValidacion
                                .fecha(sisUsuarioDato.getUsrFechaInsertaUsuario(), "yyyy-MM-dd HH:mm:ss"));
                        sisUsuario = ConversionesSistema.ConvertirSisUsuarioTO_SisUsuario(sisUsuarioTO);
                        sisUsuario.setUsrCodigoInserta(sisInfoTO.getUsuario());
                        sisUsuario.setUsrFechaInsertaUsuario(sisUsuarioDato.getUsrFechaInsertaUsuario());
                        sisUsuario.setUsrCodigo(sisUsuarioTO.getUsrCodigo());

                        SisUsuarioDetalle sisUsuarioDetalle = new SisUsuarioDetalle(
                                new SisUsuarioDetallePK(sisInfoTO.getEmpresa(), sisUsuario.getUsrCodigo()), "",
                                true, UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                        sisUsuarioDetalle.setDetActivo(sisUsuario.getUsrActivo());
                        sisUsuarioDetalle.setSisUsuario(sisUsuario);
                        sisUsuario.setUsrActivo(true);
                        sisUsuarioDetalle.setSisGrupo(new SisGrupo(
                                new SisGrupoPK(sisInfoTO.getEmpresa(), sisUsuarioTO.getGruCodigo())));
                        comprobar = usuarioDao.modificarSisUsuario(sisUsuario, sisUsuarioDetalle, sisSuceso);
                        if (comprobar) {
                            retorno = "TUsuario modificado correctamente...";
                        } else {
                            retorno = "FHubo un error al modificar el usuario...\nIntente de nuevo o contacte con el administrador.";
                        }
                    }
                }
            }
        } else {
            retorno = "FEl Código del Usuario que va a modificar ya no existe...\nIntente con otro...";
        }
        return retorno;
    }

    @Override
    public boolean eliminarSisUsuarioTO(SisUsuarioTO sisUsuarioTO, SisInfoTO sisInfoTO) throws Exception {
        int contador = sucesoDao.retornoContadoEliminarUsuario(sisUsuarioTO.getUsrCodigo());
        SisUsuario sisUsuario = null;
        comprobar = false;
        if (contador == 0) {
            sisUsuarioTO.setUsrFechaInsertaUsuario(UtilsValidacion.fechaSistema());
            sisUsuario = ConversionesSistema.ConvertirSisUsuarioTO_SisUsuario(sisUsuarioTO);
            SisUsuarioDetalle sisUsuarioDetalle = new SisUsuarioDetalle(
                    new SisUsuarioDetallePK(sisInfoTO.getEmpresa(), sisUsuario.getUsrCodigo()), "", true,
                    UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
            sisUsuarioDetalle.setSisUsuario(sisUsuario);

            sisUsuarioDetalle.setSisGrupo(
                    new SisGrupo(new SisGrupoPK(sisInfoTO.getEmpresa(), sisUsuarioTO.getGruCodigo())));

            // SUCESO
            susClave = sisUsuarioTO.getUsrCodigo();
            susTabla = "sistemaWeb.sis_usuario";
            susDetalle = "Se eliminó datos del usuario " + sisUsuarioTO.getUsrCodigo();
            susSuceso = "DELETE";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprobar = usuarioDao.eliminarSisUsuario(sisUsuario, sisUsuarioDetalle, sisSuceso);
        }
        return comprobar;

    }

    @Override
    public List<SisConsultaUsuarioGrupoTO> getListaUsuario(String infEmpresa, String infUsuario) throws Exception {
        return usuarioDao.getListaUsuario(infEmpresa, infUsuario);
    }

    @Override
    public byte[] obtenerImagenUsuario(String nombre) throws Exception {
        InputStream is = UtilsArchivos.leerImagen(UtilsArchivos.getRutaImagenUsuario() + nombre + ".jpg");
        byte[] bytes = null;
        if (is != null) {
            bytes = new byte[is.available()];
            is.read(bytes);
        }
        return bytes;
    }

    @Override
    public byte[] obtenerImagenUsuarioDefault(String nombre) throws Exception {
        InputStream is = UtilsArchivos.leerImagen(UtilsArchivos.getRutaImagenUsuario() + nombre);
        byte[] bytes = null;
        if (is != null) {
            bytes = new byte[is.available()];
            is.read(bytes);
        }
        return bytes;
    }

    @Override
    public String guardarImagenUsuario(byte[] imagen, String nombre) throws Exception {
        return UtilsArchivos.guardarImagen(UtilsArchivos.getRutaImagenUsuario(), imagen, nombre, 150, 100);
    }

    @Override
    public byte[] generarReporteUsuario(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<SisConsultaUsuarioGrupoTO> listSisConsultaUsuarioGrupoTO)
            throws Exception {

        List<ReporteUsuario> listaReporteUsuario = new ArrayList<ReporteUsuario>();
        for (SisConsultaUsuarioGrupoTO sisConsultaUsuarioGrupoTO : listSisConsultaUsuarioGrupoTO) {
            ReporteUsuario reporteUsuario = new ReporteUsuario();
            reporteUsuario.setApellidos(sisConsultaUsuarioGrupoTO.getUsrApellido());
            reporteUsuario.setCodigo(sisConsultaUsuarioGrupoTO.getUsrCodigo());
            reporteUsuario.setEstado(sisConsultaUsuarioGrupoTO.getUsrActivo() ? "Activo" : "Inactivo");
            reporteUsuario.setGrupo(sisConsultaUsuarioGrupoTO.getGruCodigo());
            reporteUsuario.setNickName(sisConsultaUsuarioGrupoTO.getUsrNick());
            reporteUsuario.setNombres(sisConsultaUsuarioGrupoTO.getUsrNombre());
            listaReporteUsuario.add(reporteUsuario);
        }
        return genericReporteService.generarReporte(modulo, "reportUsuario.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaReporteUsuario);
    }

    @Override
    public String modificarSisUsuario(SisUsuario sisUsuario, byte[] imagen, SisInfoTO sisInfoTO) throws Exception {
        Encriptacion encriptacion = new Encriptacion();
        sisUsuario.setUsrFechaInsertaUsuario(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
        SisUsuario usuarioAux = usuarioDao.obtenerPorId(SisUsuario.class, sisUsuario.getUsrCodigo().trim());
        if (usuarioAux != null) {
            String nombreArchivo = sisUsuario.getUsrNick() + "_" + sisUsuario.getUsrNombre() + "_" + sisUsuario.getUsrApellido();
            if (sisUsuario.getUsrPassword() == null || sisUsuario.getUsrPassword().equals("")) {
                throw new GeneralException("El usuario debe tener una clave de acceso.");
            }
            sisUsuario.setUsrPassword(
                    usuarioAux.getUsrPassword() != null && usuarioAux.getUsrPassword().equalsIgnoreCase(sisUsuario.getUsrPassword()) ? sisUsuario.getUsrPassword() : encriptacion.sha256(sisUsuario.getUsrPassword())
            );
            if (sisUsuario.getUsrPasswordEmail() != null) {
                sisUsuario.setUsrPasswordEmail(
                        usuarioAux.getUsrPasswordEmail() != null && usuarioAux.getUsrPasswordEmail().equalsIgnoreCase(sisUsuario.getUsrPasswordEmail()) ? sisUsuario.getUsrPasswordEmail() : this.encriptarClaveEmail(sisUsuario.getUsrPasswordEmail())
                );
            }
            if (imagen != null) {
                guardarImagenUsuario(imagen, nombreArchivo);
            } else {
                UtilsArchivos.eliminarImagen(UtilsArchivos.getRutaImagenUsuario(), nombreArchivo);
            }
            usuarioDao.actualizar(sisUsuario);
            return "El usuario " + sisUsuario.getUsrCodigo() + " se ha modificado correctamente.";
        } else {
            throw new GeneralException("El objeto a modificar no existe.");
        }
    }

    @Override
    public boolean claveActualValida(String clave, String codigoUsuario) throws Exception {
        Encriptacion encriptacion = new Encriptacion();
        SisUsuario usuarioAux = usuarioDao.obtenerPorId(SisUsuario.class, codigoUsuario.trim());
        if (usuarioAux != null) {
            clave = encriptacion.sha256(clave);
            return usuarioAux.getUsrPassword().equalsIgnoreCase(clave);
        } else {
            return false;
        }
    }

    public String encriptarClaveEmail(String claveEmail) {
        if (!claveEmail.isEmpty()) {
            Encriptacion encriptacion1 = new Encriptacion();
            java.math.BigInteger[] textoPass = encriptacion1.encripta(claveEmail);
            String clavePass = "";
            for (BigInteger textoPas : textoPass) {
                clavePass = clavePass + textoPas.toString(16).toUpperCase() + "G";
            }
            String n = encriptacion1.damen().toString(16);
            String d = encriptacion1.damed().toString(16);
            clavePass = n.length() + encriptacion1.damen().toString(16).toUpperCase() + clavePass
                    + encriptacion1.damed().toString(16).toUpperCase() + d.length();
            return clavePass;
        }
        return null;
    }

    @Override
    public Map<String, Object> obtenerPerfilDeUsuario(String usrNick) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        String foto;
        SisUsuario usuario = obtenerPorId(usrNick);
        if (usuario != null) {
            String nombreArchivo = usuario.getUsrNick() + "_" + usuario.getUsrNombre() + "_" + usuario.getUsrApellido();
            byte[] imagen = obtenerImagenUsuario(nombreArchivo);
            if (imagen != null && imagen.length > 0) {
                foto = Base64.getEncoder().encodeToString(imagen);
            } else {
                imagen = obtenerImagenUsuarioDefault("default.png");
                foto = Base64.getEncoder().encodeToString(imagen);
            }
            campos.put("usuario", usuario);
            campos.put("foto", foto);
            return campos;
        } else {
            throw new GeneralException("No existe usuario.");
        }
    }

}
