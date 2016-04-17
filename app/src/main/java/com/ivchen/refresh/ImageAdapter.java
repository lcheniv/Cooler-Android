package com.ivchen.refresh;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter{

    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            v = ((Activity) mContext).getLayoutInflater().inflate(R.layout.grid_item_view, parent, false);
            ((ImageView) v.findViewById(R.id.image)).setImageResource(mThumbIds[position]);
            v.findViewById(R.id.root).setBackgroundColor(Color.parseColor(colors[random(0,colors.length - 1)]));
//            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);
        } else {
//            imageView = (ImageView) convertView;
            v = convertView;
        }

//        imageView.setImageResource(mThumbIds[position]);
//        return imageView;
        return v;
    }

    // references to our images
    public static Integer[] mThumbIds = {
            R.drawable.coke_bottle, R.drawable.dasani,
            R.drawable.fanta, R.drawable.lemonade,
            R.drawable.minute_maid, R.drawable.nos,
            R.drawable.smartwater, R.drawable.sprite,
            R.drawable.tea, R.drawable.vitamin_water
    };

    public static String[] thumbNames = {
      "Coke", "Desani Water", "Fanta Orange", "Minute Maid Lemonade" ,"Minute Maid Orange Juice", "Nos", "Smart Water", "Sprite", "Tea", "Vitamin Water"
    };

    // http://www.materialui.co/colors
    public static String[] colors = {
        "#f44336","#E91E63","#9C27B0","#673AB7","#3F51B5","#2196F3","#00BCD4","#8BC34A","#FFC107","#FF5722","#607D8B","#03A9F4","#66BB6A","#FFE082","#8D6E63","#ff8a80"
    };

    public static int random(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

}
