/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.service.ConceptoService;
import ec.com.todocompu.ShrimpSoftServer.anexos.service.PorcentajeIvaService;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.PedidosMotivoDetalleEjecutoresDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.PedidosMotivoDetalleRegistradoresDao;
import ec.com.todocompu.ShrimpSoftServer.pedidos.report.ReportePedidosService;
import ec.com.todocompu.ShrimpSoftServer.pedidos.service.InvPedidosService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.SectorService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SisEmpresaNotificacionesDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoOrdenCompraDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.PeriodoService;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.UsuarioService;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftServer.util.service.EnviarCorreoService;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.enums.TipoNotificacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.ComprasVSOrdenesCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteGrupoEmpresarialTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEntidadTransaccionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvOrdenCompraMotivoDetalleAprobadoresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosConfiguracionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosOrdenCompraMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosOrdenCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosReporteComprasImbTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosReporteEntregaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.OrdenCompraProveedor;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.OrdenCompraSaldo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.OrdenCompraVsCompraDolaresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvComprasPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoDetalleEjecutores;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoDetalleRegistradores;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompra;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProducto;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoEtiquetas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedor;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisEmailComprobanteElectronicoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoOrdenCompra;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuario;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Developer4
 */
@Service
public class PedidosOrdenCompraServiceImpl implements PedidosOrdenCompraService {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericoDao<InvPedidosOrdenCompra, InvPedidosOrdenCompraPK> pedidosOrdenCompraDao;
    @Autowired
    private InvPedidosService invPedidosService;
    @Autowired
    private PedidosDetalleService pedidosDetalleService;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private PedidosOrdenCompraMotivoService pedidosOrdenCompraMotivoService;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private GenericoDao<InvPedidos, InvPedidosPK> invPedidoDao;
    @Autowired
    private GenericoDao<InvProveedor, InvProveedorPK> invProveedorDao;
    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;
    @Autowired
    private SisEmpresaNotificacionesDao sisEmpresaNotificacionesDao;
    @Autowired
    private EnviarCorreoService envioCorreoService;
    @Autowired
    private ReportePedidosService reportePedidosService;
    @Autowired
    private GenericReporteService genericReporteService;
    @Autowired
    private PedidosConfiguracionService pedidosConfiguracionService;
    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private PedidosMotivoService pedidosMotivoService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PedidosMotivoDetalleEjecutoresDao pedidosMotivoDetalleEjecutoresDao;
    @Autowired
    private SectorService sectorService;
    @Autowired
    private InvClienteGrupoEmpresarialService invClienteGrupoEmpresarialService;
    @Autowired
    private PorcentajeIvaService porcentajeIvaService;
    @Autowired
    private ConceptoService conceptoService;
    @Autowired
    private ComprasMotivoService comprasMotivoService;
    @Autowired
    private PeriodoService periodoService;
    @Autowired
    private SucesoOrdenCompraDao sucesoOrdenCompraDao;
    @Autowired
    private PedidosMotivoDetalleRegistradoresDao pedidosMotivoDetalleRegistradoresDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Autowired
    private OrdenCompraConfiguracionService ordenCompraConfiguracionService;

    @Override
    public Map<String, Object> getDatosGenerarInvPedidosOrdenCompra(InvPedidosPK invPedidosPK, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        Map<String, Object> campos = new HashMap<>();
        InvPedidosOrdenCompra oc = completarDatosDeCliente(invPedidosPK);
        List<InvPedidosDetalle> listaInvPedidosDetalle = invPedidosService.listarDetallesParaOrdenDecompra(invPedidosPK);
        if (listaInvPedidosDetalle == null || listaInvPedidosDetalle.isEmpty()) {
            throw new GeneralException("No se puede realizar acción debido que el pedido relacionado no se encuentra aprobado o las cantidades del pedido se encuentra ocupado en su totalidad.");
        }
        String ordenDeCompra = obtenerNumeroDeOrdenDeCompra(listaInvPedidosDetalle);
        InvProductoEtiquetas invProductoEtiquetas = productoService.traerEtiquetas(invPedidosPK.getPedEmpresa());
        List<InvPedidosOrdenCompraMotivoTO> listaInvPedidosOrdenCompraMotivoTO = pedidosOrdenCompraMotivoService.getListaInvPedidosOrdenCompraMotivo(invPedidosPK.getPedEmpresa(), invPedidosPK.getPedSector());
        Date fechaActual = sistemaWebServicio.getFechaActual();
        campos.put("fechaActual", fechaActual);
        campos.put("invPedidosOrdenCompra", oc);
        campos.put("listaInvPedidosOrdenCompraMotivoTO", listaInvPedidosOrdenCompraMotivoTO);
        campos.put("invProductoEtiquetas", invProductoEtiquetas);
        campos.put("listaInvPedidosDetalle", listaInvPedidosDetalle);
        campos.put("ordenDeCompra", ordenDeCompra);
        return campos;
    }

    @Override
    public Map<String, Object> obtenerDatosParaGenerarOrdenCompra(InvPedidosPK invPedidosPK, InvPedidosMotivoPK invPedidosMotivoPK, String provCodigo, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        Map<String, Object> campos = new HashMap<>();
        InvPedidosOrdenCompra oc = completarDatosDeCliente(invPedidosPK);
        List<InvPedidosDetalle> listaInvPedidosDetalle = invPedidosService.listarDetallesParaOrdenDecompra(invPedidosPK);
        if (listaInvPedidosDetalle == null || listaInvPedidosDetalle.isEmpty()) {
            throw new GeneralException("No se puede realizar acción debido que el pedido relacionado no esta aprobado. Apruebe el pedido para poder realizar acción.");
        }
        InvPedidos ordenPedido = invPedidosService.obtenerInvPedidos(invPedidosPK);
        String ordenDeCompra = obtenerNumeroDeOrdenDeCompra(listaInvPedidosDetalle);
        InvProductoEtiquetas invProductoEtiquetas = productoService.traerEtiquetas(invPedidosPK.getPedEmpresa());
        List<InvPedidosOrdenCompraMotivoTO> listaInvPedidosOrdenCompraMotivoTO = pedidosOrdenCompraMotivoService.getListaInvPedidosOrdenCompraMotivo(invPedidosPK.getPedEmpresa(), invPedidosPK.getPedSector());
        Date fechaActual = sistemaWebServicio.getFechaActual();
        InvPedidosMotivo motivo = pedidosMotivoService.getInvPedidosMotivo(invPedidosMotivoPK);
        InvPedidosConfiguracionTO configuracion = pedidosConfiguracionService.getListaInvPedidosConfiguracionTO(invPedidosMotivoPK, sisInfoTO);
        AnxConceptoTO concepto = conceptoService.getBuscarAnexoConceptoTO(UtilsDate.fechaFormatoString(fechaActual, "yyyy-MM-dd"), "312");
        List<InvProveedorTO> proveedor = new ArrayList<>();
        if (provCodigo != null) {
            proveedor = proveedorService.getListProveedorTO(invPedidosPK.getPedEmpresa(), null, false, provCodigo);
        }
        BigDecimal valorPorcentaje = porcentajeIvaService.getValorAnxPorcentajeIvaTO(UtilsValidacion.fecha(fechaActual, "yyyy-MM-dd"));

        campos.put("invPedidosMotivo", motivo);
        campos.put("pmAprobacionAutomatica", motivo.getPmAprobacionAutomatica());
        campos.put("fechaActual", fechaActual);
        campos.put("valorPorcentajeIva", valorPorcentaje);
        campos.put("invPedidosOrdenCompra", oc);
        campos.put("configuracion", configuracion);
        campos.put("proveedor", proveedor.size() > 0 ? proveedor.get(0) : null);
        campos.put("listaInvPedidosOrdenCompraMotivoTO", listaInvPedidosOrdenCompraMotivoTO);
        campos.put("invProductoEtiquetas", invProductoEtiquetas);
        campos.put("listaInvPedidosDetalle", listaInvPedidosDetalle);
        campos.put("ordenDeCompra", ordenDeCompra);
        campos.put("concepto", concepto);
        campos.put("ordenPedido", ordenPedido);
        return campos;
    }

    @Override
    public Map<String, Object> insertarInvPedidosOrdenCompra(InvPedidosOrdenCompra invPedidosOrdenCompra, InvPedidosPK invPedidosPK, List<InvPedidosDetalle> listaInvPedidosDetalle, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        if (invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcNumero() == null || invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcNumero().trim().isEmpty() || "0".equals(invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcNumero())) {
            invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().setOcNumero(this.getProximaNumeracionInvPedidosOrdenCompra(invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcEmpresa(), invPedidosOrdenCompra));
        }
        if (pedidosOrdenCompraDao.obtener(InvPedidosOrdenCompra.class, invPedidosOrdenCompra.getInvPedidosOrdenCompraPK()) != null) {
            throw new GeneralException("El código ingresado ya está siendo utilizado.", "Código duplicado");
        }
        //Suceso de Orden de compra
        susClave = invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcNumero();
        susDetalle = "Se insertó la orden de compra con número: " + susClave;
        susSuceso = "INSERT";
        susTabla = "inventario.inv_pedidos_orden_compra";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        //AÑADE FECHA
        invPedidosOrdenCompra.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
        //INSERCION Y RESPUESTA
        List<InvPedidosOrdenCompraDetalle> ipd = invPedidosOrdenCompra.getInvPedidosOrdenCompraDetalleList();
        List<InvPedidosOrdenCompraDetalle> ipdValidos = new ArrayList<>();
        for (InvPedidosOrdenCompraDetalle ipd1 : ipd) {
            if (ipd1.getDetCantidad().compareTo(BigDecimal.ZERO) != 0 && ipd1.getDetPrecioReal().compareTo(BigDecimal.ZERO) != 0) {
                ipd1.setInvPedidosOrdenCompra(new InvPedidosOrdenCompra(invPedidosOrdenCompra.getInvPedidosOrdenCompraPK()));
                ipdValidos.add(ipd1);
            }
        }
        if (!ipdValidos.isEmpty()) {
            invPedidosOrdenCompra.setInvPedidosOrdenCompraDetalleList(ipdValidos);
            invPedidosOrdenCompra = pedidosOrdenCompraDao.insertar(invPedidosOrdenCompra);
            sucesoDao.insertar(sisSuceso);
            ////////////////crear suceso///////////////////////
            SisSucesoOrdenCompra sucesoOC = new SisSucesoOrdenCompra();
            InvPedidosOrdenCompra copia = ConversionesInventario.convertirInvPedidosOrdenCompra_InvPedidosOrdenCompra(invPedidosOrdenCompra);
            if (copia.getInvCliente() != null && copia.getInvCliente().getInvClientePK().getCliCodigo() == null) {
                copia.setInvCliente(null);
            }
            String json = UtilsJSON.objetoToJson(copia);
            sucesoOC.setSusJson(json);
            sucesoOC.setSisSuceso(sisSuceso);
            sucesoOC.setInvPedidosOrdenCompra(copia);
            sucesoOrdenCompraDao.insertar(sucesoOC);
        } else {
            invPedidosOrdenCompra.setInvPedidosOrdenCompraPK(null);
        }
        if (actualizarDetalleTomadosEnOrdenDeCompra(listaInvPedidosDetalle, sisInfoTO)) {
            ejecutarInvPedidos(invPedidosPK, sisInfoTO, invPedidosOrdenCompra.getUsrCodigo());
            this.modificarCostoReferencialInvProducto(ipdValidos, invPedidosOrdenCompra.getOcFormaPago(), sisInfoTO);
        }
        Map<String, Object> campos = new HashMap<>();
        campos.put("invPedidosOrdenCompraPK", invPedidosOrdenCompra.getInvPedidosOrdenCompraPK());
        return campos;
    }

