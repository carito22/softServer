/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftServer.anexos.service.TipoComprobanteService;
import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.BodegaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasFormaPagoService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ComprasMotivoService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.SectorService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.ConfiguracionComprasDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteComboTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxTipoComprobanteTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvComprasMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaBodegasTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaUsuarioTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisConfiguracionCompras;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisConfiguracionComprasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author CarolValdiviezo
 */
@Service
public class ConfiguracionComprasServiceImpl implements ConfiguracionComprasService {

    @Autowired
    private GenericoDao<SisConfiguracionCompras, SisConfiguracionComprasPK> configuracionComprasDao;
    @Autowired
    private ConfiguracionComprasDao configuracionComprasOldDao;
    @Autowired
    private SectorService sectorService;
    @Autowired
    private BodegaService bodegaService;
    @Autowired
    private ComprasMotivoService comprasMotivoService;
    @Autowired
    private UsuarioDetalleService usuarioDetalleService;
    @Autowired
    private TipoComprobanteService tipoComprobanteService;
    @Autowired
    private ComprasFormaPagoService comprasFormaPagoService;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public List<SisConfiguracionCompras> listarSisConfiguracionCompras(String empresa) throws Exception {
        return configuracionComprasOldDao.listarSisConfiguracionCompras(empresa);
    }

    @Override
    public Map<String, Object> obtenerDatosParaSisConfiguracionCompras(String empresa, String sector, boolean isNuevo, SisInfoTO sisInfoTO) throws Exception {
        Map<String, Object> campos = new HashMap<>();

        List<SisListaUsuarioTO> listaSisListaUsuarioTO = isNuevo ? usuarioDetalleService.getListaSisUsuariosNoTieneConfCompras(empresa) : usuarioDetalleService.getListaSisUsuarios(empresa);
        List<InvListaBodegasTO> listaInvListaBodegasTO = bodegaService.buscarBodegasPorSector(empresa, false, sector);
        List<AnxTipoComprobanteComboTO> listaAnxTipoComprobanteComboTO = tipoComprobanteService.getListaAnxTipoComprobanteComboTO(null);
        List<PrdListaSectorTO> listPrdListaSectorTO = sectorService.getListaSectorTO(empresa, false);
        List<InvComprasMotivoTO> listaInvComprasMotivoTO = comprasMotivoService.getListaInvComprasMotivoTO(empresa, true);
        List<InvComboFormaPagoTO> listaFormaPago = comprasFormaPagoService.getComboFormaPagoCompra(empresa);
        campos.put("listaSisListaUsuarioTO", listaSisListaUsuarioTO);
        campos.put("listaAnxTipoComprobanteComboTO", listaAnxTipoComprobanteComboTO);
        campos.put("listaInvListaBodegasTO", listaInvListaBodegasTO);
        campos.put("listaPrdListaSectorTO", listPrdListaSectorTO);
        campos.put("listaInvComprasMotivoTO", listaInvComprasMotivoTO);
        campos.put("listaFormaPago", listaFormaPago);
        return campos;
    }

    @Override
    public SisConfiguracionCompras insertarSisConfiguracionCompras(SisConfiguracionCompras configuracionCompras, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        if (!configuracionComprasDao.existe(SisConfiguracionCompras.class, configuracionCompras.getSisConfiguracionComprasPK())) {
            //PREAPARANDO SISUSCESO
            susClave = configuracionCompras.getSisConfiguracionComprasPK().getConfUsuarioResponsable();
            susDetalle = "Se insertó la configuración de compras con código " + susClave;
            susSuceso = "INSERT";
            susTabla = "sistemaweb.sis_configuracion_compras";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            configuracionCompras.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
            configuracionCompras = configuracionComprasDao.insertar(configuracionCompras);
            sucesoDao.insertar(sisSuceso);
            return configuracionCompras;
        } else {
            return null;
        }
    }

    @Override
    public SisConfiguracionCompras modificarSisConfiguracionCompras(SisConfiguracionCompras configuracionCompras, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        //validar que nadie realice la misma accion, p.e que intenten aprobar a la misma vez.
        //PREAPARANDO SISUSCESO
        susClave = configuracionCompras.getSisConfiguracionComprasPK().getConfUsuarioResponsable();
        susDetalle = "Se modificó la orden de pedido con código " + susClave;
        susSuceso = "UPDATE";
        susTabla = "sistemaweb.sis_configuracion_compras";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        //FECHA Y CONVERSION DE TO
        configuracionCompras = configuracionComprasDao.actualizar(configuracionCompras);
        sucesoDao.insertar(sisSuceso);
        return configuracionCompras;
    }

    @Override
    public boolean eliminarSisConfiguracionCompras(SisConfiguracionComprasPK pk, SisInfoTO sisInfoTO) throws GeneralException {
        SisConfiguracionCompras conf = configuracionComprasDao.obtener(SisConfiguracionCompras.class, pk);
        if (conf != null) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = conf.getSisConfiguracionComprasPK().getConfUsuarioResponsable();
            susDetalle = "Se eliminó la configuración de compras " + susClave;
            susSuceso = "DELETE";
            susTabla = "sistemaweb.sis_configuracion_compras";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            //ELIMINAR
            configuracionComprasDao.eliminar(conf);
            sucesoDao.insertar(sisSuceso);
            return true;
        } else {
            throw new GeneralException("La configuración de compra que va a eliminar ya no existe. Intente con otro.", "La configuración de compra no existe");
        }
    }

    @Override
    public SisConfiguracionCompras getSisConfiguracionCompras(SisConfiguracionComprasPK pk) throws Exception {
        SisConfiguracionCompras conf = configuracionComprasDao.obtener(SisConfiguracionCompras.class, pk);
        return conf;
    }

}
