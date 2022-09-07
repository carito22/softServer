package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionUnidadesComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionUnidadesTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionUnidadesPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface ProductoPresentacionUnidadesService {

    public List<InvProductoPresentacionUnidadesComboListadoTO> getListaPresentacionUnidadComboTO(String empresa)
            throws Exception;

    public String accionInvProductoPresentacionUnidadesTO(
            InvProductoPresentacionUnidadesTO invProductoPresentacionUnidadesTO, char accion, SisInfoTO sisInfoTO)
            throws Exception;

    public boolean eliminarInvProductoPresentacionUnidades(InvProductoPresentacionUnidadesPK invProductoPresentacionUnidadesPK, SisInfoTO sisInfoTO) throws Exception;

}
