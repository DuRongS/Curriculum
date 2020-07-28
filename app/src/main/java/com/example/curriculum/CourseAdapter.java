package com.example.curriculum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class CourseAdapter extends ArrayAdapter<Course> {
    public CourseAdapter(@NonNull Context context, ArrayList<Course> courses) {
        super(context, 0,courses);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.course_list_item, parent, false);
        }

        Course currentCourse = getItem(position);

        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        timeView.setText(currentCourse.getTime());

        TextView courseView = (TextView) listItemView.findViewById(R.id.course);
        courseView.setText(currentCourse.getCourse());

        TextView addressView = (TextView) listItemView.findViewById(R.id.address);
        addressView.setText(currentCourse.getAddress());

        return listItemView;
    }
}
