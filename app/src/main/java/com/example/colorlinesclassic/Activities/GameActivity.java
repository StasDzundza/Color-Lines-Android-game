package com.example.colorlinesclassic.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.colorlinesclassic.AlertDialogWindow;
import com.example.colorlinesclassic.Cell;

import com.example.colorlinesclassic.ColorLines;
import com.example.colorlinesclassic.Drawer;
import com.example.colorlinesclassic.R;
import com.example.colorlinesclassic.Settings;

import java.util.logging.Logger;

public class GameActivity extends AppCompatActivity {
    private ViewSettings viewSettings;
    private Settings gameSettings;
    private View mainView;
    private View.OnClickListener fieldPressedListener;
    private ColorLines colorLines;
    private long backPressedTime;
    private Toast backToast;
    private int firstPressedButtonId = 0;
    private int secondPressedButtonId = 0;
    private Logger logger = Logger.getLogger(GameActivity.class.getName());
    private int layoutId;
    private int idCounter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initValues();
        createField(gameSettings.getRows(), gameSettings.getColumns());//settings value

        startNewGame();
        setupStartView();
    }

    private void setupStartView() {
        updateRecordAndScoreView(gameSettings.getRecord(), 0);
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ImageButton b1 = mainView.findViewById(R.id.next_color_btn1);
        ImageButton b2 = mainView.findViewById(R.id.next_color_btn2);
        ImageButton b3 = mainView.findViewById(R.id.next_color_btn3);
        params.width = viewSettings.getCellSize();//const values
        params.height = viewSettings.getCellSize();
        b1.setLayoutParams(params);
        b2.setLayoutParams(params);
        b3.setLayoutParams(params);
        b1.setEnabled(false);
        b2.setEnabled(false);
        b3.setEnabled(false);
        updateNextColorsButtonsView();
    }

    private void updateRecordAndScoreView(int record, int score) {
        TextView rec = (TextView) mainView.findViewById(R.id.record_text);
        TextView scor = (TextView) mainView.findViewById(R.id.score_text);
        rec.setText(String.format("Record : %d", record));
        scor.setText(String.format("Score : %d", score));
        setContentView(mainView);
    }

    private void updateNextColorsButtonsView() {
        ImageButton b1 = (ImageButton) mainView.findViewById(R.id.next_color_btn1);
        ImageButton b2 = (ImageButton) mainView.findViewById(R.id.next_color_btn2);
        ImageButton b3 = (ImageButton) mainView.findViewById(R.id.next_color_btn3);
        int i = 0;
        int colorIndex1 = colorLines.getNextColor(i++);
        int colorIndex2 = colorLines.getNextColor(i++);
        int colorIndex3 = colorLines.getNextColor(i++);
        b1.setImageBitmap(Drawer.getBallBitmap(Settings.ballColors[colorIndex1], viewSettings.getCellSize(),
                viewSettings.getCellSize(), viewSettings.getBallRadius()));
        b2.setImageBitmap(Drawer.getBallBitmap(Settings.ballColors[colorIndex2], viewSettings.getCellSize(),
                viewSettings.getCellSize(), viewSettings.getBallRadius()));
        b3.setImageBitmap(Drawer.getBallBitmap(Settings.ballColors[colorIndex3], viewSettings.getCellSize(),
                viewSettings.getCellSize(), viewSettings.getBallRadius()));
        setContentView(mainView);
    }

    private void initValues() {
        viewSettings = new ViewSettings();
        gameSettings = new Settings();
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainView = inflater.inflate(R.layout.activity_game, null);
        fieldPressedListener = getListenerForFieldButtonPress();
        layoutId = -gameSettings.getRows();
    }

    private void createField(int columns, int rows) {
        LinearLayout ly = (LinearLayout) mainView.findViewById(R.id.linesLayout);

        for (int i = 0; i < columns; i++) {
            LinearLayout line = new LinearLayout(this);
            line.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            line.setOrientation(LinearLayout.HORIZONTAL);
            line.setId(layoutId);
            layoutId++;
            for (int j = 0; j < rows; j++) {
                ImageButton imageButton = new ImageButton(this);
                setUpButton(imageButton, idCounter);
                idCounter++;
                line.addView(imageButton);
            }
            ly.addView(line);
        }
        setContentView(mainView);
    }

    private void setUpButton(ImageButton imageButton, int id) {
        imageButton.setId(id);
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.width = viewSettings.getCellSize();//const values
        params.height = viewSettings.getCellSize();
        imageButton.setLayoutParams(params);
        imageButton.setOnClickListener(fieldPressedListener);
        Drawable buttonStyle = getResources().getDrawable(getResources()
                .getIdentifier("cell_style", "drawable", getPackageName()));
        imageButton.setBackground(buttonStyle);
    }

    private void updateButtonView(ImageButton button, Bitmap bitmap) {
        button.setImageBitmap(bitmap);
        setContentView(mainView);
    }

    private View.OnClickListener getListenerForFieldButtonPress() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int buttonId = v.getId();

                if (firstPressedButtonId == 0) {
                    boolean isEmptyCell = colorLines.getCell((buttonId - 1) / gameSettings.getRows(),
                            (buttonId - 1) % gameSettings.getColumns()).isEmpty();
                    if (!isEmptyCell)
                        firstPressedButtonId = buttonId;
                } else {
                    secondPressedButtonId = buttonId;
                    firstPressedButtonId--;
                    secondPressedButtonId--;
                    int row1 = firstPressedButtonId / gameSettings.getRows();
                    int column1 = firstPressedButtonId % gameSettings.getColumns();
                    int row2 = secondPressedButtonId / gameSettings.getRows();
                    int column2 = secondPressedButtonId % gameSettings.getColumns();
                    boolean replaced = colorLines.replaceBall(row1, column1, row2, column2);
                    firstPressedButtonId = secondPressedButtonId = 0;
                    if (replaced) {
                        replacedBallHandler();
                    } else {
                        Toast backToast = Toast.makeText(getBaseContext(), R.string.no_way, Toast.LENGTH_SHORT);
                        backToast.show();
                    }
                }
            }
        };
    }

    private void replacedBallHandler() {
        updateFieldView();
        updateNextColorsButtonsView();
        int score = colorLines.getScore();
        if (score > gameSettings.getRecord()) {
            gameSettings.setRecord(score);
            updateRecordAndScoreView(score, score);
        } else {
            updateRecordAndScoreView(gameSettings.getRecord(), score);
        }
        if (colorLines.gameOver()) {
            endGameHandler();
        }
    }

    private void endGameHandler() {
        Toast backToast = Toast.makeText(getBaseContext(), String.format("Game over. Your score is : %d", colorLines.getScore()), Toast.LENGTH_SHORT);
        backToast.show();
        AlertDialogWindow.showDialogWindow(this);
    }

    private void updateFieldView() {
        int numberOfId = gameSettings.getRows() * gameSettings.getColumns() + 1;//because id starts from 1
        for (int i = 1; i < numberOfId; i++) {
            int buttonId = i - 1;
            int row = buttonId / gameSettings.getRows();
            int column = buttonId % gameSettings.getColumns();
            ImageButton imageButton = (ImageButton) mainView.findViewById(i);
            Cell c = colorLines.getCell(row, column);
            Bitmap newButtonBitmap;
            if (!c.isEmpty()) {
                newButtonBitmap = Drawer.getBallBitmap(c.getCellColor(), viewSettings.getCellSize(),
                        viewSettings.getCellSize(), viewSettings.getBallRadius());
            } else {
                newButtonBitmap = null;
            }
            updateButtonView(imageButton, newButtonBitmap);
        }
        setContentView(mainView);
    }

    public void startNewGame() {
        colorLines = new ColorLines(gameSettings.getRows(), gameSettings.getColumns());
        updateNextColorsButtonsView();
        updateRecordAndScoreView(gameSettings.getRecord(), 0);
        updateFieldView();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            returnToMainMenu();
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press one more time for exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    public void returnToMainMenu() {
        try {
            Intent intent = new Intent(GameActivity.this, MainMenuActivity.class);
            startActivity(intent);//open new activity
            finish();//close this window
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

}
