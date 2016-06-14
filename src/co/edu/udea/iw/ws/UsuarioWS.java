package co.edu.udea.iw.ws;

import java.util.List;

import javassist.tools.rmi.RemoteException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.udea.iw.dto.Usuario;
import co.edu.udea.iw.exception.ExceptionDao;
import co.edu.udea.iw.exception.IWServiceException;
import co.edu.udea.iw.logicaNegocio.UsuarioService;

/**
 * Servicios Web para logica del Negocio
 * de UsuarioService
 * 
 * @author Diana Ciro
 * @author Milena Cardenas
 * @author Jorge Bojaca  
 * @version 1.0
 */
@Component
@Path("Usuario")
public class UsuarioWS {

	@Autowired
	UsuarioService usuarioService;
	
	/**
	 * Servicio para guardar un cliente en la Base de Datos.
	 * 
	 * @param user usuario del cliente
	 * @param password contrasennia del cliente
	 * @param nombres nombre del cliente a guardar
	 * @param apellidos apellido del cliente  a guardar
	 * @param email correo electronico  a guardar
	 * @param telefono telefono  a guardar
	 * @param direccion direccion  a guardar
	 * @return Mensaje de confirmacion
	 */
	@Produces(MediaType.TEXT_PLAIN)
	@POST
	//@GET //para probar
	@Path("Guardar")
	public String guardar(@QueryParam("user")String user, @QueryParam("password")String password, @QueryParam("nombres")String nombres,
						@QueryParam("apellidos")String apellidos, @QueryParam("email")String email, @QueryParam("telefono")String telefono,
						@QueryParam("direccion")String direccion){
		
		String exito=null;
		try {
			exito = usuarioService.guardarCliente(user, password, nombres, apellidos, email, telefono, direccion);
			return exito;
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		} catch (IWServiceException e) {
			throw new RemoteException(e);
		}		
	}
	
	/**
	 * Servicio para actualizar un cliente en la Base de Datos.
	 * 
	 * @param user usuario del cliente
	 * @param password contrasennia del cliente
	 * @param nombres nombre del cliente a modificar
	 * @param apellidos apellido del cliente  a modificar
	 * @param email correo electronico  a modificar
	 * @param telefono telefono  a modificar
	 * @param direccion direccion  a modificar
	 * @return Mensaje de confirmacion
	 */
	@Produces(MediaType.TEXT_PLAIN)
	@PUT
	//@GET
	@Path("Actualizar")
	public String actualizar(@QueryParam("user")String user, @QueryParam("password")String password, @QueryParam("nombres")String nombres,
			@QueryParam("apellidos")String apellidos, @QueryParam("email")String email, @QueryParam("telefono")String telefono,
			@QueryParam("direccion")String direccion){
		
		String exito = null;
		try {
			exito = usuarioService.actualizarCliente(user, password, nombres, apellidos, email, telefono, direccion);
			return exito;
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		} catch (IWServiceException e) {
			throw new RemoteException(e);
		}
		
	}
	
	/**
	 * Servicio para autenticar un usuario en el sistema.
	 * 
	 * @param user usuario en el sistema.
	 * @param password contrasennia que correponde a un usuario.
	 * @return Mensaje de confirmacion.
	 */
	@Produces(MediaType.TEXT_PLAIN)
	@GET
	@Path("Autenticar")
	public String autenticar(@QueryParam("user") String user, @QueryParam("password") String password){
		
		try {
			return usuarioService.autenticarUsuario(user, password);	
			
			//con el objetoo session guardo el usuarrio.			
			//sesion.setAttribute("Usuario", user);
			
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		} catch (IWServiceException e) {
			throw new RemoteException(e);
		}
		
	}
	
	/**
	 * Servicio para obtener una lista de Clientes de la Base de Datos
	 * @return Lista de Clientes
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("ObtenerLista")
	public List<Usuario> obtener(){
		
		try {
			return usuarioService.obtener();
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		}
	}
	
	/**
	 * Servicio para obtener un unico usuario
	 * @param user user del usuario que se desea obtener
	 * @return El usuario que corresponde al user enviado como parametro
	 */
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("ObtenerUsuario")
	public Usuario obtener(@QueryParam("user") String user){
		
		try {
			return usuarioService.obtener(user);
		} catch (ExceptionDao e) {
			throw new RemoteException(e);
		} catch (IWServiceException e) {
			throw new RemoteException(e);
		}
	}	
	
}