    @Override
    public InvPedidosOrdenCompra mayorizarInvPedidosOrdenCompra(InvPedidosOrdenCompra invPedidosOrdenCompra, InvPedidosPK invPedidosPK, List<InvPedidosDetalle> listaInvPedidosDetalle, SisInfoTO sisInfoTO) throws Exception {

        if (!pedidosOrdenCompraDao.existe(InvPedidosOrdenCompra.class, invPedidosOrdenCompra.getInvPedidosOrdenCompraPK())) {
            throw new GeneralException("La orden de compra que desea mayorizar no existe.");
        }
        //Suceso de Orden de compra
        susClave = invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcNumero();
        susDetalle = "Se modificó la orden de compra con número: " + susClave;
        susSuceso = "UPDATE";
        susTabla = "inventario.inv_pedidos_orden_compra";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        //AÑADE FECHA
        invPedidosOrdenCompra.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
        invPedidosOrdenCompra.setUsrFechaModifica(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
        invPedidosOrdenCompra.setUsrCodigoModifica(sisInfoTO.getUsuario());
        //INSERCION Y RESPUESTA
        List<InvPedidosOrdenCompraDetalle> ipd = invPedidosOrdenCompra.getInvPedidosOrdenCompraDetalleList();
        List<InvPedidosOrdenCompraDetalle> ipdValidos = new ArrayList<>();
        int secuencialesEnCompraRepetidos = 0;
        for (InvPedidosOrdenCompraDetalle ipd1 : ipd) {
            if (ipd1.getDetCantidad().compareTo(BigDecimal.ZERO) != 0 && ipd1.getDetPrecioReal().compareTo(BigDecimal.ZERO) != 0) {
                ipd1.setInvPedidosOrdenCompra(new InvPedidosOrdenCompra(invPedidosOrdenCompra.getInvPedidosOrdenCompraPK()));
                ipdValidos.add(ipd1);
            }
            //verificar si existe en detalle de compras
            if (ipd1.getDetSecuencialOrdenCompra() != null) {
                if (verificarSiEstaEnDetalleCompra(ipd1.getDetSecuencialOrdenCompra(), invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcEmpresa())) {
                    secuencialesEnCompraRepetidos++;
                }
            }
        }

        if (secuencialesEnCompraRepetidos > 0) {
            throw new GeneralException("No se puede mayorizar una orden de compra que ya ha sido importada en compras.");
        } else {
            if (!ipdValidos.isEmpty()) {
                invPedidosOrdenCompra.setInvPedidosOrdenCompraDetalleList(ipdValidos);
                invPedidosOrdenCompra.setOcPendiente(false);
                invPedidosOrdenCompra = pedidosOrdenCompraDao.actualizar(invPedidosOrdenCompra);
                sucesoDao.insertar(sisSuceso);
                ////////////////crear suceso///////////////////////
                SisSucesoOrdenCompra sucesoOC = new SisSucesoOrdenCompra();
                InvPedidosOrdenCompra copia = ConversionesInventario.convertirInvPedidosOrdenCompra_InvPedidosOrdenCompra(invPedidosOrdenCompra);
                if (copia.getInvCliente() != null && copia.getInvCliente().getInvClientePK().getCliCodigo() == null) {
                    copia.setInvCliente(null);
                }
                String json = UtilsJSON.objetoToJson(copia);
                sucesoOC.setSusJson(json);
                sucesoOC.setSisSuceso(sisSuceso);
                sucesoOC.setInvPedidosOrdenCompra(copia);
                sucesoOrdenCompraDao.insertar(sucesoOC);
            }
            if (actualizarDetalleTomadosEnOrdenDeCompra(listaInvPedidosDetalle, sisInfoTO)) {
                ejecutarInvPedidos(invPedidosPK, sisInfoTO, invPedidosOrdenCompra.getUsrCodigo());
                this.modificarCostoReferencialInvProducto(ipdValidos, invPedidosOrdenCompra.getOcFormaPago(), sisInfoTO);
            }
            return invPedidosOrdenCompra;
        }

    }

    private boolean verificarSiEstaEnDetalleCompra(int secuencial, String empresa) {
        String sql = "SELECT COUNT(*)!=0 FROM inventario.inv_compras_detalle "
                + "WHERE (comp_empresa = '" + empresa
                + "' AND det_secuencial_orden_compra = '" + secuencial + "')";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    private InvComprasPK verificarSiOCEstaEnCompra(InvPedidosOrdenCompraPK invPedidosOrdenCompraPk) {
        String sql = "SELECT * FROM inventario.inv_compras "
                + "WHERE (oc_empresa = '" + invPedidosOrdenCompraPk.getOcEmpresa()
                + "' AND oc_sector = '" + invPedidosOrdenCompraPk.getOcSector()
                + "' AND oc_motivo = '" + invPedidosOrdenCompraPk.getOcMotivo()
                + "' AND oc_numero = '" + invPedidosOrdenCompraPk.getOcNumero() + "')";
        List<InvCompras> compras = genericSQLDao.obtenerPorSql(sql, InvCompras.class);
        if (compras != null && compras.size() > 0) {
            return compras.get(0).getInvComprasPK();
        } else {
            return null;
        }
    }

    private List<InvPedidosOrdenCompra> obtenerListaOCSegunPedido(InvPedidosPK pk) {
        String sql = "select  oc.* from inventario.inv_pedidos_orden_compra oc "
                + "inner join inventario.inv_pedidos_orden_compra_detalle ocd "
                + "on oc.oc_empresa = ocd.oc_empresa "
                + "and oc.oc_sector = ocd.oc_sector "
                + "and oc.oc_motivo = ocd.oc_motivo "
                + "and oc.oc_numero = ocd.oc_numero "
                + "inner join inventario.inv_pedidos_detalle opd "
                + "on opd.det_secuencial = ocd.det_secuencial_pedido "
                + "inner join inventario.inv_pedidos op "
                + "on op.ped_empresa = opd.ped_empresa "
                + "and op.ped_sector = opd.ped_sector "
                + "and op.ped_motivo = opd.ped_motivo "
                + "and op.ped_numero = opd.ped_numero "
                + "where op.ped_empresa = '" + pk.getPedEmpresa() + "' "
                + "and op.ped_sector = '" + pk.getPedSector() + "' "
                + "and op.ped_motivo = '" + pk.getPedMotivo() + "' "
                + "and op.ped_numero = '" + pk.getPedNumero() + "' "
                + "GROUP BY oc.oc_empresa,oc.oc_sector,oc.oc_motivo,oc.oc_numero";
        List<InvPedidosOrdenCompra> listaOC = genericSQLDao.obtenerPorSql(sql, InvPedidosOrdenCompra.class);
        return listaOC;
    }

    private String getProximaNumeracionInvPedidosOrdenCompra(String empresa, InvPedidosOrdenCompra invPedidosOrdenCompra) throws Exception {
        String sql = "SELECT num_secuencia FROM " + "inventario.inv_pedidos_orden_compra_numeracion"
                + " WHERE num_empresa = '" + empresa + "'"
                + " AND num_sector = '" + invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcSector() + "'"
                + " AND num_motivo = '" + invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcMotivo() + "' ";
        String consulta = (String) genericSQLDao.obtenerObjetoPorSql(sql);
        if (consulta != null) {
            invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().setOcNumero(consulta);
        } else {
            invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().setOcNumero("0");
        }
        int numeracion = Integer.parseInt(invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcNumero());
        String rellenarConCeros = "";
        do {
            numeracion++;
            String cadena = numeracion + "";
            rellenarConCeros = "";
            for (int i = 0; i < (7 - cadena.length()); i++) {
                rellenarConCeros = rellenarConCeros.concat("0");
            }
            invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().setOcNumero(rellenarConCeros + numeracion);
        } while (obtenerInvPedidosOrdenCompra(invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcEmpresa(), invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcSector(), invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcMotivo(), invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcNumero()) != null);
        return rellenarConCeros + numeracion;
    }

    public InvPedidosOrdenCompra obtenerInvPedidosOrdenCompra(String ocEmpresa, String ocSector, String ocMotivo, String ocNumero) throws Exception {
        return pedidosOrdenCompraDao.obtener(InvPedidosOrdenCompra.class, new InvPedidosOrdenCompraPK(ocEmpresa.trim(), ocSector.trim(), ocMotivo.trim(), ocNumero.trim()));
    }

    @Override
    public InvEntidadTransaccionTO obtenerProveedorDeOrdenDeCompra(String empresa, String sector, String motivo, String numero) throws Exception {
        InvPedidosOrdenCompra oc = obtenerInvPedidosOrdenCompra(empresa, sector, motivo, numero);
        if (oc != null) {
            InvEntidadTransaccionTO entidadTransaccion = new InvEntidadTransaccionTO();
            entidadTransaccion.setDocumento(oc.getInvPedidosOrdenCompraPK().getOcEmpresa() + "-" + oc.getInvPedidosOrdenCompraPK().getOcSector() + "-" + oc.getInvPedidosOrdenCompraPK().getOcMotivo() + "-" + oc.getInvPedidosOrdenCompraPK().getOcNumero());
            entidadTransaccion.setTipo("Orden de compra");
            entidadTransaccion.setRazonSocial(oc.getInvProveedor().getProvRazonSocial());
            entidadTransaccion.setIdentificacion(oc.getInvProveedor().getProvIdNumero());
            return entidadTransaccion;
        }
        return null;
    }

    private void modificarCostoReferencialInvProducto(List<InvPedidosOrdenCompraDetalle> listaInvPedidosDetalle, String etiqueta, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        List<InvProducto> listaInvProducto = new ArrayList<>();
        for (InvPedidosOrdenCompraDetalle detalleOC : listaInvPedidosDetalle) {
            InvPedidosDetalle invPedidosDetalle = detalleOC.getInvPedidosDetalle();
            InvProducto invProducto = productoService.obtenerPorId(sisInfoTO.getEmpresa(), invPedidosDetalle.getInvProducto().getInvProductoPK().getProCodigoPrincipal());
            if (invProducto != null) {
                switch (etiqueta) {
                    case "proCostoReferencial1":
                        invProducto.setProCostoReferencial1(detalleOC.getDetPrecioReal());
                        listaInvProducto.add(invProducto);
                        break;
                    case "proCostoReferencial2":
                        invProducto.setProCostoReferencial2(detalleOC.getDetPrecioReal());
                        listaInvProducto.add(invProducto);
                        break;
                    case "proCostoReferencial3":
                        invProducto.setProCostoReferencial3(detalleOC.getDetPrecioReal());
                        listaInvProducto.add(invProducto);
                        break;
                    case "proCostoReferencial4":
                        invProducto.setProCostoReferencial4(detalleOC.getDetPrecioReal());
                        listaInvProducto.add(invProducto);
                        break;
                    case "proCostoReferencial5":
                        invProducto.setProCostoReferencial5(detalleOC.getDetPrecioReal());
                        listaInvProducto.add(invProducto);
                        break;
                }
            }
        }
        if (listaInvProducto.size() > 0) {
            productoService.modificarListaInvProducto(listaInvProducto, sisInfoTO);
        }
    }

    private InvPedidos ejecutarInvPedidos(InvPedidosPK invPedidosPK, SisInfoTO sisInfoTO, String usuarioEjecuta) {
        InvPedidos invPedidos = invPedidosService.obtenerInvPedidos(invPedidosPK);
        boolean isEjecutado = true;
        if (invPedidos == null) {
            throw new GeneralException("No se generó la orden de compra.", "Error al actualizar detalle");
        }
        for (InvPedidosDetalle invPedidosDetalle : invPedidos.getInvPedidosDetalleList()) {
            if (invPedidosDetalle.getDetCantidadAprobada().compareTo(BigDecimal.ZERO) > 0
                    && invPedidosDetalle.getDetCantidadAdquirida().compareTo(invPedidosDetalle.getDetCantidadAprobada()) != 0) {
                isEjecutado = false;
                break;
            }
        }

        //Se ejecuta el pedido
        if (isEjecutado) {
            //Suceso de la Orden de pedido
            susClave = invPedidos.getInvPedidosPK().getPedNumero();
            susDetalle = "Se modificó la orden de pedido con número " + susClave + " al estado EJECUTADO";
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_pedidos";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            //Actualizar pedido a ejecutado
            invPedidos.setPedEjecutado(true);
            invPedidos.setUsrEjecuta(usuarioEjecuta);
            invPedidos = invPedidoDao.actualizar(invPedidos);
            sucesoDao.insertar(sisSuceso);
        }
        return invPedidos;
    }

    @Override
    public List<InvPedidosOrdenCompraTO> getListaInvPedidosOrdenCompraTO(String empresa, String motivo, String fechaInicio, String fechaFin, Boolean incluirAnulados, String busqueda, Integer nroRegistros, String usuario) throws GeneralException, Exception {
        String porcionSql = "";
        String porcionSqlLimit = "";
        if (nroRegistros != null) {
            porcionSqlLimit = "LIMIT " + nroRegistros + " ";
        }
        if (motivo != null) {
            porcionSql = " AND m.ocm_codigo = '" + motivo + "'";
        }
        if (fechaInicio != null && fechaFin != null) {
            porcionSql = porcionSql + " AND oc_fecha_emision >= '$$ || " + fechaInicio + "|| $$' ";
            porcionSql = porcionSql + " AND oc_fecha_emision <= '$$ || " + fechaFin + "|| $$' ";
        }
        if (busqueda != null && !busqueda.equals("")) {
            porcionSql = porcionSql + " AND p.oc_sector = '" + busqueda + "' ";
        }
        if (incluirAnulados == null || incluirAnulados == false) {
            porcionSql = porcionSql + " AND oc_anulado = false";
        }
        String query = "SELECT ROW_NUMBER() OVER(ORDER BY p.oc_fecha_emision DESC,p.oc_numero DESC) AS index, p.oc_empresa as ocEmpresa, p.oc_sector as ocSector, p.oc_motivo as ocMotivo, p.oc_numero as ocNumero, "
                + " p.oc_fecha_emision as ocFecha, p.oc_anulado as ocAnulado, p.oc_aprobada as ocAprobada, p.oc_cerrada as ocCerrada, p.oc_pendiente as ocPendiente, p.oc_monto_total as ocMontoTotal, p.prov_codigo as provCodigo,"
                + " r.prov_razon_social as provRazonSocial, r.prov_email_orden_compra as provEmailOrdenCompra, m.ocm_detalle as ocmDetalle, m.ocm_notificar_proveedor as ocmNotificarProveedor, "
                + " u.usr_nombre || ' ' || u.usr_apellido as usrAprueba, p.usr_codigo as usrCodigo, m.ocm_aprobacion_automatica as ocAprobacionAutomatica, m.id_notificaciones as idNotificaciones, "
                //usuario aprobador
                + " CASE WHEN '" + usuario + "' in "
                + " ("
                + "  SELECT usr_codigo FROM inventario.inv_pedidos_orden_compra_motivo_detalle_aprobadores "
                + "  WHERE ocm_empresa = p.oc_empresa AND oc_sector = p.oc_sector AND ocm_codigo = p.oc_motivo"
                + " ) THEN TRUE ELSE FALSE end as ocPuedeAprobar,"
                //usuario ejecutor
                + " case when '" + usuario + "' in "
                + " ("
                + "  SELECT ejec.usr_codigo FROM inventario.inv_pedidos_motivo_detalle_ejecutores ejec "
                + "  INNER JOIN inventario.inv_pedidos_motivo m"
                + "  ON ejec.pm_empresa = m.pm_empresa AND ejec.pm_sector = m.pm_sector AND ejec.pm_codigo = m.pm_codigo"
                + "  INNER JOIN inventario.inv_pedidos ped "
                + "  ON m.pm_empresa = ped.ped_empresa and m.pm_sector = ped.ped_sector AND m.pm_codigo = ped.ped_motivo"
                + "  INNER JOIN inventario.inv_pedidos_detalle d"
                + "  ON d.ped_empresa = ped.ped_empresa AND d.ped_sector = ped.ped_sector AND d.ped_motivo = ped.ped_motivo AND d.ped_numero = ped.ped_numero"
                + "  INNER JOIN inventario.inv_pedidos_orden_compra_detalle ocd"
                + "  ON ocd.det_secuencial_pedido = d.det_secuencial"
                + "  WHERE ocd.oc_empresa = p.oc_empresa AND ocd.oc_sector = p.oc_sector AND ocd.oc_motivo = p.oc_motivo AND ocd.oc_numero = p.oc_numero"
                + "  ) THEN TRUE ELSE FALSE end as ocPuedeModificar ,"
                + " (select det.ped_sector || '|' || det.ped_motivo || '|' || det.ped_numero as pk "
                + " from inventario.inv_pedidos_detalle det "
                + " INNER JOIN inventario.inv_pedidos_orden_compra_detalle detOC "
                + " ON det.det_secuencial = detOC.det_secuencial_pedido "
                + " WHERE p.oc_empresa = detOC.oc_empresa and p.oc_sector = detOC.oc_sector AND p.oc_motivo = detOC.oc_motivo AND p.oc_numero = detOC.oc_numero LIMIT 1) as ordenPedido, "
                + " (SELECT ped.usr_registra FROM inventario.inv_pedidos ped  INNER JOIN inventario.inv_pedidos_detalle pd ON ped.ped_empresa = pd.ped_empresa AND ped.ped_sector = pd.ped_sector "
                + " AND ped.ped_motivo = pd.ped_motivo AND ped.ped_numero = pd.ped_numero INNER JOIN inventario.inv_pedidos_orden_compra_detalle ocd ON pd.det_secuencial = ocd.det_secuencial_pedido"
                + " WHERE p.oc_empresa = ocd.oc_empresa and p.oc_sector = ocd.oc_sector AND p.oc_motivo = ocd.oc_motivo AND p.oc_numero = ocd.oc_numero LIMIT 1) as registrador,"
                + "(SELECT us.usr_email_usuario FROM sistemaweb.sis_usuario us INNER JOIN inventario.inv_pedidos ped ON us.usr_codigo = ped.usr_registra INNER JOIN inventario.inv_pedidos_detalle d "
                + " ON ped.ped_empresa = d.ped_empresa AND ped.ped_sector = d.ped_sector AND ped.ped_motivo = d.ped_motivo AND ped.ped_numero = d.ped_numero INNER JOIN inventario.inv_pedidos_orden_compra_detalle detOC ON d.det_secuencial = detOC.det_secuencial_pedido "
                + " WHERE p.oc_empresa = detOC.oc_empresa and p.oc_sector = detOC.oc_sector AND p.oc_motivo = detOC.oc_motivo AND p.oc_numero = detOC.oc_numero LIMIT 1) as emailRegistrador,"
                + " p.oc_base0 ocBase0, p.oc_base_imponible ocBaseImponible, p.oc_monto_iva ocMontoIva, p.oc_iva_vigente ocIvaVigente "
                + " FROM inventario.inv_pedidos_orden_compra p"
                + " INNER JOIN inventario.inv_proveedor r ON p.prov_codigo = r.prov_codigo AND p.prov_empresa = r.prov_empresa"
                + " LEFT JOIN sistemaweb.sis_usuario u ON p.usr_aprueba = u.usr_codigo"
                + " INNER JOIN inventario.inv_pedidos_orden_compra_motivo m ON p.oc_motivo = m.ocm_codigo AND p.oc_sector = m.ocm_sector AND p.oc_empresa = m.ocm_empresa"
                + " WHERE ocm_empresa = '" + empresa + "'"
                + porcionSql + porcionSqlLimit + ";";
        List<InvPedidosOrdenCompraTO> i = genericSQLDao.obtenerPorSql(query, InvPedidosOrdenCompraTO.class);
        return i;
    }

    @Override
    public List<InvPedidosOrdenCompraTO> listarOrdenCompraParaNotificaciones(String empresa, String motivo, String fechaInicio, String fechaFin, String busqueda, Integer nroRegistros) throws Exception {
        String porcionSqlLimit = nroRegistros != null ? "LIMIT " + nroRegistros + " " : "";
        String porcionSql = motivo != null ? " AND m.ocm_codigo = '" + motivo + "'" : "";
        if (fechaInicio != null && fechaFin != null) {
            porcionSql = porcionSql + " AND oc_fecha_emision >= '$$ || " + fechaInicio + "|| $$' ";
            porcionSql = porcionSql + " AND oc_fecha_emision <= '$$ || " + fechaFin + "|| $$' ";
        }
        if (busqueda != null && !busqueda.equals("")) {
            porcionSql = porcionSql + " AND p.oc_sector = '" + busqueda + "' ";
        }
        String query = "SELECT distinct on (p.oc_empresa, p.oc_sector, p.oc_motivo, p.oc_numero)"
                + " ROW_NUMBER() OVER() AS index, p.oc_empresa as ocEmpresa, p.oc_sector as ocSector, p.oc_motivo as ocMotivo, p.oc_numero as ocNumero, "
                + " p.oc_fecha_emision as ocFecha, p.oc_anulado as ocAnulado, p.oc_aprobada as ocAprobada, p.oc_cerrada as ocCerrada,"
                + " p.oc_pendiente as ocPendiente, p.oc_monto_total as ocMontoTotal, p.prov_codigo as provCodigo,"
                + " r.prov_razon_social as provRazonSocial, r.prov_email_orden_compra as provEmailOrdenCompra, m.ocm_detalle as ocmDetalle, "
                + " m.ocm_notificar_proveedor as ocmNotificarProveedor, m.id_notificaciones as idNotificaciones, "
                + " null as usrAprueba, "
                + " false as ocPuedeAprobar, "
                + " false as ocPuedeModificar, "
                + " (select det.ped_sector || '|' || det.ped_motivo || '|' || det.ped_numero as pk "
                + " from inventario.inv_pedidos_detalle det "
                + " INNER JOIN inventario.inv_pedidos_orden_compra_detalle detOC "
                + " ON det.det_secuencial = detOC.det_secuencial_pedido "
                + " WHERE p.oc_empresa = detOC.oc_empresa and p.oc_sector = detOC.oc_sector AND p.oc_motivo = detOC.oc_motivo AND p.oc_numero = detOC.oc_numero LIMIT 1) as ordenPedido, "
                + " null as usrCodigo,"
                + " false as ocAprobacionAutomatica, p.oc_base0 ocBase0, p.oc_base_imponible ocBaseImponible, p.oc_monto_iva ocMontoIva, p.oc_iva_vigente ocIvaVigente "
                + " FROM inventario.inv_pedidos_orden_compra_notificaciones n"
                + " INNER JOIN inventario.inv_pedidos_orden_compra p"
                + " ON p.oc_empresa = n.oc_empresa AND p.oc_sector = n.oc_sector AND p.oc_motivo = n.oc_motivo AND p.oc_numero = n.oc_numero"
                + " INNER JOIN inventario.inv_proveedor r ON p.prov_codigo = r.prov_codigo AND p.prov_empresa = r.prov_empresa"
                + " INNER JOIN inventario.inv_pedidos_orden_compra_motivo m ON p.oc_motivo = m.ocm_codigo AND p.oc_sector = m.ocm_sector AND p.oc_empresa = m.ocm_empresa"
                + " WHERE ocm_empresa = '" + empresa + "'"
                + porcionSql + porcionSqlLimit + ";";
        List<InvPedidosOrdenCompraTO> i = genericSQLDao.obtenerPorSql(query, InvPedidosOrdenCompraTO.class);
        return i;
    }

    @Override
    public List<InvPedidosOrdenCompraNotificaciones> listarNotificacionesPorOrdenDeCompra(String empresa, String motivo, String sector, String numero) throws Exception {
        String query = "SELECT * FROM inventario.inv_pedidos_orden_compra_notificaciones n"
                + " WHERE n.oc_empresa = '" + empresa + "' AND n.oc_sector = '" + sector + "' AND n.oc_motivo = '" + motivo + "' AND n.oc_numero = '" + numero + "' order by n.ocn_fecha;";
        List<InvPedidosOrdenCompraNotificaciones> notificaciones = genericSQLDao.obtenerPorSql(query, InvPedidosOrdenCompraNotificaciones.class);
        return notificaciones;
    }

    @Override
    public InvPedidosOrdenCompra getInvPedidosOrdenCompra(InvPedidosOrdenCompraPK pk) throws GeneralException, Exception {
        InvPedidosOrdenCompra ipoc = pedidosOrdenCompraDao.obtener(InvPedidosOrdenCompra.class, pk);
        if (ipoc != null) {
            if (ipoc.getInvProveedor() != null) {
                ipoc.getInvProveedor().setInvComprasList(null);
            }
            ipoc.getInvPedidosOrdenCompraDetalleList().forEach((invPedidosOrdenCompraDetalleList) -> {
                invPedidosOrdenCompraDetalleList.setInvPedidosOrdenCompra(new InvPedidosOrdenCompra(pk));
                InvPedidosDetalle ipd = invPedidosOrdenCompraDetalleList.getInvPedidosDetalle();
                InvPedidos pedido = new InvPedidos(ipd.getInvPedidos().getInvPedidosPK());
                pedido.setPedFechaEmision(ipd.getInvPedidos().getPedFechaEmision());
                pedido.setPedLugarEntrega(ipd.getInvPedidos().getPedLugarEntrega());
                pedido.setUsrRegistra(ipd.getInvPedidos().getUsrRegistra());
                pedido.setUsrAprueba(ipd.getInvPedidos().getUsrAprueba());
                pedido.setUsrEjecuta(ipd.getInvPedidos().getUsrEjecuta());
                pedido.setPedOrdenCompra(ipd.getInvPedidos() != null ? ipd.getInvPedidos().getPedOrdenCompra() : "");
                pedido.setInvPedidosMotivo(ipd.getInvPedidos().getInvPedidosMotivo());
                ipd.setInvPedidos(pedido);
            });
        }
        return ipoc;
    }

    @Override
    public Map<String, Object> obtenerOrdenCompraSoloAprobadas(InvPedidosOrdenCompra ipoc, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        Map<String, Object> map = new HashMap<>();
        List<OrdenCompraSaldo> listaOrdenCompraSaldo = new ArrayList<>();
        if (ipoc != null && ipoc.getOcAprobada()) {
            listaOrdenCompraSaldo = getInvPedidosOrdenCompraSaldo(ipoc.getInvPedidosOrdenCompraPK());
            if (ipoc.getInvProveedor() != null) {
                ipoc.getInvProveedor().setInvComprasList(null);
            }
        }
        map.put("ordenDeCompra", ipoc);
        map.put("listaOrdenCompraSaldo", listaOrdenCompraSaldo);
        return map;
    }

    @Override
    public List<OrdenCompraSaldo> getInvPedidosOrdenCompraSaldo(InvPedidosOrdenCompraPK pk) throws GeneralException, Exception {
        String sql = "SELECT * FROM inventario.fun_orden_compra_saldo('" + pk.getOcEmpresa() + "', '" + pk.getOcSector() + "', '" + pk.getOcMotivo() + "', '" + pk.getOcNumero() + "')";
        return genericSQLDao.obtenerPorSql(sql, OrdenCompraSaldo.class);
    }

    @Override
    public List<OrdenCompraProveedor> getInvPedidosOrdenCompraPorProveedorConSaldo(String empresa, String codigoProveedor, String oc_sector, String oc_motivo, String oc_numero) throws GeneralException, Exception {
        oc_sector = oc_sector != null && !oc_sector.equals("") ? "'" + oc_sector + "'" : null;
        oc_motivo = oc_motivo != null && !oc_motivo.equals("") ? "'" + oc_motivo + "'" : null;
        oc_numero = oc_numero != null && !oc_numero.equals("") ? "'" + oc_numero + "'" : null;
        String sql = "SELECT * FROM inventario.fun_orden_compra_saldo_proveedor('" + empresa + "', '" + codigoProveedor + "'," + oc_sector + "," + oc_motivo + "," + oc_numero + ")";
        return genericSQLDao.obtenerPorSql(sql, OrdenCompraProveedor.class);
    }

    @Override
    public List<InvPedidosReporteEntregaTO> listarReporteEntrega(String empresa, String motivo, String sector, String producto, String cliente, String proveedor, String grupo, String fechaInicio, String fechaFin) throws Exception {
        String sqlCodigoSector = sector != null ? "'" + sector + "'" : null;
        String sqlCodigoGrupo = grupo != null ? "'" + grupo + "'" : null;
        String sqlCodigoMotivo = motivo != null ? "'" + motivo + "'" : null;
        String sqlCodigoFechaDesde = fechaInicio != null ? "'" + fechaInicio + "'" : null;
        String sqlCodigoFechaHasta = fechaFin != null ? "'" + fechaFin + "'" : null;
        String sqlProducto = producto != null ? "'" + producto + "'" : null;
        String sqlProveedor = proveedor != null ? "'" + proveedor + "'" : null;
        String sqlCliente = cliente != null ? "'" + cliente + "'" : null;

        String sql = "SELECT * FROM inventario.fun_pedidos_reporte_entrega('" + empresa + "'," + sqlCodigoSector + "," + sqlCodigoMotivo + ","
                + sqlProducto + "," + sqlCliente + "," + sqlProveedor + "," + sqlCodigoGrupo + "," + sqlCodigoFechaDesde + "," + sqlCodigoFechaHasta + ")";
        return genericSQLDao.obtenerPorSql(sql, InvPedidosReporteEntregaTO.class);
    }

    @Override
    public List<InvPedidosReporteComprasImbTO> listarReporteComprasImb(String empresa, String periodo, String motivo, String producto, String proveedor, String fechaInicio, String fechaFin) throws Exception {
        String sqlCodigoPeriodo = periodo != null ? "'" + periodo + "'" : null;
        String sqlCodigoMotivo = motivo != null ? "'" + motivo + "'" : null;
        String sqlCodigoFechaDesde = fechaInicio != null ? "'" + fechaInicio + "'" : null;
        String sqlCodigoFechaHasta = fechaFin != null ? "'" + fechaFin + "'" : null;
        String sqlProducto = producto != null ? "'" + producto + "'" : null;
        String sqlProveedor = proveedor != null ? "'" + proveedor + "'" : null;

        String sql = "SELECT * FROM inventario.fun_pedidos_reporte_compras_imb('" + empresa + "'," + sqlCodigoPeriodo + "," + sqlCodigoMotivo + ","
                + sqlProducto + "," + sqlProveedor + "," + sqlCodigoFechaDesde + "," + sqlCodigoFechaHasta + ")";
        return genericSQLDao.obtenerPorSql(sql, InvPedidosReporteComprasImbTO.class);
    }

    @Override
    public List<ComprasVSOrdenesCompras> getInvPedidosOrdenCompraVsOrdenesCompras(String empresa, String codigoProveedor, String sector, String motivo, String fechaDesde, String fechaHasta) throws GeneralException, Exception {
        String sqlCodigoProveedor = codigoProveedor != null ? "'" + codigoProveedor + "'" : null;
        String sqlCodigoSector = sector != null ? "'" + sector + "'" : null;
        String sqlCodigoMotivo = motivo != null ? "'" + motivo + "'" : null;
        String sqlCodigoFechaDesde = fechaDesde != null ? "'" + fechaDesde + "'" : null;
        String sqlCodigoFechaHasta = fechaHasta != null ? "'" + fechaHasta + "'" : null;
        String sql = "SELECT * FROM inventario.fun_orden_compra_vs_compra('" + empresa + "'," + sqlCodigoProveedor + "," + sqlCodigoSector + "," + sqlCodigoMotivo + "," + sqlCodigoFechaDesde + "," + sqlCodigoFechaHasta + ")";
        return genericSQLDao.obtenerPorSql(sql, ComprasVSOrdenesCompras.class);
    }

    @Override
    public List<OrdenCompraVsCompraDolaresTO> listarOrdenCompraVsCompraDolares(String empresa, String sector, String motivo, String fechaDesde, String fechaHasta) throws Exception {
        String sqlCodigoSector = sector != null ? "'" + sector + "'" : null;
        String sqlCodigoMotivo = motivo != null ? "'" + motivo + "'" : null;
        String sqlCodigoFechaDesde = fechaDesde != null ? "'" + fechaDesde + "'" : null;
        String sqlCodigoFechaHasta = fechaHasta != null ? "'" + fechaHasta + "'" : null;
        String sql = "SELECT * FROM inventario.fun_orden_compra_vs_compra_dolares('" + empresa + "'," + sqlCodigoSector + "," + sqlCodigoMotivo + "," + sqlCodigoFechaDesde + "," + sqlCodigoFechaHasta + ")";
        return genericSQLDao.obtenerPorSql(sql, OrdenCompraVsCompraDolaresTO.class);
    }

    @Override
    public List<InvPedidosOrdenCompraDetalle> getInvPedidosOrdenCompraDetalle(InvPedidosOrdenCompraPK pk) throws GeneralException, Exception {
        InvPedidosOrdenCompra ipoc = pedidosOrdenCompraDao.obtener(InvPedidosOrdenCompra.class, pk);
        if (ipoc != null) {
            if (ipoc.getInvPedidosOrdenCompraDetalleList() != null) {
                ipoc.getInvPedidosOrdenCompraDetalleList().forEach((detalle) -> {
                    detalle.setInvPedidosDetalle(new InvPedidosDetalle(detalle.getInvPedidosDetalle().getDetSecuencial()));
                    detalle.setInvPedidosOrdenCompra(new InvPedidosOrdenCompra(detalle.getInvPedidosOrdenCompra().getInvPedidosOrdenCompraPK()));
                });
                return ipoc.getInvPedidosOrdenCompraDetalleList();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public Map<String, Object> obtenerInvPedidosOrdenCompra(InvPedidosOrdenCompraPK pk, boolean esParaMayorizar, boolean aprobar, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        Map<String, Object> map = new HashMap<>();
        InvPedidosOrdenCompra invPedidosOrdenCompra = this.getInvPedidosOrdenCompra(pk);
        String ordenDeCompra = obtenerNumeroDeOrdenDeCompra(invPedidosOrdenCompra);
        List<InvPedidosOrdenCompraMotivoTO> listaInvPedidosOrdenCompraMotivoTO = pedidosOrdenCompraMotivoService.getListaInvPedidosOrdenCompraMotivo(pk.getOcEmpresa(), pk.getOcSector());
        InvProductoEtiquetas invProductoEtiquetas = productoService.traerEtiquetas(pk.getOcEmpresa());
        BigDecimal valorPorcentaje = porcentajeIvaService.getValorAnxPorcentajeIvaTO(UtilsValidacion.fecha(invPedidosOrdenCompra.getOcFechaEmision(), "yyyy-MM-dd"));
        AnxConceptoTO concepto = conceptoService.getBuscarAnexoConceptoTO(UtilsValidacion.fecha(invPedidosOrdenCompra.getOcFechaEmision(), "yyyy-MM-dd"), "312");

        map.put("invPedidosOrdenCompra", invPedidosOrdenCompra);
        map.put("valorPorcentaje", valorPorcentaje);
        map.put("concepto", concepto);
        map.put("listaInvPedidosOrdenCompraMotivoTO", listaInvPedidosOrdenCompraMotivoTO);
        map.put("invProductoEtiquetas", invProductoEtiquetas);
        map.put("ordenDeCompra", ordenDeCompra);
        String usrRegistraOrdenPedido = null;
        SisUsuario usuarioRegistradorInvPedido = null;
        InvPedidosMotivo invPedidosMotivo = null;
        if (invPedidosOrdenCompra != null && invPedidosOrdenCompra.getInvPedidosOrdenCompraDetalleList().size() > 0) {
            InvPedidos invPedidos = invPedidosOrdenCompra.getInvPedidosOrdenCompraDetalleList().get(0).getInvPedidosDetalle().getInvPedidos();
            InvPedidosMotivoPK invPedidosMotivoPK = invPedidos.getInvPedidosMotivo().getInvPedidosMotivoPK();
            invPedidosMotivo = pedidosMotivoService.getInvPedidosMotivo(invPedidosMotivoPK);
            usrRegistraOrdenPedido = invPedidos.getUsrRegistra();
            usuarioRegistradorInvPedido = usuarioService.obtenerPorId(usrRegistraOrdenPedido);
        }
        map.put("usuarioRegistradorInvPedido", usuarioRegistradorInvPedido);
        map.put("invPedidosMotivo", invPedidosMotivo);
        if (esParaMayorizar) {
            if (invPedidosOrdenCompra != null && invPedidosOrdenCompra.getInvPedidosOrdenCompraDetalleList() != null && invPedidosOrdenCompra.getInvPedidosOrdenCompraDetalleList().size() > 0) {
                InvPedidos pedido = invPedidosOrdenCompra.getInvPedidosOrdenCompraDetalleList().get(0).getInvPedidosDetalle().getInvPedidos();
                pedido = invPedidoDao.obtener(InvPedidos.class, pedido.getInvPedidosPK());
                if (pedido != null) {
                    invPedidosOrdenCompra.setOcFechaHoraEntrega(pedido.getPedFechaHoraEntrega());
                }
                if (pedido != null && pedido.getInvCliente() != null) {
                    InvCliente cliente = new InvCliente(pedido.getInvCliente().getInvClientePK());
                    cliente.setCliRazonSocial(pedido.getInvCliente().getCliRazonSocial());
                    invPedidosOrdenCompra.setInvCliente(cliente);
                    invPedidosOrdenCompra.setOcContactoNombre(pedido.getPedContactoNombre());
                    invPedidosOrdenCompra.setOcContactoTelefono(pedido.getPedContactoTelefono());
                    invPedidosOrdenCompra.setOcLugarEntrega(pedido.getPedLugarEntrega());
                    invPedidosOrdenCompra.setOcObservacionesRegistra(pedido.getPedObservacionesRegistra());
                }
            }
        }
        if (aprobar) {
            InvPedidosOrdenCompraMotivo motivo = invPedidosOrdenCompra.getInvPedidosOrdenCompraMotivo();
            if (!motivo.getOcmAprobacionAutomatica()) {
                List<InvOrdenCompraMotivoDetalleAprobadoresTO> aprobadores = ordenCompraConfiguracionService.getListaInvOrdenCompraMotivoDetalleAprobadoresTO(motivo.getInvPedidosOrdenCompraMotivoPK(), null);
                if (aprobadores != null && aprobadores.size() > 0) {
                    for (InvOrdenCompraMotivoDetalleAprobadoresTO aprobador : aprobadores) {
                        if (sisInfoTO.getUsuario().equals(aprobador.getUsuario().getUsrCodigo())) {
                            map.put("aprobador", aprobador);
                        }
                    }
                }
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> listarInvPedidosOrdenCompra(Integer detSecuencial, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        Map<String, Object> map = new HashMap<>();
        List<InvPedidosOrdenCompra> listadoInvPedidosOrdenCompra = listarOrdenesDeCompraSegunSecuencialDePedido(detSecuencial);
        InvProductoEtiquetas invProductoEtiquetas = productoService.traerEtiquetas(sisInfoTO.getEmpresa());
        map.put("listadoInvPedidosOrdenCompra", listadoInvPedidosOrdenCompra);
        map.put("invProductoEtiquetas", invProductoEtiquetas);
        return map;
    }

    @Override
    public String enviarPdfOrdenCompra(String empresa, String nombreReporte, InvPedidosOrdenCompraPK invPedidosOrdenCompraPK, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String emailProveedor, Integer idNotificacionEntero, HttpServletResponse response) throws Exception, GeneralException {
        SisEmailComprobanteElectronicoTO sisEmailComprobanteElectronicoTO = new SisEmailComprobanteElectronicoTO();
        InvPedidosOrdenCompra invPedidosOrdenCompra = getInvPedidosOrdenCompra(invPedidosOrdenCompraPK);
        if (invPedidosOrdenCompra == null) {
            throw new GeneralException("No se encontró orden de compra.");
        }
        InvProveedor invProveedor = invPedidosOrdenCompra.getInvProveedor();
        if (invProveedor == null) {
            throw new GeneralException("No se encontró proveedor.");
        }
        if (emailProveedor != null && !emailProveedor.equals("")) {
            invProveedor.setProvEmailOrdenCompra(emailProveedor);
            invProveedorDao.actualizar(invProveedor);
        }
        if (invProveedor.getProvEmailOrdenCompra() == null || invProveedor.getProvEmailOrdenCompra().equals("")) {
            throw new GeneralException("No se encontraron direcciones de correo para el proveedor indicado.");
        }
        SisEmpresaParametros sisEmpresaParametros = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, empresa);
        sisEmailComprobanteElectronicoTO.setRucEmisor(sisEmpresaParametros.getEmpCodigo().getEmpRuc());
        if (sisEmpresaParametros.getEmpCodigo().getEmpRuc().equalsIgnoreCase("0903837367001")) {
            sisEmailComprobanteElectronicoTO.setNombreEmisor("AUTOPINTURAS ASSAN");
        } else {
            sisEmailComprobanteElectronicoTO.setNombreEmisor(sisEmpresaParametros.getEmpCodigo().getEmpNombre());
        }
        SisEmpresaNotificaciones idNotificacion = sisEmpresaNotificacionesDao.obtenerPorId(SisEmpresaNotificaciones.class, idNotificacionEntero);
        if (idNotificacion != null && idNotificacion.getIdVerificado()) {
            sisEmailComprobanteElectronicoTO.setMailEmisor(idNotificacion.getIdPrincipal());
            sisEmailComprobanteElectronicoTO.setClaveEmisor(idNotificacion.getIdNotificacionesEventos());
        } else {
            throw new GeneralException("Configuración de notificaciones electrónicas no establecida o no se encuentra verificada.");
        }
        sisEmailComprobanteElectronicoTO.setTelefonoEmisor(sisEmpresaParametros.getEmpCodigo().getEmpTelefono());
        sisEmailComprobanteElectronicoTO.setDireccionEmisor(sisEmpresaParametros.getEmpCodigo().getEmpDireccion());
        sisEmailComprobanteElectronicoTO.setUrlWebDocumentoElectronico(sisEmpresaParametros.getParWebDocumentosElectronicos());
        sisEmailComprobanteElectronicoTO.setNombreReceptor(invProveedor.getProvRazonSocial());
        sisEmailComprobanteElectronicoTO.setMailReceptor(invProveedor.getProvEmailOrdenCompra());

        //agregar registradores de OP
        InvPedidos pedido = obtenerPedido(invPedidosOrdenCompra.getInvPedidosOrdenCompraDetalleList());
        if (pedido != null) {
            InvPedidosMotivo motivo = pedido.getInvPedidosMotivo();
            if (motivo != null) {
                List<InvPedidosMotivoDetalleRegistradores> registradores = pedidosMotivoDetalleRegistradoresDao.getListaInvPedidosMotivoDetalleRegistradores(motivo.getInvPedidosMotivoPK());
                if (registradores != null) {
                    for (InvPedidosMotivoDetalleRegistradores registrador : registradores) {
                        SisUsuario usuario = registrador.getSisUsuario();
                        if (usuario != null && usuario.getUsrEmail() != null && !usuario.getUsrEmail().equals("")) {
                            sisEmailComprobanteElectronicoTO.setMailReceptor(sisEmailComprobanteElectronicoTO.getMailReceptor() + ";" + usuario.getUsrEmail());
                        }
                    }
                }
            }
        }
        //Datos para AWS
        sisEmailComprobanteElectronicoTO.setTipoComprobante(TipoNotificacion.NOTIFICAR_PROVEEDOR_ORDEN_COMPRA.getNombre());
        sisEmailComprobanteElectronicoTO.setEmpresa(invPedidosOrdenCompraPK.getOcEmpresa());
        sisEmailComprobanteElectronicoTO.setMotivo(invPedidosOrdenCompraPK.getOcMotivo());
        sisEmailComprobanteElectronicoTO.setNumero(invPedidosOrdenCompraPK.getOcNumero());
        sisEmailComprobanteElectronicoTO.setPeriodo(invPedidosOrdenCompraPK.getOcSector());

        if (sisEmailComprobanteElectronicoTO.getMailEmisor() == null) {
            throw new GeneralException("Correo del emisor no registrado.");
        } else if (sisEmailComprobanteElectronicoTO.getMailReceptor() == null || sisEmailComprobanteElectronicoTO.getMailReceptor().compareTo("") == 0) {
            throw new GeneralException("Correo del receptor no registrado.");
        } else {
            if (usuarioEmpresaReporteTO.getEmpRuc().equals("0993063436001")) {
                switch (invPedidosOrdenCompra.getInvPedidosOrdenCompraMotivo().getInvPedidosOrdenCompraMotivoPK().getOcmSector()) {
                    case "AM":
                    case "CO":
                    case "MED-AM":
                    case "MED-CO":
                    case "MED-PS":
                    case "PS":
                        sisEmailComprobanteElectronicoTO.setNombreEmisor("INTEDECAM SA");
                        sisEmailComprobanteElectronicoTO.setRucEmisor("0993063436001");
                        sisEmailComprobanteElectronicoTO.setDireccionEmisor("Av. Las Americas 510 Bloque B");
                        break;
                    case "ASOCAM":
                        sisEmailComprobanteElectronicoTO.setNombreEmisor("ASOCIACION INTEDECAM CAMPONIO");
                        sisEmailComprobanteElectronicoTO.setRucEmisor("0993077054001");
                        sisEmailComprobanteElectronicoTO.setDireccionEmisor("Av. Las Americas 510 Bloque B");
                        break;
                    case "ASOIPS":
                        sisEmailComprobanteElectronicoTO.setNombreEmisor("INTEDECAM ISLA PALO SANTO");
                        sisEmailComprobanteElectronicoTO.setRucEmisor("0993076481001");
                        sisEmailComprobanteElectronicoTO.setDireccionEmisor("Av. Las Americas 510 Bloque B");
                        break;
                }
            }
            File filePDF = reportePedidosService.generarFileInvPedidosOrdenCompra(usuarioEmpresaReporteTO, invPedidosOrdenCompra, nombreReporte);
            if (filePDF == null) {
                throw new GeneralException("Ocurrió un error al generar el documento a enviar.");
            }
            List<File> listAdjunto = new ArrayList<>();
            File file = genericReporteService.respondeServidorCorreo(filePDF, "OrdenCompra_N_" + invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcNumero());
            if (file == null) {
                throw new GeneralException("Ocurrió un error al generar el archivo de orden compra número: " + invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcNumero());
            }
            listAdjunto.add(file);
            sisEmailComprobanteElectronicoTO.setNumeroComprobante(invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcNumero());
            return envioCorreoService.enviarPDFOrdenCompra(sisEmailComprobanteElectronicoTO, listAdjunto);
        }
    }

    @Override
    public boolean anularInvPedidosOrdenCompra(InvPedidosOrdenCompraPK invPedidosOrdenCompraPK, String motivoAnulacion, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        InvPedidosOrdenCompra invPedidosOrdenCompra = this.pedidosOrdenCompraDao.obtener(InvPedidosOrdenCompra.class, invPedidosOrdenCompraPK);

        if (invPedidosOrdenCompra.isOcPendiente()) {
            invPedidosOrdenCompra.setOcPendiente(false);
            pedidosOrdenCompraDao.actualizar(invPedidosOrdenCompra);
        }
        if (invPedidosOrdenCompra != null) {
            susClave = invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcEmpresa() + "|" + invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcSector() + "|" + invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcMotivo() + "|" + invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcNumero();
            susDetalle = "Se anuló la orden de compra con código " + susClave;
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_pedidos_orden_compra";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            invPedidosOrdenCompra.setOcAnulado(true);
            invPedidosOrdenCompra.setOcMotivoAnulacion(motivoAnulacion);
            pedidosOrdenCompraDao.actualizar(invPedidosOrdenCompra);
            sucesoDao.insertar(sisSuceso);
            return true;
        } else {
            throw new GeneralException("La orden de compra ya no esta disponible.");
        }
    }

    @Override
    public boolean aprobarInvPedidosOrdenCompra(InvPedidosOrdenCompraPK invPedidosOrdenCompraPK, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        InvPedidosOrdenCompra invPedidosOrdenCompra = this.pedidosOrdenCompraDao.obtener(InvPedidosOrdenCompra.class, invPedidosOrdenCompraPK);
        Date fechaActual = sistemaWebServicio.getFechaActual();
        //verificar si usuario puede aprobar monto limite segun configuracion
        InvOrdenCompraMotivoDetalleAprobadoresTO aprobadorEncontrado = null;
        boolean continuar = true;
        String mensajeError = "";
        InvPedidosOrdenCompraMotivo motivo = invPedidosOrdenCompra.getInvPedidosOrdenCompraMotivo();
        if (!motivo.getOcmAprobacionAutomatica()) {
            List<InvOrdenCompraMotivoDetalleAprobadoresTO> aprobadores = ordenCompraConfiguracionService.getListaInvOrdenCompraMotivoDetalleAprobadoresTO(motivo.getInvPedidosOrdenCompraMotivoPK(), null);
            if (aprobadores != null && aprobadores.size() > 0) {
                for (InvOrdenCompraMotivoDetalleAprobadoresTO aprobador : aprobadores) {
                    if (sisInfoTO.getUsuario().equals(aprobador.getUsuario().getUsrCodigo())) {
                        aprobadorEncontrado = aprobador;
                    }
                }
            }
        }

        if (aprobadorEncontrado != null && aprobadorEncontrado.getOcmTotalAprobar() != null && aprobadorEncontrado.getOcmTotalAprobar().compareTo(BigDecimal.ZERO) > 0) {
            if (invPedidosOrdenCompra.getOcMontoTotal().compareTo(aprobadorEncontrado.getOcmTotalAprobar()) < 0) {
                continuar = false;
                mensajeError = "El usuario " + aprobadorEncontrado.getUsuario().getUsrCodigo() + " no puede aprobar una orden de compra con monto mayor a " + aprobadorEncontrado.getOcmTotalAprobar();
            }
        }

        if (continuar) {
            if (invPedidosOrdenCompra != null) {
                susClave = invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcEmpresa() + "|" + invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcSector() + "|" + invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcMotivo() + "|" + invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcNumero();
                susDetalle = "Se aprobó la orden de compra con código " + susClave;
                susSuceso = "UPDATE";
                susTabla = "inventario.inv_pedidos_orden_compra";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                invPedidosOrdenCompra.setOcAprobada(true);
                invPedidosOrdenCompra.setUsrAprueba(sisInfoTO.getUsuario());
                invPedidosOrdenCompra.setOcFechaHoraAprobado(fechaActual);
                pedidosOrdenCompraDao.actualizar(invPedidosOrdenCompra);
                sucesoDao.insertar(sisSuceso);
                return true;
            } else {
                throw new GeneralException("La orden de compra ya no esta disponible.");
            }
        } else {
            throw new GeneralException(mensajeError);
        }
    }

    @Override
    public boolean desaprobarInvPedidosOrdenCompra(InvPedidosOrdenCompraPK invPedidosOrdenCompraPK, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        InvPedidosOrdenCompra invPedidosOrdenCompra = this.pedidosOrdenCompraDao.obtener(InvPedidosOrdenCompra.class, invPedidosOrdenCompraPK);
        if (invPedidosOrdenCompra != null) {
            InvComprasPK pk = verificarSiOCEstaEnCompra(invPedidosOrdenCompra.getInvPedidosOrdenCompraPK());
            if (pk != null) {
                throw new GeneralException("No se puede desaprobar una orden de compra que ya ha sido importada en la Compra: "
                        + pk.getCompEmpresa() + "|" + pk.getCompMotivo() + "|" + pk.getCompPeriodo() + "|" + pk.getCompNumero() + ".");
            } else {
                susClave = invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcEmpresa() + "|" + invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcSector() + "|" + invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcMotivo() + "|" + invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcNumero();
                susDetalle = "Se desaprobó la orden de compra con código " + susClave;
                susSuceso = "UPDATE";
                susTabla = "inventario.inv_pedidos_orden_compra";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                invPedidosOrdenCompra.setOcAprobada(false);
                invPedidosOrdenCompra.setUsrAprueba(sisInfoTO.getUsuario());
                pedidosOrdenCompraDao.actualizar(invPedidosOrdenCompra);
                sucesoDao.insertar(sisSuceso);
                return true;
            }
        } else {
            throw new GeneralException("La orden de compra ya no esta disponible.");
        }
    }

    @Override
    public boolean cerrarInvPedidosOrdenCompra(InvPedidosOrdenCompraPK invPedidosOrdenCompraPK, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        InvPedidosOrdenCompra invPedidosOrdenCompra = this.pedidosOrdenCompraDao.obtener(InvPedidosOrdenCompra.class, invPedidosOrdenCompraPK);
        if (invPedidosOrdenCompra != null) {
            susClave = invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcEmpresa() + "|" + invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcSector() + "|" + invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcMotivo() + "|" + invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcNumero();
            susDetalle = "Se cerró la orden de compra con código " + susClave;
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_pedidos_orden_compra";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            invPedidosOrdenCompra.setOcCerrada(true);
            invPedidosOrdenCompra.setUsrAprueba(sisInfoTO.getUsuario());
            pedidosOrdenCompraDao.actualizar(invPedidosOrdenCompra);
            sucesoDao.insertar(sisSuceso);
            return true;
        } else {
            throw new GeneralException("La orden de compra ya no esta disponible.");
        }
    }

    @Override
    public Map<String, Object> desmayorizarInvPedidosOrdenCompra(InvPedidosOrdenCompraPK invPedidosOrdenCompraPK, String usuario, boolean validarEjecutores, String motivoDesmayorizar, SisInfoTO sisInfoTO) throws Exception {
        Map<String, Object> map = new HashMap<>();
        InvPedidosOrdenCompra invPedidosOrdenCompra = this.pedidosOrdenCompraDao.obtener(InvPedidosOrdenCompra.class, invPedidosOrdenCompraPK);
        if (invPedidosOrdenCompra != null) {
            boolean continuar = false;
            InvPedidos pedidos = obtenerPedido(invPedidosOrdenCompra.getInvPedidosOrdenCompraDetalleList());
            if (validarEjecutores) {
                InvPedidosMotivo motivoPedido = pedidos != null ? pedidos.getInvPedidosMotivo() : null;
                List<InvPedidosMotivoDetalleEjecutores> listInvPedidosMotivoDetalleEjecutores = pedidosMotivoDetalleEjecutoresDao.getListaInvPedidosMotivoDetalleEjecutores(motivoPedido.getInvPedidosMotivoPK());
                for (InvPedidosMotivoDetalleEjecutores ejecutor : listInvPedidosMotivoDetalleEjecutores) {
                    if (ejecutor.getSisUsuario().getUsrCodigo().equals(usuario)) {
                        continuar = true;
                        break;
                    }
                }
            } else {
                continuar = true;
            }

            if (continuar) {
                susClave = invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcEmpresa() + "|" + invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcSector() + "|" + invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcMotivo() + "|" + invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcNumero();
                susDetalle = "Se desmayorizó la orden de compra con código " + susClave;
                susSuceso = "UPDATE";
                susTabla = "inventario.inv_pedidos_orden_compra";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                invPedidosOrdenCompra.setOcPendiente(true);
                invPedidosOrdenCompra.setOcMotivoDesmayorizar(motivoDesmayorizar);
                pedidosOrdenCompraDao.actualizar(invPedidosOrdenCompra);
                sucesoDao.insertar(sisSuceso);
                map.put("accion", "Mayorizar");
                map.put("pmAprobacionAutomatica", pedidos != null ? pedidos.getInvPedidosMotivo().getPmAprobacionAutomatica() : null);
                map.put("invPedidosPK", pedidos != null ? pedidos.getInvPedidosPK() : null);
                map.put("invPedidosOrdenCompraPK", invPedidosOrdenCompraPK);
            } else {
                throw new GeneralException("El usuario no pertenece a la lista de EJECUTORES");
            }

            return map;
        } else {
            throw new GeneralException("La orden de compra ya no esta disponible.");
        }
    }

    @Override
    public Map<String, Object> obtenerParametrosParaMayorizar(InvPedidosOrdenCompraPK invPedidosOrdenCompraPK, SisInfoTO sisInfoTO) throws Exception {
        Map<String, Object> map = new HashMap<>();
        InvPedidosOrdenCompra invPedidosOrdenCompra = this.pedidosOrdenCompraDao.obtener(InvPedidosOrdenCompra.class, invPedidosOrdenCompraPK);
        if (invPedidosOrdenCompra != null) {
            InvPedidos pedidos = obtenerPedido(invPedidosOrdenCompra.getInvPedidosOrdenCompraDetalleList());
            map.put("pmAprobacionAutomatica", pedidos != null ? pedidos.getInvPedidosMotivo().getPmAprobacionAutomatica() : null);
            map.put("invPedidosPK", pedidos != null ? pedidos.getInvPedidosPK() : null);
            return map;
        } else {
            throw new GeneralException("No existe orden de compra.");
        }
    }

    @Override
    public Map<String, Object> obtenerDatosParaReporteEntrega(String empresa) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<PrdListaSectorTO> listaSectores = sectorService.getListaSectorTO(empresa, false);
        List<InvClienteGrupoEmpresarialTO> listaGrupoEmpresarial = invClienteGrupoEmpresarialService.getInvClienteGrupoEmpresarialTO(empresa, null);
        map.put("listaSectores", listaSectores);
        map.put("listaGrupoEmpresarial", listaGrupoEmpresarial);
        return map;
    }

    @Override
    public Map<String, Object> obtenerDatosParaReporteComprasImb(String empresa) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<InvComprasMotivoTO> listaMotivos = comprasMotivoService.getListaInvComprasMotivoTO(empresa, false);
        List<SisPeriodo> listaPeriodos = periodoService.getListaPeriodo(empresa);
        map.put("listaMotivos", listaMotivos);
        map.put("listaPeriodos", listaPeriodos);
        return map;
    }

    private boolean actualizarDetalleTomadosEnOrdenDeCompra(List<InvPedidosDetalle> listaInvPedidosDetalle, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        for (InvPedidosDetalle invPedidosDetalle : listaInvPedidosDetalle) {//Detalle a actualizar
            if (invPedidosDetalle.getDetCantidadAdquirida().compareTo(invPedidosDetalle.getDetCantidadAprobada()) == 0) {
                invPedidosDetalle.setDetCompletado(true);
            } else {
                invPedidosDetalle.setDetCompletado(false);
            }
        }
        if (!pedidosDetalleService.modificarListaInvPedidosDetalle(listaInvPedidosDetalle, sisInfoTO)) {
            throw new GeneralException("No se modificó el detalle de la orden de compra.", "Error al actualizar detalle");
        }
        return true;
    }

    private List<InvPedidosOrdenCompra> listarOrdenesDeCompraSegunSecuencialDePedido(Integer detSecuencial) {
        String query = "select oc.* from inventario.inv_pedidos_orden_compra_detalle ocd "
                + "inner join inventario.inv_pedidos_orden_compra oc on ocd.oc_empresa = oc.oc_empresa "
                + "and ocd.oc_sector = oc.oc_sector and ocd.oc_motivo = oc.oc_motivo and ocd.oc_numero = oc.oc_numero "
                + "where ocd.det_secuencial_pedido = " + detSecuencial + " and not oc.oc_anulado;";

        List<InvPedidosOrdenCompra> oc = genericSQLDao.obtenerPorSql(query, InvPedidosOrdenCompra.class);
        for (InvPedidosOrdenCompra ipoc : oc) {
            if (ipoc != null) {
                if (ipoc.getInvProveedor() != null) {
                    ipoc.getInvProveedor().setInvComprasList(null);
                }
                ipoc.getInvPedidosOrdenCompraDetalleList().forEach((invPedidosOrdenCompraDetalleList) -> {
                    invPedidosOrdenCompraDetalleList.setInvPedidosOrdenCompra(new InvPedidosOrdenCompra(invPedidosOrdenCompraDetalleList.getInvPedidosOrdenCompra().getInvPedidosOrdenCompraPK()));
                    InvPedidosDetalle ipd = invPedidosOrdenCompraDetalleList.getInvPedidosDetalle();
                    ipd.setInvPedidos(new InvPedidos(ipd.getInvPedidos().getInvPedidosPK()));
                });
            }
        }
        return oc;
    }

    @Override
    public boolean validarSiTieneOrdenesComprasPendientes(InvPedidosPK pk) {
        InvPedidos invPedidos = invPedidosService.obtenerInvPedidos(pk);
        List<InvPedidosDetalle> detallePedido = invPedidos.getInvPedidosDetalleList();
        boolean sinPendiente = true;

        for (int i = 0; i < detallePedido.size(); i++) {
            List<InvPedidosOrdenCompra> listaOrdenesCompra = listarOrdenesDeCompraSegunSecuencialDePedido(detallePedido.get(i).getDetSecuencial());

            for (int j = 0; j < listaOrdenesCompra.size(); j++) {
                if (listaOrdenesCompra.get(j).isOcPendiente()) {
                    sinPendiente = false;
                    break;
                }

                if (!sinPendiente) {
                    break;
                }
            }

        }
        return sinPendiente;
    }

    private InvPedidosOrdenCompra completarDatosDeCliente(InvPedidosPK invPedidosPK) {
        InvPedidosOrdenCompra oc = new InvPedidosOrdenCompra();
        InvPedidos pedido = invPedidoDao.obtener(InvPedidos.class, invPedidosPK);
        if (pedido != null) {
            oc.setOcFechaHoraEntrega(pedido.getPedFechaHoraEntrega());
            oc.setOcObservacionesRegistra(pedido.getPedObservacionesRegistra());
        }
        if (pedido != null && pedido.getInvCliente() != null) {
            InvCliente cliente = new InvCliente(pedido.getInvCliente().getInvClientePK());
            cliente.setCliRazonSocial(pedido.getInvCliente().getCliRazonSocial());
            cliente.setCliCupoCredito(pedido.getInvCliente().getCliCupoCredito());
            oc.setInvCliente(cliente);
            oc.setOcContactoNombre(pedido.getPedContactoNombre());
            oc.setOcContactoTelefono(pedido.getPedContactoTelefono());
            oc.setOcLugarEntrega(pedido.getPedLugarEntrega());
            oc.setOcObservacionesRegistra(pedido.getPedObservacionesRegistra());
        }
        return oc;
    }

    private String obtenerNumeroDeOrdenDeCompra(List<InvPedidosDetalle> listaInvPedidosDetalle) {
        if (listaInvPedidosDetalle != null) {
            InvPedidosDetalle detalle = listaInvPedidosDetalle.get(0);
            if (detalle != null) {
                InvPedidos pedido = detalle.getInvPedidos();
                if (pedido != null) {
                    return pedido.getPedOrdenCompra();
                }
            }
        }
        return "";
    }

    private String obtenerNumeroDeOrdenDeCompra(InvPedidosOrdenCompra invPedidosOrdenCompra) {
        if (invPedidosOrdenCompra != null) {
            List<InvPedidosOrdenCompraDetalle> detalles = invPedidosOrdenCompra.getInvPedidosOrdenCompraDetalleList();
            if (detalles != null) {
                InvPedidosOrdenCompraDetalle detalle = detalles.get(0);
                if (detalle != null) {
                    InvPedidosDetalle pedidoDetalle = detalle.getInvPedidosDetalle();
                    if (pedidoDetalle != null) {
                        InvPedidos pedido = pedidoDetalle.getInvPedidos();
                        if (pedido != null) {
                            return pedido.getPedOrdenCompra();
                        }
                    }
                }
            }
        }
        return "";
    }

    private InvPedidos obtenerPedido(List<InvPedidosOrdenCompraDetalle> invPedidosOrdenCompraDetalleList) {
        if (invPedidosOrdenCompraDetalleList != null && !invPedidosOrdenCompraDetalleList.isEmpty()) {
            InvPedidosOrdenCompraDetalle ocDetalle = invPedidosOrdenCompraDetalleList.get(0);
            if (ocDetalle != null) {
                InvPedidosDetalle pDetalle = ocDetalle.getInvPedidosDetalle();
                if (pDetalle != null) {
                    return pDetalle.getInvPedidos();
                }
            }
        }
        return null;
    }

    @Override
    public List<InvCompras> getOrdenComprasImportadasEnCompras(InvPedidosOrdenCompraPK pk) throws GeneralException, Exception {
        String query = "SELECT * FROM inventario.inv_compras n"
                + " WHERE n.oc_empresa = '" + pk.getOcEmpresa() + "' AND n.oc_sector = '" + pk.getOcSector() + "' AND n.oc_motivo = '" + pk.getOcMotivo() + "' AND n.oc_numero = '" + pk.getOcNumero() + "' AND n.comp_pendiente = false AND n.comp_anulado = false  order by n.comp_fecha;";
        List<InvCompras> listadoCompras = genericSQLDao.obtenerPorSql(query, InvCompras.class);

        if (listadoCompras.size() > 0) {
            for (InvCompras comp : listadoCompras) {
                comp.setInvComprasDetalleList(null);
                comp.setInvComprasMotivoAnulacionList(null);
                comp.setInvComprasRecepcionList(null);
            }
        }

        return listadoCompras;
    }

    @Override
    public InvPedidosOrdenCompra cambiarDatosProductoOC(InvPedidosOrdenCompraPK invPedidosOrdenCompraPK, Integer detSecuencial, InvProductoPK productoNuevoPk, BigDecimal productoNuevoPrecio, BigDecimal productoNuevoCantidad,
            SisInfoTO sisInfoTO) throws Exception {
        Map<String, Object> map = new HashMap<>();
        InvPedidosOrdenCompra ipoc = null;
        InvComprasPK pk = verificarSiOCEstaEnCompra(invPedidosOrdenCompraPK);
        if (pk != null) {
            throw new GeneralException("No se puede modificar una orden de compra que ya ha sido importada en la Compra: "
                    + pk.getCompEmpresa() + "|" + pk.getCompMotivo() + "|" + pk.getCompPeriodo() + "|" + pk.getCompNumero() + ".");
        } else {
            ipoc = pedidosOrdenCompraDao.obtener(InvPedidosOrdenCompra.class, invPedidosOrdenCompraPK);
            InvPedidosOrdenCompra ipocCopia = ipoc;
            if (ipoc.getOcAnulado()) {
                throw new GeneralException("No se puede modificar una orden de compra que se encuentra ANULADA");
            } else {
                //para poder modificar una oc primero se debe desaprobar
                /*
                if (ipoc.getOcAprobada()) {
                    ipoc.setOcAprobada(false);
                    ipoc.setUsrAprueba("");
                    pedidosOrdenCompraDao.actualizar(ipoc);
                }
                if (!ipoc.isOcPendiente()) {
                    //y luego ponerlo como pendiente
                    ipoc.setOcPendiente(true);
                    pedidosOrdenCompraDao.actualizar(ipoc);
                }
                 */

                InvPedidos pedidoACambiar = null;

                for (InvPedidosOrdenCompraDetalle detalle : ipoc.getInvPedidosOrdenCompraDetalleList()) {
                    if (detalle.getDetSecuencialOrdenCompra().equals(detSecuencial)) {

                        if (detalle.getInvPedidosDetalle() != null) {
                            if (detalle.getInvPedidosDetalle().getInvPedidos() != null && detalle.getInvPedidosDetalle().getInvPedidos().getPedAprobado()) {
                                if (productoNuevoPk.getProCodigoPrincipal().equals(detalle.getInvPedidosDetalle().getInvProducto().getInvProductoPK().getProCodigoPrincipal())
                                        && productoNuevoPk.getProEmpresa().equals(detalle.getInvPedidosDetalle().getInvProducto().getInvProductoPK().getProEmpresa())) {
                                    if (detalle.getInvPedidosDetalle().getDetCantidadAprobada().compareTo(productoNuevoCantidad) >= 0) {
                                        /*
                                        detalle.setDetCantidad(productoNuevoCantidad);
                                        detalle.setDetPrecioReal(productoNuevoPrecio);

                                        //actualizar oden de compra con estados anteriores y modificacion de valores
                                        ipoc.setOcAprobada(ipocCopia.getOcAprobada());
                                        ipoc.setOcPendiente(ipocCopia.isOcPendiente());
                                        ipoc.setUsrAprueba(ipocCopia.getUsrAprueba());
                                        pedidosOrdenCompraDao.actualizar(ipoc);
                                        
                                         */
                                    } else {
                                        throw new GeneralException("La cantidad que desea modificar debe ser menor o igual :" + detalle.getInvPedidosDetalle().getDetCantidadAprobada());
                                    }
                                } else {
                                    //Verificar que todas las ordenes de compra no estan importadas
                                    List<InvPedidosOrdenCompra> listaOC = obtenerListaOCSegunPedido(detalle.getInvPedidosDetalle().getInvPedidos().getInvPedidosPK());
                                    if (listaOC != null && listaOC.size() > 0) {
                                        for (InvPedidosOrdenCompra oc : listaOC) {
                                            InvComprasPK pk2 = verificarSiOCEstaEnCompra(oc.getInvPedidosOrdenCompraPK());
                                            if (pk2 != null) {
                                                throw new GeneralException("La orden de compra: "
                                                        + oc.getInvPedidosOrdenCompraPK().getOcMotivo() + "|"
                                                        + oc.getInvPedidosOrdenCompraPK().getOcSector() + "|"
                                                        + oc.getInvPedidosOrdenCompraPK().getOcNumero() + " se encuentra importada en la Compra: "
                                                        + pk2.getCompEmpresa() + "|" + pk2.getCompMotivo() + "|" + pk2.getCompPeriodo() + "|" + pk2.getCompNumero() + ".");
                                            }
                                        }
                                    }

                                    if (detalle.getInvPedidosDetalle().getDetCantidadAprobada().compareTo(productoNuevoCantidad) >= 0) {
                                        detalle.setDetCantidad(productoNuevoCantidad);
                                        detalle.setDetPrecioReal(productoNuevoPrecio);
                                        //remplazar producto en pedido
                                        InvProducto producto = new InvProducto();
                                        producto.setInvProductoPK(productoNuevoPk);
                                        detalle.getInvPedidosDetalle().setInvProducto(producto);
                                    } else {
                                        throw new GeneralException("La cantidad que desea modificar debe ser menor o igual :" + detalle.getInvPedidosDetalle().getDetCantidadAprobada());
                                    }
                                }

                                pedidoACambiar = detalle.getInvPedidosDetalle().getInvPedidos();
                            } else {
                                String pedidoString
                                        = detalle.getInvPedidosDetalle().getInvPedidos() != null ? ": " + detalle.getInvPedidosDetalle().getInvPedidos().getInvPedidosPK().getPedMotivo() + "|"
                                        + detalle.getInvPedidosDetalle().getInvPedidos().getInvPedidosPK().getPedSector() + "|"
                                        + detalle.getInvPedidosDetalle().getInvPedidos().getInvPedidosPK().getPedNumero() : "";

                                throw new GeneralException("La orden de pedido relacionada a la orden de compra no se encuentra APROBADA" + pedidoString);
                            }
                        }
                        break;
                    }
                }

                if (pedidoACambiar != null) {
                    InvPedidos invPedidos = invPedidoDao.actualizar(pedidoACambiar);
                    //actualizar oden de compra con estados anteriores y modificacion de valores
                    ipoc.setOcAprobada(ipocCopia.getOcAprobada());
                    ipoc.setOcPendiente(ipocCopia.isOcPendiente());
                    ipoc.setUsrAprueba(ipocCopia.getUsrAprueba());
                    pedidosOrdenCompraDao.actualizar(ipoc);
                    map.put("mensaje", "TSe modificó los datos de orden de compra");
                    map.put("ordenCompra", ipoc);
                } else {
                    map.put("mensaje", "FNo se encontró pedido a modificar");
                }
            }
        }

        return ipoc;
    }

}
