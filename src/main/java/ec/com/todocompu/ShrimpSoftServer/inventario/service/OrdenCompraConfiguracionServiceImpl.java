package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.criteria.Criterio;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.SectorService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SisEmpresaNotificacionesDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.UsuarioDetalleService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvOrdenCompraMotivoDetalleAprobadoresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraMotivoDetalleAprobadores;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosOrdenCompraMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaUsuarioTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.criterion.Restrictions;

@Service
public class OrdenCompraConfiguracionServiceImpl implements OrdenCompraConfiguracionService {

    @Autowired
    private GenericoDao<InvPedidosOrdenCompraMotivoDetalleAprobadores, Integer> aprobadoresDao;
    @Autowired
    private GenericoDao<SisSuceso, Integer> susecoDao;
    @Autowired
    private SectorService sectorService;
    @Autowired
    private UsuarioDetalleService usuarioDetalleService;
    @Autowired
    private SisEmpresaNotificacionesDao sisEmpresaNotificacionesDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<InvOrdenCompraMotivoDetalleAprobadoresTO> getListaInvOrdenCompraMotivoDetalleAprobadoresTO(InvPedidosOrdenCompraMotivoPK pk, SisInfoTO sisInfoTO) throws Exception {
        List<InvPedidosOrdenCompraMotivoDetalleAprobadores> invPedidosOrdenCompraMotivoDetalleAprobadores = obtenerAprobadores(pk);

        List<InvOrdenCompraMotivoDetalleAprobadoresTO> invPedidosConfiguracionTO = new ArrayList<>();
        invPedidosOrdenCompraMotivoDetalleAprobadores.stream().map((invPedidosMotivoDetalleAprobadores) -> {
            InvOrdenCompraMotivoDetalleAprobadoresTO invOrdenCompraMotivoDetalleAprobadoresTO = new InvOrdenCompraMotivoDetalleAprobadoresTO();
            invOrdenCompraMotivoDetalleAprobadoresTO.convertirObjeto(invPedidosMotivoDetalleAprobadores);
            return invOrdenCompraMotivoDetalleAprobadoresTO;
        }).forEach((invPedidosMotivoDetalleAprobadoresTO) -> {
            invPedidosConfiguracionTO.add(invPedidosMotivoDetalleAprobadoresTO);
        });
        return invPedidosConfiguracionTO;
    }

    @Override
    public boolean insertarInvOrdenCompraConfiguracionTO(List<InvOrdenCompraMotivoDetalleAprobadoresTO> invOrdenCompraMotivoDetalleAprobadores, SisInfoTO sisInfoTO) throws Exception {
        boolean retorno = false;
        for (InvOrdenCompraMotivoDetalleAprobadoresTO aprobadores : invOrdenCompraMotivoDetalleAprobadores) {
            if (!aprobadores.isActivo() && aprobadores.getDetSecuencial() != 0) {
                susClave = aprobadores.getDetSecuencial().toString();
                susDetalle = "Se eliminó la configuración: " + aprobadores.getDetSecuencial().toString() + " con código " + aprobadores.getDetSecuencial().toString();
                susSuceso = "DELETE";
                susTabla = "inventario.inv_pedidos_orden_compra_motivo_detalle_registradores";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                InvPedidosOrdenCompraMotivoDetalleAprobadores aprobador = new InvPedidosOrdenCompraMotivoDetalleAprobadores(aprobadores.getDetSecuencial());
                aprobadoresDao.eliminar(aprobador);
                susecoDao.insertar(sisSuceso);
            }
            if (aprobadores.getDetSecuencial() == 0 && aprobadores.isActivo() && aprobadores.getUsuario().getUsrCodigo() != null) {
                susClave = aprobadores.getInvPedidosOrdenCompraMotivo().getOcmDetalle();
                susDetalle = "Se insertó la configuración: " + susClave;
                susSuceso = "INSERT";
                susTabla = "inventario.inv_pedidos_motivo_detalle_registradores";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                InvPedidosOrdenCompraMotivoDetalleAprobadores aprobador = new InvPedidosOrdenCompraMotivoDetalleAprobadores();
                aprobador.convertirObjeto(aprobadores);
                aprobadoresDao.insertar(aprobador);
                susecoDao.insertar(sisSuceso);
            }
            //actualizar
            if (aprobadores.getDetSecuencial() != 0 && aprobadores.isActivo() && aprobadores.getUsuario().getUsrCodigo() != null) {
                susClave = aprobadores.getInvPedidosOrdenCompraMotivo().getOcmDetalle();
                susDetalle = "Se modificó la configuración: " + susClave;
                susSuceso = "UPDATE";
                susTabla = "inventario.inv_pedidos_motivo_detalle_registradores";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                InvPedidosOrdenCompraMotivoDetalleAprobadores aprobador = new InvPedidosOrdenCompraMotivoDetalleAprobadores();
                aprobador.convertirObjeto(aprobadores);
                aprobadoresDao.actualizar(aprobador);
                susecoDao.insertar(sisSuceso);
            }

        }
        retorno = true;
        return retorno;
    }

    private List<InvPedidosOrdenCompraMotivoDetalleAprobadores> obtenerAprobadores(InvPedidosOrdenCompraMotivoPK pk) {
        Criterio filtro;
        filtro = Criterio.forClass(InvPedidosOrdenCompraMotivoDetalleAprobadores.class);
        filtro.add(Restrictions.eq("invPedidosOrdenCompraMotivo.invPedidosOrdenCompraMotivoPK", pk));
        return aprobadoresDao.buscarPorCriteriaSinProyecciones(filtro);
    }

    @Override
    public Map<String, Object> obtenerDatosParaConfiguracionDeOrdenCompra(String empresa, SisInfoTO sisInfoTO) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<SisListaUsuarioTO> listaUsuario = usuarioDetalleService.getListaSisUsuarios(empresa);
        List<PrdListaSectorTO> listaSectores = sectorService.getListaSectorTO(empresa, false);
        List<SisEmpresaNotificaciones> listadoConfNoticaciones = sisEmpresaNotificacionesDao.listarSisEmpresaNotificaciones(empresa);
        map.put("listaUsuario", listaUsuario);
        map.put("listaSectores", listaSectores);
        map.put("listadoConfNoticaciones", listadoConfNoticaciones);
        return map;
    }

}
