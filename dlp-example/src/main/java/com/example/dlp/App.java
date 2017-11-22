package com.example.dlp;

import com.google.cloud.dlp.v2beta1.DlpServiceClient;
import com.google.privacy.dlp.v2beta1.ContentItem;
import com.google.privacy.dlp.v2beta1.Finding;
import com.google.privacy.dlp.v2beta1.InfoType;
import com.google.privacy.dlp.v2beta1.InspectConfig;
import com.google.privacy.dlp.v2beta1.InspectContentRequest;
import com.google.privacy.dlp.v2beta1.InspectContentResponse;
import com.google.privacy.dlp.v2beta1.InspectResult;
import com.google.privacy.dlp.v2beta1.Likelihood;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.Plus.Builder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;


public class App {

	public static void main(String[] args) throws IOException {

		System.out.println("111111111111111111111111111111111");
		/**
		String accessToken="ya29.c.El8MBUrRoc6Lv4NwP6Cc_yrjV6g8FQh3Z-prYmkO6bJgakTUOMyxDRS211P3EdjyBxPjeFQephb5Qh6n_AHgIWFCWn7gaKCcKBB_JfnJmoJxeH0mVmCPIa2ebFYT9rnzHA";
		GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
		Plus plus = new Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
		    .setApplicationName("dlp-redaction-poc")
		    .build();
		**/
		System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
		
		    // string to inspect
		    String text = "Robert Frost";

		    // The minimum likelihood required before returning a match:
		    // LIKELIHOOD_UNSPECIFIED, VERY_UNLIKELY, UNLIKELY, POSSIBLE, LIKELY, VERY_LIKELY, UNRECOGNIZED
		    Likelihood minLikelihood = Likelihood.VERY_LIKELY;

		    // The maximum number of findings to report (0 = server maximum)
		    int maxFindings = 0;

		    // The infoTypes of information to match
		    List<InfoType> infoTypes =
		        Arrays.asList(
		            InfoType.newBuilder().setName("US_MALE_NAME").build(),
		            InfoType.newBuilder().setName("US_FEMALE_NAME").build());

		    // Whether to include the matching string
		    boolean includeQuote = true;

		    // instantiate a client
		    try {
		     DlpServiceClient dlpServiceClient = DlpServiceClient.create();
		      InspectConfig inspectConfig =
		          InspectConfig.newBuilder()
		              .addAllInfoTypes(infoTypes)
		              .setMinLikelihood(minLikelihood)
		              .setMaxFindings(maxFindings)
		              .setIncludeQuote(includeQuote)
		              .build();

		      ContentItem contentItem =
		          ContentItem.newBuilder().setType("text/plain").setValue(text).build();

		      InspectContentRequest request =
		          InspectContentRequest.newBuilder()
		              .setInspectConfig(inspectConfig)
		              .addItems(contentItem)
		              .build();

		      // Inspect the text for info types
		      InspectContentResponse response = dlpServiceClient.inspectContent(request);

		      // Print the response
		      for (InspectResult result : response.getResultsList()) {
		        if (result.getFindingsCount() > 0) {
		          System.out.println("Findings: ");
		          for (Finding finding : result.getFindingsList()) {
		            if (includeQuote) {
		              System.out.print("Quote: " + finding.getQuote());
		            }
		            System.out.print("\tInfo type: " + finding.getInfoType().getName());
		            System.out.println("\tLikelihood: " + finding.getLikelihood());
		          }
		        } else {
		          System.out.println("No findings.");
		        }
		      }
		    } catch (Exception e) {
		      System.out.println("Error in inspectString: " + e.getMessage());
		    }
		  }

	/**
	 private static Credential authorize() throws Exception {
	   // load client secrets
	   GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
	       new InputStreamReader(CalendarSample.class.getResourceAsStream("/client_secrets.json")));
	   // set up authorization code flow
	   GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	       httpTransport, JSON_FACTORY, clientSecrets,
	       Collections.singleton(CalendarScopes.CALENDAR)).setDataStoreFactory(dataStoreFactory)
	      .build();
	   // authorize
	   return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
	}
**/
}