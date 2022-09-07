package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConAdjuntosContableWebTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.ItemListaRolTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhContableTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunPlantillaSueldosLotePreliminarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunPlantillaSueldosLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoRolesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleRolesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaProvisionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaRolSaldoEmpleadoDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoSueldosPorPagarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhProvisionesListadoTransTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhRolEmpTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhRolSaldoEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhRolSueldoEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRol;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacacionesGozadas;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.math.BigDecimal;
import java.util.Map;

public interface RolService {

    @Transactional
    public List<RhFunPlantillaSueldosLoteTO> getFunPlantillaSueldosLote(String empCodigo, String fechaDesde,
            String fechaHasta, String categoria, String sector) throws Exception;

    @Transactional
    public List<RhFunPlantillaSueldosLotePreliminarTO> getFunPlantillaSueldosLotePreliminar(String empCodigo,
            String fechaDesde, String fechaHasta, String categoria, String sector) throws Exception;

    @Transactional
    public List<RhListaRolSaldoEmpleadoDetalladoTO> getRhRolSaldoEmpleadoDetalladoTO(String empCodigo, String empId,
            String fechaDesde, String fechaHasta) throws Exception;

    @Transactional
    public RhRolSaldoEmpleadoTO getRhRolSaldoEmpleado(String empCodigo, String empId,
            String fechaDesde, String fechaHasta, String tipo) throws Exception;

    @Transactional
    public List<RhListaDetalleRolesTO> getRhDetalleRolesTO(String empCodigo, String fechaDesde, String fechaHasta,
            String sector, String empCategoria, String empId, String filtro) throws Exception;

    @Transactional
    public List<RhListaConsolidadoRolesTO> getRhConsolidadoRolesTO(String empCodigo, String fechaDesde,
            String fechaHasta, String sector, String empCategoria, String empId, boolean excluirLiquidacion) throws Exception;

    @Transactional
    public List<RhListaSaldoConsolidadoSueldosPorPagarTO> getRhSaldoConsolidadoSueldosPorPagarTO(String empCodigo,
            String fechaHasta) throws Exception;

    @Transactional
    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoSueldos(String empresa, String fecha,
            String cuenta) throws Exception;

    public RhRolSueldoEmpleadoTO getRhRolSueldoEmpleadoTO(String empCodigo, String empId) throws Exception;

    @Transactional
    public RhContableTO insertarRhListaProvisionesConContable(String empresa, String periodo, String sector, String status,
            String usuario, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO insertarModificarRhProvisiones(ConContable conContable, List<RhRol> listaRhRol,
            SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public Map<String, Object> insertarModificarRhProvisionesWeb(RhProvisionesListadoTransTO provisionesListadoTransTO, String contableProvision,
            List<RhListaProvisionesTO> listaProvisionesTO, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public List<RhListaProvisionesTO> obtenerListaImprimirProvision(List<RhListaProvisionesTO> listaProvisionesTO) throws Exception;

    @Transactional
    public MensajeTO insertarModificarRhRol(ConContable conContable, List<RhRol> listaRhRol, SisInfoTO sisInfoTO)
            throws Exception;

    @Transactional
    public MensajeTO insertarModificarRhRolEscritorio(ConContable conContable, List<RhRol> listaRhRol, SisInfoTO sisInfoTO)
            throws Exception;

    @Transactional
    public List<RhRol> getListRhRol(ConContablePK conContablePK) throws Exception;

    @Transactional
    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable);

    @Transactional
    public List<RhListaDetalleRolesTO> getRhSoporteContableRolesTO(String empresa, String periodo, String tipo, String numero) throws Exception;

    @Transactional
    public ItemListaRolTO obtenerItemListaRolTO(ConContablePK contablePk, String empresa, String idEmpleado, SisInfoTO usuario) throws Exception;

    public Map<String, Object> obtenerDatosParaRolesDePago(Map<String, Object> map) throws Exception;

    @Transactional
    public MensajeTO insertarRhRol(String observacionesContable, List<RhRol> listaRhRol, String perHasta, List<ConAdjuntosContableWebTO> listadoImagenes, List<RhVacacionesGozadas> gozadas, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO modificarRhRol(List<RhRol> listaRhRol, ConContable contable, SisInfoTO sisInfoTO) throws Exception;

    public RhRol completarDatosDelRolDePago(RhRol rol, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public RhRolSaldoEmpleadoTO actualizarSaldosEmpleado(String empCodigo, String empId, String fechaDesde, String fechaHasta) throws Exception;

    public ItemListaRolTO obtenerVistaPreliminarRol(RhRol rol, String idEmpleado, String empresa, SisInfoTO sisInfoTO) throws Exception;

    public List<ItemListaRolTO> calcularValoresRolesPago(List<RhRol> roles, String empresa, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO insertarRhLiquidacion(RhRol listaRhRol, List<ConAdjuntosContableWebTO> listadoImagenes, SisInfoTO sisInfoTO) throws Exception;

    @Transactional
    public MensajeTO modificarRhiquidacion(RhRol rhRol, ConContable contable, SisInfoTO sisInfoTO) throws Exception;

    public ItemListaRolTO obtenerRolTO(ConContablePK conContablePK, String empresa, String idEmpleado, SisInfoTO sisInfoTO) throws Exception;

    public BigDecimal calcularPermisoMedico(String empleadoId, Integer diasPermiso, String fechaDesde, String empresa, SisInfoTO sisInfoTO) throws Exception;

    public Map<String, Object> obtenerDatosParaConsultaProvisiones(Map<String, Object> map) throws Exception;

    public Map<String, Object> obtenerDatosParaLiquidacion(Map<String, Object> map) throws Exception;

    public boolean reconstruirSaldosEmpleadoPorContable(ConContablePK conContablePK) throws Exception;

    public boolean reconstruirSaldosEmpleado(String empresa) throws Exception;

    public List<RhRolEmpTO> getListRhLiquidaciones(String empresa, String fechaDesde, String fechaHasta, String sector, String empCategoria, String empId, String nroRegistros);

    @Transactional
    public RhRol buscarRolSQL(Integer secuencial) throws Exception;

    public Map<String, Object> obtenerRol(Map<String, Object> map) throws Exception;

    public Map<String, Object> obtenerRolConsulta(Map<String, Object> map) throws Exception;

    public Map<String, Object> obtenerVistaPreliminarRol(Map<String, Object> map) throws Exception;

    public List<RhRol> obtenerRolesPorPeriodoDeVacaciones(String empresa, String empId, String desde, String hasta) throws Exception;
}
