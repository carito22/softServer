package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConEstructuraTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConEstructura;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface EstructuraDao extends GenericDao<ConEstructura, String> {

    public List<ConEstructuraTO> getListaConEstructuraTO(String empresa) throws Exception;

    public boolean insertarConEstructura(ConEstructura conEstructura, SisSuceso sisSuceso) throws Exception;

    public boolean modificarConEstructura(ConEstructura conEstructura, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarConEstructura(ConEstructura conEstructura, SisSuceso sisSuceso) throws Exception;
}
