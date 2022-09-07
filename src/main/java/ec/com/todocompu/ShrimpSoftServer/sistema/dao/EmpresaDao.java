package ec.com.todocompu.ShrimpSoftServer.sistema.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisEmpresaTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresa;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisEmpresaParametros;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface EmpresaDao extends GenericDao<SisEmpresa, String> {

    public List<SisEmpresaTO> getListaSisEmpresa(String usrCodigo, String empresa) throws Exception;

    public SisEmpresaTO obtenerSisEmpresaTO(String empresa) throws Exception;

    public List<SisEmpresaTO> getListaSisEmpresaWeb(String usrCodigo) throws Exception;

    public boolean insertarSisEmpresa(SisEmpresa sisEmpresa, SisSuceso sisSuceso,
            SisEmpresaParametros sisEmpresaParametros) throws Exception;

    public boolean modificarSisEmpresa(SisEmpresa sisEmpresa, SisSuceso sisSuceso,
            SisEmpresaParametros sisEmpresaParametros) throws Exception;

    boolean estadoLlevarContabilidad(String empresa);

    public boolean existeEmpresaConElMismoRuc(String empresa, String ruc) throws Exception;

    public boolean existeEmpresaConElMismoNombre(String empresa, String nombre) throws Exception;

    public boolean existeEmpresaConLaMismaRazonSocial(String empresa, String razonSocial) throws Exception;

    public boolean insertarRegistrosComplementarios(String empresa) throws Exception;

    public SisEmpresaTO obtenerSisEmpresaTOByRUC(String ruc);
}
