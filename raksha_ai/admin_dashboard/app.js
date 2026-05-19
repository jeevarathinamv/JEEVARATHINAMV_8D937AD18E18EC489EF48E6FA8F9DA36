const API_URL = "http://127.0.0.1:8000";
async function fetchAlerts() {
    try {
        const res = await fetch(API_URL + "/all-alerts");
        const alerts = await res.json();
        console.log(alerts);
    } catch (e) { console.error(e); }
}
setInterval(fetchAlerts, 5000);
fetchAlerts();
