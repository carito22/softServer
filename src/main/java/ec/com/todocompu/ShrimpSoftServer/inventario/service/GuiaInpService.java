package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionInp;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface GuiaInpService {

    public String insertarInvGuiaRemisionInp(InvGuiaRemisionInp guiaInp, SisInfoTO sisInfoTO) throws Exception;

    public String modificarEstadoInvGuiaRemisionInp(InvGuiaRemisionInp guiaInp, boolean estado, SisInfoTO sisInfoTO) throws Exception;

    public String modificarInvGuiaRemisionInp(InvGuiaRemisionInp guiaInp, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarInvGuiaRemisionInp(InvGuiaRemisionInp guiaInp, SisInfoTO sisInfoTO) throws Exception;

    public List<InvGuiaRemisionInp> getInvGuiaRemisionInp(String empresa, String cliente, boolean inacivo) throws Exception;
}
