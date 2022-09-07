package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteContratosTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteRecurrenteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteSinMovimientoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListadoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientePK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvNumeracionVarios;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ClienteDao extends GenericDao<InvCliente, InvClientePK> {

    public InvCliente buscarInvCliente(String empresa, String codigoCliente) throws Exception;

    public boolean eliminarInvCliente(InvCliente invCliente, SisSuceso sisSuceso) throws Exception;

    public boolean insertarInvCliente(InvCliente invCliente, SisSuceso sisSuceso,
            InvNumeracionVarios invNumeracionVarios) throws Exception;

    public boolean modificarInvCliente(InvCliente invCliente, SisSuceso sisSuceso) throws Exception;

    public boolean modificarInvClienteLlavePrincipal(InvCliente invClienteEliminar, InvCliente invCliente,
            SisSuceso sisSuceso) throws Exception;

    public String getInvProximaNumeracionCliente(String empresa, InvClienteTO invClienteTO) throws Exception;

    public Boolean buscarConteoCliente(String empCodigo, String codigoCliente) throws Exception;

    public boolean getClienteRepetido(String empresa, String codigo, String id, String nombre, String razonSocial)
            throws Exception;

    public InvClienteTO getInvClienteTOByRazonSocial(String empresa, String razonSocial) throws Exception;

    public InvClienteTO obtenerClienteTOPorCedulaRuc(String empresa, String cedulaRuc) throws Exception;

    public List<InvClienteTO> getClienteTO(String empresa, String codigo) throws Exception;

    public List<InvListaClienteTO> getListaClienteTO(String empresa, String busqueda, boolean incluirClienteInactivo)
            throws Exception;

    public List<InvFunListadoClientesTO> getInvFunListadoClientesTO(String empresa, String categoria) throws Exception;

    public InvCliente obtenerInvClientePorCedulaRuc(String empresa, String cedulaRuc);

    public InvClienteTO obtenerClienteTO(String empresa, String codigo) throws Exception;

    public List<InvFunListadoClientesTO> getInvFunListadoClientesTO(String empresa, boolean mostrarInactivo, String categoria) throws Exception;

    public List<InvFunListadoClientesTO> getInvFunListadoClientesTO(String empresa, boolean mostrarInactivo, String categoria, String busqueda, String grupoEmpresarial) throws Exception;

    public List<InvFunListadoClientesTO> getInvFunListadoClientesTOSinVentaRecurrente(String empresa, boolean mostrarInactivo, String categoria, String busqueda, String grupoEmpresarial) throws Exception;

    public List<InvClienteRecurrenteTO> listarClientesParaVentarecurrente(String empresa, String fecha) throws Exception;

    public boolean validarClienteParaVentarecurrente(String empresa, String fecha, String cliCodigo, int grupo) throws Exception;

    public List<InvClienteSinMovimientoTO> obtenerClientesSinMovimientos(String empresa) throws Exception;

    public InvCliente obtenerInvClientePorCedulaRuc(String empresa, String identificacion, char tipo);

    public InvCliente obtenerCliente(String empresa, String codigo) throws Exception;

    public List<InvClienteContratosTO> listarReporteComprativoContratos(String empresa, boolean mostrarInactivo, String categoria, String busqueda, boolean diferencia) throws Exception;

    public boolean actualizarGrupoEnCliente(String empresa, String codigoCliente, String codigoGrupo, SisSuceso sisSuceso) throws Exception;
}
