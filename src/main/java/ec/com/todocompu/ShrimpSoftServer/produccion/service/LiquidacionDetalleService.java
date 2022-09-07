package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaDetalleLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionDetalle;

@Transactional
public interface LiquidacionDetalleService {

    public List<PrdLiquidacionMotivoComboTO> getListaLiquidacionMotivoComboTO(String empresa, boolean filtrarInactivos) throws Exception;

    public List<PrdListaDetalleLiquidacionTO> getListaPrdLiquidacionDetalleTO(String empresa, String motivo, String numero) throws Exception;

    public List<PrdLiquidacionDetalle> getListaPrdLiquidacionDetalle(String empresa, String motivo, String numero) throws Exception;
}
