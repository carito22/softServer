package ec.com.todocompu.ShrimpSoftServer.inventario.controller;

import ec.com.todocompu.ShrimpSoftServer.anexos.service.AnexoPorcentajeIceService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.ContableService;
import ec.com.todocompu.ShrimpSoftServer.inventario.report.ReporteInventarioService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.BodegaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ClienteCategoriaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ClienteService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasDetalleService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasFormaPagoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasMotivoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasNumeracionService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasRecepcionService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ConsumosDetalleService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ConsumosMotivoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ConsumosNumeracionService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ConsumosService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.PedidosConfiguracionService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.PedidosMotivoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoCategoriaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoMarcaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoMedidaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoPresentacionCajasService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoPresentacionUnidadesService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoSaldosService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ProductoService;
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
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VentasDetalleService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VentasFormaPagoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VentasMotivoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VentasNumeracionService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.VentasService;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.RetornoTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraReembolsoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxVentaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxPorcentajeIce;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.*;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvAdjuntosCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvBodega;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionInp;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProducto;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedor;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentasMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.DatoFunListaProductosImpresionPlaca;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.InvListaConsultaCompra;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.InvListaConsultaConsumo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.InvListaConsultaTransferencia;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.InvListaConsultaVenta;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.ReporteCompraDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.ReporteProformaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.report.ReporteVentaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.util.ArrayList;

@RestController
@RequestMapping("/todocompuWS/inventarioController")
public class InventarioController {

    @Autowired
    private BodegaService bodegaService;
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
    private ProductoService productoService;
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
    private PedidosMotivoService pedidosMotivoService;
    @Autowired
    private PedidosConfiguracionService pedidosConfiguracionService;
    @Autowired
    private EnviarCorreoService envioCorreoService;
    @Autowired
    private AnexoPorcentajeIceService anexoPorcentajeIceService;

