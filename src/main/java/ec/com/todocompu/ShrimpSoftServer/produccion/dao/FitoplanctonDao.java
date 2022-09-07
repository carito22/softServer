/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.dao;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFitoplanctonTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListadoFitoplanctonTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdFitoplancton;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdFitoplanctonPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;
import java.util.List;

/**
 *
 * @author CarolValdiviezo
 */
public interface FitoplanctonDao extends GenericDao<PrdFitoplancton, PrdFitoplanctonPK> {

    public boolean insertarPrdFitoplancton(PrdFitoplancton prdFitoplancton, SisSuceso sisSuceso) throws Exception;

    public boolean eliminarPrdFitoplancton(PrdFitoplancton prdFitoplancton, SisSuceso sisSuceso) throws Exception;

    public List<PrdFitoplanctonTO> getListPrdFitoplanctonTO(String empresa, String sector, String fecha) throws Exception;

    public List<PrdListadoFitoplanctonTO> getListadoPrdFitoplanctonTO(String empresa, String sector, String piscina, String desde, String hasta) throws Exception;
}
