package ec.com.todocompu.ShrimpSoftServer.inventario.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionCajasComboListadoTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.TO.InvProductoPresentacionCajasTO;
import ec.com.todocompu.ShrimpSoftUtils.inventario.entity.InvProductoPresentacionCajasPK;
import ec.com.todocompu.ShrimpSoftUtils.sistema.TO.SisInfoTO;

@Transactional
public interface ProductoPresentacionCajasService {

    public List<InvProductoPresentacionCajasComboListadoTO> getListaPresentacionCajaComboTO(String empresa)
            throws Exception;

    public String accionInvProductoPresentacionCajasTO(InvProductoPresentacionCajasTO invProductoPresentacionCajasTO,
            char accion, SisInfoTO sisInfoTO) throws Exception;

    public boolean eliminarInvPresentacionCajas(InvProductoPresentacionCajasPK invProductoPresentacionCajasPK, SisInfoTO sisInfoTO) throws Exception;

}
