package sg.com.kaplan.pdma.weathersg;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class Data extends AppCompatActivity {


    ListView task_list;
    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);


        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        //to replace with your own key
        //Parse.initialize(this, "");



        ParseObject.registerSubclass(Task.class);

        final EditText task_input = (EditText) findViewById(R.id.task_input);
        task_list = (ListView) findViewById(R.id.task_list);

        Button submit_button = (Button) findViewById(R.id.submit_button);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskText = task_input.getText().toString();
                if (taskText.length() > 0) {
                    Task t = new Task();
                    t.setDescription(taskText);
                    t.setCompleted(false);
                    t.saveEventually();
                    taskAdapter.insert(t, 0);
                    task_input.setText("");
                }
            }
        });

        taskAdapter = new TaskAdapter(this, new ArrayList<Task>()); //empty list
        task_list.setAdapter(taskAdapter);

        updateData();

        task_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = taskAdapter.getItem(position);
                TextView taskDescription = (TextView) view.findViewById(R.id.task_description);

                task.setCompleted(!task.isCompleted());

                if(task.isCompleted()){
                    taskDescription.setPaintFlags(taskDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    taskDescription.setPaintFlags(taskDescription.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }

                task.saveEventually();
            }
        });

//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }


    public void updateData(){
        ParseQuery<Task> query = ParseQuery.getQuery(Task.class); //SELECT * from table
        query.findInBackground(new FindCallback<Task>() {

            @Override
            public void done(List<Task> tasks, ParseException error) {
                if (tasks != null) {
                    taskAdapter.clear();
                    taskAdapter.addAll(tasks);
                }
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
