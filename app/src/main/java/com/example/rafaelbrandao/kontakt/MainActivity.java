package com.example.rafaelbrandao.kontakt;

import android.Manifest;
import android.os.Build;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.ProximityManagerFactory;
import com.kontakt.sdk.android.ble.manager.listeners.EddystoneListener;
import com.kontakt.sdk.android.ble.manager.listeners.IBeaconListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleEddystoneListener;
import com.kontakt.sdk.android.ble.manager.listeners.simple.SimpleIBeaconListener;
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;
import com.kontakt.sdk.android.common.profile.IEddystoneDevice;
import com.kontakt.sdk.android.common.profile.IEddystoneNamespace;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//public class MainActivity extends AppCompatActivity implements BeaconConsumer {
public class MainActivity extends AppCompatActivity {

    //Permissions
    private static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.BLUETOOTH_PRIVILEGED
    };
//
//    //View
//    ListView listView;
//
//    IBeaconAdapter adapter;
//
//    List<IBeaconDevice> iBeaconz;
//
//    //ALTBeacon
//
//    protected static final String TAG = "MonitoringActivity";
//    private BeaconManager beaconManager;
//
//    //Kontakt
//    //    private ProximityManager proximityManager;
//
//    //Life Cycle
////    @RequiresApi(api = Build.VERSION_CODES.M)
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        requestPermissions(INITIAL_PERMS, 1337);
////        super.onCreate(savedInstanceState);
////
////        setView();
////
//////        proximityManager = ProximityManagerFactory.create(this);
//////        proximityManager.setIBeaconListener(createIBeaconListener());
//////        proximityManager.setEddystoneListener(createEddystoneListener());
////
////
////        beaconManager = BeaconManager.getInstanceForApplication(this);
////
//////        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25")); //ALTBEACON
//////        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("x,s:0-1=feaa,m:2-2=20,d:3-3,d:4-5,d:6-7,d:8-11,d:12-15")); //EDDYSTONE TLM
//////        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("s:0-1=feaa,m:2-2=00,p:3-3:-41,i:4-13,i:14-19")); //EDDYSTONE UID
//////        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("s:0-1=feaa,m:2-2=10,p:3-3:-41,i:4-20v")); //EDDYSTONE URL
//////        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")); //IBEACON
////
////        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
////
////        beaconManager.bind(this);
////    }
////
////    @Override
////    protected void onStart() {
////        super.onStart();
//////        startScanning();
////    }
////
////    @Override
////    protected void onStop() {
//////        proximityManager.stopScanning();
////        super.onStop();
////    }
////
////    @Override
////    protected void onDestroy() {
//////        proximityManager.disconnect();
//////        proximityManager = null;
////        beaconManager.unbind(this);
////        super.onDestroy();
////    }
////
////    //Set View
////
////    private void setView(){
////        setContentView(R.layout.activity_main);
////
////        listView = (ListView) findViewById(R.id.listView);
////
////        iBeaconz = new ArrayList<IBeaconDevice>();
////
////        adapter = new IBeaconAdapter(iBeaconz, this);
////
////        listView.setAdapter(adapter);
////    }
////
////    //ALT
////    @Override
////    public void onBeaconServiceConnect() {
////        beaconManager.setMonitorNotifier(new MonitorNotifier() {
////            @Override
////            public void didEnterRegion(Region region) {
////                Log.i(TAG, "I just saw an beacon for the first time!");
////            }
////
////            @Override
////            public void didExitRegion(Region region) {
////                Log.i(TAG, "I no longer see an beacon");
////            }
////
////            @Override
////            public void didDetermineStateForRegion(int state, Region region) {
////                Log.i(TAG, "I have just switched from seeing/not seeing beacons: "+state);
////            }
////        });
////
////        try {
////            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
////        } catch (RemoteException e) {   }
////
////        beaconManager.setRangeNotifier(new RangeNotifier() {
////            @Override
////            public void didRangeBeaconsInRegion(final Collection<Beacon> rangedBeacons, Region region) {
////                runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
//////                        statusView.setText("Beacons found: " + rangedBeacons.size());
//////                        beaconAdapter.replaceWith(rangedBeacons);
////
////                        Log.i(TAG, "Agora fincionou e achou : "+rangedBeacons.size());
////                    }
////                });
////            }
////        });
////
////        try {
////            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
////        } catch (RemoteException e) {   }
////    }
////
////    //Kontakt
////
//////    private void startScanning() {
//////        proximityManager.connect(new OnServiceReadyListener() {
//////            @Override
//////            public void onServiceReady() {
//////                proximityManager.startScanning();
//////            }
//////        });
//////    }
////
//////    private IBeaconListener createIBeaconListener() {
//////        return new SimpleIBeaconListener() {
//////
//////            @Override
//////            public void onIBeaconsUpdated(List<IBeaconDevice> iBeacons, IBeaconRegion region) {
//////                //Beacons updated
//////
//////                adapter.refreshBeacons(iBeacons);
//////            }
//////        };
//////    }
////
//////    private EddystoneListener createEddystoneListener() {
//////        return new SimpleEddystoneListener() {
//////            @Override
//////            public void onEddystoneDiscovered(IEddystoneDevice eddystone, IEddystoneNamespace namespace) {
//////                Log.i("Eddystone", "Eddystone discovered: " + eddystone.toString());
//////            }
//////        };
//////    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
////        this.beaconAdapter = new BeaconCollectionAdapter(this);
////        this.listView = (ListView) findViewById(R.id.listView);
////        this.statusView = (TextView) findViewById(R.id.currentStatus);
////        listView.setAdapter(this.beaconAdapter);
//        beaconManager = BeaconManager.getInstanceForApplication(this);
//        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
//        beaconManager.bind(this);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        beaconManager.unbind(this);
//    }
//
//    @Override
//    public void onBeaconServiceConnect() {
//        beaconManager.setMonitorNotifier(new MonitorNotifier() {
//            @Override
//            public void didEnterRegion(Region region) {
//                Log.i(TAG, "I just saw an beacon for the first time!");
//            }
//
//            @Override
//            public void didExitRegion(Region region) {
//                Log.i(TAG, "I no longer see an beacon");
//            }
//
//            @Override
//            public void didDetermineStateForRegion(int state, Region region) {
//                Log.i(TAG, "I have just switched from seeing/not seeing beacons: "+state);
//            }
//        });
//
//        try {
//            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
//        } catch (RemoteException e) {   }
//
//        beaconManager.setRangeNotifier(new RangeNotifier() {
//            @Override
//            public void didRangeBeaconsInRegion(final Collection<Beacon> rangedBeacons, Region region) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.i(TAG, "Agora fincionou e achou : "+rangedBeacons.size());
//                    }
//                });
//            }
//        });
//
//        try {
//            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
//        } catch (RemoteException e) {   }
//    }

    private ProximityManager proximityManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestPermissions(INITIAL_PERMS, 1337);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        KontaktSDK.initialize("bkfazgOFWMEYzLPIEpsEqoqdqIOsQlVa");

        proximityManager = ProximityManagerFactory.create(this);
        proximityManager.setIBeaconListener(createIBeaconListener());
        proximityManager.setEddystoneListener(createEddystoneListener());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
//        requestPermissions(INITIAL_PERMS, 1337);
        super.onStart();
        startScanning();
    }

    @Override
    protected void onStop() {
        proximityManager.stopScanning();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        proximityManager.disconnect();
        proximityManager = null;
        super.onDestroy();
    }

    private void startScanning() {
        proximityManager.connect(new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                proximityManager.startScanning();
            }
        });
    }

    private IBeaconListener createIBeaconListener() {
        return new SimpleIBeaconListener() {
            @Override
            public void onIBeaconDiscovered(IBeaconDevice ibeacon, IBeaconRegion region) {
                Log.i("Sample", "IBeacon discovered: " + ibeacon.toString());
            }
        };
    }

    private EddystoneListener createEddystoneListener() {
        return new SimpleEddystoneListener() {
            @Override
            public void onEddystoneDiscovered(IEddystoneDevice eddystone, IEddystoneNamespace namespace) {
                Log.i("Sample", "Eddystone discovered: " + eddystone.toString());
            }
        };
    }
}