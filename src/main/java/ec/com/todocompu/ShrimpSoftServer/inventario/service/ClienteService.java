package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteRecurrenteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteSinMovimientoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxProvinciaCantonTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteContratosTO;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClientesVentasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEntidadTransaccionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListadoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientePK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientesDirecciones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionInp;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface ClienteService {

    public List<InvListaClienteTO> getListaClienteTO(String empresa, String busqueda, boolean activo_Cliente) throws Exception;

    public boolean getClienteRepetidoCedulaRUC(String empresa, String id, char tipo) throws Exception;//C ,R o null

    public boolean getClienteRepetido(String empresa, String codigo, String id, String nombre, String razonSocial) throws Exception;

    public List<InvClienteTO> getClienteTO(String empresa, String codigo) throws Exception;

    public List<InvFunListadoClientesTO> getInvFunListadoClientesTO(String empresa, String categoria) throws Exception;

    public Boolean buscarConteoCliente(String empCodigo, String codigoCliente) throws Exception;

    public String insertarInvClienteTO(InvClienteTO invClienteTO, List<InvGuiaRemisionInp> listadoINP, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarInvClienteTO(InvClienteTO invClienteTO, SisInfoTO sisInfoTO) throws Exception;

    public String modificarInvClienteTO(InvClienteTO invClienteTO, String codigoAnterior, List<InvGuiaRemisionInp> listadoINP, SisInfoTO sisInfoTO) throws Exception;

    public boolean modificarClienteLugarEntrega(InvClientePK invClientePK, String cliLugaresEntrega, SisInfoTO sisInfoTO) throws Exception;

    public InvCliente obtenerInvClientePorCedulaRuc(String empresa, String cedulaRuc);

    public InvCliente obtenerInvClientePorCedulaRuc(String empresa, String identificacion, char tipo);

    public InvClienteTO obtenerClienteTO(String empresa, String codigo) throws Exception;

    public boolean eliminarInvCliente(InvClientePK pk, SisInfoTO sisInfoTO) throws Exception, GeneralException;

    public boolean modificarEstadoInvCliente(InvClientePK invClientePK, boolean estado, SisInfoTO sisInfoTO) throws GeneralException;

    public List<InvFunListadoClientesTO> getInvFunListadoClientesTO(String empresa, boolean mostrarInactivo, String categoria) throws Exception;

    public List<InvFunListadoClientesTO> getInvFunListadoClientesTO(String empresa, boolean mostrarInactivo, String categoria, String busqueda, String grupoEmpresarial) throws Exception;

    public List<InvFunListadoClientesTO> getInvFunListadoClientesTOSinVentaRecurrente(String empresa, boolean mostrarInactivo, String categoria, String busqueda, String grupoEmpresarial) throws Exception;

    public Map<String, Object> obtenerDatosBasicosCliente(Map<String, Object> parametros) throws Exception;

    public String modificarInvClienteTOVentaAutomatica(InvClienteTO invClienteTO, String codigoAnterior,
            List<InvClientesVentasDetalleTO> invClientesVentasDetalleTO, List<InvClientesDirecciones> listaDirecciones, List<InvGuiaRemisionInp> listadoINP, SisInfoTO sisInfoTO) throws Exception;

    public String insertarInvClienteTOVentaAutomatica(InvClienteTO invClienteTO, List<InvClientesVentasDetalleTO> invClientesVentasDetalleTO, List<InvClientesDirecciones> listaDirecciones, List<InvGuiaRemisionInp> listadoINP,
            SisInfoTO sisInfoTO) throws Exception;

    public List<InvClienteRecurrenteTO> listarClientesParaVentarecurrente(String empresa, String fecha) throws Exception;

    public List<InvClientesDirecciones> getListInvClientesDirecciones(String empresa, String cliCodigo) throws Exception;

    public InvEntidadTransaccionTO obtenerInvEntidadTransaccionTOCuentasPorCobrar(String empresa, String codigo, String documento) throws Exception;

    public InvEntidadTransaccionTO obtenerInvEntidadTransaccionTORolesPago(String empresa, String codigo, String documento) throws Exception;

    public boolean modificarClienteGrupoEmpresarial(InvClientePK invClientePK, String codigoGrupoEmpresarial, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> validarIdentificacionCliente(Map<String, Object> map) throws Exception;

    public boolean validarClienteParaVentarecurrente(String empresa, String fecha, String cliCodigo, int grupo) throws Exception;

    public List<InvClienteSinMovimientoTO> obtenerClientesSinMovimientos(String empresa) throws Exception;

    public Map<String, Object> obtenerDatosClienteSegunEmpresa(Map<String, Object> map) throws Exception;

    public InvClienteTO getInvClienteTOByRazonSocial(String empresa, String razonSocial) throws Exception;

    public InvCliente getBuscaCedulaCliente(String empresa, String identificacion, char tipo) throws Exception;

    public Map<String, Object> verificarExistenciaEnClientes(String empresa, List<InvClienteTO> clientes, List<InvClienteTO> clientesIncompletos) throws Exception;

    public Map<String, Object> insertarListadoExcelClientes(List<InvClienteTO> listaInvClienteTO, List<AnxProvinciaCantonTO> listaProvincias, SisInfoTO sisInfoTO) throws Exception;

    public InvCliente obtenerCliente(String empresa, String codigo) throws Exception;

    public boolean actualizarGrupoEnCliente(String empresa, String codigoCliente, String codigoGrupoEmp, SisInfoTO sisInfoTO) throws Exception;

    public List<InvClienteContratosTO> listarReporteComprativoContratos(String empresa, boolean mostrarInactivo, String categoria, String busqueda, boolean diferencia) throws Exception;
}
