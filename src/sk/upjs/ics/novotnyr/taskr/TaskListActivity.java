package sk.upjs.ics.novotnyr.taskr;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class TaskListActivity extends ListActivity {
	private static final int EDIT_OR_CREATE_TASK_REQUESTCODE = 100;
	private TaskDao taskDao = TaskDao.INSTANCE;
	private BaseAdapter listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		resetListAdapter();
		registerForContextMenu(getListView());
	}

	private void resetListAdapter() {
		List<Task> tasks = taskDao.list();

		listAdapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, tasks);
		
		setListAdapter(listAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.task_list, menu);
		return true;
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		Intent intent = new Intent(this, TaskDetailActivity.class);
		// in ArrayAdapter, 'position' is same as 'id'
		// see http://stackoverflow.com/questions/5100071/whats-the-purpose-of-item-ids-in-android-listview-adapter
		
		// furthermore Task task = (Task) listView.getSelectedItem(); does not work for ListViews

		Task selectedTask = (Task) listView.getItemAtPosition(position);
		intent.putExtra("taskId", selectedTask.getId());
		
		startActivityForResult(intent, EDIT_OR_CREATE_TASK_REQUESTCODE);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_new:
			createNewTask();
			return true;
		default: 		
			return super.onOptionsItemSelected(item);
		}
		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		if (view.getId() == this.getListView().getId()) {
			getMenuInflater().inflate(R.menu.task_item, menu);
		}
	}		
	
	private void createNewTask() {
		Intent intent = new Intent(this, TaskDetailActivity.class);
		startActivityForResult(intent, EDIT_OR_CREATE_TASK_REQUESTCODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == EDIT_OR_CREATE_TASK_REQUESTCODE) {
			resetListAdapter();
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}
}
