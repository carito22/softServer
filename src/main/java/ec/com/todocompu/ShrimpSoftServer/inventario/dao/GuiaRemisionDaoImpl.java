/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoGuiaRemisionDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsExcepciones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaGuiaRemisionPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsultaGuiaRemisionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvGuiaRemisionDetalleTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvGuiaRemisionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemision;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvGuiaRemisionPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoGuiaRemision;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author CarolValdiviezo
 */
@Repository
public class GuiaRemisionDaoImpl extends GenericDaoImpl<InvGuiaRemision, InvGuiaRemisionPK> implements GuiaRemisionDao {

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GuiaRemisionDetalleDao guiaRemisionDetalleDao;
    @Autowired
    private SucesoGuiaRemisionDao sucesoGuiaRemisionDao;

    @Override
    public int buscarConteoUltimaNumeracionGuiaRemision(String empCodigo, String periodo) {
        String sql = "SELECT num_secuencia FROM inventario.inv_guias_numeracion WHERE num_empresa = ('" + empCodigo + "') AND num_periodo = ('" + periodo + "')";
        Object obj = (Object) genericSQLDao.obtenerObjetoPorSql(sql);
        if (obj != null) {
            return new Integer(String.valueOf(UtilsConversiones.convertir(obj)));
        }
        return 0;
    }

    @Override
    public String getConteoNumeroGuiaRemision(String empresaCodigo, String transCodigo, String compDocumentoNumero, InvGuiaRemisionPK pk) throws Exception {
        String sqlPk = "";
        if (pk != null) {
            sqlPk = " AND guia_empresa != ('" + pk.getGuiaEmpresa() + "') AND "
                    + "guia_periodo != ('" + pk.getGuiaPeriodo() + "') AND "
                    + "guia_numero != ('" + pk.getGuiaNumero() + "') ";
        }

        String sql = "SELECT guia_documento_numero FROM inventario.inv_guia_remision WHERE guia_empresa = ('" + empresaCodigo + "') "
                + " AND guia_documento_numero = ('" + compDocumentoNumero + "')"
                + " AND trans_codigo = ('" + transCodigo + "')" + sqlPk;
        return (String) genericSQLDao.obtenerObjetoPorSql(sql);
    }

    @Override
    public List<InvConsultaGuiaRemisionTO> getListaInvGuiaRemisionTO(String empresa, String tipoDocumento, String fechaInicio, String fechaFinal, String nRegistros) {

        String limit = "";
        String tipoDocumentoSql = null;
        String fechaInicioSql = null;
        String fechaFinalSql = null;

        if (tipoDocumento != null && !tipoDocumento.equalsIgnoreCase("")) {
            tipoDocumentoSql = "'" + tipoDocumento + "'";
        }

        if (fechaInicio != null && !fechaInicio.equalsIgnoreCase("")) {
            fechaInicioSql = "'" + fechaInicio + "'";
        }

        if (fechaFinal != null && !fechaFinal.equalsIgnoreCase("")) {
            fechaFinalSql = "'" + fechaFinal + "'";
        }

        if (nRegistros != null && nRegistros.compareTo("") != 0 && nRegistros.compareTo("0") != 0) {
            limit = " limit " + nRegistros;
        }
        String sql = "SELECT * FROM inventario.fun_guia_remision_listado('" + empresa + "'," + tipoDocumentoSql + "," + fechaInicioSql + ", " + fechaFinalSql + ")" + " ORDER BY guia_periodo, guia_numero DESC " + limit;

        return genericSQLDao.obtenerPorSql(sql, InvConsultaGuiaRemisionTO.class);
    }

