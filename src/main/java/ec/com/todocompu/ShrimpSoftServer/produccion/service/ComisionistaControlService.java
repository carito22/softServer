package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionComisionistaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdComisionista;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import ec.com.todocompu.ShrimpSoftUtils.sri.util.InformacionAdicional;
import java.util.Map;

@Transactional
public interface ComisionistaControlService {

    public String insertarComisionista(PrdComisionista prdComisionista, SisInfoTO sisInfoTO)
            throws Exception;

    public List<PrdComisionista> listarComisionista(String empresa) throws Exception;

    public String actualizarComisionista(PrdComisionista prdComisionista, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> exportarListadoComisionista(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdComisionista> listadoComisionista) throws Exception;

    public byte[] generarReporteComisionista(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdComisionista> listadoComisionista, SisInfoTO sisInfoTO) throws Exception;

    public List<PrdLiquidacionComisionistaTO> buscarListadoLiquidacionComisionista(String empresa, String sector, String piscina, String fechaDesde, String fechaHasta, String comisionista, boolean incluirTodos) throws Exception;

    public Map<String, Object> exportarLiquidacionComisionista(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, Map<String, Object> map) throws Exception;

    public byte[] generarReporteLiquidacionComisionista(UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, List<PrdLiquidacionComisionistaTO> listado, List<InformacionAdicional> infoAdicional, String fechaInicio, String fechaFin, String centroProduccion, String centroCosto) throws Exception;

}
