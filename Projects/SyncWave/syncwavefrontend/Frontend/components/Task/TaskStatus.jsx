export default function TaskStatus({ tasks, onTaskClick, useDate }) {

  const groupTasksByDate = (tasks) => {
    const groupedTasks = {};

    tasks.forEach((task) => {
      const dateKey = task[useDate];
      if (!groupedTasks[dateKey]) {
        groupedTasks[dateKey] = [];
      }
      groupedTasks[dateKey].push(task);
    });
    return groupedTasks;
  };

  const groupedTasks = groupTasksByDate(tasks);
  const sortedDates = Object.keys(groupedTasks).sort(
      (a, b) => new Date(b) - new Date(a)
  );




  return (
      <div className="">
        {sortedDates.map((date) => (
            <div key={date}>
              <div className="flex flex-column m-5 text-left">
                <h3 className="">{date}</h3>
              </div>
              {groupedTasks[date].map((task, index) => (
                  <div
                      key={task.id}
                      className="flex align-items-center m-5 p-2 bg-primary-reverse border-round border-solid"
                      onClick={() => onTaskClick(task.id)}
                      style={{ cursor: "pointer" }}
                  >
                    <div className="flex-row flex align-items-center p-3 gap-5">
                      <h3>{task.title}</h3>
                      <p>{task.description}</p>
                      <span>Status: {task.status}</span>
                    </div>
                    <div>
                      <p>Date: {task[useDate]}</p>
                    </div>
                  </div>
              ))}
            </div>
        ))}
      </div>
  );
}
