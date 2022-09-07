/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdFitoplanctonTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.TO.PrdListadoFitoplanctonTO;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CarolValdiviezo
 */
@Transactional
public interface FitoplanctonService {

    public boolean insertarPrdFitoplancton(String codigoSector, String fecha, List<PrdFitoplanctonTO> listado, SisInfoTO sisInfoTO) throws Exception;

    public String eliminarPrdFitoplanctonTO(String codigoSector, String fecha, PrdFitoplanctonTO prdFitoplanctonTO, SisInfoTO sisInfoTO) throws Exception;

    public List<PrdFitoplanctonTO> getListPrdFitoplanctonTO(String empresa, String sector, String fecha) throws Exception;

    public List<PrdListadoFitoplanctonTO> getListadoPrdFitoplanctonTO(String empresa, String sector, String piscina, String desde, String hasta) throws Exception;
}
