package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisConsultaUsuarioGrupoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisLoginTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuario;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuarioDetalle;

public interface UsuarioDao extends GenericDao<SisUsuario, String> {

    public SisLoginTO getSisAcceso(String nick, String password, String empresa) throws Exception;

    public SisLoginTO getPermisoInventarioTO(String infEmpresa, String infUsuario) throws Exception;
    
    public List<SisConsultaUsuarioGrupoTO> getListaUsuario(String infEmpresa, String infUsuario) throws Exception;

    public boolean insertarSisUsuario(SisUsuario sisUsuario, SisUsuarioDetalle sisUsuarioDetalle, SisSuceso sisSuceso)
            throws Exception;

    public boolean insertarSisUsuarioDetalle(SisUsuario sisUsuario, SisUsuarioDetalle sisUsuarioDetalle,
            SisSuceso sisSuceso) throws Exception;

    public boolean modificarSisUsuario(SisUsuario sisUsuario) throws Exception;

    public boolean modificarSisUsuario(SisUsuario sisUsuario, SisUsuarioDetalle sisUsuarioDetalle, SisSuceso sisSuceso)
            throws Exception;

    public boolean eliminarSisUsuario(SisUsuario sisUsuario, SisUsuarioDetalle sisUsuarioDetalle, SisSuceso sisSuceso)
            throws Exception;

}
