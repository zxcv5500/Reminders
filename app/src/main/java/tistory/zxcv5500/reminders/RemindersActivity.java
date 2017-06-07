package tistory.zxcv5500.reminders;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class RemindersActivity extends AppCompatActivity {

	private ListView mListView;
	private RemindersDbAdapter mDbAdapter;
	private RemindersSimpleCursorAdapter mCursorAdapter;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminders);

		mListView = (ListView) findViewById(R.id.reminders_list_view);
		mListView.setDivider(null);
		mDbAdapter = new RemindersDbAdapter(this);
		mDbAdapter.open();

		if (savedInstanceState == null) {
			// 중복을 방지하기 위해 기존의 데이터베이스 데이터를 삭제한다
			mDbAdapter.deleteAllReminders();
			// 샘플 데이터를 데이터 베이스에 추가한다
			insertSomeReminders();
		}

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

		// ListView의 항목을 터치하면 이 리스너의 onItemClick()메서드가 호출된다
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int masterListPosition, long id) {
				AlertDialog.Builder builder = new AlertDialog.Builder(RemindersActivity.this);
				ListView modeListView = new ListView(RemindersActivity.this);
				String[] modes = new String[] { "Edit Reminder", "Delete Reminder" };
				ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(
						RemindersActivity.this,
						android.R.layout.simple_list_item_1,
						android.R.id.text1,
						modes);
				modeListView.setAdapter(modeAdapter);
				builder.setView(modeListView);
				final Dialog dialog = builder.create();
				dialog.show();
				modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// 메모 데이터 변경
						if (position == 0) {
							Toast.makeText(RemindersActivity.this, "edit " + masterListPosition, Toast.LENGTH_SHORT).show();
						// 메모 데이터 삭제
						} else {
							Toast.makeText(RemindersActivity.this, "delete " + masterListPosition, Toast.LENGTH_SHORT).show();
						}
						dialog.dismiss();
					}
				});
			}
		});

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
			mListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
				@Override
				public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

				}

				@Override
				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
					MenuInflater inflater = mode.getMenuInflater();
					inflater.inflate(R.menu.cam_menu, menu);
					return true;
				}

				@Override
				public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
					return false;
				}

				@Override
				public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
					switch (item.getItemId()) {
						case R.id.menu_item_delete_reminder:
							for (int nC = mCursorAdapter.getCount() - 1; nC >= 0; nC--) {
								if (mListView.isItemChecked(nC)) {
									mDbAdapter.deleteReminderById(getIdFromPosition(nC));
								}
							}
							mode.finish();
							mCursorAdapter.changeCursor(mDbAdapter.fetchAllReminders());
							return true;
					}
					return false;
				}

				@Override
				public void onDestroyActionMode(ActionMode mode) {

				}
			});
		}

	}

	private int getIdFromPosition(int nC) {
		return (int) mCursorAdapter.getItemId(nC);
	}

	private void insertSomeReminders() {
		mDbAdapter.createReminder("Buy Learn Android Studio", true);
		mDbAdapter.createReminder("Send Dad birthday gift", false);
		mDbAdapter.createReminder("Dinner at the Gage on Friday", false);
		mDbAdapter.createReminder("String squash racket", false);
		mDbAdapter.createReminder("Shovel and salt walkways", false);
		mDbAdapter.createReminder("Prepare Advanced Android syllabus", true);
		mDbAdapter.createReminder("Buy new office chair", false);
		mDbAdapter.createReminder("call Auto-body shop for quote", false);
		mDbAdapter.createReminder("Renew membership to club", false);
		mDbAdapter.createReminder("Buy new Galaxy Android phone", true);
		mDbAdapter.createReminder("Sell old Android phone - auction", false);
		mDbAdapter.createReminder("Buy new paddles for kayaks", false);
		mDbAdapter.createReminder("Call accountant about tax returns", false);
		mDbAdapter.createReminder("Buy 300000 shares of Google", false);
		mDbAdapter.createReminder("Call the Dalai Lama back", true);
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
