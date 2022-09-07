/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.activoFijo.dao;

import ec.com.todocompu.ShrimpSoftServer.contabilidad.dao.ContableDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.TO.AfDepreciacionesTO;
import ec.com.todocompu.ShrimpSoftUtils.activoFijo.entity.AfDepreciaciones;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.TO.ConContableTO;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContable;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CarolValdiviezo
 */
@Repository
public class DepreciacionActivoFijoDaoImpl extends GenericDaoImpl<AfDepreciaciones, Integer> implements DepreciacionActivoFijoDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private ContableDao contableDao;

    @Override
    public AfDepreciaciones getAfDepreciaciones(Integer codigo) throws Exception {
        return obtenerPorId(AfDepreciaciones.class, codigo);
    }

    @Override
    public boolean insertarAfDepreciaciones(AfDepreciaciones afDepreciaciones, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        insertar(afDepreciaciones);
        return true;
    }

    @Override
    public List<AfDepreciacionesTO> listarDepreciaciones(String empresa, String fechaHasta) throws Exception {
        String sql = "SELECT * from activosfijos.fun_depreciacion_calcular('" + empresa + "','" + fechaHasta + "')";
        return genericSQLDao.obtenerPorSql(sql, AfDepreciacionesTO.class);
    }

    @Override
    public List<AfDepreciacionesTO> listarDepreciacionesConsulta(String empresa, String fechaDesde, String fechaHasta) throws Exception {
        String sql = "SELECT * from activosfijos.fun_depreciacion_consulta('" + empresa + "','" + fechaDesde + "','" + fechaHasta + "')";
        return genericSQLDao.obtenerPorSql(sql, AfDepreciacionesTO.class);
    }

    @Override
    public List<AfDepreciaciones> listarDepreciacionesSegunContable(String empresa, String periodo, String tipo, String numero) throws Exception {
        String sql = "SELECT * FROM activosfijos.af_depreciaciones where con_empresa='" + empresa + "' and con_periodo = '" + periodo + "' and con_tipo = '" + tipo + "' and con_numero = '" + numero + "'";
        return genericSQLDao.obtenerPorSql(sql, AfDepreciaciones.class);
    }

    @Override
    public boolean productoSeEncuentraDepreciacion(String empresa, String codigoProducto) throws Exception {
        String sql = "SELECT * FROM activosfijos.af_depreciaciones where af_empresa='" + empresa + "' and af_codigo = '" + codigoProducto + "'";

        List<AfDepreciaciones> lista = genericSQLDao.obtenerPorSql(sql, AfDepreciaciones.class);

        if (lista != null && lista.size() > 0) {
            return true;
        }

        return false;
    }

    @Override
    public List<ConContableTO> obtenerContablesSegunDepreciacion(String empresa, String periodo, String codigoProducto) throws Exception {
        String sql = "select con.con_numero,con.con_empresa,con.con_periodo, con.con_tipo, con.con_fecha, con.con_pendiente, con.con_bloqueado,con.con_anulado, con.con_reversado, con.con_generado, "
                + "con.con_concepto, con.con_detalle, con.con_observaciones, con.usr_codigo, con.usr_fecha_inserta, con.con_codigo, con.con_referencia from activosfijos.af_depreciaciones dep "
                + "inner join contabilidad.con_contable con "
                + "on con.con_empresa =  dep.con_empresa "
                + "and con.con_periodo =  dep.con_periodo "
                + "and con.con_tipo =  dep.con_tipo "
                + "and con.con_numero =  dep.con_numero "
                + "where dep.con_empresa='" + empresa + "' and  dep.con_tipo='C-AF' and  dep.con_periodo='" + periodo + "' and "
                + "  dep.af_codigo ='" + codigoProducto + "' "
                + "group by con.con_empresa,con.con_periodo,con.con_tipo,con.con_numero ";

        List<ConContableTO> lista = genericSQLDao.obtenerPorSql(sql, ConContableTO.class);

        return lista;
    }

    @Override
    public boolean insertarModificarAfDepreciaciones(ConContable conContable, List<AfDepreciaciones> listado, SisSuceso sisSuceso) throws Exception {

        if (conContable.getConContablePK().getConNumero() == null || conContable.getConContablePK().getConNumero().compareToIgnoreCase("") == 0) {
            contableDao.buscarConteoUltimaNumeracion(conContable.getConContablePK());

        }

        sisSuceso.setSusClave(conContable.getConContablePK().getConPeriodo() + " " + conContable.getConContablePK().getConTipo() + " " + conContable.getConContablePK().getConNumero());
        sisSuceso.setSusDetalle("Depreciación por " + conContable.getConObservaciones());

        conContable.setConConcepto(conContable.getConConcepto() == null ? null : conContable.getConConcepto().toUpperCase());
        conContable.setConDetalle(conContable.getConDetalle() == null ? null : conContable.getConDetalle().toUpperCase());
        conContable.setConObservaciones(conContable.getConObservaciones() == null ? null : conContable.getConObservaciones().toUpperCase());
        conContable.setConPendiente(true);

        contableDao.saveOrUpdate(conContable);

        for (AfDepreciaciones dep : listado) {
            dep.setConEmpresa(conContable.getConContablePK().getConEmpresa());
            dep.setConNumero(conContable.getConContablePK().getConNumero());
            dep.setConPeriodo(conContable.getConContablePK().getConPeriodo());
            dep.setConTipo(conContable.getConContablePK().getConTipo());
        }

        saveOrUpdate(listado);

        sucesoDao.insertar(sisSuceso);
        return true;
    }

}
