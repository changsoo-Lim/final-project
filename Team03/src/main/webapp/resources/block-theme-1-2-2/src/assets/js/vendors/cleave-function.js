// Cleave js

// Phone
if (document.querySelector(".input-phone")) {
   var cleavePhone = new Cleave(".input-phone", {
      phone: true,
      phoneRegionCode: "IN",
   });
}

// Date
if (document.querySelector(".input-date")) {
   var cleaveDate = new Cleave(".input-date", {
      date: true,
   });
}

// Credit card
if (document.querySelector(".input-credit-card")) {
   var cleaveCreditCard = new Cleave(".input-credit-card", {
      visaCard: true,
      blocks: [4, 4, 4, 4],
      delimiter: "-",
   });
}

// Date formatting manasvi
if (document.querySelector(".input-date-formatting")) {
   var cleave = new Cleave(".input-date-formatting", {
      date: true,
      delimiter: "-",
      datePattern: ["Y", "m", "d"],
   });
}

//Date formate
if (document.querySelector(".input-date-formate")) {
   var cleave = new Cleave(".input-date-formate", {
      date: true,
      datePattern: ["m", "y"],
   });
}

// Time formatting
if (document.querySelector(".input-time-formatting")) {
   var cleave = new Cleave(".input-time-formatting", {
      time: true,
      timePattern: ["h", "m", "s"],
   });
}

// Time formate
if (document.querySelector(".input-time-formate")) {
   var cleave = new Cleave(".input-time-formate", {
      time: true,
      timePattern: ["h", "m"],
   });
}

// Numeral formatting
if (document.querySelector(".input-numeral-formatting")) {
   var cleave = new Cleave(".input-numeral-formatting", {
      numeral: true,
      numeralThousandsGroupStyle: "thousand",
   });
}

//Custom options block
if (document.querySelector(".input-block")) {
   var cleave = new Cleave(".input-block", {
      blocks: [4, 3, 3, 4],
      uppercase: true,
   });
}

//Custom options delimiter
if (document.querySelector(".input-delimiter")) {
   var cleave = new Cleave(".input-delimiter", {
      delimiter: "·",
      blocks: [3, 3, 3],
      uppercase: true,
   });
}

//Custom options delimiters
if (document.querySelector(".input-delimiters")) {
   var cleave = new Cleave(".input-delimiters", {
      delimiters: [".", ".", "-"],
      blocks: [3, 3, 3, 2],
      uppercase: true,
   });
}

//Custom options prefix
if (document.querySelector(".input-prefix")) {
   var cleave = new Cleave(".input-prefix", {
      prefix: "PREFIX",
      delimiter: "-",
      blocks: [6, 4, 4, 4],
      uppercase: true,
   });
}
