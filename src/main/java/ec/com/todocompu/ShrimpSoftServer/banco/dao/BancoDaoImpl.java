package ec.com.todocompu.ShrimpSoftServer.banco.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoBancoDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ConsultaDatosBancoCuentaTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.ListaBanBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanBanco;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanBancoPK;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanBancoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoBanco;
import java.util.ArrayList;

@Repository
public class BancoDaoImpl extends GenericDaoImpl<BanBanco, BanBancoPK> implements BancoDao {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoBancoDao sucesoBancoDao;

    @Override
    public List<ListaBanBancoTO> getListaBanBancoTO(String empresa) throws Exception {
        String sql = "SELECT ban_codigo, ban_nombre " + "FROM banco.ban_banco " + "WHERE (ban_empresa = '" + empresa
                + "') " + "ORDER BY ban_nombre";
        return genericSQLDao.obtenerPorSql(sql, ListaBanBancoTO.class);
    }

    @Override
    public List<ListaBanBancoTO> getListaBanBancoTODefecto(String empresa) throws Exception {
        List<ListaBanBancoTO> listaSobrevivencia = new ArrayList<>();
        String sql = "SELECT * FROM banco.fun_crear_bancos('" + empresa + "')";

        boolean respuestaInsercion = (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
        if (respuestaInsercion) {
            listaSobrevivencia = getListaBanBancoTO(empresa);
        }

        return listaSobrevivencia;
    }

    @Override
    public List<BanBancoTO> getBancoTo(String empresa, String codigo) throws Exception {
        String sql = "SELECT ban_empresa, ban_codigo, ban_nombre, usr_empresa, usr_codigo, usr_fecha_inserta AS usr_fecha_inserta_banco"
                + " FROM banco.ban_banco " + "WHERE ban_empresa = ('"
                + empresa + "') AND " + "ban_codigo = ('" + codigo + "')";
        return genericSQLDao.obtenerPorSql(sql, BanBancoTO.class);
    }

    @Override
    public ConsultaDatosBancoCuentaTO getConsultaDatosBancoCuentaTO(String empresa, String cuenta) throws Exception {
        ConsultaDatosBancoCuentaTO consultaDatosBancoCuentaTO = null;
        String sql = "SELECT ban_cuenta.cta_numero, "
                + "ban_cuenta.cta_titular, ban_banco.ban_nombre, cta_formato_cheque "
                + "FROM banco.ban_banco INNER JOIN banco.ban_cuenta "
                + "ON ban_banco.ban_empresa || ban_banco.ban_codigo = "
                + "ban_cuenta.ban_empresa || ban_cuenta.ban_codigo " + "WHERE (ban_cuenta.cta_empresa = '" + empresa
                + "') AND ban_cuenta.cta_cuenta_contable " + "= '" + cuenta + "'";
        try {
            consultaDatosBancoCuentaTO = (ConsultaDatosBancoCuentaTO) genericSQLDao.obtenerObjetoPorSql(sql,
                    ConsultaDatosBancoCuentaTO.class);

        } catch (Exception e) {
            consultaDatosBancoCuentaTO = new ConsultaDatosBancoCuentaTO("", "", "", "");
        }
        return consultaDatosBancoCuentaTO;
    }

    @Override
    public boolean insertarBanBanco(BanBanco banBanco, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        insertar(banBanco);
        /////insertar suceso////////
        SisSucesoBanco sucesoBanco = new SisSucesoBanco();
        String json = UtilsJSON.objetoToJson(banBanco);
        sucesoBanco.setSusJson(json);
        sucesoBanco.setSisSuceso(sisSuceso);
        sucesoBanco.setBanBanco(banBanco);
        sucesoBancoDao.insertar(sucesoBanco);
        return true;
    }

    @Override
    public boolean modificarBanBanco(BanBanco banBanco, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        actualizar(banBanco);
        /////insertar suceso////////
        SisSucesoBanco sucesoBanco = new SisSucesoBanco();
        String json = UtilsJSON.objetoToJson(banBanco);
        sucesoBanco.setSusJson(json);
        sucesoBanco.setSisSuceso(sisSuceso);
        sucesoBanco.setBanBanco(banBanco);
        sucesoBancoDao.insertar(sucesoBanco);
        return true;
    }

    @Override
    public boolean eliminarBanBanco(BanBanco banBanco, SisSuceso sisSuceso) throws Exception {
        sucesoDao.insertar(sisSuceso);
        eliminar(banBanco);
        return true;
    }

}
