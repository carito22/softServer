package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConDetalleTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConFunContabilizarComprasDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConDetalle;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface DetalleDao extends GenericDao<ConDetalle, Long> {

	@Transactional

	public boolean modificarConDetalleTO(ConDetalle conDetalle, SisSuceso sisSuceso) throws Exception;

	public int buscarConteoDetalleEliminarCuenta(String empCodigo, String cuentaCodigo) throws Exception;

	public List<ConDetalleTO> getConDetalleTO(String empresa, String perCodigo, String tipCodigo, String numContable)
			throws Exception;

	public List<ConDetalle> getListConDetalle(ConContablePK conContablePK) throws Exception;

	public List<ConFunContabilizarComprasDetalleTO> getConDetalleEliminarTO(String empresa, String perCodigo,
			String tipCodigo, String numContable) throws Exception;

	public List<ConDetalleTablaTO> getListaConDetalleTO(String empresa, String periodo, String tipo, String numero);

	public void eliminarPorSql(Long detSecuencial);
}
