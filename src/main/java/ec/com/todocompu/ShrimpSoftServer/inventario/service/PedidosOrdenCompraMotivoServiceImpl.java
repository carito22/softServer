/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.criteria.Criterio;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericSQLDao;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvOrdenCompraMotivoDetalleAprobadoresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosOrdenCompraMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Developer4
 */
@Service
public class PedidosOrdenCompraMotivoServiceImpl implements PedidosOrdenCompraMotivoService {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericoDao<InvPedidosOrdenCompraMotivo, InvPedidosOrdenCompraMotivoPK> pedidosOrdenCompraMotivoDao;
    @Autowired
    private GenericSQLDao genericSQLDao;
    @Autowired
    private OrdenCompraConfiguracionService compraConfiguracionService;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public InvPedidosOrdenCompraMotivo insertarInvPedidosOrdenCompraMotivo(InvPedidosOrdenCompraMotivo invPedidosOrdenCompraMotivo, List<InvOrdenCompraMotivoDetalleAprobadoresTO> invOrdenCompraMotivoDetalleAprobadores, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        if (pedidosOrdenCompraMotivoDao.obtener(InvPedidosOrdenCompraMotivo.class, invPedidosOrdenCompraMotivo.getInvPedidosOrdenCompraMotivoPK()) != null) {
            throw new GeneralException("El código ingresado ya está siendo utilizado.", "Código duplicado");
        }
        if (obtenerInvPedidosOrdenCompraMotivoDetalle(invPedidosOrdenCompraMotivo) != null) {
            throw new GeneralException("El detalle ingresado ya está siendo utilizado.", "Detalle duplicado");
        }
        susClave = invPedidosOrdenCompraMotivo.getInvPedidosOrdenCompraMotivoPK().getOcmCodigo();
        susDetalle = "Se insertó el pedido motivo de orden de compra: " + invPedidosOrdenCompraMotivo.getOcmDetalle() + " con código " + susClave;
        susSuceso = "INSERT";
        susTabla = "inventario.inv_pedidos_orden_compra_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        //AÑADE FECHA
        invPedidosOrdenCompraMotivo.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
        if (invPedidosOrdenCompraMotivo.getOcmAprobacionAutomatica()) {
            invPedidosOrdenCompraMotivo.setOcmNotificarAprobador(false);
        }
        invPedidosOrdenCompraMotivo = pedidosOrdenCompraMotivoDao.insertar(invPedidosOrdenCompraMotivo);
        sucesoDao.insertar(sisSuceso);
        if (!invPedidosOrdenCompraMotivo.getOcmAprobacionAutomatica() && invOrdenCompraMotivoDetalleAprobadores != null) {
            insertarConfiguracion(invPedidosOrdenCompraMotivo, invOrdenCompraMotivoDetalleAprobadores, sisInfoTO);
        }
        return invPedidosOrdenCompraMotivo;
    }

    @Override
    public InvPedidosOrdenCompraMotivo modificarInvPedidosOrdenCompraMotivo(InvPedidosOrdenCompraMotivo invPedidosOrdenCompraMotivo, List<InvOrdenCompraMotivoDetalleAprobadoresTO> invOrdenCompraMotivoDetalleAprobadores, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        if (pedidosOrdenCompraMotivoDao.existe(InvPedidosOrdenCompraMotivo.class, invPedidosOrdenCompraMotivo.getInvPedidosOrdenCompraMotivoPK())) {
            InvPedidosOrdenCompraMotivo invPedidosMot = obtenerInvPedidosOrdenCompraMotivoDetalle(invPedidosOrdenCompraMotivo);
            if (invPedidosMot != null && !invPedidosMot.getInvPedidosOrdenCompraMotivoPK().getOcmCodigo().equalsIgnoreCase(invPedidosOrdenCompraMotivo.getInvPedidosOrdenCompraMotivoPK().getOcmCodigo())) {
                throw new GeneralException("El detalle ingresado ya está siendo utilizado.", "Detalle duplicado");
            }
            susClave = invPedidosOrdenCompraMotivo.getInvPedidosOrdenCompraMotivoPK().getOcmCodigo();
            susDetalle = "Se modificó el pedido motivo de orden de compra: " + invPedidosOrdenCompraMotivo.getOcmDetalle() + " con código " + susClave;
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_pedidos_orden_compra_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            if (invPedidosOrdenCompraMotivo.getOcmAprobacionAutomatica()) {
                invPedidosOrdenCompraMotivo.setOcmNotificarAprobador(false);
            }
            invPedidosOrdenCompraMotivo = pedidosOrdenCompraMotivoDao.actualizar(invPedidosOrdenCompraMotivo);
            sucesoDao.insertar(sisSuceso);
            if (invPedidosOrdenCompraMotivo.getOcmAprobacionAutomatica() && invOrdenCompraMotivoDetalleAprobadores != null) {
                eliminarConfiguracion(invOrdenCompraMotivoDetalleAprobadores);
            }
            if (invOrdenCompraMotivoDetalleAprobadores != null) {
                insertarConfiguracion(invPedidosOrdenCompraMotivo, invOrdenCompraMotivoDetalleAprobadores, sisInfoTO);
            }
            return invPedidosOrdenCompraMotivo;
        } else {
            throw new GeneralException("El motivo que va a modificar ya no existe. Intente con otro", "Motivo no encontrado.");
        }
    }

