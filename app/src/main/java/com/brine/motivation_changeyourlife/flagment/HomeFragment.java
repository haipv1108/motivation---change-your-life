package com.brine.motivation_changeyourlife.flagment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.brine.motivation_changeyourlife.R;
import com.brine.motivation_changeyourlife.adapter.MotivationCardAdapter;
import com.brine.motivation_changeyourlife.database.DatabaseHelper;
import com.brine.motivation_changeyourlife.model.MotivationCard;
import com.brine.motivation_changeyourlife.utils.Constant;


import java.util.ArrayList;
import java.util.List;

import static com.brine.motivation_changeyourlife.flagment.DetailsFragment.DATA;
import static com.brine.motivation_changeyourlife.model.MotivationCard.STATUS_PROCESSING;
import static com.brine.motivation_changeyourlife.model.MotivationCard.STATUS_WAIT_YOU;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment
        implements MotivationCardAdapter.MotivationCardCallback{

    private RecyclerView mRecycleView;
    FloatingActionButton mBtnFab;
    private List<MotivationCard> mMotivationCards;
    private MotivationCardAdapter mCardAdapter;
    private DatabaseHelper mDatabase;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        initData();

        String sql = "SELECT * FROM " + DatabaseHelper.TABLE_MOTIVATION_CARD;
        if(mDatabase.getAllMotivationCard(sql).size() == 0){
            List<MotivationCard> motivationCards = (new Constant()).getMotivationCards();
            for(MotivationCard motivationCard : motivationCards){
                mDatabase.insertMotivationCard(motivationCard);
            }
        }

        mMotivationCards.addAll(mDatabase.getAllMotivationCard(sql));
        mCardAdapter.notifyDataSetChanged();

        mBtnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecycleView.post(new Runnable() {
                    @Override
                    public void run() {
                        mRecycleView.smoothScrollToPosition(0);
                    }
                });
            }
        });
    }

    private void initUI(View view){
        mRecycleView = (RecyclerView) view.findViewById(R.id.recycle_motivation_card);
        mBtnFab = (FloatingActionButton) view.findViewById(R.id.fab_save);
    }

    private void initData(){
        getActivity().setTitle(getResources().getString(R.string.app_name));
        mDatabase = new DatabaseHelper(getContext());

        mMotivationCards = new ArrayList<>();
        mCardAdapter = new MotivationCardAdapter(getContext(), mMotivationCards, this);

        mRecycleView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.addItemDecoration(
                new DividerItemDecoration(getContext(), LinearLayout.HORIZONTAL));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.setAdapter(mCardAdapter);
    }

    @Override
    public void onClickCard(MotivationCard motivationCard) {
        if(motivationCard.getStatus() == STATUS_WAIT_YOU){
            motivationCard.setStatus(STATUS_PROCESSING);
        }
        mDatabase.updateMotivationCard(motivationCard);

        Bundle bundle = new Bundle();
        bundle.putInt(DATA, motivationCard.getId());
        Fragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, detailsFragment)
                .addToBackStack("homefragment")
                .commit();
    }
}
