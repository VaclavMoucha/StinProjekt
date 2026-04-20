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