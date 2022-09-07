package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.produccion.dao.PresupuestoPescaDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.UtilsDate;
import ec.com.todocompu.ShrimpSoftUtils.UtilsValidacion;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPesca;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class PresupuestoPescaServiceImpl implements PresupuestoPescaService {

    @Autowired
    private PresupuestoPescaDao presupuestoPescaDao;

    private boolean comprobar = false;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    public MensajeTO insertarModificarPrdPresupuestoPesca(PrdPresupuestoPesca prdPresupuestoPesca,
            List<PrdPresupuestoPescaDetalle> listaPrdPresupuestoPescaDetalles, SisInfoTO sisInfoTO) throws Exception {
        String retorno = "";
        MensajeTO mensajeTO = new MensajeTO();

        if (UtilsValidacion.isFechaSuperior(prdPresupuestoPesca.getPresuFecha())) {
            retorno = "FLa fecha que ingreso es superior a la fecha actual del servidor. Fecha actual del servidor: "
                    + UtilsValidacion.fechaSistema("dd-MM-yyyy");
        } else {
            sisInfoTO.setEmpresa(prdPresupuestoPesca.getPrdPresupuestoPescaPK().getPresuEmpresa());
            prdPresupuestoPesca.setPresuAnulado(false);
            prdPresupuestoPesca.setPresuPendiente(false);
            if (prdPresupuestoPesca.getPrdPresupuestoPescaPK().getPresuNumero() == null || prdPresupuestoPesca
                    .getPrdPresupuestoPescaPK().getPresuNumero().compareToIgnoreCase("") == 0) {
                prdPresupuestoPesca.setUsrEmpresa(sisInfoTO.getEmpresa());
                prdPresupuestoPesca.setUsrCodigo(sisInfoTO.getUsuario());
                prdPresupuestoPesca.setUsrFechaInserta(UtilsDate.timestamp(UtilsValidacion.fechaSistema()));
            }

            susSuceso = (prdPresupuestoPesca.getPrdPresupuestoPescaPK().getPresuNumero() == null
                    || prdPresupuestoPesca.getPrdPresupuestoPescaPK().getPresuNumero().compareToIgnoreCase("") == 0)
                    ? "INSERT" : "UPDATE";
            susTabla = "produccion.prd_presupuesto_pesca";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            String mensaje = (prdPresupuestoPesca.getPrdPresupuestoPescaPK().getPresuNumero() == null
                    || prdPresupuestoPesca.getPrdPresupuestoPescaPK().getPresuNumero().compareToIgnoreCase("") == 0)
                    ? "ingreso" : "modifico";
            comprobar = presupuestoPescaDao.insertarModificarPrdPresupuestoPesca(prdPresupuestoPesca,
                    listaPrdPresupuestoPescaDetalles, sisSuceso);
            if (!comprobar) {
                retorno = "FHubo un error al guardar el Presupuesto de Pesca...\nIntente de nuevo o contacte con el administrador";
            } else {
                retorno = "T<html>Se " + mensaje + " el Presupuesto de Pesca con la siguiente informacion:<br><br>"
                        + "Numero: <font size = 5>"
                        + prdPresupuestoPesca.getPrdPresupuestoPescaPK().getPresuNumero() + "</font></html>";
                mensajeTO.setFechaCreacion(prdPresupuestoPesca.getUsrFechaInserta().toString());
                mensajeTO.getMap().put("prdPresupuestoPesca", prdPresupuestoPesca);
            }
        }
        mensajeTO.setMensaje(retorno);
        return mensajeTO;
    }

    public PrdPresupuestoPesca getPrdPresupuestoPesca(PrdPresupuestoPescaPK presupuestoPescaPK) {
        return presupuestoPescaDao.getPrdPresupuestoPesca(presupuestoPescaPK);
    }

    public List<PrdPresupuestoPesca> getListaPrdPresupuestoPesca(String empresa) {
        return presupuestoPescaDao.getListaPrdPresupuestoPesca(empresa);
    }

    public String desmayorizarPresupuestoPesca(PrdPresupuestoPescaPK prdPresupuestoPescaPK) {
        presupuestoPescaDao.desmayorizarPresupuestoPesca(prdPresupuestoPescaPK);
        return "TSe Desmayorizo correctamente el presupuesto de pesca " + prdPresupuestoPescaPK.getPresuNumero();
    }

    public String anularRestaurarPresupuestoPesca(PrdPresupuestoPescaPK prdPresupuestoPescaPK,
            boolean anularRestaurar) {
        presupuestoPescaDao.anularRestaurarPresupuestoPesca(prdPresupuestoPescaPK, anularRestaurar);
        return "TSe " + (anularRestaurar ? "Anulo" : "Restauró") + " correctamente el presupuesto de pesca "
                + prdPresupuestoPescaPK.getPresuNumero();
    }

}
