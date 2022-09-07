package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteRecurrenteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteSinMovimientoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvFunListadoClientesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvCliente;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvClientePK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvNumeracionVarios;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoCliente;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoClienteDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvClienteContratosTO;

@Repository
public class ClienteDaoImpl extends GenericDaoImpl<InvCliente, InvClientePK> implements ClienteDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private NumeracionVariosDao numeracionVariosDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private SucesoClienteDao sucesoClienteDao;

    @Override
    public InvCliente buscarInvCliente(String empresa, String codigoCliente) throws Exception {
        return obtenerPorId(InvCliente.class, new InvClientePK(empresa, codigoCliente));
    }

    @Override
    public boolean eliminarInvCliente(InvCliente invCliente, SisSuceso sisSuceso) throws Exception {
        eliminar(invCliente);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean insertarInvCliente(InvCliente invCliente, SisSuceso sisSuceso, InvNumeracionVarios invNumeracionVarios) throws Exception {
        insertar(invCliente);
        sucesoDao.insertar(sisSuceso);
        //suceso personalizado 
        SisSucesoCliente sucesoCliente = new SisSucesoCliente();
        String json = UtilsJSON.objetoToJson(invCliente);
        InvCliente cliente = new InvCliente(invCliente.getInvClientePK());
        sucesoCliente.setSusJson(json);
        sucesoCliente.setSisSuceso(sisSuceso);
        sucesoCliente.setInvCliente(cliente);
        sucesoClienteDao.insertar(sucesoCliente);
        //****
        if (invNumeracionVarios != null) {
            numeracionVariosDao.actualizar(invNumeracionVarios);
        }
        return true;
    }

    @Override
    public boolean modificarInvCliente(InvCliente invCliente, SisSuceso sisSuceso) throws Exception {
        actualizar(invCliente);
        sucesoDao.insertar(sisSuceso);
        //suceso personalizado 
        SisSucesoCliente sucesoCliente = new SisSucesoCliente();
        String json = UtilsJSON.objetoToJson(invCliente);
        sucesoCliente.setSusJson(json);
        sucesoCliente.setSisSuceso(sisSuceso);
        sucesoCliente.setInvCliente(invCliente);
        sucesoClienteDao.insertar(sucesoCliente);
        //****
        return true;
    }

    @Override
    public boolean modificarInvClienteLlavePrincipal(InvCliente invClienteEliminar, InvCliente invCliente,
            SisSuceso sisSuceso) throws Exception {
        eliminar(invClienteEliminar);
        actualizar(invCliente);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public String getInvProximaNumeracionCliente(String empresa, InvClienteTO invClienteTO) throws Exception {
        String sql = "SELECT num_clientes FROM inventario.inv_numeracion_varios WHERE num_empresa = '" + empresa + "'";

        String consulta = (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
        if (consulta != null) {
            invClienteTO.setCliCodigo(consulta);
        } else {
            invClienteTO.setCliCodigo("00000");
        }

        int numeracion = Integer.parseInt(invClienteTO.getCliCodigo());
        String rellenarConCeros = "";
        do {
            numeracion++;
            int numeroCerosAponer = 5 - String.valueOf(numeracion).trim().length();
            rellenarConCeros = "";
            for (int i = 0; i < numeroCerosAponer; i++) {
                rellenarConCeros = rellenarConCeros + "0";
            }

            invClienteTO.setCliCodigo(rellenarConCeros + numeracion);

        } while (buscarInvCliente(invClienteTO.getEmpCodigo(), invClienteTO.getCliCodigo()) != null);
        return rellenarConCeros + numeracion;
    }

    @Override
    public Boolean buscarConteoCliente(String empCodigo, String codigoCliente) throws Exception {
        String sql = "SELECT * FROM " + "inventario.fun_sw_cliente_eliminar('" + empCodigo + "', '" + codigoCliente
                + "')";

        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

    @Override
    public boolean getClienteRepetido(String empresa, String codigo, String id, String nombre, String razonSocial)
            throws Exception {
        empresa = empresa.compareTo("") == 0 ? null : empresa;
        codigo = codigo == null || codigo.compareTo("") == 0 ? null : codigo;
        id = id == null || id.compareTo("") == 0 ? null : id;
        nombre = nombre == null || nombre.compareTo("") == 0 ? null : nombre;
        razonSocial = razonSocial == null || razonSocial.compareTo("") == 0 ? null : razonSocial;

        String sql = "SELECT * FROM inventario." + "fun_sw_cliente_repetido(" + empresa + ", " + codigo + ", " + id
                + ", " + nombre + ", " + razonSocial + ")";

        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

    @Override
    public InvCliente obtenerInvClientePorCedulaRuc(String empresa, String identificacion, char tipo) {
        InvCliente cliente = null;
        InvCliente clienteAdic = null;
        if (tipo == 'C' || tipo == 'R') {
            String cedula = tipo == 'C' ? identificacion : identificacion.substring(0, 10);
            String ruc = tipo == 'C' ? identificacion + "001" : identificacion;
            cliente = obtenerInvClientePorCedulaRuc(empresa, cedula);
            clienteAdic = obtenerInvClientePorCedulaRuc(empresa, ruc);
        } else {
            cliente = obtenerInvClientePorCedulaRuc(empresa, identificacion);
        }
        if (cliente != null) {
            return cliente;
        } else {
            if (clienteAdic != null) {
                return clienteAdic;
            }
        }
        return null;

    }

    @Override
    public List<InvClienteRecurrenteTO> listarClientesParaVentarecurrente(String empresa, String fecha) throws Exception {
        String sql = "select * from inventario.fun_clientes_ventas_recurrentes('" + empresa + "', '" + fecha + "')";
        return genericSQLDao.obtenerPorSql(sql, InvClienteRecurrenteTO.class);
    }

    @Override
    public boolean validarClienteParaVentarecurrente(String empresa, String fecha, String cliCodigo, int grupo) throws Exception {
        String sql = "select * from inventario.fun_clientes_ventas_recurrentes('" + empresa + "', '" + fecha + "')"
                + " where vr_codigo = '" + cliCodigo + "' and vr_grupo = " + grupo;
        List<InvClienteRecurrenteTO> clientesRecurrentes = genericSQLDao.obtenerPorSql(sql, InvClienteRecurrenteTO.class);
        return clientesRecurrentes != null && !clientesRecurrentes.isEmpty();
    }

    @Override
    public List<InvClienteTO> getClienteTO(String empresa, String codigo) throws Exception {
        String sql = "SELECT * FROM inventario.inv_cliente WHERE cli_empresa = ('" + empresa + "') AND "
                + "cli_codigo = ('" + codigo + "')";

        return genericSQLDao.obtenerPorSql(sql, InvClienteTO.class);
    }

    @Override
    public InvClienteTO getInvClienteTOByRazonSocial(String empresa, String razonSocial) throws Exception {
        String sql = "SELECT * FROM inventario.inv_cliente WHERE cli_empresa = ('" + empresa + "') AND "
                + "cli_razon_social = ('" + razonSocial + "')";

        return genericSQLDao.obtenerObjetoPorSql(sql, InvClienteTO.class);
    }

    @Override
    public List<InvClienteSinMovimientoTO> obtenerClientesSinMovimientos(String empresa) throws Exception {
        String sql = "select * from inventario.fun_sw_reporte_clientes_sin_movimientos('" + empresa + "')";
        return genericSQLDao.obtenerPorSql(sql, InvClienteSinMovimientoTO.class);
    }

    @Override
    public List<InvListaClienteTO> getListaClienteTO(String empresa, String busqueda, boolean incluirClienteInactivo)
            throws Exception {
        String sql = "SELECT inv_cliente.cli_codigo, inv_cliente.cli_id_numero, inv_cliente.cli_razon_social, "
                + "inv_cliente.cli_direccion FROM inventario.inv_cliente " + "WHERE "
                + (incluirClienteInactivo ? "" : "NOT cli_inactivo " + "AND ") + "inv_cliente.cli_empresa = ('"
                + empresa + "') " + "AND (cli_codigo || COALESCE(cli_id_numero,'') || cli_razon_social || cli_direccion "
                + "LIKE TRANSLATE(' ' || CASE WHEN ('" + busqueda + "') = ''  THEN '~' ELSE ('" + busqueda
                + "') END || ' ', ' ', '%')) ORDER BY cli_razon_social";

        return genericSQLDao.obtenerPorSql(sql, InvListaClienteTO.class);
    }

    @Override
    public List<InvFunListadoClientesTO> getInvFunListadoClientesTO(String empresa, String categoria) throws Exception {
        categoria = categoria == null ? categoria : "'" + categoria + "'";
        String sql = "SELECT * FROM inventario.fun_listado_clientes('" + empresa + "', " + categoria + ")";
        return genericSQLDao.obtenerPorSql(sql, InvFunListadoClientesTO.class);
    }

    @Override
    public List<InvFunListadoClientesTO> getInvFunListadoClientesTO(String empresa, boolean mostrarInactivo, String categoria) throws Exception {
        categoria = categoria == null ? categoria : "'" + categoria + "'";
        String complementoSql = mostrarInactivo ? "" : "WHERE NOT cli_inactivo";
        String sql = "SELECT * FROM inventario.fun_listado_clientes('" + empresa + "', " + categoria + ") " + complementoSql;
        return genericSQLDao.obtenerPorSql(sql, InvFunListadoClientesTO.class);
    }

    @Override
    public List<InvFunListadoClientesTO> getInvFunListadoClientesTO(String empresa, boolean mostrarInactivo, String categoria, String busqueda, String grupoEmpresarial) throws Exception {
        categoria = categoria == null ? categoria : "'" + categoria + "'";
        String complementoSqlGrupo = "";
        if (grupoEmpresarial != null && !(grupoEmpresarial.equals(""))) {
            complementoSqlGrupo = " and cli_grupo_empresarial_nombre = '" + grupoEmpresarial + "'";
        }
        String complementoSql = mostrarInactivo ? "" : "WHERE NOT cli_inactivo";
        if (busqueda != null && !busqueda.equals("")) {
            if (complementoSql.equals("")) {
                complementoSql = complementoSql + " WHERE";
            } else {
                complementoSql = complementoSql + complementoSqlGrupo + " AND ";
            }
            complementoSql = complementoSql + " (cli_codigo || COALESCE(cli_id,'') || cli_razon_social  "
                    + "LIKE TRANSLATE(' ' || CASE WHEN ('" + busqueda + "') = ''  THEN '~' ELSE ('" + busqueda
                    + "') END || ' ', ' ', '%')) ORDER BY cli_razon_social";
        } else {
            complementoSql = complementoSql + " ORDER BY cli_razon_social";
        }
        String sql = "SELECT * FROM inventario.fun_listado_clientes('" + empresa + "', " + categoria + ") " + complementoSql;
        return genericSQLDao.obtenerPorSql(sql, InvFunListadoClientesTO.class);
    }

    @Override
    public List<InvClienteContratosTO> listarReporteComprativoContratos(String empresa, boolean mostrarInactivo, String categoria, String busqueda, boolean diferencia) throws Exception {
        categoria = categoria == null ? categoria : "'" + categoria + "'";
        String complementoSqlGrupo = "";
        String complementoSql = mostrarInactivo ? "" : "WHERE NOT cli_inactivo";
        if (busqueda != null && !busqueda.equals("")) {
            if (complementoSql.equals("")) {
                complementoSql = complementoSql + " WHERE";
            } else {
                complementoSql = complementoSql + complementoSqlGrupo + " AND ";
            }
            complementoSql = complementoSql + " (cli_codigo || COALESCE(cli_id,'') || cli_razon_social "
                    + "LIKE TRANSLATE(' ' || CASE WHEN ('" + busqueda + "') = ''  THEN '~' ELSE ('" + busqueda
                    + "') END || ' ', ' ', '%')) ORDER BY cli_razon_social";
        } else {
            complementoSql = complementoSql + " ORDER BY cli_razon_social";
        }

        String sql = "SELECT * FROM inventario.fun_listado_clientes_contratos('" + empresa + "', " + categoria + ") ";
        if (diferencia) {
            sql = sql + " where cli_contratos > 0 and cli_detalles_venta != cli_contratos;";
        } else {
            sql = sql + complementoSql;
        }
        return genericSQLDao.obtenerPorSql(sql, InvClienteContratosTO.class);
    }

    @Override
    public List<InvFunListadoClientesTO> getInvFunListadoClientesTOSinVentaRecurrente(String empresa, boolean mostrarInactivo, String categoria, String busqueda, String grupoEmpresarial) throws Exception {

        String complementoSql = "";
        if (grupoEmpresarial != null && !(grupoEmpresarial.equals(""))) {
            complementoSql = " AND cli_grupo_empresarial_nombre = '" + grupoEmpresarial + "'";
        }

        if (categoria != null && !categoria.equals("")) {
            complementoSql += " AND inv_cliente.cc_codigo = '" + categoria + "' ";
        }

        complementoSql += mostrarInactivo ? "" : " AND  NOT cli_inactivo";
        if (busqueda != null && !busqueda.equals("")) {
            complementoSql = complementoSql + " AND (inv_cliente.cli_codigo || COALESCE(cli_id_numero,'') || cli_razon_social || cli_direccion "
                    + "LIKE TRANSLATE(' ' || CASE WHEN ('" + busqueda + "') = ''  THEN '~' ELSE ('" + busqueda
                    + "') END || ' ', ' ', '%')) ORDER BY cli_razon_social";
        } else {
            complementoSql = complementoSql + " ORDER BY cli_razon_social";
        }

        String sql = "SELECT ROW_NUMBER() OVER() AS id, inv_cliente.cli_codigo, cli_id_tipo cli_tipo_id, cli_id_numero cli_id, "
                + "           cli_razon_social, cli_nombre_comercial, cc_detalle cli_categoria, "
                + "           cli_provincia, cli_ciudad, cli_zona, cli_direccion, ge_nombre cli_grupo_empresarial_nombre, cli_lugares_entrega, "
                + "           cli_telefono, cli_celular, cli_email, cli_observaciones, cli_dias_credito, "
                + "           cli_inactivo, cli_precio, v.vend_nombre cli_vendedor, cli_cupo_credito, cli_descripcion, cli_ip,cli_dias_credito_aseguradora,cli_cupo_credito_aseguradora, pro_garantia "
                + "      FROM inventario.inv_cliente "
                + "	 INNER JOIN inventario.inv_cliente_categoria "
                + "        ON inv_cliente.cc_empresa = inv_cliente_categoria.cc_empresa AND "
                + "           inv_cliente.cc_codigo = inv_cliente_categoria.cc_codigo "
                + "      LEFT JOIN inventario.inv_cliente_grupo_empresarial "
                + "        ON inv_cliente.ge_empresa = inv_cliente_grupo_empresarial.ge_empresa AND "
                + "           inv_cliente.ge_codigo = inv_cliente_grupo_empresarial.ge_codigo "
                + "      LEFT JOIN inventario.inv_vendedor v "
                + "        ON inv_cliente.vend_empresa = v.vend_empresa AND "
                + "	   inv_cliente.vend_codigo = v.vend_codigo "
                + "	 LEFT JOIN inventario.inv_clientes_ventas_detalle  cvd "
                + "	   ON inv_cliente.cli_empresa = cvd.cli_empresa AND "
                + "	      inv_cliente.cli_codigo = cvd.cli_codigo "
                + "      WHERE inv_cliente.cc_empresa='" + empresa + "'  AND cvd.cli_empresa is null AND cvd.cli_codigo is null " + complementoSql;
        return genericSQLDao.obtenerPorSql(sql, InvFunListadoClientesTO.class);
    }

    @Override
    public InvCliente obtenerInvClientePorCedulaRuc(String empresa, String cedulaRuc) {
        if (empresa == null || empresa.compareToIgnoreCase("") == 0) {
            return obtenerObjetoPorHql("select c from InvCliente c where c.cliIdNumero=?1 ",
                    new Object[]{cedulaRuc});
        } else {
            return obtenerObjetoPorHql("select c from InvCliente c inner join c.invClientePK cpk "
                    + "where c.cliIdNumero=?1 and cpk.cliEmpresa=?2", new Object[]{cedulaRuc, empresa});
        }
    }

    @Override
    public InvClienteTO obtenerClienteTOPorCedulaRuc(String empresa, String cedulaRuc) throws Exception {
        String sql = "SELECT * FROM inventario.inv_cliente WHERE cli_empresa = ('" + empresa + "') AND "
                + "cli_id_numero = ('" + cedulaRuc + "')";
        InvCliente cliente = genericSQLDao.obtenerObjetoPorSql(sql, InvCliente.class);
        InvClienteTO clienteTO = null;
        if (cliente != null) {
            clienteTO = ConversionesInventario.convertirInvCliente_InvClienteTO(cliente);
        }

        return clienteTO;
    }

    @Override
    public InvClienteTO obtenerClienteTO(String empresa, String codigo) throws Exception {
        String sql = "SELECT * FROM inventario.inv_cliente WHERE cli_empresa = ('" + empresa + "') AND "
                + "cli_codigo = ('" + codigo + "')";
        return genericSQLDao.obtenerObjetoPorSql(sql, InvClienteTO.class);
    }

    @Override
    public InvCliente obtenerCliente(String empresa, String codigo) throws Exception {
        String sql = "SELECT * FROM inventario.inv_cliente WHERE cli_empresa = ('" + empresa + "') AND "
                + "cli_codigo = ('" + codigo + "')";
        return genericSQLDao.obtenerObjetoPorSql(sql, InvCliente.class);
    }

    @Override
    public boolean actualizarGrupoEnCliente(String empresa, String codigoCliente, String codigoGrupo, SisSuceso sisSuceso) throws Exception {
        String sql = "UPDATE inventario.inv_cliente SET ge_codigo = '"
                + codigoGrupo + "', ge_empresa='" + empresa + "'" + " WHERE cli_empresa= '" + empresa + "' and cli_codigo= '" + codigoCliente + "' ;";
        genericSQLDao.ejecutarSQL(sql);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

}
