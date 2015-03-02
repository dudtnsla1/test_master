package testmaster.android.page;

import java.util.ArrayList;

import testmaster.android.testingboard.MainIntroActivity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class NotNullEditTextDecorator {
	PageChanger pageChanger;
	Context context;
	private int editedCount = 0;
	private ArrayList<EditText> editList = new ArrayList<EditText>();
	private ArrayList<String> textList = new ArrayList<String>();

	public NotNullEditTextDecorator(Context context, PageChanger pageChanger) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.pageChanger = pageChanger;
	}

	public void addEditText(EditText edit) {
		if (!edit.getText().toString().equals(""))
			editedCount++;
		Log.d(MainIntroActivity._DEBUG_TAG + " NotNullEditTextAdapter", "add" + edit.getText().toString() + editedCount);
		editList.add(edit);
		edit.addTextChangedListener(new NotNullTextWhatcher());
		if (editedCount == editList.size()) {
			pageChanger.setEnable();
		}
	}

	public String getText(EditText edit) {
		for (int i = 0; i < editList.size(); i++) {
			if (edit == editList.get(i)) {
				return textList.get(i);
			}
		}
		return null;
	}

	private class NotNullTextWhatcher implements TextWatcher {
		private int index;

		public NotNullTextWhatcher() {
			// TODO Auto-generated constructor stub
			textList.add("");
			index = textList.size() - 1;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			if (start == 0 && after > 0) {
				editedCount++;
				pageChanger.setDisable();
			}
			// TODO Auto-generated method stub		
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

			textList.set(index, s.toString());

			if (s.toString().equals("")){
				editedCount--;
			}

			if (editedCount == editList.size()) {
				pageChanger.setEnable();
			}
		}		
	}

	public void destroy() {
		editList.clear();
		textList.clear();
	}
}
