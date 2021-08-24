package servicios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import clases.Videojuego;


@Path("/juego")
public class servicioREST {

	@DefaultValue("") @QueryParam("token") String token;
	public static Hashtable<String,Videojuego> games = new Hashtable<>();
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		
		System.out.print("Ejecutando getAll");
		
		JSONObject jsonRespuesta = new JSONObject();
		
		for (Videojuego game : games.values()) {
			JSONObject jsonGame = new JSONObject();
			
			jsonGame.put("id", game.getId());
			jsonGame.put("nombre", game.getNombre());
			jsonGame.put("plataforma", game.getPlataforma());
			jsonGame.put("precio", game.getPrecio());
			
			jsonRespuesta.append("juegos",jsonGame);
		}
		
		System.out.println(jsonRespuesta.toString());
		return Response.status(200).entity(jsonRespuesta.toString()).build();
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addGame(InputStream incomingData) {
		
		System.out.print("AddGame en proceso");
		
		StringBuilder sb = new StringBuilder();
		
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error");
		}
		
		JSONObject jsonRecibido = new JSONObject(sb.toString());
		
		Videojuego game = new Videojuego(jsonRecibido.getString("id"),jsonRecibido.getString("nombre"),jsonRecibido.getString("plataforma"),jsonRecibido.getString("precio"));
		
		JSONObject jsonRespuesta = new JSONObject();
		JSONObject jsonGame = new JSONObject();
		
		jsonGame.put("id", game.getId());
		jsonGame.put("nombre", game.getNombre());
		jsonGame.put("plataforma", game.getPlataforma());
		jsonGame.put("precio", game.getPrecio());
		
		jsonRespuesta.put("juego", jsonGame);
		games.put(game.getId(), game);
		
		System.out.print(jsonRespuesta.toString());
		
		return Response.status(201).entity(jsonRespuesta.toString()).build(); 
	}
	
	
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateGame(@PathParam("id") String id, InputStream incomingData) {
		
		System.out.print("updateGame en proceso");
		
		JSONObject jsonRespuesta = new JSONObject();
		String jsonString = "";
		
		BufferedReader br = new BufferedReader(new InputStreamReader(incomingData));
		try {
			jsonString = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JSONObject jsonRecibido = new JSONObject(jsonString);
		
		Videojuego game = new Videojuego(jsonRecibido.getString("id"),jsonRecibido.getString("nombre"),jsonRecibido.getString("plataforma"),jsonRecibido.getString("precio"));
		
		
		
		games.put(game.getId(), game);
		jsonRespuesta.put("juego", jsonRespuesta);
		
		
		
		return Response.ok().entity(jsonRespuesta.toString()).build();
		
	}
	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteGame(@PathParam("id") String id) {
		
		System.out.print("deleteGame en proceso");
		
		Videojuego game = games.get(id);
		
		System.out.println(game.getId() + game.getNombre());
		
		games.remove(game.getId());

		JSONObject jsonRespuesta = new JSONObject();
		jsonRespuesta.append("resultado", "Borrado"); 
		
		return Response.status(204).header("Access-Control-Allow-Origin", "*").entity(jsonRespuesta.toString()).build();
	}
}
