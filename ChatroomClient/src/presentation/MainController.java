package presentation;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;

import dataInterface.ChatRoom;
import dataInterface.ChatUser;
import entities.Client;
import entities.Message;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

@SuppressWarnings("serial")
public class MainController implements Initializable,ChatUser,Serializable {
	
	private final String SEVER_LOCATION = "localhost";
	
	@FXML
    private Pane suscribe_pan;

	@FXML
    private TextField clientPseudo;

    @FXML
    private Pane room;

    @FXML
    private TextArea message;

    @FXML
    private TextArea response;
    
    private ChatUser chatUser;
    private ChatRoom chatRoom;
    Client client;
    Message clientMessage;
    ChatUser serveurResponse;
    private final String ERROR = "Erreur";
    private final String ERROR_MESSAGE = "Une erreur est survenue du côté serveur" ;

    public MainController() {

    	try {
			this.chatRoom = (ChatRoom) Naming.lookup("rmi://"+SEVER_LOCATION+":2020/service");
			this.chatUser = (ChatUser) UnicastRemoteObject.exportObject(this, 0);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void sendText(MouseEvent event) {
    	clientMessage = new Message(message.getText().trim(), client.getPseudo());
    	try {
			chatRoom.postMessage(clientMessage);
			message.setText("");
			displayMessage(clientMessage);
		} catch (RemoteException e) {
			showAlert(ERROR, e.getMessage());
		}
    }

    @FXML
    void subscribe(MouseEvent event) {
    	this.client = new Client(clientPseudo.getText());
    	try {
			String token = this.chatRoom.subscribe(this.chatUser, client);
			switch (token) {
			case "exists":
				this.showAlert(ERROR, "Quelqu'un est déjà connecté avec ce pseudo");
				break;
			case "none":
				this.showAlert(ERROR, ERROR_MESSAGE);
				break;
			default:
				this.client.setToken(token);
				suscribe_pan.setVisible(false);
				room.setDisable(false);
				response.setVisible(true);
				
				break;
			}
		} catch (RemoteException e) {
			this.showAlert(ERROR, e.getMessage());
		}

    }
    
    @FXML
    void unsubscribe(MouseEvent event) {
    	try {
			chatRoom.unsubscribe(client);
			this.client.setToken(null);
			suscribe_pan.setVisible(true);
			room.setDisable(true);
			response.setVisible(false);
		} catch (RemoteException e) {
			this.showAlert(ERROR, e.getMessage());
		}

    }
    
    @FXML
    void quit(MouseEvent event) {
    	if(client.getPseudo() != null) {
    		try {
    			chatRoom.unsubscribe(client);
    			Platform.exit();
				System.exit(0);
    			
    		} catch (RemoteException e) {
    			this.showAlert(ERROR, e.getMessage());
    		}
    	}
    	else {
    		Platform.exit();
			System.exit(0);
    	}
    }
    
    public void showAlert(String title,String message) {
    	Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
    }
    
    @Override
	public void displayMessage(Message message) throws RemoteException {
		if(message.getPseudoClient().equals(client.getPseudo())) {
			response.appendText("Me : "+message.getMessage()+"\n");
		}
		else {
			Platform.runLater(() -> {
				response.appendText(message.getPseudoClient()+" : "+message.getMessage()+"\n");
			});
			
		}
	}

}
