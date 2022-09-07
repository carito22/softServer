package ec.com.todocompu.ShrimpSoftServer.banco.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.listaBanBancoDebitoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanBancoDebito;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanBancoDebitoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

/**
 *
 * @author KevinQuispe
 */
public interface BancoDebitoDao extends GenericDao<BanBancoDebito, BanBancoDebitoPK> {

    public List<listaBanBancoDebitoTO> getListaBanBancoDebitoTO(String empresa) throws Exception;

    public boolean insertarBancoDebito(BanBancoDebito banBanco, SisSuceso sisSuceso) throws Exception;

    public boolean modificarBancoDebito(BanBancoDebito banBanco, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarBancoDebito(BanBancoDebito banBanco, SisSuceso sisSuceso) throws Exception;

    
}
