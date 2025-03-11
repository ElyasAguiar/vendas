// This file contains the JavaScript code that handles the functionality of the application.

const BASE_URL = "http://localhost:8080/api"; // Adjust as necessary
const ITEMS_PER_PAGE = 5;

// Function to handle pagination
function paginate(items, containerId, paginationId, renderItem) {
    const container = document.getElementById(containerId);
    const pagination = document.getElementById(paginationId);
    let currentPage = 1;

    function renderPage(page) {
        container.innerHTML = '';
        const start = (page - 1) * ITEMS_PER_PAGE;
        const end = start + ITEMS_PER_PAGE;
        const paginatedItems = items.slice(start, end);

        paginatedItems.forEach(renderItem);

        pagination.innerHTML = '';
        for (let i = 1; i <= Math.ceil(items.length / ITEMS_PER_PAGE); i++) {
            const button = document.createElement('button');
            button.textContent = i;
            button.className = i === page ? 'active' : '';
            button.onclick = () => renderPage(i);
            pagination.appendChild(button);
        }
    }

    renderPage(currentPage);
}

let currentItem = null;
let currentEndpoint = '';

function openModal(item, endpoint) {
    currentItem = item;
    currentEndpoint = endpoint;
    const modal = document.getElementById('edit-modal');
    const form = document.getElementById('edit-form');
    form.innerHTML = '';

    // Add hidden input for ID
    const idField = endpoint === 'pedidos' ? 'idPedido' : 'id';
    const idInput = document.createElement('input');
    idInput.type = 'hidden';
    idInput.name = idField;
    idInput.value = item[idField];
    form.appendChild(idInput);

    for (const key in item) {
        if (key !== idField) { // Skip the ID field
            const label = document.createElement('label');
            label.textContent = key.charAt(0).toUpperCase() + key.slice(1);

            let value = item[key];
            console.log(key, value);
            if (key === 'ativo') {
                const select = document.createElement('select');
                select.name = key;
                const optionTrue = document.createElement('option');
                optionTrue.value = 'true';
                optionTrue.textContent = 'Sim';
                const optionFalse = document.createElement('option');
                optionFalse.value = 'false';
                optionFalse.textContent = 'Não';
                select.appendChild(optionTrue);
                select.appendChild(optionFalse);
                select.value = value ? 'true' : 'false';
                form.appendChild(label);
                form.appendChild(select);
            } else if (key === 'dataNascimento' || key === 'dataHora') {
                const input = document.createElement('input');
                input.type = 'date';
                if (key === 'dataHora') {
                    input.type = 'datetime-local';
                }
                input.name = key;
                let dateComponents = [...value];
                dateComponents[1] = dateComponents[1] - 1;
                date = new Date(Date.UTC(...dateComponents));
                console.log(date);
                value = date.toISOString().split('T')[0];
                console.log(value);
                if (key === 'dataHora') {
                    value = date.toISOString().split('.')[0];
                }
                input.value = value;
                form.appendChild(label);
                form.appendChild(input);
            } else if (typeof value === 'number') {
                value = value.toFixed(2); // Format numbers to 2 decimal places
                const input = document.createElement('input');
                input.type = 'text';
                input.name = key;
                input.value = value;
                form.appendChild(label);
                form.appendChild(input);
            } else {
                const input = document.createElement('input');
                input.type = 'text';
                input.name = key;
                input.value = value;
                form.appendChild(label);
                form.appendChild(input);
            }
        }
    }

    const buttonContainer = document.createElement('div');
    buttonContainer.className = 'form-buttons';

    const saveButton = document.createElement('button');
    saveButton.type = 'submit';
    saveButton.textContent = 'Salvar';
    buttonContainer.appendChild(saveButton);

    const deleteButton = document.createElement('button');
    deleteButton.type = 'button';
    deleteButton.textContent = 'Excluir';
    deleteButton.onclick = deleteItem;
    buttonContainer.appendChild(deleteButton);

    form.appendChild(buttonContainer);

    modal.style.display = 'block';
}

function closeModal() {
    const modal = document.getElementById('edit-modal');
    modal.style.display = 'none';
}

