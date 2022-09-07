package ec.com.todocompu.ShrimpSoftServer.pedidos.report;

import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.pedidos.service.InvPedidosService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.EmpresaParametrosDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.ComprasVSOrdenesCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosOrdenCompraDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosOrdenCompraMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosOrdenCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosReporteEntregaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.OrdenCompraVsCompraDolaresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompra;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProducto;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoCategoria;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoEtiquetas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionCajas;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftUtils.pedidos.TO.InvPedidoTO;
import ec.com.todocompu.ShrimpSoftUtils.pedidos.report.ReporteNotificacionesOrdenCompra;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSectorPK;
import ec.com.todocompu.ShrimpSoftUtils.service.GenericReporteService;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuario;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

@Service
public class ReportePedidosServiceImpl implements ReportePedidosService {

    @Autowired
    private GenericReporteService genericReporteService;
    @Autowired
    private InvPedidosService invPedidosService;
    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;
    @Autowired
    private GenericoDao<SisUsuario, String> usuarioDao;
    @Autowired
    private GenericoDao<InvProductoEtiquetas, String> etiquetasDao;
    @Autowired
    private GenericoDao<PrdSector, PrdSectorPK> sectorDao;
    private String modulo = "pedidos";

    @Override
    public byte[] generarReporteInvPedidosMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidosMotivoTO> listaInvPedidosMotivo, String nombreReporte) throws Exception {
        List<ReporteInvPedidosMotivo> listadoReporte = new ArrayList<>();
        for (InvPedidosMotivoTO invPedidosMotivo : listaInvPedidosMotivo) {
            ReporteInvPedidosMotivo repo = new ReporteInvPedidosMotivo(
                    invPedidosMotivo.getPmCodigo(),
                    invPedidosMotivo.getPmDetalle(),
                    invPedidosMotivo.getPmHoraInicio(),
                    invPedidosMotivo.getPmHoraFin(),
                    invPedidosMotivo.isPmInactivo());
            listadoReporte.add(repo);
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listadoReporte);
    }

    @Override
    public byte[] generarReporteInvPedidosOrdenCompraMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidosOrdenCompraMotivoTO> listaInvPedidosOrdenCompraMotivoTO, String nombreReporte) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaInvPedidosOrdenCompraMotivoTO);
    }

    @Override
    public File generarFileInvPedidosOrdenCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, InvPedidosOrdenCompra invPedidosOrdenCompra, String nombreReporte) {
        List<ReporteInvPedidosOrdenCompra> listadoReporte = new ArrayList<>();
        InvProductoEtiquetas etiqueta = etiquetasDao.obtener(InvProductoEtiquetas.class, invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcEmpresa());
        for (InvPedidosOrdenCompraDetalle invPedidosOrdenCompraDetalle : invPedidosOrdenCompra.getInvPedidosOrdenCompraDetalleList()) {
            ReporteInvPedidosOrdenCompra repo = construirReporte(invPedidosOrdenCompra, invPedidosOrdenCompraDetalle);
            repo.setFechaElaboracion(invPedidosOrdenCompra.getUsrFechaInserta());
            repo.setFormaPago(obtenerFormaDePago(etiqueta, invPedidosOrdenCompra.getOcFormaPago()));
            listadoReporte.add(repo);
        }
        completarUsuarioReporteEspecialParaIntedecam(usuarioEmpresaReporteTO, invPedidosOrdenCompra);
        return genericReporteService.generarFile(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listadoReporte);
    }

    @Override
    public File generarFileInvPedidosOrdenCompraArpobadaADigitador(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, InvPedidosOrdenCompra invPedidosOrdenCompra, String nombreReporte) {
        List<ReporteInvPedidosOrdenCompra> listadoReporte = new ArrayList<>();
        List<InvPedidosOrdenCompraDetalleTO> listaInvPedidosOrdenCompraDetalleTO = new ArrayList<>();
        InvPedidosOrdenCompraDetalleTO invPedidosOrdenCompraDetalleTO = new InvPedidosOrdenCompraDetalleTO();
        invPedidosOrdenCompraDetalleTO.setInvPedidosOrdenCompra(invPedidosOrdenCompra);
        listaInvPedidosOrdenCompraDetalleTO.add(invPedidosOrdenCompraDetalleTO);

        if (listaInvPedidosOrdenCompraDetalleTO.size() > 0) {
            listadoReporte = obtenerListaReporteInvPedidosOrdenCompraPorLote(usuarioEmpresaReporteTO, listaInvPedidosOrdenCompraDetalleTO, nombreReporte);
        }
        completarUsuarioReporteEspecialParaIntedecam(usuarioEmpresaReporteTO, invPedidosOrdenCompra);
        return genericReporteService.generarFile(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listadoReporte);
    }

    private ReporteInvPedidosOrdenCompra construirReporte(InvPedidosOrdenCompra invPedidosOrdenCompra, InvPedidosOrdenCompraDetalle invPedidosOrdenCompraDetalle) {
        ReporteInvPedidosOrdenCompra repo = new ReporteInvPedidosOrdenCompra();
        repo.setFechaElaboracion(invPedidosOrdenCompra.getUsrFechaInserta());
        repo.setOcEmpresa(invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcEmpresa());
        repo.setOcNumero(invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcNumero());
        repo.setOcFecha(invPedidosOrdenCompra.getOcFechaEmision());
        repo.setOcmCodigo(invPedidosOrdenCompra.getInvPedidosOrdenCompraMotivo().getInvPedidosOrdenCompraMotivoPK().getOcmCodigo());
        repo.setOcmDetalle(invPedidosOrdenCompra.getInvPedidosOrdenCompraMotivo().getOcmDetalle());
        repo.setOcObservacionesRegistra(invPedidosOrdenCompra.getOcObservacionesRegistra());
        repo.setFormaPago(invPedidosOrdenCompra.getOcFormaPago());
        repo.setOcBase0(invPedidosOrdenCompra.getOcBase0());
        repo.setOcBaseImponible(invPedidosOrdenCompra.getOcBaseImponible());
        repo.setOcMontoIva(invPedidosOrdenCompra.getOcMontoIva());
        repo.setOcIvaVigente(invPedidosOrdenCompra.getOcIvaVigente());
        repo.setOcMontoTotal(invPedidosOrdenCompra.getOcMontoTotal());
        //orden de pedido
        repo.setOpEmpresa(invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvPedidos().getInvPedidosPK().getPedEmpresa());
        repo.setOpmCodigo(invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvPedidos().getInvPedidosPK().getPedMotivo());
        repo.setOpSector(invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvPedidos().getInvPedidosPK().getPedSector());
        repo.setOpNumero(invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvPedidos().getInvPedidosPK().getPedNumero());
        repo.setFechaPedido(invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvPedidos().getPedFechaEmision());
        repo.setOpUsrRegistra(invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvPedidos().getUsrRegistra());
        if (invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvPedidos().getUsrRegistra() != null && !invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvPedidos().getUsrRegistra().equals("")) {
            SisUsuario usuarioR = usuarioDao.obtener(SisUsuario.class, invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvPedidos().getUsrRegistra());
            repo.setOpUsrRegistra(usuarioR.getUsrNombre() + " " + usuarioR.getUsrApellido());
        }
        repo.setOpUsrAprueba(invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvPedidos().getUsrAprueba());
        if (invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvPedidos().getUsrAprueba() != null && !invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvPedidos().getUsrAprueba().equals("")) {
            SisUsuario usuarioA = usuarioDao.obtener(SisUsuario.class, invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvPedidos().getUsrAprueba());
            repo.setOpUsrAprueba(usuarioA.getUsrNombre() + " " + usuarioA.getUsrApellido());
        }
        repo.setOcObservacionesRegistraPedido(invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getDetObservacionesRegistra());
        //Sector
        repo.setOcSector(invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcSector());
        PrdSector sector = sectorDao.obtener(PrdSector.class, new PrdSectorPK(invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcEmpresa(), invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcSector()));
        if (sector != null) {
            repo.setOcSectorDetalle(sector.getSecNombre());
        }
        //Motivo
        repo.setOcMotivo(invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcMotivo());
        //Producto
        repo.setProCodigoPrincipal(invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvProducto().getInvProductoPK().getProCodigoPrincipal());
        repo.setProNombre(invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvProducto().getProNombre());
        repo.setProMedida(invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvProducto().getInvProductoMedida().getMedDetalle());
        repo.setProMedidaCodigo(invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvProducto().getInvProductoMedida().getInvProductoMedidaPK().getMedCodigo());
        repo.setProIva(invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvProducto().getProIva());
        //Proveedor
        repo.setProvCodigo(invPedidosOrdenCompra.getInvProveedor().getInvProveedorPK().getProvCodigo());
        repo.setProvRazonSocial(invPedidosOrdenCompra.getInvProveedor().getProvRazonSocial());
        repo.setProvDireccion(invPedidosOrdenCompra.getInvProveedor().getProvDireccion());
        repo.setProvCorreo(invPedidosOrdenCompra.getInvProveedor().getProvEmailOrdenCompra());
        repo.setProvRUC(invPedidosOrdenCompra.getInvProveedor().getProvIdNumero());
        repo.setProvTelefono(invPedidosOrdenCompra.getInvProveedor().getProvCelular());
        repo.setProvProvincia(invPedidosOrdenCompra.getInvProveedor().getProvProvincia());
        repo.setProvCiudad(invPedidosOrdenCompra.getInvProveedor().getProvCiudad());
        //Cantidades
        repo.setDetCantidadAdquirida(invPedidosOrdenCompraDetalle.getDetCantidad());
        repo.setRetencion(invPedidosOrdenCompra.getOcValorRetencion());
        repo.setPrecioReal(invPedidosOrdenCompraDetalle.getDetPrecioReal());
        repo.setTotal(invPedidosOrdenCompra.getOcMontoTotal());
        //contacto
        repo.setDocumentoCliente(invPedidosOrdenCompra.getInvCliente() != null ? invPedidosOrdenCompra.getInvCliente().getCliIdNumero() : "");
        repo.setNombreCliente(invPedidosOrdenCompra.getInvCliente() != null ? invPedidosOrdenCompra.getInvCliente().getCliRazonSocial() : "");
        repo.setFechahoraEntrega(invPedidosOrdenCompra.getOcFechaHoraEntrega());
        repo.setLugarEntrega(invPedidosOrdenCompra.getOcLugarEntrega() != null && !invPedidosOrdenCompra.getOcLugarEntrega().equals("") ? invPedidosOrdenCompra.getOcLugarEntrega() : "-");
        repo.setNombreContacto(invPedidosOrdenCompra.getOcContactoNombre() != null && !invPedidosOrdenCompra.getOcContactoNombre().equals("") ? invPedidosOrdenCompra.getOcContactoNombre() : "-");
        repo.setTelefonoContacto(invPedidosOrdenCompra.getOcContactoTelefono() != null && !invPedidosOrdenCompra.getOcContactoTelefono().equals("") ? invPedidosOrdenCompra.getOcContactoTelefono() : "-");
        repo.setUserFechaInserta(invPedidosOrdenCompra.getUsrFechaInserta());
        //Orden compra
        repo.setPedOrdenCompra(invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvPedidos().getPedOrdenCompra() != null ? invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvPedidos().getPedOrdenCompra() : "");
        repo.setFechahoraAprobado(invPedidosOrdenCompra.getOcFechaHoraAprobado());

        //Obsr de detalle
        repo.setDetObservaciones(invPedidosOrdenCompraDetalle.getDetObservaciones());
        //usuario modifica
        if (invPedidosOrdenCompra.getUsrCodigoModifica() != null && !invPedidosOrdenCompra.getUsrCodigoModifica().equals("")) {
            SisUsuario usuario = usuarioDao.obtener(SisUsuario.class, invPedidosOrdenCompra.getUsrCodigoModifica());
            repo.setOcUsrModifica(usuario.getUsrNombre() + " " + usuario.getUsrApellido());
        }
        repo.setOcUsrFechaModifica(invPedidosOrdenCompra.getUsrFechaModifica());

        //usuario
        if (invPedidosOrdenCompra.getUsrCodigo() != null && !invPedidosOrdenCompra.getUsrCodigo().equals("")) {
            SisUsuario usuario = usuarioDao.obtener(SisUsuario.class, invPedidosOrdenCompra.getUsrCodigo());
            repo.setElaboradoPor(usuario.getUsrNombre() + " " + usuario.getUsrApellido());
        }
        if (invPedidosOrdenCompra.getUsrCodigo() != null && !invPedidosOrdenCompra.getUsrCodigo().equals("") && invPedidosOrdenCompra.getUsrAprueba() == null || invPedidosOrdenCompra.getUsrAprueba().equals("")) {
            repo.setAprobadoPor("");
            return repo;
        }
        if (invPedidosOrdenCompra.getUsrCodigo() != null && !invPedidosOrdenCompra.getUsrCodigo().equals("") && invPedidosOrdenCompra.getUsrCodigo().equals(invPedidosOrdenCompra.getUsrAprueba())) {
            repo.setAprobadoPor(repo.getElaboradoPor());
        } else {
            if (invPedidosOrdenCompra.getUsrAprueba() != null) {
                SisUsuario usuario = usuarioDao.obtener(SisUsuario.class, invPedidosOrdenCompra.getUsrAprueba());
                repo.setAprobadoPor(usuario.getUsrNombre() + " " + usuario.getUsrApellido());
            }
        }
        return repo;
    }

    private ReporteInvPedidos contruirReporteInvPedidos(InvPedidos invPedidos, InvPedidoTO invPedidosTO, InvPedidosDetalle invPedidosDetalle) {
        String horaEntrega = "";
        String fechaEntrega = "";
        ReporteInvPedidos repo = new ReporteInvPedidos();
        repo.setFechaElaboracion(invPedidos.getUsrFechaInserta());
        if (invPedidos.getPedFechaHoraEntrega() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            horaEntrega = sdf.format(invPedidos.getPedFechaHoraEntrega());
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            fechaEntrega = dateFormat.format(invPedidos.getPedFechaHoraEntrega());
        }
        repo.setPedEmpresa(invPedidosTO.getCodigoempresa());
        repo.setCliente(invPedidos.getInvCliente() != null ? invPedidos.getInvCliente().getCliNombreComercial() : "");
        repo.setPedNumero(invPedidosTO.getPedidonumero());
        //observaciones
        repo.setObservacionRegistrador(invPedidos.getPedObservacionesRegistra());
        repo.setObservacionAprobador(invPedidos.getPedObservacionesAprueba());
        repo.setObservacionEjecutor(invPedidos.getPedObservacionesEjecuta());
        //Usuarios
        repo.setRegistrador(invPedidosTO.getRegistrador());
        repo.setAprobador(invPedidosTO.getAprobador());
        repo.setEjecutor(invPedidosTO.getEjecutor());
        //Motivo
        repo.setPedMotivo(invPedidosTO.getCodigomotivo());
        repo.setPedMotivoDetalle(invPedidosTO.getDetallemotivo());
        //Sector
        repo.setPedSector(invPedidosTO.getCodigosector());
        repo.setPedSectorDetalle(invPedidosTO.getNombresector());
        //Fechas y horas
        repo.setPedFecha(invPedidosTO.getPedfecha());
        repo.setFechaDeEntrega(fechaEntrega);
        repo.setHoraDeEntrega(horaEntrega);
        repo.setFechaAprobada(invPedidos.getUsrFechaAprobada());
        //Estado
        repo.setEstado(invPedidosTO.getEstado());
        //Contacto
        repo.setNombreDeContacto(invPedidos.getPedContactoNombre() == null ? "" : invPedidos.getPedContactoNombre());
        repo.setLugarDeEntrega(invPedidos.getPedLugarEntrega() == null ? "" : invPedidos.getPedLugarEntrega());
        repo.setTelefonoDeContacto(invPedidos.getPedContactoTelefono() == null ? "" : invPedidos.getPedContactoTelefono());
        //Producto
        repo.setIcodigo(invPedidosDetalle.getInvProducto().getInvProductoPK().getProCodigoPrincipal());
        repo.setiDescripcion(invPedidosDetalle.getInvProducto().getProNombre());
        repo.setMedDescripcion(invPedidosDetalle.getInvProducto().getInvProductoMedida().getMedDetalle());
        repo.setProMedidaCodigo(invPedidosDetalle.getInvProducto().getInvProductoMedida().getInvProductoMedidaPK().getMedCodigo());
        repo.setCatDescripcion(invPedidosDetalle.getInvProducto().getInvProductoCategoria() != null ? invPedidosDetalle.getInvProducto().getInvProductoCategoria().getCatDetalle() : "");
        repo.setCatCodigo(invPedidosDetalle.getInvProducto().getInvProductoCategoria() != null ? invPedidosDetalle.getInvProducto().getInvProductoCategoria().getInvProductoCategoriaPK().getCatCodigo() : "");
        //Cantidades
        repo.setDetCantidadSolicitada(invPedidosDetalle.getDetCantidadSolicitada());
        repo.setDetCantidadAprobada(invPedidosDetalle.getDetCantidadAprobada());
        repo.setDetCantidadAdquirida(invPedidosDetalle.getDetCantidadAdquirida());
        //observaciones de tabla
        repo.setObservacionItemRegistrador(invPedidosDetalle.getDetObservacionesRegistra());
        repo.setObservacionItemAprobador(invPedidosDetalle.getDetObservacionesAprueba());
        repo.setObservacionItemEjecutor(invPedidosDetalle.getDetObservacionesEjecuta());
        //orden de compra
        repo.setPedOrdenCompra(invPedidos.getPedOrdenCompra() != null ? invPedidos.getPedOrdenCompra() : "");

        return repo;
    }

    //IMPRIMIR ORDEN DE PEDIDO
    @Override
    public byte[] generarReporteInvPedidosPorLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidosPK> listaInvPedidosPK, String nombreReporte) throws Exception {
        List<ReporteInvPedidos> listadoReporte = new ArrayList<>();
        for (InvPedidosPK invPedidosPK : listaInvPedidosPK) {
            InvPedidoTO invPedidosTO = invPedidosService.obtenerInvPedidosTO(invPedidosPK);
            InvPedidos invPedidos = invPedidosService.obtenerInvPedidos(invPedidosPK);
            Integer index = 1;
            for (InvPedidosDetalle invPedidosDetalle : invPedidos.getInvPedidosDetalleList()) {
                ReporteInvPedidos repo = contruirReporteInvPedidos(invPedidos, invPedidosTO, invPedidosDetalle);
                repo.setFechaElaboracion(invPedidos.getUsrFechaInserta());
                repo.setNroItem(index);
                listadoReporte.add(repo);
                index++;
            }
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listadoReporte);
    }

    @Override
    public byte[] generarReporteInvPedidosTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidoTO> listaInvPedidoTO, String codigoSector, String codigoMotivo, String nombreReporte) throws Exception {
        List<ReporteInvPedidosTO> listadoReporte = new ArrayList<>();
        for (InvPedidoTO invPedidoTO : listaInvPedidoTO) {
            InvPedidosPK invPedidosPK = new InvPedidosPK(invPedidoTO.getCodigoempresa(), invPedidoTO.getCodigosector(), invPedidoTO.getCodigomotivo(), invPedidoTO.getPedidonumero());
            InvPedidos invPedidos = invPedidosService.obtenerInvPedidos(invPedidosPK);
            ReporteInvPedidosTO repo = new ReporteInvPedidosTO();
            //cabecera
            repo.setCodigoEmpresa(invPedidoTO.getCodigoempresa());
            repo.setCodigoMotivo(codigoMotivo);
            repo.setCodigoSector(codigoSector);
            String detalleMotivoCabecera = codigoMotivo != null && !codigoMotivo.equals("") ? invPedidoTO.getDetallemotivo() : "";
            String detalleSectorCabecera = codigoSector != null && !codigoSector.equals("") ? invPedidoTO.getNombresector() : "";
            repo.setDescripcionMotivo(detalleMotivoCabecera);
            repo.setDescripcionSector(detalleSectorCabecera);
            //fecha elaboracion
            repo.setFechaElaboracion(invPedidos.getUsrFechaInserta());
            //fecha de entrega
            repo.setPedFechaEntrega(invPedidos.getPedFechaHoraEntrega());
            //detalle list
            repo.setPedCodigoMotivo(invPedidoTO.getCodigomotivo());
            repo.setPedCodigoSector(invPedidoTO.getCodigosector());
            repo.setPedSectorDescripcion(invPedidoTO.getNombresector());
            repo.setPedMotivoDescripcion(invPedidoTO.getDetallemotivo());
            repo.setPedAprobador(invPedidoTO.getAprobador());
            repo.setPedEjecutor(invPedidoTO.getEjecutor());
            repo.setPedRegistrador(invPedidoTO.getRegistrador());
            repo.setPedEstado(invPedidoTO.getEstado());
            repo.setPedFecha(invPedidoTO.getPedfecha());
            repo.setPedMontoTotal(invPedidoTO.getPedmontototal());
            repo.setPedNumero(invPedidoTO.getPedidonumero());
            listadoReporte.add(repo);
        }
        completarUsuarioReporteEspecialParaIntedecam(usuarioEmpresaReporteTO, codigoSector);
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listadoReporte);
    }

    @Override
    public byte[] generarReporteInvPedidosGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, InvPedidos invPedidos, InvPedidoTO invPedidosTO, String nombreReporte) throws Exception {
        List<ReporteInvPedidos> listadoReporte = new ArrayList<>();
        Integer index = 1;
        for (InvPedidosDetalle invPedidosDetalle : invPedidos.getInvPedidosDetalleList()) {
            ReporteInvPedidos repo = contruirReporteInvPedidos(invPedidos, invPedidosTO, invPedidosDetalle);
            repo.setFechaElaboracion(invPedidos.getUsrFechaInserta());
            //Agregar presentacion caja y factor de conversion para gagroup
            InvProducto p = invPedidosDetalle.getInvProducto();
            if (p != null) {
                InvProductoPresentacionCajas ppc = p.getInvProductoPresentacionCajas();
                if (ppc != null) {
                    repo.setPresentacionCaja(ppc.getPrescDetalle());
                }
                if (p.getProFactorCajaSacoBulto() != null && (p.getProFactorCajaSacoBulto().compareTo(BigDecimal.ZERO) == 1)) {
                    BigDecimal cantidadDivididaFactorConversion = BigDecimal.ZERO;
                    if (nombreReporte.equals("reportAprobarOrdenPedido.jrxml") || nombreReporte.equals("reportOrdenPedidoAprobado.jrxml")) {
                        cantidadDivididaFactorConversion = repo.getDetCantidadAprobada().divide(p.getProFactorCajaSacoBulto(), 2, RoundingMode.HALF_UP);
                    }
                    if (nombreReporte.equals("reportGenerarOrdenPedido.jrxml")) {
                        cantidadDivididaFactorConversion = repo.getDetCantidadSolicitada().divide(p.getProFactorCajaSacoBulto(), 2, RoundingMode.HALF_UP);
                    }
                    if (cantidadDivididaFactorConversion != null) {
                        repo.setCantidadDivididaFactorConversion(cantidadDivididaFactorConversion);
                    } else {
                        repo.setCantidadDivididaFactorConversion(BigDecimal.ZERO);
                    }
                }
                InvProductoCategoria categoria = p.getInvProductoCategoria();
                if (categoria != null) {
                    repo.setCatCodigo(categoria.getInvProductoCategoriaPK().getCatCodigo());
                    repo.setCatDescripcion(categoria.getCatDetalle());
                }
            }
            repo.setNroItem(index);
            listadoReporte.add(repo);
            index++;
        }
        completarUsuarioReporteEspecialParaIntedecam(usuarioEmpresaReporteTO, invPedidos.getInvPedidosPK().getPedSector());
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<>(), listadoReporte);
    }

    /*IMPRIMIR ORDENES COMPRA*/
    @Override
    public byte[] generarReporteInvPedidosOrdenCompraPorLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidosOrdenCompraDetalleTO> listaInvPedidosOrdenCompraDetalleTO, String nombreReporte) throws Exception {
        List<ReporteInvPedidosOrdenCompra> listadoReporte = obtenerListaReporteInvPedidosOrdenCompraPorLote(usuarioEmpresaReporteTO, listaInvPedidosOrdenCompraDetalleTO, nombreReporte);
        Map<String, Object> parametros = new HashMap<>();
        SisEmpresaParametros param = empresaParametrosDao.obtenerPorId(SisEmpresaParametros.class, usuarioEmpresaReporteTO.getEmpCodigo());
        if (param != null) {
            parametros.put("llevarContabilidad", param.getParObligadoLlevarContabilidad());
            parametros.put("contribuyenteEspecial", param.getParResolucionContribuyenteEspecial());
        }
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, parametros, listadoReporte);
    }

    public List<ReporteInvPedidosOrdenCompra> obtenerListaReporteInvPedidosOrdenCompraPorLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidosOrdenCompraDetalleTO> listaInvPedidosOrdenCompraDetalleTO, String nombreReporte) {
        List<ReporteInvPedidosOrdenCompra> listadoReporte = new ArrayList<>();
        InvProductoEtiquetas etiqueta = etiquetasDao.obtener(InvProductoEtiquetas.class, usuarioEmpresaReporteTO.getEmpCodigo());
        for (InvPedidosOrdenCompraDetalleTO invPedidosOrdenCompraDetalleTO : listaInvPedidosOrdenCompraDetalleTO) {
            InvPedidosOrdenCompra invPedidosOrdenCompra = invPedidosOrdenCompraDetalleTO.getInvPedidosOrdenCompra();
            Integer index = 1;
            for (InvPedidosOrdenCompraDetalle invPedidosOrdenCompraDetalle : invPedidosOrdenCompra.getInvPedidosOrdenCompraDetalleList()) {
                ReporteInvPedidosOrdenCompra repo = construirReporte(invPedidosOrdenCompra, invPedidosOrdenCompraDetalle);

                InvProducto p = invPedidosOrdenCompraDetalle.getInvPedidosDetalle() != null ? invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvProducto() : null;
                if (p != null) {
                    InvProductoPresentacionCajas ppc = p.getInvProductoPresentacionCajas();
                    if (ppc != null) {
                        repo.setPresentacionCaja(ppc.getPrescDetalle());
                    }
                    if (p.getProFactorCajaSacoBulto() != null && (p.getProFactorCajaSacoBulto().compareTo(BigDecimal.ZERO) == 1)) {
                        BigDecimal detalleSaco = repo.getDetCantidadAdquirida().multiply(p.getProFactorCajaSacoBulto());
                        BigDecimal cantidadDivididaFactorConversion = repo.getDetCantidadAdquirida().divide(p.getProFactorCajaSacoBulto(), 2, RoundingMode.HALF_UP);
                        if (cantidadDivididaFactorConversion != null) {
                            repo.setCantidadDivididaFactorConversion(cantidadDivididaFactorConversion);
                        } else {
                            repo.setCantidadDivididaFactorConversion(BigDecimal.ZERO);
                        }
                        detalleSaco = detalleSaco != null ? detalleSaco : BigDecimal.ZERO;
                        repo.setDetSacos(detalleSaco);
                    }
                }
                repo.setNroItem(index);
                repo.setFormaPago(obtenerFormaDePago(etiqueta, invPedidosOrdenCompra.getOcFormaPago()));
                listadoReporte.add(repo);
                index++;
            }
            completarUsuarioReporteEspecialParaIntedecam(usuarioEmpresaReporteTO, invPedidosOrdenCompra);
        }

        return listadoReporte;
    }

    @Override
    public byte[] generarReporteInvPedidosOrdenCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, InvPedidosOrdenCompra invPedidosOrdenCompra, String nombreReporte) {
        List<ReporteInvPedidosOrdenCompra> listadoReporte = new ArrayList<>();
        InvProductoEtiquetas etiqueta = etiquetasDao.obtener(InvProductoEtiquetas.class, usuarioEmpresaReporteTO.getEmpCodigo());
        Integer index = 1;
        for (InvPedidosOrdenCompraDetalle invPedidosOrdenCompraDetalle : invPedidosOrdenCompra.getInvPedidosOrdenCompraDetalleList()) {
            ReporteInvPedidosOrdenCompra repo = construirReporte(invPedidosOrdenCompra, invPedidosOrdenCompraDetalle);
            InvProducto p = invPedidosOrdenCompraDetalle.getInvPedidosDetalle() != null ? invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvProducto() : null;
            if(p != null){
                if(p.getProFactorCajaSacoBulto() != null){
                    BigDecimal detalleSaco = repo.getDetCantidadAdquirida().multiply(p.getProFactorCajaSacoBulto());
                    detalleSaco = detalleSaco != null ? detalleSaco : null;
                    repo.setDetSacos(detalleSaco);
                }
            }
            repo.setFechaElaboracion(invPedidosOrdenCompra.getUsrFechaInserta());
            repo.setNroItem(index);
            repo.setFormaPago(obtenerFormaDePago(etiqueta, invPedidosOrdenCompra.getOcFormaPago()));
            listadoReporte.add(repo);
            index++;
        }
        completarUsuarioReporteEspecialParaIntedecam(usuarioEmpresaReporteTO, invPedidosOrdenCompra);
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listadoReporte);
    }

    public void completarUsuarioReporteEspecialParaIntedecam(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, InvPedidosOrdenCompra invPedidosOrdenCompra) {
        if (usuarioEmpresaReporteTO.getEmpRuc().equals("0993063436001")) {
            switch (invPedidosOrdenCompra.getInvPedidosOrdenCompraMotivo().getInvPedidosOrdenCompraMotivoPK().getOcmSector()) {
                case "AM":
                case "CO":
                case "MED-AM":
                case "MED-CO":
                case "MED-PS":
                case "PS":
                    usuarioEmpresaReporteTO.setEmpNombre("INTEDECAM SA");
                    usuarioEmpresaReporteTO.setEmpRazonSocial("INTEDECAM SA");
                    usuarioEmpresaReporteTO.setEmpRuc("0993063436001");
                    usuarioEmpresaReporteTO.setEmpDireccion("Av. Las Americas 510 Bloque B");
                    break;
                case "ASOCAM":
                    usuarioEmpresaReporteTO.setEmpNombre("ASOCIACION INTEDECAM CAMPONIO");
                    usuarioEmpresaReporteTO.setEmpRazonSocial("ASOCIACION INTEDECAM CAMPONIO");
                    usuarioEmpresaReporteTO.setEmpRuc("0993077054001");
                    usuarioEmpresaReporteTO.setEmpDireccion("Av. Las Americas 510 Bloque B");
                    break;
                case "ASOIPS":
                    usuarioEmpresaReporteTO.setEmpNombre("INTEDECAM ISLA PALO SANTO");
                    usuarioEmpresaReporteTO.setEmpRazonSocial("INTEDECAM ISLA PALO SANTO");
                    usuarioEmpresaReporteTO.setEmpRuc("0993076481001");
                    usuarioEmpresaReporteTO.setEmpDireccion("Av. Las Americas 510 Bloque B");
                    break;
            }
        }
    }

    //reportesOrdenPedidos
    public void completarUsuarioReporteEspecialParaIntedecam(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String codSector) {
        if (usuarioEmpresaReporteTO.getEmpRuc().equals("0993063436001")) {
            switch (codSector) {
                case "AM":
                case "CO":
                case "MED-AM":
                case "MED-CO":
                case "MED-PS":
                case "PS":
                    usuarioEmpresaReporteTO.setEmpNombre("INTEDECAM SA");
                    usuarioEmpresaReporteTO.setEmpRazonSocial("INTEDECAM SA");
                    usuarioEmpresaReporteTO.setEmpRuc("0993063436001");
                    usuarioEmpresaReporteTO.setEmpDireccion("Av. Las Americas 510 Bloque B");
                    break;
                case "ASOCAM":
                    usuarioEmpresaReporteTO.setEmpNombre("ASOCIACION INTEDECAM CAMPONIO");
                    usuarioEmpresaReporteTO.setEmpRazonSocial("ASOCIACION INTEDECAM CAMPONIO");
                    usuarioEmpresaReporteTO.setEmpRuc("0993077054001");
                    usuarioEmpresaReporteTO.setEmpDireccion("Av. Las Americas 510 Bloque B");
                    break;
                case "ASOIPS":
                    usuarioEmpresaReporteTO.setEmpNombre("INTEDECAM ISLA PALO SANTO");
                    usuarioEmpresaReporteTO.setEmpRazonSocial("INTEDECAM ISLA PALO SANTO");
                    usuarioEmpresaReporteTO.setEmpRuc("0993076481001");
                    usuarioEmpresaReporteTO.setEmpDireccion("Av. Las Americas 510 Bloque B");
                    break;
            }
        }
    }

    String obtenerFormaDePago(InvProductoEtiquetas etiqueta, String costoReferencial) {
        if (etiqueta != null) {
            switch (costoReferencial) {
                case "proCostoReferencial1": {
                    return etiqueta.getECosto01();
                }
                case "proCostoReferencial2": {
                    return etiqueta.getECosto02();
                }
                case "proCostoReferencial3": {
                    return etiqueta.getECosto03();
                }
                case "proCostoReferencial4": {
                    return etiqueta.getECosto04();
                }
                case "proCostoReferencial5": {
                    return etiqueta.getECosto05();
                }
            }
        }
        return "";
    }

    @Override
    public byte[] generarReporteInvPedidosOrdenCompraTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidosOrdenCompraTO> listaInvPedidosOrdenCompraTO, String nombreReporte) throws Exception {
        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listaInvPedidosOrdenCompraTO);
    }

    @Override
    public byte[] generarReporteNotificacionesOrdenCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception {

        String nombreReporte = UtilsJSON.jsonToObjeto(String.class, map.get("nombreReporte"));
        InvPedidosOrdenCompraTO invPedidosOrdenCompraTO = UtilsJSON.jsonToObjeto(InvPedidosOrdenCompraTO.class, map.get("invPedidosOrdenCompraTO"));
        List<InvPedidosOrdenCompraNotificaciones> listaNotificaciones = UtilsJSON.jsonToList(InvPedidosOrdenCompraNotificaciones.class, map.get("listaNotificaciones"));

        List<ReporteNotificacionesOrdenCompra> listadoReporte = new ArrayList<>();
        BigDecimal totalDestinarios = new BigDecimal("0.00");
        BigDecimal totalEnviados = new BigDecimal("0.00");
        BigDecimal totalEntregados = new BigDecimal("0.00");
        BigDecimal totalLeidos = new BigDecimal("0.00");
        BigDecimal totalRebotados = new BigDecimal("0.00");

        for (InvPedidosOrdenCompraNotificaciones notificacion : listaNotificaciones) {
            ReporteNotificacionesOrdenCompra reporte = new ReporteNotificacionesOrdenCompra();
            reporte.setReportDocumentoEnviado("FACTURA DE VENTA ELECTRÓNICA");
            reporte.setReportNumeroDocumento("");
            reporte.setReportFechaDocumento(UtilsValidacion.fecha(invPedidosOrdenCompraTO.getOcFecha(), "yyyy-MM-dd"));
            reporte.setReportRazonSocialDestinatario(invPedidosOrdenCompraTO.getProvRazonSocial());
            //Detalle
            String enviado = notificacion.getOcnTipo().equalsIgnoreCase("Send") ? "Sí" : "No";
            String entregado = notificacion.getOcnTipo().equalsIgnoreCase("Delivery") ? "Sí" : "No";
            String leido = notificacion.getOcnTipo().equalsIgnoreCase("Open") ? "Sí" : "No";
            String rebotado = notificacion.getOcnTipo().equalsIgnoreCase("BouncePermanent") ? "Sí" : "No";
            String fecha = UtilsValidacion.fecha(notificacion.getOcnFecha(), "yyyy-MM-dd HH:mm:ss");

            reporte.setReportCorreoElectronico(notificacion.getOcnDestinatario());
            reporte.setReportEnviado(enviado);
            reporte.setReportFechaEnviado(enviado.equalsIgnoreCase("Sí") ? fecha : "");
            reporte.setReportEntregado(entregado);
            reporte.setReportFechaEntregado(entregado.equalsIgnoreCase("Sí") ? fecha : "");
            reporte.setReportLeido(leido);
            reporte.setReportFechaLeido(leido.equalsIgnoreCase("Sí") ? fecha : "");
            reporte.setReportRebotado(rebotado);
            reporte.setReportFechaRebotado(rebotado.equalsIgnoreCase("Sí") ? fecha : "");

            if (enviado.equalsIgnoreCase("Sí")) {
                totalEnviados = totalEnviados.add(BigDecimal.ONE);
            }
            if (entregado.equalsIgnoreCase("Sí")) {
                totalEntregados = totalEntregados.add(BigDecimal.ONE);
            }
            if (leido.equalsIgnoreCase("Sí")) {
                totalLeidos = totalLeidos.add(BigDecimal.ONE);
            }
            if (rebotado.equalsIgnoreCase("Sí")) {
                totalRebotados = totalRebotados.add(BigDecimal.ONE);
            }

            listadoReporte.add(reporte);
        }

        listadoReporte.get(0).setReportTotalDestinarios(totalDestinarios);
        listadoReporte.get(0).setReportTotalEnviados(totalEnviados);
        listadoReporte.get(0).setReportTotalEntregados(totalEntregados);
        listadoReporte.get(0).setReportTotalLeidos(totalLeidos);
        listadoReporte.get(0).setReportTotalRebotados(totalRebotados);

        return genericReporteService.generarReporte(modulo, nombreReporte, usuarioEmpresaReporteTO, new HashMap<String, Object>(), listadoReporte);
    }

    /*EXPORTAR*/
    @Override
    public Map<String, Object> exportarReporteInvPedidos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, InvPedidos invPedidos, InvPedidoTO invPedidosTO) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SHISTORIAL DE ORDEN DE PEDIDO");
            listaCabecera.add("S");
            listaCabecera.add("SNúmero:" + "¬" + "S" + invPedidos.getInvPedidosPK().getPedSector() + "|" + invPedidos.getInvPedidosPK().getPedMotivo() + "|" + invPedidos.getInvPedidosPK().getPedNumero() + "¬" + "SEstado:" + "¬" + "S" + invPedidosTO.getEstado());
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SDescripción" + "¬" + "SCantidad solicitada" + "¬" + "SCantidad aprobada" + "¬" + "SCantidad adquirida" + "¬" + "SPrecio" + "¬" + "SParcial");
            for (InvPedidosDetalle invPedidosDetalle : invPedidos.getInvPedidosDetalleList()) {
                BigDecimal precio = invPedidosDetalle.getDetCantidadAdquirida().multiply(invPedidosDetalle.getDetPrecioReal());
                listaCuerpo.add(
                        (invPedidosDetalle.getInvProducto().getInvProductoPK().getProCodigoPrincipal() == null ? "B" : "S" + invPedidosDetalle.getInvProducto().getInvProductoPK().getProCodigoPrincipal())
                        + "¬" + (invPedidosDetalle.getInvProducto().getProNombre() == null ? "B" : "S" + invPedidosDetalle.getInvProducto().getProNombre())
                        + "¬" + (invPedidosDetalle.getDetCantidadSolicitada() == null ? "B" : "D" + invPedidosDetalle.getDetCantidadSolicitada().toString())
                        + "¬" + (invPedidosDetalle.getDetCantidadAprobada() == null ? "B" : "D" + invPedidosDetalle.getDetCantidadAprobada().toString())
                        + "¬" + (invPedidosDetalle.getDetCantidadAdquirida() == null ? "B" : "D" + invPedidosDetalle.getDetCantidadAdquirida().toString())
                        + "¬" + (invPedidosDetalle.getDetPrecioReal() == null ? "B" : "D" + invPedidosDetalle.getDetPrecioReal().toString())
                        + "¬" + "D" + precio.toString()
                );
            }
            listaCuerpo.add("S" + "¬" + "S" + "¬" + "S" + "¬" + "S" + "¬" + "S" + "¬" + "STOTAL" + "¬" + "D" + invPedidos.getPedMontoTotal().toString());
            listaCuerpo.add("SElaborado por:" + "¬" + "S" + invPedidosTO.getRegistrador());
            listaCuerpo.add("SAprobado por:" + "¬" + "S" + invPedidosTO.getAprobador());
            listaCuerpo.add("SEjecutado por:" + "¬" + "S" + invPedidosTO.getEjecutor());
            listaCuerpo.add("SFecha del pedido:" + "¬" + "S" + new SimpleDateFormat("dd-MM-yyyy").format(invPedidos.getPedFechaEmision()));
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteInvPedidosOrdenCompraMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidosOrdenCompraMotivoTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SMotivo de compra");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SDetalle" + "¬" + "Ssector" + "¬" + "SNotificar proveedor" + "¬" + "SCosto fijo");
            for (InvPedidosOrdenCompraMotivoTO invPedidosOrdenCompraMotivoTO : listado) {
                listaCuerpo.add(
                        (invPedidosOrdenCompraMotivoTO.getOcmCodigo() == null ? "B" : "S" + invPedidosOrdenCompraMotivoTO.getOcmCodigo())
                        + "¬" + (invPedidosOrdenCompraMotivoTO.getOcmDetalle() == null ? "B" : "S" + invPedidosOrdenCompraMotivoTO.getOcmDetalle())
                        + "¬" + (invPedidosOrdenCompraMotivoTO.getOcmSector() == null ? "B" : "S" + invPedidosOrdenCompraMotivoTO.getOcmSector())
                        + "¬" + (!invPedidosOrdenCompraMotivoTO.isOcmNotificarProveedor() ? "S" + "NO" : "S" + "SI")
                        + "¬" + (!invPedidosOrdenCompraMotivoTO.getOcmCostoFijo() ? "S" + "NO" : "S" + "SI")
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
    public Map<String, Object> exportarReporteInvPedidosMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidosMotivoTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado de motivos de órdenes pedido");
            listaCabecera.add("S");
            listaCuerpo.add("SCódigo" + "¬" + "SDetalle" + "¬" + "SHora inicio" + "¬" + "SHora fin");
            for (InvPedidosMotivoTO invPedidosMotivoTO : listado) {
                listaCuerpo.add(
                        (invPedidosMotivoTO.getPmCodigo() == null ? "B" : "S" + invPedidosMotivoTO.getPmCodigo())
                        + "¬" + (invPedidosMotivoTO.getPmDetalle() == null ? "B" : "S" + invPedidosMotivoTO.getPmDetalle())
                        + "¬" + (invPedidosMotivoTO.getPmHoraInicio() == null ? "B" : "S" + new SimpleDateFormat("HH:mm").format(invPedidosMotivoTO.getPmHoraInicio()))
                        + "¬" + (invPedidosMotivoTO.getPmHoraFin() == null ? "B" : "S" + new SimpleDateFormat("HH:mm").format(invPedidosMotivoTO.getPmHoraFin()))
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
    public Map<String, Object> exportarReporteInvOrdenPedido(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidoTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado de órdenes de pedido");
            listaCabecera.add("S");
            listaCuerpo.add("SEstado" + "¬" + "SSector" + "¬" + "SMotivo" + "¬" + "SNumero" + "¬" + "SFecha" + "¬" + "SRegistrador" + "¬" + "SAprobador");
            for (InvPedidoTO invPedidoTO : listado) {
                listaCuerpo.add(
                        (invPedidoTO.getEstado() == null ? "B" : "S" + invPedidoTO.getEstado())
                        + "¬" + (invPedidoTO.getCodigosector() == null ? "B" : "S" + invPedidoTO.getCodigosector())
                        + "¬" + (invPedidoTO.getCodigomotivo() == null ? "B" : "S" + invPedidoTO.getCodigomotivo())
                        + "¬" + (invPedidoTO.getPedidonumero() == null ? "B" : "S" + invPedidoTO.getPedidonumero())
                        + "¬" + (invPedidoTO.getPedfecha() == null ? "B" : "S" + new SimpleDateFormat("dd-MM-yyyy").format(invPedidoTO.getPedfecha()))
                        + "¬" + (invPedidoTO.getRegistrador() == null ? "B" : "S" + invPedidoTO.getRegistrador())
                        + "¬" + (invPedidoTO.getAprobador() == null ? "B" : "S" + invPedidoTO.getAprobador())
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
    public Map<String, Object> exportarReporteInvPedidoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidoTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado de órdenes pedido");
            listaCabecera.add("S");
            listaCuerpo.add("SEstado" + "¬" + "SSector" + "¬" + "SMotivo" + "¬" + "SNumero" + "¬" + "SFecha" + "¬" + "SRegistrador" + "¬" + "SObservaciones");
            for (InvPedidoTO invPedidoTO : listado) {
                listaCuerpo.add(
                        (invPedidoTO.getEstado() == null ? "B" : "S" + invPedidoTO.getEstado())
                        + "¬" + (invPedidoTO.getCodigosector() == null ? "B" : "S" + invPedidoTO.getCodigosector())
                        + "¬" + (invPedidoTO.getCodigomotivo() == null ? "B" : "S" + invPedidoTO.getCodigomotivo())
                        + "¬" + (invPedidoTO.getPedidonumero() == null ? "B" : "S" + invPedidoTO.getPedidonumero())
                        + "¬" + (invPedidoTO.getPedfecha() == null ? "B" : "S" + new SimpleDateFormat("dd-MM-yyyy").format(invPedidoTO.getPedfecha()))
                        + "¬" + (invPedidoTO.getRegistrador() == null ? "B" : "S" + invPedidoTO.getRegistrador())
                        + "¬" + (invPedidoTO.getObsregistra() == null ? "B" : "S" + invPedidoTO.getObsregistra())
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
    public Map<String, Object> exportarReporteInvPedidosOrdenCompraTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidosOrdenCompraTO> listado) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado de órdenes de compra");
            listaCabecera.add("S");
            listaCuerpo.add("SEstado" + "¬" + "SSector" + "¬" + "SMotivo" + "¬" + "SNúmero" + "¬" + "SFecha" + "¬" + "SAprobador" + "¬" + "SProveedor" + "¬" + "SOrden de pedido" + "¬" + "SBase 0" + "¬" + "SBase Imponible" + "¬" + "SMonto Iva" + "¬" + "SIva vigente" + "¬" + "SMonto total");
            for (InvPedidosOrdenCompraTO invPedidosOrdenCompraTO : listado) {
                String Estado = " ";
                if (invPedidosOrdenCompraTO.isOcmNotificarProveedor()) {
                    Estado = "Por Aprobar";
                }
                if (invPedidosOrdenCompraTO.isOcPendiente()) {
                    Estado = "Pendiente";
                }
                if (invPedidosOrdenCompraTO.isOcAprobada()) {
                    Estado = "Aprobado";
                }
                listaCuerpo.add(
                        (Estado == null ? "B" : "S" + Estado)
                        + "¬" + (invPedidosOrdenCompraTO.getOcSector() == null ? "B" : "S" + invPedidosOrdenCompraTO.getOcSector())
                        + "¬" + (invPedidosOrdenCompraTO.getOcMotivo() == null ? "B" : "S" + invPedidosOrdenCompraTO.getOcMotivo())
                        + "¬" + (invPedidosOrdenCompraTO.getOcNumero() == null ? "B" : "S" + invPedidosOrdenCompraTO.getOcNumero())
                        + "¬" + (invPedidosOrdenCompraTO.getOcFecha() == null ? "B" : "S" + new SimpleDateFormat("dd-MM-yyyy").format(invPedidosOrdenCompraTO.getOcFecha()))
                        + "¬" + (invPedidosOrdenCompraTO.getUsrAprueba() == null ? "B" : "S" + invPedidosOrdenCompraTO.getUsrAprueba())
                        + "¬" + (invPedidosOrdenCompraTO.getProvRazonSocial() == null ? "B" : "S" + invPedidosOrdenCompraTO.getProvRazonSocial())
                        + "¬" + (invPedidosOrdenCompraTO.getOrdenPedido() == null ? "B" : "S" + invPedidosOrdenCompraTO.getOrdenPedido())
                        + "¬" + (invPedidosOrdenCompraTO.getOcBase0() == null ? "B" : "D" + invPedidosOrdenCompraTO.getOcBase0().toString())
                        + "¬" + (invPedidosOrdenCompraTO.getOcBaseImponible() == null ? "B" : "D" + invPedidosOrdenCompraTO.getOcBaseImponible().toString())
                        + "¬" + (invPedidosOrdenCompraTO.getOcMontoIva() == null ? "B" : "D" + invPedidosOrdenCompraTO.getOcMontoIva().toString())
                        + "¬" + (invPedidosOrdenCompraTO.getOcIvaVigente() == null ? "B" : "D" + invPedidosOrdenCompraTO.getOcIvaVigente().toString())
                        + "¬" + (invPedidosOrdenCompraTO.getOcMontoTotal() == null ? "B" : "D" + invPedidosOrdenCompraTO.getOcMontoTotal().toString()));
            }
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);

            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Map<String, Object> exportarReporteOrdenCompraVsOrdenesCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ComprasVSOrdenesCompras> comprasVSOrdenesCompras) {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado de compras VS órdenes de compra");
            listaCabecera.add("S");
            listaCuerpo.add("SSector" + "¬" + "SMotivo" + "¬" + "SNúmero" + "¬" + "SFecha" + "¬" + "SFecha Entrega" + "¬" + "SProveedor" + "¬" + "SOrden de pedido" + "¬" + "STotal");
            for (ComprasVSOrdenesCompras objeto : comprasVSOrdenesCompras) {
                listaCuerpo.add(
                        (objeto.getOcSector() == null ? "B" : "S" + objeto.getOcSector())
                        + "¬" + (objeto.getOcMotivo() == null ? "B" : "S" + objeto.getOcMotivo())
                        + "¬" + (objeto.getOcNumero() == null ? "B" : "S" + objeto.getOcNumero())
                        + "¬" + (objeto.getOcFechaEmision() == null ? "B" : "S" + new SimpleDateFormat("dd-MM-yyyy").format(objeto.getOcFechaEmision()))
                        + "¬" + (objeto.getOcFechaHoraEntrega() == null ? "B" : "S" + new SimpleDateFormat("dd-MM-yyyy").format(objeto.getOcFechaHoraEntrega()))
                        + "¬" + (objeto.getProvRazonSocial() == null ? "B" : "S" + objeto.getProvRazonSocial())
                        + "¬" + (objeto.getPkPedido() == null ? "B" : "S" + objeto.getPkPedido())
                        + "¬" + (objeto.getOcMontoTotal() == null ? "B" : "D" + objeto.getOcMontoTotal())
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
    public Map<String, Object> exportarOrdenCompraVsCompraDolares(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<OrdenCompraVsCompraDolaresTO> comprasVSOrdenesCompras) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SListado de compras VS órdenes de compra doláres");
            listaCabecera.add("S");
            listaCuerpo.add("SSector" + "¬" + "SMotivo" + "¬" + "SNúmero" + "¬" + "SFecha" + "¬" + "SMonto orden compra" + "¬" + "SMonto total compra");
            for (OrdenCompraVsCompraDolaresTO objeto : comprasVSOrdenesCompras) {
                listaCuerpo.add(
                        (objeto.getOcSector() == null ? "B" : "S" + objeto.getOcSector())
                        + "¬" + (objeto.getOcMotivo() == null ? "B" : "S" + objeto.getOcMotivo())
                        + "¬" + (objeto.getOcNumero() == null ? "B" : "S" + objeto.getOcNumero())
                        + "¬" + (objeto.getOcFechaEmision() == null ? "B" : "S" + objeto.getOcFechaEmision())
                        + "¬" + (objeto.getOcMontoTotal() == null ? "B" : "D" + objeto.getOcMontoTotal())
                        + "¬" + (objeto.getCompTotal() == null ? "B" : "D" + objeto.getCompTotal())
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
    public Map<String, Object> exportarReporteEntrega(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String titulo, List<InvPedidosReporteEntregaTO> datos) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SReporte de entrega");
            listaCabecera.add("S");
            listaCuerpo.add("SOrden de compra" + "¬" + "SFecha Emision" + "¬" + "SFecha Entrega" + "¬" + "SOrden de compra de cliente" + "¬" + "SLugar de Entrega" + "¬"
                    + "SCliente" + "¬" + "SProveedor" + "¬" + "SGrupo empresarial" + "¬" + "SCodigo" + "¬" + "SDetalle de producto" + "¬" + "SCantidad del pedido"
                    + "¬" + "SEntregado" + "¬" + "SPor entregar" + "¬" + "SEntrega Realizada");
            for (InvPedidosReporteEntregaTO objeto : datos) {
                listaCuerpo.add(
                        (objeto.getEntOcNumero() == null ? "B" : "S" + objeto.getEntOcNumero())
                        + "¬" + (objeto.getEntFechaEmision() == null ? "B" : "T" + objeto.getEntFechaEmision())
                        + "¬" + (objeto.getEntFechaEntrega() == null ? "B" : "T" + objeto.getEntFechaEntrega())
                        + "¬" + (objeto.getEntPedOrdenCompra() == null ? "B" : "T" + objeto.getEntPedOrdenCompra())
                        + "¬" + (objeto.getEntOcLugarEntrega() == null ? "B" : "S" + objeto.getEntOcLugarEntrega())
                        + "¬" + (objeto.getEntCliente() == null ? "B" : "S" + objeto.getEntCliente())
                        + "¬" + (objeto.getEntProveedor() == null ? "B" : "S" + objeto.getEntProveedor())
                        + "¬" + (objeto.getEntGrupoEmpresarial() == null ? "B" : "S" + objeto.getEntGrupoEmpresarial())
                        + "¬" + (objeto.getEntProCodigoPrincipal() == null ? "B" : "S" + objeto.getEntProCodigoPrincipal())
                        + "¬" + (objeto.getEntProDetalle() == null ? "B" : "S" + objeto.getEntProDetalle())
                        + "¬" + (objeto.getEntDetCantidad() == null ? "B" : "D" + objeto.getEntDetCantidad())
                        + "¬" + (objeto.getEntDetEntregado() == null ? "B" : "D" + objeto.getEntDetEntregado())
                        + "¬" + (objeto.getEntDetPorEntregar() == null ? "B" : "D" + objeto.getEntDetPorEntregar())
                        + "¬" + (objeto.getEntEntregado() == null ? "B" : "S" + objeto.getEntEntregado())
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
    public Map<String, Object> exportarReporteInvPedidosOrdenCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, InvPedidosOrdenCompra invPedidosOrdenCompra) throws Exception {
        try {
            List<String> listaCabecera = new ArrayList();
            List<String> listaCuerpo = new ArrayList();
            Map<String, Object> respuesta = new HashMap<String, Object>();
            String nombreEmpresa = usuarioEmpresaReporteTO.getEmpNombre();
            listaCabecera.add("S" + nombreEmpresa);
            listaCabecera.add("SORDEN DE COMPRA");
            listaCabecera.add("S");
            listaCabecera.add("SNúmero: "
                    + "¬" + "S" + invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcSector() + "|" + invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcMotivo() + "|" + invPedidosOrdenCompra.getInvPedidosOrdenCompraPK().getOcNumero()
                    + "¬" + "SFecha: "
                    + "¬" + "S" + new SimpleDateFormat("dd/MM/yyyy").format(invPedidosOrdenCompra.getOcFechaEmision()));
            listaCabecera.add("SProveedor: " + "¬" + "S" + invPedidosOrdenCompra.getInvProveedor().getInvProveedorPK().getProvCodigo() + " - " + invPedidosOrdenCompra.getInvProveedor().getProvRazonSocial());
            listaCabecera.add("SMotivo: " + "S" + invPedidosOrdenCompra.getInvPedidosOrdenCompraMotivo().getInvPedidosOrdenCompraMotivoPK().getOcmCodigo() + " - " + invPedidosOrdenCompra.getInvPedidosOrdenCompraMotivo().getOcmDetalle());
            listaCabecera.add("S");
            listaCabecera.add("SSírvase despachar lo siguiente: ");
            listaCuerpo.add("SCódigo" + "¬" + "SDescripción" + "¬" + "SCantidad");
            for (InvPedidosOrdenCompraDetalle invPedidosOrdenCompraDetalle : invPedidosOrdenCompra.getInvPedidosOrdenCompraDetalleList()) {
                listaCuerpo.add(
                        (invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvProducto().getInvProductoPK().getProCodigoPrincipal() == null ? "B" : "S" + invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvProducto().getInvProductoPK().getProCodigoPrincipal())
                        + "¬" + (invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvProducto().getProNombre() == null ? "B" : "S" + invPedidosOrdenCompraDetalle.getInvPedidosDetalle().getInvProducto().getProNombre())
                        + "¬" + (invPedidosOrdenCompraDetalle.getDetCantidad() == null ? "B" : "D" + invPedidosOrdenCompraDetalle.getDetCantidad().toString())
                );
            }
            listaCuerpo.add("S");
            listaCuerpo.add("S");

            listaCuerpo.add("SObservaciones" + "¬" + "S" + invPedidosOrdenCompra.getOcObservacionesRegistra());
            respuesta.put("listaCabecera", listaCabecera);
            respuesta.put("listaCuerpo", listaCuerpo);
            return respuesta;
        } catch (Exception e) {
            throw e;
        }
    }

}
