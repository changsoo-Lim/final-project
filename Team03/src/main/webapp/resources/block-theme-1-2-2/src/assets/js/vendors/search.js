// Search Input
var searchInput = document.querySelector("#quickSearch");
var listItems = document.querySelectorAll("#sidebarnav .nav-item");

if (listItems.length) {
   searchInput.addEventListener("input", function () {
      let filter = this.value.toLowerCase();
      let matches = [];

      listItems.forEach(function (item) {
         let text = item.textContent.toLowerCase();
         if (text.includes(filter)) {
            item.style.display = ""; // Show the item
            matches.push(item); // Push matched item to the array
         } else {
            item.style.display = "none"; // Hide the item
         }
      });

      // Check if there are any matches using the .length condition
      if (matches.length) {
         console.log(matches.length + " items found");
      } else {
         console.log("No items found");
      }
   });
}
