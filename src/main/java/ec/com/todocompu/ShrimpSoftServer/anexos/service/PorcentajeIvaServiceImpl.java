package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.PorcentajeIvaDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;

@Service
public class PorcentajeIvaServiceImpl implements PorcentajeIvaService {

    @Autowired
    private PorcentajeIvaDao porcentajeIvaDao;

    @Override
    public BigDecimal getValorAnxPorcentajeIvaTO(String fechaFactura) throws Exception {

        if (fechaFactura.trim().isEmpty() || fechaFactura == null) {
            fechaFactura = UtilsValidacion.fechaSistema("yyyy-MM-dd");
        }
        return porcentajeIvaDao.getValorAnxPorcentajeIvaTO(fechaFactura);
    }

    @Override
    public BigDecimal getValorAnxMontoMaximoConsumidorFinalTO(String fechaFactura) throws Exception {
        if (fechaFactura.trim().isEmpty()) {
            fechaFactura = UtilsValidacion.fechaSistema("yyyy-MM-dd");
        }
        return porcentajeIvaDao.getValorAnxMontoMaximoConsumidorFinalTO(fechaFactura);
    }
}
