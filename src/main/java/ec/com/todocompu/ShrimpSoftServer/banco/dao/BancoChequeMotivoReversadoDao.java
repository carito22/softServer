package ec.com.todocompu.ShrimpSoftServer.banco.dao;

import java.util.List;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanChequeMotivoReversado;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanChequeMotivoReversadoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface BancoChequeMotivoReversadoDao extends GenericDao<BanChequeMotivoReversado, BanChequeMotivoReversadoPK> {

    public boolean insertarBanChequeMotivoReversado(BanChequeMotivoReversado banChequeMotivo, SisSuceso sisSuceso) throws Exception;

    public List<BanChequeMotivoReversado> getListaMotivoReversadoCheque(String empresa) throws Exception;
    
    public boolean modificarMotivoReversado(BanChequeMotivoReversado banChequeMotivo, SisSuceso sisSuceso) throws Exception;
    
    public boolean eliminarMotivoReversado(BanChequeMotivoReversado banChequeMotivo, SisSuceso sisSuceso) throws Exception;
    
    public boolean modificarEstadoMotivoReversado(BanChequeMotivoReversado banChequeMotivo, SisSuceso sisSuceso) throws Exception;
}
