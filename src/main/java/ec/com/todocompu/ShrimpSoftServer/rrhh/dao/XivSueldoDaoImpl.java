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
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXivSueldoCalcularTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunXivSueldoConsultarTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhPreavisoAnticiposPrestamosSueldoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhEmpleado;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class XivSueldoDaoImpl extends GenericDaoImpl<RhXivSueldo, String> implements XivSueldoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private ContableDao contableDao;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public List<RhFunXivSueldoCalcularTO> getRhFunCalcularXivSueldo(String empresa, String sector, String desde,
            String hasta) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        String sql = "SELECT id,xiv_id,xiv_nombres,xiv_apellidos,xiv_genero,xiv_fecha_ingreso,xiv_cargo,xiv_total_ingresos,xiv_dias_laborados,xiv_valor_xiv_sueldo,xiv_categoria,xiv_cuenta,xiv_sector, xiv_salario_minimo_vital, false as estado "
                + "FROM recursoshumanos.fun_xiv_sueldo_calcular(" + empresa + ", " + sector + ", " + desde + ", "
                + hasta + ")";

        return genericSQLDao.obtenerPorSql(sql, RhFunXivSueldoCalcularTO.class);
    }

    @Override
    public List<RhFunXivSueldoConsultarTO> getRhFunConsultarXivSueldo(String empresa, String sector, String desde,
            String hasta) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        desde = desde == null || desde.compareTo("") == 0 ? null : "'" + desde + "'";
        hasta = hasta == null || hasta.compareTo("") == 0 ? null : "'" + hasta + "'";
        String sql = "SELECT * FROM recursoshumanos.fun_xiv_sueldo_consultar(" + empresa + ", " + sector + ", " + desde
                + ", " + hasta + ")";

        return genericSQLDao.obtenerPorSql(sql, RhFunXivSueldoConsultarTO.class);
    }

    @Override
    public List<RhPreavisoAnticiposPrestamosSueldoTO> getRhFunPreavisoXivs(String empresa, String fecha, String cuenta)
            throws Exception {
        String sql = "SELECT * FROM recursoshumanos.fun_preaviso_xiv_sueldo('" + empresa + "', '" + fecha + "', '"
                + cuenta + "')";
        return genericSQLDao.obtenerPorSql(sql, RhPreavisoAnticiposPrestamosSueldoTO.class);
    }

    @Override
    public boolean insertarModificarRhXivSueldo(ConContable conContable, List<RhXivSueldo> rhXivSueldos, SisSuceso sisSuceso) throws Exception {
        if (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) {
            contableDao.buscarConteoUltimaNumeracion(conContable.getConContablePK());
        }

        sisSuceso.setSusClave(conContable.getConContablePK().getConPeriodo() + " " + conContable.getConContablePK().getConTipo() + " " + conContable.getConContablePK().getConNumero());
        sisSuceso.setSusDetalle("Xiv Sueldo por " + conContable.getConObservaciones());

        BigDecimal cero = new BigDecimal("0.00");
        for (int i = 0; i < rhXivSueldos.size(); i++) {
            if (rhXivSueldos.get(i).getXivValor().compareTo(cero) == 0) {
                rhXivSueldos.remove(i);
                i--;
            }
        }
        List<RhXivSueldo> list = getListRhXivSueldo(conContable.getConContablePK());
        for (RhXivSueldo rhXivSueldo : rhXivSueldos) {
            if (rhXivSueldo.isXivAuxiliar() == false) {
                rhXivSueldo.setXivSecuencial(null);
            }
            rhXivSueldo.setXivDocumento(rhXivSueldo.getXivDocumento() == null ? null : rhXivSueldo.getXivDocumento().toUpperCase());
            rhXivSueldo.setXivObservaciones(rhXivSueldo.getXivObservaciones() == null ? null : rhXivSueldo.getXivObservaciones().toUpperCase());
            rhXivSueldo.setXivAuxiliar(false);
            rhXivSueldo.setRhEmpleado(new RhEmpleado(rhXivSueldo.getRhEmpleado().getRhEmpleadoPK()));
            rhXivSueldo.setConContable(conContable);
        }

        conContable.setConConcepto(conContable.getConConcepto() == null ? null : conContable.getConConcepto().toUpperCase());
        conContable.setConDetalle(conContable.getConDetalle() == null ? null : conContable.getConDetalle().toUpperCase());
        conContable.setConObservaciones(conContable.getConObservaciones() == null ? null : conContable.getConObservaciones().toUpperCase());
        conContable.setConPendiente(true);

        contableDao.saveOrUpdate(conContable);

        if (list != null && !list.isEmpty()) {
            list.removeAll(rhXivSueldos);
            list.forEach((rhXivSueldo) -> {
                eliminar(rhXivSueldo);
            });
            saveOrUpdate(rhXivSueldos);
        } else {
            insertar(rhXivSueldos);
        }

        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<RhXivSueldo> getListRhXivSueldo(ConContablePK conContablePK) {
        return obtenerPorHql(
                "select xivSueldo from RhXivSueldo xivSueldo inner join xivSueldo.conContable con inner join con.conContablePK conpk inner join xivSueldo.rhEmpleado empl "
                + "where conpk.conEmpresa=?1 and conpk.conPeriodo=?2 and conpk.conTipo=?3 and conpk.conNumero=?4 order by empl.empApellidos||empl.empNombres",
                new Object[]{conContablePK.getConEmpresa(), conContablePK.getConPeriodo(), conContablePK.getConTipo(),
                    conContablePK.getConNumero()});
    }

    @Override
    public boolean getPermisoAcciones(ConContablePK conContablePK, String fechaContable) {
        String sql = "SELECT COUNT(ant_secuencial)  FROM recursoshumanos.rh_xiv_sueldo INNER JOIN recursoshumanos.rh_empleado "
                + "ON (rh_xiv_sueldo.emp_empresa, rh_xiv_sueldo.emp_id)=(rh_empleado.emp_empresa, rh_empleado.emp_id) "
                + "WHERE (con_empresa, con_periodo, con_tipo, con_numero)=('" + conContablePK.getConEmpresa() + "','"
                + conContablePK.getConPeriodo() + "','" + conContablePK.getConTipo() + "','"
                + conContablePK.getConNumero() + "') AND " + "('" + fechaContable
                + "' <= rh_empleado.emp_fecha_ultimo_sueldo);";
        int count = (int) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
        return count == 0 ? true : false;
    }

}
