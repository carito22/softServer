package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificacionesEventos;

@Transactional
public interface SisEmpresaNotificacionesService {

    public List<SisEmpresaNotificaciones> listarSisEmpresaNotificaciones(String empresa) throws Exception;

    public SisEmpresaNotificaciones insertarSisEmpresaNotificaciones(SisEmpresaNotificaciones sisEmpresaNotificaciones, SisInfoTO sisInfoTO) throws Exception;

    public SisEmpresaNotificaciones modificarSisEmpresaNotificaciones(SisEmpresaNotificaciones sisEmpresaNotificaciones, SisInfoTO sisInfoTO) throws Exception;

    public boolean eliminarSisEmpresaNotificaciones(Integer idNotificaciones, SisInfoTO sisInfoTO) throws Exception;

    public boolean verificarSisEmpresaNotificaciones(List<SisEmpresaNotificaciones> sisEmpresaNotificaciones, boolean enviarEmail, SisInfoTO sisInfoTO) throws Exception;

    public List<SisEmpresaNotificacionesEventos> listarSisEventoNotificacion() throws Exception;

    public String borrarCorreoListaNegra(String correo, SisInfoTO sisInfoTO) throws Exception;

    //verificar correo 
    public String verificarCorreo(String correo, SisInfoTO sisInfoTO) throws Exception;

    public boolean existeCorreoEnListaNegra(String correo) throws Exception;
}
