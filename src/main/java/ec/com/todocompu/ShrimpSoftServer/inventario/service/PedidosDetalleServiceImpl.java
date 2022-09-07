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
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosDetalle;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProducto;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Developer4
 */
@Service
public class PedidosDetalleServiceImpl implements PedidosDetalleService {

    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericoDao<InvPedidosDetalle, Integer> pedidosDetalleDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public boolean eliminarInvPedidosDetalle(Integer secuencial, SisInfoTO sisInfoTO) throws GeneralException {
        InvPedidosDetalle invPedidosDetalle = pedidosDetalleDao.obtener(InvPedidosDetalle.class, secuencial);
        if (invPedidosDetalle != null) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = invPedidosDetalle.getDetSecuencial().toString();
            susDetalle = "Se eliminó el detalle de pedido con código " + susClave;
            susSuceso = "DELETE";
            susTabla = "inventario.inv_pedidos_detalle";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            //ELIMINAR
            pedidosDetalleDao.eliminar(invPedidosDetalle);
            sucesoDao.insertar(sisSuceso);
            return true;
        } else {
            throw new GeneralException("El detalle de pedido ya no está disponible. Intente con otro.");
        }
    }
    
    @Override
    public List<InvPedidosDetalle> getListaInvPedidosDetallePorOrdenPedido(InvPedidosPK invPedidosPK, SisInfoTO sisInfoTO) throws Exception {
        Criterio filtro;
        filtro = Criterio.forClass(InvPedidosDetalle.class);
        filtro.add(Restrictions.eq("invPedidos.invPedidosPK.pedEmpresa", invPedidosPK.getPedEmpresa()));
        filtro.add(Restrictions.eq("invPedidos.invPedidosPK.pedSector", invPedidosPK.getPedSector()));
        filtro.add(Restrictions.eq("invPedidos.invPedidosPK.pedMotivo", invPedidosPK.getPedMotivo()));
        filtro.add(Restrictions.eq("invPedidos.invPedidosPK.pedNumero", invPedidosPK.getPedNumero()));
        filtro.setProjection(Projections.projectionList()
                .add(Projections.property("detSecuencial"), "detSecuencial")
                .add(Projections.property("detOrden"), "detOrden")
                .add(Projections.property("detCantidadSolicitada"), "detCantidadSolicitada")
                .add(Projections.property("detCantidadAprobada"), "detCantidadAprobada")
                .add(Projections.property("detCantidadAdquirida"), "detCantidadAdquirida")
                .add(Projections.property("detPrecioReferencial"), "detPrecioReferencial")
                .add(Projections.property("detPrecioReal"), "detPrecioReal")
                .add(Projections.property("detObservacionesRegistra"), "detObservacionesRegistra")
                .add(Projections.property("detObservacionesAprueba"), "detObservacionesAprueba")
                .add(Projections.property("detObservacionesEjecuta"), "detObservacionesEjecuta")
                .add(Projections.property("detCompletado"), "detCompletado")
                .add(Projections.property("invPedidos"), "invPedidos")
                .add(Projections.property("invProducto"), "invProducto"));
        filtro.addOrder(Order.asc("detOrden"));
        //Se quitan las listas
        List<InvPedidosDetalle> listaInvPedidosDetalle = pedidosDetalleDao.proyeccionPorCriteria(filtro, InvPedidosDetalle.class);
        listaInvPedidosDetalle.forEach((detalle) -> {
            detalle.setInvPedidos(new InvPedidos(detalle.getInvPedidos().getInvPedidosPK()));
        });
        return listaInvPedidosDetalle;
    }

    @Override
    public boolean modificarListaInvPedidosDetalle(List<InvPedidosDetalle> listaInvPedidosDetalle, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        List<SisSuceso> listaSuceso = new ArrayList<>();
        listaInvPedidosDetalle.forEach((detalle) -> {
            susClave = detalle.getDetSecuencial() + "";
            susDetalle = "Se modificó el detalle de pedido con código " + susClave;
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_pedidos_detalle";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            detalle.setInvPedidos(new InvPedidos(detalle.getInvPedidos().getInvPedidosPK()));
            detalle.setInvProducto(new InvProducto(detalle.getInvProducto().getInvProductoPK()));
            listaSuceso.add(sisSuceso);
        });
        pedidosDetalleDao.actualizarLista(listaInvPedidosDetalle);
        sucesoDao.insertar(listaSuceso);
        return true;
    }
}
