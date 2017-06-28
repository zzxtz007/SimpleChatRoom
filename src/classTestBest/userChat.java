package classTestBest;

import java.io.Serializable;
import java.util.Date;

public class userChat implements Serializable{
	private String chatname;
	private int flag;
	private String say;
	private Date date;
	
	public userChat() {
		super();
	}

	public userChat(String chatname, String say) {
		super();
		this.chatname = chatname;
		this.say = say;
	}
	
	@Override
	public int hashCode() {
		return chatname.hashCode()*35+say.hashCode();
	}

	@Override
	public String toString() {
		return chatname + " : " +date +" : "+"say:\n\t" + say ;
	}

	public String getChatname() {
		return chatname;
	}

	public void setChatname(String chatname) {
		this.chatname = chatname;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getSay() {
		return say;
	}

	public void setSay(String say) {
		this.say = say;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
