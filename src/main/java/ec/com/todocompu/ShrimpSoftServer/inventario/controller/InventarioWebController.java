/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.controller;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CompraDetalleDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.dao.VentaDao;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.CompraElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.CompraReembolsoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.TipoComprobanteService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.TipoTransaccionService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.VentaElectronicaService;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ClientesVentasDetalleDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.VentasDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.report.ReporteInventarioService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.BodegaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ClienteCategoriaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ClienteContratoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ClienteService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ClientesDireccionesService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasDetalleImbService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasDetalleService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasFormaPagoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasMotivoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasNumeracionService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasProgramadasService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasRecepcionService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ConsumosDetalleService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ConsumosMotivoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ConsumosNumeracionService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ConsumosService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.GuiaInpService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.GuiaRemisionService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.InvClienteGrupoEmpresarialService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.InvClienteNotificacionesService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.InvProveedorTransportistaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.InvSeriesService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.InvTransportistaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.LiquidacionComprasService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.PedidosConfiguracionService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoCategoriaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoEtiquetasService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoMarcaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoMedidaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoPresentacionCajasService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoPresentacionUnidadesService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoSaldosService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoSubcategoriaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoTipoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProformasDetalleService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProformasMotivoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProformasService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProveedorCategoriaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProveedorService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.TransferenciasDetalleService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.TransferenciasMotivoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.TransferenciasService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VendedorService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VentaGuiaRemisionService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VentasDetalleService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VentasFormaCobroService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VentasFormaPagoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VentasMotivoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VentasNumeracionService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VentasService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.SistemaWebUtil;
import ec.com.todocompu.ShrimpSoftServer.util.service.ArchivoService;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.DetalleExportarFiltrado;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.RetornoTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfActivoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraReembolsoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaExportacionTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvAdjuntosComprasWebTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvAdjuntosProductosWebTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvBodegaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCategoriaClienteComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCategoriaComboProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCategoriaProveedorComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteGrupoEmpresarialTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteRecurrenteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClientesVentasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComboBodegaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCompraCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCompraMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCompraTotalesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDatosBasicoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDetalleImbTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasRecepcionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsultaCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsultaGuiaRemisionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosDatosAdjuntosWebTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosImportarExportarTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunComprasConsolidandoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunComprasProgramadasListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunComprasVsVentasTonelajeTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunConsumosConsolidandoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListaProductosImpresionPlacasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListadoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListadoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListadoProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunMovimientosSerieTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunUltimasComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasConsolidandoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasConsolidandoProductosCoberturaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasConsolidandoProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasVendedorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunVentasVsCostoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvGuiaRemisionDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvGuiaRemisionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvKardexTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaBodegasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaComprasFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaProformaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaTransferenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsumosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleConsumoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleProformasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleTransferenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosCambiarPrecioCantidadTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosCambiarPrecioTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaSecuencialesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListadoCobrosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListadoPagosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvMedidaComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvNumeracionCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvNumeracionConsumoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvNumeracionVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosConfiguracionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoDAOTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoFormulaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoMarcaComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoMarcaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoMedidaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionCajasComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionCajasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionUnidadesComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionUnidadesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoRelacionadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoSimpleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoSincronizarTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoTipoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoTipoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductosConErrorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorTransportistaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvSecuenciaComprobanteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvSerieTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciaMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransportistaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVendedorComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVendedorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaRetencionesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasDetalleProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasFormaCobroTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.ItemComprobanteElectronico;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.SaldoBodegaComprobacionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.SaldoBodegaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvAdjuntosCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvBodega;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteGrupoEmpresarial;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteGrupoEmpresarialPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientePK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientesDirecciones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalleImb;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasProgramadas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemision;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionInp;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProducto;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoCategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoEtiquetas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMarcaPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMedidaPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionCajasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionUnidadesPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoSubcategoria;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoSubcategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedor;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorTransportista;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransportistaPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVendedorPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentaGuiaRemision;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.DatoFunListaProductosImpresionPlaca;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.ReporteCompraDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.ReporteProformaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.ReporteVentaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.quartz.TO.InvComprasProgramadasTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.InformacionAdicional;
import ec.com.todocompu.ShrimpSoftUtils.web.ComboGenericoTO;
import ec.com.todocompu.ShrimpSoftUtils.web.RespuestaWebTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRutas;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.GuiaRutasService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.TipoContratoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VentasRecurrentesService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfActivos;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxProvinciaCantonTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaReembolsoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.ImportarProductos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteContratoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteContratosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContrato;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContratoDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContratoTipo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContratoTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sri.ws.autorizacion.RespuestaComprobante;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Developer4
 */
@RestController
@RequestMapping("/todocompuWS/inventarioWebController")
public class InventarioWebController {

    @Autowired
    private BodegaService bodegaService;
    @Autowired
    private GuiaInpService guiaInpService;
    @Autowired
    private ClienteCategoriaService clienteCategoriaService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ComprasService comprasService;
    @Autowired
    private ComprasDetalleService comprasDetalleService;
    @Autowired
    private ComprasFormaPagoService comprasFormaPagoService;
    @Autowired
    private ComprasProgramadasService comprasProgramadasService;
    @Autowired
    private ComprasMotivoService comprasMotivoService;
    @Autowired
    private ComprasNumeracionService comprasNumeracionService;
    @Autowired
    private ComprasRecepcionService comprasRecepcionService;
    @Autowired
    private ConsumosService consumosService;
    @Autowired
    private ConsumosNumeracionService consumosNumeracionService;
    @Autowired
    private ConsumosDetalleService consumosDetalleService;
    @Autowired
    private ConsumosMotivoService consumosMotivoService;
    @Autowired
    private ProductoCategoriaService productoCategoriaService;
    @Autowired
    private ProductoSubcategoriaService productoSubcategoriaService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private ProductoEtiquetasService productoEtiquetasService;
    @Autowired
    private ProductoMarcaService productoMarcaService;
    @Autowired
    private ProductoMedidaService productoMedidaService;
    @Autowired
    private ProductoPresentacionCajasService productoPresentacionCajasService;
    @Autowired
    private ProductoPresentacionUnidadesService productoPresentacionUnidadesService;
    @Autowired
    private ProductoTipoService productoTipoService;
    @Autowired
    private ProductoSaldosService productoSaldosService;
    @Autowired
    private ProformasService proformasService;
    @Autowired
    private ProformasDetalleService proformasDetalleService;
    @Autowired
    private ProformasMotivoService proformasMotivoService;
    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private ProveedorCategoriaService proveedorCategoriaService;
    @Autowired
    private TransferenciasService transferenciasService;
    @Autowired
    private TransferenciasMotivoService transferenciasMotivoService;
    @Autowired
    private TransferenciasDetalleService transferenciasDetalleService;
    @Autowired
    private VendedorService vendedorService;
    @Autowired
    private VentasService ventasService;
    @Autowired
    private VentasNumeracionService ventasNumeracionService;
    @Autowired
    private VentasDetalleService ventasDetalleService;
    @Autowired
    private VentasFormaPagoService ventasFormaPagoService;
    @Autowired
    private VentasMotivoService ventasMotivoService;
    @Autowired
    private ReporteInventarioService reporteInventarioService;
    @Autowired
    private ContableService contableService;
    @Autowired
    private PedidosConfiguracionService pedidosConfiguracionService;
    @Autowired
    private EnviarCorreoService envioCorreoService;
    @Autowired
    private InvClienteGrupoEmpresarialService invClienteGrupoEmpresarialService;
    @Autowired
    private ArchivoService archivoService;
    @Autowired
    private VentasFormaCobroService ventaFormaCobroService;
    @Autowired
    private TipoTransaccionService tipoTransaccionService;
    @Autowired
    private TipoComprobanteService tipoComprobanteService;
    @Autowired
    private CompraReembolsoService compraReembolsoService;
    @Autowired
    private VentasDao ventasDao;
    @Autowired
    private VentaDao ventaDao;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private ClientesVentasDetalleDao clientesVentasDetalleDao;
    @Autowired
    private CompraElectronicaService compraElectronicaService;
    @Autowired
    private VentaElectronicaService ventaElectronicaService;
    @Autowired
    private InvSeriesService invSeriesService;
    @Autowired
    private CompraDetalleDao compraDetalleDao;
    @Autowired
    private InvTransportistaService invTransportistaService;
    @Autowired
    private GuiaRemisionService guiaRemisionService;
    @Autowired
    private VentaGuiaRemisionService ventaGuiaRemisionService;
    @Autowired
    private LiquidacionComprasService liquidacionComprasService;
    @Autowired
    private InvClienteNotificacionesService invClienteNotificacionesService;
    @Autowired
    private ClientesDireccionesService clientesDireccionesService;
    @Autowired
    private ComprasDetalleImbService comprasDetalleImbService;
    @Autowired
    private InvProveedorTransportistaService invProveedorTransportistaService;
    @Autowired
    private GuiaRutasService guiaRutasService;
    @Autowired
    private ClienteContratoService clienteContratoService;
    @Autowired
    private TipoContratoService tipoContratoService;
    @Autowired
    private VentasRecurrentesService ventasRecurrentesService;
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;

    /*BODEGA*/
    @RequestMapping("/getBodegaTO")
    public RespuestaWebTO getBodegaTO(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        try {
            List<InvBodegaTO> respues = bodegaService.getBodegaTO(empresa, codigo);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues.get(0));
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaBodegasTO")
    public RespuestaWebTO getListaBodegasTO(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        boolean inactivo = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivo"));
        try {
            List<InvListaBodegasTO> respues = bodegaService.buscarBodegasTO(empresa, inactivo, busqueda);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaBodegasPorSector")
    public RespuestaWebTO getListaBodegasPorSector(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        boolean inactivo = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivo"));
        try {
            List<InvListaBodegasTO> respues = bodegaService.buscarBodegasPorSector(empresa, inactivo, sector);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron bodegas.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaBodegas")
    public RespuestaWebTO getListaBodegas(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean inactivo = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivo"));
        try {
            List<InvBodega> respues = bodegaService.getListaBodegas(empresa, inactivo);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarBodegaTO")
    public RespuestaWebTO insertarBodegaTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvBodegaTO invBodegaTO = UtilsJSON.jsonToObjeto(InvBodegaTO.class, map.get("invBodegaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = bodegaService.insertarInvBodegaTO(invBodegaTO, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La bodega: Código " + invBodegaTO.getBodCodigo() + ", se ha guardado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarBodegaTO")
    public RespuestaWebTO modificarBodegaTO(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvBodegaTO invBodegaTO = UtilsJSON.jsonToObjeto(InvBodegaTO.class, map.get("invBodegaTO"));
        try {
            String msje = bodegaService.modificarInvBodegaTO(invBodegaTO, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La bodega: Código " + invBodegaTO.getBodCodigo() + ", se ha modificado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoBodegaTO")
    public RespuestaWebTO modificarEstadoBodegaTO(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvBodegaTO invBodegaTO = UtilsJSON.jsonToObjeto(InvBodegaTO.class, map.get("invBodegaTO"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        try {
            String msje = bodegaService.modificarEstadoInvBodegaTO(invBodegaTO, estado, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La bodega: Código " + invBodegaTO.getBodCodigo() + (estado ? ", se ha inactivado correctamente." : ", se ha activado correctamente."));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarBodegaTO")
    public RespuestaWebTO eliminarBodegaTO(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvBodegaTO invBodegaTO = UtilsJSON.jsonToObjeto(InvBodegaTO.class, map.get("invBodegaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = bodegaService.eliminarInvBodegaTO(invBodegaTO, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La bodega: Código " + invBodegaTO.getBodCodigo() + ", se ha eliminado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteBodegas")
    public @ResponseBody
    String generarReporteBodegas(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportBodega.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvListaBodegasTO> listado = UtilsJSON.jsonToList(InvListaBodegasTO.class, parametros.get("ListadoBodegas"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteBodega(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteBodegas")
    public @ResponseBody
    String exportarReporteBodegas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvListaBodegasTO> listado = UtilsJSON.jsonToList(InvListaBodegasTO.class, map.get("ListadoBodegas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteBodegas(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*FORMA PAGO*/
    @RequestMapping("/getInvListaComprasFormaPagoTO")
    public RespuestaWebTO getInvListaComprasFormaPagoTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        try {
            List<InvListaComprasFormaPagoTO> respues = comprasFormaPagoService.getInvListaComprasFormaPagoTO(empresa);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaInvComprasFormaPagoTO")
    public RespuestaWebTO getInvListaInvComprasFormaPagoTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        boolean inactivos = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("inactivos"));
        try {
            List<InvComprasFormaPagoTO> respues = comprasFormaPagoService.getInvListaInvComprasFormaPagoTO(empresa, inactivos);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/accionInvComprasPagosForma")
    public RespuestaWebTO accionInvComprasPagosForma(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasFormaPagoTO invComprasFormaPagoTO = UtilsJSON.jsonToObjeto(InvComprasFormaPagoTO.class, map.get("invComprasFormaPagoTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));//I,M,E
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = comprasFormaPagoService.accionInvComprasPagosForma(invComprasFormaPagoTO, accion, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(accion == 'I' ? comprasFormaPagoService.getInvComprasFormaPagoTO(invComprasFormaPagoTO.getUsrEmpresa(), invComprasFormaPagoTO.getCtaCodigo()) : true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoInvComprasPagosForma")
    public RespuestaWebTO modificarEstadoInvComprasPagosForma(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvComprasFormaPagoTO invComprasFormaPagoTO = UtilsJSON.jsonToObjeto(InvComprasFormaPagoTO.class, map.get("invComprasFormaPagoTO"));
        try {
            String msje = comprasFormaPagoService.modificarEstadoInvComprasPagosForma(invComprasFormaPagoTO, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                resp.setOperacionMensaje(e.getMessage());
            } else {
                resp.setOperacionMensaje("FLo sentimos, ocurrió un error inesperado!");
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteFormaPago")
    public @ResponseBody
    String generarReporteFormaPago(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportFormaPago.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvComprasFormaPagoTO> listado = UtilsJSON.jsonToList(InvComprasFormaPagoTO.class, parametros.get("ListadoFormaPago"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteFormaPago(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteFormaPago")
    public @ResponseBody
    String exportarReporteFormaPago(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvComprasFormaPagoTO> listado = UtilsJSON.jsonToList(InvComprasFormaPagoTO.class, map.get("ListadoFormaPago"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteFormaPago(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*FORMA COBRO*/
    @RequestMapping("/getListaInvVentasFormaCobroTO")
    public RespuestaWebTO getListaInvVentasFormaPagoTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        boolean inactivos = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("inactivos"));
        try {
            List<InvVentasFormaCobroTO> respues = ventaFormaCobroService.getListaInvVentasFormaCobroTO(empresa, inactivos);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
            return resp;
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                resp.setOperacionMensaje(e.getMessage());
            } else {
                resp.setOperacionMensaje("FLo sentimos, ocurrió un error inesperado!");
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return resp;
    }

    @RequestMapping("/accionInvVentasFormaCobro")
    public RespuestaWebTO accionInvVentasPagosForma(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvVentasFormaCobroTO invVentasFormaCobroTO = UtilsJSON.jsonToObjeto(InvVentasFormaCobroTO.class, map.get("invVentasFormaCobroTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));//I,M,E
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = ventaFormaCobroService.accionInvVentasFormaCobroTO(invVentasFormaCobroTO, accion, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(accion == 'I' ? ventaFormaCobroService.getInvVentasFormaCobroTO(invVentasFormaCobroTO.getUsrEmpresa(), invVentasFormaCobroTO.getCtaCodigo()) : true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoInvVentasFormaCobroTO")
    public RespuestaWebTO modificarEstadoInvVentasFormaPagoTO(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvVentasFormaCobroTO invVentasFormaCobroTO = UtilsJSON.jsonToObjeto(InvVentasFormaCobroTO.class, map.get("invVentasFormaCobroTO"));
        try {
            String msje = ventaFormaCobroService.modificarEstadoInvVentasFormaCobroTO(invVentasFormaCobroTO, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteFormaCobro")
    public @ResponseBody
    String generarReporteFormaCobro(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportFormaPago.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvVentasFormaCobroTO> listado = UtilsJSON.jsonToList(InvVentasFormaCobroTO.class, parametros.get("ListadoFormaCobro"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteFormaCobro(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteFormaCobro")
    public @ResponseBody
    String exportarReporteFormaCobro(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvVentasFormaCobroTO> listado = UtilsJSON.jsonToList(InvVentasFormaCobroTO.class, map.get("ListadoFormaCobro"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteFormaCobro(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*GRUPO EMPRESARIAL*/
    @RequestMapping("/listarInvClienteGrupoEmpresarial")
    public RespuestaWebTO listarInvClienteGrupoEmpresarial(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String busqueda = SistemaWebUtil.obtenerFiltroComoString(parametros, "busqueda");
        try {
            List<InvClienteGrupoEmpresarialTO> respues = invClienteGrupoEmpresarialService.getInvClienteGrupoEmpresarialTO(empresa, busqueda);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron grupos empresariales.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarInvClienteGrupoEmpresarial")
    public RespuestaWebTO insertarInvClienteGrupoEmpresarial(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvClienteGrupoEmpresarial invClienteGrupoEmpresarial = UtilsJSON.jsonToObjeto(InvClienteGrupoEmpresarial.class, map.get("invClienteGrupoEmpresarial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvClienteGrupoEmpresarial grupo = invClienteGrupoEmpresarialService.insertarInvClienteGrupoEmpresarial(invClienteGrupoEmpresarial, sisInfoTO);
            if (grupo != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El grupo empresarial:Código " + grupo.getInvClienteGrupoEmpresarialPK().getGeCodigo() + ", se ha guardado correctamente.");
                resp.setExtraInfo(grupo);
            } else {
                resp.setOperacionMensaje("No se ha guardado el grupo empresarial debido a que ya existe.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            resp.setOperacionMensaje(e.getMessage());
        }
        return resp;
    }

    @RequestMapping("/modificarInvClienteGrupoEmpresarial")
    public RespuestaWebTO modificarInvClienteGrupoEmpresarial(@RequestBody String json) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvClienteGrupoEmpresarial invClienteGrupoEmpresarial = UtilsJSON.jsonToObjeto(InvClienteGrupoEmpresarial.class, map.get("invClienteGrupoEmpresarial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvClienteGrupoEmpresarial grupo = invClienteGrupoEmpresarialService.modificarInvClienteGrupoEmpresarial(invClienteGrupoEmpresarial, sisInfoTO);
            if (grupo != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El grupo empresarial:Código " + grupo.getInvClienteGrupoEmpresarialPK().getGeCodigo() + ", se ha modificado correctamente.");
                resp.setExtraInfo(grupo);
            } else {
                resp.setOperacionMensaje("No se ha modificado el grupo empresarial debido a que ya existe.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarInvClienteGrupoEmpresarial")
    public RespuestaWebTO eliminarInvClienteGrupoEmpresarial(@RequestBody String json) throws GeneralException, DataIntegrityViolationException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvClienteGrupoEmpresarialPK pk = UtilsJSON.jsonToObjeto(InvClienteGrupoEmpresarialPK.class, map.get("pk"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean grupo = invClienteGrupoEmpresarialService.eliminarInvClienteGrupoEmpresarial(pk, sisInfoTO);
            if (grupo) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El grupo empresarial:Código " + pk.getGeCodigo() + ", se ha eliminado correctamente.");
                resp.setExtraInfo(grupo);
            } else {
                resp.setOperacionMensaje("No se ha eliminado el grupo empresarial.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (DataIntegrityViolationException ge) {
            resp.setOperacionMensaje("No se ha eliminado. El grupo empresarial tiene movimientos.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteInvClienteGrupoEmpresarial")
    public @ResponseBody
    String generarReporteInvClienteGrupoEmpresarial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombreReporte = "reporteInvClienteGrupoEmpresarial.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<InvClienteGrupoEmpresarial> listado = UtilsJSON.jsonToList(InvClienteGrupoEmpresarial.class,
                map.get("listadoInvClienteGrupoEmpresarial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = null;
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteInvClienteGrupoEmpresarial")
    public @ResponseBody
    String generarReporteExcelInvClienteGrupoEmpresarial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombreReporte = "reporteInvClienteGrupoEmpresarial.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<InvClienteGrupoEmpresarial> listado = UtilsJSON.jsonToList(InvClienteGrupoEmpresarial.class,
                map.get("listadoInvClienteGrupoEmpresarial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = null;
            return archivoService.generarReporteEXCEL(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*MOTIVO DE COMPRAS*/
    @RequestMapping("/getListaInvComprasMotivoTO")
    public RespuestaWebTO getListaInvComprasMotivoTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        boolean soloActivos = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("activos"));
        try {
            List<InvComprasMotivoTO> respues = comprasMotivoService.getListaInvComprasMotivoTO(empresa, soloActivos);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarInvComprasMotivoTO")
    public RespuestaWebTO insertarInvComprasMotivoTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasMotivoTO invCompraMotivoTO = UtilsJSON.jsonToObjeto(InvComprasMotivoTO.class, map.get("invCompraMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = comprasMotivoService.insertarInvComprasMotivoTO(invCompraMotivoTO, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarInvComprasMotivoTO")
    public RespuestaWebTO modificarInvComprasMotivoTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasMotivoTO invCompraMotivoTO = UtilsJSON.jsonToObjeto(InvComprasMotivoTO.class, map.get("invCompraMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = comprasMotivoService.modificarInvComprasMotivoTO(invCompraMotivoTO, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoInvComprasMotivoTO")
    public RespuestaWebTO modificarEstadoInvComprasMotivoTO(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvComprasMotivoTO invComprasMotivoTO = UtilsJSON.jsonToObjeto(InvComprasMotivoTO.class, map.get("invCompraMotivoTO"));
        try {
            String msje = comprasMotivoService.modificarEstadoInvComprasMotivoTO(invComprasMotivoTO, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarInvComprasMotivoTO")
    public RespuestaWebTO eliminarInvComprasMotivoTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasMotivoTO invCompraMotivoTO = UtilsJSON.jsonToObjeto(InvComprasMotivoTO.class, map.get("invCompraMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = comprasMotivoService.eliminarInvComprasMotivoTO(invCompraMotivoTO, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteMotivoCompra")
    public @ResponseBody
    String generarReporteMotivoCompra(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportMotivoCompra.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvComprasMotivoTO> listado = UtilsJSON.jsonToList(InvComprasMotivoTO.class, parametros.get("ListadoMotivo"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteMotivoCompra(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteMotivoCompra")
    public @ResponseBody
    String exportarReporteMotivoCompra(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvComprasMotivoTO> listado = UtilsJSON.jsonToList(InvComprasMotivoTO.class, map.get("ListadoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteMotivoCompra(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*MOTIVO DE VENTAS*/
    @RequestMapping("/getListaInvVentasMotivoTO")
    public RespuestaWebTO getListaInvVentasMotivoTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        boolean soloActivos = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("activos"));
        String tipodocumento = UtilsJSON.jsonToObjeto(String.class, parametros.get("tipodocumento"));
        try {
            List<InvVentaMotivoTO> respues = ventasMotivoService.getListaInvVentasMotivoTO(empresa, soloActivos, tipodocumento);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
            return resp;
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarInvVentasMotivoTO")
    public RespuestaWebTO insertarInvVentasMotivoTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvVentaMotivoTO invVentaMotivoTO = UtilsJSON.jsonToObjeto(InvVentaMotivoTO.class, map.get("invVentaMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = ventasMotivoService.insertarInvVentasMotivoTO(invVentaMotivoTO, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarInvVentasMotivoTO")
    public RespuestaWebTO modificarInvVentasMotivoTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvVentaMotivoTO invVentaMotivoTO = UtilsJSON.jsonToObjeto(InvVentaMotivoTO.class, map.get("invVentaMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = ventasMotivoService.modificarInvVentasMotivoTO(invVentaMotivoTO, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoInvVentaMotivoTO")
    public RespuestaWebTO modificarEstadoInvVentaMotivoTO(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvVentaMotivoTO invVentaMotivoTO = UtilsJSON.jsonToObjeto(InvVentaMotivoTO.class, map.get("invVentaMotivoTO"));
        try {
            String msje = ventasMotivoService.modificarEstadoInvVentaMotivoTO(invVentaMotivoTO, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarInvVentaMotivoTO")
    public RespuestaWebTO eliminarInvVentaMotivoTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvVentaMotivoTO invVentaMotivoTO = UtilsJSON.jsonToObjeto(InvVentaMotivoTO.class, map.get("invVentaMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = ventasMotivoService.eliminarInvVentasMotivoTO(invVentaMotivoTO, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteMotivoVenta")
    public @ResponseBody
    String generarReporteMotivoVenta(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportMotivoVenta.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvVentaMotivoTO> listado = UtilsJSON.jsonToList(InvVentaMotivoTO.class, parametros.get("ListadoMotivo"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteMotivoVenta(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteMotivoVenta")
    public @ResponseBody
    String exportarReporteMotivoVenta(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvVentaMotivoTO> listado = UtilsJSON.jsonToList(InvVentaMotivoTO.class, map.get("ListadoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteMotivoVenta(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*CATEGORIA CLIENTE*/
    @RequestMapping("/getInvClienteCategoriaTO")
    public RespuestaWebTO getInvClienteCategoriaTO(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvClienteCategoriaTO> respues = clienteCategoriaService.getInvClienteCategoriaTO(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron categorías de cliente.");
            }
            return resp;
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/accionInvClienteCategoria")
    public RespuestaWebTO accionInvClienteCategoria(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvClienteCategoriaTO invClienteCategoriaTO = UtilsJSON.jsonToObjeto(InvClienteCategoriaTO.class, map.get("invClienteCategoriaTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));//I,M,E
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = clienteCategoriaService.accionInvClienteCategoria(invClienteCategoriaTO, accion, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteCategoriaCliente")
    public @ResponseBody
    String generarReporteCategoriaCliente(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportCategoriaCliente.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvClienteCategoriaTO> listado = UtilsJSON.jsonToList(InvClienteCategoriaTO.class, parametros.get("ListadoCategorias"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteCategoriaCliente(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCategoriaCliente")
    public @ResponseBody
    String exportarReporteCategoriaCliente(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvClienteCategoriaTO> listado = UtilsJSON.jsonToList(InvClienteCategoriaTO.class, map.get("ListadoCategorias"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteCategoriaCliente(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*SUBCATEGORIA PRODUCTO*/
    @RequestMapping("/listarSubcategoriasProducto")
    public RespuestaWebTO listarSubcategoriasProducto(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvProductoSubcategoria> respues = productoSubcategoriaService.listarSubcategoriasProducto(empresa);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
            return resp;
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarInvSubCategoriaProducto")
    public RespuestaWebTO insertarInvSubCategoriaProducto(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoSubcategoria invProductoSubcategoria = UtilsJSON.jsonToObjeto(InvProductoSubcategoria.class, map.get("invProductoSubcategoria"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvProductoSubcategoria rpta = productoSubcategoriaService.insertarInvSubCategoriaProducto(invProductoSubcategoria, sisInfoTO);
            if (rpta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La subcategoría: Código " + rpta.getInvProductoSubcategoriaPK().getScatCodigo() + ", se ha guardado correctamente");
                resp.setExtraInfo(rpta);
            } else {
                resp.setOperacionMensaje("La subcategoría no se ha guardado correctamente");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarInvSubCategoriaProducto")
    public RespuestaWebTO modificarInvSubCategoriaProducto(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoSubcategoria invProductoSubcategoria = UtilsJSON.jsonToObjeto(InvProductoSubcategoria.class, map.get("invProductoSubcategoria"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvProductoSubcategoria rpta = productoSubcategoriaService.modificarInvSubCategoriaProducto(invProductoSubcategoria, sisInfoTO);
            if (rpta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La subcategoría: Código " + rpta.getInvProductoSubcategoriaPK().getScatCodigo() + ", se ha modificado correctamente");
                resp.setExtraInfo(rpta);
            } else {
                resp.setOperacionMensaje("La subcategoría no se ha modificado correctamente");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarInvSubCategoriaProducto")
    public RespuestaWebTO eliminarInvSubCategoriaProducto(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoSubcategoriaPK invProductoSubcategoriaPK = UtilsJSON.jsonToObjeto(InvProductoSubcategoriaPK.class, map.get("invProductoSubcategoriaPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean rpta = productoSubcategoriaService.eliminarInvSubCategoriaProducto(invProductoSubcategoriaPK, sisInfoTO);
            if (rpta) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La subcategoría: Código " + invProductoSubcategoriaPK.getScatCodigo() + ", se ha eliminado correctamente");
                resp.setExtraInfo(rpta);
            } else {
                resp.setOperacionMensaje("La subcategoría no se ha eliminado correctamente");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*CATEGORIA PROVEEDOR*/
    @RequestMapping("/getInvProveedorCategoriaTO")
    public RespuestaWebTO getInvProveedorCategoriaTO(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvProveedorCategoriaTO> respues = proveedorCategoriaService.getInvProveedorCategoriaTO(empresa);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
            return resp;
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/accionInvProveedorCategoria")
    public RespuestaWebTO accionInvProveedorCategoria(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProveedorCategoriaTO invProveedorCategoriaTO = UtilsJSON.jsonToObjeto(InvProveedorCategoriaTO.class, map.get("invProveedorCategoriaTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));//I,M,E
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = proveedorCategoriaService.accionInvProveedorCategoria(invProveedorCategoriaTO, accion, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteCategoriaProveedor")
    public @ResponseBody
    String generarReporteCategoriaProveedor(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportCategoriaProveedor.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvProveedorCategoriaTO> listado = UtilsJSON.jsonToList(InvProveedorCategoriaTO.class, parametros.get("ListadoCategorias"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteCategoriaProveedor(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCategoriaProveedor")
    public @ResponseBody
    String exportarReporteCategoriaProveedor(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvProveedorCategoriaTO> listado = UtilsJSON.jsonToList(InvProveedorCategoriaTO.class, map.get("ListadoCategorias"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteCategoriaProveedor(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*PRODUCTO*/
    @RequestMapping("/getInvProductoTO")
    public RespuestaWebTO getInvProducto(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String busqueda = SistemaWebUtil.obtenerFiltroComoString(parametros, "busqueda");
        String categoria = SistemaWebUtil.obtenerFiltroComoString(parametros, "categoria");
        boolean incluirInactivos = SistemaWebUtil.obtenerFiltroComoBoolean(parametros, "incluirInactivos");
        boolean limite = SistemaWebUtil.obtenerFiltroComoBoolean(parametros, "limite");
        try {
            List<InvProductoSimpleTO> respues = productoService.listarProductos(empresa, busqueda, categoria, incluirInactivos, limite);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerInvClientePorCedulaRuc")
    public RespuestaWebTO obtenerInvClientePorCedulaRuc(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cedulaRuc = UtilsJSON.jsonToObjeto(String.class, map.get("cedulaRuc"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvCliente respues = clienteService.obtenerInvClientePorCedulaRuc(empresa, cedulaRuc);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron cliente por cedula ruc.");
            }
            return resp;
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerInvProveedorPorCedulaRuc")
    public InvProveedor obtenerInvProveedorPorCedulaRuc(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String ruc = UtilsJSON.jsonToObjeto(String.class, map.get("ruc"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proveedorService.obtenerInvProveedorPorCedulaRuc(empresa, ruc);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*INVENTARIO PRODUCTO CATEGORIA*/
    @RequestMapping("/accionInvProductoCategoria")
    public RespuestaWebTO accionInvProductoCategoria(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        resp.setOperacionMensaje("La operación no obtuvo ninguna respuesta");
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoCategoriaTO invProductoCategoriaTO = UtilsJSON.jsonToObjeto(InvProductoCategoriaTO.class, map.get("invProductoCategoriaTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = productoCategoriaService.accionInvProductoCategoria(invProductoCategoriaTO, accion, sisInfoTO);
            if (respues.length() > 0 && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues.substring(1));
                resp.setOperacionMensaje(respues.substring(1));
            } else if (respues.length() > 0) {
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarInvProductoCategoria")
    public RespuestaWebTO eliminarInvProductoCategoria(@RequestBody String json) throws GeneralException, Exception, DataIntegrityViolationException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        resp.setOperacionMensaje("La operación no obtuvo ninguna respuesta");
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvProductoCategoriaPK invProductoCategoriaPK = UtilsJSON.jsonToObjeto(InvProductoCategoriaPK.class, map.get("invProductoCategoriaPK"));
        try {
            boolean respues = productoCategoriaService.eliminarInvProductoCategoria(invProductoCategoriaPK, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La categoría de producto:Código " + invProductoCategoriaPK.getCatCodigo() + ", se ha eliminado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se ha eliminado categoría de producto.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (DataIntegrityViolationException dive) {
            resp.setOperacionMensaje("No se ha eliminado. La categoría de producto tiene movimientos.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getInvProductoCategoriaTO")
    public RespuestaWebTO getInvProductoCategoriaTO(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvProductoCategoriaTO> respues = productoCategoriaService.getInvProductoCategoriaTO(empresa);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteInvProductoCategoriaTO")
    public @ResponseBody
    String generarReporteInvProductoCategoriaTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombreReporte = "reportProductoCategoria.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvProductoCategoriaTO> listaInvProductoCategoriaTO = UtilsJSON.jsonToList(InvProductoCategoriaTO.class, map.get("listadoInvProductoCategoriaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteInvProductoCategoriaTO(usuarioEmpresaReporteTO, listaInvProductoCategoriaTO, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteInvProductoCategoriaTO")
    public @ResponseBody
    String exportarReporteInvProductoCategoriaTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvProductoCategoriaTO> listaInvProductoCategoriaTO = UtilsJSON.jsonToList(InvProductoCategoriaTO.class, map.get("listadoInvProductoCategoriaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteInvProductoCategoriaTO(usuarioEmpresaReporteTO, listaInvProductoCategoriaTO);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*IMAGENES DE COMPRA*/
    @RequestMapping("/obtenerAdjuntosCompra")
    public RespuestaWebTO obtenerAdjuntosCompra(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvComprasDatosBasicoTO datosBasicosCompra = comprasService.getDatosBasicosCompra(conContablePK);
        InvComprasPK invComprasPK = new InvComprasPK(datosBasicosCompra.getCompEmpresa(), datosBasicosCompra.getCompPeriodo(), datosBasicosCompra.getCompMotivo(), datosBasicosCompra.getCompNumero());
        try {
            List<InvAdjuntosCompras> respues = comprasService.convertirStringUTF8(invComprasPK);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron imágenes de compra.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*IMAGENES DE COMPRA LISTADO*/
    @RequestMapping("/obtenerAdjuntosCompraListado")
    public RespuestaWebTO obtenerAdjuntosCompraListado(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        InvComprasPK invComprasPK = new InvComprasPK(empresa, periodo, motivo, numero);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvAdjuntosCompras> respues = comprasService.convertirStringUTF8(invComprasPK);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron imágenes de compra.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*  INVENTARIO PRODUCTO MEDIDA */
    @RequestMapping("/accionInvProductoMedida")
    public RespuestaWebTO accionInvProductoMedida(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        resp.setOperacionMensaje("La operación no obtuvo ninguna respuesta");
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoMedidaTO invProductoMedidaTO = UtilsJSON.jsonToObjeto(InvProductoMedidaTO.class, map.get("invProductoMedidaTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = productoMedidaService.accionInvProductoMedida(invProductoMedidaTO, accion, sisInfoTO);
            if (respues.length() > 0 && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues.substring(1));
                resp.setOperacionMensaje(respues.substring(1));
            } else if (respues.length() > 0) {
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarInvProductoMedida")
    public RespuestaWebTO eliminarInvProductoMedida(@RequestBody String json) throws GeneralException, Exception, DataIntegrityViolationException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        resp.setOperacionMensaje("La operación no obtuvo ninguna respuesta");
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvProductoMedidaPK invProductoMedidaPK = UtilsJSON.jsonToObjeto(InvProductoMedidaPK.class, map.get("invProductoMedidaPK"));
        try {
            boolean respues = productoMedidaService.eliminarInvProductoMedida(invProductoMedidaPK, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La medida producto:Código " + invProductoMedidaPK.getMedCodigo() + ", se ha eliminado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se ha eliminado medida de producto");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (DataIntegrityViolationException dive) {
            resp.setOperacionMensaje("No se ha eliminado. La medida de producto tiene movimientos.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getInvProductoMedidaTO")
    public RespuestaWebTO getInvProductoMedidaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvProductoMedidaTO> respues = productoMedidaService.getInvProductoMedidaTO(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteInvProductoMedidaTO")
    public @ResponseBody
    String generarReporteInvProductoMedidaTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String nombreReporte = "reportProductoUnidadMedida.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvProductoMedidaTO> listaInvProductoMedidaTO = UtilsJSON.jsonToList(InvProductoMedidaTO.class, map.get("listadoInvProductoMedidaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteInvProductoMedidaTO(usuarioEmpresaReporteTO, listaInvProductoMedidaTO, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteInvProductoMedidaTO")
    public @ResponseBody
    String exportarReporteInvProductoMedidaTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvProductoMedidaTO> listado = UtilsJSON.jsonToList(InvProductoMedidaTO.class, map.get("listadoInvProductoMedidaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteInvProductoMedidaTO(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*  PRODUCTO */
    @RequestMapping("/buscarInvProductoDAOTO")
    public RespuestaWebTO buscarInvProductoDAOTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoProducto = UtilsJSON.jsonToObjeto(String.class, map.get("codigoProducto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvProductoDAOTO respues = productoService.buscarInvProductoDAOTO(empresa, codigoProducto);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontró datos adicionales del producto seleccionado.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarProductoTO")
    public RespuestaWebTO insertarProductoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        resp.setOperacionMensaje("La operación no obtuvo ninguna respuesta.");
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoTO invProductoTO = UtilsJSON.jsonToObjeto(InvProductoTO.class, map.get("invProductoTO"));
        List<InvAdjuntosProductosWebTO> listadoImagenes = UtilsJSON.jsonToList(InvAdjuntosProductosWebTO.class, map.get("listaImagenes"));
        AfActivoTO afActivoTO = UtilsJSON.jsonToObjeto(AfActivoTO.class, map.get("afActivoTO"));
        List<InvProductoRelacionadoTO> listaInvProductoRelacionados = UtilsJSON.jsonToList(InvProductoRelacionadoTO.class, map.get("listaInvProductoRelacionados"));
        List<InvProductoFormulaTO> listaInvProductoFormulaTO = UtilsJSON.jsonToList(InvProductoFormulaTO.class, map.get("listaInvProductoFormulaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = productoService.insertarInvProductoTO(invProductoTO, afActivoTO, listadoImagenes, listaInvProductoRelacionados, listaInvProductoFormulaTO, sisInfoTO);
            if (respuesta != null) {
                String mensaje = UtilsJSON.jsonToObjeto(String.class, respuesta.get("mensaje"));
                if (mensaje.length() > 0 && mensaje.charAt(0) == 'T') {
                    InvProducto invProducto = UtilsJSON.jsonToObjeto(InvProducto.class, respuesta.get("invProducto"));
                    String codigoPrincipal = invProducto.getInvProductoPK().getProCodigoPrincipal();
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(codigoPrincipal);
                    resp.setOperacionMensaje("El producto: Código " + codigoPrincipal + ", se ha guardado correctamente.");
                } else if (mensaje.length() > 0 && mensaje.substring(1).equals("Cuenta invalidas")) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ERROR.getValor());
                    resp.setExtraInfo(respuesta);
                } else {
                    resp.setOperacionMensaje(mensaje.substring(1));
                }
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ERROR.getValor());
                resp.setExtraInfo("Error al validar cuentas");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/productoRepetidoCodigoBarra")
    public RespuestaWebTO productoRepetidoCodigoBarra(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String barras = UtilsJSON.jsonToObjeto(String.class, map.get("barras"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = productoService.productoRepetidoCodigoBarra(empresa, barras);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("El producto está repetido");
            } else {
                resp.setOperacionMensaje("El producto no está repetido");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarProductoTO")
    public RespuestaWebTO modificarProductoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoTO invProductoTO = UtilsJSON.jsonToObjeto(InvProductoTO.class, map.get("invProductoTO"));
        List<InvAdjuntosProductosWebTO> listadoImagenesElimados = UtilsJSON.jsonToList(InvAdjuntosProductosWebTO.class, map.get("listaImagenesEliminar"));
        List<InvAdjuntosProductosWebTO> listadoImagenesInsertar = UtilsJSON.jsonToList(InvAdjuntosProductosWebTO.class, map.get("listaImagenesInsertar"));
        String codigoCambiarLlave = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCambiarLlave"));
        AfActivoTO afActivoTO = UtilsJSON.jsonToObjeto(AfActivoTO.class, map.get("afActivoTO"));
        List<InvProductoFormulaTO> listaInvProductoFormulaTO = UtilsJSON.jsonToList(InvProductoFormulaTO.class, map.get("listaInvProductoFormulaTO"));
        List<InvProductoFormulaTO> listaInvProductoFormulaTOEliminar = UtilsJSON.jsonToList(InvProductoFormulaTO.class, map.get("listaInvProductoFormulaTOEliminar"));
        List<InvProductoRelacionadoTO> listaInvProductoRelacionados = UtilsJSON.jsonToList(InvProductoRelacionadoTO.class, map.get("listaInvProductoRelacionados"));
        List<InvProductoRelacionadoTO> listaInvProductoRelacionadosEliminar = UtilsJSON.jsonToList(InvProductoRelacionadoTO.class, map.get("listaInvProductoRelacionadosEliminar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = productoService.modificarInvProductoTO(invProductoTO, codigoCambiarLlave, afActivoTO, listadoImagenesInsertar, listadoImagenesElimados, listaInvProductoRelacionados,
                    listaInvProductoRelacionadosEliminar, listaInvProductoFormulaTO, listaInvProductoFormulaTOEliminar, sisInfoTO);
            if (respuesta != null) {
                String mensaje = UtilsJSON.jsonToObjeto(String.class, respuesta.get("mensaje"));
                if (mensaje.length() > 0 && mensaje.charAt(0) == 'T') {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(productoService.obtenerInvFunListadoProductosTO(invProductoTO.getCatEmpresa(), invProductoTO.getProCodigoPrincipal()));
                    resp.setOperacionMensaje("El producto: Código " + invProductoTO.getProCodigoPrincipal() + ", se ha modificado correctamente.");
                } else if (mensaje.length() > 0 && mensaje.substring(1).equals("Cuenta invalidas")) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ERROR.getValor());
                    resp.setExtraInfo(respuesta);
                } else {
                    resp.setOperacionMensaje(mensaje.substring(1));
                }
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ERROR.getValor());
                resp.setExtraInfo("Error al validar cuentas");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarInvProducto")
    public RespuestaWebTO eliminarInvProducto(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoPK pk = UtilsJSON.jsonToObjeto(InvProductoPK.class, map.get("invProductoPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = productoService.eliminarInvProducto(pk, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("El producto: Código: " + pk.getProCodigoPrincipal() + ", se ha eliminado correctamente.");
            } else {
                resp.setOperacionMensaje("No se ha eliminado producto.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (DataIntegrityViolationException ge) {
            resp.setOperacionMensaje("No se ha eliminado. El producto tiene movimientos.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/cambiarEstadoInvProducto")
    public RespuestaWebTO cambiarEstadoInvProducto(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoPK pk = UtilsJSON.jsonToObjeto(InvProductoPK.class, map.get("invProductoPK"));
        boolean estado = UtilsJSON.jsonToObjeto(Boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = productoService.modificarEstadoInvProducto(pk, estado, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("El estado de producto: Código: " + pk.getProCodigoPrincipal() + ", se ha modificado correctamente.");
            } else {
                resp.setOperacionMensaje("No se ha modificado el estado del producto.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/activarEcommerceInvProducto")
    public RespuestaWebTO activarEcommerceInvProducto(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoPK pk = UtilsJSON.jsonToObjeto(InvProductoPK.class, map.get("invProductoPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = productoService.activarEcommerceInvProducto(pk, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("El estado de producto: Código: " + pk.getProCodigoPrincipal() + ", se ha modificado correctamente.");
            } else {
                resp.setOperacionMensaje("No se ha modificado el estado del producto.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerAdjuntosProducto")
    public RespuestaWebTO obtenerAdjuntosProducto(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoPK invProductoPK = UtilsJSON.jsonToObjeto(InvProductoPK.class, map.get("invProductoPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvAdjuntosProductosWebTO> respues = productoService.convertirStringUTF8(invProductoPK);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron imágenes");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarClienteTO")
    public RespuestaWebTO insertarClienteTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvClienteTO invClienteTO = UtilsJSON.jsonToObjeto(InvClienteTO.class, map.get("invClienteTO"));
        List<InvGuiaRemisionInp> listadoINP = UtilsJSON.jsonToList(InvGuiaRemisionInp.class, map.get("listadoINP"));
        List<InvClientesVentasDetalleTO> invClientesVentasDetalleTO = UtilsJSON.jsonToList(InvClientesVentasDetalleTO.class, map.get("invClientesVentasDetalleTO"));
        List<InvClientesDirecciones> listaDirecciones = UtilsJSON.jsonToList(InvClientesDirecciones.class, map.get("listaDirecciones"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = clienteService.insertarInvClienteTOVentaAutomatica(invClienteTO, invClientesVentasDetalleTO, listaDirecciones, listadoINP, sisInfoTO);
            if (respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(invClienteTO.getCliCodigo());
                resp.setOperacionMensaje("El cliente: Código " + invClienteTO.getCliCodigo() + ", se ha insertado correctamente.");
            } else {
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarInvCliente")
    public RespuestaWebTO eliminarInvCliente(@RequestBody String json) throws Exception, GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvClientePK pk = UtilsJSON.jsonToObjeto(InvClientePK.class, map.get("invClientePK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = clienteService.eliminarInvCliente(pk, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("El cliente: Código " + pk.getCliCodigo() + ", se ha eliminado correctamente.");
            } else {
                resp.setOperacionMensaje("No se ha eliminado el cliente.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (DataIntegrityViolationException ge) {
            resp.setOperacionMensaje("No se ha eliminado. El cliente tiene movimientos.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoInvCliente")
    public RespuestaWebTO modificarEstadoInvCliente(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvClientePK pk = UtilsJSON.jsonToObjeto(InvClientePK.class, map.get("invClientePK"));
        boolean estado = UtilsJSON.jsonToObjeto(Boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = clienteService.modificarEstadoInvCliente(pk, estado, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("El estado de cliente: Código: " + pk.getCliCodigo() + ", se ha modificado correctamente.");
            } else {
                resp.setOperacionMensaje("No se ha modificado correctamente el estado del cliente.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/validarIdentificacionCliente")
    public RespuestaWebTO validarIdentificacionCliente(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = clienteService.validarIdentificacionCliente(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("Error al validar identificación.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getClienteRepetido")
    public RespuestaWebTO getClienteRepetido(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        if (empresa != null && !empresa.equals("")) {
            empresa = "'" + empresa + "'";
        }
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        if (codigo != null && !codigo.equals("")) {
            codigo = "'" + codigo + "'";
        }
        String id = UtilsJSON.jsonToObjeto(String.class, map.get("id"));
        if (id != null && !id.equals("")) {
            id = "'" + id + "'";
        }
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        if (nombre != null && !nombre.equals("")) {
            nombre = "'" + nombre + "'";
        }
        String razonSocial = UtilsJSON.jsonToObjeto(String.class, map.get("razonSocial"));
        if (razonSocial != null && !razonSocial.equals("")) {
            razonSocial = "'" + razonSocial + "'";
        }
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = clienteService.getClienteRepetido(empresa, codigo, id, nombre, razonSocial);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("El cliente ya existe.");
            } else {
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("El cliente no existe.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarClienteTO")
    public RespuestaWebTO modificarClienteTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvClienteTO invClienteTO = UtilsJSON.jsonToObjeto(InvClienteTO.class, map.get("invClienteTO"));
        List<InvGuiaRemisionInp> listadoINP = UtilsJSON.jsonToList(InvGuiaRemisionInp.class, map.get("listadoINP"));
        List<InvClientesVentasDetalleTO> invClientesVentasDetalleTO = UtilsJSON.jsonToList(InvClientesVentasDetalleTO.class, map.get("invClientesVentasDetalleTO"));
        List<InvClientesDirecciones> listaDirecciones = UtilsJSON.jsonToList(InvClientesDirecciones.class, map.get("listaDirecciones"));
        String codigoAnterior = UtilsJSON.jsonToObjeto(String.class, map.get("codigoAnterior"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = clienteService.modificarInvClienteTOVentaAutomatica(invClienteTO, codigoAnterior, invClientesVentasDetalleTO, listaDirecciones, listadoINP, sisInfoTO);
            if (respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues.substring(1));
                resp.setOperacionMensaje("El cliente: Código " + invClienteTO.getCliCodigo() + ", se ha modificado correctamente.");
            } else {
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarClienteLugarEntrega")
    public RespuestaWebTO modificarClienteLugarEntrega(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvClientePK invClientePk = UtilsJSON.jsonToObjeto(InvClientePK.class, map.get("invClientePk"));
        String cliLugaresEntrega = UtilsJSON.jsonToObjeto(String.class, map.get("cliLugaresEntrega"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = clienteService.modificarClienteLugarEntrega(invClientePk, cliLugaresEntrega, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("El cliente: Código " + invClientePk.getCliCodigo() + ", se ha modificado correctamente.");
            } else {
                resp.setOperacionMensaje("El cliente ya no existe");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*PROVEEDOR*/
    @RequestMapping("/getListInvProveedorTO")
    public RespuestaWebTO getListInvProveedorTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        boolean inactivos = UtilsJSON.jsonToObjeto(Boolean.class, map.get("inactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvProveedorTO> respues = proveedorService.getListProveedorTO(empresa, categoria, inactivos, busqueda);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaCategoriaProveedorComboTO")
    public RespuestaWebTO getListaCategoriaProveedorComboTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvCategoriaProveedorComboTO> respues = proveedorCategoriaService.getListaCategoriaProveedorComboTO(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //Para consulta listado proveedor
    @RequestMapping("/getInvFunListadoProveedoresTO")
    public RespuestaWebTO getInvFunListadoProveedoresTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunListadoProveedoresTO> respuesta = proveedorService.getInvFunListadoProveedoresTO(empresa, categoria);
            if (respuesta != null && !respuesta.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarProveedorTO")
    public RespuestaWebTO insertarProveedorTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProveedorTO invProveedorTO = UtilsJSON.jsonToObjeto(InvProveedorTO.class, map.get("invProveedorTO"));
        List<InvProveedorTransportista> transportistas = UtilsJSON.jsonToList(InvProveedorTransportista.class, map.get("transportistas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = proveedorService.insertarInvProveedorTO(invProveedorTO, sisInfoTO, transportistas);
            if (respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(proveedorService.getBuscaCedulaProveedorTO(invProveedorTO.getEmpCodigo(), invProveedorTO.getProvId()));
                resp.setOperacionMensaje("El proveedor: Código " + invProveedorTO.getProvCodigo() + ", se ha guardado correctamente.");
            } else {
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarListadoExcelProveedores")
    public RespuestaWebTO insertarListadoExcelProveedores(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<InvProveedorTO> listaInvProveedorTO = UtilsJSON.jsonToList(InvProveedorTO.class, map.get("listado"));
        List<AnxProvinciaCantonTO> listaProvincias = UtilsJSON.jsonToList(AnxProvinciaCantonTO.class, map.get("listaProvincias"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = proveedorService.insertarListadoExcelProveedores(listaInvProveedorTO, listaProvincias, sisInfoTO);
            if (respuesta != null) {
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No hay lista que importar. comunicarse con el administrador");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarListadoExcelClientes")
    public RespuestaWebTO insertarListadoExcelClientes(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<InvClienteTO> listaInvClienteTO = UtilsJSON.jsonToList(InvClienteTO.class, map.get("listado"));
        List<AnxProvinciaCantonTO> listaProvincias = UtilsJSON.jsonToList(AnxProvinciaCantonTO.class, map.get("listaProvincias"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = clienteService.insertarListadoExcelClientes(listaInvClienteTO, listaProvincias, sisInfoTO);
            if (respuesta != null) {
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No hay lista que importar. comunicarse con el administrador");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarProveedorTO")
    public RespuestaWebTO modificarProveedorTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProveedorTO invProveedorTO = UtilsJSON.jsonToObjeto(InvProveedorTO.class, map.get("invProveedorTO"));
        List<InvProveedorTransportista> transportistas = UtilsJSON.jsonToList(InvProveedorTransportista.class, map.get("transportistas"));
        String codigoAnterior = UtilsJSON.jsonToObjeto(String.class, map.get("codigoAnterior"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = proveedorService.modificarInvProveedorTO(invProveedorTO, codigoAnterior, sisInfoTO, transportistas);
            if (respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(proveedorService.getBuscaCedulaProveedorTO(invProveedorTO.getEmpCodigo(), invProveedorTO.getProvId()));
                resp.setOperacionMensaje("El proveedor: Código " + invProveedorTO.getProvCodigo() + ", se ha modificado correctamente.");
            } else {
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarInvProveedorTO")
    public RespuestaWebTO eliminarInvProveedorTO(@RequestBody String json) throws Exception, GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProveedorTO invProveedorTO = UtilsJSON.jsonToObjeto(InvProveedorTO.class, map.get("invProveedorTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = proveedorService.eliminarInvProveedorTO(invProveedorTO, sisInfoTO);
            if (respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("El proveedor: Código " + invProveedorTO.getProvCodigo() + ", se ha eliminado correctamente.");
            } else {
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (DataIntegrityViolationException ge) {
            resp.setOperacionMensaje("No se ha eliminado. El proveedor tiene movimientos.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoInvProveedor")
    public RespuestaWebTO modificarEstadoInvProveedor(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProveedorPK pk = UtilsJSON.jsonToObjeto(InvProveedorPK.class, map.get("invProveedorPK"));
        boolean estado = UtilsJSON.jsonToObjeto(Boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = proveedorService.modificarEstadoInvProveedor(pk, estado, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("El estado de proveedor: Código " + pk.getProvCodigo() + ", se ha modificado correctamente.");
            } else {
                resp.setOperacionMensaje("El estado del proveedor no se ha modificado correctamente.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getProveedorTO")
    public List<InvProveedorTO> getProveedorTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proveedorService.getProveedorTO(empresa, codigo);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBuscaCedulaProveedorTO")
    public RespuestaWebTO getBuscaCedulaProveedorTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cedRuc = UtilsJSON.jsonToObjeto(String.class, map.get("cedRuc"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvProveedorTO respues = proveedorService.getBuscaCedulaProveedorTO(empresa, cedRuc);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("El proveedor no existe");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getProveedorRepetido")
    public RespuestaWebTO getProveedorRepetido(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        if (empresa != null && !empresa.equals("")) {
            empresa = "'" + empresa + "'";
        }
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        if (codigo != null && !codigo.equals("")) {
            codigo = "'" + codigo + "'";
        }
        String id = UtilsJSON.jsonToObjeto(String.class, map.get("id"));
        if (id != null && !id.equals("")) {
            id = "'" + id + "'";
        }
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        if (nombre != null && !nombre.equals("")) {
            nombre = "'" + nombre + "'";
        }
        String razonSocial = UtilsJSON.jsonToObjeto(String.class, map.get("razonSocial"));
        if (razonSocial != null && !razonSocial.equals("")) {
            razonSocial = "'" + razonSocial + "'";
        }
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = proveedorService.getProveedorRepetido(empresa, codigo, id, nombre, razonSocial);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("El proveedor ya existe.");
            } else {
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("El proveedor no existe.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteProveedor")
    public @ResponseBody
    String generarReporteProveedor(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportCategoriaProveedor.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvProveedorTO> listado = UtilsJSON.jsonToList(InvProveedorTO.class, parametros.get("ListadoProveedores"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = proveedorService.generarReporteProveedor(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteProveedor")
    public @ResponseBody
    String exportarReporteProveedor(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvProveedorTO> listado = UtilsJSON.jsonToList(InvProveedorTO.class, map.get("listadoProveedores"));
        List<DetalleExportarFiltrado> listadoFiltrado = UtilsJSON.jsonToList(DetalleExportarFiltrado.class, map.get("listaFiltradoProveedores"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = proveedorService.exportarReporteProveedor(usuarioEmpresaReporteTO, listado, listadoFiltrado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteProveedorTodo")
    public @ResponseBody
    String exportarReporteProveedorTodo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvProveedorTO> listado = UtilsJSON.jsonToList(InvProveedorTO.class, map.get("listadoProveedores"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = proveedorService.exportarReporteProveedorTodo(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*MOTIVO PROFORMA*/
    @RequestMapping("/getListaInvProformaMotivoTO")
    public RespuestaWebTO getListaInvProformaMotivoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        boolean inactivos = UtilsJSON.jsonToObjeto(Boolean.class, map.get("inactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvProformaMotivoTO> respues = proformasMotivoService.getListaInvProformaMotivoTO(empresa, inactivos, busqueda);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarInvProformaMotivoTO")
    public RespuestaWebTO insertarInvProformaMotivoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProformaMotivoTO invProformaMotivoTO = UtilsJSON.jsonToObjeto(InvProformaMotivoTO.class, map.get("invProformaMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = proformasMotivoService.insertarInvProformaMotivoTO(invProformaMotivoTO, sisInfoTO);
            if (respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje(respues.substring(1));
            } else {
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarInvProformaMotivoTO")
    public RespuestaWebTO modificarInvProformaMotivoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProformaMotivoTO invProformaMotivoTO = UtilsJSON.jsonToObjeto(InvProformaMotivoTO.class, map.get("invProformaMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = proformasMotivoService.modificarInvProformaMotivoTO(invProformaMotivoTO, sisInfoTO);
            if (respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje(respues.substring(1));
            } else {
                resp.setOperacionMensaje("No se logró completar la operación.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoInvProformaMotivoTO")
    public RespuestaWebTO modificarEstadoInvProformaMotivoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProformasMotivoPK pk = UtilsJSON.jsonToObjeto(InvProformasMotivoPK.class, map.get("invProformasMotivoPK"));
        boolean estado = UtilsJSON.jsonToObjeto(Boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = proformasMotivoService.modificarEstadoInvProformaMotivo(pk, estado, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("El motivo de proforma: Código " + pk.getPmCodigo() + (estado ? ", se ha inactivado correctamente" : ", se ha activado correctamente"));
            } else {
                resp.setOperacionMensaje("No se logró completar la operación.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarInvProformaMotivoTO")
    public RespuestaWebTO eliminarInvProformaMotivoTO(@RequestBody String json) throws Exception, GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProformaMotivoTO invProformaMotivoTO = UtilsJSON.jsonToObjeto(InvProformaMotivoTO.class, map.get("invProformaMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = proformasMotivoService.eliminarInvProformaMotivoTO(invProformaMotivoTO, sisInfoTO);
            if (respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje(respues.substring(1));
            } else {
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (DataIntegrityViolationException ge) {
            resp.setOperacionMensaje("No se puede eliminar. El motivo de proforma tiene movimientos.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteMotivoProforma")
    public @ResponseBody
    String generarReporteMotivoProforma(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportMotivoProforma.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvProformaMotivoTO> listado = UtilsJSON.jsonToList(InvProformaMotivoTO.class, parametros.get("ListadoMotivo"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteMotivoProforma(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteMotivoProforma")
    public @ResponseBody
    String exportarReporteMotivoProforma(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvProformaMotivoTO> listado = UtilsJSON.jsonToList(InvProformaMotivoTO.class, map.get("ListadoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteMotivoProforma(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*MOTIVO DE CONSUMOS*/
    @RequestMapping("/getListaInvConsumosMotivoTO")
    public RespuestaWebTO getListaInvConsumosMotivoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        boolean inactivos = UtilsJSON.jsonToObjeto(Boolean.class, map.get("inactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvConsumosMotivoTO> respues = consumosMotivoService.getInvListaConsumoMotivoTO(empresa, inactivos, busqueda);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/accionInvConsumosMotivo")
    public RespuestaWebTO accionInvConsumosMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        resp.setOperacionMensaje("La operación no obtuvo ninguna respuesta");
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvConsumosMotivoTO invConsumosMotivoTO = UtilsJSON.jsonToObjeto(InvConsumosMotivoTO.class, map.get("invConsumosMotivoTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = consumosMotivoService.accionInvConsumosMotivo(invConsumosMotivoTO, accion, sisInfoTO);
            if (respues.length() > 0 && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues.substring(1));
                resp.setOperacionMensaje(respues.substring(1));
            } else if (respues.length() > 0) {
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoInvConsumosMotivoTO")
    public RespuestaWebTO modificarEstadoInvConsumosMotivoTO(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvConsumosMotivoTO invConsumosMotivoTO = UtilsJSON.jsonToObjeto(InvConsumosMotivoTO.class, map.get("invConsumosMotivoTO"));
        try {
            String msje = consumosMotivoService.modificarEstadoInvConsumosMotivoTO(invConsumosMotivoTO, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/tieneMovimientosConsumosMotivo")
    public RespuestaWebTO tieneMovimientosConsumosMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String motivoCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("motivoCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = consumosMotivoService.tieneMovimientosConsumosMotivo(empresa, motivoCodigo);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(respues);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteMotivoConsumo")
    public @ResponseBody
    String generarReporteMotivoConsumo(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportMotivoConsumo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvConsumosMotivoTO> listado = UtilsJSON.jsonToList(InvConsumosMotivoTO.class, parametros.get("ListadoMotivo"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteMotivoConsumo(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteMotivoConsumo")
    public @ResponseBody
    String exportarReporteMotivoConsumo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvConsumosMotivoTO> listado = UtilsJSON.jsonToList(InvConsumosMotivoTO.class, map.get("ListadoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteMotivoConsumo(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*MOTIVO DE TRANSFERENCIA*/
    @RequestMapping("/getListaTransferenciaMotivoTO")
    public RespuestaWebTO getListaTransferenciaMotivoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean inactivos = UtilsJSON.jsonToObjeto(Boolean.class, map.get("inactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvTransferenciaMotivoTO> respues = transferenciasMotivoService.getListaTransferenciaMotivoTO(empresa, inactivos);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/accionInvTransferenciaMotivo")
    public RespuestaWebTO accionInvTransferenciaMotivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        resp.setOperacionMensaje("La operación no obtuvo ninguna respuesta");
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvTransferenciaMotivoTO invTransferenciaMotivoTO = UtilsJSON.jsonToObjeto(InvTransferenciaMotivoTO.class, map.get("invTransferenciaMotivoTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = transferenciasMotivoService.accionInvTransferenciaMotivo(invTransferenciaMotivoTO, accion, sisInfoTO);
            if (respues.length() > 0 && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues.substring(1));
                resp.setOperacionMensaje(respues.substring(1));
            } else if (respues.length() > 0) {
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoInvTransferenciaMotivoTO")
    public RespuestaWebTO modificarEstadoInvTransferenciaMotivoTO(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvTransferenciaMotivoTO invTransferenciaMotivoTO = UtilsJSON.jsonToObjeto(InvTransferenciaMotivoTO.class, map.get("invTransferenciaMotivoTO"));
        try {
            String msje = transferenciasMotivoService.modificarEstadoInvTransferenciaMotivoTO(invTransferenciaMotivoTO, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteMotivoTransferencia")
    public @ResponseBody
    String generarReporteMotivoTransferencia(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportMotivoTransferencia.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvTransferenciaMotivoTO> listado = UtilsJSON.jsonToList(InvTransferenciaMotivoTO.class, parametros.get("ListadoMotivo"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteMotivoTransferencia(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteMotivoTransferencia")
    public @ResponseBody
    String exportarReporteMotivoTransferencia(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvTransferenciaMotivoTO> listado = UtilsJSON.jsonToList(InvTransferenciaMotivoTO.class, map.get("ListadoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteMotivoTransferencia(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*NUMERACION DE COMPRAS*/
    @RequestMapping("/getListaInvNumeracionCompraTO")
    public RespuestaWebTO getListaInvNumeracionCompraTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvNumeracionCompraTO> respues = comprasNumeracionService.getListaInvNumeracionCompraTO(empresa, periodo, motivo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteNumeracionCompra")
    public @ResponseBody
    String generarReporteNumeracionCompra(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportNumeracionCompra.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvNumeracionCompraTO> listado = UtilsJSON.jsonToList(InvNumeracionCompraTO.class, parametros.get("ListadoNumeracion"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteNumeracionCompra(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteNumeracionCompra")
    public @ResponseBody
    String exportarReporteNumeracionCompra(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvNumeracionCompraTO> listado = UtilsJSON.jsonToList(InvNumeracionCompraTO.class, map.get("ListadoNumeracion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteNumeracionCompra(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*NUMERACION DE VENTAS*/
    @RequestMapping("/getListaInvNumeracionVentaTO")
    public RespuestaWebTO getListaInvNumeracionVentaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvNumeracionVentaTO> respues = ventasNumeracionService.getListaInvNumeracionVentaTO(empresa, periodo, motivo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteNumeracionVenta")
    public @ResponseBody
    String generarReporteNumeracionVenta(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportNumeracionVenta.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvNumeracionVentaTO> listado = UtilsJSON.jsonToList(InvNumeracionVentaTO.class, parametros.get("ListadoNumeracion"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteNumeracionVenta(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteNumeracionVenta")
    public @ResponseBody
    String exportarReporteNumeracionVenta(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvNumeracionVentaTO> listado = UtilsJSON.jsonToList(InvNumeracionVentaTO.class, map.get("ListadoNumeracion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteNumeracionVenta(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*NUMERACION DE CONSUMOS*/
    @RequestMapping("/getListaInvNumeracionConsumoTO")
    public RespuestaWebTO getListaInvNumeracionConsumoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvNumeracionConsumoTO> respues = consumosNumeracionService.getListaInvNumeracionConsumoTO(empresa, periodo, motivo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteNumeracionConsumo")
    public @ResponseBody
    String generarReporteNumeracionConsumo(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportNumeracionConsumo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvNumeracionConsumoTO> listado = UtilsJSON.jsonToList(InvNumeracionConsumoTO.class, parametros.get("ListadoNumeracion"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteNumeracionConsumo(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteNumeracionConsumo")
    public @ResponseBody
    String exportarReporteNumeracionConsumo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvNumeracionConsumoTO> listado = UtilsJSON.jsonToList(InvNumeracionConsumoTO.class, map.get("ListadoNumeracion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteNumeracionConsumo(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*SALDO BODEGA*/
    @RequestMapping("/getListaSaldoBodegaTO")
    public RespuestaWebTO getListaSaldoBodegaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SaldoBodegaTO> respues = bodegaService.getListaSaldoBodegaTO(empresa, bodega, hasta, categoria);
            if (respues != null && respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteSaldoBodega")
    public @ResponseBody
    String generarReporteSaldoBodega(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportSaldoBodega.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, parametros.get("bodega"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<SaldoBodegaTO> listSaldoBodegaTO = UtilsJSON.jsonToList(SaldoBodegaTO.class, parametros.get("listSaldoBodegaTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteSaldoBodega(usuarioEmpresaReporteTO, bodega, fechaHasta, listSaldoBodegaTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteSaldoBodega")
    public @ResponseBody
    String exportarReporteSaldoBodega(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<SaldoBodegaTO> listado = UtilsJSON.jsonToList(SaldoBodegaTO.class, map.get("listSaldoBodegaTO"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteSaldoBodega(usuarioEmpresaReporteTO, listado, fechaHasta);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*CLIENTES*/
    @RequestMapping("/getInvFunListadoClientesTO")
    public RespuestaWebTO getInvFunListadoClientesTO(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean mostrarInactivo = UtilsJSON.jsonToObjeto(Boolean.class, map.get("mostrarInactivo"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        String grupoEmprearial = UtilsJSON.jsonToObjeto(String.class, map.get("grupoEmprearial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunListadoClientesTO> respues = clienteService.getInvFunListadoClientesTO(empresa, mostrarInactivo, categoria, busqueda, grupoEmprearial);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resutados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getInvFunListadoClientesTOSinVentaRecurrente")
    public RespuestaWebTO getInvFunListadoClientesTOSinVentaRecurrente(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean mostrarInactivo = UtilsJSON.jsonToObjeto(Boolean.class, map.get("mostrarInactivo"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        String grupoEmprearial = UtilsJSON.jsonToObjeto(String.class, map.get("grupoEmprearial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunListadoClientesTO> respues = clienteService.getInvFunListadoClientesTOSinVentaRecurrente(empresa, mostrarInactivo, categoria, busqueda, grupoEmprearial);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resutados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCliente")
    public @ResponseBody
    String generarReporteCliente(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportCliente.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvFunListadoClientesTO> listado = UtilsJSON.jsonToList(InvFunListadoClientesTO.class, parametros.get("listClientes"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteCliente(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteCliente")
    public @ResponseBody
    String exportarReporteCliente(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvFunListadoClientesTO> listado = UtilsJSON.jsonToList(InvFunListadoClientesTO.class, map.get("listClientes"));
        List<DetalleExportarFiltrado> listadoFiltrado = UtilsJSON.jsonToList(DetalleExportarFiltrado.class, map.get("listClientesFiltrado"));

        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteCliente(usuarioEmpresaReporteTO, listado, listadoFiltrado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*IMPRIMIR PLACAS */
    @RequestMapping("/getInvFunListaProductosImpresionPlacasTO")
    public RespuestaWebTO getInvFunListaProductosImpresionPlacasTO(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String producto = UtilsJSON.jsonToObjeto(String.class, map.get("producto"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunListaProductosImpresionPlacasTO> respues = productoService.getInvFunListaProductosImpresionPlacasTO(empresa, producto, estado);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getDatoFunListaProductosImpresionPlaca")
    public RespuestaWebTO getDatoFunListaProductosImpresionPlaca(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String producto = UtilsJSON.jsonToObjeto(String.class, map.get("producto"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunListaProductosImpresionPlacasTO> listado = productoService.getInvFunListaProductosImpresionPlacasTO(empresa, producto, estado);
            List<DatoFunListaProductosImpresionPlaca> respues = productoService.convertirInvFunListaProductosImpresionPlacasTO_DatoFunListaProductosImpresionPlaca(listado);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteImpresionPlaca")
    public @ResponseBody
    String generarReporteImpresionPlaca(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportInvImpresionPlaca.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<DatoFunListaProductosImpresionPlaca> listProductosImpresionPlaca = UtilsJSON.jsonToList(DatoFunListaProductosImpresionPlaca.class, parametros.get("listProductosImpresionPlaca"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteImpresionPlaca(usuarioEmpresaReporteTO, listProductosImpresionPlaca);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteProductoImprimirPlacas")
    public @ResponseBody
    String exportarReporteProductoImprimirPlacas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<DatoFunListaProductosImpresionPlaca> listProductosImpresionPlaca = UtilsJSON.jsonToList(DatoFunListaProductosImpresionPlaca.class, map.get("listProductosImpresionPlaca"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteProductoImprimirPlacas(usuarioEmpresaReporteTO, listProductosImpresionPlaca);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*COMPRAS VS VENTAS TONELAJE*/
    @RequestMapping("/listarComprasVsVentasTonelaje")
    public RespuestaWebTO listarComprasVsVentasTonelaje(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunComprasVsVentasTonelajeTO> respues = comprasService.listarComprasVsVentasTonelaje(empresa, desde, hasta, sector, bodega, proveedor);
            if (respues != null && respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*COMPRAS CONSOLIDANDO PRODUCTOS*/
    @RequestMapping("/getInvFunComprasConsolidandoProductosTO")
    public RespuestaWebTO getInvFunComprasConsolidandoProductosTO(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunComprasConsolidandoProductosTO> respues = comprasService.getInvFunComprasConsolidandoProductosTO(empresa, desde, hasta, sector, bodega, proveedor);
            if (respues != null && respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsolidadoCompraProducto")
    public @ResponseBody
    String generarReporteComprasConsolidandoProductos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportConsolidadoCompraVentaProducto.jrxml";
        byte[] respuesta;
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, parametros.get("bodega"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvFunComprasConsolidandoProductosTO> listado = UtilsJSON.jsonToList(InvFunComprasConsolidandoProductosTO.class, parametros.get("listInvFunComprasConsolidandoProductosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteConsolidadoCompraProducto(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, bodega, proveedor, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteComprasConsolidandoProductos")
    public @ResponseBody
    String exportarReporteComprasConsolidandoProductos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvFunComprasConsolidandoProductosTO> listado = UtilsJSON.jsonToList(InvFunComprasConsolidandoProductosTO.class, map.get("listInvFunComprasConsolidandoProductosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteComprasConsolidandoProductos(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*VENTAS CONSOLIDANDO PRODUCTOS*/
    @RequestMapping("/getInvFunVentasConsolidandoProductosTO")
    public RespuestaWebTO getInvFunVentasConsolidandoProductosTO(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunVentasConsolidandoProductosTO> respues = ventasService.getInvFunVentasConsolidandoProductosTO(empresa, desde, hasta, sector, bodega, cliente);
            if (respues != null && respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsolidadoVentaProducto")
    public @ResponseBody
    String generarReporteConsolidadoVentaProducto(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportConsolidadoCompraVentaProducto.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, parametros.get("bodega"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, parametros.get("cliente"));
        List<InvFunVentasConsolidandoProductosTO> listado = UtilsJSON.jsonToList(InvFunVentasConsolidandoProductosTO.class, parametros.get("listInvFunVentasConsolidandoProductosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteConsolidadoVentaProducto(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, bodega, cliente, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteVentasConsolidandoProductos")
    public @ResponseBody
    String exportarReporteVentasConsolidandoProductos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvFunVentasConsolidandoProductosTO> listado = UtilsJSON.jsonToList(InvFunVentasConsolidandoProductosTO.class, map.get("listInvFunVentasConsolidandoProductosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteVentasConsolidandoProductos(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*SALDO COMPROBACIÓN (MONTOS)*/
    @RequestMapping("/getInvFunSaldoBodegaComprobacionCantidadesTO")
    public RespuestaWebTO getInvFunSaldoBodegaComprobacionCantidadesTO(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SaldoBodegaComprobacionTO> respues = bodegaService.getInvFunSaldoBodegaComprobacionCantidadesTO(empresa, bodega, desde, hasta);
            if (respues != null && respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getInvFunSaldoBodegaComprobacionTO")
    public RespuestaWebTO getInvFunSaldoBodegaComprobacionTO(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SaldoBodegaComprobacionTO> respues = bodegaService.getInvFunSaldoBodegaComprobacionTO(empresa, bodega, desde, hasta);
            if (respues != null && respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteSaldoBodegaComprobacion")
    public @ResponseBody
    String generarReporteSaldoBodegaComprobacion(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportSaldoBodegaComprobacion.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, parametros.get("bodega"));
        List<SaldoBodegaComprobacionTO> listado = UtilsJSON.jsonToList(SaldoBodegaComprobacionTO.class, parametros.get("lista"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteSaldoBodegaComprobacion(usuarioEmpresaReporteTO, bodega, fechaDesde, fechaHasta, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteSaldoBodegaComprobacion")
    public @ResponseBody
    String exportarReporteSaldoBodegaComprobacion(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<SaldoBodegaComprobacionTO> listado = UtilsJSON.jsonToList(SaldoBodegaComprobacionTO.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteSaldoBodegaComprobacion(usuarioEmpresaReporteTO, bodega, fechaDesde, fechaHasta, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteSaldoBodegaComprobacionCantidades")
    public @ResponseBody
    String generarReporteSaldoBodegaComprobacionCantidades(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportSaldoBodegaComprobacionCantidades.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, parametros.get("bodega"));
        List<SaldoBodegaComprobacionTO> listado = UtilsJSON.jsonToList(SaldoBodegaComprobacionTO.class, parametros.get("lista"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteSaldoBodegaComprobacionCantidades(usuarioEmpresaReporteTO, bodega, fechaDesde, fechaHasta, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteSaldoBodegaComprobacionCantidades")
    public @ResponseBody
    String exportarReporteSaldoBodegaComprobacionCantidades(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<SaldoBodegaComprobacionTO> listado = UtilsJSON.jsonToList(SaldoBodegaComprobacionTO.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteSaldoBodegaComprobacionCantidades(usuarioEmpresaReporteTO, bodega, fechaDesde, fechaHasta, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*SALDO BODEGA GEENRAL*/
    @RequestMapping("/getInvFunListaProductosSaldosGeneralTO")
    public RespuestaWebTO getInvFunListaProductosSaldosGeneralTO(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String producto = UtilsJSON.jsonToObjeto(String.class, map.get("producto"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            RetornoTO respues = productoSaldosService.getInvFunListaProductosSaldosGeneralTO(empresa, producto, fecha, estado, usuario);
            if (respues.getColumnas() != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteInvFunListaProductosSaldosGeneral")
    public @ResponseBody
    String exportarReporteInvFunListaProductosSaldosGeneral(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        Object[][] datos = UtilsJSON.jsonToObjeto(Object[][].class, map.get("datos"));
        List<String> columnas = UtilsJSON.jsonToObjeto(List.class, map.get("columnas"));
        List<String> columnasFaltantes = UtilsJSON.jsonToObjeto(List.class, map.get("columnasFaltantes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteInvFunListaProductosSaldosGeneral(usuarioEmpresaReporteTO, datos, columnas, columnasFaltantes);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*VENTAS CONSOLIDANDO PRODUCTOS COBERTURA*/
    @RequestMapping("/getInvFunVentasConsolidandoProductosCoberturaTO")
    public RespuestaWebTO getInvFunVentasConsolidandoProductosCoberturaTO(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunVentasConsolidandoProductosCoberturaTO> respues = ventasService.getInvFunVentasConsolidandoProductosCoberturaTO(empresa, desde, hasta, sector, bodega, motivo, cliente);
            if (respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsolidadoVentaProductoCobertura")
    public @ResponseBody
    String generarReporteConsolidadoVentaProductoCobertura(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportConsolidadoVentaProductoCobertura.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, parametros.get("motivo"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, parametros.get("bodega"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, parametros.get("cliente"));
        List<InvFunVentasConsolidandoProductosCoberturaTO> listado = UtilsJSON.jsonToList(InvFunVentasConsolidandoProductosCoberturaTO.class, parametros.get("listInvFunVentasConsolidandoProductosCoberturaTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteConsolidadoVentaProductoCobertura(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, sector, bodega, motivo, cliente, listado, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteInvFunVentasConsolidandoProductosCoberturaTO")
    public @ResponseBody
    String exportarReporteInvFunVentasConsolidandoProductosCoberturaTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        List<InvFunVentasConsolidandoProductosCoberturaTO> listado = UtilsJSON.jsonToList(InvFunVentasConsolidandoProductosCoberturaTO.class, map.get("listInvFunVentasConsolidandoProductosCoberturaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteInvFunVentasConsolidandoProductosCoberturaTO(usuarioEmpresaReporteTO, sector, fechaDesde, fechaHasta, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*VENTAS CONSOLIDANDO CLIENTES*/
    @RequestMapping("/getInvFunVentasConsolidandoClientesTO")
    public RespuestaWebTO getInvFunVentasConsolidandoClientesTO(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunVentasConsolidandoClientesTO> respues = ventasService.getInvFunVentasConsolidandoClientesTO(empresa, sector, desde, hasta);
            if (respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsolidadoVentaCliente")
    public @ResponseBody
    String generarReporteConsolidadoVentaCliente(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportConsolidadoVentaCliente.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        List<InvFunVentasConsolidandoClientesTO> listado = UtilsJSON.jsonToList(InvFunVentasConsolidandoClientesTO.class, parametros.get("listInvFunVentasConsolidandoClientesTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteConsolidadoVentaCliente(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, sector, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteInvFunVentasConsolidandoClientesTO")
    public @ResponseBody
    String exportarReporteInvFunVentasConsolidandoClientesTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        List<InvFunVentasConsolidandoClientesTO> listado = UtilsJSON.jsonToList(InvFunVentasConsolidandoClientesTO.class, map.get("listInvFunVentasConsolidandoClientesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteInvFunVentasConsolidandoClientesTO(usuarioEmpresaReporteTO, sector, fechaDesde, fechaHasta, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*COMPRAS CONSOLIDANDO PRODUCTOS MENSUAL*/
    @RequestMapping("/getComprasPorPeriodo")
    public RespuestaWebTO getComprasPorPeriodo(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        boolean chk = UtilsJSON.jsonToObjeto(boolean.class, map.get("chk"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            RetornoTO respues = comprasService.getComprasPorPeriodo(empresa, codigoSector, fechaInicio, fechaFin, chk);
            if (respues.getColumnas() != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteComprasPorPeriodo")
    public @ResponseBody
    String exportarReporteComprasPorPeriodo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        Object[][] datos = UtilsJSON.jsonToObjeto(Object[][].class, map.get("datos"));
        List<String> columnas = UtilsJSON.jsonToObjeto(List.class, map.get("columnas"));
        List<String> columnasFaltantes = UtilsJSON.jsonToObjeto(List.class, map.get("columnasFaltantes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteComprasPorPeriodo(usuarioEmpresaReporteTO, datos, columnas, columnasFaltantes);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarInvComprasTO")
    public MensajeTO insertarInvComprasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasTO invComprasTO = UtilsJSON.jsonToObjeto(InvComprasTO.class,
                map.get("invComprasTO"));
        List<InvComprasDetalleTO> listaInvComprasDetalleTO = UtilsJSON.jsonToList(InvComprasDetalleTO.class,
                map.get("listaInvComprasDetalleTO"));
        AnxCompraTO anxCompraTO = UtilsJSON.jsonToObjeto(AnxCompraTO.class,
                map.get("anxCompraTO"));
        List<AnxCompraDetalleTO> anxCompraDetalleTO = UtilsJSON.jsonToList(AnxCompraDetalleTO.class,
                map.get("anxCompraDetalleTO"));
        List<AnxCompraReembolsoTO> anxCompraReembolsoTO = UtilsJSON.jsonToList(AnxCompraReembolsoTO.class,
                map.get("anxCompraReembolsoTO"));
        List<AnxCompraFormaPagoTO> anxCompraFormaPagoTO = UtilsJSON.jsonToList(AnxCompraFormaPagoTO.class,
                map.get("anxCompraFormaPagoTO"));
        List<InvAdjuntosCompras> listImagen = UtilsJSON.jsonToList(InvAdjuntosCompras.class,
                map.get("listImagen"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return comprasService.insertarInvComprasTO(invComprasTO, listaInvComprasDetalleTO, anxCompraTO,
                    anxCompraDetalleTO, anxCompraReembolsoTO, anxCompraFormaPagoTO, listImagen, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarInvVentasTO")
    public RespuestaWebTO insertarInvVentasTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvVentasTO invVentasTO = UtilsJSON.jsonToObjeto(InvVentasTO.class, map.get("invVentasTO"));
        List<InvVentasDetalleTO> listaInvVentasDetalleTO = UtilsJSON.jsonToList(InvVentasDetalleTO.class, map.get("listaInvVentasDetalleTO"));
        AnxVentaTO anxVentasTO = UtilsJSON.jsonToObjeto(AnxVentaTO.class, map.get("anxVentasTO"));
        String tipoDocumentoComplemento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumentoComplemento"));
        String numeroDocumentoComplemento = UtilsJSON.jsonToObjeto(String.class, map.get("numeroDocumentoComplemento"));
        String motivoDocumentoComplemento = UtilsJSON.jsonToObjeto(String.class, map.get("motivoDocumentoComplemento"));
        Boolean isComprobanteElectronica = UtilsJSON.jsonToObjeto(Boolean.class, map.get("isComprobanteElectronica"));
        InvVentaGuiaRemision invVentaGuiaRemision = UtilsJSON.jsonToObjeto(InvVentaGuiaRemision.class, map.get("invVentaGuiaRemision"));
        List<InvVentasLiquidacionTO> listaInvVentasLiquidacionTO = UtilsJSON.jsonToList(InvVentasLiquidacionTO.class, map.get("listaInvVentasLiquidacionTO"));
        AnxVentaExportacionTO anxVentaExportacionTO = UtilsJSON.jsonToObjeto(AnxVentaExportacionTO.class, map.get("anxVentaExportacionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        List<InvVentasDatosAdjuntos> listadoImagenes = UtilsJSON.jsonToList(InvVentasDatosAdjuntos.class, map.get("listaImagenes"));
        List<AnxVentaReembolsoTO> listAnxVentaReembolsoTO = UtilsJSON.jsonToList(AnxVentaReembolsoTO.class, map.get("listAnxVentaReembolsoTO"));
        try {
            boolean esPendiente = invVentasTO.getVtaPendiente();
            MensajeTO mensajeTO = ventasService.insertarInventarioVentasTO(invVentasTO,
                    listaInvVentasDetalleTO,
                    anxVentasTO,
                    tipoDocumentoComplemento,
                    numeroDocumentoComplemento,
                    motivoDocumentoComplemento,
                    isComprobanteElectronica,
                    invVentaGuiaRemision,
                    listaInvVentasLiquidacionTO,
                    anxVentaExportacionTO,
                    listadoImagenes,
                    listAnxVentaReembolsoTO,
                    sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                if (!esPendiente) {
                    try {
                        ventasService.quitarPendiente(invVentasTO);
                        resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
                    } catch (Exception e) {
                        invVentasTO.setVtaPendiente(true);
                        mensajeTO.getMap().put("invVentasTO", invVentasTO);
                        e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
                        resp.setOperacionMensaje(e.getMessage());
                    }
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMap());
            } else {
                resp.setExtraInfo(mensajeTO.getListaErrores1());
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado venta.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerReporteVentaDetalle")
    public RespuestaWebTO obtenerReporteVentaDetalle(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvVentasTO invVentasTO = UtilsJSON.jsonToObjeto(InvVentasTO.class, map.get("invVentasTO"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvVentasDetalleTO> listaInvVentasDetalleTO = UtilsJSON.jsonToList(InvVentasDetalleTO.class, map.get("listaInvVentasDetalleTO"));
        Boolean isComprobanteElectronica = UtilsJSON.jsonToObjeto(Boolean.class, map.get("isComprobanteElectronica"));
        try {
            sisInfoTO.setEmpresaRuc(usuarioEmpresaReporteTO.getEmpRuc());
            List<ReporteVentaDetalle> lista = ventasService.obtenerReporteVentaDetalle(invVentasTO, listaInvVentasDetalleTO, isComprobanteElectronica, sisInfoTO);
            resp.setExtraInfo(lista);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaVentas")
    public RespuestaWebTO obtenerDatosParaVentas(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = ventasService.obtenerDatosParaVentas(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron los datos para generar la venta.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosListadoVentas")
    public RespuestaWebTO obtenerDatosListadoVentas(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = ventasService.obtenerDatosListadoVentas(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron los datos.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaVentasRecurrentes")
    public RespuestaWebTO obtenerDatosParaVentasRecurrentes(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = ventasService.obtenerDatosParaVentasRecurrentes(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron los datos para generar la venta recurrente.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarEtiquetaPrecios")
    public RespuestaWebTO listarEtiquetaPrecios(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvProductoEtiquetas respues = productoService.traerEtiquetas(sisInfoTO.getEmpresa());
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron precios de venta.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/consultarVenta")
    public RespuestaWebTO consultarVenta(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String vtaNumero = UtilsJSON.jsonToObjeto(String.class, map.get("vtaNumero"));
        List<InvListadoCobrosTO> listaInvListadoCobrosTO = new ArrayList<>();
        try {
            Map<String, Object> respues = ventasService.consultarVenta(map);
            if (vtaNumero != null && !vtaNumero.equals("")) {
                List<String> documento = UtilsValidacion.separar(vtaNumero, "|");
                if (documento != null && !documento.isEmpty()) {
                    listaInvListadoCobrosTO = ventasService.invListadoCobrosTO(empresa, documento.get(0), documento.get(1), documento.get(2));
                }
            }
            respues.put("listaInvListadoCobrosTO", listaInvListadoCobrosTO);

            InvVentasTO invVentasTORespuesta = UtilsJSON.jsonToObjeto(InvVentasTO.class, respues.get("ventasTO"));
            if (invVentasTORespuesta != null) {
                InvVentaGuiaRemision guia = ventaGuiaRemisionService.obtenerInvVentaGuiaRemision(empresa, invVentasTORespuesta.getVtaPeriodo(), invVentasTORespuesta.getVtaMotivo(), invVentasTORespuesta.getVtaNumero());
                if (guia != null) {
                    guia.setInvVentas(null);
                }
                respues.put("invVentaGuiaRemision", guia);
            }

            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException e) {
            resp.setOperacionMensaje(e.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/suprimirVenta")
    public RespuestaWebTO suprimirVenta(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        boolean isPendiente = UtilsJSON.jsonToObjeto(boolean.class, map.get("isPendiente"));
        String vtaNumero = UtilsJSON.jsonToObjeto(String.class, map.get("vtaNumero"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        boolean suprimirVenta = false;
        boolean isPeriodoAbierto = false;
        SisPeriodo sisPeriodo = null;

        List<String> documento = UtilsValidacion.separar(vtaNumero, "|");
        try {
            boolean estaProcesoAutorizacion = ventaElectronicaService.comprobarAnxVentaElectronica(empresa, documento.get(0), documento.get(1), documento.get(2));
            if (!estaProcesoAutorizacion) {
                InvVentas venta = ventasDao.buscarInvVentas(empresa, documento.get(0), documento.get(1), documento.get(2));
                Date fecha = UtilsDate.fechaFormatoDate(venta.getVtaFecha(), "yyyy-MM-dd");

                List<SisPeriodo> listaPeriodos = periodoService.getListaPeriodo(empresa);
                if (listaPeriodos != null) {
                    for (SisPeriodo itemSisPeriodo : listaPeriodos) {
                        if (fecha.getTime() >= itemSisPeriodo.getPerDesde().getTime() && fecha.getTime() <= UtilsDate.dateCompleto(itemSisPeriodo.getPerHasta()).getTime()) {
                            sisPeriodo = itemSisPeriodo;
                        }
                    }
                    if (sisPeriodo != null && !sisPeriodo.getPerCerrado()) {
                        isPeriodoAbierto = true;
                    }
                }

                if (isPeriodoAbierto) {
                    InvVentasTO ventasTO = ConversionesInventario.convertirInvVentas_InvVentasTO(venta);
                    AnxVentaTO anxVentaTO = ventaDao.getAnexoVentaTO(sisInfoTO.getEmpresa(), ventasTO.getVtaPeriodo(), ventasTO.getVtaMotivo(), venta.getVtaDocumentoNumero());
                    if (isPendiente) {
                        suprimirVenta = true;
                    } else {
                        String respues = ventasService.desmayorizarVenta(ventasTO, anxVentaTO, sisInfoTO);
                        if (respues != null) {
                            suprimirVenta = true;
                            ventasTO.setVtaPendiente(true);
                        } else {
                            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                            resp.setOperacionMensaje("No se pudo DESMAYORIZAR la venta.");
                        }
                    }

                    if (suprimirVenta) {
                        Map<String, Object> respues = ventasService.suprimirVenta(ventasTO, sisInfoTO);
                        String mensaje = UtilsJSON.jsonToObjeto(String.class, respues.get("mensaje"));
                        InvVentasTO invVentasTO = UtilsJSON.jsonToObjeto(InvVentasTO.class, respues.get("invVentasTO"));
                        List<InvVentasDetalleTO> listaInvVentasDetalleTO = UtilsJSON.jsonToList(InvVentasDetalleTO.class, respues.get("listaInvVentasDetalleTO"));
                        if (mensaje.substring(0, 1).equals("T")) {
                            ventasService.quitarPendiente(invVentasTO);
                            invVentasTO.setVtaPendiente(false);
                            //Anular
                            invVentasTO.setVtaAnulado(true);
                            InvVentasMotivoAnulacion motivoAnulacion = new InvVentasMotivoAnulacion();
                            motivoAnulacion.setAnuComentario("VENTA ANULADA DESDE LA APLICACION WEB");
                            motivoAnulacion.setInvVentas(null);
                            List<InvVentasDatosAdjuntos> listadoImagenes = ventasService.listarImagenesDeVenta(new InvVentasPK(sisInfoTO.getEmpresa(), ventasTO.getVtaPeriodo(), ventasTO.getVtaMotivo(), ventasTO.getVtaNumero()));
                            MensajeTO respuesAnulado = ventasService.modificarInventarioVentasTO(invVentasTO, listaInvVentasDetalleTO, null, false, null, false, null, null, null,
                                    motivoAnulacion, null, new ArrayList<InvVentasLiquidacionTO>(), new ArrayList<InvVentasLiquidacionTO>(), null, listadoImagenes,
                                    new ArrayList<>(),
                                    new ArrayList<>(),
                                    sisInfoTO);
                            if (respuesAnulado.getMensaje().charAt(0) == 'T') {

                                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                                resp.setExtraInfo(true);
                                resp.setOperacionMensaje("La venta " + invVentasTO.getVtaPeriodo() + "|" + invVentasTO.getVtaMotivo() + "|" + invVentasTO.getVtaNumero() + " se ha suprimido correctamente.");
                            } else {
                                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                                resp.setOperacionMensaje("La venta " + invVentasTO.getVtaPeriodo() + "|" + invVentasTO.getVtaMotivo() + "|" + invVentasTO.getVtaNumero() + "no se pudo suprimir.");
                            }
                        } else {
                            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                            resp.setOperacionMensaje("No se pudo suprimir la venta.");
                        }
                    }
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    resp.setOperacionMensaje("El período de la venta se encuentra CERRADO");
                }
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("La retención de la venta se encuentra en PROCESO DE AUTORIZACIÓN");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/desmayorizarVenta")
    public RespuestaWebTO desmayorizarVenta(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> rpt = ventasService.consultarVenta(map);
            if (rpt != null) {
                InvVentasTO invVentasTO = (InvVentasTO) rpt.get("ventasTO");
                AnxVentaTO anxVentaTO = (AnxVentaTO) rpt.get("anxVentaTO");
                String respues = ventasService.desmayorizarVenta(invVentasTO, anxVentaTO, sisInfoTO);
                if (respues != null) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(respues);
                    resp.setOperacionMensaje(respues);
                } else {
                    resp.setOperacionMensaje("La venta no se ha desmayorizado.");
                }
            } else {
                resp.setOperacionMensaje("La venta no se ha desmayorizado.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/desmayorizarLoteVenta")
    public RespuestaWebTO desmayorizarLoteVenta(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<InvListaConsultaVentaTO> listado = UtilsJSON.jsonToList(InvListaConsultaVentaTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        String[] listaMensajes = new String[]{};
        ArrayList<String> listaMensajesEnviar = new ArrayList<String>();
        try {
            int contador = 0;
            String respuestaCadena = "";
            StringBuilder mensaje = new StringBuilder();
            for (InvListaConsultaVentaTO venta : listado) {
                if (venta.getVtaStatus().equalsIgnoreCase("ANULADO") || venta.getVtaStatus().equalsIgnoreCase("PENDIENTE")) {
                    mensaje.append("\n").append("No se puede Desmayorizar la venta ").append(venta.getVtaStatus()).append(" ya ha sido ANULADA o está PENDIENTE");
                    contador++;
                } else {
                    String[] numero = venta.getVtaNumero().split("\\|");
                    InvVentas invVentas = ventasDao.buscarInvVentas(sisInfoTO.getEmpresa(), numero[0], numero[1], numero[2]);
                    InvVentasTO ventasTO = ConversionesInventario.convertirInvVentas_InvVentasTO(invVentas);
                    AnxVentaTO anxVentaTO = ventaDao.getAnexoVentaTO(sisInfoTO.getEmpresa(), ventasTO.getVtaPeriodo(), ventasTO.getVtaMotivo(), venta.getVtaDocumentoNumero());
                    String cadena = ventasService.desmayorizarLoteVenta(ventasTO, anxVentaTO, sisInfoTO) + "/";

                    if (cadena.charAt(0) != 'T') {
                        mensaje.append("FLa venta " + venta.getVtaNumero() + " no se pudo desmayorizar" + "/");
                        contador++;
                    } else {
                        mensaje.append(cadena.substring(0, cadena.length()));
                    }
                }
            }
            if (contador == 0) {
                respuestaCadena = 'T' + mensaje.toString();
            } else {
                respuestaCadena = 'F' + mensaje.toString();
            }
            String respues = respuestaCadena;

            listaMensajes = respues.substring(1).split("\\/");
            for (int i = 0; i < listaMensajes.length; i++) {
                if (!listaMensajes[i].equals(".")) {
                    listaMensajesEnviar.add(listaMensajes[i]);
                }
            }

            if (respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(listaMensajesEnviar);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se desmayorizó la venta.");
                resp.setExtraInfo(listaMensajesEnviar);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaCrudProductos")
    public RespuestaWebTO obtenerDatosParaCrudProductos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = productoService.obtenerDatosParaCrudProductos(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron los datos para generar producto.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getConteoNumeroFacturaVenta")
    public String getConteoNumeroFacturaVenta(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresaCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empresaCodigo"));
        String compDocumentoTipo = UtilsJSON.jsonToObjeto(String.class, map.get("compDocumentoTipo"));
        String compDocumentoNumero = UtilsJSON.jsonToObjeto(String.class, map.get("compDocumentoNumero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventasService.getConteoNumeroFacturaVenta(empresaCodigo, compDocumentoTipo, compDocumentoNumero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/validarInvContableComprasDetalleTO")
    public MensajeTO validarInvContableComprasDetalleTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String compraNumero = UtilsJSON.jsonToObjeto(String.class, map.get("compraNumero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasService.validarInvContableComprasDetalleTO(empresa, periodo, motivo, compraNumero, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarInvContableComprasTO")
    public MensajeTO insertarInvContableComprasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String compraNumero = UtilsJSON.jsonToObjeto(String.class, map.get("compraNumero"));
        String codigoUsuario = UtilsJSON.jsonToObjeto(String.class, map.get("codigoUsuario"));
        boolean recontabilizar = UtilsJSON.jsonToObjeto(boolean.class, map.get("recontabilizar"));
        String conNumero = UtilsJSON.jsonToObjeto(String.class, map.get("conNumero"));
        boolean recontabilizarSinPendiente = UtilsJSON.jsonToObjeto(boolean.class, map.get("recontabilizarSinPendiente"));
        String tipCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("tipCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasService.insertarInvContableComprasTO(empresa, periodo, motivo, compraNumero, codigoUsuario, recontabilizar, conNumero, recontabilizarSinPendiente, tipCodigo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarInvContableVentasTO")
    public RespuestaWebTO insertarInvContableVentasTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String ventaNumero = UtilsJSON.jsonToObjeto(String.class, map.get("ventaNumero"));
        String codigoUsuario = UtilsJSON.jsonToObjeto(String.class, map.get("codigoUsuario"));
        boolean recontabilizar = UtilsJSON.jsonToObjeto(boolean.class, map.get("recontabilizar"));
        String conNumero = UtilsJSON.jsonToObjeto(String.class, map.get("conNumero"));
        String tipCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("tipCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO respues = ventasService.insertarInvContableVentasTO(empresa, periodo, motivo, ventaNumero, codigoUsuario, recontabilizar, conNumero, tipCodigo, sisInfoTO);
            if (respues != null && respues.getMensaje() != null && respues.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues.getMap().get("conContable"));
                resp.setOperacionMensaje(respues.getMensaje().substring(1));
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respues != null && respues.getMensaje() != null ? respues.getMensaje().substring(1) + respues.getListaErrores1() : "No se ha contabilizado la venta.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getInvVentaCabeceraTO")
    public InvVentaCabeceraTO getInvVentaCabeceraTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoPeriodo = UtilsJSON.jsonToObjeto(String.class, map.get("codigoPeriodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroVenta = UtilsJSON.jsonToObjeto(String.class, map.get("numeroVenta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventasService.getInvVentaCabeceraTO(empresa, codigoPeriodo, motivo, numeroVenta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvProformaCabeceraTO")
    public InvProformaCabeceraTO getInvProformaCabeceraTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoPeriodo = UtilsJSON.jsonToObjeto(String.class, map.get("codigoPeriodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroProforma = UtilsJSON.jsonToObjeto(String.class, map.get("numeroProforma"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proformasService.getInvProformaCabeceraTO(empresa, codigoPeriodo, motivo, numeroProforma);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvConsumoCabeceraTO")
    public InvConsumosTO getInvConsumoCabeceraTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String codigoPeriodo = UtilsJSON.jsonToObjeto(String.class,
                map.get("codigoPeriodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class,
                map.get("motivo"));
        String numeroConsumo = UtilsJSON.jsonToObjeto(String.class,
                map.get("numeroConsumo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return consumosService.getInvConsumoCabeceraTO(empresa, codigoPeriodo, motivo, numeroConsumo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvVentasDetalleTO")
    public List<InvListaDetalleVentasTO> getListaInvVentasDetalleTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class,
                map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class,
                map.get("motivo"));
        String numeroVentas = UtilsJSON.jsonToObjeto(String.class,
                map.get("numeroVentas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return ventasDetalleService.getListaInvVentasDetalleTO(empresa, periodo, motivo, numeroVentas);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvConsumoDetalleTO")
    public List<InvListaDetalleConsumoTO> getListaInvConsumoDetalleTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class,
                map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class,
                map.get("motivo"));
        String numeroConsumo = UtilsJSON.jsonToObjeto(String.class,
                map.get("numeroConsumo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return consumosDetalleService.getListaInvConsumoDetalleTO(empresa, periodo, motivo, numeroConsumo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarInvComprasTO")
    public RespuestaWebTO modificarInvComprasTO(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasTO invComprasTO = UtilsJSON.jsonToObjeto(InvComprasTO.class, map.get("invComprasTO"));
        List<InvComprasDetalleTO> listaInvComprasDetalleTO = UtilsJSON.jsonToList(InvComprasDetalleTO.class, map.get("listaInvComprasDetalleTO"));
        List<InvComprasDetalleTO> listaInvComprasEliminarDetalleTO = UtilsJSON.jsonToList(InvComprasDetalleTO.class, map.get("listaInvComprasEliminarDetalleTO"));
        AnxCompraTO anxCompraTO = UtilsJSON.jsonToObjeto(AnxCompraTO.class, map.get("anxCompraTO"));
        List<AnxCompraDetalleTO> anxCompraDetalleTO = UtilsJSON.jsonToList(AnxCompraDetalleTO.class, map.get("anxCompraDetalleTO"));
        List<AnxCompraDetalleTO> anxCompraDetalleEliminarTO = UtilsJSON.jsonToList(AnxCompraDetalleTO.class, map.get("anxCompraDetalleEliminarTO"));
        List<AnxCompraReembolsoTO> anxCompraReembolsoTO = UtilsJSON.jsonToList(AnxCompraReembolsoTO.class, map.get("anxCompraReembolsoTO"));
        List<AnxCompraReembolsoTO> anxCompraReembolsoEliminarTO = UtilsJSON.jsonToList(AnxCompraReembolsoTO.class, map.get("anxCompraReembolsoEliminarTO"));
        List<AnxCompraFormaPagoTO> anxCompraFormaPagoTO = UtilsJSON.jsonToList(AnxCompraFormaPagoTO.class, map.get("anxCompraFormaPagoTO"));
        List<AnxCompraFormaPagoTO> anxCompraFormaPagoEliminarTO = UtilsJSON.jsonToList(AnxCompraFormaPagoTO.class, map.get("anxCompraFormaPagoEliminarTO"));
        boolean desmayorizar = UtilsJSON.jsonToObjeto(boolean.class, map.get("desmayorizar"));
        boolean quitarAnulado = UtilsJSON.jsonToObjeto(boolean.class, map.get("quitarAnulado"));
        InvComprasMotivoAnulacion invComprasMotivoAnulacion = UtilsJSON.jsonToObjeto(InvComprasMotivoAnulacion.class, map.get("invComprasMotivoAnulacion"));
        List<InvAdjuntosCompras> listImagen = UtilsJSON.jsonToList(InvAdjuntosCompras.class, map.get("listImagen"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = null;
        try {
            mensajeTO = comprasService.modificarInvComprasTO(invComprasTO, listaInvComprasDetalleTO, listaInvComprasEliminarDetalleTO, anxCompraTO, anxCompraDetalleTO, anxCompraDetalleEliminarTO,
                    anxCompraReembolsoTO, anxCompraReembolsoEliminarTO, anxCompraFormaPagoTO, anxCompraFormaPagoEliminarTO, desmayorizar, quitarAnulado, invComprasMotivoAnulacion, listImagen, sisInfoTO);
            if (!desmayorizar && mensajeTO.getMensaje().charAt(0) == 'T') {
                if (!invComprasTO.getCompPendiente()) {
                    comprasService.quitarPendiente(invComprasTO);
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMensaje());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha modificado compra.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarInvVentasTO")
    public RespuestaWebTO modificarInvVentasTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvVentasTO invVentasTO = UtilsJSON.jsonToObjeto(InvVentasTO.class, map.get("invVentasTO"));
        List<InvVentasDetalleTO> listaInvVentasDetalleTO = UtilsJSON.jsonToList(InvVentasDetalleTO.class, map.get("listaInvVentasDetalleTO"));
        List<InvVentasDetalleTO> listaInvVentasEliminarDetalleTO = UtilsJSON.jsonToList(InvVentasDetalleTO.class, map.get("listaInvVentasEliminarDetalleTO"));
        boolean desmayorizar = UtilsJSON.jsonToObjeto(boolean.class, map.get("desmayorizar"));
        AnxVentaTO anxVentasTO = UtilsJSON.jsonToObjeto(AnxVentaTO.class, map.get("anxVentasTO"));
        boolean quitarAnulado = UtilsJSON.jsonToObjeto(boolean.class, map.get("quitarAnulado"));
        String tipoDocumentoComplemento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumentoComplemento"));
        String numeroDocumentoComplemento = UtilsJSON.jsonToObjeto(String.class, map.get("numeroDocumentoComplemento"));
        String motivoDocumentoComplemento = UtilsJSON.jsonToObjeto(String.class, map.get("motivoDocumentoComplemento"));
        InvVentasMotivoAnulacion invVentasMotivoAnulacion = UtilsJSON.jsonToObjeto(InvVentasMotivoAnulacion.class, map.get("invVentasMotivoAnulacion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvVentaGuiaRemision invVentaGuiaRemision = UtilsJSON.jsonToObjeto(InvVentaGuiaRemision.class, map.get("invVentaGuiaRemision"));
        List<InvVentasLiquidacionTO> listaInvVentasLiquidacionTO = UtilsJSON.jsonToList(InvVentasLiquidacionTO.class, map.get("listaInvVentasLiquidacionTO"));
        List<InvVentasLiquidacionTO> listaInvVentasLiquidacionTOEliminar = UtilsJSON.jsonToList(InvVentasLiquidacionTO.class, map.get("listaInvVentasLiquidacionTOEliminar"));
        AnxVentaExportacionTO anxVentaExportacionTO = UtilsJSON.jsonToObjeto(AnxVentaExportacionTO.class, map.get("anxVentaExportacionTO"));
        List<InvVentasDatosAdjuntos> listadoImagenes = UtilsJSON.jsonToList(InvVentasDatosAdjuntos.class, map.get("listaImagenes"));
        List<AnxVentaReembolsoTO> listAnxVentaReembolsoTO = UtilsJSON.jsonToList(AnxVentaReembolsoTO.class, map.get("listAnxVentaReembolsoTO"));
        List<AnxVentaReembolsoTO> listAnxVentaReembolsoEliminarTO = UtilsJSON.jsonToList(AnxVentaReembolsoTO.class, map.get("listAnxVentaReembolsoEliminarTO"));
        MensajeTO mensajeTO = null;
        try {
            mensajeTO = ventasService.modificarInventarioVentasTO(
                    invVentasTO, listaInvVentasDetalleTO,
                    listaInvVentasEliminarDetalleTO, desmayorizar,
                    anxVentasTO, quitarAnulado,
                    tipoDocumentoComplemento,
                    numeroDocumentoComplemento, motivoDocumentoComplemento, invVentasMotivoAnulacion,
                    invVentaGuiaRemision, listaInvVentasLiquidacionTO, listaInvVentasLiquidacionTOEliminar,
                    anxVentaExportacionTO,
                    listadoImagenes,
                    listAnxVentaReembolsoTO,
                    listAnxVentaReembolsoEliminarTO,
                    sisInfoTO);
            if (!desmayorizar && mensajeTO.getMensaje().charAt(0) == 'T') {
                if (!invVentasTO.getVtaPendiente()) {
                    ventasService.quitarPendiente(invVentasTO);
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMap());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) + mensajeTO.getListaErrores1() : "No se ha modificado venta.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getInvConsumos")
    public RespuestaWebTO getInvConsumos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvConsumos respues = consumosService.obtenerPorId(empresa, periodo, motivo, numero);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se obtuvo consumo.");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaCrudConsumos")
    public RespuestaWebTO obtenerDatosParaCrudConsumos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = consumosService.obtenerDatosParaCrudConsumos(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron los datos para generar consumos.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/desmayorizarConsumosPorLote")
    public RespuestaWebTO desmayorizarConsumoPorLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<InvListaConsultaConsumosTO> lista = UtilsJSON.jsonToList(InvListaConsultaConsumosTO.class, map.get("listadoConsumos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<String> resultados = consumosService.desmayorizarConsumosLote(empresa, lista, sisInfoTO);
            String desmayorizados = "";
            String noDesmayorizados = "";
            if (resultados != null && !resultados.isEmpty()) {
                for (String respue : resultados) {
                    if (respue.charAt(0) == 'T') {
                        desmayorizados = desmayorizados + "<li>" + respue.substring(1) + "</li>";
                    } else {
                        noDesmayorizados = noDesmayorizados + "<li>" + respue.substring(1) + "</li>";
                    }
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                desmayorizados = desmayorizados != null && !desmayorizados.equals("") ? "<div class='card border-success pt-2 pb-2'>" + desmayorizados + "</div>" : "";
                noDesmayorizados = noDesmayorizados != null && !noDesmayorizados.equals("") ? "<div class='card border-danger pt-2 pb-2'>" + noDesmayorizados + "</div>" : "";
                resp.setExtraInfo("<htlm>" + desmayorizados + noDesmayorizados + "</html>");
            } else {
                resp.setOperacionMensaje("Ocurrió un error al desmayorizar consumos.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/desmayorizarConsumo")
    public RespuestaWebTO desmayorizarConsumo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = consumosService.desmayorizarConsumo(empresa, periodo, motivo, numero, sisInfoTO);
            if (respues != null && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1));
                resp.setExtraInfo(respues.substring(1));
            } else {
                resp.setOperacionMensaje(respues != null ? respues.substring(1) : "El consumo no se ha desmayorizado.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarConsumo")
    public RespuestaWebTO eliminarConsumo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = consumosService.eliminarConsumo(empresa, periodo, motivo, numero, sisInfoTO);
            if (respues != null && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El consumo [Empresa=" + empresa + ", Periodo=" + periodo + ", Motivo=" + motivo + ", Numero=" + numero + "] se eliminó correctamente.");
                resp.setExtraInfo(respues.substring(1));
            } else {
                resp.setOperacionMensaje(respues != null ? respues.substring(1) : "El consumo no se ha eliminado.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/anularConsumo")
    public RespuestaWebTO anularConsumo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = consumosService.anularConsumo(empresa, periodo, motivo, numero, sisInfoTO);
            if (respues != null && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1));
                resp.setExtraInfo(respues.substring(1));
            } else {
                resp.setOperacionMensaje(respues != null ? respues.substring(1) : "El consumo no se ha anulado.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/restaurarConsumo")
    public RespuestaWebTO restaurarConsumo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = consumosService.restaurarConsumo(empresa, periodo, motivo, numero, sisInfoTO);
            if (respues != null && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1));
                resp.setExtraInfo(respues.substring(1));
            } else {
                resp.setOperacionMensaje(respues != null ? respues.substring(1) : "El consumo no se ha restaurado.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarModificarInvConsumos")
    public RespuestaWebTO insertarModificarInvConsumos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvConsumos invConsumos = UtilsJSON.jsonToObjeto(InvConsumos.class, map.get("invConsumos"));
        List<InvConsumosDetalle> listaInvConsumosDetalle = UtilsJSON.jsonToList(InvConsumosDetalle.class, map.get("listaInvConsumosDetalle"));
        List<InvConsumosDatosAdjuntosWebTO> listadoImagenes = UtilsJSON.jsonToList(InvConsumosDatosAdjuntosWebTO.class, map.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean pendiente = invConsumos.getConsPendiente();
            MensajeTO mensajeTO = consumosService.insertarModificarInvConsumos(invConsumos, listaInvConsumosDetalle, sisInfoTO, false, listadoImagenes);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                if (!pendiente) {
                    consumosService.quitarPendiente(invConsumos.getInvConsumosPK(), sisInfoTO);
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                InvConsumos consumo = (InvConsumos) mensajeTO.getMap().get("consumo");//quitar las series para que no viajen al cliente
                if (consumo != null && consumo.getInvConsumosDetalleList() != null && !consumo.getInvConsumosDetalleList().isEmpty()) {
                    for (InvConsumosDetalle detalle : invConsumos.getInvConsumosDetalleList()) {
                        detalle.setInvConsumosDetalleSeriesList(null);
                    }
                }
                mensajeTO.getMap().put("consumo", consumo);
                resp.setExtraInfo(mensajeTO.getMap());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null
                        ? mensajeTO.getMensaje().substring(1) + " " + mensajeTO.getListaErrores1()
                        : "No se ha guardado consumo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarImagenesConsumo")
    public RespuestaWebTO listarImagenesConsumo(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvConsumosPK invConsumosPK = UtilsJSON.jsonToObjeto(InvConsumosPK.class, map.get("invConsumosPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvConsumosDatosAdjuntosWebTO> respues = consumosService.listarImagenesDeConsumo(invConsumosPK);
            if (respues != null) {
                respues.forEach((detalle) -> {
                    detalle.setInvConsumos(new InvConsumos(detalle.getInvConsumos().getInvConsumosPK()));
                });
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron imágenes de consumos.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaProductosGeneralTO")
    public RespuestaWebTO getListaProductosGeneralTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        boolean incluirInactivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirInactivos"));
        boolean limite = UtilsJSON.jsonToObjeto(boolean.class, map.get("limite"));
        Integer precio = UtilsJSON.jsonToObjeto(Integer.class, map.get("precio"));
        boolean codigo = UtilsJSON.jsonToObjeto(boolean.class, map.get("codigo"));
        String buscarPorcodigo = UtilsJSON.jsonToObjeto(String.class, map.get("buscarPorcodigo"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvListaProductosGeneralTO> respues = productoSaldosService.getListaProductosTOWeb(empresa, busqueda, bodega, categoria, fecha, precio, incluirInactivos, limite, codigo, buscarPorcodigo, tipo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/importarProductosSinStock")
    public RespuestaWebTO importarProductosSinStock(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvListaProductosGeneralTO> respues = productoSaldosService.importarProductosSinStock(empresa, categoria);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteProductos")
    public @ResponseBody
    String generarReporteProductos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportProductos.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvListaProductosGeneralTO> listado = UtilsJSON.jsonToList(InvListaProductosGeneralTO.class, parametros.get("listInvListaProductosGeneralTO"));
        List<InvFunListadoProductosTO> listado2 = UtilsJSON.jsonToList(InvFunListadoProductosTO.class, parametros.get("listInvFunListadoProductosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteProductos(usuarioEmpresaReporteTO, listado, listado2);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteInvListaProductosGeneralTO")
    public @ResponseBody
    String exportarReporteInvListaProductosGeneralTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvListaProductosGeneralTO> listado = UtilsJSON.jsonToList(InvListaProductosGeneralTO.class, map.get("listInvListaProductosGeneralTO"));
        List<InvFunListadoProductosTO> listado2 = UtilsJSON.jsonToList(InvFunListadoProductosTO.class, map.get("listInvFunListadoProductosTO"));
        List<DetalleExportarFiltrado> listadoFiltrado = UtilsJSON.jsonToList(DetalleExportarFiltrado.class, map.get("listadoFiltrado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteProductos(usuarioEmpresaReporteTO, listado, listado2, listadoFiltrado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteInvFunListadoProductosTO")
    public @ResponseBody
    String exportarReporteInvFunListadoProductosTO(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvFunListadoProductosTO> listado = UtilsJSON.jsonToList(InvFunListadoProductosTO.class, map.get("listInvFunListadoProductosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteInvFunListadoProductosTO(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaClienteTO")
    public RespuestaWebTO getListaClienteTO(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class,
                map.get("busqueda"));

        boolean activo_Cliente = UtilsJSON.jsonToObjeto(boolean.class,
                map.get("activo_Cliente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            List<InvListaClienteTO> respues = clienteService.getListaClienteTO(empresa, busqueda, activo_Cliente);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados de la búsqueda de clientes.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaProveedoresTO")
    public RespuestaWebTO getListaProveedoresTO(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class,
                map.get("busqueda"));

        boolean activoProveedor = UtilsJSON.jsonToObjeto(boolean.class,
                map.get("activoProveedor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            List<InvListaProveedoresTO> respues = proveedorService.getListaProveedoresTO(empresa, busqueda, activoProveedor);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados de la búsqueda de proveedores");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getInvComboBodegaTO")
    public RespuestaWebTO getInvComboBodegaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvComboBodegaTO> respues = bodegaService.getInvComboBodegaTO(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron bodegas.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaCompraMotivoComboTO")
    public List<InvCompraMotivoComboTO> getListaCompraMotivoComboTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));

        boolean filtrarInactivos = UtilsJSON.jsonToObjeto(boolean.class,
                map.get("filtrarInactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return comprasMotivoService.getListaCompraMotivoComboTO(empresa, filtrarInactivos);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaVentaMotivoComboTO")
    public RespuestaWebTO getListaVentaMotivoComboTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean filtrarInactivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("filtrarInactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvVentaMotivoComboTO> respues = ventasMotivoService.getListaVentaMotivoComboTO(empresa, filtrarInactivos);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaProformaMotivoComboTO")
    public List<InvProformaMotivoComboTO> getListaProformaMotivoComboTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));

        boolean filtrarInactivos = UtilsJSON.jsonToObjeto(boolean.class,
                map.get("filtrarInactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return proformasMotivoService.getListaProformaMotivoComboTO(empresa, filtrarInactivos);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaCategoriaComboTO")
    public List<InvCategoriaComboProductoTO> getListaCategoriaComboTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return productoCategoriaService.getListaCategoriaComboTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*  INVENTARIO INVPRODUCTOPRESENTACIONUNIDADES*/
    @RequestMapping("/getListaPresentacionUnidadComboTO")
    public RespuestaWebTO getListaPresentacionUnidadComboTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvProductoPresentacionUnidadesComboListadoTO> respues = productoPresentacionUnidadesService.getListaPresentacionUnidadComboTO(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/accionInvProductoPresentacionUnidadesTO")
    public RespuestaWebTO accionInvProductoPresentacionUnidadesTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        resp.setOperacionMensaje("La operación no obtuvo ninguna respuesta");
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoPresentacionUnidadesTO invProductoPresentacionUnidadesTO = UtilsJSON.jsonToObjeto(InvProductoPresentacionUnidadesTO.class, map.get("invProductoPresentacionUnidadesTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = productoPresentacionUnidadesService.accionInvProductoPresentacionUnidadesTO(invProductoPresentacionUnidadesTO, accion, sisInfoTO);
            if (respues.length() > 0 && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues.substring(1));
                resp.setOperacionMensaje(respues.substring(1));
            } else if (respues.length() > 0) {
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (DataIntegrityViolationException ge) {
            resp.setOperacionMensaje("El detalle ingresado ya existe.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarInvProductoPresentacionUnidades")
    public RespuestaWebTO eliminarInvProductoPresentacionUnidades(@RequestBody String json) throws GeneralException, Exception, DataIntegrityViolationException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        InvProductoPresentacionUnidadesPK invProductoPresentacionUnidadesPK = UtilsJSON.jsonToObjeto(InvProductoPresentacionUnidadesPK.class,
                map.get("invProductoPresentacionUnidadesPK"));
        try {
            boolean respues = productoPresentacionUnidadesService.eliminarInvProductoPresentacionUnidades(invProductoPresentacionUnidadesPK, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El producto presentación unidad: Código " + invProductoPresentacionUnidadesPK.getPresuCodigo() + ", se ha eliminado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se ha eliminado producto presentación unidad.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (DataIntegrityViolationException ge) {
            resp.setOperacionMensaje("No se ha eliminado. Producto presentación unidad tiene movimientos.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteProductoPresentacionUnidad")
    public @ResponseBody
    String generarReporteProductoPresentacionUnidad(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportPresentacionUnidad.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvProductoPresentacionUnidadesComboListadoTO> listado = UtilsJSON.jsonToList(InvProductoPresentacionUnidadesComboListadoTO.class, parametros.get("listInvProductoPresentacionUnidadesTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReportePresentacionUnidad(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReportePresentacionUnidad")
    public @ResponseBody
    String exportarReportePresentacionUnidad(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvProductoPresentacionUnidadesComboListadoTO> listado = UtilsJSON.jsonToList(InvProductoPresentacionUnidadesComboListadoTO.class, map.get("listInvProductoPresentacionUnidadesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReportePresentacionUnidad(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*  INVENTARIO INVPRODUCTOMARCA*/
    @RequestMapping("/getInvMarcaComboListadoTO")
    public RespuestaWebTO getInvMarcaComboListadoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvProductoMarcaComboListadoTO> respues = productoMarcaService.getInvMarcaComboListadoTO(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/accionInvProductoMarcaTO")
    public RespuestaWebTO accionInvProductoMarcaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        resp.setOperacionMensaje("La operación no obtuvo ninguna respuesta");
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoMarcaTO invProductoMarcaTO = UtilsJSON.jsonToObjeto(InvProductoMarcaTO.class,
                map.get("invProductoMarcaTO"));

        char accion = UtilsJSON.jsonToObjeto(char.class,
                map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            String respues = this.productoMarcaService.accionInvProductoMarcaTO(invProductoMarcaTO, accion, sisInfoTO);
            if (respues != null && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues.substring(1));
                resp.setOperacionMensaje(respues.substring(1));
            } else if (respues.length() > 0) {
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (DataIntegrityViolationException ge) {
            resp.setOperacionMensaje("El detalle ingresado ya existe.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarInvProductoMarca")
    public RespuestaWebTO eliminarInvProductoMarca(@RequestBody String json) throws GeneralException, Exception, DataIntegrityViolationException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        InvProductoMarcaPK invProductoMarcaPK = UtilsJSON.jsonToObjeto(InvProductoMarcaPK.class,
                map.get("invProductoMarcaPK"));
        try {
            boolean respues = productoMarcaService.eliminarInvProductoMarca(invProductoMarcaPK, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La marca de producto: Código " + invProductoMarcaPK.getMarCodigo() + ", se ha eliminado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se ha eliminado marca de producto");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (DataIntegrityViolationException ge) {
            resp.setOperacionMensaje("No se ha eliminado. Marca de producto tiene movimientos.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteProductoMarca")
    public @ResponseBody
    String generarReporteProductoMarca(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportProductoMarca.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvProductoMarcaComboListadoTO> listado = UtilsJSON.jsonToList(InvProductoMarcaComboListadoTO.class, parametros.get("listInvProductoMarcaComboListadoTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteProductoMarca(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteProductoMarca")
    public @ResponseBody
    String exportarReporteProductoMarca(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvProductoMarcaComboListadoTO> listado = UtilsJSON.jsonToList(InvProductoMarcaComboListadoTO.class, map.get("listInvProductoMarcaComboListadoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteProductoMarca(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*  INVENTARIO INVVENDEDOR*/
    @RequestMapping("/accionInvVendedorTO")
    public RespuestaWebTO accionInvVendedorTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        resp.setOperacionMensaje("La operación no obtuvo ninguna respuesta");
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvVendedorTO invVendedorTO = UtilsJSON.jsonToObjeto(InvVendedorTO.class,
                map.get("invVendedorTO"));

        char accion = UtilsJSON.jsonToObjeto(char.class,
                map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            String respues = this.vendedorService.accionInvVendedorTO(invVendedorTO, accion, sisInfoTO);
            if (respues.length() > 0 && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues.substring(1));
                resp.setOperacionMensaje(respues.substring(1));
            } else if (respues.length() > 0) {
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (DataIntegrityViolationException ge) {
            resp.setOperacionMensaje("El nombre ingresado ya existe.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarInvVendedor")
    public RespuestaWebTO eliminarInvVendedor(@RequestBody String json) throws GeneralException, Exception, DataIntegrityViolationException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        InvVendedorPK invVendedorPK = UtilsJSON.jsonToObjeto(InvVendedorPK.class,
                map.get("invVendedorPK"));
        try {
            boolean respues = vendedorService.eliminarInvVendedor(invVendedorPK, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Se ha eliminado correctamente el vendedor.");
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se ha eliminado vendedor");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (DataIntegrityViolationException ge) {
            resp.setOperacionMensaje("No se ha eliminado. El vendedor tiene movimientos.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*  INVENTARIO INVPRESENTACIONCAJA*/
    @RequestMapping("/getListaPresentacionCajaComboTO")
    public RespuestaWebTO getListaPresentacionCajaComboTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvProductoPresentacionCajasComboListadoTO> respues = productoPresentacionCajasService.getListaPresentacionCajaComboTO(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/accionInvProductoPresentacionCajasTO")
    public RespuestaWebTO accionInvProductoPresentacionCajasTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        resp.setOperacionMensaje("La operación no obtuvo ninguna respuesta");
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoPresentacionCajasTO invProductoPresentacionCajasTO = UtilsJSON.jsonToObjeto(InvProductoPresentacionCajasTO.class, map.get("invProductoPresentacionCajasTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = this.productoPresentacionCajasService.accionInvProductoPresentacionCajasTO(invProductoPresentacionCajasTO, accion, sisInfoTO);
            if (respues.length() > 0 && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues.substring(1));
                resp.setOperacionMensaje(respues.substring(1));
            } else if (respues.length() > 0) {
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (DataIntegrityViolationException ge) {
            resp.setOperacionMensaje("El nombre ingresado ya existe.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarInvPresentacionCajas")
    public RespuestaWebTO eliminarInvPresentacionCajas(@RequestBody String json) throws GeneralException, Exception, DataIntegrityViolationException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvProductoPresentacionCajasPK invProductoPresentacionCajasPK = UtilsJSON.jsonToObjeto(InvProductoPresentacionCajasPK.class, map.get("invProductoPresentacionCajasPK"));
        try {
            boolean respues = productoPresentacionCajasService.eliminarInvPresentacionCajas(invProductoPresentacionCajasPK, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El producto presentación de caja: Código " + invProductoPresentacionCajasPK.getPrescCodigo() + ", se ha eliminado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se ha eliminado producto presentación cajas");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (DataIntegrityViolationException ge) {
            resp.setOperacionMensaje("No se ha eliminado. El producto presentación cajas tiene movimientos.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePresentacionCaja")
    public @ResponseBody
    String generarReportePresentacionCaja(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportPresentacionCaja.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvProductoPresentacionCajasComboListadoTO> listado = UtilsJSON.jsonToList(InvProductoPresentacionCajasComboListadoTO.class, parametros.get("listInvProductoPresentacionCajasComboListadoTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReportePresentacionCaja(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReportePresentacionCaja")
    public @ResponseBody
    String exportarReportePresentacionCaja(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvProductoPresentacionCajasComboListadoTO> listado = UtilsJSON.jsonToList(InvProductoPresentacionCajasComboListadoTO.class, map.get("listInvProductoPresentacionCajasComboListadoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReportePresentacionCaja(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*  INVENTARIO LISTA CATEGORIA CLIENTE*/
    @RequestMapping("/getListaCategoriaClienteComboTO")
    public RespuestaWebTO getListaCategoriaClienteComboTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvCategoriaClienteComboTO> respues = clienteCategoriaService.getListaCategoriaClienteComboTO(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron categorías de cliente.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*ANALISIS VENTAS VS COSTO*/
    @RequestMapping("/getInvFunVentasVsCostoTO")
    public RespuestaWebTO getInvFunVentasVsCostoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunVentasVsCostoTO> respues = ventasService.getInvFunVentasVsCostoTO(empresa, desde, hasta, bodega, cliente);
            if (respues != null && respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteInvFunVentasVsCosto")
    public @ResponseBody
    String generarReporteInvFunVentasVsCosto(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportInvFunVentasVsCosto.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, parametros.get("bodega"));
        String clienteId = UtilsJSON.jsonToObjeto(String.class, parametros.get("clienteId"));
        List<InvFunVentasVsCostoTO> invFunVentasVsCostoTO = UtilsJSON.jsonToList(InvFunVentasVsCostoTO.class, parametros.get("invFunVentasVsCostoTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteInvFunVentasVsCosto(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, bodega, clienteId, invFunVentasVsCostoTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteInvFunVentasVsCosto")
    public @ResponseBody
    String exportarReporteInvFunVentasVsCosto(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvFunVentasVsCostoTO> listado = UtilsJSON.jsonToList(InvFunVentasVsCostoTO.class, map.get("listInvFunVentasVsCostoTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteInvFunVentasVsCosto(usuarioEmpresaReporteTO, listado, fechaDesde, fechaHasta, cliente, bodega);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*Listado producto,precio y stock*/
    @RequestMapping("/getListaProductosTO")
    public RespuestaWebTO getListaProductosTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        boolean incluirInactivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirInactivos"));
        boolean limite = UtilsJSON.jsonToObjeto(boolean.class, map.get("limite"));
        boolean codigo = UtilsJSON.jsonToObjeto(boolean.class, map.get("codigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvListaProductosTO> respues = productoSaldosService.getListaProductosTO(empresa, busqueda, bodega, categoria, fecha, incluirInactivos, limite, codigo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListaProductos")
    public @ResponseBody
    String generarReporteListaProductos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportListaProductos.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, parametros.get("bodega"));
        List<InvListaProductosTO> listInvListaProductosTO = UtilsJSON.jsonToList(InvListaProductosTO.class, parametros.get("listInvListaProductosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteListaProductos(usuarioEmpresaReporteTO, bodega, listInvListaProductosTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarListadoProductosPreciosStock")
    public @ResponseBody
    String exportarListadoProductosPreciosStock(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvListaProductosTO> listado = UtilsJSON.jsonToList(InvListaProductosTO.class, map.get("listInvListaProductosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarListadoProductosPreciosStock(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*RECONSTRUCCION DE SALDOS COSTOS*/
    @RequestMapping("/getListadoProductosConError")
    public RespuestaWebTO getListadoProductosConError(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String condicion = UtilsJSON.jsonToObjeto(String.class, map.get("condicion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvProductosConErrorTO> respues = productoService.getListadoProductosConError(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteInvProductosConError")
    public @ResponseBody
    String generarReporteInvProductosConError(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportReporteInvProductosConError.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvProductosConErrorTO> listInvProductosConErrorTO = UtilsJSON.jsonToList(InvProductosConErrorTO.class, parametros.get("listInvProductosConErrorTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteInvProductosConError(usuarioEmpresaReporteTO, listInvProductosConErrorTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReconstruccionSaldosCostos")
    public @ResponseBody
    String exportarReconstruccionSaldosCostos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvProductosConErrorTO> listado = UtilsJSON.jsonToList(InvProductosConErrorTO.class, map.get("listInvProductosConErrorTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReconstruccionSaldosCostos(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getProductoTO")
    public List<InvProductoTO> getProductoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoService.getProductoTO(empresa, codigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerProductoTO")
    public RespuestaWebTO obtenerProductoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvProductoTO respues = productoService.obtenerProductoTO(empresa, codigo);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontró producto.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getProducto")
    public InvProducto getProducto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String codigo = UtilsJSON.jsonToObjeto(String.class,
                map.get("codigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return productoService.obtenerPorId(empresa, codigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getClienteTO")
    public List<InvClienteTO> getClienteTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String codigo = UtilsJSON.jsonToObjeto(String.class,
                map.get("codigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return clienteService.getClienteTO(empresa, codigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerClienteTO")
    public RespuestaWebTO obtenerClienteTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String codigo = UtilsJSON.jsonToObjeto(String.class,
                map.get("codigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            InvClienteTO respues = clienteService.obtenerClienteTO(empresa, codigo);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontró cliente.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/comprobarInvAplicaRetencionIva")
    public boolean comprobarInvAplicaRetencionIva(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String codigo = UtilsJSON.jsonToObjeto(String.class,
                map.get("codigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return proveedorService.comprobarInvAplicaRetencionIva(empresa, codigo);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/validarNumeroFacturaCompraProveedor")
    public RespuestaWebTO validarNumeroFacturaCompraProveedor(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String provCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("provCodigo"));
        String compDocumentoTipo = UtilsJSON.jsonToObjeto(String.class, map.get("compDocumentoTipo"));
        String compDocumentoNumero = UtilsJSON.jsonToObjeto(String.class, map.get("compDocumentoNumero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = comprasService.getConteoNumeroFacturaCompra(empresa, provCodigo, compDocumentoTipo, compDocumentoNumero);//Si es de diferente de null o "" entonces ya esta creado y no se puede usar
            if (respues == null || respues.equals("")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje("El número esta en uso");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getPrecioProductoUltimaCompra")
    public RespuestaWebTO getPrecioProductoUltimaCompra(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String produCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("produCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            BigDecimal respues = comprasService.getPrecioProductoUltimaCompra(empresa, produCodigo);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(0.000001);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setEstadoOperacion(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getComboFormaPagoCompra")
    public RespuestaWebTO getComboFormaPagoCompra(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvComboFormaPagoTO> respues = comprasFormaPagoService.getComboFormaPagoCompra(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron formas de pago.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getComboFormaPagoVenta")
    public RespuestaWebTO getComboFormaPagoVenta(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvComboFormaPagoTO> respues = ventasFormaPagoService.getComboFormaPagoVenta(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron formas de pago.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getFunComprasListado")
    public List<InvListaConsultaCompraTO> getFunComprasListado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class,
                map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class,
                map.get("fechaHasta"));
        String status = UtilsJSON.jsonToObjeto(String.class,
                map.get("status"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return comprasService.getFunComprasListado(empresa, fechaDesde, fechaHasta, status);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getFunVentasListado")
    public RespuestaWebTO getFunVentasListado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String status = UtilsJSON.jsonToObjeto(String.class, map.get("status"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvListaConsultaVentaTO> respues = ventasService.getFunVentasListado(empresa, fechaDesde, fechaHasta, status);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaInvConsultaVenta")
    public RespuestaWebTO getListaInvConsultaVenta(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        String tipoDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumento"));
        String nRegistros = UtilsJSON.jsonToObjeto(String.class, map.get("nRegistros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvListaConsultaVentaTO> respues = ventasService.getListaInvConsultaVentaPorTipoDoc(empresa, periodo, motivo, busqueda, nRegistros, tipoDocumento);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaInvConsultaVentaFiltrado")
    public RespuestaWebTO getListaInvConsultaVentaFiltrado(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        String nRegistros = UtilsJSON.jsonToObjeto(String.class, map.get("nRegistros"));
        String tipoDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumento"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvListaConsultaVentaTO> respues = ventasService.getListaInvConsultaVentaFiltrado(empresa, periodo, motivo, busqueda, nRegistros, tipoDocumento);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getFunConsumosListado")
    public List<InvListaConsultaConsumosTO> getFunConsumosListado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class,
                map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class,
                map.get("fechaHasta"));
        String status = UtilsJSON.jsonToObjeto(String.class,
                map.get("status"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return consumosService.getFunConsumosListado(empresa, fechaDesde, fechaHasta, status);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvConsultaConsumos")
    public RespuestaWebTO getListaInvConsultaConsumos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        String producto = UtilsJSON.jsonToObjeto(String.class, map.get("producto"));
        String empleado = UtilsJSON.jsonToObjeto(String.class, map.get("empleado"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        String nRegistros = UtilsJSON.jsonToObjeto(String.class, map.get("nRegistros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvListaConsultaConsumosTO> respues = consumosService.getListaInvConsultaConsumos(empresa, periodo, motivo, cliente, proveedor, producto, empleado, busqueda, nRegistros);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron consumos.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaInvConsumosImportarExportarTO")
    public List<InvConsumosImportarExportarTO> getListaInvConsumosImportarExportarTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class,
                map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class,
                map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return consumosService.getListaInvConsumosImportarExportarTO(empresa, desde, hasta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaConsumosMotivoComboTO")
    @Deprecated
    public List<InvConsumosMotivoComboTO> getListaConsumosMotivoComboTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));

        boolean filtrarInactivos = UtilsJSON.jsonToObjeto(boolean.class,
                map.get("filtrarInactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return consumosMotivoService.getListaConsumosMotivoComboTO(empresa, filtrarInactivos);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaConsumosMotivo")
    public List<InvConsumosMotivo> getListaConsumosMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));

        boolean filtrarInactivos = UtilsJSON.jsonToObjeto(boolean.class,
                map.get("filtrarInactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return consumosMotivoService.getListaConsumosMotivo(empresa, filtrarInactivos);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvMedidaTablaTO")
    public RespuestaWebTO getListaInvMedidaTablaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvMedidaComboTO> respues = productoMedidaService.getListaInvMedidaTablaTO(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron unidad de medidas para productos.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getInvListaConsumosMotivoTO")
    public List<InvListaConsumosMotivoTO> getInvListaConsumosMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return consumosMotivoService.getInvListaConsumosMotivoTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvKardexTO")
    public RespuestaWebTO getListaInvKardexTO(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String producto = UtilsJSON.jsonToObjeto(String.class, map.get("producto"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String promedio = UtilsJSON.jsonToObjeto(String.class, map.get("promedio"));
        Boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvKardexTO> respues = productoSaldosService.getListaInvKardexTO(empresa, bodega, producto, desde, hasta, promedio, incluirTodos);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados de kardex");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getInvComprasRecepcionTO")
    public RespuestaWebTO getInvComprasRecepcionTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvComprasRecepcionTO respuesta = comprasRecepcionService.getInvComprasRecepcionTO(empresa, periodo, motivo, numero);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarModificarComprasRecepcionTO")
    public RespuestaWebTO insertarModificarComprasRecepcionTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasRecepcionTO invComprasRecepcionTO = UtilsJSON.jsonToObjeto(InvComprasRecepcionTO.class, map.get("invComprasRecepcionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = comprasService.insertarModificarComprasRecepcionTO(invComprasRecepcionTO, sisInfoTO);
            if (respuesta != null && respuesta.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respuesta.substring(1));
                resp.setExtraInfo(respuesta.substring(1));
            } else {
                resp.setOperacionMensaje(respuesta != null ? respuesta.substring(1) : "No se modificó.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/guardarClaveAcceso")
    public RespuestaWebTO guardarClaveAcceso(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String codigoEmpresa = UtilsJSON.jsonToObjeto(String.class, map.get("codigoEmpresa"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String claveAcceso = UtilsJSON.jsonToObjeto(String.class, map.get("claveAcceso"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = comprasService.guardarClaveAcceso(codigoEmpresa, motivo, numero, periodo, claveAcceso, sisInfoTO);
            if (respues.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getPrecioFijoCategoriaProducto")
    public Boolean getPrecioFijoCategoriaProducto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String codigoCategoria = UtilsJSON.jsonToObjeto(String.class,
                map.get("codigoCategoria"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return productoCategoriaService.getPrecioFijoCategoriaProducto(empresa, codigoCategoria);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/getListaTransferenciaMotivoComboTO")
    public List<InvTransferenciaMotivoComboTO> getListaTransferenciaMotivoComboTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));

        boolean filtrarInactivos = UtilsJSON.jsonToObjeto(boolean.class,
                map.get("filtrarInactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return transferenciasMotivoService.getListaTransferenciaMotivoComboTO(empresa, filtrarInactivos);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvTransferenciasDetalleTO")
    public List<InvListaDetalleTransferenciaTO> getListaInvTransferenciasDetalleTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class,
                map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class,
                map.get("motivo"));
        String numeroTransferencia = UtilsJSON.jsonToObjeto(String.class,
                map.get("numeroTransferencia"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return transferenciasDetalleService.getListaInvTransferenciasDetalleTO(empresa, periodo, motivo,
                    numeroTransferencia);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvTransferenciasCabeceraTO")
    public RespuestaWebTO getInvTransferenciasCabeceraTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoPeriodo = UtilsJSON.jsonToObjeto(String.class, map.get("codigoPeriodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroTransferencia = UtilsJSON.jsonToObjeto(String.class, map.get("numeroTransferencia"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvTransferenciasTO respues = transferenciasService.getInvTransferenciasCabeceraTO(empresa, codigoPeriodo, motivo, numeroTransferencia);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontró transferencia.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosBasicosNuevaTransferencia")
    public RespuestaWebTO obtenerDatosBasicosNuevaTransferencia(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = transferenciasService.obtenerDatosBasicosNuevaTransferencia(map);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontro transferencia");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return resp;
    }

    @RequestMapping("/consultaTransferencia")
    public RespuestaWebTO consultaTransferencia(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = transferenciasService.consultarTransferencia(map);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontro transferencia");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return resp;
    }

    @RequestMapping("/consultaTransferenciaActivo")
    public RespuestaWebTO consultaTransferenciaActivo(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = transferenciasService.consultarTransferenciaActivo(map);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontro transferencia");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return resp;
    }

    @RequestMapping("/insertarInvTransferenciaTO")
    public RespuestaWebTO insertarInvTransferenciaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvTransferenciasTO invTransferenciasTO = UtilsJSON.jsonToObjeto(InvTransferenciasTO.class, map.get("invTransferenciasTO"));
        List<InvTransferenciasDetalleTO> listaInvTransferenciasDetalleTO = UtilsJSON.jsonToList(InvTransferenciasDetalleTO.class, map.get("listaInvTransferenciasDetalleTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO respues = transferenciasService.insertarInvTransferenciaTO(invTransferenciasTO, listaInvTransferenciasDetalleTO, sisInfoTO);
            if (respues.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.getMensaje().substring(1, respues.getMensaje().length()));
                resp.setExtraInfo(respues.getMap());
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respues.getMensaje().substring(1, respues.getMensaje().length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                resp.setOperacionMensaje("F" + e.getMessage());
            } else {
                resp.setOperacionMensaje("FLo sentimos, ocurrió un error inesperado!");
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return resp;
    }

    @RequestMapping("/modificarInvTransferenciasTO")
    public RespuestaWebTO modificarInvTransferenciasTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvTransferenciasTO invTransferenciasTO = UtilsJSON.jsonToObjeto(InvTransferenciasTO.class, map.get("invTransferenciasTO"));
        List<InvTransferenciasDetalleTO> listaInvTransferenciasDetalleTO = UtilsJSON.jsonToList(InvTransferenciasDetalleTO.class, map.get("listaInvTransferenciasDetalleTO"));
        List<InvTransferenciasDetalleTO> listaInvTransferenciasEliminarDetalleTO = UtilsJSON.jsonToList(InvTransferenciasDetalleTO.class, map.get("listaInvTransferenciasEliminarDetalleTO"));
        boolean desmayorizar = UtilsJSON.jsonToObjeto(boolean.class, map.get("desmayorizar"));
        InvTransferenciasMotivoAnulacion invTransferenciasMotivoAnulacion = UtilsJSON.jsonToObjeto(InvTransferenciasMotivoAnulacion.class, map.get("invTransferenciasMotivoAnulacion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO respues = transferenciasService.modificarInvTransferenciasTO(invTransferenciasTO, listaInvTransferenciasDetalleTO, listaInvTransferenciasEliminarDetalleTO, desmayorizar,
                    invTransferenciasMotivoAnulacion, sisInfoTO);
            if (respues.getMensaje().charAt(0) == 'T' && !desmayorizar && !invTransferenciasTO.getTransPendiente()) {
                transferenciasService.quitarPendiente(invTransferenciasTO);
            }
            if (respues.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.getMensaje().substring(1, respues.getMensaje().length()));
                resp.setExtraInfo(respues.getMap());
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respues.getMensaje().substring(1, respues.getMensaje().length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                resp.setOperacionMensaje("F" + e.getMessage());
            } else {
                resp.setOperacionMensaje("FLo sentimos, ocurrió un error inesperado!");
                envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            }
        }
        return resp;
    }

    @RequestMapping("/getFunListadoTransferencias")
    public List<InvListaConsultaTransferenciaTO> getFunListadoTransferencias(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class,
                map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class,
                map.get("fechaHasta"));
        String status = UtilsJSON.jsonToObjeto(String.class,
                map.get("status"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {

            return transferenciasService.getFunListadoTransferencias(empresa, fechaDesde, fechaHasta, status);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvConsultaTransferencias")
    public RespuestaWebTO getListaInvConsultaTransferencias(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        String nRegistros = UtilsJSON.jsonToObjeto(String.class, map.get("nRegistros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvListaConsultaTransferenciaTO> respues = transferenciasService.getListaInvConsultaTransferencias(empresa, periodo, motivo, busqueda, nRegistros);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaProductosCambiarPrecioCantidadTO")
    public RespuestaWebTO getListaProductosCambiarPrecioCantidadTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvListaProductosCambiarPrecioCantidadTO> respues = productoSaldosService.getListaProductosCambiarPrecioCantidadTO(empresa, busqueda, bodega, null);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron productos.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/invCambiarPrecioCantidadProducto")
    public RespuestaWebTO invCambiarPrecioCantidadProducto(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        List<InvListaProductosCambiarPrecioCantidadTO> invListaProductosCambiarPrecioCantidadTOs = UtilsJSON.jsonToList(
                InvListaProductosCambiarPrecioCantidadTO.class, map.get("invListaProductosCambiarPrecioCantidadTOs"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = productoService.invCambiarPrecioCantidadProducto(empresa, usuario, invListaProductosCambiarPrecioCantidadTOs, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMap());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setExtraInfo(mensajeTO != null ? mensajeTO.getListaErrores1() : new ArrayList<>());
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado la guía de remisión.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getEstadoCCCVT")
    public InvEstadoCCCVT getEstadoCCCVT(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class,
                map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class,
                map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class,
                map.get("numero"));
        String proceso = UtilsJSON.jsonToObjeto(String.class,
                map.get("proceso"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            if (proceso.trim().indexOf("COMPRA") >= 0) {
                return comprasService.getEstadoCCCVT(empresa, periodo, motivo, numero);
            } else if (proceso.trim().equals("CONSUMO")) {
                return consumosService.getEstadoCCCVT(empresa, periodo, motivo, numero);
            } else if (proceso.trim().indexOf("VENTA") >= 0) {
                return ventasService.getEstadoCCCVT(empresa, periodo, motivo, numero);
            } else if (proceso.trim().equals("PROFORMA")) {
                return proformasService.getEstadoCCCVT(empresa, periodo, motivo, numero);
            } else if (proceso.trim().equals("TRANSFERENCIA")) {
                return transferenciasService.getEstadoCCCVT(empresa, periodo, motivo, numero);
            } else if (proceso.trim().equals("CONTABLE")) {
                return contableService.getEstadoCCCVT(empresa, periodo, motivo, numero);
            }
            return null;
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/invProductoSincronizar")
    public List<InvProductoSincronizarTO> invProductoSincronizar(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresaOrigen = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresaOrigen"));
        String empresaDestino = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresaDestino"));
        String usuario = UtilsJSON.jsonToObjeto(String.class,
                map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return productoService.invProductoSincronizar(empresaOrigen, empresaDestino, usuario, sisInfoTO);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/invListadoPagosTO")
    public List<InvListadoPagosTO> invListadoPagosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class,
                map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class,
                map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class,
                map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return comprasService.invListadoPagosTO(empresa, periodo, motivo, numero);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvFunVentasTO")
    public List<InvFunVentasTO> getInvFunVentasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class,
                map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class,
                map.get("hasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class,
                map.get("motivo"));
        String cliente = UtilsJSON.jsonToObjeto(String.class,
                map.get("cliente"));
        String documento = UtilsJSON.jsonToObjeto(String.class,
                map.get("documento"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        String grupo_empresarial = UtilsJSON.jsonToObjeto(String.class, map.get("grupo_empresarial"));
        try {
            return ventasService.getInvFunVentasTO(empresa, desde, hasta, motivo, cliente, documento, grupo_empresarial);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvFunListadoProductosTO")
    public RespuestaWebTO getInvFunListadoProductosTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunListadoProductosTO> respues = productoService.getInvFunListadoProductosTO(empresa, categoria, busqueda);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron productos.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getPuedeEliminarProducto")
    public RespuestaWebTO getPuedeEliminarProducto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String producto = UtilsJSON.jsonToObjeto(String.class, map.get("producto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Boolean respues = productoService.getPuedeEliminarProducto(empresa, producto);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(respues);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getPrecioProductoPorCantidad")
    public RespuestaWebTO getPrecioProductoPorCantidad(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String cliente = UtilsJSON.jsonToObjeto(String.class,
                map.get("cliente"));
        String codProducto = UtilsJSON.jsonToObjeto(String.class,
                map.get("codProducto"));
        BigDecimal cantidad = UtilsJSON.jsonToObjeto(BigDecimal.class,
                map.get("cantidad"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            BigDecimal respues = productoService.getPrecioProductoPorCantidad(empresa, cliente, codProducto, cantidad);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontró precio por cantidad del producto.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getCantidad3")
    public BigDecimal getCantidad3(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String codProducto = UtilsJSON.jsonToObjeto(String.class,
                map.get("codProducto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return productoService.getCantidad3(empresa, codProducto);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvSecuenciaComprobanteTO")
    public List<InvSecuenciaComprobanteTO> getInvSecuenciaComprobanteTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class,
                map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class,
                map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return comprasService.getInvSecuenciaComprobanteTO(empresa, fechaDesde, fechaHasta);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*  INVENTARIO PRODUCTO TIPO */
    @RequestMapping("/getInvProductoTipoComboListadoTO")
    public RespuestaWebTO getInvProductoTipoComboListadoTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String accion = UtilsJSON.jsonToObjeto(String.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvProductoTipoComboTO> respues = productoTipoService.getInvProductoTipoComboListadoTO(empresa, accion);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron tipos de producto.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/accionInvProductoTipo")
    public RespuestaWebTO accionInvProductoTipo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        resp.setOperacionMensaje("La operación no obtuvo ninguna respuesta");
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoTipoTO invProductoTipoTO = UtilsJSON.jsonToObjeto(InvProductoTipoTO.class, map.get("invProductoTipoTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = productoTipoService.accionInvProductoTipo(invProductoTipoTO, accion, sisInfoTO);
            if (respues != null && respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues.substring(1));
                resp.setOperacionMensaje(respues.substring(1));
            } else if (respues.length() > 0) {
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarInvProductoTipo")
    public RespuestaWebTO eliminarInvProductoTipo(@RequestBody String json) throws GeneralException, Exception, DataIntegrityViolationException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvProductoTipoPK invProductoTipoPK = UtilsJSON.jsonToObjeto(InvProductoTipoPK.class, map.get("invProductoTipoPK"));
        try {
            boolean respues = productoTipoService.eliminarInvProductoTipo(invProductoTipoPK, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Se ha eliminado tipo de manera exitosa");
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se pudo eliminar tipo");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (DataIntegrityViolationException ge) {
            resp.setOperacionMensaje("No se puede eliminar. El tipo tiene movimientos.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/comprobarEliminarInvProductoTipo")
    public Boolean comprobarEliminarInvProductoTipo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return this.productoTipoService.comprobarEliminarInvProductoTipo(empresa, codigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteProductoTipo")
    public @ResponseBody
    String generarReporteProductoTipo(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportProductoTipo.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvProductoTipoComboTO> listado = UtilsJSON.jsonToList(InvProductoTipoComboTO.class, parametros.get("listInvProductoTipoComboTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteProductoTipo(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteProductoTipo")
    public @ResponseBody
    String exportarReporteProductoTipo(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvProductoTipoComboTO> listado = UtilsJSON.jsonToList(InvProductoTipoComboTO.class, map.get("listInvProductoTipoComboTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteProductoTipo(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*Listado de compras*/
    @RequestMapping("/getInvFunComprasTO")
    public RespuestaWebTO getInvFunComprasTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        String documento = UtilsJSON.jsonToObjeto(String.class, map.get("documento"));
        String formaPago = UtilsJSON.jsonToObjeto(String.class, map.get("formaPago"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunComprasTO> respues = comprasService.getInvFunComprasTO(empresa, desde, hasta, motivo, proveedor, documento, formaPago);
            if (respues != null && respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getInvFunComprasTOAgrupadoTipoContribuyente")
    public RespuestaWebTO getInvFunComprasTOAgrupadoTipoContribuyente(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        String documento = UtilsJSON.jsonToObjeto(String.class, map.get("documento"));
        String formaPago = UtilsJSON.jsonToObjeto(String.class, map.get("formaPago"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunComprasTO> respues = comprasService.getInvFunComprasTOAgrupadoTipoContribuyente(empresa, desde, hasta, motivo, proveedor, documento, formaPago);
            if (respues != null && respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getInvFunComprasTOAgrupadoTipoDocumento")
    public RespuestaWebTO getInvFunComprasTOAgrupadoTipoDocumento(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        String documento = UtilsJSON.jsonToObjeto(String.class, map.get("documento"));
        String formaPago = UtilsJSON.jsonToObjeto(String.class, map.get("formaPago"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunComprasTO> respues = comprasService.getInvFunComprasTOAgrupadoTipoDocumento(empresa, desde, hasta, motivo, proveedor, documento, formaPago);
            if (respues != null && respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoCompras")
    public @ResponseBody
    String generarReporteListadoCompras(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportListadoCompras.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, parametros.get("motivo"));
        String proveedorId = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedorId"));
        String documento = UtilsJSON.jsonToObjeto(String.class, parametros.get("documento"));
        List<InvFunComprasTO> listInvFunComprasTO = UtilsJSON.jsonToList(InvFunComprasTO.class, parametros.get("listInvFunComprasTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteListadoCompras(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, motivo, proveedorId, documento, listInvFunComprasTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteListadoCompras")
    public @ResponseBody
    String exportarReporteListadoCompras(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvFunComprasTO> listado = UtilsJSON.jsonToList(InvFunComprasTO.class, map.get("listInvFunComprasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteListadoCompras(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*Listado de consumos*/
    @RequestMapping("/getInvFunConsumosTO")
    public RespuestaWebTO getInvFunConsumosTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunConsumosTO> respues = consumosService.getInvFunConsumosTO(empresa, desde, hasta, motivo);
            if (respues != null && respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoConsumos")
    public @ResponseBody
    String generarReporteListadoConsumos(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportListadoConsumos.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<InvFunConsumosTO> listInvFunConsumosTO = UtilsJSON.jsonToList(InvFunConsumosTO.class, parametros.get("listInvFunConsumosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteListadoConsumos(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listInvFunConsumosTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteListadoConsumos")
    public @ResponseBody
    String exportarReporteListadoConsumos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvFunConsumosTO> listado = UtilsJSON.jsonToList(InvFunConsumosTO.class, map.get("listInvFunConsumosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteListadoConsumos(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*Listado de ventas*/
    @RequestMapping("/listarInvFunVentasTO")
    public RespuestaWebTO listarInvFunVentasTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String documento = UtilsJSON.jsonToObjeto(String.class, map.get("documento"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String estado = UtilsJSON.jsonToObjeto(String.class, map.get("estado"));
        String grupo_empresarial = UtilsJSON.jsonToObjeto(String.class, map.get("grupo_empresarial"));
        String formaCobro = UtilsJSON.jsonToObjeto(String.class, map.get("formaCobro"));
        boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunVentasTO> respues = ventasService.listarInvFunVentasTO(empresa, desde, hasta, motivo, cliente, documento, sector, estado, grupo_empresarial, formaCobro, incluirTodos);
            if (respues != null && respues.size() >= 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarInvFunVentasTOAgrupadoTipoDocumento")
    public RespuestaWebTO listarInvFunVentasTOAgrupadoTipoDocumento(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String documento = UtilsJSON.jsonToObjeto(String.class, map.get("documento"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String estado = UtilsJSON.jsonToObjeto(String.class, map.get("estado"));
        String grupo_empresarial = UtilsJSON.jsonToObjeto(String.class, map.get("grupo_empresarial"));
        String formaCobro = UtilsJSON.jsonToObjeto(String.class, map.get("formaCobro"));
        boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunVentasTO> respues = ventasService.listarInvFunVentasTOAgrupadoTipoDocumento(empresa, desde, hasta, motivo, cliente, documento, sector, estado, grupo_empresarial, formaCobro, incluirTodos);
            if (respues != null && respues.size() >= 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarInvFunVentasTOAgrupadoTipoContribuyente")
    public RespuestaWebTO listarInvFunVentasTOAgrupadoTipoContribuyente(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String documento = UtilsJSON.jsonToObjeto(String.class, map.get("documento"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String estado = UtilsJSON.jsonToObjeto(String.class, map.get("estado"));
        String grupo_empresarial = UtilsJSON.jsonToObjeto(String.class, map.get("grupo_empresarial"));
        String formaCobro = UtilsJSON.jsonToObjeto(String.class, map.get("formaCobro"));
        boolean incluirTodos = UtilsJSON.jsonToObjeto(boolean.class, map.get("incluirTodos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunVentasTO> respues = ventasService.listarInvFunVentasTOAgrupadoTipoContribuyente(empresa, desde, hasta, motivo, cliente, documento, sector, estado, grupo_empresarial, formaCobro, incluirTodos);
            if (respues != null && respues.size() >= 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }


    /*Listado de ventas vendedor*/
    @RequestMapping("/listarInvFunVentasVendedorTO")
    public RespuestaWebTO listarInvFunVentasVendedorTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunVentasVendedorTO> respues = ventasService.listarInvFunVentasVendedorTO(empresa, desde, hasta);
            if (respues != null && respues.size() > 1) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoVentas")
    public @ResponseBody
    String generarReporteListadoVentas(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportListadoVentas.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, parametros.get("motivo"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, parametros.get("cliente"));
        String documento = UtilsJSON.jsonToObjeto(String.class, parametros.get("documento"));
        List<InvFunVentasTO> listInvFunVentasTO = UtilsJSON.jsonToList(InvFunVentasTO.class, parametros.get("listInvFunVentasTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteListadoVentas(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, motivo, cliente, documento, listInvFunVentasTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteListadoVentas")
    public @ResponseBody
    String exportarReporteListadoVentas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String documento = UtilsJSON.jsonToObjeto(String.class, map.get("documento"));
        List<InvFunVentasTO> listInvFunVentasTO = UtilsJSON.jsonToList(InvFunVentasTO.class, map.get("listInvFunVentasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteListadoVentas(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, motivo, cliente, documento, listInvFunVentasTO);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteListadoVentasVendedor")
    public @ResponseBody
    String exportarReporteListadoVentasVendedor(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String documento = UtilsJSON.jsonToObjeto(String.class, map.get("documento"));
        List<InvFunVentasVendedorTO> listInvFunVentasTO = UtilsJSON.jsonToList(InvFunVentasVendedorTO.class, map.get("listInvFunVentasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteListadoVentasVendedor(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, motivo, cliente, documento, listInvFunVentasTO);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /**/
    @RequestMapping("/buscarConteoCliente")
    public Boolean buscarConteoCliente(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class,
                map.get("empCodigo"));
        String codigoCliente = UtilsJSON.jsonToObjeto(String.class,
                map.get("codigoCliente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return clienteService.buscarConteoCliente(empCodigo, codigoCliente);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/buscarConteoProveedor")
    public RespuestaWebTO buscarConteoProveedor(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String codigoProveedor = UtilsJSON.jsonToObjeto(String.class, map.get("codigoProveedor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Boolean respues = proveedorService.buscarConteoProveedor(empCodigo, codigoProveedor);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("Proveedor no existe.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getComboinvListaVendedorTOs")
    public RespuestaWebTO getComboinvListaVendedorTOs(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));

        try {
            List<InvVendedorComboListadoTO> respues = vendedorService.getComboinvListaVendedorTOs(empresa, busqueda);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron vendedores");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getInvFunUltimasComprasTOs")
    public List<InvFunUltimasComprasTO> getInvFunUltimasComprasTOs(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String producto = UtilsJSON.jsonToObjeto(String.class,
                map.get("producto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return comprasService.getInvFunUltimasComprasTOs(empresa, producto);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvVentaRetencionesTO")
    public InvVentaRetencionesTO getInvVentaRetencionesTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String codigoEmpresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("codigoEmpresa"));
        String facturaNumero = UtilsJSON.jsonToObjeto(String.class,
                map.get("facturaNumero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return ventasService.getInvVentaRetencionesTO(codigoEmpresa, facturaNumero);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getVenta")
    public Object[] getVenta(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String perCodigo = UtilsJSON.jsonToObjeto(String.class,
                map.get("perCodigo"));
        String motCodigo = UtilsJSON.jsonToObjeto(String.class,
                map.get("motCodigo"));
        String compNumero = UtilsJSON.jsonToObjeto(String.class,
                map.get("compNumero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return ventasService.getVenta(empresa, perCodigo, motCodigo, compNumero);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    // * **************** REPORTES ******************
    @RequestMapping("/generarReporteInvKardex")
    public @ResponseBody
    String generarReporteInvKardex(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
        String nombreReporte;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String nombreProducto = UtilsJSON.jsonToObjeto(String.class, map.get("nombreProducto"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<InvKardexTO> listInvKardexTO = UtilsJSON.jsonToList(InvKardexTO.class, map.get("listInvKardexTO"));
        boolean banderaCosto = UtilsJSON.jsonToObjeto(boolean.class, map.get("banderaCosto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            nombreReporte = banderaCosto ? "reportInventarioKardexValorizado.jrxml" : "reportInventarioKardex.jrxml";
            respuesta = reporteInventarioService.generarReporteInvKardex(usuarioEmpresaReporteTO, nombreProducto, fechaDesde, fechaHasta, listInvKardexTO, banderaCosto);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteInvKardex")
    public @ResponseBody
    String exportarReporteInvKardex(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String nombreProducto = UtilsJSON.jsonToObjeto(String.class, map.get("nombreProducto"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<InvKardexTO> listInvKardexTO = UtilsJSON.jsonToList(InvKardexTO.class, map.get("listInvKardexTO"));
        boolean banderaCosto = UtilsJSON.jsonToObjeto(boolean.class, map.get("banderaCosto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteInvKardex(usuarioEmpresaReporteTO, nombreProducto, fechaDesde, fechaHasta, listInvKardexTO, banderaCosto);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteTrasferencias")
    public @ResponseBody
    String generarReporteTrasferencias(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
        String nombreReporte = "reportComprobanteTrasferencias.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<String> numero = UtilsJSON.jsonToList(String.class, map.get("numero"));
        List<String> comprobante = UtilsValidacion.separarComprobante(numero.get(0));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteTrasferencias(empresa, comprobante.get(0), comprobante.get(1), comprobante.get(2), usuarioEmpresaReporteTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteTrasferenciasListado")
    public @ResponseBody
    String generarReporteTrasferenciasListado(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
        String nombreReporte = "reportTransferenciaListado.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<InvListaConsultaTransferenciaTO> listado = UtilsJSON.jsonToList(InvListaConsultaTransferenciaTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteTrasferenciasListado(empresa, listado, usuarioEmpresaReporteTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReportePorLoteTrasferencias")
    public @ResponseBody
    String generarReportePorLoteTrasferencias(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
        String nombreReporte = "reportComprobanteTransferenciaPorLote.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<String> numero = UtilsJSON.jsonToList(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReportePorLoteTrasferencias(empresa, numero, usuarioEmpresaReporteTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConsumoDetalle")
    public @ResponseBody
    String generarReporteConsumoDetalle(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
        String nombreReporte = "";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvConsumosTO> invConsumosTOs = UtilsJSON.jsonToList(InvConsumosTO.class, map.get("invConsumosTOs"));
        boolean ordenado = UtilsJSON.jsonToObjeto(boolean.class, map.get("ordenado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (ordenado) {
                nombreReporte = "reportComprobanteConsumoOrdenado.jrxml";
            } else {
                nombreReporte = "reportComprobanteConsumo.jrxml";
            }
            respuesta = reporteInventarioService.generarReporteConsumoDetalle(invConsumosTOs, ordenado, usuarioEmpresaReporteTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCompraDetalle")
    public @ResponseBody
    String generarReporteCompraDetalle(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
        String nombreReporte = "reportComprobanteCompra.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<ReporteCompraDetalle> reporteCompraDetalles = UtilsJSON.jsonToList(ReporteCompraDetalle.class, map.get("reporteCompraDetalles"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteCompraDetalle(usuarioEmpresaReporteTO, reporteCompraDetalles);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCompra")
    public @ResponseBody
    String generarReporteCompra(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
        String nombreReporte = "reportComprobanteCompra.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvComprasPK> listaPk = UtilsJSON.jsonToList(InvComprasPK.class, map.get("listaPk"));
        Boolean isIMB = UtilsJSON.jsonToObjeto(Boolean.class, map.get("isIMB"));
        String secNombre = UtilsJSON.jsonToObjeto(String.class, map.get("nomSector"));
        String validarImpresionIMB = UtilsJSON.jsonToObjeto(String.class, map.get("validarImpresionIMB"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            isIMB = isIMB == null ? false : isIMB;
            respuesta = reporteInventarioService.generarReporteCompra(usuarioEmpresaReporteTO, listaPk, isIMB, secNombre, nombreReporte, validarImpresionIMB);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCompraMatricial")
    public RespuestaWebTO generarReporteCompraMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportComprobanteCompra.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvComprasPK> listaPk = UtilsJSON.jsonToList(InvComprasPK.class, map.get("listaPk"));
        Boolean isIMB = UtilsJSON.jsonToObjeto(Boolean.class, map.get("isIMB"));
        String secNombre = UtilsJSON.jsonToObjeto(String.class, map.get("nomSector"));
        String validarImpresionIMB = UtilsJSON.jsonToObjeto(String.class, map.get("validarImpresionIMB"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteCompra(usuarioEmpresaReporteTO, listaPk, isIMB, secNombre, nombreReporte, validarImpresionIMB);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteVentaDetalleImpresion")
    public @ResponseBody
    String generarReporteVentaDetalleImpresion(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String nombreCliente = UtilsJSON.jsonToObjeto(String.class, map.get("nombreCliente"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvVentasTO invVentasTO = UtilsJSON.jsonToObjeto(InvVentasTO.class, map.get("invVentasTO"));
        List<InvVentasDetalleTO> listaInvVentasDetalleTO = UtilsJSON.jsonToList(InvVentasDetalleTO.class, map.get("listaInvVentasDetalleTO"));
        Boolean isComprobanteElectronica = UtilsJSON.jsonToObjeto(Boolean.class, map.get("isComprobanteElectronica"));
        try {
            sisInfoTO.setEmpresaRuc(usuarioEmpresaReporteTO.getEmpRuc());
            List<ReporteVentaDetalle> lista = ventasService.obtenerReporteVentaDetalle(invVentasTO, listaInvVentasDetalleTO, isComprobanteElectronica, sisInfoTO);
            respuesta = reporteInventarioService.generarReporteVentaDetalleImpresion(usuarioEmpresaReporteTO, lista, nombreCliente, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteVentaDetalleImpresionMatricial")
    public RespuestaWebTO generarReporteVentaDetalleImpresionMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String nombreCliente = UtilsJSON.jsonToObjeto(String.class, map.get("nombreCliente"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        sisInfoTO.setEmpresaRuc(usuarioEmpresaReporteTO.getEmpRuc());
        InvVentasTO invVentasTO = UtilsJSON.jsonToObjeto(InvVentasTO.class, map.get("invVentasTO"));
        List<InvVentasDetalleTO> listaInvVentasDetalleTO = UtilsJSON.jsonToList(InvVentasDetalleTO.class, map.get("listaInvVentasDetalleTO"));
        Boolean isComprobanteElectronica = UtilsJSON.jsonToObjeto(Boolean.class, map.get("isComprobanteElectronica"));
        try {
            List<ReporteVentaDetalle> lista = ventasService.obtenerReporteVentaDetalle(invVentasTO, listaInvVentasDetalleTO, isComprobanteElectronica, sisInfoTO);
            respuesta = reporteInventarioService.generarReporteVentaDetalleImpresion(usuarioEmpresaReporteTO, lista, nombreCliente, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirVentaPorLote")
    public @ResponseBody
    String imprimirVentaPorLote(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<String> numerosVentas = UtilsJSON.jsonToList(String.class, map.get("numerosVentas"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        Boolean isComprobanteElectronica = UtilsJSON.jsonToObjeto(Boolean.class, map.get("isComprobanteElectronica"));
        try {
            sisInfoTO.setEmpresaRuc(usuarioEmpresaReporteTO.getEmpRuc());
            List<ReporteVentaDetalle> lista = new ArrayList<>();
            List<InformacionAdicional> listaInf = new ArrayList<>();
            if (numerosVentas != null) {
                for (String numeroVenta : numerosVentas) {
                    List<String> documento = UtilsValidacion.separar(numeroVenta, "|");
                    if (documento != null && !documento.isEmpty()) {
                        InvVentas venta = ventasDao.buscarInvVentas(empresa, documento.get(0), documento.get(1), documento.get(2));
                        if (venta != null) {
                            if (nombreReporte != null && nombreReporte.equals("reportPreRide") && venta.getVtaDocumentoTipo().equals("04")) {
                                nombreReporte = "reportPreRideNC";
                            }
                            InvVentasTO ventasTO = ConversionesInventario.convertirInvVentas_InvVentasTO(venta);
                            List<InvVentasDetalleTO> listaInvVentasDetalleTO = ventasService.obtenerVentaDetalleTOPorNumero(venta.getInvVentasPK().getVtaEmpresa(), venta.getInvVentasPK().getVtaPeriodo(), venta.getInvVentasPK().getVtaMotivo(), venta.getInvVentasPK().getVtaNumero());
                            if (ventasTO.getVtaRecargoBase0() == null) {
                                ventasTO.setVtaRecargoBase0(new BigDecimal("0.00"));
                            }
                            if (ventasTO.getVtaRecargoBaseImponible() == null) {
                                ventasTO.setVtaRecargoBaseImponible(new BigDecimal("0.00"));
                            }
                            if (ventasTO.getVtaInformacionAdicional() != null && !ventasTO.getVtaInformacionAdicional().equals("")) {
                                java.util.List<String> informacionAdicional = UtilsValidacion.separar(ventasTO.getVtaInformacionAdicional(), "|");
                                for (int i = 0; i < informacionAdicional.size(); i++) {
                                    if (!informacionAdicional.get(i).equals("|")
                                            && informacionAdicional.get(i).compareTo("") > 0
                                            && informacionAdicional.get(i).lastIndexOf("=") >= 0) {
                                        InformacionAdicional inf = new InformacionAdicional();
                                        inf.setNombre(informacionAdicional.get(i).substring(0, informacionAdicional.get(i).lastIndexOf("=")));
                                        inf.setValor(informacionAdicional.get(i).substring(informacionAdicional.get(i).lastIndexOf("=") + 1));
                                        listaInf.add(inf);
                                    }

                                }
                                for (int i = 0; i < listaInvVentasDetalleTO.size(); i++) {
                                    listaInvVentasDetalleTO.get(i).setInfoAdicional(listaInf);
                                }
                            }
                            lista.addAll(ventasService.obtenerReporteVentaDetalle(ventasTO, listaInvVentasDetalleTO, isComprobanteElectronica, sisInfoTO));
                        }
                    }
                }
            }
            respuesta = reporteInventarioService.generarReporteVentaDetalleImpresion(usuarioEmpresaReporteTO, lista, "", nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/imprimirVentaPorLoteMatricial")
    public RespuestaWebTO imprimirVentaPorLoteMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<String> numerosVentas = UtilsJSON.jsonToList(String.class, map.get("numerosVentas"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        Boolean isComprobanteElectronica = UtilsJSON.jsonToObjeto(Boolean.class, map.get("isComprobanteElectronica"));
        try {
            sisInfoTO.setEmpresaRuc(usuarioEmpresaReporteTO.getEmpRuc());
            List<ReporteVentaDetalle> lista = new ArrayList<>();
            List<InformacionAdicional> listaInf = new ArrayList<>();
            if (numerosVentas != null) {
                for (String numeroVenta : numerosVentas) {
                    List<String> documento = UtilsValidacion.separar(numeroVenta, "|");
                    if (documento != null && !documento.isEmpty()) {
                        InvVentas venta = ventasDao.buscarInvVentas(empresa, documento.get(0), documento.get(1), documento.get(2));
                        if (venta != null) {
                            if (nombreReporte != null && nombreReporte.equals("reportPreRide") && venta.getVtaDocumentoTipo().equals("04")) {
                                nombreReporte = "reportPreRideNC";
                            }
                            InvVentasTO ventasTO = ConversionesInventario.convertirInvVentas_InvVentasTO(venta);
                            List<InvVentasDetalleTO> listaInvVentasDetalleTO = ventasService.obtenerVentaDetalleTOPorNumero(venta.getInvVentasPK().getVtaEmpresa(), venta.getInvVentasPK().getVtaPeriodo(), venta.getInvVentasPK().getVtaMotivo(), venta.getInvVentasPK().getVtaNumero());
                            if (ventasTO.getVtaRecargoBase0() == null) {
                                ventasTO.setVtaRecargoBase0(new BigDecimal("0.00"));
                            }
                            if (ventasTO.getVtaRecargoBaseImponible() == null) {
                                ventasTO.setVtaRecargoBaseImponible(new BigDecimal("0.00"));
                            }
                            if (ventasTO.getVtaInformacionAdicional() != null && !ventasTO.getVtaInformacionAdicional().equals("")) {
                                java.util.List<String> informacionAdicional = UtilsValidacion.separar(ventasTO.getVtaInformacionAdicional(), "|");
                                for (int i = 0; i < informacionAdicional.size(); i++) {
                                    if (!informacionAdicional.get(i).equals("|")
                                            && informacionAdicional.get(i).compareTo("") > 0
                                            && informacionAdicional.get(i).lastIndexOf("=") >= 0) {
                                        InformacionAdicional inf = new InformacionAdicional();
                                        inf.setNombre(informacionAdicional.get(i).substring(0, informacionAdicional.get(i).lastIndexOf("=")));
                                        inf.setValor(informacionAdicional.get(i).substring(informacionAdicional.get(i).lastIndexOf("=") + 1));
                                        listaInf.add(inf);
                                    }

                                }
                                for (int i = 0; i < listaInvVentasDetalleTO.size(); i++) {
                                    listaInvVentasDetalleTO.get(i).setInfoAdicional(listaInf);
                                }
                            }
                            lista.addAll(ventasService.obtenerReporteVentaDetalle(ventasTO, listaInvVentasDetalleTO, isComprobanteElectronica, sisInfoTO));
                        }
                    }
                }
            }
            respuesta = reporteInventarioService.generarReporteVentaDetalleImpresion(usuarioEmpresaReporteTO, lista, "", nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteProformaDetalleImpresion")
    public byte[] generarReporteProformaDetalleImpresion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteProformaDetalle> lista = UtilsJSON.jsonToList(ReporteProformaDetalle.class,
                map.get("lista"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class,
                map.get("nombreReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteProformaDetalleImpresion(usuarioEmpresaReporteTO, lista,
                    nombreReporte);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/anularTransferencia")
    public RespuestaWebTO anularTransferencia(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = transferenciasService.anularTransferencia(empresa, periodo, motivo, numero);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(transferenciasService.getInvTransferenciasCabeceraTO(empresa, periodo, motivo, numero));
                resp.setOperacionMensaje(respues.substring(1));
            } else {
                resp.setOperacionMensaje("No se ha podido anular la transferencia");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/restaurarTransferencia")
    public RespuestaWebTO restaurarTransferencia(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = transferenciasService.restaurarTransferencia(empresa, periodo, motivo, numero);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(transferenciasService.getInvTransferenciasCabeceraTO(empresa, periodo, motivo, numero));
                resp.setOperacionMensaje(respues.substring(1));
            } else {
                resp.setOperacionMensaje("No se ha podido restaurar la transferencia.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/desmayorizarTransferencia")
    public RespuestaWebTO desmayorizarTransferencia(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = transferenciasService.desmayorizarTransferencia(empresa, periodo, motivo, numero);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje("No se ha podido desmayorizar");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/desmayorizarTransferenciaPorLote")
    public RespuestaWebTO desmayorizarTransferenciaPorLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<InvListaConsultaTransferenciaTO> lista = UtilsJSON.jsonToList(InvListaConsultaTransferenciaTO.class, map.get("listadoTransferenciaPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<String> resultados = transferenciasService.desmayorizarTransferenciaLote(empresa, lista, sisInfoTO);
            String desmayorizados = "";
            String noDesmayorizados = "";
            if (resultados != null && !resultados.isEmpty()) {
                for (String respue : resultados) {
                    if (respue.charAt(0) == 'T') {
                        desmayorizados = desmayorizados + "<li>" + respue.substring(1) + "</li>";
                    } else {
                        noDesmayorizados = noDesmayorizados + "<li>" + respue.substring(1) + "</li>";
                    }
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                desmayorizados = desmayorizados != null && !desmayorizados.equals("") ? "<div class='card border-success pt-2 pb-2'>" + desmayorizados + "</div>" : "";
                noDesmayorizados = noDesmayorizados != null && !noDesmayorizados.equals("") ? "<div class='card border-danger pt-2 pb-2'>" + noDesmayorizados + "</div>" : "";
                resp.setExtraInfo("<htlm>" + desmayorizados + noDesmayorizados + "</html>");
            } else {
                resp.setOperacionMensaje("Ocurrió un error al desmayorizar transferencia.");

            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarInvPedidosConfiguracionTO")
    public String insertarInvPedidosConfiguracionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosConfiguracionTO invPedidosConfiguracionTO = UtilsJSON.jsonToObjeto(InvPedidosConfiguracionTO.class,
                map.get("invPedidosConfiguracionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return pedidosConfiguracionService.insertarInvPedidosConfiguracionTO(invPedidosConfiguracionTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvPedidosConfiguracionTO")
    public InvPedidosConfiguracionTO getListaInvPedidosConfiguracionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        InvPedidosMotivoTO invPedidosMotivoTO = UtilsJSON.jsonToObjeto(InvPedidosMotivoTO.class,
                map.get("invPedidosMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            InvPedidosMotivoPK invPedidosMotivoPK = new InvPedidosMotivoPK(empresa, invPedidosMotivoTO.getPmSector(), invPedidosMotivoTO.getPmCodigo());
            return pedidosConfiguracionService.getListaInvPedidosConfiguracionTO(invPedidosMotivoPK, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/asignarNumeroDocumento")
    public RespuestaWebTO asignarNumeroDocumento(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String tipoDocumento = UtilsJSON.jsonToObjeto(String.class,
                map.get("tipoDocumento"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            String respues = ventasService.asignarNumeroDocumento(empresa, tipoDocumento, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se asignó número de documento");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/validarNumero")
    public RespuestaWebTO validarNumero(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String tipoDocumento = UtilsJSON.jsonToObjeto(String.class,
                map.get("tipoDocumento"));
        String numeroDocumento = UtilsJSON.jsonToObjeto(String.class,
                map.get("numeroDocumento"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            boolean respues = ventasService.validarNumero(empresa, tipoDocumento, numeroDocumento, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("Número de documento no válido.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*ETIQUETAS*/
    @RequestMapping("/traerEtiquetas")
    public RespuestaWebTO traerEtiquetas(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            InvProductoEtiquetas respues = productoService.traerEtiquetas(empresa);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("Debe definir una estructura de precios.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarInvProductoEtiquetas")
    public RespuestaWebTO insertarInvProductoEtiquetas(@RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoEtiquetas invProductoEtiquetas = UtilsJSON.jsonToObjeto(InvProductoEtiquetas.class, map.get("invProductoEtiquetas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvProductoEtiquetas respues = productoEtiquetasService.insertarInvProductoEtiquetas(invProductoEtiquetas, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Se ha guardado etiquetas de producto de manera exitosa");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se pudo completar la operación.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaKardex")
    public ResponseEntity obtenerDatosParaKardex(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            Map<String, Object> respues = productoSaldosService.obtenerDatosParaKardex(empresa);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/obtenerDatosParaMotivoConsumos")
    public RespuestaWebTO obtenerDatosParaMotivoConsumos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = consumosMotivoService.obtenerDatosParaMotivoConsumos(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron los datos para generar consumos.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*Compras*/
    @RequestMapping("/getListaInvConsultaCompra")
    public RespuestaWebTO getListaInvConsultaCompra(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        String nRegistros = UtilsJSON.jsonToObjeto(String.class, map.get("nRegistros"));
        Boolean listarImb = UtilsJSON.jsonToObjeto(Boolean.class, map.get("listadoImb"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvListaConsultaCompraTO> respues = comprasService.getListaInvConsultaCompra(empresa, periodo, motivo, busqueda, nRegistros, listarImb);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*Compras*/
    @RequestMapping("/listarComprasPorOrdenCompra")
    public RespuestaWebTO listarComprasPorOrdenCompra(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvListaConsultaCompraTO> respues = comprasService.listarComprasPorOrdenCompra(empresa, sector, motivo, numero);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarComprasPorOrdenCompraYProducto")
    public RespuestaWebTO listarComprasPorOrdenCompraYProducto(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        String producto = UtilsJSON.jsonToObjeto(String.class, map.get("producto"));
        int ocSecuencial = UtilsJSON.jsonToObjeto(int.class, map.get("oc_secuencial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvListaConsultaCompraTO> respues = comprasService.listarComprasPorOrdenCompraYProducto(empresa, sector, motivo, numero, producto, ocSecuencial);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarComprasProgramadas")
    public RespuestaWebTO listarComprasProgramadas(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String nRegistros = UtilsJSON.jsonToObjeto(String.class, map.get("nRegistros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunComprasProgramadasListadoTO> respues = comprasService.listarComprasProgramadas(empresa, periodo, motivo, desde, hasta, nRegistros);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/configurarCompraProgramada")
    public RespuestaWebTO configurarCompraProgramada(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String numeroCompra = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCompra"));
        InvComprasProgramadas comprasProgramadas = UtilsJSON.jsonToObjeto(InvComprasProgramadas.class, map.get("invComprasProgramadas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvComprasProgramadas respues = comprasProgramadasService.configurarCompraProgramada(empresa, numeroCompra, comprasProgramadas, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("La configuración para la compra N. " + numeroCompra + " se ha insertado correctamente.");
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/editarConfiguracionCompraProgramada")
    public RespuestaWebTO editarConfiguracionCompraProgramada(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String numeroCompra = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCompra"));
        InvComprasProgramadas comprasProgramadas = UtilsJSON.jsonToObjeto(InvComprasProgramadas.class, map.get("invComprasProgramadas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<String> pks = UtilsValidacion.separar(numeroCompra, "|");
            InvCompras compra = new InvCompras(empresa, pks.get(0), pks.get(1), pks.get(2));
            comprasProgramadas.setInvCompras(compra);
            InvComprasProgramadas respues = comprasProgramadasService.editarConfiguracionCompraProgramada(comprasProgramadas, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("La configuración de compra programada N. " + respues.getCpSecuencial() + "" + " se ha modificado correctamente.");
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarConfiguracionCompraProgramada")
    public RespuestaWebTO eliminarConfiguracionCompraProgramada(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasProgramadas comprasProgramadas = UtilsJSON.jsonToObjeto(InvComprasProgramadas.class, map.get("invComprasProgramadas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvComprasProgramadas respues = comprasProgramadasService.eliminarConfiguracionCompraProgramada(comprasProgramadas, sisInfoTO);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("La configuración de compra programada N. " + respues.getCpSecuencial() + "" + " se ha elimiando correctamente.");
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarInvComprasProgramadasTO")
    public RespuestaWebTO listarInvComprasProgramadasTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvComprasProgramadasTO> respues = comprasProgramadasService.listarInvComprasProgramadasTO(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarComprasProgramadas")
    public RespuestaWebTO eliminarComprasProgramadas(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<String> numerosCompras = UtilsJSON.jsonToList(String.class, map.get("numerosCompras"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (numerosCompras != null && !numerosCompras.isEmpty()) {
                List<RespuestaWebTO> respuestas = new ArrayList<>();
                for (String numeroCompra : numerosCompras) {
                    RespuestaWebTO respuestaEliinacion = new RespuestaWebTO();
                    respuestaEliinacion.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    List<String> pks = UtilsValidacion.separar(numeroCompra, "|");
                    String resultado = comprasService.eliminarComprasProgramadas(empresa, pks.get(0), pks.get(1), pks.get(2), sisInfoTO);
                    if (resultado.equals("t")) {
                        respuestaEliinacion.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                        respuestaEliinacion.setExtraInfo("La compra programada N. " + numeroCompra + " se eliminó exitosamente.");
                    } else {
                        respuestaEliinacion.setExtraInfo(resultado);
                    }
                    respuestas.add(respuestaEliinacion);
                }
                resp.setExtraInfo(respuestas);
            } else {
                resp.setOperacionMensaje("No se realizó ninguna operación.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/duplicarComprasProgramada")
    public RespuestaWebTO duplicarComprasProgramada(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        Date desde = UtilsJSON.jsonToObjeto(Date.class, map.get("desde"));
        Date hasta = UtilsJSON.jsonToObjeto(Date.class, map.get("hasta"));
        String numeroCompra = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCompra"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (numeroCompra != null) {
                List<RespuestaWebTO> respuestas = new ArrayList<>();
                Calendar start = Calendar.getInstance();
                start.setTime(desde);
                Calendar end = Calendar.getInstance();
                end.setTime(hasta);
                for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                    RespuestaWebTO respuestaDuplicado = new RespuestaWebTO();
                    respuestaDuplicado.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    String resultado = comprasService.duplicarComprasProgramada(empresa, numeroCompra, date, sisInfoTO);
                    if (resultado != null && resultado.charAt(0) == '-') {
                        respuestaDuplicado.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                        respuestaDuplicado.setExtraInfo("Se insertó la compra programada N. " + resultado.substring(1));
                    } else {
                        respuestaDuplicado.setExtraInfo(resultado);
                    }
                    respuestas.add(respuestaDuplicado);
                }
                resp.setExtraInfo(respuestas);
            } else {
                resp.setOperacionMensaje("No se realizó ninguna operación.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/consultarCompra")
    public RespuestaWebTO consultarCompra(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = comprasService.consultarCompra(map);
            if (respues != null) {
                List<InvListadoPagosTO> listaInvListadoPagosTO = comprasService.invListadoPagosTO(empresa, periodo, motivo, numero);
                respues.put("listaInvListadoPagosTO", listaInvListadoPagosTO);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/consultarCompraActivo")
    public RespuestaWebTO consultarCompraActivo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = comprasService.consultarCompraActivo(map);
            String mensaje = UtilsJSON.jsonToObjeto(String.class, respues.get("mensaje"));
            if (respues != null) {
                if (mensaje != null && mensaje.substring(0, 1).equals("T")) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(respues);
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    resp.setOperacionMensaje(mensaje.substring(1));
                }
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/desmayorizarCompra")
    public RespuestaWebTO desmayorizarCompra(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasPK invComprasPK = UtilsJSON.jsonToObjeto(InvComprasPK.class, map.get("invComprasPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = comprasService.mayorizarDesmayorizarComprasSql(invComprasPK, true, false, sisInfoTO);
            if (respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/desmayorizarLoteCompra")
    public RespuestaWebTO desmayorizarLoteCompra(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<InvListaConsultaCompraTO> listado = UtilsJSON.jsonToList(InvListaConsultaCompraTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        String[] listaMensajes = new String[]{};
        ArrayList<String> listaMensajesEnviar = new ArrayList<String>();
        try {
            String respues = comprasService.desmayorizarLoteCompra(listado, sisInfoTO);
            listaMensajes = respues.substring(0).split("\\/");
            for (int i = 0; i < listaMensajes.length; i++) {
                if (!listaMensajes[i].equals(".")) {
                    String mensaje = listaMensajes[i].substring(0);
                    listaMensajesEnviar.add(mensaje);
                }
            }
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setOperacionMensaje(respues.substring(0));
            resp.setExtraInfo(listaMensajesEnviar);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/anularCompra")
    public RespuestaWebTO anularCompra(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasPK invComprasPK = UtilsJSON.jsonToObjeto(InvComprasPK.class, map.get("invComprasPK"));
        boolean actualizarFechaUltimaValidacionSri = UtilsJSON.jsonToObjeto(boolean.class, map.get("actualizarFechaUltimaValidacionSri"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = comprasService.anularRestaurarComprasSql(invComprasPK, true, actualizarFechaUltimaValidacionSri, sisInfoTO);
            if (respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se anuló la compra.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/restaurarCompra")
    public RespuestaWebTO restaurarCompra(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasPK invComprasPK = UtilsJSON.jsonToObjeto(InvComprasPK.class, map.get("invComprasPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = comprasService.anularRestaurarComprasSql(invComprasPK, false, false, sisInfoTO);
            if (respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1));
                resp.setExtraInfo(true);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se restauró la compra.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/suprimirCompra")
    public RespuestaWebTO suprimirCompra(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasPK invComprasPK = UtilsJSON.jsonToObjeto(InvComprasPK.class, map.get("invComprasPK"));
        boolean isPendiente = UtilsJSON.jsonToObjeto(boolean.class, map.get("isPendiente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        boolean suprimirCompra = false;
        boolean isPeriodoAbierto = false;
        SisPeriodo sisPeriodo = null;

        try {
            boolean estaProcesoAutorizacion = compraElectronicaService.comprobarRetencionAutorizadaProcesamiento(invComprasPK.getCompEmpresa(), invComprasPK.getCompPeriodo(), invComprasPK.getCompMotivo(), invComprasPK.getCompNumero());
            if (!estaProcesoAutorizacion) {
                InvCompraCabeceraTO invCompraCabeceraTO = comprasService.getInvCompraCabeceraTO(invComprasPK.getCompEmpresa(), invComprasPK.getCompPeriodo(), invComprasPK.getCompMotivo(), invComprasPK.getCompNumero());
                InvComprasTO invComprasTO = ConversionesInventario.convertirInvCompraCabeceraTO_InvComprasTO(invCompraCabeceraTO, invComprasPK);
                Date fecha = UtilsDate.fechaFormatoDate(invComprasTO.getCompFecha(), "yyyy-MM-dd");
                List<SisPeriodo> listaPeriodos = periodoService.getListaPeriodo(invComprasPK.getCompEmpresa());
                if (listaPeriodos != null) {
                    for (SisPeriodo itemSisPeriodo : listaPeriodos) {
                        //Si la fecha está entre el 'desde' y 'hasta' del periodo, ese periodo es el que corresponde a nuestra fecha pasada como parámetro 
                        if (fecha.getTime() >= itemSisPeriodo.getPerDesde().getTime() && fecha.getTime() <= UtilsDate.dateCompleto(itemSisPeriodo.getPerHasta()).getTime()) {
                            sisPeriodo = itemSisPeriodo;
                        }
                    }
                    if (sisPeriodo != null && !sisPeriodo.getPerCerrado()) {
                        isPeriodoAbierto = true;
                    }
                }

                if (isPeriodoAbierto) {
                    if (isPendiente) {
                        suprimirCompra = true;
                    } else {
                        String respuestaDesmayorizar = comprasService.mayorizarDesmayorizarComprasSql(invComprasPK, true, false, sisInfoTO);
                        if (respuestaDesmayorizar.charAt(0) == 'T') {
                            suprimirCompra = true;
                            invComprasTO.setCompPendiente(true);
                        } else {
                            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                            resp.setOperacionMensaje("No se pudo DESMAYORIZAR la compra.");
                        }
                    }

                    if (suprimirCompra) {
                        Map<String, Object> respues = comprasService.suprimirCompra(invComprasTO, sisInfoTO);
                        String mensaje = UtilsJSON.jsonToObjeto(String.class, respues.get("mensaje"));
                        InvComprasTO invCompraTORetorno = UtilsJSON.jsonToObjeto(InvComprasTO.class, respues.get("invComprasTO"));
                        if (mensaje.substring(0, 1).equals("T")) {
                            if (invCompraTORetorno.getCompPendiente()) {
                                comprasService.quitarPendiente(invCompraTORetorno);
                            }
                            String respuesAnulado = comprasService.anularRestaurarComprasSql(invComprasPK, true, false, sisInfoTO);
                            if (respuesAnulado.charAt(0) == 'T') {
                                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                                resp.setOperacionMensaje("La compra " + invComprasPK.getCompPeriodo() + "|" + invComprasPK.getCompMotivo() + "|" + invComprasPK.getCompNumero() + " se ha suprimido correctamente.");
                                resp.setExtraInfo(true);
                            } else {
                                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                                resp.setOperacionMensaje("La compra " + invComprasPK.getCompPeriodo() + "|" + invComprasPK.getCompMotivo() + "|" + invComprasPK.getCompNumero() + "no se pudo suprimir.");
                            }

                        } else {
                            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                            resp.setOperacionMensaje("No se pudo suprimir la compra.");
                        }
                    }
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    resp.setOperacionMensaje("El período de la compra se encuentra CERRADO");
                }
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("La retención de la compra se encuentra en PROCESO DE AUTORIZACIÓN");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/documentos")
    public RespuestaWebTO documentos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String tipoIdentificacion = UtilsJSON.jsonToObjeto(String.class, map.get("tipoIdentificacion"));
        String tipoTransaccion = UtilsJSON.jsonToObjeto(String.class, map.get("tipoTransaccion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String codigoTipoTransaccion = tipoTransaccionService.getCodigoAnxTipoTransaccionTO(tipoIdentificacion, tipoTransaccion);
            if (codigoTipoTransaccion != null) {
                List<AnxTipoComprobanteComboTO> lista = tipoComprobanteService.getListaAnxTipoComprobanteComboTO(codigoTipoTransaccion);
                if (lista.size() > 0) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(lista);
                } else {
                    resp.setOperacionMensaje("No se encontraron resultados.");
                }
            } else {
                resp.setOperacionMensaje("No se encontraron tipo de comprobante");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getInvCompraCabeceraTO")
    public InvCompraCabeceraTO getInvCompraCabeceraTO(@RequestBody String json
    ) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoPeriodo = UtilsJSON.jsonToObjeto(String.class, map.get("codigoPeriodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroCompra = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCompra"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasService.getInvCompraCabeceraTO(empresa, codigoPeriodo, motivo, numeroCompra);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCompra")
    public Object[] getCompra(@RequestBody String json
    ) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String perCodigo = UtilsJSON.jsonToObjeto(String.class,
                map.get("perCodigo"));
        String motCodigo = UtilsJSON.jsonToObjeto(String.class,
                map.get("motCodigo"));
        String compNumero = UtilsJSON.jsonToObjeto(String.class,
                map.get("compNumero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return comprasService.getCompra(empresa, perCodigo, motCodigo, compNumero);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getComprasTO")
    public InvComprasTO getComprasTO(@RequestBody String json
    ) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class,
                map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class,
                map.get("motivo"));
        String numeroCompra = UtilsJSON.jsonToObjeto(String.class,
                map.get("numeroCompra"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return comprasService.getComprasTO(empresa, periodo, motivo, numeroCompra);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCompraTotalesTO")
    public InvCompraTotalesTO getCompraTotalesTO(@RequestBody String json
    ) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class,
                map.get("empresa"));
        String comPeriodo = UtilsJSON.jsonToObjeto(String.class,
                map.get("comPeriodo"));
        String comMotivo = UtilsJSON.jsonToObjeto(String.class,
                map.get("comMotivo"));
        String ComNumero = UtilsJSON.jsonToObjeto(String.class,
                map.get("ComNumero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));
        try {
            return comprasService.getCompraTotalesTO(empresa, comPeriodo, comMotivo, ComNumero);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCompraDetalleImprimir")
    public byte[] generarReporteCompraDetalleImprimir(@RequestBody String json
    ) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String cmFormaImprimir = UtilsJSON.jsonToObjeto(String.class,
                map.get("cmFormaImprimir"));
        List<ReporteCompraDetalle> reporteCompraDetalles = UtilsJSON.jsonToList(ReporteCompraDetalle.class,
                map.get("reporteCompraDetalles"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class,
                map.get("sisInfoTO"));

        try {
            return reporteInventarioService.generarReporteCompraDetalleImprimir(usuarioEmpresaReporteTO,
                    reporteCompraDetalles, cmFormaImprimir);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerDatosBasicosCompraNueva")
    public RespuestaWebTO obtenerDatosBasicosCompra(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        String provTipoId = UtilsJSON.jsonToObjeto(String.class, map.get("provTipoId"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = comprasService.obtenerDatosBasicosCompraNueva(empresa, fecha, provTipoId, proveedor, sisInfoTO.getUsuario());
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/guardarImagenesCompra")
    public RespuestaWebTO guardarImagenesCompra(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasPK pk = UtilsJSON.jsonToObjeto(InvComprasPK.class, map.get("pk"));
        List<InvAdjuntosComprasWebTO> imagenes = UtilsJSON.jsonToList(InvAdjuntosComprasWebTO.class, map.get("listImagen"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = comprasService.guardarImagenesCompra(pk, imagenes, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                if (imagenes == null || imagenes.isEmpty()) {
                    resp.setOperacionMensaje("Las imagenes para la compra se han eliminado correctamente.");
                } else {
                    resp.setOperacionMensaje("Las imagenes para la compra se han guardado correctamente.");
                }
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al guardar imagenes de compra.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/validarRetencionCompra")
    public RespuestaWebTO validarRetencionCompra(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        InvComprasTO invCompraTO = UtilsJSON.jsonToObjeto(InvComprasTO.class, map.get("invComprasTO"));
        AnxCompraTO anxCompraTO = UtilsJSON.jsonToObjeto(AnxCompraTO.class, map.get("anxCompraTO"));

        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = comprasService.validarRetencionCompra(empresa, anxCompraTO, invCompraTO, usuario);
            if (respues != null) {
                String mensaje = UtilsJSON.jsonToObjeto(String.class, respues.get("mensaje"));
                if (mensaje.substring(0, 1).equals("T")) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(respues);
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
                }
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/validarRetencionDesdeCompra")
    public RespuestaWebTO validarRetencionDesdeCompra(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        InvComprasTO invCompraTO = UtilsJSON.jsonToObjeto(InvComprasTO.class, map.get("invComprasTO"));
        AnxCompraTO anxCompraTO = UtilsJSON.jsonToObjeto(AnxCompraTO.class, map.get("anxCompraTO"));

        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = comprasService.validarRetencionDesdeCompra(empresa, anxCompraTO, invCompraTO);
            if (respues != null) {
                String mensaje = UtilsJSON.jsonToObjeto(String.class, respues.get("mensaje"));
                if (mensaje.substring(0, 1).equals("T")) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(respues);
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
                }
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/contabilizarComprasTrans")
    public RespuestaWebTO contabilizarComprasTrans(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        InvComprasTO invCompraTO = UtilsJSON.jsonToObjeto(InvComprasTO.class, map.get("invComprasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO1 = new MensajeTO();
            mensajeTO1 = comprasService.validarInvContableComprasDetalleTO(invCompraTO.getEmpCodigo(), invCompraTO.getCompPeriodo(), invCompraTO.getCompMotivo(), invCompraTO.getCompNumero(), sisInfoTO);
            Map<String, Object> respues = comprasService.contabilizarComprasTrans(empresa, invCompraTO, mensajeTO1, sisInfoTO);
            if (respues != null) {
                String mensaje = UtilsJSON.jsonToObjeto(String.class, respues.get("mensaje"));
                if (mensaje.substring(0, 1).equals("T")) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(respues);
                    resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
                }
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/recontabilizarComprasTrans")
    public RespuestaWebTO recontabilizarComprasTrans(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        InvComprasTO invCompraTO = UtilsJSON.jsonToObjeto(InvComprasTO.class, map.get("invComprasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO1 = new MensajeTO();
            mensajeTO1 = comprasService.validarInvContableComprasDetalleTO(invCompraTO.getEmpCodigo(), invCompraTO.getCompPeriodo(), invCompraTO.getCompMotivo(), invCompraTO.getCompNumero(), sisInfoTO);
            Map<String, Object> respues = comprasService.reContabilizarComprasTrans(empresa, invCompraTO, mensajeTO1, sisInfoTO);
            if (respues != null) {
                String mensaje = UtilsJSON.jsonToObjeto(String.class, respues.get("mensaje"));
                if (mensaje.substring(0, 1).equals("T")) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(respues);
                    resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
                }
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/guardarCompra")
    public RespuestaWebTO guardarCompra(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        InvComprasTO invComprasTO = UtilsJSON.jsonToObjeto(InvComprasTO.class, map.get("invComprasTO"));
        InvProveedorTO proveedor = UtilsJSON.jsonToObjeto(InvProveedorTO.class, map.get("proveedor"));
        InvComboFormaPagoTO formaPago = UtilsJSON.jsonToObjeto(InvComboFormaPagoTO.class, map.get("formaPagoCompra"));
        AnxFormaPagoTO fp = UtilsJSON.jsonToObjeto(AnxFormaPagoTO.class, map.get("anxFormaPagoTO"));
        AnxCompraTO anxCompraTO = UtilsJSON.jsonToObjeto(AnxCompraTO.class, map.get("anxCompraTO"));
        List<InvComprasDetalleTO> listaInvComprasDetalleTO = UtilsJSON.jsonToList(InvComprasDetalleTO.class, map.get("listInvComprasDetalleTO"));
        List<AnxCompraDetalleTO> listAnxCompraDetalleTO = UtilsJSON.jsonToList(AnxCompraDetalleTO.class, map.get("listAnxCompraDetalleTO"));
        List<AnxCompraReembolsoTO> listAnxCompraReembolsoTO = UtilsJSON.jsonToList(AnxCompraReembolsoTO.class, map.get("listAnxCompraReembolsoTO"));
        List<InvAdjuntosComprasWebTO> listImagen = UtilsJSON.jsonToList(InvAdjuntosComprasWebTO.class, map.get("listImagen"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = comprasService.guardarCompra(
                    empresa,
                    usuario,
                    listaInvComprasDetalleTO,
                    listAnxCompraDetalleTO,
                    listAnxCompraReembolsoTO,
                    anxCompraTO,
                    invComprasTO,
                    proveedor,
                    formaPago,
                    fp,
                    listImagen,
                    sisInfoTO);
            if (respues != null) {
                String mensaje = UtilsJSON.jsonToObjeto(String.class, respues.get("mensaje"));
                InvComprasTO invCompraTORetorno = UtilsJSON.jsonToObjeto(InvComprasTO.class, respues.get("invCompraTO"));
                if (mensaje.substring(0, 1).equals("T")) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
                    resp.setExtraInfo(invCompraTORetorno);
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
                }
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/guardarCompraImb")
    public RespuestaWebTO guardarCompraImb(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        InvComprasTO invComprasTO = UtilsJSON.jsonToObjeto(InvComprasTO.class, map.get("invComprasTO"));
        InvProveedorTO proveedor = UtilsJSON.jsonToObjeto(InvProveedorTO.class, map.get("proveedor"));
        InvComboFormaPagoTO formaPago = UtilsJSON.jsonToObjeto(InvComboFormaPagoTO.class, map.get("formaPagoCompra"));
        AnxFormaPagoTO fp = UtilsJSON.jsonToObjeto(AnxFormaPagoTO.class, map.get("anxFormaPagoTO"));
        AnxCompraTO anxCompraTO = UtilsJSON.jsonToObjeto(AnxCompraTO.class, map.get("anxCompraTO"));
        List<InvComprasDetalleTO> listaInvComprasDetalleTO = UtilsJSON.jsonToList(InvComprasDetalleTO.class, map.get("listInvComprasDetalleTO"));
        List<AnxCompraDetalleTO> listAnxCompraDetalleTO = UtilsJSON.jsonToList(AnxCompraDetalleTO.class, map.get("listAnxCompraDetalleTO"));
        List<AnxCompraReembolsoTO> listAnxCompraReembolsoTO = UtilsJSON.jsonToList(AnxCompraReembolsoTO.class, map.get("listAnxCompraReembolsoTO"));
        List<InvAdjuntosComprasWebTO> listImagen = UtilsJSON.jsonToList(InvAdjuntosComprasWebTO.class, map.get("listImagen"));
        List<InvComprasDetalleImbTO> listaCompraImb = UtilsJSON.jsonToList(InvComprasDetalleImbTO.class, map.get("listInvComprasDetalleImbTO"));
        List<InvComprasLiquidacionTO> listaCompraLiquidacionTO = UtilsJSON.jsonToList(InvComprasLiquidacionTO.class, map.get("listInvComprasLiquidacionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = comprasService.guardarCompraImb(
                    empresa,
                    usuario,
                    listaInvComprasDetalleTO,
                    listAnxCompraDetalleTO,
                    listAnxCompraReembolsoTO,
                    anxCompraTO,
                    invComprasTO,
                    proveedor,
                    formaPago,
                    fp,
                    listImagen,
                    listaCompraImb,
                    listaCompraLiquidacionTO,
                    sisInfoTO);
            if (respues != null) {
                String mensaje = UtilsJSON.jsonToObjeto(String.class, respues.get("mensaje"));
                InvComprasTO invCompraTORetorno = UtilsJSON.jsonToObjeto(InvComprasTO.class, respues.get("invCompraTO"));
                if (mensaje.substring(0, 1).equals("T")) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
                    resp.setExtraInfo(invCompraTORetorno);
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
                }
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarCompra")
    public RespuestaWebTO modificarCompra(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean desmayorizar = UtilsJSON.jsonToObjeto(boolean.class, map.get("desmayorizar"));
        InvComprasTO invComprasTO = UtilsJSON.jsonToObjeto(InvComprasTO.class, map.get("invComprasTO"));
        InvComboFormaPagoTO formaPago = UtilsJSON.jsonToObjeto(InvComboFormaPagoTO.class, map.get("formaPagoCompra"));
        AnxFormaPagoTO fp = UtilsJSON.jsonToObjeto(AnxFormaPagoTO.class, map.get("anxFormaPagoTO"));
        AnxFormaPagoTO fpEliminar = UtilsJSON.jsonToObjeto(AnxFormaPagoTO.class, map.get("anxFormaPagoTOEliminar"));
        boolean seBuscoImagenes = UtilsJSON.jsonToObjeto(boolean.class, map.get("seBuscoImagenes"));
        List<InvComprasDetalleTO> listaInvComprasDetalleTO = UtilsJSON.jsonToList(InvComprasDetalleTO.class, map.get("listInvComprasDetalleTO"));
        List<InvComprasDetalleTO> listInvComprasDetalleTOEliminar = UtilsJSON.jsonToList(InvComprasDetalleTO.class, map.get("listInvComprasDetalleTOEliminar"));

        AnxCompraTO anxCompraTO = UtilsJSON.jsonToObjeto(AnxCompraTO.class, map.get("anxCompraTO"));
        List<AnxCompraDetalleTO> listAnxCompraDetalleTO = UtilsJSON.jsonToList(AnxCompraDetalleTO.class, map.get("listAnxCompraDetalleTO"));
        List<AnxCompraDetalleTO> listAnxCompraDetalleTOEliminar = UtilsJSON.jsonToList(AnxCompraDetalleTO.class, map.get("listAnxCompraDetalleTOEliminar"));

        List<AnxCompraReembolsoTO> listAnxCompraReembolsoTO = UtilsJSON.jsonToList(AnxCompraReembolsoTO.class, map.get("listAnxCompraReembolsoTO"));
        List<AnxCompraReembolsoTO> listAnxCompraReembolsoTOEliminar = UtilsJSON.jsonToList(AnxCompraReembolsoTO.class, map.get("listAnxCompraReembolsoTOEliminar"));

        List<InvAdjuntosComprasWebTO> listImagen = UtilsJSON.jsonToList(InvAdjuntosComprasWebTO.class, map.get("listImagen"));
        List<InvComprasDetalleImbTO> listaCompraImb = UtilsJSON.jsonToList(InvComprasDetalleImbTO.class, map.get("listInvComprasDetalleImbTO"));
        List<InvComprasDetalleImbTO> listaCompraImbEliminar = UtilsJSON.jsonToList(InvComprasDetalleImbTO.class, map.get("listInvComprasDetalleImbTOEliminar"));

        List<InvComprasLiquidacionTO> listaCompraLiquidacionTO = UtilsJSON.jsonToList(InvComprasLiquidacionTO.class, map.get("listInvComprasLiquidacionTO"));
        List<InvComprasLiquidacionTO> listInvComprasLiquidacionTOEliminar = UtilsJSON.jsonToList(InvComprasLiquidacionTO.class, map.get("listInvComprasLiquidacionTOEliminar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        try {
            if (seBuscoImagenes && (listImagen == null || listImagen.isEmpty())) {
                listImagen = new ArrayList<>();
//                listImagen.add(new InvAdjuntosComprasWebTO());
            }
            Map<String, Object> respues = comprasService.modificarCompraInventario(empresa,
                    listaInvComprasDetalleTO,
                    listInvComprasDetalleTOEliminar,
                    listAnxCompraDetalleTO,
                    listAnxCompraDetalleTOEliminar,
                    listAnxCompraReembolsoTO,
                    listAnxCompraReembolsoTOEliminar,
                    anxCompraTO,
                    invComprasTO,
                    formaPago,
                    fp,
                    fpEliminar,
                    desmayorizar,
                    listImagen,
                    listaCompraImb,
                    listaCompraImbEliminar,
                    listaCompraLiquidacionTO,
                    listInvComprasLiquidacionTOEliminar,
                    sisInfoTO);
            if (respues != null) {
                String mensaje = UtilsJSON.jsonToObjeto(String.class, respues.get("mensaje"));
                InvComprasTO invCompraTORetorno = UtilsJSON.jsonToObjeto(InvComprasTO.class, respues.get("invCompraTO"));
                if (mensaje.substring(0, 1).equals("T")) {
                    if (!invCompraTORetorno.getCompPendiente()) {
                        comprasService.quitarPendiente(invCompraTORetorno);
                    }
                    invCompraTORetorno.setTieneImagenes(listImagen.size() > 0 ? true : false);
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
                    resp.setExtraInfo(invCompraTORetorno);
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
                    if (respues.get("listaErrores") != null) {
                        resp.setExtraInfo(respues.get("listaErrores"));
                    }
                }
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/anularContableEnCompra")
    public RespuestaWebTO anularContable(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean desmayorizar = UtilsJSON.jsonToObjeto(boolean.class, map.get("desmayorizar"));
        boolean seBuscoImagenes = UtilsJSON.jsonToObjeto(boolean.class, map.get("seBuscoImagenes"));
        InvComprasTO invComprasTO = UtilsJSON.jsonToObjeto(InvComprasTO.class, map.get("invComprasTO"));
        InvComboFormaPagoTO formaPago = UtilsJSON.jsonToObjeto(InvComboFormaPagoTO.class, map.get("formaPagoCompra"));
        AnxFormaPagoTO fp = UtilsJSON.jsonToObjeto(AnxFormaPagoTO.class, map.get("anxFormaPagoTO"));
        AnxFormaPagoTO fpEliminar = null;
        List<InvComprasDetalleTO> listaInvComprasDetalleTO = UtilsJSON.jsonToList(InvComprasDetalleTO.class, map.get("listInvComprasDetalleTO"));
        List<InvComprasDetalleTO> listInvComprasDetalleTOEliminar = new ArrayList<>();
        //Retencion
        AnxCompraTO anxCompraTO = UtilsJSON.jsonToObjeto(AnxCompraTO.class, map.get("anxCompraTO"));
        List<AnxCompraDetalleTO> listAnxCompraDetalleTO = new ArrayList<>();
        List<AnxCompraDetalleTO> listAnxCompraDetalleTOEliminar = new ArrayList<>();
        List<AnxCompraReembolsoTO> listAnxCompraReembolsoTO = new ArrayList<>();
        List<AnxCompraReembolsoTO> listAnxCompraReembolsoTOEliminar = new ArrayList<>();
        if (anxCompraTO != null) {
            listAnxCompraDetalleTO = compraDetalleDao.getAnexoCompraDetalleTO(empresa, invComprasTO.getCompPeriodo(), invComprasTO.getCompMotivo(), invComprasTO.getCompNumero());
            listAnxCompraReembolsoTO = compraReembolsoService.getAnexoCompraReembolsoTOs(empresa, invComprasTO.getCompPeriodo(), invComprasTO.getCompMotivo(), invComprasTO.getCompNumero());
        }
        List<InvAdjuntosComprasWebTO> listImagen = new ArrayList<>();

        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        //Obtener pk contable a ANULAR
        ConContablePK conContablePK = new ConContablePK(invComprasTO.getEmpCodigo(), invComprasTO.getContPeriodo(), invComprasTO.getContTipo(), invComprasTO.getContNumero());
        //Elimina referencia contable
        invComprasTO.setContNumero(null);
        invComprasTO.setContPeriodo(null);
        invComprasTO.setContTipo(null);
        try {
            Map<String, Object> respues = comprasService.modificarCompra(empresa,
                    listaInvComprasDetalleTO,
                    listInvComprasDetalleTOEliminar,
                    listAnxCompraDetalleTO,
                    listAnxCompraDetalleTOEliminar,
                    listAnxCompraReembolsoTO,
                    listAnxCompraReembolsoTOEliminar,
                    anxCompraTO,
                    invComprasTO,
                    formaPago,
                    fp,
                    fpEliminar,
                    desmayorizar,
                    listImagen,
                    sisInfoTO);
            if (respues != null) {
                String mensaje = UtilsJSON.jsonToObjeto(String.class, respues.get("mensaje"));
                InvComprasTO invCompraTORetorno = UtilsJSON.jsonToObjeto(InvComprasTO.class, respues.get("invCompraTO"));
                if (mensaje.substring(0, 1).equals("T")) {
                    if (!invCompraTORetorno.getCompPendiente()) {
                        comprasService.quitarPendiente(invCompraTORetorno);
                    }
                    invCompraTORetorno.setTieneImagenes(listImagen.size() > 0 ? true : false);
                    //anular contable
                    StringBuilder mensajeRetorno = new StringBuilder();
                    String retorno = contableService.anularReversarSql(conContablePK, true, sisInfoTO);
                    mensajeRetorno.append("\n").append(retorno.substring(1, retorno.length()));
                    if (retorno.charAt(0) == 'T') {
                        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                        resp.setOperacionMensaje(retorno.substring(1, retorno.length()));
                        resp.setExtraInfo(invCompraTORetorno);
                        contableService.crearSuceso(conContablePK, "U", sisInfoTO);
                    } else {
                        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                        resp.setOperacionMensaje(retorno.substring(1, retorno.length()));
                    }

                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
                }
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteCompras")
    public @ResponseBody
    String exportarReporteCompras(HttpServletResponse response,
            @RequestBody String json
    ) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvListaConsultaCompraTO> listado = UtilsJSON.jsonToList(InvListaConsultaCompraTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteCompras(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/imprimirCompras")
    public @ResponseBody
    String imprimirCompras(HttpServletResponse response,
            @RequestBody Map<String, Object> parametros
    ) {
        String nombreReporte = "reportCompras.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvListaConsultaCompraTO> listado = UtilsJSON.jsonToList(InvListaConsultaCompraTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteCompras(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /*retencion*/
    @RequestMapping("/isFechaDentroDeDiasHabiles")
    public RespuestaWebTO isFechaDentroDeDiasHabiles(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String fechaCompra = UtilsJSON.jsonToObjeto(String.class, map.get("fechaCompra"));
        String fechaRetencion = UtilsJSON.jsonToObjeto(String.class, map.get("fechaRetencion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean isFechaValida = comprasService.isFechaDentroDeDiasHabiles(fechaRetencion, fechaCompra, sisInfoTO.getEmpresa());
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(isFechaValida);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosBasicosRetencionNueva")
    public RespuestaWebTO obtenerDatosBasicosRetencionNueva(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> resultado = comprasService.obtenerDatosBasicosRetencionNueva(map);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(resultado);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosBasicosRetencionCreada")
    public RespuestaWebTO obtenerDatosBasicosRetencionCreada(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> resultado = comprasService.obtenerDatosBasicosRetencionCreada(map);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(resultado);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/consultarRetencionCompra")
    public RespuestaWebTO consultarRetencionCompra(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> resultado = comprasService.consultarRetencionCompra(map);
            if (resultado != null) {
                List<AnxCompraReembolsoTO> listaAnxCompraReembolsoTO = compraReembolsoService.getAnexoCompraReembolsoTOs(empresa, periodo, motivo, numero);
                resultado.put("listAnxCompraReembolsoTO", listaAnxCompraReembolsoTO);

                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(resultado);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/validarFechasDeRetencionYComprasMismoMes")
    public RespuestaWebTO validarFechasDeRetencionYComprasMismoMes(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String fechaCompra = UtilsJSON.jsonToObjeto(String.class, map.get("fechaCompra"));
        String fechaRetencion = UtilsJSON.jsonToObjeto(String.class, map.get("fechaRetencion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean resultado = comprasService.validarFechasDeRetencionYComprasMismoMes(fechaRetencion, fechaCompra, sisInfoTO);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(resultado);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/validarFechaRetencion")
    public RespuestaWebTO validarFechaRetencion(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = comprasService.validarFechaRetencion(map);
            if (respues != null) {
                String mensaje = UtilsJSON.jsonToObjeto(String.class, respues.get("mensaje"));
                if (mensaje.substring(0, 1).equals("T")) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(true);
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                }
                resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/buscarComprobanteElectronico")
    public RespuestaWebTO buscarComprobanteElectronico(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        RespuestaComprobante respuestaComprobante = UtilsJSON.jsonToObjeto(RespuestaComprobante.class, map.get("respuestaComprobante"));
        String clave = UtilsJSON.jsonToObjeto(String.class, map.get("clave"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = comprasService.buscarComprobanteElectronico(empresa, clave, respuestaComprobante, sisInfoTO);
            if (respues != null) {
                String mensaje = UtilsJSON.jsonToObjeto(String.class, respues.get("mensaje"));
                if (mensaje.substring(0, 1).equals("T")) {
                    //ItemComprobanteElectronico itemComprobanteElectronico = UtilsJSON.jsonToObjeto(ItemComprobanteElectronico.class, respues.get("itemComprobanteElectronico"));
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(respues);
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                }
                resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/compararCombrobanteElectronico")
    public RespuestaWebTO compararCombrobanteElectronico(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String clave = UtilsJSON.jsonToObjeto(String.class, map.get("clave"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = comprasService.compararCombrobanteElectronico(empresa, clave, sisInfoTO);
            String mensaje = UtilsJSON.jsonToObjeto(String.class, respues.get("mensaje"));
            if (mensaje != null && !mensaje.equals("") && mensaje.substring(0, 1) != null && mensaje.substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
            }
            resp.setExtraInfo(respues);
            resp.setOperacionMensaje(mensaje != null && !mensaje.equals("") && mensaje.substring(0, 1) != null ? mensaje.substring(1, mensaje.length()) : "Comprobante electronico no autorizado.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerImagenesCompra")
    public RespuestaWebTO obtenerImagenesCompra(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasPK invComprasPK = UtilsJSON.jsonToObjeto(InvComprasPK.class, map.get("invComprasPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvAdjuntosCompras> respues = comprasService.convertirStringUTF8(invComprasPK);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron imágenes de compra.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerLicenciaScanner")
    public RespuestaWebTO obtenerLicenciaScanner(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respuesta = comprasService.obtenerLicenciaScanner(empresa);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }

        return resp;
    }

    @RequestMapping("/imprimirImagenes")
    public RespuestaWebTO imprimirImagenes(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<String> urls = UtilsJSON.jsonToList(String.class, map.get("urls"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<String> imagenes = new ArrayList<>();
            for (int i = 0; i < urls.size(); i++) {
                java.net.URL url = new java.net.URL(urls.get(i));
                InputStream is = url.openStream();
                byte[] bytes = org.apache.commons.io.IOUtils.toByteArray(is);
                String imagen = Base64.encodeBase64String(bytes);
                imagenes.add(imagen);
            }
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(imagenes);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //PROFORMAS
    @RequestMapping("/getListaInvConsultaProforma")
    public RespuestaWebTO getListaInvConsultaProforma(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        String nRegistros = UtilsJSON.jsonToObjeto(String.class, map.get("nRegistros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvListaConsultaProformaTO> respues = proformasService.getListaInvConsultaProformas(empresa, periodo, motivo, busqueda, nRegistros);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarInvProformasTO")
    public RespuestaWebTO insertarInvProformasTO(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProformasTO invProformasTO = UtilsJSON.jsonToObjeto(InvProformasTO.class, map.get("invProformasTO"));
        List<InvProformasDetalleTO> listaInvProformasDetalleTO = UtilsJSON.jsonToList(InvProformasDetalleTO.class, map.get("listaInvProformasDetalleTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO respues = proformasService.insertarInvProformasTO(invProformasTO, listaInvProformasDetalleTO, sisInfoTO);
            if (respues != null && respues.getMensaje().substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues.getMap().get("pk"));
                resp.setOperacionMensaje(respues.getMensaje().substring(1, respues.getMensaje().length()));
            } else {
                resp.setOperacionMensaje(respues.getMensaje().substring(1, respues.getMensaje().length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarInvProformasTO")
    public RespuestaWebTO modificarInvProformasTO(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProformasTO invProformasTO = UtilsJSON.jsonToObjeto(InvProformasTO.class, map.get("invProformasTO"));
        List<InvProformasDetalleTO> listaInvProformasDetalleTO = UtilsJSON.jsonToList(InvProformasDetalleTO.class, map.get("listaInvProformasDetalleTO"));
        List<InvProformasDetalleTO> listaInvProformasEliminarDetalleTO = UtilsJSON.jsonToList(InvProformasDetalleTO.class, map.get("listaInvProfromasEliminarDetalleTO"));
        boolean quitarAnulado = UtilsJSON.jsonToObjeto(boolean.class, map.get("quitarAnulado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO respues = proformasService.modificarInvProformasTO(invProformasTO, listaInvProformasDetalleTO, listaInvProformasEliminarDetalleTO, quitarAnulado, sisInfoTO);
            if (respues != null && respues.getMensaje().substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje(respues.getMensaje().substring(1, respues.getMensaje().length()));
            } else {
                resp.setOperacionMensaje(respues.getMensaje().substring(1, respues.getMensaje().length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/desmayorizarProforma")
    public RespuestaWebTO desmayorizarProforma(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = proformasService.desmayorizarProforma(empresa, periodo, motivo, numero);
            if (respues != null && respues.substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
            } else {
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/desmayorizarLoteProforma")
    public RespuestaWebTO desmayorizarLoteProforma(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<InvListaConsultaProformaTO> listado = UtilsJSON.jsonToList(InvListaConsultaProformaTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String[] listaMensajes = new String[]{};
        ArrayList<String> listaMensajesEnviar = new ArrayList<String>();
        try {
            String respues = proformasService.desmayorizarLoteProforma(listado, sisInfoTO);
            listaMensajes = respues.substring(1).split("\\|");
            for (int i = 0; i < listaMensajes.length; i++) {
                if (!listaMensajes[i].equals(".")) {
                    listaMensajesEnviar.add(listaMensajes[i].substring(1));
                }
            }

            if (respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1));
                resp.setExtraInfo(listaMensajesEnviar);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se desmayorizó la proforma.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/anularProforma")
    public RespuestaWebTO anularProforma(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = proformasService.anularProforma(empresa, periodo, motivo, numero);
            if (respues != null && respues.substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
            } else {
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/restaurarProforma")
    public RespuestaWebTO restaurarProforma(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = proformasService.restaurarProforma(empresa, periodo, motivo, numero);
            if (respues != null && respues.substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
            } else {
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaInvProformasDetalleTO")
    public RespuestaWebTO getListaInvProformasDetalleTO(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroProformas = UtilsJSON.jsonToObjeto(String.class, map.get("numeroProformas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvListaDetalleProformasTO> respues = proformasDetalleService.getListaInvProformasDetalleTO(empresa, periodo, motivo, numeroProformas);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/nuevaProforma")
    public RespuestaWebTO nuevaProforma(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = proformasService.nuevaProforma(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/consultarProforma")
    public RespuestaWebTO consultarProforma(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = proformasService.consultarProforma(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/consultarProformaSoloActivos")
    public RespuestaWebTO consultarProformaSoloActivos(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());

        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = proformasService.consultarProformaSoloActivos(map);
            if (respues != null) {
                InvProformasTO proforma = UtilsJSON.jsonToObjeto(InvProformasTO.class, respues.get("invProformasTO"));
                if (proforma != null) {
                    if (proforma.getProfAnulado()) {
                        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                        resp.setOperacionMensaje("La proforma está ANULADA");
                    } else {
                        if (proforma.getProfPendiente()) {
                            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                            resp.setOperacionMensaje("La proforma está PENDIENTE");
                        } else {
                            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                            resp.setExtraInfo(respues);
                        }
                    }
                } else {
                    resp.setOperacionMensaje("No existe la proforma");
                }
            } else {
                resp.setOperacionMensaje("No existe la proforma");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirProforma")
    public @ResponseBody
    String imprimirProforma(HttpServletResponse response,
            @RequestBody Map<String, Object> parametros
    ) {
        String nombreReporte = "reportComprobanteProforma.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvProformasPK> listadoPk = UtilsJSON.jsonToList(InvProformasPK.class, parametros.get("listadoPk"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteProforma(usuarioEmpresaReporteTO, nombreReporte, listadoPk);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/imprimirProformaListado")
    public @ResponseBody
    String imprimirProformaListado(HttpServletResponse response,
            @RequestBody Map<String, Object> parametros
    ) {
        String nombreReporte = "reportProformaListado.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvListaConsultaProformaTO> listado = UtilsJSON.jsonToList(InvListaConsultaProformaTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteProformaListado(usuarioEmpresaReporteTO, nombreReporte, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteProformas")
    public @ResponseBody
    String exportarReporteProformas(HttpServletResponse response,
            @RequestBody String json
    ) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvListaConsultaProformaTO> listado = UtilsJSON.jsonToList(InvListaConsultaProformaTO.class, map.get("listadoInvListaConsultaProformaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteProformas(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarInvContableTO")
    public RespuestaWebTO insertarInvContableTO(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String ventaNumero = UtilsJSON.jsonToObjeto(String.class, map.get("ventaNumero"));
        String codigoUsuario = UtilsJSON.jsonToObjeto(String.class, map.get("codigoUsuario"));
        boolean recontabilizar = UtilsJSON.jsonToObjeto(boolean.class, map.get("recontabilizar"));
        String conNumero = UtilsJSON.jsonToObjeto(String.class, map.get("conNumero"));
        String tipCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("tipCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO respues = ventasService.insertarInvContableVentasTO(empresa, periodo, motivo, ventaNumero, codigoUsuario, recontabilizar, conNumero, tipCodigo, sisInfoTO);
            if (respues != null && respues.getMensaje() != null && respues.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje(respues.getMensaje().substring(1));
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respues != null && respues.getMensaje() != null ? respues.getMensaje().substring(1) + respues.getListaErrores1() : "No se ha contabilizado la venta.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteInvListaConsultaVentaTO")
    public @ResponseBody
    String exportarReporteInvListaConsultaVentaTO(HttpServletResponse response,
            @RequestBody String json
    ) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvListaConsultaVentaTO> listInvListaConsultaVentaTO = UtilsJSON.jsonToList(InvListaConsultaVentaTO.class, map.get("listInvListaConsultaVentaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarInvListaConsultaVenta(usuarioEmpresaReporteTO, listInvListaConsultaVentaTO);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteVentas")
    public @ResponseBody
    String generarReporteVentas(HttpServletResponse response,
            @RequestBody Map<String, Object> parametros
    ) {
        String nombreReporte = "reportVentaListado.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvListaConsultaVentaTO> listInvListaConsultaVentaTO = UtilsJSON.jsonToList(InvListaConsultaVentaTO.class, parametros.get("listInvListaConsultaVentaTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteVentas(usuarioEmpresaReporteTO, nombreReporte, listInvListaConsultaVentaTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //Cliente
    @RequestMapping("/obtenerDatosBasicosCliente")
    public RespuestaWebTO obtenerDatosBasicosCliente(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = clienteService.obtenerDatosBasicosCliente(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDetalleVentaRecurrente")
    public RespuestaWebTO obtenerDetalleVentaRecurrente(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        int grupo = UtilsJSON.jsonToObjeto(int.class, map.get("grupo"));
        try {
            List<InvClientesVentasDetalleTO> respues = clientesVentasDetalleDao.listarInvClientesVentasDetalleTO(empresa, cliente, grupo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //Proveedor
    @RequestMapping("/obtenerDatosBasicosProveedor")
    public RespuestaWebTO obtenerDatosBasicosProveedor(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = proveedorService.obtenerDatosBasicosProveedor(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListadoVentasDetalleProducto")
    public RespuestaWebTO getListadoVentasDetalleProducto(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvVentasDetalleProductoTO> respues = productoService.getListadoVentasDetalleProducto(empresa, fechaDesde, fechaHasta, sector, piscina, cliente, bodega);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteInvListaVentasDetalleProducto")
    public @ResponseBody
    String exportarReporteInvListaVentasDetalleProducto(HttpServletResponse response,
            @RequestBody String json
    ) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvVentasDetalleProductoTO> listaVentasDetalleProducto = UtilsJSON.jsonToList(InvVentasDetalleProductoTO.class, map.get("listaVentasDetalleProducto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteInvListaVentasDetalleProducto(usuarioEmpresaReporteTO, listaVentasDetalleProducto);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteVentasDetalleProducto")
    public @ResponseBody
    String generarReporteVentasDetalleProducto(HttpServletResponse response,
            @RequestBody Map<String, Object> parametros
    ) {
        String nombreReporte = "reportVentaDetalleProducto.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvVentasDetalleProductoTO> listaVentasDetalleProducto = UtilsJSON.jsonToList(InvVentasDetalleProductoTO.class, parametros.get("listaVentasDetalleProducto"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteVentasDetalleProducto(usuarioEmpresaReporteTO, nombreReporte, listaVentasDetalleProducto);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReportePorLoteVentasDetalleProducto")
    public @ResponseBody
    String generarReportePorLoteVentasDetalleProducto(HttpServletResponse response,
            @RequestBody String json
    ) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<String> numero = UtilsJSON.jsonToList(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<ReporteVentaDetalle> listadoDetalle = new ArrayList<>();
            for (String numero1 : numero) {
                listadoDetalle.addAll(ventasService.obtenerReporteVentaDetalleProducto(empresa, numero1, sisInfoTO));
            }
            respuesta = reporteInventarioService.generarReporteVentaDetalleImpresion(usuarioEmpresaReporteTO, listadoDetalle, "", "reportComprobanteVentaDetalleProducto");
            return archivoService.generarReportePDF(respuesta, "reportComprobanteVentaDetalleProducto", response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListadoComprasDetalleProducto")
    public RespuestaWebTO getListadoComprasDetalleProducto(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvComprasDetalleProductoTO> respues = productoService.getListadoComprasDetalleProducto(empresa, fechaDesde, fechaHasta, sector, piscina, bodega);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteComprasDetalleProducto")
    public @ResponseBody
    String generarReporteComprasDetalleProducto(HttpServletResponse response,
            @RequestBody Map<String, Object> parametros
    ) {
        String nombreReporte = "reportCompraDetalleProducto.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvComprasDetalleProductoTO> listaComprasDetalleProducto = UtilsJSON.jsonToList(InvComprasDetalleProductoTO.class, parametros.get("listaComprasDetalleProducto"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteComprasDetalleProducto(usuarioEmpresaReporteTO, nombreReporte, listaComprasDetalleProducto);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarInvListaComprasDetalleProducto")
    public @ResponseBody
    String exportarInvListaComprasDetalleProducto(HttpServletResponse response,
            @RequestBody String json
    ) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvComprasDetalleProductoTO> listaComprasDetalleProducto = UtilsJSON.jsonToList(InvComprasDetalleProductoTO.class, map.get("listaComprasDetalleProducto"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechafinal = UtilsJSON.jsonToObjeto(String.class, map.get("fechafinal"));
        String centroProduccion = UtilsJSON.jsonToObjeto(String.class, map.get("centroProduccion"));
        String centroCosto = UtilsJSON.jsonToObjeto(String.class, map.get("centroCosto"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteInvListaComprasDetalleProducto(usuarioEmpresaReporteTO, fechaInicio, fechafinal, centroProduccion, centroCosto, bodega, listaComprasDetalleProducto);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerInvVenta")
    public RespuestaWebTO obtenerInvVenta(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvVentas respues = ventasService.obtenerInvVenta(empresa, periodo, motivo, numero);
            if (respues != null) {
                respues.setInvVentasDetalleList(null);
                respues.setInvVentasRecepcionList(null);
                respues.setInvVentasComplementoList(null);
                respues.setInvVentasMotivoAnulacionList(null);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontró la venta.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //consumos -consolidando productos
    @RequestMapping("/getInvFunConsumosConsolidandoProductosTO")
    public RespuestaWebTO getInvFunConsumosConsolidandoProductosTO(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunConsumosConsolidandoProductosTO> respues = consumosService.getInvFunConsumosConsolidandoProductosTOWeb(empresa, desde, hasta, sector, bodega, motivo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsolidadoConsumoProducto")
    public @ResponseBody
    String generarReporteConsolidadoConsumoProducto(HttpServletResponse response,
            @RequestBody Map<String, Object> map
    ) {
        String nombreReporte = "reportConsolidadoConsumoProducto.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        List<InvFunConsumosConsolidandoProductosTO> listInvFunConsumosConsolidandoProductosTO = UtilsJSON.jsonToList(InvFunConsumosConsolidandoProductosTO.class, map.get("listInvFunConsumosConsolidandoProductosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(map, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteConsolidadoConsumoProducto(usuarioEmpresaReporteTO,
                    fechaDesde, fechaHasta, bodega, sector, motivo, listInvFunConsumosConsolidandoProductosTO);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarReporteConsolidadoConsumoProducto")
    public @ResponseBody
    String exportarReporteConsolidadoConsumoProducto(HttpServletResponse response,
            @RequestBody String json
    ) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        List<InvFunConsumosConsolidandoProductosTO> listInvFunConsumosConsolidandoProductosTO = UtilsJSON.jsonToList(InvFunConsumosConsolidandoProductosTO.class, map.get("listInvFunConsumosConsolidandoProductosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteConsolidadoConsumoProducto(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, bodega, sector, motivo, listInvFunConsumosConsolidandoProductosTO);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerDatosParaAnularRetencion")
    public RespuestaWebTO obtenerDatosParaAnularRetencion(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> resultado = comprasService.obtenerDatosParaAnularRetencion(map);
            if (resultado != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(resultado);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //venta recurrente
    @RequestMapping("/listarClientesParaVentarecurrente")
    public RespuestaWebTO listarClientesParaVentarecurrente(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (sisInfoTO.getEmail() != null && !sisInfoTO.getEmail().equals("")) {
                SisEmpresaParametros parametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, sisInfoTO.getEmpresa());
                if (parametros != null) {
                    if (parametros.isParVentasEnProceso()) {//tiene ventas en proceso
                        resp.setOperacionMensaje("Actualmente existe un proceso realizando ventas recurrentes que aún no culmina. Intente nuevamente dentro de unos minutos..");
                    } else {
                        Map<String, Object> resultado = ventasService.listarClientesParaVentarecurrente(map);
                        if (resultado != null && resultado.get("clientesRecurrentes") != null) {
                            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                            resp.setExtraInfo(resultado);
                        } else {
                            resp.setOperacionMensaje("No se encontraron resultados para realizar la venta recurrente.");
                        }
                    }
                } else {
                    resp.setOperacionMensaje("No se encontraron resultados para empresa parametros.");
                }
            } else {
                resp.setOperacionMensaje("El usuario que realiza la venta recurrente debe tener un correo registrado.");
            }
        } catch (GeneralException e) {
            resp.setOperacionMensaje(e.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarVentasRecurrentes")
    public RespuestaWebTO insertarVentasRecurrentes(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<InvClienteRecurrenteTO> clientes = UtilsJSON.jsonToList(InvClienteRecurrenteTO.class, map.get("clientes"));
        InvVentasTO venta = UtilsJSON.jsonToObjeto(InvVentasTO.class, map.get("venta"));
        String tipodocumento = UtilsJSON.jsonToObjeto(String.class, map.get("tipodocumento"));
        String secuencial = UtilsJSON.jsonToObjeto(String.class, map.get("secuencial"));
        String descripcionProducto = UtilsJSON.jsonToObjeto(String.class, map.get("descripcionProducto"));
        boolean debeContabilizar = UtilsJSON.jsonToObjeto(boolean.class, map.get("debeContabilizar"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RespuestaWebTO> respuestas = new ArrayList<>();
        try {
            if (tipodocumento != null && clientes != null && !clientes.isEmpty() && (secuencial != null || tipodocumento.equals("00"))) {
                SisEmpresaParametros parametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, sisInfoTO.getEmpresa());
                if (parametros != null) {
                    parametros.setParVentasEnProceso(true);
                    empresaParametrosDao.actualizar(parametros);
                }
                ventasRecurrentesService.recorrerClientesParaVentasRecurrentes(clientes, empresa, venta, tipodocumento, secuencial, descripcionProducto, debeContabilizar, usuarioEmpresaReporteTO, sisInfoTO);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("Se enviará un mensaje de confirmación al correo: " + sisInfoTO.getEmail() + ", para poder realizar el siguiente envío de comprobantes.");
            } else {
                resp.setOperacionMensaje("No se realizó ninguna operación por falta de parámetros.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            resp.setExtraInfo(respuestas);
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarMensajesConError")
    public @ResponseBody
    String exportarMensajesConError(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<RespuestaWebTO> listado = UtilsJSON.jsonToList(RespuestaWebTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarMensajesConError(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/validarCantidadDeFacturasParaVentasRecurrentes")
    public RespuestaWebTO validarCantidadDeFacturasParaVentasRecurrentes(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String secuencial = UtilsJSON.jsonToObjeto(String.class, map.get("secuencial"));
        String tipoDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumento"));
        List<InvClienteRecurrenteTO> clientes = UtilsJSON.jsonToList(InvClienteRecurrenteTO.class, map.get("clientes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            int totalFacturas = ventasService.obtenerFacturasDisponiblesPorPuntoEmision(sisInfoTO.getEmpresa(), tipoDocumento, secuencial);
            int totalDeVentasAIngresar = clientes.size();
            if (totalFacturas >= totalDeVentasAIngresar) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(totalFacturas >= totalDeVentasAIngresar);
            } else {
                resp.setOperacionMensaje("No se puede ingresar " + totalDeVentasAIngresar + " ventas debido a que el secuencial elegido: " + secuencial + ", dispone de " + totalFacturas + " documentos.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //Reportes matriciales
    @RequestMapping("/generarReporteBodegasMatricial")
    public RespuestaWebTO generarReporteBodegasMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvListaBodegasTO> listado = UtilsJSON.jsonToList(InvListaBodegasTO.class, map.get("ListadoBodegas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteBodega(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCategoriaClienteMatricial")
    public RespuestaWebTO generarReporteCategoriaClienteMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvClienteCategoriaTO> listado = UtilsJSON.jsonToList(InvClienteCategoriaTO.class, map.get("ListadoCategorias"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteCategoriaCliente(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteFormaCobroMatricial")
    public RespuestaWebTO generarReporteFormaCobroMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvVentasFormaCobroTO> listado = UtilsJSON.jsonToList(InvVentasFormaCobroTO.class, map.get("ListadoFormaCobro"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteFormaCobro(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteFormaPagoMatricial")
    public RespuestaWebTO generarReporteFormaPagoMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvComprasFormaPagoTO> listado = UtilsJSON.jsonToList(InvComprasFormaPagoTO.class, map.get("ListadoFormaPago"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteFormaPago(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteMotivoCompraMatricial")
    public RespuestaWebTO generarReporteMotivoCompraMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvComprasMotivoTO> listado = UtilsJSON.jsonToList(InvComprasMotivoTO.class, map.get("ListadoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteMotivoCompra(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteMotivoConsumoMatricial")
    public RespuestaWebTO generarReporteMotivoConsumoMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvConsumosMotivoTO> listado = UtilsJSON.jsonToList(InvConsumosMotivoTO.class, map.get("ListadoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteMotivoConsumo(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteMotivoProformaMatricial")
    public RespuestaWebTO generarReporteMotivoProformaMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvProformaMotivoTO> listado = UtilsJSON.jsonToList(InvProformaMotivoTO.class, map.get("ListadoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteMotivoProforma(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteMotivoTransferenciaMatricial")
    public RespuestaWebTO generarReporteMotivoTransferenciaMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvTransferenciaMotivoTO> listado = UtilsJSON.jsonToList(InvTransferenciaMotivoTO.class, map.get("ListadoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteMotivoTransferencia(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteMotivoVentaMatricial")
    public RespuestaWebTO generarReporteMotivoVentaMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvVentaMotivoTO> listado = UtilsJSON.jsonToList(InvVentaMotivoTO.class, map.get("ListadoMotivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteMotivoVenta(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteNumeracionCompraMatricial")
    public RespuestaWebTO generarReporteNumeracionCompraMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvNumeracionCompraTO> listado = UtilsJSON.jsonToList(InvNumeracionCompraTO.class, map.get("ListadoNumeracion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteNumeracionCompra(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteNumeracionConsumoMatricial")
    public RespuestaWebTO generarReporteNumeracionConsumoMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvNumeracionConsumoTO> listado = UtilsJSON.jsonToList(InvNumeracionConsumoTO.class, map.get("ListadoNumeracion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteNumeracionConsumo(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteNumeracionVentaMatricial")
    public RespuestaWebTO generarReporteNumeracionVentaMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvNumeracionVentaTO> listado = UtilsJSON.jsonToList(InvNumeracionVentaTO.class, map.get("ListadoNumeracion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteNumeracionVenta(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteProductoMarcaMatricial")
    public RespuestaWebTO generarReporteProductoMarcaMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvProductoMarcaComboListadoTO> listado = UtilsJSON.jsonToList(InvProductoMarcaComboListadoTO.class, map.get("listInvProductoMarcaComboListadoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteProductoMarca(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePresentacionCajaMatricial")
    public RespuestaWebTO generarReportePresentacionCajaMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvProductoPresentacionCajasComboListadoTO> listado = UtilsJSON.jsonToList(InvProductoPresentacionCajasComboListadoTO.class, map.get("listInvProductoPresentacionCajasComboListadoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReportePresentacionCaja(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteProductoPresentacionUnidadMatricial")
    public RespuestaWebTO generarReporteProductoPresentacionUnidadMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvProductoPresentacionUnidadesComboListadoTO> listado = UtilsJSON.jsonToList(InvProductoPresentacionUnidadesComboListadoTO.class, map.get("listInvProductoPresentacionUnidadesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReportePresentacionUnidad(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteProductoTipoMatricial")
    public RespuestaWebTO generarReporteProductoTipoMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvProductoTipoComboTO> listado = UtilsJSON.jsonToList(InvProductoTipoComboTO.class, map.get("listInvProductoTipoComboTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteProductoTipo(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteProveedorMatricial")
    public RespuestaWebTO generarReporteProveedorMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvProveedorTO> listado = UtilsJSON.jsonToList(InvProveedorTO.class, map.get("ListadoProveedores"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = proveedorService.generarReporteProveedor(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteCategoriaProveedorMatricial")
    public RespuestaWebTO generarReporteCategoriaProveedorMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvProveedorCategoriaTO> listado = UtilsJSON.jsonToList(InvProveedorCategoriaTO.class, map.get("ListadoCategorias"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteCategoriaProveedor(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteClienteMatricial")
    public RespuestaWebTO generarReporteClienteMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvFunListadoClientesTO> listado = UtilsJSON.jsonToList(InvFunListadoClientesTO.class, map.get("listClientes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteCliente(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteInvKardexMatricial")
    public RespuestaWebTO generarReporteInvKardexMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String nombreProducto = UtilsJSON.jsonToObjeto(String.class, map.get("nombreProducto"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<InvKardexTO> listInvKardexTO = UtilsJSON.jsonToList(InvKardexTO.class, map.get("listInvKardexTO"));
        boolean banderaCosto = UtilsJSON.jsonToObjeto(boolean.class, map.get("banderaCosto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteInvKardex(usuarioEmpresaReporteTO, nombreProducto, fechaDesde, fechaHasta, listInvKardexTO, banderaCosto);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteProductosMatricial")
    public RespuestaWebTO generarReporteProductosMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvListaProductosGeneralTO> listado = UtilsJSON.jsonToList(InvListaProductosGeneralTO.class, parametros.get("listInvListaProductosGeneralTO"));
        List<InvFunListadoProductosTO> listado2 = UtilsJSON.jsonToList(InvFunListadoProductosTO.class, parametros.get("listInvFunListadoProductosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteProductos(usuarioEmpresaReporteTO, listado, listado2);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteInvProductoCategoriaTOMatricial")
    public RespuestaWebTO generarReporteInvProductoCategoriaTOMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportProductoCategoria.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvProductoCategoriaTO> listaInvProductoCategoriaTO = UtilsJSON.jsonToList(InvProductoCategoriaTO.class, parametros.get("listadoInvProductoCategoriaTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteInvProductoCategoriaTO(usuarioEmpresaReporteTO, listaInvProductoCategoriaTO, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteInvProductoMedidaTOMatricial")
    public RespuestaWebTO generarReporteInvProductoMedidaTOMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportProductoUnidadMedida.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvProductoMedidaTO> listaInvProductoMedidaTO = UtilsJSON.jsonToList(InvProductoMedidaTO.class, parametros.get("listadoInvProductoMedidaTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteInvProductoMedidaTO(usuarioEmpresaReporteTO, listaInvProductoMedidaTO, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteProformaMatricial")
    public RespuestaWebTO generarReporteProformaMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportComprobanteProforma.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvProformasPK> listadoPk = UtilsJSON.jsonToList(InvProformasPK.class, parametros.get("listadoPk"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteProforma(usuarioEmpresaReporteTO, nombreReporte, listadoPk);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteTrasferenciasMatricial")
    public RespuestaWebTO generarReporteTrasferenciasMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        List<String> numero = UtilsJSON.jsonToList(String.class, parametros.get("numero"));
        List<String> comprobante = UtilsValidacion.separarComprobante(numero.get(0));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteTrasferencias(empresa, comprobante.get(0), comprobante.get(1), comprobante.get(2), usuarioEmpresaReporteTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteVentasMatricial")
    public RespuestaWebTO generarReporteVentasMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportVentaListado.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvListaConsultaVentaTO> listInvListaConsultaVentaTO = UtilsJSON.jsonToList(InvListaConsultaVentaTO.class, parametros.get("listInvListaConsultaVentaTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteVentas(usuarioEmpresaReporteTO, nombreReporte, listInvListaConsultaVentaTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteInvFunVentasVsCostoMatricial")
    public RespuestaWebTO generarReporteInvFunVentasVsCostoMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, parametros.get("bodega"));
        String clienteId = UtilsJSON.jsonToObjeto(String.class, parametros.get("clienteId"));
        List<InvFunVentasVsCostoTO> invFunVentasVsCostoTO = UtilsJSON.jsonToList(InvFunVentasVsCostoTO.class, parametros.get("invFunVentasVsCostoTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteInvFunVentasVsCosto(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, bodega, clienteId, invFunVentasVsCostoTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsolidadoCompraProductoMatricial")
    public RespuestaWebTO generarReporteConsolidadoCompraProductoMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, parametros.get("bodega"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedor"));
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvFunComprasConsolidandoProductosTO> listado = UtilsJSON.jsonToList(InvFunComprasConsolidandoProductosTO.class, parametros.get("listInvFunComprasConsolidandoProductosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteConsolidadoCompraProducto(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, bodega, proveedor, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteComprasDetalleProductoMatricial")
    public RespuestaWebTO generarReporteComprasDetalleProductoMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportCompraDetalleProducto.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvComprasDetalleProductoTO> listaComprasDetalleProducto = UtilsJSON.jsonToList(InvComprasDetalleProductoTO.class, parametros.get("listaComprasDetalleProducto"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteComprasDetalleProducto(usuarioEmpresaReporteTO, nombreReporte, listaComprasDetalleProducto);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteImpresionPlacaMatricial")
    public RespuestaWebTO generarReporteImpresionPlacaMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<DatoFunListaProductosImpresionPlaca> listProductosImpresionPlaca = UtilsJSON.jsonToList(DatoFunListaProductosImpresionPlaca.class, parametros.get("listProductosImpresionPlaca"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteImpresionPlaca(usuarioEmpresaReporteTO, listProductosImpresionPlaca);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoComprasMatricial")
    public RespuestaWebTO generarReporteListadoComprasMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, parametros.get("motivo"));
        String proveedorId = UtilsJSON.jsonToObjeto(String.class, parametros.get("proveedorId"));
        String documento = UtilsJSON.jsonToObjeto(String.class, parametros.get("documento"));
        List<InvFunComprasTO> listInvFunComprasTO = UtilsJSON.jsonToList(InvFunComprasTO.class, parametros.get("listInvFunComprasTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteListadoCompras(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, motivo, proveedorId, documento, listInvFunComprasTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoConsumosMatricial")
    public RespuestaWebTO generarReporteListadoConsumosMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<InvFunConsumosTO> listInvFunConsumosTO = UtilsJSON.jsonToList(InvFunConsumosTO.class, parametros.get("listInvFunConsumosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteListadoConsumos(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, listInvFunConsumosTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsumoDetalleMatricial")
    public RespuestaWebTO generarReporteConsumoDetalleMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvConsumosTO> invConsumosTOs = UtilsJSON.jsonToList(InvConsumosTO.class, parametros.get("invConsumosTOs"));
        boolean ordenado = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("ordenado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            if (ordenado) {
                nombreReporte = "reportComprobanteConsumoOrdenado.jrxml";
            } else {
                nombreReporte = "reportComprobanteConsumo.jrxml";
            }
            respuesta = reporteInventarioService.generarReporteConsumoDetalle(invConsumosTOs, ordenado, usuarioEmpresaReporteTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsolidadoConsumoProductoMatricial")
    public RespuestaWebTO generarReporteConsolidadoConsumoProductoMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        List<InvFunConsumosConsolidandoProductosTO> listInvFunConsumosConsolidandoProductosTO = UtilsJSON.jsonToList(InvFunConsumosConsolidandoProductosTO.class, map.get("listInvFunConsumosConsolidandoProductosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(map, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteConsolidadoConsumoProducto(usuarioEmpresaReporteTO,
                    fechaDesde, fechaHasta, bodega, sector, motivo, listInvFunConsumosConsolidandoProductosTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListaProductosMatricial")
    public RespuestaWebTO generarReporteListaProductosMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, parametros.get("bodega"));
        List<InvListaProductosTO> listInvListaProductosTO = UtilsJSON.jsonToList(InvListaProductosTO.class, parametros.get("listInvListaProductosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteListaProductos(usuarioEmpresaReporteTO, bodega, listInvListaProductosTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteProveedoresMatricial")
    public RespuestaWebTO generarReporteProveedoresMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvProveedorTO> listado = UtilsJSON.jsonToList(InvProveedorTO.class, parametros.get("ListadoProveedores"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = proveedorService.generarReporteProveedor(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteListadoVentasMatricial")
    public RespuestaWebTO generarReporteListadoVentasMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, parametros.get("motivo"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, parametros.get("cliente"));
        String documento = UtilsJSON.jsonToObjeto(String.class, parametros.get("documento"));
        List<InvFunVentasTO> listInvFunVentasTO = UtilsJSON.jsonToList(InvFunVentasTO.class, parametros.get("listInvFunVentasTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteListadoVentas(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, motivo, cliente, documento, listInvFunVentasTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteInvProductosConErrorMatricial")
    public RespuestaWebTO generarReporteInvProductosConErrorMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvProductosConErrorTO> listInvProductosConErrorTO = UtilsJSON.jsonToList(InvProductosConErrorTO.class, parametros.get("listInvProductosConErrorTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteInvProductosConError(usuarioEmpresaReporteTO, listInvProductosConErrorTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteSaldoBodegaMatricial")
    public RespuestaWebTO generarReporteSaldoBodegaMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, parametros.get("bodega"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        List<SaldoBodegaTO> listSaldoBodegaTO = UtilsJSON.jsonToList(SaldoBodegaTO.class, parametros.get("listSaldoBodegaTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteSaldoBodega(usuarioEmpresaReporteTO, bodega, fechaHasta, listSaldoBodegaTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteSaldoBodegaComprobacionCantidadesMatricial")
    public RespuestaWebTO generarReporteSaldoBodegaComprobacionCantidadesMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, parametros.get("bodega"));
        List<SaldoBodegaComprobacionTO> listado = UtilsJSON.jsonToList(SaldoBodegaComprobacionTO.class, parametros.get("lista"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteSaldoBodegaComprobacionCantidades(usuarioEmpresaReporteTO, bodega, fechaDesde, fechaHasta, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteSaldoBodegaComprobacionMatricial")
    public RespuestaWebTO generarReporteSaldoBodegaComprobacionMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, parametros.get("bodega"));
        List<SaldoBodegaComprobacionTO> listado = UtilsJSON.jsonToList(SaldoBodegaComprobacionTO.class, parametros.get("lista"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteSaldoBodegaComprobacion(usuarioEmpresaReporteTO, bodega, fechaDesde, fechaHasta, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsolidadoVentaClienteMatricial")
    public RespuestaWebTO generarReporteConsolidadoVentaClienteMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        List<InvFunVentasConsolidandoClientesTO> listado = UtilsJSON.jsonToList(InvFunVentasConsolidandoClientesTO.class, parametros.get("listInvFunVentasConsolidandoClientesTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteConsolidadoVentaCliente(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, sector, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsolidadoVentaProductoMatricial")
    public RespuestaWebTO generarReporteConsolidadoVentaProductoMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, parametros.get("bodega"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, parametros.get("cliente"));
        List<InvFunVentasConsolidandoProductosTO> listado = UtilsJSON.jsonToList(InvFunVentasConsolidandoProductosTO.class, parametros.get("listInvFunVentasConsolidandoProductosTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteConsolidadoVentaProducto(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, bodega, cliente, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteConsolidadoVentaProductoCoberturaMatricial")
    public RespuestaWebTO generarReporteConsolidadoVentaProductoCoberturaMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportConsolidadoVentaProductoCobertura.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, parametros.get("fechaHasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, parametros.get("motivo"));
        String sector = UtilsJSON.jsonToObjeto(String.class, parametros.get("sector"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, parametros.get("bodega"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, parametros.get("cliente"));
        List<InvFunVentasConsolidandoProductosCoberturaTO> listado = UtilsJSON.jsonToList(InvFunVentasConsolidandoProductosCoberturaTO.class, parametros.get("listInvFunVentasConsolidandoProductosCoberturaTO"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteConsolidadoVentaProductoCobertura(usuarioEmpresaReporteTO, fechaDesde, fechaHasta, sector, bodega, motivo, cliente, listado, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteVentasDetalleProductoMatricial")
    public RespuestaWebTO generarReporteVentasDetalleProductoMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportVentaDetalleProducto.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvVentasDetalleProductoTO> listaVentasDetalleProducto = UtilsJSON.jsonToList(InvVentasDetalleProductoTO.class, parametros.get("listaVentasDetalleProducto"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteVentasDetalleProducto(usuarioEmpresaReporteTO, nombreReporte, listaVentasDetalleProducto);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePorLoteVentasDetalleProductoMatricial")
    public RespuestaWebTO generarReportePorLoteVentasDetalleProductoMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<String> numero = UtilsJSON.jsonToList(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<ReporteVentaDetalle> listadoDetalle = new ArrayList<>();
            for (String numero1 : numero) {
                listadoDetalle.addAll(ventasService.obtenerReporteVentaDetalleProducto(empresa, numero1, sisInfoTO));
            }
            respuesta = reporteInventarioService.generarReporteVentaDetalleImpresion(usuarioEmpresaReporteTO, listadoDetalle, "", "reportComprobanteVentaDetalleProducto");
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteImprimirComprasMatricial")
    public RespuestaWebTO generarReporteImprimirComprasMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvListaConsultaCompraTO> listado = UtilsJSON.jsonToList(InvListaConsultaCompraTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteCompras(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteImprimirProformasMatricial")
    public RespuestaWebTO generarReporteImprimirProformasMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        String nombreReporte = "reportComprobanteProforma.jrxml";
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvProformasPK> listadoPk = UtilsJSON.jsonToList(InvProformasPK.class, parametros.get("listadoPk"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteProforma(usuarioEmpresaReporteTO, nombreReporte, listadoPk);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteImprimirProformaListadoMatricial")
    public RespuestaWebTO generarReporteImprimirProformaListadoMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> parametros = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        String nombreReporte = "reportProformaListado.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvListaConsultaProformaTO> listado = UtilsJSON.jsonToList(InvListaConsultaProformaTO.class, parametros.get("listado"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteProformaListado(usuarioEmpresaReporteTO, nombreReporte, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReportePorLoteTrasferenciasMatricial")
    public RespuestaWebTO generarReportePorLoteTrasferenciasMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<String> numero = UtilsJSON.jsonToList(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReportePorLoteTrasferencias(empresa, numero, usuarioEmpresaReporteTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteTrasferenciasListadoMatricial")
    public RespuestaWebTO generarReporteTrasferenciasListadoMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        List<InvListaConsultaTransferenciaTO> listado = UtilsJSON.jsonToList(InvListaConsultaTransferenciaTO.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteTrasferenciasListado(empresa, listado, usuarioEmpresaReporteTO);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*Series*/
    @RequestMapping("/listarSeriesVigentes")
    public RespuestaWebTO listarSeriesVigentes(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String producto = UtilsJSON.jsonToObjeto(String.class, map.get("producto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvSerieTO> series = invSeriesService.listarSeriesVigentes(empresa, bodega, producto);
            if (series != null && !series.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(series);
            } else {
                resp.setOperacionMensaje("No se encontraron series.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/validarSerieRepetidaAlGuardarVenta")
    public RespuestaWebTO validarSerieRepetidaAlGuardarVenta(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String tipoDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumento"));
        String producto = UtilsJSON.jsonToObjeto(String.class, map.get("producto"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String serie = UtilsJSON.jsonToObjeto(String.class, map.get("serie"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean esSerieValida = invSeriesService.esSerieValidaAlGuardarVenta(tipoDocumento, serie, producto, bodega, empresa);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(esSerieValida);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/validarSerieRepetidaAlGuardarConsumo")
    public RespuestaWebTO validarSerieRepetidaAlGuardarConsumo(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String producto = UtilsJSON.jsonToObjeto(String.class, map.get("producto"));
        String serie = UtilsJSON.jsonToObjeto(String.class, map.get("serie"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean esRepetido = invSeriesService.esSerieValidaAlGuardarVenta(null, serie, producto, bodega, sisInfoTO.getEmpresa());
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(esRepetido);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/validarSerieOcupada")
    public RespuestaWebTO validarSerieOcupada(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigo_producto = UtilsJSON.jsonToObjeto(String.class, map.get("prodCodigoPrincipal"));
        String serie = UtilsJSON.jsonToObjeto(String.class, map.get("serie"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean ocupado = invSeriesService.serieCompraOcupada(empresa, serie, codigo_producto);
            resp.setExtraInfo(ocupado);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/validarSiExisteSerieCompra")
    public RespuestaWebTO validarSiExisteSerieCompra(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Integer secuencial = UtilsJSON.jsonToObjeto(Integer.class, map.get("secuencial"));//Editando
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigo_producto = UtilsJSON.jsonToObjeto(String.class, map.get("prodCodigoPrincipal"));
        String serie = UtilsJSON.jsonToObjeto(String.class, map.get("serie"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean ocupado = invSeriesService.validarSiExisteSerieCompra(empresa, serie, codigo_producto, secuencial);
            resp.setExtraInfo(ocupado);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaCrudMotivoVentas")
    public RespuestaWebTO obtenerDatosParaCrudMotivoVentas(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> resultado = ventasMotivoService.obtenerDatosParaCrudMotivoVentas(map);
            if (resultado != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(resultado);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteVendedor")
    public @ResponseBody
    String generarReporteVendedor(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportVendedor.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvVendedorComboListadoTO> listado = UtilsJSON.jsonToList(InvVendedorComboListadoTO.class, parametros.get("listVendedores"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteVendedor(usuarioEmpresaReporteTO, listado);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteVendedorMatricial")
    public RespuestaWebTO generarReporteVendedorMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvVendedorComboListadoTO> listado = UtilsJSON.jsonToList(InvVendedorComboListadoTO.class, map.get("listVendedores"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteVendedor(usuarioEmpresaReporteTO, listado);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteVendedor")
    public @ResponseBody
    String exportarReporteVendedor(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvVendedorComboListadoTO> listado = UtilsJSON.jsonToList(InvVendedorComboListadoTO.class, map.get("listVendedores"));
        List<DetalleExportarFiltrado> listadoFiltrado = UtilsJSON.jsonToList(DetalleExportarFiltrado.class, map.get("listVendedoresFiltrado"));

        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteVendedor(usuarioEmpresaReporteTO, listado, listadoFiltrado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //TRANSPORTISTA
    @RequestMapping("/listarInvTransportistaTO")
    public RespuestaWebTO listarInvTransportistaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        boolean inactivo = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvTransportistaTO> lista = invTransportistaService.getListaInvTransportistaTO(empresa, busqueda, inactivo);
            if (lista != null && !lista.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(lista);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarInvTransportistaTO")
    public RespuestaWebTO insertarInvTransportistaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvTransportistaTO invTransportistaTO = UtilsJSON.jsonToObjeto(InvTransportistaTO.class, map.get("invTransportistaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = invTransportistaService.insertarInvTransportistaTO(invTransportistaTO, sisInfoTO);
            if (respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(invTransportistaTO);
                resp.setOperacionMensaje("El transportista: Código " + invTransportistaTO.getTransCodigo() + ", se ha guardado correctamente.");
            } else {
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarInvTransportistaTO")
    public RespuestaWebTO modificarInvTransportistaTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvTransportistaTO invTransportistaTO = UtilsJSON.jsonToObjeto(InvTransportistaTO.class, map.get("invTransportistaTO"));
        String codigoAnterior = UtilsJSON.jsonToObjeto(String.class, map.get("codigoAnterior"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = invTransportistaService.modificarInvTransportistaTO(invTransportistaTO, codigoAnterior, sisInfoTO);
            if (respues.charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(invTransportistaTO);
                resp.setOperacionMensaje("El transportista: código " + invTransportistaTO.getTransCodigo() + ", se ha modificado correctamente.");
            } else {
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarEstadoInvTransportista")
    public RespuestaWebTO modificarEstadoInvTransportista(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvTransportistaPK pk = UtilsJSON.jsonToObjeto(InvTransportistaPK.class, map.get("pk"));
        boolean estado = UtilsJSON.jsonToObjeto(Boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = invTransportistaService.modificarEstadoInvTransportista(pk, estado, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("El estado de transportista: Código: " + pk.getTransCodigo() + ", se ha modificado correctamente.");
            } else {
                resp.setOperacionMensaje("No se ha modificado correctamente el estado del cliente.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarInvTransportista")
    public RespuestaWebTO eliminarInvTransportista(@RequestBody String json) throws Exception, GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvTransportistaPK pk = UtilsJSON.jsonToObjeto(InvTransportistaPK.class, map.get("pk"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = invTransportistaService.eliminarInvTransportista(pk, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("El transportista: Código " + pk.getTransCodigo() + ", se ha eliminado correctamente.");
            } else {
                resp.setOperacionMensaje("No se ha eliminado el transportista.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (DataIntegrityViolationException ge) {
            resp.setOperacionMensaje("No se ha eliminado. El transportista tiene movimientos.");
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getTransportistaRepetido")
    public RespuestaWebTO getTransportistaRepetido(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        if (empresa != null && !empresa.equals("")) {
            empresa = "'" + empresa + "'";
        }
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        if (codigo != null && !codigo.equals("")) {
            codigo = "'" + codigo + "'";
        }
        String id = UtilsJSON.jsonToObjeto(String.class, map.get("id"));
        if (id != null && !id.equals("")) {
            id = "'" + id + "'";
        }
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        if (nombre != null && !nombre.equals("")) {
            nombre = "'" + nombre + "'";
        }
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = invTransportistaService.getTransportistaRepetido(empresa, codigo, id, nombre);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("El transportista ya existe.");
            } else {
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje("El transportista no existe.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //GUIA REMISION
    @RequestMapping("/getListaInvGuiaRemisionTO")
    public RespuestaWebTO getListaInvGuiaRemisionTO(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String tipoDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumento"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFinal = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFinal"));
        String nRegistros = UtilsJSON.jsonToObjeto(String.class, map.get("nRegistros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvConsultaGuiaRemisionTO> respues = guiaRemisionService.getListaInvGuiaRemisionTO(empresa, tipoDocumento, fechaInicio, fechaFinal, nRegistros);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosBasicosGuiaRemision")
    public RespuestaWebTO obtenerDatosBasicosGuiaRemision(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = guiaRemisionService.obtenerDatosBasicosGuiaRemision(map);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontro guia remisión");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosBasicosGuiaRemisionConsulta")
    public RespuestaWebTO obtenerDatosBasicosGuiaRemisionConsulta(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = guiaRemisionService.obtenerDatosBasicosGuiaRemisionConsulta(map);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontro guia remisión");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return resp;
    }

    @RequestMapping("/insertarInvGuiaRemision")
    public RespuestaWebTO insertarInvGuiaRemision(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvGuiaRemision invGuiaRemision = UtilsJSON.jsonToObjeto(InvGuiaRemision.class, map.get("invGuiaRemision"));
        List<InvGuiaRemisionDetalleTO> listaInvGuiaRemisionDetalleTO = UtilsJSON.jsonToList(InvGuiaRemisionDetalleTO.class, map.get("listaDetalle"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = guiaRemisionService.insertarTransaccionInvGuiaRemision(invGuiaRemision, listaInvGuiaRemisionDetalleTO, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMap());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setExtraInfo(mensajeTO.getListaErrores1());
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado la guía de remisión.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarInvGuiaRemision")
    public RespuestaWebTO modificarInvGuiaRemision(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvGuiaRemision invGuiaRemision = UtilsJSON.jsonToObjeto(InvGuiaRemision.class, map.get("invGuiaRemision"));
        List<InvGuiaRemisionDetalleTO> listaInvGuiaRemisionDetalleTO = UtilsJSON.jsonToList(InvGuiaRemisionDetalleTO.class, map.get("listaDetalle"));
        List<InvGuiaRemisionDetalleTO> listaInvGuiaRemisionDetalleTOEliminar = UtilsJSON.jsonToList(InvGuiaRemisionDetalleTO.class, map.get("listaDetalleEliminar"));

        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = guiaRemisionService.modificarTransaccionInvGuiaRemision(invGuiaRemision, listaInvGuiaRemisionDetalleTO, listaInvGuiaRemisionDetalleTOEliminar, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMap());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setExtraInfo(mensajeTO.getListaErrores1());
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado la guía de remisión.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListInvClientesDirecciones")
    public RespuestaWebTO getListInvClientesDirecciones(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cliCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("cliCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvClientesDirecciones> respues = clienteService.getListInvClientesDirecciones(empresa, cliCodigo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/anularGuiaRemision")
    public RespuestaWebTO anularGuiaRemision(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = guiaRemisionService.anularGuiaRemision(empresa, periodo, numero);
            if (respues != null && respues.substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
            } else {
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/desmayorizarGuiaRemision")
    public RespuestaWebTO desmayorizarGuiaRemision(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String respues = guiaRemisionService.desmayorizarGuiaRemision(empresa, periodo, numero);
            if (respues != null && respues.substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
            } else {
                resp.setOperacionMensaje(respues.substring(1, respues.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteInvGuiaRemision")
    public @ResponseBody
    String generarReporteInvGuiaRemision(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvGuiaRemisionPK> listado = UtilsJSON.jsonToList(InvGuiaRemisionPK.class, parametros.get("listInvGuiaRemisionPK"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, parametros.get("nombreReporte"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            List<InvGuiaRemision> lista = new ArrayList<InvGuiaRemision>();
            for (InvGuiaRemisionPK pk : listado) {
                InvGuiaRemision guiaRemision = guiaRemisionService.buscarInvGuiaRemision(pk.getGuiaEmpresa(), pk.getGuiaPeriodo(), pk.getGuiaNumero());
                lista.add(guiaRemision);
            }
            respuesta = reporteInventarioService.generarReporteInvGuiaRemision(usuarioEmpresaReporteTO, lista, nombreReporte);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteInvGuiaRemisionMatricial")
    public RespuestaWebTO generarReporteInvGuiaRemisionMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvGuiaRemisionPK> listado = UtilsJSON.jsonToList(InvGuiaRemisionPK.class, map.get("listInvGuiaRemisionPK"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvGuiaRemision> lista = new ArrayList<InvGuiaRemision>();
            for (InvGuiaRemisionPK pk : listado) {
                InvGuiaRemision guiaRemision = guiaRemisionService.buscarInvGuiaRemision(pk.getGuiaEmpresa(), pk.getGuiaPeriodo(), pk.getGuiaNumero());
                lista.add(guiaRemision);
            }
            respuesta = reporteInventarioService.generarReporteInvGuiaRemision(usuarioEmpresaReporteTO, lista, nombreReporte);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //VALIDACION LIQUIDACION COMPRAS
    @RequestMapping("/obtenerDatosParaLiquidacionCompra")
    public RespuestaWebTO obtenerDatosParaLiquidacionCompra(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = liquidacionComprasService.obtenerDatosParaLiquidacionCompra(map);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(respues);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarMovimientosSeries")
    public RespuestaWebTO listarMovimientosSeries(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String serie = UtilsJSON.jsonToObjeto(String.class, map.get("serie"));
        String producto = UtilsJSON.jsonToObjeto(String.class, map.get("producto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvFunMovimientosSerieTO> respues = invSeriesService.listarMovimientosSeries(empresa, serie, producto);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarMovimientosSeries")
    public @ResponseBody
    String exportarMovimientosSeries(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvFunMovimientosSerieTO> datos = UtilsJSON.jsonToList(InvFunMovimientosSerieTO.class, map.get("datos"));
        String titulo = "Reporte de Movimientos de series";
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarMovimientosSeries(usuarioEmpresaReporteTO, titulo, datos);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/listarInvClienteNotificaciones")
    public RespuestaWebTO listarInvClienteNotificaciones(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String cliCodigo = UtilsJSON.jsonToObjeto(String.class, parametros.get("cliCodigo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, parametros.get("motivo"));
        int posicion = UtilsJSON.jsonToObjeto(int.class, parametros.get("posicion"));
        int limite = UtilsJSON.jsonToObjeto(int.class, parametros.get("limite"));
        try {
            List<InvClienteNotificaciones> respues = invClienteNotificacionesService.listarNotificacionesElectronicas(empresa, cliCodigo, motivo, limite, posicion);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encuentra historial de notificaciones");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarInvListaSecuencialesVentas")
    public RespuestaWebTO listarInvListaSecuencialesVentas(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String tipoDocumento = SistemaWebUtil.obtenerFiltroComoString(parametros, "tipoDocumento");
        try {
            List<InvListaSecuencialesTO> respues = ventasService.listarInvListaSecuencialesVentas(empresa, tipoDocumento);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarVentas")
    public RespuestaWebTO eliminarVentas(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String vtaNumero = UtilsJSON.jsonToObjeto(String.class, map.get("vtaNumero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<String> documento = UtilsValidacion.separar(vtaNumero, "|");
            String periodo = documento.get(0);
            String motivo = documento.get(1);
            String numero = documento.get(2);
            String resultado = ventasService.eliminarVentas(empresa, periodo, motivo, numero, sisInfoTO);
            if (resultado.equals("t")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo("La venta N. " + numero + " se eliminó exitosamente.");
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(resultado);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarCompras")
    public RespuestaWebTO eliminarCompras(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String resultado = comprasService.eliminarCompras(empresa, periodo, motivo, numero, sisInfoTO);
            if (resultado.equals("t")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo("La compra N. " + numero + " se eliminó exitosamente.");
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(resultado);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarClienteGrupoEmpresarial")
    public RespuestaWebTO modificarClienteGrupoEmpresarial(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvClientePK invClientePk = UtilsJSON.jsonToObjeto(InvClientePK.class, map.get("invClientePk"));
        String codigoGrupoEmpresarial = UtilsJSON.jsonToObjeto(String.class, map.get("codigoGrupoEmpresarial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = clienteService.modificarClienteGrupoEmpresarial(invClientePk, codigoGrupoEmpresarial, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(true);
                resp.setOperacionMensaje("El grupo empresarial del cliente: Código " + invClientePk.getCliCodigo() + ", se ha modificado correctamente.");
            } else {
                resp.setOperacionMensaje("El cliente ya no existe");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/validarNumeracionSegunSector")
    public RespuestaWebTO validarNumeracionSegunSector(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String tipoDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumento"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = ventasService.validarNumeracionSegunSector(empresa, tipoDocumento, codigoSector, numero);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(respues);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/invListadoCobrosTO")
    public RespuestaWebTO invListadoCobrosTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, parametros.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, parametros.get("numero"));
        try {
            List<InvListadoCobrosTO> respues = ventasService.invListadoCobrosTO(empresa, periodo, motivo, numero);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/validacionGrupoSegunCategoria")
    public RespuestaWebTO validacionGrupoSegunCategoria(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String categoriaCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("categoriaCodigo"));
        String cuentaInventario = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaInventario"));
        String cuentaVenta = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaVenta"));
        String cuentaCostoAutomatico = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaCostoAutomatico"));
        String cuentaGasto = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaGasto"));
        String cuentaVentaExt = UtilsJSON.jsonToObjeto(String.class, map.get("cuentaVentaExterior"));
        boolean isActivoFijo = UtilsJSON.jsonToObjeto(boolean.class, map.get("isActivoFijo"));
        boolean isExportadora = UtilsJSON.jsonToObjeto(boolean.class, map.get("isExportadora"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = productoCategoriaService.validacionGrupoSegunCategoria(empresa, categoriaCodigo, cuentaInventario, cuentaVenta, cuentaCostoAutomatico, cuentaGasto, cuentaVentaExt, isExportadora, isActivoFijo);
            resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
            resp.setExtraInfo(respues);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    //Guia remision venta
    @RequestMapping("/obtenerDatosBasicosVentaGuiaRemision")
    public RespuestaWebTO obtenerDatosBasicosVentaGuiaRemision(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = ventaGuiaRemisionService.obtenerDatosBasicosVentaGuiaRemision(map);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontro guia remisión");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaProductosPrecio")
    public RespuestaWebTO obtenerDatosParaProductosPrecio(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = productoService.obtenerDatosParaProductosPrecio(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron los datos para generar producto.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarInvProformaClienteTO")
    public RespuestaWebTO listarInvProformaClienteTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String cliCodigo = UtilsJSON.jsonToObjeto(String.class, parametros.get("cliCodigo"));
        try {
            List<InvProformaClienteTO> respues = proformasService.listarInvProformaClienteTO(empresa, cliCodigo);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encuentraron proformas.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/validarNumeracionVenta")
    public RespuestaWebTO validarNumeracionVenta(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = ventasService.validarNumeracionVenta(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaProductosCambiarPrecioTO")
    public RespuestaWebTO getListaProductosCambiarPrecioTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, parametros.get("busqueda"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, parametros.get("bodega"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, parametros.get("fecha"));
        try {
            List<InvListaProductosCambiarPrecioTO> respues = productoSaldosService.getListaProductosCambiarPrecioTO(empresa, busqueda, bodega, fecha);
            if (respues != null && !respues.isEmpty()) {
                List<InvListaProductosCambiarPrecioTO> listadoFormateado = productoSaldosService.formatearListadoInvListaProductosCambiarPrecioTO(respues);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(listadoFormateado);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListaInvCompraDetalleTO")
    public RespuestaWebTO getListaInvCompraDetalleTO(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, parametros.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, parametros.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, parametros.get("motivo"));
        String numeroCompra = UtilsJSON.jsonToObjeto(String.class, parametros.get("numeroCompra"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            List<InvListaDetalleComprasTO> respues = comprasDetalleService.getListaInvCompraDetalleTO(empresa, periodo, motivo, numeroCompra);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/invCambiarPrecioProducto")
    public RespuestaWebTO invCambiarPrecioProducto(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        List<InvListaProductosCambiarPrecioTO> invListaProductosCambiarPrecioTOs = UtilsJSON.jsonToList(InvListaProductosCambiarPrecioTO.class, map.get("invListaProductosCambiarPrecioTOs"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = productoService.invCambiarPrecioProducto(empresa, usuario, invListaProductosCambiarPrecioTOs, sisInfoTO);
            if (mensajeTO != null && mensajeTO.getMensaje() != null && mensajeTO.getMensaje().charAt(0) == 'T') {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(mensajeTO.getMap());
                resp.setOperacionMensaje(mensajeTO.getMensaje().substring(1));
            } else {
                resp.setExtraInfo(mensajeTO.getListaErrores1());
                resp.setOperacionMensaje(mensajeTO != null && mensajeTO.getMensaje() != null ? mensajeTO.getMensaje().substring(1) : "No se ha guardado la guía de remisión.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosBasicosImportarComprasTransferencias")
    public RespuestaWebTO obtenerDatosBasicosImportarComprasTransferencias(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = ventaGuiaRemisionService.obtenerDatosBasicosImportarComprasTransferencias(map);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("");
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontro guia remisión");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosParaModificacionPrecios")
    public RespuestaWebTO obtenerDatosParaModificacionPrecios(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = productoService.obtenerDatosParaModificacionPrecios(sisInfoTO.getEmpresa());
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron datos para modificar precios.");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            resp.setOperacionMensaje(e.getMessage());
            throw e;
        }
        return resp;
    }

    @RequestMapping("/exportarPlantillaConsumo")
    public @ResponseBody
    String exportarPlantilla(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarPlantillaConsumo(usuarioEmpresaReporteTO, map);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/verificarExistencias")
    public RespuestaWebTO verificarExistencias(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<ComboGenericoTO> productos = UtilsJSON.jsonToList(ComboGenericoTO.class, map.get("productos"));//clave: codigo valor:descripcion de producto
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (productos != null && !productos.isEmpty()) {
                List<InvProducto> productosEncontrados = new ArrayList<>();
                String mensaje = "";
                for (int i = 0; i < productos.size(); i++) {
                    InvProducto producto = productoService.obtenerPorId(sisInfoTO.getEmpresa(), productos.get(i).getClave());
                    if (producto == null) {
                        mensaje = mensaje + "El producto con código: <strong class='pl-2'>" + productos.get(i).getClave() + " </strong> no existe.<br>";
                    } else {
                        if (productos.get(i).getValor() != null) {
                            producto.setProNombre(productos.get(i).getValor());
                        }
                        productosEncontrados.add(producto);
                    }
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(productosEncontrados);
                if (mensaje != null && !mensaje.isEmpty()) {
                    resp.setOperacionMensaje("<strong>Los siguientes productos no existen en la base de datos actual: <br></strong>" + mensaje);
                }
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListReporteInventario")
    public RespuestaWebTO getListReporteInventario(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = productoService.obtenerInventarioReporte(sisInfoTO.getEmpresa());
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron datos para modificar precios.");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            resp.setOperacionMensaje(e.getMessage());
            throw e;
        }
        return resp;
    }

    @RequestMapping("/listarComprasPorProveedorSoloNotaEntrega")
    public RespuestaWebTO listarComprasPorProveedorSoloNotaEntrega(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        String provCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("provCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvConsultaCompraTO> respues = comprasService.listarComprasPorProveedorSoloNotaEntrega(empresa, periodo, motivo, numero, provCodigo);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarDirecciones")
    public RespuestaWebTO listarDirecciones(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cliCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("cliCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvClientesDirecciones> respues = clientesDireccionesService.listarInvClientesDirecciones(empresa, cliCodigo);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron direcciones.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListInvComprasDetalleImb")
    public RespuestaWebTO getListInvComprasDetalleImb(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvComprasDetalleImb> respues = comprasDetalleImbService.getListInvComprasDetalleImb(empresa, periodo, motivo, numero);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarComprasImb")
    public RespuestaWebTO listarComprasImb(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvConsultaCompraTO> respues = comprasService.listarComprasImb(empresa, periodo, motivo, numero);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/cambiarFechaVencimientoVenta")
    public RespuestaWebTO cambiarFechaVencimientoVenta(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Date fecha = UtilsJSON.jsonToObjeto(Date.class, map.get("fecha"));
        InvVentasPK vtaPK = UtilsJSON.jsonToObjeto(InvVentasPK.class, map.get("vtaPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Date fechaVencimiento = ventasService.cambiarFechaVencimientoVenta(vtaPK, fecha, sisInfoTO);
            if (fechaVencimiento != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("La fecha de vencimiento se ha modificado correctamente.");
                resp.setExtraInfo(fechaVencimiento);
            } else {
                resp.setOperacionMensaje("Ocurrió un error al modificar la fecha de vencimiento.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosClienteSegunEmpresa")
    public RespuestaWebTO obtenerDatosClienteSegunEmpresa(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respuesta = clienteService.obtenerDatosClienteSegunEmpresa(map);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron datos para cliente.");
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            resp.setOperacionMensaje(e.getMessage());
            throw e;
        }
        return resp;
    }

    @RequestMapping("/eliminarGuiaRemision")
    public RespuestaWebTO eliminarGuiaRemision(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String resultado = guiaRemisionService.eliminarGuiaRemision(empresa, periodo, numero, sisInfoTO);
            if (resultado.equals("t")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo("La guia de remisión N. " + numero + " se eliminó exitosamente.");
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(resultado);
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerDatosAjusteInventario")
    public RespuestaWebTO obtenerDatosAjusteInventario(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String accion = UtilsJSON.jsonToObjeto(String.class, map.get("accion"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = comprasService.obtenerDatosAjusteInventario(empresa, accion, bodega, hasta, categoria, sisInfoTO.getUsuario());
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/crearAjusteInventario")
    public RespuestaWebTO crearAjusteInventario(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        InvComprasTO invComprasTO = UtilsJSON.jsonToObjeto(InvComprasTO.class, map.get("invComprasTO"));
        InvComboFormaPagoTO formaPago = UtilsJSON.jsonToObjeto(InvComboFormaPagoTO.class, map.get("formaPagoCompra"));
        List<InvComprasDetalleTO> listaInvComprasDetalleTO = UtilsJSON.jsonToList(InvComprasDetalleTO.class, map.get("listInvComprasDetalleTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = comprasService.crearAjusteInventario(
                    empresa,
                    usuario,
                    listaInvComprasDetalleTO,
                    invComprasTO,
                    formaPago,
                    sisInfoTO);
            if (respues != null) {
                String mensaje = UtilsJSON.jsonToObjeto(String.class, respues.get("mensaje"));
                if (mensaje.substring(0, 1).equals("T")) {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
                    resp.setExtraInfo(respues);
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                    resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
                }
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarInvComprasMotivoTOAjusteInv")
    public RespuestaWebTO listarInvComprasMotivoTOAjusteInv(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        boolean soloActivos = UtilsJSON.jsonToObjeto(boolean.class, parametros.get("activos"));
        try {
            List<InvComprasMotivoTO> respues = comprasMotivoService.listarInvComprasMotivoTOAjusteInv(empresa, soloActivos);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/validarIdentificacionProveedor")
    public RespuestaWebTO validarIdentificacionProveedor(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = proveedorService.validarIdentificacionProveedor(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("Error al validar identificación.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/validarIdentificacionTransportista")
    public RespuestaWebTO validarIdentificacionTransportista(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = proveedorService.validarIdentificacionTransportista(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("Error al validar identificación.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarTransportistas")
    public RespuestaWebTO listarTransportistas(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvProveedorTransportistaTO> respues = invProveedorTransportistaService.listarTransportistasTO(empresa, proveedor);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/verificarExistenciaDocumentoInvVenta")
    public RespuestaWebTO verificarExistenciaDocumentoInvVenta(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<ComboGenericoTO> documentos = UtilsJSON.jsonToList(ComboGenericoTO.class, map.get("documentos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (documentos != null && !documentos.isEmpty()) {
                List<InvVentasTO> documentosEncontrados = new ArrayList<>();
                String mensaje = "";
                for (int i = 0; i < documentos.size(); i++) {
                    InvVentasTO ventaTO = ventasService.getInvVentasTO(sisInfoTO.getEmpresa(), "18", documentos.get(i).getClave());
                    if (ventaTO == null) {
                        mensaje = mensaje + "La venta con documento: <strong class='pl-2'>" + documentos.get(i).getClave() + " </strong> no existe.<br>";
                    } else {
                        documentosEncontrados.add(ventaTO);
                    }
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                if (mensaje != null && !mensaje.isEmpty()) {
                    resp.setOperacionMensaje("<strong>Las siguientes ventas no existen en la base de datos actual: <br></strong>" + mensaje);
                } else {
                    //actualizamos
                    String mensajeActualizar = "";
                    String mensajeActualizarError = "";

                    for (int i = 0; i < documentosEncontrados.size(); i++) {
                        InvVentasTO documentosEncontrado = documentosEncontrados.get(i);
                        InvVentasPK pk = new InvVentasPK();
                        pk.setVtaEmpresa(documentosEncontrado.getVtaEmpresa());
                        pk.setVtaMotivo(documentosEncontrado.getVtaMotivo());
                        pk.setVtaNumero(documentosEncontrado.getVtaNumero());
                        pk.setVtaPeriodo(documentosEncontrado.getVtaPeriodo());

                        if (ventasService.actualizarClaveExternaVenta(
                                pk,
                                documentos.get(i).getValor(),
                                sisInfoTO)) {
                            mensajeActualizar = mensajeActualizar + "La venta con documento: <strong class='pl-2'>" + documentos.get(i).getClave() + " </strong> se logró actualizar correctamente.<br>";
                        } else {
                            mensajeActualizarError = mensajeActualizarError + "La venta con documento: <strong class='pl-2'>" + documentos.get(i).getClave() + " </strong> no se logró actualizar la clave externa.<br>";
                        }
                    }

                    if (mensajeActualizarError != null && !mensajeActualizarError.isEmpty()) {
                        resp.setOperacionMensaje("<strong>Las siguientes documentos no se lograron actualizar: <br></strong>" + mensajeActualizarError);
                    } else {
                        resp.setExtraInfo(documentosEncontrados);
                        resp.setOperacionMensaje("<strong>Las siguientes documentos se lograron actualizar: <br></strong>" + mensajeActualizar);
                    }
                }
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/verificarExistenciaDocumentoInvGuiaRemision")
    public RespuestaWebTO verificarExistenciaDocumentoInvGuiaRemision(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<ComboGenericoTO> documentos = UtilsJSON.jsonToList(ComboGenericoTO.class, map.get("documentos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (documentos != null && !documentos.isEmpty()) {
                List<InvGuiaRemisionTO> documentosEncontrados = new ArrayList<>();
                String mensaje = "";
                for (int i = 0; i < documentos.size(); i++) {
                    InvGuiaRemisionTO invGuiaRemisionTO = guiaRemisionService.buscarInvGuiaRemisionTO(sisInfoTO.getEmpresa(), documentos.get(i).getClave());
                    if (invGuiaRemisionTO == null) {
                        mensaje = mensaje + "La guía de remisión con documento: <strong class='pl-2'>" + documentos.get(i).getClave() + " </strong> no existe.<br>";
                    } else {
                        documentosEncontrados.add(invGuiaRemisionTO);
                    }
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                if (mensaje != null && !mensaje.isEmpty()) {
                    resp.setOperacionMensaje("<strong>Las siguientes guías de remisión no existen en la base de datos actual: <br></strong>" + mensaje);
                } else {
                    //actualizamos
                    String mensajeActualizar = "";
                    String mensajeActualizarError = "";

                    for (int i = 0; i < documentosEncontrados.size(); i++) {
                        InvGuiaRemisionTO documentosEncontrado = documentosEncontrados.get(i);
                        InvGuiaRemisionPK pk = new InvGuiaRemisionPK();
                        pk.setGuiaEmpresa(documentosEncontrado.getGuiaEmpresa());
                        pk.setGuiaNumero(documentosEncontrado.getGuiaNumero());
                        pk.setGuiaPeriodo(documentosEncontrado.getGuiaPeriodo());

                        if (guiaRemisionService.actualizarClaveExterna(
                                pk,
                                documentos.get(i).getValor(),
                                sisInfoTO)) {
                            mensajeActualizar = mensajeActualizar + "La guía de remisión con documento: <strong class='pl-2'>" + documentos.get(i).getClave() + " </strong> se logró actualizar correctamente.<br>";
                        } else {
                            mensajeActualizarError = mensajeActualizarError + "La guía de remisión con documento: <strong class='pl-2'>" + documentos.get(i).getClave() + " </strong> no se logró actualizar la clave externa.<br>";
                        }
                    }

                    if (mensajeActualizarError != null && !mensajeActualizarError.isEmpty()) {
                        resp.setOperacionMensaje("<strong>Las siguientes documentos no se lograron actualizar: <br></strong>" + mensajeActualizarError);
                    } else {
                        resp.setExtraInfo(documentosEncontrados);
                        resp.setOperacionMensaje("<strong>Las siguientes documentos se lograron actualizar: <br></strong>" + mensajeActualizar);
                    }
                }
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/verificarExistenciaDocumentoInvCompras")
    public RespuestaWebTO verificarExistenciaDocumentoInvCompras(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<ComboGenericoTO> documentos = UtilsJSON.jsonToList(ComboGenericoTO.class, map.get("documentos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (documentos != null && !documentos.isEmpty()) {
                List<InvComprasTO> documentosEncontrados = new ArrayList<>();
                String mensaje = "";
                for (int i = 0; i < documentos.size(); i++) {
                    InvComprasTO invGuiaRemisionTO = comprasService.getComprasTO(sisInfoTO.getEmpresa(), "03", documentos.get(i).getClave());
                    if (invGuiaRemisionTO == null) {
                        mensaje = mensaje + "La liquidación de compra con documento: <strong class='pl-2'>" + documentos.get(i).getClave() + " </strong> no existe.<br>";
                    } else {
                        documentosEncontrados.add(invGuiaRemisionTO);
                    }
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                if (mensaje != null && !mensaje.isEmpty()) {
                    resp.setOperacionMensaje("<strong>Las siguientes liquidaciones de compra no existen en la base de datos actual: <br></strong>" + mensaje);
                } else {
                    //actualizamos
                    String mensajeActualizar = "";
                    String mensajeActualizarError = "";

                    for (int i = 0; i < documentosEncontrados.size(); i++) {
                        InvComprasTO documentosEncontrado = documentosEncontrados.get(i);
                        InvComprasPK pk = new InvComprasPK();
                        pk.setCompEmpresa(documentosEncontrado.getEmpCodigo());
                        pk.setCompNumero(documentosEncontrado.getCompNumero());
                        pk.setCompMotivo(documentosEncontrado.getCompMotivo());
                        pk.setCompPeriodo(documentosEncontrado.getCompPeriodo());

                        if (comprasService.actualizarClaveExterna(
                                pk,
                                documentos.get(i).getValor(),
                                sisInfoTO)) {
                            mensajeActualizar = mensajeActualizar + "La liquidación de compra con documento: <strong class='pl-2'>" + documentos.get(i).getClave() + " </strong> se logró actualizar correctamente.<br>";
                        } else {
                            mensajeActualizarError = mensajeActualizarError + "La liquidación de compra con documento: <strong class='pl-2'>" + documentos.get(i).getClave() + " </strong> no se logró actualizar la clave externa.<br>";
                        }
                    }

                    if (mensajeActualizarError != null && !mensajeActualizarError.isEmpty()) {
                        resp.setOperacionMensaje("<strong>Las siguientes documentos no se lograron actualizar: <br></strong>" + mensajeActualizarError);
                    } else {
                        resp.setExtraInfo(documentosEncontrados);
                        resp.setOperacionMensaje("<strong>Las siguientes documentos se lograron actualizar: <br></strong>" + mensajeActualizar);
                    }
                }
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarPlantillaCompras")
    public @ResponseBody
    String exportarPlantillaCompras(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarPlantillaCompras(usuarioEmpresaReporteTO, map);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarPlantillaVentas")
    public @ResponseBody
    String exportarPlantillaVentas(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarPlantillaVentas(usuarioEmpresaReporteTO, map);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/exportarPlantillaProveedores")
    public @ResponseBody
    String exportarPlantillaProveedores(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarPlantillaProveedores(usuarioEmpresaReporteTO, map);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/verificarExistenciaProveedores")
    public RespuestaWebTO verificarExistenciaProveedores(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<InvProveedorTO> proveedores = UtilsJSON.jsonToList(InvProveedorTO.class, map.get("proveedores"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (proveedores != null && !proveedores.isEmpty()) {
                List<InvProveedorTO> proveedoresNoEncontrados = new ArrayList<>();
                String mensaje = "";
                for (int i = 0; i < proveedores.size(); i++) {
                    String mensajeValido = proveedorService.verificarDatosProveedorTO(sisInfoTO.getEmpresa(), proveedores.get(i));
                    if (mensajeValido != null && mensajeValido.subSequence(0, 1).equals("F")) {
                        mensaje = mensaje + "El proveedor con número de identificación: <strong class='pl-2'>" + proveedores.get(i).getProvId() + " </strong> " + mensajeValido.substring(1) + "<br>";
                    } else {
                        proveedoresNoEncontrados.add(proveedores.get(i));
                    }
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                if (mensaje != null && !mensaje.isEmpty()) {
                    resp.setOperacionMensaje("<strong>Los siguientes proveedores existen en la base de datos actual: <br></strong>" + mensaje);
                } else {
                    //insertamos
                    String mensajeInsertar = "";
                    String mensajeInsertarError = "";

                    for (int i = 0; i < proveedoresNoEncontrados.size(); i++) {
                        if (proveedorService.insertarInvProveedorTO(proveedoresNoEncontrados.get(i), sisInfoTO, null).charAt(0) == 'T') {
                            mensajeInsertar = mensajeInsertar + "El proveedor con número de identificación: <strong class='pl-2'>" + proveedoresNoEncontrados.get(i).getProvId() + " </strong> se ha guardado correctamente.<br>";
                        } else {
                            mensajeInsertarError = mensajeInsertarError + "El proveedor con número de identificación: <strong class='pl-2'>" + proveedoresNoEncontrados.get(i).getProvId() + " </strong> no se logró guardar.<br>";
                        }
                    }

                    if (mensajeInsertarError != null && !mensajeInsertarError.isEmpty()) {
                        resp.setOperacionMensaje("<strong>Las siguientes documentos no se lograron actualizar: <br></strong>" + mensajeInsertarError);
                    } else {
                        resp.setExtraInfo(proveedoresNoEncontrados);
                        resp.setOperacionMensaje("<strong>Las siguientes documentos se lograron actualizar: <br></strong>" + mensajeInsertar);
                    }
                }
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarINP")
    public RespuestaWebTO listarINP(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        try {
            List<InvGuiaRemisionInp> msje = guiaInpService.getInvGuiaRemisionInp(sisInfoTO.getEmpresa(), cliente, true);
            if (msje != null && !msje.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(msje);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarINP")
    public RespuestaWebTO insertarINP(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvGuiaRemisionInp inp = UtilsJSON.jsonToObjeto(InvGuiaRemisionInp.class, map.get("inp"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = guiaInpService.insertarInvGuiaRemisionInp(inp, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El registro se ha guardado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/actualizarINP")
    public RespuestaWebTO insertactualizarINParINP(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvGuiaRemisionInp inp = UtilsJSON.jsonToObjeto(InvGuiaRemisionInp.class, map.get("inp"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = guiaInpService.modificarInvGuiaRemisionInp(inp, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El registro se ha modificado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarINP")
    public RespuestaWebTO eliminarINP(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvGuiaRemisionInp inp = UtilsJSON.jsonToObjeto(InvGuiaRemisionInp.class, map.get("inp"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = guiaInpService.eliminarInvGuiaRemisionInp(inp, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El registro se ha eliminado correctamente.");
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/convertirXMLResultado")
    public RespuestaWebTO convertirXMLResultado(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String xml = UtilsJSON.jsonToObjeto(String.class, map.get("xml"));
        String clave = UtilsJSON.jsonToObjeto(String.class, map.get("clave"));
        boolean validarCompra = UtilsJSON.jsonToObjeto(boolean.class, map.get("validarCompra"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> rpta = comprasService.convertirXMLResultado(sisInfoTO.getEmpresa(), clave, xml, validarCompra);
            if (rpta != null) {
                String mensaje = UtilsJSON.jsonToObjeto(String.class, rpta.get("mensaje"));
                if (mensaje.substring(0, 1).equals("T")) {
                    ItemComprobanteElectronico itemComprobanteElectronico = UtilsJSON.jsonToObjeto(ItemComprobanteElectronico.class, rpta.get("itemComprobanteElectronico"));
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(rpta);
                } else {
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                }
                resp.setOperacionMensaje(mensaje.substring(1, mensaje.length()));
            } else {
                resp.setOperacionMensaje("Hubo un error al obtener datos de XML");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarInvRutasGuias")
    public RespuestaWebTO insertarInvRutasGuias(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvGuiaRutas invGuiaRutas = UtilsJSON.jsonToObjeto(InvGuiaRutas.class, map.get("invRutasGuias"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = guiaRutasService.insertarInvRutasGuias(invGuiaRutas, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));

            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListarInvGuiaRutas")
    public RespuestaWebTO getListarInvGuiaRutas(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        try {
            List<InvGuiaRutas> respues = guiaRutasService.getListarInvGuiaRutas(empresa);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/modificarInvGuiaRutas")
    public RespuestaWebTO modificarInvRutasGuias(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvGuiaRutas invGuiaRutas = UtilsJSON.jsonToObjeto(InvGuiaRutas.class, map.get("invRutasGuias"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = guiaRutasService.modificarInvGuiaRutas(invGuiaRutas, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarInvGuiaRutas")
    public RespuestaWebTO eliminarInvGuiaRutas(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvGuiaRutas invGuiaRutas = UtilsJSON.jsonToObjeto(InvGuiaRutas.class, map.get("invRutasGuias"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String msje = guiaRutasService.eliminarInvGuiaRutas(invGuiaRutas, sisInfoTO);
            if (msje.substring(0, 1).equalsIgnoreCase("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
                resp.setExtraInfo(true);
            } else {
                resp.setOperacionMensaje(msje.substring(1, msje.length()));
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListarInvClienteContrato")
    public RespuestaWebTO getListarInvClienteContrato(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String cliCodigo = SistemaWebUtil.obtenerFiltroComoString(parametros, "cliCodigo");
        String busqueda = SistemaWebUtil.obtenerFiltroComoString(parametros, "busqueda");
        String nroRegistro = SistemaWebUtil.obtenerFiltroComoString(parametros, "nroRegistro");
        try {
            List<InvClienteContratoTO> respues = clienteContratoService.getListarInvClienteContratoTO(empresa, cliCodigo, busqueda, nroRegistro);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarInvClienteContrato")
    public ResponseEntity insertarInvClienteContrato(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        InvClienteContrato invClienteContrato = UtilsJSON.jsonToObjeto(InvClienteContrato.class, parametros.get("invClienteContrato"));
        List<InvClienteContratoDatosAdjuntos> listadoImagenes = UtilsJSON.jsonToList(InvClienteContratoDatosAdjuntos.class, parametros.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            String respues = clienteContratoService.insertarInvClienteContrato(invClienteContrato, listadoImagenes, sisInfoTO);
            if (respues.substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1));
                resp.setExtraInfo(invClienteContrato);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/modificarInvClienteContrato")
    public ResponseEntity modificarInvClienteContrato(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        InvClienteContrato invClienteContrato = UtilsJSON.jsonToObjeto(InvClienteContrato.class, parametros.get("invClienteContrato"));
        List<InvClienteContratoDatosAdjuntos> listadoImagenes = UtilsJSON.jsonToList(InvClienteContratoDatosAdjuntos.class, parametros.get("listaImagenes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            String respues = clienteContratoService.modificarInvClienteContrato(invClienteContrato, listadoImagenes, sisInfoTO);
            if (respues.substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1));
                resp.setExtraInfo(invClienteContrato);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/eliminarInvClienteContrato")
    public ResponseEntity eliminarInvClienteContrato(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Integer secuencial = UtilsJSON.jsonToObjeto(Integer.class, parametros.get("secuencial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, parametros.get("sisInfoTO"));
        try {
            String respues = clienteContratoService.eliminarInvClienteContrato(secuencial, sisInfoTO);
            if (respues.substring(0, 1).equals("T")) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje(respues.substring(1));
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje(respues.substring(1));
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            throw e;
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @RequestMapping("/obtenerDatosParaContrato")
    public RespuestaWebTO obtenerDatosParaContrato(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = clienteContratoService.obtenerDatosParaContrato(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("Ocurrió un error al obtener datos para cheques postfechados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/listarImagenesDeClienteContrato")
    public RespuestaWebTO listarImagenesDeClienteContrato(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        Integer secuencial = UtilsJSON.jsonToObjeto(Integer.class, parametros.get("secuencial"));
        try {
            List<InvClienteContratoDatosAdjuntos> respues = clienteContratoService.listarImagenesDeClienteContrato(secuencial);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/contabilizarAjusteInventario")
    public RespuestaWebTO contabilizarAjusteInventario(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        InvCompras invComprasNC = UtilsJSON.jsonToObjeto(InvCompras.class, map.get("invComprasNC"));
        InvCompras invComprasNE = UtilsJSON.jsonToObjeto(InvCompras.class, map.get("invComprasNE"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTONC = null;
            MensajeTO mensajeTONE = null;
            Map<String, Object> respuesNC = null;
            Map<String, Object> respuesNE = null;

            Map<String, Object> campos = new HashMap<>();
            //nota de credito
            if (invComprasNC != null) {
                mensajeTONC = new MensajeTO();
                mensajeTONC = comprasService.validarInvContableComprasDetalleTO(
                        invComprasNC.getEmpCodigo(),
                        invComprasNC.getInvComprasPK().getCompPeriodo(),
                        invComprasNC.getInvComprasPK().getCompMotivo(),
                        invComprasNC.getInvComprasPK().getCompNumero(), sisInfoTO);
                InvComprasTO invCompraTO = new InvComprasTO();
                invCompraTO.setEmpCodigo(invComprasNC.getEmpCodigo());
                invCompraTO.setCompPeriodo(invComprasNC.getInvComprasPK().getCompPeriodo());
                invCompraTO.setCompMotivo(invComprasNC.getInvComprasPK().getCompMotivo());
                invCompraTO.setCompNumero(invComprasNC.getInvComprasPK().getCompNumero());
                respuesNC = comprasService.contabilizarComprasTrans(empresa, invCompraTO, mensajeTONC, sisInfoTO);
                if (respuesNC != null) {
                    campos.put("respuestaNC", respuesNC);
                }
            }
            //nota de entrega
            if (invComprasNE != null) {
                mensajeTONE = new MensajeTO();
                mensajeTONE = comprasService.validarInvContableComprasDetalleTO(
                        invComprasNE.getEmpCodigo(),
                        invComprasNE.getInvComprasPK().getCompPeriodo(),
                        invComprasNE.getInvComprasPK().getCompMotivo(),
                        invComprasNE.getInvComprasPK().getCompNumero(), sisInfoTO);
                InvComprasTO invCompraTO = new InvComprasTO();
                invCompraTO.setEmpCodigo(invComprasNE.getEmpCodigo());
                invCompraTO.setCompPeriodo(invComprasNE.getInvComprasPK().getCompPeriodo());
                invCompraTO.setCompMotivo(invComprasNE.getInvComprasPK().getCompMotivo());
                invCompraTO.setCompNumero(invComprasNE.getInvComprasPK().getCompNumero());
                respuesNE = comprasService.contabilizarComprasTrans(empresa, invCompraTO, mensajeTONE, sisInfoTO);
                if (respuesNE != null) {
                    campos.put("respuestaNE", respuesNE);
                }
            }

            if (campos != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(campos);
                resp.setOperacionMensaje("");
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarInvTipoContrato")
    public RespuestaWebTO insertarInvTipoContrato(HttpServletRequest request, @RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvClienteContratoTipo invTipoContrato = UtilsJSON.jsonToObjeto(InvClienteContratoTipo.class, map.get("invTipoContrato"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvClienteContratoTipo tipoContrato = tipoContratoService.insertarInvTipoContrato(invTipoContrato, sisInfoTO);
            if (tipoContrato != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El registro con el Código " + tipoContrato.getInvClienteContratoTipoPK().getCliCodigo() + ", se ha guardado correctamente.");
                resp.setExtraInfo(tipoContrato);
            } else {
                resp.setOperacionMensaje("No se ha guardado el tipo debido a que ya existe.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            resp.setOperacionMensaje(e.getMessage());
        }
        return resp;
    }

    @RequestMapping("/modificarInvTipoContrato")
    public RespuestaWebTO modificarInvTipoContrato(@RequestBody String json) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvClienteContratoTipo tipoContrato = UtilsJSON.jsonToObjeto(InvClienteContratoTipo.class, map.get("invTipoContrato"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvClienteContratoTipo tiContrato = tipoContratoService.modificarInvTipoContrato(tipoContrato, sisInfoTO);
            if (tiContrato != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El grupo empresarial:Código " + tiContrato.getInvClienteContratoTipoPK().getCliCodigo() + ", se ha modificado correctamente.");
                resp.setExtraInfo(tiContrato);
            } else {
                resp.setOperacionMensaje("No se ha modificado registro debido a que ya existe.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/eliminarInvTipoContrato")
    public RespuestaWebTO eliminarInvTipoContrato(@RequestBody String json) throws GeneralException, DataIntegrityViolationException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvClienteContratoTipoPK pk = UtilsJSON.jsonToObjeto(InvClienteContratoTipoPK.class, map.get("pk"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean grupo = tipoContratoService.eliminarInvTipoContrato(pk, sisInfoTO);
            if (grupo) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El tipo contrato :Código " + pk.getCliCodigo() + ", se ha eliminado correctamente.");
                resp.setExtraInfo(grupo);
            } else {
                resp.setOperacionMensaje("No se ha eliminado el tipo contrato.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListarInvTipoContrato")
    public RespuestaWebTO getListarInvTipoContrato(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        try {
            List<InvClienteContratoTipo> respues = tipoContratoService.getListarInvTipoContrato(empresa);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron tipos de contratos.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/verificarExistenciaContratos")
    public RespuestaWebTO verificarExistenciaContratos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<InvClienteContrato> contratos = UtilsJSON.jsonToList(InvClienteContrato.class, map.get("contratos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> resp2 = clienteContratoService.validarExistenciasDatos(contratos, sisInfoTO.getEmpresa());
            ArrayList<String> listaMensajesEnviar = (ArrayList<String>) resp2.get("listaMensajesEnviar");
            List<InvClienteContrato> correctos = (List<InvClienteContrato>) resp2.get("correctos");

            if (contratos != null && !contratos.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                if (correctos.isEmpty()) {
                    if (listaMensajesEnviar != null && !listaMensajesEnviar.isEmpty()) {
                        resp.setOperacionMensaje("Existe errores");
                        resp.setExtraInfo(listaMensajesEnviar);
                    } else {
                        resp.setOperacionMensaje("Existe errores al intentar guardar los contratos");
                        resp.setExtraInfo(null);
                    }
                } else {
                    //insertamos
                    ArrayList<String> mensajeInsertar = new ArrayList<>();
                    for (int i = 0; i < correctos.size(); i++) {
                        //setear entidades
                        correctos.get(i).setUsrEmpresa(sisInfoTO.getEmpresa());
                        correctos.get(i).setUsrCodigo(sisInfoTO.getUsuario());
                        String respuesta = clienteContratoService.insertarInvClienteContrato(correctos.get(i), null, sisInfoTO);
                        if (respuesta != null && respuesta.substring(0, 1).equals("T")) {
                            mensajeInsertar.add("TEl contrato con número:<strong class='pl-2'>" + correctos.get(i).getCliNumeroContrato() + " </strong> se ha guardado correctamente.");
                        } else {
                            mensajeInsertar.add("FEl contrato con número:<strong class='pl-2'>" + correctos.get(i).getCliNumeroContrato() + " </strong> no se logró guardar.");
                        }
                    }
                    if (mensajeInsertar != null && !mensajeInsertar.isEmpty()) {
                        if (listaMensajesEnviar != null && !listaMensajesEnviar.isEmpty()) {
                            listaMensajesEnviar.forEach((item) -> mensajeInsertar.add(item));
                        }
                        resp.setExtraInfo(mensajeInsertar);
                        resp.setOperacionMensaje("");
                    } else {
                        resp.setOperacionMensaje("Hubo un error. comunicarse con el administrador");
                    }
                }
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/guardarImagenesConsumos")
    public RespuestaWebTO guardarImagenesConsumos(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvConsumosPK pk = UtilsJSON.jsonToObjeto(InvConsumosPK.class, map.get("pk"));
        List<InvConsumosDatosAdjuntosWebTO> imagenes = UtilsJSON.jsonToList(InvConsumosDatosAdjuntosWebTO.class, map.get("listImagen"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = consumosService.guardarImagenesConsumos(pk, imagenes, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                if (imagenes == null || imagenes.isEmpty()) {
                    resp.setOperacionMensaje("Las imagenes para consumo se han eliminado correctamente.");
                } else {
                    resp.setOperacionMensaje("Las imagenes para consumo se han guardado correctamente.");
                }
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al guardar imagenes de consumo.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerListadoIMBPendientes")
    public RespuestaWebTO obtenerListadoIMBPendientes(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String empresa = SistemaWebUtil.obtenerFiltroComoString(parametros, "empresa");
        String motivo = SistemaWebUtil.obtenerFiltroComoString(parametros, "motivo");
        String periodo = SistemaWebUtil.obtenerFiltroComoString(parametros, "periodo");
        String proveedor = SistemaWebUtil.obtenerFiltroComoString(parametros, "proveedor");
        String producto = SistemaWebUtil.obtenerFiltroComoString(parametros, "producto");
        String fechaInicio = SistemaWebUtil.obtenerFiltroComoString(parametros, "fechaInicio");
        String fechaFin = SistemaWebUtil.obtenerFiltroComoString(parametros, "fechaFin");
        try {
            List<InvListaConsultaCompraTO> respues = comprasService.obtenerListadoIMBPendientes(empresa, periodo, motivo, proveedor, producto, fechaInicio, fechaFin);
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteClienteContratos")
    public @ResponseBody
    String exportarReporteClienteContratos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvFunListadoClientesTO> listado = UtilsJSON.jsonToList(InvFunListadoClientesTO.class, map.get("listClientes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteClienteContratos(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }
//SALDO POR PAGAR

    @RequestMapping("/obtenerListadoComprasSaldosImportados")
    public RespuestaWebTO obtenerListadoComprasSaldosImportados(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String motivo = SistemaWebUtil.obtenerFiltroComoString(parametros, "motivo");
        String sector = SistemaWebUtil.obtenerFiltroComoString(parametros, "sector");
        String fecha = SistemaWebUtil.obtenerFiltroComoString(parametros, "fecha");
        try {
            List<InvCompras> respues = comprasService.obtenerListadoComprasSaldosImportados(sisInfoTO.getEmpresa(), motivo, sector, fecha
            );
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/verificarExistenciaCompras")
    public RespuestaWebTO verificarExistenciaCompras(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<InvCompras> compras = UtilsJSON.jsonToList(InvCompras.class, map.get("compras"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<String> listaMensajesEnviar = comprasService.validarExistenciasCompras(compras, sisInfoTO);
            if (compras != null && !compras.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                if (listaMensajesEnviar != null && !listaMensajesEnviar.isEmpty()) {
                    resp.setOperacionMensaje("Existe errores");
                    Map<String, Object> mapResultadoEnviar = new HashMap<String, Object>();
                    mapResultadoEnviar.put("mensajeInsertar", listaMensajesEnviar);
                    resp.setExtraInfo(mapResultadoEnviar);
                } else {
                    //insertamos
                    ArrayList<String> mensajeInsertar = new ArrayList<>();
                    ArrayList<Integer> idInsertados = new ArrayList<>();
                    for (int i = 0; i < compras.size(); i++) {
                        //setear entidades
                        compras.get(i).setCompSaldoImportado(true);
                        compras.get(i).setEmpCodigo(sisInfoTO.getEmpresa());
                        compras.get(i).setUsrCodigo(sisInfoTO.getUsuario());
                        MensajeTO respuesta = comprasService.insertarInvComprasTO(compras.get(i), sisInfoTO);
                        if (respuesta != null) {
                            //pk y id de compra
                            if (respuesta.getMensaje().substring(0, 1).equals("T")) {
                                Map<String, Object> mapResultado = respuesta.getMap();
                                idInsertados.add((Integer) mapResultado.get("id"));
                            }
                            mensajeInsertar.add(respuesta.getMensaje().substring(0));
                        } else {
                            mensajeInsertar.add("FLa compra con proveedor:<strong class='pl-2'>" + compras.get(i).getInvProveedor().getProvIdNumero() + " </strong> no se logró guardar.");
                        }
                    }

                    if (mensajeInsertar != null && !mensajeInsertar.isEmpty()) {
                        Map<String, Object> mapResultadoEnviar = new HashMap<String, Object>();
                        mapResultadoEnviar.put("mensajeInsertar", mensajeInsertar);
                        mapResultadoEnviar.put("idInsertados", idInsertados);
                        resp.setExtraInfo(mapResultadoEnviar);
                        resp.setOperacionMensaje("");
                    } else {
                        resp.setOperacionMensaje("Hubo un error. comunicarse con el administrador");
                    }
                }
            } else {
                resp.setOperacionMensaje("No hay lista que importar. comunicarse con el administrador");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarProveedoresRezagadosLote")
    public RespuestaWebTO insertarProveedoresRezagadosLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<InvProveedor> listaEnviar = UtilsJSON.jsonToList(InvProveedor.class, map.get("listaEnviar"));//identificacion y razon social
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String[] listaMensajes = new String[]{};
        ArrayList<String> listaMensajesEnviar = new ArrayList<>();

        try {
            Map<String, Object> respuesta = comprasService.insertarProveedoresRezagadosLote(listaEnviar, sisInfoTO);
            if (respuesta != null) {
                Map<String, Object> respuestaEnviar = new HashMap<>();
                String mensaje = (String) respuesta.get("mensaje");//String  concatenado  resp1|resp2|resp3....  T|FNo se...|FError...|T
                List<InvProveedorTO> listaProveedoresInsertado = (List<InvProveedorTO>) respuesta.get("listaProveedoresInsertado");
                listaMensajes = mensaje.split("\\|");
                for (int i = 0; i < listaMensajes.length; i++) {
                    if (!listaMensajes[i].equals("")) {
                        listaMensajesEnviar.add(listaMensajes[i].substring(0));
                    }
                }
                respuestaEnviar.put("listaMensajesEnviar", listaMensajesEnviar);
                respuestaEnviar.put("listaProveedoresInsertado", listaProveedoresInsertado);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuestaEnviar);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ERROR.getValor());
                resp.setExtraInfo(null);
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }
//SALDO POR COBRAR

    @RequestMapping("/obtenerListadoVentasSaldosImportados")
    public RespuestaWebTO obtenerListadoVentasSaldosImportados(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        String motivo = SistemaWebUtil.obtenerFiltroComoString(parametros, "motivo");
        String sector = SistemaWebUtil.obtenerFiltroComoString(parametros, "sector");
        String fecha = SistemaWebUtil.obtenerFiltroComoString(parametros, "fecha");
        try {
            List<InvVentas> respues = ventasService.obtenerListadoVentasSaldosImportados(sisInfoTO.getEmpresa(), motivo, sector, fecha
            );
            if (respues != null && respues.size() > 0) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/verificarExistenciaVentas")
    public RespuestaWebTO verificarExistenciaVentas(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<InvVentas> ventas = UtilsJSON.jsonToList(InvVentas.class, map.get("ventas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<String> listaMensajesEnviar = ventasService.verificarExistenciaVentas(ventas, sisInfoTO);
            if (ventas != null && !ventas.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                if (listaMensajesEnviar != null && !listaMensajesEnviar.isEmpty()) {
                    resp.setOperacionMensaje("Existe errores");
                    Map<String, Object> mapResultadoEnviar = new HashMap<String, Object>();
                    mapResultadoEnviar.put("mensajeInsertar", listaMensajesEnviar);
                    resp.setExtraInfo(mapResultadoEnviar);
                } else {
                    //insertamos
                    ArrayList<String> mensajeInsertar = new ArrayList<>();
                    ArrayList<Integer> idInsertados = new ArrayList<>();
                    for (int i = 0; i < ventas.size(); i++) {
                        //setear entidades
                        ventas.get(i).setUsrCodigo(sisInfoTO.getUsuario());
                        MensajeTO respuesta = ventasService.insertarInvVentas(ventas.get(i), sisInfoTO);
                        if (respuesta != null) {
                            if (respuesta.getMensaje().substring(0, 1).equals("T")) {
                                //pk y id de compra
                                Map<String, Object> mapResultado = respuesta.getMap();
                                idInsertados.add((Integer) mapResultado.get("id"));
                            }
                            mensajeInsertar.add(respuesta.getMensaje().substring(0));
                        } else {
                            mensajeInsertar.add("FLa venta con cliene:<strong class='pl-2'>" + ventas.get(i).getInvCliente().getCliIdNumero() + " </strong> no se logró guardar.");
                        }
                    }

                    if (mensajeInsertar != null && !mensajeInsertar.isEmpty()) {
                        Map<String, Object> mapResultadoEnviar = new HashMap<String, Object>();
                        mapResultadoEnviar.put("mensajeInsertar", mensajeInsertar);
                        mapResultadoEnviar.put("idInsertados", idInsertados);
                        resp.setExtraInfo(mapResultadoEnviar);
                        resp.setOperacionMensaje("");
                    } else {
                        resp.setOperacionMensaje("Hubo un error. comunicarse con el administrador");
                    }
                }
            } else {
                resp.setOperacionMensaje("No hay lista que importar. comunicarse con el administrador");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/insertarClientesRezagadosLote")
    public RespuestaWebTO insertarClientesRezagadosLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<InvCliente> listaEnviar = UtilsJSON.jsonToList(InvCliente.class, map.get("listaEnviar"));//identificacion y razon social
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String[] listaMensajes = new String[]{};
        ArrayList<String> listaMensajesEnviar = new ArrayList<>();

        try {
            Map<String, Object> respuesta = ventasService.insertarClientesRezagadosLote(listaEnviar, sisInfoTO);
            if (respuesta != null) {
                Map<String, Object> respuestaEnviar = new HashMap<>();
                String mensaje = (String) respuesta.get("mensaje");//String  concatenado  resp1|resp2|resp3....  T|FNo se...|FError...|T
                List<InvCliente> listaClientesInsertado = (List<InvCliente>) respuesta.get("listaClientesInsertado");
                listaMensajes = mensaje.split("\\|");
                for (int i = 0; i < listaMensajes.length; i++) {
                    if (!listaMensajes[i].equals("")) {
                        listaMensajesEnviar.add(listaMensajes[i].substring(0));
                    }
                }
                respuestaEnviar.put("listaMensajesEnviar", listaMensajesEnviar);
                respuestaEnviar.put("listaClientesInsertado", listaClientesInsertado);
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuestaEnviar);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ERROR.getValor());
                resp.setExtraInfo(null);
            }
        } catch (GeneralException ge) {
            resp.setOperacionMensaje(ge.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/verificarExistenciaProductos")
    public RespuestaWebTO verificarExistenciaProductos(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<ImportarProductos> importarProductos = UtilsJSON.jsonToList(ImportarProductos.class, map.get("importarProductos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String empresa = sisInfoTO.getEmpresa();
            Map<String, Object> respuesta = productoService.verificarExistenciaEnProductos(empresa, importarProductos);
            if (respuesta != null) {
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No hay lista que importar. comunicarse con el administrador");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/verificarImportarExcelProveedor")
    public RespuestaWebTO verificarImportarExcelProveedor(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<InvProveedorTO> proveedores = UtilsJSON.jsonToList(InvProveedorTO.class, map.get("proveedores"));
        List<InvProveedorTO> proveedoresIncompletos = UtilsJSON.jsonToList(InvProveedorTO.class, map.get("proveedoresIncompletos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String empresa = sisInfoTO.getEmpresa();
            Map<String, Object> respuesta = proveedorService.verificarExistenciaEnProovedor(empresa, proveedores, proveedoresIncompletos);
            if (respuesta != null) {
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No hay lista que importar. comunicarse con el administrador");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/verificarImportarExcelCliente")
    public RespuestaWebTO verificarImportarExcelCliente(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<InvClienteTO> clientes = UtilsJSON.jsonToList(InvClienteTO.class, map.get("clientes"));
        List<InvClienteTO> clientesIncompletos = UtilsJSON.jsonToList(InvClienteTO.class, map.get("clientesIncompletos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            String empresa = sisInfoTO.getEmpresa();
            Map<String, Object> respuesta = clienteService.verificarExistenciaEnClientes(empresa, clientes, clientesIncompletos);
            if (respuesta != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respuesta);
            } else {
                resp.setOperacionMensaje("No hay lista que importar. comunicarse con el administrador");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/importarProductosEnLote")
    public RespuestaWebTO importarProductosEnLote(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<ImportarProductos> listado = UtilsJSON.jsonToList(ImportarProductos.class, map.get("listado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvProducto> productosInsertados = new ArrayList<>();
            List<String> mensajes = new ArrayList<>();
            Map<String, Object> respuestaEnviar = new HashMap<>();

            for (int i = 0; i < listado.size(); i++) {
                InvProducto producto = listado.get(i).getProducto();
                AfActivos activoFijo = listado.get(i).getActivoFijo();
                Map<String, Object> respuesta = productoService.insertarInvProducto(producto, activoFijo, sisInfoTO);
                if (respuesta != null) {
                    String mensaje = (String) respuesta.get("mensaje");
                    InvProducto productoResp = (InvProducto) respuesta.get("invProducto");
                    if (mensaje.subSequence(0, 1).equals("T")) {
                        productosInsertados.add(productoResp);
                    }
                    mensajes.add(mensaje);
                } else {
                    mensajes.add("FHubo un error al insertar producto: " + producto.getProNombre());
                }
            }
            respuestaEnviar.put("productosInsertados", productosInsertados);
            respuestaEnviar.put("listaMensajesEnviar", mensajes);
            resp.setExtraInfo(respuestaEnviar);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/generarReporteContratoMatricial")
    public RespuestaWebTO generarReporteContratoMatricial(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        RespuestaWebTO resp = new RespuestaWebTO();
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvClienteContratoTO> listadoContratos = UtilsJSON.jsonToList(InvClienteContratoTO.class, map.get("listadoContratos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.generarReporteContratoCliente(usuarioEmpresaReporteTO, listadoContratos);
            resp.setExtraInfo(respuesta);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/imprimirReporteContrato")
    public @ResponseBody
    String imprimirReporteContrato(HttpServletResponse response, @RequestBody Map<String, Object> parametros) {
        String nombreReporte = "reportContratoCliente.jrxml";
        byte[] respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, parametros.get("usuarioEmpresaReporteTO"));
        List<InvClienteContratoTO> listadoContratos = UtilsJSON.jsonToList(InvClienteContratoTO.class, parametros.get("listadoContratos"));
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        try {
            respuesta = reporteInventarioService.generarReporteContratoCliente(usuarioEmpresaReporteTO, listadoContratos);
            return archivoService.generarReportePDF(respuesta, nombreReporte, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarCompraSaldoPagar")
    public RespuestaWebTO modificarCompraSaldoPagar(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        InvCompras compra = UtilsJSON.jsonToObjeto(InvCompras.class, parametros.get("compra"));
        try {
            MensajeTO respuesta = comprasService.modificarInvCompras(compra, sisInfoTO);
            if (respuesta != null) {
                if (respuesta.getMensaje().substring(0, 1).equals("T")) {
                    resp.setOperacionMensaje(respuesta.getMensaje().substring(1));
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(respuesta);
                } else {
                    resp.setOperacionMensaje(respuesta.getMensaje().substring(1));
                }
            } else {
                resp.setOperacionMensaje("FLa compra no se logró modificar.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteContrato")
    public @ResponseBody
    String exportarReporteContrato(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvClienteContratoTO> listadoContratos = UtilsJSON.jsonToList(InvClienteContratoTO.class, map.get("listadoContratos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteContrato(usuarioEmpresaReporteTO, listadoContratos);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarVentaSaldoCobrar")
    public RespuestaWebTO modificarVentaSaldoCobrar(HttpServletRequest request, @RequestBody Map<String, Object> parametros) throws GeneralException {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        SisInfoTO sisInfoTO = SistemaWebUtil.obtenerFiltroComoSisInfoTO(parametros, "sisInfoTO");
        InvVentas venta = UtilsJSON.jsonToObjeto(InvVentas.class, parametros.get("venta"));
        try {
            MensajeTO respuesta = ventasService.modificarInvVentas(venta, sisInfoTO);
            if (respuesta != null) {
                if (respuesta.getMensaje().substring(0, 1).equals("T")) {
                    resp.setOperacionMensaje(respuesta.getMensaje().substring(1));
                    resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                    resp.setExtraInfo(respuesta);
                } else {
                    resp.setOperacionMensaje(respuesta.getMensaje().substring(1));
                }
            } else {
                resp.setOperacionMensaje("FLa venta no se logró modificar.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/anularContableVentas")
    public RespuestaWebTO anularContableVentas(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvVentasPK pk = UtilsJSON.jsonToObjeto(InvVentasPK.class, map.get("pk"));
        ConContablePK pkContable = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("pkContable"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = ventasService.anularContableVentas(pk, pkContable, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setOperacionMensaje("El contable de ventas se ha anulado correctamente.");
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al anular contable de ventas.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListadoComprasDetalleProductoAgrupadoProveedor")
    public RespuestaWebTO getListadoComprasDetalleProductoAgrupadoProveedor(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvComprasDetalleProductoTO> respues = productoService.getListadoComprasDetalleProductoAgrupadoProveedor(empresa, fechaDesde, fechaHasta, sector, piscina, bodega);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListadoComprasDetalleProductoAgrupadoCentroCosto")
    public RespuestaWebTO getListadoComprasDetalleProductoAgrupadoCentroCosto(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvComprasDetalleProductoTO> respues = productoService.getListadoComprasDetalleProductoAgrupadoCentroCosto(empresa, fechaDesde, fechaHasta, sector, piscina, bodega);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListadoComprasDetalleProductoAgrupadoEquipoControl")
    public RespuestaWebTO getListadoComprasDetalleProductoAgrupadoEquipoControl(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvComprasDetalleProductoTO> respues = productoService.getListadoComprasDetalleProductoAgrupadoEquipoControl(empresa, fechaDesde, fechaHasta, sector, piscina, bodega);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListadoVentasDetalleProductoAgrupadoCliente")
    public RespuestaWebTO getListadoVentasDetalleProductoAgrupadoCliente(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvVentasDetalleProductoTO> respues = productoService.getListadoVentasDetalleProductoAgrupadoCliente(empresa, fechaDesde, fechaHasta, sector, piscina, cliente, bodega);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/getListadoVentasDetalleProductoAgrupadoCC")
    public RespuestaWebTO getListadoVentasDetalleProductoAgrupadoCC(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String piscina = UtilsJSON.jsonToObjeto(String.class, map.get("piscina"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvVentasDetalleProductoTO> respues = productoService.getListadoVentasDetalleProductoAgrupadoCC(empresa, fechaDesde, fechaHasta, sector, piscina, cliente, bodega);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    /*CLIENTES*/
    @RequestMapping("/listarReporteComprativoContratos")
    public RespuestaWebTO listarReporteComprativoContratos(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean mostrarInactivo = UtilsJSON.jsonToObjeto(Boolean.class, map.get("mostrarInactivo"));
        boolean diferencia = UtilsJSON.jsonToObjeto(boolean.class, map.get("diferencia"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvClienteContratosTO> respues = clienteService.listarReporteComprativoContratos(empresa, mostrarInactivo, categoria, busqueda, diferencia);
            if (respues != null && !respues.isEmpty()) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resutados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/exportarReporteComprativoClienteContratos")
    public @ResponseBody
    String exportarReporteComprativoClienteContratos(HttpServletResponse response, @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        Map<String, Object> respuesta;
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvClienteContratosTO> listado = UtilsJSON.jsonToList(InvClienteContratosTO.class, map.get("listClientes"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            respuesta = reporteInventarioService.exportarReporteComprativoClienteContratos(usuarioEmpresaReporteTO, listado);
            List<String> listaCabecera = UtilsJSON.jsonToList(String.class, respuesta.get("listaCabecera"));
            List<String> listaCuerpo = UtilsJSON.jsonToList(String.class, respuesta.get("listaCuerpo"));
            archivoService.generarReporteXLSAngular(listaCabecera, listaCuerpo, response);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/guardarImagenesVentas")
    public RespuestaWebTO guardarImagenesVentas(@RequestBody String json
    ) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvVentasPK pk = UtilsJSON.jsonToObjeto(InvVentasPK.class, map.get("pk"));
        List<InvVentasDatosAdjuntos> imagenes = UtilsJSON.jsonToList(InvVentasDatosAdjuntos.class, map.get("listImagen"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            boolean respues = ventasService.guardarImagenesVentas(imagenes, pk, sisInfoTO);
            if (respues) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                if (imagenes == null || imagenes.isEmpty()) {
                    resp.setOperacionMensaje("Las imágenes para la venta se han eliminado correctamente.");
                } else {
                    resp.setOperacionMensaje("Las imágenes para la venta se han guardado correctamente.");
                }
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("Error al guardar imagenes de compra.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerImagenesVenta")
    public RespuestaWebTO obtenerImagenesVenta(@RequestBody String json) throws Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvVentasPK pk = UtilsJSON.jsonToObjeto(InvVentasPK.class, map.get("pk"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<InvVentasDatosAdjuntos> respues = ventasService.listarImagenesDeVenta(pk);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setOperacionMensaje("No se encontraron imágenes de venta.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/obtenerCompra")
    public RespuestaWebTO obtenerCompra(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String documento = UtilsJSON.jsonToObjeto(String.class, map.get("documento"));
        String tipo = UtilsJSON.jsonToObjeto(String.class, map.get("tipo"));
        String provCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("provCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = comprasService.obtenerCompras(sisInfoTO.getEmpresa(), tipo, documento, provCodigo);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }

    @RequestMapping("/consultarCompraPorContablePk")
    public RespuestaWebTO consultarCompraPorContablePk(HttpServletRequest request, @RequestBody String json) throws GeneralException, Exception {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            Map<String, Object> respues = comprasService.consultarCompraPorContablePk(map);
            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }

        return resp;
    }

    @RequestMapping("/consultarVentaSegunContable")
    public RespuestaWebTO consultarVentaSegunContable(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        List<InvListadoCobrosTO> listaInvListadoCobrosTO = new ArrayList<>();
        try {
            Map<String, Object> respues = ventasService.consultarVentaSegunContable(map);
            if (respues != null) {
                InvVentasTO ventaTO = UtilsJSON.jsonToObjeto(InvVentasTO.class, respues.get("ventasTO"));
                listaInvListadoCobrosTO = ventasService.invListadoCobrosTO(sisInfoTO.getEmpresa(), ventaTO.getVtaPeriodo(), ventaTO.getVtaMotivo(), ventaTO.getVtaNumero());
            }

            respues.put("listaInvListadoCobrosTO", listaInvListadoCobrosTO);

            InvVentasTO invVentasTORespuesta = UtilsJSON.jsonToObjeto(InvVentasTO.class, respues.get("ventasTO"));
            if (invVentasTORespuesta != null) {
                InvVentaGuiaRemision guia = ventaGuiaRemisionService.obtenerInvVentaGuiaRemision(sisInfoTO.getEmpresa(), invVentasTORespuesta.getVtaPeriodo(), invVentasTORespuesta.getVtaMotivo(), invVentasTORespuesta.getVtaNumero());
                if (guia != null) {
                    guia.setInvVentas(null);
                }
                respues.put("invVentaGuiaRemision", guia);
            }

            if (respues != null) {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                resp.setExtraInfo(respues);
            } else {
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
                resp.setOperacionMensaje("No se encontraron resultados.");
            }
        } catch (GeneralException e) {
            resp.setOperacionMensaje(e.getMessage());
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }
    
    @RequestMapping("/verificarExistenciasBodega")
    public RespuestaWebTO verificarExistenciasBodega(@RequestBody String json) {
        RespuestaWebTO resp = new RespuestaWebTO();
        resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.ADVERTENCIA.getValor());
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        List<ComboGenericoTO> bodegas = UtilsJSON.jsonToList(ComboGenericoTO.class, map.get("bodega"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            if (bodegas != null && !bodegas.isEmpty()) {
                String mensaje = "";
                for (int i = 0; i < bodegas.size(); i++) {
                    InvBodega invBodega = bodegaService.buscarInvBodega(sisInfoTO.getEmpresa(), bodegas.get(i).getClave());
                    if (invBodega == null) {
                        mensaje = mensaje + "La bodega con código: <strong class='pl-2'>" + bodegas.get(i).getClave() + " </strong> no existe.<br>";
                    } 
                }
                resp.setEstadoOperacion(RespuestaWebTO.EstadoOperacionEnum.EXITO.getValor());
                if (mensaje != null && !mensaje.isEmpty()) {
                    resp.setOperacionMensaje("<strong>Las siguientes bodegas no existen en la base de datos actual: <br></strong>" + mensaje);
                }
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            resp.setOperacionMensaje(e.getMessage());
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return resp;
    }
}
