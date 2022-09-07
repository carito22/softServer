package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPreLiquidacionMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface PreLiquidacionMotivoService {

    public String insertarPrdPreLiquidacionMotivo(PrdPreLiquidacionMotivo prdPreLiquidacionMotivo, SisInfoTO sisInfoTO)
            throws Exception;

    public String modificarPrdPreLiquidacionMotivo(PrdPreLiquidacionMotivo prdPreLiquidacionMotivo, SisInfoTO sisInfoTO)
            throws Exception;

    public String eliminarPrdPreLiquidacionMotivo(PrdPreLiquidacionMotivo prdPreLiquidacionMotivo, SisInfoTO sisInfoTO)
            throws Exception;

    public List<PrdPreLiquidacionMotivo> getListaPrdPreLiquidacionMotivo(String empresa) throws Exception;

    public List<PrdPreLiquidacionMotivo> getListaPrdPreLiquidacionMotivoTO(String empresa, boolean inactivo) throws Exception;

    public String modificarEstadoPrdPreLiquidacionMotivo(PrdPreLiquidacionMotivo prdPreLiquidacionMotivo, boolean estado, SisInfoTO sisInfoTO) throws Exception;

}
