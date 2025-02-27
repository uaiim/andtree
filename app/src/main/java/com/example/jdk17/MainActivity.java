package com.example.jdk17;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.jdk17.databinding.ActivityMainBinding;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    static {
        System.loadLibrary("my_native_lib");
    }
    private native void startServer();
    private native void stopServer();

    String toPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate and get instance of binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // set content view to binding's root
        setContentView(binding.getRoot());
        toPath = toPath = "/data/data/" + getPackageName() + "/files";
        // createInternalFolder(this, "usr");
        // copyAssetToInternalStorage(toPath, "php.zip", "php.zip");
        // copyAssetToInternalStorage(toPath, "server.php", "server.php");
        binding.input.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE
                                || (event != null
                                        && event.getAction() == KeyEvent.ACTION_DOWN
                                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                            // Handle the action here
                            String enteredText = binding.input.getText().toString();
                            if (enteredText.contains("cls")) {
                                binding.output.setText("");
                                startServer();
                            }
                            //binding.output.append(stringFromJNI());

                            // runCommand(getApplicationContext(), enteredText);
                            binding.input.setText("");
                            binding.scroll.post(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            binding.scroll.fullScroll(ScrollView.FOCUS_DOWN);
                                        }
                                    });
                            return true; // Return true if you have handled the action
                        }
                        return false; // Return false if you haven't handled the action
                    }
                });
        // copyAssets(); // Copy assets to internal storage
        // startPhpServer();
    }

    public void toast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public String runCommand(Context context, String command) {
        StringBuilder output = new StringBuilder();
        try {
            // Get the app's data directory
            String dataDir = context.getApplicationInfo().dataDir;

            // Prepare the command
            // String fullCommand = "cd " + dataDir + " && " + command;
            String fullCommand = "cd " + toPath + " && " + command;

            // Execute the command
            Process process = Runtime.getRuntime().exec(new String[] {"sh", "-c", fullCommand});
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            binding.output.append(output);
            reader.close();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            output.append("Error: ").append(e.getMessage());
            binding.output.append(output);
        }
        return output.toString();
    }

    public String copyAssetToInternalStorage(
            String path, String assetFileName, String destinationFileName) {
        AssetManager assetManager = getAssets();
        InputStream in = null;
        FileOutputStream out = null;

        try {
            // Open the asset file
            in = assetManager.open(assetFileName);
            // Create a file in the internal storage
            File outFile = new File(path, destinationFileName);
            if (outFile.exists()) {
                return "File already exist: " + outFile.getCanonicalPath();
            }
            out = new FileOutputStream(outFile);

            // Buffer for copying
            byte[] buffer = new byte[1024];
            int read;

            // Read from the asset and write to the internal storage
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }

            // Optionally, you can log or show a message that the file has been copied
            System.out.println("File copied to: " + outFile.getAbsolutePath());
            return outFile.getAbsolutePath();

        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            // Close the streams
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createInternalFolder(Context context, String folderName) {
        // Get the internal storage directory
        File directory = new File(context.getFilesDir(), folderName);

        // Create the directory if it doesn't exist
        if (!directory.exists()) {
            boolean isCreated = directory.mkdirs();
            if (isCreated) {
                // Folder created successfully
                System.out.println("Folder created: " + directory.getAbsolutePath());
            } else {
                // Failed to create folder
                System.out.println("Failed to create folder");
            }
        } else {
            // Folder already exists
            System.out.println("Folder already exists: " + directory.getAbsolutePath());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.binding = null;
    }
}
