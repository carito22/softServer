package ec.com.todocompu.ShrimpSoftServer.activoFijo.service;

import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfCategoriasTO;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.util.List;
import java.util.Map;

@Transactional
public interface CategoriaActivoFijoService {

    public AfCategoriasTO consultarAfCategoriasTO(String empresa, String codigo) throws Exception;

    public String insertarAfCategoriasTO(AfCategoriasTO afCategoriasTO, SisInfoTO sisInfoTO) throws Exception;

    public List<AfCategoriasTO> getListaAfCategorias(String empresa, boolean filtrarInactivos) throws Exception;

    public String modificarAfCategoriasTO(AfCategoriasTO afCategoriasTO, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarAfCategoriasTO(AfCategoriasTO afCategoriasTO, SisInfoTO sisInfoTO) throws Exception;

    public byte[] generarReporteAfCategoriasTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AfCategoriasTO> listado, String nombreReporte);

    public Map<String, Object> exportarReporteAfCategoriasTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AfCategoriasTO> listado) throws Exception;

}
