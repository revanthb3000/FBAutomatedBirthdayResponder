package org.rb.fb;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.List;

import org.rb.utils.ProxyWebRequestor;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultJsonMapper;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Post;
import com.restfb.types.User;

public class FBFunctions {

	public static String getUserName(String accessToken){
		Authenticator authenticator = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return (new PasswordAuthentication("b.revanth",
						"batman9903".toCharArray()));
			}
		};
		Authenticator.setDefault(authenticator);

		System.setProperty("http.proxySet", "true");
		System.setProperty("http.proxyHost", "202.141.80.22");
		System.setProperty("http.proxyPort", "3128");

		FacebookClient facebookClient = new DefaultFacebookClient(accessToken, new ProxyWebRequestor(), new DefaultJsonMapper());

		User user = facebookClient.fetchObject("me", User.class);

		return user.getName();
	}
	
	public static void thankOnFB(String accessToken){
		Authenticator authenticator = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return (new PasswordAuthentication("b.revanth",
						"batman9903".toCharArray()));
			}
		};
		Authenticator.setDefault(authenticator);

		System.setProperty("http.proxySet", "true");
		System.setProperty("http.proxyHost", "202.141.80.22");
		System.setProperty("http.proxyPort", "3128");

		FacebookClient facebookClient = new DefaultFacebookClient(accessToken, new ProxyWebRequestor(), new DefaultJsonMapper());
		Connection<Post> myFeed = facebookClient.fetchConnection("me/feed",
				Post.class);

		for (List<Post> myFeedConnectionPage : myFeed){
			for (Post post : myFeedConnectionPage){
				System.out.println("Post (by "+post.getFrom().getName()+" ): " + post.getMessage());
				if(post.getMessage().toLowerCase().contains("birth")){
					facebookClient.publish(post.getId() + "/comments", String.class, Parameter.with("message", "Thanks " + post.getFrom().getName() + " !"));
				}
			}	
		}		
	}
	
	private static FacebookClient.AccessToken getFacebookUserToken(String code, String redirectUrl) throws IOException {
	    String appId = AppConstants.APP_ID;
	    String secretKey = AppConstants.SECRET_KEY;

	    ProxyWebRequestor wr = new ProxyWebRequestor();
	    ProxyWebRequestor.Response accessTokenResponse = wr.executeGet(
	            "https://graph.facebook.com/oauth/access_token?client_id=" + appId + "&redirect_uri=" + redirectUrl
	            + "&client_secret=" + secretKey + "&code=" + code);

	    return DefaultFacebookClient.AccessToken.fromQueryString(accessTokenResponse.getBody());
	}
}
