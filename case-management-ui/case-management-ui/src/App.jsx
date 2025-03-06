import React, { useEffect, useState } from "react";
import CaseDetails from "./components/CaseDetails";

const API_URL = "http://localhost:9090/cases"; // Adjust as needed

const App = () => {
    const [cases, setCases] = useState([]);
    const [selectedCaseId, setSelectedCaseId] = useState(null);
    const [leftPaneWidth, setLeftPaneWidth] = useState(40); // Default left pane width

    const fetchCases = async () => {
        try {
            const response = await fetch(API_URL);
            if (!response.ok) throw new Error("Failed to fetch cases");
            const data = await response.json();
            setCases(data);
        } catch (error) {
            console.error("Error fetching cases:", error);
        }
    };

    useEffect(() => {
        fetchCases();
        const interval = setInterval(fetchCases, 5000);
        return () => clearInterval(interval);
    }, []);

    // Find the selected case dynamically
    const selectedCase = cases.find((c) => c.id === selectedCaseId);

    return (
        <div style={{ display: "flex", flexDirection: "column", height: "100vh", width: "100vw" }}>

            {/* Common Header */}
            <div style={{ height: "50px", background: "#333", color: "white", display: "flex", alignItems: "center", paddingLeft: "10px" }}>
                <h2>Case Management</h2>
            </div>

            <div style={{ display: "flex", flexGrow: 1 }}>
                {/* Left Pane - Case List */}
                <div
                    style={{
                        width: `${leftPaneWidth}%`,
                        overflowY: "auto",
                        borderRight: "1px solid #ccc",
                        padding: "10px",
                    }}
                >
                    <h3>Cases</h3>
                    <ul style={{ listStyle: "none", padding: 0 }}>
                        {cases.map((c) => (
                            <li
                                key={c.id}
                                onClick={() => setSelectedCaseId(c.id)}
                                style={{
                                    padding: "10px",
                                    borderBottom: "1px solid #ddd",
                                    cursor: "pointer",
                                    background: selectedCaseId === c.id ? "#f0f0f0" : "white",
                                }}
                            >
                                <strong>{c.title}</strong> <br />
                                <span>Status: {c.status}</span>
                            </li>
                        ))}
                    </ul>
                </div>

                {/* Resizable Divider */}
                <div
                    style={{
                        width: "5px",
                        cursor: "ew-resize",
                        background: "#ccc",
                        minWidth: "5px",
                    }}
                    onMouseDown={(e) => {
                        const startX = e.clientX;
                        const startWidth = leftPaneWidth;

                        const onMouseMove = (moveEvent) => {
                            const newWidth = Math.max(
                                20,
                                Math.min(60, startWidth + ((moveEvent.clientX - startX) / window.innerWidth) * 100)
                            );
                            setLeftPaneWidth(newWidth);
                        };

                        const onMouseUp = () => {
                            document.removeEventListener("mousemove", onMouseMove);
                            document.removeEventListener("mouseup", onMouseUp);
                        };

                        document.addEventListener("mousemove", onMouseMove);
                        document.addEventListener("mouseup", onMouseUp);
                    }}
                ></div>

                {/* Right Pane - Case Details */}
                <div style={{ flexGrow: 1, padding: "10px", overflowY: "auto" }}>
                    {selectedCase ? <CaseDetails selectedCase={selectedCase} /> : <p>Select a case to view details</p>}
                </div>
            </div>
        </div>
    );
};

export default App;
