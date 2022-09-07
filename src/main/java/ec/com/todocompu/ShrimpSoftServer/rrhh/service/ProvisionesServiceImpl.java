package ec.com.todocompu.ShrimpSoftServer.rrhh.service;

import ec.com.todocompu.ShrimpSoftServer.excepciones.GeneralException;
import ec.com.todocompu.ShrimpSoftServer.rrhh.dao.ProvisionesDao;
import ec.com.todocompu.ShrimpSoftServer.util.Suceso;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhProvisiones;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author KevinQuispe
 */
@Service
public class ProvisionesServiceImpl implements ProvisionesService {

    @Autowired
    private ProvisionesDao provisionesDao;
    private String susTabla;
    private String susClave;
    private String susSuceso;
    private String susDetalle;

    @Override
    public RhProvisiones listarRhProvisiones(String empresa) throws Exception {
        return provisionesDao.listarRhProvisiones(empresa);
    }

    @Override
    public RhProvisiones insertarRhProvisiones(RhProvisiones rhProvisiones, SisInfoTO sisInfoTO) throws Exception {
        RhProvisiones consultaEmpresa = provisionesDao.obtenerPorId(RhProvisiones.class, rhProvisiones.getProvEmpresa());
        if (consultaEmpresa == null) {
            susClave = rhProvisiones.getProvEmpresa() + "";
            susDetalle = "Se ingresó la configuración de provisines correctamente.";
            susSuceso = "INSERT";
            susTabla = "recursoshumanos.rh_provisiones";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            rhProvisiones = provisionesDao.insertarRhProvisiones(rhProvisiones, sisSuceso);
            return rhProvisiones;
        } else {
            throw new GeneralException("La empresa seleccionada ya tiene valores de configuración de provisiones");
        }
    }

    @Override
    public RhProvisiones modificarRhProvisiones(RhProvisiones rhProvisiones, SisInfoTO sisInfoTO) throws Exception {
       if (provisionesDao.obtenerPorIdEvict(RhProvisiones.class, rhProvisiones.getProvEmpresa()) != null) {
            susClave = rhProvisiones.getProvEmpresa() + "";
            susDetalle = "Los valores de configuración de provisines se han modificado correctamente";
            susSuceso = "UPDATE";
            susTabla = "recursoshumanos.rh_provisiones";
            SisSuceso sisSuceso = Suceso.crearSisSuceso(susTabla, susClave, susSuceso, susDetalle, sisInfoTO);
            rhProvisiones = provisionesDao.modificarRhProvisiones(rhProvisiones, sisSuceso);
            return rhProvisiones;
        } else {
            throw new GeneralException("Ocurrio un Error, Los valores de configuración de provisines No se han podido modificar");
        }
    }
}
