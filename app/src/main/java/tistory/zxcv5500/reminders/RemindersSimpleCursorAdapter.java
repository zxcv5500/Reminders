package tistory.zxcv5500.reminders;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zxcv5500 on 2017-06-06.
 */

public class RemindersSimpleCursorAdapter extends SimpleCursorAdapter {

	public RemindersSimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
	}

	// ViewHolder 객체를 사용하려면 다음 두개의 메서드를 오버라이딩 하고
	// ViewHolder 클래스를 정의해야 한다.


	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return super.newView(context, cursor, parent);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		super.bindView(view, context, cursor);

		ViewHolder holder = (ViewHolder) view.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.colImp = cursor.getColumnIndexOrThrow(
					RemindersDbAdapter.COL_IMPORTANT
			);
			holder.listTab = view.findViewById(R.id.row_tab);
			view.setTag(holder);
		}

		if (cursor.getInt(holder.colImp) > 0) {
			holder.listTab.setBackgroundColor(context.getResources().getColor(R.color.orange));
		} else {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				holder.listTab.setBackgroundColor(context.getResources().getColor(R.color.green, context.getTheme()));
			} else {
				holder.listTab.setBackgroundColor(context.getResources().getColor(R.color.green));
			}

		}

	}

	static class ViewHolder {
		// 칼럼 인덱스를 저장한다.
		int colImp;
		// 뷰를 저장한다.
		View listTab;
	}
}
