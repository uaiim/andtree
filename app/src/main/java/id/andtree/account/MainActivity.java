package id.andtree.account;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.FileProvider;
import com.google.android.gms.auth.GoogleAuthUtil;

import id.andtree.account.databinding.ActivityMainBinding;
import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    int REQUEST_CODE_CHOOSE_ACCOUNT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate and get instance of binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // set content view to binding's root
        setContentView(binding.getRoot());
        // satu();
        openFile(new File("content://com.android.externalstorage.documents/root/primary"));
        /*
        if (!b(
                        "android.intent.action.VIEW",
                        new ComponentName(
                                "com.google.android.documentsui",
                                "com.android.documentsui.files.FilesActivity"))
                && !b(
                        "android.intent.action.VIEW",
                        new ComponentName(
                                "com.android.documentsui",
                                "com.android.documentsui.files.FilesActivity"))
                && !b(
                        "android.intent.action.VIEW",
                        new ComponentName(
                                "com.android.documentsui", "com.android.documentsui.FilesActivity"))
                && !b("android.intent.action.VIEW", (ComponentName) null)
                && !b("android.provider.action.BROWSE", (ComponentName) null)
                && !b("android.provider.action.BROWSE_DOCUMENT_ROOT", (ComponentName) null)) {
            // c(new Intent(this, ErrorActivity.class));
        }
        */
    }
    private static final int REQUEST_CODE_OPEN_DOCUMENT = 1;

private void openFile(File file) {
        Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Handle the error if no app can open the file
            e.printStackTrace();
        }
    }
private void shareContent() {
        // Create the intent
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain"); // Set the type of content to share

        // Add the content to share
        String shareBody = "Check out this amazing content!";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

        // Create a chooser for the user to select an app
        Intent chooser = Intent.createChooser(shareIntent, "Share via");
        startActivity(chooser);
    }
    public final Uri f0a =
            Uri.parse("content://com.android.externalstorage.documents/root/primary");

    public final void a() {
        try {
            if (Build.VERSION.SDK_INT >= 34) {
                overridePendingTransition(0, 0, 0);
                overridePendingTransition(1, 0, 0);
            }
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } catch (Throwable unused) {
        }
    }

    public final boolean b(String str, ComponentName componentName) {
        Intent intent = new Intent(str, this.f0a);
        if (componentName != null) {
            intent.setComponent(componentName);
        }
        return c(intent);
    }

    public final boolean c(Intent intent) {
        try {
            a();
            startActivity(intent);
            a();
            System.exit(0);
            // finish();
            a();
            return true;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    public void openNotificationSettings(Context context) {
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // For Android Oreo and above
            intent =
                    new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                            .putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        } else {
            // For Android versions below Oreo
            intent =
                    new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            .setData(Uri.parse("package:" + context.getPackageName()));
        }
        context.startActivity(intent);
    }

    void satu() {
        AccountManager accountManager = AccountManager.get(this);
        Intent intent =
                AccountManager.newChooseAccountIntent(
                        null,
                        null,
                        new String[] {GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE},
                        false,
                        null,
                        null,
                        null,
                        null);

        startActivityForResult(intent, REQUEST_CODE_CHOOSE_ACCOUNT);
    }

    void dua() {
        AccountManager accountManager = AccountManager.get(this);
        Intent intent =
                AccountManager.newChooseAccountIntent(
                        null, null, new String[] {"com.google"}, null, null, null, null);
        startActivityForResult(intent, REQUEST_CODE_CHOOSE_ACCOUNT);
    }

    void tiga() {
        AccountManager accountManager = AccountManager.get(this);
        String accountType = "com.google";
        String authTokenType = "oauth2:https://www.googleapis.com/auth/userinfo.email";
        String[] requiredFeatures = null;
        Bundle options = new Bundle();

        accountManager.addAccount(
                accountType,
                authTokenType,
                requiredFeatures,
                options,
                this,
                new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        try {
                            Bundle result = future.getResult();
                            // Retrieve the account name and other details
                            String accountName = result.getString(AccountManager.KEY_ACCOUNT_NAME);
                            // Use the account name as needed
                        } catch (OperationCanceledException e) {
                            // The user canceled the operation
                        } catch (IOException e) {
                            // Handle network errors
                        } catch (AuthenticatorException e) {
                            // Handle authentication errors
                        }
                    }
                },
                null);
        // startActivity(intent);
    }

    void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    void toast(int text) {
        Toast.makeText(this, String.valueOf(text), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        toast(resultCode);
        if (requestCode == REQUEST_CODE_CHOOSE_ACCOUNT) {
            if (resultCode == RESULT_OK) {
                String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                binding.tv.setText(accountName);
            } else {
                toast("Tolong pilih salah satu akun");
                tiga();
            }
            // Use the selected account name
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.binding = null;
    }
}
