package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.PedidosMotivoDetalleAprobadoresDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.PedidosMotivoDetalleEjecutoresDao;
import ec.com.todocompu.ShrimpSoftServer.inventario.dao.PedidosMotivoDetalleRegistradoresDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.SectorService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SisEmpresaNotificacionesDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.service.UsuarioDetalleService;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosConfiguracionTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosMotivoDetalleAprobadoresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosMotivoDetalleEjecutoresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosMotivoDetalleRegistradoresTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvPedidosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoCategoriaTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivo;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoDetalleAprobadores;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoDetalleEjecutores;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoDetalleRegistradores;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvPedidosMotivoPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaUsuarioTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaNotificaciones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisUsuarioDetalle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PedidosConfiguracionServiceImpl implements PedidosConfiguracionService {
    
    @Autowired
    private PedidosMotivoDetalleAprobadoresDao pedidosMotivoDetalleAprobadoresDao;
    @Autowired
    private PedidosMotivoDetalleEjecutoresDao pedidosMotivoDetalleEjecutoresDao;
    @Autowired
    private PedidosMotivoDetalleRegistradoresDao pedidosMotivoDetalleRegistradoresDao;
    @Autowired
    private ProductoCategoriaService productoCategoriaService;
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
    public InvPedidosConfiguracionTO getListaInvPedidosConfiguracionTO(InvPedidosMotivoPK invPedidosMotivoPK, SisInfoTO sisInfoTO) throws Exception {
        List<InvPedidosMotivoDetalleAprobadores> listInvPedidosMotivoDetalleAprobadores = pedidosMotivoDetalleAprobadoresDao.getListaInvPedidosMotivoDetalleAprobadores(invPedidosMotivoPK);
        List<InvPedidosMotivoDetalleEjecutores> listInvPedidosMotivoDetalleEjecutores = pedidosMotivoDetalleEjecutoresDao.getListaInvPedidosMotivoDetalleEjecutores(invPedidosMotivoPK);
        List<InvPedidosMotivoDetalleRegistradores> listInvPedidosMotivoDetalleRegistradores = pedidosMotivoDetalleRegistradoresDao.getListaInvPedidosMotivoDetalleRegistradores(invPedidosMotivoPK);
        
        InvPedidosConfiguracionTO invPedidosConfiguracionTO = new InvPedidosConfiguracionTO();
        
        List<InvPedidosMotivoDetalleAprobadores> listDetalleAprobadoresTemporal = listInvPedidosMotivoDetalleAprobadores;
        listInvPedidosMotivoDetalleAprobadores = new ArrayList<>();
        for (InvPedidosMotivoDetalleAprobadores item : listDetalleAprobadoresTemporal) {
            SisUsuarioDetalle detalleUsuario = usuarioDetalleService.obtenerDetalleUsuario(sisInfoTO.getEmpresa(), item.getSisUsuario().getUsrCodigo());
            if (detalleUsuario.getDetActivo()) {
                listInvPedidosMotivoDetalleAprobadores.add(item);
            }
        }
        
        listInvPedidosMotivoDetalleRegistradores.stream().map((invPedidosMotivoDetalleRegistradores) -> {
            InvPedidosMotivoDetalleRegistradoresTO invPedidosMotivoDetalleRegistradoresTO = new InvPedidosMotivoDetalleRegistradoresTO();
            invPedidosMotivoDetalleRegistradoresTO.convertirObjeto(invPedidosMotivoDetalleRegistradores);
            return invPedidosMotivoDetalleRegistradoresTO;
        }).forEach((invPedidosMotivoDetalleRegistradoresTO) -> {
            invPedidosConfiguracionTO.getListRegistradores().add(invPedidosMotivoDetalleRegistradoresTO);
        });
        listInvPedidosMotivoDetalleAprobadores.stream().map((invPedidosMotivoDetalleAprobadores) -> {
            InvPedidosMotivoDetalleAprobadoresTO invPedidosMotivoDetalleAprobadoresTO = new InvPedidosMotivoDetalleAprobadoresTO();
            invPedidosMotivoDetalleAprobadoresTO.convertirObjeto(invPedidosMotivoDetalleAprobadores);
            return invPedidosMotivoDetalleAprobadoresTO;
        }).forEach((invPedidosMotivoDetalleAprobadoresTO) -> {
            invPedidosConfiguracionTO.getListAprobadores().add(invPedidosMotivoDetalleAprobadoresTO);
        });
        listInvPedidosMotivoDetalleEjecutores.stream().map((invPedidosMotivoDetalleEjecutores) -> {
            InvPedidosMotivoDetalleEjecutoresTO invPedidosMotivoDetalleEjecutoresTO = new InvPedidosMotivoDetalleEjecutoresTO();
            invPedidosMotivoDetalleEjecutoresTO.convertirObjeto(invPedidosMotivoDetalleEjecutores);
            return invPedidosMotivoDetalleEjecutoresTO;
        }).forEach((invPedidosMotivoDetalleEjecutoresTO) -> {
            invPedidosConfiguracionTO.getListEjecutores().add(invPedidosMotivoDetalleEjecutoresTO);
        });
        return invPedidosConfiguracionTO;
    }
    
    @Override
    public String insertarInvPedidosConfiguracionTO(InvPedidosConfiguracionTO invPedidosConfiguracionTO, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        for (InvPedidosMotivoDetalleRegistradoresTO invPedidosMotivoDetalleRegistradoresTO : invPedidosConfiguracionTO.getListRegistradores()) {
            if (!invPedidosMotivoDetalleRegistradoresTO.isActivo() && invPedidosMotivoDetalleRegistradoresTO.getDetSecuencial() != 0) {
                susClave = invPedidosMotivoDetalleRegistradoresTO.getDetSecuencial().toString();
                susDetalle = "Se eliminó la configuración: " + invPedidosMotivoDetalleRegistradoresTO.getDetSecuencial().toString() + " con código " + invPedidosMotivoDetalleRegistradoresTO.getDetSecuencial().toString();
                susSuceso = "DELETE";
                susTabla = "inventario.inv_pedidos_motivo_detalle_registradores";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                InvPedidosMotivoDetalleRegistradores invPedidosMotivoDetalleRegistradores = new InvPedidosMotivoDetalleRegistradores();
                invPedidosMotivoDetalleRegistradores.convertirObjeto(invPedidosMotivoDetalleRegistradoresTO);
                pedidosMotivoDetalleRegistradoresDao.eliminarInvPedidosMotivoDetalleRegistradores(invPedidosMotivoDetalleRegistradores, sisSuceso);
            }
            if (invPedidosMotivoDetalleRegistradoresTO.getDetSecuencial() == 0 && invPedidosMotivoDetalleRegistradoresTO.isActivo() && invPedidosMotivoDetalleRegistradoresTO.getUsuario().getUsrCodigo() != null) {
                susClave = invPedidosMotivoDetalleRegistradoresTO.getInvPedidosMotivoTO().getPmCodigo();
                susDetalle = "Se insertó la configuración: " + invPedidosMotivoDetalleRegistradoresTO.getInvPedidosMotivoTO().getPmCodigo();
                susSuceso = "INSERT";
                susTabla = "inventario.inv_pedidos_motivo_detalle_registradores";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                InvPedidosMotivoDetalleRegistradores invPedidosMotivoDetalleRegistradores = new InvPedidosMotivoDetalleRegistradores();
                invPedidosMotivoDetalleRegistradores.convertirObjeto(invPedidosMotivoDetalleRegistradoresTO);
                pedidosMotivoDetalleRegistradoresDao.insertarInvPedidosMotivoDetalleRegistradores(invPedidosMotivoDetalleRegistradores, sisSuceso);
            }
        }
        for (InvPedidosMotivoDetalleAprobadoresTO invPedidosMotivoDetalleAprobadoresTO : invPedidosConfiguracionTO.getListAprobadores()) {
            if (!invPedidosMotivoDetalleAprobadoresTO.isActivo() && invPedidosMotivoDetalleAprobadoresTO.getDetSecuencial() != 0) {
                susClave = invPedidosMotivoDetalleAprobadoresTO.getDetSecuencial().toString();
                susDetalle = "Se eliminó la configuración: " + invPedidosMotivoDetalleAprobadoresTO.getDetSecuencial().toString() + " con código " + invPedidosMotivoDetalleAprobadoresTO.getDetSecuencial().toString();
                susSuceso = "DELETE";
                susTabla = "inventario.inv_pedidos_motivo_detalle_aprobadores";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                InvPedidosMotivoDetalleAprobadores invPedidosMotivoDetalleAprobadores = new InvPedidosMotivoDetalleAprobadores();
                invPedidosMotivoDetalleAprobadores.convertirObjeto(invPedidosMotivoDetalleAprobadoresTO);
                pedidosMotivoDetalleAprobadoresDao.eliminarInvPedidosMotivoDetalleAprobadores(invPedidosMotivoDetalleAprobadores, sisSuceso);
            }
            if (invPedidosMotivoDetalleAprobadoresTO.getDetSecuencial() == 0 && invPedidosMotivoDetalleAprobadoresTO.isActivo() && invPedidosMotivoDetalleAprobadoresTO.getUsuario().getUsrCodigo() != null) {
                susClave = invPedidosMotivoDetalleAprobadoresTO.getInvPedidosMotivoTO().getPmCodigo();
                susDetalle = "Se insertó la configuración: " + invPedidosMotivoDetalleAprobadoresTO.getInvPedidosMotivoTO().getPmCodigo();
                susSuceso = "INSERT";
                susTabla = "inventario.inv_pedidos_motivo_detalle_aprobadores";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                InvPedidosMotivoDetalleAprobadores invPedidosMotivoDetalleAprobadores = new InvPedidosMotivoDetalleAprobadores();
                invPedidosMotivoDetalleAprobadores.convertirObjeto(invPedidosMotivoDetalleAprobadoresTO);
                pedidosMotivoDetalleAprobadoresDao.insertarInvPedidosMotivoDetalleAprobadores(invPedidosMotivoDetalleAprobadores, sisSuceso);
            }
        }
        for (InvPedidosMotivoDetalleEjecutoresTO invPedidosMotivoDetalleEjecutoresTO : invPedidosConfiguracionTO.getListEjecutores()) {
            if (!invPedidosMotivoDetalleEjecutoresTO.isActivo() && invPedidosMotivoDetalleEjecutoresTO.getDetSecuencial() != 0) {
                susClave = invPedidosMotivoDetalleEjecutoresTO.getDetSecuencial().toString();
                susDetalle = "Se eliminó la configuración: " + invPedidosMotivoDetalleEjecutoresTO.getDetSecuencial().toString() + " con código " + invPedidosMotivoDetalleEjecutoresTO.getDetSecuencial().toString();
                susSuceso = "DELETE";
                susTabla = "inventario.inv_pedidos_motivo_detalle_ejecutores";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                InvPedidosMotivoDetalleEjecutores invPedidosMotivoDetalleEjecutores = new InvPedidosMotivoDetalleEjecutores();
                invPedidosMotivoDetalleEjecutores.convertirObjeto(invPedidosMotivoDetalleEjecutoresTO);
                pedidosMotivoDetalleEjecutoresDao.eliminarInvPedidosMotivoDetalleEjecutores(invPedidosMotivoDetalleEjecutores, sisSuceso);
            }
            if (invPedidosMotivoDetalleEjecutoresTO.getDetSecuencial() == 0 && invPedidosMotivoDetalleEjecutoresTO.isActivo() && invPedidosMotivoDetalleEjecutoresTO.getUsuario().getUsrCodigo() != null) {
                susClave = invPedidosMotivoDetalleEjecutoresTO.getInvPedidosMotivoTO().getPmCodigo();
                susDetalle = "Se insertó la configuración: " + invPedidosMotivoDetalleEjecutoresTO.getInvPedidosMotivoTO().getPmCodigo();
                susSuceso = "INSERT";
                susTabla = "inventario.inv_pedidos_motivo_detalle_ejecutores";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                InvPedidosMotivoDetalleEjecutores invPedidosMotivoDetalleEjecutores = new InvPedidosMotivoDetalleEjecutores();
                invPedidosMotivoDetalleEjecutores.convertirObjeto(invPedidosMotivoDetalleEjecutoresTO);
                pedidosMotivoDetalleEjecutoresDao.insertarInvPedidosMotivoDetalleEjecutores(invPedidosMotivoDetalleEjecutores, sisSuceso);
            }
        }
        retorno = "TLa configuración se guardo correctamente...";
        return retorno;
    }
    
    @Override
    public InvPedidosMotivo insertarMotivoPedidoConfiguracionTO(InvPedidosConfiguracionTO invPedidosConfiguracionTO, InvPedidosMotivo invPedidosMotivo, boolean parametro, SisInfoTO sisInfoTO) throws Exception {
        InvPedidosMotivoTO invPedidosMotivoTO = ConversionesInventario.convertirInvPedidosMotivoTO_InvPedidosMotivo(invPedidosMotivo);
        for (InvPedidosMotivoDetalleRegistradoresTO invPedidosMotivoDetalleRegistradoresTO : invPedidosConfiguracionTO.getListRegistradores()) {
            invPedidosMotivoDetalleRegistradoresTO.setInvPedidosMotivoTO(invPedidosMotivoTO);
            if (!invPedidosMotivoDetalleRegistradoresTO.isActivo() && invPedidosMotivoDetalleRegistradoresTO.getDetSecuencial() != 0) {
                susClave = invPedidosMotivoDetalleRegistradoresTO.getDetSecuencial().toString();
                susDetalle = "Se eliminó la configuración: " + invPedidosMotivoDetalleRegistradoresTO.getDetSecuencial().toString() + " con código " + invPedidosMotivoDetalleRegistradoresTO.getDetSecuencial().toString();
                susSuceso = "DELETE";
                susTabla = "inventario.inv_pedidos_motivo_detalle_registradores";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                InvPedidosMotivoDetalleRegistradores invPedidosMotivoDetalleRegistradores = new InvPedidosMotivoDetalleRegistradores();
                invPedidosMotivoDetalleRegistradores.convertirObjeto(invPedidosMotivoDetalleRegistradoresTO);
                try {
                    pedidosMotivoDetalleRegistradoresDao.eliminarInvPedidosMotivoDetalleRegistradores(invPedidosMotivoDetalleRegistradores, sisSuceso);
                } catch (Exception ex) {
                    Logger.getLogger(PedidosConfiguracionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (invPedidosMotivoDetalleRegistradoresTO.getDetSecuencial() == 0 && invPedidosMotivoDetalleRegistradoresTO.isActivo() && invPedidosMotivoDetalleRegistradoresTO.getUsuario().getUsrCodigo() != null) {
                susClave = invPedidosMotivoDetalleRegistradoresTO.getInvPedidosMotivoTO().getPmCodigo();
                susDetalle = "Se insertó la configuración: " + invPedidosMotivoDetalleRegistradoresTO.getInvPedidosMotivoTO().getPmCodigo();
                susSuceso = "INSERT";
                susTabla = "inventario.inv_pedidos_motivo_detalle_registradores";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                InvPedidosMotivoDetalleRegistradores invPedidosMotivoDetalleRegistradores = new InvPedidosMotivoDetalleRegistradores();
                invPedidosMotivoDetalleRegistradores.convertirObjeto(invPedidosMotivoDetalleRegistradoresTO);
                try {
                    pedidosMotivoDetalleRegistradoresDao.insertarInvPedidosMotivoDetalleRegistradores(invPedidosMotivoDetalleRegistradores, sisSuceso);
                } catch (Exception ex) {
                    Logger.getLogger(PedidosConfiguracionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        for (InvPedidosMotivoDetalleAprobadoresTO invPedidosMotivoDetalleAprobadoresTO : invPedidosConfiguracionTO.getListAprobadores()) {
            invPedidosMotivoDetalleAprobadoresTO.setInvPedidosMotivoTO(invPedidosMotivoTO);
            if (!invPedidosMotivoDetalleAprobadoresTO.isActivo() && invPedidosMotivoDetalleAprobadoresTO.getDetSecuencial() != 0) {
                susClave = invPedidosMotivoDetalleAprobadoresTO.getDetSecuencial().toString();
                susDetalle = "Se eliminó la configuración: " + invPedidosMotivoDetalleAprobadoresTO.getDetSecuencial().toString() + " con código " + invPedidosMotivoDetalleAprobadoresTO.getDetSecuencial().toString();
                susSuceso = "DELETE";
                susTabla = "inventario.inv_pedidos_motivo_detalle_aprobadores";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                InvPedidosMotivoDetalleAprobadores invPedidosMotivoDetalleAprobadores = new InvPedidosMotivoDetalleAprobadores();
                invPedidosMotivoDetalleAprobadores.convertirObjeto(invPedidosMotivoDetalleAprobadoresTO);
                try {
                    pedidosMotivoDetalleAprobadoresDao.eliminarInvPedidosMotivoDetalleAprobadores(invPedidosMotivoDetalleAprobadores, sisSuceso);
                } catch (Exception ex) {
                    Logger.getLogger(PedidosConfiguracionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (invPedidosMotivoDetalleAprobadoresTO.getDetSecuencial() == 0 && invPedidosMotivoDetalleAprobadoresTO.isActivo() && invPedidosMotivoDetalleAprobadoresTO.getUsuario().getUsrCodigo() != null) {
                susClave = invPedidosMotivoDetalleAprobadoresTO.getInvPedidosMotivoTO().getPmCodigo();
                susDetalle = "Se insertó la configuración: " + invPedidosMotivoDetalleAprobadoresTO.getInvPedidosMotivoTO().getPmCodigo();
                susSuceso = "INSERT";
                susTabla = "inventario.inv_pedidos_motivo_detalle_aprobadores";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                InvPedidosMotivoDetalleAprobadores invPedidosMotivoDetalleAprobadores = new InvPedidosMotivoDetalleAprobadores();
                invPedidosMotivoDetalleAprobadores.convertirObjeto(invPedidosMotivoDetalleAprobadoresTO);
                try {
                    pedidosMotivoDetalleAprobadoresDao.insertarInvPedidosMotivoDetalleAprobadores(invPedidosMotivoDetalleAprobadores, sisSuceso);
                } catch (Exception ex) {
                    Logger.getLogger(PedidosConfiguracionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        for (InvPedidosMotivoDetalleEjecutoresTO invPedidosMotivoDetalleEjecutoresTO : invPedidosConfiguracionTO.getListEjecutores()) {
            invPedidosMotivoDetalleEjecutoresTO.setInvPedidosMotivoTO(invPedidosMotivoTO);
            if (!invPedidosMotivoDetalleEjecutoresTO.isActivo() && invPedidosMotivoDetalleEjecutoresTO.getDetSecuencial() != 0) {
                susClave = invPedidosMotivoDetalleEjecutoresTO.getDetSecuencial().toString();
                susDetalle = "Se eliminó la configuración: " + invPedidosMotivoDetalleEjecutoresTO.getDetSecuencial().toString() + " con código " + invPedidosMotivoDetalleEjecutoresTO.getDetSecuencial().toString();
                susSuceso = "DELETE";
                susTabla = "inventario.inv_pedidos_motivo_detalle_ejecutores";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                InvPedidosMotivoDetalleEjecutores invPedidosMotivoDetalleEjecutores = new InvPedidosMotivoDetalleEjecutores();
                invPedidosMotivoDetalleEjecutores.convertirObjeto(invPedidosMotivoDetalleEjecutoresTO);
                try {
                    pedidosMotivoDetalleEjecutoresDao.eliminarInvPedidosMotivoDetalleEjecutores(invPedidosMotivoDetalleEjecutores, sisSuceso);
                } catch (Exception ex) {
                    Logger.getLogger(PedidosConfiguracionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (invPedidosMotivoDetalleEjecutoresTO.getDetSecuencial() == 0 && invPedidosMotivoDetalleEjecutoresTO.isActivo() && invPedidosMotivoDetalleEjecutoresTO.getUsuario().getUsrCodigo() != null) {
                susClave = invPedidosMotivoDetalleEjecutoresTO.getInvPedidosMotivoTO().getPmCodigo();
                susDetalle = "Se insertó la configuración: " + invPedidosMotivoDetalleEjecutoresTO.getInvPedidosMotivoTO().getPmCodigo();
                susSuceso = "INSERT";
                susTabla = "inventario.inv_pedidos_motivo_detalle_ejecutores";
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                InvPedidosMotivoDetalleEjecutores invPedidosMotivoDetalleEjecutores = new InvPedidosMotivoDetalleEjecutores();
                invPedidosMotivoDetalleEjecutores.convertirObjeto(invPedidosMotivoDetalleEjecutoresTO);
                try {
                    pedidosMotivoDetalleEjecutoresDao.insertarInvPedidosMotivoDetalleEjecutores(invPedidosMotivoDetalleEjecutores, sisSuceso);
                } catch (Exception ex) {
                    Logger.getLogger(PedidosConfiguracionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return invPedidosMotivo;
    }
    
    @Override
    public Map<String, Object> obtenerDatosParaConfiguracionDePedidos(String empresa, SisInfoTO sisInfoTO) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<SisListaUsuarioTO> listaUsuario = usuarioDetalleService.getListaSisUsuarios(empresa);
        List<SisEmpresaNotificaciones> listadoConfNoticaciones = sisEmpresaNotificacionesDao.listarSisEmpresaNotificaciones(empresa);
        List<PrdListaSectorTO> listaSectores = sectorService.getListaSectorTO(empresa, false);
        List<InvProductoCategoriaTO> listaCategorias = productoCategoriaService.getInvProductoCategoriaTO(empresa);
        map.put("listaUsuario", listaUsuario);
        map.put("listaSectores", listaSectores);
        map.put("listadoConfNoticaciones", listadoConfNoticaciones);
        map.put("listaCategorias", listaCategorias);
        return map;
    }
    
}
