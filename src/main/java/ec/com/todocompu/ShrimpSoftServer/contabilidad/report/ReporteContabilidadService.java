package ec.com.todocompu.ShrimpSoftServer.contabilidad.report;

import java.util.List;

import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceComprobacionTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceGeneralComparativoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadoComparativoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadosMensualizadosTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConBalanceResultadosVsInventarioDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConCuentasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDiarioAuxiliarTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceCentroProduccionTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceGeneralNecTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunBalanceResultadosNecTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContablesVerificacionesComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContablesVerificacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConListaBalanceResultadosVsInventarioTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConMayorAuxiliarTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConMayorGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConTipoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.InventarioProductosCuentasInconsistentes;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.InventarioProductosEnProcesoErroresCompra;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.InventarioProductosEnProcesoErroresConsumo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.report.ReporteContableDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.ReporteCompraDetalle;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.io.File;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public interface ReporteContabilidadService {

    Map<String, Object> generarReporteConCuenta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ConCuentasTO> listConCuentasTO) throws Exception;

    Map<String, Object> generarReporteConTipoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ConTipoTO> listConTipoTO) throws Exception;

    Map<String, Object> generarReporteMayorAuxiliar(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ConMayorAuxiliarTO> listConMayorAuxiliarTO, String codigoCuenta, String codigoCuentaHasta, String fechaDesde, String fechaHasta) throws Exception;

    Map<String, Object> generarReporteMayorGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ConMayorGeneralTO> listConMayorGeneralTO, String codigoCuenta, String fechaHasta) throws Exception;

    Map<String, Object> generarReporteContablesVerificacionesCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ConFunContablesVerificacionesComprasTO> listConFunContablesVerificacionesComprasTO, String fechaDesde, String fechaHasta) throws Exception;

    Map<String, Object> generarReporteDiarioAuxiliar(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ConDiarioAuxiliarTO> listConDiarioAuxiliarTO, String fechaDesde, String fechaHasta) throws Exception;

    Map<String, Object> generarReporteContablesVerificacionesErroresExcel(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ConFunContablesVerificacionesTO> listConFunContablesVerificacionesTO) throws Exception;

    Map<String, Object> generarReporteConCuentasSobregiradasTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ConBalanceGeneralTO> listConBalanceGeneralTO, String fecha, String codigoSector) throws Exception;

    public Map<String, Object> generarReporteBalanceResultadoMensualizado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConBalanceResultadosMensualizadosTO> lista, String fechaInicio, String fechaFin, String codigoSector) throws Exception;

    Map<String, Object> generarReporteEstadosComprobacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConBalanceComprobacionTO> listado, String fechaDesde,
            String fechaHasta) throws Exception;

    Map<String, Object> generarReporteEstadoResultadoIntegralComparativo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConBalanceResultadoComparativoTO> listaConBalanceResultadoComparativoTO, String fechaDesde,
            String fechaHasta, String sector) throws Exception;

    Map<String, Object> generarReporteEstadoResultadosVsInventario(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConListaBalanceResultadosVsInventarioTO> listaBalanceResultadosVsInventarioTO, String fechaDesde,
            String fechaHasta) throws Exception;

    Map<String, Object> generarReporteEstadoResultadoIntegral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConBalanceResultadoTO> listaConBalanceResultadoTO, String fechaDesde,
            String fechaHasta, String sector) throws Exception;

    Map<String, Object> generarReporteEstadoSituacionFinanciera(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConBalanceGeneralTO> listaBalanceGeneral,
            String fechaHasta, String sector) throws Exception;

    Map<String, Object> generarReporteEstadoSituacionFinancieraComparativo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConBalanceGeneralComparativoTO> listaBalanceGeneralComparativo, String fechaDesde,
            String fechaHasta, String sector) throws Exception;

    byte[] generarReporteTipoContable(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ConTipoTO> listado) throws Exception;

    //cambios
    byte[] generarReporteContableDetalle(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ConContableReporteTO> listConContableReporteTO) throws Exception;

    byte[] generarReporteMayorAuxiliar(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, String cuentaContableDesde, String cuentaContablePadreDesde, String cuentaContableHasta,
            String cuentaContablePadreHasta, List<ConMayorAuxiliarTO> listConMayorAuxiliarTO) throws Exception;

    byte[] generarReporteMayorGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaHasta,
            String cuentaContable, Object[][] datos) throws Exception;

    byte[] generarReporteDiarioAuxiliar(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, String codigoTipo, List<ConDiarioAuxiliarTO> listConDiarioAuxiliarTO) throws Exception;

    byte[] generarReporteBalanceComprobacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, List<ConBalanceComprobacionTO> listConBalanceComprobacionTO) throws Exception;

    byte[] generarReporteBalanceGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaHasta,
            String codigoSector, List<ConFunBalanceGeneralNecTO> listConFunBalanceGeneralNecTO,
            List<ConBalanceGeneralTO> listConBalanceGeneralTO) throws Exception;

    byte[] generarReporteBalanceGeneralComparativo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaAnterior, String fechaActual, String codigoSector,
            List<ConBalanceGeneralComparativoTO> listConBalanceGeneralComparativoTO) throws Exception;

    byte[] generarReporteBalanceResultado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, String codigoSector, Integer columnasEstadosFinancieros,
            List<ConFunBalanceResultadosNecTO> listConFunBalanceResultadosNecTO) throws Exception;

    byte[] generarReporteBalanceResultadoComparativo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, String fechaDesde2, String fechaHasta2, String codigoSector,
            List<ConBalanceResultadoComparativoTO> conBalanceResultadoComparativoTO) throws Exception;

    byte[] generarReporteContablesVerificacionesCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaDesde, String fechaHasta, List<ConFunContablesVerificacionesComprasTO> listConFunContablesVerificacionesComprasTO) throws Exception;

    public File generarReporteContablesVerificacionesComprasFile(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaDesde, String fechaHasta,
            List<ConFunContablesVerificacionesComprasTO> listConFunContablesVerificacionesComprasTO) throws Exception;

    byte[] generarReporteConListaBalanceResultadosVsInventario(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            String fechaDesde, String fechaHasta,
            List<ConListaBalanceResultadosVsInventarioTO> listConListaBalanceResultadosVsInventarioTO) throws Exception;

    byte[] generarReporteContablesVerificacionesErrores(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ConFunContablesVerificacionesTO> listConFunContablesVerificacionesTO) throws Exception;

    public File generarReporteContablesVerificacionesErroresFile(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ConFunContablesVerificacionesTO> listConFunContablesVerificacionesTO) throws Exception;

    byte[] generarReporteMayorGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaHasta,
            String cuentaContable, List<ConMayorGeneralTO> listConMayorGeneralTO) throws Exception;

    byte[] generarReporteCompraContableDetalle(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO,
            List<ReporteCompraDetalle> reporteCompraDetalles, List<ConContableReporteTO> list) throws Exception;

    public List<ConContableReporteTO> generarListaConContableReporteTO(List<ConContablePK> listContable, SisInfoTO usuario) throws Exception;

    public List<ReporteContableDetalle> generarReporteContableDetalleParaCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, ConContablePK pk) throws Exception;
    
    public String obtenerNombreReporteContable(List<ReporteContableDetalle> listReporteContableDetalle) throws Exception;
    
    public void generarTXT(List<ConCuentasTO> listadoConCuentasTO, HttpServletResponse response) throws Exception;

    public byte[] generarReporteBalanceGeneralNecTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaHasta,
            String codigoSector, List<ConFunBalanceGeneralNecTO> listConBalanceGeneralNecTO) throws Exception;

    public Map<String, Object> generarReporteEstadoSituacionFinancieraTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConFunBalanceGeneralNecTO> listado, String fechaHasta, String sector) throws Exception;

    public byte[] generarReporteBalanceResultadoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String codigoSector,
            Integer columnasEstadosFinancieros, List<ConFunBalanceResultadosNecTO> listConFunBalanceResultadosNecTO) throws Exception;

    public Map<String, Object> exportarReporteEstadoResultadoIntegralTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConFunBalanceResultadosNecTO> listConListaConBalanceResultadoTO, String fechaDesde, String fechaHasta, String sector) throws Exception;

    public Map<String, Object> exportarReporteConCuentasSobregiradasTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConFunBalanceGeneralNecTO> listado,
            String fecha, String codigoSector) throws Exception;

    public byte[] generarReporteConsumosVerificacionesErrores(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InventarioProductosEnProcesoErroresConsumo> listConFunConsumoVerificacionesTO) throws Exception;

    public File generarReporteConsumosVerificacionesErroresFile(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InventarioProductosEnProcesoErroresConsumo> listConFunConsumoVerificacionesTO) throws Exception;

    public File generarReporteComprasVerificacionesErroresFile(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InventarioProductosEnProcesoErroresCompra> listConFunConsumoVerificacionesTO) throws Exception;

    public File generarReporteConBalanceResultadosVsInventarioDetalladoTOFile(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<ConBalanceResultadosVsInventarioDetalladoTO> listConListaBalanceResultadosVsInventarioTO) throws Exception;

    public Map<String, Object> exportarReporteConsumoErrores(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InventarioProductosEnProcesoErroresConsumo> listado) throws Exception;

    public Map<String, Object> exportarPlantillaConCuentasTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConCuentasTO> listado) throws Exception;

    public byte[] generarReporteConBalanceResultadosVsInventarioDetalladoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<ConBalanceResultadosVsInventarioDetalladoTO> listConListaBalanceResultadosVsInventarioTO) throws Exception;

    public Map<String, Object> generarReporteConBalanceResultadosVsInventarioDetalladoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConBalanceResultadosVsInventarioDetalladoTO> listConListaBalanceResultadosVsInventarioTO, String fechaDesde, String fechaHasta) throws Exception;

    public Map<String, Object> generarReporteBalanceResultadoMensualizadoAntiguo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Object[][] datos, List<String> columnas, String codigoSector) throws Exception;

    public byte[] generarReporteProductosInconsistencias(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InventarioProductosCuentasInconsistentes> listado) throws Exception;

    public Map<String, Object> exportarReporteProductosInconsistencias(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InventarioProductosCuentasInconsistentes> listado) throws Exception;

    public byte[] imprimirReporteProyectoFlujoCaja(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> resultado) throws Exception;

    public Map<String, Object> exportarReporteFlujoDeCaja(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> resultado, String hasta) throws Exception;

    public Map<String, Object> exportarPlantillaContables(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception;

    public Map<String, Object> exportarReporteEstadoResultadoCentroProduccion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ConFunBalanceCentroProduccionTO> listEstadoResultadoCentroProduccion, String fechaDesde, String fechaHasta) throws Exception;

    public byte[] generarReporteBalanceResultadoCentroProduccion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<ConFunBalanceCentroProduccionTO> listEstadoResultadoCentroProduccion) throws Exception;
}
