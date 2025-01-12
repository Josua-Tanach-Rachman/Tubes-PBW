// Memproses data untuk chart
const processData = () => {
    const groupedData = {};

    setlistData.forEach(setlist => {
        if (setlist && setlist.tanggal) {
            const date = new Date(setlist.tanggal).toISOString().split('T')[0]; // Gunakan format ISO (YYYY-MM-DD)
            groupedData[date] = Math.round((groupedData[date] || 0) + 1); // Memastikan nilai bulat
        }
    });

    // Urutkan berdasarkan tanggal yang sebenarnya
    const labels = Object.keys(groupedData).sort((a, b) => new Date(a) - new Date(b));
    const data = labels.map(date => groupedData[date]);

    return { labels, data };
};


// Konfigurasi chart
const getChartConfig = (labels, data) => {
    return {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Number of Setlists',
                data: data,
                borderColor: '#8100B3',
                tension: 0.1,
                fill: false,
                borderWidth: 2,
                pointRadius: 4,
                pointHoverRadius: 6,
                pointBackgroundColor: '#8100B3',
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Setlist Activity Over Time',
                    font: { size: 16 }
                },
                legend: {
                    position: 'bottom'
                },
                tooltip: {
                    mode: 'index',
                    intersect: false,
                    backgroundColor: 'rgba(255, 255, 255, 0.9)',
                    titleColor: '#000',
                    bodyColor: '#000',
                    borderColor: 'rgb(75, 192, 192)',
                    borderWidth: 1,
                    padding: 10,
                    callbacks: {
                        label: function (context) {
                            return `Setlists: ${Math.round(context.parsed.y)}`; // Memastikan nilai bulat di tooltip
                        }
                    }
                }
            },
            scales: {
                x: {
                    grid: { display: false },
                    title: {
                        display: true,
                        text: 'Date'
                    }
                },
                y: {
                    min: 1, // Minimum value
                    max: 10, // Maximum value
                    ticks: {
                        stepSize: 1, // Step size between ticks
                        callback: function (value) {
                            return Math.round(value); // Memastikan nilai bulat pada axis
                        }
                    },
                    grid: { borderDash: [2, 4] },
                    title: {
                        display: true,
                        text: 'Number of Setlists'
                    }
                }
            }
        }
    };
};

// Inisialisasi chart
const initChart = () => {
    try {
        const { labels, data } = processData();
        const ctx = document.getElementById('setlistChart').getContext('2d');
        const chartConfig = getChartConfig(labels, data);

        new Chart(ctx, chartConfig);
    } catch (error) {
        console.error('Error initializing chart:', error);
        const container = document.querySelector('.chart-container');
        container.innerHTML = '<div class="error-message">Error loading chart: ' + error.message + '</div>';
    }
};

// Initialize when DOM is loaded
document.addEventListener('DOMContentLoaded', initChart);
