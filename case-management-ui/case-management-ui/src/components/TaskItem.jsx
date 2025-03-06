import React from "react";

const TaskItem = ({ task }) => {
    return (
        <li
            style={{
                padding: "10px",
                borderBottom: "1px solid #ddd",
                background: task.status === "CLOSED" ? "lightgray" : "white",
            }}
        >
            <strong>{task.TaskType}</strong> | {task.description} <br />
            Assignee: {task.Assignee} | Status: {task.status} <br />
            Created: {new Date(task.created_datetime).toLocaleString()}
        </li>
    );
};

export default TaskItem;
