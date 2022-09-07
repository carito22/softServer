package ec.com.todocompu.ShrimpSoftServer.banco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.todocompu.ShrimpSoftServer.banco.dao.ChequeDao;
import ec.com.todocompu.ShrimpSoftServer.banco.dao.PreavisosDao;
import ec.com.todocompu.ShrimpSoftServer.util.ConversionesBanco;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.banco.TO.BanPreavisosTO;
import ec.com.todocompu.ShrimpSoftUtils.banco.entity.BanPreavisos;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

@Service
public class PreavisosServiceImpl implements PreavisosService {

    @Autowired
    private PreavisosDao preavisosDao;

    @Autowired
    private ChequeDao chequeDao;

    private boolean comprobar = false;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public String insertarPreaviso(BanPreavisosTO banPreavisosTO, SisInfoTO sisInfoTO) throws Exception {
        String mensajes = "";
        // / CREAR EL SUCESO
        susSuceso = "INSERT";
        susClave = banPreavisosTO.getPreCuentaContable();
        susDetalle = "Se inserta un preaviso de cheques.";
        susTabla = "banco.ban_preavisos";
        // /// CREANDO CarPagosForma
        BanPreavisos banPreavisos = ConversionesBanco.convertirBanPreavisosTO_BanPreavisos(banPreavisosTO);
        comprobar = false;
        if (chequeDao.getConteoChequesPreavisados(banPreavisosTO.getPreEmpresa(),
                banPreavisosTO.getPreCuentaContable(), banPreavisosTO.getPreNombreArchivoGenerado()) == 0) {
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprobar = preavisosDao.insertarPreaviso(banPreavisos, sisSuceso);
            if (comprobar) {
                mensajes = "T";
            } else {
                mensajes = "FHubo un error en el lado del servidor al intentar almacenar la información...";
            }
        } else {
            mensajes = "FEl registro que se intenta guardar ya existe. Contacte con el administrador...";
        }
        return mensajes;
    }

    @Override
    public String eliminarBanPreaviso(String empresa, String usuario, String cuenta, SisInfoTO sisInfoTO)
            throws Exception {
        String mensajes = "";
        // /// CREANDO Suceso
        susSuceso = "DELETE";
        susClave = cuenta;
        susTabla = "banco.ban_preavisos";
        BanPreavisos banPreavisos = preavisosDao.getBanPreavisos(empresa, cuenta);
        if (banPreavisos != null) {
            susDetalle = "Se eliminó el archivo " + banPreavisos.getBanPreavisosPK().getPreNombreArchivoGenerado()
                    + " de preaviso..";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            comprobar = preavisosDao.eliminarBanPreaviso(banPreavisos, sisSuceso);
            if (comprobar) {
                mensajes = "TSe eliminó el preaviso\n"
                        + banPreavisos.getBanPreavisosPK().getPreNombreArchivoGenerado();
            } else {
                mensajes = "FHubo un error en el lado del servidor al intentar almacenar la información...";
            }
        } else {
            mensajes = "FNo existe preaviso para eliminar...";
        }
        return mensajes;
    }

}
