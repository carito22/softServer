/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.contabilidad.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.contabilidad.entity.ConContablePlanillaFondoReserva;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

/**
 *
 * @author Trabajo
 */
public interface ContablePlanillaFondoReservaDao extends GenericDao<ConContablePlanillaFondoReserva, Integer> {

    public List<ConContablePlanillaFondoReserva> getListaConContablePlanillaFondoReserva(String empresa, String sector, String periodo) throws Exception;

    public boolean insertarContablePlanillaFondoReserva(ConContablePlanillaFondoReserva conContablePlanillaFondoReserva, SisSuceso sisSuceso) throws Exception;

    public boolean modificarContablePlanillaFondoReserva(ConContablePlanillaFondoReserva conContablePlanillaFondoReserva, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarContablePlanillaFondoReserva(ConContablePlanillaFondoReserva conContablePlanillaFondoReserva, SisSuceso sisSuceso) throws Exception;

}
