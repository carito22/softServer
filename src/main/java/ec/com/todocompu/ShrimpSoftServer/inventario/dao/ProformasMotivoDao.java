package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaMotivoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ProformasMotivoDao extends GenericDao<InvProformasMotivo, InvProformasMotivoPK> {

    public boolean insertarInvProformaMotivo(InvProformasMotivo invProformasMotivo, SisSuceso sisSuceso) throws Exception;

    public boolean modificarInvProformaMotivo(InvProformasMotivo invProformaMotivo, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarInvProformaMotivo(InvProformasMotivo invProformaMotivo, SisSuceso sisSuceso) throws Exception;

    public InvProformasMotivo getInvProformasMotivo(String empresa, String codigo) throws Exception;

    public InvProformaMotivoTO getInvProformasMotivoTO(String empresa, String pmCodigo) throws Exception;

    public Boolean comprobarInvProformaMotivo(String empresa, String motCodigo) throws Exception;

    public Boolean comprobarInvProformasMotivo(String empresa, String motCodigo) throws Exception;

    public List<InvProformaMotivoTablaTO> getListaInvProformaMotivoTablaTO(String empresa) throws Exception;

    public List<InvProformaMotivoComboTO> getListaProformaMotivoComboTO(String empresa, boolean filtrarInactivos) throws Exception;

    public boolean retornoContadoEliminarProformasMotivo(String empresa, String motivo) throws Exception;

    /*lista motivos*/
    public List<InvProformaMotivoTO> getListaInvProformaMotivoTO(String empresa, boolean filtrarInactivos, String busqueda) throws Exception;

}
