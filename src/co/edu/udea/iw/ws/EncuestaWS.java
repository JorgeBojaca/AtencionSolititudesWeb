package co.edu.udea.iw.ws;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import co.edu.udea.iw.dto.Pregunta;
import co.edu.udea.iw.dto.Respuesta;
import co.edu.udea.iw.dto.RespuestaDTOws;
import co.edu.udea.iw.exception.ExceptionDao;
import co.edu.udea.iw.exception.IWServiceException;
import co.edu.udea.iw.logicaNegocio.EncuestaService;
import javassist.tools.rmi.RemoteException;

/**
 * Servicios Web para logica del Negocio
 * de la EncuestaService
 * 
 * @author Diana Ciro
 * @author Milena Cardenas
 * @author Jorge Bojaca  
 * @version 1.0
 */
@Component
@Path("Encuesta")
public class EncuestaWS {
	
	/*Se realiza una inyeccion de dependencia, para crear una instancia de EncuestaService */
	@Autowired
	EncuestaService encuestaService;

	/**
	 * Servicio para generar una encuesta en formato JSON.
	 * @return Lista de preguntas
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pregunta> generarEncuesta() {
		List<Pregunta> preguntas = null;
		try {
			preguntas = encuestaService.generarEncuesta();
			return preguntas;
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		}
	}

	/**
	 * Servicio para obtener las respuesta de una solicitud enviada como parametro en formato JSON
	 * @param idSolicitud identificador de la solicitud
	 * @return Lista de respuestas
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("ObtenerRespuestas")
	public List<RespuestaDTOws> obtenerRespuestas(@QueryParam("idSolicitud") int idSolicitud) {
		List<Respuesta> respuestas = null;
		List<RespuestaDTOws> respuestasRet = null;

		try {
			respuestas = encuestaService.obtener(idSolicitud);
			if (!respuestas.isEmpty()) {
				respuestasRet = new ArrayList<RespuestaDTOws>();
				for (Respuesta respuesta : respuestas) {
					RespuestaDTOws resp = new RespuestaDTOws();
					resp.setIdSolicitud(respuesta.getId().getIdSolicitud().getId());
					resp.setIdPregunta(respuesta.getId().getIdPregunta().getId());
					resp.setRespuesta(respuesta.getRespuesta());
					respuestasRet.add(resp);
				}
			}
			return respuestasRet;
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		} catch (IWServiceException e) {
			throw new RemoteException(e);
		}
	}
	
	/**
	 * Servicio para generar el Promedio de una pregunta enviada como parametro.
	 * @param idPregunta identificador de la pregunta.
	 * @return String que tiene la informacion estadistica de una pregunta.
	 */
	@Produces(MediaType.TEXT_HTML)
	@GET
	@Path("Estadistica")
	public String estadisticaPregunta(@QueryParam("idPregunta")int idPregunta){
		String estadistica=null;
		try {
			estadistica=encuestaService.estadisticaPorPregunta(idPregunta);
			return estadistica;
		} catch (IWServiceException e) {
			throw new RemoteException(e);
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		}
	}
	
	/**
	 * Servicio para responder una pregunta enviada como parametro.
	 * @param idSolicitud identificador de la solicitud.
	 * @param idPregunta identificador de la pregunta.
	 * @param respuesta respuesta de la pregunta.
	 * @return Mensaje de agradecimiento.
	 */
	@Produces(MediaType.TEXT_HTML)
	@POST
	@Path("ResponderPregunta")
	public String responderPregunta(@QueryParam("idSolicitud")int idSolicitud,
			@QueryParam("idPregunta")int idPregunta,@QueryParam("respuesta")int respuesta){
		
		try {
			encuestaService.guardarRespuesta(idSolicitud, idPregunta, respuesta);
			return "Gracias por responder!";
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		} catch (IWServiceException e) {
			throw new RemoteException(e);
		}
		
	}

}