async function saveChanges(event) {
    event.preventDefault();
    const form = document.getElementById('edit-form');
    const formData = new FormData(form);
    const data = {};
    formData.forEach((value, key) => {
        data[key] = value;
    });

    const idField = currentEndpoint === 'pedidos' ? 'idPedido' : 'id';

    try {
        const response = await fetch(`${BASE_URL}/${currentEndpoint}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            alert('Alterações salvas com sucesso!');
            closeModal();
            location.reload();
        } else {
            alert('Erro ao salvar alterações.');
        }
    } catch (error) {
        console.error('Error saving changes:', error);
    }
}

async function deleteItem() {
    const idField = currentEndpoint === 'pedidos' ? 'idPedido' : 'id';

    if (confirm('Tem certeza que deseja excluir este item?')) {
        try {
            const response = await fetch(`${BASE_URL}/${currentEndpoint}/${currentItem[idField]}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                alert('Item excluído com sucesso!');
                closeModal();
                location.reload();
            } else {
                alert('Erro ao excluir item.');
            }
        } catch (error) {
            console.error('Error deleting item:', error);
        }
    }
}

// Function to fetch and display products
async function fetchProducts() {
    try {
        const response = await fetch(`${BASE_URL}/produto`);
        const produtos = await response.json();
        paginate(produtos, 'product-list', 'product-pagination', (produto) => {
            const productItem = document.createElement('div');
            productItem.className = 'product-item';
            productItem.innerHTML = `
                <h3>${produto.nome}</h3>
                <p>${produto.descricao}</p>
                <p>Preço: R$ ${produto.preco.toFixed(2)}</p>
            `;
            productItem.onclick = () => openModal(produto, 'produto');
            document.getElementById('product-list').appendChild(productItem);
        });
    } catch (error) {
        console.error('Error fetching products:', error);
    }
}

// Function to fetch and display clients
async function fetchClients() {
    try {
        const response = await fetch(`${BASE_URL}/cliente`);
        const clientes = await response.json();
        paginate(clientes, 'client-list', 'client-pagination', (cliente) => {
            const clientItem = document.createElement('div');
            clientItem.className = 'client-item';
            clientItem.innerHTML = `
                <h3>${cliente.nome}</h3>
                <p>CPF: ${cliente.cpf}</p>
                <p>Email: ${cliente.email}</p>
            `;
            clientItem.onclick = () => openModal(cliente, 'cliente');
            document.getElementById('client-list').appendChild(clientItem);
        });
    } catch (error) {
        console.error('Error fetching clients:', error);
    }
}

// Function to fetch and display payment methods
async function fetchPaymentMethods() {
    try {
        const response = await fetch(`${BASE_URL}/formaPagamento`);
        const formasPagamento = await response.json();
        paginate(formasPagamento, 'payment-method-list', 'payment-pagination', (forma) => {
            const paymentItem = document.createElement('div');
            paymentItem.className = 'payment-item';
            paymentItem.innerHTML = `
                <h3>${forma.nome}</h3>
                <p>${forma.descricao}</p>
            `;
            paymentItem.onclick = () => openModal(forma, 'formaPagamento');
            document.getElementById('payment-method-list').appendChild(paymentItem);
        });
    } catch (error) {
        console.error('Error fetching payment methods:', error);
    }
}

// Function to fetch and display orders
async function fetchOrders() {
    try {
        const response = await fetch(`${BASE_URL}/pedidos`);
        const pedidos = await response.json();
        paginate(pedidos, 'order-list', 'order-pagination', (pedido) => {
            const orderItem = document.createElement('div');
            orderItem.className = 'order-item';
            orderItem.innerHTML = `
                <h3>Pedido Número: ${pedido.numeroPedido}</h3>
                <p>Valor Total: R$ ${pedido.valorTotal.toFixed(2)}</p>
                <p>Observação: ${pedido.observacao}</p>
            `;
            orderItem.onclick = () => openModal(pedido, 'pedidos');
            document.getElementById('order-list').appendChild(orderItem);
        });
    } catch (error) {
        console.error('Error fetching orders:', error);
    }
}

async function fetchRevenue() {
    let startDate = document.getElementById('start-date').value;
    let endDate = document.getElementById('end-date').value;

    if (!startDate || !endDate) {
        alert('Por favor, selecione as datas inicial e final.');
        return;
    }

    try {
        const response = await fetch(`${BASE_URL}/pedidos/faturamento?dataInicial=${startDate}&dataFinal=${endDate}`);
        const revenue = await response.json();
        const revenueResult = document.getElementById('revenue-result');
        startDate = new Date(startDate).toLocaleDateString('pt-BR');
        endDate = new Date(endDate).toLocaleDateString('pt-BR');
        revenueResult.innerHTML = `
            <h3>Faturamento de ${startDate} a ${endDate}</h3>
            <p>Valor Total: R$ ${revenue.toFixed(2)}</p>
        `;
    } catch (error) {
        console.error('Error fetching revenue:', error);
        alert('Erro ao buscar faturamento.');
    }
}

