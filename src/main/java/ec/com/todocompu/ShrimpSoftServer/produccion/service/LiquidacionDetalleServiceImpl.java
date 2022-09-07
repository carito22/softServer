package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.LiquidacionDetalleDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.LiquidacionMotivoDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdLiquidacionMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaDetalleLiquidacionTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdLiquidacionDetalle;

@Service
public class LiquidacionDetalleServiceImpl implements LiquidacionDetalleService {

    @Autowired
    private LiquidacionMotivoDao liquidacionMotivoDao;
    @Autowired
    private LiquidacionDetalleDao liquidacionDetalleDao;

    @Override
    public List<PrdLiquidacionMotivoComboTO> getListaLiquidacionMotivoComboTO(String empresa, boolean filtrarInactivos) throws Exception {
        return liquidacionMotivoDao.getListaLiquidacionMotivoComboTO(empresa, filtrarInactivos);
    }

    @Override
    public List<PrdListaDetalleLiquidacionTO> getListaPrdLiquidacionDetalleTO(String empresa, String motivo, String numero) throws Exception {
        return liquidacionDetalleDao.getListaPrdLiquidacionDetalleTO(empresa, motivo, numero);
    }

    @Override
    public List<PrdLiquidacionDetalle> getListaPrdLiquidacionDetalle(String empresa, String motivo, String numero) throws Exception {
        List<PrdLiquidacionDetalle> detalle = liquidacionDetalleDao.getListaPrdLiquidacionDetalle(empresa, motivo, numero);
        return detalle;
    }

}
