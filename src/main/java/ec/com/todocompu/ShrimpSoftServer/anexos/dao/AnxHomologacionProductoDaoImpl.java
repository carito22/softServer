/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxHomologacionProducto;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author VALDIVIEZO
 */
@Repository
public class AnxHomologacionProductoDaoImpl extends GenericDaoImpl<AnxHomologacionProducto, Integer> implements AnxHomologacionProductoDao {

    @Autowired
    private SucesoDao sisSucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;

    @Override
    public AnxHomologacionProducto obtenerHomologacionProducto(String empresa, String codigoProductoProveedor, String identificacionProveedor) {
        codigoProductoProveedor = codigoProductoProveedor == null ? "" : codigoProductoProveedor.replaceAll("'", "");
        String sql = "SELECT * FROM anexo.anx_homologacion_producto WHERE hom_empresa='" + empresa
                + "' AND prov_identificacion='" + identificacionProveedor
                + "' AND hom_codigo_producto_proveedor='" + codigoProductoProveedor + "'";

        AnxHomologacionProducto homol = genericSQLDao.obtenerObjetoPorSql(sql, AnxHomologacionProducto.class);
        return homol;
    }

    @Override
    public AnxHomologacionProducto getAnxHomologacionProducto(Integer secuencia) throws Exception {
        return obtenerPorId(AnxHomologacionProducto.class, secuencia);
    }

    @Override
    public List<AnxHomologacionProducto> listarAnxHomologacionProducto(String empresa, String provIdentificacion, String busqueda, boolean incluirTodos) throws Exception {
        String sqlProveedor = provIdentificacion != null ? " AND prov_identificacion='" + provIdentificacion + "'" : "";

        String complementoSql = busqueda != null && !busqueda.equals("") ? (" AND  (prov_identificacion || hom_descripcion_producto_proveedor || prov_identificacion || hom_codigo_producto_proveedor "
                + "LIKE TRANSLATE(' ' || CASE WHEN ('" + busqueda + "') = '' THEN '~' ELSE ('" + busqueda + "') END || ' ', ' ', '%'))") : "";

        String sql = "SELECT * FROM anexo.anx_homologacion_producto WHERE hom_empresa = '" + empresa
                + "'" + sqlProveedor + complementoSql + (incluirTodos ? "" : " AND pro_empresa IS NULL AND pro_codigo_principal IS NULL ") + ";";
        List<AnxHomologacionProducto> listado = genericSQLDao.obtenerPorSql(sql, AnxHomologacionProducto.class);
        return listado;
    }

    @Override
    public boolean insertarListadoAnxHomologacionProducto(List<AnxHomologacionProducto> listado, List<SisSuceso> listadoSisSuceso) throws Exception {
        insertar(listado);
        sisSucesoDao.insertar(listadoSisSuceso);
        return true;
    }

    @Override
    public boolean insertarAnxHomologacionProducto(AnxHomologacionProducto anxHomologacionProducto, SisSuceso sisSuceso) throws Exception {
        insertar(anxHomologacionProducto);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean modificarAnxHomologacionProducto(AnxHomologacionProducto anxHomologacionProducto, SisSuceso sisSuceso) throws Exception {
        actualizar(anxHomologacionProducto);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean eliminarAnxHomologacionProducto(AnxHomologacionProducto anxHomologacionProducto, SisSuceso sisSuceso) throws Exception {
        eliminar(anxHomologacionProducto);
        sisSucesoDao.insertar(sisSuceso);
        return true;
    }

}
