package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisFirmaElectronica;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

public interface FirmaElectronicaDao extends GenericDao<SisFirmaElectronica, Integer> {

    public List<SisFirmaElectronica> listarFirmaElectronica(String empresa) throws Exception;

    public List<SisFirmaElectronica> listarFirmaElectronicaNoVerificadas(String empresa) throws Exception;

    public SisFirmaElectronica insertarFirmaElectronica(SisFirmaElectronica firmaElectronica, SisSuceso sisSuceso) throws Exception;

    public SisFirmaElectronica modificarFirmaElectronica(SisFirmaElectronica firmaElectronica, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarFirmaElectronica(SisFirmaElectronica firmaElectronicaF, SisSuceso sisSuceso) throws Exception;

    public boolean existeCertificado(String empresa, Integer secuencia, byte[] base64) throws Exception;

    public SisFirmaElectronica obtenerFirmaElectronica(Integer secuencial) throws Exception;
}
