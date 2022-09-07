package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosImportarExportarTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunConsumosConsolidandoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvValidarStockTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoSaldos;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ConsumosDao extends GenericDao<InvConsumos, InvConsumosPK> {

    public InvConsumos buscarInvConsumos(String empresa, String perCodigo, String motCodigo, String compNumero)
            throws Exception;

    public InvConsumosTO getInvConsumoCabeceraTO(String empresa, String periodo, String motivo, String numeroConsumo)
            throws Exception;

    public boolean insertarModificarInvConsumos(InvConsumos invConsumos, List<InvConsumosDetalle> listaInvConsumosDetalles,
            SisSuceso sisSuceso, boolean ignorarSeries) throws Exception;

    public boolean modificarInvConsumos(InvConsumos invConsumos, List<InvConsumosDetalle> listaInvDetalle,
            List<InvConsumosDetalle> listaInvDetalleEliminar, SisSuceso sisSuceso,
            List<InvProductoSaldos> listaInvProductoSaldos, InvConsumosMotivoAnulacion invConsumosMotivoAnulacion,
            boolean desmayorizar) throws Exception;

    public int buscarConteoUltimaNumeracionConsumo(String empCodigo, String perCodigo, String motCodigo)
            throws Exception;

    public List<InvConsumosImportarExportarTO> getListaInvConsumosImportarExportarTO(String empresa, String desde,
            String hasta) throws Exception;

    public List<InvListaConsultaConsumosTO> getFunConsumosListado(String empresa, String fechaDesde, String fechaHasta,
            String status) throws Exception;

    public List<InvFunConsumosConsolidandoProductosTO> getInvFunConsumosConsolidandoProductosTO(String empresa,
            String desde, String hasta, String sector, String bodega) throws Exception;

    public List<InvListaConsultaConsumosTO> getListaInvConsultaConsumos(String empresa, String periodo, String motivo, String cliente, String proveedor, String producto, String empleado,
            String busqueda, String nRegistros) throws Exception;

    public List<InvFunConsumosTO> getInvFunConsumosTO(String empresa, String desde, String hasta, String motivo)
            throws Exception;

    public Boolean reconstruirCostos(String empCodigo, String Producto, String periodoHasta, Boolean periodoEstado)
            throws Exception;

    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivoTipo, String numero)
            throws Exception;

    public void actualizarPendienteSql(InvConsumosPK invConsumosPK, boolean pendiente, SisSuceso sisSuceso);

    public void anularRestaurarSql(InvConsumosPK invConsumosPK, boolean anulado, SisSuceso sisSuceso);

    public String eliminarConsumo(InvConsumosPK invConsumosPK, SisSuceso sisSuceso);

    public List<InvValidarStockTO> getListaInvConsumosValidarStockTO(String empresa, String periodo, String motivo,
            String numero, String accion);

    public boolean comprobarInvConsumosCodigoAleatorio(String codAleatorio) throws Exception;

    public List<InvFunConsumosConsolidandoProductosTO> obtenerInvFunConsumosConsolidandoProductosTO(String empresa, String desde, String hasta, String sector, String bodega, String motivo) throws Exception;

    public List<InvConsumosDatosAdjuntos> listarImagenesDeConsumo(InvConsumosPK invConsumosPK) throws Exception;

}
