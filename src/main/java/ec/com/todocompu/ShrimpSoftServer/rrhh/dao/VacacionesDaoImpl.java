package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhDetalleVacionesPagadasGozadasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhVacacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRol;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacaciones;
import java.math.BigDecimal;

@Repository
public class VacacionesDaoImpl extends GenericDaoImpl<RhVacaciones, String> implements VacacionesDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public List<RhVacacionesTO> getRhVacaciones(String empresa, String periodo, String numero) throws Exception {
        String sql = "SELECT *, null as  con_det_documento, null as con_fecha, null as vac_Observaciones, null as emp_apellidos_nombres, "
                + "null as usr_codigo "
                // + "vac_secuencial, vac_desde, vac_hasta, "
                // + "vac_valor, vac_forma_pago, vac_reversado, emp_id,
                // sec_codigo, "
                // + "vac_gozadas, vac_gozadas_desde, vac_gozadas_hasta "
                + "FROM recursoshumanos.rh_vacaciones " + "WHERE rh_vacaciones.emp_empresa = '" + empresa + "' "
                + "AND " + "rh_vacaciones.con_periodo = '" + periodo + "' " + "AND "
                + "rh_vacaciones.con_tipo = 'C-VAC' AND " + "rh_vacaciones.con_numero = '" + numero + "'";

