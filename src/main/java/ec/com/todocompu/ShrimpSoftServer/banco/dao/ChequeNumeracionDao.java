package ec.com.todocompu.ShrimpSoftServer.banco.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanChequeNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface ChequeNumeracionDao extends GenericDao<BanChequeNumeracion, Integer> {

	public boolean insertarBanChequesNumeracion(BanChequeNumeracion banBanco, SisSuceso sisSuceso) throws Exception;

	public boolean modificarBanChequeNumeracion(BanChequeNumeracion banBanco, SisSuceso sisSuceso) throws Exception;

	public boolean eliminarBanChequeNumeracion(BanChequeNumeracion banBanco, SisSuceso sisSuceso) throws Exception;

}
