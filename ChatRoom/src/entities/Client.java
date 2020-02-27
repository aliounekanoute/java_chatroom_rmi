package entities;

public class Client extends Inherit {

	private static final long serialVersionUID = 1L;
	
	private String pseudo;
	
	private String token;
	
	public Client() {}
	
	public Client(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

}
