package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.SectorDao;
import ec.com.todocompu.ShrimpSoftServer.produccion.dao.VehiculosDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesProduccion;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdVehiculosTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscina;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPiscinaPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSector;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdSectorPK;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdVehiculos;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdVehiculosPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class VehiculosServiceImpl implements VehiculosService {

    @Autowired
    private SectorDao sectorDao;
    @Autowired
    private VehiculosDao vehiculosDao;

    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;
    private Boolean comprobar = false;

    @Override
    public String accionPrdVehiculos(PrdVehiculosTO prdVehiculosTO, char accion, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        String mensaje = "";
        PrdVehiculos prdVehiculos = null;
        ///// BUSCANDO existencia piscina
        PrdSector prdSector = sectorDao.obtenerPorId(PrdSector.class,
                new PrdSectorPK(sisInfoTO.getEmpresa(), prdVehiculosTO.getVehCentroProduccion()));
        if (prdSector != null) {// revisar si estan llenos
            ///// CREANDO Suceso
            susClave = prdVehiculosTO.getVehPlaca();
            if (accion == 'I') {
                susDetalle = "Se insertó Vehiculo Nº " + prdVehiculosTO.getVehPlaca();
                susSuceso = "INSERT";
            }
            if (accion == 'M') {
                susDetalle = "Se modificó Vehiculo Nº " + prdVehiculosTO.getVehPlaca();
                susSuceso = "UPDATE";
            }
            if (accion == 'E') {
                susDetalle = "Se eliminó Vehiculo Nº " + prdVehiculosTO.getVehPlaca();
                susSuceso = "DELETE";
            }
            susTabla = "produccion.prd_vehiculos";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            ///// CREANDO PrdPiscina
            PrdPiscina prdPiscina = new PrdPiscina();
            prdPiscina.setPrdPiscinaPK(new PrdPiscinaPK(sisInfoTO.getEmpresa(),
                    prdVehiculosTO.getVehCentroProduccion(), prdVehiculosTO.getVehPlaca()));
            prdPiscina.setPrdSector(prdSector);
            prdPiscina.setUsrEmpresa(sisInfoTO.getEmpresa());
            prdPiscina.setUsrCodigo(sisInfoTO.getUsuario());
            prdPiscina.setUsrFechaInserta(
                    UtilsDate.timestamp(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema())));
            ///// CREANDO PrdVehiculos
            prdVehiculos = ConversionesProduccion.convertirPrdVehiculosTO_PrdVehiculos(prdVehiculosTO);

            prdVehiculos.setPrdPiscina(prdPiscina);

            if (accion == 'E') {
                ////// BUSCANDO existencia Vehiculos
                if (vehiculosDao.obtenerPorId(PrdVehiculos.class, new PrdVehiculosPK(sisInfoTO.getEmpresa(),
                        prdVehiculosTO.getVehEstablecimiento(), prdVehiculosTO.getVehPlaca())) != null) {
                    comprobar = vehiculosDao.accionPrdVehiculos(prdVehiculos, prdPiscina, sisSuceso, accion);
                } else {
                    mensaje = "FNo se encuentra la Vehiculos...";
                }
            }
            if (accion == 'M') {
                ////// BUSCANDO existencia Vehiculos
                if (vehiculosDao.obtenerPorId(PrdVehiculos.class, new PrdVehiculosPK(sisInfoTO.getEmpresa(),
                        prdVehiculosTO.getVehCentroProduccion(), prdVehiculosTO.getVehPlaca())) != null) {
                    comprobar = vehiculosDao.accionPrdVehiculos(prdVehiculos, prdPiscina, sisSuceso, accion);
                } else {
                    mensaje = "FNo se encuentra la compra Vehiculos...";
                }
            }
            if (accion == 'I') {
                ////// BUSCANDO existencia Vehiculos
                if (vehiculosDao.obtenerPorId(PrdVehiculos.class, new PrdVehiculosPK(sisInfoTO.getEmpresa(),
                        prdVehiculosTO.getVehCentroProduccion(), prdVehiculosTO.getVehPlaca())) == null) {
                    comprobar = vehiculosDao.accionPrdVehiculos(prdVehiculos, prdPiscina, sisSuceso, accion);
                } else {
                    mensaje = "FYa existe la Vehiculos...";
                }
            }
        }
        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TSe eliminó correctamente el Vehículo";
            }
            if (accion == 'M') {
                mensaje = "TSe modificó correctamente el Vehículo";
            }
            if (accion == 'I') {
                mensaje = "TSe ingresó correctamente el Vehículo";
            }
        }
        return mensaje;
    }
}
