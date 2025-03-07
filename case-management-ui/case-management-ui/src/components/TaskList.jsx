import React from "react";
import TaskItem from "./TaskItem";

const TaskList = ({ tasks }) => {
    if (!tasks || tasks.length === 0) return <p>No tasks available.</p>;

    return (
        <div>
            <h3>Tasks</h3>
            <ul style={{ listStyle: "none", padding: 0 }}>
                {tasks
                    .sort((a, b) => new Date(b.id) - new Date(a.id))
                    .map((task) => (
                        <TaskItem key={task.id} task={task} />
                    ))}
            </ul>
        </div>
    );
};

export default TaskList;
