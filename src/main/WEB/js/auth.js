// Function to handle Login Form Submit
async function handleLogin(event) {
    event.preventDefault(); // Stop page reload

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    try {
        // Use our central request function
        const data = await request("/auth/authenticate", "POST", { email, password });
        
        // 1. Save Token to Local Storage
        localStorage.setItem("jwt_token", data.token);
        
        // 2. Redirect to Products Page
        window.location.href = "products.html";
        
    } catch (error) {
        alert("Login Failed: Invalid Credentials");
    }
}

// Attach listener if on login page
const loginForm = document.getElementById("login-form");
if (loginForm) {
    loginForm.addEventListener("submit", handleLogin);
}