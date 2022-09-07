package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhContableTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhDetalleVacionesPagadasGozadasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhVacacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRol;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.math.BigDecimal;
import java.util.Map;

@Transactional
public interface VacacionesService {

    public List<RhDetalleVacionesPagadasGozadasTO> getRhDetalleVacacionesPagadasGozadasTO(String empCodigo, String empId, String sector, String fechaDesde, String fechaHasta, String tipo) throws Exception;

    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoVacaciones(String empresa, String fecha, String cuenta) throws Exception;
    
    public List<RhVacaciones> listarVacacionesEntreUnRol(RhRol rol) throws Exception;

    public RhContableTO insertarRhVacacionesConContable(RhVacacionesTO rhVacacionesTO, SisInfoTO sisInfoTO) throws Exception;

    public RhContableTO modificarRhVacacionesConContable(RhVacacionesTO rhVacacionesTO, ConContable conContable, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> obtenerDatosParaCrudVacaciones(Map<String, Object> map) throws Exception;

    public List<RhVacaciones> getListRhVacaciones(ConContablePK conContablePK) throws Exception;

    String buscarFechaHastaRhVacaciones(String empCodigo, String empId) throws Exception;

    public BigDecimal obtenerValorProvisionadoRoles(String fechaDesde, String fechaHasta, String empresa, String empId) throws Exception;

    public List<RhVacaciones> listarRhVacacionesGozadas(String empresa, String empId, String sector, String fechaDesde, String fechaHasta) throws Exception;
    
    public RhVacaciones buscarRhVacacionesPorPeriodoTrabajo(String empCodigo, String empId, String desde, String hasta) throws Exception;
}
