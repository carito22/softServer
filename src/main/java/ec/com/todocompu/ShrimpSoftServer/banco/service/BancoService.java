package ec.com.todocompu.ShrimpSoftServer.banco.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ConsultaDatosBancoCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

public interface BancoService {

    @Transactional
    List<ListaBanBancoTO> getListaBanBancoTO(String empresa) throws Exception;

    @Transactional
    List<ListaBanBancoTO> getListaBanBancoTODefecto(String empresa) throws Exception;

    @Transactional
    BanBancoTO getBancoTO(String empresa, String codigo) throws Exception;

    @Transactional
    boolean insertarBanBancoTO(BanBancoTO banBancoTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    boolean modificarBanBancoTO(BanBancoTO banBancoTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    boolean eliminarBanBancoTO(BanBancoTO banBancoTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    ConsultaDatosBancoCuentaTO getConsultaDatosBancoCuentaTO(String empresa, String cuenta) throws Exception;

}