    @Override
    public boolean insertarTransaccionInvGuiaRemision(InvGuiaRemision invGuiaRemision, List<InvGuiaRemisionDetalle> listaInvGuiaRemisionDetalles, SisSuceso sisSuceso) throws Exception {
        String rellenarConCeros = "";
        int numeracion = buscarConteoUltimaNumeracionGuiaRemision(invGuiaRemision.getInvGuiaRemisionPK().getGuiaEmpresa(), invGuiaRemision.getInvGuiaRemisionPK().getGuiaPeriodo());
        do {
            numeracion++;
            int numeroCerosAponer = 7 - String.valueOf(numeracion).trim().length();
            rellenarConCeros = "";
            for (int i = 0; i < numeroCerosAponer; i++) {
                rellenarConCeros = rellenarConCeros + "0";
            }
            invGuiaRemision.setInvGuiaRemisionPK(new InvGuiaRemisionPK(invGuiaRemision.getInvGuiaRemisionPK().getGuiaEmpresa(), invGuiaRemision.getInvGuiaRemisionPK().getGuiaPeriodo(), rellenarConCeros + numeracion));

        } while (buscarInvGuiaRemision(invGuiaRemision.getInvGuiaRemisionPK().getGuiaEmpresa(), invGuiaRemision.getInvGuiaRemisionPK().getGuiaPeriodo(), rellenarConCeros + numeracion) != null);
        sisSuceso.setSusClave(invGuiaRemision.getInvGuiaRemisionPK().getGuiaEmpresa() + " " + " " + invGuiaRemision.getInvGuiaRemisionPK().getGuiaNumero());
        String detalle = "";
        detalle = "Guía de remisión a " + invGuiaRemision.getInvCliente().getCliIdNumero() + " según documento # " + invGuiaRemision.getGuiaDocumentoNumero();
        sisSuceso.setSusDetalle(detalle);
        insertarInvGuiaRemision(invGuiaRemision, listaInvGuiaRemisionDetalles, sisSuceso);
        return true;
    }

    @Override
    public boolean modificarTransaccionInvGuiaRemision(InvGuiaRemision invGuiaRemision, List<InvGuiaRemisionDetalle> listaInvGuiaRemisionDetalles, List<InvGuiaRemisionDetalle> listaInvGuiaRemisionDetallesEliminar, SisSuceso sisSuceso) throws Exception {
        sisSuceso.setSusClave(invGuiaRemision.getInvGuiaRemisionPK().getGuiaEmpresa() + " " + " " + invGuiaRemision.getInvGuiaRemisionPK().getGuiaNumero());
        String detalle = "";
        detalle = "Guía de remisión a " + invGuiaRemision.getInvCliente().getCliIdNumero() + " según documento # " + invGuiaRemision.getGuiaDocumentoNumero();
        sisSuceso.setSusDetalle(detalle);
        modificarInvGuiaRemision(invGuiaRemision, listaInvGuiaRemisionDetalles, listaInvGuiaRemisionDetallesEliminar, sisSuceso);
        return true;
    }

    @Override
    public InvGuiaRemision buscarInvGuiaRemision(String empresa, String periodo, String compNumero) throws Exception {
        InvGuiaRemision guia = obtenerPorIdEvict(InvGuiaRemision.class, new InvGuiaRemisionPK(empresa, periodo, compNumero));
        return guia;
    }

    @Override
    public void insertarInvGuiaRemision(InvGuiaRemision invGuiaRemision, List<InvGuiaRemisionDetalle> listaInvGuiaRemisionDetalles, SisSuceso sisSuceso) throws Exception {
        insertar(invGuiaRemision);
        for (int i = 0; i < listaInvGuiaRemisionDetalles.size(); i++) {
            listaInvGuiaRemisionDetalles.get(i).setInvGuiaRemision(invGuiaRemision);
            guiaRemisionDetalleDao.insertar(listaInvGuiaRemisionDetalles.get(i));
        }
        sucesoDao.insertar(sisSuceso);
        //crear suceso
        SisSucesoGuiaRemision sucesoGuiaRemision = new SisSucesoGuiaRemision();
        InvGuiaRemision copia = ConversionesInventario.convertirInvGuiaRemision_InvGuiaRemision(invGuiaRemision);
        List<InvGuiaRemisionDetalle> detalleCopia = new ArrayList<>();
        for (InvGuiaRemisionDetalle en : listaInvGuiaRemisionDetalles) {
            InvGuiaRemisionDetalle det = ConversionesInventario.convertirInvGuiaRemisionDetalles_InvGuiaRemisionDetalle(en);
            det.setInvGuiaRemision(null);
            detalleCopia.add(det);
        }
        String json = UtilsJSON.objetoToJson(copia);
        String jsonListado = UtilsJSON.objetoToJson(detalleCopia);
        if (json != null && !json.equals("") && jsonListado != null && !jsonListado.equals("")) {
            json = json.substring(0, json.length() - 1) + "," + "\"invGuiaRemisionDetalle\"" + ":" + jsonListado + "}";
        }
        sucesoGuiaRemision.setSusJson(json);
        sucesoGuiaRemision.setSisSuceso(sisSuceso);
        sucesoGuiaRemision.setInvGuiaRemision(copia);
        sucesoGuiaRemisionDao.insertar(sucesoGuiaRemision);
    }

