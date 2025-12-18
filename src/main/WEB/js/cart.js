function addToCart(productId) {
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    
    // Check if item already exists
    const existingItem = cart.find(item => item.productId === productId);
    
    if (existingItem) {
        existingItem.quantity += 1;
    } else {
        cart.push({ productId: productId, quantity: 1 });
    }
    
    localStorage.setItem("cart", JSON.stringify(cart));
    alert("Item added to cart!");
}

async function checkout() {
    const cart = JSON.parse(localStorage.getItem("cart")) || [];
    if (cart.length === 0) {
        alert("Cart is empty!");
        return;
    }

    try {
        // Send cart to backend Order API
        await request("/orders", "POST", { items: cart });
        
        alert("Order Placed Successfully!");
        localStorage.removeItem("cart"); // Clear cart
        window.location.href = "products.html";
        
    } catch (error) {
        alert("Order failed. Are you logged in?");
    }
}