package chatroom;


public class calculation
{


	/**
	 * Create the chatroom
	 *
	 * @param args
	 */
	public static int strtoint (String arg){
		int ret =0;
		int len = arg.length();
		double tenpower = Math.pow(10,(len-1));
		for (int i = 0; i<arg.length(); i++) {
			char c = arg.charAt(i);
			ret += (c-'0') *tenpower;
			tenpower = tenpower /10;
		}
		return ret;
	}
}
