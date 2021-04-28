package in.org.videoplayer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import in.org.videoplayer.Models.VideoModel;
import in.org.videoplayer.R;
import in.org.videoplayer.VideoPlayer;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.MyViewHolder> {
    private Context context;
    private List<VideoModel> videoModelList;
    public OnItemClickListener onItemClickListener;


    public VideoListAdapter(Context context, List<VideoModel> videoModelList) {
        this.context = context;
        this.videoModelList = videoModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_row_of_video_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VideoModel model = videoModelList.get(position);
        holder.name.setText(model.getName());
        holder.name.setSelected(true);
        holder.duration.setText(model.getDuration());
        holder.date.setText(model.getDate());
        holder.size.setText(model.getSize());
        Glide.with(context).load(new File(model.getData().getPath())).thumbnail(0.1f).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return videoModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, size, date, duration;
        ConstraintLayout oneRow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.video_themnail_image);
            name = itemView.findViewById(R.id.video_name);
            size = itemView.findViewById(R.id.video_size);
            date = itemView.findViewById(R.id.video_date);
            duration = itemView.findViewById(R.id.video_duration);
            oneRow = itemView.findViewById(R.id.one_row);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition(), v);
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, View v);
    }
}
