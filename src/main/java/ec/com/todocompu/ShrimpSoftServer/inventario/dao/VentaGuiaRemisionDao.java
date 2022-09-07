/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.inventario.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvVentaGuiaRemision;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

/**
 *
 * @author CarolValdiviezo
 */
public interface VentaGuiaRemisionDao extends GenericDao<InvVentaGuiaRemision, Integer> {

    public List<InvVentaGuiaRemision> obtenerInvVentaGuiaRemision(String empresa, String periodo, String motivo, String numero);

    public InvVentaGuiaRemision obtenerInvVentaGuiaRemision(int secuencial);

    public Boolean accionInvVentaGuiaRemision(InvVentaGuiaRemision invVentaGuiaRemision, SisSuceso sisSuceso, char accion) throws Exception;
}
