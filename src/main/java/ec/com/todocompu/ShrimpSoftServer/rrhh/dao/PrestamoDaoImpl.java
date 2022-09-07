package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.ContableDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoPrestamoEmpleadoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetallePrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoIndividualAnticiposPrestamosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPrestamoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhPrestamo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoPrestamoEmpleado;

@Repository
public class PrestamoDaoImpl extends GenericDaoImpl<RhPrestamo, String> implements PrestamoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private ContableDao contableDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private SucesoPrestamoEmpleadoDao sucesoPrestamoEmpleadoDao;

    @Override
    public List<RhPrestamoTO> getRhPrestamo(String empresa, String periodo, String numero) throws Exception {
        String sql = "SELECT pre_secuencial, pre_valor, pre_numero_pagos, pre_forma_pago as pre_formas_pago, pre_reversado as reversar, emp_id, sec_codigo,'' as con_observaciones,'' as con_det_documento,'' as pre_fecha,'' as emp_apellidos_nombres,'' as con_numero,'' as tip_codigo,'' as per_codigo,'' as emp_codigo,'' as usr_inserta_prestamo "
                + "FROM recursoshumanos.rh_prestamo " + "WHERE rh_prestamo.emp_empresa = '" + empresa + "' "
                + "AND rh_prestamo.con_periodo = '" + periodo + "' " + "AND rh_prestamo.con_tipo = 'C-PRE' "
                + "AND rh_prestamo.con_numero = '" + numero + "'";

        return genericSQLDao.obtenerPorSql(sql, RhPrestamoTO.class);
    }

    @Override
    public BigDecimal getRhRolSaldoPrestamo(String empCodigo, String empId, String fechaDesde, String fechaHasta)
            throws Exception {
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        String sql = "SELECT COALESCE(saldo_prestamo, 0) FROM recursoshumanos.fun_saldo_empleado('" + empCodigo + "', "
                + "'" + empId + "', 'P', " + fechaDesde + ", " + fechaHasta + ")";

        return (BigDecimal) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public List<RhListaDetalleAnticiposPrestamosTO> getRhDetalleAnticiposPrestamosTO(String empCodigo,
            String fechaDesde, String fechaHasta, String empCategoria, String empId, String parametro)
            throws Exception {
        empCategoria = empCategoria.isEmpty() ? null : "'" + empCategoria + "'";
        empId = empId.isEmpty() ? null : "'" + empId + "'";
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        String sql = "";
        if (parametro.compareTo("PENDIENTE") == 0) {
            sql = "SELECT * FROM recursoshumanos.fun_detalle_anticipos_prestamos('" + empCodigo + "', " + ""
                    + fechaDesde + ", " + fechaHasta + ", " + empCategoria + ", " + empId
                    + ") where dap_pendiente=true";
        } else if (parametro.compareTo("ANULADO") == 0) {
            sql = "SELECT * FROM recursoshumanos.fun_detalle_anticipos_prestamos('" + empCodigo + "', " + ""
                    + fechaDesde + ", " + fechaHasta + ", " + empCategoria + ", " + empId + ") where dap_anulado=true";
        } else if (parametro.compareTo("REVERSADO") == 0) {
            sql = "SELECT * FROM recursoshumanos.fun_detalle_anticipos_prestamos('" + empCodigo + "', " + ""
                    + fechaDesde + ", " + fechaHasta + ", " + empCategoria + ", " + empId
                    + ") where dap_reversado=true";
        } else {
            sql = "SELECT * FROM recursoshumanos.fun_detalle_anticipos_prestamos('" + empCodigo + "', " + ""
                    + fechaDesde + ", " + fechaHasta + ", " + empCategoria + ", " + empId + ")";
        }
        return genericSQLDao.obtenerPorSql(sql, RhListaDetalleAnticiposPrestamosTO.class);
    }

    @Override
    public List<RhListaDetallePrestamosTO> getRhDetallePrestamosTO(String empCodigo, String fechaDesde,
            String fechaHasta, String empCategoria, String empId) throws Exception {
        empCategoria = empCategoria.isEmpty() ? null : "'" + empCategoria + "'";
        empId = empId.isEmpty() ? null : "'" + empId + "'";
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        String sql = "SELECT * FROM recursoshumanos.fun_detalle_prestamos('" + empCodigo + "', " + "" + fechaDesde
                + ", " + fechaHasta + ", " + empCategoria + ", " + empId + ")";

        return genericSQLDao.obtenerPorSql(sql, RhListaDetallePrestamosTO.class);
    }

    @Override
    public List<RhListaConsolidadoAnticiposPrestamosTO> getRhConsolidadoAnticiposPrestamosTO(String empCodigo,
            String fechaDesde, String fechaHasta, String empCategoria, String empId) throws Exception {
        empCategoria = empCategoria.isEmpty() ? null : "'" + empCategoria + "'";
        empId = empId.isEmpty() ? null : "'" + empId + "'";
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        String sql = "SELECT * FROM recursoshumanos.fun_consolidado_anticipos_prestamos('" + empCodigo + "', " + ""
                + fechaDesde + ", " + fechaHasta + ", " + empCategoria + ", " + empId + ")";

        return genericSQLDao.obtenerPorSql(sql, RhListaConsolidadoAnticiposPrestamosTO.class);
    }

    @Override
    public List<RhListaSaldoConsolidadoAnticiposPrestamosTO> getRhSaldoConsolidadoAnticiposPrestamosTO(String empCodigo,
            String fechaHasta) throws Exception {
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        String sql = "SELECT * FROM recursoshumanos.fun_saldo_consolidado_anticipos_prestamos('" + empCodigo + "', "
                + "" + fechaHasta + ")";

        return genericSQLDao.obtenerPorSql(sql, RhListaSaldoConsolidadoAnticiposPrestamosTO.class);
    }

    @Override
    public List<RhListaSaldoIndividualAnticiposPrestamosTO> getRhSaldoIndividualAnticiposPrestamosTO(String empCodigo,
            String fechaDesde, String fechaHasta, String empId, String tipo) throws Exception {
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        empId = empId == null ? null : "'" + empId + "'";
        String sql = "SELECT * FROM recursoshumanos.fun_saldo_individual_anticipos_prestamos('" + empCodigo + "', " + ""
                + fechaDesde + ", " + fechaHasta + ", " + empId + ", '" + tipo + "')";
        List<RhListaSaldoIndividualAnticiposPrestamosTO> list = genericSQLDao.obtenerPorSql(sql,
                RhListaSaldoIndividualAnticiposPrestamosTO.class);
        return list;
    }

    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoPrestamos(String empresa, String fecha,
            String cuenta) throws Exception {
        String sql = "SELECT * FROM recursoshumanos.fun_preaviso_prestamos('" + empresa + "', '" + fecha + "', '"
                + cuenta + "')";

        return genericSQLDao.obtenerPorSql(sql, RhPreavisoAnticiposPrestamosSueldoTO.class);
    }

    @Override
    public boolean insertarModificarRhPrestamo(ConContable conContable, RhPrestamo rhPrestamo, SisSuceso sisSuceso) throws Exception {
        if (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) {
            contableDao.buscarConteoUltimaNumeracion(conContable.getConContablePK());
        }

        sisSuceso.setSusClave(conContable.getConContablePK().getConPeriodo() + " " + conContable.getConContablePK().getConTipo() + " " + conContable.getConContablePK().getConNumero());
        sisSuceso.setSusDetalle("Prestamo por " + conContable.getConObservaciones());

        if (rhPrestamo.isPreAuxiliar() == false) {
            rhPrestamo.setPreSecuencial(null);
        }
        rhPrestamo.setPreDocumento(rhPrestamo.getPreDocumento() == null ? null : rhPrestamo.getPreDocumento().toUpperCase());
        rhPrestamo.setPreObservaciones(rhPrestamo.getPreObservaciones() == null ? null : rhPrestamo.getPreObservaciones().toUpperCase());
        rhPrestamo.setPreAuxiliar(false);
        rhPrestamo.setRhEmpleado(new RhEmpleado(rhPrestamo.getRhEmpleado().getRhEmpleadoPK()));
        rhPrestamo.setConContable(conContable);

        conContable.setConConcepto(conContable.getConConcepto() == null ? null : conContable.getConConcepto().toUpperCase());
        conContable.setConDetalle(conContable.getConDetalle() == null ? null : conContable.getConDetalle().toUpperCase());
        conContable.setConObservaciones(conContable.getConObservaciones() == null ? null : conContable.getConObservaciones().toUpperCase());
        conContable.setConPendiente(true);

        contableDao.saveOrUpdate(conContable);

        List<RhPrestamo> list = getListRhPrestamo(conContable.getConContablePK());
        if (list != null && !list.isEmpty()) {
            saveOrUpdate(rhPrestamo);
        } else {
            insertar(rhPrestamo);
        }

        sucesoDao.insertar(sisSuceso);
        /////////////////////////insertar suceso////////////////////////
        SisSucesoPrestamoEmpleado sucesoPres = new SisSucesoPrestamoEmpleado();
        String json = UtilsJSON.objetoToJson(rhPrestamo);
        sucesoPres.setSusJson(json);
        sucesoPres.setSisSuceso(sisSuceso);
        sucesoPres.setRhPrestamo(rhPrestamo);
        sucesoPrestamoEmpleadoDao.insertar(sucesoPres);
        return true;
    }

    @Override
    public List<RhPrestamo> getListRhPrestamo(ConContablePK conContablePK) {
        return obtenerPorHql(
                "select pre from RhPrestamo pre inner join pre.conContable con inner join con.conContablePK conpk inner join pre.rhEmpleado empl "
                + "where conpk.conEmpresa=?1 and conpk.conPeriodo=?2 and conpk.conTipo=?3 and conpk.conNumero=?4 order by empl.empApellidos||empl.empNombres",
                new Object[]{conContablePK.getConEmpresa(), conContablePK.getConPeriodo(), conContablePK.getConTipo(),
                    conContablePK.getConNumero()});
    }

    @Override
    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable) {
        String sql = "SELECT COUNT(pre_secuencial) FROM recursoshumanos.rh_prestamo INNER JOIN recursoshumanos.rh_empleado "
                + "ON (rh_prestamo.emp_empresa, rh_prestamo.emp_id)=(rh_empleado.emp_empresa, rh_empleado.emp_id) "
                + "WHERE (con_empresa, con_periodo, con_tipo, con_numero)=('" + conContablePK.getConEmpresa() + "','"
                + conContablePK.getConPeriodo() + "','" + conContablePK.getConTipo() + "','"
                + conContablePK.getConNumero() + "') AND " + "('" + fechaContable
                + "' <= rh_empleado.emp_fecha_ultimo_sueldo);";
        int count = (int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
        return (count == 0);
    }

}
