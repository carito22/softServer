package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaEmpresaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaUsuarioTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisUsuarioEmailTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresa;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuarioDetalle;

@Repository
public class UsuarioDetalleDaoImpl extends GenericDaoImpl<SisUsuarioDetalle, String> implements UsuarioDetalleDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<SisListaEmpresaTO> getListaLoginEmpresaTO(String usrCodigo) throws Exception {
        String sql = "SELECT sis_empresa.emp_codigo, emp_ruc, emp_razon_social, "
                + "emp_nombre, emp_direccion, emp_telefono, emp_pais, emp_ciudad, par_gerente, "
                + "par_gerente_id, par_contador as \"empContador\", par_contador_ruc as \"empContadorRuc\", par_actividad, "
                + "par_escoger_precio_por, par_obligado_llevar_contabilidad, det_equipo, "
                + "par_resolucion_contribuyente_especial, par_ruta_reportes, par_ruta_logo, emp_email, emp_clave, "
                + "par_web_documentos_electronicos, par_obligado_emitir_documentos_electronicos, "
                + "par_obligado_registrar_liquidacion_pesca FROM sistemaweb.sis_usuario_detalle "
                + "INNER JOIN sistemaweb.sis_empresa LEFT JOIN sistemaweb.sis_empresa_parametros "
                + "ON sis_empresa.emp_codigo = sis_empresa_parametros.emp_codigo "
                + "ON sis_usuario_detalle.det_empresa = sis_empresa.emp_codigo "
                + "WHERE sis_usuario_detalle.usr_codigo = '" + usrCodigo + "' "
                + "AND det_activo ORDER BY sis_empresa.emp_nombre;";
        return genericSQLDao.obtenerPorSql(sql, SisListaEmpresaTO.class);
    }

    @Override
    public List<SisEmpresa> getEmpresasPorUsuarioItem(String usuario, String item) throws Exception {
        String sql = "SELECT sis_empresa.emp_codigo, emp_ruc, emp_nombre, "
                + "emp_razon_social, emp_direccion, emp_ciudad , emp_telefono , emp_celular, "
                + "emp_email , emp_clave , "
                + "emp_activa , sis_empresa.usr_codigo, "
                + "usr_fecha_inserta_empresa, emp_pais "
                + "FROM sistemaweb.sis_usuario_detalle INNER "
                + "JOIN sistemaweb.sis_empresa LEFT JOIN sistemaweb.sis_empresa_parametros "
                + "ON sis_empresa.emp_codigo = sis_empresa_parametros.emp_codigo "
                + "ON sis_usuario_detalle.det_empresa = sis_empresa.emp_codigo LEFT JOIN sistemaweb.sis_permiso "
                + "ON sis_usuario_detalle.gru_empresa = sis_permiso.per_empresa AND "
                + "POSITION(gru_codigo IN sis_permiso.per_usuarios)!=0 AND per_item = '" + item + "' "
                + "WHERE sis_usuario_detalle.usr_codigo = '" + usuario + "' AND det_activo AND per_empresa IS NOT NULL "
                + "ORDER BY sis_empresa.emp_nombre;";
        return genericSQLDao.obtenerPorSql(sql, SisEmpresa.class);
    }

    @Override
    public List<SisEmpresa> getEmpresasPorUsuarioItem(String usuario, String item, String empresa) throws Exception {
        String sql = "SELECT sis_empresa.emp_codigo, emp_ruc, emp_nombre, "
                + "emp_razon_social, emp_direccion, emp_ciudad , emp_telefono , emp_celular, "
                + "emp_email , emp_clave , "
                + "emp_activa , sis_empresa.usr_codigo, "
                + "usr_fecha_inserta_empresa, emp_pais "
                + "FROM sistemaweb.sis_usuario_detalle INNER "
                + "JOIN sistemaweb.sis_empresa LEFT JOIN sistemaweb.sis_empresa_parametros "
                + "ON sis_empresa.emp_codigo = sis_empresa_parametros.emp_codigo "
                + "ON sis_usuario_detalle.det_empresa = sis_empresa.emp_codigo LEFT JOIN sistemaweb.sis_permiso "
                + "ON sis_usuario_detalle.gru_empresa = sis_permiso.per_empresa AND "
                + "POSITION(gru_codigo IN sis_permiso.per_usuarios)!=0 AND per_item = '" + item + "' "
                + "WHERE sis_usuario_detalle.usr_codigo = '" + usuario + "' AND det_activo AND per_empresa = '" + empresa + "' "
                + "ORDER BY sis_empresa.emp_nombre;";
        return genericSQLDao.obtenerPorSql(sql, SisEmpresa.class);
    }

    @Override
    public boolean getUsuarioRepetido(String codigo, String nick, String nombre, String apellido) throws Exception {
        String sql = "SELECT * FROM sistemaweb." + "fun_sw_usuario_repetido(" + codigo + ", " + nick + ", " + nombre
                + ", " + apellido + ")";

        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public int retornoContadoEliminarGrupo(String codEmpresa, String codGrupo) throws Exception {
        String sql = "select count(usr_codigo) from sistemaweb.sis_usuario_detalle " + "where gru_codigo = ('"
                + codGrupo.trim() + "') and det_empresa = ('" + codEmpresa.trim() + "')";

        return (int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String getUsuarioNombreApellido(String usrCodigo) throws Exception {
        String sql = "SELECT usr_nombre || ' ' || usr_apellido "
                + "FROM sistemaweb.sis_usuario WHERE usr_codigo = '" + usrCodigo + "'";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public List<SisListaUsuarioTO> getListaSisUsuario(String empresa) throws Exception {
        String var = "('" + empresa.trim() + "')";
        String sql = "SELECT sis_usuario.usr_codigo, usr_nombre, usr_apellido, sis_usuario.usr_email_usuario " + "FROM sistemaweb.sis_usuario_detalle "
                + "INNER JOIN sistemaweb.sis_usuario " + "ON sis_usuario.usr_codigo = sis_usuario_detalle.usr_codigo  "
                + "WHERE det_empresa = " + var + "AND usr_activo ORDER BY usr_nombre, usr_apellido";

        return genericSQLDao.obtenerPorSql(sql, SisListaUsuarioTO.class);
    }

    @Override
    public List<SisUsuarioEmailTO> obtenerCorreosADM(String empresa) throws Exception {
        String sql = "select u.usr_codigo, u.usr_nombre, u.usr_apellido, u.usr_email_usuario as usr_email, ud.det_empresa as usr_empresa from sistemaweb.sis_usuario u "
                + "inner join sistemaweb.sis_usuario_detalle ud on ud.det_usuario = u.usr_codigo "
                + "where ud.gru_codigo = 'ADM' and ud.det_empresa = '" + empresa + "' and u.usr_email_usuario is not null and u.usr_email_usuario!='';";

        return genericSQLDao.obtenerPorSql(sql, SisUsuarioEmailTO.class);
    }

    @Override
    public List<SisListaUsuarioTO> getListaSisUsuariosNoTieneConfCompras(String empresa) throws Exception {
        String var = "('" + empresa.trim() + "')";
        String sql = "SELECT sis_usuario.usr_codigo, usr_nombre, usr_apellido, sis_usuario.usr_email_usuario " + "FROM sistemaweb.sis_usuario_detalle "
                + "INNER JOIN sistemaweb.sis_usuario " + "ON sis_usuario.usr_codigo = sis_usuario_detalle.usr_codigo LEFT JOIN sistemaweb.sis_configuracion_compras "
                + "ON sis_configuracion_compras.conf_usuario_responsable = sis_usuario.usr_codigo  "
                + "WHERE det_empresa = " + var + "AND usr_activo and sis_configuracion_compras.conf_usuario_responsable is null ORDER BY usr_nombre, usr_apellido";

        return genericSQLDao.obtenerPorSql(sql, SisListaUsuarioTO.class);
    }

    @Override
    public SisUsuarioDetalle obtenerDetalleUsuario(String empresa, String usuario) throws Exception {
        String sql = "SELECT * FROM sistemaweb.sis_usuario_detalle WHERE det_empresa='" + empresa + "' AND det_usuario='" + usuario + "'";
        List<SisUsuarioDetalle> lista =  genericSQLDao.obtenerPorSql(sql, SisUsuarioDetalle.class);
        return lista.isEmpty() == true ? null : lista.get(0);
    }

}
