package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.criteria.Criterio;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.PedidosMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosConfiguracionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosMotivoDetalleAprobadoresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosMotivoDetalleEjecutoresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosMotivoDetalleRegistradoresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoCategoria;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoCategoriaPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSectorPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.ArrayList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

@Service
public class PedidosMotivoServiceImpl implements PedidosMotivoService {

    @Autowired
    private PedidosMotivoDao pedidosMotivoDaoOld;
    @Autowired
    private SucesoDao sucesoDao;
    @Autowired
    private GenericoDao<InvPedidosMotivo, InvPedidosMotivoPK> pedidosMotivoDao;
    @Autowired
    private PedidosConfiguracionService pedidosConfiguracionService;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<InvPedidosMotivoTO> getListaInvPedidosMotivo(String empresa, boolean filtrarInactivos) throws Exception {
        List<InvPedidosMotivo> listInvPedidosMotivo = pedidosMotivoDaoOld.getListaInvPedidosMotivo(empresa, filtrarInactivos);
        List<InvPedidosMotivoTO> listInvPedidosMotivoTO = new ArrayList<>();
        listInvPedidosMotivo.stream().map((motivo) -> {
            InvPedidosMotivoTO invPedidosMotivoTO = new InvPedidosMotivoTO();
            invPedidosMotivoTO.convertirObjeto(motivo);
            return invPedidosMotivoTO;
        }).forEach((invPedidosMotivoTO) -> {
            listInvPedidosMotivoTO.add(invPedidosMotivoTO);
        });
        return listInvPedidosMotivoTO;
    }

    @Override
    public String eliminarInvPedidosMotivoTO(InvPedidosMotivoTO invPedidosMotivoTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        InvPedidosMotivo invPedidosMotivoLocal = pedidosMotivoDaoOld.getInvPedidosMotivo(invPedidosMotivoTO.getPmEmpresa(), null, invPedidosMotivoTO.getPmCodigo());
        if (invPedidosMotivoLocal != null) {
            susClave = invPedidosMotivoTO.getPmCodigo();
            susDetalle = "Se eliminó el motivo de pedido: " + invPedidosMotivoTO.getPmDetalle() + " con código " + invPedidosMotivoTO.getPmCodigo();
            susSuceso = "DELETE";
            susTabla = "inventario.inv_pedidos_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            InvPedidosMotivo invPedidosMotivo = new InvPedidosMotivo();
            invPedidosMotivo.convertirObjeto(invPedidosMotivoTO);
            if (pedidosMotivoDaoOld.eliminarInvPedidosMotivo(invPedidosMotivo, sisSuceso)) {
                retorno = "TEl motivo se eliminó correctamente...";
            } else {
                retorno = "FHubo un error al eliminar el motivo...\nIntente de nuevo o contacte con el administrador";
            }
        }
        return retorno;
    }

    @Override
    public List<InvPedidosMotivo> getListaInvPedidosMotivo(String empresa, boolean filtrarInactivos, PrdSectorPK prdSectorPK) throws GeneralException {
        Criterio filtro;
        filtro = Criterio.forClass(InvPedidosMotivo.class);
        filtro.createAlias("prdSector", "sector", JoinType.LEFT_OUTER_JOIN);
        if (!filtrarInactivos) {
            filtro.add(Restrictions.eq("pmInactivo", Boolean.FALSE));
        }
        if (prdSectorPK != null) {
            filtro.add(Restrictions.eq("sector.prdSectorPK.secEmpresa", prdSectorPK.getSecEmpresa()));
            filtro.add(Restrictions.eq("sector.prdSectorPK.secCodigo", prdSectorPK.getSecCodigo()));
        }
        filtro.add(Restrictions.eq("invPedidosMotivoPK.pmEmpresa", empresa));
        filtro.setProjection(Projections.projectionList()
                .add(Projections.property("invPedidosMotivoPK"), "invPedidosMotivoPK")
                .add(Projections.property("pmDetalle"), "pmDetalle")
                .add(Projections.property("pmInactivo"), "pmInactivo")
                .add(Projections.property("usrCodigo"), "usrCodigo")
                .add(Projections.property("pmHoraInicio"), "pmHoraInicio")
                .add(Projections.property("pmHoraFin"), "pmHoraFin")
                .add(Projections.property("pmLunes"), "pmLunes")
                .add(Projections.property("pmMartes"), "pmMartes")
                .add(Projections.property("pmMiercoles"), "pmMiercoles")
                .add(Projections.property("pmJueves"), "pmJueves")
                .add(Projections.property("pmViernes"), "pmViernes")
                .add(Projections.property("pmSabado"), "pmSabado")
                .add(Projections.property("pmDomingo"), "pmDomingo")
                .add(Projections.property("pmAprobacionAutomatica"), "pmAprobacionAutomatica")
                .add(Projections.property("pmNotificarAprobador"), "pmNotificarAprobador")
                .add(Projections.property("pmNotificarRegistrador"), "pmNotificarRegistrador")
                .add(Projections.property("pmNotificarEjecutor"), "pmNotificarEjecutor")
                .add(Projections.property("prdSector"), "prdSector")
                .add(Projections.property("invProductoCategoria"), "invProductoCategoria")
                .add(Projections.property("pmExigirCliente"), "pmExigirCliente")
                .add(Projections.property("pmCambiarFecha"), "pmCambiarFecha")
        );
        filtro.addOrder(Order.asc("invPedidosMotivoPK.pmCodigo"));
        return pedidosMotivoDao.proyeccionPorCriteria(filtro, InvPedidosMotivo.class);
    }

