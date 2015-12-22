package Controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

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
	
	public ChatController(Game parent) {
		
		this.parent = parent;
		view = new ChatMessageView();
		
		ComponentUtility.addActionListener(view, "btnSendMessage", (e) -> btnSendMessage_Press());

	}
	
	private void btnSendMessage_Press()
	{
		//model.addObserver(view);
		
		Date date = new Date();
		int milisecs = toIntExact(new Timestamp(date.getTime()).getTime());
		String playername = DataManager.getInstance().getCurrentUser().getName();
		
		// TODO Find out which playername gets used for this
		model = new ChatMessage(parent.getId(), 
				new Timestamp(date.getTime()), 
				milisecs, 
				playername, view.getMessage());
	
		model.send();
		
		updateChatMessages(); 
	}
	
	private void updateChatMessages()
	{
		ArrayList<ChatMessage> chatMessages = 
				DataManager.getInstance().getChatMessages(parent.getId());
	}
	
	public ChatMessageView returnView(){
		return view;
	}
}
