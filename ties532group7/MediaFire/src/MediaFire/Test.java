package MediaFire;


import java.io.IOException;

import MediaFire.MediaFire;


public class Test 
{
	public static void main(String[] args) 
	{
		MediaFire mediafire = new MediaFire("apiKey", "applicationID"); 
		
		try 
		{
			mediafire.connect("email adress", "password");
			
			UserInfo user = new UserInfo();
			user = mediafire.getinfo();
			System.out.println(user);
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
