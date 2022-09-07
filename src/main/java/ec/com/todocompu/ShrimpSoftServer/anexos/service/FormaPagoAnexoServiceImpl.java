package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.AnexoFormaPagoDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxFormaPago;

@Service
public class FormaPagoAnexoServiceImpl implements FormaPagoAnexoService {

    @Autowired
    private AnexoFormaPagoDao formaPagoDao;

    @Override
    public AnxFormaPago getAnexoFormaPago(String codigo) throws Exception {
        return formaPagoDao.obtenerPorId(AnxFormaPago.class, codigo);
    }

    @Override
    public List<AnxFormaPagoTO> getAnexoFormaPagoTO(Date fechaFactura) throws Exception {
        return formaPagoDao.getAnexoFormaPagoTO(fechaFactura);
    }
}
