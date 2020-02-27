package entities;

public class Message extends Inherit {

	private static final long serialVersionUID = 1L;
	
	private String message;
	
	private String pseudoClient;
	
	public Message() {}
	
	public Message(String message,String pseudoClient) {
		this.message = message;
		this.pseudoClient = pseudoClient;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPseudoClient() {
		return pseudoClient;
	}

	public void setPseudoClient(String pseudoClient) {
		this.pseudoClient = pseudoClient;
	}
	
}
