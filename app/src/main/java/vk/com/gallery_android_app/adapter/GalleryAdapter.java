package vk.com.gallery_android_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import vk.com.gallery_android_app.R;
import vk.com.gallery_android_app.provider.GalleryDataProvider;

public class GalleryAdapter extends ArrayAdapter {

    List<GalleryDataProvider> list = new ArrayList<>();

    public GalleryAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    static class DataHandler
    {
        ImageView imageView;
    }

    @Override
    public void add(@Nullable final Object object) {
        list.add((GalleryDataProvider) object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public void remove(@Nullable Object object) {
        list.remove(object);
    }

    public List<GalleryDataProvider> getList() {
        return list;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        view = convertView;
        DataHandler dataHandler;
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_image_list_view_layout, parent, false);
            dataHandler = new DataHandler();
            dataHandler.imageView = view.findViewById(R.id.custom_image_view_list_layout);
            view.setTag(dataHandler);
        }
        else
        {
            dataHandler = (DataHandler) view.getTag();
        }

        GalleryDataProvider galleryDataProvider;
        galleryDataProvider = (GalleryDataProvider) this.getItem(position);
        dataHandler.imageView.setImageResource(galleryDataProvider.getImageId());
        return view;
    }
}