function generateOrderNumber() {
    return Math.floor(Math.random() * 100000).toString().padStart(5, '0');
}

let selectedProducts = [];

function openNewOrderModal() {
    selectedProducts = [];
    const modal = document.getElementById('new-order-modal');
    const productSelection = document.getElementById('product-selection');
    const selectedProductsContainer = document.getElementById('selected-products');
    const paymentMethodSelect = document.getElementById('payment-method');
    productSelection.innerHTML = '';
    selectedProductsContainer.innerHTML = '<h3>Itens Selecionados</h3>';
    paymentMethodSelect.innerHTML = '';

    fetchProductsForSelection();
    fetchPaymentMethodsForSelection();

    modal.style.display = 'block';
}

function closeNewOrderModal() {
    const modal = document.getElementById('new-order-modal');
    modal.style.display = 'none';
}

async function fetchProductsForSelection() {
    try {
        const response = await fetch(`${BASE_URL}/produto`);
        const produtos = await response.json();
        const productSelection = document.getElementById('product-selection');

        produtos.forEach(produto => {
            const productItem = document.createElement('div');
            productItem.className = 'product-item';
            productItem.innerHTML = `
                <h3>${produto.nome}</h3>
                <p>${produto.descricao}</p>
                <p>Preço: R$ ${produto.preco.toFixed(2)}</p>
            `;
            productItem.onclick = () => toggleProductSelection(produto);
            productSelection.appendChild(productItem);
        });
    } catch (error) {
        console.error('Error fetching products for selection:', error);
    }
}

async function fetchPaymentMethodsForSelection() {
    try {
        const response = await fetch(`${BASE_URL}/formaPagamento`);
        const formasPagamento = await response.json();
        const paymentMethodSelect = document.getElementById('payment-method');

        formasPagamento.forEach(forma => {
            const option = document.createElement('option');
            option.value = forma.id;
            option.textContent = forma.nome;
            paymentMethodSelect.appendChild(option);
        });
    } catch (error) {
        console.error('Error fetching payment methods for selection:', error);
    }
}

function toggleProductSelection(produto) {
    const index = selectedProducts.findIndex(p => p.id === produto.id);
    if (index === -1) {
        selectedProducts.push({ ...produto, quantidade: 1 });
    } else {
        selectedProducts.splice(index, 1);
    }
    updateSelectedProducts();
}

function updateSelectedProducts() {
    const selectedProductsContainer = document.getElementById('selected-products');
    selectedProductsContainer.innerHTML = '<h3>Itens Selecionados</h3>';
    selectedProducts.forEach(produto => {
        const selectedItem = document.createElement('div');
        selectedItem.className = 'selected-item';
        selectedItem.innerHTML = `
            <h4>${produto.nome}</h4>
            <p>Preço: R$ ${produto.preco.toFixed(2)}</p>
            <label for="quantity-${produto.id}">Quantidade:</label>
            <input type="number" id="quantity-${produto.id}" value="${produto.quantidade}" min="1" onchange="updateProductQuantity(${produto.id}, this.value)">
            <button onclick="removeProductFromSelection(${produto.id})">Remover</button>
        `;
        selectedProductsContainer.appendChild(selectedItem);
    });
}

function updateProductQuantity(produtoId, quantidade) {
    const produto = selectedProducts.find(p => p.id === produtoId);
    if (produto) {
        produto.quantidade = parseInt(quantidade, 10);
    }
}

function removeProductFromSelection(produtoId) {
    const index = selectedProducts.findIndex(p => p.id === produtoId);
    if (index !== -1) {
        selectedProducts.splice(index, 1);
    }
    updateSelectedProducts();
}

