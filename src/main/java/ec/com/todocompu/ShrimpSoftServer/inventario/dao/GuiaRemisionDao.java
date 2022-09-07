/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaGuiaRemisionPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsultaGuiaRemisionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvGuiaRemisionDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvGuiaRemisionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemision;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

/**
 *
 * @author CarolValdiviezo
 */
public interface GuiaRemisionDao extends GenericDao<InvGuiaRemision, InvGuiaRemisionPK> {

    public int buscarConteoUltimaNumeracionGuiaRemision(String empCodigo, String periodo);

    public String getConteoNumeroGuiaRemision(String empresaCodigo, String transCodigo, String compDocumentoNumero, InvGuiaRemisionPK pk) throws Exception;

    public InvGuiaRemision buscarInvGuiaRemision(String empresa, String periodo, String compNumero) throws Exception;

    public List<InvConsultaGuiaRemisionTO> getListaInvGuiaRemisionTO(String empresa, String tipoDocumento, String fechaInicio, String fechaFinal, String nRegistros);

    public boolean insertarTransaccionInvGuiaRemision(InvGuiaRemision invGuiaRemision, List<InvGuiaRemisionDetalle> listaInvGuiaRemisionDetalles, SisSuceso sisSuceso) throws Exception;

    public boolean modificarTransaccionInvGuiaRemision(InvGuiaRemision invGuiaRemision, List<InvGuiaRemisionDetalle> listaInvGuiaRemisionDetalles, List<InvGuiaRemisionDetalle> listaInvGuiaRemisionDetallesEliminar, SisSuceso sisSuceso) throws Exception;

    public void insertarInvGuiaRemision(InvGuiaRemision invGuiaRemision, List<InvGuiaRemisionDetalle> listaInvGuiaRemisionDetalles, SisSuceso sisSuceso) throws Exception;

    public void modificarInvGuiaRemision(InvGuiaRemision invGuiaRemision, List<InvGuiaRemisionDetalle> listaInvGuiaRemisionDetalles, List<InvGuiaRemisionDetalle> listaInvGuiaRemisionDetallesEliminar, SisSuceso sisSuceso) throws Exception;

    public List<InvGuiaRemisionDetalle> obtenerGuiaRemisionDetallePorNumero(String empresa, String periodo, String numero) throws Exception;

    public List<InvGuiaRemisionDetalleTO> obtenerGuiaRemisionDetalleTO(String empresa, String periodo, String numero) throws Exception;

    public void anularRestaurarSql(InvGuiaRemisionPK pk, boolean anulado) throws Exception;

    public void desmayorizarRestaurarSql(InvGuiaRemisionPK pk, boolean pendiente) throws Exception;
    //pendientes

    public List<AnxListaGuiaRemisionPendientesTO> getListaGuiaRemisionPendientes(String empresa) throws Exception;

    public String eliminarGuiaRemision(String empresa, String periodo, String numero, SisSuceso sisSuceso) throws Exception;

    public InvGuiaRemisionTO buscarInvGuiaRemision(String empresa, String numeroDocumento) throws Exception;

    public Boolean actualizarClaveExterna(InvGuiaRemisionPK pk, String clave, SisSuceso sisSuceso) throws Exception;
}
