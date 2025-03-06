import React from "react";
import TaskList from "./TaskList";

const statusOrder = ["OPEN", "ASSIGNED", "IN_PROGRESS", "CLOSED"];

const CaseDetails = ({ selectedCase }) => {
    if (!selectedCase) return <div>No associated tasks</div>;

    const handleStatusChange = (newStatus) => {
        const currentIndex = statusOrder.indexOf(selectedCase.status);
        const newIndex = statusOrder.indexOf(newStatus);

        if (newIndex > currentIndex) {
            const confirmChange = window.confirm(`Change status to ${newStatus}?`);
            if (confirmChange) {
                console.log(`Updating case ${selectedCase.id} to status: ${newStatus}`);
                // API call to update status can be placed here
            }
        } else {
            alert("Cannot go back to a previous status.");
        }
    };

    return (
        <div>
            <h2>{selectedCase.title}</h2>
            <p>{selectedCase.description}</p>

            {/* Status Bar */}
            <div style={{ display: "flex", marginBottom: "10px" }}>
                {statusOrder.map((status) => (
                    <div
                        key={status}
                        onClick={() => handleStatusChange(status)}
                        style={{
                            padding: "10px",
                            flex: 1,
                            textAlign: "center",
                            cursor: "pointer",
                            background: selectedCase.status === "CLOSED" ? "green" : selectedCase.status === status ? "blue" : "lightgray",
                            color: "white",
                        }}
                    >
                        {status}
                    </div>
                ))}
            </div>

            {/* Task List */}
            <TaskList tasks={selectedCase.tasks} />
        </div>
    );
};

export default CaseDetails;
