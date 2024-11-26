const id = new URLSearchParams(window.location.search).get("id")
const titleTextField = document.querySelector(".title")
const blogPostBody = document.querySelector(".content")
const likesParagraph = document.querySelector(".likes")
const likeButton = document.querySelector(".likeButton")

/**
 * Diese Funktion fragt den Server nach dem Post ab und stellt die Details dar.
 * @returns {Promise<void>}
 */
const renderDetails = async () => { //todo

    const response = await fetch("/posts/" + id)
    const json = await response.json();

    console.log(json);

    titleTextField.innerHTML = json.title
    blogPostBody.innerHTML = json.body
    likesParagraph.innerHTML = json.likes + " Likes"

    likeButton.addEventListener("click", async () => {
        
        const updatedLikes = json.likes + (json.liked ? -1 : 1);

        await fetch("/posts/" + id, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                id: json.id,
                title: json.title,
                body: json.body,
                likes: updatedLikes,
                liked: !json.liked,
            }),
        });

        renderDetails();

    });
};

renderDetails()