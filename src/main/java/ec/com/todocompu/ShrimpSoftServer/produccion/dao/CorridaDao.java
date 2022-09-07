package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.CostoDetalleMultipleCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.MultiplePiscinaCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdComboCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdConsumosDetalladoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdConsumosDiariosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdContabilizarCorridaCostoVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdContabilizarCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCorridaListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCostoDetalleFechaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCostoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdCostosDiariosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunAnalisisVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFunCostosPorFechaSimpleTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaConsultaGrameajePromedioPorPiscinaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaCostosDetalleCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaFunAnalisisPesosComplementoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaFunAnalisisPesosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenCorridaSubcategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenFinancieroTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenPescaSiembraTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdResumenTrazabilidadAlimentacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdTrazabilidadCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdUtilidadDiariaCorridaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorrida;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdCorridaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.math.BigDecimal;

public interface CorridaDao extends GenericDao<PrdCorrida, PrdCorridaPK> {

    @Transactional
    public boolean modificarBooleanoCorrida(List<PrdCorrida> prdCorrida) throws Exception;

    @Transactional
    public void actualizarSQL(String sql) throws Exception;

    public List<PrdListaCorridaTO> obtenerCorrida(String empresa, String sector, String piscina, Date fecha)
            throws Exception;

    public List<PrdListaCorridaTO> getListaCorrida(String empresa, String sector, String piscina, String fechaDesde, String fechaHasta, String tipo) throws Exception;

    public List<PrdCorridaListadoTO> listarCorridasPorFecha(String empresa, String desde, String hasta, String tipo) throws Exception;

    public List<PrdListaCorridaTO> getListaCorrida(String empresa, String sector, String piscina, String corrida)
            throws Exception;

    public List<PrdComboCorridaTO> getComboPrdCorrida(String empresa, String sector, String piscina) throws Exception;

    public List<PrdComboCorridaTO> getComboPrdCorridaFiltrado(String empresa, String sector, String piscina, Date fecha) throws Exception;

    public String consultarFechaMaxMin(String empresa, String tipoResumen) throws Exception;

    public PrdCorridaTO getPrdCorridaTO(String empresa, String sector, String piscina, String fecha) throws Exception;

    public List<MultiplePiscinaCorrida> getCostoDetallePersonalizado(String empresa, String sector, String fecha);

    public List<PrdListaConsultaGrameajePromedioPorPiscinaTO> getListaConsultaGrameajePromedioPorPiscinaTOs(
            String empresa, String sector, String nombreSector) throws Exception;

    public List<PrdListaCostosDetalleCorridaTO> getPrdListaCostosDetalleCorridaTO(String empresa, String sector,
            String piscina, String corrida, String desde, String hasta, java.lang.String agrupadoPor) throws Exception;

    public List<PrdResumenCorridaTO> getListaResumenCorridaTO(String empresa, String sector, String desde, String hasta,
            String tipoResumen) throws Exception;

    public List<PrdContabilizarCorridaTO> getListaContabilizarCorridaTO(String empresa, String desde, String hasta)
            throws Exception;

    public List<PrdResumenPescaSiembraTO> getResumenPesca(String empresa, String codigoSector, String fechaInicio,
            String fechaFin, boolean incluirTransferencia) throws Exception;

    public List<PrdResumenPescaSiembraTO> getResumenSiembra(String empresa, String codigoSector, String fechaInicio,
            String fechaFin) throws Exception;

    public PrdFunAnalisisVentasTO getPrdFunAnalisisVentasTO(String empresa, String sector, String piscina,
            String fechaDesde, String fechaHasta) throws Exception;

    public List<PrdCostoDetalleFechaTO> getPrdListadoCostoDetalleFechaTO(String empresa, java.lang.String secCodigo,
            String desde, String hasta, String agrupadoPor) throws Exception;

    @Transactional
    public List<CostoDetalleMultipleCorridaTO> getCostoDetalleMultipleCorridaTO(String empresa, String hasta,
            String agrupadoPor) throws Exception;

    @Transactional
    public List<PrdCostoTO> getCostoPorFechaProrrateado(String empresa, String codigoSector, String fechaInicio,
            String fechaFin, String agrupadoPor) throws Exception;

    public List<PrdFunCostosPorFechaSimpleTO> getPrdFunCostosPorFechaSimpleTO(String codigoSector, String fechaInicio,
            String fechaFin, String infEmpresea) throws Exception;

    @Transactional
    public List<PrdCostoTO> getCostoPorPorPiscinaSemanal(String empresa, String codigoSector, String numeroPiscina,
            String fechaInicio, String fechaFin, java.lang.String agrupadoPor, String periodo) throws Exception;

    @Transactional
    public List<PrdCostoTO> getCostosMensuales(String empresa, String codigoSector, String codigoBodega,
            String fechaInicio, String fechaFin, java.lang.String agrupadoPor) throws Exception;

    @Transactional
    public List<PrdConsumosTO> getConsumoPorFechaDesglosado(String empresa, String codigoSector, String fechaInicio,
            String fechaFin) throws Exception;

    @Transactional
    public List<PrdConsumosTO> getConsumoProductosProceso(String empresa, String sector, String fecha) throws Exception;

    @Transactional
    public List<PrdConsumosTO> getConsumoMultiplePiscina(String empresa) throws Exception;

