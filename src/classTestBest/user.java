package classTestBest;

import java.io.Serializable;

public class user implements Serializable {
	private String name;
	private String password;
	//private String say;
	private int flag = 0;

	public user(String name, String password ) {
		super();
		this.name = name;
		this.password = password;
		//this.say = say;
	}
	
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public user(){
		this.name = "test";
		this.password = "test";
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/*public String getSay() {
		return say;
	}

	public void setSay(String say) {
		this.say = say;
	}*/

	@Override
	public String toString() {
//		return "user [name=" + name + ", password=" + password + ", say=" + say + "]";
		return "user [name=" + name + ", password=" + password + "]";
	}
	
}
