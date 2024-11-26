document.addEventListener('DOMContentLoaded', () => {
    const sections = document.querySelectorAll('section');
    const sprintTitles = document.querySelectorAll('.sprinttitle');
    const currentSprintTitle = document.getElementById('current-sprint');
    const headerHeight = document.querySelector('.header').offsetHeight;
    
    const observerOptions = {
        root: null,
        threshold: [0.1, 0.5, 0.9]
    };

    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.intersectionRatio > 0.5) {
                const sectionId = entry.target.id;
                updateCurrentSprintTitle(sectionId);
            }
        });
    }, observerOptions);

    sections.forEach(section => {
        observer.observe(section);
    });

    function updateCurrentSprintTitle(sectionId) {
        sprintTitles.forEach(title => {
            title.classList.remove('fixed');
        });
        const currentTitle = document.querySelector(`#${sectionId} .sprinttitle`);
        if (currentTitle) {
            currentSprintTitle.textContent = currentTitle.textContent;
        }
    }
});
