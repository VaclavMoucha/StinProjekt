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