/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.todocompu.ShrimpSoftServer.rrhh.dao;

import java.util.List;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDao;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhComboFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhCtaFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.TO.RhListaFormaPagoTO;
import ec.com.todocompu.ShrimpSoftUtils.rrhh.entity.RhFormaPago;
import ec.com.todocompu.ShrimpSoftUtils.sistema.entity.SisSuceso;

public interface FormaPagoDao extends GenericDao<RhFormaPago, Integer> {
	Boolean accionRhFormaPago(RhFormaPago rhFormaPago, SisSuceso sisSuceso, char accion) throws Exception;

	RhFormaPago buscarFormaPago(Integer fpSecuencia) throws Exception;

	Boolean buscarRhFormaPago(String ctaContable, String empresa) throws Exception;

	List<RhListaFormaPagoTO> getListaFormaPago(String empresa) throws Exception;

	List<RhComboFormaPagoTO> getComboFormaPago(String empresa) throws Exception;

	RhCtaFormaPagoTO buscarCtaRhFormaPago(String empCodigo, String fpDetalle) throws Exception;

}
