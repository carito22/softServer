package ec.com.todocompu.ShrimpSoftServer.banco.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanChequeMotivoReversado;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface BanChequeMotivoReversadoService {

    
    public String insertarMotivoBancoReversoCheque(BanChequeMotivoReversado banChequeMotivo, SisInfoTO sisInfoTO) throws Exception;
    
    public List<BanChequeMotivoReversado> getListaMotivoReversadoCheque(String empresa) throws Exception;

    public String modificarMotivoReversado(BanChequeMotivoReversado banChequeMotivo, SisInfoTO sisInfoTO) throws Exception;
    
    public String eliminarMotivoReversado(BanChequeMotivoReversado banChequeMotivo, SisInfoTO sisInfoTO) throws Exception;
    
    public String modificarEstadoMotivoReversado(BanChequeMotivoReversado banChequeMotivo, boolean estado, SisInfoTO sisInfoTO) throws Exception;
    
}
