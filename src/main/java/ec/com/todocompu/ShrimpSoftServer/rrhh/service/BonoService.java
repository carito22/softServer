package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaBonosLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoBonosHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoBonosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleBonosLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleBonosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoBonosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhBono;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Date;

@Transactional
public interface BonoService {

    public List<RhListaDetalleBonosLoteTO> getRhDetalleBonosLoteTO(String empresa, String periodo, String tipo,
            String numero) throws Exception;

    public List<RhListaDetalleBonosTO> getRhDetalleBonosTO(String empCodigo, String fechaDesde, String fechaHasta,
            String empCategoria, String empId, String estadoDeducible) throws Exception;

    public List<RhListaDetalleBonosTO> getRhListaDetalleBonosFiltradoTO(String empCodigo, String fechaDesde,
            String fechaHasta, String empCategoria, String empId, String estadoDeducible, String parametro)
            throws Exception;

    public MensajeTO insertarModificarRhBono(ConContable conContable, List<RhBono> listaRhBono, SisInfoTO sisInfoTO)
            throws Exception;

    public MensajeTO insertarRhBono(Date fechaHasta, String empresa, List<RhListaBonosLoteTO> listaRhListaBonosLoteTO, String observacionesContable, 
            List<ConAdjuntosContableWebTO> listadoImagenes, String codigoUnico, SisInfoTO sisInfoTO)
            throws Exception;

    public List<RhBono> getListRhBono(ConContablePK conContablePK) throws Exception;

    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable);

    public List<RhListaConsolidadoBonosTO> getRhConsolidadoBonosTO(String empCodigo, String fechaDesde,
            String fechaHasta) throws Exception;

    public List<RhListaSaldoConsolidadoBonosTO> getRhSaldoConsolidadoBonosTO(String empCodigo, String fechaHasta)
            throws Exception;

    public MensajeTO modificarRhBono(ConContable conContable, List<RhListaBonosLoteTO> listaRhBono, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO) throws Exception;

    public MensajeTO insertarRhBonoEscritorio(ConContable conContable, List<RhBono> listaRhBono, SisInfoTO sisInfoTO) throws Exception;

    public List<RhListaConsolidadoBonosHorasExtrasTO> listarConsolidadoBonosHorasExtras(String empCodigo, String fechaDesde, String fechaHasta) throws Exception;

}
