package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoProformaDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaProformaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaCabeceraTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProformaClienteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformas;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasNumeracion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProformasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoProforma;
import java.util.ArrayList;

@Repository
public class ProformasDaoImpl extends GenericDaoImpl<InvProformas, InvProformasPK> implements ProformasDao {

    @Autowired
    private ProformasDetalleDao proformasDetalleDao;

    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private SucesoProformaDao sucesoProformaDao;

    @Override
    public void insertarInvProforma(InvProformas invProformas, List<InvProformasDetalle> listaInvProformasDetalles,
            SisSuceso sisSuceso, InvProformasNumeracion invProformasNumeracion, boolean nuevo) throws Exception {
        List<InvProformasDetalle> listaCopia = new ArrayList<>();
        insertar(invProformas);
        for (int i = 0; i < listaInvProformasDetalles.size(); i++) {
            listaInvProformasDetalles.get(i).setInvProformas(invProformas);
            proformasDetalleDao.insertar(listaInvProformasDetalles.get(i));
            InvProformasDetalle detalle = ConversionesInventario.convertirInvProformasDetalle_InvProformasDetalle(listaInvProformasDetalles.get(i));
            detalle.setInvProformas(null);
            listaCopia.add(detalle);
        }
        sucesoDao.insertar(sisSuceso);
        //////////////////suceso insertar//////////
        SisSucesoProforma sucesoProf = new SisSucesoProforma();
        InvProformas copia = ConversionesInventario.convertirInvProformas_InvProformas(invProformas);
        copia.setInvProformasDetalleList(listaCopia);
        String json = UtilsJSON.objetoToJson(copia);
        sucesoProf.setSusJson(json);
        sucesoProf.setSisSuceso(sisSuceso);
        sucesoProf.setInvProformas(copia);
        sucesoProformaDao.insertar(sucesoProf);
    }

    @Override
    public int buscarConteoUltimaNumeracionProforma(String empCodigo, String perCodigo, String motCodigo)
            throws Exception {
        String sql = "SELECT num_secuencia FROM inventario.inv_proformas_numeracion " + "WHERE num_empresa = ('"
                + empCodigo + "') AND num_periodo = " + "('" + perCodigo + "') AND num_motivo = ('" + motCodigo + "')";

        Object objeto = genericSQLDao.obtenerObjetoPorSql(sql);
        return objeto == null ? 0 : Integer.parseInt(objeto.toString());
    }

    @Override
    public boolean insertarTransaccionInvProformas(InvProformas invProformas,
            List<InvProformasDetalle> listaInvProformasDetalles, SisSuceso sisSuceso,
            InvProformasNumeracion invProformasNumeracion) throws Exception {
        String rellenarConCeros = "";
        int numeracion = buscarConteoUltimaNumeracionProforma(invProformas.getInvProformasPK().getProfEmpresa(),
                invProformas.getInvProformasPK().getProfPeriodo(), invProformas.getInvProformasPK().getProfMotivo());
        boolean nuevo = false;
        if (numeracion == 0) {
            nuevo = true;
        }

        do {
            numeracion++;
            int numeroCerosAponer = 7 - String.valueOf(numeracion).trim().length();
            rellenarConCeros = "";
            for (int i = 0; i < numeroCerosAponer; i++) {
                rellenarConCeros = rellenarConCeros + "0";
            }

            invProformas.setInvProformasPK(new InvProformasPK(invProformas.getInvProformasPK().getProfEmpresa(),
                    invProformas.getInvProformasPK().getProfPeriodo(), invProformas.getInvProformasPK().getProfMotivo(),
                    rellenarConCeros + numeracion));
            invProformasNumeracion.setNumSecuencia(rellenarConCeros + numeracion);

        } while (buscarInvProformas(invProformas.getInvProformasPK().getProfEmpresa(),
                invProformas.getInvProformasPK().getProfPeriodo(), invProformas.getInvProformasPK().getProfMotivo(),
                rellenarConCeros + numeracion) != null);

        sisSuceso.setSusClave(invProformas.getInvProformasPK().getProfPeriodo() + " "
                + invProformas.getInvProformasPK().getProfMotivo() + " "
                + invProformas.getInvProformasPK().getProfNumero());
        String detalle = "Proforma con el cliente: " + invProformas.getInvCliente().getCliRazonSocial();

        sisSuceso.setSusDetalle(detalle.length() > 300 ? detalle.substring(0, 300) : detalle);

        insertarInvProforma(invProformas, listaInvProformasDetalles, sisSuceso, invProformasNumeracion, nuevo);
        return true;
    }

    @Override
    public boolean modificarInvProformas(InvProformas invProformas, List<InvProformasDetalle> listaInvDetalle,
            List<InvProformasDetalle> listaInvDetalleEliminar, SisSuceso sisSuceso) throws Exception {
        List<InvProformasDetalle> listaCopia = new ArrayList<>();

        actualizar(invProformas);
        for (int i = 0; i < listaInvDetalle.size(); i++) {
            listaInvDetalle.get(i).setInvProformas(invProformas);
            proformasDetalleDao.saveOrUpdate(listaInvDetalle.get(i));
            InvProformasDetalle detalle = ConversionesInventario.convertirInvProformasDetalle_InvProformasDetalle(listaInvDetalle.get(i));
            detalle.setInvProformas(null);
            listaCopia.add(detalle);
        }

        if (!listaInvDetalleEliminar.isEmpty()) {
            for (int i = 0; i < listaInvDetalleEliminar.size(); i++) {
                listaInvDetalleEliminar.get(i).setInvProformas(invProformas);
                proformasDetalleDao.eliminar(listaInvDetalleEliminar.get(i));
            }
        }

        if (sisSuceso != null) {
            sucesoDao.insertar(sisSuceso);
            ////////////insertar suceso////////////////////////
            SisSucesoProforma sucesoProf = new SisSucesoProforma();
            InvProformas copia = ConversionesInventario.convertirInvProformas_InvProformas(invProformas);
            copia.setInvProformasDetalleList(listaCopia);
            String json = UtilsJSON.objetoToJson(copia);
            sucesoProf.setSusJson(json);
            sucesoProf.setSisSuceso(sisSuceso);
            sucesoProf.setInvProformas(copia);
            sucesoProformaDao.insertar(sucesoProf);
        }

        return true;
    }

