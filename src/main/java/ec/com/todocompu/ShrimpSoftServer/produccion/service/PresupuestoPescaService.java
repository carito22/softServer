package ec.com.todocompu.ShrimpSoftServer.produccion.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.MensajeTO;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPesca;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaDetalle;
import ec.com.todocompu.ShrimpSoftUtils.produccion.entity.PrdPresupuestoPescaPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface PresupuestoPescaService {

	public MensajeTO insertarModificarPrdPresupuestoPesca(PrdPresupuestoPesca prdPresupuestoPesca,
			List<PrdPresupuestoPescaDetalle> listaPrdPresupuestoPescaDetalles, SisInfoTO sisInfoTO) throws Exception;

	public PrdPresupuestoPesca getPrdPresupuestoPesca(PrdPresupuestoPescaPK presupuestoPescaPK);

	public List<PrdPresupuestoPesca> getListaPrdPresupuestoPesca(String empresa);

	public String desmayorizarPresupuestoPesca(PrdPresupuestoPescaPK prdPresupuestoPescaPK);

	public String anularRestaurarPresupuestoPesca(PrdPresupuestoPescaPK prdPresupuestoPescaPK, boolean anularRestaurar);
}
