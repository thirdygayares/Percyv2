package com.firetera.percyv2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firetera.percyv2.Adapter.BestDishesListAdapter;
import com.firetera.percyv2.Adapter.EventThemeAdapter;
import com.firetera.percyv2.Adapter.VenueAdapter;
import com.firetera.percyv2.Model.BestDishesListModel;
import com.firetera.percyv2.Model.EventThemeModel;
import com.firetera.percyv2.Model.VenueModel;
import com.firetera.percyv2.ReservationProcessPackage.ReservationProcessPersonalInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Random;


public class HomeFragment extends Fragment {



    EditText fullname;
    TextView username, password, clientName, seeAllDishes;
    Button signinbtn, googlesigninbtn, logoutbtn,reservationBtn, reservationBtn1;
    ImageView cateringinfo_btn;
    CheckBox showpassword;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    String randNumStr;
    String randLettStr;
    public static String name;

    public static String reservationID;




    RecyclerView eventRecyclerView, bestDishesRecyclerView, venueRecyclerView;


    ArrayList<EventThemeModel> eventThemeModels = new ArrayList<>();

    int[] eventImages = {R.drawable.bdaypic, R.drawable.weddingpic,
            R.drawable.corporatepic, R.drawable.businesspic,
            R.drawable.eventspic};

    ArrayList<VenueModel> venueModels = new ArrayList<>();
    int [] venueImages = {R.drawable.palaciodemanilarecyclerimage, R.drawable.warehouseeightrecyclerimage,
            R.drawable.villacapcorecyclerimage, R.drawable.eventscenterrecyclerimage,
            R.drawable.eventvenuemakatirecyclerimage, R.drawable.metsplacerecyclerimage};

