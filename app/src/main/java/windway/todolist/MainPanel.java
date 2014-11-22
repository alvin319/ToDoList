package windway.listviewtodolist;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainPanel extends Activity {

    private ListView listView;
    private ArrayList<String> itemList;
    // private ArrayAdapter<String> adapter;
    private CustomAdapter adapter;
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_panel);
        listView = (ListView) findViewById(R.id.list_view);
        size = 0;
        itemList = new ArrayList<String>();
        // adapter = new ArrayAdapter<String>(this,R.layout.simple_list_item, itemList);
        adapter = new CustomAdapter(this, R.layout.list_item, itemList);

        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_panel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.add_item) {
            size++;
            itemList.add("Item " + size);
            adapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class CustomAdapter extends ArrayAdapter<String> {
        Context context;
        int resourceId;
        ArrayList<String> items;

        public CustomAdapter(Context context, int resourceId, ArrayList<String> items) {
            super(context, resourceId, items);
            this.context = context;
            this.resourceId = resourceId;
            this.items = items;
        }

        @Override
        public View getView(int position, final View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = getLayoutInflater();
                v = vi.inflate(R.layout.list_item, null);
            }

            String str = itemList.get(position);
            TextView viewTitle = (TextView) v.findViewById(R.id.viewTitle);
            viewTitle.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    TextView vt = (TextView) convertView.findViewById(R.id.viewTitle);
                    EditText et = (EditText) convertView.findViewById(R.id.editTitle);
                    ImageButton button = (ImageButton) convertView.findViewById(R.id.button);
                    ImageButton checkButton = (ImageButton) convertView.findViewById(R.id.checkButton);

                    vt.setVisibility(View.GONE);
                    et.setText(vt.getText().toString());
                    button.setVisibility(View.GONE);
                    et.setVisibility(View.VISIBLE);
                    checkButton.setVisibility(View.VISIBLE);
                    return false;
                }
            });
            ImageButton checkButton = (ImageButton) v.findViewById(R.id.checkButton);
            checkButton.setTag(position);
            checkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (Integer) v.getTag();
                    TextView vt = (TextView) convertView.findViewById(R.id.viewTitle);
                    EditText et = (EditText) convertView.findViewById(R.id.editTitle);
                    ImageButton button = (ImageButton) convertView.findViewById(R.id.button);

                    et.setVisibility(View.GONE);
                    vt.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                    v.findViewById(R.id.checkButton).setVisibility(View.GONE);
                    itemList.set(pos, et.getText().toString());
                    adapter.notifyDataSetChanged();
                }
            });
            ImageButton button = (ImageButton) v.findViewById(R.id.button);
            button.setTag(position);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (Integer) v.getTag();
                    itemList.remove(pos);
                    adapter.notifyDataSetChanged();
                }
            });
            viewTitle.setText(str);
            return v;
        }


    }
}