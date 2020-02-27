package presentation;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import dataInterface.ChatRoom;
import dataInterface.ChatUser;
import entities.Client;
import entities.Message;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import utils.TokenGenerator;

public class MainController implements Initializable,ChatRoom {

	@FXML
    private Button start_button;

    @FXML
    private Button off_button;

    @FXML
    private TextArea text_log;
    
    private Map<Client, ChatUser> list;

	public MainController() throws RemoteException {
		super();
		list = new HashMap<Client, ChatUser>();
	}

    @FXML
    void powerOff(MouseEvent event) {
    	try {
			Naming.unbind("rmi://localhost:2020/service");
			text_log.appendText("\nLe serveur est eteint");
			off_button.setVisible(false);
			start_button.setVisible(true);
		} catch (Exception e) {
			text_log.appendText(e.getMessage());
		}

    }

    @FXML
    void start(MouseEvent event) {
    	try {
    		InetAddress inetAdress = InetAddress.getLocalHost();
			ChatRoom chatRoom = (ChatRoom) UnicastRemoteObject.exportObject(this,0);
			
			LocateRegistry.createRegistry(2020);
			Naming.rebind("rmi://localhost:2020/service", chatRoom);
			text_log.appendText("\nLe serveur a demarré");
    		text_log.appendText("\nMon adresse IP : "+inetAdress.getHostAddress());
			start_button.setVisible(false);
			off_button.setVisible(true);
		} catch (RemoteException e) {
			text_log.appendText("\n"+e.getMessage());
		} catch (MalformedURLException e) {
			text_log.appendText("\n"+e.getMessage());
		} catch (UnknownHostException e) {
			text_log.appendText("\n"+e.getMessage());
		}
    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	
		text_log.appendText("\nCliquez sur Demarrer pour demarrer le Serveur");
    		
	}
    
    public void displayLog(String log) {
//    	Platform.runLater(() -> {
//    		text_log.appendText("\n"+log);
//		});
    	text_log.appendText("\n"+log);
    	
    }
    
    
	private boolean exists(Client client) {
		boolean response = false;
		String pseudo;
		for(Map.Entry<Client, ChatUser> entry : list.entrySet()) {
			pseudo = entry.getKey().getPseudo();
			
			if(pseudo.equals(client.getPseudo())) {
				response = true;
				break;
			}
		}
		
		return response;
	}

	@Override
	public String subscribe(ChatUser chatUser, Client client) throws RemoteException {
		String token = "none";
		
		if(exists(client)) {
			token = "exists";
		}
		else {
			list.put(client, chatUser);
			token = new TokenGenerator().getToken();
			displayLog(client.getPseudo() + " s'est connecté");
		}
		
		return token;
	}

	@Override
	public void unsubscribe(Client client) throws RemoteException {
		for(Map.Entry<Client, ChatUser> map : list.entrySet()) {
			if(map.getKey().getPseudo().equals(client.getPseudo())) {
				list.remove(map.getKey());
				displayLog(map.getKey().getPseudo()+" s'est deconnecté");
				break;
			}
		}
	}

	@Override
	public void postMessage(Message message) throws RemoteException {
		
		displayLog(message.getPseudoClient()+" a posté : " + message.getMessage());
		ChatUser chatUser;
		Client client;
		for(Map.Entry<Client, ChatUser> entry : list.entrySet()) {
			client = entry.getKey();
			chatUser = entry.getValue();
			
			if(!client.getPseudo().equals(message.getPseudoClient())) {
				chatUser.displayMessage(message);
				
				displayLog(client.getPseudo() + " a été notifié");
			}
		}
	}
}
