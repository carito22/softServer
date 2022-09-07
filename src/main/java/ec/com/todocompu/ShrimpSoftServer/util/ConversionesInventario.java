package ec.com.todocompu.ShrimpSoftServer.util;

import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import java.util.Date;

import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxNotificacionAWSTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaReembolsoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxVentaReembolso;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvAdjuntoComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvBodegaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClientesVentasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvCompraCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDetalleImbTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDetalleSeriesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasRecepcionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvGuiaRemisionDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvGuiaRemisionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleComprasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoDAOTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoMarcaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoMedidaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionCajasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionUnidadesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoTipoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciaDetalleSeriesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransportistaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVendedorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentaRetencionesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasComplementoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasDetalleSeriesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasFormaCobroTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvVentasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvAdjuntosCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvBodega;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvBodegaPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteCategoria;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteCategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContrato;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteGrupoEmpresarial;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientePK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientesVentasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalleImb;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasDetalleSeries;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasFormaPago;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasRecepcion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemision;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvNumeracionVarios;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompra;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProducto;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoCategoria;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoCategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMarca;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMarcaPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMedida;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoMedidaPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionCajas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionCajasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionUnidades;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionUnidadesPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoSubcategoria;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoTipo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedor;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorCategoria;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorCategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferencias;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasDetalleSeries;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransportista;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransportistaPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVendedor;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVendedorPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasComplemento;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasDetalleSeries;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasFormaCobro;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.ReporteVentaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscina;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ConversionesInventario {

    public static InvProductoCategoria convertirInvProductoCategoriaTO_InvProductoCategoria(
            InvProductoCategoriaTO invProductoCategoriaTO) {
        InvProductoCategoria invProductoCategoria = new InvProductoCategoria();
        invProductoCategoria.setInvProductoCategoriaPK(new InvProductoCategoriaPK(
                invProductoCategoriaTO.getCatEmpresa(), invProductoCategoriaTO.getCatCodigo()));
        invProductoCategoria.setCatDetalle(invProductoCategoriaTO.getCatDetalle());
        invProductoCategoria.setCatPrecioFijo(invProductoCategoriaTO.isCatPrecioFijo());
        invProductoCategoria.setCatActiva(invProductoCategoriaTO.isCatActiva());
        invProductoCategoria.setUsrEmpresa(invProductoCategoriaTO.getUsrEmpresa());
        invProductoCategoria.setUsrCodigo(invProductoCategoriaTO.getUsrCodigo());
        if (invProductoCategoriaTO.getScatEmpresa() != null && invProductoCategoriaTO.getScatCodigo() != null) {
            invProductoCategoria.setInvProductoSubcategoria(new InvProductoSubcategoria(invProductoCategoriaTO.getScatEmpresa(), invProductoCategoriaTO.getScatCodigo()));
        }
        invProductoCategoria.setUsrFechaInserta(UtilsValidacion.fechaString_Date(invProductoCategoriaTO.getUsrFechaInserta()));
        invProductoCategoria.setCatCuentaVenta(invProductoCategoriaTO.getCatCuentaVenta());
        invProductoCategoria.setCatCuentaCompra(invProductoCategoriaTO.getCatCuentaCompra());
        invProductoCategoria.setCatCuentaCostoVenta(invProductoCategoriaTO.getCatCuentaCostoVenta());
        invProductoCategoria.setCatReplicar(invProductoCategoriaTO.isCatReplicar());
        invProductoCategoria.setCtaEmpresa(invProductoCategoriaTO.getCtaEmpresa());
        return invProductoCategoria;
    }

    public static InvProductoCategoria convertirInvProductoCategoria_InvProductoCategoria(InvProductoCategoria invProductoCategoriaAux) {
        InvProductoCategoria invProductoCategoria = new InvProductoCategoria();
        invProductoCategoria.setInvProductoCategoriaPK(invProductoCategoriaAux.getInvProductoCategoriaPK());
        invProductoCategoria.setCatDetalle(invProductoCategoriaAux.getCatDetalle());
        invProductoCategoria.setCatPrecioFijo(invProductoCategoriaAux.getCatPrecioFijo());
        invProductoCategoria.setCatActiva(invProductoCategoriaAux.getCatActiva());
        invProductoCategoria.setUsrEmpresa(invProductoCategoriaAux.getUsrEmpresa());
        invProductoCategoria.setUsrCodigo(invProductoCategoriaAux.getUsrCodigo());
        invProductoCategoria.setInvProductoSubcategoria(invProductoCategoriaAux.getInvProductoSubcategoria());
        invProductoCategoria.setUsrFechaInserta(invProductoCategoriaAux.getUsrFechaInserta());
        invProductoCategoria.setCatCuentaVenta(invProductoCategoriaAux.getCatCuentaVenta());
        invProductoCategoria.setCatCuentaCompra(invProductoCategoriaAux.getCatCuentaCompra());
        invProductoCategoria.setCatCuentaCostoVenta(invProductoCategoriaAux.getCatCuentaCostoVenta());
        invProductoCategoria.setCatReplicar(invProductoCategoriaAux.isCatReplicar());
        invProductoCategoria.setCtaEmpresa(invProductoCategoriaAux.getCtaEmpresa());
        return invProductoCategoria;
    }

    public static InvProductoMedida convertirInvProductoMedidaTO_InvProductoMedida(InvProductoMedidaTO invProductoMedidaTO) {
        InvProductoMedida invProductoMedida = new InvProductoMedida();
        invProductoMedida.setInvProductoMedidaPK(new InvProductoMedidaPK(invProductoMedidaTO.getMedEmpresa(), invProductoMedidaTO.getMedCodigo()));
        invProductoMedida.setMedDetalle(invProductoMedidaTO.getMedDetalle());
        invProductoMedida.setMedConversionLibras(invProductoMedidaTO.getMedConvLibras());
        invProductoMedida.setMedConversionKilos(invProductoMedidaTO.getMedConvKilos());
        invProductoMedida.setUsrEmpresa(invProductoMedidaTO.getUsrEmpresa());
        invProductoMedida.setUsrCodigo(invProductoMedidaTO.getUsrCodigo());
        invProductoMedida.setUsrFechaInserta(UtilsValidacion.fechaString_Date(invProductoMedidaTO.getUsrFechaInserta()));
        invProductoMedida.setMedReplicar(invProductoMedidaTO.isMedReplicar());
        return invProductoMedida;
    }

    public static InvProductoPresentacionUnidades convertirInvProductoPresentacionUnidadesTO_InvProductoPresentacionUnidades(InvProductoPresentacionUnidadesTO invProductoPresentacionUnidadesTO) {
        InvProductoPresentacionUnidades invProductoPresentacionUnidades = new InvProductoPresentacionUnidades();
        invProductoPresentacionUnidades.setInvProductoPresentacionUnidadesPK(new InvProductoPresentacionUnidadesPK(invProductoPresentacionUnidadesTO.getPresuEmpresa(), invProductoPresentacionUnidadesTO.getPresuCodigo()));
        invProductoPresentacionUnidades.setPresuDetalle(invProductoPresentacionUnidadesTO.getPresuDetalle());
        invProductoPresentacionUnidades.setPresuAbreviado(invProductoPresentacionUnidadesTO.getPresuAbreviado());
        invProductoPresentacionUnidades.setUsrEmpresa(invProductoPresentacionUnidadesTO.getUsrEmpresa());
        invProductoPresentacionUnidades.setUsrCodigo(invProductoPresentacionUnidadesTO.getUsrCodigo());
        invProductoPresentacionUnidades.setUsrFechaInserta(UtilsValidacion.fechaString_Date(invProductoPresentacionUnidadesTO.getUsrFechaInserta()));
        return invProductoPresentacionUnidades;
    }

    public static InvProductoMarca convertirInvProductoMarcaTO_InvProductoMarca(InvProductoMarcaTO invProductoMarcaTO) {
        InvProductoMarca invProductoMarca = new InvProductoMarca();
        invProductoMarca.setInvProductoMarcaPK(new InvProductoMarcaPK(invProductoMarcaTO.getMarEmpresa(), invProductoMarcaTO.getMarCodigo()));
        invProductoMarca.setMarDetalle(invProductoMarcaTO.getMarDetalle());
        invProductoMarca.setMarAbreviado(invProductoMarcaTO.getMarAbreviado());
        invProductoMarca.setUsrEmpresa(invProductoMarcaTO.getUsrEmpresa());
        invProductoMarca.setUsrCodigo(invProductoMarcaTO.getUsrCodigo());
        invProductoMarca.setUsrFechaInserta(UtilsValidacion.fechaString_Date(invProductoMarcaTO.getUsrFechaInserta()));
        invProductoMarca.setMarIncluirEnFacturacion(invProductoMarcaTO.isMarIncluirEnFacturacion());
        invProductoMarca.setMarReplicar(invProductoMarcaTO.isMarReplicar());
        return invProductoMarca;
    }

    public static InvVendedor convertirInvVendedorTO_InvVendedor(InvVendedorTO invVendedorTO) {
        InvVendedor invVendedor = new InvVendedor();
        invVendedor.setInvVendedorPK(new InvVendedorPK(invVendedorTO.getVendEmpresa(), invVendedorTO.getVendCodigo()));
        invVendedor.setVendNombre(invVendedorTO.getVendNombre());
        invVendedor.setUsrCodigo(invVendedorTO.getUsrCodigo());
        invVendedor.setUsrEmpresa(invVendedorTO.getUsrEmpresa());
        invVendedor.setUsrFechaInserta(UtilsValidacion.fechaString_Date(invVendedorTO.getUsrFechaInserta()));
        invVendedor.setVendInactivo(invVendedorTO.getVendInactivo());
        return invVendedor;
    }

    public static InvProductoPresentacionCajas convertirInvProductoPresentacionCajasTO_InvProductoPresentacionCajas(
            InvProductoPresentacionCajasTO invProductoPresentacionCajasTO) {
        InvProductoPresentacionCajas invProductoPresentacionCajas = new InvProductoPresentacionCajas();
        invProductoPresentacionCajas.setInvProductoPresentacionCajasPK(new InvProductoPresentacionCajasPK(
                invProductoPresentacionCajasTO.getPrescEmpresa(), invProductoPresentacionCajasTO.getPrescCodigo()));
        invProductoPresentacionCajas.setPrescDetalle(invProductoPresentacionCajasTO.getPrescDetalle());
        invProductoPresentacionCajas.setPrescAbreviado(invProductoPresentacionCajasTO.getPrescAbreviado());
        invProductoPresentacionCajas.setUsrEmpresa(invProductoPresentacionCajasTO.getUsrEmpresa());
        invProductoPresentacionCajas.setUsrCodigo(invProductoPresentacionCajasTO.getUsrCodigo());
        invProductoPresentacionCajas.setUsrFechaInserta(
                UtilsValidacion.fechaString_Date(invProductoPresentacionCajasTO.getUsrFechaInserta()));
        return invProductoPresentacionCajas;
    }

    public static InvProductoTipo convertirInvProductoTipoTO_InvProductoTipo(InvProductoTipoTO invProductoTipoTO) {
        InvProductoTipo invProductoTipo = new InvProductoTipo();
        invProductoTipo.setInvProductoTipoPK(new InvProductoTipoPK(invProductoTipoTO.getTipEmpresa(), invProductoTipoTO.getTipCodigo()));
        invProductoTipo.setTipDetalle(invProductoTipoTO.getTipDetalle());
        invProductoTipo.setTipTipo(invProductoTipoTO.getTipTipo());
        invProductoTipo.setTipActivo(invProductoTipoTO.getTipActivo());
        invProductoTipo.setUsrEmpresa(invProductoTipoTO.getUsrEmpresa());
        invProductoTipo.setUsrCodigo(invProductoTipoTO.getUsrCodigo());
        invProductoTipo.setUsrFechaInserta(UtilsValidacion.fechaString_Date(invProductoTipoTO.getUsrFechaInserta()));
        invProductoTipo.setCtaCodigo(invProductoTipoTO.getCtaCodigo());
        invProductoTipo.setCtaEmpresa(invProductoTipoTO.getCtaEmpresa());
        invProductoTipo.setTipReplicar(invProductoTipoTO.isTipReplicar());
        return invProductoTipo;
    }

    public static InvProducto convertirInvProductoTO_InvProducto(InvProductoTO invProductoTO) {

        InvProducto invProducto = new InvProducto();
        invProducto.setInvProductoPK(
                new InvProductoPK(invProductoTO.getProEmpresa(), invProductoTO.getProCodigoPrincipal()));
        invProducto.setProCodigoAlterno(invProductoTO.getProCodigoAlterno());
        invProducto.setProCodigoBarra(invProductoTO.getProCodigoBarra());
        invProducto.setProCodigoBarra2(invProductoTO.getProCodigoBarra2());
        invProducto.setProCodigoBarra3(invProductoTO.getProCodigoBarra3());
        invProducto.setProCodigoBarra4(invProductoTO.getProCodigoBarra4());
        invProducto.setProCodigoBarra5(invProductoTO.getProCodigoBarra5());// 8
        invProducto.setProEmpaque(invProductoTO.getProEmpaque());
        invProducto.setProNombre(invProductoTO.getProNombre());

        invProducto.setProDetalle(invProductoTO.getProDetalle());

        invProducto.setProCantidad1(invProductoTO.getProCantidad1() != null ? invProductoTO.getProCantidad1() : BigDecimal.ZERO);
        invProducto.setProCantidad2(invProductoTO.getProCantidad2() != null ? invProductoTO.getProCantidad2() : BigDecimal.ZERO);
        invProducto.setProCantidad3(invProductoTO.getProCantidad3() != null ? invProductoTO.getProCantidad3() : BigDecimal.ZERO);
        invProducto.setProCantidad4(invProductoTO.getProCantidad4() != null ? invProductoTO.getProCantidad4() : BigDecimal.ZERO);
        invProducto.setProCantidad5(invProductoTO.getProCantidad5() != null ? invProductoTO.getProCantidad5() : BigDecimal.ZERO);
        invProducto.setProCantidad6(invProductoTO.getProCantidad6() != null ? invProductoTO.getProCantidad6() : BigDecimal.ZERO);
        invProducto.setProCantidad7(invProductoTO.getProCantidad7() != null ? invProductoTO.getProCantidad7() : BigDecimal.ZERO);
        invProducto.setProCantidad8(invProductoTO.getProCantidad8() != null ? invProductoTO.getProCantidad8() : BigDecimal.ZERO);
        invProducto.setProCantidad9(invProductoTO.getProCantidad9() != null ? invProductoTO.getProCantidad9() : BigDecimal.ZERO);
        invProducto.setProCantidad10(invProductoTO.getProCantidad10() != null ? invProductoTO.getProCantidad10() : BigDecimal.ZERO);
        invProducto.setProCantidad11(invProductoTO.getProCantidad11() != null ? invProductoTO.getProCantidad11() : BigDecimal.ZERO);
        invProducto.setProCantidad12(invProductoTO.getProCantidad12() != null ? invProductoTO.getProCantidad12() : BigDecimal.ZERO);
        invProducto.setProCantidad13(invProductoTO.getProCantidad13() != null ? invProductoTO.getProCantidad13() : BigDecimal.ZERO);
        invProducto.setProCantidad14(invProductoTO.getProCantidad14() != null ? invProductoTO.getProCantidad14() : BigDecimal.ZERO);
        invProducto.setProCantidad15(invProductoTO.getProCantidad15() != null ? invProductoTO.getProCantidad15() : BigDecimal.ZERO);
        invProducto.setProCantidad16(invProductoTO.getProCantidad16() != null ? invProductoTO.getProCantidad16() : BigDecimal.ZERO);

        invProducto.setProPrecio1(invProductoTO.getProPrecio1() != null ? invProductoTO.getProPrecio1() : BigDecimal.ZERO);
        invProducto.setProPrecio2(invProductoTO.getProPrecio2() != null ? invProductoTO.getProPrecio2() : BigDecimal.ZERO);
        invProducto.setProPrecio3(invProductoTO.getProPrecio3() != null ? invProductoTO.getProPrecio3() : BigDecimal.ZERO);
        invProducto.setProPrecio4(invProductoTO.getProPrecio4() != null ? invProductoTO.getProPrecio4() : BigDecimal.ZERO);
        invProducto.setProPrecio5(invProductoTO.getProPrecio5() != null ? invProductoTO.getProPrecio5() : BigDecimal.ZERO);
        invProducto.setProPrecio6(invProductoTO.getProPrecio6() != null ? invProductoTO.getProPrecio6() : BigDecimal.ZERO);
        invProducto.setProPrecio7(invProductoTO.getProPrecio7() != null ? invProductoTO.getProPrecio7() : BigDecimal.ZERO);
        invProducto.setProPrecio8(invProductoTO.getProPrecio8() != null ? invProductoTO.getProPrecio8() : BigDecimal.ZERO);
        invProducto.setProPrecio9(invProductoTO.getProPrecio9() != null ? invProductoTO.getProPrecio9() : BigDecimal.ZERO);
        invProducto.setProPrecio10(invProductoTO.getProPrecio10() != null ? invProductoTO.getProPrecio10() : BigDecimal.ZERO);
        invProducto.setProPrecio11(invProductoTO.getProPrecio11() != null ? invProductoTO.getProPrecio11() : BigDecimal.ZERO);
        invProducto.setProPrecio12(invProductoTO.getProPrecio12() != null ? invProductoTO.getProPrecio12() : BigDecimal.ZERO);
        invProducto.setProPrecio13(invProductoTO.getProPrecio13() != null ? invProductoTO.getProPrecio13() : BigDecimal.ZERO);
        invProducto.setProPrecio14(invProductoTO.getProPrecio14() != null ? invProductoTO.getProPrecio14() : BigDecimal.ZERO);
        invProducto.setProPrecio15(invProductoTO.getProPrecio15() != null ? invProductoTO.getProPrecio15() : BigDecimal.ZERO);
        invProducto.setProPrecio16(invProductoTO.getProPrecio16() != null ? invProductoTO.getProPrecio16() : BigDecimal.ZERO);

        invProducto.setProDescuento1(invProductoTO.getProDescuento1() != null ? invProductoTO.getProDescuento1() : BigDecimal.ZERO);
        invProducto.setProDescuento2(invProductoTO.getProDescuento2() != null ? invProductoTO.getProDescuento2() : BigDecimal.ZERO);
        invProducto.setProDescuento3(invProductoTO.getProDescuento3() != null ? invProductoTO.getProDescuento3() : BigDecimal.ZERO);
        invProducto.setProDescuento4(invProductoTO.getProDescuento4() != null ? invProductoTO.getProDescuento4() : BigDecimal.ZERO);
        invProducto.setProDescuento5(invProductoTO.getProDescuento5() != null ? invProductoTO.getProDescuento5() : BigDecimal.ZERO);
        invProducto.setProDescuento6(invProductoTO.getProDescuento6() != null ? invProductoTO.getProDescuento6() : BigDecimal.ZERO);
        invProducto.setProDescuento7(invProductoTO.getProDescuento7() != null ? invProductoTO.getProDescuento7() : BigDecimal.ZERO);
        invProducto.setProDescuento8(invProductoTO.getProDescuento8() != null ? invProductoTO.getProDescuento8() : BigDecimal.ZERO);
        invProducto.setProDescuento9(invProductoTO.getProDescuento9() != null ? invProductoTO.getProDescuento9() : BigDecimal.ZERO);
        invProducto.setProDescuento10(invProductoTO.getProDescuento10() != null ? invProductoTO.getProDescuento10() : BigDecimal.ZERO);
        invProducto.setProDescuento11(invProductoTO.getProDescuento11() != null ? invProductoTO.getProDescuento11() : BigDecimal.ZERO);
        invProducto.setProDescuento12(invProductoTO.getProDescuento12() != null ? invProductoTO.getProDescuento12() : BigDecimal.ZERO);
        invProducto.setProDescuento13(invProductoTO.getProDescuento13() != null ? invProductoTO.getProDescuento13() : BigDecimal.ZERO);
        invProducto.setProDescuento14(invProductoTO.getProDescuento14() != null ? invProductoTO.getProDescuento14() : BigDecimal.ZERO);
        invProducto.setProDescuento15(invProductoTO.getProDescuento15() != null ? invProductoTO.getProDescuento15() : BigDecimal.ZERO);
        invProducto.setProDescuento16(invProductoTO.getProDescuento16() != null ? invProductoTO.getProDescuento16() : BigDecimal.ZERO);

        invProducto.setProMargenUtilidad1(invProductoTO.getProMargenUtilidad1() != null ? invProductoTO.getProMargenUtilidad1() : BigDecimal.ZERO);
        invProducto.setProMargenUtilidad2(invProductoTO.getProMargenUtilidad2() != null ? invProductoTO.getProMargenUtilidad2() : BigDecimal.ZERO);
        invProducto.setProMargenUtilidad3(invProductoTO.getProMargenUtilidad3() != null ? invProductoTO.getProMargenUtilidad3() : BigDecimal.ZERO);
        invProducto.setProMargenUtilidad4(invProductoTO.getProMargenUtilidad4() != null ? invProductoTO.getProMargenUtilidad4() : BigDecimal.ZERO);
        invProducto.setProMargenUtilidad5(invProductoTO.getProMargenUtilidad5() != null ? invProductoTO.getProMargenUtilidad5() : BigDecimal.ZERO);
        invProducto.setProMargenUtilidad6(invProductoTO.getProMargenUtilidad6() != null ? invProductoTO.getProMargenUtilidad6() : BigDecimal.ZERO);
        invProducto.setProMargenUtilidad7(invProductoTO.getProMargenUtilidad7() != null ? invProductoTO.getProMargenUtilidad7() : BigDecimal.ZERO);
        invProducto.setProMargenUtilidad8(invProductoTO.getProMargenUtilidad8() != null ? invProductoTO.getProMargenUtilidad8() : BigDecimal.ZERO);
        invProducto.setProMargenUtilidad9(invProductoTO.getProMargenUtilidad9() != null ? invProductoTO.getProMargenUtilidad9() : BigDecimal.ZERO);
        invProducto.setProMargenUtilidad10(invProductoTO.getProMargenUtilidad10() != null ? invProductoTO.getProMargenUtilidad10() : BigDecimal.ZERO);
        invProducto.setProMargenUtilidad11(invProductoTO.getProMargenUtilidad11() != null ? invProductoTO.getProMargenUtilidad11() : BigDecimal.ZERO);
        invProducto.setProMargenUtilidad12(invProductoTO.getProMargenUtilidad12() != null ? invProductoTO.getProMargenUtilidad12() : BigDecimal.ZERO);
        invProducto.setProMargenUtilidad13(invProductoTO.getProMargenUtilidad13() != null ? invProductoTO.getProMargenUtilidad13() : BigDecimal.ZERO);
        invProducto.setProMargenUtilidad14(invProductoTO.getProMargenUtilidad14() != null ? invProductoTO.getProMargenUtilidad14() : BigDecimal.ZERO);
        invProducto.setProMargenUtilidad15(invProductoTO.getProMargenUtilidad15() != null ? invProductoTO.getProMargenUtilidad15() : BigDecimal.ZERO);
        invProducto.setProMargenUtilidad16(invProductoTO.getProMargenUtilidad16() != null ? invProductoTO.getProMargenUtilidad16() : BigDecimal.ZERO);

        invProducto.setProCostoReferencial1(invProductoTO.getProCostoReferencial1() != null ? invProductoTO.getProCostoReferencial1() : BigDecimal.ZERO);
        invProducto.setProCostoReferencial2(invProductoTO.getProCostoReferencial2() != null ? invProductoTO.getProCostoReferencial2() : BigDecimal.ZERO);
        invProducto.setProCostoReferencial3(invProductoTO.getProCostoReferencial3() != null ? invProductoTO.getProCostoReferencial3() : BigDecimal.ZERO);
        invProducto.setProCostoReferencial4(invProductoTO.getProCostoReferencial4() != null ? invProductoTO.getProCostoReferencial4() : BigDecimal.ZERO);
        invProducto.setProCostoReferencial5(invProductoTO.getProCostoReferencial5() != null ? invProductoTO.getProCostoReferencial5() : BigDecimal.ZERO);

        invProducto.setProMinimo(invProductoTO.getProMinimo());
        invProducto.setProMaximo(invProductoTO.getProMaximo());
        invProducto.setProIva(invProductoTO.getProIva());
        invProducto.setProCreditoTributario(invProductoTO.getProCreditoTributario() == null || invProductoTO.getProCreditoTributario().equals("")
                ? "NO APLICA" : invProductoTO.getProCreditoTributario());
        invProducto.setProExigirSerie(invProductoTO.isProExigirSerie());
        invProducto.setProInactivo(invProductoTO.getProInactivo());
        invProducto.setProCuentaEmpresa(invProductoTO.getProEmpresa());
        invProducto.setProCuentaInventario(invProductoTO.getProCuentaInventario());
        invProducto.setProCuentaGasto(invProductoTO.getProCuentaGasto());
        invProducto.setProCuentaTransferenciaIpp(invProductoTO.getProCuentaTransferenciaIpp());

        invProducto.setProCuentaVenta(invProductoTO.getProCuentaVenta());
        invProducto.setConCodigo(invProductoTO.getConCodigo());
        invProducto.setSusCodigo(invProductoTO.getSusCodigo());
        // invProducto.setProFactorConversion(invProductoTO.getProFactorConversion());
        invProducto.setProFactorCajaSacoBulto(invProductoTO.getProFactorCajabulto());
        invProducto.setUsrEmpresa(invProductoTO.getUsrEmpresa());
        invProducto.setUsrCodigo(invProductoTO.getUsrInsertaProducto());
        invProducto.setUsrFechaInserta(UtilsValidacion.fechaString_Date(invProductoTO.getUsrFechaInsertaProducto()));
        invProducto.setProEspecificaciones(invProductoTO.getProEspecificaciones());
        invProducto.setProReplicar(invProductoTO.isProReplicar());
        invProducto.setProEcommerce(invProductoTO.isProEcommerce());
        invProducto.setProIncluirWebshop(invProductoTO.getProIncluirWebshop() == null ? -1 : invProductoTO.getProIncluirWebshop());
        invProducto.setProIce(invProductoTO.getProIce());
        invProducto.setProCuentaVentaExterior(invProductoTO.getProCuentaVentaExterior());
        return invProducto;
    }

    public static InvProducto convertirInvProducto_InvProducto(InvProducto invProductoOrigen) {

        InvProducto invProducto = new InvProducto();
        invProducto.setInvProductoPK(new InvProductoPK(invProductoOrigen.getInvProductoPK().getProEmpresa(),
                invProductoOrigen.getInvProductoPK().getProCodigoPrincipal()));
        invProducto.setProCodigoAlterno(invProductoOrigen.getProCodigoAlterno());
        invProducto.setProCodigoBarra(invProductoOrigen.getProCodigoBarra());
        invProducto.setProCodigoBarra2(invProductoOrigen.getProCodigoBarra2());
        invProducto.setProCodigoBarra3(invProductoOrigen.getProCodigoBarra3());
        invProducto.setProCodigoBarra4(invProductoOrigen.getProCodigoBarra4());
        invProducto.setProCodigoBarra5(invProductoOrigen.getProCodigoBarra5());

        invProducto.setProEmpaque(invProductoOrigen.getProEmpaque());
        invProducto.setProNombre(invProductoOrigen.getProNombre());
        invProducto.setProDetalle(invProductoOrigen.getProDetalle());
        invProducto.setProCantidad1(invProductoOrigen.getProCantidad1());
        invProducto.setProCantidad2(invProductoOrigen.getProCantidad2());
        invProducto.setProCantidad3(invProductoOrigen.getProCantidad3());
        invProducto.setProCantidad4(invProductoOrigen.getProCantidad4());
        invProducto.setProCantidad5(invProductoOrigen.getProCantidad5());
        invProducto.setProFactorCajaSacoBulto(invProductoOrigen.getProFactorCajaSacoBulto());
        invProducto.setProPrecio1(invProductoOrigen.getProPrecio1());
        invProducto.setProPrecio2(invProductoOrigen.getProPrecio2());
        invProducto.setProPrecio3(invProductoOrigen.getProPrecio3());
        invProducto.setProPrecio4(invProductoOrigen.getProPrecio4());
        invProducto.setProPrecio5(invProductoOrigen.getProPrecio5());
        invProducto.setProDescuento1(invProductoOrigen.getProDescuento1());
        invProducto.setProDescuento2(invProductoOrigen.getProDescuento2());
        invProducto.setProDescuento3(invProductoOrigen.getProDescuento3());
        invProducto.setProDescuento4(invProductoOrigen.getProDescuento4());
        invProducto.setProDescuento5(invProductoOrigen.getProDescuento5());
        invProducto.setProMargenUtilidad1(invProductoOrigen.getProMargenUtilidad1());
        invProducto.setProMargenUtilidad2(invProductoOrigen.getProMargenUtilidad2());
        invProducto.setProMargenUtilidad3(invProductoOrigen.getProMargenUtilidad3());
        invProducto.setProMargenUtilidad4(invProductoOrigen.getProMargenUtilidad4());
        invProducto.setProMargenUtilidad5(invProductoOrigen.getProMargenUtilidad5());

        invProducto.setProMinimo(invProductoOrigen.getProMinimo());
        invProducto.setProMaximo(invProductoOrigen.getProMaximo());
        invProducto.setProIva(invProductoOrigen.getProIva());
        invProducto.setProCreditoTributario(invProductoOrigen.getProCreditoTributario());
        invProducto.setProExigirSerie(invProductoOrigen.isProExigirSerie());
        invProducto.setProInactivo(invProductoOrigen.getProInactivo());

        invProducto.setProCuentaEmpresa(invProductoOrigen.getProCuentaEmpresa());
        invProducto.setProCuentaInventario(invProductoOrigen.getProCuentaInventario());
        invProducto.setProCuentaGasto(invProductoOrigen.getProCuentaGasto());
        invProducto.setProCuentaVenta(invProductoOrigen.getProCuentaVenta());
        //
        invProducto.setInvProductoCategoria(invProductoOrigen.getInvProductoCategoria());
        invProducto.setInvProductoPresentacionUnidades(invProductoOrigen.getInvProductoPresentacionUnidades());
        invProducto.setInvProductoPresentacionCajas(invProductoOrigen.getInvProductoPresentacionCajas());
        invProducto.setInvProductoTipo(invProductoOrigen.getInvProductoTipo());
        invProducto.setInvProductoMedida(invProductoOrigen.getInvProductoMedida());
        invProducto.setInvProductoMarca(invProductoOrigen.getInvProductoMarca());
        invProducto.setSusCodigo(invProductoOrigen.getSusCodigo());
        invProducto.setConCodigo(invProductoOrigen.getConCodigo());
        // invProducto.setInvProductoMedida2(invProductoOrigen.getInvProductoMedida2());
        // invProducto.setProFactorConversion(invProductoOrigen.getProFactorConversion());

        invProducto.setUsrEmpresa(invProductoOrigen.getUsrEmpresa());
        invProducto.setUsrCodigo(invProductoOrigen.getUsrCodigo());
        invProducto.setUsrFechaInserta(invProductoOrigen.getUsrFechaInserta());
        invProducto.setProReplicar(invProductoOrigen.isProReplicar());
        invProducto.setProEcommerce(invProductoOrigen.isProEcommerce());
        invProducto.setProIncluirWebshop(invProductoOrigen.getProIncluirWebshop() == null ? -1 : invProductoOrigen.getProIncluirWebshop());
        invProducto.setProIce(invProductoOrigen.getProIce());
        invProducto.setProCuentaVentaExterior(invProductoOrigen.getProCuentaVentaExterior());
        return invProducto;
    }

    public static InvProductoDAOTO convertirInvProducto_InvProductoDAOTO(InvProducto invProducto) {
        InvProductoDAOTO invProductoDAOTO = null;

        if (invProducto != null) {
            invProductoDAOTO = new InvProductoDAOTO();
            invProductoDAOTO.setProEmpresa(invProducto.getInvProductoPK().getProEmpresa());
            invProductoDAOTO.setProCodigoPrincipal(invProducto.getInvProductoPK().getProCodigoPrincipal());
            invProductoDAOTO.setProCodigoAlterno(invProducto.getProCodigoAlterno());
            invProductoDAOTO.setProCodigoBarra(invProducto.getProCodigoBarra());
            invProductoDAOTO.setProCodigoBarra2(invProducto.getProCodigoBarra2());
            invProductoDAOTO.setProCodigoBarra3(invProducto.getProCodigoBarra3());
            invProductoDAOTO.setProCodigoBarra4(invProducto.getProCodigoBarra4());
            invProductoDAOTO.setProCodigoBarra5(invProducto.getProCodigoBarra5());

            invProductoDAOTO.setProNombre(invProducto.getProNombre());
            invProductoDAOTO.setProDetalle(invProducto.getProDetalle());

            invProductoDAOTO.setProCantidad1(invProducto.getProCantidad1());
            invProductoDAOTO.setProCantidad2(invProducto.getProCantidad2());
            invProductoDAOTO.setProCantidad3(invProducto.getProCantidad3());
            invProductoDAOTO.setProCantidad4(invProducto.getProCantidad4());
            invProductoDAOTO.setProCantidad5(invProducto.getProCantidad5());
            invProductoDAOTO.setProPrecio1(invProducto.getProPrecio1());
            invProductoDAOTO.setProPrecio2(invProducto.getProPrecio2());
            invProductoDAOTO.setProPrecio3(invProducto.getProPrecio3());
            invProductoDAOTO.setProPrecio4(invProducto.getProPrecio4());
            invProductoDAOTO.setProPrecio5(invProducto.getProPrecio5());
            invProductoDAOTO.setProDescuento1(invProducto.getProDescuento1());
            invProductoDAOTO.setProDescuento2(invProducto.getProDescuento2());
            invProductoDAOTO.setProDescuento3(invProducto.getProDescuento3());
            invProductoDAOTO.setProDescuento4(invProducto.getProDescuento4());
            invProductoDAOTO.setProDescuento5(invProducto.getProDescuento5());
            invProductoDAOTO.setProMargenUtilidad1(invProducto.getProMargenUtilidad1());
            invProductoDAOTO.setProMargenUtilidad2(invProducto.getProMargenUtilidad2());
            invProductoDAOTO.setProMargenUtilidad3(invProducto.getProMargenUtilidad3());
            invProductoDAOTO.setProMargenUtilidad4(invProducto.getProMargenUtilidad4());
            invProductoDAOTO.setProMargenUtilidad5(invProducto.getProMargenUtilidad5());

            invProductoDAOTO.setProMinimo(invProducto.getProMinimo());
            invProductoDAOTO.setProMaximo(invProducto.getProMaximo());
            invProductoDAOTO.setProIva(invProducto.getProIva());
            invProductoDAOTO.setProCreditoTributario(invProducto.getProCreditoTributario());
            invProductoDAOTO.setProExigirSerie(invProducto.isProExigirSerie());
            invProductoDAOTO.setProInactivo(invProducto.getProInactivo());

            invProductoDAOTO.setProCuentaInventario(invProducto.getProCuentaInventario());
            invProductoDAOTO.setProCuentaGasto(invProducto.getProCuentaGasto());
            invProductoDAOTO.setProCuentaVenta(invProducto.getProCuentaVenta());

            // categoria
            invProductoDAOTO
                    .setCatCodigo(invProducto.getInvProductoCategoria().getInvProductoCategoriaPK().getCatCodigo());
            invProductoDAOTO.setCatDetalle(invProducto.getInvProductoCategoria().getCatDetalle());
            invProductoDAOTO.setCatPrecioFijo(invProducto.getInvProductoCategoria().getCatPrecioFijo());
            invProductoDAOTO.setCatActiva(invProducto.getInvProductoCategoria().getCatActiva());
            // marca
            invProductoDAOTO.setMarCodigo(invProducto.getInvProductoMarca().getInvProductoMarcaPK().getMarCodigo());
            invProductoDAOTO.setMarDetalle(invProducto.getInvProductoMarca().getMarDetalle());
            invProductoDAOTO.setMarAbreviado(invProducto.getInvProductoMarca().getMarAbreviado());
            // presentacion unidad
            invProductoDAOTO.setPresuCodigo(invProducto.getInvProductoPresentacionUnidades()
                    .getInvProductoPresentacionUnidadesPK().getPresuCodigo());
            invProductoDAOTO.setPresuDetalle(invProducto.getInvProductoPresentacionUnidades().getPresuDetalle());
            invProductoDAOTO.setPresuAbreviado(invProducto.getInvProductoPresentacionUnidades().getPresuAbreviado());
            // presentacion caja
            invProductoDAOTO.setPrescCodigo(
                    invProducto.getInvProductoPresentacionCajas().getInvProductoPresentacionCajasPK().getPrescCodigo());
            invProductoDAOTO.setPrescDetalle(invProducto.getInvProductoPresentacionCajas().getPrescDetalle());
            invProductoDAOTO.setPrescAbreviado(invProducto.getInvProductoPresentacionCajas().getPrescAbreviado());
            // tipo producto
            invProductoDAOTO.setTipCodigo(invProducto.getInvProductoTipo().getInvProductoTipoPK().getTipCodigo());
            invProductoDAOTO.setTipTipo(invProducto.getInvProductoTipo().getTipTipo());
            invProductoDAOTO.setTipDetalle(invProducto.getInvProductoTipo().getTipDetalle());
            invProductoDAOTO.setTipActivo(invProducto.getInvProductoTipo().getTipActivo());
            // medida
            invProductoDAOTO.setMedCodigo(invProducto.getInvProductoMedida().getInvProductoMedidaPK().getMedCodigo());
            invProductoDAOTO.setMedDetalle(invProducto.getInvProductoMedida().getMedDetalle());
            //
            invProductoDAOTO.setUsrEmpresa(invProducto.getUsrEmpresa());
            invProductoDAOTO.setUsrCodigo(invProducto.getUsrCodigo());
            invProductoDAOTO.setUsrFechaInserta(UtilsValidacion.fecha(invProducto.getUsrFechaInserta(), "yyyy-MM-dd"));
            // CANTIDADCAJA, EMPAQUE, PRESENTACIONUNIDAD, PRESENTACIONCAJA
            invProductoDAOTO.setProFactorCajaSacoBulto(invProducto.getProFactorCajaSacoBulto());
            invProductoDAOTO.setProEmpaque(invProducto.getProEmpaque());
            // codigo sustento y conecpto
            invProductoDAOTO.setConCodigo(invProducto.getConCodigo());
            invProductoDAOTO.setSusCodigo(invProducto.getSusCodigo());
        }
        return invProductoDAOTO;
    }

    public static InvClienteCategoria convertirInvClienteCategoriaTO_InvClienteCategoria(
            InvClienteCategoriaTO invClienteCategoriaTO) {
        InvClienteCategoria invClienteCategoria = new InvClienteCategoria();
        invClienteCategoria.setInvClienteCategoriaPK(new InvClienteCategoriaPK(invClienteCategoriaTO.getCcEmpresa(), invClienteCategoriaTO.getCcCodigo()));
        invClienteCategoria.setCcDetalle(invClienteCategoriaTO.getCcDetalle());
        // invClienteCategoria.setCtaAntipos(invClienteCategoriaTO.getCtaAntipos());
        // invClienteCategoria.setCtaCobros(invClienteCategoriaTO.getCtaCobros());
        invClienteCategoria.setUsrEmpresa(invClienteCategoriaTO.getUsrEmpresa());
        invClienteCategoria.setUsrCodigo(invClienteCategoriaTO.getUsrCodigo());
        invClienteCategoria.setUsrFechaInserta(UtilsValidacion.fechaString_Date(invClienteCategoriaTO.getUsrFechaInserta()));
        invClienteCategoria.setCcRetiene(invClienteCategoriaTO.isCcRetiene());
        return invClienteCategoria;
    }

    public static InvCliente convertirInvClienteTO_InvCliente(InvClienteTO invClienteTO) {
        InvCliente invCliente = new InvCliente();
        invCliente.setInvClientePK(new InvClientePK(invClienteTO.getEmpCodigo(), invClienteTO.getCliCodigo()));
        invCliente.setCliIdTipo(invClienteTO.getCliTipoId());
        invCliente.setCliExtranjeroTipo(invClienteTO.getCliExtranjeroTipo());
        invCliente.setCliIdNumero(invClienteTO.getCliId());
        invCliente.setCliNombreComercial(invClienteTO.getCliNombreComercial());
        invCliente.setCliRazonSocial(invClienteTO.getCliRazonSocial());
        invCliente.setCliProvincia(invClienteTO.getCliProvincia());
        invCliente.setCliCiudad(invClienteTO.getCliCiudad());
        invCliente.setCliParroquia(invClienteTO.getCliParroquia());
        invCliente.setCliZona(invClienteTO.getCliZona());
        invCliente.setCliCodigoEstablecimiento(invClienteTO.getCliCodigoEstablecimiento());
        invCliente.setCliDireccion(invClienteTO.getCliDireccion());
        invCliente.setCliTelefono(invClienteTO.getCliTelefono());
        invCliente.setCliCelular(invClienteTO.getCliCelular());
        invCliente.setCliEmail(invClienteTO.getCliEmail());
        invCliente.setCliPrecio(invClienteTO.getCliPrecio());
        invCliente.setCliDiasCredito(invClienteTO.getCliDiasCredito());
        invCliente.setCliCupoCredito(invClienteTO.getCliCupoCredito());
        invCliente.setCliObservaciones(invClienteTO.getCliObservaciones());
        invCliente.setCliRelacionado(invClienteTO.getCliRelacionado());
        invCliente.setInvVendedor(new InvVendedor(invClienteTO.getVendEmpresa(), invClienteTO.getVendCodigo()));
        invCliente.setInvClienteGrupoEmpresarial(new InvClienteGrupoEmpresarial(invClienteTO.getGeEmpresa(), invClienteTO.getGeCodigo()));
        invCliente.setCliInactivo(invClienteTO.getCliInactivo());
        invCliente.setUsrEmpresa(invClienteTO.getEmpCodigo());
        invCliente.setUsrCodigo(invClienteTO.getUsrInsertaCliente());
        invCliente.setCliLugaresEntrega(invClienteTO.getCliLugaresEntrega());
        invCliente.setCliDescripcion(invClienteTO.getCliDescripcion());
        invCliente.setBanCodigo(invClienteTO.getBanCodigo());
        invCliente.setCliSexo(invClienteTO.getCliSexo());
        invCliente.setCliEstadoCivil(invClienteTO.getCliEstadoCivil());
        invCliente.setCliExportador(invClienteTO.getCliExportador() == null ? false : invClienteTO.getCliExportador());
        invCliente.setProGarantia(invClienteTO.getProGarantia());
        invCliente.setCliPais(invClienteTO.getCliPais());
        if (invClienteTO.getBanCodigo() != null && !invClienteTO.getBanCodigo().equals("")) {
            invCliente.setBanEmpresa(invClienteTO.getEmpCodigo());
        } else {
            invCliente.setBanEmpresa("");
        }
        if (invClienteTO.getVmCodigo() != null && !invClienteTO.getVmCodigo().equals("")) {
            invCliente.setInvVentasMotivo(new InvVentasMotivo(invClienteTO.getVmEmpresa(), invClienteTO.getVmCodigo()));
        }
        invCliente.setCliTipoCuenta(invClienteTO.getCliTipoCuenta());
        invCliente.setCliNumeroCuenta(invClienteTO.getCliNumeroCuenta());

        invCliente.setUsrFechaInserta(UtilsValidacion.fechaString_Date(invClienteTO.getUsrFechaInsertaCliente()));
        invCliente.setCliFechaFacturacion(invClienteTO.getCliFechaFacturacion());
        invCliente.setCliRazonSocialVerificada(invClienteTO.getCliRazonSocialVerificada());
        invCliente.setCliCupoCreditoAseguradora(invClienteTO.getCliCupoCreditoAseguradora());
        invCliente.setCliDiasCreditoAseguradora(invClienteTO.getCliDiasCreditoAseguradora());

        return invCliente;
    }

    public static InvClienteTO convertirInvCliente_InvClienteTO(InvCliente cliente) {
        InvClienteTO clienteTO = new InvClienteTO();
        clienteTO.setBanCodigo(cliente.getBanCodigo());
        clienteTO.setBanEmpresa(cliente.getBanEmpresa());
        if (cliente.getInvClienteCategoria() != null && cliente.getInvClienteCategoria().getInvClienteCategoriaPK() != null) {
            clienteTO.setCliCategoria(cliente.getInvClienteCategoria().getInvClienteCategoriaPK().getCcCodigo());
        }

        clienteTO.setCliCelular(cliente.getCliCelular());
        clienteTO.setCliCiudad(cliente.getCliCiudad());
        clienteTO.setCliCodigo(cliente.getInvClientePK() != null ? cliente.getInvClientePK().getCliCodigo() : null);
        clienteTO.setCliCodigoEstablecimiento(cliente.getCliCodigoEstablecimiento());
        clienteTO.setCliCupoCredito(cliente.getCliCupoCredito());
        clienteTO.setCliCupoCreditoAseguradora(cliente.getCliCupoCreditoAseguradora());
        clienteTO.setCliDescripcion(cliente.getCliDescripcion());
        clienteTO.setCliDiasCredito(cliente.getCliDiasCredito());
        clienteTO.setCliDiasCreditoAseguradora(cliente.getCliDiasCreditoAseguradora());
        clienteTO.setCliDireccion(cliente.getCliDireccion());
        clienteTO.setCliEmail(cliente.getCliEmail());
        clienteTO.setCliEstadoCivil(cliente.getCliEstadoCivil());
        clienteTO.setCliExportador(cliente.getCliExportador());
        clienteTO.setCliExtranjeroTipo(cliente.getCliExtranjeroTipo());
        clienteTO.setCliFechaFacturacion(cliente.getCliFechaFacturacion());
        clienteTO.setCliId(cliente.getCliIdNumero());
        clienteTO.setCliInactivo(cliente.getCliInactivo());
        clienteTO.setCliLugaresEntrega(cliente.getCliLugaresEntrega());
        clienteTO.setCliNombreComercial(cliente.getCliNombreComercial());
        clienteTO.setCliNumeroCuenta(cliente.getCliNumeroCuenta());
        clienteTO.setCliObservaciones(cliente.getCliObservaciones());
        clienteTO.setCliPais(cliente.getCliPais());
        clienteTO.setCliParroquia(cliente.getCliParroquia());
        clienteTO.setCliPrecio(cliente.getCliPrecio());
        clienteTO.setCliProvincia(cliente.getCliProvincia());
        clienteTO.setCliRazonSocial(cliente.getCliRazonSocial());
        clienteTO.setCliRazonSocialVerificada(cliente.getCliRazonSocialVerificada());
        clienteTO.setCliRelacionado(cliente.getCliRelacionado());
        clienteTO.setCliSexo(cliente.getCliSexo());
        clienteTO.setCliTelefono(cliente.getCliTelefono());
        clienteTO.setCliTipoCuenta(cliente.getCliTipoCuenta());
        clienteTO.setCliTipoId(cliente.getCliIdTipo());
        clienteTO.setCliZona(cliente.getCliZona());
        clienteTO.setEmpCodigo(cliente.getInvClientePK().getCliEmpresa());
        clienteTO.setGeCodigo(cliente.getInvClienteGrupoEmpresarial() != null ? cliente.getInvClienteGrupoEmpresarial().getInvClienteGrupoEmpresarialPK().getGeCodigo() : null);
        clienteTO.setGeEmpresa(cliente.getInvClienteGrupoEmpresarial() != null ? cliente.getInvClienteGrupoEmpresarial().getInvClienteGrupoEmpresarialPK().getGeEmpresa() : null);
        clienteTO.setProGarantia(cliente.getProGarantia());
        clienteTO.setUsrFechaInsertaCliente(UtilsValidacion.fechaDate_String((cliente.getUsrFechaInserta())));
        clienteTO.setUsrInsertaCliente(cliente.getUsrCodigo());

        if (cliente.getInvVendedor() != null && cliente.getInvVendedor().getInvVendedorPK() != null) {
            clienteTO.setVendCodigo(cliente.getInvVendedor().getInvVendedorPK().getVendCodigo());
            clienteTO.setVendEmpresa(cliente.getInvVendedor().getInvVendedorPK().getVendEmpresa());
        }

        if (cliente.getInvVentasMotivo() != null && cliente.getInvVentasMotivo().getInvVentasMotivoPK() != null) {
            clienteTO.setVmEmpresa(cliente.getInvVentasMotivo().getInvVentasMotivoPK().getVmEmpresa());
            clienteTO.setVmCodigo(cliente.getInvVentasMotivo().getInvVentasMotivoPK().getVmCodigo());
        }

        return clienteTO;
    }

    public static InvTransportista convertirInvTransportistaTO_InvTransportista(InvTransportistaTO invTransportistaTO) {
        InvTransportista invTransportista = new InvTransportista();
        invTransportista.setInvTransportistaPK(new InvTransportistaPK(invTransportistaTO.getTransEmpresa(), invTransportistaTO.getTransCodigo()));
        invTransportista.setTransEmail(invTransportistaTO.getTransEmail());
        invTransportista.setTransIdNumero(invTransportistaTO.getTransIdNumero());
        invTransportista.setTransIdTipo(invTransportistaTO.getTransIdTipo());
        invTransportista.setTransInactivo(invTransportistaTO.getTransInactivo());
        invTransportista.setTransNombres(invTransportistaTO.getTransNombres());
        invTransportista.setTransPlaca(invTransportistaTO.getTransPlaca());
        invTransportista.setUsrCodigo(invTransportistaTO.getUsrCodigo());
        invTransportista.setUsrEmpresa(invTransportistaTO.getUsrEmpresa());
        invTransportista.setUsrFechaInserta(UtilsValidacion.fechaString_Date(invTransportistaTO.getUsrFechaInserta()));

        return invTransportista;
    }

    public static InvProveedorCategoria convertirInvProveedorCategoriaTO_InvProveedorCategoria(
            InvProveedorCategoriaTO invProveedorCategoriaTO) {
        InvProveedorCategoria invProveedorCategoria = new InvProveedorCategoria();
        invProveedorCategoria.setInvProveedorCategoriaPK(new InvProveedorCategoriaPK(
                invProveedorCategoriaTO.getPcEmpresa(), invProveedorCategoriaTO.getPcCodigo()));
        invProveedorCategoria.setPcDetalle(invProveedorCategoriaTO.getPcDetalle());
        invProveedorCategoria.setPcAplicaRetencionIva(invProveedorCategoriaTO.getPcAplicaRetencionIva());
        // invProveedorCategoria.setCtaAntipos(invProveedorCategoriaTO.getCtaAntipos());
        // invProveedorCategoria.setCtaAntiposActivosFijos(invProveedorCategoriaTO.getCtaAntiposActivosFijos());
        // invProveedorCategoria.setCtaPagos(invProveedorCategoriaTO.getCtaPagos());
        // invProveedorCategoria.setCtaPagosActivosFijos(invProveedorCategoriaTO.getCtaPagosActivosFijos());
        invProveedorCategoria.setUsrEmpresa(invProveedorCategoriaTO.getUsrEmpresa());
        invProveedorCategoria.setUsrCodigo(invProveedorCategoriaTO.getUsrCodigo());
        invProveedorCategoria
                .setUsrFechaInserta(UtilsValidacion.fechaString_Date(invProveedorCategoriaTO.getUsrFechaInserta()));
        return invProveedorCategoria;
    }

    public static InvProveedor convertirInvProveedorTO_InvProveedor(InvProveedorTO invProveedorTO) {
        InvProveedor invProveedor = new InvProveedor();
        invProveedor.setInvProveedorPK(new InvProveedorPK(invProveedorTO.getEmpCodigo(), invProveedorTO.getProvCodigo()));
        invProveedor.setProvIdTipo(invProveedorTO.getProvTipoId());
        invProveedor.setProvIdNumero(invProveedorTO.getProvId());
        invProveedor.setProvRazonSocial(invProveedorTO.getProvRazonSocial());
        invProveedor.setProvNombreComercial(invProveedorTO.getProvNombreComercial());
        invProveedor.setProvProvincia(invProveedorTO.getProvProvincia());
        invProveedor.setProvCiudad(invProveedorTO.getProvCiudad());
        invProveedor.setProvParroquia(invProveedorTO.getProvParroquia());
        invProveedor.setProvZona(invProveedorTO.getProvZona());
        invProveedor.setProvExtranjeroTipo(invProveedorTO.getProvExtranjeroTipo() != null ? invProveedorTO.getProvExtranjeroTipo() : null);//nuevo campo
        invProveedor.setProvDireccion(invProveedorTO.getProvDireccion());
        invProveedor.setProvTelefono(invProveedorTO.getProvTelefono());
        invProveedor.setProvCelular(invProveedorTO.getProvCelular());
        invProveedor.setProvEmail(invProveedorTO.getProvEmail());
        invProveedor.setProvEmailOrdenCompra(invProveedorTO.getProvEmailOrdenCompra());
        invProveedor.setProvObservaciones(invProveedorTO.getProvObservaciones());
        invProveedor.setProvRelacionado(invProveedorTO.getProvRelacionado());
        invProveedor.setProvInactivo(invProveedorTO.getProvInactivo());
        invProveedor.setProvRazonSocialVerificada(invProveedorTO.getProvRazonSocialVerificada());
        invProveedor.setProvCodigoAsc(invProveedorTO.getProvCodigoAsc());
        invProveedor.setProvCodigoInp(invProveedorTO.getProvCodigoInp());
        invProveedor.setProvPais(invProveedorTO.getProvPais());
        if (invProveedorTO.getProvCupoCredito() == null) {
            invProveedor.setProvCupoCredito(BigDecimal.ZERO);
        } else {
            invProveedor.setProvCupoCredito(invProveedorTO.getProvCupoCredito());
        }

        if (invProveedorTO.getProvDiasCredito() == null) {
            invProveedor.setProvDiasCredito(0);
        } else {
            invProveedor.setProvDiasCredito(invProveedorTO.getProvDiasCredito());
        }
        invProveedor.setProvTipoCuenta(invProveedorTO.getProvTipoCuenta());
        invProveedor.setProvBanco(invProveedorTO.getProvBanco());
        invProveedor.setProvNroCuenta(invProveedorTO.getProvNroCuenta());

        invProveedor.setUsrEmpresa(invProveedorTO.getEmpCodigo());
        invProveedor.setUsrCodigo(invProveedorTO.getUsrInsertaProveedor());
        invProveedor.setUsrFechaInserta(UtilsValidacion.fechaString_Date(invProveedorTO.getUsrFechaInsertaProveedor()));
        invProveedor.setProvLugaresEntrega(invProveedorTO.getProvLugaresEntrega());
        invProveedor.setProvDescripcion(invProveedorTO.getProvDescripcion());

        invProveedor.setProvTipoIdentificacionBancaria(invProveedorTO.getProvTipoIdentificacionBancaria());
        invProveedor.setProvIdentificacionBancaria(invProveedorTO.getProvIdentificacionBancaria());
        return invProveedor;
    }

    public static InvBodega convertirInvBodegaTO_InvBodega(InvBodegaTO invBodegaTO) {
        InvBodega invBodega = new InvBodega();
        invBodega.setInvBodegaPK(new InvBodegaPK(invBodegaTO.getEmpCodigo(), invBodegaTO.getBodCodigo()));
        invBodega.setBodNombre(invBodegaTO.getBodNombre());
        invBodega.setBodInactiva(invBodegaTO.getBodInactiva());
        invBodega.setSecEmpresa(invBodegaTO.getSecEmpresa());
        invBodega.setSecCodigo(invBodegaTO.getSecCodigo());
        invBodega.setDetEmpresa(invBodegaTO.getDetEmpresa());
        invBodega.setDetUsuario(invBodegaTO.getDetUsuario());
        invBodega.setUsrEmpresa(invBodegaTO.getUsrEmpresa());
        invBodega.setUsrCodigo(invBodegaTO.getUsrInsertaBodega());
        invBodega.setUsrFechaInserta(UtilsValidacion.fechaString_Date(invBodegaTO.getUsrFechaInsertaBodega()));

        return invBodega;
    }

    public static InvGuiaRemision convertirInvGuiaRemisionTO_InvGuiaRemision(InvGuiaRemisionTO invGuiaRemisionTO) {
        InvGuiaRemision invGuiaRemision = new InvGuiaRemision();
        InvTransportista invTransportista = new InvTransportista();
        InvCliente invCliente = new InvCliente();
        invTransportista.setInvTransportistaPK(new InvTransportistaPK(invGuiaRemisionTO.getTransEmpresa(), invGuiaRemisionTO.getTransCodigo()));
        invCliente.setInvClientePK(new InvClientePK(invGuiaRemisionTO.getCliEmpresa(), invGuiaRemisionTO.getCliCodigo()));

        invGuiaRemision.setInvGuiaRemisionPK(new InvGuiaRemisionPK(invGuiaRemisionTO.getGuiaEmpresa(), invGuiaRemisionTO.getGuiaPeriodo(), invGuiaRemisionTO.getGuiaNumero()));
        invGuiaRemision.setGuiaAnulado(invGuiaRemisionTO.isGuiaAnulado());
        invGuiaRemision.setGuiaPendiente(invGuiaRemisionTO.isGuiaPendiente());
        invGuiaRemision.setGuiaCodigoEstablecimiento(invGuiaRemisionTO.getGuiaCodigoEstablecimiento());
        invGuiaRemision.setGuiaDocumentoAduanero(invGuiaRemisionTO.getGuiaDocumentoAduanero());
        invGuiaRemision.setGuiaDocumentoNumero(invGuiaRemisionTO.getGuiaDocumentoNumero());
        invGuiaRemision.setGuiaFechaEmision(UtilsValidacion.fecha(invGuiaRemisionTO.getGuiaFechaEmision(), "yyyy-MM-dd"));
        invGuiaRemision.setGuiaFechaFinTransporte(UtilsValidacion.fecha(invGuiaRemisionTO.getGuiaFechaFinTransporte(), "yyyy-MM-dd"));
        invGuiaRemision.setGuiaFechaInicioTransporte(UtilsValidacion.fecha(invGuiaRemisionTO.getGuiaFechaInicioTransporte(), "yyyy-MM-dd"));

        invGuiaRemision.setInvCliente(invCliente);

        invGuiaRemision.setGuiaMotivoTraslado(invGuiaRemisionTO.getGuiaMotivoTraslado());
        invGuiaRemision.setGuiaPlaca(invGuiaRemisionTO.getGuiaPlaca());
        invGuiaRemision.setGuiaPuntoLlegada(invGuiaRemisionTO.getGuiaPuntoLlegada());
        invGuiaRemision.setGuiaPuntoPartida(invGuiaRemisionTO.getGuiaPuntoPartida());
        invGuiaRemision.setGuiaRuta(invGuiaRemisionTO.getGuiaRuta());

        invGuiaRemision.setInvTransportista(invTransportista);

        invGuiaRemision.setNroAutorizacion(invGuiaRemisionTO.getNroAutorizacion());
        invGuiaRemision.setNroDocumento(invGuiaRemisionTO.getNroDocumento());
        invGuiaRemision.setTipoDocumento(invGuiaRemisionTO.getTipoDocumento());
        invGuiaRemision.setUsrCodigo(invGuiaRemisionTO.getUsrCodigo());
        invGuiaRemision.setUsrFechaInserta(UtilsValidacion.fechaString_Date(invGuiaRemisionTO.getUsrFechaInserta()));
        invGuiaRemision.setGuiaInformacionAdicional(invGuiaRemisionTO.getGuiaInformacionAdicional());
        invGuiaRemision.setGuiaClaveAccesoExterna(invGuiaRemisionTO.getGuiaClaveAccesoExterna());

        invGuiaRemision.setGuiaSello(invGuiaRemisionTO.getGuiaSello());
        invGuiaRemision.setGuiaRecibidor(invGuiaRemisionTO.getGuiaRecibidor());
        invGuiaRemision.setGuiaTipoMovil(invGuiaRemisionTO.getGuiaTipoMovil());
        return invGuiaRemision;
    }

    public static InvGuiaRemisionTO convertirInvGuiaRemision_InvGuiaRemisionTO(InvGuiaRemision invGuiaRemision) {
        InvGuiaRemisionTO invGuiaTO = new InvGuiaRemisionTO();

        invGuiaTO.setGuiaPeriodo(invGuiaRemision.getInvGuiaRemisionPK().getGuiaPeriodo());
        invGuiaTO.setGuiaNumero(invGuiaRemision.getInvGuiaRemisionPK().getGuiaNumero());
        invGuiaTO.setGuiaEmpresa(invGuiaRemision.getInvGuiaRemisionPK().getGuiaEmpresa());
        invGuiaTO.setGuiaAnulado(invGuiaRemision.isGuiaAnulado());
        invGuiaTO.setGuiaPendiente(invGuiaRemision.isGuiaPendiente());
        invGuiaTO.setGuiaCodigoEstablecimiento(invGuiaRemision.getGuiaCodigoEstablecimiento());
        invGuiaTO.setGuiaDocumentoAduanero(invGuiaRemision.getGuiaDocumentoAduanero());
        invGuiaTO.setGuiaDocumentoNumero(invGuiaRemision.getGuiaDocumentoNumero());
        invGuiaTO.setGuiaFechaEmision(UtilsValidacion.fecha(invGuiaRemision.getGuiaFechaEmision(), "yyyy-MM-dd"));
        invGuiaTO.setGuiaFechaFinTransporte(UtilsValidacion.fecha(invGuiaRemision.getGuiaFechaFinTransporte(), "yyyy-MM-dd"));
        invGuiaTO.setGuiaFechaInicioTransporte(UtilsValidacion.fecha(invGuiaRemision.getGuiaFechaInicioTransporte(), "yyyy-MM-dd"));

        invGuiaTO.setCliCodigo(invGuiaRemision.getInvCliente().getInvClientePK().getCliCodigo());
        invGuiaTO.setCliEmpresa(invGuiaRemision.getInvCliente().getInvClientePK().getCliEmpresa());

        invGuiaTO.setGuiaMotivoTraslado(invGuiaRemision.getGuiaMotivoTraslado());
        invGuiaTO.setGuiaPlaca(invGuiaRemision.getGuiaPlaca());
        invGuiaTO.setGuiaPuntoLlegada(invGuiaRemision.getGuiaPuntoLlegada());
        invGuiaTO.setGuiaPuntoPartida(invGuiaRemision.getGuiaPuntoPartida());
        invGuiaTO.setGuiaRuta(invGuiaRemision.getGuiaRuta());

        invGuiaTO.setTransCodigo(invGuiaRemision.getInvTransportista().getInvTransportistaPK().getTransCodigo());
        invGuiaTO.setTransEmpresa(invGuiaRemision.getInvTransportista().getInvTransportistaPK().getTransEmpresa());

        invGuiaTO.setNroAutorizacion(invGuiaRemision.getNroAutorizacion());
        invGuiaTO.setNroDocumento(invGuiaRemision.getNroDocumento());
        invGuiaTO.setTipoDocumento(invGuiaRemision.getTipoDocumento());
        invGuiaTO.setUsrCodigo(invGuiaRemision.getUsrCodigo());
        invGuiaTO.setUsrFechaInserta(UtilsValidacion.fechaDate_String(invGuiaRemision.getUsrFechaInserta()));
        invGuiaTO.setGuiaInformacionAdicional(invGuiaRemision.getGuiaInformacionAdicional());
        invGuiaTO.setGuiaClaveAccesoExterna(invGuiaRemision.getGuiaClaveAccesoExterna());

        invGuiaTO.setGuiaSello(invGuiaRemision.getGuiaSello());
        invGuiaTO.setGuiaRecibidor(invGuiaRemision.getGuiaRecibidor());
        invGuiaTO.setGuiaTipoMovil(invGuiaRemision.getGuiaTipoMovil());
        return invGuiaTO;
    }

    public static InvGuiaRemision convertirInvGuiaRemision_InvGuiaRemision(InvGuiaRemision invGuiaRemisionAux) {
        InvGuiaRemision invGuiaRemision = new InvGuiaRemision();
        invGuiaRemision.setInvGuiaRemisionPK(invGuiaRemisionAux.getInvGuiaRemisionPK());
        invGuiaRemision.setGuiaAnulado(invGuiaRemisionAux.isGuiaAnulado());
        invGuiaRemision.setGuiaPendiente(invGuiaRemisionAux.isGuiaPendiente());
        invGuiaRemision.setGuiaCodigoEstablecimiento(invGuiaRemisionAux.getGuiaCodigoEstablecimiento());
        invGuiaRemision.setGuiaDocumentoAduanero(invGuiaRemisionAux.getGuiaDocumentoAduanero());
        invGuiaRemision.setGuiaDocumentoNumero(invGuiaRemisionAux.getGuiaDocumentoNumero());
        invGuiaRemision.setGuiaFechaEmision(invGuiaRemisionAux.getGuiaFechaEmision());
        invGuiaRemision.setGuiaFechaFinTransporte(invGuiaRemisionAux.getGuiaFechaFinTransporte());
        invGuiaRemision.setGuiaFechaInicioTransporte(invGuiaRemisionAux.getGuiaFechaInicioTransporte());
        invGuiaRemision.setInvCliente(invGuiaRemisionAux.getInvCliente());
        invGuiaRemision.setGuiaMotivoTraslado(invGuiaRemisionAux.getGuiaMotivoTraslado());
        invGuiaRemision.setGuiaPlaca(invGuiaRemisionAux.getGuiaPlaca());
        invGuiaRemision.setGuiaPuntoLlegada(invGuiaRemisionAux.getGuiaPuntoLlegada());
        invGuiaRemision.setGuiaPuntoPartida(invGuiaRemisionAux.getGuiaPuntoPartida());
        invGuiaRemision.setGuiaRuta(invGuiaRemisionAux.getGuiaRuta());
        invGuiaRemision.setInvTransportista(invGuiaRemisionAux.getInvTransportista());
        invGuiaRemision.setNroAutorizacion(invGuiaRemisionAux.getNroAutorizacion());
        invGuiaRemision.setNroDocumento(invGuiaRemisionAux.getNroDocumento());
        invGuiaRemision.setTipoDocumento(invGuiaRemisionAux.getTipoDocumento());
        invGuiaRemision.setUsrCodigo(invGuiaRemisionAux.getUsrCodigo());
        invGuiaRemision.setUsrFechaInserta(invGuiaRemisionAux.getUsrFechaInserta());
        invGuiaRemision.setGuiaInformacionAdicional(invGuiaRemisionAux.getGuiaInformacionAdicional());
        invGuiaRemision.setGuiaClaveAccesoExterna(invGuiaRemisionAux.getGuiaClaveAccesoExterna());
        invGuiaRemision.setGuiaSello(invGuiaRemisionAux.getGuiaSello());
        invGuiaRemision.setGuiaRecibidor(invGuiaRemisionAux.getGuiaRecibidor());
        invGuiaRemision.setGuiaTipoMovil(invGuiaRemisionAux.getGuiaTipoMovil());

        invGuiaRemision.setGuiaGramos(invGuiaRemisionAux.getGuiaGramos());
        invGuiaRemision.setGuiaLibras(invGuiaRemisionAux.getGuiaLibras());
        invGuiaRemision.setGuiaHora(invGuiaRemisionAux.getGuiaHora());
        invGuiaRemision.setGuiaInp(invGuiaRemisionAux.getGuiaInp());
        invGuiaRemision.setGuiaInpEmpresa(invGuiaRemisionAux.getGuiaInpEmpresa());
        invGuiaRemision.setGuiaInpCliCodigo(invGuiaRemisionAux.getGuiaInpCliCodigo());
        invGuiaRemision.setGuiaRemisionRuta(invGuiaRemisionAux.getGuiaRemisionRuta());
        return invGuiaRemision;
    }

    public static InvCompras convertirInvComprasTO_InvCompras(InvComprasTO invComprasTO) {

        InvCompras invCompras = new InvCompras();
        invCompras.setInvComprasPK(new InvComprasPK(invComprasTO.getEmpCodigo(), invComprasTO.getCompPeriodo(),
                invComprasTO.getCompMotivo(), invComprasTO.getCompNumero()));
        invCompras.setCompDocumentoEmpresa(invComprasTO.getEmpCodigo());
        invCompras.setCompDocumentoProveedor(invComprasTO.getProvCodigo());
        invCompras.setCompDocumentoTipo(invComprasTO.getCompDocumentoTipo());
        invCompras.setCompDocumentoNumero(invComprasTO.getCompDocumentoNumero());
        // invCompras.setProvCodigo(invComprasTO.getProvCodigo());
        invCompras.setCompFecha(UtilsValidacion.fecha(invComprasTO.getCompFecha(), "yyyy-MM-dd"));
        invCompras.setCompFechaVencimiento(UtilsValidacion.fecha(invComprasTO.getCompFechaVencimiento(), "yyyy-MM-dd"));
        invCompras.setCompIvaVigente(invComprasTO.getCompIvaVigente());
        invCompras.setCompObservaciones(invComprasTO.getCompObservaciones());
        invCompras.setCompElectronica(invComprasTO.getCompElectronica());
        invCompras.setCompActivoFijo(invComprasTO.getCompActivoFijo());
        invCompras.setCompImportacion(invComprasTO.getCompImportacion());

        invCompras.setCompPropina(invComprasTO.getCompPropina());
        invCompras.setCompIce(invComprasTO.getCompIce());
        // solo puede serun solo valor
        invCompras.setCompPendiente(invComprasTO.getCompPendiente());
        invCompras.setCompRevisado(invComprasTO.getCompRevisado());
        invCompras.setCompAnulado(invComprasTO.getCompAnulado());

        invCompras.setCompFormaPago(invComprasTO.getCompFormaPago());
        invCompras.setCompDocumentoFormaPago(invComprasTO.getCompDocumentoFormaPago());

        invCompras.setCompBase0(invComprasTO.getCompBase0());
        invCompras.setCompBaseImponible(invComprasTO.getCompBaseImponible());
        invCompras.setCompBaseNoObjeto(invComprasTO.getCompBaseNoObjeto());
        invCompras.setCompBaseExenta(invComprasTO.getCompBaseExenta());

        invCompras.setCompMontoIva(invComprasTO.getCompMontoIva());

        invCompras.setCompOtrosImpuestos(invComprasTO.getCompOtrosImpuestos());
        invCompras.setCompTotal(invComprasTO.getCompTotal());
        invCompras.setCompValorRetenido(invComprasTO.getCompValorRetenido());
        invCompras.setCompRetencionAsumida(invComprasTO.isCompRetencionAsumida());
        invCompras.setCompSaldo(invComprasTO.getCompSaldo());
        invCompras.setCompIce(invComprasTO.getCompIce());

        invCompras.setCompPropina(invComprasTO.getCompPropina());

        // invCompras.setInvProveedor(new
        // InvProveedor(invComprasTO.getProvEmpresa(),invComprasTO.getProvCodigo()));
        if (invComprasTO.getBodCodigo() != null) {
            invCompras.setInvBodega(new InvBodega(invComprasTO.getBodEmpresa(), invComprasTO.getBodCodigo()));
        }
        invCompras.setSecEmpresa(invComprasTO.getEmpCodigo());
        invCompras.setSecCodigo(invComprasTO.getSecCodigo());

        invCompras.setCtaEmpresa(invComprasTO.getCtaEmpresa());
        invCompras.setCtaCodigo(invComprasTO.getCtaCodigo());

        invCompras.setConEmpresa(invComprasTO.getEmpCodigo());
        invCompras.setConPeriodo(invComprasTO.getContPeriodo());
        invCompras.setConTipo(invComprasTO.getContTipo());
        invCompras.setConNumero(invComprasTO.getContNumero());

        invCompras.setEmpCodigo(invComprasTO.getEmpCodigo());
        invCompras.setUsrCodigo(invComprasTO.getUsrInsertaCompra());
        invCompras.setUsrFechaInserta(UtilsValidacion.fechaString_Date(invComprasTO.getUsrFechaInsertaCompra()));
        //orden de compra
        invCompras.setOcEmpresa(!esNuloOVacio(invComprasTO.getOcEmpresa()) ? invComprasTO.getOcEmpresa() : null);
        invCompras.setOcMotivo(!esNuloOVacio(invComprasTO.getOcMotivo()) ? invComprasTO.getOcMotivo() : null);
        invCompras.setOcSector(!esNuloOVacio(invComprasTO.getOcSector()) ? invComprasTO.getOcSector() : null);
        invCompras.setOcNumero(!esNuloOVacio(invComprasTO.getOcNumero()) ? invComprasTO.getOcNumero() : null);
        invCompras.setCompProgramada(invComprasTO.isCompProgramada());
        invCompras.setCompUsuarioApruebaPago(invComprasTO.getCompUsuarioApruebaPago());
        invCompras.setCompFechaApruebaPago(invComprasTO.getCompFechaApruebaPago());
        invCompras.setCompImbFacturado(invComprasTO.isCompImbFacturado());
        invCompras.setCompTransportistaRuc(invComprasTO.getCompTransportistaRuc());
        invCompras.setFechaUltimaValidacionSri(
                invComprasTO.getFechaUltimaValidacionSri() != null
                ? UtilsValidacion.fecha(invComprasTO.getFechaUltimaValidacionSri(), "yyyy-MM-dd") : null);
        invCompras.setOcOrdenPedido(invComprasTO.getOcOrdenPedido() != null ? invComprasTO.getOcOrdenPedido() : null);
        invCompras.setCompSaldoImportado(invComprasTO.isCompSaldoImportado());
        invCompras.setFpSecuencial(invComprasTO.getFpSecuencial());
        invCompras.setCompGuiaCompra(invComprasTO.getCompGuiaCompra());
        invCompras.setCompEsImb(invComprasTO.getCompEsImb());
        return invCompras;
    }

    public static boolean esNuloOVacio(String cadena) {
        return cadena == null || cadena.equals("");
    }

    public static ReporteVentaDetalle obtenerItemReporte(String claveDeAcceso, InvVentasTO invVentasTO, InvVentasComplementoTO invVentasComplemento, InvVentasDetalleTO item, InvCliente cliente, InvVendedor vendedor) {
        ReporteVentaDetalle reporteVentaDetalle = new ReporteVentaDetalle();
        reporteVentaDetalle.setEmpCodigo(invVentasTO.getVtaEmpresa());
        reporteVentaDetalle.setPerCodigo(invVentasTO.getVtaPeriodo());
        reporteVentaDetalle.setMotCodigo(invVentasTO.getVtaMotivo());
        reporteVentaDetalle.setVtaNumero(invVentasTO.getVtaNumero());
        reporteVentaDetalle.setVtaDocumentoTipo(invVentasTO.getVtaDocumentoTipo());
        reporteVentaDetalle.setVtaDocumentoNumero(invVentasTO.getVtaDocumentoNumero());
        reporteVentaDetalle.setDocElectronicoClaveAcceso(claveDeAcceso);
        reporteVentaDetalle.setVtaFecha(invVentasTO.getVtaFecha());
        reporteVentaDetalle.setVtaFechaVencimiento(invVentasTO.getVtaFechaVencimiento());
        reporteVentaDetalle.setVtaIvaVigente(invVentasTO.getVtaIvaVigente());
        reporteVentaDetalle.setVtaObservaciones(invVentasTO.getVtaObservaciones());
        reporteVentaDetalle.setVtaPendiente(invVentasTO.getVtaPendiente());
        reporteVentaDetalle.setVtaRevisado(invVentasTO.getVtaRevisado());
        reporteVentaDetalle.setVtaAnulado(invVentasTO.getVtaAnulado());
        reporteVentaDetalle.setVtaFormaPago(invVentasTO.getVtaFormaPago());
        reporteVentaDetalle.setVtaBase0(invVentasTO.getVtaBase0());
        reporteVentaDetalle.setVtaBaseimponible(invVentasTO.getVtaBaseImponible());
        reporteVentaDetalle.setVtaRecargoBase0(invVentasTO.getVtaRecargoBase0());
        reporteVentaDetalle.setVtaRecargoImponible(invVentasTO.getVtaRecargoBaseImponible());
        reporteVentaDetalle.setVtaDescuentoBase0(invVentasTO.getVtaDescuentoBase0());
        reporteVentaDetalle.setVtaDescuentoBaseImponible(invVentasTO.getVtaDescuentoBaseImponible());
        reporteVentaDetalle.setVtaDescuentoBaseExenta(invVentasTO.getVtaDescuentoBaseExenta());
        reporteVentaDetalle.setVtaDescuentoBaseNoObjeto(invVentasTO.getVtaDescuentoBaseNoObjeto());
        reporteVentaDetalle.setVtaDescuentoGeneralBase0(invVentasTO.getVtaDescuentoBase0() != null ? invVentasTO.getVtaDescuentoBase0() : new BigDecimal(BigInteger.ZERO));
        reporteVentaDetalle.setVtaDescuentoGeneralBaseImponible(invVentasTO.getVtaDescuentoBaseImponible() != null ? invVentasTO.getVtaDescuentoBaseImponible() : new BigDecimal(BigInteger.ZERO));
        reporteVentaDetalle.setVtaSubtotalBase0(invVentasTO.getVtaSubtotalBase0());
        reporteVentaDetalle.setVtaSubtotalBaseImponible(invVentasTO.getVtaSubtotalBaseImponible());
        reporteVentaDetalle.setVtaMontoiva(invVentasTO.getVtaMontoIva());
        reporteVentaDetalle.setVtaMontoice(invVentasTO.getVtaMontoIce());
        reporteVentaDetalle.setVtaTotal(invVentasTO.getVtaTotal());
        reporteVentaDetalle.setVtaPagado(invVentasTO.getVtaPagadoEfectivo());
        reporteVentaDetalle.setVtaPagadoDineroElectronico(invVentasTO.getVtaPagadoDineroElectronico());
        reporteVentaDetalle.setVtaPagadoEfectivo(invVentasTO.getVtaPagadoEfectivo());
        reporteVentaDetalle.setVtaPagadoOtro(invVentasTO.getVtaPagadoOtro());
        reporteVentaDetalle.setVtaPagadoTarjetaCredito(invVentasTO.getVtaPagadoTarjetaCredito());
        reporteVentaDetalle.setCliCodigo(invVentasTO.getCliCodigo());
        reporteVentaDetalle.setCliNombre(cliente.getCliRazonSocial());
        reporteVentaDetalle.setVtaTotalLetra(NumberToLetterConverter.convertNumberToLetter((!invVentasTO.getVtaTotal().toString().contains(".") ? (invVentasTO.getVtaTotal().toString() + ".00") : invVentasTO.getVtaTotal().toString())));
        reporteVentaDetalle.setCliRuc(cliente.getCliIdNumero());
        reporteVentaDetalle.setCliEmail(cliente.getCliEmail());
        reporteVentaDetalle.setCliDireccion(cliente.getCliDireccion());
        reporteVentaDetalle.setCliTelefono(cliente.getCliTelefono());
        reporteVentaDetalle.setCliCiudad(cliente.getCliCiudad());
        reporteVentaDetalle.setCodigoSector(invVentasTO.getSecCodigo());
        reporteVentaDetalle.setContPeriodo(invVentasTO.getConPeriodo());
        reporteVentaDetalle.setContTipo(invVentasTO.getConTipo());
        reporteVentaDetalle.setContNumero(invVentasTO.getConNumero());
        reporteVentaDetalle.setUsrInsertaCompra(invVentasTO.getUsrCodigo());
        reporteVentaDetalle.setUsrFechaInsertaCompra(invVentasTO.getUsrFechaInserta());
        reporteVentaDetalle.setBodCodigo(item.getBodCodigo());
        reporteVentaDetalle.setSecCodigo(item.getPisSector());
        reporteVentaDetalle.setPisNumero(item.getPisNumero());
        reporteVentaDetalle.setVtaPagadoDineroElectronico(invVentasTO.getVtaPagadoDineroElectronico());
        reporteVentaDetalle.setVtaPagadoEfectivo(invVentasTO.getVtaPagadoEfectivo());
        reporteVentaDetalle.setVtaPagadoOtro(invVentasTO.getVtaPagadoOtro());
        reporteVentaDetalle.setVtaPagadoTarjetaCredito(invVentasTO.getVtaPagadoTarjetaCredito());
        reporteVentaDetalle.setProCodigoPrincipal(item.getProCodigoPrincipal());
        reporteVentaDetalle.setProNombre(item.getProNombre());
        reporteVentaDetalle.setDetMedida(item.getProMedida());
        reporteVentaDetalle.setDetCantidad(item.getDetCantidad());
        reporteVentaDetalle.setDetPrecio(item.getDetPrecio());
        reporteVentaDetalle.setDetParcial(item.getDetValorPromedio());
        reporteVentaDetalle.setDetTotal(item.getDetSaldo());
        reporteVentaDetalle.setDetPorcentajeRecargo(item.getDetPorcentajeRecargo());
        reporteVentaDetalle.setDetPorcentajeDescuento(item.getDetPorcentajeDescuento());
//        reporteVentaDetalle.setDetTotal(item.getTotal());
        reporteVentaDetalle.setDetIva(item.getVtIva());
        reporteVentaDetalle.setDetCantidadCaja(new BigDecimal(BigInteger.ZERO));
        reporteVentaDetalle.setVtaVendedor(vendedor != null ? vendedor.getVendNombre() : "");
        reporteVentaDetalle.setVtaConsignacion(invVentasTO.isVtaConsignacion());

        reporteVentaDetalle.setParAgenteRetencion(item.getParAgenteRetencion());
        reporteVentaDetalle.setParContribuyenteRegimenMicroempresa(item.getParContribuyenteRegimenMicroempresa());
        reporteVentaDetalle.setParObligadoLlevarContabilidad(item.getParObligadoLlevarContabilidad());
        reporteVentaDetalle.setParResolucionContribuyenteEspecial(item.getParResolucionContribuyenteEspecial());

        reporteVentaDetalle.setDetEmpaque(item.getDetEmpaque());
        reporteVentaDetalle.setDetEmpaqueCantidad(item.getDetEmpaqueCantidad());
        reporteVentaDetalle.setDetConversionPesoNeto(item.getDetConversionPesoNeto());
        reporteVentaDetalle.setMedConversionKilos(item.getMedConversionKilos());
        reporteVentaDetalle.setMedConversionLibras(item.getMedConversionLibras());

        reporteVentaDetalle.setInfoAdicional(item.getInfoAdicional());
        //COMPLEMENTO
        if (invVentasComplemento != null) {
            reporteVentaDetalle.setComDocumentoMotivo(invVentasComplemento.getComDocumentoMotivo());
            reporteVentaDetalle.setComDocumentoNumero(invVentasComplemento.getComDocumentoNumero());
            reporteVentaDetalle.setComDocumentoTipo(invVentasComplemento.getComDocumentoTipo());
        }
        return reporteVentaDetalle;
    }

    public static ReporteVentaDetalle obtenerItemReporte(String claveDeAcceso, InvVentasTO invVentasTO, InvVentasComplemento invVentasComplemento, InvVentasDetalleTO item, InvCliente cliente, InvVendedor vendedor) {
        ReporteVentaDetalle reporteVentaDetalle = new ReporteVentaDetalle();
        reporteVentaDetalle.setEmpCodigo(invVentasTO.getVtaEmpresa());
        reporteVentaDetalle.setPerCodigo(invVentasTO.getVtaPeriodo());
        reporteVentaDetalle.setMotCodigo(invVentasTO.getVtaMotivo());
        reporteVentaDetalle.setVtaNumero(invVentasTO.getVtaNumero());
        reporteVentaDetalle.setVtaDocumentoTipo(invVentasTO.getVtaDocumentoTipo());
        reporteVentaDetalle.setVtaDocumentoNumero(invVentasTO.getVtaDocumentoNumero());
        reporteVentaDetalle.setDocElectronicoClaveAcceso(claveDeAcceso);
        reporteVentaDetalle.setVtaFecha(invVentasTO.getVtaFecha());
        reporteVentaDetalle.setVtaFechaVencimiento(invVentasTO.getVtaFechaVencimiento());
        reporteVentaDetalle.setVtaIvaVigente(invVentasTO.getVtaIvaVigente());
        reporteVentaDetalle.setVtaObservaciones(invVentasTO.getVtaObservaciones());
        reporteVentaDetalle.setVtaPendiente(invVentasTO.getVtaPendiente());
        reporteVentaDetalle.setVtaRevisado(invVentasTO.getVtaRevisado());
        reporteVentaDetalle.setVtaAnulado(invVentasTO.getVtaAnulado());
        reporteVentaDetalle.setVtaFormaPago(invVentasTO.getVtaFormaPago());
        reporteVentaDetalle.setVtaBase0(invVentasTO.getVtaBase0());
        reporteVentaDetalle.setVtaBaseimponible(invVentasTO.getVtaBaseImponible());
        reporteVentaDetalle.setVtaRecargoBase0(invVentasTO.getVtaRecargoBase0());
        reporteVentaDetalle.setVtaRecargoImponible(invVentasTO.getVtaRecargoBaseImponible());
        reporteVentaDetalle.setVtaDescuentoBase0(invVentasTO.getVtaDescuentoBase0());
        reporteVentaDetalle.setVtaDescuentoBaseImponible(invVentasTO.getVtaDescuentoBaseImponible());
        reporteVentaDetalle.setVtaDescuentoBaseExenta(invVentasTO.getVtaDescuentoBaseExenta());
        reporteVentaDetalle.setVtaDescuentoBaseNoObjeto(invVentasTO.getVtaDescuentoBaseNoObjeto());
        reporteVentaDetalle.setVtaDescuentoGeneralBase0(invVentasTO.getVtaDescuentoBase0() != null ? invVentasTO.getVtaDescuentoBase0() : new BigDecimal(BigInteger.ZERO));
        reporteVentaDetalle.setVtaDescuentoGeneralBaseImponible(invVentasTO.getVtaDescuentoBaseImponible() != null ? invVentasTO.getVtaDescuentoBaseImponible() : new BigDecimal(BigInteger.ZERO));
        reporteVentaDetalle.setVtaSubtotalBase0(invVentasTO.getVtaSubtotalBase0());
        reporteVentaDetalle.setVtaSubtotalBaseImponible(invVentasTO.getVtaSubtotalBaseImponible());
        reporteVentaDetalle.setVtaMontoiva(invVentasTO.getVtaMontoIva());
        reporteVentaDetalle.setVtaMontoice(invVentasTO.getVtaMontoIce());
        reporteVentaDetalle.setVtaTotal(invVentasTO.getVtaTotal());
        reporteVentaDetalle.setVtaPagado(invVentasTO.getVtaPagadoEfectivo());
        reporteVentaDetalle.setVtaPagadoDineroElectronico(invVentasTO.getVtaPagadoDineroElectronico());
        reporteVentaDetalle.setVtaPagadoEfectivo(invVentasTO.getVtaPagadoEfectivo());
        reporteVentaDetalle.setVtaPagadoOtro(invVentasTO.getVtaPagadoOtro());
        reporteVentaDetalle.setVtaPagadoTarjetaCredito(invVentasTO.getVtaPagadoTarjetaCredito());
        reporteVentaDetalle.setCliCodigo(invVentasTO.getCliCodigo());
        reporteVentaDetalle.setCliNombre(cliente.getCliRazonSocial());
        reporteVentaDetalle.setVtaTotalLetra(NumberToLetterConverter.convertNumberToLetter((!invVentasTO.getVtaTotal().toString().contains(".") ? (invVentasTO.getVtaTotal().toString() + ".00") : invVentasTO.getVtaTotal().toString())));
        reporteVentaDetalle.setCliRuc(cliente.getCliIdNumero());
        reporteVentaDetalle.setCliEmail(cliente.getCliEmail());
        reporteVentaDetalle.setCliDireccion(cliente.getCliDireccion());
        reporteVentaDetalle.setCliTelefono(cliente.getCliTelefono());
        reporteVentaDetalle.setCliCiudad(cliente.getCliCiudad());
        reporteVentaDetalle.setCodigoSector(invVentasTO.getSecCodigo());
        reporteVentaDetalle.setContPeriodo(invVentasTO.getConPeriodo());
        reporteVentaDetalle.setContTipo(invVentasTO.getConTipo());
        reporteVentaDetalle.setContNumero(invVentasTO.getConNumero());
        reporteVentaDetalle.setUsrInsertaCompra(invVentasTO.getUsrCodigo());
        reporteVentaDetalle.setUsrFechaInsertaCompra(invVentasTO.getUsrFechaInserta());
        reporteVentaDetalle.setBodCodigo(item.getBodCodigo());
        reporteVentaDetalle.setSecCodigo(item.getPisSector());
        reporteVentaDetalle.setPisNumero(item.getPisNumero());
        reporteVentaDetalle.setVtaPagadoDineroElectronico(invVentasTO.getVtaPagadoDineroElectronico());
        reporteVentaDetalle.setVtaPagadoEfectivo(invVentasTO.getVtaPagadoEfectivo());
        reporteVentaDetalle.setVtaPagadoOtro(invVentasTO.getVtaPagadoOtro());
        reporteVentaDetalle.setVtaPagadoTarjetaCredito(invVentasTO.getVtaPagadoTarjetaCredito());
        reporteVentaDetalle.setProCodigoPrincipal(item.getProCodigoPrincipal());
        reporteVentaDetalle.setProNombre(item.getProNombre());
        reporteVentaDetalle.setDetMedida(item.getProMedida());
        reporteVentaDetalle.setDetCantidad(item.getDetCantidad());
        reporteVentaDetalle.setDetPrecio(item.getDetPrecio());
        reporteVentaDetalle.setDetParcial(item.getDetValorPromedio());
        reporteVentaDetalle.setDetTotal(item.getDetSaldo());
        reporteVentaDetalle.setDetPorcentajeRecargo(item.getDetPorcentajeRecargo());
        reporteVentaDetalle.setDetPorcentajeDescuento(item.getDetPorcentajeDescuento());
//        reporteVentaDetalle.setDetTotal(item.getTotal());
        reporteVentaDetalle.setDetIva(item.getVtIva());
        reporteVentaDetalle.setDetCantidadCaja(new BigDecimal(BigInteger.ZERO));
        reporteVentaDetalle.setVtaVendedor(vendedor != null ? vendedor.getVendNombre() : "");
        reporteVentaDetalle.setVtaConsignacion(invVentasTO.isVtaConsignacion());

        reporteVentaDetalle.setParAgenteRetencion(item.getParAgenteRetencion());
        reporteVentaDetalle.setParContribuyenteRegimenMicroempresa(item.getParContribuyenteRegimenMicroempresa());
        reporteVentaDetalle.setParObligadoLlevarContabilidad(item.getParObligadoLlevarContabilidad());
        reporteVentaDetalle.setParResolucionContribuyenteEspecial(item.getParResolucionContribuyenteEspecial());

        reporteVentaDetalle.setDetEmpaque(item.getDetEmpaque());
        reporteVentaDetalle.setDetEmpaqueCantidad(item.getDetEmpaqueCantidad());
        reporteVentaDetalle.setDetConversionPesoNeto(item.getDetConversionPesoNeto());
        reporteVentaDetalle.setMedConversionKilos(item.getMedConversionKilos());
        reporteVentaDetalle.setMedConversionLibras(item.getMedConversionLibras());

        reporteVentaDetalle.setInfoAdicional(item.getInfoAdicional());
        //COMPLEMENTO
        if (invVentasComplemento != null) {
            reporteVentaDetalle.setComDocumentoMotivo(invVentasComplemento.getComDocumentoMotivo());
            reporteVentaDetalle.setComDocumentoNumero(invVentasComplemento.getComDocumentoNumero());
            reporteVentaDetalle.setComDocumentoTipo(invVentasComplemento.getComDocumentoTipo());
            reporteVentaDetalle.setComFechaEmisionDocSustento(UtilsDate.DeDateAString(invVentasComplemento.getInvVentas().getVtaFecha()));
        }
        return reporteVentaDetalle;
    }

    public static InvVentas convertirInvVentasTO_InvVentas(InvVentasTO invVentasTO) {
        InvVentas invVentas = new InvVentas();

        invVentas.setInvVentasPK(new InvVentasPK(invVentasTO.getVtaEmpresa(), invVentasTO.getVtaPeriodo(),
                invVentasTO.getVtaMotivo(), invVentasTO.getVtaNumero()));
        invVentas.setVtaDocumentoTipo(invVentasTO.getVtaDocumentoTipo());
        invVentas.setVtaReembolso(invVentasTO.isVtaReembolso());
        invVentas.setVtaDocumentoNumero(invVentasTO.getVtaDocumentoNumero());
        invVentas.setVtaFecha(UtilsValidacion.fecha(invVentasTO.getVtaFecha(), "yyyy-MM-dd"));
        invVentas.setVtaFechaVencimiento(UtilsValidacion.fecha(invVentasTO.getVtaFechaVencimiento(), "yyyy-MM-dd"));
        invVentas.setVtaIvaVigente(invVentasTO.getVtaIvaVigente());
        invVentas.setVtaObservaciones(invVentasTO.getVtaObservaciones());
        invVentas.setVtaInformacionAdicional(invVentasTO.getVtaInformacionAdicional());
        invVentas.setVtaPendiente(invVentasTO.getVtaPendiente());
        invVentas.setVtaRevisado(invVentasTO.getVtaRevisado());
        invVentas.setVtaAnulado(invVentasTO.getVtaAnulado());
        invVentas.setVtaFormaPago(invVentasTO.getVtaFormaPago());
        invVentas.setVtaBase0(invVentasTO.getVtaBase0());
        invVentas.setVtaBaseImponible(invVentasTO.getVtaBaseImponible());
        invVentas.setVtaBaseNoObjeto(invVentasTO.getVtaBaseNoObjeto());
        invVentas.setVtaBaseExenta(invVentasTO.getVtaBaseExenta());
        invVentas.setVtaDescuentoBase0(invVentasTO.getVtaDescuentoBase0());
        invVentas.setVtaDescuentoBaseImponible(invVentasTO.getVtaDescuentoBaseImponible());
        invVentas.setVtaDescuentoBaseNoObjeto(invVentasTO.getVtaDescuentoBaseNoObjeto());
        invVentas.setVtaDescuentoBaseExenta(invVentasTO.getVtaDescuentoBaseExenta());
        invVentas.setVtaSubtotalBase0(invVentasTO.getVtaSubtotalBase0());
        invVentas.setVtaSubtotalBaseImponible(invVentasTO.getVtaSubtotalBaseImponible());
        invVentas.setVtaSubtotalBaseNoObjeto(invVentasTO.getVtaSubtotalBaseNoObjeto());
        invVentas.setVtaSubtotalBaseExenta(invVentasTO.getVtaSubtotalBaseExenta());
        invVentas.setVtaMontoiva(invVentasTO.getVtaMontoIva());
        invVentas.setVtaTotal(invVentasTO.getVtaTotal());

        //Campos nuevos
        invVentas.setVtaListaDePrecios(invVentasTO.getVtaListaDePrecios());
        invVentas.setVtaReembolso(invVentasTO.isVtaReembolso());
        invVentas.setFcBanco(invVentasTO.getFcBanco());
        invVentas.setFcCuenta(invVentasTO.getFcCuenta());
        invVentas.setFcCheque(invVentasTO.getFcCheque());
        invVentas.setFcLote(invVentasTO.getFcLote());
        invVentas.setFcTitular(invVentasTO.getFcTitular());
        invVentas.setFcSecuencial(invVentasTO.getFcSecuencial() != null && invVentasTO.getFcSecuencial() > 0 ? new InvVentasFormaCobro(invVentasTO.getFcSecuencial()) : null);
        invVentas.setInvProformas(invVentasTO.getProfNumero() != null && !invVentasTO.getProfNumero().equals("") ? new InvProformas(invVentasTO.getProfEmpresa(), invVentasTO.getProfPeriodo(), invVentasTO.getProfMotivo(), invVentasTO.getProfNumero()) : null);

        invVentas.setVtaPagadoDineroElectronico(invVentasTO.getVtaPagadoDineroElectronico());
        invVentas.setVtaPagadoEfectivo(invVentasTO.getVtaPagadoEfectivo());
        invVentas.setVtaPagadoOtro(invVentasTO.getVtaPagadoOtro());
        invVentas.setVtaPagadoTarjetaCredito(invVentasTO.getVtaPagadoTarjetaCredito());

        if (invVentasTO.getBodCodigo() != null && !invVentasTO.getBodCodigo().equals("")) {
            invVentas.setInvBodega(new InvBodega(invVentasTO.getBodEmpresa(), invVentasTO.getBodCodigo()));
        }
        invVentas.setInvCliente(new InvCliente(invVentasTO.getCliEmpresa(), invVentasTO.getCliCodigo()));
        invVentas.setSecEmpresa(invVentasTO.getVtaEmpresa());
        invVentas.setSecCodigo(invVentasTO.getSecCodigo() != null ? invVentasTO.getSecCodigo().trim() : null);
        invVentas.setCtaEmpresa(invVentasTO.getCtaEmpresa());
        invVentas.setCtaCodigo(invVentasTO.getCtaCodigo());
        invVentas.setConEmpresa(invVentasTO.getConEmpresa());
        invVentas.setConPeriodo(invVentasTO.getConPeriodo());
        invVentas.setConTipo(invVentasTO.getConTipo());
        invVentas.setConNumero(invVentasTO.getConNumero());
        invVentas.setUsrCodigo(invVentasTO.getUsrCodigo());
        invVentas.setUsrEmpresa(invVentasTO.getUsrEmpresa());
        invVentas.setUsrFechaInserta(UtilsValidacion.fechaString_Date(invVentasTO.getUsrFechaInserta() != null && !invVentasTO.getUsrFechaInserta().equals("") ? invVentasTO.getUsrFechaInserta() : null));
        invVentas.setUsrModifica(invVentasTO.getUsrModifica());
        invVentas.setUsrFechaModifica(UtilsValidacion.fechaString_Date(invVentasTO.getUsrFechaModifica() != null && !invVentasTO.getUsrFechaModifica().equals("") ? invVentasTO.getUsrFechaModifica() : null));

        invVentas.setVtaRecurrente(invVentasTO.getVtaRecurrente());
        invVentas.setVtaConsignacion(invVentasTO.isVtaConsignacion());
        invVentas.setVtaVendedor(invVentasTO.getVtaVendedor());
        invVentas.setCliCodigoEstablecimiento(invVentasTO.getCliCodigoEstablecimiento());
        invVentas.setVtaMontoIce(invVentasTO.getVtaMontoIce());
        invVentas.setVtaCodigoTransaccional(invVentasTO.getVtaCodigoTransaccional() != null && invVentasTO.getVtaCodigoTransaccional().equals("") ? null : invVentasTO.getVtaCodigoTransaccional());
        invVentas.setFechaUltimaValidacionSri(
                invVentasTO.getFechaUltimaValidacionSri() != null
                ? UtilsValidacion.fecha(invVentasTO.getFechaUltimaValidacionSri(), "yyyy-MM-dd") : null);
        invVentas.setVtaNegociable(invVentasTO.isVtaNegociable());
        return invVentas;
    }

    public static InvVentasTO convertirInvVentas_InvVentasTO(InvVentas invVentas) {
        InvVentasTO invVentasTO = new InvVentasTO();
        invVentasTO.setFcSecuencial(invVentasTO.getFcSecuencial());
        invVentasTO.setVtaEmpresa(invVentas.getInvVentasPK() != null ? invVentas.getInvVentasPK().getVtaEmpresa() : null);
        invVentasTO.setVtaMotivo(invVentas.getInvVentasPK() != null ? invVentas.getInvVentasPK().getVtaMotivo() : null);
        invVentasTO.setVtaNumero(invVentas.getInvVentasPK() != null ? invVentas.getInvVentasPK().getVtaNumero() : null);
        invVentasTO.setVtaPeriodo(invVentas.getInvVentasPK() != null ? invVentas.getInvVentasPK().getVtaPeriodo() : null);
        invVentasTO.setVtaDocumentoTipo(invVentas.getVtaDocumentoTipo());
        invVentasTO.setVtaDocumentoNumero(invVentas.getVtaDocumentoNumero());
        invVentasTO.setVtaFecha(UtilsValidacion.fecha(invVentas.getVtaFecha(), "yyyy-MM-dd"));
        invVentasTO.setVtaFechaVencimiento(UtilsValidacion.fecha(invVentas.getVtaFechaVencimiento(), "yyyy-MM-dd"));
        invVentasTO.setFechaUltimaValidacionSri(
                invVentas.getFechaUltimaValidacionSri() != null
                ? UtilsValidacion.fecha(invVentas.getFechaUltimaValidacionSri(), "yyyy-MM-dd") : null);
        invVentasTO.setVtaIvaVigente(invVentas.getVtaIvaVigente());
        invVentasTO.setVtaObservaciones(invVentas.getVtaObservaciones());
        invVentasTO.setVtaInformacionAdicional(invVentas.getVtaInformacionAdicional());
        invVentasTO.setVtaPendiente(invVentas.getVtaPendiente());
        invVentasTO.setVtaRevisado(invVentas.getVtaRevisado());
        invVentasTO.setVtaAnulado(invVentas.getVtaAnulado());
        invVentasTO.setVtaFormaPago(invVentas.getVtaFormaPago());
        invVentasTO.setVtaBase0(invVentas.getVtaBase0());
        invVentasTO.setVtaBaseImponible(invVentas.getVtaBaseImponible());
        invVentasTO.setVtaBaseNoObjeto(invVentas.getVtaBaseNoObjeto());
        invVentasTO.setVtaBaseExenta(invVentas.getVtaBaseExenta());
        invVentasTO.setVtaDescuentoBase0(invVentas.getVtaDescuentoBase0());
        invVentasTO.setVtaDescuentoBaseImponible(invVentas.getVtaDescuentoBaseImponible());
        invVentasTO.setVtaDescuentoBaseNoObjeto(invVentas.getVtaDescuentoBaseNoObjeto());
        invVentasTO.setVtaDescuentoBaseExenta(invVentas.getVtaDescuentoBaseExenta());
        invVentasTO.setVtaSubtotalBase0(invVentas.getVtaSubtotalBase0());
        invVentasTO.setVtaSubtotalBaseImponible(invVentas.getVtaSubtotalBaseImponible());
        invVentasTO.setVtaSubtotalBaseNoObjeto(invVentas.getVtaSubtotalBaseNoObjeto());
        invVentasTO.setVtaSubtotalBaseExenta(invVentas.getVtaSubtotalBaseExenta());
        invVentasTO.setVtaMontoIva(invVentas.getVtaMontoiva());
        invVentasTO.setVtaTotal(invVentas.getVtaTotal());
        invVentasTO.setVtaConsignacion(invVentas.isVtaConsignacion());
        invVentasTO.setVtaRecurrente(invVentas.getVtaRecurrente());
        //Campos nuevos
        invVentasTO.setVtaListaDePrecios(invVentas.getVtaListaDePrecios());
        invVentasTO.setVtaReembolso(invVentas.getVtaReembolso());
        invVentasTO.setFcBanco(invVentas.getFcBanco());
        invVentasTO.setFcCuenta(invVentas.getFcCuenta());
        invVentasTO.setFcCheque(invVentas.getFcCheque());
        invVentasTO.setFcLote(invVentas.getFcLote());
        invVentasTO.setFcTitular(invVentas.getFcTitular());
        invVentasTO.setFcSecuencial(invVentas.getFcSecuencial() != null ? invVentas.getFcSecuencial().getFcSecuencial() : null);
        invVentasTO.setProfEmpresa(invVentas.getInvProformas() != null ? invVentas.getInvProformas().getInvProformasPK().getProfEmpresa() : null);
        invVentasTO.setProfMotivo(invVentas.getInvProformas() != null ? invVentas.getInvProformas().getInvProformasPK().getProfMotivo() : null);
        invVentasTO.setProfNumero(invVentas.getInvProformas() != null ? invVentas.getInvProformas().getInvProformasPK().getProfNumero() : null);
        invVentasTO.setProfPeriodo(invVentas.getInvProformas() != null ? invVentas.getInvProformas().getInvProformasPK().getProfPeriodo() : null);

        invVentasTO.setVtaPagadoDineroElectronico(invVentas.getVtaPagadoDineroElectronico());
        invVentasTO.setVtaPagadoEfectivo(invVentas.getVtaPagadoEfectivo());
        invVentasTO.setVtaPagadoOtro(invVentas.getVtaPagadoOtro());
        invVentasTO.setVtaPagadoTarjetaCredito(invVentas.getVtaPagadoTarjetaCredito());

        invVentasTO.setBodCodigo(invVentas.getInvBodega() != null ? invVentas.getInvBodega().getInvBodegaPK().getBodCodigo() : null);
        invVentasTO.setBodEmpresa(invVentas.getInvBodega() != null ? invVentas.getInvBodega().getInvBodegaPK().getBodEmpresa() : null);
        invVentasTO.setCliCodigo(invVentas.getInvCliente() != null ? invVentas.getInvCliente().getInvClientePK().getCliCodigo() : null);
        invVentasTO.setCliEmpresa(invVentas.getInvCliente() != null ? invVentas.getInvCliente().getInvClientePK().getCliEmpresa() : null);
        invVentasTO.setSecEmpresa(invVentas.getSecEmpresa());
        invVentasTO.setSecCodigo(invVentas.getSecCodigo());
        invVentasTO.setCtaEmpresa(invVentas.getCtaEmpresa());
        invVentasTO.setCtaCodigo(invVentas.getCtaCodigo());
        invVentasTO.setConEmpresa(invVentas.getConEmpresa());
        invVentasTO.setConPeriodo(invVentas.getConPeriodo());
        invVentasTO.setConTipo(invVentas.getConTipo());
        invVentasTO.setConNumero(invVentas.getConNumero());
        invVentasTO.setUsrCodigo(invVentas.getUsrCodigo());
        invVentasTO.setUsrEmpresa(invVentas.getUsrEmpresa());
        invVentasTO.setUsrFechaInserta(UtilsValidacion.fechaDate_String(invVentas.getUsrFechaInserta()));
        invVentasTO.setUsrModifica(invVentas.getUsrModifica());
        invVentasTO.setUsrFechaModifica(invVentas.getUsrFechaModifica() != null ? UtilsValidacion.fechaDate_String(invVentas.getUsrFechaModifica()) : null);
        invVentasTO.setVtaVendedor(invVentas.getVtaVendedor());
        invVentasTO.setCliCodigoEstablecimiento(invVentas.getCliCodigoEstablecimiento());
        invVentasTO.setVtaMontoIce(invVentas.getVtaMontoIce());
        invVentasTO.setVtaCodigoTransaccional(invVentas.getVtaCodigoTransaccional() != null && invVentas.getVtaCodigoTransaccional().equals("") ? null : invVentas.getVtaCodigoTransaccional());
        invVentasTO.setVtaNegociable(invVentas.isVtaNegociable());
        return invVentasTO;
    }

    public static InvVentas convertirInvVentas_InvVentas(InvVentas invVentas) {
        InvVentas invVentasAux = new InvVentas();
        invVentasAux.setConEmpresa(invVentas.getConEmpresa());
        invVentasAux.setConNumero(invVentas.getConNumero());
        invVentasAux.setConPeriodo(invVentas.getConPeriodo());
        invVentasAux.setConTipo(invVentas.getConTipo());
        invVentasAux.setInvCliente(invVentas.getInvCliente());
        invVentasAux.setInvVentasPK(invVentas.getInvVentasPK());
        invVentasAux.setSecCodigo(invVentas.getSecCodigo());
        invVentasAux.setSecEmpresa(invVentas.getSecEmpresa());
        invVentasAux.setUsrCodigo(invVentas.getUsrCodigo());
        invVentasAux.setUsrEmpresa(invVentas.getUsrEmpresa());
        invVentasAux.setUsrFechaInserta(invVentas.getUsrFechaInserta());
        invVentasAux.setUsrModifica(invVentas.getUsrModifica());
        invVentasAux.setUsrFechaModifica(invVentas.getUsrFechaModifica());
        invVentasAux.setUsrComentario(invVentas.getUsrComentario());
        invVentasAux.setVtaAnulado(invVentas.getVtaAnulado());

        invVentasAux.setVtaBase0(invVentas.getVtaBase0());
        invVentasAux.setVtaBaseExenta(invVentas.getVtaBaseExenta());
        invVentasAux.setVtaBaseNoObjeto(invVentas.getVtaBaseNoObjeto());
        invVentasAux.setVtaBaseImponible(invVentas.getVtaBaseImponible());

        invVentasAux.setVtaDescuentoBase0(invVentas.getVtaDescuentoBase0());
        invVentasAux.setVtaDescuentoBaseImponible(invVentas.getVtaDescuentoBaseImponible());
        invVentasAux.setVtaDescuentoBaseExenta(invVentas.getVtaDescuentoBaseExenta());
        invVentasAux.setVtaDescuentoBaseNoObjeto(invVentas.getVtaDescuentoBaseNoObjeto());

        invVentasAux.setVtaSubtotalBase0(invVentas.getVtaSubtotalBase0());
        invVentasAux.setVtaSubtotalBaseImponible(invVentas.getVtaSubtotalBaseImponible());
        invVentasAux.setVtaSubtotalBaseExenta(invVentas.getVtaSubtotalBaseExenta());
        invVentasAux.setVtaSubtotalBaseNoObjeto(invVentas.getVtaSubtotalBaseNoObjeto());

        invVentasAux.setInvBodega(invVentas.getInvBodega());
        // invVentasAux.setBodCodigo(invVentas.getInvBodega().getInvBodegaPK().getBodCodigo());
        // invVentasAux.setBodEmpresa(invVentas.getBodEmpresa());
        invVentasAux.setSecEmpresa(invVentas.getSecEmpresa());
        invVentasAux.setSecCodigo(invVentas.getSecCodigo());

        invVentasAux.setCtaEmpresa(invVentas.getCtaEmpresa());
        invVentasAux.setCtaCodigo(invVentas.getCtaCodigo());

        invVentasAux.setVtaDocumentoNumero(invVentas.getVtaDocumentoNumero());
        invVentasAux.setVtaDocumentoTipo(invVentas.getVtaDocumentoTipo());
        invVentasAux.setVtaFecha(invVentas.getVtaFecha());
        invVentasAux.setVtaFechaVencimiento(invVentas.getVtaFechaVencimiento());
        invVentasAux.setVtaFormaPago(invVentas.getVtaFormaPago());
        invVentasAux.setVtaIvaVigente(invVentas.getVtaIvaVigente());
        invVentasAux.setVtaMontoiva(invVentas.getVtaMontoiva());
        invVentasAux.setVtaObservaciones(invVentas.getVtaObservaciones());
        invVentasAux.setVtaInformacionAdicional(invVentas.getVtaInformacionAdicional());
        invVentasAux.setVtaPendiente(invVentas.getVtaPendiente());
        invVentasAux.setVtaRevisado(invVentas.getVtaRevisado());
        invVentasAux.setVtaTotal(invVentas.getVtaTotal());
        invVentasAux.setVtaPagadoEfectivo(invVentas.getVtaPagadoEfectivo());
        invVentasAux.setVtaPagadoDineroElectronico(invVentas.getVtaPagadoDineroElectronico());
        invVentasAux.setVtaPagadoTarjetaCredito(invVentas.getVtaPagadoTarjetaCredito());
        invVentasAux.setVtaPagadoOtro(invVentas.getVtaPagadoOtro());
        //Campos Nuevos
        invVentasAux.setFcTitular(invVentas.getFcTitular());
        invVentasAux.setFcSecuencial(invVentas.getFcSecuencial());
        invVentasAux.setVtaListaDePrecios(invVentas.getVtaListaDePrecios());
        invVentasAux.setFcBanco(invVentas.getFcBanco());
        invVentasAux.setFcCuenta(invVentas.getFcCuenta());
        invVentasAux.setFcCheque(invVentas.getFcCheque());
        invVentasAux.setFcLote(invVentas.getFcLote());
        invVentasAux.setVtaVendedor(invVentas.getVtaVendedor());
        invVentasAux.setCliCodigoEstablecimiento(invVentas.getCliCodigoEstablecimiento());
        invVentasAux.setVtaMontoIce(invVentas.getVtaMontoIce());
        invVentasAux.setVtaCodigoTransaccional(invVentas.getVtaCodigoTransaccional());
        invVentasAux.setFechaUltimaValidacionSri(invVentas.getFechaUltimaValidacionSri());
        return invVentasAux;
    }

    public static InvProformas convertirInvProformasTO_InvProformas(InvProformasTO invProformasTO) {
        InvProformas invProformas = new InvProformas();

        /*
		 * cli_empresa character(7) NOT NULL, cli_codigo character(7) NOT NULL,
		 * usr_empresa character(7) NOT NULL, usr_codigo character(7) NOT NULL,
		 * usr_fecha_inserta timestamp without time zone NOT NULL,
         */
        invProformas.setInvProformasPK(new InvProformasPK(invProformasTO.getProfEmpresa(),
                invProformasTO.getProfPeriodo(), invProformasTO.getProfMotivo(), invProformasTO.getProfNumero()));
        // invCompras.setProvCodigo(invComprasTO.getProvCodigo());
        invProformas.setProfFecha(UtilsValidacion.fecha(invProformasTO.getProfFecha(), "yyyy-MM-dd"));
        invProformas.setProfIvaVigente(invProformasTO.getProfIvaVigente());
        invProformas.setProfObservaciones(invProformasTO.getProfObservaciones());
        invProformas.setProfPendiente(invProformasTO.getProfPendiente());
        invProformas.setProfAnulado(invProformasTO.getProfAnulado());

        invProformas.setProfBase0(invProformasTO.getProfBase0());
        invProformas.setProfBaseimponible(invProformasTO.getProfBaseImponible());

        invProformas.setProfDescuentoBase0(invProformasTO.getProfDescuentoBase0());
        invProformas.setProfDescuentoBaseimponible(invProformasTO.getProfDescuentoBaseImponible());

        invProformas.setProfSubtotalBase0(invProformasTO.getProfSubtotalBase0());
        invProformas.setProfSubtotalBaseimponible(invProformasTO.getProfSubtotalBaseImponible());

        invProformas.setProfMontoiva(invProformasTO.getProfMontoIva());
        invProformas.setProfTotal(invProformasTO.getProfTotal());
        // invProformas.setInvCliente(null);

        invProformas.setUsrEmpresa(invProformasTO.getUsrEmpresa());
        invProformas.setUsrCodigo(invProformasTO.getUsrCodigo());
        invProformas.setUsrFechaInserta(UtilsValidacion.fechaString_Date(invProformasTO.getUsrFechaInserta()));
        return invProformas;
    }

    public static InvCompras convertirInvCompras_InvCompras(InvCompras invCompras) {
        InvCompras invComprasAux = new InvCompras();
        invComprasAux.setCompBase0(invCompras.getCompBase0());
        invComprasAux.setCompBaseNoObjeto(invCompras.getCompBaseNoObjeto());
        invComprasAux.setCompBaseExenta(invCompras.getCompBaseExenta());
        invComprasAux.setCompBaseImponible(invCompras.getCompBaseImponible());
        invComprasAux.setInvBodega(invCompras.getInvBodega());
        invComprasAux.setCtaCodigo(invCompras.getCtaCodigo());
        invComprasAux.setCtaEmpresa(invCompras.getCtaEmpresa());
        invComprasAux.setCompDocumentoEmpresa(invCompras.getInvProveedor().getInvProveedorPK().getProvEmpresa());
        invComprasAux.setCompDocumentoProveedor(invCompras.getInvProveedor().getInvProveedorPK().getProvCodigo());
        invComprasAux.setCompDocumentoFormaPago(invCompras.getCompDocumentoFormaPago());
        invComprasAux.setCompDocumentoNumero(invCompras.getCompDocumentoNumero());
        invComprasAux.setCompDocumentoTipo(invCompras.getCompDocumentoTipo());
        invComprasAux.setCompFecha(invCompras.getCompFecha());
        invComprasAux.setCompFechaVencimiento(invCompras.getCompFechaVencimiento());
        invComprasAux.setCompFormaPago(invCompras.getCompFormaPago());
        invComprasAux.setCompIvaVigente(invCompras.getCompIvaVigente());
        invComprasAux.setCompMontoIva(invCompras.getCompMontoIva());
        invComprasAux.setCompObservaciones(invCompras.getCompObservaciones());
        invComprasAux.setCompElectronica(invCompras.getCompElectronica());
        invComprasAux.setCompActivoFijo(invCompras.getCompActivoFijo());
        invComprasAux.setCompImportacion(invCompras.getCompImportacion());
        if (invCompras.getInvBodega() != null) {
            invComprasAux.setInvBodega(new InvBodega(invCompras.getInvBodega().getInvBodegaPK().getBodEmpresa(), invCompras.getInvBodega().getInvBodegaPK().getBodCodigo()));
        }
        invComprasAux.setCompRetencionAsumida(invCompras.getCompRetencionAsumida());

        invComprasAux.setCompPendiente(invCompras.getCompPendiente());
        invComprasAux.setCompRevisado(invCompras.getCompRevisado());
        invComprasAux.setCompAnulado(invCompras.getCompAnulado());

        invComprasAux.setCompSaldo(invCompras.getCompSaldo());
        invComprasAux.setCompIce(invCompras.getCompIce());
        invComprasAux.setCompPropina(invCompras.getCompPropina());
        invComprasAux.setCompOtrosImpuestos(invCompras.getCompOtrosImpuestos());
        invComprasAux.setCompTotal(invCompras.getCompTotal());
        invComprasAux.setCompValorRetenido(invCompras.getCompValorRetenido());
        invComprasAux.setConEmpresa(invCompras.getConEmpresa());
        invComprasAux.setConPeriodo(invCompras.getConPeriodo());
        invComprasAux.setConTipo(invCompras.getConTipo());
        invComprasAux.setConNumero(invCompras.getConNumero());
        invComprasAux.setInvComprasPK(invCompras.getInvComprasPK());
        invComprasAux.setInvProveedor(invCompras.getInvProveedor());
        invComprasAux.setSecEmpresa(invCompras.getSecEmpresa());
        invComprasAux.setSecCodigo(invCompras.getSecCodigo());
        invComprasAux.setEmpCodigo(invCompras.getEmpCodigo());
        invComprasAux.setUsrCodigo(invCompras.getUsrCodigo());
        //ORDEN DE COMPRA
        invComprasAux.setOcEmpresa(invCompras.getOcEmpresa());
        invComprasAux.setOcMotivo(invCompras.getOcMotivo());
        invComprasAux.setOcNumero(invCompras.getOcNumero());
        invComprasAux.setOcSector(invCompras.getOcSector());
        invComprasAux.setCompProgramada(invCompras.isCompProgramada());
        invComprasAux.setUsrFechaInserta(invCompras.getUsrFechaInserta());
        invComprasAux.setCompUsuarioApruebaPago(invCompras.getCompUsuarioApruebaPago());
        invComprasAux.setCompFechaApruebaPago(invCompras.getCompFechaApruebaPago());
        invComprasAux.setCompTransportistaRuc(invCompras.getCompTransportistaRuc());
        invComprasAux.setFechaUltimaValidacionSri(invCompras.getFechaUltimaValidacionSri());
        invComprasAux.setFpSecuencial(invCompras.getFpSecuencial());
        invComprasAux.setCompSaldoImportado(invCompras.isCompSaldoImportado());
        invComprasAux.setOcOrdenPedido(invCompras.getOcOrdenPedido());
        invComprasAux.setCompClaveAccesoExterna(invCompras.getCompClaveAccesoExterna());
        invComprasAux.setCompImbFacturado(invCompras.isCompImbFacturado());
        invComprasAux.setCompGuiaCompra(invCompras.getCompGuiaCompra());
        invComprasAux.setCompEsImb(invCompras.getCompEsImb());
        return invComprasAux;
    }

    public static InvComprasDetalle convertirInvCompraDetalle_InvCompraDetalle(InvComprasDetalle invCompraDetalle) {
        InvComprasDetalle invComprasDetalle = new InvComprasDetalle();
        invComprasDetalle.setDetSecuencial(invCompraDetalle.getDetSecuencial());
        invComprasDetalle.setDetOrden(invCompraDetalle.getDetOrden());
        invComprasDetalle.setDetPendiente(invCompraDetalle.getDetPendiente());
        invComprasDetalle.setDetConfirmado(invCompraDetalle.getDetConfirmado());
        invComprasDetalle.setDetCantidad(invCompraDetalle.getDetCantidad());
        invComprasDetalle.setDetPrecio(invCompraDetalle.getDetPrecio());
        invComprasDetalle.setDetPorcentajeDescuento(invCompraDetalle.getDetPorcentajeDescuento());

        invComprasDetalle.setDetOtrosImpuestos(invCompraDetalle.getDetOtrosImpuestos());
        invComprasDetalle.setDetValorPromedio(invCompraDetalle.getDetValorPromedio());
        invComprasDetalle.setDetValorUltimaCompra(invCompraDetalle.getDetValorUltimaCompra());
        invComprasDetalle.setDetSaldo(invCompraDetalle.getDetSaldo());
        invComprasDetalle.setSecEmpresa(invCompraDetalle.getSecEmpresa());
        invComprasDetalle.setSecCodigo(invCompraDetalle.getSecCodigo());
        invComprasDetalle.setPisEmpresa(invCompraDetalle.getPisEmpresa());
        invComprasDetalle.setPisSector(invCompraDetalle.getPisSector());
        invComprasDetalle.setPisNumero(invCompraDetalle.getPisNumero());
        invComprasDetalle.setInvBodega(invCompraDetalle.getInvBodega());
        invComprasDetalle.setInvCompras(invCompraDetalle.getInvCompras());
        invComprasDetalle.setInvProducto(invCompraDetalle.getInvProducto());
        invComprasDetalle.setInvPedidosOrdenCompraDetalle(invCompraDetalle.getInvPedidosOrdenCompraDetalle());
        return invComprasDetalle;
    }

    public static InvVentasDetalle convertirInvVentaDetalle_InvVentaDetalle(InvVentasDetalle invVentaDetalle) {
        InvVentasDetalle invVentasDetalle = new InvVentasDetalle();
        invVentasDetalle.setDetSecuencial(invVentaDetalle.getDetSecuencial());
        invVentasDetalle.setDetOrden(invVentaDetalle.getDetOrden());
        invVentasDetalle.setDetPendiente(invVentaDetalle.getDetPendiente());
        invVentasDetalle.setDetPorcentajeDescuento(invVentaDetalle.getDetPorcentajeDescuento());
        invVentasDetalle.setDetCantidad(invVentaDetalle.getDetCantidad());
        invVentasDetalle.setDetPrecio(invVentaDetalle.getDetPrecio());

        // invVentasDetalle.setDetValorPromedio(invVentasDetalle.getDetValorPromedio());
        // invVentasDetalle.setDetValorUltimaCompra(invVentasDetalle.getDetValorUltimaCompra());
        invVentasDetalle.setDetPorcentajeRecargo(invVentaDetalle.getDetPorcentajeRecargo());
        invVentasDetalle.setSecEmpresa(invVentaDetalle.getSecEmpresa());
        invVentasDetalle.setSecCodigo(invVentaDetalle.getSecCodigo());
        invVentasDetalle.setPisEmpresa(invVentaDetalle.getPisEmpresa());
        invVentasDetalle.setPisSector(invVentaDetalle.getPisSector());
        invVentasDetalle.setPisNumero(invVentaDetalle.getPisNumero());
        invVentasDetalle.setInvBodega(invVentaDetalle.getInvBodega());
        invVentasDetalle.setInvVentas(invVentaDetalle.getInvVentas());
        invVentasDetalle.setInvProducto(invVentaDetalle.getInvProducto());
        invVentasDetalle.setProComplementario(invVentaDetalle.getProComplementario());
        invVentasDetalle.setDetObservaciones(invVentasDetalle.getDetObservaciones());

        return invVentasDetalle;
    }

    public static InvConsumosDetalle convertirInvConsumoDetalle_InvConsumoDetalle(
            InvConsumosDetalle invConsumoDetalle) {
        InvConsumosDetalle invConsumosDetalle = new InvConsumosDetalle();
        invConsumosDetalle.setDetSecuencial(invConsumoDetalle.getDetSecuencial());
        invConsumosDetalle.setDetOrden(invConsumoDetalle.getDetOrden());
        invConsumosDetalle.setDetCantidad(invConsumoDetalle.getDetCantidad());

        // invConsumosDetalle.setDetValorPromedio(invConsumosDetalle.getDetValorPromedio());
        // invConsumosDetalle.setDetValorUltimaCompra(invConsumosDetalle.getDetValorUltimaCompra());
        invConsumosDetalle.setSecEmpresa(invConsumoDetalle.getSecEmpresa());
        invConsumosDetalle.setSecCodigo(invConsumoDetalle.getSecCodigo());
        invConsumosDetalle.setPisEmpresa(invConsumoDetalle.getPisEmpresa());
        invConsumosDetalle.setPisSector(invConsumoDetalle.getPisSector());
        invConsumosDetalle.setPisNumero(invConsumoDetalle.getPisNumero());
        invConsumosDetalle.setInvBodega(invConsumoDetalle.getInvBodega());
        invConsumosDetalle.setInvConsumos(invConsumoDetalle.getInvConsumos());
        invConsumosDetalle.setInvProducto(invConsumoDetalle.getInvProducto());
        return invConsumosDetalle;
    }

    public static InvTransferenciasDetalle convertirInvTransferenciasDetalle_InvTransferenciasDetalle(
            InvTransferenciasDetalle invTransferenciaDetalle) {
        InvTransferenciasDetalle invTransferenciasDetalle = new InvTransferenciasDetalle();
        invTransferenciasDetalle.setDetSecuencial(invTransferenciaDetalle.getDetSecuencial());
        invTransferenciasDetalle.setDetOrden(invTransferenciaDetalle.getDetOrden());
        invTransferenciasDetalle.setDetOrden(invTransferenciaDetalle.getDetOrden());
        invTransferenciasDetalle.setDetPendiente(invTransferenciaDetalle.getDetPendiente());
        invTransferenciasDetalle.setDetConfirmado(invTransferenciaDetalle.getDetConfirmado());
        invTransferenciasDetalle.setDetCantidad(invTransferenciasDetalle.getDetCantidad());
        invTransferenciasDetalle.setDetOrigenValorPromedio(invTransferenciasDetalle.getDetOrigenValorPromedio());
        invTransferenciasDetalle.setDetOrigenValorUltimaCompra(invTransferenciasDetalle.getDetOrigenValorUltimaCompra());
        invTransferenciasDetalle.setDetOrigenSaldo(invTransferenciasDetalle.getDetOrigenSaldo());
        invTransferenciasDetalle.setDetDestinoValorPromedio(invTransferenciasDetalle.getDetDestinoValorPromedio());
        invTransferenciasDetalle.setDetDestinoValorUltimaCompra(invTransferenciasDetalle.getDetDestinoValorUltimaCompra());
        invTransferenciasDetalle.setDetDestinoSaldo(invTransferenciasDetalle.getDetDestinoSaldo());
        invTransferenciasDetalle.setSecOrigenEmpresa(invTransferenciaDetalle.getSecOrigenEmpresa());
        invTransferenciasDetalle.setSecOrigenCodigo(invTransferenciaDetalle.getSecOrigenCodigo());
        invTransferenciasDetalle.setSecDestinoEmpresa(invTransferenciaDetalle.getSecDestinoEmpresa());
        invTransferenciasDetalle.setSecDestinoCodigo(invTransferenciaDetalle.getSecDestinoCodigo());
        invTransferenciasDetalle.setInvTransferencias(invTransferenciaDetalle.getInvTransferencias());
        invTransferenciasDetalle.setInvProducto(invTransferenciaDetalle.getInvProducto());
        invTransferenciasDetalle.setInvBodega(invTransferenciaDetalle.getInvBodega());
        invTransferenciasDetalle.setInvBodega1(invTransferenciaDetalle.getInvBodega1());
        invTransferenciasDetalle.setInvTransferenciasDetalleSeriesList(invTransferenciaDetalle.getInvTransferenciasDetalleSeriesList());
        return invTransferenciasDetalle;
    }

    public static InvComprasDetalle convertirInvComprasDetalleTO_InvComprasDetalle(InvComprasDetalleTO invComprasDetalleTO) {
        InvComprasDetalle invComprasDetalle = new InvComprasDetalle();
        invComprasDetalle.setDetSecuencial(invComprasDetalleTO.getDetSecuencia());
        if (invComprasDetalleTO.getDetSecuencialOrdenCompra() != null) {
            InvPedidosOrdenCompraDetalle detalle = new InvPedidosOrdenCompraDetalle();
            detalle.setDetSecuencialOrdenCompra(invComprasDetalleTO.getDetSecuencialOrdenCompra());
            invComprasDetalle.setInvPedidosOrdenCompraDetalle(detalle);
        }
        invComprasDetalle.setDetObservaciones(invComprasDetalleTO.getDetObservaciones());
        invComprasDetalle.setDetOrden(invComprasDetalleTO.getDetOrden());
        invComprasDetalle.setDetPendiente(invComprasDetalleTO.getDetPendiente());
        invComprasDetalle.setDetConfirmado(invComprasDetalleTO.getDetConfirmado());

        // invComprasDetalle.setDetFecha(UtilsValidacion.fecha(invComprasDetalleTO.getDetFecha()));
        invComprasDetalle.setDetCantidad(invComprasDetalleTO.getDetCantidad());
        invComprasDetalle.setDetPrecio(invComprasDetalleTO.getDetPrecio());

        // invComprasDetalle.setDetValorPromedio(new
        // java.math.BigDecimal("0.00"));
        // invComprasDetalle.setDetValorUltimaCompra(new
        // java.math.BigDecimal("0.00"));
        invComprasDetalle.setDetValorUltimaCompra(invComprasDetalleTO.getDetValorUltimaCompra());
        invComprasDetalle.setDetValorPromedio(invComprasDetalleTO.getDetValorPromedio());
        invComprasDetalle.setDetOtrosImpuestos(invComprasDetalleTO.getDetOtrosImpuestos());
        invComprasDetalle.setDetSaldo(invComprasDetalleTO.getDetSaldo());

        invComprasDetalle.setDetPorcentajeDescuento(invComprasDetalleTO.getDetPorcentajeDescuento());
        // invComprasDetalle.setInvBodega(new
        // InvBodega(invComprasDetalleTO.getBodEmpresa(),
        // invComprasDetalleTO.getBodCodigo()));

        invComprasDetalle.setDetIce(invComprasDetalleTO.getDetIce());
        invComprasDetalle.setProPrecio1(invComprasDetalleTO.getDetPrecio1());
        invComprasDetalle.setProPrecio2(invComprasDetalleTO.getDetPrecio2());
        invComprasDetalle.setProPrecio3(invComprasDetalleTO.getDetPrecio3());
        invComprasDetalle.setProPrecio4(invComprasDetalleTO.getDetPrecio4());
        invComprasDetalle.setProPrecio5(invComprasDetalleTO.getDetPrecio5());
        invComprasDetalle.setPrdEquipoControl(invComprasDetalleTO.getPrdEquipoControl() != null && invComprasDetalleTO.getPrdEquipoControl().getPrdEquipoControlPK().getEcCodigo() == "" ? null : invComprasDetalleTO.getPrdEquipoControl());
        invComprasDetalle.setSecEmpresa(invComprasDetalleTO.getSecEmpresa());
        invComprasDetalle.setSecCodigo(invComprasDetalleTO.getSecCodigo());
        if (invComprasDetalleTO.getPisEmpresa() != null && invComprasDetalleTO.getPisSector() != null && invComprasDetalleTO.getPisNumero() != null) {
            invComprasDetalle.setPisEmpresa(invComprasDetalleTO.getPisEmpresa());
            invComprasDetalle.setPisSector(invComprasDetalleTO.getPisSector());
            invComprasDetalle.setPisNumero(invComprasDetalleTO.getPisNumero());
        } else {
            invComprasDetalle.setPisEmpresa(null);
            invComprasDetalle.setPisSector(null);
            invComprasDetalle.setPisNumero(null);
        }

        if (invComprasDetalleTO.getInvComprasDetalleSeriesListTO() != null && invComprasDetalleTO.getInvComprasDetalleSeriesListTO().size() > 0) {
            List<InvComprasDetalleSeries> listaSeries = new ArrayList<>();
            for (int i = 0; i < invComprasDetalleTO.getInvComprasDetalleSeriesListTO().size(); i++) {
                InvComprasDetalleSeries serie = new InvComprasDetalleSeries();
                serie.setDetNumeroSerie(invComprasDetalleTO.getInvComprasDetalleSeriesListTO().get(i).getDetNumeroSerie());
                serie.setDetSecuencial(invComprasDetalleTO.getInvComprasDetalleSeriesListTO().get(i).getDetSecuencial());
                serie.setDetSecuencialCompra(invComprasDetalle);
                listaSeries.add(serie);
            }
            invComprasDetalle.setInvComprasDetalleSeriesList(listaSeries);
        }

        invComprasDetalle.setProCreditoTributario(invComprasDetalleTO.getDetCreditoTributario());

        return invComprasDetalle;
    }

    public static InvGuiaRemisionDetalle convertirInvGuiaRemisionDetalleTO_InvGuiaRemisionDetalle(InvGuiaRemisionDetalleTO invGuiaRemisionDetalleTO) {
        InvGuiaRemisionDetalle invGuiaRemisionDetalle = new InvGuiaRemisionDetalle();
        invGuiaRemisionDetalle.setDetSecuencial(invGuiaRemisionDetalleTO.getDetSecuencia());
        invGuiaRemisionDetalle.setDetOrden(invGuiaRemisionDetalleTO.getDetOrden());
        invGuiaRemisionDetalle.setDetCantidad(invGuiaRemisionDetalleTO.getDetCantidad());
        invGuiaRemisionDetalle.setNombreProducto(invGuiaRemisionDetalleTO.getNombreProducto());
        return invGuiaRemisionDetalle;
    }

    public static InvClientesVentasDetalle convertirInvClientesVentasDetalleTO_InvClientesVentasDetalle(InvClientesVentasDetalleTO invClientesVentasDetalleTO) {
        InvClientesVentasDetalle invClientesVentasDetalle = new InvClientesVentasDetalle();
        invClientesVentasDetalle.setDetSecuencial(invClientesVentasDetalleTO.getDetSecuencia() == 0 ? 0 : invClientesVentasDetalleTO.getDetSecuencia());
        invClientesVentasDetalle.setDetOrden(invClientesVentasDetalleTO.getDetOrden());
        invClientesVentasDetalle.setDetGrupo(invClientesVentasDetalleTO.getDetGrupo());
        invClientesVentasDetalle.setCliCodigoEstablecimiento(invClientesVentasDetalleTO.getCliCodigoEstablecimiento());
        invClientesVentasDetalle.setDetCantidad(invClientesVentasDetalleTO.getDetCantidad());
        invClientesVentasDetalle.setDetPrecio(invClientesVentasDetalleTO.getDetPrecio());
        if (invClientesVentasDetalleTO.getBodCodigo() != null && !invClientesVentasDetalleTO.getBodCodigo().equals("")) {
            invClientesVentasDetalle.setBodEmpresa(invClientesVentasDetalleTO.getBodEmpresa());
            invClientesVentasDetalle.setBodCodigo(invClientesVentasDetalleTO.getBodCodigo());
        } else {
            invClientesVentasDetalle.setBodEmpresa(null);
            invClientesVentasDetalle.setBodCodigo(null);
        }
        invClientesVentasDetalle.setDetPorcentajeRecargo(invClientesVentasDetalleTO.getDetPorcentajeRecargo());
        invClientesVentasDetalle.setDetPorcentajeDescuento(invClientesVentasDetalleTO.getDetPorcentajeDescuento());
        invClientesVentasDetalle.setProNombre(invClientesVentasDetalleTO.getProNombre());
        invClientesVentasDetalle.setInvCliente(new InvCliente(invClientesVentasDetalleTO.getCliEmpresa(), invClientesVentasDetalleTO.getCliCodigo()));
        invClientesVentasDetalle.setInvProducto(new InvProducto(invClientesVentasDetalleTO.getProEmpresa(), invClientesVentasDetalleTO.getProCodigoPrincipal()));
        if (invClientesVentasDetalleTO.getDetContratoSecuencial() != null && invClientesVentasDetalleTO.getDetContratoSecuencial() > 0) { //cuando hay contrato debo borrar lo demas, solo es visual
            invClientesVentasDetalle.setInvClienteContrato(new InvClienteContrato(invClientesVentasDetalleTO.getDetContratoSecuencial()));
            invClientesVentasDetalle.setBodEmpresa(null);
            invClientesVentasDetalle.setBodCodigo(null);
            invClientesVentasDetalle.setCliCodigoEstablecimiento(null);
            invClientesVentasDetalle.setDetGrupo(invClientesVentasDetalleTO.getDetContratoSecuencial());
        }
        return invClientesVentasDetalle;
    }

    public static List<InvVentasDetalleTO> convertirListInvClientesVentasDetalle_InvVentasDetalleTO(List<InvClientesVentasDetalle> detalles, InvVentasTO ventaTO, String descripcionProducto) {
        List<InvVentasDetalleTO> listadoDetallesTO = new ArrayList<>();
        String bodega = null;
        for (InvClientesVentasDetalle detalle : detalles) {//poner bodega
            if (detalle.getBodCodigo() != null && !detalle.getBodCodigo().equals("")) {
                bodega = detalle.getBodCodigo();
            }
        }
        if (bodega != null) {
            ventaTO.setBodCodigo(bodega);
        }
        for (InvClientesVentasDetalle detalle : detalles) {
            InvVentasDetalleTO invVentasDetalleTO = new InvVentasDetalleTO();
            invVentasDetalleTO.setBodCodigo(ventaTO.getBodCodigo());
            invVentasDetalleTO.setBodEmpresa(ventaTO.getBodEmpresa());
            invVentasDetalleTO.setDetCantidad(detalle.getDetCantidad());
            invVentasDetalleTO.setDetOrden(detalle.getDetOrden());
            invVentasDetalleTO.setDetPendiente(false);
            invVentasDetalleTO.setDetPorcentajeDescuento(detalle.getDetPorcentajeDescuento());
            invVentasDetalleTO.setDetPorcentajeRecargo(detalle.getDetPorcentajeRecargo());
            invVentasDetalleTO.setDetPrecio(detalle.getDetPrecio());
            invVentasDetalleTO.setDetSecuencia(detalle.getDetSecuencial());
            invVentasDetalleTO.setProCodigoPrincipal(detalle.getInvProducto().getInvProductoPK().getProCodigoPrincipal());
            invVentasDetalleTO.setProEmpresa(detalle.getInvProducto().getInvProductoPK().getProEmpresa());
            invVentasDetalleTO.setProEstadoIva(detalle.getInvProducto().getProIva());
            invVentasDetalleTO.setProMedida(detalle.getInvProducto().getInvProductoMedida().getMedDetalle());
            if (descripcionProducto != null && !descripcionProducto.equals("")) {
                invVentasDetalleTO.setProNombre(detalle.getProNombre() + " - " + descripcionProducto);
            } else {
                invVentasDetalleTO.setProNombre(detalle.getProNombre());
            }
            invVentasDetalleTO.setProTipo(detalle.getInvProducto().getInvProductoTipo().getTipDetalle());
            invVentasDetalleTO.setVtaEmpresa(ventaTO.getVtaEmpresa());
            invVentasDetalleTO.setVtaMotivo(ventaTO.getVtaMotivo());
            invVentasDetalleTO.setVtaNumero(ventaTO.getVtaNumero());
            invVentasDetalleTO.setVtaPeriodo(ventaTO.getVtaPeriodo());
            invVentasDetalleTO.setSecCodigo(ventaTO.getSecCodigo());
            invVentasDetalleTO.setSecEmpresa(ventaTO.getSecEmpresa());
            listadoDetallesTO.add(invVentasDetalleTO);
        }
        return listadoDetallesTO;
    }

    public static InvVentasDetalle convertirInvVentasDetalleTO_InvVentasDetalle(InvVentasDetalleTO invVentasDetalleTO) {

        InvVentasDetalle invVentasDetalle = new InvVentasDetalle();
        invVentasDetalle
                .setDetSecuencial(invVentasDetalleTO.getDetSecuencia() == 0 ? 0 : invVentasDetalleTO.getDetSecuencia());
        invVentasDetalle.setDetOrden(invVentasDetalleTO.getDetOrden());
        invVentasDetalle.setDetPendiente(invVentasDetalleTO.getDetPendiente());
        invVentasDetalle.setDetCantidad(invVentasDetalleTO.getDetCantidad());
        invVentasDetalle.setDetPrecio(invVentasDetalleTO.getDetPrecio());
        invVentasDetalle.setDetParcial(invVentasDetalleTO.getDetParcial());
        invVentasDetalle.setDetPorcentajeRecargo(invVentasDetalleTO.getDetPorcentajeRecargo());
        invVentasDetalle.setDetPorcentajeDescuento(invVentasDetalleTO.getDetPorcentajeDescuento());
        invVentasDetalle.setDetValorPromedio(invVentasDetalleTO.getDetValorPromedio());

        invVentasDetalle.setDetValorUltimaCompra(invVentasDetalleTO.getDetValorUltimaCompra());

        invVentasDetalle.setDetSaldo(invVentasDetalleTO.getDetSaldo());
        // invVentasDetalle.setInvBodega(new
        // InvBodega(invVentasDetalleTO.getBodEmpresa(),
        // invVentasDetalleTO.getBodCodigo()));
        invVentasDetalle.setProNombre(invVentasDetalleTO.getProNombre());
        // invVentasDetalle.setInvProducto(new
        // InvProducto(invVentasDetalleTO.getProEmpresa(),
        // invVentasDetalleTO.getProCodigoPrincipal()));
        // invVentasDetalle.setProCreditoTributario(invVentasDetalleTO.getProCreditoTributario());
        invVentasDetalle.setSecEmpresa(invVentasDetalleTO.getSecEmpresa());
        invVentasDetalle.setSecCodigo(invVentasDetalleTO.getSecCodigo().trim());
        invVentasDetalle.setDetMontoIce(invVentasDetalleTO.getDetMontoIce());
        invVentasDetalle.setIceCodigo(invVentasDetalleTO.getIceCodigo());
        invVentasDetalle.setIcePorcentaje(invVentasDetalleTO.getIcePorcentaje());
        invVentasDetalle.setIceTarifaFija(invVentasDetalleTO.getIceTarifaFija());
        invVentasDetalle.setProComplementario(invVentasDetalleTO.getProComplementario());
        invVentasDetalle.setDetObservaciones(invVentasDetalleTO.getDetObservaciones());
        invVentasDetalle.setDetEmpaque(invVentasDetalleTO.getDetEmpaque());
        invVentasDetalle.setDetEmpaqueCantidad(invVentasDetalleTO.getDetEmpaqueCantidad());
        invVentasDetalle.setDetConversionPesoNeto(invVentasDetalleTO.getDetConversionPesoNeto());

        if (invVentasDetalleTO.getPisEmpresa() != null && invVentasDetalleTO.getPisSector() != null
                && invVentasDetalleTO.getPisNumero() != null) {
            invVentasDetalle.setPisEmpresa(invVentasDetalleTO.getPisEmpresa());
            invVentasDetalle.setPisSector(invVentasDetalleTO.getPisSector());
            invVentasDetalle.setPisNumero(invVentasDetalleTO.getPisNumero());
        } else {
            invVentasDetalle.setPisEmpresa(null);
            invVentasDetalle.setPisSector(null);
            invVentasDetalle.setPisNumero(null);
        }

        if (invVentasDetalleTO.getInvVentasDetalleSeriesList() != null && !invVentasDetalleTO.getInvVentasDetalleSeriesList().isEmpty()) {
            List<InvVentasDetalleSeries> series = new ArrayList<>();
            for (InvVentasDetalleSeriesTO serieTO : invVentasDetalleTO.getInvVentasDetalleSeriesList()) {
                InvVentasDetalleSeries serie = new InvVentasDetalleSeries();
                serie.setDetNumeroSerie(serieTO.getDetNumeroSerie());
                serie.setDetSecuencial(serieTO.getDetSecuencial());
                serie.setDetSecuencialVenta(invVentasDetalle);
                series.add(serie);
            }
            invVentasDetalle.setInvVentasDetalleSeriesList(series);
        }

        return invVentasDetalle;
    }

    public static InvVentasDetalleTO convertirInvVentasDetalle_InvVentasDetalleTO(InvVentasDetalle invVentasDetalle) {

        InvVentasDetalleTO invVentasDetalleTO = new InvVentasDetalleTO();
        invVentasDetalleTO.setDetSecuencia(invVentasDetalle.getDetSecuencial());
        invVentasDetalleTO.setDetOrden(invVentasDetalle.getDetOrden());
        invVentasDetalleTO.setDetPendiente(invVentasDetalle.getDetPendiente());
        invVentasDetalleTO.setDetCantidad(invVentasDetalle.getDetCantidad());
        invVentasDetalleTO.setDetPrecio(invVentasDetalle.getDetPrecio());
        invVentasDetalleTO.setDetParcial(invVentasDetalle.getDetParcial());
        invVentasDetalleTO.setDetPorcentajeRecargo(invVentasDetalle.getDetPorcentajeRecargo());
        invVentasDetalleTO.setDetPorcentajeDescuento(invVentasDetalle.getDetPorcentajeDescuento());
        invVentasDetalleTO.setDetValorPromedio(invVentasDetalle.getDetValorPromedio());
        invVentasDetalleTO.setDetValorUltimaCompra(invVentasDetalle.getDetValorUltimaCompra());
        invVentasDetalleTO.setDetSaldo(invVentasDetalle.getDetSaldo());
        invVentasDetalleTO.setBodCodigo(invVentasDetalle.getInvBodega() != null ? invVentasDetalle.getInvBodega().getInvBodegaPK().getBodCodigo() : null);
        invVentasDetalleTO.setBodEmpresa(invVentasDetalle.getInvBodega() != null ? invVentasDetalle.getInvBodega().getInvBodegaPK().getBodEmpresa() : null);
        invVentasDetalleTO.setProNombre(invVentasDetalle.getProNombre());
        invVentasDetalleTO.setProCodigoPrincipal(invVentasDetalle.getInvProducto() != null ? invVentasDetalle.getInvProducto().getInvProductoPK().getProCodigoPrincipal() : null);
        invVentasDetalleTO.setProCreditoTributario(invVentasDetalle.getInvProducto() != null ? invVentasDetalle.getInvProducto().getProCreditoTributario() : null);
        invVentasDetalleTO.setProEmpresa(invVentasDetalle.getInvProducto() != null ? invVentasDetalle.getInvProducto().getInvProductoPK().getProEmpresa() : null);
        invVentasDetalleTO.setProEstadoIva(invVentasDetalle.getInvProducto() != null ? invVentasDetalle.getInvProducto().getProIva() : null);
        invVentasDetalleTO.setProMedida(invVentasDetalle.getInvProducto() != null ? invVentasDetalle.getInvProducto().getInvProductoMedida().getMedDetalle() : null);
        invVentasDetalleTO.setProTipo(invVentasDetalle.getInvProducto() != null ? invVentasDetalle.getInvProducto().getInvProductoTipo().getTipDetalle() : null);
        invVentasDetalleTO.setConversion(invVentasDetalle.getInvProducto() != null ? invVentasDetalle.getInvProducto().getProFactorCajaSacoBulto() : null);
        invVentasDetalleTO.setSecEmpresa(invVentasDetalle.getSecEmpresa());
        invVentasDetalleTO.setSecCodigo(invVentasDetalle.getSecCodigo());
        invVentasDetalleTO.setDetMontoIce(invVentasDetalle.getDetMontoIce());
        invVentasDetalleTO.setIceCodigo(invVentasDetalle.getIceCodigo());
        invVentasDetalleTO.setIcePorcentaje(invVentasDetalle.getIcePorcentaje());
        invVentasDetalleTO.setIceTarifaFija(invVentasDetalle.getIceTarifaFija());
        invVentasDetalleTO.setProComplementario(invVentasDetalle.getProComplementario());
        invVentasDetalleTO.setDetObservaciones(invVentasDetalle.getDetObservaciones());
        invVentasDetalleTO.setDetConversionPesoNeto(invVentasDetalle.getDetConversionPesoNeto());
        invVentasDetalleTO.setDetEmpaque(invVentasDetalle.getDetEmpaque());
        invVentasDetalleTO.setDetEmpaqueCantidad(invVentasDetalle.getDetEmpaqueCantidad());

        if (invVentasDetalle.getInvVentas() != null) {
            invVentasDetalleTO.setVtaEmpresa(invVentasDetalle.getInvVentas().getInvVentasPK().getVtaEmpresa());
            invVentasDetalleTO.setVtaMotivo(invVentasDetalle.getInvVentas().getInvVentasPK().getVtaMotivo());
            invVentasDetalleTO.setVtaNumero(invVentasDetalle.getInvVentas().getInvVentasPK().getVtaNumero());
            invVentasDetalleTO.setVtaPeriodo(invVentasDetalle.getInvVentas().getInvVentasPK().getVtaPeriodo());
        }
        if (invVentasDetalle.getPisEmpresa() != null && invVentasDetalle.getPisSector() != null && invVentasDetalle.getPisNumero() != null) {
            invVentasDetalleTO.setPisEmpresa(invVentasDetalle.getPisEmpresa());
            invVentasDetalleTO.setPisSector(invVentasDetalle.getPisSector());
            invVentasDetalleTO.setPisNumero(invVentasDetalle.getPisNumero());
        } else {
            invVentasDetalleTO.setPisEmpresa(null);
            invVentasDetalleTO.setPisSector(null);
            invVentasDetalleTO.setPisNumero(null);
            invVentasDetalleTO.setPisNumeroLiq(null);
            invVentasDetalleTO.setSectorLiq(null);
        }

        if (invVentasDetalle.getInvVentasDetalleSeriesList() != null && !invVentasDetalle.getInvVentasDetalleSeriesList().isEmpty()) {
            List<InvVentasDetalleSeriesTO> series = new ArrayList<>();
            for (InvVentasDetalleSeries serie : invVentasDetalle.getInvVentasDetalleSeriesList()) {
                InvVentasDetalleSeriesTO serieTO = new InvVentasDetalleSeriesTO();
                serieTO.setDetNumeroSerie(serie.getDetNumeroSerie());
                serieTO.setDetSecuencial(serie.getDetSecuencial());
                serieTO.setDetSecuencialVenta(invVentasDetalle.getDetSecuencial());
                series.add(serieTO);
            }
            invVentasDetalleTO.setInvVentasDetalleSeriesList(series);
        }

        return invVentasDetalleTO;
    }

    public static InvProformasDetalle convertirInvProformasDetalleTO_InvProformasDetalle(
            InvProformasDetalleTO invProformasDetalleTO) {
        InvProformasDetalle invProformasDetalle = new InvProformasDetalle();
        invProformasDetalle.setDetSecuencial(invProformasDetalleTO.getDetSecuencia());
        invProformasDetalle.setDetOrden(invProformasDetalleTO.getDetOrden());
        invProformasDetalle.setDetCantidad(invProformasDetalleTO.getDetCantidad());
        invProformasDetalle.setDetPrecio(invProformasDetalleTO.getDetPrecio());

        invProformasDetalle.setDetPorcentajeRecargo(invProformasDetalleTO.getDetPorcentajeRecargo());
        invProformasDetalle.setDetPorcentajeDescuento(invProformasDetalleTO.getDetPorcentajeDescuento());
        invProformasDetalle.setProNombre(invProformasDetalleTO.getProNombre());
        return invProformasDetalle;
    }

    public static InvConsumos convertirInvConsumosTO_InvConsumos(InvConsumosTO invConsumosTO) {
        InvConsumos invConsumos = new InvConsumos();
        invConsumos.setInvConsumosPK(new InvConsumosPK(invConsumosTO.getConsEmpresa(), invConsumosTO.getConsPeriodo(),
                invConsumosTO.getConsMotivo(), invConsumosTO.getConsNumero()));
        invConsumos.setConsReferencia(invConsumosTO.getConsReferencia());
        invConsumos.setConsFecha(UtilsValidacion.fecha(invConsumosTO.getConsFecha(), "yyyy-MM-dd"));
        invConsumos.setConsObservaciones(invConsumosTO.getConsObservaciones());
        invConsumos.setConsPendiente(invConsumosTO.getConsPendiente());
        invConsumos.setConsRevisado(invConsumosTO.getConsRevisado());
        invConsumos.setConsAnulado(invConsumosTO.getConsAnulado());
        invConsumos.setUsrEmpresa(invConsumosTO.getConsEmpresa());
        invConsumos.setUsrCodigo(invConsumosTO.getUsrCodigo());
        invConsumos.setUsrFechaInserta(UtilsValidacion.fechaString_Date(invConsumosTO.getUsrFechaInserta()));
        invConsumos.setProCantidad(invConsumosTO.getProCantidad());

        if (invConsumosTO.getBodCodigo() != null) {
            invConsumos.setInvBodega(new InvBodega(invConsumosTO.getBodEmpresa(), invConsumosTO.getBodCodigo()));
        }
        if (invConsumosTO.getCliCodigo() != null) {
            invConsumos.setInvCliente(new InvCliente(invConsumosTO.getCliEmpresa(), invConsumosTO.getCliCodigo()));
        }
        if (invConsumosTO.getProvCodigo() != null) {
            invConsumos.setInvProveedor(new InvProveedor(invConsumosTO.getProvEmpresa(), invConsumosTO.getProvCodigo()));
        }
        if (invConsumosTO.getEmpId() != null) {
            invConsumos.setRhEmpleado(new RhEmpleado(invConsumosTO.getEmpEmpresa(), invConsumosTO.getEmpId()));
        }
        if (invConsumosTO.getProCodigoPrincipal() != null) {
            invConsumos.setInvProducto(new InvProducto(invConsumosTO.getProEmpresa(), invConsumosTO.getProCodigoPrincipal()));
        }
        return invConsumos;
    }

    public static InvConsumosTO convertirInvConsumosCabecera_InvConsumosCabeceraTO(InvConsumos invConsumos) {
        InvConsumosTO invConsumosTO = new InvConsumosTO();
        invConsumosTO.setConsEmpresa(invConsumos == null ? "" : invConsumos.getInvConsumosPK().getConsEmpresa());
        invConsumosTO.setConsPeriodo(invConsumos == null ? "" : invConsumos.getInvConsumosPK().getConsPeriodo());
        invConsumosTO.setConsMotivo(invConsumos == null ? "" : invConsumos.getInvConsumosPK().getConsMotivo());
        invConsumosTO.setConsNumero(invConsumos == null ? "" : invConsumos.getInvConsumosPK().getConsNumero());

        invConsumosTO.setConsReferencia(invConsumos == null ? "" : invConsumos.getConsReferencia());

        invConsumosTO.setConsFecha(
                UtilsValidacion.fecha(invConsumos == null ? new Date() : invConsumos.getConsFecha(), "yyyy-MM-dd"));
        invConsumosTO.setConsObservaciones(invConsumos == null ? "" : invConsumos.getConsObservaciones());
        invConsumosTO.setConsPendiente(invConsumos == null ? false : invConsumos.getConsPendiente());
        invConsumosTO.setConsRevisado(invConsumos == null ? false : invConsumos.getConsRevisado());
        invConsumosTO.setConsAnulado(invConsumos == null ? false : invConsumos.getConsAnulado());
        invConsumosTO.setUsrCodigo(invConsumos == null ? "" : invConsumos.getUsrCodigo());
        invConsumosTO.setUsrFechaInserta(UtilsValidacion.fecha(invConsumos == null ? new Date() : invConsumos.getUsrFechaInserta(), "yyyy-MM-dd HH:mm:ss"));
        invConsumosTO.setProCantidad(invConsumos.getProCantidad());

        if (invConsumos != null && invConsumos.getInvBodega() != null) {
            invConsumosTO.setBodCodigo(invConsumos.getInvBodega().getInvBodegaPK().getBodCodigo());
            invConsumosTO.setBodEmpresa(invConsumos.getInvBodega().getInvBodegaPK().getBodEmpresa());
        }
        if (invConsumos != null && invConsumos.getInvCliente() != null) {
            invConsumosTO.setCliCodigo(invConsumos.getInvCliente().getInvClientePK().getCliCodigo());
            invConsumosTO.setCliEmpresa(invConsumos.getInvCliente().getInvClientePK().getCliEmpresa());
        }
        if (invConsumos != null && invConsumos.getInvProveedor() != null) {
            invConsumosTO.setProvCodigo(invConsumos.getInvProveedor().getInvProveedorPK().getProvCodigo());
            invConsumosTO.setProvEmpresa(invConsumos.getInvProveedor().getInvProveedorPK().getProvEmpresa());
        }
        if (invConsumos != null && invConsumos.getRhEmpleado() != null) {
            invConsumosTO.setEmpId(invConsumos.getRhEmpleado().getRhEmpleadoPK().getEmpId());
            invConsumosTO.setEmpEmpresa(invConsumos.getRhEmpleado().getRhEmpleadoPK().getEmpEmpresa());
        }

        if (invConsumos != null && invConsumos.getInvProducto() != null) {
            invConsumosTO.setProEmpresa(invConsumos.getInvProducto().getInvProductoPK().getProEmpresa());
            invConsumosTO.setProCodigoPrincipal(invConsumos.getInvProducto().getInvProductoPK().getProCodigoPrincipal());
        }

        return invConsumosTO;
    }

    /*
	 * private String cliNombre;// private String cliCedula;// private String
	 * venEmpresa; private String venPeriodo; private String venMotivo; private
	 * String venNumero; private Boolean vtaPendiente;// private Boolean
	 * vtaRevisado;// private Boolean vtaAnulado;// //-------private Boolean
	 * venContabilizado;//
	 * 
	 * private String vtaDocumentoNumero;// private String vtaFecha;// private
	 * String vtaFechaVencimiento;
	 * 
	 * private java.math.BigDecimal venBase0;// private java.math.BigDecimal
	 * venBaseImponible;// //****private java.math.BigDecimal
	 * venBaseNoObjetoIva;// private java.math.BigDecimal venMontoIva;//
	 * //private java.math.BigDecimal venValorRetenidoIva;// private
	 * java.math.BigDecimal venValorRetenidoRenta;//
	 * 
	 * 
	 * 
	 * 
	 * ************************************
	 * 
	 * private java.math.BigDecimal vtaPagado; //---private String
	 * venRetencionNumero;// //---private String venRetencionAutorizacion;//
	 * //---private String venRetencionFechaEmision;//
	 * 
	 * 
	 * private String usrEmpresa;// private String usrCodigo;// private String
	 * usrFechaInserta;
     */
    public static InvVentaRetencionesTO convertirInvVentaRetenciones_InvVentaRetencionesTO(InvVentas invVentas) {
        InvVentaRetencionesTO invVentaRetencionesTO = new InvVentaRetencionesTO();
        invVentaRetencionesTO.setCliCodigo(invVentas.getInvCliente().getInvClientePK().getCliCodigo());
        invVentaRetencionesTO.setCliNombre(invVentas.getInvCliente().getCliRazonSocial());
        invVentaRetencionesTO.setCliCedula(invVentas.getInvCliente().getCliIdNumero());
        invVentaRetencionesTO.setCliDireccion(invVentas.getInvCliente().getCliDireccion());
        invVentaRetencionesTO.setCliTelefono(invVentas.getInvCliente().getCliTelefono());

        invVentaRetencionesTO.setVenEmpresa(invVentas.getInvVentasPK().getVtaEmpresa());
        invVentaRetencionesTO.setVenPeriodo(invVentas.getInvVentasPK().getVtaPeriodo());
        invVentaRetencionesTO.setVenMotivo(invVentas.getInvVentasPK().getVtaMotivo());
        invVentaRetencionesTO.setVenNumero(invVentas.getInvVentasPK().getVtaNumero());
        invVentaRetencionesTO.setVtaPendiente(invVentas.getVtaPendiente());
        invVentaRetencionesTO.setVtaRevisado(invVentas.getVtaRevisado());
        invVentaRetencionesTO.setVtaAnulado(invVentas.getVtaAnulado());
        // invVentaRetencionesTO.setVenContabilizado(invVent);
        invVentaRetencionesTO.setVtaDocumentoNumero(invVentas.getVtaDocumentoNumero());
        invVentaRetencionesTO.setVtaFecha(UtilsValidacion.fecha(invVentas.getVtaFecha(), "yyyy-MM-dd"));
        invVentaRetencionesTO
                .setVtaFechaVencimiento(UtilsValidacion.fecha(invVentas.getVtaFechaVencimiento(), "yyyy-MM-dd"));
        invVentaRetencionesTO.setVenBase0(invVentas.getVtaSubtotalBase0());
        invVentaRetencionesTO.setVenBaseImponible(invVentas.getVtaSubtotalBaseImponible());

        // invVentaRetencionesTO.setVenRecargo0(invVentas.getvt);
        // invVentaRetencionesTO.setVenRecargoBaseImponible(BaseImponible(invVentas.getVtaBaseImponible());
        // invVentaRetencionesTO.setVenBaseNoObjetoIva(invVentas.getVtaBaseimponible());
        invVentaRetencionesTO.setVenMontoIva(invVentas.getVtaMontoiva());
        // invVentaRetencionesTO.setVenValorRetenidoIva(invVentas.getVtaMontoiva());
        // invVentaRetencionesTO.setVenValorRetenidoRenta(invVentas.getVtaMontoiva());

        return invVentaRetencionesTO;

    }

    public static InvConsumosDetalle convertirInvConsumosDetalleTO_InvConsumosDetalle(
            InvConsumosDetalleTO invConsumosDetalleTO) {
        InvConsumosDetalle invConsumosDetalle = new InvConsumosDetalle();
        invConsumosDetalle.setDetSecuencial(invConsumosDetalleTO.getDetSecuencial());
        invConsumosDetalle.setDetOrden(invConsumosDetalleTO.getDetOrden());
        invConsumosDetalle.setDetCantidad(invConsumosDetalleTO.getDetCantidad());

        // invConsumosDetalle.setDetValorPromedio(new
        // java.math.BigDecimal("0.00"));
        // invConsumosDetalle.setDetValorUltimaCompra(new
        // java.math.BigDecimal("0.00"));
        // invConsumosDetalle.setBodEmpresa(invConsumosDetalleTO.getBodEmpresa());
        // invConsumosDetalle.setBodCodigo(invConsumosDetalleTO.getBodCodigo());
        invConsumosDetalle.setSecEmpresa(invConsumosDetalleTO.getSecEmpresa());
        invConsumosDetalle.setSecCodigo(invConsumosDetalleTO.getSecCodigo());
        invConsumosDetalle.setPisEmpresa(invConsumosDetalleTO.getPisEmpresa());
        invConsumosDetalle.setPisSector(invConsumosDetalleTO.getPisSector());
        invConsumosDetalle.setPisNumero(invConsumosDetalleTO.getPisNumero());
        return invConsumosDetalle;
    }

    public static InvConsumosMotivo convertirInvConsumosMotivoTO_InvConsumosMotivo(
            InvConsumosMotivoTO invConsumosMotivoTO) {
        InvConsumosMotivo invConsumosMotivo = new InvConsumosMotivo();
        invConsumosMotivo.setInvConsumosMotivoPK(new InvConsumosMotivoPK(invConsumosMotivoTO.getUsrEmpresa(), invConsumosMotivoTO.getCmCodigo()));
        invConsumosMotivo.setCmDetalle(invConsumosMotivoTO.getCmDetalle());
        invConsumosMotivo.setCmSector(invConsumosMotivoTO.getCmSector());
        invConsumosMotivo.setCmBodega(invConsumosMotivoTO.getCmBodega());
        invConsumosMotivo.setCmFormaContabilizar(invConsumosMotivoTO.getCmFormaContabilizar());
        invConsumosMotivo.setCmInactivo(invConsumosMotivoTO.getCmInactivo());
        invConsumosMotivo.setUsrEmpresa(invConsumosMotivoTO.getUsrEmpresa());
        invConsumosMotivo.setUsrCodigo(invConsumosMotivoTO.getUsrCodigo());
        invConsumosMotivo.setCmExigirCliente(invConsumosMotivoTO.getCmExigirCliente());
        invConsumosMotivo.setCmExigirProveedor(invConsumosMotivoTO.getCmExigirProveedor());
        invConsumosMotivo.setCmExigirTrabajador(invConsumosMotivoTO.getCmExigirTrabajador());
        invConsumosMotivo.setCmExigirProducto(invConsumosMotivoTO.getCmExigirProducto());
        invConsumosMotivo.setUsrFechaInserta(UtilsValidacion.fechaString_Date(invConsumosMotivoTO.getUsrFechaInserta()));
        return invConsumosMotivo;
    }

    public static InvConsumosMotivoTO convertirInvConsumosMotivo_InvConsumosMotivoTO(
            InvConsumosMotivo invConsumosMotivo) {
        InvConsumosMotivoTO invConsumosMotivoTO = new InvConsumosMotivoTO();
        invConsumosMotivoTO.setUsrEmpresa(invConsumosMotivo.getInvConsumosMotivoPK().getCmEmpresa());
        invConsumosMotivoTO.setCmCodigo(invConsumosMotivo.getInvConsumosMotivoPK().getCmCodigo());
        invConsumosMotivoTO.setCmDetalle(invConsumosMotivo.getCmDetalle());
        invConsumosMotivoTO.setCmSector(invConsumosMotivo.getCmSector());
        invConsumosMotivoTO.setCmBodega(invConsumosMotivo.getCmBodega());
        invConsumosMotivoTO.setCmFormaContabilizar(invConsumosMotivo.getCmFormaContabilizar());
        invConsumosMotivoTO.setCmInactivo(invConsumosMotivo.getCmInactivo());
        invConsumosMotivoTO.setUsrCodigo(invConsumosMotivo.getUsrCodigo());
        invConsumosMotivoTO.setCmExigirCliente(invConsumosMotivo.isCmExigirCliente());
        invConsumosMotivoTO.setCmExigirProveedor(invConsumosMotivo.isCmExigirProveedor());
        invConsumosMotivoTO.setCmExigirTrabajador(invConsumosMotivo.isCmExigirTrabajador());
        invConsumosMotivoTO.setCmExigirProducto(invConsumosMotivo.isCmExigirProducto());
        invConsumosMotivoTO.setUsrFechaInserta(UtilsValidacion.fecha(invConsumosMotivo.getUsrFechaInserta(), "yyyy-MM-dd"));
        return invConsumosMotivoTO;
    }

    public static InvComprasFormaPago convertirInvComprasFormaPagoTO_InvComprasFormaPago(
            InvComprasFormaPagoTO invComprasFormaPagoTO) throws Exception {
        InvComprasFormaPago invComprasFormaPago = new InvComprasFormaPago();
        invComprasFormaPago.setFpSecuencial(invComprasFormaPagoTO.getFpSecuencial());
        invComprasFormaPago.setFpDetalle(invComprasFormaPagoTO.getFpDetalle());
        invComprasFormaPago.setFpInactivo(invComprasFormaPagoTO.getFpInactivo());
        invComprasFormaPago.setCtaEmpresa(invComprasFormaPagoTO.getUsrEmpresa());
        invComprasFormaPago.setCtaCodigo(invComprasFormaPagoTO.getCtaCodigo());
        invComprasFormaPago.setUsrEmpresa(invComprasFormaPagoTO.getUsrEmpresa());
        invComprasFormaPago.setUsrCodigo(invComprasFormaPagoTO.getUsrCodigo());
        invComprasFormaPago.setUsrFechaInserta(UtilsValidacion.fechaString_Date(invComprasFormaPagoTO.getUsrFechaInserta()));
        invComprasFormaPago.setFpTipoPrincipal(invComprasFormaPagoTO.getFpTipoPrincipal());

        return invComprasFormaPago;
    }

    public static InvVentasFormaCobro convertirInvVentasFormaCobroTO_InvVentasFormaCobro(
            InvVentasFormaPagoTO invVentasFormaPagoTO) throws Exception {
        InvVentasFormaCobro invVentasFormaCobro = new InvVentasFormaCobro();
        invVentasFormaCobro.setFcSecuencial(invVentasFormaPagoTO.getFpSecuencial());
        invVentasFormaCobro.setFcDetalle(invVentasFormaPagoTO.getFpDetalle());
        invVentasFormaCobro.setFcTipoPrincipal(invVentasFormaPagoTO.getFpTipoPrincipal());
        invVentasFormaCobro.setFcInactivo(invVentasFormaPagoTO.getFpInactivo());
        invVentasFormaCobro.setCtaEmpresa(invVentasFormaPagoTO.getUsrEmpresa());
        invVentasFormaCobro.setCtaCodigo(invVentasFormaPagoTO.getCtaCodigo());
        invVentasFormaCobro.setUsrEmpresa(invVentasFormaPagoTO.getUsrEmpresa());
        invVentasFormaCobro.setUsrCodigo(invVentasFormaPagoTO.getUsrCodigo());
        return invVentasFormaCobro;
    }

    public static InvVentasFormaCobro convertirInvVentasFormaCobroTO_InvVentasFormaCobro(InvVentasFormaCobroTO invVentasFormaCobroTO) throws Exception {
        InvVentasFormaCobro invVentasFormaPago = new InvVentasFormaCobro();
        invVentasFormaPago.setFcSecuencial(invVentasFormaCobroTO.getFcSecuencial());
        invVentasFormaPago.setFcTipoPrincipal(invVentasFormaCobroTO.getFcTipoPrincipal());
        invVentasFormaPago.setFcDetalle(invVentasFormaCobroTO.getFcDetalle());
        invVentasFormaPago.setFcInactivo(invVentasFormaCobroTO.getFcInactivo());
        invVentasFormaPago.setCtaEmpresa(invVentasFormaCobroTO.getUsrEmpresa());
        invVentasFormaPago.setCtaCodigo(invVentasFormaCobroTO.getCtaCodigo());
        invVentasFormaPago.setUsrEmpresa(invVentasFormaCobroTO.getUsrEmpresa());
        invVentasFormaPago.setUsrCodigo(invVentasFormaCobroTO.getUsrCodigo());
        invVentasFormaPago.setUsrFechaInserta(UtilsValidacion.fechaString_Date(invVentasFormaCobroTO.getUsrFechaInserta()));
        return invVentasFormaPago;
    }

    public static InvComprasMotivo convertirInvComprasMotivoTO_InvComprasMotivo(InvComprasMotivoTO invComprasMotivoTO) {
        InvComprasMotivo invComprasMotivo = new InvComprasMotivo();
        invComprasMotivo.setInvComprasMotivoPK(
                new InvComprasMotivoPK(invComprasMotivoTO.getCmEmpresa(), invComprasMotivoTO.getCmCodigo()));
        invComprasMotivo.setCmDetalle(invComprasMotivoTO.getCmDetalle());
        invComprasMotivo.setCmFormaContabilizar(invComprasMotivoTO.getCmFormaContabilizar());
        invComprasMotivo.setCmFormaImprimir(invComprasMotivoTO.getCmFormaImprimir());
        invComprasMotivo.setCmAjustesDeInventario(invComprasMotivoTO.getCmAjustesDeInventario());
        invComprasMotivo.setCmInactivo(invComprasMotivoTO.getCmInactivo());
        invComprasMotivo.setTipEmpresa(invComprasMotivoTO.getCmEmpresa());
        invComprasMotivo.setTipCodigo(invComprasMotivoTO.getTipCodigo());
        invComprasMotivo.setUsrEmpresa(invComprasMotivoTO.getCmEmpresa());
        invComprasMotivo.setUsrCodigo(invComprasMotivoTO.getUsrCodigo());
        invComprasMotivo.setUsrFechaInserta(UtilsValidacion.fechaString_Date(invComprasMotivoTO.getUsrFechaInserta()));
        invComprasMotivo.setCmImb(invComprasMotivoTO.getCmImb());
        invComprasMotivo.setCmExigirImb(invComprasMotivoTO.getCmExigirImb());
        invComprasMotivo.setCmExigirLiquidacion(invComprasMotivoTO.getCmExigirLiquidacion());
        invComprasMotivo.setCmExigirOrdenCompra(invComprasMotivoTO.getCmExigirOrdenCompra());
        return invComprasMotivo;
    }

    public static InvComprasMotivoTO convertirInvComprasMotivo_InvComprasMotivoTO(InvComprasMotivo invComprasMotivo) {
        InvComprasMotivoTO invComprasMotivoTO = new InvComprasMotivoTO();
        invComprasMotivoTO.setCmEmpresa(invComprasMotivo.getInvComprasMotivoPK().getCmEmpresa());
        invComprasMotivoTO.setCmCodigo(invComprasMotivo.getInvComprasMotivoPK().getCmCodigo());
        invComprasMotivoTO.setCmDetalle(invComprasMotivo.getCmDetalle());
        invComprasMotivoTO.setCmFormaContabilizar(invComprasMotivo.getCmFormaContabilizar());
        invComprasMotivoTO.setCmFormaImprimir(invComprasMotivo.getCmFormaImprimir());
        invComprasMotivoTO.setCmAjustesDeInventario(invComprasMotivo.getCmAjustesDeInventario());
        invComprasMotivoTO.setCmInactivo(invComprasMotivo.getCmInactivo());
        invComprasMotivoTO.setTipCodigo(invComprasMotivo.getTipCodigo());
        invComprasMotivoTO.setUsrCodigo(invComprasMotivo.getUsrCodigo());
        invComprasMotivoTO
                .setUsrFechaInserta(UtilsValidacion.fecha(invComprasMotivo.getUsrFechaInserta(), "yyyy-MM-dd"));
        return invComprasMotivoTO;
    }

    public static InvVentasMotivo convertirInvVentasMotivoTO_InvVentasMotivo(InvVentaMotivoTO invVentasMotivoTO) {
        InvVentasMotivo invVentasMotivo = new InvVentasMotivo();
        invVentasMotivo.setInvVentasMotivoPK(
                new InvVentasMotivoPK(invVentasMotivoTO.getVmEmpresa(), invVentasMotivoTO.getVmCodigo()));
        invVentasMotivo.setVmDetalle(invVentasMotivoTO.getVmDetalle());
        invVentasMotivo.setVmInactivo(invVentasMotivoTO.getVmInactivo());
        invVentasMotivo.setTipEmpresa(invVentasMotivoTO.getVmEmpresa());
        invVentasMotivo.setTipCodigo(invVentasMotivoTO.getTipCodigo());
        invVentasMotivo.setVmFormaContabilizar(invVentasMotivoTO.getVmFormaContabilizar());
        invVentasMotivo.setVmFormaImprimir(invVentasMotivoTO.getVmFormaImprimir());
        invVentasMotivo.setUsrEmpresa(invVentasMotivoTO.getVmEmpresa());
        invVentasMotivo.setUsrCodigo(invVentasMotivoTO.getUsrCodigo());
        invVentasMotivo.setTcCodigo(invVentasMotivoTO.getTcCodigo());
        invVentasMotivo.setUsrFechaInserta(UtilsValidacion.fechaString_Date(invVentasMotivoTO.getUsrFechaInserta()));
        invVentasMotivo.setVmExigirLiquidacion(invVentasMotivoTO.getVmExigirLiquidacion());
        invVentasMotivo.setVmExigirBodega(invVentasMotivoTO.getVmExigirBodega());
        return invVentasMotivo;
    }

    public static InvProformasMotivo convertirInvProformasMotivoTO_InvProformasMotivo(
            InvProformaMotivoTO invProformaMotivoTO) {
        InvProformasMotivo invProformasMotivo = new InvProformasMotivo();
        invProformasMotivo.setInvProformasMotivoPK(
                new InvProformasMotivoPK(invProformaMotivoTO.getpmEmpresa(), invProformaMotivoTO.getpmCodigo()));
        invProformasMotivo.setPmDetalle(invProformaMotivoTO.getpmDetalle());
        invProformasMotivo.setPmInactivo(invProformaMotivoTO.getpmInactivo());
        invProformasMotivo.setUsrEmpresa(invProformaMotivoTO.getpmEmpresa());
        invProformasMotivo.setUsrCodigo(invProformaMotivoTO.getUsrCodigo());
        invProformasMotivo
                .setUsrFechaInserta(UtilsValidacion.fechaString_Date(invProformaMotivoTO.getUsrFechaInserta()));
        return invProformasMotivo;
    }

    public static InvVentaMotivoTO convertirInvVentasMotivo_InvVentasMotivoTO(InvVentasMotivo invVentasMotivo) {
        InvVentaMotivoTO invVentaMotivoTO = new InvVentaMotivoTO();
        invVentaMotivoTO.setVmEmpresa(invVentasMotivo.getInvVentasMotivoPK().getVmEmpresa());
        invVentaMotivoTO.setVmCodigo(invVentasMotivo.getInvVentasMotivoPK().getVmCodigo());
        invVentaMotivoTO.setVmDetalle(invVentasMotivo.getVmDetalle());
        invVentaMotivoTO.setVmFormaContabilizar(invVentasMotivo.getVmFormaContabilizar());
        invVentaMotivoTO.setVmFormaImprimir(invVentasMotivo.getVmFormaImprimir());
        invVentaMotivoTO.setVmDetalle(invVentasMotivo.getVmDetalle());
        invVentaMotivoTO.setVmInactivo(invVentasMotivo.getVmInactivo());
        invVentaMotivoTO.setTipCodigo(invVentasMotivo.getTipCodigo());
        invVentaMotivoTO.setUsrCodigo(invVentasMotivo.getUsrCodigo());
        invVentaMotivoTO.setTcCodigo(invVentasMotivo.getTcCodigo());
        invVentaMotivoTO.setUsrFechaInserta(UtilsValidacion.fecha(invVentasMotivo.getUsrFechaInserta(), "yyyy-MM-dd"));
        invVentaMotivoTO.setVmExigirLiquidacion(invVentaMotivoTO.getVmExigirLiquidacion());
        invVentaMotivoTO.setVmExigirBodega(invVentasMotivo.isVmExigirBodega());
        return invVentaMotivoTO;
    }

    public static InvProformaMotivoTO convertirInvProformaMotivo_InvProformaMotivoTO(
            InvProformasMotivo invProformasMotivo) {
        InvProformaMotivoTO invProformaMotivoTO = new InvProformaMotivoTO();
        invProformaMotivoTO.setpmEmpresa(invProformasMotivo.getInvProformasMotivoPK().getPmEmpresa());
        invProformaMotivoTO.setpmCodigo(invProformasMotivo.getInvProformasMotivoPK().getPmCodigo());
        invProformaMotivoTO.setpmDetalle(invProformasMotivo.getPmDetalle());
        invProformaMotivoTO.setpmInactivo(invProformasMotivo.getPmInactivo());
        invProformaMotivoTO.setUsrCodigo(invProformasMotivo.getUsrCodigo());
        invProformaMotivoTO
                .setUsrFechaInserta(UtilsValidacion.fecha(invProformasMotivo.getUsrFechaInserta(), "yyyy-MM-dd"));
        return invProformaMotivoTO;
    }

    public static InvComprasRecepcion convertirInvComprasRecepcion_InvComprasRecepcionTO(
            InvComprasRecepcionTO invComprasRecepcionTO) {
        InvComprasRecepcion invComprasRecepcion = new InvComprasRecepcion();
        invComprasRecepcion.setRecep_secuencial(invComprasRecepcionTO.getRecep_secuencial());
        invComprasRecepcion.setRecepFecha(UtilsValidacion.fecha(invComprasRecepcionTO.getRecepFecha(), "yyyy-MM-dd"));
        invComprasRecepcion.setUsrEmpresa(invComprasRecepcionTO.getUsrEmpresa());
        invComprasRecepcion.setUsrCodigo(invComprasRecepcionTO.getUsrCodigo());
        invComprasRecepcion
                .setUsrFechaInserta(UtilsValidacion.fecha(invComprasRecepcionTO.getUsrFechaInserta(), "yyyy-MM-dd"));
        return invComprasRecepcion;
    }

    public static InvTransferenciasTO convertirInvTransferenciasCabecera_InvTransferenciasCabeceraTO(
            InvTransferencias invTransferencias) {
        InvTransferenciasTO invTransferenciasTO = new InvTransferenciasTO();
        invTransferenciasTO.setTransEmpresa(invTransferencias.getInvTransferenciasPK().getTransEmpresa());
        invTransferenciasTO.setTransPeriodo(invTransferencias.getInvTransferenciasPK().getTransPeriodo());
        invTransferenciasTO.setTransMotivo(invTransferencias.getInvTransferenciasPK().getTransMotivo());
        invTransferenciasTO.setTransNumero(invTransferencias.getInvTransferenciasPK().getTransNumero());

        invTransferenciasTO.setTransFecha(UtilsValidacion.fecha(invTransferencias.getTransFecha(), "yyyy-MM-dd"));
        invTransferenciasTO.setTransObservaciones(invTransferencias.getTransObservaciones());
        invTransferenciasTO.setTransPendiente(invTransferencias.getTransPendiente());
        invTransferenciasTO.setTransRevisado(invTransferencias.getTransRevisado());
        invTransferenciasTO.setTransAnulado(invTransferencias.getTransAnulado());
        invTransferenciasTO.setUsrCodigo(invTransferencias.getUsrCodigo());
        invTransferenciasTO.setUsrFechaInserta(
                UtilsValidacion.fecha(invTransferencias.getUsrFechaInserta(), "yyyy-MM-dd hh:mm:ss"));
        return invTransferenciasTO;
    }

    public static InvTransferenciaMotivoTO convertirInvTransferenciaMotivo_InvTransferenciaMotivoTO(
            InvTransferenciasMotivo invTransferenciasMotivo) {
        InvTransferenciaMotivoTO invTransferenciaMotivoTO = new InvTransferenciaMotivoTO();
        invTransferenciaMotivoTO.setUsrEmpresa(invTransferenciasMotivo.getInvTransferenciasMotivoPK().getTmEmpresa());
        invTransferenciaMotivoTO.setTmCodigo(invTransferenciasMotivo.getInvTransferenciasMotivoPK().getTmCodigo());
        invTransferenciaMotivoTO.setTmDetalle(invTransferenciasMotivo.getTmDetalle());
        invTransferenciaMotivoTO.setTmInactivo(invTransferenciasMotivo.getTmInactivo());
        invTransferenciaMotivoTO.setUsrCodigo(invTransferenciasMotivo.getUsrCodigo());
        invTransferenciaMotivoTO
                .setUsrFechaInserta(UtilsValidacion.fecha(invTransferenciasMotivo.getUsrFechaInserta(), "yyyy-MM-dd"));
        return invTransferenciaMotivoTO;
    }

    public static InvTransferencias convertirInvTransferenciasTO_InvTransferencias(
            InvTransferenciasTO invTransferenciasTO) {
        InvTransferencias invTransferencias = new InvTransferencias();
        invTransferencias.setInvTransferenciasPK(
                new InvTransferenciasPK(invTransferenciasTO.getTransEmpresa(), invTransferenciasTO.getTransPeriodo(),
                        invTransferenciasTO.getTransMotivo(), invTransferenciasTO.getTransNumero()));
        invTransferencias.setTransFecha(UtilsValidacion.fecha(invTransferenciasTO.getTransFecha(), "yyyy-MM-dd"));
        invTransferencias.setTransObservaciones(invTransferenciasTO.getTransObservaciones());
        invTransferencias.setTransPendiente(invTransferenciasTO.getTransPendiente());
        invTransferencias.setTransRevisado(invTransferenciasTO.getTransRevisado());
        invTransferencias.setTransAnulado(invTransferenciasTO.getTransAnulado());
        // invTransferencias.setConEmpresa(invTransferenciasTO.getConEmpresa());
        // invTransferencias.setConPeriodo(invTransferenciasTO.getConPeriodo());
        // invTransferencias.setConTipo(invTransferenciasTO.getConTipo());
        // invTransferencias.setConNumero(invTransferenciasTO.getConNumero());
        invTransferencias.setUsrEmpresa(invTransferenciasTO.getTransEmpresa());
        invTransferencias.setUsrCodigo(invTransferenciasTO.getUsrCodigo());
        invTransferencias
                .setUsrFechaInserta(UtilsValidacion.fechaString_Date(invTransferenciasTO.getUsrFechaInserta()));
        return invTransferencias;
    }

    public static InvTransferenciasDetalle convertirInvTransferenciasDetalleTO_InvTransferenciasDetalle(
            InvTransferenciasDetalleTO invTransferenciasDetalleTO) {
        InvTransferenciasDetalle invTransferenciasDetalle = new InvTransferenciasDetalle();
        invTransferenciasDetalle.setDetSecuencial(invTransferenciasDetalleTO.getDetSecuencial());
        invTransferenciasDetalle.setDetOrden(invTransferenciasDetalleTO.getDetOrden());
        invTransferenciasDetalle.setDetCantidad(invTransferenciasDetalleTO.getDetCantidad());

        // invTransferenciasDetalle.setDetValorPromedio(new
        // java.math.BigDecimal("0.00"));
        // invTransferenciasDetalle.setDetValorUltimaCompra(new
        // java.math.BigDecimal("0.00"));
        invTransferenciasDetalle.setSecDestinoEmpresa(invTransferenciasDetalleTO.getSecDestinoEmpresa());
        invTransferenciasDetalle.setSecDestinoCodigo(invTransferenciasDetalleTO.getSecDestinoCodigo());
        invTransferenciasDetalle.setSecOrigenEmpresa(invTransferenciasDetalleTO.getSecOrigenEmpresa());
        invTransferenciasDetalle.setSecOrigenCodigo(invTransferenciasDetalleTO.getSecOrigenCodigo());
        if (invTransferenciasDetalleTO.getTransPiscinaTo() != null && invTransferenciasDetalleTO.getTransPiscinaTo().getPisNumero() != null) {
            invTransferenciasDetalle.setPrdPiscina(new PrdPiscina(invTransferenciasDetalleTO.getTransEmpresa(), invTransferenciasDetalleTO.getTransPiscinaTo().getPisSector(), invTransferenciasDetalleTO.getTransPiscinaTo().getPisNumero()));
        }

        List<InvTransferenciasDetalleSeries> listaSeries = new ArrayList<>();
        if (invTransferenciasDetalleTO.getInvTransferenciaDetalleSeriesListTO() != null && invTransferenciasDetalleTO.getInvTransferenciaDetalleSeriesListTO().size() > 0) {
            for (int i = 0; i < invTransferenciasDetalleTO.getInvTransferenciaDetalleSeriesListTO().size(); i++) {
                InvTransferenciasDetalleSeries serie = new InvTransferenciasDetalleSeries();
                serie.setDetNumeroSerie(invTransferenciasDetalleTO.getInvTransferenciaDetalleSeriesListTO().get(i).getDetNumeroSerie());
                serie.setDetSecuencial(invTransferenciasDetalleTO.getInvTransferenciaDetalleSeriesListTO().get(i).getDetSecuencial());
                serie.setDetSecuencialTransferencia(invTransferenciasDetalle);
                listaSeries.add(serie);
            }
        }
        invTransferenciasDetalle.setInvTransferenciasDetalleSeriesList(listaSeries);
        return invTransferenciasDetalle;
    }

    public static InvNumeracionVarios convertirInvNumeracionVarios_InvNumeracionVarios(
            InvNumeracionVarios invNumeracionVariosAux) {
        InvNumeracionVarios invNumeracionVarios = new InvNumeracionVarios();
        if (invNumeracionVariosAux != null) {
            invNumeracionVarios.setNumEmpresa(invNumeracionVariosAux.getNumEmpresa());
            invNumeracionVarios.setNumClientes(invNumeracionVariosAux.getNumClientes());
            invNumeracionVarios.setNumProductos(invNumeracionVariosAux.getNumProductos());
            invNumeracionVarios.setNumProveedores(invNumeracionVariosAux.getNumProveedores());
            invNumeracionVarios.setEmpCodigo(invNumeracionVariosAux.getEmpCodigo());
        } else {
            invNumeracionVarios = null;
        }

        return invNumeracionVarios;
    }

    public static InvTransferenciasMotivo convertirInvTransferenciasMotivoTO_InvTransferenciasMotivo(
            InvTransferenciaMotivoTO invTransferenciaMotivoTO) {
        InvTransferenciasMotivo invTransferenciasMotivo = new InvTransferenciasMotivo();
        invTransferenciasMotivo.setInvTransferenciasMotivoPK(new InvTransferenciasMotivoPK(
                invTransferenciaMotivoTO.getUsrEmpresa(), invTransferenciaMotivoTO.getTmCodigo()));
        invTransferenciasMotivo.setTmDetalle(invTransferenciaMotivoTO.getTmDetalle());
        invTransferenciasMotivo.setTmInactivo(invTransferenciaMotivoTO.getTmInactivo());
        invTransferenciasMotivo.setUsrEmpresa(invTransferenciaMotivoTO.getUsrEmpresa());
        invTransferenciasMotivo.setUsrCodigo(invTransferenciaMotivoTO.getUsrCodigo());
        invTransferenciasMotivo
                .setUsrFechaInserta(UtilsValidacion.fechaString_Date(invTransferenciaMotivoTO.getUsrFechaInserta()));
        return invTransferenciasMotivo;
    }

    public static InvComprasTO convertirInvCompras_InvComprasTO(InvCompras invCompras) {
        if (invCompras != null) {

            InvComprasTO invComprasTO = new InvComprasTO();

            invComprasTO.setEmpCodigo(invCompras.getInvComprasPK().getCompEmpresa());
            invComprasTO.setCompPeriodo(invCompras.getInvComprasPK().getCompPeriodo());
            invComprasTO.setCompMotivo(invCompras.getInvComprasPK().getCompMotivo());
            invComprasTO.setCompNumero(invCompras.getInvComprasPK().getCompNumero());
            invComprasTO.setCompDocumentoNumero(invCompras.getCompDocumentoNumero());
            invComprasTO.setCompFecha(invCompras.getCompFecha() != null
                    ? UtilsValidacion.fecha(invCompras.getCompFecha(), "dd-MM-yyyy") : null);
            // invComprasTO.setCompFecha(invCompras.getCompFecha().toString());
            invComprasTO.setCompFechaVencimiento(invCompras.getCompFechaVencimiento().toString());
            invComprasTO.setCompDocumentoTipo(invCompras.getCompDocumentoTipo());

            invComprasTO.setCompIvaVigente(invCompras.getCompIvaVigente());
            invComprasTO.setCompObservaciones(invCompras.getCompObservaciones());
            invComprasTO.setCompElectronica(invCompras.getCompElectronica());
            invComprasTO.setCompActivoFijo(invCompras.getCompActivoFijo());
            invComprasTO.setCompPendiente(invCompras.getCompPendiente());
            invComprasTO.setCompRevisado(invCompras.getCompRevisado());
            invComprasTO.setCompAnulado(invCompras.getCompAnulado());
            invComprasTO.setCompFormaPago(invCompras.getCompFormaPago());
            invComprasTO.setCompDocumentoFormaPago(invCompras.getCompDocumentoFormaPago());

            invComprasTO.setCompBase0(invCompras.getCompBase0());
            invComprasTO.setCompBaseImponible(invCompras.getCompBaseImponible());
            invComprasTO.setCompMontoIva(invCompras.getCompMontoIva());
            invComprasTO.setCompOtrosImpuestos(invCompras.getCompOtrosImpuestos());
            invComprasTO.setCompTotal(invCompras.getCompTotal());
            invComprasTO.setCompValorRetenido(invCompras.getCompValorRetenido());
            invComprasTO.setCompRetencionAsumida(invCompras.getCompRetencionAsumida());
            invComprasTO.setCompSaldo(invCompras.getCompSaldo());
            invComprasTO.setProvCodigo(invCompras.getInvProveedor().getInvProveedorPK().getProvCodigo());
            invComprasTO.setContPeriodo(invCompras.getConPeriodo());
            invComprasTO.setContTipo(invCompras.getConTipo());
            invComprasTO.setContNumero(invCompras.getConNumero());
            invComprasTO.setCompTransportistaRuc(invCompras.getCompTransportistaRuc());
            invComprasTO.setFpSecuencial(invCompras.getFpSecuencial());
            invComprasTO.setUsrInsertaCompra(invCompras.getUsrCodigo());
            invComprasTO.setUsrFechaInsertaCompra(UtilsValidacion.fecha(invCompras.getUsrFechaInserta(), "dd-MM-yyyy HH:mm:ss.SS"));
            invComprasTO.setFechaUltimaValidacionSri(invCompras.getFechaUltimaValidacionSri() != null
                    ? UtilsValidacion.fecha(invCompras.getFechaUltimaValidacionSri(), "dd-MM-yyyy") : null);
            invComprasTO.setCompSaldoImportado(invCompras.isCompSaldoImportado());
            invComprasTO.setFpSecuencial(invCompras.getFpSecuencial());
            return invComprasTO;
        } else {
            return null;
        }
    }

    public static InvPedidosMotivoTO convertirInvPedidosMotivoTO_InvPedidosMotivo(InvPedidosMotivo invPedidosMotivo) {
        InvPedidosMotivoTO invPedidosMotivoTO = new InvPedidosMotivoTO();
        invPedidosMotivoTO.setPmEmpresa(invPedidosMotivo.getInvPedidosMotivoPK().getPmEmpresa());
        invPedidosMotivoTO.setPmSector(invPedidosMotivo.getInvPedidosMotivoPK().getPmSector());
        invPedidosMotivoTO.setPmCodigo(invPedidosMotivo.getInvPedidosMotivoPK().getPmCodigo());
        return invPedidosMotivoTO;
    }

    public static InvPedidosOrdenCompraNotificaciones convertirAnxNotificacionAWSTO_InvPedidosOrdenCompraNotificaciones(AnxNotificacionAWSTO anxNotificacionAWSTO) {
        InvPedidosOrdenCompraNotificaciones ocNotificaciones = new InvPedidosOrdenCompraNotificaciones();
        ocNotificaciones.setOcEmpresa(anxNotificacionAWSTO.getEmpresa());
        ocNotificaciones.setOcMotivo(anxNotificacionAWSTO.getMotivo());
        ocNotificaciones.setOcNumero(anxNotificacionAWSTO.getNumero());
        ocNotificaciones.setOcSector(anxNotificacionAWSTO.getSector());
        ocNotificaciones.setOcnDestinatario(anxNotificacionAWSTO.getDestinatario());
        ocNotificaciones.setOcnFecha(anxNotificacionAWSTO.getFecha());
        ocNotificaciones.setOcnInforme(anxNotificacionAWSTO.getInforme());
        ocNotificaciones.setOcnObservacion(anxNotificacionAWSTO.getObservacion());
        ocNotificaciones.setOcnSecuencial(anxNotificacionAWSTO.getSecuencial());
        ocNotificaciones.setOcnTipo(anxNotificacionAWSTO.getTipo());
        return ocNotificaciones;
    }

    public static InvComprasDetalleSeriesTO convertirInvComprasDetalleSeries_InvComprasDetalleSeriesTO(InvComprasDetalleSeries invComprasDetalleSeries) {
        InvComprasDetalleSeriesTO invComprasDetalleSeriesTO = new InvComprasDetalleSeriesTO();
        invComprasDetalleSeriesTO.setDetSecuencial(invComprasDetalleSeries.getDetSecuencial());
        invComprasDetalleSeriesTO.setDetNumeroSerie(invComprasDetalleSeries.getDetNumeroSerie());
        invComprasDetalleSeriesTO.setDetSecuencialCompra(invComprasDetalleSeries.getDetSecuencialCompra().getDetSecuencial());
        return invComprasDetalleSeriesTO;
    }

    public static InvTransferenciaDetalleSeriesTO convertirInvTransferenciasDetalleSeries_InvTransferenciaDetalleSeriesTO(InvTransferenciasDetalleSeries invTransferenciasDetalleSeries) {
        InvTransferenciaDetalleSeriesTO serieTO = new InvTransferenciaDetalleSeriesTO();
        serieTO.setDetSecuencial(invTransferenciasDetalleSeries.getDetSecuencial());
        serieTO.setDetNumeroSerie(invTransferenciasDetalleSeries.getDetNumeroSerie());
        serieTO.setDetSecuencialTransferencia(invTransferenciasDetalleSeries.getDetSecuencialTransferencia().getDetSecuencial());
        return serieTO;
    }

    public static InvGuiaRemisionDetalleTO convertirInvGuiaRemisionDetalle_InvGuiaRemisionDetalleTO(InvGuiaRemisionDetalle invGuiaRemisionDetalle) {
        InvGuiaRemisionDetalleTO invGuiaRemisionDetalleTO = new InvGuiaRemisionDetalleTO();
        invGuiaRemisionDetalleTO.setDetCantidad(invGuiaRemisionDetalle.getDetCantidad());
        invGuiaRemisionDetalleTO.setDetSecuencia(invGuiaRemisionDetalle.getDetSecuencial());
        invGuiaRemisionDetalleTO.setDetOrden(invGuiaRemisionDetalle.getDetOrden());
        invGuiaRemisionDetalleTO.setGuiaEmpresa(invGuiaRemisionDetalle.getInvGuiaRemision().getInvGuiaRemisionPK().getGuiaEmpresa());
        invGuiaRemisionDetalleTO.setGuiaNumero(invGuiaRemisionDetalle.getInvGuiaRemision().getInvGuiaRemisionPK().getGuiaNumero());
        invGuiaRemisionDetalleTO.setGuiaPeriodo(invGuiaRemisionDetalle.getInvGuiaRemision().getInvGuiaRemisionPK().getGuiaPeriodo());
        invGuiaRemisionDetalleTO.setNombreProducto(invGuiaRemisionDetalle.getNombreProducto());
        invGuiaRemisionDetalleTO.setProCodigoPrincipal(invGuiaRemisionDetalle.getInvProducto().getInvProductoPK().getProCodigoPrincipal());
        invGuiaRemisionDetalleTO.setProCodigoPrincipalCopia(invGuiaRemisionDetalle.getInvProducto().getInvProductoPK().getProCodigoPrincipal());
        invGuiaRemisionDetalleTO.setProEmpresa(invGuiaRemisionDetalle.getInvProducto().getInvProductoPK().getProEmpresa());
        return invGuiaRemisionDetalleTO;
    }

    public static InvComprasDetalleImb convertirInvComprasDetalleImbTO_InvComprasDetalleImb(InvComprasDetalleImbTO detalleImbTO) {
        InvComprasDetalleImb detalleImb = new InvComprasDetalleImb();
        detalleImb.setDetSecuencial(detalleImbTO.getDetSecuencia());
        detalleImb.setCompImbTotal(detalleImbTO.getComImbTotal());
        //
        InvCompras invCompras = new InvCompras(detalleImbTO.getComEmpresa(), detalleImbTO.getComPeriodo(), detalleImbTO.getComMotivo(), detalleImbTO.getComNumero());
        detalleImb.setInvCompras(invCompras);
        //
        InvCompras invComprasImb = new InvCompras(detalleImbTO.getComImbEmpresa(), detalleImbTO.getComImbPeriodo(), detalleImbTO.getComImbMotivo(), detalleImbTO.getComImbNumero());
        detalleImb.setInvComprasImb(invComprasImb);
        //
        InvProveedor invProveedorImb = new InvProveedor(detalleImbTO.getProvImbEmpresa(), detalleImbTO.getProvImbCodigo());
        detalleImb.setInvProveedorImb(invProveedorImb);

        return detalleImb;
    }

    public static InvComprasLiquidacion convertirInvComprasLiquidacionTO_InvComprasLiquidacion(InvComprasLiquidacionTO detalleLiqTO) {
        InvComprasLiquidacion compraLiquidacion = new InvComprasLiquidacion();
        compraLiquidacion.setDetSecuencial(detalleLiqTO.getDetSecuencia());
        compraLiquidacion.setLiqTotal(detalleLiqTO.getLiqTotal());
        //
        InvCompras invCompras = new InvCompras(detalleLiqTO.getComEmpresa(), detalleLiqTO.getComPeriodo(), detalleLiqTO.getComMotivo(), detalleLiqTO.getComNumero());
        compraLiquidacion.setInvCompras(invCompras);
        //
        PrdLiquidacion liquidacion = new PrdLiquidacion(detalleLiqTO.getLiqEmpresa(), detalleLiqTO.getLiqMotivo(), detalleLiqTO.getLiqNumero());
        compraLiquidacion.setPrdLiquidacion(liquidacion);

        return compraLiquidacion;
    }

    public static InvVentasLiquidacion convertirInvVentasLiquidacionTO_InvVentasLiquidacion(InvVentasLiquidacionTO detalleLiqTO) {
        InvVentasLiquidacion ventaLiquidacion = new InvVentasLiquidacion();
        ventaLiquidacion.setDetSecuencial(detalleLiqTO.getDetSecuencia());
        ventaLiquidacion.setLiqTotal(detalleLiqTO.getLiqTotal());
        //
        InvVentas invVenta = new InvVentas(detalleLiqTO.getVtaEmpresa(), detalleLiqTO.getVtaPeriodo(), detalleLiqTO.getVtaMotivo(), detalleLiqTO.getVtaNumero());
        ventaLiquidacion.setInvVentas(invVenta);
        //
        PrdLiquidacion liquidacion = new PrdLiquidacion(detalleLiqTO.getLiqEmpresa(), detalleLiqTO.getLiqMotivo(), detalleLiqTO.getLiqNumero());
        ventaLiquidacion.setPrdLiquidacion(liquidacion);

        return ventaLiquidacion;
    }

    public static List<InvComprasDetalleImbTO> convertirListInvComprasDetalleImb_InvComprasDetalleImbTO(List<InvComprasDetalleImb> listaInvComprasDetalleImb) {
        List<InvComprasDetalleImbTO> listaInvComprasDetalleImbTO = new ArrayList<>();

        for (int i = 0; i < listaInvComprasDetalleImb.size(); i++) {
            InvComprasDetalleImbTO item = new InvComprasDetalleImbTO();
            item.setDetSecuencia(listaInvComprasDetalleImb.get(i).getDetSecuencial());
            item.setComEmpresa(listaInvComprasDetalleImb.get(i).getInvCompras().getInvComprasPK().getCompEmpresa());
            item.setComPeriodo(listaInvComprasDetalleImb.get(i).getInvCompras().getInvComprasPK().getCompPeriodo());
            item.setComMotivo(listaInvComprasDetalleImb.get(i).getInvCompras().getInvComprasPK().getCompMotivo());
            item.setComNumero(listaInvComprasDetalleImb.get(i).getInvCompras().getInvComprasPK().getCompNumero());
            item.setComImbEmpresa(listaInvComprasDetalleImb.get(i).getInvComprasImb().getInvComprasPK().getCompEmpresa());
            item.setComImbPeriodo(listaInvComprasDetalleImb.get(i).getInvComprasImb().getInvComprasPK().getCompPeriodo());
            item.setComImbMotivo(listaInvComprasDetalleImb.get(i).getInvComprasImb().getInvComprasPK().getCompMotivo());
            item.setComImbNumero(listaInvComprasDetalleImb.get(i).getInvComprasImb().getInvComprasPK().getCompNumero());
            item.setComImbTotal(listaInvComprasDetalleImb.get(i).getCompImbTotal());
            item.setProvImbCodigo(listaInvComprasDetalleImb.get(i).getInvProveedorImb().getInvProveedorPK().getProvCodigo());
            item.setProvImbEmpresa(listaInvComprasDetalleImb.get(i).getInvProveedorImb().getInvProveedorPK().getProvEmpresa());

            listaInvComprasDetalleImbTO.add(item);
        }

        return listaInvComprasDetalleImbTO;
    }

    public static List<InvAdjuntosCompras> convertirInvAdjuntosCompras_InvAdjuntoComprasTO(List<InvAdjuntoComprasTO> listaInvAdjuntoCompraTO, InvCompras invCompras) {
        List<InvAdjuntosCompras> listAdjuntos = new ArrayList<>();
        for (InvAdjuntoComprasTO imagen : listaInvAdjuntoCompraTO) {
            InvAdjuntosCompras invAdjuntoCompras = new InvAdjuntosCompras();
            invAdjuntoCompras.setAdjSecuencial(imagen.getAdjSecuencial());
            invAdjuntoCompras.setInvCompras(invCompras);
            invAdjuntoCompras.setAdjArchivo(imagen.getAdjArchivo());
            invAdjuntoCompras.setAdjTipo(imagen.getAdjTipo());
            listAdjuntos.add(invAdjuntoCompras);
        }

        return listAdjuntos;
    }

    public static List<InvComprasDetalleTO> convertirInvListaDetalleComprasTO_InvComprasDetalleTO(List<InvListaDetalleComprasTO> listaInvCompraDetalleTO, InvComprasPK invComprasPK) {
        List<InvComprasDetalleTO> listInvComprasDetalleTO = new ArrayList<>();
        for (int i = 0; i < listaInvCompraDetalleTO.size(); i++) {
            InvComprasDetalleTO item = new InvComprasDetalleTO();
            item.setBodCodigo(listaInvCompraDetalleTO.get(i).getCodigoBodega());
            item.setBodEmpresa(invComprasPK.getCompEmpresa());
            item.setComEmpresa(invComprasPK.getCompEmpresa());
            item.setComMotivo(invComprasPK.getCompMotivo());
            item.setComNumero(invComprasPK.getCompNumero());
            item.setComPeriodo(invComprasPK.getCompPeriodo());
            item.setDetCantidad(listaInvCompraDetalleTO.get(i).getCantidadProducto());
            item.setDetIce(listaInvCompraDetalleTO.get(i).getDetIce());
            item.setDetOrden(i);
            item.setDetOtrosImpuestos(listaInvCompraDetalleTO.get(i).getDetOtrosImpuestos());
            item.setDetPendiente(listaInvCompraDetalleTO.get(i).getPendiente());
            item.setDetPorcentajeDescuento(listaInvCompraDetalleTO.get(i).getPorcentajeDescuento());
            item.setDetPrecio(listaInvCompraDetalleTO.get(i).getPrecioProducto());
            item.setDetPrecio1(listaInvCompraDetalleTO.get(i).getDetPrecio1());
            item.setDetPrecio2(listaInvCompraDetalleTO.get(i).getDetPrecio2());
            item.setDetPrecio3(listaInvCompraDetalleTO.get(i).getDetPrecio3());
            item.setDetPrecio4(listaInvCompraDetalleTO.get(i).getDetPrecio4());
            item.setDetPrecio5(listaInvCompraDetalleTO.get(i).getDetPrecio5());
            item.setDetSecuencia(listaInvCompraDetalleTO.get(i).getSecuencial());
            item.setDetValorUltimaCompra(listaInvCompraDetalleTO.get(i).getValorUltimaCompra());
            item.setPisEmpresa(invComprasPK.getCompEmpresa());
            item.setPisNumero(listaInvCompraDetalleTO.get(i).getCodigoCC());
            item.setPisSector(listaInvCompraDetalleTO.get(i).getCodigoCP());
            item.setProCodigoPrincipal(listaInvCompraDetalleTO.get(i).getCodigoProducto());
            item.setProEmpresa(invComprasPK.getCompEmpresa());
            item.setProEstadoIva(listaInvCompraDetalleTO.get(i).getGravaIva());
            item.setSecCodigo(listaInvCompraDetalleTO.get(i).getCodigoCP());
            item.setSecEmpresa(invComprasPK.getCompEmpresa());
            item.setDetObservaciones(listaInvCompraDetalleTO.get(i).getCdObservaciones());
            item.setDetSecuencialOrdenCompra(listaInvCompraDetalleTO.get(i).getCdSecuencialOrdenCompra());
            listInvComprasDetalleTO.add(item);
        }

        return listInvComprasDetalleTO;
    }

    public static InvComprasTO convertirInvCompraCabeceraTO_InvComprasTO(InvCompraCabeceraTO invCompraCabeceraTO, InvComprasPK invComprasPK) {
        InvComprasTO invComprasTO = new InvComprasTO();
        invComprasTO.setBodEmpresa(invComprasPK.getCompEmpresa());
        invComprasTO.setCompActivoFijo(invCompraCabeceraTO.getCompActivoFijo());
        invComprasTO.setCompAnulado(invCompraCabeceraTO.getCompAnulado());
        invComprasTO.setCompBase0(invCompraCabeceraTO.getCompBase0());
        invComprasTO.setCompBaseExenta(invCompraCabeceraTO.getCompBaseExenta());
        invComprasTO.setCompBaseImponible(invCompraCabeceraTO.getCompBaseimponible());
        invComprasTO.setCompBaseNoObjeto(invCompraCabeceraTO.getCompBaseNoObjeto());
        invComprasTO.setCompDocumentoFormaPago(invCompraCabeceraTO.getDocumentoFormaPago());
        invComprasTO.setCompDocumentoNumero(invCompraCabeceraTO.getCompDocumentoNumero());
        invComprasTO.setCompDocumentoTipo(invCompraCabeceraTO.getCompDocumentoTipo());
        invComprasTO.setCompElectronica(invCompraCabeceraTO.getCompElectronica());
        invComprasTO.setCompFecha(invCompraCabeceraTO.getCompfecha());
        invComprasTO.setCompFechaVencimiento(invCompraCabeceraTO.getCompFechaVencimiento());
        invComprasTO.setCompFormaPago(invCompraCabeceraTO.getCompFormaPago());
        invComprasTO.setCompIce(invCompraCabeceraTO.getCompIce());
        invComprasTO.setCompImportacion(invCompraCabeceraTO.getCompImportacion());
        invComprasTO.setCompIvaVigente(invCompraCabeceraTO.getCompIvaVigente());
        invComprasTO.setCompMontoIva(invCompraCabeceraTO.getCompMontoiva());
        invComprasTO.setCompMotivo(invComprasPK.getCompMotivo());
        invComprasTO.setCompNumero(invComprasPK.getCompNumero());
        invComprasTO.setCompObservaciones(invCompraCabeceraTO.getCompObservaciones());
        invComprasTO.setCompOtrosImpuestos(invCompraCabeceraTO.getCompOtrosImpuestos());
        invComprasTO.setCompPendiente(invCompraCabeceraTO.getCompPendiente());
        invComprasTO.setCompPeriodo(invComprasPK.getCompPeriodo());
        invComprasTO.setCompRetencionAsumida(invCompraCabeceraTO.isRetencionAsumida());
        invComprasTO.setCompPropina(invCompraCabeceraTO.getCompPropina());
        invComprasTO.setCompRevisado(invCompraCabeceraTO.getCompRevisado());
        invComprasTO.setCompSaldo(invCompraCabeceraTO.getCompSaldo());
        invComprasTO.setCompTotal(invCompraCabeceraTO.getCompTotal());
        invComprasTO.setCompValorRetenido(invCompraCabeceraTO.getCompValorretenido());
        invComprasTO.setContNumero(invCompraCabeceraTO.getConNumero());
        invComprasTO.setContPeriodo(invCompraCabeceraTO.getConPeriodo());
        invComprasTO.setContTipo(invCompraCabeceraTO.getConTipo());
        invComprasTO.setEmpCodigo(invComprasPK.getCompEmpresa());
        invComprasTO.setProvCodigo(invCompraCabeceraTO.getProvCodigo());
        invComprasTO.setProvEmpresa(invComprasPK.getCompEmpresa());
        invComprasTO.setSecCodigo(invCompraCabeceraTO.getSecCodigo());
        invComprasTO.setSecEmpresa(invComprasPK.getCompEmpresa());
        invComprasTO.setUsrFechaInsertaCompra(invCompraCabeceraTO.getFechaUsuarioInserto());
        invComprasTO.setUsrInsertaCompra(invCompraCabeceraTO.getUsuarioInserto());
        //orden de compra
        invComprasTO.setOcEmpresa(invCompraCabeceraTO.getOcEmpresa());
        invComprasTO.setOcMotivo(invCompraCabeceraTO.getOcMotivo());
        invComprasTO.setOcSector(invCompraCabeceraTO.getOcSector());
        invComprasTO.setOcNumero(invCompraCabeceraTO.getOcNumero());
        invComprasTO.setCompProgramada(invCompraCabeceraTO.isCompProgramada());
        invComprasTO.setCompUsuarioApruebaPago(invCompraCabeceraTO.getCompUsuarioApruebaPago());
        invComprasTO.setCompFechaApruebaPago(invCompraCabeceraTO.getCompFechaApruebaPago());
        invComprasTO.setCompImbFacturado(invCompraCabeceraTO.isCompImbFacturado());
        invComprasTO.setCompEsImb(invCompraCabeceraTO.getCompEsImb());
        return invComprasTO;
    }

    public static InvConsumos convertirInvConsumos_InvConsumos(InvConsumos invConsumos2) {
        InvConsumos invConsumos = new InvConsumos();
        invConsumos.setInvConsumosPK(invConsumos2.getInvConsumosPK());
        invConsumos.setConsReferencia(invConsumos2.getConsReferencia());
        invConsumos.setConsFecha(invConsumos2.getConsFecha());
        invConsumos.setConsObservaciones(invConsumos2.getConsObservaciones());
        invConsumos.setConsPendiente(invConsumos2.getConsPendiente());
        invConsumos.setConsRevisado(invConsumos2.getConsRevisado());
        invConsumos.setConsAnulado(invConsumos2.getConsAnulado());
        invConsumos.setUsrEmpresa(invConsumos2.getUsrEmpresa());
        invConsumos.setUsrCodigo(invConsumos2.getUsrCodigo());
        invConsumos.setUsrFechaInserta(invConsumos2.getUsrFechaInserta());
        invConsumos.setConsCodigo(invConsumos2.getConsCodigo());
        invConsumos.setProCantidad(invConsumos2.getProCantidad());
        invConsumos.setInvBodega(invConsumos2.getInvBodega());
        invConsumos.setInvCliente(invConsumos2.getInvCliente());
        invConsumos.setInvProveedor(invConsumos2.getInvProveedor());
        invConsumos.setRhEmpleado(invConsumos2.getRhEmpleado());
        invConsumos.setInvProducto(invConsumos2.getInvProducto());

        return invConsumos;
    }

    public static InvPedidosOrdenCompra convertirInvPedidosOrdenCompra_InvPedidosOrdenCompra(InvPedidosOrdenCompra invPedidosOrdenCompraAux) {
        InvPedidosOrdenCompra oc = new InvPedidosOrdenCompra();
        oc.setInvCliente(invPedidosOrdenCompraAux.getInvCliente());
        oc.setInvPedidosOrdenCompraDetalleList(invPedidosOrdenCompraAux.getInvPedidosOrdenCompraDetalleList());
        oc.setInvPedidosOrdenCompraMotivo(invPedidosOrdenCompraAux.getInvPedidosOrdenCompraMotivo());
        oc.setInvPedidosOrdenCompraPK(invPedidosOrdenCompraAux.getInvPedidosOrdenCompraPK());
        oc.setInvProveedor(invPedidosOrdenCompraAux.getInvProveedor());
        oc.setOcAnulado(invPedidosOrdenCompraAux.getOcAnulado());
        oc.setOcAprobada(invPedidosOrdenCompraAux.getOcAprobada());

        oc.setOcBase0(invPedidosOrdenCompraAux.getOcBase0());
        oc.setOcBaseImponible(invPedidosOrdenCompraAux.getOcBaseImponible());
        oc.setOcCerrada(invPedidosOrdenCompraAux.isOcCerrada());
        oc.setOcCodigoTransaccional(invPedidosOrdenCompraAux.getOcCodigoTransaccional());
        oc.setOcContactoNombre(invPedidosOrdenCompraAux.getOcContactoNombre());
        oc.setOcContactoTelefono(invPedidosOrdenCompraAux.getOcContactoTelefono());
        oc.setOcFechaEmision(invPedidosOrdenCompraAux.getOcFechaEmision());
        oc.setOcFechaHoraAprobado(invPedidosOrdenCompraAux.getOcFechaHoraAprobado());
        oc.setOcFechaHoraEntrega(invPedidosOrdenCompraAux.getOcFechaHoraEntrega());
        oc.setOcFormaPago(invPedidosOrdenCompraAux.getOcFormaPago());
        oc.setOcIvaVigente(invPedidosOrdenCompraAux.getOcIvaVigente());
        oc.setOcLugarEntrega(invPedidosOrdenCompraAux.getOcLugarEntrega());
        oc.setOcMontoIva(invPedidosOrdenCompraAux.getOcMontoIva());

        oc.setOcMontoTotal(invPedidosOrdenCompraAux.getOcMontoTotal());
        oc.setOcMotivoAnulacion(invPedidosOrdenCompraAux.getOcMotivoAnulacion());
        oc.setOcMotivoDesmayorizar(invPedidosOrdenCompraAux.getOcMotivoDesmayorizar());
        oc.setOcObservacionesRegistra(invPedidosOrdenCompraAux.getOcObservacionesRegistra());
        oc.setOcPendiente(invPedidosOrdenCompraAux.isOcPendiente());
        oc.setOcValorRetencion(invPedidosOrdenCompraAux.getOcValorRetencion());
        oc.setUsrAprueba(invPedidosOrdenCompraAux.getUsrAprueba());
        oc.setUsrCodigo(invPedidosOrdenCompraAux.getUsrCodigo());
        oc.setUsrCodigoModifica(invPedidosOrdenCompraAux.getUsrCodigoModifica());
        oc.setUsrFechaInserta(invPedidosOrdenCompraAux.getUsrFechaInserta());
        oc.setUsrFechaModifica(invPedidosOrdenCompraAux.getUsrFechaModifica());
        return oc;
    }

    public static InvPedidos convertirInvPedidos_InvPedidos(InvPedidos invPedidosAux) {
        InvPedidos op = new InvPedidos();
        op.setInvCliente(invPedidosAux.getInvCliente());
        op.setInvPedidosDetalleList(invPedidosAux.getInvPedidosDetalleList());
        op.setInvPedidosMotivo(invPedidosAux.getInvPedidosMotivo());
        op.setInvPedidosPK(invPedidosAux.getInvPedidosPK());
        op.setPedAnulado(invPedidosAux.getPedAnulado());
        op.setPedAprobado(invPedidosAux.getPedAprobado());
        op.setPedCodigoTransaccional(invPedidosAux.getPedCodigoTransaccional());
        op.setPedContactoNombre(invPedidosAux.getPedContactoNombre());
        op.setPedContactoTelefono(invPedidosAux.getPedContactoTelefono());
        op.setPedEjecutado(invPedidosAux.getPedEjecutado());
        op.setPedFechaEmision(invPedidosAux.getPedFechaEmision());
        op.setPedFechaHoraEntrega(invPedidosAux.getPedFechaHoraEntrega());
        op.setPedFechaVencimiento(invPedidosAux.getPedFechaVencimiento());
        op.setPedFormaCobro(invPedidosAux.getPedFormaCobro());
        op.setPedLugarEntrega(invPedidosAux.getPedLugarEntrega());
        op.setPedMontoTotal(invPedidosAux.getPedMontoTotal());
        op.setPedMotivoAnulacion(invPedidosAux.getPedMotivoAnulacion());
        op.setPedObservacionesAprueba(invPedidosAux.getPedObservacionesAprueba());
        op.setPedObservacionesEjecuta(invPedidosAux.getPedObservacionesEjecuta());
        op.setPedObservacionesRegistra(invPedidosAux.getPedObservacionesRegistra());
        op.setPedOrdenCompra(invPedidosAux.getPedOrdenCompra());
        op.setPedPendiente(invPedidosAux.getPedPendiente());
        op.setUsrAprueba(invPedidosAux.getUsrAprueba());
        op.setUsrEjecuta(invPedidosAux.getUsrEjecuta());
        op.setUsrRegistra(invPedidosAux.getUsrRegistra());
        op.setUsrFechaAprobada(invPedidosAux.getUsrFechaAprobada());
        op.setUsrFechaInserta(invPedidosAux.getUsrFechaInserta());

        return op;
    }

    public static InvGuiaRemisionDetalle convertirInvGuiaRemisionDetalles_InvGuiaRemisionDetalle(InvGuiaRemisionDetalle invGuiaRemisionDetalleAux) {
        InvGuiaRemisionDetalle ivGuiaRemisionDetalle = new InvGuiaRemisionDetalle();
        ivGuiaRemisionDetalle.setDetSecuencial(invGuiaRemisionDetalleAux.getDetSecuencial());
        ivGuiaRemisionDetalle.setInvGuiaRemision(invGuiaRemisionDetalleAux.getInvGuiaRemision());
        ivGuiaRemisionDetalle.setDetOrden(invGuiaRemisionDetalleAux.getDetOrden());
        ivGuiaRemisionDetalle.setDetCantidad(invGuiaRemisionDetalleAux.getDetCantidad());
        ivGuiaRemisionDetalle.setNombreProducto(invGuiaRemisionDetalleAux.getNombreProducto());
        ivGuiaRemisionDetalle.setInvProducto(invGuiaRemisionDetalleAux.getInvProducto());

        return ivGuiaRemisionDetalle;
    }

    public static InvVentasDetalle convertirInvVentasDetalle_InvVentasDetalle(InvVentasDetalle invVentasDetalleAux) {
        InvVentasDetalle detalle = new InvVentasDetalle();
        detalle.setDetSecuencial(invVentasDetalleAux.getDetSecuencial());
        detalle.setDetOrden(invVentasDetalleAux.getDetOrden());
        detalle.setDetPendiente(invVentasDetalleAux.getDetPendiente());
        detalle.setDetCantidad(invVentasDetalleAux.getDetCantidad());
        detalle.setDetPrecio(invVentasDetalleAux.getDetPrecio());
        detalle.setDetParcial(invVentasDetalleAux.getDetParcial());
        detalle.setDetPorcentajeRecargo(invVentasDetalleAux.getDetPorcentajeRecargo());
        detalle.setDetPorcentajeDescuento(invVentasDetalleAux.getDetPorcentajeDescuento());
        detalle.setDetValorPromedio(invVentasDetalleAux.getDetValorPromedio());
        detalle.setDetValorUltimaCompra(invVentasDetalleAux.getDetValorUltimaCompra());
        detalle.setDetSaldo(invVentasDetalleAux.getDetSaldo());
        detalle.setProNombre(invVentasDetalleAux.getProNombre());
        detalle.setProCreditoTributario(invVentasDetalleAux.getProCreditoTributario());
        detalle.setSecEmpresa(invVentasDetalleAux.getSecEmpresa());
        detalle.setSecCodigo(invVentasDetalleAux.getSecCodigo());
        detalle.setPisEmpresa(invVentasDetalleAux.getPisEmpresa());
        detalle.setPisSector(invVentasDetalleAux.getPisSector());
        detalle.setPisNumero(invVentasDetalleAux.getPisNumero());
        detalle.setInvVentas(invVentasDetalleAux.getInvVentas());
        detalle.setInvProducto(invVentasDetalleAux.getInvProducto());
        detalle.setInvBodega(invVentasDetalleAux.getInvBodega());
        detalle.setDetMontoIce(invVentasDetalleAux.getDetMontoIce());
        detalle.setIceTarifaFija(invVentasDetalleAux.getIceTarifaFija());
        detalle.setIcePorcentaje(invVentasDetalleAux.getIcePorcentaje());
        detalle.setIceCodigo(invVentasDetalleAux.getIceCodigo());
        detalle.setProComplementario(invVentasDetalleAux.getProComplementario());
        detalle.setDetObservaciones(invVentasDetalleAux.getDetObservaciones());
        detalle.setDetEmpaque(invVentasDetalleAux.getDetEmpaque());
        detalle.setDetEmpaqueCantidad(invVentasDetalleAux.getDetEmpaqueCantidad());
        detalle.setDetConversionPesoNeto(invVentasDetalleAux.getDetConversionPesoNeto());

        return detalle;
    }

    public static InvProductoTipo convertirInvProductoTipo_InvProductoTipo(InvProductoTipo invProductoTipoAux) {
        InvProductoTipo invProductoTipo = new InvProductoTipo();
        invProductoTipo.setInvProductoTipoPK(invProductoTipoAux.getInvProductoTipoPK());
        invProductoTipo.setTipDetalle(invProductoTipoAux.getTipDetalle());
        invProductoTipo.setTipTipo(invProductoTipoAux.getTipTipo());
        invProductoTipo.setTipActivo(invProductoTipoAux.getTipActivo());
        invProductoTipo.setUsrEmpresa(invProductoTipoAux.getUsrEmpresa());
        invProductoTipo.setUsrCodigo(invProductoTipoAux.getUsrCodigo());
        invProductoTipo.setUsrFechaInserta(invProductoTipoAux.getUsrFechaInserta());
        invProductoTipo.setCtaCodigo(invProductoTipoAux.getCtaCodigo());
        invProductoTipo.setCtaEmpresa(invProductoTipoAux.getCtaEmpresa());
        invProductoTipo.setTipReplicar(invProductoTipoAux.isTipReplicar());
        return invProductoTipo;
    }

    public static InvTransferencias convertirInvTransferencias_InvTransferencias(InvTransferencias invTransferenciasAux) {
        InvTransferencias invTransferencias = new InvTransferencias();
        invTransferencias.setInvTransferenciasPK(invTransferenciasAux.getInvTransferenciasPK());
        invTransferencias.setTransFecha(invTransferenciasAux.getTransFecha());
        invTransferencias.setTransObservaciones(invTransferenciasAux.getTransObservaciones());
        invTransferencias.setTransPendiente(invTransferenciasAux.getTransPendiente());
        invTransferencias.setTransRevisado(invTransferenciasAux.getTransRevisado());
        invTransferencias.setTransAnulado(invTransferenciasAux.getTransAnulado());
        invTransferencias.setUsrEmpresa(invTransferenciasAux.getUsrEmpresa());
        invTransferencias.setUsrCodigo(invTransferenciasAux.getUsrCodigo());
        invTransferencias.setUsrFechaInserta(invTransferenciasAux.getUsrFechaInserta());
        if (invTransferenciasAux.getInvBodega() != null) {
            invTransferenciasAux.getInvBodega().setInvTransferenciasDetalleList(new ArrayList<>());
            invTransferenciasAux.getInvBodega().setInvTransferenciasDetalleList1(new ArrayList<>());
        }
        invTransferencias.setInvBodega(invTransferenciasAux.getInvBodega());
        if (invTransferenciasAux.getInvBodega() != null) {
            invTransferenciasAux.getInvBodega1().setInvTransferenciasDetalleList(new ArrayList<>());
            invTransferenciasAux.getInvBodega1().setInvTransferenciasDetalleList1(new ArrayList<>());
        }

        invTransferencias.setInvBodega1(invTransferenciasAux.getInvBodega1());
        invTransferencias.setInvTransferenciasMotivoAnulacionList(new ArrayList<>());
        return invTransferencias;
    }

    public static InvProformas convertirInvProformas_InvProformas(InvProformas invProformasAux) {
        InvProformas invProformas = new InvProformas();
        invProformas.setInvProformasPK(invProformasAux.getInvProformasPK());
        invProformas.setProfFecha(invProformasAux.getProfFecha());
        invProformas.setProfIvaVigente(invProformasAux.getProfIvaVigente());
        invProformas.setProfObservaciones(invProformasAux.getProfObservaciones());
        invProformas.setProfPendiente(invProformasAux.getProfPendiente());
        invProformas.setProfAnulado(invProformasAux.getProfAnulado());
        invProformas.setProfBase0(invProformasAux.getProfBase0());
        invProformas.setProfBaseimponible(invProformasAux.getProfBaseimponible());
        invProformas.setProfDescuentoBase0(invProformasAux.getProfDescuentoBase0());
        invProformas.setProfDescuentoBaseimponible(invProformasAux.getProfDescuentoBaseimponible());
        invProformas.setProfSubtotalBase0(invProformasAux.getProfSubtotalBase0());
        invProformas.setProfSubtotalBaseimponible(invProformasAux.getProfSubtotalBaseimponible());
        invProformas.setProfMontoiva(invProformasAux.getProfMontoiva());
        invProformas.setProfTotal(invProformasAux.getProfTotal());
        invProformas.setUsrEmpresa(invProformasAux.getUsrEmpresa());
        invProformas.setUsrCodigo(invProformasAux.getUsrCodigo());
        invProformas.setUsrFechaInserta(invProformasAux.getUsrFechaInserta());
        invProformas.setInvCliente(invProformasAux.getInvCliente());
        return invProformas;
    }

    public static InvProformasDetalle convertirInvProformasDetalle_InvProformasDetalle(InvProformasDetalle detalleAux) {
        InvProformasDetalle det = new InvProformasDetalle();
        det.setDetSecuencial(detalleAux.getDetSecuencial());
        det.setDetOrden(detalleAux.getDetOrden());
        det.setDetCantidad(detalleAux.getDetCantidad());
        det.setDetPrecio(detalleAux.getDetPrecio());
        det.setDetPorcentajeRecargo(detalleAux.getDetPorcentajeRecargo());
        det.setDetPorcentajeDescuento(detalleAux.getDetPorcentajeDescuento());
        det.setProNombre(detalleAux.getProNombre());
        det.setInvProformas(detalleAux.getInvProformas());
        det.setInvProducto(detalleAux.getInvProducto());
        return det;
    }

    public static AnxVentaReembolso convertirAnxVentaReembolsoTO_AnxVentaReembolso(AnxVentaReembolsoTO anxVentaReembolsoTO) {
        AnxVentaReembolso anxVentaReembolso = new AnxVentaReembolso();

        InvVentas venta = new InvVentas();
        venta.setInvVentasPK(new InvVentasPK(
                anxVentaReembolsoTO.getVtaEmpresa(),
                anxVentaReembolsoTO.getVtaPeriodo(),
                anxVentaReembolsoTO.getVtaMotivo(),
                anxVentaReembolsoTO.getVtaNumero()));
        anxVentaReembolso.setInvVenta(venta);
        InvCompras invCompra = new InvCompras();
        invCompra.setInvComprasPK(new InvComprasPK(
                anxVentaReembolsoTO.getCompEmpresa(),
                anxVentaReembolsoTO.getCompPeriodo(),
                anxVentaReembolsoTO.getCompMotivo(),
                anxVentaReembolsoTO.getCompNumero()));
        anxVentaReembolso.setInvCompra(invCompra);
        anxVentaReembolso.setReembSecuencial(anxVentaReembolsoTO.getReembSecuencial());
        return anxVentaReembolso;
    }
}
