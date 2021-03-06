package com.sesumy.aeschat_kripto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sesumy.aeschat_kripto.custom.CustomActivity;
import com.sesumy.aeschat_kripto.model.ChatUser;
import com.sesumy.aeschat_kripto.model.Key;
import com.sesumy.aeschat_kripto.utils.Utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Class Register is the Activity class that shows user registration screen
 * that allows user to register itself on Parse server for this Chat app.
 */
public class Register extends CustomActivity
{

	/** The password EditText. */
	private EditText pwd;

	/** The email EditText. */
	private EditText email;

    /** The displayName EditText. */
    private EditText displayName;

    //Kullanıcıdan alınan key değeri
    private EditText keyText;

    /** Register progress dialog */
    private ProgressDialog registerProgressDlg;
    FirebaseDatabase fbData;
    DatabaseReference dbRef;
    String uid;

	/* (non-Javadoc)
	 * @see com.chatt.custom.CustomActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
        fbData=FirebaseDatabase.getInstance ();
		setTouchNClick(R.id.btnReg);

		pwd = (EditText) findViewById(R.id.pwd);
		email = (EditText) findViewById(R.id.email);
        displayName = (EditText) findViewById(R.id.displayName);
        keyText=(EditText)findViewById (R.id.key);
 	}

	/* (non-Javadoc)
	 * @see com.chatt.custom.CustomActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		super.onClick(v);

        // Extract form fields
		final String password = pwd.getText().toString();
        final String email = this.email.getText().toString();
        final String displayName = this.displayName.getText().toString();

		if ( password.length() == 0 || email.length() == 0 || displayName.length() == 0)
		{
			Utils.showDialog(this, R.string.err_fields_empty);
			return;
		}

        // Register the user
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password) .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Logger.getLogger(Login.class.getName()).log(Level.ALL, "createUserWithEmailAndPassword:onComplete:" + task.isSuccessful());
                registerProgressDlg.dismiss();
                if (!task.isSuccessful()) {
                    Logger.getLogger(Register.class.getName()).log(Level.ALL, "createUserWithEmailAndPassword", task.getException());
                    Utils.showDialog(
                            Register.this,
                            getString(R.string.err_singup));
                }
                else {
                    final ArrayList<String> defaultRoom = new ArrayList<String>();
                    defaultRoom.add("home");

                    // Update the user profile information
                    final FirebaseUser user = task.getResult().getUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(displayName)
                            .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                            .build();
                    user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void> () {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Logger.getLogger(Register.class.getName()).log(Level.ALL, "User profile updated.");
                                // Construct the ChatUser
                                UserList.user = new ChatUser (user.getUid(),displayName, email,true,defaultRoom);
                                    uid=user.getUid ();
                                // Setup link to users database
                                FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).setValue(UserList.user);
                                //KEYLERİ DATABASE EKLEME
                                keyEkle();

                                startActivity(new Intent(Register.this, UserList.class));
                                finish();
                            }
                        }
                    });

                }

            }
        });

        registerProgressDlg = ProgressDialog.show(this, null,
				getString(R.string.alert_wait));
	}

    public void keyEkle(){
        dbRef=fbData.getReference ("keys");
        String k=dbRef.push ().getKey ();
        DatabaseReference dbRefYeni=fbData.getReference ("keys/"+k);
        System.out.println (keyText.getText ().toString ());

        //Kullanıcının değerini tekrar kullanacağımız için static veritabanına kaydediyorum.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("userKey", Integer.parseInt (keyText.getText ().toString ()));
        editor.apply ();
        //Kaydettik
       // int publicKey= (int) Math.pow(5, Integer.parseInt (keyText.getText ().toString ()) )% 129; //POW ÜS ALMA İŞLEMİ

         BigInteger bi1;
        //Prime Number 5654972695164239
        //Generator 2476357078398256
          bi1 = new BigInteger("8690333381690951");
          BigInteger base=BigInteger.valueOf(5);
          BigInteger number=base.pow(Integer.parseInt (keyText.getText ().toString ()));
          BigInteger bigInteger=number.mod(bi1);

        //% ise mod alma işlemidir .
        //Burada Deffi Hellman ile Açık anahtarı oluşturuyoruz.

        dbRefYeni.setValue (new Key (uid,bigInteger.toString ()));
        //Bu anahtar veri tabanımızda string şeklinde kaydedildi.
        //VERİ TABABNINA AÇIK ANAHTARI KAYDETTİK
        //bigInteger  bizim Açık anahtarımız
        // 16 bitlik .
    }










}
