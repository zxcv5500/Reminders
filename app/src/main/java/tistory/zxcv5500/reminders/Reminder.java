package tistory.zxcv5500.reminders;

/**
 * Created by zxcv5500 on 2017-06-06.
 */

public class Reminder {
	private int mId;
	private String mContent;
	private int mImportant;

	public Reminder(int id, String content, int important) {
		mId = id;
		mContent = content;
		mImportant = important;
	}

	public int getId() {
		return mId;
	}

	public void setId(int id) {
		mId = id;
	}

	public String getContent() {
		return mContent;
	}

	public void setContent(String content) {
		mContent = content;
	}

	public int getImportant() {
		return mImportant;
	}

	public void setImportant(int important) {
		mImportant = important;
	}
}
