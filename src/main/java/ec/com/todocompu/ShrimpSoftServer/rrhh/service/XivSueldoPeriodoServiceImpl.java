package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.XivSueldoPeriodoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesRRHH;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhXivSueldoPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhXivSueldoPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class XivSueldoPeriodoServiceImpl implements XivSueldoPeriodoService {

    @Autowired
    private XivSueldoPeriodoDao xivSueldoPeriodoDao;

    private Boolean comprobar = false;
    private String mensaje = "";
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<RhXivSueldoPeriodoTO> getRhComboXivSueldoPeriodoTO() throws Exception {
        return xivSueldoPeriodoDao.getRhComboXivSueldoPeriodoTO();
    }

    @Override
    public String accionRhXivSueldoPeriodo(RhXivSueldoPeriodoTO rhXivSueldoPeriodoTO, String codigoEmpresa, String codigoUsuario, char accion, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        ///// CREANDO Suceso
        susClave = rhXivSueldoPeriodoTO.getXivDescripcion();
        if (accion == 'I') {
            susDetalle = "El periodo de 14° sueldo: Detalle " + rhXivSueldoPeriodoTO.getXivDescripcion() + ", se ha guardado correctamente";
            susSuceso = "INSERT";
        }
        if (accion == 'M') {
            susDetalle = "El periodo de 14° sueldo: Detalle " + rhXivSueldoPeriodoTO.getXivDescripcion() + ", se ha modificado correctamente";
            susSuceso = "UPDATE";
        }
        if (accion == 'E') {
            susDetalle = "El periodo de 14° sueldo: Detalle " + rhXivSueldoPeriodoTO.getXivDescripcion() + ", se ha eliminado correctamente";
            susSuceso = "DELETE";
        }
        susTabla = "recursoshumanos.rh_xiv_sueldo_periodo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        ///// CREANDO invProductoMedida
        RhXivSueldoPeriodo RhXivSueldoPeriodo = ConversionesRRHH.convertirRhXivSueldoPeriodoTO_RhXivSueldoPeriodo(rhXivSueldoPeriodoTO);
        if (accion == 'E') {
            ////// BUSCANDO existencia Categoría
            if (xivSueldoPeriodoDao.buscarRhXivSueldoPeriodo(rhXivSueldoPeriodoTO.getPeriodoSecuencial()) != null) {
                comprobar = xivSueldoPeriodoDao.accionRhXivSueldoPeriodo(RhXivSueldoPeriodo, sisSuceso, accion);
            } else {
                mensaje = "FNo se encuentra el periodo...";
            }
        }
        if (accion == 'M') {
            ////// BUSCANDO existencia Categoría
            if (xivSueldoPeriodoDao.buscarRhXivSueldoPeriodo(rhXivSueldoPeriodoTO.getPeriodoSecuencial()) != null) {
                comprobar = xivSueldoPeriodoDao.accionRhXivSueldoPeriodo(RhXivSueldoPeriodo, sisSuceso, accion);
            } else {
                mensaje = "FNo se encuentra el periodo...";
            }
        }
        if (accion == 'I') {
            ////// BUSCANDO existencia Categoría
            if (!xivSueldoPeriodoDao.comprobarRhXivSueldoPeriodoTO(RhXivSueldoPeriodo.getXivDescripcion())) {
                comprobar = xivSueldoPeriodoDao.accionRhXivSueldoPeriodo(RhXivSueldoPeriodo, sisSuceso, accion);
            } else {
                mensaje = "FYa existe el Periodo...";
            }
        }
        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TEl periodo de 14° sueldo: Código " + RhXivSueldoPeriodo.getXivSecuencial() + ", se ha eliminado correctamente.";
            }
            if (accion == 'M') {
                mensaje = "TEl periodo de 14° sueldo: Código " + RhXivSueldoPeriodo.getXivSecuencial() + ", se ha modificado correctamente.";
            }
            if (accion == 'I') {
                Integer secuencial = getRhXivSueldoPeriodoPorAtributo("xivDescripcion", rhXivSueldoPeriodoTO.getXivDescripcion()).getXivSecuencial();
                mensaje = "TEl periodo de 14° sueldo: Código " + secuencial + ", se ha guardado correctamente.";
            }
        }
        return mensaje;
    }

    @Override
    public RhXivSueldoPeriodo getRhXivSueldoPeriodoPorAtributo(String atributo, String valorAtributo) throws Exception {
        return xivSueldoPeriodoDao.obtenerPorAtributo(RhXivSueldoPeriodo.class, atributo, valorAtributo, null);
    }

}