    @Override
    public List<InvPedidosMotivoTO> getListaInvPedidosMotivoTO(String empresa, boolean filtrarInactivos, PrdSectorPK prdSectorPK, InvProductoCategoriaPK invProductoCategoriaPK) throws GeneralException {
        Criterio filtro;
        filtro = Criterio.forClass(InvPedidosMotivo.class);
        filtro.createAlias("prdSector", "sector", JoinType.LEFT_OUTER_JOIN);
        filtro.createAlias("invProductoCategoria", "categoria", JoinType.LEFT_OUTER_JOIN);
        if (!filtrarInactivos) {
            filtro.add(Restrictions.eq("pmInactivo", Boolean.FALSE));
        }
        if (prdSectorPK != null) {
            filtro.add(Restrictions.eq("sector.prdSectorPK.secEmpresa", prdSectorPK.getSecEmpresa()));
            filtro.add(Restrictions.eq("sector.prdSectorPK.secCodigo", prdSectorPK.getSecCodigo()));
        }
        if (invProductoCategoriaPK != null) {
            filtro.add(Restrictions.eq("categoria.invProductoCategoriaPK.catEmpresa", invProductoCategoriaPK.getCatEmpresa()));
            filtro.add(Restrictions.eq("categoria.invProductoCategoriaPK.catCodigo", invProductoCategoriaPK.getCatCodigo()));
        }
        filtro.add(Restrictions.eq("invPedidosMotivoPK.pmEmpresa", empresa));
        filtro.setProjection(Projections.projectionList()
                .add(Projections.property("invPedidosMotivoPK.pmCodigo"), "pmCodigo")
                .add(Projections.property("invPedidosMotivoPK.pmEmpresa"), "pmEmpresa")
                .add(Projections.property("invPedidosMotivoPK.pmSector"), "pmSector")
                .add(Projections.property("pmDetalle"), "pmDetalle")
                .add(Projections.property("pmInactivo"), "pmInactivo")
                .add(Projections.property("pmHoraInicio"), "pmHoraInicio")
                .add(Projections.property("pmHoraFin"), "pmHoraFin")
                .add(Projections.property("pmExigirCliente"), "pmExigirCliente")
                .add(Projections.property("pmCambiarFecha"), "pmCambiarFecha")
                .add(Projections.property("pmAprobacionAutomatica"), "pmAprobacionAutomatica")
                .add(Projections.property("pmNotificarAprobador"), "pmNotificarAprobador")
                .add(Projections.property("pmNotificarRegistrador"), "pmNotificarRegistrador")
                .add(Projections.property("pmNotificarEjecutor"), "pmNotificarEjecutor"));

        filtro.addOrder(Order.asc("pmDetalle"));
        return pedidosMotivoDao.proyeccionPorCriteria(filtro, InvPedidosMotivoTO.class);
    }

    @Override
    public InvPedidosMotivo insertarInvPedidosMotivo(InvPedidosMotivo invPedidosMotivo, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        if (pedidosMotivoDao.obtener(InvPedidosMotivo.class, invPedidosMotivo.getInvPedidosMotivoPK()) != null) {
            throw new GeneralException("El código ingresado ya está siendo utilizado.", "Código duplicado");
        }
        if (obtenerPedidoMotivoPorDetalle(invPedidosMotivo) != null) {
            throw new GeneralException("El detalle ingresado ya está siendo utilizado.", "Detalle duplicado");
        }
        susClave = invPedidosMotivo.getInvPedidosMotivoPK().getPmCodigo();
        susDetalle = "Se insertó el motivo de pedido: " + invPedidosMotivo.getPmDetalle() + " con código " + susClave;
        susSuceso = "INSERT";
        susTabla = "inventario.inv_pedidos_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        //FECHA Y CONVERSION DE TO
        invPedidosMotivo.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
        invPedidosMotivo = pedidosMotivoDao.insertar(invPedidosMotivo);
        sucesoDao.insertar(sisSuceso);
        return invPedidosMotivo;
    }

