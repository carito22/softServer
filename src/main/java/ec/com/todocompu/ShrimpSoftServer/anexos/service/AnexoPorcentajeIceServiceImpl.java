package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.AnexoPorcentajeIceDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxPorcentajeIce;

@Service
public class AnexoPorcentajeIceServiceImpl implements AnexoPorcentajeIceService {

    @Autowired
    private AnexoPorcentajeIceDao anexoPorcentajeIceDao;

    @Override
    public List<AnxPorcentajeIce> listarAnexoPorcentajeIce() throws Exception {
        return anexoPorcentajeIceDao.listarAnexoPorcentajeIce();
    }

}
