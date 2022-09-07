package ec.com.todocompu.ShrimpSoftServer.caja.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.caja.dao.ValesConceptosDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesCaja;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajValesConceptoTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.TO.CajValesConceptosComboTO;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajValesConceptos;
import ec.com.todocompu.ShrimpSoftUtils.caja.entity.CajValesConceptosPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class ValesConceptosServiceImpl implements ValesConceptosService {

    @Autowired
    private ValesConceptosDao valesConceptosDao;

    private boolean comprobar = false;
    private String mensaje = "";
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public List<CajValesConceptosComboTO> getCajValesConceptosComboTOs(String empresa) throws Exception {
        return valesConceptosDao.getCajValesConceptosComboTOs(empresa);
    }

    @Override
    public String accionCajValesConceptosTO(CajValesConceptoTO cajValesConceptosTO, char accion, SisInfoTO sisInfoTO) throws Exception {
        comprobar = false;
        ///// CREANDO Suceso
        susClave = cajValesConceptosTO.getConcCodigo();

        if (accion == 'I') {
            susDetalle = "Se insertó Concepto vales de Caja  : " + cajValesConceptosTO.getConcDetalle();
            susSuceso = "INSERT";
        }
        if (accion == 'M') {
            susDetalle = "Se modificó Concepto vales de Caja : " + cajValesConceptosTO.getConcDetalle();
            susSuceso = "UPDATE";
        }
        if (accion == 'E') {
            susDetalle = "Se eliminó Concepto vales de Caja  : " + cajValesConceptosTO.getConcDetalle();
            susSuceso = "DELETE";
        }
        susTabla = "caja.caj_vales_conceptos";
        ///// CREANDO invProductoMedida
        cajValesConceptosTO.setUsrFechaInserta(UtilsValidacion.fechaSistema());
        CajValesConceptos cajValesConceptos = ConversionesCaja.convertirCajValesConceptosTO_cajValesConceptos(cajValesConceptosTO);
        if (accion == 'I') {
            ////// BUSCANDO existencia Categoría
            if (!valesConceptosDao.comprobarCajValesConceptos(cajValesConceptosTO.getConcEmpresa(),
                    cajValesConceptosTO.getConcCodigo())) {
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                cajValesConceptos.setUsrFechaInserta(UtilsValidacion.fechaString_Date(UtilsValidacion.fechaSistema()));
                comprobar = valesConceptosDao.accionCajValesConceptos(cajValesConceptos, sisSuceso, accion);
            } else {
                mensaje = "FYa existe el Concepto de Vale de caja...";
            }
        }
        if (accion == 'M') {
            ////// BUSCANDO existencia Categoría
            if (valesConceptosDao.comprobarCajValesConceptos(cajValesConceptosTO.getConcEmpresa(), cajValesConceptosTO.getConcCodigo())) {
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                cajValesConceptos.setUsrFechaInserta(valesConceptosDao.obtenerPorId(CajValesConceptos.class, new CajValesConceptosPK(
                        cajValesConceptosTO.getConcEmpresa(), cajValesConceptosTO.getConcCodigo())).getUsrFechaInserta());
                comprobar = valesConceptosDao.accionCajValesConceptos(cajValesConceptos, sisSuceso, accion);
            } else {
                mensaje = "FNo se encuentra el Datos...";
            }
        }
        if (accion == 'E') {
            ////// BUSCANDO existencia Categoría
            if (valesConceptosDao.comprobarCajValesConceptos(cajValesConceptosTO.getConcEmpresa(),
                    cajValesConceptosTO.getConcCodigo())) {
                SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
                cajValesConceptos.setUsrFechaInserta(valesConceptosDao
                        .obtenerPorId(CajValesConceptos.class, new CajValesConceptosPK(
                                cajValesConceptosTO.getConcEmpresa(), cajValesConceptosTO.getConcCodigo()))
                        .getUsrFechaInserta());
                comprobar = valesConceptosDao.accionCajValesConceptos(cajValesConceptos, sisSuceso, accion);
            } else {
                mensaje = "FNo se encuentra el Datos...";
            }
        }
        if (comprobar) {
            if (accion == 'E') {
                mensaje = "TSe eliminó correctamente el Concepto de Vale de caja";
            }
            if (accion == 'M') {
                mensaje = "TSe modificó correctamente el Concepto de Vale de caja";
            }
            if (accion == 'I') {
                mensaje = "TSe ingresó correctamente  el Concepto de Vale de caja";
            }
        }
        return mensaje;
    }
}
