package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionInp;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionInpPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface GuiaInpDao extends GenericDao<InvGuiaRemisionInp, InvGuiaRemisionInpPK> {

    public InvGuiaRemisionInp buscarInvGuiaRemisionInp(String empresa, String codigo, String cliente) throws Exception;

    public boolean eliminarInvGuiaRemisionInp(InvGuiaRemisionInp guiaInp, SisSuceso sisSuceso);

    public boolean insertarInvGuiaRemisionInp(InvGuiaRemisionInp guiaInp, SisSuceso sisSuceso) throws Exception;

    public boolean modificarInvGuiaRemisionInp(InvGuiaRemisionInp guiaInp, SisSuceso sisSuceso) throws Exception;

    public List<InvGuiaRemisionInp> getInvGuiaRemisionInp(String empresa, String cliente, boolean inactivo) throws Exception;

}
