import {TabMenu} from "primereact/tabmenu";
import {useEffect, useState} from "react";
import TaskStatus from "./TaskStatus";
import TaskDetail from "./TaskDetail";
import {loadAllTaskByUser} from "../../lib/task";

export default function TaskHeader() {
    const [selectedIndex, setSelectedIndex] = useState(0);
    const [selectedTaskId, setSelectedTaskId] = useState(null);
    const [backendTasks, setBackendTasks] = useState([]);
    const [userTasks, setUserTasks] = useState([]);

    useEffect(() => {
        const loadData = async () => {
            try {
                const response = await loadAllTaskByUser();
                setBackendTasks(response);
            } catch (e) {
                console.log(e);
            }
        };
        loadData();
    }, []);

    console.log("backend" + JSON.stringify(backendTasks))

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        return date.toLocaleDateString();
    };

    useEffect(() => {
        setUserTasks(backendTasks.map(taskEntry => ({
            id: taskEntry.task.id,
            title: taskEntry.task.title,
            description: taskEntry.task.description,
            status: taskEntry.status,
            dueAt: formatDate(taskEntry.task.dueAt),
            createdAt: formatDate(taskEntry.task.createdAt),
            updatedAt: formatDate(taskEntry.task.updatedAt)
        })));
    }, [backendTasks]);

    console.log("userTasks: "+ JSON.stringify(userTasks))

    const filterAndSortTasks = (tasks, status, dateField) => {
        return tasks
            .filter(task => task.status === status)
            .sort((a, b) => new Date(a[dateField]) - new Date(b[dateField]));
    };

    const currentTasks = filterAndSortTasks(userTasks, "CURRENT", "createdAt");
    const overdueTasks = filterAndSortTasks(userTasks, "OVERDUE", "dueAt");
    const doneTasks = filterAndSortTasks(userTasks, "DONE", "updatedAt");

    const elements = [
        <div className="items-center justify-center">
            <TaskStatus tasks={currentTasks} onTaskClick={setSelectedTaskId} useDate="createdAt"/>
        </div>,
        <div className="items-center justify-center">
            <TaskStatus tasks={overdueTasks} onTaskClick={setSelectedTaskId} useDate="dueAt"/>
        </div>,
        <div className="items-center justify-center">
            <TaskStatus tasks={doneTasks} onTaskClick={setSelectedTaskId} useDate="dueAt"/>
        </div>
    ];

    const items = [
        {
            label: 'Current', icon: 'pi pi-clock', command: () => {
                setSelectedIndex(0)
            }
        },
        {
            label: 'Overdue', icon: 'pi pi-exclamation-circle', command: () => {
                setSelectedIndex(1)
            }
        },
        {
            label: 'Done', icon: 'pi pi-check-circle', command: () => {
                setSelectedIndex(2)
            }
        }
    ];


    console.log("usersTask", userTasks)
    console.log("usersTask", selectedTaskId)

    return (
        <div className="flex flex-column h-full">
            <div className="border-none">
                <div className="fixed card w-screen surface-card border-noround">
                    <TabMenu model={items} className=""/>
                </div>
            </div>
            <div className="pt-6 border-none">
                {selectedTaskId === null ? (
                    elements[selectedIndex]
                ) : (
                    <TaskDetail task={userTasks.find(t => t.id === selectedTaskId)} setTaskId={setSelectedTaskId} taskUser={backendTasks}/>
                )}
            </div>
        </div>
    );
}
