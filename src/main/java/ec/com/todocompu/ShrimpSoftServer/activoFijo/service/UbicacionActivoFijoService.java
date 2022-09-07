package ec.com.todocompu.ShrimpSoftServer.activoFijo.service;

import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfUbicacionesTO;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.util.List;
import java.util.Map;

@Transactional
public interface UbicacionActivoFijoService {

    public String insertarAfUbicacionesTO(AfUbicacionesTO afUbicacionesTO, SisInfoTO sisInfoTO) throws Exception;

    public List<AfUbicacionesTO> getListaAfUbicaciones(String empresa) throws Exception;

    public String modificarAfUbicacionesTO(AfUbicacionesTO afUbicacionesTO, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarAfUbicacionesTO(AfUbicacionesTO afUbicacionesTO, SisInfoTO sisInfoTO) throws Exception;

    public byte[] generarReporteAfUbicacionesTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AfUbicacionesTO> listado, String nombreReporte);

    public Map<String, Object> exportarReporteAfUbicacionesTO(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<AfUbicacionesTO> listado) throws Exception;

}
