/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoContratoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteContratoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContrato;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClienteContratoDatosAdjuntos;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoContrato;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author VALDIVIEZO
 */
@Repository
public class ClienteContratoDaoImpl extends GenericDaoImpl<InvClienteContrato, Integer> implements ClienteContratoDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private SucesoContratoDao sucesoContratoDao;

    @Override
    public List<InvClienteContratoTO> getListaInvClienteContratoTO(String empresa, String cliente, String busqueda, String nRegistros) throws Exception {
        String limit = "";
        String sqlCliente = "";
        if (cliente != null && !cliente.equals("")) {
            sqlCliente = " AND con.cli_codigo='" + cliente + "'";
        }

        String complementoSql = busqueda != null && !busqueda.equals("")
                ? (" AND  (COALESCE(cli_numero_contrato,'')  ||  COALESCE(cli_Establecimiento,'')  || c.cli_codigo || COALESCE(cli_id_numero,'')  ||  cli_razon_social || \n"
                + "  COALESCE(cli_tipo_plan,'') || COALESCE(cli_comparticion,'')  || COALESCE(cli_direccion_ip,'')  ||  COALESCE(cli_distancia,'')   || COALESCE(cli_downlink,'')  || \n"
                + " COALESCE(cli_uplink,'')  || COALESCE(cli_tarifa,0)  || COALESCE(cli_punto_emision,'')  ||  COALESCE(wispro_contrato_id,'') "
                + "LIKE TRANSLATE(' ' || CASE WHEN ('" + busqueda + "') = '' THEN '~' ELSE ('" + busqueda + "') END || ' ', ' ', '%'))") : "";

        if (nRegistros != null && nRegistros.compareTo("") != 0 && nRegistros.compareTo("0") != 0) {
            limit = " limit " + nRegistros;
        }
        String sql = "SELECT cli_secuencial,cli_numero_contrato, "
                + " c.cli_empresa, c.cli_codigo,c.cli_id_numero,c.cli_razon_social, "
                + " cli_Establecimiento,cli_tipo_plan,cli_comparticion,cli_direccion_ip, "
                + " cli_distancia, cli_fecha_consumo, cli_downlink, cli_uplink, cli_tarifa, "
                + " con.usr_empresa, con.usr_codigo, con.usr_fecha_inserta, cli_punto_emision, cli_es_tercera_edad, cli_discapacitado,"
                + " b.bod_codigo, b.bod_empresa,b.bod_nombre, cli_tipo_codigo, cli_tipo_empresa, cli_usuario, cli_password, wispro_contrato_id,cli_fecha_corte "
                + "FROM inventario.inv_cliente_contrato con "
                + "inner join inventario.inv_cliente c "
                + "on c.cli_codigo =  con.cli_codigo "
                + "and c.cli_empresa = con.cli_empresa "
                + "left join inventario.inv_bodega b "
                + "on b.bod_empresa =  con.bod_empresa "
                + "and b.bod_codigo = con.bod_codigo "
                + "WHERE con.cli_empresa='" + empresa + "' "
                + sqlCliente + complementoSql + " Order by cli_fecha_consumo DESC " + limit;
        return genericSQLDao.obtenerPorSql(sql, InvClienteContratoTO.class);
    }

    @Override
    public InvClienteContrato obtenerInvClienteContrato(String empresa, String numeroContrato) throws Exception {
        String sql = "SELECT * FROM inventario.inv_cliente_contrato WHERE cli_empresa='" + empresa + "'  AND cli_numero_contrato='" + numeroContrato + "'";
        return genericSQLDao.obtenerObjetoPorSql(sql, InvClienteContrato.class);
    }

    @Override
    public InvClienteContrato obtenerInvClienteContrato(String empresa, String cliCodigo, String ip) throws Exception {
        String sql = "SELECT * FROM inventario.inv_cliente_contrato WHERE cli_empresa='" + empresa + "'  AND cli_codigo='" + cliCodigo + "' AND cli_direccion_ip='" + ip + "'";
        return genericSQLDao.obtenerObjetoPorSql(sql, InvClienteContrato.class);
    }

    @Override
    public InvClienteContrato obtenerInvClienteContrato(Integer secuencial) throws Exception {
        String sql = "SELECT * FROM inventario.inv_cliente_contrato WHERE cli_secuencial=" + secuencial;
        return genericSQLDao.obtenerObjetoPorSql(sql, InvClienteContrato.class);
    }

    @Override
    public boolean insertarInvClienteContrato(InvClienteContrato invClienteContrato, SisSuceso sisSuceso) throws Exception {
        insertar(invClienteContrato);
        sucesoDao.insertar(sisSuceso);
        //suceso contrato
        SisSucesoContrato sucesoContrato = new SisSucesoContrato();
        String json = UtilsJSON.objetoToJson(invClienteContrato);
        sucesoContrato.setSusJson(json);
        sucesoContrato.setSisSuceso(sisSuceso);
        sucesoContrato.setInvClienteContrato(invClienteContrato);
        sucesoContratoDao.insertar(sucesoContrato);
        return true;
    }

    @Override
    public boolean modificarInvClienteContrato(InvClienteContrato invClienteContrato, SisSuceso sisSuceso) throws Exception {
        actualizar(invClienteContrato);
        sucesoDao.insertar(sisSuceso);
        //suceso contrato
        SisSucesoContrato sucesoContrato = new SisSucesoContrato();
        String json = UtilsJSON.objetoToJson(invClienteContrato);
        sucesoContrato.setSusJson(json);
        sucesoContrato.setSisSuceso(sisSuceso);
        sucesoContrato.setInvClienteContrato(invClienteContrato);
        sucesoContratoDao.insertar(sucesoContrato);
        return true;
    }

    @Override
    public boolean eliminarInvClienteContrato(InvClienteContrato invClienteContrato, SisSuceso sisSuceso) throws Exception {
        eliminar(invClienteContrato);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public List<InvClienteContratoDatosAdjuntos> listarImagenesDeClienteContrato(Integer secuencial) throws Exception {
        String sql = "select * from inventario.inv_cliente_contrato_datos_adjuntos where "
                + " cli_secuencial = " + secuencial;
        return genericSQLDao.obtenerPorSql(sql, InvClienteContratoDatosAdjuntos.class);
    }

}
