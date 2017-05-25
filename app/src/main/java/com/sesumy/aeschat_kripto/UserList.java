package com.sesumy.aeschat_kripto;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sesumy.aeschat_kripto.custom.CustomActivity;
import com.sesumy.aeschat_kripto.model.ChatUser;
import com.sesumy.aeschat_kripto.model.Key;
import com.sesumy.aeschat_kripto.utils.Const;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Class UserList is the Activity class. It shows a list of all users of
 * this app. It also shows the Offline/Online status of users.
 */
public class UserList extends CustomActivity
{
    static BigInteger usKey;
	/** Users database reference */
	DatabaseReference database;
	BigInteger prime=new BigInteger("8690333381690951");
	/** The Chat list. */
	private ArrayList<ChatUser> uList;

	FirebaseDatabase fbData;
	/** The user. */
	public static ChatUser user;
    private BigInteger karsiKey = BigInteger.valueOf(0);


    /* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_list);
		// Get reference to the Firebase database
		database  = FirebaseDatabase.getInstance().getReference();
        fbData=FirebaseDatabase.getInstance ();
		getActionBar().setDisplayHomeAsUpEnabled(false);
		updateUserStatus(true);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onDestroy()
	 */
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		updateUserStatus(false);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume()
	{
		super.onResume();
		loadUserList();

	}

	/**
	 * Update user status.
	 * 
	 * @param online
	 *            true if user is online
	 */
	private void updateUserStatus(boolean online)
	{
		// TODO: Add user status updates
    }

	/**
	 * Load list of users.
	 */
	private void loadUserList()
	{
		final ProgressDialog dia = ProgressDialog.show(this, null, getString(R.string.alert_loading));
        // Pull the users list once no sync required.
        database.child("users").addListenerForSingleValueEvent(new ValueEventListener () {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {dia.dismiss();
                long size  = dataSnapshot.getChildrenCount();
                if(size == 0) {
					Toast.makeText(UserList.this,
							R.string.msg_no_user_found,
							Toast.LENGTH_SHORT).show();
					return;
				}
                uList = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    ChatUser user = ds.getValue(ChatUser.class);
                    Logger.getLogger(UserList.class.getName()).log(Level.ALL,user.getUsername());
                    if(!user.getId().contentEquals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                        uList.add(user);
                }
                ListView list = (ListView) findViewById(R.id.list);
                list.setAdapter(new UserAdapter());
                list.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, final int pos, long arg3)
                    {

						DatabaseReference dbKullanıcıBulKeyAl=fbData.getReference ("keys");
						dbKullanıcıBulKeyAl.addListenerForSingleValueEvent (new ValueEventListener ( ) {
							@Override
							public void onDataChange(DataSnapshot dataSnapshot) {
								for (DataSnapshot gelenVeri:dataSnapshot.getChildren ()){
									if(Objects.equals (uList.get (pos).getId ( ), gelenVeri.getValue (Key.class).keyUserid)){
									Log.i (uList.get (pos).getId ( ),"Aldığımız Kullanıcı ");
									//Kendi keyi ile işlem yapıyor.
										String karsiTarafKey=gelenVeri.getValue (Key.class).key;
										Log.i ("Diğer kişinin keyi", karsiTarafKey);
										Log.i ("User Id", uList.get (pos).getId ( ));
										 karsiKey=BigInteger.valueOf (Long.valueOf (karsiTarafKey));
										SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
										int myKey = preferences.getInt ("userKey", -1);
                                        usKey=  (karsiKey.pow(myKey)).mod (prime);
                                        System.out.println ("Kullanılacak key "+ usKey);

                                        //KENDİ A SI İLE ŞİFRELEYİP GÖNDERECEK DAHA SONRASINDA B İLE AÇACAK VE ŞİFREYİ BULACAK
									}
									System.out.print (gelenVeri.getValue (Key.class));

								}
							}

							@Override
							public void onCancelled(DatabaseError databaseError) {

							}
						});


                        startActivity(new Intent(UserList.this, Chat.class).putExtra(Const.EXTRA_DATA,  uList.get(pos)));
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
	}

	/**
	 * The Class UserAdapter is the adapter class for User ListView. This
	 * adapter shows the user name and it's only online status for each item.
	 */
	private class UserAdapter extends BaseAdapter
	{

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount()
		{
			return uList.size();
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public ChatUser getItem(int arg0)
		{
			return uList.get(arg0);
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int arg0)
		{
			return arg0;
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getView(int pos, View v, ViewGroup arg2)
		{
			if (v == null)
				v = getLayoutInflater().inflate(R.layout.chat_item, null);


			ChatUser c = getItem(pos);
			TextView lbl = (TextView) v;
			lbl.setText(c.getUsername());
			lbl.setCompoundDrawablesWithIntrinsicBounds(c.isOnline() ? R.drawable.ic_online : R.drawable.ic_offline, 0, R.drawable.arrow, 0);

			return v;
		}

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.ecb:
				return true;
			case R.id.cbc:
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

}