    public List<PrdConsumosTO> getPrdConsumosFechaTO(String empresa, String sector, String fechaDesde,
            String fechaHasta) throws Exception;

    public List<PrdConsumosTO> getPrdConsumosPiscinaTO(String empresa, String sector, String piscina, String fechaDesde,
            String fechaHasta) throws Exception;

    @Transactional
    public List<PrdConsumosTO> getConsumoPorPiscinaPeriodo(String empresa, String codigoSector, String numeroPiscina,
            String fechaInicio, String fechaFin, String periodo) throws Exception;

    @Transactional
    public List<PrdUtilidadDiariaCorridaTO> getUtilidadDiariaCorrida(String empresa, String codigoSector, String numeroPiscina, String numeroCorrida) throws Exception;

    @Transactional
    public List<PrdConsumosTO> getConsumosMensuales(String empresa, String codigoSector, String codigoBodega, String fechaInicio, String fechaFin) throws Exception;

    public List<PrdResumenFinancieroTO> getPrdResumenFinancieroTO(String empresa, java.lang.String secCodigo,
            String desde, String hasta) throws Exception;

    public Boolean comprobarPrdBalanceado(String empresa, String codigoPrincipal) throws Exception;

    @Transactional
    public List<PrdListaFunAnalisisPesosTO> getFunAnalisisPesos(String empresa, String sector, String fechaHasta)
            throws Exception;

    @Transactional
    public List<PrdListaFunAnalisisPesosComplementoTO> getFunAnalisisPesosComplemento(String empresa, String sector,
            String fechaHasta) throws Exception;

    public List<String> getFunFechaSemanas(String empresa, String sector, String fechaHasta) throws Exception;

    @Transactional
    public boolean eliminarPrdCorrida(String empresa, String sector, String piscina, String numero) throws Exception;

    @Transactional
    public String cambiarCodigoPrdCorrida(PrdCorridaPK prdCorridaPK, String nuevoCodigoCorrida) throws Exception;

    @Transactional
    public String validarFechasComprasyConsumosHuerfanos(String empresa, String sector, String piscina, String numero,
            Date desde, Date hasta, String accion) throws Exception;

    @Transactional
    public boolean insertarPrdCorrida(PrdCorrida prdCorrida, SisSuceso sisSuceso) throws Exception;

    @Transactional
    public boolean modificarPrdCorrida(PrdCorrida prdCorrida, SisSuceso sisSuceso) throws Exception;

    @Transactional
    public boolean eliminarPrdCorrida(PrdCorrida prdCorrida, SisSuceso sisSuceso) throws Exception;

    @Transactional
    public PrdCorrida obtenerCorrida(PrdCorridaPK prdCorridaPK) throws Exception;

    @Transactional
    public List<PrdCorrida> getListaCorridasPorEmpresaSectorPiscina(String empresa, String sector, String piscina);

    @Transactional
    public List<PrdCorrida> getListaCorridasPorEmpresa(String empresa);

    @Transactional
    public List<PrdCorrida> getCorridaPorNumero(String empresa, String sector, String piscina, String corrida);

    public List<PrdConsumosDiariosTO> getConsumosDiarios(String empresa, String codigoSector, String codigoBodega, String fechaInicio, String fechaFin, String piscina, String codigoProducto) throws Exception;

    public List<PrdCostosDiariosTO> getCostosDiarios(String empresa, String codigoSector, String codigoBodega, String fechaInicio, String fechaFin, String piscina, String codigoProducto) throws Exception;

    public List<PrdCorrida> getComboPrdCorridaFiltradoFecha(String empresa, String sector, String piscina, Date fecha) throws Exception;

    public List<PrdResumenTrazabilidadAlimentacionTO> getPrdResumenTrazabilidadAlimentacionTO(String empresa, String sector, String tipo, String fechaInicio, String fechaFin);

    public List<PrdResumenCorridaSubcategoriaTO> getPrdResumenCorridaSubcategoriaTO(String empresa, String sector, String tipo, String fechaInicio, String fechaFin);

    public List<PrdTrazabilidadCorridaTO> getPrdTrazabilidadCorridaTO(String empresa, String sector, String piscina, String corrida, BigDecimal costo, String tipo) throws Exception;

    public List<PrdConsumosDetalladoProductosTO> getPrdConsumosDetalladoProductos(String empresa, String motivo, String sector, String piscina, String busqueda, String fechaDesde, String fechaHasta, String equipo) throws Exception;

    public List<PrdConsumosDetalladoProductosTO> getPrdConsumosDetalladoProductosAgrupadoCC(String empresa, String motivo, String sector, String piscina, String busqueda, String fechaDesde, String fechaHasta, String equipo) throws Exception;

    public List<PrdConsumosDetalladoProductosTO> getPrdConsumosDetalladoProductosAgrupadoEquipo(String empresa, String motivo, String sector, String piscina, String busqueda, String fechaDesde, String fechaHasta, String equipo) throws Exception;

    public List<PrdContabilizarCorridaCostoVentaTO> getResumenCorridaCostoVenta(String empresa, String fechaInicio, String fechaFin) throws Exception;

    public List<String> obtenerLaboratiroDeCorrida(String empresa) throws Exception;

    public List<String> obtenerNauplioDeCorrida(String empresa) throws Exception;
}
