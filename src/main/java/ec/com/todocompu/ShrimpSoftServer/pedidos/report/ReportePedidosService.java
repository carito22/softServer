package ec.com.todocompu.ShrimpSoftServer.pedidos.report;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.ComprasVSOrdenesCompras;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosOrdenCompraDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosOrdenCompraMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosOrdenCompraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosReporteEntregaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.OrdenCompraVsCompraDolaresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompra;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosPK;
import ec.com.todocompu.ShrimpSoftUtils.pedidos.TO.InvPedidoTO;
import java.util.List;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.io.File;
import java.util.Map;

public interface ReportePedidosService {

    public byte[] generarReporteInvPedidosPorLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidosPK> listaInvPedidosPK, String nombreReporte) throws Exception;

    public Map<String, Object> exportarReporteInvPedidos(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, InvPedidos invPedidos, InvPedidoTO invPedidosTO) throws Exception;

    public byte[] generarReporteInvPedidosTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidoTO> listaInvPedidoTO, String codigoSector, String codigoMotivo, String nombreReporte) throws Exception;

    public byte[] generarReporteInvPedidosMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidosMotivoTO> listaInvPedidosMotivo, String nombreReporte) throws Exception;

    public byte[] generarReporteInvPedidosOrdenCompraMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidosOrdenCompraMotivoTO> listaInvPedidosOrdenCompraMotivoTO, String nombreReporte) throws Exception;

    public Map<String, Object> exportarReporteInvPedidosOrdenCompraMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidosOrdenCompraMotivoTO> listaInvPedidosOrdenCompraMotivoTO) throws Exception;

    public Map<String, Object> exportarReporteInvPedidosMotivo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidosMotivoTO> listado) throws Exception;

    public Map<String, Object> exportarReporteInvPedidoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidoTO> listado) throws Exception;
    
    public Map<String, Object> exportarReporteInvOrdenPedido(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidoTO> listado) throws Exception;

    public byte[] generarReporteInvPedidosOrdenCompraTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidosOrdenCompraTO> listaInvPedidosOrdenCompraTO, String nombreReporte) throws Exception;

    public Map<String, Object> exportarReporteInvPedidosOrdenCompraTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidosOrdenCompraTO> listado) throws Exception;

    public byte[] generarReporteInvPedidosOrdenCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, InvPedidosOrdenCompra invPedidosOrdenCompra, String nombreReporte) throws Exception;

    public byte[] generarReporteInvPedidosOrdenCompraPorLote(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvPedidosOrdenCompraDetalleTO> listaInvPedidosOrdenCompraDetalleTO, String nombreReporte) throws Exception;

    public File generarFileInvPedidosOrdenCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, InvPedidosOrdenCompra invPedidosOrdenCompra, String nombreReporte);

    public File generarFileInvPedidosOrdenCompraArpobadaADigitador(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, InvPedidosOrdenCompra invPedidosOrdenCompra, String nombreReporte);

    public Map<String, Object> exportarReporteInvPedidosOrdenCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, InvPedidosOrdenCompra invPedidosOrdenCompra) throws Exception;

    public byte[] generarReporteNotificacionesOrdenCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception;

    //Reporte general
    public byte[] generarReporteInvPedidosGeneral(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, InvPedidos invPedidos, InvPedidoTO invPedidosTO, String nombreReporte) throws Exception;

    public Map<String, Object> exportarReporteOrdenCompraVsOrdenesCompras(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<ComprasVSOrdenesCompras> comprasVSOrdenesCompras);

    public Map<String, Object> exportarReporteEntrega(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, String titulo, List<InvPedidosReporteEntregaTO> datos) throws Exception;

    public Map<String, Object> exportarOrdenCompraVsCompraDolares(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<OrdenCompraVsCompraDolaresTO> comprasVSOrdenesCompras) throws Exception;

}
