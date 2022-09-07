package ec.com.todocompu.ShrimpSoftServer.banco.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanPreavisos;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanPreavisosPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface PreavisosDao extends GenericDao<BanPreavisos, BanPreavisosPK> {

	public BanPreavisos getBanPreavisos(String empresa, String cuentaContable) throws Exception;

	public Boolean insertarPreaviso(BanPreavisos banPreavisos, SisSuceso sisSuceso) throws Exception;

	public boolean eliminarBanPreaviso(BanPreavisos banPreavisos, SisSuceso sisSuceso) throws Exception;

}
