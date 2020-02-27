package dataInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

import entities.Client;
import entities.Message;

public interface ChatRoom extends Remote {
	
	public String subscribe(ChatUser chatUser, Client client) throws RemoteException;
	
	public void unsubscribe(Client client) throws RemoteException;
	
	public void postMessage(Message message) throws RemoteException;

}