    private InvPedidosOrdenCompraMotivo obtenerInvPedidosOrdenCompraMotivoDetalle(InvPedidosOrdenCompraMotivo invPedidosOrdenCompraMotivo) {
        Criterio filtro;
        filtro = Criterio.forClass(InvPedidosOrdenCompraMotivo.class);
        filtro.add(Restrictions.eq("invPedidosOrdenCompraMotivoPK.ocmEmpresa", invPedidosOrdenCompraMotivo.getInvPedidosOrdenCompraMotivoPK().getOcmEmpresa()));
        filtro.add(Restrictions.eq("invPedidosOrdenCompraMotivoPK.ocmSector", invPedidosOrdenCompraMotivo.getInvPedidosOrdenCompraMotivoPK().getOcmSector()));
        filtro.add(Restrictions.eq("ocmDetalle", invPedidosOrdenCompraMotivo.getOcmDetalle()));
        return pedidosOrdenCompraMotivoDao.obtenerPorCriteriaSinProyeccionesDistinct(filtro);
    }

    @Override
    public List<InvPedidosOrdenCompraMotivoTO> getListaInvPedidosOrdenCompraMotivo(String empresa, String sector) throws Exception {
        List<InvPedidosOrdenCompraMotivoTO> motivosTO = new ArrayList<>();
        String sqlSector = "";
        if (sector != null && !"".equals(sector.trim())) {
            sqlSector = " AND ocm_sector = '" + sector + "'";
        }
        String sql = "SELECT * "
                + " FROM inventario.inv_pedidos_orden_compra_motivo "
                + " WHERE ocm_empresa = '" + empresa + "' " + sqlSector
                + " ORDER BY ocm_detalle;";
        List<InvPedidosOrdenCompraMotivo> listaMotivos = genericSQLDao.obtenerPorSql(sql, InvPedidosOrdenCompraMotivo.class);
        if (listaMotivos != null && !listaMotivos.isEmpty()) {
            listaMotivos.forEach((motivo) -> {
                InvPedidosOrdenCompraMotivoTO motivoTO = new InvPedidosOrdenCompraMotivoTO();
                motivoTO.setOcmEmpresa(motivo.getInvPedidosOrdenCompraMotivoPK().getOcmEmpresa());
                motivoTO.setOcmSector(motivo.getInvPedidosOrdenCompraMotivoPK().getOcmSector());
                motivoTO.setOcmCodigo(motivo.getInvPedidosOrdenCompraMotivoPK().getOcmCodigo());
                motivoTO.setOcmDetalle(motivo.getOcmDetalle());
                motivoTO.setOcmNotificarAprobador(motivo.isOcmNotificarAprobador());
                motivoTO.setOcmNotificarProveedor(motivo.getOcmNotificarProveedor());
                motivoTO.setOcmAprobacionAutomatica(motivo.getOcmAprobacionAutomatica());
                motivoTO.setOcmCostoFijo(motivo.getOcmCostoFijo());
                motivoTO.setOcmInactivo(motivo.isOcmInactivo());
                motivoTO.setIdNotificaciones(motivo.getIdNotificaciones() == null ? 0 : motivo.getIdNotificaciones().getId());
                motivosTO.add(motivoTO);
            });
        }
        return motivosTO;
    }

