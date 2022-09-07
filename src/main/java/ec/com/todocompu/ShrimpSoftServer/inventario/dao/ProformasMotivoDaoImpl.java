package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaMotivoTablaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class ProformasMotivoDaoImpl extends GenericDaoImpl<InvProformasMotivo, InvProformasMotivoPK> implements ProformasMotivoDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public boolean insertarInvProformaMotivo(InvProformasMotivo invProformasMotivo, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        insertar(invProformasMotivo);
        return true;
    }

    @Override
    public boolean modificarInvProformaMotivo(InvProformasMotivo invProformaMotivo, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        actualizar(invProformaMotivo);
        return true;
    }

    @Override
    public boolean eliminarInvProformaMotivo(InvProformasMotivo invProformaMotivo, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        eliminar(invProformaMotivo);
        return true;
    }

    @Override
    public InvProformasMotivo getInvProformasMotivo(String empresa, String codigo) throws Exception {
        return obtenerPorId(InvProformasMotivo.class, new InvProformasMotivoPK(empresa, codigo));
    }

    @Override
    public InvProformaMotivoTO getInvProformasMotivoTO(String empresa, String pmCodigo) throws Exception {
        return ConversionesInventario.convertirInvProformaMotivo_InvProformaMotivoTO(
                obtenerPorId(InvProformasMotivo.class, new InvProformasMotivoPK(empresa, pmCodigo)));
    }

    @Override
    public Boolean comprobarInvProformaMotivo(String empresa, String motCodigo) throws Exception {
        String sql = "SELECT COUNT(*) FROM inventario.inv_proformas_motivo " + "WHERE pm_empresa = ('" + empresa
                + "') AND pm_codigo = ('" + motCodigo + "')";

        return ((int) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0)) == 0) ? false : true;
    }

    @Override
    public Boolean comprobarInvProformasMotivo(String empresa, String motCodigo) throws Exception {
        String sql = "SELECT COUNT(*) FROM inventario.inv_proformas_motivo " + "WHERE pm_empresa = ('" + empresa
                + "') AND pm_codigo = ('" + motCodigo + "')";

        return ((int) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0)) == 0) ? false : true;
    }

    @Override
    public List<InvProformaMotivoTablaTO> getListaInvProformaMotivoTablaTO(String empresa) throws Exception {
        String sql = "SELECT pm_codigo, pm_detalle FROM inventario.inv_proformas_motivo " + "WHERE pm_empresa = ('"
                + empresa + "') ORDER BY pm_codigo";

        return genericSQLDao.obtenerPorSql(sql, InvProformaMotivoTablaTO.class);
    }

    @Override
    public List<InvProformaMotivoComboTO> getListaProformaMotivoComboTO(String empresa, boolean filtrarInactivos)
            throws Exception {
        String sql = "";
        if (filtrarInactivos) {
            sql = "SELECT pm_codigo, pm_detalle FROM inventario.inv_proformas_motivo " + "WHERE pm_empresa = ('"
                    + empresa + "') AND NOT pm_inactivo ORDER BY pm_codigo";
        } else {
            sql = "SELECT pm_codigo, pm_detalle FROM inventario.inv_proformas_motivo " + "WHERE pm_empresa = ('"
                    + empresa + "') ORDER BY pm_codigo";
        }

        return genericSQLDao.obtenerPorSql(sql, InvProformaMotivoComboTO.class);
    }

    @Override
    public boolean retornoContadoEliminarProformasMotivo(String empresa, String motivo) throws Exception {
        String sql = "SELECT inventario.fun_sw_proforma_motivo_eliminar('" + empresa + "', '" + motivo + "');";
        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    /*lista motivos*/
    @Override
    public List<InvProformaMotivoTO> getListaInvProformaMotivoTO(String empresa, boolean filtrarInactivos, String busqueda) throws Exception {
        String sqlString = "";
        if (busqueda != null) {
            sqlString += " AND " + "CASE WHEN ('" + busqueda + "') = '' THEN TRUE "
                    + "ELSE (pm_codigo || UPPER(pm_detalle) LIKE '%' || TRANSLATE('" + busqueda
                    + "', ' ', '%') || '%') END";
        }
        String sql = "SELECT * FROM inventario.inv_proformas_motivo WHERE pm_empresa = ('" + empresa + "')" + (filtrarInactivos ? "" : "AND NOT pm_inactivo ") + sqlString + " ORDER BY pm_codigo;";

        return genericSQLDao.obtenerPorSql(sql, InvProformaMotivoTO.class);
    }

}
