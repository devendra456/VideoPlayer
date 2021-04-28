package in.org.videoplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import in.org.videoplayer.Adapters.VideoListAdapter;
import in.org.videoplayer.Models.VideoModel;

import static android.bluetooth.BluetoothGattCharacteristic.PERMISSION_READ;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    public static List<VideoModel> videoArrayList;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.video_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        videoArrayList=new ArrayList<>();
        if(checkPermission()){
            getVideos();
        }
    }
    public void getVideos() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Video.VideoColumns.DATA ,MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.TITLE,MediaStore.Video.Media.DURATION,MediaStore.Video.Media.SIZE,MediaStore.Video.Media.DATE_MODIFIED};
        Cursor cursor = contentResolver.query(uri, projection, null, null, null);

        //looping through all rows and adding to list
        if (cursor != null && cursor.moveToFirst()) {
            do {

                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                String size = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                String date = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED));
                String data = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                VideoModel videoModel  = new VideoModel ();
                videoModel.setName(title);
                videoModel.setData(Uri.parse(data));
                videoModel.setDuration(timeConversion(Long.parseLong(duration)));
                videoModel.setSize(sizeConversion(size));
                videoModel.setDate(getDate(date));
                videoArrayList.add(videoModel);

            } while (cursor.moveToNext());
        }
        VideoListAdapter  adapter = new VideoListAdapter (this, videoArrayList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new VideoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View v) {
                Intent intent=new Intent(getApplicationContext(),VideoPlayer.class);
                intent.putExtra("pos",pos);
                startActivity(intent);
            }
        });
    }
    //runtime storage permission
    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case  PERMISSION_READ: {
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getApplicationContext(), "Please allow storage permission", Toast.LENGTH_LONG).show();
                    } else {
                        getVideos();
                    }
                }
            }
        }
    }
    //time conversion
    public String timeConversion(long value) {
        String videoTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            videoTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            videoTime = String.format("%02d:%02d", mns, scs);
        }
        return videoTime;
    }

    public String sizeConversion(String size){
        String videoSize;
        long l=Long.parseLong(size);
        long l1= (long) (l/1048576);
        long l2=(long) (l%1048576);
        String s=String.valueOf(l2);
        String s1=s.substring(0,2);
        videoSize=l1+"."+s1+" MB";
        return videoSize;
    }

    public String getDate(String unixDate){
        long val=Long.parseLong(unixDate);
        val*=1000L;
        return new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date(val));
    }
}