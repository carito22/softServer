package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXiiiSueldoCalcularTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXiiiSueldoConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXiiiSueldoPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXiiiSueldoXiiiSueldoCalcular;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.Date;

@Transactional
public interface XiiiSueldoService {

    public List<RhFunXiiiSueldoCalcularTO> getRhFunCalcularXiiiSueldo(String empresa, String sector, String desde, String hasta) throws Exception;

    public List<RhFunXiiiSueldoConsultarTO> getRhFunConsultarXiiiSueldo(String empresa, String sector, String desde, String hasta) throws Exception;

    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoXiiis(String empresa, String fecha, String cuenta) throws Exception;

    public MensajeTO insertarModificarRhXiiiSueldo(ConContable conContable, List<RhXiiiSueldo> rhXiiiSueldos, SisInfoTO sisInfoTO) throws Exception;

    public List<RhXiiiSueldo> getListRhXiiiSueldo(ConContablePK conContablePK) throws Exception;

    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable);

    //Nuevo
    public List<RhXiiiSueldoXiiiSueldoCalcular> getListaRhXiiiSueldoXiiiSueldoCalcular(String empresa, String sector, String desde, String hasta, boolean formaPagoSeleccionado) throws Exception;

    public MensajeTO insertarRhXiiiSueldoXiiiSueldoCalcular(List<RhXiiiSueldoXiiiSueldoCalcular> lista, String observaciones, boolean aceptado, String empresa, Date fechaMaximo, RhXiiiSueldoPeriodoTO periodoSelccionado, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO) throws Exception;

    public String xiiiSueldoVerificarMayor(List<RhXiiiSueldoXiiiSueldoCalcular> lista);

    public MensajeTO modificarRhXiiiSueldoXiiiSueldoCalcular(ConContable contable, List<RhXiiiSueldoXiiiSueldoCalcular> lista, boolean aceptado, String empresa, SisInfoTO sisInfoTO) throws Exception;

}
