package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.XiiiSueldoPeriodoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesRRHH;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXiiiSueldoPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXiiiSueldoPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class XiiiSueldoPeriodoServiceImpl implements XiiiSueldoPeriodoService {

    @Autowired
    private XiiiSueldoPeriodoDao xiiiSueldoPeriodoDao;

    private Boolean comprobar = false;
    private String mensaje = "";
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<RhXiiiSueldoPeriodoTO> getRhComboXiiiSueldoPeriodoTO() throws Exception {
        return xiiiSueldoPeriodoDao.getRhComboXiiiSueldoPeriodoTO();
    }

    @Override
    public String accionRhXiiiSueldoPeriodo(RhXiiiSueldoPeriodoTO rhXiiiSueldoPeriodoTO, String codigoEmpresa, String codigoUsuario, char accion, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        ///// CREANDO Suceso
        susClave = rhXiiiSueldoPeriodoTO.getXiiiDescripcion();
        if (accion == 'I') {
            susDetalle = "El periodo de 13° sueldo: Detalle " + rhXiiiSueldoPeriodoTO.getXiiiDescripcion() + ", se ha guardado correctamente";
            susSuceso = "INSERT";
        }
        if (accion == 'M') {
            susDetalle = "El periodo de 13° sueldo: Detalle " + rhXiiiSueldoPeriodoTO.getXiiiDescripcion() + ", se ha modificado correctamente";
            susSuceso = "UPDATE";
        }
        if (accion == 'E') {
            susDetalle = "El periodo de 13° sueldo: Detalle " + rhXiiiSueldoPeriodoTO.getXiiiDescripcion() + ", se ha eliminado correctamente";
            susSuceso = "DELETE";
        }
        susTabla = "recursoshumanos.rh_xiii_sueldo_periodo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, new SisInfoTO());
        ///// CREANDO invProductoMedida
        RhXiiiSueldoPeriodo RhXiiiSueldoPeriodo = ConversionesRRHH.convertirRhXiiiSueldoPeriodoTO_RhXiiiSueldoPeriodo(rhXiiiSueldoPeriodoTO);
        if (accion == 'E') {
            ////// BUSCANDO existencia Categoría
            if (xiiiSueldoPeriodoDao.buscarRhXiiiSueldoPeriodo(rhXiiiSueldoPeriodoTO.getPeriodoSecuencial()) != null) {
                comprobar = xiiiSueldoPeriodoDao.accionRhXiiiSueldoPeriodo(RhXiiiSueldoPeriodo, sisSuceso, accion);
            } else {
                mensaje = "FNo se encuentra el periodo 13º sueldo...";
            }
        }
        if (accion == 'M') {
            ////// BUSCANDO existencia Categoría
            if (xiiiSueldoPeriodoDao.buscarRhXiiiSueldoPeriodo(rhXiiiSueldoPeriodoTO.getPeriodoSecuencial()) != null) {
                comprobar = xiiiSueldoPeriodoDao.accionRhXiiiSueldoPeriodo(RhXiiiSueldoPeriodo, sisSuceso, accion);
            } else {
                mensaje = "FNo se encuentra el periodo 13º sueldo...";
            }
        }
        if (accion == 'I') {
            ////// BUSCANDO existencia Categoría
            if (!xiiiSueldoPeriodoDao.comprobarRhXiiiSueldoPeriodoTO(RhXiiiSueldoPeriodo.getXiiiDescripcion())) {
                comprobar = xiiiSueldoPeriodoDao.accionRhXiiiSueldoPeriodo(RhXiiiSueldoPeriodo, sisSuceso, accion);
            } else {
                mensaje = "FYa existe el periodo 13º sueldo...";
            }
        }
        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TEl periodo de 13° sueldo: Código " + rhXiiiSueldoPeriodoTO.getPeriodoSecuencial() + ", se ha eliminado correctamente.";
            }
            if (accion == 'M') {
                mensaje = "TEl periodo de 13° sueldo: Código " + rhXiiiSueldoPeriodoTO.getPeriodoSecuencial() + ", se ha modificado correctamente.";
            }
            if (accion == 'I') {
                Integer secuencial = getRhXiiiSueldoPeriodoPorAtributo("xiiiDescripcion", rhXiiiSueldoPeriodoTO.getXiiiDescripcion()).getXiiiSecuencial();
                mensaje = "TEl periodo de 13° sueldo: Código " + secuencial + ", se ha guardado correctamente.";
            }
        }
        return mensaje;
    }

    @Override
    public RhXiiiSueldoPeriodo getRhXiiiSueldoPeriodoPorAtributo(String atributo, String valorAtributo) throws Exception {
        return xiiiSueldoPeriodoDao.obtenerPorAtributo(RhXiiiSueldoPeriodo.class, atributo, valorAtributo, null);
    }

}
