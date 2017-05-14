package com.example.danny.mapboxproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Question>{
    Context context;

    Integer[] idStrings;
    Integer[] isLockedBooleen;
    Integer[] isAnsweredBooleen;
    String[] questionStrings;
    String[] answerStrings;

    LayoutInflater inflater;

    public ItemAdapter(Context context, ArrayList<Question> items){
        super(context, R.layout.question_list_item_layout, items);

        ArrayList<Integer> idStrings = new ArrayList<Integer>();
        ArrayList<Integer> isLockedBooleen = new ArrayList<Integer>();
        ArrayList<Integer> isAnsweredBooleen = new ArrayList<Integer>();
        ArrayList<String> questionStrings = new ArrayList<String>();
        ArrayList<String> answerStrings = new ArrayList<String>();

        for (Question question : items){
            idStrings.add(question.getId());
            isLockedBooleen.add(question.getLocked());
            isAnsweredBooleen.add(question.getAnswered());
            questionStrings.add(question.getQuestion());
            answerStrings.add(question.getAnswerA());
        }

        this.context = context;
        this.idStrings = idStrings.toArray(new Integer[idStrings.size()]);
        this.isLockedBooleen = isLockedBooleen.toArray(new Integer[isLockedBooleen.size()]);
        this.isAnsweredBooleen = isAnsweredBooleen.toArray(new Integer[isAnsweredBooleen.size()]);
        this.questionStrings = questionStrings.toArray(new String[questionStrings.size()]);
        this.answerStrings = answerStrings.toArray(new String[answerStrings.size()]);
    }

    static class ViewHolder{
        ImageView imageView;
        TextView textView2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        if(convertView == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.question_list_item_layout, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.question_item_image);
            holder.textView2 = (TextView) convertView.findViewById(R.id.question_item_text2);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView2.setText(questionStrings[position]);
        if(isAnsweredBooleen[position]==1){
            holder.imageView.setImageResource(R.drawable.ic_check_box_black_24dp);
        }
        else{
            if (isLockedBooleen[position]==1){
                holder.imageView.setImageResource(R.drawable.ic_https_black_24dp);
            }
            else{
                holder.imageView.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
            }
        }
        return convertView;
    }

}