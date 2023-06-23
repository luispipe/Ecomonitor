package unal.todosalau.ecomonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private boolean isCharging = false;
    private int batteryLevel = 0;
    private TextView batteryLevelTextView;
    private ProgressBar batteryProgressBar;
    private Button chargeButton;
    private Button dischargeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batteryLevelTextView = findViewById(R.id.batteryLevelTextView);
        batteryProgressBar = findViewById(R.id.batteryProgressBar);
        chargeButton = findViewById(R.id.chargeButton);
        dischargeButton = findViewById(R.id.dischargeButton);

        chargeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSolarCharging();
            }
        });

        dischargeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBatteryDischarging();
            }
        });
    }

    private void startSolarCharging() {
        isCharging = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isCharging && batteryLevel < 100) {
                    batteryLevel += 5;

                    if (batteryLevel > 100) {
                        batteryLevel = 100;
                    }

                    updateBatteryLevel();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void startBatteryDischarging() {
        isCharging = false;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isCharging && batteryLevel > 0) {
                    batteryLevel -= 5;

                    if (batteryLevel < 0) {
                        batteryLevel = 0;
                    }

                    updateBatteryLevel();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void updateBatteryLevel() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                batteryLevelTextView.setText("Battery Level: " + batteryLevel + "%");
                batteryProgressBar.setProgress(batteryLevel);
            }
        });
    }
}