    @Override
    public InvProformas buscarInvProformas(String empresa, String perCodigo, String motCodigo, String compNumero)
            throws Exception {
        return obtenerPorId(InvProformas.class, new InvProformasPK(empresa, perCodigo, motCodigo, compNumero));
    }

    @Override
    public InvProformaCabeceraTO getInvProformaCabeceraTO(String empresa, String periodo, String motivo,
            String numeroProforma) throws Exception {
        String sql = "SELECT SUBSTRING(cast(prof_fecha as TEXT), 1, 10) as prof_fecha, " + "prof_iva_vigente, "
                + "prof_observaciones, " + "prof_pendiente, " + "prof_anulado, " + "prof_base0, "
                + "prof_baseimponible, " + "prof_descuento_base0, " + "prof_descuento_baseimponible, "
                + "prof_subtotal_base0, " + "prof_subtotal_baseimponible, " + "prof_montoiva, " + "prof_total, "
                + "cli_empresa, " + "cli_codigo, " + "usr_empresa, " + "usr_codigo, "
                + "usr_fecha_inserta FROM inventario.inv_proformas "
                + "WHERE (inventario.inv_proformas.prof_empresa = '" + empresa + "') AND "
                + "inventario.inv_proformas.prof_periodo = ('" + periodo
                + "') AND inventario.inv_proformas.prof_motivo = ('" + motivo + "') "
                + "AND inventario.inv_proformas.prof_numero = ('" + numeroProforma + "')";

        return genericSQLDao.obtenerObjetoPorSql(sql, InvProformaCabeceraTO.class);

    }

    @Override
    public List<InvListaConsultaProformaTO> getListaInvConsultaProforma(String empresa, String periodo, String motivo,
            String busqueda) throws Exception {
        String sql = "SELECT * FROM inventario.fun_proformas_listado('" + empresa + "', '" + periodo + "', '" + motivo
                + "', '" + busqueda + "')";

        return genericSQLDao.obtenerPorSql(sql, InvListaConsultaProformaTO.class);
    }

    @Override
    public List<InvListaConsultaProformaTO> getListaInvConsultaProforma(String empresa, String periodo, String motivo, String busqueda, String nRegistros) throws Exception {
        String limit = "";
        if (nRegistros != null && nRegistros.compareTo("") != 0 && nRegistros.compareTo("0") != 0) {
            limit = " limit " + nRegistros;
        }
        String sql = "SELECT * FROM inventario.fun_proformas_listado('" + empresa + "', '" + periodo + "', '" + motivo + "', '" + busqueda + "')" + limit;

        return genericSQLDao.obtenerPorSql(sql, InvListaConsultaProformaTO.class);
    }

    @Override
    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivoTipo, String numero)
            throws Exception {
        String sql = "SELECT prof_pendiente as comp_pendiente, prof_anulado as comp_anulado, "
                + "false as est_bloqueado, false as est_generado, false as con_reversado FROM inventario.inv_proformas WHERE "
                + "prof_empresa = '" + empresa + "' AND prof_periodo = '" + periodo + "' " + "AND prof_motivo = '"
                + motivoTipo + "' AND prof_numero = '" + numero + "'";
        return genericSQLDao.obtenerPorSql(sql, InvEstadoCCCVT.class).isEmpty() ? null
                : genericSQLDao.obtenerPorSql(sql, InvEstadoCCCVT.class).get(0);
    }

    @Override
    public void actualizarPendienteSql(InvProformasPK invProformasPK, boolean pendiente) throws Exception {
        String sql = "UPDATE inventario.inv_proformas SET prof_pendiente=" + pendiente + " WHERE prof_empresa='"
                + invProformasPK.getProfEmpresa() + "' and  prof_periodo='"
                + invProformasPK.getProfPeriodo() + "' and prof_motivo='"
                + invProformasPK.getProfMotivo() + "' and prof_numero='"
                + invProformasPK.getProfNumero() + "';";
        genericSQLDao.ejecutarSQL(sql);
    }

    @Override
    public void anularRestaurarSql(InvProformasPK invProformasPK, boolean anulado) throws Exception {
        String sql = "UPDATE inventario.inv_proformas SET prof_anulado=" + anulado + " WHERE prof_empresa='" + invProformasPK.getProfEmpresa() + "' and  prof_periodo='" + invProformasPK.getProfPeriodo() + "' and \n"
                + "        prof_motivo='" + invProformasPK.getProfMotivo() + "' and prof_numero='" + invProformasPK.getProfNumero() + "';";
        genericSQLDao.ejecutarSQL(sql);
    }

    @Override
    public List<InvProformaClienteTO> listarInvProformaClienteTO(String empresa, String cliCodigo) throws Exception {
        String sql = "SELECT * FROM inventario.fun_proformas_saldo_cliente('" + empresa + "', '" + cliCodigo + "')";
        return genericSQLDao.obtenerPorSql(sql, InvProformaClienteTO.class);
    }

}
