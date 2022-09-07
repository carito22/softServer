package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRolMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface RolMotivoService {

    public RhRolMotivo getRhRolMotivo(String empresa, String codigo) throws Exception;

    public String insertarRhRolMotivo(RhRolMotivo rhRolMotivo, SisInfoTO sisInfoTO) throws Exception;

    public String modificarRhRolMotivo(RhRolMotivo rhRolMotivo, SisInfoTO sisInfoTO) throws Exception;

    public String modificarEstadoRhRolMotivo(RhRolMotivo rhRolMotivo, boolean estado, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarRhRolMotivo(RhRolMotivo rhRolMotivo, SisInfoTO sisInfoTO) throws Exception;

    public List<RhRolMotivo> getListaRhRolMotivo(String empresa) throws Exception;

    public List<RhRolMotivo> getListaRhRolMotivos(String empresa, boolean inactivo) throws Exception;

}
