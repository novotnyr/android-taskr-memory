package sk.upjs.ics.novotnyr.taskr;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.CheckBox;
import android.widget.EditText;

public class TaskDetailActivity extends Activity {
	private static final int UNKNOWN_TASK_ID = -1;
	private TaskDao taskDao = TaskDao.INSTANCE;
	private EditText txtName;
	private CheckBox chkDone;
	
	private Task task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_detail);
		
		txtName = (EditText) findViewById(R.id.txtName);
		chkDone = (CheckBox) findViewById(R.id.chkDone);
		
		long taskId = retrieveTaskId();
		if(taskId != UNKNOWN_TASK_ID) {
			task = taskDao.getTask(taskId);
		} else {
			task = new Task();
		}
		txtName.setText(task.getName());
		chkDone.setChecked(task.isDone());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.task_detail, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_done:
			saveTaskAndFinish();
			return true;
		default:
			return super.onOptionsItemSelected(item);			
		}
	}


	private void saveTaskAndFinish() {
		task.setName(txtName.getText().toString());
		task.setDone(chkDone.isChecked());
		
		taskDao.saveOrUpdate(task);
		
		finish();
	}

	private long retrieveTaskId() {
		return getIntent().getLongExtra("taskId", UNKNOWN_TASK_ID);
	}
	

}
