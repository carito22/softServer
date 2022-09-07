package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.BodegaDao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.inventario.dao.ConsumosMotivoDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.SectorDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesInventario;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsJSON;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosMotivoComboTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvConsumosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaBodegasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvListaConsumosMotivoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvConsumosMotivo;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListaSectorTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.*;

@Service
public class ConsumosMotivoServiceImpl implements ConsumosMotivoService {

    @Autowired
    private ConsumosMotivoDao consumosMotivoDao;

    private boolean comprobar = false;
    private String mensaje;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    @Autowired
    private BodegaDao bodegaDao;
    @Autowired
    private SectorDao sectorDao;

    @Override
    public List<InvConsumosMotivoComboTO> getListaConsumosMotivoComboTO(String empresa, boolean filtrarInactivos)
            throws Exception {
        return consumosMotivoDao.getListaConsumosMotivoComboTO(empresa, filtrarInactivos);
    }

    @Override
    public List<InvConsumosMotivo> getListaConsumosMotivo(String empresa, boolean filtrarInactivos) throws Exception {
        return consumosMotivoDao.getListaConsumosMotivo(empresa, filtrarInactivos);
    }

    @Override
    public List<InvListaConsumosMotivoTO> getInvListaConsumosMotivoTO(String empresa) throws Exception {
        return consumosMotivoDao.getInvListaConsumosMotivoTO(empresa);
    }

    @Override
    public InvConsumosMotivoTO getInvConsumoMotivoTO(String empresa, String cmCodigo) throws Exception {
        return consumosMotivoDao.getInvConsumoMotivoTO(empresa, cmCodigo);
    }

    @Override
    public boolean tieneMovimientosConsumosMotivo(String empresa, String motivo) throws Exception {
        return consumosMotivoDao.retornoContadoEliminarConsumosMotivo(empresa, motivo);
    }

