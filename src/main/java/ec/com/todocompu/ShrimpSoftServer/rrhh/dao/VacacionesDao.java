package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhDetalleVacionesPagadasGozadasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhVacacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRol;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacaciones;
import java.math.BigDecimal;

public interface VacacionesDao extends GenericDao<RhVacaciones, String> {

    List<RhVacacionesTO> getRhVacaciones(String empresa, String periodo, String numero) throws Exception;

    public List<RhVacaciones> listarVacacionesEntreUnRol(RhRol rol) throws Exception;

    RhVacaciones buscarFechaRhVacaciones(String empCodigo, String empId) throws Exception;

    RhVacaciones buscarSiExisteVacaciones(String empCodigo, String empId, String fechaHasta, Integer vacacionesPk) throws Exception;

    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoVacaciones(String empresa, String fecha,
            String cuenta) throws Exception;

    public List<RhDetalleVacionesPagadasGozadasTO> getRhDetalleVacacionesPagadasGozadasTO(String empCodigo,
            String empId, String sector, String fechaDesde, String fechaHasta, String tipo) throws Exception;

    public List<RhVacaciones> getListRhVacaciones(ConContablePK conContablePK) throws Exception;

    BigDecimal obtenerValorProvisionadoRoles(String fechaDesde, String fechaHasta, String empresa, String empId);
    
    public RhVacaciones buscarRhVacacionesPorPeriodoTrabajo(String empCodigo, String empId, String desde, String hasta) throws Exception;
}
