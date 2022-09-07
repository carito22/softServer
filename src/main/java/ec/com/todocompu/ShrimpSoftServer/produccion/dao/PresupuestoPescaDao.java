package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPesca;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface PresupuestoPescaDao extends GenericDao<PrdPresupuestoPesca, PrdPresupuestoPescaPK> {

	public boolean insertarModificarPrdPresupuestoPesca(PrdPresupuestoPesca prdPresupuestoPesca,
			List<PrdPresupuestoPescaDetalle> listaPrdPresupuestoPescaDetalles, SisSuceso sisSuceso) throws Exception;

	public PrdPresupuestoPesca getPrdPresupuestoPesca(PrdPresupuestoPescaPK presupuestoPescaPK);

	public List<PrdPresupuestoPesca> getListaPrdPresupuestoPesca(String empresa);

	public void desmayorizarPresupuestoPesca(PrdPresupuestoPescaPK prdPresupuestoPescaPK);

	public void anularRestaurarPresupuestoPesca(PrdPresupuestoPescaPK prdPresupuestoPescaPK, boolean anularRestaurar);

	public int buscarConteoUltimaNumeracionPresupuesto(String empCodigo, String motCodigo) throws Exception;

}
