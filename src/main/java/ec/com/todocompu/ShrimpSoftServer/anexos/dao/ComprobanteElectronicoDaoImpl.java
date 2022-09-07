package ec.com.todocompu.ShrimpSoftServer.anexos.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import ec.com.todocompu.ShrimpSoftServer.util.dao.GenericDaoImpl;
import ec.com.todocompu.ShrimpSoftUtils.anexos.TO.ComprobanteElectronico;

@Repository
public class ComprobanteElectronicoDaoImpl extends GenericDaoImpl<ComprobanteElectronico, String>
		implements ComprobanteElectronicoDao {

	@Override
	public List<ComprobanteElectronico> obtenerDocumentosPorCedulaRucMesAnio(String cedulaRuc, String mes,
			String anio) {
		List<ComprobanteElectronico> listaComprobanteElectronico = obtenerPorSql(
                    "select a.e_clave_acceso as \"claveAcceso\", a.comp_empresa as \"codigoEmpresa\", (select e.emp_nombre from sistemaweb.sis_empresa e where e.emp_codigo=a.comp_empresa) as \"empresa\", "
                        + "case when substring(a.e_clave_acceso,9,2)='01' then 'FACTURA' "
                        + "when substring(a.e_clave_acceso,9,2)='04' then 'NOTA DÉBITO' "
                        + "when substring(a.e_clave_acceso,9,2)='05' then 'NOTA CRÉDITO' "
                        + "when substring(a.e_clave_acceso,9,2)='07' then 'RETENCIÓN' end as \"tipoDocumento\", "
                        + " substring(a.e_clave_acceso,25,3)||'-'||substring(a.e_clave_acceso,28,3)||'-'||substring(a.e_clave_acceso,31,8) as \"numeroDocumento\", "
                        + "substring(a.e_clave_acceso,1,2)||'/'||substring(a.e_clave_acceso,3,2)||'/'||substring(a.e_clave_acceso,5,4) as \"fechaEmision\", "
                        + "a.e_autorizacion_fecha as \"fechaAutorizacion\", a.e_autorizacion_numero as \"numeroAutorizacion\", a.e_xml as \"eXml\" "
                        + "from anexo.anx_compra_electronica a "
                        + "inner join inventario.inv_compras c on(a.comp_empresa=c.comp_empresa and a.comp_periodo=c.comp_periodo and a.comp_motivo=c.comp_motivo and a.comp_numero=c.comp_numero) "
                        + "inner join inventario.inv_proveedor p on(c.prov_empresa=p.prov_empresa and c.prov_codigo=p.prov_codigo) "
                        + "where p.prov_id_numero='" + cedulaRuc + "' and substring(a.comp_periodo,6,2)='" + mes
                                    + "' and substring(a.comp_periodo,1,4)='" + anio + "' and a.e_estado='AUTORIZADO'",
                    ComprobanteElectronico.class);

		listaComprobanteElectronico.addAll(obtenerPorSql(
                    "select a.e_clave_acceso as \"claveAcceso\", a.vta_empresa as \"codigoEmpresa\", (select e.emp_nombre from sistemaweb.sis_empresa e where e.emp_codigo=a.vta_empresa) as \"empresa\", "
                        + "case when substring(a.e_clave_acceso,9,2)='01' then 'FACTURA' "
                        + "when substring(a.e_clave_acceso,9,2)='04' then 'NOTA DÉBITO' "
                        + "when substring(a.e_clave_acceso,9,2)='05' then 'NOTA CRÉDITO' "
                        + "when substring(a.e_clave_acceso,9,2)='07' then 'RETENCIÓN' end as \"tipoDocumento\", "
                        + "substring(a.e_clave_acceso,25,3)||'-'||substring(a.e_clave_acceso,28,3)||'-'||substring(a.e_clave_acceso,31,8) as \"numeroDocumento\", "
                        + "substring(a.e_clave_acceso,1,2)||'/'||substring(a.e_clave_acceso,3,2)||'/'||substring(a.e_clave_acceso,5,4) as \"fechaEmision\", "
                        + "a.e_autorizacion_fecha as \"fechaAutorizacion\", a.e_autorizacion_numero as \"numeroAutorizacion\", a.e_xml as \"eXml\" "
                        + "from anexo.anx_venta_electronica a "
                        + "inner join inventario.inv_ventas v on(a.vta_empresa=v.vta_empresa and a.vta_periodo=v.vta_periodo and a.vta_motivo=v.vta_motivo and a.vta_numero=v.vta_numero) "
                        + "inner join inventario.inv_cliente c on(v.cli_empresa=c.cli_empresa and v.cli_codigo=c.cli_codigo) "
                        + "where c.cli_id_numero='" + cedulaRuc + "' and substring(a.vta_periodo,6,2)='" + mes
                        + "' and substring(a.vta_periodo,1,4)='" + anio + "' and a.e_estado='AUTORIZADO'",
                    ComprobanteElectronico.class));
		return listaComprobanteElectronico;
	}
}
