const tabsToDropdown = document.querySelectorAll(".tabs-to-dropdown");

function generateDropdownMarkup(container) {
   const navWrapper = container.querySelector(".nav-wrapper");
   const navPills = container.querySelector(".nav-line-bottom");
   const firstTextLink = navPills.querySelector("li:first-child a").textContent;
   const items = navPills.querySelectorAll("li");
   const markup = `
     <div class="dropdown d-lg-none mb-4">
          <button class="btn btn-light w-100 d-flex justify-content-between dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            ${firstTextLink}
          </button>
          <div class="dropdown-menu w-100" aria-labelledby="dropdownMenuButton">
            ${generateDropdownLinksMarkup(items)}
          </div>
        </div>
  `;
   navWrapper.insertAdjacentHTML("afterbegin", markup);
}

function generateDropdownLinksMarkup(items) {
   let markup = "";
   items.forEach(function (item) {
      const textLink = item.querySelector("a").textContent.trim();
      markup += `<a class="dropdown-item" href="#">${textLink}</a>`;
   });
   return markup;
}

function showDropdownHandler(e) {
   const dropdown = e.target.closest(".dropdown");
   const dropdownToggle = dropdown.querySelector(".dropdown-toggle");
   const dropdownToggleText = dropdownToggle.textContent.trim();
   const dropdownMenuLinks = e.target.querySelectorAll(".dropdown-menu a");
   dropdownMenuLinks.forEach(function (link) {
      if (link.textContent.trim() === dropdownToggleText) {
         link.classList.add("d-none");
      } else {
         link.classList.remove("d-none");
      }
   });
}

function clickHandler(e) {
   e.preventDefault();
   const text = e.target.textContent;
   const dropdown = e.target.closest(".dropdown");
   dropdown.querySelector(".dropdown-toggle").textContent = text;

   const tabsToDropdownContainer = e.target.closest(".tabs-to-dropdown");
   const index = Array.from(dropdown.querySelectorAll(".dropdown-menu a")).indexOf(e.target);
   const tabToShow = tabsToDropdownContainer.querySelectorAll(".nav-pills li a")[index];

   // Trigger click event on the corresponding tab link
   tabToShow.click();
}

function shownTabsHandler(e) {
   const index = Array.from(e.target.parentNode.parentNode.children).indexOf(e.target.parentNode);
   const parent = e.target.closest(".tabs-to-dropdown");
   const targetDropdownLink = parent.querySelectorAll(".dropdown-menu a")[index];
   parent.querySelector(".dropdown-toggle").textContent = targetDropdownLink.textContent;
}

tabsToDropdown.forEach(function (tabsToDropdownContainer) {
   const pills = tabsToDropdownContainer.querySelectorAll('a[data-bs-toggle="pill"]');

   generateDropdownMarkup(tabsToDropdownContainer);

   const dropdown = tabsToDropdownContainer.querySelector(".dropdown");
   const dropdownLinks = tabsToDropdownContainer.querySelectorAll(".dropdown-menu a");

   dropdown.addEventListener("show.bs.dropdown", showDropdownHandler);
   dropdownLinks.forEach(function (link) {
      link.addEventListener("click", clickHandler);
   });
   pills.forEach(function (pill) {
      pill.addEventListener("shown.bs.tab", shownTabsHandler);
   });
});
