package com.firetera.percyv2.ReservationProcessPackage;

import static com.firetera.percyv2.RegistrationJavaClass.Register.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firetera.percyv2.Adapter.SelectListiner;
import com.firetera.percyv2.HomeFragment;
import com.firetera.percyv2.LoadingDialog;
import com.firetera.percyv2.Model.FoodPackageModel;
import com.firetera.percyv2.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
//import com.google.android.gms.cast.framework.media.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DecimalFormat;
import java.util.HashMap;

public class PaymentMethod extends AppCompatActivity {

    TextView packageName, foodNo1, foodNo2, foodNo3, foodNo4, totalPrice, precyMobileNum, uploadImage;
    ImageView copy;
    Button reserve_Btn, chooseFile_Btn;
    ImageView receipt_ImageView;
    TextInputEditText clientGCashNumber;
    ProgressBar progressBar;
    AutoCompleteTextView autoCompleteTextView;
    FrameLayout frameLayout, receiptFrameLayout;

    //storing the link of image
    static String imageproof = "";
    Uri uri;

    //I upload the image to the firebase call them
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference = firebaseStorage.getReference();



    public String reservationID, date, event, name, phoneNumber, numOfPeople,
            venue, companyName, foodPackageName, email;
    StorageReference imageGcash;
    public String numOfPendingReservation;


    String [] items = {"GCash"};
    ArrayAdapter<String> adapterItems;


    public String priceOfFoodPackage = ReservationProcess.price;
    String numOfPax = ReservationProcess.numOfPeople;
    String totalPriceString;
    int priceOfFoodPackageInt, numOfPaxInt, totalPriceInt;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        receiptFrameLayout = findViewById(R.id.receipt_frameLayout);
//        uploadImage = findViewById(R.id.uploadReceipt_TextView);
        packageName = findViewById(R.id.PM_packageName);
        foodNo1 = findViewById(R.id.PM_foodNo1);
        foodNo2 = findViewById(R.id.PM_foodNo2);
        foodNo3 = findViewById(R.id.PM_foodNo3);
        foodNo4 = findViewById(R.id.PM_foodNo4);
        totalPrice = findViewById(R.id.PM_totalPrice);
        autoCompleteTextView = findViewById(R.id.modeOfPayment_ACT);
        precyMobileNum = findViewById(R.id.precyMobileNum_TextView);
        copy = findViewById(R.id.copy_ImageView);
        frameLayout = findViewById(R.id.gCash_frameLayout);
        reserve_Btn = findViewById(R.id.reserve_Btn);
        progressBar = findViewById(R.id.reserveBtn_ProgBar);
        //add input layout for Gcash
        clientGCashNumber = findViewById(R.id.clientGCashNumber);
        //choose file button
        chooseFile_Btn = findViewById(R.id.chooseFile_Btn);
        //image preview for payment
        receipt_ImageView = findViewById(R.id.receipt_ImageView);


        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        //TODO uncomment this
        reservationID = HomeFragment.reservationID;

        imageGcash = storageReference.child("GCashReceipt/" + reservationID);

        priceOfFoodPackageInt = Integer.parseInt(priceOfFoodPackage);
        numOfPaxInt = Integer.parseInt(numOfPax);

        adapterItems = new ArrayAdapter<String>(this, R.layout.events_list_item, items);
        autoCompleteTextView.setAdapter(adapterItems);

        firebaseFirestore.collection("NumberOfPending"). document("NumberOfPendingReservation") .addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
                if (documentSnapshot.exists()) {
                    numOfPendingReservation = documentSnapshot.get("NumberOfPendingReservation").toString();
                }
            }
        });

        reserve_Btn.setVisibility(View.VISIBLE);
        setUpReserveButton();
        setUpFoodPackageName();
        setUpFireBase();
        setUpAutoCompleteTextView();
        totalPriceInt = numOfPaxInt * priceOfFoodPackageInt;

        DecimalFormat decimalFormat = new DecimalFormat("###,###.##");


        totalPriceString = Integer.toString(totalPriceInt);

        totalPrice.setText(numOfPax + " x " + priceOfFoodPackage + " = P" + decimalFormat.format(totalPriceInt));

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("TextView", precyMobileNum.getText().toString());
                clipboardManager.setPrimaryClip(clipData);

                clipData.getDescription();

                Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_LONG).show();

            }
        });

        //method for uploading Imagve
        imagepickerMethod();

        receiptFrameLayout.setVisibility(View.GONE);


    }

    // upload image
    private void uploadImageMethod() {

//        chooseFile_Btn.setOnClickListener(new View.OnClickListener() {
//         @Override
//         public void onClick(View v) {

             imageGcash.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                 @Override
                 public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                     Snackbar.make(findViewById(android.R.id.content), "Proof Uploaded", Snackbar.LENGTH_LONG).show();

                     imageGcash.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                         @Override
                         public void onSuccess(Uri uri) {
                             //show the Firebase Storage Link
                             Log.d(TAG,String.valueOf(uri));
                             imageproof = String.valueOf(uri);
                             finish();
                         }
                     }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             Log.d("TAG", "FDAILUERE CAUSE " + e);
                         }
                     });
                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                     progressBar.setVisibility(View.GONE);
                 }
             }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                 @Override
                 public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                     progressBar.setVisibility(View.VISIBLE);
                 }
             });

