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
  "Accept": "application/json"
};

function showError(error) {
  console.error("Chyba:", error);
  document.getElementById("api-error").style.display = "block";
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

loadRates();
loadStrongest();
loadWeakest();
