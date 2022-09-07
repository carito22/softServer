package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ProformasDetalleDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaDetalleProformasTO;

@Service
public class ProformasDetalleServiceImpl implements ProformasDetalleService {

    @Autowired
    private ProformasDetalleDao proformasDetalleDao;

    @Override
    public List<InvListaDetalleProformasTO> getListaInvProformasDetalleTO(String empresa, String periodo, String motivo,
            String numeroProformas) throws Exception {
        return proformasDetalleDao.getListaInvProformasDetalleTO(empresa, periodo, motivo, numeroProformas);
    }

}
