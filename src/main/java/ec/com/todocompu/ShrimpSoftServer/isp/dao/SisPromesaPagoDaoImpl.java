/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.isp.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPromesaPago;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author VALDIVIEZO
 */
@Repository
public class SisPromesaPagoDaoImpl extends GenericDaoImpl<SisPromesaPago, Integer> implements SisPromesaPagoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public List<SisPromesaPago> getListSisPromesaPagoPorcliente(String empresa, String cliCodigo) throws Exception {
        String sql = "SELECT * FROM sistemaweb.sis_promesa_pago WHERE cli_empresa='" + empresa + "' AND cli_codigo='" + cliCodigo + "'";
        return genericSQLDao.obtenerPorSql(sql, SisPromesaPago.class);
    }

    @Override
    public SisPromesaPago insertarSisPromesaPago(SisPromesaPago promesa, SisSuceso sisSuceso) throws Exception {
        insertar(promesa);
        sucesoDao.insertar(sisSuceso);
        return promesa;
    }

    @Override
    public SisPromesaPago modificarSisPromesaPago(SisPromesaPago promesa, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        actualizar(promesa);
        return promesa;
    }

    @Override
    public boolean eliminarSisPromesaPago(SisPromesaPago promesa, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        eliminar(promesa);
        return true;
    }

}
