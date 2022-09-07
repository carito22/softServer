package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosDatosAdjuntosWebTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosImportarExportarTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunConsumosConsolidandoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface ConsumosService {

    public List<InvFunConsumosTO> getInvFunConsumosTO(String empresa, String desde, String hasta, String bodega)
            throws Exception;

    public Boolean reconstruirCostos(String empCodigo, String Producto, String periodoHasta, Boolean periodoEstado)
            throws Exception;

    public List<InvFunConsumosConsolidandoProductosTO> getInvFunConsumosConsolidandoProductosTO(String empresa,
            String desde, String hasta, String sector, String bodega) throws Exception;

    public InvConsumosTO getInvConsumoCabeceraTO(String empresa, String codigoPeriodo, String motivo,
            String numeroConsumo) throws Exception;

    public List<InvListaConsultaConsumosTO> getFunConsumosListado(String empresa, String fechaDesde, String fechaHasta,
            String status) throws Exception;

    public List<InvListaConsultaConsumosTO> getListaInvConsultaConsumos(String empresa, String periodo, String motivo, String cliente, String proveedor, String producto, String empleado,
            String busqueda, String nRegistros) throws Exception;

    public List<InvConsumosImportarExportarTO> getListaInvConsumosImportarExportarTO(String empresa, String desde,
            String hasta) throws Exception;

    public MensajeTO insertarInvConsumosTO(InvConsumosTO invConsumosTO,
            List<InvConsumosDetalleTO> listaInvConsumosDetalleTO, SisInfoTO sisInfoTO)
            throws NumberFormatException, Exception;

    @Transactional
    public MensajeTO insertarModificarInvConsumos(InvConsumos invConsumos,
            List<InvConsumosDetalle> listaInvConsumosDetalle, SisInfoTO sisInfoTO, boolean ignorarSeries, List<InvConsumosDatosAdjuntosWebTO> listadoImagenes) throws Exception;

    public MensajeTO modificarInvConsumosTO(InvConsumosTO invConsumosTO,
            List<InvConsumosDetalleTO> listaInvConsumosDetalleTO,
            List<InvConsumosDetalleTO> listaInvConsumosEliminarDetalleTO, boolean desmayorizar,
            InvConsumosMotivoAnulacion invConsumosMotivoAnulacion, SisInfoTO sisInfoTO) throws Exception;

    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivo, String numero) throws Exception;

    public void quitarPendiente(InvConsumosPK invConsumosPK, SisInfoTO sisInfoTO);

    public InvConsumos obtenerPorId(String empresa, String periodo, String motivo, String numero);

    public String desmayorizarConsumo(String empresa, String periodo, String motivo, String numero, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarConsumo(String empresa, String periodo, String motivo, String numero, SisInfoTO sisInfoTO) throws Exception;

    public String anularConsumo(String empresa, String periodo, String motivo, String numero, SisInfoTO sisInfoTO) throws Exception;

    public String restaurarConsumo(String empresa, String periodo, String motivo, String numero, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> obtenerDatosParaCrudConsumos(Map<String, Object> map) throws Exception;

    public List<String> desmayorizarConsumos(String empresa, List<InvListaConsultaConsumosTO> lista, SisInfoTO sisInfoTO) throws Exception;

    public List<String> desmayorizarConsumosLote(String empresa, List<InvListaConsultaConsumosTO> lista, SisInfoTO sisInfoTO) throws Exception;

    public List<InvFunConsumosConsolidandoProductosTO> getInvFunConsumosConsolidandoProductosTOWeb(String empresa, String desde, String hasta, String sector, String bodega, String motivo) throws Exception;

    public boolean insertarImagenesConsumo(List<InvConsumosDatosAdjuntosWebTO> listadoImagenes, InvConsumosPK invConsumosPK) throws Exception;

    public List<InvConsumosDatosAdjuntosWebTO> listarImagenesDeConsumo(InvConsumosPK invConsumosPK) throws Exception;

    public boolean actualizarImagenesConsumo(List<InvConsumosDatosAdjuntosWebTO> listadoImagenes, InvConsumosPK invConsumosPK) throws Exception;

    public boolean guardarImagenesConsumos(InvConsumosPK pk, List<InvConsumosDatosAdjuntosWebTO> imagenes, SisInfoTO sisInfoTO) throws Exception;
}
