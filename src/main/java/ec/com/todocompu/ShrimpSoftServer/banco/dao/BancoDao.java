package ec.com.todocompu.ShrimpSoftServer.banco.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ConsultaDatosBancoCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanBanco;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanBancoPK;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface BancoDao extends GenericDao<BanBanco, BanBancoPK> {

    public List<ListaBanBancoTO> getListaBanBancoTO(String empresa) throws Exception;

    public List<ListaBanBancoTO> getListaBanBancoTODefecto(String empresa) throws Exception;

    public List<BanBancoTO> getBancoTo(String empresa, String codigo) throws Exception;

    public ConsultaDatosBancoCuentaTO getConsultaDatosBancoCuentaTO(String empresa, String cuenta) throws Exception;

    public boolean insertarBanBanco(BanBanco banBanco, SisSuceso sisSuceso) throws Exception;

    public boolean modificarBanBanco(BanBanco banBanco, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarBanBanco(BanBanco banBanco, SisSuceso sisSuceso) throws Exception;

}
