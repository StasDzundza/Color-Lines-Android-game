package com.example.colorlinesclassic;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.example.colorlinesclassic.Activities.GameActivity;

public class AlertDialogWindow {
    public static void showDialogWindow(final GameActivity context){
        new AlertDialog.Builder(context)
                .setTitle("Game Over!")
                .setMessage("Do you want to start new game?")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        context.startNewGame();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        context.returnToMainMenu();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
