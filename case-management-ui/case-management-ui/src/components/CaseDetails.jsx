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

        fetchCaseDetails();
    }, [selectedCase]);

    if (!selectedCase) return <div>Select a case to view details</div>;

    const handleStatusChange = (newStatus) => {
        const currentIndex = statusOrder.indexOf(caseDetails.status);
        const newIndex = statusOrder.indexOf(newStatus);

        if (newIndex > currentIndex) {
            const confirmChange = window.confirm(`Change status to ${newStatus}?`);
            if (confirmChange) {
                console.log(`Updating case ${caseDetails.id} to status: ${newStatus}`);
                // API call to update status can be placed here
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

            <CallWidget caseDetails={caseDetails} />

            {/* Task List */}
            <TaskList tasks={caseDetails.tasks} />
        </div>
    );
};

export default CaseDetails;
