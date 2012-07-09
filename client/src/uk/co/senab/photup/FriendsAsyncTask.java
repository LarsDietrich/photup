package uk.co.senab.photup;

import java.lang.ref.WeakReference;
import java.util.List;

import uk.co.senab.photup.facebook.FacebookRequester;
import uk.co.senab.photup.model.FbUser;
import android.content.Context;
import android.os.AsyncTask;

public class FriendsAsyncTask extends AsyncTask<Void, Void, List<FbUser>> {

	public static interface FriendsResultListener {
		public void onFriendsLoaded(List<FbUser> friends);
	}

	private final WeakReference<Context> mContext;
	private final WeakReference<FriendsResultListener> mListener;

	public FriendsAsyncTask(Context context, FriendsResultListener listener) {
		mContext = new WeakReference<Context>(context);
		mListener = new WeakReference<FriendsResultListener>(listener);
	}

	@Override
	protected List<FbUser> doInBackground(Void... params) {
		Context context = mContext.get();
		if (null != context) {
			FacebookRequester requester = new FacebookRequester(context);
			return requester.getFriends();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(List<FbUser> result) {
		super.onPostExecute(result);
		
		FriendsResultListener listener = mListener.get();
		if (null != listener) {
			listener.onFriendsLoaded(result);
		}
	}

}