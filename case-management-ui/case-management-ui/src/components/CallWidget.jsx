import React, { useState } from "react";
import CallPopup from "./CallPopup";

const CallWidget = ({ caseDetails, onTaskCreated}) => {
    const [showPopup, setShowPopup] = useState(false);

    const handleCallClick = () => {
        setShowPopup(true);
    };

    return (
        <div style={{ padding: "10px", border: "1px solid #ddd", borderRadius: "8px" }}>
            <h3>Customer Info</h3>
            <p><strong><a href={`https://astra.allen.in/student-support/resource-management/student/view/${caseDetails.studentId}`} target="_blank" rel="noopener noreferrer">
                Student Astra Page
            </a></strong></p>
            <p><strong>Phone:</strong> {caseDetails.phone}</p>
            <button onClick={handleCallClick} style={{ padding: "8px", background: "green", color: "white", border: "none", borderRadius: "8px", cursor: "pointer" }}>
                📞 Call
            </button>

            {showPopup && <CallPopup caseId={caseDetails.id} onClose={() => setShowPopup(false)} onTaskCreated={onTaskCreated} />}
        </div>
    );
};

export default CallWidget;
