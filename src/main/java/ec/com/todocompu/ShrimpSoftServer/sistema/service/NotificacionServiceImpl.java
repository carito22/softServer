package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftServer.criteria.Criterio;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.sistema.dao.NotificacionDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidos;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoDetalleAprobadores;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoDetalleEjecutores;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisNotificacion;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuarioDetalle;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuarioDetallePK;
import ec.com.todocompu.ShrimpSoftUtils.web.NotificacionesTO;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Restrictions;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    @Autowired
    private NotificacionDao notificacionDao;
    @Autowired
    private GenericoDao<InvPedidosMotivoDetalleAprobadores, Integer> aprobadoresDao;
    @Autowired
    private GenericoDao<InvPedidosMotivoDetalleEjecutores, Integer> ejecutoresDao;
    @Autowired
    private GenericoDao<SisUsuarioDetalle, SisUsuarioDetallePK> sisUsuarioDetalleDao;
    @Autowired
    private GenericoDao<InvPedidos, InvPedidosPK> invPedidosDao;

    @Override
    public SisNotificacion obtener() {
        return notificacionDao.obtenerPorId(SisNotificacion.class, 1);
    }

    @Override
    public List<NotificacionesTO> notificar(String codigoUsuario) throws Exception {
        List<NotificacionesTO> listado = new ArrayList<>();
        List<String> ejecutores = isEjecutor(codigoUsuario);
        List<String> aprobadores = isAprobador(codigoUsuario);
        List<String> empresas = encontrarEmpresasDeUsuario(codigoUsuario);
        if (!aprobadores.isEmpty()) {
            obtenerNotificacionesAprobador(empresas, aprobadores, listado);
        }
        if (!ejecutores.isEmpty()) {
            obtenerNotificacionesEjecutor(empresas, ejecutores, listado);
        }
        return listado;
    }

    private List<String> isEjecutor(String codigoUsuario) {
        List<String> rpta = new ArrayList<>();
        Criterio filtro;
        filtro = Criterio.forClass(InvPedidosMotivoDetalleEjecutores.class);
        filtro.add(Restrictions.eq("sisUsuario.usrCodigo", codigoUsuario));
        List<InvPedidosMotivoDetalleEjecutores> list = ejecutoresDao.buscarPorCriteriaSinProyecciones(filtro);
        for (InvPedidosMotivoDetalleEjecutores list1 : list) {
            if (list1.getInvPedidosMotivo() != null) {
                rpta.add(list1.getInvPedidosMotivo().getInvPedidosMotivoPK().getPmCodigo());
            }
        }
        return rpta;
    }

    private List<String> isAprobador(String codigoUsuario) {
        List<String> rpta = new ArrayList<>();
        Criterio filtro;
        filtro = Criterio.forClass(InvPedidosMotivoDetalleAprobadores.class);
        filtro.add(Restrictions.eq("sisUsuario.usrCodigo", codigoUsuario));
        List<InvPedidosMotivoDetalleAprobadores> list = aprobadoresDao.buscarPorCriteriaSinProyecciones(filtro);
        for (InvPedidosMotivoDetalleAprobadores list1 : list) {
            if (list1.getInvPedidosMotivo() != null) {
                rpta.add(list1.getInvPedidosMotivo().getInvPedidosMotivoPK().getPmCodigo());
            }
        }
        return rpta;
    }

    private List<String> encontrarEmpresasDeUsuario(String codigoUsuario) {
        List<String> rpta = new ArrayList<>();
        Criterio filtro;
        filtro = Criterio.forClass(SisUsuarioDetalle.class);
        filtro.add(Restrictions.eq("sisUsuarioDetallePK.detUsuario", codigoUsuario));
        List<SisUsuarioDetalle> list = sisUsuarioDetalleDao.buscarPorCriteriaSinProyecciones(filtro);
        for (SisUsuarioDetalle list1 : list) {
            rpta.add(list1.getSisUsuarioDetallePK().getDetEmpresa());
        }
        return rpta;
    }

    private void obtenerNotificacionesAprobador(List<String> empresas, List<String> aprobadores, List<NotificacionesTO> listado) {
        Criterio filtro;
        filtro = Criterio.forClass(InvPedidos.class);
        filtro.createAlias("invPedidosMotivo", "motivo");
        filtro.add(Restrictions.in("invPedidosPK.pedEmpresa", empresas));
        filtro.add(Restrictions.in("motivo.invPedidosMotivoPK.pmCodigo", aprobadores));
        filtro.add(Restrictions.eq("pedAprobado", false));
        filtro.add(Restrictions.eq("pedPendiente", false));
        filtro.add(Restrictions.eq("pedEjecutado", false));
        filtro.add(Restrictions.eq("pedAnulado", false));
        List<InvPedidos> list = invPedidosDao.buscarPorCriteriaSinProyecciones(filtro);
        if (!list.isEmpty()) {
            NotificacionesTO not = new NotificacionesTO();
            not.setCantidad(list.size());
            not.setUrl("/modulos/pedidos/aprobarOrdenPedido");
            not.setNotificacion("Tiene " + list.size() + " órdenes de pedidos que podría aprobar.");
            listado.add(not);
        }
    }

    private void obtenerNotificacionesEjecutor(List<String> empresas, List<String> ejecutores, List<NotificacionesTO> listado) {
        Criterio filtro;
        filtro = Criterio.forClass(InvPedidos.class);
        filtro.createAlias("invPedidosMotivo", "motivo");
        filtro.add(Restrictions.in("invPedidosPK.pedEmpresa", empresas));
        filtro.add(Restrictions.in("motivo.invPedidosMotivoPK.pmCodigo", ejecutores));
        filtro.add(Restrictions.eq("pedAprobado", true));
        filtro.add(Restrictions.eq("pedEjecutado", false));
        List<InvPedidos> list = invPedidosDao.buscarPorCriteriaSinProyecciones(filtro);
        if (!list.isEmpty()) {
            NotificacionesTO not = new NotificacionesTO();
            not.setCantidad(list.size());
            not.setUrl("/modulos/pedidos/generarOrdenCompra");
            not.setNotificacion("Tiene " + list.size() + " órdenes de compra que podría generar.");
            listado.add(not);
        }
    }

}
