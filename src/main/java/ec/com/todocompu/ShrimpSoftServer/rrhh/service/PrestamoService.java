package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetallePrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoIndividualAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhPrestamo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.report.ReporteAnticipoPrestamoXIIISueldo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.UsuarioEmpresaReporteTO;
import java.util.Date;
import java.util.Map;

@Transactional
public interface PrestamoService {

    public BigDecimal getRhRolSaldoPrestamo(String empCodigo, String empId, String fechaDesde, String fechaHasta)
            throws Exception;

    public List<RhListaDetalleAnticiposPrestamosTO> getRhDetalleAnticiposPrestamosTO(String empCodigo,
            String fechaDesde, String fechaHasta, String empCategoria, String empId, String parametro) throws Exception;

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

    public MensajeTO insertarModificarRhPrestamo(ConContable conContable, RhPrestamo rhPrestamo, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO)
            throws Exception;

    public MensajeTO insertarModificarRhPrestamoWeb(ConContable conContable, RhPrestamo rhPrestamo, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO)
            throws Exception;

    public MensajeTO postIngresarPrestamo(RhPrestamo rhPrestamo, RhComboFormaPagoTO rhComboFormaPagoTO, Date fechaHasta, List<ConAdjuntosContableWebTO> listadoImagenes, String codigoUnico, SisInfoTO usuario) throws Exception;

    public ConContable generarConContable(String empresa, Date fecha, String observacion) throws Exception;

    public List<RhPrestamo> getListRhPrestamo(ConContablePK conContablePK) throws Exception;

    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable);

    public List<ReporteAnticipoPrestamoXIIISueldo> postGenerarReporteComprobantePrestamo(ConContable con, UsuarioEmpresaReporteTO usuarioEmpresaReporteTO, RhPrestamo rhPrestamo,
            RhComboFormaPagoTO rhComboFormaPagoTO, Date fechaHasta) throws Exception;

    public Map<String, Object> obtenerDatosParaCrudPrestamos(Map<String, Object> map) throws Exception;
}
