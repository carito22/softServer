package ec.com.todocompu.ShrimpSoftServer.contabilidad.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.RetornoTO;
import ec.com.todocompu.ShrimpSoftUtils.cartera.TO.CarCobrosDetalleVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceComprobacionTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceGeneralComparativoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadoComparativoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadosMensualizadosTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadosVsInventarioDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDiarioAuxiliarTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceGeneralNecTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceResultadosNecTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContabilizarComprasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContabilizarVentasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContablesVerificacionesComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContablesVerificacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunIPPTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConListaBalanceResultadosVsInventarioTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConListaContableDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConMayorAuxiliarTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConMayorGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ItemListaContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ItemStatusItemListaConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ListaConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.PersonaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentas;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdContabilizarCorridaCostoVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdContabilizarCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.Map;

public interface ContableService {

    @Transactional
    public boolean insertarTransaccionContableCompra(ConContable conContable, List<ConDetalle> listaConDetalle, SisSuceso sisSuceso, ConNumeracion conNumeracion, InvCompras invCompras, SisInfoTO sisInfoTO)
            throws Exception;

    @Transactional
    public boolean insertarTransaccionContableVenta(ConContable conContable, List<ConDetalle> listaConDetalle, SisSuceso sisSuceso, ConNumeracion conNumeracion, List<InvVentas> invVentas, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public ConContable obtenerPorId(String empresa, String periodo, String tipoCodigo, String conNumero);

    @Transactional
    public List<ConFunContabilizarComprasDetalleTO> getConFunContabilizarComprasDetalle(String empresa, String periodo, String motivo, String compraNumero, String validar) throws Exception;

    @Transactional
    public List<ConFunContabilizarVentasDetalleTO> getConFunContabilizarVentasDetalle(String empresa, String vtaPeriodo, String vtaMotivo, String vtaNumero, String validar) throws Exception;

    @Transactional
    public void actualizar(ConContable conContable);

    @Transactional
    public List<ConContableTO> getListaConContableTO(String empresa, String perCodigo, String tipCodigo, String numContable) throws Exception;

    @Transactional
    public List<ConFunBalanceResultadosNecTO> getConEstadoResultadosIntegralTO(String empresa, String sector, String fechaDesde, String fechaHasta, Boolean ocultarDetalle, Boolean excluirAsientoCierre) throws Exception;

    @Transactional
    public List<ConBalanceResultadoComparativoTO> getConBalanceResultadoComparativoTO(String empresa, String sector, String fechaDesde, String fechaHasta, String fechaDesde2, String fechaHasta2, Boolean ocultarDetalle, Boolean excluirCierre) throws Exception;

    @Transactional
    public List<ConBalanceResultadoTO> getConFunBalanceFlujoEfectivo(String empresa, String sector, String fechaDesde, String fechaHasta) throws Exception;

    @Transactional
    public List<ConFunBalanceGeneralNecTO> getConEstadoSituacionFinancieraTO(String empresa, String sector, String fecha, Boolean ocultar, Boolean ocultarDetalle) throws Exception;

    @Transactional
    public List<ConFunBalanceGeneralNecTO> getFunCuentasSobregiradasTO(String empresa, String secCodigo, String fecha) throws Exception;

    @Transactional
    public List<ConBalanceGeneralComparativoTO> getFunBalanceGeneralComparativoTO(String empresa, String secCodigo, String fechaAnterior, String fechaActual, Boolean ocultar) throws Exception;

    @Transactional
    public List<ConBalanceComprobacionTO> getListaBalanceComprobacionTO(String empresa, String codigoSector,
            String fechaInicio, String fechaFin, Boolean ocultarDetalle) throws Exception;

//    @Transactional
    public List<ConMayorAuxiliarTO> getListaMayorAuxiliarTO(String empresa, String codigoCuentaDesde, String codigoCuentaHasta, String fechaInicio, String fechaFin, String sector, boolean estado) throws Exception;

    @Transactional
    public List<ConMayorGeneralTO> getListaMayorGeneralTO(String empresa, String codigoSector, String codigoCuenta, String fechaFin) throws Exception;

    @Transactional
    public List<ConDiarioAuxiliarTO> getListaDiarioAuxiliarTO(String empresa, String codigoTipo, String fechaInicio, String fechaFin) throws Exception;

    @Transactional
    public MensajeTO insertarConContable(ConContableTO conContableTO, List<ConDetalleTO> listaConDetalleTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String desbloquearConContable(String empresa, String periodo, String tipo, String numero, String usuario, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String anularConContable(String empresa, String periodo, String tipo, String numero, String usuario, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public List<ConFunContablesVerificacionesTO> getFunContablesVerificacionesTO(String empresa) throws Exception;

    @Transactional
    public String eliminarConContable(ConContablePK conContablePK, SisInfoTO sisInfoTO) throws Exception;

    public List<ConFunIPPTO> getFunListaIPP(String empresa, String fechaInicio, String fechaFin, String parametro) throws Exception;

    @Transactional
    public MensajeTO getListaInvConsultaConsumosPendientes(String empCodigo, String fechaDesde, String fechaHasta) throws Exception;

    @Transactional
    public MensajeTO insertarModificarIPP(String empresa, String fechaDesde, String fechaHasta, String tipo, Date fechaHoraBusqueda, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO insertarModificarContablesIPPTodo(String empresa, String fechaDesde, String fechaHasta, Date fechaHoraBusqueda, List<PrdContabilizarCorridaTO> listaContabilizarCorrida, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO insertarModificarContabilizarCorrida(String empresa, String desde, String hasta, List<PrdContabilizarCorridaTO> listaContabilizarCorrida, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO insertarModificarContabilizarCorridaCostoVenta(String empresa, String desde, String hasta, List<PrdContabilizarCorridaCostoVentaTO> listaContabilizarCorrida, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO insertarConContableCierreCuentas(ConContableTO conContableTO, String centroProduccion, String conNumero_Contable, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public List<ConFunContablesVerificacionesComprasTO> getConFunContablesVerificacionesComprasTOs(String empresa, String fechaInicio, String fechaFin) throws Exception;

    @Transactional
    public List<PersonaTO> getFunPersonaTOs(String empresa, String busquedad) throws Exception;

    @Transactional
    public List<ConListaBalanceResultadosVsInventarioTO> getConListaBalanceResultadosVsInventarioTO(String empresa, String desde, String hasta, String sector) throws Exception;

    @Transactional
    public List<ConBalanceResultadosMensualizadosTO> getBalanceResultadoMensualizado(String empresa, String codigoSector, String fechaInicio, String fechaFin, String usuario, SisInfoTO sisInfoTO) throws Exception;

    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivo, String numero) throws Exception;

    @Transactional
    public List<ListaConContableTO> getListConContableTO(String empresa, String periodo, String tipo, String busqueda, String referencia, String nRegistros) throws Exception;

    @Transactional
    public List<ItemStatusItemListaConContableTO> getListConContableTOConStatus(String empresa, String periodo, String tipo, String busqueda, String referencia, String nRegistros) throws Exception;

    @Transactional
    public MensajeTO insertarModificarContable(ConContable conContable, List<ConDetalle> listaConDetalle, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public ConContable getConContable(ConContablePK conContablePK);

    @Transactional
    public void mayorizarSql(List<ConContablePK> listaContable);

    @Transactional
    public String mayorizarDesmayorizarSql(ConContablePK conContablePK, boolean pendiente, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String anularReversarSql(ConContablePK conContablePK, boolean anularReversar, SisInfoTO sisInfoTO);

    @Transactional
    public String restaurarSql(ConContablePK conContablePK) throws Exception;

    @Transactional
    public List<String> validarChequesConciliados(String empresa, String periodo, String conTipo, String conNumero) throws Exception;

    @Transactional
    public MensajeTO validarContables(String empresa, String periodo, String conTipo, String conNumero, String accionUsuario, String bandera) throws Exception;

    @Transactional
    public ItemListaContableTO obtenerContableConDetalle(ConContablePK conContablePK, boolean mostrarSoloActivos) throws Exception;

    public List<ConContablePK> obtenerListadoContablePK(List<ItemStatusItemListaConContableTO> listaConContableTOSeleccion);

    public ConContable obtenerConContableConDetalle(ConContable conContable, List<ConListaContableDetalleTO> detalle) throws Exception;

    public boolean validarCuentaBancos(ConListaContableDetalleTO detalleContableEvaluar, String empresa) throws Exception;

    public void crearSuceso(ConContablePK conContablePK, String tipo, SisInfoTO sisInfoTO);

    public List<ConFunBalanceGeneralNecTO> convertirConBalanceGeneralTO_ConFunBalanceGeneralNecTO(List<ConBalanceGeneralTO> listaConBalanceGeneralTO);

    public List<ConFunBalanceResultadosNecTO> convertirConBalanceResultadoTO_ConFunBalanceGeneralNecTO(List<ConBalanceResultadoTO> listado);

    public Map<String, Object> obtenerListasParaContabilizarTodoProceso(Map<String, Object> map) throws Exception;

    public boolean verificarDocumentoBanco(String documento, String cuenta, String empresa) throws Exception;

    public Map<String, Object> obtenerDatosParaMayorizarContableRRHH(Map<String, Object> map) throws Exception;

    public Map<String, Object> obtenerDatosParaCrudContable(String empresa, boolean mostrarTodos) throws Exception;

    public List<ConFunContablesVerificacionesTO> getFunContablesVerificaciones(String empresa, String fechaInicio, String fechaFin) throws Exception;

    public Map<String, Object> obtenerListasVerificacionConErrores(Map<String, Object> map) throws Exception;

    public Map<String, Object> obtenerDatosMayorAuxiliar(Map<String, Object> map) throws Exception;

    @Transactional
    public Map<String, Object> obtenerDatosContable(Map<String, Object> map) throws Exception;

    @Transactional
    public ItemListaContableTO consultaContableVirtual(ConContablePK conContablePK, String tipoContableVirtual) throws Exception;

    public Map<String, Object> obtenerDatosDiarioAuxiliar(Map<String, Object> map) throws Exception;

    public Map<String, Object> obtenerDatosParaContabilizarDatos(Map<String, Object> map) throws Exception;

    public Map<String, Object> obtenerDatosEstadoResultadoIntegralCompar(Map<String, Object> map) throws Exception;

    public Map<String, Object> obtenerDatosConsultaContabilidad(Map<String, Object> map) throws Exception;

    public Map<String, Object> obtenerDatosConsultaComprobanteContable(Map<String, Object> map) throws Exception;

    public List<ConBalanceResultadosVsInventarioDetalladoTO> getConListaBalanceResultadosVsInventarioDetalladoTO(String empresa, String desde, String hasta, String sector, boolean estado) throws Exception;

    /*IMAGENES*/
    public List<ConAdjuntosContableWebTO> convertirStringUTF8(ConContablePK conContablePK);

    public boolean guardarImagenesContable(List<ConAdjuntosContableWebTO> listadoInsertar, ConContablePK conContablePK, SisInfoTO sisInfoTO) throws Exception;

    public boolean actualizarImagenesContables(List<ConAdjuntosContableWebTO> listadoInsertar, ConContablePK conContablePK) throws Exception;

    public RetornoTO getBalanceResultadoMensualizadoAntiguo(String empresa, String codigoSector, String fechaInicio, String fechaFin, String usuario, SisInfoTO sisInfoTO) throws Exception;

    public List<String> enviarPorCorreoErroresDeContabilidad(Map<String, Object> map, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, SisInfoTO sisInfoTO) throws Exception;

    public RetornoTO getConBalanceBalanceCentroProduccionTO(String empresa, String fechaDesde, String fechaHasta, Boolean ocultarDetalle, Boolean excluirAsientoCierre) throws Exception;
    
    public boolean activarWispro(SisInfoTO sisSuceso, List<CarCobrosDetalleVentasTO> carCobrosDetalleVentas) throws Exception;

}
