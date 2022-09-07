package ec.com.todocompu.ShrimpSoftServer.banco.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCaja;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanCajaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface BancoCajaDao extends GenericDao<BanCaja, BanCajaPK> {

    public List<ListaBanCajaTO> getListaBanCajaTO(String empresa) throws Exception;

    public List<BanCajaTO> getListBanCajaTO(String empresa) throws Exception;

    public boolean insertarBanCaja(BanCaja banCaja, SisSuceso sisSuceso) throws Exception;

    public boolean modificarBanCaja(BanCaja banCaja, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarBanCaja(BanCaja banCaja, SisSuceso sisSuceso) throws Exception;

}
