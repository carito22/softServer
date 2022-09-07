package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.math.BigDecimal;
import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetallePrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoIndividualAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPrestamoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhPrestamo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface PrestamoDao extends GenericDao<RhPrestamo, String> {

	List<RhPrestamoTO> getRhPrestamo(String empresa, String periodo, String numero) throws Exception;

	public BigDecimal getRhRolSaldoPrestamo(String empCodigo, String empId, String fechaDesde, String fechaHasta)
			throws Exception;

	public List<RhListaDetalleAnticiposPrestamosTO> getRhDetalleAnticiposPrestamosTO(String empCodigo,
			String fechaDesde, String fechaHasta, String empCategoria, String empId,String parametro) throws Exception;

	public List<RhListaDetallePrestamosTO> getRhDetallePrestamosTO(String empCodigo, String fechaDesde,
			String fechaHasta, String empCategoria, String empId) throws Exception;

	public List<RhListaConsolidadoAnticiposPrestamosTO> getRhConsolidadoAnticiposPrestamosTO(String empCodigo,
			String fechaDesde, String fechaHasta, String empCategoria, String empId) throws Exception;

	public List<RhListaSaldoConsolidadoAnticiposPrestamosTO> getRhSaldoConsolidadoAnticiposPrestamosTO(String empCodigo,
			String fechaHasta) throws Exception;

	public List<RhListaSaldoIndividualAnticiposPrestamosTO> getRhSaldoIndividualAnticiposPrestamosTO(String empCodigo,
			String fechaDesde, String fechaHasta, String empId, String tipo) throws Exception;

	public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoPrestamos(String empresa, String fecha,
			String cuenta) throws Exception;

	public boolean insertarModificarRhPrestamo(ConContable conContable, RhPrestamo rhPrestamo, SisSuceso sisSuceso)
			throws Exception;

	public List<RhPrestamo> getListRhPrestamo(ConContablePK conContablePK);

	public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable);

}
