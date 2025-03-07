import React, { useEffect, useState } from "react";
import TaskList from "./TaskList";
import CallWidget from "./CallWidget";

const statusOrder = ["OPEN", "ASSIGNED", "IN_PROGRESS", "CLOSED"];

const CaseDetails = ({ selectedCase }) => {
    const [caseDetails, setCaseDetails] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);


    useEffect(() => {
        if (!selectedCase) return;

        // Fetch only if selectedCase.id is different from the last fetched case
        if (caseDetails && caseDetails.id === selectedCase.id) return;

        fetchCaseDetails();
    }, [selectedCase]);

    const fetchCaseDetails = async () => {
        setLoading(true);
        setError(null);

        try {
            const response = await fetch(`http://localhost:9090/cases/${selectedCase.id}`);
            if (!response.ok) {
                throw new Error("Failed to fetch case details");
            }
            const data = await response.json();
            setCaseDetails(data);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    if (!selectedCase) return <div>Select a case to view details</div>;
    if (loading) return <div>Loading case details...</div>;
    if (error) return <div>Error: {error}</div>;
    if (!caseDetails) return null;

    const handleStatusChange = async (newStatus) => {
        const currentIndex = statusOrder.indexOf(caseDetails.status);
        const newIndex = statusOrder.indexOf(newStatus);

        if (newIndex > currentIndex) {
            const comment = prompt(`Please enter a comment for changing status to ${newStatus}:`);
            if (!comment) {
                alert("Status change requires a comment.");
                return;
            }

            const confirmChange = window.confirm(`Change status to ${newStatus}?`);
            if (confirmChange) {
                try {
                    const response = await fetch(`http://localhost:9090/cases/${caseDetails.id}/status`, {
                        method: "PATCH",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify({ status: newStatus, comment: comment }),
                    });

                    if (!response.ok) {
                        throw new Error("Failed to update status.");
                    }

                    // Refresh case details after status change
                    fetchCaseDetails();
                } catch (error) {
                    console.error("Error updating case status:", error);
                    alert("Failed to update status. Please try again.");
                }
            }
        } else {
            alert("Cannot go back to a previous status.");
        }
    };


    return (
        <div>
            <h2>{caseDetails.title}</h2>
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
                            background: caseDetails.status === "CLOSED" ? "green" : caseDetails.status === status ? "blue" : "lightgray",
                            color: "white",
                        }}
                    >
                        {status}
                    </div>
                ))}
            </div>
            {/* <p><strong>Title:</strong> {caseDetails
            .title}</p> */}
            <p><strong>Description:</strong> {caseDetails.description}</p>
            <p><strong>Category:</strong> {caseDetails.category}</p>
            <p><strong>Created At:</strong> {new Date(caseDetails.createdAt).toLocaleString()}</p>

            {/*<CallWidget caseDetails={caseDetails} />*/}
            <CallWidget caseDetails={caseDetails} onTaskCreated={fetchCaseDetails} />

            {/* Task List */}
            <TaskList tasks={caseDetails.tasks} />
        </div>
    );
};

export default CaseDetails;
