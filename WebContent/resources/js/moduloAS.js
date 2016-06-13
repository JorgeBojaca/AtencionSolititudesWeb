var app = angular.module('ModAS', [ 'ngRoute' ]);

var texto;


/*
 * Configura las vistas del aplicativo
 */
app.config([ '$routeProvider', function($routeProvider) {

	$routeProvider.when('/', {
		templateUrl : 'login.html',
		controller : 'contLogin'
	})
	$routeProvider.when('/responderSol', {
		templateUrl : 'responderSolicitud.html',
		controller : 'contResponder'
	})
	
	$routeProvider.when('/solicitud',{
		templateUrl: 'solicitud.html',
		controller: 'contSolicitud'
	})
} ]);

/*
 * Controlador para manejar las solicitudes, se le inyecta el servicio Solicitudes
 */
app.controller('contSolicitud',function(Solicitudes,$scope,$location){
	
	$scope.solicitud={
			descripcion: '',
			tipoSolicitud:'',
			cliente:'',
			producto:'',
			idSucursal:''	
	};
	$scope.validar=function(){
		texto=solicitud.tipo;
		alert(texto);
	}
	$scope.guardar=function(){
		Solicitudes.guardarSolicitud($scope.solicitud).success(function(data){
			alert(data);
			$location.url('/solicitud');
			
		})
	}
	
})

/*
 * Controlador para manejar el formulario de autenticacion
 */
app.controller('contLogin', function(Autenticacion, $scope, $location) {
	$scope.usuario = '';
	$scope.pwd = '';
	/**
	 * Funcion para llamar el servicio de autenticar usuario y contraseña
	 */
	$scope.autenticar = function() {
		Autenticacion.autenticarUser($scope.usuario, $scope.pwd).success(
				function(data) {
					if (data != '') {	
						//alert(data);
						if ((data == 'gerente') || (data == 'administrador')) {
							alert(data);
							$location.url('/responderSol');
						}else if (data == cliente) {
							$location.url('/solicitud');
						}else{
							alert(data);
						}
						$scope.usuario = '';
						$scope.pwd = '';
						return;
					}
				})

	}
});

/*
 * Controlador para manejar el formulario de Respuesta a solicitudes
 */
app.controller('contResponder', function(Solicitudes, $scope, $location) {
	$scope.mostrar=false;
	$scope.respuesta={
			idSol:'',
			descripcion:''
	}
	Solicitudes.obtenerTodas().success(function(data) {		
		$scope.solicitudes = data.solicitudDTOws;
	});
	$scope.mostrarSolicitudes = function() {
		if($scope.mostrar==true){
			$scope.mostrar=false;
		}else{
			$scope.mostrar=true;
		}
	}
	$scope.guardarRespuesta=function(){
		Solicitudes.responderSolicitud($scope.respuesta).success(function(data){
			alert(data);
		})
		
	}

});

/*
 * Servicio de Angular
 * Encargado de hacer los llamados de los servicios Web para Autenticar un usuario
 */
app.service('Autenticacion',function($http) {
					this.autenticarUser = function(user, password) {
						return $http({
							method : 'GET',
							url : 'http://localhost:8080/AtencionSolicitudesWeb/rest/Usuario/Autenticar',
							params : {
								user : user,
								password : password
							}
						});
					}
				});

/*
 * Servicio de Angular
 * Encargado de hacer los llamados de los servicios Web para manejar las Solicitudes
 */
app.service('Solicitudes',function($http) {
					this.obtenerTodas = function() {
						return $http({
							method : 'GET',
							url : 'http://localhost:8080/AtencionSolicitudesWeb/rest/Solicitud',
							params : {
								user : 'bojaca'
							}
						});
					}
					this.guardarSolicitud = function(solicitud) {
						return $http({
							method : 'POST',
							url : 'http://localhost:8080/AtencionSolicitudesWeb/rest/Solicitud/Guardar',
							params : {
								descripcion: solicitud.descripcion,
								tiposolicitud: solicitud.tipo,
								cliente:'milena',
								producto: solicitud.producto,
								idSucursal:''
							}
						});
					}
					this.responderSolicitud=function(respuesta){
						return $http({
							method: 'PUT',
							url:'http://localhost:8080/AtencionSolicitudesWeb/rest/Solicitud/ResponderSolicitud',
							params:{
								idSolicitud: respuesta.idSol,
								respuesta: respuesta.descripcion,
								responsable: 'bojaca'
							}
								
						});
					}
				});







