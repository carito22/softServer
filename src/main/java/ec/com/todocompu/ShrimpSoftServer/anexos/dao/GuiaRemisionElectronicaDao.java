/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaGuiaRemisionElectronicaTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.AnxListaGuiaRemisionPendientesTO;
import ec.com.todocompu.ShrimpSoftUtils.anexos.entity.AnxGuiaRemisionElectronica;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

/**
 *
 * @author CarolValdiviezo
 */
public interface GuiaRemisionElectronicaDao extends GenericDao<AnxGuiaRemisionElectronica, Integer> {

    public List<AnxListaGuiaRemisionPendientesTO> getListaGuiaRemisionPendientesTO(String empresa) throws Exception;

    public List<AnxListaGuiaRemisionElectronicaTO> getListaAnxListaGuiaRemisionElectronicaTO(String empresa, String fechaDesde, String fechaHasta) throws Exception;

    public AnxGuiaRemisionElectronica buscarAnxGuiaRemisionElectronica(String empresa, String periodo, String numero) throws Exception;

    public Boolean accionAnxGuiaRemisionElectronica(AnxGuiaRemisionElectronica anxGuiaRemisionElectronica, SisSuceso sisSuceso, char accion) throws Exception;

    public String getXmlComprobanteElectronico(String empresa, String ePeriodo, String eNumero) throws Exception;

    public boolean comprobarAnxGuiaRemisionElectronica(String empresa, String periodo, String numero) throws Exception;

    public String comprobarAnxGuiaRemisionElectronicaAutorizacion(String empresa, String periodo, String numero) throws Exception;
}
