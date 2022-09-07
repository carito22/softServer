package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhAnulacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCancelarBeneficioSocialTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhConsolidadoIngresosTabuladoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCtaIessTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhEmpleadoDescuentosFijosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhEmpleadoRolTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunListadoEmpleadosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaEmpleadoLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaProvisionesTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhProvisionDetalladoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhProvisionFechasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhRolSaldoEmpleadoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleadoDescuentosFijos;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleadoPK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhRol;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class EmpleadoDaoImpl extends GenericDaoImpl<RhEmpleado, RhEmpleadoPK> implements EmpleadoDao {

    @Autowired
    private EmpleadoDescuentosFijosDao empleadoDescuentosFijosDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;

    @Override
    public Boolean accionRhAvisoEntrada(RhEmpleado rhEmpleado, SisSuceso sisSuceso, char accion) throws Exception {
        actualizar(rhEmpleado);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public RhCtaIessTO buscarCtaRhIess(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_aportepersonal, rh_empleado.emp_fecha_afiliacion_iess, "
                + "emp_fecha_primer_ingreso, emp_fecha_primera_salida, "
                + "emp_fecha_ultimo_ingreso, emp_fecha_ultima_salida, emp_extension_cobertura_iess "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId.trim() + "')";

        return genericSQLDao.obtenerObjetoPorSql(sql, RhCtaIessTO.class);
    }

    @Override
    public List<RhEmpleadoTO> getListaEmpleadoTO(String empresa, String buscar, boolean estado) throws Exception {
        String sql = "SELECT emp_empresa, emp_residencia_tipo, emp_residencia_pais, emp_convenio, "
                + "emp_tipo_id, emp_id, emp_apellidos, emp_nombres, emp_genero, emp_lugar_nacimiento, "
                + "SUBSTRING(cast(emp_fecha_nacimiento as TEXT), 1, 10) as emp_fecha_nacimiento, "
                + "emp_estado_civil, emp_cargas_familiares, emp_provincia, emp_canton, "
                + "emp_domicilio, emp_numero, emp_telefono, sec_codigo, "
                + "SUBSTRING(cast(emp_fecha_primer_ingreso as TEXT), 1, 10) as emp_fecha_primer_ingreso, "
                + "SUBSTRING(cast(emp_fecha_primera_salida as TEXT), 1, 10) as emp_fecha_primera_salida, "
                + "SUBSTRING(cast(emp_fecha_ultimo_ingreso as TEXT), 1, 10) as emp_fecha_ultimo_ingreso, "
                + "SUBSTRING(cast(emp_fecha_ultima_salida as TEXT), 1, 10) as emp_fecha_ultima_salida, "
                + "emp_motivo_salida, "
                + "SUBSTRING(cast(emp_fecha_afiliacion_iess as TEXT), 1, 10) emp_fecha_afiliacion_iess, "
                + "emp_codigo_afiliacion_iess, emp_codigo_cargo, emp_cargo, emp_sueldo_iess, "
                + "emp_forma_pago, emp_dias_trabajados, emp_dias_descanso, emp_bono_fijo, emp_bono_fijo_nd, "
                + "emp_bono_fijo_nd, emp_cancelar_xiii_sueldo_mensualmente, "
                + "emp_cancelar_xiv_sueldo_mensualmente, emp_cancelar_fondo_reserva_mensualmente, "
                + "emp_retencion, emp_educacion, emp_alimentacion, emp_salud, emp_vivienda, "
                + "emp_vestuario, emp_rebaja_discapacidad, emp_rebaja_tercera_edad, "
                + "emp_sueldo_otra_compania, emp_otras_deducciones, emp_otras_rebajas, emp_utilidades, "
                + "emp_otros_ingresos, emp_otros_ingresos_nd, emp_extension_cobertura_iess, "
                + "emp_observaciones, emp_discapacidad_tipo, emp_discapacidad_porcentaje, "
                + "emp_discapacidad_id_tipo, emp_discapacidad_id_numero, emp_banco, emp_cuenta_tipo, "
                + "emp_cuenta_numero, emp_inactivo, emp_saldo_anterior, emp_saldo_anticipos, emp_saldo_bonos, emp_saldo_bonos_nd, emp_saldo_prestamos, emp_saldo_cuotas, emp_fecha_ultimo_sueldo, cat_nombre, emp_salario_neto, "
                + "emp_correo_electronico, usr_codigo, usr_fecha_inserta, emp_anticipo_quincena, emp_prestamo_quirografario, emp_prestamo_hipotecario, "
                + "emp_saldo_horas_extras_50, emp_saldo_horas_extras_100, emp_saldo_horas_extras_extraordinarias_100, emp_relacion_trabajo, emp_descripcion,emp_maternidad,emp_lactancia,emp_fecha_desde_lactancia,emp_fecha_hasta_lactancia "
                + "FROM recursoshumanos.rh_empleado "
                + "WHERE CASE WHEN " + estado + " THEN TRUE ELSE emp_inactivo = FALSE END AND (emp_empresa = '"
                + empresa + "') AND (emp_id || emp_apellidos || emp_nombres || sec_codigo || "
                + "sec_codigo || emp_nombres || emp_apellidos || emp_id LIKE TRANSLATE(' ' || '" + buscar
                + "' || ' ', ' ', '%')) ORDER BY emp_apellidos, emp_nombres";
        return genericSQLDao.obtenerPorSql(sql, RhEmpleadoTO.class);

    }

    @Override
    public List<RhListaEmpleadoTO> getListaConsultaEmpleado(String empresa, String buscar, Boolean interno,
            boolean estado) throws Exception {
        String sql;
        if (interno) {
            sql = "SELECT sec_codigo, emp_id, emp_apellidos || ' ' || emp_nombres emp_apellidos_nombre, "
                    + "emp_cargo, emp_fecha_afiliacion_iess IS NOT NULL as emp_fecha_afiliacion_iess, emp_salario_neto, emp_residencia_tipo "
                    + "FROM recursoshumanos.rh_empleado WHERE (emp_empresa = '" + empresa + "') AND (emp_id = '"
                    + buscar + "') ORDER BY 3;";
        } else {
            sql = "SELECT sec_codigo, emp_id, emp_apellidos || ' ' || emp_nombres emp_apellidos_nombre, "
                    + "emp_cargo, emp_fecha_afiliacion_iess IS NOT NULL as emp_fecha_afiliacion_iess, emp_salario_neto, emp_residencia_tipo "
                    + "FROM recursoshumanos.rh_empleado WHERE (emp_empresa = '" + empresa + "') AND "
                    + "(emp_id || emp_apellidos || emp_nombres || sec_codigo || "
                    + "sec_codigo || emp_nombres || emp_apellidos || emp_id LIKE TRANSLATE(' ' || '" + buscar
                    + "' || ' ', ' ', '%')) AND CASE WHEN " + estado + " THEN TRUE ELSE emp_inactivo = FALSE END ORDER BY 3;";
        }

        return genericSQLDao.obtenerPorSql(sql, RhListaEmpleadoTO.class);
    }

    @Override
    public RhEmpleadoRolTO getEmpleadoRolTO(String empCodigo, String empId) throws Exception {
        String sql = "SELECT emp_dias_trabajados, emp_dias_descanso, emp_sueldo_iess, "
                + "emp_retencion, emp_utilidades, emp_sueldo_otra_compania, emp_educacion, "
                + "emp_alimentacion, emp_salud, emp_vivienda, emp_vestuario, cat_nombre "
                + "FROM recursoshumanos.rh_empleado WHERE (emp_empresa = '" + empCodigo + "') AND(emp_id = '" + empId
                + "')";

        return genericSQLDao.obtenerObjetoPorSql(sql, RhEmpleadoRolTO.class);
    }

    @Override
    public boolean getRhEmpleadoRetencion(String empCodigo, String empId) throws Exception {
        String sql = "SELECT emp_retencion FROM recursoshumanos.rh_empleado WHERE (emp_empresa = '" + empCodigo
                + "') AND(emp_id = '" + empId + "');";

        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));

    }

    @Override
    public String buscarCtaRhAnticipo(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_anticipos "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";

        return (String) genericSQLDao.obtenerObjetoPorSql(sql);
    }

    @Override
    public String buscarCtaRhPrestamo(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_prestamos "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhGastoViaticos(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_gasto_viaticos "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhPorPagarViaticos(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_por_pagar_viaticos "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '"
                + empCodigo.trim() + "') AND (rh_empleado.emp_id = '" + empId.trim() + "')";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhPorPagarImpuestoRenta(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_por_pagar_impuesto_renta "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '"
                + empCodigo.trim() + "') AND (rh_empleado.emp_id = '" + empId.trim() + "')";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhPorPagarUtilidades(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_por_pagar_utilidades "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '"
                + empCodigo.trim() + "') AND (rh_empleado.emp_id = '" + empId.trim() + "')";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhPorPagarBonos(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_por_pagar_bonos "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '"
                + empCodigo.trim() + "') AND (rh_empleado.emp_id = '" + empId.trim() + "')";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));

    }

    @Override
    public String buscarCtaRhGastoBonoFijo(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_gasto_bono_fijo "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhGastoBonoFijoND(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_gasto_bono_fijo_nd "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhGastoOtrosIngresos(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_gasto_otros_ingresos "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhGastoOtrosIngresosND(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_gasto_otros_ingresos_nd "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhGastoBonosND(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_gasto_bonos_nd "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhGastoBonos(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_gasto_bonos "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));

    }

    @Override
    public String buscarCategoriaTipo(String empCodigo, String empId, String catNombre) throws Exception {
        String sql = "SELECT rh_categoria.tip_codigo "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "') AND (rh_empleado.cat_nombre = '" + catNombre + "')";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhPorPagarSueldos(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_por_pagar_sueldo "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '"
                + empCodigo.trim() + "') AND (rh_empleado.emp_id = '" + empId.trim() + "')";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhGastoSueldos(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_gasto_sueldos "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhSalarioNeto(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_gasto_aporteindividual "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhGastoXiii(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_gasto_xiii "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhGastoXiv(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_gasto_xiv "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhFondoReserva(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_fondoreserva "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhLiquidacionBonificacion(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_gasto_liquidacion_bono "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhGastoFondoReserva(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_gasto_fondoreserva "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhXiii(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_xiii "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";
        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhXiv(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_xiv "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";
        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhVacaciones(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_vacaciones "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";
        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhSalarioDigno(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_gasto_salario_digno "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";
        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhGastoSalarioDigno(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_gasto_salario_digno "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";
        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhDesahucio(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_desahucio "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";
        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarCtaRhGastoDesahucioIntempestivo(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_categoria.cta_gasto_desahucio_intempestivo "
                + "FROM recursoshumanos.rh_empleado INNER JOIN recursoshumanos.rh_categoria "
                + "ON (rh_empleado.cat_empresa || rh_empleado.cat_nombre) = "
                + "(rh_categoria.cat_empresa || rh_categoria.cat_nombre) WHERE (rh_empleado.emp_empresa = '" + empCodigo
                + "') AND (rh_empleado.emp_id = '" + empId + "')";
        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public RhCancelarBeneficioSocialTO getRhCancelarBeneficioSocialTO(String empresa, String empleadoId)
            throws Exception {
        String sql = "SELECT emp_cancelar_xiii_sueldo_mensualmente, emp_cancelar_xiv_sueldo_mensualmente, "
                + "emp_cancelar_fondo_reserva_mensualmente FROM recursoshumanos.rh_empleado WHERE (emp_empresa = '"
                + empresa + "') AND (emp_id = '" + empleadoId + "')";
        return genericSQLDao.obtenerObjetoPorSql(sql, RhCancelarBeneficioSocialTO.class);
    }

    @Override
    public List<RhEmpleadoDescuentosFijosTO> getRhEmpleadoDescuentosFijosTO(String empresa, String empresaID)
            throws Exception {
        String sql = "SELECT * FROM recursoshumanos.rh_empleado_descuentos_fijos WHERE recursoshumanos.rh_empleado_descuentos_fijos.emp_empresa = '"
                + empresa + "' AND  recursoshumanos.rh_empleado_descuentos_fijos.emp_id = '" + empresaID + "'";

        return genericSQLDao.obtenerPorSql(sql, RhEmpleadoDescuentosFijosTO.class);
    }

    @Override
    public boolean repetidoRhEmpleado(String empresa, String id, String apellidos, String nombres) throws Exception {
        id = id.isEmpty() ? null : "'" + id + "'";
        String sql = "SELECT * FROM recursoshumanos.fun_sw_empleado_repetido('" + empresa + "', " + id + ", '"
                + apellidos.toUpperCase() + "', '" + nombres.toUpperCase() + "')";

        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public List<RhFunListadoEmpleadosTO> getRhFunListadoEmpleadosTO(String empresa, String provincia, String canton,
            String sector, String categoria, boolean estado) throws Exception {
        provincia = provincia == null ? provincia : "'" + provincia + "'";
        canton = canton == null ? canton : "'" + canton + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        categoria = categoria == null ? categoria : "'" + categoria + "'";
        String sql = "SELECT * FROM recursoshumanos.fun_listado_empleados('" + empresa + "', " + provincia + ", "
                + canton + ", " + sector + ", " + categoria + ") WHERE CASE WHEN " + estado
                + " THEN TRUE ELSE emp_inactivo = FALSE END";

        return genericSQLDao.obtenerPorSql(sql, RhFunListadoEmpleadosTO.class);
    }

    @Override
    public List<RhEmpleado> getListaEmpleado(String empresa, String buscar, boolean estado) throws Exception {
        String sql = "SELECT * FROM recursoshumanos.rh_empleado WHERE CASE WHEN " + estado
                + " THEN TRUE ELSE emp_inactivo = FALSE END AND (emp_empresa = '" + empresa
                + "') AND (emp_id || emp_apellidos || emp_nombres || sec_codigo || "
                + "sec_codigo || emp_nombres || emp_apellidos || emp_id LIKE TRANSLATE(' ' || '" + buscar
                + "' || ' ', ' ', '%')) ORDER BY emp_apellidos, emp_nombres";
        return obtenerPorSql(sql, RhEmpleado.class);
    }

    @Override
    public boolean getSwInactivaEmpleado(String empresa, String empleado) throws Exception {
        String sql = "SELECT * FROM recursoshumanos.fun_sw_inactiva_empleado('" + empresa + "', '" + empleado + "')";

        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String getInactivaEmpleado(String empresa, String empleado) throws Exception {
        String fecha = UtilsDate.fechaFormatoString(sistemaWebServicio.getFechaActual(), "yyyy-MM-dd");
        String respuesta = "T";
        String sql = "SELECT * FROM recursoshumanos.fun_saldo_empleado('" + empresa + "', " + "'" + empleado
                + "', 'LIQUIDACION', '" + fecha + "', '" + fecha + "')";

        List<String> listaErrores1 = new ArrayList<>();

        RhRolSaldoEmpleadoTO resultado = genericSQLDao.obtenerObjetoPorSql(sql, RhRolSaldoEmpleadoTO.class);
        if (resultado != null) {
            if (resultado.getSaldoAnticipo().compareTo(BigDecimal.ZERO) > 0) {
                listaErrores1.add("Anticipo: " + resultado.getSaldoAnticipo());
            }

            if (resultado.getSaldoAnterior().compareTo(BigDecimal.ZERO) > 0) {
                listaErrores1.add("Saldo anterior pendiente: " + resultado.getSaldoAnterior());
            }

            if (resultado.getSaldoPrestamo().compareTo(BigDecimal.ZERO) > 0) {
                listaErrores1.add("Préstamo: " + resultado.getSaldoPrestamo());
            }

            if (resultado.getSaldoBono().compareTo(BigDecimal.ZERO) > 0) {
                listaErrores1.add("Bonos: " + resultado.getSaldoBono());
            }

            if (resultado.getSaldoBonond().compareTo(BigDecimal.ZERO) > 0) {
                listaErrores1.add("Bonos ND: " + resultado.getSaldoBonond());
            }
        }

        if (!listaErrores1.isEmpty()) {
            String mensajeListaErrores = "FEl empleado " + empleado + ", tiene los siguientes saldos pendientes:";
            for (int i = 0; i < listaErrores1.size(); i++) {
                mensajeListaErrores = mensajeListaErrores + "<br>" + listaErrores1.get(i);
            }
            respuesta = mensajeListaErrores;
        }

        return respuesta;
    }

    @Override
    public BigDecimal getRhValorImpuestoRenta(String empCodigo, String empId, String fechaHasta, Integer diasLaborados,
            java.math.BigDecimal rolIngresos, java.math.BigDecimal rolExtras, java.math.BigDecimal rolIngresosExento) throws Exception {
        String sql = "SELECT * FROM recursoshumanos.fun_calcula_ir('" + empCodigo + "', '" + empId + "', '" + fechaHasta
                + "', " + diasLaborados + ", " + rolIngresos + ", " + rolExtras + "," + rolIngresosExento + ")";

        return (BigDecimal) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public List<RhAnulacionesTO> getRhAnulacionesTO(String empresa, String periodo, String tipo, String numero)
            throws Exception {
        periodo = periodo == null ? null : "'" + periodo + "'";
        tipo = tipo == null ? null : "'" + tipo + "'";
        numero = numero == null ? null : "'" + numero + "'";
        String sql = "SELECT * FROM recursoshumanos.fun_anulaciones('" + empresa + "', " + periodo + ", " + tipo + ", "
                + numero + ")";

        return genericSQLDao.obtenerPorSql(sql, RhAnulacionesTO.class);
    }

    @Override
    public List<RhListaProvisionesTO> getRhListaProvisionesTO(String empresa, String periodo, String sector,
            String status) throws Exception {
        String sql = "SELECT * FROM recursoshumanos.fun_provisiones('" + empresa + "', '" + periodo + "', '" + sector
                + "', '" + status + "')";
        return genericSQLDao.obtenerPorSql(sql, RhListaProvisionesTO.class);
    }

    @Override
    public List<RhListaProvisionesTO> getRhListaProvisionesComprobanteContableTO(String empresa, String periodo,
            String tipo, String numero) throws Exception {
        String sql = "SELECT * FROM recursoshumanos.fun_provisiones_por_comprobante_contable('" + empresa + "', '"
                + periodo + "', '" + tipo + "', '" + numero + "')";
        return genericSQLDao.obtenerPorSql(sql, RhListaProvisionesTO.class);
    }

    @Override
    public BigDecimal getValorRecalculadoIR(java.math.BigDecimal valor, String desde, String hasta) throws Exception {
        String sql = "SELECT * FROM recursoshumanos.fun_calcular_ir(" + valor + ", '" + desde + "', '" + hasta + "')";

        return (BigDecimal) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public List<RhConsolidadoIngresosTabuladoTO> getConsolidadoIngresosTabulado(String empresa, String codigoSector,
            String fechaInicio, String fechaFin) throws Exception {
        codigoSector = codigoSector.isEmpty() ? null : "'" + codigoSector + "'";
        fechaInicio = fechaInicio.isEmpty() ? null : "'" + fechaInicio + "'";
        fechaFin = fechaFin.isEmpty() ? null : "'" + fechaFin + "'";
        String sql = "SELECT * FROM recursoshumanos.fun_consolidado_ingresos_tabulado('" + empresa + "', "
                + codigoSector + ", " + fechaInicio + ", " + fechaFin + ")";
        List<RhConsolidadoIngresosTabuladoTO> list = genericSQLDao.obtenerPorSql(sql,
                RhConsolidadoIngresosTabuladoTO.class);
        return list;
    }

    @Override
    public List<RhProvisionFechasTO> getProvisionPorFechas(String empresa, String codigoSector, String fechaInicio,
            String fechaFin, String agrupacion) throws Exception {
        codigoSector = codigoSector.isEmpty() ? null : "'" + codigoSector + "'";
        fechaInicio = fechaInicio.isEmpty() ? null : "'" + fechaInicio + "'";
        fechaFin = fechaFin.isEmpty() ? null : "'" + fechaFin + "'";
        agrupacion = agrupacion.isEmpty() ? null : "'" + agrupacion + "'";
        String sql = "SELECT * FROM recursoshumanos.fun_provision_por_fechas('" + empresa + "', " + codigoSector + ", "
                + fechaInicio + ", " + fechaFin + ", " + agrupacion + ")";

        List<RhProvisionFechasTO> list = genericSQLDao.obtenerPorSql(sql, RhProvisionFechasTO.class);
        return list;
    }

    @Override
    public List<RhProvisionDetalladoTO> listarProvisionesPorEmpleado(String empresa, String codigoSector, String fechaInicio, String fechaFin, boolean mostrarSaldos, String trabajador) throws Exception {
        codigoSector = codigoSector.isEmpty() ? null : "'" + codigoSector + "'";
        fechaInicio = fechaInicio.isEmpty() ? null : "'" + fechaInicio + "'";
        fechaFin = fechaFin.isEmpty() ? null : "'" + fechaFin + "'";
        trabajador = trabajador.isEmpty() ? null : "'" + trabajador + "'";
        String sql = "";
        if (mostrarSaldos) {
            sql = "SELECT * FROM recursoshumanos.fun_provision_detallado_saldos('" + empresa + "', " + codigoSector + ", " + fechaInicio + ", " + fechaFin + ", " + trabajador + ")";
        } else {
            sql = "SELECT * FROM recursoshumanos.fun_provision_detallado('" + empresa + "', " + codigoSector + ", " + fechaInicio + ", " + fechaFin + ", " + trabajador + ")";
        }

        List<RhProvisionDetalladoTO> list = genericSQLDao.obtenerPorSql(sql, RhProvisionDetalladoTO.class);
        return list;
    }

    @Override
    public RhEmpleado buscarEmpleado(String empCodigo, String empId) throws Exception {
        RhEmpleado empleado = obtenerObjetoPorHql(
                "select distinct e from RhEmpleado e inner join e.rhEmpleadoPK epk left join fetch e.rhEmpleadoDescuentosFijosList "
                + "where epk.empEmpresa=?1 and epk.empId=?2",
                new Object[]{empCodigo, empId});
        return empleado;
    }

    @Override
    public boolean insertarModificarRhEmpleado(RhEmpleado rhEmpleado,
            List<RhEmpleadoDescuentosFijos> listEmpleadoDescuentosFijos, SisSuceso sisSuceso) throws Exception {
        rhEmpleado.setEmpNombres(rhEmpleado.getEmpNombres().toUpperCase());
        rhEmpleado.setEmpApellidos(rhEmpleado.getEmpApellidos().toUpperCase());
        rhEmpleado.setEmpCargo(rhEmpleado.getEmpCargo().toUpperCase());
        rhEmpleado.setEmpLugarNacimiento(rhEmpleado.getEmpLugarNacimiento().toUpperCase());
        rhEmpleado.setEmpDomicilio(rhEmpleado.getEmpDomicilio().toUpperCase());
        rhEmpleado.setEmpObservaciones(rhEmpleado.getEmpObservaciones() == null ? null : rhEmpleado.getEmpObservaciones().toUpperCase());
        BigDecimal cero = new BigDecimal("0.00");
        rhEmpleado.setEmpOtrasRebajas(rhEmpleado.getEmpOtrasRebajas() == null ? cero : rhEmpleado.getEmpOtrasRebajas());
        rhEmpleado.setEmpSaldoAnterior(rhEmpleado.getEmpSaldoAnterior() == null ? cero : rhEmpleado.getEmpSaldoAnterior());
        rhEmpleado.setEmpSaldoAnticipos(rhEmpleado.getEmpSaldoAnticipos() == null ? cero : rhEmpleado.getEmpSaldoAnticipos());
        rhEmpleado.setEmpSaldoBonos(rhEmpleado.getEmpSaldoBonos() == null ? cero : rhEmpleado.getEmpSaldoBonos());
        rhEmpleado.setEmpSaldoBonosNd(rhEmpleado.getEmpSaldoBonosNd() == null ? cero : rhEmpleado.getEmpSaldoBonosNd());
        rhEmpleado.setEmpSaldoPrestamos(rhEmpleado.getEmpSaldoPrestamos() == null ? cero : rhEmpleado.getEmpSaldoPrestamos());
        rhEmpleado.setEmpBonoFijoNd(rhEmpleado.getEmpBonoFijoNd() == null ? cero : rhEmpleado.getEmpBonoFijoNd());
        rhEmpleado.setEmpBonoFijo(rhEmpleado.getEmpBonoFijo() == null ? cero : rhEmpleado.getEmpBonoFijo());
        rhEmpleado.setEmpOtrosIngresos(rhEmpleado.getEmpOtrosIngresos() == null ? cero : rhEmpleado.getEmpOtrosIngresos());
        rhEmpleado.setEmpOtrosIngresosNd(rhEmpleado.getEmpOtrosIngresosNd() == null ? cero : rhEmpleado.getEmpOtrosIngresosNd());
        rhEmpleado.setEmpEducacion(rhEmpleado.getEmpEducacion() == null ? cero : rhEmpleado.getEmpEducacion());
        rhEmpleado.setEmpRebajaDiscapacidad(rhEmpleado.getEmpRebajaDiscapacidad() == null ? cero : rhEmpleado.getEmpRebajaDiscapacidad());
        rhEmpleado.setEmpAlimentacion(rhEmpleado.getEmpAlimentacion() == null ? cero : rhEmpleado.getEmpAlimentacion());
        rhEmpleado.setEmpRebajaTerceraEdad(rhEmpleado.getEmpRebajaTerceraEdad() == null ? cero : rhEmpleado.getEmpRebajaTerceraEdad());
        rhEmpleado.setEmpSalud(rhEmpleado.getEmpSalud() == null ? cero : rhEmpleado.getEmpSalud());
        rhEmpleado.setEmpSueldoOtraCompania(rhEmpleado.getEmpSueldoOtraCompania() == null ? cero : rhEmpleado.getEmpSueldoOtraCompania());
        rhEmpleado.setEmpVivienda(rhEmpleado.getEmpVivienda() == null ? cero : rhEmpleado.getEmpVivienda());
        rhEmpleado.setEmpOtrasDeducciones(rhEmpleado.getEmpOtrasDeducciones() == null ? cero : rhEmpleado.getEmpOtrasDeducciones());
        rhEmpleado.setEmpVestuario(rhEmpleado.getEmpVestuario() == null ? cero : rhEmpleado.getEmpVestuario());
        rhEmpleado.setEmpUtilidades(rhEmpleado.getEmpUtilidades() == null ? cero : rhEmpleado.getEmpUtilidades());
        rhEmpleado.setEmpAnticipoQuincena(rhEmpleado.getEmpAnticipoQuincena() == null ? cero : rhEmpleado.getEmpAnticipoQuincena());
        rhEmpleado.setEmpPrestamoQuirografario(rhEmpleado.getEmpPrestamoQuirografario() == null ? cero : rhEmpleado.getEmpPrestamoQuirografario());
        rhEmpleado.setEmpPrestamoHipotecario(rhEmpleado.getEmpPrestamoHipotecario() == null ? cero : rhEmpleado.getEmpPrestamoHipotecario());
        rhEmpleado.setEmpTurismo(rhEmpleado.getEmpTurismo() == null ? cero : rhEmpleado.getEmpTurismo());
        rhEmpleado.setEmpPensionesAlimenticias(rhEmpleado.getEmpPensionesAlimenticias() == null ? cero : rhEmpleado.getEmpPensionesAlimenticias());
        rhEmpleado.setEmpDescuentoPrestamos(rhEmpleado.getEmpDescuentoPrestamos() == null ? cero : rhEmpleado.getEmpDescuentoPrestamos());

        List<RhEmpleadoDescuentosFijos> list = rhEmpleado.getRhEmpleadoDescuentosFijosList();

        for (int i = 0; i < listEmpleadoDescuentosFijos.size(); i++) {
            if (listEmpleadoDescuentosFijos.get(i).getRhEmpleado() == null) {
                listEmpleadoDescuentosFijos.get(i).setDescSecuencial(null);
            }
            listEmpleadoDescuentosFijos.get(i).setRhEmpleado(rhEmpleado);
        }

        if (listEmpleadoDescuentosFijos != null && !listEmpleadoDescuentosFijos.isEmpty()) {
            rhEmpleado.setRhEmpleadoDescuentosFijosList(new ArrayList<RhEmpleadoDescuentosFijos>());
            rhEmpleado.getRhEmpleadoDescuentosFijosList().addAll(listEmpleadoDescuentosFijos);
        }

        if (list != null && !list.isEmpty() && listEmpleadoDescuentosFijos.isEmpty()) {
            rhEmpleado.setRhEmpleadoDescuentosFijosList(null);
        }

        saveOrUpdate(rhEmpleado);

        if (list != null && !list.isEmpty()) {
            list.removeAll(listEmpleadoDescuentosFijos);
            for (int i = 0; i < list.size(); i++) {
                empleadoDescuentosFijosDao.eliminarPorSql(list.get(i).getDescSecuencial());
            }
        }

        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean insertarRhEmpleado(RhEmpleado rhEmpleado,
            List<RhEmpleadoDescuentosFijos> listRhEmpleadoDescuentosFijos, SisSuceso sisSuceso) throws Exception {
        if (listRhEmpleadoDescuentosFijos != null && !listRhEmpleadoDescuentosFijos.isEmpty()) {
            for (RhEmpleadoDescuentosFijos rhEmpleadoDescuentosFijos : listRhEmpleadoDescuentosFijos) {
                rhEmpleadoDescuentosFijos.setDescSecuencial(null);
                rhEmpleadoDescuentosFijos.setRhEmpleado(rhEmpleado);
            }
        }

        rhEmpleado.setRhEmpleadoDescuentosFijosList(listRhEmpleadoDescuentosFijos);
        insertar(rhEmpleado);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarRhEmpleado(RhEmpleado rhEmpleado, List<RhEmpleadoDescuentosFijos> listaModificar,
            List<RhEmpleadoDescuentosFijos> listaEliminar, SisSuceso sisSuceso) throws Exception {
        actualizar(rhEmpleado);
        if (listaModificar != null && !listaModificar.isEmpty()) {
            for (RhEmpleadoDescuentosFijos rhEmpleadoDescuentosFijos : listaModificar) {
                if (rhEmpleadoDescuentosFijos.getRhEmpleado() == null) {
                    rhEmpleadoDescuentosFijos.setDescSecuencial(null);
                }
                rhEmpleadoDescuentosFijos.setRhEmpleado(rhEmpleado);
                empleadoDescuentosFijosDao.saveOrUpdate(rhEmpleadoDescuentosFijos);
            }
        }

        if (listaEliminar != null && !listaEliminar.isEmpty()) {
            for (RhEmpleadoDescuentosFijos rhEmpleadoDescuentosFijos : listaEliminar) {
                rhEmpleadoDescuentosFijos.setRhEmpleado(rhEmpleado);
                empleadoDescuentosFijosDao.eliminar(rhEmpleadoDescuentosFijos);
            }
        }
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarRhEmpleado(RhEmpleado rhEmpleado, List<RhEmpleadoDescuentosFijos> lista, SisSuceso sisSuceso)
            throws Exception {
        eliminar(rhEmpleado);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarRhEmpleado(String empCodigo, String empId) throws Exception {
        String sql = "SELECT * FROM recursoshumanos.fun_sw_elimina_empleado('" + empCodigo + "', '" + empId + "')";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String getRhEmpleadoApellidosNombres(String empresa, String id) throws Exception {
        id = id.isEmpty() ? null : "'" + id + "'";
        String sql = "SELECT emp_apellidos || ' ' || emp_nombres emp_apellidos_nombres "
                + "FROM recursoshumanos.rh_empleado WHERE (emp_empresa = '" + empresa + "') AND (emp_id = " + id + ")";
        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public List<RhListaEmpleadoLoteTO> getListaEmpleadoLote(String empresa, String categoria, String sector,
            String fechaHasta, String motivo, boolean rol) {
        categoria = categoria == null || categoria.isEmpty() ? null : "'" + categoria + "'";
        sector = sector == null || sector.isEmpty() ? null : "'" + sector + "'";
        motivo = motivo == null || motivo.isEmpty() ? null : "'" + motivo + "'";
        String fechaDesde = "";
        if (rol) {
            String[] split = fechaHasta.split("¬");
            fechaHasta = split[0];
            fechaDesde = split[1];
        }
        String sql = "SELECT * FROM recursoshumanos.fun_plantilla_empleados('" + empresa + "', '" + fechaHasta + "', "
                + categoria + ", " + sector + ", " + motivo + ") ";
        //+ (rol ? "where pr_fecha_ultimo_sueldo+1='" + fechaDesde + "' or pr_fecha_ultimo_sueldo is null" : "");

        return genericSQLDao.obtenerPorSql(sql, RhListaEmpleadoLoteTO.class);
    }

    @Override
    public RhListaEmpleadoLoteTO existeEmpleadoEnPlantilla(RhRol rol) throws Exception {
        String motivo = "'ROLES DE PAGO '";
        String fechaHasta = UtilsDate.fechaFormatoString(rol.getRolHasta(), "YYYY-MM-dd");
        String sql = "SELECT * FROM recursoshumanos.fun_plantilla_empleados('" + rol.getRhEmpleado().getRhEmpleadoPK().getEmpEmpresa() + "', '" + fechaHasta + "', null, null, " + motivo + ") "
                + " WHERE pr_id = '" + rol.getRhEmpleado().getRhEmpleadoPK().getEmpId() + "'";
        return genericSQLDao.obtenerObjetoPorSql(sql, RhListaEmpleadoLoteTO.class);
    }

    @Override
    public boolean cambiarCedulaEmpleado(String empresa, String cedulaInconrrecta, String cedulaCorrecta) throws Exception {
        String sql = "SELECT * FROM  recursoshumanos.fun_reestructurar_empleado('" + empresa + "', '" + cedulaInconrrecta + "', '" + cedulaCorrecta + "')";
        String respuesta = (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
        return respuesta.equals("true");
    }

    @Override
    public RhEmpleadoTO getEmpleadoTO(String empresa, String empleadoId) throws Exception {
        String sql = "SELECT * FROM recursoshumanos.rh_empleado WHERE emp_empresa = '" + empresa + "' AND emp_id = '" + empleadoId + "'";
        return genericSQLDao.obtenerObjetoPorSql(sql, RhEmpleadoTO.class);
    }

    @Override
    public boolean getReconstruccionSaldosEmpleados(String empresa) throws Exception {
        String sql = "";
        if (empresa != null && !empresa.equals("")) {
            sql = "SELECT * FROM  recursoshumanos.recontruir_saldos_empleados('" + empresa + "')";
        } else {
            sql = "SELECT * FROM  recursoshumanos.recontruir_saldos_empleados()";
        }
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }
}
