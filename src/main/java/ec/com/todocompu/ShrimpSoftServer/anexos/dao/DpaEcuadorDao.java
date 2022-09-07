package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxFunRegistroDatosCrediticiosTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxProvinciaCantonTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxDpaEcuador;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxDpaEcuadorPK;

public interface DpaEcuadorDao extends GenericDao<AnxDpaEcuador, AnxDpaEcuadorPK> {

    public List<AnxProvinciaCantonTO> getComboAnxDpaProvinciaTO() throws Exception;

    public List<AnxProvinciaCantonTO> getComboAnxDpaCantonTO(String codigoProvincia) throws Exception;

    public List<AnxProvinciaCantonTO> getComboAnxParroquiaTO(String codigoProvincia, String codigoCanton)
            throws Exception;

    public String getObtenerProvincia(String canton) throws Exception;

    public List<AnxFunRegistroDatosCrediticiosTO> getFunRegistroDatosCrediticiosTOs(String codigoEmpresa,
            String fechaDesde, String fechaHasta) throws Exception;

    public List<String> getListadoCantonesAllTO() throws Exception; 
}
