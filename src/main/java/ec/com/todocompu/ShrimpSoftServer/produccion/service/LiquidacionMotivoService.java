package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionMotivoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionMotivo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface LiquidacionMotivoService {

    public PrdLiquidacionMotivoTO getPrdLiquidacionMotivoTO(String empresa,
            PrdLiquidacionMotivoTablaTO prdLiquidacionMotivoTablaTO) throws Exception;

    public List<PrdLiquidacionMotivoTablaTO> getListaPrdLiquidacionMotivoTablaTO(String empresa) throws Exception;

    public String insertarPrdLiquidacionMotivoTO(PrdLiquidacionMotivoTO prdLiquidacionMotivoTO, SisInfoTO sisInfoTO)
            throws Exception;

    public String modificarPrdLiquidacionMotivoTO(PrdLiquidacionMotivoTO prdLiquidacionMotivoTO, SisInfoTO sisInfoTO)
            throws Exception;

    public String eliminarPrdLiquidacionMotivoTO(PrdLiquidacionMotivoTO prdLiquidacionMotivoTO, SisInfoTO sisInfoTO)
            throws Exception;

    public String insertarPrdLiquidacionMotivo(PrdLiquidacionMotivo prdLiquidacionMotivo, SisInfoTO sisInfoTO)
            throws Exception;

    public String modificarPrdLiquidacionMotivo(PrdLiquidacionMotivo prdLiquidacionMotivo, SisInfoTO sisInfoTO)
            throws Exception;

    public String eliminarPrdLiquidacionMotivo(PrdLiquidacionMotivo prdLiquidacionMotivo, SisInfoTO sisInfoTO)
            throws Exception;

    public List<PrdLiquidacionMotivo> getListaPrdLiquidacionMotivo(String empresa) throws Exception;

    public List<PrdLiquidacionMotivo> getListaPrdLiquidacionMotivoTO(String empresa, boolean inactivo) throws Exception;

    public String modificarEstadoPrdLiquidacionMotivo(PrdLiquidacionMotivo prdLiquidacionMotivo, boolean estado, SisInfoTO sisInfoTO) throws Exception;

}