    @Override
    public void modificarInvGuiaRemision(InvGuiaRemision invGuiaRemision, List<InvGuiaRemisionDetalle> listaInvGuiaRemisionDetalles, List<InvGuiaRemisionDetalle> listaInvGuiaRemisionDetallesEliminar, SisSuceso sisSuceso) throws Exception {
        actualizar(invGuiaRemision);
        for (int i = 0; i < listaInvGuiaRemisionDetalles.size(); i++) {
            listaInvGuiaRemisionDetalles.get(i).setInvGuiaRemision(invGuiaRemision);
            guiaRemisionDetalleDao.saveOrUpdate(listaInvGuiaRemisionDetalles.get(i));
        }

        if (!listaInvGuiaRemisionDetallesEliminar.isEmpty()) {
            for (int i = 0; i < listaInvGuiaRemisionDetallesEliminar.size(); i++) {
                listaInvGuiaRemisionDetallesEliminar.get(i).setInvGuiaRemision(invGuiaRemision);
                guiaRemisionDetalleDao.eliminar(listaInvGuiaRemisionDetallesEliminar.get(i));
            }
        }

        sucesoDao.insertar(sisSuceso);
        //crear suceso
        SisSucesoGuiaRemision sucesoGuiaRemision = new SisSucesoGuiaRemision();
        InvGuiaRemision copia = ConversionesInventario.convertirInvGuiaRemision_InvGuiaRemision(invGuiaRemision);

        List<InvGuiaRemisionDetalle> detalleCopia = new ArrayList<>();
        for (InvGuiaRemisionDetalle en : listaInvGuiaRemisionDetalles) {
            InvGuiaRemisionDetalle det = ConversionesInventario.convertirInvGuiaRemisionDetalles_InvGuiaRemisionDetalle(en);
            det.setInvGuiaRemision(null);
            detalleCopia.add(det);
        }

        String json = UtilsJSON.objetoToJson(copia);
        String jsonListado = UtilsJSON.objetoToJson(detalleCopia);
        if (json != null && !json.equals("") && jsonListado != null && !jsonListado.equals("")) {
            json = json.substring(0, json.length() - 1) + "," + "\"invGuiaRemisionDetalle\"" + ":" + jsonListado + "}";
        }
        sucesoGuiaRemision.setSusJson(json);
        sucesoGuiaRemision.setSisSuceso(sisSuceso);
        sucesoGuiaRemision.setInvGuiaRemision(copia);
        sucesoGuiaRemisionDao.insertar(sucesoGuiaRemision);
    }

    @Override
    public List<InvGuiaRemisionDetalle> obtenerGuiaRemisionDetallePorNumero(String empresa, String periodo, String numero) throws Exception {
        String sql = "SELECT * " + "FROM inventario.inv_guia_remision_detalle WHERE guia_empresa = ('" + empresa + "') "
                + "AND guia_periodo = ('" + periodo + "') AND guia_numero = ('" + numero + "') " + "Order by det_secuencial ASC";

        return genericSQLDao.obtenerPorSql(sql, InvGuiaRemisionDetalle.class);
    }

    @Override
    public List<InvGuiaRemisionDetalleTO> obtenerGuiaRemisionDetalleTO(String empresa, String periodo, String numero) throws Exception {
        List<InvGuiaRemisionDetalle> listaDetalle = obtenerGuiaRemisionDetallePorNumero(empresa, periodo, numero);
        List<InvGuiaRemisionDetalleTO> listaDetalleTO = new ArrayList<>();
        int i = 0;
        for (InvGuiaRemisionDetalle item : listaDetalle) {
            InvGuiaRemisionDetalleTO detalleTO = new InvGuiaRemisionDetalleTO();
            detalleTO.setDetCantidad(item.getDetCantidad());
            detalleTO.setDetOrden(i);
            detalleTO.setDetSecuencia(item.getDetSecuencial());
            detalleTO.setGuiaEmpresa(item.getInvGuiaRemision().getInvGuiaRemisionPK().getGuiaEmpresa());
            detalleTO.setGuiaNumero(item.getInvGuiaRemision().getInvGuiaRemisionPK().getGuiaNumero());
            detalleTO.setGuiaPeriodo(item.getInvGuiaRemision().getInvGuiaRemisionPK().getGuiaPeriodo());
            detalleTO.setMedidaProducto(item.getInvProducto().getInvProductoMedida().getMedDetalle());
            detalleTO.setNombreProducto(item.getNombreProducto());
            detalleTO.setProCodigoPrincipal(item.getInvProducto().getInvProductoPK().getProCodigoPrincipal());
            detalleTO.setProCodigoPrincipalCopia(item.getInvProducto().getInvProductoPK().getProCodigoPrincipal());
            detalleTO.setProEmpresa(item.getInvProducto().getInvProductoPK().getProEmpresa());
            i++;
            listaDetalleTO.add(detalleTO);

        }
        return listaDetalleTO;
    }

