import React, { useState, useEffect, useRef } from "react";

const CallPopup = ({ caseId, onClose }) => {
    const [seconds, setSeconds] = useState(0);
    const [transcript, setTranscript] = useState("");
    const mediaRecorderRef = useRef(null);
    const recognitionRef = useRef(null);

    useEffect(() => {
        // Start the timer
        const timer = setInterval(() => {
            setSeconds((prev) => prev + 1);
        }, 1000);

        // Start recording and speech recognition
        startRecording();

        return () => {
            clearInterval(timer);
            stopRecording();
        };
    }, []);

    // Function to start recording & transcription
    const startRecording = async () => {
        try {
            // Get user microphone access
            const stream = await navigator.mediaDevices.getUserMedia({ audio: true });

            // Initialize MediaRecorder for recording
            const mediaRecorder = new MediaRecorder(stream);
            mediaRecorderRef.current = mediaRecorder;
            mediaRecorder.start();

            // Initialize SpeechRecognition API
            const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
            if (!SpeechRecognition) {
                console.warn("SpeechRecognition is not supported in this browser.");
                return;
            }

            const recognition = new SpeechRecognition();
            recognition.lang = "en-US";
            recognition.continuous = true;
            recognition.interimResults = true;

            recognition.onresult = (event) => {
                let finalTranscript = "";
                for (let i = 0; i < event.results.length; i++) {
                    finalTranscript += event.results[i][0].transcript + " ";
                }
                setTranscript(finalTranscript);
            };

            recognition.start();
            recognitionRef.current = recognition;
        } catch (error) {
            console.error("Error accessing microphone:", error);
        }
    };

    // Function to stop recording & transcription
    const stopRecording = () => {
        if (mediaRecorderRef.current) {
            mediaRecorderRef.current.stop();
        }
        if (recognitionRef.current) {
            recognitionRef.current.stop();
        }
    };

    // Function to send API request when call ends
    const handleEndCall = async () => {
        stopRecording();

        const taskData = {
            taskType: "CALL",
            assigneeID: assigneeID || "ABC1", // Default value if not provided
            description: "Outbound call to customer",
            duration: `${seconds}`,
            transcript: `${transcript}`,
            status: "CLOSED",
        };

        try {
            await fetch(`http://localhost:9090/cases/${caseId}/tasks`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(taskData),
            });
            console.log("Task created:", taskData);
        } catch (error) {
            console.error("Failed to create task", error);
        }

        onClose();
    };

    return (
        <div style={{ position: "fixed", top: "30%", left: "50%", transform: "translate(-50%, -50%)", background: "white", padding: "20px", border: "1px solid #ccc", borderRadius: "8px", zIndex: 1000 }}>
            <h3>Call connected to Customer</h3>
            <p>⏳ {Math.floor(seconds / 60)}:{(seconds % 60).toString().padStart(2, "0")}</p>

            <h4>Live Transcript:</h4>
            <div style={{ height: "100px", overflowY: "scroll", background: "#f9f9f9", padding: "10px", border: "1px solid #ddd" }}>
                {transcript || "Listening..."}
            </div>

            <button onClick={handleEndCall} style={{ padding: "10px", background: "red", color: "white", border: "none", borderRadius: "5px", cursor: "pointer", marginTop: "10px" }}>
                End Call
            </button>
        </div>
    );
};

export default CallPopup;
