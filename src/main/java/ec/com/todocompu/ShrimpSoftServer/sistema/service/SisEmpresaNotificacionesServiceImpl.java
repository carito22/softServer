package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SisEmpresaNotificacionesDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SisEventoNotificacionDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.UtilsMail;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificacionesEventos;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.Date;

@Service
public class SisEmpresaNotificacionesServiceImpl implements SisEmpresaNotificacionesService {

    @Autowired
    private SisEmpresaNotificacionesDao sisEmpresaNotificacionesDao;
    @Autowired
    private SisEventoNotificacionDao sisEventoNotificacionDao;

    @Autowired
    private GenericSQLDao genericSQLDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<SisEmpresaNotificaciones> listarSisEmpresaNotificaciones(String empresa) throws Exception {
        return sisEmpresaNotificacionesDao.listarSisEmpresaNotificaciones(empresa);
    }

    @Override
    public SisEmpresaNotificaciones insertarSisEmpresaNotificaciones(SisEmpresaNotificaciones sisEmpresaNotificaciones, SisInfoTO sisInfoTO) throws Exception {
        sisEmpresaNotificaciones.setUsrFechaInsertaEmpresa(new Date());
        sisEmpresaNotificaciones.setEmpCodigo(sisInfoTO.getEmpresa());
        sisEmpresaNotificaciones.setUsrCodigo(sisInfoTO.getUsuario());
        if (sisEmpresaNotificacionesDao.obtenerPorId(SisEmpresaNotificaciones.class, sisEmpresaNotificaciones.getId()) == null) {
            if (!UtilsMail.esUnaEntidadVerificada(sisEmpresaNotificaciones.getIdPrincipal())) {
                UtilsMail.verificarEmail(sisEmpresaNotificaciones.getIdPrincipal());
            } else {
                sisEmpresaNotificaciones.setIdVerificado(Boolean.TRUE);
            }
            susClave = sisEmpresaNotificaciones.getId() + "";
            susDetalle = "Se insertó la configuración de notificaciones: " + sisEmpresaNotificaciones.getIdDescripcion();
            susSuceso = "INSERT";
            susTabla = "sistemaweb.sis_empresa_notificaciones";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            sisEmpresaNotificacionesDao.insertarSisEmpresaNotificaciones(sisEmpresaNotificaciones, sisSuceso);
        }
        return sisEmpresaNotificaciones;
    }

    @Override
    public SisEmpresaNotificaciones modificarSisEmpresaNotificaciones(SisEmpresaNotificaciones sisEmpresaNotificaciones, SisInfoTO sisInfoTO) throws Exception {
        sisEmpresaNotificaciones.setUsrFechaInsertaEmpresa(new Date());
        sisEmpresaNotificaciones.setEmpCodigo(sisInfoTO.getEmpresa());
        sisEmpresaNotificaciones.setUsrCodigo(sisInfoTO.getUsuario());
        if (sisEmpresaNotificacionesDao.obtenerPorId(SisEmpresaNotificaciones.class, sisEmpresaNotificaciones.getId()) != null) {
            if (!UtilsMail.esUnaEntidadVerificada(sisEmpresaNotificaciones.getIdPrincipal())) {
                sisEmpresaNotificaciones.setIdVerificado(Boolean.FALSE);
            } else {
                sisEmpresaNotificaciones.setIdVerificado(Boolean.TRUE);
            }
            susClave = sisEmpresaNotificaciones.getId() + "";
            susDetalle = "Se modificó la configuración de notificaciones: " + sisEmpresaNotificaciones.getIdDescripcion();
            susSuceso = "UPDATE";
            susTabla = "sistemaweb.sis_empresa_notificaciones";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            sisEmpresaNotificacionesDao.modificarSisEmpresaNotificaciones(sisEmpresaNotificaciones, sisSuceso);
        }
        return sisEmpresaNotificaciones;
    }

    @Override
    public boolean eliminarSisEmpresaNotificaciones(Integer idNotificaciones, SisInfoTO sisInfoTO) throws Exception {
        SisEmpresaNotificaciones notificacion = sisEmpresaNotificacionesDao.obtenerPorId(SisEmpresaNotificaciones.class, idNotificaciones);
        if (notificacion != null) {
            susClave = idNotificaciones + "";
            susTabla = "sistemaweb.sis_periodo";
            susDetalle = "Se eliminó la configuración de notificaciones: " + notificacion.getIdDescripcion();
            susSuceso = "DELETE";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            sisEmpresaNotificacionesDao.eliminarSisEmpresaNotificaciones(idNotificaciones, sisSuceso);
        }
        return true;
    }

    @Override
    public boolean verificarSisEmpresaNotificaciones(List<SisEmpresaNotificaciones> sisEmpresaNotificaciones, boolean enviarEmail, SisInfoTO sisInfoTO) throws Exception {
        for (SisEmpresaNotificaciones notificacion : sisEmpresaNotificaciones) {
            if (notificacion != null) {
                if (!UtilsMail.esUnaEntidadVerificada(notificacion.getIdPrincipal())) {
                    if (enviarEmail) {
                        UtilsMail.verificarEmail(notificacion.getIdPrincipal());
                    }
                    notificacion.setIdVerificado(Boolean.FALSE);
                    sisEmpresaNotificacionesDao.actualizar(notificacion);
                } else {
                    notificacion.setIdVerificado(Boolean.TRUE);
                    susClave = notificacion.getId() + "";
                    susDetalle = "Se verificó la configuración de notificaciones: " + notificacion.getIdDescripcion();
                    susSuceso = "UPDATE";
                    susTabla = "sistemaweb.sis_empresa_notificaciones";
                    SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                    sisEmpresaNotificacionesDao.modificarSisEmpresaNotificaciones(notificacion, sisSuceso);
                }
            }
        }
        return true;
    }

    @Override
    public List<SisEmpresaNotificacionesEventos> listarSisEventoNotificacion() throws Exception {
        return sisEventoNotificacionDao.listarSisEventoNotificacion();
    }

    @Override
    public String borrarCorreoListaNegra(String correo, SisInfoTO sisInfoTO) throws Exception {
        String sql = "SELECT * FROM sistemaweb.fun_actualizar_correo_lista_negra('" + correo + "')";
        String respuestaFuncion = (String) genericSQLDao.obtenerObjetoPorSql(sql);
        return respuestaFuncion;
    }
    
    @Override
    public boolean existeCorreoEnListaNegra(String correo) throws Exception {
        String sql = "SELECT * FROM sistemaweb.fun_verificar_correo_lista_negra('" + correo + "')";
        String respuestaFuncion = (String) genericSQLDao.obtenerObjetoPorSql(sql);
        if(respuestaFuncion!=null && respuestaFuncion.charAt(0)== 'T'){
            return true;
        }
        return false;
    }

    @Override
    public String verificarCorreo(String correo, SisInfoTO sisInfoTO) throws Exception {
        if (!UtilsMail.esUnaEntidadVerificada(correo)) {
            UtilsMail.verificarEmail(correo);
        } else {
            return "TCorreo valido";
        }
        return "FEl correo no se encuentra verificado. Revise su correo electrónico, luego de verificar presione F5";
    }

}
