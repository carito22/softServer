package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.service.PorcentajeIvaService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProductoSaldosDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.NumeroColumnaDesconocidadFunListaProductosSaldosGeneral;
import ec.com.todocompu.ShrimpSoftUtils.RetornoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListaProductosSaldosGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvKardexTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaBodegasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosCambiarPrecioCantidadTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosCambiarPrecioTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosGeneralTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProductosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoSaldos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProductoSaldosServiceImpl implements ProductoSaldosService {

    @Autowired
    private ProductoSaldosDao productoSaldosDao;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private BodegaService bodegaService;
    @Autowired
    private ConsumosMotivoService consumosMotivoService;
    @Autowired
    private TransferenciasMotivoService transferenciasMotivoService;
    @Autowired
    private PorcentajeIvaService porcentajeIvaService;
    private BigDecimal cero = new BigDecimal("0.00");

    public List<InvListaProductosTO> getListaProductosTO(String empresa, String busqueda, String bodega,
            String categoria, String fecha, boolean incluirInactivos, boolean limite, boolean codigo) throws Exception {
        return productoSaldosDao.getListaProductosTO(empresa, busqueda, bodega, categoria, fecha, incluirInactivos,
                limite, codigo);
    }

    @Override
    public List<InvListaProductosGeneralTO> getListaProductosTOWeb(String empresa, String busqueda, String bodega, String categoria, String fecha,
            Integer precio, boolean incluirInactivos, boolean limite, boolean codigo, String buscarPorcodigo, String tipo) throws Exception {
        return productoSaldosDao.getListaProductosTOWeb(empresa, busqueda, bodega, categoria, fecha, precio, incluirInactivos, limite, codigo, buscarPorcodigo, tipo);
    }

    public InvProductoSaldos getInvProductoSaldo(String empresa, String codigoBodega, String codigoProducto)
            throws Exception {
        return productoSaldosDao.getInvProductoSaldo(empresa, codigoBodega, codigoProducto);
    }

    @Override
    public List<InvKardexTO> getListaInvKardexTO(String empresa, String bodega, String producto, String desde,
            String hasta, String promedio) throws Exception {
        return productoSaldosDao.getListaInvKardexTO(empresa, bodega, producto, desde, hasta, promedio);
    }

    @Override
    public List<InvKardexTO> getListaInvKardexTO(String empresa, String bodega, String producto, String desde, String hasta, String promedio, boolean incluirTodos) throws Exception {
        return productoSaldosDao.getListaInvKardexTO(empresa, bodega, producto, desde, hasta, promedio, incluirTodos);
    }

    public List<InvListaProductosCambiarPrecioTO> getListaProductosCambiarPrecioTO(String empresa, String busqueda,
            String bodega, String fecha) throws Exception {
        return productoSaldosDao.getListaProductosCambiarPrecioTO(empresa, busqueda, bodega, fecha);
    }

    public List<InvListaProductosCambiarPrecioCantidadTO> getListaProductosCambiarPrecioCantidadTO(String empresa,
            String busqueda, String bodega, String fecha) throws Exception {
        return productoSaldosDao.getListaProductosCambiarPrecioCantidadTO(empresa, busqueda, bodega, fecha);
    }

    public RetornoTO getInvFunListaProductosSaldosGeneralTO(String empresa, String producto, String fecha,
            boolean estado, String usuario) throws Exception {
        String mensaje = "";
        RetornoTO retornoTO = new RetornoTO();
        try {
            List<InvFunListaProductosSaldosGeneralTO> funListaProductosSaldosGeneralTOs = productoSaldosDao
                    .getInvFunListaProductosSaldosGeneralTO(empresa, producto, fecha, estado);
            mensaje = "T";
            NumeroColumnaDesconocidadFunListaProductosSaldosGeneral obj = new NumeroColumnaDesconocidadFunListaProductosSaldosGeneral();
            obj.agruparCabeceraColumnas(funListaProductosSaldosGeneralTOs);
            obj.agruparProductos(funListaProductosSaldosGeneralTOs);
            obj.llenarObjetoParaTabla(funListaProductosSaldosGeneralTOs);
            retornoTO.setColumnasFaltantes(obj.getColumnasFaltantes());
            retornoTO.setColumnas(obj.getColumnas());
            retornoTO.setDatos(obj.getDatos());
        } catch (Exception e) {
            mensaje = "FOcurrió un error al obtener los datos de la Base de Datos. \nContacte con el administrador...";
        }
        retornoTO.setMensaje(mensaje);
        return retornoTO;
    }

    @Override
    public List<String> verificarStockVentas(List<InvVentasDetalle> listInvVentasDetalle) throws Exception {
        List<String> verificar = new ArrayList<>();
        List<InvVentasDetalle> listadoDetalleVentas = agrupraProductosBodegaVenta(listInvVentasDetalle);
        for (int i = 0; i < listadoDetalleVentas.size(); i++) {
            if (!listadoDetalleVentas.get(i).getInvProducto().getInvProductoTipo().getTipTipo().equals("COSTO O GASTO")
                    && !listadoDetalleVentas.get(i).getInvProducto().getInvProductoTipo().getTipTipo().equals("SERVICIOS")
                    && !listadoDetalleVentas.get(i).getInvProducto().getInvProductoTipo().getTipTipo().equals("MERCADERIA EN TRANSITO")
                    && !listadoDetalleVentas.get(i).getInvProducto().getInvProductoTipo().getTipTipo().equals("CONSTRUCCION EN CURSO")
                    && !listadoDetalleVentas.get(i).getInvProducto().getInvProductoTipo().getTipTipo().equals("ACTIVO FIJO")) {

                InvProductoSaldos invProductoSaldosConsulta = productoSaldosDao.getInvProductoSaldo(
                        listadoDetalleVentas.get(i).getInvBodega().getInvBodegaPK().getBodEmpresa(),
                        listadoDetalleVentas.get(i).getInvBodega().getInvBodegaPK().getBodCodigo(),
                        listadoDetalleVentas.get(i).getInvProducto().getInvProductoPK().getProCodigoPrincipal());
                if (invProductoSaldosConsulta != null) {
                    BigDecimal saldoFinal = invProductoSaldosConsulta.getStkSaldoFinal().subtract(listadoDetalleVentas.get(i).getDetCantidad());

                    if (saldoFinal.compareTo(cero) < 0) {
                        verificar.add(listadoDetalleVentas.get(i).getInvBodega().getInvBodegaPK().getBodCodigo() + "|"
                                + listadoDetalleVentas.get(i).getInvBodega().getBodNombre() + "\t"
                                + listadoDetalleVentas.get(i).getInvProducto().getInvProductoPK()
                                        .getProCodigoPrincipal()
                                + " \t" + listadoDetalleVentas.get(i).getInvProducto().getProNombre() + " : "
                                + listadoDetalleVentas.get(i).getDetCantidad() + " - Saldo : "
                                + invProductoSaldosConsulta.getStkSaldoFinal().setScale(2, BigDecimal.ROUND_HALF_UP));
                    }
                } else {
                    verificar.add(listadoDetalleVentas.get(i).getInvBodega().getInvBodegaPK().getBodCodigo() + "|"
                            + listadoDetalleVentas.get(i).getInvBodega().getBodNombre() + "\t\t"
                            + listadoDetalleVentas.get(i).getInvProducto().getInvProductoPK().getProCodigoPrincipal()
                            + " \t\t" + listadoDetalleVentas.get(i).getInvProducto().getProNombre());
                }
            }
        }
        return verificar;
    }

    private List<InvVentasDetalle> agrupraProductosBodegaVenta(List<InvVentasDetalle> ventasDetalle) throws Exception {
        List<InvVentasDetalle> listaDetalleFinal = new ArrayList<InvVentasDetalle>();

        listaDetalleFinal.add(ConversionesInventario.convertirInvVentaDetalle_InvVentaDetalle(ventasDetalle.get(0)));

        int contador = 0;
        boolean encontro = false;

        for (int i = 1; i < ventasDetalle.size(); i++) {
            contador = 0;
            for (InvVentasDetalle invVentasDetalleAux : listaDetalleFinal) {
                if (ventasDetalle.get(i).getInvProducto().getInvProductoPK().getProCodigoPrincipal()
                        .equals(invVentasDetalleAux.getInvProducto().getInvProductoPK().getProCodigoPrincipal())
                        && ventasDetalle.get(i).getInvBodega().getInvBodegaPK().getBodCodigo()
                                .equals(invVentasDetalleAux.getInvBodega().getInvBodegaPK().getBodCodigo())) {
                    encontro = true;
                    break;
                } else {
                    encontro = false;
                    contador++;
                }
            }

            if (encontro) {
                listaDetalleFinal.get(contador).setDetCantidad(
                        listaDetalleFinal.get(contador).getDetCantidad().add(ventasDetalle.get(i).getDetCantidad()));
                listaDetalleFinal.get(contador).setDetPrecio(
                        listaDetalleFinal.get(contador).getDetPrecio().add(ventasDetalle.get(i).getDetPrecio()));
            } else {
                listaDetalleFinal
                        .add(ConversionesInventario.convertirInvVentaDetalle_InvVentaDetalle(ventasDetalle.get(i)));
            }
        }

        return listaDetalleFinal;
    }

    @Override
    public Map<String, Object> obtenerDatosParaKardex(String empresa) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        List<SisPeriodo> listaPeriodos = new ArrayList<SisPeriodo>();
        List<InvConsumosMotivoTO> listaMotivos = new ArrayList<InvConsumosMotivoTO>();
        List<InvListaBodegasTO> listaBodegas = new ArrayList<InvListaBodegasTO>();
        List<InvTransferenciaMotivoTO> listaMotivosTransferencias = new ArrayList<InvTransferenciaMotivoTO>();
        listaMotivosTransferencias = transferenciasMotivoService.getListaTransferenciaMotivoTO(empresa, false);
        listaPeriodos = periodoService.getListaPeriodo(empresa);
        listaMotivos = consumosMotivoService.getInvListaConsumoMotivoTO(empresa, false, null);
        listaBodegas = bodegaService.buscarBodegasTO(empresa, true, null);

        campos.put("listadoPeriodos", listaPeriodos);
        campos.put("listadoMotivos", listaMotivos);
        campos.put("listadoBodegas", listaBodegas);
        campos.put("listadoMotivosTransferencias", listaMotivosTransferencias);

        return campos;
    }

    @Override
    public List<InvListaProductosGeneralTO> importarProductosSinStock(String empresa, String categoria) throws Exception {
        return productoSaldosDao.importarProductosSinStock(empresa, categoria);
    }

    @Override
    public List<InvListaProductosCambiarPrecioTO> formatearListadoInvListaProductosCambiarPrecioTO(List<InvListaProductosCambiarPrecioTO> listado) throws Exception {
        List<InvListaProductosCambiarPrecioTO> invListaProductosCambiarPrecioTO = listado;
        BigDecimal ivaVigente = porcentajeIvaService.getValorAnxPorcentajeIvaTO("");
        for (int i = 0; i < invListaProductosCambiarPrecioTO.size(); i++) {
            if (invListaProductosCambiarPrecioTO.get(i).getStockGrabaIva().equals("GRAVA")) {
                invListaProductosCambiarPrecioTO.get(i).setStockUltimoCosto(invListaProductosCambiarPrecioTO.get(i).getStockUltimoCosto() == null ? cero : invListaProductosCambiarPrecioTO.get(i).getStockUltimoCosto());
                invListaProductosCambiarPrecioTO.get(i).setStockPrecio1(invListaProductosCambiarPrecioTO.get(i).getStockPrecio1() == null ? cero : invListaProductosCambiarPrecioTO.get(i).getStockPrecio1());
                invListaProductosCambiarPrecioTO.get(i).setStockDescuento1(invListaProductosCambiarPrecioTO.get(i).getStockDescuento1() == null ? cero : invListaProductosCambiarPrecioTO.get(i).getStockDescuento1());
                invListaProductosCambiarPrecioTO.get(i).setStockPrecio2(invListaProductosCambiarPrecioTO.get(i).getStockPrecio2() == null ? cero : invListaProductosCambiarPrecioTO.get(i).getStockPrecio2());
                invListaProductosCambiarPrecioTO.get(i).setStockDescuento2(invListaProductosCambiarPrecioTO.get(i).getStockDescuento2() == null ? cero : invListaProductosCambiarPrecioTO.get(i).getStockDescuento2());
                invListaProductosCambiarPrecioTO.get(i).setStockPrecio3(invListaProductosCambiarPrecioTO.get(i).getStockPrecio3() == null ? cero : invListaProductosCambiarPrecioTO.get(i).getStockPrecio3());
                invListaProductosCambiarPrecioTO.get(i).setStockDescuento3(invListaProductosCambiarPrecioTO.get(i).getStockDescuento3() == null ? cero : invListaProductosCambiarPrecioTO.get(i).getStockDescuento3());
                invListaProductosCambiarPrecioTO.get(i).setStockPrecio4(invListaProductosCambiarPrecioTO.get(i).getStockPrecio4() == null ? cero : invListaProductosCambiarPrecioTO.get(i).getStockPrecio4());
                invListaProductosCambiarPrecioTO.get(i).setStockDescuento4(invListaProductosCambiarPrecioTO.get(i).getStockDescuento4() == null ? cero : invListaProductosCambiarPrecioTO.get(i).getStockDescuento4());
                invListaProductosCambiarPrecioTO.get(i).setStockPrecio5(invListaProductosCambiarPrecioTO.get(i).getStockPrecio5() == null ? cero : invListaProductosCambiarPrecioTO.get(i).getStockPrecio5());
                invListaProductosCambiarPrecioTO.get(i).setStockDescuento5(invListaProductosCambiarPrecioTO.get(i).getStockDescuento5() == null ? cero : invListaProductosCambiarPrecioTO.get(i).getStockDescuento5());

                BigDecimal multiplicacion = cero;
                BigDecimal division = cero;
                BigDecimal suma = cero;
                multiplicacion = invListaProductosCambiarPrecioTO.get(i).getStockUltimoCosto().multiply(ivaVigente);
                division = multiplicacion.divide(new BigDecimal("100.00"), 6, RoundingMode.HALF_UP);
                suma = invListaProductosCambiarPrecioTO.get(i).getStockUltimoCosto().add(division);
                invListaProductosCambiarPrecioTO.get(i).setStockUltimoCosto(suma);

                multiplicacion = cero;
                suma = cero;
                division = cero;
                multiplicacion = invListaProductosCambiarPrecioTO.get(i).getStockPrecio1().multiply(ivaVigente);
                division = multiplicacion.divide(new BigDecimal("100.00"), 6, RoundingMode.HALF_EVEN);
                suma = invListaProductosCambiarPrecioTO.get(i).getStockPrecio1().add(division);
                invListaProductosCambiarPrecioTO.get(i).setStockPrecio1(suma);

                multiplicacion = cero;
                suma = cero;
                division = cero;
                multiplicacion = invListaProductosCambiarPrecioTO.get(i).getStockPrecio2().multiply(ivaVigente);
                division = multiplicacion.divide(new BigDecimal("100.00"), 6, RoundingMode.HALF_EVEN);
                suma = invListaProductosCambiarPrecioTO.get(i).getStockPrecio2().add(division);
                invListaProductosCambiarPrecioTO.get(i).setStockPrecio2(suma);

                multiplicacion = cero;
                suma = cero;
                division = cero;
                multiplicacion = invListaProductosCambiarPrecioTO.get(i).getStockPrecio3().multiply(ivaVigente);
                division = multiplicacion.divide(new BigDecimal("100.00"), 6, RoundingMode.HALF_EVEN);
                suma = invListaProductosCambiarPrecioTO.get(i).getStockPrecio3().add(division);
                invListaProductosCambiarPrecioTO.get(i).setStockPrecio3(suma);

                multiplicacion = cero;
                suma = cero;
                division = cero;
                multiplicacion = invListaProductosCambiarPrecioTO.get(i).getStockPrecio4().multiply(ivaVigente);
                division = multiplicacion.divide(new BigDecimal("100.00"), 6, RoundingMode.HALF_EVEN);
                suma = invListaProductosCambiarPrecioTO.get(i).getStockPrecio4().add(division);
                invListaProductosCambiarPrecioTO.get(i).setStockPrecio4(suma);

                multiplicacion = cero;
                suma = cero;
                division = cero;
                multiplicacion = invListaProductosCambiarPrecioTO.get(i).getStockPrecio5().multiply(ivaVigente);
                division = multiplicacion.divide(new BigDecimal("100.00"), 6, RoundingMode.HALF_EVEN);
                suma = invListaProductosCambiarPrecioTO.get(i).getStockPrecio5().add(division);
                invListaProductosCambiarPrecioTO.get(i).setStockPrecio5(suma);

                invListaProductosCambiarPrecioTO.get(i).setStockPrecio1(invListaProductosCambiarPrecioTO.get(i).getStockPrecio1() == cero ? null : invListaProductosCambiarPrecioTO.get(i).getStockPrecio1());
                invListaProductosCambiarPrecioTO.get(i).setStockPrecio2(invListaProductosCambiarPrecioTO.get(i).getStockPrecio2() == cero ? null : invListaProductosCambiarPrecioTO.get(i).getStockPrecio2());
                invListaProductosCambiarPrecioTO.get(i).setStockPrecio3(invListaProductosCambiarPrecioTO.get(i).getStockPrecio3() == cero ? null : invListaProductosCambiarPrecioTO.get(i).getStockPrecio3());
                invListaProductosCambiarPrecioTO.get(i).setStockPrecio4(invListaProductosCambiarPrecioTO.get(i).getStockPrecio4() == cero ? null : invListaProductosCambiarPrecioTO.get(i).getStockPrecio4());
                invListaProductosCambiarPrecioTO.get(i).setStockPrecio5(invListaProductosCambiarPrecioTO.get(i).getStockPrecio5() == cero ? null : invListaProductosCambiarPrecioTO.get(i).getStockPrecio5());

                invListaProductosCambiarPrecioTO.get(i).setStockDescuento1(invListaProductosCambiarPrecioTO.get(i).getStockDescuento1() == cero ? null : invListaProductosCambiarPrecioTO.get(i).getStockDescuento1());
                invListaProductosCambiarPrecioTO.get(i).setStockDescuento2(invListaProductosCambiarPrecioTO.get(i).getStockDescuento2() == cero ? null : invListaProductosCambiarPrecioTO.get(i).getStockDescuento2());
                invListaProductosCambiarPrecioTO.get(i).setStockDescuento3(invListaProductosCambiarPrecioTO.get(i).getStockDescuento3() == cero ? null : invListaProductosCambiarPrecioTO.get(i).getStockDescuento3());
                invListaProductosCambiarPrecioTO.get(i).setStockDescuento4(invListaProductosCambiarPrecioTO.get(i).getStockDescuento4() == cero ? null : invListaProductosCambiarPrecioTO.get(i).getStockDescuento4());
                invListaProductosCambiarPrecioTO.get(i).setStockDescuento5(invListaProductosCambiarPrecioTO.get(i).getStockDescuento5() == cero ? null : invListaProductosCambiarPrecioTO.get(i).getStockDescuento5());
            }
        }

        return invListaProductosCambiarPrecioTO;

    }

}
