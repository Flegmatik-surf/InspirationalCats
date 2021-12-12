package alex.inspirationalcats;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.Vector;

public class BitmapListAdapter extends BaseAdapter {

    private Vector<Bitmap> bitmaps = new Vector<Bitmap>();


    @Override
    public int getCount() {
        return bitmaps.size();
    }

    @Override
    public Object getItem(int position) {
        return bitmaps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bitmap bitmap = (Bitmap) (this.getItem(position));
        Log.i("Alex", "get a bitmaaaap!");
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_image_layout, parent, false);
        }
        ImageView catTopTenView = convertView.findViewById(R.id.catTopTenImage);
        catTopTenView.setImageBitmap(bitmap);

        return convertView;
    }


    public void add(Bitmap bitmap){
        bitmaps.add(bitmap);
    }

    public void clear(){
        bitmaps = new Vector<Bitmap>();
    }
}
