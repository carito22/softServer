package ec.com.todocompu.ShrimpSoftServer.banco.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

public interface BancoCajaService {

    @Transactional
    public List<ListaBanCajaTO> getListaBanCajaTO(String empresa) throws Exception;

    @Transactional
    public List<BanCajaTO> getListBanCajaTO(String empresa) throws Exception;

    @Transactional
    public boolean insertarBanCajaTO(BanCajaTO banCajaTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean modificarBanCajaTO(BanCajaTO banCajaTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public boolean eliminarBanCajaTO(BanCajaTO banCajaTO, SisInfoTO sisInfoTO) throws Exception;

}
