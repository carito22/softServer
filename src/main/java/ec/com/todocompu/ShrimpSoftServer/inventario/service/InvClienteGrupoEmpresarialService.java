package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteGrupoEmpresarialTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteGrupoEmpresarial;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteGrupoEmpresarialPK;
import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;

@Transactional
public interface InvClienteGrupoEmpresarialService {

    public InvClienteGrupoEmpresarial obtenerInvClienteGrupoEmpresarial(String empresa, String codigo) throws Exception;

    public InvClienteGrupoEmpresarial insertarInvClienteGrupoEmpresarial(InvClienteGrupoEmpresarial invClienteGrupoEmpresarial, SisInfoTO sisInfoTO) throws Exception;

    public InvClienteGrupoEmpresarial modificarInvClienteGrupoEmpresarial(InvClienteGrupoEmpresarial invClienteGrupoEmpresarial, SisInfoTO sisInfoTO) throws GeneralException;

    public List<InvClienteGrupoEmpresarial> getInvClienteGrupoEmpresarial(String empresa, String busqueda) throws Exception;

    public List<InvClienteGrupoEmpresarialTO> getInvClienteGrupoEmpresarialTO(String empresa, String busqueda) throws Exception;

    public boolean eliminarInvClienteGrupoEmpresarial(InvClienteGrupoEmpresarialPK pk, SisInfoTO sisInfoTO) throws GeneralException;

}
