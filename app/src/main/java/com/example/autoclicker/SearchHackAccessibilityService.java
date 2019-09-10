package com.example.autoclicker;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class SearchHackAccessibilityService extends BaseAccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        String packageName = event.getPackageName().toString();
        if (packageName.equals("it.colucciweb.sstpvpnclient")){
            if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                AccessibilityNodeInfo rootNode = getRootInActiveWindow();
                keepAppPraise(rootNode, "it.colucciweb.sstpvpnclient:id/add_button");
                //goThrough(rootNode);
            }
        }
    }

    public static void keepAppPraise(AccessibilityNodeInfo nodeInfo, String id) {
        if (nodeInfo != null) {
            // 该界面下所有 ViewId 节点
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(id);
            for (AccessibilityNodeInfo item : list) {
                if (item.isClickable()) {
                    Log.d("keepAppPraise", "keepAppPraise = " + item.getClassName());
                    item.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }
    }
    private boolean goThrough(AccessibilityNodeInfo info) {
        if (info.getChildCount() == 0) {
            if (info.getText() != null && info.getText().toString().contains("搜索")) {
                if ("在沪江网校中搜索".equals(info.getText().toString()) && "android.widget.TextView".equals(info.getClassName())) {
                    AccessibilityNodeInfo parent = info;
                    while (parent != null) {
                        if (parent.isClickable()) {
                            parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            break;
                        }
                        parent = parent.getParent();
                    }
                } else if ("输入关键字搜索".equals(info.getText().toString()) && "android.widget.EditText".equals(info.getClassName())) {
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("paste", "雅思英语");
                    clipboardManager.setPrimaryClip(clipData);
                    info.performAction(AccessibilityNodeInfo.ACTION_PASTE);
                } else if ("搜索".equals(info.getText().toString()) && "android.widget.TextView".equals(info.getClassName())) {
                    AccessibilityNodeInfo parent = info;
                    while (parent != null) {
                        if (parent.isClickable()) {
                            parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            break;
                        }
                        parent = parent.getParent();
                    }
                    return true;
                }
            }
        } else {
            for (int i = 0; i < info.getChildCount(); i++) {
                if (info.getChild(i) != null) {
                    goThrough(info.getChild(i));
                }
            }
        }
        return false;
    }
}
