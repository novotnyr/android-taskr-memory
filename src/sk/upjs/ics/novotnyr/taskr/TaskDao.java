package sk.upjs.ics.novotnyr.taskr;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TaskDao {
	public static final TaskDao INSTANCE = new TaskDao();

	private List<Task> tasks = new LinkedList<Task>();

	private long idGenerator = 0;
	
	public TaskDao() {
		Task zubar = new Task();
		zubar.setName("Zubár");
		
		saveOrUpdate(zubar);

		Task obed = new Task();
		obed.setName("Obed");
		obed.setDone(true);
		saveOrUpdate(obed);
	}
	
	public void saveOrUpdate(Task task) {
		if(task.getId() == null) {
			task.setId(idGenerator++);
			tasks.add(task);
		} else {
			Iterator<Task> iterator = tasks.iterator();
			while(iterator.hasNext()) {
				Task t = iterator.next();
				if(t.getId() == task.getId()) {
					iterator.remove();
					break;
				}
			}
			tasks.add(task);
		}
	}

	public List<Task> list() {
		return new LinkedList<Task>(this.tasks);
	}

	public Task getTask(long taskId) {
		for (Task task : this.tasks) {
			if(task.getId() == taskId) {
				return task;
			}
		}
		return null;
	}
	
}
