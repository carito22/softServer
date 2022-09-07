package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionInp;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionInpPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class GuiaInpDaoImpl extends GenericDaoImpl<InvGuiaRemisionInp, InvGuiaRemisionInpPK> implements GuiaInpDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public InvGuiaRemisionInp buscarInvGuiaRemisionInp(String empresa, String codigo, String cliente) throws Exception {
        return obtenerPorId(InvGuiaRemisionInp.class, new InvGuiaRemisionInpPK(empresa.trim(), codigo.trim(), cliente));
    }

    @Override
    public boolean eliminarInvGuiaRemisionInp(InvGuiaRemisionInp guiaInp, SisSuceso sisSuceso) {
        eliminar(guiaInp);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean insertarInvGuiaRemisionInp(InvGuiaRemisionInp guiaInp, SisSuceso sisSuceso) throws Exception {
        insertar(guiaInp);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarInvGuiaRemisionInp(InvGuiaRemisionInp guiaInp, SisSuceso sisSuceso) throws Exception {
        actualizar(guiaInp);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<InvGuiaRemisionInp> getInvGuiaRemisionInp(String empresa, String cliente, boolean inactivo) throws Exception {
        String sql = " SELECT * FROM inventario.inv_guia_remision_inp  WHERE inp_empresa = '" + empresa + "' AND inp_cli_codigo = '" + cliente + "' "
                + (inactivo ? "" : "AND NOT inp_inactivo ");
        return genericSQLDao.obtenerPorSql(sql, InvGuiaRemisionInp.class);
    }

}
