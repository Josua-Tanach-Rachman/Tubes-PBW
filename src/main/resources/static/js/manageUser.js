// Get DOM elements
const searchInput = document.querySelector('.search_bar');
const roleFilter = document.getElementById('filter-role');
const statusFilter = document.getElementById('filter-status');
const applyFilterBtn = document.getElementById('apply-filter');
const userList = document.getElementById('user-list');

// Update the role filter options to match database values
roleFilter.innerHTML = `
    <option value="all">All Roles</option>
    <option value="pengguna">User</option>
    <option value="admin">Admin</option>
`;

// Function to filter users based on search, role, and status
function filterUsers() {
    const searchTerm = searchInput.value.toLowerCase();
    const selectedRole = roleFilter.value;
    const selectedStatus = statusFilter.value;
    
    // Get all user rows
    const userRows = userList.getElementsByTagName('tr');
    
    Array.from(userRows).forEach(row => {
        const username = row.cells[1].textContent.toLowerCase();
        const email = row.cells[2].textContent.toLowerCase();
        const roleSelect = row.querySelector('.role-dropdown');
        const role = roleSelect.value.toLowerCase();
        const status = row.querySelector('.status-btn').textContent.toLowerCase();
        
        // Check if row matches all filter criteria
        const matchesSearch = username.includes(searchTerm) || email.includes(searchTerm);
        const matchesRole = selectedRole === 'all' || role === selectedRole;
        const matchesStatus = selectedStatus === 'all' || status === selectedStatus;
        
        // Show/hide row based on filter matches
        row.style.display = matchesSearch && matchesRole && matchesStatus ? '' : 'none';
    });
}

// Event listeners for real-time filtering
searchInput.addEventListener('input', filterUsers);
roleFilter.addEventListener('change', filterUsers);
statusFilter.addEventListener('change', filterUsers);
applyFilterBtn.addEventListener('click', filterUsers);

// Function to handle role changes
function handleRoleChange(event) {
    const select = event.target;
    const userId = select.getAttribute('data-user-id');
    const newRole = select.value;
    
    // Here you would typically make an API call to update the user's role
    console.log(`Updating user ${userId} role to ${newRole}`);
    // Example API call:
    // fetch('/api/users/update-role', {
    //     method: 'POST',
    //     headers: {
    //         'Content-Type': 'application/json',
    //     },
    //     body: JSON.stringify({ userId, role: newRole })
    // });
}

// Function to handle status changes
function handleStatusToggle(event) {
    const button = event.target;
    const currentStatus = button.textContent.toLowerCase();
    const newStatus = currentStatus === 'active' ? 'Inactive' : 'Active';
    
    // Update button text and class
    button.textContent = newStatus;
    button.classList.remove('active', 'inactive');
    button.classList.add(newStatus.toLowerCase());
    
    // Here you would typically make an API call to update the user's status
    console.log(`Updating user status to ${newStatus}`);
    // Example API call:
    // fetch('/api/users/update-status', {
    //     method: 'POST',
    //     headers: {
    //         'Content-Type': 'application/json',
    //     },
    //     body: JSON.stringify({ userId, status: newStatus === 'Active' })
    // });
}

// Add event listeners for role and status changes
document.querySelectorAll('.role-dropdown').forEach(select => {
    select.addEventListener('change', handleRoleChange);
});

document.querySelectorAll('.status-btn').forEach(button => {
    button.addEventListener('click', handleStatusToggle);
});

// Initial filter on page load
filterUsers();

// Previous filter-related code remains the same...

// Function to handle role changes
async function handleRoleChange(event) {
    const select = event.target;
    const userId = select.getAttribute('data-user-id');
    const newRole = select.value;
    
    try {
        const response = await fetch('/update-role', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ userId, role: newRole })
        });

        if (!response.ok) {
            throw new Error('Failed to update role');
        }

        // Show success message
        alert('Role updated successfully');
    } catch (error) {
        console.error('Error updating role:', error);
        alert('Failed to update role. Please try again.');
        // Revert the select to its previous value
        location.reload();
    }
}

// Function to handle status changes
async function handleStatusToggle(event) {
    const button = event.target;
    const row = button.closest('tr');
    const userId = row.querySelector('.role-dropdown').getAttribute('data-user-id');
    const currentStatus = button.textContent.toLowerCase();
    const newStatus = currentStatus === 'active' ? false : true;
    
    try {
        const response = await fetch('/update-status', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ userId, status: newStatus })
        });

        if (!response.ok) {
            throw new Error('Failed to update status');
        }

        // Update button text and class
        const newStatusText = newStatus ? 'Active' : 'Inactive';
        button.textContent = newStatusText;
        button.classList.remove('active', 'inactive');
        button.classList.add(newStatusText.toLowerCase());

        // Show success message
        alert('Status updated successfully');
    } catch (error) {
        console.error('Error updating status:', error);
        alert('Failed to update status. Please try again.');
        // Reload the page to show correct state
        location.reload();
    }
}

// Add event listeners
document.querySelectorAll('.role-dropdown').forEach(select => {
    select.addEventListener('change', handleRoleChange);
});

document.querySelectorAll('.status-btn').forEach(button => {
    button.addEventListener('click', handleStatusToggle);
});