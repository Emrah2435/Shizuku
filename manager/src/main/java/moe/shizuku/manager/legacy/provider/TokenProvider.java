package moe.shizuku.manager.legacy.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import moe.shizuku.manager.ShizukuManagerApplication;
import moe.shizuku.manager.authorization.AuthorizationManager;
import moe.shizuku.manager.legacy.ShizukuLegacy;

/**
 * Created by rikka on 2017/10/28.
 */

public class TokenProvider extends ContentProvider {

    private static final String TAG = "TokenProvider";

    @Override
    public boolean onCreate() {
        ShizukuManagerApplication.init(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public Bundle call(@NonNull String method, @Nullable String arg, @Nullable Bundle extras) {
        String packageName = getCallingPackage();
        int uid = Binder.getCallingUid();

        if (packageName != null
                && Build.VERSION.SDK_INT < 23 // system will help us check on 23+
                && !AuthorizationManager.granted(packageName, uid)) {
            return null;
        }

        Bundle result = new Bundle();
        result.putLong(ShizukuLegacy.EXTRA_TOKEN_MOST_SIG, ShizukuLegacy.getToken().getMostSignificantBits());
        result.putLong(ShizukuLegacy.EXTRA_TOKEN_LEAST_SIG, ShizukuLegacy.getToken().getLeastSignificantBits());
        return result;
    }
}