    @Override
    public String accionInvConsumosMotivo(InvConsumosMotivoTO invConsumosMotivoTO, char accion, SisInfoTO sisInfoTO) throws Exception {
        ///// CREANDO Suceso
        susClave = invConsumosMotivoTO.getCmDetalle();
        if (accion == 'I') {
            susDetalle = "El motivo de consumo: Detalle " + invConsumosMotivoTO.getCmDetalle() + ", se ha guardado correctamente";
            susSuceso = "INSERT";
        }
        if (accion == 'M') {
            susDetalle = "El motivo de consumo: Detalle " + invConsumosMotivoTO.getCmDetalle() + ", se ha modificado correctamente";
            susSuceso = "UPDATE";
        }
        if (accion == 'E') {
            susDetalle = "El motivo de consumo: Detalle " + invConsumosMotivoTO.getCmDetalle() + ", se ha eliminado correctamente";
            susSuceso = "DELETE";
        }
        susTabla = "inventario.inv_consumos_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        ///// CREANDO CarPagosForma
        InvConsumosMotivo invConsumosMotivo = ConversionesInventario
                .convertirInvConsumosMotivoTO_InvConsumosMotivo(invConsumosMotivoTO);
        comprobar = false;
        InvConsumosMotivo invConsumosMotivoAux = null;

        if (accion == 'E') {
            ////// BUSCANDO existencia PagosForma
            boolean seguir = consumosMotivoDao.retornoContadoEliminarConsumosMotivo(
                    invConsumosMotivoTO.getCmEmpresa(), invConsumosMotivoTO.getCmCodigo());

            if (seguir) {
                if (consumosMotivoDao.comprobarInvConsumosMotivo(invConsumosMotivoTO.getUsrEmpresa(),
                        invConsumosMotivoTO.getCmCodigo())) {
                    invConsumosMotivo.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                    comprobar = consumosMotivoDao.accionInvConsumosMotivo(invConsumosMotivo, sisSuceso, accion);
                } else {
                    mensaje = "FNo se encuentra el ConsumosMotivo...";
                }
            } else {
                mensaje = "FNo se puede eliminar un motivo con movimientos";
            }
        } else {
            if (accion == 'I') {
                if (!consumosMotivoDao.comprobarInvConsumosMotivo(invConsumosMotivoTO.getUsrEmpresa(),
                        invConsumosMotivoTO.getCmCodigo())) {
                    invConsumosMotivo.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                    comprobar = consumosMotivoDao.accionInvConsumosMotivo(invConsumosMotivo, sisSuceso, accion);
                } else {
                    mensaje = "FSe encuentra creado el motivo de consumo...";
                }
            }
            if (accion == 'M') {
                if (consumosMotivoDao.comprobarInvConsumosMotivo(invConsumosMotivoTO.getUsrEmpresa(),
                        invConsumosMotivoTO.getCmCodigo())) {
                    invConsumosMotivoAux = consumosMotivoDao.buscarInvConsumosMotivo(
                            invConsumosMotivoTO.getUsrEmpresa(), invConsumosMotivoTO.getCmCodigo());
                    invConsumosMotivo.setUsrFechaInserta(invConsumosMotivoAux.getUsrFechaInserta());
                    comprobar = consumosMotivoDao.accionInvConsumosMotivo(invConsumosMotivo, sisSuceso, accion);
                } else {
                    mensaje = "FNo se encuentra creado el motivo de consumo...";
                }
            }
        }
        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TEl motivo de consumo: Código " + invConsumosMotivoTO.getCmCodigo() + ", se ha eliminado correctamente.";
            }
            if (accion == 'M') {
                mensaje = "TEl motivo de consumo: Código " + invConsumosMotivoTO.getCmCodigo() + ", se ha modificado correctamente.";
            }
            if (accion == 'I') {
                mensaje = "TEl motivo de consumo: Código " + invConsumosMotivoTO.getCmCodigo() + ", se ha guardado correctamente.";
            }
        }
        return mensaje;
    }

    @Override
    public String modificarEstadoInvConsumosMotivoTO(InvConsumosMotivoTO invConsumosMotivoTO, SisInfoTO sisInfoTO) throws Exception {
        susClave = invConsumosMotivoTO.getCmDetalle();
        susDetalle = "El motivo de consumo: Detalle " + invConsumosMotivoTO.getCmDetalle() + (invConsumosMotivoTO.getCmInactivo() ? ", se ha inactivado correctamente" : ", se ha activado correctamente");
        susSuceso = "UPDATE";
        susTabla = "inventario.inv_consumos_motivo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        ///// CREANDO CarPagosForma
        InvConsumosMotivo invConsumosMotivo = ConversionesInventario.convertirInvConsumosMotivoTO_InvConsumosMotivo(invConsumosMotivoTO);
        comprobar = false;
        InvConsumosMotivo invConsumosMotivoAux = null;
        if (consumosMotivoDao.comprobarInvConsumosMotivo(invConsumosMotivoTO.getUsrEmpresa(),
                invConsumosMotivoTO.getCmCodigo())) {
            invConsumosMotivoAux = consumosMotivoDao.buscarInvConsumosMotivo(
                    invConsumosMotivoTO.getUsrEmpresa(), invConsumosMotivoTO.getCmCodigo());
            invConsumosMotivo.setUsrFechaInserta(invConsumosMotivoAux.getUsrFechaInserta());
            comprobar = consumosMotivoDao.accionInvConsumosMotivo(invConsumosMotivo, sisSuceso, 'M');
        } else {
            mensaje = "FNo se encuentra creado el motivo de consumo...";
        }
        if (comprobar) {
            mensaje = "TEl motivo de consumo: Código " + invConsumosMotivoTO.getCmCodigo() + (invConsumosMotivoTO.getCmInactivo() ? ", se ha inactivado correctamente" : ", se ha activado correctamente");
        }
        return mensaje;
    }

    /*Lista InvConsumosMotivoTO*/
    @Override
    public List<InvConsumosMotivoTO> getInvListaConsumoMotivoTO(String empresa, boolean filtrarInactivos, String busqueda) throws Exception {
        return consumosMotivoDao.getInvListaConsumoMotivoTO(empresa, filtrarInactivos, busqueda);

    }

    @Override
    public Map<String, Object> obtenerDatosParaMotivoConsumos(Map<String, Object> map) throws Exception {

        Map<String, Object> campos = new HashMap<>();
        String empresa = UtilsJSON.jsonToObjeto(String.class, map.get("empresa"));
        boolean inactivo = UtilsJSON.jsonToObjeto(boolean.class, map.get("inactivo"));
        boolean activo = UtilsJSON.jsonToObjeto(boolean.class, map.get("activo"));

        List<InvListaBodegasTO> listaBodega = bodegaDao.buscarBodegasTO(empresa, inactivo, null);
        List<PrdListaSectorTO> listaSector = sectorDao.getListaSector(empresa, activo);

        campos.put("listaBodega", listaBodega);
        campos.put("listaSector", listaSector);

        return campos;
    }
}
