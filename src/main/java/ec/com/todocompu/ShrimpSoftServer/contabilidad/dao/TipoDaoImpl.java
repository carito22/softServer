package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConTipoTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipo;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConTipoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class TipoDaoImpl extends GenericDaoImpl<ConTipo, ConTipoPK> implements TipoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public boolean insertarConTipo(ConTipo conTipo, SisSuceso sisSuceso) throws Exception {
        insertar(conTipo);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarConTipo(ConTipo conTipo, SisSuceso sisSuceso) throws Exception {
        actualizar(conTipo);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarConTipo(ConTipo conTipo, SisSuceso sisSuceso) throws Exception {
        eliminar(conTipo);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public Boolean buscarTipoContable(String empCodigo, String tipCodigo) throws Exception {
        String sql = "SELECT COUNT(*) FROM contabilidad.con_tipo                WHERE tip_empresa = ('" + empCodigo
                + "') AND tip_codigo = ('" + tipCodigo + "')";
        return ((Integer) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql))) == 0 ? false : true;
    }

    @Override
    public List<ConTipoTO> getListaConTipoTO(String empresa) throws Exception {
        String sql = "SELECT * FROM contabilidad.con_tipo WHERE tip_empresa = ('" + empresa + "') ORDER BY tip_detalle";

        return genericSQLDao.obtenerPorSql(sql, ConTipoTO.class);
    }

    @Override
    public List<ConTipoTO> getListaConTipoTOFiltrado(String empresa, String filtro) throws Exception {
        String sql = "SELECT * FROM contabilidad.con_tipo WHERE tip_empresa = ('" + empresa + "') and tip_modulo in (" + filtro + ") ORDER BY tip_detalle";

        return genericSQLDao.obtenerPorSql(sql, ConTipoTO.class);
    }

    @Override
    public ConTipoTO getConTipoTO(String empresa, String tipCodigo) throws Exception {
        String sql = "SELECT * FROM contabilidad.con_tipo WHERE tip_empresa = ('" + empresa + "') and tip_codigo=('"
                + tipCodigo + "')";

        return (ConTipoTO) genericSQLDao.obtenerObjetoPorSql(sql, ConTipoTO.class);
    }

    @Override
    public List<ConTipoTO> getListaConTipoRrhhTO(String empresa) throws Exception {
        String sql = "SELECT * FROM contabilidad.con_tipo WHERE " + "tip_empresa = ('" + empresa + "') AND ("
                + "tip_codigo = 'C-ANT' OR tip_codigo = 'C-PRE' OR tip_codigo = 'C-PRO' OR "
                + "tip_codigo = 'C-BON' OR tip_codigo = 'C-VIA' OR tip_codigo = 'C-ROL' OR " + "tip_codigo = 'C-VAC')";

        return genericSQLDao.obtenerPorSql(sql, ConTipoTO.class);
    }

    @Override
    public List<ConTipoTO> getListaConTipoCarteraTO(String empresa) throws Exception {
        String sql = "SELECT * FROM contabilidad.con_tipo WHERE tip_empresa = ('" + empresa + "') AND ("
                + "tip_codigo = 'C-COB' OR tip_codigo = 'C-PAG')";

        return genericSQLDao.obtenerPorSql(sql, ConTipoTO.class);
    }

    @Override
    public List<ConTipoTO> getListaConTipoCarteraAnticiposTO(String empresa) throws Exception {
        String sql = "SELECT * FROM contabilidad.con_tipo WHERE tip_empresa = ('" + empresa + "') AND ("
                + "tip_codigo = 'C-ACLI' OR tip_codigo = 'C-APRO')";

        return genericSQLDao.obtenerPorSql(sql, ConTipoTO.class);
    }

    @Override
    public String buscarTipoContablePorMotivoCompra(String empresa, String codigoMotivo) throws Exception {
        String sql = "SELECT tip_codigo " + "FROM inventario.inv_compras_motivo where cm_empresa = ('" + empresa + "') "
                + "AND cm_codigo = ('" + codigoMotivo + "')";
        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public String buscarTipoContablePorMotivoVenta(String empresa, String codigoMotivo) throws Exception {
        String sql = "SELECT tip_codigo " + "FROM inventario.inv_ventas_motivo where vm_empresa = ('" + empresa + "') "
                + "AND vm_codigo = ('" + codigoMotivo + "')";

        return (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
    }

    @Override
    public List<ConTipo> getListaConTipo(String empresa) throws Exception {
        return obtenerPorHql(
                "SELECT conTipo FROM ConTipo conTipo inner join conTipo.conTipoPK conTipoPK WHERE conTipoPK.tipEmpresa=?1 ORDER BY conTipoPK.tipCodigo",
                new Object[]{empresa});
    }

    @Override
    public List<ConTipo> getListaConTipo(String empresa, String referencia) {
        return obtenerPorSql(
                "SELECT distinct ct.* FROM contabilidad.con_tipo ct "
                + "inner join contabilidad.con_contable con on (con.con_empresa=ct.tip_empresa and con.con_tipo=ct.tip_codigo) "
                + "where ct.tip_empresa='" + empresa + "' and con.con_referencia like '%" + referencia + "%';",
                ConTipo.class);
    }

    @Override
    public List<ConTipoTO> getListaConTipoCodigo(String empresa, String codigo) throws Exception {
        String sql = "SELECT * FROM contabilidad.con_tipo WHERE tip_empresa = ('" + empresa + "') AND ("
                + "tip_codigo = '" + codigo + "')";

        return genericSQLDao.obtenerPorSql(sql, ConTipoTO.class);
    }

}
