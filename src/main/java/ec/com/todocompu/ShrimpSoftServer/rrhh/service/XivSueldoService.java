package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXivSueldoCalcularTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXivSueldoConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXivSueldoPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXivSueldoXivSueldoCalcular;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Date;

@Transactional
public interface XivSueldoService {

    public List<RhFunXivSueldoCalcularTO> getRhFunCalcularXivSueldo(String empresa, String sector, String desde, String hasta) throws Exception;

    public List<RhFunXivSueldoConsultarTO> getRhFunConsultarXivSueldo(String empresa, String sector, String desde, String hasta) throws Exception;

    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoXivs(String empresa, String fecha, String cuenta) throws Exception;

    public MensajeTO insertarModificarRhXivSueldo(ConContable conContable, List<RhXivSueldo> rhXivSueldos, SisInfoTO sisInfoTO) throws Exception;

    public List<RhXivSueldo> getListRhXivSueldo(ConContablePK conContablePK) throws Exception;

    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable);
//Nuevo

    public List<RhXivSueldoXivSueldoCalcular> getListaRhXivSueldoXivSueldoCalcular(String empresa, String sector, String desde, String hasta, boolean formaPagoSeleccionado) throws Exception;

    public MensajeTO insertarRhXivSueldoXivSueldoCalcular(ConContable conContable, List<RhXivSueldoXivSueldoCalcular> lista, String observaciones, boolean aceptado, String empresa, Date fechaMaximo, RhXivSueldoPeriodoTO periodoSelccionado, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO) throws Exception;

    public MensajeTO modificarRhXivSueldoXivSueldoCalcular(ConContable contable, List<RhXivSueldoXivSueldoCalcular> lista, boolean aceptado, String empresa, SisInfoTO sisInfoTO) throws Exception;

}
