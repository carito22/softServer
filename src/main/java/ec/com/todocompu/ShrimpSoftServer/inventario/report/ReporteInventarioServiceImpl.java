package ec.com.todocompu.ShrimpSoftServer.inventario.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.CompraService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.SustentoService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.report.ReporteContabilidadService;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClientesVentasDetalleDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.BodegaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ClienteService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasDetalleImbService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasDetalleService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasMotivoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ConsumosDetalleService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ConsumosService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.GuiaRemisionService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.GuiaRutasService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProformasDetalleService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProformasService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProveedorService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.TransferenciasDetalleService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.TransferenciasService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.SectorService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.UsuarioService;
import ec.com.todocompu.ShrimpSoftUtils.DetalleExportarFiltrado;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxSustentoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteContratoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteContratosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCompraCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDetalleImbTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunComprasConsolidandoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunConsumosConsolidandoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListadoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListadoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunMovimientosSerieTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasConsolidandoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasConsolidandoProductosCoberturaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasConsolidandoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasVendedorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasVsCostoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvKardexTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaBodegasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaProformaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaTransferenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleConsumoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleProformasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleTransferenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvNumeracionCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvNumeracionConsumoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvNumeracionVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoMarcaComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoMedidaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionCajasComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionUnidadesComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoTipoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductosConErrorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVendedorComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasFormaCobroTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.SaldoBodegaComprobacionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.SaldoBodegaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvBodega;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientesVentasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemision;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRutas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProducto;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.DatoFunListaProductosImpresionPlaca;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.InvListaConsultaCompra;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.InvListaConsultaConsumo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.InvListaConsultaTransferencia;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.InvListaConsultaVenta;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.ReporteCompraDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.ReporteConsumoDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.ReporteProformaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.ReporteTransferenciaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.ReporteVentaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuario;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContrato;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalleImb;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import java.io.File;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ReporteInventarioServiceImpl implements ReporteInventarioService {

    @Autowired
    private GenericReporteService genericReporteService;
    @Autowired
    private ConsumosDetalleService consumosDetalleService;
    @Autowired
    private ConsumosService consumosService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TransferenciasDetalleService transferenciasDetalleService;
    @Autowired
    private TransferenciasService transferenciasService;
    @Autowired
    private GuiaRutasService guiaRutasService;
    private String modulo = "inventario";
    @Autowired
    private CompraService compraService;
    @Autowired
    private ComprasService comprasService;
    @Autowired
    private ComprasDetalleService comprasDetalleService;
    @Autowired
    private ComprasDetalleImbService comprasDetalleImbService;
    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private ProformasService proformasService;
    @Autowired
    private ProformasDetalleService proformasDetalleService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ClientesVentasDetalleDao clientesVentasDetalleDao;
    @Autowired
    private GuiaRemisionService guiaRemisionService;
    @Autowired
    private SustentoService sustentoService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private ReporteContabilidadService reporteContabilidadService;
    @Autowired
    private ComprasMotivoService comprasMotivoService;
    @Autowired
    private BodegaService bodegaService;
    @Autowired
    private SectorService sectorService;

    @Override
    public byte[] generarReporteBodega(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvListaBodegasTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportBodega.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteCategoriaCliente(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvClienteCategoriaTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportCategoriaCliente.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteCategoriaProveedor(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProveedorCategoriaTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportCategoriaProveedor.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteFormaPago(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvComprasFormaPagoTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportFormaPago.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteFormaCobro(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvVentasFormaCobroTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportFormaCobro.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteMotivoCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvComprasMotivoTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportMotivoCompra.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteMotivoVenta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvVentaMotivoTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportMotivoVenta.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteMotivoConsumo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvConsumosMotivoTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportMotivoConsumo.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteMotivoProforma(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProformaMotivoTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportMotivoProforma.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteMotivoTransferencia(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvTransferenciaMotivoTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportMotivoTransferencia.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteNumeracionCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvNumeracionCompraTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportNumeracionCompra.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteNumeracionVenta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvNumeracionVentaTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportNumeracionVenta.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteNumeracionConsumo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvNumeracionConsumoTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportNumeracionConsumo.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteProductos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvListaProductosGeneralTO> listado, List<InvFunListadoProductosTO> listado2) throws Exception {
        String nombreReporte = "reportProductos.jrxml";
        List listaEnviar = listado;
        if (listado2 != null) {
            nombreReporte = "reportProductosListado.jrxml";
            listaEnviar = listado2;
        }

        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaEnviar);
    }

    @Override
    public byte[] generarReporteInvKardex(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreProducto, String fechaDesde, String fechaHasta, List<InvKardexTO> listInvKardexTO, boolean banderaCosto) throws Exception {
        boolean excamecor = usuarioEmpresaReporteTO.getEmpRuc().equals("0791768039001");

        List<ReporteInvKardexDetalle> listaReporteInvKardexDetalleParametro = new ArrayList<ReporteInvKardexDetalle>();
        for (InvKardexTO invKardexTO : listInvKardexTO) {
            BigDecimal entradaC = BigDecimal.ZERO;
            BigDecimal salidaC = BigDecimal.ZERO;
            BigDecimal saldoC = BigDecimal.ZERO;
            BigDecimal conversion = invKardexTO.getkFactorConversion() == null ? BigDecimal.ZERO : invKardexTO.getkFactorConversion();
            if (conversion.compareTo(java.math.BigDecimal.ZERO) > 0 && excamecor) {
                entradaC = (invKardexTO.getkEntradasCantidad() == null ? BigDecimal.ZERO : invKardexTO.getkEntradasCantidad()).divide(conversion, 2, java.math.RoundingMode.HALF_UP);
                salidaC = (invKardexTO.getkSalidasCantidad() == null ? BigDecimal.ZERO : invKardexTO.getkSalidasCantidad()).divide(conversion, 2, java.math.RoundingMode.HALF_UP);
                saldoC = (invKardexTO.getkSaldosCantidad() == null ? BigDecimal.ZERO : invKardexTO.getkSaldosCantidad()).divide(conversion, 2, java.math.RoundingMode.HALF_UP);
            }

            ReporteInvKardexDetalle reporteInvKardexDetalle = new ReporteInvKardexDetalle();
            reporteInvKardexDetalle.setSisUsuarioEmpresaTO(usuarioEmpresaReporteTO.getEmpRazonSocial());
            reporteInvKardexDetalle.setNomProducto(nombreProducto);
            reporteInvKardexDetalle.setFechaDesde(fechaDesde);
            reporteInvKardexDetalle.setFechaHasta(fechaHasta);
            reporteInvKardexDetalle.setKtransaccion(invKardexTO.getkTransaccion());
            reporteInvKardexDetalle.setkFecha(invKardexTO.getkFecha());
            reporteInvKardexDetalle.setkEntradasCantidad(
                    (invKardexTO.getkEntradasCantidad() == null ? null : invKardexTO.getkEntradasCantidad()));
            reporteInvKardexDetalle.setkEntradasCosto(
                    (invKardexTO.getkEntradasCosto() == null ? null : invKardexTO.getkEntradasCosto()));//
            reporteInvKardexDetalle.setkSalidasCantidad(invKardexTO.getkSalidasCantidad());
            reporteInvKardexDetalle.setkSalidasCosto(invKardexTO.getkSalidasCosto());//
            reporteInvKardexDetalle.setkSaldosCantidad(invKardexTO.getkSaldosCantidad());
            reporteInvKardexDetalle.setkSaldosCosto(invKardexTO.getkSaldosCosto());//
            reporteInvKardexDetalle.setkCostoActual(invKardexTO.getkCostoActual());//
            reporteInvKardexDetalle.setkSectorPiscina(invKardexTO.getkSectorPiscina());
            reporteInvKardexDetalle.setkNúmeroSistema(invKardexTO.getkNumeroSistema());
            reporteInvKardexDetalle.setkCategoria(invKardexTO.getkCategoria());
            reporteInvKardexDetalle.setkFactorConversion(invKardexTO.getkFactorConversion());
            reporteInvKardexDetalle.setkDocumentoNumero(
                    invKardexTO.getkDocumentoNumero() == null ? null : invKardexTO.getkDocumentoNumero());
            reporteInvKardexDetalle.setkObservaciones(invKardexTO.getkObservaciones());
            //
            reporteInvKardexDetalle.setkEntradasCaja(entradaC);
            reporteInvKardexDetalle.setkSalidasCaja(salidaC);
            reporteInvKardexDetalle.setkSaldosCaja(saldoC);
            listaReporteInvKardexDetalleParametro.add(reporteInvKardexDetalle);
        }

        return genericReporteService.generarReporte(modulo,
                banderaCosto ? "reportInventarioKardexValorizado.jrxml" : (excamecor ? "reportInventarioKardexExcamecor.jrxml" : "reportInventarioKardex.jrxml"),
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaReporteInvKardexDetalleParametro);
    }

    @Override
    public byte[] generarReporteImpresionPlaca(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<DatoFunListaProductosImpresionPlaca> listProductosImpresionPlaca) throws Exception {
        List<ReporteProductosImpresionPlaca> listaProductosImpresionPlacas = new ArrayList<ReporteProductosImpresionPlaca>();
        for (DatoFunListaProductosImpresionPlaca dflpip : listProductosImpresionPlaca) {
            if (dflpip.isEstado()) {
                ReporteProductosImpresionPlaca rpip = new ReporteProductosImpresionPlaca();
                rpip.setLpspCodigoPrincipal(dflpip.getLpspCodigoPrincipal());
                rpip.setLpspCodigoBarra(dflpip.getLpspCodigoBarra());
                rpip.setLpspNombre(dflpip.getLpspNombre());
                rpip.setProEmpaque(dflpip.getProEmpaque());
                rpip.setLpspPrecio1(dflpip.getLpspPrecio1());
                rpip.setLpspPrecio2(dflpip.getLpspPrecio2());
                rpip.setLpspPrecio3(dflpip.getLpspPrecio3());
                listaProductosImpresionPlacas.add(rpip);
            }
        }
        return genericReporteService.generarReporte(modulo, "reportInvImpresionPlaca.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaProductosImpresionPlacas);
    }

    @Override
    public byte[] generarReporteListadoCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String motivo, String proveedorId, String documento, List<InvFunComprasTO> listInvFunComprasTO) throws Exception {
        List<ReporteListadoCompras> reporteListadoComprases = new ArrayList<ReporteListadoCompras>();
        for (InvFunComprasTO ifcto : listInvFunComprasTO) {
            ReporteListadoCompras reporteListadoCompras = new ReporteListadoCompras();
            reporteListadoCompras.setDesde(fechaDesde);
            reporteListadoCompras.setHasta(fechaHasta);
            reporteListadoCompras.setMotivo(motivo);
            reporteListadoCompras.setProveedor(proveedorId);
            reporteListadoCompras.setTipoDocumento(documento);
            reporteListadoCompras.setCompNumeroSistema(ifcto.getCompNumeroSistema());
            reporteListadoCompras.setCompFecha(ifcto.getCompFecha());
            reporteListadoCompras.setCompProveedorRuc(ifcto.getCompProveedorRuc());
            reporteListadoCompras.setCompProveedorNombre(ifcto.getCompProveedorNombre());
            reporteListadoCompras.setCompDocumentoTipo(ifcto.getCompDocumentoTipo());
            reporteListadoCompras.setCompDocumentoNumero(ifcto.getCompDocumentoNumero());
            reporteListadoCompras.setCompDocumentoAutorizacion(ifcto.getCompDocumentoAutorizacion());
            reporteListadoCompras.setCompBase0(ifcto.getCompBase0());
            reporteListadoCompras.setCompBaseImponible(ifcto.getCompBaseImponible());
            reporteListadoCompras.setCompMontoIva(ifcto.getCompMontoIva());
            reporteListadoCompras.setCompTotal(ifcto.getCompTotal());
            reporteListadoCompras.setCompFormaPago(ifcto.getCompFormaPago());
            reporteListadoCompras.setCompObservaciones(ifcto.getCompObservaciones());
            reporteListadoCompras.setCompPendiente(ifcto.getCompPendiente());
            reporteListadoCompras.setCompAnulado(ifcto.getCompAnulado());
            reporteListadoComprases.add(reporteListadoCompras);
        }
        return genericReporteService.generarReporte(modulo, "reportListadoCompras.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteListadoComprases);
    }

    @Override
    public byte[] generarReporteConsolidadoConsumoProducto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta,
            String bodega, String sector, String motivo, List<InvFunConsumosConsolidandoProductosTO> listInvFunConsumosConsolidandoProductosTO) throws Exception {

        List<ReporteConsolidadoProducto> reporteConsolidadoProductos = new ArrayList<ReporteConsolidadoProducto>();
        for (InvFunConsumosConsolidandoProductosTO ifccpto : listInvFunConsumosConsolidandoProductosTO) {
            ReporteConsolidadoProducto reporteConsolidadoProducto = new ReporteConsolidadoProducto();
            reporteConsolidadoProducto.setDesde(fechaDesde);
            reporteConsolidadoProducto.setHasta(fechaHasta);
            reporteConsolidadoProducto.setBodega(bodega);
            reporteConsolidadoProducto.setSector(sector);
            reporteConsolidadoProducto.setMotivo(motivo);
            reporteConsolidadoProducto.setCcpProducto(ifccpto.getCcpProducto());
            reporteConsolidadoProducto.setCcpCodigo(ifccpto.getCcpCodigo());
            reporteConsolidadoProducto.setCcpMedida(ifccpto.getCcpMedida());
            reporteConsolidadoProducto.setCcpCantidad(ifccpto.getCcpCantidad());
            reporteConsolidadoProducto.setCcpTotal(ifccpto.getCcpCostoTotal());
            reporteConsolidadoProducto.setCcpPorcentaje(ifccpto.getCcpPorcentaje());

            reporteConsolidadoProductos.add(reporteConsolidadoProducto);
        }
        return genericReporteService.generarReporte(modulo, "reportConsolidadoConsumoProducto.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteConsolidadoProductos);
    }

    @Override
    public byte[] generarReporteConsolidadoCompraProducto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String bodega, String proveedor, List<InvFunComprasConsolidandoProductosTO> listInvFunComprasConsolidandoProductosTO) throws Exception {
        List<ReporteConsolidadoProducto> reporteConsolidadoProductos = new ArrayList<ReporteConsolidadoProducto>();
        for (InvFunComprasConsolidandoProductosTO ifccpto : listInvFunComprasConsolidandoProductosTO) {
            ReporteConsolidadoProducto reporteConsolidadoProducto = new ReporteConsolidadoProducto();
            reporteConsolidadoProducto.setDesde(fechaDesde);
            reporteConsolidadoProducto.setHasta(fechaHasta);
            reporteConsolidadoProducto.setBodega(bodega);
            reporteConsolidadoProducto.setProveedor(proveedor);
            reporteConsolidadoProducto.setCcpProducto(ifccpto.getCcpProducto());
            reporteConsolidadoProducto.setCcpCodigo(ifccpto.getCcpCodigo());
            reporteConsolidadoProducto.setCcpMedida(ifccpto.getCcpMedida());
            reporteConsolidadoProducto.setCcpCantidad(ifccpto.getCcpCantidad());
            reporteConsolidadoProducto.setCcpTotal(ifccpto.getCcpTotal());
            reporteConsolidadoProducto.setCcpPorcentaje(ifccpto.getCcpPorcentaje());
            reporteConsolidadoProducto.setEsProveedor(true);
            reporteConsolidadoProductos.add(reporteConsolidadoProducto);
        }
        return genericReporteService.generarReporte(modulo, "reportConsolidadoCompraVentaProducto.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteConsolidadoProductos);
    }

    @Override
    public byte[] generarReporteListadoVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String motivo, String cliente, String documento, List<InvFunVentasTO> listInvFunVentasTO) throws Exception {
        List<ReporteListadoVentas> reporteListadoVentases = new ArrayList<ReporteListadoVentas>();
        for (InvFunVentasTO ifvto : listInvFunVentasTO) {
            ReporteListadoVentas reporteListadoVentas = new ReporteListadoVentas();
            reporteListadoVentas.setDesde(fechaDesde);
            reporteListadoVentas.setHasta(fechaHasta);
            reporteListadoVentas.setMotivo(motivo);
            reporteListadoVentas.setCliente(cliente);
            reporteListadoVentas.setIdentificacion(ifvto.getVtaIdentificacion());
            reporteListadoVentas.setTipoDocumento(documento);
            reporteListadoVentas.setVtaNumeroSistema(ifvto.getVtaNumeroSistema());
            reporteListadoVentas.setVtaFecha(ifvto.getVtaFecha());
            reporteListadoVentas.setVtaCliente(ifvto.getVtaCliente());
            reporteListadoVentas.setVtaDocumentoNumero(ifvto.getVtaDocumentoNumero());
            reporteListadoVentas.setVtaCantidad(ifvto.getVtaCantidad());
            reporteListadoVentas.setVtaBase0(ifvto.getVtaBase0());
            reporteListadoVentas.setVtaBaseImponible(ifvto.getVtaBaseImponible());
            reporteListadoVentas.setVtaMontoIva(ifvto.getVtaMontoIva());
            reporteListadoVentas.setVtaTotal(ifvto.getVtaTotal());
            reporteListadoVentas.setVtaSector(ifvto.getVtaSector());
            reporteListadoVentas.setVtaFormaPago(ifvto.getVtaFormaPago());
            reporteListadoVentas.setVtaObservaciones(ifvto.getVtaObservaciones());
            reporteListadoVentas.setVtaPendiente(ifvto.getVtaPendiente());
            reporteListadoVentas.setVtaAnulado(ifvto.getVtaAnulado());
            reporteListadoVentases.add(reporteListadoVentas);
        }
        return genericReporteService.generarReporte(modulo, "reportListadoVentas.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteListadoVentases);
    }

    @Override
    public byte[] generarReporteConsolidadoVentaProducto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String bodega, String cliente, List<InvFunVentasConsolidandoProductosTO> listInvFunVentasConsolidandoProductosTO) throws Exception {
        List<ReporteConsolidadoProducto> reporteConsolidadoProductos = new ArrayList<ReporteConsolidadoProducto>();
        for (InvFunVentasConsolidandoProductosTO ifccpto : listInvFunVentasConsolidandoProductosTO) {
            ReporteConsolidadoProducto reporteConsolidadoProducto = new ReporteConsolidadoProducto();
            reporteConsolidadoProducto.setDesde(fechaDesde);
            reporteConsolidadoProducto.setHasta(fechaHasta);
            reporteConsolidadoProducto.setBodega(bodega);
            reporteConsolidadoProducto.setProveedor(cliente);
            reporteConsolidadoProducto.setCcpProducto(ifccpto.getVcpProducto());
            reporteConsolidadoProducto.setCcpCodigo(ifccpto.getVcpCodigo());
            reporteConsolidadoProducto.setCcpMedida(ifccpto.getVcpMedida());
            reporteConsolidadoProducto.setCcpCantidad(ifccpto.getVcpCantidad());
            reporteConsolidadoProducto.setCcpTotal(ifccpto.getVcpTotal());
            reporteConsolidadoProducto.setCcpPorcentaje(ifccpto.getVcpPorcentaje());
            reporteConsolidadoProducto.setEsProveedor(false);
            reporteConsolidadoProductos.add(reporteConsolidadoProducto);
        }
        return genericReporteService.generarReporte(modulo, "reportConsolidadoCompraVentaProducto.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteConsolidadoProductos);
    }

    @Override
    public byte[] generarReporteConsolidadoVentaProductoCobertura(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String sector, String bodega, String motivo, String cliente, List<InvFunVentasConsolidandoProductosCoberturaTO> listado, String nombreReporte) throws Exception {
        List<ReporteConsolidadoProducto> reporteConsolidadoProductos = new ArrayList<ReporteConsolidadoProducto>();
        for (InvFunVentasConsolidandoProductosCoberturaTO ifccpto : listado) {
            ReporteConsolidadoProducto reporteConsolidadoProducto = new ReporteConsolidadoProducto();
            reporteConsolidadoProducto.setDesde(fechaDesde);
            reporteConsolidadoProducto.setHasta(fechaHasta);
            reporteConsolidadoProducto.setBodega(bodega);
            reporteConsolidadoProducto.setMotivo(motivo);
            reporteConsolidadoProducto.setSector(sector);
            reporteConsolidadoProducto.setCliente(cliente);
            reporteConsolidadoProducto.setCcpProducto(ifccpto.getVcpProducto());
            reporteConsolidadoProducto.setCcpCodigo(ifccpto.getVcpCodigo());
            reporteConsolidadoProducto.setCcpMedida(ifccpto.getVcpMedida());
            reporteConsolidadoProducto.setCcpCantidad(ifccpto.getVcpCantidad());
            reporteConsolidadoProducto.setCcpTotal(ifccpto.getVcpTotal());
            reporteConsolidadoProducto.setCcpPorcentaje(ifccpto.getVcpPorcentaje());
            reporteConsolidadoProductos.add(reporteConsolidadoProducto);
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteConsolidadoProductos);
    }

    @Override
    public byte[] generarReporteInvProductoCategoriaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoCategoriaTO> listaInvProductoCategoriaTO, String nombreReporte) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaInvProductoCategoriaTO);
    }

    @Override
    public byte[] generarReporteConsolidadoVentaCliente(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String sector, List<InvFunVentasConsolidandoClientesTO> listInvFunVentasConsolidandoClientesTO) throws Exception {
        List<ReporteConsolidadoCliente> reporteConsolidadoClientes = new ArrayList<ReporteConsolidadoCliente>();
        for (InvFunVentasConsolidandoClientesTO ifccpto : listInvFunVentasConsolidandoClientesTO) {
            ReporteConsolidadoCliente reporteConsolidadoCliente = new ReporteConsolidadoCliente();
            reporteConsolidadoCliente.setDesde(fechaDesde);
            reporteConsolidadoCliente.setHasta(fechaHasta);
            reporteConsolidadoCliente.setSector(sector);
            reporteConsolidadoCliente.setVtaIdentificacion(ifccpto.getVtaIdentificacion());
            reporteConsolidadoCliente.setVtaNombreCliente(ifccpto.getVtaNombreCliente());
            reporteConsolidadoCliente.setVtaNumeroComprobantes(ifccpto.getVtaNumeroComprobantes());
            reporteConsolidadoCliente.setVtaBase0(ifccpto.getVtaBase0());
            reporteConsolidadoCliente.setVtaBaseImponible(ifccpto.getVtaBaseimponible());
            reporteConsolidadoCliente.setVtaMontoIva(ifccpto.getVtaMontoiva());
            reporteConsolidadoCliente.setVtaTotal(ifccpto.getVtaTotal());
            reporteConsolidadoClientes.add(reporteConsolidadoCliente);
        }
        return genericReporteService.generarReporte(modulo, "reportConsolidadoCompraVentaCliente.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteConsolidadoClientes);
    }

    @Override
    public byte[] generarReporteListadoConsumos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, List<InvFunConsumosTO> listInvFunConsumosTO) throws Exception {
        List<ReporteListadoConsumos> reporteListadoConsumos = new ArrayList<ReporteListadoConsumos>();
        for (InvFunConsumosTO ifvto : listInvFunConsumosTO) {
            ReporteListadoConsumos consumos = new ReporteListadoConsumos();
            consumos.setDesde(fechaDesde);
            consumos.setHasta(fechaHasta);
            consumos.setConNumeroSistema(ifvto.getCompNumeroSistema());
            consumos.setConFecha(ifvto.getCompFecha());
            consumos.setConObservaciones(ifvto.getCompObservaciones());
            consumos.setConPendiente(ifvto.getCompPendiente());
            consumos.setConAnulado(ifvto.getCompAnulado());
            reporteListadoConsumos.add(consumos);
        }
        return genericReporteService.generarReporte(modulo, "reportListadoConsumos.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteListadoConsumos);
    }

    @Override
    public byte[] generarReporteListaProductos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String bodega, List<InvListaProductosTO> listInvListaProductosTO) throws Exception {
        List<ReporteListaProductos> reporteListaProductoses = new ArrayList<ReporteListaProductos>();
        for (InvListaProductosTO ilpto : listInvListaProductosTO) {
            ReporteListaProductos reporteListaProductos = new ReporteListaProductos();
            reporteListaProductos.setBodega(bodega);
            reporteListaProductos.setProCodigoPrincipal(ilpto.getProCodigoPrincipal());
            reporteListaProductos.setProCodigoBarra(ilpto.getProCodigoBarra());
            reporteListaProductos.setProNombre(ilpto.getProNombre());
            reporteListaProductos.setProCategoria(ilpto.getProCategoria());
            reporteListaProductos.setStockSaldo(ilpto.getStockSaldo());
            reporteListaProductos.setStockCosto(ilpto.getStockUltimoCosto());
            reporteListaProductos.setDetalleMedida(ilpto.getDetalleMedida());
            reporteListaProductos.setStockPrecio1(ilpto.getStockPrecio1());
            reporteListaProductos.setStockPrecio2(ilpto.getStockPrecio2());
            reporteListaProductos.setStockPrecio3(ilpto.getStockPrecio3());
            reporteListaProductos.setStockPrecio4(ilpto.getStockPrecio4());
            reporteListaProductos.setStockPrecio5(ilpto.getStockPrecio5());
            reporteListaProductos.setProGravaIva(ilpto.getProGravaIva().equals("GRAVA") ? true : false);
            reporteListaProductoses.add(reporteListaProductos);
        }
        return genericReporteService.generarReporte(modulo, "reportListaProductos.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteListaProductoses);
    }

    @Override
    public byte[] generarReporteSaldoBodega(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String bodega, String fechaHasta, List<SaldoBodegaTO> listSaldoBodegaTO) throws Exception {
        List<ReporteSaldoBodega> lista = new ArrayList<>();
        for (SaldoBodegaTO sbto : listSaldoBodegaTO) {
            ReporteSaldoBodega reporteSaldoBodega = new ReporteSaldoBodega(bodega, fechaHasta,
                    sbto.getSbBodega() == null ? "" : sbto.getSbBodega(),
                    sbto.getSbProducto() == null ? "" : sbto.getSbProducto(),
                    sbto.getSbNombre() == null ? "" : sbto.getSbNombre(),
                    sbto.getSbMedida() == null ? "" : sbto.getSbMedida(),
                    sbto.getSbStock() == null ? null : sbto.getSbStock(),
                    sbto.getSbCosto() == null ? null : sbto.getSbCosto(),
                    sbto.getSbTotal() == null ? null : sbto.getSbTotal(),
                    sbto.getSbCajetas() == null ? null : sbto.getSbCajetas());
            reporteSaldoBodega.setSbSerie(sbto.getSbSerie() != null ? sbto.getSbSerie() : "");
            lista.add(reporteSaldoBodega);
        }
        return genericReporteService.generarReporte(modulo, "reportSaldoBodega.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), lista);
    }

    @Override
    public byte[] generarReporteSaldoBodegaComprobacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String bodega, String fechaDesde, String fechaHasta, List<SaldoBodegaComprobacionTO> lista) throws Exception {
        List<ReporteSaldoBodegaComprobacion> lt = new ArrayList<ReporteSaldoBodegaComprobacion>();
        for (SaldoBodegaComprobacionTO sbcTO : lista) {
            ReporteSaldoBodegaComprobacion reporteSaldoBodega = new ReporteSaldoBodegaComprobacion(bodega, fechaDesde,
                    fechaHasta, sbcTO.getSbcProductoNombre() == null ? "" : sbcTO.getSbcProductoNombre(),
                    sbcTO.getSbcProductoCodigo() == null ? "" : sbcTO.getSbcProductoCodigo(),
                    sbcTO.getSbcMedida() == null ? "" : sbcTO.getSbcMedida(),
                    sbcTO.getSbcInicial() == null ? null : sbcTO.getSbcInicial(),
                    sbcTO.getSbcCompra() == null ? null : sbcTO.getSbcCompra(),
                    sbcTO.getSbcVenta() == null ? null : sbcTO.getSbcVenta(),
                    sbcTO.getSbcConsumo() == null ? null : sbcTO.getSbcConsumo(),
                    sbcTO.getSbcTransferenciaI() == null ? null : sbcTO.getSbcTransferenciaI(),
                    sbcTO.getSbcTransferenciaE() == null ? null : sbcTO.getSbcTransferenciaE(),
                    sbcTO.getSbcDevolucionC() == null ? null : sbcTO.getSbcDevolucionC(),
                    sbcTO.getSbcDevolucionV() == null ? null : sbcTO.getSbcDevolucionV(),
                    sbcTO.getSbcFinal() == null ? null : sbcTO.getSbcFinal(),
                    sbcTO.getSbcDiferencia() == null ? null : sbcTO.getSbcDiferencia());
            lt.add(reporteSaldoBodega);
        }
        return genericReporteService.generarReporte(modulo, "reportSaldoBodegaComprobacion.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), lt);
    }

    @Override
    public byte[] generarReporteSaldoBodegaComprobacionCantidades(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String bodega, String fechaDesde, String fechaHasta, List<SaldoBodegaComprobacionTO> lista) throws Exception {
        List<ReporteSaldoBodegaComprobacion> lt = new ArrayList<ReporteSaldoBodegaComprobacion>();
        for (SaldoBodegaComprobacionTO sbcTO : lista) {
            ReporteSaldoBodegaComprobacion reporteSaldoBodega = new ReporteSaldoBodegaComprobacion(bodega, fechaDesde,
                    fechaHasta, sbcTO.getSbcProductoNombre() == null ? "" : sbcTO.getSbcProductoNombre(),
                    sbcTO.getSbcProductoCodigo() == null ? "" : sbcTO.getSbcProductoCodigo(),
                    sbcTO.getSbcMedida() == null ? "" : sbcTO.getSbcMedida(),
                    sbcTO.getSbcInicial() == null ? null : sbcTO.getSbcInicial(),
                    sbcTO.getSbcCompra() == null ? null : sbcTO.getSbcCompra(),
                    sbcTO.getSbcVenta() == null ? null : sbcTO.getSbcVenta(),
                    sbcTO.getSbcConsumo() == null ? null : sbcTO.getSbcConsumo(),
                    sbcTO.getSbcTransferenciaI() == null ? null : sbcTO.getSbcTransferenciaI(),
                    sbcTO.getSbcTransferenciaE() == null ? null : sbcTO.getSbcTransferenciaE(),
                    sbcTO.getSbcDevolucionC() == null ? null : sbcTO.getSbcDevolucionC(),
                    sbcTO.getSbcDevolucionV() == null ? null : sbcTO.getSbcDevolucionV(),
                    sbcTO.getSbcFinal() == null ? null : sbcTO.getSbcFinal(),
                    sbcTO.getSbcDiferencia() == null ? null : sbcTO.getSbcDiferencia());
            lt.add(reporteSaldoBodega);
        }
        return genericReporteService.generarReporte(modulo, "reportSaldoBodegaComprobacionCantidades.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), lt);
    }

    @Override
    public byte[] generarReporteInvProductosConError(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductosConErrorTO> listInvProductosConErrorTO) throws Exception {
        List<ReporteReconstruccionSaldosCostos> lt = new ArrayList<ReporteReconstruccionSaldosCostos>();
        for (InvProductosConErrorTO pce : listInvProductosConErrorTO) {
            ReporteReconstruccionSaldosCostos reporteSaldoBodega = new ReporteReconstruccionSaldosCostos();
            reporteSaldoBodega.setErrBodega(pce.getErrBodega());
            reporteSaldoBodega.setErrProductoCodigo(pce.getErrProductoCodigo());
            reporteSaldoBodega.setErrProductoNombre(pce.getErrProductoNombre());
            reporteSaldoBodega.setErrCantidad(pce.getErrCantidad());
            reporteSaldoBodega.setErrDesde(pce.getErrDesde());
            reporteSaldoBodega.setErrHasta(pce.getErrHasta());
            lt.add(reporteSaldoBodega);
        }
        return genericReporteService.generarReporte(modulo, "reportListadoProductosConError.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listInvProductosConErrorTO);
    }

    @Override
    public File generarReporteInvProductosConErrorFile(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductosConErrorTO> listInvProductosConErrorTO) throws Exception {
        List<ReporteReconstruccionSaldosCostos> lt = new ArrayList<>();
        for (InvProductosConErrorTO pce : listInvProductosConErrorTO) {
            ReporteReconstruccionSaldosCostos reporteSaldoBodega = new ReporteReconstruccionSaldosCostos();
            reporteSaldoBodega.setErrBodega(pce.getErrBodega());
            reporteSaldoBodega.setErrProductoCodigo(pce.getErrProductoCodigo());
            reporteSaldoBodega.setErrProductoNombre(pce.getErrProductoNombre());
            reporteSaldoBodega.setErrCantidad(pce.getErrCantidad());
            reporteSaldoBodega.setErrDesde(pce.getErrDesde());
            reporteSaldoBodega.setErrHasta(pce.getErrHasta());
            lt.add(reporteSaldoBodega);
        }
        return genericReporteService.generarFile(modulo, "reportListadoProductosConError.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listInvProductosConErrorTO);
    }

    @Override
    public byte[] generarReporteTrasferencias(String empresa, String periodo, String motivo, String numero, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception {
        InvTransferenciasTO invTransferenciasTO = transferenciasService.getInvTransferenciasCabeceraTO(empresa, periodo, motivo, numero);
        List<InvListaDetalleTransferenciaTO> listaDetalleTransferenciaTO = transferenciasDetalleService.getListaInvTransferenciasDetalleTO(empresa, periodo, motivo, numero);
        List<ReporteTransferenciaDetalle> datosReporte = new ArrayList();

        for (int i = 0; i < listaDetalleTransferenciaTO.size(); i++) {
            ReporteTransferenciaDetalle reporteTransferenciaDetalle = new ReporteTransferenciaDetalle();

            reporteTransferenciaDetalle.setTransPeriodo(periodo);
            reporteTransferenciaDetalle.setTransMotivo(motivo);
            reporteTransferenciaDetalle.setTransNumero(numero);
            reporteTransferenciaDetalle.setTransEmpresa(usuarioEmpresaReporteTO.getEmpRuc());

            reporteTransferenciaDetalle.setTransFecha(invTransferenciasTO.getTransFecha());
            reporteTransferenciaDetalle.setTransObservacion(invTransferenciasTO.getTransObservaciones());
            reporteTransferenciaDetalle.setUsrFechaInsert(invTransferenciasTO.getUsrFechaInserta());
            reporteTransferenciaDetalle.setUsrCodigo(invTransferenciasTO.getUsrCodigo());

            reporteTransferenciaDetalle.setBodOrigenCodigo(listaDetalleTransferenciaTO.get(i).getBodOrigenCodigo());
            reporteTransferenciaDetalle.setBodDestinoCodigo(listaDetalleTransferenciaTO.get(i).getBodDestinoCodigo());
            reporteTransferenciaDetalle.setProCodigoPrincipal(listaDetalleTransferenciaTO.get(i).getProCodigoPrincipal());
            reporteTransferenciaDetalle.setProNombre(listaDetalleTransferenciaTO.get(i).getProNombre());
            reporteTransferenciaDetalle.setDetCantidad(listaDetalleTransferenciaTO.get(i).getDetCantidad());
            reporteTransferenciaDetalle.setProMedida(listaDetalleTransferenciaTO.get(i).getMedDetalle());
            reporteTransferenciaDetalle.setDetCostoPromedio(listaDetalleTransferenciaTO.get(i).getDetCostoPromedio());
            reporteTransferenciaDetalle.setDetPrecio1(listaDetalleTransferenciaTO.get(i).getDetPrecio1());
            reporteTransferenciaDetalle.setCantidadCaja(listaDetalleTransferenciaTO.get(i).getCantidadCaja());
            reporteTransferenciaDetalle.setEmpaque(listaDetalleTransferenciaTO.get(i).getEmpaque());
            reporteTransferenciaDetalle.setPresentacionUnidad(listaDetalleTransferenciaTO.get(i).getPresentacionUnidad());
            reporteTransferenciaDetalle.setPresentacionCaja(listaDetalleTransferenciaTO.get(i).getPresentacionCaja());

            datosReporte.add(reporteTransferenciaDetalle);
        }
        return genericReporteService.generarReporte(modulo, "reportComprobanteTrasferencias.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), datosReporte);
    }

    @Override
    public byte[] generarReporteTrasferenciasListado(String empresa, List<InvListaConsultaTransferenciaTO> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportTransferenciaListado.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReportePorLoteTrasferencias(String empresa, List<String> numeroTransferencia, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception {
        List<ReporteTransferenciaDetalle> datosReporte = new ArrayList();

        for (int t = 0; t < numeroTransferencia.size(); t++) {
            List<String> comprobante = UtilsValidacion.separarComprobante(numeroTransferencia.get(t));
            InvTransferenciasTO invTransferenciasTO = transferenciasService.getInvTransferenciasCabeceraTO(empresa, comprobante.get(0), comprobante.get(1), comprobante.get(2));
            List<InvListaDetalleTransferenciaTO> listaDetalleTransferenciaTO = transferenciasDetalleService.getListaInvTransferenciasDetalleTO(empresa, comprobante.get(0), comprobante.get(1), comprobante.get(2));

            for (int i = 0; i < listaDetalleTransferenciaTO.size(); i++) {
                ReporteTransferenciaDetalle reporteTransferenciaDetalle = new ReporteTransferenciaDetalle();

                reporteTransferenciaDetalle.setTransPeriodo(comprobante.get(0));
                reporteTransferenciaDetalle.setTransMotivo(comprobante.get(1));
                reporteTransferenciaDetalle.setTransNumero(comprobante.get(2));

                reporteTransferenciaDetalle.setTransFecha(invTransferenciasTO.getTransFecha());
                reporteTransferenciaDetalle.setTransObservacion(invTransferenciasTO.getTransObservaciones());
                reporteTransferenciaDetalle.setUsrFechaInsert(invTransferenciasTO.getUsrFechaInserta());
                reporteTransferenciaDetalle.setUsrCodigo(invTransferenciasTO.getUsrCodigo());

                reporteTransferenciaDetalle.setBodOrigenCodigo(listaDetalleTransferenciaTO.get(i).getBodOrigenCodigo());
                reporteTransferenciaDetalle.setBodDestinoCodigo(listaDetalleTransferenciaTO.get(i).getBodDestinoCodigo());
                reporteTransferenciaDetalle.setProCodigoPrincipal(listaDetalleTransferenciaTO.get(i).getProCodigoPrincipal());
                reporteTransferenciaDetalle.setProNombre(listaDetalleTransferenciaTO.get(i).getProNombre());
                reporteTransferenciaDetalle.setDetCantidad(listaDetalleTransferenciaTO.get(i).getDetCantidad());
                reporteTransferenciaDetalle.setProMedida(listaDetalleTransferenciaTO.get(i).getMedDetalle());
                reporteTransferenciaDetalle.setDetCostoPromedio(listaDetalleTransferenciaTO.get(i).getDetCostoPromedio());
                reporteTransferenciaDetalle.setDetPrecio1(listaDetalleTransferenciaTO.get(i).getDetPrecio1());
                reporteTransferenciaDetalle.setCantidadCaja(listaDetalleTransferenciaTO.get(i).getCantidadCaja());
                reporteTransferenciaDetalle.setEmpaque(listaDetalleTransferenciaTO.get(i).getEmpaque());
                reporteTransferenciaDetalle.setPresentacionUnidad(listaDetalleTransferenciaTO.get(i).getPresentacionUnidad());
                reporteTransferenciaDetalle.setPresentacionCaja(listaDetalleTransferenciaTO.get(i).getPresentacionCaja());

                datosReporte.add(reporteTransferenciaDetalle);
            }
        }

        return genericReporteService.generarReporte(modulo, "reportComprobanteTransferenciaPorLote.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), datosReporte);
    }

    @Override
    public byte[] generarReporteConsumoDetalle(List<InvConsumosTO> invConsumosTOs, boolean ordenado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception {
        String nombreReporte = "";
        List<ReporteConsumoDetalle> datosReporte = new ArrayList();

        for (int i = 0; i < invConsumosTOs.size(); i++) {
            String empresa = invConsumosTOs.get(i).getConsEmpresa();
            String periodo = invConsumosTOs.get(i).getConsPeriodo();
            String motivo = invConsumosTOs.get(i).getConsMotivo();
            String numero = invConsumosTOs.get(i).getConsNumero();
            InvConsumosTO invConsumosTO = new InvConsumosTO();
            invConsumosTO = consumosService.getInvConsumoCabeceraTO(empresa, periodo, motivo, numero);

            List<InvListaDetalleConsumoTO> listaDetalleConsumoTO = new ArrayList();
            listaDetalleConsumoTO = consumosDetalleService.getListaInvConsumoDetalleTO(empresa, periodo, motivo, numero);

            for (int j = 0; j < listaDetalleConsumoTO.size(); j++) {
                ReporteConsumoDetalle reporteConsumoDetalle = new ReporteConsumoDetalle();
                reporteConsumoDetalle.setConsEmpresa(empresa);
                reporteConsumoDetalle.setConsPeriodo(periodo);
                reporteConsumoDetalle.setConsMotivo(motivo);
                reporteConsumoDetalle.setConsNumero(numero);
                reporteConsumoDetalle.setConsFecha(invConsumosTO.getConsFecha());
                reporteConsumoDetalle.setConsObservaciones(invConsumosTO.getConsObservaciones());
                reporteConsumoDetalle.setConsReferencia(invConsumosTO.getConsReferencia());
                reporteConsumoDetalle.setUsrCodigo(invConsumosTO.getUsrCodigo());
                reporteConsumoDetalle.setUsrFechaInserta(invConsumosTO.getUsrFechaInserta());
                ///////////// DETALLE
                reporteConsumoDetalle.setCodigoBodega(listaDetalleConsumoTO.get(j).getCodigoBodega());
                reporteConsumoDetalle.setCodigoProducto(listaDetalleConsumoTO.get(j).getCodigoProducto());
                reporteConsumoDetalle.setNombreProducto(listaDetalleConsumoTO.get(j).getNombreProducto());
                reporteConsumoDetalle.setCodigoCP(listaDetalleConsumoTO.get(j).getCodigoCP());
                reporteConsumoDetalle.setCodigoCC(listaDetalleConsumoTO.get(j).getCodigoCC());
                reporteConsumoDetalle.setCantidadProducto(listaDetalleConsumoTO.get(j).getCantidadProducto());
                reporteConsumoDetalle.setCostoProducto(listaDetalleConsumoTO.get(j).getCostoPromedio());
                reporteConsumoDetalle.setMedidaDetalle(listaDetalleConsumoTO.get(j).getMedidaDetalle());
                //estados
                reporteConsumoDetalle.setConsPendiente(invConsumosTO.getConsPendiente());
                reporteConsumoDetalle.setConsAnulado(invConsumosTO.getConsAnulado());
                datosReporte.add(reporteConsumoDetalle);
            }
        }
        if (ordenado) {
            Collections.sort(datosReporte, new Comparator<ReporteConsumoDetalle>() {
                @Override
                public int compare(ReporteConsumoDetalle reporteConsumoDetalle,
                        ReporteConsumoDetalle reporteConsumoDetalle1) {
                    return reporteConsumoDetalle.getNombreProducto()
                            .compareTo(reporteConsumoDetalle1.getNombreProducto());
                }
            });
            Collections.sort(datosReporte, new Comparator<ReporteConsumoDetalle>() {

                @Override
                public int compare(ReporteConsumoDetalle reporteConsumoDetalle,
                        ReporteConsumoDetalle reporteConsumoDetalle1) {
                    return reporteConsumoDetalle.getConsNumero().compareTo(reporteConsumoDetalle1.getConsNumero());
                }
            });
            nombreReporte = "reportComprobanteConsumoOrdenado.jrxml";
        } else {
            nombreReporte = "reportComprobanteConsumo.jrxml";
        }

        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO,
                new HashMap<String, Object>(), datosReporte);
    }

    @Override
    public byte[] generarReporteCompraDetalle(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCompraDetalle> reporteCompraDetalles) throws Exception {
        List<ReporteCompraDetalle> reporteCompraDetalles2 = new ArrayList<>();
        String nombreReporte = "reportComprobanteCompra.jrxml";
        for (ReporteCompraDetalle rcd : reporteCompraDetalles) {
            rcd.setCompBase0(rcd.getCompBase0().setScale(2));
            rcd.setCompBaseimponible(rcd.getCompBaseimponible().setScale(2));
            rcd.setCompMontoiva(rcd.getCompMontoiva().setScale(2));
            rcd.setCompTotal(rcd.getCompTotal().setScale(2));
            reporteCompraDetalles2.add(rcd);
        }
        if (!reporteCompraDetalles.isEmpty() && reporteCompraDetalles.size() > 10) {
            nombreReporte = "reportComprobanteCompraExtendido.jrxml";
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteCompraDetalles2);
    }

    @Override
    public byte[] generarReporteCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvComprasPK> listaPk, boolean isIMB, String secNombre, String nombreReporte, String validarImpresionIMB) throws Exception {
        List<ReporteCompraDetalle> reporteCompraDetalles = new ArrayList<>();
        Boolean imprimirImb = false;
        Boolean imprimirImbConDetalles = false;
        validarImpresionIMB = validarImpresionIMB == null ? "NO" : validarImpresionIMB;
        for (InvComprasPK pk : listaPk) {
            InvCompraCabeceraTO invCompraCabeceraTO = comprasService.getInvCompraCabeceraTO(pk.getCompEmpresa(), pk.getCompPeriodo(), pk.getCompMotivo(), pk.getCompNumero());
            AnxCompraTO anxCompraTO = compraService.getAnexoCompraTO(pk.getCompEmpresa(), pk.getCompPeriodo(), pk.getCompMotivo(), pk.getCompNumero());
            InvProveedorTO proveedor = proveedorService.getProveedorTO(pk.getCompEmpresa(), invCompraCabeceraTO.getProvCodigo()).get(0);
            InvComprasMotivo motivo = comprasMotivoService.getInvComprasMotivo(usuarioEmpresaReporteTO.getEmpCodigo(), pk.getCompMotivo());
//            List<ReporteContableDetalle> detalleContable = reporteContabilidadService.generarReporteContableDetalleParaCompra(usuarioEmpresaReporteTO, new ConContablePK(usuarioEmpresaReporteTO.getEmpCodigo(), invCompraCabeceraTO.getConPeriodo(), invCompraCabeceraTO.getConTipo(), invCompraCabeceraTO.getConNumero()));
//            String nombreReportes = reporteContabilidadService.obtenerNombreReporteContable(detalleContable);
            /*Detalle*/
            imprimirImb = validarImpresionIMB.equals("SI") ? invCompraCabeceraTO.getCompEsImb() : false;
            String motivoDetalle = motivo.getCmDetalle().contains("-")
                    ? motivo.getCmDetalle().split("-")[1] : motivo.getCmDetalle();
            List<InvListaDetalleComprasTO> listaInvCompraDetalleTO = comprasDetalleService.getListaInvCompraDetalleTO(pk.getCompEmpresa(), pk.getCompPeriodo(), pk.getCompMotivo(), pk.getCompNumero());
            for (InvListaDetalleComprasTO detalle : listaInvCompraDetalleTO) {
                ReporteCompraDetalle reporte = new ReporteCompraDetalle();
                //Cabecera
                reporte.setEmpCodigo(pk.getCompEmpresa());
                reporte.setPerCodigo(pk.getCompPeriodo());
                reporte.setMotCodigo(pk.getCompMotivo());
                reporte.setCompEsImb(invCompraCabeceraTO.getCompEsImb());
                reporte.setMotDetalle(motivoDetalle);
                reporte.setCompNumero(pk.getCompNumero());
                reporte.setCompFecha(invCompraCabeceraTO.getCompfecha());
                reporte.setCompFechaVencimiento(invCompraCabeceraTO.getCompFechaVencimiento());
                reporte.setCompObservaciones(invCompraCabeceraTO.getCompObservaciones());
                reporte.setCompFormaPago(invCompraCabeceraTO.getCompFormaPago());
                reporte.setCompPropina(invCompraCabeceraTO.getCompPropina());
                reporte.setCompDocumentoNumero(invCompraCabeceraTO.getCompDocumentoNumero());
                reporte.setCompDocumentoTipo(invCompraCabeceraTO.getCompDocumentoTipo());
                reporte.setCompElectronica(invCompraCabeceraTO.getCompElectronica() != null ? invCompraCabeceraTO.getCompElectronica() : false);
                reporte.setUsrFechaInsertaCompra(invCompraCabeceraTO.getFechaUsuarioInserto());
                reporte.setUsrInsertaCompra(invCompraCabeceraTO.getUsuarioInserto());
                reporte.setOcMotivo(invCompraCabeceraTO.getOcMotivo());
                reporte.setOcNumero(invCompraCabeceraTO.getOcNumero());
                reporte.setOcSector(invCompraCabeceraTO.getOcSector());
                reporte.setOcOrdenPedido(invCompraCabeceraTO.getOcOrdenPedido());
                reporte.setCompRevisado(invCompraCabeceraTO.getCompRevisado() != null ? invCompraCabeceraTO.getCompRevisado() : false);
                reporte.setEsImb(isIMB);
                /* retencion*/
                if (anxCompraTO != null) {
                    reporte.setCompSustentoTributario(anxCompraTO.getCompSustentotributario());
                    reporte.setCompAutorizacion(anxCompraTO.getCompAutorizacion());
                    if (anxCompraTO.getCompSustentotributario() != null && !anxCompraTO.getCompSustentotributario().equals("")) {
                        AnxSustentoComboTO sustento = sustentoService.obtenerAnxSustentoComboTO(anxCompraTO.getCompSustentotributario());
                        reporte.setCompSustentoTributario(sustento != null ? sustento.getSusDescripcion() : "");
                    }
                }
                /*Proveedor*/

                reporte.setProvCodigo(invCompraCabeceraTO.getProvCodigo());
                reporte.setProvRazonSocial(proveedor.getProvRazonSocial());
                /*Detalle*/
 /*Bodega*/
                InvBodega bodega = bodegaService.buscarInvBodega(usuarioEmpresaReporteTO.getEmpCodigo(), detalle.getCodigoBodega());
                reporte.setBogDetalle(bodega.getBodNombre());
                reporte.setBodCodigo(detalle.getCodigoBodega());
                reporte.setProCodigoPrincipal(detalle.getCodigoProducto());
                reporte.setProNombre(detalle.getNombreProducto());
                PrdSector sector = sectorService.obtenerPorEmpresaSector(usuarioEmpresaReporteTO.getEmpCodigo(), detalle.getCodigoCP());
                reporte.setSecDetalle(sector.getSecNombre());
                reporte.setSecCodigo(detalle.getCodigoCP());
                reporte.setNombreSector(secNombre);
                reporte.setPisNumero(detalle.getCodigoCC());
                reporte.setDetCantidad(detalle.getCantidadProducto());
                reporte.setDetMedida(detalle.getMedidaDetalle());
                reporte.setDetPrecio(detalle.getPrecioProducto());
                reporte.setDetTotal(detalle.getPrecioProducto().multiply(detalle.getCantidadProducto()));
                reporte.setDetIva(detalle.getGravaIva().equals("GRAVA") ? true : false);

                reporte.setCompBase0(invCompraCabeceraTO.getCompBase0());
                reporte.setCompBaseimponible(invCompraCabeceraTO.getCompBaseimponible());
                reporte.setCompMontoiva(invCompraCabeceraTO.getCompMontoiva());
                reporte.setCompTotal(invCompraCabeceraTO.getCompTotal());
                reporte.setCompGuiaCompra(invCompraCabeceraTO.getCompGuiaCompra());

                //listado IMB
                List<InvListaDetalleComprasTO> listaInvComprasDetalleImbTO = new ArrayList<>();
                List<InvComprasDetalleImb> listaInvComprasDetalleImb = comprasDetalleImbService.getListInvComprasDetalleImb(pk.getCompEmpresa(), pk.getCompPeriodo(), pk.getCompMotivo(), pk.getCompNumero());

                if (listaInvComprasDetalleImb != null && listaInvComprasDetalleImb.size() > 0) {
                    imprimirImbConDetalles = usuarioEmpresaReporteTO.getEmpCodigo().equals("SA17") ? false : true;
                    for (int i = 0; i < listaInvComprasDetalleImb.size(); i++) {
                        List<InvListaDetalleComprasTO> detalleIMB = new ArrayList<>();
                        detalleIMB = comprasDetalleService.getListaInvCompraDetalleTO(listaInvComprasDetalleImb.get(i).getInvComprasImb().getInvComprasPK());
                        if (detalleIMB != null && detalleIMB.size() > 0) {
                            for (int j = 0; j < detalleIMB.size(); j++) {
                                InvListaDetalleComprasTO item = new InvListaDetalleComprasTO();
                                item.setCodigoProducto(detalleIMB.get(j).getCodigoProducto());
                                item.setNombreProducto(detalleIMB.get(j).getNombreProducto());
                                item.setCantidadProducto(detalleIMB.get(j).getCantidadProducto());
                                item.setMedidaDetalle(detalleIMB.get(j).getDetPresentacionUnidad());
                                item.setPrecioProducto(detalleIMB.get(j).getPrecioProducto());
                                item.setGravaIva(detalleIMB.get(j).getGravaIva());
                                item.setParcialProducto(detalleIMB.get(j).getParcialProducto());
                                listaInvComprasDetalleImbTO.add(item);
                            }
                        }
                    }
                }
                reporte.setListaInvListaDetalleComprasTO(listaInvComprasDetalleImbTO);

//                if (detalleContable != null && !detalleContable.isEmpty()) {
//                    reporte.setNombreSubreporte(nombreReportes);
//                    reporte.setContable(new JRBeanCollectionDataSource(detalleContable));
//                } else {
//                    reporte.setContable(null);
//                    reporte.setNombreSubreporte(null);
//                }
                reporteCompraDetalles.add(reporte);
            }

        }
        if (imprimirImb) {
            nombreReporte = "reportComprobanteCompraIMB.jrxml";
        } else {
            if (imprimirImbConDetalles) {
                nombreReporte = "reportComprobanteCompraIMBDetalles.jrxml";
            } else if (!reporteCompraDetalles.isEmpty() && reporteCompraDetalles.size() > 10) {
                nombreReporte = "reportComprobanteCompraExtendido.jrxml";
            }
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<>(), reporteCompraDetalles);
    }

    @Override
    public byte[] generarReporteVentaDetalleImpresion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteVentaDetalle> lista, String nombreCliente, String nombreReporte) throws Exception {
        if (usuarioEmpresaReporteTO.getEmpCodigo().equals("BC") || usuarioEmpresaReporteTO.getEmpCodigo().equals("QP20")) {
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getVtaDocumentoTipo().equals("00")) {
                    nombreReporte = "reportComprobanteNotaEntrega";
                }
            }
        }if(usuarioEmpresaReporteTO.getEmpCodigo().equals("AA")){
             for (int i = 0; i < lista.size(); i++) {
                 if (lista.get(i).getVtaDocumentoTipo().equals("18")) {
                    String secuencia = lista.get(i).getVtaDocumentoNumero().substring(0, 7);
                    nombreReporte = "reportComprobanteVentaFactura"+secuencia;
                }
            }
        }
        return genericReporteService.generarReporte(modulo, nombreReporte + ".jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteProformaDetalleImpresion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteProformaDetalle> lista, String nombreReporte) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportComprobanteProforma.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteCompraDetalleImprimir(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ReporteCompraDetalle> reporteCompraDetalles, String cmFormaImprimir) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportComprobanteCompra.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), reporteCompraDetalles);
    }

    @Override
    public byte[] generarReporteCliente(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvFunListadoClientesTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportCliente.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteInvFunVentasVsCosto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde,
            String fechaHasta, String bodega, String clienteId, List<InvFunVentasVsCostoTO> invFunVentasVsCostoTO)
            throws Exception {

        List<ReporteInvFunVentasVsCosto> lista = new ArrayList<ReporteInvFunVentasVsCosto>();
        for (InvFunVentasVsCostoTO sbto : invFunVentasVsCostoTO) {
            ReporteInvFunVentasVsCosto reporteInvFunVentasVsCosto = new ReporteInvFunVentasVsCosto();

            reporteInvFunVentasVsCosto.setFechaDesde(fechaDesde);
            reporteInvFunVentasVsCosto.setFechaHasta(fechaHasta);
            reporteInvFunVentasVsCosto.setBodega(bodega);
            reporteInvFunVentasVsCosto.setCliente(clienteId);
            reporteInvFunVentasVsCosto.setVcProducto(sbto.getVcProducto());
            reporteInvFunVentasVsCosto.setVcPorcentaje(BigDecimal.ZERO);
            reporteInvFunVentasVsCosto.setVcCodigo(sbto.getVcCodigo());
            reporteInvFunVentasVsCosto.setVcMedida(sbto.getVcMedida());
            reporteInvFunVentasVsCosto.setVcCantidad(sbto.getVcCantidad());
            reporteInvFunVentasVsCosto.setVcTotalVentas(sbto.getVcTotalVentas());
            reporteInvFunVentasVsCosto.setVcTotalCosto(sbto.getVcTotalCosto());
            reporteInvFunVentasVsCosto.setVcPorcentaje(sbto.getVcPorcentaje());

            lista.add(reporteInvFunVentasVsCosto);
        }
        return genericReporteService.generarReporte(modulo, "reportAnalisisVentasVsCostos.jrxml",
                usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteInvProductoMedidaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoMedidaTO> listaInvProductoMedidaTO, String nombreReporte) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaInvProductoMedidaTO);
    }

    @Override
    public byte[] generarReporteProductoMarca(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoMarcaComboListadoTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportProductoMarca.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReportePresentacionUnidad(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoPresentacionUnidadesComboListadoTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportPresentacionUnidad.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReportePresentacionCaja(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoPresentacionCajasComboListadoTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportPresentacionCaja.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteProductoTipo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoTipoComboTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportProductoTipo.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvListaConsultaCompraTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportCompras.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteProforma(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<InvProformasPK> listadoPk) throws Exception {

        List<ReporteProformaDetalle> lista = new ArrayList();
        for (InvProformasPK pk : listadoPk) {
            InvProformaCabeceraTO invProformaCabeceraTO = proformasService.getInvProformaCabeceraTO(pk.getProfEmpresa(), pk.getProfPeriodo(), pk.getProfMotivo(), pk.getProfNumero());
            List<InvListaDetalleProformasTO> listaDetalle = proformasDetalleService.getListaInvProformasDetalleTO(pk.getProfEmpresa(), pk.getProfPeriodo(), pk.getProfMotivo(), pk.getProfNumero());
            SisUsuario usuario = usuarioService.obtenerPorNick(usuarioEmpresaReporteTO.getUsrNick());
            ///////////// DETALLE
            for (InvListaDetalleProformasTO detalle : listaDetalle) {
                ReporteProformaDetalle reporteProformaDetalle = new ReporteProformaDetalle();
                reporteProformaDetalle.setEmpCodigo(pk.getProfEmpresa());
                reporteProformaDetalle.setMotCodigo(pk.getProfMotivo());
                reporteProformaDetalle.setProfNumero(pk.getProfNumero());
                reporteProformaDetalle.setPerCodigo(pk.getProfPeriodo());
                reporteProformaDetalle.setProfFecha(invProformaCabeceraTO.getProfFecha());
                reporteProformaDetalle.setProfIvaVigente(invProformaCabeceraTO.getProfIvaVigente());
                reporteProformaDetalle.setProfObservaciones(invProformaCabeceraTO.getProfObservaciones());
                reporteProformaDetalle.setProfPendiente(invProformaCabeceraTO.getProfPendiente());
                reporteProformaDetalle.setProfBase0(invProformaCabeceraTO.getProfBase0());
                reporteProformaDetalle.setProfBaseimponible(invProformaCabeceraTO.getProfBaseimponible());
                reporteProformaDetalle.setProfDescuentoBase0(invProformaCabeceraTO.getProfDescuentoBase0());
                reporteProformaDetalle.setProfDescuentoBaseImponible(invProformaCabeceraTO.getProfDescuentoBaseImponible());
                reporteProformaDetalle.setProfSubtotalBase0(invProformaCabeceraTO.getProfSubtotalBase0());
                reporteProformaDetalle.setProfSubtotalBaseImponible(invProformaCabeceraTO.getProfSubtotalBaseImponible());
                reporteProformaDetalle.setProfMontoiva(invProformaCabeceraTO.getProfMontoiva());
                reporteProformaDetalle.setProfTotal(invProformaCabeceraTO.getProfTotal());

                /*EMPLEADO*/
                reporteProformaDetalle.setEmpNombre(usuario.getUsrNombre() + " " + usuario.getUsrApellido());
                reporteProformaDetalle.setEmpTelefono(usuarioEmpresaReporteTO.getEmpTelefono());
                reporteProformaDetalle.setEmpDireccion(usuarioEmpresaReporteTO.getEmpDireccion());
                reporteProformaDetalle.setEmpEmail(usuarioEmpresaReporteTO.getEmpEmail());

                /*CLIENTE*/
                InvClienteTO invClienteTO = clienteService.obtenerClienteTO(pk.getProfEmpresa(), invProformaCabeceraTO.getCliCodigo());
                reporteProformaDetalle.setCliCodigo(invProformaCabeceraTO.getCliCodigo());
                reporteProformaDetalle.setCliNombre(invClienteTO.getCliRazonSocial());
                reporteProformaDetalle.setCliRuc(invClienteTO.getCliId());
                reporteProformaDetalle.setCliDireccion(invClienteTO.getCliDireccion());
                reporteProformaDetalle.setCliTelefono(invClienteTO.getCliTelefono());
                reporteProformaDetalle.setCliCiudad(invClienteTO.getCliCiudad());
                reporteProformaDetalle.setCliEmail(invClienteTO.getCliEmail());
                if (invClienteTO.getCliLugaresEntrega() != null && !invClienteTO.getCliLugaresEntrega().equals("")) {
                    String con = invClienteTO.getCliLugaresEntrega().replaceAll("\\[|\\]", "");
                    Map<String, Object> map = UtilsJSON.jsonToMap(con);
                    String contac = UtilsJSON.jsonToObjeto(String.class, map.get("contacto"));
                    reporteProformaDetalle.setContacto(contac);
                }
                reporteProformaDetalle.setUsrInsertaCompra(invProformaCabeceraTO.getUsuarioInserto());
                reporteProformaDetalle.setUsrFechaInsertaCompra(invProformaCabeceraTO.getFechaUsuarioInserto());

                reporteProformaDetalle.setDetPendiente(false);
                reporteProformaDetalle.setProCodigoPrincipal(detalle.getCodigoProducto());
                reporteProformaDetalle.setProNombre(detalle.getNombreProducto());
                reporteProformaDetalle.setDetCantidad(detalle.getCantidadProducto());
                reporteProformaDetalle.setDetMedida(detalle.getMedidaDetalle());
                reporteProformaDetalle.setDetPrecio(detalle.getPrecioProducto());
                reporteProformaDetalle.setDetParcial(detalle.getParcialProducto());
                reporteProformaDetalle.setDetPorcentajeRecargo(detalle.getPorcentajeRecargo());
                reporteProformaDetalle.setDetPorcentajeDescuento(detalle.getPorcentajeDescuento());
                reporteProformaDetalle.setDetTotal(detalle.getDetalleTotal());
                reporteProformaDetalle.setDetIva(detalle.getGravaIva());
                reporteProformaDetalle.setDetCantidadCaja(detalle.getDetCantidadCaja());
                reporteProformaDetalle.setDetEmpaque(detalle.getDetEmpaque());
                reporteProformaDetalle.setDetPresentacionUnidad(detalle.getDetPresentacionUnidad());
                reporteProformaDetalle.setDetPresentacionCaja(detalle.getDetPresentacionCaja());
                reporteProformaDetalle.setProfRecargoBase0(BigDecimal.ZERO);
                reporteProformaDetalle.setProfRecargoImponible(BigDecimal.ZERO);
                lista.add(reporteProformaDetalle);
            }

        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);
    }

    @Override
    public byte[] generarReporteProformaListado(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<InvListaConsultaProformaTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<InvListaConsultaVentaTO> listInvListaConsultaVentaTO) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listInvListaConsultaVentaTO);
    }

    @Override
    public byte[] generarReporteVentasDetalleProducto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<InvVentasDetalleProductoTO> listaVentasDetalleProducto) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportVentaDetalleProducto.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaVentasDetalleProducto);
    }

    @Override
    public byte[] generarReporteComprasDetalleProducto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreReporte, List<InvComprasDetalleProductoTO> listaComprasDetalleProducto) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportCompraDetalleProducto.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaComprasDetalleProducto);
    }

    @Override
    public byte[] generarReporteVendedor(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvVendedorComboListadoTO> listado) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportVendedor.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteInvGuiaRemision(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvGuiaRemision> listado, String nombreReporte) throws Exception {
        List<ReporteInvGuiaRemision> lista = new ArrayList<ReporteInvGuiaRemision>();
        if (nombreReporte == null) {
            nombreReporte = "reportComprobanteGuiaRemision.jrxml";
        }
        for (InvGuiaRemision guiaRemision : listado) {
            InvGuiaRemisionPK pk = guiaRemision.getInvGuiaRemisionPK();
            List<InvGuiaRemisionDetalle> listaDetalle = guiaRemisionService.obtenerGuiaRemisionDetalle(pk.getGuiaEmpresa(), pk.getGuiaPeriodo(), pk.getGuiaNumero());
            for (InvGuiaRemisionDetalle detalle : listaDetalle) {
                ReporteInvGuiaRemision reporte = new ReporteInvGuiaRemision();
                reporte.setTipoDocumento(guiaRemision.getTipoDocumento());
                reporte.setNroDocumento(guiaRemision.getNroDocumento());
                reporte.setNroAutorizacion(guiaRemision.getNroAutorizacion());
                if (guiaRemision.getGuiaRemisionRuta() != null) {
                    List<InvGuiaRutas> rutaPrecio = guiaRutasService.obtenerRuta(pk.getGuiaEmpresa(), guiaRemision.getGuiaRemisionRuta());
                    reporte.setGuiaRutaPrecio(rutaPrecio.get(0).getGuiaValor());
                }
                reporte.setGuiaRuta(guiaRemision.getGuiaRuta());
                reporte.setGuiaPuntoPartida(guiaRemision.getGuiaPuntoPartida());
                reporte.setGuiaPuntoLlegada(guiaRemision.getGuiaPuntoLlegada());
                reporte.setGuiaPlaca(guiaRemision.getGuiaPlaca());
                reporte.setGuiaMotivoTraslado(guiaRemision.getGuiaMotivoTraslado());
                reporte.setGuiaFechaInicioTransporte(UtilsDate.fechaFormatoString(guiaRemision.getGuiaFechaInicioTransporte(), "yyyy-MM-dd"));
                reporte.setGuiaFechaFinTransporte(UtilsDate.fechaFormatoString(guiaRemision.getGuiaFechaFinTransporte(), "yyyy-MM-dd"));
                reporte.setGuiaFechaEmision(UtilsDate.fechaFormatoString(guiaRemision.getGuiaFechaEmision(), "yyyy-MM-dd"));
                reporte.setGuiaDocumentoNumero(guiaRemision.getGuiaDocumentoNumero());
                reporte.setGuiaDocumentoAduanero(guiaRemision.getGuiaDocumentoAduanero());
                reporte.setGuiaCodigoEstablecimiento(guiaRemision.getGuiaCodigoEstablecimiento());

                //transportista
                reporte.setIdentificacionTransportista(guiaRemision.getInvTransportista().getTransIdNumero());
                reporte.setNombresTransportista(guiaRemision.getInvTransportista().getTransNombres());

                //destinatario
                reporte.setIdentificacionDestinatario(guiaRemision.getInvCliente().getCliIdNumero());
                reporte.setNombresDestinatario(guiaRemision.getInvCliente().getCliRazonSocial());

                reporte.setProCodigoPrincipal(detalle.getInvProducto().getInvProductoPK().getProCodigoPrincipal());
                reporte.setNombreProducto(detalle.getInvProducto().getProNombre());
                reporte.setDetCantidad(detalle.getDetCantidad());

                reporte.setGuiaTipoMovil(guiaRemision.getGuiaTipoMovil());
                reporte.setGuiaRecibidor(guiaRemision.getGuiaRecibidor());
                reporte.setGuiaSello(guiaRemision.getGuiaSello());

                reporte.setGuiaGramos(guiaRemision.getGuiaGramos());
                reporte.setGuiaLibras(guiaRemision.getGuiaLibras());
                reporte.setGuiaHora(guiaRemision.getGuiaHora());

                reporte.setGuiaNumero(guiaRemision.getInvGuiaRemisionPK().getGuiaNumero());
                reporte.setGuiaInp(guiaRemision.getGuiaInp());

                lista.add(reporte);
            }
        }

        if (nombreReporte.equals("reportComprobanteGuiaRemisionLote.jrxml")) {
            int con = 1;
            int indice = 0;
            for (int i = 0; i <= lista.size(); i++) {
                if (con >= lista.size()) {
                    break;
                }
                if (lista.get(indice).getGuiaDocumentoNumero() == lista.get(con).getGuiaDocumentoNumero()) {
                    lista.remove(con);
                    i = 0;
                } else {
                    indice++;
                    con++;
                }
            }
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), lista);

    }

    @Override
    public Map<String, Object> exportarReporteInvKardex(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String nombreProducto, String fechaDesde, String fechaHasta, List<InvKardexTO> listInvKardexTO, boolean banderaCosto) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SKardex");
            listaCabecera.add("SProducto: " + nombreProducto);
            listaCabecera.add("SDesde: " + fechaDesde + " Hasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SEstado" + "¬" + "STransaccion" + "¬" + "SFecha" + "¬" + "SEntradas Cantidad" + "¬"
                    + (banderaCosto ? ("SEntradas Costo" + "¬") : ("")) + "SSalidas Cantidad" + "¬"
                    + (banderaCosto ? ("SSalidas Costo" + "¬") : ("")) + "SSaldos Cantidad" + "¬"
                    + (banderaCosto ? ("SSaldos Costo" + "¬") : (""))
                    + (banderaCosto ? ("SCosto Actual" + "¬") : (""))
                    + "SCP | CC" + "¬" + "SNúmero Sistema" + "¬" + "SDocumento Número" + "¬" + "SProveedor" + "¬"
                    + "SObservaciones");
            for (InvKardexTO invKardexTO : listInvKardexTO) {
                listaCuerpo
                        .add((invKardexTO.getkStatus() == null ? "B" : "S" + invKardexTO.getkStatus()) + "¬"
                                + (invKardexTO.getkTransaccion() == null ? "B" : "S" + invKardexTO.getkTransaccion()) + "¬"
                                + (invKardexTO.getkFecha() == null ? "B" : "S" + invKardexTO.getkFecha())
                                + "¬"
                                + (invKardexTO.getkEntradasCantidad() == null ? "B" : "D" + invKardexTO.getkEntradasCantidad().toString())
                                + "¬"
                                + (banderaCosto ? ((invKardexTO.getkEntradasCosto() == null ? "B" : "D" + invKardexTO.getkEntradasCosto().toString()) + "¬") : "")
                                + (invKardexTO.getkSalidasCantidad() == null ? "B" : "D" + invKardexTO.getkSalidasCantidad().toString())
                                + "¬"
                                + (banderaCosto ? ((invKardexTO.getkSalidasCosto() == null ? "B" : "D" + invKardexTO.getkSalidasCosto().toString()) + "¬") : "")
                                + (invKardexTO.getkSaldosCantidad() == null ? "B" : "D" + invKardexTO.getkSaldosCantidad().toString())
                                + "¬"
                                + (banderaCosto ? ((invKardexTO.getkSaldosCosto() == null ? "B" : "D" + invKardexTO.getkSaldosCosto().toString()) + "¬") : "")
                                + (banderaCosto ? ((invKardexTO.getkCostoActual() == null ? "B" : "D" + invKardexTO.getkCostoActual().toString()) + "¬") : "")
                                + (invKardexTO.getkSectorPiscina() == null ? "B" : "S" + invKardexTO.getkSectorPiscina())
                                + "¬"
                                + (invKardexTO.getkNumeroSistema() == null ? "B" : "S" + invKardexTO.getkNumeroSistema())
                                + "¬"
                                + (invKardexTO.getkDocumentoNumero() == null ? "B" : "S" + invKardexTO.getkDocumentoNumero())
                                + "¬" + (invKardexTO.getkObservaciones() == null ? "B" : "S" + invKardexTO.getkObservaciones()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteBodegas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvListaBodegasTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de bodegas");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SNombre" + "¬" + "SCP");
            for (InvListaBodegasTO invListaBodegasTO : listado) {
                listaCuerpo.add((invListaBodegasTO.getBodCodigo() == null ? "B"
                        : "S" + invListaBodegasTO.getBodCodigo())
                        + "¬"
                        + (invListaBodegasTO.getBodNombre() == null ? "B"
                        : "S" + invListaBodegasTO.getBodNombre())
                        + "¬" + (invListaBodegasTO.getCodigoCP() == null ? "B"
                        : "S" + invListaBodegasTO.getCodigoCP()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteCategoriaCliente(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvClienteCategoriaTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de categoría cliente");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SNombre");
            for (InvClienteCategoriaTO invClienteCategoriaTO : listado) {
                listaCuerpo.add(
                        (invClienteCategoriaTO.getCcCodigo() == null ? "B" : "S" + invClienteCategoriaTO.getCcCodigo()) + "¬"
                        + (invClienteCategoriaTO.getCcDetalle() == null ? "B" : "S" + invClienteCategoriaTO.getCcDetalle())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteCategoriaProveedor(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProveedorCategoriaTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de categoría proveedor");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SNombre" + "¬" + "SAplica retención IVA");
            for (InvProveedorCategoriaTO invProveedorCategoriaTO : listado) {
                listaCuerpo.add(
                        (invProveedorCategoriaTO.getPcCodigo() == null ? "B" : "S" + invProveedorCategoriaTO.getPcCodigo()) + "¬"
                        + (invProveedorCategoriaTO.getPcDetalle() == null ? "B" : "S" + invProveedorCategoriaTO.getPcDetalle()) + "¬"
                        + (invProveedorCategoriaTO.getPcAplicaRetencionIva() == true ? "SSí" : "SNo")
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteFormaPago(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvComprasFormaPagoTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de forma de pago");
            listaCabecera.add("S");
            listaCuerpo.add("SCuenta" + "¬" + "SDetalle" + "¬" + "STipo" + "¬" + "SInactivo");
            for (InvComprasFormaPagoTO invComprasFormaPagoTO : listado) {
                listaCuerpo.add(
                        (invComprasFormaPagoTO.getCtaCodigo() == null ? "B" : "S" + invComprasFormaPagoTO.getCtaCodigo()) + "¬"
                        + (invComprasFormaPagoTO.getFpDetalle() == null ? "B" : "S" + invComprasFormaPagoTO.getFpDetalle()) + "¬"
                        + (invComprasFormaPagoTO.getFpTipoPrincipal() == null ? "B" : "S" + invComprasFormaPagoTO.getFpTipoPrincipal()) + "¬"
                        + (invComprasFormaPagoTO.getFpInactivo() == true ? "SSí" : "SNo")
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteFormaCobro(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvVentasFormaCobroTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de forma de cobro");
            listaCabecera.add("S");
            listaCuerpo.add("SCuenta" + "¬" + "SDetalle" + "¬" + "STipo" + "¬" + "SInactivo");
            for (InvVentasFormaCobroTO invVentasFormaCobroTO : listado) {
                listaCuerpo.add(
                        (invVentasFormaCobroTO.getCtaCodigo() == null ? "B" : "S" + invVentasFormaCobroTO.getCtaCodigo()) + "¬"
                        + (invVentasFormaCobroTO.getFcDetalle() == null ? "B" : "S" + invVentasFormaCobroTO.getFcDetalle()) + "¬"
                        + (invVentasFormaCobroTO.getFcTipoPrincipal() == null ? "B" : "S" + invVentasFormaCobroTO.getFcTipoPrincipal()) + "¬"
                        + (invVentasFormaCobroTO.getFcInactivo() == true ? "SSí" : "SNo")
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteMotivoCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvComprasMotivoTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de motivo de compra");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SDetalle" + "¬" + "STipo contable" + "¬" + "SForma contabilizar" + "¬" + "SForma imprimir" + "¬" + "SAjuste inventario" + "¬" + "SInactivo");
            for (InvComprasMotivoTO invComprasMotivoTO : listado) {
                listaCuerpo.add(
                        (invComprasMotivoTO.getCmCodigo() == null ? "B" : "S" + invComprasMotivoTO.getCmCodigo()) + "¬"
                        + (invComprasMotivoTO.getCmDetalle() == null ? "B" : "S" + invComprasMotivoTO.getCmDetalle()) + "¬"
                        + (invComprasMotivoTO.getTipCodigo() == null ? "B" : "S" + invComprasMotivoTO.getTipCodigo()) + "¬"
                        + (invComprasMotivoTO.getCmFormaContabilizar() == null ? "B" : "S" + invComprasMotivoTO.getCmFormaContabilizar()) + "¬"
                        + (invComprasMotivoTO.getCmFormaImprimir() == null ? "B" : "S" + invComprasMotivoTO.getCmFormaImprimir()) + "¬"
                        + (invComprasMotivoTO.getCmAjustesDeInventario() == true ? "SSí" : "SNo") + "¬"
                        + (invComprasMotivoTO.getCmInactivo() == true ? "SINACTIVO" : "SACTIVO")
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteMotivoVenta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvVentaMotivoTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de motivo de venta");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SDetalle" + "¬" + "STipo contable" + "¬" + "STipo documento" + "¬" + "SForma contabilizar" + "¬" + "SForma imprimir" + "¬" + "SInactivo");
            for (InvVentaMotivoTO invVentaMotivoTO : listado) {
                listaCuerpo.add(
                        (invVentaMotivoTO.getVmCodigo() == null ? "B" : "S" + invVentaMotivoTO.getVmCodigo()) + "¬"
                        + (invVentaMotivoTO.getVmDetalle() == null ? "B" : "S" + invVentaMotivoTO.getVmDetalle()) + "¬"
                        + (invVentaMotivoTO.getTipCodigo() == null ? "B" : "S" + invVentaMotivoTO.getTipCodigo()) + "¬"
                        + (invVentaMotivoTO.getTcCodigo() == null ? "B" : "S" + invVentaMotivoTO.getTcCodigo()) + "¬"
                        + (invVentaMotivoTO.getVmFormaContabilizar() == null ? "B" : "S" + invVentaMotivoTO.getVmFormaContabilizar()) + "¬"
                        + (invVentaMotivoTO.getVmFormaImprimir() == null ? "B" : "S" + invVentaMotivoTO.getVmFormaImprimir()) + "¬"
                        + (invVentaMotivoTO.getVmInactivo() == true ? "SINACTIVO" : "SACTIVO")
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteMotivoConsumo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvConsumosMotivoTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de motivo de consumo");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SDetalle" + "¬" + "SForma contabilizar" + "¬" + "SExigir cliente" + "¬" + "SExigir proveedor" + "¬" + "SExigir trabajador" + "¬" + "SInactivo");
            listado.forEach((invConsumosMotivoTO) -> {
                listaCuerpo.add(
                        (invConsumosMotivoTO.getCmCodigo() == null ? "B" : "S" + invConsumosMotivoTO.getCmCodigo()) + "¬"
                        + (invConsumosMotivoTO.getCmDetalle() == null ? "B" : "S" + invConsumosMotivoTO.getCmDetalle()) + "¬"
                        + (invConsumosMotivoTO.getCmFormaContabilizar() == null ? "B" : "S" + invConsumosMotivoTO.getCmFormaContabilizar()) + "¬"
                        + (invConsumosMotivoTO.getCmExigirCliente() == true ? "SSI" : "SNO") + "¬"
                        + (invConsumosMotivoTO.getCmExigirProveedor() == true ? "SSI" : "SNO") + "¬"
                        + (invConsumosMotivoTO.getCmExigirTrabajador() == true ? "SSI" : "SNO") + "¬"
                        + (invConsumosMotivoTO.getCmInactivo() == true ? "SINACTIVO" : "SACTIVO")
                );
            });
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteMotivoProforma(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProformaMotivoTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de motivo de proforma");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SDetalle" + "¬" + "SInactivo");
            for (InvProformaMotivoTO invProformaMotivoTO : listado) {
                listaCuerpo.add(
                        (invProformaMotivoTO.getpmCodigo() == null ? "B" : "S" + invProformaMotivoTO.getpmCodigo()) + "¬"
                        + (invProformaMotivoTO.getpmDetalle() == null ? "B" : "S" + invProformaMotivoTO.getpmDetalle()) + "¬"
                        + (invProformaMotivoTO.getpmInactivo() == true ? "SINACTIVO" : "SACTIVO")
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteMotivoTransferencia(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvTransferenciaMotivoTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de motivo de transferencia");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SDetalle" + "¬" + "SInactivo");
            for (InvTransferenciaMotivoTO invTransferenciaMotivoTO : listado) {
                listaCuerpo.add(
                        (invTransferenciaMotivoTO.getTmCodigo() == null ? "B" : "S" + invTransferenciaMotivoTO.getTmCodigo()) + "¬"
                        + (invTransferenciaMotivoTO.getTmDetalle() == null ? "B" : "S" + invTransferenciaMotivoTO.getTmDetalle()) + "¬"
                        + (invTransferenciaMotivoTO.getTmInactivo() == true ? "SINACTIVO" : "SACTIVO")
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteNumeracionCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvNumeracionCompraTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de numeración de compra");
            listaCabecera.add("S");
            listaCuerpo.add("SSecuencia" + "¬" + "SPeríodo" + "¬" + "SMotivo");
            for (InvNumeracionCompraTO invNumeracionCompraTO : listado) {
                listaCuerpo.add(
                        (invNumeracionCompraTO.getNumSecuencia() == null ? "B" : "S" + invNumeracionCompraTO.getNumSecuencia()) + "¬"
                        + (invNumeracionCompraTO.getNumPeriodo() == null ? "B" : "S" + invNumeracionCompraTO.getNumPeriodo()) + "¬"
                        + (invNumeracionCompraTO.getNumMotivo() == null ? "B" : "S" + invNumeracionCompraTO.getNumMotivo()) + "¬"
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteNumeracionVenta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvNumeracionVentaTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de numeración de venta");
            listaCabecera.add("S");
            listaCuerpo.add("SSecuencia" + "¬" + "SPeríodo" + "¬" + "SMotivo");
            for (InvNumeracionVentaTO invNumeracionVentaTO : listado) {
                listaCuerpo.add(
                        (invNumeracionVentaTO.getNumSecuencia() == null ? "B" : "S" + invNumeracionVentaTO.getNumSecuencia()) + "¬"
                        + (invNumeracionVentaTO.getNumPeriodo() == null ? "B" : "S" + invNumeracionVentaTO.getNumPeriodo()) + "¬"
                        + (invNumeracionVentaTO.getNumMotivo() == null ? "B" : "S" + invNumeracionVentaTO.getNumMotivo()) + "¬"
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteNumeracionConsumo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvNumeracionConsumoTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de numeración de consumo");
            listaCabecera.add("S");
            listaCuerpo.add("SSecuencia" + "¬" + "SPeríodo" + "¬" + "SMotivo");
            for (InvNumeracionConsumoTO invNumeracionConsumoTO : listado) {
                listaCuerpo.add(
                        (invNumeracionConsumoTO.getNumSecuencia() == null ? "B" : "S" + invNumeracionConsumoTO.getNumSecuencia()) + "¬"
                        + (invNumeracionConsumoTO.getNumPeriodo() == null ? "B" : "S" + invNumeracionConsumoTO.getNumPeriodo()) + "¬"
                        + (invNumeracionConsumoTO.getNumMotivo() == null ? "B" : "S" + invNumeracionConsumoTO.getNumMotivo()) + "¬"
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteSaldoBodega(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<SaldoBodegaTO> listado, String fechaHasta) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String encabezado = usuarioEmpresaReporteTO.getEmpCodigo().equals("OCE") ? "SCajetas" : "SFactor de conversión";
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Saldo Bodega");
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SBodega" + "¬" + "SProducto" + "¬" + "SNombre" + "¬" + "SMedida" + "¬" + encabezado + "¬" + "SProducto cuenta" + "¬" + "SStock" + "¬" + "SCosto" + "¬" + "STotal");

            listado.forEach((saldoBodegaTO) -> {
                if (saldoBodegaTO.getSbSerie() == null || saldoBodegaTO.getSbSerie().equals("")) {
                    listaCuerpo.add((saldoBodegaTO.getSbBodega() == null ? "B" : "S" + saldoBodegaTO.getSbBodega())
                            + "¬" + (saldoBodegaTO.getSbProducto() == null ? "B" : "S" + saldoBodegaTO.getSbProducto())
                            + "¬" + (saldoBodegaTO.getSbNombre() == null ? "B" : "S" + saldoBodegaTO.getSbNombre())
                            + "¬" + (saldoBodegaTO.getSbMedida() == null ? "B" : "S" + saldoBodegaTO.getSbMedida())
                            + "¬" + (saldoBodegaTO.getSbCajetas() == null ? "B" : "D" + saldoBodegaTO.getSbCajetas())
                            + "¬" + (saldoBodegaTO.getSbProductoCuenta() == null ? "B" : "S" + saldoBodegaTO.getSbProductoCuenta())
                            + "¬" + (saldoBodegaTO.getSbStock() == null ? "B" : "D" + saldoBodegaTO.getSbStock().toString())
                            + "¬" + (saldoBodegaTO.getSbCosto() == null ? "B" : "D" + saldoBodegaTO.getSbCosto().toString())
                            + "¬" + (saldoBodegaTO.getSbTotal() == null ? "B" : "D" + saldoBodegaTO.getSbTotal().toString()));
                    if (saldoBodegaTO.isSbExigirSerie() != null && saldoBodegaTO.isSbExigirSerie()) {
                        List<SaldoBodegaTO> series = new ArrayList<>();
                        listado.stream().map(detalle
                                -> detalle.getSbProducto() != null
                                && detalle.getSbSerie() != null
                                && !detalle.getSbSerie().equals("")
                                && saldoBodegaTO.getSbProducto() != null
                                && detalle.getSbProducto().equals(saldoBodegaTO.getSbProducto())
                                ? series.add(detalle) : null).collect(Collectors.toList());
                        listaCuerpo.add("S" + "¬" + "S" + "¬" + "RSERIES");
                        series.forEach((serie) -> {
                            listaCuerpo.add("S"
                                    + "¬" + "B"
                                    + "¬" + (serie.getSbSerie() == null ? "B" : "S" + serie.getSbSerie()));
                        });
                        listaCuerpo.add("S");
                    }
                }
            });

            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteCliente(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvFunListadoClientesTO> listado, List<DetalleExportarFiltrado> listadoFiltrado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            String fila;
            String filaCabecera = "";
            boolean vtaRecurrentecheck = false;
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de clientes");
            listaCabecera.add("S");
            for (DetalleExportarFiltrado cabecera : listadoFiltrado) {
                if (cabecera.getField().equals("vtaRecurrente") && cabecera.isCheck()) {
                    vtaRecurrentecheck = true;
                }
                filaCabecera += cabecera.isCheck() && !cabecera.getField().equals("vtaRecurrente") ? "S" + cabecera.getHeaderName() + "¬" : "";
            }
            if (vtaRecurrentecheck) {
                filaCabecera = filaCabecera + "¬" + "S==>" + "¬" + "SProducto" + "¬" + "SNombre" + "¬" + "SCantidad" + "¬" + "SPrecio" + "¬" + "SGrupo";
            }
            listaCuerpo.add(filaCabecera);
            for (InvFunListadoClientesTO invFunListadoClientesTO : listado) {//Filas
                fila = completarDatosDetalleClientesParaExcel(listadoFiltrado, invFunListadoClientesTO);

                if (vtaRecurrentecheck) {
                    List<InvClientesVentasDetalle> detalles = clientesVentasDetalleDao.listarInvClientesVentasDetalle(usuarioEmpresaReporteTO.getEmpCodigo(), invFunListadoClientesTO.getCliCodigo(), 0);
                    if (detalles != null && !detalles.isEmpty()) {
                        for (InvClientesVentasDetalle detalle : detalles) {
                            String filaRecurrente = completarDatosDetalleClientesParaExcel(listadoFiltrado, invFunListadoClientesTO);
                            listaCuerpo.add(
                                    filaRecurrente + "S==>" + "¬"
                                    + (detalle.getInvProducto().getInvProductoPK().getProCodigoPrincipal() == null ? "B" : "S" + detalle.getInvProducto().getInvProductoPK().getProCodigoPrincipal()) + "¬"
                                    + (detalle.getProNombre() == null ? "B" : "S" + detalle.getProNombre()) + "¬"
                                    + (detalle.getDetCantidad() == null ? "B" : "D" + detalle.getDetCantidad().toString()) + "¬"
                                    + (detalle.getDetPrecio() == null ? "B" : "D" + detalle.getDetPrecio().toString()) + "¬"
                                    + (detalle.getDetGrupo() <= 0 ? "B" : "S" + detalle.getDetGrupo())
                            );
                        }
                    } else {
                        listaCuerpo.add(fila);
                    }
                } else {
                    listaCuerpo.add(fila);
                }
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    public String completarDatosDetalleClientesParaExcel(List<DetalleExportarFiltrado> listadoFiltrado, InvFunListadoClientesTO invFunListadoClientesTO) {
        String fila = "";
        for (int i = 0; i < listadoFiltrado.size(); i++) {//Columnas
            if (listadoFiltrado.get(i).isCheck()) {
                switch (listadoFiltrado.get(i).getField()) {
                    case "cliCodigo":
                        fila += (invFunListadoClientesTO.getCliCodigo() == null ? "B" : "S" + invFunListadoClientesTO.getCliCodigo()) + "¬";
                        break;
                    case "cliId":
                        fila += (invFunListadoClientesTO.getCliId() == null ? "B" : "S" + invFunListadoClientesTO.getCliId()) + "¬";
                        break;
                    case "cliTipoId":
                        fila += (invFunListadoClientesTO.getCliTipoId() == null ? "B" : "S" + invFunListadoClientesTO.getCliTipoId()) + "¬";
                        break;
                    case "cliNombre":
                        fila += (invFunListadoClientesTO.getCliNombreComercial() == null ? "B" : "S" + invFunListadoClientesTO.getCliNombreComercial()) + "¬";
                        break;
                    case "cliRazonSocial":
                        fila += (invFunListadoClientesTO.getCliRazonSocial() == null ? "B" : "S" + invFunListadoClientesTO.getCliRazonSocial()) + "¬";
                        break;
                    case "cliGrupoEmpresarialNombre":
                        fila += (invFunListadoClientesTO.getCliGrupoEmpresarialNombre() == null ? "B" : "S" + invFunListadoClientesTO.getCliGrupoEmpresarialNombre()) + "¬";
                        break;
                    case "cliCategoria":
                        fila += (invFunListadoClientesTO.getCliCategoria() == null ? "B" : "S" + invFunListadoClientesTO.getCliCategoria()) + "¬";
                        break;
                    case "cliProvincia":
                        fila += (invFunListadoClientesTO.getCliProvincia() == null ? "B" : "S" + invFunListadoClientesTO.getCliProvincia()) + "¬";
                        break;
                    case "cliCiudad":
                        fila += (invFunListadoClientesTO.getCliCiudad() == null ? "B" : "S" + invFunListadoClientesTO.getCliCiudad()) + "¬";
                        break;
                    case "cliZona":
                        fila += (invFunListadoClientesTO.getCliZona() == null ? "B" : "S" + invFunListadoClientesTO.getCliZona()) + "¬";
                        break;
                    case "cliDireccion":
                        fila += (invFunListadoClientesTO.getCliDireccion() == null ? "B" : "S" + invFunListadoClientesTO.getCliDireccion()) + "¬";
                        break;
                    case "cliTelefono":
                        fila += (invFunListadoClientesTO.getCliTelefono() == null ? "B" : "S" + invFunListadoClientesTO.getCliTelefono()) + "¬";
                        break;
                    case "cliCelular":
                        fila += (invFunListadoClientesTO.getCliCelular() == null ? "B" : "S" + invFunListadoClientesTO.getCliCelular()) + "¬";
                        break;
                    case "cliEmail":
                        fila += (invFunListadoClientesTO.getCliEmail() == null ? "B" : "S" + invFunListadoClientesTO.getCliEmail()) + "¬";
                        break;
                    case "cliObservaciones":
                        fila += (invFunListadoClientesTO.getCliObservaciones() == null ? "B" : "S" + invFunListadoClientesTO.getCliObservaciones()) + "¬";
                        break;
                    case "cliInactivo":
                        fila += (invFunListadoClientesTO.getCliInactivo() == null ? "SINACTIVO" : "SACTIVO") + "¬";
                        break;
                    case "cliDiasCredito":
                        fila += (invFunListadoClientesTO.getCliDiasCredito() == null ? "B" : "I" + invFunListadoClientesTO.getCliDiasCredito()) + "¬";
                        break;
                    case "cliVendedor":
                        fila += (invFunListadoClientesTO.getCliVendedor() == null ? "B" : "S" + invFunListadoClientesTO.getCliVendedor()) + "¬";
                        break;
                    case "cliCupoCredito":
                        fila += (invFunListadoClientesTO.getCliCupoCredito() == null ? "B" : "I" + invFunListadoClientesTO.getCliCupoCredito()) + "¬";
                        break;
                    case "cliDescripcion":
                        if (invFunListadoClientesTO.getCliDescripcion() != null) {
                            Object[] lista = UtilsJSON.jsonToObjeto(Object[].class, invFunListadoClientesTO.getCliDescripcion());
                            if (lista != null) {
                                for (Object item : lista) {
                                    Map<String, Object> map = UtilsJSON.jsonToObjeto(Map.class, item);
                                    String valor = UtilsJSON.jsonToObjeto(String.class, map.get("valor"));
                                    fila += (valor == null ? "B" : "S" + valor + " | ");
                                }
                            } else {
                                fila += "B";
                            }
                        } else {
                            fila += "B";
                        }
                        fila += "¬";

                        break;
                    case "cliIp":
                        fila += (invFunListadoClientesTO.getCliIp() == null ? "B" : "S" + invFunListadoClientesTO.getCliIp()) + "¬";
                        break;
                    case "cliDiasCreditoAseguradora":
                        fila += (invFunListadoClientesTO.getCliDiasCreditoAseguradora() == null ? "B" : "I" + invFunListadoClientesTO.getCliDiasCreditoAseguradora()) + "¬";
                        break;
                    case "cliCupoCreditoAseguradora":
                        fila += (invFunListadoClientesTO.getCliCupoCreditoAseguradora() == null ? "B" : "I" + invFunListadoClientesTO.getCliCupoCreditoAseguradora()) + "¬";
                        break;
                    case "proGarantia":
                        fila += (invFunListadoClientesTO.getProGarantia() == null ? "B" : "I" + invFunListadoClientesTO.getProGarantia()) + "¬";
                        break;
                    default:
                        fila += "";
                        break;
                }
            }
        }
        return fila;
    }

    @Override
    public Map<String, Object> exportarReporteProductoImprimirPlacas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<DatoFunListaProductosImpresionPlaca> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de impresión de placa de productos");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SCódigo barra" + "¬" + "SProducto" + "¬" + "SEmpaque" + "¬" + "SPrecio 1" + "¬" + "SPrecia 2" + "¬" + "SPrecio 3");
            for (DatoFunListaProductosImpresionPlaca datoFunListaProductosImpresionPlaca : listado) {
                listaCuerpo.add(
                        (datoFunListaProductosImpresionPlaca.getLpspCodigoPrincipal() == null ? "B" : "S" + datoFunListaProductosImpresionPlaca.getLpspCodigoPrincipal()) + "¬"
                        + (datoFunListaProductosImpresionPlaca.getLpspCodigoBarra() == null ? "B" : "S" + datoFunListaProductosImpresionPlaca.getLpspCodigoBarra()) + "¬"
                        + (datoFunListaProductosImpresionPlaca.getLpspNombre() == null ? "B" : "S" + datoFunListaProductosImpresionPlaca.getLpspNombre()) + "¬"
                        + (datoFunListaProductosImpresionPlaca.getProEmpaque() == null ? "B" : "S" + datoFunListaProductosImpresionPlaca.getProEmpaque()) + "¬"
                        + (datoFunListaProductosImpresionPlaca.getLpspPrecio1() == null ? "B" : "D" + datoFunListaProductosImpresionPlaca.getLpspPrecio1()) + "¬"
                        + (datoFunListaProductosImpresionPlaca.getLpspPrecio2() == null ? "B" : "D" + datoFunListaProductosImpresionPlaca.getLpspPrecio2()) + "¬"
                        + (datoFunListaProductosImpresionPlaca.getLpspPrecio3() == null ? "B" : "D" + datoFunListaProductosImpresionPlaca.getLpspPrecio3())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteInvProductoCategoriaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoCategoriaTO> listaInvProductoCategoriaTO) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de categorías de producto");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SDetalle");
            for (InvProductoCategoriaTO categoria : listaInvProductoCategoriaTO) {
                listaCuerpo.add((categoria.getCatCodigo() == null ? "B"
                        : "S" + categoria.getCatCodigo())
                        + "¬"
                        + (categoria.getCatDetalle() == null ? "B"
                        : "S" + categoria.getCatDetalle()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteInvProductoMedidaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoMedidaTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de unidad de medida de producto");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SDetalle" + "¬" + "SConv. Kilos" + "¬" + "SConv. Libras");
            for (InvProductoMedidaTO unidad : listado) {
                listaCuerpo.add(
                        (unidad.getMedCodigo() == null ? "B" : "S" + unidad.getMedCodigo())
                        + "¬" + (unidad.getMedDetalle() == null ? "B" : "S" + unidad.getMedDetalle())
                        + "¬" + (unidad.getMedConvKilos() == null ? "B" : "D" + unidad.getMedConvKilos())
                        + "¬" + (unidad.getMedConvLibras() == null ? "B" : "D" + unidad.getMedConvLibras())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteInvFunListadoProductosTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvFunListadoProductosTO> listado) throws Exception {

        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de producto");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SDescripcción" + "¬" + "SMedida" + "¬" + "SCategoría" + "¬" + "SPrecio 1" + "¬" + "SPrecio 2" + "¬" + "SPrecio 3" + "¬" + "SPrecio 4" + "¬" + "SPrecio 5");
            for (InvFunListadoProductosTO producto : listado) {
                listaCuerpo.add(
                        (producto.getPrdCodigoPrincipal() == null ? "B" : "S" + producto.getPrdCodigoPrincipal())
                        + "¬" + (producto.getPrdNombre() == null ? "B" : "S" + producto.getPrdNombre())
                        + "¬" + (producto.getPrdMedida() == null ? "B" : "S" + producto.getPrdMedida())
                        + "¬" + (producto.getPrdCategoria() == null ? "B" : "S" + producto.getPrdCategoria())
                        + "¬" + (producto.getPrdPrecio1() == null ? "B" : "D" + producto.getPrdPrecio1())
                        + "¬" + (producto.getPrdPrecio2() == null ? "B" : "D" + producto.getPrdPrecio2())
                        + "¬" + (producto.getPrdPrecio3() == null ? "B" : "D" + producto.getPrdPrecio3())
                        + "¬" + (producto.getPrdPrecio4() == null ? "B" : "D" + producto.getPrdPrecio4())
                        + "¬" + (producto.getPrdPrecio5() == null ? "B" : "D" + producto.getPrdPrecio5())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Map<String, Object> exportarReporteProductos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvListaProductosGeneralTO> listado, List<InvFunListadoProductosTO> listado2, List<DetalleExportarFiltrado> listadoFiltrado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String filaCabecera = "";
            String fila = "";
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de producto");
            listaCabecera.add("S");
            for (DetalleExportarFiltrado cabecera : listadoFiltrado) {
                filaCabecera += cabecera.isCheck() ? "S" + cabecera.getHeaderName() + "¬" : "";
                filaCabecera += cabecera.isCheck() && cabecera.getField().equals("prdCuentaInventario") ? "SInventario" + "¬" : "";
                filaCabecera += cabecera.isCheck() && cabecera.getField().equals("prdCuentaGasto") ? "SGasto" + "¬" : "";
                filaCabecera += cabecera.isCheck() && cabecera.getField().equals("prdCuentaVenta") ? "SVenta" + "¬" : "";
            }
            listaCuerpo.add(filaCabecera);

            if (listado != null && listado.size() > 0) {
                for (InvListaProductosGeneralTO producto : listado) {
                    fila = "";
                    for (int i = 0; i < listadoFiltrado.size(); i++) {//Columnas
                        if (listadoFiltrado.get(i).isCheck()) {
                            switch (listadoFiltrado.get(i).getField()) {
                                case "proCodigoPrincipal":
                                    fila += (producto.getProCodigoPrincipal() == null ? "B" : "S" + producto.getProCodigoPrincipal()) + "¬";
                                    break;
                                case "proNombre":
                                    fila += (producto.getProNombre() == null ? "B" : "S" + producto.getProNombre()) + "¬";
                                    break;
                                case "detalleMedida":
                                    fila += (producto.getDetalleMedida() == null ? "B" : "S" + producto.getDetalleMedida()) + "¬";
                                    break;
                                case "proCategoria":
                                    fila += (producto.getProCategoria() == null ? "B" : "S" + producto.getProCategoria()) + "¬";
                                    break;
                                case "proPrecio":
                                    fila += (producto.getProPrecio() == null ? "B" : "D" + producto.getProPrecio()) + "¬";
                                    break;
                                case "stockSaldo":
                                    fila += (producto.getStockSaldo() == null ? "B" : "I" + producto.getStockSaldo()) + "¬";
                                    break;
                                case "tipTipo":
                                    fila += (producto.getTipTipo() == null ? "B" : "S" + producto.getTipTipo()) + "¬";
                                    break;
                                case "proGravaIva":
                                    fila += (producto.getProGravaIva() != null && producto.getProGravaIva().contains("NO") == true ? "SNO" : "SSI") + "¬";
                                    break;
                                default:
                                    fila += "";
                                    break;
                            }
                        }
                    }
                    listaCuerpo.add(fila);
                }
            }

            if (listado2 != null && listado2.size() > 0) {
                for (InvFunListadoProductosTO producto : listado2) {
                    fila = "";
                    for (int i = 0; i < listadoFiltrado.size(); i++) {//Columnas
                        if (listadoFiltrado.get(i).isCheck()) {
                            switch (listadoFiltrado.get(i).getField()) {
                                case "prdCodigoPrincipal":
                                    fila += (producto.getPrdCodigoPrincipal() == null ? "B" : "S" + producto.getPrdCodigoPrincipal()) + "¬";
                                    break;
                                case "prdCodigoAlterno":
                                    fila += (producto.getPrdCodigoAlterno() == null ? "B" : "S" + producto.getPrdCodigoAlterno()) + "¬";
                                    break;
                                case "prdCodigoBarra":
                                    fila += (producto.getPrdCodigoBarra() == null ? "B" : "S" + producto.getPrdCodigoBarra()) + "¬";
                                    break;
                                case "prdNombre":
                                    fila += (producto.getPrdNombre() == null ? "B" : "S" + producto.getPrdNombre()) + "¬";
                                    break;
                                case "prdDetalle":
                                    fila += (producto.getPrdDetalle() == null ? "B" : "S" + producto.getPrdDetalle()) + "¬";
                                    break;
                                case "prdMedida":
                                    fila += (producto.getPrdMedida() == null ? "B" : "S" + producto.getPrdMedida()) + "¬";
                                    break;
                                case "prdMarca":
                                    fila += (producto.getPrdMarca() == null ? "B" : "S" + producto.getPrdMarca()) + "¬";
                                    break;
                                case "prdCategoria":
                                    fila += (producto.getPrdCategoria() == null ? "B" : "S" + producto.getPrdCategoria()) + "¬";
                                    break;
                                case "prdPrecio1":
                                    fila += (producto.getPrdPrecio1() == null ? "B" : "D" + producto.getPrdPrecio1()) + "¬";
                                    break;
                                case "prdPrecio2":
                                    fila += (producto.getPrdPrecio2() == null ? "B" : "D" + producto.getPrdPrecio2()) + "¬";
                                    break;
                                case "prdPrecio3":
                                    fila += (producto.getPrdPrecio3() == null ? "B" : "D" + producto.getPrdPrecio3()) + "¬";
                                    break;
                                case "prdPrecio4":
                                    fila += (producto.getPrdPrecio4() == null ? "B" : "D" + producto.getPrdPrecio4()) + "¬";
                                    break;
                                case "prdPrecio5":
                                    fila += (producto.getPrdPrecio5() == null ? "B" : "D" + producto.getPrdPrecio5()) + "¬";
                                    break;
                                case "prdMinimo":
                                    fila += (producto.getPrdMinimo() == null ? "B" : "I" + producto.getPrdMinimo()) + "¬";
                                    break;
                                case "prdMaximo":
                                    fila += (producto.getPrdMaximo() == null ? "B" : "I" + producto.getPrdMaximo()) + "¬";
                                    break;
                                case "prdTipo":
                                    fila += (producto.getPrdTipo() == null ? "B" : "S" + producto.getPrdTipo()) + "¬";
                                    break;
                                case "prdCuentaInventario":
                                    fila += (producto.getPrdCuentaInventario() == null ? "B" : "S" + producto.getPrdCuentaInventario()) + "¬";
                                    fila += (producto.getPrdCuentaInventario() == null ? "B" : "S" + producto.getPrdNombreInventario()) + "¬";
                                    break;
                                case "prdCuentaGasto":
                                    fila += (producto.getPrdCuentaGasto() == null ? "B" : "S" + producto.getPrdCuentaGasto()) + "¬";
                                    fila += (producto.getPrdCuentaGasto() == null ? "B" : "S" + producto.getPrdNombreGasto()) + "¬";
                                    break;
                                case "prdCuentaVenta":
                                    fila += (producto.getPrdCuentaVenta() == null ? "B" : "S" + producto.getPrdCuentaVenta()) + "¬";
                                    fila += (producto.getPrdCuentaVenta() == null ? "B" : "S" + producto.getPrdNombreVenta()) + "¬";
                                    break;
                                case "prdIva":
                                    fila += (producto.getPrdIva() != null && producto.getPrdIva().contains("NO") == true ? "SNO" : "SSI") + "¬";
                                    break;
                                case "prdCreditoTributario":
                                    fila += (producto.getPrdCreditoTributario() == null ? "B" : "S" + producto.getPrdCreditoTributario()) + "¬";
                                    break;
                                case "prdStockNegativo":
                                    fila += (producto.getPrdStockNegativo() == false ? "SNO" : "SSI") + "¬";
                                    break;
                                case "prdInactivo":
                                    fila += (producto.getPrdInactivo() == false ? "SNO" : "SSI") + "¬";
                                    break;
                                default:
                                    fila += "";
                                    break;
                            }
                        }
                    }
                    listaCuerpo.add(fila);
                }
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteProductoMarca(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoMarcaComboListadoTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de producto");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SNombre" + "¬" + "SAbreviado" + "¬" + "SFacturación");
            for (InvProductoMarcaComboListadoTO productoMarca : listado) {
                listaCuerpo.add(
                        (productoMarca.getMarCodigo() == null ? "B" : "S" + productoMarca.getMarCodigo())
                        + "¬" + (productoMarca.getMarDetalle() == null ? "B" : "S" + productoMarca.getMarDetalle())
                        + "¬" + (productoMarca.getMarAbreviado() == null ? "B" : "S" + productoMarca.getMarAbreviado())
                        + "¬" + (productoMarca.isMarIncluirEnFacturacion() == true ? "SSI" : "SNO")
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReportePresentacionUnidad(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoPresentacionUnidadesComboListadoTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de presentación de unidad");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SDetalle" + "¬" + "SAbreviado");
            for (InvProductoPresentacionUnidadesComboListadoTO presentacion : listado) {
                listaCuerpo.add(
                        (presentacion.getPresuCodigo() == null ? "B" : "S" + presentacion.getPresuCodigo())
                        + "¬" + (presentacion.getPresuDetalle() == null ? "B" : "S" + presentacion.getPresuDetalle())
                        + "¬" + (presentacion.getPresuAbreviado() == null ? "B" : "S" + presentacion.getPresuAbreviado())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReportePresentacionCaja(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoPresentacionCajasComboListadoTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de presentación de caja");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SDetalle" + "¬" + "SAbreviado");
            for (InvProductoPresentacionCajasComboListadoTO presentacion : listado) {
                listaCuerpo.add(
                        (presentacion.getPrescCodigo() == null ? "B" : "S" + presentacion.getPrescCodigo())
                        + "¬" + (presentacion.getPrescDetalle() == null ? "B" : "S" + presentacion.getPrescDetalle())
                        + "¬" + (presentacion.getPrescAbreviado() == null ? "B" : "S" + presentacion.getPrescAbreviado())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteProductoTipo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductoTipoComboTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de tipo de producto");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SDetalle" + "¬" + "STipo" + "¬" + "SActivo");
            for (InvProductoTipoComboTO invProductoTipoComboTO : listado) {
                listaCuerpo.add(
                        (invProductoTipoComboTO.getTipCodigo() == null ? "B" : "S" + invProductoTipoComboTO.getTipCodigo())
                        + "¬" + (invProductoTipoComboTO.getTipDetalle() == null ? "B" : "S" + invProductoTipoComboTO.getTipDetalle())
                        + "¬" + (invProductoTipoComboTO.getTipTipo() == null ? "B" : "S" + invProductoTipoComboTO.getTipTipo())
                        + "¬" + (invProductoTipoComboTO.getTipActivo() == true ? "SSÍ" : "SNO")
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteComprasConsolidandoProductos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvFunComprasConsolidandoProductosTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SCompras Consolidado Productos");

            listaCabecera.add("S");
            listaCuerpo.add("SProducto" + "¬" + "SCódigo" + "¬" + "SMedida" + "¬" + "SCantidad" + "¬" + "STotal" + "¬" + "SPorcentaje");
            for (InvFunComprasConsolidandoProductosTO invFunComprasConsolidandoProductosTO : listado) {
                listaCuerpo.add((invFunComprasConsolidandoProductosTO.getCcpProducto() == null ? "B"
                        : "S" + invFunComprasConsolidandoProductosTO.getCcpProducto())
                        + "¬"
                        + (invFunComprasConsolidandoProductosTO.getCcpCodigo() == null ? "B"
                        : "S" + invFunComprasConsolidandoProductosTO.getCcpCodigo().toString())
                        + "¬"
                        + (invFunComprasConsolidandoProductosTO.getCcpMedida() == null ? "B"
                        : "S" + invFunComprasConsolidandoProductosTO.getCcpMedida().toString())
                        + "¬"
                        + (invFunComprasConsolidandoProductosTO.getCcpCantidad() == null ? "B"
                        : "D" + invFunComprasConsolidandoProductosTO.getCcpCantidad().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (invFunComprasConsolidandoProductosTO.getCcpTotal() == null ? "B"
                        : "D" + invFunComprasConsolidandoProductosTO.getCcpTotal().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (invFunComprasConsolidandoProductosTO.getCcpPorcentaje() == null ? "B"
                        : "D" + invFunComprasConsolidandoProductosTO.getCcpPorcentaje().add(BigDecimal.ZERO).toString())
                        + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteVentasConsolidandoProductos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvFunVentasConsolidandoProductosTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SVentas Consolidado Productos");
            listaCabecera.add("S");
            listaCuerpo.add("SProducto" + "¬" + "SCódigo" + "¬" + "SMedida" + "¬" + "SCantidad" + "¬" + "STotal" + "¬" + "SPorcentaje");
            for (InvFunVentasConsolidandoProductosTO invFunVentasConsolidandoProductosTO : listado) {
                listaCuerpo.add((invFunVentasConsolidandoProductosTO.getVcpProducto() == null ? "B"
                        : "S" + invFunVentasConsolidandoProductosTO.getVcpProducto())
                        + "¬"
                        + (invFunVentasConsolidandoProductosTO.getVcpCodigo() == null ? "B"
                        : "S" + invFunVentasConsolidandoProductosTO.getVcpCodigo())
                        + "¬"
                        + (invFunVentasConsolidandoProductosTO.getVcpMedida() == null ? "B"
                        : "S" + invFunVentasConsolidandoProductosTO.getVcpMedida())
                        + "¬" + (invFunVentasConsolidandoProductosTO.getVcpCantidad() == null ? "B"
                        : "D" + invFunVentasConsolidandoProductosTO.getVcpCantidad().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (invFunVentasConsolidandoProductosTO.getVcpTotal() == null ? "B"
                        : "D" + invFunVentasConsolidandoProductosTO.getVcpTotal().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (invFunVentasConsolidandoProductosTO.getVcpPorcentaje() == null ? "B"
                        : "D" + invFunVentasConsolidandoProductosTO.getVcpPorcentaje().add(BigDecimal.ZERO).toString())
                        + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteSaldoBodegaComprobacion(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String bodega, String fechaDesde, String fechaHasta, List<SaldoBodegaComprobacionTO> listado) throws Exception {
        try {
            BigDecimal cero = new BigDecimal("0.00");
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SSaldo Bodega Comprobación Montos");
            listaCabecera.add("SBodega: " + bodega);
            listaCabecera.add("SFecha Desde: " + fechaDesde);
            listaCabecera.add("SFecha Hasta: " + fechaHasta);
            listaCabecera.add("T");
            listaCuerpo.add("SProducto" + "¬" + "SCódigo" + "¬" + "SProducto cuenta" + "¬" + "SMedida" + "¬" + "SInicial" + "¬" + "SCompra" + "¬"
                    + "SVenta" + "¬" + "SConsumo" + "¬" + "STransferenciaI" + "¬" + "STransferenciaE" + "¬" + "SAjusteI" + "¬" + "SAjusteE" + "¬"
                    + "SDevolucion Compra" + "¬" + "SDevolución Venta" + "¬" + "SFinal" + "¬" + "SDiferencia");
            for (SaldoBodegaComprobacionTO saldoBodegaComprobacionTO : listado) {
                listaCuerpo
                        .add((saldoBodegaComprobacionTO.getSbcProductoNombre() == null ? "B"
                                : "S" + saldoBodegaComprobacionTO.getSbcProductoNombre())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcProductoCodigo() == null ? "B"
                                : "S" + saldoBodegaComprobacionTO.getSbcProductoCodigo())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcProductoCuenta() == null ? "B"
                                : "S" + saldoBodegaComprobacionTO.getSbcProductoCuenta())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcMedida() == null ? "B"
                                : "S" + saldoBodegaComprobacionTO.getSbcMedida())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcInicial() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcInicial().add(cero).toString())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcCompra() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcCompra().add(cero).toString())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcVenta() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcVenta().add(cero).toString())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcConsumo() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcConsumo().add(cero).toString())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcTransferenciaI() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcTransferenciaI().add(cero).toString())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcTransferenciaE() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcTransferenciaE().add(cero).toString())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcAjusteI() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcAjusteI().add(cero).toString())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcAjusteE() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcAjusteE().add(cero).toString())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcDevolucionC() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcDevolucionC().add(cero).toString())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcDevolucionV() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcDevolucionV().add(cero).toString())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcFinal() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcFinal().add(cero).toString())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcDiferencia() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcDiferencia().add(cero).toString())
                                + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteSaldoBodegaComprobacionCantidades(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String bodega, String fechaDesde, String fechaHasta, List<SaldoBodegaComprobacionTO> listado) throws Exception {
        try {
            BigDecimal cero = new BigDecimal("0.00");
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SSaldo Bodega Comprobación Cantidades");
            listaCabecera.add("SBodega: " + bodega);
            listaCabecera.add("SFecha Desde: " + fechaDesde);
            listaCabecera.add("SFecha Hasta: " + fechaHasta);
            listaCabecera.add("T");
            listaCuerpo.add("SProducto" + "¬" + "SCódigo" + "¬" + "SProducto cuenta" + "¬" + "SMedida" + "¬" + "SInicial" + "¬" + "SCompra" + "¬"
                    + "SVenta" + "¬" + "SConsumo" + "¬" + "STransferenciaI" + "¬" + "STransferenciaE" + "¬" + "SAjusteI" + "¬" + "SAjusteE" + "¬"
                    + "SDevolucion Compra" + "¬" + "SDevolución Venta" + "¬" + "SFinal" + "¬" + "SDiferencia");
            for (SaldoBodegaComprobacionTO saldoBodegaComprobacionTO : listado) {
                listaCuerpo
                        .add((saldoBodegaComprobacionTO.getSbcProductoNombre() == null ? "B"
                                : "S" + saldoBodegaComprobacionTO.getSbcProductoNombre())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcProductoCodigo() == null ? "B"
                                : "S" + saldoBodegaComprobacionTO.getSbcProductoCodigo())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcProductoCuenta() == null ? "B"
                                : "S" + saldoBodegaComprobacionTO.getSbcProductoCuenta())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcMedida() == null ? "B"
                                : "S" + saldoBodegaComprobacionTO.getSbcMedida())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcInicial() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcInicial().add(cero).toString())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcCompra() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcCompra().add(cero).toString())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcVenta() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcVenta().add(cero).toString())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcConsumo() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcConsumo().add(cero).toString())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcTransferenciaI() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcTransferenciaI().add(cero).toString())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcTransferenciaE() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcTransferenciaE().add(cero).toString())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcAjusteI() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcAjusteI().add(cero).toString())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcAjusteE() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcAjusteE().add(cero).toString())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcDevolucionC() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcDevolucionC().add(cero).toString())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcDevolucionV() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcDevolucionV().add(cero).toString())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcFinal() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcFinal().add(cero).toString())
                                + "¬"
                                + (saldoBodegaComprobacionTO.getSbcDiferencia() == null ? "B"
                                : "D" + saldoBodegaComprobacionTO.getSbcDiferencia().add(cero).toString())
                                + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteInvFunListaProductosSaldosGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Object[][] datos, List<String> columnas, List<String> columnasFaltantes) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SConsumo Por Fecha Desglosado");
            listaCabecera.add("S");
            String nombresColumnas = "SNombre" + "¬" + "SCódigo" + "¬";
            for (int i = 0; i < columnasFaltantes.size(); i++) {
                nombresColumnas = nombresColumnas + "S" + columnasFaltantes.get(i).toString() + "¬";
            }
            listaCuerpo.add(nombresColumnas);
            int z = 0;
            String dato = "";
            do {
                dato = "";
                for (int i = 0; i < columnas.size(); i++) {
                    if (i < 4 || (datos[z][i] != null && datos[z][i] == "")) {
                        dato = dato + "S" + (datos[z][i] != null ? datos[z][i].toString() : "") + "¬";
                    } else {
                        if (datos[z][i] != null && datos[z][i] == "") {
                            dato = dato + "S" + (datos[z][i] != null ? datos[z][i].toString() : "") + "¬";
                        } else {
                            dato = dato + "D" + (datos[z][i] != null ? datos[z][i].toString() : "") + "¬";
                        }
                    };
                }
                listaCuerpo.add(dato);
                z++;
            } while (z < datos.length);
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteComprasPorPeriodo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Object[][] datos, List<String> columnas, List<String> columnasFaltantes) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SCosto Por Fechas (Porrateado)");
            listaCabecera.add("S");
            String nombresColumnas = "SNombre" + "¬" + "SCódigo" + "¬" + "SMedida" + "¬";

            for (String item : columnasFaltantes) {
                nombresColumnas = nombresColumnas + "S" + item + "¬";
            }
            listaCuerpo.add(nombresColumnas);
            int z = 0;
            String dato = "";
            do {
                dato = "";
                for (int i = 0; i < columnas.size(); i++) {
                    if (i < 4 || (datos[z][i] != null && datos[z][i] == "")) {
                        dato = dato + "S" + (datos[z][i] != null ? datos[z][i].toString() : "") + "¬";
                    } else {
                        if (datos[z][i] != null && datos[z][i] == "") {
                            dato = dato + "S" + (datos[z][i] != null ? datos[z][i].toString() : "") + "¬";
                        } else {
                            dato = dato + "D" + (datos[z][i] != null ? datos[z][i].toString() : "") + "¬";
                        }
                    }
                }
                listaCuerpo.add(dato);
                z++;
            } while (z < datos.length);
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteInvFunVentasVsCosto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvFunVentasVsCostoTO> listado, String fechaDesde, String fechaHasta, String cliente, String bodega) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte Ventas Vs Costo");
            if (cliente != null) {
                listaCabecera.add("SCliente = " + cliente);
            }
            if (bodega != null) {
                listaCabecera.add("SBodega = " + bodega);
            }
            listaCabecera.add("SFecha desde = " + fechaDesde);
            listaCabecera.add("SFecha hasta = " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SProducto" + "¬" + "SCódigo" + "¬" + "SMedida" + "¬" + "SCantidad" + "¬" + "STotal Ventas" + "¬" + "STotal Costo" + "¬" + "SPorcentaje");
            for (InvFunVentasVsCostoTO invFunVentasVsCostoTO : listado) {
                listaCuerpo.add((invFunVentasVsCostoTO.getVcProducto() == null ? "B"
                        : "S" + invFunVentasVsCostoTO.getVcProducto())
                        + "¬"
                        + (invFunVentasVsCostoTO.getVcCodigo() == null ? "B"
                        : "S" + invFunVentasVsCostoTO.getVcCodigo())
                        + "¬"
                        + (invFunVentasVsCostoTO.getVcMedida() == null ? "B"
                        : "S" + invFunVentasVsCostoTO.getVcMedida())
                        + "¬"
                        + (invFunVentasVsCostoTO.getVcCantidad() == null ? "B"
                        : "D" + invFunVentasVsCostoTO.getVcCantidad().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (invFunVentasVsCostoTO.getVcTotalVentas() == null ? "B"
                        : "D" + invFunVentasVsCostoTO.getVcTotalVentas().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (invFunVentasVsCostoTO.getVcTotalCosto() == null ? "B"
                        : "D" + invFunVentasVsCostoTO.getVcTotalCosto().add(BigDecimal.ZERO).toString())
                        + "¬" + (invFunVentasVsCostoTO.getVcPorcentaje() == null ? "B"
                        : "D" + invFunVentasVsCostoTO.getVcPorcentaje().add(BigDecimal.ZERO).toString())
                        + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarListadoProductosPreciosStock(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvListaProductosTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de Productos, Precios y Cantidades");
            listaCabecera.add("S");

            listaCuerpo.add("SCódigo" + "¬" + "SNombre" + "¬" + "SCategoría" + "¬" + "SStock" + "¬" + "SMedida" + "¬" + "SPrecio 1" + "¬" + "SPrecio 2" + "¬" + "SPrecio 3");
            for (InvListaProductosTO invListaProductosTO : listado) {
                listaCuerpo.add((invListaProductosTO.getProCodigoPrincipal() == null ? "B"
                        : "S" + invListaProductosTO.getProCodigoPrincipal().toString())
                        + "¬"
                        + (invListaProductosTO.getProNombre() == null ? "B"
                        : "S" + invListaProductosTO.getProNombre().toString())
                        + "¬"
                        + (invListaProductosTO.getProCategoria() == null ? "B"
                        : "S" + invListaProductosTO.getProCategoria().toString())
                        + "¬"
                        + (invListaProductosTO.getStockSaldo() == null ? "B"
                        : "D" + invListaProductosTO.getStockSaldo().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (invListaProductosTO.getDetalleMedida() == null ? "B"
                        : "S" + invListaProductosTO.getDetalleMedida().toString())
                        + "¬"
                        + (invListaProductosTO.getStockPrecio1() == null ? "B"
                        : "D" + invListaProductosTO.getStockPrecio1().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (invListaProductosTO.getStockPrecio2() == null ? "B"
                        : "D" + invListaProductosTO.getStockPrecio2().add(BigDecimal.ZERO).toString())
                        + "¬" + (invListaProductosTO.getStockPrecio3() == null ? "B"
                        : "D" + invListaProductosTO.getStockPrecio3().add(BigDecimal.ZERO).toString()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReconstruccionSaldosCostos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProductosConErrorTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de Productos con Errores");
            listaCabecera.add("S");
            listaCuerpo.add("SBodega" + "¬" + "SCódigo" + "¬" + "SNombre" + "¬" + "SCantidad");
            for (InvProductosConErrorTO invProductosConErrorTO : listado) {
                listaCuerpo.add((invProductosConErrorTO.getErrBodega() == null ? "B"
                        : "S" + invProductosConErrorTO.getErrBodega())
                        + "¬"
                        + (invProductosConErrorTO.getErrProductoCodigo() == null ? "B"
                        : "S" + invProductosConErrorTO.getErrProductoCodigo())
                        + "¬"
                        + (invProductosConErrorTO.getErrProductoNombre() == null ? "B"
                        : "S" + invProductosConErrorTO.getErrProductoNombre())
                        + "¬" + (invProductosConErrorTO.getErrCantidad() == null ? "B"
                        : "D" + invProductosConErrorTO.getErrCantidad()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteInvFunVentasConsolidandoClientesTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector, String fechaDesde, String fechaHasta, List<InvFunVentasConsolidandoClientesTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SVentas Consolidado Clientes");
            listaCabecera.add("SSector: " + sector);
            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("Sidentificación" + "¬" + "SNombre" + "¬" + "SNº Comprobante" + "¬" + "SBase 0" + "¬" + "SBase Imponible" + "¬" + "SMonto Iva" + "¬" + "STotal");
            for (InvFunVentasConsolidandoClientesTO invFunVentasConsolidandoClientesTO : listado) {
                listaCuerpo.add((invFunVentasConsolidandoClientesTO.getVtaIdentificacion() == null ? "B"
                        : "S" + invFunVentasConsolidandoClientesTO.getVtaIdentificacion()) + "¬"
                        + (invFunVentasConsolidandoClientesTO.getVtaNombreCliente() == null ? "B"
                        : "S" + invFunVentasConsolidandoClientesTO.getVtaNombreCliente())
                        + "¬" + (invFunVentasConsolidandoClientesTO.getVtaNumeroComprobantes() == null ? "B"
                        : "D" + invFunVentasConsolidandoClientesTO.getVtaNumeroComprobantes().toString())
                        + "¬"
                        + (invFunVentasConsolidandoClientesTO.getVtaBase0() == null ? "B"
                        : "D" + invFunVentasConsolidandoClientesTO.getVtaBase0().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (invFunVentasConsolidandoClientesTO.getVtaBaseimponible() == null ? "B"
                        : "D" + invFunVentasConsolidandoClientesTO.getVtaBaseimponible().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (invFunVentasConsolidandoClientesTO.getVtaMontoiva() == null ? "B"
                        : "D" + invFunVentasConsolidandoClientesTO.getVtaMontoiva().add(BigDecimal.ZERO).toString())
                        + "¬" + (invFunVentasConsolidandoClientesTO.getVtaTotal() == null ? "B"
                        : "D" + invFunVentasConsolidandoClientesTO.getVtaTotal().add(BigDecimal.ZERO).toString())
                        + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteInvFunVentasConsolidandoProductosCoberturaTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String sector, String fechaDesde, String fechaHasta, List<InvFunVentasConsolidandoProductosCoberturaTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SVentas Consolidado Productos");
            listaCabecera.add("SSector: " + sector);
            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SProducto" + "¬" + "SCódigo" + "¬" + "SMedida" + "¬" + "SCantidad" + "¬" + "STotal" + "¬" + "SPorcentaje");
            for (InvFunVentasConsolidandoProductosCoberturaTO invFunVentasConsolidandoProductosCoberturaTO : listado) {
                listaCuerpo.add((invFunVentasConsolidandoProductosCoberturaTO.getVcpProducto() == null ? "B"
                        : "S" + invFunVentasConsolidandoProductosCoberturaTO.getVcpProducto())
                        + "¬"
                        + (invFunVentasConsolidandoProductosCoberturaTO.getVcpCodigo() == null ? "B"
                        : "S" + invFunVentasConsolidandoProductosCoberturaTO.getVcpCodigo())
                        + "¬"
                        + (invFunVentasConsolidandoProductosCoberturaTO.getVcpMedida() == null ? "B"
                        : "S" + invFunVentasConsolidandoProductosCoberturaTO.getVcpMedida())
                        + "¬" + (invFunVentasConsolidandoProductosCoberturaTO.getVcpCantidad() == null ? "B"
                        : "D" + invFunVentasConsolidandoProductosCoberturaTO.getVcpCantidad().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (invFunVentasConsolidandoProductosCoberturaTO.getVcpTotal() == null ? "B"
                        : "D" + invFunVentasConsolidandoProductosCoberturaTO.getVcpTotal().add(BigDecimal.ZERO).toString())
                        + "¬"
                        + (invFunVentasConsolidandoProductosCoberturaTO.getVcpPorcentaje() == null ? "B"
                        : "D" + invFunVentasConsolidandoProductosCoberturaTO.getVcpPorcentaje().add(BigDecimal.ZERO).toString())
                        + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteListadoCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvFunComprasTO> listado) throws Exception {
        try {
            BigDecimal cero = BigDecimal.ZERO;
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado de Compras");
            listaCabecera.add("S");
            listaCuerpo.add("SNúmero" + "¬" + "SFecha" + "¬" + "SRuc" + "¬" + "SProveedor" + "¬"
                    + "STipo Documento" + "¬" + "SNúmero Documento" + "¬" + "SAutorización" + "¬"
                    + "SBase 0" + "¬" + "SBase Imponible" + "¬" + "SICE" + "¬" + "SMonto Iva" + "¬" + "STotal" + "¬" + "SForma Pago" + "¬"
                    + "SObservaciones" + "¬" + "SPendiente" + "¬" + "SAnulado");
            for (InvFunComprasTO invFunComprasTO : listado) {
                boolean pendiente = invFunComprasTO.getCompPendiente() == null ? false
                        : invFunComprasTO.getCompPendiente();
                boolean anulado = invFunComprasTO.getCompAnulado() == null ? false : invFunComprasTO.getCompAnulado();
                listaCuerpo.add((invFunComprasTO.getCompNumeroSistema() == null ? "B"
                        : "S" + invFunComprasTO.getCompNumeroSistema()) + "¬"
                        + (invFunComprasTO.getCompFecha() == null ? "B" : "S" + invFunComprasTO.getCompFecha()) + "¬"
                        + (invFunComprasTO.getCompProveedorRuc() == null ? "B" : "S" + invFunComprasTO.getCompProveedorRuc()) + "¬"
                        + (invFunComprasTO.getCompProveedorNombre() == null ? "B" : "S" + invFunComprasTO.getCompProveedorNombre()) + "¬"
                        + (invFunComprasTO.getCompDocumentoTipo() == null ? "B" : "S" + invFunComprasTO.getCompDocumentoTipo()) + "¬"
                        + (invFunComprasTO.getCompDocumentoNumero() == null ? "B" : "S" + invFunComprasTO.getCompDocumentoNumero()) + "¬"
                        + (invFunComprasTO.getCompDocumentoAutorizacion() == null ? "B" : "S" + invFunComprasTO.getCompDocumentoAutorizacion()) + "¬"
                        + (invFunComprasTO.getCompBase0() == null ? "B" : "D" + invFunComprasTO.getCompBase0().add(cero).toString()) + "¬"
                        + (invFunComprasTO.getCompBaseImponible() == null ? "B" : "D" + invFunComprasTO.getCompBaseImponible().add(cero).toString()) + "¬"
                        + (invFunComprasTO.getCompIce() == null ? "B" : "D" + invFunComprasTO.getCompIce().add(cero).toString()) + "¬"
                        + (invFunComprasTO.getCompMontoIva() == null ? "B" : "D" + invFunComprasTO.getCompMontoIva().add(cero).toString()) + "¬"
                        + (invFunComprasTO.getCompTotal() == null ? "B" : "D" + invFunComprasTO.getCompTotal().add(cero).toString()) + "¬"
                        + (invFunComprasTO.getCompFormaPago() == null ? "B" : "S" + invFunComprasTO.getCompFormaPago()) + "¬"
                        + (invFunComprasTO.getCompObservaciones() == null ? "B" : "S" + invFunComprasTO.getCompObservaciones()) + "¬"
                        + (pendiente == false ? "S" : "SPENDIENTE") + "¬" + (anulado == false ? "S" : "SANULADO") + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteListadoConsumos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvFunConsumosTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado de Consumos");
            listaCabecera.add("S");
            listaCuerpo.add("SNúmero¬" + "SFecha¬" + "SObservaciones¬" + "SPendiente¬" + "SAnulado");
            for (InvFunConsumosTO ifcto : listado) {
                boolean pendiente = ifcto.getCompPendiente() == null ? false : ifcto.getCompPendiente();
                boolean anulado = ifcto.getCompAnulado() == null ? false : ifcto.getCompAnulado();
                listaCuerpo
                        .add((ifcto.getCompNumeroSistema() == null ? "B" : "S" + ifcto.getCompNumeroSistema()) + "¬"
                                + (ifcto.getCompFecha() == null ? "B" : "S" + ifcto.getCompFecha()) + "¬"
                                + (ifcto.getCompObservaciones() == null ? "B"
                                : "S" + ifcto.getCompObservaciones().toString())
                                + "¬" + (pendiente == false ? "S" : "SPENDIENTE") + "¬"
                                + (anulado == false ? "S" : "SANULADO") + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteListadoVentasVendedor(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String motivo, String cliente, String documento, List<InvFunVentasVendedorTO> listInvFunVentasTO) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado de Ventas");
            listaCabecera.add("S");
            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("S");
            listaCuerpo.add("SCodigo vendedor" + "¬" + "SVendedor" + "SNúmero" + "¬" + "SFecha" + "¬" + "SIdentificaciòn" + "¬" + "SCliente" + "¬"
                    + "SDocumento" + "¬" + "SCantidad" + "¬" + "SBase 0" + "¬" + "SBase Imponible" + "¬"
                    + "SMonto Iva" + "¬" + "STotal" + "¬" + "SForma Pago" + "¬" + "SObservaciones" + "¬"
                    + "SPendiente" + "¬" + "SAnulado");
            for (InvFunVentasVendedorTO invFunVentasTO : listInvFunVentasTO) {
                boolean pendiente = invFunVentasTO.getVtaPendiente() == null ? false
                        : invFunVentasTO.getVtaPendiente();
                boolean anulado = invFunVentasTO.getVtaAnulado() == null ? false : invFunVentasTO.getVtaAnulado();
                listaCuerpo.add(
                        (invFunVentasTO.getVtaVendedorCodigo() == null ? "B" : "S" + invFunVentasTO.getVtaVendedorCodigo()) + "¬"
                        + (invFunVentasTO.getVtaVendedorNombre() == null ? "B" : "S" + invFunVentasTO.getVtaVendedorNombre()) + "¬"
                        + (invFunVentasTO.getVtaNumeroSistema() == null ? "B" : "S" + invFunVentasTO.getVtaNumeroSistema()) + "¬"
                        + (invFunVentasTO.getVtaFecha() == null ? "B" : "S" + invFunVentasTO.getVtaFecha()) + "¬"
                        + (invFunVentasTO.getVtaIdentificacion() == null ? "B" : "S" + invFunVentasTO.getVtaIdentificacion()) + "¬"
                        + (invFunVentasTO.getVtaCliente() == null ? "B" : "S" + invFunVentasTO.getVtaCliente()) + "¬"
                        + (invFunVentasTO.getVtaDocumentoNumero() == null ? "B" : "S" + invFunVentasTO.getVtaDocumentoNumero()) + "¬"
                        + (invFunVentasTO.getVtaCantidad() == null ? "B" : "D" + invFunVentasTO.getVtaCantidad().add(new BigDecimal("0.00")).toString()) + "¬"
                        + (invFunVentasTO.getVtaBase0() == null ? "B" : "D" + invFunVentasTO.getVtaBase0().add(new BigDecimal("0.00")).toString()) + "¬"
                        + (invFunVentasTO.getVtaBaseImponible() == null ? "B" : "D" + invFunVentasTO.getVtaBaseImponible().add(new BigDecimal("0.00")).toString()) + "¬"
                        + (invFunVentasTO.getVtaMontoIva() == null ? "B" : "D" + invFunVentasTO.getVtaMontoIva().add(new BigDecimal("0.00")).toString()) + "¬"
                        + (invFunVentasTO.getVtaTotal() == null ? "B" : "D" + invFunVentasTO.getVtaTotal().add(new BigDecimal("0.00")).toString()) + "¬"
                        + (invFunVentasTO.getVtaFormaPago() == null ? "B" : "S" + invFunVentasTO.getVtaFormaPago()) + "¬"
                        + (invFunVentasTO.getVtaObservaciones() == null ? "B" : "S" + invFunVentasTO.getVtaObservaciones()) + "¬"
                        + (invFunVentasTO.getVtaNumeroSistema() == null ? "B" : pendiente == false ? "SNO" : "SSI") + "¬"
                        + (invFunVentasTO.getVtaNumeroSistema() == null ? "B" : anulado == false ? "SNO" : "SSI") + "¬"
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteListadoVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String motivo, String cliente, String documento, List<InvFunVentasTO> listInvFunVentasTO) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado de Ventas");
            listaCabecera.add("S");
            String tipoDocumento = documento != null ? documento : "";
            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("SMotivo: " + motivo);
            listaCabecera.add("STipo comprobante: " + tipoDocumento);
            listaCabecera.add("SCliente: " + cliente);
            listaCuerpo.add("SNúmero" + "¬" + "SSector" + "¬" + "SFecha" + "¬" + "SIdentificaciòn" + "¬" + "SCliente" + "¬"
                    + "SDocumento" + "¬" + "SCantidad" + "¬" + "SBase 0" + "¬" + "SBase Imponible" + "¬" + "SIce" + "¬"
                    + "SMonto Iva" + "¬" + "STotal" + "¬" + "SForma Pago" + "¬" + "SObservaciones" + "¬"
                    + "SPendiente" + "¬" + "SAnulado");
            for (InvFunVentasTO invFunVentasTO : listInvFunVentasTO) {
                boolean pendiente = invFunVentasTO.getVtaPendiente() == null ? false
                        : invFunVentasTO.getVtaPendiente();
                boolean anulado = invFunVentasTO.getVtaAnulado() == null ? false : invFunVentasTO.getVtaAnulado();
                listaCuerpo.add((invFunVentasTO.getVtaNumeroSistema() == null ? "B"
                        : "S" + invFunVentasTO.getVtaNumeroSistema()) + "¬"
                        + (invFunVentasTO.getVtaSector() == null ? "B" : "S" + invFunVentasTO.getVtaSector()) + "¬"
                        + (invFunVentasTO.getVtaFecha() == null ? "B" : "S" + invFunVentasTO.getVtaFecha()) + "¬"
                        + (invFunVentasTO.getVtaIdentificacion() == null ? "B"
                        : "S" + invFunVentasTO.getVtaIdentificacion())
                        + "¬"
                        + (invFunVentasTO.getVtaCliente() == null ? "B"
                        : "S" + invFunVentasTO.getVtaCliente().toString())
                        + "¬"
                        + (invFunVentasTO.getVtaDocumentoNumero() == null ? "B"
                        : "S" + invFunVentasTO.getVtaDocumentoNumero().toString())
                        + "¬"
                        + (invFunVentasTO.getVtaCantidad() == null ? "B"
                        : "D" + invFunVentasTO.getVtaCantidad().add(new BigDecimal("0.00")).toString())
                        + "¬"
                        + (invFunVentasTO.getVtaBase0() == null ? "B"
                        : "D" + invFunVentasTO.getVtaBase0().add(new BigDecimal("0.00")).toString())
                        + "¬"
                        + (invFunVentasTO.getVtaBaseImponible() == null ? "B"
                        : "D" + invFunVentasTO.getVtaBaseImponible().add(new BigDecimal("0.00")).toString())
                        + "¬"
                        + (invFunVentasTO.getVtaIce() == null ? "B"
                        : "D" + invFunVentasTO.getVtaIce().add(new BigDecimal("0.00")).toString())
                        + "¬"
                        + (invFunVentasTO.getVtaMontoIva() == null ? "B"
                        : "D" + invFunVentasTO.getVtaMontoIva().add(new BigDecimal("0.00")).toString())
                        + "¬"
                        + (invFunVentasTO.getVtaTotal() == null ? "B"
                        : "D" + invFunVentasTO.getVtaTotal().add(new BigDecimal("0.00")).toString())
                        + "¬"
                        + (invFunVentasTO.getVtaFormaPago() == null ? "B"
                        : "S" + invFunVentasTO.getVtaFormaPago().toString())
                        + "¬"
                        + (invFunVentasTO.getVtaObservaciones() == null ? "B"
                        : "S" + invFunVentasTO.getVtaObservaciones().toString())
                        + "¬"
                        + (invFunVentasTO.getVtaNumeroSistema() == null ? "B" : pendiente == false ? "SNO" : "SSI")
                        + "¬"
                        + (invFunVentasTO.getVtaNumeroSistema() == null ? "B" : anulado == false ? "SNO" : "SSI")
                        + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvListaConsultaCompraTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de compras");
            listaCabecera.add("S");
            listaCuerpo.add("SNúmero de documento" + "¬" + "SFecha" + "¬" + "SNombre" + "¬" + "STotal" + "¬"
                    + "SForma de Pago" + "¬" + "SObservaciones" + "¬" + "SNúmero" + "¬"
                    + "SContable" + "¬" + "SEstado");
            for (InvListaConsultaCompraTO invFunComprasTO : listado) {
                listaCuerpo.add((invFunComprasTO.getCompDocumentoNumero() == null ? "B"
                        : "S" + invFunComprasTO.getCompDocumentoNumero()) + "¬"
                        + (invFunComprasTO.getCompFecha() == null ? "B" : "S" + invFunComprasTO.getCompFecha()) + "¬"
                        + (invFunComprasTO.getProvRazonSocial() == null ? "B" : "S" + invFunComprasTO.getProvRazonSocial()) + "¬"
                        + (invFunComprasTO.getCompTotal() == null ? "B" : "D" + invFunComprasTO.getCompTotal()) + "¬"
                        + (invFunComprasTO.getCompFormaPago() == null ? "B" : "S" + invFunComprasTO.getCompFormaPago()) + "¬"
                        + (invFunComprasTO.getCompObservaciones() == null ? "B" : "S" + invFunComprasTO.getCompObservaciones()) + "¬"
                        + (invFunComprasTO.getCompNumero() == null ? "B" : "S" + invFunComprasTO.getCompNumero()) + "¬"
                        + (invFunComprasTO.getConContable() == null ? "B" : "S" + invFunComprasTO.getConContable()) + "¬"
                        + (invFunComprasTO.getCompStatus() == null ? "S" : "S" + invFunComprasTO.getCompStatus()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteProformas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvListaConsultaProformaTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de proformas");
            listaCabecera.add("S");
            listaCuerpo.add("SCliente" + "¬" + "SNombre cliente" + "¬" + "SFecha" + "¬" + "SMotivo" + "¬"
                    + "SNúmero" + "¬" + "SObservaciones" + "¬" + "SPeriodo" + "¬"
                    + "SBase 0" + "SBase imponible" + "SMonto IVA" + "STotal");
            for (InvListaConsultaProformaTO proforma : listado) {
                listaCuerpo.add((proforma.getCliCodigo() == null ? "B" : "S" + proforma.getCliCodigo()) + "¬"
                        + (proforma.getCliNombre() == null ? "B" : "S" + proforma.getCliNombre()) + "¬"
                        + (proforma.getProfFecha() == null ? "B" : "S" + proforma.getProfFecha()) + "¬"
                        + (proforma.getProfMotivo() == null ? "B" : "S" + proforma.getProfMotivo()) + "¬"
                        + (proforma.getProfNumero() == null ? "B" : "S" + proforma.getProfNumero()) + "¬"
                        + (proforma.getProfObservaciones() == null ? "B" : "S" + proforma.getProfObservaciones()) + "¬"
                        + (proforma.getProfPeriodo() == null ? "B" : "S" + proforma.getProfPeriodo()) + "¬"
                        + (proforma.getProfBase0() == null ? "B" : "D" + proforma.getProfBase0()) + "¬"
                        + (proforma.getProfBaseImponible() == null ? "B" : "D" + proforma.getProfBaseImponible()) + "¬"
                        + (proforma.getProfMontoIva() == null ? "B" : "D" + proforma.getProfMontoIva()) + "¬"
                        + (proforma.getProfTotal() == null ? "B" : "D" + proforma.getProfTotal()) + "¬");
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarInvListaConsultaVenta(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvListaConsultaVentaTO> listInvListaConsultaVentaTO) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de Ventas");
            listaCabecera.add("S");
            listaCuerpo.add("SEstado" + "¬" + "SNúmero documento" + "¬" + "SFecha" + "¬" + "SCliente" + "¬"
                    + "SNombre" + "¬" + "¬" + "SForma de cobro" + "¬"
                    + "SNúmero" + "¬" + "SContable" + "¬" + "SBase 0" + "¬" + "SBase imp." + "¬" + "SMonto Iva" + "¬" + "STotal");
            for (InvListaConsultaVentaTO ventas : listInvListaConsultaVentaTO) {
                listaCuerpo.add((ventas.getVtaStatus() == null ? "S" : "S" + ventas.getVtaStatus()) + "¬"
                        + (ventas.getVtaDocumentoNumero() == null ? "S" : "S" + ventas.getVtaDocumentoNumero()) + "¬"
                        + (ventas.getVtaFecha() == null ? "S" : "S" + ventas.getVtaFecha()) + "¬"
                        + (ventas.getCliCodigo() == null ? "S" : "S" + ventas.getCliCodigo()) + "¬"
                        + (ventas.getCliNombre() == null ? "S" : "S" + ventas.getCliNombre()) + "¬"
                        + (ventas.getVtaFormaPago() == null ? "S" : "S" + ventas.getVtaFormaPago()) + "¬"
                        + (ventas.getVtaNumero() == null ? "S" : "S" + ventas.getVtaNumero()) + "¬"
                        + (ventas.getConNumero() == null ? "S" : "S" + ventas.getConNumero()) + "¬"
                        + (ventas.getVtaBase0() == null ? "S" : "D" + ventas.getVtaBase0()) + "¬"
                        + (ventas.getVtaBaseImponible() == null ? "S" : "D" + ventas.getVtaBaseImponible()) + "¬"
                        + (ventas.getVtaMontoIva() == null ? "S" : "D" + ventas.getVtaMontoIva()) + "¬"
                        + (ventas.getVtaTotal() == null ? "S" : "D" + ventas.getVtaTotal()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteInvListaVentasDetalleProducto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvVentasDetalleProductoTO> listaVentasDetalleProducto) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de Ventas detalle producto");
            listaCabecera.add("S");
            listaCuerpo.add("SNúmero sistema" + "¬" + "SFecha" + "¬" + "SFecha vencimiento" + "¬" + "SDías de crédito" + "¬" + "SCódigo cliente" + "¬"
                    + "SIdentificación" + "¬" + "SRazón social" + "¬" + "SGrupo empresarial" + "¬" + "STipo documento" + "¬" + "SNúmero" + "¬" + "SCentro de producción" + "¬" + "SCentro de costo" + "¬" + "SBodega" + "¬"
                    + "SCódigo del producto" + "¬" + "SNombre de producto" + "¬" + "SDetalle" + "¬" + "SProducto marca" + "¬" + "SProducto tipo" + "¬" + "SCantidad" + "¬"
                    + "SProducto medida" + "¬" + "SPrecio" + "¬" + "SSubtotal" + "¬" + "SIva" + "¬" + "STotal por parcial" + "¬" + "SForma de pago" + "¬" + "SObservaciones" + "¬" + "STotal");
            for (InvVentasDetalleProductoTO item : listaVentasDetalleProducto) {
                listaCuerpo.add(
                        (item.getVtaNumeroSistema() == null ? "B" : "S" + item.getVtaNumeroSistema()) + "¬"
                        + (item.getVtaFecha() == null ? "B" : "T" + (UtilsDate.fechaFormatoString(item.getVtaFecha(), "dd-MM-yyyy"))) + "¬"
                        + (item.getVtaFechaVencimiento() == null ? "B" : "T" + (UtilsDate.fechaFormatoString(item.getVtaFechaVencimiento(), "dd-MM-yyyy"))) + "¬"
                        + (item.getVtaDiasCredito() == null ? "B" : "I" + item.getVtaDiasCredito()) + "¬"
                        + (item.getVtaClienteCodigo() == null ? "B" : "S" + item.getVtaClienteCodigo()) + "¬"
                        + (item.getVtaClienteId() == null ? "B" : "S" + item.getVtaClienteId()) + "¬"
                        + (item.getVtaClienteRazonSocial() == null ? "B" : "S" + item.getVtaClienteRazonSocial()) + "¬"
                        + (item.getVtaClienteGrupoEmpresarial() == null ? "B" : "S" + item.getVtaClienteGrupoEmpresarial()) + "¬"
                        + (item.getVtaDocumentoTipo() == null ? "B" : "S" + item.getVtaDocumentoTipo()) + "¬"
                        + (item.getVtaDocumentoNumero() == null ? "B" : "S" + item.getVtaDocumentoNumero()) + "¬"
                        + (item.getDetCentroProduccion() == null ? "B" : "S" + item.getDetCentroProduccion()) + "¬"
                        + (item.getDetCentroCosto() == null ? "B" : "S" + item.getDetCentroCosto()) + "¬"
                        + (item.getDetBodega() == null ? "B" : "S" + item.getDetBodega()) + "¬"
                        + (item.getDetProductoCodigo() == null ? "B" : "S" + item.getDetProductoCodigo()) + "¬"
                        + (item.getDetProductoNombre() == null ? "B" : "S" + item.getDetProductoNombre()) + "¬"
                        + (item.getDetProductoDetalle() == null ? "B" : "S" + item.getDetProductoDetalle()) + "¬"
                        + (item.getDetProductoMarca() == null ? "B" : "S" + item.getDetProductoMarca()) + "¬"
                        + (item.getDetProductoTipo() == null ? "B" : "S" + item.getDetProductoTipo()) + "¬"
                        + (item.getDetCantidad() == null ? "B" : "D" + item.getDetCantidad()) + "¬"
                        + (item.getDetProductoMedida() == null ? "B" : "S" + item.getDetProductoMedida()) + "¬"
                        + (item.getDetPrecio() == null ? "B" : "D" + item.getDetPrecio()) + "¬"
                        + (item.getDetSubtotal() == null ? "B" : "D" + item.getDetSubtotal()) + "¬"
                        + (item.getDetIva() == null ? "B" : "D" + item.getDetIva()) + "¬"
                        + (item.getDetTotal() == null ? "B" : "D" + item.getDetTotal()) + "¬"
                        + (item.getVtaFormaPago() == null ? "B" : "S" + item.getVtaFormaPago()) + "¬"
                        + (item.getVtaObservaciones() == null ? "B" : "S" + item.getVtaObservaciones()) + "¬"
                        + (item.getVtaTotalFinal() == null ? "B" : "D" + item.getVtaTotalFinal()) + "¬"
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteInvListaComprasDetalleProducto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaInicio, String fechafinal, String centroProduccion, String centroCosto, String bodega, List<InvComprasDetalleProductoTO> listaComprasDetalleProducto) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            String observacionesCabecera = usuarioEmpresaReporteTO.getEmpRuc().equals("0993013447001") ? "SLote" : "SObservaciones producto";
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de Compras detalle producto");
            listaCabecera.add("SFecha inicio : " + fechaInicio);
            listaCabecera.add("SFecha final: " + fechafinal);
            listaCabecera.add("SCentro de Produccion: " + centroProduccion);
            listaCabecera.add("SCentro de Costo: " + centroCosto);
            listaCabecera.add("SBodega : " + bodega);
            listaCabecera.add("S");
            listaCuerpo.add("SNúmero sistema" + "¬" + "SFecha" + "¬" + "SFecha vencimiento" + "¬" + "SDías de crédito" + "¬"
                    + "SIdentificación" + "¬" + "SRazón social" + "¬" + "STipo documento" + "¬" + "SNúmero" + "¬" + "SCentro de producción" + "¬" + "SCentro de costo" + "¬" + "SBodega" + "¬"
                    + "SCódigo del producto" + "¬" + "SNombre de producto" + "¬" + "SProducto marca" + "¬" + "SProducto tipo" + "¬" + "SCantidad" + "¬"
                    + "SProducto medida" + "¬" + "SPrecio" + "¬" + "SSubtotal" + "¬" + "SIce" + "¬" + "SIva" + "¬" + "STotal" + "¬" + "SForma de pago" + "¬" + "SObservaciones" + "¬" + observacionesCabecera);
            for (InvComprasDetalleProductoTO item : listaComprasDetalleProducto) {
                listaCuerpo.add(
                        (item.getCompNumeroSistema() == null ? "B" : "S" + item.getCompNumeroSistema()) + "¬"
                        + (item.getCompFecha() == null ? "B" : "T" + (UtilsDate.fechaFormatoString(item.getCompFecha(), "dd-MM-yyyy"))) + "¬"
                        + (item.getCompFechaVencimiento() == null ? "B" : "T" + (UtilsDate.fechaFormatoString(item.getCompFechaVencimiento(), "dd-MM-yyyy"))) + "¬"
                        + (item.getCompDiasCredito() == null ? "B" : "I" + item.getCompDiasCredito()) + "¬"
                        + (item.getCompClienteId() == null ? "B" : (item.getCompClienteRazonSocial() != null && item.getCompClienteId() != null ? "S" + item.getCompClienteId() : "B")) + "¬"
                        + (item.getCompClienteRazonSocial() == null ? "B" : "S" + item.getCompClienteRazonSocial()) + "¬"
                        + (item.getCompDocumentoTipo() == null ? "B" : "S" + item.getCompDocumentoTipo()) + "¬"
                        + (item.getCompDocumentoNumero() == null ? "B" : "S" + item.getCompDocumentoNumero()) + "¬"
                        + (item.getCompNumeroSistema() != null && item.getCompNumeroSistema().contains("SUBTOTAL") ? "B"
                        : (item.getDetCentroProduccion() == null ? "B" : "S" + item.getDetCentroProduccion())) + "¬"
                        + (item.getCompNumeroSistema() != null && item.getCompNumeroSistema().contains("SUBTOTAL") ? "B"
                        : (item.getDetCentroCosto() == null ? "B" : "S" + item.getDetCentroCosto())) + "¬"
                        + (item.getDetBodega() == null ? "B" : "S" + item.getDetBodega()) + "¬"
                        + (item.getDetProductoCodigo() == null ? "B" : "S" + item.getDetProductoCodigo()) + "¬"
                        + (item.getDetProductoNombre() == null ? "B" : "S" + item.getDetProductoNombre()) + "¬"
                        + (item.getDetProductoMarca() == null ? "B" : "S" + item.getDetProductoMarca()) + "¬"
                        + (item.getDetProductoTipo() == null ? "B" : "S" + item.getDetProductoTipo()) + "¬"
                        + (item.getDetCantidad() == null ? "B" : "D" + item.getDetCantidad()) + "¬"
                        + (item.getDetProductoMedida() == null ? "B" : "S" + item.getDetProductoMedida()) + "¬"
                        + (item.getDetPrecio() == null ? "B" : "D" + item.getDetPrecio()) + "¬"
                        + (item.getDetSubtotal() == null ? "B" : "D" + item.getDetSubtotal()) + "¬"
                        + (item.getDetIce() == null ? "B" : "D" + item.getDetIce()) + "¬"
                        + (item.getDetIva() == null ? "B" : "D" + item.getDetIva()) + "¬"
                        + (item.getDetTotal() == null ? "B" : "D" + item.getDetTotal()) + "¬"
                        + (item.getCompFormaPago() == null ? "B" : "S" + item.getCompFormaPago()) + "¬"
                        + (item.getCompObservaciones() == null ? "B" : "S" + item.getCompObservaciones()) + "¬"
                        + (item.getDetProductoObservaciones() == null ? "B" : "S" + item.getDetProductoObservaciones().toUpperCase()) + "¬"
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteConsolidadoConsumoProducto(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String fechaDesde, String fechaHasta, String bodega, String sector, String motivo, List<InvFunConsumosConsolidandoProductosTO> listInvFunConsumosConsolidandoProductosTO) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SConsumos - consolidando productos");
            listaCabecera.add("S");
            listaCabecera.add("SDesde: " + fechaDesde);
            listaCabecera.add("SHasta: " + fechaHasta);
            listaCabecera.add("SBodega: " + bodega);
            listaCabecera.add("SSector: " + sector);
            listaCabecera.add("SMotivo: " + motivo);
            listaCuerpo.add("SProducto" + "¬" + "SCódigo" + "¬" + "SMedida" + "¬" + "SCantidad" + "¬" + "SPorcentaje" + "¬" + "SCosto total");
            for (InvFunConsumosConsolidandoProductosTO consumo : listInvFunConsumosConsolidandoProductosTO) {
                listaCuerpo.add(
                        (consumo.getCcpProducto() == null ? "B" : "S" + consumo.getCcpProducto()) + "¬"
                        + (consumo.getCcpCodigo() == null ? "B" : "S" + consumo.getCcpCodigo()) + "¬"
                        + (consumo.getCcpMedida() == null ? "B" : "S" + consumo.getCcpMedida()) + "¬"
                        + (consumo.getCcpCantidad() == null ? "B" : "D" + consumo.getCcpCantidad().toString()) + "¬"
                        + (consumo.getCcpPorcentaje() == null ? "B" : "D" + consumo.getCcpPorcentaje().toString()) + "¬"
                        + (consumo.getCcpCostoTotal() == null ? "B" : "D" + consumo.getCcpCostoTotal().toString())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarMensajesConError(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<RespuestaWebTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SVentas recurrentes no guardadas.");
            listaCabecera.add("S");
            listaCuerpo.add("SMensaje" + "¬" + "SDetalle" + "¬" + "SCantidad" + "¬" + "SGrupo");
            for (RespuestaWebTO mensaje : listado) {
                List<InvClientesVentasDetalle> detalle = (List<InvClientesVentasDetalle>) mensaje.getExtraInfo();
                listaCuerpo.add(
                        (mensaje.getOperacionMensaje() == null ? "B" : "S" + mensaje.getOperacionMensaje()) + "¬" + ("B") + "¬" + ("B") + "¬" + ("B")
                );
                if (detalle != null && !detalle.isEmpty()) {
                    for (int i = 0; i < detalle.size(); i++) {
                        ObjectMapper mapper = new ObjectMapper();
                        InvClientesVentasDetalle ventaDetalle = mapper.convertValue(detalle.get(i), InvClientesVentasDetalle.class);
                        listaCuerpo.add(
                                ("B") + "¬"
                                + (ventaDetalle.getProNombre() == null ? "B" : "S" + ventaDetalle.getProNombre()) + "¬"
                                + (ventaDetalle.getDetCantidad() == null ? "B" : "D" + ventaDetalle.getDetCantidad().toString()) + "¬"
                                + (ventaDetalle.getDetGrupo() <= 0 ? "B" : "S" + ventaDetalle.getDetGrupo())
                        );
                    }
                }
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    public String completarDatosDetalleVendedoresParaExcel(List<DetalleExportarFiltrado> listadoFiltrado, InvVendedorComboListadoTO invVendedorComboListadoTO) {
        String fila = "";
        for (int i = 0; i < listadoFiltrado.size(); i++) {//Columnas
            if (listadoFiltrado.get(i).isCheck()) {
                switch (listadoFiltrado.get(i).getField()) {
                    case "vendCodigo":
                        fila += (invVendedorComboListadoTO.getVendCodigo() == null ? "B" : "S" + invVendedorComboListadoTO.getVendCodigo()) + "¬";
                        break;
                    case "vendNombre":
                        fila += (invVendedorComboListadoTO.getVendNombre() == null ? "B" : "S" + invVendedorComboListadoTO.getVendNombre()) + "¬";
                        break;
                    case "vendInactivo":
                        fila += (invVendedorComboListadoTO.getVendInactivo() == true ? "SINACTIVO" : "SACTIVO") + "¬";
                        break;
                    default:
                        fila += "";
                        break;
                }
            }
        }
        return fila;
    }

    @Override
    public Map<String, Object> exportarReporteVendedor(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvVendedorComboListadoTO> listado, List<DetalleExportarFiltrado> listadoFiltrado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            String fila;
            String filaCabecera = "";
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de vendedores");
            listaCabecera.add("S");
            for (DetalleExportarFiltrado cabecera : listadoFiltrado) {
                filaCabecera += cabecera.isCheck() && !cabecera.getField().equals("vtaRecurrente") ? "S" + cabecera.getHeaderName() + "¬" : "";
            }
            listaCuerpo.add(filaCabecera);
            for (InvVendedorComboListadoTO invVendedorComboListadoTO : listado) {//Filas
                fila = completarDatosDetalleVendedoresParaExcel(listadoFiltrado, invVendedorComboListadoTO);
                listaCuerpo.add(fila);
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarMovimientosSeries(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String titulo, List<InvFunMovimientosSerieTO> datos) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("S" + titulo);
            listaCabecera.add("S");
            listaCuerpo.add("SMovimiento" + "¬" + "SSerie" + "¬" + "SProducto" + "¬" + "SPeriodo" + "¬"
                    + "SMotivo" + "¬" + "SNumero" + "¬" + "STipo comprobante");
            for (InvFunMovimientosSerieTO movimiento : datos) {
                listaCuerpo.add(
                        (movimiento.getMvtoMovimiento() == null ? "B" : "S" + movimiento.getMvtoMovimiento()) + "¬"
                        + (movimiento.getMvtoSerie() == null ? "B" : "S" + movimiento.getMvtoSerie()) + "¬"
                        + (movimiento.getMvtoProducto() == null ? "B" : "S" + movimiento.getMvtoProducto()) + "¬"
                        + (movimiento.getMvtoPeriodo() == null ? "B" : "S" + movimiento.getMvtoPeriodo()) + "¬"
                        + (movimiento.getMvtoMotivo() == null ? "B" : "S" + movimiento.getMvtoMotivo()) + "¬"
                        + (movimiento.getMvtoNumero() == null ? "B" : "S" + movimiento.getMvtoNumero()) + "¬"
                        + (movimiento.getMvtoTipoComprobante() == null ? "B" : "S" + movimiento.getMvtoTipoComprobante())
                );
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public byte[] generarReporteInvListaConsultaCompra(List<InvListaConsultaCompra> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportInvListaConsultaCompraTO.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteInvListaConsultaVenta(List<InvListaConsultaVenta> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportInvListaConsultaVentaTO.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteInvListaConsultaConsumo(List<InvListaConsultaConsumo> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportInvListaConsultaConsumoTO.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listado);
    }

    @Override
    public byte[] generarReporteInvListaConsultaTransferencia(List<InvListaConsultaTransferencia> listado, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportInvListaConsultaTransferenciaTO.jrxml", usuarioEmpresaReporteTO, new HashMap<>(), listado);
    }

    @Override
    public Map<String, Object> exportarPlantillaConsumo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception {
        List<InvConsumosDetalle> listado = UtilsJSON.jsonToList(InvConsumosDetalle.class, map.get("listado"));
        Map<String, Object> respuesta = new HashMap<>();
        List<String> listaCabecera = new ArrayList<>();
        List<String> listaCuerpo = new ArrayList<>();
        listaCuerpo.add("SARTICULO" + "¬" + "SNOMBRE" + "¬" + "SCANTIDAD" + "¬" + "SPISCINA");
        listado.stream().forEach((item) -> {
            if (item.getInvProducto() != null && item.getInvProducto().getInvProductoPK() != null) {
                listaCuerpo.add(
                        "S" + item.getInvProducto().getInvProductoPK().getProCodigoPrincipal()
                        + "¬" + "S" + item.getInvProducto().getProNombre()
                        + "¬" + (item.getDetCantidad() == null ? "B" : "D" + item.getDetCantidad())
                        + "¬" + (item.getPisNumero() == null ? "B" : "S" + item.getPisNumero()));
            }
        });
        respuesta.put("listaCabecera", listaCabecera);
        respuesta.put("listaCuerpo", listaCuerpo);
        return respuesta;
    }

    @Override
    public Map<String, Object> exportarPlantillaCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception {
        List<InvComprasDetalleTO> listado = UtilsJSON.jsonToList(InvComprasDetalleTO.class, map.get("listado"));
        Map<String, Object> respuesta = new HashMap<>();
        List<String> listaCabecera = new ArrayList<>();
        List<String> listaCuerpo = new ArrayList<>();
        listaCuerpo.add("SBODEGA" + "¬" + "SARTICULO" + "¬" + "SNOMBRE" + "¬" + "SCANTIDAD" + "¬" + "SPARCIAL" + "¬" + "SOBSERVACIONES" + "¬");
        listado.stream().forEach((item) -> {
            if (item.getProCodigoPrincipal() != null) {
                InvProducto producto = null;
                try {
                    producto = productoService.obtenerPorId(usuarioEmpresaReporteTO.getEmpCodigo(), item.getProCodigoPrincipal());
                } catch (Exception ex) {
                    Logger.getLogger(ReporteInventarioServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (producto != null) {
                    listaCuerpo.add(
                            "S" + item.getBodCodigo()
                            + "¬" + "S" + item.getProCodigoPrincipal()
                            + "¬" + "S" + producto.getProNombre()
                            + "¬" + (item.getDetCantidad() == null ? "B" : "D" + item.getDetCantidad())
                            + "¬" + (item.getParcialProducto() == null ? "B" : "D" + item.getParcialProducto())
                            + "¬" + (item.getDetObservaciones() == null ? "S" : "S" + item.getDetObservaciones())
                    );
                }

            }
        });
        respuesta.put("listaCabecera", listaCabecera);
        respuesta.put("listaCuerpo", listaCuerpo);
        return respuesta;
    }

    @Override
    public Map<String, Object> exportarPlantillaVentas(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception {
        List<InvVentasDetalleTO> listado = UtilsJSON.jsonToList(InvVentasDetalleTO.class, map.get("listado"));
        Map<String, Object> respuesta = new HashMap<>();
        List<String> listaCabecera = new ArrayList<>();
        List<String> listaCuerpo = new ArrayList<>();
        listaCuerpo.add("SARTICULO" + "¬" + "SNOMBRE" + "¬" + "SCANTIDAD" + "¬" + "SPRECIO" + "¬");
        listado.stream().forEach((item) -> {
            if (item.getProCodigoPrincipal() != null) {
                InvProducto producto = null;
                try {
                    producto = productoService.obtenerPorId(usuarioEmpresaReporteTO.getEmpCodigo(), item.getProCodigoPrincipal());
                } catch (Exception ex) {
                    Logger.getLogger(ReporteInventarioServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (producto != null) {
                    listaCuerpo.add(
                            "S" + item.getProCodigoPrincipal()
                            + "¬" + "S" + producto.getProNombre()
                            + "¬" + (item.getDetCantidad() == null ? "B" : "D" + item.getDetCantidad())
                            + "¬" + (item.getDetPrecio() == null ? "B" : "D" + item.getDetPrecio())
                    );
                }

            }
        });
        respuesta.put("listaCabecera", listaCabecera);
        respuesta.put("listaCuerpo", listaCuerpo);
        return respuesta;
    }

    @Override
    public Map<String, Object> exportarPlantillaProveedores(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception {
        Map<String, Object> respuesta = new HashMap<>();
        List<String> listaCabecera = new ArrayList<>();
        List<String> listaCuerpo = new ArrayList<>();

        listaCuerpo.add("STIPO IDENTIFICACION" + "¬" + "SNUMERO IDENTIFICACION" + "¬" + "STIPO CONTRIBUYENTE" + "¬" + "SRAZON SOCIAL" + "¬"
                + "SNOMBRE COMERCIAL" + "¬" + "STELEFONO CONVENCIONAL" + "¬" + "STELEFONO CELULAR" + "¬"
                + "SEMAIL" + "¬" + "SPROVINCIA" + "¬" + "SCIUDAD" + "¬" + "SDIRECCION" + "¬");

        respuesta.put("listaCabecera", listaCabecera);
        respuesta.put("listaCuerpo", listaCuerpo);
        return respuesta;
    }

    @Override
    public Map<String, Object> exportarReporteClienteContratos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvFunListadoClientesTO> listado) throws Exception {

        Map<String, Object> respuesta = new HashMap<>();
        List<String> listaCabecera = new ArrayList<>();
        List<String> listaCuerpo = new ArrayList<>();
        listaCuerpo.add("SNUMERO_CONTRATO" + "¬" + "SIDENTIFICACION" + "¬" + "SRAZON_SOCIAL" + "¬"
                + "SESTABLECIMIENTO" + "¬" + "SPUNTO_EMISION" + "¬" + "SBODEGA" + "¬"
                + "STIPO_CONTRATO" + "¬" + "STIPO_PLAN" + "¬" + "SIP" + "¬"
                + "SDISTANCIA" + "¬" + "SFECHA_CONSUMO" + "¬" + "SCOMPARTICION" + "¬"
                + "SDOWNLINK" + "¬" + "SUPLINK" + "¬" + "STARIFA");

        if (listado != null && listado.size() > 0) {
            for (InvFunListadoClientesTO cliente : listado) {//Filas
                if (cliente.getCliId() != null && !cliente.getCliId().equals("")) {
                    try {
                        String fila = "";
                        InvClienteTO cienteTO = clienteService.obtenerClienteTO(usuarioEmpresaReporteTO.getEmpCodigo(), cliente.getCliCodigo());
                        //***filas***
                        fila += ("B") + "¬";
                        fila += (cliente.getCliId() == null ? "B" : "S" + cliente.getCliId().trim()) + "¬";
                        fila += (cliente.getCliRazonSocial() == null ? "B" : "S" + cliente.getCliRazonSocial().trim()) + "¬";

                        fila += (cienteTO.getCliCodigoEstablecimiento() == null ? "B" : "S" + cienteTO.getCliCodigoEstablecimiento().trim()) + "¬";
                        fila += ("B") + "¬";
                        fila += ("B") + "¬";

                        fila += ("B") + "¬";
                        fila += ("B") + "¬";
                        fila += (cliente.getCliIp() == null ? "B" : "S" + cliente.getCliIp()) + "¬";

                        fila += ("B") + "¬";
                        fila += ("B") + "¬";
                        fila += ("B") + "¬";

                        fila += ("B") + "¬";
                        fila += ("B") + "¬";
                        fila += ("B") + "¬";
                        //***********
                        List<InvClientesVentasDetalle> detalles = clientesVentasDetalleDao.listarInvClientesVentasDetalle(usuarioEmpresaReporteTO.getEmpCodigo(), cliente.getCliCodigo(), 0);
                        if (detalles != null && !detalles.isEmpty()) {
                            for (InvClientesVentasDetalle detalle : detalles) {
                                //***filas***
                                String filaRecurrente = "";
                                filaRecurrente += ("B") + "¬";
                                filaRecurrente += (cliente.getCliId() == null ? "B" : "S" + cliente.getCliId().trim()) + "¬";
                                filaRecurrente += (cliente.getCliRazonSocial() == null ? "B" : "S" + cliente.getCliRazonSocial().trim()) + "¬";

                                filaRecurrente += (detalle.getCliCodigoEstablecimiento() != null && !detalle.getCliCodigoEstablecimiento().equals("")
                                        ? "S" + detalle.getCliCodigoEstablecimiento()
                                        : (cienteTO.getCliCodigoEstablecimiento() != null && !cienteTO.getCliCodigoEstablecimiento().equals("")
                                        ? "S" + cienteTO.getCliCodigoEstablecimiento() : "B"))
                                        + "¬";
                                filaRecurrente += ("B") + "¬";//punto emision
                                filaRecurrente += (detalle.getBodCodigo() == null ? "B" : "S" + detalle.getBodCodigo().trim()) + "¬";//bodega

                                filaRecurrente += ("B") + "¬";
                                filaRecurrente += ("B") + "¬";
                                filaRecurrente += (cliente.getCliIp() == null ? "B" : "S" + cliente.getCliIp().trim()) + "¬";

                                filaRecurrente += ("B") + "¬";
                                filaRecurrente += ("B") + "¬";
                                filaRecurrente += ("B") + "¬";

                                filaRecurrente += ("B") + "¬";
                                filaRecurrente += ("B") + "¬";
                                filaRecurrente += ("B") + "¬";
                                //***********
                                listaCuerpo.add(filaRecurrente);
                            }
                        } else {
                            listaCuerpo.add(fila);
                        }
                    } catch (Exception ex) {
                    }
                }
            }
        }

        respuesta.put("listaCabecera", listaCabecera);
        respuesta.put("listaCuerpo", listaCuerpo);
        return respuesta;
    }

    @Override
    public byte[] generarReporteContratoCliente(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvClienteContratoTO> listadoContratos) throws Exception {
        return genericReporteService.generarReporte(modulo, "reportContratoCliente.jrxml", usuarioEmpresaReporteTO, new HashMap<String, Object>(), listadoContratos);
    }

    @Override
    public Map<String, Object> exportarReporteContrato(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvClienteContratoTO> listadoContratos) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de contrato");
            listaCabecera.add("S");
            listaCuerpo.add("SDescripción" + "¬" + "SFecha" + "¬" + "SId" + "¬" + "SCliente" + "¬" + "SEstablecimiento" + "¬" + "SPunto emisión" + "¬" + "SBodega" + "¬" + "SIp");
            for (InvClienteContratoTO registroContrato : listadoContratos) {
                listaCuerpo.add(
                        (registroContrato.getCliNumeroContrato() == null ? "B" : "S" + registroContrato.getCliNumeroContrato()) + "¬"
                        + (UtilsValidacion.fechaDate_String(registroContrato.getCliFechaConsumo()) == null ? "B" : "S" + UtilsValidacion.fechaDate_String(registroContrato.getCliFechaConsumo())) + "¬"
                        + (registroContrato.getCliIdNumero() == null ? "B" : "S" + registroContrato.getCliIdNumero()) + "¬"
                        + (registroContrato.getCliRazonSocial() == null ? "B" : "S" + registroContrato.getCliRazonSocial()) + "¬"
                        + (registroContrato.getCliEstablecimiento() == null ? "B" : "S" + registroContrato.getCliEstablecimiento()) + "¬"
                        + (registroContrato.getCliPuntoEmision() == null ? "B" : "S" + registroContrato.getCliPuntoEmision()) + "¬"
                        + (registroContrato.getBodCodigo() == null ? "B" : "S" + registroContrato.getBodCodigo() + " | " + registroContrato.getBodNombre()) + "¬"
                        + (registroContrato.getCliDireccionIp() == null ? "B" : "S" + registroContrato.getCliDireccionIp()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteComprativoClienteContratos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvClienteContratosTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de contrato");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SIdentificación" + "¬" + "SRazón social" + "¬" + "SCategoría" + "¬" + "SIp" + "¬" + "SCantidad de contratos" + "¬" + "SCantidad de items de venta" + "¬" + "SInactivo");
            for (InvClienteContratosTO registroContrato : listado) {
                listaCuerpo.add(
                        (registroContrato.getCliCodigo() == null ? "B" : "S" + registroContrato.getCliCodigo()) + "¬"
                        + (registroContrato.getCliId() == null ? "B" : "S" + registroContrato.getCliId()) + "¬"
                        + (registroContrato.getCliRazonSocial() == null ? "B" : "S" + registroContrato.getCliRazonSocial()) + "¬"
                        + (registroContrato.getCliCategoria() == null ? "B" : "S" + registroContrato.getCliCategoria()) + "¬"
                        + (registroContrato.getCliIp() == null ? "B" : "S" + registroContrato.getCliIp()) + "¬"
                        + (registroContrato.getCliContratos() == null ? "B" : "I" + registroContrato.getCliContratos()) + "¬"
                        + (registroContrato.getCliDetallesVenta() == null ? "B" : "S" + registroContrato.getCliDetallesVenta()) + "¬"
                        + (registroContrato.getCliInactivo() == true ? "BSi" : "SNo"));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

}
