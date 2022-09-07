/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxFormulario104;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author VALDIVIEZO
 */
@Repository
public class AnxFormulario104DaoImpl extends GenericDaoImpl<AnxFormulario104, Integer> implements AnxFormulario104Dao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sisSucesoDao;

    @Override
    public AnxFormulario104 getAnxFormulario104(Integer secuencia) throws Exception {
        return obtenerPorId(AnxFormulario104.class, secuencia);
    }

    @Override
    public List<AnxFormulario104> getAnxListaAnxFormulario104(String empresa, String fechaDesde, String fechaHasta) throws Exception {
        fechaDesde = fechaDesde.equals("") ? null : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta.equals("") ? null : "'" + fechaHasta + "'";
        String sql = "SELECT * FROM anexo.anx_formulario_104 WHERE frm104_empresa='" + empresa + "' and  frm104_fecha_desde=" + fechaDesde + " AND frm104_fecha_hasta=" + fechaHasta;
        return genericSQLDao.obtenerPorSql(sql, AnxFormulario104.class);
    }

    @Override
    public boolean insertarAnxFormulario104(AnxFormulario104 anxFormulario104, SisSuceso sisSuceso) throws Exception {
        sisSucesoDao.insertar(sisSuceso);
        insertar(anxFormulario104);
        return true;
    }

    @Override
    public boolean modificarAnxFormulario104(AnxFormulario104 anxFormulario104, SisSuceso sisSuceso) throws Exception {
        sisSucesoDao.insertar(sisSuceso);
        actualizar(anxFormulario104);
        return true;
    }

    @Override
    public boolean eliminarAnxFormulario104(AnxFormulario104 anxFormulario104, SisSuceso sisSuceso) throws Exception {
        eliminar(anxFormulario104);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

}
