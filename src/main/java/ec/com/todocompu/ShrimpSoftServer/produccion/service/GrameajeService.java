package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.ListadoGrameaje;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdGrameajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListadoGrameajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdGrameaje;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface GrameajeService {

    public PrdGrameajeTO getPrdGrameajeTO(String empresa, String sector, String piscina, String fecha) throws Exception;

    public PrdGrameajeTO getPrdGrameajeTO(String empresa, String sector, String piscina, String desde, String hasta)
            throws Exception;

    public PrdGrameaje getPrdGrameaje(String empresa, String sector, String piscina) throws Exception;

    public List<PrdListadoGrameajeTO> getPrdListadoGrameajeTO(String empresa, String sector, String piscina,
            String desde, String hasta) throws Exception;

    public boolean getIsFechaGrameajeSuperior(String empresa, String sector, String numPiscina, String fechaDesde,
            String fechaHasta, String fecha) throws Exception;

    public String obtenerFechaGrameajeSuperior(String empresa, String sector, String numPiscina) throws Exception;

    public boolean insertarPrdGrameaje(PrdGrameajeTO prdGrameajeTO, SisInfoTO sisInfoTO) throws Exception;

    public boolean insertarGrameajeListado(List<PrdGrameaje> prdGrameaje, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarPrdGrameaje(PrdGrameajeTO prdGrameajeTO, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarGrameaje(PrdGrameaje prdGrameaje, SisInfoTO sisInfoTO) throws Exception;

    public List<PrdGrameaje> getPrdListadoGrameaje(String empresa, String sector, String fecha) throws Exception;

    public List<ListadoGrameaje> getListaGrameajesPorFechasCorrida(String empresa, String sector, String piscina,
            String desde, String hasta) throws Exception;

    public Map<String, Object> obtenerGramajesYFitoplanctonListado(String empresa, String sector, String piscina, String desde, String hasta) throws Exception;

}
