package clases;

public class Videojuego {

	private String id;
	private String nombre;
	private String plataforma;
	private String precio;

	
	public Videojuego(String id, String nombre, String plataforma, String precio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.plataforma = plataforma;
		this.precio = precio;
	
	}

	
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}


	
	
}
