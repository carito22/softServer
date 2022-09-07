package ec.com.todocompu.ShrimpSoftServer.anexos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.anexos.dao.CuentasContablesDao;
import ec.com.todocompu.ShrimpSoftServer.contabilidad.service.CuentasService;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesAnexos;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCuentasContablesObjetosTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxCuentasContablesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxCuentascontables;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class CuentasContablesServiceImpl implements CuentasContablesService {

    @Autowired
    private CuentasContablesDao cuentasContablesDao;

    private String susClave;
    private String susDetalle;
    private String susSuceso;
    private String susTabla;
    @Autowired
    private CuentasService cuentasService;

    @Override
    public AnxCuentasContablesTO getAnxCuentasContablesTO(String empresa, String nombreCuenta) throws Exception {
        return cuentasContablesDao.getAnxCuentasContablesTO(empresa, nombreCuenta);
    }

    @Override
    public AnxCuentasContablesObjetosTO getAnxCuentasContablesObjetosTO(String empresa, String nombreCuenta) throws Exception {
        AnxCuentasContablesTO anxCuentasContablesTO = cuentasContablesDao.getAnxCuentasContablesTO(empresa, nombreCuenta);
        AnxCuentasContablesObjetosTO anxCuentasContablesObjetosTO = null;

        if (anxCuentasContablesTO != null) {
            anxCuentasContablesObjetosTO = new AnxCuentasContablesObjetosTO();
            if (anxCuentasContablesTO.getCtaIvaPagado() != null) {
                anxCuentasContablesObjetosTO.setCtaIvaPagado(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaIvaPagado()));
            }
            if (anxCuentasContablesTO.getCtaIvaPagadoActivoFijo() != null) {
                anxCuentasContablesObjetosTO.setCtaIvaPagadoActivoFijo(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaIvaPagadoActivoFijo()));
            }
            if (anxCuentasContablesTO.getCtaIvaPagadoGasto() != null) {
                anxCuentasContablesObjetosTO.setCtaIvaPagadoGasto(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaIvaPagadoGasto()));
            }
            if (anxCuentasContablesTO.getCtaIvaPagado() != null) {
                anxCuentasContablesObjetosTO.setCtaIvaCobrado(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaIvaCobrado()));
            }
            if (anxCuentasContablesTO.getCtaIvaPagado() != null) {
                anxCuentasContablesObjetosTO.setCtaRetFteIvaPagado(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaRetFteIvaPagado()));
            }
            if (anxCuentasContablesTO.getCtaIvaPagado() != null) {
                anxCuentasContablesObjetosTO.setCtaRetFteIvaCobrado(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaRetFteIvaCobrado()));
            }
            if (anxCuentasContablesTO.getCtaIvaPagado() != null) {
                anxCuentasContablesObjetosTO.setCtaRetFteIvaAsumido(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaRetFteIvaAsumido()));
            }
            if (anxCuentasContablesTO.getCtaIvaPagado() != null) {
                anxCuentasContablesObjetosTO.setCtaRetFteIrPagado(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaRetFteIrPagado()));
            }
            if (anxCuentasContablesTO.getCtaIvaPagado() != null) {
                anxCuentasContablesObjetosTO.setCtaRetFteIrCobrado(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaRetFteIrCobrado()));
            }
            if (anxCuentasContablesTO.getCtaIvaPagado() != null) {
                anxCuentasContablesObjetosTO.setCtaRetFteIrAsumido(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaRetFteIrAsumido()));
            }
            if (anxCuentasContablesTO.getCtaIvaPagado() != null) {
                anxCuentasContablesObjetosTO.setCtaOtrosImpuestos(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaOtrosImpuestos()));
            }
            if (anxCuentasContablesTO.getCtaIvaPagado() != null) {
                anxCuentasContablesObjetosTO.setCtaCuentasPorCobrar(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaCuentasPorCobrar()));
            }
            if (anxCuentasContablesTO.getCtaIvaPagado() != null) {
                anxCuentasContablesObjetosTO.setCtaCuentasPorPagar(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaCuentasPorPagar()));
            }
            if (anxCuentasContablesTO.getCtaIvaPagado() != null) {
                anxCuentasContablesObjetosTO.setCtaCuentasPorPagarActivoFijo(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaCuentasPorPagarActivoFijo()));
            }
            if (anxCuentasContablesTO.getCtaIvaPagado() != null) {
                anxCuentasContablesObjetosTO.setCtaAnticiposDeClientes(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaAnticiposDeClientes()));
            }
            if (anxCuentasContablesTO.getCtaIvaPagado() != null) {
                anxCuentasContablesObjetosTO.setCtaAnticiposAProveedores(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaAnticiposAProveedores()));
            }
            if (anxCuentasContablesTO.getCtaIvaPagado() != null) {
                anxCuentasContablesObjetosTO.setCtaInventarioProductosProceso(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaInventarioProductosProceso()));
            }
            if (anxCuentasContablesTO.getCtaIvaPagado() != null) {
                anxCuentasContablesObjetosTO.setCtaCostoProductosProceso(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaCostoProductosProceso()));
            }
            if (anxCuentasContablesTO.getCtaIvaPagado() != null) {
                anxCuentasContablesObjetosTO.setCtaUtilidadEjercicio(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaUtilidadEjercicio()));
            }
            if (anxCuentasContablesTO.getCtaPropina() != null) {
                anxCuentasContablesObjetosTO.setCtaPropina(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaPropina()));
            }

            if (anxCuentasContablesTO.getCtaCuentasMercaderiaTransito() != null) {
                anxCuentasContablesObjetosTO.setCtaCuentasMercaderiaTransito(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaCuentasMercaderiaTransito()));
            }
            if(anxCuentasContablesTO.getCtaCostoInicialProductosProceso() != null){
                anxCuentasContablesObjetosTO.setCtaCostoInicialProductosProceso(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaCostoInicialProductosProceso()));
            }
            if(anxCuentasContablesTO.getCtaCostoFinalProductosProceso() != null){
                anxCuentasContablesObjetosTO.setCtaCostoFinalProductosProceso(cuentasService.obtenerConCuentaTO(empresa, anxCuentasContablesTO.getCtaCostoFinalProductosProceso()));
            }

        }

        return anxCuentasContablesObjetosTO;
    }

    @Override
    public String actualizarAnxCuentasContables(AnxCuentasContablesTO anxCuentasContablesTO, String empresa,
            String usuario, SisInfoTO sisInfoTO) throws Exception {
        boolean comprobar = false;
        String mensaje = "";
        // /// CREANDO Suceso
        susClave = anxCuentasContablesTO.getCtaEmpresa();
        susDetalle = "Se modificó la(s) cuenta(s) contable(s)";
        susTabla = "anexos.anx_cuentascontables";
        susSuceso = "UPDATE";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        // /// CREANDO anxCuentasContables
        AnxCuentasContablesTO auxAnxCuentasContablesTO = getAnxCuentasContablesTO(anxCuentasContablesTO.getCtaEmpresa(), null);
        anxCuentasContablesTO.setCtaSecuencial(auxAnxCuentasContablesTO.getCtaSecuencial());

        AnxCuentascontables anxCuentascontables = ConversionesAnexos.convertirAnxCuentasContablesTO_AnxCuentasContables(anxCuentasContablesTO);
        // //// BUSCANDO existencia Categoría
        if (cuentasContablesDao.comprobarAnxCuentascontables(empresa)) {
            comprobar = cuentasContablesDao.actualizarAnxCuentascontables(anxCuentascontables, sisSuceso);
        } else {
            mensaje = "FNo se encuentra...";
        }

        if (comprobar) {
            mensaje = "TSe modificó correctamente";
        }
        return mensaje;
    }

}
