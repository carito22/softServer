package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.RetornoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.MultiplePiscinaCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdComboCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdConsumosDetalladoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdContabilizarCorridaCostoVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdContabilizarCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCorridaListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCostoDetalleFechaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunAnalisisVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunCostosPorFechaSimpleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaConsultaGrameajePromedioPorPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaCostosDetalleCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaFunAnalisisPesosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenFinancieroTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenPescaSiembraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdTrazabilidadCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdUtilidadDiariaCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorridaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.math.BigDecimal;
import java.util.Map;

public interface CorridaService {

    public List<PrdListaCorridaTO> obtenerCorrida(String empresa, String sector, String piscina, Date fecha)
            throws Exception;

    @Transactional
    public List<PrdListaCorridaTO> getListaCorridaTO(String empresa, String sector, String piscina, String fechaDesde, String fechaHasta, String tipo) throws Exception;

    @Transactional
    public List<PrdCorridaListadoTO> listarCorridasPorFecha(String empresa, String fechaDesde, String fechaHasta, String tipo) throws Exception;

    @Transactional
    public List<PrdListaCorridaTO> getListaCorridaTO(String empresa, String sector, String piscina, String corrida)
            throws Exception;

    @Transactional
    public PrdFunAnalisisVentasTO getPrdFunAnalisisVentasTO(String empresa, String sector, String piscina,
            String fechaDesde, String fechaHasta) throws Exception;

    @Transactional
    public List<PrdListaCostosDetalleCorridaTO> getPrdListaCostosDetalleCorridaTO(String empresa, String sector,
            String piscina, String corrida, String desde, String hasta, String agrupadoPor) throws Exception;

    @Transactional
    public List<PrdComboCorridaTO> getComboPrdCorridaTO(String empresa, String sector, String piscina) throws Exception;

    @Transactional
    public List<PrdComboCorridaTO> getComboPrdCorridaFiltradoTO(String empresa, String sector, String piscina, Date fecha) throws Exception;

    @Transactional
    public List<PrdResumenCorridaTO> getListaResumenCorridaTO(String empresa, String sector, String desde, String hasta,
            String tipoResumen) throws Exception;

    @Transactional
    public List<PrdContabilizarCorridaTO> getListaContabilizarCorridaTO(String empresa, String desde, String hasta)
            throws Exception;

    @Transactional
    public String consultarFechaMaxMin(String empresa, String tipoResumen) throws Exception;

    @Transactional
    public List<PrdCostoDetalleFechaTO> getPrdListadoCostoDetalleFechaTO(String empresa, String secCodigo, String desde,
            String hasta, String agrupadoPor) throws Exception;

    public RetornoTO modificarCorridaActivo(String empresa, String sector, String hasta, String proceso,
            String agrupadoPor) throws Exception;

    public RetornoTO modificarCorridaActivoSeleccionMultiple(String empresa,
            List<MultiplePiscinaCorrida> multiplePiscinaCorrida, String proceso, String agrupadoPor) throws Exception;

    @Transactional
    public PrdCorridaTO getPrdCorridaTO(String empresa, String sector, String piscina, String fecha) throws Exception;

    @Transactional
    public List<MultiplePiscinaCorrida> getCostoDetallePersonalizado(String empresa, String sector, String fecha);

    public RetornoTO getCostoPorFechaProrrateado(String empresa, String codigoSector, String fechaInicio,
            String fechaFin, String usuario, String agrupadoPor) throws Exception;

    @Transactional
    public List<PrdFunCostosPorFechaSimpleTO> getPrdFunCostosPorFechaSimpleTO(String codigoSector, String fechaInicio,
            String fechaFin, String infEmpresea) throws Exception;

    public RetornoTO getCostoPorPiscinaSemanal(String empresa, String codigoSector, String numeroPiscina,
            String fechaInicio, String fechaFin, String usuario, String agrupadoPor, String periodo) throws Exception;

    public RetornoTO getCostosMensuales(String empresa, String codigoSector, String codigoBodega, String fechaInicio, String fechaFin, String usuario, String agrupadoPor) throws Exception;

    public RetornoTO getConsumoPorFechaDesglosado(String empresa, String codigoSector, String fechaInicio,
            String fechaFin, String usuario) throws Exception;

    @Transactional
    public RetornoTO getConsumoProductosProceso(String empresa, String fecha, String usuario, SisInfoTO sisInfoTO)
            throws Exception;

    @Transactional
    public List<PrdConsumosTO> getPrdConsumosFechaTO(String empresa, String sector, String fechaDesde,
            String fechaHasta) throws Exception;

    @Transactional
    public List<PrdConsumosTO> getPrdConsumosPiscinaTO(String empresa, String sector, String piscina, String fechaDesde,
            String fechaHasta) throws Exception;

    public RetornoTO getConsumoPorPiscinaPeriodo(String empresa, String codigoSector, String numeroPiscina,
            String fechaInicio, String fechaFin, String periodo, String usuario) throws Exception;

    public List<PrdUtilidadDiariaCorridaTO> getUtilidadDiariaCorrida(String empresa, String codigoSector, String numeroPiscina, String numeroCorrida) throws Exception;

