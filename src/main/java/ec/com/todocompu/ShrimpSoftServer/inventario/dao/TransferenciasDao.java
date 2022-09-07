package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaTransferenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoSaldos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferencias;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface TransferenciasDao extends GenericDao<InvTransferencias, InvTransferenciasPK> {

	public void insertarInvTransferencias(InvTransferencias invTransferencias,
			List<InvTransferenciasDetalle> listaInvTransferenciasDetalle, SisSuceso sisSuceso)
					throws Exception;

	public int buscarConteoUltimaNumeracionTransferencias(String empCodigo, String perCodigo, String motCodigo)
			throws Exception;

	public boolean insertarTransaccionInvTransferencias(InvTransferencias invTransferencias,
			List<InvTransferenciasDetalle> listaInvTransferenciasDetalles, SisSuceso sisSuceso)
					throws Exception;

	public boolean modificarInvTransferencias(InvTransferencias invTransferencias,
			List<InvTransferenciasDetalle> listaInvDetalle, List<InvTransferenciasDetalle> listaInvDetalleEliminar,
			SisSuceso sisSuceso, List<InvProductoSaldos> listaInvProductoSaldosOrigen,
			List<InvProductoSaldos> listaInvProductoSaldosDestino,
			InvTransferenciasMotivoAnulacion invTransferenciasMotivoAnulacion, boolean desmayorizar) throws Exception;

	public InvTransferencias buscarInvTransferencias(String empresa, String perCodigo, String transCodigo,
			String transNumero) throws Exception;

	public InvTransferenciasTO getInvTransferenciasCabeceraTO(String empresa, String periodo, String motivo,
			String numeroConsumo) throws Exception;

	public List<InvListaConsultaTransferenciaTO> getFunListadoTransferencias(String empresa, String fechaDesde,
			String fechaHasta, String status) throws Exception;

	public List<InvListaConsultaTransferenciaTO> getListaInvConsultaTransferencia(String empresa, String periodo,
			String motivo, String busqueda, String nRegistros) throws Exception;

	public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivoTipo, String numero)
			throws Exception;

	public void actualizarPendienteSql(InvTransferenciasPK invTransferenciasPK, boolean pendiente) throws Exception;

    public void anularRestaurarSql(InvTransferenciasPK invTransferenciasPK, boolean anulado);
}
