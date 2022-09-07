package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.math.BigDecimal;
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
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleHorasExtrasLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhHorasExtras;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class HorasExtrasDaoImpl extends GenericDaoImpl<RhHorasExtras, Integer> implements HorasExtrasDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private ContableDao contableDao;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public List<RhListaDetalleHorasExtrasLoteTO> getRhDetalleHorasExtrasLoteTO(String empresa, String periodo, String tipo, String numero) throws Exception {
        String sql = "SELECT dbl_fecha, dbl_id, dbl_nombres, dbl_valor_50, dbl_valor_100, dbl_valor_extraordinarias_100, "
                + "dbl_forma_pago_detalle, dbl_documento, dbl_observaciones, id "
                + "FROM recursoshumanos.fun_detalle_horas_extras_por_lote(" + "'" + empresa + "', " + "'" + periodo + "', "
                + "'" + tipo + "', " + "'" + numero + "')";
        return genericSQLDao.obtenerPorSql(sql, RhListaDetalleHorasExtrasLoteTO.class);
    }

    @Override
    public List<RhListaSaldoConsolidadoHorasExtrasTO> getRhSaldoConsolidadoHorasExtrasTO(String empCodigo, String fechaHasta) throws Exception {
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        String sql = "SELECT * FROM recursoshumanos.fun_saldo_consolidado_horas_extras('" + empCodigo + "', " + "" + fechaHasta + ")";
        return genericSQLDao.obtenerPorSql(sql, RhListaSaldoConsolidadoHorasExtrasTO.class);
    }

    @Override
    public List<RhListaConsolidadoHorasExtrasTO> getRhListadoConsolidadoHorasExtrasTO(String empCodigo, String fechaDesde, String fechaHasta) throws Exception{
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        String sql = "SELECT * FROM recursoshumanos.fun_consolidado_horas_extras('" + empCodigo + "', " + "" + fechaDesde + ", " + "" + fechaHasta + ")";
        return genericSQLDao.obtenerPorSql(sql, RhListaConsolidadoHorasExtrasTO.class);
    }
    
    @Override
    public List<RhListaDetalleHorasExtrasTO> getRhListaDetalleHorasExtrasFiltradoTO(String empCodigo, String fechaDesde,
            String fechaHasta, String empCategoria, String empId, String parametro)
            throws Exception {
        empCategoria = empCategoria == null ? null : empCategoria.isEmpty() ? null : "'" + empCategoria + "'";
        empId = empId == null ? null : empId.isEmpty() ? null : "'" + empId + "'";
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        String sql = "";
        if (parametro.compareTo("PENDIENTES") == 0) {
            sql = "SELECT * FROM recursoshumanos.fun_detalle_horas_extras('" + empCodigo + "', " + "" + fechaDesde + ", "
                    + fechaHasta + ", " + empCategoria + ", " + empId + ") WHERE db_pendiente";
        } else if (parametro.compareTo("ANULADOS") == 0) {
            sql = "SELECT * FROM recursoshumanos.fun_detalle_horas_extras('" + empCodigo + "', " + "" + fechaDesde + ", "
                    + fechaHasta + ", " + empCategoria + ", " + empId + ") WHERE db_anulado";
        } else if (parametro.compareTo("REVERSADOS") == 0) {
            sql = "SELECT * FROM recursoshumanos.fun_detalle_horas_extras('" + empCodigo + "', " + "" + fechaDesde + ", "
                    + fechaHasta + ", " + empCategoria + ", " + empId + ") WHERE db_reversado ";
        } else if (parametro.compareTo("TODOS") == 0) {
            sql = "SELECT * FROM recursoshumanos.fun_detalle_horas_extras('" + empCodigo + "', " + "" + fechaDesde + ", "
                    + fechaHasta + ", " + empCategoria + ", " + empId + ") ";
        } else if (parametro.compareTo("") == 0) {
            sql = "SELECT * FROM recursoshumanos.fun_detalle_horas_extras('" + empCodigo + "', " + "" + fechaDesde + ", "
                    + fechaHasta + ", " + empCategoria + ", " + empId
                    + ") WHERE NOT db_pendiente AND NOT db_reversado AND NOT db_anulado ";
        }
        return genericSQLDao.obtenerPorSql(sql, RhListaDetalleHorasExtrasTO.class);
    }

    @Override
    public List<RhHorasExtrasTO> getRhHorasExtras(String empresa, String periodo, String numero) throws Exception {
        String sql = "SELECT emp_empresa, he_secuencial, he_concepto, "
                + "he_cantidad, he_precio, he_valor, "
                + "he_observacion, he_reversado, emp_id, " + "sec_codigo, pis_numero "
                + "FROM recursoshumanos.rh_horas_extras " + "WHERE rh_horas_extras.emp_empresa = '" + empresa + "' "
                + "AND rh_horas_extras.con_periodo = '" + periodo + "' " + "AND rh_horas_extras.con_tipo = 'C-BON' "
                + "AND rh_horas_extras.con_numero = '" + numero + "'";

        return genericSQLDao.obtenerPorSql(sql, RhHorasExtrasTO.class);
    }

    @Override
    public boolean insertarModificarRhHorasExtras(ConContable conContable, List<RhHorasExtras> listaRhHorasExtras, SisSuceso sisSuceso) throws Exception {
        if (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) {
            contableDao.buscarConteoUltimaNumeracion(conContable.getConContablePK());
        }

        sisSuceso.setSusClave(conContable.getConContablePK().getConPeriodo() + " " + conContable.getConContablePK().getConTipo() + " " + conContable.getConContablePK().getConNumero());
        sisSuceso.setSusDetalle("Horas Extras por " + conContable.getConObservaciones());

        BigDecimal cero = new BigDecimal("0.00");
        int horas_extrasValidos = 0;
        for (int i = 0; i < listaRhHorasExtras.size(); i++) {
            if (listaRhHorasExtras.get(i).getHeValor50().compareTo(cero) == 0
                    && listaRhHorasExtras.get(i).getHeValor100().compareTo(cero) == 0
                    && listaRhHorasExtras.get(i).getHeValorExtraordinaria100().compareTo(cero) == 0) {
                listaRhHorasExtras.remove(i);
                i--;
            } else {
                horas_extrasValidos++;
            }
        }

        if (horas_extrasValidos == 0) {
            return false;
        }
        List<RhHorasExtras> list = getListRhHorasExtras(conContable.getConContablePK());
        for (RhHorasExtras rhHorasExtras : listaRhHorasExtras) {
            if (rhHorasExtras.isHeAuxiliar() == false) {
                rhHorasExtras.setHeSecuencial(null);
            }
            if (rhHorasExtras.getPrdPiscina() == null || rhHorasExtras.getPrdPiscina().getPrdPiscinaPK().getPisNumero() == null || rhHorasExtras.getPrdPiscina().getPrdPiscinaPK().getPisNumero().compareToIgnoreCase("") == 0) {
                rhHorasExtras.setPrdPiscina(null);
            }
            rhHorasExtras.setHeObservacion(rhHorasExtras.getHeObservacion() == null ? null : rhHorasExtras.getHeObservacion().toUpperCase());
            rhHorasExtras.setHeAuxiliar(false);
            rhHorasExtras.setRhEmpleado(new RhEmpleado(rhHorasExtras.getRhEmpleado().getRhEmpleadoPK()));
            rhHorasExtras.setConContable(conContable);
        }

        conContable.setConConcepto(conContable.getConConcepto() == null ? null : conContable.getConConcepto().toUpperCase());
        conContable.setConDetalle(conContable.getConDetalle() == null ? null : conContable.getConDetalle().toUpperCase());
        conContable.setConObservaciones(conContable.getConObservaciones() == null ? null : conContable.getConObservaciones().toUpperCase());
        conContable.setConPendiente(true);

        contableDao.saveOrUpdate(conContable);

        if (list != null && !list.isEmpty()) {
            list.removeAll(listaRhHorasExtras);
            list.forEach((rhHorasExtras) -> {
                eliminar(rhHorasExtras);
            });
            saveOrUpdate(listaRhHorasExtras);
        } else {
            insertar(listaRhHorasExtras);
        }

        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<RhHorasExtras> getListRhHorasExtras(ConContablePK conContablePK) {
        return obtenerPorHql(
                "select horasExtras from RhHorasExtras horasExtras inner join horasExtras.conContable con inner join con.conContablePK conpk inner join horasExtras.rhEmpleado empl "
                + "where conpk.conEmpresa=?1 and conpk.conPeriodo=?2 and conpk.conTipo=?3 and conpk.conNumero=?4 order by empl.empApellidos||empl.empNombres",
                new Object[]{conContablePK.getConEmpresa(), conContablePK.getConPeriodo(), conContablePK.getConTipo(),
                    conContablePK.getConNumero()});
    }

    @Override
    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable) {
        String sql = "SELECT COUNT(he_secuencial)  FROM recursoshumanos.rh_horas_extras INNER JOIN recursoshumanos.rh_empleado "
                + "ON (rh_horas_extras.emp_empresa, rh_horas_extras.emp_id)=(rh_empleado.emp_empresa, rh_empleado.emp_id) "
                + "WHERE (con_empresa, con_periodo, con_tipo, con_numero)=('" + conContablePK.getConEmpresa() + "','"
                + conContablePK.getConPeriodo() + "','" + conContablePK.getConTipo() + "','"
                + conContablePK.getConNumero() + "') AND " + "('" + fechaContable
                + "' <= rh_empleado.emp_fecha_ultimo_sueldo);";
        int count = (int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
        return count == 0;
    }
}
