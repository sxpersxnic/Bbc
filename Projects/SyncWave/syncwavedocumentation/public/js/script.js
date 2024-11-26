function dropDown(event) {
    const target = event.target;
    const container = target.closest('.dropdown-ctn');
    if (!container) return;

    const content = container.querySelector('.dropdown-content');

    // Close all other dropdowns
    document.querySelectorAll('.dropdown-content').forEach(otherContent => {
        if (otherContent !== content) {
            otherContent.classList.remove('show');
        }
    });

    // Toggle current dropdown
    content.classList.toggle('show');
}

// Attach the event listener to the parent container
document.querySelector('.navbar-desktop').addEventListener('click', dropDown);


window.onscroll = function() {scrollFunction()};
  
function scrollFunction() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        document.getElementById("header").style.top = "0";
    } else {
        document.getElementById("header").style.top = "0";
    }
}