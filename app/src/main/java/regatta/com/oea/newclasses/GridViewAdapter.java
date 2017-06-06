package regatta.com.oea.newclasses;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import regatta.com.oea.R;

/**
 * Created by Belal on 12/22/2015.
 */
public class GridViewAdapter extends BaseAdapter {
    //Imageloader to load images
    private ImageLoader imageLoader;
    //Context
    private Context context;
    //Array List that would contain the urls and the titles for the images
    private ArrayList<String> images;
    private ArrayList<String> names;
    private ArrayList<String> subid;

    public GridViewAdapter (Context context, ArrayList<String> images, ArrayList<String> names,ArrayList<String> subid){
        //Getting all the values
        this.context = context;
        this.images = images;
        this.names = names;
        this.subid = subid;
    }
    @Override
    public int getCount() {
        return images.size();
    }
    @Override
    public Object getItem(int position) {
        return images.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Creating a linear layout
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        //NetworkImageView
        NetworkImageView networkImageView = new NetworkImageView(context);
        //Initializing ImageLoader
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(images.get(position), ImageLoader.getImageListener(networkImageView, R.drawable.partnerogo, android.R.drawable.ic_dialog_alert));
        //Setting the image url to load
        networkImageView.setImageUrl(images.get(position),imageLoader);
        //Creating a textview to show the title
        TextView textView = new TextView(context);
        TextView textView1= new TextView(context);
        textView.setText(names.get(position));
        textView1.setText(subid.get(position));
        textView1.setVisibility(View.GONE);
         //  textView1.setText(subid.get(position));
        //Scaling the imageview
        networkImageView.setLayoutParams(new GridView.LayoutParams(300,400));
       // networkImageView.setForegroundGravity(Gravity.CENTER_VERTICAL);
        //Adding views to the layout
        textView.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL);
        textView.setTextSize(20);
        textView.setTextColor(Color.BLACK);
        linearLayout.addView(networkImageView);
        linearLayout.addView(textView);
        linearLayout.addView(textView1);
        //Returnint the layout
        return linearLayout;
    }
}
