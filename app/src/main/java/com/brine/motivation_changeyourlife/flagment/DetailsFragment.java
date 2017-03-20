package com.brine.motivation_changeyourlife.flagment;

import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brine.motivation_changeyourlife.R;
import com.brine.motivation_changeyourlife.database.DatabaseHelper;
import com.brine.motivation_changeyourlife.model.MotivationCard;
import com.bumptech.glide.Glide;

import java.io.IOException;

import static com.brine.motivation_changeyourlife.model.MotivationCard.CHECKED;
import static com.brine.motivation_changeyourlife.model.MotivationCard.STATUS_DONE;
import static com.brine.motivation_changeyourlife.model.MotivationCard.UNCHECKED;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    public static final String DATA = "data";

    private CollapsingToolbarLayout mCollapsingToolbar;
    FloatingActionButton mFabSave, mFabSetWall;
    private TextView mTvTitle, mTvDescription;
    private ImageView mImageDetails;
    private CheckBox mCheckboxUnderstand, mCheckboxCanDo, mCheckboxDoneIt;

    private MotivationCard mMotivationCard;
    private DatabaseHelper mDatabase;

    public DetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDatabase = new DatabaseHelper(getContext());

        initUI(view);

        Bundle bundle = getArguments();
        int motivationId = bundle.getInt(DATA);
        String sqlMotivationCard = "SELECT * FROM " + DatabaseHelper.TABLE_MOTIVATION_CARD + " WHERE " + DatabaseHelper.KEY_ID_MOTIVATION_CARD + " = " + motivationId;
        mMotivationCard = mDatabase.getMotivationCard(sqlMotivationCard);
        Log.d("MotivationCard", "ID: " + mMotivationCard.getId() + "\n" +
                "Title: " + mMotivationCard.getTitle() + "\n" +
                "Image: " + mMotivationCard.getImage() + "\n" +
                "Description: " + mMotivationCard.getDescription() + "\n" +
                "Understand: " + mMotivationCard.getUnderstand() + "\n" +
                "CanDoIt: " + mMotivationCard.getCanDoIt() + "\n" +
                "DoneIt: " + mMotivationCard.getDoneIt() + "\n");
        initData();
        mFabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setMessage("Are you sure you want to save this data?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                saveData();
                                if(!checkedDone()){
                                    Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                                }else{
                                    donateMe();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
        mFabSetWall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setMessage("Are you sure you want to set wallpaper?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                WallpaperManager myWallpaperManager =
                                        WallpaperManager.getInstance(getContext());
                                int res = getContext().getResources().getIdentifier(
                                        mMotivationCard.getImage(),
                                        "drawable",
                                        getContext().getPackageName());
                                try {
                                    myWallpaperManager.setResource(res);
                                    Toast.makeText(getContext(), "Done!", Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
    }

    private void initUI(View view){
        mCollapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        mFabSave = (FloatingActionButton) view.findViewById(R.id.fab_save);
        mFabSetWall = (FloatingActionButton) view.findViewById(R.id.fab_set_wall);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvDescription = (TextView) view.findViewById(R.id.tv_description);
        mImageDetails = (ImageView) view.findViewById(R.id.image_details);
        mCheckboxUnderstand = (CheckBox) view.findViewById(R.id.checkbox_understand);
        mCheckboxCanDo = (CheckBox) view.findViewById(R.id.checkbox_can_do_it);
        mCheckboxDoneIt = (CheckBox) view.findViewById(R.id.checkbox_done_it);
    }

    private void initData(){
        getActivity().setTitle(getResources().getString(R.string.app_name));
        if(mMotivationCard != null){
            mTvTitle.setText(mMotivationCard.getTitle());
            mTvDescription.setText(mMotivationCard.getDescription());

            int res = getContext().getResources().getIdentifier(
                    mMotivationCard.getImage(),
                    "drawable",
                    getContext().getPackageName());
            Glide.with(getContext())
                    .load(res)
                    .asBitmap()
                    .override(1200, 1500)
                    .centerCrop()
                    .into(mImageDetails);
            mCheckboxUnderstand.setChecked(mMotivationCard.isUnderstand());
            mCheckboxCanDo.setChecked(mMotivationCard.isCanDoIt());
            mCheckboxDoneIt.setChecked(mMotivationCard.isDoneIt());
        }else{
            //TODO: back
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveData();
    }

    private void saveData(){
        mMotivationCard.setUnderstand(mCheckboxUnderstand.isChecked()? CHECKED: UNCHECKED);
        mMotivationCard.setCanDoIt(mCheckboxCanDo.isChecked()? CHECKED: UNCHECKED);
        mMotivationCard.setDoneIt(mCheckboxDoneIt.isChecked()? CHECKED: UNCHECKED);
        if(checkedDone()){
            mMotivationCard.setStatus(STATUS_DONE);
        }
        Log.d("MotivationCard", "ID: " + mMotivationCard.getId() + "\n" +
                "Title: " + mMotivationCard.getTitle() + "\n" +
                "Image: " + mMotivationCard.getImage() + "\n" +
                "Description: " + mMotivationCard.getDescription() + "\n" +
                "Understand: " + mMotivationCard.getUnderstand() + "\n" +
                "CanDoIt: " + mMotivationCard.getCanDoIt() + "\n" +
                "DoneIt: " + mMotivationCard.getDoneIt() + "\n");
        mDatabase.updateMotivationCard(mMotivationCard);
    }

    private boolean checkedDone(){
        if(mMotivationCard.isUnderstand() && mMotivationCard.isCanDoIt() && mMotivationCard.isDoneIt()){
            return true;
        }
        return false;
    }

    private void donateMe(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Can you donate me?")
                .setIcon(R.drawable.ic_success)
                .setMessage("Have you really confidence that you have won yourself?")
                .setPositiveButton("Yes, donate you", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Yes, but later", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Thanks very much!", Toast.LENGTH_LONG).show();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
