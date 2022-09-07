package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleHorasExtrasLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtras;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Date;
import java.util.Map;

@Transactional
public interface HorasExtrasService {

    public MensajeTO insertarModificarRhHorasExtras(ConContable conContable, List<RhHorasExtras> listaRhHorasExtras, SisInfoTO sisInfoTO)
            throws Exception;

    public MensajeTO insertarRhHorasExtras(Date fechaHasta, String empresa, List<RhHorasExtras> listaRhHorasExtras, String observacionesContable, String codigoUnico, SisInfoTO sisInfoTO)
            throws Exception;

    public List<RhHorasExtras> getListRhHorasExtras(ConContablePK conContablePK) throws Exception;

    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable);

    public Map<String, Object> obtenerDatosParaCrudHorasExtras(String empresa) throws Exception;

    public List<RhListaDetalleHorasExtrasTO> getRhListaDetalleHorasExtrasFiltradoTO(String empCodigo, String fechaDesde, String fechaHasta, String empCategoria, String empId, String parametro) throws Exception;

    public List<RhListaDetalleHorasExtrasLoteTO> getRhDetalleHorasExtrasLoteTO(String empresa, String periodo, String tipo, String numero) throws Exception;

    public List<RhListaSaldoConsolidadoHorasExtrasTO> getRhSaldoConsolidadoHorasExtrasTO(String empCodigo, String fechaHasta) throws Exception;

    public List<RhListaConsolidadoHorasExtrasTO> getRhListadoConsolidadoHorasExtrasTO(String empCodigo, String fechaDesde, String fechaHasta) throws Exception;

}
