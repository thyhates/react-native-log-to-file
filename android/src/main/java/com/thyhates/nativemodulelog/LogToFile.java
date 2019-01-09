package com.thyhates.nativemodulelog;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class LogToFile extends ReactContextBaseJavaModule {

    public LogToFile(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "NativeModuleLogToFile";
    }

    @ReactMethod
    public void checkPermissions(Promise promise) {
        int permissionCheck = ContextCompat.checkSelfPermission(Objects.requireNonNull(getCurrentActivity()),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            promise.resolve(permissionCheck);
        } else {
            promise.resolve(permissionCheck);
        }
    }

    @ReactMethod
    public void getPackageName(Promise promise) {
        String applicationName = getReactApplicationContext().getApplicationInfo().loadLabel(getReactApplicationContext().getPackageManager()).toString();
        promise.resolve(applicationName);
    }

    public static String getDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    @ReactMethod
    public void logToFile(String text, Promise promise) {
        String applicationName = getReactApplicationContext().getApplicationInfo().loadLabel(getReactApplicationContext().getPackageManager()).toString();
        File appFolder = new File(Environment.getExternalStorageDirectory().getPath() + "/" + applicationName);
        File logFile = new File(Environment.getExternalStorageDirectory().getPath() + "/"+applicationName+"/" + applicationName + "_log.log");
        if (!appFolder.exists()) {
            try {
                boolean createAppFolder = appFolder.mkdirs();
                if (!createAppFolder) {
                    promise.resolve("App folder file create fail, check your storage permission.");
                }
            } catch (Exception e) {
                promise.resolve(e.getMessage());
                e.printStackTrace();
            }
        }
        if (!logFile.exists()) {
            try {
                boolean createSuccess = logFile.createNewFile();
                if (!createSuccess) {
                    promise.resolve("Log file create fail, check your storage permission.");
                }
            } catch (IOException e) {
                promise.resolve(e.getMessage());
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            String log = getDateTime() + "  " + text;
            buf.append(log);
            buf.newLine();
            buf.close();
            promise.resolve("Log success");
        } catch (IOException e) {
            promise.resolve(e.getMessage());
            e.printStackTrace();
        }
    }

    @ReactMethod
    public void requestStoragePermission(Promise promise) {
        if (getCurrentActivity() != null) {
            if (ContextCompat.checkSelfPermission(getCurrentActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(getCurrentActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    promise.resolve("desc");
                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(getCurrentActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            2);
                    promise.resolve(true);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                promise.resolve("no need grant");
            }
        } else {
            promise.resolve(false);
        }
    }
}
