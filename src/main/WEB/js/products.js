async function loadProducts() {
    try {
        // GET request to /products
        const products = await request("/products", "GET");
        
        const container = document.getElementById("product-list");
        container.innerHTML = ""; // Clear loading text

        products.forEach(product => {
            const card = `
                <div class="product-card">
                    <img src="${product.imageUrl}" alt="${product.name}" width="100">
                    <h3>${product.name}</h3>
                    <p>${product.description}</p>
                    <p>Price: $${product.price}</p>
                    <button onclick="addToCart(${product.id})">Add to Cart</button>
                </div>
            `;
            container.innerHTML += card;
        });

    } catch (error) {
        document.getElementById("product-list").innerHTML = "<p>Failed to load products.</p>";
    }
}

// Load automatically when page opens
document.addEventListener("DOMContentLoaded", loadProducts);