package com.jacksen.appshortcuts;

import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView maxShortcutsCount, staticShortcutsCount, pinnedShortcutsCount, dynamicShortcutsCount, maxWidth, maxHeight;

    private Button addShortcutBtn;

    private ShortcutManager shortcutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        maxShortcutsCount = (TextView) findViewById(R.id.max_count_shortcuts);
        staticShortcutsCount = (TextView) findViewById(R.id.static_shortcuts_count);
        pinnedShortcutsCount = (TextView) findViewById(R.id.pinned_shortcuts_count);
        dynamicShortcutsCount = (TextView) findViewById(R.id.dynamic_shortcuts_count);
        maxWidth = (TextView) findViewById(R.id.max_width);
        maxHeight = (TextView) findViewById(R.id.max_height);

        addShortcutBtn = (Button) findViewById(R.id.add_shortcut_btn);

        addShortcutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                    addOneShortcut();
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            initViewData();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    public void initViewData() {
        shortcutManager = getSystemService(ShortcutManager.class);

        int maxShortcutCount = shortcutManager.getMaxShortcutCountPerActivity();
        maxShortcutsCount.setText(String.valueOf(maxShortcutCount));

        List<ShortcutInfo> shortcutInfos = shortcutManager.getManifestShortcuts();
        staticShortcutsCount.setText(String.valueOf(shortcutInfos.size()));

        List<ShortcutInfo> pinnedShortcutInfos = shortcutManager.getPinnedShortcuts();
        pinnedShortcutsCount.setText(String.valueOf(pinnedShortcutInfos.size()));

        List<ShortcutInfo> dynamicShortcutInfos = shortcutManager.getDynamicShortcuts();
        dynamicShortcutsCount.setText(String.valueOf(dynamicShortcutInfos.size()));

        int iconMaxWidth = shortcutManager.getIconMaxWidth();
        int iconMaxHeight = shortcutManager.getIconMaxHeight();
        maxWidth.setText(String.valueOf(iconMaxWidth));
        maxHeight.setText(String.valueOf(iconMaxHeight));
    }

    /**
     * add one shortcut
     */
    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    public void addOneShortcut() {

//        Intent intent = new Intent(this, FifthActivity.class);
//        intent.setAction(Intent.ACTION_VIEW);

        ShortcutInfo shortcutInfo = new ShortcutInfo.Builder(this, "new shortcut")
                .setShortLabel("tel sb.")
                .setLongLabel("call sb up!")
                .setIcon(Icon.createWithResource(this, R.drawable.ic_color_lens_black_24dp))
                .setIntent(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:10086")))
                .build();
        shortcutManager.setDynamicShortcuts(Collections.singletonList(shortcutInfo));
    }
}
