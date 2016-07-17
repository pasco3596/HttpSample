package jp.ac.hal.httpsample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pasuco on 2016/07/17.
 */
public class ShopArrayAdapter extends ArrayAdapter<Shop> {
    private int resourceId;
    private List<Shop> shop;
    private LayoutInflater inflater;

    public ShopArrayAdapter(Context context, int resourceId, List<Shop> shop) {
        super(context, resourceId, shop);

        this.resourceId = resourceId;
        this.shop   = shop;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = this.inflater.inflate(this.resourceId, null);
        }

        Shop shop = this.shop.get(position);

        // テキストをセット
        TextView shopName = (TextView)view.findViewById(R.id.shop_name);
        shopName.setText(shop.getName());
        // テキストをセット
        TextView shopAddress = (TextView)view.findViewById(R.id.shop_address);
        shopAddress.setText(shop.getAddress());
        return view;
    }
}