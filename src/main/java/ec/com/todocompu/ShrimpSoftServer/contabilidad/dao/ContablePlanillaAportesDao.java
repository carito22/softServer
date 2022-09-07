/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePlanillaAportes;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

/**
 *
 * @author Trabajo
 */
public interface ContablePlanillaAportesDao extends GenericDao<ConContablePlanillaAportes, Integer> {

    public List<ConContablePlanillaAportes> getListaConContablePlanillaAportes(String empresa, String sector, String periodo) throws Exception;

    public boolean insertarContablePlanillaAportes(ConContablePlanillaAportes conContablePlanillaAportes, SisSuceso sisSuceso) throws Exception;

    public boolean modificarContablePlanillaAportes(ConContablePlanillaAportes conContablePlanillaAportes, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarContablePlanillaAportes(ConContablePlanillaAportes conContablePlanillaAportes, SisSuceso sisSuceso) throws Exception;
}
