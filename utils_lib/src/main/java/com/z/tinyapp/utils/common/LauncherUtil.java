package com.z.tinyapp.utils.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.List;

/**
 * Created by zhengfei on 2018/8/16.
 */

public class LauncherUtil {
    public static void addShortcut(Activity cx, String name,int icon,String path) {
//        if (isCreateShortCut(cx,name)) {
//            ToastUtil.show(cx,"快捷方式已经存在");
//            return;
//        }
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        //  快捷图标是允许重复
        shortcut.putExtra("duplicate", false);
        // 快捷图标
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(cx, icon);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
        Intent carryIntent = new Intent(Intent.ACTION_MAIN);
        carryIntent.setClassName(cx.getPackageName(),"com.tinyapp.tinyappplateform.weex.BaseWeexActivity");
        carryIntent.putExtra("js_path",path);
//        carryIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //添加携带的Intent
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, carryIntent);
        cx.sendBroadcast(shortcut);
    }

    /**
     * 判断桌面是否已经存在快捷方式
     */
    private static boolean isExit(Context context,String name) {

        Uri uri = null;
        if (android.os.Build.VERSION.SDK_INT < 8) {
            uri = Uri.parse("content://com.android.launcher.settings/favorites");
        } else {
            uri = Uri.parse("content://com.android.launcher2.settings/favorites");
        }
        String selection = "title=?";
        String[] selectionArgs = new String[] {name};
        Cursor cursor = context.getContentResolver().query(uri, null, selection, selectionArgs, null);
        if(cursor==null){
            return false;
        }
        if (cursor.moveToNext()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }

    }

    public static void delShortcut(Activity cx, String name,int icon,String path) {
//        if (!isCreateShortCut(cx,name)) {
//            ToastUtil.show(cx,"快捷方式已经不存在");
//            return;
//        }
        Intent carryIntent = new Intent(Intent.ACTION_MAIN);
        carryIntent.setClassName(cx.getPackageName(),"com.tinyapp.tinyappplateform.weex.BaseWeexActivity");
        carryIntent.putExtra("js_path",path);

        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,name);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, carryIntent);
        cx.sendBroadcast(shortcut);
    }

    private static String getAuthorityFromPermission(Context context, String permission) {
        if (permission == null) {
            return null;
        }
        List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
        if (packs != null) {
            for (PackageInfo pack : packs) {
                ProviderInfo[] providers = pack.providers;
                if (providers != null) {
                    for (ProviderInfo provider : providers) {
                        if (permission.equals(provider.readPermission)) {
                            return provider.authority;
                        }
                        if (permission.equals(provider.writePermission)) {
                            return provider.authority;
                        }
                    }
                }
            }
        }
        return null;
    }

    private static boolean isCreateShortCut(Context context,String name) {
        boolean isInstallShortcut = false;
        String[] permission = { "com.android.launcher.permission.WRITE_SETTINGS", "com.android.launcher.permission.READ_SETTINGS" };
        String AUTHORITY = null;
        for (int i = 0; i < permission.length; i++) {
            if ((AUTHORITY = getAuthorityFromPermission(context, permission[i])) != null) {
                System.out.println("AUTHORITY: " + AUTHORITY);
                break;
            }
        }
        final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/favorites?notify=true");

        Cursor c = context.getContentResolver().query(CONTENT_URI, new String[] { "title", "iconResource" }, "title=?", new String[] { name }, null);// XXX表示应用名称。
        if (c != null && c.getCount() > 0) {
            isInstallShortcut = true;
            c.close();
        } else if(c != null){
            c.close();
        }
        return isInstallShortcut;
    }
}
