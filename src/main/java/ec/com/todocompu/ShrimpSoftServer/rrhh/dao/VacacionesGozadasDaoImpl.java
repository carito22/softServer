package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacacionesGozadas;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacacionesGozadasDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacacionesGozadasNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhVacacionesGozadasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class VacacionesGozadasDaoImpl extends GenericDaoImpl<RhVacacionesGozadas, RhVacacionesGozadasPK> implements VacacionesGozadasDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private GenericoDao<RhVacacionesGozadasNumeracion, String> genericoDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericoDao<RhVacacionesGozadasDatosAdjuntos, Integer> rhVacacionesGozadasDatosAdjuntosDao;

    @Override
    public List<RhVacacionesGozadas> listarRhVacacionesGozadas(String empCodigo, String empId, String sector, String fechaDesde, String fechaHasta) throws Exception {
        empId = empId == null || empId.compareToIgnoreCase("") == 0 ? null : "'" + empId + "'";
        sector = sector == null || sector.compareToIgnoreCase("") == 0 ? null : "'" + sector + "'";
        fechaDesde = fechaDesde == null ? null : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? null : "'" + fechaHasta + "'";
        empCodigo = empCodigo == null ? null : "'" + empCodigo + "'";
        String pFechaDesde = fechaDesde;
        String pFechaHasta = fechaHasta;
        String sql = "SELECT * from recursoshumanos.fun_listado_vacaciones_gozadas(" + empCodigo + ", " + "" + sector + ", "
                + empId + ", " + pFechaDesde + ", " + pFechaHasta + ")";
        return genericSQLDao.obtenerPorSql(sql, RhVacacionesGozadas.class);

    }
    
    @Override
    public List<RhVacacionesGozadas> listarRhVacacionesGozadasSinContable(String empCodigo, String empId, String sector, String fechaDesde, String fechaHasta) throws Exception {
        empId = empId == null || empId.compareToIgnoreCase("") == 0 ? null : "'" + empId + "'";
        sector = sector == null || sector.compareToIgnoreCase("") == 0 ? null : "'" + sector + "'";
        fechaDesde = fechaDesde == null ? null : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? null : "'" + fechaHasta + "'";
        empCodigo = empCodigo == null ? null : "'" + empCodigo + "'";
        String pFechaDesde = fechaDesde;
        String pFechaHasta = fechaHasta;
        String sql = "SELECT * from recursoshumanos.fun_listado_vacaciones_gozadas(" + empCodigo + ", " + "" + sector + ", "
                + empId + ", " + pFechaDesde + ", " + pFechaHasta + ") WHERE con_numero is null;";
        return genericSQLDao.obtenerPorSql(sql, RhVacacionesGozadas.class);

    }

    @Override
    public String buscarFechaRhVacaciones(String empCodigo, String empId) throws Exception {
        String sql = "SELECT rh_vacaciones_gozadas.vac_hasta fecha_ultimo_sueldo "
                + "FROM recursoshumanos.rh_vacaciones_gozadas WHERE (emp_empresa = '"
                + empCodigo + "') AND (emp_id = '" + empId + "') "
                + "ORDER BY vac_hasta DESC LIMIT 1";
        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public void insertarRhVacacionesGozadas(RhVacacionesGozadas rhVacaciones, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        genericoDao.actualizar(new RhVacacionesGozadasNumeracion(rhVacaciones.getRhVacacionesGozadasPK().getVacEmpresa(), rhVacaciones.getRhVacacionesGozadasPK().getVacNumero()));
        insertar(rhVacaciones);
    }

    @Override
    public void modificarRhVacacionesGozadas(RhVacacionesGozadas rhVacaciones, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        actualizar(rhVacaciones);
    }

    @Override
    public void eliminarRhVacacionesGozadas(RhVacacionesGozadas rhVacaciones, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        eliminar(rhVacaciones);
    }

    @Override
    public String getInvProximaNumeracion(String empresa) throws Exception {
        String sql = "SELECT vac_numero FROM recursoshumanos.rh_vacaciones_gozadas_numeracion WHERE vac_empresa = '" + empresa + "'";
        String consulta = (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
        if (consulta != null) {
        } else {
            consulta = "000000";
        }
        int numeracion = Integer.parseInt(consulta);
        String rellenarConCeros = "";
        do {
            numeracion++;
            int numeroCerosAponer = 6 - String.valueOf(numeracion).trim().length();
            rellenarConCeros = "";
            for (int i = 0; i < numeroCerosAponer; i++) {
                rellenarConCeros = rellenarConCeros + "0";
            }
            consulta = rellenarConCeros + numeracion;
        } while (obtenerPorId(RhVacacionesGozadas.class, new RhVacacionesGozadasPK(empresa, consulta)) != null);
        return consulta;
    }

    @Override
    public String eliminarUltimaVacacionesGozadas(String empresa, String numero, SisSuceso sisSuceso) throws Exception {
        String sql = "SELECT * FROM recursoshumanos.fun_eliminar_vacaciones_gozadas('" + empresa + "', '" + numero + "')";
        String respuesta = "";
        try {
            String respuestaFuncion = (String) genericSQLDao.obtenerObjetoPorSql(sql);
            if (respuestaFuncion.substring(0, 1).equalsIgnoreCase("T")) {
                sucesoDao.insertar(sisSuceso);
                respuesta = "t";
            } else {
                respuesta = respuesta + ". N: " + sisSuceso.getSusClave();
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            respuesta = e.getMessage() + ". N: " + sisSuceso.getSusClave();
        }
        return respuesta;
    }

    @Override
    public void anularRestaurarRhVacacionesGozadas(RhVacacionesGozadasPK pk, boolean anularRestaurar, SisSuceso sisSuceso) {
        String sql = "UPDATE recursoshumanos.rh_vacaciones_gozadas SET vac_anulado=" + anularRestaurar
                + " WHERE vac_empresa='" + pk.getVacEmpresa() + "' and vac_numero='" + pk.getVacNumero() + "';";
        genericSQLDao.ejecutarSQL(sql);
    }

    @Override
    public List<RhVacacionesGozadasDatosAdjuntos> getAdjuntosContable(RhVacacionesGozadasPK pk) {
        String sql = "select * from recursoshumanos.rh_vacaciones_gozadas_datos_adjuntos where "
                + "vac_empresa = '" + pk.getVacEmpresa()
                + "' and vac_numero = '" + pk.getVacNumero() + "' ";
        return genericSQLDao.obtenerPorSql(sql, RhVacacionesGozadasDatosAdjuntos.class);
    }

    @Override
    public boolean insertarImagen(RhVacacionesGozadasDatosAdjuntos rhVacacionesGozadasDatosAdjuntos) {
        rhVacacionesGozadasDatosAdjuntosDao.insertar(rhVacacionesGozadasDatosAdjuntos);
        return true;
    }

    @Override
    public boolean eliminarImagen(RhVacacionesGozadasDatosAdjuntos rhVacacionesGozadasDatosAdjuntos) {
        rhVacacionesGozadasDatosAdjuntosDao.eliminar(rhVacacionesGozadasDatosAdjuntos);
        return true;
    }

}
