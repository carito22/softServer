package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaMotivoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface ProformasMotivoService {

    public List<InvProformaMotivoComboTO> getListaProformaMotivoComboTO(String empresa, boolean filtrarInactivos) throws Exception;

    public InvProformaMotivoTO getInvProformasMotivoTO(String empresa, String pmCodigo) throws Exception;

    public List<InvProformaMotivoTablaTO> getListaInvProformaMotivoTablaTO(String empresa) throws Exception;

    public String insertarInvProformaMotivoTO(InvProformaMotivoTO invProformaMotivoTO, SisInfoTO sisInfoTO) throws Exception;

    public String modificarInvProformaMotivoTO(InvProformaMotivoTO invProformaMotivoTO, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarInvProformaMotivoTO(InvProformaMotivoTO invProformaMotivoTO, SisInfoTO sisInfoTO) throws Exception;

    /*lista motivos*/
    public List<InvProformaMotivoTO> getListaInvProformaMotivoTO(String empresa, boolean filtrarInactivos, String busqueda) throws Exception;

    public boolean modificarEstadoInvProformaMotivo(InvProformasMotivoPK invProformasMotivoPK, boolean estado, SisInfoTO sisInfoTO) throws GeneralException;

}