    @Override
    public InvPedidosMotivo modificarInvPedidosMotivo(InvPedidosMotivo invPedidosMotivo, InvPedidosConfiguracionTO invPedidosConfiguracionTO, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        if (pedidosMotivoDao.existe(InvPedidosMotivo.class, invPedidosMotivo.getInvPedidosMotivoPK())) {
            InvPedidosMotivo invPedidosMot = obtenerPedidoMotivoPorDetalle(invPedidosMotivo);
            if (invPedidosMot != null && !invPedidosMot.getInvPedidosMotivoPK().getPmCodigo().equalsIgnoreCase(invPedidosMotivo.getInvPedidosMotivoPK().getPmCodigo())) {
                throw new GeneralException("El detalle ingresado ya está siendo utilizado.", "Detalle duplicado");
            }
            susClave = invPedidosMotivo.getInvPedidosMotivoPK().getPmCodigo();
            susDetalle = "Se modificó el motivo de pedido: " + invPedidosMotivo.getPmDetalle() + " con código " + susClave;
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_pedidos_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            invPedidosMotivo = pedidosMotivoDao.actualizar(invPedidosMotivo);
            sucesoDao.insertar(sisSuceso);
            if (invPedidosMotivo.getPmAprobacionAutomatica()) {
                resgistradorEsAprobador(invPedidosConfiguracionTO);
            }
            pedidosConfiguracionService.insertarMotivoPedidoConfiguracionTO(invPedidosConfiguracionTO, invPedidosMotivo, false, sisInfoTO);
            return invPedidosMotivo;
        } else {
            throw new GeneralException("El motivo que va a modificar ya no existe. Intente con otro", "Motivo no encontrado.");
        }
    }

    @Override
    public boolean eliminarInvPedidosMotivo(InvPedidosMotivoPK pk, InvPedidosConfiguracionTO conf, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        InvPedidosMotivo invPedidosMotivoLocal = pedidosMotivoDao.obtener(InvPedidosMotivo.class, pk);
        eliminarConfiguracion(conf);
        if (conf.getListAprobadores() != null || conf.getListEjecutores() != null || conf.getListRegistradores() != null) {
            insertarConfiguracion(conf, sisInfoTO);
        }
        if (invPedidosMotivoLocal != null) {
            susClave = invPedidosMotivoLocal.getInvPedidosMotivoPK().getPmCodigo();
            susDetalle = "Se eliminó el motivo de pedido: " + invPedidosMotivoLocal.getPmDetalle() + " con código " + susClave;
            susSuceso = "DELETE";
            susTabla = "inventario.inv_pedidos_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            pedidosMotivoDao.eliminar(invPedidosMotivoLocal);
            sucesoDao.insertar(sisSuceso);
            return true;
        } else {
            throw new GeneralException("El motivo que desea eliminar ya no existe", "Motivo no encontrado");
        }
    }

    @Override
    public boolean modificarEstadoInvPedidosMotivo(InvPedidosMotivoPK pk, boolean estado, SisInfoTO sisInfoTO) throws GeneralException {
        InvPedidosMotivo invPedidosMotivoLocal = pedidosMotivoDao.obtener(InvPedidosMotivo.class, pk);
        if (invPedidosMotivoLocal != null) {
            susClave = invPedidosMotivoLocal.getInvPedidosMotivoPK().getPmCodigo();
            susDetalle = "Se cambió el estado del motivo de pedido: " + invPedidosMotivoLocal.getPmDetalle() + " con código " + susClave;
            susSuceso = "UPDATE";
            susTabla = "inventario.inv_pedidos_motivo";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            invPedidosMotivoLocal.setPmInactivo(estado);
            pedidosMotivoDao.actualizar(invPedidosMotivoLocal);
            sucesoDao.insertar(sisSuceso);
            return true;
        } else {
            throw new GeneralException("El motivo que desea modificar ya no existe", "El motivo que desea modificar ya no existe");
        }
    }

