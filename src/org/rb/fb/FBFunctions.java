package org.rb.fb;

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

	public static void setProxy() {
		Authenticator authenticator = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return (new PasswordAuthentication("username",
						"password".toCharArray()));
			}
		};
		Authenticator.setDefault(authenticator);

		System.setProperty("http.proxySet", "true");
		System.setProperty("http.proxyHost", "202.141.80.22");
		System.setProperty("http.proxyPort", "3128");
	}

	public static String getUserName(String accessToken) {
		setProxy();

		FacebookClient facebookClient = new DefaultFacebookClient(accessToken,
				new ProxyWebRequestor(), new DefaultJsonMapper());
		User user = facebookClient.fetchObject("me", User.class);
		return user.getName();
	}

	public static void thankOnFB(String accessToken) {
		setProxy();

		FacebookClient facebookClient = new DefaultFacebookClient(accessToken,
				new ProxyWebRequestor(), new DefaultJsonMapper());
		Connection<Post> myFeed = facebookClient.fetchConnection("me/feed",
				Post.class);

		for (List<Post> myFeedConnectionPage : myFeed) {
			for (Post post : myFeedConnectionPage) {
				System.out.println("Post (by " + post.getFrom().getName()
						+ " ): " + post.getMessage());
				if (post.getMessage().toLowerCase().contains("birth")) {
					facebookClient.publish(
							post.getId() + "/comments",
							String.class,
							Parameter.with("message", "Thanks "
									+ post.getFrom().getName() + " !"));
				}
			}
		}
	}

}
