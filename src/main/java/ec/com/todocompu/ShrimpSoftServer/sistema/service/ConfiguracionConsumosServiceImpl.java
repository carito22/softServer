/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import ec.com.todocompu.ShrimpSoftServer.criteria.GenericoDao;
import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.BodegaService;
import ec.com.todocompu.ShrimpSoftServer.inventario.service.ConsumosMotivoService;
import ec.com.todocompu.ShrimpSoftServer.produccion.service.SectorService;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.ConfiguracionConsumosDao;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.SucesoDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaBodegasTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisListaUsuarioTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisConfiguracionConsumos;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisConfiguracionConsumosPK;
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
public class ConfiguracionConsumosServiceImpl implements ConfiguracionConsumosService {

    @Autowired
    private GenericoDao<SisConfiguracionConsumos, SisConfiguracionConsumosPK> configuracionConsumosDao;
    @Autowired
    private ConfiguracionConsumosDao configuracionConsumosOldDao;
    @Autowired
    private SectorService sectorService;
    @Autowired
    private BodegaService bodegaService;
    @Autowired
    private ConsumosMotivoService consumosMotivoService;
    @Autowired
    private UsuarioDetalleService usuarioDetalleService;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private SucesoDao sucesoDao;

    @Override
    public List<SisConfiguracionConsumos> listarSisConfiguracionConsumos(String empresa) throws Exception {
        return configuracionConsumosOldDao.listarSisConfiguracionConsumos(empresa);
    }

    @Override
    public Map<String, Object> obtenerDatosParaSisConfiguracionConsumos(String empresa, String sector, SisInfoTO sisInfoTO) throws Exception {
        Map<String, Object> campos = new HashMap<>();
        List<SisListaUsuarioTO> listaSisListaUsuarioTO = usuarioDetalleService.getListaSisUsuarios(empresa);
        List<InvListaBodegasTO> listaInvListaBodegasTO = bodegaService.buscarBodegasPorSector(empresa, false, sector);
        List<PrdListaSectorTO> listPrdListaSectorTO = sectorService.getListaSectorTO(empresa, false);
        List<InvConsumosMotivoTO> listaInvConsumosMotivoTO = consumosMotivoService.getInvListaConsumoMotivoTO(empresa, false, null);
        campos.put("listaSisListaUsuarioTO", listaSisListaUsuarioTO);
        campos.put("listaInvListaBodegasTO", listaInvListaBodegasTO);
        campos.put("listaPrdListaSectorTO", listPrdListaSectorTO);
        campos.put("listaInvConsumosMotivoTO", listaInvConsumosMotivoTO);
        return campos;
    }

    @Override
    public SisConfiguracionConsumos insertarSisConfiguracionConsumos(SisConfiguracionConsumos configuracionConsumos, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        if (!configuracionConsumosDao.existe(SisConfiguracionConsumos.class, configuracionConsumos.getSisConfiguracionConsumosPK())) {
            //PREAPARANDO SISUSCESO
            susClave = configuracionConsumos.getSisConfiguracionConsumosPK().getConfUsuarioResponsable();
            susDetalle = "Se insertó la configuración de consumos con código " + susClave;
            susSuceso = "INSERT";
            susTabla = "sistemaweb.sis_configuracion_consumos";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            //FECHA Y CONVERSION DE TO

            configuracionConsumos.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));

            configuracionConsumos = configuracionConsumosDao.insertar(configuracionConsumos);
            sucesoDao.insertar(sisSuceso);
            return configuracionConsumos;
        } else {
            return null;
        }
    }

    @Override
    public SisConfiguracionConsumos modificarSisConfiguracionConsumos(SisConfiguracionConsumos configuracionConsumos, SisInfoTO sisInfoTO) throws GeneralException, Exception {
        //validar que nadie realice la misma accion, p.e que intenten aprobar a la misma vez.
        //PREAPARANDO SISUSCESO
        susClave = configuracionConsumos.getSisConfiguracionConsumosPK().getConfUsuarioResponsable();
        susDetalle = "Se modificó la orden de pedido con código " + susClave;
        susSuceso = "UPDATE";
        susTabla = "sistemaweb.sis_configuracion_consumos";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        //FECHA Y CONVERSION DE TO
        configuracionConsumos = configuracionConsumosDao.actualizar(configuracionConsumos);
        sucesoDao.insertar(sisSuceso);
        return configuracionConsumos;
    }

    @Override
    public boolean eliminarSisConfiguracionConsumos(SisConfiguracionConsumosPK pk, SisInfoTO sisInfoTO) throws GeneralException {
        SisConfiguracionConsumos conf = configuracionConsumosDao.obtener(SisConfiguracionConsumos.class, pk);
        if (conf != null) {
            /// PREPARANDO OBJETO SISSUCESO
            susClave = conf.getSisConfiguracionConsumosPK().getConfUsuarioResponsable();
            susDetalle = "Se eliminó la configuración de consumos " + susClave;
            susSuceso = "DELETE";
            susTabla = "sistemaweb.sis_configuracion_consumos";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            //ELIMINAR
            configuracionConsumosDao.eliminar(conf);
            sucesoDao.insertar(sisSuceso);
            return true;
        } else {
            throw new GeneralException("La configuración de consumo que va a eliminar ya no existe. Intente con otro.", "La configuración de consumo no existe");
        }
    }

    @Override
    public SisConfiguracionConsumos getSisConfiguracionConsumos(SisConfiguracionConsumosPK pk) throws Exception {
        SisConfiguracionConsumos conf = configuracionConsumosDao.obtener(SisConfiguracionConsumos.class, pk);
        return conf;
    }

}
