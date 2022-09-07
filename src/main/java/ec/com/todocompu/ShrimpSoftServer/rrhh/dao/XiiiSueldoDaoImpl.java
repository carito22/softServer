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
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXiiiSueldoCalcularTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXiiiSueldoConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class XiiiSueldoDaoImpl extends GenericDaoImpl<RhXiiiSueldo, String> implements XiiiSueldoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private ContableDao contableDao;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoXiiis(String empresa, String fecha, String cuenta)
            throws Exception {
        String sql = "SELECT * FROM recursoshumanos.fun_preaviso_xiii_sueldo('" + empresa + "', '" + fecha + "', '"
                + cuenta + "')";

        return genericSQLDao.obtenerPorSql(sql, RhPreavisoAnticiposPrestamosSueldoTO.class);

    }

    @Override
    public List<RhFunXiiiSueldoCalcularTO> getRhFunCalcularXiiiSueldo(String empresa, String sector, String desde,
            String hasta) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        String sql = "SELECT id, xiii_id, xiii_nombres, xiii_apellidos, xiii_genero, "
                + "xiii_fecha_ingreso, xiii_cargo, xiii_total_ingresos, xiii_dias_laborados, "
                + "xiii_valor_xiii_sueldo, xiii_categoria, xiii_cuenta, xiii_sector, false as estado "
                + "FROM recursoshumanos.fun_xiii_sueldo_calcular(" + empresa + ", " + sector + ", " + desde + ", "
                + hasta + ")";

        return genericSQLDao.obtenerPorSql(sql, RhFunXiiiSueldoCalcularTO.class);
    }

    @Override
    public List<RhFunXiiiSueldoConsultarTO> getRhFunConsultarXiiiSueldo(String empresa, String sector, String desde,
            String hasta) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        desde = desde == null || desde.compareTo("") == 0 ? null : "'" + desde + "'";
        hasta = hasta == null || hasta.compareTo("") == 0 ? null : "'" + hasta + "'";
        String sql = "SELECT * FROM recursoshumanos.fun_xiii_sueldo_consultar(" + empresa + ", " + sector + ", " + desde
                + ", " + hasta + ");";

        return genericSQLDao.obtenerPorSql(sql, RhFunXiiiSueldoConsultarTO.class);
    }

    @Override
    public boolean insertarModificarRhXiiiSueldo(ConContable conContable, List<RhXiiiSueldo> rhXiiiSueldos, SisSuceso sisSuceso) throws Exception {
        if (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) {
            contableDao.buscarConteoUltimaNumeracion(conContable.getConContablePK());
        }
        sisSuceso.setSusClave(conContable.getConContablePK().getConPeriodo() + " " + conContable.getConContablePK().getConTipo() + " " + conContable.getConContablePK().getConNumero());
        sisSuceso.setSusDetalle("Xiii Sueldo por " + conContable.getConObservaciones());
        BigDecimal cero = new BigDecimal("0.00");
        for (int i = 0; i < rhXiiiSueldos.size(); i++) {
            if (rhXiiiSueldos.get(i).getXiiiValor().compareTo(cero) == 0) {
                rhXiiiSueldos.remove(i);
                i--;
            }
        }

        List<RhXiiiSueldo> list = getListRhXiiiSueldo(conContable.getConContablePK());
        for (RhXiiiSueldo rhXiiiSueldo : rhXiiiSueldos) {
            if (rhXiiiSueldo.isXiiiAuxiliar() == false) {
                rhXiiiSueldo.setXiiiSecuencial(null);
            }
            rhXiiiSueldo.setXiiiDocumento(rhXiiiSueldo.getXiiiDocumento() == null ? null : rhXiiiSueldo.getXiiiDocumento().toUpperCase());
            rhXiiiSueldo.setXiiiObservaciones(rhXiiiSueldo.getXiiiObservaciones() == null ? null : rhXiiiSueldo.getXiiiObservaciones().toUpperCase());
            rhXiiiSueldo.setXiiiAuxiliar(false);
            rhXiiiSueldo.setRhEmpleado(new RhEmpleado(rhXiiiSueldo.getRhEmpleado().getRhEmpleadoPK()));
            rhXiiiSueldo.setConContable(conContable);
        }

        conContable.setConConcepto(conContable.getConConcepto() == null ? null : conContable.getConConcepto().toUpperCase());
        conContable.setConDetalle(conContable.getConDetalle() == null ? null : conContable.getConDetalle().toUpperCase());
        conContable.setConObservaciones(conContable.getConObservaciones() == null ? null : conContable.getConObservaciones().toUpperCase());
        conContable.setConPendiente(true);

        contableDao.saveOrUpdate(conContable);
        if (list != null && !list.isEmpty()) {
            list.removeAll(rhXiiiSueldos);
            list.forEach((xhXiiiSueldo) -> {
                eliminar(xhXiiiSueldo);
            });
            saveOrUpdate(rhXiiiSueldos);
        } else {
            insertar(rhXiiiSueldos);
        }
        
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<RhXiiiSueldo> getListRhXiiiSueldo(ConContablePK conContablePK) {
        return obtenerPorHql(
                "select xiiiSueldo from RhXiiiSueldo xiiiSueldo inner join xiiiSueldo.conContable con inner join con.conContablePK conpk inner join xiiiSueldo.rhEmpleado empl "
                + "where conpk.conEmpresa=?1 and conpk.conPeriodo=?2 and conpk.conTipo=?3 and conpk.conNumero=?4 order by empl.empApellidos||empl.empNombres",
                new Object[]{conContablePK.getConEmpresa(), conContablePK.getConPeriodo(), conContablePK.getConTipo(),
                    conContablePK.getConNumero()});
    }

    @Override
    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable) {
        String sql = "SELECT COUNT(ant_secuencial)  FROM recursoshumanos.rh_xiii_sueldo INNER JOIN recursoshumanos.rh_empleado "
                + "ON (rh_xiii_sueldo.emp_empresa, rh_xiii_sueldo.emp_id)=(rh_empleado.emp_empresa, rh_empleado.emp_id) "
                + "WHERE (con_empresa, con_periodo, con_tipo, con_numero)=('" + conContablePK.getConEmpresa() + "','"
                + conContablePK.getConPeriodo() + "','" + conContablePK.getConTipo() + "','"
                + conContablePK.getConNumero() + "') AND " + "('" + fechaContable
                + "' <= rh_empleado.emp_fecha_ultimo_sueldo);";
        int count = (int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
        return count == 0;
    }

}