async function saveNewOrder() {
    const observation = document.getElementById('order-observation').value;
    const paymentMethod = document.getElementById('payment-method').value;

    const orderData = {
        numeroPedido: generateOrderNumber(),
        valorTotal: selectedProducts.reduce((total, produto) => total + (produto.preco * produto.quantidade), 0),
        ativo: true,
        observacao: observation,
        idFormaPagamento: parseInt(paymentMethod, 10),
        idCliente: 1 // This should be selected by the user
    };

    try {
        // Create the order
        const orderResponse = await fetch(`${BASE_URL}/pedidos`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(orderData)
        });

        if (!orderResponse.ok) {
            alert('Erro ao salvar pedido.');
            return;
        }

        const order = await orderResponse.json();

        // Add items to the order
        const itemsData = selectedProducts.map(produto => ({
            quantidade: produto.quantidade,
            idPedido: order.idPedido,
            idProduto: produto.id
        }));

        for (const itemData of itemsData) {
            const itemResponse = await fetch(`${BASE_URL}/itensPedido`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(itemData)
            });

            if (!itemResponse.ok) {
                alert('Erro ao salvar item do pedido.');
                return;
            }
        }

        alert('Pedido salvo com sucesso!');
        closeNewOrderModal();
        fetchOrders();
    } catch (error) {
        console.error('Error saving new order:', error);
    }
}

function openNewProductModal() {
    const modal = document.getElementById('new-product-modal');
    modal.style.display = 'block';
}

function closeNewProductModal() {
    const modal = document.getElementById('new-product-modal');
    modal.style.display = 'none';
}

async function saveNewProduct(event) {
    event.preventDefault();
    const form = document.getElementById('new-product-form');
    const formData = new FormData(form);
    const data = {ativo: true};
    formData.forEach((value, key) => {
        data[key] = value;
    });

    try {
        const response = await fetch(`${BASE_URL}/produto`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            alert('Produto salvo com sucesso!');
            closeNewProductModal();
            fetchProducts();
        } else {
            alert('Erro ao salvar produto.');
        }
    } catch (error) {
        console.error('Error saving product:', error);
    }
}

function openNewClientModal() {
    const modal = document.getElementById('new-client-modal');
    modal.style.display = 'block';
}

function closeNewClientModal() {
    const modal = document.getElementById('new-client-modal');
    modal.style.display = 'none';
}

async function saveNewClient(event) {
    event.preventDefault();
    const form = document.getElementById('new-client-form');
    const formData = new FormData(form);
    const data = {};
    formData.forEach((value, key) => {
        data[key] = value;
    });

    try {
        const response = await fetch(`${BASE_URL}/cliente`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            alert('Cliente salvo com sucesso!');
            closeNewClientModal();
            fetchClients();
        } else {
            alert('Erro ao salvar cliente.');
        }
    } catch (error) {
        console.error('Error saving client:', error);
    }
}

function openNewPaymentMethodModal() {
    const modal = document.getElementById('new-payment-method-modal');
    modal.style.display = 'block';
}

function closeNewPaymentMethodModal() {
    const modal = document.getElementById('new-payment-method-modal');
    modal.style.display = 'none';
}

async function saveNewPaymentMethod(event) {
    event.preventDefault();
    const form = document.getElementById('new-payment-method-form');
    const formData = new FormData(form);
    const data = {};
    formData.forEach((value, key) => {
        data[key] = value;
    });

    try {
        const response = await fetch(`${BASE_URL}/formaPagamento`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            alert('Forma de pagamento salva com sucesso!');
            closeNewPaymentMethodModal();
            fetchPaymentMethods();
        } else {
            alert('Erro ao salvar forma de pagamento.');
        }
    } catch (error) {
        console.error('Error saving payment method:', error);
    }
}

// Function to handle tab switching
function openTab(evt, tabName) {
    var i, tabcontent, tabbuttons;

    // Hide all tab contents
    tabcontent = document.getElementsByClassName("tab-content");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Remove the active class from all tab buttons
    tabbuttons = document.getElementsByClassName("tab-button");
    for (i = 0; i < tabbuttons.length; i++) {
        tabbuttons[i].className = tabbuttons[i].className.replace(" active", "");
    }

    // Show the current tab content and add an active class to the button that opened the tab
    document.getElementById(tabName).style.display = "block";
    evt.currentTarget.className += " active";
}

// Initialize the application
document.addEventListener('DOMContentLoaded', () => {
    fetchProducts();
    fetchClients();
    fetchPaymentMethods();
    fetchOrders();
    document.querySelector(".tab-button").click();
});