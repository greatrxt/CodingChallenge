package com.shaadi.custom;

import android.app.Activity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SweetAlertDialogBox {
    public static void showOkDialogBox(String message, Activity activity){
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Message")
                .setContentText(message);

        sweetAlertDialog.show();
    }
}
