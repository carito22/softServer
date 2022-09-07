package ec.com.todocompu.ShrimpSoftServer.pedidos.service;

import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.PedidosConfiguracionService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.PedidosMotivoService;
import ec.com.todocompu.ShrimpSoftServer.pedidos.dao.InvPedidosDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.SectorService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoPedidoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.SistemaWebServicio;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosConfiguracionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosPK;
import ec.com.todocompu.ShrimpSoftUtils.pedidos.TO.InvPedidoTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSectorPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSucesoPedido;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InvPedidosServiceImpl implements InvPedidosService {

    @Autowired
    private InvPedidosDao invPedidoDaoOld;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericoDao<InvPedidos, InvPedidosPK> invPedidoDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private PedidosMotivoService pedidosMotivoService;
    @Autowired
    private SistemaWebServicio sistemaWebServicio;
    @Autowired
    private PedidosConfiguracionService pedidosConfiguracionService;
    @Autowired
    private SectorService sectorService;
    @Autowired
    private SucesoPedidoDao sucesoPedidoDao;

    @Override
    public InvPedidos insertarInvPedidos(InvPedidos invPedidos, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        if (invPedidos.getInvPedidosPK().getPedNumero() == null || invPedidos.getInvPedidosPK().getPedNumero().trim().isEmpty() || "0".equals(invPedidos.getInvPedidosPK().getPedNumero())) {
            invPedidos.getInvPedidosPK().setPedNumero(invPedidoDaoOld.getProximaNumeracionInvPedidos(invPedidos.getInvPedidosPK().getPedEmpresa(), invPedidos));
        }
        if (!invPedidoDao.existe(InvPedidos.class, invPedidos.getInvPedidosPK())) {
            //PREAPARANDO SISUSCESO
            susClave = invPedidos.getInvPedidosPK().getPedNumero();
            susDetalle = "Se insertó la orden de pedido con código " + susClave;
            susSuceso = "INSERT";
            susTabla = "inventario.inv_pedidos";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            //FECHA Y CONVERSION DE TO
            int plazo = invPedidos.getInvPedidosMotivo() != null ? invPedidos.getInvPedidosMotivo().getPmPlazoPredeterminado() : 0;
            if (invPedidos.getPedFechaEmision() == null) {
                invPedidos.setPedFechaEmision(new Date());
            }
            invPedidos.setPedFechaVencimiento(new Date(invPedidos.getPedFechaEmision().getTime() + new Long(plazo)));
            invPedidos.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
            //INSERCION Y RESPUESTA
            List<InvPedidosDetalle> ipd = invPedidos.getInvPedidosDetalleList();
            for (InvPedidosDetalle ipd1 : ipd) {
                ipd1.setInvPedidos(new InvPedidos(invPedidos.getInvPedidosPK()));
            }
            invPedidos.setInvPedidosDetalleList(ipd);
            invPedidos = invPedidoDao.insertar(invPedidos);
            sucesoDao.insertar(sisSuceso);
            ////////////CREAR SUCESO////////////////////
            SisSucesoPedido sucesoPedido = new SisSucesoPedido();
            InvPedidos copia = ConversionesInventario.convertirInvPedidos_InvPedidos(invPedidos);
            if (copia.getInvCliente() != null && copia.getInvCliente().getInvClientePK().getCliCodigo() == null) {
                copia.setInvCliente(null);
            }
            String json = UtilsJSON.objetoToJson(copia);
            sucesoPedido.setSusJson(json);
            sucesoPedido.setSisSuceso(sisSuceso);
            sucesoPedido.setInvPedidos(copia);
            sucesoPedidoDao.insertar(sucesoPedido);

            return invPedidos;
        } else {
            return null;
        }
    }

    @Override
    public InvPedidos modificarInvPedidos(InvPedidos invPedidos, String accion, SisInfoTO sisInfoTO) throws GeneralException {
        //validar que nadie realice la misma accion, p.e que intenten aprobar a la misma vez.
        if (accion != null) {
            verificarAccion(accion, invPedidos.getInvPedidosPK());
        }
        if (accion != null && accion.equals("DESAPROBAR")) {
            resetearCantidadesAprobadas(invPedidos);
        }
        //PREAPARANDO SISUSCESO
        susClave = invPedidos.getInvPedidosPK().getPedNumero();
        susDetalle = "Se modificó la orden de pedido con código " + susClave;
        susSuceso = "UPDATE";
        susTabla = "inventario.inv_pedidos";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        //FECHA Y CONVERSION DE TO
        //INSERCION Y RESPUESTA
        List<InvPedidosDetalle> ipd = invPedidos.getInvPedidosDetalleList();
        for (InvPedidosDetalle ipd1 : ipd) {
            if (ipd1.getDetSecuencial() == 0) {
                ipd1.setDetSecuencial(null);
            }
            ipd1.setInvPedidos(new InvPedidos(invPedidos.getInvPedidosPK()));
        }
        invPedidos.setInvPedidosDetalleList(ipd);
        invPedidos.setUsrFechaAprobada(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
        invPedidos = invPedidoDao.actualizar(invPedidos);
        sucesoDao.insertar(sisSuceso);
        ////////////CREAR SUCESO////////////////////
        SisSucesoPedido sucesoPedido = new SisSucesoPedido();
        InvPedidos copia = ConversionesInventario.convertirInvPedidos_InvPedidos(invPedidos);
        if (copia.getInvCliente() != null && copia.getInvCliente().getInvClientePK().getCliCodigo() == null) {
            copia.setInvCliente(null);
        }
        String json = UtilsJSON.objetoToJson(copia);
        sucesoPedido.setSusJson(json);
        sucesoPedido.setSisSuceso(sisSuceso);
        sucesoPedido.setInvPedidos(copia);
        sucesoPedidoDao.insertar(sucesoPedido);
        return invPedidos;
    }

    @Override
    public InvPedidos desaprobarInvPedidos(InvPedidos invPedidos, SisInfoTO sisInfoTO) throws GeneralException {
        String sql = "";

        if (invPedidos.getInvPedidosDetalleList() != null) {
            for (InvPedidosDetalle detalle : invPedidos.getInvPedidosDetalleList()) {
                sql = " SELECT * from inventario.fun_verificar_si_desaprobar_pedido( '" + sisInfoTO.getEmpresa() + "', " + detalle.getDetSecuencial() + ");";
                boolean resp = (boolean) genericSQLDao.obtenerObjetoPorSql(sql);
                if (!resp) {
                    throw new GeneralException("Las ordenes de compras generadas por esta orden de pedido deben estar PENDIENTES.", "DESAPROBAR");
                }
            }
            resetearCantidadesAprobadas(invPedidos);
            invPedidos.setPedPendiente(true);
            //PREAPARANDO SISUSCESO
            susClave = invPedidos.getInvPedidosPK().getPedNumero();
            susDetalle = "Se desaprobó la orden de pedido con código " + susClave;
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_pedidos";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            //FECHA Y CONVERSION DE TO
            //INSERCION Y RESPUESTA
            List<InvPedidosDetalle> ipd = invPedidos.getInvPedidosDetalleList();
            for (InvPedidosDetalle ipd1 : ipd) {
                if (ipd1.getDetSecuencial() == 0) {
                    ipd1.setDetSecuencial(null);
                }
                ipd1.setInvPedidos(new InvPedidos(invPedidos.getInvPedidosPK()));
            }
            invPedidos.setInvPedidosDetalleList(ipd);
            invPedidos = invPedidoDao.actualizar(invPedidos);
            sucesoDao.insertar(sisSuceso);
        }

        return invPedidos;
    }

    @Override
    public String desmayorizarInvPedidos(InvPedidosPK invPedidosPK, SisInfoTO sisInfoTO) throws GeneralException {
        InvPedidos invPedido = invPedidoDao.obtener(InvPedidos.class, invPedidosPK);
        String respuesta = "F";
        if (!invPedido.getPedAnulado() && !invPedido.getPedPendiente() && !invPedido.getPedAprobado() && !invPedido.getPedEjecutado()) {
            //PREAPARANDO SISUSCESO
            susClave = invPedidosPK.getPedNumero();
            susDetalle = "Se desmayorizó la orden de pedido con código " + susClave;
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_pedidos";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            invPedido.setPedPendiente(true);
            invPedidoDao.actualizar(invPedido);
            sucesoDao.insertar(sisSuceso);
            respuesta = "TLa orden de pedido N. " + invPedidosPK.getPedNumero() + " se ha desmayorizado correctamente.";
        } else {
            if (invPedido.getPedPendiente()) {
                respuesta = "FLa orden de pedido N. " + invPedidosPK.getPedNumero() + " está PENDIENTE.";
            } else {
                if (invPedido.getPedAnulado()) {
                    respuesta = "FLa orden de pedido N. " + invPedidosPK.getPedNumero() + " está ANULADO.";
                } else {
                    respuesta = "FLa orden de pedido N. " + invPedidosPK.getPedNumero() + " está APROBADO O EJECUTADO.";
                }
            }
        }
        return respuesta;
    }

    @Override
    public String anularInvPedidos(InvPedidosPK invPedidosPK, String tipo, String motivoAnulacion, SisInfoTO sisInfoTO) throws GeneralException {
        InvPedidos invPedido = invPedidoDao.obtener(InvPedidos.class, invPedidosPK);
        String respuesta = "F";
        boolean esValido = false;
        if (tipo.equals("GENERAROP")) {
            esValido = !invPedido.getPedAnulado() && !invPedido.getPedPendiente() && !invPedido.getPedAprobado() && !invPedido.getPedEjecutado();
        } else {
            if (tipo.equals("APROBAROP")) {
                invPedido.setPedAprobado(false);
                resetearCantidadesAprobadas(invPedido);
                esValido = !invPedido.getPedAnulado() && !invPedido.getPedPendiente() && !invPedido.getPedEjecutado();
            }
        }

        if (esValido) {
            //PREAPARANDO SISUSCESO
            susClave = invPedidosPK.getPedNumero();
            susDetalle = "Se anuló la orden de pedido con código " + susClave;
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_pedidos";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            invPedido.setPedAnulado(true);
            invPedido.setPedMotivoAnulacion(motivoAnulacion);
            invPedidoDao.actualizar(invPedido);
            sucesoDao.insertar(sisSuceso);
            respuesta = "TLa orden de pedido N. " + invPedidosPK.getPedNumero() + " se ha anulado correctamente.";
        } else {
            if (invPedido.getPedPendiente()) {
                respuesta = "FLa orden de pedido N. " + invPedidosPK.getPedNumero() + " está PENDIENTE.";
            } else {
                if (invPedido.getPedAnulado()) {
                    respuesta = "FLa orden de pedido N. " + invPedidosPK.getPedNumero() + " está ANULADO.";
                } else {
                    respuesta = "FLa orden de pedido N. " + invPedidosPK.getPedNumero() + " está APROBADO O EJECUTADO.";
                }
            }
        }
        return respuesta;
    }

    @Override
    public String restaturarInvPedidos(InvPedidosPK invPedidosPK, SisInfoTO sisInfoTO) throws GeneralException {
        InvPedidos invPedido = invPedidoDao.obtener(InvPedidos.class, invPedidosPK);
        String respuesta = "F";
        if (invPedido.getPedAnulado() && !invPedido.getPedPendiente() && !invPedido.getPedAprobado() && !invPedido.getPedEjecutado()) {
            //PREAPARANDO SISUSCESO
            susClave = invPedidosPK.getPedNumero();
            susDetalle = "Se restauró la orden de pedido con código " + susClave;
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_pedidos";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            invPedido.setPedAnulado(false);
            invPedidoDao.actualizar(invPedido);
            sucesoDao.insertar(sisSuceso);
            respuesta = "TLa orden de pedido N. " + invPedidosPK.getPedNumero() + " se ha restaurado correctamente.";
        } else {
            if (invPedido.getPedPendiente()) {
                respuesta = "FLa orden de pedido N. " + invPedidosPK.getPedNumero() + " está PENDIENTE.";
            } else {
                if (invPedido.getPedAnulado()) {
                    respuesta = "FLa orden de pedido N. " + invPedidosPK.getPedNumero() + "no está ANULADO.";
                } else {
                    respuesta = "FLa orden de pedido N. " + invPedidosPK.getPedNumero() + " está APROBADO O EJECUTADO.";
                }
            }
        }
        return respuesta;
    }

    @Override
    public List<InvPedidoTO> getInvPedidos(String empresa, String busqueda, String motivo, Integer nroRegistros, String tipo, String fechaInicio, String fechaFin) throws GeneralException {
        String porcionSql = "";
        String porcionSqlLimit = "";
        String porcionSqlFiltrarEstado = "";

        if (nroRegistros != null) {
            porcionSqlLimit = "LIMIT " + nroRegistros + " ";
        }
        if (motivo != null) {
            porcionSql = " AND m.pm_codigo = '" + motivo + "'";
        }
        if (busqueda != null && !busqueda.equals("")) {
            porcionSql = porcionSql + " AND p.ped_sector = '" + busqueda + "' ";
        }
        if (fechaInicio != null && fechaFin != null) {
            porcionSql = porcionSql + " AND ped_fecha_emision >= '$$ || " + fechaInicio + "|| $$' ";
            porcionSql = porcionSql + " AND ped_fecha_emision <= '$$ || " + fechaFin + "|| $$' ";
        }
        if (tipo != null && tipo.equals("GENERAROP")) {
            porcionSqlFiltrarEstado = "'  AND p.ped_ejecutado is false AND p.ped_aprobado is false ";
        } else if (tipo != null && tipo.equals("GENERAROC")) {
            porcionSqlFiltrarEstado = "'  AND p.ped_ejecutado is false AND p.ped_aprobado is true ";
        } else {
            porcionSqlFiltrarEstado = "'  AND p.ped_ejecutado is false AND ((p.ped_aprobado is true)  OR (p.ped_aprobado is false AND p.ped_pendiente is false)) ";
        }

        String sql = " SELECT ROW_NUMBER() OVER(ORDER BY p.ped_fecha_emision DESC,p.ped_numero DESC) AS index, p.ped_numero as pedidonumero, p.ped_empresa as codigoempresa, p.ped_motivo as codigomotivo, m.pm_detalle as detallemotivo, p.ped_sector as codigosector, s.sec_nombre as nombresector,"
                + " CASE "
                + " WHEN (p.ped_pendiente is true AND p.ped_aprobado is false AND p.ped_anulado is false AND p.ped_ejecutado is false) THEN 'PENDIENTE'"
                + " WHEN (p.ped_pendiente is false AND p.ped_aprobado is false AND p.ped_anulado is false AND p.ped_ejecutado is false) THEN 'POR APROBAR PEDIDO'"
                + " WHEN (p.ped_pendiente is false AND p.ped_aprobado is true AND p.ped_anulado is false AND p.ped_ejecutado is false) THEN 'PEDIDO APROBADO'"
                + " WHEN (p.ped_pendiente is false AND p.ped_aprobado is false AND p.ped_anulado is true AND p.ped_ejecutado is false) THEN 'ANULADO'"
                + " WHEN (p.ped_pendiente is false AND p.ped_aprobado is true AND p.ped_anulado is false AND p.ped_ejecutado is true) THEN 'EJECUTADO'"
                + " END AS estado,"
                + " p.ped_fecha_emision as pedfechaemision, p.ped_pendiente as pedpendiente, p.ped_aprobado as pedaprobado, p.ped_ejecutado as pedejecutado,"
                + " p.ped_anulado as pedanulado, p.ped_monto_total as pedmontototal, p.usr_fecha_inserta as usrfechainserta, p.ped_observaciones_registra as obsregistra,"
                + " p.ped_observaciones_aprueba as obsaprueba, p.ped_observaciones_ejecuta as obsejecuta,"
                + " CASE WHEN (p.ped_aprobado) THEN a.usr_nombre || ' ' || a.usr_apellido ELSE '' END as aprobador,"
                + " CASE WHEN (p.ped_ejecutado) THEN e.usr_nombre || ' ' || e.usr_apellido ELSE '' END as ejecutor,"
                + " r.usr_nombre || ' ' || r.usr_apellido as registrador, p.usr_fecha_aprobada as usrfechaaprobada"
                + " FROM inventario.inv_pedidos p"
                + " INNER JOIN inventario.inv_pedidos_motivo m ON p.ped_motivo = m.pm_codigo AND p.ped_empresa = m.pm_empresa AND p.ped_sector = m.pm_sector "
                + " INNER JOIN produccion.prd_sector s ON p.ped_sector = s.sec_codigo AND p.ped_empresa = s.sec_empresa "
                + " INNER JOIN sistemaweb.sis_usuario a ON p.usr_aprueba = a.usr_codigo "
                + " INNER JOIN sistemaweb.sis_usuario e ON p.usr_ejecuta = e.usr_codigo "
                + " INNER JOIN sistemaweb.sis_usuario r ON p.usr_registra = r.usr_codigo "
                + " WHERE ped_empresa = '" + empresa + porcionSqlFiltrarEstado + porcionSql + porcionSqlLimit + ";";
        List<InvPedidoTO> i = genericSQLDao.obtenerPorSql(sql, InvPedidoTO.class);
        return i;
    }

    @Override
    public InvPedidoTO obtenerInvPedidosTO(InvPedidosPK invPedidosPK) throws GeneralException, Exception {
        String porcionSql = "";
        String sql = "SELECT ROW_NUMBER() OVER(ORDER BY p.ped_fecha_emision DESC,p.ped_numero DESC) AS index, p.ped_numero as pedidonumero, p.ped_empresa as codigoempresa, p.ped_motivo as codigomotivo, m.pm_detalle as detallemotivo, p.ped_sector as codigosector, s.sec_nombre as nombresector,"
                + " CASE "
                + " WHEN (p.ped_pendiente is true AND p.ped_aprobado is false AND p.ped_anulado is false AND p.ped_ejecutado is false) THEN 'PENDIENTE'"
                + " WHEN (p.ped_pendiente is false AND p.ped_aprobado is true AND p.ped_anulado is false AND p.ped_ejecutado is false) THEN 'PEDIDO APROBADO'"
                + " WHEN (p.ped_pendiente is false AND p.ped_aprobado is false AND p.ped_anulado is true AND p.ped_ejecutado is false) THEN 'ANULADO'"
                + " WHEN (p.ped_pendiente is false AND p.ped_aprobado is true AND p.ped_anulado is false AND p.ped_ejecutado is true) THEN 'EJECUTADO'"
                + " END AS estado,"
                + " p.ped_fecha_emision as pedfechaemision, p.ped_pendiente as pedpendiente, p.ped_aprobado as pedaprobado, p.ped_ejecutado as pedejecutado,"
                + " p.ped_anulado as pedanulado, p.ped_monto_total as pedmontototal, p.usr_fecha_inserta as usrfechainserta, p.ped_observaciones_registra as obsregistra,"
                + " p.ped_observaciones_aprueba as obsaprueba, p.ped_observaciones_ejecuta as obsejecuta,"
                + " CASE WHEN (p.ped_aprobado) THEN a.usr_nombre || ' ' || a.usr_apellido ELSE '' END as aprobador,"
                + " CASE WHEN (p.ped_ejecutado) THEN e.usr_nombre || ' ' || e.usr_apellido ELSE '' END as ejecutor,"
                + " r.usr_nombre || ' ' || r.usr_apellido as registrador, p.usr_fecha_aprobada as usrfechaaprobada "
                + " FROM inventario.inv_pedidos p"
                + " INNER JOIN inventario.inv_pedidos_motivo m ON p.ped_motivo = m.pm_codigo AND p.ped_empresa = m.pm_empresa "
                + " INNER JOIN produccion.prd_sector s ON p.ped_sector = s.sec_codigo AND p.ped_empresa = s.sec_empresa "
                + " INNER JOIN sistemaweb.sis_usuario a ON p.usr_aprueba = a.usr_codigo "
                + " INNER JOIN sistemaweb.sis_usuario e ON p.usr_ejecuta = e.usr_codigo "
                + " INNER JOIN sistemaweb.sis_usuario r ON p.usr_registra = r.usr_codigo "
                + " WHERE ped_empresa = '" + invPedidosPK.getPedEmpresa() + "'"
                + " AND ped_sector = '" + invPedidosPK.getPedSector() + "'"
                + " AND ped_motivo = '" + invPedidosPK.getPedMotivo() + "'"
                + " AND ped_numero = '" + invPedidosPK.getPedNumero() + "'"
                + porcionSql + ";";
        List<InvPedidoTO> i = genericSQLDao.obtenerPorSql(sql, InvPedidoTO.class);
        return i.size() > 0 ? i.get(0) : null;
    }

    @Override
    public boolean eliminarInvPedidos(InvPedidosPK pk, SisInfoTO sisInfoTO) throws GeneralException {
        InvPedidos invPedidos = invPedidoDao.obtener(InvPedidos.class, pk);
        if (invPedidos != null) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = invPedidos.getInvPedidosPK().getPedNumero();
            susDetalle = "Se eliminó el pedido con código " + susClave;
            susSuceso = "DELETE";
            susTabla = "inventario.inv_pedidos";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            //ELIMINAR
            invPedidoDao.eliminar(invPedidos);
            sucesoDao.insertar(sisSuceso);
            return true;
        } else {
            throw new GeneralException("La orden de pedido que va a eliminar ya no existe. Intente con otro.", "La orden de pedido no existe");
        }
    }

    @Override
    public InvPedidos obtenerInvPedidos(InvPedidosPK pk) throws GeneralException {
        InvPedidos ip = invPedidoDao.obtener(InvPedidos.class, pk);
        if (ip != null) {
            if (ip.getInvPedidosMotivo() != null) {
                if (ip.getInvPedidosMotivo().getPrdSector() != null) {
                    ip.getInvPedidosMotivo().getPrdSector().setPrdPiscinaList(null);
                    ip.getInvPedidosMotivo().getPrdSector().setPrdSobrevivenciaList(null);
                }
            }
            if (ip.getInvPedidosDetalleList() != null) {
                ip.getInvPedidosDetalleList().forEach((detalle) -> {
                    detalle.setInvPedidos(new InvPedidos(ip.getInvPedidosPK()));
                    if (detalle.getInvProducto() != null) {
                        if (detalle.getInvProducto().getInvProductoCategoria() != null) {
                            detalle.getInvProducto().getInvProductoCategoria().setInvProductoList(null);
                        }
                    }
                });
                List<InvPedidosDetalle> slist = ip.getInvPedidosDetalleList().stream().sorted(Comparator.comparing(InvPedidosDetalle::getDetOrden)).collect(Collectors.toList());
                ip.setInvPedidosDetalleList(slist);
            }
        }
        return ip;
    }

    private void resetearCantidadesAprobadas(InvPedidos ip) {
        if (ip.getInvPedidosDetalleList() != null) {
            ip.getInvPedidosDetalleList().forEach((detalle) -> {
                detalle.setDetCantidadAprobada(BigDecimal.ZERO);
            });
        }
    }

    private void verificarAccion(String accion, InvPedidosPK pk) {
        InvPedidos p = invPedidoDao.obtenerLocal(InvPedidos.class, pk);
        switch (accion) {
            case "APROBAR":
                if (p.getPedAprobado()) {
                    throw new GeneralException("La orden de pedido ya ha sido aprobado previamente por " + p.getUsrAprueba() + ".", accion);
                }
                break;
            case "DESAPROBAR":
                if (!p.getPedAprobado()) {
                    throw new GeneralException("La orden de pedido no ha sido aprobado previamente por " + p.getUsrAprueba() + ".", accion);
                }
                break;
            case "EJECUTAR":
                if (p.getPedEjecutado()) {
                    throw new GeneralException("La orden de pedido ya ha sido ejecutado previamente por " + p.getUsrEjecuta() + ".", accion);
                }
                break;
            case "EDITAR":
                if (p.getPedAprobado() || p.getPedEjecutado()) {
                    throw new GeneralException("No se modificó la orden de pedido, ya ha sido aprobado o ejecutado.", accion);
                }
                break;
        }
    }

    @Override
    public Map<String, Object> obtenerDatosParaGenerarOP(InvPedidosMotivoPK invPedidosMotivoPK, SisInfoTO sisInfoTO) throws Exception {
        Map<String, Object> map = new HashMap<>();
        InvPedidosMotivo invPedidosMotivo = pedidosMotivoService.getInvPedidosMotivo(invPedidosMotivoPK);
        InvPedidosConfiguracionTO invPedidosConfiguracionTO = pedidosConfiguracionService.getListaInvPedidosConfiguracionTO(invPedidosMotivoPK, sisInfoTO);
        Date fechaInicio = sistemaWebServicio.getFechaInicioMes();
        Date fechaActual = sistemaWebServicio.getFechaActual();
        map.put("fechaInicio", fechaInicio);
        map.put("fechaActual", fechaActual);
        map.put("invPedidosMotivo", invPedidosMotivo);
        map.put("invPedidosConfiguracionTO", invPedidosConfiguracionTO);
        return map;
    }

    @Override
    public Map<String, Object> obtenerDatosParaPantallaOP(String empresa, SisInfoTO sisInfoTO) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<PrdListaSectorTO> listaSectores = sectorService.getListaSectorTO(empresa, false);
        List<InvPedidosMotivo> listaMotivos;
        if (listaSectores != null && !listaSectores.isEmpty()) {
            PrdListaSectorTO sectorSeleccionado = listaSectores.get(0);
            PrdSectorPK pk = new PrdSectorPK(empresa, sectorSeleccionado.getSecCodigo());
            listaMotivos = pedidosMotivoService.getListaInvPedidosMotivo(empresa, false, pk);
            map.put("sectorSeleccionado", sectorSeleccionado);
        } else {
            listaMotivos = pedidosMotivoService.getListaInvPedidosMotivo(empresa, false, null);
        }
        if (listaMotivos != null && !listaMotivos.isEmpty()) {
            InvPedidosMotivo motivoSeleccionado = listaMotivos.get(0);
            InvPedidosConfiguracionTO configuracion = pedidosConfiguracionService.getListaInvPedidosConfiguracionTO(motivoSeleccionado.getInvPedidosMotivoPK(), sisInfoTO);
            map.put("configuracion", configuracion);
            map.put("motivoSeleccionado", motivoSeleccionado);
        }
        map.put("listaSectores", listaSectores);
        map.put("listaMotivos", listaMotivos);
        return map;
    }

    @Override
    public List<InvPedidosDetalle> listarDetallesParaOrdenDecompra(InvPedidosPK invPedidosPK) throws Exception {
        String sql = "SELECT * FROM inventario.inv_pedidos_detalle where ped_empresa = '" + invPedidosPK.getPedEmpresa()
                + "' and ped_sector = '" + invPedidosPK.getPedSector() + "' and ped_numero = '" + invPedidosPK.getPedNumero() + "' "
                + " and ped_motivo = '" + invPedidosPK.getPedMotivo() + "' and det_cantidad_aprobada <> 0 and det_completado = false "
                + " and det_cantidad_aprobada >= det_cantidad_adquirida order by det_orden asc;";
        List<InvPedidosDetalle> ipd = genericSQLDao.obtenerPorSql(sql, InvPedidosDetalle.class);
        ipd.forEach((detalle) -> {
            InvPedidos pedido = new InvPedidos(invPedidosPK);
            pedido.setPedOrdenCompra(detalle.getInvPedidos() != null ? detalle.getInvPedidos().getPedOrdenCompra() : "");
            detalle.setInvPedidos(pedido);
        });
        return ipd;
    }

    @Override
    public List<InvPedidoTO> getListaInvPedidosOrdenTO(String empresa, String motivo, String busqueda, String fechaInicio, String fechaFin, Boolean soloRegistrados, Boolean soloAprobados, Boolean soloEjecutados, String usuario) throws GeneralException, Exception {
        String porcionSql = "";
        if (fechaInicio != null && fechaFin != null) {
            porcionSql = porcionSql + " AND ped_fecha_emision >= '$$ || " + fechaInicio + "|| $$' ";
            porcionSql = porcionSql + " AND ped_fecha_emision <= '$$ || " + fechaFin + "|| $$' ";
        }
        if (motivo != null) {
            porcionSql = " AND m.pm_codigo = '" + motivo + "'";
        }
        if (busqueda != null && !busqueda.equals("")) {
            porcionSql = porcionSql + " AND p.ped_sector = '" + busqueda + "' ";
        }
        if (soloRegistrados) {
            porcionSql = porcionSql + " AND ped_aprobado = false AND ped_ejecutado = false";
        }
        if (soloAprobados) {
            porcionSql = porcionSql + " AND ped_aprobado = true AND ped_ejecutado = false";
        }
        if (soloEjecutados) {
            porcionSql = porcionSql + " AND ped_ejecutado = true";
        }
        String sql = " SELECT ROW_NUMBER() OVER(ORDER BY p.ped_fecha_emision DESC,p.ped_numero DESC) AS index, p.ped_numero as pedidonumero, p.ped_empresa as codigoempresa, p.ped_motivo as codigomotivo, m.pm_detalle as detallemotivo, p.ped_sector as codigosector, s.sec_nombre as nombresector,"
                + " CASE "
                + " WHEN (p.ped_pendiente is true AND p.ped_aprobado is false AND p.ped_anulado is false AND p.ped_ejecutado is false) THEN 'PENDIENTE'"
                + " WHEN (p.ped_pendiente is false AND p.ped_aprobado is false AND p.ped_anulado is false AND p.ped_ejecutado is false) THEN 'POR APROBAR PEDIDO'"
                + " WHEN (p.ped_pendiente is false AND p.ped_aprobado is true AND p.ped_anulado is false AND p.ped_ejecutado is false) THEN 'PEDIDO APROBADO'"
                + " WHEN (p.ped_pendiente is false AND p.ped_aprobado is false AND p.ped_anulado is true AND p.ped_ejecutado is false) THEN 'ANULADO'"
                + " WHEN (p.ped_pendiente is false AND p.ped_aprobado is true AND p.ped_anulado is false AND p.ped_ejecutado is true) THEN 'EJECUTADO'"
                + " END AS estado,"
                + " p.ped_fecha_emision as pedfechaemision, p.ped_pendiente as pedpendiente, p.ped_aprobado as pedaprobado, p.ped_ejecutado as pedejecutado,"
                + " p.ped_anulado as pedanulado, p.ped_monto_total as pedmontototal, p.usr_fecha_inserta as usrfechainserta, p.ped_observaciones_registra as obsregistra,"
                + " p.ped_observaciones_aprueba as obsaprueba, p.ped_observaciones_ejecuta as obsejecuta,"
                + " CASE WHEN (p.ped_aprobado) THEN a.usr_nombre || ' ' || a.usr_apellido ELSE '' END as aprobador,"
                + " CASE WHEN (p.ped_ejecutado) THEN e.usr_nombre || ' ' || e.usr_apellido ELSE '' END as ejecutor,"
                + " r.usr_nombre || ' ' || r.usr_apellido as registrador, p.usr_fecha_aprobada as usrfechaaprobada "
                + " FROM inventario.inv_pedidos p"
                + " INNER JOIN inventario.inv_pedidos_motivo m ON p.ped_motivo = m.pm_codigo AND p.ped_empresa = m.pm_empresa AND p.ped_sector = m.pm_sector "
                + " INNER JOIN produccion.prd_sector s ON p.ped_sector = s.sec_codigo AND p.ped_empresa = s.sec_empresa "
                + " INNER JOIN sistemaweb.sis_usuario a ON p.usr_aprueba = a.usr_codigo "
                + " INNER JOIN sistemaweb.sis_usuario e ON p.usr_ejecuta = e.usr_codigo "
                + " INNER JOIN sistemaweb.sis_usuario r ON p.usr_registra = r.usr_codigo "
                + " WHERE ped_empresa = '" + empresa + "' " + porcionSql + ";";
        List<InvPedidoTO> i = genericSQLDao.obtenerPorSql(sql, InvPedidoTO.class);
        return i;
    }
}
