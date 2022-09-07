package ec.com.todocompu.ShrimpSoftServer.caja.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaComboTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajCajaTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajCaja;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajCajaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface CajaDao extends GenericDao<CajCaja, CajCajaPK> {

	public List<CajCajaTO> getListadoCajCajaTO(String empresa) throws Exception;

	public List<CajCajaComboTO> getCajCajaComboTO(String empresa) throws Exception;

	public CajCajaTO getCajCajaTO(String empresa, String usuarioCodigo) throws Exception;

	public void accionCajCaja(CajCaja cajCajaAux, CajCaja cajCaja, SisSuceso sisSuceso, String accion) throws Exception;
}
