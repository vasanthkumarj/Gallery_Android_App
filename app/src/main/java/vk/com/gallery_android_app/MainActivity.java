package vk.com.gallery_android_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import vk.com.gallery_android_app.adapter.GalleryAdapter;
import vk.com.gallery_android_app.provider.GalleryDataProvider;

public class MainActivity extends AppCompatActivity {

    private Integer[] imageViewIdArray = {R.mipmap.bigil, R.mipmap.comali, R.mipmap.ethir, R.mipmap.imk,
                             R.mipmap.kabali, R.mipmap.kvendam, R.mipmap.lucifer, R.mipmap.manithan,
                             R.mipmap.monster, R.mipmap.ngk};
    private List<Integer> imageViewIdList = new ArrayList<>();
    private List<Integer> tempImageIdListForActionMode = new ArrayList<>();
    private ListView listView;
    private GalleryDataProvider galleryDataProvider;
    private GalleryAdapter galleryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(Integer i: imageViewIdArray)
        {
            imageViewIdList.add(i);
        }
        listView = findViewById(R.id.list_view);
        galleryAdapter = new GalleryAdapter(getApplicationContext(), R.layout.custom_image_list_view_layout);
        listView.setAdapter(galleryAdapter);
        for(Integer image : imageViewIdList)
        {
            galleryDataProvider = new GalleryDataProvider(image);
            galleryAdapter.add(galleryDataProvider);
        }



        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

                if(b)
                {
                    tempImageIdListForActionMode.add(imageViewIdList.get(i));
                    actionMode.setTitle(tempImageIdListForActionMode.size()+" item selected");
                }
                else
                {
                    tempImageIdListForActionMode.remove(imageViewIdList.get(i));
                    actionMode.setTitle(tempImageIdListForActionMode.size()+" item selected");
                }


            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.custom_gallery_contextual_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.delete)
                {
                    //int size = tempImageIdListForActionMode.size();
                    for(Object item : tempImageIdListForActionMode)
                    {
                        List<GalleryDataProvider> dynamicList = galleryAdapter.getList();
                        ListIterator<GalleryDataProvider> dynamicIterator = dynamicList.listIterator();
                        while(dynamicIterator.hasNext())
                        {
                            GalleryDataProvider dataProvider = dynamicIterator.next();
                            if(dataProvider.getImageId()==(int) item)
                            {
                                galleryAdapter.remove(dataProvider);
                                int index = imageViewIdList.indexOf(dataProvider.getImageId());
                                imageViewIdList.remove(index);
                                break;
                            }
                        }

                    }
                    galleryAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(),galleryAdapter.getCount()+" item deleted", Toast.LENGTH_LONG).show();
                    actionMode.finish();
                    return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
              tempImageIdListForActionMode.clear();
            }
        });
    }
}
