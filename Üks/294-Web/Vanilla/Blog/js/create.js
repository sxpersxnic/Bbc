const form = document.querySelector("form")

/**
 * Diese Methode wird aufgerufen, wenn das Formular eingereicht wird.
 * @returns {Promise<Response>}
 */
async function create() { //todo

  const title = document.getElementById("title").value
  const body = document.getElementById("body").value

  const post = {
    title: title,
    body: body,
    likes: 0
  }

  await fetch("/posts", {
    method: "POST",
    body: JSON.stringify(post),
    headers: {
      "Content-Type": "application/json"
    }
  })
}

form.addEventListener("submit", async (e) => {
  e.preventDefault()
  await create()
  window.location.replace("/index.html")
})
