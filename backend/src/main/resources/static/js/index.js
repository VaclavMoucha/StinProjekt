const langButton = document.querySelector(".langButton");
let lang = localStorage.getItem("lang") || "cs";
function updateLanguage() {
  document.querySelectorAll("[data-en]").forEach((el) => {
    el.textContent = el.getAttribute(`data-${lang}`);
  });
}
langButton.addEventListener("click", () => {
  console.log("click");
  if (lang === "cs") {
    lang = "en";
  } else {
    lang = "cs";
  }

  localStorage.setItem("lang", lang);
  updateLanguage();
});

const headers = {
  Accept: "application/json",
};

function showError(error) {
  console.error("Chyba:", error);
  document.getElementById("api-error").style.display = "block";
}

function loadSelectedCurrency() {
  fetch("/api/settings", { headers })
    .then((response) => {
      if (!response.ok) throw new Error("API error");
      return response.json();
    })
    .then((data) => {
      document.getElementById("selected-currency").textContent =
        data.preferredCurrency;
    })
    .catch((error) => showError(error));
}
function loadRates() {
  fetch("/api/rates", { headers })
    .then((response) => {
      if (!response.ok) throw new Error("API error");
      return response.json();
    })
    .then((data) => {
      const tbody = document.getElementById("rates-table");
      tbody.innerHTML = "";
      for (const [currency, rate] of Object.entries(data.rates)) {
        tbody.innerHTML += `
                    <tr>
                        <td>${currency}</td>
                        <td>${rate.toFixed(4)}</td>
                    </tr>`;
      }
    })
    .catch((error) => showError(error));
}

function loadStrongest() {
  fetch("/api/rates/strongest", { headers })
    .then((response) => {
      if (!response.ok) throw new Error("API error");
      return response.json();
    })
    .then((data) => {
      document.getElementById("strongest").textContent =
        `${data.currency} (${data.rate.toFixed(4)})`;
    })
    .catch((error) => showError(error));
}

function loadWeakest() {
  fetch("/api/rates/weakest", { headers })
    .then((response) => {
      if (!response.ok) throw new Error("API error");
      return response.json();
    })
    .then((data) => {
      document.getElementById("weakest").textContent =
        `${data.currency} (${data.rate.toFixed(4)})`;
    })
    .catch((error) => showError(error));
}

function loadAverage() {
  const from = document.getElementById("from-date").value;
  const to = document.getElementById("to-date").value;

  if (!from || !to) {
    alert("Vyplňte obě data!");
    return;
  }

  fetch(`/api/rates/average?from=${from}&to=${to}`, { headers })
    .then((response) => {
      if (!response.ok) throw new Error("API error");
      return response.json();
    })
    .then((data) => {
      const tbody = document.getElementById("average-table");
      tbody.innerHTML = "";
      data.forEach((rate) => {
        tbody.innerHTML += `
                    <tr>
                        <td>${rate.currency}</td>
                        <td>${rate.rate.toFixed(4)}</td>
                    </tr>`;
      });
    })
    .catch((error) => showError(error));
}
function loadChart() {
  const from = document.getElementById("chart-from-date").value;
  const to = document.getElementById("chart-to-date").value;

  if (!from || !to) {
    alert("Vyplňte obě data!");
    return;
  }

  fetch(`/api/rates/historical?from=${from}&to=${to}`, { headers })
    .then((response) => {
      if (!response.ok) throw new Error("API error");
      return response.json();
    })
    .then((data) => {
      const dates = Object.keys(data.rates).sort();
      const currencies = Object.keys(data.rates[dates[0]]);
      console.log(dates, currencies, data.rates);
      const colors = [
        "rgba(76, 175, 80, 1)",
        "rgba(33, 150, 243, 1)",
        "rgba(255, 87, 34, 1)",
        "rgba(156, 39, 176, 1)",
        "rgba(255, 193, 7, 1)",
      ];

      const datasets = currencies.map((currency, index) => ({
        label: currency,
        data: dates.map((date) => data.rates[date][currency]),
        borderColor: colors[index % colors.length],
        backgroundColor: colors[index % colors.length].replace("1)", "0.1)"),
        borderWidth: 2,
        pointRadius: 3,
        tension: 0.3,
      }));

      const ctx = document.getElementById("historicalChart").getContext("2d");

      if (window.myHistoricalChart) window.myHistoricalChart.destroy();

      window.myHistoricalChart = new Chart(ctx, {
        type: "line",
        data: {
          labels: dates,
          datasets: datasets,
        },
        options: {
          responsive: true,
          plugins: {
            legend: { position: "top" },
          },
          scales: {
            y: {
              beginAtZero: false,
            },
          },
        },
      });
    })
    .catch((error) => showError(error));
}
updateLanguage();
loadSelectedCurrency();
loadRates();
loadStrongest();
loadWeakest();
