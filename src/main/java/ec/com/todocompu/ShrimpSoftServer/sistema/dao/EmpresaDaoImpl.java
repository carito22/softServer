package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import static ec.com.todocompu.ShrimpSoftServer.util.ConversionesSistema.ConvertirSisEmpresa_SisEmpresaTO;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisEmpresaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresa;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Repository
public class EmpresaDaoImpl extends GenericDaoImpl<SisEmpresa, String> implements EmpresaDao {

    @Autowired
    private GenericSQLDao genericSQLDao;

    @Autowired
    private EmpresaParametrosDao empresaParametrosDao;

    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public List<SisEmpresaTO> getListaSisEmpresa(String usrCodigo, String empresa) throws Exception {
        String where = "";
        if (!usrCodigo.equals("ADM")) {
            where = " WHERE sis_empresa.emp_codigo = '" + empresa + "'";
        }
        String sql = "SELECT sis_empresa.emp_codigo, emp_ruc, emp_nombre, emp_razon_social, "
                + "emp_direccion, emp_ciudad, emp_pais, emp_telefono, emp_celular, emp_email, emp_clave, "
                + "par_resolucion_contribuyente_especial, par_obligado_llevar_contabilidad, "
                + "par_gerente, par_gerente_tipo_id, par_gerente_id, par_contador, "
                + "par_contador_ruc, emp_activa, usr_codigo, usr_fecha_inserta_empresa, "
                + "par_actividad, par_escoger_precio_por, par_financiero, par_financiero_id, "
                + "par_columnas_estados_financieros, par_codigo_dinardap ,par_obligado_emitir_documentos_electronicos, "
                + "par_obligado_registrar_liquidacion_pesca, par_obligado_aprobar_pagos, par_contribuyente_regimen_microempresa,par_agente_retencion,is_exportadora "
                + "FROM sistemaweb.sis_empresa LEFT JOIN sistemaweb.sis_empresa_parametros "
                + "ON sis_empresa.emp_codigo = sis_empresa_parametros.emp_codigo " + where
                + " ORDER BY sis_empresa.emp_nombre";

        return genericSQLDao.obtenerPorSql(sql, SisEmpresaTO.class);
    }

    @Override
    public SisEmpresaTO obtenerSisEmpresaTO(String empresa) throws Exception {
        String sql = "SELECT sis_empresa.emp_codigo, emp_ruc, emp_nombre, emp_razon_social, "
                + "emp_direccion, emp_ciudad, emp_pais, emp_telefono, emp_celular, emp_email, emp_clave, "
                + "par_resolucion_contribuyente_especial, par_obligado_llevar_contabilidad, "
                + "par_gerente, par_gerente_tipo_id, par_gerente_id, par_contador, "
                + "par_contador_ruc, emp_activa, usr_codigo, usr_fecha_inserta_empresa, "
                + "par_actividad, par_escoger_precio_por, par_financiero, par_financiero_id, "
                + "par_columnas_estados_financieros, par_codigo_dinardap ,par_obligado_emitir_documentos_electronicos, "
                + "par_obligado_registrar_liquidacion_pesca, par_obligado_aprobar_pagos, par_contribuyente_regimen_microempresa,par_agente_retencion,is_exportadora "
                + "FROM sistemaweb.sis_empresa LEFT JOIN sistemaweb.sis_empresa_parametros "
                + "ON sis_empresa.emp_codigo = sis_empresa_parametros.emp_codigo "
                + "WHERE sis_empresa.emp_codigo = '" + empresa + "'"
                + " ORDER BY sis_empresa.emp_nombre";
        return genericSQLDao.obtenerObjetoPorSql(sql, SisEmpresaTO.class);
    }

    @Override
    public boolean existeEmpresaConElMismoRuc(String empresa, String ruc) throws Exception {
        ruc = ruc != null ? ruc.trim() : null;
        String sql = "SELECT * FROM sistemaweb.sis_empresa "
                + "WHERE sis_empresa.emp_codigo != '" + empresa + "'"
                + " AND sis_empresa.emp_ruc = '" + ruc + "'";
        SisEmpresa sisEmpresa = genericSQLDao.obtenerObjetoPorSql(sql, SisEmpresa.class);
        return sisEmpresa != null;
    }

