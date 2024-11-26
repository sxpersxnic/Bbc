document.addEventListener('DOMContentLoaded', () => {
    const sections = document.querySelectorAll('section');
    const toolTitles = document.querySelectorAll('.tool-title');
    const currentToolTitle = document.getElementById('current-title');
    const currentToolIcon = document.getElementById('current-icon');

    const observerOptions = {
        root: null,
        threshold: [0.1, 0.5, 0.9]
    };

    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.intersectionRatio > 0.8) {
                const sectionId = entry.target.id;
                updateCurrentToolTitleAndIcon(sectionId);
            }
        });
    }, observerOptions);

    sections.forEach(section => {
        observer.observe(section);
    });

    function updateCurrentToolTitleAndIcon(sectionId) {
        toolTitles.forEach(title => {
            title.classList.remove('fixed');
        });
        const currentTitle = document.querySelector(`#${sectionId} .tool-title`);
        const currentIcon = document.querySelector(`#${sectionId} .tool-icon`);
        if (currentTitle && currentIcon) {
            currentToolTitle.textContent = currentTitle.textContent;
            currentToolIcon.src = currentIcon.src;
            currentToolIcon.alt = currentIcon.alt;
        }
    }
});
