const BASE_URL = "http://localhost:8080/api";

async function request(endpoint, method = "GET", body = null) {
    const token = localStorage.getItem("jwt_token");

    const headers = {
        "Content-Type": "application/json"
    };

    if (token) {
        headers["Authorization"] = `Bearer ${token}`;
    }

    const config = {
        method: method,
        headers: headers
    };

    if (body) {
        config.body = JSON.stringify(body);
    }

    try {
        const response = await fetch(`${BASE_URL}${endpoint}`, config);
        
        // Handle 403 Forbidden (Token expired)
        if (response.status === 403) {
            alert("Session expired. Please login again.");
            localStorage.removeItem("jwt_token");
            window.location.href = "login.html";
            return;
        }

        if (!response.ok) {
            throw new Error(`Error: ${response.statusText}`);
        }

        // Some endpoints (like Delete) might not return JSON
        const text = await response.text();
        return text ? JSON.parse(text) : {};
        
    } catch (error) {
        console.error("API Request Failed:", error);
        throw error;
    }
}