package com.example.danny.mapboxproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Question>{
    Context context;

    String[] idStrings;
    Boolean[] isLockedBooleen;
    Boolean[] isAnsweredBooleen;
    String[] questionStrings;
    String[] answerStrings;

    LayoutInflater inflater;

    public ItemAdapter(Context context, ArrayList<Question> items){
        super(context, R.layout.question_list_item_layout, items);

        Log.e("entrou", "------------");
        ArrayList<String> idStrings = new ArrayList<String>();
        ArrayList<Boolean> isLockedBooleen = new ArrayList<Boolean>();
        ArrayList<Boolean> isAnsweredBooleen = new ArrayList<Boolean>();
        ArrayList<String> questionStrings = new ArrayList<String>();
        ArrayList<String> answerStrings = new ArrayList<String>();

        for (Question question : items){
            idStrings.add(question.getId());
            isLockedBooleen.add(question.getLocked());
            isAnsweredBooleen.add(question.getAnswered());
            questionStrings.add(question.getQuestion());
            answerStrings.add(question.getAnswer());
        }

        this.context = context;
        this.idStrings = idStrings.toArray(new String[idStrings.size()]);
        this.isLockedBooleen = isLockedBooleen.toArray(new Boolean[isLockedBooleen.size()]);
        this.isAnsweredBooleen = isAnsweredBooleen.toArray(new Boolean[isAnsweredBooleen.size()]);
        this.questionStrings = questionStrings.toArray(new String[questionStrings.size()]);
        this.answerStrings = answerStrings.toArray(new String[answerStrings.size()]);
    }

    static class ViewHolder{
        ImageView imageView;
        TextView textView;
        TextView textView2;
    }

    int number = 0;

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        if(convertView == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.question_list_item_layout, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.question_item_image);
            holder.textView = (TextView) convertView.findViewById(R.id.question_item_text);
            holder.textView2 = (TextView) convertView.findViewById(R.id.question_item_text2);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        number = position;
        number = number + 1;
        holder.textView.setText("Question " + number);
        holder.textView2.setText(questionStrings[position]);
        if(isAnsweredBooleen[position]){
            holder.imageView.setImageResource(R.drawable.ic_check_box_black_24dp);
        }
        else{
            if (isLockedBooleen[position]){
                holder.imageView.setImageResource(R.drawable.ic_https_black_24dp);
            }
            else{
                holder.imageView.setImageResource(R.drawable.ic_check_box_outline_blank_black_24dp);
            }
        }
        return convertView;
    }

}