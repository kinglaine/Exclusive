package com.example.exclusive;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.drawable.TransitionDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.transition.Transition;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountSetting extends AppCompatActivity implements PersonalInfo.PersonalInfoListener{
    private Button Home; private CircleImageView ProfilePicture; private TextView UserFullName; private Button profileButton; private Button Cart;
    private static final int PICK_IMAGE = 1;
    private TextView PersonaInfoButton1; private TextView ShippingAddressButton1; private  TextView PaymentMethodButton1; private TextView OrderInfo; private TextView LogOutButton1;
    private TextView ChangePasswordButton1;private TextView ChangeEmailAddressButton1; private  TextView DeleteAccountButton1;
    private static Uri imageUri;
    private ImageButton PersonalInfoButton; private ImageButton ShippingAddressButton; private ImageButton PaymentMethodButton; private ImageButton MyOrdersButton;
    private ImageButton LogOutButton; private ImageButton ChangePasswordButton; private ImageButton ChangeEmailAddressButton; private ImageButton DeleteAccountButton;
    private FirebaseUser user; private DatabaseReference databaseReference;  private String userName; private String userID;

    //open any new activity with class as parameter
    public  <T> void openNewPage(Class<T> className){
        Intent intent = new Intent(AccountSetting.this, className);
        startActivity(intent);
    }
    public void openDialog(View v){
        if (v.getId()==R.id.PersonalInfoButton || v.getId()==R.id.PersonalInfo) {
            PersonalInfo personalInfo = new PersonalInfo();
            personalInfo.show(getSupportFragmentManager(), "Personal Information");
        }else if(v.getId()==R.id.ShippingAddressButton || v.getId()==R.id.ShippingAddress){
            ShippingAddress shippingAddress = new ShippingAddress();
            shippingAddress.show(getSupportFragmentManager(),"Shipping Address");
        }else if(v.getId()==R.id.PaymentMethodButton || v.getId()==R.id.PaymentMethod){
            PaymentSetting paymentSetting = new PaymentSetting();
            paymentSetting.show(getSupportFragmentManager(),"Payment Information");
        }else if(v.getId()==R.id.ChangePasswordButton || v.getId()==R.id.change_password){
            ChangePassword changePassword = new ChangePassword();
            changePassword.show(getSupportFragmentManager(),"Reset Password");
        }else if(v.getId()==R.id.ChangeEmailButton|| v.getId()==R.id.change_Email){
            ChangeEmailAddress changeEmailAddress = new ChangeEmailAddress();
            changeEmailAddress.show(getSupportFragmentManager(),"Change Email Address");
        }else if(v.getId()==R.id.Delete_Account|| v.getId()==R.id.DeleteAccountButton){
            DeleteAccount deleteAccount = new DeleteAccount();
            deleteAccount.show(getSupportFragmentManager(),"Delete Account");
        }else if (v.getId()==R.id.MyOrdersButton || v.getId()==R.id.MyOrders){
           OrderInfo orderInfo = new OrderInfo();
           orderInfo.show(getSupportFragmentManager(),"Orders");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_setting);
        Home = (Button)findViewById(R.id.home);
        profileButton = (Button) findViewById(R.id.Account_info);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewPage(MerchandiseScreen.class);
                overridePendingTransition(0, R.anim.righttoleft);

            }
        });


        //click on profile picture to add photo
        ProfilePicture = (CircleImageView) findViewById(R.id.profile_image);
        ProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(AccountSetting.this,"Permission Granted",Toast.LENGTH_SHORT).show();
                        gallery.setType("image/*");
                        gallery.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(gallery,"Select_Picture"),PICK_IMAGE);
                    }else{
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                    }
                }else{
                    Toast.makeText(AccountSetting.this,"Permission Granted",Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Enter personal Information
        PersonaInfoButton1 = (TextView) findViewById(R.id.PersonalInfo);
        PersonaInfoButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(v);
            }
        });
        PersonalInfoButton = (ImageButton) findViewById(R.id.PersonalInfoButton);
        PersonalInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(v);
            }
        });

        //Enter shipping Information
        ShippingAddressButton1 = (TextView) findViewById(R.id.ShippingAddress);
        ShippingAddressButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(v);
            }
        });
        ShippingAddressButton = (ImageButton) findViewById(R.id.ShippingAddressButton);
        ShippingAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(v);
            }
        });

        //Enter payment information(debut cart information)
        PaymentMethodButton1 = (TextView) findViewById(R.id.PaymentMethod);
        PaymentMethodButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(v);
            }
        });
        PaymentMethodButton = (ImageButton) findViewById(R.id.PaymentMethodButton);
        PaymentMethodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(v);
            }
        });

        //See pending orders and past orders
        OrderInfo = (TextView) findViewById(R.id.MyOrders);
        OrderInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(v);
            }
        });
        MyOrdersButton = (ImageButton) findViewById(R.id.MyOrdersButton);
        MyOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(v);
            }
        });

        //cart
        Cart = (Button) findViewById(R.id.Access_cart);
        Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewPage(Cart.class);
                overridePendingTransition(0, R.anim.righttoleft);

            }
        });

        //Logout
        LogOutButton1 = (TextView) findViewById(R.id.Log_Out);
        LogOutButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                openNewPage(LoginScreen.class);
                finish();
            }
        });
        LogOutButton = (ImageButton) findViewById(R.id.Log_OutButton);
        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                openNewPage(LoginScreen.class);
                finish();
            }
        });

        //Change password from app settings
        ChangePasswordButton1 = (TextView) findViewById(R.id.change_password);
        ChangePasswordButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(v);
            }
        });
        ChangePasswordButton = (ImageButton) findViewById(R.id.ChangePasswordButton);
        ChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(v);
            }
        });

        //change email address from app settings
        ChangeEmailAddressButton1 = (TextView) findViewById(R.id.change_Email);
        ChangeEmailAddressButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(v);
            }
        });
        ChangeEmailAddressButton = (ImageButton) findViewById(R.id.ChangeEmailButton);
        ChangeEmailAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(v);
            }
        });

        //Delete account
        DeleteAccountButton1 = (TextView) findViewById(R.id.Delete_Account);
        DeleteAccountButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(v);
            }
        });
        DeleteAccountButton = (ImageButton) findViewById(R.id.DeleteAccountButton);
        DeleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(v);
            }
        });

        /**
         * get the name that the user used when signing up from database and use it to display as profile name
         * */
        UserFullName =(TextView) findViewById(R.id.User_FullName);
        //get the id for the current user
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        //make userfullName equal to user name from database
        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile!=null){
                    String fullName = userProfile.fullName;
                    String email = userProfile.Email;
                    UserFullName.setText(fullName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AccountSetting.this, "Error!", Toast.LENGTH_SHORT).show();
            }
        });



    }
    //request permission to add profile picture
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(AccountSetting.this,"Permission Granted",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(AccountSetting.this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode==RESULT_OK){
            imageUri= data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                ProfilePicture.setImageBitmap(rotateImageIfRequired(getApplicationContext(),bitmap,imageUri));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void applyTexts(String FirstName, String LastName) {
        String FullName = FirstName + LastName;
        UserFullName.setText(FullName);
    }
    //rotate image to a specific angle
    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }
    //check in which way the image is rotated to make it look normal in other words rotate it if required
    private static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

        InputStream input = context.getContentResolver().openInputStream(selectedImage);
        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else
            ei = new ExifInterface(selectedImage.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    @Override
    public void onBackPressed() {
        openNewPage(MerchandiseScreen.class);
    }


}