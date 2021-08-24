<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>CRUD Videojuegos</title>



<script type="text/javascript" src="js/jquery-1.12.4.min.js"></script>	
<script type="text/javascript">

	function load(id,nombre,plataforma,precio){
	
		var entry = document.createElement('li');
		entry.id=id;
		
		var a = document.createElement('a');
		var b = document.createElement('a');
		b.setAttribute("id", id);
		
		
		var btnPut = document.createElement("BUTTON");
		btnPut.innerHTML = "Modificar";
		var btnDelete = document.createElement("BUTTON");
		btnDelete.innerHTML = "Borrar";
		b.appendChild(btnPut);
		a.appendChild(btnDelete);
		
		
		a.onclick = function () {
			$.ajax({
			    url: 'rest/juego/' + id,
			    type: 'DELETE',
			    dataType: "json",
			    success: function(result) {
	
			    	document.getElementById(id).remove();
			    	
			    }
			});
			
			location.reload();
		};
		
		//Función MODIFICAR
		b.onclick = function () {
			$('#id').val(id);
 			$('#nombre').val(nombre);
 			$('#plataforma').val(plataforma);
 			$('#precio').val(precio);
 			$('#sendButton').text("Modificar");
		};
		
		entry.appendChild(document.createTextNode(id + " " + nombre + " " + plataforma + " " + precio));
		entry.appendChild(a);
		entry.appendChild(b);
		
		$('#games').append(entry);
	}
	

	$(document).ready(function(){
			
			$("#sendButton").click(function(){
				
				var sendInfo = {id: $('#id').val(),nombre: $('#nombre').val(),plataforma: $('#plataforma').val(),precio: $('#precio').val()};
				
				if($('#sendButton').text() == "Crear"){
					$.ajax({
						url : 'rest/juego', 
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : 'POST',
						dataType : "json",
						success : function(result) {
							load(result.juego.id, result.juego.nombre, result.juego.plataforma, result.juego.precio); 
						},
						error : function(result) {
							alert('Error');
						},
						data : JSON.stringify(sendInfo)
					});

					
				}else if($('#sendButton').text() == "Modificar"){
					$.ajax({
						url : 'rest/juego/' + id,
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : 'PUT',
						dataType : "json",
						success : function(result) {										
							load(result.juego.id, result.juego.nombre, result.juego.plataforma, result.juego.precio);
						},
						error : function(result) {
							alert('Error');
						},
						data : JSON.stringify(sendInfo)
					});
					$('#sendButton').text("Crear Videojuego");
		 			
		 			location.reload();
				}
			
			    });
			
			$.ajax({
			    url: 'rest/juego',
			    type: 'GET',
			    dataType: "json",
			    success: function(result) {
			    	
			    	jQuery.each(result.juegos, function(i, val) { 
			    		  load(val.id, val.nombre, val.plataforma, val.precio);
			    		});
			    }
			});
		});
		
	</script>
</head>


<body>

<h2> Videojuegos</h2><br>
ID: <input type=text id="id"><br><hr>
Nombre: <input type=text id="nombre"><br><hr>
Plataforma: <input type=text id="plataforma"><br><hr>
Precio:	<input type=text id="precio"><br><br>
<button id="sendButton">Crear</button>

<br><br>
Listado de Videojuegos:

<br> <ul id="games"></ul>
<br>

</body>
</html>