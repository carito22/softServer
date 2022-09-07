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
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhBonoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoBonosHorasExtrasTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaConsolidadoBonosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleBonosLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleBonosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaSaldoConsolidadoBonosTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhBono;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class BonoDaoImpl extends GenericDaoImpl<RhBono, Integer> implements BonoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private ContableDao contableDao;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public List<RhBonoTO> getRhBono(String empresa, String periodo, String numero) throws Exception {
        String sql = "SELECT emp_empresa, bono_secuencial, bono_concepto, "
                + "bono_cantidad, bono_precio, bono_valor, bono_deducible, "
                + "bono_observacion, bono_reversado, emp_id, " + "sec_codigo, pis_numero "
                + "FROM recursoshumanos.rh_bono " + "WHERE rh_bono.emp_empresa = '" + empresa + "' "
                + "AND rh_bono.con_periodo = '" + periodo + "' " + "AND rh_bono.con_tipo = 'C-BON' "
                + "AND rh_bono.con_numero = '" + numero + "'";

        return genericSQLDao.obtenerPorSql(sql, RhBonoTO.class);
    }

    @Override
    public List<RhListaDetalleBonosLoteTO> getRhDetalleBonosLoteTO(String empresa, String periodo, String tipo,
            String numero) throws Exception {
        String sql = "SELECT dbl_fecha, dbl_id, dbl_nombres, dbl_valor, "
                + "dbl_forma_pago_detalle, dbl_documento, dbl_observaciones, id "
                + "FROM recursoshumanos.fun_detalle_bonos_por_lote(" + "'" + empresa + "', " + "'" + periodo + "', "
                + "'" + tipo + "', " + "'" + numero + "')";

        return genericSQLDao.obtenerPorSql(sql, RhListaDetalleBonosLoteTO.class);
    }

    @Override
    public List<RhListaDetalleBonosTO> getRhDetalleBonosTO(String empCodigo, String fechaDesde, String fechaHasta,
            String empCategoria, String empId, String estadoDeducible) throws Exception {
        empCategoria = empCategoria.isEmpty() ? null : "'" + empCategoria + "'";
        empId = empId == null || empId.isEmpty() ? null : "'" + empId + "'";
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        String condicion = "";
        if (estadoDeducible.equals("TODOS")) {
            condicion = ";";
        } else {
            if (estadoDeducible.equals("DEDUCIBLE")) {
                condicion = "WHERE db_deducible;";
            } else {
                condicion = "WHERE NOT db_deducible;";
            }
        }
        String sql = "SELECT * FROM recursoshumanos.fun_detalle_bonos('" + empCodigo + "', " + "" + fechaDesde + ", "
                + fechaHasta + ", " + empCategoria + ", " + empId + ")" + condicion;

        return genericSQLDao.obtenerPorSql(sql, RhListaDetalleBonosTO.class);
    }

    @Override
    public List<RhListaDetalleBonosTO> getRhListaDetalleBonosFiltradoTO(String empCodigo, String fechaDesde,
            String fechaHasta, String empCategoria, String empId, String estadoDeducible, String parametro)
            throws Exception {
        empCategoria = empCategoria == null ? null : empCategoria.isEmpty() ? null : "'" + empCategoria + "'";
        empId = empId == null ? null : empId.isEmpty() ? null : "'" + empId + "'";
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        String sql = "";

        if (estadoDeducible.equals("TODOS")) {
            estadoDeducible = "";
        } else {
            if (estadoDeducible.equals("DEDUCIBLE")) {
                estadoDeducible = "db_deducible;";
            } else {
                estadoDeducible = "NOT db_deducible;";
            }
        }
        if (parametro.compareTo("PENDIENTES") == 0) {
            sql = "SELECT * FROM recursoshumanos.fun_detalle_bonos('" + empCodigo + "', " + "" + fechaDesde + ", "
                    + fechaHasta + ", " + empCategoria + ", " + empId + ") WHERE db_pendiente "
                    + (estadoDeducible.compareTo("") == 0 ? "" : "AND " + estadoDeducible);
        } else if (parametro.compareTo("ANULADOS") == 0) {
            sql = "SELECT * FROM recursoshumanos.fun_detalle_bonos('" + empCodigo + "', " + "" + fechaDesde + ", "
                    + fechaHasta + ", " + empCategoria + ", " + empId + ") WHERE db_anulado "
                    + (estadoDeducible.compareTo("") == 0 ? "" : "AND " + estadoDeducible);
        } else if (parametro.compareTo("REVERSADOS") == 0) {
            sql = "SELECT * FROM recursoshumanos.fun_detalle_bonos('" + empCodigo + "', " + "" + fechaDesde + ", "
                    + fechaHasta + ", " + empCategoria + ", " + empId + ") WHERE db_reversado "
                    + (estadoDeducible.compareTo("") == 0 ? "" : "AND " + estadoDeducible);
        } else if (parametro.compareTo("TODOS") == 0) {
            sql = "SELECT * FROM recursoshumanos.fun_detalle_bonos('" + empCodigo + "', " + "" + fechaDesde + ", "
                    + fechaHasta + ", " + empCategoria + ", " + empId + ") "
                    + (estadoDeducible.compareTo("") == 0 ? "" : "WHERE " + estadoDeducible);
        } else if (parametro.compareTo("") == 0) {
            sql = "SELECT * FROM recursoshumanos.fun_detalle_bonos('" + empCodigo + "', " + "" + fechaDesde + ", "
                    + fechaHasta + ", " + empCategoria + ", " + empId
                    + ") WHERE NOT db_pendiente AND NOT db_reversado AND NOT db_anulado "
                    + (estadoDeducible.compareTo("") == 0 ? "" : "AND " + estadoDeducible);
        }

        return genericSQLDao.obtenerPorSql(sql, RhListaDetalleBonosTO.class);
    }

    @Override
    public List<RhListaConsolidadoBonosTO> getRhConsolidadoBonosTO(String empCodigo, String fechaDesde,
            String fechaHasta) throws Exception {
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        String sql = "SELECT * FROM recursoshumanos.fun_consolidado_bonos('" + empCodigo + "', " + "" + fechaDesde
                + ", " + fechaHasta + ")";

        return genericSQLDao.obtenerPorSql(sql, RhListaConsolidadoBonosTO.class);
    }

    @Override
    public List<RhListaConsolidadoBonosHorasExtrasTO> listarConsolidadoBonosHorasExtras(String empCodigo, String fechaDesde, String fechaHasta) throws Exception {
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        String sql = "SELECT * FROM recursoshumanos.fun_consolidado_bonos_horas_extras('" + empCodigo + "', " + "" + fechaDesde
                + ", " + fechaHasta + ")";

        return genericSQLDao.obtenerPorSql(sql, RhListaConsolidadoBonosHorasExtrasTO.class);
    }

    @Override
    public List<RhListaSaldoConsolidadoBonosTO> getRhSaldoConsolidadoBonosTO(String empCodigo, String fechaHasta)
            throws Exception {
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        String sql = "SELECT * FROM recursoshumanos.fun_saldo_consolidado_bonos('" + empCodigo + "', " + "" + fechaHasta
                + ")";
        return genericSQLDao.obtenerPorSql(sql, RhListaSaldoConsolidadoBonosTO.class);
    }

    @Override
    public boolean insertarModificarRhBono(ConContable conContable, List<RhBono> listaRhBono, SisSuceso sisSuceso) throws Exception {
        if (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) {
            contableDao.buscarConteoUltimaNumeracion(conContable.getConContablePK());
        }

        sisSuceso.setSusClave(conContable.getConContablePK().getConPeriodo() + " " + conContable.getConContablePK().getConTipo() + " " + conContable.getConContablePK().getConNumero());
        sisSuceso.setSusDetalle("Bono por " + conContable.getConObservaciones());

        BigDecimal cero = new BigDecimal("0.00");
        int bonosValidos = 0;
        for (int i = 0; i < listaRhBono.size(); i++) {
            if (listaRhBono.get(i).getBonoValor().compareTo(cero) == 0) {
                listaRhBono.remove(i);
                i--;
            } else {
                bonosValidos++;
            }
        }

        if (bonosValidos == 0) {
            return false;
        }
        List<RhBono> list = getListRhBono(conContable.getConContablePK());
        for (RhBono rhBono : listaRhBono) {
            if (rhBono.isBonoAuxiliar() == false) {
                rhBono.setBonoSecuencial(null);
            }
            if (rhBono.getPrdPiscina() == null || rhBono.getPrdPiscina().getPrdPiscinaPK().getPisNumero() == null || rhBono.getPrdPiscina().getPrdPiscinaPK().getPisNumero().compareToIgnoreCase("") == 0) {
                rhBono.setPrdPiscina(null);
            }
            rhBono.setBonoObservacion(rhBono.getBonoObservacion() == null ? null : rhBono.getBonoObservacion().toUpperCase());
            rhBono.setBonoAuxiliar(false);
            rhBono.setRhEmpleado(new RhEmpleado(rhBono.getRhEmpleado().getRhEmpleadoPK()));
            rhBono.setConContable(conContable);
        }

        conContable.setConConcepto(conContable.getConConcepto() == null ? null : conContable.getConConcepto().toUpperCase());
        conContable.setConDetalle(conContable.getConDetalle() == null ? null : conContable.getConDetalle().toUpperCase());
        conContable.setConObservaciones(conContable.getConObservaciones() == null ? null : conContable.getConObservaciones().toUpperCase());
        conContable.setConPendiente(true);

        contableDao.saveOrUpdate(conContable);
        
        if (list != null && !list.isEmpty()) {
            list.removeAll(listaRhBono);
            list.forEach((rhBono) -> {
                eliminar(rhBono);
            });
            saveOrUpdate(listaRhBono);
        } else {
            insertar(listaRhBono);
        }

        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<RhBono> getListRhBono(ConContablePK conContablePK) {
        return obtenerPorHql(
                "select bono from RhBono bono inner join bono.conContable con inner join con.conContablePK conpk inner join bono.rhEmpleado empl "
                + "where conpk.conEmpresa=?1 and conpk.conPeriodo=?2 and conpk.conTipo=?3 and conpk.conNumero=?4 order by empl.empApellidos||empl.empNombres",
                new Object[]{conContablePK.getConEmpresa(), conContablePK.getConPeriodo(), conContablePK.getConTipo(),
                    conContablePK.getConNumero()});
    }

    @Override
    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable) {
        String sql = "SELECT COUNT(bono_secuencial)  FROM recursoshumanos.rh_bono INNER JOIN recursoshumanos.rh_empleado "
                + "ON (rh_bono.emp_empresa, rh_bono.emp_id)=(rh_empleado.emp_empresa, rh_empleado.emp_id) "
                + "WHERE (con_empresa, con_periodo, con_tipo, con_numero)=('" + conContablePK.getConEmpresa() + "','"
                + conContablePK.getConPeriodo() + "','" + conContablePK.getConTipo() + "','"
                + conContablePK.getConNumero() + "') AND " + "('" + fechaContable
                + "' <= rh_empleado.emp_fecha_ultimo_sueldo);";
        int count = (int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
        return count == 0 ? true : false;
    }
}
