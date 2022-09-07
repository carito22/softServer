package ec.com.todocompu.ShrimpSoftServer.banco.service;

import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanBancoDebitoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.listaBanBancoDebitoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author KevinQuispe
 */
public interface BancoDebitoService {

    @Transactional
    List<listaBanBancoDebitoTO> getListaBanBancoDebitoTO(String empresa) throws Exception;

    @Transactional
    boolean insertarBancoDebitoTO(BanBancoDebitoTO banBancoTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    boolean modificarBancoDebitoTO(BanBancoDebitoTO banBancoTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    boolean eliminarBancoDebitoTO(BanBancoDebitoTO banBancoTO, SisInfoTO sisInfoTO) throws Exception;

}
