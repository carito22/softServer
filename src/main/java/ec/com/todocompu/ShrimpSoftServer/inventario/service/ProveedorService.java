package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftUtils.DetalleExportarFiltrado;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxProvinciaCantonTO;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListadoProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaProveedoresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorSinMovimientoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProveedorTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedor;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProveedorTransportista;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.util.Map;

@Transactional
public interface ProveedorService {

    public InvProveedor buscarInvProveedor(String empresa, String provCodigo) throws Exception;

    public List<InvListaProveedoresTO> getListaProveedoresTO(String empresa, String busqueda, boolean activoProveedor) throws Exception;

    public List<InvProveedorTO> getProveedorTO(String empresa, String codigo) throws Exception;

    public InvProveedorTO getBuscaCedulaProveedorTO(String empresa, String cedRuc) throws Exception;

    public InvProveedorTO getBuscaCedulaProveedorTO(String empresa, String cedRuc, char tipo) throws Exception;

    public boolean getProveedorRepetidoCedulaRUC(String empresa, String id, char tipo) throws Exception;//C ,R o null

    public boolean comprobarInvAplicaRetencionIva(String empresa, String codigo) throws Exception;

    public List<InvFunListadoProveedoresTO> getInvFunListadoProveedoresTO(String empresa, String categoria) throws Exception;

    public Boolean buscarConteoProveedor(String empCodigo, String codigoProveedor) throws Exception;

    public String insertarInvProveedorTO(InvProveedorTO invProveedorTO, SisInfoTO sisInfoTO, List<InvProveedorTransportista> transportistas) throws Exception;

    public String modificarInvProveedorTO(InvProveedorTO invProveedorTO, String codigoAnterior, SisInfoTO sisInfoTO, List<InvProveedorTransportista> transportistas) throws Exception;

    public String eliminarInvProveedorTO(InvProveedorTO invProveedorTO, SisInfoTO sisInfoTO) throws Exception;

    public InvProveedor obtenerInvProveedorPorCedulaRuc(String empresa, String ruc);

    public Map<String, Object> obtenerDatosBasicosProveedor(Map<String, Object> parametros) throws Exception;

    /*getListProveedorTO*/
    public boolean getProveedorRepetido(String empresa, String codigo, String id, String nombre, String razonSocial) throws Exception;

    public List<InvProveedorTO> getListProveedorTO(String empresa, String codigoCategoria, boolean inactivos, String busqueda) throws Exception;

    public boolean modificarEstadoInvProveedor(InvProveedorPK invProveedorPK, boolean estado, SisInfoTO sisInfoTO) throws GeneralException;

    public String verificarDatosProveedorTO(String empresa, InvProveedorTO proveedor) throws Exception;

    /*IMPRIMIR Y EXPORTAR*/
    public byte[] generarReporteProveedor(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProveedorTO> listado) throws Exception;

    public Map<String, Object> exportarReporteProveedor(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProveedorTO> listado, List<DetalleExportarFiltrado> listadoFiltrado) throws Exception;

    public Map<String, Object> exportarReporteProveedorTodo(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<InvProveedorTO> listado) throws Exception;

    public List<InvProveedorSinMovimientoTO> obtenerProveedoresSinMovimientos(String empresa) throws Exception;

    public Map<String, Object> validarIdentificacionProveedor(Map<String, Object> map) throws Exception;

    public Map<String, Object> validarIdentificacionTransportista(Map<String, Object> map) throws Exception;
    
    public Map<String, Object>  verificarExistenciaEnProovedor(String empresa, List<InvProveedorTO> proveedores, List<InvProveedorTO> clientesIncompletos) throws Exception;
    
    public Map<String, Object> insertarListadoExcelProveedores(List<InvProveedorTO> listaInvProveedorTO, List<AnxProvinciaCantonTO> listaProvincias, SisInfoTO sisInfoTO) throws Exception;
}
