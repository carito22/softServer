package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.ContableDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoAnticipoEmpleadoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesRRHH;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.RhPreavisoAnticiposPrestamosSueldoMachalaTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePK;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhAnticipoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleAnticiposLoteTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaDetalleAnticiposTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoGuayaquilTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoPichinchaTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhAnticipo;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoAnticipoEmpleado;

@Repository
public class AnticipoDaoImpl extends GenericDaoImpl<RhAnticipo, String> implements AnticipoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private ContableDao contableDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private SucesoAnticipoEmpleadoDao sucesoAnticipoEmpleadoDao;

    @Override
    public List<RhAnticipoTO> getRhAnticipo(String empresa, String periodo, String numero) throws Exception {
        String sql = "SELECT ant_secuencial, emp_empresa, ant_valor, ant_forma_pago, "
                + "ant_reversado, emp_id, sec_codigo " + "FROM recursoshumanos.rh_anticipo "
                + "WHERE rh_anticipo.emp_empresa = '" + empresa + "' " + "AND rh_anticipo.con_periodo = '" + periodo
                + "' " + "AND rh_anticipo.con_tipo = 'C-ANT' " + "AND rh_anticipo.con_numero = '" + numero + "'";

        return genericSQLDao.obtenerPorSql(sql, RhAnticipoTO.class);
    }

    @Override
    public List<RhListaDetalleAnticiposTO> getRhDetalleAnticiposTO(String empCodigo, String fechaDesde,
            String fechaHasta, String empCategoria, String empId, String formaPago) throws Exception {
        empCategoria = empCategoria == null ? null : empCategoria.isEmpty() ? null : "'" + empCategoria + "'";
        empId = empId == null ? null : empId.isEmpty() ? null : "'" + empId + "'";
        formaPago = formaPago == null ? null : formaPago.isEmpty() ? null : "'" + formaPago + "'";
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        String sql = "SELECT * FROM recursoshumanos.fun_detalle_anticipos('" + empCodigo + "', " + "" + fechaDesde
                + ", " + fechaHasta + ", " + empCategoria + ", " + empId + ", " + formaPago + ")";

        return genericSQLDao.obtenerPorSql(sql, RhListaDetalleAnticiposTO.class);
    }

    @Override
    public List<RhListaDetalleAnticiposTO> getRhDetalleAnticiposFiltradoTO(String empCodigo, String fechaDesde,
            String fechaHasta, String empCategoria, String empId, String formaPago, String parametro) throws Exception {
        empCategoria = empCategoria == null ? null : empCategoria.isEmpty() ? null : "'" + empCategoria + "'";
        empId = empId == null ? null : empId.isEmpty() ? null : "'" + empId + "'";
        boolean agrupar = parametro.equals("AGRUPADOS");
        formaPago = formaPago == null ? null : formaPago.isEmpty() ? null : "'" + formaPago + "'";
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        String sql = "";
        if (parametro.compareTo("PENDIENTES") == 0) {
            sql = "SELECT * FROM recursoshumanos.fun_detalle_anticipos('" + empCodigo + "', " + "" + fechaDesde + ", "
                    + fechaHasta + ", " + empCategoria + ", " + empId + ", " + formaPago + ", null) WHERE da_pendiente";
        } else if (parametro.compareTo("ANULADOS") == 0) {
            sql = "SELECT * FROM recursoshumanos.fun_detalle_anticipos('" + empCodigo + "', " + "" + fechaDesde + ", "
                    + fechaHasta + ", " + empCategoria + ", " + empId + ", " + formaPago + ", null) WHERE da_anulado";
        } else if (parametro.compareTo("REVERSADOS") == 0) {
            sql = "SELECT * FROM recursoshumanos.fun_detalle_anticipos('" + empCodigo + "', " + "" + fechaDesde + ", "
                    + fechaHasta + ", " + empCategoria + ", " + empId + ", " + formaPago + ", null) WHERE da_reverso";
        } else if (parametro.compareTo("AGRUPADOS") == 0) {
            sql = "SELECT * FROM recursoshumanos.fun_detalle_anticipos('" + empCodigo + "', " + "" + fechaDesde + ", "
                    + fechaHasta + ", " + empCategoria + ", " + empId + ", " + formaPago + ", '" + parametro + "')";
        } else if (parametro.compareTo("TODOS") == 0) {
            sql = "SELECT * FROM recursoshumanos.fun_detalle_anticipos('" + empCodigo + "', " + "" + fechaDesde + ", "
                    + fechaHasta + ", " + empCategoria + ", " + empId + ", " + formaPago + ", null)";
        } else if (parametro.compareTo("") == 0) {
            sql = "SELECT * FROM recursoshumanos.fun_detalle_anticipos('" + empCodigo + "', " + "" + fechaDesde + ", "
                    + fechaHasta + ", " + empCategoria + ", " + empId + ", " + formaPago
                    + ", null) WHERE NOT da_pendiente AND NOT da_reverso AND NOT da_anulado";
        }

        return genericSQLDao.obtenerPorSql(sql, RhListaDetalleAnticiposTO.class);
    }

    @Override
    public List<RhListaDetalleAnticiposLoteTO> getRhDetalleAnticiposLoteTO(String empresa, String periodo, String tipo,
            String numero) throws Exception {
        String sql = "SELECT dal_fecha, dal_id, dal_nombres, dal_valor, "
                + "dal_forma_pago_detalle, dal_documento, dal_observaciones, id "
                + "FROM recursoshumanos.fun_detalle_anticipos_por_lote(" + "'" + empresa + "', " + "'" + periodo + "', "
                + "'" + tipo + "', " + "'" + numero + "')";
        List<RhListaDetalleAnticiposLoteTO> lista = genericSQLDao.obtenerPorSql(sql,
                RhListaDetalleAnticiposLoteTO.class);
        return lista;
    }

    //bolibariano
    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoAnticiposBolivariano(String empresa, String fecha,
            String cuenta, String tipoPreAviso, String servicio, String sector, boolean sinCuenta) throws Exception {
        sector = sector != null ? sector : "";
        String sql = "SELECT * FROM recursoshumanos.fun_preavisos_bolivariano('" + empresa + "', '" + fecha + "','"
                + cuenta + "', '" + servicio + "','" + tipoPreAviso + "','" + sector + "', " + sinCuenta + ")";

        if (servicio != null && servicio.equals("RPA")) {
            sql = sql + " where pre_cuenta_numero is not null and pre_cuenta_numero != '';";
        }
        if (servicio != null && servicio.equals("TER")) {
            sql = sql + " where pre_cuenta_numero is null or pre_cuenta_numero = '';";
        }

        return genericSQLDao.obtenerPorSql(sql, RhPreavisoAnticiposPrestamosSueldoTO.class);

    }

    //pichincha
    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoPichinchaTO> getRhFunPreavisoAnticiposPichincha(String empresa,
            String fecha, String cuenta, String tipo, String banco, String sector) throws Exception {
        sector = sector != null ? sector : "";
        String sql = "SELECT * FROM recursoshumanos.fun_preavisos_pichincha_internacional('" + empresa + "', '" + fecha
                + "','" + cuenta + "', '" + tipo + "', '" + banco + "','" + sector + "')";

        return genericSQLDao.obtenerPorSql(sql, RhPreavisoAnticiposPrestamosSueldoPichinchaTO.class);
    }

    //machala
    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoMachalaTO> getRhFunPreavisoAnticiposMachala(String empresa,
            String fecha, String cuenta, String tipo, String banco, String sector) throws Exception {
        sector = sector != null ? sector : "";
        String sql = "SELECT * FROM recursoshumanos.fun_preavisos_machala('" + empresa + "', '" + fecha
                + "','" + cuenta + "', '" + tipo + "', '" + banco + "','" + sector + "')";

        return genericSQLDao.obtenerPorSql(sql, RhPreavisoAnticiposPrestamosSueldoMachalaTO.class);
    }

    //guayquil
    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoGuayaquilTO> getRhFunPreavisoAnticiposGuayaquil(String empresa, String fecha, String cuenta, String tipo, String banco, String sector) throws Exception {
        sector = sector != null ? sector : "";
        String sql = "SELECT * FROM recursoshumanos.fun_preavisos_guayaquil('" + empresa + "', '" + fecha + "','" + cuenta + "', '" + tipo + "', '" + banco + "','" + sector + "')";
        return genericSQLDao.obtenerPorSql(sql, RhPreavisoAnticiposPrestamosSueldoGuayaquilTO.class);
    }

    @Override
    public boolean insertarModificarRhAnticipo(ConContable conContable, List<RhAnticipo> rhAnticipos, SisSuceso sisSuceso) throws Exception {
        if (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) {
            contableDao.buscarConteoUltimaNumeracion(conContable.getConContablePK());
        }

        sisSuceso.setSusClave(conContable.getConContablePK().getConPeriodo() + " " + conContable.getConContablePK().getConTipo() + " " + conContable.getConContablePK().getConNumero());
        sisSuceso.setSusDetalle("Anticipo por " + conContable.getConObservaciones());

        BigDecimal cero = new BigDecimal("0.00");
        int anticiposValidos = 0;
        for (int i = 0; i < rhAnticipos.size(); i++) {
            if (rhAnticipos.get(i).getAntValor().compareTo(cero) == 0) {
                rhAnticipos.remove(i);
                i--;
            } else {
                anticiposValidos++;
            }
        }

        if (anticiposValidos == 0) {
            return false;
        }
        List<RhAnticipo> list = getListRhAnticipo(conContable.getConContablePK());
        for (RhAnticipo rhAnticipo : rhAnticipos) {
            if (rhAnticipo.isAntAuxiliar() == false) {
                rhAnticipo.setAntSecuencial(null);
            }
            rhAnticipo.setAntDocumento(rhAnticipo.getAntDocumento() == null ? null : rhAnticipo.getAntDocumento().toUpperCase());
            rhAnticipo.setAntObservaciones(rhAnticipo.getAntObservaciones() == null ? null : rhAnticipo.getAntObservaciones().toUpperCase());
            rhAnticipo.setAntAuxiliar(false);
            rhAnticipo.setRhEmpleado(new RhEmpleado(rhAnticipo.getRhEmpleado().getRhEmpleadoPK()));
            rhAnticipo.setConContable(conContable);
        }

        conContable.setConConcepto(conContable.getConConcepto() == null ? null : conContable.getConConcepto().toUpperCase());
        conContable.setConDetalle(conContable.getConDetalle() == null ? null : conContable.getConDetalle().toUpperCase());
        conContable.setConObservaciones(conContable.getConObservaciones() == null ? null : conContable.getConObservaciones().toUpperCase());
        conContable.setConPendiente(true);

        contableDao.saveOrUpdate(conContable);

        if (list != null && !list.isEmpty()) {
            list.removeAll(rhAnticipos);
            list.forEach((rhAnticipo) -> {
                eliminar(rhAnticipo);
            });
            saveOrUpdate(rhAnticipos);
        } else {
            insertar(rhAnticipos);
        }

        sucesoDao.insertar(sisSuceso);
        /////////////////////////////i8nsertar suceso//////////////////////////
        for (RhAnticipo rhAnticipo : rhAnticipos) {
            SisSucesoAnticipoEmpleado sucesoAnt = new SisSucesoAnticipoEmpleado();
            RhAnticipo copia = ConversionesRRHH.convertirRhAnticipo_RhAnticipo(rhAnticipo);
            String json = UtilsJSON.objetoToJson(copia);
            sucesoAnt.setSusJson(json);
            sucesoAnt.setSisSuceso(sisSuceso);
            sucesoAnt.setRhAnticipo(copia);
            sucesoAnticipoEmpleadoDao.insertar(sucesoAnt);
        }

        return true;
    }

    @Override
    public List<RhAnticipo> getListRhAnticipo(ConContablePK conContablePK) {
        return obtenerPorHql(
                "select ant from RhAnticipo ant inner join ant.conContable con inner join con.conContablePK conpk inner join ant.rhEmpleado empl "
                + "where conpk.conEmpresa=?1 and conpk.conPeriodo=?2 and conpk.conTipo=?3 and conpk.conNumero=?4 order by empl.empApellidos||empl.empNombres",
                new Object[]{conContablePK.getConEmpresa(), conContablePK.getConPeriodo(), conContablePK.getConTipo(),
                    conContablePK.getConNumero()});
    }

    @Override
    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable) {
        String sql = "SELECT COUNT(ant_secuencial)  FROM recursoshumanos.rh_anticipo INNER JOIN recursoshumanos.rh_empleado "
                + "ON (rh_anticipo.emp_empresa, rh_anticipo.emp_id)=(rh_empleado.emp_empresa, rh_empleado.emp_id) "
                + "WHERE (con_empresa, con_periodo, con_tipo, con_numero)=('" + conContablePK.getConEmpresa() + "','"
                + conContablePK.getConPeriodo() + "','" + conContablePK.getConTipo() + "','"
                + conContablePK.getConNumero() + "') AND " + "('" + fechaContable
                + "' <= rh_empleado.emp_fecha_ultimo_sueldo);";
        int count = (int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
        return count == 0 ? true : false;
    }
}
