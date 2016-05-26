package co.edu.udea.iw.ws;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.iw.dto.Solicitud;
import co.edu.udea.iw.dto.SolicitudDTOws;
import co.edu.udea.iw.exception.ExceptionDao;
import co.edu.udea.iw.exception.IWServiceException;
import co.edu.udea.iw.logicaNegocio.SolicitudService;
import javassist.tools.rmi.RemoteException;

@Component
@Path("Solicitud")
public class SolicitudWS {

	@Autowired
	SolicitudService solicitudService;

	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public List<SolicitudDTOws> obtener(@QueryParam("user") String user) throws RemoteException {
		List<Solicitud> solicitudes = null;
		List<SolicitudDTOws> solicitudesRet = null;
		try {
			solicitudes = solicitudService.obtenerSolicitudes(user);
			if (!solicitudes.isEmpty()) {

				solicitudesRet = new ArrayList<SolicitudDTOws>();
				for (Solicitud solicitud : solicitudes) {

					SolicitudDTOws solic = new SolicitudDTOws();
					solic.setCliente(solicitud.getCliente().getNombres() + " " + solicitud.getCliente().getApellidos());
					solic.setComplejidad(solicitud.getComplejidad());
					solic.setDescripcion(solicitud.getDescripcion());
					solic.setFechaSolicitud(solicitud.getFechaSolicitud());
					solic.setFechaRespuesta(solicitud.getFechaRespuesta());
					solic.setId(solicitud.getId());
					if (solicitud.getResponsable() != null) {
						solic.setResponsable(solicitud.getResponsable().getUser());
					}
					solic.setTipoSolicitud(solicitud.getTipoSolicitud().getNombre());
					solic.setProducto(solicitud.getProducto());
					solic.setRespuestaSolicitud(solicitud.getRespuesta());
					solicitudesRet.add(solic);
				}
			}
			return solicitudesRet;
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		} catch (IWServiceException e) {
			throw new RemoteException(e);
		}
	}

	@Produces(MediaType.TEXT_PLAIN)
	@POST
	@Path("Guardar")
	public String guardar(@QueryParam("descripcion") String descripcion, @QueryParam("tiposolicitud") int tiposolicitud,
			@QueryParam("cliente") String cliente, @QueryParam("producto") String producto,
			@QueryParam("idsucursal") int idSucursal) {

		try {
			solicitudService.guardarSolicitud(descripcion, tiposolicitud, cliente, producto, idSucursal, new Date());
			return "Se guardo correctamente la solicitud";
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		} catch (IWServiceException e) {
			throw new RemoteException(e);
		}
	}

	@Produces(MediaType.TEXT_PLAIN)
	@PUT
	@Path("AsignarResponsable")
	public String asignarResponsable(@QueryParam("idSolicitud") int idSolicitud,
			@QueryParam("responsable") String usuarioResponsable, @QueryParam("gerente") String usuarioGerente) {
		try {
			solicitudService.asignarResponsable(idSolicitud, usuarioResponsable, usuarioGerente);
			return "Se asignó responsable correctamente";
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		} catch (IWServiceException e) {
			throw new RemoteException(e);
		}
	}

