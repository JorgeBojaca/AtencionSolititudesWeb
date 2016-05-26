package co.edu.udea.iw.dto;

import javax.xml.bind.annotation.XmlRootElement;

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