    public RetornoTO getConsumosMensuales(String empresa, String codigoSector, String codigoBodega, String fechaInicio, String fechaFin, String usuario) throws Exception;

    @Transactional
    public List<PrdResumenFinancieroTO> getPrdResumenFinancieroTO(String empresa, String secCodigo, String desde,
            String hasta) throws Exception;

    @Transactional
    public List<PrdResumenPescaSiembraTO> getResumenPesca(String empresa, String codigoSector, String fechaInicio, String fechaFin,
            String usuario, boolean incluirTransferencia) throws Exception;

    @Transactional
    public List<PrdResumenPescaSiembraTO> getResumenSiembra(String empresa, String codigoSector, String fechaInicio, String fechaFin,
            String usuario) throws Exception;

    public List<PrdListaFunAnalisisPesosTO> getFunAnalisisPesos(String empresa, String sector, String fecha)
            throws Exception;

    @Transactional
    public List<String> getFunFechaSemanas(String empresa, String sector, String fecha) throws Exception;

    @Transactional
    public List<PrdListaConsultaGrameajePromedioPorPiscinaTO> getListaConsultaGrameajePromedioPorPiscinaTOs(
            String empresa, String sector, String nombreSector) throws Exception;

//    @Transactional
    public RetornoTO getConsultaGrameajePromedioPorPiscina(String empresa, String sector, String nombreSector, boolean sobrevivencia, SisInfoTO sisInfoTO)
            throws Exception;

    @Transactional
    public String insertarPrdCorrida(PrdCorrida prdCorrida, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String modificarPrdCorrida(PrdCorrida prdCorrida, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String eliminarPrdCorrida(PrdCorrida prdCorrida, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String cambiarCodigoPrdCorrida(PrdCorridaPK prdCorridaPK, String nuevoCodigoPrdCorrida) throws Exception;

    @Transactional
    public List<PrdCorrida> getListaCorridasPorEmpresaSectorPiscina(String empresa, String sector, String piscina)
            throws Exception;

    @Transactional
    public List<PrdCorrida> getListaCorridasPorEmpresa(String empresa) throws Exception;

    public List<PrdCorrida> getCorridaPorNumero(String empresa, String sector, String piscina, String numero)
            throws Exception;

    @Transactional
    public String insertarCorrida(PrdCorrida prdCorrida, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public String modificarCorrida(PrdCorrida prdCorrida, boolean cambioFechaDesde, boolean cambioFechaHasta, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> obtenerDetalleCorrida(String empresa, String sector, String piscina, String numero, Date fechaHasta) throws Exception;

    public Map<String, Object> obtenerDatosParaCrudCorrida(Map<String, Object> map) throws Exception;

    public Map<String, Object> getConsumosDiarios(String empresa, String codigoSector, String codigoBodega, String fechaInicio, String fechaFin, String usuario, String piscina, String codigoProducto) throws Exception;

    public Map<String, Object> getCostosDiarios(String empresa, String codigoSector, String codigoBodega, String fechaInicio, String fechaFin, String usuario, String piscina, String codigoProducto) throws Exception;

    public List<PrdListaCostosDetalleCorridaTO> completarProductos(List<PrdListaCostosDetalleCorridaTO> consumos, String empresa) throws Exception;

    public List<PrdCorrida> getComboPrdCorridaFiltradoFecha(String empresa, String sector, String piscina, Date fecha) throws Exception;

    public RetornoTO getPrdResumenTrazabilidadAlimentacionTO(String empresa, String sector, String tipo, String fechaInicio, String fechaFin, SisInfoTO sisInfoTO)
            throws Exception;

    public RetornoTO getPrdResumenCorridaSubcategoriaTO(String empresa, String sector, String tipo, String fechaInicio, String fechaFin, SisInfoTO sisInfoTO)
            throws Exception;

    public List<PrdTrazabilidadCorridaTO> getPrdTrazabilidadCorridaTO(String empresa, String sector, String piscina, String corrida, BigDecimal costo, String tipo) throws Exception;

    public List<PrdConsumosDetalladoProductosTO> getPrdConsumosDetalladoProductos(String empresa, String motivo, String sector, String piscina, String busqueda, String fechaDesde, String fechaHasta, String equipo) throws Exception;

    public List<PrdConsumosDetalladoProductosTO> getPrdConsumosDetalladoProductosAgrupadoCC(String empresa, String motivo, String sector, String piscina, String busqueda, String fechaDesde, String fechaHasta, String equipo) throws Exception;

    public List<PrdConsumosDetalladoProductosTO> getPrdConsumosDetalladoProductosAgrupadoEquipo(String empresa, String motivo, String sector, String piscina, String busqueda, String fechaDesde, String fechaHasta, String equipo) throws Exception;

    public List<PrdContabilizarCorridaCostoVentaTO> getResumenCorridaCostoVenta(String empresa, String fechaInicio, String fechaFin) throws Exception;

    public Map<String, Object> obtenerDatosConsumoDetalladoProductos(Map<String, Object> map) throws Exception;

}
