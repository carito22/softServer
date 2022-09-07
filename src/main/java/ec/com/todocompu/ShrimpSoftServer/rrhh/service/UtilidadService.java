package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunUtilidadesCalcularTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunUtilidadesConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleUtilidadesLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidades;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Map;

@Transactional
public interface UtilidadService {

    public List<RhFunUtilidadesCalcularTO> getRhFunCalcularUtilidades(String empresa, String sector, String desde,
            String hasta, Integer totalDias, Integer totalCargas, BigDecimal totalPagar) throws Exception;

    public List<RhFunUtilidadesConsultarTO> getRhFunConsultarUtilidades(String empresa, String sector, String desde,
            String hasta) throws Exception;

    public List<RhListaDetalleUtilidadesLoteTO> getRhDetalleUtilidadesLoteTO(String empresa, String periodo, String tipo,
            String numero) throws Exception;

    public MensajeTO insertarModificarRhUtilidades(ConContable conContable, List<RhUtilidades> rhUtilidades,
            SisInfoTO sisInfoTO) throws Exception;

    public List<RhUtilidades> getListRhUtilidades(ConContablePK conContablePK) throws Exception;

    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable);

    public Map<String, Object> obtenerDatosParaCrudUtilidades(Map<String, Object> map) throws Exception;

    public MensajeTO insertarRhUtilidades(String observacionesContable, List<RhUtilidades> listaRhUtilidades, String fechaMaximaPago, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO) throws Exception;

}