    @Override
    public SisEmpresaTO obtenerSisEmpresaTOByRUC(String ruc) {
        String sql = "SELECT * FROM sistemaweb.sis_empresa "
                + "WHERE sis_empresa.emp_ruc = '" + ruc.trim() + "'";
        SisEmpresa sisEmpresa = genericSQLDao.obtenerObjetoPorSql(sql, SisEmpresa.class);
        if (sisEmpresa != null) {
            return ConvertirSisEmpresa_SisEmpresaTO(sisEmpresa);
        }
        return null;
    }

    @Override
    public boolean existeEmpresaConElMismoNombre(String empresa, String nombre) throws Exception {
        nombre = nombre != null ? nombre.trim() : null;
        String sql = "SELECT * FROM sistemaweb.sis_empresa "
                + "WHERE sis_empresa.emp_codigo != '" + empresa + "'"
                + " AND sis_empresa.emp_nombre = '" + nombre + "'";
        SisEmpresa sisEmpresa = genericSQLDao.obtenerObjetoPorSql(sql, SisEmpresa.class);
        return sisEmpresa != null;
    }

    @Override
    public boolean existeEmpresaConLaMismaRazonSocial(String empresa, String razonSocial) throws Exception {
        razonSocial = razonSocial != null ? razonSocial.trim() : null;
        String sql = "SELECT * FROM sistemaweb.sis_empresa "
                + "WHERE sis_empresa.emp_codigo != '" + empresa + "'"
                + " AND sis_empresa.emp_razon_social = '" + razonSocial + "'";
        SisEmpresa sisEmpresa = genericSQLDao.obtenerObjetoPorSql(sql, SisEmpresa.class);
        return sisEmpresa != null;
    }

    @Override
    public List<SisEmpresaTO> getListaSisEmpresaWeb(String usrCodigo) throws Exception {
        String sql = "SELECT sis_empresa.emp_codigo, emp_ruc, emp_nombre, emp_razon_social, emp_direccion,"
                + "	emp_ciudad, emp_pais, emp_telefono, emp_celular, emp_email, emp_clave, par_resolucion_contribuyente_especial,"
                + "	par_obligado_llevar_contabilidad, par_gerente, par_gerente_tipo_id, par_gerente_id, par_contador,"
                + "	par_contador_ruc, emp_activa, sis_empresa.usr_codigo, sis_empresa.usr_fecha_inserta_empresa, par_actividad, par_escoger_precio_por,"
                + " par_financiero, par_financiero_id, par_columnas_estados_financieros, par_codigo_dinardap ,par_obligado_emitir_documentos_electronicos,"
                + " par_obligado_registrar_liquidacion_pesca, par_obligado_aprobar_pagos, par_contribuyente_regimen_microempresa,par_agente_retencion,is_exportadora "
                + " FROM sistemaweb.sis_usuario_detalle INNER JOIN sistemaweb.sis_empresa INNER JOIN sistemaweb.sis_empresa_parametros "
                + " ON sis_empresa.emp_codigo = sis_empresa_parametros.emp_codigo ON sis_usuario_detalle.det_empresa = sis_empresa.emp_codigo "
                + "WHERE sis_usuario_detalle.usr_codigo = '" + usrCodigo + "' AND det_activo ORDER BY sis_empresa.emp_nombre";

        return genericSQLDao.obtenerPorSql(sql, SisEmpresaTO.class);
    }

    @Override
    public boolean insertarSisEmpresa(SisEmpresa sisEmpresa, SisSuceso sisSuceso,
            SisEmpresaParametros sisEmpresaParametros) throws Exception {
        insertar(sisEmpresa);
        empresaParametrosDao.insertar(sisEmpresaParametros);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean insertarRegistrosComplementarios(String empCodigo) throws Exception {
        boolean creo = (boolean) genericSQLDao.obtenerObjetoPorSql(
                "select * from sistemaweb.fun_registros_complementarios('" + empCodigo.trim() + "')");
        return creo;
    }

    @Override
    public boolean modificarSisEmpresa(SisEmpresa sisEmpresa, SisSuceso sisSuceso,
            SisEmpresaParametros sisEmpresaParametros) throws Exception {
        actualizar(sisEmpresa);
        empresaParametrosDao.actualizar(sisEmpresaParametros);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean estadoLlevarContabilidad(String empCodigo) {
        String sql = "SELECT  par_obligado_llevar_contabilidad FROM sistemaweb.sis_empresa_parametros WHERE par_empresa = '" + empCodigo + "'";
        Object obj = (Object) genericSQLDao.obtenerObjetoPorSql(sql);
        return (boolean) obj;
    }
}
