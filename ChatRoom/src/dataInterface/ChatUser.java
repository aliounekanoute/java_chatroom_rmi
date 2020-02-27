package dataInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

import entities.Message;

public interface ChatUser extends Remote {

	public void displayMessage(Message message) throws RemoteException;
}
