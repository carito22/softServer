package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunPlantillaRolesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunPlantillaSueldosLotePreliminarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunPlantillaSueldosLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoRolesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleRolesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaRolSaldoEmpleadoDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoSueldosPorPagarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhRolBDTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhRolEmpTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhRolSaldoEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhRolSueldoEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRol;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface RolDao extends GenericDao<RhRol, Integer> {

    public RhRol buscarRol(Integer secuencial) throws Exception;

    public RhRol buscarRolSQL(Integer secuencial) throws Exception;

    public List<RhFunPlantillaSueldosLoteTO> getFunPlantillaSueldosLote(String empCodigo, String fechaDesde,
            String fechaHasta, String categoria, String sector) throws Exception;

    public List<RhFunPlantillaSueldosLotePreliminarTO> getFunPlantillaSueldosLotePreliminar(String empCodigo,
            String fechaDesde, String fechaHasta, String categoria, String sector) throws Exception;

    public List<RhFunPlantillaRolesTO> getRhFunPlantillaRolesTO(String empresa, String fechaDesde, String fechaHasta,
            String categoria, String sector) throws Exception;

    public List<RhRolBDTO> getRhRol(String empresa, String periodo, String numero) throws Exception;

    public String buscarFechaRhRol(String empCodigo, String empId) throws Exception;

    public RhRolSueldoEmpleadoTO getRhRolSueldoEmpleado(String empCodigo, String empId) throws Exception;

    public RhRolSaldoEmpleadoTO getRhRolSaldoEmpleado(String empCodigo, String empId, String fechaDesde,
            String fechaHasta, String tipo) throws Exception;

    public List<RhListaRolSaldoEmpleadoDetalladoTO> getRhRolSaldoEmpleadoDetallado(String empCodigo, String empId,
            String fechaDesde, String fechaHasta) throws Exception;

    public List<RhListaDetalleRolesTO> getRhDetalleRolesTO(String empCodigo, String fechaDesde, String fechaHasta,
            String sector, String empCategoria, String empId, String filtro) throws Exception;

    public List<RhListaConsolidadoRolesTO> getRhConsolidadoRolesTO(String empCodigo, String fechaDesde,
            String fechaHasta, String sector, String empCategoria, String empId, boolean excluirLiquidacion) throws Exception;

    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoSueldos(String empresa, String fecha,
            String cuenta) throws Exception;

    public List<RhListaSaldoConsolidadoSueldosPorPagarTO> getRhSaldoConsolidadoSueldosPorPagarTO(String empCodigo,
            String fechaHasta) throws Exception;

    public ConContable insertarModificarRhProvisiones(ConContable conContable, List<RhRol> rhRoles, SisSuceso sisSuceso)
            throws Exception;

    public boolean insertarModificarRhRol(ConContable conContable, List<RhRol> rhRoles, SisSuceso sisSuceso)
            throws Exception;

    public List<RhRol> getListRhRol(ConContablePK conContablePK);

    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable);

    public boolean reconstruirSaldosEmpleadoPorContable(ConContablePK conContablePK);

    public boolean reconstruirSaldosEmpleado(String empresa);

    public List<RhListaDetalleRolesTO> getRhSoporteContableRolesTO(String empresa, String periodo, String tipo, String numero) throws Exception;

    public List<RhRol> obtenerTresUltimosRoles(String empleadoId, String fechaDesde, String empresa);

    public List<RhRolEmpTO> getListRhLiquidaciones(String empresa, String fechaDesde, String fechaHasta, String sector, String empCategoria, String empId, String nroRegistros);

    public List<RhRol> obtenerRolesPorPeriodoDeVacaciones(String empresa, String empId, String desde, String hasta) throws Exception;
}
