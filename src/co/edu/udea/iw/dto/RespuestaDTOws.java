package co.edu.udea.iw.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO para el servicio Web EncuestaWS
 * 
 * @author Diana Ciro
 * @author Milena Cardenas
 * @author Jorge Bojaca  
 * @version 1.0
 */
/*Parsear de manera automatica los objetos de esta clase a formato JSON*/
@XmlRootElement
public class RespuestaDTOws {
	
	private int idSolicitud;
	private int idPregunta;
	private int respuesta;
	
	public int getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(int idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	public int getIdPregunta() {
		return idPregunta;
	}
	public void setIdPregunta(int idPregunta) {
		this.idPregunta = idPregunta;
	}
	public int getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(int respuesta) {
		this.respuesta = respuesta;
	}
	

}
