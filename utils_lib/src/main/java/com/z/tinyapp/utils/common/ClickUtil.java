package com.z.tinyapp.utils.common;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * 点击事件工具类
 */
public abstract class ClickUtil implements OnClickListener {

	@Override
	public void onClick(View v) {
		// 执行点击的方法
		doClick(v);

		// 添加点击日志
		String log = AddRecord(v);

		try {
			LogUtil.addLog(v.getContext(), log);
		} catch (Exception e) {
		}
	}

	// 点击事件处理方法
	public abstract void doClick(View v);

	// 重写该方法
	public String AddRecord(View v) {
		return null;
	}
}
