package tistory.zxcv5500.reminders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RemindersActivity extends AppCompatActivity {

	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminders);

		mListView = (ListView) findViewById(R.id.reminders_list_view);
		// arrayAdapter는 MVC(모델-뷰-컨트롤러)의 컨트롤러 역할을 한다.
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				// 컨텍스트(Context)
				this,
				// 레이아웃 뷰(View)
				R.layout.reminders_row,
				// 행의 텍스트 뷰(TextView)
				R.id.row_text,
				// ListView에 제공하는 데이터를 갖는 데이터 모델
				new String[] {"first record", "second record", "third record"}
		);

		mListView.setAdapter(arrayAdapter);

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
