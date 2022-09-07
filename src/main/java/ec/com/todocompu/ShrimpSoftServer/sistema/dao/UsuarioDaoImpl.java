package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisConsultaUsuarioGrupoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisLoginTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuario;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuarioDetalle;

@Repository
public class UsuarioDaoImpl extends GenericDaoImpl<SisUsuario, String> implements UsuarioDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Autowired
    private SucesoDao sucesoDao;

    @Autowired
    private UsuarioDetalleDao usuarioDetalleDao;

    @Override
    public SisLoginTO getSisAcceso(String nick, String password, String empresa) throws Exception {
        SisLoginTO sisLoginTO = null;
        String loginPassword = "";
        Boolean loginActivo = null;
        Boolean loginCaducado = null;
        Boolean loginCambiarPassword = null;
        String completarWhere = "";
        try {
            if (empresa != null && empresa.compareTo("") != 0) {
                completarWhere = "AND login_empresa=('" + empresa + "')";
            }
            Object[] array = (Object[]) genericSQLDao
                    .obtenerPorSql(
                            "SELECT * FROM sistemaweb.sis_login WHERE login_nick=('" + nick + "') " + completarWhere)
                    .get(0);
            if (array != null) {
                sisLoginTO = new SisLoginTO();
                sisLoginTO.setUsrCodigo((String) UtilsConversiones.convertir(array[1]));
                sisLoginTO.setUsrNick((String) UtilsConversiones.convertir(array[2]));
                sisLoginTO.setUsrNombre((String) UtilsConversiones.convertir(array[3]));
                sisLoginTO.setUsrApellido((String) UtilsConversiones.convertir(array[4]));
                sisLoginTO.setUsrEmail((String) UtilsConversiones.convertir(array[5]));
                sisLoginTO.setUsrTelefono((String) UtilsConversiones.convertir(array[6]));
                loginPassword = (String) UtilsConversiones.convertir(array[7]);
                loginActivo = (Boolean) UtilsConversiones.convertir(array[8]);
                loginCaducado = (Boolean) UtilsConversiones.convertir(array[9]);
                loginCambiarPassword = (Boolean) UtilsConversiones.convertir(array[10]);
                sisLoginTO.setPerEliminarFilas((Boolean) UtilsConversiones.convertir(array[11]));
                sisLoginTO.setPerCambiarPrecio((Boolean) UtilsConversiones.convertir(array[12]));
                sisLoginTO.setPerAplicarDescuentos((Boolean) UtilsConversiones.convertir(array[13]));
                sisLoginTO.setPerCambiarFecha((Boolean) UtilsConversiones.convertir(array[14]));
                sisLoginTO.setPerMotivoPermitido((String) UtilsConversiones.convertir(array[15]));
                sisLoginTO.setPerBodegaPermitida((String) UtilsConversiones.convertir(array[16]));
                sisLoginTO.setPerDocumentoPermitido((String) UtilsConversiones.convertir(array[17]));
                sisLoginTO.setPerSecuencialPermitidoVentas((String) UtilsConversiones.convertir(array[18]));
                sisLoginTO.setPerSecuencialPermitidoRetenciones((String) UtilsConversiones.convertir(array[19]));
                sisLoginTO.setPerSecuencialPermitidoGuias((String) UtilsConversiones.convertir(array[20]));
                sisLoginTO.setPerFormaPagoPermitida((String) UtilsConversiones.convertir(array[21]));
                sisLoginTO.setPerPermisoCredito((Boolean) UtilsConversiones.convertir(array[22]));
                sisLoginTO.setPerPrecioPermitido((Integer) UtilsConversiones.convertir(array[23]));
                sisLoginTO.setPerSecuencialPermitidoNotaCredito((String) UtilsConversiones.convertir(array[24]));
                sisLoginTO.setPerSecuencialPermitidoNotaDebito((String) UtilsConversiones.convertir(array[25]));
                loginPassword = loginPassword.trim();
                if (loginPassword.equals(password)) {
                    if (!loginActivo) {
                        sisLoginTO.setUsrEstado("DENEGADO");
                    } else {
                        if ((loginActivo) && (loginCaducado) && (loginCambiarPassword)) {
                            sisLoginTO.setUsrEstado("CADUCAPASSWORD");
                        }
                        if ((loginActivo) && (!loginCaducado) && (loginCambiarPassword)) {
                            sisLoginTO.setUsrEstado("PASSWORD");
                        }
                        if ((loginActivo) && (loginCaducado) && (!loginCambiarPassword)) {
                            sisLoginTO.setUsrEstado("CADUCA");
                        }
                        if ((loginActivo) && (!loginCaducado) && (!loginCambiarPassword)) {
                            sisLoginTO.setUsrEstado("CONCEDIDO");
                        }
                    }
                } else {
                    sisLoginTO.setUsrEstado("ERROR");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sisLoginTO;
    }

    @Override
    public SisLoginTO getPermisoInventarioTO(String infEmpresa, String infUsuario) throws Exception {
        SisLoginTO sisLoginTO = new SisLoginTO();
        try {
            String sql = "SELECT * FROM sistemaweb.sis_login WHERE login_empresa=('" + infEmpresa + "') AND login_codigo=('" + infUsuario + "')";
            Object[] array = (Object[]) genericSQLDao.obtenerPorSql(sql).get(0);
            if (array != null) {
                sisLoginTO.setPerEliminarFilas((Boolean) UtilsConversiones.convertir(array[11]));
                sisLoginTO.setPerCambiarPrecio((Boolean) UtilsConversiones.convertir(array[12]));
                sisLoginTO.setPerAplicarDescuentos((Boolean) UtilsConversiones.convertir(array[13]));
                sisLoginTO.setPerCambiarFecha((Boolean) UtilsConversiones.convertir(array[14]));
                sisLoginTO.setPerMotivoPermitido((String) UtilsConversiones.convertir(array[15]));
                sisLoginTO.setPerBodegaPermitida((String) UtilsConversiones.convertir(array[16]));
                sisLoginTO.setPerDocumentoPermitido((String) UtilsConversiones.convertir(array[17]));
                sisLoginTO.setPerSecuencialPermitidoVentas((String) UtilsConversiones.convertir(array[18]));
                sisLoginTO.setPerSecuencialPermitidoRetenciones((String) UtilsConversiones.convertir(array[19]));
                sisLoginTO.setPerSecuencialPermitidoGuias((String) UtilsConversiones.convertir(array[20]));
                sisLoginTO.setPerFormaPagoPermitida((String) UtilsConversiones.convertir(array[21]));
                sisLoginTO.setPerPermisoCredito((Boolean) UtilsConversiones.convertir(array[22]));
                sisLoginTO.setPerPrecioPermitido((Integer) UtilsConversiones.convertir(array[23]));
                sisLoginTO.setPerSecuencialPermitidoNotaCredito((String) UtilsConversiones.convertir(array[24]));
                sisLoginTO.setPerSecuencialPermitidoNotaDebito((String) UtilsConversiones.convertir(array[25]));
                sisLoginTO.setPerSecuencialPermitidoLiquidacionCompras((String) UtilsConversiones.convertir(array[26]));
            } else {
                sisLoginTO = null;
            }
        } catch (Exception e) {
            sisLoginTO = null;
        }
        return sisLoginTO;
    }

    @Override
    public List<SisConsultaUsuarioGrupoTO> getListaUsuario(String infEmpresa, String infUsuario) throws Exception {
        infUsuario = infUsuario == null ? null : "'" + infUsuario + "'";
        String sql = null;
        if (infUsuario != null && infUsuario.equals("'INACTIVO'")) {
            sql = "SELECT u.usr_codigo, usr_nick, usr_nombre, usr_apellido, usr_password, usr_caduca, det_activo usr_activo, usr_cambiar_contrasenia, usr_email_usuario, usr_email_password, usr_codigo_inserta,"
                    + "u.usr_fecha_inserta_usuario, usr_ip, gru_codigo, usr_telefono FROM sistemaweb.sis_usuario u LEFT JOIN sistemaweb.sis_usuario_detalle d ON '" + infEmpresa + "' = d.det_empresa AND u.usr_codigo = d.det_usuario "
                    + "WHERE det_activo=false ORDER BY u.usr_codigo;";
        } else {
            sql = "SELECT u.usr_codigo, usr_nick, usr_nombre, usr_apellido, usr_password,"
                    + " usr_caduca, det_activo usr_activo, usr_cambiar_contrasenia, usr_email_usuario,"
                    + " usr_email_password, usr_codigo_inserta, u.usr_fecha_inserta_usuario, usr_ip, gru_codigo, usr_telefono"
                    + " FROM sistemaweb.sis_usuario u LEFT JOIN sistemaweb.sis_usuario_detalle d"
                    + " ON '" + infEmpresa + "' = d.det_empresa AND u.usr_codigo = d.det_usuario"
                    + " WHERE CASE WHEN " + infUsuario + " = 'TODOS' THEN TRUE ELSE (CASE WHEN " + infUsuario
                    + " IS NOT NULL THEN u.usr_codigo=" + infUsuario + " ELSE gru_codigo IS NOT NULL END) END "
                    + "ORDER BY u.usr_codigo;";
        }
        return genericSQLDao.obtenerPorSql(sql, SisConsultaUsuarioGrupoTO.class);
    }

    @Override
    public boolean insertarSisUsuario(SisUsuario sisUsuario, SisUsuarioDetalle sisUsuarioDetalle, SisSuceso sisSuceso)
            throws Exception {
        insertar(sisUsuario);
        usuarioDetalleDao.insertar(sisUsuarioDetalle);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean insertarSisUsuarioDetalle(SisUsuario sisUsuario, SisUsuarioDetalle sisUsuarioDetalle,
            SisSuceso sisSuceso) throws Exception {
        saveOrUpdate(sisUsuario);
        usuarioDetalleDao.saveOrUpdate(sisUsuarioDetalle);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarSisUsuario(SisUsuario sisUsuario) throws Exception {
        actualizar(sisUsuario);
        return true;
    }

    @Override
    public boolean modificarSisUsuario(SisUsuario sisUsuario, SisUsuarioDetalle sisUsuarioDetalle, SisSuceso sisSuceso)
            throws Exception {
        sisUsuario.setUsrIP(sisUsuario.getUsrIP() == null || sisUsuario.getUsrIP().compareToIgnoreCase("") == 0 ? null
                : sisUsuario.getUsrIP());
        actualizar(sisUsuario);
        usuarioDetalleDao.actualizar(sisUsuarioDetalle);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarSisUsuario(SisUsuario sisUsuario, SisUsuarioDetalle sisUsuarioDetalle, SisSuceso sisSuceso)
            throws Exception {
        usuarioDetalleDao.eliminar(sisUsuarioDetalle);
        eliminar(sisUsuario);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

}
