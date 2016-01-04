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
import Models.GameInfo;
import Models.MyTimer;
import Models.ThreeSixNine;
import Utilities.ComponentUtility;
import View.ChatMessageView;
import View.ThreeSixNineView;


public class ChatController {

	private ChatMessage model;
	private ChatMessageView view;
	private GameInfo parent;
	//private Timer timer;
	
	private TimerTask timer;
	
	public ChatController(GameInfo parent) {
		
		this.parent = parent;
		view = new ChatMessageView();
		
		ComponentUtility.addActionListener(view, "btnSendMessage", (e) -> btnSendMessage_Press());

		timer = new MyTimer().schedule(() -> updateChatMessages(), 1000);
	}
	
	private void btnSendMessage_Press()
	{

		Date date = new Date();
		String playername = DataManager.getInstance().getCurrentUser().getName();
		
		// TODO Find out which playername gets used for this
		model = new ChatMessage(parent.getGameId(), 
				playername, 
				view.getMessage());
	
		model.send();
		
		
		
		updateChatMessages(); 
		
		view.clearMessageBox();
	}
	
	private void updateChatMessages()
	{
		ArrayList<ChatMessage> chatMessages = 
				DataManager.getInstance().getChatMessages(parent.getGameId());
		
		view.updateChatBehavior(chatMessages);
	}

	public ChatMessageView returnView(){
		return view;
	}
	
}
