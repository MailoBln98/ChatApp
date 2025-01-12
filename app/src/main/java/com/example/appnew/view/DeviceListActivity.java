package com.example.appnew.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.appnew.R;
import com.google.android.apps.common.testing.accessibility.framework.BuildConfig;

import java.util.ArrayList;
import java.util.Set;

public class DeviceListActivity extends Activity {

    private static final int REQUEST_CODE_BLUETOOTH_PERMISSIONS = 1;
    private boolean isPermissionRequested = false;

    private BluetoothAdapter bluetoothAdapter;
    private ArrayAdapter<String> pairedDevicesAdapter;
    private ArrayAdapter<String> availableDevicesAdapter;
    private ArrayList<BluetoothDevice> availableDevices;
    private ProgressBar progressBar;

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device != null && !availableDevices.contains(device)) {
                    availableDevices.add(device);
                    availableDevicesAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                progressBar.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_device_list);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not supported on this device", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        progressBar = findViewById(R.id.progress_bar);

        pairedDevicesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        availableDevicesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        availableDevices = new ArrayList<>();

        ListView pairedListView = findViewById(R.id.paired_devices_list);
        ListView availableListView = findViewById(R.id.available_devices_list);

        pairedListView.setAdapter(pairedDevicesAdapter);
        availableListView.setAdapter(availableDevicesAdapter);

        pairedListView.setOnItemClickListener(deviceClickListener);
        availableListView.setOnItemClickListener(deviceClickListener);

        findViewById(R.id.cancel_button).setOnClickListener(v -> finish());

        // Lade Testdaten, wenn Debug-Modus aktiv ist
        loadMockDataForTesting();

        // Starte die Berechtigungsprüfung
        if (!isPermissionRequested) {
            requestBluetoothPermissions();
        }
    }

    private void requestBluetoothPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            isPermissionRequested = true;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN},
                    REQUEST_CODE_BLUETOOTH_PERMISSIONS);
        } else {
            displayPairedDevices();
            discoverDevices();
        }
    }

    private void displayPairedDevices() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Bluetooth connect permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                pairedDevicesAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            pairedDevicesAdapter.add("No paired devices found");
        }
    }

    private void loadMockDataForTesting() {
        if (BuildConfig.DEBUG) {
            pairedDevicesAdapter.add("TestDevice\n00:11:22:33:44:55");
            availableDevicesAdapter.add("MockDevice\n66:77:88:99:AA:BB");
        }
    }

    private void discoverDevices() {
        progressBar.setVisibility(View.VISIBLE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Bluetooth scan permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }

        bluetoothAdapter.startDiscovery();
        registerReceiver(broadcastReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        registerReceiver(broadcastReceiver, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));
    }

    @SuppressLint("MissingPermission")
    private final AdapterView.OnItemClickListener deviceClickListener = (adapterView, view, position, id) -> {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Bluetooth connect permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }

        bluetoothAdapter.cancelDiscovery();
        String info = ((TextView) view).getText().toString();
        String address = info.substring(info.length() - 17);

        Intent intent = new Intent();
        intent.putExtra("deviceAddress", address);
        setResult(Activity.RESULT_OK, intent);
        finish();
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (IllegalArgumentException e) {
            // Receiver not registered
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_BLUETOOTH_PERMISSIONS) {
            boolean allGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
            if (allGranted) {
                displayPairedDevices();
                discoverDevices();
            } else {
                Toast.makeText(this, "Permissions denied. Some features may not work.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public ArrayAdapter<String> getPairedDevicesAdapter() {
        return pairedDevicesAdapter;
    }

    public ArrayAdapter<String> getAvailableDevicesAdapter() {
        return availableDevicesAdapter;
    }
}