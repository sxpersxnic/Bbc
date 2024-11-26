function toggleSidebar() {
    var sidebar = document.getElementById("sidebar");
    sidebar.style.width = sidebar.style.width === "240px" ? "0" : "240px";
  
    var icon = document.querySelector('.accordion');
    icon.classList.toggle("change");
}