//     }
// });


    }

    //method for picking image
    private void imagepickerMethod() {
        chooseFile_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                receiptFrameLayout.setVisibility(View.VISIBLE);
                ImagePicker.with(PaymentMethod.this)
                        .galleryOnly()
                        .crop(9f, 16f)//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            uri = data.getData();
            receipt_ImageView.setVisibility(View.VISIBLE);
            receipt_ImageView.setImageURI(uri);
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }



    private void setUpReserveButton() {



        reserve_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //upload image to firebase storage

                uploadImageMethod();
                reserve_Btn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);



                imageGcash.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){

                            imageproof = task.getResult().toString();
                            Log.d("TAG", "kukunin ko" + imageproof);

                            int  intNumOfPendingReservation = Integer.parseInt(numOfPendingReservation);
                            intNumOfPendingReservation += 1;

                            HashMap<String, Object> ReservationDetails = new HashMap<>();
                            ReservationDetails.put("Reservation ID", reservationID);
                            ReservationDetails.put("Name", name);
                            ReservationDetails.put("CompanyName", companyName);
                            ReservationDetails.put("Phone Number", phoneNumber);
                            ReservationDetails.put("ImageProof", imageproof); //getting image link
                            ReservationDetails.put("GCash", clientGCashNumber.getText().toString()); //getting gcash number
                            ReservationDetails.put("ReservationDate", date);
                            ReservationDetails.put("Venue", venue);
                            ReservationDetails.put("Event", event);
                            ReservationDetails.put("Number of People", numOfPeople);
                            ReservationDetails.put("User ID", firebaseAuth.getUid());
                            ReservationDetails.put("Status", false);
                            ReservationDetails.put("Time of Reservation", ReservationProcess.currentTime());
                            ReservationDetails.put("Date of Reservation", ReservationProcess.currentDate());

                            firebaseFirestore.collection("PendingReservation").document(reservationID)
                                    .set(ReservationDetails)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d("TAG", "SUCCESS DATA UPLOAD");

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("TAG", "data sending" + e);
                                        }
                                    });


                numOfPendingReservation = Integer.toString(intNumOfPendingReservation);

                HashMap<String, String> number = new HashMap<>();
                number.put("NumberOfPendingReservation", numOfPendingReservation );

                firebaseFirestore.collection("NumberOfPending").document("NumberOfPendingReservation")
                        .set(number)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG", "SUCCESS DATA UPLOAD");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TAG", "data sending" + e);
                            }
                        });


                firebaseFirestore.collection("Users").document(firebaseAuth.getUid())
                        .collection("My Reservation")
                        .document(reservationID)
                        .set(ReservationDetails);



                        }
                    }
                });

                //go to confirm reservcation

                final LoadingDialog loadingDialog = new LoadingDialog(PaymentMethod.this);

                loadingDialog.startLoadingDialog();



                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismissDialog();
                        startActivity(new Intent(PaymentMethod.this, ConfirmationOfReservation.class));
                    }
                },3000);


            }
        });
    }

    private void setUpFoodPackageName() {
        foodNo1.setText(ReservationProcess.foodNo1);
        foodNo2.setText(ReservationProcess.foodNo2);
        foodNo3.setText(ReservationProcess.foodNo3);
        foodNo4.setText(ReservationProcess.foodNo4);

    }

    private void setUpAutoCompleteTextView() {

        frameLayout.setVisibility(View.GONE);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                if (autoCompleteTextView.getText().toString().equals("GCash")){
                    frameLayout.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    private void setUpFireBase() {

        firebaseFirestore.collection("ClientReservationDetails"). document(reservationID)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot.exists()){
                                reservationID =documentSnapshot.getString("Reservation ID");
                                date = documentSnapshot.getString("ReservationDate");
                                venue = documentSnapshot.getString("Venue");
                                companyName = documentSnapshot.getString("CompanyName");
                                name  = documentSnapshot.getString("Name");
                                event = documentSnapshot.getString("Event");
                                phoneNumber =  "+63" + documentSnapshot.getString("Phone Number");
                                numOfPeople = documentSnapshot.getString("Number of People");
                                packageName.setText(documentSnapshot.getString("PackageName"));
                                email = documentSnapshot.getString("Email");

                            } else{
                                Log.d("TAG", "no such document");
                            }
                        }
                    }
                });
    }


}