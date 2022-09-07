package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesRRHH;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFormulario107TO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunFormulario107TO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhFunFormulario107_2013TO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhFormulario107;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhFormulario107PK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.Date;

@Repository
public class Formulario107DaoImpl extends GenericDaoImpl<RhFormulario107, RhFormulario107PK>
        implements Formulario107Dao {

    @Autowired
    private SucesoDao sucesoDao;

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public RhFormulario107 getRhFormulario107(String empresa, String anio, String empleadoID) throws Exception {
        return obtenerPorId(RhFormulario107.class, new RhFormulario107PK(empresa, anio, empleadoID));
    }

    @Override
    public RhFormulario107TO getRhFormulario107TO(String empresa, String anio, String empleadoID) throws Exception {
        return ConversionesRRHH
                .convertirRhFormulario107_RhFormulario107TO(getRhFormulario107(empresa, anio, empleadoID));
    }

    @Override
    public boolean insertarRhFormulario107(RhFormulario107 rhFormulario107, SisSuceso sisSuceso) throws Exception {
        insertar(rhFormulario107);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<RhFunFormulario107TO> getRhFunFormulario107(String empresa, String desde, String hasta,
            Character status) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        String condicion = "";
        if (status == 'T') {
            condicion = "WHERE TRUE";
        }
        if (status == 'A') {
            condicion = "WHERE NOT f107_empleado_inactivo";
        }
        if (status == 'I') {
            condicion = "WHERE f107_empleado_inactivo";
        }
        String sql = "SELECT f107_id, f107_nombres, f107_sueldo, f107_bonos, "
                + "f107_xiii_sueldo, f107_xiv_sueldo, f107_fondo_reserva, "
                + "f107_salario_digno, f107_utilidades, f107_desahucio, "
                + "f107_iess, f107_vivienda, f107_salud, f107_educacion, "
                + "f107_alimentacion, f107_vestuario, f107_rebaja_discapacitado, "
                + "f107_rebaja_tercera_edad, f107_subtotal, f107_meses_trabajados, "
                + "f107_otros_ingresos, f107_otras_deducciones, f107_otras_rebajas, "
                + "f107_base_imponible, f107_impuesto_causado, f107_valor_retenido, "
                + "f107_numero_retenciones, f107_empleado_inactivo " + "FROM recursoshumanos.fun_formulario_107("
                + empresa + ", " + desde + ", " + hasta + ", null)" + " " + condicion;

        return genericSQLDao.obtenerPorSql(sql, RhFunFormulario107TO.class);
    }

    @Override
    public List<RhFunFormulario107_2013TO> getRhFunFormulario107_2013(String empresa, String desde, String hasta,
            Character status) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";

        String condicion = "";
        if (status == 'T') {
            condicion = "WHERE TRUE";
        }
        if (status == 'A') {
            condicion = "WHERE NOT f107_empleado_inactivo";
        }
        if (status == 'I') {
            condicion = "WHERE f107_empleado_inactivo";
        }
        String sql = "SELECT f107_tipo ,f107_id ,f107_apellidos ,f107_nombres ,f107_establecimiento ,f107_residencia_tipo ,f107_residencia_pais ,"
                + "f107_convenio ,f107_discapacidad_tipo ,f107_discapacidad_porcentaje ,f107_discapacidad_id_tipo ,f107_discapacidad_id_numero ,"
                + "f107_sueldo ,f107_bonos ,f107_utilidades ,f107_sueldo_otros_empleadores ,f107_impuesto_asumido ,f107_xiii_sueldo ,"
                + "f107_xiv_sueldo ,f107_fondo_reserva ,f107_salario_digno ,f107_desahucio ,f107_subtotal  ,f107_salario_neto ,f107_iess ,"
                + "f107_iess_otros_empleadores ,f107_vivienda ,f107_salud ,f107_educacion ,f107_alimentacion ,f107_vestuario ,f107_rebaja_discapacitado ,"
                + "f107_rebaja_tercera_edad ,f107_base_imponible ,f107_impuesto_causado ,f107_impuesto_asumido_otros_empleadores ,"
                + "f107_impuesto_asumido_este_empleador ,f107_valor_retenido ,f107_empleado_inactivo,f107_turismo, id  "
                + "FROM recursoshumanos.fun_formulario_107_2013(" + empresa + ", " + desde + ", " + hasta + ", null)"
                + " " + condicion;

        return genericSQLDao.obtenerPorSql(sql, RhFunFormulario107_2013TO.class);
    }

    @Override
    public RhFormulario107TO getRhFormulario107ConsultaTO(String empresa, String desde, String hasta, String empleadoID)
            throws Exception {
        String sql = "SELECT * " + "FROM recursoshumanos.fun_formulario_107('" + empresa + "', '" + desde + "', '"
                + hasta + "', '" + empleadoID + "')";

        return genericSQLDao.obtenerObjetoPorSql(sql, RhFormulario107TO.class);
    }

    @Override
    public List<RhFunFormulario107_2013TO> getRhFunFormulario107(String empresa, String desde, String hasta, Character status, String sector) throws Exception {
        empresa = empresa == null ? empresa : "'" + empresa + "'";
        int anioHasta = UtilsDate.getNumeroAnio(hasta, "yyyy-MM-dd");
        desde = desde == null ? desde : "'" + desde + "'";
        hasta = hasta == null ? hasta : "'" + hasta + "'";
        sector = sector == null ? sector : "'" + sector + "'";
        String anio = "2019";
        if (anioHasta >= 2019) {
            anio = anioHasta + "";
        }

        String condicion = "";
        if (status == 'T') {
            condicion = "WHERE TRUE";
        }
        if (status == 'A') {
            condicion = "WHERE NOT f107_empleado_inactivo";
        }
        if (status == 'I') {
            condicion = "WHERE f107_empleado_inactivo";
        }
        String sql = "SELECT f107_tipo ,f107_id ,f107_apellidos ,f107_nombres ,f107_establecimiento ,f107_residencia_tipo ,f107_residencia_pais ,"
                + "f107_convenio ,f107_discapacidad_tipo ,f107_discapacidad_porcentaje ,f107_discapacidad_id_tipo ,f107_discapacidad_id_numero ,"
                + "f107_sueldo ,f107_bonos ,f107_utilidades ,f107_sueldo_otros_empleadores ,f107_impuesto_asumido ,f107_xiii_sueldo ,"
                + "f107_xiv_sueldo ,f107_fondo_reserva ,f107_salario_digno ,f107_desahucio ,f107_subtotal  ,f107_salario_neto ,f107_iess ,"
                + "f107_iess_otros_empleadores ,f107_vivienda ,f107_salud ,f107_educacion ,f107_alimentacion ,f107_vestuario ,f107_rebaja_discapacitado ,"
                + "f107_rebaja_tercera_edad ,f107_base_imponible ,f107_impuesto_causado ,f107_impuesto_asumido_otros_empleadores ,"
                + "f107_impuesto_asumido_este_empleador ,f107_valor_retenido ,f107_empleado_inactivo,f107_turismo, id  "
                + "FROM recursoshumanos.fun_formulario_107_" + anio + "(" + empresa + ", " + sector + ", " + desde + ", " + hasta + ", null)"
                + " " + condicion;

        return genericSQLDao.obtenerPorSql(sql, RhFunFormulario107_2013TO.class);
    }

}
