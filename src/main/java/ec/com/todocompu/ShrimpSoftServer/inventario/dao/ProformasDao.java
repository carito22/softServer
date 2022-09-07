package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaProformaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ProformasDao extends GenericDao<InvProformas, InvProformasPK> {

    public void insertarInvProforma(InvProformas invProformas, List<InvProformasDetalle> listaInvProformasDetalles,
            SisSuceso sisSuceso, InvProformasNumeracion invProformasNumeracion, boolean nuevo) throws Exception;

    public int buscarConteoUltimaNumeracionProforma(String empCodigo, String perCodigo, String motCodigo)
            throws Exception;

    public boolean insertarTransaccionInvProformas(InvProformas invProformas,
            List<InvProformasDetalle> listaInvProformasDetalles, SisSuceso sisSuceso,
            InvProformasNumeracion invProformasNumeracion) throws Exception;

    public boolean modificarInvProformas(InvProformas invProformas, List<InvProformasDetalle> listaInvDetalle,
            List<InvProformasDetalle> listaInvDetalleEliminar, SisSuceso sisSuceso) throws Exception;

    public InvProformas buscarInvProformas(String empresa, String perCodigo, String motCodigo, String compNumero)
            throws Exception;

    public InvProformaCabeceraTO getInvProformaCabeceraTO(String empresa, String periodo, String motivo,
            String numeroProforma) throws Exception;

    public List<InvListaConsultaProformaTO> getListaInvConsultaProforma(String empresa, String periodo, String motivo,
            String busqueda) throws Exception;

    public List<InvListaConsultaProformaTO> getListaInvConsultaProforma(String empresa, String periodo, String motivo,
            String busqueda, String nRegistros) throws Exception;

    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivoTipo, String numero)
            throws Exception;

    public void actualizarPendienteSql(InvProformasPK invProformasPK, boolean pendiente) throws Exception;

    public void anularRestaurarSql(InvProformasPK invProformasPK, boolean anulado) throws Exception;

    public List<InvProformaClienteTO> listarInvProformaClienteTO(String empresa, String cliCodigo) throws Exception;
}