        return genericSQLDao.obtenerPorSql(sql, RhVacacionesTO.class);
    }

    @Override
    public List<RhVacaciones> listarVacacionesEntreUnRol(RhRol rol) throws Exception {
        String sql = "SELECT v.* FROM recursoshumanos.rh_vacaciones v"
                + " INNER JOIN contabilidad.con_contable c "
                + "on c.con_numero = v.con_numero and c.con_tipo = v.con_tipo and c.con_periodo = v.con_periodo and c.con_numero = v.con_numero "
                + " WHERE v.emp_empresa = '" + rol.getRhEmpleado().getRhEmpleadoPK().getEmpEmpresa() + "' "
                + " AND " + "(c.con_fecha between '" + rol.getRolDesde() + "' AND '" + rol.getRolHasta()
                + "') AND v.emp_id = '" + rol.getRhEmpleado().getRhEmpleadoPK().getEmpId() + "' ";

        return genericSQLDao.obtenerPorSql(sql, RhVacaciones.class);
    }

    @Override
    public RhVacaciones buscarFechaRhVacaciones(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_vacaciones.* "
                + "FROM recursoshumanos.rh_vacaciones INNER JOIN contabilidad.con_contable "
                + "ON rh_vacaciones.con_empresa = con_contable.con_empresa AND "
                + "rh_vacaciones.con_periodo = con_contable.con_periodo AND "
                + "rh_vacaciones.con_tipo = con_contable.con_tipo AND "
                + "rh_vacaciones.con_numero = con_contable.con_numero " + "WHERE (rh_vacaciones.emp_empresa = '"
                + empCodigo + "') " + "AND (rh_vacaciones.emp_id = '" + empId + "') "
                + "AND NOT (con_contable.con_pendiente OR con_contable.con_reversado OR con_contable.con_anulado) "
                + "ORDER BY rh_vacaciones.vac_hasta DESC LIMIT 1";

        return genericSQLDao.obtenerObjetoPorSql(sql, RhVacaciones.class);
    }

    @Override
    public RhVacaciones buscarRhVacacionesPorPeriodoTrabajo(String empCodigo, String empId, String desde, String hasta) throws Exception {
        String sql = "SELECT rh_vacaciones.* "
                + "FROM recursoshumanos.rh_vacaciones "
                + "WHERE (rh_vacaciones.emp_empresa = '" + empCodigo + "') " + "AND (rh_vacaciones.emp_id = '" + empId + "') "
                + "AND (rh_vacaciones.vac_desde = '" + desde + "') " + "AND (rh_vacaciones.vac_hasta = '" + hasta + "') "
                + "ORDER BY rh_vacaciones.vac_hasta DESC LIMIT 1";

        return genericSQLDao.obtenerObjetoPorSql(sql, RhVacaciones.class);
    }

    @Override
    public RhVacaciones buscarSiExisteVacaciones(String empCodigo, String empId, String fechaHasta, Integer vacacionesPk) throws Exception {
        String sql = "SELECT rh_vacaciones.* "
                + "FROM recursoshumanos.rh_vacaciones INNER JOIN contabilidad.con_contable "
                + "ON rh_vacaciones.con_empresa = con_contable.con_empresa AND "
                + "rh_vacaciones.con_periodo = con_contable.con_periodo AND "
                + "rh_vacaciones.con_tipo = con_contable.con_tipo AND "
                + "rh_vacaciones.con_numero = con_contable.con_numero " + "WHERE (rh_vacaciones.emp_empresa = '"
                + empCodigo + "') " + "AND (rh_vacaciones.emp_id = '" + empId + "') "
                + "AND '" + fechaHasta + "' between rh_vacaciones.vac_desde AND rh_vacaciones.vac_hasta "
                + "AND rh_vacaciones.vac_secuencial != " + vacacionesPk
                + "AND NOT (con_contable.con_pendiente OR con_contable.con_reversado OR con_contable.con_anulado) "
                + "ORDER BY rh_vacaciones.vac_hasta DESC LIMIT 1";

        return genericSQLDao.obtenerObjetoPorSql(sql, RhVacaciones.class);
    }

    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoVacaciones(String empresa, String fecha,
            String cuenta) throws Exception {
        String sql = "SELECT * FROM recursoshumanos.fun_preaviso_vacaciones('" + empresa + "', '" + fecha + "', '"
                + cuenta + "')";

        return genericSQLDao.obtenerPorSql(sql, RhPreavisoAnticiposPrestamosSueldoTO.class);
    }

    @Override
    public List<RhDetalleVacionesPagadasGozadasTO> getRhDetalleVacacionesPagadasGozadasTO(String empCodigo,
            String empId, String sector, String fechaDesde, String fechaHasta, String tipo) throws Exception {

        empId = empId == null || empId.compareToIgnoreCase("") == 0 ? null : "'" + empId + "'";
        sector = sector == null || sector.compareToIgnoreCase("") == 0 ? null : "'" + sector + "'";
        fechaDesde = fechaDesde == null ? null : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? null : "'" + fechaHasta + "'";
        String pFechaDesde = null, pFechaHasta = null, gFechaDesde = null, gFechaHasta = null;
        if (tipo.compareToIgnoreCase("P") == 0) {
            pFechaDesde = fechaDesde;
            pFechaHasta = fechaHasta;
        } else if (tipo.compareToIgnoreCase("G") == 0) {
            gFechaDesde = fechaDesde;
            gFechaHasta = fechaHasta;
        }
        String sql = "SELECT * from recursoshumanos.fun_detalle_vacaciones(" + empCodigo + ", " + "" + sector + ", "
                + empId + ", " + pFechaDesde + ", " + pFechaHasta + ", " + gFechaDesde + ", " + gFechaHasta + ")";

        return genericSQLDao.obtenerPorSql(sql, RhDetalleVacionesPagadasGozadasTO.class);

    }

    @Override
    public List<RhVacaciones> getListRhVacaciones(ConContablePK conContablePK) throws Exception {
        return obtenerPorHql(
                "select vac from RhVacaciones vac inner join vac.rhEmpleado empl "
                + "where vac.conEmpresa=?1 and vac.conPeriodo=?2 and vac.conTipo=?3 and vac.conNumero=?4 order by empl.empApellidos||empl.empNombres",
                new Object[]{conContablePK.getConEmpresa(), conContablePK.getConPeriodo(), conContablePK.getConTipo(),
                    conContablePK.getConNumero()});
    }

    @Override
    public BigDecimal obtenerValorProvisionadoRoles(String fechaDesde, String fechaHasta, String empresa, String empId) {
        String sql = "SELECT sum(rol_total)"
                + " FROM recursoshumanos.rh_rol "
                + " INNER JOIN contabilidad.con_contable ON "
                + " rh_rol.con_empresa = con_contable.con_empresa "
                + " AND rh_rol.con_periodo = con_contable.con_periodo "
                + " AND rh_rol.con_tipo = con_contable.con_tipo "
                + " AND rh_rol.con_numero = con_contable.con_numero "
                + " WHERE (rh_rol.emp_empresa = '" + empresa + "') "
                + " AND (rh_rol.emp_id = '" + empId + "') "
                + " AND rh_rol.rol_desde>='" + fechaDesde + "' and rh_rol.rol_hasta <= '" + fechaHasta + "'"
                + " AND NOT (con_contable.con_pendiente OR con_contable.con_reversado OR con_contable.con_anulado) ";
        BigDecimal totalRoles = null;
        BigDecimal totalRolesSql = (BigDecimal) genericSQLDao.obtenerObjetoPorSql(sql);

        totalRoles = totalRolesSql != null ? totalRolesSql : new BigDecimal("0.00");
        return totalRoles;
    }

}