    @RequestMapping("/obtenerInvClientePorCedulaRuc")
    public InvCliente obtenerInvClientePorCedulaRuc(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cedulaRuc = UtilsJSON.jsonToObjeto(String.class, map.get("cedulaRuc"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return clienteService.obtenerInvClientePorCedulaRuc(empresa, cedulaRuc);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/obtenerInvProveedorPorCedulaRuc")
    public InvProveedor obtenerInvProveedorPorCedulaRuc(@RequestBody String json) {
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

    @RequestMapping("/accionInvProveedorCategoria")
    public String accionInvProveedorCategoria(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProveedorCategoriaTO invProveedorCategoriaTO = UtilsJSON.jsonToObjeto(InvProveedorCategoriaTO.class,
                map.get("invProveedorCategoriaTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proveedorCategoriaService.accionInvProveedorCategoria(invProveedorCategoriaTO, accion, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvProveedorCategoriaTO")
    public List<InvProveedorCategoriaTO> getInvProveedorCategoriaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proveedorCategoriaService.getInvProveedorCategoriaTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionInvClienteCategoria")
    public String accionInvClienteCategoria(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvClienteCategoriaTO invClienteCategoriaTO = UtilsJSON.jsonToObjeto(InvClienteCategoriaTO.class,
                map.get("invClienteCategoriaTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return clienteCategoriaService.accionInvClienteCategoria(invClienteCategoriaTO, accion, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvClienteCategoriaTO")
    public List<InvClienteCategoriaTO> getInvClienteCategoriaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return clienteCategoriaService.getInvClienteCategoriaTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionInvProductoCategoria")
    public String accionInvProductoCategoria(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoCategoriaTO invProductoCategoriaTO = UtilsJSON.jsonToObjeto(InvProductoCategoriaTO.class,
                map.get("invProductoCategoriaTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoCategoriaService.accionInvProductoCategoria(invProductoCategoriaTO, accion, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvProductoCategoriaTO")
    public List<InvProductoCategoriaTO> getInvProductoCategoriaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoCategoriaService.getInvProductoCategoriaTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionInvProductoMedida")
    public String accionInvProductoMedida(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoMedidaTO invProductoMedidaTO = UtilsJSON.jsonToObjeto(InvProductoMedidaTO.class,
                map.get("invProductoMedidaTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoMedidaService.accionInvProductoMedida(invProductoMedidaTO, accion, sisInfoTO);
        } catch (Exception e) {
        }
        return null;
    }

    @RequestMapping("/getInvProductoMedidaTO")
    public List<InvProductoMedidaTO> getInvProductoMedidaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoMedidaService.getInvProductoMedidaTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/buscarInvProductoDAOTO")
    public InvProductoDAOTO buscarInvProductoDAOTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoProducto = UtilsJSON.jsonToObjeto(String.class, map.get("codigoProducto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoService.buscarInvProductoDAOTO(empresa, codigoProducto);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarProductoTO")
    public String insertarProductoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoTO invProductoTO = UtilsJSON.jsonToObjeto(InvProductoTO.class, map.get("invProductoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoService.insertarInvProductoTO(invProductoTO, sisInfoTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/productoRepetidoCodigoBarra")
    public boolean productoRepetidoCodigoBarra(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String barras = UtilsJSON.jsonToObjeto(String.class, map.get("barras"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoService.productoRepetidoCodigoBarra(empresa, barras);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/modificarProductoTO")
    public String modificarProductoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoTO invProductoTO = UtilsJSON.jsonToObjeto(InvProductoTO.class, map.get("invProductoTO"));
        String codigoCambiarLlave = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCambiarLlave"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoService.modificarInvProductoTO(invProductoTO, codigoCambiarLlave, sisInfoTO);
        } catch (Exception e) {
        }
        return null;
    }

    @RequestMapping("/eliminarInvProductoTO")
    public String eliminarInvProductoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoTO invProductoTO = UtilsJSON.jsonToObjeto(InvProductoTO.class, map.get("invProductoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoService.eliminarInvProductoTO(invProductoTO, sisInfoTO);
        } catch (Exception e) {
        }
        return null;
    }

    @RequestMapping("/insertarClienteTO")
    public MensajeTO insertarClienteTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvClienteTO invClienteTO = UtilsJSON.jsonToObjeto(InvClienteTO.class, map.get("invClienteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = new MensajeTO();
        try {
            mensajeTO.setMensaje(clienteService.insertarInvClienteTO(invClienteTO, new ArrayList<>(), sisInfoTO));
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            mensajeTO.setMensaje("F" + e.getMessage() + "\n\nSe ha enviado un informe al administrador del sistema");
        }
        return mensajeTO;
    }

    @RequestMapping("/modificarClienteTO")
    public MensajeTO modificarClienteTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvClienteTO invClienteTO = UtilsJSON.jsonToObjeto(InvClienteTO.class, map.get("invClienteTO"));
        String codigoAnterior = UtilsJSON.jsonToObjeto(String.class, map.get("codigoAnterior"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = new MensajeTO();
        try {
            mensajeTO.setMensaje(clienteService.modificarInvClienteTO(invClienteTO, codigoAnterior, new ArrayList<>(), sisInfoTO));
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            mensajeTO.setMensaje("F" + e.getMessage() + "\n\nSe ha enviado un informe al administrador del sistema");
        }
        return mensajeTO;
    }

    @RequestMapping("/eliminarInvClienteTO")
    public MensajeTO eliminarInvClienteTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvClienteTO invClienteTO = UtilsJSON.jsonToObjeto(InvClienteTO.class, map.get("invClienteTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = new MensajeTO();
        try {
            mensajeTO.setMensaje(clienteService.eliminarInvClienteTO(invClienteTO, sisInfoTO));
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            mensajeTO.setMensaje("F" + e.getMessage() + "\n\nSe ha enviado un informe al administrador del sistema");
        }
        return mensajeTO;
    }

    @RequestMapping("/getClienteRepetido")
    public boolean getClienteRepetido(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        String id = UtilsJSON.jsonToObjeto(String.class, map.get("id"));
        String nombre = UtilsJSON.jsonToObjeto(String.class, map.get("nombre"));
        String razonSocial = UtilsJSON.jsonToObjeto(String.class, map.get("razonSocial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return clienteService.getClienteRepetido(empresa, codigo, id, nombre, razonSocial);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/insertarProveedorTO")
    public MensajeTO insertarProveedorTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProveedorTO invProveedorTO = UtilsJSON.jsonToObjeto(InvProveedorTO.class, map.get("invProveedorTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = new MensajeTO();
        try {
            mensajeTO.setMensaje(proveedorService.insertarInvProveedorTO(invProveedorTO, sisInfoTO, null));
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            mensajeTO.setMensaje("F" + e.getMessage() + "\n\nSe ha enviado un informe al administrador del sistema");
        }
        return mensajeTO;
    }

    @RequestMapping("/modificarProveedorTO")
    public MensajeTO modificarProveedorTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProveedorTO invProveedorTO = UtilsJSON.jsonToObjeto(InvProveedorTO.class, map.get("invProveedorTO"));
        String codigoAnterior = UtilsJSON.jsonToObjeto(String.class, map.get("codigoAnterior"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = new MensajeTO();
        try {
            mensajeTO.setMensaje(proveedorService.modificarInvProveedorTO(invProveedorTO, codigoAnterior, sisInfoTO, null));
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            mensajeTO.setMensaje("F" + e.getMessage() + "\n\nSe ha enviado un informe al administrador del sistema");
        }
        return mensajeTO;
    }

    @RequestMapping("/eliminarInvProveedorTO")
    public MensajeTO eliminarInvProveedorTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProveedorTO invProveedorTO = UtilsJSON.jsonToObjeto(InvProveedorTO.class, map.get("invProveedorTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = new MensajeTO();
        try {
            mensajeTO.setMensaje(proveedorService.eliminarInvProveedorTO(invProveedorTO, sisInfoTO));
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
            mensajeTO.setMensaje("F" + e.getMessage() + "\n\nSe ha enviado un informe al administrador del sistema");
        }
        return mensajeTO;
    }

    @RequestMapping("/insertarBodegaTO")
    public String insertarBodegaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvBodegaTO invBodegaTO = UtilsJSON.jsonToObjeto(InvBodegaTO.class, map.get("invBodegaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bodegaService.insertarInvBodegaTO(invBodegaTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarBodegaTO")
    public String modificarBodegaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvBodegaTO invBodegaTO = UtilsJSON.jsonToObjeto(InvBodegaTO.class, map.get("invBodegaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bodegaService.modificarInvBodegaTO(invBodegaTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarBodegaTO")
    public String eliminarBodegaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvBodegaTO invBodegaTO = UtilsJSON.jsonToObjeto(InvBodegaTO.class, map.get("invBodegaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bodegaService.eliminarInvBodegaTO(invBodegaTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarInvComprasTO")
    public MensajeTO insertarInvComprasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasTO invComprasTO = UtilsJSON.jsonToObjeto(InvComprasTO.class, map.get("invComprasTO"));
        List<InvComprasDetalleTO> listaInvComprasDetalleTO = UtilsJSON.jsonToList(InvComprasDetalleTO.class,
                map.get("listaInvComprasDetalleTO"));
        AnxCompraTO anxCompraTO = UtilsJSON.jsonToObjeto(AnxCompraTO.class, map.get("anxCompraTO"));
        List<AnxCompraDetalleTO> anxCompraDetalleTO = UtilsJSON.jsonToList(AnxCompraDetalleTO.class,
                map.get("anxCompraDetalleTO"));
        List<AnxCompraReembolsoTO> anxCompraReembolsoTO = UtilsJSON.jsonToList(AnxCompraReembolsoTO.class,
                map.get("anxCompraReembolsoTO"));
        List<AnxCompraFormaPagoTO> anxCompraFormaPagoTO = UtilsJSON.jsonToList(AnxCompraFormaPagoTO.class,
                map.get("anxCompraFormaPagoTO"));
        List<InvAdjuntosCompras> listImagen = UtilsJSON.jsonToList(InvAdjuntosCompras.class, map.get("listImagen"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
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
    public MensajeTO insertarInvVentasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvVentasTO invVentasTO = UtilsJSON.jsonToObjeto(InvVentasTO.class, map.get("invVentasTO"));
        List<InvVentasDetalleTO> listaInvVentasDetalleTO = UtilsJSON.jsonToList(InvVentasDetalleTO.class,
                map.get("listaInvVentasDetalleTO"));
        AnxVentaTO anxVentasTO = UtilsJSON.jsonToObjeto(AnxVentaTO.class, map.get("anxVentasTO"));
        String tipoDocumentoComplemento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumentoComplemento"));
        String numeroDocumentoComplemento = UtilsJSON.jsonToObjeto(String.class, map.get("numeroDocumentoComplemento"));
        Boolean isComprobanteElectronica = UtilsJSON.jsonToObjeto(Boolean.class, map.get("isComprobanteElectronica"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = null;
        try {
            mensajeTO = ventasService.insertarInvVentasTO(invVentasTO, listaInvVentasDetalleTO, anxVentasTO,
                    tipoDocumentoComplemento, numeroDocumentoComplemento, isComprobanteElectronica, false, sisInfoTO);

            if (mensajeTO.getMensaje().charAt(0) == 'T') {
                ventasService.quitarPendiente(invVentasTO);
            }

        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                mensajeTO = new MensajeTO();
                mensajeTO.setMensaje("M<html>" + e.getMessage() + "</html>");
            }
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return mensajeTO;
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
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarInvProformasTO")
    public MensajeTO insertarInvProformasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProformasTO invProformasTO = UtilsJSON.jsonToObjeto(InvProformasTO.class, map.get("invProformasTO"));
        List<InvProformasDetalleTO> listaInvProformasDetalleTO = UtilsJSON.jsonToList(InvProformasDetalleTO.class,
                map.get("listaInvProformasDetalleTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proformasService.insertarInvProformasTO(invProformasTO, listaInvProformasDetalleTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
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
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
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
        boolean recontabilizarSinPendiente = UtilsJSON.jsonToObjeto(boolean.class,
                map.get("recontabilizarSinPendiente"));
        String tipCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("tipCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasService.insertarInvContableComprasTO(empresa, periodo, motivo, compraNumero, codigoUsuario,
                    recontabilizar, conNumero, recontabilizarSinPendiente, tipCodigo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarInvContableVentasTO")
    public MensajeTO insertarInvContableVentasTO(@RequestBody String json) {
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
            return ventasService.insertarInvContableVentasTO(empresa, periodo, motivo, ventaNumero, codigoUsuario,
                    recontabilizar, conNumero, tipCodigo, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvCompraCabeceraTO")
    public InvCompraCabeceraTO getInvCompraCabeceraTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoPeriodo = UtilsJSON.jsonToObjeto(String.class, map.get("codigoPeriodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroCompra = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCompra"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasService.getInvCompraCabeceraTO(empresa, codigoPeriodo, motivo, numeroCompra);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
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
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
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
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvConsumoCabeceraTO")
    public InvConsumosTO getInvConsumoCabeceraTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoPeriodo = UtilsJSON.jsonToObjeto(String.class, map.get("codigoPeriodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroConsumo = UtilsJSON.jsonToObjeto(String.class, map.get("numeroConsumo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return consumosService.getInvConsumoCabeceraTO(empresa, codigoPeriodo, motivo, numeroConsumo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvCompraDetalleTO")
    public List<InvListaDetalleComprasTO> getListaInvCompraDetalleTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroCompra = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCompra"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasDetalleService.getListaInvCompraDetalleTO(empresa, periodo, motivo, numeroCompra);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvVentasDetalleTO")
    public List<InvListaDetalleVentasTO> getListaInvVentasDetalleTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroVentas = UtilsJSON.jsonToObjeto(String.class, map.get("numeroVentas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventasDetalleService.getListaInvVentasDetalleTO(empresa, periodo, motivo, numeroVentas);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvProformasDetalleTO")
    public List<InvListaDetalleProformasTO> getListaInvProformasDetalleTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroProformas = UtilsJSON.jsonToObjeto(String.class, map.get("numeroProformas"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proformasDetalleService.getListaInvProformasDetalleTO(empresa, periodo, motivo, numeroProformas);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvConsumoDetalleTO")
    public List<InvListaDetalleConsumoTO> getListaInvConsumoDetalleTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroConsumo = UtilsJSON.jsonToObjeto(String.class, map.get("numeroConsumo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return consumosDetalleService.getListaInvConsumoDetalleTO(empresa, periodo, motivo, numeroConsumo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarInvComprasTO")
    public MensajeTO modificarInvComprasTO(@RequestBody String json) throws Exception {
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
            invComprasTO.setCompUsuarioApruebaPago(null);
            invComprasTO.setCompFechaApruebaPago(null);
            mensajeTO = comprasService.modificarInvComprasTO(invComprasTO, listaInvComprasDetalleTO,
                    listaInvComprasEliminarDetalleTO, anxCompraTO, anxCompraDetalleTO, anxCompraDetalleEliminarTO,
                    anxCompraReembolsoTO, anxCompraReembolsoEliminarTO, anxCompraFormaPagoTO,
                    anxCompraFormaPagoEliminarTO, desmayorizar, quitarAnulado, invComprasMotivoAnulacion, listImagen, sisInfoTO);
            if (!desmayorizar && mensajeTO.getMensaje().charAt(0) == 'T') {
                if (!invComprasTO.getCompPendiente()) {
                    comprasService.quitarPendiente(invComprasTO);
                }
            }
            return mensajeTO;
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                mensajeTO = new MensajeTO();
                mensajeTO.setMensaje("F" + e.getMessage());
            }
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return mensajeTO;
    }

    @RequestMapping("/modificarInvVentasTO")
    public MensajeTO modificarInvVentasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvVentasTO invVentasTO = UtilsJSON.jsonToObjeto(InvVentasTO.class, map.get("invVentasTO"));
        List<InvVentasDetalleTO> listaInvVentasDetalleTO = UtilsJSON.jsonToList(InvVentasDetalleTO.class,
                map.get("listaInvVentasDetalleTO"));
        List<InvVentasDetalleTO> listaInvVentasEliminarDetalleTO = UtilsJSON.jsonToList(InvVentasDetalleTO.class,
                map.get("listaInvVentasEliminarDetalleTO"));
        boolean desmayorizar = UtilsJSON.jsonToObjeto(boolean.class, map.get("desmayorizar"));
        AnxVentaTO anxVentasTO = UtilsJSON.jsonToObjeto(AnxVentaTO.class, map.get("anxVentasTO"));
        boolean quitarAnulado = UtilsJSON.jsonToObjeto(boolean.class, map.get("quitarAnulado"));
        String tipoDocumentoComplemento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumentoComplemento"));
        String numeroDocumentoComplemento = UtilsJSON.jsonToObjeto(String.class, map.get("numeroDocumentoComplemento"));
        InvVentasMotivoAnulacion invVentasMotivoAnulacion = UtilsJSON.jsonToObjeto(InvVentasMotivoAnulacion.class,
                map.get("invVentasMotivoAnulacion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = null;
        try {
            mensajeTO = ventasService.modificarInvVentasTO(invVentasTO, listaInvVentasDetalleTO,
                    listaInvVentasEliminarDetalleTO, desmayorizar, anxVentasTO, quitarAnulado, tipoDocumentoComplemento,
                    numeroDocumentoComplemento, invVentasMotivoAnulacion, sisInfoTO);

            if (!desmayorizar && mensajeTO.getMensaje().charAt(0) == 'T' && !invVentasTO.getVtaPendiente()) {
                ventasService.quitarPendiente(invVentasTO);
            }

        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                mensajeTO = new MensajeTO();
                mensajeTO.setMensaje("M<html>" + e.getMessage() + "</html>");
            }
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return mensajeTO;
    }

    @RequestMapping("/modificarInvProformasTO")
    public MensajeTO modificarInvProformasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProformasTO invProformasTO = UtilsJSON.jsonToObjeto(InvProformasTO.class, map.get("invProformasTO"));
        List<InvProformasDetalleTO> listaInvProformasDetalleTO = UtilsJSON.jsonToList(InvProformasDetalleTO.class,
                map.get("listaInvProformasDetalleTO"));
        List<InvProformasDetalleTO> listaInvProformasEliminarDetalleTO = UtilsJSON
                .jsonToList(InvProformasDetalleTO.class, map.get("listaInvProfromasEliminarDetalleTO"));
        boolean quitarAnulado = UtilsJSON.jsonToObjeto(boolean.class, map.get("quitarAnulado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proformasService.modificarInvProformasTO(invProformasTO, listaInvProformasDetalleTO,
                    listaInvProformasEliminarDetalleTO, quitarAnulado, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvConsumos")
    public InvConsumos getInvConsumos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return consumosService.obtenerPorId(empresa, periodo, motivo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/desmayorizarConsumo")
    public String desmayorizarConsumo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return consumosService.desmayorizarConsumo(empresa, periodo, motivo, numero, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/anularConsumo")
    public String anularConsumo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return consumosService.anularConsumo(empresa, periodo, motivo, numero, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/restaurarConsumo")
    public String restaurarConsumo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return consumosService.restaurarConsumo(empresa, periodo, motivo, numero, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarInvConsumosTO")
    public MensajeTO insertarInvConsumosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvConsumosTO invConsumosTO = UtilsJSON.jsonToObjeto(InvConsumosTO.class, map.get("invConsumosTO"));
        List<InvConsumosDetalleTO> listaInvConsumosDetalleTO = UtilsJSON.jsonToList(InvConsumosDetalleTO.class,
                map.get("listaInvConsumosDetalleTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = new MensajeTO();;
        try {
            mensajeTO = consumosService.insertarInvConsumosTO(invConsumosTO, listaInvConsumosDetalleTO, sisInfoTO);
        } catch (NumberFormatException nfe) {
            mensajeTO.setMensaje(nfe.getMessage());
            return mensajeTO;
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                mensajeTO.setMensaje("F" + e.getMessage());
            }
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return mensajeTO;
    }

    @RequestMapping("/insertarModificarInvConsumos")
    public MensajeTO insertarModificarInvConsumos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvConsumos invConsumos = UtilsJSON.jsonToObjeto(InvConsumos.class, map.get("invConsumos"));
        List<InvConsumosDetalle> listaInvConsumosDetalle = UtilsJSON.jsonToList(InvConsumosDetalle.class,
                map.get("listaInvConsumosDetalle"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = null;
        try {
            boolean pendiente = invConsumos.getConsPendiente();
            mensajeTO = consumosService.insertarModificarInvConsumos(invConsumos, listaInvConsumosDetalle, sisInfoTO, true, new ArrayList<>());
            if (mensajeTO.getMensaje().charAt(0) == 'T' && !pendiente) {
                consumosService.quitarPendiente(invConsumos.getInvConsumosPK(), sisInfoTO);
            }
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return mensajeTO;
    }

    @RequestMapping("/modificarInvConsumosTO")
    public MensajeTO modificarInvConsumosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvConsumosTO invConsumosTO = UtilsJSON.jsonToObjeto(InvConsumosTO.class, map.get("invConsumosTO"));
        List<InvConsumosDetalleTO> listaInvConsumosDetalleTO = UtilsJSON.jsonToList(InvConsumosDetalleTO.class,
                map.get("listaInvConsumosDetalleTO"));
        List<InvConsumosDetalleTO> listaInvConsumosEliminarDetalleTO = UtilsJSON.jsonToList(InvConsumosDetalleTO.class,
                map.get("listaInvConsumosEliminarDetalleTO"));
        boolean desmayorizar = UtilsJSON.jsonToObjeto(boolean.class, map.get("desmayorizar"));
        InvConsumosMotivoAnulacion invConsumosMotivoAnulacion = UtilsJSON.jsonToObjeto(InvConsumosMotivoAnulacion.class,
                map.get("invConsumosMotivoAnulacion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            MensajeTO mensajeTO = consumosService.modificarInvConsumosTO(invConsumosTO, listaInvConsumosDetalleTO,
                    listaInvConsumosEliminarDetalleTO, desmayorizar, invConsumosMotivoAnulacion, sisInfoTO);
            if (mensajeTO.getMensaje().charAt(0) == 'T' && !desmayorizar && !invConsumosTO.getConsPendiente()) {
                consumosService.quitarPendiente(new InvConsumosPK(invConsumosTO.getConsEmpresa(),
                        invConsumosTO.getConsPeriodo(), invConsumosTO.getConsMotivo(), invConsumosTO.getConsNumero()), sisInfoTO);
            }
            return mensajeTO;
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaProductosTO")
    public List<InvListaProductosTO> getListaProductosTO(@RequestBody String json) {
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
            return productoSaldosService.getListaProductosTO(empresa, busqueda, bodega, categoria, fecha,
                    incluirInactivos, limite, codigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaClienteTO")
    public List<InvListaClienteTO> getListaClienteTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        boolean activo_Cliente = UtilsJSON.jsonToObjeto(boolean.class, map.get("activo_Cliente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return clienteService.getListaClienteTO(empresa, busqueda, activo_Cliente);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaProveedoresTO")
    public List<InvListaProveedoresTO> getListaProveedoresTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        boolean activoProveedor = UtilsJSON.jsonToObjeto(boolean.class, map.get("activoProveedor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proveedorService.getListaProveedoresTO(empresa, busqueda, activoProveedor);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaBodegasTO")
    public List<InvListaBodegasTO> getListaBodegasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean inactivo = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bodegaService.getListaBodegasTO(empresa, inactivo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaBodegas")
    public List<InvBodega> getListaBodegas(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean inactivo = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bodegaService.getListaBodegas(empresa, inactivo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvComboBodegaTO")
    public List<InvComboBodegaTO> getInvComboBodegaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bodegaService.getInvComboBodegaTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaCategoriaProveedorComboTO")
    public List<InvCategoriaProveedorComboTO> getListaCategoriaProveedorComboTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proveedorCategoriaService.getListaCategoriaProveedorComboTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaCompraMotivoComboTO")
    public List<InvCompraMotivoComboTO> getListaCompraMotivoComboTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean filtrarInactivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("filtrarInactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasMotivoService.getListaCompraMotivoComboTO(empresa, filtrarInactivos);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaVentaMotivoComboTO")
    public List<InvVentaMotivoComboTO> getListaVentaMotivoComboTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean filtrarInactivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("filtrarInactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventasMotivoService.getListaVentaMotivoComboTO(empresa, filtrarInactivos);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaProformaMotivoComboTO")
    public List<InvProformaMotivoComboTO> getListaProformaMotivoComboTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean filtrarInactivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("filtrarInactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
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
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoCategoriaService.getListaCategoriaComboTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPresentacionUnidadComboTO")
    public List<InvProductoPresentacionUnidadesComboListadoTO> getListaPresentacionUnidadComboTO(
            @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoPresentacionUnidadesService.getListaPresentacionUnidadComboTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionInvProductoPresentacionUnidadesTO")
    public String accionInvProductoPresentacionUnidadesTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoPresentacionUnidadesTO invProductoPresentacionUnidadesTO = UtilsJSON
                .jsonToObjeto(InvProductoPresentacionUnidadesTO.class, map.get("invProductoPresentacionUnidadesTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoPresentacionUnidadesService
                    .accionInvProductoPresentacionUnidadesTO(invProductoPresentacionUnidadesTO, accion, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvMarcaComboListadoTO")
    public List<InvProductoMarcaComboListadoTO> getInvMarcaComboListadoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoMarcaService.getInvMarcaComboListadoTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionInvProductoMarcaTO")
    public String accionInvProductoMarcaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoMarcaTO invProductoMarcaTO = UtilsJSON.jsonToObjeto(InvProductoMarcaTO.class,
                map.get("invProductoMarcaTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return this.productoMarcaService.accionInvProductoMarcaTO(invProductoMarcaTO, accion, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionInvVendedorTO")
    public String accionInvVendedorTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvVendedorTO invVendedorTO = UtilsJSON.jsonToObjeto(InvVendedorTO.class, map.get("invVendedorTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return this.vendedorService.accionInvVendedorTO(invVendedorTO, accion, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaPresentacionCajaComboTO")
    public List<InvProductoPresentacionCajasComboListadoTO> getListaPresentacionCajaComboTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoPresentacionCajasService.getListaPresentacionCajaComboTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionInvProductoPresentacionCajasTO")
    public String accionInvProductoPresentacionCajasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoPresentacionCajasTO invProductoPresentacionCajasTO = UtilsJSON
                .jsonToObjeto(InvProductoPresentacionCajasTO.class, map.get("invProductoPresentacionCajasTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoPresentacionCajasService.accionInvProductoPresentacionCajasTO(invProductoPresentacionCajasTO,
                    accion, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaCategoriaClienteComboTO")
    public List<InvCategoriaClienteComboTO> getListaCategoriaClienteComboTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return clienteCategoriaService.getListaCategoriaClienteComboTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getBodegaTO")
    public List<InvBodegaTO> getBodegaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bodegaService.getBodegaTO(empresa, codigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
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

    @RequestMapping("/getProducto")
    public InvProducto getProducto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            InvProducto p = productoService.obtenerPorId(empresa, codigo);
            return p;
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getClienteTO")
    public List<InvClienteTO> getClienteTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return clienteService.getClienteTO(empresa, codigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
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
    public InvProveedorTO getBuscaCedulaProveedorTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cedRuc = UtilsJSON.jsonToObjeto(String.class, map.get("cedRuc"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proveedorService.getBuscaCedulaProveedorTO(empresa, cedRuc);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/comprobarInvAplicaRetencionIva")
    public boolean comprobarInvAplicaRetencionIva(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigo = UtilsJSON.jsonToObjeto(String.class, map.get("codigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proveedorService.comprobarInvAplicaRetencionIva(empresa, codigo);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/getConteoNumeroFacturaCompra")
    public String getConteoNumeroFacturaCompra(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresaCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empresaCodigo"));
        String provCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("provCodigo"));
        String compDocumentoTipo = UtilsJSON.jsonToObjeto(String.class, map.get("compDocumentoTipo"));
        String compDocumentoNumero = UtilsJSON.jsonToObjeto(String.class, map.get("compDocumentoNumero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasService.getConteoNumeroFacturaCompra(empresaCodigo, provCodigo, compDocumentoTipo,
                    compDocumentoNumero);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getComboFormaPagoCompra")
    public List<InvComboFormaPagoTO> getComboFormaPagoCompra(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasFormaPagoService.getComboFormaPagoCompra(empresa);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getComboFormaPagoVenta")
    public List<InvComboFormaPagoTO> getComboFormaPagoVenta(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventasFormaPagoService.getComboFormaPagoVenta(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getFunComprasListado")
    public List<InvListaConsultaCompraTO> getFunComprasListado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String status = UtilsJSON.jsonToObjeto(String.class, map.get("status"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasService.getFunComprasListado(empresa, fechaDesde, fechaHasta, status);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvConsultaCompra")
    public List<InvListaConsultaCompraTO> getListaInvConsultaCompra(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        String nRegistros = UtilsJSON.jsonToObjeto(String.class, map.get("nRegistros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasService.getListaInvConsultaCompra(empresa, periodo, motivo, busqueda, nRegistros, false);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getFunVentasListado")
    public List<InvListaConsultaVentaTO> getFunVentasListado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String status = UtilsJSON.jsonToObjeto(String.class, map.get("status"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventasService.getFunVentasListado(empresa, fechaDesde, fechaHasta, status);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvConsultaVenta")
    public List<InvListaConsultaVentaTO> getListaInvConsultaVenta(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        String nRegistros = UtilsJSON.jsonToObjeto(String.class, map.get("nRegistros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventasService.getListaInvConsultaVenta(empresa, periodo, motivo, busqueda, nRegistros);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvConsultaVentaFiltrado")
    public List<InvListaConsultaVentaTO> getListaInvConsultaVentaFiltrado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        String nRegistros = UtilsJSON.jsonToObjeto(String.class, map.get("nRegistros"));
        String tipoDocumento = UtilsJSON.jsonToObjeto(String.class, map.get("tipoDocumento"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventasService.getListaInvConsultaVentaFiltrado(empresa, periodo, motivo, busqueda, nRegistros, tipoDocumento);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvConsultaProforma")
    public List<InvListaConsultaProformaTO> getListaInvConsultaProforma(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proformasService.getListaInvConsultaProforma(empresa, periodo, motivo, busqueda);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getFunConsumosListado")
    public List<InvListaConsultaConsumosTO> getFunConsumosListado(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String status = UtilsJSON.jsonToObjeto(String.class, map.get("status"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return consumosService.getFunConsumosListado(empresa, fechaDesde, fechaHasta, status);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvConsultaConsumos")
    public List<InvListaConsultaConsumosTO> getListaInvConsultaConsumos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        String nRegistros = UtilsJSON.jsonToObjeto(String.class, map.get("nRegistros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return consumosService.getListaInvConsultaConsumos(empresa, periodo, motivo, null, null, null, null, busqueda, nRegistros);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvConsumosImportarExportarTO")
    public List<InvConsumosImportarExportarTO> getListaInvConsumosImportarExportarTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
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
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean filtrarInactivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("filtrarInactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
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
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean filtrarInactivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("filtrarInactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return consumosMotivoService.getListaConsumosMotivo(empresa, filtrarInactivos);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvMedidaTablaTO")
    public List<InvMedidaComboTO> getListaInvMedidaTablaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoMedidaService.getListaInvMedidaTablaTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarInvComprasMotivoTO")
    public String insertarInvComprasMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasMotivoTO invCompraMotivoTO = UtilsJSON.jsonToObjeto(InvComprasMotivoTO.class,
                map.get("invCompraMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasMotivoService.insertarInvComprasMotivoTO(invCompraMotivoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarInvComprasMotivoTO")
    public String modificarInvComprasMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasMotivoTO invCompraMotivoTO = UtilsJSON.jsonToObjeto(InvComprasMotivoTO.class,
                map.get("invCompraMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasMotivoService.modificarInvComprasMotivoTO(invCompraMotivoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarInvComprasMotivoTO")
    public String eliminarInvComprasMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasMotivoTO invCompraMotivoTO = UtilsJSON.jsonToObjeto(InvComprasMotivoTO.class,
                map.get("invCompraMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasMotivoService.eliminarInvComprasMotivoTO(invCompraMotivoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvComprasMotivoTablaTO")
    public List<InvCompraMotivoTablaTO> getListaInvComprasMotivoTablaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasMotivoService.getListaInvComprasMotivoTablaTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvComprasMotivoTO")
    public InvComprasMotivoTO getInvComprasMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cmCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("cmCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasMotivoService.getInvComprasMotivoTO(empresa, cmCodigo);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarInvVentasMotivoTO")
    public String insertarInvVentasMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvVentaMotivoTO invVentaMotivoTO = UtilsJSON.jsonToObjeto(InvVentaMotivoTO.class, map.get("invVentaMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventasMotivoService.insertarInvVentasMotivoTO(invVentaMotivoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarInvVentasMotivoTO")
    public String modificarInvVentasMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvVentaMotivoTO invVentaMotivoTO = UtilsJSON.jsonToObjeto(InvVentaMotivoTO.class, map.get("invVentaMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventasMotivoService.modificarInvVentasMotivoTO(invVentaMotivoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarInvVentaMotivoTO")
    public String eliminarInvVentaMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvVentaMotivoTO invVentaMotivoTO = UtilsJSON.jsonToObjeto(InvVentaMotivoTO.class, map.get("invVentaMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventasMotivoService.eliminarInvVentasMotivoTO(invVentaMotivoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvVentasMotivoTablaTO")
    public List<InvVentaMotivoTablaTO> getListaInvVentasMotivoTablaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventasMotivoService.getListaInvVentasMotivoTablaTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvVentasMotivoTO")
    public InvVentaMotivoTO getInvVentasMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String vmCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("vmCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventasMotivoService.getInvVentasMotivoTO(empresa, vmCodigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarInvProformaMotivoTO")
    public String insertarInvProformaMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProformaMotivoTO invProformaMotivoTO = UtilsJSON.jsonToObjeto(InvProformaMotivoTO.class,
                map.get("invProformaMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proformasMotivoService.insertarInvProformaMotivoTO(invProformaMotivoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarInvProformaMotivoTO")
    public String modificarInvProformaMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProformaMotivoTO invProformaMotivoTO = UtilsJSON.jsonToObjeto(InvProformaMotivoTO.class,
                map.get("invProformaMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proformasMotivoService.modificarInvProformaMotivoTO(invProformaMotivoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarInvProformaMotivoTO")
    public String eliminarInvProformaMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProformaMotivoTO invProformaMotivoTO = UtilsJSON.jsonToObjeto(InvProformaMotivoTO.class,
                map.get("invProformaMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proformasMotivoService.eliminarInvProformaMotivoTO(invProformaMotivoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvProformaMotivoTablaTO")
    public List<InvProformaMotivoTablaTO> getListaInvProformaMotivoTablaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proformasMotivoService.getListaInvProformaMotivoTablaTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvProformasMotivoTO")
    public InvProformaMotivoTO getInvProformasMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String pmCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("pmCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proformasMotivoService.getInvProformasMotivoTO(empresa, pmCodigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvListaConsumosMotivoTO")
    public List<InvListaConsumosMotivoTO> getInvListaConsumosMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return consumosMotivoService.getInvListaConsumosMotivoTO(empresa);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionInvConsumosMotivo")
    public String accionInvConsumosMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvConsumosMotivoTO invConsumosMotivoTO = UtilsJSON.jsonToObjeto(InvConsumosMotivoTO.class,
                map.get("invConsumosMotivoTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return consumosMotivoService.accionInvConsumosMotivo(invConsumosMotivoTO, accion, sisInfoTO);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvConsumoMotivoTO")
    public InvConsumosMotivoTO getInvConsumoMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cmCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("cmCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return consumosMotivoService.getInvConsumoMotivoTO(empresa, cmCodigo);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvNumeracionCompraTO")
    public List<InvNumeracionCompraTO> getListaInvNumeracionCompraTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasNumeracionService.getListaInvNumeracionCompraTO(empresa, periodo, motivo);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvNumeracionVentaTO")
    public List<InvNumeracionVentaTO> getListaInvNumeracionVentaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventasNumeracionService.getListaInvNumeracionVentaTO(empresa, periodo, motivo);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvNumeracionConsumoTO")
    public List<InvNumeracionConsumoTO> getListaInvNumeracionConsumoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return consumosNumeracionService.getListaInvNumeracionConsumoTO(empresa, periodo, motivo);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvListaComprasFormaPagoTO")
    public List<InvListaComprasFormaPagoTO> getInvListaComprasFormaPagoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasFormaPagoService.getInvListaComprasFormaPagoTO(empresa);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionInvComprasPagosForma")
    public String accionInvComprasPagosForma(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasFormaPagoTO invComprasFormaPagoTO = UtilsJSON.jsonToObjeto(InvComprasFormaPagoTO.class,
                map.get("invComprasFormaPagoTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasFormaPagoService.accionInvComprasPagosForma(invComprasFormaPagoTO, accion, sisInfoTO);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvListaVentasFormaPagoTO")
    public List<InvListaVentasFormaPagoTO> getInvListaVentasFormaPagoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventasFormaPagoService.getInvListaVentasFormaPagoTO(empresa);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionInvVentasPagosForma")
    public String accionInvVentasPagosForma(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvVentasFormaPagoTO invVentasFormaPagoTO = UtilsJSON.jsonToObjeto(InvVentasFormaPagoTO.class,
                map.get("invVentasFormaPagoTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventasFormaPagoService.accionInvVentasPagosForma(invVentasFormaPagoTO, accion, sisInfoTO);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvKardexTO")
    public List<InvKardexTO> getListaInvKardexTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String producto = UtilsJSON.jsonToObjeto(String.class, map.get("producto"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String promedio = UtilsJSON.jsonToObjeto(String.class, map.get("promedio"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoSaldosService.getListaInvKardexTO(empresa, bodega, producto, desde, hasta, promedio);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaSaldoBodegaTO")
    public List<SaldoBodegaTO> getListaSaldoBodegaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            List<SaldoBodegaTO> resultadoAEnviar = new ArrayList<>();
            List<SaldoBodegaTO> respuesta = bodegaService.getListaSaldoBodegaTO(empresa, bodega, hasta, categoria);
            if (respuesta != null) {
                for (SaldoBodegaTO sb : respuesta) {
                    if (sb.getSbSerie() == null || sb.getSbSerie().equals("")) {
                        resultadoAEnviar.add(sb);
                    }
                }
            }
            return resultadoAEnviar;
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvComprasRecepcionTO")
    public InvComprasRecepcionTO getInvComprasRecepcionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {

            return comprasRecepcionService.getInvComprasRecepcionTO(empresa, periodo, motivo, numero);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarModificarComprasRecepcionTO")
    public String insertarModificarComprasRecepcionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasRecepcionTO invComprasRecepcionTO = UtilsJSON.jsonToObjeto(InvComprasRecepcionTO.class,
                map.get("invComprasRecepcionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasService.insertarModificarComprasRecepcionTO(invComprasRecepcionTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListadoProductosConError")
    public List<InvProductosConErrorTO> getListadoProductosConError(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoService.getListadoProductosConError(empresa);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrecioFijoCategoriaProducto")
    public Boolean getPrecioFijoCategoriaProducto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoCategoria = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCategoria"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoCategoriaService.getPrecioFijoCategoriaProducto(empresa, codigoCategoria);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/insertarInvTransferenciaTO")
    public MensajeTO insertarInvTransferenciaTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvTransferenciasTO invTransferenciasTO = UtilsJSON.jsonToObjeto(InvTransferenciasTO.class,
                map.get("invTransferenciasTO"));
        List<InvTransferenciasDetalleTO> listaInvTransferenciasDetalleTO = UtilsJSON
                .jsonToList(InvTransferenciasDetalleTO.class, map.get("listaInvTransferenciasDetalleTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = null;
        try {
            mensajeTO = transferenciasService.insertarInvTransferenciaTO(invTransferenciasTO,
                    listaInvTransferenciasDetalleTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return mensajeTO;
    }

    @RequestMapping("/getInvListaTransferenciaMotivoTO")
    public List<InvListaTransferenciaMotivoTO> getInvListaTransferenciaMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return transferenciasMotivoService.getInvListaTransferenciaMotivoTO(empresa);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvTransferenciaMotivoTO")
    public InvTransferenciaMotivoTO getInvTransferenciaMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String tmCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("tmCodigo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return transferenciasMotivoService.getInvTransferenciaMotivoTO(empresa, tmCodigo);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionInvTransferenciaMotivo")
    public String accionInvTransferenciaMotivo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvTransferenciaMotivoTO invTransferenciaMotivoTO = UtilsJSON.jsonToObjeto(InvTransferenciaMotivoTO.class,
                map.get("invTransferenciaMotivoTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return transferenciasMotivoService.accionInvTransferenciaMotivo(invTransferenciaMotivoTO, accion,
                    sisInfoTO);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaTransferenciaMotivoComboTO")
    public List<InvTransferenciaMotivoComboTO> getListaTransferenciaMotivoComboTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean filtrarInactivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("filtrarInactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
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
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroTransferencia = UtilsJSON.jsonToObjeto(String.class, map.get("numeroTransferencia"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
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
    public InvTransferenciasTO getInvTransferenciasCabeceraTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoPeriodo = UtilsJSON.jsonToObjeto(String.class, map.get("codigoPeriodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroTransferencia = UtilsJSON.jsonToObjeto(String.class, map.get("numeroTransferencia"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return transferenciasService.getInvTransferenciasCabeceraTO(empresa, codigoPeriodo, motivo,
                    numeroTransferencia);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/modificarInvTransferenciasTO")
    public MensajeTO modificarInvTransferenciasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvTransferenciasTO invTransferenciasTO = UtilsJSON.jsonToObjeto(InvTransferenciasTO.class,
                map.get("invTransferenciasTO"));
        List<InvTransferenciasDetalleTO> listaInvTransferenciasDetalleTO = UtilsJSON
                .jsonToList(InvTransferenciasDetalleTO.class, map.get("listaInvTransferenciasDetalleTO"));
        List<InvTransferenciasDetalleTO> listaInvTransferenciasEliminarDetalleTO = UtilsJSON
                .jsonToList(InvTransferenciasDetalleTO.class, map.get("listaInvTransferenciasEliminarDetalleTO"));
        boolean desmayorizar = UtilsJSON.jsonToObjeto(boolean.class, map.get("desmayorizar"));
        InvTransferenciasMotivoAnulacion invTransferenciasMotivoAnulacion = UtilsJSON
                .jsonToObjeto(InvTransferenciasMotivoAnulacion.class, map.get("invTransferenciasMotivoAnulacion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        MensajeTO mensajeTO = new MensajeTO();
        try {
            mensajeTO = transferenciasService.modificarInvTransferenciasTO(invTransferenciasTO,
                    listaInvTransferenciasDetalleTO, listaInvTransferenciasEliminarDetalleTO, desmayorizar,
                    invTransferenciasMotivoAnulacion, sisInfoTO);
            if (mensajeTO.getMensaje().charAt(0) == 'T' && !desmayorizar && !invTransferenciasTO.getTransPendiente()) {
                transferenciasService.quitarPendiente(invTransferenciasTO);
            }
            return mensajeTO;
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                mensajeTO.setMensaje("F" + e.getMessage());
            }
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getFunListadoTransferencias")
    public List<InvListaConsultaTransferenciaTO> getFunListadoTransferencias(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String status = UtilsJSON.jsonToObjeto(String.class, map.get("status"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {

            return transferenciasService.getFunListadoTransferencias(empresa, fechaDesde, fechaHasta, status);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvConsultaTransferencias")
    public List<InvListaConsultaTransferenciaTO> getListaInvConsultaTransferencias(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        String nRegistros = UtilsJSON.jsonToObjeto(String.class, map.get("nRegistros"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return transferenciasService.getListaInvConsultaTransferencias(empresa, periodo, motivo, busqueda,
                    nRegistros);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaProductosCambiarPrecioTO")
    public List<InvListaProductosCambiarPrecioTO> getListaProductosCambiarPrecioTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoSaldosService.getListaProductosCambiarPrecioTO(empresa, busqueda, bodega, fecha);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaProductosCambiarPrecioCantidadTO")
    public List<InvListaProductosCambiarPrecioCantidadTO> getListaProductosCambiarPrecioCantidadTO(
            @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoSaldosService.getListaProductosCambiarPrecioCantidadTO(empresa, busqueda, bodega, fecha);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/invCambiarPrecioProducto")
    public MensajeTO invCambiarPrecioProducto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        List<InvListaProductosCambiarPrecioTO> invListaProductosCambiarPrecioTOs = UtilsJSON
                .jsonToList(InvListaProductosCambiarPrecioTO.class, map.get("invListaProductosCambiarPrecioTOs"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoService.invCambiarPrecioProducto(empresa, usuario, invListaProductosCambiarPrecioTOs,
                    sisInfoTO);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/invCambiarPrecioCantidadProducto")
    public MensajeTO invCambiarPrecioCantidadProducto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        List<InvListaProductosCambiarPrecioCantidadTO> invListaProductosCambiarPrecioCantidadTOs = UtilsJSON.jsonToList(
                InvListaProductosCambiarPrecioCantidadTO.class, map.get("invListaProductosCambiarPrecioCantidadTOs"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoService.invCambiarPrecioCantidadProducto(empresa, usuario,
                    invListaProductosCambiarPrecioCantidadTOs, sisInfoTO);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getEstadoCCCVT")
    public InvEstadoCCCVT getEstadoCCCVT(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        String proceso = UtilsJSON.jsonToObjeto(String.class, map.get("proceso"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
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
        String empresaOrigen = UtilsJSON.jsonToObjeto(String.class, map.get("empresaOrigen"));
        String empresaDestino = UtilsJSON.jsonToObjeto(String.class, map.get("empresaDestino"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
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
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasService.invListadoPagosTO(empresa, periodo, motivo, numero);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/invListadoCobrosTO")
    public List<InvListadoCobrosTO> invListadoCobrosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventasService.invListadoCobrosTO(empresa, periodo, motivo, numero);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvFunComprasTO")
    public List<InvFunComprasTO> getInvFunComprasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        String documento = UtilsJSON.jsonToObjeto(String.class, map.get("documento"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasService.getInvFunComprasTO(empresa, desde, hasta, motivo, proveedor, documento, null);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /**/
    @RequestMapping("/listarInvFunVentasTO")
    public List<InvFunVentasTO> listarInvFunVentasTO(@RequestBody String json) {
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
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventasService.listarInvFunVentasTO(empresa, desde, hasta, motivo, cliente, documento, sector, estado, grupo_empresarial, null, false);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /**/
    @RequestMapping("/getInvFunVentasTO")
    public List<InvFunVentasTO> getInvFunVentasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String documento = UtilsJSON.jsonToObjeto(String.class, map.get("documento"));
        String grupo_empresarial = UtilsJSON.jsonToObjeto(String.class, map.get("grupo_empresarial"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventasService.getInvFunVentasTO(empresa, desde, hasta, motivo, cliente, documento, grupo_empresarial);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvFunComprasConsolidandoProductosTO")
    public List<InvFunComprasConsolidandoProductosTO> getInvFunComprasConsolidandoProductosTO(
            @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasService.getInvFunComprasConsolidandoProductosTO(empresa, desde, hasta, sector, motivo,
                    proveedor);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvFunVentasConsolidandoProductosTO")
    public List<InvFunVentasConsolidandoProductosTO> getInvFunVentasConsolidandoProductosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        try {
            return ventasService.getInvFunVentasConsolidandoProductosTO(empresa, desde, hasta, sector, bodega, cliente);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvFunVentasConsolidandoProductosCoberturaTO")
    public List<InvFunVentasConsolidandoProductosCoberturaTO> getInvFunVentasConsolidandoProductosCoberturaTO(@RequestBody String json) {
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
            return ventasService.getInvFunVentasConsolidandoProductosCoberturaTO(empresa, desde, hasta, sector, bodega, motivo, cliente);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvFunVentasConsolidandoClientesTO")
    public List<InvFunVentasConsolidandoClientesTO> getInvFunVentasConsolidandoClientesTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));

        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventasService.getInvFunVentasConsolidandoClientesTO(empresa, sector, desde, hasta);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvFunVentasVsCostoTO")
    public List<InvFunVentasVsCostoTO> getInvFunVentasVsCostoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        try {
            return ventasService.getInvFunVentasVsCostoTO(empresa, desde, hasta, bodega, cliente);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvFunListadoProductosTO")
    public List<InvFunListadoProductosTO> getInvFunListadoProductosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoService.getInvFunListadoProductosTO(empresa, categoria, busqueda);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvFunListadoClientesTO")
    public List<InvFunListadoClientesTO> getInvFunListadoClientesTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return clienteService.getInvFunListadoClientesTO(empresa, categoria);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvFunListadoProveedoresTO")
    public List<InvFunListadoProveedoresTO> getInvFunListadoProveedoresTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String categoria = UtilsJSON.jsonToObjeto(String.class, map.get("categoria"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proveedorService.getInvFunListadoProveedoresTO(empresa, categoria);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPuedeEliminarProducto")
    public Boolean getPuedeEliminarProducto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String producto = UtilsJSON.jsonToObjeto(String.class, map.get("producto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoService.getPuedeEliminarProducto(empresa, producto);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getPrecioProductoPorCantidad")
    public BigDecimal getPrecioProductoPorCantidad(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String codProducto = UtilsJSON.jsonToObjeto(String.class, map.get("codProducto"));
        BigDecimal cantidad = UtilsJSON.jsonToObjeto(BigDecimal.class, map.get("cantidad"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoService.getPrecioProductoPorCantidad(empresa, cliente, codProducto, cantidad);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCantidad3")
    public BigDecimal getCantidad3(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codProducto = UtilsJSON.jsonToObjeto(String.class, map.get("codProducto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
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
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasService.getInvSecuenciaComprobanteTO(empresa, fechaDesde, fechaHasta);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvProductoTipoComboListadoTO")
    public List<InvProductoTipoComboTO> getInvProductoTipoComboListadoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String accion = UtilsJSON.jsonToObjeto(String.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoTipoService.getInvProductoTipoComboListadoTO(empresa, accion);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/accionInvProductoTipo")
    public String accionInvProductoTipo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvProductoTipoTO invProductoTipoTO = UtilsJSON.jsonToObjeto(InvProductoTipoTO.class,
                map.get("invProductoTipoTO"));
        char accion = UtilsJSON.jsonToObjeto(char.class, map.get("accion"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return this.productoTipoService.accionInvProductoTipo(invProductoTipoTO, accion, sisInfoTO);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
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

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/buscarConteoCliente")
    public Boolean buscarConteoCliente(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String codigoCliente = UtilsJSON.jsonToObjeto(String.class, map.get("codigoCliente"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return clienteService.buscarConteoCliente(empCodigo, codigoCliente);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/buscarConteoProveedor")
    public Boolean buscarConteoProveedor(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("empCodigo"));
        String codigoProveedor = UtilsJSON.jsonToObjeto(String.class, map.get("codigoProveedor"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proveedorService.buscarConteoProveedor(empCodigo, codigoProveedor);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return false;
    }

    @RequestMapping("/getInvFunSaldoBodegaComprobacionTO")
    public List<SaldoBodegaComprobacionTO> getInvFunSaldoBodegaComprobacionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bodegaService.getInvFunSaldoBodegaComprobacionTO(empresa, bodega, desde, hasta);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvFunConsumosTO")
    public List<InvFunConsumosTO> getInvFunConsumosTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return consumosService.getInvFunConsumosTO(empresa, desde, hasta, motivo);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvFunConsumosConsolidandoProductosTO")
    public List<InvFunConsumosConsolidandoProductosTO> getInvFunConsumosConsolidandoProductosTO(
            @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return consumosService.getInvFunConsumosConsolidandoProductosTO(empresa, desde, hasta, sector, bodega);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvFunSaldoBodegaComprobacionCantidadesTO")
    public List<SaldoBodegaComprobacionTO> getInvFunSaldoBodegaComprobacionCantidadesTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String desde = UtilsJSON.jsonToObjeto(String.class, map.get("desde"));
        String hasta = UtilsJSON.jsonToObjeto(String.class, map.get("hasta"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return bodegaService.getInvFunSaldoBodegaComprobacionCantidadesTO(empresa, bodega, desde, hasta);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getComboinvListaVendedorTOs")
    public List<InvVendedorComboListadoTO> getComboinvListaVendedorTOs(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String busqueda = UtilsJSON.jsonToObjeto(String.class, map.get("busqueda"));
        try {
            return vendedorService.getComboinvListaVendedorTOs(empresa, busqueda);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvFunListaProductosImpresionPlacasTO")
    public List<InvFunListaProductosImpresionPlacasTO> getInvFunListaProductosImpresionPlacasTO(
            @RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String producto = UtilsJSON.jsonToObjeto(String.class, map.get("producto"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoService.getInvFunListaProductosImpresionPlacasTO(empresa, producto, estado);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvFunListaProductosSaldosGeneralTO")
    public RetornoTO getInvFunListaProductosSaldosGeneralTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String producto = UtilsJSON.jsonToObjeto(String.class, map.get("producto"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        boolean estado = UtilsJSON.jsonToObjeto(boolean.class, map.get("estado"));
        String usuario = UtilsJSON.jsonToObjeto(String.class, map.get("usuario"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoSaldosService.getInvFunListaProductosSaldosGeneralTO(empresa, producto, fecha, estado,
                    usuario);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvFunUltimasComprasTOs")
    public List<InvFunUltimasComprasTO> getInvFunUltimasComprasTOs(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String producto = UtilsJSON.jsonToObjeto(String.class, map.get("producto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
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
        String codigoEmpresa = UtilsJSON.jsonToObjeto(String.class, map.get("codigoEmpresa"));
        String facturaNumero = UtilsJSON.jsonToObjeto(String.class, map.get("facturaNumero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventasService.getInvVentaRetencionesTO(codigoEmpresa, facturaNumero);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getComprasTO")
    public InvComprasTO getComprasTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numeroCompra = UtilsJSON.jsonToObjeto(String.class, map.get("numeroCompra"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasService.getComprasTO(empresa, periodo, motivo, numeroCompra);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCompra")
    public Object[] getCompra(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String perCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("perCodigo"));
        String motCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("motCodigo"));
        String compNumero = UtilsJSON.jsonToObjeto(String.class, map.get("compNumero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasService.getCompra(empresa, perCodigo, motCodigo, compNumero);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getVenta")
    public Object[] getVenta(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String perCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("perCodigo"));
        String motCodigo = UtilsJSON.jsonToObjeto(String.class, map.get("motCodigo"));
        String compNumero = UtilsJSON.jsonToObjeto(String.class, map.get("compNumero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return ventasService.getVenta(empresa, perCodigo, motCodigo, compNumero);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getInvProductoSustentoConcepto")
    public List<InvListaProductosCompraSustentoConceptoTO> getInvProductoSustentoConcepto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String fecha = UtilsJSON.jsonToObjeto(String.class, map.get("fecha"));
        List<InvListaProductosCompraSustentoConceptoTO> invListaProductosCompraTOs = UtilsJSON
                .jsonToList(InvListaProductosCompraSustentoConceptoTO.class, map.get("invListaProductosCompraTOs"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return productoService.getInvProductoSustentoConcepto(empresa, fecha, invListaProductosCompraTOs);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getComprasPorPeriodo")
    public RetornoTO getComprasPorPeriodo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String codigoSector = UtilsJSON.jsonToObjeto(String.class, map.get("codigoSector"));
        String fechaInicio = UtilsJSON.jsonToObjeto(String.class, map.get("fechaInicio"));
        String fechaFin = UtilsJSON.jsonToObjeto(String.class, map.get("fechaFin"));
        boolean chk = UtilsJSON.jsonToObjeto(boolean.class, map.get("chk"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasService.getComprasPorPeriodo(empresa, codigoSector, fechaInicio, fechaFin, chk);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getCompraTotalesTO")
    public InvCompraTotalesTO getCompraTotalesTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String comPeriodo = UtilsJSON.jsonToObjeto(String.class, map.get("comPeriodo"));
        String comMotivo = UtilsJSON.jsonToObjeto(String.class, map.get("comMotivo"));
        String ComNumero = UtilsJSON.jsonToObjeto(String.class, map.get("ComNumero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasService.getCompraTotalesTO(empresa, comPeriodo, comMotivo, ComNumero);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    /**
     * **************** REPORTES ******************
     */
    @RequestMapping("/generarReporteInvKardex")
    public byte[] generarReporteInvKardex(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String nombreProducto = UtilsJSON.jsonToObjeto(String.class, map.get("nombreProducto"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<InvKardexTO> listInvKardexTO = UtilsJSON.jsonToList(InvKardexTO.class, map.get("listInvKardexTO"));
        boolean banderaCosto = UtilsJSON.jsonToObjeto(boolean.class, map.get("banderaCosto"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteInvKardex(usuarioEmpresaReporteTO, nombreProducto, fechaDesde,
                    fechaHasta, listInvKardexTO, banderaCosto);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteImpresionPlaca")
    public byte[] generarReporteImpresionPlaca(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<DatoFunListaProductosImpresionPlaca> listProductosImpresionPlaca = UtilsJSON.jsonToList(DatoFunListaProductosImpresionPlaca.class, map.get("listProductosImpresionPlaca"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteImpresionPlaca(usuarioEmpresaReporteTO,
                    listProductosImpresionPlaca);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoCompras")
    public byte[] generarReporteListadoCompras(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String proveedorId = UtilsJSON.jsonToObjeto(String.class, map.get("proveedorId"));
        String documento = UtilsJSON.jsonToObjeto(String.class, map.get("documento"));
        List<InvFunComprasTO> listInvFunComprasTO = UtilsJSON.jsonToList(InvFunComprasTO.class,
                map.get("listInvFunComprasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteListadoCompras(usuarioEmpresaReporteTO, fechaDesde,
                    fechaHasta, motivo, proveedorId, documento, listInvFunComprasTO);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConsolidadoConsumoProducto")
    public byte[] generarReporteConsolidadoConsumoProducto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        List<InvFunConsumosConsolidandoProductosTO> listInvFunConsumosConsolidandoProductosTO = UtilsJSON.jsonToList(
                InvFunConsumosConsolidandoProductosTO.class, map.get("listInvFunConsumosConsolidandoProductosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteConsolidadoConsumoProducto(usuarioEmpresaReporteTO,
                    fechaDesde, fechaHasta, bodega, proveedor, null, listInvFunConsumosConsolidandoProductosTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConsolidadoCompraProducto")
    public byte[] generarReporteConsolidadoCompraProducto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String proveedor = UtilsJSON.jsonToObjeto(String.class, map.get("proveedor"));
        List<InvFunComprasConsolidandoProductosTO> listInvFunComprasConsolidandoProductosTO = UtilsJSON.jsonToList(
                InvFunComprasConsolidandoProductosTO.class, map.get("listInvFunComprasConsolidandoProductosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteConsolidadoCompraProducto(usuarioEmpresaReporteTO, fechaDesde,
                    fechaHasta, bodega, proveedor, listInvFunComprasConsolidandoProductosTO);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoVentas")
    public byte[] generarReporteListadoVentas(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        String documento = UtilsJSON.jsonToObjeto(String.class, map.get("documento"));
        List<InvFunVentasTO> listInvFunVentasTO = UtilsJSON.jsonToList(InvFunVentasTO.class,
                map.get("listInvFunVentasTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteListadoVentas(usuarioEmpresaReporteTO, fechaDesde, fechaHasta,
                    motivo, cliente, documento, listInvFunVentasTO);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConsolidadoVentaProducto")
    public byte[] generarReporteConsolidadoVentaProducto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String cliente = UtilsJSON.jsonToObjeto(String.class, map.get("cliente"));
        List<InvFunVentasConsolidandoProductosTO> listInvFunVentasConsolidandoProductosTO = UtilsJSON.jsonToList(
                InvFunVentasConsolidandoProductosTO.class, map.get("listInvFunVentasConsolidandoProductosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteConsolidadoVentaProducto(usuarioEmpresaReporteTO, fechaDesde,
                    fechaHasta, bodega, cliente, listInvFunVentasConsolidandoProductosTO);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConsolidadoVentaCliente")
    public byte[] generarReporteConsolidadoVentaCliente(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String sector = UtilsJSON.jsonToObjeto(String.class, map.get("sector"));

        List<InvFunVentasConsolidandoClientesTO> listInvFunVentasConsolidandoClientesTO = UtilsJSON.jsonToList(
                InvFunVentasConsolidandoClientesTO.class, map.get("listInvFunVentasConsolidandoClientesTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteConsolidadoVentaCliente(usuarioEmpresaReporteTO, fechaDesde,
                    fechaHasta, sector, listInvFunVentasConsolidandoClientesTO);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListadoConsumos")
    public byte[] generarReporteListadoConsumos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<InvFunConsumosTO> listInvFunConsumosTO = UtilsJSON.jsonToList(InvFunConsumosTO.class,
                map.get("listInvFunConsumosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteListadoConsumos(usuarioEmpresaReporteTO, fechaDesde,
                    fechaHasta, listInvFunConsumosTO);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteListaProductos")
    public byte[] generarReporteListaProductos(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        List<InvListaProductosTO> listInvListaProductosTO = UtilsJSON.jsonToList(InvListaProductosTO.class,
                map.get("listInvListaProductosTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteListaProductos(usuarioEmpresaReporteTO, bodega,
                    listInvListaProductosTO);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteSaldoBodega")
    public byte[] generarReporteSaldoBodega(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<SaldoBodegaTO> listSaldoBodegaTO = UtilsJSON.jsonToList(SaldoBodegaTO.class, map.get("listSaldoBodegaTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteSaldoBodega(usuarioEmpresaReporteTO, bodega, fechaHasta,
                    listSaldoBodegaTO);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteSaldoBodegaComprobacion")
    public byte[] generarReporteSaldoBodegaComprobacion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        List<SaldoBodegaComprobacionTO> lista = UtilsJSON.jsonToList(SaldoBodegaComprobacionTO.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteSaldoBodegaComprobacion(usuarioEmpresaReporteTO, bodega,
                    fechaDesde, fechaHasta, lista);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteInvProductosConError")
    public byte[] generarReporteInvProductosConError(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<InvProductosConErrorTO> listInvProductosConErrorTO = UtilsJSON.jsonToList(InvProductosConErrorTO.class,
                map.get("listInvProductosConErrorTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteInvProductosConError(usuarioEmpresaReporteTO,
                    listInvProductosConErrorTO);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteTrasferencias")
    public byte[] generarReporteTrasferencias(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteTrasferencias(empresa, periodo, motivo, numero, usuarioEmpresaReporteTO);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteConsumoDetalle")
    public byte[] generarReporteConsumoDetalle(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvConsumosTO> invConsumosTOs = UtilsJSON.jsonToList(InvConsumosTO.class, map.get("invConsumosTOs"));
        boolean ordenado = UtilsJSON.jsonToObjeto(boolean.class, map.get("ordenado"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteConsumoDetalle(invConsumosTOs, ordenado, usuarioEmpresaReporteTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCompraDetalle")
    public byte[] generarReporteCompraDetalle(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteCompraDetalle> reporteCompraDetalles = UtilsJSON.jsonToList(ReporteCompraDetalle.class,
                map.get("reporteCompraDetalles"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteCompraDetalle(usuarioEmpresaReporteTO, reporteCompraDetalles);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteVentaDetalleImpresion")
    public byte[] generarReporteVentaDetalleImpresion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteVentaDetalle> lista = UtilsJSON.jsonToList(ReporteVentaDetalle.class, map.get("lista"));

        String nombreCliente = UtilsJSON.jsonToObjeto(String.class, map.get("nombreCliente"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteVentaDetalleImpresion(usuarioEmpresaReporteTO, lista,
                    nombreCliente, nombreReporte);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteProformaDetalleImpresion")
    public byte[] generarReporteProformaDetalleImpresion(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        List<ReporteProformaDetalle> lista = UtilsJSON.jsonToList(ReporteProformaDetalle.class, map.get("lista"));
        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporte"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteProformaDetalleImpresion(usuarioEmpresaReporteTO, lista,
                    nombreReporte);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteInvFunVentasVsCosto")
    public byte[] generarReporteInvFunVentasVsCosto(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String fechaDesde = UtilsJSON.jsonToObjeto(String.class, map.get("fechaDesde"));
        String fechaHasta = UtilsJSON.jsonToObjeto(String.class, map.get("fechaHasta"));
        String bodega = UtilsJSON.jsonToObjeto(String.class, map.get("bodega"));
        String clienteId = UtilsJSON.jsonToObjeto(String.class, map.get("clienteId"));
        List<InvFunVentasVsCostoTO> invFunVentasVsCostoTO = UtilsJSON.jsonToList(InvFunVentasVsCostoTO.class,
                map.get("invFunVentasVsCostoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteInvFunVentasVsCosto(usuarioEmpresaReporteTO, fechaDesde,
                    fechaHasta, bodega, clienteId, invFunVentasVsCostoTO);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteCompraDetalleImprimir")
    public byte[] generarReporteCompraDetalleImprimir(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class,
                map.get("usuarioEmpresaReporteTO"));
        String cmFormaImprimir = UtilsJSON.jsonToObjeto(String.class, map.get("cmFormaImprimir"));
        List<ReporteCompraDetalle> reporteCompraDetalles = UtilsJSON.jsonToList(ReporteCompraDetalle.class,
                map.get("reporteCompraDetalles"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));

        try {
            return reporteInventarioService.generarReporteCompraDetalleImprimir(usuarioEmpresaReporteTO,
                    reporteCompraDetalles, cmFormaImprimir);
        } catch (Exception e) {

            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/desmayorizarCompra")
    public String desmayorizarCompra(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasPK invComprasPK = UtilsJSON.jsonToObjeto(InvComprasPK.class, map.get("invComprasPK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String retorno = "";
        try {
            retorno = comprasService.mayorizarDesmayorizarComprasSql(invComprasPK, true, false, sisInfoTO);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                retorno = "F" + e.getMessage();
            }
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return retorno;
    }

    @RequestMapping("/anularCompra")
    public String anularCompra(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasPK invComprasPK = UtilsJSON.jsonToObjeto(InvComprasPK.class, map.get("invComprasPK"));
        boolean actualizarFechaUltimaValidacionSri = UtilsJSON.jsonToObjeto(boolean.class, map.get("actualizarFechaUltimaValidacionSri"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String retorno = "";
        try {
            retorno = comprasService.anularRestaurarComprasSql(invComprasPK, true, actualizarFechaUltimaValidacionSri, sisInfoTO);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                retorno = "F" + e.getMessage();
            }
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return retorno;
    }

    @RequestMapping("/restaurarCompra")
    public String restaurarCompra(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvComprasPK invComprasPK = UtilsJSON.jsonToObjeto(InvComprasPK.class, map.get("invComprasPK"));
        boolean actualizarFechaUltimaValidacionSri = UtilsJSON.jsonToObjeto(boolean.class, map.get("actualizarFechaUltimaValidacionSri"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        String retorno = "";
        try {
            retorno = comprasService.anularRestaurarComprasSql(invComprasPK, false, actualizarFechaUltimaValidacionSri, sisInfoTO);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            if (e instanceof PSQLException && !e.getMessage().contains("<NULL>")) {
                retorno = "F" + e.getMessage();
            }
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return retorno;
    }

    @RequestMapping("/obtenerDatosBasicosCompra")
    public InvComprasDatosBasicoTO obtenerDatosBasicosCompra(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        ConContablePK conContablePK = UtilsJSON.jsonToObjeto(ConContablePK.class, map.get("conContablePK"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return comprasService.getDatosBasicosCompra(conContablePK);
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/anularTransferencia")
    public String anularTransferencia(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return transferenciasService.anularTransferencia(empresa, periodo, motivo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/restaurarTransferencia")
    public String restaurarTransferencia(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return transferenciasService.restaurarTransferencia(empresa, periodo, motivo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/desmayorizarTransferencia")
    public String desmayorizarTransferencia(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return transferenciasService.desmayorizarTransferencia(empresa, periodo, motivo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/desmayorizarProforma")
    public String desmayorizarProforma(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proformasService.desmayorizarProforma(empresa, periodo, motivo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/anularProforma")
    public String anularProforma(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proformasService.anularProforma(empresa, periodo, motivo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/restaurarProforma")
    public String restaurarProforma(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        String periodo = UtilsJSON.jsonToObjeto(String.class, map.get("periodo"));
        String motivo = UtilsJSON.jsonToObjeto(String.class, map.get("motivo"));
        String numero = UtilsJSON.jsonToObjeto(String.class, map.get("numero"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return proformasService.restaurarProforma(empresa, periodo, motivo, numero);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER",
                    UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/eliminarInvPedidosMotivoTO")
    public String eliminarInvPedidosMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        InvPedidosMotivoTO invPedidosMotivoTO = UtilsJSON.jsonToObjeto(InvPedidosMotivoTO.class, map.get("invPedidosMotivoTO"));
        try {
            return pedidosMotivoService.eliminarInvPedidosMotivoTO(invPedidosMotivoTO, sisInfoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/getListaInvPedidosMotivoTO")
    public List<InvPedidosMotivoTO> getListaInvPedidosMotivoTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean filtrarInactivos = UtilsJSON.jsonToObjeto(boolean.class, map.get("filtrarInactivos"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return pedidosMotivoService.getListaInvPedidosMotivo(empresa, filtrarInactivos);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/listarPorcentajesIce")
    public List<AnxPorcentajeIce> listarPorcentajesIce(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return anexoPorcentajeIceService.listarAnexoPorcentajeIce();
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/insertarInvPedidosConfiguracionTO")
    public String insertarInvPedidosConfiguracionTO(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        InvPedidosConfiguracionTO invPedidosConfiguracionTO = UtilsJSON.jsonToObjeto(InvPedidosConfiguracionTO.class, map.get("invPedidosConfiguracionTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
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
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        InvPedidosMotivoTO invPedidosMotivoTO = UtilsJSON.jsonToObjeto(InvPedidosMotivoTO.class, map.get("invPedidosMotivoTO"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            //return pedidosConfiguracionService.getListaInvPedidosConfiguracionTO(empresa, invPedidosMotivoTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    //nuevos
    @RequestMapping("/generarReporteInvListaConsultaCompra")
    public byte[] generarReporteInvListaConsultaCompra(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvListaConsultaCompra> lista = UtilsJSON.jsonToList(InvListaConsultaCompra.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteInvListaConsultaCompra(lista, usuarioEmpresaReporteTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteInvListaConsultaVenta")
    public byte[] generarReporteInvListaConsultaVenta(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvListaConsultaVenta> lista = UtilsJSON.jsonToList(InvListaConsultaVenta.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteInvListaConsultaVenta(lista, usuarioEmpresaReporteTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteInvListaConsultaConsumo")
    public byte[] generarReporteInvListaConsultaConsumo(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvListaConsultaConsumo> lista = UtilsJSON.jsonToList(InvListaConsultaConsumo.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteInvListaConsultaConsumo(lista, usuarioEmpresaReporteTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }

    @RequestMapping("/generarReporteInvListaConsultaTransferencia")
    public byte[] generarReporteInvListaConsultaTransferencia(@RequestBody String json) {
        Map<String, Object> map = UtilsJSON.jsonToMap(json);
        UsuarioEmpresaReporteTO usuarioEmpresaReporteTO = UtilsJSON.jsonToObjeto(UsuarioEmpresaReporteTO.class, map.get("usuarioEmpresaReporteTO"));
        List<InvListaConsultaTransferencia> lista = UtilsJSON.jsonToList(InvListaConsultaTransferencia.class, map.get("lista"));
        SisInfoTO sisInfoTO = UtilsJSON.jsonToObjeto(SisInfoTO.class, map.get("sisInfoTO"));
        try {
            return reporteInventarioService.generarReporteInvListaConsultaTransferencia(lista, usuarioEmpresaReporteTO);
        } catch (Exception e) {
            envioCorreoService.enviarError("SERVER", UtilsExcepciones.crearSisErrorTO(e, getClass().getName(), "", sisInfoTO));
        }
        return null;
    }
}
