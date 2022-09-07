package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoTransferenciaDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsConversiones;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvEstadoCCCVT;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsultaTransferenciaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvTransferenciasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoSaldos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferencias;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasDetalleSeries;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasMotivoAnulacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvTransferenciasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoTransferencia;
import java.util.ArrayList;

@Repository
public class TransferenciasDaoImpl extends GenericDaoImpl<InvTransferencias, InvTransferenciasPK>
        implements TransferenciasDao {
    
    @Autowired
    private TransferenciasDetalleDao transferenciasDetalleDao;
    @Autowired
    private TransferenciasMotivoAnulacionDao transferenciasMotivoAnulacionDao;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private TransferenciasDetalleSeriesDao transferenciasDetalleSeriesDao;
    @Autowired
    private SucesoTransferenciaDao sucesoTransferenciaDao;
    
    @Override
    public void insertarInvTransferencias(InvTransferencias invTransferencias,
            List<InvTransferenciasDetalle> listaInvTransferenciasDetalle, SisSuceso sisSuceso)
            throws Exception {
        // Condicion para que invTransferencia mantenga su estado pendiente: false o true
        boolean pendiente = invTransferencias.getTransPendiente();
        if (!pendiente) {
            invTransferencias.setTransPendiente(false);
        } else {
            invTransferencias.setTransPendiente(true);/**/
            
        }
        insertar(invTransferencias);
        List<InvTransferenciasDetalle> listadoDetalleSuceso = new ArrayList<>();
        for (int i = 0; i < listaInvTransferenciasDetalle.size(); i++) {
            listaInvTransferenciasDetalle.get(i).setInvTransferencias(invTransferencias);
            listaInvTransferenciasDetalle.get(i).setDetPendiente(true);
            transferenciasDetalleDao.insertar(listaInvTransferenciasDetalle.get(i));
            //detalle suceso
            InvTransferenciasDetalle det = ConversionesInventario.convertirInvTransferenciasDetalle_InvTransferenciasDetalle(listaInvTransferenciasDetalle.get(i));
            det.setInvTransferencias(new InvTransferencias(listaInvTransferenciasDetalle.get(i).getInvTransferencias().getInvTransferenciasPK()));
            if (det.getInvTransferenciasDetalleSeriesList() != null && det.getInvTransferenciasDetalleSeriesList().size() > 0) {
                for (InvTransferenciasDetalleSeries en : det.getInvTransferenciasDetalleSeriesList()) {
                    en.setDetSecuencialTransferencia(new InvTransferenciasDetalle(listaInvTransferenciasDetalle.get(i).getDetSecuencial()));
                }
            }
            listadoDetalleSuceso.add(det);
        }
        sucesoDao.insertar(sisSuceso);
        ////////////////insertar suceso////////////
        SisSucesoTransferencia sucesoTrans = new SisSucesoTransferencia();
        InvTransferencias copia = ConversionesInventario.convertirInvTransferencias_InvTransferencias(invTransferencias);
        copia.setInvTransferenciasMotivoAnulacionList(new ArrayList<>());
        copia.setInvTransferenciasDetalleList(listadoDetalleSuceso);
        String json = UtilsJSON.objetoToJson(copia);
        sucesoTrans.setSusJson(json);
        sucesoTrans.setSisSuceso(sisSuceso);
        sucesoTrans.setInvTransferencias(copia);
        sucesoTransferenciaDao.insertar(sucesoTrans);
    }
    
    @Override
    public int buscarConteoUltimaNumeracionTransferencias(String empCodigo, String perCodigo, String motCodigo)
            throws Exception {
        String sql = "SELECT num_secuencia FROM " + "inventario.inv_transferencias_numeracion WHERE num_empresa = ('"
                + empCodigo + "') " + "AND num_periodo = ('" + perCodigo + "') AND num_motivo = ('" + motCodigo + "')";
        Object retorno = genericSQLDao.obtenerObjetoPorSql(sql);
        if (retorno == null) {
            return 0;
        } else {
            return Integer.parseInt(UtilsConversiones.convertir(retorno).toString());
        }
    }
    
    @Override
    public boolean insertarTransaccionInvTransferencias(InvTransferencias invTransferencias,
            List<InvTransferenciasDetalle> listaInvTransferenciasDetalles, SisSuceso sisSuceso) throws Exception {
        
        String rellenarConCeros = "";
        int numeracion = buscarConteoUltimaNumeracionTransferencias(
                invTransferencias.getInvTransferenciasPK().getTransEmpresa(),
                invTransferencias.getInvTransferenciasPK().getTransPeriodo(),
                invTransferencias.getInvTransferenciasPK().getTransMotivo());
        do {
            numeracion++;
            int numeroCerosAponer = 7 - String.valueOf(numeracion).trim().length();
            rellenarConCeros = "";
            for (int i = 0; i < numeroCerosAponer; i++) {
                rellenarConCeros = rellenarConCeros + "0";
            }
            
            invTransferencias.setInvTransferenciasPK(new InvTransferenciasPK(
                    invTransferencias.getInvTransferenciasPK().getTransEmpresa(),
                    invTransferencias.getInvTransferenciasPK().getTransPeriodo(),
                    invTransferencias.getInvTransferenciasPK().getTransMotivo(), rellenarConCeros + numeracion));
        } while (buscarInvTransferencias(invTransferencias.getInvTransferenciasPK().getTransEmpresa(),
                invTransferencias.getInvTransferenciasPK().getTransPeriodo(),
                invTransferencias.getInvTransferenciasPK().getTransMotivo(), rellenarConCeros + numeracion) != null);
        
        sisSuceso.setSusClave(invTransferencias.getInvTransferenciasPK().getTransPeriodo() + " "
                + invTransferencias.getInvTransferenciasPK().getTransMotivo() + " "
                + invTransferencias.getInvTransferenciasPK().getTransNumero());
        
        sisSuceso.setSusDetalle("Se insertó la transferencia en el periodo "
                + invTransferencias.getInvTransferenciasPK().getTransPeriodo() + ", del motivo "
                + invTransferencias.getInvTransferenciasPK().getTransMotivo() + ", de la numeración "
                + invTransferencias.getInvTransferenciasPK().getTransNumero());
        
        insertarInvTransferencias(invTransferencias, listaInvTransferenciasDetalles, sisSuceso);
        return true;
    }
    
    @Override
    public boolean modificarInvTransferencias(InvTransferencias invTransferencias,
            List<InvTransferenciasDetalle> listaInvDetalle, List<InvTransferenciasDetalle> listaInvDetalleEliminar,
            SisSuceso sisSuceso, List<InvProductoSaldos> listaInvProductoSaldosOrigen,
            List<InvProductoSaldos> listaInvProductoSaldosDestino,
            InvTransferenciasMotivoAnulacion invTransferenciasMotivoAnulacion, boolean desmayorizar) throws Exception {
        if (!invTransferencias.getTransAnulado()) {
            invTransferencias.setTransPendiente(true);
        }
        boolean ignorarSeries = false;
        actualizar(invTransferencias);
        List<InvTransferenciasDetalle> listadoDetalleSuceso = new ArrayList<>();
        if (!invTransferencias.getTransAnulado()) {
            if (!desmayorizar) {
                for (int i = 0; i < listaInvDetalle.size(); i++) {
                    listaInvDetalle.get(i).setDetPendiente(true);
                    listaInvDetalle.get(i).setInvTransferencias(invTransferencias);

                    //************SERIES*********************
                    List<InvTransferenciasDetalleSeries> listaSerieSuceso = new ArrayList<>();
                    if (listaInvDetalle.get(i).getDetSecuencial() != null && !ignorarSeries) {
                        List<InvTransferenciasDetalleSeries> seriesEnLaBase = transferenciasDetalleSeriesDao.listarSeriesPorSecuencialDetalle(listaInvDetalle.get(i).getDetSecuencial());
                        if (seriesEnLaBase != null && !seriesEnLaBase.isEmpty()) {
                            List<InvTransferenciasDetalleSeries> seriesEntrantes = listaInvDetalle.get(i).getInvTransferenciasDetalleSeriesList();
                            if (seriesEntrantes == null) {
                                seriesEntrantes = new ArrayList<>();
                            }
                            seriesEnLaBase.removeAll(seriesEntrantes);
                            seriesEnLaBase.forEach((serie) -> {
                                transferenciasDetalleSeriesDao.eliminar(serie);
                            });
                        }
                    }
                    //FIN SERIES
                    transferenciasDetalleDao.saveOrUpdate(listaInvDetalle.get(i));
                    //detalle suceso
                    InvTransferenciasDetalle det = ConversionesInventario.convertirInvTransferenciasDetalle_InvTransferenciasDetalle(listaInvDetalle.get(i));
                    det.setInvTransferencias(new InvTransferencias(listaInvDetalle.get(i).getInvTransferencias().getInvTransferenciasPK()));
                    if (det.getInvTransferenciasDetalleSeriesList() != null && det.getInvTransferenciasDetalleSeriesList().size() > 0) {
                        for (InvTransferenciasDetalleSeries en : det.getInvTransferenciasDetalleSeriesList()) {
                            en.setDetSecuencialTransferencia(new InvTransferenciasDetalle(listaInvDetalle.get(i).getDetSecuencial()));
                        }
                    }
                    listadoDetalleSuceso.add(det);
                }
            }
        }
        
        if (!listaInvDetalleEliminar.isEmpty()) {
            for (int i = 0; i < listaInvDetalleEliminar.size(); i++) {
                listaInvDetalleEliminar.get(i).setInvTransferencias(invTransferencias);
                transferenciasDetalleDao.eliminar(listaInvDetalleEliminar.get(i));
            }
        }
        
        sucesoDao.insertar(sisSuceso);
        ////////////////insertar suceso////////////
        SisSucesoTransferencia sucesoTrans = new SisSucesoTransferencia();
        InvTransferencias copia = ConversionesInventario.convertirInvTransferencias_InvTransferencias(invTransferencias);
        copia.setInvTransferenciasDetalleList(listadoDetalleSuceso);
        copia.setInvTransferenciasMotivoAnulacionList(new ArrayList<>());
        String json = UtilsJSON.objetoToJson(copia);
        sucesoTrans.setSusJson(json);
        sucesoTrans.setSisSuceso(sisSuceso);
        sucesoTrans.setInvTransferencias(copia);
        sucesoTransferenciaDao.insertar(sucesoTrans);
        if (invTransferenciasMotivoAnulacion != null) {
            transferenciasMotivoAnulacionDao.insertar(invTransferenciasMotivoAnulacion);
        }
        return true;
    }
    
    @Override
    public InvTransferencias buscarInvTransferencias(String empresa, String perCodigo, String transCodigo,
            String transNumero) throws Exception {
        return obtenerPorId(InvTransferencias.class,
                new InvTransferenciasPK(empresa, perCodigo, transCodigo, transNumero));
    }
    
    @Override
    public InvTransferenciasTO getInvTransferenciasCabeceraTO(String empresa, String periodo, String motivo,
            String numeroConsumo) throws Exception {
        return ConversionesInventario.convertirInvTransferenciasCabecera_InvTransferenciasCabeceraTO(obtenerPorId(
                InvTransferencias.class, new InvTransferenciasPK(empresa, periodo, motivo, numeroConsumo)));
    }
    
    @Override
    public List<InvListaConsultaTransferenciaTO> getFunListadoTransferencias(String empresa, String fechaDesde,
            String fechaHasta, String status) throws Exception {
        String pendiente = null;
        String anulado = null;
        if (status.equals("PENDIENTE")) {
            pendiente = "'PENDIENTE'";
            anulado = null;
        }
        if (status.equals("ANULADO")) {
            pendiente = null;
            anulado = "'ANULADO'";
        }
        if (status.equals("AMBOS")) {
            pendiente = "'PENDIENTE'";
            anulado = "'ANULADO'";
        }
        fechaDesde = fechaDesde == null ? fechaDesde : "'" + fechaDesde + "'";
        fechaHasta = fechaHasta == null ? fechaHasta : "'" + fechaHasta + "'";
        String sql = "SELECT * FROM inventario.fun_listado_transferencias('" + empresa + "', null, null, null) "
                + "WHERE trans_fecha >= " + fechaDesde + " AND trans_fecha <= " + fechaHasta + " "
                + "AND trans_pendiente = " + pendiente + " OR trans_anulado = " + anulado;
        return genericSQLDao.obtenerPorSql(sql, InvListaConsultaTransferenciaTO.class);
    }
    
    @Override
    public List<InvListaConsultaTransferenciaTO> getListaInvConsultaTransferencia(String empresa, String periodo,
            String motivo, String busqueda, String nRegistros) throws Exception {
        String limit = "";
        if (nRegistros != null && nRegistros.compareTo("") != 0 && nRegistros.compareTo("0") != 0) {
            limit = " limit " + nRegistros;
        }
        String sql = "SELECT * FROM inventario.fun_listado_transferencias('" + empresa + "','" + periodo + "','"
                + motivo + "','" + busqueda + "')" + " Order by id DESC " + limit;
        return genericSQLDao.obtenerPorSql(sql, InvListaConsultaTransferenciaTO.class);
    }
    
    @Override
    public InvEstadoCCCVT getEstadoCCCVT(String empresa, String periodo, String motivoTipo, String numero)
            throws Exception {
        String sql = "SELECT trans_pendiente as comp_pendiente, trans_anulado as comp_anulado, trans_pendiente as est_bloqueado, trans_revisado as est_generado, false as con_reversado FROM inventario.inv_transferencias WHERE "
                + "trans_empresa = '" + empresa + "' AND trans_periodo = '" + periodo + "' " + "AND trans_motivo = '"
                + motivoTipo + "' AND trans_numero = '" + numero + "'";
        return genericSQLDao.obtenerPorSql(sql, InvEstadoCCCVT.class).isEmpty() ? null
                : genericSQLDao.obtenerPorSql(sql, InvEstadoCCCVT.class).get(0);
    }
    
    @Override
    public void actualizarPendienteSql(InvTransferenciasPK invTransferenciasPK, boolean pendiente) throws Exception {
        String sql = "UPDATE inventario.inv_transferencias SET trans_pendiente=" + pendiente + " WHERE trans_empresa='"
                + invTransferenciasPK.getTransEmpresa() + "' and  trans_periodo='"
                + invTransferenciasPK.getTransPeriodo() + "' and trans_motivo='"
                + invTransferenciasPK.getTransMotivo() + "' and trans_numero='"
                + invTransferenciasPK.getTransNumero() + "';";
        genericSQLDao.ejecutarSQL(sql);
    }
    
    @Override
    public void anularRestaurarSql(InvTransferenciasPK invTransferenciasPK, boolean anulado) {
        String sql = "UPDATE inventario.inv_transferencias SET trans_anulado=" + anulado + " WHERE trans_empresa='" + invTransferenciasPK.getTransEmpresa() + "' and  trans_periodo='" + invTransferenciasPK.getTransPeriodo() + "' and \n"
                + "        trans_motivo='" + invTransferenciasPK.getTransMotivo() + "' and trans_numero='" + invTransferenciasPK.getTransNumero() + "';";
        genericSQLDao.ejecutarSQL(sql);
    }
    
}