    @Override
    public InvPedidosMotivo getInvPedidosMotivo(InvPedidosMotivoPK invPedidosMotivoPK) throws GeneralException {
        InvPedidosMotivo invPedidosMotivo = pedidosMotivoDao.obtener(InvPedidosMotivo.class, invPedidosMotivoPK);
        if (invPedidosMotivo != null) {
            if (invPedidosMotivo.getPrdSector() != null) {
                invPedidosMotivo.setPrdSector(new PrdSector(invPedidosMotivo.getPrdSector().getPrdSectorPK()));
            }
            if (invPedidosMotivo.getInvProductoCategoria() != null) {
                String detalle = invPedidosMotivo.getInvProductoCategoria().getCatDetalle();
                invPedidosMotivo.setInvProductoCategoria(new InvProductoCategoria(invPedidosMotivo.getInvProductoCategoria().getInvProductoCategoriaPK()));
                invPedidosMotivo.getInvProductoCategoria().setCatDetalle(detalle);
            }
        }
        return invPedidosMotivo;
    }

    private InvPedidosMotivo obtenerPedidoMotivoPorDetalle(InvPedidosMotivo invPedidosMotivo) {
        Criterio filtro;
        filtro = Criterio.forClass(InvPedidosMotivo.class);
        filtro.add(Restrictions.eq("invPedidosMotivoPK.pmEmpresa", invPedidosMotivo.getInvPedidosMotivoPK().getPmEmpresa()));
        filtro.add(Restrictions.eq("invPedidosMotivoPK.pmSector", invPedidosMotivo.getInvPedidosMotivoPK().getPmSector()));
        filtro.add(Restrictions.eq("pmDetalle", invPedidosMotivo.getPmDetalle()));
        return pedidosMotivoDao.obtenerPorCriteriaSinProyeccionesDistinct(filtro);
    }

    private void insertarConfiguracion(InvPedidosConfiguracionTO invPedidosConfiguracionTO, SisInfoTO sisInfoTO) throws Exception {
        pedidosConfiguracionService.insertarInvPedidosConfiguracionTO(invPedidosConfiguracionTO, sisInfoTO);
    }

    private void eliminarConfiguracion(InvPedidosConfiguracionTO conf) throws Exception {
        List<InvPedidosMotivoDetalleRegistradoresTO> listRegistradores = conf.getListRegistradores();
        List<InvPedidosMotivoDetalleAprobadoresTO> listAprobadores = conf.getListAprobadores();
        List<InvPedidosMotivoDetalleEjecutoresTO> listEjecutores = conf.getListEjecutores();

        for (InvPedidosMotivoDetalleRegistradoresTO registrador : listRegistradores) {
            registrador.setActivo(false);
        }
        for (InvPedidosMotivoDetalleAprobadoresTO aprobador : listAprobadores) {
            aprobador.setActivo(false);
        }
        for (InvPedidosMotivoDetalleEjecutoresTO ejecutor : listEjecutores) {
            ejecutor.setActivo(false);
        }
    }

    private void resgistradorEsAprobador(InvPedidosConfiguracionTO invPedidosConfiguracionTO) {
        if (invPedidosConfiguracionTO != null) {
            List<InvPedidosMotivoDetalleRegistradoresTO> registradores = invPedidosConfiguracionTO.getListRegistradores();
            List<InvPedidosMotivoDetalleAprobadoresTO> aprobadores = invPedidosConfiguracionTO.getListAprobadores();
            if (registradores != null) {
                for (InvPedidosMotivoDetalleRegistradoresTO registrador : registradores) {
                    InvPedidosMotivoDetalleAprobadoresTO aprobadorRegistrador = new InvPedidosMotivoDetalleAprobadoresTO(registrador.getInvPedidosMotivoTO());
                    aprobadorRegistrador.setActivo(true);
                    aprobadorRegistrador.setDetSecuencial(0);
                    aprobadorRegistrador.setUsuario(registrador.getUsuario());
                    if (aprobadores != null && aprobadores.size() > 0) {
                        boolean rpta = true;
                        for (InvPedidosMotivoDetalleAprobadoresTO aprobador : aprobadores) {
                            if (aprobador.getUsuario() != null && aprobador.getUsuario().getUsrCodigo().equals(aprobadorRegistrador.getUsuario().getUsrCodigo())) {
                                rpta = false;
                            }
                        }
                        if (rpta) {
                            aprobadores.add(aprobadorRegistrador);
                        }
                    } else {
                        aprobadores = new ArrayList<>();
                        aprobadores.add(aprobadorRegistrador);
                    }
                }
            }
            invPedidosConfiguracionTO.setListAprobadores(aprobadores);
        }
    }
}
