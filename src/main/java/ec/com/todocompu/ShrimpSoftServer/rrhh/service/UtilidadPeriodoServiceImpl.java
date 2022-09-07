package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.UtilidadesPeriodoDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesRRHH;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhUtilidadesPeriodoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhUtilidadesPeriodo;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class UtilidadPeriodoServiceImpl implements UtilidadPeriodoService {

    @Autowired
    private UtilidadesPeriodoDao utilidadesPeriodoDao;

    private Boolean comprobar = false;
    private String mensaje = "";
    private BigDecimal cero = new BigDecimal("0.00");
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<RhUtilidadesPeriodoTO> getRhComboUtilidadesPeriodoTO(String empresa) throws Exception {
        return utilidadesPeriodoDao.getRhComboUtilidadesPeriodoTO(empresa);
    }

    @Override
    public String accionRhUtilidadesPeriodo(RhUtilidadesPeriodoTO rhUtilidadesPeriodoTO, char accion, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        susClave = rhUtilidadesPeriodoTO.getUtiEmpresa();
        if (accion == 'I') {
            susDetalle = "El periodo de utilidad: Detalle " + rhUtilidadesPeriodoTO.getUtiDescripcion() + ", se ha guardado correctamente";
            susSuceso = "INSERT";
        }
        if (accion == 'M') {
            susDetalle = "El periodo de utilidad: Detalle " + rhUtilidadesPeriodoTO.getUtiDescripcion() + ", se ha modificado correctamente";
            susSuceso = "UPDATE";
        }
        if (accion == 'E') {
            susDetalle = "El periodo de utilidad: Detalle " + rhUtilidadesPeriodoTO.getUtiDescripcion() + ", se ha eliminado correctamente";
            susSuceso = "DELETE";
        }
        susTabla = "recursoshumanos.rh_utilidades_periodo";
        sisInfoTO.setEmpresa(rhUtilidadesPeriodoTO.getUtiEmpresa());
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        RhUtilidadesPeriodo rhUtilidadesPeriodo = ConversionesRRHH.convertirRhUtilidadesPeriodoTO_RhUtilidadesPeriodo(rhUtilidadesPeriodoTO);
        if (accion == 'E') {
            if (utilidadesPeriodoDao.comprobarRhUtilidadesPeriodo(rhUtilidadesPeriodoTO.getUtiEmpresa(), rhUtilidadesPeriodoTO.getUtiDescripcion())) {
                comprobar = utilidadesPeriodoDao.accionRhUtilidadesPeriodo(rhUtilidadesPeriodo, sisSuceso, accion);
            } else {
                mensaje = "FNo se encuentra el Marca Periodo...";
            }
        }
        if (accion == 'M') {
            if (utilidadesPeriodoDao.comprobarRhUtilidadesPeriodo(rhUtilidadesPeriodoTO.getUtiEmpresa(), rhUtilidadesPeriodoTO.getUtiDescripcion())) {
                comprobar = utilidadesPeriodoDao.accionRhUtilidadesPeriodo(rhUtilidadesPeriodo, sisSuceso, accion);
            } else {
                mensaje = "FNo se encuentra el Periodo...";
            }
        }
        if (accion == 'I') {
            if (!utilidadesPeriodoDao.comprobarRhUtilidadesPeriodo(rhUtilidadesPeriodoTO.getUtiEmpresa(), rhUtilidadesPeriodoTO.getUtiDescripcion())) {
                comprobar = utilidadesPeriodoDao.accionRhUtilidadesPeriodo(rhUtilidadesPeriodo, sisSuceso, accion);
            } else {
                mensaje = "FYa existe el Marca Periodo...";
            }
        }
        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TEl periodo de utilidad: Detalle " + rhUtilidadesPeriodoTO.getUtiDescripcion() + ", se ha eliminado correctamente.";
            }
            if (accion == 'M') {
                mensaje = "TEl periodo de utilidad: Detalle " + rhUtilidadesPeriodoTO.getUtiDescripcion() + ", se ha modificado correctamente.";

            }
            if (accion == 'I') {
                mensaje = "TEl periodo de utilidad: Detalle " + rhUtilidadesPeriodoTO.getUtiDescripcion() + ", se ha guardado correctamente.";
            }
        }
        return mensaje;
    }

    @Override
    public String modificarEstadoUtilidadesPeriodo(RhUtilidadesPeriodoTO rhUtilidadesPeriodoTO, boolean estado, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        sisInfoTO.setEmpresa(rhUtilidadesPeriodoTO.getUtiEmpresa());
        susDetalle = "El periodo de utilidad: Detalle " + rhUtilidadesPeriodoTO.getUtiDescripcion() + (estado ? ", se ha inactivado correctamente" : ", se ha activado correctamente");
        susClave = rhUtilidadesPeriodoTO.getUtiDescripcion();
        susSuceso = "UPDATE";
        susTabla = "recursoshumanos.rh_utilidades_periodo";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        RhUtilidadesPeriodo rhUtilidadesPeriodo = ConversionesRRHH.convertirRhUtilidadesPeriodoTO_RhUtilidadesPeriodo(rhUtilidadesPeriodoTO);
        // rhUtilidadesPeriodoTO.set(estado);
        if (utilidadesPeriodoDao.comprobarRhUtilidadesPeriodo(rhUtilidadesPeriodoTO.getUtiEmpresa(), rhUtilidadesPeriodoTO.getUtiDescripcion())) {
            comprobar = utilidadesPeriodoDao.accionRhUtilidadesPeriodo(rhUtilidadesPeriodo, sisSuceso, 'M');
        } else {
            retorno = "Hubo un error al modificar el periodo de utilidad. Intente de nuevo o contacte con el administrador";
        }

        if (comprobar) {
            retorno = "TEl periodo de utilidad: Detalle " + rhUtilidadesPeriodoTO.getUtiDescripcion() + (estado ? ", se ha inactivado correctamente" : ", se ha activado correctamente");
        }
        
        return retorno;
    }

}
