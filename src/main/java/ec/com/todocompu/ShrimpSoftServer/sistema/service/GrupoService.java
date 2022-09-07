package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisGrupoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

public interface GrupoService {

    @Transactional
    public SisGrupoTO sisGrupoUsuariosTO(String infEmpresa, String infUsuario) throws Exception;

    @Transactional
    public boolean accionSisGrupoTO(SisGrupoTO sisGrupoTO, char accion, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public List<SisGrupoTO> getSisGrupoTOs(String empresa) throws Exception;

    @Transactional
    public List<SisGrupoTO> getListaSisGrupo(String empresa) throws Exception;

}
