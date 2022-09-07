package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxPorcentajeIce;

@Transactional
public interface AnexoPorcentajeIceService {

    public List<AnxPorcentajeIce> listarAnexoPorcentajeIce() throws Exception;

}
