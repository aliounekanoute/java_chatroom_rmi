# java_chatroom_rmi
A chatroom App with rmi/ Une application de chatroom avec rmi 

* **Français**

  On a deux dossiers :
  * Le dossier ChatRoom, qui contient le code du serveur
  * Le dossier ChatRoomClient; qui contient le du client


  Configurer le build path.


  Démarrer le serveur.

  Dans le code du client, ouvrir le fichier MainController.java qui se trouve dans src->presentation, spécifier l'adresse IP de la machine qui contient l'application serveur dans la constante "SEVER_LOCATION".

  Démarrer plusieurs clients sur différentes machines.

  **Si un client n'arrive pas à joindre le serveur, c'est que :**
  * **soit le la machine contenant l'application serveur n'accepte pas les requêtes venant des autres machines**
  * **soit la machine client n'accepte pas les requêtes venant des autres machines**
  
  
  
  
* **English**

  We have two directories :
  * The directory ChatRoom which contains the server code
  * The directory ChatRoomClient which contains the client code
  
  
  Configure the build path.


  Start the serveur.
  
  In the client code, open the MainController.java file located in src-> presentation, specify the IP address of the machine containing the server application in the constant "SEVER_LOCATION".

  Start multiple clients on different machines.


  **If a client cannot reach the server, it is because:**
  * **either the machine containing the server application does not accept requests from other machines**
  * **either the client machine does not accept requests from other machines**
