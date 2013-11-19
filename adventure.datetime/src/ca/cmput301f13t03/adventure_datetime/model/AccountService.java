package ca.cmput301f13t03.adventure_datetime.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

/**
 * Class for interacting with a user's account
 */
public class AccountService {
	
	private static final String[] projection = { ContactsContract.Contacts.DISPLAY_NAME }; 
	
	private static String userName;
	
	private AccountService() {	}
	
	/**
	 * Returns a users display name from their contacts profile.
	 * @param context, for querying the user's contacts profile
	 * @return
	 */
	public static String getUserName(Context context) {
		// TODO: Should this remain static?
		
		if (userName == null) {
			setUserName(context);
		}
		
		return userName;
	}
	
	private static void setUserName(Context context) {
		ContentResolver cr = context.getContentResolver();
		Cursor c = cr.query(
				ContactsContract.Profile.CONTENT_URI, // Query only the user's profile 
				projection, // get display name
				null, // get everything
				null, // get everything
				null  // 1 item doesn't need ordering
				);
		
		if (c.moveToFirst()) {
			userName = c.getString(c.getColumnIndex(projection[0]));
		}
		
		c.close();
	}
	
}
