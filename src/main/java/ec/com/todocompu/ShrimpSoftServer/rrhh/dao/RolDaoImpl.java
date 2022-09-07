package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.ConContableProvisionDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.ContableDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoRolPagoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesRRHH;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContableProvision;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhEmpleadoFechaUltimoSueldoTO;
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
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoRolPago;
import java.util.Date;

@Repository
public class RolDaoImpl extends GenericDaoImpl<RhRol, Integer> implements RolDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private ContableDao contableDao;
    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private ConContableProvisionDao conContableProvisionDao;
    @Autowired
    private SucesoRolPagoDao sucesoRolPagoDao;

    @Override
    public RhRol buscarRol(Integer secuencial) throws Exception {
        return obtenerPorId(RhRol.class, secuencial);
    }

    @Override
    public RhRol buscarRolSQL(Integer secuencial) throws Exception {
        return genericSQLDao.obtenerObjetoPorSql(
                "SELECT * FROM recursoshumanos.rh_rol where rol_secuencial='" + secuencial + "'", RhRol.class);
    }

    @Override
    public List<RhFunPlantillaSueldosLoteTO> getFunPlantillaSueldosLote(String empCodigo, String fechaDesde,
            String fechaHasta, String categoria, String sector) throws Exception {
        categoria = categoria == null ? null : categoria.isEmpty() ? null : "'" + categoria + "'";
        sector = sector == null ? null : sector.isEmpty() ? null : "'" + sector + "'";
        String sql = "SELECT * " + "FROM recursoshumanos.fun_plantilla_sueldos_lote(" + "'" + empCodigo + "', " + "'"
                + fechaDesde + "', " + "'" + fechaHasta + "', " + "" + categoria + ", " + "" + sector + ");";

        return genericSQLDao.obtenerPorSql(sql, RhFunPlantillaSueldosLoteTO.class);
    }

    @Override
    public List<RhFunPlantillaSueldosLotePreliminarTO> getFunPlantillaSueldosLotePreliminar(String empCodigo,
            String fechaDesde, String fechaHasta, String categoria, String sector) throws Exception {
        categoria = categoria == null ? null : categoria.isEmpty() ? null : "'" + categoria + "'";
        sector = sector == null ? null : sector.isEmpty() ? null : "'" + sector + "'";
        String sql = "SELECT * " + "FROM recursoshumanos.fun_plantilla_sueldos_lote(" + "'" + empCodigo + "', " + "'"
                + fechaDesde + "', " + "'" + fechaHasta + "', " + "" + categoria + ", " + "" + sector + ");";

        return genericSQLDao.obtenerPorSql(sql, RhFunPlantillaSueldosLotePreliminarTO.class);

    }

    @Override
    public List<RhFunPlantillaRolesTO> getRhFunPlantillaRolesTO(String empresa, String fechaDesde, String fechaHasta,
            String categoria, String sector) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        categoria = categoria == null ? categoria : "'" + categoria + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        String sql = "SELECT * FROM recursoshumanos.fun_plantilla_roles(" + empresa + ", " + fechaDesde + ", "
                + fechaHasta + ", " + categoria + ", " + sector + ")";

        return genericSQLDao.obtenerPorSql(sql, RhFunPlantillaRolesTO.class);
    }

    @Override
    public List<RhRolBDTO> getRhRol(String empresa, String periodo, String numero) throws Exception {
        String sql = "SELECT rol_secuencial, rol_desde, "
                + "rol_hasta, rol_fecha_ultimo_sueldo, rol_dias_laborados_reales, "
                + "rol_dias_faltas_reales, rol_dias_extras_reales, "
                + "rol_dias_descanso_reales, rol_dias_pagados_reales, "
                + "rol_dias_permiso_medico, rol_saldo_anterior, rol_ingresos, "
                + "rol_bonos, rol_bonosnd, rol_bono_fijo, rol_bono_fijo_nd, "
                + "rol_otros_ingresos, rol_otros_ingresos_nd, " + "rol_liq_fondo_reserva, rol_liq_xiii, rol_liq_xiv, "
                + "rol_liq_vacaciones, rol_liq_salario_digno, rol_liq_desahucio, "
                + "rol_liq_desahucio_intempestivo, rol_liq_bonificacion, rol_anticipos, rol_prestamos, "
                + "rol_iess, rol_retencion_fuente, rol_descuento_permiso_medico, "
                + "rol_total, rol_forma_pago, rol_aporte_patronal, rol_iece, "
                + "rol_secap, rol_xiii, rol_xiv, rol_fondo_reserva, "
                + "rol_vacaciones, rol_desahucio, rol_reversado, emp_id, "
                + "emp_cargo, emp_sueldo, emp_bono_fijo, emp_bono_fijo_nd, "
                + "emp_otros_ingresos, emp_otros_ingresos_nd, emp_dias_laborados, "
                + "emp_dias_descanso, emp_cancelar_xiii_sueldo_mensualmente, "
                + "emp_cancelar_xiv_sueldo_mensualmente, " + "emp_cancelar_fondo_reserva_mensualmente, sec_codigo, "
                + "con_provision_periodo, con_provision_tipo, con_provision_numero " + "FROM recursoshumanos.rh_rol "
                + "WHERE rh_rol.emp_empresa = '" + empresa + "' " + "AND rh_rol.con_periodo = '" + periodo + "' "
                + "AND rh_rol.con_tipo = 'C-ROL' " + "AND rh_rol.con_numero = '" + numero + "'";

        return genericSQLDao.obtenerPorSql(sql, RhRolBDTO.class);
    }

    @Override
    public String buscarFechaRhRol(String empCodigo, String empId) throws Exception {
        Object ultimoSueldo = null;
        String fechaUltimoSueldo = "";
        String sql = "SELECT rh_rol.rol_hasta fecha_ultimo_sueldo "
                + "FROM recursoshumanos.rh_rol INNER JOIN contabilidad.con_contable "
                + "ON rh_rol.con_empresa = con_contable.con_empresa AND "
                + "rh_rol.con_periodo = con_contable.con_periodo AND rh_rol.con_tipo = con_contable.con_tipo AND "
                + "rh_rol.con_numero = con_contable.con_numero WHERE (rh_rol.emp_empresa = '" + empCodigo + "') "
                + "AND (rh_rol.emp_id = '" + empId + "') "
                + "AND (NOT con_contable.con_anulado)  AND (NOT rol_reversado)"
                + "ORDER BY rh_rol.rol_hasta DESC LIMIT 1";

        ultimoSueldo = (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
        ultimoSueldo = ultimoSueldo == null ? "" : ultimoSueldo;
        if (!ultimoSueldo.equals("")) {
            List<String> fecha = UtilsValidacion.separar(ultimoSueldo.toString(), "-");
            fechaUltimoSueldo = fecha.get(0) + "-" + fecha.get(1) + "-" + fecha.get(2);
        }
        return fechaUltimoSueldo;
    }

    @Override
    public RhRolSueldoEmpleadoTO getRhRolSueldoEmpleado(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_empleado.emp_cargo, rh_empleado.emp_forma_pago, rh_empleado.emp_sueldo_iess, rh_empleado.emp_fecha_ultimo_sueldo fecha_ultimo_sueldo "
                + "FROM recursoshumanos.rh_empleado WHERE emp_empresa = '" + empCodigo + "' AND emp_id='" + empId + "'";
        RhRolSueldoEmpleadoTO resultado = genericSQLDao.obtenerObjetoPorSql(sql, RhRolSueldoEmpleadoTO.class);
        if (resultado != null) {
            resultado.setRolFechaUltimoSueldo(obtenerFechaUltimoRol(empCodigo, empId));
        }
        return resultado;
    }

    public String obtenerFechaUltimoRol(String empCodigo, String empId) throws Exception {
        String sql = "SELECT DISTINCT ON (rh_rol.emp_empresa, rh_rol.emp_id) rh_rol.emp_empresa empresa, rh_rol.emp_id empleado, rh_rol.rol_hasta fecha"
                + "	FROM recursoshumanos.rh_rol INNER JOIN contabilidad.con_contable"
                + "	  ON rh_rol.con_empresa = con_contable.con_empresa AND"
                + "	     rh_rol.con_periodo = con_contable.con_periodo AND"
                + "	     rh_rol.con_tipo = con_contable.con_tipo AND"
                + "	     rh_rol.con_numero = con_contable.con_numero"
                + "	  FULL JOIN recursoshumanos.rh_empleado"
                + "	  ON rh_rol.emp_empresa = rh_empleado.emp_empresa AND"
                + "	     rh_rol.emp_id      = rh_empleado.emp_id AND"
                + "	     NOT rh_empleado.emp_inactivo"
                + "	WHERE rh_rol.emp_empresa='" + empCodigo + "' AND rh_rol.emp_id='" + empId + "' AND NOT (con_pendiente OR con_reversado OR con_anulado OR rol_auxiliar)"
                + "	ORDER BY rh_rol.emp_empresa, rh_rol.emp_id, rh_rol.rol_hasta DESC;";

        RhEmpleadoFechaUltimoSueldoTO resultado = genericSQLDao.obtenerObjetoPorSql(sql, RhEmpleadoFechaUltimoSueldoTO.class);
        if (resultado != null) {
            return resultado.getFecha();
        }
        return null;
    }

    @Override
    public RhRolSaldoEmpleadoTO getRhRolSaldoEmpleado(String empCodigo, String empId, String fechaDesde,
            String fechaHasta, String tipo) throws Exception {
        if (tipo == null || tipo.isEmpty()) {
            tipo = "T";
        }
        String sql = "SELECT * FROM recursoshumanos.fun_saldo_empleado('" + empCodigo + "', " + "'" + empId
                + "', '" + tipo + "', '" + fechaDesde + "', '" + fechaHasta + "')";
        RhRolSaldoEmpleadoTO resultado = genericSQLDao.obtenerObjetoPorSql(sql, RhRolSaldoEmpleadoTO.class);
        if (resultado != null) {
            resultado.setRolFechaUltimoSueldo(obtenerFechaUltimoRol(empCodigo, empId));
        }
        return resultado;
    }

    @Override
    public List<RhListaRolSaldoEmpleadoDetalladoTO> getRhRolSaldoEmpleadoDetallado(String empCodigo, String empId,
            String fechaDesde, String fechaHasta) throws Exception {
        String sql = "SELECT * FROM recursoshumanos.fun_saldo_empleado_detallado('" + empCodigo + "', " + "'" + empId
                + "', 'T', '" + fechaDesde + "', '" + fechaHasta + "')";

        return genericSQLDao.obtenerPorSql(sql, RhListaRolSaldoEmpleadoDetalladoTO.class);
    }

    @Override
    public List<RhListaDetalleRolesTO> getRhDetalleRolesTO(String empCodigo, String fechaDesde, String fechaHasta,
            String sector, String empCategoria, String empId, String filtro) throws Exception {
        empCategoria = empCategoria == null ? null : "'" + empCategoria + "'";
        empId = empId == null ? null : "'" + empId + "'";
        sector = sector == null ? null : "'" + sector + "'";
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        String complemento = "";

        if (filtro == null || filtro.equals("")) {
            complemento = "WHERE NOT (lrp_pendiente or lrp_reversado or lrp_anulado) AND lrp_liquidacion is null ";
        } else if (filtro.equalsIgnoreCase("LIQUIDACIONES")) {
            complemento = "WHERE NOT (lrp_pendiente or lrp_reversado or lrp_anulado) AND lrp_liquidacion IS NOT NULL AND lrp_liquidacion!=0 ";
        } else if (filtro.equalsIgnoreCase("PENDIENTES")) {
            complemento = "WHERE lrp_pendiente AND lrp_liquidacion is null ";
        } else if (filtro.equalsIgnoreCase("REVERSADOS")) {
            complemento = "WHERE lrp_reversado AND lrp_liquidacion is null ";
        } else if (filtro.equalsIgnoreCase("ANULADOS")) {
            complemento = "WHERE lrp_anulado AND lrp_liquidacion is null ";
        } else if (filtro.equalsIgnoreCase("TODOS")) {
            complemento = "WHERE lrp_liquidacion is null";
        } else if (filtro.equalsIgnoreCase("LIQ")) {
            complemento = "WHERE NOT (lrp_pendiente or lrp_reversado or lrp_anulado)";
        } else {
            complemento = "WHERE lrp_liquidacion is null";
        }
        String sql = "SELECT * FROM recursoshumanos.fun_detalle_roles('" + empCodigo + "', " + "" + fechaDesde + ", "
                + fechaHasta + ", " + sector + ", " + empCategoria + ", " + empId + ") " + complemento;
        return genericSQLDao.obtenerPorSql(sql, RhListaDetalleRolesTO.class);
    }

    @Override
    public List<RhListaConsolidadoRolesTO> getRhConsolidadoRolesTO(String empCodigo, String fechaDesde,
            String fechaHasta, String sector, String empCategoria, String empId, boolean excluirLiquidacion) throws Exception {
        String complemento = "";
        empCategoria = empCategoria == null ? null : "'" + empCategoria + "'";
        empId = empId == null ? null : empId.isEmpty() ? null : "'" + empId + "'";
        sector = sector == null ? null : sector.isEmpty() ? null : "'" + sector + "'";
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";

        String sql = "SELECT * FROM recursoshumanos.fun_consolidado_roles('" + empCodigo + "', " + "" + fechaDesde
                + ", " + fechaHasta + ", " + sector + ", " + empCategoria + ", " + empId + ", " + excluirLiquidacion + ")" + complemento + ";";

        return genericSQLDao.obtenerPorSql(sql, RhListaConsolidadoRolesTO.class);
    }

    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoSueldos(String empresa, String fecha,
            String cuenta) throws Exception {
        String sql = "SELECT * FROM recursoshumanos.fun_preaviso_sueldos('" + empresa + "', '" + fecha + "', '" + cuenta
                + "')";

        return genericSQLDao.obtenerPorSql(sql, RhPreavisoAnticiposPrestamosSueldoTO.class);
    }

    @Override
    public List<RhListaSaldoConsolidadoSueldosPorPagarTO> getRhSaldoConsolidadoSueldosPorPagarTO(String empCodigo,
            String fechaHasta) throws Exception {
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        String sql = "SELECT * FROM recursoshumanos.fun_saldo_consolidado_sueldos_por_pagar('" + empCodigo + "', " + ""
                + fechaHasta + ")";
        List<RhListaSaldoConsolidadoSueldosPorPagarTO> lista = genericSQLDao.obtenerPorSql(sql,
                RhListaSaldoConsolidadoSueldosPorPagarTO.class);
        return lista;
    }

    @Override
    public ConContable insertarModificarRhProvisiones(ConContable conContable, List<RhRol> rhRoles, SisSuceso sisSuceso) throws Exception {
        PrdSector sector = rhRoles.get(0).getPrdSector();
        boolean debeGeneraConContableProvision = false;
        if (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) {
            contableDao.buscarConteoUltimaNumeracion(conContable.getConContablePK());
            debeGeneraConContableProvision = true;
        } else {
            conContable = contableDao.obtenerPorId(ConContable.class, conContable.getConContablePK());
            conContable.setConBloqueado(false);
            actualizarConContableProvision(conContable, sector, sisSuceso);
        }
        sisSuceso.setSusClave(conContable.getConContablePK().getConPeriodo() + " " + conContable.getConContablePK().getConTipo() + " " + conContable.getConContablePK().getConNumero());
        sisSuceso.setSusDetalle("Provisiones por " + conContable.getConObservaciones());
        for (RhRol rhRol : rhRoles) {
            rhRol.setConContableProvision(conContable);
        }
        conContable.setConPendiente(true);
        conContable.setConReferencia("recursoshumanos.rh_provisiones*");
        contableDao.saveOrUpdate(conContable);
        saveOrUpdate(rhRoles);
        sucesoDao.insertar(sisSuceso);
        if (debeGeneraConContableProvision) {
            generarConContableProvision(conContable, sector, sisSuceso);
        }
        return conContable;
    }

    public ConContableProvision actualizarConContableProvision(ConContable conContable, PrdSector sector, SisSuceso sisSuceso) {
        ConContableProvision conContableProvision = conContableProvisionDao.obtenerObjetoPorSql("select * from contabilidad.con_contable_provision where sec_empresa = '" + sector.getPrdSectorPK().getSecEmpresa()
                + "' and sec_codigo = '" + sector.getPrdSectorPK().getSecCodigo() + "' and con_periodo = '" + conContable.getConContablePK().getConPeriodo() + "'", ConContableProvision.class);
        if (conContableProvision != null) {
            conContableProvision.setProvActualizarContable(true);
            conContableProvisionDao.actualizar(conContableProvision);
            SisSuceso sisSucesoProvision = Suceso.crearSisSuceso("contabilidad.con_contable_provision", conContableProvision.getProvSecuencial() + "", "UPDATE", "Se modificó el conContable de provisión.", new SisInfoTO());
            sisSucesoProvision.setSusMac(sisSuceso.getSusMac());
            sisSucesoProvision.setSisUsuario(sisSuceso.getSisUsuario());
            sisSucesoProvision.setDetEmpresa(sisSuceso.getDetEmpresa());
            sucesoDao.insertar(sisSucesoProvision);
            return conContableProvision;
        } else {
            return generarConContableProvision(conContable, sector, sisSuceso);
        }
    }

    public ConContableProvision generarConContableProvision(ConContable contable, PrdSector sector, SisSuceso sisSuceso) {
        ConContableProvision conContableProvision = new ConContableProvision();
        conContableProvision.setConContable(contable);
        conContableProvision.setProvActualizarContable(true);
        conContableProvision.setSecCodigo(sector.getPrdSectorPK().getSecCodigo());
        conContableProvision.setSecEmpresa(sector.getPrdSectorPK().getSecEmpresa());
        conContableProvision.setUsrCodigo(contable.getUsrCodigo());
        conContableProvision.setUsrEmpresa(contable.getUsrEmpresa());
        conContableProvision.setUsrFechaInserta(contable.getUsrFechaInserta() != null ? contable.getUsrFechaInserta() : new Date());
        conContableProvisionDao.insertar(conContableProvision);
        SisSuceso sisSucesoProvision = Suceso.crearSisSuceso("contabilidad.con_contable_provision", conContableProvision.getProvSecuencial() + "", "INSERT", "Se insertó el conContable de provisión.", new SisInfoTO());
        sisSucesoProvision.setSusMac(sisSuceso.getSusMac());
        sisSucesoProvision.setSisUsuario(sisSuceso.getSisUsuario());
        sisSucesoProvision.setDetEmpresa(sisSuceso.getDetEmpresa());
        sucesoDao.insertar(sisSucesoProvision);
        return conContableProvision;
    }

    @Override
    public boolean insertarModificarRhRol(ConContable conContable, List<RhRol> rhRoles, SisSuceso sisSuceso) throws Exception {
        if (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) {
            contableDao.buscarConteoUltimaNumeracion(conContable.getConContablePK());
        }

        sisSuceso.setSusClave(conContable.getConContablePK().getConPeriodo() + " " + conContable.getConContablePK().getConTipo() + " " + conContable.getConContablePK().getConNumero());
        sisSuceso.setSusDetalle("Rol por " + conContable.getConObservaciones());

        int rolesValidos = 0;
        for (int i = 0; i < rhRoles.size(); i++) {
            if (rhRoles.get(i).getConCuentas() == null
                    || rhRoles.get(i).getConCuentas().getConCuentasPK() == null
                    || rhRoles.get(i).getConCuentas().getConCuentasPK().getCtaCodigo() == null
                    || rhRoles.get(i).getConCuentas().getConCuentasPK().getCtaCodigo().compareTo("") == 0) {
                rhRoles.remove(i);
                i--;
            } else {
                rolesValidos++;
            }
        }

        if (rolesValidos == 0) {
            return false;
        }
        List<RhRol> list = getListRhRol(conContable.getConContablePK());
        for (RhRol rhRol : rhRoles) {
            if (rhRol.isRolAuxiliar() == false) {
                rhRol.setRolSecuencial(null);
            }
            rhRol.setRolDocumento(rhRol.getRolDocumento() == null ? null : rhRol.getRolDocumento().toUpperCase());
            rhRol.setRolObservaciones(rhRol.getRolObservaciones() == null ? null : rhRol.getRolObservaciones().toUpperCase());
            rhRol.setRolAuxiliar(false);
            rhRol.setConContable(conContable);
            if ("recursoshumanos.rh_liquidacion".equals(conContable.getConReferencia())) {
                empleadoDao.actualizar(rhRol.getRhEmpleado());
            }
        }

        conContable.setConConcepto(conContable.getConConcepto() == null ? null : conContable.getConConcepto().toUpperCase());
        conContable.setConDetalle(conContable.getConDetalle() == null ? null : conContable.getConDetalle().toUpperCase());
        conContable.setConObservaciones(conContable.getConObservaciones() == null ? null : conContable.getConObservaciones().toUpperCase());
        conContable.setConPendiente(true);

        contableDao.saveOrUpdate(conContable);

        if (list != null && !list.isEmpty()) {
            list.removeAll(rhRoles);
            list.forEach((rhRol) -> {
                eliminar(rhRol);
            });
            saveOrUpdate(rhRoles);
        } else {
            insertar(rhRoles);
        }

        sucesoDao.insertar(sisSuceso);
        ////////crear suceso/////////////////////
        for (RhRol rol : rhRoles) {
            SisSucesoRolPago sucesoRol = new SisSucesoRolPago();
            RhRol copia = ConversionesRRHH.convertirRhRol_RhRol(rol);
            String json = UtilsJSON.objetoToJson(copia);
            sucesoRol.setSusJson(json);
            sucesoRol.setSisSuceso(sisSuceso);
            sucesoRol.setRolEmpresa(rol.getConContable().getConContablePK().getConEmpresa());
            sucesoRol.setRhRol(copia);
            sucesoRolPagoDao.insertar(sucesoRol);
        }

        return true;
    }

    @Override
    public List<RhRol> getListRhRol(ConContablePK conContablePK) {
        return obtenerPorHql(
                "select rol from RhRol rol inner join rol.conContable con inner join con.conContablePK conpk inner join rol.rhEmpleado empl "
                + "where conpk.conEmpresa=?1 and conpk.conPeriodo=?2 and conpk.conTipo=?3 and conpk.conNumero=?4 order by empl.empApellidos||empl.empNombres",
                new Object[]{conContablePK.getConEmpresa(), conContablePK.getConPeriodo(), conContablePK.getConTipo(),
                    conContablePK.getConNumero()});
    }

    @Override
    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable) {
        String sql = "SELECT COUNT(rol_secuencial)  FROM recursoshumanos.rh_rol INNER JOIN recursoshumanos.rh_empleado "
                + "ON (rh_rol.emp_empresa, rh_rol.emp_id)=(rh_empleado.emp_empresa, rh_empleado.emp_id) "
                + "WHERE (con_empresa, con_periodo, con_tipo, con_numero)=('" + conContablePK.getConEmpresa() + "','"
                + conContablePK.getConPeriodo() + "','" + conContablePK.getConTipo() + "','"
                + conContablePK.getConNumero() + "') AND " + "('" + fechaContable
                + "' < rh_empleado.emp_fecha_ultimo_sueldo);";
        int count = (int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
        return (count == 0);
    }

    @Override
    public boolean reconstruirSaldosEmpleadoPorContable(ConContablePK conContablePK) {
        String sql = "SELECT * FROM recursoshumanos.recontruir_saldos_empleados_contable("
                + "'" + conContablePK.getConEmpresa() + "', '" + conContablePK.getConPeriodo() + "', '" + conContablePK.getConTipo() + "', '" + conContablePK.getConNumero() + "'"
                + ");";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public boolean reconstruirSaldosEmpleado(String empresa) {
        String sql = "SELECT * FROM recursoshumanos.recontruir_saldos_empleados('" + empresa + "');";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public List<RhListaDetalleRolesTO> getRhSoporteContableRolesTO(String empresa, String periodo, String tipo, String numero) {
        String sql = "SELECT * FROM recursoshumanos.fun_detalle_roles_por_lote('" + empresa + "', '" + periodo + "', '" + tipo + "', '" + numero + "')";
        return genericSQLDao.obtenerPorSql(sql, RhListaDetalleRolesTO.class);
    }

    @Override
    public List<RhRol> obtenerTresUltimosRoles(String empleadoId, String fechaDesde, String empresa) {

        String sql = "SELECT * FROM recursoshumanos.rh_rol r INNER JOIN contabilidad.con_contable c "
                + "ON r.con_empresa = c.con_empresa AND r.con_periodo = c.con_periodo AND r.con_tipo = c.con_tipo AND r.con_numero = c.con_numero "
                + "WHERE (emp_id, emp_empresa, con_referencia) = ('" + empleadoId + "', '" + empresa + "', 'recursoshumanos.rh_rol') "
                + "AND r.rol_desde < " + "'" + fechaDesde + "' ORDER BY rol_desde DESC limit 3";
        return genericSQLDao.obtenerPorSql(sql, RhRol.class);
    }

    @Override
    public List<RhRol> obtenerRolesPorPeriodoDeVacaciones(String empresa, String empId, String desde, String hasta) throws Exception {
        String sql = "SELECT * FROM recursoshumanos.rh_rol r INNER JOIN contabilidad.con_contable c "
                + "ON r.con_empresa = c.con_empresa AND r.con_periodo = c.con_periodo AND r.con_tipo = c.con_tipo AND r.con_numero = c.con_numero "
                + "WHERE (emp_id, emp_empresa, con_referencia) = ('" + empId + "', '" + empresa + "', 'recursoshumanos.rh_rol') "
                + "AND c.con_fecha between " + "'" + desde + "' and " + "'" + hasta + "'";
        return genericSQLDao.obtenerPorSql(sql, RhRol.class);
    }

    @Override
    public List<RhRolEmpTO> getListRhLiquidaciones(String empresa, String fechaDesde, String fechaHasta, String sector, String empCategoria, String empId, String nRegistros) {
        String limit = "";
        empresa = empresa == null ? null : "'" + empresa + "'";
        empCategoria = empCategoria == null ? null : "'" + empCategoria + "'";
        empId = empId == null ? null : "'" + empId + "'";
        sector = sector == null ? null : "'" + sector + "'";
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        if (nRegistros != null && nRegistros.compareTo("") != 0 && nRegistros.compareTo("0") != 0) {
            limit = " limit " + nRegistros;
        }

        String sql = "SELECT r.* FROM contabilidad.con_contable c INNER JOIN recursoshumanos.rh_rol r INNER JOIN recursoshumanos.rh_empleado e "
                + " ON r.emp_empresa = e.emp_empresa AND r.emp_id = e.emp_id "
                + " ON r.con_empresa = c.con_empresa AND r.con_periodo = c.con_periodo AND r.con_tipo = c.con_tipo AND r.con_numero = c.con_numero "
                + "WHERE c.con_empresa = " + empresa + " AND c.con_fecha >= " + fechaDesde + " AND c.con_fecha <= " + fechaHasta + " AND con_referencia = 'recursoshumanos.rh_liquidacion' "
                + "AND NOT c.con_pendiente AND NOT con_reversado AND NOT con_anulado AND "
                + "CASE WHEN " + sector + " IS NULL THEN  TRUE ELSE (r.sec_empresa = " + empresa + " AND r.sec_codigo = " + sector + ") END AND "
                + "CASE WHEN " + empCategoria + " IS NULL THEN TRUE  ELSE (e.emp_empresa = " + empresa + " AND e.cat_nombre = " + empCategoria + ") END  AND "
                + "CASE WHEN " + empId + "  IS NULL THEN TRUE ELSE (e.emp_empresa = " + empresa + " AND e.emp_id = " + empId + ") END "
                + "ORDER BY rol_desde DESC " + limit;
        return genericSQLDao.obtenerPorSql(sql, RhRolEmpTO.class);
    }

}
