package Controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import Managers.DataManager;

import static java.lang.Math.toIntExact;

import Models.ChatMessage;
import Models.Game;
import Models.ThreeSixNine;
import Utilities.ComponentUtility;
import View.ChatMessageView;
import View.ThreeSixNineView;


public class ChatController {

	private ChatMessage model;
	private ChatMessageView view;
	private Game parent;
	private Timer timer;
	
	public ChatController(Game parent) {
		
		this.parent = parent;
		view = new ChatMessageView();
		
		ComponentUtility.addActionListener(view, "btnSendMessage", (e) -> btnSendMessage_Press());

		timer = new Timer();
		timer.schedule(new PerformRepeatedTask(), 5000);
		
	}
	
	private void btnSendMessage_Press()
	{
		System.out.println("test");
		
		Date date = new Date();
		String playername = DataManager.getInstance().getCurrentUser().getName();
		
		// TODO Find out which playername gets used for this
		model = new ChatMessage(parent.getId(), 
				playername, 
				view.getMessage());
	
		model.send();
		
		updateChatMessages(); 
	}
	
	private void updateChatMessages()
	{
		ArrayList<ChatMessage> chatMessages = 
				DataManager.getInstance().getChatMessages(parent.getId());
		
		view.updateChatBehavior(chatMessages);
	}

	public ChatMessageView returnView(){
		return view;
	}
	

	class PerformRepeatedTask extends TimerTask {
	    public void run() {
	    	updateChatMessages();
	    }
	  }
	
	
	
}