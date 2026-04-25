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
      const checkboxContainer = document.getElementById("checkbox-container");

      tbody.innerHTML = "";
      if (checkboxContainer) checkboxContainer.innerHTML = "";

      for (const [currency, rate] of Object.entries(data.rates)) {
        // Tabulka (původní kód)
        tbody.innerHTML += `
                    <tr>
                        <td>${currency}</td>
                        <td>${rate.toFixed(4)}</td>
                    </tr>`;
        if (checkboxContainer) {
          const label = document.createElement("label");
          label.style =
            "display: inline-flex; align-items: center; margin-right: 15px; cursor: pointer; padding: 5px; border: 1px solid #eee; border-radius: 4px; margin-bottom: 5px;";

          label.innerHTML = `
            <input type="checkbox" class="chart-curr-checkbox" value="${currency}" checked>
            <span style="margin-left: 5px;">${currency}</span>
          `;
          checkboxContainer.appendChild(label);
        }
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

  const selectedCheckboxes = document.querySelectorAll(
    ".chart-curr-checkbox:checked",
  );
  const activeCurrencies = Array.from(selectedCheckboxes).map((cb) => cb.value);

  if (!from || !to) {
    alert("Vyplňte obě data!");
    return;
  }

  if (activeCurrencies.length === 0) {
    alert("Zaškrtněte alespoň jednu měnu!");
    return;
  }

  fetch(`/api/rates/historical?from=${from}&to=${to}`, { headers })
    .then((response) => {
      if (!response.ok) throw new Error("API error");
      return response.json();
    })
    .then((data) => {
      const dates = Object.keys(data.rates).sort();
      const currencies = Object.keys(data.rates[dates[0]]).filter((c) =>
        activeCurrencies.includes(c),
      );

      const colors = [
        "#4caf50",
        "#2196f3",
        "#ff5722",
        "#9c27b0",
        "#ffc107",
        "#00bcd4",
      ];

      const datasets = currencies.map((currency, index) => ({
        label: currency,
        data: dates.map((date) => data.rates[date][currency]),
        borderColor: colors[index % colors.length],
        backgroundColor: colors[index % colors.length] + "1A", // jemné pozadí
        borderWidth: 2,
        tension: 0.3,
      }));

      const ctx = document.getElementById("historicalChart").getContext("2d");
      if (window.myHistoricalChart) window.myHistoricalChart.destroy();

      window.myHistoricalChart = new Chart(ctx, {
        type: "line",
        data: { labels: dates, datasets: datasets },
        options: {
          responsive: true,
          plugins: { legend: { position: "top" } },
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
