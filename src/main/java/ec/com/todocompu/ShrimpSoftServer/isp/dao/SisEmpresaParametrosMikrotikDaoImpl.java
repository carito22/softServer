/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.isp.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametrosMikrotik;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author VALDIVIEZO
 */
@Repository
public class SisEmpresaParametrosMikrotikDaoImpl extends GenericDaoImpl<SisEmpresaParametrosMikrotik, Integer> implements SisEmpresaParametrosMikrotikDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public SisEmpresaParametrosMikrotik obtenerConfiguracionMikrotik(String empresa) throws Exception {
        String sql = "SELECT * FROM sistemaweb.sis_empresa_parametros_mikrotik WHERE par_empresa='" + empresa + "'";
        SisEmpresaParametrosMikrotik res = genericSQLDao.obtenerObjetoPorSql(sql, SisEmpresaParametrosMikrotik.class);
        return res;
    }

    @Override
    public SisEmpresaParametrosMikrotik insertarSisEmpresaParametrosMikrotik(SisEmpresaParametrosMikrotik SisEmpresaParametrosMikrotik, SisSuceso sisSuceso) throws Exception {
        insertar(SisEmpresaParametrosMikrotik);
        sucesoDao.insertar(sisSuceso);
        return SisEmpresaParametrosMikrotik;
    }

    @Override
    public SisEmpresaParametrosMikrotik modificarSisEmpresaParametrosMikrotik(SisEmpresaParametrosMikrotik sisEmpresaParametrosMikrotik, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        actualizar(sisEmpresaParametrosMikrotik);
        return sisEmpresaParametrosMikrotik;
    }

    @Override
    public boolean eliminarSisEmpresaParametrosMikrotik(SisEmpresaParametrosMikrotik sisEmpresaParametrosMikrotik, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        eliminar(sisEmpresaParametrosMikrotik);
        return true;
    }

}
