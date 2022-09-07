package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.ConversionesAnexos;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxConceptoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxConcepto;

@Repository
public class ConceptoDaoImpl extends GenericDaoImpl<AnxConcepto, Integer> implements ConceptoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public AnxConceptoTO getBuscarAnexoConceptoTO(String fechaRetencion, String conceptoCodigo) throws Exception {
        String sql = "SELECT con_secuencial, con_codigo, con_concepto, con_porcentaje as con_ingresa_porcentaje, con_fecha_inicio, con_fecha_fin"
                + " FROM anexo.anx_concepto "
                + "WHERE con_codigo = '" + conceptoCodigo + "'"
                + " AND CASE WHEN con_fecha_fin IS NULL THEN (('" + fechaRetencion + "') >= "
                + "con_fecha_inicio) ELSE (('" + fechaRetencion + "') >= con_fecha_inicio AND ('" + fechaRetencion
                + "') " + "<= con_fecha_fin) END";
        List<AnxConceptoTO> conceptos = genericSQLDao.obtenerPorSql(sql, AnxConceptoTO.class);
        return conceptos != null && !conceptos.isEmpty() ? conceptos.get(0) : null;
    }

    @Override
    public AnxConcepto obtenerAnexo(String fechaRetencion, String conceptoCodigo) throws Exception {
        String sql = "SELECT * "
                + " FROM anexo.anx_concepto "
                + "WHERE con_codigo = '" + conceptoCodigo + "'"
                + " AND CASE WHEN con_fecha_fin IS NULL THEN (('" + fechaRetencion + "') >= "
                + "con_fecha_inicio) ELSE (('" + fechaRetencion + "') >= con_fecha_inicio AND ('" + fechaRetencion
                + "') " + "<= con_fecha_fin) END";
        List<AnxConcepto> conceptos = genericSQLDao.obtenerPorSql(sql, AnxConcepto.class);
        return conceptos != null && !conceptos.isEmpty() ? conceptos.get(0) : null;
    }

    @Override
    public List<AnxConceptoTO> getAnexoConceptoTO() throws Exception {
        List<AnxConceptoTO> listaAnxConceptoTO = new ArrayList<AnxConceptoTO>(1);
        List<AnxConcepto> listaAnxConcepto = obtenerTodos(AnxConcepto.class);
        if (!listaAnxConcepto.isEmpty()) {
            for (int i = 0; i < listaAnxConcepto.size(); i++) {
                listaAnxConceptoTO.add(ConversionesAnexos.convertirAnxConcepto_AnxConceptoTO(listaAnxConcepto.get(i)));

            }
        }
        return listaAnxConceptoTO;
    }

    @Override
    public List<AnxConceptoComboTO> getListaAnxConceptoTO() throws Exception {
        String fechaRetencion = UtilsValidacion.fechaSistema();
        String sql = "SELECT con_codigo, con_concepto, con_porcentaje, con_ingresa_porcentaje "
                + "FROM anexo.anx_concepto WHERE CASE WHEN con_fecha_fin IS NULL THEN (('" + fechaRetencion + "') >= "
                + "con_fecha_inicio) ELSE (('" + fechaRetencion + "') >= con_fecha_inicio AND ('" + fechaRetencion
                + "') " + "<= con_fecha_fin) END ORDER BY con_concepto";

        return genericSQLDao.obtenerPorSql(sql, AnxConceptoComboTO.class);
    }

    @Override
    public List<AnxConceptoComboTO> getListaAnxConceptoTO(String fechaRetencion) throws Exception {
        String sql = "SELECT con_codigo, con_concepto, con_porcentaje, con_ingresa_porcentaje "
                + "FROM anexo.anx_concepto WHERE CASE WHEN con_fecha_fin IS NULL THEN (('" + fechaRetencion + "') >= "
                + "con_fecha_inicio) ELSE (('" + fechaRetencion + "') >= con_fecha_inicio AND ('" + fechaRetencion
                + "') " + "<= con_fecha_fin) END";

        return genericSQLDao.obtenerPorSql(sql, AnxConceptoComboTO.class);
    }

    @Override
    public List<AnxConceptoComboTO> getListaAnxConceptoTO(String fechaRetencion, String busqueda) throws Exception {
        String sql = "SELECT con_codigo, con_concepto, con_porcentaje, con_ingresa_porcentaje "
                + "FROM anexo.anx_concepto WHERE CASE WHEN con_fecha_fin IS NULL THEN (('" + fechaRetencion + "') >= "
                + "con_fecha_inicio) ELSE (('" + fechaRetencion + "') >= con_fecha_inicio AND ('" + fechaRetencion
                + "') <= con_fecha_fin) END AND "
                + "UPPER(con_codigo || con_concepto || con_concepto || con_codigo) LIKE CASE WHEN '" + busqueda
                + "' IS NOT NULL " + "AND '" + busqueda + "' = '' THEN NULL ELSE '%' || TRANSLATE('" + busqueda
                + "', ' ', '%') || '%' END";

        return genericSQLDao.obtenerPorSql(sql, AnxConceptoComboTO.class);
    }

}
