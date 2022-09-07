package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCatastroMicroempresa;

@Repository
public class AnexoCatastroMicroempresaDaoImpl extends GenericDaoImpl<AnxCatastroMicroempresa, Integer> implements AnexoCatastroMicroempresaDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public boolean insertarListadoCatastroMicroempresa(List<AnxCatastroMicroempresa> listadoCatastroMicroempresa, boolean permitirBorrar) throws Exception {
        if (permitirBorrar) {
            String sql = " DELETE FROM anexo.anx_catastro_microempresa";
            int mensaje = genericSQLDao.ejecutarSQL(sql);
        }

        insertar(listadoCatastroMicroempresa);
        return true;
    }

}
