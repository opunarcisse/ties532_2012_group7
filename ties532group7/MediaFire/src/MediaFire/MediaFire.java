package MediaFire;


import java.io.IOException;
import java.io.StringReader;
import java.net.URI;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request; 
import org.xml.sax.InputSource;

//import com.sun.org.apache.xpath.*;

public class MediaFire 
{
	public static final String MF_GET_SESSION_TOKEN = "https://www.mediafire.com/api/user/get_session_token.php";
	public static final String MF_GET_USER_INFO = "http://www.mediafire.com/api/user/get_info.php?session_token=";
	private static final char[] UserInfo = null; 
	private String applicationID;
	private String apiKey;
	private String session_token;
	
	
	public MediaFire(String applicationID, String apiKey) 
	{
		this.applicationID = applicationID;
		this.apiKey = apiKey;

		System.out.println("apiKey: " + this.applicationID);
		System.out.println("applicationID: " + this.apiKey);
	}
	
	public void connect(String email, String password) throws ClientProtocolException, IOException
	{
		String signature = new String(DigestUtils.shaHex(email + password + applicationID + apiKey));
		String params = "?email=" + email + "&password=" + password + "&application_id=" + applicationID + "&signature=" + signature + "&response_format=xml&version=1";
		String url = MF_GET_SESSION_TOKEN + params;		
		
		URI address = URI.create(url);
		Content response = Request.Get(url).execute().returnContent();

		XPathFactory factory = XPathFactory.newInstance();
		XPath path = factory.newXPath();
		try {
			session_token = path.evaluate("/response/session_token", new InputSource(new StringReader(response.asString())));
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(session_token);
	}
	
	public UserInfo getinfo() throws ClientProtocolException, IOException
	{
		UserInfo user = new UserInfo();
		
		String url = MF_GET_USER_INFO + session_token+"&version=1";		
		URI address = URI.create(url);
		System.out.println(address);
		Content response = Request.Get(url).execute().returnContent();
		System.out.println(response.asString());
		
		XPathFactory factory = XPathFactory.newInstance();
		XPath path = factory.newXPath();
		try {
			user.setEmail(path.evaluate("/response/user_info/email", new InputSource(new StringReader(response.asString()))));
			user.setFirst_name(path.evaluate("/response/user_info/first_name", new InputSource(new StringReader(response.asString()))));
			user.setLast_name(path.evaluate("/response/user_info/last_name", new InputSource(new StringReader(response.asString()))));
			user.setDisplay_name(path.evaluate("/response/user_info/display_name", new InputSource(new StringReader(response.asString()))));
			user.setGender(path.evaluate("/response/user_info/gender", new InputSource(new StringReader(response.asString()))));
			user.setBirth_date(path.evaluate("/response/user_info/birth_date", new InputSource(new StringReader(response.asString()))));
			user.setLocation(path.evaluate("/response/user_info/location", new InputSource(new StringReader(response.asString()))));
			user.setWebsite(path.evaluate("/response/user_info/website", new InputSource(new StringReader(response.asString()))));
			user.setPremium(path.evaluate("/response/user_info/premium", new InputSource(new StringReader(response.asString()))));
			user.setBandwidth(path.evaluate("/response/user_info/bandwidth", new InputSource(new StringReader(response.asString()))));
			user.setCreated(path.evaluate("/response/user_info/created", new InputSource(new StringReader(response.asString()))));			
			user.setValidated(Boolean.parseBoolean(path.evaluate("/response/user_info/validated", new InputSource(new StringReader(response.toString())))));
			
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;
	}
	
}