    @Override
    public InvPedidosOrdenCompraMotivo getInvPedidosOrdenCompraMotivo(InvPedidosOrdenCompraMotivoPK invPedidosMotivoPK) throws GeneralException {
        InvPedidosOrdenCompraMotivo invPedidosOCM = pedidosOrdenCompraMotivoDao.obtener(InvPedidosOrdenCompraMotivo.class, invPedidosMotivoPK);
        return invPedidosOCM;
    }

    @Override
    public boolean eliminarInvPedidosOrdenCompraMotivo(InvPedidosOrdenCompraMotivoPK pk, SisInfoTO sisInfoTO) throws Exception, GeneralException {
        InvPedidosOrdenCompraMotivo invPedidosMotivoLocal = pedidosOrdenCompraMotivoDao.obtener(InvPedidosOrdenCompraMotivo.class, pk);
        if (invPedidosMotivoLocal != null) {
            List<InvOrdenCompraMotivoDetalleAprobadoresTO> aprobadores = compraConfiguracionService.getListaInvOrdenCompraMotivoDetalleAprobadoresTO(pk, sisInfoTO);
            eliminarConfiguracion(aprobadores);
            compraConfiguracionService.insertarInvOrdenCompraConfiguracionTO(aprobadores, sisInfoTO);
            susClave = invPedidosMotivoLocal.getInvPedidosOrdenCompraMotivoPK().getOcmCodigo();
            susDetalle = "Se eliminó el motivo de orden de compra: " + invPedidosMotivoLocal.getOcmDetalle() + " con código " + susClave;
            susSuceso = "DELETE";
            susTabla = "inventario.inv_pedidos_orden_compra_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            pedidosOrdenCompraMotivoDao.eliminar(invPedidosMotivoLocal);
            sucesoDao.insertar(sisSuceso);
            return true;
        } else {
            throw new GeneralException("El motivo que desea eliminar ya no existe", "Motivo no encontrado");
        }
    }

    @Override
    public boolean modificarEstadoInvPedidosOrdenCompraMotivo(InvPedidosOrdenCompraMotivoPK pk, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        InvPedidosOrdenCompraMotivo invCompraMotivoLocal = pedidosOrdenCompraMotivoDao.obtener(InvPedidosOrdenCompraMotivo.class, pk);
        if (invCompraMotivoLocal != null) {
            susClave = invCompraMotivoLocal.getInvPedidosOrdenCompraMotivoPK().getOcmCodigo();
            susDetalle = "El motivo de compra: Detalle " + invCompraMotivoLocal.getOcmDetalle() + (estado ? ", se ha inactivado correctamente" : ", se ha activado correctamente");
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_pedidos_orden_compra_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            invCompraMotivoLocal.setOcmInactivo(estado);
            pedidosOrdenCompraMotivoDao.actualizar(invCompraMotivoLocal);
            sucesoDao.insertar(sisSuceso);
            return true;
        } else {
            throw new GeneralException("El motivo que desea modificar ya no existe", "El motivo que desea modificar ya no existe");
        }

    }

    private void insertarConfiguracion(InvPedidosOrdenCompraMotivo invPedidosOrdenCompraMotivo, List<InvOrdenCompraMotivoDetalleAprobadoresTO> invOrdenCompraMotivoDetalleAprobadores, SisInfoTO sisInfoTO) throws Exception {
        for (InvOrdenCompraMotivoDetalleAprobadoresTO invOrdenCompraMotivoDetalleAprobadore : invOrdenCompraMotivoDetalleAprobadores) {
            invOrdenCompraMotivoDetalleAprobadore.setInvPedidosOrdenCompraMotivo(invPedidosOrdenCompraMotivo);
        }
        compraConfiguracionService.insertarInvOrdenCompraConfiguracionTO(invOrdenCompraMotivoDetalleAprobadores, sisInfoTO);
    }

    private void eliminarConfiguracion(List<InvOrdenCompraMotivoDetalleAprobadoresTO> aprobadores) throws Exception {
        for (InvOrdenCompraMotivoDetalleAprobadoresTO aprobadore : aprobadores) {
            aprobadore.setActivo(false);
        }
    }

}
