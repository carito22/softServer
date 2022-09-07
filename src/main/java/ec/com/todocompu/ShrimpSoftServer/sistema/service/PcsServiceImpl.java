package ec.com.todocompu.ShrimpSoftServer.sistema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ec.com.todocompu.ShrimpSoftServer.sistema.dao.PcsDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisPcs;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.Date;
import java.util.List;

@Service
public class PcsServiceImpl implements PcsService {

    @Autowired
    private PcsDao pcsDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public boolean comprobarSisPcs(String mac) throws Exception {
        return pcsDao.comprobarSisPcs(mac);
    }

    @Override
    public List<SisPcs> listarSisPcs(String busqueda, boolean mostrarActivos) throws Exception {
        return pcsDao.listarSisPcs(busqueda, mostrarActivos);
    }

    @Override
    public SisPcs insertarSisPc(SisPcs sisPc, SisInfoTO sisInfoTO) throws Exception {
        sisPc.setInfEstado(true);
        sisPc.setUsrCodigo(sisInfoTO.getUsuario());
        sisPc.setUsrFechaInsertaPc(new Date());
        susClave = sisPc.getInfMac();
        susDetalle = "Se ingresó la mac: " + susClave;
        susSuceso = "INSERT";
        susTabla = "sistemaweb.sis_pcs";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        sisPc = pcsDao.insertarSisPc(sisPc, sisSuceso);
        return sisPc;
    }

    @Override
    public SisPcs modificarSisPc(SisPcs sisPc, SisInfoTO sisInfoTO) throws Exception {
        sisPc.setUsrCodigo(sisInfoTO.getUsuario());
        sisPc.setUsrFechaInsertaPc(new Date());
        susClave = sisPc.getInfMac();
        susDetalle = "Se modificó la mac: " + susClave;
        susSuceso = "UPDATE";
        susTabla = "sistemaweb.sis_pcs";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        sisPc = pcsDao.modificarSisPc(sisPc, sisSuceso);
        return sisPc;
    }

    @Override
    public boolean eliminarSisPc(String sisPc, SisInfoTO sisInfoTO) throws Exception {
        susClave = sisPc;
        susDetalle = "Se eliminó la mac: " + susClave;
        susSuceso = "DELETE";
        susTabla = "sistemaweb.sis_pcs";
        SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
        return pcsDao.eliminarSisPc(new SisPcs(sisPc), sisSuceso);
    }

}
