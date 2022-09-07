package ec.com.todocompu.ShrimpSoftServer.pedidos.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosPK;
import ec.com.todocompu.ShrimpSoftUtils.pedidos.TO.InvPedidoTO;
import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import java.util.Map;

@Transactional
public interface InvPedidosService {

    public InvPedidos insertarInvPedidos(InvPedidos invPedidos, SisInfoTO sisInfoTO) throws GeneralException, Exception;

    public InvPedidoTO obtenerInvPedidosTO(InvPedidosPK invPedidosPK) throws GeneralException, Exception;

    public InvPedidos modificarInvPedidos(InvPedidos invPedidos, String accion, SisInfoTO sisInfoTO) throws GeneralException;

    public InvPedidos desaprobarInvPedidos(InvPedidos invPedidos, SisInfoTO sisInfoTO) throws GeneralException;

    public String desmayorizarInvPedidos(InvPedidosPK invPedidosPK, SisInfoTO sisInfoTO) throws GeneralException;

    public String anularInvPedidos(InvPedidosPK invPedidosPK, String tipo, String motivoAnulacion, SisInfoTO sisInfoTO) throws GeneralException;

    public String restaturarInvPedidos(InvPedidosPK invPedidosPK, SisInfoTO sisInfoTO) throws GeneralException;

    public List<InvPedidoTO> getInvPedidos(String empresa, String busqueda, String motivo, Integer nroRegistros, String tipo, String fechaInicio, String fechaFin) throws GeneralException;

    public boolean eliminarInvPedidos(InvPedidosPK pk, SisInfoTO sisInfoTO) throws GeneralException;

    public InvPedidos obtenerInvPedidos(InvPedidosPK pk) throws GeneralException;

    public Map<String, Object> obtenerDatosParaGenerarOP(InvPedidosMotivoPK invPedidosMotivoPK, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> obtenerDatosParaPantallaOP(String empresa, SisInfoTO sisInfoTO) throws Exception;

    public List<InvPedidosDetalle> listarDetallesParaOrdenDecompra(InvPedidosPK invPedidosPK) throws Exception;

    public List<InvPedidoTO> getListaInvPedidosOrdenTO(String empresa, String motivo, String busqueda, String fechaInicio, String fechaFin, Boolean soloRegistrados, Boolean soloAprobados, Boolean soloEjecutados, String usuario) throws GeneralException, Exception;

}
