package com.wushange.commsdk.util;

import android.content.Context;
import android.content.DialogInterface;

import com.wushange.commsdk.customview.actionsheet.ActionSheetDialog;
import com.wushange.commsdk.customview.actionsheet.ActionSheetDialog.OnSheetItemClickListener;
import com.wushange.commsdk.customview.actionsheet.ActionSheetDialog.SheetItemColor;
import com.wushange.commsdk.customview.svprogresshud.SVProgressHUD;
import com.wushange.commsdk.customview.widget.CustomDialog;

public class DialogUtil {

    public static void showCustomDialog(Context context, boolean isCanle,
                                        String title, String message, String ok,
                                        DialogInterface.OnClickListener okclick, String canale,
                                        DialogInterface.OnClickListener canaleClick) {
        CustomDialog.Builder customdialog = new CustomDialog.Builder(context);
        customdialog.setTitle(title);
        customdialog.setCanCanle(isCanle);
        customdialog.setMessage(message);
        customdialog.setPositiveButton(ok, okclick);
        customdialog.setNegativeButton(canale, canaleClick);
        customdialog.create().show();
    }

    public static void showSVProgress(Context context, String string,
                                      SVProgressHUDStyle style) {
        SVProgressHUD mSVProgressHUD = new SVProgressHUD(context);
        switch (style) {
            case ERRORWITHMSG:
                mSVProgressHUD.showErrorWithStatus(string);
                break;
            case INFOWITHMSG:
                mSVProgressHUD.showInfoWithStatus(string);
                break;
            case SUCCESSWHITMSG:
                mSVProgressHUD.showSuccessWithStatus(string);
                break;
            case SHOW:
                mSVProgressHUD.showWithStatus(string);
                break;

            default:
                break;
        }

    }

    public enum SVProgressHUDStyle {
        /**
         * ERRORWITHMSG
         * INFOWITHMSG
         * SUCCESSWHITMSG
         * SHOW
         */
        ERRORWITHMSG, INFOWITHMSG, SUCCESSWHITMSG, SHOW
    }

    public static void showActionSheetDialog(Context context, String title,
                                             String[] items, OnSheetItemClickListener itemClickListener) {
        ActionSheetDialog actionSheetDialog = new ActionSheetDialog(context)
                .builder();
        actionSheetDialog.setTitle(title);
        actionSheetDialog.setCancelable(true);
        actionSheetDialog.setCanceledOnTouchOutside(true);
        for (String item : items) {
            actionSheetDialog.addSheetItem(item, SheetItemColor.Blue,
                    itemClickListener);
        }
        actionSheetDialog.show();
    }

    ;

}