    @Override
    public void anularRestaurarSql(InvGuiaRemisionPK pk, boolean anulado) throws Exception {
        String sql = "UPDATE inventario.inv_guia_remision SET guia_anulado=" + anulado
                + " WHERE guia_empresa='" + pk.getGuiaEmpresa() + "' and  guia_periodo='" + pk.getGuiaPeriodo()
                + "' and guia_numero='" + pk.getGuiaNumero() + "';";
        genericSQLDao.ejecutarSQL(sql);
    }

    @Override
    public void desmayorizarRestaurarSql(InvGuiaRemisionPK pk, boolean pendiente) throws Exception {
        String sql = "UPDATE inventario.inv_guia_remision SET guia_pendiente=" + pendiente
                + " WHERE guia_empresa='" + pk.getGuiaEmpresa() + "' and  guia_periodo='" + pk.getGuiaPeriodo()
                + "' and guia_numero='" + pk.getGuiaNumero() + "';";
        genericSQLDao.ejecutarSQL(sql);
    }

    @Override
    public List<AnxListaGuiaRemisionPendientesTO> getListaGuiaRemisionPendientes(String empresa) throws Exception {
        String sql = "SELECT * FROM anexo.fun_guia_remision_electronicas_listado('" + empresa + "')";
        return genericSQLDao.obtenerPorSql(sql, AnxListaGuiaRemisionPendientesTO.class);
    }

    @Override
    public String eliminarGuiaRemision(String empresa, String periodo, String numero, SisSuceso sisSuceso) throws Exception {
        String sql = "SELECT * FROM inventario.fun_eliminar_guia('" + empresa + "', '" + periodo + "', '" + numero + "')";
        String respuesta = "";
        try {
            String respuestaFuncion = (String) genericSQLDao.obtenerObjetoPorSql(sql);
            if (respuestaFuncion.substring(0, 1).equalsIgnoreCase("T")) {
                sucesoDao.insertar(sisSuceso);
                respuesta = "t";
            } else {
                respuesta = respuesta + ". N: " + sisSuceso.getSusClave();
            }
        } catch (Exception e) {
            e = (Exception) UtilsExcepciones.obtenerErrorPostgreSQL(e);
            respuesta = e.getMessage() + ". N: " + sisSuceso.getSusClave();
        }
        return respuesta;
    }

    @Override
    public InvGuiaRemisionTO buscarInvGuiaRemision(String empresa, String numeroDocumento) throws Exception {
        String sql = "SELECT * " + "FROM inventario.inv_guia_remision WHERE guia_empresa = ('" + empresa + "') "
                + "AND guia_documento_numero = ('" + numeroDocumento + "') ";
        InvGuiaRemisionTO guiaTO = null;
        InvGuiaRemision guia = genericSQLDao.obtenerObjetoPorSql(sql, InvGuiaRemision.class);
        if (guia != null) {
            guiaTO = ConversionesInventario.convertirInvGuiaRemision_InvGuiaRemisionTO(guia);
        }
        return guiaTO;
    }

    @Override
    public Boolean actualizarClaveExterna(InvGuiaRemisionPK pk, String clave, SisSuceso sisSuceso) throws Exception {
        String sql = "UPDATE inventario.inv_guia_remision SET guia_clave_acceso_externa=" + clave
                + " WHERE guia_empresa='" + pk.getGuiaEmpresa()
                + "' and guia_periodo='" + pk.getGuiaPeriodo()
                + "' and guia_numero='" + pk.getGuiaNumero()
                + "';";
        genericSQLDao.ejecutarSQL(sql);
        sucesoDao.insertar(sisSuceso);
        return true;
    }

}
