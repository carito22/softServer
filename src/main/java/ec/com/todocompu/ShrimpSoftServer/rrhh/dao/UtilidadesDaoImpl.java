package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.ContableDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunUtilidadesCalcularTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunUtilidadesConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleUtilidadesLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhUtilidadesPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidades;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class UtilidadesDaoImpl extends GenericDaoImpl<RhUtilidades, String> implements UtilidadesDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private ContableDao contableDao;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public RhUtilidadesPeriodoTO buscarRhUtilidadesPeriodoTO(String descripcion) throws Exception {
        descripcion = descripcion == null ? descripcion : "'" + descripcion + "'";
        String sql = "SELECT * FROM recursoshumanos.rh_utilidades_periodo WHERE (uti_descripcion = " + descripcion
                + ");";

        return genericSQLDao.obtenerObjetoPorSql(sql, RhUtilidadesPeriodoTO.class);
    }

    @Override
    public List<RhFunUtilidadesCalcularTO> getRhFunCalcularUtilidades(String empresa, String sector, String desde,
            String hasta, Integer totalDias, Integer totalCargas, BigDecimal totalPagar) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        String sql = "SELECT uti_id, uti_nombres, uti_apellidos, uti_genero, uti_fecha_ingreso, uti_fecha_salida, uti_cargo, "
                + "uti_cargas_familiares, uti_dias_laborados, uti_valor_personal, uti_valor_cargas, uti_valor_utilidades,"
                + "uti_valor_sueldos, uti_valor_bonos, uti_valor_xiii, uti_valor_xiv, uti_valor_fondo_reserva, uti_valor_salario_digno,"
                + "uti_valor_utilidad_anio_anterior, uti_valor_impuesto, uti_categoria, uti_cuenta, uti_sector, false as estado, id "
                + "FROM recursoshumanos.fun_utilidades_calcular(" + empresa + ", " + sector + ", " + desde + ", "
                + hasta + ", " + totalDias + ", " + totalCargas + ", " + totalPagar + ")";

        return genericSQLDao.obtenerPorSql(sql, RhFunUtilidadesCalcularTO.class);
    }

    @Override
    public List<RhListaDetalleUtilidadesLoteTO> getRhDetalleUtilidadesLoteTO(String empresa, String periodo, String tipo,
            String numero) throws Exception {
        String sql = "SELECT util_fecha, util_id, util_nombres, util_valor, util_impuesto_renta, "
                + "util_forma_pago_detalle, util_documento, util_observaciones, id "
                + "FROM recursoshumanos.fun_detalle_utilidades_por_lote(" + "'" + empresa + "', " + "'" + periodo + "', "
                + "'" + tipo + "', " + "'" + numero + "')";
        List<RhListaDetalleUtilidadesLoteTO> lista = genericSQLDao.obtenerPorSql(sql,
                RhListaDetalleUtilidadesLoteTO.class);
        return lista;
    }

    @Override
    public List<RhFunUtilidadesConsultarTO> getRhFunConsultarUtilidades(String empresa, String sector, String desde,
            String hasta) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        String sql = "SELECT * FROM recursoshumanos.fun_utilidades_consultar(" + empresa + ", " + sector + ", " + desde
                + ", " + hasta + ")";

        return genericSQLDao.obtenerPorSql(sql, RhFunUtilidadesConsultarTO.class);
    }

    @Override
    public boolean insertarModificarRhUtilidades(ConContable conContable, List<RhUtilidades> rhUtilidades, SisSuceso sisSuceso) throws Exception {
        if (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) {
            contableDao.buscarConteoUltimaNumeracion(conContable.getConContablePK());
        }

        sisSuceso.setSusClave(conContable.getConContablePK().getConPeriodo() + " " + conContable.getConContablePK().getConTipo() + " " + conContable.getConContablePK().getConNumero());
        sisSuceso.setSusDetalle("Utilidades por " + conContable.getConObservaciones());

        BigDecimal cero = new BigDecimal("0.00");
        for (int i = 0; i < rhUtilidades.size(); i++) {
            if (rhUtilidades.get(i).getUtiValorTotal().compareTo(cero) == 0) {
                rhUtilidades.remove(i);
                i--;
            }
        }

        List<RhUtilidades> list = getListRhUtilidades(conContable.getConContablePK());
        for (RhUtilidades rhUtilidad : rhUtilidades) {
            if (rhUtilidad.isUtiAuxiliar() == false) {
                rhUtilidad.setUtiSecuencial(null);
            }
            if (rhUtilidad.getUtiImpuestoRenta() == null) {
                rhUtilidad.setUtiImpuestoRenta(BigDecimal.ZERO);
            }
            rhUtilidad.setUtiDocumento(rhUtilidad.getUtiDocumento() == null ? null : rhUtilidad.getUtiDocumento().toUpperCase());
            rhUtilidad.setUtiObservaciones(rhUtilidad.getUtiObservaciones() == null ? null : rhUtilidad.getUtiObservaciones().toUpperCase());
            rhUtilidad.setUtiAuxiliar(false);
            rhUtilidad.setRhEmpleado(new RhEmpleado(rhUtilidad.getRhEmpleado().getRhEmpleadoPK()));
            rhUtilidad.setConContable(conContable);
        }

        conContable.setConConcepto(conContable.getConConcepto() == null ? null : conContable.getConConcepto().toUpperCase());
        conContable.setConDetalle(conContable.getConDetalle() == null ? null : conContable.getConDetalle().toUpperCase());
        conContable.setConObservaciones(conContable.getConObservaciones() == null ? null : conContable.getConObservaciones().toUpperCase());
        conContable.setConPendiente(true);

        contableDao.saveOrUpdate(conContable);

        if (list != null && !list.isEmpty()) {
            list.removeAll(rhUtilidades);
            list.forEach((rhUtilidad) -> {
                eliminar(rhUtilidad);
            });
            saveOrUpdate(rhUtilidades);
        } else {
            insertar(rhUtilidades);
        }

        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<RhUtilidades> getListRhUtilidades(ConContablePK conContablePK) {
        return obtenerPorHql(
                "select uti from RhUtilidades uti inner join uti.conContable con inner join con.conContablePK conpk inner join uti.rhEmpleado empl "
                + "where conpk.conEmpresa=?1 and conpk.conPeriodo=?2 and conpk.conTipo=?3 and conpk.conNumero=?4 order by empl.empApellidos||empl.empNombres",
                new Object[]{conContablePK.getConEmpresa(), conContablePK.getConPeriodo(), conContablePK.getConTipo(),
                    conContablePK.getConNumero()});
    }

    @Override
    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable) {
        String sql = "SELECT COUNT(ant_secuencial)  FROM recursoshumanos.rh_utilidades INNER JOIN recursoshumanos.rh_empleado "
                + "ON (rh_utilidades.emp_empresa, rh_utilidades.emp_id)=(rh_empleado.emp_empresa, rh_empleado.emp_id) "
                + "WHERE (con_empresa, con_periodo, con_tipo, con_numero)=('" + conContablePK.getConEmpresa() + "','"
                + conContablePK.getConPeriodo() + "','" + conContablePK.getConTipo() + "','"
                + conContablePK.getConNumero() + "') AND " + "('" + fechaContable
                + "' <= rh_empleado.emp_fecha_ultimo_sueldo);";
        int count = (int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
        return count == 0;
    }

}