    ArrayList<BestDishesListModel> bestDishesListModels = new ArrayList<>();
    int[] bestDishesImages = {R.drawable.chickencordonbleu1, R.drawable.chickenlollipop2,
            R.drawable.fishfillet3, R.drawable.kaldereta4,
            R.drawable.lechon5,  R.drawable.butteredveggie6,
            R.drawable.lecheflan7,  R.drawable.bukosalad8,  R.drawable.fruitsalad9};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.activity_home_fragment, container, false);

        //XML
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        fullname = view.findViewById(R.id.fullname);
        signinbtn = view.findViewById(R.id.signinbtn);
        showpassword = view.findViewById(R.id.showpw);
        clientName = view.findViewById(R.id.name);
        logoutbtn = view.findViewById(R.id.logoutbtn);
        cateringinfo_btn = view.findViewById(R.id.homeImage);
        seeAllDishes = view.findViewById(R.id.seeAll_TxtView);
        reservationBtn = view.findViewById(R.id.reservenow_Btn);
        reservationBtn1 = view.findViewById(R.id.reservenow_Btn1);

        eventRecyclerView = view.findViewById(R.id.eventRecyclerview1);
        bestDishesRecyclerView = view.findViewById(R.id.bestDishesRecyclerview1);
        venueRecyclerView = view.findViewById(R.id.venue_RecyclerView);

        setUpBestDishesRecyclerView();
        setUpVenueRecyclerView();
        setUpEventRecyclerView();



        //FIREBASE
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        reservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randNumStr = "";
                randLettStr = "";
                Random random = new Random();

                String reservationIDLett = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                String reservationIDNum = "1234567890";

                int lenNum = 4;
                int lenLett = 8;
                for (int j = 0; j < lenNum; j++) {

                    randNumStr +=
                            reservationIDNum.charAt(random.nextInt(reservationIDNum.length()));


                }

                for (int i = 0; i < lenLett; i++) {

                    randLettStr +=
                            reservationIDLett.charAt(random.nextInt(reservationIDLett.length()));

                }

              reservationID = randNumStr + "-" + randLettStr;

                startActivity( new Intent(getContext(), ReservationProcessPersonalInfo.class));
            }
        });

        reservationBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                randNumStr = "";
                randLettStr = "";
                Random random = new Random();

                String reservationIDLett = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                String reservationIDNum = "1234567890";

                int lenNum = 4;
                int lenLett = 8;
                for (int j = 0; j < lenNum; j++) {

                    randNumStr +=
                            reservationIDNum.charAt(random.nextInt(reservationIDNum.length()));


                }

                for (int i = 0; i < lenLett; i++) {

                    randLettStr +=
                            reservationIDLett.charAt(random.nextInt(reservationIDLett.length()));

                }

               reservationID = randNumStr + "-" + randLettStr;
                startActivity( new Intent(getContext(), ReservationProcessPersonalInfo.class));
            }
        });

        cateringinfo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CateringInfo.class));
            }
        });

        seeAllDishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MenuOrFood.class));
            }
        });


        firestore.collection("Users"). document(firebaseAuth.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot.exists()){
                                clientName.setText( documentSnapshot.getString("Fullname"));
                            } else{
                                Log.d("TAG", "no such document");
                            }
                        }
                    }
                });




        return view;
    }

    private void setUpVenueRecyclerView() {

        VenueAdapter VenueAdapter = new VenueAdapter(getContext(), venueModels);
        venueRecyclerView.setAdapter(VenueAdapter);
        venueRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false
        );
        venueRecyclerView.setLayoutManager(linearLayoutManager);
        venueRecyclerView.setItemAnimator(new DefaultItemAnimator());

        String[] venueName = getResources().getStringArray(R.array.venue_name_list);
        String[] venueAddress = getResources().getStringArray(R.array.venue_address_list);



        for (int i = 0; i < venueName.length; i++) {


            venueModels.add(new VenueModel(venueName[i],
                    venueAddress[i],
                    venueImages[i]));

        }
    }

    private void setUpEventRecyclerView() {


        EventThemeAdapter eventThemeAdapter = new EventThemeAdapter(getContext(), eventThemeModels);
        eventRecyclerView.setAdapter(eventThemeAdapter);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false
        );
        eventRecyclerView.setLayoutManager(linearLayoutManager);
        eventRecyclerView.setItemAnimator(new DefaultItemAnimator());

        String[] dishNameList = getResources().getStringArray(R.array.event_name_list);
        String[] coursNameList = getResources().getStringArray(R.array.num_of_people_list);
        String[] pricePerPeopleList = getResources().getStringArray(R.array.price_perpeople_list);


        for (int i = 0; i < dishNameList.length; i++) {


            eventThemeModels.add(new EventThemeModel(dishNameList[i],
                    coursNameList[i],
                    pricePerPeopleList[i],
                    eventImages[i]));

        }
    }

    private void setUpBestDishesRecyclerView() {

        BestDishesListAdapter bestDishesListAdapter = new BestDishesListAdapter(getContext(), bestDishesListModels);
        bestDishesRecyclerView.setAdapter(bestDishesListAdapter);
        bestDishesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false
        );
        bestDishesRecyclerView.setLayoutManager(linearLayoutManager);
        bestDishesRecyclerView.setItemAnimator(new DefaultItemAnimator());

        String[] dishNameList = getResources().getStringArray(R.array.food_name_list);
        String[] courseNameList = getResources().getStringArray(R.array.course_name_list);



        for (int i = 0; i < dishNameList.length; i++) {


            bestDishesListModels.add(new BestDishesListModel(dishNameList[i],
                    courseNameList[i],
                    bestDishesImages[i]));

        }
    }


}












//    private void cateringinfo(){
//
//
//    }
//
//    private void eventthemephase() {
//        eventthemebutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), EventThemeOption.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void schedulephasemethod() {
//        schedulebutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), ScheduleOption.class);
//                startActivity(intent);
//            }
//        });
//    }
//
//
//    private void foodphasemethod() {
//        foodbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), Foodoption.class);
//                startActivity(intent);
//            }
//        });
