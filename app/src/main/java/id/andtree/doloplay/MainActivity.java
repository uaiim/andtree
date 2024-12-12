package id.andtree.doloplay;

import android.os.Environment;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import id.andtree.doloplay.databinding.ActivityMainBinding;
import id.andtree.req.Error;
import id.andtree.req.Download;
import id.andtree.req.Fetch;
import id.andtree.req.FetchConfiguration;
import id.andtree.req.FetchListener;
import id.andtree.req.HttpUrlConnectionDownloader;
import id.andtree.req.NetworkType;
import id.andtree.req.Priority;
import id.andtree.req.Request;
import id.andtree.reqcore.Downloader;
import java.io.File;
import java.util.List;
import id.andtree.reqcore.DownloadBlock;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    Fetch fetch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate and get instance of binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // set content view to binding's root
        setContentView(binding.getRoot());
        FetchConfiguration fetchConfiguration =
                new FetchConfiguration.Builder(this)
    .setHttpDownloader(new HttpUrlConnectionDownloader(Downloader.FileDownloaderType.PARALLEL)).setDownloadConcurrentLimit(3).build();

        fetch = Fetch.Impl.getInstance(fetchConfiguration);

        String url = "https://download.blender.org/peach/bigbuckbunny_movies/big_buck_bunny_720p_stereo.avi";
        String file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"video.mp4").toString();

        final Request request = new Request(url, file);
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);
        request.addHeader("referer", "https://www.google.com");

        fetch.enqueue(
                request,
                updatedRequest -> {
                    // Request was successfully enqueued for download.
        Toast.makeText(this,"ok",Toast.LENGTH_LONG).show();
                },
                error -> {
                    // An error occurred enqueuing the request.
        Toast.makeText(this,error.toString(),Toast.LENGTH_LONG).show();
                });
        FetchListener fetchListener =
                new FetchListener() {

                    @Override
                    public void onAdded(Download arg0) {}

                    @Override
                    public void onQueued(Download arg0, boolean arg1) {}

                    @Override
                    public void onWaitingNetwork(Download arg0) {}

                    @Override
                    public void onCompleted(Download arg0) {
                      Toast.makeText(getApplicationContext(),arg0.getFile(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Download arg0, Error arg1, Throwable arg2) {}

                    @Override
                    public void onDownloadBlockUpdated(
                            Download arg0, DownloadBlock arg1, int arg2) {}

                    @Override
                    public void onStarted(
                            Download arg0, List<? extends DownloadBlock> arg1, int arg2) {}

                    @Override
                    public void onProgress(Download arg0, long arg1, long arg2) {}

                    @Override
                    public void onPaused(Download arg0) {}

                    @Override
                    public void onResumed(Download arg0) {}

                    @Override
                    public void onCancelled(Download arg0) {}

                    @Override
                    public void onRemoved(Download arg0) {}

                    @Override
                    public void onDeleted(Download arg0) {}
                };

        fetch.addListener(fetchListener);

        // Remove listener when done.
        // fetch.removeListener(fetchListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.binding = null;
    }
}
