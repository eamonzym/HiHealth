package com.example.eamon.hihealth.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.eamon.hihealth.R;
import com.example.eamon.hihealth.activity.CreateExerciseActivity;
import com.example.eamon.hihealth.db.Exercise;

import java.util.List;

/**
 * 作者：Created by eamon
 * 时间：  on 2018/6/1.
 */

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private Context mContext;

    private List<Exercise> mExerciseList;


    static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        TextView exerciseName;
        TextView exerciseCal;
        TextView exerciseEffectTime;

        public ViewHolder(View view) {
            super(view);
            relativeLayout = (RelativeLayout) view;
            exerciseName = (TextView) view.findViewById(R.id.exercise_name);
            exerciseCal = (TextView) view.findViewById(R.id.exercise_cal);
            exerciseEffectTime = (TextView) view.findViewById(R.id.exercise_exerciseeffecttime);
        }
    }

    public ExerciseAdapter(List<Exercise> exerciseList) {
        mExerciseList = exerciseList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.exercise_item,
                parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Exercise exercise = mExerciseList.get(position);
                Intent intent = new Intent(mContext, CreateExerciseActivity.class);
                intent.putExtra("exercise", exercise);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Exercise exercise = mExerciseList.get(position);
        holder.exerciseName.setText(exercise.getExercisename());
        holder.exerciseCal.setText(exercise.getExercisecalorie().toString());
        holder.exerciseEffectTime.setText("  千卡 / " + exercise.getExerciseeffecttime().toString() + "分钟");

    }

    @Override
    public int getItemCount() {
        return mExerciseList.size();
    }
}