	@Produces(MediaType.TEXT_PLAIN)
	@PUT
	@Path("ResponderSolicitud")
	public String responderSolicitud(@QueryParam("idSolicitud") int idSolicitud,
			@QueryParam("respuesta") String respuestaSolicitud, @QueryParam("responsable") String usuarioResponsable) {

		try {
			solicitudService.responderSolicitud(idSolicitud, respuestaSolicitud, new Date(), usuarioResponsable);
			return "Se respondio la solicitud correctamente";
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		} catch (IWServiceException e) {
			throw new RemoteException(e);
		}

	}

	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("ObtenerSolicitud")
	public SolicitudDTOws obtenerSolicitud(@QueryParam("idSolicitud") int idSolicitud) {
		Solicitud solicitud = null;
		SolicitudDTOws solic = null;
		try {
			solicitud = solicitudService.obtenerSolicitud(idSolicitud);
			if (solicitud != null) {
				solic = new SolicitudDTOws();
				solic.setCliente(solicitud.getCliente().getNombres() + " " + solicitud.getCliente().getApellidos());
				solic.setComplejidad(solicitud.getComplejidad());
				solic.setDescripcion(solicitud.getDescripcion());
				solic.setFechaSolicitud(solicitud.getFechaSolicitud());
				solic.setFechaRespuesta(solicitud.getFechaRespuesta());
				solic.setId(solicitud.getId());
				if (solicitud.getResponsable() != null) {
					solic.setResponsable(solicitud.getResponsable().getUser());
				}
				solic.setTipoSolicitud(solicitud.getTipoSolicitud().getNombre());
				solic.setProducto(solicitud.getProducto());
				solic.setRespuestaSolicitud(solicitud.getRespuesta());
			}

			return solic;
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		} catch (IWServiceException e) {
			throw new RemoteException(e);
		}
	}

	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("SolicitudesAtrasadas")
	public List<SolicitudDTOws> solicitudesAtrasadas() {
		List<Solicitud> solicitudes = null;
		List<SolicitudDTOws> solicitudesRet = null;
		try {
			solicitudes = solicitudService.seguimientoSolicitudes();
			if (!solicitudes.isEmpty()) {

				solicitudesRet = new ArrayList<SolicitudDTOws>();
				for (Solicitud solicitud : solicitudes) {

					SolicitudDTOws solic = new SolicitudDTOws();
					solic.setCliente(solicitud.getCliente().getNombres() + " " + solicitud.getCliente().getApellidos());
					solic.setComplejidad(solicitud.getComplejidad());
					solic.setDescripcion(solicitud.getDescripcion());
					solic.setFechaSolicitud(solicitud.getFechaSolicitud());
					solic.setFechaRespuesta(solicitud.getFechaRespuesta());
					solic.setId(solicitud.getId());
					if (solicitud.getResponsable() != null) {
						solic.setResponsable(solicitud.getResponsable().getUser());
					}
					solic.setTipoSolicitud(solicitud.getTipoSolicitud().getNombre());
					solic.setProducto(solicitud.getProducto());
					solic.setRespuestaSolicitud(solicitud.getRespuesta());
					solicitudesRet.add(solic);
				}
			}
			return solicitudesRet;
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		} catch (IWServiceException e) {
			throw new RemoteException(e);
		}
	}

	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("Filtrar") // NO ESTA FUNCIONANDO puede ser porque se utiliza el Query
						// y no se hace Join
	public List<SolicitudDTOws> fitrarSolicitudes(@QueryParam("tipo") int tipoSolicitud) {
		List<Solicitud> solicitudes = null;
		List<SolicitudDTOws> solicitudesRet = null;
		try {
			solicitudes = solicitudService.filtrarPorTipo(tipoSolicitud);
			if (!solicitudes.isEmpty()) {

				solicitudesRet = new ArrayList<SolicitudDTOws>();
				for (Solicitud solicitud : solicitudes) {

					SolicitudDTOws solic = new SolicitudDTOws();
					solic.setCliente(solicitud.getCliente().getNombres() + " " + solicitud.getCliente().getApellidos());
					solic.setComplejidad(solicitud.getComplejidad());
					solic.setDescripcion(solicitud.getDescripcion());
					solic.setFechaSolicitud(solicitud.getFechaSolicitud());
					solic.setFechaRespuesta(solicitud.getFechaRespuesta());
					solic.setId(solicitud.getId());
					if (solicitud.getResponsable() != null) {
						solic.setResponsable(solicitud.getResponsable().getUser());
					}
					solic.setTipoSolicitud(solicitud.getTipoSolicitud().getNombre());
					solic.setProducto(solicitud.getProducto());
					solic.setRespuestaSolicitud(solicitud.getRespuesta());
					solicitudesRet.add(solic);
				}
			}
			return solicitudesRet;
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		} catch (IWServiceException e) {
			throw new RemoteException(e);
		}

	}

}
