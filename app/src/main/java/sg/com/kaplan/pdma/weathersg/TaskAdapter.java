package sg.com.kaplan.pdma.weathersg;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {



    Context context;
    List<Task> tasks;

    public TaskAdapter(Context context, ArrayList<Task> tasks) {
        super(context, R.layout.task_row_item, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            LayoutInflater mLayoutInflater = LayoutInflater.from(context);
            convertView = mLayoutInflater.inflate(R.layout.task_row_item, null);
        }

        Task task = tasks.get(position);

        TextView descriptionView = (TextView) convertView.findViewById(R.id.task_description);

        descriptionView.setText(task.getDescription());

        if(task.isCompleted()){
            descriptionView.setPaintFlags(descriptionView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            descriptionView.setPaintFlags(descriptionView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        return convertView;
    }

}
