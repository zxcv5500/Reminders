package tistory.zxcv5500.reminders;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class RemindersActivity extends AppCompatActivity {

	private ListView mListView;
	private RemindersDbAdapter mDbAdapter;
	private RemindersSimpleCursorAdapter mCursorAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminders);

		mListView = (ListView) findViewById(R.id.reminders_list_view);
		mListView.setDivider(null);
		mDbAdapter = new RemindersDbAdapter(this);
		mDbAdapter.open();

		Cursor cursor = mDbAdapter.fetchAllReminders();

		String[] from = new String[] {
				RemindersDbAdapter.COL_CONTENT
		};

		int[] to = new int[] {
				R.id.row_text
		};

		mCursorAdapter = new RemindersSimpleCursorAdapter(
				// 컨택스트
				RemindersActivity.this,
				// 행의 레이아웃
				R.layout.reminders_row,
				// 커서
				cursor,
				// 데이터베이스에 정의된 칼럼
				from,
				// 레이아웃의 뷰 id
				to,
				// 플래그이며 사용하지 않음
				0
		);

		// 이제는 cursorAdapter(MVC의 컨트롤러)가
		// db(MVC의 모델)의 데이터로 ListView(MVC의 뷰)를 갱신한다.
		mListView.setAdapter(mCursorAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 메뉴를 인플레이트하여 객체로 생성한다.
		getMenuInflater().inflate(R.menu.menu_reminders, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_new:
			    // 새로운 메모 생성
				Log.d(getLocalClassName(), "create new Reminder");
				return true;

			case R.id.action_exit:
			    finish();
			    return true;

			default:
				return false;
		}

	}
}
