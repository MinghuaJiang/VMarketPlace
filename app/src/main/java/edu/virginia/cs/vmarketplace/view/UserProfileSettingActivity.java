package edu.virginia.cs.vmarketplace.view;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.virginia.cs.vmarketplace.R;
import edu.virginia.cs.vmarketplace.model.UserProfileDO;
import edu.virginia.cs.vmarketplace.service.ProfileItemService;
import edu.virginia.cs.vmarketplace.service.S3Service;
import edu.virginia.cs.vmarketplace.service.UserProfileService;
import edu.virginia.cs.vmarketplace.service.loader.CommonAyncTask;
import edu.virginia.cs.vmarketplace.service.login.AppContext;
import edu.virginia.cs.vmarketplace.service.login.AppContextManager;

public class UserProfileSettingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private CircleImageView avatar;
    private RelativeLayout avatarClick;
    private RelativeLayout backgroundClick;
    private TextView gender;
    private RelativeLayout genderClick;
    private TextView birthdate;
    private RelativeLayout birthdateClick;
    private TextView bio;
    private RelativeLayout bioClick;
    private TextView department;
    private RelativeLayout departmentClick;
    private TextView school;
    private RelativeLayout schoolClick;
    private TextView address;
    private RelativeLayout addressClick;
    private AppContext appContext;
    private String[] genderItems = {"Male", "Female"};
    private String[] departments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = AppContextManager.getContextManager().getAppContext();
        String[] schools = ProfileItemService.getInstance().getSchools();
        setContentView(R.layout.activity_user_profile_setting);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        TextView titleView = findViewById(R.id.toolbar_title);
        titleView.setText(R.string.profile_setting);

        avatar = findViewById(R.id.profile_portrait);
        avatarClick = findViewById(R.id.text_container_image);

        if(appContext.getUserDO().getAvatar() != null){
                if(appContext.getUserDO().getAvatar().startsWith(S3Service.S3_PREFIX)){
                    S3Service.getInstance(this).download(appContext.getUserDO().getAvatar(),
                            (x)-> Picasso.with(this).load(x.get(0)).fit().into(avatar));
                }else{
                    Picasso.with(this).load(appContext.getUserDO().getAvatar()).fit().into(avatar);
                }
        }

        backgroundClick = findViewById(R.id.text_container_background);

        gender = findViewById(R.id.profile_gender_info);
        gender.setText(appContext.getUserDO().getGender());
        genderClick = findViewById(R.id.text_container_gender);

        genderClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(UserProfileSettingActivity.this)
                        .setItems(genderItems, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                gender.setText(genderItems[which]);
                                appContext.getUserDO().setGender(genderItems[which]);
                            }
                        }).create();

                dialog.show();
            }
        });

        birthdate = findViewById(R.id.profile_bod_info);
        birthdateClick = findViewById(R.id.text_container_bod);

        birthdate.setText(appContext.getUserDO().getBirthdate());

        birthdateClick.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);

                        // Create a new instance of DatePickerDialog and return it
                        new DatePickerDialog(UserProfileSettingActivity.this,
                                UserProfileSettingActivity.this, year, month, day).show();
                    }
                }
        );

        bio = findViewById(R.id.profile_bio_info);
        bioClick = findViewById(R.id.text_container_bio);

        bioClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileSettingActivity.this,
                        BioSettingActivity.class);
                startActivity(intent);
            }
        });

        bio.setText(appContext.getUserDO().getBio());

        school = findViewById(R.id.profile_school_info);
        schoolClick = findViewById(R.id.text_container_school);

        school.setText(appContext.getUserDO().getSchool());

        schoolClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(UserProfileSettingActivity.this)
                        .setItems(schools, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(!school.getText().equals(schools[which])){
                                    department.setText("");
                                    appContext.getUserDO().setDepartment(null);
                                }
                                
                                school.setText(schools[which]);
                                appContext.getUserDO().setSchool(schools[which]);
                                List<String> result = ProfileItemService.getInstance().getDepartments(school.getText().toString());
                                if(result == null){
                                    departmentClick.setEnabled(false);
                                    departmentClick.setBackgroundColor(ContextCompat.getColor(UserProfileSettingActivity.this,
                                            R.color.hint_color));
                                }else{
                                    departmentClick.setEnabled(true);
                                    departmentClick.setBackgroundColor(ContextCompat.getColor(UserProfileSettingActivity.this,
                                            R.color.tan_background));
                                    departments = result.toArray(new String[0]);
                                }
                            }
                        }).create();

                dialog.show();

            }
        });

        department = findViewById(R.id.profile_department_info);
        departmentClick = findViewById(R.id.text_container_department);

        department.setText(appContext.getUserDO().getDepartment());

        departmentClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(school.getText().toString().isEmpty()){
                    Toast.makeText(UserProfileSettingActivity.this, "Please choose school first", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog dialog = new AlertDialog.Builder(UserProfileSettingActivity.this)
                            .setItems(departments, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    department.setText(departments[which]);
                                    appContext.getUserDO().setDepartment(departments[which]);
                                }
                            }).create();
                    dialog.show();
                }
            }
        });

        address = findViewById(R.id.profile_address_info);
        addressClick = findViewById(R.id.text_container_address);


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        birthdate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
        appContext.getUserDO().setBirthdate(year + "-" + (month + 1)  + "-" + dayOfMonth);
    }

    @Override
    protected void onPause(){
        new CommonAyncTask<UserProfileDO,Void,Void>(UserProfileService.getInstance()::insertOrUpdate,
                appContext.getUserDO()).run();
        super.onPause();
    }
}
