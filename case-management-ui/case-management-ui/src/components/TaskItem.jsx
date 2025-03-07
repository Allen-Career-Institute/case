import React from "react";

const TaskItem = ({ task }) => (
    <table style={{ border: "1px solid black", width: "100%", marginBottom: "10px", borderCollapse: "collapse" }}>
        <colgroup>
            <col style={{ width: "10%" }} />
            <col style={{ width: "70%" }} />
            <col style={{ width: "20%" }} />
        </colgroup>
        <tbody>
        <tr>
            <td rowSpan="2" style={{ border: "1px solid black", padding: "8px", fontWeight: "bold", textAlign: "center", verticalAlign: "middle" }}>
                {task.taskType}
            </td>
            <td style={{ border: "1px solid black", padding: "8px" }}>
                <strong>Description:</strong> {task.description}
            </td>
            <td style={{ border: "1px solid black", padding: "8px" }}>
                <strong>Status:</strong> {task.status}
            </td>
        </tr>
        <tr>
            <td colSpan="2" style={{ border: "1px solid black", padding: "8px" }}>
                <strong>Transcript:</strong> {task.transcript || "N/A"}
            </td>
        </tr>
        </tbody>
    </table>
);

export default TaskItem;
