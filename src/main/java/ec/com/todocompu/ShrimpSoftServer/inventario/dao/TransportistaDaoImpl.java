/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransportistaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvNumeracionVarios;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransportista;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransportistaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CarolValdiviezo
 */
@Repository
public class TransportistaDaoImpl extends GenericDaoImpl<InvTransportista, InvTransportistaPK> implements TransportistaDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private NumeracionVariosDao numeracionVariosDao;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public List<InvTransportistaTO> getListaInvTransportistaTO(String empresa, String busqueda, boolean incluirInactivo) throws Exception {
        String sqlBusqueda = "";
        if (busqueda != null && !"".equals(busqueda)) {
            sqlBusqueda = "AND (trans_codigo || COALESCE(trans_id_numero,'') || trans_nombres  "
                    + "LIKE TRANSLATE(' ' || CASE WHEN ('" + busqueda + "') = ''  THEN '~' ELSE ('" + busqueda
                    + "') END || ' ', ' ', '%')) ";
        }
        String sql = "SELECT * FROM inventario.inv_transportista WHERE "
                + (incluirInactivo ? "" : "NOT trans_inactivo AND ") + " inv_transportista.trans_empresa = ('" + empresa + "') "
                + sqlBusqueda + "ORDER BY trans_nombres";

        return genericSQLDao.obtenerPorSql(sql, InvTransportistaTO.class);
    }

    @Override
    public boolean getTransportistaRepetido(String empresa, String codigo, String id, String nombre) throws Exception {
        empresa = empresa.compareTo("") == 0 ? null : empresa;
        codigo = codigo == null || codigo.compareTo("") == 0 ? null : codigo;
        id = id.compareTo("") == 0 ? null : id;
        nombre = nombre == null || nombre.compareTo("") == 0 ? null : nombre;

        String sql = "SELECT * FROM inventario." + "fun_sw_transportista_repetido(" + empresa + ", " + codigo + ", " + id + ", " + nombre + ")";

        return (boolean) UtilsConversiones.convertir(genericSQLDao.obtenerPorSql(sql).get(0));
    }

    @Override
    public boolean modificarInvTransportistaLlavePrincipal(InvTransportista invTransportistaEliminar, InvTransportista invTransportista, SisSuceso sisSuceso) throws Exception {
        eliminar(invTransportistaEliminar);
        actualizar(invTransportista);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public String getInvProximaNumeracionTransportista(String empresa, InvTransportistaTO invTransportistaTO) throws Exception {
        String sql = "SELECT num_transportistas FROM inventario.inv_numeracion_varios WHERE num_empresa = '" + empresa + "'";

        String consulta = (String) UtilsConversiones.convertir(genericSQLDao.obtenerObjetoPorSql(sql));
        if (consulta != null) {
            invTransportistaTO.setTransCodigo(consulta);
        } else {
            invTransportistaTO.setTransCodigo("00000");
        }

        int numeracion = Integer.parseInt(invTransportistaTO.getTransCodigo());
        String rellenarConCeros = "";
        do {
            numeracion++;
            int numeroCerosAponer = 5 - String.valueOf(numeracion).trim().length();
            rellenarConCeros = "";
            for (int i = 0; i < numeroCerosAponer; i++) {
                rellenarConCeros = rellenarConCeros + "0";
            }

            invTransportistaTO.setTransCodigo(rellenarConCeros + numeracion);

        } while (buscarInvTransportista(invTransportistaTO.getTransEmpresa(), invTransportistaTO.getTransCodigo()) != null);
        return rellenarConCeros + numeracion;
    }

    @Override
    public InvTransportista buscarInvTransportista(String empresa, String codigo) throws Exception {
        return obtenerPorId(InvTransportista.class, new InvTransportistaPK(empresa, codigo));
    }

    @Override
    public boolean eliminarInvTransportista(InvTransportista invTransportista, SisSuceso sisSuceso) throws Exception {
        eliminar(invTransportista);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

    @Override
    public boolean insertarInvTransportista(InvTransportista invTransportista, SisSuceso sisSuceso, InvNumeracionVarios invNumeracionVarios) throws Exception {
        insertar(invTransportista);
        sucesoDao.insertar(sisSuceso);
        if (invNumeracionVarios != null) {
            numeracionVariosDao.actualizar(invNumeracionVarios);
        }
        return true;
    }

    @Override
    public boolean modificarInvTransportista(InvTransportista invTransportista, SisSuceso sisSuceso) throws Exception {
        actualizar(invTransportista);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

}
