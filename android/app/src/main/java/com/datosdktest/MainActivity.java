package com.datosdktest;

import android.os.Bundle;
import android.widget.Toast;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactRootView;

import datos_health.com.sdk.DatosHealthSDK;
import datos_health.com.sdk.OnFinishDatosHealthListener;
import datos_health.com.sdk.app.utils.DatosSDKLogUtils;

public class MainActivity extends ReactActivity {
  String serverUrl = "https://telesheba-test.datosconnectedhealth.com";
  boolean sdkInitiated = false;
  boolean canShowSDKUI = false;
  String authToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJKU29oaV9IUFZlOG9XcU1temJSMzhUUXlTZXFoRXBqZWVFMFRnWWpuMnZvIn0.eyJleHAiOjE2NzM3OTA1MjgsImlhdCI6MTY3Mzc5MDIyOCwianRpIjoiNGJjY2M3YzUtNzdjNi00Mjk5LTgwNjktNDA3NzhmNmZiOWNiIiwiaXNzIjoiaHR0cHM6Ly90ZWxlc2hlYmEtdGVzdC5kYXRvc2Nvbm5lY3RlZGhlYWx0aC5jb20vYXV0aC9yZWFsbXMvZGF0b3MiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiOTNlZTIzYmQtNDgyOS00NjY5LWE1MzMtZWZlZWVlMWU3ODFhIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoid2ViIiwic2Vzc2lvbl9zdGF0ZSI6IjdhOTE3NGE4LWQwZTUtNDJiZi1hZGVjLTIyNTNjODA2Yzk1NyIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cHM6Ly90ZWxlc2hlYmEtdGVzdC5kYXRvc2Nvbm5lY3RlZGhlYWx0aC5jb20iXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIlBBVElFTlRTIiwiZGVmYXVsdC1yb2xlcy1kYXRvcyIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJzaWQiOiI3YTkxNzRhOC1kMGU1LTQyYmYtYWRlYy0yMjUzYzgwNmM5NTciLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJQQVRJRU5UUyIsImRlZmF1bHQtcm9sZXMtZGF0b3MiLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJuYW1lIjoiT2ZpciBadWtlcm1hbiIsInByZWZlcnJlZF91c2VybmFtZSI6ImRyb2ZpcnoiLCJnaXZlbl9uYW1lIjoiT2ZpciIsImZhbWlseV9uYW1lIjoiWnVrZXJtYW4ifQ.D5CswBKc7Mt0mgvjw3qpdNXtntAVXoAoeA7mg96Bjd0GQ_XYNROBi2pgroSuo1nafFjkoWVze27SLKmwQK42pxXyAs-UzKQ4wfuncaN5f_3FjPLn2EgTHRzfc52_4dgSWjdZ93XIWF7FnQz4XmonQBUKcc-uAs-esdafUahkrvZpYgNQRkzrX7nufYqczz1GX8XDWnAg1iu1q2Fj_nNW-0bbKGudbA94vq3ZNqaBtKRoRqc1eMQSa1HUTmUFYgPkRqU14IitE1aAkB4eKhuWrYFJFXXisMUqSwfwtXV2-d9Re7tYO2Nf0p8yYPlrf9zhFmk1FtaQNSADgVK8Hw4LuA";
  String refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI0OGYwYzczMC01MDc3LTQ5YzUtOTY3Yi1jN2Y5MGQxZTc5M2UifQ.eyJleHAiOjE2NzM3OTIwMjgsImlhdCI6MTY3Mzc5MDIyOCwianRpIjoiNjA4NTQzNDMtZjkxNy00MTc3LTg5NDMtZjU2ZTQwMjMwYTA0IiwiaXNzIjoiaHR0cHM6Ly90ZWxlc2hlYmEtdGVzdC5kYXRvc2Nvbm5lY3RlZGhlYWx0aC5jb20vYXV0aC9yZWFsbXMvZGF0b3MiLCJhdWQiOiJodHRwczovL3RlbGVzaGViYS10ZXN0LmRhdG9zY29ubmVjdGVkaGVhbHRoLmNvbS9hdXRoL3JlYWxtcy9kYXRvcyIsInN1YiI6IjkzZWUyM2JkLTQ4MjktNDY2OS1hNTMzLWVmZWVlZTFlNzgxYSIsInR5cCI6IlJlZnJlc2giLCJhenAiOiJ3ZWIiLCJzZXNzaW9uX3N0YXRlIjoiN2E5MTc0YTgtZDBlNS00MmJmLWFkZWMtMjI1M2M4MDZjOTU3Iiwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwic2lkIjoiN2E5MTc0YTgtZDBlNS00MmJmLWFkZWMtMjI1M2M4MDZjOTU3In0.4A2fEcTw-oiD4FQsArOKVM4vMF7T3-yG-4E6zzLPLtA";
  MainActivity that = this;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (!sdkInitiated) {
      DatosHealthSDK.initialize(this, serverUrl, null, "en", false, false, false,
              getString(R.string.app_name));
      // DatosSDKLogUtils.initialize(true);
      sdkInitiated = true;
      this.populateUserData();
    }
  }

  private void populateUserData() {
    if (authToken == null || authToken.isEmpty()) {
      Toast.makeText(getApplicationContext(), "Error in populateUserData no auth token", Toast.LENGTH_LONG).show();
      return;
    }
    DatosHealthSDK.populateUserDataAndConfiguration(authToken, refreshToken, new
            OnFinishDatosHealthListener() {
              @Override
              public void onSucceess() {
                DatosHealthSDK.show(new OnFinishDatosHealthListener() {
                  @Override
                  public void onSucceess() {
//                    Toast.makeText(getApplicationContext(), "good", Toast.LENGTH_LONG).show();
                  }
                  @Override
                  public void onFailure(DatosHealthSDK.ErrorCodes s) {
                    Toast.makeText(getApplicationContext(), "SDK failed to show "+s,
                            Toast.LENGTH_LONG).show();
                  }
                });
              }
              @Override
              public void onFailure(DatosHealthSDK.ErrorCodes s) {
                Toast.makeText(getApplicationContext(), "User data failed to populate "+s, Toast.LENGTH_LONG).show();
              }
            });
  }

  public void showUI() {

  }

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "datoSDKTest";
  }

  /**
   * Returns the instance of the {@link ReactActivityDelegate}. There the RootView is created and
   * you can specify the renderer you wish to use - the new renderer (Fabric) or the old renderer
   * (Paper).
   */
  @Override
  protected ReactActivityDelegate createReactActivityDelegate() {
    return new MainActivityDelegate(this, getMainComponentName());
  }

  public static class MainActivityDelegate extends ReactActivityDelegate {
    public MainActivityDelegate(ReactActivity activity, String mainComponentName) {
      super(activity, mainComponentName);
    }

    @Override
    protected ReactRootView createRootView() {
      ReactRootView reactRootView = new ReactRootView(getContext());
      // If you opted-in for the New Architecture, we enable the Fabric Renderer.
      reactRootView.setIsFabric(BuildConfig.IS_NEW_ARCHITECTURE_ENABLED);
      return reactRootView;
    }

    @Override
    protected boolean isConcurrentRootEnabled() {
      // If you opted-in for the New Architecture, we enable Concurrent Root (i.e. React 18).
      // More on this on https://reactjs.org/blog/2022/03/29/react-v18.html
      return BuildConfig.IS_NEW_ARCHITECTURE_ENABLED;
    }
  }
}
