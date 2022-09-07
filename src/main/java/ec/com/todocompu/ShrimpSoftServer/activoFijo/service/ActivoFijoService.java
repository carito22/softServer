package ec.com.todocompu.ShrimpSoftServer.activoFijo.service;

import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfActivoTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfListadoActivoFijoTO;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.util.List;
import java.util.Map;

@Transactional
public interface ActivoFijoService {

    public String insertarModificarEliminarAfActivoTODesdeProducto(String empresa, String codigoProducto, String tipo, AfActivoTO afActivoTO, SisInfoTO sisInfoTO) throws Exception;

    public String insertarAfActivoTO(AfActivoTO afActivoTO, SisInfoTO sisInfoTO) throws Exception;

    public List<AfActivoTO> getListaAfActivos(String empresa, String sector, String ubicacion) throws Exception;

    public String modificarAfActivoTO(AfActivoTO afActivoTO, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarAfActivoTO(String empresa, String codigoProducto, SisInfoTO sisInfoTO) throws Exception;

    public boolean existeAfActivo(String empresa, String codigo) throws Exception;

    public byte[] generarReporteAfActivoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AfActivoTO> listado, String nombreReporte);

    public Map<String, Object> exportarReporteAfActivoTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AfActivoTO> listado) throws Exception;

    public List<AfListadoActivoFijoTO> listarActivoFijosCompras(String empresa) throws Exception;

    public Map<String, Object> exportarReporteListadoActivoFijoCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AfListadoActivoFijoTO> listado) throws Exception;

    public byte[] generarReporteListadoActivoCompra(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AfListadoActivoFijoTO> listado, String nombreReporte);

}
